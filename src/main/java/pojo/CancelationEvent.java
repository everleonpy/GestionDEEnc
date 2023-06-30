package pojo;

public class CancelationEvent {
	private long transactionId;
	private long cashId;
	private long cashControlId;
	private String controlCode;
	private String cancelReason;
	
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
	public String getControlCode() {
		return controlCode;
	}
	public void setControlCode(String controlCode) {
		this.controlCode = controlCode;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
}
