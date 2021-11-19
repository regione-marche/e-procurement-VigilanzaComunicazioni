package it.eldasoft.w9.bl.sa;

import it.avlp.simog.massload.xmlbeans.Collaborazione;
import it.avlp.simog.massload.xmlbeans.ConsultaGaraResponseDocument;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.eldasoft.gene.bl.GenChiaviManager;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.GaraDelegataManager;
import it.eldasoft.w9.bl.RichiestaIdGaraCigManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiSimog;
import it.eldasoft.w9.common.bean.GaraSABean;
import it.eldasoft.w9.common.bean.MigrazioneBean;
import it.eldasoft.w9.common.bean.PresaInCaricoGaraDelegataBean;
import it.toscana.rete.rfc.sitatsa.client.ResponsePresaInCaricoGaraDelegata;
import it.toscana.rete.rfc.sitatsa.client.WsOsservatorioProxy;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.springframework.transaction.TransactionStatus;

/**
 * Manager per la gestione dell'operazione di migrazione di una S.A.
 * 
 * @author Luca.Giacomazzo
 */
public class MigrazioneSaManager {

  /**
   * Logger della classe.
   */
  private static Logger logger = Logger.getLogger(MigrazioneSaManager.class);
  
  private SqlManager sqlManager;
  
  private W9Manager w9Manager;
  
  private RichiestaIdGaraCigManager richiestaIdGaraCigManager;
  
  private GeneManager geneManager;
  
  private GenChiaviManager genChiaviManager;

  private GaraDelegataManager garaDelegataManager;
  
  public void setSqlManager(SqlManager sqlMan) {
    this.sqlManager = sqlMan;
  }
  
  public void setW9Manager(final W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }
  
  public void setRichiestaIdGaraCigManager(RichiestaIdGaraCigManager richiestaIdGaraCigManager) {
    this.richiestaIdGaraCigManager = richiestaIdGaraCigManager;
  }

  public void setGeneManager(final GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  public void setGenChiaviManager(final GenChiaviManager genChiaviManager) {
    this.genChiaviManager = genChiaviManager;
  }
  
  public void setGaraDelegataManager(GaraDelegataManager garaDelegataManager) {
    this.garaDelegataManager = garaDelegataManager;
  }
  
  /**
   * Valorizzazione degli attributi dell'oggetto GaraSABean passato come argomento del metodo stesso.
   * 
   * @param codiceGara
   * @param syscon
   * @param codUffint
   * @return
   * @throws CriptazioneException
   * @throws SQLException
   * @throws GestoreException
   */
  public GaraSABean valorizzaGaraSABean(long codiceGara, long syscon, String codUffint) throws SQLException {
    boolean continua = true;
    
    GaraSABean garaSaBean = this.getDatiGara(codiceGara);
    
    if (continua) {
      if (garaSaBean.getCodiciCig() != null && garaSaBean.getCodiciCig().length > 0) {
        HashMap<String, Object> hm2 = this.richiestaIdGaraCigManager.getXmlSimog(
            garaSaBean.getCodiceCig(0), syscon, codUffint, null, null);

        if (((Boolean) hm2.get("esito")).booleanValue() ) {
          String garaXml = (String) hm2.get("garaXml");
          garaSaBean.setXmlSimog(garaXml);
        } else {
          garaSaBean.setOk(false);
          garaSaBean.setDescrErrore((String) hm2.get("msgErr"));
          continua = false;
        }
      }
    }
    
    return garaSaBean;
  }
  

  /**
   * Controllo preliminari per la gara, che consistono in
   * - controllo che la gara scaricata da SIMOG abbia una S.A. appaltante diversa da quella di origine
   * - che la stazione appaltante di destinazione esista a DB e che sia unica
   * 
   * @param mb MigrazioneBean
   * @param syscon
   * @param isOperazioneMigrazione
   * @return Ritorna true se controlli superati, false altrimenti
   *  
   * @throws GestoreException
   */
  public boolean controlloDatiGaraSABean(MigrazioneBean mb, long syscon)
      throws GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("controlloDatiGaraSABean: inizio metodo");
    }
    
    boolean result = true;
    boolean continua = true;
    
    while (mb.getNumeroGareControllate() < mb.getNumeroGareDaMigrare() && continua) {
      try {
        if (mb.getNumeroGareControllate() == 0) {
          mb.resetGaraInElaborazione();
        }
        mb.passaAllaGaraSuccessiva();
    
        GaraSABean garaSaBean = mb.getGaraSABeanInElaborazione();
        
        // Rimappatura dell'oggetto ResponseConsultaGara nell'oggetto SchedaDocument per
        // evitare problemi di namespace fra i WS tra SA e ORT e tra ORT e SIMOG
        ConsultaGaraResponseDocument consultaGaraResponseDocument = 
          ConsultaGaraResponseDocument.Factory.parse(garaSaBean.getXmlSimog());
        
        GaraType garaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiGara().getGara();
        DatiComuniType datiComuniType = null;
        if (consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().isSetDatiScheda()) {
          datiComuniType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML().getDatiScheda().getDatiComuni();
        }
        if (garaType.getCFAMMINISTRAZIONE().equalsIgnoreCase(garaSaBean.getCfsaOrigine())) {
          // Errore: la gara in Simog non e' stata migrata nella nuova SA
          logger.error("Impossibile completare l'operazione di migrazione della gara con IdAvGAra=" +
            garaType.getIDGARA() + " da parte dell'utente SYSCON=" + syscon + " per la SA CODEIN="
            + garaSaBean.getCodeinOrigine() + " perche' CF S.A. in Simog e' uguale al CF S.A. attiva.");

          garaSaBean.setOggettoGara(garaType.getOGGETTO());
          garaSaBean.setIdAvGara("" + garaType.getIDGARA());
          
          garaSaBean.setIdStazAppaltanteSimog(garaType.getIDSTAZIONEAPPALTANTE());
          garaSaBean.setDenomStazAppaltanteSimog(garaType.getDENOMAMMINISTRAZIONE());
          if (datiComuniType  != null) {
          	garaSaBean.setCodiceCentroDiCosto(datiComuniType.getCODICECC());
          	garaSaBean.setDenominazioneCentroDiCosto(datiComuniType.getDENOMCC());
          }
          garaSaBean.setOk(false);
          garaSaBean.setDescrErrore("errors.migrazioneSA.migrareGaraInSimog");

          result = false;
        } else {
          garaSaBean.setCfsaDestinazione(garaType.getCFAMMINISTRAZIONE());
          garaSaBean.setIdStazAppaltanteSimog(garaType.getIDSTAZIONEAPPALTANTE());
          garaSaBean.setDenomStazAppaltanteSimog(garaType.getDENOMAMMINISTRAZIONE());
          if (datiComuniType  != null) {
          	garaSaBean.setCodiceCentroDiCosto(datiComuniType.getCODICECC());
          	garaSaBean.setDenominazioneCentroDiCosto(datiComuniType.getDENOMCC());
          }
        }
        
        if (result) {
          String cfSaDest = garaType.getCFAMMINISTRAZIONE();
          
          Long numeroSA = (Long) this.sqlManager.getObject(
              "select count(*) from UFFINT where UPPER(CFEIN)=?",
              new Object[] { cfSaDest });
          
          if (numeroSA.intValue() == 1) {
            // la SA di destinazione esiste in base dati
            garaSaBean.setCfsaDestinazione(garaType.getCFAMMINISTRAZIONE());
            garaSaBean.setCodeinDestinazione((String) this.sqlManager.getObject(
                "select CODEIN from UFFINT where UPPER(CFEIN)=?", new Object[] { cfSaDest }));

            result = true;
          } else if (numeroSA.intValue() < 1) {
            // Non esiste la SA con il CF indicato
            logger.error("Impossibile completare l’operazione di migrazione della gara con IdAvGAra=" +
              garaType.getIDGARA() + " da parte dell'utente SYSCON=" + syscon + " per la SA CODEIN="
              + garaSaBean.getCodeinOrigine() + " perche' in Sitat non esiste la SA con CFEIN="
              + cfSaDest.toUpperCase());

            result = false;
            
            garaSaBean.setOggettoGara(garaType.getOGGETTO());
            garaSaBean.setIdAvGara("" + garaType.getIDGARA());
            garaSaBean.setOk(false);
            garaSaBean.setDescrErrore("errors.migrazioneSA.noSaDestinazione");
            // La stazione appaltante di destinazione non e' presente nell'archivio
            // dell'applicativo. Contattare un amministratore del sistema
            
          } else {
            // Più SA con lo stesso CF
            logger.error("Impossibile completare l’operazione di migrazione della gara con IdAvGAra=" +
              garaType.getIDGARA() + " da parte dell'utente SYSCON=" + syscon + " dalla SA CODEIN="
              + garaSaBean.getCodeinOrigine() + " perche' in Sitat esiste piu' volte la SA con CFEIN="
              + cfSaDest.toUpperCase());

            garaSaBean.setOggettoGara(garaType.getOGGETTO());
            garaSaBean.setIdAvGara("" + garaType.getIDGARA());
            garaSaBean.setOk(false);
            garaSaBean.setDescrErrore("errors.migrazioneSA.noUnicaSaDestinazione");
            // La stazione appaltante di destinazione e' presente piu' volte nell'archivio
            // dell'applicativo. Contattare un amministratore del sistema
            result = false;
          }
        }
    
      } catch (XmlException xe) {
        logger.error("Errore nel parsing del Xml ricevuto da Simog nel recupero " +
            "dei dati della gara in fase esecuzione dei controlli preliminari", xe);

        continua = false;
      } catch (SQLException se) {
        logger.error("Errore inaspettato nel recupero dei dati della gara " +
            "in fase esecuzione dei controlli preliminari", se);

        continua = false;
      } finally {
        if (continua == false) {
          throw new GestoreException("Errore inaspettato nel controllo dei dati della gara con numero gara pari a "
              + mb.getGaraSABeanInElaborazione().getIdAvGara(), null);
        } else {
          mb.incrementaNumeroGareControllate();
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("controlloDatiGaraSABean: fine metodo");
    }
    
    return result;
  }

  
  /**
   * Aggiornamento della stazione appaltante, del RUP, degli archivi imprese,
   * legali rappresentanti per la gara passata come argomento.
   * 
   * @param garaSaBean
   * @param profiloUtente
   * @throws GestoreException
   * @throws SQLException
   */
  public synchronized boolean updateStazioneAppaltante(MigrazioneBean migrazioneBean,
      ProfiloUtente profiloUtente) throws GestoreException, SQLException, Throwable {

    if (logger.isDebugEnabled()) {
      logger.debug("updateStazioneAppaltante: inizio metodo");
    }
    
    boolean continua = true;
    
    while (migrazioneBean.getNumeroGareMigrate() < migrazioneBean.getNumeroGareDaMigrare() && continua) {
      if (migrazioneBean.getNumeroGareMigrate() == 0) {
        migrazioneBean.resetGaraInElaborazione();
      }
      migrazioneBean.passaAllaGaraSuccessiva();
      
      GaraSABean garaSaBean = migrazioneBean.getGaraSABeanInElaborazione();
      
      try {
        Long codiceGara = garaSaBean.getCodiceGara();
        String codeinSaDestinazione = this.w9Manager.getCodein(garaSaBean.getCfsaDestinazione());

        ConsultaGaraResponseDocument consultaGaraResponseDocument = 
          ConsultaGaraResponseDocument.Factory.parse(garaSaBean.getXmlSimog());
        
        SchedaType schedaType = consultaGaraResponseDocument.getConsultaGaraResponse().getReturn().getGaraXML();
        GaraType garaType = schedaType.getDatiGara().getGara();
        
        String cfRup = null;
        if (schedaType != null && schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getDatiComuni() != null) {
          cfRup = schedaType.getDatiScheda().getDatiComuni().getCFRUP();
        }
        if (StringUtils.isEmpty(cfRup)) {
          if (garaType != null) {
            cfRup = garaType.getCFUTENTE();
          }
        }
        
        String codiceTecnicoDestinazione = null;
        if (StringUtils.isNotEmpty(cfRup)) {
          codiceTecnicoDestinazione = (String) this.sqlManager.getObject(
              "select CODTEC from TECNI where CGENTEI=? and UPPER(CFTEC)=?",
              new Object[] { codeinSaDestinazione, cfRup.toUpperCase() });
          if (StringUtils.isEmpty(codiceTecnicoDestinazione)) {
            codiceTecnicoDestinazione = this.w9Manager.insertTecnico(
                garaSaBean.getCfsaDestinazione(), cfRup);
          }
        }
        
        // Migrazione della gara e dei lotti e gestione del centro di costo
        // Migrazione degli incarichi professionali dalla S.A. sorgente a quella di destinazione
        // Migrazione delle imprese e dei legali rappresentanti associate alle diverse entita' figlie 
        // della gara e dei lotti dalla S.A. sorgente a quella di destinazione 
        this.migraGaraLotti(codiceGara, codeinSaDestinazione, codiceTecnicoDestinazione, 
            garaSaBean.getCodiceCentroDiCosto(), garaSaBean.getDenominazioneCentroDiCosto());

        migrazioneBean.incrementaNumeroGareMigrate();
      } catch (GestoreException e) {
        logger.error("Errore inaspettato nell'aggiornamento della gara con IdAvGara=" + garaSaBean.getIdAvGara()
            + " da parte dell'utente " + profiloUtente.getNome() + " USRSYS.SYSCON=" + profiloUtente.getId()
            + " nel migrare la gara dalla S.A. con CF=" + garaSaBean.getCfsaOrigine()
            + " alla S.A. con CF=" + garaSaBean.getCfsaDestinazione(), e);
        garaSaBean.setOk(false);

        // in caso di errore
        migrazioneBean.setErrore("updatesa");
        continua = false;
      } catch (SQLException e) {
        logger.error("Errore inaspettato nell'aggiornamento della gara con IdAvGara=" + garaSaBean.getIdAvGara()
            + " da parte dell'utente " + profiloUtente.getNome() + " USRSYS.SYSCON=" + profiloUtente.getId()
            + " nel migrare la gara dalla S.A. con CF=" + garaSaBean.getCfsaOrigine()
            + " alla S.A. con CF=" + garaSaBean.getCfsaDestinazione(), e);
        garaSaBean.setOk(false);

        // in caso di errore
        migrazioneBean.setErrore("updatesa");
        continua = false;
      } catch (Throwable t) {
        logger.error("Errore inaspettato nell'aggiornamento della gara con IdAvGara=" + garaSaBean.getIdAvGara()
            + " da parte dell'utente " + profiloUtente.getNome() + " USRSYS.SYSCON=" + profiloUtente.getId()
            + " nel migrare la gara dalla S.A. con CF=" + garaSaBean.getCfsaOrigine()
            + " alla S.A. con CF=" + garaSaBean.getCfsaDestinazione(), t);
        garaSaBean.setOk(false);

        // in caso di errore
        migrazioneBean.setErrore("updatesa");
        continua = false;
      } finally {
        if (continua == false) {
          throw new GestoreException("Errore inaspettato durante l'operazione di migrazione ", null);
        }
      }
    }
    
    // E' terminata la fase di migrazione delle gara.
    // Si puo' terminare la procedura.
    migrazioneBean.resetGaraInElaborazione();
    
    if (logger.isDebugEnabled()) {
      logger.debug("updateStazioneAppaltante: fine metodo");
    }
    
    return continua;
  }

  /**
   * @param codiceGara
   * @param codeinSaDestinazione
   * @param codiceTecnicoDestinazione
   * @param codiceCentroCostoDestinazione
   * @param denominazioneCentrocostoDestinazione
   * @return
   * @throws SQLException
   * @throws GestoreException 
   */
  private void migraGaraLotti( Long codiceGara, String codeinSaDestinazione,
      String codiceTecnicoDestinazione, String codiceCentroCostoDestinazione, String denominazioneCentrocostoDestinazione)
      throws SQLException, GestoreException {
    
    Long idCentroCosto = null;
    if (StringUtils.isNotEmpty(codiceCentroCostoDestinazione) && StringUtils.isNotEmpty(denominazioneCentrocostoDestinazione)) {
    	idCentroCosto = (Long) this.sqlManager.getObject(
	        "select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)=? and CODEIN=?",
	        new Object[] { codiceCentroCostoDestinazione.toUpperCase(), codeinSaDestinazione });
	    
	    if (idCentroCosto == null) {
	      Long maxIdCentroCosto = (Long) this.sqlManager.getObject("select max(IDCENTRO)+1 from CENTRICOSTO", 
	          new Object[]{});
	      if (maxIdCentroCosto == null) {
	        idCentroCosto = new Long(1);
	      } else {
	        idCentroCosto = maxIdCentroCosto;
	      }
	      
	      this.sqlManager.update("INSERT INTO CENTRICOSTO (IDCENTRO,CODEIN,CODCENTRO,DENOMCENTRO) VALUES (?,?,?,?)",
	          new Object[] { idCentroCosto, codeinSaDestinazione, codiceCentroCostoDestinazione,
	          denominazioneCentrocostoDestinazione } );
	    }
    }

    // Aggiornamento della Gara: RUP e CODEIN.
    this.sqlManager.update("update W9GARA set RUP=?, CODEIN=?, IDCC=? where CODGARA=?", 
        new Object[] { codiceTecnicoDestinazione, codeinSaDestinazione, idCentroCosto, codiceGara } );
    
    // Aggiornamento del RUP e del DAEXPORT per tutti i lotti della gara. Il campo DAEXPORT
    // viene impostato a 1 per imporre l'invio dell'anagrafica gara-lotto a OR
    this.sqlManager.update("update W9LOTT set RUP=?, DAEXPORT='1' where CODGARA=? ",
        new Object[] { codiceTecnicoDestinazione, codiceGara  } );
    
    // Migrazione degli incarichi professionali dalla S.A. sorgente a quella di destinazione
    this.migraIncarichiProfessionali(codiceGara, codeinSaDestinazione);
    
    // Gestione delle imprese associate alla gara: W9AGGI, W9SUBA, W9CANTIMP, W9IMPRESE,
    // W9INASIC, W9INFOR, W9MISSCI, W9SIC, W9REGCON e dei legali rappresentanti.
    String[] arrayTabelleImpresa = new String[] { "W9AGGI", "W9AGGI", "W9SUBA", "W9SUBA", 
        "W9CANTIMP", "W9IMPRESE", "W9INASIC", "W9INFOR", "W9MISSIC", "W9SIC", "W9REGCON" };
    String[] arrayNomiCampiImpresa = new String[] { "CODIMP,NUM_AGGI", "CODIMP_AUSILIARIA,NUM_AGGI",
        "CODIMP,NUM_SUBA", "CODIMP_AGGIUDICATRICE,NUM_SUBA", "CODIMP,NUM_CANT", "CODIMP,NUM_IMPR", 
        "CODIMP,NUM_INAD", "CODIMP,NUM_INFOR", "CODIMP,NUM_MISS", "CODIMP,NUM_SIC", "CODIMP,NUM_REGO" };
    this.migraImpreseLegaliRappr(codiceGara, codeinSaDestinazione, arrayTabelleImpresa, arrayNomiCampiImpresa);
    
  }

  /**
   * @param codiceGara
   * @param codeinSaDestinazione
   * @param arrayTabelleImpresa
   * @param arrayNomiCampiImpresa
   * @throws SQLException
   * @throws GestoreException
   */
  private void migraImpreseLegaliRappr(Long codiceGara, String codeinSaDestinazione, String[] arrayTabelleImpresa,
      String[] arrayNomiCampiImpresa) throws SQLException, GestoreException {
    
    Long codiceLotto;
    for (int i=0; i < arrayTabelleImpresa.length; i++) {
      List< ? > listaImprese = this.sqlManager.getListVector(
          "select CODLOTT," + arrayNomiCampiImpresa[i] + " from " + arrayTabelleImpresa[i] +
            " where CODGARA=? order by CODLOTT asc ", new Object[] { codiceGara });
      
      Long progressivo = null;
      if (listaImprese != null && listaImprese.size() > 0) {
        for (int i1=0; i1 < listaImprese.size(); i1++) {
          Vector< ? > impresa = (Vector< ? >) listaImprese.get(i1);
          if (impresa != null && (impresa.size() == 2 && impresa.get(0) != null && impresa.get(1) != null)
              || (impresa.size() == 3 && impresa.get(0) != null && impresa.get(1) != null && impresa.get(2) != null))  {
            codiceLotto = new Long(impresa.get(0).toString());
            String codiceImpresaOrigine  = impresa.get(1).toString();
            
            if (impresa.size() == 3) {
              progressivo = new Long(impresa.get(2).toString());
            } else {
              progressivo = null;
            }
  
            String codiceImpresaDestinazione = null;
            if (StringUtils.isNotEmpty(codiceImpresaOrigine)) {
              codiceImpresaDestinazione = (String) this.sqlManager.getObject(
                  "select CODIMP from IMPR where CGENIMP=? and (CFIMP=(select CFIMP from IMPR where CODIMP=?) " +
                  "or PIVIMP=(select PIVIMP from IMPR where CODIMP=?))",
                  new Object[] { codeinSaDestinazione, codiceImpresaOrigine, codiceImpresaOrigine });
              
              if (StringUtils.isEmpty(codiceImpresaDestinazione)) {
                // Copia dell'impresa nella Stazione appalatante di destinazione...
                codiceImpresaDestinazione = this.geneManager.calcolaCodificaAutomatica("IMPR", "CODIMP");
                
                DataColumnContainer dccIMPR = new DataColumnContainer(sqlManager, "IMPR",
                    "select IMPR.CODIMP, IMPR.CGENIMP, IMPR.NOMEST, IMPR.NOMIMP, IMPR.NAZIMP, " +
                           "IMPR.CFIMP, IMPR.TIPIMP, IMPR.PIVIMP, IMPR.INDIMP, IMPR.NCIIMP, " +
                           "IMPR.PROIMP, IMPR.CAPIMP, IMPR.LOCIMP, IMPR.CODCIT,IMPR.TELIMP, " +
                           "IMPR.FAXIMP, IMPR.TELCEL, IMPR.EMAIIP, IMPR.EMAI2IP, IMPR.INDWEB, " +
                           "IMPR.NCCIAA, IMPR.NATGIUI from IMPR where CODIMP=?", new Object[] { codiceImpresaOrigine } );
                this.copiaValori(dccIMPR);
                
                dccIMPR.getColumn("IMPR.CODIMP").setChiave(true);
                dccIMPR.setValue("IMPR.CODIMP", codiceImpresaDestinazione);
                dccIMPR.setValue("IMPR.CGENIMP", codeinSaDestinazione);
                dccIMPR.insert("IMPR", this.sqlManager);

                // Gestione dei legali rappresentanti dell'impresa (IMPLEG, TEIM)
                // Si crea l'occorrenza in IMPLEG, copiandola dall'impresa 
                // di origine ed aggiornando i campi CODIMP2 e CODLEG di IMPLEG.
                List< ? > listaImpleg = this.sqlManager.getListVector(
                    "select CODLEG from IMPLEG where CODIMP2=?", new Object[] { codiceImpresaOrigine });
                String codiceTEIM = codiceImpresaDestinazione;
                
                if (listaImpleg != null && listaImpleg.size() > 0) {
                  for (int j = 0; j < listaImpleg.size(); j++) {
                    Vector< ? > obj = (Vector< ? >) listaImpleg.get(j);
                    if (obj != null && obj.size() == 1 && obj.get(0) != null) {
                      String codImpLeg = obj.get(0).toString();
                      
                      Long countTeim = (Long) this.sqlManager.getObject("select count(*) from TEIM where CODTIM=?",
                          new Object[] { codImpLeg });
                      if (countTeim != null && countTeim.longValue() == 1) {
                        // Copia del tecnico d'impresa nella nuova stazione appaltante
                        DataColumnContainer dccTEIM = new DataColumnContainer(this.sqlManager, "TEIM",
                            "select CODTIM, COGTIM, NOMETIM, NOMTIM, CFTIM, CGENTIM from TEIM where CODTIM=?", 
                            new Object[] { codImpLeg });
                          
                        this.copiaValori(dccTEIM);
                        dccTEIM.getColumn("TEIM.CODTIM").setChiave(true);
                        dccTEIM.setValue("TEIM.CODTIM", codiceTEIM);
                        dccTEIM.setValue("TEIM.CGENTIM", codeinSaDestinazione);
                        dccTEIM.insert("TEIM", this.sqlManager);

                        // IMPLEG:
                        // Copiare il record di IMPLEG e cambiare i campi CODIMP2 e CODLEG per creare 
                        // l'associazione tra l'impresa ed il legale rappresentante
                        Long countImpleg = (Long) this.sqlManager.getObject("select count(*) from IMPLEG where CODIMP2=? and CODLEG=?",
                            new Object[] { codiceImpresaOrigine, codImpLeg });
                        if (countImpleg != null && countImpleg.longValue() == 1) {
                          DataColumnContainer dccIMPLEG = new DataColumnContainer(this.sqlManager, "IMPLEG",
                              "select ID, CODIMP2, CODLEG, NOMLEG from IMPLEG " +
                               "where CODIMP2=? and CODLEG=?",
                              new Object[] { codiceImpresaOrigine, codImpLeg });
                          this.copiaValori(dccIMPLEG);
                          int nextId = this.genChiaviManager.getNextId("IMPLEG");
                          dccIMPLEG.setValue("IMPLEG.ID", new Long(nextId));
                          dccIMPLEG.getColumn("IMPLEG.ID").setChiave(true);
                          dccIMPLEG.setValue("IMPLEG.CODIMP2", codiceImpresaDestinazione);
                          dccIMPLEG.setValue("IMPLEG.CODLEG", codiceTEIM);
                          dccIMPLEG.insert("IMPLEG", this.sqlManager);
                        }
                      }
                    }
                  }
                }
              }
            }
              
            if (progressivo == null) {
              this.sqlManager.update("update " + arrayTabelleImpresa[i] + " set "
                  + arrayNomiCampiImpresa[i].split(",")[0] + "=? where CODGARA=? and CODLOTT=?",
                  new Object[] { codiceImpresaDestinazione, codiceGara, codiceLotto });
            } else {
              this.sqlManager.update("update " + arrayTabelleImpresa[i] + " set "
                  + arrayNomiCampiImpresa[i].split(",")[0] + "=? where CODGARA=? and CODLOTT=? and "
                  + arrayNomiCampiImpresa[i].split(",")[1] + "=?",
                  new Object[] { codiceImpresaDestinazione, codiceGara, codiceLotto, progressivo });
            }
          }
        }
      }
    }
  }

  /**
   * @param codiceGara
   * @param codeinSaDestinazione
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  private void migraIncarichiProfessionali(Long codiceGara, String codeinSaDestinazione) 
      throws SQLException, GestoreException {
    // Gestione degli incarichi professionali diversi dal RUP.
    List< ? > listaIncaricati = this.sqlManager.getListVector(
        "select CODTEC from W9INCA where CODGARA=?", new Object[] { codiceGara } );
    
    if (listaIncaricati != null && listaIncaricati.size() > 0) {
      for (int ui=0; ui < listaIncaricati.size(); ui++) {
        Vector< ? > incaricato = (Vector< ? >) listaIncaricati.get(ui);
        
        if (incaricato != null && incaricato.get(0) != null) {
          String codiceTec = incaricato.get(0).toString();
          
          String codTecDest = (String) this.sqlManager.getObject(
              "select CODTEC from TECNI where CGENTEI=? and CFTEC=(select CFTEC from TECNI where CODTEC=?)",
              new Object[] { codeinSaDestinazione, codiceTec });
          if (StringUtils.isEmpty(codTecDest)) {
            // Non esiste il tecnico associato alla nuova Stazione appaltante.
            // Si provvede ad inserirne uno, copaiando i dati dal tecnico sorgente
            
            DataColumnContainer dccTecni = new DataColumnContainer(this.sqlManager, "TECNI",
                "select CODTEC, CGENTEI, CFTEC, CITTEC, COGTEI, NOMETEI, NOMTEC, INDTEC, NCITEC, LOCTEC, CAPTEC, TELTEC, " +
                "FAXTEC, EMATEC, PROTEC, SYSCON from TECNI where CODTEC=? ", new Object[] { codiceTec });
  
            this.copiaValori(dccTecni);
            dccTecni.getColumn("TECNI.CODTEC").setChiave(true);
            codTecDest = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");
            dccTecni.getColumn("TECNI.CODTEC").setObjectValue(codTecDest);
            dccTecni.getColumn("TECNI.CGENTEI").setObjectValue(codeinSaDestinazione);
            dccTecni.insert("TECNI", this.sqlManager);
          }
          
          this.sqlManager.update("update W9INCA set CODTEC=? where CODGARA=? and CODTEC=?", 
              new Object[] { codTecDest, codiceGara, codiceTec });
        }
      }
    }
  }

  private void copiaValori(DataColumnContainer dcc) throws GestoreException {
    HashMap<String, DataColumn> hm = dcc.getColonne();
    Set<String> set = hm.keySet();
    Iterator<String> iter = set.iterator();
    while(iter.hasNext()) {
      String tmp = iter.next();
      dcc.setValue(tmp, dcc.getColumn(tmp).getOriginalValue().getValue());
      dcc.getColumn(tmp).setObjectOriginalValue(null);
    }
  }

  /**
   * Valorizzazione di codiceGara, IdAvGara, Oggetto, codici CIG in SA e codici CIG da Simog
   * nell'oggetto GaraSABean passato come argomento del metodo stesso.
   * 
   * @param codiceGara
   * @param garaSaBean
   * @throws SQLException
   */
  public GaraSABean getDatiGara(Long codiceGara) throws SQLException {
    Vector< ? > datiGara = this.sqlManager.getVector(
        "select g.IDAVGARA, g.OGGETTO, g.CODEIN, u.CFEIN from W9GARA g, UFFINT u " +
        " where g.CODGARA=? and g.CODEIN=u.CODEIN",
        new Object[] { codiceGara } );

    GaraSABean garaSaBean = new GaraSABean(codiceGara);
    garaSaBean.setIdAvGara(SqlManager.getValueFromVectorParam(datiGara, 0).getStringValue());
    garaSaBean.setOggettoGara(SqlManager.getValueFromVectorParam(datiGara, 1).getStringValue());
    garaSaBean.setCodeinOrigine(SqlManager.getValueFromVectorParam(datiGara, 2).getStringValue());
    garaSaBean.setCfsaOrigine(SqlManager.getValueFromVectorParam(datiGara, 3).getStringValue());

    List< ? > listaCodiciCIG =  this.sqlManager.getListVector(
        "select CIG from W9LOTT where CODGARA=? order by CODLOTT asc",
        new Object[] { codiceGara } );
    
    if (listaCodiciCIG != null && listaCodiciCIG.size() > 0) {
      String[] arrayCodiciCig = new String[listaCodiciCIG.size()];
      
      for (int i=0; i < listaCodiciCIG.size(); i++) {
        Vector< ? > riga = (Vector< ? >)listaCodiciCIG.get(i);
        arrayCodiciCig[i] = SqlManager.getValueFromVectorParam(riga, 0).getStringValue();
      }
      garaSaBean.setCodiciCig(arrayCodiciCig);
    }
        
    return garaSaBean;
  }
  
  /**
   * 
   * @param codiceGara
   * @param codeinSaDestinazione
   * @param codiceTecnicoDestinazione
   * @param codiceCentroDiCosto
   * @param denominazioneCentrodiCosto
   * @throws SQLException
   * @throws GestoreException
   */
  public ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegataIndiretta(PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean,
      Collaborazione collaborazione) throws SQLException {

    logger.debug("presaInCaricoGaraDelegataIndiretta: inizio metodo");
    
    if (presaInCaricoGaraDelegataBean.getCodiceGara() != null) {
      return this.presaInCaricoGaraDelegataIndirettaGaraEsistente(presaInCaricoGaraDelegataBean, collaborazione);
    } else {
      return this.presaInCaricoGaraDelegataIndirettaGaraInesistente(presaInCaricoGaraDelegataBean, collaborazione);
    }
  }
  
  public it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata prendiInCaricoGaraDelegataDiretta(PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean,
      Collaborazione collaborazione) throws SQLException {

    logger.debug("presaInCaricoGaraDelegataDiretta: inizio metodo");
    
    if (presaInCaricoGaraDelegataBean.getCodiceGara() != null) {
      return this.presaInCaricoGaraDelegataDirettaGaraEsistente(presaInCaricoGaraDelegataBean, collaborazione);
    } else {
      return this.presaInCaricoGaraDelegataDirettaGaraInesistente(presaInCaricoGaraDelegataBean, collaborazione);
    }
  }

  
  /**
   * Esecuzione della presa in carico gara con delega con gara esistente in base dati.
   * L'operazione consiste nella migrazione della gara ed esecuzione dell'operazione
   * di presa in carico gara con delega su SIMOG. In caso SIMOG risponda positivamente
   * si esegue il commit della migrazione, altrimenti si fa la roolback.
   *
   * @param presaInCaricoGaraDelegataBean
   * @param collaborazione
   * @return
   * @throws SQLException
   */
  private ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegataIndirettaGaraEsistente(
      PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean, Collaborazione collaborazione) throws SQLException {
    
    logger.debug("presaInCaricoGaraDelegataIndirettaGaraEsistente: inizio metodo");
    
    TransactionStatus status = this.sqlManager.startTransaction();
    
    long codiceGara = presaInCaricoGaraDelegataBean.getCodiceGara();
    String codeinSaDestinazione = presaInCaricoGaraDelegataBean.getCodeinStazAppAttiva();
    String codiceTecnicoDestinazione = presaInCaricoGaraDelegataBean.getCodTec();
    String codiceCentroDiCosto = collaborazione.getUfficioId();
    String denominazioneCentrodiCosto = collaborazione.getUfficioDenominazione();

    // L'operazione di presa in carico gara delegata si compone delle seguenti fasi:
    // -fase 1: aggiornamento della base dati con la migrazione della gara alla nuova S.A,
    //          cambio del RUP e del centro di costo
    // -fase 2: invocazione del WsOsservatorio per l'esecuzione dell'operazione di presa
    //          in carico della gara su SIMOG. 
    // -fase 3: se la fase 2 termina con esito positivo si fa il commit della transazione SQL, 
    //          altrimenti si effettua la rollback delle operazioni fatte nella fase 1

    ResponsePresaInCaricoGaraDelegata responsePresaInCaricoGaraDelegata = null;
    try { 

      // Fase 1:
      // Migrazione della gara e dei lotti e gestione del centro di costo
      // Migrazione degli incarichi professionali dalla S.A. sorgente a quella di destinazione
      // Migrazione delle imprese e dei legali rappresentanti associate alle diverse entita' figlie 
      // della gara e dei lotti dalla S.A. sorgente a quella di destinazione
      this.migraGaraLotti(codiceGara, codeinSaDestinazione, codiceTecnicoDestinazione, 
          codiceCentroDiCosto, denominazioneCentrodiCosto);

      // Fase 2:      
      String urlWSOsservatorio = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
      WsOsservatorioProxy wsOsservatorio = new WsOsservatorioProxy(urlWSOsservatorio);
  
      logger.info("Invocazione del metodo presaInCaricoGaraDelegata sul WsOsservatorio");
      responsePresaInCaricoGaraDelegata = wsOsservatorio.presaInCaricoGaraDelegata(
          presaInCaricoGaraDelegataBean.getIdAvGara(), presaInCaricoGaraDelegataBean.getCfRup(),
          collaborazione.getIndex());
      logger.info("Invocato il metodo presaInCaricoGaraDelegata sul WsOsservatorio");

    } catch (GestoreException ge) {
      logger.error("Errore durante le operazioni di migrazione della gara IdAvGara=" + 
          presaInCaricoGaraDelegataBean.getIdAvGara(), ge);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("");
    } catch (SQLException se) {
      logger.error("Errore durante le operazioni di migrazione della gara IdAvGara=" + 
          presaInCaricoGaraDelegataBean.getIdAvGara(), se);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("");
    } catch (RemoteException re) {
      logger.error("Errore nell'interazione con il servizio WsOsservatorio per la presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), re);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore remoto nella richiesta di presa in carico della gara delegata");
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'operazione presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), t);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore inaspettato nella richiesta di presa in carico della gara delegata");
    } finally {
      // Fase 3:      
      if (responsePresaInCaricoGaraDelegata.isSuccess()) {
        this.sqlManager.commitTransaction(status);
      } else {
        this.sqlManager.rollbackTransaction(status);
      }
    }
    logger.debug("presaInCaricoGaraDelegataIndirettaGaraEsistente: fine metodo");

    return responsePresaInCaricoGaraDelegata;
  }
  
  /**
   * Esecuzione della presa in carico gara con delega con gara inesistente in base dati.
   * L'operazione consiste nell'esecuzione dell'operazione  di presa in carico gara con 
   * delega su SIMOG
   * 
   * @param presaInCaricoGaraDelegataBean
   * @param collaborazione
   * @return
   * @throws SQLException
   */
  private ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegataIndirettaGaraInesistente(
      PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean, Collaborazione collaborazione) {
    
    logger.debug("presaInCaricoGaraDelegataIndirettaGaraInesistente: inizio metodo");

    ResponsePresaInCaricoGaraDelegata responsePresaInCaricoGaraDelegata = null;
    try { 

      // Fase 1:
      String urlWSOsservatorio = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
      WsOsservatorioProxy wsOsservatorio = new WsOsservatorioProxy(urlWSOsservatorio);
  
      logger.info("Invocazione del metodo presaInCaricoGaraDelegata sul WsOsservatorio");
      responsePresaInCaricoGaraDelegata = wsOsservatorio.presaInCaricoGaraDelegata(
          presaInCaricoGaraDelegataBean.getIdAvGara(), presaInCaricoGaraDelegataBean.getCfRup(),
          collaborazione.getIndex());
      logger.info("Invocato il metodo presaInCaricoGaraDelegata sul WsOsservatorio");

    } catch (RemoteException re) {
      logger.error("Errore nell'interazione con il servizio WsOsservatorio per la presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), re);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore remoto nella richiesta di presa in carico della gara delegata");
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'operazione presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), t);
      responsePresaInCaricoGaraDelegata = new ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore inaspettato nella richiesta di presa in carico della gara delegata");
    }
    
    logger.debug("presaInCaricoGaraDelegataIndirettaGaraInesistente: fine metodo");

    return responsePresaInCaricoGaraDelegata;
  }


  /**
   * Esecuzione della presa in carico gara con delega con gara esistente in base dati.
   * L'operazione consiste nella migrazione della gara ed esecuzione dell'operazione
   * di presa in carico gara con delega su SIMOG. In caso SIMOG risponda positivamente
   * si esegue il commit della migrazione, altrimenti si fa la roolback.
   *
   * @param presaInCaricoGaraDelegataBean
   * @param collaborazione
   * @return
   * @throws SQLException
   */
  private it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegataDirettaGaraEsistente(
      PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean, Collaborazione collaborazione) throws SQLException {
    
    logger.debug("presaInCaricoGaraDelegataDirettaGaraEsistente: inizio metodo");
    
    TransactionStatus status = this.sqlManager.startTransaction();
    
    long codiceGara = presaInCaricoGaraDelegataBean.getCodiceGara();
    String codeinSaDestinazione = presaInCaricoGaraDelegataBean.getCodeinStazAppAttiva();
    String codiceTecnicoDestinazione = presaInCaricoGaraDelegataBean.getCodTec();
    String codiceCentroDiCosto = collaborazione.getUfficioId();
    String denominazioneCentrodiCosto = collaborazione.getUfficioDenominazione();

    // L'operazione di presa in carico gara delegata si compone delle seguenti fasi:
    // -fase 1: aggiornamento della base dati con la migrazione della gara alla nuova S.A,
    //          cambio del RUP e del centro di costo
    // -fase 2: invocazione del WsOsservatorio per l'esecuzione dell'operazione di presa
    //          in carico della gara su SIMOG. 
    // -fase 3: se la fase 2 termina con esito positivo si fa il commit della transazione SQL, 
    //          altrimenti si effettua la rollback delle operazioni fatte nella fase 1

    it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata responsePresaInCaricoGaraDelegata = null;
    try { 

      // Fase 1:
      // Migrazione della gara e dei lotti e gestione del centro di costo
      // Migrazione degli incarichi professionali dalla S.A. sorgente a quella di destinazione
      // Migrazione delle imprese e dei legali rappresentanti associate alle diverse entita' figlie 
      // della gara e dei lotti dalla S.A. sorgente a quella di destinazione
      this.migraGaraLotti(codiceGara, codeinSaDestinazione, codiceTecnicoDestinazione, 
          codiceCentroDiCosto, denominazioneCentrodiCosto);

      
      
      // Fase 2:      
      String idGara = presaInCaricoGaraDelegataBean.getIdAvGara();
      String cfRup = presaInCaricoGaraDelegataBean.getCfRup();
      String simogUser = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogUser");
      String simogPassword = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogPass");
      
      responsePresaInCaricoGaraDelegata =
        this.garaDelegataManager.presaInCaricoGaraDelegata(idGara, cfRup, collaborazione.getIndex(), simogUser, simogPassword);

    } catch (GestoreException ge) {
      logger.error("Errore durante le operazioni di migrazione della gara IdAvGara=" + 
          presaInCaricoGaraDelegataBean.getIdAvGara(), ge);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("");
    } catch (SQLException se) {
      logger.error("Errore durante le operazioni di migrazione della gara IdAvGara=" + 
          presaInCaricoGaraDelegataBean.getIdAvGara(), se);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("");
    } catch (RemoteException re) {
      logger.error("Errore nell'interazione con il servizio SIMOG per la presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), re);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore remoto nella richiesta di presa in carico della gara delegata");
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'operazione presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), t);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore inaspettato nella richiesta di presa in carico della gara delegata");
    } finally {
      // Fase 3:      
      if (responsePresaInCaricoGaraDelegata.isSuccess()) {
        this.sqlManager.commitTransaction(status);
      } else {
        this.sqlManager.rollbackTransaction(status);
      }
    }
    logger.debug("presaInCaricoGaraDelegataDirettaGaraEsistente: fine metodo");

    return responsePresaInCaricoGaraDelegata;
  }
  
  
  /**
   * Esecuzione della presa in carico gara con delega con gara inesistente in base dati.
   * L'operazione consiste nell'esecuzione dell'operazione  di presa in carico gara con 
   * delega su SIMOG
   * 
   * @param presaInCaricoGaraDelegataBean
   * @param collaborazione
   * @return
   * @throws SQLException
   */
  private it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegataDirettaGaraInesistente(
      PresaInCaricoGaraDelegataBean presaInCaricoGaraDelegataBean, Collaborazione collaborazione) throws SQLException {
    
    logger.debug("presaInCaricoGaraDelegataDirettaGaraInesistente: inizio metodo");

    it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata responsePresaInCaricoGaraDelegata = null;
    try { 

      String idGara = presaInCaricoGaraDelegataBean.getIdAvGara();
      String cfRup = presaInCaricoGaraDelegataBean.getCfRup();
      String simogUser = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogUser");
      String simogPassword = presaInCaricoGaraDelegataBean.getHmParametriPerConsultaGara().get("valoreSimogPass");
      
      responsePresaInCaricoGaraDelegata =
        this.garaDelegataManager.presaInCaricoGaraDelegata(idGara, cfRup, collaborazione.getIndex(), simogUser, simogPassword);

    } catch (RemoteException re) {
      logger.error("Errore nell'interazione con il servizio SIMOG per la presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), re);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore remoto nella richiesta di presa in carico della gara delegata");
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'operazione presa in carico della gara delegata IdAvGara=" +
          presaInCaricoGaraDelegataBean.getIdAvGara(), t);
      responsePresaInCaricoGaraDelegata = new it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata();
      responsePresaInCaricoGaraDelegata.setSuccess(false);
      responsePresaInCaricoGaraDelegata.setError("Errore inaspettato nella richiesta di presa in carico della gara delegata");
    }
    
    logger.debug("presaInCaricoGaraDelegataDirettaGaraInesistente: fine metodo");

    return responsePresaInCaricoGaraDelegata;
  }
  
}
