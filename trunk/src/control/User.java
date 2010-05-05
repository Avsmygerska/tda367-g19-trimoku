package control;
import model.*;

public abstract class User {
	
	private Player player;
	
	public User(Player p) {	player = p; }	
	public Player getPlayer() { return player; }
	protected void setPlayer(Player p) { player = p; }
	public abstract void doTurn(Board b);
	public abstract String getNotice();
	public abstract void disconnect();
}
