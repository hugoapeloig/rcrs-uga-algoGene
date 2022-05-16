package convertisseur_map_to_graph;

import java.util.ArrayList;
import java.util.Map;

import convertisseur_map_to_graph.maps.gml.GMLBuilding;
import convertisseur_map_to_graph.maps.gml.GMLMap;
import convertisseur_map_to_graph.maps.gml.GMLShape;
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
	public Convertisseur (GMLMap map, StandardWorldModel world){ //faudrait trouver a quoi correspond world car c'est nécessaire pour avoir la distance
		
		this.map = map;
		
		//Récupère la liste des batiments
		buildings = map.getListBuilding(); //getListBuilding ses moi qu'il l'ai rajouté dans GMLMap (a la fin)
		
		//Créer la list de nodes et la listes de edges
		
		this.createNodes();
		this.createEdges();
		
		
		//Créer le graph ici
		graph = new Graph(nodes,edges);
		
	}
	
	
	/*
	 retourne le graphique
	 */
	public Graph getGraph(){
		
		return this.graph;
		
	}
	
	
	/*
	 Créer les nodes du graph
	 En récupérant la liste des batiments on crée N (nombre de batiments) nodes avec l'id de chaque batiments
	 */
	public void createNodes(){
		
		int i =0;
		Node bat;
		
		while(i < buildings.size()){
			
			bat = new Node(i); //i représente l'id
			nodes.add(bat);
			i++;
		}
		
	}
	
	
	/*
	 Créer les nodes du graph
	 En récupérant la liste des batiments on crée une route reliant chaque batiment a un autre avec la distance les separant
	 */
	public void createEdges(){
		
		int i=0;
		Edge route;
		int distance;
		
		while(i < buildgins.size()){
			
			int j = 0;
			
			while(j < buildings.size()){
				
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
	
	
	public int distanceBatiment(GMLBuilding a, GMLBuilding b){
		
		double xa= a.getCentreX();
		double ya= a.getCentreY();
		
		double xb= b.getCentreX();
		double yb= b.getCentreY();
		
		int distance = math.sqrt(math.raw(xb-xa,2)-math.raw(yb-ya,2));
		
		return distance;
	}
	
	
}
