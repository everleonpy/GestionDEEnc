package pojo;

import java.math.BigDecimal;

import util.UtilPOS;

public class ItemsUtils {
	
	public XgValorItem calcItemValues ( double quantity, 
			double unitPrice, 
			double unitDiscount, 
			double itemDiscPct, 
			double globalDiscAmt,
			double itemAdvAmt, 
			double globalAdvAmt, 
			double itemExchgRate ) {
		double amount = 0.0;
		double localAmount = 0.0;
		//
		XgValorItem v = new XgValorItem();
		v.setdPUniProSer(BigDecimal.valueOf(unitPrice));
		// el tipo de cambio por item no se informa porque la aplicacion no maneja
		// ese concepto
		if (itemExchgRate == 0.0) {
			v.setdTiCamIt(null);
		} else {
			v.setdTiCamIt(BigDecimal.valueOf(itemExchgRate));    		
		}
		/*
		 * Este codigo lo ejecuta el paquete Roshka
		amount = quantity * unitPrice;
		amount = UtilPOS.appRound(amount, 8);
		v.setdTotBruOpeItem(BigDecimal.valueOf(amount));
		*/
		// completar los valores del descuento
		XgValorRestaItem r = new XgValorRestaItem();
		r.setdAntGloPreUniIt(null);
		if (globalAdvAmt > 0.0) {
		    r.setdAntGloPreUniIt(BigDecimal.valueOf(globalAdvAmt));
		}
		r.setdAntPreUniIt(null);
		if (itemAdvAmt > 0.0) {
		    r.setdAntPreUniIt(BigDecimal.valueOf(itemAdvAmt));
		}
		globalDiscAmt = UtilPOS.appRound(globalDiscAmt, 8);
		r.setdDescGloItem(BigDecimal.ZERO);
		if (globalDiscAmt > 0.0) {
		    r.setdDescGloItem(BigDecimal.valueOf(globalDiscAmt));
		}
		// no sabemos si el valor del item "dDescItem" debe ser el descuento unitario
		// o el descuento unitario por la cantidad
		//amount = UtilPOS.appRound(unitDiscount * quantity);
		amount  = UtilPOS.appRound(unitDiscount, 8);
		r.setdDescItem(BigDecimal.valueOf(amount));
		// Debe existir si EA002 es mayor a 0 (cero)
		// [EA002 * 100 / E721]
		/*
		 * Este codigo lo ejecuta el paquete Roshka
		r.setdPorcDesIt(null);
		if (unitDiscount > 0.0) {
		    //r.setdPorcDesIt(BigDecimal.valueOf(itemDiscPct));
			amount = (unitDiscount * 100) / unitPrice;
			amount = UtilPOS.appRound(amount, 8);
		    r.setdPorcDesIt(BigDecimal.valueOf(amount));
		}
		*/
		/* 
		  Si D013 = 1, 3, 4 o 5 (afectado al IVA, Renta, ninguno, IVA - Renta),
          entonces EA008 corresponde al cálculo aritmético: 
          (E721 (Precio unitario) – EA002 (Descuento  particular) – EA004 (Descuento  global) – 
           EA006 (Anticipo particular) – EA007 (Anticipo global)) * E711(cantidad)
 
      	  Si D013 = 1, 3, 4 o 5 ( tipo impuesto afectado )
    	      EA008 = ( E721 – EA002 – EA004 – EA006 – EA007 ) * E711	 

    	      EA008 dTotOpeItem ( CamposDescuentosItem )
          E721  dPUniProSer ( CamposValoresItem )
          EA002 dDescItem ( CamposDescuentosItem )
          EA004 dDescGloItem ( CamposDescuentosItem )
          EA006 dAntPreUniIt ( CamposDescuentosItem )
          EA007 dAntGloPreUniIt ( CamposDescuentosItem )
          E711  dCantProSer ( CamposItemsOperacion )
          E725  dTiCamIt ( CamposValoresItem )
		 */
		/*
		 * Este codigo lo ejecuta el paquete Roshka
		 *
		amount = ( unitPrice - unitDiscount - globalDiscAmt - itemAdvAmt - globalAdvAmt ) * quantity; 
		amount = UtilPOS.appRound(amount, 8);
		r.setdTotOpeItem(amount);
		if (itemExchgRate == 0) {
			localAmount = amount * itemExchgRate;
			localAmount = UtilPOS.appRound(localAmount, 8);
			r.setdTotOpeGs(BigDecimal.valueOf(localAmount));
		} else {
			r.setdTotOpeGs(null);    		
		}
		*/
		v.setgValorRestaItem(r);
		//System.out.println("descuento valores item: " + v.getgValorRestaItem().getdDescItem());
		return v;
	}

	public XgCamIVA calcTaxValues ( double taxRate, double itemAmount ) {
		// Completar los campos del IVA
		XgCamIVA t = new XgCamIVA();
		double vatProportion = 100.0;
		double taxableAmt = 0.0;
		double taxAmount = 0.0;
		/*
        1 = Gravado IVA
        2 = Exonerado (Art. 83- Ley 125/91)
        3 = Exento
        4 = Gravado parcial (Grav-Exento)
        La aplicacion no dispone actualmente de la funcionalidad para manejar
        los casos 2 y 4
		 */
		final short GRAVADO_IVA = 1;
		final short ARTICULO_83 = 2;
		final short EXENTO = 3;
		final short GRAVADO_PARCIAL = 4;
		//
		if (taxRate > 0.0) {
			t.setiAfecIVA(GRAVADO_IVA);
			vatProportion = 100.0;
		} else {
			t.setiAfecIVA(EXENTO);
			vatProportion = 0.0;
		}
		// cuanto por ciento del importe paga IVA
		t.setdPropIVA(BigDecimal.valueOf(vatProportion));
		t.setdTasaIVA(new BigDecimal(taxRate));
		/*
    	 EA008 dTotOpeItem
    	 E733  dPropIVA
    	 E731  iAfecIVA
    	 Si E731 = 1 o 4: 
    	   tasa 10 = [EA008* (E733/100)] / 1,1 
           tasa 5 = [EA008* (E733/100)] / 1,05 
         Si E731 = 2 o 3 este campo es igual 0 
		 */
		/*
		 * Este codigo lo ejecuta el paquete Roshka
		taxableAmt = 0.0;
		if (t.getiAfecIVA() == GRAVADO_IVA | t.getiAfecIVA() == GRAVADO_PARCIAL) {
			// [EA008* (E733/100)] / 1,1 = (r.getdTotOpeItem() * (t.getdPropIVA() / 100)) / 1.1;
			taxableAmt = ( itemAmount * ( vatProportion / 100));
			if (taxRate == 5) {
				taxableAmt = taxableAmt / 1.05;
			}
			if (taxRate == 10) {
				taxableAmt = taxableAmt / 1.1;
			}
			taxableAmt = UtilPOS.appRound(taxableAmt, 8);
			t.setdBasGravIVA(BigDecimal.valueOf(taxableAmt));
		} else {
			taxableAmt = UtilPOS.appRound(taxableAmt, 8);
			t.setdBasGravIVA(BigDecimal.valueOf(taxableAmt));	        		
		}
		*/
		/*
         Corresponde al cálculo aritmético: E735 * (E734/100)
         dBasGravIVA * ( dTasaIVA / 100 )
         Si E731 = 2 o 3 este campo es igual 0	        	 
		 */
		/*
		 * Este codigo lo ejecuta el paquete Roshka
		taxAmount = 0.0;
		if (t.getiAfecIVA() == GRAVADO_IVA | t.getiAfecIVA() == GRAVADO_PARCIAL) {
			taxAmount = taxableAmt * (taxRate / 100);
			taxAmount = UtilPOS.appRound(taxAmount, 8);
			t.setdLiqIVAItem(BigDecimal.valueOf(taxAmount));
		} else {
			taxAmount = UtilPOS.appRound(taxAmount, 8);
			t.setdLiqIVAItem(BigDecimal.valueOf(taxAmount));	        		
		}
		*/
		return t;
	}

}
