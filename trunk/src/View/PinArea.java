package View;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PinArea extends JPanel{

	private static final long serialVersionUID = 9109603426210093692L;
	private ArrayList<ArrayList<JCheckBox>> Boxes;

	private JPanel bxs;
	private JPanel xButtons;
	private JPanel yButtons;
	private JPanel marker;
	private Render r;

	public PinArea(int x, int y, Render r) {
		this.r = r;
		bxs = new JPanel();
		xButtons = new JPanel();
		yButtons = new JPanel();

		// Layout stuff
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.rowHeights = new int[] {7, 7, 7, 7};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7, 7, 7};
		this.setLayout(thisLayout);

		yButtons = new JPanel();
		BoxLayout yButtonsLayout = new BoxLayout(yButtons, javax.swing.BoxLayout.X_AXIS);
		this.add(yButtons, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		yButtons.setLayout(yButtonsLayout);

		xButtons = new JPanel();
		BoxLayout xPanelLayout = new BoxLayout(xButtons, javax.swing.BoxLayout.Y_AXIS);
		this.add(xButtons, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		xButtons.setLayout(xPanelLayout);

		marker = new JPanel();
		this.add(marker, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		GridLayout bxsLayout = new GridLayout(x, y, 1, 1);		
		bxsLayout.setColumns(1);		
		bxs.setLayout(bxsLayout);
		this.add(bxs, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


		// Add the check boxes to the panel and the storing data structure
		Boxes = new ArrayList<ArrayList<JCheckBox>>();
		JCheckBox t;
		ArrayList<JCheckBox> tmp;
		for(int i = 0; i < x; i++) {			
			tmp = new ArrayList<JCheckBox>(); 
			for(int j = 0; j < y; j++) {
				t = new JCheckBox();
				bxs.add(t);
				tmp.add(t);				
			}
			Boxes.add(tmp);
		}

		JButton raj;
		for(int i = 0; i < x; i++) {
			raj = new JButton();			
			raj.setMargin(new Insets(1,1,1,1));
			raj.addActionListener(new Hider(raj,false,i));			
			xButtons.add(raj);	
		}

		for(int i = 0; i < y; i++) {
			raj = new JButton();
			raj.setMargin(new Insets(1,1,1,1));
			raj.addActionListener(new Hider(raj,true,i));
			yButtons.add(raj);
		}

	}

	public JCheckBox getPin(int i, int j){
		return Boxes.get(i).get(j);
	}

	public ArrayList<Point> getSelectedPins () {
		ArrayList<Point> pts = new ArrayList<Point>();

		for(int i = 0; i < 5; i++)		 
			for(int j = 0; j < 5; j++)
				if(getPin(i,j).isSelected()) {
					pts.add(new Point(i,j));
					getPin(i,j).setSelected(false);
				}

		return pts;
	}

	class Hider extends AbstractAction{

		private static final long serialVersionUID = -6172890423930729396L;
		JButton button;
		boolean vertical;
		boolean val;
		int number;

		public Hider(JButton button, boolean vertical, int number) {
			this.button = button;
			this.vertical = vertical;
			this.number = number;

			val = true;
			setText("Hide");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {			
			if(val = !val)
				setText("Hide");
			else 
				setText("Show");			

			if(vertical)
				r.setRow(number, val);
			else
				r.setCol(number, val);		
		}

		private void setText(String t) {
			if(vertical) {
				Font f = getFont ();
				FontMetrics fm = getFontMetrics (f);
				int captionHeight = fm.getHeight ();
				int captionWidth = fm.stringWidth (t);
				BufferedImage bi = new BufferedImage (captionHeight + 4,
						captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) bi.getGraphics ();

				g.setColor (new Color (0, 0, 0, 0)); // transparent
				g.fillRect (0, 0, bi.getWidth (), bi.getHeight ());

				g.setColor (getForeground ());
				g.setFont (f);
				g.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				g.rotate (Math.PI / 2);
				g.drawString (t, 2, -6);
				Icon icon = new ImageIcon (bi);
				button.setIcon (icon);
				button.setActionCommand (t);
			}else {
				button.setText(t);
			}

		}
	}
}
