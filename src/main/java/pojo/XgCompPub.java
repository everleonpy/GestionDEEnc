package pojo;

import java.time.LocalDate;

public class XgCompPub {
    private String dModCont;
    private int dEntCont;
    private short dAnoCont;
    private int dSecCont;
    private LocalDate dFeCodCont;

    public String getdModCont() {
        return dModCont;
    }

    public void setdModCont(String dModCont) {
        this.dModCont = dModCont;
    }

    public int getdEntCont() {
        return dEntCont;
    }

    public void setdEntCont(int dEntCont) {
        this.dEntCont = dEntCont;
    }

    public short getdAnoCont() {
        return dAnoCont;
    }

    public void setdAnoCont(short dAnoCont) {
        this.dAnoCont = dAnoCont;
    }

    public int getdSecCont() {
        return dSecCont;
    }

    public void setdSecCont(int dSecCont) {
        this.dSecCont = dSecCont;
    }

    public LocalDate getdFeCodCont() {
        return dFeCodCont;
    }

    public void setdFeCodCont(LocalDate dFeCodCont) {
        this.dFeCodCont = dFeCodCont;
    }
}
