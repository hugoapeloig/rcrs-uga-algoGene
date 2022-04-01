package graphResolution;

import java.util.ArrayList;

public class Constants {
	
	//CONSTANTS
	public static final double MUTATIONPROBABILITY = 0.04;
	
	public static final int POPULATIONSIZE = 100;
	
	public static final int NUMBEROFBUILDINGS = 10;
	public static final int NUMBEROFGENERATIONS = 1000;
	
	//FUNCTION
	
	public static int GETANONALREADYCHOSENNUMBER(ArrayList<Integer> list) {
		boolean in = false;
		int theNumber = (int) (Math.random()*NUMBEROFBUILDINGS);
		for(Integer i : list) if (theNumber == i) in=true;
		while(in) {
			in=false;
			theNumber = (int) (Math.random()*NUMBEROFBUILDINGS);
			for(Integer i : list) if (theNumber == i) in=true;
		}
		return theNumber;
	}
	
	//GRAPH CONSTANTS
	public static final Graph THEGRAPHTEST = GRAPHCREATOR();
	
	private static Graph GRAPHCREATOR() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		//we create each nodes of the graph
		String[] names = {"0","1","2","3","4","5","6","7","8","9"};
		for(String name : names) nodes.add(new Node(name));
		//for each couples of nodes we assign them a edge's value
		//AB=1;AC=3;AE=2;AD=5;BC=1;BD=2;BE=1;CD=5;CE=3;ED=1;
		
		for(int i=0; i<nodes.size(); i++) {
			for(int j=nodes.size()-1; j>i; j--) {
				int value = (int) ((Math.random()*10)+1);
				edges.add(new Edge(nodes.get(i), nodes.get(j), value));
				System.out.println("start : "+nodes.get(i).getName()+" end : "+nodes.get(j).getName()+" value : "+value);
			}
		}
		Graph g = new Graph(nodes,edges);
		return g;
	}
}