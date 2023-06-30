package pojo;

import java.time.LocalDate;

/**
 * Campos de la nota de remisión electrónica
 */
public class XgCamNRE {
    private short iMotEmiNR;
    private short iRespEmiNR;
    private int dKmR;
    private String dFecEm;

    public short getiMotEmiNR() {
        return iMotEmiNR;
    }

    public void setiMotEmiNR(short iMotEmiNR) {
        this.iMotEmiNR = iMotEmiNR;
    }

    public short getiRespEmiNR() {
        return iRespEmiNR;
    }

    public void setiRespEmiNR(short iRespEmiNR) {
        this.iRespEmiNR = iRespEmiNR;
    }

    public int getdKmR() {
        return dKmR;
    }

    public void setdKmR(int dKmR) {
        this.dKmR = dKmR;
    }

    public String getdFecEm() {
        return dFecEm;
    }

    public void setdFecEm(String dFecEm) {
        this.dFecEm = dFecEm;
    }
}