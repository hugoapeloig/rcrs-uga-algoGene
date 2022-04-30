package convertisseur_map_to_graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import graphResolution.Edge;
import graphResolution.Graph;


public class ConvertisseurV3_2 {
	
	/**
	 * Convertie une Map sous forme de fichier gml en un Graph
	 * @param carte : File
	 * @return graph : Graph
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public Graph convertie (File fichier) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(fichier);
        document.getDocumentElement().normalize();
//      NodeList nList = document.getElementsByTagName("");
//      EdgeList eList = document.getElementsByTagName("");
        //------
        @SuppressWarnings("unchecked")
		ArrayList<graphResolution.Node> nAList = (ArrayList<graphResolution.Node>) document.getElementsByTagName("");
        @SuppressWarnings("unchecked")
		ArrayList<Edge> eAList = (ArrayList<Edge>) document.getElementsByTagName("");
        
        
        return new Graph(nAList, eAList);
	}
	
}