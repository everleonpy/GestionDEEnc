package nider;

import java.math.BigDecimal;

public class TmpFactuDE_D1 {
	/**
	 * gOpeCom
	 * Campos inherentes a la operación comercial
	 */
	private int idConfig;
	private int idMov;
	private short iTipTra;
	private String dDesTipTra;
	private short iTImp;
	private String dDesTImp;
	private String cMoneOpe;
	private String dDesMoneOpe;
	private short dCondTiCam;
	private BigDecimal dTiCam;
	private short iCondAnt;
	private String dDesCondAnt;
	
	public int getIdConfig() {
		return idConfig;
	}
	public void setIdConfig(int idConfig) {
		this.idConfig = idConfig;
	}
	public int getIdMov() {
		return idMov;
	}
	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}
	public short getiTipTra() {
		return iTipTra;
	}
	public void setiTipTra(short iTipTra) {
		this.iTipTra = iTipTra;
	}
	public String getdDesTipTra() {
		return dDesTipTra;
	}
	public void setdDesTipTra(String dDesTipTra) {
		this.dDesTipTra = dDesTipTra;
	}
	public short getiTImp() {
		return iTImp;
	}
	public void setiTImp(short iTImp) {
		this.iTImp = iTImp;
	}
	public String getdDesTImp() {
		return dDesTImp;
	}
	public void setdDesTImp(String dDesTImp) {
		this.dDesTImp = dDesTImp;
	}
	public String getcMoneOpe() {
		return cMoneOpe;
	}
	public void setcMoneOpe(String cMoneOpe) {
		this.cMoneOpe = cMoneOpe;
	}
	public String getdDesMoneOpe() {
		return dDesMoneOpe;
	}
	public void setdDesMoneOpe(String dDesMoneOpe) {
		this.dDesMoneOpe = dDesMoneOpe;
	}
	public short getdCondTiCam() {
		return dCondTiCam;
	}
	public void setdCondTiCam(short dCondTiCam) {
		this.dCondTiCam = dCondTiCam;
	}
	public BigDecimal getdTiCam() {
		return dTiCam;
	}
	public void setdTiCam(BigDecimal dTiCam) {
		this.dTiCam = dTiCam;
	}
	public short getiCondAnt() {
		return iCondAnt;
	}
	public void setiCondAnt(short iCondAnt) {
		this.iCondAnt = iCondAnt;
	}
	public String getdDesCondAnt() {
		return dDesCondAnt;
	}
	public void setdDesCondAnt(String dDesCondAnt) {
		this.dDesCondAnt = dDesCondAnt;
	}

}
