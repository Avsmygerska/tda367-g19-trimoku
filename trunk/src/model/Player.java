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
		return color.getRed()/255f;
	}
	
	public float getGreen() {
		return color.getGreen() / 255f;
	}
	
	public float getBlue() {
		return color.getBlue() / 255F;
	}
	
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass().equals(this.getClass())) {
			Player p = (Player) o;
			return p.getName().equals(name) && p.getColor().equals(color);
		}
		return false;
	}
	
	public int hashCode() {
		assert false;
		return 4343;
	}
}
