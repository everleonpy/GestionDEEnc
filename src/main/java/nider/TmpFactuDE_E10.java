package nider;

import java.util.ArrayList;

public class TmpFactuDE_E10 {
	/**
	 * gTransp
	 * Campos que describen el transporte de mercaderias
	 */
	private int idConfig;
	private int idMov;
	private short iTipTrans;
	private String dDesTipTrans;
	private short iModTrans;
	private String dDesModTrans;
	private short iRespFlete;
	private String cCondNeg;
	private String dNuManif;
	private String dNuDespImp;
    private String dIniTras;
    private String dFinTras;
    private String cPaisDest;
    private String dDesPaisDest;
    //
    private 	TmpFactuDE_E10 gTransp;
    private TmpFactuDE_E101 gCamSal;
    private ArrayList<TmpFactuDE_E102> gCamEnt;
    private ArrayList<TmpFactuDE_E103> gVehTras;
    private TmpFactuDE_E104 gCamTrans;
    
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
	public short getiTipTrans() {
		return iTipTrans;
	}
	public void setiTipTrans(short iTipTrans) {
		this.iTipTrans = iTipTrans;
	}
	public String getdDesTipTrans() {
		return dDesTipTrans;
	}
	public void setdDesTipTrans(String dDesTipTrans) {
		this.dDesTipTrans = dDesTipTrans;
	}
	public short getiModTrans() {
		return iModTrans;
	}
	public void setiModTrans(short iModTrans) {
		this.iModTrans = iModTrans;
	}
	public String getdDesModTrans() {
		return dDesModTrans;
	}
	public void setdDesModTrans(String dDesModTrans) {
		this.dDesModTrans = dDesModTrans;
	}
	public short getiRespFlete() {
		return iRespFlete;
	}
	public void setiRespFlete(short iRespFlete) {
		this.iRespFlete = iRespFlete;
	}
	public String getcCondNeg() {
		return cCondNeg;
	}
	public void setcCondNeg(String cCondNeg) {
		this.cCondNeg = cCondNeg;
	}
	public String getdNuManif() {
		return dNuManif;
	}
	public void setdNuManif(String dNuManif) {
		this.dNuManif = dNuManif;
	}
	public String getdNuDespImp() {
		return dNuDespImp;
	}
	public void setdNuDespImp(String dNuDespImp) {
		this.dNuDespImp = dNuDespImp;
	}
	public String getdIniTras() {
		return dIniTras;
	}
	public void setdIniTras(String dIniTras) {
		this.dIniTras = dIniTras;
	}
	public String getdFinTras() {
		return dFinTras;
	}
	public void setdFinTras(String dFinTras) {
		this.dFinTras = dFinTras;
	}
	public String getcPaisDest() {
		return cPaisDest;
	}
	public void setcPaisDest(String cPaisDest) {
		this.cPaisDest = cPaisDest;
	}
	public String getdDesPaisDest() {
		return dDesPaisDest;
	}
	public void setdDesPaisDest(String dDesPaisDest) {
		this.dDesPaisDest = dDesPaisDest;
	}
	public TmpFactuDE_E10 getgTransp() {
		return gTransp;
	}
	public void setgTransp(TmpFactuDE_E10 gTransp) {
		this.gTransp = gTransp;
	}
	public TmpFactuDE_E101 getgCamSal() {
		return gCamSal;
	}
	public void setgCamSal(TmpFactuDE_E101 gCamSal) {
		this.gCamSal = gCamSal;
	}
	public ArrayList<TmpFactuDE_E102> getgCamEnt() {
		return gCamEnt;
	}
	public void setgCamEnt(ArrayList<TmpFactuDE_E102> gCamEnt) {
		this.gCamEnt = gCamEnt;
	}
	public ArrayList<TmpFactuDE_E103> getgVehTras() {
		return gVehTras;
	}
	public void setgVehTras(ArrayList<TmpFactuDE_E103> gVehTras) {
		this.gVehTras = gVehTras;
	}
	public TmpFactuDE_E104 getgCamTrans() {
		return gCamTrans;
	}
	public void setgCamTrans(TmpFactuDE_E104 gCamTrans) {
		this.gCamTrans = gCamTrans;
	}

}
