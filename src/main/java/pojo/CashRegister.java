package pojo;

public class CashRegister {
	private long IDENTIFIER;
	private long ORG_ID;
	private long UNIT_ID;
	private String CR_NUMBER;
	private String DESCRIPTION;
	private long SITE_ID;
	private String WORKSTATION_NAME;
	private String ACTIVE_FLAG;
	private String CREATED_BY;
	private java.util.Date CREATED_ON;
	private java.util.Date MODIFIED_ON;
	private String MODIFIED_BY;
	private long ISSUE_POINT_ID;
	private String CR_GROUP_CODE;
	private String ENABLE_JOURNAL;
	private String ENABLE_PAPER_CUT;
	private String ENABLE_DRAWER;
	private String ENABLE_SIMULT_OPEN;
	private String PRINT_MODE;
	private String PRINTER_TYPE;
	private String PRINTER_PORT_TYPE;
	private String PRINTER_PORT_NAME;
	private String PRINTER_MODEL;
	private long PRINT_SERVICE_ID;
	private String IS_LOCKED;
	private String DRAWER_PORT_TYPE;
	private String DRAWER_PORT_NAME;
	private String DRAWER_OPENING_CMD;
	private long SEQUENCE_NUMBER;
	private long MAX_UNIT_QUANTITIES;
	private String ENABLE_SCALE;
	private String SCALE_PORT;
	private String SCALE_TYPE;
	private String SCALE_MODEL;
	private String SCANNER_MODEL;
	private String DISPLAY_MODEL;
	private long DISPLAY_SIZE;
	private int CUTTING_LINES_QTY;
	private long WAREHOUSE_ID;
	private String PHYSICAL_INV_FLAG;
	private long PHYSICAL_INV_ID;
	private String SALES_ROOM_AREA;
	private String ADVERTISING_FLAG;
	private String GEN_CUST_ORDER_NO;
	private String SRCH_NEWS_ON_MENU_ENTRY;
	private String SRCH_NEWS_AT_TRX_END;
	private String ALLOW_OFFLINE_CARDS_AUTH;
	private int CARDS_AUTH_ATTEMPTS;
	private String ENABLE_CARDS_AUTH;
	//
	private String IP_ADDRESS;
	private long LAST_NEW_RECORD_ID;
	private long LAST_PRICE_CHG_ID;      
	private long LAST_DB_CMD_ID;         
	private long LAST_DD_CMD_ID;         

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
	public String getCR_NUMBER() {
		return CR_NUMBER;
	}
	public void setCR_NUMBER(String cR_NUMBER) {
		CR_NUMBER = cR_NUMBER;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public long getSITE_ID() {
		return SITE_ID;
	}
	public void setSITE_ID(long sITE_ID) {
		SITE_ID = sITE_ID;
	}
	public String getWORKSTATION_NAME() {
		return WORKSTATION_NAME;
	}
	public void setWORKSTATION_NAME(String wORKSTATION_NAME) {
		WORKSTATION_NAME = wORKSTATION_NAME;
	}
	public String getACTIVE_FLAG() {
		return ACTIVE_FLAG;
	}
	public void setACTIVE_FLAG(String aCTIVE_FLAG) {
		ACTIVE_FLAG = aCTIVE_FLAG;
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
	public long getISSUE_POINT_ID() {
		return ISSUE_POINT_ID;
	}
	public void setISSUE_POINT_ID(long iSSUE_POINT_ID) {
		ISSUE_POINT_ID = iSSUE_POINT_ID;
	}
	public String getCR_GROUP_CODE() {
		return CR_GROUP_CODE;
	}
	public void setCR_GROUP_CODE(String cR_GROUP_CODE) {
		CR_GROUP_CODE = cR_GROUP_CODE;
	}
	public String getENABLE_JOURNAL() {
		return ENABLE_JOURNAL;
	}
	public void setENABLE_JOURNAL(String eNABLE_JOURNAL) {
		ENABLE_JOURNAL = eNABLE_JOURNAL;
	}
	public String getENABLE_PAPER_CUT() {
		return ENABLE_PAPER_CUT;
	}
	public void setENABLE_PAPER_CUT(String eNABLE_PAPER_CUT) {
		ENABLE_PAPER_CUT = eNABLE_PAPER_CUT;
	}
	public String getENABLE_DRAWER() {
		return ENABLE_DRAWER;
	}
	public void setENABLE_DRAWER(String eNABLE_DRAWER) {
		ENABLE_DRAWER = eNABLE_DRAWER;
	}
	public String getENABLE_SIMULT_OPEN() {
		return ENABLE_SIMULT_OPEN;
	}
	public void setENABLE_SIMULT_OPEN(String eNABLE_SIMULT_OPEN) {
		ENABLE_SIMULT_OPEN = eNABLE_SIMULT_OPEN;
	}
	public String getPRINT_MODE() {
		return PRINT_MODE;
	}
	public void setPRINT_MODE(String pRINT_MODE) {
		PRINT_MODE = pRINT_MODE;
	}
	public String getPRINTER_TYPE() {
		return PRINTER_TYPE;
	}
	public void setPRINTER_TYPE(String pRINTER_TYPE) {
		PRINTER_TYPE = pRINTER_TYPE;
	}
	public String getPRINTER_PORT_TYPE() {
		return PRINTER_PORT_TYPE;
	}
	public void setPRINTER_PORT_TYPE(String pRINTER_PORT_TYPE) {
		PRINTER_PORT_TYPE = pRINTER_PORT_TYPE;
	}
	public String getPRINTER_PORT_NAME() {
		return PRINTER_PORT_NAME;
	}
	public void setPRINTER_PORT_NAME(String pRINTER_PORT_NAME) {
		PRINTER_PORT_NAME = pRINTER_PORT_NAME;
	}
	public String getPRINTER_MODEL() {
		return PRINTER_MODEL;
	}
	public void setPRINTER_MODEL(String pRINTER_MODEL) {
		PRINTER_MODEL = pRINTER_MODEL;
	}
	public long getPRINT_SERVICE_ID() {
		return PRINT_SERVICE_ID;
	}
	public void setPRINT_SERVICE_ID(long pRINT_SERVICE_ID) {
		PRINT_SERVICE_ID = pRINT_SERVICE_ID;
	}
	public String getIS_LOCKED() {
		return IS_LOCKED;
	}
	public void setIS_LOCKED(String iS_LOCKED) {
		IS_LOCKED = iS_LOCKED;
	}
	public String getDRAWER_PORT_TYPE() {
		return DRAWER_PORT_TYPE;
	}
	public void setDRAWER_PORT_TYPE(String dRAWER_PORT_TYPE) {
		DRAWER_PORT_TYPE = dRAWER_PORT_TYPE;
	}
	public String getDRAWER_PORT_NAME() {
		return DRAWER_PORT_NAME;
	}
	public void setDRAWER_PORT_NAME(String dRAWER_PORT_NAME) {
		DRAWER_PORT_NAME = dRAWER_PORT_NAME;
	}
	public String getDRAWER_OPENING_CMD() {
		return DRAWER_OPENING_CMD;
	}
	public void setDRAWER_OPENING_CMD(String dRAWER_OPENING_CMD) {
		DRAWER_OPENING_CMD = dRAWER_OPENING_CMD;
	}
	public long getSEQUENCE_NUMBER() {
		return SEQUENCE_NUMBER;
	}
	public void setSEQUENCE_NUMBER(long sEQUENCE_NUMBER) {
		SEQUENCE_NUMBER = sEQUENCE_NUMBER;
	}
	public long getMAX_UNIT_QUANTITIES() {
		return MAX_UNIT_QUANTITIES;
	}
	public void setMAX_UNIT_QUANTITIES(long mAX_UNIT_QUANTITIES) {
		MAX_UNIT_QUANTITIES = mAX_UNIT_QUANTITIES;
	}
	public String getENABLE_SCALE() {
		return ENABLE_SCALE;
	}
	public void setENABLE_SCALE(String eNABLE_SCALE) {
		ENABLE_SCALE = eNABLE_SCALE;
	}
	public String getSCALE_PORT() {
		return SCALE_PORT;
	}
	public void setSCALE_PORT(String sCALE_PORT) {
		SCALE_PORT = sCALE_PORT;
	}
	public String getSCALE_TYPE() {
		return SCALE_TYPE;
	}
	public void setSCALE_TYPE(String sCALE_TYPE) {
		SCALE_TYPE = sCALE_TYPE;
	}
	public String getSCALE_MODEL() {
		return SCALE_MODEL;
	}
	public void setSCALE_MODEL(String sCALE_MODEL) {
		SCALE_MODEL = sCALE_MODEL;
	}
	public String getSCANNER_MODEL() {
		return SCANNER_MODEL;
	}
	public void setSCANNER_MODEL(String sCANNER_MODEL) {
		SCANNER_MODEL = sCANNER_MODEL;
	}
	public String getDISPLAY_MODEL() {
		return DISPLAY_MODEL;
	}
	public void setDISPLAY_MODEL(String dISPLAY_MODEL) {
		DISPLAY_MODEL = dISPLAY_MODEL;
	}
	public long getDISPLAY_SIZE() {
		return DISPLAY_SIZE;
	}
	public void setDISPLAY_SIZE(long dISPLAY_SIZE) {
		DISPLAY_SIZE = dISPLAY_SIZE;
	}
	public int getCUTTING_LINES_QTY() {
		return CUTTING_LINES_QTY;
	}
	public void setCUTTING_LINES_QTY(int cUTTING_LINES_QTY) {
		CUTTING_LINES_QTY = cUTTING_LINES_QTY;
	}
	public long getWAREHOUSE_ID() {
		return WAREHOUSE_ID;
	}
	public void setWAREHOUSE_ID(long wAREHOUSE_ID) {
		WAREHOUSE_ID = wAREHOUSE_ID;
	}
	public String getPHYSICAL_INV_FLAG() {
		return PHYSICAL_INV_FLAG;
	}
	public void setPHYSICAL_INV_FLAG(String pHYSICAL_INV_FLAG) {
		PHYSICAL_INV_FLAG = pHYSICAL_INV_FLAG;
	}
	public long getPHYSICAL_INV_ID() {
		return PHYSICAL_INV_ID;
	}
	public void setPHYSICAL_INV_ID(long pHISICAL_INV_ID) {
		PHYSICAL_INV_ID = pHISICAL_INV_ID;
	}
	public String getSALES_ROOM_AREA() {
		return SALES_ROOM_AREA;
	}
	public void setSALES_ROOM_AREA(String sALES_ROOM_AREA) {
		SALES_ROOM_AREA = sALES_ROOM_AREA;
	}
	public String getADVERTISING_FLAG() {
		return ADVERTISING_FLAG;
	}
	public void setADVERTISING_FLAG(String aDVERTISING_FLAG) {
		ADVERTISING_FLAG = aDVERTISING_FLAG;
	}
	public String getGEN_CUST_ORDER_NO() {
		return GEN_CUST_ORDER_NO;
	}
	public void setGEN_CUST_ORDER_NO(String gEN_CUST_ORDER_NO) {
		GEN_CUST_ORDER_NO = gEN_CUST_ORDER_NO;
	}
	public String getSRCH_NEWS_ON_MENU_ENTRY() {
		return SRCH_NEWS_ON_MENU_ENTRY;
	}
	public void setSRCH_NEWS_ON_MENU_ENTRY(String sRCH_NEWS_ON_MENU_ENTRY) {
		SRCH_NEWS_ON_MENU_ENTRY = sRCH_NEWS_ON_MENU_ENTRY;
	}
	public String getSRCH_NEWS_AT_TRX_END() {
		return SRCH_NEWS_AT_TRX_END;
	}
	public void setSRCH_NEWS_AT_TRX_END(String sRCH_NEWS_AT_TRX_END) {
		SRCH_NEWS_AT_TRX_END = sRCH_NEWS_AT_TRX_END;
	}
	public String getENABLE_CARDS_AUTH() {
		return ENABLE_CARDS_AUTH;
	}
	public void setENABLE_CARDS_AUTH(String eNABLE_CARDS_AUTH) {
		ENABLE_CARDS_AUTH = eNABLE_CARDS_AUTH;
	}
	public String getIP_ADDRESS() {
		return IP_ADDRESS;
	}
	public void setIP_ADDRESS(String iP_ADDRESS) {
		IP_ADDRESS = iP_ADDRESS;
	}
	public long getLAST_NEW_RECORD_ID() {
		return LAST_NEW_RECORD_ID;
	}
	public void setLAST_NEW_RECORD_ID(long lAST_NEW_RECORD_ID) {
		LAST_NEW_RECORD_ID = lAST_NEW_RECORD_ID;
	}
	public long getLAST_PRICE_CHG_ID() {
		return LAST_PRICE_CHG_ID;
	}
	public void setLAST_PRICE_CHG_ID(long lAST_PRICE_CHG_ID) {
		LAST_PRICE_CHG_ID = lAST_PRICE_CHG_ID;
	}
	public long getLAST_DB_CMD_ID() {
		return LAST_DB_CMD_ID;
	}
	public void setLAST_DB_CMD_ID(long lAST_DB_CMD_ID) {
		LAST_DB_CMD_ID = lAST_DB_CMD_ID;
	}
	public long getLAST_DD_CMD_ID() {
		return LAST_DD_CMD_ID;
	}
	public void setLAST_DD_CMD_ID(long lAST_DD_CMD_ID) {
		LAST_DD_CMD_ID = lAST_DD_CMD_ID;
	}
	public String getALLOW_OFFLINE_CARDS_AUTH() {
		return ALLOW_OFFLINE_CARDS_AUTH;
	}
	public void setALLOW_OFFLINE_CARDS_AUTH(String aLLOW_OFFLINE_CARDS_AUTH) {
		ALLOW_OFFLINE_CARDS_AUTH = aLLOW_OFFLINE_CARDS_AUTH;
	}
	public int getCARDS_AUTH_ATTEMPTS() {
		return CARDS_AUTH_ATTEMPTS;
	}
	public void setCARDS_AUTH_ATTEMPTS(int cARDS_AUTH_ATTEMPTS) {
		CARDS_AUTH_ATTEMPTS = cARDS_AUTH_ATTEMPTS;
	}

}
