package model;

import java.awt.Color;

public class Player {

	private String name;
	private Color color;
	
	public Player(String name, int R, int G, int B) {
		this.name = name;
		this.color = new Color(R,G,B);	
	}
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;		
	}
	
	public float getRed() {
		if(color.getRed() == 0)
			return 0f;		
		return 255f / color.getRed();
	}
	public float getGreen() {
		if(color.getGreen() == 0)
			return 0f;		
		return 255f / color.getGreen();
	}
	public float getBlue() {
		if(color.getBlue() == 0)
			return 0f;		
		return 255f / color.getBlue();
	}
	
	public boolean equals(Object o) {
		if(o.getClass().equals(model.Player.class)) {
			Player p = (Player) o;
			return p.getName().equals(name) && p.getColor().equals(color);
		}
		return false;
	}
}
