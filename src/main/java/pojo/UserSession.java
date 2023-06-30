package pojo;

public class UserSession {
	private long IDENTIFIER;
	private long ORG_ID;
	private long UNIT_ID;
	private String USER_NAME;
	private long CASH_REGISTER_ID;
	private String WORK_STATION;
	private java.util.Date START_DATE;
	private java.util.Date END_DATE;
	private String NORMAL_EXIT;
	private long CLOSING_HEADTLLR_ID;
	private java.util.Date CLOSING_DATE;
	private String PRINT_HEADER_FLAG;
	
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
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public long getCASH_REGISTER_ID() {
		return CASH_REGISTER_ID;
	}
	public void setCASH_REGISTER_ID(long cASH_REGISTER_ID) {
		CASH_REGISTER_ID = cASH_REGISTER_ID;
	}
	public String getWORK_STATION() {
		return WORK_STATION;
	}
	public void setWORK_STATION(String wORK_STATION) {
		WORK_STATION = wORK_STATION;
	}
	public java.util.Date getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(java.util.Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public java.util.Date getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(java.util.Date eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getNORMAL_EXIT() {
		return NORMAL_EXIT;
	}
	public void setNORMAL_EXIT(String nORMAL_EXIT) {
		NORMAL_EXIT = nORMAL_EXIT;
	}
	public long getCLOSING_HEADTLLR_ID() {
		return CLOSING_HEADTLLR_ID;
	}
	public void setCLOSING_HEADTLLR_ID(long cLOSING_HEADTLLR_ID) {
		CLOSING_HEADTLLR_ID = cLOSING_HEADTLLR_ID;
	}
	public java.util.Date getCLOSING_DATE() {
		return CLOSING_DATE;
	}
	public void setCLOSING_DATE(java.util.Date cLOSING_DATE) {
		CLOSING_DATE = cLOSING_DATE;
	}
	public String getPRINT_HEADER_FLAG() {
		return PRINT_HEADER_FLAG;
	}
	public void setPRINT_HEADER_FLAG(String pRINT_HEADER_FLAG) {
		PRINT_HEADER_FLAG = pRINT_HEADER_FLAG;
	}
}
