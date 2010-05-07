package control;

import java.util.ArrayList;
import view.MainFrame;
import view.UserInterface;

import java.util.concurrent.CountDownLatch;

import model.*;

public class GameLogic {

	private ArrayList<User> players;
	private int active;
	private Board board;
	MainFrame mainframe;
	CountDownLatch ready;

	UserInterface ui;

	boolean reset;

	public GameLogic (UserInterface ui) {
		players = new ArrayList<User>();
		setUserInterface(ui);
		ready = new CountDownLatch(0);
	}

	public void setUserInterface(UserInterface ui) {
		this.ui = ui;
		ui.setGameLogic(this);
	}

	public void configure (int x, int y, int z, ArrayList<User> newPlayers) {
		reset = true;
		
		for(User u : players)
			u.disconnect();
		
		players.clear();
		players.addAll(newPlayers);
		active = 0;
		
		board = new Board(x,y,z);
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

	public void setMainFrame(MainFrame mf){
		this.mainframe = mf;
	}
}
