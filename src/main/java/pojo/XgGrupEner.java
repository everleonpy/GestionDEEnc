package pojo;

import java.math.BigDecimal;

public class XgGrupEner{
    private String dNroMed;
    private short dActiv;
    private String dCateg;
    private BigDecimal dLecAnt;
    private BigDecimal dLecAct;
    private BigDecimal dConKwh;

    public String getdNroMed() {
        return dNroMed;
    }

    public void setdNroMed(String dNroMed) {
        this.dNroMed = dNroMed;
    }

    public short getdActiv() {
        return dActiv;
    }

    public void setdActiv(short dActiv) {
        this.dActiv = dActiv;
    }

    public String getdCateg() {
        return dCateg;
    }

    public void setdCateg(String dCateg) {
        this.dCateg = dCateg;
    }

    public BigDecimal getdLecAnt() {
        return dLecAnt;
    }

    public void setdLecAnt(BigDecimal dLecAnt) {
        this.dLecAnt = dLecAnt;
    }

    public BigDecimal getdLecAct() {
        return dLecAct;
    }

    public void setdLecAct(BigDecimal dLecAct) {
        this.dLecAct = dLecAct;
    }

    public BigDecimal getdConKwh() {
        return dConKwh;
    }

    public void setdConKwh(BigDecimal dConKwh) {
        this.dConKwh = dConKwh;
    }

}
