package dto;

import java.io.Serializable;
import java.util.Date;


/**
* DTO encargado de actualizar los estados de los documentos Electronicos
* @author eleon
*
*/
public class RcvTrxEbBatchItemDTO  implements Serializable
{

	private static final long serialVersionUID = -8326369325886895483L;

	private Long idVenta; 
	private String cdc;
	private Date transmissDate;
	private String resultStatusng;
	
	public RcvTrxEbBatchItemDTO(){}
	
	public RcvTrxEbBatchItemDTO(Long idVenta, String cdc, Date transmissDate, String resultStatusng) {
		super();
		this.idVenta = idVenta;
		this.cdc = cdc;
		this.transmissDate = transmissDate;
		this.resultStatusng = resultStatusng;
	}

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public String getCdc() {
		return cdc;
	}

	public void setCdc(String cdc) {
		this.cdc = cdc;
	}

	public Date getTransmissDate() {
		return transmissDate;
	}

	public void setTransmissDate(Date transmissDate) {
		this.transmissDate = transmissDate;
	}

	public String getResultStatusng() {
		return resultStatusng;
	}

	public void setResultStatusng(String resultStatusng) {
		this.resultStatusng = resultStatusng;
	}
	
}
