package pojo;

import java.io.Serializable;

public class PosTrxEbBatchItem implements Serializable {
    /**
	 * POJO que representa una fila de la tabla POS_TRX_EB_BATCH_ITEM
	 */
	private static final long serialVersionUID = 1L;
	private long batchId;
    private long cashControlId;
    private long cashRegisterId;
    private String controlCode;
    private String createdBy;
    private java.util.Date createdOn;
    private long identifier;
    private long orgId;
    private long resultCode;
    private String resultMessage;
    private String resultStatus;
    private long resultTxNumber;
    private long transactionId;
    private long unitId;
    private String localDocNumber;

    public long getBatchId() {
        return this.batchId;
    }
    public void setBatchId ( long batchid ) {
        this.batchId = batchid;
    }

    public long getCashControlId() {
        return this.cashControlId;
    }
    public void setCashControlId ( long cashcontrolid ) {
        this.cashControlId = cashcontrolid;
    }

    public long getCashRegisterId() {
        return this.cashRegisterId;
    }
    public void setCashRegisterId ( long cashregisterid ) {
        this.cashRegisterId = cashregisterid;
    }

    public String getControlCode() {
        return this.controlCode;
    }
    public void setControlCode ( String controlcode ) {
        this.controlCode = controlcode;
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

    public long getResultCode() {
        return this.resultCode;
    }
    public void setResultCode ( long resultcode ) {
        this.resultCode = resultcode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }
    public void setResultMessage ( String resultmessage ) {
        this.resultMessage = resultmessage;
    }

    public String getResultStatus() {
        return this.resultStatus;
    }
    public void setResultStatus ( String resultstatus ) {
        this.resultStatus = resultstatus;
    }

    public long getResultTxNumber() {
        return this.resultTxNumber;
    }
    public void setResultTxNumber ( long resulttxnumber ) {
        this.resultTxNumber = resulttxnumber;
    }

    public long getTransactionId() {
        return this.transactionId;
    }
    public void setTransactionId ( long transactionid ) {
        this.transactionId = transactionid;
    }

    public long getUnitId() {
        return this.unitId;
    }
    public void setUnitId ( long unitid ) {
        this.unitId = unitid;
    }
	public String getLocalDocNumber() {
		return localDocNumber;
	}
	public void setLocalDocNumber(String localDocNumber) {
		this.localDocNumber = localDocNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}