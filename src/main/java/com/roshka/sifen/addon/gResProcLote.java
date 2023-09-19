package com.roshka.sifen.addon;

import java.io.Serializable;


@SuppressWarnings("serial")
public class gResProcLote implements Serializable
{

	private String id;
    private String dEstRes;
    private int dProtAut;
    private gResProc gResProc;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getdEstRes() {
        return dEstRes;
    }

    public void setdEstRes(String dEstRes) {
        this.dEstRes = dEstRes;
    }

    public int getdProtAut() {
        return dProtAut;
    }

    public void setdProtAut(int dProtAut) {
        this.dProtAut = dProtAut;
    }

    public com.roshka.sifen.addon.gResProc getgResProc() {
        return gResProc;
    }

    public void setgResProc(gResProc gResProc) {
        this.gResProc = gResProc;
    }
}
