package it.eldasoft.w9.bl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.avlp.simog.massload.xmlbeans.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CPVSecondariaType;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.CategLottoType;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.DatiStipulaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.FlagTCType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.RecVarianteType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.RitardiType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;
import it.avlp.smartCig.comunicazione.ComunicazioneType;
import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.SituazioneGaraLotto;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.W3Z05Type;

public class ConsultaGaraUnificatoManager {

	/**
	 * Logger di classe.
	 */
	private static Logger logger = Logger.getLogger(ConsultaGaraUnificatoManager.class);

	/** Manager per le transazioni SQL. */
	private SqlManager sqlManager;

	/** W9Manager. */
	private W9Manager w9Manager;

	private GeneManager geneManager;
	
	private GenChiaviManager genChiaviManager;
	
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	public void setW9Manager(W9Manager w9Manager) {
		this.w9Manager = w9Manager;
	}
	
	public void setGeneManager(GeneManager geneManager) {
	  this.geneManager = geneManager;
	}
	
	public void setGenChiaviManager(GenChiaviManager genChiaviManager) {
		this.genChiaviManager = genChiaviManager;
	}
	
  public synchronized ConsultaLottoBean consultaGaraUnificatoWS(final String codiceCIG, final String idAvGara, final String codUffInt,
      final int syscon, final SchedaType schedaType, final GaraType garaType, final LottoType lottoType, boolean isCfgVigilanza) 
          throws SQLException, GestoreException, ParseException, Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraUnificatoWS: inizio metodo");
    }

    ConsultaLottoBean consultaLottoBean = new ConsultaLottoBean(codiceCIG, true, false);
    
    long idGara = garaType.getIDGARA();
    String abilitaControlloCFSA = ConfigManager.getValore(CostantiSimog.PROP_SITAT_CONSULTA_GARA_CONTROLLO_CFSA);
    String cfUffInt = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codUffInt });
    String cfAnac = (String) this.sqlManager.getObject("select CFANAC from UFFINT where CODEIN=?", new Object[] { codUffInt });

    if ((StringUtils.isEmpty(idAvGara) || (("" + garaType.getIDGARA()).equals(idAvGara))) 
        && ((abilitaControlloCFSA != null && abilitaControlloCFSA.equals("0")) || garaType.getCFAMMINISTRAZIONE().equals(cfUffInt) || garaType.getCFAMMINISTRAZIONE().equals(cfAnac))
        && (! garaType.isSetDATACANCELLAZIONEGARA())
        && (! lottoType.isSetDATACANCELLAZIONELOTTO())
        && lottoType.isSetDATAPUBBLICAZIONE()) {

      // Flag che indica se in DB esiste la gara con idAvGara specificato dall'utente
      boolean esisteGara = UtilitySITAT.existsGara("" + garaType.getIDGARA(), this.sqlManager);

      if (logger.isDebugEnabled()) {
        logger.debug("Inizio import del xml fornito dal WS dopo controllo su IDAVGARA e CF stazione appaltante");
        logger.debug("Nella base dati " + (esisteGara ? "esiste" : "non esiste") + " la gara con IDAVGARA = '" + idAvGara + "'");
      }
      
      Long codGara = null;
      if (esisteGara) {
        codGara = (Long) this.sqlManager.getObject("select CODGARA from W9GARA where idAVGARA=?", new Object[] { "" + idGara });
      } else {
        Long maxCodGara = (Long) this.sqlManager.getObject("select max(CODGARA)+1 from W9GARA", new Object[] {});
        if (maxCodGara == null) {
          codGara = new Long(1);
        } else {
          codGara = maxCodGara;
        }
      }
      consultaLottoBean.setCodgara(codGara);
      // Estrazione del campo TECNI.CODTEC per valorizzare i campi W9GARA.RUP e W9LOTT.RUP
      String codTec = null;
      String cfRup = null;
      if (schedaType != null && schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getDatiComuni() != null) {
        cfRup = schedaType.getDatiScheda().getDatiComuni().getCFRUP();
      }
      if (StringUtils.isEmpty(cfRup)) {
        if (garaType != null) {
          cfRup = garaType.getCFUTENTE();
        }
      }
      
      codTec = (String) this.sqlManager.getObject("select CODTEC from TECNI where CGENTEI=? and UPPER(CFTEC)=?", 
        new Object[] { codUffInt, cfRup.toUpperCase() });
      if (StringUtils.isEmpty(codTec)) {
        codTec = this.w9Manager.insertTecnico(codUffInt, cfRup.toUpperCase());
      }

      boolean esisteTecnico = StringUtils.isNotEmpty(codTec);

      if (esisteTecnico) {
        
        GregorianCalendar dataCreazioneGara = null;
        if (garaType.isSetDATACREAZIONE()) {
          dataCreazioneGara = new GregorianCalendar(
            garaType.getDATACREAZIONE().get(Calendar.YEAR), 
            garaType.getDATACREAZIONE().get(Calendar.MONTH), 
            garaType.getDATACREAZIONE().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        }
      
        GregorianCalendar dataPerfezionamentoBando = null;
        if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
          dataPerfezionamentoBando = new GregorianCalendar(
            garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.YEAR),
            garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.MONTH),
            garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        }
     
        if (!esisteGara) {
          List<String> listaSqlGaraCampi = new ArrayList<String>();
          listaSqlGaraCampi.add("CODGARA");
          listaSqlGaraCampi.add("SYSCON");
          listaSqlGaraCampi.add("OGGETTO");
          listaSqlGaraCampi.add("PROV_DATO");
          listaSqlGaraCampi.add("RUP");
          listaSqlGaraCampi.add("IMPORTO_GARA");
          listaSqlGaraCampi.add("NLOTTI");
          listaSqlGaraCampi.add("CODEIN");
          //listaSqlGaraCampi.add("RIC_ALLUV");
          listaSqlGaraCampi.add("SITUAZIONE");
          listaSqlGaraCampi.add("PREV_BANDO");
          listaSqlGaraCampi.add("VER_SIMOG");
            
          List<Object> listaSqlGaraParams = new ArrayList<Object>();
          listaSqlGaraParams.add(codGara);
          listaSqlGaraParams.add(syscon);
          listaSqlGaraParams.add(garaType.getOGGETTO());
          listaSqlGaraParams.add(new Long(2)); // Provenienza Simog
          listaSqlGaraParams.add(codTec);      // RUP
          listaSqlGaraParams.add(new Double(garaType.getIMPORTOGARA().doubleValue()));
          listaSqlGaraParams.add(new Long(1)); // NLOTTI
          listaSqlGaraParams.add(codUffInt);   // CODEIN
          //listaSqlGaraParams.add("2");         // RIC_ALLUV
          listaSqlGaraParams.add(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione()));  // SITUAZIONE
          listaSqlGaraParams.add("2");         // PREV_BANDO
          int versioneSIMOG = UtilitySITAT.getVersioneSimog(dataCreazioneGara, dataPerfezionamentoBando);
          listaSqlGaraParams.add(new Long(versioneSIMOG));  // VER_SIMOG
          
          if (versioneSIMOG >= 5) {
            if (garaType.isSetFLAGSAAGENTEGARA()) {
              listaSqlGaraCampi.add("FLAG_SA_AGENTE");
              listaSqlGaraParams.add(it.avlp.simog.massload.xmlbeans.FlagSNType.S.equals(garaType.getFLAGSAAGENTEGARA()) ? "1" : "2");
            }
            if (garaType.isSetDENAMMAGENTEGARA()) {
              listaSqlGaraCampi.add("DENOM_SA_AGENTE");
              listaSqlGaraParams.add(garaType.getDENAMMAGENTEGARA());
            }
            if (garaType.isSetCFAMMAGENTEGARA()) {  
              listaSqlGaraCampi.add("CF_SA_AGENTE");
              listaSqlGaraParams.add(garaType.getCFAMMAGENTEGARA());
            }
            if (garaType.isSetIDFDELEGATE()) {
              listaSqlGaraCampi.add("ID_F_DELEGATE");
              listaSqlGaraParams.add(new Long(garaType.getIDFDELEGATE()));
            }
          }
          
          if (garaType.isSetDURATAACCQUADROCONVENZIONEGARA()) {
            listaSqlGaraCampi.add("DURATA_ACCQUADRO");
            listaSqlGaraParams.add(new Long(garaType.getDURATAACCQUADROCONVENZIONEGARA()));
          } else if (schedaType.isSetDatiScheda() &&
            schedaType.getDatiScheda().getDatiComuni().isSetDURATAACCQUADROCONVENZIONE()) {
            listaSqlGaraCampi.add("DURATA_ACCQUADRO");
            listaSqlGaraParams.add(new Long(
              schedaType.getDatiScheda().getDatiComuni().getDURATAACCQUADROCONVENZIONE()));
          }
    
          if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
            listaSqlGaraCampi.add("DATA_PUBBLICAZIONE");
            listaSqlGaraParams.add(garaType.getDATAPERFEZIONAMENTOBANDO().getTime());
          }
        
          if (garaType.isSetIDGARA()) {
            listaSqlGaraCampi.add("IDAVGARA");
            listaSqlGaraParams.add("" + garaType.getIDGARA());
          }
        
          if (garaType.isSetMODOREALIZZAZIONE()) {
            listaSqlGaraCampi.add("TIPO_APP");
            listaSqlGaraParams.add(new Long(garaType.getMODOREALIZZAZIONE()));
          }
        
          if (garaType.isSetCIGACCQUADRO()) {
            listaSqlGaraCampi.add("CIG_ACCQUADRO");
            listaSqlGaraParams.add(garaType.getCIGACCQUADRO());
          }
        
          if (garaType.isSetMODOINDIZIONE()) {
            listaSqlGaraCampi.add("ID_MODO_INDIZIONE");
            listaSqlGaraParams.add(new Long(garaType.getMODOINDIZIONE()));
          }

          if (garaType.isSetTIPOSCHEDA()) {
            listaSqlGaraCampi.add("FLAG_ENTE_SPECIALE");
            listaSqlGaraParams.add(garaType.getTIPOSCHEDA().toString());
          }
          
          if (garaType.isSetURGENZADL133()) {
            listaSqlGaraCampi.add("SOMMA_URGENZA");
            listaSqlGaraParams.add(it.avlp.simog.massload.xmlbeans.FlagSNType.S.equals(garaType.getURGENZADL133()) ? "1" : "2");
          }
    
          if (garaType.isSetDATACREAZIONE()) {
            listaSqlGaraCampi.add("DATA_CREAZIONE");
            listaSqlGaraParams.add(garaType.getDATACREAZIONE().getTime());
          }
          
          String codiceCentroCostoFromSIMOG = garaType.getIDSTAZIONEAPPALTANTE();
          String descrCentroCostoFromSIMOG = garaType.getDENOMSTAZIONEAPPALTANTE();
          
          Long idCentroCosto = (Long) this.sqlManager.getObject(
              "select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)=? and CODEIN=?",
              new Object[] { codiceCentroCostoFromSIMOG.toUpperCase(), codUffInt });
    
          if (idCentroCosto == null) {
            Long maxIdCentroCosto = (Long) this.sqlManager.getObject("select max(IDCENTRO)+1 from CENTRICOSTO", new Object[] {});
            if (maxIdCentroCosto == null) {
              idCentroCosto = new Long(1);
            } else {
              idCentroCosto = maxIdCentroCosto;
            }
    
            this.sqlManager.update("INSERT INTO CENTRICOSTO (IDCENTRO,CODEIN,CODCENTRO,DENOMCENTRO) VALUES (?,?,?,?)",
                new Object[] { idCentroCosto, codUffInt, codiceCentroCostoFromSIMOG, StringUtils.substring(descrCentroCostoFromSIMOG,0,250) });
          } else {
            this.sqlManager.update("UPDATE CENTRICOSTO set DENOMCENTRO=? where IDCENTRO=?", 
              new Object[] { StringUtils.substring(descrCentroCostoFromSIMOG,0,250), idCentroCosto });
          }
    
          listaSqlGaraCampi.add("IDCC");
          listaSqlGaraParams.add(idCentroCosto);
    
          // Inserimento in W9GARA
          StringBuffer listaCampi = new StringBuffer("");
          StringBuffer listaParams = new StringBuffer("");
          for (int ir = 0; ir < listaSqlGaraCampi.size(); ir++) {
            listaCampi.append(listaSqlGaraCampi.get(ir) + ", ");
            listaParams.append("?,");
          }
          
          String sqlInsertW9GARA = "insert into W9GARA (LISTACAMPI) values (LISTAPARAMS)";
          sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
          sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 1));
          this.sqlManager.update(sqlInsertW9GARA, listaSqlGaraParams.toArray());
      
          this.istanziaPubblicazione(schedaType, codGara);
        }

        if (lottoType.isSetDATACANCELLAZIONELOTTO()) {
          consultaLottoBean.setCaricato(false);
          consultaLottoBean.setMsg("Lotto non importato perche' cancellato");
        } else {
          Long codLott = (Long) this.sqlManager.getObject(
            "select max(CODLOTT)+1 from W9LOTT where CODGARA=?", new Object[] { codGara });
          if (codLott == null) {
            codLott = new Long(1);
          }
    
          Long numeroLotti = (Long) this.sqlManager.getObject(
            "select count(*) from W9LOTT where CODGARA=?", new Object[] { codGara });
            numeroLotti++;
    
          List<String> listaSqlLottoCampi = new ArrayList<String>();
          List<Object> listaSqlLottoParams = new ArrayList<Object>();
    
          listaSqlLottoCampi.add("CODGARA");
          listaSqlLottoCampi.add("CODLOTT");
          listaSqlLottoCampi.add("OGGETTO");
          listaSqlLottoCampi.add("RUP");
          listaSqlLottoCampi.add("IMPORTO_LOTTO");
          listaSqlLottoCampi.add("CPV");
          listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE");
          listaSqlLottoCampi.add("ID_CATEGORIA_PREVALENTE");
          listaSqlLottoCampi.add("NLOTTO");
          listaSqlLottoCampi.add("CIG");
          listaSqlLottoCampi.add("SITUAZIONE");
          listaSqlLottoCampi.add("DATA_CONSULTA_GARA");
          listaSqlLottoCampi.add("EXSOTTOSOGLIA");
        
          listaSqlLottoParams.add(codGara);
          listaSqlLottoParams.add(codLott);
          listaSqlLottoParams.add(lottoType.getOGGETTO());
          listaSqlLottoParams.add(codTec); // RUP
          if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()
              - new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()).doubleValue()));
          } else {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
          }
          
          listaSqlLottoParams.add(lottoType.getCPV()); // CPV
          listaSqlLottoParams.add(Long.parseLong(lottoType.getIDSCELTACONTRAENTE())); // Id Scelta contraente
          String categoria = (String) this.sqlManager.getObject(
            "select CODSITAT from W9CODICI_AVCP where CODAVCP = ? and TABCOD = 'W3z03' ",
            new Object[] { lottoType.getIDCATEGORIAPREVALENTE() });
          // se manca corrispondenza inserisco il codice non decodificato
          if (StringUtils.isEmpty(categoria)) {
            categoria = lottoType.getIDCATEGORIAPREVALENTE();
          }
          listaSqlLottoParams.add(categoria); // Id Categoria Prevalente
      
          listaSqlLottoParams.add(codLott); // NLOTTO (uguale a W9LOTT.CODLOTT)
          listaSqlLottoParams.add(codiceCIG); // CIG
          listaSqlLottoParams.add(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione())); // Situazione gara
          listaSqlLottoParams.add(new Timestamp(new GregorianCalendar().getTimeInMillis())); // Data consulta gara
        
          // ExSottoSoglia: se lotto.Importo < 150k e gara.DataPubblicazione < 29/10/2013
          Double tempImportoLotto = new Double(lottoType.getIMPORTOLOTTO().doubleValue());
          boolean isDataPubbPrecedente =  false;
          boolean isImportoLottoSottoSoglia = false;
          if (tempImportoLotto.compareTo(new Double(150000)) < 0) {
            isImportoLottoSottoSoglia = true;
          }
          if (isImportoLottoSottoSoglia && dataPerfezionamentoBando != null) {
            if (dataPerfezionamentoBando.before(new GregorianCalendar(2013, 9, 29, 0, 0, 0))) {
              isDataPubbPrecedente = true;
            }
          }
          if (isImportoLottoSottoSoglia && isDataPubbPrecedente) {
            listaSqlLottoParams.add("1");
          } else {
            listaSqlLottoParams.add("2");
          }
          // ExSottoSoglia - end
          if (dataPerfezionamentoBando != null 
            && dataPerfezionamentoBando.before(UtilitySITAT.getDataAttuazioneSimogVer2())) {
            listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE_50");
            listaSqlLottoParams.add(this.getIdSceltaContraente50(lottoType.getIDSCELTACONTRAENTE()));
          }
      
          if (garaType.isSetTIPOSCHEDA()) {
            listaSqlLottoCampi.add("FLAG_ENTE_SPECIALE");
            listaSqlLottoParams.add(garaType.getTIPOSCHEDA().toString());
          }
      
          listaSqlLottoCampi.add("DAEXPORT");
          listaSqlLottoParams.add("1");
      
          listaSqlLottoCampi.add("IMPORTO_ATTUAZIONE_SICUREZZA");
          if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()));
          } else {
            listaSqlLottoParams.add(new Double(0));
          }
      
          listaSqlLottoCampi.add("IMPORTO_TOT");
          listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
      
          listaSqlLottoCampi.add("CLASCAT");
      
          double importoLotto = lottoType.getIMPORTOLOTTO().doubleValue();
          if (importoLotto <= 258000) {
            listaSqlLottoParams.add("I");
          } else if (importoLotto <= 516000) {
            listaSqlLottoParams.add("II");
          } else if (importoLotto <= 1033000) {
            listaSqlLottoParams.add("III");
          } else if (importoLotto <= 1500000) {
            listaSqlLottoParams.add("IIIB");
          } else if (importoLotto <= 2582000) {
            listaSqlLottoParams.add("IV");
          } else if (importoLotto <= 3500000) {
            listaSqlLottoParams.add("IVB");
          } else if (importoLotto <= 5165000) {
            listaSqlLottoParams.add("V");
          } else if (importoLotto <= 10329000) {
            listaSqlLottoParams.add("VI");
          } else if (importoLotto <= 15494000) {
            listaSqlLottoParams.add("VII");
          } else {
            listaSqlLottoParams.add("VIII");
          }

          if (lottoType.isSetTIPOCONTRATTO()) {
            listaSqlLottoCampi.add("TIPO_CONTRATTO");
            listaSqlLottoParams.add(lottoType.getTIPOCONTRATTO().toString());
            listaSqlLottoCampi.add("MANOD");
            if (lottoType.getTIPOCONTRATTO().toString().equalsIgnoreCase(W3Z05Type.L.toString())) {
              listaSqlLottoParams.add("1");
            } else {
              listaSqlLottoParams.add("2");
            }
          } else {
            listaSqlLottoCampi.add("MANOD");
            listaSqlLottoParams.add("2");
          }

          listaSqlLottoCampi.add("COMCON");
          listaSqlLottoParams.add("2");
          if (lottoType.isSetLUOGOISTAT()) {
            String luogoIstat = lottoType.getLUOGOISTAT();
            if (luogoIstat.length() == 6) {
              String codiceIstatRegione = (String) this.sqlManager.getObject(
                "select tabcod3 from tabsche where tabcod = 'S2003' and tabcod1='09' and tabcod3 like ?",
                new Object[] { "%".concat(luogoIstat) });
              if (StringUtils.isNotEmpty(codiceIstatRegione)) {
                luogoIstat = codiceIstatRegione;
              } else {
                luogoIstat = null;
              }
            }
            listaSqlLottoCampi.add("LUOGO_ISTAT");
            listaSqlLottoParams.add(luogoIstat);
          }
      
          if (lottoType.isSetLUOGONUTS()) {
            listaSqlLottoCampi.add("LUOGO_NUTS");
            String tmp = this.verificaNUTS(lottoType.getLUOGONUTS());
            if (tmp != null) {
              listaSqlLottoParams.add(tmp);
            } else {
              listaSqlLottoParams.add(lottoType.getLUOGONUTS());
            }
          }
      
          // CUI programmazione
          if (lottoType.isSetANNUALECUIMININF()) {
            listaSqlLottoCampi.add("CUIINT");
            listaSqlLottoParams.add(lottoType.getANNUALECUIMININF());
          }
          
          // ART_E1
          listaSqlLottoCampi.add("ART_E1");
          int versioneSimog = UtilitySITAT.getVersioneSimog(this.sqlManager, codGara);
          if (versioneSimog <= 2) {
            if (lottoType.isSetIDESCLUSIONE()) {
              String strArtEsclusione = lottoType.getIDESCLUSIONE();
              int artEsclusione = Integer.parseInt(strArtEsclusione);
              if (artEsclusione <= 7 || artEsclusione == 11) {
                listaSqlLottoParams.add("1");
              } else {
                listaSqlLottoParams.add("2");
              }
            } else {
              listaSqlLottoParams.add("2");
            }
          } else {
            listaSqlLottoParams.add("2");
          }

          if (lottoType.isSetCUPLOTTO()) {
            CUPLOTTOType cupLotto = lottoType.getCUPLOTTO();
        
            // Per ora si legge solo il primo codice CUP presente nell'array CODICICUP
            // e si suppone che il CUP sia legato al lotto a cui e' associato
            DatiCUPType datiCup = cupLotto.getCODICICUPArray(0);
        
            listaSqlLottoCampi.add("CUP");
            listaSqlLottoParams.add(datiCup.getCUP());
            listaSqlLottoCampi.add("CUPESENTE");
            listaSqlLottoParams.add("2");
          } else {
            listaSqlLottoCampi.add("CUPESENTE");
            listaSqlLottoParams.add("1");
          }
      
          if (schedaType.isSetDatiScheda()) {
            if (schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDALOCALE()) {
              listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
              listaSqlLottoParams.add(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE());
            } else {
              listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
              listaSqlLottoParams.add(codiceCIG);
            }
          } else {
            listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
            listaSqlLottoParams.add(codiceCIG);
          }
          
          if (versioneSimog > 2) {
            listaSqlLottoCampi.add("CONTRATTO_ESCLUSO_ALLEGGERITO");
            if (lottoType.isSetFLAGESCLUSO()) {
              if ("S".equals(lottoType.getFLAGESCLUSO())) {
                listaSqlLottoParams.add("1");
              } else {
                listaSqlLottoParams.add("2");
              }
            } else {
              listaSqlLottoParams.add("2");
            }
      
            listaSqlLottoCampi.add("ESCLUSIONE_REGIME_SPECIALE");
            if (lottoType.isSetFLAGREGIME()) {
              if (FlagSNType.S.equals(lottoType.getFLAGREGIME())) {
                listaSqlLottoParams.add("1");
              } else {
                listaSqlLottoParams.add("2");
              }
            } else {
              listaSqlLottoParams.add("2");
            }
          }
          
          // Inserimento W9LOTT
          String sqlInsertW9LOTT = "insert into W9LOTT (LISTACAMPI) values (LISTAPARAMS)";
          StringBuffer listaCampi = new StringBuffer("");
          StringBuffer listaParams = new StringBuffer("");
          for (int ir = 0; ir < listaSqlLottoCampi.size(); ir++) {
            listaCampi.append(listaSqlLottoCampi.get(ir) + ", ");
              listaParams.append("?, ");
          }
        
          sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
          sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 2));
          this.sqlManager.update(sqlInsertW9LOTT, listaSqlLottoParams.toArray());
      
          // Inserimento W9LOTTCATE
          this.istanziaCategorieLotto(lottoType, codGara, codLott);
      
          // inserimento W9APPALAV
          this.istanziaTipologieLavoroLotto(lottoType, codGara, codLott);
      
          // Inserimento W9APPAFORN
          this.istanziaModalitaAcquisFornServLotto(lottoType, codGara, codLott);
      
          // Inserimento W9CPV
          this.istanziaCpvSecondariLotto(lottoType, codGara, codLott);
          
          if (esisteGara) {
            // Aggiornamento W9GARA
            this.sqlManager.update("update W9GARA set NLOTTI=?, SITUAZIONE=? where CODGARA=?", 
              new Object[] { numeroLotti, SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione(), codGara });
          }
          
          if (schedaType.isSetDatiScheda()) {
          	boolean importaSchedeGara = "1".equals(ConfigManager.getValore("it.eldasoft.simog.consultaGara.importaFasi"));
          	
          	SchedaCompletaType[] arraySchedeComplete = schedaType.getDatiScheda().getSchedaCompletaArray();
          	SchedaCompletaType schedaCompleta = arraySchedeComplete[arraySchedeComplete.length-1];
          	
            // inserimento W9ESITO solo in cfg Osservatorio
          	if (!isCfgVigilanza && ("1".equals(garaType.getIDFDELEGATE()) || "2".equals(garaType.getIDFDELEGATE()) || "4".equals(garaType.getIDFDELEGATE()) || importaSchedeGara)) { 
          		this.istanziaEsito(codGara, codLott, schedaType, codUffInt, isCfgVigilanza);
          	}
	          // Inserimento dell'aggiudicazione (W9APPA, W9AGGI, IMPR, IMPLEG, TEIM, W9INCA, W9FINA)
            if ("1".equals(garaType.getIDFDELEGATE()) || "2".equals(garaType.getIDFDELEGATE()) || 
            			(importaSchedeGara && hasAggiudicazione(schedaCompleta))) {  
             	this.istanziaAggiudicazione(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
             			schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
            }
            
            // Importazione delle fasi successive all'aggiudicazione
            if (importaSchedeGara) {

          		if (hasAggiudicazioneSottosoglia(schedaCompleta)) {
          			this.istanziaAggiudicazioneSottosoglia(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAggiudicazioneEsclusa(schedaCompleta)) {
          			this.istanziaAggiudicazioneEsclusa(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAdesione(schedaCompleta)) {
          			this.istanziaAdesioneAccordoQuadro(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasInizio(schedaCompleta)) {
          			this.istanziaInizio(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasStipula(schedaCompleta)) {
          			this.istanziaStipulaAccordoQuadro(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAvanzamento(schedaCompleta)) {
          			this.istanziaFaseAvanzamento(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasSospensioni(schedaCompleta)) {
          			this.istanziaSospensioni(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasSubappalti(schedaCompleta)) {
          			this.istanziaSubappalti(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}
          		
          		if (hasConclusione(schedaCompleta)) {
          			this.istanziaFaseConclusione(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasRitardo(schedaCompleta)) {
          			this.istanziaRitardo(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasAccordoBonario(schedaCompleta)) {
          			this.istanziaAccordBonario(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasVarianti(schedaCompleta)) {
          			this.istanziaVarianti(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}
          		
          		if (hasCollaudo(schedaCompleta)) {
          			this.istanziaFaseCollaudo(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getResponsabili().getResponsabileArray());
          		}
          		
            }
            
            // Aggiornamento della situazione della gara e del lotto
            this.w9Manager.updateSituazioneGaraLotto(codGara, codLott);
          }

          if (this.w9Manager.isStazioneAppaltanteConPermessiDaApplicareInCreazione(codUffInt)) {
            this.w9Manager.updateAttribuzioneModello(codGara, new Long(1));

            // Inserimento dei ruolo per il RUP, altrimenti con la gestione dei
            // permessi attiva nessun utente accederebbe alla gara/lotto
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { codGara, codLott, new Long(1), new Long(1), "RA", 14, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { codGara, codLott, new Long(1), new Long(2), "RA", 15, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { codGara, codLott, new Long(1), new Long(3), "RA", 16, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { codGara, codLott, new Long(1), new Long(4), "RA", 17, codTec });
          }
          consultaLottoBean.setCaricato(true);
          consultaLottoBean.setCaricato(true);
          consultaLottoBean.setMsg("Lotto importato");
        }
        //resultMap.put("esito", Boolean.TRUE);
      } else {
        // Non e' stato individuato il tecnico con CFTEC = cfRup recuperato in Simog oppure non si e' riusciti a crearlo
        String msgErrore = "Per la richiesta consultaGara avviata dall'utente SYSCON=" + syscon
          + " non si e' riusciti ad individuare il tecnico o a creare il tecnico)";
        
        logger.error(msgErrore);

        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Lotto non importato: non e' stato individuato il RUP");
      }

    } else {
      if ((StringUtils.isNotEmpty(idAvGara) && !("" + garaType.getIDGARA()).equals(idAvGara))) {
        logger.error("La richiesta consultaGara ha restituito una gara con IDGARA diverso da quello digitato dall'utente");

        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto fa riferimento ad un Numero Gara ANAC diverso da quello indicato");
      
      } else if (!((abilitaControlloCFSA != null && abilitaControlloCFSA.equals("0")) || garaType.getCFAMMINISTRAZIONE().equals(cfUffInt) || garaType.getCFAMMINISTRAZIONE().equals(cfAnac))) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: la stazione appaltante "
            + "associata al codice CIG e' differente da quella da cui si sta effettuando l’operazione, cioe' il codice fiscale "
            + "della stazione appaltante in uso (UFFINT.CFEIN) e' diverso dal campo CF_AMMINISTRAZIONE ritornato dal WS dell'AVCP.");
          
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto fa riferimento ad una stazione appaltante diversa da quella indicata");
        
      } else if (garaType.isSetDATACANCELLAZIONEGARA()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: "
          + "il codice CIG indicato fa riferimento ad una gara cancellata.");
      
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Gara cancellata");
      
      } else if (lottoType.isSetDATACANCELLAZIONELOTTO()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: "
          + "il codice CIG indicato fa riferimento ad un lotto cancellato.");
      
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto e' cancellato");
      
      } else if (!lottoType.isSetDATAPUBBLICAZIONE()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: "
          + "il codice CIG indicato fa riferimento ad un lotto non perfezionato.");
      
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto non e' perfezionato");
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraUnificatoWS: fine metodo");
    }
      
    return consultaLottoBean;
  }

  public synchronized ConsultaLottoBean consultaGaraSmartCIGUnificatoWS(final ComunicazioneType scheda, final String codUffInt,
	      final int syscon, boolean isCfgVigilanza) 
	          throws SQLException, GestoreException, ParseException {

	    if (logger.isDebugEnabled()) {
	      logger.debug("consultaSmartCIGUnificatoWS: inizio metodo");
	    }

	    ConsultaLottoBean consultaLottoBean = new ConsultaLottoBean(scheda.getCig(), true, false);
	    
	    // Flag che indica se in DB esiste la gara con lo smartCIG specificato dall'utente
	    boolean esisteGara = UtilitySITAT.existsSmartCigSA(scheda.getCig(), codUffInt, this.sqlManager);
	    
	      if (logger.isDebugEnabled()) {
	        logger.debug("Inizio import del xml fornito dal WS dopo controllo su smartCIG e CF stazione appaltante");
	        logger.debug("Nella base dati " + (esisteGara ? "esiste" : "non esiste") + " la gara con smartCIG = '" + scheda.getCig() + "'");
	      }
	      
	      Long codGara = null;
	      if (esisteGara) {
	        codGara = (Long) this.sqlManager.getObject("select CODGARA from W9GARA left join W9LOTT on W9GARA.CODGARA = W9LOTT.CODGARA where W9LOTT.CIG = ? and W9GARA.CODEIN = ?", new Object[] {scheda.getCig(), codUffInt });
	      } else {
	    	  Long maxCodGara = (Long) this.sqlManager.getObject("select max(CODGARA)+1 from W9GARA", new Object[] {});
	          if (maxCodGara == null) {
	            codGara = new Long(1);
	          } else {
	            codGara = maxCodGara;
	          }
	      }
	      consultaLottoBean.setCodgara(codGara);
	      // Estrazione del campo TECNI.CODTEC per valorizzare i campi W9GARA.RUP e W9LOTT.RUP
	      String codTec = "";
	      String cfRup = null;
	      
	      cfRup = (String) sqlManager.getObject("select simoguser from W9LOADER_APPALTO_USR where syscon = ?", new Object[] {syscon});
	      if (cfRup == null) {
	    	  cfRup = (String) sqlManager.getObject("select SYSCF from USRSYS where SYSCON = ?", new Object[] {syscon});
	      }
	      if (cfRup != null && cfRup.length() > 0) {
	    	  codTec = (String) this.sqlManager.getObject("select CODTEC from TECNI where CGENTEI=? and UPPER(CFTEC)=?", 
	    		        new Object[] { codUffInt, cfRup.toUpperCase() });
	    	  if (StringUtils.isEmpty(codTec)) {
	    		  codTec = this.w9Manager.insertTecnico(codUffInt, cfRup.toUpperCase());
	          }
	      } 
	      
	      if (!esisteGara) {
	        
	        if (scheda.getStato().equals("03")) {
		          consultaLottoBean.setCaricato(false);
		          consultaLottoBean.setMsg("Lotto non importato perche' cancellato");
		    } else {
		    	List<String> listaSqlGaraCampi = new ArrayList<String>();
		        listaSqlGaraCampi.add("CODGARA");	
		        listaSqlGaraCampi.add("VER_SIMOG");	
		        listaSqlGaraCampi.add("SITUAZIONE");
		        listaSqlGaraCampi.add("PREV_BANDO");
		        listaSqlGaraCampi.add("PROV_DATO");
		        listaSqlGaraCampi.add("IDAVGARA");
		        listaSqlGaraCampi.add("FLAG_ENTE_SPECIALE");
		        listaSqlGaraCampi.add("TIPO_APP");
		        listaSqlGaraCampi.add("CIG_ACCQUADRO");
		        listaSqlGaraCampi.add("RUP");
		        listaSqlGaraCampi.add("CODEIN");
		        listaSqlGaraCampi.add("FLAG_SA_AGENTE");
		        listaSqlGaraCampi.add("SYSCON");
		        listaSqlGaraCampi.add("OGGETTO");
		        listaSqlGaraCampi.add("IMPORTO_GARA");
		        listaSqlGaraCampi.add("NLOTTI");
		        listaSqlGaraCampi.add("DATA_CREAZIONE");
		        listaSqlGaraCampi.add("DATA_PUBBLICAZIONE");
		        
		        List<Object> listaSqlGaraParams = new ArrayList<Object>();
		        listaSqlGaraParams.add(codGara);		//CODGARA
		        listaSqlGaraParams.add(null);			//*VER_SIMOG
		        listaSqlGaraParams.add(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione()));			//SITUAZIONE
		        listaSqlGaraParams.add("2");			//PREV_BANDO
		        listaSqlGaraParams.add(new Long(4));	//PROV_DATO
		        listaSqlGaraParams.add("0");			//IDAVGARA
		        listaSqlGaraParams.add(null);			//*FLAG_ENTE_SPECIALE
		        listaSqlGaraParams.add(null);			//*TIPO_APP
		        listaSqlGaraParams.add(scheda.getCigAccordoQuadro());	//CIG_ACCQUADRO
		        listaSqlGaraParams.add(codTec);			//RUP
		        listaSqlGaraParams.add(codUffInt);		//CODEIN
		        listaSqlGaraParams.add("2");			//FLAG_SA_AGENTE
		        listaSqlGaraParams.add(syscon);			//SYSCON
		        listaSqlGaraParams.add(scheda.getOggetto());	//OGGETTO
		        listaSqlGaraParams.add(scheda.getImporto().doubleValue());	//IMPORTO_GARA
		        listaSqlGaraParams.add(new Long(1));	//NLOTTI
		        listaSqlGaraParams.add(scheda.getDataOperazione().getTime());	//DATA_CREAZIONE
		        listaSqlGaraParams.add(scheda.getDataOperazione().getTime());	//DATA_PUBBLICAZIONE
		        
		        // Inserimento in W9GARA
		        StringBuffer listaCampi = new StringBuffer("");
		        StringBuffer listaParams = new StringBuffer("");
		        for (int ir = 0; ir < listaSqlGaraCampi.size(); ir++) {
		            listaCampi.append(listaSqlGaraCampi.get(ir) + ", ");
		            listaParams.append("?,");
		        }
		          
		        String sqlInsertW9GARA = "insert into W9GARA (LISTACAMPI) values (LISTAPARAMS)";
		        sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
		        sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 1));
		        this.sqlManager.update(sqlInsertW9GARA, listaSqlGaraParams.toArray());
		      
		        List<String> listaSqlLottoCampi = new ArrayList<String>();
		        List<Object> listaSqlLottoParams = new ArrayList<Object>();
		        
		        listaSqlLottoCampi.add("CODGARA");
		        listaSqlLottoCampi.add("CODLOTT");
		        listaSqlLottoCampi.add("DAEXPORT");
		        listaSqlLottoCampi.add("CIG");
		        listaSqlLottoCampi.add("OGGETTO");
		        listaSqlLottoCampi.add("NLOTTO");
		        listaSqlLottoCampi.add("TIPO_CONTRATTO");
		        listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE");
		        listaSqlLottoCampi.add("ID_MODO_GARA");
		        listaSqlLottoCampi.add("IMPORTO_LOTTO");
		        listaSqlLottoCampi.add("IMPORTO_ATTUAZIONE_SICUREZZA");
		        listaSqlLottoCampi.add("IMPORTO_TOT");
		        listaSqlLottoCampi.add("CUP");
		        listaSqlLottoCampi.add("CUPESENTE");
		        listaSqlLottoCampi.add("DATA_CONSULTA_GARA");
	        	
		        listaSqlLottoParams.add(codGara);				//CODGARA
		        listaSqlLottoParams.add(new Long(1));			//CODLOTT
		        listaSqlLottoParams.add("1");					//DAEXPORT
		        listaSqlLottoParams.add(scheda.getCig());		//CIG
		        listaSqlLottoParams.add(scheda.getOggetto());	//OGGETTO
		        listaSqlLottoParams.add(new Long(1));			//NLOTT
		        listaSqlLottoParams.add(scheda.getCodiceClassificazioneGara());	//TIPO_CONTRATTO
		        listaSqlLottoParams.add(new Long(this.getCodiceSCtoSITAT("SC_W3005", scheda.getCodiceProceduraSceltaContraente())));	//ID_SCELTA_CONTRAENTE
		        listaSqlLottoParams.add(null);					//ID_MODO_GARA
		        listaSqlLottoParams.add(scheda.getImporto().doubleValue());					//IMPORTO_LOTTO
		        listaSqlLottoParams.add(new Double(0));			//IMPORTO_ATTUAZIONE_SICUREZZA
		        listaSqlLottoParams.add(scheda.getImporto().doubleValue());					//IMPORTO_TOT
		        listaSqlLottoParams.add(scheda.getCup());		//CUP
		        listaSqlLottoParams.add((scheda.getCup()!=null && scheda.getCup().length()>0)?"2":"1");		//CUPESENTE
		        listaSqlLottoParams.add(new Timestamp(new GregorianCalendar().getTimeInMillis())); // Data consulta gara
		        
		        // Inserimento W9LOTT
		        String sqlInsertW9LOTT = "insert into W9LOTT (LISTACAMPI) values (LISTAPARAMS)";
		        listaCampi = new StringBuffer("");
		        listaParams = new StringBuffer("");
		        for (int ir = 0; ir < listaSqlLottoCampi.size(); ir++) {
		            listaCampi.append(listaSqlLottoCampi.get(ir) + ", ");
		            listaParams.append("?, ");
		        }
		        
		        sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
		        sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 2));
		        this.sqlManager.update(sqlInsertW9LOTT, listaSqlLottoParams.toArray());
  		  	
	        	consultaLottoBean.setCaricato(true);
		        consultaLottoBean.setMsg("Lotto importato");
		    }
	      } else {
	        // Non e' stato individuato il tecnico con CFTEC = cfRup recuperato in Simog oppure non si e' riusciti a crearlo
	        String msgErrore = "Per la richiesta consultaGara avviata dall'utente SYSCON=" + syscon
	          + " non si e' riusciti ad individuare il tecnico o a creare il tecnico)";
	        
	        logger.error(msgErrore);

	        consultaLottoBean.setCaricato(false);
	        consultaLottoBean.setMsg("Lotto non importato: non e' stato individuato il RUP");
	      }


	    if (logger.isDebugEnabled()) {
	      logger.debug("consultaGaraUnificatoWS: fine metodo");
	    }
	      
	    return consultaLottoBean;
	  }

  /**
   * Ritorna il codice dello smartCIG richiesto tradotto per Sitat.
   * 
   * @param tabellato
   * @param valore
   * @return Ritorna il codice richiesto tradotto per Regione Lombardia
   * @throws SQLException
   */
  public String getCodiceSCtoSITAT(final String tabellato, final String valore) throws SQLException {
    return (String) sqlManager.getObject("select CODAVCP from W9CODICI_AVCP where TABCOD='" + tabellato + "' and CODSITAT=?", 
        new Object[] { valore } );
  }	
  /**
   * @param lottoType
   * @param codGara
   * @param codLott
   * @throws SQLException
   */
  private void istanziaCpvSecondariLotto(final LottoType lottoType,
      Long codGara, Long codLott) throws SQLException {
    if (lottoType.getCPVSecondariaArray() != null && lottoType.getCPVSecondariaArray().length > 0) {
      CPVSecondariaType[] cpvSecondari = lottoType.getCPVSecondariaArray();
      
      Object[] sqlW9CpvParams = new Object[4];
      sqlW9CpvParams[0] = codGara;
      sqlW9CpvParams[1] = codLott; // progressivo per lotto
      for (int cpvSec=0; cpvSec < cpvSecondari.length; cpvSec++) {
        CPVSecondariaType cpvSecondario = cpvSecondari[cpvSec];
        sqlW9CpvParams[2] = new Long((cpvSec + 1)); // progressivo
        sqlW9CpvParams[3] = cpvSecondario.getCODCPVSECONDARIA();
        this.sqlManager.update("insert into W9CPV (CODGARA, CODLOTT, NUM_CPV, CPV) values (?,?,?,?)", 
            sqlW9CpvParams);
      }
    }
  }

  /**
   * @param lottoType
   * @param codGara
   * @param codLott
   * @throws SQLException
   */
  private void istanziaModalitaAcquisFornServLotto(final LottoType lottoType,
      Long codGara, Long codLott) throws SQLException {
    if (lottoType.getTipiAppaltoFornArray() != null && lottoType.getTipiAppaltoFornArray().length > 0) {
      TipiAppaltoType[] arrayAppaltpForn = lottoType.getTipiAppaltoFornArray();
   
      Object[] sqlW9AppaFornParams = new Object[4];
      sqlW9AppaFornParams[0] = codGara;
      sqlW9AppaFornParams[1] = codLott; // progressivo per lotto
    
      for (int i = 0; i < arrayAppaltpForn.length; i++) {
        sqlW9AppaFornParams[2] = new Long((i + 1)); // progressivo
        sqlW9AppaFornParams[3] = new Long(arrayAppaltpForn[i].getIDAPPALTO());
        this.sqlManager.update("insert into W9APPAFORN (CODGARA, CODLOTT, NUM_APPAF, ID_APPALTO) values (?,?,?,?)", 
          sqlW9AppaFornParams);
      }
    }
  }

  /**
   * @param lottoType
   * @param codGara
   * @param codLott
   * @throws SQLException
   */
  private void istanziaTipologieLavoroLotto(final LottoType lottoType,
      Long codGara, Long codLott) throws SQLException {
    if (lottoType.getTipiAppaltoLavArray() != null && lottoType.getTipiAppaltoLavArray().length > 0) {
      TipiAppaltoType[] arrayAppaltoLav = lottoType.getTipiAppaltoLavArray();
   
      Object[] sqlW9AppaLavParams = new Object[4];
      sqlW9AppaLavParams[0] = codGara;
      sqlW9AppaLavParams[1] = codLott; // progressivo per lotto
    
      for (int i = 0; i < arrayAppaltoLav.length; i++) {
        sqlW9AppaLavParams[2] = new Long((i + 1)); // progressivo
        sqlW9AppaLavParams[3] = new Long(arrayAppaltoLav[i].getIDAPPALTO());
        this.sqlManager.update("insert into W9APPALAV (CODGARA, CODLOTT, NUM_APPAL, ID_APPALTO) values (?,?,?,?)",
          sqlW9AppaLavParams);
      }
    }
  }

  /**
   * @param lottoType
   * @param codGara
   * @param codLott
   * @throws SQLException
   */
  private void istanziaCategorieLotto(final LottoType lottoType, Long codGara,
      Long codLott) throws SQLException {
    String categoria;
    if (lottoType.isSetCATEGORIE()) {
      CategLottoType categorieLotto = lottoType.getCATEGORIE();
      if (categorieLotto.getCATEGORIAArray() != null && categorieLotto.getCATEGORIAArray().length > 0) {
        Object[] sqlLottCateParams = new Object[4];
        sqlLottCateParams[0] = codGara;
        sqlLottCateParams[1] = codLott; // progressivo per lotto

        long numCate = 1;
        for (int i = 0; i < categorieLotto.getCATEGORIAArray().length; i++) {
          if (! StringUtils.equals(categorieLotto.getCATEGORIAArray()[i], lottoType.getIDCATEGORIAPREVALENTE())) { 
            sqlLottCateParams[2] = new Long(numCate + i); // progressivo per categoria

            // tabella W9CODICI_AVCP contiene codici per
            // decodificare ID_CATEGORIA_PREVALENTE e CLASCAT
            categoria = (String) sqlManager.getObject(
              "select CODSITAT from W9CODICI_AVCP where CODAVCP = ?  and TABCOD = 'W3z03' ",
              new Object[] { categorieLotto.getCATEGORIAArray()[i] });
            if (categoria == null) {
              categoria = categorieLotto.getCATEGORIAArray()[i];
            }
            sqlLottCateParams[3] = categoria;
            this.sqlManager.update("insert into W9LOTTCATE (CODGARA, CODLOTT, NUM_CATE, CATEGORIA) values (?,?,?,?)", 
              sqlLottCateParams);
          }
        }
      }
    }
  }

	private String verificaNUTS(String codiceNUTS) {
	  String result = null;
	  
	  if ("ITF46".equals(codiceNUTS.toUpperCase())) {
	    result = "ITF41"; 
	  } else if ("ITF47".equals(codiceNUTS.toUpperCase())) {
      result = "ITF42"; 
    } else if ("ITF4C".equals(codiceNUTS.toUpperCase())) {
      result = "ITF45"; 
    } else if (codiceNUTS.toUpperCase().startsWith("ITH")) {
      result = StringUtils.replace(codiceNUTS, "ITD", "ITH");
    } else if (codiceNUTS.toUpperCase().startsWith("ITE")) {
      result = StringUtils.replace(codiceNUTS, "ITE", "ITI");
    }
	  
	  return result;
	}
	
  public synchronized ConsultaLottoBean consultaGaraUnificatoPDD(final String codiceCIG, final String idAvGara, final String codUffInt,
      final int syscon, final SchedaType schedaType, final GaraType garaType, final LottoType lottoType, boolean isCfgVigilanza)
          throws SQLException, GestoreException, ParseException, Exception {

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraUnificatoPDD: inizio metodo");
    }

    ConsultaLottoBean consultaLottoBean = new ConsultaLottoBean(codiceCIG, true, false);
    
    long idGara = garaType.getIDGARA();
    String cfUffInt = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codUffInt });
    String cfAnac = (String) this.sqlManager.getObject("select CFANAC from UFFINT where CODEIN=?", new Object[] { codUffInt });

    if ((StringUtils.isEmpty(idAvGara) || (garaType.getIDGARA() > 0 && ("" + garaType.getIDGARA()).equalsIgnoreCase(idAvGara))) 
        && (garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfUffInt) || garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfAnac))
        && (! garaType.isSetDATACANCELLAZIONEGARA())
        && (! lottoType.isSetDATACANCELLAZIONELOTTO()) 
        && lottoType.isSetDATAPUBBLICAZIONE()) {

      // Flag che indica se in DB esiste la gara con idAvGara specificato dall'utente
      boolean esisteGara = UtilitySITAT.existsGara("" + garaType.getIDGARA(), this.sqlManager);

      if (logger.isDebugEnabled()) {
        logger.debug("Inizio import del xml fornito dal WS dopo controllo su IDAVGARA e CF stazione appaltante");
        logger.debug("Nella base dati " + (esisteGara ? "esiste" : "non esiste") + " la gara con IDAVGARA = '" + idAvGara + "'");
      }

      Long codGara = null;
      if (esisteGara) {
        codGara = (Long) this.sqlManager.getObject("select CODGARA from W9GARA where idAVGARA=?", new Object[] { "" + idGara });
      } else {
        Long maxCodGara = (Long) this.sqlManager.getObject("select max(CODGARA)+1 from W9GARA", new Object[] {});
        if (maxCodGara == null) {
          codGara = new Long(1);
        } else {
          codGara = maxCodGara;
        }
      }
      consultaLottoBean.setCodgara(codGara);
      // Estrazione del campo TECNI.CODTEC per valorizzare i campi W9GARA.RUP e W9LOTT.RUP
      String cfRup = null;
      if (schedaType != null && schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getDatiComuni() != null) {
        cfRup = schedaType.getDatiScheda().getDatiComuni().getCFRUP();
      }
      if (StringUtils.isEmpty(cfRup)) {
        if (garaType != null) {
          cfRup = garaType.getCFUTENTE();
        }
      }
      
      String codTec = (String) this.sqlManager.getObject("select CODTEC from TECNI where CGENTEI=? and UPPER(CFTEC)=?", 
          new Object[] { codUffInt, cfRup.toUpperCase() });
      if (StringUtils.isEmpty(codTec)) {
       	codTec = this.w9Manager.insertTecnico(codUffInt, cfRup.toUpperCase());
      }

      boolean esisteTecnico = StringUtils.isNotEmpty(codTec);

      if (esisteTecnico) {
        
        GregorianCalendar dataCreazioneGara = null;
        if (garaType.isSetDATACREAZIONE()) {
          dataCreazioneGara = new GregorianCalendar(
              garaType.getDATACREAZIONE().get(Calendar.YEAR), 
              garaType.getDATACREAZIONE().get(Calendar.MONTH), 
              garaType.getDATACREAZIONE().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        }
        
        GregorianCalendar dataPerfezionamentoBando = null;
        if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
          dataPerfezionamentoBando = new GregorianCalendar(
              garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.YEAR),
              garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.MONTH),
              garaType.getDATAPERFEZIONAMENTOBANDO().get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        }
		 
        if (!esisteGara) {
        
          List<String> listaSqlGaraCampi = new ArrayList<String>();
          listaSqlGaraCampi.add("CODGARA");
          listaSqlGaraCampi.add("SYSCON");
          listaSqlGaraCampi.add("OGGETTO");
          listaSqlGaraCampi.add("PROV_DATO");
          listaSqlGaraCampi.add("RUP");
          listaSqlGaraCampi.add("IMPORTO_GARA");
          listaSqlGaraCampi.add("NLOTTI");
          listaSqlGaraCampi.add("CODEIN");
          //listaSqlGaraCampi.add("RIC_ALLUV");
          listaSqlGaraCampi.add("SITUAZIONE");
          listaSqlGaraCampi.add("PREV_BANDO");
          listaSqlGaraCampi.add("VER_SIMOG");
          
          List<Object> listaSqlGaraParams = new ArrayList<Object>();
          listaSqlGaraParams.add(codGara);
          listaSqlGaraParams.add(syscon);
          listaSqlGaraParams.add(garaType.getOGGETTO());
          listaSqlGaraParams.add(new Long(2)); // Provenienza Simog
          listaSqlGaraParams.add(codTec);      // RUP
          listaSqlGaraParams.add(new Double(garaType.getIMPORTOGARA().doubleValue()));
          listaSqlGaraParams.add(new Long(1)); // NLOTTI
          listaSqlGaraParams.add(codUffInt);   // CODEIN
          //listaSqlGaraParams.add("2");         // RIC_ALLUV
          listaSqlGaraParams.add(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione()));  // SITUAZIONE
          listaSqlGaraParams.add("2");         // PREV_BANDO
          int versioneSIMOG = UtilitySITAT.getVersioneSimog(dataCreazioneGara, dataPerfezionamentoBando);
          listaSqlGaraParams.add(new Long(versioneSIMOG));  // VER_SIMOG
          
          if (versioneSIMOG >= 5) {
            if (garaType.isSetFLAGSAAGENTEGARA()) {
              listaSqlGaraCampi.add("FLAG_SA_AGENTE");
              listaSqlGaraParams.add(FlagSNType.S.equals(garaType.getFLAGSAAGENTEGARA()) ? "1" : "2");
            }
            if (garaType.isSetDENAMMAGENTEGARA()) {
              listaSqlGaraCampi.add("DENOM_SA_AGENTE");
              listaSqlGaraParams.add(garaType.getDENAMMAGENTEGARA().toString());
            }
            if (garaType.isSetCFAMMAGENTEGARA()) {  
              listaSqlGaraCampi.add("CF_SA_AGENTE");
              listaSqlGaraParams.add(garaType.getCFAMMAGENTEGARA().toString());
            }
            if (garaType.isSetIDFDELEGATE()) {
              listaSqlGaraCampi.add("ID_F_DELEGATE");
              listaSqlGaraParams.add(new Long(garaType.getIDFDELEGATE()));
            }
          }
          
          if (garaType.isSetDURATAACCQUADROCONVENZIONEGARA()) {
            listaSqlGaraCampi.add("DURATA_ACCQUADRO");
            listaSqlGaraParams.add(new Long(garaType.getDURATAACCQUADROCONVENZIONEGARA()));
          } else  if (schedaType.isSetDatiScheda() &&
              schedaType.getDatiScheda().getDatiComuni().isSetDURATAACCQUADROCONVENZIONE()) {
            listaSqlGaraCampi.add("DURATA_ACCQUADRO");
            listaSqlGaraParams.add(new Long(
                schedaType.getDatiScheda().getDatiComuni().getDURATAACCQUADROCONVENZIONE()));
          }
          
          if (garaType.isSetDATAPERFEZIONAMENTOBANDO()) {
            listaSqlGaraCampi.add("DATA_PUBBLICAZIONE");
            listaSqlGaraParams.add(garaType.getDATAPERFEZIONAMENTOBANDO().getTime());
          }
    
          if (garaType.isSetIDGARA()) {
            listaSqlGaraCampi.add("IDAVGARA");
            listaSqlGaraParams.add("" + garaType.getIDGARA());
          }
    
          if (garaType.isSetMODOREALIZZAZIONE()) {
            listaSqlGaraCampi.add("TIPO_APP");
            listaSqlGaraParams.add(new Long(garaType.getMODOREALIZZAZIONE()));
          }
    
          if (garaType.isSetCIGACCQUADRO()) {
            listaSqlGaraCampi.add("CIG_ACCQUADRO");
            listaSqlGaraParams.add(garaType.getCIGACCQUADRO());
          }
    
          if (garaType.isSetMODOINDIZIONE()) {
            listaSqlGaraCampi.add("ID_MODO_INDIZIONE");
            listaSqlGaraParams.add(new Long(garaType.getMODOINDIZIONE()));
          }
    
          if (garaType.isSetTIPOSCHEDA()) {
            listaSqlGaraCampi.add("FLAG_ENTE_SPECIALE");
            listaSqlGaraParams.add(garaType.getTIPOSCHEDA().toString());
          }

          if (garaType.isSetURGENZADL133()) {
            listaSqlGaraCampi.add("SOMMA_URGENZA");
            listaSqlGaraParams.add(FlagSNType.S.equals(garaType.getURGENZADL133()) ? "1" : "2");
          }
          
          if (garaType.isSetDATACREAZIONE()) {
            listaSqlGaraCampi.add("DATA_CREAZIONE");
            listaSqlGaraParams.add(garaType.getDATACREAZIONE().getTime());
          }
          
          String codiceCentroCostoFromSIMOG = garaType.getIDSTAZIONEAPPALTANTE();
          String descrCentroCostoFromSIMOG = garaType.getDENOMSTAZIONEAPPALTANTE();
          
          Long idCentroCosto = (Long) this.sqlManager.getObject(
              "select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)=? and CODEIN=?",
              new Object[] { codiceCentroCostoFromSIMOG.toUpperCase(), codUffInt });
    
          if (idCentroCosto == null) {
            Long maxIdCentroCosto = (Long) this.sqlManager.getObject("select max(IDCENTRO)+1 from CENTRICOSTO", new Object[] {});
            if (maxIdCentroCosto == null) {
              idCentroCosto = new Long(1);
            } else {
              idCentroCosto = maxIdCentroCosto;
            }
    
            this.sqlManager.update("INSERT INTO CENTRICOSTO (IDCENTRO,CODEIN,CODCENTRO,DENOMCENTRO) VALUES (?,?,?,?)",
                new Object[] { idCentroCosto, codUffInt, codiceCentroCostoFromSIMOG, StringUtils.substring(descrCentroCostoFromSIMOG,0,250) });
          } else {
            this.sqlManager.update("UPDATE CENTRICOSTO set DENOMCENTRO=? where IDCENTRO=?", 
              new Object[] { StringUtils.substring(descrCentroCostoFromSIMOG,0,250), idCentroCosto });
          }
    
          listaSqlGaraCampi.add("IDCC");
          listaSqlGaraParams.add(idCentroCosto);
    
          // Inserimento in W9GARA
          StringBuffer listaCampi = new StringBuffer("");
          StringBuffer listaParams = new StringBuffer("");
          for (int ir = 0; ir < listaSqlGaraCampi.size(); ir++) {
            listaCampi.append(listaSqlGaraCampi.get(ir) + ", ");
            listaParams.append("?,");
          }
    
          String sqlInsertW9GARA = "insert into W9GARA (LISTACAMPI) values (LISTAPARAMS)";
          
          sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
          sqlInsertW9GARA = sqlInsertW9GARA.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 1));
          this.sqlManager.update(sqlInsertW9GARA, listaSqlGaraParams.toArray());

          // Pubblicazione
          this.istanziaPubblicazione(schedaType, codGara);
        }

        if (lottoType.isSetDATACANCELLAZIONELOTTO()) {
          consultaLottoBean.setCaricato(false);
          consultaLottoBean.setMsg("Lotto importato perche' cancellato");
        } else {
          Long codLott = (Long) this.sqlManager.getObject(
            "select max(CODLOTT)+1 from W9LOTT where CODGARA=?", new Object[] { codGara });
          if (codLott == null) {
            codLott = new Long(1);
          }
  
          Long numeroLotti = (Long) this.sqlManager.getObject(
            "select count(*) from W9LOTT where CODGARA=?", new Object[] { codGara });
          numeroLotti++;
  
          List<String> listaSqlLottoCampi = new ArrayList<String>();
          listaSqlLottoCampi.add("CODGARA");
          listaSqlLottoCampi.add("CODLOTT");
          listaSqlLottoCampi.add("OGGETTO");
          listaSqlLottoCampi.add("RUP");
          listaSqlLottoCampi.add("IMPORTO_LOTTO");
          listaSqlLottoCampi.add("CPV");
          listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE");
          listaSqlLottoCampi.add("ID_CATEGORIA_PREVALENTE");
          listaSqlLottoCampi.add("NLOTTO");
          listaSqlLottoCampi.add("CIG");
          listaSqlLottoCampi.add("SITUAZIONE");
          listaSqlLottoCampi.add("DATA_CONSULTA_GARA");
          listaSqlLottoCampi.add("EXSOTTOSOGLIA");
  
          List<Object> listaSqlLottoParams = new ArrayList<Object>();
          listaSqlLottoParams.add(codGara);
          listaSqlLottoParams.add(codLott); // progressivo all'interno della gara
          listaSqlLottoParams.add(lottoType.getOGGETTO());
          listaSqlLottoParams.add(codTec); // RUP
  
          if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()
                - new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()).doubleValue()));
          } else {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
          }
          listaSqlLottoParams.add(lottoType.getCPV()); // CPV
          listaSqlLottoParams.add(Long.parseLong(lottoType.getIDSCELTACONTRAENTE())); // Id Scelta contraente
          String categoria = (String) this.sqlManager.getObject(
            "select CODSITAT from W9CODICI_AVCP where CODAVCP = ? and TABCOD = 'W3z03' ",
              new Object[] { lottoType.getIDCATEGORIAPREVALENTE() });
          // se manca corrispondenza inserisco il codice non decodificato
          if (StringUtils.isEmpty(categoria)) {
            categoria = lottoType.getIDCATEGORIAPREVALENTE();
          }
          listaSqlLottoParams.add(categoria); // Id Categoria Prevalente
          
          listaSqlLottoParams.add(codLott); // NLOTTO (uguale a W9LOTT.CODLOTT)
          listaSqlLottoParams.add(codiceCIG); // CIG
          listaSqlLottoParams.add(new Long(SituazioneGaraLotto.SITUAZIONE_IN_GARA.getSituazione())); // Situazione gara
          listaSqlLottoParams.add(new Date()); // Data consulta gara
  
          // ExSottoSoglia: se lotto.Importo < 150k e gara.DataPubblicazione < 29/10/2013
          Double tempImportoLotto = new Double(lottoType.getIMPORTOLOTTO().doubleValue());
          boolean isDataPubbPrecedente =  false;
          boolean isImportoLottoSottoSoglia = false;
          if (tempImportoLotto.compareTo(new Double(150000)) < 0) {
            isImportoLottoSottoSoglia = true;
          }
  
          if (isImportoLottoSottoSoglia && dataPerfezionamentoBando != null) {
            if (dataPerfezionamentoBando.before(new GregorianCalendar(2013, 9, 29, 0, 0, 0))) {
              isDataPubbPrecedente = true;
            }
          }
          
          if (isImportoLottoSottoSoglia && isDataPubbPrecedente) {
            listaSqlLottoParams.add("1");
          } else {
            listaSqlLottoParams.add("2");
          }
          
          if (dataPerfezionamentoBando != null 
              && dataPerfezionamentoBando.before(UtilitySITAT.getDataAttuazioneSimogVer2())) {
            listaSqlLottoCampi.add("ID_SCELTA_CONTRAENTE_50");
            listaSqlLottoParams.add(this.getIdSceltaContraente50(lottoType.getIDSCELTACONTRAENTE()));
          }
          if (garaType.isSetTIPOSCHEDA()) {
            listaSqlLottoCampi.add("FLAG_ENTE_SPECIALE");
            listaSqlLottoParams.add(garaType.getTIPOSCHEDA().toString());
          }
  
          listaSqlLottoCampi.add("DAEXPORT");
          listaSqlLottoParams.add("1");
  
          listaSqlLottoCampi.add("IMPORTO_ATTUAZIONE_SICUREZZA");
          if (lottoType.getIMPORTOATTUAZIONESICUREZZA() != null) {
            listaSqlLottoParams.add(new Double(lottoType.getIMPORTOATTUAZIONESICUREZZA()));
          } else {
            listaSqlLottoParams.add(new Double(0));
          }
  
          listaSqlLottoCampi.add("IMPORTO_TOT");
          listaSqlLottoParams.add(new Double(lottoType.getIMPORTOLOTTO().doubleValue()));
  
          listaSqlLottoCampi.add("CLASCAT");
  
          double importoLotto = lottoType.getIMPORTOLOTTO().doubleValue();
          if (importoLotto <= 258000) {
            listaSqlLottoParams.add("I");
          } else if (importoLotto <= 516000) {
            listaSqlLottoParams.add("II");
          } else if (importoLotto <= 1033000) {
            listaSqlLottoParams.add("III");
          } else if (importoLotto <= 1500000) {
            listaSqlLottoParams.add("IIIB");
          } else if (importoLotto <= 2582000) {
            listaSqlLottoParams.add("IV");
          } else if (importoLotto <= 3500000) {
            listaSqlLottoParams.add("IVB");
          } else if (importoLotto <= 5165000) {
            listaSqlLottoParams.add("V");
          } else if (importoLotto <= 10329000) {
            listaSqlLottoParams.add("VI");
          } else if (importoLotto <= 15494000) {
            listaSqlLottoParams.add("VII");
          } else {
            listaSqlLottoParams.add("VIII");
          }
  
          if (lottoType.isSetTIPOCONTRATTO()) {
            listaSqlLottoCampi.add("TIPO_CONTRATTO");
            listaSqlLottoParams.add(lottoType.getTIPOCONTRATTO().toString());
            listaSqlLottoCampi.add("MANOD");
            if (lottoType.getTIPOCONTRATTO().toString().equalsIgnoreCase(W3Z05Type.L.toString())) {
              listaSqlLottoParams.add("1");
            } else {
              listaSqlLottoParams.add("2");
            }
          } else {
            listaSqlLottoCampi.add("MANOD");
            listaSqlLottoParams.add("2");
          }
  
          listaSqlLottoCampi.add("COMCON");
          listaSqlLottoParams.add("2");
          if (lottoType.isSetLUOGOISTAT()) {
            String luogoIstat = lottoType.getLUOGOISTAT();
  
            if (luogoIstat.length() == 6) {
              String codiceIstatRegione = (String) this.sqlManager.getObject(
                  "select tabcod3 from tabsche where tabcod = 'S2003' and tabcod1='09' and tabcod3 like ?",
                  new Object[] { "%".concat(luogoIstat) });
              if (StringUtils.isNotEmpty(codiceIstatRegione)) {
                luogoIstat = codiceIstatRegione;
              } else {
                luogoIstat = null;
              }
            }
            listaSqlLottoCampi.add("LUOGO_ISTAT");
            listaSqlLottoParams.add(luogoIstat);
          }
          if (lottoType.isSetLUOGONUTS()) {
            listaSqlLottoCampi.add("LUOGO_NUTS");
            String tmp = this.verificaNUTS(lottoType.getLUOGONUTS());
            if (tmp != null) {
              listaSqlLottoParams.add(tmp);
            } else {
              listaSqlLottoParams.add(lottoType.getLUOGONUTS());
            }
          }
          
          // CUI programmazione
          if (lottoType.isSetANNUALECUIMININF()) {
          	listaSqlLottoCampi.add("CUIINT");
          	listaSqlLottoParams.add(lottoType.getANNUALECUIMININF());
          }
          
          // ART_E1
          listaSqlLottoCampi.add("ART_E1");
          int versioneSimog = UtilitySITAT.getVersioneSimog(this.sqlManager, codGara);
          if (versioneSimog <= 2) {
            if (lottoType.isSetIDESCLUSIONE()) {
              String strArtEsclusione = lottoType.getIDESCLUSIONE();
              int artEsclusione = Integer.parseInt(strArtEsclusione);
              if (artEsclusione <= 7 || artEsclusione == 11) {
                listaSqlLottoParams.add("1");
              } else {
                listaSqlLottoParams.add("2");
              }
            } else {
              listaSqlLottoParams.add("2");
            }
          } else {
            listaSqlLottoParams.add("2");
          }

          if (lottoType.isSetCUPLOTTO()) {
            CUPLOTTOType cupLotto = lottoType.getCUPLOTTO();
  
            // Per ora si legge solo il primo codice CUP presente nell'array CODICICUP
            // e si suppone che il CUP sia legato al lotto a cui e' associato
            DatiCUPType datiCup = cupLotto.getCODICICUPArray(0);
  
            listaSqlLottoCampi.add("CUP");
            listaSqlLottoParams.add(datiCup.getCUP());
            listaSqlLottoCampi.add("CUPESENTE");
            listaSqlLottoParams.add("2");
          } else {
            listaSqlLottoCampi.add("CUPESENTE");
            listaSqlLottoParams.add("1");
          }
  
          if (schedaType.isSetDatiScheda()) {
            if (schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDALOCALE()) {
              listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
              listaSqlLottoParams.add(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDALOCALE());
            } else {
              listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
              listaSqlLottoParams.add(codiceCIG);
            }
            
            if (schedaType.getDatiScheda().getDatiComuni().isSetIDSCHEDASIMOG()) {
              listaSqlLottoCampi.add("ID_SCHEDA_SIMOG");
              listaSqlLottoParams.add(schedaType.getDatiScheda().getDatiComuni().getIDSCHEDASIMOG());
            }
          } else {
            listaSqlLottoCampi.add("ID_SCHEDA_LOCALE");
            listaSqlLottoParams.add(codiceCIG);
          }
          
          if (versioneSimog > 2) {
        	  listaSqlLottoCampi.add("CONTRATTO_ESCLUSO_ALLEGGERITO");
        	  if (lottoType.isSetFLAGESCLUSO()) {
        		  if ("S".equals(lottoType.getFLAGESCLUSO())) {
        			  listaSqlLottoParams.add("1");
        		  } else {
        			  listaSqlLottoParams.add("2");
        		  }
        	  } else {
        	    listaSqlLottoParams.add("2");
        	  }
    
        	  listaSqlLottoCampi.add("ESCLUSIONE_REGIME_SPECIALE");
        	  if (lottoType.isSetFLAGREGIME()) {
        		  if (FlagSNType.S.equals(lottoType.getFLAGREGIME())) {
        			  listaSqlLottoParams.add("1");
        		  } else {
        			  listaSqlLottoParams.add("2");
        		  }
        	  } else {
        	    listaSqlLottoParams.add("2");
        	  }
          }
          
          // Inserimento W9LOTT
          String sqlInsertW9LOTT = "insert into W9LOTT (LISTACAMPI) values (LISTAPARAMS)";
          StringBuffer listaCampi = new StringBuffer("");
          StringBuffer listaParams = new StringBuffer("");
          for (int ir = 0; ir < listaSqlLottoCampi.size(); ir++) {
            listaCampi.append(listaSqlLottoCampi.get(ir) + ", ");
            listaParams.append("?, ");
          }
  
          sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
          sqlInsertW9LOTT = sqlInsertW9LOTT.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 2));
          this.sqlManager.update(sqlInsertW9LOTT, listaSqlLottoParams.toArray());

          // Inserimento W9LOTTCATE
          this.istanziaCategorieLotto(lottoType, codGara, codLott);
      
          // inserimento W9APPALAV
          this.istanziaTipologieLavoroLotto(lottoType, codGara, codLott);
      
          // Inserimento W9APPAFORN
          this.istanziaModalitaAcquisFornServLotto(lottoType, codGara, codLott);
      
          // Inserimento W9CPV
          this.istanziaCpvSecondariLotto(lottoType, codGara, codLott);

          if (esisteGara) {
            // Aggiornamento W9GARA
            this.sqlManager.update("update W9GARA set NLOTTI=?, SITUAZIONE=1 where CODGARA=?", 
                new Object[] { numeroLotti, codGara });
          }
          
          if (schedaType.isSetDatiScheda()) {
          	boolean importaSchedeGara = "1".equals(ConfigManager.getValore("it.eldasoft.simog.consultaGara.importaFasi"));
          	
          	SchedaCompletaType[] arraySchedeComplete = schedaType.getDatiScheda().getSchedaCompletaArray();
          	SchedaCompletaType schedaCompleta = arraySchedeComplete[arraySchedeComplete.length-1];
          	
            // inserimento W9ESITO solo in cfg Osservatorio
          	if (!isCfgVigilanza && ("1".equals(garaType.getIDFDELEGATE()) || "2".equals(garaType.getIDFDELEGATE()) || "4".equals(garaType.getIDFDELEGATE()) || importaSchedeGara)) { 
          		this.istanziaEsito(codGara, codLott, schedaType, codUffInt, isCfgVigilanza);
          	}
          	// Inserimento dell'aggiudicazione (W9APPA, W9AGGI, IMPR, IMPLEG, TEIM, W9INCA, W9FINA)
          	if ("1".equals(garaType.getIDFDELEGATE()) || "2".equals(garaType.getIDFDELEGATE()) || 
            			(importaSchedeGara && hasAggiudicazione(schedaCompleta))) {
             	this.istanziaAggiudicazione(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
             			schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
            }
            
            // Importazione delle fasi successive all'aggiudicazione
            if (importaSchedeGara) {

          		if (hasAggiudicazioneSottosoglia(schedaCompleta)) {
          			this.istanziaAggiudicazioneSottosoglia(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAggiudicazioneEsclusa(schedaCompleta)) {
          			this.istanziaAggiudicazioneEsclusa(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAdesione(schedaCompleta)) {
          			this.istanziaAdesioneAccordoQuadro(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, 
          					schedaType.getAggiudicatari().getAggiudicatarioArray(), schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasInizio(schedaCompleta)) {
          			this.istanziaInizio(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasStipula(schedaCompleta)) {
          			this.istanziaStipulaAccordoQuadro(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getResponsabili().getResponsabileArray());
          		}

          		if (hasAvanzamento(schedaCompleta)) {
          			this.istanziaFaseAvanzamento(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasSospensioni(schedaCompleta)) {
          			this.istanziaSospensioni(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasVarianti(schedaCompleta)) {
          			this.istanziaVarianti(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasSubappalti(schedaCompleta)) {
          			this.istanziaSubappalti(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getAggiudicatari().getAggiudicatarioArray(),
          					schedaType.getResponsabili().getResponsabileArray());
          		}
          		
          		if (hasConclusione(schedaCompleta)) {
          			this.istanziaFaseConclusione(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasRitardo(schedaCompleta)) {
          			this.istanziaRitardo(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasAccordoBonario(schedaCompleta)) {
          			this.istanziaAccordBonario(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta);
          		}

          		if (hasCollaudo(schedaCompleta)) {
          			this.istanziaFaseCollaudo(codGara, codLott, codUffInt, isCfgVigilanza, schedaCompleta, schedaType.getResponsabili().getResponsabileArray());
          		}
            }
            
            // Aggiornamento situazione della gara e del lotto
            this.w9Manager.updateSituazioneGaraLotto(codGara, codLott);

          }

          if (this.w9Manager.isStazioneAppaltanteConPermessiDaApplicareInCreazione(codUffInt)) {
            this.w9Manager.updateAttribuzioneModello(codGara, new Long(1));
  
            // Inserimento dei ruolo per il RUP, altrimenti con la gestione dei
            // permessi attiva nessun utente accederebbe alla gara/lotto
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
                new Object[] { codGara, codLott, new Long(1), new Long(1), "RA", 14, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
                new Object[] { codGara, codLott, new Long(1), new Long(2), "RA", 15, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
                new Object[] { codGara, codLott, new Long(1), new Long(3), "RA", 16, codTec });
            this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
                new Object[] { codGara, codLott, new Long(1), new Long(4), "RA", 17, codTec });
          }
          consultaLottoBean.setCaricato(true);
          consultaLottoBean.setMsg("Lotto importato");
        }
        //resultMap.put("esito", Boolean.TRUE);
      } else {
        // Non e' stato individuato il tecnico con CFTEC = cfRup recuperato in Simog oppure non si e' riusciti a crearlo
        String msgErrore = "Per la richiesta consultaGara avviata dall'utente SYSCON=" + syscon
            + " non si e' riusciti ad individuare il tecnico o a creare il tecnico)";

        logger.error(msgErrore);

        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Lotto non importato: non e' stato individuato il RUP");
      }
    } else {

      if (!garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(cfUffInt)) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: la stazione appaltante "
            + "associata al codice CIG e' differente da quella da cui si sta effettuando l’operazione, cioe' il codice fiscale "
            + "della stazione appaltante in uso (UFFINT.CFEIN) e' diverso dal campo CF_AMMINISTRAZIONE ritornato dal WS dell'AVCP.");
        
        consultaLottoBean.setEsistente(false);
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto fa riferimento ad una stazione appaltante diversa da quella attiva");

      } else if (StringUtils.isNotEmpty(idAvGara) && !("" + garaType.getIDGARA()).equalsIgnoreCase(idAvGara)) {
        logger.error("La richiesta consultaGara ha restituito una gara con IDGARA diverso da quello digitato dall'utente");
        
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto fa riferimento ad un Numero Gara ANAC diverso da quello indicato");

      } else if (garaType.isSetDATACANCELLAZIONEGARA()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: " 
            + "il codice CIG indicato fa riferimento ad una gara cancellata.");
        
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Gara cancellata");
        
      } else if (lottoType.isSetDATACANCELLAZIONELOTTO()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: " 
            + "il codice CIG indicato fa riferimento ad un lotto cancellato.");
        
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Lotto cancellato");
        
      } else if (!lottoType.isSetDATAPUBBLICAZIONE()) {
        logger.error("La richiesta consultaGara e' stata interrotta per il seguente motivo: " 
            + "il codice CIG indicato fa riferimento ad un lotto non perfezionato.");
        
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Lotto non perfezionato");
        
      /*} else {
        logger.error("La richiesta consultaGara e' stata interrotta per piu' motivi: al codice CIG e' associata "
            + "una gara con codice gara diverso da quello digitato, la stazione appaltante associata al "
            + "codice CIG e' differente da quella da cui si sta effettuando l’operazione (cioe' il codice fiscale "
            + "della stazione appaltante in uso (UFFINT.CFEIN) e' diverso dal campo CF_AMMINISTRAZIONE ritornato "
            + "dal WS dell'AVCP) e il codice CIG indicato fa riferimento ad un lotto non perfezionato.");
        
        consultaLottoBean.setCaricato(false);
        consultaLottoBean.setMsg("Il lotto non importabile");
        */
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaGaraUnificatoPDD: fine metodo");
    }

    return consultaLottoBean;
  }


	/**
   * @param schedaType
   * @param codGara
   * @throws SQLException
   */
  private void istanziaPubblicazione(final SchedaType schedaType, Long codGara)
      throws SQLException {
    StringBuffer listaCampi;
    StringBuffer listaParams;
    if (schedaType.isSetDatiScheda() && schedaType.getDatiScheda().isSetPubblicazione()) {
      PubblicazioneType pubblicazione = schedaType.getDatiScheda().getPubblicazione();

      List<String> listaSqlPubblicazioneCampi = new ArrayList<String>();
      List<Object> listaSqlPubblicazioneParams = new ArrayList<Object>();

      listaSqlPubblicazioneCampi.add("CODGARA");
      listaSqlPubblicazioneParams.add(codGara);

      listaSqlPubblicazioneCampi.add("CODLOTT");
      listaSqlPubblicazioneParams.add(new Long(1));

      listaSqlPubblicazioneCampi.add("NUM_APPA");
      listaSqlPubblicazioneParams.add(new Long(1));

      listaSqlPubblicazioneCampi.add("NUM_PUBB");
      listaSqlPubblicazioneParams.add(new Long(1));

      if (pubblicazione.getDATAGUCE() != null) {
        listaSqlPubblicazioneCampi.add("DATA_GUCE");
        listaSqlPubblicazioneParams.add(pubblicazione.getDATAGUCE().getTime());
      }

      if (pubblicazione.getDATAGURI() != null) {
        listaSqlPubblicazioneCampi.add("DATA_GURI");
        listaSqlPubblicazioneParams.add(pubblicazione.getDATAGURI().getTime());
      }

      if (pubblicazione.getDATAALBO() != null) {
        listaSqlPubblicazioneCampi.add("DATA_ALBO");
        listaSqlPubblicazioneParams.add(pubblicazione.getDATAALBO().getTime());
      }

      if (pubblicazione.getQUOTIDIANINAZ() > 0) {
        listaSqlPubblicazioneCampi.add("QUOTIDIANI_NAZ");
        listaSqlPubblicazioneParams.add(pubblicazione.getQUOTIDIANINAZ());
      }

      if (pubblicazione.getQUOTIDIANIREG() > 0) {
        listaSqlPubblicazioneCampi.add("QUOTIDIANI_REG");
        listaSqlPubblicazioneParams.add(pubblicazione.getQUOTIDIANIREG());
      }

      if (pubblicazione.getPROFILOCOMMITTENTE() != null) {
        listaSqlPubblicazioneCampi.add("PROFILO_COMMITTENTE");
        listaSqlPubblicazioneParams.add(FlagSNType.S.equals(pubblicazione.getPROFILOCOMMITTENTE()) ? "1" : "2");
      }

      if (pubblicazione.getSITOMINISTEROINFTRASP() != null) {
        listaSqlPubblicazioneCampi.add("SITO_MINISTERO_INF_TRASP");
        listaSqlPubblicazioneParams.add(FlagSNType.S.equals(pubblicazione.getSITOMINISTEROINFTRASP()) ? "1" : "2");
      }

      if (pubblicazione.getSITOOSSERVATORIOCP() != null) {
        listaSqlPubblicazioneCampi.add("SITO_OSSERVATORIO_CP");
        listaSqlPubblicazioneParams.add(FlagSNType.S.equals(pubblicazione.getSITOOSSERVATORIOCP()) ? "1" : "2");
      }

      if (pubblicazione.getDATABORE() != null) {
        listaSqlPubblicazioneCampi.add("DATA_BORE");
        listaSqlPubblicazioneParams.add(pubblicazione.getDATABORE().getTime());
      }

      if (pubblicazione.getPERIODICI() > 0) {
        listaSqlPubblicazioneCampi.add("PERIODICI");
        listaSqlPubblicazioneParams.add(pubblicazione.getPERIODICI());
      }

      // Inserimento W9PUBB
      String sqlInsertW9PUBB = "insert into W9PUBB (LISTACAMPI) values (LISTAPARAMS)";
      listaCampi = new StringBuffer("");
      listaParams = new StringBuffer("");
      for (int i = 0; i < listaSqlPubblicazioneCampi.size(); i++) {
        listaCampi.append(listaSqlPubblicazioneCampi.get(i) + ", ");
        listaParams.append("?, ");
      }

      sqlInsertW9PUBB = sqlInsertW9PUBB.replaceAll("LISTACAMPI", listaCampi.toString().substring(0, listaCampi.length() - 2));
      sqlInsertW9PUBB = sqlInsertW9PUBB.replaceAll("LISTAPARAMS", listaParams.toString().substring(0, listaParams.length() - 2));

      this.sqlManager.update(sqlInsertW9PUBB, listaSqlPubblicazioneParams.toArray());
    }
  }

	private Long getIdSceltaContraente50(String IdSceltaContraente) {
		Long result = null;
		if (IdSceltaContraente != null) {
			if (IdSceltaContraente.equals("1")) {
				return new Long(1);
			} else if (IdSceltaContraente.equals("2")) {
				return new Long(12);
			} else if (IdSceltaContraente.equals("4")) {
				return new Long(6);
			} else if (IdSceltaContraente.equals("6")) {
				return new Long(17);
			} else if (IdSceltaContraente.equals("7")) {
				return null;
			} else if (IdSceltaContraente.equals("8")) {
				return new Long(18);
			} else if (IdSceltaContraente.equals("9")) {
				return new Long(5);
			} else if (IdSceltaContraente.equals("10")) {
				return new Long(6);
			} else if (IdSceltaContraente.equals("11")) {
				return null;
			} else if (IdSceltaContraente.equals("12")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("13")) {
				return new Long(12);
			} else if (IdSceltaContraente.equals("14")) {
				return null;
			} else if (IdSceltaContraente.equals("15")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("16")) {
				return new Long(2);
			} else if (IdSceltaContraente.equals("17")) {
				return new Long(9);
			} else if (IdSceltaContraente.equals("18")) {
				return new Long(10);
			} else if (IdSceltaContraente.equals("19")) {
				return new Long(11);
			} else if (IdSceltaContraente.equals("20")) {
				return new Long(13);
			} else if (IdSceltaContraente.equals("25")) {
				return null;
			} else if (IdSceltaContraente.equals("31")) {
				return new Long(2);
			}
		}
		return result;
	}
  
  /**
   * Update del campo TECNI.SYSCON
   * 
   * @param syscon
   * @param codTec
   * @throws SQLException
   */
  public void updateTecnico(final int syscon, final String codTec) throws SQLException {
    this.sqlManager.update("update TECNI set SYSCON=? where CODTEC=?", new Object[] { new Long(syscon), codTec } );
  }
  
  /**
   * @param codiceCIG
   * @param codUffInt
   * @param schedaType
   * @throws Exception 
   */
  private void istanziaAggiudicazione(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza,
  		final SchedaCompletaType schedaCompletaType, final AggiudicatarioType[] arrayAggiudicatari, final ResponsabileType[] arrayResponsabili)
  				throws Exception {
    
    // Importazione dell'aggiudicazione e delle entità figlie.
    // Insert aggiudicazione (W9APPA, W9AGGI, W9INCA, W9FINA, IMPR, IMPLEG, TEIM)
    
    boolean isS2 = UtilitySITAT.isS2(codGara, codLott, this.sqlManager);
    boolean isAAQ = UtilitySITAT.isAAQ(codGara, this.sqlManager);

    String codiceCUI = schedaCompletaType.getCUI();
    // Aggiudicazione
    AggiudicazioneType aggiudicazioneType = schedaCompletaType.getAggiudicazione();
    AppaltoType appaltoType = aggiudicazioneType.getAppalto();
    
    Long faseEsecuz = null;
    if (isAAQ) {
      faseEsecuz = new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO);
    } else  if (isS2) {
      faseEsecuz = new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA);
    } else {
      faseEsecuz = new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
    }
    
    String idSchedaLocale = null;
    String idSchedaSimog = null;
    if (appaltoType.isSetIDSCHEDALOCALE()) {
    	idSchedaLocale = appaltoType.getIDSCHEDALOCALE();
    }
    if (appaltoType.isSetIDSCHEDASIMOG()) {
    	idSchedaSimog = appaltoType.getIDSCHEDASIMOG();
    }
    
    // Record in W9FASI.
    this.istanziaFaseFlusso(new Long(codGara), new Long(codLott), faseEsecuz, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
    
    DataColumn codGaraAppa = new DataColumn("W9APPA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
    DataColumn codLottAppa = new DataColumn("W9APPA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));
    DataColumn numAppa = new DataColumn("W9APPA.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
    DataColumn codCui = new DataColumn("W9APPA.CODCUI",    new JdbcParametro(JdbcParametro.TIPO_TESTO, codiceCUI));
    DataColumn flagAccordoQuadro = null;
    if (appaltoType.isSetFLAGACCORDOQUADRO()) {
      flagAccordoQuadro = new DataColumn("W9APPA.FLAG_ACCORDO_QUADRO", new JdbcParametro(JdbcParametro.TIPO_TESTO,
              FlagSNType.S.equals(appaltoType.getFLAGACCORDOQUADRO()) ? "1" : "2"));
    } else {
      flagAccordoQuadro = new DataColumn("W9APPA.FLAG_ACCORDO_QUADRO", new JdbcParametro(JdbcParametro.TIPO_TESTO, "2"));
    }
    
    DataColumnContainer dccAggiudicazione = new DataColumnContainer(new DataColumn[] {
        codGaraAppa, codLottAppa, numAppa, codCui, flagAccordoQuadro } );
 
    if (appaltoType.getPROCEDURAACC() != null ) {
      dccAggiudicazione.addColumn("W9APPA.PROCEDURA_ACC", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          FlagSNType.S.equals(appaltoType.getPROCEDURAACC()) ? "1" : "2"));
    }
    if (appaltoType.getPREINFORMAZIONE() != null) {
      dccAggiudicazione.addColumn("W9APPA.PREINFORMAZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          FlagSNType.S.equals(appaltoType.getPREINFORMAZIONE()) ? "1" : "2"));
    }
    if (appaltoType.getTERMINERIDOTTO() != null) {
      dccAggiudicazione.addColumn("W9APPA.TERMINE_RIDOTTO", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          FlagSNType.S.equals(appaltoType.getTERMINERIDOTTO()) ? "1" : "2"));
    }
    if (appaltoType.isSetIDMODOINDIZIONE() ) {
      dccAggiudicazione.addColumn("W9APPA.ID_MODO_INDIZIONE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 
          new Long(appaltoType.getIDMODOINDIZIONE().toString())));
    }
    dccAggiudicazione.addColumn("W9APPA.NUM_IMPRESE_INVITATE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMIMPRESEINVITATE())));
 
    dccAggiudicazione.addColumn("W9APPA.NUM_OFFERTE_AMMESSE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMOFFERTEAMMESSE())));
    
    if (appaltoType.isSetNUMIMPRESERICHIEDENTI()) {
      dccAggiudicazione.addColumn("W9APPA.NUM_IMPRESE_RICHIEDENTI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
          new Long(appaltoType.getNUMIMPRESERICHIEDENTI())));
    }
    dccAggiudicazione.addColumn("W9APPA.NUM_IMPRESE_OFFERENTI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMIMPRESEOFFERENTI())));
    dccAggiudicazione.addColumn("W9APPA.DATA_VERB_AGGIUDICAZIONE", new JdbcParametro(JdbcParametro.TIPO_DATA,
        appaltoType.getDATAVERBAGGIUDICAZIONE().getTime()));
 
    if (appaltoType.isSetDATASCADENZARICHIESTAINVITO()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
      sdf.parse(appaltoType.getDATASCADENZARICHIESTAINVITO());
      dccAggiudicazione.addColumn("W9APPA.DATA_SCADENZA_RICHIESTA_INVITO", new JdbcParametro(JdbcParametro.TIPO_DATA,
          sdf.getCalendar().getTime()));
    }
    if (appaltoType.isSetDATASCADENZAPRESOFFERTA()) {
      dccAggiudicazione.addColumn("W9APPA.DATA_SCADENZA_PRES_OFFERTA", new JdbcParametro(JdbcParametro.TIPO_DATA,
          appaltoType.getDATASCADENZAPRESOFFERTA().getTime()));
    }
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_AGGIUDICAZIONE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoType.getIMPORTOAGGIUDICAZIONE())));
    if (StringUtils.isNotEmpty(appaltoType.getASTAELETTRONICA())) {       
      dccAggiudicazione.addColumn("W9APPA.ASTA_ELETTRONICA", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          "S".equals(appaltoType.getASTAELETTRONICA()) ? "1" : "2"));
    }
    if (appaltoType.isSetPERCRIBASSOAGG() && new Float(appaltoType.getPERCRIBASSOAGG()).doubleValue() > 0) {
      dccAggiudicazione.addColumn("W9APPA.PERC_RIBASSO_AGG", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(appaltoType.getPERCRIBASSOAGG()).toString())));
    }
    if (appaltoType.isSetPERCOFFAUMENTO() && new Float(appaltoType.getPERCOFFAUMENTO()).doubleValue() > 0) {
      dccAggiudicazione.addColumn("W9APPA.PERC_OFF_AUMENTO", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(appaltoType.getPERCOFFAUMENTO()).toString())));
    }
    if (appaltoType.isSetDATAINVITO()) {
      dccAggiudicazione.addColumn("W9APPA.DATA_INVITO", new JdbcParametro(JdbcParametro.TIPO_DATA, 
          appaltoType.getDATAINVITO().getTime()));
    }
    if (StringUtils.isNotEmpty(appaltoType.getFLAGRICHSUBAPPALTO())) {
      dccAggiudicazione.addColumn("W9APPA.FLAG_RICH_SUBAPPALTO", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          "S".equals(appaltoType.getFLAGRICHSUBAPPALTO()) ? "1" : "2"));
    }
    dccAggiudicazione.addColumn("W9APPA.NUM_OFFERTE_ESCLUSE", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMOFFERTEESCLUSE())));
    if (appaltoType.isSetOFFERTAMASSIMO()) {
      dccAggiudicazione.addColumn("W9APPA.OFFERTA_MASSIMO", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, 
          appaltoType.getOFFERTAMASSIMO().doubleValue()));
    }
    if (appaltoType.isSetOFFERTAMINIMA()) {
      dccAggiudicazione.addColumn("W9APPA.OFFERTA_MINIMA", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          appaltoType.getOFFERTAMINIMA().doubleValue()));
    }
    if (appaltoType.isSetVALSOGLIAANOMALIA()) {
      dccAggiudicazione.addColumn("W9APPA.VAL_SOGLIA_ANOMALIA", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          appaltoType.getVALSOGLIAANOMALIA().doubleValue()));
    }
    dccAggiudicazione.addColumn("W9APPA.NUM_OFFERTE_FUORI_SOGLIA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMOFFERTEFUORISOGLIA())));
    dccAggiudicazione.addColumn("W9APPA.NUM_IMP_ESCL_INSUF_GIUST", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
        new Long(appaltoType.getNUMIMPESCLINSUFGIUST())));
  
    double importoLavori = 0, importoForniture = 0, importoServizi = 0, importoProgettazione = 0, importoIva = 0;
    double importoSubTotale = 0, importoAltreSomme = 0, importoAttuazioneSicurezza = 0,  importoNonSoggettoARibasso = 0;
    
    importoLavori = new Double(appaltoType.getIMPORTOLAVORI()).doubleValue();
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_LAVORI", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(appaltoType.getIMPORTOLAVORI())));
    importoServizi = new Double(appaltoType.getIMPORTOSERVIZI()).doubleValue(); 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_SERVIZI", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoType.getIMPORTOSERVIZI())));
    importoForniture = new Double(appaltoType.getIMPORTOFORNITURE()).doubleValue(); 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_FORNITURE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoType.getIMPORTOFORNITURE())));
    importoSubTotale = importoLavori + importoForniture + importoServizi;
    
    importoProgettazione = new Double(appaltoType.getIMPORTOPROGETTAZIONE()).doubleValue(); 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_PROGETTAZIONE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoType.getIMPORTOPROGETTAZIONE())));
    
    /*if (appaltoType.isSetW9APIMPIVA()) {
      importoIva = appaltoType.getW9APIMPIVA();
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_IVA", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          appaltoType.getIVA()));
    }
    
    if (appaltoType.isSetW9APIVA()) {
      dccAggiudicazione.addColumn("W9APPA.IVA", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(appaltoType.getW9APIVA()).toString())));
    }*/
    
    if (appaltoType.isSetIMPNONASSOG()) {
      importoNonSoggettoARibasso = new Double(appaltoType.getIMPNONASSOG()).doubleValue();
      dccAggiudicazione.addColumn("W9APPA.IMP_NON_ASSOG", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(appaltoType.getIMPNONASSOG())));
    }
    
    if (importoSubTotale > 0) {
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_SUBTOTALE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, importoSubTotale));
    } else {
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_SUBTOTALE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, new Double(0)));
    }
    
    importoAttuazioneSicurezza = new Double(appaltoType.getIMPORTOATTUAZIONESICUREZZA()).doubleValue();
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA", 
          new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
              new Double(appaltoType.getIMPORTOATTUAZIONESICUREZZA())));
    
    if (StringUtils.isNotEmpty(appaltoType.getIMPORTODISPOSIZIONE())) {
    	importoAltreSomme = new Double(appaltoType.getIMPORTODISPOSIZIONE()).doubleValue();
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_DISPOSIZIONE", new JdbcParametro(
          JdbcParametro.TIPO_DECIMALE, new Double(appaltoType.getIMPORTODISPOSIZIONE())));
      dccAggiudicazione.addColumn("W9APPA.ALTRE_SOMME", new JdbcParametro(
          JdbcParametro.TIPO_DECIMALE, new Double(appaltoType.getIMPORTODISPOSIZIONE())));
    } else {
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_DISPOSIZIONE", new JdbcParametro(
          JdbcParametro.TIPO_DECIMALE, importoIva + importoAltreSomme));
    }
 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_COMPL_APPALTO", new JdbcParametro(
        JdbcParametro.TIPO_DECIMALE, importoSubTotale + importoAttuazioneSicurezza 
            + importoProgettazione + importoNonSoggettoARibasso));
    
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO", new JdbcParametro(
        JdbcParametro.TIPO_DECIMALE, importoSubTotale + importoAttuazioneSicurezza 
        + importoProgettazione + importoNonSoggettoARibasso + importoIva + importoAltreSomme));
    
    if (appaltoType.isSetOPEREURBANIZSCOMPUTO()) {
      dccAggiudicazione.addColumn("W9APPA.OPERE_URBANIZ_SCOMPUTO", new JdbcParametro(
          JdbcParametro.TIPO_TESTO,FlagSNType.S.equals(appaltoType.getOPEREURBANIZSCOMPUTO()) ? "1" : "2"));
    }

    if (appaltoType.isSetCODSTRUMENTO()){
      dccAggiudicazione.addColumn("W9APPA.COD_STRUMENTO", new JdbcParametro(
            JdbcParametro.TIPO_TESTO, appaltoType.getCODSTRUMENTO()));
    }
 
    /*if (appaltoType.isSetW9APTIPAT()) {
      dccAggiudicazione.addColumn("W9APPA.TIPO_ATTO", new JdbcParametro(
          JdbcParametro.TIPO_NUMERICO, new Long(appaltoType.get IPAT().toString())));
    }    
    if (appaltoType.isSetW9APDATAT()) {
      dccAggiudicazione.addColumn("W9APPA.DATA_ATTO", new JdbcParametro(
          JdbcParametro.TIPO_DATA, appaltoType.getW9APDATAT().getTime()));
    }
    if (appaltoType.isSetW9APNUMAT()) {
      dccAggiudicazione.addColumn("W9APPA.NUMERO_ATTO", new JdbcParametro(
          JdbcParametro.TIPO_TESTO, appaltoType.getW9APNUMAT()));
    }*/
    /*if (appaltoType.isSetW9APDATASTI()) {
    dccAggiudicazione.addColumn("W9APPA.DATA_STIPULA", new JdbcParametro(
        JdbcParametro.TIPO_DATA, appaltoType.getDATASTI().getTime()));
    }*/
    /*if (appaltoType.isSetW9APDURCON()) {
      dccAggiudicazione.addColumn("W9APPA.DURATA_CON", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, new Long(appaltoType.getW9APDURCON())));
    }*/

    if (appaltoType.isSetDATAMANIFINTERESSE()) {
      dccAggiudicazione.addColumn("W9APPA.DATA_MANIF_INTERESSE", new JdbcParametro(
          JdbcParametro.TIPO_DATA, appaltoType.getDATAMANIFINTERESSE().getTime()));
    }
    dccAggiudicazione.addColumn("W9APPA.NUM_MANIF_INTERESSE", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, new Long(appaltoType.getNUMMANIFINTERESSE())));
    
     
    // Inserimento dell'aggiudicazione in W9APPA
    dccAggiudicazione.insert("W9APPA", this.sqlManager);
 
    String updateW9LOTT = "update W9LOTT set ID_TIPO_PRESTAZIONE=?, CODCUI=? REPLACE where CODGARA=? and CODLOTT=?";
    Object[] paramUpdateW9LOTT = null;
    if (appaltoType.isSetIDMODOGARA()) {
      updateW9LOTT = StringUtils.replace(updateW9LOTT, "REPLACE", ", ID_MODO_GARA=? ");
      appaltoType.getIDTIPOPRESTAZIONE();
      paramUpdateW9LOTT = new Object [] { new Long(appaltoType.getIDTIPOPRESTAZIONE()), codiceCUI,
          new Long(appaltoType.getIDMODOGARA()), codGara, codLott }; 
    } else {
      updateW9LOTT = StringUtils.replace(updateW9LOTT, "REPLACE", "");
      paramUpdateW9LOTT = new Object [] { new Long(appaltoType.getIDTIPOPRESTAZIONE()), codiceCUI,
          codGara, codLott };
    }
    this.sqlManager.update(updateW9LOTT, paramUpdateW9LOTT);
    
    if (schedaCompletaType.getAggiudicazione().getCondizioniArray() != null && schedaCompletaType.getAggiudicazione().getCondizioniArray().length > 0) {
    	// Inserimento delle condizioni (W9COND)
    	this.istanziaCondizioni(codGara, codLott, schedaCompletaType.getAggiudicazione().getCondizioniArray());
    }
    
    this.w9Manager.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(new Long(codGara), new Long(codLott),
        new Long(numAppa.getValue().getStringValue()), codUffInt, aggiudicazioneType.getAggiudicatariArray(),
        aggiudicazioneType.getDitteAusiliarieArray(), arrayAggiudicatari);

    String codFiscaleStazAppaltante = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", 
        new Object[] { codUffInt } );

    IncaricatoType[] arrayIncarichiProfessionali = aggiudicazioneType.getIncaricatiArray();
    this.istanziaIncarichiProfessionali(new Long(codGara), new Long(codLott),
        new Long(numAppa.getValue().getStringValue()), codUffInt,
        arrayIncarichiProfessionali, arrayResponsabili);
    
    this.istanziaFinanziamenti(codFiscaleStazAppaltante, aggiudicazioneType.getFinanziamentiArray(),
        codGara, codLott, (Long) numAppa.getValue().getValue(), false);
 
    Long numeroFinanziamentiRegionali = (Long) this.sqlManager.getObject(
        "select count(*) from W9FINA where CODGARA=? and CODLOTT=? and NUM_APPA=1 and ID_FINANZIAMENTO='C03'",
        new Object[] { codGara, codLott });
    if (numeroFinanziamentiRegionali > 0) {
      this.sqlManager.update("update W9APPA set FIN_REGIONALE='1' where CODGARA=? and CODLOTT=? and NUM_APPA=1", new Object[] { codGara, codLott });
    } else {
      this.sqlManager.update("update W9APPA set FIN_REGIONALE='2' where CODGARA=? and CODLOTT=? and NUM_APPA=1", new Object[] { codGara, codLott });
    }
    
  }


  private void istanziaCondizioni(final long codGara, final long codLott, final CondizioneType[] arraycondizioni) throws SQLException {
		String sqlInsertW9COND = "insert into W9COND (codgara,codlott,num_cond,id_condizione) values (?,?,?,?)";
		for (int j=0; j < arraycondizioni.length; j++) {
			Object[] paramSqlW9COND = new Object[4];
			paramSqlW9COND[0] = new Long(codGara);
			paramSqlW9COND[1] = new Long(codLott);
			paramSqlW9COND[2] = new Long(arraycondizioni[j].getIDCONDIZIONE());
			paramSqlW9COND[3] = new Long(arraycondizioni[j].getIDCONDIZIONE());
			this.sqlManager.update(sqlInsertW9COND, paramSqlW9COND);
		}
	}

  /**
   * @param codGara
   * @param codLott
   * @param schedaType
   * @throws Exception 
   */
  private void istanziaEsito(final long codGara, final long codLott, final SchedaType schedaType, final String codUffInt, final boolean isCfgVigilanza) throws Exception {
  	if (schedaType.getDatiScheda().getDatiComuni().isSetESITOPROCEDURA()) {
      DatiComuniType datiComuni = schedaType.getDatiScheda().getDatiComuni();
      String esitoProcedura = null;
      try {
        esitoProcedura = schedaType.getDatiScheda().getDatiComuni().getESITOPROCEDURA();
      } catch (org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException xvore) {
        logger.error("Errore nella lettura del ESITO_PROCEDURA nei DatiComuni ricevuti da SIMOG e questo e' l'XML ricevuto ("
            + schedaType.getDatiScheda().getDatiComuni().toString() + " )", xvore);
        esitoProcedura = null;
      }
      
      if (esitoProcedura == null) {
        String xmlDatiComuni = schedaType.getDatiScheda().getDatiComuni().toString();
        if (xmlDatiComuni.indexOf("ESITO_PROCEDURA") >= 0) {
          int inizio = xmlDatiComuni.indexOf("ESITO_PROCEDURA");
          int fine = 17;
          
          String s1 = xmlDatiComuni.substring(inizio+fine);
          int primoApiceSpazio = s1.indexOf("\" ");
          esitoProcedura = s1.substring(0, primoApiceSpazio);
        }
      }
      
      String idSchedaLocale = null;
      String idSchedaSimog = null;
      if (datiComuni.isSetIDSCHEDALOCALE()) {
      	idSchedaLocale = datiComuni.getIDSCHEDALOCALE();
      }
      if (datiComuni.isSetIDSCHEDASIMOG()) {
      	idSchedaSimog = datiComuni.getIDSCHEDASIMOG();
      }

      if (!isCfgVigilanza || (isCfgVigilanza && !StringUtils.equals("1", esitoProcedura))) {
      	this.sqlManager.update("insert into W9ESITO (CODGARA, CODLOTT, ESITO_PROCEDURA) values(?,?,?)",
      			new Object[] { codGara, codLott, new Long(esitoProcedura) } );
      	this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.COMUNICAZIONE_ESITO), new Long(1), new Long(1), idSchedaLocale, 
      			idSchedaSimog, codUffInt, isCfgVigilanza);
      }
    }
  }

  /**
   * Finanziamenti nell'aggiudicazione
   *  
   * @param codFiscaleStazAppaltante
   * @param oggettoLottoAggiudicazione
   * @param codiceGara
   * @param codiceLotto
   * @param numAppa
   * @param esisteFaseAggiudicazione
   * @throws SQLException
   * @throws GestoreException
   */
  private void istanziaFinanziamenti(String codFiscaleStazAppaltante, FinanziamentoType[] oggettoLottoAggiudicazione, 
      Long codiceGara, Long codiceLotto, Long numAppa, boolean esisteFaseAggiudicazione)
          throws SQLException, GestoreException {
    
    if (oggettoLottoAggiudicazione != null && oggettoLottoAggiudicazione.length > 0) {
      if (esisteFaseAggiudicazione) {
        this.sqlManager.update("delete from W9FINA where CODGARA=? and CODLOTT=? and NUM_APPA=?",
            new Object[] {codiceGara, codiceLotto, new Long(1)} ) ;
      }
    
      for (int nFinanziamento = 0; nFinanziamento < oggettoLottoAggiudicazione.length; nFinanziamento++) {
        FinanziamentoType finanziamento = oggettoLottoAggiudicazione[nFinanziamento];
        
        // codgara, codlott, numappa
        DataColumn codGaraFina = new DataColumn("W9FINA.CODGARA", new JdbcParametro(
              JdbcParametro.TIPO_NUMERICO, codiceGara));
        DataColumn codLottFina = new DataColumn("W9FINA.CODLOTT", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, codiceLotto));
        DataColumn numAppaFina = new DataColumn("W9FINA.NUM_APPA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, numAppa));
        
        //progressivo numero finanziamento 
        DataColumn numFinanziamenti = new DataColumn("W9FINA.NUM_FINA", new JdbcParametro(
            JdbcParametro.TIPO_NUMERICO, new Long(nFinanziamento+1)));
        
        DataColumnContainer dccFina = new DataColumnContainer(new DataColumn[] {
              codGaraFina, codLottFina, numAppaFina, numFinanziamenti} );
        
        // id finanziamento - campo obbligatorio
        dccFina.addColumn("W9FINA.ID_FINANZIAMENTO", new JdbcParametro(
            JdbcParametro.TIPO_TESTO, finanziamento.getIDFINANZIAMENTO()));
        
        // importo finanziamento
        dccFina.addColumn("W9FINA.IMPORTO_FINANZIAMENTO", new JdbcParametro(
          JdbcParametro.TIPO_DECIMALE, finanziamento.getIMPORTOFINANZIAMENTO().doubleValue()));
      
        // Inserimento record in W9FINA
        dccFina.insert("W9FINA", this.sqlManager);
          
      }
    }
  }

  /**
   * Inserimento del record in W9FASI per ogni flusso
   * (esempio: esito ).
   * 
   * @param sqlManager SqlManager
   * @param codiceGara Codice della gara
   * @param codiceLotto Codice del lotto
   * @param numeroFase Numero della fase
   * @param faseEsecuz Fase esecuzione
   * @param numAppa Numero appalto
   * @throws Exception 
   */
  private void istanziaFaseFlusso(Long codiceGara, Long codiceLotto, Long faseEsecuz, Long numeroFase, Long numAppa, 
  		String idSchedaLocale, String idSchedaSimog, String codUffInt, boolean isCfgVigilanza) throws Exception {

    // Creazione del record di W9FASI.
    DataColumn codiceGaraFasi = new DataColumn("W9FASI.CODGARA",
        new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceGara));
    DataColumn codiceLottoFasi = new DataColumn("W9FASI.CODLOTT", 
        new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceLotto));
    DataColumn faseEsecuzione = new DataColumn("W9FASI.FASE_ESECUZIONE", 
          new JdbcParametro(JdbcParametro.TIPO_NUMERICO, faseEsecuz));
    DataColumn w9FasiNum = new DataColumn("W9FASI.NUM", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, numeroFase));
    DataColumn w9NumAppa = new DataColumn("W9FASI.NUM_APPA", new JdbcParametro(
        JdbcParametro.TIPO_NUMERICO, numAppa));
    
    codiceGaraFasi.setChiave(true);
    codiceLottoFasi.setChiave(true);
    faseEsecuzione.setChiave(true);
    w9FasiNum.setChiave(true);
    
    DataColumn w9FasiIdSchedaLocale = null;
    DataColumn w9FasiIdSchedaSimog = null;
    DataColumn w9FasiDaExport = null;
    DataColumnContainer dccFasi = null;
    
    if (isCfgVigilanza) {
    	w9FasiDaExport = new DataColumn("W9FASI.DAEXPORT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(2)));
    } else {
    	w9FasiDaExport = new DataColumn("W9FASI.DAEXPORT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
    }
    
    if (StringUtils.isNotEmpty(idSchedaLocale)) {
    	w9FasiIdSchedaLocale = new DataColumn("W9FASI.ID_SCHEDA_LOCALE", new JdbcParametro(JdbcParametro.TIPO_TESTO, idSchedaLocale));
    } else {
    	String cig = (String) this.sqlManager.getObject("select cig from w9lott where codlott=? and codgara=?", 
      		new Object[]{codiceLotto,codiceGara});
      String strIdSchedaLocale =  cig.concat("_").concat(UtilityStringhe.fillLeft(faseEsecuz.toString(), '0', 3)).concat("_").concat(UtilityStringhe.fillLeft(numeroFase.toString(), '0', 3));
      w9FasiIdSchedaLocale = new DataColumn("W9FASI.ID_SCHEDA_LOCALE", new JdbcParametro(JdbcParametro.TIPO_TESTO, strIdSchedaLocale));
    }
    
    if (StringUtils.isNotEmpty(idSchedaSimog)) {
    	w9FasiIdSchedaSimog = new DataColumn("W9FASI.ID_SCHEDA_SIMOG", new JdbcParametro(JdbcParametro.TIPO_TESTO, idSchedaSimog));
    	dccFasi = new DataColumnContainer(
          new DataColumn[] {codiceGaraFasi, codiceLottoFasi, faseEsecuzione, w9FasiNum, w9FasiIdSchedaLocale, w9FasiDaExport, w9NumAppa, w9FasiIdSchedaSimog });
    } else {
    	dccFasi = new DataColumnContainer(
          new DataColumn[] {codiceGaraFasi, codiceLottoFasi, faseEsecuzione, w9FasiNum, w9FasiIdSchedaLocale, w9FasiDaExport, w9NumAppa});
    }

    // Inserimento in W9FASI
    dccFasi.insert("W9FASI", this.sqlManager);
    
    if (isCfgVigilanza) {
    	int idFlusso = this.genChiaviManager.getNextId("W9FLUSSI");
    	String cfsa = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codUffInt });
    	String cig = UtilitySITAT.getCIGLotto(codiceGara, codiceLotto, this.sqlManager);

    	this.sqlManager.update("Insert into W9FLUSSI (IDFLUSSO,AREA,KEY01,KEY02,KEY03,KEY04,TINVIO2,CFSA,XML,CODOGG) values (?,?,?,?,?,?,?,?,?,?)", 
    			new Object[] { new Long(idFlusso), new Long(1), codiceGara, codiceLotto, faseEsecuz, numeroFase, new Long(1), cfsa, "<void>", cig });
    	
    	Long nextNumXml= (Long) this.sqlManager.getObject("select coalesce(max(NUMXML),0) + 1 from W9XML where CODGARA=? and CODLOTT=?", 
    			new Object[] { codiceGara, codiceLotto });
    	
    	this.sqlManager.update("insert into W9XML (CODGARA,CODLOTT,NUMXML,NUM_ELABORATE,NUM_ERRORE,NUM_WARNING,NUM_CARICATE,IDFLUSSO,FASE_ESECUZIONE,NUM,XML) values (?,?,?,?,?,?,?,?,?,?,?)", 
    			new Object[] { codiceGara,codiceLotto,nextNumXml,new Long(1),new Long(0),new Long(0),new Long(1),new Long(idFlusso),faseEsecuz,numeroFase,"<void>" } );
    }
  }
  
  private void istanziaIncarichiProfessionali(Long codiceGara, Long codiceLotto, Long numAppa, 
      String codeinStazAppaltante, IncaricatoType[] arrayIncarichiProfessionali,
      ResponsabileType[] arrayResponsabili) 
          throws SQLException, GestoreException {
    
    if (arrayIncarichiProfessionali != null && arrayIncarichiProfessionali.length > 0 &&
        arrayResponsabili != null && arrayResponsabili.length > 0) {

        for (int numIncarico = 0; numIncarico < arrayIncarichiProfessionali.length; numIncarico++) {
          IncaricatoType incaricoProfessionale = arrayIncarichiProfessionali[numIncarico];

          DataColumn codGaraInca = new DataColumn("W9INCA.CODGARA", 
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceGara));
          DataColumn codLottInca = new DataColumn("W9INCA.CODLOTT", 
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceLotto));
          DataColumn numAppaInca = new DataColumn("W9INCA.NUM", 
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, numAppa));
          DataColumn numInca = new DataColumn("W9INCA.NUM_INCA", 
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numIncarico+1)));
          DataColumn sezioneInca = new DataColumn("W9INCA.SEZIONE", 
              new JdbcParametro(JdbcParametro.TIPO_TESTO, incaricoProfessionale.getSEZIONE().toString()));
          DataColumn idRuoloInca = new DataColumn("W9INCA.ID_RUOLO",
              new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
              Long.parseLong(incaricoProfessionale.getIDRUOLO())));
          
          DataColumnContainer dccInca = new DataColumnContainer(new DataColumn[] {
              codGaraInca, codLottInca, numAppaInca, numInca, sezioneInca, idRuoloInca} );
          
          if (incaricoProfessionale.isSetCIGPROGESTERNA()) {
            dccInca.addColumn("W9INCA.CIG_PROG_ESTERNA", JdbcParametro.TIPO_TESTO, 
                incaricoProfessionale.getCIGPROGESTERNA());
          }
          if (incaricoProfessionale.isSetDATAAFFPROGESTERNA()) {
            dccInca.addColumn("W9INCA.DATA_AFF_PROG_ESTERNA", JdbcParametro.TIPO_DATA, 
                incaricoProfessionale.getDATAAFFPROGESTERNA().getTime());
          }
          if (incaricoProfessionale.isSetDATACONSPROGESTERNA()) {
            dccInca.addColumn("W9INCA.DATA_CONS_PROG_ESTERNA", JdbcParametro.TIPO_DATA, 
                incaricoProfessionale.getDATACONSPROGESTERNA().getTime());
          }
      
          String cfTec = incaricoProfessionale.getCODICEFISCALERESPONSABILE();
          String codiceTecnico = null;
          ResponsabileType responsabile = null;
          boolean trovatoResponsabile = false;
          if (arrayResponsabili != null) {
            for (int d=0; d < arrayResponsabili.length && !trovatoResponsabile; d++) {
              if (StringUtils.equalsIgnoreCase(cfTec, arrayResponsabili[d].getCODICEFISCALERESPONSABILE())) {
                trovatoResponsabile = true;
                responsabile = arrayResponsabili[d];
              }
            }
            codiceTecnico = this.getTecnico(codeinStazAppaltante, responsabile);
          }

          if (StringUtils.isNotEmpty(codiceTecnico)) {
            dccInca.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, codiceTecnico);
          } else {
            throw new GestoreException("Errore nel determinare il codice del tecnico incaricato", null);
          }

          dccInca.insert("W9INCA", sqlManager);
        }

    } else {
      // Se l'array arrayIncarichiProfessionali e' arrivato vuoto, allora si cancellano
      // i record esistenti nella tabella W9INCA filtrati per sezione
      
      sqlManager.update(
          "delete from W9INCA where CODGARA=? and CODLOTT=? and NUM=? and SEZIONE in ('PA', 'RS', 'RA', 'RE', 'RQ')",
              new Object[] { codiceGara, codiceLotto, numAppa} );
    }
  }
  
  
  private void istanziaAggiudicazioneSottosoglia(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, 
  		final SchedaCompletaType schedaCompletaType, final AggiudicatarioType[] arrayAggiudicatari, 
  		final ResponsabileType[] arrayResponsabili)
  				throws Exception {

  	String sqlInsertW9appa = "Insert into W9APPA (CODGARA,CODLOTT,NUM_APPA,CUI,ASTA_ELETTRONICA,IMPORTO_DISPOSIZIONE"
  			+ ",IMPORTO_AGGIUDICAZIONE, IMPORTO_COMPL_APPALTO, IMPORTO_COMPL_INTERVENTO,PERC_RIBASSO_AGG,PERC_OFF_AUMENTO,"
  			+ "DATA_VERB_AGGIUDICAZIONE,IMPORTO_ATTUAZIONE_SICUREZZA,DATA_STIPULA,DURATA_CON,"
  			+ "IMPORTO_SUBTOTALE,ALTRE_SOMME) values (?,?,?,?,?,?,?,?,?,#perc_ribasso_agg,#perc_off_aumento,#data_verb_aggiudicazione,"
  			+ "#importo_attuazione_sicurezza,#data_stipula,#durata_con,#importo_subtotale,#altre_somme)";

  	SottoEsclusoType appaltoSottoSoglia = schedaCompletaType.getSottosoglia().getAppalto();
  	
  	List<Object> paramSql = new ArrayList<Object>();
  	paramSql.add(new Long(codGara));
  	paramSql.add(new Long(codLott));
  	paramSql.add(new Long(1));
  	paramSql.add(schedaCompletaType.getCUI());
  	paramSql.add("S".equals(appaltoSottoSoglia.getASTAELETTRONICA()) ? "1" : "2");
  	BigDecimal importoADisposizione = new BigDecimal(appaltoSottoSoglia.getIMPORTODISPOSIZIONE());
  	paramSql.add(importoADisposizione);
  	paramSql.add(new BigDecimal(appaltoSottoSoglia.getIMPORTOAGGIUDICAZIONE()));
  	paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO());
  	paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO().add(importoADisposizione));
  	
  	if (appaltoSottoSoglia.isSetPERCRIBASSOAGG() && new Double(appaltoSottoSoglia.getPERCRIBASSOAGG()).doubleValue() > 0) {
  		paramSql.add(new Double(appaltoSottoSoglia.getPERCRIBASSOAGG()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_ribasso_agg","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_ribasso_agg", "null");
  	}
  	if (appaltoSottoSoglia.isSetPERCOFFAUMENTO() && new Float(appaltoSottoSoglia.getPERCOFFAUMENTO()).doubleValue() > 0) {
  		paramSql.add(new Double(appaltoSottoSoglia.getPERCOFFAUMENTO()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_off_aumento", "?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_off_aumento", "null");
  	}
  	if (appaltoSottoSoglia.isSetDATAAGGIUDICAZIONE()) {
  		paramSql.add(this.convertiData(appaltoSottoSoglia.getDATAAGGIUDICAZIONE()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_verb_aggiudicazione", "?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_verb_aggiudicazione", "null");
  	}
  	if (appaltoSottoSoglia.isSetIMPORTOATTUAZIONESICUREZZA()) {
  		paramSql.add(new BigDecimal(appaltoSottoSoglia.getIMPORTOATTUAZIONESICUREZZA()));
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_attuazione_sicurezza", "?");
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_attuazione_sicurezza", "null");
  	}

  	if (appaltoSottoSoglia.isSetDATASTIPULA()) {
  		paramSql.add(appaltoSottoSoglia.getDATASTIPULA().getTime());
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_stipula", "?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_stipula","null");
  	}
  	
  	if (appaltoSottoSoglia.isSetDURATACONTRATTUALE()) {
  		paramSql.add(new Long(appaltoSottoSoglia.getDURATACONTRATTUALE()));
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#durata_con", "?");
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#durata_con", "null");
  	}

  	// Parametro sql per il campo ALTRE_SOMME
  	paramSql.add(appaltoSottoSoglia.getIMPORTODISPOSIZIONE());
  	sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#altre_somme", "?");
  	
  	// Parametro sql per importo subtotale
  	if (appaltoSottoSoglia.isSetIMPORTOATTUAZIONESICUREZZA()) {
  		double importoAttsicurezza = Double.parseDouble(appaltoSottoSoglia.getIMPORTOATTUAZIONESICUREZZA());
  		double importoSubTotale = appaltoSottoSoglia.getIMPORTOCOMPLESSIVO().doubleValue() - importoAttsicurezza;
 			paramSql.add(importoSubTotale);
 			sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_subtotale", "?");
  	} else {
  		paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO());
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_subtotale", "?");
  	}
  	
  	// Inserimento in W9APPA
  	this.sqlManager.update(sqlInsertW9appa, paramSql.toArray());
  	String idSchedaLocale = null;
  	if (StringUtils.isNotEmpty(appaltoSottoSoglia.getIDSCHEDALOCALE())) {
  		idSchedaLocale = appaltoSottoSoglia.getIDSCHEDALOCALE();
  	}
  	String idSchedaSimog = null;
  	if (StringUtils.isNotEmpty(appaltoSottoSoglia.getIDSCHEDASIMOG())) {
  		idSchedaSimog = appaltoSottoSoglia.getIDSCHEDASIMOG();
  	}
  	
    boolean isS2 = UtilitySITAT.isS2(codGara, codLott, this.sqlManager);
    boolean isAAQ = UtilitySITAT.isAAQ(codGara, this.sqlManager);
    
    Long faseEsecuzione = null;
    if (isAAQ) {
      faseEsecuzione = new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO);
    } else  if (isS2) {
      faseEsecuzione = new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA);
    } else {
      faseEsecuzione = new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
    }
  	
  	// Inserimento in W9FASI e in W9FLUSSI
  	this.istanziaFaseFlusso(codGara, codLott, faseEsecuzione, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  	
  	// Gestione incarichi professionali
		IncaricatoType[] incarichi = schedaCompletaType.getSottosoglia().getIncaricatiArray();
		this.istanziaIncarichiProfessionali(codGara, codLott, new Long(1), codUffInt, incarichi, arrayResponsabili);

		// Gestione imprese aggiudicatarie
		SoggAggiudicatarioType[] arraySoggAggiudicatari = schedaCompletaType.getSottosoglia().getAggiudicatariArray();
		this.w9Manager.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(new Long(codGara), new Long(codLott),
        new Long(1), codUffInt, arraySoggAggiudicatari, null, arrayAggiudicatari);

		if (schedaCompletaType.getSottosoglia().getCondizioniArray() != null && schedaCompletaType.getSottosoglia().getCondizioniArray().length > 0) {
			
		}
		
  }
  
  
  private void istanziaAggiudicazioneEsclusa(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, 
  		final SchedaCompletaType schedaCompletaType, final AggiudicatarioType[] arrayAggiudicatari, 
  		final ResponsabileType[] arrayResponsabili)
  				throws Exception {
  	
  	String sqlInsertW9appa = "Insert into W9APPA (CODGARA,CODLOTT,NUM_APPA,CUI,ASTA_ELETTRONICA,IMPORTO_DISPOSIZIONE,"
  			+ "IMPORTO_AGGIUDICAZIONE,IMPORTO_COMPL_APPALTO,IMPORTO_COMPL_INTERVENTO,PERC_RIBASSO_AGG,PERC_OFF_AUMENTO,"
  			+ "DATA_VERB_AGGIUDICAZIONE,IMPORTO_ATTUAZIONE_SICUREZZA,DATA_STIPULA,DURATA_CON,"
  			+ "IMPORTO_SUBTOTALE,ALTRE_SOMME) values (?,?,?,?,?,?,?,?,?,#perc_ribasso_agg,#perc_off_aumento,"
  			+ "#data_verb_aggiudicazione,#importo_attuazione_sicurezza,#data_stipula,#durata_con,"
  			+ "#importo_subtotale,#altre_somme)";

  	SottoEsclusoType appaltoSottoSoglia = schedaCompletaType.getEscluso().getAppalto();
  	
  	List<Object> paramSql = new ArrayList<Object>();
  	paramSql.add(new Long(codGara));
  	paramSql.add(new Long(codLott));
  	paramSql.add(new Long(1));
  	paramSql.add(schedaCompletaType.getCUI());
  	paramSql.add("S".equals(appaltoSottoSoglia.getASTAELETTRONICA()) ? "1" : "2");
  	double importoADisposizione = Double.parseDouble(appaltoSottoSoglia.getIMPORTODISPOSIZIONE());
  	paramSql.add(importoADisposizione);
  	paramSql.add(Double.parseDouble(appaltoSottoSoglia.getIMPORTOAGGIUDICAZIONE()));
  	paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO().doubleValue());
  	paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO().doubleValue() + importoADisposizione);
  	
  	if (appaltoSottoSoglia.isSetPERCRIBASSOAGG() && new Double(appaltoSottoSoglia.getPERCRIBASSOAGG()).doubleValue() > 0) {
  		paramSql.add(new Double(appaltoSottoSoglia.getPERCRIBASSOAGG()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_ribasso_agg", "?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_ribasso_agg","null");
  	}
  	if (appaltoSottoSoglia.isSetPERCOFFAUMENTO() && new Float(appaltoSottoSoglia.getPERCOFFAUMENTO()).doubleValue() > 0) {
  		paramSql.add(new Double(appaltoSottoSoglia.getPERCOFFAUMENTO()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_off_aumento","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#perc_off_aumento","null");
  	}
  	if (appaltoSottoSoglia.isSetDATAAGGIUDICAZIONE()) {
  		paramSql.add(this.convertiData(appaltoSottoSoglia.getDATAAGGIUDICAZIONE()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_verb_aggiudicazione","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_verb_aggiudicazione", "null");
  	}
  	if (appaltoSottoSoglia.isSetIMPORTOATTUAZIONESICUREZZA()) {
  		paramSql.add(new BigDecimal(appaltoSottoSoglia.getIMPORTOATTUAZIONESICUREZZA()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_attuazione_sicurezza","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_attuazione_sicurezza","null");
  	}

  	if (appaltoSottoSoglia.isSetDATASTIPULA()) {
  		paramSql.add(appaltoSottoSoglia.getDATASTIPULA().getTime());
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_stipula","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#data_stipula","null");
  	}
  	
  	if (appaltoSottoSoglia.isSetDURATACONTRATTUALE()) {
  		paramSql.add(new Long(appaltoSottoSoglia.getDURATACONTRATTUALE()));
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#durata_con","?");
  	} else {
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#durata_con","null");
  	}

  	// Parametro sql per il campo ALTRE_SOMME
  	paramSql.add(appaltoSottoSoglia.getIMPORTODISPOSIZIONE());
  	sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#altre_somme","?");
  	paramSql.add(appaltoSottoSoglia.getIMPORTODISPOSIZIONE());
  	sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#altre_somme","?");
  	
  	// Parametro sql per importo subtotale
  	if (appaltoSottoSoglia.isSetIMPORTOATTUAZIONESICUREZZA()) {
  		BigDecimal importoAttsicurezza = new BigDecimal(appaltoSottoSoglia.getIMPORTOATTUAZIONESICUREZZA());
  		BigDecimal importoSubTotale = appaltoSottoSoglia.getIMPORTOCOMPLESSIVO().add(importoAttsicurezza.multiply(new BigDecimal(-1)));
 			paramSql.add(importoSubTotale);
 			sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_subtotale","?");
  	} else {
  		paramSql.add(appaltoSottoSoglia.getIMPORTOCOMPLESSIVO());
  		sqlInsertW9appa = StringUtils.replace(sqlInsertW9appa, "#importo_subtotale","?");
  	}

  	// Inserimento in W9APPA
  	this.sqlManager.update(sqlInsertW9appa, paramSql.toArray());
  	String idSchedaLocale = null;
  	if (StringUtils.isNotEmpty(appaltoSottoSoglia.getIDSCHEDALOCALE())) {
  		idSchedaLocale = appaltoSottoSoglia.getIDSCHEDALOCALE();
  	}
  	String idSchedaSimog = null;
  	if (StringUtils.isNotEmpty(appaltoSottoSoglia.getIDSCHEDASIMOG())) {
  		idSchedaSimog = appaltoSottoSoglia.getIDSCHEDASIMOG();
  	}

    boolean isS2 = UtilitySITAT.isS2(codGara, codLott, this.sqlManager);
    boolean isAAQ = UtilitySITAT.isAAQ(codGara, this.sqlManager);
    
    Long faseEsecuzione = null;
    if (isAAQ) {
      faseEsecuzione = new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO);
    } else  if (isS2) {
      faseEsecuzione = new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA);
    } else {
      faseEsecuzione = new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
    }
  	
  	// Inserimento in W9FASI e in W9FLUSSI
  	this.istanziaFaseFlusso(codGara, codLott, faseEsecuzione, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  	
  	// Gestione incarichi professionali
		IncaricatoType[] incarichi = schedaCompletaType.getEscluso().getIncaricatiArray();
		this.istanziaIncarichiProfessionali(codGara, codLott, new Long(1), codUffInt, incarichi, arrayResponsabili);

		// Gestione imprese aggiudicatarie
		SoggAggiudicatarioType[] arraySoggAggiudicatari = schedaCompletaType.getEscluso().getAggiudicatariArray();
		this.w9Manager.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(new Long(codGara), new Long(codLott),
        new Long(1), codUffInt, arraySoggAggiudicatari, null, arrayAggiudicatari);
  }
  
  
  
  private void istanziaAdesioneAccordoQuadro(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, 
  		final SchedaCompletaType schedaCompletaType, final AggiudicatarioType[] arrayAggiudicatari, 
  		final ResponsabileType[] arrayResponsabili)
  				throws Exception {
  	
    // Importazione dell'aggiudicazione e delle entità figlie.
    // Insert aggiudicazione (W9APPA, W9AGGI, W9INCA, W9FINA, IMPR, IMPLEG, TEIM)
    Long faseEsecuz = new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO);
    
    String codiceCUI = schedaCompletaType.getCUI();
    // Adesione
    AdesioneType adesioneType = schedaCompletaType.getAdesione();
    AppaltoAdesioneType appaltoAdesioneType = adesioneType.getAppalto();
        
    String idSchedaLocale = null;
    String idSchedaSimog = null;
    if (appaltoAdesioneType.isSetIDSCHEDALOCALE()) {
    	idSchedaLocale = appaltoAdesioneType.getIDSCHEDALOCALE();
    }
    if (appaltoAdesioneType.isSetIDSCHEDASIMOG()) {
    	idSchedaSimog = appaltoAdesioneType.getIDSCHEDASIMOG();
    }
    
    // Record in W9FASI e in W9FLUSSI.
    this.istanziaFaseFlusso(codGara, codLott, faseEsecuz, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
    
    DataColumn codGaraAppa = new DataColumn("W9APPA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara));
    DataColumn codLottAppa = new DataColumn("W9APPA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott));
    DataColumn numAppa = new DataColumn("W9APPA.NUM_APPA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
    DataColumn codCui = new DataColumn("W9APPA.CODCUI",    new JdbcParametro(JdbcParametro.TIPO_TESTO, codiceCUI));
    
    DataColumnContainer dccAggiudicazione = new DataColumnContainer(new DataColumn[] {
        codGaraAppa, codLottAppa, numAppa, codCui } );
    
    if (appaltoAdesioneType.isSetPERCRIBASSOAGG() && new Double(appaltoAdesioneType.getPERCRIBASSOAGG()).doubleValue() > 0) {
      dccAggiudicazione.addColumn("W9APPA.PERC_RIBASSO_AGG", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(appaltoAdesioneType.getPERCRIBASSOAGG()).toString())));
    }
    if (appaltoAdesioneType.isSetPERCOFFAUMENTO() && new Float(appaltoAdesioneType.getPERCOFFAUMENTO()).doubleValue() > 0) {
      dccAggiudicazione.addColumn("W9APPA.PERC_OFF_AUMENTO", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(new Float(appaltoAdesioneType.getPERCOFFAUMENTO()).toString())));
    }
    if (StringUtils.isNotEmpty(appaltoAdesioneType.getFLAGRICHSUBAPPALTO())) {
      dccAggiudicazione.addColumn("W9APPA.FLAG_RICH_SUBAPPALTO", new JdbcParametro(JdbcParametro.TIPO_TESTO,
          "S".equals(appaltoAdesioneType.getFLAGRICHSUBAPPALTO()) ? "1" : "2"));
    }
  
    double importoLavori = 0, importoForniture = 0, importoServizi = 0, importoProgettazione = 0, importoIva = 0;
    double importoSubTotale = 0, importoAltreSomme = 0, importoAttuazioneSicurezza = 0,  importoNonSoggettoARibasso = 0;
    
    importoLavori = new Double(appaltoAdesioneType.getIMPORTOLAVORI()).doubleValue();
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_LAVORI", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(appaltoAdesioneType.getIMPORTOLAVORI())));
    importoServizi = new Double(appaltoAdesioneType.getIMPORTOSERVIZI()).doubleValue(); 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_SERVIZI", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoAdesioneType.getIMPORTOSERVIZI())));
    importoForniture = new Double(appaltoAdesioneType.getIMPORTOFORNITURE()).doubleValue(); 
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_FORNITURE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
        new Double(appaltoAdesioneType.getIMPORTOFORNITURE())));
    importoSubTotale = importoLavori + importoForniture + importoServizi;
    
    importoProgettazione = new Double(appaltoAdesioneType.getIMPORTOPROGETTAZIONE()).doubleValue(); 
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_PROGETTAZIONE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(appaltoAdesioneType.getIMPORTOPROGETTAZIONE())));
    
    if (appaltoAdesioneType.isSetIMPNONASSOG()) {
      importoNonSoggettoARibasso = new Double(appaltoAdesioneType.getIMPNONASSOG()).doubleValue();
      dccAggiudicazione.addColumn("W9APPA.IMP_NON_ASSOG", new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
          new Double(appaltoAdesioneType.getIMPNONASSOG())));
    }
    
    if (importoSubTotale > 0) {
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_SUBTOTALE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, importoSubTotale));
    } else {
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_SUBTOTALE", new JdbcParametro(JdbcParametro.TIPO_DECIMALE, new Double(0)));
    }
    
    importoAttuazioneSicurezza = new Double(appaltoAdesioneType.getIMPORTOATTUAZIONESICUREZZA()).doubleValue();
      dccAggiudicazione.addColumn("W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA", 
          new JdbcParametro(JdbcParametro.TIPO_DECIMALE,
              new Double(appaltoAdesioneType.getIMPORTOATTUAZIONESICUREZZA())));
    
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_DISPOSIZIONE", new JdbcParametro(
          JdbcParametro.TIPO_DECIMALE, importoIva + importoAltreSomme));
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_COMPL_APPALTO", new JdbcParametro(
        JdbcParametro.TIPO_DECIMALE, importoSubTotale + importoAttuazioneSicurezza 
            + importoProgettazione + importoNonSoggettoARibasso));
    dccAggiudicazione.addColumn("W9APPA.IMPORTO_COMPL_INTERVENTO", new JdbcParametro(
        JdbcParametro.TIPO_DECIMALE, importoSubTotale + importoAttuazioneSicurezza 
        + importoProgettazione + importoNonSoggettoARibasso + importoIva + importoAltreSomme));

    if (appaltoAdesioneType.isSetCODSTRUMENTO()){
      dccAggiudicazione.addColumn("W9APPA.COD_STRUMENTO", new JdbcParametro(
            JdbcParametro.TIPO_TESTO, appaltoAdesioneType.getCODSTRUMENTO()));
    }
     
    // Inserimento dell'aggiudicazione in W9APPA
    dccAggiudicazione.insert("W9APPA", this.sqlManager);
 
    String updateW9LOTT = "update W9LOTT set CODCUI=? where CODGARA=? and CODLOTT=?";
    Object[] paramUpdateW9LOTT = new Object [] { codiceCUI, codGara, codLott }; 
    this.sqlManager.update(updateW9LOTT, paramUpdateW9LOTT);
    
    this.w9Manager.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(new Long(codGara), new Long(codLott),
        new Long(numAppa.getValue().getStringValue()), codUffInt, adesioneType.getAggiudicatariArray(),
        adesioneType.getDitteAusiliarieArray(), arrayAggiudicatari);

    String codFiscaleStazAppaltante = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", 
        new Object[] { codUffInt } );

    IncaricatoType[] arrayIncarichiProfessionali = adesioneType.getIncaricatiArray();
    this.istanziaIncarichiProfessionali(new Long(codGara), new Long(codLott),
        new Long(numAppa.getValue().getStringValue()), codUffInt,
        arrayIncarichiProfessionali, arrayResponsabili);
    
    this.istanziaFinanziamenti(codFiscaleStazAppaltante, adesioneType.getFinanziamentiArray(),
        codGara, codLott, (Long) numAppa.getValue().getValue(), false);

  }

  
  
  private void istanziaInizio(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, final SchedaCompletaType schedaCompleta,
  		ResponsabileType[] arrayResponsabili) throws SQLException, GestoreException, ParseException, Exception {
  	
  	try {

  		// Importazione dell'aggiudicazione e delle entità figlie.
      // Insert aggiudicazione (W9APPA, W9AGGI, W9INCA, W9FINA, IMPR, IMPLEG, TEIM)
      Long faseEsecuzione = new Long(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA);
      
      // Adesione
      DatiInizioType datiInizioType = schedaCompleta.getDatiInizio();
      InizioType inizioType = datiInizioType.getInizio();
          
      String idSchedaLocale = null;
      String idSchedaSimog = null;
      if (inizioType.isSetIDSCHEDALOCALE()) {
      	idSchedaLocale = inizioType.getIDSCHEDALOCALE();
      }
      if (inizioType.isSetIDSCHEDASIMOG()) {
      	idSchedaSimog = inizioType.getIDSCHEDASIMOG();
      }
      
      // Record in W9FASI e in W9FLUSSI
      this.istanziaFaseFlusso(codGara, codLott, faseEsecuzione, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  		
      String sqlInsertW9INIZ = "insert into w9iniz (codgara,codlott,num_iniz,num_appa,flag_frazionata,flag_riserva,importo_cauzione,"
      		+ "data_app_prog_esec,data_esecutivita,data_ini_prog_esec,data_stipula,data_termine,data_verbale_cons,data_verbale_def,"
      		+ "data_verb_inizio) values (?,?,?,?,?,?,?,#data_app_prog_esec,#data_esecutivita,#data_ini_prog_esec,#data_stipula,#data_termine,"
      		+ "#data_verbale_cons,#data_verbale_def,#data_verb_inizio)";
			
			if (inizioType != null) {
				PubblicazioneType pubblicazione = datiInizioType.getPubblicazioneEsito();

				List<Object> paramSqlW9INIZ = new ArrayList<Object>();
				paramSqlW9INIZ.add(codGara);
				paramSqlW9INIZ.add(codLott);
				paramSqlW9INIZ.add(1L);
				paramSqlW9INIZ.add(1L);
				paramSqlW9INIZ.add(FlagSNType.S.equals(inizioType.getFLAGFRAZIONATA()) ? "1" : "2");
				paramSqlW9INIZ.add("S".equals(inizioType.getFLAGRISERVA()) ? "1" : "2");
				paramSqlW9INIZ.add(inizioType.getIMPORTOCAUZIONE().doubleValue());
				
				if (inizioType.isSetDATAAPPPROGESEC()) {
					paramSqlW9INIZ.add(inizioType.getDATAAPPPROGESEC().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_app_prog_esec","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_app_prog_esec","null");
				}
				if (inizioType.isSetDATAESECUTIVITA()) {
					paramSqlW9INIZ.add(inizioType.getDATAESECUTIVITA().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_esecutivita", "?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_esecutivita", "null");
				}
				if (inizioType.isSetDATAINIPROGESEC()) {
					paramSqlW9INIZ.add(inizioType.getDATAINIPROGESEC().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_ini_prog_esec","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_ini_prog_esec","null");
				}
				if (inizioType.isSetDATASTIPULA()) {
					paramSqlW9INIZ.add(inizioType.getDATASTIPULA().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_stipula","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_stipula","null");
				}
				if (inizioType.isSetDATATERMINE()) {
					paramSqlW9INIZ.add(this.convertiData(inizioType.getDATATERMINE()));
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_termine","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_termine","null");
				}
				if (inizioType.isSetDATAVERBALECONS()) {
					paramSqlW9INIZ.add(inizioType.getDATAVERBALECONS().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verbale_cons","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verbale_cons","null");
				}
				if (inizioType.isSetDATAVERBALEDEF()) {
					paramSqlW9INIZ.add(inizioType.getDATAVERBALEDEF().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verbale_def","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verbale_def","null");
				}
				if (inizioType.isSetDATAVERBINIZIO()) {
					paramSqlW9INIZ.add(inizioType.getDATAVERBINIZIO().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verb_inizio","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_verb_inizio","null");
				}

				this.sqlManager.update(sqlInsertW9INIZ, paramSqlW9INIZ.toArray());

				IncaricatoType[] arrayIncarichi = datiInizioType.getIncaricatiArray();
				this.istanziaIncarichiProfessionali(codGara, codLott, 1L, codUffInt, arrayIncarichi, arrayResponsabili);
				
				// W9PUES
				this.istanziaPubblicita(codGara, codLott, pubblicazione);
				
				// W9SIC
				String sqlInsertW9SIC = "insert into w9sic (codgara, codlott, num_sic, num_iniz, pianscic, dirope, tutor) values (?,?,?,?,?,?,?)";

				List<Object> paramSqlW9SIC = new ArrayList<Object>();
				paramSqlW9SIC.add(codGara);
				paramSqlW9SIC.add(codLott);
				paramSqlW9SIC.add(1L);
				paramSqlW9SIC.add(1L);
				paramSqlW9SIC.add("2");
				paramSqlW9SIC.add("2");
				paramSqlW9SIC.add("2");
				
				this.sqlManager.update(sqlInsertW9SIC, paramSqlW9SIC.toArray());
			}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda iniziale. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  private void istanziaStipulaAccordoQuadro(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza,
  		final SchedaCompletaType schedaCompleta, ResponsabileType[] arrayResponsabili) throws SQLException, GestoreException, ParseException, Exception {
  	
		try {
		  Long faseEsecuzione = new Long(CostantiW9.STIPULA_ACCORDO_QUADRO);
		  
      // Adesione
      DatiStipulaType datiStipulaType = schedaCompleta.getDatiStipula();
      StipulaType stipulaType = datiStipulaType.getStipula();
          
      String idSchedaLocale = null;
      String idSchedaSimog = null;
      if (stipulaType.isSetIDSCHEDALOCALE()) {
      	idSchedaLocale = stipulaType.getIDSCHEDALOCALE();
      }
      if (stipulaType.isSetIDSCHEDASIMOG()) {
      	idSchedaSimog = stipulaType.getIDSCHEDASIMOG();
      }
      
      // Record in W9FASI.
      this.istanziaFaseFlusso(codGara, codLott, faseEsecuzione, new Long(1), new Long(1), idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  		
      String sqlInsertW9INIZ = "Insert into W9INIZ (codgara,codlott,num_iniz,num_appa,data_stipula,data_decorrenza,"
      		+ "data_scadenza) values (?,?,?,?,#data_stipula,#data_decorrenza,#data_scadenza)";
      
			if (stipulaType != null) {
				PubblicazioneType pubblicazione = datiStipulaType.getPubblicazioneEsito();

				List<Object> paramSqlW9INIZ = new ArrayList<Object>();
				paramSqlW9INIZ.add(codGara);
				paramSqlW9INIZ.add(codLott);
				paramSqlW9INIZ.add(1L);
				paramSqlW9INIZ.add(1L);
				if (stipulaType.isSetDATASTIPULA()) {
					paramSqlW9INIZ.add(stipulaType.getDATASTIPULA().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_stipula","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_stipula","null");
				}
				if (stipulaType.isSetDATADECORRRENZA()) {
					paramSqlW9INIZ.add(stipulaType.getDATADECORRRENZA().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_decorrenza","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_decorrenza","null");
				}
				if (stipulaType.isSetDATASCADENZA()) {
					paramSqlW9INIZ.add(stipulaType.getDATASCADENZA().getTime());
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_scadenza","?");
				} else {
					sqlInsertW9INIZ = StringUtils.replace(sqlInsertW9INIZ, "#data_scadenza","null");
				}
				
				this.sqlManager.update(sqlInsertW9INIZ, paramSqlW9INIZ.toArray());

				// W9PUES
				this.istanziaPubblicita(codGara, codLott, pubblicazione);
				
			}		  
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda stipula accordo quadro. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
	}
  
  

  private void istanziaFaseAvanzamento(Long codGara, Long codLott, String codUffInt, final boolean isCfgVigilanza, SchedaCompletaType schedaCompleta)
  		 throws SQLException, GestoreException, ParseException, Exception {
  	try {
  		
  		AvanzamentiType datiAvanzamento = schedaCompleta.getDatiAvanzamenti();
  		if (datiAvanzamento != null && datiAvanzamento.getAvanzamentoArray() != null && datiAvanzamento.getAvanzamentoArray().length > 0) {
	  		AvanzamentoType[] arrayAvanzamento = datiAvanzamento.getAvanzamentoArray();
	  		
	  		if (arrayAvanzamento != null && arrayAvanzamento.length > 0) {
	  			for (int i=0; i < arrayAvanzamento.length; i++) {
	  	  		String sqlInsertW9AVAN = "Insert into W9AVAN (codgara,codlott,num_avan,num_appa,data_raggiungimento,importo_sal,flag_ritardo,"
	  	  				+ "flag_pagamento,denom_avanzamento,data_certificato,importo_certificato,num_giorni_scost,num_giorni_proroga,"
	  	  				+ "importo_anticipazione,data_anticipazione) values (?,?,?,?,?,?,?,?,#denom_avanzamento,#data_certificato,#importo_certificato,"
	  	  				+ "#num_giorni_scost,#num_giorni_proroga,#importo_anticipazione,#data_anticipazione)";
	  				
	  				AvanzamentoType avanzamentoType = arrayAvanzamento[i];
	  				
	  	  		String idSchedaLocale = null;
	  	      String idSchedaSimog = null;
	  	      if (avanzamentoType.isSetIDSCHEDALOCALE()) {
	  	      	idSchedaLocale = avanzamentoType.getIDSCHEDALOCALE();
	  	      }
	  	      if (avanzamentoType.isSetIDSCHEDASIMOG()) {
	  	      	idSchedaSimog = avanzamentoType.getIDSCHEDASIMOG();
	  	      }
	  				this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA), new Long(i+1), 1L, 
	  						idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
						
	  				List<Object> paramSqlW9AVAN = new ArrayList<Object>();
	  				paramSqlW9AVAN.add(codGara);
	  				paramSqlW9AVAN.add(codLott);
	  				paramSqlW9AVAN.add(new Long(i+1));
	  				paramSqlW9AVAN.add(new Long(1));
	  				paramSqlW9AVAN.add(avanzamentoType.getDATARAGGIUNGIMENTO().getTime());
	  				paramSqlW9AVAN.add(avanzamentoType.getIMPORTOSAL().doubleValue());
	  				paramSqlW9AVAN.add(avanzamentoType.getFLAGRITARDO().toString());
	  				String flagPagamento = null;
	  				try {
	  					flagPagamento = avanzamentoType.getFLAGPAGAMENTO();
	  				} catch (org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException xvoore) {
	  	        logger.error("Errore nella lettura del FLAG_PAGAMENTO nell'oggetto avanzamentoType ricevuto da SIMOG e questo e' l'XML ricevuto ("
	  	            + avanzamentoType.toString() + " )", xvoore);
	  	        flagPagamento = null;
	  	      }
	  	      
	  	      if (flagPagamento == null) {
	  	        String xmlAvanzamento = avanzamentoType.toString();
	  	        if (xmlAvanzamento.indexOf("FLAG_PAGAMENTO") >= 0) {
	  	          int inizio = xmlAvanzamento.indexOf("FLAG_PAGAMENTO");
	  	          int fine = 16;
	  	          
	  	          String s1 = xmlAvanzamento.substring(inizio+fine);
	  	          int primoApiceSpazio = s1.indexOf("\" ");
	  	          flagPagamento = s1.substring(0, primoApiceSpazio);
	  	        }
	  	      }

	  	      if (flagPagamento != null) {
	  	      	paramSqlW9AVAN.add(new Long(flagPagamento));
	  	      } else {
	  	      	sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "?,#denom_avanzamento", "null,#denom_avanzamento");
	  	      }
	  	      
	  				if (avanzamentoType.isSetDENOMAVANZAMENTO()) {
	  					paramSqlW9AVAN.add(avanzamentoType.getDENOMAVANZAMENTO());
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#denom_avanzamento", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#denom_avanzamento", "null");
	  				}
	  				
	  				if (avanzamentoType.isSetDATACERTIFICATO()) {
	  					paramSqlW9AVAN.add(avanzamentoType.getDATACERTIFICATO().getTime());
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#data_certificato", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#data_certificato", "null");
	  				}
	  				if (avanzamentoType.isSetIMPORTOCERTIFICATO()) {
	  					paramSqlW9AVAN.add(avanzamentoType.getIMPORTOCERTIFICATO().doubleValue());
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#importo_certificato", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#importo_certificato", "null");
	  				}
	  				if (avanzamentoType.isSetNUMGIORNISCOST()) {
	  					paramSqlW9AVAN.add(new Long(avanzamentoType.getNUMGIORNISCOST()));
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#num_giorni_scost", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#num_giorni_scost", "null");
	  				}
	  				if (avanzamentoType.isSetNUMGIORNIPROROGA()) {
	  					paramSqlW9AVAN.add(new Long(avanzamentoType.getNUMGIORNIPROROGA()));
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#num_giorni_proroga", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#num_giorni_proroga", "null");
	  				}
	  				
	  				if (i == 0 && avanzamentoType.isSetIMPORTOANTICIPAZIONE()) {
	  					paramSqlW9AVAN.add(avanzamentoType.getIMPORTOANTICIPAZIONE().doubleValue());
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#importo_anticipazione", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#importo_anticipazione", "null");
	  				}
	  				if (i == 0 && avanzamentoType.isSetDATAANTICIPAZIONE()) {
	  					paramSqlW9AVAN.add(avanzamentoType.getDATAANTICIPAZIONE().getTime());
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#data_anticipazione", "?");
	  				} else {
	  					sqlInsertW9AVAN = StringUtils.replace(sqlInsertW9AVAN, "#data_anticipazione", "null");
	  				}
	
	 					// W9AVAN
	 					this.sqlManager.update(sqlInsertW9AVAN, paramSqlW9AVAN.toArray());
	 					
	  			}
	  		}
  		
  		}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda avanzamento. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
		
	}
  
  
  private void istanziaFaseConclusione(Long codGara, Long codLott, String codUffInt, final boolean isCfgVigilanza, SchedaCompletaType schedaCompleta)
  		 throws SQLException, GestoreException, ParseException, Exception {
  	try {
  		
  		String sqlInsertW9CONC = "Insert into W9CONC (codgara,codlott,num_conc,num_appa,num_infortuni,num_inf_perm,num_inf_mort,intantic,"
  				+ "id_motivo_interr,id_motivo_risol,data_risoluzione,flag_oneri,oneri_risoluzione,flag_polizza,data_verbale_consegna,"
  				+ "termine_contratto_ult,data_ultimazione,num_giorni_proroga) values (?,?,?,?,?,?,?,#intantic,#id_motivo_interr,#id_motivo_risol,#data_risoluzione,"
  				+ "#flag_oneri,#oneri_risoluzione,#flag_polizza,#data_verbale_consegna,#termine_contratto_ult,#data_ultimazione,#num_giorni_proroga)";

  		ConclusioneType conclusione = schedaCompleta.getDatiConclusione();
  		
  		if (conclusione != null) {
	  		String idSchedaLocale = null;
	  		String idSchedaSimog = null;
	  		if (conclusione.isSetIDSCHEDALOCALE()) {
	  			idSchedaLocale = conclusione.getIDSCHEDALOCALE();
	  		}
	  		if (conclusione.isSetIDSCHEDASIMOG()) {
	  			idSchedaSimog = conclusione.getIDSCHEDASIMOG();
	  		}
	  		
	  		this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA), 1L, 1L, 
	  				idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  		
	  		List<Object> paramSqlW9CONC = new ArrayList<Object>();
	  		paramSqlW9CONC.add(codGara);
	  		paramSqlW9CONC.add(codLott);
	  		paramSqlW9CONC.add(1L);
	  		paramSqlW9CONC.add(1L);
	  		paramSqlW9CONC.add(new Long(conclusione.getNUMINFORTUNI()));
	  		paramSqlW9CONC.add(new Long(conclusione.getNUMINFPERM()));
	  		paramSqlW9CONC.add(new Long(conclusione.getNUMINFMORT()));
	  		
	  		if (conclusione.isSetIDMOTIVOINTERR()) {
	  			paramSqlW9CONC.add("1");
	  			paramSqlW9CONC.add(new Long(conclusione.getIDMOTIVOINTERR()));
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#intantic", "?");
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#id_motivo_interr", "?");
	  		} else {
	  			paramSqlW9CONC.add("2");
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#intantic", "?");
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#id_motivo_interr", "null");
	  		}
	  		if (conclusione.isSetIDMOTIVORISOL()) {
	  			paramSqlW9CONC.add(conclusione.getIDMOTIVORISOL());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#id_motivo_risol", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#id_motivo_risol", "null");
	  		}
	  		
	  		if (conclusione.isSetDATARISOLUZIONE()) {
	  			paramSqlW9CONC.add(conclusione.getDATARISOLUZIONE().getTime());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_risoluzione", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_risoluzione", "null");	  			
	  		}
	  		if (conclusione.isSetFLAGONERI()) {
	  			paramSqlW9CONC.add(Long.parseLong(conclusione.getFLAGONERI()));
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#flag_oneri", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#flag_oneri", "null");
	  		}
	  		if (conclusione.isSetONERIRISOLUZIONE()) {
	  			paramSqlW9CONC.add(conclusione.getONERIRISOLUZIONE().doubleValue());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#oneri_risoluzione", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#oneri_risoluzione", "null");
  			}
	  		if (conclusione.isSetFLAGPOLIZZA()) {
	  			paramSqlW9CONC.add(FlagSNType.S.equals(conclusione.getFLAGPOLIZZA()) ? "1" : "2");
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#flag_polizza", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#flag_polizza", "null");
	  		}
	  		if (conclusione.isSetDATAVERBCONSEGNAAVVIO()) {
	  			paramSqlW9CONC.add(conclusione.getDATAVERBCONSEGNAAVVIO().getTime());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_verbale_consegna", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_verbale_consegna", "null");
	  		}
	  		if (conclusione.isSetTERMINECONTRATTULTIMAZIONE()) {
	  			paramSqlW9CONC.add(conclusione.getTERMINECONTRATTULTIMAZIONE().getTime());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#termine_contratto_ult", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#termine_contratto_ult", "null");
	  		}
	  		if (conclusione.isSetDATAULTIMAZIONE()) {
	  			paramSqlW9CONC.add(conclusione.getDATAULTIMAZIONE().getTime());
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_ultimazione", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#data_ultimazione", "null");
	  		}
	  		if (conclusione.isSetNUMGIORNIPROROGA()) {
	  			paramSqlW9CONC.add(new Long(conclusione.getNUMGIORNIPROROGA()));
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#num_giorni_proroga", "?");
	  		} else {
	  			sqlInsertW9CONC = StringUtils.replace(sqlInsertW9CONC, "#num_giorni_proroga", "null");
	  		}
	  		
	  		this.sqlManager.update(sqlInsertW9CONC, paramSqlW9CONC.toArray());
	  		
  		}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda conclusione. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
	}
  
  
  private void istanziaFaseCollaudo(final Long codGara, final Long codLott, final String codUffInt, final boolean isCfgVigilanza, 
  		final SchedaCompletaType schedaCompleta, final ResponsabileType[] arrayResponsabili) throws SQLException, Exception {
  	
  	try {
  		
  		String sqlInsertW9COLL = "Insert into W9COLL (codgara,codlott,num_coll,num_appa,esito_collaudo,data_regolare_esec,data_collaudo_stat,modo_collaudo,tipo_collaudo,"
  				+ "data_nomina_coll,data_inizio_oper,data_cert_collaudo,data_delibera,imp_finale_lavori,imp_finale_servizi,imp_finale_fornit,imp_subtotale,"
  				+ "imp_finale_secur,imp_progettazione,imp_compl_appalto,imp_disposizione,imp_compl_intervento,lavori_estesi,amm_importo_def,amm_num_definite,"
  				+ "amm_num_dadef,amm_importo_rich,arb_importo_def,arb_num_definite,arb_num_dadef,arb_importo_rich,giu_importo_def,giu_num_definite,"
  				+ "giu_num_dadef,giu_importo_rich,tra_importo_def,tra_num_definite,tra_num_dadef,tra_importo_rich) "
  				+ "values (?,?,?,?,?,#data_regolare_esec,#data_collaudo_stat,#modo_collaudo,#tipo_collaudo,#data_nomina_coll,#data_inizio_oper,#data_cert_collaudo,#data_delibera,"
  				+ "#imp_finale_lavori,#imp_finale_servizi,#imp_finale_fornit,#imp_subtotale,#imp_finale_secur,#imp_progettazione,#imp_compl_appalto,"
  				+ "#imp_disposizione,#imp_compl_intervento,#lavori_estesi,#amm_importo_def,#amm_num_definite,#amm_num_dadef,#amm_importo_rich,#arb_importo_def,"
  				+ "#arb_num_definite,#arb_num_dadef,#arb_importo_rich,#giu_importo_def,#giu_num_definite,#giu_num_dadef,#giu_importo_rich,#tra_importo_def,"
  				+ "#tra_num_definite,#tra_num_dadef,#tra_importo_rich)";
  		
  		/*  flag_singolo_commissione,data_approvazione,flag_importi,flag_sicurezza,flag_subappaltatori,      5
					data_inizio_amm_riserve,data_fine_amm_riserve,data_inizio_arb_riserve,data_fine_arb_riserve,"    4
					data_inizio_giu_riserve,data_fine_giu_riserve,data_inizio_tra_riserve,data_fine_tra_riserve      4
					,?,?,?,?,?,?,?,?,?,?,?,?,?  */
  		DatiCollaudoType datiCollaudo = schedaCompleta.getDatiCollaudo();
  		
  		if (datiCollaudo != null) {
  			CollaudoType collaudoType = datiCollaudo.getCollaudo();
  			
	  		String idSchedaLocale = null;
	  		String idSchedaSimog = null;
	  		if (collaudoType.isSetIDSCHEDALOCALE()) {
	  			idSchedaLocale = collaudoType.getIDSCHEDALOCALE();
	  		}
	  		if (collaudoType.isSetIDSCHEDASIMOG()) {
	  			idSchedaSimog = collaudoType.getIDSCHEDASIMOG();
	  		}
	  		
	  		this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.COLLAUDO_CONTRATTO), 1L, 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  		
	  		List<Object> sqlParamW9COLL = new ArrayList<Object>();
	  		sqlParamW9COLL.add(codGara);
	  		sqlParamW9COLL.add(codLott);
	  		sqlParamW9COLL.add(1L);
	  		sqlParamW9COLL.add(1L);
	  		sqlParamW9COLL.add(FlagEsitoCollaudoType.P.equals(collaudoType.getESITOCOLLAUDO()) ? "P" : "N");
	  		
	  		if (collaudoType.isSetDATAREGOLAREESEC()) {
	  			sqlParamW9COLL.add(collaudoType.getDATAREGOLAREESEC().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_regolare_esec","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_regolare_esec","null");
	  		}
	  		if (collaudoType.isSetDATACOLLAUDOSTAT()) {
	  			sqlParamW9COLL.add(collaudoType.getDATACOLLAUDOSTAT().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_collaudo_stat","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_collaudo_stat","null");
	  		}
	  		if (collaudoType.isSetMODOCOLLAUDO()) {
	  			String modoCollaudo = null;
	  			try {
	  				sqlParamW9COLL.add(collaudoType.getMODOCOLLAUDO());
	  			} catch (org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException xvoore) {
	  				logger.error("Errore nella lettura del MODO_COLLAUDO nell'oggetto collaudoType ricevuto da SIMOG e questo e' l'XML ricevuto ("
  	            		+ collaudoType.toString() + " )", xvoore);
	  				modoCollaudo = null;
  	      		}
  	      
  	      		if (modoCollaudo == null) {
  	        		String xmlCollaudo = collaudoType.toString();
	  	        	if (xmlCollaudo.indexOf("MODO_COLLAUDO") >= 0) {
	  	          		int inizio = xmlCollaudo.indexOf("MODO_COLLAUDO");
	  	          		int fine = 15;
	  	          
						String s1 = xmlCollaudo.substring(inizio+fine);
						int primoApiceSpazio = s1.indexOf("\" ");
						modoCollaudo = s1.substring(0, primoApiceSpazio);
	  	        	}
	  	      	}

				if (modoCollaudo != null) {
				sqlParamW9COLL.add(new Long(modoCollaudo));
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#modo_collaudo","?");
				sqlParamW9COLL.add(new Long(1));
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tipo_collaudo", "?");
				} else {
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL, "?,#modo_collaudo", "null");
				sqlParamW9COLL.add(new Long(2));
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tipo_collaudo","?");
				}
			} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#modo_collaudo","null");
					sqlParamW9COLL.add(new Long(2));
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tipo_collaudo","?");
	  		}
	  		if (collaudoType.isSetDATANOMINACOLL()) {
	  			sqlParamW9COLL.add(collaudoType.getDATANOMINACOLL().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_nomina_coll","?");
			} else {
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_nomina_coll","null");
	  		}
	  		if (collaudoType.isSetDATAINIZIOOPER()) {
	  			sqlParamW9COLL.add(collaudoType.getDATAINIZIOOPER().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_inizio_oper","?");
			} else {
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_inizio_oper","null");
	  		}
	  		if (collaudoType.isSetDATACERTCOLLAUDO()) {
	  			sqlParamW9COLL.add(collaudoType.getDATACERTCOLLAUDO().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_cert_collaudo","?");
			} else {
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_cert_collaudo","null");
	  		}
	  		if (collaudoType.isSetDATADELIBERA()) {
	  			sqlParamW9COLL.add(collaudoType.getDATADELIBERA().getTime());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_delibera","?");
			} else {
				sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#data_delibera","null");
	  		}
	  		
	  		double impFinaleLavori = 0;
	  		double impFinaleForniture = 0;
	  		double impFinaleServizi = 0;
	  		double impSubTotale = 0;
	  		double impFinaleSecur = 0;
	  		double impProgettazione = 0;
	  		double impComplAppalto = 0;
	  		double impDisposizione = 0;
	  		double impComplIntervento = 0;
	  		
	  		if (collaudoType.isSetIMPFINALELAVORI()) {
	  			sqlParamW9COLL.add(collaudoType.getIMPFINALELAVORI().doubleValue());
	  			impFinaleLavori = collaudoType.getIMPFINALELAVORI().doubleValue();
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_lavori","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_lavori","null");
	  		}
	  		if (collaudoType.isSetIMPFINALESERVIZI()) {
	  			sqlParamW9COLL.add(collaudoType.getIMPFINALESERVIZI().doubleValue());
	  			impFinaleServizi = collaudoType.getIMPFINALESERVIZI().doubleValue();
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_servizi","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_servizi","null");
	  		}
	  		if (collaudoType.isSetIMPFINALEFORNIT()) {
	  			sqlParamW9COLL.add(collaudoType.getIMPFINALEFORNIT().doubleValue());
	  			impFinaleForniture = collaudoType.getIMPFINALEFORNIT().doubleValue();
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_fornit","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_fornit","null");
	  		}

	  		// SUB_TOTALE = W9COLL.IMP_FINALE_LAVORI +	W9COLL.IMP_FINALE_SERVIZI +	W9COLL.IMP_FINALE_FORNIT
	  		impSubTotale = impFinaleLavori + impFinaleForniture + impFinaleServizi;
	  		if (impSubTotale != 0) {
	  			sqlParamW9COLL.add(impSubTotale);
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_subtotale","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_subtotale","null");
	  		}
	  		if (collaudoType.isSetIMPFINALESECUR()) {
	  			sqlParamW9COLL.add(collaudoType.getIMPFINALESECUR().doubleValue());
	  			impFinaleSecur = collaudoType.getIMPFINALESECUR().doubleValue();
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_secur","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_finale_secur","null");
	  		}
	  		if (collaudoType.isSetIMPPROGETTAZIONE()) {
	  			sqlParamW9COLL.add(Double.parseDouble(collaudoType.getIMPPROGETTAZIONE()));
	  			impProgettazione = Double.parseDouble(collaudoType.getIMPPROGETTAZIONE());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_progettazione","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_progettazione","null");
	  		}
	  		
	  		// IMPL_COMPL_APPALTO = W9COLL.IMP_SUBTOTALE + W9COLL.IMP_FINALE_SECUR + W9COLL.IMP_PROGETTAZIONE
	  		impComplAppalto = impSubTotale + impFinaleSecur + impProgettazione;
	  		if (impComplAppalto != 0) {
	  			sqlParamW9COLL.add(impComplAppalto);
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_compl_appalto","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_compl_appalto","null");
	  		}
	  		if (collaudoType.isSetIMPDISPOSIZIONE()) {
	  			impDisposizione = Double.parseDouble(collaudoType.getIMPDISPOSIZIONE());
	  			sqlParamW9COLL.add(Double.parseDouble(collaudoType.getIMPDISPOSIZIONE()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_disposizione","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_disposizione","null");
	  		}

	  		// imp_compl_intervento = W9COLL.IMP_COMPL_APPALTO + W9COLL.IMP_DISPOSIZIONE
	  		impComplIntervento = impDisposizione + impComplAppalto;
	  		if (impComplIntervento != 0) {
	  			sqlParamW9COLL.add(impComplIntervento);
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_compl_intervento","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#imp_compl_intervento","null");
	  		}
	  		if (collaudoType.isSetLAVORIESTESI()) {
	  			sqlParamW9COLL.add(FlagSNType.S.equals(collaudoType.getLAVORIESTESI()) ? "1" : "2");
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#lavori_estesi","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#lavori_estesi","null");
	  		}
	  		if (collaudoType.isSetAMMIMPORTODEF()) {
	  			sqlParamW9COLL.add(collaudoType.getAMMIMPORTODEF().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_importo_def","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_importo_def","null");
	  		}
	  		if (collaudoType.isSetAMMNUMDEFINITE()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getAMMNUMDEFINITE()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_num_definite","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_num_definite","null");
	  		}
	  		if (collaudoType.isSetAMMNUMDADEF()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getAMMNUMDADEF()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_num_dadef","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_num_dadef","null");
	  		}
	  		if (collaudoType.isSetAMMIMPORTORICH()) {
	  			sqlParamW9COLL.add(collaudoType.getAMMIMPORTORICH().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_importo_rich","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#amm_importo_rich","null");
	  		}
	  		if (collaudoType.isSetARBIMPORTODEF()) {
	  			sqlParamW9COLL.add(collaudoType.getARBIMPORTODEF().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_importo_def","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_importo_def","null");
	  		}
	  		if (collaudoType.isSetARBNUMDEFINITE()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getARBNUMDEFINITE()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_num_definite","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_num_definite","null");
	  		}
	  		if (collaudoType.isSetARBNUMDADEF()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getARBNUMDADEF()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_num_dadef","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_num_dadef","null");
	  		}
	  		if (collaudoType.isSetARBIMPORTORICH()) {
	  			sqlParamW9COLL.add(collaudoType.getARBIMPORTORICH().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_importo_rich","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#arb_importo_rich","null");
	  		}
	  		if (collaudoType.isSetARBIMPORTODEF()) {
	  			sqlParamW9COLL.add(collaudoType.getARBIMPORTODEF().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_importo_def","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_importo_def","null");
	  		}
	  		if (collaudoType.isSetGIUNUMDEFINITE()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getGIUNUMDEFINITE()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_num_definite","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_num_definite","null");
	  		}
	  		if (collaudoType.isSetGIUNUMDADEF()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getGIUNUMDADEF()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_num_dadef","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_num_dadef","null");
	  		}
	  		if (collaudoType.isSetGIUIMPORTORICH()) {
	  			sqlParamW9COLL.add(collaudoType.getGIUIMPORTORICH().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_importo_rich","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#giu_importo_rich","null");
	  		}
	  		if (collaudoType.isSetTRAIMPORTODEF()) {
	  			sqlParamW9COLL.add(collaudoType.getTRAIMPORTODEF().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_importo_def","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_importo_def","null");
	  		}
	  		if (collaudoType.isSetTRANUMDEFINITE()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getTRANUMDEFINITE()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_num_definite","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_num_definite","null");
	  		}
	  		if (collaudoType.isSetTRANUMDADEF()) {
	  			sqlParamW9COLL.add(new Long(collaudoType.getTRANUMDADEF()));
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_num_dadef","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_num_dadef","null");
	  		}
	  		if (collaudoType.isSetTRAIMPORTORICH()) {
	  			sqlParamW9COLL.add(collaudoType.getTRAIMPORTORICH().doubleValue());
	  			sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_importo_rich","?");
				} else {
					sqlInsertW9COLL = StringUtils.replace(sqlInsertW9COLL,"#tra_importo_rich","null");
	  		}
	  		
	  		this.sqlManager.update(sqlInsertW9COLL, sqlParamW9COLL.toArray());
	  		this.istanziaIncarichiProfessionali(codGara, codLott, 1L, codUffInt, datiCollaudo.getIncaricatiArray(), arrayResponsabili);
  		}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda collaudo. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  private void istanziaRitardo(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, final SchedaCompletaType schedaCompleta)
  				throws SQLException, Exception {
  	try {
  		RitardiType datiRitardi = schedaCompleta.getDatiRitardi();
  		if (datiRitardi != null) {
  			RitardoType[] arrayRitardi = datiRitardi.getRitardoArray();
  			if (arrayRitardi != null && arrayRitardi.length > 0) {
  				
  				for (int i=0; i < arrayRitardi.length; i++) {
  					RitardoType ritardoType = arrayRitardi[i];
  					
  					String idSchedaLocale = null;
  		      String idSchedaSimog = null;
  		      if (ritardoType.isSetIDSCHEDALOCALE()) {
  		      	idSchedaLocale = ritardoType.getIDSCHEDALOCALE();
  		      }
  		      if (ritardoType.isSetIDSCHEDASIMOG()) {
  		      	idSchedaSimog = ritardoType.getIDSCHEDASIMOG();
  		      }
  		      
  					this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.ISTANZA_RECESSO), new Long(i+1), 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
  					
  					String sqlInsertW9RITA = "Insert into W9RITA (codgara,codlott,num_rita,num_appa,data_termine,durata_sosp,flag_ripresa,"
  							+ "flag_riserva,flag_tardiva,tipo_comun,"   // ritardo,
  							+ "data_consegna,motivo_sosp,data_ist_recesso,flag_accolta,importo_spese,importo_oneri) "
  							+ "values (?,?,?,?,?,?,?,?,?,?,#dataConsegna,#motivoSosp,#dataIstRecesso,#flagAccolta,#impOneri,#impSpese)";

  					List<Object> paramSqlW9RITA = new ArrayList<Object>();
  					paramSqlW9RITA.add(codGara);
  					paramSqlW9RITA.add(codLott);
  					paramSqlW9RITA.add(new Long(i+1));
  					paramSqlW9RITA.add(1l);
  					paramSqlW9RITA.add(this.convertiData(ritardoType.getDATATERMINE()));
  					paramSqlW9RITA.add(new Long(ritardoType.getDURATASOSP()));
  					paramSqlW9RITA.add(FlagSNType.S.equals(ritardoType.getFLAGRIPRESA()) ? "1" : "2");
  					paramSqlW9RITA.add("S".equals(ritardoType.getFLAGRISERVA()) ? "1" : "2");
  					paramSqlW9RITA.add(FlagSNType.S.equals(ritardoType.getFLAGTARDIVA()) ? "1" : "2");
  					paramSqlW9RITA.add(FlagTCType.R.equals(ritardoType.getTIPOCOMUN()) ? "R" : "S");
  					
  					if (ritardoType.isSetDATACONSEGNA()) {
  						paramSqlW9RITA.add(ritardoType.getDATACONSEGNA().getTime());
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#dataConsegna","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#dataConsegna","null");
  					}
  					if (ritardoType.isSetMOTIVOSOSP()) {
  						paramSqlW9RITA.add(Long.parseLong(ritardoType.getMOTIVOSOSP()));
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#motivoSosp","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#motivoSosp","null");
  					}
  					if (ritardoType.isSetDATAISTRECESSO()) {
  						paramSqlW9RITA.add(ritardoType.getDATAISTRECESSO().getTime());
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#dataIstRecesso","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#dataIstRecesso","null");
  					}
  					if (ritardoType.isSetFLAGACCOLTA()) {
  						paramSqlW9RITA.add(FlagSNType.S.equals(ritardoType.getFLAGACCOLTA()) ? "1" : "2");
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#flagAccolta","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#flagAccolta","null");
  					}
  					if (ritardoType.isSetIMPORTOONERI()) {
  						paramSqlW9RITA.add(ritardoType.getIMPORTOONERI().doubleValue());
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#impOneri","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#impOneri","null");
  					}
  					if (ritardoType.isSetIMPORTOSPESE()) {
  						paramSqlW9RITA.add(ritardoType.getIMPORTOSPESE().doubleValue());
  						sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#impSpese","?");
    				} else {
    					sqlInsertW9RITA = StringUtils.replace(sqlInsertW9RITA,"#impSpese","null");
  					}
  					
  					this.sqlManager.update(sqlInsertW9RITA, paramSqlW9RITA.toArray());
  				}
  			}
  		}
  	} catch (Exception e) {
  		logger.error("Errore in consulta gara, creazione scheda ritardo. codgara ="
  				+ codGara + " codLotto =" + codLott, e);
  		throw e;
  	}
  }
  
  
  private void istanziaAccordBonario(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza,
  		final SchedaCompletaType schedaCompleta) throws SQLException, Exception {
  	
  	logger.debug("istanziaAccordBonario: inizio metodo");
  	
  	try {
  		
  		AccordiBonariType accordiBonari = schedaCompleta.getDatiAccordi();
  		if (accordiBonari != null && accordiBonari.getAccordoBonarioArray() != null && accordiBonari.getAccordoBonarioArray().length > 0) {
  			for (int i=0; i < accordiBonari.getAccordoBonarioArray().length; i++) {
  				AccordoBonarioType accordoBonario = accordiBonari.getAccordoBonarioArray()[i];
  	  		
  				String idSchedaLocale = null;
  	      String idSchedaSimog = null;
  	      if (accordoBonario.isSetIDSCHEDALOCALE()) {
  	      	idSchedaLocale = accordoBonario.getIDSCHEDALOCALE();
  	      }
  	      if (accordoBonario.isSetIDSCHEDASIMOG()) {
  	      	idSchedaSimog = accordoBonario.getIDSCHEDASIMOG();
  	      }
  				this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.SOSPENSIONE_CONTRATTO), new Long(i+1), 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);

  				String sqlInsertW9ACCO = "Insert into W9ACCO (codgara,codlott,num_acco,num_appa,data_accordo,num_riserve,oneri_derivanti) "
  						+ "values (?,?,?,?,?,?,#oneriDerivanti)";
  				
  				List<Object> paramSqlW9ACCO = new ArrayList<Object>();
  				paramSqlW9ACCO.add(codGara);
  				paramSqlW9ACCO.add(codLott);
  				paramSqlW9ACCO.add(new Long(i+1));
  				paramSqlW9ACCO.add(1L);
  				paramSqlW9ACCO.add(accordoBonario.getDATAACCORDO().getTime());
  				paramSqlW9ACCO.add(new Long(accordoBonario.getNUMRISERVE()));
  				
  				if (accordoBonario.isSetONERIDERIVANTI()) {
  					paramSqlW9ACCO.add(accordoBonario.getONERIDERIVANTI().doubleValue());
  					sqlInsertW9ACCO = StringUtils.replace(sqlInsertW9ACCO,"#oneriDerivanti","?");
  				} else {
  					sqlInsertW9ACCO = StringUtils.replace(sqlInsertW9ACCO,"#oneriDerivanti","null");
  				}
  				
  				this.sqlManager.update(sqlInsertW9ACCO, paramSqlW9ACCO.toArray());
  			}
  		}
  	} catch (Exception e) {
  		logger.error("Errore in consulta gara, creazione scheda accordo bonario. codgara ="
  				+ codGara + " codLotto =" + codLott, e);
  		throw e;
  	}
  	
  	logger.debug("istanziaAccordBonario: fine metodo");
  }
  
  
  private void istanziaSospensioni(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, final SchedaCompletaType schedaCompleta)
			throws SQLException, Exception {
		try {
			
			SospensioniType sospensioniType = schedaCompleta.getDatiSospensioni();
			if (sospensioniType != null && sospensioniType.getSospensioneArray() != null && sospensioniType.getSospensioneArray().length > 0) {
				for (int i=0; i < sospensioniType.getSospensioneArray().length; i++) {
					SospensioneType sospensione = sospensioniType.getSospensioneArray()[i];
					
		  		String idSchedaLocale = null;
		      String idSchedaSimog = null;
		      if (sospensione.isSetIDSCHEDALOCALE()) {
		      	idSchedaLocale = sospensione.getIDSCHEDALOCALE();
		      }
		      if (sospensione.isSetIDSCHEDASIMOG()) {
		      	idSchedaSimog = sospensione.getIDSCHEDASIMOG();
		      }
					this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.SOSPENSIONE_CONTRATTO), new Long(i+1), 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
					
					String sqlInsertW9SOSP = "insert into w9sosp (codgara,codlott,num_sosp,num_appa,data_verb_sosp,flag_supero_tempo,"
							+ "flag_riserve,flag_verbale,id_motivo_sosp,data_verb_ripr) values (?,?,?,?,?,?,?,?,?,#dataVerbRipr)";
					List<Object> paramSqlW9SOSP = new ArrayList<Object>();
  				paramSqlW9SOSP.add(codGara);
  				paramSqlW9SOSP.add(codLott);
  				paramSqlW9SOSP.add(new Long(i+1));
  				paramSqlW9SOSP.add(1L);
  				paramSqlW9SOSP.add(sospensione.getDATAVERBSOSP().getTime());
  				paramSqlW9SOSP.add(FlagSNType.S.equals(sospensione.getFLAGSUPEROTEMPO()) ? "1" : "2");
  				paramSqlW9SOSP.add(FlagSNType.S.equals(sospensione.getFLAGRISERVE()) ? "1" : "2");
  				paramSqlW9SOSP.add(FlagSNType.S.equals(sospensione.getFLAGVERBALE()) ? "1" : "2");
  				paramSqlW9SOSP.add(new Long(sospensione.getIDMOTIVOSOSP()));
  				
  				if (sospensione.isSetDATAVERBRIPR()) {
  					paramSqlW9SOSP.add(sospensione.getDATAVERBRIPR().getTime());
  					sqlInsertW9SOSP = StringUtils.replace(sqlInsertW9SOSP,"#dataVerbRipr","?");
  				} else {
  					sqlInsertW9SOSP = StringUtils.replace(sqlInsertW9SOSP,"#dataVerbRipr","null");
  				}
					
  				this.sqlManager.update(sqlInsertW9SOSP,paramSqlW9SOSP.toArray());
				}
			}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda sospensione. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  private void istanziaVarianti(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, 
  		final SchedaCompletaType schedaCompleta) throws SQLException, Exception {
		try {
			
			VariantiType datiVariantiType = schedaCompleta.getDatiVarianti();
			if (datiVariantiType != null && datiVariantiType.getVarianteArray() != null && datiVariantiType.getVarianteArray().length > 0) {
				for (int i=0; i < datiVariantiType.getVarianteArray().length; i++) {
					VarianteType varianteType = datiVariantiType.getVarianteArray(i);
					RecVarianteType recVariante = varianteType.getVariante();
					RecMotivoVarType[] arrayRecMotiviVarianti = varianteType.getMotiviArray();
					
		  		String idSchedaLocale = null;
		      String idSchedaSimog = null;
		      if (recVariante.isSetIDSCHEDALOCALE()) {
		      	idSchedaLocale = recVariante.getIDSCHEDALOCALE();
		      }
		      if (recVariante.isSetIDSCHEDASIMOG()) {
		      	idSchedaSimog = recVariante.getIDSCHEDASIMOG();
		      }
		      
					this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.VARIANTE_CONTRATTO), new Long(i+1), 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
					
					String sqlInsertW9VARI = "Insert into W9VARI (codgara,codlott,num_vari,num_appa,data_verb_appr,altre_motivazioni,data_atto_aggiuntivo,"
							+ "num_giorni_proroga,imp_ridet_lavori,imp_ridet_servizi,imp_ridet_fornit,imp_subtotale,imp_sicurezza,imp_progettazione,imp_non_assog,"
							+ "imp_compl_appalto,imp_disposizione,imp_compl_intervento,cig_nuova_proc) values "
							+ "(?,?,?,?,?,#altreMotivazioni,#dataAttoAggiuntivo,#numGiorniProroga,#importoRideterminatoLavori,#importoRideterminatoServizi,"
							+ "#importoRideterminatoForniture,#importoSubtotale,#importoSicurezza,#importoProgettazione,#importoNonAssog,#importoComplAppalto,"
							+ "#importoDisposizione,#importoComplIntervento,#cigNuovaProc)";   // ,flag_variante,quinto_obbligo,flag_importi,flag_sicurezza
					
					List<Object> paramSqlW9VARI = new ArrayList<Object>();
					paramSqlW9VARI.add(codGara);
					paramSqlW9VARI.add(codLott);
					paramSqlW9VARI.add(new Long(i+1));
					paramSqlW9VARI.add(new Long(1));
					paramSqlW9VARI.add(recVariante.getDATAVERBAPPR().getTime());
					
		  		double impRideterLavori = 0;
		  		double impRideterForniture = 0;
		  		double impRideterServizi = 0;
		  		double impSubTotale = 0;
		  		double impSicurezza = 0;
		  		double impProgettazione = 0;
		  		double impComplAppalto = 0;
		  		double impDisposizione = 0;
		  		double impUltSomme = 0;
		  		double impComplIntervento = 0;
					
					if (recVariante.isSetALTREMOTIVAZIONI()) {
						paramSqlW9VARI.add(StringUtils.substring(recVariante.getALTREMOTIVAZIONI(), 0, 995));
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#altreMotivazioni","?");
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#altreMotivazioni","null");
					}
					if (recVariante.isSetDATAATTOAGGIUNTIVO()) {
						paramSqlW9VARI.add(recVariante.getDATAATTOAGGIUNTIVO().getTime());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#dataAttoAggiuntivo","?");
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#dataAttoAggiuntivo","null");
					}
					if (recVariante.isSetNUMGIORNIPROROGA()) {
						paramSqlW9VARI.add(new Long(recVariante.getNUMGIORNIPROROGA()));
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#numGiorniProroga","?");
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#numGiorniProroga","null");
					}
					if (recVariante.isSetIMPRIDETLAVORI()) {
						paramSqlW9VARI.add(recVariante.getIMPRIDETLAVORI().doubleValue());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoLavori","?");
						impRideterLavori = recVariante.getIMPRIDETLAVORI().doubleValue();
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoLavori","null");
					}
					if (recVariante.isSetIMPRIDETSERVIZI()) {
						paramSqlW9VARI.add(recVariante.getIMPRIDETSERVIZI().doubleValue());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoServizi","?");
						impRideterServizi = recVariante.getIMPRIDETSERVIZI().doubleValue();
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoServizi","null");
					}
					if (recVariante.isSetIMPRIDETFORNIT()) {
						paramSqlW9VARI.add(recVariante.getIMPRIDETFORNIT().doubleValue());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoForniture","?");
						impRideterForniture = recVariante.getIMPRIDETFORNIT().doubleValue();
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoRideterminatoForniture","null");
					}
					
					impSubTotale = impRideterLavori + impRideterServizi + impRideterForniture;
					paramSqlW9VARI.add(impSubTotale);
					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoSubtotale","?");
					
					if (recVariante.isSetIMPSICUREZZA()) {
						paramSqlW9VARI.add(recVariante.getIMPSICUREZZA().doubleValue());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoSicurezza","?");
						impSicurezza = recVariante.getIMPSICUREZZA().doubleValue();
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoSicurezza","null");
					}
					if (recVariante.isSetIMPPROGETTAZIONE()) {
						paramSqlW9VARI.add(Double.parseDouble(recVariante.getIMPPROGETTAZIONE()));
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoProgettazione","?");
						impProgettazione = Double.parseDouble(recVariante.getIMPPROGETTAZIONE());
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoProgettazione","null");
					}
					if (recVariante.isSetULTERIORISOMME()) {
						paramSqlW9VARI.add(recVariante.getULTERIORISOMME().doubleValue());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoNonAssog","?");
						impUltSomme = Double.parseDouble(recVariante.getIMPDISPOSIZIONE());
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoNonAssog","null");
					}
					
					impComplAppalto = impSubTotale + impSicurezza + impProgettazione + impUltSomme;
					paramSqlW9VARI.add(impComplAppalto);
					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoComplAppalto","?");

					if (recVariante.isSetIMPDISPOSIZIONE()) {
						paramSqlW9VARI.add(Double.parseDouble(recVariante.getIMPDISPOSIZIONE()));
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoDisposizione","?");
						impDisposizione = Double.parseDouble(recVariante.getIMPDISPOSIZIONE());
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoDisposizione","null");
					}					
					
					impComplIntervento = impComplAppalto + impDisposizione;
					paramSqlW9VARI.add(impComplIntervento);
					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#importoComplIntervento","?");
					
					if (recVariante.isSetCIGPROCEDURA()) {
						paramSqlW9VARI.add(recVariante.getCIGPROCEDURA());
						sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#cigNuovaProc","?");
  				} else {
  					sqlInsertW9VARI = StringUtils.replace(sqlInsertW9VARI,"#cigNuovaProc","null");
					}
					this.sqlManager.update(sqlInsertW9VARI, paramSqlW9VARI.toArray());
					
					if (arrayRecMotiviVarianti != null && arrayRecMotiviVarianti.length > 0) {
						for (int j=0; j < arrayRecMotiviVarianti.length; j++) {
							this.sqlManager.update("Insert into W9MOTI (codgara,codlott,num_vari,num_moti,id_motivo_var) values (?,?,?,?,?)", 
									new Object[] { codGara, codLott, new Long(i+1), new Long(j+1), new Long(arrayRecMotiviVarianti[j].getIDMOTIVOVAR()) }) ;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda variante. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  private void istanziaSubappalti(final long codGara, final long codLott, final String codUffInt, final boolean isCfgVigilanza, final SchedaCompletaType schedaCompleta, 
  		final AggiudicatarioType[] arrayAggiudicatari, final ResponsabileType[] arrayResponsabili)
  				throws SQLException, Exception {
  	try {
  		SubappaltiType subappalti = schedaCompleta.getDatiSubappalti();
  		if (subappalti != null && subappalti.getSubappaltoArray() != null && subappalti.getSubappaltoArray().length > 0) {
  			
  			SubappaltoType[] arraySubappalti = subappalti.getSubappaltoArray();
  			
  			for (int i=0; i < arraySubappalti.length; i++) {
  				SubappaltoType subappaltoType = arraySubappalti[i];
  				
		  		String idSchedaLocale = null;
		      String idSchedaSimog = null;
		      if (subappaltoType.isSetIDSCHEDALOCALE()) {
		      	idSchedaLocale = subappaltoType.getIDSCHEDALOCALE();
		      }
		      if (subappaltoType.isSetIDSCHEDASIMOG()) {
		      	idSchedaSimog = subappaltoType.getIDSCHEDASIMOG();
		      }
		      
					this.istanziaFaseFlusso(codGara, codLott, new Long(CostantiW9.SUBAPPALTO), new Long(i+1), 1L, idSchedaLocale, idSchedaSimog, codUffInt, isCfgVigilanza);
					
					String sqlInsertW9SUBA = "insert into W9SUBA (codgara,codlott,num_suba,num_appa,id_cpv,codimp,importo_presunto,data_autorizzazione,"
							+ "oggetto_subappalto,importo_effettivo,id_categoria,codimp_aggiudicatrice) values (?,?,?,?,?,?,?,#data_autorizzazione,"
							+ "#oggetto_subappalto,#importo_effettivo,#id_categoria,#codimp_aggiudicatrice)";
					
					List<Object> paramSqlW9SUBA = new ArrayList<Object>();
					paramSqlW9SUBA.add(codGara);
					paramSqlW9SUBA.add(codLott);
					paramSqlW9SUBA.add(new Long(i+1));
					paramSqlW9SUBA.add(new Long(1));
					paramSqlW9SUBA.add(subappaltoType.getIDCPV());
					
					String cfImpresa = subappaltoType.getCFDITTA();
	        String codImp = (String) this.sqlManager.getObject("select CODIMP from IMPR where UPPER(CFIMP)=? and CGENIMP=?",
	            new Object[] { cfImpresa.toUpperCase(), codUffInt });
	        if (StringUtils.isEmpty(codImp)) {
	          codImp = this.gestioneImpresa(codUffInt, cfImpresa, null);
	        }
	        paramSqlW9SUBA.add(codImp);
	        paramSqlW9SUBA.add(subappaltoType.getIMPORTOPRESUNTO().doubleValue());
	        
	        if (subappaltoType.isSetDATAAUTORIZZAZIONE()) {
	        	paramSqlW9SUBA.add(subappaltoType.getDATAAUTORIZZAZIONE().getTime());
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#data_autorizzazione", "?");
	        } else {
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#data_autorizzazione", "null");
	        }
	        if (subappaltoType.isSetOGGETTOSUBAPPALTO()) {
	        	paramSqlW9SUBA.add(subappaltoType.getOGGETTOSUBAPPALTO());
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#oggetto_subappalto","?");
	        } else {
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#oggetto_subappalto","null");
	        }
	        if (subappaltoType.isSetIMPORTOEFFETTIVO()) {
	        	paramSqlW9SUBA.add(subappaltoType.getIMPORTOEFFETTIVO().doubleValue());
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#importo_effettivo","?");
	        } else {
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#importo_effettivo","null");
	        }
	        if (subappaltoType.isSetIDCATEGORIA()) {
	        	String categoria = (String) this.sqlManager.getObject(
	              "select CODSITAT from W9CODICI_AVCP where CODAVCP = ? and TABCOD = 'W3z03' ",
	                new Object[] { subappaltoType.getIDCATEGORIA() });
	            // se manca corrispondenza inserisco il codice non decodificato
	            if (StringUtils.isEmpty(categoria)) {
	              categoria = subappaltoType.getIDCATEGORIA();
	            }
	        	
	        	paramSqlW9SUBA.add(categoria);
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#id_categoria","?");
	        } else {
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#id_categoria","null");
	        }

	        if (subappaltoType.isSetCODICEFISCALEAGGIUDICATARIO()) {
	        	String cfImpresaAggiu = subappaltoType.getCODICEFISCALEAGGIUDICATARIO();
	        	String codImpAggiu = (String) this.sqlManager.getObject("select CODIMP from IMPR where UPPER(CFIMP)=? and CGENIMP=?",
		            new Object[] { cfImpresa.toUpperCase(), codUffInt });
	        	if (StringUtils.isEmpty(codImpAggiu)) {
	        		codImpAggiu = this.gestioneImpresa(codUffInt, cfImpresaAggiu, null);
		        }
	        	paramSqlW9SUBA.add(codImpAggiu);
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#codimp_aggiudicatrice","?");
	        } else {
	        	sqlInsertW9SUBA = StringUtils.replace(sqlInsertW9SUBA, "#codimp_aggiudicatrice","null");
	        }
	        
					this.sqlManager.update(sqlInsertW9SUBA, paramSqlW9SUBA.toArray());
					
  			}
  		}
  	} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione scheda subappalti. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  /**
   * Inserimento dell'impresa e del legale rappresentante (IMPR - IMPLEG e TEIM).
   * 
   * @param codein
   * @param aggiudicatari
   * @param cfImpresa
   * @throws GestoreException
   * @throws SQLException
   */
  private String gestioneImpresa(String codein, String cfImpresa, AggiudicatarioType[] aggiudicatari)
  		throws GestoreException, SQLException {
  	
    boolean trovato = false;
    String codiceImpresa = null;
    
    if (aggiudicatari != null && aggiudicatari.length > 0) {
      for (int j=0; j < aggiudicatari.length && !trovato; j++) {
        if (cfImpresa.equalsIgnoreCase(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO().trim())) {
          List<DataColumn> listaCampi = new ArrayList<DataColumn>(); 
          DataColumn codimp = new DataColumn("IMPR.CODIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, null));
          listaCampi.add(codimp);
          
          DataColumn cgenimp = new DataColumn("IMPR.CGENIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, codein));
          listaCampi.add(cgenimp);
          
          if (StringUtils.isNotEmpty(aggiudicatari[j].getDENOMINAZIONE().trim())) {
            DataColumn nomimp = new DataColumn("IMPR.NOMIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getDENOMINAZIONE().trim(),0,60)));
            DataColumn nomest = new DataColumn("IMPR.NOMEST", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getDENOMINAZIONE().trim(),0,2000)));
            listaCampi.add(nomimp);
            listaCampi.add(nomest);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO().trim())) {
            DataColumn cfimp = new DataColumn("IMPR.CFIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCODICEFISCALEAGGIUDICATARIO(),0,16)));
            listaCampi.add(cfimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getPARTITAIVA().trim())) {
            DataColumn pivimp = new DataColumn("IMPR.PIVIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getPARTITAIVA().trim(),0,16)));
            listaCampi.add(pivimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCITTA().trim())) {
            DataColumn locimp = new DataColumn("IMPR.LOCIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCITTA().trim(),0,60)));
            listaCampi.add(locimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCIVICO().trim())) {
            DataColumn nciimp = new DataColumn("IMPR.NCIIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCIVICO().trim(),0,10)));
            listaCampi.add(nciimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getINDIRIZZO().trim())) {
            DataColumn indimp = new DataColumn("IMPR.INDIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getINDIRIZZO().trim(),0,60)));
            listaCampi.add(indimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCAP().trim())) {
            DataColumn capimp = new DataColumn("IMPR.CAPIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getCAP().trim(),0,5)));
            listaCampi.add(capimp);
          }
          if (StringUtils.isNotEmpty(aggiudicatari[j].getPROVINCIA().trim())) {
            DataColumn proimp = new DataColumn("IMPR.PROIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, 
                StringUtils.substring(aggiudicatari[j].getPROVINCIA().trim(),0,2)));
            listaCampi.add(proimp);
          }
          
          if (StringUtils.isNotEmpty(aggiudicatari[j].getCODICESTATO().trim())) {
            String statoEstero = "";
            try {
              statoEstero = (String) sqlManager.getObject("select tab2d1 from tab2 where tab2cod='W3z12' and tab2tip=?",
                  new Object[] { aggiudicatari[j].getCODICESTATO().trim() });
            } catch (SQLException e) {
              throw new GestoreException("Errore nella lettura della nazione dell'impresa", "getStatoEstero", e);
            }
            if (StringUtils.isNotEmpty(statoEstero)) {
              DataColumn nazimp = new DataColumn("IMPR.NAZIMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 
                  new Long(statoEstero)));
              listaCampi.add(nazimp);
            }
          } else {
            DataColumn nazimp = new DataColumn("IMPR.NAZIMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1)));
            listaCampi.add(nazimp);
          }
  
          DataColumnContainer dccImpresa = new DataColumnContainer(listaCampi.toArray(new DataColumn[0]));
          dccImpresa.getColumn("IMPR.CODIMP").setChiave(true);
          String strCodImp = this.geneManager.calcolaCodificaAutomatica("IMPR", "CODIMP");
          dccImpresa.setValue("IMPR.CODIMP", strCodImp);
          dccImpresa.insert("IMPR", this.sqlManager);
          
          codiceImpresa = new String(strCodImp);
  
          // Legale rappresentante
          DataColumn codtim = new DataColumn("TEIM.CODTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn nometim = null;
          DataColumn cogtim  = null;
          DataColumn nomtim  = null;
          String nomeCognome = null;
          
          if (StringUtils.isNotEmpty(aggiudicatari[j].getNOME().trim())) {
            nomeCognome = aggiudicatari[j].getNOME().trim();
            nometim = new DataColumn("TEIM.NOMETIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, aggiudicatari[j].getNOME().trim()));
            if (StringUtils.isNotEmpty(aggiudicatari[j].getCOGNOME().trim())) {
              nomeCognome = nomeCognome.concat(" ").concat(aggiudicatari[j].getCOGNOME().trim());
              cogtim = new DataColumn("TEIM.COGTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, aggiudicatari[j].getCOGNOME().trim()));
            } 
          } if (StringUtils.isNotEmpty(aggiudicatari[j].getCOGNOME().trim())) {
            cogtim = new DataColumn("TEIM.COGTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, aggiudicatari[j].getCOGNOME().trim()));
            nomeCognome = aggiudicatari[j].getCOGNOME().trim();
          }
          
          if (StringUtils.isNotEmpty(nomeCognome)) {
            nomtim = new DataColumn("TEIM.NOMTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, nomeCognome));
          }
          DataColumn cftim = new DataColumn("TEIM.CFTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, aggiudicatari[j].getCFRAPPRESENTANTE().trim()));
          DataColumn cgentim = new DataColumn("TEIM.CGENTIM", new JdbcParametro(JdbcParametro.TIPO_TESTO, codein));
          DataColumnContainer dccTecnicoImpresa = new DataColumnContainer(new DataColumn[] { codtim, cogtim, nometim, nomtim, cftim, cgentim });
          dccTecnicoImpresa.getColumn("TEIM.CODTIM").setChiave(true);
          dccTecnicoImpresa.insert("TEIM", this.sqlManager);
          
          DataColumn codimp2 = new DataColumn("IMPLEG.CODIMP2", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn codleg  = new DataColumn("IMPLEG.CODLEG", new JdbcParametro(JdbcParametro.TIPO_TESTO, strCodImp));
          DataColumn nomleg  = new DataColumn("IMPLEG.NOMLEG", new JdbcParametro(JdbcParametro.TIPO_TESTO, nomeCognome));

          int idImpLeg = this.genChiaviManager.getNextId("IMPLEG");
          DataColumn id = new DataColumn("IMPLEG.ID", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(idImpLeg)));
          id.setChiave(true);
          
          DataColumnContainer dccLegaleRapp = new DataColumnContainer(new DataColumn[] { id, codimp2, codleg, nomleg } );
          dccLegaleRapp.insert("IMPLEG", this.sqlManager);
          
          trovato = true;
        }
      }
    } else {
    	List<DataColumn> listaCampi = new ArrayList<DataColumn>(); 
    	DataColumn codimp = new DataColumn("IMPR.CODIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, null));
    	listaCampi.add(codimp);

    	DataColumn nomimp = new DataColumn("IMPR.NOMIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, cfImpresa));
    	DataColumn nomest = new DataColumn("IMPR.NOMEST", new JdbcParametro(JdbcParametro.TIPO_TESTO, cfImpresa));
    	listaCampi.add(nomimp);
    	listaCampi.add(nomest);
    	DataColumn cfimp = new DataColumn("IMPR.CFIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO, cfImpresa));
   		listaCampi.add(cfimp);
    	 
    	DataColumnContainer dccImpresa = new DataColumnContainer(listaCampi.toArray(new DataColumn[0]));
    	dccImpresa.getColumn("IMPR.CODIMP").setChiave(true);
    	String strCodImp = this.geneManager.calcolaCodificaAutomatica("IMPR", "CODIMP");
    	dccImpresa.setValue("IMPR.CODIMP", strCodImp);
    	dccImpresa.insert("IMPR", this.sqlManager);

    	codiceImpresa = new String(strCodImp);
    	
    	this.sqlManager.update("insert into teim (codtim,nometim,cogtim,cftim) values (?,?,?,?)", 
    			new Object[] { codiceImpresa, "", "", cfImpresa });
    	int id = this.genChiaviManager.getNextId("IMPLEG");
    	this.sqlManager.update("insert into impleg (id,codimp2,codleg,nomleg) values (?,?,?,?)", 
    			new Object[] { new Long(id), codiceImpresa, codiceImpresa, "Legale rapprsentante impresa " + cfImpresa });
    }
    
    return codiceImpresa;
  }
  
  
  private void istanziaPubblicita(final long codGara, final long codLott, final PubblicazioneType pubblicazione) throws SQLException, Exception {
  	try {
	  	String sqlInsertW9PUES = "Insert into W9PUES (codgara,codlott,num_iniz,num_pues,data_guce,data_guri,data_albo,quotidiani_naz,"
	  			+ "quotidiani_reg, profilo_committente, sito_ministero_inf_trasp,sito_osservatorio_cp) values (?,?,?,?,#data_guce,#data_guri,#data_albo,"
	  			+ "#quotidiani_naz,#quotidiani_reg,#profilo_committente,#sito_ministero_inf_trasp,#sito_osservatorio_cp)";
	  	List<Object> paramSqlW9PUES = new ArrayList<Object>();
	  	
	  	paramSqlW9PUES.add(codGara);
	  	paramSqlW9PUES.add(codLott);
	  	paramSqlW9PUES.add(1L);
	  	paramSqlW9PUES.add(1L);
	  	
	  	if (pubblicazione.isSetDATAGUCE()) {
	  		paramSqlW9PUES.add(pubblicazione.getDATAGUCE().getTime());
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_guce","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_guce","null");
	  	}
	  	if (pubblicazione.isSetDATAGURI()) {
	  		paramSqlW9PUES.add(pubblicazione.getDATAGURI().getTime());
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_guri","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_guri","null");
	  	}
	  	if (pubblicazione.isSetDATAALBO()) {
	  		paramSqlW9PUES.add(pubblicazione.getDATAALBO().getTime());
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_albo","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#data_albo","null");
	  	}
	  	if (pubblicazione.isSetQUOTIDIANINAZ()) {
	  		paramSqlW9PUES.add(new Long(pubblicazione.getQUOTIDIANINAZ()));
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#quotidiani_naz","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#quotidiani_naz","null");
	  	}
	  	if (pubblicazione.isSetQUOTIDIANIREG()) {
	  		paramSqlW9PUES.add(new Long(pubblicazione.getQUOTIDIANIREG()));
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#quotidiani_reg","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#quotidiani_reg","null");
	  	}
	  	if (pubblicazione.isSetPROFILOCOMMITTENTE()) {
	  		paramSqlW9PUES.add(FlagSNType.S.equals(pubblicazione.getPROFILOCOMMITTENTE()) ? "1" : "2");
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#profilo_committente","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#profilo_committente","null");
	  	}
	  	if (pubblicazione.isSetSITOMINISTEROINFTRASP()) {
	  		paramSqlW9PUES.add(FlagSNType.S.equals(pubblicazione.getSITOMINISTEROINFTRASP()) ? "1" : "2");
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#sito_ministero_inf_trasp","?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#sito_ministero_inf_trasp","null");
	  	}
	  	if (pubblicazione.isSetSITOOSSERVATORIOCP()) {
	  		paramSqlW9PUES.add(FlagSNType.S.equals(pubblicazione.getSITOOSSERVATORIOCP()) ? "1" : "2");
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#sito_osservatorio_cp", "?");
	  	} else {
	  		sqlInsertW9PUES = StringUtils.replace(sqlInsertW9PUES, "#sito_osservatorio_cp","null");
	  	}
	  	
	  	this.sqlManager.update(sqlInsertW9PUES, paramSqlW9PUES.toArray());
	  	
		} catch (Exception e) {
			logger.error("Errore in consulta gara, creazione W9PUES per la scheda iniziale o la stipula accordo quadro. codgara ="
					+ codGara + " codLotto =" + codLott, e);
			throw e;
		}
  }
  
  
  
  private Date convertiData(String data) {
  	Date date = null;
  	if (StringUtils.isNotEmpty(data)) {
  		try {
				date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(data);
			} catch (ParseException e) {
				logger.error("Errore nel parsing della stringa ".concat(data));
			}
  	}
  	return date;
  }
  
  /**
   * Ritorna il codtec per l'incaricato professionale (se non esiste, lo crea)
   * 
   * @param codiceStazAppaltante
   * @param responsabile
   * @return
   * @throws GestoreException
   * @throws SQLException
   */
  private String getTecnico(String codiceStazAppaltante, 
      ResponsabileType responsabile) throws GestoreException, SQLException {

    String codiceChiaveTecnico = "";
    
    long numeroTecnicoByCodFisStazApp = this.geneManager.countOccorrenze("TECNI", " CGENTEI=? and UPPER(CFTEC)=?",
        new Object[]{ codiceStazAppaltante, responsabile.getCODICEFISCALERESPONSABILE().toUpperCase() });
    
    if (numeroTecnicoByCodFisStazApp == 1) {
      String pkTecni = (String) this.sqlManager.getObject(
          "select CODTEC from TECNI where CGENTEI=? and UPPER(CFTEC)=?",
          new Object[]{ codiceStazAppaltante, responsabile.getCODICEFISCALERESPONSABILE().toUpperCase()});
      codiceChiaveTecnico = pkTecni;
    } else {
      // Non esiste in TECNI il tecnico indicato nell'XML. Si procede con la creazione
      // dell'occorrenze e associazione dello stesso alla Stazione Appaltante e
      // all'utente di USRSYS che ha fatto accesso al WS.
      synchronized (codiceChiaveTecnico) {
          
        String pkTecni = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
        codiceChiaveTecnico = pkTecni;
          
        DataColumn codiceTecnico = new DataColumn("TECNI.CODTEC",
            new JdbcParametro(JdbcParametro.TIPO_TESTO, pkTecni));
        DataColumn uffint = new DataColumn("TECNI.CGENTEI", new JdbcParametro(
            JdbcParametro.TIPO_TESTO, codiceStazAppaltante));
        DataColumn codiceFiscale = new DataColumn("TECNI.CFTEC", new JdbcParametro(
            JdbcParametro.TIPO_TESTO, responsabile.getCODICEFISCALERESPONSABILE().toUpperCase()));

        DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] { codiceTecnico, uffint, codiceFiscale });
        
        String nomeTecnico = null;
        dcc.addColumn("TECNI.COGTEI", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(responsabile.getCOGNOME(), 0, 40)));
          nomeTecnico = responsabile.getCOGNOME();
        dcc.addColumn("TECNI.NOMETEI", new JdbcParametro(JdbcParametro.TIPO_TESTO, StringUtils.substring(responsabile.getNOME(), 0, 20)));
        if (StringUtils.isNotEmpty(nomeTecnico)) {
          nomeTecnico += " " + responsabile.getNOME();
        } else {
          nomeTecnico = responsabile.getNOME();
        }
        dcc.addColumn("TECNI.NOMTEC", new JdbcParametro(JdbcParametro.TIPO_TESTO, nomeTecnico));
        dcc.addColumn("TECNI.INDTEC", JdbcParametro.TIPO_TESTO, StringUtils.substring(responsabile.getINDIRIZZO(), 0, 55));
        dcc.addColumn("TECNI.NCITEC", JdbcParametro.TIPO_TESTO, responsabile.getCAP());

        dcc.addColumn("TECNI.TELTEC", JdbcParametro.TIPO_TESTO, responsabile.getTELEFONO());
        dcc.addColumn("TECNI.FAXTEC", JdbcParametro.TIPO_TESTO, responsabile.getFAX());
        dcc.addColumn("TECNI.EMATEC", JdbcParametro.TIPO_TESTO, responsabile.getEMAIL());

        dcc.insert("TECNI", this.sqlManager);
      }
    }
    
    if (StringUtils.isNotEmpty(codiceChiaveTecnico)) {
      return codiceChiaveTecnico;
    } else {
      return null;
    }
  }
  

  /*private boolean hasEsito(SchedaType scheda) {
		return scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getDatiComuni() != null
				&& scheda.getDatiScheda().getDatiComuni().isSetESITOPROCEDURA();
	}*/

	public boolean hasAggiudicazione(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetAggiudicazione();
	}

	private boolean hasAggiudicazioneSottosoglia(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetSottosoglia();
	}

	private boolean hasAggiudicazioneEsclusa(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetEscluso();
	}

	private boolean hasAdesione(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetAdesione();
	}

	private boolean hasInizio(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiInizio();
	}

	private boolean hasStipula(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiStipula();
	}

	private boolean hasAvanzamento(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiAvanzamenti();
	}

	private boolean hasConclusione(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiConclusione();
	}

	private boolean hasCollaudo(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiCollaudo();
	}

	private boolean hasRitardo(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiRitardi();
	}

	private boolean hasAccordoBonario(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiAccordi();
	}

	private boolean hasSospensioni(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiSospensioni();
	}

	private boolean hasVarianti(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiVarianti();
	}

	private boolean hasSubappalti(SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.isSetDatiSubappalti();
	}
  
  // Fine check
  
}
