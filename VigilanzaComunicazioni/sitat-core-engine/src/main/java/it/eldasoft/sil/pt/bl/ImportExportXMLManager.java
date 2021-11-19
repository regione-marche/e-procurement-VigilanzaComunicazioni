package it.eldasoft.sil.pt.bl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.InterventiFornitureServizi;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.InterventiTriennali;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.BeniImmobili;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.BeniImmobili.Bene;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.InterventiTriennali.Intervento.InterventoAnnuale;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.LavoriEconomia;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.LavoriEconomia.LavoroEconomia;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.Note;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.QuadroRisorse;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument.Pt.SetupInfo;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.transaction.TransactionStatus;

/**
 * @author Mirco.Franzoni - Eldasoft S.p.A. Treviso
 *
 */

public class ImportExportXMLManager {

	 /** Logger Log4J di classe. */
  private static Logger      logger = Logger.getLogger(ControlliValidazioneCUPManager.class);
  /** manager per operazioni di interrogazione del db. */
  private SqlManager sqlManager;
  private GeneManager geneManager;

   /**
   * 
   * @return SqlManager
   */
  public SqlManager getSqlManager() {
    return this.sqlManager;
  }

  /**setSqlManager.
   * @param sqlManagerParam
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManagerParam) {
    this.sqlManager = sqlManagerParam;
  }
  public void setGeneManager(GeneManager geneManagerParam) {
	    this.geneManager = geneManagerParam;
	  }

  /**
   * Crea il documento XML ministeriale versione 3.0.0 AliProg4 relativo al programma.
   *
   * @param contri codice del programma
   * @param tiprog tipo del programma
   * 
   * @return oggetto documento contenente i dati del programma
   * @throws GestoreException GestoreException 
   */
	public XmlObject getXMLAliProg4Programma(Long contri, String tiprog, int idAmministrazione)
			throws GestoreException {
		String sqlSelectPiatri = "select ANNTRI,BRETRI,RESPRO,DV1TRI,DV2TRI,DV3TRI,MU1TRI,MU2TRI,MU3TRI,PR1TRI,PR2TRI,PR3TRI,IM1TRI,IM2TRI,IM3TRI,BI1TRI,BI2TRI,BI3TRI,AL1TRI,AL2TRI,AL3TRI,TO1TRI,TO2TRI,TO3TRI,CENINT,IMPACC,NOTSCHE1,NOTSCHE2,NOTSCHE2B,NOTSCHE3,NOTSCHE4 from piatri where contri = ? ";
		String sqlSelectInttri = "select * from inttri where contri = ? and (tipint = 1 " + (tiprog.equals("1")?" OR tipint is null":"") + " ) order by annrif, nprogint";
		String sqlSelectInttriFS = "select * from inttri where contri = ? and tipint = 2 order by conint";
		String sqlSelectEcotri = "select * from ecotri where contri = ?";
		XmlObject document = null;
		DataColumnContainer piatri = null;
		PtDocument doc = PtDocument.Factory.newInstance();
		Pt programma = doc.addNewPt();

		try {
			piatri = new DataColumnContainer(this.sqlManager, "PIATRI",	sqlSelectPiatri, new Object[] { contri });
			//aggiorno l'id amministratore nella tabella uffint
			TransactionStatus status = this.sqlManager.startTransaction();
			this.sqlManager.update("update uffint set idammin = ? where codein = ? ", new Object[] {idAmministrazione, piatri.getString("CENINT") });
			this.sqlManager.commitTransaction(status);
			//setup info
			SetupInfo setupInfo = programma.addNewSetupInfo();
			setupInfo.setDbDecSeparator(".");
			setupInfo.setSysDecSeparator(",");
			setupInfo.setHiprog3Version("3.0.2");
			//dati generali
			programma.setProdottoDa("AliProg4");
			programma.setFirmaHash("");
			programma.setAnnoElencoAnnuale(piatri.getLong("ANNTRI").toString());
			programma.setTriennio(piatri.getLong("ANNTRI") + "/" + (piatri.getLong("ANNTRI") + 2));
			String codiceFiscale = (String) this.sqlManager.getObject(PtManager.sqlSelectCfisStazioneAppaltante, new Object[] { piatri.getString("CENINT") });
			programma.setAmmCodfisc(codiceFiscale);
			programma.setIdEnte(idAmministrazione);	
			programma.setDescrizione(piatri.getString("BRETRI")!=null?piatri.getString("BRETRI"):"");
			//responsabile programma
			//tecnico
			if (piatri.getString("RESPRO") != null) {
				DataColumnContainer tecnico = new DataColumnContainer(this.sqlManager, "TECNI",	PtManager.sqlSelectTecni, new Object[] { piatri.getString("RESPRO") });
				programma.setNomeRproc((tecnico.getString("NOMETEI") != null) ? tecnico.getString("NOMETEI") : "");
				programma.setCognomeRproc((tecnico.getString("COGTEI") != null) ? tecnico.getString("COGTEI") : "");
				programma.setCfRproc((tecnico.getString("CFTEC") != null) ? tecnico.getString("CFTEC").toUpperCase() : "");
			}
			//note
			Note note = programma.addNewNote();
			note.setScheda1(piatri.getString("NOTSCHE1")!=null?piatri.getString("NOTSCHE1"):"");
			note.setScheda2(piatri.getString("NOTSCHE2")!=null?piatri.getString("NOTSCHE2"):"");
			note.setScheda2B(piatri.getString("NOTSCHE2B")!=null?piatri.getString("NOTSCHE2B"):"");
			note.setScheda3(piatri.getString("NOTSCHE3")!=null?piatri.getString("NOTSCHE3"):"");
			note.setScheda4(piatri.getString("NOTSCHE4")!=null?piatri.getString("NOTSCHE4"):"");
			//quadro risorse
			QuadroRisorse quadroRisorse = programma.addNewQuadroRisorse();
			quadroRisorse.setPtTriennio(piatri.getLong("ANNTRI") + "/" + (piatri.getLong("ANNTRI") + 2));
			quadroRisorse.setAnnoInserim(piatri.getLong("ANNTRI").toString());
			
			quadroRisorse.setImpVincolato1(PtManager.doubleToMoney(piatri.getDouble("DV1TRI")));
			quadroRisorse.setImpVincolato2(PtManager.doubleToMoney(piatri.getDouble("DV2TRI")));
			quadroRisorse.setImpVincolato3(PtManager.doubleToMoney(piatri.getDouble("DV3TRI")));
			quadroRisorse.setImpVincolatoTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("DV1TRI"),piatri.getDouble("DV2TRI"),piatri.getDouble("DV3TRI")}));

			quadroRisorse.setImpMutuo1(PtManager.doubleToMoney(piatri.getDouble("MU1TRI")));
			quadroRisorse.setImpMutuo2(PtManager.doubleToMoney(piatri.getDouble("MU2TRI")));
			quadroRisorse.setImpMutuo3(PtManager.doubleToMoney(piatri.getDouble("MU3TRI")));
			quadroRisorse.setImpMutuoTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("MU1TRI"),piatri.getDouble("MU2TRI"),piatri.getDouble("MU3TRI")}));
			
			quadroRisorse.setImpCapitaliPrivati1(PtManager.doubleToMoney(piatri.getDouble("PR1TRI")));
			quadroRisorse.setImpCapitaliPrivati2(PtManager.doubleToMoney(piatri.getDouble("PR2TRI")));
			quadroRisorse.setImpCapitaliPrivati3(PtManager.doubleToMoney(piatri.getDouble("PR3TRI")));
			quadroRisorse.setImpCapitaliPrivatiTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("PR1TRI"),piatri.getDouble("PR2TRI"),piatri.getDouble("PR3TRI")}));
			
			quadroRisorse.setImpTrasfImmob1(PtManager.doubleToMoney(piatri.getDouble("IM1TRI")));
			quadroRisorse.setImpTrasfImmob2(PtManager.doubleToMoney(piatri.getDouble("IM2TRI")));
			quadroRisorse.setImpTrasfImmob3(PtManager.doubleToMoney(piatri.getDouble("IM3TRI")));
			quadroRisorse.setImpTrasfImmobTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("IM1TRI"),piatri.getDouble("IM2TRI"),piatri.getDouble("IM3TRI")}));
			
			quadroRisorse.setImpStanzBilancio1(PtManager.doubleToMoney(piatri.getDouble("BI1TRI")));
			quadroRisorse.setImpStanzBilancio2(PtManager.doubleToMoney(piatri.getDouble("BI2TRI")));
			quadroRisorse.setImpStanzBilancio3(PtManager.doubleToMoney(piatri.getDouble("BI3TRI")));
			quadroRisorse.setImpStanzBilancioTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("BI1TRI"),piatri.getDouble("BI2TRI"),piatri.getDouble("BI3TRI")}));
			
			quadroRisorse.setImpAltro1(PtManager.doubleToMoney(piatri.getDouble("AL1TRI")));
			quadroRisorse.setImpAltro2(PtManager.doubleToMoney(piatri.getDouble("AL2TRI")));
			quadroRisorse.setImpAltro3(PtManager.doubleToMoney(piatri.getDouble("AL3TRI")));
			quadroRisorse.setImpAltroTotale(PtManager.sumToMoney(new Double[]{piatri.getDouble("AL1TRI"),piatri.getDouble("AL2TRI"),piatri.getDouble("AL3TRI")}));
			
			quadroRisorse.setImp1Totale(PtManager.sumToMoney(new Double[]{piatri.getDouble("TO1TRI"),piatri.getDouble("PR1TRI")}));
			quadroRisorse.setImp2Totale(PtManager.sumToMoney(new Double[]{piatri.getDouble("TO2TRI"),piatri.getDouble("PR2TRI")}));
			quadroRisorse.setImp3Totale(PtManager.sumToMoney(new Double[]{piatri.getDouble("TO3TRI"),piatri.getDouble("PR3TRI")}));
			
			quadroRisorse.setAccantonamento1Anno(PtManager.doubleToMoney(piatri.getDouble("IMPACC")));
			//interventi triennali
			InterventiTriennali interventiTriennali = programma.addNewInterventiTriennali();
			List< ? > interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri });
			if (interventi != null && interventi.size() > 0) {
				for (int i = 0; i < interventi.size(); i++) {
					DataColumnContainer inttri = new DataColumnContainer(this.sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri });
					inttri.setValoriFromMap((HashMap< ?, ? >) interventi.get(i), true);
					InterventiTriennali.Intervento intervento = interventiTriennali.addNewIntervento();
					intervento.setNProgressivo(i + 1);
					intervento.setCodIntervAmm((inttri.getString("CODINT") != null) ? inttri.getString("CODINT") : "");
					intervento.setAnnoIntervento(Long.toString(piatri.getLong("ANNTRI") + inttri.getLong("ANNRIF") - 1));
					intervento.setLocalizzazioneCodIstat((inttri.getString("COMINT") != null) ? inttri.getString("COMINT") : "");
					intervento.setCodiceCpv((inttri.getString("CODCPV") != null) ? inttri.getString("CODCPV") : "");
					intervento.setCodiceNuts((inttri.getString("NUTS") != null) ? inttri.getString("NUTS") : "");
					intervento.setDescrizioneInterv((inttri.getString("DESINT") != null) ? inttri.getString("DESINT") : "");
					if (inttri.getObject("SEZINT") != null) {
						intervento.setTipoOpera(Integer.parseInt(inttri.getString("SEZINT")));
					}
					Object idCategoria = this.sqlManager.getObject(PtManager.sqlSelectCategoriaIntervento, new Object[] { inttri.getString("CATINT") });
					if (idCategoria != null) {
						intervento.setIdCategoria(Integer.parseInt(idCategoria.toString()));
					}
					if (inttri.getObject("FLAG_CUP") != null) {
						intervento.setEsenteCup(Integer.parseInt(inttri.getObject("FLAG_CUP").toString()));
					}
					intervento.setCodiceCup((inttri.getString("CUPPRG") != null) ? inttri.getString("CUPPRG") : "");
					
					intervento.setCostoStimato1(PtManager.sumToMoney(new Double[]{inttri.getDouble("DI1INT"), inttri.getDouble("PR1TRI")}));
					intervento.setCostoStimato2(PtManager.sumToMoney(new Double[]{inttri.getDouble("DI2INT"), inttri.getDouble("PR2TRI")}));
					intervento.setCostoStimato3(PtManager.sumToMoney(new Double[]{inttri.getDouble("DI3INT"), inttri.getDouble("PR3TRI")}));
					intervento.setCostoStimatoTot(PtManager.sumToMoney(new Double[]{inttri.getDouble("DI1INT"),inttri.getDouble("DI2INT"),inttri.getDouble("DI3INT"), inttri.getDouble("PR1TRI"), inttri.getDouble("PR2TRI"), inttri.getDouble("PR3TRI")}));
					
					if ((!intervento.getCostoStimato1().equals("0,00") && !intervento.getCostoStimato2().equals("0,00")) || (!intervento.getCostoStimato1().equals("0,00") && !intervento.getCostoStimato3().equals("0,00")) || (!intervento.getCostoStimato2().equals("0,00") && !intervento.getCostoStimato3().equals("0,00"))) {
						intervento.setPiuAnnualita("Y");
					} else {
						intervento.setPiuAnnualita("N");
					}
					
					intervento.setImpVincolato1(PtManager.doubleToMoney(inttri.getDouble("DV1TRI")));
					intervento.setImpVincolato2(PtManager.doubleToMoney(inttri.getDouble("DV2TRI")));
					intervento.setImpVincolato3(PtManager.doubleToMoney(inttri.getDouble("DV3TRI")));

					intervento.setImpMutuo1(PtManager.doubleToMoney(inttri.getDouble("MU1TRI")));
					intervento.setImpMutuo2(PtManager.doubleToMoney(inttri.getDouble("MU2TRI")));
					intervento.setImpMutuo3(PtManager.doubleToMoney(inttri.getDouble("MU3TRI")));
					
					intervento.setImpCapitaliPrivati1(PtManager.doubleToMoney(inttri.getDouble("PR1TRI")));
					intervento.setImpCapitaliPrivati2(PtManager.doubleToMoney(inttri.getDouble("PR2TRI")));
					intervento.setImpCapitaliPrivati3(PtManager.doubleToMoney(inttri.getDouble("PR3TRI")));
					
					intervento.setImpTrasfImmob1(PtManager.doubleToMoney(inttri.getDouble("IM1TRI")));
					intervento.setImpTrasfImmob2(PtManager.doubleToMoney(inttri.getDouble("IM2TRI")));
					intervento.setImpTrasfImmob3(PtManager.doubleToMoney(inttri.getDouble("IM3TRI")));
					
					intervento.setImpStanzBilancio1(PtManager.doubleToMoney(inttri.getDouble("BI1TRI")));
					intervento.setImpStanzBilancio2(PtManager.doubleToMoney(inttri.getDouble("BI2TRI")));
					intervento.setImpStanzBilancio3(PtManager.doubleToMoney(inttri.getDouble("BI3TRI")));
					
					intervento.setImpAltro1(PtManager.doubleToMoney(inttri.getDouble("AL1TRI")));
					intervento.setImpAltro2(PtManager.doubleToMoney(inttri.getDouble("AL2TRI")));
					intervento.setImpAltro3(PtManager.doubleToMoney(inttri.getDouble("AL3TRI")));
					
					if (!intervento.getImpTrasfImmob1().equals("0,00") || !intervento.getImpTrasfImmob2().equals("0,00") || !intervento.getImpTrasfImmob3().equals("0,00")) {
						intervento.setTrasfImmobFlag("Y");
					} else {
						intervento.setTrasfImmobFlag("N");
					}
					/*
					if (!quadroRisorse.getImpTrasfImmobTotale().equals("0,00")) {
						intervento.setTrasfImmobFlag("Y");
					} else {
						intervento.setTrasfImmobFlag("N");
					}*/
					intervento.setTipoApportoCapitale((inttri.getObject("TCPINT") != null) ? Integer.parseInt(inttri.getString("TCPINT")) : 0);
					intervento.setImpCapitalePrivato(PtManager.doubleToMoney(inttri.getDouble("ICPINT")));
					intervento.setHasIntAnnuale((inttri.getString("ANNINT") != null && inttri.getString("ANNINT").equals("1")) ? "Y" : "N");
					if (inttri.getObject("PRGINT") != null) {
						intervento.setPriorita(Integer.parseInt(inttri.getObject("PRGINT").toString()));
					}
					intervento.setOrderId(i + 1);
					//intervento annuale
					if (intervento.getHasIntAnnuale().equals("Y")) {
						InterventoAnnuale annuale = intervento.addNewInterventoAnnuale();
						annuale.setConfUrbanistica((inttri.getString("URCINT") != null && inttri.getString("URCINT").equals("1")) ? "Y" : "N");
						annuale.setConfAmbientale((inttri.getString("APCINT") != null && inttri.getString("APCINT").equals("1")) ? "Y" : "N");
						Object statoProgettazione = this.sqlManager.getObject(PtManager.sqlSelectStatoProgettazione, new Object[] { inttri.getString("STAPRO") });
						if (statoProgettazione != null) {
							annuale.setStatoProgettazione(Integer.parseInt(statoProgettazione.toString()));
						}
						Object finalita = this.sqlManager.getObject(PtManager.sqlSelectFinalitaIntervento, new Object[] { inttri.getString("FIINTR") });
						if (finalita != null) {
							annuale.setFinalita(Integer.parseInt(finalita.toString()));
						}
						annuale.setTrimInizioLav((inttri.getObject("TILINT") != null) ? Integer.parseInt(inttri.getLong("TILINT").toString()) : 0);
						annuale.setAnnoInizioLav(inttri.getLong("AILINT").toString());
						annuale.setTrimFineLav((inttri.getObject("TFLINT") != null) ? Integer.parseInt(inttri.getLong("TFLINT").toString()) : 0);
						annuale.setAnnoFineLav(inttri.getLong("AFLINT").toString());
						//tecnico
						if (inttri.getString("CODRUP") != null) {
							DataColumnContainer tecnico = new DataColumnContainer(this.sqlManager, "TECNI",	PtManager.sqlSelectTecni, new Object[] { inttri.getString("CODRUP") });
							if (inttri.getObject("CODRUP") != null) {
								//annuale.setIdRproc(Integer.parseInt(inttri.getString("CODRUP")));
								annuale.setIdRproc(0);
							}
							annuale.setNomeRproc((tecnico.getString("NOMETEI") != null) ? tecnico.getString("NOMETEI") : "");
							annuale.setCognomeRproc((tecnico.getString("COGTEI") != null) ? tecnico.getString("COGTEI") : "");
							annuale.setCfRproc((tecnico.getString("CFTEC") != null) ? tecnico.getString("CFTEC").toUpperCase() : "");
						}

					}
					//beni immobili
					List< ? > immobili = this.sqlManager.getListHashMap(PtManager.sqlSelectImmtrai, new Object[] { contri , inttri.getLong("CONINT")});
					BeniImmobili beniImmobili = intervento.addNewBeniImmobili();
					if (immobili != null && immobili.size() > 0) {
						for (int j = 0; j < immobili.size(); j++) {
							DataColumnContainer immtrai = new DataColumnContainer(sqlManager, "IMMTRAI", PtManager.sqlSelectImmtrai, new Object[] { contri, inttri.getLong("CONINT") });
							immtrai.setValoriFromMap((HashMap< ?, ? >) immobili.get(j), true);
							InterventiTriennali.Intervento.BeniImmobili.Bene bene = beniImmobili.addNewBene();
							bene.setDescImmobile((immtrai.getString("DESIMM") != null) ? immtrai.getString("DESIMM") : "");
							bene.setPienaProprieta((immtrai.getLong("PROIMM") == 1) ? "Y" : "N");
							bene.setSoloSuperficie((immtrai.getLong("PROIMM") == 2) ? "Y" : "N");
							bene.setValoreStimato(PtManager.doubleToMoney(immtrai.getDouble("VALIMM")));
							bene.setAnnualita(intervento.getAnnoIntervento());
						}
					}
					
					
				}
			}
			
			//lavori in economia
			List< ? > lavoriEconomia = this.sqlManager.getListHashMap(sqlSelectEcotri, new Object[] { contri });
			LavoriEconomia lavoriEco = programma.addNewLavoriEconomia();
			if (lavoriEconomia != null && lavoriEconomia.size() > 0) {
				for (int i = 0; i < lavoriEconomia.size(); i++) {
					DataColumnContainer ecotri = new DataColumnContainer(this.sqlManager, "ECOTRI", sqlSelectEcotri, new Object[] { contri });
					ecotri.setValoriFromMap((HashMap< ?, ? >) lavoriEconomia.get(i), true);
					LavoroEconomia lavoro = lavoriEco.addNewLavoroEconomia();
					lavoro.setNProgressivo(i + 1);
					lavoro.setDescrizioneLavoro((ecotri.getString("DESCRI") != null) ? ecotri.getString("DESCRI") : "");
					lavoro.setCodiceCup((ecotri.getString("CUPPRG") != null) ? ecotri.getString("CUPPRG") : "");
					lavoro.setStima(PtManager.doubleToMoney(ecotri.getDouble("STIMA")));
				}
			}
			
			//interventi forniture servizi
			List< ? > fornitureServizi = this.sqlManager.getListHashMap(sqlSelectInttriFS, new Object[] { contri });
			InterventiFornitureServizi interventiFornitureServizi = programma.addNewInterventiFornitureServizi();
			if (fornitureServizi != null && fornitureServizi.size() > 0) {
				for (int i = 0; i < fornitureServizi.size(); i++) {
					DataColumnContainer inttriFS = new DataColumnContainer(this.sqlManager, "INTTRI", sqlSelectInttriFS, new Object[] { contri });
					inttriFS.setValoriFromMap((HashMap< ?, ? >) fornitureServizi.get(i), true);
					InterventiFornitureServizi.Intervento intervento = interventiFornitureServizi.addNewIntervento();
					
					intervento.setNProgressivo(i + 1);
					intervento.setCodIntervAmm((inttriFS.getString("CODINT") != null) ? inttriFS.getString("CODINT") : "");
					intervento.setCodiceCpv((inttriFS.getString("CODCPV") != null) ? inttriFS.getString("CODCPV") : "");
					if (inttriFS.getObject("FLAG_CUP") != null) {
						intervento.setEsenteCup(Integer.parseInt(inttriFS.getObject("FLAG_CUP").toString()));
					}
					if (inttriFS.getString("CUPPRG") != null) {
						intervento.setCodiceCup(inttriFS.getString("CUPPRG"));
					}
					intervento.setDescrizioneInterv((inttriFS.getString("DESINT") != null) ? inttriFS.getString("DESINT") : "");
					intervento.setTipoIntervento((inttriFS.getString("TIPOIN") != null) ? inttriFS.getString("TIPOIN") : "");

					intervento.setImpStanzBilancio(PtManager.doubleToMoney(inttriFS.getDouble("BI1TRI")));
					intervento.setImpRisorseRegionali(PtManager.doubleToMoney(inttriFS.getDouble("RG1TRI")));
					intervento.setImpRisorseStatoUe(PtManager.doubleToMoney(inttriFS.getDouble("IMPRFS")));
					intervento.setImpMutuo(PtManager.doubleToMoney(inttriFS.getDouble("MU1TRI")));
					intervento.setImpCapitaliPrivati(PtManager.doubleToMoney(inttriFS.getDouble("PR1TRI")));
					intervento.setImpAltro(PtManager.doubleToMoney(inttriFS.getDouble("AL1TRI")));
					intervento.setImpTotalePresunto(PtManager.doubleToMoney(inttriFS.getDouble("DI1INT")));
					
					//tecnico
					if (inttriFS.getString("CODRUP") != null) {
						DataColumnContainer tecnico = new DataColumnContainer(this.sqlManager, "TECNI",	PtManager.sqlSelectTecni, new Object[] { inttriFS.getString("CODRUP") });
						if (inttriFS.getObject("CODRUP") != null) {
							//intervento.setIdRproc(Integer.parseInt(inttriFS.getString("CODRUP")));
							intervento.setIdRproc(0);
						}
						intervento.setNomeRproc((tecnico.getString("NOMETEI") != null) ? tecnico.getString("NOMETEI") : "");
						intervento.setCognomeRproc((tecnico.getString("COGTEI") != null) ? tecnico.getString("COGTEI") : "");
						intervento.setCfRproc((tecnico.getString("CFTEC") != null) ? tecnico.getString("CFTEC").toUpperCase() : "");
					}
				}
			}
					
		} catch (Exception e) {
			throw new GestoreException(
					"Errore durante la creazione del documento xml per il programma triennale",
					"Piano triennale n " + contri, e);
		}
		
		//procediamo alla validazione del document
		ArrayList<Object> validationErrors = new ArrayList<Object>();
	    XmlOptions validationOptions = new XmlOptions();
	    validationOptions.setErrorListener(validationErrors);
	      	
		if (!doc.validate(validationOptions)) {
			String listaErroriValidazione = "";
	      	Iterator< ? > iter = validationErrors.iterator();
	      	while (iter.hasNext()) {
	      		listaErroriValidazione += iter.next() + "\n";
	      	}
	      	logger.error("Errore durante la validazione formale del documento xml"
	      				+ "\n"
	      				+ listaErroriValidazione);
	      	throw new GestoreException(
	      				"Errore durante la validazione formale del documento xml",
	      				"commons.validate",
	      				new Object[] { validationErrors }, null);
		}
		//ricavo l'HashCode del documento xml
		int hash = (doc.toString() + "AliProg4 by Eldasoft S.p.a").hashCode();
		programma.setFirmaHash("" + hash);
		document = doc;
		document.documentProperties().setEncoding("iso-8859-1");
		
		return document;
	}

	  /**
	   * Importazione documento XML AliProg4 nel database.
	   * 
	   * @param doc documento XML AliProg4.
	   * @param codein codice stazione appaltante.
	   * @return Ritorna true se l'inserimento nel database va a buon fine.
	   *         false altrimenti.
	   */
	  public boolean importAliProg4(final PtDocument doc, String codein) throws GestoreException {
	    boolean result = false;
	    TransactionStatus transazione = null;
	    StringBuilder id = new StringBuilder("");
	    try {
	    	ArrayList<Object> validationErrors = new ArrayList<Object>();
	        XmlOptions validationOptions = new XmlOptions();
	        validationOptions.setErrorListener(validationErrors);
	        boolean isValid = doc.validate(validationOptions);

	        if (!isValid) {
	          String listaErroriValidazione = "";
	          Iterator<?> iter = validationErrors.iterator();
	          while (iter.hasNext()) {
	            listaErroriValidazione += iter.next() + "\n";
	          }
	        }
	    	
	    	if (!doc.validate()) {
	    		throw new GestoreException("Documento xml non valido","importXML.validate");
	    	} else {
    			transazione = this.sqlManager.startTransaction();
	    		//inserimento programma
	    		Long contri = insertPiatriAliProg4(doc, codein, id);
	    		//inserimento interventi
	    		insertInterventiAliProg4(doc, contri, codein);
	    		//inserimento lavori economia
	    		insertLavoriEconomiaAliProg4(doc, contri);
	    		//inserimento forniture servizi
	    		insertFornitureServiziAliProg4(doc, contri, codein);
	    		result = true;
	    	}
	    } catch (SQLException e) {
	    	throw new GestoreException("Non e' possibile importare il file XML allegato", "importXML.generic", e);
	    } finally {
	        if (transazione != null) {
	          try {
	            if (result)
	              sqlManager.commitTransaction(transazione);
	            else
	              sqlManager.rollbackTransaction(transazione);
	            } catch (SQLException e1) {
	            }
	        }
	    }
	    return result;
	  }

	  /**
	   * Inserimento nelle tabelle Programmi del documento XML passato come parametro
	   * 
	   * @param doc documento XML AliProg4.
	   * @param codein codice stazione appaltante.
	   * @param idProgramma se presente prima di procedere all'inserimento vengono cancellati i record corrispondenti
	   * 
	   * @return idProgramma codice del programma appena inserito PIATRI.ID
	   */
	  public String inserisciProgramma(final PtDocument doc, String codein, String idProgramma)
	  {
		  boolean result = false;
		  TransactionStatus transazione = null;
		  StringBuilder id = new StringBuilder("");
		  try {
			  transazione = this.sqlManager.startTransaction();
			  if (!StringUtils.isEmpty(idProgramma)) {
				  Long contri = (Long) sqlManager.getObject("select contri from piatri where id = ?", new Object[] { idProgramma });
				  this.sqlManager.update("delete from piatri where contri = ?", new Object[] { contri });
				  this.sqlManager.update("delete from inttri where contri = ?", new Object[] { contri });
				  this.sqlManager.update("delete from immtrai where contri = ?", new Object[] { contri });
				  this.sqlManager.update("delete from ecotri where contri = ?", new Object[] { contri });
			  }
		  	  //inserimento programma
		  	  Long contri = insertPiatriAliProg4(doc, codein, id);
		  	  //inserimento interventi
		  	  insertInterventiAliProg4(doc, contri, codein);
		  	  //inserimento lavori economia
		  	  insertLavoriEconomiaAliProg4(doc, contri);
		  	  //inserimento forniture servizi
		  	  insertFornitureServiziAliProg4(doc, contri, codein);
		  	  result = true;
		  } catch (Exception e) {
			  logger.error("Errore durante l'inserimento del programma triennale", e);
		  } finally {
			  if (transazione != null) {
		          try {
		        	  if (result) {
		        		  sqlManager.commitTransaction(transazione);
		        	  } else {
		        		  sqlManager.rollbackTransaction(transazione);
		        		  id = new StringBuilder("");
		        	  }
		          } catch (SQLException e1) {
		          }
		      }
		  }
		  return id.toString();
	  }
	  /**
	   * Inserimento nella tabella PIATRI a partire dall'XML AliProg4.
	   * 
	   * @param doc documento XML AliProg4.
	   * @param codein codice stazione appaltante.
	   * @param idProgramma codice di ritorno dell'id del programma
	   */
	private Long insertPiatriAliProg4(final PtDocument doc, String codein, StringBuilder idProgramma)
			throws GestoreException {
		String query = "";
		String fields = "";
		Long contri;
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			String cenint = codein;
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//ricavo il codice del programma
			contri = (Long) sqlManager.getObject(
					"select max(contri) from piatri", new Object[] {});
			if (contri == null) {
				contri = new Long(1);
			} else {
				contri = contri + 1;
			}
			//dati generali
			Long existTiprog3 = (Long) this.sqlManager.getObject("select count(*) from TAB1 where tab1cod='W9002' and tab1tip=3", new Object[] { });
			Long tiprog = new Long(1);
		    if (existTiprog3 > 0) {
		    	tiprog = new Long(3);
		    } 
			
		    idProgramma.append(GestorePIATRI.gestoreIdPiatri(sqlManager, cenint, tiprog, Long.parseLong(programma.getAnnoElencoAnnuale())));
			fields = "CONTRI, ID, BRETRI, TIPROG, CENINT, STATRI, ";
			values.add(contri);
			values.add(idProgramma.toString());
			values.add(programma.getDescrizione());
			values.add(tiprog);
			values.add(cenint);
			values.add(1);

	    	fields += "ANNTRI, ";
	    	values.add(Integer.parseInt(programma.getAnnoElencoAnnuale()));
	    	
			//note
			Note note = programma.getNote();
			fields += "NOTSCHE1, NOTSCHE2, NOTSCHE2B, NOTSCHE3, NOTSCHE4, ";
			values.add(note.getScheda1());
			values.add(note.getScheda2());
			values.add(note.getScheda2B());
			values.add(note.getScheda3());
			values.add(note.getScheda4());
			//Responsabile programma
			if (programma.getCfRproc()!=null && !programma.getCfRproc().equals("")) {
	    		fields += "RESPRO, ";
				values.add(gestioneTECNI(programma.getCognomeRproc(), programma.getNomeRproc(), programma.getCfRproc(), codein));
	    	}
	    	//quadro risorse
	    	QuadroRisorse quadroRisorse = programma.getQuadroRisorse();
	    	fields += "DV1TRI, DV2TRI, DV3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpVincolato1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpVincolato2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpVincolato3()));
	    	fields += "MU1TRI, MU2TRI, MU3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpMutuo1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpMutuo2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpMutuo3()));
	    	fields += "PR1TRI, PR2TRI, PR3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati3()));
	    	fields += "IM1TRI, IM2TRI, IM3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpTrasfImmob3()));
	    	fields += "BI1TRI, BI2TRI, BI3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpStanzBilancio3()));
	    	fields += "AL1TRI, AL2TRI, AL3TRI, ";
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpAltro1()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpAltro2()));
	    	values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpAltro3()));
	    	fields += "TO1TRI, TO2TRI, TO3TRI, ";
	    	values.add((PtManager.getImporti(sys_dec_separator, quadroRisorse.getImp1Totale()) - PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati1())));
	    	values.add((PtManager.getImporti(sys_dec_separator, quadroRisorse.getImp2Totale()) - PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati2())));
	    	values.add((PtManager.getImporti(sys_dec_separator, quadroRisorse.getImp3Totale()) - PtManager.getImporti(sys_dec_separator, quadroRisorse.getImpCapitaliPrivati3())));
	    	
	    	fields += "IMPACC, ";
	    	if (quadroRisorse.getAccantonamento1Anno() != null) {
	    		values.add(PtManager.getImporti(sys_dec_separator, quadroRisorse.getAccantonamento1Anno()));
	    	} else {
	    		values.add(new Double(0.0));
	    	}
	    	
	    	fields = fields.substring(0, fields.length()-2);
	    	query = "INSERT INTO PIATRI (" + fields + ") VALUES (";
	    	for (int i =0 ; i < values.size(); i++)
	    		query += "?,";
	    	query = query.substring(0, query.length()-1) + ")";
	    	//inserisco l'intervento
			this.sqlManager.update(query, values.toArray());
			
		} catch (GestoreException e) {
			throw new GestoreException(e.getMessage(),e.getCodice(), e);
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella PIATRI","importXML.piatri.insert", e);
		}
		return contri;
	}

	 /**
	   * Inserimento nella tabella INNTRI degli interventi a partire dall'XML AliProg4.
	   * 
	   * @param doc documento XML AliProg4.
	   * @param codein codice stazione appaltante.
	   */
	private void insertInterventiAliProg4(final PtDocument doc, Long contri, String codein)
			throws GestoreException {
		String query = "";
		String fields = "";
		NumberFormat formatter = new DecimalFormat("00");
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//interventi triennali
			InterventiTriennali interventiTriennali = programma.getInterventiTriennali();
			for (int i = 0; i < interventiTriennali.sizeOfInterventoArray(); i++) {
				values.clear();
				InterventiTriennali.Intervento intervento = interventiTriennali.getInterventoArray(i);
				fields = "CONTRI, ";
				values.add(contri);
				fields += "NPROGINT, ";
				values.add(i + 1);
				fields += "CONINT, ";
				values.add(intervento.getNProgressivo());
				fields += "CODINT, ";
				values.add(intervento.getCodIntervAmm());
				fields += "TIPINT, ";
				values.add(1);
				fields += "TIPOIN, ";
				values.add("L");
				fields += "MANTRI, ";
				values.add("1");				
				fields += "ANNRIF, ";
				values.add(Long.parseLong(intervento.getAnnoIntervento()) - Long.parseLong(programma.getAnnoElencoAnnuale()) + 1);
				fields += "COMINT, ";
				values.add(intervento.getLocalizzazioneCodIstat());
				fields += "CODCPV, ";
				values.add(intervento.getCodiceCpv());
				fields += "NUTS, ";
				values.add(intervento.getCodiceNuts());
				fields += "DESINT, ";
				values.add(intervento.getDescrizioneInterv());
				fields += "SEZINT, ";
				values.add(formatter.format(intervento.getTipoOpera()));
				
				List< ? > categoria = this.sqlManager.getVector("select tabcod1, tabcod2 from tabsche where tabcod = 'S2006' and tabcod3 = ?",
				          new Object[] { String.valueOf(intervento.getIdCategoria()) });
				if (categoria != null && categoria.size() > 0) {
					fields += "INTERV, ";
					values.add((String)SqlManager.getValueFromVectorParam(categoria, 0).getValue());
					fields += "CATINT, ";
					values.add(formatter.format(Integer.parseInt(SqlManager.getValueFromVectorParam(categoria, 1).getStringValue())));
				}
				fields += "FLAG_CUP, ";
				values.add((intervento.getEsenteCup()==0)?null:intervento.getEsenteCup());
				fields += "CUPPRG, ";
				values.add(intervento.getCodiceCup());
				
				fields += "DI1INT, DI2INT, DI3INT, DITINT, TOTINT, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getCostoStimato1()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getCostoStimato2()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getCostoStimato3()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati3()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getCostoStimatoTot()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati1()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati2()) - PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati3()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getCostoStimatoTot()));
		    	
		    	fields += "DV1TRI, DV2TRI, DV3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpVincolato1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpVincolato2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpVincolato3()));
		    	fields += "MU1TRI, MU2TRI, MU3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpMutuo1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpMutuo2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpMutuo3()));
		    	fields += "PR1TRI, PR2TRI, PR3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati3()));
		    	fields += "IM1TRI, IM2TRI, IM3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpTrasfImmob1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpTrasfImmob2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpTrasfImmob3()));
		    	fields += "BI1TRI, BI2TRI, BI3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpStanzBilancio1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpStanzBilancio2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpStanzBilancio3()));
		    	fields += "AL1TRI, AL2TRI, AL3TRI, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpAltro1()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpAltro2()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpAltro3()));
				
		    	fields += "TCPINT, ";
				values.add(formatter.format(intervento.getTipoApportoCapitale()));
				fields += "PRGINT, ";
				values.add(intervento.getPriorita());
				fields += "ICPINT, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpCapitalePrivato()));
		    	fields += "ANNINT, ";
		    	if (intervento.getHasIntAnnuale()!=null && intervento.getHasIntAnnuale().equalsIgnoreCase("Y")) {
		    		values.add("1");
		    		InterventoAnnuale annuale = intervento.getInterventoAnnuale();
		    		fields += "URCINT, ";
					values.add((annuale.getConfUrbanistica()!=null && annuale.getConfUrbanistica().equalsIgnoreCase("Y"))?"1":"2");
					fields += "APCINT, ";
					values.add((annuale.getConfAmbientale()!=null && annuale.getConfAmbientale().equalsIgnoreCase("Y"))?"1":"2");
					String stapro = (String)sqlManager.getObject("select tabcod1 from tabsche where tabcod = 'S2017' and tabcod3 = ? ", new Object[] {"" + annuale.getStatoProgettazione()});
					if (stapro != null) {
						fields += "STAPRO, ";
						values.add(stapro);
					}
					String fiintr = (String)sqlManager.getObject("select tabcod1 from tabsche where tabcod = 'S2016' and tabcod3 = ? ", new Object[] {"" + annuale.getFinalita()});
					if (fiintr != null) {
						fields += "FIINTR, ";
						values.add(fiintr);
					}
					fields += "TILINT, ";
					values.add(annuale.getTrimInizioLav());
					fields += "AILINT, ";
					values.add(Integer.parseInt(annuale.getAnnoInizioLav()));
					fields += "TFLINT, ";
					values.add(annuale.getTrimFineLav());
					fields += "AFLINT, ";
					values.add(Integer.parseInt(annuale.getAnnoFineLav()));
					fields += "CODRUP, ";
					
					values.add(gestioneTECNI(annuale.getCognomeRproc(), annuale.getNomeRproc(), annuale.getCfRproc(), codein));

		    	} else {
		    		values.add("2");
		    	}
				
		    	fields = fields.substring(0, fields.length()-2);
		    	query = "INSERT INTO INTTRI (" + fields + ") VALUES (";
		    	for (int j =0 ; j < values.size(); j++)
		    		query += "?,";
		    	query = query.substring(0, query.length()-1) + ")";
		    	//inserisco l'intervento
				this.sqlManager.update(query, values.toArray());
				//beni immobili
				BeniImmobili beniImmobili = intervento.getBeniImmobili();
				for (int y = 0; y < beniImmobili.sizeOfBeneArray(); y++) {
					values.clear();
					Bene bene = beniImmobili.getBeneArray(y);
					Long numimm = new Long(y + 1);
					fields = "CONTRI, CONINT, NUMIMM, ";
					values.add(contri);
					values.add(intervento.getNProgressivo());
					values.add(numimm);
					fields += "DESIMM, ";
					values.add(bene.getDescImmobile());
					
					if (bene.getPienaProprieta().equalsIgnoreCase("Y")) {
						fields += "PROIMM, ";
						values.add(new Long(1));
					} else if (bene.getSoloSuperficie().equalsIgnoreCase("Y")) {
						fields += "PROIMM, ";
						values.add(new Long(2));
					}
					fields += "VALIMM, ";
			    	values.add(PtManager.getImporti(sys_dec_separator, bene.getValoreStimato()));
					fields += "ANNIMM, ";
					values.add(Integer.parseInt(bene.getAnnualita()));
					
					fields = fields.substring(0, fields.length()-2);
			    	query = "INSERT INTO IMMTRAI (" + fields + ") VALUES (";
			    	for (int j =0 ; j < values.size(); j++)
			    		query += "?,";
			    	query = query.substring(0, query.length()-1) + ")";
			    	//inserisco il bene
					this.sqlManager.update(query, values.toArray());
					
				}
			}
			this.sqlManager.update("UPDATE INTTRI SET PRGINT = null WHERE PRGINT=0 AND CONTRI = " + contri, null);
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella INTTRI","importXML.inttri.insert", e);
		}
	}

	/**
	   * Inserimento nella tabella ECOTRI dei lavori in economia a partire dall'XML AliProg4.
	   * 
	   * @param doc documento XML AliProg4.
	   */
	private void insertLavoriEconomiaAliProg4(final PtDocument doc, Long contri)
			throws GestoreException {
		String query = "";
		String fields = "";
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//lavori in economia
		    LavoriEconomia lavoriEconomia = programma.getLavoriEconomia();
			for (int i = 0; i < lavoriEconomia.sizeOfLavoroEconomiaArray(); i++) {
				values.clear();
				LavoroEconomia lavoro = lavoriEconomia.getLavoroEconomiaArray(i);
				fields = "CONTRI, ";
				values.add(contri);
				fields += "CONECO, ";
				values.add(i + 1);
				fields += "DESCRI, ";
				values.add(lavoro.getDescrizioneLavoro());
				fields += "CUPPRG, ";
				values.add(lavoro.getCodiceCup());
				fields += "STIMA, ";
				values.add(PtManager.getImporti(sys_dec_separator, lavoro.getStima()));
				
		    	fields = fields.substring(0, fields.length()-2);
		    	query = "INSERT INTO ECOTRI (" + fields + ") VALUES (";
		    	for (int j =0 ; j < values.size(); j++)
		    		query += "?,";
		    	query = query.substring(0, query.length()-1) + ")";
		    	//inserisco il lavoro
				this.sqlManager.update(query, values.toArray());
			}
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella ECOTRI","importXML.ecotri.insert", e);
		}
	}
	
	/**
	   * Inserimento nella tabella INTTRI degli interventi forniture e servizi a partire dall'XML AliProg4.
	   * 
	   * @param doc documento XML AliProg4.
	   * @param codein codice stazione appaltante.
	   */
	private void insertFornitureServiziAliProg4(final PtDocument doc, Long contri, String codein)
			throws GestoreException {
		String query = "";
		String fields = "";
		ArrayList<Object> values = new ArrayList<Object>();
		try {
			Pt programma = doc.getPt();
			// Separatore decimale
		    String sys_dec_separator = programma.getSetupInfo().getSysDecSeparator();
			//interventi forniture e servizi
		    InterventiFornitureServizi interventiFornitureServizi = programma.getInterventiFornitureServizi();
		    //contatore forniture e servizi
		    int startIndex = programma.getInterventiTriennali().sizeOfInterventoArray();
			for (int i = 0; i < interventiFornitureServizi.sizeOfInterventoArray(); i++) {
				values.clear();
				InterventiFornitureServizi.Intervento intervento = interventiFornitureServizi.getInterventoArray(i);
				fields = "CONTRI, ";
				values.add(contri);
				fields += "NPROGINT, ";
				values.add(i + 1);
				fields += "CONINT, ";
				values.add(startIndex + intervento.getNProgressivo());
				fields += "TIPINT, ";
				values.add(2);
				fields += "CODINT, ";
				values.add(intervento.getCodIntervAmm());
				fields += "CODCPV, ";
				values.add(intervento.getCodiceCpv());
				fields += "FLAG_CUP, ";
				values.add((intervento.getEsenteCup()==0)?null:intervento.getEsenteCup());
				fields += "CUPPRG, ";
				values.add(intervento.getCodiceCup());
				fields += "DESINT, ";
				values.add(intervento.getDescrizioneInterv());
				fields += "TIPOIN, ";
				values.add(intervento.getTipoIntervento());
				fields += "ANNINT, ";
				values.add("1");
				fields += "BI1TRI, RG1TRI, IMPRFS, MU1TRI, PR1TRI, AL1TRI, DI1INT, DITINT, ";
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpStanzBilancio()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpRisorseRegionali()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpRisorseStatoUe()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpMutuo()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpCapitaliPrivati()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpAltro()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpTotalePresunto()));
		    	values.add(PtManager.getImporti(sys_dec_separator, intervento.getImpTotalePresunto()));
		    	
		    	if (intervento.getCfRproc()!=null && !intervento.getCfRproc().equals("")) {
		    		fields += "CODRUP, ";
					values.add(gestioneTECNI(intervento.getCognomeRproc(), intervento.getNomeRproc(), intervento.getCfRproc(), codein));
		    	}
		    	
		    	fields = fields.substring(0, fields.length()-2);
		    	query = "INSERT INTO INTTRI (" + fields + ") VALUES (";
		    	for (int j =0 ; j < values.size(); j++)
		    		query += "?,";
		    	query = query.substring(0, query.length()-1) + ")";
		    	//inserisco l'intervento
				this.sqlManager.update(query, values.toArray());
			}
		} catch (Exception e) {
			throw new GestoreException("Errore di inserimento nella tabella INTTRIFS","importXML.inttrifs.insert", e);
		}
	}
	/**
	   * ATTENZIONE - Uguale al metodo in PTmanager -
	   * Restituisce TECNI.CODTEC del tecnico
	   * Se il tecnico non esiste provvede ad inserirlo
	   * @param cognome_rproc
	   * @param nome_rproc
	   * @param cf_rproc
	   * @param cenint
	   * @return codice tecnico
	   * @throws SQLException
	   */
	  private String gestioneTECNI(String cognome_rproc, String nome_rproc, String cf_rproc, String cenint)
	      throws GestoreException {

	    String codtec = null;
	    
	    try {
	    	codtec = (String)this.sqlManager.getObject("select codtec from tecni where UPPER(cftec) = ? and cgentei = ?", new Object[] {cf_rproc.toUpperCase(), cenint});

		    if (codtec == null || (codtec != null && "".equals(codtec))) {
		    	// Il tecnico non esiste si procede all'inserimento nell'archivio dei
		    	// tecnici
		        // Calcolo delle chiave di TECNI
		        codtec = geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
		    
		        String sqlTECNI = "insert into tecni (codtec, " // 1
			          + "cogtei, " // 2
			          + "nometei, " // 3
			          + "nomtec, " // 4
			          + "cftec, " // 5
			          + "cgentei) " // 6
			          + "values "
			          + "(?,?,?,?,?,?)";
		        this.sqlManager.update(sqlTECNI, new Object[] {codtec, cognome_rproc, nome_rproc, cognome_rproc + " " + nome_rproc, cf_rproc, cenint});
		    }
	    }  catch (SQLException e) {
			throw new GestoreException("Errore di inserimento nella tabella TECNI.", null, e);
		}
	    return codtec;
	  }
	  
}