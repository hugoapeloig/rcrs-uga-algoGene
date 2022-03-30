package graphResolution;

public class Edge {
	
	private Node startNode;
	private Node endNode;
	private int value;
	
	public Edge() {
		//Do not use him without recreate the edge after
	}
	
	public Edge(Node startNode, Node endNode, int value) {
		this.startNode=startNode;
		this.endNode=endNode;
		this.value=value;
	}
	
	public Node getStart() {
		return startNode;
	}

	public Node getEnd() {
		return endNode;
	}
	
	public int getValue() {
		return value;
	}
}
