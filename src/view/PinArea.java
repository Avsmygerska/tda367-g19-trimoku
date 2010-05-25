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
	private JCheckBox[][] boxes;

	ArrayList<Hider> rowHiders;
	ArrayList<Hider> colHiders;

	private JPanel bxs;
	private JPanel rowButtons;
	private JPanel colButtons;
	private JPanel marker;
	private Render render;

	private Icon verticalShow;
	private Icon verticalHide;
	private Icon horisontalShow;
	private Icon horisontalHide;	

	int rows, cols;

	public PinArea( Render render,int rows, int cols) {
		this.render = render;

		bxs = new JPanel();
		rowButtons = new JPanel();
		colButtons = new JPanel();
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

		BoxLayout yButtonsLayout = new BoxLayout(colButtons, javax.swing.BoxLayout.LINE_AXIS);
		colButtons.setLayout(yButtonsLayout);
		add(colButtons, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));		

		BoxLayout xPanelLayout = new BoxLayout(rowButtons, javax.swing.BoxLayout.PAGE_AXIS);
		rowButtons.setLayout(xPanelLayout);
		add(rowButtons, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		GridLayout bxsLayout = new GridLayout(rows, cols);
		bxs.setLayout(bxsLayout);
		add(bxs, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		verticalHide   = buildIcon("Hide",true);
		verticalShow   = buildIcon("Show",true);		
		horisontalHide = buildIcon("Hide",false);
		horisontalShow = buildIcon("Show",false);

		constructButtons(rows,cols);
	}

	private Icon buildIcon(String t,boolean vertical) {
		Font f = getFont ();
		FontMetrics fm = getFontMetrics (f);
		int captionHeight = fm.getHeight ();
		int captionWidth = fm.stringWidth (t);
		BufferedImage bi; 
		if(vertical)
			bi = new BufferedImage (captionHeight, captionWidth, BufferedImage.TYPE_INT_ARGB);
		else
			bi = new BufferedImage (captionWidth + 4, captionHeight + 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics ();

		g.setColor (new Color (0, 0, 0, 0)); // transparent
		g.fillRect (0, 0, bi.getWidth (), bi.getHeight ());

		g.setColor (getForeground ());
		g.setFont (f);
		g.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if(vertical){
			g.rotate(Math.PI / 2);
			g.drawString (t,0,-2);
		} else {
			g.drawString(t, 2, (bi.getHeight()+captionHeight)/2-2);
		}
		return new ImageIcon(bi);
	}

	public void constructButtons(int rows, int cols) {
		rowButtons.removeAll();
		colButtons.removeAll();
		bxs.removeAll();
		GridLayout bxsLayout = new GridLayout(rows, cols);
		bxs.setLayout(bxsLayout);

		this.rows = rows;
		this.cols = cols;

		// Add the check boxes to the panel and the storing data structure
		boxes = new JCheckBox[rows][cols];
		JCheckBox t;

		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				t = new JCheckBox();
				t.setAction(new Marker(t,row,col));
				boxes[row][col] = t;
				bxs.add(t);
			}			
		}

		// Add hider buttons for each row and column.	
		JButton btn;
		Hider h;		

		// Row-hiding buttons.
		rowHiders = new ArrayList<Hider>();
		for(int i = 0; i < rows; i++) {			
			btn = new JButton();
			btn.setPreferredSize(new java.awt.Dimension(37,23));
			btn.setFocusable(false);
			h = new Hider(btn,false,i);
			rowHiders.add(h);
			btn.addActionListener(h);			
			rowButtons.add(btn);
		}

		// Column-hiding buttons
		colHiders = new ArrayList<Hider>();
		for(int i = 0; i < cols; i++) {
			btn = new JButton();
			btn.setPreferredSize(new java.awt.Dimension(23,37));
			btn.setFocusable(false);
			h = new Hider(btn,true,i);
			colHiders.add(h);
			btn.addActionListener(h);
			colButtons.add(btn);
		}
	}

	public JCheckBox getPin(int row, int col){
		if(row >= rows || col >= cols)
			return null;
		return boxes[row][col];
	}

	public ArrayList<Point> getSelectedPins () {
		ArrayList<Point> pts = new ArrayList<Point>();

		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				if(getPin(row,col).isSelected()) {
					pts.add(new Point(row,col));					
					getPin(row,col).setSelected(false);
				}
		render.markPins(false);
		return pts;
	}

	public void showAllPins() {
		for(Hider c : rowHiders)
			c.hide(true);

		for(Hider c : colHiders)
			c.hide(true);
	}

	public void active(boolean val) {
		for (Component c : rowButtons.getComponents())
			if(c != null)
				c.setEnabled(val);

		for (Component c : colButtons.getComponents())
			if(c != null)
				c.setEnabled(val);

		for (Component c : bxs.getComponents())
			if(c != null)
				c.setEnabled(val);
	}

	static class MarkerPanel extends JPanel {
		private static final long serialVersionUID = -467353064175378414L;

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 25, 25);
		}
	}

	class Marker extends AbstractAction{
		private static final long serialVersionUID = -2605860486344728631L;
		private JCheckBox bx;
		private int row, col;

		public Marker(JCheckBox bx, int row, int col){
			this.bx = bx;
			this.row = row;
			this.col = col;			
		}

		public void actionPerformed(ActionEvent arg0) {
			render.markPin(row, col, bx.isSelected());
		}
	}

	class Hider extends AbstractAction{

		private static final long serialVersionUID = -6172890423930729396L;
		private JButton button;
		private boolean vertical;
		private boolean val;
		private int number;

		public Hider(JButton button, boolean vertical, int number) {
			this.button = button;
			this.vertical = vertical;
			this.number = number;

			hide(true);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			hide(!val);			

			if(vertical) {
				render.setCol(number, val);
				for(int i = 0; i < rows;i++)
					updateRowHider(i, render.visibleRow(i));
			} else {
				render.setRow(number, val);
				for(int i = 0; i < cols;i++)
					updateColHider(i, render.visibleCol(i));				
			}
		}

		public void hide(boolean value){
			val = value;

			Icon ic;
			if(vertical && val)
				ic = verticalHide;
			else if(vertical)
				ic = verticalShow;
			else if(val)
				ic = horisontalHide;
			else
				ic = horisontalShow;

			button.setIcon(ic);		
		}
	}

	public void updateColHider(int no, boolean vis){ colHiders.get(no).hide(vis); }

	public void updateRowHider(int no, boolean vis){ rowHiders.get(no).hide(vis); }
}
