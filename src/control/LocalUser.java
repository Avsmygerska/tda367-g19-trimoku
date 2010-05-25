package control;

import java.awt.Point;
import control.interfaces.*;
import view.interfaces.*;
import model.*;

public class LocalUser extends User {
	private ControlInterface controlInterface;
	private boolean disconnect;
	
	public LocalUser(Player p, ControlInterface ci) {
		super(p);		
		controlInterface = ci;
		disconnect = false;
	}
	
	public void doTurn(Board b) {		
		Point pt = controlInterface.getSelected();
		while(!disconnect && !b.place(pt.x, pt.y,getPlayer()))
			pt = controlInterface.getSelected();		
	}
	
	public String getNotice() {
		return "It is your turn " + getPlayer().getName();
	}
	
	public void disconnect() {
		disconnect = true;		
		controlInterface.forceMove();
	}
	
}
