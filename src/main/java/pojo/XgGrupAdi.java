package pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class XgGrupAdi {
    private String dCiclo;
    private String dFecIniC;
    private String dFecFinC;
    private List<String> dVencPagList;
    private String dContrato;
    private BigDecimal dSalAnt;

    public String getdCiclo() {
        return dCiclo;
    }

    public void setdCiclo(String dCiclo) {
        this.dCiclo = dCiclo;
    }

    public String getdFecIniC() {
        return dFecIniC;
    }

    public void setdFecIniC(String dFecIniC) {
        this.dFecIniC = dFecIniC;
    }

    public String getdFecFinC() {
        return dFecFinC;
    }

    public void setdFecFinC(String dFecFinC) {
        this.dFecFinC = dFecFinC;
    }

    public List<String> getdVencPagList() {
        return dVencPagList;
    }

    public void setdVencPagList(List<String> dVencPagList) {
        this.dVencPagList = dVencPagList;
    }

    public String getdContrato() {
        return dContrato;
    }

    public void setdContrato(String dContrato) {
        this.dContrato = dContrato;
    }

    public BigDecimal getdSalAnt() {
        return dSalAnt;
    }

    public void setdSalAnt(BigDecimal dSalAnt) {
        this.dSalAnt = dSalAnt;
    }
}