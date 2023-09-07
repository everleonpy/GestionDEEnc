package util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlTools 
{

	/**
	* Metodo encargado de transformar un StringXML a un objeto
	* @param xmlString
	* @return Document
	*/
	public static Document convertStringToXMLDocument(String xmlString) {

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
	
}
