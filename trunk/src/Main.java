import java.util.ArrayList;

import model.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b = new Board(3,3,3);
		/*
		Board b = new Board(2,2,2);

		Player p1 = new Player("p1",255,0,0);
		Player p2 = new Player("p2",0,255,0);
		
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		queue.add(1);
		queue.add(4);
		queue.add(2);
		
		System.out.println(queue.element());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		//System.out.println(queue.);
		/*System.out.println("1: " + b.place(0, 0, p1));
		System.out.println("1: " + b.place(0, 1, p2));
		
		System.out.println("1: " + b.place(0, 1, p1));
		System.out.println("1: " + b.place(0, 2, p2));
		
		System.out.println("1: " + b.place(0, 2, p1));
		System.out.println("1: " + b.place(1, 0, p2));
		
		System.out.println("1: " + b.place(0, 2, p1));
		System.out.println("1: " + b.place2(3, 0, p2));
		
		System.out.println("1: " + b.place2(4, 0, p1));
		System.out.println("1: " + b.place2(3, 0, p2));
		
		System.out.println("1: " + b.place2(4, 0, p1));
		System.out.println("1: " + b.place2(0, 4, p2));
		
		System.out.println("1: " + b.place2(0, 4, p1));
		System.out.println("1: " + b.place2(0, 4, p2));

		System.out.println("1: " + b.place2(0, 4, p1));
		
		//System.out.println(b.peek2(2, 1, 0).getName() + " " + b.peek2(2, 1, 1).getName() + " " + b.peek2(2, 1, 2).getName());
		
		System.out.println(b.win(p1));
		System.out.println(b.win(p2));
		*/
		
		ArrayList<Integer> ints = new ArrayList<Integer>();
		int active = 0;
		ints.add(0);
		ints.add(1);
		ints.add(2);
		
		for(int i = 0; i < 6; i++) {
			System.out.println(ints.get(active));
			active = (active+1)%ints.size();			
		}
		
		System.exit(0);
	}

}
