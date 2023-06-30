package pojo;

import java.time.LocalDate;

public class XgCamFE {
    private short iIndPres;
    private String dDesIndPres;
    private String dFecEmNR;
    private XgCompPub gCompPub;

    public short getiIndPres() {
        return iIndPres;
    }

    public void setiIndPres(short iIndPres) {
        this.iIndPres = iIndPres;
    }

    public String getdDesIndPres() {
        return dDesIndPres;
    }

    public void setdDesIndPres(String dDesIndPres) {
        this.dDesIndPres = dDesIndPres;
    }

    public String getdFecEmNR() {
        return dFecEmNR;
    }

    public void setdFecEmNR(String dFecEmNR) {
        this.dFecEmNR = dFecEmNR;
    }

    public XgCompPub getgCompPub() {
        return gCompPub;
    }

    public void setgCompPub(XgCompPub gCompPub) {
        this.gCompPub = gCompPub;
    }
}
