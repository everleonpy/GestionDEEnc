package pojo;

public class CamposGeneralesDE {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | D                                                                          |
	// | ID          | D001                                                                       |
	// | Padre       | A001                                                                       |
	// | Campo       | gDatGralOpe                                                                |
	// | Descripcion | Campos generales del DE                                                    |
	// +------------------------------------------------------------------------------------------+
    private java.util.Date dFeEmiDE;
    private CamposOperacionComercial gOpeComer;
    private CamposEmisorDE gEmis;
    private CamposReceptorDE gDatRec;

	public java.util.Date getdFeEmiDE() {
		return dFeEmiDE;
	}

	public void setdFeEmiDE(java.util.Date dFeEmiDE) {
		this.dFeEmiDE = dFeEmiDE;
	}

	public CamposOperacionComercial getgOpeComer() {
		return gOpeComer;
	}

	public void setgOpeComer(CamposOperacionComercial gOpeComer) {
		this.gOpeComer = gOpeComer;
	}

	public CamposEmisorDE getgEmis() {
		return gEmis;
	}

	public void setgEmis(CamposEmisorDE gEmis) {
		this.gEmis = gEmis;
	}
	
	public CamposReceptorDE getgDatRec() {
		return gDatRec;
	}

	public void setgDatRec(CamposReceptorDE gDatRec) {
		this.gDatRec = gDatRec;
	}

}
