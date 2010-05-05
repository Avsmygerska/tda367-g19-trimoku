package control;

import model.Board;
import model.Player;

public class AIUser extends User {

	public AIUser(Player p) {
		super(p);
	}

	public void doTurn(Board b) {
		int startX = 0;
		int maxBallsInARowX = 0;
		for(int x = 0; x < b.getX(); x++){
			
			int allYourBaseAreBelongToUs = 0;
						
			for(int y = 0; y < b.getY(); y++){
				if(b.peek(x,y,0) != null && !b.peek(x,y,0).equals(getPlayer())){
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
		for(int y = 0; y < b.getX(); y++){
			
			int allYourBaseAreBelongToUs = 0;
						
			for(int x = 0; x < b.getY(); x++){
				if(b.peek(x,y,0) != null && !b.peek(x,y,0).equals(getPlayer())){
					allYourBaseAreBelongToUs++;
				}
			}
			if(maxBallsInARowY < allYourBaseAreBelongToUs){
				maxBallsInARowY = allYourBaseAreBelongToUs;
				startY = y;
			}
		}
		
		if(maxBallsInARowX >= maxBallsInARowY){
			for(int y = 0; y < b.getY(); y++){
				if(b.peek(startX,y,0) == null){
					b.place(startX, y, getPlayer());
					return;
				}
					
			}
		}
		else{
			for(int x = 0; x < b.getX(); x++){
				if(b.peek(x,startY,0) == null){					
					b.place(x, startY, getPlayer());
					return;
				}
					
			}
		}
		
		while(true){
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
