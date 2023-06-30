package pojo;

import java.util.List;

public class XgGrupSeg {
    private String dCodEmpSeg;
    private List<XgGrupPolSeg> gGrupPolSegList;

    public String getdCodEmpSeg() {
        return dCodEmpSeg;
    }

    public void setdCodEmpSeg(String dCodEmpSeg) {
        this.dCodEmpSeg = dCodEmpSeg;
    }

    public List<XgGrupPolSeg> getgGrupPolSegList() {
        return gGrupPolSegList;
    }

    public void setgGrupPolSegList(List<XgGrupPolSeg> gGrupPolSegList) {
        this.gGrupPolSegList = gGrupPolSegList;
    }
}