package nider;

public class TmpFactuDE_D {
	/**
	 * gDatGralOpe
	 * Campos generales del DE
	 */
	private int idConfig;
	private int idMov;
	private java.util.Date dFeEmiDE;
	// contenido del elemento datos generales de la operacion
	private TmpFactuDE_D1 gOpeCom;
	private TmpFactuDE_D2 gEmis;
	private TmpFactuDE_D3 gDatRec;
	
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
	public java.util.Date getdFeEmiDE() {
		return dFeEmiDE;
	}
	public void setdFeEmiDE(java.util.Date dFeEmiDE) {
		this.dFeEmiDE = dFeEmiDE;
	}
	public TmpFactuDE_D1 getgOpeCom() {
		return gOpeCom;
	}
	public void setgOpeCom(TmpFactuDE_D1 gOpeCom) {
		this.gOpeCom = gOpeCom;
	}
	public TmpFactuDE_D2 getgEmis() {
		return gEmis;
	}
	public void setgEmis(TmpFactuDE_D2 gEmis) {
		this.gEmis = gEmis;
	}
	public TmpFactuDE_D3 getgDatRec() {
		return gDatRec;
	}
	public void setgDatRec(TmpFactuDE_D3 gDatRec) {
		this.gDatRec = gDatRec;
	}

}
