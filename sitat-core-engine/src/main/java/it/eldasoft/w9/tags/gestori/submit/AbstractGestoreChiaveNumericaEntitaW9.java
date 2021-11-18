package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.common.CostantiW9;

/**
 * Gestore per il salvataggio delle entita' (non tutte) dello schema W9. I
 * metodi afterUpdateEntita e afterInsertEntita sono sovrascritti per
 * l'update/insert del record in W9FASI, al fine di valorizzare il campo
 * W9FASI.DAEXPORT = '1'.
 * 
 * @author Luca.Giacomazzo
 */
public abstract class AbstractGestoreChiaveNumericaEntitaW9 extends AbstractGestoreChiaveNumerica {

	private static Logger logger = Logger.getLogger(AbstractGestoreChiaveNumerica.class);

	public AbstractGestoreChiaveNumericaEntitaW9(boolean isGestoreStandard) {
		super(isGestoreStandard);
	}

	public AbstractGestoreChiaveNumericaEntitaW9() {
		super();
	}

	/**
	 * Sovrascrittura del metodo.
	 * 
	 * @param status
	 *            Stato della transazione
	 * @param datiForm
	 *            Elenco dei campi del form della pagina che ha inoltrato la
	 *            richiesta
	 * @throws GestoreException
	 */
	public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		Boolean insert = true;

		if (impl.getLong("FASE_ESECUZIONE").equals(new Long(CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI))) {
			// sono nel caso delle imprese invitate e partecipanti

			// se presente gia' una fase con (dato che non puo' esser multipla,
			// esguo l'update
			long numeroFasiElenco = this.getGeneManager().countOccorrenze("W9FASI", " CODGARA=? and CODLOTT=? and FASE_ESECUZIONE=? ",
					new Object[] { impl.getLong("W9IMPRESE.CODGARA"), impl.getLong("W9IMPRESE.CODLOTT"), impl.getLong("FASE_ESECUZIONE") });

			if (numeroFasiElenco != 0) {
				insert = false;
			}
		}

		Long codiceGara = impl.getLong("CODGARA");
		Long codiceLotto = impl.getLong("CODLOTT");
		Long faseEsecuzione = impl.getLong("FASE_ESECUZIONE");
		Long numAppa = impl.getLong("NUM_APPA");
		Long num = null;

		// distinguo i casi in cui il numero progressivo fa riferimento ad una
		// possibile condizione di molteplicita'
		int caseFaseEvento = impl.getLong("FASE_ESECUZIONE").intValue();
		switch (caseFaseEvento) {
		case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
		case CostantiW9.STIPULA_ACCORDO_QUADRO:
			num = impl.getLong("W9INIZ.NUM_INIZ"); 	// Iniziale
			break;
		case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
		case CostantiW9.ADESIONE_ACCORDO_QUADRO:			
			num = impl.getLong("W9APPA.NUM_APPA"); 	// Aggiudicazione
			break;
		case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
			num = impl.getLong("W9CONC.NUM_CONC"); 	// Conclusione
			break;
		case CostantiW9.COLLAUDO_CONTRATTO:
			num = impl.getLong("W9COLL.NUM_COLL"); 	// Collaudo
			break;
		case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
			num = impl.getLong("NUM_AVAN"); 	// Avanzamento
			break;
		case CostantiW9.SOSPENSIONE_CONTRATTO:
			num = impl.getLong("NUM_SOSP"); 	// Sospensione
			break;
		case CostantiW9.VARIANTE_CONTRATTO:
			num = impl.getLong("NUM_VARI"); 	// Variante
			break;
		case CostantiW9.ACCORDO_BONARIO:
			num = impl.getLong("NUM_ACCO"); 	// Accordo Bonario
			break;
		case CostantiW9.SUBAPPALTO:
			num = impl.getLong("NUM_SUBA"); 	// Subappalto
			break;
		case CostantiW9.ISTANZA_RECESSO:
			num = impl.getLong("NUM_RITA"); 	// Istanza di Recesso
			break;
		case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
			num = impl.getLong("NUM_AVAN"); 	// Avanzamento Semplificato
			break;
		case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
			num = impl.getLong("NUM_INFOR"); 	// Infortuni
			break;
		case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
			num = impl.getLong("NUM_INAD"); 	// Inadempienze sicurezza
			break;
		case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
			num = impl.getLong("NUM_REGO"); 	// Idoneita' Contributiva
			break;
		case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
			num = impl.getLong("NUM_TPRO"); 	// Idoneita' Tecnico Professionale
			break;
		case CostantiW9.APERTURA_CANTIERE:
			num = impl.getLong("NUM_CANT"); 	// Scheda Cantiere
			break;
		default:
			// caso di default 
			// la fase/evento puo' avere 
			// una sola istanza
			num = new Long(1);
			if (numAppa > 1) {
				num = new Long(numAppa);
			}
			break;
		}
		
	    // Valorizzazione del campo ID_SCHEDA_LOCALE con il valore ottenuto
	    // concatenando W9LOTT.CIG, FASE_ESECUZIONE (completato a 3 cifre),
	    // NUM (completato a 3 cifre); tra i valori deve essere messo il carattere
	    // underscore (es: 00039488D_007_002)
		String idSchedaLocale = null;
	    
			// Lettura del codice CIG del lotto
	    try {
	    	String codiceCIG = (String) this.sqlManager.getObject(
	    		"select CIG from W9LOTT where CODGARA=? and CODLOTT=?",
	    			new Object[] { codiceGara, codiceLotto });
	    	if (StringUtils.isNotEmpty(codiceCIG)) {
	    		StringBuffer stringBuffer = new StringBuffer(codiceCIG);
	    		stringBuffer.append("_");
	    		stringBuffer.append(UtilityStringhe.fillLeft("" + faseEsecuzione, '0', 3));
	    		stringBuffer.append("_");
	    		stringBuffer.append(UtilityStringhe.fillLeft(num.toString(), '0', 3));
	
	    		idSchedaLocale = stringBuffer.toString();
	    	}
	    } catch (SQLException e) {
	    	throw new GestoreException("Errore nel determinare il valore del campo W9FASI.ID_SCHEDA_LOCALE", null, e);
	    }

		String sqlInstruction = null;
	
		if (insert) {
			if (StringUtils.isNotEmpty(idSchedaLocale)) {
				sqlInstruction = "INSERT INTO W9FASI (CODGARA,CODLOTT,FASE_ESECUZIONE,NUM,DAEXPORT,ID_SCHEDA_LOCALE,NUM_APPA) VALUES (?,?,?,?,'1','" + idSchedaLocale + "',?)";
			} else {
				sqlInstruction = "INSERT INTO W9FASI (CODGARA,CODLOTT,FASE_ESECUZIONE,NUM,DAEXPORT,NUM_APPA) VALUES (?,?,?,?,'1',?)";
			}
		} else {
			sqlInstruction = "UPDATE W9FASI SET DAEXPORT='1' WHERE  CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = ? AND NUM_APPA = ?";
		}
	
		try {
			this.sqlManager.update(sqlInstruction, new Object[] { codiceGara, codiceLotto, faseEsecuzione, num, numAppa });
		} catch (SQLException sqlEx) {
			logger.error("Errore nel salvataggio della fase (Fase_esecuzione=" + faseEsecuzione + ", Num_appa=" + numAppa + ", Progressivo=" + num +")", sqlEx);
			throw new GestoreException("Errore nel salvataggio della fase (Fase_esecuzione=" + faseEsecuzione + ",Num_appa=" + numAppa + " Progressivo=" + num +")", null, sqlEx);
		}
	}

	/**
	 * Sovrascrittura del metodo.
	 * 
	 * @param status
	 *            Stato della transazione
	 * @param datiForm
	 *            Elenco dei campi del form della pagina che ha inoltrato la
	 *            richiesta
	 * @throws GestoreException
	 */
	public void afterUpdateEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		// presumo di avere gia' tutti i valori --> modifica JSP 4 istruzioni di
		// set
		Long codiceGara = impl.getLong("CODGARA");
		Long codiceLotto = impl.getLong("CODLOTT");
		Long numAppa = impl.getLong("NUM_APPA");
		// da inserire
		Long faseEsecuzione = impl.getLong("FASE_ESECUZIONE");
		
		Long num = null;

		// distinguo i casi in cui il numero progressivo fa riferimento ad una
		// possibile condizione di molteplicita'
		switch (faseEsecuzione.intValue()) {
		case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
		case CostantiW9.STIPULA_ACCORDO_QUADRO:
			num = impl.getLong("W9INIZ.NUM_INIZ"); 	// Iniziale
			break;
		case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
		case CostantiW9.ADESIONE_ACCORDO_QUADRO:
			num = impl.getLong("W9APPA.NUM_APPA"); 	// Aggiudicazione
			break;
		case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
		case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
			num = impl.getLong("W9CONC.NUM_CONC"); 	// Conclusione
			break;
		case CostantiW9.COLLAUDO_CONTRATTO:
			num = impl.getLong("W9COLL.NUM_COLL"); 	// Collaudo
			break;
		case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
			num = impl.getLong("NUM_AVAN"); 	// Avanzamento
			break;
		case CostantiW9.SOSPENSIONE_CONTRATTO:
			num = impl.getLong("NUM_SOSP"); 	// Sospensione
			break;
		case CostantiW9.VARIANTE_CONTRATTO:
			num = impl.getLong("NUM_VARI"); 	// Variante
			break;
		case CostantiW9.ACCORDO_BONARIO:
			num = impl.getLong("NUM_ACCO"); 	// Accordo Bonario
			break;
		case CostantiW9.SUBAPPALTO:
			num = impl.getLong("NUM_SUBA"); 	// Subappalto
			break;
		case CostantiW9.ISTANZA_RECESSO:
			num = impl.getLong("NUM_RITA"); 	// Istanza di Recesso
			break;
		case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
			num = impl.getLong("NUM_AVAN"); 	// Avanzamento Semplificato
			break;
		case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
			num = impl.getLong("NUM_INFOR"); 	// Infortuni
			break;
		case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
			num = impl.getLong("NUM_INAD"); 	// Inadempienze sicurezza
			break;
		case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
			num = impl.getLong("NUM_REGO"); 	// Idoneita' Contributiva
			break;
		case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
			num = impl.getLong("NUM_TPRO"); 	// Idoneita' Tecnico Professionale
			break;
		case CostantiW9.APERTURA_CANTIERE:
			num = impl.getLong("NUM_CANT"); 	// Scheda Cantiere
			break;
		default:
			num = new Long(1); 					// caso di default la fase/evento puo' avere una sola istanza
			break;
		}

		try {
			this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='1' WHERE  CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = ? AND NUM_APPA = ?",
					new Object[] { codiceGara, codiceLotto, faseEsecuzione, num, numAppa });
		} catch (SQLException sqlEx) {
			logger.error("Errore nell'aggiornamento della fase (Fase_esecuzione=" + faseEsecuzione + ", Progressivo=" + num +")", sqlEx);
			throw new GestoreException("Errore nell'aggiornamento della fase (Fase_esecuzione=" + faseEsecuzione + ", Progressivo=" + num +")", null, sqlEx);
		}
	}

}
