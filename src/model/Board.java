package model;

import java.awt.Point;
import java.util.ArrayList;

public class Board {
	
	Player[][][] brd;
	
	private int rows, cols, lays;
	private Point lastMove;
	
	
	public Board(int r, int c, int l) {
		rows = r;
		cols = c;
		lays = l;
		
		lastMove = new Point();
		brd = new Player[rows][cols][lays];
	}
	
	// Could return a null pointer, if there is no peice at the specified place.
	public Player peek(int r, int c, int l) {
		if(!(r < rows && c < cols && l < lays))
			return null;
		return brd[r][c][l];		
	}
		
	public ArrayList<Player> getPin(int row, int col) {
		ArrayList<Player> pin = new ArrayList<Player>();
		
		for (int lay = 0; lay < lays; lay++) {
			if(brd[row][col][lay] == null)
				break;
			pin.add(brd[row][col][lay]);			
		}		
		return pin;		
	}
	
	public int getRows() { return rows; }
	public int getColumns() { return cols; }
	public int getLayers() { return lays; }	
	
	public boolean tryPlace(int row, int col) {
		if (row >= rows || col >= cols)
			return false;
		
		for(int l = 0; l < lays;l++){
			if(brd[row][col][l] == null){				
				return true;
			}		
		}
		return false;
	}
	
	public boolean place(int row, int col, Player p) {
		
		if (row >= rows || col >= cols)
			return false;
		
		for(int i = 0; i < lays;i++){
			if(brd[row][col][i] == null){
				brd[row][col][i] = p;
				lastMove.x = row;
				lastMove.y = col;
				return true;
			}		
		}
		return false;
	}
	
	public boolean isFull() {
		for(int x = 0; x < rows;x++)
			for(int y = 0; y < cols;y++)
				for(int z = 0; z < lays;z++)
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
		for(int y = 0; y < cols; y++){			
			for(int z = 0; z < lays; z++) {
				int cal = 0;
				for(int x = 0; x < cols; x++) {
					
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p) ){
						break;
					}
					cal++;
				}
				if (cal == lays){
					return true;
				}
			}			
		}
		// Check if a player have won on a diagonal in the X-dimension
		for(int x = 0; x < rows; x++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < rows){
				if( brd[x][i][i] == null || !brd[x][i][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[x][i][rows-1-i] == null || !brd[x][i][rows-1-i].equals(p)){
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
		for(int x = 0; x < rows; x++){			
			for(int z = 0; z < lays; z++) {
				int cal = 0;
				for(int y = 0; y < cols; y++) {
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p)){
						break;
					}
					cal++;
				}
				if (cal == lays){
					return true;
				}
			}			
		}
		
		// Check if a player have won on a diagonal in the y-dimension
		for(int y = 0; y < cols; y++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < cols){
				if( brd[i][y][i] == null || !brd[i][y][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[i][y][cols-1-i] == null || !brd[i][y][cols-1-i].equals(p)){
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
		for(int x = 0; x < rows; x++){
			for(int y = 0; y < cols; y++) {
				int cal = 0;
				for(int z = 0; z < lays; z++) {
					if (brd[x][y][z] == null || !brd[x][y][z].equals(p)){
						break;
					}
					cal++;
				}
				if (cal == lays){
					return true;
				}
			}			
		}
		// Check if a player have won on a diagonal in the z-dimension
		for(int z = 0; z < lays; z++){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < lays){
				if( brd[i][i][z] == null || !brd[i][i][z].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[lays-1-i][i][z] == null || !brd[lays-1-i][i][z].equals(p)){
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
		while((winDiagonalDown || winDiagonalUp) && i < rows){
			if( brd[i][i][i] == null || !brd[i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd[i][i][rows-1-i] == null || !brd[i][i][rows-1-i].equals(p)){
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
		while((winDiagonalDown || winDiagonalUp) && i < rows){
			if( brd[rows-1-i][i][i] == null || !brd[rows-1-i][i][i].equals(p)){
				winDiagonalUp = false;
			}
			if( brd[rows-1-i][i][rows-1-i] == null || !brd[rows-1-i][i][rows-1-i].equals(p)){
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
		brd = new Player[rows][cols][lays];
	}
	
	public Point getLastMove(){
		return lastMove;
	}
}
