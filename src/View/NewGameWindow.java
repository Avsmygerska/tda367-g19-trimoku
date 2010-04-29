package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class NewGameWindow extends JPanel {
	
	
	public NewGameWindow(){
		super();
		
		GridLayout newGameLayout = new GridLayout(3, 1);
		GridLayout hotSeatLayout = new GridLayout(2, 2);
		
		
		
		setMaximumSize(new Dimension(600,600));
		System.out.println("hejhej");
		JLabel label = new JLabel();
		JButton button = new JButton("pewpew");
		
		add(button);
		
	}
	
}


