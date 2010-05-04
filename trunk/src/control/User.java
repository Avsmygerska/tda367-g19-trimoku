package control;

import model.*;

public abstract class User {
	
	private Player player;	
	public Player getPlayer() { return player; }
	protected void setPlayer(Player p) { player = p; }
	
	public abstract void doTurn();

}
