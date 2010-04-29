package model;

public class AI {
	
	Board board;
	
	public AI(Board board){
		
		this.board = board;
	}
	
	public boolean place2(Player p){
		
		while(true){
			int x = (int) (board.maxX * Math.random());
			int y = (int) (board.maxY * Math.random());
			if(board.place(x, y, p)){
				return true;				
			}
		}
	}
	
	public boolean place(Player p){
		int startX = 0;
		int maxBallsInARowX = 0;
		for(int x = 0; x < board.maxX; x++){
			
			int allYourBaseAreBelongToUs = 0;
						
			for(int y = 0; y < board.maxY; y++){
				if(board.brd[x][y][0] != null && !board.brd[x][y][0].equals(p)){
					allYourBaseAreBelongToUs++;
				}
			}
			if(maxBallsInARowX < allYourBaseAreBelongToUs){
				maxBallsInARowX = allYourBaseAreBelongToUs;
				startX = x;
			}
		}
		int startY = 0;
		int maxBallsInARowY = 0;
		for(int y = 0; y < board.maxX; y++){
			
			int allYourBaseAreBelongToUs = 0;
						
			for(int x = 0; x < board.maxY; x++){
				if(board.brd[x][y][0] != null && !board.brd[x][y][0].equals(p)){
					allYourBaseAreBelongToUs++;
				}
			}
			if(maxBallsInARowY < allYourBaseAreBelongToUs){
				maxBallsInARowY = allYourBaseAreBelongToUs;
				startY = y;
			}
		}
		
		if(maxBallsInARowX >= maxBallsInARowY){
			for(int y = 0; y < board.maxY; y++){
				if(board.brd[startX][y][0] == null){
					board.place(startX, y, p);
					return true;
				}
					
			}
		}
		else{
			for(int x = 0; x < board.maxX; x++){
				if(board.brd[x][startY][0] == null){
					board.place(x, startY, p);
					return true;
				}
					
			}
		}
		
		while(true){
			int x = (int) (board.maxX * Math.random());
			int y = (int) (board.maxY * Math.random());
			if(board.place(x, y, p)){
				return true;				
			}
		}
		
	}
			
}
