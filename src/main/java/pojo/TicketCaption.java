package pojo;

public class TicketCaption {
	private long IDENTIFIER;
	private long ORG_ID;
	private long UNIT_ID;
	private String LOCATION;
	private long LINE_NUMBER;
	private String EMPTY_FLAG;
	private java.util.Date FROM_DATE;
	private String CREATED_BY;
	private java.util.Date CREATED_ON;
	private java.util.Date MODIFIED_ON;
	private String MODIFIED_BY;
	private String TEXT;
	private String ALIGNMENT;
	private java.util.Date TO_DATE;
	
	public long getIDENTIFIER() {
		return IDENTIFIER;
	}
	public void setIDENTIFIER(long iDENTIFIER) {
		IDENTIFIER = iDENTIFIER;
	}
	public long getORG_ID() {
		return ORG_ID;
	}
	public void setORG_ID(long oRG_ID) {
		ORG_ID = oRG_ID;
	}
	public long getUNIT_ID() {
		return UNIT_ID;
	}
	public void setUNIT_ID(long uNIT_ID) {
		UNIT_ID = uNIT_ID;
	}
	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	public long getLINE_NUMBER() {
		return LINE_NUMBER;
	}
	public void setLINE_NUMBER(long lINE_NUMBER) {
		LINE_NUMBER = lINE_NUMBER;
	}
	public String getEMPTY_FLAG() {
		return EMPTY_FLAG;
	}
	public void setEMPTY_FLAG(String eMPTY_FLAG) {
		EMPTY_FLAG = eMPTY_FLAG;
	}
	public java.util.Date getFROM_DATE() {
		return FROM_DATE;
	}
	public void setFROM_DATE(java.util.Date fROM_DATE) {
		FROM_DATE = fROM_DATE;
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
	public java.util.Date getMODIFIED_ON() {
		return MODIFIED_ON;
	}
	public void setMODIFIED_ON(java.util.Date mODIFIED_ON) {
		MODIFIED_ON = mODIFIED_ON;
	}
	public String getMODIFIED_BY() {
		return MODIFIED_BY;
	}
	public void setMODIFIED_BY(String mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}
	public String getTEXT() {
		return TEXT;
	}
	public void setTEXT(String tEXT) {
		TEXT = tEXT;
	}
	public String getALIGNMENT() {
		return ALIGNMENT;
	}
	public void setALIGNMENT(String aLIGNMENT) {
		ALIGNMENT = aLIGNMENT;
	}
	public java.util.Date getTO_DATE() {
		return TO_DATE;
	}
	public void setTO_DATE(java.util.Date tO_DATE) {
		TO_DATE = tO_DATE;
	}

}
