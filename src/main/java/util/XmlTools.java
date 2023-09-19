package util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlTools 
{

	/**
	* Metodo encargado de transformar un StringXML a un objeto
	* @param xmlString
	* @return Document
	*/
	public static Document convertStringToXMLDocument(String xmlString) 
	{

	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
	    
	    try {

	      builder = factory.newDocumentBuilder();
	      Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
	      return doc;
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	
	
	/**
	* 
	* @param xml
	* @param miNodo
	* @return
	*/
	public static Map<String, String> getNodeFromString(String xml, String miNodo, Map<String, String> xnode) 
	{
		
		Map<String, String> resp = new HashMap<String, String>();

        try {
            // Crear un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Analizar el XML
            Document document = builder.parse(new InputSource(new StringReader(xml)));

            // Obtener el nodo raíz
            Element root = document.getDocumentElement();

            // Obtener el nodo <ns2:rEnviConsDeResponse>
            NodeList rEnviConsDeResponseList = root.getElementsByTagName(miNodo);
            if (rEnviConsDeResponseList.getLength() > 0) {
                Node rEnviConsDeResponse = rEnviConsDeResponseList.item(0);

                // Navegar por los nodos internos 
                NodeList childNodes = rEnviConsDeResponse.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);

                    // Realizar acciones con el nodo interno
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = childNode.getNodeName();
                        String nodeValue = childNode.getTextContent();
                        
                        if(nodeName.equalsIgnoreCase(xnode.get(nodeName))) 
                        {
                        	resp.put(nodeName, nodeValue);
                        }

                        //System.out.println("Nombre del nodo: " + nodeName);
                        //System.out.println("Valor del nodo: " + nodeValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return resp;
	}
	
	/**
	* Extrae un nodo de un XML
	* @param xml 
	* @param node a recuperar
	* @return
	*/
	public static String getNode(String xml, String node) 
	{
		try {
            // Crear un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Analizar el XML
            Document document = builder.parse(new InputSource(new StringReader(xml)));

            // Obtener el nodo 
            NodeList xnode = document.getElementsByTagName(node);
            if ( xnode.getLength() > 0 ) 
            {
                Node dCarQRNode = xnode.item(0);
                String dCarQRValue = dCarQRNode.getTextContent();
                return dCarQRValue;
                //System.out.println("Valor del nodo dCarQR: " + dCarQRValue);
            } else {
                return "Nodo dCarQR no encontrado.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	
		return null;
	}
	
}
