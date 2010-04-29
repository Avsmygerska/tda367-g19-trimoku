package View;

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


import Model.Board;
import Model.Player;



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

	private String game;
	
	private MainFrame hejochhopp = this;
	
	// Testing stuff

	Board b;
	Player active;
	Player p1 = new Player("p1",Color.RED);
	Player p2 = new Player("p2",Color.GREEN);	
	//

	public MainFrame(int x, int y, int z) {
		super();
		initGUI();
	}

	private void initGUI() {
		BorderLayout thisLayout = new BorderLayout();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(thisLayout);			

		b = new Board(5,5,5);
		active = p1;

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
		DDD.getRender().setBoard(b);
		getContentPane().add(DDD, BorderLayout.CENTER);
		controlPanel = new ControlPanel(5,5,DDD.getRender());
		getContentPane().add(controlPanel,BorderLayout.EAST);

		pack();
		DDD.start();
	}

	private AbstractAction getExitGameAction() {
		if(ExitGameAction == null) {
			ExitGameAction = new AbstractAction("Exit Game", null) {
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
				public void actionPerformed(ActionEvent evt){
					JFrame frame = new JFrame();
					JPanel panel = new NewGame(hejochhopp, frame);
					frame.setPreferredSize(new Dimension(500,500));	
					frame.setLocation(550, 280);
					frame.setTitle("New Game");
					frame.add(panel);
					frame.pack();
					frame.setVisible(true);
				    
					
				}
			};
		}
				
		return NewGameAction;
	}
	
	public void startNewGame(String game){
		this.game = game;
		b.clear();
	}
}
