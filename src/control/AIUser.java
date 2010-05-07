package control;

import java.util.Arrays;

import model.Board;
import model.Player;

public class AIUser extends User {

	private boolean [][] possibleWinZ;
	private boolean [][][] possibleWinX;
	private boolean [][][] possibleWinY;
	private boolean hasPlaced;
	private int count = 0;
	private Player AI;
	
	public AIUser(Player p) {
		super(p);
		
	}

	public void doTurn(Board b) {
		AI = getPlayer();
		hasPlaced = false;
		if(count == 0){
			possibleWinZ = new boolean [b.getX()][b.getY()];
			possibleWinX = new boolean [b.getX()][b.getY()][b.getZ()];
			possibleWinY = new boolean [b.getX()][b.getY()][b.getZ()];
			for(int i = 0; i<b.getX(); i++){
				for(int j = 0; j<b.getY(); j++){
					possibleWinZ[i][j] = true;
						for(int k = 0; k < b.getZ(); k++){
							possibleWinX[i][j][k] = true;
							possibleWinY[i][j][k] = true;
						}
				}
			}
		}
		count++;
		
		/* =========================================================================== //
		 						Defensive calculations
		// =========================================================================== */
		
		int defensiveStartXinXDirection = 0;
		int defensiveCurrentY = 0;
		int defensiveMaxBallsInARowX = 0;
		int defensiveCurrentZinXDirection = 0;
		for(int x = 0; x < b.getX(); x++){
			for(int z = 0; z < b.getZ(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int y = 0; y < b.getY(); y++){
					defensiveCurrentY = y;
					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
					}
				}
				if(possibleWinX[x][defensiveCurrentY][z] && defensiveMaxBallsInARowX < allYourBaseAreBelongToUs){
					defensiveMaxBallsInARowX = allYourBaseAreBelongToUs;
					defensiveStartXinXDirection = x;
					defensiveCurrentZinXDirection = z;
				}
				System.out.println("< Turn: " + count +" x > "+ defensiveMaxBallsInARowX +":" + allYourBaseAreBelongToUs+ " cords: " +x+", "+ z );
			}
		}
		int defensiveStartYinYDirection = 0;
		int defensiveMaxBallsInARowY = 0;
		int defensiveCurrentX = 0;
		int defensiveCurrentZinYDirection = 0;
		for(int y = 0; y < b.getY(); y++){
			for(int z = 0; z < b.getZ(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int x = 0; x < b.getX(); x++){
					defensiveCurrentX = x;
					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
					}
				}
				
				if(possibleWinY[defensiveCurrentX][y][z] && defensiveMaxBallsInARowY < allYourBaseAreBelongToUs){
					defensiveMaxBallsInARowY = allYourBaseAreBelongToUs;
					defensiveStartYinYDirection = y;
					defensiveCurrentZinYDirection = z;
				}
				System.out.println("< Turn: " + count +"y > "+ defensiveMaxBallsInARowY+":"+allYourBaseAreBelongToUs + " cords: " +y+", "+ z );
			}
			
		}
		
		int defensiveMaxBallsInaRowZ = 0;
		int defensiveStartYinZDirection = 0;
		int defensiveStartXinZDirection = 0;
		
		for(int x = 0; x<b.getX(); x++){
			int maxZ;
			for(int y = 0; y<b.getY(); y++){
				maxZ = 0;
				for(int z = 0; z<b.getZ(); z++){
					if(b.peek(x, y, z)!= null && !b.peek(x, y, z).equals(getPlayer())){
						maxZ++;
					}
					
				}
				if(possibleWinZ[x][y] && maxZ > defensiveMaxBallsInaRowZ){
					defensiveMaxBallsInaRowZ = maxZ;
					defensiveStartYinZDirection = y;
					defensiveStartXinZDirection = x;
				}
			}
		}
		
		/* =========================================================================== //
								Offensive calculations
		// =========================================================================== */
		
		int offsensiveStartXinXDirection = 0;
		int offensiveCurrentY = 0;
		int offensiveMaxBallsInARowX = 0;
		int offensiveCurrentZinXDirection = 0;
		for(int x = 0; x < b.getX(); x++){
			for(int z = 0; z < b.getZ(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int y = 0; y < b.getY(); y++){
					offensiveCurrentY = y;
					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
					}
				}
				if(possibleWinX[x][offensiveCurrentY][z] && offensiveMaxBallsInARowX < allYourBaseAreBelongToUs){
					offensiveMaxBallsInARowX = allYourBaseAreBelongToUs;
					offsensiveStartXinXDirection = x;
					offensiveCurrentZinXDirection = z;
				}
				System.out.println("< Turn: " + count +" x > "+ offensiveMaxBallsInARowX +":" + allYourBaseAreBelongToUs+ " cords: " +x+", "+ z );
			}
		}
		int offensiveStartYinYDirection = 0;
		int offensiveMaxBallsInARowY = 0;
		int offensiveCurrentX = 0;
		int offensiveCurrentZinYDirection = 0;
		for(int y = 0; y < b.getY(); y++){
			for(int z = 0; z < b.getZ(); z++){
				int allYourBaseAreBelongToUs = 0;
				for(int x = 0; x < b.getX(); x++){
					offensiveCurrentX = x;
					if(b.peek(x,y,z) != null && !b.peek(x,y,z).equals(getPlayer())){
						allYourBaseAreBelongToUs++;
					}
				}
				
				if(possibleWinY[offensiveCurrentX][y][z] && offensiveMaxBallsInARowY < allYourBaseAreBelongToUs){
					offensiveMaxBallsInARowY = allYourBaseAreBelongToUs;
					offensiveStartYinYDirection = y;
					offensiveCurrentZinYDirection = z;
				}
				System.out.println("< Turn: " + count +"y > "+ offensiveMaxBallsInARowY+":"+allYourBaseAreBelongToUs + " cords: " +y+", "+ z );
			}
			
		}
		
		int offensiveMaxBallsInaRowZ = 0;
		int offensiveStartYinZDirection = 0;
		int offensiveStartXinZDirection = 0;
		
		for(int x = 0; x<b.getX(); x++){
			int maxZ;
			for(int y = 0; y<b.getY(); y++){
				maxZ = 0;
				for(int z = 0; z<b.getZ(); z++){
					if(b.peek(x, y, z)!= null && !b.peek(x, y, z).equals(getPlayer())){
						maxZ++;
					}
					
				}
				if(possibleWinZ[x][y] && maxZ > offensiveMaxBallsInaRowZ){
					offensiveMaxBallsInaRowZ = maxZ;
					offensiveStartYinZDirection = y;
					offensiveStartXinZDirection = x;
				}
			}
		}
		
		/* =========================================================================== //
					Where the AI should put the balls, defensive calculations
		// =========================================================================== */
		
		if(defensiveMaxBallsInARowX > (b.getX()-3) || offensiveMaxBallsInARowY > (b.getY()-3) || offensiveMaxBallsInaRowZ > (b.getX()-2)){	
			if(defensiveMaxBallsInARowX >= offensiveMaxBallsInARowY){
				if(defensiveMaxBallsInARowX >= offensiveMaxBallsInaRowZ){
					System.out.println("printar i x");
					for(int y = 0; y < b.getY(); y++){
						if(b.peek(defensiveStartXinXDirection,y,defensiveCurrentZinXDirection) == null){
							b.place(defensiveStartXinXDirection, y, getPlayer());
							hasPlaced = true;
							break;
						}
						
					}
					
					if(hasPlaced){
						for(int y = 0; y<b.getY();y++){
							possibleWinX[defensiveStartXinXDirection][y][defensiveCurrentZinXDirection]= false;
							possibleWinY[y][defensiveStartXinXDirection][defensiveCurrentZinXDirection] = false;
						}
						possibleWinZ[defensiveStartXinXDirection][defensiveCurrentZinXDirection] = false;
						return;
					}
				}
				else{
					if(b.place(offensiveStartXinZDirection, offensiveStartYinZDirection, getPlayer())){
						System.out.println(possibleWinZ[offensiveStartYinYDirection][offensiveCurrentZinYDirection]);
						possibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection] = false;
						return;
					}
				}
			}
			else{
				if(offensiveMaxBallsInARowY >= offensiveMaxBallsInaRowZ){
					System.out.println("printar i y, " + offensiveMaxBallsInARowY);
					for(int x = 0; x < b.getX(); x++){
						if(b.peek(x,offensiveStartYinYDirection,offensiveCurrentZinYDirection) == null){					
							b.place(x, offensiveStartYinYDirection, getPlayer());
							hasPlaced = true;
							break;	
						}
					}
					if(hasPlaced){
						for (int x = 0; x < b.getX(); x++) {
							possibleWinY[x][offensiveStartYinYDirection][offensiveCurrentZinYDirection]= false;
							possibleWinX[offensiveStartYinYDirection][x][offensiveCurrentZinYDirection] = false;
						}
						possibleWinZ[offensiveStartYinYDirection][offensiveCurrentZinYDirection] = false;
						return;
					}
					
						
				}
				else{
					if(b.place(offensiveStartXinZDirection, offensiveStartYinZDirection, getPlayer())){
						System.out.println(possibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection]);
						possibleWinZ[offensiveStartXinZDirection][offensiveStartYinZDirection] = false;
						return;
					}
					
				}
			}
		}
		
		/* =========================================================================== //
					Where the AI should put the balls, offensive calculations
		// =========================================================================== */
		
		while(true){
			System.out.println("AI goes Random!");
			int x = (int) (b.getX() * Math.random());
			int y = (int) (b.getY() * Math.random());
			if(b.tryPlace(x, y)) {
				b.place(x, y, getPlayer());
				return;				
			}
		}
		
	}

	public String getNotice() {
		return "Please wait. The AI is working.";
	}
	
	public void disconnect() {}

}
