package control;

import java.awt.Point;
import java.util.Arrays;

import model.Board;
import model.Player;

public class AIUser extends User {

	private boolean [][] defensivePossibleWinZ;
	private boolean [][][] defensivePossibleWinX;
	private boolean [][][] defensivePossibleWinY;
	private boolean [][] offensivePossibleWinZ;
	private boolean [][][] offensivePossibleWinX;
	private boolean [][][] offensivePossibleWinY;
	private boolean hasPlaced;
	private int count = 0;
	private Point lastMove;

	public AIUser(Player p) {
		super(p);

	}

	public void doTurn(Board b) {
		hasPlaced = false;
		if(count == 0){
			defensivePossibleWinZ = new boolean [b.getRows()][b.getColumns()];
			defensivePossibleWinX = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
			defensivePossibleWinY = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
			offensivePossibleWinZ = new boolean [b.getRows()][b.getColumns()];
			offensivePossibleWinX = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
			offensivePossibleWinY = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
			for(int i = 0; i<b.getRows(); i++){
				for(int j = 0; j<b.getColumns(); j++){
					defensivePossibleWinZ[i][j] = true;
					offensivePossibleWinZ[i][j] = true;
					for(int k = 0; k < b.getLayers(); k++){
						defensivePossibleWinX[i][j][k] = true;
						defensivePossibleWinY[i][j][k] = true;
						offensivePossibleWinX[i][j][k] = true;
						offensivePossibleWinY[i][j][k] = true;

					}
				}
			}
		}
		count++;

		/* =========================================================================== //
		 						Defensive calculations
		// =========================================================================== */

		for(int i = 0; i < b.getRows(); i++){
			for(int j = 0; j < b.getColumns()-1; j++){
				System.out.print(defensivePossibleWinX[i][j][0]+"	|	");
			}
			System.out.println(defensivePossibleWinX[i][b.getColumns()-1][0]);
		}
		System.out.println("==================================");
		for(int i = 0; i < b.getRows(); i++){
			for(int j = 0; j < b.getColumns()-1; j++){
				System.out.print(defensivePossibleWinY[i][j][0]+"	|	");
			}
			System.out.println(defensivePossibleWinY[i][b.getColumns()-1][0]);
		}


		int defensiveStartXinXDirection = 0;
		int defensiveCurrentY = 0;
		int defensiveMaxBallsInARowX = 0;
		int defensiveCurrentZinXDirection = 0;
		boolean possibleToWinY = true;
		for(int x = 0; x < b.getRows(); x++){
			for(int z = 0; z < b.getLayers(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int y = 0; y < b.getColumns(); y++){
					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
						defensiveCurrentY = y;
					}
					else{
						if(x!=b.getRows()-1 && possibleToWinY && allYourBaseAreBelongToUs > defensiveMaxBallsInARowX){
							if(countHeight(b,x,y)!=z && b.peek(x, y, z) == null && z!=0){
								possibleToWinY = false;
							}
						}
					}
				}
				if(defensivePossibleWinX[x][defensiveCurrentY][z] && defensiveMaxBallsInARowX < allYourBaseAreBelongToUs && possibleToWinY){
					defensiveMaxBallsInARowX = allYourBaseAreBelongToUs;
					defensiveStartXinXDirection = x;
					defensiveCurrentZinXDirection = z;
				}
				//System.out.println("< Turn: " + count +" x > "+ defensiveMaxBallsInARowX +":" + allYourBaseAreBelongToUs+ " cords: " +x+", "+ z );
			}
		}
		int defensiveStartYinYDirection = 0;
		int defensiveMaxBallsInARowY = 0;
		int defensiveCurrentX = 0;
		int defensiveCurrentZinYDirection = 0;
		boolean possibleToWinX = true;
		for(int y = 0; y < b.getColumns(); y++){
			for(int z = 0; z < b.getLayers(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int x = 0; x < b.getRows(); x++){

					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
						defensiveCurrentX = x;
					}
					else{
						if(x!=b.getRows()-1 && possibleToWinX && allYourBaseAreBelongToUs > defensiveMaxBallsInARowY){
							if(countHeight(b,x,y)!=z && b.peek(x, y, z) == null && z!=0){
								possibleToWinX = false;
							}
						}
					}
				}

				if(defensivePossibleWinY[defensiveCurrentX][y][z] && defensiveMaxBallsInARowY < allYourBaseAreBelongToUs && possibleToWinX){
					defensiveMaxBallsInARowY = allYourBaseAreBelongToUs;
					defensiveStartYinYDirection = y;
					defensiveCurrentZinYDirection = z;
				}
				//System.out.println("< Turn: " + count +"y > "+ defensiveMaxBallsInARowY+":"+allYourBaseAreBelongToUs + " cords: " +y+", "+ z );
			}

		}

		int defensiveMaxBallsInaRowZ = 0;
		int defensiveStartYinZDirection = 0;
		int defensiveStartXinZDirection = 0;

		for(int x = 0; x<b.getRows(); x++){
			int maxZ;
			for(int y = 0; y<b.getColumns(); y++){
				maxZ = 0;
				for(int z = 0; z<b.getLayers(); z++){
					if(b.peek(x, y, z)!= null && !b.peek(x, y, z).equals(getPlayer())){
						maxZ++;
					}


				}
				if(defensivePossibleWinZ[x][y] && maxZ > defensiveMaxBallsInaRowZ){
					defensiveMaxBallsInaRowZ = maxZ;
					defensiveStartYinZDirection = y;
					defensiveStartXinZDirection = x;
				}
			}
		}


		/* =========================================================================== //
								Offensive calculations
		// =========================================================================== */

		lastMove = b.getLastMove();


		for(int row = 0; row < b.getRows(); row++){
			offensivePossibleWinY[row][lastMove.y][countHeight(b,lastMove.x,lastMove.y)-1] = false;
		}
		for(int column = 0; column < b.getColumns(); column++){
			offensivePossibleWinX[lastMove.x][column][countHeight(b,lastMove.x,lastMove.y)-1] = false;;
		}
		offensivePossibleWinZ[lastMove.x][lastMove.y] = false;


		/*boolean columnWithNoBalls = false;
		boolean rowWithNoBalls    = false;
		for(int i = 0; i < b.getRows(); i++){
			boolean innerCheckRow    = true;
			boolean innerCheckColumn = true;
			for(int j = 0; j < b.getColumns()-1; j++){
				if(offensivePossibleWinX[i][j][0]==false && innerCheckRow){
					innerCheckRow = false;
				}
				if(offensivePossibleWinY[j][i][0]){
					innerCheckColumn = false;
				}
			}
			if(innerCheckRow)
				rowWithNoBalls = true;

			if(innerCheckColumn)
				columnWithNoBalls = true;
		}*/

		// Räknar vertikalt sett från PinArea
		int offensiveStartXinXDirection = 0;
		int offensiveCurrentY = 0;
		int offensiveMaxBallsInARowX = 0;
		int offensiveCurrentZinXDirection = 0;
		for(int x = 0; x < b.getRows(); x++){
			for(int z = 0; z < b.getLayers(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int y = 0; y < b.getColumns(); y++){

					if(b.peek(x,y,z) != null && b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
						offensiveCurrentY = y;
						System.out.println("< Turn: " + count +"offensive x > "+ offensiveMaxBallsInARowX +":" + allYourBaseAreBelongToUs+ " cords: " +x+", "+ offensiveCurrentY+", "+ z );
					}
				}
				if(offensivePossibleWinX[x][offensiveCurrentY][z] && offensiveMaxBallsInARowX < allYourBaseAreBelongToUs){
					offensiveMaxBallsInARowX = allYourBaseAreBelongToUs;
					offensiveStartXinXDirection = x;
					offensiveCurrentZinXDirection = z;
				}

			}
		}


		//Räknar rakt ner sett från PinArea
		int offensiveStartYinYDirection = 0;
		int offensiveMaxBallsInARowY = 0;
		int offensiveCurrentX = 0;
		int offensiveCurrentZinYDirection = 0;
		for(int y = 0; y < b.getColumns(); y++){
			for(int z = 0; z < b.getLayers(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int x = 0; x < b.getRows(); x++){
					if(b.peek(x,y,z) != null && b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
						offensiveCurrentX = x;
						//System.out.println("< Turn: " + count +"offensive y > "+ offensiveMaxBallsInARowY+":"+allYourBaseAreBelongToUs + " cords: "+ offensiveCurrentX+", "+y+", "+ z );
					}
				}

				if(offensivePossibleWinY[offensiveCurrentX][y][z] && offensiveMaxBallsInARowY < allYourBaseAreBelongToUs){
					offensiveMaxBallsInARowY = allYourBaseAreBelongToUs;
					offensiveStartYinYDirection = y;
					offensiveCurrentZinYDirection = z;
				}
				//System.out.println("< Turn: " + count +"offensive y > "+ offensiveMaxBallsInARowY+":"+allYourBaseAreBelongToUs + " cords: "+ offensiveCurrentX+", "+y+", "+ z );
			}

		}

		int offensiveMaxBallsInaRowZ = 0;
		int offensiveStartYinZDirection = 0;
		int offensiveStartXinZDirection = 0;

		for(int x = 0; x<b.getRows(); x++){
			int maxZ;
			for(int y = 0; y<b.getColumns(); y++){
				maxZ = 0;
				for(int z = 0; z<b.getLayers(); z++){
					if(b.peek(x, y, z)!= null && b.peek(x, y, z).equals(getPlayer())){
						maxZ++;
					}

				}
				if(offensivePossibleWinZ[x][y] && maxZ > offensiveMaxBallsInaRowZ){
					offensiveMaxBallsInaRowZ = maxZ;
					offensiveStartYinZDirection = y;
					offensiveStartXinZDirection = x;
				}
			}
		}


		/* =========================================================================== //
					Where the AI should put the balls, defensive calculations
		// =========================================================================== */

		if((offensiveMaxBallsInARowX != (b.getRows()-1) && offensiveMaxBallsInARowY != (b.getColumns()-1) && offensiveMaxBallsInaRowZ != (b.getLayers())-1) && 
				(defensiveMaxBallsInARowX > (b.getRows()-3) || defensiveMaxBallsInARowY > (b.getColumns()-3) || defensiveMaxBallsInaRowZ > (b.getRows()-2))){	
			if(defensiveMaxBallsInARowX >= defensiveMaxBallsInARowY){
				if(defensiveMaxBallsInARowX >= defensiveMaxBallsInaRowZ){
					System.out.println("printar i x");
					int xcurrenty = 0;
					for(int y = 0; y < b.getColumns(); y++){
						if(b.peek(defensiveStartXinXDirection,y,defensiveCurrentZinXDirection) == null){
							b.place(defensiveStartXinXDirection, y, getPlayer());
							hasPlaced = true;
							xcurrenty = y;
							break;
						}

					}

					if(hasPlaced){
						for(int y = 0; y<b.getColumns();y++){
							defensivePossibleWinX[defensiveStartXinXDirection][y][defensiveCurrentZinXDirection]= false;
							defensivePossibleWinY[y][xcurrenty][defensiveCurrentZinXDirection] = false;
						}
						defensivePossibleWinZ[defensiveStartXinXDirection][defensiveCurrentZinXDirection] = false;
						return;
					}
				}
				else{
					if(b.place(defensiveStartXinZDirection, defensiveStartYinZDirection, getPlayer())){
						System.out.println(defensivePossibleWinZ[defensiveStartYinYDirection][defensiveCurrentZinYDirection]);
						defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection] = false;
						return;
					}
				}
			}
			else{

				if(defensiveMaxBallsInARowY >= defensiveMaxBallsInaRowZ){
					System.out.println("printar i y, " + defensiveMaxBallsInARowY);
					int ycurrentx = 0;
					for(int x = 0; x < b.getRows(); x++){
						if(b.peek(x,defensiveStartYinYDirection,defensiveCurrentZinYDirection) == null){					
							b.place(x, defensiveStartYinYDirection, getPlayer());
							hasPlaced = true;
							ycurrentx = x;
							break;	
						}
					}
					if(hasPlaced){
						for (int x = 0; x < b.getRows(); x++) {
							defensivePossibleWinY[x][defensiveStartYinYDirection][defensiveCurrentZinYDirection]= false;
							defensivePossibleWinX[ycurrentx][x][defensiveCurrentZinYDirection] = false;
						}
						defensivePossibleWinZ[defensiveStartYinYDirection][defensiveCurrentZinYDirection] = false;
						return;
					}


				}
				else{
					if(b.place(defensiveStartXinZDirection, defensiveStartYinZDirection, getPlayer())){
						System.out.println(defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection]);
						defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection] = false;
						return;
					}

				}
			}
		}
		else{

			/* =========================================================================== //
					Where the AI should put the balls, offensive calculations
		// =========================================================================== */ 
			if(!(offensiveMaxBallsInARowX == 0 && offensiveMaxBallsInARowY ==0 && offensiveMaxBallsInaRowZ < 2)){
				if(offensiveMaxBallsInARowX >= offensiveMaxBallsInARowY){
					if(offensiveMaxBallsInARowX >= offensiveMaxBallsInaRowZ){
						System.out.println("printar i x offensive " + count);
						int xcurrenty = 0;
						for(int y = 0; y < b.getColumns(); y++){
							if(count != 1){
								if(b.peek(offensiveStartXinXDirection,y,offensiveCurrentZinXDirection) == null){
									b.place(offensiveStartXinXDirection, y, getPlayer());
									hasPlaced = true;
									xcurrenty = y;
									break;
								}
							}
							else{
								offensiveStartXinXDirection = (int)(Math.random()*b.getRows());
								xcurrenty = (int)(Math.random()*b.getColumns());
								System.out.println(offensiveStartXinXDirection + ", " + xcurrenty+", "+ b.getColumns());
								b.place(offensiveStartXinXDirection, xcurrenty, getPlayer());
								hasPlaced = true;
								break;
							}
						}

						if(hasPlaced){
							for(int y = 0; y<b.getColumns();y++){
								defensivePossibleWinX[offensiveStartXinXDirection][y][offensiveCurrentZinXDirection]= false;
								defensivePossibleWinY[y][xcurrenty][offensiveCurrentZinXDirection] = false;
							}
							defensivePossibleWinZ[offensiveStartXinXDirection][offensiveCurrentZinXDirection] = false;
							return;
						}
					}
					else{
						if(b.place(offensiveStartXinZDirection, offensiveStartYinZDirection, getPlayer())){
							System.out.println(offensivePossibleWinZ[offensiveStartYinYDirection][offensiveCurrentZinYDirection]);
							defensivePossibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection] = false;
							return;
						}
					}
				}
				else{
					if(offensiveMaxBallsInARowY >= offensiveMaxBallsInaRowZ){
						System.out.println("printar i y, " + defensiveMaxBallsInARowY);
						int ycurrentx = 0;
						for(int x = 0; x < b.getRows(); x++){
							if(b.peek(x,offensiveStartYinYDirection,offensiveCurrentZinYDirection) == null){					
								b.place(x, offensiveStartYinYDirection, getPlayer());
								hasPlaced = true;
								ycurrentx = x;
								break;	
							}
						}
						if(hasPlaced){
							for (int x = 0; x < b.getRows(); x++) {
								defensivePossibleWinY[x][offensiveStartYinYDirection][offensiveCurrentZinYDirection]= false;
								defensivePossibleWinX[ycurrentx][x][offensiveCurrentZinYDirection] = false;
							}
							defensivePossibleWinZ[offensiveStartYinYDirection][offensiveCurrentZinYDirection] = false;
							return;
						}


					}
					else{
						if(b.place(offensiveStartXinZDirection, offensiveStartYinZDirection, getPlayer())){
							System.out.println(offensivePossibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection]);
							defensivePossibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection] = false;
							return;
						}

					}
				}
			}
		}



		while(true){
			System.out.println("AI goes Random!");
			java.util.Random r = new java.util.Random();
			
			int x = r.nextInt(b.getRows());
			int y = r.nextInt(b.getColumns());
			if(b.tryPlace(x, y)) {
				b.place(x, y, getPlayer());
				return;				
			}
		}

	}

	public int countHeight(Board board, int row, int column){
		int calculateHeight = 0;
		while(true){
			if(board.peek(row, column, calculateHeight) == null)
				break;
			calculateHeight++;
		}
		return calculateHeight;
	}

	public String getNotice() {
		return "Please wait. The AI is working.";
	}

	public void disconnect() {}

}
