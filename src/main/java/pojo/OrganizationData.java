package pojo;

import java.util.ArrayList;

public class OrganizationData {
	private String taxNumber;
	private short checkDigit;
	private short taxPayerType;
	private short regimeType;
	private String name;
	private String alternativeName;
	private String eMail;
	//private ArrayList<EconomicActivity> activList;
	private ArrayList<CamposActivEconomica> activList;
	
	public String getTaxNumber() {
		return taxNumber;
	}
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	public short getCheckDigit() {
		return checkDigit;
	}
	public void setCheckDigit(short checkDigit) {
		this.checkDigit = checkDigit;
	}
	public short getTaxPayerType() {
		return taxPayerType;
	}
	public void setTaxPayerType(short taxPayerType) {
		this.taxPayerType = taxPayerType;
	}
	public short getRegimeType() {
		return regimeType;
	}
	public void setRegimeType(short regimeType) {
		this.regimeType = regimeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public void setAlternativeName(String alternativeName) {
		this.alternativeName = alternativeName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	/*
	public ArrayList<EconomicActivity> getActivList() {
		return activList;
	}
	public void setActivList(ArrayList<EconomicActivity> activList) {
		this.activList = activList;
	}
	*/
	public ArrayList<CamposActivEconomica> getActivList() {
		return activList;
	}
	public void setActivList(ArrayList<CamposActivEconomica> activList) {
		this.activList = activList;
	}

}
