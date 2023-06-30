package pojo;

import java.math.BigDecimal;
import java.util.List;


public class XgPagCred {
    private short iCondCred;
    private String dPlazoCre;
    private short dCuotas;
    private BigDecimal dMonEnt;
    private List<XgCuotas> gCuotasList;

    public short getiCondCred() {
        return iCondCred;
    }

    public void setiCondCred(short iCondCred) {
        this.iCondCred = iCondCred;
    }

    public String getdPlazoCre() {
        return dPlazoCre;
    }

    public void setdPlazoCre(String dPlazoCre) {
        this.dPlazoCre = dPlazoCre;
    }

    public short getdCuotas() {
        return dCuotas;
    }

    public void setdCuotas(short dCuotas) {
        this.dCuotas = dCuotas;
    }

    public BigDecimal getdMonEnt() {
        return dMonEnt;
    }

    public void setdMonEnt(BigDecimal dMonEnt) {
        this.dMonEnt = dMonEnt;
    }

    public List<XgCuotas> getgCuotasList() {
        return gCuotasList;
    }

    public void setgCuotasList(List<XgCuotas> gCuotasList) {
        this.gCuotasList = gCuotasList;
    }
}
