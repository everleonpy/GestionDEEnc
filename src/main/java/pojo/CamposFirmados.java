package pojo;

import java.util.ArrayList;

public class CamposFirmados {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | A                                                                          |
	// | ID          | A001                                                                       |
	// | Padre       | AA001                                                                      |
	// | Campo       | DE                                                                         |
	// | Descripcion | Campos firmados del DE                                                     |
	// +------------------------------------------------------------------------------------------+
	private String Id;
	private String dDVId;
	private String dFecFirma;
	private short dSisFact;
	private CamposOperacionDE gOpeDE;
	private CamposTimbrado gTimb;
	private CamposGeneralesDE gDatGralOpe;
	private CamposEspecificosDE gDtipDE;
	private CamposSubtotalesTotales gTotSub;
	private CamposComplUsoGeneral gCamGen;
	private ArrayList<CamposDEAsociado> gCamDEAsoc;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getdDVId() {
		return dDVId;
	}
	public void setdDVId(String dDVId) {
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
	public CamposOperacionDE getgOpeDE() {
		return gOpeDE;
	}
	public void setgOpeDE(CamposOperacionDE gOpeDE) {
		this.gOpeDE = gOpeDE;
	}
	public CamposTimbrado getgTimb() {
		return gTimb;
	}
	public void setgTimb(CamposTimbrado gTimb) {
		this.gTimb = gTimb;
	}
	public CamposGeneralesDE getgDatGralOpe() {
		return gDatGralOpe;
	}
	public void setgDatGralOpe(CamposGeneralesDE gDatGralOpe) {
		this.gDatGralOpe = gDatGralOpe;
	}
	public CamposEspecificosDE getgDtipDE() {
		return gDtipDE;
	}
	public void setgDtipDE(CamposEspecificosDE gDtipDE) {
		this.gDtipDE = gDtipDE;
	}
	public CamposSubtotalesTotales getgTotSub() {
		return gTotSub;
	}
	public void setgTotSub(CamposSubtotalesTotales gTotSub) {
		this.gTotSub = gTotSub;
	}	
	public CamposComplUsoGeneral getgCamGen() {
		return gCamGen;
	}
	public void setgCamGen(CamposComplUsoGeneral gCamGen) {
		this.gCamGen = gCamGen;
	}
	public ArrayList<CamposDEAsociado> getgCamDEAsoc() {
		return gCamDEAsoc;
	}
	public void setgCamDEAsoc(ArrayList<CamposDEAsociado> gCamDEAsoc) {
		this.gCamDEAsoc = gCamDEAsoc;
	}

}
