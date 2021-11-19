package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.bl.EsportazioneXMLSIMOGManager;
import it.eldasoft.w9.bl.delegate.AbstractRequestHandler;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.common.StatoComunicazione;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.message.SOAPBody;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;

/**
 * Action per eseguire l'import di flussi giunti nella Inbox di ORT in stato di errore/warning.
 */
public class EseguiImportazioneOrtAction extends ActionBaseNoOpzioni {

  static Logger              		 	logger      = Logger.getLogger(EseguiImportazioneOrtAction.class);
  private SqlManager          		sqlManager;
  private static final String 		SQL_GET_XML = "select XML, IDEGOV, MSG, DATRIC from W9INBOX where IDCOMUN = ?";
  private AbstractRequestHandler requestHandler;
  private EsportazioneXMLSIMOGManager esportazioneXMLSIMOGManager;
  
  public void setEsportazioneXMLSIMOGManager(EsportazioneXMLSIMOGManager esportazioneXMLSIMOGManager) {
		this.esportazioneXMLSIMOGManager = esportazioneXMLSIMOGManager;
  }
  
  /**
   * @param requestHandler
   *        requestHandler da settare internamente alla classe.
   */
  public void setRequestHandler(AbstractRequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }

  /**
   * @param sqlManager
   *        sqlManager da settare internamente alla classe.
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * @see it.eldasoft.gene.commons.web.struts.ActionBase#runAction(org.apache.struts.action.ActionMapping,
   *      org.apache.struts.action.ActionForm,
   *      javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    String target = CostantiGeneraliStruts.FORWARD_OK;
    boolean successProcess = false;
    String xmlEvento = null;

    try { 

      Long idcomun = new Long((String) request.getParameter("idcomun"));
      /* Significato dei valori ammessi per il parameter "azione":
       * azione = 1: importazione forzata di un flusso in stato di warning
       * azione = 2: annullamento dell'importazione di un flusso
       * azione = 3: importazione forzata di un flusso in stato di errore
       * azione = 4: accetta richiesta di cancellazione */
      String azione = request.getParameter("azione");
      //motivazione presente solo per Richieste di cancellazione rifiutate
      String motivazione = request.getParameter("motivazione");
      Vector< ? > datiW9INBOX = sqlManager.getVector(SQL_GET_XML, new Object[]{new Long(idcomun)});
      String xml = (String) SqlManager.getValueFromVectorParam(datiW9INBOX, 0).getValue();
      String idegov = (String) SqlManager.getValueFromVectorParam(datiW9INBOX, 1).getValue();
      //String msgg = (String) SqlManager.getValueFromVectorParam(datiW9INBOX, 2).getValue();
      //Object dataRicW9INBOX = SqlManager.getValueFromVectorParam(datiW9INBOX, 3).getValue();

      // estrazione della richiesta inviata dalla stazione appaltante, contenuta
      // nel body della busta SOAP
      try {
        SOAPEnvelope envelope = new SOAPEnvelope(new ByteArrayInputStream(xml.getBytes()));

        SOAPBody body = (SOAPBody) envelope.getBody();
        java.util.Iterator< ? > it = body.getChildElements();
        if (it.hasNext()) {
          org.apache.axis.message.MessageElement bodyElement = (org.apache.axis.message.MessageElement) it.next();
          xmlEvento = bodyElement.getAsString();
        }
      } catch (Exception e) {
        // alquanto improbabile, dato che i messaggi che arrivano all'Osservatorio
        // sono controllati e inoltrati dal proxy applicativo
        logger.error("La richiesta non corrisponde ad una busta SOAP", e);
      }

      TransactionStatus status = null;
      try {
        // si avvia la transazione per l'aggiornamento del DB
        status = this.sqlManager.startTransaction();

        // si creano tutte le colonne di W9INBOX
        DataColumn chiaveW9inbox = new DataColumn("W9INBOX.IDCOMUN",
            new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(idcomun)));
        DataColumn statoComunicazione = new DataColumn("W9INBOX.STACOM",
            new JdbcParametro(JdbcParametro.TIPO_NUMERICO, 
                StatoComunicazione.STATO_WARNING.getStato()));

        // effettuato l'inserimento, si aggiornano gli original value con i value,
        // in modo da consentire successivi update del record; inoltre si imposta
        // la chiave in modo da consentire l'update per chiave, altrimenti
        // impossibile
        chiaveW9inbox.setChiave(true);
        chiaveW9inbox.setObjectOriginalValue(chiaveW9inbox.getValue());
        statoComunicazione.setObjectOriginalValue(statoComunicazione.getValue());

        DataColumnContainer datiAggiornamento = new DataColumnContainer(
            new DataColumn[]{chiaveW9inbox, statoComunicazione});

        Long idFlusso = null;
        Long codiceGara = null;
        Long codiceLotto = null;
        Long faseEsecuzione = null;
        Long progressivoFase = null;
        Long numappa = null;
        
        // Preparazione dei dati necessari ai vari tipi di importazione dei flussi
        if ("1".equals(azione) || "3".equals(azione) || "4".equals(azione)) {
          Vector< ? > datiW9FLUSSI = this.sqlManager.getVector(
              "select idflusso, area, key01, key02, key03, key04, tinvio2, datinv, "
              + "noteinvio, xml, codcomp, idcomun, cfsa, codogg from w9flussi where idcomun=? ",
              new Object[]{idcomun});
          
          datiAggiornamento.addColumn("W9INBOX.IDEGOV", JdbcParametro.TIPO_TESTO, idegov);
          datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, null);

          datiAggiornamento.setValue("W9INBOX.STACOM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
              StatoComunicazione.STATO_IMPORTATA.getStato()));
          datiAggiornamento.addColumn("W9FLUSSI.IDCOMUN", JdbcParametro.TIPO_NUMERICO, idcomun);
          
          int indiceDatiAggiornamento = 0;
          idFlusso = (Long) SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue(); 
          datiAggiornamento.addColumn("W9FLUSSI.IDFLUSSO", JdbcParametro.TIPO_NUMERICO, idFlusso);
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 1
          datiAggiornamento.addColumn("W9FLUSSI.AREA", JdbcParametro.TIPO_NUMERICO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 2
          codiceGara = (Long) SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue();
          datiAggiornamento.addColumn("W9FLUSSI.KEY01", JdbcParametro.TIPO_NUMERICO, codiceGara);
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 3
          codiceLotto = (Long) SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue();
          datiAggiornamento.addColumn("W9FLUSSI.KEY02", JdbcParametro.TIPO_NUMERICO, codiceLotto);
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 4
          faseEsecuzione = (Long) SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue();
          datiAggiornamento.addColumn("W9FLUSSI.KEY03", JdbcParametro.TIPO_NUMERICO, faseEsecuzione);
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 5 
          progressivoFase = (Long) SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue();
          datiAggiornamento.addColumn("W9FLUSSI.KEY04", JdbcParametro.TIPO_NUMERICO, progressivoFase);
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 6
          datiAggiornamento.addColumn("W9FLUSSI.TINVIO2", JdbcParametro.TIPO_NUMERICO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 7
          datiAggiornamento.addColumn("W9FLUSSI.DATINV", JdbcParametro.TIPO_DATA,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 8
          datiAggiornamento.addColumn("W9FLUSSI.NOTEINVIO", JdbcParametro.TIPO_TESTO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 9
          datiAggiornamento.addColumn("W9FLUSSI.XML", JdbcParametro.TIPO_TESTO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 10
          datiAggiornamento.addColumn("W9FLUSSI.CODCOMP", JdbcParametro.TIPO_NUMERICO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 11
          datiAggiornamento.addColumn("W9FLUSSI.IDCOMUN", JdbcParametro.TIPO_NUMERICO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 12
          datiAggiornamento.addColumn("W9FLUSSI.CFSA", JdbcParametro.TIPO_TESTO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          indiceDatiAggiornamento++; // indiceDatiAggiornamento = 13
          datiAggiornamento.addColumn("W9FLUSSI.CODOGG", JdbcParametro.TIPO_TESTO,
              SqlManager.getValueFromVectorParam(datiW9FLUSSI, indiceDatiAggiornamento).getValue());
          numappa = (Long) this.sqlManager.getObject("SELECT NUM_APPA FROM W9FASI WHERE CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = ?", new Object[]{codiceGara, codiceLotto, faseEsecuzione, progressivoFase});
        } else if ("2".equals(azione)) {
          datiAggiornamento.setValue("W9INBOX.STACOM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
              StatoComunicazione.STATO_ERRATA.getStato()));
          if (motivazione != null) {
        	  datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "Richiesta rifiutata. " + motivazione);
          }
        }

        // Esecuzione dell'import dei dati del flusso attraverso la catena degli handler
        if ("1".equals(azione)) {
          this.requestHandler.processEvento(xmlEvento, datiAggiornamento, true);

          // Per forzare l'aggiornamento del campo W9INBOX.MSG nel caso l'importazione sia andata a buon fine
          if (datiAggiornamento.isColumn("W9INBOX.STACOM")
              && datiAggiornamento.getColumn("W9INBOX.STACOM").getValue().getValue().equals(
                  StatoComunicazione.STATO_IMPORTATA.getStato())){
            DataColumn dataColumnMsg = datiAggiornamento.getColumn("W9INBOX.MSG");
            dataColumnMsg.setObjectValue(null);
            dataColumnMsg.setObjectOriginalValue(" ");
          }
        } else if ("3".equals(azione)) {
          this.requestHandler.processEvento(xmlEvento, datiAggiornamento, false);

          // Per forzare l'aggiornamento del campo W9INBOX.MSG nel caso l'importazione sia andata a buon fine
          if (datiAggiornamento.isColumn("W9INBOX.STACOM")
              && datiAggiornamento.getColumn("W9INBOX.STACOM").getValue().getValue().equals(
                  StatoComunicazione.STATO_IMPORTATA.getStato())){
            DataColumn dataColumnMsg = datiAggiornamento.getColumn("W9INBOX.MSG");
            dataColumnMsg.setObjectValue(null);
            dataColumnMsg.setObjectOriginalValue(" ");
          }
        } else if ("4".equals(azione)) {
        	if (this.isEliminabile(datiAggiornamento, numappa)) {
        		if (codiceGara != null && codiceLotto != null && faseEsecuzione != null && progressivoFase != null) {
	        		int result = this.esportazioneXMLSIMOGManager.eliminaSchedaSimogInORT(datiAggiornamento, request, status);
	        		switch (result) {
    					case 0://Conclusione positiva
    						datiAggiornamento.setValue("W9INBOX.STACOM", StatoComunicazione.STATO_IMPORTATA.getStato());
    						datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "La richiesta di cancellazione si è conclusa positivamente");
    						aggiungiMessaggio(request, "info.generico", "La richiesta di cancellazione si è conclusa positivamente");
    						break;
    					case 1://Abort procedura
    						datiAggiornamento.setValue("W9INBOX.STACOM", StatoComunicazione.STATO_WARNING.getStato());
    						datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "La richiesta di cancellazione è stata interrotta per problemi con il servizio SIMOG");
    						aggiungiMessaggio(request, "warnings.generico", "La richiesta di cancellazione è stata interrotta per problemi con il servizio SIMOG");
    						break;
    					case 2://Conclusione negativa
    						datiAggiornamento.setValue("W9INBOX.STACOM", StatoComunicazione.STATO_ERRATA.getStato());
    						//datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "La richiesta di cancellazione si è conclusa negativamente");
    						aggiungiMessaggio(request, "errors.generico", "La richiesta di cancellazione si è conclusa negativamente");
    						break;
    					case 3://La scheda non esiste in Simog
    						datiAggiornamento.setValue("W9INBOX.STACOM", StatoComunicazione.STATO_IMPORTATA.getStato());
    						datiAggiornamento.addColumn("W9INBOX.MSG", JdbcParametro.TIPO_TESTO, "La richiesta di cancellazione fa riferimento ad una scheda non presente in SIMOG");
    						aggiungiMessaggio(request, "warnings.generico", "La richiesta di cancellazione fa riferimento ad una scheda non presente in SIMOG");
    						break;
    					default:
    						break;
    					}
        		} else {
        			// Errore nel record in cancellazione in W9LFUSSI perche' e' nullo almeno uno dei campo
        			// key01, key02, key03 e key04 che rappresentano rispettivamente codice gara, codice lotto,
        			// fase esecuzione e progressivo della fase della fase che si vuole cancellare.
        			logger.error("Cancellazione fase interrotta per inconsistenza dei dati. Nella tabella W9FLUSSI il record con idFlusso="
       					+ idFlusso + " e idComun= " + idcomun + " ha almeno uno dei campi key01,key02,key03,key04 non valorizzati. "
       					+ "Questo non pemette di salire alla fase che si desidera cancellare. Si consiglia di risalire alla richiesta di cancellazione a partire dal campo W9FLUSSI.CODOGG");
        			
        			aggiungiMessaggio(request, "errors.generico", "Non è possibile proseguire con la cancellazione della fase per incoerenza dei dati. Contattare l'assistenza");
            	target = CostantiGeneraliStruts.FORWARD_OK;
            	throw new GestoreException("Non è possibile proseguire con la cancellazione della fase per incoerenza dei dati presenti in base dati. Contattare ", "errors.generico");
        		}
        	} else {
        		aggiungiMessaggio(request, "errors.generico", "Non è possibile proseguire con la cancellazione della fase. Esistono schede che hanno precedenza sulla cancellazione");
        	  target = CostantiGeneraliStruts.FORWARD_OK;
        	  throw new GestoreException("Non è possibile proseguire con la cancellazione della fase. Esistono schede che hanno precedenza sulla cancellazione", "errors.generico");
        	}
        }

        // si effettua il process della richiesta
        // si aggiorna quindi lo stato della comunicazione e l'eventuale messaggio
        // di errore, modificato dalla catena degli handler
        datiAggiornamento.update("W9INBOX", this.sqlManager);
        successProcess = true;
      } catch (DataAccessException e) {
        logger.error("Errore inaspettato durante l'interazione con la base dati", e);
      } catch (SQLException e) {
        if (status == null) {
          logger.error("Impossibile aprire la transazione per salvare i dati letti nel messaggio", e);
        } else {
          logger.error("Si e' verificato un errore inaspettato durante l'interazione con la base dati", e);
        }
      } catch (Throwable e) {
        // per sicurezza si blinda il codice in modo da avere ogni eccezione
        // catchata e tracciata nel log
        logger.error("Errore inaspettato durante l'elaborazione dell'operazione", e);
      } finally {
        if (status != null) {
          try {
            if (successProcess) {
              this.sqlManager.commitTransaction(status);
            } else {
              this.sqlManager.rollbackTransaction(status);
            }
          } catch (SQLException e) {
          }
        }
      }

    } catch (SQLException e) {
      logger.error("Problema nel recupero della busta soap dalla base dati.", e);
      aggiungiMessaggio(request, "errors.importaORT.comunicazionewarning");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }

    return mapping.findForward(target);
  }
  
  //viene verificato se esiste la possibilità di cancellare la fase richiesta
  private boolean isEliminabile(final DataColumnContainer datiAggiornamento, final Long numappa) {
	  boolean result = true;
	  try {
		  Long codgara = datiAggiornamento.getLong("W9FLUSSI.KEY01");
		  Long codlott = datiAggiornamento.getLong("W9FLUSSI.KEY02");
		  Long tipofase = datiAggiornamento.getLong("W9FLUSSI.KEY03");
		  String sqlEsistenzaFasiSuccessive = "select count(*) from W9FASI where CODGARA=? and CODLOTT=? and NUM_APPA=? and FASE_ESECUZIONE in (TO_REPLACE)";
      	String sqlToReplace = "";
      	Object[] sqlParams = null;
      	switch (tipofase.intValue()) {
      	case CostantiW9.COMUNICAZIONE_ESITO:
        
      		sqlToReplace = "?, ?, ?, ?, ?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.STIPULA_ACCORDO_QUADRO, 
      				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, 
      				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
      				CostantiW9.SUBAPPALTO,
      				CostantiW9.ISTANZA_RECESSO,
      				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA,
      				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI};
      		break;

      	case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
        
      		sqlToReplace = "?, ?, ?, ?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.STIPULA_ACCORDO_QUADRO, 
      				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA,
      				CostantiW9.SUBAPPALTO,
      				CostantiW9.ISTANZA_RECESSO,
      				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
      				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI};
      		break;

      	case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:

      		sqlToReplace = "?, ?, ?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.STIPULA_ACCORDO_QUADRO,
      				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
      				CostantiW9.SUBAPPALTO,
      				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA,
      				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI};
      		break;

      	case CostantiW9.STIPULA_ACCORDO_QUADRO:
        
      		sqlToReplace = "?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
            	CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, 
            	CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO};
      		break;

      	case CostantiW9.ADESIONE_ACCORDO_QUADRO:
        
      		sqlToReplace = "?, ?, ?, ?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, 
      				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
      				CostantiW9.SUBAPPALTO,
      				CostantiW9.ISTANZA_RECESSO,
      				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
      				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI};
      		break;
        
      	case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
        
      		sqlToReplace = "?, ?, ?, ?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA,
      				CostantiW9.SOSPENSIONE_CONTRATTO,
      				CostantiW9.ACCORDO_BONARIO,
      				CostantiW9.VARIANTE_CONTRATTO,
      				CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
      				CostantiW9.APERTURA_CANTIERE};
      		break;

      	case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
        
      		sqlToReplace = "?, ?, ?";
      		sqlParams = new Object[]{ codgara, codlott, numappa,
      				CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, 
      				CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
      				CostantiW9.APERTURA_CANTIERE};
      		break;
      	case CostantiW9.ANAGRAFICA_GARA_LOTTI:
      	case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
      		sqlToReplace = "?";
      		sqlParams = new Object[]{ codgara, codlott, numappa, CostantiW9.COLLAUDO_CONTRATTO};
      		break;
      		
      	case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
      	case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
      	case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
      	case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
      	case CostantiW9.COLLAUDO_CONTRATTO:
      	case CostantiW9.SOSPENSIONE_CONTRATTO:
      	case CostantiW9.VARIANTE_CONTRATTO:
      	case CostantiW9.ACCORDO_BONARIO:
      	case CostantiW9.SUBAPPALTO:
      	case CostantiW9.ISTANZA_RECESSO:
      	case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
      	case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
      	case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
      	case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
      	case CostantiW9.APERTURA_CANTIERE:
      	default:
      		break;
      	}
      
      	if (sqlToReplace.length() > 0 && sqlParams != null) {
      		Long numeroFasiSuccessiveDipendenti = (Long) sqlManager.getObject(
      				StringUtils.replace(sqlEsistenzaFasiSuccessive, "TO_REPLACE", sqlToReplace), sqlParams);
      		if (numeroFasiSuccessiveDipendenti.longValue() > 0) {
      			result = false;
      		}
      	}
	  } catch (Exception e) { 
	      logger.error("Errore durante la verifica della eliminibilità della scheda", e);
	  }
	  return result;
  }

}