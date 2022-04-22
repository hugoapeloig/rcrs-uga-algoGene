package convertisseur_map_to_graph;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

public class ConvertisseurV2 { 
	
    public static void main(String[] args) throws ParserConfigurationException,
    SAXException, IOException {
        try {
            File inputFile = new File("/employee.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            System.out.println("Root element :" + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> studentList = classElement.getChildren();
            System.out.println("----------------------------");

            for (int temp = 0; temp < studentList.size(); temp++) {    
                Element student = studentList.get(temp);
                System.out.println("\nCurrent Element :" 
                        + student.getName());
                System.out.println("First Name : "
                        + student.getChild("firstname").getText());
                System.out.println("Last Name : "
                        + student.getChild("lastname").getText());
                System.out.println("Salary : "
                        + student.getChild("salary").getText());
            }
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}