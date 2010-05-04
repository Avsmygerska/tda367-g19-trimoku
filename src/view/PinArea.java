package view;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
	private Render render;

	int maxX, maxY;
	
	public PinArea(Render render) {
		this.render = render;
		
		bxs = new JPanel();
		xButtons = new JPanel();
		yButtons = new JPanel();
		marker = new MarkerPanel();

		// Layout stuff
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.rowHeights = new int[] {7, 7, 7, 7};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7, 7, 7};
		setLayout(thisLayout);

		add(marker, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		BoxLayout yButtonsLayout = new BoxLayout(yButtons, javax.swing.BoxLayout.X_AXIS);
		yButtons.setLayout(yButtonsLayout);
		add(yButtons, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));		

		BoxLayout xPanelLayout = new BoxLayout(xButtons, javax.swing.BoxLayout.Y_AXIS);
		xButtons.setLayout(xPanelLayout);
		add(xButtons, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		add(bxs, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		
		
	}
	public PinArea(int x, int y, Render render) {
		this.render = render;
		maxX = x;
		maxY = y;

		bxs = new JPanel();
		xButtons = new JPanel();
		yButtons = new JPanel();
		marker = new MarkerPanel();

		// Layout stuff
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.rowHeights = new int[] {7, 7, 7, 7};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7, 7, 7};
		setLayout(thisLayout);

		add(marker, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		BoxLayout yButtonsLayout = new BoxLayout(yButtons, javax.swing.BoxLayout.X_AXIS);
		yButtons.setLayout(yButtonsLayout);
		add(yButtons, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));		

		BoxLayout xPanelLayout = new BoxLayout(xButtons, javax.swing.BoxLayout.Y_AXIS);
		xButtons.setLayout(xPanelLayout);
		add(xButtons, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		GridLayout bxsLayout = new GridLayout(x, y);
		bxs.setLayout(bxsLayout);
		add(bxs, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		constructButtons(x,y);
	}

	public void constructButtons(int x, int y) {
		xButtons.removeAll();
		yButtons.removeAll();
		bxs.removeAll();
		GridLayout bxsLayout = new GridLayout(x, y);
		bxs.setLayout(bxsLayout);
				
		maxX = x;
		maxY = y;
		
		
		// Add the check boxes to the panel and the storing data structure
		Boxes = new ArrayList<ArrayList<JCheckBox>>();
		JCheckBox t;
		ArrayList<JCheckBox> tmp;
		for(int i = 0; i < maxX; i++) {			
			tmp = new ArrayList<JCheckBox>(); 
			for(int j = 0; j < maxY; j++) {
				t = new JCheckBox();
				bxs.add(t);
				tmp.add(t);				
			}
			Boxes.add(tmp);
		}

		// Add hider buttons for each row and column.

		JButton btn;
		for(int i = 0; i < maxX; i++) {
			btn = new JButton();			
			btn.setMargin(new Insets(1,1,1,1));
			btn.addActionListener(new Hider(btn,false,i));			
			xButtons.add(btn);	
		}

		for(int i = 0; i < maxY; i++) {
			btn = new JButton();
			btn.setMargin(new Insets(1,1,1,1));
			btn.addActionListener(new Hider(btn,true,i));
			yButtons.add(btn);
		}
		
	}

	public JCheckBox getPin(int i, int j){
		return Boxes.get(i).get(j);
	}

	public ArrayList<Point> getSelectedPins () {
		ArrayList<Point> pts = new ArrayList<Point>();

		for(int i = 0; i < maxX; i++)
			for(int j = 0; j < maxY; j++)
				if(getPin(i,j).isSelected()) {
					pts.add(new Point(j,i)); // Reversed order, since that's how the layout works.
					getPin(i,j).setSelected(false);
				}

		return pts;
	}


	public void active(boolean val) {
		for (Component c : xButtons.getComponents())
			c.setEnabled(val);

		for (Component c : yButtons.getComponents())
			c.setEnabled(val);

		for (Component c : bxs.getComponents())
			c.setEnabled(val);
	}

	class MarkerPanel extends JPanel {
		private static final long serialVersionUID = -467353064175378414L;

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 25, 25);
		}
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
				render.setRow(number, val);
			else
				render.setCol(number, val);		
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
