package model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testGetName() {
		String str = "Player";
		Player p = new Player(str, Color.green);
		assertTrue(str.equals(p.getName()));
	}

	@Test
	public void testGetColor() {
		Color color = Color.green;
		Player p = new Player("Player", color);
		assertTrue(color.equals(p.getColor()));
	}

	@Test
	public void testEqualsObject() {
		Color color = Color.green;
		Player p = new Player("Player1", color);
		assertTrue(p.equals(p));
	}

}
