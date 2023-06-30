package nider;

public class TmpFactuDE_E71 {
	/**
	 * gPaConEIni
	 * Campos que describen la forma de pago al contado o del monto de la entrega inicial
	 */

	private int idConfig;
	private int idMov;
	private short iTiPago;
	private String dDesTiPag;
	private double dMonTiPag;
	private String cMoneTiPag;
	private String dDMoneTiPag;
	private double dTiCamTiPag;
	//
	private TmpFactuDE_E711 gPagTarCD;
	private TmpFactuDE_E712 gPagCheq;
	
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
	public short getiTiPago() {
		return iTiPago;
	}
	public void setiTiPago(short iTiPago) {
		this.iTiPago = iTiPago;
	}
	public String getdDesTiPag() {
		return dDesTiPag;
	}
	public void setdDesTiPag(String dDesTiPag) {
		this.dDesTiPag = dDesTiPag;
	}
	public double getdMonTiPag() {
		return dMonTiPag;
	}
	public void setdMonTiPag(double dMonTiPag) {
		this.dMonTiPag = dMonTiPag;
	}
	public String getcMoneTiPag() {
		return cMoneTiPag;
	}
	public void setcMoneTiPag(String cMoneTiPag) {
		this.cMoneTiPag = cMoneTiPag;
	}
	public String getdDMoneTiPag() {
		return dDMoneTiPag;
	}
	public void setdDMoneTiPag(String dDMoneTiPag) {
		this.dDMoneTiPag = dDMoneTiPag;
	}
	public double getdTiCamTiPag() {
		return dTiCamTiPag;
	}
	public void setdTiCamTiPag(double dTiCamTiPag) {
		this.dTiCamTiPag = dTiCamTiPag;
	}
	public TmpFactuDE_E711 getgPagTarCD() {
		return gPagTarCD;
	}
	public void setgPagTarCD(TmpFactuDE_E711 gPagTarCD) {
		this.gPagTarCD = gPagTarCD;
	}
	public TmpFactuDE_E712 getgPagCheq() {
		return gPagCheq;
	}
	public void setgPagCheq(TmpFactuDE_E712 gPagCheq) {
		this.gPagCheq = gPagCheq;
	}

}
