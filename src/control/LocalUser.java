package control;

import java.awt.Point;

import model.*;
import view.*;

public class LocalUser extends User {	
	private Board board;
	private ControlPanel controlPanel;
	
	public LocalUser(Player p, Board b, ControlPanel cp) {
		setPlayer(p);
		board = b;
		this.controlPanel = cp;
	}
	
	public void setBoard(Board board){
		this.board = board;	
	}
	
	public void doTurn() {
		Point pt = controlPanel.doTurn(getPlayer().getName() + "s turn.");
		board.place(pt.x, pt.y,getPlayer());		
	}
	
}
