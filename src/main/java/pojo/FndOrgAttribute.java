package pojo;

public class FndOrgAttribute {
	private long IDENTIFIER;     
	private long ORG_ID;         
	private String ATTR_TYPE;      
	private String ATTR_VALUE;     
	private String CREATED_BY;     
	private java.util.Date CREATED_ON;     
	private String MODIFIED_BY;
	private java.util.Date MODIFIED_ON;
	
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
	public String getATTR_TYPE() {
		return ATTR_TYPE;
	}
	public void setATTR_TYPE(String aTTR_TYPE) {
		ATTR_TYPE = aTTR_TYPE;
	}
	public String getATTR_VALUE() {
		return ATTR_VALUE;
	}
	public void setATTR_VALUE(String aTTR_VALUE) {
		ATTR_VALUE = aTTR_VALUE;
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
}
