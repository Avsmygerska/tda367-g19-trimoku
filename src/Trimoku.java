import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import view.*;
import model.*;
import control.interfaces.*;
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
		
		final TrimokuLogic gameLogic = new TrimokuLogic(inst);
		
		// Default game.
		ArrayList<User> players = new ArrayList<User>();		
		players.add(new LocalUser(new Player("Player 1",Color.BLUE),inst.getControlPanel()));
		players.add(new LocalUser(new Player("Player 2",Color.RED),inst.getControlPanel()));		
		gameLogic.configure(5, 5, 5, players);
		
		gameLogic.run();
	}
}
