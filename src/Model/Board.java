package Model;

import java.util.ArrayList;

public class Board {
	
	//ArrayList<ArrayList<ArrayList<Player>>> brd;
	Player[][][] brd2;
	Player NO_PLAYER = new Player("NO_PLAYER");
	
	int maxX;
	int maxY;
	int maxZ;

	public Board(int x, int y, int z) {
		maxX = x;
		maxY = y;
		maxZ = z;
		
		/*brd = new ArrayList<ArrayList<ArrayList<Player>>>();
		for(int i = 0; i < x+1; i++){
			ArrayList <ArrayList<Player>> tmp = new ArrayList<ArrayList<Player>>();
			for (int j = 0; j < y+1; j++) {
				tmp.add(new ArrayList<Player>());
			}
			brd.add(tmp);
		}*/
		brd2 = new Player[maxX][maxY][maxZ];
	}
	
	/*public Player peek(int x, int y, int z) {
		return brd.get(x).get(y).get(z);		
	}*/
	
	// Returns a null if the given position was empty.
	public Player peek2(int x, int y, int z) {
		if(brd2[x][y][z] == null){
			return NO_PLAYER;
		}
		return brd2[x][y][z];		
	}
	
	/*public boolean tryPlace(int x, int y) {
		return (brd.get(x).get(y).size() <= maxZ);
	}*/
	
	public boolean tryPlace2(int x, int y) {
		if (x >= maxX || y >= maxY)
			return false;
		
		for(int i = 0; i < maxZ;i++){
			if(brd2[x][y][i] == null){				
				return true;
			}		
		}
		return false;
	}
	
	/*public boolean place(int x, int y, Player p) {
		
		if (x > maxX || y > maxY)
			return false;
		
		if (brd.get(x).get(y).size() <= maxZ) {
			brd.get(x).get(y).add(p);
			return true;			
		}
		
		return false;		
	}*/
	
	public boolean place2(int x, int y, Player p) {
		
		if (x >= maxX || y >= maxY)
			return false;
		
		for(int i = 0; i < maxZ;i++){
			if(brd2[x][y][i] == null){
				brd2[x][y][i] = p;
				return true;
			}		
		}
		return false;
	}
	
	
	/*public boolean win(Player p) {
		
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
		}
		
		return false;
	}*/
	
	public boolean win2(Player p) {
		
		if(checkX(p)){
			return true;
		}
		if(checkY(p)){
			return true;
		}
		if(checkZ(p)){
			return true;
		}
		if(checkInnerDiagonals(p)){
			return true;
		}
		
		return false;
	}
	
	public boolean checkX(Player p){
		// Check if a player have 5 in a row in the X-dimension
		for(int y = 0; y < maxY; y++){			
			for(int z = 0; z < maxZ; z++) {
				int cal = 0;
				for(int x = 0; x < maxY; x++) {
					
					if (brd2[x][y][z] == null || !brd2[x][y][z].equals(p) ){
						break;
					}
					cal++;
				}
				if (cal == maxZ){
					return true;
				}
			}			
		}
		// Check if a player have won on a diagonal in the X-dimension
		for(int x = 0; x < maxX; x++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < maxX){
				if( brd2[x][i][i] == null || !brd2[x][i][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd2[x][i][maxX-1-i] == null || !brd2[x][i][maxX-1-i].equals(p)){
					winDiagonalDown = false;
				}
				i++;
			}
			if(winDiagonalDown || winDiagonalUp){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkY(Player p){
		
		// Check if a player have 5 in a row in the Y-dimension
		for(int x = 0; x < maxX; x++){			
			for(int z = 0; z < maxZ; z++) {
				int cal = 0;
				for(int y = 0; y < maxY; y++) {
					if (brd2[x][y][z] == null || !brd2[x][y][z].equals(p)){
						break;
					}
					cal++;
				}
				if (cal == maxZ){
					return true;
				}
			}			
		}
		
		// Check if a player have won on a diagonal in the y-dimension
		for(int y = 0; y < maxY; y++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < maxY){
				if( brd2[i][y][i] == null || !brd2[i][y][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd2[i][y][maxY-1-i] == null || !brd2[i][y][maxY-1-i].equals(p)){
					winDiagonalDown = false;
				}
				i++;
			}
			if(winDiagonalDown || winDiagonalUp){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkZ(Player p){
		
		// Check if a player have 5 in a row in the Z-dimension
		for(int x = 0; x < maxX; x++){
			for(int y = 0; y < maxY; y++) {
				int cal = 0;
				for(int z = 0; z < maxZ; z++) {
					if (brd2[x][y][z] == null || !brd2[x][y][z].equals(p)){
						break;
					}
					cal++;
				}
				if (cal == maxZ){
					return true;
				}
			}			
		}
		// Check if a player have won on a diagonal in the z-dimension
		for(int z = 0; z < maxZ; z++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < maxZ){
				if( brd2[i][i][z] == null || !brd2[i][i][z].equals(p)){
					winDiagonalUp = false;
				}
				if( brd2[maxZ-1-i][i][z] == null || !brd2[maxZ-1-i][i][z].equals(p)){
					winDiagonalDown = false;
				}
				i++;
			}
			if(winDiagonalDown || winDiagonalUp){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkInnerDiagonals(Player p){
		
		/* 
	 		Here we want to check if a player have won in two of the inner diagonals.
	 		Example: From (0,0,0) - (4,4,4) and (0,0,4) - (4,4,0)
		*/
		
		boolean winDiagonalDown = true;
		boolean winDiagonalUp   = true;
		int i = 0;
		while((winDiagonalDown || winDiagonalUp) && i < maxX){
			if( brd2[i][i][i] == null || !brd2[i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd2[i][i][maxX-1-i] == null || !brd2[i][i][maxX-1-i].equals(p)){
				winDiagonalDown = false;
			}
			i++;
		}
		if(winDiagonalDown || winDiagonalUp){
			return true;
		}
		
		/* 
		 	Here we want to check if a player have won in two of the inner diagonals.
		 	Example: From (4,0,0) - (0,4,4) and (4,0,4) - (0,4,0)
		*/ 
		winDiagonalDown = true;
		winDiagonalUp   = true;
		i = 0;
		while((winDiagonalDown || winDiagonalUp) && i < maxX){
			if( brd2[maxX-1-i][i][i] == null || !brd2[maxX-1-i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd2[maxX-1-i][i][maxX-1-i] == null || !brd2[maxX-1-i][i][maxX-1-i].equals(p)){
				winDiagonalDown = false;
			}
			i++;
		}
		if(winDiagonalDown || winDiagonalUp){
			return true;
		}
						
		return false;
	}
}
