import Model.Board;
import Model.Player;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b = new Board(2,2,2);
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		System.out.println("1: " + b.place(2, 1, p1));
		System.out.println("1: " + b.place(0, 2, p2));
		
		System.out.println("1: " + b.place(0, 1, p1));
		System.out.println("1: " + b.place(2, 1, p2));
		
		System.out.println("1: " + b.place(0, 1, p1));
		System.out.println("1: " + b.place(2, 1, p2));
		
		System.out.println("1: " + b.place(0, 1, p1));
		System.out.println("1: " + b.place(2, 1, p2));
		
		System.out.println("1: " + b.place(0, 1, p1));
		
		
		System.out.println(b.peek(2, 1, 0).getName() + " " + b.peek(2, 1, 1).getName() + " " + b.peek(2, 1, 2).getName());
		
		System.out.println(b.win(p1));
		System.out.println(b.win(p2));
		
		System.exit(0);
	}

}
