package control;

import java.util.ArrayList;

import Model.*;
import View.*;


public class GameLogic {
	ArrayList<Player> players;
	Board b;
	
	public GameLogic (int x, int y, int z) {
		reset();
		
	}
	
	public void reset() {
		players = new ArrayList<Player>();
		b = new Board(5,5,5);
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void run() {
		for (Player p : players) {
			/*
			p.doTurn();
			if(b.win(p)) {
				gameover(p);
			}
			if(draw) {
				drawnGame();
				newGame();				
			}
			*/
		}	
	}
	

}
