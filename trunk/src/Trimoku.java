import java.awt.Color;

import javax.swing.SwingUtilities;
import view.*;
import model.*;
import control.*;




public class Trimoku {	
		
	public static void main(String[] args) {
		
		// Default game
		final GameLogic gameLogic = new GameLogic(5,5,5);
		gameLogic.addPlayer(new User(new Player("Player 1",Color.GREEN)));
		gameLogic.addPlayer(new User(new Player("Player 2",Color.RED)));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame(gameLogic);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
