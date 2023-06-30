package pojo;

import business.ApplicationMessage;

public class RespuestaSifen {
    private String estado;
    private int codigo;
    private String mensaje;
    private String codigoControl;
    private String codigoQr;
    private java.util.Date fechaFirma;
    private String archivoXml;
    private ApplicationMessage mensajeApp;
    
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getCodigoControl() {
		return codigoControl;
	}
	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}
	public String getCodigoQr() {
		return codigoQr;
	}
	public void setCodigoQr(String codigoQr) {
		this.codigoQr = codigoQr;
	}
	public java.util.Date getFechaFirma() {
		return fechaFirma;
	}
	public void setFechaFirma(java.util.Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}
	public String getArchivoXml() {
		return archivoXml;
	}
	public void setArchivoXml(String archivoXml) {
		this.archivoXml = archivoXml;
	}
	public ApplicationMessage getMensajeApp() {
		return mensajeApp;
	}
	public void setMensajeApp(ApplicationMessage mensajeApp) {
		this.mensajeApp = mensajeApp;
	}
    
}
