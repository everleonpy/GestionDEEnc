package sifen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.roshka.sifen.core.beans.DocumentoElectronico;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.core.fields.request.de.TdDatGralOpe;
import com.roshka.sifen.core.fields.request.de.TgActEco;
import com.roshka.sifen.core.fields.request.de.TgCamCond;
import com.roshka.sifen.core.fields.request.de.TgCamFE;
import com.roshka.sifen.core.fields.request.de.TgCamIVA;
import com.roshka.sifen.core.fields.request.de.TgCamItem;
import com.roshka.sifen.core.fields.request.de.TgCuotas;
import com.roshka.sifen.core.fields.request.de.TgDatRec;
import com.roshka.sifen.core.fields.request.de.TgDtipDE;
import com.roshka.sifen.core.fields.request.de.TgEmis;
import com.roshka.sifen.core.fields.request.de.TgOpeCom;
import com.roshka.sifen.core.fields.request.de.TgOpeDE;
import com.roshka.sifen.core.fields.request.de.TgPaConEIni;
import com.roshka.sifen.core.fields.request.de.TgPagCheq;
import com.roshka.sifen.core.fields.request.de.TgPagCred;
import com.roshka.sifen.core.fields.request.de.TgPagTarCD;
import com.roshka.sifen.core.fields.request.de.TgTimb;
import com.roshka.sifen.core.fields.request.de.TgTotSub;
import com.roshka.sifen.core.fields.request.de.TgValorItem;
import com.roshka.sifen.core.fields.request.de.TgValorRestaItem;
import com.roshka.sifen.core.types.CMondT;
import com.roshka.sifen.core.types.PaisType;
import com.roshka.sifen.core.types.TDepartamento;
import com.roshka.sifen.core.types.TTImp;
import com.roshka.sifen.core.types.TTiDE;
import com.roshka.sifen.core.types.TTipEmi;
import com.roshka.sifen.core.types.TTipReg;
import com.roshka.sifen.core.types.TTipTra;
import com.roshka.sifen.core.types.TcRelMerc;
import com.roshka.sifen.core.types.TcUniMed;
import com.roshka.sifen.core.types.TdCondTiCam;
import com.roshka.sifen.core.types.TiAfecIVA;
import com.roshka.sifen.core.types.TiCondCred;
import com.roshka.sifen.core.types.TiCondOpe;
import com.roshka.sifen.core.types.TiDenTarj;
import com.roshka.sifen.core.types.TiForProPa;
import com.roshka.sifen.core.types.TiIndPres;
import com.roshka.sifen.core.types.TiNatRec;
import com.roshka.sifen.core.types.TiTiOpe;
import com.roshka.sifen.core.types.TiTiPago;
import com.roshka.sifen.core.types.TiTipCont;
import com.roshka.sifen.core.types.TiTipDocRec;

import dao.PosTransactionsDAO;
import pojo.CamposActivEconomica;
import pojo.CamposCondicionOperacion;
import pojo.CamposCuotas;
import pojo.CamposDescuentosItem;
import pojo.CamposEmisorDE;
import pojo.CamposFacturaElectronica;
import pojo.CamposItemsOperacion;
import pojo.CamposOperacionComercial;
import pojo.CamposOperacionContado;
import pojo.CamposOperacionCredito;
import pojo.CamposReceptorDE;
import pojo.CamposTimbrado;
import pojo.DocumElectronico;

public class PosInvoicesMapper {
	
	public DocumentoElectronico mapInvoice ( DocumElectronico da, 
			                                 long cashId, 
			                                 long controlId, 
			                                 long invoiceId,
			                                 Connection conn ) {
		//System.out.println("Cargando documento auxiliar: " + da.getDE().getId());
		DocumentoElectronico DE = new DocumentoElectronico();
		LocalDateTime currentDate = LocalDateTime.now();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s;
		BigDecimal bd;
		final short CONTADO = 1;
		final short CREDITO = 2;
		boolean dataFound = false;
		String codePlace = "Inicio";

		try {



			// Grupo A
			if (da.getDE().getId() != null) {
				codePlace = "Asignar CDC";
				// Este codigo sera ejecutado solamente si el valor del CDC fue asignado en forma previa a
				// la preparacion del documento electronico para el envio.
				// Por ejemplo cuando ya se generan el CDC y el codigo QR en el acto de confirmar la transaccion
				// para poder emitir el KuDE en dicho acto.
				DE.setId(da.getDE().getId());
				DE.setdDVId(da.getDE().getdDVId());
				//System.out.println("CDC pre-generado: " + DE.getId() + " - " + DE.getdDVId());
			}
			codePlace = "Fecha Firma";
			DE.setdFecFirma(currentDate);
			DE.setdSisFact(da.getDE().getdSisFact());
			dataFound = true;

			// Grupo B
			codePlace = "Asignar gOpeDE";
			TgOpeDE gOpeDE;
			if (da.getDE().getId() != null) {
				gOpeDE = new TgOpeDE(da.getDE().getId());
			} else {
				gOpeDE = new TgOpeDE(null);
			}
			gOpeDE.setiTipEmi(TTipEmi.getByVal(da.getDE().getgOpeDE().getiTipEmi()));
			// esta informacion la completa el paquete de Roshka
			//gOpeDE.setdInfoEmi(da.getDE().getgOpeDE().getdInfoEmi());
			//gOpeDE.setdInfoFisc(da.getDE().getgOpeDE().getdInfoFisc());
			DE.setgOpeDE(gOpeDE);

			// Grupo C
			/**
             +-----------------------------------------------------------------------------+
             | gTimb - Datos del timbrado                                                  |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar gTimb";
			TgTimb gTimb = new TgTimb();
			CamposTimbrado t = da.getDE().getgTimb();
			gTimb.setiTiDE(TTiDE.getByVal(t.getiTiDE()));
			gTimb.setdNumTim(t.getdNumTim());
			gTimb.setdEst(t.getdEst());
			gTimb.setdPunExp(t.getdPunExp());
			gTimb.setdNumDoc(t.getdNumDoc());
			if (t.getdSerieNum() != null) {
				gTimb.setdSerieNum(t.getdSerieNum());
			}
			s = sdf.format(t.getdFeIniT());
			gTimb.setdFeIniT(LocalDate.parse(s));
			DE.setgTimb(gTimb);

			// Grupo D
			codePlace = "Asignar gDatGralOpe";
			TdDatGralOpe dDatGralOpe = new TdDatGralOpe();
			LocalDateTime ldt = LocalDateTime.ofInstant(da.getDE().getgDatGralOpe().getdFeEmiDE().toInstant(),
					ZoneId.systemDefault());
			dDatGralOpe.setdFeEmiDE(ldt);
			/**
             +-----------------------------------------------------------------------------+
             | gOpeCom - Datos de la operacion comercial                                   |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar gOpeCom";
			TgOpeCom gOpeCom = new TgOpeCom();
			CamposOperacionComercial oc = da.getDE().getgDatGralOpe().getgOpeComer();
			gOpeCom.setiTipTra(TTipTra.getByVal(oc.getiTipTra()));
			gOpeCom.setiTImp(TTImp.getByVal(oc.getiTImp()));
			gOpeCom.setcMoneOpe(CMondT.getByName(oc.getcMoneOpe()));
			if (oc.getcMoneOpe().equalsIgnoreCase("PYG") == false) {
				gOpeCom.setdCondTiCam(TdCondTiCam.getByVal(oc.getdCondTiCam()));
				gOpeCom.setdTiCam(oc.getdTiCam());
			}
			dDatGralOpe.setgOpeCom(gOpeCom);

			/**
             +-----------------------------------------------------------------------------+
             | gEmis - Emisor del documento electronico                                    |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar gEmis";
			TgEmis gEmis = new TgEmis();
			CamposEmisorDE em = da.getDE().getgDatGralOpe().getgEmis();
			gEmis.setdRucEm(em.getdRucEm());
			gEmis.setdDVEmi(em.getdDVEmi());
			gEmis.setiTipCont(TiTipCont.getByVal(em.getiTipCont()));
			gEmis.setcTipReg(TTipReg.getByVal(em.getcTipReg()));
			gEmis.setdNomEmi(em.getdNomEmi());
			if (em.getdNomFanEmi() != null) {
				gEmis.setdNomFanEmi(em.getdNomFanEmi());
			}
			gEmis.setdDirEmi(em.getdDirEmi());
			if (em.getdNumCas() != null) {
				gEmis.setdNumCas(em.getdNumCas());
			}
			if (em.getdCompDir1() != null) {
				gEmis.setdCompDir1(em.getdCompDir1());
			}
			if (em.getdCompDir2() != null) {
				gEmis.setdCompDir2(em.getdCompDir2());
			}
			gEmis.setcDepEmi(TDepartamento.getByVal(em.getcDepEmi()));
			if (em.getcDisEmi() != 0) {
				gEmis.setcDisEmi(em.getcDisEmi());
				gEmis.setdDesDisEmi(em.getdDesDisEmi());
			}
			gEmis.setcCiuEmi(em.getcCiuEmi());
			gEmis.setdDesCiuEmi(em.getdDesCiuEmi());
			gEmis.setdTelEmi(em.getdTelEmi());
			gEmis.setdEmailE(em.getdEmailE());
			if (em.getdDenSuc() != null) {
				gEmis.setdDenSuc(em.getdDenSuc());
			}

			List<TgActEco> gActEcoList = new ArrayList<>();
			ArrayList<CamposActivEconomica> la = em.getgActEco();
			codePlace = "Asignar gActEco";
			Iterator itr1 = la.iterator();
			while (itr1.hasNext()) {
				CamposActivEconomica x = (CamposActivEconomica) itr1.next();
				if (x.getcActEco().equalsIgnoreCase("46301") | x.getcActEco().equalsIgnoreCase("46302") |
						x.getcActEco().equalsIgnoreCase("46304")) {
					TgActEco gActEco = new TgActEco();
					gActEco.setcActEco(x.getcActEco());
					gActEco.setdDesActEco(x.getdDesActEco());
					gActEcoList.add(gActEco);
					//System.out.println(gActEco.getcActEco() + ": " + gActEco.getdDesActEco());
				}
			}    	        
			gEmis.setgActEcoList(gActEcoList);
			dDatGralOpe.setgEmis(gEmis);

			/**
             +-----------------------------------------------------------------------------+
             | gDatRec - Receptor del documento electronico                                |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar gDatRec";
			TgDatRec gDatRec = new TgDatRec();
			CamposReceptorDE rd = da.getDE().getgDatGralOpe().getgDatRec();
			gDatRec.setiNatRec(TiNatRec.getByVal(rd.getiNatRec()));
			gDatRec.setiTiOpe(TiTiOpe.getByVal(rd.getiTiOpe()));
			gDatRec.setcPaisRec(PaisType.getByName(rd.getcPaisRec()));
			if (rd.getiTiContRec() != 0) {
				gDatRec.setiTiContRec(TiTipCont.getByVal(rd.getiTiContRec()));
			}
			if (rd.getdRucRec() != null) {
				gDatRec.setdRucRec(rd.getdRucRec());
				gDatRec.setdDVRec(rd.getdDVRec());
			}
			if (rd.getiTipIDRec() != 0) {
				gDatRec.setiTipIDRec(TiTipDocRec.getByVal(rd.getiTipIDRec()));
			}
			if (rd.getdNumIDRec() != null) {
				gDatRec.setdNumIDRec(rd.getdNumIDRec());
			}
			gDatRec.setdNomRec(rd.getdNomRec());
			if (rd.getdNomFanRec() != null) {
				gDatRec.setdNomFanRec(rd.getdNomFanRec());
			}
			if (rd.getdDirRec() != null) {
				gDatRec.setdDirRec(rd.getdDirRec());
			}
			if (rd.getdNumCasRec() != 0) {
				gDatRec.setdNumCasRec(rd.getdNumCasRec());
			}
			if (rd.getcDepRec() != 0) {
				gDatRec.setcDepRec(TDepartamento.getByVal(rd.getcDepRec()));
			}
			if (rd.getcDisRec() != 0) {
				gDatRec.setcDisRec(rd.getcDisRec());
				gDatRec.setdDesDisRec(rd.getdDesDisRec());
			}
			if (rd.getcCiuRec() != 0) {
				gDatRec.setcCiuRec(rd.getcCiuRec());
				gDatRec.setdDesCiuRec(rd.getdDesCiuRec());
			}
			if (rd.getdTelRec() != null) {
				gDatRec.setdTelRec(rd.getdTelRec());
			}
			if (rd.getdCelRec() != null) {
				gDatRec.setdCelRec(rd.getdCelRec());
			}
			if (rd.getdEmailRec() != null) {
				gDatRec.setdEmailRec(rd.getdEmailRec());
			}
			if (rd.getdCodCliente() != null) {
				gDatRec.setdCodCliente(rd.getdCodCliente());
			}

			dDatGralOpe.setgDatRec(gDatRec);
			DE.setgDatGralOpe(dDatGralOpe);

			// Grupo E
			TgDtipDE gDtipDE = new TgDtipDE();

			/**
             +-----------------------------------------------------------------------------+
             | gCamFE - Documento tipo factura electronica                                 |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar gCamFE";
			TgCamFE gCamFE = new TgCamFE();
			CamposFacturaElectronica fe = da.getDE().getgDtipDE().getgCamFE();
			gCamFE.setiIndPres(TiIndPres.getByVal(fe.getiIndPres()));
			if (fe.getdFecEmNR() != null) {
				s = sdf.format(fe.getdFecEmNR());
				gCamFE.setdFecEmNR(LocalDate.parse(s));
			}
			gDtipDE.setgCamFE(gCamFE);

			codePlace = "Asignar gCamCond";
			TgCamCond gCamCond = new TgCamCond();
			CamposCondicionOperacion co = da.getDE().getgDtipDE().getgCamCond();
			gCamCond.setiCondOpe(TiCondOpe.getByVal(co.getiCondOpe()));

			// Operciones contado
			//System.out.println("condicion operacion: " + co.getiCondOpe());
			if (co.getiCondOpe() == CONTADO) {
				if (co.getgPaConEIni() != null) {
					codePlace = "Asignar gPaConEIni";
					List<TgPaConEIni> gPaConEIniList = new ArrayList<>();
					ArrayList<CamposOperacionContado> cn = co.getgPaConEIni();
					Iterator itr2 = cn.iterator();
					while (itr2.hasNext()) {
						CamposOperacionContado x = (CamposOperacionContado) itr2.next();
						//System.out.println("forma pago: " + x.getcMoneTiPag() + " - " + x.getdDesTiPag() + " - " + x.getdMonTiPag());
						TgPaConEIni gPaConEIni = new TgPaConEIni();
						gPaConEIni.setiTiPago(TiTiPago.getByVal(x.getiTiPago()));
						bd = new BigDecimal(x.getdMonTiPag());
						bd = bd.setScale(4, RoundingMode.HALF_UP);
						gPaConEIni.setdMonTiPag(bd);
						gPaConEIni.setcMoneTiPag(CMondT.getByName(x.getcMoneTiPag()));
						if (x.getcMoneTiPag().equalsIgnoreCase("PYG") == false) {
							gPaConEIni.setdTiCamTiPag(new BigDecimal(x.getdTiCamTiPag()));
						}
						// tarjetas de credito o debito
						if (x.getgPagTarCD() != null) {
							codePlace = "Asignar gPagTarDC";
							TgPagTarCD gPagTarCD = new TgPagTarCD();
							gPagTarCD.setiDenTarj(TiDenTarj.getByVal(x.getgPagTarCD().getiDenTarj()));
							if (x.getgPagTarCD().getdRSProTar() != null) {
								gPagTarCD.setdRSProTar(x.getgPagTarCD().getdRSProTar());
							}
							if (x.getgPagTarCD().getdRUCProTar() != null) {
								gPagTarCD.setdRUCProTar(x.getgPagTarCD().getdRUCProTar());
								gPagTarCD.setdDVProTar(x.getgPagTarCD().getdDVProTar());
							}
							gPagTarCD.setiForProPa(TiForProPa.getByVal(x.getgPagTarCD().getiForProPa()));
							if (x.getgPagTarCD().getdCodAuOpe() != 0) {
								gPagTarCD.setdCodAuOpe(x.getgPagTarCD().getdCodAuOpe());
							}
							if (x.getgPagTarCD().getdNomTit() != null) {
								gPagTarCD.setdNomTit(x.getgPagTarCD().getdNomTit());
							}
							if (x.getgPagTarCD().getdNumTarj() != 0) {
								gPagTarCD.setdNumTarj(x.getgPagTarCD().getdNumTarj());
							}
							gPaConEIni.setgPagTarCD(gPagTarCD);
						}
						// cheques
						if (x.getgPagCheq() != null) {
							codePlace = "Asignar gPagCheq";
							TgPagCheq gPagCheq = new TgPagCheq();
							gPagCheq.setdNumCheq(x.getgPagCheq().getdNumCheq());
							gPagCheq.setdBcoEmi(x.getgPagCheq().getdBcoEmi());
							gPaConEIni.setgPagCheq(gPagCheq);
						}
						gPaConEIniList.add(gPaConEIni);
					}
					codePlace = "Asignar lista gPaConEIni";
					gCamCond.setgPaConEIniList(gPaConEIniList);
				}
			}

			// Operaciones credito
			if (co.getiCondOpe() == CREDITO) {
				codePlace = "Asignar gPagCred";
				CamposOperacionCredito cr = co.getgPagCred();
				TgPagCred gPagCred = new TgPagCred();
				gPagCred.setiCondCred(TiCondCred.getByVal(cr.getiCondCred()));
				if (cr.getdPlazoCre() != null) {
					gPagCred.setdPlazoCre(cr.getdPlazoCre());
				}
				if (cr.getdCuotas() > 0) {
					gPagCred.setdCuotas(cr.getdCuotas());
				}
				if (cr.getdMonEnt().doubleValue() > 0.0) {
					gPagCred.setdMonEnt(cr.getdMonEnt());
				}
				// cuotas de la operacion credito
				if (cr.getgCuotas() != null) {
					codePlace = "Asignar gCuotas";
					List<TgCuotas> gTgCuotasList = new ArrayList<>();
					ArrayList<CamposCuotas> cn = cr.getgCuotas();
					Iterator itr3 = cn.iterator();
					while (itr3.hasNext()) {
						CamposCuotas x = (CamposCuotas) itr3.next();
						TgCuotas gCuota = new TgCuotas();
						gCuota.setcMoneCuo(CMondT.getByName(x.getcMoneCuo()));
						gCuota.setdMonCuota(new BigDecimal(x.getdMonCuota()));
						if (x.getdVencCuo() != null) {
							s = sdf.format(x.getdVencCuo());
							gCuota.setdVencCuo(LocalDate.parse(s));
						}
						gTgCuotasList.add(gCuota);
					}
					gPagCred.setgCuotasList(gTgCuotasList);
				}
				gCamCond.setgPagCred(gPagCred);
			}
			codePlace = "Asignar gCamCond";
			gDtipDE.setgCamCond(gCamCond);

			/**
             +-----------------------------------------------------------------------------+
             | gCamItem - Items de la operacion                                            |
             +-----------------------------------------------------------------------------+
			 */
			codePlace = "Asignar lista items";
			List<TgCamItem> gCamItemList = new ArrayList<>();
			ArrayList<CamposItemsOperacion> im = da.getDE().getgDtipDE().getgCamItem();
			Iterator itr4 = im.iterator();
			while (itr4.hasNext()) {
				CamposItemsOperacion x = (CamposItemsOperacion) itr4.next();
				codePlace = "Asignar item " + x.getdDesProSer();
				TgCamItem gCamItem = new TgCamItem();
				gCamItem.setdCodInt(x.getdCodInt());
				if (x.getdParAranc() != 0) {
					gCamItem.setdParAranc(x.getdParAranc());
				}
				if (x.getdNCM() != 0) {
					gCamItem.setdNCM(x.getdNCM());
				}
				if (x.getdDncpG() != null) {
					gCamItem.setdDncpG(x.getdDncpG());
				}
				if (x.getdDncpE() != null) {
					gCamItem.setdDncpE(x.getdDncpE());
				}
				if (x.getdGtin() != 0) {
					gCamItem.setdGtin(x.getdGtin());
				}
				if (x.getdGtinPq() != 0) {
					gCamItem.setdGtinPq(x.getdGtinPq());
				}
				gCamItem.setdDesProSer(x.getdDesProSer());
				gCamItem.setcUniMed(TcUniMed.getByVal(x.getcUniMed()));
				gCamItem.setdCantProSer(x.getdCantProSer());
				if (x.getcPaisOrig() != null) {
					gCamItem.setcPaisOrig(PaisType.getByName(x.getcPaisOrig()));
				}
				if (x.getdInfItem() != null) {
					gCamItem.setdInfItem(x.getdInfItem());
				}
				if (x.getcRelMerc() != 0) {
					gCamItem.setcRelMerc(TcRelMerc.getByVal(x.getcRelMerc()));
				}
				if (x.getdCanQuiMer() != null) {
					if (x.getdCanQuiMer().doubleValue() != 0.0) {
						gCamItem.setdCanQuiMer(x.getdCanQuiMer());
					}
				}
				if (x.getdPorQuiMer() != null) {
					if (x.getdPorQuiMer().doubleValue() != 0.0) {
						gCamItem.setdPorQuiMer(x.getdPorQuiMer());
					}
				}
				if (x.getdCDCAnticipo() != null) {
					gCamItem.setdCDCAnticipo(x.getdCDCAnticipo());
				}

				// valores del item
				TgValorItem gValorItem = new TgValorItem();
				gValorItem.setdPUniProSer(x.getgValorItem().getdPUniProSer());
				codePlace = "Asignar gValorItem";
				if (x.getgValorItem().getdTiCamIt() != null) {
					if (x.getgValorItem().getdTiCamIt().doubleValue() != 0.0) {
						gValorItem.setdTiCamIt(x.getgValorItem().getdTiCamIt());
					}
				}
				// el valor del campo "dTotBruOpeItem" es asignado en el momeno de la generacion del xml

				// valores de descuentos por item
				if (x.getgValorItem().getgValorRestaItem() != null) {
					CamposDescuentosItem d = x.getgValorItem().getgValorRestaItem();
					TgValorRestaItem gValorRestaItem = new TgValorRestaItem();
					codePlace = "Asignar resta item";
					if (d.getdDescItem().doubleValue() != 0.0) {
						gValorRestaItem.setdDescItem(d.getdDescItem());
					}
					//System.out.println("gValorRestaItem.getdDescItem(): " + gValorRestaItem.getdDescItem());
					// el valor del campo "dPorcDesIt" es asignado en el momento de la generacion del xml
					if (d.getdDescGloItem() != null) {
						gValorRestaItem.setdDescGloItem(d.getdDescGloItem());
					}
					if (d.getdAntPreUniIt() != null) {
						gValorRestaItem.setdAntPreUniIt(d.getdAntPreUniIt());
					}
					if (d.getdAntGloPreUniIt() != null) {
						gValorRestaItem.setdAntGloPreUniIt(d.getdAntGloPreUniIt());
					}
					// los valores de los campos "dTotOpeItem" y "dTotOpeGs" son asignados en el momento
					// de la generacion del xml
					gValorItem.setgValorRestaItem(gValorRestaItem);
				}

				gCamItem.setgValorItem(gValorItem);

				// datos del IVA
				if (x.getgCamIVA() != null) {
					TgCamIVA gCamIVA = new TgCamIVA();
					codePlace = "Asignar gCamIVA";
					gCamIVA.setiAfecIVA(TiAfecIVA.getByVal(x.getgCamIVA().getiAfecIVA()));
					gCamIVA.setdPropIVA(x.getgCamIVA().getdPropIVA());
					gCamIVA.setdTasaIVA(new BigDecimal(x.getgCamIVA().getdTasaIVA()));
					// los valores de los campos "dBasGravIVA" y "dLiqIVAItem" son asignados en el momento
					// de la generacion del xml
					gCamItem.setgCamIVA(gCamIVA);
				}

				gCamItemList.add(gCamItem);
			}

			codePlace = "Asignar lista lineas";
			gDtipDE.setgCamItemList(gCamItemList);
			DE.setgDtipDE(gDtipDE);

			// Grupo E
			DE.setgTotSub(new TgTotSub());

			// Grupo J
			// Este codigo sera ejecutado solamente si el valor del codigo QR es asignado en forma previa a
			// la preparacion del documento electronico para el envio.
			// Por ejemplo cuando ya se generan el CDC y el codigo QR en el acto de confirmar la transaccion
			// para poder emitir el KuDE en dicho acto.
			//if (da.getgCamFuFD().getdCarQR() != null) {
			//    DE.setEnlaceQR(da.getgCamFuFD().getdCarQR());
			//}

			// aqui realizaremos algunas validaciones para evitar que lleguen objetos DE que no tengan algunos
			// de sus atributos obligatorios
			if (gCamCond.getgPaConEIniList() == null) {
				return null;
			} else {
				if (gCamCond.getgPaConEIniList().isEmpty() == true) {
					return null;
				}			
			}



			// generar el CDC si la transaccion aun no la tiene
			if (da.getDE().getId() == null) {
				codePlace = "Obtener CDC";
				try {
					String controlCode = DE.obtenerCDC(null);
					String qrCode = DE.getEnlaceQR();
					// actualizar en la tabla de transacciones los CDC generados en el momento del envio
					PosTransactionsDAO.updateControlCode(invoiceId, controlId, cashId, controlCode, qrCode, conn);
				} catch (SifenException e1) {
					System.out.println(cashId + "-" + controlId + "-" + invoiceId + ": " + e1.getMessage());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}		

			System.out.println("Finalizando carga de documento auxiliar: " + da.getDE().getId());
			return DE;

		} catch ( Exception e) {
			System.out.println("Error en " + codePlace);
			e.printStackTrace();
			return null;
		}
	}

}
