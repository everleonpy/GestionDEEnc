package pojo;

import java.util.ArrayList;

public class CamposEmisorDE {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | D2                                                                         |
	// | ID          | D100                                                                       |
	// | Padre       | D001                                                                       |
	// | Campo       | gEmis                                                                      |
	// | Descripcion | Grupo de campos que identifican al emisor                                  |
	// +------------------------------------------------------------------------------------------+
	private String dRucEm;
	private String dDVEmi;
	private short iTipCont;
	private short cTipReg;
	private String dNomEmi;
	private String dNomFanEmi;
	private String dDirEmi;
	private String dNumCas;
	private String dCompDir1;
	private String dCompDir2;
	private short cDepEmi;
	private String dDesDepEmi;
	private short cDisEmi;
	private String dDesDisEmi;
	private short cCiuEmi;
	private String dDesCiuEmi;
	private String dTelEmi;
	private String dEmailE;
	private String dDenSuc;
	//
	private ArrayList<CamposActivEconomica> gActEco;
	private CamposResponsableDE gRespDE;
	
	public String getdRucEm() {
		return dRucEm;
	}
	public void setdRucEm(String dRucEm) {
		this.dRucEm = dRucEm;
	}
	public String getdDVEmi() {
		return dDVEmi;
	}
	public void setdDVEmi(String dDVEmi) {
		this.dDVEmi = dDVEmi;
	}
	public short getiTipCont() {
		return iTipCont;
	}
	public void setiTipCont(short iTipCont) {
		this.iTipCont = iTipCont;
	}
	public short getcTipReg() {
		return cTipReg;
	}
	public void setcTipReg(short cTipReg) {
		this.cTipReg = cTipReg;
	}
	public String getdNomEmi() {
		return dNomEmi;
	}
	public void setdNomEmi(String dNomEmi) {
		this.dNomEmi = dNomEmi;
	}
	public String getdNomFanEmi() {
		return dNomFanEmi;
	}
	public void setdNomFanEmi(String dNomFanEmi) {
		this.dNomFanEmi = dNomFanEmi;
	}
	public String getdDirEmi() {
		return dDirEmi;
	}
	public void setdDirEmi(String dDirEmi) {
		this.dDirEmi = dDirEmi;
	}
	public String getdNumCas() {
		return dNumCas;
	}
	public void setdNumCas(String dNumCas) {
		this.dNumCas = dNumCas;
	}
	public String getdCompDir1() {
		return dCompDir1;
	}
	public void setdCompDir1(String dCompDir1) {
		this.dCompDir1 = dCompDir1;
	}
	public String getdCompDir2() {
		return dCompDir2;
	}
	public void setdCompDir2(String dCompDir2) {
		this.dCompDir2 = dCompDir2;
	}
	public short getcDepEmi() {
		return cDepEmi;
	}
	public void setcDepEmi(short cDepEmi) {
		this.cDepEmi = cDepEmi;
	}
	public String getdDesDepEmi() {
		return dDesDepEmi;
	}
	public void setdDesDepEmi(String dDesDepEmi) {
		this.dDesDepEmi = dDesDepEmi;
	}
	public short getcDisEmi() {
		return cDisEmi;
	}
	public void setcDisEmi(short cDisEmi) {
		this.cDisEmi = cDisEmi;
	}
	public String getdDesDisEmi() {
		return dDesDisEmi;
	}
	public void setdDesDisEmi(String dDesDisEmi) {
		this.dDesDisEmi = dDesDisEmi;
	}
	public short getcCiuEmi() {
		return cCiuEmi;
	}
	public void setcCiuEmi(short cCiuEmi) {
		this.cCiuEmi = cCiuEmi;
	}
	public String getdDesCiuEmi() {
		return dDesCiuEmi;
	}
	public void setdDesCiuEmi(String dDesCiuEmi) {
		this.dDesCiuEmi = dDesCiuEmi;
	}
	public String getdTelEmi() {
		return dTelEmi;
	}
	public void setdTelEmi(String dTelEmi) {
		this.dTelEmi = dTelEmi;
	}
	public String getdEmailE() {
		return dEmailE;
	}
	public void setdEmailE(String dEmailE) {
		this.dEmailE = dEmailE;
	}
	public String getdDenSuc() {
		return dDenSuc;
	}
	public void setdDenSuc(String dDenSuc) {
		this.dDenSuc = dDenSuc;
	}
	public ArrayList<CamposActivEconomica> getgActEco() {
		return gActEco;
	}
	public void setgActEco(ArrayList<CamposActivEconomica> gActEco) {
		this.gActEco = gActEco;
	}
	public CamposResponsableDE getgRespDE() {
		return gRespDE;
	}
	public void setgRespDE(CamposResponsableDE gRespDE) {
		this.gRespDE = gRespDE;
	}

}
