package util;



import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaRUC;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.response.ruc.TxContRuc;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")
public class CheckStatusDETools 
{
	
	private SifenConfig sifenConfig;
	
	public CheckStatusDETools(String fileprop) throws SifenException 
	{
		if( fileprop != null ) 
		{
			sifenConfig = SifenConfig.cargarConfiguracion(fileprop);
			 Sifen.setSifenConfig(sifenConfig);
		}
	}
	
	
	/**
	* 
	* @param nroLote
	* @return
	* @throws SifenException
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	*/
	public String checkLote(String nroLote) throws SifenException, IOException, SAXException, ParserConfigurationException 
	{
		RespuestaConsultaLoteDE resp = Sifen.consultaLoteDE(nroLote);
		if( resp.getRespuestaBruta() != null ) 
		{
			String xml = resp.getRespuestaBruta();
			return format(xml, true);
		}
		return "*** No hay respuesta desde el SIFEN ****";
	}


	/**
	* 
	* @param cdc
	* @return
	 * @throws SifenException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	*/
	public String checkCDC(String cdc) throws SifenException, IOException, SAXException, ParserConfigurationException 
	{
		RespuestaConsultaDE resp = Sifen.consultaDE(cdc.trim());
		
		if(resp.getRespuestaBruta() != null ) 
		{
			String xml = resp.getRespuestaBruta();
			return format(xml, true);
		}
		
		return "*** No hay respuesta desde el SIFEN ****";
	}
	
	
	
	/**
	* 
	* @param ruc
	* @return
	* @throws SifenException
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	*/
	public String checkRUC(String ruc) throws SifenException, IOException, SAXException, ParserConfigurationException 
	{
		RespuestaConsultaRUC resp = Sifen.consultaRUC(ruc.trim());
		TxContRuc txContRuc = resp.getxContRUC();
		if( txContRuc != null ) 
		{
			StringBuilder sb = new StringBuilder("**********************************************************\n"+
												 "*** "+txContRuc.getdRazCons()+"\n"+
												 "*** Facturador Electronico :"+txContRuc.getdRUCFactElec()+"\n"+
												 "*** "+txContRuc.getdDesEstCons()+"\n"+
												 "*** "+txContRuc.getdRUCCons()+"\n"+
												 "**********************************************************"
												);
			return sb.toString();
		}
		return "*** No hay respuesta desde el SIFEN ****";
	}
	
	/**
	 * Formate un string xml para visualizarlo
	 * @param xml
	 * @param ommitXmlDeclaration
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static String format(String xml, Boolean ommitXmlDeclaration)
			throws IOException, SAXException, ParserConfigurationException 
	{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		OutputFormat format = new OutputFormat(doc);
		format.setIndenting(true);
		format.setIndent(2);
		format.setOmitXMLDeclaration(ommitXmlDeclaration);
		format.setLineWidth(Integer.MAX_VALUE);
		Writer outxml = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(outxml, format);
		serializer.serialize(doc);
		return outxml.toString();
	}
	
	
	
	
}