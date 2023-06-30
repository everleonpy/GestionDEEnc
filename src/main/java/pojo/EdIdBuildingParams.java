package pojo;

import java.time.LocalDateTime;

import com.roshka.sifen.core.types.TTipEmi;

public class EdIdBuildingParams {
	private short iTiDE;
    private String dRucEm;
    private String dDVEmi;
    private String dEst; 
    private String dPunExp; 
    private String dNumDoc; 
    private short iTipCont;
    private LocalDateTime dFeEmiDE;
    private short iTipEmi;
    private String dCodSeg;
    
	public short getiTiDE() {
		return iTiDE;
	}
	public void setiTiDE(short iTiDE) {
		this.iTiDE = iTiDE;
	}
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
	public String getdEst() {
		return dEst;
	}
	public void setdEst(String dEst) {
		this.dEst = dEst;
	}
	public String getdPunExp() {
		return dPunExp;
	}
	public void setdPunExp(String dPunExp) {
		this.dPunExp = dPunExp;
	}
	public String getdNumDoc() {
		return dNumDoc;
	}
	public void setdNumDoc(String dNumDoc) {
		this.dNumDoc = dNumDoc;
	}
	public short getiTipCont() {
		return iTipCont;
	}
	public void setiTipCont(short iTipCont) {
		this.iTipCont = iTipCont;
	}
	public LocalDateTime getdFeEmiDE() {
		return dFeEmiDE;
	}
	public void setdFeEmiDE(LocalDateTime dFeEmiDE) {
		this.dFeEmiDE = dFeEmiDE;
	}
	public short getiTipEmi() {
		return iTipEmi;
	}
	public void setiTipEmi(short iTipEmi) {
		this.iTipEmi = iTipEmi;
	}
	public String getdCodSeg() {
		return dCodSeg;
	}
	public void setdCodSeg(String dCodSeg) {
		this.dCodSeg = dCodSeg;
	} 
	
}
