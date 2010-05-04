package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinPanel extends JPanel{
	
	private String winner;
	private MainFrame mf;
	private JFrame frame;
	
	private AbstractAction startNewGameAction;
	
	public WinPanel(String winner, MainFrame mf, JFrame frame) {
		super();
		this.winner = winner;
		this.mf = mf;
		this.frame = frame;
		initGUI();
	}
	
	public void initGUI(){
		try{
			BorderLayout border = new BorderLayout();
			setPreferredSize(new Dimension(300,200));
			this.setLayout(border);
			JButton newGame = new JButton();
			JLabel winnerLabel = new JLabel();
			
			newGame.setAction(getStartNewGameAction());
			
			newGame.setText("pewpew");
			winnerLabel.setText("Congratulations " + winner + ", you won!");
			this.add(winnerLabel, BorderLayout.NORTH);
			this.add(newGame, BorderLayout.SOUTH);
			
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
}
