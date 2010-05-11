package control;

import java.awt.Point;
import control.interfaces.*;
import model.*;
import view.*;

public class LocalUser extends User {
	private ControlPanel controlPanel;
	private boolean disconnect;
	
	public LocalUser(Player p, ControlPanel cp) {
		super(p);		
		this.controlPanel = cp;
		disconnect = false;
	}
	
	public void doTurn(Board b) {		
		Point pt = controlPanel.doTurn();
		while(!disconnect && !b.place(pt.x, pt.y,getPlayer()))
			pt = controlPanel.doTurn();		
	}
	
	public String getNotice() {
		return "It is your turn " + getPlayer().getName();
	}
	
	public void disconnect() {
		disconnect = true;		
		controlPanel.forceMove();
	}
	
}
