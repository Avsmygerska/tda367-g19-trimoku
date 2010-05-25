package control.interfaces;

import java.util.ArrayList;

import model.Player;

import view.interfaces.ControlInterface;
import view.interfaces.UserInterface;

public interface GameLogic {
	
	public enum GameMode { HOT_SEAT, AI }
	
	public void setUserInterface(UserInterface ui);
	
	public void configure(int dim, GameMode gm, 
			ControlInterface ci, ArrayList<Player> players);
}