package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import pojo.CamposItemsOperacion;
import pojo.CamposReceptorDE;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;

public class AutoFacturasUtils {
	
	public CamposResponsableDE datosResponsable ( String respName, String respId, String docTypeId ) {
		// rs.getString("DNOMRESPDE") = respName
		// rs.getString("DNUMIDRESPDE") = respId
		// rs.getString("USERID_TYPE") = docTypeId
		short docType;
		CamposResponsableDE gRespDE = new CamposResponsableDE();
        gRespDE.setdCarRespDE("ADMINISTRATIVO");
        gRespDE.setdNomRespDE(respName);
        gRespDE.setdNumIDRespDE(respId);
        docType = 1;
        gRespDE.setiTipIDRespDE(docType);
        gRespDE.setdDTipIDRespDE("Cédula paraguaya");
        if (docTypeId.equalsIgnoreCase("PASAPORTE")) {
        	docType = 2;
            gRespDE.setiTipIDRespDE(docType);
            gRespDE.setdDTipIDRespDE("Pasaporte");
        }
        if (docTypeId.equalsIgnoreCase("CEDULA-EXTRANJERA")) {
        	docType = 3;
            gRespDE.setiTipIDRespDE(docType);
            gRespDE.setdDTipIDRespDE("Cédula extranjera");
        }
        if (docTypeId.equalsIgnoreCase("CARNET-RESIDENCIA")) {
        	docType = 4;
            gRespDE.setiTipIDRespDE(docType);
            gRespDE.setdDTipIDRespDE("Carnet de residencia”");
        }
        if (docTypeId.equalsIgnoreCase("OTRO")) {
        	docType = 9;
            gRespDE.setiTipIDRespDE(docType);
            gRespDE.setdDTipIDRespDE(docTypeId);
        }
		return gRespDE;
	}

	public CamposReceptorDE datosReceptor ( String cellPhoneNo,
			                                String vendorName,
			                                String address, 
			                                String custIdType, 
			                                String eMail, 
			                                String alternName, 
			                                String buildingNo, 
			                                String identityNo, 
			                                String phoneNo ) {
		/**
	     +--------------------------------------------------------------+
	     | completar los datos del receptor                             |
	     +--------------------------------------------------------------+
		 */
	    final short CEDULA_PARAGUAYA = 1;
	    final short PASAPORTE = 2;
	    final short CEDULA_EXTRANJERA = 3;
	    final short CARNET_RESIDENCIA = 4;
	    final short INNOMINADO = 5;
	    final short TARJETA_DIPLOMATICA = 6;
	    final short OTRO = 9;
		short docType;
		String docTypeName;

		CamposReceptorDE gDatRec = new CamposReceptorDE();
		// dRucRec
		gDatRec.setdRucRec(null);
		// dDVRec
     	gDatRec.setdDVRec(Short.valueOf("0"));
		// iTipIDRec, dDTipIDRec
     	if (custIdType != null) {
	        docType = CEDULA_PARAGUAYA;
	        docTypeName = "Cédula paraguaya";
			if (custIdType.equalsIgnoreCase("PASAPORTE")) {
				docType = PASAPORTE;
		        docTypeName = "Pasaporte";
		    }
			if (custIdType.equalsIgnoreCase("CEDULA-EXTRANJERA")) {
				docType = CEDULA_EXTRANJERA;
	           	docTypeName = "Cédula extranjera";
			}
			if (custIdType.equalsIgnoreCase("CARNET-RESIDENCIA")) {
				docType = CARNET_RESIDENCIA;
	         	docTypeName = "Carnet de residencia";
			}
			if (custIdType.equalsIgnoreCase("TARJETA-DIPLOMATICA")) {
				docType = TARJETA_DIPLOMATICA;
	          	docTypeName = "Tarjeta Diplomática de exoneración fiscal";
			}
			if (custIdType.equalsIgnoreCase("OTRO")) {
				docType = OTRO;
	        	    docTypeName = "Otro";
			}
			gDatRec.setiTipIDRec(docType);
			gDatRec.setdDTipIDRec(docTypeName);
		} else {
			docType = INNOMINADO;
    	        docTypeName = "Innominado";
			gDatRec.setiTipIDRec(docType);
			gDatRec.setdDTipIDRec(docTypeName);
		}
		// dNumIDRec
     	if (identityNo != null) {
            gDatRec.setdNumIDRec(identityNo);
		} else {
		    gDatRec.setdNumIDRec("0");				
	    }
		// dNomRec, dNomFanRec
		gDatRec.setdNomRec(vendorName);
	    if (alternName != null) {
			gDatRec.setdNomFanRec(alternName);	            	
		}			
		// dDirRec
		if (address != null) {
			gDatRec.setdDirRec(address);
		}
		// dNumCasRec
		if (buildingNo != null) {
			gDatRec.setdNumCasRec(Short.valueOf(buildingNo));
		}
		// dTelRec
		if (phoneNo != null) {
			gDatRec.setdTelRec(phoneNo);
		}
		// dCelRec
		if (cellPhoneNo != null) {
			gDatRec.setdCelRec(cellPhoneNo);
		}
		// dEmailRec
		if (eMail != null) {
			gDatRec.setdEmailRec(eMail);
		}
		// dCodCliente
		gDatRec.setdCodCliente(null);
		//
		return gDatRec;
	}
	
	public CamposSubtotalesTotales totalesOperacion ( ArrayList<CamposItemsOperacion> items, 
			short transType, 
			short taxType, 
			String currencyCode, 
			short exchangeType, 
			double exchangeRate ) {
		double dAnticipo = 0.0;
		double dBaseGrav10 = 0.0;
		double dBaseGrav5 = 0.0;
		double dComi = 0.0;
		double dDescTotal = 0.0;
		double dIVA10 = 0.0;
		double dIVA5 = 0.0;
		double dIVAComi = 0.0;
		double dTotIVA = 0.0;
		double dLiqTotIVA10 = 0.0;
		double dLiqTotIVA5 = 0.0;
		double dPorcDescTotal = 0.0;
		double dRedon = 0.0;
		double dSub10 = 0.0;
		double dSub5 = 0.0;
		double dSubExe = 0.0;
		double dSubExo = 0.0;
		double dTBasGraIVA = 0.0;
		double dTotalGs = 0.0;
		double dTotAnt = 0.0;
		double dTotAntItem = 0.0;
		double dTotDesc = 0.0;
		double dTotDescGlotem = 0.0;
		double dTotGralOpe = 0.0;
		double dTotOpe = 0.0;
		double dTotCom = 0.0;
		//
		/*
		 * iAfecIVA ( E731 )
		 * 1 = Gravado IVA
		 * 2 = Exonerado (Art. 83- Ley 125/91)
		 * 3 = Exento
		 * 4 = Gravado parcial (Grav-Exento)
		 */
		CamposSubtotalesTotales gTotSub = new CamposSubtotalesTotales();
		Iterator i = items.iterator();
		while (i.hasNext()) {
			CamposItemsOperacion o = (CamposItemsOperacion) i.next();
			/*
			 * Si E731 = 3: Suma de todas las ocurrencias de EA008 (Valor total de la operación por ítem) 
			 * cuando la operación sea exenta
			 * EA008: dTotOpeItem
			 */
			if (o.getgCamIVA().getiAfecIVA() == 3) {
				dSubExe = dSubExe + o.getgValorItem().getgValorRestaItem().getdTotOpeItem().doubleValue();
			}
			/*
			 * Si E731 = 2: Suma de todas las ocurrencias de EA008 (Valor total de la operación por ítem) 
			 * cuando la operación sea exonerada
			 * EA008: dTotOpeItem
			 */
			if (o.getgCamIVA().getiAfecIVA() == 2) {
				dSubExo = dSubExo + o.getgValorItem().getgValorRestaItem().getdTotOpeItem().doubleValue();
			}
			/*
			 * Si E731 = 1 o 4: Suma de todas las ocurrencias de EA008 (Valor total de la operación por ítem) 
			 * cuando la operación sea a la tasa del 5% (E734=5)
			 * No debe existir el campo si D013 != 1
			 * EA008: dTotOpeItem
			 */
			if (taxType == 1) {
				if (o.getgCamIVA().getiAfecIVA() == 1 | o.getgCamIVA().getiAfecIVA() == 4) {
					if (o.getgCamIVA().getdTasaIVA() == 5) {
						dSub5 = dSub5 + o.getgValorItem().getgValorRestaItem().getdTotOpeItem().doubleValue();
					}
				}
			}
			/*
			 * Si E731 = 1 o 4: Suma de todas las ocurrencias de EA008 (Valor total de la operación por ítem) 
			 * cuando la operación sea a la tasa del 10% (E734=10)
			 * No debe existir el campo si D013 != 1
			 * EA008: dTotOpeItem
			 */
			if (taxType == 1) {
				if (o.getgCamIVA().getiAfecIVA() == 1 | o.getgCamIVA().getiAfecIVA() == 4) {
					if (o.getgCamIVA().getdTasaIVA() == 10) {
						dSub10 = dSub10 + o.getgValorItem().getgValorRestaItem().getdTotOpeItem().doubleValue();
					}
				}
			}
			/*
			 * Cuando D013 = 1, 3, 4 o 5 corresponde a la suma de los subtotales de la operación (F002, F003, 
			 * F004 y F005) Cuando D013 = 2 corresponde a F006
			 * Cuando C002=4 corresponde a la suma de todas las ocurrencias de EA008 (Valor total de la 
			 * operación por ítem)
			 * EA008: dTotOpeItem
			 * C002 = 4: Autofactura electronica
			 */
			if (transType != 4) {
				if (taxType == 1 | taxType == 3 | taxType == 4 | taxType == 5) {
					dTotOpe = dSubExe + dSubExo + dSub5 + dSub10;
				}
				if (taxType == 2) {
					// Cuando D013 = 2 corresponde a F006 --> F006 no existe en la documentacion
				}
			} else {
				dTotOpe = dTotOpe + o.getgValorItem().getgValorRestaItem().getdTotOpeItem().doubleValue();
			}
			/*
			 * Suma de todos los descuentos particulares por ítem (EA002 - dDescItem)
			 */
			dTotDesc = dTotDesc + o.getgValorItem().getgValorRestaItem().getdDescItem().doubleValue();
			/*
			 * Sumatoria de todas las ocurrencias de descuentos globales por ítem (EA004 - dDescGloItem)
			 */
			dTotDescGlotem = dTotDescGlotem + o.getgValorItem().getgValorRestaItem().getdDescGloItem().doubleValue();
			/*
			 * Sumatoria de todas las ocurrencias de anticipos por ítem (EA006 - dAntPreUniIt)
			 */
			dTotAntItem = dTotAntItem + o.getgValorItem().getgValorRestaItem().getdAntPreUniIt().doubleValue();
			/*
			 * Sumatoria de todas las ocurrencias de anticipos global por ítem (EA007 - dAntGloPreUniIt)
			 */
			dTotAnt = dTotAnt + o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt().doubleValue();
			/*
			 * Informativo, si no existe %, completar con cero
			 */
			dPorcDescTotal = 0;
			/*
			 * Sumatoria de todos los descuentos (Global por Ítem y particular por ítem) de cada ítem
			 */
			dDescTotal = dDescTotal + dTotDesc + dTotAnt;
			/*
			 * Sumatoria de todos los Anticipos (Global por Ítem y particular por ítem)
			 */
			dAnticipo = dAnticipo + o.getgValorItem().getgValorRestaItem().getdAntPreUniIt().doubleValue() +
					o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt().doubleValue();
			/*
			 * Se realiza sobre el campo F008 y conforme a la explicación inicial en el grupo F
			 * Si no cuenta con redondeo completar con cero
			 */
			dRedon = 0;
			/* no hay comentarios para este item */
			dComi = 0;
			/*
			 * Corresponde al cálculo aritmético F008 - F013 + F025
			 *                                   dTotOpe - dRedon + dComi
			 */
			dTotGralOpe = dTotOpe - dRedon + dComi;
			/*
			 * Suma de todas las ocurrencias de E736 (Liquidación del IVA por ítem) cuando la operación sea 
			 * a la tasa del 5% (E734=5)
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 5) {
					dIVA5 = dIVA5 + o.getgCamIVA().getdLiqIVAItem().doubleValue();
				}
			}
			/*
			 * Suma de todas las ocurrencias de E736 (Liquidación del IVA por ítem) cuando la operación sea 
			 * a la tasa del 10% (E734=10)
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 10) {
					dIVA10 = dIVA10 + o.getgCamIVA().getdLiqIVAItem().doubleValue();
				}
			}
			/*
			 * Corresponde al cálculo del impuesto al IVA a la tasa del 5% sobre el valor del redondeo
			 * (Valor del redondeo / 1,05), cuando la operación sea a la tasa del 5% (E734=5)
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 5) {
					dLiqTotIVA5 = dLiqTotIVA5 + (dRedon / 1.05);
				}
			}
			/*
			 * Corresponde al cálculo del impuesto al IVA a la tasa del 10% sobre el valor del redondeo
			 * (Valor del redondeo / 1,1), cuando la operación sea a la tasa del 10% (E734=10)
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 10) {
					dLiqTotIVA5 = dLiqTotIVA5 + (dRedon / 1.1);
				}
			}
			/*
			 * Se aplica la tasa del 10% para comisiones
			 */
			dIVAComi = dIVAComi + ((dComi / 1.1) * 0.1);
			/*
			 * Corresponde al cálculo aritmético F015 (Liquidación del IVA al 10%) + F016(Liquidación
			 * del IVA al 5%) – F036 (redondeo al 5%) – F037 (redondeo al 10%) + F026 (Liquidación  
			 * total del IVA de la comisión)
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				dTotIVA = dTotIVA + (dIVA5 + dIVA10 - dLiqTotIVA5 - dLiqTotIVA10 + dIVAComi);
			}
			/*
			 * Suma de todas las ocurrencias de E735 (base gravada del IVA por ítem) cuando la operación
			 * sea a la tasa del 5% (E734=5). 
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 5) {
					dBaseGrav5 = dBaseGrav5 + o.getgCamIVA().getdBasGravIVA().doubleValue();
				}
			}
			/*
			 * Suma de todas las ocurrencias de E735 (base gravada del IVA por ítem) cuando la operación
			 * sea a la tasa del 10% (E734=10). 
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				if (o.getgCamIVA().getdTasaIVA() == 10) {
					dBaseGrav10 = dBaseGrav10 + o.getgCamIVA().getdBasGravIVA().doubleValue();
				}
			}
			/*
			 * Corresponde al cálculo aritmético F018+F019
			 * No debe existir el campo si D013 != 1 o D013 != 5
			 */
			if (taxType == 1 | taxType == 5) {
				dTBasGraIVA = dBaseGrav5 + dBaseGrav10;
			}
			/*
			 * Si D015 != PYG y D017 = 1, corresponde al cálculo aritmético: F014 * D018
			 * Si D015 != PYG y D017 = 2, corresponde a la suma de todas las ocurrencias de EA009
			 * Este campo no debe existir si D015=PYG
			 * No informar si D015 = PYG
			 */
			if (currencyCode.equalsIgnoreCase("PYG") == false & exchangeType == 1) {
				dTotalGs = 	dTotGralOpe * exchangeRate;	    	
			}
			if (currencyCode.equalsIgnoreCase("PYG") == false & exchangeType == 2) {
				dTotalGs = dTotalGs + o.getgValorItem().getgValorRestaItem().getdTotOpeGs().doubleValue();	    	
			}
			dTotCom = dTotalGs + dComi;
		}
		gTotSub.setdAnticipo(new BigDecimal(dAnticipo));
		gTotSub.setdBaseGrav10(new BigDecimal(dBaseGrav10));
		gTotSub.setdBaseGrav5(new BigDecimal(dBaseGrav5));
		gTotSub.setdComi(new BigDecimal(dComi));
		gTotSub.setdDescTotal(new BigDecimal(dDescTotal));
		gTotSub.setdIVA10(new BigDecimal(dIVA10));
		gTotSub.setdIVA5(new BigDecimal(dIVA5));
		gTotSub.setdIVAComi(new BigDecimal(dIVAComi));
		gTotSub.setdLiqTotIVA10(new BigDecimal(dLiqTotIVA10));
		gTotSub.setdLiqTotIVA5(new BigDecimal(dLiqTotIVA5));
		gTotSub.setdPorcDescTotal(new BigDecimal(dPorcDescTotal));
		gTotSub.setdRedon(new BigDecimal(dRedon));
		gTotSub.setdSub10(new BigDecimal(dSub10));
		gTotSub.setdSub5(new BigDecimal(dSub5));
		gTotSub.setdSubExe(new BigDecimal(dSubExe));
		gTotSub.setdSubExo(new BigDecimal(dSubExo));
		gTotSub.setdTBasGraIVA(new BigDecimal(dTBasGraIVA));
		gTotSub.setdTotalGs(new BigDecimal(dTotalGs));
		gTotSub.setdTotAnt(new BigDecimal(dTotAnt));
		gTotSub.setdTotAntItem(new BigDecimal(dTotAntItem));
		gTotSub.setdTotDesc(new BigDecimal(dTotDesc));
		gTotSub.setdTotDescGlotem(new BigDecimal(dTotDescGlotem));
		gTotSub.setdTotGralOpe(new BigDecimal(dTotGralOpe));
		gTotSub.setdTotIVA(new BigDecimal(dTotIVA));
		gTotSub.setdTotOpe(new BigDecimal(dTotOpe));
		return gTotSub;
	}
	
}
