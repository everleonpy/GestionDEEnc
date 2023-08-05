package pojo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class XgCuotas {
    private String cMoneCuo;
    private BigDecimal dMonCuota;
    private String dVencCuo;

    public String getcMoneCuo() {
        return cMoneCuo;
    }

    public void setcMoneCuo(String cMoneCuo) {
        this.cMoneCuo = cMoneCuo;
    }

    public BigDecimal getdMonCuota() {
        return dMonCuota.setScale(4, RoundingMode.UNNECESSARY);
    }

    public void setdMonCuota(BigDecimal dMonCuota) {
        this.dMonCuota = dMonCuota.setScale(4, RoundingMode.UNNECESSARY);
    }

    public String getdVencCuo() {
        return dVencCuo;
    }

    public void setdVencCuo(String dVencCuo) {
        this.dVencCuo = dVencCuo;
    }
}
