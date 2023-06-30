package pojo;

public class ListOfValuesParameters {
	private String entity; 
	private String title; 
	private long unitId;
	private long referenceId;
	private String searchString;
	private long [] numFilters; 
	private String [] charFilters;
	private boolean autoDisplay;
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(long referenceId) {
		this.referenceId = referenceId;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public long[] getNumFilters() {
		return numFilters;
	}
	public void setNumFilters(long[] numFilters) {
		this.numFilters = numFilters;
	}
	public String[] getCharFilters() {
		return charFilters;
	}
	public void setCharFilters(String[] charFilters) {
		this.charFilters = charFilters;
	}
	public boolean isAutoDisplay() {
		return autoDisplay;
	}
	public void setAutoDisplay(boolean autoDisplay) {
		this.autoDisplay = autoDisplay;
	}
	
}
