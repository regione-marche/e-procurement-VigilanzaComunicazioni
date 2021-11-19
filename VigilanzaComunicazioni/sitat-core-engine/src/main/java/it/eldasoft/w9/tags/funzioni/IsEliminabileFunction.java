package it.eldasoft.w9.tags.funzioni;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

/**
 * Classe per la determinazione se un utente puo' oppure no eliminare una gara.
 */
public class IsEliminabileFunction extends AbstractFunzioneTag {

  private static final String CANCELLA_NO                 = "no";
  private static final String CANCELLA_SI                 = "si";

  private static final String ANNULLA_NO                  = "no";
  private static final String ANNULLA_SI                  = "si";

  private static final String SQL_ESISTENZA_LOTTI               = "select codgara from W9LOTT where codgara = ? ";
  private static final String SQL_PROVENIENZA_GARA              = "select PROV_DATO from W9GARA where codgara = ? ";
  
  private static final String SQL_ESISTENZA_FLUSSI_GARA         = "select key01 from W9FLUSSI where area = 2 and key01 = ?";
  private static final String SQL_ESISTENZA_FLUSSI_PROGRAMMA    = "select key01 from W9FLUSSI where area = 4 and key01 = ?";
  private static final String SQL_ESISTENZA_FLUSSI_FASE_LOTTO   = "select key01 from W9FLUSSI where area = 1 and key01 = ? and key02 = ?";
  private static final String SQL_ESISTENZA_FASI_LOTTO          = "select codlott from W9FASI where codgara = ? and codlott = ?";
  private static final String SQL_ESISTENZA_FLUSSI_GARA_ENTINAZ = "select key01 from W9FLUSSI where area = 5 and key01 = ?";
  
  public IsEliminabileFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params)
  throws JspException {
    String cancellabile = CANCELLA_NO;
    String annullabile  = "";
    
    try {
      SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
          pageContext, SqlManager.class);
      String key = params[1].toString();
      String entita = params[2].toString();
      if (key == null || key.equals("")) {
        key = UtilityTags.getParametro(pageContext, UtilityTags.DEFAULT_HIDDEN_KEY_TABELLA);
      }
      
      DataColumnContainer keys = new DataColumnContainer(key);
      if (entita.equals("PIATRI")) {
        Long contri = (keys.getColumnsBySuffix("CONTRI", true))[0].getValue().longValue();
        List< ? > flussiCollegati = sqlManager.getListVector(SQL_ESISTENZA_FLUSSI_PROGRAMMA, new Object[] { contri });

        if (flussiCollegati != null && flussiCollegati.size() == 0) {
          cancellabile = CANCELLA_SI;
        }
      }
      
      if (entita.equals("W9GARA")) {
        // Una fase non e' eliminabile se la fase stessa e' gia' stata inviata (cioe' se esiste in W9FLUSSI il relativo record).
        Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        List< ? > lottiCollegati = sqlManager.getListVector(SQL_ESISTENZA_LOTTI, new Object[] { codgara });
        List< ? > flussiCollegati = sqlManager.getListVector(SQL_ESISTENZA_FLUSSI_GARA, new Object[] { codgara });
        Long provDato = (Long) sqlManager.getObject(SQL_PROVENIENZA_GARA, new Object[]{codgara});

        if ((flussiCollegati != null && flussiCollegati.size() == 0) && ((lottiCollegati != null && lottiCollegati.size() == 0) || new Long(4).equals(provDato))) {
          cancellabile = CANCELLA_SI;
        }
      }
      if (entita.equals("W9LOTT")) {
        // Una fase non e' eliminabile se la fase stessa e' gia' stata inviata (cioe' se esiste in W9FLUSSI il relativo record).
        Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        Long codlott = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        List< ? > fasiCollegate = sqlManager.getListVector(SQL_ESISTENZA_FASI_LOTTO, new Object[] { codgara, codlott });

        List< ? > flussiCollegatiFase = sqlManager.getListVector(SQL_ESISTENZA_FLUSSI_FASE_LOTTO, new Object[] { codgara, codlott});

        if ((flussiCollegatiFase != null && flussiCollegatiFase.size() == 0) && (fasiCollegate != null && fasiCollegate.size() == 0)) {
          cancellabile = CANCELLA_SI;
        }
      }
      if (entita.equals("W9FASI")) {
        Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        Long codlott = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
        Long tipofase = (keys.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().longValue();
        Long progfase = (keys.getColumnsBySuffix("NUM", true))[0].getValue().longValue();
        Long numappa = new Long(this.getRequest().getSession().getAttribute("aggiudicazioneSelezionata").toString());
        
      	cancellabile = CANCELLA_SI;
      	annullabile  = ANNULLA_NO;

        // Se la gara e' una gara delegata e la S.A. attiva coincide con la S.A. delegante, allora:
        // - se W9GARA.ID_F_DELEGATE = 1 o 2 non si possono cancellare le fasi Imprese partecipanti,
        // Esito e Aggiudicazione (o equivalenti)
        // - se W9GARA.ID_F_DELEGATE = 4 non si possono cancellare le fasi Imprese partecipanti e Esito
        String codeinStazAppAttiva = (String) pageContext.getSession().getAttribute(
            CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
        String cfStazAppAttiva = (String) sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", 
            new Object[] { codeinStazAppAttiva });
        String cfSaAgente = (String) sqlManager.getObject("select CF_SA_AGENTE from W9GARA where CODGARA=?", 
            new Object[] { codgara });
        Long idFDelegate = (Long) sqlManager.getObject("select ID_F_DELEGATE from W9GARA where CODGARA=?", 
            new Object[] { codgara });
        
        boolean isGaraDelegataConAggiudicazione = idFDelegate != null 
          && ("1".equals(idFDelegate.toString()) || "2".equals(idFDelegate.toString())) 
            && StringUtils.equals(cfStazAppAttiva, cfSaAgente) && 
              ( tipofase.intValue() == CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI || 
                  tipofase.intValue() == CostantiW9.COMUNICAZIONE_ESITO || 
                    tipofase.intValue() == CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA || 
                      tipofase.intValue() == CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE || 
                        tipofase.intValue() == CostantiW9.ADESIONE_ACCORDO_QUADRO); 
        
        boolean isGaraDelegataConPropostaDiAggiudicazione = idFDelegate != null 
          && "4".equals(idFDelegate.toString()) && StringUtils.equals(cfStazAppAttiva, cfSaAgente)
            && ( tipofase.intValue() == CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI || 
              tipofase.intValue() == CostantiW9.COMUNICAZIONE_ESITO ); 
        
        if (isGaraDelegataConAggiudicazione || isGaraDelegataConPropostaDiAggiudicazione) {
          cancellabile = CANCELLA_NO;
          annullabile  = ANNULLA_NO;
        } else {
        	// Una fase non e' eliminabile se la fase stessa e' necessaria per l'abilitazione di una fase (gia' esistente, anche se non inviata). 
        	// Ad esempio: non puo' essere eliminata la fase di aggiudicazione se e' presente la fase iniziale.
        	String sqlEsistenzaFasiSuccessive = "select count(*) from W9FASI where CODGARA=? and CODLOTT=? and NUM_APPA=? and FASE_ESECUZIONE in (TO_REPLACE)";
        	
        	// Query per determinare quante fra le fasi successive alla fase corrente sono nello stato 'inviato' (IDFLUSSO is not null && TINVIO2 > 0)
        	String sqlFasiSuccessiveInviateOAnnullate = 
        			"select count(*) from V_W9INVIIFASI where CODGARA=? and CODLOTT=? and NUM_APPA=? and FASE_ESECUZIONE in (TO_REPLACE) "
        					+ "and IDFLUSSO is not NULL and TINVIO2 > 0";

        	Vector<JdbcParametro> vectorFase = null;
        	
        	if (UtilitySITAT.isConfigurazioneVigilanza()) {
        		// in cfg Vigilanza una fase non puo' essere mai nello stato di richiesta di annullamento perche' 
        		// la richiesta di annullamento di invio e' fatta direttamente su SIMOG e non all'osservatorio.
        		// Quindi la condizione TINVIO2 = -1 equivale ad fase annullata in cfg SA-OR
        		vectorFase = (Vector<JdbcParametro>) sqlManager.getVector(
          			"select (case when (IDFLUSSO is not null and TINVIO2 > 0) then 'true' else 'false' end) as isFaseInviata, "
          					+ "'false' as isRichiestoAnnullamentoFase, "
          					+ "(case when (IDFLUSSO is not null and TINVIO2 < 0) then 'true' else 'false' end) as isFaseAnnullata "
          				+ "from V_W9INVIIFASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=? and NUM_APPA=?",
        					new Object[] { codgara, codlott, tipofase, progfase, numappa });
        	} else {
        		vectorFase = (Vector<JdbcParametro>) sqlManager.getVector(
        				"select (case when (IDFLUSSO is not null and TINVIO2 > 0) then 'true' else 'false' end) as isFaseInviata, "
          					+ "(case when (IDFLUSSO is not null and TINVIO2 = -1) then 'true' else 'false' end) as isRichiestoAnnullamentoFase, "
          					+ "(case when (IDFLUSSO is not null and TINVIO2 = -2) then 'true' else 'false' end) as isFaseAnnullata "
          				+ "from V_W9INVIIFASI where CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? and NUM=? and NUM_APPA=?", 
          					new Object[] { codgara, codlott, tipofase, progfase, numappa });
        	}
        	
        	boolean isFaseInviata = StringUtils.equals("true", vectorFase.get(0).getStringValue());
        	boolean isRichiestoAnnullamentoFase = StringUtils.equals("true", vectorFase.get(1).getStringValue());
        	boolean isFaseAnnullata = StringUtils.equals("true", vectorFase.get(2).getStringValue());
        	
        	String sqlToReplace = "";
        	Object[] sqlParams = null;
        	
        	String sqlToReplaceInvioFasiSuccessive = "";
        	Object[] sqlParamsInvioFasiSuccessive = null;

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
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		
        		sqlToReplaceInvioFasiSuccessive = "?, ?, ?, ?, ?, ?, ?";
        		sqlParamsInvioFasiSuccessive = new Object[] { codgara, codlott, numappa,
        				CostantiW9.STIPULA_ACCORDO_QUADRO, 
        				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, 
        				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ISTANZA_RECESSO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA,
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		break;

        	case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
          
        		sqlToReplace = "?, ?, ?, ?, ?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.STIPULA_ACCORDO_QUADRO, 
        				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ISTANZA_RECESSO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		
        		sqlToReplaceInvioFasiSuccessive = "?, ?, ?, ?, ?, ?";
        		sqlParamsInvioFasiSuccessive = new Object[] { codgara, codlott, numappa,
        				CostantiW9.STIPULA_ACCORDO_QUADRO, 
        				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ISTANZA_RECESSO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		break;

        	case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:

        		sqlToReplace = "?, ?, ?, ?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.STIPULA_ACCORDO_QUADRO,
        				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA,
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		
        		sqlToReplaceInvioFasiSuccessive = "?, ?, ?, ?, ?";
        		sqlParamsInvioFasiSuccessive = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.STIPULA_ACCORDO_QUADRO,
        				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA,
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		break;

        	case CostantiW9.STIPULA_ACCORDO_QUADRO:
          
        		sqlToReplace = "?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa,
              	CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, 
              	CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO };
        		
       			sqlToReplaceInvioFasiSuccessive = "?, ?";
       			sqlParamsInvioFasiSuccessive = new Object[]{ codgara, codlott, numappa,
       					CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, 
       					CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO };
        		break;

        	case CostantiW9.ADESIONE_ACCORDO_QUADRO:
          
        		sqlToReplace = "?, ?, ?, ?, ?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, 
        				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ISTANZA_RECESSO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };

        		sqlToReplaceInvioFasiSuccessive = "?, ?, ?, ?, ?, ?";
        		sqlParamsInvioFasiSuccessive = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, 
        				CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO,
        				CostantiW9.SUBAPPALTO,
        				CostantiW9.ISTANZA_RECESSO,
        				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, 
        				CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI };
        		break;
          
        	case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
          
        		sqlToReplace = "?, ?, ?, ?, ?, ?, ?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.SOSPENSIONE_CONTRATTO,
        				CostantiW9.ACCORDO_BONARIO,
        				CostantiW9.VARIANTE_CONTRATTO,
        				CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
        				CostantiW9.APERTURA_CANTIERE,
        				CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO };
        		
      			sqlToReplaceInvioFasiSuccessive = "?, ?, ?, ?, ?, ?, ?, ?";
      			sqlParamsInvioFasiSuccessive = new Object[]{ codgara, codlott, numappa,
        				CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.SOSPENSIONE_CONTRATTO,
        				CostantiW9.ACCORDO_BONARIO,
        				CostantiW9.VARIANTE_CONTRATTO,
        				CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
        				CostantiW9.APERTURA_CANTIERE,
        				CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA,
        				CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO };
        		break;

        	case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:

        		sqlToReplace = "?, ?, ?";
        		sqlParams = new Object[]{ codgara, codlott, numappa, 
        				CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, 
        				CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
        				CostantiW9.APERTURA_CANTIERE };

      			sqlToReplaceInvioFasiSuccessive = "?, ?, ?";
      			sqlParamsInvioFasiSuccessive = new Object[]{ codgara, codlott, numappa,
      					CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, 
      					CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI,
      					CostantiW9.APERTURA_CANTIERE };
        		break;
        		
        	case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
        		sqlToReplace = "?";
        		sqlParams = new Object[]{ codgara, codlott, numappa, CostantiW9.COLLAUDO_CONTRATTO };
        		
       			sqlToReplaceInvioFasiSuccessive = "?";
       			sqlParamsInvioFasiSuccessive = new Object[] { codgara, codlott, numappa, CostantiW9.COLLAUDO_CONTRATTO };
        		break;
        		
        	case CostantiW9.COLLAUDO_CONTRATTO:
        	case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
        	case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
        	case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
        	case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
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
        		// Queste fasi non hanno fanno successive, quindi sono sempre eliminabili fisicamente se non inviate,
        		// mentre se inviate sono sempre annullabili
        	default:
        		break;
        	}
        
        	long numeroFasiSuccessiveDipendenti = 0;
        	long numeroFasiSuccessiveInviate = 0;
        	if (sqlToReplace.length() > 0 && sqlParams != null) {
        		numeroFasiSuccessiveDipendenti = (Long) sqlManager.getObject(
        				StringUtils.replace(sqlEsistenzaFasiSuccessive, "TO_REPLACE", sqlToReplace), sqlParams);
        	}
        	
        	if (sqlToReplaceInvioFasiSuccessive.length() > 0 && sqlParamsInvioFasiSuccessive != null) {
        		numeroFasiSuccessiveInviate = (Long) sqlManager.getObject(
        				StringUtils.replace(sqlFasiSuccessiveInviateOAnnullate, "TO_REPLACE", sqlToReplaceInvioFasiSuccessive), sqlParamsInvioFasiSuccessive);
        	}
        	
        	if (isFaseInviata) {
       			annullabile = ANNULLA_SI;
        		if (sqlToReplaceInvioFasiSuccessive.length() > 0) {
        			if (numeroFasiSuccessiveInviate > 0) {
        				annullabile = ANNULLA_NO;
        			}
        		}
        	}

        	if ((!isFaseInviata || isFaseAnnullata) && !isRichiestoAnnullamentoFase) {
        		if (numeroFasiSuccessiveDipendenti > 0) {
        			cancellabile = CANCELLA_NO;
        		} else {
        			cancellabile = CANCELLA_SI;
        		}
        	} else {
        		cancellabile = CANCELLA_NO;
        	}
        	
    		}
    	}

      if (entita.equals("W9GARA_ENTINAZ")) {
        // Una fase non e' eliminabile la fase stessa e' gia' stata inviata (cioe' se esiste in W9FLUSSI il relativo record).
        Long codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
        List< ? > flussiCollegati = sqlManager.getListVector(SQL_ESISTENZA_FLUSSI_GARA_ENTINAZ, new Object[]{codgara});

        if (flussiCollegati != null && flussiCollegati.size() == 0) {
          cancellabile = CANCELLA_SI;
        }
      }
    } catch (SQLException sqle) {
      throw new JspException(
          "Errore in fase di esecuzione della query per i controlli preventivi all'eliminazione del Programma, della Gara, del Lotto, o della Fase/evento",
          sqle);
    } catch (Exception exc) {
      throw new JspException(
          "Errore in fase di esecuzione della query per i controlli preventivi all'eliminazione del Programma, della Gara, del Lotto, o della Fase/evento",
          exc);
    }
    
    if (StringUtils.isEmpty(annullabile))
    	return cancellabile;
    else
    	return cancellabile.concat("-").concat(annullabile);
  }

}