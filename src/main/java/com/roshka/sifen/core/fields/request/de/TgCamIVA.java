package com.roshka.sifen.core.fields.request.de;

import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.internal.response.SifenObjectBase;
import com.roshka.sifen.core.types.CMondT;
import com.roshka.sifen.internal.util.ResponseUtil;
import com.roshka.sifen.core.types.TiAfecIVA;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TgCamIVA extends SifenObjectBase {
    private TiAfecIVA iAfecIVA;
    private BigDecimal dPropIVA;
    private BigDecimal dTasaIVA;
    private BigDecimal dBasGravIVA;
    private BigDecimal dLiqIVAItem;
    private BigDecimal dBasExe;

    public void setupSOAPElements(SOAPElement gCamItem, CMondT cMoneOpe, BigDecimal dTotOpeItem) throws SOAPException {
    	
    	    boolean enableTechNote13 = true;
        BigDecimal hundred = BigDecimal.valueOf(100);
        int scale = cMoneOpe.name().equals("PYG") ? 0 : 2;
    	    
        SOAPElement gCamIVA = gCamItem.addChildElement("gCamIVA");
        gCamIVA.addChildElement("iAfecIVA").setTextContent(String.valueOf(this.iAfecIVA.getVal()));
        gCamIVA.addChildElement("dDesAfecIVA").setTextContent(this.iAfecIVA.getDescripcion());
        gCamIVA.addChildElement("dPropIVA").setTextContent(String.valueOf(this.dPropIVA));
        gCamIVA.addChildElement("dTasaIVA").setTextContent(String.valueOf(this.dTasaIVA));

        if (this.iAfecIVA.getVal() == 1 || this.iAfecIVA.getVal() == 4) {   
            BigDecimal propIVA = this.dPropIVA.divide(hundred, 2, RoundingMode.HALF_UP);
    	        //System.out.println("this.dPropIVA: " + this.dPropIVA + " propIVA: " + propIVA);

            // tecnica roshka: paso 1, calcular la base imponible
            // jLcc - Enero 4, 2023
            // aumentamos la escala en los calculos de base imponible porque el servicio no recibe algunos
            // importes calculados a solo 2 decimales
            // en el calculo del item "this.dBasGravIVA" era utilizado "scale" con valor 2, pero levantamos
            // dicho valor a 8 para obtener mas precision
            scale = 8;
            if (this.dTasaIVA.equals(BigDecimal.valueOf(10))) {
                //System.out.println("dTotOpeItem: " + dTotOpeItem + " propIVA: " + propIVA);
                this.dBasGravIVA = dTotOpeItem.multiply(propIVA).divide(BigDecimal.valueOf(1.1), scale, RoundingMode.HALF_UP);
                //System.out.println("dBasGravIVA: " + this.dBasGravIVA);
                if (this.dBasGravIVA.compareTo(BigDecimal.ZERO) != 0) {
                    this.dBasGravIVA = this.dBasGravIVA.setScale(8, RoundingMode.HALF_UP);
                }
            } else if (this.dTasaIVA.equals(BigDecimal.valueOf(5))) {
                this.dBasGravIVA = dTotOpeItem.multiply(propIVA).divide(BigDecimal.valueOf(1.05), scale, RoundingMode.HALF_UP);
                //System.out.println("dBasGravIVA: " + this.dBasGravIVA);
                if (this.dBasGravIVA.compareTo(BigDecimal.ZERO) != 0) {
                    this.dBasGravIVA = this.dBasGravIVA.setScale(8, RoundingMode.HALF_UP);
                }
            }

            // paso 2, calcular el impuesto como la diferencia total - base_imponible
            this.dLiqIVAItem = dTotOpeItem.subtract(this.dBasGravIVA);
            
            //System.out.println(this.dLiqIVAItem + " = " + this.dBasGravIVA + " / 100");
            //double iva1 = this.dLiqIVAItem.doubleValue();
            //double iva2 = this.dBasGravIVA.doubleValue() * (this.dTasaIVA.doubleValue() / 100);
            //if (iva1 != iva2) {
            	//    System.out.println("iva roshka: " + iva1 + " iva calcuado: " + iva2);
            //}
            
        } else {
            this.dBasGravIVA = BigDecimal.ZERO;
            this.dLiqIVAItem = BigDecimal.ZERO;
        }

        //System.out.println(this.iAfecIVA.getVal() + " - " + this.dTasaIVA + " - " +
        //       dTotOpeItem + " - " + this.dBasGravIVA + " - " + this.dLiqIVAItem);
        
        gCamIVA.addChildElement("dBasGravIVA").setTextContent(String.valueOf(this.dBasGravIVA));
        gCamIVA.addChildElement("dLiqIVAItem").setTextContent(String.valueOf(this.dLiqIVAItem));
        
        // Nota Tecnica No. 13
        if (enableTechNote13 == true) {
            if (this.iAfecIVA.getVal() == 4) {
                // Actualización: https://ekuatia.set.gov.py/portal/ekuatia/detail?content-id=/repository/collaboration/sites/ekuatia/documents/documentacion/documentacion-tecnica/NT_E_KUATIA_013_MT_V150.pdf
                // E737 = [100 * EA008 * (100 – E733)] / [10000 + (E734 * E733)]
                this.dBasExe = (dTotOpeItem.multiply(hundred.subtract(dPropIVA)).multiply(hundred)).divide((this.dTasaIVA.multiply(dPropIVA)).add(BigDecimal.valueOf(10000)), scale, RoundingMode.HALF_UP);
            } else {
                this.dBasExe = BigDecimal.valueOf(0);
            }
            gCamIVA.addChildElement("dBasExe").setTextContent(String.valueOf(this.dBasExe));
        }

    }

    @Override
    public void setValueFromChildNode(Node value) throws SifenException {
        switch (value.getLocalName()) {
            case "iAfecIVA":
                this.iAfecIVA = TiAfecIVA.getByVal(Short.parseShort(ResponseUtil.getTextValue(value)));
                break;
            case "dPropIVA":
                this.dPropIVA = new BigDecimal(ResponseUtil.getTextValue(value));
                break;
            case "dTasaIVA":
                this.dTasaIVA = new BigDecimal(ResponseUtil.getTextValue(value));
                break;
            case "dBasGravIVA":
                this.dBasGravIVA = new BigDecimal(ResponseUtil.getTextValue(value));
                break;
            case "dLiqIVAItem":
                this.dLiqIVAItem = new BigDecimal(ResponseUtil.getTextValue(value));
                break;
            case "dBasExe":
                this.dBasExe = new BigDecimal(ResponseUtil.getTextValue(value));
                break;
        }
    }

    public TiAfecIVA getiAfecIVA() {
        return iAfecIVA;
    }

    public void setiAfecIVA(TiAfecIVA iAfecIVA) {
        this.iAfecIVA = iAfecIVA;
    }

    public BigDecimal getdPropIVA() {
        return dPropIVA;
    }

    public void setdPropIVA(BigDecimal dPropIVA) {
        this.dPropIVA = dPropIVA;
    }

    public BigDecimal getdTasaIVA() {
        return dTasaIVA;
    }

    public void setdTasaIVA(BigDecimal dTasaIVA) {
        this.dTasaIVA = dTasaIVA;
    }

    public BigDecimal getdBasGravIVA() {
        return dBasGravIVA;
    }

    public void setdBasGravIVA(BigDecimal dBasGravIVA) {
        this.dBasGravIVA = dBasGravIVA;
    }
    
    public BigDecimal getdLiqIVAItem() {
        return dLiqIVAItem;
    }

    public void setdLiqIVAItem(BigDecimal dLiqIVAItem) {
        this.dLiqIVAItem = dLiqIVAItem;
    }

    public BigDecimal getdBasExe() {
        return dBasExe;
    }

    public void setdBasExe(BigDecimal dBasExe) {
        this.dBasExe = dBasExe;
    }
}
