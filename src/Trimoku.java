import java.awt.Color;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import view.*;
import model.*;
import control.*;




public class Trimoku {
	static MainFrame inst;

	public static void main(String[] args) {
		
		final CountDownLatch cl = new CountDownLatch(1);

		// Default game
		final GameLogic gameLogic = new GameLogic(5,5,5);

		final Runnable t = new Runnable () {
			public void run() {
				
				// Synchronization stuff
				try { cl.await();
				} catch (InterruptedException e) { e.printStackTrace();	}
				
				gameLogic.addUser(new LocalUser(new Player("Player 1",Color.GREEN),gameLogic.getBoard(),inst.getControlPanel()));
				gameLogic.addUser(new LocalUser(new Player("Player 2",Color.RED),gameLogic.getBoard(),inst.getControlPanel()));				
				gameLogic.run();
			}
		};	

		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						inst = new MainFrame(gameLogic);
						inst.setLocationRelativeTo(null);
						inst.setVisible(true);
						cl.countDown();
					}
				});		

		t.run();
	}
}
