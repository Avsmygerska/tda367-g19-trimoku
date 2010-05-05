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

	public GameLogic (UserInterface ui) {
		setUserInterface(ui);
		ready = new CountDownLatch(0);
	}
	
	public void setUserInterface(UserInterface ui) {
		System.out.println("Connecting Logic to UI.");
		this.ui = ui;
		ui.setGameLogic(this);
	}

	public void configure (int x, int y, int z, ArrayList<User> players) {				
		board = new Board(x,y,z);
		ui.updateModel(board);
		this.players = players;
		active = 0;
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
			u = players.get(active);			
			ui.getNotifier().notifyTurn("It is your turn " + u.getPlayer().getName());
			u.doTurn(board);
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
				System.out.println("Drawn game.");
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
