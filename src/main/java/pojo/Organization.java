package pojo;

import java.util.ArrayList;

public class Organization {
	private long IDENTIFIER;
	private String NAME;
	private long OFFICIAL_CURRENCY_ID;
	private long LANGUAGE_ID;
	private String CREATED_BY;
	private java.util.Date CREATED_ON;
	private String MODIFIED_BY;
	private java.util.Date MODIFIED_ON;
	private String IS_WITHHOLDING_AGENT;
	private String IS_SELF_PRINTER;
	private String ALTERNATIVE_NAME;
	private String ADDRESS1;
	private String AREA_CODE;
	private String PHONE1;
	private String TAX_NUMBER;
	private String IDENTITY_NUMBER;
	private String CITY;
	private String COUNTRY;
	private long PRN_UNIT_ID;
	private String SYSTEM_ITEM;
	private long SECOND_CURRENCY_ID;
	private long THIRD_CURRENCY_ID;
	private long SEC_LANGUAGE_ID;
	private String ECONOMIC_ACTIVITY;
	private ArrayList<FndOrgAttribute> SEC_ACTIVITIES;
	
	public long getIDENTIFIER() {
		return IDENTIFIER;
	}
	public void setIDENTIFIER(long iDENTIFIER) {
		IDENTIFIER = iDENTIFIER;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public long getOFFICIAL_CURRENCY_ID() {
		return OFFICIAL_CURRENCY_ID;
	}
	public void setOFFICIAL_CURRENCY_ID(long oFFICIAL_CURRENCY_ID) {
		OFFICIAL_CURRENCY_ID = oFFICIAL_CURRENCY_ID;
	}
	public long getLANGUAGE_ID() {
		return LANGUAGE_ID;
	}
	public void setLANGUAGE_ID(long lANGUAGE_ID) {
		LANGUAGE_ID = lANGUAGE_ID;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public java.util.Date getCREATED_ON() {
		return CREATED_ON;
	}
	public void setCREATED_ON(java.util.Date cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}
	public String getMODIFIED_BY() {
		return MODIFIED_BY;
	}
	public void setMODIFIED_BY(String mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}
	public java.util.Date getMODIFIED_ON() {
		return MODIFIED_ON;
	}
	public void setMODIFIED_ON(java.util.Date mODIFIED_ON) {
		MODIFIED_ON = mODIFIED_ON;
	}
	public String getIS_WITHHOLDING_AGENT() {
		return IS_WITHHOLDING_AGENT;
	}
	public void setIS_WITHHOLDING_AGENT(String iS_WITHHOLDING_AGENT) {
		IS_WITHHOLDING_AGENT = iS_WITHHOLDING_AGENT;
	}
	public String getIS_SELF_PRINTER() {
		return IS_SELF_PRINTER;
	}
	public void setIS_SELF_PRINTER(String iS_SELF_PRINTER) {
		IS_SELF_PRINTER = iS_SELF_PRINTER;
	}
	public String getALTERNATIVE_NAME() {
		return ALTERNATIVE_NAME;
	}
	public void setALTERNATIVE_NAME(String aLTERNATIVE_NAME) {
		ALTERNATIVE_NAME = aLTERNATIVE_NAME;
	}
	public String getADDRESS1() {
		return ADDRESS1;
	}
	public void setADDRESS1(String aDDRESS1) {
		ADDRESS1 = aDDRESS1;
	}
	public String getAREA_CODE() {
		return AREA_CODE;
	}
	public void setAREA_CODE(String aREA_CODE) {
		AREA_CODE = aREA_CODE;
	}
	public String getPHONE1() {
		return PHONE1;
	}
	public void setPHONE1(String pHONE1) {
		PHONE1 = pHONE1;
	}
	public String getTAX_NUMBER() {
		return TAX_NUMBER;
	}
	public void setTAX_NUMBER(String tAX_NUMBER) {
		TAX_NUMBER = tAX_NUMBER;
	}
	public String getIDENTITY_NUMBER() {
		return IDENTITY_NUMBER;
	}
	public void setIDENTITY_NUMBER(String iDENTITY_NUMBER) {
		IDENTITY_NUMBER = iDENTITY_NUMBER;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public long getPRN_UNIT_ID() {
		return PRN_UNIT_ID;
	}
	public void setPRN_UNIT_ID(long pRN_UNIT_ID) {
		PRN_UNIT_ID = pRN_UNIT_ID;
	}
	public String getSYSTEM_ITEM() {
		return SYSTEM_ITEM;
	}
	public void setSYSTEM_ITEM(String sYSTEM_ITEM) {
		SYSTEM_ITEM = sYSTEM_ITEM;
	}
	public long getSECOND_CURRENCY_ID() {
		return SECOND_CURRENCY_ID;
	}
	public void setSECOND_CURRENCY_ID(long sECOND_CURRENCY_ID) {
		SECOND_CURRENCY_ID = sECOND_CURRENCY_ID;
	}
	public long getTHIRD_CURRENCY_ID() {
		return THIRD_CURRENCY_ID;
	}
	public void setTHIRD_CURRENCY_ID(long tHIRD_CURRENCY_ID) {
		THIRD_CURRENCY_ID = tHIRD_CURRENCY_ID;
	}
	public long getSEC_LANGUAGE_ID() {
		return SEC_LANGUAGE_ID;
	}
	public void setSEC_LANGUAGE_ID(long sEC_LANGUAGE_ID) {
		SEC_LANGUAGE_ID = sEC_LANGUAGE_ID;
	}
	public String getECONOMIC_ACTIVITY() {
		return ECONOMIC_ACTIVITY;
	}
	public void setECONOMIC_ACTIVITY(String eCONOMIC_ACTIVITY) {
		ECONOMIC_ACTIVITY = eCONOMIC_ACTIVITY;
	}
	public ArrayList<FndOrgAttribute> getSEC_ACTIVITIES() {
		return SEC_ACTIVITIES;
	}
	public void setSEC_ACTIVITIES(ArrayList<FndOrgAttribute> sEC_ACTIVITIES) {
		SEC_ACTIVITIES = sEC_ACTIVITIES;
	}

}
