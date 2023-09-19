package com.roshka.sifen.addon;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class gResProcEVe implements Serializable 
{
    private String dEstRes;
    private int dProtAut;
    // Corresponde al id autogenerado por el emisor, para identificar cada evento
    private String id;
    private List<gResProc> gResProc;
    private int dCodRes;
    private String dMsgRes;
    
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<gResProc> getgResProc() {
		return gResProc;
	}
	public void setgResProc(List<gResProc> gResProc) {
		this.gResProc = gResProc;
	}
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
