package pojo;

public class DisablingEvent {
    private long transactionId;
    private long cashId;
    private long cashControlId;
    private int stampNo;
    private String estabCode;
    private String issuePointCode;
    private String firstNumber;
    private String lastNumber;
    private short txTypeId;
    private String disablingReason;
    
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public long getCashId() {
		return cashId;
	}
	public void setCashId(long cashId) {
		this.cashId = cashId;
	}
	public long getCashControlId() {
		return cashControlId;
	}
	public void setCashControlId(long cashControlId) {
		this.cashControlId = cashControlId;
	}
	public int getStampNo() {
		return stampNo;
	}
	public void setStampNo(int stampNo) {
		this.stampNo = stampNo;
	}
	public String getEstabCode() {
		return estabCode;
	}
	public void setEstabCode(String estabCode) {
		this.estabCode = estabCode;
	}
	public String getIssuePointCode() {
		return issuePointCode;
	}
	public void setIssuePointCode(String issuePointCode) {
		this.issuePointCode = issuePointCode;
	}
	public String getFirstNumber() {
		return firstNumber;
	}
	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}
	public String getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(String lastNumber) {
		this.lastNumber = lastNumber;
	}
	public short getTxTypeId() {
		return txTypeId;
	}
	public void setTxTypeId(short txTypeId) {
		this.txTypeId = txTypeId;
	}
	public String getDisablingReason() {
		return disablingReason;
	}
	public void setDisablingReason(String disablingReason) {
		this.disablingReason = disablingReason;
	}
        
}
