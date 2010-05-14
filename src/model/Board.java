package model;

import java.awt.Point;
import java.util.ArrayList;

public class Board {
	
	Player[][][] brd;
	
	private int rows, cols, lays;
	private Point lastMove;
	private int win;
	
	private ArrayList<Integer> incCols;
	private ArrayList<Integer> decCols;
	private ArrayList<Integer> incRows;
	private ArrayList<Integer> decRows;
	private ArrayList<Integer> incLays;
	private ArrayList<Integer> decLays;
	
	public Board(int r, int c, int l) {
		rows = r;
		cols = c;
		lays = l;
		win  = Math.min(Math.min(r, c), l);
		
		lastMove = new Point();
		brd = new Player[rows][cols][lays];
		
		incCols = new ArrayList<Integer>();
		for(int i = 0; i < c; i++) incCols.add(i);
		
		incRows = new ArrayList<Integer>();
		for(int i = 0; i < r; i++) incRows.add(i);
		
		incLays = new ArrayList<Integer>();
		for(int i = 0; i < l; i++) incLays.add(i);
		
		decCols = new ArrayList<Integer>();
		for(int i = c - 1; i > 0; i--) decCols.add(i);
		
		decRows = new ArrayList<Integer>();
		for(int i = r - 1; i > 0; i--) decRows.add(i);
		
		decLays = new ArrayList<Integer>();
		for(int i = l - 1; i > 0; i--)
			decLays.add(i);		
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
	public int getWinLength() { return win; }
	
	public boolean tryPlace(int row, int col) {
		if (row >= rows || col >= cols)
			return false;
		
		for(int l : incLays){
			if(brd[row][col][l] == null){				
				return true;
			}		
		}
		return false;
	}
	
	public boolean place(int row, int col, Player p) {
		
		if (row >= rows || col >= cols)
			return false;
		
		for(int l : incLays){
			if(brd[row][col][l] == null){
				brd[row][col][l] = p;
				lastMove.x = row;
				lastMove.y = col;
				return true;
			}		
		}
		return false;
	}
	
	public boolean isFull() {		
		for(int r : incRows)
			for(int c : incCols)
				for(int l : incLays)
					if(brd[r][c][l] == null)
						return false;
		return true;
	}
	
	public boolean win(Player p) {
		return checkX(p) || checkY(p) || checkZ(p) || checkInnerDiagonals(p);
	}
	
	private boolean checkX(Player p){
		// Check if a player have 5 in a row in the X-dimension
		for(int c : incCols){			
			for(int l : incLays) {
				int cal = 0;
				for(int r : incCols) {
					
					if (brd[r][c][l] == null || !brd[r][c][l].equals(p) ){
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
		for(int r : incRows){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < rows){
				if( brd[r][i][i] == null || !brd[r][i][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[r][i][rows-1-i] == null || !brd[r][i][rows-1-i].equals(p)){
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
		for(int r : incRows){			
			for(int l : incLays) {
				int cal = 0;
				for(int c : incCols) {
					if (brd[r][c][l] == null || !brd[r][c][l].equals(p)){
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
		for(int c : incCols){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < cols){
				if( brd[i][c][i] == null || !brd[i][c][i].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[i][c][cols-1-i] == null || !brd[i][c][cols-1-i].equals(p)){
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
		for(int r : incRows){
			for(int c : incCols) {
				int cal = 0;
				for(int z = 0; z < lays; z++) {
					if (brd[r][c][z] == null || !brd[r][c][z].equals(p)){
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
		for(int l : incLays){
			boolean winDiagonalDown = true;
			boolean winDiagonalUp   = true;
			int i = 0;
			while((winDiagonalDown || winDiagonalUp) && i < lays){
				if( brd[i][i][l] == null || !brd[i][i][l].equals(p)){
					winDiagonalUp = false;
				}
				if( brd[lays-1-i][i][l] == null || !brd[lays-1-i][i][l].equals(p)){
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
