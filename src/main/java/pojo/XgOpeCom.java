package pojo;

import java.math.BigDecimal;

public class XgOpeCom {
    private short iTipTra;
    private short iTImp;
    private String cMoneOpe;
    private short dCondTiCam; // opcional
    private BigDecimal dTiCam;
    private short iCondAnt;

    public short getiTipTra() {
        return iTipTra;
    }

    public void setiTipTra(short iTipTra) {
        this.iTipTra = iTipTra;
    }

    public short getiTImp() {
        return iTImp;
    }

    public void setiTImp(short iTImp) {
        this.iTImp = iTImp;
    }

    public String getcMoneOpe() {
        return cMoneOpe;
    }

    public void setcMoneOpe(String cMoneOpe) {
        this.cMoneOpe = cMoneOpe;
    }

    public short getdCondTiCam() {
        return dCondTiCam;
    }

    public void setdCondTiCam(short dCondTiCam) {
        this.dCondTiCam = dCondTiCam;
    }

    public BigDecimal getdTiCam() {
        return dTiCam;
    }

    public void setdTiCam(BigDecimal dTiCam) {
        this.dTiCam = dTiCam;
    }

    public short getiCondAnt() {
        return iCondAnt;
    }

    public void setiCondAnt(short iCondAnt) {
        this.iCondAnt = iCondAnt;
    }
}