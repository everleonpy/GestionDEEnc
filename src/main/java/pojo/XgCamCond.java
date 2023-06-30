package pojo;

import java.util.List;

public class XgCamCond {
    private short iCondOpe;
    private List<XgPaConEIni> gPaConEIniList;
    private XgPagCred gPagCred;

    public short getiCondOpe() {
        return iCondOpe;
    }

    public void setiCondOpe(short iCondOpe) {
        this.iCondOpe = iCondOpe;
    }

    public XgPagCred getgPagCred() {
        return gPagCred;
    }

    public void setgPagCred(XgPagCred gPagCred) {
        this.gPagCred = gPagCred;
    }

    public List<XgPaConEIni> getgPaConEIniList() {
        return gPaConEIniList;
    }

    public void setgPaConEIniList(List<XgPaConEIni> gPaConEIniList) {
        this.gPaConEIniList = gPaConEIniList;
    }
}
