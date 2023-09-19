package com.roshka.sifen.addon;

import java.io.Serializable;

@SuppressWarnings("serial")
public class rResEnviConsRUC implements Serializable 
{
	private int dCodRes;
	private String dMsgRes;
	private rContRUC xContRUC;
	
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
	public rContRUC getxContRUC() {
		return xContRUC;
	}
	public void setxContRUC(rContRUC xContRUC) {
		this.xContRUC = xContRUC;
	}
	
}
