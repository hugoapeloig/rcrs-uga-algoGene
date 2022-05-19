package convertisseur_map_to_graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import rescuecore2.standard.entities.StandardWorldModel;

import maps.gml.GMLMap;
import maps.gml.GMLBuilding;
import graphResolution.Edge;
import graphResolution.Graph;
import graphResolution.Node;

public class Convertisseur {
	
	private GMLMap map;
	private Graph graph;
	
	private Map<Integer, GMLBuilding> buildings;
	
	private Set<Integer> listeID;
	private int[] tabID;
	
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	
	
	/*
	 Constructeur
	 @param Une map en format GML
	 Permet de creer un graph a l aide de la liste de batiments et des classes
	 node et edge;
	 */
	public Convertisseur (GMLMap map){ 
		
		this.map = map;
		
		//Recupere la liste des batiments
		buildings = map.getListBuilding(); //getListBuilding ses moi qu il l ai rajoute dans GMLMap (a la fin)
		
		//Recupere les ids des batiments et les transfere dans un tableau de int
		
		listeID=buildings.keySet();
		tabID = listeID.toArray();
		
		//Creer la list de nodes et la listes de edges
		
		this.createNodes();
		this.createEdges();
		
		
		//Creer le graph ici
		graph = new Graph(nodes,edges);
		
	}
	
	
	/*
	 retourne le graphique
	 */
	public Graph getGraph(){
		
		return this.graph;
		
	}
	
	
	/*
	 Creer les nodes du graph
	 En recuperant la liste des batiments on cree N (nombre de batiments) nodes avec l id de chaque batiments
	 */
	public void createNodes(){
		
		int i =0;
		Node bat;
		
		while(i < buildings.size()){
			
			int id = tabID[i];
			
			bat = new Node(id);
			nodes.add(bat);
			i++;
		}
		
	}
	
	
	/*
	 Creer les nodes du graph
	 En recuperant la liste des batiments on cree une route reliant chaque batiment a un autre avec la distance les separant
	 */
	public void createEdges(){
		
		int i=0;
		Edge route;
		int distance;
		
		while(i < buildings.size()){
			
			int j = 0;
			
			while(j < buildings.size()){
				
				GMLBuilding bat1 = buildings.get(i);
				GMLBuilding bat2 = buildings.get(j);
				
				distance = distanceBatiment(bat1,bat2);
				
				Node start = new Node(); Node end = new Node();
				for(Node n : nodes) {
					if(bat1.getID() == n.getID()) start = n;
					if(bat2.getID() == n.getID()) end = n;
				}
				route = new Edge(start,end,distance);
				edges.add(route);
				j++;
			}	
			i++;
		}
	}
	
	
	public int distanceBatiment(GMLBuilding a, GMLBuilding b){
		
		double xa= a.getCentreX();
		double ya= a.getCentreY();
		
		double xb= b.getCentreX();
		double yb= b.getCentreY();
		
		int distance = (int)Math.sqrt(Math.pow(xb-xa,2)-Math.pow(yb-ya,2));
		
		return distance;
	}
	
	
}
