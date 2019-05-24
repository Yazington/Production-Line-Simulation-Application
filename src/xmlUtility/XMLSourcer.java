package xmlUtility;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.IOException;
import java.util.*;
import static xmlUtility.ForeachUtility.asList;;

public class XMLSourcer {
	
	private String filePath;
	private static List<String> simList;
	private static List<String> metaList;


	public XMLSourcer(String filePath)
	{
		this.filePath = filePath;
	}
	
	public void getData()
	{
		
		//Instancier la Factory qui permet d'accéder à un parser (appelé DocumentBuilder)
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//Récupérer le parser
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e1) 
		{
			e1.printStackTrace();
		}
		//Parser le fichier XML 
		Document doc = null;
		try 
		{
			doc = db.parse(this.filePath);
		} 
		catch (SAXException | IOException e1)
		{
			e1.printStackTrace();
		}
		
		Element element = doc.getDocumentElement();
		element.normalize();
		
		//Récupération d'un ensemble d'éléments ayant le même nom
		NodeList firstChildren = element.getChildNodes();
		List<String> simList = new LinkedList<String>();
		List<String> metaList = new LinkedList<String>();
				
		for(Node child: asList(firstChildren))
		{
			if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName() == "simulation")
			{
				parse(doc, simList, (Element) child);
			}
			else if(child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName() == "metadonnees")
			{
				parse(doc, metaList, (Element) child);
			}
		}
		System.out.println(simList);
		System.out.println(metaList);
		this.simList = simList;
		this.metaList = metaList;
		

	}
	
	// Get simulation and metadonnees information
	private void parse(Document doc, List<String> list, Element e)
	{
		NodeList children= e.getChildNodes();
		
        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if(n.hasAttributes())
                {
                	list.add(n.getNodeName());
                	for(int j=0; j < n.getAttributes().getLength(); j++ )
                	{
                		String nodeName = n.getAttributes().item(j).getNodeName();
                		list.add(nodeName + ":"+ n.getAttributes().item(j).getTextContent());
                	}
                	
                }
                else if(n.getNodeName() == "interval-production")
                {
                	list.add(n.getNodeName() + ":" + n.getFirstChild().getTextContent());
                }

                	
                parse(doc, list, (Element) n);
            }
            
        }
        
	}

	public List<String> getSimList() {
		return simList;
	}

	public List<String> getMetaList() {
		return metaList;
	}
}
