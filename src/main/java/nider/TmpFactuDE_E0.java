package nider;

import java.util.ArrayList;

public class TmpFactuDE_E0 {
	/**
	 * gDTipDE
	 * Campos específicos por tipo de Documento Electronico
	 */
    private TmpFactuDE_E gCamFE;
    private TmpFactuDE_E5 gCamNCDE;
	private TmpFactuDE_E7 gCamCond;
    private ArrayList<TmpFactuDE_E8> itemsList;
    private TmpFactuDE_E6 gCamNRE;
    private TmpFactuDE_E10 gTransp;
    
	public TmpFactuDE_E getgCamFE() {
		return gCamFE;
	}

	public void setgCamFE(TmpFactuDE_E gCamFE) {
		this.gCamFE = gCamFE;
	}

    public TmpFactuDE_E5 getgCamNCDE() {
		return gCamNCDE;
	}

	public void setgCamNCDE(TmpFactuDE_E5 gCamNCDE) {
		this.gCamNCDE = gCamNCDE;
	}
	
	public TmpFactuDE_E7 getgCamCond() {
		return gCamCond;
	}

	public void setgCamCond(TmpFactuDE_E7 gCamCond) {
		this.gCamCond = gCamCond;
	}

	public ArrayList<TmpFactuDE_E8> getItemsList() {
		return itemsList;
	}

	public void setItemsList(ArrayList<TmpFactuDE_E8> itemsList) {
		this.itemsList = itemsList;
	}

	public TmpFactuDE_E6 getgCamNRE() {
		return gCamNRE;
	}

	public void setgCamNRE(TmpFactuDE_E6 gCamNRE) {
		this.gCamNRE = gCamNRE;
	}

	public TmpFactuDE_E10 getgTransp() {
		return gTransp;
	}

	public void setgTransp(TmpFactuDE_E10 gTransp) {
		this.gTransp = gTransp;
	}
    
}
