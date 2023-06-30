package pojo;

import util.UtilPOS;

public class XgPagCheq {
    private String dNumCheq;
    private String dBcoEmi;

    public String getdNumCheq() {
        return dNumCheq;
    }

    public void setdNumCheq(String dNumCheq) {
        //this.dNumCheq = SifenUtil.leftPad(dNumCheq, '0', 8);
        this.dNumCheq = UtilPOS.paddingString(dNumCheq, 8, '0', true);
    }

    public String getdBcoEmi() {
        return dBcoEmi;
    }

    public void setdBcoEmi(String dBcoEmi) {
        this.dBcoEmi = dBcoEmi;
    }
}