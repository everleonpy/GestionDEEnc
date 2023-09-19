package com.roshka.sifen.addon;

import java.io.Serializable;


@SuppressWarnings("serial")
public class gResProc implements Serializable
{

	public int dCodRes;
    public String dMsgRes;

    public int getdCodRes() {
        return dCodRes;
    }

    public void setdCodRes(int dCodRes) {
        this.dCodRes = dCodRes;
    }

    public String getdMsgRes() {
        return dMsgRes;
    }

    public void setdMsgRes(String dMsgRes) {
        this.dMsgRes = dMsgRes;
    }
}