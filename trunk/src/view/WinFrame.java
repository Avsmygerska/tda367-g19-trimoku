package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WinFrame extends JFrame{
	
	private static final long serialVersionUID = 5235829744803819437L;
	private String winner;
	private MainFrame mf;
	
	private AbstractAction startNewGameAction;
	private AbstractAction quitGameAction;
	
	public WinFrame(String winner, MainFrame mf) {
		super();
		this.winner = winner;
		this.mf = mf;
		setPreferredSize(new Dimension(300,200));
		setResizable(false);
		setLocation(670, 360);
		setTitle("Game Over!");
		initGUI();
		pack();
		setVisible(true);
	}
	
	public void initGUI(){
		try{
			GridBagLayout thisLayout = new GridBagLayout();
			setPreferredSize(new Dimension(300,200));
			thisLayout.rowWeights    = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights    = new int[] {100, 50};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths  = new int[] {100};
			setLayout(thisLayout);
			JButton newGame    = new JButton();
			JButton quitGame   = new JButton(); 
			JLabel winnerLabel = new JLabel();
			
			newGame.setAction(getStartNewGameAction());
			quitGame.setAction(getQuitGameAction());
			
			if(!winner.equals("drawn"))
				winnerLabel.setText("Congratulations " + winner + ", you won!");
			else
				winnerLabel.setText("Game Over: Drawn Game");
			add(winnerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			add(newGame,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
			add(quitGame,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	private AbstractAction getStartNewGameAction(){
		if(startNewGameAction == null) {
			startNewGameAction = new AbstractAction("New Game", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					mf.newGame();
					dispose();
				}	
			};
		}
				
		return startNewGameAction;
	}
	private AbstractAction getQuitGameAction(){
		if(quitGameAction == null) {
			quitGameAction = new AbstractAction("Quit Game", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					mf.quit();
				}	
			};
		}
				
		return quitGameAction;
	}
}
