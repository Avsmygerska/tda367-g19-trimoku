package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import control.interfaces.User;
import control.LocalUser;
import control.TidyAI;
import model.Player;

public class NewGame extends JPanel {
	private static final long serialVersionUID = -8006283261844224326L;

	private int boardSize;
	private MainFrame mf;
	private JFrame frame;
	
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel aiPlayerLabel;	
	private JTextField player1TextField;
	private JTextField player2TextField;
	private JTextField aiPlayerTextField;
	
	private JComboBox colorSelectorAI;
	private JComboBox colorSelectorP2;
	private JComboBox colorSelectorP1;

	private JLabel boardSizeLabel;
	private JLabel boardSizeLabel3x3;
	private JLabel boardSizeLabel4x4;
	private JLabel boardSizeLabel5x5;
	private JCheckBox checkBoxBoardSize3x3;
	private JCheckBox checkBoxBoardSize4x4;
	private JCheckBox checkBoxBoardSize5x5;	

	private JButton buttonStartNewGame;
	private JButton buttonCancelNewGame;	

	private JLabel aiCheckBoxLabel;
	private JLabel hsCheckBoxLabel;
	private JCheckBox aiCheckBox;
	private JCheckBox hsCheckBox;

	private JSeparator jSeparator1;
	private JSeparator jSeparator2;

	private AbstractAction getHotSeatCheckBoxAction;
	private AbstractAction getAICheckBoxAction;
	private AbstractAction getStartButtonAction;
	private AbstractAction getCancelButtonAction;
	private AbstractAction boardSizeAction;
	

	private String[] colName = {"Red","Blue","Green","Orange"};
	private Color[] colors = {Color.RED,Color.BLUE,Color.GREEN,new Color(255,70,0)};

	public NewGame(MainFrame mf, JFrame frame) {
		super();
		frame.add(this);
		this.mf = mf;
		this.frame = frame;	

		initGUI();
	}

	private void initGUI() {
		Insets none = new Insets(0,0,0,0);
		Dimension textFieldSize = new Dimension(101,22);
		Dimension buttonSize = new Dimension(76,22);
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setPreferredSize(new Dimension(400, 400));
			thisLayout.rowWeights    = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights    = new int[] {50, 50, 100, 20, 20, 20, 20, 20};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths  = new int[] {20, 20, 100};
			setLayout(thisLayout);
			
			// Separators.
			jSeparator1 = new JSeparator();
			jSeparator2 = new JSeparator();			
			jSeparator2.setPreferredSize(new java.awt.Dimension(394, 5));
			add(jSeparator1, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, none, 0, 0));
			add(jSeparator2, new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, none, 0, 0));

			// Player name selection
			player1TextField = new JTextField("Player 1");
			player2TextField = new JTextField("Player 2");
			aiPlayerTextField = new JTextField("Player");
			player1TextField.setPreferredSize(textFieldSize);			
			player2TextField.setPreferredSize(textFieldSize);
			aiPlayerTextField.setPreferredSize(textFieldSize);
			player1TextField.setEnabled(false);
			player2TextField.setEnabled(false);
			aiPlayerTextField.setEnabled(false);
			add(player1TextField,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, none, 0, 0));
			add(player2TextField,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, none, 0, 0));
			add(aiPlayerTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, none, 0, 0));
			
			// Player name selection labels.
			player1Label  = new JLabel("Player 1:");
			player2Label  = new JLabel("Player 2:");
			aiPlayerLabel = new JLabel("Player");
			add(player1Label,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));
			add(player2Label,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, none, 0, 0));
			add(aiPlayerLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));

			// Game mode selection.
			hsCheckBox = new JCheckBox();
			aiCheckBox = new JCheckBox();
			hsCheckBox.setAction(getHotSeatCheckBoxAction());
			aiCheckBox.setAction(getAICheckBoxAction());
			add(hsCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));
			add(aiCheckBox, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));

			// Game mode selection labels.
			hsCheckBoxLabel = new JLabel("Hot Seat");
			aiCheckBoxLabel = new JLabel("AI");
			add(hsCheckBoxLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));			
			add(aiCheckBoxLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));

			// Add buttons			
			buttonStartNewGame = new JButton("Start");
			buttonCancelNewGame = new JButton("Cancel");
			buttonStartNewGame.setPreferredSize(buttonSize);
			buttonCancelNewGame.setPreferredSize(buttonSize);
			buttonStartNewGame.setAction(getStartButtonAction());		
			buttonCancelNewGame.setAction(getCancelButtonAction());			
			add(buttonStartNewGame, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 25), 0, 0));
			add(buttonCancelNewGame, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, none, 0, 0));

			// Board size selection things.
			checkBoxBoardSize3x3 = new JCheckBox();
			checkBoxBoardSize4x4 = new JCheckBox();
			checkBoxBoardSize5x5 = new JCheckBox();
			checkBoxBoardSize3x3.setAction(getBoardSizeAction());			
			checkBoxBoardSize4x4.setAction(getBoardSizeAction());			
			checkBoxBoardSize5x5.setAction(getBoardSizeAction());
			add(checkBoxBoardSize3x3, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));
			add(checkBoxBoardSize4x4, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));
			add(checkBoxBoardSize5x5, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));

			// Labels for the board sizes.
			boardSizeLabel = new JLabel("Board Size");
			boardSizeLabel3x3 = new JLabel("3x3x3");
			boardSizeLabel4x4 = new JLabel("4x4x4");
			boardSizeLabel5x5 = new JLabel("5x5x5");			
			add(boardSizeLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, none, 0, 0));			
			add(boardSizeLabel3x3, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 9), 0, 0));			
			add(boardSizeLabel4x4, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));			
			add(boardSizeLabel5x5, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0));

			// Color selection things.
			colorSelectorP1 = new JComboBox();
			colorSelectorP2 = new JComboBox();
			colorSelectorAI = new JComboBox();
			colorSelectorP1.setModel(new DefaultComboBoxModel(colName));
			colorSelectorP2.setModel(new DefaultComboBoxModel(colName));
			colorSelectorAI.setModel(new DefaultComboBoxModel(colName));
			colorSelectorP1.setSelectedIndex(0);
			colorSelectorP2.setSelectedIndex(1);
			colorSelectorAI.setSelectedIndex(0);
			colorSelectorP1.setAction(new getColor(colorSelectorP1,colorSelectorP2));			
			colorSelectorP2.setAction(new getColor(colorSelectorP2,colorSelectorP1));			
			add(colorSelectorP1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, none, 0, 0));
			add(colorSelectorP2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, none, 0, 0));
			add(colorSelectorAI, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, none, 0, 0));

			// Default settings.
			checkBoxBoardSize5x5.setSelected(true);
			boardSize = 5;
			hsCheckBox.setSelected(true);
			getHotSeatCheckBoxAction().actionPerformed(null);
			// ----------------

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private AbstractAction getHotSeatCheckBoxAction(){
		if(getHotSeatCheckBoxAction == null) {
			getHotSeatCheckBoxAction = new AbstractAction("", null) {
				private static final long serialVersionUID = -2381311498380011349L;

				public void actionPerformed(ActionEvent evt){
					if(hsCheckBox.isSelected()){
						player1TextField.setEnabled(true);
						player2TextField.setEnabled(true);
						colorSelectorP1.setEnabled(true);
						colorSelectorP2.setEnabled(true);
						colorSelectorAI.setEnabled(false);
						aiCheckBox.setSelected(false);
						aiPlayerTextField.setEnabled(false);
					} else {
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
						colorSelectorP1.setEnabled(false);
						colorSelectorP2.setEnabled(false);
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
						hsCheckBox.setSelected(false);
						player1TextField.setEnabled(false);
						player2TextField.setEnabled(false);
						colorSelectorP1.setEnabled(false);
						colorSelectorP2.setEnabled(false);
						colorSelectorAI.setEnabled(true);
					} else {
						aiPlayerTextField.setEnabled(false);
						colorSelectorAI.setEnabled(false);
					}
				}
			};
		}

		return getAICheckBoxAction;
	}

	//Calls the MainFrame with the new Players and disposes of the frame. 
	private AbstractAction getStartButtonAction(){
		if(getStartButtonAction == null) {
			getStartButtonAction = new AbstractAction("Start", null) {
				private static final long serialVersionUID = -132098728087120287L;

				public void actionPerformed(ActionEvent evt){
					ArrayList<User> players = new ArrayList<User>();
					if(hsCheckBox.isSelected()){
						String n1 = player1TextField.getText();
						Color c1  = colors[colorSelectorP1.getSelectedIndex()];						
						String n2 = player2TextField.getText();
						Color c2  = colors[colorSelectorP2.getSelectedIndex()];												
						players.add(new LocalUser(new Player(n1,c1),mf.getControlPanel()));
						players.add(new LocalUser(new Player(n2,c2),mf.getControlPanel()));						
					}
					if(aiCheckBox.isSelected()){
						Color c  = colors[colorSelectorAI.getSelectedIndex()];
						String n = aiPlayerTextField.getText();
						players.add(new LocalUser(new Player(n,c),mf.getControlPanel()));
						players.add(new TidyAI(new Player("AI",new Color(200,138,101))));
					}
					mf.startNewGame(players, boardSize, boardSize, boardSize);
					frame.dispose();
				}
			};
		}

		return getStartButtonAction;
	}

	// Just disposes of the frame.
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

	// Checks what JCheckBox is selected and sets boardSize to this value.
	private AbstractAction getBoardSizeAction() {
		if(boardSizeAction == null) {
			boardSizeAction = new AbstractAction("", null) {
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
		return boardSizeAction;
	}

	// If the two associated JComboBoxes collide the changed one is reset to it's previous value.  
	class getColor extends AbstractAction {
		private static final long serialVersionUID = -3636924121100604109L;
		private int index;
		private JComboBox me;
		private JComboBox him;

		public getColor(JComboBox me, JComboBox him) {			
			this.me = me;			
			this.him = him;
			index = me.getSelectedIndex();
		}

		public void actionPerformed(ActionEvent arg0) {
			if(me.getSelectedIndex() == him.getSelectedIndex())
				me.setSelectedIndex(index);
			index = me.getSelectedIndex();			
		}

	}
}
