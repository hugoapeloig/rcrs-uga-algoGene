package convertisseur_map_to_graph;

import java.util.ArrayList;
import graphResolution.Edge;
import graphResolution.Graph;
import graphResolution.Node;



/**
 * Class qui convertie une Map en Graph
 * @author noegr
 */
public class Convertisseur {
	
	
	
	/**
	 * Convertie une Map en un Graph
	 * @param carte : Map
	 * @return graph : Graph
	 */
	public Graph convertie (Map carte) {
		Graph_nono grphN = new Graph_nono(carte);
		Graph graph = Graph_nono_To_Graph(grphN);
		return graph;
	}
	
	
	
	/**
	 * Convertie un Graph_nono en Graph
	 * @param grph_N : Graph_nono
	 * @return graph : Graph
	 */
	private Graph Graph_nono_To_Graph (Graph_nono grph_N) {
		ArrayList<Node> noeuds = grph_N.getNoeuds();
		ArrayList<Edge> chemins = grph_N.getChemins();
		Graph graph = new Graph(noeuds, chemins);
		return graph;
	}
	
}