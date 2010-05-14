package control;

import control.interfaces.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import model.Board;
import model.Player;

public class TidyAI extends User {

	PriorityQueue<Element<Point>> pqueue;

	public TidyAI(Player p) {
		super(p);
		
	}

	public void doTurn(Board b) {
		pqueue = new PriorityQueue<Element<Point>>();
		
	}
	
	
	public ArrayList<ArrayList<Player>> getNeighbors(Board board, Point point){
		ArrayList<ArrayList<Player>> arr = new ArrayList<ArrayList<Player>>();
		
		int x = point.x;
		int y = point.y;
		int z = board.getPin(point.x, point.y).size();
		
		ArrayList<Player> tmp = new ArrayList<Player>();		
		for(int row = 0; row < board.getRows(); row++) {
			Player pl;
			if((pl = board.peek(row, y, z)) != null)
				tmp.add(pl);
		}		
		
		Player last = null;
		boolean disregard = false;
		for(Player p : tmp)
			if(!p.equals(last)) {
				disregard = true;
				break;				
			}
		
		if(!disregard)
			arr.add(tmp);			
		
		return arr;
	}
	
	public String getNotice() {
		return "Please wait. The AI is working.";
	}
	
	public void disconnect() {}

}

