package com.roshka.sifen.addon;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class rResEnviLoteDe implements Serializable 
{
	public Date dFecProc;
	public int dCodRes;
	public String dMsgRes;
	public String dProtConsLote;
	public int dTpoProces;
	
	public Date getdFecProc() {
		return dFecProc;
	}
	public void setdFecProc(Date dFecProc) {
		this.dFecProc = dFecProc;
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
	public String getdProtConsLote() {
		return dProtConsLote;
	}
	public void setdProtConsLote(String dProtConsLote) {
		this.dProtConsLote = dProtConsLote;
	}
	public int getdTpoProces() {
		return dTpoProces;
	}
	public void setdTpoProces(int dTpoProces) {
		this.dTpoProces = dTpoProces;
	}
	
}
