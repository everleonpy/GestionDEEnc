package pojo;

import java.util.List;

import com.roshka.sifen.core.fields.request.de.TgCamEnt;
import com.roshka.sifen.core.fields.request.de.TgCamSal;
import com.roshka.sifen.core.fields.request.de.TgCamTrans;
import com.roshka.sifen.core.fields.request.de.TgVehTras;

public class CamposTranspMercaderias {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E10                                                                        |
	// | ID          | E900                                                                       |
	// | Padre       | E001                                                                       |
	// | Campo       | gTransp                                                                    |
	// | Descripcion | Campos que describen el transporte de las mercaderías                      |
	// +------------------------------------------------------------------------------------------+
    private short iTipTrans;
    private String dDesTipTrans;
    private short iModTrans;
    private String dDesModTrans;
    private short iRespFlete;
    private String cCondNeg;
    private String dNuManif;
    private String dNuDespImp;
    private java.util.Date dIniTras;
    private java.util.Date dFinTras;
    private String cPaisDest;
    private String dDesPaisDest;
    private CamposSalidaMercaderias gCamSal;
    private List<CamposEntregaMercaderias> gCamEntList;
    private List<CamposVehiculoTraslado> gVehTrasList;
    private CamposTransportista gCamTrans;

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
	public java.util.Date getdIniTras() {
		return dIniTras;
	}
	public void setdIniTras(java.util.Date dIniTras) {
		this.dIniTras = dIniTras;
	}
	public java.util.Date getdFinTras() {
		return dFinTras;
	}
	public void setdFinTras(java.util.Date dFinTras) {
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
	public CamposSalidaMercaderias getgCamSal() {
		return gCamSal;
	}
	public void setgCamSal(CamposSalidaMercaderias gCamSal) {
		this.gCamSal = gCamSal;
	}
	public List<CamposEntregaMercaderias> getgCamEntList() {
		return gCamEntList;
	}
	public void setgCamEntList(List<CamposEntregaMercaderias> gCamEntList) {
		this.gCamEntList = gCamEntList;
	}
	public List<CamposVehiculoTraslado> getgVehTrasList() {
		return gVehTrasList;
	}
	public void setgVehTrasList(List<CamposVehiculoTraslado> gVehTrasList) {
		this.gVehTrasList = gVehTrasList;
	}
	public CamposTransportista getgCamTrans() {
		return gCamTrans;
	}
	public void setgCamTrans(CamposTransportista gCamTrans) {
		this.gCamTrans = gCamTrans;
	}
    
}
