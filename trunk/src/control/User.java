package control;

import model.*;

public class User {
	private Player player;
	private Board board;
	
	public User(Player p) {
		player = p;
	}
	
	public User(Player p, Board b) {
		player = p;
		board = b;
	}
	
	public void setBoard(Board board){
		this.board = board;	
	}
	
	public void doTurn() {
		board.place(0, 0, player);		
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
