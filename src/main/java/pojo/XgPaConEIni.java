package pojo;

import java.math.BigDecimal;

public class XgPaConEIni {
    private short iTiPago;
    private String dDesTiPag;
    private BigDecimal dMonTiPag;
    private String cMoneTiPag;
    private BigDecimal dTiCamTiPag;
    private XgPagTarCD gPagTarCD;
    private XgPagCheq gPagCheq;

    public short getiTiPago() {
        return iTiPago;
    }

    public void setiTiPago(short iTiPago) {
        this.iTiPago = iTiPago;
    }

    public String getdDesTiPag() {
        return dDesTiPag;
    }

    public void setdDesTiPag(String dDesTiPag) {
        this.dDesTiPag = dDesTiPag;
    }

    public BigDecimal getdMonTiPag() {
        return dMonTiPag;
    }

    public void setdMonTiPag(BigDecimal dMonTiPag) {
        this.dMonTiPag = dMonTiPag;
    }

    public String getcMoneTiPag() {
        return cMoneTiPag;
    }

    public void setcMoneTiPag(String cMoneTiPag) {
        this.cMoneTiPag = cMoneTiPag;
    }

    public BigDecimal getdTiCamTiPag() {
        return dTiCamTiPag;
    }

    public void setdTiCamTiPag(BigDecimal dTiCamTiPag) {
        this.dTiCamTiPag = dTiCamTiPag;
    }

    public XgPagTarCD getgPagTarCD() {
        return gPagTarCD;
    }

    public void setgPagTarCD(XgPagTarCD gPagTarCD) {
        this.gPagTarCD = gPagTarCD;
    }

    public XgPagCheq getgPagCheq() {
        return gPagCheq;
    }

    public void setgPagCheq(XgPagCheq gPagCheq) {
        this.gPagCheq = gPagCheq;
    }
}