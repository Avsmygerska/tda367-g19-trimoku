package view;

import model.Board;
import control.GameLogic;
import control.User;

public interface UserInterface {
	
	public void setGameLogic(GameLogic gl);
	public void wonGame(User p);
	public void drawnGame();
	public void updateModel(Board b);
	public Notifier getNotifier();
	public void activatePostGameControls();

}
