package com.roshka.sifen.addon;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class rRetEnviEventoDe implements Serializable 
{
    private java.util.Date dFecProc;
    private List<gResProcEVe> gResProcEVe;
    
	public java.util.Date getdFecProc() {
		return dFecProc;
	}
	public void setdFecProc(java.util.Date dFecProc) {
		this.dFecProc = dFecProc;
	}
	public List<gResProcEVe> getgResProcEVe() {
		return gResProcEVe;
	}
	public void setgResProcEVe(List<gResProcEVe> gResProcEVe) {
		this.gResProcEVe = gResProcEVe;
	}

}
