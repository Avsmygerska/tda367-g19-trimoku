package control;

import control.interfaces.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import model.Board;
import model.Player;

public class AIUser extends User {

	private ArrayList<Point> diagonalTopDown = new ArrayList<Point>();
	private ArrayList<Point> diagonalDownTop = new ArrayList<Point>();
	private int defensiveRowColumnDiagonalLayer = 0;
	private int defensiveRowLayerDiagonalStartColumn = 0;
	private int defensiveColumnLayerDiagonalStartRow = 0;
	private int currentDiagonal;
	private int currentDiagonalLength;
	private boolean [][] defensivePossibleWinZ;
	private boolean [][] defensivePossibleWinRowLayerDiagonal;
	private boolean [][] defensivePossibleWinColumnLayerDiagonal;
	private boolean [][][] defensivePossibleWinX;
	private boolean [][][] defensivePossibleWinY;
	private boolean defensivePossibleWinInnerDiagonalXTopToDown;
	private boolean defensivePossibleWinInnerDiagonalXDownToTop;
	private boolean defensivePossibleWinInnerDiagonalYTopToDown;
	private boolean defensivePossibleWinInnerDiagonalYDownToTop;
	private boolean [][] defensiveDiagonal;

	private boolean [][] offensivePossibleWinZ;
	private boolean [][][] offensivePossibleWinX;
	private boolean [][][] offensivePossibleWinY;
	private boolean [][][] offensivePossibleWinXDiagonal;
	private boolean [][][] offensivePossibleWinYDiagonal;

	private boolean offensivePossibleWinInnerDiagonalXTopToDown;
	private boolean offensivePossibleWinInnerDiagonalXDownToTop;
	private boolean offensivePossibleWinInnerDiagonalYTopToDown;
	private boolean offensivePossibleWinInnerDiagonalYDownToTop;
	
	/* ======== Defensive variables =========== */
	private int defensiveStartXinXDirection = 0;
	private int defensiveCurrentY = 0;
	private int defensiveMaxBallsInARowX = 0;
	private int defensiveCurrentZinXDirection = 0;
	private boolean possibleToWinY = true;
	
	private int defensiveStartYinYDirection = 0;
	private int defensiveMaxBallsInARowY = 0;
	private int defensiveCurrentX = 0;
	private int defensiveCurrentZinYDirection = 0;
	private boolean possibleToWinX = true;
	
	private int defensiveMaxBallsInaRowZ = 0;
	private int defensiveStartYinZDirection = 0;
	private int defensiveStartXinZDirection = 0;
	
	private int defensiveRowColumnMaxDiagonal = 0;
	private int defensiveRowColumnDiagonalStartRow = 0;
	private int defensiveRowColumnDiagonalStartColumn = 0;
	
	private boolean possibleWinRowLayer = false;
	private int defensiveMaxRowLayerDiagonal = 0; 
	private int layerHeight;
	private int defensiveRowLayerDiagonalStartRow = 0;
	private int defensiveRowLayerDiagonalStartLayer = 0;
	private int currentHeightDownTop = 0;
	private int nextHeightDownTop= 0;
	private int currentHeightTopDown = 0;
	private int nextHeightTopDown= 0;
	
	private boolean possibleWinColumnLayer = false;
	private int defensiveColumnLayerDiagonalStartColumn = 0;
	private int defensiveColumnLayerDiagonalStartLayer = 0;
	private int defensiveMaxColumnLayerDiagonal = 0;
	/* ==================================================== */
	
	/* ========= Offensive variables =====================*/
	private int offensiveStartXinXDirection = 0;
	private int offensiveCurrentY = 0;
	private int offensiveMaxBallsInARowX = 0;
	private int offensiveCurrentZinXDirection = 0;
	
	private int offensiveStartYinYDirection = 0;
	private int offensiveMaxBallsInARowY = 0;
	private int offensiveCurrentX = 0;
	private int offensiveCurrentZinYDirection = 0;
	
	private int offensiveMaxBallsInaRowZ = 0;
	private int offensiveStartYinZDirection = 0;
	private int offensiveStartXinZDirection = 0;
	/* ======================================================*/

	private boolean initialized = false;

	private boolean hasPlaced;
	private int count = 0;
	private Point lastMove;

	public AIUser(Player p) {
		super(p);
	}

	public void doTurn(Board b) {
		hasPlaced = false;
		if(!initialized)
			init(b);

		calculateDefense(b);
		calculateOffense(b);

		System.out.println("Defense: X " + defensiveMaxBallsInARowX + ", Y "+ defensiveMaxBallsInARowY + ", Z " + defensiveMaxBallsInaRowZ
				+", Diagonal " + currentDiagonalLength);
		System.out.println("Offensive: X " + offensiveMaxBallsInARowX + ", Y "+ offensiveMaxBallsInARowY + ", Z " + offensiveMaxBallsInaRowZ);
		if((offensiveMaxBallsInARowX != (b.getRows()-1) && offensiveMaxBallsInARowY != (b.getColumns()-1) && offensiveMaxBallsInaRowZ != (b.getLayers())-1) && 
				(defensiveMaxBallsInARowX > (b.getRows()-3) || defensiveMaxBallsInARowY > (b.getColumns()-3) || defensiveMaxBallsInaRowZ > (b.getRows()-2) ||
						currentDiagonalLength > (b.getColumns()-2)))
			playDefensive(b);
		else
			playOffensive(b);
		
		if(!hasPlaced)
			randomMove(b);
	}

	private void playOffensive(Board b) {
		if(!(offensiveMaxBallsInARowX == 0 && offensiveMaxBallsInARowY ==0 && offensiveMaxBallsInaRowZ < 2)){
			if(offensiveMaxBallsInARowX >= offensiveMaxBallsInARowY){
				if(offensiveMaxBallsInARowX >= offensiveMaxBallsInaRowZ){
					System.out.println("printar i x offensive " + count);
					Point xPoint = new Point();
					int xcurrenty = 0;
					for(int y = 0; y < b.getColumns(); y++){

						if(b.peek(offensiveStartXinXDirection,y,offensiveCurrentZinXDirection) == null){
							b.place(offensiveStartXinXDirection, y, getPlayer());
							hasPlaced = true;
							xcurrenty = y;
							xPoint = new Point(offensiveStartXinXDirection, y);
							break;
						}


					}

					if(hasPlaced){
						System.out.println(xPoint.x + "," + xPoint.y);
						if(diagonalDownTop.contains(xPoint)){
							defensiveDiagonal[1][offensiveCurrentZinXDirection] = false;
						}
						if(diagonalTopDown.contains(xPoint)){
							defensiveDiagonal[0][offensiveCurrentZinXDirection] = false;
						}
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
						Point zPoint = new Point(offensiveStartXinZDirection, offensiveStartYinZDirection);
						if(diagonalDownTop.contains(zPoint)){
							defensiveDiagonal[1][offensiveCurrentZinXDirection] = false;
						}
						if(diagonalTopDown.contains(zPoint)){
							defensiveDiagonal[0][offensiveCurrentZinXDirection] = false;
						}
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
					Point yPoint = new Point();
					for(int x = 0; x < b.getRows(); x++){
						if(b.peek(x,offensiveStartYinYDirection,offensiveCurrentZinYDirection) == null){					
							b.place(x, offensiveStartYinYDirection, getPlayer());
							hasPlaced = true;
							ycurrentx = x;
							yPoint = new Point(x, offensiveStartYinYDirection);
							break;	
						}
					}
					if(hasPlaced){
						if(diagonalDownTop.contains(yPoint)){
							defensiveDiagonal[1][offensiveCurrentZinXDirection] = false;
						}
						if(diagonalTopDown.contains(yPoint)){
							defensiveDiagonal[0][offensiveCurrentZinXDirection] = false;
						}
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
						Point zPoint = new Point(offensiveStartXinZDirection, offensiveStartYinZDirection);
						if(diagonalDownTop.contains(zPoint)){
							defensiveDiagonal[1][offensiveCurrentZinXDirection] = false;
						}
						if(diagonalTopDown.contains(zPoint)){
							defensiveDiagonal[0][offensiveCurrentZinXDirection] = false;
						}
						System.out.println(offensivePossibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection]);
						defensivePossibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection] = false;
						return;
					}

				}
			}
		}
	}

	private void playDefensive(Board b) {
		System.out.println(currentDiagonal);
		if(defensiveMaxBallsInARowX >= defensiveMaxBallsInARowY){
			if(defensiveMaxBallsInARowX >= defensiveMaxBallsInaRowZ){
				if(defensiveMaxBallsInARowX > currentDiagonalLength){	
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
				// Behöver ändras!
				else{
					switch(currentDiagonal){
					case 0:  defensiveRowColumnDiagonal(b,defensiveRowColumnDiagonalStartRow, defensiveRowColumnDiagonalStartColumn); break;
					case 1:  defensiveRowLayerDiagonal(b, defensiveRowLayerDiagonalStartRow, defensiveRowLayerDiagonalStartLayer); break;
					case 2:  defensiveColumnLayerDiagonal(b, defensiveColumnLayerDiagonalStartColumn, defensiveColumnLayerDiagonalStartLayer); break;
					}
					return;

				}


			}
			else{
				if(defensiveMaxBallsInaRowZ > currentDiagonalLength)	
					if(b.place(defensiveStartXinZDirection, defensiveStartYinZDirection, getPlayer())){
						//System.out.println(defensivePossibleWinZ[defensiveStartYinYDirection][defensiveCurrentZinYDirection]);
						defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection] = false;
						return;
					}
					else{
						for (int i = 0; i < b.getColumns(); i++) {
							//System.out.println("Diagonal Z:" + (defensiveMaxDiagonalStartX+i) +", " + (defensiveMaxDiagonalStartY+i));
							if(b.peek(defensiveRowColumnDiagonalStartRow+i, defensiveRowColumnDiagonalStartColumn+i, 0)==null){
								b.place(defensiveRowColumnDiagonalStartRow+i, defensiveRowColumnDiagonalStartColumn, getPlayer());
								return;
							}
						}

					}
				else{
					switch(currentDiagonal){
					case 0:  defensiveRowColumnDiagonal(b,defensiveRowColumnDiagonalStartRow, defensiveRowColumnDiagonalStartColumn);break;
					case 1:  defensiveRowLayerDiagonal(b, defensiveRowLayerDiagonalStartRow, defensiveRowLayerDiagonalStartLayer);break;
					case 2:  defensiveColumnLayerDiagonal(b, defensiveColumnLayerDiagonalStartColumn, defensiveColumnLayerDiagonalStartLayer); break;
					}
					return;
				}
			}	
		}
		else{

			if(defensiveMaxBallsInARowY >= defensiveMaxBallsInaRowZ){
				if(defensiveMaxBallsInARowY > currentDiagonalLength){
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
					switch(currentDiagonal){
					case 0:  defensiveRowColumnDiagonal(b,defensiveRowColumnDiagonalStartRow, defensiveRowColumnDiagonalStartColumn);break;
					case 1:  defensiveRowLayerDiagonal(b, defensiveRowLayerDiagonalStartRow, defensiveRowLayerDiagonalStartLayer);break;
					case 2:  defensiveColumnLayerDiagonal(b, defensiveColumnLayerDiagonalStartColumn, defensiveColumnLayerDiagonalStartLayer); break;
					}
					return;

				}

			}
			else{
				if(defensiveMaxBallsInaRowZ >= currentDiagonalLength){
					if(b.place(defensiveStartXinZDirection, defensiveStartYinZDirection, getPlayer())){
						System.out.println(defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection]);
						defensivePossibleWinZ[defensiveStartXinZDirection][defensiveStartYinZDirection] = false;
						return;
					}
				}
				else{
					switch(currentDiagonal){
					case 0:  defensiveRowColumnDiagonal(b,defensiveRowColumnDiagonalStartRow, defensiveRowColumnDiagonalStartColumn);break;
					case 1:  defensiveRowLayerDiagonal(b, defensiveRowLayerDiagonalStartRow, defensiveRowLayerDiagonalStartLayer);break;
					case 2:  defensiveColumnLayerDiagonal(b, defensiveColumnLayerDiagonalStartColumn, defensiveColumnLayerDiagonalStartLayer); break;
					}
					return;
				}
			}
		}
	}

	private void calculateOffense(Board b) {
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

		
		
		for(int x = 0; x < b.getRows(); x++){
			for(int z = 0; z < b.getLayers(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int y = 0; y < b.getColumns(); y++){

					if(b.peek(x,y,z) != null && b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
						offensiveCurrentY = y;
						//System.out.println("< Turn: " + count +"offensive x > "+ offensiveMaxBallsInARowX +":" + allYourBaseAreBelongToUs+ " cords: " +x+", "+ offensiveCurrentY+", "+ z );
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
	}

	private void calculateDefense(Board b) {
		/* =========================================================================== //
		 						Defensive calculations
		// =========================================================================== */

		System.out.println(defensiveDiagonal[0][0] + ", " + defensiveDiagonal[1][0]);


		/*for(int i = 0; i < b.getRows(); i++){
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
		}*/

		// Calculating how many balls the opponent have in the X-dimension



		layerHeight = b.getLayers() -1;
		
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

		// Calculating how many balls the opponent have in the Y-dimension

		
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

		// Calculating how many balls the opponent have in the Z-dimension



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

		/*								 _______________
		 	diagonal vanlig 5-i-rad.  	|O				|
		 								|	O			|
		 								|		O		|
		 								|			O	|
		 								|______________O|
		 */
		currentDiagonal = 0;

		for(int layer = 0; layer <b.getLayers(); layer++){
			int y = b.getRows()-1;
			int maxTopDown = 0;
			int maxDownTop = 0;
			for(int row= 0, column = 0; row < b.getRows(); row++, column++){

				if(b.peek(row, column, layer)!= null && !b.peek(row, column, layer).equals(getPlayer())){
					maxTopDown++;
				}
				if(b.peek(row, y-column, layer)!= null && !b.peek(row, y-column, layer).equals(getPlayer())){
					maxDownTop++;
				}
			}
			if(defensiveRowColumnMaxDiagonal < maxTopDown && defensiveDiagonal[0][layer]){
				defensiveRowColumnMaxDiagonal = maxTopDown;
				defensiveRowColumnDiagonalStartRow = 0;
				defensiveRowColumnDiagonalStartColumn = 0;
				defensiveRowColumnDiagonalLayer = layer;
				currentDiagonalLength = defensiveRowColumnMaxDiagonal;
			}
			if(defensiveRowColumnMaxDiagonal < maxDownTop && defensiveDiagonal[1][layer]){
				defensiveRowColumnMaxDiagonal = maxDownTop;
				defensiveRowColumnDiagonalStartRow = 4;
				defensiveRowColumnDiagonalStartColumn = 0;
				defensiveRowColumnDiagonalLayer = layer;
				currentDiagonalLength = defensiveRowColumnMaxDiagonal;
			}
		}
		//System.out.println("diagonal: "+defensiveRowColumnMaxDiagonal + ","+ defensiveRowColumnDiagonalLayer);



		/*		 (4,0,4)______________(4,4,4)
					  /|			 /|
					 /______________/ |				
	 	  			|O |			| |
	 				|  |O			| |
	 				|  |____O_______| | (4,4,0)
	 				| /			O	| / 
	 				|/_____________O|/
	 			 (0,0,0)		   (0,4,0)										*/


		for(int column = 0; column < b.getColumns(); column++){	
			int diagonalDownTop = 0;
			int diagonalTopDown = 0;
			for (int row = 0, layer = 0; row < b.getRows() && layer <b.getLayers(); row++, layer++) {

				if(b.peek(row, column, layer)!= null && !b.peek(row, column, layer).equals(getPlayer())){
					diagonalDownTop++;
					if(diagonalDownTop==b.getRows()-1){
						for(int i = 0; i < b.getRows(); i++){
							if(b.peek(i, column, i)==null){
								currentHeightDownTop = countHeight(b, i, column);
								if(currentHeightDownTop == i){
									possibleWinRowLayer = true;
								}
							}
						}
					}
				}
				if(b.peek(row, column, layerHeight-layer)!= null && !b.peek(row, column, layerHeight-layer).equals(getPlayer())){
					diagonalTopDown++;
					if(diagonalTopDown==b.getRows()-1){
						for(int i = 0; i < b.getRows(); i++){
							if(b.peek(i, column, b.getLayers()-i)==null){
								currentHeightTopDown = countHeight(b, i, column);
								if(currentHeightTopDown == i){
									possibleWinRowLayer = true;
								}
							}
						}
					}
				}
			}
			if(column == 0){
				//System.out.println("TopDown:" + diagonalTopDown);
			}
			if(diagonalTopDown > defensiveMaxRowLayerDiagonal && possibleWinRowLayer){
				defensiveMaxRowLayerDiagonal = diagonalTopDown;
				defensiveRowLayerDiagonalStartRow = 0;
				defensiveRowLayerDiagonalStartLayer = 4;
				defensiveColumnLayerDiagonalStartRow = column;

			}
			if(diagonalDownTop > defensiveMaxRowLayerDiagonal && possibleWinRowLayer){
				defensiveMaxRowLayerDiagonal = diagonalDownTop;
				defensiveRowLayerDiagonalStartRow = 0;
				defensiveRowLayerDiagonalStartLayer = 0;
				defensiveColumnLayerDiagonalStartRow = column;
			}
		}
		//System.out.println("defensiveMaxRowLayerDiagonal: " + defensiveMaxRowLayerDiagonal + ", Current Column:" + defensiveRowLayerDiagonalStartColumn);
		//System.out.println("maxRowLayer: " +defensiveMaxRowLayerDiagonal + ", MaxRowColumn: " + defensiveRowColumnMaxDiagonal);
		if(defensiveMaxRowLayerDiagonal > defensiveRowColumnMaxDiagonal){
			currentDiagonal = 1;
			currentDiagonalLength = defensiveMaxRowLayerDiagonal;
		}


		/*   (0,0,4)______________(4,0,4)
				  /|			 /|
				 /______________/ |				
				|O |			| |
				|  |O			| |
				|  |____O_______| | (4,0,0)
				| /			O	| / 
				|/_____________O|/
			 (0,4,0)		   (4,4,0)										*/

 
		layerHeight = b.getLayers() -1;
		currentHeightDownTop = 0;
		nextHeightDownTop= 0;
		currentHeightTopDown = 0;
		nextHeightTopDown= 0;
		for(int row = 0; row < b.getRows(); row++){	
			int diagonalDownTop = 0;
			int diagonalTopDown = 0;
			for (int column = 0, layer = 0; column < b.getColumns() && layer <b.getLayers(); column++, layer++) {

				if(b.peek(row, column, layer)!= null && !b.peek(row, column, layer).equals(getPlayer())){
					diagonalDownTop++;
					if(diagonalDownTop==b.getRows()-1){
						for(int i = 0; i < b.getColumns(); i++){
							if(b.peek(row, i, i)==null){
								currentHeightDownTop = countHeight(b, row, i);
								if(currentHeightDownTop == i){
									possibleWinRowLayer = true;
								}
							}
						}
					}
				}
				if(b.peek(row, column, layerHeight-layer)!= null && !b.peek(row, column, layerHeight-layer).equals(getPlayer())){
					diagonalTopDown++;
					if(diagonalTopDown==b.getRows()-1){
						for(int i = 0; i < b.getRows(); i++){
							if(b.peek(i, column, b.getLayers()-i)==null){
								currentHeightTopDown = countHeight(b, i, column);
								if(currentHeightTopDown == i){
									possibleWinRowLayer = true;
								}
							}
						}
					}
				}
			}
			if(row == 0){
				System.out.println("TopDown:" + diagonalTopDown + ", DownTop:" + diagonalDownTop);
			}
			if(diagonalTopDown > defensiveMaxColumnLayerDiagonal && possibleWinRowLayer){
				defensiveMaxColumnLayerDiagonal = diagonalTopDown;
				defensiveColumnLayerDiagonalStartRow = 0;
				defensiveColumnLayerDiagonalStartLayer = 4;
				defensiveColumnLayerDiagonalStartRow = row;

			}
			if(diagonalDownTop > defensiveMaxColumnLayerDiagonal && possibleWinRowLayer){
				defensiveMaxColumnLayerDiagonal = diagonalDownTop;
				defensiveColumnLayerDiagonalStartRow = 0;
				defensiveColumnLayerDiagonalStartLayer = 0;
				defensiveColumnLayerDiagonalStartRow = row;
			}
		}
		System.out.println("defensiveMaxColumnLayerDiagonal: " + defensiveMaxColumnLayerDiagonal + ", Current Column:" + defensiveColumnLayerDiagonalStartRow);
		//System.out.println("maxColumnLayer: " +defensiveMaxColumnLayerDiagonal + ", MaxRowColumn: " + defensiveRowColumnMaxDiagonal);
		if(defensiveMaxColumnLayerDiagonal > Math.max(defensiveMaxRowLayerDiagonal , defensiveRowColumnMaxDiagonal)){
			currentDiagonal = 2;
			currentDiagonalLength = defensiveMaxColumnLayerDiagonal;
		}

		int defensiveInnerDiagonals = 0;
	}

	private void init(Board b) {
		defensivePossibleWinInnerDiagonalXTopToDown = true;
		defensivePossibleWinInnerDiagonalXDownToTop = true;
		defensivePossibleWinInnerDiagonalYTopToDown = true;
		defensivePossibleWinInnerDiagonalYDownToTop = true;

		offensivePossibleWinInnerDiagonalXTopToDown = true;
		offensivePossibleWinInnerDiagonalXDownToTop = true;
		offensivePossibleWinInnerDiagonalYTopToDown = true;
		offensivePossibleWinInnerDiagonalYDownToTop = true;

		defensiveDiagonal = new boolean [2][b.getColumns()];
		defensivePossibleWinZ = new boolean [b.getRows()][b.getColumns()];
		defensivePossibleWinX = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
		defensivePossibleWinY = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
		defensivePossibleWinRowLayerDiagonal = new boolean [2][b.getColumns()];
		defensivePossibleWinColumnLayerDiagonal = new boolean [2][b.getColumns()];

		offensivePossibleWinZ = new boolean [b.getRows()][b.getColumns()];
		offensivePossibleWinX = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
		offensivePossibleWinY = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
		offensivePossibleWinXDiagonal = new boolean [b.getRows()][b.getColumns()][b.getLayers()];
		offensivePossibleWinYDiagonal = new boolean [b.getRows()][b.getColumns()][b.getLayers()];

		for(int i = 0; i<b.getRows(); i++){
			diagonalTopDown.add(new Point(i,i));
			diagonalDownTop.add(new Point(b.getRows()-1-i, i));
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

		for(int i = 0; i<2; i++)
			for(int j = 0; j<b.getColumns(); j++)
				defensiveDiagonal[i][j] = true;
		
	}

	private void randomMove(Board b) {
		while(true){
			System.out.println("AI goes Random!");
			java.util.Random r = new java.util.Random();

			int x = r.nextInt(b.getRows());
			int y = r.nextInt(b.getColumns());
			if(b.tryPlace(x, y)) {
				b.place(x, y, getPlayer());
				Point rPoint = new Point(x, y);
				if(diagonalDownTop.contains(rPoint)){
					defensiveDiagonal[1][countHeight(b, x, y)] = false;
				}
				if(diagonalTopDown.contains(rPoint)){
					defensiveDiagonal[0][countHeight(b, x, y)] = false;
				}
				//defensivePossibleWinX[x][y][countHeight(b, x, y)] = false;
				return;				
			}
		}
	}

	public int countHeight(Board board, int row, int column){
		return board.getPin(row, column).size();
	}

	public void defensiveRowColumnDiagonal(Board board, int startX, int startY){
		if(startX == 0){
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: rowColumn" + (startX+i) +", " + (startY+i));
				if(board.peek(startX+i, startY+i, defensiveRowColumnDiagonalLayer)==null){
					if(board.place(startX+i, startY+i, getPlayer())){
						defensiveDiagonal[0][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}
		else{
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: rowColumn" + (startX-i) +", " + (startY+i));
				if(board.peek(startX-i, startY+i, defensiveRowColumnDiagonalLayer)==null){
					if(board.place(startX-i, startY+i, getPlayer())){
						defensiveDiagonal[1][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}
	}


	public void defensiveRowLayerDiagonal(Board board, int startX, int startZ){
		if(startZ == 0){
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: rowLayer1 " + (startX+i) +","+defensiveRowLayerDiagonalStartColumn +"," + (startZ+i));
				if(board.peek(startX+i, defensiveRowLayerDiagonalStartColumn , startZ+i)==null){
					if(board.place(startX+i, defensiveRowLayerDiagonalStartColumn, getPlayer())){
						//defensiveDiagonal[0][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}
		else{
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: rowLayer2 " + (startX-i) +", " + (startZ+i));
				if(board.peek(startX+i, defensiveRowLayerDiagonalStartColumn , startZ-i)==null){
					if(board.place(startX+i, defensiveRowLayerDiagonalStartColumn, getPlayer())){
						//defensiveDiagonal[1][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}
	}

	public void defensiveColumnLayerDiagonal(Board board, int startY, int startZ){
		System.out.println("ASDSADSADASDASDSADASDSADSAASDSADASDSA");

		if(startZ == 0){
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: columnLayer1 " + defensiveColumnLayerDiagonalStartRow + "," +(startY+i) +","+ (startZ+i));
				if(board.peek(defensiveColumnLayerDiagonalStartRow, startY+i , startZ+i)==null){
					if(board.place(defensiveColumnLayerDiagonalStartRow, startY+i, getPlayer())){
						//defensiveDiagonal[0][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}
		else{
			for (int i = 0; i < board.getColumns(); i++) {
				System.out.println("Diagonal: rowLayer2 " + (startY-i) +", " + (startZ+i));
				if(board.peek(defensiveColumnLayerDiagonalStartRow,startY+i, startZ-i)==null){
					if(board.place(defensiveColumnLayerDiagonalStartRow, startY+i, getPlayer())){
						//defensiveDiagonal[1][defensiveRowColumnDiagonalLayer] = false;
						return;
					}
				}
			}
		}


	}
	public String getNotice() {
		return "Please wait. The AI is working.";
	}

	public int getWinLength(){
		int winLength = 5;
		return winLength;
	}

	public void disconnect() {}

}
