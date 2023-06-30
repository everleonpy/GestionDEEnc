package pojo;

public class CamposPagoCheque {
	// +------------------------------------------------------------------------------------------+
	// | Grupo       | E7.1.2                                                                     |
	// | ID          | E630	                                                                      |
	// | Padre       | E605                                                                       |
	// | Campo       | gPagCheq                                                                   |
	// | Descripcion | Campos que describen el pago o entrega inicial de la operación con cheque  |
	// +------------------------------------------------------------------------------------------+
	private String dNumCheq;
	private String dBcoEmi;
	
	public String getdNumCheq() {
		return dNumCheq;
	}
	public void setdNumCheq(String dNumCheq) {
		this.dNumCheq = dNumCheq;
	}
	public String getdBcoEmi() {
		return dBcoEmi;
	}
	public void setdBcoEmi(String dBcoEmi) {
		this.dBcoEmi = dBcoEmi;
	}
	
}
