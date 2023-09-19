package util;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.roshka.sifen.Sifen;
import com.roshka.sifen.addon.Envelope;
import com.roshka.sifen.addon.gResProcLote;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.EventosDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaRUC;
import com.roshka.sifen.core.beans.response.RespuestaRecepcionEvento;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.event.TgGroupTiEvt;
import com.roshka.sifen.core.fields.request.event.TrGeVeCan;
import com.roshka.sifen.core.fields.request.event.TrGesEve;
import com.roshka.sifen.core.fields.response.ruc.TxContRuc;
import dto.TmpFactuDE_ADTO;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
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
			System.out.println("FILE_PROP : "+fileprop);
			sifenConfig = SifenConfig.cargarConfiguracion(fileprop);
			Sifen.setSifenConfig(sifenConfig);
		}
	}
	
	
	/**
	* 
	* @param nroLote
	* @return String
	* @throws SifenException
	* @throws ParserConfigurationException 
	* @throws SAXException 
	* @throws IOException 
	 * @throws JAXBException 
	*/
	public String checkLote(String nroLote) throws SifenException, IOException, SAXException, ParserConfigurationException, JAXBException 
	{
		RespuestaConsultaLoteDE resp = Sifen.consultaLoteDE(nroLote);
		
		if( resp.getRespuestaBruta() != null ) 
		{
				//String xml = resp.getRespuestaBruta();
				//return format(xml, true);
				//System.out.println("RESPUESTA : "+xml);
								
				JacksonXmlModule module = new JacksonXmlModule();
			    module.setDefaultUseWrapper(false);
				XmlMapper xmlMapper = new XmlMapper(module);
				Envelope tmp = xmlMapper.readValue(resp.getRespuestaBruta(),Envelope.class);
				StringBuilder str =  new StringBuilder();
				
				if( tmp.getBody().getrResEnviConsLoteDe() != null) {
					str.append(" _______________________________________________________________________\n");
					str.append("| Nro de Lote : "+nroLote+" \n");
				    str.append("|_______________________________________________________________________\n");
				    str.append("| Codigo de Respuesta  : "+tmp.getBody().getrResEnviConsLoteDe()
				    										.dCodResLot+"\n");
				    str.append("| Mensaje              : "+tmp.getBody().getrResEnviConsLoteDe()
				    										.getdMsgResLot()+"\n");
				    
				    List<gResProcLote> lst = tmp.getBody().getrResEnviConsLoteDe().getgResProcLote();
				    for (Iterator<gResProcLote> iterator = lst.iterator(); iterator.hasNext();) 
				    {
						gResProcLote rsp = (gResProcLote) iterator.next();
						str.append("____________________________________________________________________ \n");
						str.append("| CDC : "+rsp.getId() +"\n");
						str.append("| Cod. Respuesta : "+rsp.getgResProc().getdCodRes() +"\n");
						str.append("| Estado         : "+rsp.getgResProc().getdMsgRes() +"\n");
						str.append("|___________________________________________________________________ \n");
						
					}
				    
				    //str.append("*--------------------------------------------------------------------\n");
				}
		     
		     return str.toString();
			
		}
		return "*** No hay respuesta desde el SIFEN ****";
	}

	
	/**
	* 
	* @param cdc
	* @param msgCancelacion
	* @param idEvento
	* @return
	* @throws ParserConfigurationException 
	* @throws SAXException 
	* @throws IOException 
	* @throws SifenException 
	*/
	public String eventoCancelacion(String cdc, String msgCancelacion, String idEvento) 
									throws IOException, SAXException, ParserConfigurationException, SifenException 
	{
		LocalDateTime currentDate = LocalDateTime.now();
		 // Evento de Cancelación
        TrGeVeCan trGeVeCan = new TrGeVeCan();
        trGeVeCan.setId(cdc);
        trGeVeCan.setmOtEve(msgCancelacion);

        TgGroupTiEvt tgGroupTiEvt = new TgGroupTiEvt();
        tgGroupTiEvt.setrGeVeCan(trGeVeCan);
        
        TrGesEve rGesEve1 = new TrGesEve();
        rGesEve1.setId(idEvento);
        rGesEve1.setdFecFirma(currentDate);
        rGesEve1.setgGroupTiEvt(tgGroupTiEvt);

        EventosDE eventosDE = new EventosDE();
        eventosDE.setrGesEveList(Collections.singletonList(rGesEve1));

        RespuestaRecepcionEvento resp = Sifen.recepcionEvento(eventosDE);
        
        if( resp.getRespuestaBruta() != null ) 
        {
        	//String xml = resp.getRespuestaBruta();
        	//return format(xml, true);
        	
        	XmlMapper xmlMapper = new XmlMapper();
			Envelope tmp = xmlMapper.readValue(resp.getRespuestaBruta(),Envelope.class);
			
			StringBuilder str =  new StringBuilder();
			str.append(" ** Nro de CDC : "+cdc+" \n");
		    str.append("*--------------------------------------------------------------------\n");
		    str.append("| Codigo de Respuesta  : "+tmp.getBody().getrRetEnviDe().getrProtDe()
		    										.getgResProc().getdCodRes()+"\n");
		    str.append("| Estado Evento        : "+tmp.getBody().getrRetEnviDe().getrProtDe()
					.getdEstRes()+"\n");
		    str.append("| Mensaje              : "+tmp.getBody().getrRetEnviDe().getrProtDe()
		    										.getgResProc().getdMsgRes()+"\n");
		    str.append("*--------------------------------------------------------------------\n");
		    return str.toString();
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
			//String xml = resp.getRespuestaBruta();
			//return format(xml, true);
			Map<String, String> data  = new HashMap<String, String>();
			
			
			data.put("ns2:dFecProc", "ns2:dFecProc");
			data.put("ns2:dCodRes", "ns2:dCodRes");
			data.put("ns2:dMsgRes", "ns2:dMsgRes");
			data.put("ns2:xContenDE", "ns2:xContenDE");
			data  = XmlTools.getNodeFromString(resp.getRespuestaBruta(), "ns2:rEnviConsDeResponse", data);
			
			String QR = XmlTools.getNode(resp.getRespuestaBruta(), "dCarQR");
			
			StringBuilder str =  new StringBuilder();
			str.append("* Nro de CDC : "+cdc+" \n");
		    str.append("|=====================================================================-\n");
		    str.append("| Codigo de Respuesta  : "+data.get("ns2:dCodRes")+"\n");
		    str.append("| Mensaje              : "+data.get("ns2:dMsgRes")+"\n");
		    str.append("*======================================================================\n");
		    str.append("| QR : "+StringTools.cortarString(QR, 80)+" \n");
		    str.append("*======================================================================-\n");
		    
		    return str.toString();
			
		}
		
		return "*** No hay respuesta desde el SIFEN ****";
	}
	
	
	
	/**
	* Metodo encargado de Verificar el status de una FE via WS y actulizar la cabezera 
	* @param date
	* @return
	* @throws SifenException 
	*/
	public String checkCDCtoDate(String date) throws SifenException 
	{
		if( date.length() > 0 ) 
		{
			Date dateProc = DateTools.getDate(date, null);
			StringBuilder strResp = new StringBuilder();
		
				if( dateProc != null ) 
				{
					/* Obtenemos la lista de los CDCs que no estan en estado Aprobado*/
					List<String> listaCDCs = TmpFactuDE_ADTO.obtenrCDCs(dateProc);
					if( listaCDCs != null ) 
					{
						
						for (String cdc : listaCDCs) 
						{
							RespuestaConsultaDE resp = Sifen.consultaDE(cdc.trim());
							if( resp.getdMsgRes().trim().equalsIgnoreCase("CDC encontrado") ) 
							{
								strResp.append("CDC  Encontrado : "+cdc+"\n");
								TmpFactuDE_ADTO.updateStatus(cdc);
							}
						}
					}
					return strResp.toString();
					
				}
			
			return "FORMATO DE FECHA INCORRECTO ...";
		}
		return "INGRESE UNA FECHA VALIDA";
		
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
	@SuppressWarnings("unused")
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
