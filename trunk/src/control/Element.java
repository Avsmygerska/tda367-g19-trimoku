package control;

public class Element<A> implements Comparable<Element<A>> {
	private A object;
	private int value;

	public Element(A object, int value) {
		this.object = object;
		this.value = value;
	}
	
	public int getValue() { return value; }
	public A getObject()  { return object; }

	// Reversed comparison.
	public int compareTo(Element<A> other) {
		return ((Integer)other.getValue()).compareTo(value);
	}
	
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if((getClass().equals(o.getClass()))){
			Element<A> e = (Element<A>)o;
			return e.getValue() == value && e.getObject().equals(object);
		}
		return false;
	}
	
	public int hashCode() {
		assert false;
		return 234234;
	}
}
