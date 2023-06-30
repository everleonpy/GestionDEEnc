package nider;

public class TmpFactuDE_E {
	/**
	 * gCamFE
	 * Campos que componen la FE
	 */
	private int idConfig;
	private int idMov;
	private short iIndPres;
	private String dDesIndPres;
	private String dFecEmNR;
	// elementos contenidos por este elemento
	private TmpFactuDE_E1 gCompPub;
	
	public int getIdConfig() {
		return idConfig;
	}
	public void setIdConfig(int idConfig) {
		this.idConfig = idConfig;
	}
	public int getIdMov() {
		return idMov;
	}
	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}
	public short getiIndPres() {
		return iIndPres;
	}
	public void setiIndPres(short iIndPres) {
		this.iIndPres = iIndPres;
	}
	public String getdDesIndPres() {
		return dDesIndPres;
	}
	public void setdDesIndPres(String dDesIndPres) {
		this.dDesIndPres = dDesIndPres;
	}
	public String getdFecEmNR() {
		return dFecEmNR;
	}
	public void setdFecEmNR(String dFecEmNR) {
		this.dFecEmNR = dFecEmNR;
	}
	public TmpFactuDE_E1 getgCompPub() {
		return gCompPub;
	}
	public void setgCompPub(TmpFactuDE_E1 gCompPub) {
		this.gCompPub = gCompPub;
	}
	
}
