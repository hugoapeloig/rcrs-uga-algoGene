package convertisseur_map_to_graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import convertisseur_map_to_graph.maps.gml.GMLBuilding;
import convertisseur_map_to_graph.maps.gml.GMLMap;
import convertisseur_map_to_graph.maps.gml.GMLRoad;
import graphResolution.Edge;
import graphResolution.Graph;
import graphResolution.Node;


public class Convertisseur {
	
	private GMLMap map;
	private Graph graph;
	
	private Map<Integer, GMLBuilding> buildings;
	
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	
	/*
	 Constructeur
	 @param Une map en format GML
	 Permet de créer un graph à l'aide de la liste de batiments et des classes
	 node et edge;
	 */
	public Convertisseur (GMLMap map){
		
		this.map = map;
		
		//Récupère la liste des batiments
		buildings = map.getListBuilding(); //getListBuilding c'est moi qu'il l'ai rajouté dans GMLMap (a la fin)
		
		//Créer la list de nodes et la listes de edges
		
		this.createNodes();
		this.createEdges();
		
		
		//Créer le graph ici
		graph = new Graph(nodes,edges);
		
	}
	
	
	public Graph getGraph(){
		
		return this.graph;
		
	}
	
	
	public void createNodes(){
		
		int i =0;
		Node bat;
		
		while(i < buildings.size()){
			
			bat = new Node(i); //i représente l'id
			nodes.add(bat);
			i++;
		}
		
	}
	
	
	public void createEdges(){
		
		int i=0;
		Edge route;
		int distance;
		
		while(i < buildgins.size()){
			
			int j = i;
			
			while(j < buildings.size()-1){
				
				bat1 = buildings.get(i);
				bat2 = buidlings.get(j)
				
				distance = distanceBatiment(bat1,bat2);
						
				route = new Edge(bat1,bat2,distance);
				edges.add(route);
				j++;
			}	
			i++;
		}
	}
	
	
	public int distanceBatiment(GMLbuilding a, GMLbuilding b){
		
		return 0
	}
	
	
}
