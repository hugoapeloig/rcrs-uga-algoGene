package convertisseur_map_to_graph;

import java.util.ArrayList;
import graphResolution.Edge;
import graphResolution.Node;

/**
 * Class qui représente un Graph mais à la sauce nono
 * Elle est utiliser comme intermédiaire dans la Class Convertisseur
 * @author noegr
 */
public class Graph_nono {
	
	private ArrayList<Node> noeuds;
	
	private ArrayList<Edge> chemins;
	
	
	
	public Graph_nono (Map carte_1) {
		//TODO
		Map_nono carte = new Map_nono(carte_1); 
	}
	
	
	
	public ArrayList<Node> getNoeuds () {
		return noeuds;
	}
	
	public ArrayList<Edge> getChemins () {
		return chemins;
	}
	
}