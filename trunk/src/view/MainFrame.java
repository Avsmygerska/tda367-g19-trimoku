package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import model.Board;
import model.Player;
import control.*;

public class MainFrame extends JFrame implements UserInterface {

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
	
	public MainFrame() {
		super();
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

		DDD = new RenderPanel(600,600);
		getContentPane().add(DDD, BorderLayout.CENTER);
		controlPanel = new ControlPanel(DDD.getRender());
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
					newGame();
				}
			};
		}
				
		return NewGameAction;
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	
	public void newGame(){
		JFrame frame = new JFrame();
		NewGame newgame = new NewGame(hejochhopp, frame);
		frame.setPreferredSize(new Dimension(500,400));
		frame.setLocation(550, 280);
		frame.setTitle("New Game");
		frame.add(newgame);
		frame.pack();
		frame.setVisible(true);
	}
	
	// Det utkommenterade skall vara med senare
	
	public void startNewGame(int gameMode, String player1, String player2, int xSize, int ySize, int zSize){
		ArrayList<User> players = new ArrayList<User>();
		players.add(new LocalUser(new Player(player1, Color.GREEN), controlPanel));		
		if(gameMode == 1)
			players.add(new LocalUser(new Player(player2, Color.RED), controlPanel));
		else
			players.add(new AIUser(new Player("AI",Color.RED)));
		gameLogic.configure(xSize, ySize, zSize, players);
	}

	@Override
	public void drawnGame() {
		// TODO		
	}

	@Override
	public void wonGame(User u) {
		JFrame frame = new JFrame();
		WinPanel winpanel = new WinPanel(u.getPlayer().getName(), MainFrame.this, frame);
		frame.setPreferredSize(new Dimension(300,200));
		frame.setLocation(450, 320);
		frame.setTitle(u.getPlayer().getName() + " Wins!");
		frame.add(winpanel);
		frame.pack();
		frame.setVisible(true);		
	}

	@Override
	public void newModel(Board board) {
		DDD.getRender().setBoard(board);
		controlPanel.updateModel(board.getX(), board.getY());
		pack();
	}
	
	
	@Override
	public Notifier getNotifier() {
		return controlPanel;
	}

	@Override
	public void setGameLogic(GameLogic gl) {
		gameLogic = gl;		
	}

	@Override
	public void postGame() {
		controlPanel.activatePostGameControls();		
	}

	@Override
	public void updateModel() {
		//TODO Don't update the renderer all the time, only when changes have occurred.		
	}
}
