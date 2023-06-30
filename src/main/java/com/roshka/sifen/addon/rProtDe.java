package com.roshka.sifen.addon;

import java.util.Date;

public class rProtDe {
    public double Id;
    public Date dFecProc;
    public String dDigVal;
    public String dEstRes;
    public long dProtAut;

	public gResProc gResProc;

    public double getId() {
        return Id;
    }

    public void setId(double id) {
        Id = id;
    }

    public Date getdFecProc() {
        return dFecProc;
    }

    public void setdFecProc(Date dFecProc) {
        this.dFecProc = dFecProc;
    }

    public String getdDigVal() {
        return dDigVal;
    }

    public void setdDigVal(String dDigVal) {
        this.dDigVal = dDigVal;
    }

    public String getdEstRes() {
        return dEstRes;
    }

    public void setdEstRes(String dEstRes) {
        this.dEstRes = dEstRes;
    }

    public long getdProtAut() {
		return dProtAut;
	}

	public void setdProtAut(long dProtAut) {
		this.dProtAut = dProtAut;
	}
    
    public com.roshka.sifen.addon.gResProc getgResProc() {
        return gResProc;
    }

    public void setgResProc(com.roshka.sifen.addon.gResProc gResProc) {
        this.gResProc = gResProc;
    }
}
