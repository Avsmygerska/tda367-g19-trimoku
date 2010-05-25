package control;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import control.interfaces.*;
import view.interfaces.*;
import model.*;

public class TrimokuLogic implements GameLogic {

	private ArrayList<User> players;
	private int active;
	private Board board;
	private CountDownLatch ready;
	private UserInterface ui;
	private boolean reset;

	public TrimokuLogic () {
		players = new ArrayList<User>();
		ready = new CountDownLatch(0);
	}

	public void setUserInterface(UserInterface ui) {
		this.ui = ui;
		ui.setGameLogic(this);
	}

	public void configure (int dim, GameMode gm, ControlInterface ci, ArrayList<Player> pl){
		reset = true;
		
		for(User u : players)
			u.disconnect();

		players.clear();

		switch(gm) {
		case HOT_SEAT:
			for(Player p : pl)
				players.add(new LocalUser(p,ci));
			break;
		case AI:
			players.add(new LocalUser(pl.get(0),ci));
			players.add(new TidyAI(pl.get(1)));
			break;
		}
		active = 0;
		
		board = new Board(dim);
		ui.newModel(board);
		ready.countDown();

	}

	public void run () {
		User u;
		while(true) {
			try {
				// Wait for the configuration to complete.
				ready.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(reset) {
				// The game was reset for some reason, it's the first players turn
				active = 0;
				reset = false;
			}			
			u = players.get(active);
			ui.getNotifier().notifyTurn(u.getNotice());
			u.doTurn(board);
			ui.updateModel();
			if(board.win(u.getPlayer())) {
				// The active player won.
				ready = new CountDownLatch(1);
				ui.getNotifier().notifyTurn((u.getPlayer().getName() + " has won."));
				ui.postGame();
				ui.wonGame(u);
			} else if(board.isFull()) {
				// Drawn game.
				ready = new CountDownLatch(1);
				ui.getNotifier().notifyTurn("Drawn game.");
				ui.postGame();
				ui.drawnGame();
			} else {
				// Next player;
				active = (active+1)%players.size();
			}
		}
	}
}
