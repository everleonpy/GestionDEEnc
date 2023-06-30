package pojo;

public class XgOpeDE {
    private short iTipEmi;
    private String dCodSeg;         // número aleatorio entre 1 y 999999999 generado por el emisor
    private String dInfoEmi;        // información de interés del emisor (opcional). Entre 1 y 3000 carácteres
    private String dInfoFisc;       // información de interés del fisco (opcional). Entre 1 y 3000 carácteres

    public short getiTipEmi() {
        return iTipEmi;
    }

    public void setiTipEmi(short iTipEmi) {
        this.iTipEmi = iTipEmi;
    }

    public String getdCodSeg() {
        return dCodSeg;
    }

    public void setdCodSeg(String dCodSeg) {
        this.dCodSeg = dCodSeg;
    }

    public String getdInfoEmi() {
        return dInfoEmi;
    }

    public void setdInfoEmi(String dInfoEmi) {
        this.dInfoEmi = dInfoEmi;
    }

    public String getdInfoFisc() {
        return dInfoFisc;
    }

    public void setdInfoFisc(String dInfoFisc) {
        this.dInfoFisc = dInfoFisc;
    }
}