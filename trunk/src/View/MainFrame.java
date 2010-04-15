package View;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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
public class MainFrame extends javax.swing.JFrame {
	private RenderPanel DDD;
	private JPanel Controls;
	private PinArea PinArea;
	
	private JButton PlaceButton;
	private JButton HideButton;

	private AbstractAction ExitGameAction;
	private AbstractAction PlaceAction;
	private AbstractAction HidePinAction;
	
	private JMenuItem ExitGameMenuOption;
	private JMenuItem NewGameMenuOption;

	private JMenuBar MenuBar;
	private JMenu jMenu1;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			this.setResizable(false);
			{
				MenuBar = new JMenuBar();
				setJMenuBar(MenuBar);
				{
					jMenu1 = new JMenu();
					MenuBar.add(jMenu1);
					jMenu1.setText("File");
					{
						NewGameMenuOption = new JMenuItem();
						jMenu1.add(NewGameMenuOption);
						NewGameMenuOption.setText("New Game");
					}
					{
						ExitGameMenuOption = new JMenuItem();
						jMenu1.add(ExitGameMenuOption);
						ExitGameMenuOption.setText("Exit Game");
						ExitGameMenuOption.setAction(getExitGameAction());					}
				}
			}
			{
				DDD = new RenderPanel(600,600);
				//DDD.setPreferredSize(new java.awt.Dimension(600, 600));
				getContentPane().add(DDD.getPanel(), BorderLayout.CENTER);
				Controls = new JPanel();
				BoxLayout ControlsLayout = new BoxLayout(Controls, javax.swing.BoxLayout.Y_AXIS);
				Controls.setLayout(ControlsLayout);
				Controls.setPreferredSize(new java.awt.Dimension(110, 70));
				getContentPane().add(Controls, BorderLayout.EAST);				
				{
					PlaceButton = new JButton();
					{
						PinArea = new PinArea();
						Controls.add(PinArea);
						Controls.add(PlaceButton);						
						Controls.add(getHideButton());
						//PinArea.setMaximumSize(new java.awt.Dimension(90, 90));
					}
					
					PlaceButton.setText("Place");
					PlaceButton.setPreferredSize(new java.awt.Dimension(44, 16));
					PlaceButton.addActionListener(getPlaceAction());
				}
			}
			pack();
			DDD.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AbstractAction getPlaceAction() {
		if(PlaceAction == null) {
			PlaceAction = new AbstractAction("Place", null) {
				public void actionPerformed(ActionEvent evt) {
					String s = "";
					for(int i = 0; i < 5;i++){
						for(int j = 0; j < 5;j++){
							JCheckBox r = PinArea.getPin(i,j);
							if(r.isSelected()) {
								s = s + i + j+" ";								
							}							
						}					
					}
				}
			};
		}
		return PlaceAction;
	}
	
	private AbstractAction getHidePinAction() {
		if(HidePinAction == null) {
			HidePinAction = new AbstractAction("Hide Pin", null) {
				public void actionPerformed(ActionEvent evt) {
					
				}
			};
		}
		return HidePinAction;
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

	private JButton getHideButton() {
		if(HideButton == null) {
			HideButton = new JButton();
			HideButton.setText("Hide");
		}
		return HideButton;
	}
}
