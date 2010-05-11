package view.interfaces;

import model.Board;
import control.interfaces.*;

public interface UserInterface {
	
	// The UI needs to interact with the GameLogic
	public void setGameLogic(GameLogic gl);
	
	// User p has won the game, take appropriate actions.
	public void wonGame(User p);
	
	// The game was drawn. Take appropriate actions.
	public void drawnGame();
	
	// Rebuild the UI according to this new model.
	public void newModel(Board b);
	
	// The model has been updated, update the UI.
	public void updateModel();
	
	// Retrieve the related notifier
	public Notifier getNotifier();
	
	// The game is over, let the user(s) inspect the board. 
	public void postGame();

}
