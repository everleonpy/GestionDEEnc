package dao;

public class ProblemaDatosException extends Exception implements DatosException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorDescription;
	int numError;
	
	public ProblemaDatosException (int numError, String errorDescription) {
		this.numError = numError;
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String getErrorDescription() {
		return errorDescription;
	}

	@Override
	public int getNumError() {
		return numError;
	}
	
	
}
