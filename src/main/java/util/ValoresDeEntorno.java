package util;

public class ValoresDeEntorno 
{
	private Long orgId;
	private Long unitId;
	private String userName;
	
	
	public ValoresDeEntorno() 
	{
		//TODO Aca Cargamos los valores ya sea de un properties o desde un WebServices
	}
	
	public Long getOrgId() {
		return orgId;
	}
	public Long getUnitId() {
		return unitId;
	}

	public String getUserName() {
		return userName;
	}
	
}
