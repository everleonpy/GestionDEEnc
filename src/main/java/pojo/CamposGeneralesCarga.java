package pojo;

import java.math.BigDecimal;

public class CamposGeneralesCarga {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | G1                                                                         |
	// | ID          | G050                                                                       |
	// | Padre       | G001                                                                       |
	// | Campo       | gCamCarg                                                                   |
	// | Descripcion | Campos generales de la carga                                               |
	// +------------------------------------------------------------------------------------------+
	private short cUniMedTotVol;
	private String dDesUniMedTotVol;
	private BigDecimal dTotVolMerc;
	private short cUniMedTotPes;
	private String dDesUniMedTotPes;
	private BigDecimal dTotPesMerc;
	private short iCarCarga;
	private String dDesCarCarga;
	
	public short getcUniMedTotVol() {
		return cUniMedTotVol;
	}
	public void setcUniMedTotVol(short cUniMedTotVol) {
		this.cUniMedTotVol = cUniMedTotVol;
	}
	public String getdDesUniMedTotVol() {
		return dDesUniMedTotVol;
	}
	public void setdDesUniMedTotVol(String dDesUniMedTotVol) {
		this.dDesUniMedTotVol = dDesUniMedTotVol;
	}
	public BigDecimal getdTotVolMerc() {
		return dTotVolMerc;
	}
	public void setdTotVolMerc(BigDecimal dTotVolMerc) {
		this.dTotVolMerc = dTotVolMerc;
	}
	public short getcUniMedTotPes() {
		return cUniMedTotPes;
	}
	public void setcUniMedTotPes(short cUniMedTotPes) {
		this.cUniMedTotPes = cUniMedTotPes;
	}
	public String getdDesUniMedTotPes() {
		return dDesUniMedTotPes;
	}
	public void setdDesUniMedTotPes(String dDesUniMedTotPes) {
		this.dDesUniMedTotPes = dDesUniMedTotPes;
	}
	public BigDecimal getdTotPesMerc() {
		return dTotPesMerc;
	}
	public void setdTotPesMerc(BigDecimal dTotPesMerc) {
		this.dTotPesMerc = dTotPesMerc;
	}
	public short getiCarCarga() {
		return iCarCarga;
	}
	public void setiCarCarga(short iCarCarga) {
		this.iCarCarga = iCarCarga;
	}
	public String getdDesCarCarga() {
		return dDesCarCarga;
	}
	public void setdDesCarCarga(String dDesCarCarga) {
		this.dDesCarCarga = dDesCarCarga;
	}
	
}
