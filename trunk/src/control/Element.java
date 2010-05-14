package control;

public class Element<A> implements Comparable<Element<A>> {
	private A object;
	private int value;

	public Element(A object, int value) {
		this.object = object;
		this.value = value;
	}
	
	public int getValue() { return value; }
	public A getObject() { return object; }

	@Override
	public int compareTo(Element<A> other) {
		return new Integer(value).compareTo(new Integer(other.getValue()));
	}
}
