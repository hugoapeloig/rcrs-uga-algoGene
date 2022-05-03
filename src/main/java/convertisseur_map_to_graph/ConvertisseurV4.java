package convertisseur_map_to_graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import convertisseur_map_to_graph.maps.gml.GMLEdge;
import convertisseur_map_to_graph.maps.gml.GMLMap;
import convertisseur_map_to_graph.maps.gml.GMLNode;
import graphResolution.Edge;
import graphResolution.Graph;
import graphResolution.Node;

/**
 * Convertie une Map sous forme de class GMLMap en un Graph
 * @author noe gravrand
 * @param GMLMap 
 * @return Graph : Graph(ArrayList<Node>, ArrayList<Edge> edges)
 */
public class ConvertisseurV4 {
	
	
	/**
	 * Convertie une Map sous forme de class GMLMap en un Graph
	 * @author noe gravrand
	 * @param GMLMap 
	 * @return Graph : Graph(ArrayList<Node>, ArrayList<Edge> edges)
	 */
	@SuppressWarnings("unchecked")
	public Graph convertie (GMLMap map) {
		
		
		Set<GMLNode> nodesTmpr = map.getNodes();
		Set<GMLEdge> edgesTmpr = map.getEdges();
		
		
		 ArrayList<graphResolution.Node> nodesL = new ArrayList<graphResolution.Node>(nodesTmpr.size());
		 for (int xx=0; xx<nodesTmpr.size(); xx++)
//			 nodesL.addAll((Collection<? extends Node>) xx);
			 nodesL.addAll((Collection<? extends Node>) nodesTmpr.iterator().next());
		
		 ArrayList<graphResolution.Edge> edgesL = new ArrayList<graphResolution.Edge>(nodesTmpr.size());
		 for (int yy=0; yy<edgesTmpr.size();yy++)
//			 nodesL.addAll((Collection<? extends graphResolution.Edge>) yy);
			 edgesL.addAll((Collection<? extends Edge>) edgesTmpr.iterator().next());
		
		 
		nodesTmpr.iterator().next();
		
		
		Graph rslt = new Graph(nodesL, edgesL);
		return rslt;
	}
	
	
}