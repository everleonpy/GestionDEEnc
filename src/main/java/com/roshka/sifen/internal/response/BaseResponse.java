package com.roshka.sifen.internal.response;

import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.internal.util.ResponseUtil;
import org.w3c.dom.Node;

/**
 * Clase abstracta heredada por las clases de respuestas a las peticiones.
 */
public abstract class BaseResponse extends SifenObjectBase {
    private int codigoEstado;
    private String respuestaBruta;
    // esto lo agregue para probar si se puede obtener el codigo QR de la respuesta
    private String dCarQR;

	private String dCodRes;
    private String dMsgRes;

    @Override
    public void setValueFromChildNode(Node value) throws SifenException {
        if (value.getLocalName().equals("dCodRes")) {
            dCodRes = ResponseUtil.getTextValue(value);
        } else if (value.getLocalName().equals("dMsgRes")) {
            dMsgRes = ResponseUtil.getTextValue(value);
        } else if (value.getLocalName().equals("dCarQR")) {
        	    dCarQR = ResponseUtil.getTextValue(value);
        }
    }

    public String getdCodRes() {
        return dCodRes;
    }

    public String getdMsgRes() {
        return dMsgRes;
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getRespuestaBruta() {
        return respuestaBruta;
    }

    public void setRespuestaBruta(String respuestaBruta) {
        this.respuestaBruta = respuestaBruta;
    }

    public String getdCarQR() {
		return dCarQR;
	}

	public void setdCarQR(String dCarQR) {
		this.dCarQR = dCarQR;
	}
    
    public void setdCodRes(String dCodRes) {
        this.dCodRes = dCodRes;
    }

    public void setdMsgRes(String dMsgRes) {
        this.dMsgRes = dMsgRes;
    }
}