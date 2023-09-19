package com.roshka.sifen.addon;

import java.io.Serializable;

@SuppressWarnings("serial")
public class rContRUC implements Serializable 
{
	private String dRUCCons;
	private String dRazCons;
	private String dCodEstCons;
	private String dDesEstCons;
	private String dRUCFactElec;
	
	public String getdRUCCons() {
		return dRUCCons;
	}
	public void setdRUCCons(String dRUCCons) {
		this.dRUCCons = dRUCCons;
	}
	public String getdRazCons() {
		return dRazCons;
	}
	public void setdRazCons(String dRazCons) {
		this.dRazCons = dRazCons;
	}
	public String getdCodEstCons() {
		return dCodEstCons;
	}
	public void setdCodEstCons(String dCodEstCons) {
		this.dCodEstCons = dCodEstCons;
	}
	public String getdDesEstCons() {
		return dDesEstCons;
	}
	public void setdDesEstCons(String dDesEstCons) {
		this.dDesEstCons = dDesEstCons;
	}
	public String getdRUCFactElec() {
		return dRUCFactElec;
	}
	public void setdRUCFactElec(String dRUCFactElec) {
		this.dRUCFactElec = dRUCFactElec;
	}
	
}
