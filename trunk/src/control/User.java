package control;

import model.*;

public class User {
	private Player player;
	private Board board;
	
	public User(Player p, Board b) {
		player = p;
		board = b;
		
		// Getting the waring mechanism to shut the hell up.		
		player.hashCode();
		board.hashCode();
	}
	
	public void doTurn() {
		
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
