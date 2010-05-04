package model;

import java.util.ArrayList;

public class Board {
	
	Player[][][] brd;
	
	private int maxX, maxY, maxZ;
	
	
	public Board(int x, int y, int z) {
		maxX = x;
		maxY = y;
		maxZ = z;
		
		brd = new Player[maxX][maxY][maxZ];
	}
	
	// Could return a null pointer, if there is no peice at the specified place.
	public Player peek(int x, int y, int z) {
		if(!(x < maxX && y < maxY && z < maxZ))
			return null;
		return brd[x][y][z];		
	}
		
	public ArrayList<Player> getPin(int x, int y) {
		ArrayList<Player> pin = new ArrayList<Player>();
		
		for (int z = 0; z < maxZ; z++) {
			if(brd[x][y][z] == null)
				break;
			pin.add(brd[x][y][z]);			
		}		
		return pin;		
	}
	
	public int getX() { return maxX; }
	public int getY() { return maxY; }
	public int getZ() { return maxZ; }	
	
	public boolean tryPlace(int x, int y) {
		if (x >= maxX || y >= maxY)
			return false;
		
		for(int i = 0; i < maxZ;i++){
			if(brd[x][y][i] == null){				
				return true;
			}		
		}
		return false;
	}
	
	public boolean place(int x, int y, Player p) {
		
		if (x >= maxX || y >= maxY)
			return false;
		
		for(int i = 0; i < maxZ;i++){
			if(brd[x][y][i] == null){
				brd[x][y][i] = p;
				return true;
			}		
		}
		return false;
	}
	
	public boolean isFull() {
		for(int x = 0; x < maxX;x++)
			for(int y = 0; y < maxY;y++)
				for(int z = 0; z < maxZ;z++)
					if(brd[x][y][z] == null)
						return false;
		return true;
	}
	
	public boolean win(Player p) {
		
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
	
	private boolean checkX(Player p){
		// Check if a player have 5 in a row in the X-dimension
		for(int y = 0; y < maxY; y++){			
			for(int z = 0; z < maxZ; z++) {
				int cal = 0;
				for(int x = 0; x < maxY; x++) {
					
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p) ){
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
				if( brd[x][i][i] == null || !brd[x][i][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[x][i][maxX-1-i] == null || !brd[x][i][maxX-1-i].equals(p)){
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
	
	private boolean checkY(Player p){
		
		// Check if a player have 5 in a row in the Y-dimension
		for(int x = 0; x < maxX; x++){			
			for(int z = 0; z < maxZ; z++) {
				int cal = 0;
				for(int y = 0; y < maxY; y++) {
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p)){
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
				if( brd[i][y][i] == null || !brd[i][y][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[i][y][maxY-1-i] == null || !brd[i][y][maxY-1-i].equals(p)){
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
	
	private boolean checkZ(Player p){
		
		// Check if a player have 5 in a row in the Z-dimension
		for(int x = 0; x < maxX; x++){
			for(int y = 0; y < maxY; y++) {
				int cal = 0;
				for(int z = 0; z < maxZ; z++) {
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p)){
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
				if( brd[i][i][z] == null || !brd[i][i][z].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[maxZ-1-i][i][z] == null || !brd[maxZ-1-i][i][z].equals(p)){
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
	
	private boolean checkInnerDiagonals(Player p){
		
		/* 
	 		Here we want to check if a player have won in two of the inner diagonals.
	 		Example: From (0,0,0) - (4,4,4) and (0,0,4) - (4,4,0)
		*/
		
		boolean winDiagonalDown = true;
		boolean winDiagonalUp   = true;
		int i = 0;
		while((winDiagonalDown || winDiagonalUp) && i < maxX){
			if( brd[i][i][i] == null || !brd[i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd[i][i][maxX-1-i] == null || !brd[i][i][maxX-1-i].equals(p)){
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
			if( brd[maxX-1-i][i][i] == null || !brd[maxX-1-i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd[maxX-1-i][i][maxX-1-i] == null || !brd[maxX-1-i][i][maxX-1-i].equals(p)){
				winDiagonalDown = false;
			}
			i++;
		}
		if(winDiagonalDown || winDiagonalUp){
			return true;
		}
						
		return false;
	}
		
	public void clear(){
		brd = new Player[maxX][maxY][maxZ];
	}
}
