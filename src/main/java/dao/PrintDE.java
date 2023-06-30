package dao;

import java.util.ArrayList;
import java.util.Iterator;

import pojo.CamposActivEconomica;
import pojo.CamposCondicionOperacion;
import pojo.CamposItemsOperacion;
import pojo.CamposOperacionContado;
import pojo.CamposOperacionCredito;
import pojo.DocumElectronico;
import java.math.BigDecimal;

public class PrintDE {
	
	public static String printObject ( DocumElectronico de ) {
		String lvl1 = "    ";
		String lvl2 = "        ";
		String lvl3 = "            ";
		String lvl4 = "                ";
		String lvl5 = "                    ";
		String lvl6 = "                        ";
		String lvl7 = "                            ";
		int a = 34;
		int b = 92;
		String dblQuote = new Character((char)a).toString();
		String invSlash = new Character((char)b).toString();
		//
		String x = "<rDE xmlns...>" + "\n";
		if (de.getdVerFor() == 0) {
            x = x + lvl1 + "<dVerFor>150</dVerFor>" + "\n";
		}
		if (de.getDE() != null) {
			//x = x + lvl1 + "<DE Id=\"01800139798001005000053222022100710000870931\">" + "\n";
			x = x + lvl1 + "<DE Id=" + invSlash + dblQuote + de.getDE().getId() + invSlash + dblQuote + ">" + dblQuote + "\n";
			x = x + lvl2 + "<dDVId>" + de.getDE().getdDVId() + "</dDVId>" + "\n";
			x = x + lvl2 + "<dFecFirma>" + de.getDE().getdFecFirma() + "</dFecFirma>" + "\n";
			x = x + lvl2 + "<dSisFact>" + de.getDE().getdSisFact() + "</dSisFact>" + "\n";
			x = x + lvl2 + "<gOpeDE>" + "\n";
			x = x + lvl3 + "<iTipEmi>" + de.getDE().getgOpeDE().getiTipEmi() + "</iTipEmi>" + "\n";
		    x = x + lvl3 + "<dDesTipEmi>" + de.getDE().getgOpeDE().getdDesTipEmi() + "</dDesTipEmi>" + "\n";
			x = x + lvl3 + "<dCodSeg>000087093</dCodSeg>" + "\n";
		    x = x + lvl3 + "<dInfoEmi>Ventas mayoristas</dInfoEmi>" + "\n";
		    x = x + lvl2 + "</gOpeDE>" + "\n";
		    x = x + lvl2 + "<gTimb>" + "\n";
		    x = x + lvl3 + "<iTiDE>" + de.getDE().getgTimb().getiTiDE() + "</iTiDE>" + "\n";
		    x = x + lvl3 + "<dDesTiDE>" + de.getDE().getgTimb().getdDesTiDE() + "</dDesTiDE>" + "\n";
		    x = x + lvl3 + "<dNumTim>" + de.getDE().getgTimb().getdNumTim() + "</dNumTim>" + "\n";
		    x = x + lvl3 + "<dEst>" + de.getDE().getgTimb().getdEst() + "</dEst>" + "\n";
		    x = x + lvl3 + "<dPunExp>" + de.getDE().getgTimb().getdPunExp() + "</dPunExp>" + "\n";
		    x = x + lvl3 + "<dNumDoc>" + de.getDE().getgTimb().getdNumDoc() + "</dNumDoc>" + "\n";
		    if (de.getDE().getgTimb().getdSerieNum() != null) {
		        x = x + lvl3 + "<dSerieNum>" + de.getDE().getgTimb().getdSerieNum() + "</dSerieNum>" + "\n";
		    }
		    x = x + lvl3 + "<dFeIniT>" + de.getDE().getgTimb().getdFeIniT() + "</dFeIniT>" + "\n";
		    x = x + lvl3 + "<dFeFinT>" + de.getDE().getgTimb().getdFeFinT() + "</dFeFinT>" + "\n";
	        x = x + lvl2 + "</gTimb>" + "\n";
	        x = x + lvl2 + "<gDatGralOpe>" + "\n";
	        x = x + lvl3 + "<dFeEmiDE>" + de.getDE().getgDatGralOpe().getdFeEmiDE() + "</dFeEmiDE>" + "\n";
	        // Obligatorio si C002 != 7 No informar si C002 = 7
	        if (de.getDE().getgTimb().getiTiDE() != 7) {
	            x = x + lvl3 + "<gOpeCom>" + "\n";
		        if (de.getDE().getgTimb().getiTiDE() == 1 | de.getDE().getgTimb().getiTiDE() == 4) {
	                x = x + lvl4 + "<iTipTra>" + de.getDE().getgDatGralOpe().getgOpeComer().getiTipTra() + "</iTipTra>" + "\n";
	                x = x + lvl4 + "<dDesTipTra>" + de.getDE().getgDatGralOpe().getgOpeComer().getdDesTipTra() + "</dDesTipTra>" + "\n";
		        }
	            x = x + lvl4 + "<iTImp>" + de.getDE().getgDatGralOpe().getgOpeComer().getiTImp() + "</iTImp>" + "\n";
	            x = x + lvl4 + "<dDesTImp>" + de.getDE().getgDatGralOpe().getgOpeComer().getdDesTImp() + "</dDesTImp>" + "\n";
	            x = x + lvl4 + "<cMoneOpe>" + de.getDE().getgDatGralOpe().getgOpeComer().getcMoneOpe() + "</cMoneOpe>" + "\n";
	            x = x + lvl4 + "<dDesMoneOpe>" + de.getDE().getgDatGralOpe().getgOpeComer().getdDesMoneOpe() + "</dDesMoneOpe>" + "\n";
	            if (de.getDE().getgDatGralOpe().getgOpeComer().getcMoneOpe().equalsIgnoreCase("PYG") == false) {
		            x = x + lvl4 + "<dCondTiCam>" + de.getDE().getgDatGralOpe().getgOpeComer().getdCondTiCam() + "</dCondTiCam>" + "\n";
		            if (de.getDE().getgDatGralOpe().getgOpeComer().getdCondTiCam() == 1) {
			            x = x + lvl4 + "<dTiCam>" + de.getDE().getgDatGralOpe().getgOpeComer().getdTiCam() + "</dTiCam>" + "\n";		            	
		            }
	            }
	            if (de.getDE().getgDatGralOpe().getgOpeComer().getiCondAnt() != 0) {
	                x = x + lvl4 + "<iCondAnt>" + de.getDE().getgDatGralOpe().getgOpeComer().getiCondAnt() + "</iCondAnt>" + "\n";
	                x = x + lvl4 + "<dDesCondAnt>" + de.getDE().getgDatGralOpe().getgOpeComer().getdDesCondAnt() + "</dDesCondAnt>" + "\n";
	            }
	            x = x + lvl3 + "</gOpeCom>" + "\n";
		    }
	        x = x + lvl3 + "<gEmis>" + "\n";
	        x = x + lvl4 + "<dRucEm>" + de.getDE().getgDatGralOpe().getgEmis().getdRucEm() + "</dRucEm>" + "\n";
	        x = x + lvl4 + "<dDVEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdDVEmi() + "</dDVEmi>" + "\n";
	        x = x + lvl4 + "<iTipCont>" + de.getDE().getgDatGralOpe().getgEmis().getiTipCont() + "</iTipCont>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgEmis().getcTipReg() != 0) {
	            x = x + lvl4 + "<cTipReg>" + de.getDE().getgDatGralOpe().getgEmis().getcTipReg() + "</cTipReg>" + "\n";
	        }
	        x = x + lvl4 + "<dNomEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdNomEmi() + "</dNomEmi>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgEmis().getdNomFanEmi() != null) {
	            x = x + lvl4 + "<dNomFanEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdNomFanEmi() + "</dNomFanEmi>" + "\n";
	        }
	        x = x + lvl4 + "<dDirEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdDirEmi() + "</dDirEmi>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgEmis().getdNumCas() != null) {
	            x = x + lvl4 + "<dNumCas>" + de.getDE().getgDatGralOpe().getgEmis().getdNumCas() + "</dNumCas>" + "\n";
	        } else {
	            x = x + lvl4 + "<dNumCas>" + "0" + "</dNumCas>" + "\n";	        	
	        }
	        if (de.getDE().getgDatGralOpe().getgEmis().getdCompDir1() != null) {
	            x = x + lvl4 + "<dCompDir1>" + de.getDE().getgDatGralOpe().getgEmis().getdCompDir1() + "</dCompDir1>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgEmis().getdCompDir2() != null) {
	            x = x + lvl4 + "<dCompDir2>" + de.getDE().getgDatGralOpe().getgEmis().getdCompDir2() + "</dCompDir2>" + "\n";
	        }
	        x = x + lvl4 + "<cDepEmi>" + de.getDE().getgDatGralOpe().getgEmis().getcDepEmi() + "</cDepEmi>" + "\n";
	        x = x + lvl4 + "<dDesDepEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdDesDepEmi() + "</dDesDepEmi>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgEmis().getcDisEmi() != 0) {
	            x = x + lvl4 + "<cDisEmi>" + de.getDE().getgDatGralOpe().getgEmis().getcDisEmi() + "</cDisEmi>" + "\n";
	            x = x + lvl4 + "<dDesDisEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdDesDisEmi() + "</dDesDisEmi>" + "\n";
	        }
	        x = x + lvl4 + "<cCiuEmi>" + de.getDE().getgDatGralOpe().getgEmis().getcCiuEmi() + "</cCiuEmi>" + "\n";
	        x = x + lvl4 + "<dDesCiuEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdDesCiuEmi() + "</dDesCiuEmi>" + "\n";
	        x = x + lvl4 + "<dTelEmi>" + de.getDE().getgDatGralOpe().getgEmis().getdTelEmi() + "</dTelEmi>" + "\n";
	        x = x + lvl4 + "<dEmailE>" + de.getDE().getgDatGralOpe().getgEmis().getdEmailE() + "</dEmailE>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgEmis().getdDenSuc() != null) {
	            x = x + lvl4 + "<dDenSuc>" + de.getDE().getgDatGralOpe().getgEmis().getdDenSuc() + "</dDenSuc>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgEmis().getgActEco() != null) {
	        	ArrayList<CamposActivEconomica> ea = de.getDE().getgDatGralOpe().getgEmis().getgActEco();
	        	if (ea.size() > 0) {
	        		Iterator i = ea.iterator();
	        		while (i.hasNext()) {
	        			CamposActivEconomica o = (CamposActivEconomica) i.next();
	                    x = x + lvl4 + "<gActEco>" + "\n";
	                    x = x + lvl5 + "<cActEco>" + o.getcActEco() + "</cActEco>" + "\n";
	                    x = x + lvl5 + "<dDesActEco>" + o.getdDesActEco() + "</dDesActEco>" + "\n";
	                    x = x + lvl4 + "</gActEco>" + "\n";
	        		}
	            }
	        }
	        if (de.getDE().getgDatGralOpe().getgEmis().getgRespDE() != null) {
                x = x + lvl4 + "<gRespDE>" + "\n";
                x = x + lvl5 + "<iTipIDRespDE>" + de.getDE().getgDatGralOpe().getgEmis().getgRespDE().getiTipIDRespDE() + "</iTipIDRespDE>" + "\n";
                x = x + lvl5 + "<dDTipIDRespDE>" + de.getDE().getgDatGralOpe().getgEmis().getgRespDE().getdDTipIDRespDE() + "</dDTipIDRespDE>" + "\n";
                x = x + lvl5 + "<dNumIDRespDE>" + de.getDE().getgDatGralOpe().getgEmis().getgRespDE().getdNumIDRespDE() + "</dNumIDRespDE>" + "\n";
                x = x + lvl5 + "<dNomRespDE>" + de.getDE().getgDatGralOpe().getgEmis().getgRespDE().getdNomRespDE() + "</dNomRespDE>" + "\n";
                x = x + lvl5 + "<dCarRespDE>" + de.getDE().getgDatGralOpe().getgEmis().getgRespDE().getdCarRespDE() + "</dCarRespDE>" + "\n";	        	
                x = x + lvl4 + "</gRespDE>" + "\n";
	        }
	        x = x + lvl3 + "</gEmis>" + "\n";
	        //
            x = x + lvl3 + "<gDatRec>" + "\n";
            x = x + lvl4 + "<iNatRec>" + de.getDE().getgDatGralOpe().getgDatRec().getiNatRec() + "</iNatRec>" + "\n";
	        x = x + lvl4 + "<iTiOpe>" + de.getDE().getgDatGralOpe().getgDatRec().getiTiOpe() + "</iTiOpe>" + "\n";
	        x = x + lvl4 + "<cPaisRec>" + de.getDE().getgDatGralOpe().getgDatRec().getcPaisRec() + "</cPaisRec>" + "\n";
	        x = x + lvl4 + "<dDesPaisRe>" + de.getDE().getgDatGralOpe().getgDatRec().getdDesPaisRe() + "</dDesPaisRe>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgDatRec().getiNatRec() == 1) {
	            x = x + lvl4 + "<iTiContRec>" + de.getDE().getgDatGralOpe().getgDatRec().getiTiContRec() + "</iTiContRec>" + "\n";
		        x = x + lvl4 + "<dRucRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdRucRec() + "</dRucRec>" + "\n";
		        x = x + lvl4 + "<dDVRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDVRec() + "</dDVRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getiNatRec() == 2 & 
	            de.getDE().getgDatGralOpe().getgDatRec().getiTiOpe() != 4) {
	            x = x + lvl4 + "<iTipIDRec>" + de.getDE().getgDatGralOpe().getgDatRec().getiTipIDRec() + "</iTipIDRec>" + "\n";
	            x = x + lvl4 + "<dDTipIDRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDTipIDRec() + "</dDTipIDRec>" + "\n";
	            // En caso de DE innominado, completar con 0 (cero)
	            x = x + lvl4 + "<dNumIDRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdNumIDRec() + "</dNumIDRec>" + "\n";
	        }
	        x = x + lvl4 + "<dNomRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdNomRec() + "</dNomRec>" + "\n";
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdNomFanRec() != null) {
	            x = x + lvl4 + "<dNomFanRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdNomFanRec() + "</dNomFanRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdDirRec() != null) {
	            x = x + lvl4 + "<dDirRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDirRec() + "</dDirRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdNumCasRec() != 0) {
	            x = x + lvl4 + "<dNumCasRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdNumCasRec() + "</dNumCasRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getcDepRec() != 0) {
	            x = x + lvl4 + "<cDepRec>" + de.getDE().getgDatGralOpe().getgDatRec().getcDepRec() + "</cDepRec>" + "\n";
	            x = x + lvl4 + "<dDesDepRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDesDepRec() + "</dDesDepRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getcDisRec() != 0) {
	            x = x + lvl4 + "<cDisRec>" + de.getDE().getgDatGralOpe().getgDatRec().getcDisRec() + "</cDisRec>" + "\n";
	            x = x + lvl4 + "<dDesDisRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDesDisRec() + "</dDesDisRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getcCiuRec() != 0) {
	            x = x + lvl4 + "<cCiuRec>" + de.getDE().getgDatGralOpe().getgDatRec().getcCiuRec() + "</cCiuRec>" + "\n";
	            x = x + lvl4 + "<dDesCiuRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdDesCiuRec() + "</dDesCiuRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdTelRec() != null) {
	            x = x + lvl4 + "<dTelRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdTelRec() + "</dTelRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdEmailRec() != null) {
	            x = x + lvl4 + "<dEmailRec>" + de.getDE().getgDatGralOpe().getgDatRec().getdEmailRec() + "</dEmailRec>" + "\n";
	        }
	        if (de.getDE().getgDatGralOpe().getgDatRec().getdCodCliente() != null) {
	            x = x + lvl4 + "<dCodCliente>" + de.getDE().getgDatGralOpe().getgDatRec().getdCodCliente() + "</dCodCliente>" + "\n";
	        }
	        x = x + lvl3 + "</gDatRec>" + "\n";
	        //
	        x = x + lvl2 + "</gDatGralOpe>" + "\n";
	        //
	        x = x + lvl2 + "<gDtipDE>" + "\n";
	        if (de.getDE().getgTimb().getiTiDE() == 1) {
	            x = x + lvl3 + "<gCamFE>" + "\n";
	            x = x + lvl4 + "<iIndPres>" + de.getDE().getgDtipDE().getgCamFE().getiIndPres() + "</iIndPres>" + "\n";
	            x = x + lvl4 + "<dDesIndPres>" + de.getDE().getgDtipDE().getgCamFE().getdDesIndPres() + "</dDesIndPres>" + "\n";
	            if (de.getDE().getgDtipDE().getgCamFE().getdFecEmNR() != null) {
	                x = x + lvl4 + "<dFecEmNR>" + de.getDE().getgDtipDE().getgCamFE().getdFecEmNR() + "</dFecEmNR>" + "\n";
	            }
	            x = x + lvl3 + "</gCamFE>" + "\n";
	        }
	        if (de.getDE().getgTimb().getiTiDE() == 1 | de.getDE().getgTimb().getiTiDE() == 4) {
	            if (de.getDE().getgDtipDE().getgCamCond() != null) {
	        	    CamposCondicionOperacion c = de.getDE().getgDtipDE().getgCamCond();
		            x = x + lvl3 + "<gCamCond>" + "\n";
		            x = x + lvl4 + "<iCondOpe>" + c.getiCondOpe() + "</iCondOpe>" + "\n";
		            x = x + lvl4 + "<dDCondOpe>" + c.getdDCondOpe() + "</dDCondOpe>" + "\n";
		            if (c.getiCondOpe() == 1) {
	        	        ArrayList<CamposOperacionContado> n = c.getgPaConEIni();
	        	        if (n != null) {
	        		        if (n.size() > 0) {
		        		        Iterator i = n.iterator();
		        		        while (i.hasNext()) {
		        			        CamposOperacionContado o = (CamposOperacionContado) i.next();
		        	                x = x + lvl4 + "<gPaConEIni>" + "\n";
		        	                x = x + lvl5 + "<iTiPago>" + o.getiTiPago() + "</iTiPago>" + "\n";
		        	                x = x + lvl5 + "<dDesTiPag>" + o.getdDesTiPag() + "</dDesTiPag>" + "\n";
		        	                x = x + lvl5 + "<dMonTiPag>" + o.getdMonTiPag() + "</dMonTiPag>" + "\n";
		        	                x = x + lvl5 + "<cMoneTiPag>" + o.getcMoneTiPag() + "</cMoneTiPag>" + "\n";
		        	                x = x + lvl5 + "<dDMoneTiPag>" + o.getdDMoneTiPag() + "</dDMoneTiPag>" + "\n";
		        	                if (o.getcMoneTiPag().equalsIgnoreCase("PYG") == false) {
		        	                    x = x + lvl5 + "<dTiCamTiPag>" + o.getdTiCamTiPag() + "</dTiCamTiPag>" + "\n";
		        	                }
		        	                if (o.getgPagTarCD() != null) {
			        	                x = x + lvl6 + "<gPagTarCD>" + "\n";		        	        	
			        	                x = x + lvl7 + "<iDenTarj>" + o.getgPagTarCD().getiDenTarj() + "</iDenTarj>" + "\n";		        	        	
			        	                x = x + lvl7 + "<dDesDenTarj>" + o.getgPagTarCD().getdDesDenTarj() + "</dDesDenTarj>" + "\n";
			        	                if (o.getgPagTarCD().getdRSProTar() != null) {
			        	                    x = x + lvl7 + "<dRSProTar>" + o.getgPagTarCD().getdRSProTar() + "</dRSProTar>" + "\n";	
			        	                }
			        	                if (o.getgPagTarCD().getdRUCProTar() != null) {
			        	                    x = x + lvl7 + "<dRUCProTar>" + o.getgPagTarCD().getdRUCProTar() + "</dRUCProTar>" + "\n";	
			        	                }
			        	                if (o.getgPagTarCD().getdDVProTar() != 0) {
			        	                    x = x + lvl7 + "<dDVProTar>" + o.getgPagTarCD().getdDVProTar() + "</dDVProTar>" + "\n";	
			        	                }
			        	                x = x + lvl7 + "<iForProPa>" + o.getgPagTarCD().getiForProPa() + "</iForProPa>" + "\n";	
			        	                if (o.getgPagTarCD().getdCodAuOpe() != 0) {
			        	                    x = x + lvl7 + "<dCodAuOpe>" + o.getgPagTarCD().getdCodAuOpe() + "</dCodAuOpe>" + "\n";	
			        	                }
			        	                if (o.getgPagTarCD().getdNomTit() != null) {
			        	                    x = x + lvl7 + "<dNomTit>" + o.getgPagTarCD().getdNomTit() + "</dNomTit>" + "\n";	
			        	                }
			        	                if (o.getgPagTarCD().getdNumTarj() != 0) {
			        	                    x = x + lvl7 + "<dNumTarj>" + o.getgPagTarCD().getdNumTarj() + "</dNumTarj>" + "\n";	
			        	                }
			        	                x = x + lvl6 + "</gPagTarCD>" + "\n";
		        	                }
		        	                if (o.getgPagCheq() != null) {
			        	                x = x + lvl6 + "<gPagCheq>" + "\n";		        	        	
			        	                x = x + lvl7 + "<dNumCheq>" + o.getgPagCheq().getdNumCheq() + "</dNumCheq>" + "\n";		        	        	
			        	                x = x + lvl7 + "<dBcoEmi>" + o.getgPagCheq().getdBcoEmi() + "</dBcoEmi>" + "\n";		        	        	
			        	                x = x + lvl6 + "</gPagCheq>" + "\n";		        	        	
		        	                }
		        	                x = x + lvl4 + "</gPaConEIni>" + "\n";
		            	        }
	        		        }		
	        	        }
	                } else {
	        	        CamposOperacionCredito r = de.getDE().getgDtipDE().getgCamCond().getgPagCred();
	        	        if (r != null) {
	                        x = x + lvl4 + "<gPagCred>" + "\n";
	                        x = x + lvl5 + "<iCondCred>" + r.getiCondCred() + "</iCondCred>" + "\n";
	                        x = x + lvl5 + "<dDCondCred>" + r.getdDCondCred() + "</dDCondCred>" + "\n";
	                        if (r.getdPlazoCre() != null) {
	                            x = x + lvl5 + "<dPlazoCre>" + r.getdPlazoCre() + "</dPlazoCre>" + "\n";
	                        }
	                        if (r.getdCuotas() != 0) {
	                            x = x + lvl5 + "<dCuotas>" + r.getdCuotas() + "</dCuotas>" + "\n";
	                        }
	                        if (r.getdMonEnt() != new BigDecimal("0")) {
	                            x = x + lvl5 + "<dMonEnt>" + r.getdMonEnt() + "</dMonEnt>" + "\n";
	                        }
	                        x = x + lvl4 + "</gPagCred>" + "\n";
	        	        }
	                }
	                x = x + lvl3 + "</gCamCond>" + "\n";
	            }
		    }
        	ArrayList<CamposItemsOperacion> l = de.getDE().getgDtipDE().getgCamItem();
        	if (l.size() > 0) {
        		Iterator i = l.iterator();
        		while (i.hasNext()) {
        			CamposItemsOperacion o = (CamposItemsOperacion) i.next();
	                x = x + lvl3 + "<gCamItem>" + "\n";
	                x = x + lvl4 + "<dCodInt>" + o.getdCodInt() + "</dCodInt>" + "\n";
	                x = x + lvl4 + "<dDesProSer>" + o.getdDesProSer() + "</dDesProSer>" + "\n";
	                x = x + lvl4 + "<cUniMed>" + o.getcUniMed() + "</cUniMed>" + "\n";
	                x = x + lvl4 + "<dDesUniMed>" + o.getdDesUniMed() + "</dDesUniMed>" + "\n";
	                x = x + lvl4 + "<dCantProSer>" + o.getdCantProSer() + "</dCantProSer>" + "\n";
	    	        if (de.getDE().getgTimb().getiTiDE() != 7) {
	                    x = x + lvl4 + "<gValorItem>" + "\n";
	                    x = x + lvl5 + "<dPUniProSer>" + o.getgValorItem().getdPUniProSer() + "</dPUniProSer>" + "\n";
	                    if (de.getDE().getgDatGralOpe().getgOpeComer().getdCondTiCam() == 2) {
		                    x = x + lvl5 + "<dTiCamIt>" + o.getgValorItem().getdTiCamIt() + "</dTiCamIt>" + "\n";	                    	
	                    }
	                    x = x + lvl5 + "<dTotBruOpeItem>" + o.getgValorItem().getdTotBruOpeItem() + "</dTotBruOpeItem>" + "\n";
	                    if (o.getgValorItem().getgValorRestaItem() != null) {
	                        x = x + lvl5 + "<gValorRestaItem>" + "\n";
	                        x = x + lvl6 + "<dDescItem>" + o.getgValorItem().getgValorRestaItem().getdDescItem() + "</dDescItem>" + "\n";
	                        x = x + lvl6 + "<dPorcDesIt>" + o.getgValorItem().getgValorRestaItem().getdPorcDesIt() + "</dPorcDesIt>" + "\n";
	                        x = x + lvl6 + "<dDescGloItem>" + o.getgValorItem().getgValorRestaItem().getdDescGloItem() + "</dDescGloItem>" + "\n";
	                        x = x + lvl6 + "<dAntPreUniIt>" + o.getgValorItem().getgValorRestaItem().getdAntPreUniIt() + "</dAntPreUniIt>" + "\n";
	                        x = x + lvl6 + "<dAntGloPreUniIt>" + o.getgValorItem().getgValorRestaItem().getdAntGloPreUniIt() + "</dAntGloPreUniIt>" + "\n";
	                        x = x + lvl6 + "<dTotOpeItem>" + o.getgValorItem().getgValorRestaItem().getdTotOpeItem() + "</dTotOpeItem>" + "\n";
	                        x = x + lvl6 + "<dTotOpeGs>" + o.getgValorItem().getgValorRestaItem().getdTotOpeGs() + "</dTotOpeGs>" + "\n";
	                        x = x + lvl5 + "</gValorRestaItem>" + "\n";
	                    }
	                }
	                x = x + lvl4 + "</gValorItem>" + "\n";
	                if ((de.getDE().getgDatGralOpe().getgOpeComer().getiTImp() == 1 |
	                	de.getDE().getgDatGralOpe().getgOpeComer().getiTImp() == 3 |
	                	de.getDE().getgDatGralOpe().getgOpeComer().getiTImp() == 4 |
	                	de.getDE().getgDatGralOpe().getgOpeComer().getiTImp() == 5) &
	                	(de.getDE().getgTimb().getiTiDE() != 4 & de.getDE().getgTimb().getiTiDE() != 7)) {
	                    if (o.getgCamIVA() != null) {
	                        x = x + lvl4 + "<gCamIVA>" + "\n";
	                        x = x + lvl5 + "<iAfecIVA>" + o.getgCamIVA().getiAfecIVA() + "</iAfecIVA>" + "\n";
	                        x = x + lvl5 + "<dDesAfecIVA>" + o.getgCamIVA().getdDesAfecIVA() + "</dDesAfecIVA>" + "\n";
	                        x = x + lvl5 + "<dPropIVA>" + o.getgCamIVA().getdPropIVA() + "</dPropIVA>" + "\n";
	                        x = x + lvl5 + "<dTasaIVA>" + o.getgCamIVA().getdTasaIVA() + "</dTasaIVA>" + "\n";
	                        x = x + lvl5 + "<dBasGravIVA>" + o.getgCamIVA().getdBasGravIVA() + "</dBasGravIVA>" + "\n";
	                        x = x + lvl5 + "<dLiqIVAItem>" + o.getgCamIVA().getdLiqIVAItem() + "</dLiqIVAItem>" + "\n";
	                        x = x + lvl4 + "</gCamIVA>" + "\n";
	                    }
	                }
	                x = x + lvl3 + "</gCamItem>" + "\n";
	            }
	        }
            x = x + lvl2 + "</gDtipDE>" + "\n";
            //
            x = x + lvl2 + "<gTotSub>" + "\n";
            x = x + lvl3 + "<dSub10>" + de.getDE().getgTotSub().getdSub10() + "</dSub10>" + "\n";
            x = x + lvl3 + "<dTotOpe>" + de.getDE().getgTotSub().getdTotOpe() + "</dTotOpe>" + "\n";
            x = x + lvl3 + "<dTotDesc>" + de.getDE().getgTotSub().getdTotDesc() + "</dTotDesc>" + "\n";
            x = x + lvl3 + "<dTotDescGlotem>" + de.getDE().getgTotSub().getdTotDescGlotem() + "</dTotDescGlotem>" + "\n";
            x = x + lvl3 + "<dTotAntItem>" + de.getDE().getgTotSub().getdTotAntItem() + "</dTotAntItem>" + "\n";
            x = x + lvl3 + "<dTotAnt>" + de.getDE().getgTotSub().getdTotAnt() + "</dTotAnt>" + "\n";
            x = x + lvl3 + "<dPorcDescTotal>" + de.getDE().getgTotSub().getdPorcDescTotal() + "</dPorcDescTotal>" + "\n";
            x = x + lvl3 + "<dDescTotal>" + de.getDE().getgTotSub().getdDescTotal() + "</dDescTotal>" + "\n";
            x = x + lvl3 + "<dAnticipo>" + de.getDE().getgTotSub().getdAnticipo() + "</dAnticipo>" + "\n";
            x = x + lvl3 + "<dRedon>" + de.getDE().getgTotSub().getdRedon() + "</dRedon>" + "\n";
            x = x + lvl3 + "<dComi>" + de.getDE().getgTotSub().getdComi() + "</dComi>" + "\n";
            x = x + lvl3 + "<dTotGralOpe>" + de.getDE().getgTotSub().getdTotGralOpe() + "</dTotGralOpe>" + "\n";
            x = x + lvl3 + "<dIVA5>" + de.getDE().getgTotSub().getdIVA5() + "</dIVA5>" + "\n";
            x = x + lvl3 + "<dIVA10>" + de.getDE().getgTotSub().getdIVA10() + "</dIVA10>" + "\n";
            x = x + lvl3 + "<dLiqTotIVA5>" + de.getDE().getgTotSub().getdLiqTotIVA5() + "</dLiqTotIVA5>" + "\n";
            x = x + lvl3 + "<dLiqTotIVA10>" + de.getDE().getgTotSub().getdLiqTotIVA10() + "</dLiqTotIVA10>" + "\n";
            x = x + lvl3 + "<dIVAComi>" + de.getDE().getgTotSub().getdIVAComi() + "</dIVAComi>" + "\n";
            x = x + lvl3 + "<dTotIVA>" + de.getDE().getgTotSub().getdTotIVA() + "</dTotIVA>" + "\n";
            x = x + lvl3 + "<dBaseGrav5>" + de.getDE().getgTotSub().getdBaseGrav5() + "</dBaseGrav5>" + "\n";
            x = x + lvl3 + "<dBaseGrav10>" + de.getDE().getgTotSub().getdBaseGrav10() + "</dBaseGrav10>" + "\n";
            x = x + lvl3 + "<dTBasGraIVA>" + de.getDE().getgTotSub().getdTBasGraIVA() + "</dTBasGraIVA>" + "\n";
            x = x + lvl2 + "</gTotSub>" + "\n";
            //
            x = x + lvl1 + "</DE>" + "\n";        	
		}
        x = x + "</rDE>" + "\n";
		//
		System.out.println(x);
		return x;
	}
}
