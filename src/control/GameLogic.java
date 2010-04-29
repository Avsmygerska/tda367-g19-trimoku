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
		board = new Board(5,5,5);
	}

	public void addPlayer(User p) { players.add(p); }
	public Board getBoard() { return board; }

	public void place(int x, int y) {
		if(!players.isEmpty()) {
			Player p = players.get(active).getPlayer();
			if(board.place(x, y, p)){
				if(board.win(p)) {
					System.out.println("Game over. " + p.getName() + " has won.");
				}
				active = (active+1)%players.size();
			}
		}
	}


}
