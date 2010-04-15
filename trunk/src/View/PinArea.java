package View;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

public class PinArea extends JPanel{
	
	private ArrayList<ArrayList<JCheckBox>> Boxes;
	
	public PinArea() {
		super();
		
		// Layout stuff
		GridLayout PinAreaLayout = new GridLayout(5, 5, 1, 1);
		PinAreaLayout.setColumns(1);
		setLayout(PinAreaLayout);		
		setMaximumSize(new Dimension(90,90));
		
	    // Add the check boxes to the panel and the storing data structure
		Boxes = new ArrayList<ArrayList<JCheckBox>>();
		JCheckBox t;
		ArrayList<JCheckBox> tmp;
		for(int i = 0; i < 5; i++) {
			tmp = new ArrayList<JCheckBox>(); 
			for(int j = 0; j < 5; j++) {
				t = new JCheckBox();
				add(t);
				tmp.add(t);				
			}
			Boxes.add(tmp);
		}
	}
	
	public JCheckBox getPin(int i, int j){
		return Boxes.get(i).get(j);
	}
}