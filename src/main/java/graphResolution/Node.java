package graphResolution;

public class Node {
	
	private int id;
	private String name;
	
	public Node() {
		id=0;
		name = "";
	}
	
	public Node(int id) {
		this.id=id;
		name = "";
	}
	
	public Node(String name) {
		id=0;
		this.name=name;
	}
	
	public Node(int id, String name) {
		this.id=id;
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
