package control.interfaces;

import java.util.ArrayList;

import view.interfaces.UserInterface;

public interface GameLogic {

	public abstract void setUserInterface(UserInterface ui);

	public abstract void configure(int x, int y, int z,
			ArrayList<User> newPlayers);

}