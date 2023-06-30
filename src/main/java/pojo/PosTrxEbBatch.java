package pojo;

import java.io.Serializable;

public class PosTrxEbBatch implements Serializable {
    /**
	 * POJO que representa una fila de la tabla POS_TRX_EB_BATCH
	 */
	private static final long serialVersionUID = 1L;
	private String batchNumber;
    private String createdBy;
    private java.util.Date createdOn;
    private long identifier;
    private long orgId;
    private java.util.Date transmissDate;
    private long unitId;
    private int resultCode;
    private String resultMessage;
    private int processTime;
    private String queriedFlag;
    private String trxType;
    private java.util.Date trxDate;
    private int itemsQty;
    private String refNumber;
    
    public String getBatchNumber() {
        return this.batchNumber;
    }
    public void setBatchNumber ( String batchnumber ) {
        this.batchNumber = batchnumber;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy ( String createdby ) {
        this.createdBy = createdby;
    }

    public java.util.Date getCreatedOn() {
        return this.createdOn;
    }
    public void setCreatedOn ( java.util.Date createdon ) {
        this.createdOn = createdon;
    }

    public long getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier ( long identifier ) {
        this.identifier = identifier;
    }

    public long getOrgId() {
        return this.orgId;
    }
    public void setOrgId ( long orgid ) {
        this.orgId = orgid;
    }

    public java.util.Date getTransmissDate() {
        return this.transmissDate;
    }
    public void setTransmissDate ( java.util.Date transmissdate ) {
        this.transmissDate = transmissdate;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	public String getQueriedFlag() {
		return queriedFlag;
	}
	public void setQueriedFlag(String queriedFlag) {
		this.queriedFlag = queriedFlag;
	}
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public java.util.Date getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}
	public int getItemsQty() {
		return itemsQty;
	}
	public void setItemsQty(int itemsQty) {
		this.itemsQty = itemsQty;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}