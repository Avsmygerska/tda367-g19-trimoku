import javax.swing.SwingUtilities;
import view.*;
import model.*;
import control.*;




public class Trimoku {	
		
	public static void main(String[] args) {
		
		GameLogic gameLogic = new GameLogic();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame(5,5,5);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
