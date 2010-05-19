package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import view.interfaces.*;
import control.interfaces.*;
import model.Board;

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
		
	private static MainFrame mainFrame = null;
	private GameLogic gameLogic;
	
	public static MainFrame getInstance() {
		if(mainFrame == null)
			mainFrame = new MainFrame();
		
		return mainFrame;
	}
	
	private MainFrame() {
		super();
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);		
		
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
		add(DDD, BorderLayout.CENTER);
		
		controlPanel = new ControlPanel(DDD.getRender());
		add(controlPanel,BorderLayout.EAST);
		
		pack();
		DDD.start();		
	}

	private AbstractAction getExitGameAction() {
		if(ExitGameAction == null) {
			ExitGameAction = new AbstractAction("Exit Game", null) {
				private static final long serialVersionUID = 891261472154540862L;

				public void actionPerformed(ActionEvent evt) {
					quit();
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
	
	public ControlPanel getControlPanel() { return controlPanel; }
	
	public void quit() {
		DDD.stop();
		System.exit(0);
	}
	
	public void newGame() {
		JFrame frame = new JFrame();
		NewGame newgame = new NewGame(mainFrame, frame);
		frame.setPreferredSize(new Dimension(500,400));
		frame.setLocation(550, 280);
		frame.setTitle("New Game");
		frame.add(newgame);
		frame.pack();
		frame.setVisible(true);
	}
		
	public void startNewGame(ArrayList<User> players, int xSize, int ySize, int zSize){
		gameLogic.configure(xSize, ySize, zSize, players);
	}

	public void drawnGame() {
		JFrame frame = new JFrame();
		WinPanel winpanel = new WinPanel("drawn", MainFrame.this, frame);
		frame.setPreferredSize(new Dimension(300,200));
		frame.setMaximumSize(new Dimension(300,200));
		frame.setResizable(false);
		frame.setLocation(670, 360);
		frame.setTitle("Drawn Game");
		frame.add(winpanel);
		frame.pack();
		frame.setVisible(true);	
	}

	public void wonGame(User u) {
		JFrame frame = new JFrame();
		WinPanel winpanel = new WinPanel(u.getPlayer().getName(), MainFrame.this, frame);
		frame.setPreferredSize(new Dimension(300,200));
		frame.setResizable(false);
		frame.setLocation(670, 360);
		frame.setTitle(u.getPlayer().getName() + " Wins!");
		frame.add(winpanel);
		frame.pack();
		frame.setVisible(true);		
	}

	@Override
	public void newModel(Board board) {
		DDD.getRender().setBoard(board);
		controlPanel.updateModel(board.getRows(), board.getColumns());
		pack();
	}
	
	public Notifier getNotifier() { return controlPanel; }
	public void setGameLogic(GameLogic gl) { gameLogic = gl; }
	public void postGame() { controlPanel.activatePostGameControls(); }
	public void updateModel() { /* Not implemented yet. */ }
}
