package graphResolution;

public class Node {
	
	private int abs, ord;
	private String name;
	
	public Node() {
		abs=0;
		ord=0;
		name = "";
	}
	
	public Node(int abs, int ord) {
		this.abs=abs;
		this.ord=ord;
		name = "";
	}
	
	public Node(String name) {
		abs=0;
		ord=0;
		this.name=name;
	}
	
	public Node(int abs, int ord, String name) {
		this.abs = abs;
		this.ord = ord;
		this.name = name;
	}
	
	public int getAbs() {
		return abs;
	}
	
	public int getOrd() {
		return ord;
	}
	
	public String getName() {
		return name;
	}
}
