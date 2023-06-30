package pojo;

public class PrepareTxParams {
    private long batchId;
    private int rowsQty;
    private int firstGroup;
    private int lastGroup;
    
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public int getRowsQty() {
		return rowsQty;
	}
	public void setRowsQty(int rowsQty) {
		this.rowsQty = rowsQty;
	}
	public int getFirstGroup() {
		return firstGroup;
	}
	public void setFirstGroup(int firstGroup) {
		this.firstGroup = firstGroup;
	}
	public int getLastGroup() {
		return lastGroup;
	}
	public void setLastGroup(int lastGroup) {
		this.lastGroup = lastGroup;
	}
}
