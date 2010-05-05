package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinPanel extends JPanel{
	
	private static final long serialVersionUID = 5235829744803819437L;
	private String winner;
	private MainFrame mf;
	private JFrame frame;
	
	private AbstractAction startNewGameAction;
	private AbstractAction quitGameAction;
	
	public WinPanel(String winner, MainFrame mf, JFrame frame) {
		super();
		this.winner = winner;
		this.mf = mf;
		this.frame = frame;
		initGUI();
	}
	
	public void initGUI(){
		try{
			GridBagLayout thisLayout = new GridBagLayout();
			setPreferredSize(new Dimension(300,200));
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {100, 50};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {100};
			this.setLayout(thisLayout);
			JButton newGame  = new JButton();
			JButton quitGame = new JButton(); 
			JLabel winnerLabel = new JLabel();
			
			newGame.setAction(getStartNewGameAction());
			newGame.setText("New Game");
			quitGame.setAction(getQuitGameAction());
			quitGame.setText("Quit Game");
			if(!winner.equals("drawn")){
				winnerLabel.setText("Congratulations " + winner + ", you won!");
			}
			else{
				winnerLabel.setText("Game Over: Drawn Game");
			}
			this.add(winnerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			this.add(newGame, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
			this.add(quitGame, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	private AbstractAction getStartNewGameAction(){
		if(startNewGameAction == null) {
			startNewGameAction = new AbstractAction("", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					mf.newGame();
					frame.dispose();
				}	
			};
		}
				
		return startNewGameAction;
	}
	private AbstractAction getQuitGameAction(){
		if(quitGameAction == null) {
			quitGameAction = new AbstractAction("", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					System.exit(0);
				}	
			};
		}
				
		return quitGameAction;
	}
}
