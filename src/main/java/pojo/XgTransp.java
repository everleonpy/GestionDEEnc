package pojo;

import java.time.LocalDate;
import java.util.List;


public class XgTransp {
    private short iTipTrans;
    private short iModTrans;
    private short iRespFlete;
    private short cCondNeg;
    private String dNuManif;
    private String dNuDespImp;
    private String dIniTras;
    private String dFinTras;
    private String cPaisDest;
    private XgCamSal gCamSal;
    private List<XgCamEnt> gCamEntList;
    private List<XgVehTras> gVehTrasList;
    private XgCamTrans gCamTrans;

    public short getiTipTrans() {
        return iTipTrans;
    }

    public void setiTipTrans(short iTipTrans) {
        this.iTipTrans = iTipTrans;
    }

    public short getiModTrans() {
        return iModTrans;
    }

    public void setiModTrans(short iModTrans) {
        this.iModTrans = iModTrans;
    }

    public short getiRespFlete() {
        return iRespFlete;
    }

    public void setiRespFlete(short iRespFlete) {
        this.iRespFlete = iRespFlete;
    }

    public short getcCondNeg() {
        return cCondNeg;
    }

    public void setcCondNeg(short cCondNeg) {
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

    public XgCamSal getgCamSal() {
        return gCamSal;
    }

    public void setgCamSal(XgCamSal gCamSal) {
        this.gCamSal = gCamSal;
    }

    public List<XgCamEnt> getgCamEntList() {
        return gCamEntList;
    }

    public void setgCamEntList(List<XgCamEnt> gCamEntList) {
        this.gCamEntList = gCamEntList;
    }

    public List<XgVehTras> getgVehTrasList() {
        return gVehTrasList;
    }

    public void setgVehTrasList(List<XgVehTras> gVehTrasList) {
        this.gVehTrasList = gVehTrasList;
    }

    public XgCamTrans getgCamTrans() {
        return gCamTrans;
    }

    public void setgCamTrans(XgCamTrans gCamTrans) {
        this.gCamTrans = gCamTrans;
    }

}
