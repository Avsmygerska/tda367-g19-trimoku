package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{

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

	public ControlPanel(int width, int height, Render render) {
		this.render = render;

		this.setLayout(new BorderLayout());

		buttonArea = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonArea, javax.swing.BoxLayout.Y_AXIS);
		buttonArea.setLayout(buttonLayout);

		placeButton = new JButton();
		placeButton.setAction(getPlaceAction());

		hideButton = new JButton();
		hideButton.setAction(getHideAction());

		buttonArea.add(placeButton);
		buttonArea.add(hideButton);

		notice = new JLabel();		

		pinArea = new PinArea(width, height, render);
		add(pinArea,BorderLayout.NORTH);		
		add(buttonArea,BorderLayout.CENTER);
		add(notice,BorderLayout.SOUTH);

		active(false);
	}

	public void active(boolean val) {
		placeButton.setEnabled(val);
		hideButton.setEnabled(val);		
		pinArea.active(val);		
	}

	public Point doTurn(String notice) {
		
		latch = new CountDownLatch(1);

		setNotice(notice);
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

	public void setNotice(String s) {
		notice.setText(s);
	}

	private AbstractAction getPlaceAction() {
		if(placeAction == null) {
			placeAction = new AbstractAction("Place", null) {
				private static final long serialVersionUID = 2033526430007994507L;

				public void actionPerformed(ActionEvent evt) {					
					ArrayList<Point> pt = pinArea.getSelectedPins();
					if(pt.size() == 1) {
						placePoint = pt.get(0);
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
					for(Point p : pinArea.getSelectedPins())
						render.switchPin(p.x, p.y);
				}
			};
		}
		return hideAction;
	}



}
