package pojo;

import java.time.LocalDate;

import util.UtilPOS;

public class XgTimb {
    private short iTiDE;        // este campo engloba a iTiDE y a dDesTiDE
    private int dNumTim;        // número de timbrado
    private String dEst;        // código de establecimiento: patron ej: 001
    private String dPunExp;     // punto de expedición: patron ej: 001
    private String dNumDoc;     // número de documento: patron ej: 0192312
    private String dSerieNum;   // número de serie del timbrado (opcional)
    private String dFeIniT;  // fecha de inicio de vigencia del timbrado

    public short getiTiDE() {
        return iTiDE;
    }

    public void setiTiDE(short iTiDE) {
        this.iTiDE = iTiDE;
    }

    public int getdNumTim() {
        return dNumTim;
    }

    public void setdNumTim(int dNumTim) {
        this.dNumTim = dNumTim;
    }

    public String getdEst() {
        return dEst;
    }

    public void setdEst(String dEst) {
        //this.dEst = SifenUtil.leftPad(String.valueOf(dEst), '0', 3);
        this.dEst = UtilPOS.paddingString(dEst, 3, '0', true);
    }

    public String getdPunExp() {
        return dPunExp;
    }

    public void setdPunExp(String dPunExp) {
        //this.dPunExp = SifenUtil.leftPad(String.valueOf(dPunExp), '0', 3);
        this.dPunExp = UtilPOS.paddingString(dPunExp, 3, '0', true);
    }

    public String getdNumDoc() {
        return dNumDoc;
    }

    public void setdNumDoc(String dNumDoc) {
        //this.dNumDoc = SifenUtil.leftPad(String.valueOf(dNumDoc), '0', 7);
        this.dNumDoc = UtilPOS.paddingString(dNumDoc, 7, '0', true);
    }

    public String getdSerieNum() {
        return dSerieNum;
    }

    public void setdSerieNum(String dSerieNum) {
        this.dSerieNum = dSerieNum;
    }

    public String getdFeIniT() {
        return dFeIniT;
    }

    public void setdFeIniT(String dFeIniT) {
        this.dFeIniT = dFeIniT;
    }
}