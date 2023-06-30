package com.roshka.sifen.addon;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class rResEnviConsLoteDe implements Serializable
{
    public Date dFecProc;
    public int dCodResLot;
    public String dMsgResLot;

    public List<gResProcLote> gResProcLote;
    public String ns2;
    public String text;


    public Date getdFecProc() {
        return dFecProc;
    }

    public void setdFecProc(Date dFecProc) {
        this.dFecProc = dFecProc;
    }

    public int getdCodResLot() {
        return dCodResLot;
    }

    public void setdCodResLot(int dCodResLot) {
        this.dCodResLot = dCodResLot;
    }

    public String getdMsgResLot() {
        return dMsgResLot;
    }

    public void setdMsgResLot(String dMsgResLot) {
        this.dMsgResLot = dMsgResLot;
    }

    public List<gResProcLote> getgResProcLote() {
        return gResProcLote;
    }

    public void setgResProcLote(List<gResProcLote> gResProcLote) {
        this.gResProcLote = gResProcLote;
    }

    public String getNs2() {
        return ns2;
    }

    public void setNs2(String ns2) {
        this.ns2 = ns2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
