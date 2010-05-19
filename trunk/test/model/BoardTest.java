package model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

public class BoardTest {


	@Test
	public void testPeek() {
		Board board = new Board(5);
		Player p = new Player("Player", Color.green);
		board.place(0, 0, p);
		assertTrue(board.peek(0, 0, 0) == p);
	}

	@Test
	public void testGetPin() {
		Board board = new Board(5);
		Player p = new Player("Player", Color.green);
		for(int i = 1; i < board.getLayers(); i++){
			board.place(0, 0, p);
			assertTrue(board.getPin(0, 0).size()== i);
		}

	}

	@Test
	public void testGetRows() {
		int dimension = 5;
		Board board = new Board(dimension);
		assertTrue(board.getRows() == dimension);
	}

	@Test
	public void testGetColumns() {
		int dimension = 5;
		Board board = new Board(dimension);
		assertTrue(board.getColumns() == dimension);
	}

	@Test
	public void testGetLayers() {
		int dimension = 5;
		Board board = new Board(dimension);
		assertTrue(board.getLayers() == dimension);
	}

	@Test
	public void testGetWinLength() {
		int dimension = 5;
		Board board = new Board(dimension);
		assertTrue(board.getWinLength() == dimension);
	}



	@Test
	public void testPlace() {
		int dimension = 5;
		Player p = new Player("Player", Color.green);
		Board board = new Board(dimension);
		for(int i = 0; i < board.getWinLength(); i++){
			board.place(i, i, p);
			assertTrue(board.peek(i, i, 0) == p);
		}
	}

	@Test
	public void testIsFull() {
		int dimension = 5;
		Board board = new Board(dimension);
		Player p = new Player("Player", Color.green);
		for(int lays = 0; lays < board.getLayers(); lays++){
			for (int row = 0; row < board.getRows(); row++) {
				for (int column = 0; column < board.getColumns(); column++) {
					board.place(row, column, p);
				}

			}
		}
		assertTrue(board.isFull() == true);
	}

	@Test
	public void testWin() {
		int dimension = 5;
		Board board = new Board(dimension);
		Player p = new Player("Player", Color.green);
		for (int row = 0; row < board.getRows(); row++) {
			board.place(row, 0, p);			
		}
		assertTrue(board.win(p)== true);
	}

	@Test
	public void testGetLastMove() {
		int dimension = 5;
		Board board = new Board(dimension);
		Player p = new Player("Player", Color.green);
		Point po = new Point(2,2);
		if(board.place(po.x, po.y, p)){
			Point pi = board.getLastMove();
			assertTrue(board.getLastMove().x == po.x && board.getLastMove().y == po.y);
		}
	}

}
