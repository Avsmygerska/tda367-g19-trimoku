package control;

import control.interfaces.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import model.Board;
import model.Player;

public class TidyAI extends User {

	PriorityQueue<Element<ArrayList<Point>>> pqueue;
	HashMap<Point,Integer> points;

	private int modifier;							// This is used to calculate the "where to put the next ball" score 
	private int lowerBound;							// Every score that's lower than this variable will not be included in the PriorityQueue 
	private int offensiveBonus;                 	// A small bonus for offensive moves    
	private int[] mods;								// Contains the score for 2-,3- & 4-inARow.

	public TidyAI(Player p) {
		super(p);
	}
	
	public void doTurn(Board b) {
		modifier = 10;	

		mods = new int[b.getWinLength()];			
		for(int i = 0; i < mods.length; i++)		
			mods[i] = (int)Math.pow(modifier,i);	// Calculates the score and put it into mods

		offensiveBonus = 5;
		lowerBound = 30;
		pqueue = new PriorityQueue<Element<ArrayList<Point>>>();
		points = new HashMap<Point,Integer>();
		HashMap<Integer,ArrayList<Point>> sortPoints = new HashMap<Integer,ArrayList<Point>>(); 

		vertical(b);
		leftToRight(b);
		frontToBack(b);
		innerDiagonals(b);

		for(Point p : points.keySet()) {
			int val = points.get(p);
			if (val > lowerBound) {
				if(sortPoints.containsKey(val)) {
					sortPoints.get(val).add(p);
				} else {
					ArrayList<Point> nl = new ArrayList<Point>();
					nl.add(p);
					sortPoints.put(val, nl);
				}
			}
		}

		for(Entry<Integer, ArrayList<Point>> ent : sortPoints.entrySet())
			pqueue.offer(new Element<ArrayList<Point>>(ent.getValue(),ent.getKey()));

		int row, col;

		while(true) {
			java.util.Random r = new java.util.Random();
			if(pqueue.isEmpty()) {
				row = r.nextInt(b.getRows());
				col = r.nextInt(b.getColumns());
			} else {
				ArrayList<Point> ap = pqueue.poll().getObject();
				Point p = ap.get(r.nextInt(ap.size()));
				row = p.x;
				col = p.y;
			}

			if(b.place(row, col, getPlayer())) {
				break;
			}
		}
	}
	
	
	private void vertical(Board board) {
		for(int row = 0; row < board.getRows(); row++)
			for(int col = 0; col < board.getColumns(); col++)
				updateState(new Point(row,col), score(board.getPin(row, col)));

		ArrayList<Player> pls = new ArrayList<Player>();		
		ArrayList<Point> empty = new ArrayList<Point>();		
		Player pl;	

		for(int lay = 0; lay < board.getLayers(); lay++) {		
			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++) {				
				if((pl = board.peek(step, step, lay)) == null) {
					if(lay != board.getPin(step, step).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(step,step));
				} else
					pls.add(pl);
			}

			int score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++){
				int pets = (board.getWinLength()-1)-step;

				if((pl = board.peek(pets, step, lay)) == null) {
					if(lay != board.getPin(pets, step).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(pets,step));
				} else
					pls.add(pl);

			}

			score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

		}
	}

	private void leftToRight(Board board) {
		ArrayList<Player> pls  = new ArrayList<Player>();
		ArrayList<Point> empty = new ArrayList<Point>();
		Player pl;
		for(int lay = 0; lay < board.getLayers(); lay++)
			for(int col = 0; col < board.getColumns(); col++) {
				pls.clear();
				empty.clear();
				for(int row = 0; row < board.getRows(); row++) {
					if((pl = board.peek(row, col, lay)) == null) {
						if(lay != board.getPin(row, col).size()) {
							empty.clear();
							break;
						}						
						empty.add(new Point(row,col));
					} else
						pls.add(pl);					
				}
				int score = score(pls);
				for(Point p : empty)
					updateState(p, score);				
			}

		for(int col = 0; col < board.getColumns(); col++) {		
			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++) {				
				if((pl = board.peek(step, col, step)) == null) {
					if(step != board.getPin(step, col).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(step,col));
				} else
					pls.add(pl);
			}

			int score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++){
				int pets = (board.getWinLength()-1)-step;

				if((pl = board.peek(pets, col, step)) == null) {
					if(step != board.getPin(pets, col).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(pets,col));
				} else
					pls.add(pl);

			}

			score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

		}

	}

	private void frontToBack(Board board) {
		ArrayList<Player> pls = new ArrayList<Player>();
		ArrayList<Point> empty = new ArrayList<Point>();
		Player pl;
		for(int lay = 0; lay < board.getLayers(); lay++)
			for(int row = 0; row < board.getRows(); row++) {
				pls.clear();
				empty.clear();
				for(int col = 0; col < board.getColumns(); col++) {
					if((pl = board.peek(row, col, lay)) == null) {
						if(lay != board.getPin(row, col).size()) {
							empty.clear();
							break;
						}
						empty.add(new Point(row,col));
					} else
						pls.add(pl);					
				}
				for(Point p : empty)
					updateState(p, score(pls));				
			}

		for(int row = 0; row < board.getRows(); row++) {
			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++) {				
				if((pl = board.peek(row, step, step)) == null) {
					if(step != board.getPin(row, step).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(row, step));
				} else
					pls.add(pl);
			}

			int score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

			pls.clear();		
			empty.clear();

			for(int step = 0; step < board.getWinLength(); step++){
				int pets = (board.getWinLength()-1)-step;

				if((pl = board.peek(row, pets, step)) == null) {
					if(step != board.getPin(row,pets).size()) {
						empty.clear();
						break;
					}
					empty.add(new Point(row,pets));
				} else
					pls.add(pl);

			}

			score = score(pls);
			if(score > lowerBound)
				for(Point p : empty)
					updateState(p,score);

		}

	}

	private void innerDiagonals(Board board) {
		ArrayList<Player> pls = new ArrayList<Player>();
		ArrayList<Point> empty = new ArrayList<Point>();		
		Player pl;
		int score;

		// Rows, columns and layers increasing.
		for(int step = 0; step < board.getWinLength(); step++) {
			if((pl = board.peek(step, step, step)) == null){
				if(step != board.getPin(step, step).size()) {
					empty.clear();
					break;
				}
				empty.add(new Point(step,step));
			} else
				pls.add(pl);
		}

		score = score(pls);
		if(score > lowerBound)
			for(Point p : empty)
				updateState(p,score);

		pls.clear();
		empty.clear();		
		// ----------------------------------------------

		// Increasing rows and columns, decreasing layers.
		for(int step = 0; step < board.getWinLength(); step++) {
			int pets = board.getWinLength()-1-step;
			if((pl = board.peek(step, step, pets)) == null){
				if(pets != board.getPin(step, step).size()) {
					empty.clear();
					break;
				}
				empty.add(new Point(step,step));
			} else
				pls.add(pl);
		}		

		score = score(pls);
		if(score > lowerBound)
			for(Point p : empty)
				updateState(p,score);

		pls.clear();
		empty.clear();
		// ----------------------------------------------

		// Decreasing rows, increasing columns and layers
		for(int step = 0; step < board.getWinLength(); step++) {
			int pets = board.getWinLength()-1-step;
			if((pl = board.peek(pets, step, step)) == null){
				if(step != board.getPin(pets, step).size()) {
					empty.clear();
					break;
				}
				empty.add(new Point(pets,step));
			} else
				pls.add(pl);
		}

		score = score(pls);
		if(score > lowerBound)
			for(Point p : empty)
				updateState(p,score);

		pls.clear();
		empty.clear();
		// ----------------------------------------------

		// Decreasing rows and layers, increasing columns.
		for(int step = 0; step < board.getWinLength(); step++) {
			int pets = board.getWinLength()-1-step; 
			if((pl = board.peek(pets, step, pets)) == null){
				if(pets != board.getPin(pets, step).size()) {
					empty.clear();
					break;
				}
				empty.add(new Point(pets,step));
			} else
				pls.add(pl);
		}

		score = score(pls);
		if(score > lowerBound)
			for(Point p : empty)
				updateState(p,score);

		pls.clear();
		empty.clear();
		// ----------------------------------------------		
	}


	private void updateState(Point p, int val) {
		int score = 0;
		if(points.get(p) != null)
			score = points.get(p);
		points.put(p, score+val);
	}

	private boolean onePlayer(ArrayList<Player> pl) {
		if(pl.size() == 0)
			return false;
		Player last = pl.get(0);
		for(Player p : pl)
			if(!p.equals(last))
				return false;
		return true;
	}

	private int score(ArrayList<Player> pl) {
		if(!onePlayer(pl))
			return 0; // Added bonus of ensuring that there is at least one piece.

		int result = mods[pl.size()];
		Player last = pl.get(0);

		if(last.equals(getPlayer()))
			result += offensiveBonus;

		return result;
	}

	public String getNotice() {
		return "Please wait. The AI is working.";
	}

	public void disconnect() {}

}

