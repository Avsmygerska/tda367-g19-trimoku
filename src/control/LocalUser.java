package control;

import java.awt.Point;

import model.*;
import view.*;

public class LocalUser extends User {
	private ControlPanel controlPanel;
	
	public LocalUser(Player p, ControlPanel cp) {
		super(p);		
		this.controlPanel = cp;
	}
	
	public void doTurn(Board b) {		
		Point pt = controlPanel.doTurn();
		if(pt == null) {
			System.out.println("I got told to stop waitin'.");
			return;
		}
		while(!b.place(pt.x, pt.y,getPlayer())) {
			pt = controlPanel.doTurn();			
		}
		System.out.println("Puttin' a piece.");
	}
	
}
