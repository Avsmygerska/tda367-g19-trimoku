package Model;

import java.util.ArrayList;

public class Board {
	
	ArrayList<ArrayList<ArrayList<Player>>> brd;
	
	int maxX;
	int maxY;
	int maxZ;

	public Board(int x, int y, int z) {
		maxX = x;
		maxY = y;
		maxZ = z;
		
		brd = new ArrayList<ArrayList<ArrayList<Player>>>();
		for(int i = 0; i < x+1; i++){
			ArrayList <ArrayList<Player>> tmp = new ArrayList<ArrayList<Player>>();
			for (int j = 0; j < y+1; j++) {
				tmp.add(new ArrayList<Player>());
			}
			brd.add(tmp);
		}
				
	}
	
	public Player peek(int x, int y, int z) {
		return brd.get(x).get(y).get(z);		
	}
	
	public boolean tryPlace(int x, int y) {
		return (brd.get(x).get(y).size() <= maxZ);
	}
	public boolean place(int x, int y, Player p) {		
		if (brd.get(x).get(y).size() <= maxZ) {
			brd.get(x).get(y).add(p);
			return true;			
		}		
		
		return false;		
	}
	
	public boolean win(Player p) {
		
		// Z:s
		for(int x = 0; x < brd.size(); x++){			
			for(int y = 0; y < brd.get(x).size(); y++) {
				int cal = 0;
				for(int z = 0; z < brd.get(x).get(y).size(); z++) {
					if (!brd.get(x).get(y).get(z).equals(p))
						break;
					cal++;
				}
				if (cal == maxZ+1){
					return true;
				}
			}			
		}
		/*
		// X:s
		for(int z = 0; z < maxZ; z++){			
			for(int y = 0; y < maxY; y++) {
				int cal = 0;
				for(int x = 0; x < maxX; x++) {
					if (!brd.get(x).get(y).get(z).equals(p))
						break;
					cal++;
				}
				if (cal == maxX+1){
					return true;
				}
			}			
		}
		
		// Y:s		
		for(int x = 0; x < maxX; x++){			
			for(int z = 0; z < maxZ; z++) {
				int cal = 0;
				for(int y = 0; y < maxY; y++) {
					if (!brd.get(x).get(y).get(z).equals(p))
						break;
					cal++;
				}
				if (cal == maxY+1){
					return true;
				}
			}			
		}*/
		
		return false;
	}
	
}
