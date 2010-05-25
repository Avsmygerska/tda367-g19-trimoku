import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import view.*;
import model.*;
import control.interfaces.*;
import control.interfaces.GameLogic.GameMode;
import control.*;



public class Trimoku {
	static MainFrame inst = null;
	public static void main(String[] args) {
		
		final CountDownLatch cl = new CountDownLatch(1);

		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						inst = MainFrame.getInstance();
						inst.setLocationRelativeTo(null);
						inst.setVisible(true);
						cl.countDown();
					}
				});
		
		try { cl.await();
		} catch (InterruptedException e) { e.printStackTrace();	}		
		
		final TrimokuLogic gameLogic = new TrimokuLogic();
		gameLogic.setUserInterface(inst);
		
		// Default game.
		ArrayList<Player> players = new ArrayList<Player>();		
		players.add(new Player("Player 1",Color.GREEN));
		players.add(new Player("Player 2",Color.RED));		
		gameLogic.configure(5,GameMode.HOT_SEAT,inst.getControlPanel(), players);
		
		gameLogic.run();
	}
}
