package graphResolution;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

public class Graph {
	
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	
	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}
	
	public Graph(int numberOfNode) {
		//Build a graph with numberOfNode nodes and random value for each edges
		this();
		for(int i=0; i<numberOfNode; i++) nodes.add(new Node());
		edgesWithRandomValueCreator();
	}
	
	public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
		this.nodes=nodes;
		this.edges=edges;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	private void edgesWithRandomValueCreator() {
		//Build each edges of the graph, given them a random value between 1 and 10
		for(int i=0; i<nodes.size(); i++) {
			for(int j=0; j<nodes.size(); j++) {
				if(i==nodes.size()-1 && i==j) break;
				if(i==j) j++;
				int value = (int) ((Math.random()*10)+1);
				edges.add(new Edge(nodes.get(i), nodes.get(j), value));
//				System.out.println("start : "+i+" end : "+j+" value : "+value);
			}
		}
	}
	
	public int calcTotalValue(String[] namesOrder) {
		//Return the value of a given road wich is defined by namesoRDER
		int totalValue=0;
		for(int i=0; i<namesOrder.length-1; i++) {
			//For each edge unless the last and the first nodes
			String start=namesOrder[i];
			String end=namesOrder[i+1];
			Edge e = getEdgeNamed(start, end);
			totalValue+=e.getValue();
		}
		//We take care of the last edge
		String start=namesOrder[namesOrder.length-1];
		String end=namesOrder[0];
		Edge e = getEdgeNamed(start, end);
		totalValue+=e.getValue();
		return totalValue;
	}
	
	public double getMeanValue() {
		//Return the mean value of the edges multiply by the minimum number of edges needed to solve
		//the graph, passing by every node at least once
		double mean = 0;
		for(Edge e : edges) mean += e.getValue();
		mean = (double) mean/edges.size();
		mean = mean*(nodes.size()-1);
		return mean;
	}
	
	private Edge getEdgeNamed(String start, String end) {
		//return the edge between start-end or end-start because it is the same one
		for(Edge e : edges) {
			if(e.getStart().getName().equals(start) && e.getEnd().getName().equals(end)) return e;
			if(e.getStart().getName().equals(end) && e.getEnd().getName().equals(start)) return e;
		}
		throw new RuntimeException("This name does not exist : " + start +" "+ end);
	}
	
}
