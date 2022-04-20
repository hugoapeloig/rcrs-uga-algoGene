package graphResolution;

import java.util.ArrayList;

public class Constants {
	
	//CONSTANTS
	public static final double MUTATIONPROBABILITY = 0.04;
	
	public static final int POPULATIONSIZE = 100;
	
	public static final int NUMBEROFBUILDINGS = 40;
	public static final int NUMBEROFGENERATIONS = 1000;
	
	//FUNCTION
	
	public static int GETANONALREADYCHOOSENNUMBER(ArrayList<Integer> list) {
		//Return a number that isn't in the list given in parameter and wich is between 1 and NUMBEROFBUILDINGS
		boolean in = false;
		int theNumber = (int) (Math.random()*NUMBEROFBUILDINGS-1)+1;//0 can't be taken
		for(Integer i : list) if (theNumber == i) in=true;
		while(in) {
			in=false;
			theNumber = (int) (Math.random()*NUMBEROFBUILDINGS-1)+1;
			for(Integer i : list) if (theNumber == i) in=true;
		}
		return theNumber;
	}
	
	public static int FACTORIAL(int i) {
		//Return the factorial of i
		for(int j=i-1; j>0; j--) i = j*i;
		return i;
	}
	
	
	//GRAPH CONSTANTS
	public static final Graph THEGRAPHTEST = GRAPHCREATOR();
	
	private static Graph GRAPHCREATOR() {
		//Create a graph with NUMBEROFBUILDINGS nodes and random edges
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		//we create each nodes of the graph
		String[] names = new String[NUMBEROFBUILDINGS];
		for(int i=0; i<names.length; i++) names[i] = ""+i;
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
