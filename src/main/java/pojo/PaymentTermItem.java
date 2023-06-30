package pojo;

public class PaymentTermItem {
	private short termId;
	private double amount;
	private short cardMark;
	private String checkNumber;
	private String checkBank;
	
	public short getTermId() {
		return termId;
	}
	public void setTermId(short termId) {
		this.termId = termId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public short getCardMark() {
		return cardMark;
	}
	public void setCardMark(short cardMark) {
		this.cardMark = cardMark;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getCheckBank() {
		return checkBank;
	}
	public void setCheckBank(String checkBank) {
		this.checkBank = checkBank;
	}

}
