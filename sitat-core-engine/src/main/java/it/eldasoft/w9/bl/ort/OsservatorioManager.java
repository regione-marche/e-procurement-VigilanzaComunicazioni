package it.eldasoft.w9.bl.ort;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.CreazioneXmlManager;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.bean.DatiComuniBean;
import it.eldasoft.w9.common.bean.GaraLottoBean;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitatort.EsitoType;
import it.toscana.rete.rfc.sitatort.FaseEsecuzioneType;
import it.toscana.rete.rfc.sitatort.FaseType;
import it.toscana.rete.rfc.sitatort.FeedbackType;
import it.toscana.rete.rfc.sitatort.ResponseSchedaType;
import it.toscana.rete.rfc.sitatort.TipoFeedbackType;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;

/**
 * Classe per l'implementazione dei metodi getElencoFeedback, getElencoSchede, getScheda
 * messi a disposizione dal WsOsservatorio di SitatORT.
 * 
 * @author Luca.Giacomazzo
 */
public class OsservatorioManager {

  /**
   * Logger della classe.
   */
  private static Logger logger = Logger.getLogger(OsservatorioManager.class);
  
  private SqlManager sqlManager;
  
  private W9Manager w9Manager;
  
  /**
   * Set SqlManager.
   * @param sqlMan the sqlManager to set
   */
  public void setSqlManager(SqlManager sqlMan) {
    this.sqlManager = sqlMan;
  }
  
  /**
   * Set W9Manager.
   * 
   * @param w9Manager the w9Manager to set
   */
  public void setW9Manager(final W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }
  
  /**
   * Ritorna l'elenco dei feedback associati ad una specifica fase. 
   * 
   * @param cig Codice CIG
   * @param idgara Numero della gara
   * @param cfrup Codice fiscale del RUP
   * @param cfsa Codice fiscale della S.A.
   * @return
   */
  public it.toscana.rete.rfc.sitatort.ResponseElencoFeedback getElencoFeedback(
      String cig, String idgara, String cfrup, String cfsa,
      it.toscana.rete.rfc.sitatort.TipoFeedbackType tipoFeedBack,
      it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getElencoFeedback: inizio metodo");
    }
    
    it.toscana.rete.rfc.sitatort.ResponseElencoFeedback responseElencoFeedback = null;
    
    try {
      Long codiceGara = null;
      Long codiceLotto = null;

      // Estrazione di codice gara, codice lotto, codice tecnico e codice SA della
      // gara e lotto che da W9GARA.IDAVGARA = idgara, W9GARA.CODEIN= (sa con CFEIN=cfsa),
      // W9LOTT.CIG=cig, il cui rup (W9GARA.RUP) e' tecnico della sa con CFEIN=cfsa
      Vector< ? > datiGara = this.sqlManager.getVector(
          "select g.CODGARA, l.CODLOTT from W9GARA g, W9LOTT l " +
           "where g.IDAVGARA=? and g.CODGARA=l.CODGARA and l.CIG=? " +
            // "and g.RUP in (select CODTEC from TECNI where CFTEC=? " +
            // "and CGENTEI=(select CODEIN from UFFINT where CFEIN=?)) " +
             "and g.CODEIN=(select CODEIN from UFFINT where CFEIN=?)",
          new Object[] { idgara, cig, cfsa });
          //new Object[] { idgara, cig, cfrup, cfsa, cfsa });
      
      if (datiGara != null) {
        // Esiste nella base dati la gara e il lotto indicati dagli argomenti di questa funzione.
        codiceGara = new Long(SqlManager.getValueFromVectorParam(datiGara, 0).getStringValue());
        codiceLotto = new Long(SqlManager.getValueFromVectorParam(datiGara, 1).getStringValue());
        //codTecnicoGara = SqlManager.getValueFromVectorParam(datiGara, 2).getStringValue();
        //codUffintGara = SqlManager.getValueFromVectorParam(datiGara, 3).getStringValue();
        
        FaseType fase = null;
        Long progresFase = null;
        
        if (faseEsecuzione != null ) {
          if (faseEsecuzione.getCodiceFase() != null) {
            fase = faseEsecuzione.getCodiceFase();
            
            if (faseEsecuzione.getNum() > 0) {
              progresFase = new Long(faseEsecuzione.getNum().longValue());
            }
          }
        }

        List<it.toscana.rete.rfc.sitatort.FeedbackType> listaElencoFeedback = null;
        
        if (TipoFeedbackType.FEEDBACK_OR.equals(tipoFeedBack)) {
          listaElencoFeedback =
              this.getElencoFeedbackOsservatorio(cig, codiceGara, codiceLotto, fase, progresFase);
        } else if (TipoFeedbackType.FEEDBACK_SIMOG.equals(tipoFeedBack)){
          listaElencoFeedback =
              this.getElencoFeedbackSimog(cig, codiceGara, codiceLotto, fase, progresFase);
        } else {
        	//TipoFeedbackType.CANCELLAZIONE_SCHEDE_SIMOG
        	listaElencoFeedback = this.getElencoFeedbackRichiesteCancellazione(cig, codiceGara, codiceLotto, fase, progresFase);
        }

        responseElencoFeedback = new it.toscana.rete.rfc.sitatort.ResponseElencoFeedback();
        if (listaElencoFeedback == null) {
          responseElencoFeedback.setElencoFeedback(new ArrayList<it.toscana.rete.rfc.sitatort.FeedbackType>().toArray(
              new it.toscana.rete.rfc.sitatort.FeedbackType[0]));
        } else {
          responseElencoFeedback.setElencoFeedback(listaElencoFeedback.toArray(
            new it.toscana.rete.rfc.sitatort.FeedbackType[0]));
        }
        responseElencoFeedback.setSuccess(true);

      } else {
        // Non esiste in banca dati una gara e un lotto che rispettano tutti i criteri di ricerca.
        logger.error("Nella base dati non esite una gara con IDAVGARA=" + idgara
            + " associata ad lotto con CIG= " + cig
            + ", relativa alla stazione appaltante con UFFINT.CFEIN=" + cfsa
            + " e come RUP il tecnico della medesima stazione appaltante e con TECNI.CFTEC=" + cfrup);
        
        responseElencoFeedback = new it.toscana.rete.rfc.sitatort.ResponseElencoFeedback();
        responseElencoFeedback.setError("Nell'osservatorio non esiste gara e/o lotto che rispecchia i dati "
            + "indicati (IDGARA=" + idgara + ", CIG=" + cig + ", CFSA=" + cfsa + ", CFRUP=" + cfrup + ")");
        responseElencoFeedback.setSuccess(false);
      }
    } catch (SQLException se) {
      logger.error("Errore nell'estrarre i feedback relativi alla gara/lotto "
          + "con IDGARA=" + idgara + ", CIG=" + cig + ", CFSA=" + cfsa + ", CFRUP=" + cfrup
          + ", tipoFeedback=" + tipoFeedBack.getValue() + ", fase=" + faseEsecuzione.getCodiceFase()
          + ", numero fase=" + faseEsecuzione.getNum() + ")", se);
      
      responseElencoFeedback = new it.toscana.rete.rfc.sitatort.ResponseElencoFeedback();
      responseElencoFeedback.setError("Errore inaspettato nell'estrazione dell'elenco delle schede");
      responseElencoFeedback.setSuccess(false);
      
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'estrarre i feedback relativi alla gara/lotto " +
          "con IDGARA=" + idgara + ", CIG=" + cig + ", CFSA=" + cfsa + ", CFRUP=" + cfrup
          + ", tipoFeedback=" + tipoFeedBack.getValue() + ", fase=" + faseEsecuzione.getCodiceFase()
          + ", numero fase=" + faseEsecuzione.getNum() + ")", t);
      
      responseElencoFeedback = new it.toscana.rete.rfc.sitatort.ResponseElencoFeedback();
      responseElencoFeedback.setError("Errore inaspettato nell'estrazione dell'elenco delle schede");
      responseElencoFeedback.setSuccess(false);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getElencoFeedback: fine metodo");
    }
    
    return responseElencoFeedback;
  }

  /**
   * Estrae l'elenco dei feedback relativi all'invio dei dati delle gare ai servizi Simog.
   * 
   * @param cig Codice CIG
   * @param codiceGara Codice della gara
   * @param codiceLotto Codice del lotto
   * @param fase Fase di gara
   * @param progresFase Progressivo 
   * @param listaElencoFeedback
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  private List<it.toscana.rete.rfc.sitatort.FeedbackType>
      getElencoFeedbackSimog(String cig, Long codiceGara, Long codiceLotto,
          FaseType fase, Long progresFase) throws SQLException, GestoreException {

    List<it.toscana.rete.rfc.sitatort.FeedbackType> listaElencoFeedback = null;
    
    String queryFeedbackANAC = 
          "select x.DATA_FEEDBACK, f.KEY03, f.KEY04, x.NUM_ERRORE, x.NUMXML "
          + "from W9XML x, W9FLUSSI f "
        +  "where x.IDFLUSSO = f.IDFLUSSO ";
    
    if (fase != null) {
      queryFeedbackANAC = queryFeedbackANAC.concat("and f.KEY03=" + fase.getValue());
      
      if (progresFase != null) {
        queryFeedbackANAC = queryFeedbackANAC.concat("and f.KEY04=" + progresFase.toString());
      }
    }

    queryFeedbackANAC = queryFeedbackANAC.concat("and x.CODGARA=? and x.CODLOTT=? "
      + "order by x.NUMXML asc, x.num asc ");
    
    List< ? > listaFeedbackEsistenti = this.sqlManager.getListVector(queryFeedbackANAC,
        new Object[] { cig, codiceGara, codiceLotto } );
    
    if (listaFeedbackEsistenti != null && !listaFeedbackEsistenti.isEmpty()) {
      listaElencoFeedback = new ArrayList<FeedbackType>();
      
      for (int i = 0; i < listaFeedbackEsistenti.size(); i++) {
        Vector< ? > feedbackEsistente = (Vector< ? >) listaFeedbackEsistenti.get(i);
        
        Timestamp dataFeedback = null;
        Long faseEsec = null;
        Long progresFaseEsec = null;
        Long numeroErrori = null;
        Long numXml = null;
        
        Object obj = SqlManager.getValueFromVectorParam(feedbackEsistente, 0).getValue();
        if (obj != null) {
          dataFeedback = (Timestamp) SqlManager.getValueFromVectorParam(
            feedbackEsistente, 0).getValue();
        }
        
        obj = null;
        if (fase != null) {
          if (fase.getValue() == null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 1).getValue();
            if (obj != null) {
              faseEsec = SqlManager.getValueFromVectorParam(
                  feedbackEsistente, 1).longValue();
            }
          } else {
            faseEsec = new Long(fase.getValue());
          }
          
          obj = null;
          if (progresFase != null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 2).getValue();
            if (obj != null) {
              progresFaseEsec = (Long) obj;
            }
          } else {
            progresFaseEsec = progresFase;
          }
        }              
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 3).getValue();
        if (obj != null) {
          numeroErrori = (Long) obj;
        }
        
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 4).getValue();
        if (obj != null) {
          numXml = (Long) obj;
        }
        
        it.toscana.rete.rfc.sitatort.FeedbackType feedback =
            new it.toscana.rete.rfc.sitatort.FeedbackType();
        
        if (dataFeedback != null) {
          Calendar date = Calendar.getInstance();
          date.setTime(dataFeedback);
          feedback.setData(date);
        }

        if (numeroErrori != null) {
          if (numeroErrori > 0) {
            feedback.setEsito(EsitoType.ERRORE);
          } else {
            feedback.setEsito(EsitoType.IMPORTATA);
          }
        } else {
          feedback.setEsito(EsitoType.IMPORTATA);
        }
        
        if (faseEsec != null) {
          feedback.setScheda(FaseType.fromString("" + faseEsec));
        }
        if (progresFaseEsec != null) {
          feedback.setNum(progresFaseEsec.intValue());
        }
        
        if (numXml != null) {
          String queryDescrAnomalia = 
            "select DESCRIZIONE from W9XMLANOM where CODGARA=? and CODLOTT=? and NUMXML=? order by NUM asc";
          
          List< ? > listaDescrizioneAnomalie = this.sqlManager.getListVector(
              queryDescrAnomalia, new Object[] { codiceGara, codiceLotto, numXml });
          
          if (listaDescrizioneAnomalie != null && !listaDescrizioneAnomalie.isEmpty()) {
            String[] arrayDescrizAnomalie = new String[listaDescrizioneAnomalie.size()];
            for (int j = 0; j < listaDescrizioneAnomalie.size(); j++) {
              
              Vector< ? > descrizAnomalia = (Vector< ? >) listaDescrizioneAnomalie.get(j);
              String descAnomalia = (String) SqlManager.getValueFromVectorParam(descrizAnomalia, 0).getValue();
              
              if (StringUtils.isNotEmpty(descAnomalia)) {
                arrayDescrizAnomalie[j] = descAnomalia;
              }
            }
            feedback.setMessaggio(arrayDescrizAnomalie);
          }
        }
        listaElencoFeedback.add(feedback);
      }
    }
    return listaElencoFeedback;
  }

  /**
   * Estrae l'elenco dei feedback relativi alle richieste di cancellazione.
   * Anche se in pratica viene ritornato solo l'ultimo feedback in ordine cronologico
   * 
   * @param cig Codice CIG
   * @param codiceGara Codice della gara
   * @param codiceLotto Codice del lotto
   * @param fase Fase di gara
   * @param progresFase Progressivo 
   * @param listaElencoFeedback
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  private List<it.toscana.rete.rfc.sitatort.FeedbackType>
      getElencoFeedbackRichiesteCancellazione(String cig, Long codiceGara, Long codiceLotto,
          FaseType fase, Long progresFase) throws SQLException, GestoreException {

	  List<it.toscana.rete.rfc.sitatort.FeedbackType> listaElencoFeedback = null;

	    String queryFeedbackORT = 
	       "select i.DATRIC, f.KEY03, f.KEY04, i.STACOM, i.MSG "
	      +  "from W9FLUSSI f left outer join W9INBOX i on f.IDCOMUN=i.IDCOMUN "
	      + "where f.CODOGG=? and f.KEY01=? and f.KEY02=? and f.TINVIO2=-1 and f.KEY03= ? and f.KEY04= ? ";
	    
	    queryFeedbackORT += "UNION "
	    	+ "select i.DATRIC, f.KEY03, f.KEY04, i.STACOM, i.MSG "
		      +  "from W9FLUSSI_ELIMINATI f left outer join W9INBOX i on f.IDCOMUN=i.IDCOMUN "
		      + "where f.CODOGG=? and f.KEY01=? and f.KEY02=? and f.TINVIO2=-1 and f.KEY03= ? and f.KEY04= ?";
		    
	    queryFeedbackORT += " order by DATRIC desc";
	    List< ? > listaFeedbackEsistenti = this.sqlManager.getListVector(queryFeedbackORT,
	        new Object[] { cig, codiceGara, codiceLotto, new Long(fase.getValue()), progresFase, 
	        		cig, codiceGara, codiceLotto, new Long(fase.getValue()), progresFase } );

	    if (listaFeedbackEsistenti != null && !listaFeedbackEsistenti.isEmpty()) {
	      listaElencoFeedback = new ArrayList<FeedbackType>();
        Vector< ? > feedbackEsistente = (Vector< ? >) listaFeedbackEsistenti.get(0);
        
        Timestamp dataFeedback = null;
        Long faseEsec = null;
        Long progresFaseEsec = null;
        Long stacom = null;
        String msg = null;
        
        Object obj = SqlManager.getValueFromVectorParam(feedbackEsistente, 0).getValue();
        if (obj != null) {
          dataFeedback = new Timestamp(((Date) SqlManager.getValueFromVectorParam(
            feedbackEsistente, 0).getValue()).getTime());
        }
        
        obj = null;
        if (fase != null) {
          if (fase.getValue() == null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 1).getValue();
            if (obj != null) {
              faseEsec = SqlManager.getValueFromVectorParam(
                  feedbackEsistente, 1).longValue();
            }
          } else {
            faseEsec = new Long(fase.getValue());
          }
          
          obj = null;
          if (progresFase != null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 2).getValue();
            if (obj != null) {
              progresFaseEsec = (Long) obj;
            }
          } else {
            progresFaseEsec = progresFase;
          }
        }              
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 3).getValue();
        if (obj != null) {
        	stacom = (Long) obj;
        }
        
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 4).getValue();
        if (obj != null) {
        	msg = (String) obj;
        }
        
        it.toscana.rete.rfc.sitatort.FeedbackType feedback =
            new it.toscana.rete.rfc.sitatort.FeedbackType();
        
        if (dataFeedback != null) {
          Calendar date = Calendar.getInstance();
          date.setTimeInMillis(dataFeedback.getTime());
          feedback.setData(date);
        }

        if (stacom != null) {
        	feedback.setEsito(EsitoType.fromString(stacom.toString()));
        }
        
        if (faseEsec != null) {
          feedback.setScheda(FaseType.fromString("" + faseEsec));
        }
        if (progresFaseEsec != null) {
          feedback.setNum(progresFaseEsec.intValue());
        }
        
        if (msg != null) {
        	feedback.setMessaggio(new String[] {msg});
        }
        
        listaElencoFeedback.add(feedback);
	    }
	    return listaElencoFeedback;
  }
  
  /**
   * Estrae l'elenco dei feedback dell'osservatorio relativi ai flussi ricevuti.
   * 
   * @param cig
   * @param codiceGara
   * @param codiceLotto
   * @param fase
   * @param progresFase
   * @param listaElencoFeedback
   * @return
   * @throws SQLException
   * @throws GestoreException
   */
  private List<it.toscana.rete.rfc.sitatort.FeedbackType>
      getElencoFeedbackOsservatorio(String cig, Long codiceGara,
          Long codiceLotto, FaseType fase, Long progresFase)
          throws SQLException, GestoreException {

    List<it.toscana.rete.rfc.sitatort.FeedbackType> listaElencoFeedback = null;

    String queryFeedbackORT = 
       "select i.DATRIC, f.KEY03, f.KEY04, i.STACOM, i.MSG "
      +  "from W9FLUSSI f left outer join W9INBOX i on f.IDCOMUN=i.IDCOMUN "
      + "where f.CODOGG=? and f.KEY01=? and f.KEY02=? ";
    
    if (fase != null) {
      queryFeedbackORT = queryFeedbackORT.concat("and f.KEY03=" + fase.getValue() );
      if (progresFase != null) {
        queryFeedbackORT = queryFeedbackORT.concat(" and f.KEY04=" + progresFase);
      }
    }
    
    List< ? > listaFeedbackEsistenti = this.sqlManager.getListVector(queryFeedbackORT,
        new Object[] { cig, codiceGara, codiceLotto } );

    if (listaFeedbackEsistenti != null && !listaFeedbackEsistenti.isEmpty()) {
      listaElencoFeedback = new ArrayList<FeedbackType>();
      
      for (int i = 0; i < listaFeedbackEsistenti.size(); i++) {
        Vector< ? > feedbackEsistente = (Vector< ? >) listaFeedbackEsistenti.get(i);
        
        Timestamp dataFeedback = null;
        Long faseEsec = null;
        Long progresFaseEsec = null;
        Long numeroErrori = null;
        Long numXml = null;
        
        Object obj = SqlManager.getValueFromVectorParam(feedbackEsistente, 0).getValue();
        if (obj != null) {
          dataFeedback = (Timestamp) SqlManager.getValueFromVectorParam(
            feedbackEsistente, 0).getValue();
        }
        
        obj = null;
        if (fase != null) {
          if (fase.getValue() == null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 1).getValue();
            if (obj != null) {
              faseEsec = SqlManager.getValueFromVectorParam(
                  feedbackEsistente, 1).longValue();
            }
          } else {
            faseEsec = new Long(fase.getValue());
          }
          
          obj = null;
          if (progresFase != null) {
            obj = SqlManager.getValueFromVectorParam(
                feedbackEsistente, 2).getValue();
            if (obj != null) {
              progresFaseEsec = (Long) obj;
            }
          } else {
            progresFaseEsec = progresFase;
          }
        }              
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 3).getValue();
        if (obj != null) {
          numeroErrori = (Long) obj;
        }
        
        obj = null;
        obj = SqlManager.getValueFromVectorParam(
            feedbackEsistente, 4).getValue();
        if (obj != null) {
          numXml = (Long) obj;
        }
        
        it.toscana.rete.rfc.sitatort.FeedbackType feedback =
            new it.toscana.rete.rfc.sitatort.FeedbackType();
        
        if (dataFeedback != null) {
          Calendar date = Calendar.getInstance();
          date.setTime(dataFeedback);
          feedback.setData(date);
        }

        if (numeroErrori != null) {
          if (numeroErrori > 0) {
            feedback.setEsito(EsitoType.ERRORE);
          } else {
            feedback.setEsito(EsitoType.IMPORTATA);
          }
        } else {
          feedback.setEsito(EsitoType.IMPORTATA);
        }
        
        if (faseEsec != null) {
          feedback.setScheda(FaseType.fromString("" + faseEsec));
        }
        if (progresFaseEsec != null) {
          feedback.setNum(progresFaseEsec.intValue());
        }
        
        if (numXml != null) {
          String queryDescrAnomalia = 
            "select DESCRIZIONE from W9XMLANOM where CODGARA=? and CODLOTT=? and NUMXML=? order by NUM asc";
          
          List< ? > listaDescrizioneAnomalie = this.sqlManager.getListVector(
              queryDescrAnomalia, new Object[] { codiceGara, codiceLotto, numXml });
          
          if (listaDescrizioneAnomalie != null && !listaDescrizioneAnomalie.isEmpty()) {
            String[] arrayDescrizAnomalie = new String[listaDescrizioneAnomalie.size()];
            for (int j = 0; j < listaDescrizioneAnomalie.size(); j++) {
              
              Vector< ? > descrizAnomalia = (Vector< ? >) listaDescrizioneAnomalie.get(j);
              String descAnomalia = (String) SqlManager.getValueFromVectorParam(descrizAnomalia, 0).getValue();
              
              if (StringUtils.isNotEmpty(descAnomalia)) {
                arrayDescrizAnomalie[j] = descAnomalia;
              }
            }
            feedback.setMessaggio(arrayDescrizAnomalie);
          }
        }
        
        listaElencoFeedback.add(feedback);
      }
    }
    return listaElencoFeedback;
  }
  
  /**
   * Ritorna l'oggetto it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType 
   * contenente l'elenco delle schede/fasi esistenti per il CIG indicato,
   * compresa l'anagrafica gara-lotto. 
   *
   * @param cig Codice CIG
   * @param idgara Numero della gara
   * @param cfrup Codice fiscale del RUP
   * @param cfsa Codice fiscale della S.A.
   * @return Ritorna l'oggetto it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType 
   */
  public it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType getElencoSchede(
      String cig, String idgara, String cfrup, String cfsa) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getElencoSchede: inizio metodo");
    }
    
    it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType responseElencoSchede = null;
    FaseEsecuzioneType[] arrayElencoSchede = null;
    try {
      Long codiceGara = null;
      Long codiceLotto = null;

      // Estrazione di codice gara, codice lotto, codice tecnico e codice SA della
      // gara e lotto che da W9GARA.IDAVGARA = idgara, W9GARA.CODEIN= (sa con CFEIN=cfsa),
      // W9LOTT.CIG=cig, il cui rup (W9GARA.RUP) e' tecnico della sa con CFEIN=cfsa
      Vector< ? > datiGara = this.sqlManager.getVector(
          "select g.CODGARA, l.CODLOTT, g.RUP, g.CODEIN from W9GARA g, W9LOTT l " +
           "where g.IDAVGARA=? and g.CODGARA=l.CODGARA and l.CIG=? " +
             "and g.RUP=(select max(CODTEC) from TECNI where CFTEC=? " +
             "and CGENTEI=(select CODEIN from UFFINT where CFEIN=?)) " +
             "and g.CODEIN=(select CODEIN from UFFINT where CFEIN=?)",
          new Object[] { idgara, cig, cfrup, cfsa, cfsa });
      
      if (datiGara != null) {
        // Esiste nella base dati la gara e il lotto indicati dagli argomenti di questa funzione.
        codiceGara = new Long(SqlManager.getValueFromVectorParam(datiGara, 0).getStringValue());
        codiceLotto = new Long(SqlManager.getValueFromVectorParam(datiGara, 1).getStringValue());

        // Di default si inserisce sempre la fase anagrafica gara/lotto 
       
        List< ? > listaFasiEsistenti = this.sqlManager.getListVector(
            "select f.FASE_ESECUZIONE, f.NUM from W9FASI f, tab1 t " +
             "where t.tab1cod = 'W3020' and t.tab1tip = f.FASE_ESECUZIONE " +
               "AND f.CODGARA=? and f.CODLOTT=?" +
             "order by T.TAB1NORD ASC",
            new Object[] { codiceGara, codiceLotto } );
        
        if (listaFasiEsistenti != null && listaFasiEsistenti.size() > 0) {

          arrayElencoSchede = new FaseEsecuzioneType[listaFasiEsistenti.size() + 1];
          arrayElencoSchede[0] = new FaseEsecuzioneType();
          arrayElencoSchede[0].setCodiceFase(FaseType.FASE_988);
          
          for (int i = 0; i < listaFasiEsistenti.size(); i++) {
            Vector< ? > faseEsistente = (Vector< ? >) listaFasiEsistenti.get(i);
            Long faseEsec = new Long(SqlManager.getValueFromVectorParam(faseEsistente, 0).getStringValue());
            Long numEsec = new Long(SqlManager.getValueFromVectorParam(faseEsistente, 1).getStringValue());
            
            arrayElencoSchede[i+1] = new FaseEsecuzioneType();
            arrayElencoSchede[i+1].setCodiceFase(FaseType.fromValue(faseEsec.toString()));
            if (numEsec != null && numEsec.longValue() > 0) {
              arrayElencoSchede[i+1].setNum(numEsec.intValue());
            }
          }
        } else {
          arrayElencoSchede = new FaseEsecuzioneType[1];
          arrayElencoSchede[0] = new FaseEsecuzioneType();
          arrayElencoSchede[0].setCodiceFase(FaseType.FASE_988);
        }
       
        responseElencoSchede = new it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType();
        responseElencoSchede.setElencoSchede(arrayElencoSchede);
        responseElencoSchede.setSuccess(true);
      } else {
        // Non esiste in banca dati una gara e un lotto che rispettano tutti i criteri di ricerca.
        logger.error("Nella base dati non esite una gara con IDAVGARA=" + idgara
            + " associata ad lotto con CIG= " + cig
            + ", relativa alla stazione appaltante con UFFINT.CFEIN=" + cfsa
            + " e come RUP il tecnico della medesima stazione appaltante e con TECNI.CFTEC=" + cfrup);
        
        responseElencoSchede = new it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType();
        responseElencoSchede.setError("Nell'osservatorio non esiste gara e/o lotto che rispecchia i dati "
            + "indicati (IDGARA=" + idgara + ", CIG=" + cig + ", codice fiscale S.A.=" + cfsa
            + ", codice fiscale RUP=" + cfrup + ")");
        responseElencoSchede.setSuccess(false);
      }
    } catch (SQLException se) {
      logger.error("Errore nell'estrarre l'elenco delle fasi relative alla gara/lotto " +
      		"con IDGARA=" + idgara + ", CIG=" + cig + ", codice fiscale S.A.=" + cfsa
      		+ ", codice fiscale RUP=" + cfrup + ")", se);
      
      responseElencoSchede = new it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType();
      responseElencoSchede.setError(
          "Errore inaspettato nell'estrazione dell'elenco delle schede da parte del Ws Osservatorio");
      responseElencoSchede.setSuccess(false);
    } catch (Throwable t) {
      logger.error("Errore inaspettato nell'estrarre l'elenco delle fasi relative alla gara/lotto " +
          "con IDGARA=" + idgara + ", CIG=" + cig + ", codice fiscale S.A.=" + cfsa
          + ", codice fiscale RUP=" + cfrup + ")", t);
      
      responseElencoSchede = new it.toscana.rete.rfc.sitatort.ResponseElencoSchedeType();
      responseElencoSchede.setError(
          "Errore inaspettato nell'estrazione dell'elenco delle schede da parte del Ws Osservatorio");
      responseElencoSchede.setSuccess(false);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getElencoSchede: fine metodo");
    }
    
    return responseElencoSchede;
  }

  /**
   * Ritorna l'oggetto ResponseSchedaType contenente l'esito dell'operazione (success true/false),
   * l'XML della scheda richiesta (schedaXml) e l'eventuale stringa di errore. 
   * 
   * Nel creare la scheda richiesta viene creato lo stesso XML che SitatSA invia a ORT attraverso
   * il proxy-sitat.
   * Tuttavia sul flusso non viene effettuata la validazione XML, perché visto che non si sa quali
   * informazioni sono disponibili nella base dati di ORT e visto che gli XSD di definizione dei 
   * diversi flussi prevedono alcuni campi obbligatori, non si vuole creare vincoli sull'operazione
   * di recupero da parte di SitatSA della fasi esistenti in ORT.
   * 
   * @param cig Codice CIG
   * @param idgara Numero della gara
   * @param cfrup Codice fiscale del RUP
   * @param cfsa Codice fiscale della S.A.
   * @param faseEsecuzione FaseEsecuzioneType
   * @return
   */
  public ResponseSchedaType getScheda(String cig, String idgara, String cfrup, String cfsa,
      it.toscana.rete.rfc.sitatort.FaseEsecuzioneType faseEsecuzione) {
    
    StringBuilder strb = new StringBuilder("Parametri di ingresso: ");
    strb.append(", IdGara=");
    strb.append(idgara);
    strb.append(", CIG=");
    strb.append(cig);
    strb.append(", CF del RUP=");
    strb.append(cfrup);
    strb.append(", CF della SA=");
    strb.append(cfsa);
    strb.append(", fase=");
    strb.append(faseEsecuzione.getCodiceFase().getValue());
    if (faseEsecuzione.getNum() != null) {
      strb.append(", progressivo=" + faseEsecuzione.getNum());
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getScheda: inizio metodo. ".concat(strb.toString()));
    }
    
    ResponseSchedaType resultScheda = new ResponseSchedaType();
    
    // Controlli preliminari...
    /*
    deve esistere un lotto di gara con il CIG specificato;
    il codice fiscale del RUP deve coincidere con quello passato come parametro;
    il codice fiscale della stazione appaltante deve coincidere con quello passato come parametro;
    l’ID gara deve coincidere con quello passato come parametro;
    il lotto deve avere una fase con codice e numero progressivi indicati.
     */
    
    try {
      boolean esisteGara = UtilitySITAT.existsGara(idgara, this.sqlManager);
      boolean esisteLotto = UtilitySITAT.existsLotto(cig, this.sqlManager);
      boolean isCigLottoDellaGara = UtilitySITAT.isCigLottoDellaGara(idgara, cig, sqlManager);
      
      if (esisteGara && esisteLotto && isCigLottoDellaGara) {
        HashMap<String, Long> hm = UtilitySITAT.getCodGaraCodLottByCIG(cig, this.sqlManager);
        Long codiceGara = new Long(hm.get("CODGARA"));
        Long codiceLotto = new Long(hm.get("CODLOTT"));

        int codiceFase = Integer.parseInt(faseEsecuzione.getCodiceFase().getValue());
        Long progressivoFase = null;
        if (faseEsecuzione.getNum() != null) {
          progressivoFase = new Long(faseEsecuzione.getNum());
        }

        boolean esisteFase = false;
        
        if (FaseType.FASE_988.getValue().equals("" + codiceFase) || 
            FaseType.FASE_13.getValue().equals("" + codiceFase)) {
          esisteFase = true; 
          // esisteFase viene messa a true perche' in questo punto del codice le variabili
          // esisteGara, esisteLotto e isCigLottoDellaGara sono tutte a true.
        } else {
        	
          Long numAppa = new Long(1); 
          Long numeroAppalti = (Long) this.sqlManager.getObject(
          		"select count(NUM_APPA) from W9APPA a, W9LOTT l where a.CODGARA=l.CODGARA and a.CODLOTT=l.CODLOTT and l.CIG=?",
          		new Object[] { cig });
          
          if (numeroAppalti.longValue() > 1) {
          	numAppa = (Long) this.sqlManager.getObject(
            		"select max(NUM_APPA) from W9APPA a, W9LOTT l where a.CODGARA=l.CODGARA and a.CODLOTT=l.CODLOTT and l.CIG=?", new Object[] { cig });
          }
        	
          if (progressivoFase == null) {
            esisteFase = UtilitySITAT.existsFase(numAppa.intValue(), cig, codiceFase, this.sqlManager);
          } else {
            esisteFase = UtilitySITAT.existsFase(numAppa.intValue(), cig, codiceFase, progressivoFase.intValue(), this.sqlManager);
          }
        }

        if (esisteFase) {
          Vector<?> datiTecnicoUffint = this.sqlManager.getVector(
              "select t.CODTEC, t.SYSCON, u.CODEIN from TECNI t, UFFINT u, W9GARA g " 
            + " where UPPER(g.IDAVGARA) = ? "
              + " and g.CODEIN is not null "
              + " and g.CODEIN = u.CODEIN and u.CFEIN = ? "
              + " and t.CGENTEI = u.CODEIN and UPPER(t.CFTEC) = ? ", 
              new Object[] { idgara.toUpperCase(), cfsa.toUpperCase(), cfrup.toUpperCase()});
          
          String codiceTecnico = null;
          Long sysConRup = null;
          String codiceUffint = null;
          
          if (datiTecnicoUffint != null) {
            String strTemp = SqlManager.getValueFromVectorParam(datiTecnicoUffint, 0).getStringValue();
            if (StringUtils.isNotEmpty(strTemp)) {
              codiceTecnico = new String(strTemp);
            }
            strTemp = null;
            strTemp = SqlManager.getValueFromVectorParam(datiTecnicoUffint, 1).getStringValue();
            if (StringUtils.isNotEmpty(strTemp)) {
              sysConRup = new Long(strTemp);
            }
            
            strTemp = null;
            strTemp = SqlManager.getValueFromVectorParam(datiTecnicoUffint, 1).getStringValue();
            if (StringUtils.isNotEmpty(strTemp)) {
              codiceUffint = new String(strTemp);
            }
            strTemp = null;

            if (StringUtils.isNotEmpty(codiceTecnico) && sysConRup != null &&
                StringUtils.isNotEmpty(codiceUffint)) {
              
              DatiComuniBean datiComuni = new DatiComuniBean();
              datiComuni.setCodiceFase(codiceFase);
              datiComuni.setProgressivoFase(progressivoFase);
              datiComuni.setCfStazioneAppaltante(cfsa);
              datiComuni.setCodiceCompositore(sysConRup);
              datiComuni.setEsito(Boolean.TRUE);
              
              GaraLottoBean garaLottoBean = new GaraLottoBean();              
              garaLottoBean.setDatiComuni(datiComuni);
              garaLottoBean.setCodiceGara(codiceGara);
              garaLottoBean.setCodiceLotto(codiceLotto);
              
              XmlObject xmlObj = CreazioneXmlManager.getXmlGaraLottoFasi(
                  garaLottoBean, this.sqlManager, this.w9Manager);

              Boolean esito = datiComuni.getEsito();
              if (esito.booleanValue()) {
                resultScheda.setSchedaXML(xmlObj.toString());
                resultScheda.setSuccess(true);
                resultScheda.setError(null);
              } else {
                resultScheda.setError("errore");
                resultScheda.setSuccess(false);
                resultScheda.setSchedaXML(null);
              }
            } else {
              // Per qualche motivo non si e' riusciti a recuperare il CODTEC del
              // RUP e/o il SYSCON del RUP e/o il CODEIN della S.A.
              
            }
          } else {
            // non esiste nessun tecnico con TECNI.CFTEC=cfrup associato alla S.A. con UFFINT.CFEIN=cfsa
            // che sia RUP della gara con W9GARA.IDAVGARA=idgara
            
          }
          
        } else {
          if (progressivoFase != null) {
            logger.error("In base dati per il lotto con CIG=" + cig + " non esiste la fase="
                + codiceFase + " e progressino=" + progressivoFase);
          } else {
            logger.error("In base dati per il lotto con CIG=" + cig + " non esiste la fase=" + codiceFase);
          }
          resultScheda.setSuccess(false);
          resultScheda.setError("scrivere un messaggio di errore opportuno...");
        }

      } else {
        if (!esisteGara) {
          logger.error("In base dati non esiste la gara con Numero gara=" + idgara
              + ", ma esiste il lotto con CIG=" + cig
              + ". Parametri della richiesta: ".concat(strb.toString()));
          resultScheda.setSuccess(false);
          resultScheda.setError("WsOsservatorio: in base dati non esiste la gara con Numero gara=" + idgara);
        } else if (!esisteLotto) {
          logger.error("In base dati esiste la gara con Numero gara=" + idgara
              + ", ma non esiste il lotto con CIG=" + cig
              + ". Parametri della richiesta: ".concat(strb.toString()));
          resultScheda.setSuccess(false);
          resultScheda.setError("WsOsservatorio: in base dati esiste la gara con Numero gara=" + idgara
              + ", ma esiste il lotto con CIG=" + cig);
        } else if (!isCigLottoDellaGara) {
          logger.error("In base dati esistono la gara con Numero gara="
              + idgara + " e il lotto con CIG=" + cig +
              ", ma il lotto non appartiene alla gara indicata. Parametri della richiesta :".concat(strb.toString()));
          resultScheda.setSuccess(false);
          resultScheda.setError("WsOsservatorio: in base dati esistono la gara con Numero gara=" + idgara
              + " e il lotto con CIG=" + cig +
              ", ma il lotto non appartiene alla gara indicata.");
        }
      }
    } catch (SQLException se) {
      logger.error("Errore nell'estrazione dei dati della fase "
          + faseEsecuzione.getCodiceFase().getValue()
          + ". Parametri della richiesta: ".concat(strb.toString()), se);
    } catch (GestoreException ge) {
      logger.error("Errore nell'estrazione dei dati della fase "
          + faseEsecuzione.getCodiceFase().getValue()
          + ". Parametri della richiesta: ".concat(strb.toString()), ge);
    } catch (ParseException pe) {
      logger.error("Errore nell'estrazione dei dati della fase "
          + faseEsecuzione.getCodiceFase().getValue()
          + ". Parametri della richiesta: ".concat(strb.toString()), pe);
    } catch (IOException ie) {
      logger.error("Errore nell'estrazione dei dati della fase "
          + faseEsecuzione.getCodiceFase().getValue()
          + ". Parametri della richiesta: ".concat(strb.toString()), ie);
    } finally {
      resultScheda.setSuccess(false);
      resultScheda.setError("Errore inaspettato nella creazione della scheda richiesta. Si prega di riprovare pi&ugrave; tardi. ");
      resultScheda.setSchedaXML(null);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getScheda: fine metodo".concat(strb.toString()));
    }
    return resultScheda;
  }
  
}
