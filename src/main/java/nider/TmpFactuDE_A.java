package nider;

public class TmpFactuDE_A {
	/**
	 * DE
	 * Campos firmados del DE
	 */
	private int idConfig;
	private int idMov;
	private short dVerFor;
	// identificador del DE (CDC)
	private String Id;
	private short dDVId;
	private String dFecFirma;
	private short dSisFact;
	// contenido del documento electronico
	private TmpFactuDE_B gOpeDe;
	private TmpFactuDE_C gTimb;
	private TmpFactuDE_D gDatGralOpe;
	private TmpFactuDE_E0 gTipDE;
	
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
	public short getdVerFor() {
		return dVerFor;
	}
	public void setdVerFor(short dVerFor) {
		this.dVerFor = dVerFor;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public short getdDVId() {
		return dDVId;
	}
	public void setdDVId(short dDVId) {
		this.dDVId = dDVId;
	}
	public String getdFecFirma() {
		return dFecFirma;
	}
	public void setdFecFirma(String dFecFirma) {
		this.dFecFirma = dFecFirma;
	}
	public short getdSisFact() {
		return dSisFact;
	}
	public void setdSisFact(short dSisFact) {
		this.dSisFact = dSisFact;
	}
	public TmpFactuDE_B getgOpeDe() {
		return gOpeDe;
	}
	public void setgOpeDe(TmpFactuDE_B gOpeDe) {
		this.gOpeDe = gOpeDe;
	}
	public TmpFactuDE_C getgTimb() {
		return gTimb;
	}
	public void setgTimb(TmpFactuDE_C gTimb) {
		this.gTimb = gTimb;
	}
	public TmpFactuDE_D getgDatGralOpe() {
		return gDatGralOpe;
	}
	public void setgDatGralOpe(TmpFactuDE_D gDatGralOpe) {
		this.gDatGralOpe = gDatGralOpe;
	}
	public TmpFactuDE_E0 getgTipDE() {
		return gTipDE;
	}
	public void setgTipDE(TmpFactuDE_E0 gTipDE) {
		this.gTipDE = gTipDE;
	}

}
