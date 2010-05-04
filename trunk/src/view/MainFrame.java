package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Player;
import control.*;

public class MainFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 8261482053967412810L;
	private RenderPanel DDD;
	private ControlPanel controlPanel;
	
	private AbstractAction NewGameAction;
	private AbstractAction ExitGameAction;

	private JMenuItem ExitGameMenuOption;
	private JMenuItem NewGameMenuOption;

	private JMenuBar MenuBar;
	private JMenu jMenu1;
	
	String game;
		
	private MainFrame hejochhopp = this;
	
	// Testing stuff
	GameLogic gameLogic;
	
	public MainFrame(GameLogic gameLogic) {
		super();
		this.gameLogic = gameLogic;
		initGUI();
	}

	private void initGUI() {
		BorderLayout thisLayout = new BorderLayout();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(thisLayout);	

		this.setResizable(false);

		NewGameMenuOption = new JMenuItem();
		NewGameMenuOption.setText("New Game");
		NewGameMenuOption.setAction(getNewGameAction());
		
		ExitGameMenuOption = new JMenuItem();
		ExitGameMenuOption.setText("Exit Game");
		ExitGameMenuOption.setAction(getExitGameAction());
		
		jMenu1 = new JMenu();
		jMenu1.setText("File");
		jMenu1.add(NewGameMenuOption);
		jMenu1.add(ExitGameMenuOption);
		MenuBar = new JMenuBar();
		setJMenuBar(MenuBar);
		MenuBar.add(jMenu1);		

		DDD = new RenderPanel(600,600,gameLogic.getBoard());
		getContentPane().add(DDD, BorderLayout.CENTER);
		controlPanel = new ControlPanel(5,5,DDD.getRender());
		getContentPane().add(controlPanel,BorderLayout.EAST);
		
		pack();
		DDD.start();
		
	}

	private AbstractAction getExitGameAction() {
		if(ExitGameAction == null) {
			ExitGameAction = new AbstractAction("Exit Game", null) {
				private static final long serialVersionUID = 891261472154540862L;

				public void actionPerformed(ActionEvent evt) {
					DDD.stop();
					System.exit(0);
				}
			};
		}
		return ExitGameAction;
	}
	
	private AbstractAction getNewGameAction(){
		if(NewGameAction == null) {
			NewGameAction = new AbstractAction("New Game", null) {
				private static final long serialVersionUID = -5710960946166469906L;

				public void actionPerformed(ActionEvent evt){
					JFrame frame = new JFrame();
					NewGame newgame = new NewGame(hejochhopp, frame);
					frame.setPreferredSize(new Dimension(500,400));
					frame.setLocation(550, 280);
					frame.setTitle("New Game");
					frame.add(newgame);
					frame.pack();
					frame.setVisible(true);
				}
			};
		}
				
		return NewGameAction;
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	
	// Det utkommenterade skall vara med senare
	
	public void startNewGame(int gameMode, String player1, String player2, int boardSize){

		gameLogic.reset(boardSize,boardSize,boardSize); // Needs to get proper dimensions and stuff

		if(gameMode == 1){
			gameLogic.addUser(new LocalUser(new Player(player1, Color.red), gameLogic.getBoard(), controlPanel));
			gameLogic.addUser(new LocalUser(new Player(player2, Color.green),gameLogic.getBoard(), controlPanel));
		}
		else{
			gameLogic.addUser(new LocalUser(new Player(player1, Color.red), gameLogic.getBoard(), controlPanel));
			//gameLogic.addplayer(new AIUser(new AI()));
		}
		DDD.getRender().setBoard(gameLogic.getBoard());
	}
}
