package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

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
	private static final long serialVersionUID = -8006283261844224326L;
	
	private int boardSize;
	
	private MainFrame mf;
	private JTextField player1TextField;
	private JLabel boardSizeLabel;
	private JComboBox jComboBox3;
	private JComboBox jComboBox2;
	private JComboBox jComboBox1;
	private AbstractAction getBoardSizeAction;
	private JLabel boardSizeLabel5x5;
	private JCheckBox checkBoxBoardSize5x5;
	private JLabel boardSizeLabel4x4;
	private JCheckBox checkBoxBoardSize4x4;
	private JLabel boardSize3x3;
	private JCheckBox checkBoxBoardSize3x3;
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
	private AbstractAction player1Color;
	private AbstractAction player2Color;
	private AbstractAction aiPlayerColor;
	private JFrame frame;
	private Color player1 = Color.red;
	private Color player2 = Color.blue;
	private Color aiPlayer = Color.red;
	private int jComboBox1Index = 0;
	private int jComboBox2Index = 1;
	
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
			thisLayout.rowHeights = new int[] {50, 50, 100, 20, 20, 20, 20, 20};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {20, 20, 100};
			this.setLayout(thisLayout);
			{
				player1Label = new JLabel();
				this.add(player1Label, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player1Label.setText("Player1:");
			}
			{
				player2Label = new JLabel();
				this.add(player2Label, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player2Label.setText("Player2:");
			}
			{
				player1TextField = new JTextField();
				player1TextField.setSize(40, 20);
				this.add(player1TextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player1TextField.setPreferredSize(new java.awt.Dimension(101, 22));
				player1TextField.setEnabled(false);
			}
			{
				player2TextField = new JTextField();
				this.add(player2TextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				player2TextField.setPreferredSize(new java.awt.Dimension(101, 22));
				player2TextField.setEnabled(false);
				player2TextField.setSize(70, 22);
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
			}
			{
				aiPlayerTextField = new JTextField();
				aiPlayerTextField.setText("Player");
				this.add(aiPlayerTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				aiPlayerTextField.setPreferredSize(new java.awt.Dimension(98, 22));
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
				this.add(buttonStartNewGame, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 25), 0, 0));
				buttonStartNewGame.setText("Start");
				buttonStartNewGame.setPreferredSize(new java.awt.Dimension(76, 22));
				buttonStartNewGame.setAction(getStartButtonAction());
			}
			{
				buttonCancelNewGame = new JButton();
				buttonCancelNewGame.setText("Cancel");
				buttonCancelNewGame.setPreferredSize(new java.awt.Dimension(77, 22));
				buttonCancelNewGame.setAction(getCancelButtonAction());
				
				checkBoxBoardSize3x3 = new JCheckBox();
				checkBoxBoardSize3x3.setAction(getBoardSizeAction());
				
				checkBoxBoardSize4x4 = new JCheckBox();
				checkBoxBoardSize4x4.setAction(getBoardSizeAction);
				
				checkBoxBoardSize5x5 = new JCheckBox();
				checkBoxBoardSize5x5.setAction(getBoardSizeAction);
				
							
				this.add(buttonCancelNewGame, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getBoardSizeLabel(), new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(checkBoxBoardSize3x3, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getBoardSize3x3(), new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 9), 0, 0));
				this.add(checkBoxBoardSize4x4, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getBoardSizeLabel4x4(), new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));
				this.add(checkBoxBoardSize5x5, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getBoardSizeLabel5x5(), new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));
				this.add(getJComboBox1(), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getJComboBox2(), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getJComboBox3(), new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

				// Default settings.
				checkBoxBoardSize5x5.setSelected(true);
				boardSize = 5;
				hotSeatCheckBox.setSelected(true);
				getHotSeatCheckBoxAction().actionPerformed(null);
				aiPlayerTextField.setText("Player");
				player1TextField.setText("Player1");
				player2TextField.setText("Player2");
				// ----------------
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
	
	private JLabel getBoardSizeLabel() {
		if(boardSizeLabel == null) {
			boardSizeLabel = new JLabel();
			boardSizeLabel.setText("Board Size");
		}
		return boardSizeLabel;
	}
	
	
	private JLabel getBoardSize3x3() {
		if(boardSize3x3 == null) {
			boardSize3x3 = new JLabel();
			boardSize3x3.setText("3x3x3");
		}
		return boardSize3x3;
	}
	
	
	private JLabel getBoardSizeLabel4x4() {
		if(boardSizeLabel4x4 == null) {
			boardSizeLabel4x4 = new JLabel();
			boardSizeLabel4x4.setText("4x4x4");
		}
		return boardSizeLabel4x4;
	}
	
	private JLabel getBoardSizeLabel5x5() {
		if(boardSizeLabel5x5 == null) {
			boardSizeLabel5x5 = new JLabel();
			boardSizeLabel5x5.setText("5x5x5");
		}
		return boardSizeLabel5x5;
	}
	
	private JComboBox getJComboBox1() {
		if(jComboBox1 == null) {
			ComboBoxModel jComboBox1Model = 
				new DefaultComboBoxModel(
						new String[] { "Red", "Blue", "Green", "Orange" });
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(jComboBox1Model);
			jComboBox1.setSelectedIndex(0);
			jComboBox1.setAction(getPlayer1Color());
		}
		return jComboBox1;
	}
	
	private JComboBox getJComboBox2() {
		if(jComboBox2 == null) {
			ComboBoxModel jComboBox2Model = 
				new DefaultComboBoxModel(
						new String[] { "Red", "Blue", "Green", "Orange" });
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(jComboBox2Model);
			jComboBox2.setSelectedIndex(1);
			jComboBox2.setAction(getPlayer2Color());
		}
		return jComboBox2;
	}
	
	private JComboBox getJComboBox3() {
		if(jComboBox3 == null) {
			ComboBoxModel jComboBox3Model = 
				new DefaultComboBoxModel(
						new String[]{ "Red", "Blue", "Green", "Orange" });
			jComboBox3 = new JComboBox();
			jComboBox3.setModel(jComboBox3Model);
			jComboBox3.setSelectedIndex(0);
			jComboBox3.setAction(getAIPlayerColor());
		}
		return jComboBox3;
	}
	
	
	private AbstractAction getHotSeatCheckBoxAction(){
		if(getHotSeatCheckBoxAction == null) {
			getHotSeatCheckBoxAction = new AbstractAction("", null) {
				private static final long serialVersionUID = -2381311498380011349L;

				public void actionPerformed(ActionEvent evt){
					if(hotSeatCheckBox.isSelected()){
						player1TextField.setEnabled(true);
						player2TextField.setEnabled(true);
						jComboBox1.setEnabled(true);
						jComboBox2.setEnabled(true);
						jComboBox3.setEnabled(false);
						aiCheckBox.setSelected(false);
						aiPlayerTextField.setEnabled(false);
						
					}
					else{
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
						jComboBox1.setEnabled(false);
						jComboBox2.setEnabled(false);
					}
					
				}
			};
		}
				
		return getHotSeatCheckBoxAction;
	}
	
	private AbstractAction getAICheckBoxAction(){
		if(getAICheckBoxAction == null) {
			getAICheckBoxAction = new AbstractAction("", null) {
				private static final long serialVersionUID = -4532880389414923636L;

				public void actionPerformed(ActionEvent evt){
					if(aiCheckBox.isSelected()){
						aiPlayerTextField.setEnabled(true);
						hotSeatCheckBox.setSelected(false);
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
						jComboBox1.setEnabled(false);
						jComboBox2.setEnabled(false);
						jComboBox3.setEnabled(true);
						
					}
					else{
						aiPlayerTextField.setEnabled(false);
						jComboBox3.setEnabled(false);
					}
					
				}
			};
		}
				
		return getAICheckBoxAction;
	}
	
	private AbstractAction getStartButtonAction(){
		if(getStartButtonAction == null) {
			getStartButtonAction = new AbstractAction("Start", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					if(hotSeatCheckBox.isSelected()){
						mf.startNewGame(1, getPlayer1TextField(), getPlayer2TextField(), boardSize, boardSize, boardSize, player1, player2);
						frame.dispose();
					}
					if(aiCheckBox.isSelected()){
						mf.startNewGame(2, getAIPlayerTextField(), getPlayer2TextField(), boardSize, boardSize, boardSize, aiPlayer, player2);
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
				private static final long serialVersionUID = 1085655044529206578L;

				public void actionPerformed(ActionEvent evt){
					frame.dispose();					
				}
			};
		}
				
		return getCancelButtonAction;
	}
		
	private AbstractAction getBoardSizeAction() {
		if(getBoardSizeAction == null) {
			getBoardSizeAction = new AbstractAction("", null) {
				private static final long serialVersionUID = 6789890595330428545L;

				public void actionPerformed(ActionEvent evt) {
					if(evt.getSource().equals(checkBoxBoardSize3x3)){
						checkBoxBoardSize4x4.setSelected(false);
						checkBoxBoardSize5x5.setSelected(false);
						boardSize = 3;
					}
					if(evt.getSource().equals(checkBoxBoardSize4x4)){
						checkBoxBoardSize3x3.setSelected(false);
						checkBoxBoardSize5x5.setSelected(false);
						boardSize = 4;
					}
					if(evt.getSource().equals(checkBoxBoardSize5x5)){
						checkBoxBoardSize3x3.setSelected(false);
						checkBoxBoardSize4x4.setSelected(false);
						boardSize = 5;
					}
				}
			};
		}
		return getBoardSizeAction;
	}
	private AbstractAction getPlayer1Color() {
		if(player1Color == null) {
			player1Color = new AbstractAction("", null) {
				private static final long serialVersionUID = 6789890595330428545L;

				public void actionPerformed(ActionEvent evt) {
					if(jComboBox1.getSelectedItem().equals(jComboBox2.getSelectedItem())){
						jComboBox1.setSelectedIndex(jComboBox1Index);
					}
					if(jComboBox1.getSelectedItem().equals("Red")){
						jComboBox1Index = 0;
						player1 = Color.red;
					}
					if(jComboBox1.getSelectedItem().equals("Blue")){
						jComboBox1Index = 1;
						player1 = Color.blue;
					}
					if(jComboBox1.getSelectedItem().equals("Green")){
						jComboBox1Index = 2;
						player1 = Color.green;
					}
					if(jComboBox1.getSelectedItem().equals("Orange")){
						jComboBox1Index = 3;
						player1 = Color.orange;
					}
				}	
			};
		}
		return player1Color;
	}
	
	private AbstractAction getPlayer2Color() {
		if(player2Color == null) {
			player2Color = new AbstractAction("", null) {
				private static final long serialVersionUID = 6789890595330428545L;

				public void actionPerformed(ActionEvent evt) {
					if(jComboBox2.getSelectedItem().equals(jComboBox1.getSelectedItem())){
						jComboBox2.setSelectedIndex(jComboBox2Index);
					}
					if(jComboBox2.getSelectedItem().equals("Red")){
						jComboBox2Index = 0;
						player2 = Color.red;
					}
					if(jComboBox2.getSelectedItem().equals("Blue")){
						jComboBox2Index = 1;
						player2 = Color.blue;
					}
					if(jComboBox2.getSelectedItem().equals("Green")){
						jComboBox2Index = 2;
						player2 = Color.green;
					}
					if(jComboBox2.getSelectedItem().equals("Orange")){
						jComboBox2Index = 3;
						player2 = Color.orange;
					}
				}
				
			};
		}
		return player2Color;
	}
	
	private AbstractAction getAIPlayerColor() {
		if(aiPlayerColor == null) {
			aiPlayerColor = new AbstractAction("", null) {
				private static final long serialVersionUID = 6789890595330428545L;

				public void actionPerformed(ActionEvent evt) {
					
					if(jComboBox3.getSelectedItem().equals("Red")){
						aiPlayer = Color.red;
					}
					if(jComboBox3.getSelectedItem().equals("Blue")){
						aiPlayer = Color.blue;
					}
					if(jComboBox3.getSelectedItem().equals("Green")){
						aiPlayer = Color.green;
					}
					if(jComboBox3.getSelectedItem().equals("Orange")){
						aiPlayer = Color.orange;
					}
				}
				
			};
		}
		return aiPlayerColor;
	}

}
