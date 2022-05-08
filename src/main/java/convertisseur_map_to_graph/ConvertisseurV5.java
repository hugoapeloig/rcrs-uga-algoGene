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

/**
 * Convertie une Map sous forme de class GMLMap en un Graph
 * @author noe gravrand
 * @param GMLMap 
 * @return Graph : Graph(ArrayList<Node>, ArrayList<Edge> edges)
 */
public class ConvertisseurV5 {
	
	
	/**
	 * Convertie une Map sous forme de class GMLMap en un Graph
	 * @author noe gravrand
	 * @param GMLMap 
	 * @return Graph : Graph(ArrayList<Node>, ArrayList<Edge> edges)
	 */
	@SuppressWarnings("unchecked")
	public Graph convertie (GMLMap map) {
		
		
		Set<GMLBuilding> batTmpr = map.getBuildings();
		Set<GMLRoad> routesTmpr = map.getRoads();
		
		
		
		
		 ArrayList<graphResolution.Node> batL = new ArrayList<graphResolution.Node>(batTmpr.size());
		 for (int xx=0; xx<batTmpr.size(); xx++)
//			 nodesL.addAll((Collection<? extends Node>) xx);
			 batL.addAll((Collection<? extends Node>) batTmpr.iterator().next());
		
		 ArrayList<graphResolution.Edge> routesL = new ArrayList<graphResolution.Edge>(batTmpr.size());
		 for (int yy=0; yy<routesTmpr.size();yy++)
//			 nodesL.addAll((Collection<? extends graphResolution.Edge>) yy);
			 routesL.addAll((Collection<? extends Edge>) routesTmpr.iterator().next());
		
		 
		routesTmpr.iterator().next();
		
		
		Graph rslt = new Graph(batL, routesL);
		return rslt;
	}
	
	
}