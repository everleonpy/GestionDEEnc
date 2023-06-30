package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import pojo.CamposItemsOperacion;
import pojo.CamposReceptorDE;
import pojo.CamposResponsableDE;
import pojo.CamposSubtotalesTotales;
import pojo.GeographicLocation;
import pojo.ReceiverData;
import util.UtilPOS;

public class FacturasUtils {
	
	public CamposResponsableDE datosResponsable ( String respName, String respId, String docTypeId ) {
		// rs.getString("DNOMRESPDE") = respName
		// rs.getString("DNUMIDRESPDE") = respId
		// rs.getString("USERID_TYPE") = docTypeId
		short docType;
		CamposResponsableDE gRespDE = new CamposResponsableDE();
        gRespDE.setdCarRespDE("FACTURADOR");
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

	public CamposReceptorDE datosReceptor ( ReceiverData r ) {
		/**
	     +--------------------------------------------------------------+
	     | completar los datos del receptor                             |
	     +--------------------------------------------------------------+
		 */
		final short B2B = 1;
		final short B2C = 2;
		final short B2G = 3;
		final short B2F = 4;
		//
		final short PERSONA_FISICA = 1;
		final short PERSONA_JURIDICA = 2;
		final short CONTRIBUYENTE = 1;
		final short NO_CONTRIBUYENTE = 2;
		//
	    final short CEDULA_PARAGUAYA = 1;
	    final short PASAPORTE = 2;
	    final short CEDULA_EXTRANJERA = 3;
	    final short CARNET_RESIDENCIA = 4;
	    final short INNOMINADO = 5;
	    final short TARJETA_DIPLOMATICA = 6;
	    final short OTRO = 9;

	    //System.out.println("10a-1");
		CamposReceptorDE gDatRec = new CamposReceptorDE();
		// iNatRec
		gDatRec.setiNatRec(NO_CONTRIBUYENTE);
		if (r.getTaxNumber() != null) {
			//existsTaxPayer = FacturasUtils.existsTaxPayer(r.getTaxNumber());
			//if (existsTaxPayer == true) {
			    gDatRec.setiNatRec(CONTRIBUYENTE);
			//}
		}
		// iTiOpe
		if (r.getOperationType() != 0) {
			gDatRec.setiTiOpe(r.getOperationType());
		} else {
		    if (r.getTaxNumber() != null) {
			    if (r.getOrgType().equalsIgnoreCase("INDIVIDUO")) {
				    gDatRec.setiTiOpe(B2C);				
			    } else {
				    gDatRec.setiTiOpe(B2B);				
			    }
		    } else {
			    gDatRec.setiTiOpe(B2C);				
		    }
		}
		// cPaisRec, dDesPaisRe
		//if (r.getCountryId() != 0) {
		//    System.out.println("10a-2");
		//	GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(r.getCountryId(), "PAIS");
		//	gDatRec.setcPaisRec(geoLoc.getLocationCode());
		//	gDatRec.setdDesPaisRe(geoLoc.getLocationName());
		//}
		gDatRec.setcPaisRec(r.getCountryCode());
		gDatRec.setdDesPaisRe(r.getCountryName());

		// iTiContRec
		// Obligatorio si D201 = 1 ( D201: gDatRec.iNatRec )
		// No informar si D201 = 2 
		// 1 = Persona Física
		// 2 = Persona Jurídica
	    gDatRec.setiTiContRec(Short.valueOf("0"));					
		if (gDatRec.getiNatRec() == CONTRIBUYENTE) {
		    gDatRec.setiTiContRec(PERSONA_JURIDICA);
		    if (r.getOrgType().equalsIgnoreCase("INDIVIDUO")) {
			    gDatRec.setiTiContRec(PERSONA_FISICA);
		    }
		}
		// dRucRec, dDVRec
		// Obligatorio si D201 = 1 ( D201: gDatRec.iNatRec )
		// No informar si D201 = 2
		gDatRec.setdRucRec(null);
		gDatRec.setdDVRec(Short.valueOf("0"));
		if (r.getTaxNumber() != null) {
			String taxNo = r.getTaxNumber().substring(0, (r.getTaxNumber().length() - 2));
			gDatRec.setdRucRec(taxNo);
			String cd = r.getTaxNumber().substring(r.getTaxNumber().length() - 1);
			//System.out.println("cadena: " + r.getTaxNumber() + " ruc: " + taxNo + " dv: " + cd);
			gDatRec.setdDVRec(Short.valueOf(cd));
		}
		// iTipIDRec, dDTipIDRec
		// Obligatorio si D201 = 2 y D202 != 4 
		// No informar si D201 = 1 o D202 = 4
		if (gDatRec.getiNatRec() == CONTRIBUYENTE | gDatRec.getiTiOpe() == B2F) {
			gDatRec.setiTipIDRec(Short.valueOf("0"));
			gDatRec.setdDTipIDRec(null);			
		} else {
     	    if (r.getIdentityType() != null) {
			    gDatRec.setiTipIDRec(CEDULA_PARAGUAYA);
			    gDatRec.setdDTipIDRec("Cédula paraguaya");
			    if (r.getIdentityType().equalsIgnoreCase("PASAPORTE")) {
				    gDatRec.setiTipIDRec(PASAPORTE);
				    gDatRec.setdDTipIDRec("Pasaporte");
		        }
			    if (r.getIdentityType().equalsIgnoreCase("CEDULA-EXTRANJERA")) {
				    gDatRec.setiTipIDRec(CEDULA_EXTRANJERA);
				    gDatRec.setdDTipIDRec("Cédula extranjera");
			    }
			    if (r.getIdentityType().equalsIgnoreCase("CARNET-RESIDENCIA")) {
				    gDatRec.setiTipIDRec(CARNET_RESIDENCIA);
				    gDatRec.setdDTipIDRec("Carnet de residencia");
			    }
			    if (r.getIdentityType().equalsIgnoreCase("TARJETA-DIPLOMATICA")) {
				    gDatRec.setiTipIDRec(TARJETA_DIPLOMATICA);
				    gDatRec.setdDTipIDRec("Tarjeta Diplomática de exoneración fiscal");
			    }
			    if (r.getIdentityType().equalsIgnoreCase("OTRO")) {
					gDatRec.setiTipIDRec(OTRO);
					gDatRec.setdDTipIDRec("Otro");
			    }
		    } else {
		    	    if (r.getTaxNumber() != null) {
				    gDatRec.setiTipIDRec(CEDULA_PARAGUAYA);
				    gDatRec.setdDTipIDRec("Cédula paraguaya");		    	    	
		    	    } else {
			        gDatRec.setiTipIDRec(INNOMINADO);
			        gDatRec.setdDTipIDRec("Innominado");
		    	    }
		    }
		}
		// dNumIDRec
     	// Obligatorio si D201 = 2 y D202 != 4 (gDatRec.iNatRec = No Contribuyente)
     	// No informar si D201 = 1 o D202 = 4  (gDatRec.iNatRec = Contribuyente)
     	// En caso de DE innominado, completar con 0 (cero)
	    gDatRec.setdNumIDRec(null);
		if (gDatRec.getiNatRec() == NO_CONTRIBUYENTE) {
			if (gDatRec.getiTiOpe() != B2F) {
			    if (r.getIdentityNumber() != null) {
		            gDatRec.setdNumIDRec(r.getIdentityNumber());
			    } else {
		            gDatRec.setdNumIDRec("0");				
			    }
			}
		}
		// dNomRec
		// En caso de DE innominado, completar con “Sin Nombre”
		if (r.getName() == null) {
		    gDatRec.setdNomRec("Sin Nombre");
		} else {
		    gDatRec.setdNomRec(r.getName());
		}
		// dNomFanRec
		if (r.getAltenativeName() != null) {
			gDatRec.setdNomFanRec(r.getAltenativeName());	            	
		}			
		// dDirRec
		// Campo obligatorio cuando C002 = 7 ( Remision )  o cuando D202 = 4 
		gDatRec.setdDirRec(null);
		if (r.getAddress() != null) {
			gDatRec.setdDirRec(r.getAddress());
		}
		// dNumCasRec
		// Campo obligatorio si se informa el campo D213 (gDatRec.dDirRec)
		// Cuando D201 = 1 (contribuyente), debe corresponder a lo declarado en el RUC
		if (r.getBuildingNo() != null) {
			gDatRec.setdNumCasRec(Short.valueOf(r.getBuildingNo()));
		}
		// cDepRec, dDesDepRec
	    // Campo obligatorio si se informa el campo D213 (gDatRec.dDirRec) 
	    // y D202 != 4 ( D202 = gDatRec.iTiOpe | 4 = B2F )
	    // No se debe informar cuando D202 = 4
		if (gDatRec.getiTiOpe() != B2F) {
		    //System.out.println("10a-3");
			//GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(r.getStateId(), "DEPARTAMENTO");
			//gDatRec.setcDepRec(Short.valueOf(geoLoc.getLocationCode()));
			//gDatRec.setdDesDepRec(geoLoc.getLocationName());
			gDatRec.setcDepRec(r.getStateCode());
			gDatRec.setdDesDepRec(r.getStateName());
		}
		// cDisRec, dDesDisRec
		if (r.getCountyCode() != 0) {
		    //System.out.println("10a-4");
			//GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(r.getCountyId(), "DISTRITO");
			//gDatRec.setcDisRec(Short.valueOf(geoLoc.getLocationCode()));
			//gDatRec.setdDesDisRec(geoLoc.getLocationName());
			gDatRec.setcDisRec(r.getCountyCode());
			gDatRec.setdDesDisRec(r.getCountyName());
		}  
		// cCiuRec, dDesCiuRec
		// Campo obligatorio si se informa el campo D213 (gDatRec.dDirRec) 
		// y D202 != 4 ( D202 = gDatRec.iTiOpe | 4 = B2F )
		// No se debe informar cuando D202 = 4
		if (gDatRec.getiTiOpe() != B2F) {
		    //System.out.println("10a-5");
			//GeographicLocation geoLoc = EmisorDocumentosDAO.getLocation(r.getCityId(), "CIUDAD");
			//gDatRec.setcCiuRec(Short.valueOf(geoLoc.getLocationCode()));
			//gDatRec.setdDesCiuRec(geoLoc.getLocationName());
			gDatRec.setcCiuRec(r.getCityCode());
			gDatRec.setdDesCiuRec(r.getCityName());
		}
		// dTelRec
		// Debe incluir el prefijo de la ciudad si D203 = PRY
		gDatRec.setdTelRec(null);
		if (r.getPhone() != null) {
			gDatRec.setdTelRec(r.getPhone());
		}
		// dCelRec
		gDatRec.setdCelRec(null);
		if (r.getCellPhone() != null) {
			gDatRec.setdCelRec(r.getCellPhone());
		}
		// dEmailRec
		gDatRec.setdEmailRec(null);
		if (r.geteMail() != null) {
			gDatRec.setdEmailRec(r.geteMail());
		}
		// dCodCliente
		gDatRec.setdCodCliente(null);
		if (r.getCustomerCode() != null) {
			gDatRec.setdCodCliente(r.getCustomerCode());
		}
	    //System.out.println("10a-sale");
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
			if (o.getgValorItem().getgValorRestaItem().getdDescGloItem() != null) {
			    dTotDescGlotem = dTotDescGlotem + o.getgValorItem().getgValorRestaItem().getdDescGloItem().doubleValue();
			}
			/*
			 * Sumatoria de todas las ocurrencias de anticipos por ítem (EA006 - dAntPreUniIt)
			 */
			if (o.getgValorItem().getgValorRestaItem().getdAntPreUniIt() != null) {
			    dTotAntItem = dTotAntItem + o.getgValorItem().getgValorRestaItem().getdAntPreUniIt().doubleValue();
			}
			/*
			 * Sumatoria de todas las ocurrencias de anticipos global por ítem (EA007 - dAntGloPreUniIt)
			 */
			if (o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt() != null) {
			    dTotAnt = dTotAnt + o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt().doubleValue();
			}
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
			if (o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt() != null) {
			    dAnticipo = dAnticipo + o.getgValorItem().getgValorRestaItem().getdAntPreUniIt().doubleValue() +
				                        o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt().doubleValue();
			}
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
				//System.out.println(dTotIVA + " - " + dIVA5 + " - " + dIVA10 + " - " +  dLiqTotIVA5 + " - " +  dLiqTotIVA10 + " - " + dIVAComi);
				//System.out.println("dTotIVA(1): " + dTotIVA);
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
		gTotSub.setdAnticipo(BigDecimal.valueOf(dAnticipo));
		gTotSub.setdBaseGrav10(BigDecimal.valueOf(dBaseGrav10));
		gTotSub.setdBaseGrav5(BigDecimal.valueOf(dBaseGrav5));
		gTotSub.setdComi(BigDecimal.valueOf(dComi));
		gTotSub.setdDescTotal(BigDecimal.valueOf(dDescTotal));
		gTotSub.setdIVA10(BigDecimal.valueOf(dIVA10));
		gTotSub.setdIVA5(BigDecimal.valueOf(dIVA5));
		gTotSub.setdIVAComi(BigDecimal.valueOf(dIVAComi));
		gTotSub.setdLiqTotIVA10(BigDecimal.valueOf(dLiqTotIVA10));
		gTotSub.setdLiqTotIVA5(BigDecimal.valueOf(dLiqTotIVA5));
		gTotSub.setdPorcDescTotal(BigDecimal.valueOf(dPorcDescTotal));
		gTotSub.setdRedon(BigDecimal.valueOf(dRedon));
		gTotSub.setdSub10(BigDecimal.valueOf(dSub10));
		gTotSub.setdSub5(BigDecimal.valueOf(dSub5));
		gTotSub.setdSubExe(BigDecimal.valueOf(dSubExe));
		gTotSub.setdSubExo(BigDecimal.valueOf(dSubExo));
		gTotSub.setdTBasGraIVA(BigDecimal.valueOf(dTBasGraIVA));
		gTotSub.setdTotalGs(BigDecimal.valueOf(dTotalGs));
		gTotSub.setdTotAnt(BigDecimal.valueOf(dTotAnt));
		gTotSub.setdTotAntItem(BigDecimal.valueOf(dTotAntItem));
		gTotSub.setdTotDesc(BigDecimal.valueOf(dTotDesc));
		gTotSub.setdTotDescGlotem(BigDecimal.valueOf(dTotDescGlotem));
		gTotSub.setdTotGralOpe(BigDecimal.valueOf(dTotGralOpe));
		dTotIVA = UtilPOS.appRound(dTotIVA, 8);
		//System.out.println("dTotIVA(2): " + dTotIVA);
		gTotSub.setdTotIVA(BigDecimal.valueOf(dTotIVA));
		gTotSub.setdTotOpe(BigDecimal.valueOf(dTotOpe));
		return gTotSub;
	}
	
	public static boolean existsTaxPayer ( String taxPayerNo ) {
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select FULL_NAME");
		    buffer.append(" from RCV_TAX_PAYERS");
		    buffer.append(" where TAX_PAYER_NO = ?");

		    stmtConsulta = conn.prepareStatement(buffer.toString());
		    stmtConsulta.setString(1, taxPayerNo );			

		    rsConsulta = stmtConsulta.executeQuery();
		    if (rsConsulta.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}
	
}
