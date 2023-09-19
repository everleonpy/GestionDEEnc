package py.com.softpoint.context;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class UserApp implements Serializable
{
	@JsonProperty("id")
	private Long id;
	@JsonProperty("orgId")
	private Long orgId;
	@JsonProperty("unitId")
	private Long unitId;
	@JsonProperty("usuario")
	private String usuario;
	@JsonProperty("password")
	private String password;
	@JsonProperty("hddserial")
	private String hddserial;
	@JsonProperty("Nombre_Completo")
	private String Nombre_Completo;
	@JsonProperty("Activo")
	private String Activo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("created_on")
	private LocalDateTime createdOn;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("modified_on")
	private LocalDateTime modifiedOn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHddserial() {
		return hddserial;
	}
	public void setHddserial(String hddserial) {
		this.hddserial = hddserial;
	}
	public String getNombre_Completo() {
		return Nombre_Completo;
	}
	public void setNombre_Completo(String nombre_Completo) {
		Nombre_Completo = nombre_Completo;
	}
	public String getActivo() {
		return Activo;
	}
	public void setActivo(String activo) {
		Activo = activo;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		//this.createdOn = createdOn;
		this.createdOn = LocalDateTime.parse(createdOn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		//this.modifiedOn = modifiedOn;
		if( modifiedOn != null ) {
			this.modifiedOn = LocalDateTime.parse(modifiedOn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
	}
	
}
