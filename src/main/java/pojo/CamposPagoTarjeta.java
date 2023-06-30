package pojo;

public class CamposPagoTarjeta {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7.1.1                                                                     |
	// | ID          | E620	                                                                      |
	// | Padre       | E605                                                                       |
	// | Campo       | gPagTarCD                                                                  |
	// | Descripcion | Campos que describen el pago o entrega inicial de la operación con tarjeta |
	// |             | de credito/debito                                                          |
	// +------------------------------------------------------------------------------------------+
	private short iDenTarj;
	private String dDesDenTarj;
	private String dRSProTar;
	private String dRUCProTar;
	private short dDVProTar;
	private short iForProPa;
	private int dCodAuOpe;
	private String dNomTit;
	private short dNumTarj;
	
	public short getiDenTarj() {
		return iDenTarj;
	}
	public void setiDenTarj(short iDenTarj) {
		this.iDenTarj = iDenTarj;
	}
	public String getdDesDenTarj() {
		return dDesDenTarj;
	}
	public void setdDesDenTarj(String dDesDenTarj) {
		this.dDesDenTarj = dDesDenTarj;
	}
	public String getdRSProTar() {
		return dRSProTar;
	}
	public void setdRSProTar(String dRSProTar) {
		this.dRSProTar = dRSProTar;
	}
	public String getdRUCProTar() {
		return dRUCProTar;
	}
	public void setdRUCProTar(String dRUCProTar) {
		this.dRUCProTar = dRUCProTar;
	}
	public short getdDVProTar() {
		return dDVProTar;
	}
	public void setdDVProTar(short dDVProTar) {
		this.dDVProTar = dDVProTar;
	}
	public short getiForProPa() {
		return iForProPa;
	}
	public void setiForProPa(short iForProPa) {
		this.iForProPa = iForProPa;
	}
	public int getdCodAuOpe() {
		return dCodAuOpe;
	}
	public void setdCodAuOpe(int dCodAuOpe) {
		this.dCodAuOpe = dCodAuOpe;
	}
	public String getdNomTit() {
		return dNomTit;
	}
	public void setdNomTit(String dNomTit) {
		this.dNomTit = dNomTit;
	}
	public short getdNumTarj() {
		return dNumTarj;
	}
	public void setdNumTarj(short dNumTarj) {
		this.dNumTarj = dNumTarj;
	}
	
}
