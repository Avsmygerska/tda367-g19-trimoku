package control;

import java.util.ArrayList;

import model.*;
import view.*;


public class GameLogic {
	ArrayList<User> players;
	int active;
	Board b;
	
	public GameLogic () {
		
	}
	
	public GameLogic (int x, int y, int z) {
		reset(x,y,z);				
	}
	
	public void reset(int x, int y, int z) {
		players = new ArrayList<User>();
		b = new Board(5,5,5);
	}
	
	public void addPlayer(User p) {
		players.add(p);
	}	
	
	public void place(int x, int y) {
		if(!players.isEmpty())
			if(b.place(x, y, players.get(active).getPlayer())){
				active = (active+1)%players.size();
			}				
	}
	

}
