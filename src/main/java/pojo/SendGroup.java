package pojo;

public class SendGroup {
	private int groupNumber;
	private int txQuantity;
	private int sentQuantity;
	private int notSentQuantity;
	
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	public int getTxQuantity() {
		return txQuantity;
	}
	public void setTxQuantity(int txQuantity) {
		this.txQuantity = txQuantity;
	}
	public int getSentQuantity() {
		return sentQuantity;
	}
	public void setSentQuantity(int sentQuantity) {
		this.sentQuantity = sentQuantity;
	}
	public int getNotSentQuantity() {
		return notSentQuantity;
	}
	public void setNotSentQuantity(int notSentQuantity) {
		this.notSentQuantity = notSentQuantity;
	}
	
}
