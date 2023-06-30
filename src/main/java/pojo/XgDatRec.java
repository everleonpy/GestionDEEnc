package pojo;

import util.UtilPOS;

public class XgDatRec {
    private short iNatRec;
    private short iTiOpe;
    private String cPaisRec;
    private short iTiContRec;
    private String dRucRec;
    private short dDVRec;
    private short iTipIDRec;
    private String dDTipIDRec;
    private String dNumIDRec;
    private String dNomRec;
    private String dNomFanRec;
    private String dDirRec;
    private int dNumCasRec;
    private short cDepRec;
    private short cDisRec;
    private String dDesDisRec;
    private int cCiuRec;
    private String dDesCiuRec;
    private String dTelRec;
    private String dCelRec;
    private String dEmailRec;
    private String dCodCliente;

    public short getiNatRec() {
        return iNatRec;
    }

    public void setiNatRec(short iNatRec) {
        this.iNatRec = iNatRec;
    }

    public short getiTiOpe() {
        return iTiOpe;
    }

    public void setiTiOpe(short iTiOpe) {
        this.iTiOpe = iTiOpe;
    }

    public String getcPaisRec() {
        return cPaisRec;
    }

    public void setcPaisRec(String cPaisRec) {
        this.cPaisRec = cPaisRec;
    }

    public short getiTiContRec() {
        return iTiContRec;
    }

    public void setiTiContRec(short iTiContRec) {
        this.iTiContRec = iTiContRec;
    }

    public String getdRucRec() {
        return dRucRec;
    }

    public void setdRucRec(String dRucRec) {
        this.dRucRec = dRucRec;
    }

    public short getdDVRec() {
        return dDVRec;
    }

    public void setdDVRec(short dDVRec) {
        this.dDVRec = dDVRec;
    }

    public short getiTipIDRec() {
        return iTipIDRec;
    }

    public void setiTipIDRec(short iTipIDRec) {
        this.iTipIDRec = iTipIDRec;
    }

    public String getdDTipIDRec() {
        return dDTipIDRec;
    }

    public void setdDTipIDRec(String dDTipIDRec) {
        this.dDTipIDRec = dDTipIDRec;
    }

    public String getdNumIDRec() {
        return dNumIDRec;
    }

    public void setdNumIDRec(String dNumIDRec) {
        this.dNumIDRec = dNumIDRec;
    }

    public String getdNomRec() {
        return dNomRec;
    }

    public void setdNomRec(String dNomRec) {
        this.dNomRec = dNomRec;
    }

    public String getdNomFanRec() {
        return dNomFanRec;
    }

    public void setdNomFanRec(String dNomFanRec) {
        this.dNomFanRec = dNomFanRec;
    }

    public String getdDirRec() {
        return dDirRec;
    }

    public void setdDirRec(String dDirRec) {
        this.dDirRec = dDirRec;
    }

    public int getdNumCasRec() {
        return dNumCasRec;
    }

    public void setdNumCasRec(int dNumCasRec) {
        this.dNumCasRec = dNumCasRec;
    }

    public short getcDepRec() {
        return cDepRec;
    }

    public void setcDepRec(short cDepRec) {
        this.cDepRec = cDepRec;
    }

    public short getcDisRec() {
        return cDisRec;
    }

    public void setcDisRec(short cDisRec) {
        this.cDisRec = cDisRec;
    }

    public String getdDesDisRec() {
        return dDesDisRec;
    }

    public void setdDesDisRec(String dDesDisRec) {
        this.dDesDisRec = dDesDisRec;
    }

    public int getcCiuRec() {
        return cCiuRec;
    }

    public void setcCiuRec(int cCiuRec) {
        this.cCiuRec = cCiuRec;
    }

    public String getdDesCiuRec() {
        return dDesCiuRec;
    }

    public void setdDesCiuRec(String dDesCiuRec) {
        this.dDesCiuRec = dDesCiuRec;
    }

    public String getdTelRec() {
        return dTelRec;
    }

    public void setdTelRec(String dTelRec) {
        this.dTelRec = dTelRec;
    }

    public String getdCelRec() {
        return dCelRec;
    }

    public void setdCelRec(String dCelRec) {
        this.dCelRec = dCelRec;
    }

    public String getdEmailRec() {
        return dEmailRec;
    }

    public void setdEmailRec(String dEmailRec) {
        this.dEmailRec = dEmailRec;
    }

    public String getdCodCliente() {
        return dCodCliente;
    }

    public void setdCodCliente(String dCodCliente) {
        //this.dCodCliente = SifenUtil.leftPad(dCodCliente, '0', 3);
        this.dCodCliente = UtilPOS.paddingString(dCodCliente, 3, '0', true);
    }
}
