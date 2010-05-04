package control;

import java.util.ArrayList;

import model.*;

public class GameLogic {
	ArrayList<User> players;
	int active;
	Board board;

	public GameLogic (int x, int y, int z) {
		reset(x,y,z);				
	}

	public void reset(int x, int y, int z) {
		players = new ArrayList<User>();
		board = new Board(x,y,z);
	}
	
	public String getNotice() {
		return players.get(active).getPlayer().getName() + "s turn.";
	}

	public void addUser(User p) { players.add(p); }
	public Board getBoard() { return board; }
	
	public void run () {
		User u;		
		while(true) {			
			u = players.get(active);
			u.doTurn();
			
			if(board.win(u.getPlayer())) {
				System.out.println("Game over. " + u.getPlayer().getName() + " has won.");
				break;
			}
			
			/*  DRAWN GAME
			if(board.win(u.getPlayer())) {
				System.out.println("Game over. " + u.getPlayer().getName() + " has won.");
				break;
			}
			*/
			
			active = (active+1)%players.size();
		}		
	}
}
