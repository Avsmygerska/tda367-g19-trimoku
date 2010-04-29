package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.*;
import control.*;

public class ControlPanel extends JPanel{

	private static final long serialVersionUID = -8040537962044433652L;	
	
	Player p1 = new Player("p1",Color.RED);
	Player p2 = new Player("p2",Color.GREEN);
	Player active = p1;
	
	private GameLogic gl;
	
	private PinArea pinArea;
	private JPanel  buttonArea;
	
	private JButton placeButton;
	private JButton hideButton;

	private AbstractAction placeAction;
	private AbstractAction hideAction;
	
	private Render render;	
		
	public ControlPanel(int width, int height, Render render, GameLogic gl) {
		this.render = render;
		this.gl = gl;
		
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1};
		thisLayout.rowHeights = new int[] {7};
		thisLayout.columnWeights = new double[] {0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7};
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
		
		pinArea = new PinArea(width, height, render);
		add(pinArea,BorderLayout.NORTH);		
		add(buttonArea,BorderLayout.CENTER);
	}	
	
	private AbstractAction getPlaceAction() {
		if(placeAction == null) {
			placeAction = new AbstractAction("Place", null) {
				private static final long serialVersionUID = 2033526430007994507L;

				public void actionPerformed(ActionEvent evt) {					
					ArrayList<Point> pt = pinArea.getSelectedPins();
					if(pt.size() == 1) {
						System.out.println(pt.get(0).x + " " + pt.get(0).y);
						gl.place(pt.get(0).x, pt.get(0).y);
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
					for(Point p : pinArea.getSelectedPins()){
						System.out.println(p.x + " " + p.y);
						render.switchPin(p.x, p.y);
					}				
				}
			};
		}
		return hideAction;
	}
	
	
	
}
