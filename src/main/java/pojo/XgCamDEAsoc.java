package pojo;

import java.math.BigInteger;
import java.time.LocalDate;

import util.UtilPOS;

public class XgCamDEAsoc {
    private short iTipDocAso;
    private String dCdCDERef;
    private String dNTimDI;
    private String dEstDocAso;
    private String dPExpDocAso;
    private String dNumDocAso;
    private short iTipoDocAso;
    private LocalDate dFecEmiDI;
    private String dNumComRet;
    private String dNumResCF;
    private short iTipCons;
    private BigInteger dNumCons;
    private String dNumControl;


    public short getiTipDocAso() {
        return iTipDocAso;
    }

    public void setiTipDocAso(short iTipDocAso) {
        this.iTipDocAso = iTipDocAso;
    }

    public String getdCdCDERef() {
        return dCdCDERef;
    }

    public void setdCdCDERef(String dCdCDERef) {
        this.dCdCDERef = dCdCDERef;
    }

    public String getdNTimDI() {
        return dNTimDI;
    }

    public void setdNTimDI(String dNTimDI) {
        this.dNTimDI = dNTimDI;
    }

    public String getdEstDocAso() {
        return dEstDocAso;
    }

    public void setdEstDocAso(String dEstDocAso) {
        //this.dEstDocAso = SifenUtil.leftPad(dEstDocAso, '0', 3);
        this.dEstDocAso = UtilPOS.paddingString(dEstDocAso, 3, '0', true);
    }

    public String getdPExpDocAso() {
        return dPExpDocAso;
    }

    public void setdPExpDocAso(String dPExpDocAso) {
        //this.dPExpDocAso = SifenUtil.leftPad(dPExpDocAso, '0', 3);
        this.dPExpDocAso = UtilPOS.paddingString(dPExpDocAso, 3, '0', true);
    }

    public String getdNumDocAso() {
        return dNumDocAso;
    }

    public void setdNumDocAso(String dNumDocAso) {
        //this.dNumDocAso = SifenUtil.leftPad(dNumDocAso, '0', 7);
        this.dNumDocAso = UtilPOS.paddingString(dNumDocAso, 7, '0', true);
    }

    public short getiTipoDocAso() {
        return iTipoDocAso;
    }

    public void setiTipoDocAso(short iTipoDocAso) {
        this.iTipoDocAso = iTipoDocAso;
    }

    public LocalDate getdFecEmiDI() {
        return dFecEmiDI;
    }

    public void setdFecEmiDI(LocalDate dFecEmiDI) {
        this.dFecEmiDI = dFecEmiDI;
    }

    public String getdNumComRet() {
        return dNumComRet;
    }

    public void setdNumComRet(String dNumComRet) {
        this.dNumComRet = dNumComRet;
    }

    public String getdNumResCF() {
        return dNumResCF;
    }

    public void setdNumResCF(String dNumResCF) {
        this.dNumResCF = dNumResCF;
    }

    public short getiTipCons() {
        return iTipCons;
    }

    public void setiTipCons(short iTipCons) {
        this.iTipCons = iTipCons;
    }

    public BigInteger getdNumCons() {
        return dNumCons;
    }

    public void setdNumCons(BigInteger dNumCons) {
        this.dNumCons = dNumCons;
    }

    public String getdNumControl() {
        return dNumControl;
    }

    public void setdNumControl(String dNumControl) {
        this.dNumControl = dNumControl;
    }
}