package pojo;

public class CamposOperacionContado {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7.1                                                                       |
	// | ID          | E605                                                                       |
	// | Padre       | E600                                                                       |
	// | Campo       | gPaConEIni                                                                 |
	// | Descripcion | Campos que describen la forma de pago al contado o del monto de la entrega |
	// |             | inicial                                                                    |
	// +------------------------------------------------------------------------------------------+
	private short iTiPago;
	private String dDesTiPag;
	private double dMonTiPag;
	private String cMoneTiPag;
	private String dDMoneTiPag;
	private double dTiCamTiPag;
	private CamposPagoTarjeta gPagTarCD;
	private CamposPagoCheque gPagCheq;
	
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
	public CamposPagoTarjeta getgPagTarCD() {
		return gPagTarCD;
	}
	public void setgPagTarCD(CamposPagoTarjeta gPagTarCD) {
		this.gPagTarCD = gPagTarCD;
	}
	public CamposPagoCheque getgPagCheq() {
		return gPagCheq;
	}
	public void setgPagCheq(CamposPagoCheque gPagCheq) {
		this.gPagCheq = gPagCheq;
	}
	
}
