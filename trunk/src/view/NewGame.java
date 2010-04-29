package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewGame extends javax.swing.JPanel {
	
	private MainFrame mf;
	private JTextField player1TextField;
	private JButton buttonCancelNewGame;
	private JButton buttonStartNewGame;
	private JLabel aiCheckBoxLabel;
	private JLabel hotSeatCheckBoxLabel;
	private JCheckBox hotSeatCheckBox;
	private JTextField aiPlayerTextField;
	private JLabel aiPlayerLabel;
	private JCheckBox aiCheckBox;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JTextField player2TextField;
	private JLabel player2Label;
	private JLabel player1Label;
	private AbstractAction getHotSeatCheckBoxAction;
	private AbstractAction getAICheckBoxAction;
	private AbstractAction getStartButtonAction;
	private AbstractAction getCancelButtonAction;
	private JFrame frame;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
/*	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewGame());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}*/
	
	public NewGame(MainFrame mf, JFrame frame) {
		super();
		this.mf = mf;
		this.frame = frame;
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setPreferredSize(new Dimension(400, 400));
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {10, 10, 40, 40, 40, 40};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {10, 20, 70};
			this.setLayout(thisLayout);
			{
				player1Label = new JLabel();
				this.add(player1Label, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player1Label.setText("Player1:");
			}
			{
				player2Label = new JLabel();
				this.add(player2Label, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player2Label.setText("Player2:");
			}
			{
				player1TextField = new JTextField();
				player1TextField.setSize(40, 20);
				this.add(player1TextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player1TextField.setPreferredSize(new java.awt.Dimension(140, 22));
				player1TextField.setEnabled(false);
			}
			{
				player2TextField = new JTextField();
				this.add(player2TextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player2TextField.setPreferredSize(new java.awt.Dimension(140, 22));
				player2TextField.setEnabled(false);
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				
			}
			{
				aiCheckBox = new JCheckBox();
				aiCheckBox.setAction(getAICheckBoxAction());
				this.add(aiCheckBox, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				aiPlayerLabel = new JLabel();
				this.add(aiPlayerLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				aiPlayerLabel.setText("Player");
			}
			{
				aiPlayerTextField = new JTextField();
				this.add(aiPlayerTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				aiPlayerTextField.setPreferredSize(new java.awt.Dimension(140, 22));
				aiPlayerTextField.setEnabled(false);
			}
			{
				hotSeatCheckBox = new JCheckBox();
				hotSeatCheckBox.setAction(getHotSeatCheckBoxAction());
				this.add(hotSeatCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				hotSeatCheckBoxLabel = new JLabel();
				this.add(hotSeatCheckBoxLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				hotSeatCheckBoxLabel.setText("Hot Seat");
			}
			{
				aiCheckBoxLabel = new JLabel();
				this.add(aiCheckBoxLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
				aiCheckBoxLabel.setText("AI");
			}
			{
				jSeparator2 = new JSeparator();
			this.add(jSeparator2, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			jSeparator2.setPreferredSize(new java.awt.Dimension(394, 5));
			}
			{
				buttonStartNewGame = new JButton();
				this.add(buttonStartNewGame, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				buttonStartNewGame.setText("Start");
				buttonStartNewGame.setPreferredSize(new java.awt.Dimension(76, 22));
				buttonStartNewGame.setAction(getStartButtonAction());
			}
			{
				buttonCancelNewGame = new JButton();
				this.add(buttonCancelNewGame, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				buttonCancelNewGame.setText("Cancel");
				buttonCancelNewGame.setPreferredSize(new java.awt.Dimension(77, 22));
				buttonCancelNewGame.setAction(getCancelButtonAction());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPlayer1TextField() {
		return player1TextField.getText();
	}
	
	public String getPlayer2TextField() {
		return player2TextField.getText();
	}
	
	public String getAIPlayerTextField(){
		return aiPlayerTextField.getText();
	}
	
	private AbstractAction getHotSeatCheckBoxAction(){
		if(getHotSeatCheckBoxAction == null) {
			getHotSeatCheckBoxAction = new AbstractAction("", null) {
				public void actionPerformed(ActionEvent evt){
					if(hotSeatCheckBox.isSelected()){
						player1TextField.setEnabled(true);
						player2TextField.setEnabled(true);
						aiCheckBox.setSelected(false);
						aiPlayerTextField.setEnabled(false);
					}
					else{
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
					}
					
				}
			};
		}
				
		return getHotSeatCheckBoxAction;
	}
	
	private AbstractAction getAICheckBoxAction(){
		if(getAICheckBoxAction == null) {
			getAICheckBoxAction = new AbstractAction("", null) {
				public void actionPerformed(ActionEvent evt){
					if(aiCheckBox.isSelected()){
						aiPlayerTextField.setEnabled(true);
						hotSeatCheckBox.setSelected(false);
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
						
					}
					else{
						aiPlayerTextField.setEnabled(false);
					}
					
				}
			};
		}
				
		return getAICheckBoxAction;
	}
	
	private AbstractAction getStartButtonAction(){
		if(getStartButtonAction == null) {
			getStartButtonAction = new AbstractAction("Start", null) {
				public void actionPerformed(ActionEvent evt){
					if(hotSeatCheckBox.isSelected()){
						mf.startNewGame("Hot Seat");
						frame.dispose();
					}
					if(aiCheckBox.isSelected()){
						mf.startNewGame("AI");
						frame.dispose();
					}
					
				}
			};
		}
				
		return getStartButtonAction;
	}
	
	private AbstractAction getCancelButtonAction(){
		if(getCancelButtonAction == null) {
			getCancelButtonAction = new AbstractAction("Cancel", null) {
				public void actionPerformed(ActionEvent evt){
					frame.dispose();					
				}
			};
		}
				
		return getCancelButtonAction;
	}
}
