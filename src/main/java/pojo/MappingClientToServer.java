package pojo;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import com.roshka.sifen.core.beans.DocumentoElectronico;
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
import com.roshka.sifen.core.types.TiCondAnt;
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
import com.roshka.sifen.internal.util.SifenUtil;

public class MappingClientToServer {
	//
	public DocumentoElectronico mapObjects ( DocElectronico sourceDE ) {
		
		/*
            CDC = SifenUtil.leftPad(String.valueOf(this.getgTimb().getiTiDE().getVal()), '0', 2) +
                    this.getgDatGralOpe().getgEmis().getdRucEm() +
                    this.getgDatGralOpe().getgEmis().getdDVEmi() +
                    this.getgTimb().getdEst() +
                    this.getgTimb().getdPunExp() +
                    this.getgTimb().getdNumDoc() +
                    this.getgDatGralOpe().getgEmis().getiTipCont().getVal() +
                    this.getgDatGralOpe().getdFeEmiDE().format(formatter) +
                    this.getgOpeDE().getiTipEmi().getVal() +
                    this.getgOpeDE().getdCodSeg();
		 
		 */
		
		
		ArrayList<TgCamItem> lit = new ArrayList<TgCamItem>();
		short shortZero = 0;
		DateTimeFormatter dtFmtr = DateTimeFormatter.ofPattern("yyyy-mm-dd'T'hh:mm:ss"); 
		DateTimeFormatter dFmtr = DateTimeFormatter.ofPattern("yyyy-mm-dd"); 
		//
		DocumentoElectronico tDE = new DocumentoElectronico();
		// A001 - DE
		tDE.setdDVId(sourceDE.getdDVId());
		
		LocalDateTime dateTime = LocalDateTime.parse(sourceDE.getdFecFirma(), dtFmtr);
		tDE.setdFecFirma(dateTime);
		tDE.setdSisFact(sourceDE.getdSisFact());
		// B001 - gOpeDE
		TgOpeDE tod = new TgOpeDE(null);
		XgOpeDE sod = sourceDE.getgOpeDE();
		tod.setiTipEmi(TTipEmi.getByVal(sod.getiTipEmi()));
		tod.setdInfoEmi(sod.getdInfoEmi());
		tod.setdInfoFisc(sod.getdInfoFisc());
		// C001 - gTimb
		TgTimb ttm = new TgTimb();
		XgTimb stm = sourceDE.getgTimb();
		ttm.setiTiDE(TTiDE.getByVal(stm.getiTiDE()));
		ttm.setdNumTim(stm.getdNumTim());
		ttm.setdEst(stm.getdEst());
		ttm.setdPunExp(stm.getdPunExp());
		ttm.setdNumDoc(stm.getdNumDoc());
		ttm.setdSerieNum(stm.getdSerieNum());
		LocalDate ld = LocalDate.parse(stm.getdFeIniT(), dFmtr);
		ttm.setdFeIniT(ld);
		// D001 - gDatGralOpe
		TdDatGralOpe tdg = new TdDatGralOpe();
		XdDatGralOpe sdg = sourceDE.getgDatGralOpe();
		dateTime = LocalDateTime.parse(sdg.getdFeEmiDE(), dtFmtr);
		tdg.setdFeEmiDE(dateTime);
		// D010 - gOpeCom
		TgOpeCom toc = new TgOpeCom();
		XgOpeCom soc = sourceDE.getgDatGralOpe().getgOpeCom();
		if (soc.getiTipTra() != shortZero) {
		    toc.setiTipTra(TTipTra.getByVal(soc.getiTipTra()));
		}
		toc.setiTImp(TTImp.getByVal(soc.getiTImp()));
		toc.setcMoneOpe(CMondT.getByName(soc.getcMoneOpe()));
		if (soc.getdCondTiCam() != shortZero) {
		    toc.setdCondTiCam(TdCondTiCam.getByVal(soc.getdCondTiCam()));
		}
		toc.setdTiCam(soc.getdTiCam());
		toc.setiCondAnt(TiCondAnt.getByVal(soc.getiCondAnt()));
		// D100 - gEmis
		TgEmis tem = new TgEmis();
		XgEmis sem = sourceDE.getgDatGralOpe().getgEmis();
		tem.setdRucEm(sem.getdRucEm());
		tem.setdDVEmi(sem.getdDVEmi());
		tem.setiTipCont(TiTipCont.getByVal(sem.getiTipCont()));
		if (sem.getcTipReg() != shortZero) {
		    tem.setcTipReg(TTipReg.getByVal(sem.getcTipReg()));
		}
		tem.setdNomEmi(sem.getdNomEmi());
		tem.setdNomFanEmi(sem.getdNomFanEmi());
		tem.setdDirEmi(sem.getdDirEmi());
		tem.setdNumCas(sem.getdNumCas());
		tem.setdCompDir1(sem.getdCompDir1());
		tem.setdCompDir2(sem.getdCompDir2());
		if (sem.getcDepEmi() != shortZero) {
		    tem.setcDepEmi(TDepartamento.getByVal(sem.getcDepEmi()));
		}
		tem.setcDisEmi(sem.getcDisEmi());
		tem.setdDesDisEmi(sem.getdDesDisEmi());
		tem.setcCiuEmi(sem.getcCiuEmi());
		tem.setdDesCiuEmi(sem.getdDesCiuEmi());
		tem.setdTelEmi(sem.getdTelEmi());
		tem.setdEmailE(sem.getdEmailE());
		tem.setdDenSuc(sem.getdDenSuc());
		ArrayList<TgActEco> ael = new ArrayList<TgActEco>();
		Iterator itr1 = sem.getgActEcoList().iterator();
		while (itr1.hasNext()) {
			XgActEco x = (XgActEco) itr1.next();
			TgActEco y = new TgActEco();
			y.setcActEco(x.getcActEco());
			y.setdDesActEco(x.getdDesActEco());
			ael.add(y);
		}
		tem.setgActEcoList(ael);
		// TdDatGralOpe incluye a TgOpeCom y TgEmis
		tdg.setgOpeCom(toc);
		tdg.setgEmis(tem);
		// D200 - gDatRec
		TgDatRec tdr = new TgDatRec();
		XgDatRec sdr = sourceDE.getgDatGralOpe().getgDatRec();
		tdr.setiNatRec(TiNatRec.getByVal(sdr.getiNatRec()));
		tdr.setiTiOpe(TiTiOpe.getByVal(sdr.getiTiOpe()));
		tdr.setcPaisRec(PaisType.getByName(sdr.getcPaisRec()));
		if (sdr.getiTiContRec() != shortZero) {
		    tdr.setiTiContRec(TiTipCont.getByVal(sdr.getiTiContRec()));
		}
		tdr.setdRucRec(sdr.getdRucRec());
		tdr.setdDVRec(sdr.getdDVRec());
		if (sdr.getiTipIDRec() != shortZero) {
		    tdr.setiTipIDRec(TiTipDocRec.getByVal(sdr.getiTipIDRec()));
		}
		tdr.setdDTipIDRec(sdr.getdDTipIDRec());
		tdr.setdNumIDRec(sdr.getdNumIDRec());
		tdr.setdNomRec(sdr.getdNomRec());
		tdr.setdNomFanRec(sdr.getdNomFanRec());
		tdr.setdDirRec(sdr.getdDirRec());
		tdr.setdNumCasRec(sdr.getdNumCasRec());
		if (sdr.getcDepRec() != shortZero) {
		    tdr.setcDepRec(TDepartamento.getByVal(sdr.getcDepRec()));
		}
		tdr.setcDisRec(sdr.getcDisRec());
		tdr.setdDesDisRec(sdr.getdDesDisRec());
		tdr.setcCiuRec(sdr.getcCiuRec());
		tdr.setdDesCiuRec(sdr.getdDesCiuRec());
		tdr.setdTelRec(sdr.getdTelRec());
		tdr.setdCelRec(sdr.getdCelRec());
		tdr.setdEmailRec(sdr.getdEmailRec());
		tdr.setdCodCliente(sdr.getdCodCliente());
		tdg.setgDatRec(tdr);
		// E001 - gDtipDE
		TgDtipDE ttd = new TgDtipDE();
		XgDtipDE std = sourceDE.getgDtipDE();
		// E010 - gCamFE
		TgCamFE tfe = new TgCamFE();
		XgCamFE sfe = sourceDE.getgDtipDE().getgCamFE();
		tfe.setiIndPres(TiIndPres.getByVal(sfe.getiIndPres()));
		tfe.setdDesIndPres(sfe.getdDesIndPres());
		ld = LocalDate.parse(sfe.getdFecEmNR(), dFmtr);
		tfe.setdFecEmNR(ld);
		// E600 - gCamCond
		TgCamCond tco = new TgCamCond();
		XgCamCond sco = std.getgCamCond();
		tco.setiCondOpe(TiCondOpe.getByVal(sco.getiCondOpe()));
		if (sco.getgPaConEIniList() != null) {
			ArrayList<TgPaConEIni> lpc = new ArrayList<TgPaConEIni>();
			Iterator itr2 = sco.getgPaConEIniList().iterator();
			while (itr2.hasNext()) {
				XgPaConEIni x = (XgPaConEIni) itr2.next();
				TgPaConEIni y = new TgPaConEIni();
				y.setiTiPago(TiTiPago.getByVal(x.getiTiPago()));
				y.setdDesTiPag(x.getdDesTiPag());
				y.setdMonTiPag(x.getdMonTiPag());
				y.setcMoneTiPag(CMondT.getByName(x.getcMoneTiPag()));
				y.setdTiCamTiPag(x.getdTiCamTiPag());
				//
				if (x.getgPagTarCD() != null) {
					TgPagTarCD t = new TgPagTarCD();
					t.setiDenTarj(TiDenTarj.getByVal(x.getgPagTarCD().getiDenTarj()));
					t.setdDesDenTarj(x.getgPagTarCD().getdDesDenTarj());
					t.setdRSProTar(x.getgPagTarCD().getdRSProTar());
					t.setdRUCProTar(x.getgPagTarCD().getdRUCProTar());
					t.setdDVProTar(x.getgPagTarCD().getdDVProTar());
					t.setiForProPa(TiForProPa.getByVal(x.getgPagTarCD().getiForProPa()));
					t.setdCodAuOpe(x.getgPagTarCD().getdCodAuOpe());
					t.setdNomTit(x.getgPagTarCD().getdNomTit());
					t.setdNumTarj(x.getgPagTarCD().getdNumTarj());
					//
					y.setgPagTarCD(t);
				}
				//
				if (x.getgPagCheq() != null) {
					TgPagCheq h = new TgPagCheq();
					h.setdNumCheq(x.getgPagCheq().getdNumCheq());
					h.setdBcoEmi(x.getgPagCheq().getdBcoEmi());
					//
					y.setgPagCheq(h);
				}
				// TgPaConEIni incluye a TgPagTarCD y TgPagCheq
				lpc.add(y);
			}
			tco.setgPaConEIniList(lpc);
		}
		TgPagCred tpc = new TgPagCred();
		XgPagCred spc = sco.getgPagCred();
		tpc.setiCondCred(TiCondCred.getByVal(spc.getiCondCred()));
		tpc.setdPlazoCre(spc.getdPlazoCre());
		tpc.setdCuotas(spc.getdCuotas());
		tpc.setdMonEnt(spc.getdMonEnt());
		if (spc.getgCuotasList() != null) {
			ArrayList<TgCuotas> lcu = new ArrayList<TgCuotas>();
			Iterator itr3 = spc.getgCuotasList().iterator();
			while (itr3.hasNext()) {
				XgCuotas s = (XgCuotas) itr3.next();
				TgCuotas t = new TgCuotas();
				t.setcMoneCuo(CMondT.getByName(s.getcMoneCuo()));
				t.setdMonCuota( s.getdMonCuota().setScale(4, RoundingMode.UNNECESSARY) );
				ld = LocalDate.parse(s.getdVencCuo(), dFmtr);
				t.setdVencCuo(ld);
				//
				lcu.add(t);
			}
			// TgPagCred incluye a TgCuotas
			tpc.setgCuotasList(lcu);
		}
		tco.setgPagCred(tpc);
		// Cargar la lista de los items de la transaccion
		if ( std.getgCamItemList() != null) {
			Iterator itr4 = std.getgCamItemList().iterator();
			while (itr4.hasNext()) {
				XgCamItem s = (XgCamItem) itr4.next();
				TgCamItem t = new TgCamItem();
				t.setdCodInt(s.getdCodInt());
				//t.setdParAranc(s.getdParAranc());
				//t.setdNCM(s.getdNCM());
				//t.setdDncpG(s.getdDncpG());
				//t.setdDncpE(s.getdDncpE());
				//t.setdGtin(s.getdGtin());
				//t.setdGtinPq(s.getdGtinPq());
				t.setdDesProSer(s.getdDesProSer());
				t.setcUniMed(TcUniMed.getByVal(s.getcUniMed()));
				t.setdCantProSer(s.getdCantProSer());
				//t.setcPaisOrig(PaisType.getByName(s.getcPaisOrig()));
				t.setdInfItem(s.getdInfItem());
				//t.setcRelMerc(TcRelMerc.getByVal(s.getcRelMerc()));
				//t.setdCanQuiMer(s.getdCanQuiMer());
				//t.setdPorQuiMer(s.getdPorQuiMer());
				//t.setdCDCAnticipo(s.getdCDCAnticipo());
				//
				TgValorItem tvi = new TgValorItem();
				tvi.setdPUniProSer(s.getgValorItem().getdPUniProSer());
				tvi.setdTiCamIt(s.getgValorItem().getdTiCamIt());
				// TgValorItem incluye a TgValorRestaItem
				TgValorRestaItem tvr = new TgValorRestaItem();
				XgValorRestaItem svr = s.getgValorItem().getgValorRestaItem();
				tvr.setdDescItem(svr.getdDescItem());
				tvr.setdDescGloItem(svr.getdDescGloItem());
				tvr.setdAntPreUniIt(svr.getdAntPreUniIt());
				tvr.setdAntGloPreUniIt(svr.getdAntGloPreUniIt());
				tvi.setgValorRestaItem(tvr);
				//
				t.setgValorItem(tvi);
				//
				if ( s.getgCamIVA() != null) {
					TgCamIVA i = new TgCamIVA();
					i.setiAfecIVA(TiAfecIVA.getByVal(s.getgCamIVA().getiAfecIVA()));
					i.setdPropIVA(s.getgCamIVA().getdPropIVA());
					i.setdTasaIVA(s.getgCamIVA().getdTasaIVA());
					//
					t.setgCamIVA(i);
				}
				// TgCamItem incluye a TgValorItem y TgCamIVA
				lit.add(t);
			}
			//
		}
		// TgDtipDE incluye a: TgCamFE, TgCamCond y TgCamItem
		ttd.setgCamFE(tfe);
		ttd.setgCamCond(tco);
		if (lit.isEmpty() == false) {
		    ttd.setgCamItemList(lit);
		}
		// DE incluye a: TgOpeDE, TgTimb, TgDatGralOpe, TgDtipDE y TgTotSub
		tDE.setgOpeDE(tod);
		tDE.setgTimb(ttm);
		tDE.setgDatGralOpe(tdg);
		tDE.setgDtipDE(ttd);
		TgTotSub tts = new TgTotSub();
		tDE.setgTotSub(tts);
		//
		return tDE;
		
	}
}
