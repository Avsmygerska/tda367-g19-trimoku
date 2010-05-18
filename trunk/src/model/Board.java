package model;

import java.awt.Point;
import java.util.ArrayList;

public class Board {

	Player[][][] brd;

	private int rows, cols, lays;
	private Point lastMove;
	private int win;

	public Board(int sides) {
		// We only allow for boards with equal number of sides, or the rule-set becomes really weird.
		rows = cols = lays = win = sides;
		
		lastMove = new Point();
		brd = new Player[rows][cols][lays];		
	}	

	// Could return a null pointer, if there is no piece at the specified place.
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

	public int getRows()      { return rows; }
	public int getColumns()   { return cols; }
	public int getLayers()    { return lays; }
	public int getWinLength() { return win; }

	public boolean tryPlace(int row, int col) {
		if (row < rows && col < cols)
			for(int l = 0; l < lays;l++)
				if(brd[row][col][l] == null)				
					return true;

		return false;
	}

	public boolean place(int row, int col, Player p) {
		if (row < rows && col < cols)
			for(int l = 0; l < lays; l++)
				if(brd[row][col][l] == null){
					brd[row][col][l] = p;
					lastMove.x = row;
					lastMove.y = col;
					return true;
				}

		return false;
	}

	public boolean isFull() {		
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				for(int l = 0; l < lays; l++)
					if(brd[r][c][l] == null)
						return false;
		return true;
	}

	public boolean win(Player p) {
		return straightLine(p) || diagonalLine(p) || innerDiagonals(p);
	}

	// Check if the given player has produced a straight line in any direction.
	private boolean straightLine(Player p){
		for(int l = 0; l < lays;l++) {
			// In a column.
			for(int c = 0; c < cols; c++){
				int cal = 0;
				for(int r = 0; r < rows; r++,cal++)					
					if (brd[r][c][l] == null || !brd[r][c][l].equals(p) )
						break;

				if (cal == win)
					return true;
			}
			// In a row.
			for(int r = 0; r < rows; r++){
				int cal = 0;
				for(int c = 0; c < cols; c++,cal++)
					if (brd[r][c][l] == null || !brd[r][c][l].equals(p))
						break;

				if (cal == lays)
					return true;
			}
		}
		
		// Straight up.
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < cols; c++) {
				int cal = 0;
				for(int l = 0; l < lays;l++,cal++)
					if (brd[r][c][l] == null || !brd[r][c][l].equals(p))
						break;
				if (cal == lays)
					return true;
			}			
		}

		return false;
	}

	private boolean diagonalLine(Player p){
		
		boolean winDiagonalDown = true;
		boolean winDiagonalUp   = true;
		
		for(int c = 0; c < cols; c++){
			winDiagonalDown = true;
			winDiagonalUp   = true;
			for(int step = 0;(winDiagonalDown || winDiagonalUp) && step < win; step++){
				if( brd[step][c][step] == null || !brd[step][c][step].equals(p))
					winDiagonalUp = false;
				if( brd[step][c][lays-1-step] == null || !brd[step][c][lays-1-step].equals(p))
					winDiagonalDown = false;
			}
			if(winDiagonalDown || winDiagonalUp)
				return true;
		}
		
		for(int r = 0; r < rows; r++){
			winDiagonalDown = true;
			winDiagonalUp   = true;
			for(int step = 0;(winDiagonalDown || winDiagonalUp) && step < win; step++){
				if( brd[r][step][step] == null || !brd[r][step][step].equals(p))
					winDiagonalUp = false;
				if( brd[r][step][lays-1-step] == null || !brd[r][step][lays-1-step].equals(p))
					winDiagonalDown = false;
			}

			if(winDiagonalDown || winDiagonalUp)
				return true;
		}
		
		for(int l = 0; l < lays; l++){
			winDiagonalDown = true;
			winDiagonalUp   = true;
			for(int step = 0;(winDiagonalDown || winDiagonalUp) && step < win; step++){
				if( brd[step][step][l] == null || !brd[step][step][l].equals(p))
					winDiagonalUp = false;
				if( brd[rows-1-step][step][l] == null || !brd[rows-1-step][step][l].equals(p))
					winDiagonalDown = false;
			}
			
			if(winDiagonalDown || winDiagonalUp)
				return true;
		}

		return false;
	}

	private boolean innerDiagonals(Player p){
		/* 
	 		Here we want to check if a player have won in two of the inner diagonals.
	 		Example: From (0,0,0) - (4,4,4) and (0,0,4) - (4,4,0)
		 */

		boolean winDiagonalDown = true;
		boolean winDiagonalUp   = true;
		for(int i = 0;(winDiagonalDown || winDiagonalUp) && i < win;i++){
			if( brd[i][i][i] == null || !brd[i][i][i].equals(p))
				winDiagonalUp = false;
			if( brd[i][i][rows-1-i] == null || !brd[i][i][rows-1-i].equals(p))
				winDiagonalDown = false;
		}
		if(winDiagonalDown || winDiagonalUp)
			return true;

		/* 
		 	Here we want to check if a player have won in two of the inner diagonals.
		 	Example: From (4,0,0) - (0,4,4) and (4,0,4) - (0,4,0)
		 */ 
		winDiagonalDown = true;
		winDiagonalUp   = true;
		for(int i = 0;(winDiagonalDown || winDiagonalUp) && i < win;i++){
			if( brd[rows-1-i][i][i] == null || !brd[rows-1-i][i][i].equals(p))
				winDiagonalUp = false;
			if( brd[rows-1-i][i][rows-1-i] == null || !brd[rows-1-i][i][rows-1-i].equals(p))
				winDiagonalDown = false;
		}
		if(winDiagonalDown || winDiagonalUp)
			return true;

		return false;
	}

	public Point getLastMove(){
		return lastMove;
	}
}
