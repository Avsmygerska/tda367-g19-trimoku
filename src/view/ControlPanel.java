package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.interfaces.*;

public class ControlPanel extends JPanel implements Notifier{

	private static final long serialVersionUID = -8040537962044433652L;
	private PinArea pinArea;
	private JPanel  buttonArea;

	private JButton placeButton;
	private JButton hideButton;

	private JLabel notice;

	private AbstractAction placeAction;
	private AbstractAction hideAction;

	private Render render;

	private Point placePoint;
	CountDownLatch latch;
	
	public ControlPanel(Render render) {
		this.render = render;

		this.setLayout(new BorderLayout());

		buttonArea = new JPanel();
		placeButton = new JButton();
		placeButton.setAction(getPlaceAction());
		placeButton.setFocusable(false);
		buttonArea.add(placeButton);

		hideButton = new JButton();
		hideButton.setAction(getHideAction());
		hideButton.setFocusable(false);
		buttonArea.add(hideButton);		

		notice = new JLabel();		

		pinArea = new PinArea(render,1, 1);
		
		add(pinArea,BorderLayout.NORTH);		
		add(buttonArea,BorderLayout.CENTER);
		add(notice,BorderLayout.SOUTH);
		
		latch = new CountDownLatch(0);

		active(false);
	}	

	public void active(boolean val) {
		placeButton.setEnabled(val);
		hideButton.setEnabled(val);		
		pinArea.active(val);		
	}
	
	public Point doTurn() {
		
		placePoint = new Point();		
		latch = new CountDownLatch(1);
		active(true);
		
		try {
			// Waiting for user input.
			latch.await();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		active(false);		
		render.showPins(true);
		return placePoint;
	}
	
	public void notifyTurn(String s) {
		notice.setText(s);
	}
	
	public void updateModel(int x, int y) {
		pinArea.constructButtons(x, y);
	}

	private AbstractAction getPlaceAction() {
		if(placeAction == null) {
			placeAction = new AbstractAction("Place", null) {
				private static final long serialVersionUID = 2033526430007994507L;

				public void actionPerformed(ActionEvent evt) {					
					ArrayList<Point> pt = pinArea.getSelectedPins();
					if(pt.size() == 1) {						
						placePoint = pt.get(0);
						pinArea.showAllPins();
						latch.countDown();
					}
				}
			};
		}
		return placeAction;
	}
	
	private AbstractAction getHideAction() {		
		if(hideAction == null) {
			hideAction = new AbstractAction("Hide", null) {
				private static final long serialVersionUID = 320879940932949134L;

				public void actionPerformed(ActionEvent evt) {
					for(Point p : pinArea.getSelectedPins()) {
						// Switch the visibility state for the selected pin and updated the related row and column hiders.						
						render.switchPin(p.x, p.y);						
						pinArea.updateRowHider(p.x, render.visibleRow(p.x));
						pinArea.updateColHider(p.y, render.visibleCol(p.y));
					}
				}
			};
		}
		return hideAction;
	}
	
	// After winning or loosing a game, this makes the player unable to place but able to inspect the the board.
	public void activatePostGameControls() {
		placeButton.setEnabled(false);
		hideButton.setEnabled(true);
		pinArea.active(true);		
	}
	
	// When starting a new game, this will force the player to do a move to unlock the latch
	public void forceMove() {
		placePoint = new Point(0,0);
		latch.countDown();		
	}	
}
