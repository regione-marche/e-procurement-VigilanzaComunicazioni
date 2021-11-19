package it.eldasoft.w9.bl;

import it.eldasoft.commons.TabControlliManager;
import it.eldasoft.commons.beans.MessaggioControlloBean;
import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.utility.UtilityDate;
import it.eldasoft.utils.utility.UtilityFiscali;
import it.eldasoft.utils.utility.UtilityMath;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Algoritmo per testare la correttezza dei dati trattati dall'applicativo ed
 * utilizzati per la produzione dei flussi XML da inviare a Sitat ORT.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class ControlliValidazioneManager {
	/** Logger della classe. */
	static Logger logger = Logger.getLogger(ControlliValidazioneManager.class);

	/** SqlManager. */
	private SqlManager sqlManager;
	
	private GeneManager geneManager;
	
	private TabControlliManager tabControlliManager;

	/**
	 * Setting this.sqlManager.
	 * 
	 * @param sqlManager
	 *            sqlManager da settare internamente alla classe.
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}	
	/**
	 * Setting this.geneMaanger.
	 * 
   * @param geneManager the geneManager to set.
   */
  public void setGeneManager(GeneManager geneManager) {
    this.geneManager = geneManager;
  }

  /**
   * Setting this.tabControlliManager
   * 
   * @param tabControlliManager the tabControlliManager to set.
   */
  public void setTabControlliManager(TabControlliManager tabControlliManager) {
    this.tabControlliManager = tabControlliManager;
  }


  /**
	 * Validazione del flusso in generale.
	 * 
   * ------------------------------------------------------------------------------------
   *           |     Area 1      |        Area 2        |    Area 3     |    Area 4     |
   * ----------|-----------------|----------------------|---------------|---------------|
   *           |Flussi del lotto |Anagrafica gara/lotto | Avviso        | Programma     |
   *           |(fasi/eventi)    |e Pubblicazione       |               | triennale     |
   *           |                 |documenti (988 e 901) |(989)          |(991, 992, 981 |
   *           |                 |                      |               | e 982)        |
   * ----------|-----------------|----------------------|---------------|---------------|
   * params[0] |                 |                      |               |               |
   * params[1] | codice flusso   | codice flusso        | codice flusso | codice flusso |
   * params[2] | codice gara     | codice gara          | codein        | contri        |
   * params[3] | codice lotto    |                      | idavviso      |               |
   * params[4] | num             | numeroPubblicazione  |               |               |
   * params[5] |                 |                      | codice sistema|               |
   * ------------------------------------------------------------------------------------
   *
	 * @param params
	 *            array di Object con il seguente significato
	 * @param custom
	 *            tipo di Validazione
	 * @return HashMap HashMap chiave, valore
	 * @throws JspException
	 *             JspException
	 */
	public HashMap<String, Object> validate(Object[] params, String custom, String profiloAttivo) throws JspException {
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

		try {
			int areaInvio = 0;
			String titolo = null;
			List<Object> listaControlli = new Vector<Object>();
			
			// Tipo di  flusso come da tabellato W3020
			Long flusso = Long.parseLong(params[1].toString());
			titolo = this.getDescrizioneFlusso(flusso);

			areaInvio = UtilitySITAT.getAreaFlusso(flusso.intValue());
			if (custom == null || !custom.equals("RM")) {

				// *** Area 1 - Flussi del lotto (fasi/eventi) ***
				if (CostantiW9.AREA_FASI_DI_GARA == areaInvio) {
					Long codgara = Long.parseLong(params[2].toString());
					Long codlott = Long.parseLong(params[3].toString());
					Long num = Long.parseLong(params[4].toString());

					// A prescindere che si stia inviando dati al proxy (in modalita' osservatorio)
					// o che si stia inviando una fase con il loaderAppalto a Simog (in modalita' Vigilanza)
					// si effettua la validazione dell'anagrafica lotto per segnalare eventuali dati mancanti
					List<Object> listaControlliAnagrafica = new ArrayList<Object>();
					this.validazione988(codgara, codlott, listaControlliAnagrafica, profiloAttivo);
					
					if (!listaControlliAnagrafica.isEmpty()) {
						this.setTitolo(listaControlli, "Anagrafica gara/lotto");
						for (int as=0; as < listaControlliAnagrafica.size(); as++) {
							listaControlli.add(listaControlliAnagrafica.get(as));
						}
					}
					
					switch (flusso.intValue()) {
					case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
						// Fase aggiudicazione/affidamento (>150.000 euro)
						this.validazione1(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
						// Fase iniziale esecuzione contratto (>150.000 euro)
						this.validazione2(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
						// Fase esecuzione e avanzamento del contratto
						this.validazione3(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
						// Fase di conclusione del contratto (>150.000 euro)
						this.validazione4(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.COLLAUDO_CONTRATTO: // Fase di collaudo del contratto
						this.validazione5(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.SOSPENSIONE_CONTRATTO: // Sospensione del contratto
						this.validazione6(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.VARIANTE_CONTRATTO: // Variante del contratto
						this.validazione7(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.ACCORDO_BONARIO: // Accordi bonari
						this.validazione8(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.SUBAPPALTO: // Subappalti
						this.validazione9(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.ISTANZA_RECESSO: // Istanza di recesso
						this.validazione10(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.STIPULA_ACCORDO_QUADRO: // Stipula accordo quadro
						this.validazione11(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.ADESIONE_ACCORDO_QUADRO: // Adesione accordo quadro
						this.validazione12(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
						// Fase aggiudicazione/affidamento appalto (<150.000 euro)
						this.validazione987(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
						// Fase iniziale esecuzione contratto (<150.000 euro)
						this.validazione986(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
						// Fase di conclusione del contratto (<150.000 euro)
						this.validazione985(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
						// Scheda segnalazione infortuni
						this.validazione994(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
						// Inadempienze predisposizioni sicurezza e regolarita' lavoro
						this.validazione995(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
						// Esito negativo verifica regolarita' contributiva ed assicurativa
						this.validazione996(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
						// Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria
						this.validazione997(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.APERTURA_CANTIERE: // Apertura cantiere
						this.validazione998(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.COMUNICAZIONE_ESITO: // Comunicazione esito
						this.validazione984(codgara, codlott, listaControlli);
						break;
					case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // Elenco imprese invitate/partecipanti
						this.validazione101(codgara, codlott, num, listaControlli);
						break;
					case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // Avanzamento contratto semplicata
						this.validazione102(codgara, codlott, num, listaControlli);
						break;
					default:
						break;
					}
				} else if (CostantiW9.AREA_ANAGRAFICA_GARE == areaInvio) {
					// *** Area 2 - Anagrafica Gara e Lotto/i + CIG ***
					Long codgara = Long.parseLong(params[2].toString());
					if (CostantiW9.ANAGRAFICA_GARA_LOTTI == flusso.intValue()) {
						this.validazione988(codgara, null, listaControlli, profiloAttivo);
					} else if (CostantiW9.ANAGRAFICA_SEMPLIFICATA_BANDO_DI_GARA == flusso.intValue()) {
						this.validazione13(codgara, null, listaControlli, profiloAttivo);
					} else if (CostantiW9.PUBBLICAZIONE_DOCUMENTI == flusso.intValue()) {
					  Long numeroPubblicazione = Long.parseLong(params[4].toString());
					  this.validazione901(codgara, numeroPubblicazione, listaControlli, profiloAttivo);
					}
				} else if (CostantiW9.AREA_AVVISI == areaInvio) {
					// *** Area 3 - Avviso ***
					String codein = (String) params[2];
					Long idavviso = Long.parseLong(params[3].toString());
					Long codiceSistema = Long.parseLong(params[5].toString());
					this.validazione989(codein, idavviso, codiceSistema, listaControlli);
				}
			} else {
				if (CostantiW9.AREA_FASI_DI_GARA == areaInvio) {
					// *** Area 1 - Flussi del lotto (fasi/eventi) ***
					Long codgara = Long.parseLong(params[2].toString());
					Long codlott = Long.parseLong(params[3].toString());
					if (CostantiW9.COMUNICAZIONE_ESITO == flusso.intValue()) {
						this.validazione984RegioneMarche(codgara, codlott, listaControlli);
					}
				} else if (CostantiW9.AREA_ANAGRAFICA_GARE == areaInvio) {
					// *** Area 2 - Anagrafica Gara e Lotto/i + CIG ***
					Long codgara = Long.parseLong(params[2].toString());
					if (CostantiW9.ANAGRAFICA_GARA_LOTTI == flusso.intValue()) {
						this.validazione988RegioneMarche(codgara, listaControlli);
					}
				} else if (CostantiW9.AREA_AVVISI == areaInvio) {
					// *** Area 3 - Avviso ***
					String codein = (String) params[2];
					Long idavviso = Long.parseLong(params[3].toString());
					Long codiceSistema = Long.parseLong((String) params[4]);
					this.validazione989RegioneMarche(codein, idavviso, codiceSistema, listaControlli);
				}
			}

			if (CostantiW9.AREA_PROGRAMMA_TRIENNALI_ANNUALI == areaInvio) {
				// *** Area 4 - Programma triennale ***
				Long contri = Long.parseLong(params[2].toString());

				switch (flusso.intValue()) {
				case CostantiW9.PROGRAMMA_TRIENNALE_LAVORI:
					// Programma triennale ed elenco annuale Lavori
					this.validazione992(contri, listaControlli);
					break;

				case CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI:
					// Programma triennale ed elenco annuale
					// Forniture e Servizi
					this.validazione991(contri, listaControlli);
					break;
					
				case CostantiW9.PROGRAMMA_TRIENNALE_OPERE_PUBBLICHE:
				  // Programma triennale delle opere pubbliche
				  this.validazione982(contri, listaControlli);
          break;
				
				case CostantiW9.PROGRAMMA_BIENNALE_ACQUISTI:
          // Programma biennale acquisti
          this.validazione981(contri, listaControlli);
          break;
          
				default:
					break;
				}
			}

			infoValidazione.put("titolo", titolo);
			infoValidazione.put("listaControlli", listaControlli);

			// Controllo errori e warning
			int numeroErrori = 0;
			int numeroWarning = 0;

			if (!listaControlli.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Messaggi di errore durante la validazione del flusso con codice = " + flusso.intValue());
				}
				
				if (listaControlli.get(0) instanceof MessaggioControlloBean) {
				  infoValidazione.put("controlliSuBean", Boolean.TRUE);
				  for (int i = 0; i < listaControlli.size(); i++) {
				    MessaggioControlloBean messaggioControllo = (MessaggioControlloBean) listaControlli.get(i);
            if ("E".equals(messaggioControllo.getTipoMessaggio())) {
              numeroErrori++;
            }
            if ("W".equals(messaggioControllo.getTipoMessaggio())) {
              numeroWarning++;
            }

            if (logger.isDebugEnabled()) {
              logger.debug(messaggioControllo.getPagina() + " - " + messaggioControllo.getTipoMessaggio()
                  + " - " + messaggioControllo.getDescrizioneCampo() + " - " + messaggioControllo.getMessaggio());
            }
          }
				} else {
	        for (int i = 0; i < listaControlli.size(); i++) {
	          Object[] controllo = (Object[]) listaControlli.get(i);
	          String tipo = (String) controllo[0];
	          if ("E".equals(tipo)) {
	            numeroErrori++;
	          }
	          if ("W".equals(tipo)) {
	            numeroWarning++;
	          }

	          if (logger.isDebugEnabled()) {
	            logger.debug(controllo[0] + " - " + controllo[1] + " - " + controllo[2] + " - " + controllo[3]);
	          }
	        }				  
				}
			}

			infoValidazione.put("numeroErrori", numeroErrori);
			infoValidazione.put("numeroWarning", numeroWarning);
		} catch (GestoreException e) {
			throw new JspException("Errore nella funzione di controllo dei dati", e);
		}
		return infoValidazione;
	}

	@SuppressWarnings("rawtypes")
	public HashMap<String, Object> validateMultilotto(Long codgara, String associazioni, String profiloAttivo) throws JspException {
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
		
		try {
			String lottiDaInviare = "";
			String titolo = "Validazione monitoraggio multilotto";
			//verifico se effettivamente sono stati accorpati dei lotti 
			boolean validazioneMultilotto = false;
			String[] associazioneLotti = associazioni.split("-");
			HashMap<String, List< String >> raggruppamentiLotti = new HashMap<String, List< String >>();
			for(int i = 0; i < associazioneLotti.length; i++ ) {
				String[] item = associazioneLotti[i].split(";");
				//item[0] = raggruppamento, item[1] = codlott, item[2] = master
				List< String > itemRaggruppamento;
				if (raggruppamentiLotti.containsKey(item[0])) {
					itemRaggruppamento = raggruppamentiLotti.get(item[0]);
					itemRaggruppamento.add(associazioneLotti[i]);
					validazioneMultilotto = true;
				} else {
					itemRaggruppamento = new ArrayList< String >();
					itemRaggruppamento.add(associazioneLotti[i]);
					raggruppamentiLotti.put(item[0], itemRaggruppamento);
				}
			}
			
			List<Object> listaControlli = new Vector<Object>();
			
			infoValidazione.put("titolo", titolo);
			int numeroErrori = 0;
			if (!validazioneMultilotto) {
				listaControlli.add(((new Object[] { "E", "Monitoraggio Multilotto", "Avviso", "Non sono stati accorpati lotti per il monitoraggio multilotto." })));
	        	numeroErrori++;
			} else {
				List<Object> listaControlliAnagrafica = new ArrayList<Object>();
				this.validazione988(codgara, null, listaControlliAnagrafica, profiloAttivo);
				for (int i = 0; i < listaControlliAnagrafica.size(); i++) {
					Object[] controllo = (Object[]) listaControlliAnagrafica.get(i);
			        String tipo = (String) controllo[0];
			        if ("E".equals(tipo)) {
			        	listaControlli.add(((new Object[] { "E", "Anagrafica Gara/Lotti", "Anagrafica", "Errori bloccanti rilevati nell'anagrafica Gara e Lotti. Per il dettaglio degli errori entrare nei dati generali della gara ed eseguire \"Controllo dati inseriti\"" })));
			        	numeroErrori++;
			            break;
			        }
				}
				for (Map.Entry itemRaggruppamento : raggruppamentiLotti.entrySet()) {
					@SuppressWarnings("unchecked")
					List<String> listaLottiRaggruppamento = (List<String>)itemRaggruppamento.getValue();
					//verifico che il raggruppamento contenga più di un lotto
					if (listaLottiRaggruppamento.size() > 1) {
						for (String item : listaLottiRaggruppamento) {
							lottiDaInviare += item + "-";
							String[] itemArray = item.split(";");
							List<Object> listaControlliAggiudicazione = new ArrayList<Object>();
							this.validazione1(codgara, new Long(itemArray[1]), new Long(1), listaControlliAggiudicazione);
							for (int i = 0; i < listaControlliAggiudicazione.size(); i++) {
								Object[] controllo = (Object[]) listaControlliAggiudicazione.get(i);
						        String tipo = (String) controllo[0];
						        if ("E".equals(tipo)) {
						        	String cig = (String)this.sqlManager.getObject("select CIG from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, new Long(itemArray[1])});
						        	listaControlli.add(((new Object[] { "E", "Aggiudicazione lotto " + cig, "Aggiudicazione lotto", "Errori bloccanti rilevati nella fase di aggiudicazione del lotto. Per il dettaglio degli errori entrare nella scheda di aggiudicazione dalla lista delle fasi del lotto segnalato ed eseguire \"Controllo dati inseriti\"" })));
						        	numeroErrori++;
						            break;
						        }
							}
						}
					}
				}
				
			} 
			infoValidazione.put("listaControlli", listaControlli);
			infoValidazione.put("numeroErrori", numeroErrori);
			infoValidazione.put("numeroWarning", 0);
			infoValidazione.put("lottiDaInviare", lottiDaInviare);
			
		} catch (Exception e) {
			throw new JspException("Errore nella funzione di controllo dei dati per il monitoraggio multilotto", e);
		}
		return infoValidazione;
	}
	
	
	/**
	 * Validazione del lotto.
	 * 
	 * @param params
	 * @param custom
	 * @return
	 * @throws JspException
	 */
	public HashMap<String, Object> validateLotto(Object[] params, String custom, String profiloAttivo) throws JspException {
		HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

		if (logger.isDebugEnabled()) {
			logger.debug("validateLotto: inizio metodo");
		}

		try {
			List<Object> listaControlli = new Vector<Object>();

			// Tipo di flusso come da tabellato W3020
			Long flusso  = Long.parseLong(params[1].toString()); 
			Long codgara = Long.parseLong(params[2].toString());
			Long codlott = Long.parseLong(params[3].toString());

			if ("RM".equals(custom)) {
				this.validazioneLottiRegioneMarche(codgara, listaControlli, "");
			} else {
				if (this.geneManager.getProfili().checkProtec(profiloAttivo, "FUNZ", "VIS", "ALT.W9.XML-REGLIGURIA")) {
					List<MessaggioControlloBean> listaControlliMessaggioBean = new ArrayList<MessaggioControlloBean>();
					try {
						this.tabControlliManager.eseguiControlliLotto(codgara, codlott, "APPALTILIGURIA", listaControlliMessaggioBean);

						if (listaControlliMessaggioBean.size() > 0) {
							for (int i=0; i < listaControlliMessaggioBean.size(); i++) {
								listaControlli.add(listaControlliMessaggioBean.get(i));
							}
						}
					} catch (SQLException e) {
						throw new GestoreException("Errore nella lettura delle informazioni relative al lotto", "validazioneLotto", e);
					}
				} else {
					this.validazioneLotto(codgara, codlott, listaControlli, profiloAttivo);
				}
			}

			infoValidazione.put("titolo", this.getDescrizioneGaraLotto(codgara, codlott));
			infoValidazione.put("listaControlli", listaControlli);

			// Controllo errori e warning
			int numeroErrori = 0;
			int numeroWarning = 0;

			if (!listaControlli.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Messaggi di errore durante la validazione del flusso con codice = " + flusso.intValue());
				}

				for (int i = 0; i < listaControlli.size(); i++) {
					if (listaControlli.get(i) instanceof MessaggioControlloBean) {
						MessaggioControlloBean msg = (MessaggioControlloBean) listaControlli.get(i);
						if ("E".equals(msg.getTipoMessaggio())) {
							numeroErrori++;
						}
						if ("W".equals(msg.getTipoMessaggio())) {
							numeroWarning++;
						}
						infoValidazione.put("controlliSuBean", Boolean.TRUE);
					} else {
  						Object[] controllo = (Object[]) listaControlli.get(i);
	  					String tipo = (String) controllo[0];
  						if ("E".equals(tipo)) {
  							numeroErrori++;
  						}
  						if ("W".equals(tipo)) {
  							numeroWarning++;
  						}
						if (logger.isDebugEnabled()) {
							logger.debug(controllo[0] + " - " + controllo[1] + " - " + controllo[2] + " - " + controllo[3]);
						}
					}
				}
			}

			infoValidazione.put("numeroErrori", numeroErrori);
			infoValidazione.put("numeroWarning", numeroWarning);

		} catch (GestoreException e) {
			throw new JspException("Errore nella funzione di controllo dei dati di un lotto", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validateLotto: fine metodo");
		}

		return infoValidazione;
	}

	/**
	 * Validazione dei principali campi di W9GARA e W9LOTT.
	 * 
	 * @param codgara
	 * @param codlott
	 * @param num
	 * @param listaControlli
	 * @throws GestoreException
	 */
	/*private void validazioneDatiComuni(Long codgara, long codlott, List<Object> listaControlli)
			throws GestoreException {
		
		String nomeTabella = "";
		
		String flagEnteSpeciale = null;
		String tipoContratto = null;
		Long idSceltaContraente = null;
		Long idTipoPrestazione = null;
		Long modoGara = null;
		String codiceCup = null;
		String luogoIstat = null;
		String luogoNuts = null;
		String flag_sa_agente = null;
		
		try {
			Vector<?> datiW9LOTT_GARA = this.sqlManager.getVector(
					"select l.FLAG_ENTE_SPECIALE, l.TIPO_CONTRATTO, l.ID_SCELTA_CONTRAENTE, l.ID_TIPO_PRESTAZIONE, l.ID_MODO_GARA, "
					+ " l.CUP, l.LUOGO_ISTAT, l.LUOGO_NUTS, g.FLAG_SA_AGENTE, g.ID_TIPOLOGIA_SA, g.DENOM_SA_AGENTE, g.CF_SA_AGENTE, "
					+ " g.TIPOLOGIA_PROCEDURA, g.FLAG_CENTRALE_STIPULA "
					+ " from W9LOTT l, W9GARA g where l.CODGARA=? and l.CODLOTT=? and l.CODGARA=g.CODGARA",
					new Object[] { codgara, codlott });

			flagEnteSpeciale = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 0).getValue();
			tipoContratto = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 1).getValue();
			idSceltaContraente = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 2).getValue();
			idTipoPrestazione = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 3).getValue();
			modoGara  = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 4).getValue();
			codiceCup  = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 5).getValue();
			luogoIstat = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 6).getValue();
			luogoNuts  = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 7).getValue();
			flag_sa_agente = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 8).getValue();
			
			nomeTabella = "W9GARA";
			
			if (StringUtils.isEmpty(flag_sa_agente)) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SA_AGENTE",
					"Dati generali della gara");
			} else {
				// Stazione appaltante agente
				if ("1".equals(flag_sa_agente)) {
					// Tipologia della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 9).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPOLOGIA_SA", "Dati generali della gara");
					}
	
					// Denominazione della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 10).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DENOM_SA_AGENTE", "Dati generali della gara");
					}
	
					// Codice fiscale della stazione appaltante agente
					String cfSaAgente = (String) SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 11).getValue();
					if (!isStringaValorizzata(cfSaAgente)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CF_SA_AGENTE", "Dati generali della gara");
					} else {
						if ((!UtilityFiscali.isValidCodiceFiscale(cfSaAgente)) && (!UtilityFiscali.isValidPartitaIVA(cfSaAgente))) {
							listaControlli.add(((new Object[] { "E", "Dati generali della gara", 
									this.getDescrizioneCampo(nomeTabella, "CF_SA_AGENTE"), 
									"Il campo non rispetta il formato previsto" })));
						}
					}
	
					// Tipologia procedura (nel caso agisca per conto di altri)
					if (SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 12).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOLOGIA_PROCEDURA", "Dati generali della gara");
					}
	
					// La centrale di committenza provvede alla stipula?
					if (SqlManager.getValueFromVectorParam(datiW9LOTT_GARA, 13).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_CENTRALE_STIPULA", "Dati generali della gara");
					}
				}
			}
			
			nomeTabella = "W9LOTT";
			// La stazione appaltante agisce per conto di altro soggetto ?
			if (StringUtils.isEmpty(flagEnteSpeciale)) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ENTE_SPECIALE", "Dati generali del lotto");
			}
			
			if (StringUtils.isEmpty(tipoContratto)) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_CONTRATTO", "Dati generali del lotto");
			}

			if (idSceltaContraente == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", "Dati generali del lotto");
			} else {
				// Condizioni
				if (idSceltaContraente.longValue() == 4 || idSceltaContraente.longValue() == 10) {
					Long conteggiow9cond = (Long) this.sqlManager.getObject(
							"select count(*) from w9cond where codgara = ? and codlott = ? and id_condizione is not null",
							new Object[] { codgara, codlott });
					if (conteggiow9cond == null || (conteggiow9cond != null && (new Long(0)).equals(conteggiow9cond))) {
						String messaggio = "Indicare \"Si\" in almeno una condizione fra quelle previste";
						listaControlli.add(((new Object[] { "E", "Dati generali del lotto", "Condizioni", messaggio })));
					}
				}
			}
			
			if (idTipoPrestazione == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPO_PRESTAZIONE", "Dati generali del lotto");
			}

			// Criterio di aggiudicazione
			// Valorizzare se W3ID_CONDI != 2,5,7,9,10,11,14,16,17,18,23,24,28
			Long numeroCondizioni = (Long) this.sqlManager.getObject("select count(*) from W9COND where CODGARA=? and CODLOTT=? "
					+ "and ID_CONDIZIONE not in (2,5,7,9,10,11,14,16,17,18,23,24,28)", new Object[] { codgara, codlott });
			if (numeroCondizioni > 0 && modoGara == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_GARA", "Dati generali del lotto");
			}

			// Codice CUP
			if (StringUtils.isNotEmpty(codiceCup) && codiceCup.length() != 15) {
				listaControlli.add(((new Object[] { "E", "Dati generali del lotto", this.getDescrizioneCampo(nomeTabella, "CUP"),
						"Il codice CUP non \u00E8 valido" })));
			}

			// LuogoIstat e luogoNuts
			if ((StringUtils.isEmpty(luogoIstat)) && StringUtils.isEmpty(luogoNuts)) {
				String descrizione = this.getDescrizioneCampo(nomeTabella, "LUOGO_ISTAT") + ", "
						+ this.getDescrizioneCampo(nomeTabella, "LUOGO_NUTS");
				String messaggio = "Valorizzare uno dei due campi";
				listaControlli.add(((new Object[] { "E", "Dati generali del lotto", descrizione, messaggio })));
			}

			if (StringUtils.isNotEmpty(luogoIstat) && StringUtils.isNotEmpty(luogoNuts)) {
				String descrizione = this.getDescrizioneCampo(nomeTabella, "LUOGO_ISTAT") + ", "
						+ this.getDescrizioneCampo(nomeTabella, "LUOGO_NUTS");
				String messaggio = "Valorizzare solo uno dei due campi";
				listaControlli.add(((new Object[] { "E", "Dati generali del lotto", descrizione, messaggio })));
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di aggiudicazione > 40000 euro (seconda parte)", "validazione1", e);
		}
	}*/
	
	/**
	 * Controllo dei dati per la fase di aggiudicazione per appalti di importo
	 * superiore a 150.000 euro (codice 1).
	 * 
	 * @param codgara
	 * @param codlott
	 * @param num
	 * @param listaControlli
	 * @throws GestoreException
	 */
	private void validazione1(Long codgara, Long codlott, Long num, List<Object> listaControlli)
			throws GestoreException {
	
		if (logger.isDebugEnabled()) {
			logger.debug("validazione1: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Aggiudicazione");
		}
		
		String nomeTabella = "";
		String pagina = "";

		String flagEnteSpeciale = null;
		String tipoContratto = null;
		Long idSceltaContraente = null;
		Long versioneSimog = null;
		Boolean importoComponenteLavori = false;

		try {
			Vector<?> datiW9LOTT = this.sqlManager.getVector(
					"select l.FLAG_ENTE_SPECIALE, l.TIPO_CONTRATTO, l.ID_SCELTA_CONTRAENTE, g.VER_SIMOG "
					+ " from W9LOTT l, W9GARA g where l.CODGARA=g.CODGARA and l.CODGARA=? and l.CODLOTT=? ",
					new Object[] { codgara, codlott });

			flagEnteSpeciale = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 0).getValue();
			tipoContratto = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 1).getValue();
			idSceltaContraente = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT, 2).getValue();
			versioneSimog = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT, 3).getValue();
			  
			nomeTabella = "W9APPA";
			String selectW9APPA = "select cod_strumento, " // 0
					+ " importo_lavori, " // 1
					+ " importo_servizi, " // 2
					+ " importo_forniture, " // 3
					+ " importo_attuazione_sicurezza, " // 4
					+ " importo_progettazione, " // 5
					+ " importo_disposizione, " // 6
					+ " sistema_qualificazione, " // 7
					+ " criteri_selezione_stabiliti_sa, " // 8
					+ " flag_accordo_quadro, " // 9
					+ " procedura_acc, " // 10
					+ " preinformazione, " // 11
					+ " termine_ridotto, " // 12
					+ " id_modo_indizione, " // 13
					+ " IMPORTO_COMPL_INTERVENTO, " // 14
					+ " OPERE_URBANIZ_SCOMPUTO, " // 15
					+ " DURATA_ACCQUADRO, " // 16
					+ " REQUISITI_SETT_SPEC " // 17
					+ " from w9appa where codgara = ? and codlott = ? and num_appa = ?";

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, num });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				pagina = "Dati economici dell\'appalto";
				// Codice dello strumento di programmazione
				if (flagEnteSpeciale != null && "O".equals(flagEnteSpeciale) && ("L".equals(tipoContratto) || "CL".equals(tipoContratto))) {
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 0).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "COD_STRUMENTO", pagina);
					}
				}

				// Importo componente lavori
				Long conteggiow9appalav = (Long) this.sqlManager.getObject("select count(*) from w9appalav where codgara = ? and codlott = ? and id_appalto is not null", 
				    new Object[] { codgara, codlott });
				
				Double importoLavori = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue();
				
				if (importoLavori == null || (importoLavori != null && importoLavori.longValue() == 0)) {
					if ((tipoContratto != null && tipoContratto.equals("L")) && (conteggiow9appalav != null && conteggiow9appalav.longValue() > 0)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LAVORI", pagina);
					}
				} else {
					importoComponenteLavori = true;
				}
				
				// Importo componente servizi
				if (tipoContratto != null && ("S".equals(tipoContratto) || "CS".equals(tipoContratto))) {
					Double importoComponenteServizi = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue();
					if ((importoComponenteServizi == null) || (importoComponenteServizi != null && importoComponenteServizi.longValue() == 0)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SERVIZI", pagina);
					} else if (importoComponenteServizi != null && importoComponenteServizi.doubleValue() <= 0) {
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_SERVIZI"), "L'importo componente servizi deve essere maggiore di zero" })));
					}
				}

				// Importo componente forniture
				if (tipoContratto != null && "F".equals(tipoContratto)) {
					Double importoComponenteForniture = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue();
					if ((importoComponenteForniture == null) || (importoComponenteForniture != null && importoComponenteForniture.longValue() == 0)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_FORNITURE", pagina);
					} else if (importoComponenteForniture != null && importoComponenteForniture.doubleValue() <= 0) {
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_FORNITURE"), "L'importo componente forniture deve essere maggiore di zero" })));
					}
				}

				// Tipologia del lavoro
				if (conteggiow9appalav != null && conteggiow9appalav.longValue() > 0 && (importoLavori == null || (importoLavori != null && importoLavori <= 0))) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_LAVORI"), "L'importo componente lavori deve essere maggiore di zero" })));
				}
				if((importoComponenteLavori && (conteggiow9appalav == null || conteggiow9appalav.longValue() == 0)) ||
						(!importoComponenteLavori && (conteggiow9appalav != null && conteggiow9appalav.longValue() > 0))) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_LAVORI"), "Se c'è una componente lavori, nel lotto è necessario indicare una tipologia lavoro." })));
				}

				// Importo totale per l'attuazione della sicurezza
				// *** Controllo non necessario ***

				// Importo totale somme a disposizione
				Double w3impDisp = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue();
				if (w3impDisp == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_DISPOSIZIONE", pagina);
				} else {
					if (w3impDisp.doubleValue() == 0) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_DISPOSIZIONE", "W", pagina, "Non sono state indicate le somme a disposizione");
					}
				}

				// Importo complessivo intervento
				Double importoComplessivoIntervento = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 14).getValue();
				if (importoComplessivoIntervento != null) {

					// Importi dei finanziamenti del lotto
					List<?> listaImportFinanziamenti = this.sqlManager.getListVector("select IMPORTO_FINANZIAMENTO from W9FINA where CODGARA=? and CODLOTT=? and  NUM_APPA=?", new Object[] { codgara,
							codlott, num });

					if (listaImportFinanziamenti != null) {
						double sommaImpFinan = 0;
						for (int i = 0; i < listaImportFinanziamenti.size(); i++) {
							Double temp = ((JdbcParametro) ((Vector<?>) listaImportFinanziamenti.get(i)).get(0)).doubleValue();
							if (temp != null && temp.doubleValue() != 0) {
								sommaImpFinan += temp.doubleValue();
							}
						}

						if (UtilityMath.round(sommaImpFinan, 2) > 0) {
							if (UtilityMath.round(importoComplessivoIntervento.doubleValue(), 2) > UtilityMath.round(sommaImpFinan, 2)) {
								this.addAvviso(listaControlli, nomeTabella, "IMPORTO_COMPL_INTERVENTO", "W", pagina,
										"L'importo complessivo degli interventi non &egrave; interamente coperto dall'importo cumulativo dei finanziamenti");
							}
						}
					}
				}

				// Opere di urbanizzazione a scomputo?
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 15).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OPERE_URBANIZ_SCOMPUTO", pagina);
				}

				// Durata Accordo quadro
				//if (UtilitySITAT.isSAQ(codgara, this.sqlManager) && SqlManager.getValueFromVectorParam(datiW9APPA, 16).getValue() == null) {
				//	this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_ACCQUADRO", pagina);
				//}

				// Requisiti settori speciali
				if (flagEnteSpeciale != null && ("S".equals(flagEnteSpeciale))) {
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 17).getValue() == null) {
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "REQUISITI_SETT_SPEC"),
								"Requisiti Settori Speciali: non sono stati selezionati i requisiti" })));
					}
				}

				// Importo progettazione
				// *** Controllo non necessario ***

				pagina = "Dati procedurali dell\'appalto";

				// Sistema di qualificazione interno ?
				/*
				 * String sistemaQualificazione = (String)
				 * SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue();
				 * 
				 * // Criteri di selezione stabiliti dalla stazione appaltante ?
				 * String criteriSelezioneStabilitiSA = (String)
				 * SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue();
				 * if ("1".equals(sistemaQualificazione) &&
				 * "1".equals(criteriSelezioneStabilitiSA)) {
				 * listaControlli.add(((new Object[] { "E", pagina,
				 * this.getDescrizioneCampo(nomeTabella,
				 * "CRITERI_SELEZIONE_STABILITI_SA"),
				 * "Criteri di selezione stabiliti dalla stazione appaltante incoerente "
				 * + "rispetto a Sistema di qualificazione interno" }))); }
				 */

				// L'appalto rientra in un accordo quadro?
				// if (SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue() == null) {
				// this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ACCORDO_QUADRO", pagina);
				// }
				// E' stata utilizzata la procedura accelerata?
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 10).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PROCEDURA_ACC", pagina);
				}
				// E' stata effettuata la preinformazione?
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PREINFORMAZIONE", pagina);
				}
				// E' stato utilizzato il termine ridotto?
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 12).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TERMINE_RIDOTTO", pagina);
				}
				
				// Modalita' di indizione della gara
				if (versioneSimog.longValue() == 1 && "S".equals(flagEnteSpeciale) && 
				    (idSceltaContraente != null && 
				        (!idSceltaContraente.equals(new Long(10))) && (!idSceltaContraente.equals(new Long(11))))) {
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 13).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_INDIZIONE", pagina);
					}
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di aggiudicazione > 40000 euro (seconda parte)", "validazione1", e);
		}

		try {

			Long idModoGara = (Long) this.sqlManager.getObject("select id_modo_gara from w9lott where codgara = ? and codlott = ?", new Object[] { codgara, codlott });

			nomeTabella = "W9APPA";
			String selectW9APPA = "select " + " num_manif_interesse, " // 0
					+ " data_manif_interesse, " // 1
					+ " num_imprese_invitate, " // 2
					+ " data_invito, " // 3
					+ " num_imprese_richiedenti, " // 4
					+ " num_imprese_offerenti, " // 5
					+ " num_offerte_ammesse, " // 6
					+ " data_scadenza_richiesta_invito, " // 7
					+ " data_scadenza_pres_offerta, " // 8
					+ " num_offerte_escluse, " // 9
					+ " num_offerte_fuori_soglia, " // 10
					+ " num_imp_escl_insuf_giust, " // 11
					+ " asta_elettronica, " // 12
					+ " offerta_massimo, " // 13
					+ " offerta_minima, " // 14
					+ " val_soglia_anomalia, " // 15
					+ " perc_ribasso_agg, " // 16
					+ " perc_off_aumento, " // 17
					+ " importo_aggiudicazione, " // 18
					+ " importo_subtotale, " // 19
					+ " importo_attuazione_sicurezza, " // 20
					+ " imp_non_assog, " // 21
					+ " importo_progettazione, " // 22
					+ " data_verb_aggiudicazione, " // 23
					+ " flag_rich_subappalto, " // 24
					+ " id_modo_indizione, " // 25
					+ " importo_lavori, " // 26
					+ " importo_servizi, " // 27
					+ " importo_forniture, " // 28
					+ " RELAZIONE_UNICA " // 29
					+ " from w9appa where codgara = ? and codlott = ? and num_appa = ?";

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, num });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				pagina = "Inviti ed offerte / soglia di anomalia";

				Date dataManifInteresse = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue();
				// Numero imprese che hanno presentato manifestazione interesse
				if (dataManifInteresse != null) {
					Long numManifInteresse = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 0).getValue();
					if (numManifInteresse == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_MANIF_INTERESSE", pagina);
					}
					if (numManifInteresse != null && numManifInteresse.longValue() <= 0) {
						String messaggio = "Il valore digitato deve essere maggiore di 0";
						this.addAvviso(listaControlli, nomeTabella, "NUM_MANIF_INTERESSE", "E", pagina, messaggio);
					}
				}

				// Data di scadenza per la presentazione delle manifestazioni
				//Long idModoIndizione = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 25).getValue();
				/*if (idModoIndizione != null && (new Long(1)).equals(idModoIndizione)) {
					//idSceltaContraente = (Long) this.sqlManager.getObject("select ID_SCELTA_CONTRAENTE from W9LOTT where CODGARA = ? and CODLOTT = ?", new Object[] { codgara, codlott });
					//if (idSceltaContraente != null && idSceltaContraente.longValue() >= 3) {
						if (dataManifInteresse == null) {
							this.addAvviso(listaControlli, nomeTabella, "DATA_MANIF_INTERESSE", "W", pagina, "La data scadenza presentazione delle manifestazioni non \u00E8 valorizzata");
						}
					//}
				}*/

				Date dataInvito = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue();
				// Numero imprese invitate a presentare l'offerta
				Long numImpreseInvitate = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue();
				if (dataInvito != null && numImpreseInvitate == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_IMPRESE_INVITATE", pagina);
				}

				if (numImpreseInvitate != null && numImpreseInvitate.longValue() <= 0) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "NUM_IMPRESE_INVITATE"), "Il valore del campo deve essere maggiore di zero" })));
				}

				// Data di invito
				Date dataScadenzaRichiestaInvito = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue();

				/*if (idSceltaContraente != null && idSceltaContraente.longValue() != 2 && idSceltaContraente.longValue() != 9 && dataScadenzaRichiestaInvito != null) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_SCADENZA_RICHIESTA_INVITO"),
							"La data scadenza presentazione delle richieste invito non deve essere valorizzato" })));
				}*/

				if (dataScadenzaRichiestaInvito != null) {
					// Numero imprese che hanno presentato richiesta di invito
					Long numImpreseRichiedenti = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 4).getValue();
					if (numImpreseRichiedenti == null || (numImpreseRichiedenti != null && numImpreseRichiedenti.longValue() <= 0)) {
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "NUM_IMPRESE_RICHIEDENTI"), "Il valore del campo deve essere maggiore di zero" })));
					}
				}

				// Numero imprese che hanno presentato offerta
				Long numImpreseOfferenti = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 5).getValue();
				if (numImpreseOfferenti == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_IMPRESE_OFFERENTI", pagina);
				} else {
					if (numImpreseInvitate != null) {
						if (numImpreseInvitate > 0 && (numImpreseOfferenti.longValue() < 0 || (numImpreseOfferenti.longValue() > numImpreseInvitate.longValue()))) {
							String messaggio = "Il valore digitato (" + numImpreseOfferenti.toString() + ") deve essere compreso tra 0 e " + numImpreseInvitate.toString();
							this.addAvviso(listaControlli, nomeTabella, "NUM_IMPRESE_OFFERENTI", "E", pagina, messaggio);
						}
					}
				}

				// Numero offerte ammesse
				Long numOfferteAmmesse = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue();
				if (numOfferteAmmesse == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_OFFERTE_AMMESSE", pagina);
				}

				if (numOfferteAmmesse != null && numImpreseOfferenti != null) {
					if (numOfferteAmmesse.longValue() < 0 || (numOfferteAmmesse.longValue() > numImpreseOfferenti.longValue())) {
						String messaggio = "Il valore digitato (" + numOfferteAmmesse.toString() + ") deve essere compreso tra " + " 0 e " + numImpreseOfferenti.toString();
						this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_AMMESSE", "E", pagina, messaggio);
					}
				}

				// Data di scadenza per la presentazione delle richieste invito
				// *** Controllo non necessario ***

				// Data di scadenza per la presentazione delle offerte
				// if (id_scelta_contraente != null
				// && (id_scelta_contraente.longValue() == 2 ||
				// id_scelta_contraente.longValue() == 3)) {
				// if (data_invito == null) {
				Date dataScadenzaPresentazioneOfferta = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue();
				if (dataScadenzaPresentazioneOfferta == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_SCADENZA_PRES_OFFERTA", pagina);
				}
				// }
				// }

				// Numero imprese escluse automaticamente
				Long numOfferteEscluse = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue();
				// L.G. - 12/01/2011: il campo non e' piu' obbligatorio
				// if (num_offerte_escluse == null) {
				// this.addCampoObbligatorio(listaControlli, nomeTabella,
				// "NUM_OFFERTE_ESCLUSE", pagina);
				// }
				if (numOfferteEscluse != null && numOfferteAmmesse != null) {
					if (numOfferteEscluse.longValue() < 0 || numOfferteEscluse.longValue() > numOfferteAmmesse.longValue()) {
						String messaggio = "Il valore digitato (" + numOfferteEscluse.toString() + ") deve essere compreso " + " tra 0 e " + numOfferteAmmesse.toString();
						this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_ESCLUSE", "E", pagina, messaggio);
					}
					
					if (numOfferteEscluse.longValue() >= 0 && numOfferteEscluse.longValue() > numOfferteAmmesse.longValue()) {
						this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_ESCLUSE", "E", pagina, "Il numero di imprese escluse automaticamente non puo' essere superiore al numero di offerte ammesse");
					}		 
				}

				// Numero offerte al di fuori della soglia di anomalia
				Long numOfferteFuoriSoglia = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 10).getValue(); 
				if (numOfferteFuoriSoglia != null && numOfferteAmmesse != null) {
					if (numOfferteFuoriSoglia.longValue() >= 0 && numOfferteFuoriSoglia.longValue() > numOfferteAmmesse.longValue()) {
						this.addAvviso(listaControlli, nomeTabella, "NUM_OFFERTE_FUORI_SOGLIA", "E", pagina, "Il numero di offerte al di fuori della soglia di anomalia non può essere superiore al numero di offerte ammesse");
					}	
				}

				

				// Numero imprese escluse per insufficienti giustificazioni
				Long numImprEsclInsufGiust = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue();
				if (numImprEsclInsufGiust != null && numOfferteAmmesse != null) {
					if (numImprEsclInsufGiust.longValue() < 0 || numImprEsclInsufGiust.longValue() > numOfferteAmmesse.longValue()) {
						String messaggio = "Il valore digitato (" + numImprEsclInsufGiust.toString() + ") deve essere compreso " + " tra 0 e " + numOfferteAmmesse.toString();
						this.addAvviso(listaControlli, nomeTabella, "NUM_IMP_ESCL_INSUF_GIUST", "E", pagina, messaggio);
					}
					if (numImprEsclInsufGiust.longValue() >= 0 && numImprEsclInsufGiust.longValue() > numOfferteAmmesse.longValue()) {
						this.addAvviso(listaControlli, nomeTabella, "NUM_IMP_ESCL_INSUF_GIUST", "E", pagina, "Il numero di imprese escluse per insufficienti giustificazioni non può essere superiore al numero di offerte ammesse");
					}
					
				}

				// Ricorso all'asta elettronica
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 12).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ASTA_ELETTRONICA", pagina);
				}

				// Offerta di massimo ribasso %
				Double offertaMassimo = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 13).getValue();
				if (idModoGara != null && (idModoGara.longValue() == 1 || idModoGara.longValue() == 4)) {
					if (flagEnteSpeciale != null && "O".equals(flagEnteSpeciale)) {
						if (numOfferteAmmesse != null && numOfferteAmmesse.longValue() > 1) {
							if (offertaMassimo == null) {
								this.addAvviso(listaControlli, nomeTabella, "OFFERTA_MASSIMO", "E", pagina, "Non e' stato indicato il valore dell'offerta di massimo ribasso");
							}
						}
					}
				}

				if (offertaMassimo != null && !isPercentualeValida(offertaMassimo)) {
					String messaggio = "Indicare una percentuale >= 0 e < 100 ";
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "OFFERTA_MASSIMO"), messaggio })));
				}

				// Offerta di minimo ribasso %
				Double offertaMinima = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 14).getValue();
				if (idModoGara != null && (idModoGara.longValue() == 1 || idModoGara.longValue() == 4)) {
					if (flagEnteSpeciale != null && "O".equals(flagEnteSpeciale)) {
						if (numOfferteAmmesse != null && numOfferteAmmesse.longValue() > 1) {
							if (offertaMinima == null) {
								this.addAvviso(listaControlli, nomeTabella, "OFFERTA_MINIMA", "E", pagina, "Non e' stato indicato il valore dell'offerta di minimo ribasso");	
							}
						}
					}
				}
				
				if (offertaMinima != null && offertaMassimo != null && offertaMinima > offertaMassimo) {
					this.addAvviso(listaControlli, nomeTabella, "OFFERTA_MINIMA", "E", pagina, "Il valore dell'offerta di massimo ribasso deve essere superiore a quello dell'offerta di minimo ribasso");
				}
				
				if (offertaMinima != null && offertaMinima.doubleValue() < 0) {
					String messaggio = "Indicare una percentuale >= 0 e < 100 ";
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "OFFERTA_MINIMA"), messaggio })));
				}

				// Valore soglia di anomalia %
				Double valSogliaAnomalia = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 15).getValue();

				if (idModoGara != null && idModoGara.longValue() == 2 && valSogliaAnomalia != null) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "VAL_SOGLIA_ANOMALIA"),
							"Dato non richiesto in relazione al criterio di aggiudicazione adottato" })));
				}

				if (valSogliaAnomalia != null) {
					if (numOfferteAmmesse != null && numOfferteAmmesse.longValue() >= 15) {
						if (offertaMassimo == null || offertaMinima == null) {
							String temp = this.getDescrizioneCampo(nomeTabella, "OFFERTA_MINIMA").concat(" - ").concat(this.getDescrizioneCampo(nomeTabella, "OFFERTA_MASSIMO"));
							listaControlli.add(((new Object[] { "E", pagina, temp, "Valorizzare Offerta di massimo ribasso e Offerta di minimo ribasso" })));
						} else {
							if ((valSogliaAnomalia.doubleValue() > offertaMassimo.doubleValue()) || (valSogliaAnomalia.doubleValue() < offertaMinima.doubleValue())) {
								String messaggio = "Il valore digitato (" + valSogliaAnomalia.toString() + ") deve essere compreso tra " + " " + offertaMinima.toString() + " e "
										+ offertaMassimo.toString();
								this.addAvviso(listaControlli, nomeTabella, "VAL_SOGLIA_ANOMALIA", "E", pagina, messaggio);
							}
						}
					}
					
				} else {
				  if ((idModoGara != null && idModoGara.longValue() == 4) 
				      && (numOfferteAmmesse != null && numOfferteAmmesse.longValue() >= 15)) {
				    listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "VAL_SOGLIA_ANOMALIA"),
            "Non è stata indicata la soglia di anomalia" })));
				  }
				}

				if (valSogliaAnomalia != null && valSogliaAnomalia.doubleValue() < 0) {
					String messaggio = "Indicare una percentuale >= 0 e < 100 ";
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "VAL_SOGLIA_ANOMALIA"), messaggio })));
				}
				
				//if (numOfferteAmmesse != null && numOfferteAmmesse.longValue() < 5 && valSogliaAnomalia != null ) {
				//	this.addAvviso(listaControlli, nomeTabella, "VAL_SOGLIA_ANOMALIA", "E", pagina, "Il valore di soglia anomala non deve essere valorizzato");
				//}

				pagina = "Aggiudicazione / affidamento";
				Double percRibassoAgg = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 16).getValue();
				Double percOffAumento = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 17).getValue();

				if ((new Long(1).equals(idModoGara) || new Long(4).equals(idModoGara)) &&
				      percOffAumento == null && percRibassoAgg == null) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "W", pagina, "Non e' stato valorizzato il campo");
				}

				if (new Long(2).equals(idModoGara) && percRibassoAgg == null && percOffAumento == null) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_OFF_AUMENTO", "W", pagina, "Non e' stato valorizzato il campo");
				}

				if (percOffAumento != null && percRibassoAgg != null) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "E", pagina, "Non valorizzare il campo");
				}

				if (percRibassoAgg != null && (percRibassoAgg.doubleValue() < 0 || percRibassoAgg.doubleValue() >= 100)) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "E", pagina, "Il valore percentuale deve essere &ge; 0 e &lt; 100");
				}
				if (percOffAumento != null && (percOffAumento.doubleValue() < 0)) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_OFF_AUMENTO", "E", pagina, "Il valore percentuale deve essere &ge; 0");
				}
				// Importo di aggiudicazione
				Double importoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 18).getValue();
				if (importoAggiudicazione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", pagina);
				}

				// *** Controllo congruenza dell'importo di aggiudicazione ***
				if (importoAggiudicazione != null) {

					double importoSubtotale = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 19).getValue() != null) {
						importoSubtotale = SqlManager.getValueFromVectorParam(datiW9APPA, 19).doubleValue().doubleValue();
					}

					double importoAttuazioneSicurezza = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 20).getValue() != null) {
						importoAttuazioneSicurezza = SqlManager.getValueFromVectorParam(datiW9APPA, 20).doubleValue().doubleValue();
					}

					double impNonAssog = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 21).getValue() != null) {
						impNonAssog = SqlManager.getValueFromVectorParam(datiW9APPA, 21).doubleValue().doubleValue();
					}

					double importoProgettazione = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 22).getValue() != null) {
						importoProgettazione = SqlManager.getValueFromVectorParam(datiW9APPA, 22).doubleValue().doubleValue();
					}

					double importoAggiudicazioneCalcolato = 0;
					// importo_aggiudicazione_calcolato = (importo_subtotale +
					// importo_progettazione);

					/*
					 * if (perc_ribasso_agg != null) {
					 * importo_aggiudicazione_calcolato =
					 * importo_aggiudicazione_calcolato (1 -
					 * perc_ribasso_agg.doubleValue() / 100); } else if
					 * (perc_off_aumento != null) {
					 * importo_aggiudicazione_calcolato =
					 * importo_aggiudicazione_calcolato (1 +
					 * perc_off_aumento.doubleValue() / 100); }
					 */

					// Importo Lavori
					double importoLavori = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 26).getValue() != null) {
						importoLavori = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 26).getValue()).doubleValue();
					}
					// Importo servizi
					double importoServizi = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 27).getValue() != null) {
						importoServizi = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 27).getValue()).doubleValue();
					}
					// Importo forniture
					double importoForniture = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 28).getValue() != null) {
						importoForniture = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 28).getValue()).doubleValue();
					}
					importoAggiudicazioneCalcolato = importoLavori + importoServizi + importoForniture + importoAttuazioneSicurezza + impNonAssog + importoProgettazione;

					importoAggiudicazioneCalcolato = UtilityMath.round(importoAggiudicazioneCalcolato, 2);

					if (importoAggiudicazione.doubleValue() > importoAggiudicazioneCalcolato) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "W", pagina, "L'importo di aggiudicazione è superiore dell'importo complessivo dell'appalto");
					}

					double impAggiudicazione = 0;
					double percRibAgg = 0;
					double percOffAum = 0;
					double impSubTot = 0;
					double impAttSic = 0;

					if (importoAggiudicazione != null) {
						impAggiudicazione = importoAggiudicazione.doubleValue();
					}
					if (percRibassoAgg != null) {
						percRibAgg = percRibassoAgg.doubleValue();
					}
					if (percOffAumento != null) {
						percOffAum = percOffAumento.doubleValue();
					}

					impSubTot = importoSubtotale;
					impAttSic = importoAttuazioneSicurezza;

					if (Math.abs(UtilityMath.round((impAggiudicazione - (impSubTot * ((100 - percRibAgg + percOffAum) / 100) + impAttSic)), 2)) > 0.01) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "W", pagina, "L'importo di aggiudicazione non coincide con l'importo calcolato");
					}
				}

				// Data di aggiudicazione definitiva
				Date dataVerbAggiudicazione = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 23).getValue();
				if (dataVerbAggiudicazione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
				} else {
					GregorianCalendar dataMinimaCal = new GregorianCalendar(2008, 0, 1); // data
					// precedente
					// 01/01/2000
					Date dataMinima = dataMinimaCal.getTime();
					Date dataOdierna = new Date();
					if (dataVerbAggiudicazione != null && dataOdierna != null && dataMinima != null) {
						if (dataVerbAggiudicazione.getTime() > dataOdierna.getTime() || dataVerbAggiudicazione.getTime() < dataMinima.getTime()) {
							String messaggio = "La data digitata (" + UtilityDate.convertiData(dataVerbAggiudicazione, UtilityDate.FORMATO_GG_MM_AAAA) + ") deve essere compresa tra il "
									+ UtilityDate.convertiData(dataMinima, UtilityDate.FORMATO_GG_MM_AAAA) + " ed il " + UtilityDate.convertiData(dataOdierna, UtilityDate.FORMATO_GG_MM_AAAA);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, messaggio);
						}
						if (dataScadenzaPresentazioneOfferta != null && dataVerbAggiudicazione.getTime() < dataScadenzaPresentazioneOfferta.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataVerbAggiudicazione, ">=", dataScadenzaPresentazioneOfferta, "W9APPA", "DATA_SCADENZA_PRES_OFFERTA", null);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "W", pagina, messaggio);
						}
					}
				}

				// L'affidatario ha richiesto di subappaltare
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 24).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RICH_SUBAPPALTO", pagina);
				}
				
				// Relazione unica?
				if (versioneSimog.longValue() >= 6 && SqlManager.getValueFromVectorParam(datiW9APPA, 29).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "RELAZIONE_UNICA", pagina);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di aggiudicazione > 40000 euro (terza parte)", "validazione1", e);
		}

		/* RIMOZIONE CONTROLLI PER ACQUISIZIONE BENI
		 * try {
			nomeTabella = "W9APPA";

			String selectW9APPA = "select " + " acqbeni, " // 0
					+ " impbeni, " // 1
					+ " impricic, " // 2
					+ " acqrepric, " // 3
					+ " impamm1, " // 4
					+ " impcar1, " // 5
					+ " impgom1, " // 6
					+ " impine1, " // 7
					+ " impleg1, " // 8
					+ " imppla1, " // 9
					+ " imptes1, " // 10
					+ " impamm2, " // 11
					+ " impcar2, " // 12
					+ " impgom2, " // 13
					+ " impine2, " // 14
					+ " impleg2, " // 15
					+ " imppla2, " // 16
					+ " imptes2 " // 17
					+ " from w9appa where codgara = ? and codlott = ? and num_appa = ?";

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, num });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				pagina = "Acquisizione beni";

				// Acquisizione beni ?
				String acqbeni = (String) SqlManager.getValueFromVectorParam(datiW9APPA, 0).getValue();
				if (acqbeni == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ACQBENI", pagina);
				}
				if (acqbeni != null && "1".equals(acqbeni)) {
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPBENI", pagina);
					}
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPRICIC", pagina);
					}
					// Acquisizione di beni e manufatti di cui al repertorio del
					// riciclaggio ?
					String acqrepric = (String) SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue();
					if (acqrepric == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ACQREPRIC", pagina);
					}

					if (acqrepric != null && "1".equals(acqrepric)) {
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 4).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPAMM1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 5).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPCAR1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPGOM1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPINE1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPLEG1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPPLA1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 10).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPTES1", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPAMM2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 12).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPCAR2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 13).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPGOM2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 14).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPINE2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 15).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPLEG2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 16).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPPLA2", pagina);
						}
						if (SqlManager.getValueFromVectorParam(datiW9APPA, 17).getValue() == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPTES2", pagina);
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di aggiudicazione > 40000 euro (quarta parte)", "validazione1", e);
		}*/

		// Imprese aggiudicatarie
		try {
			Long conteggiow9aggi = (Long) this.sqlManager.getObject("select count(*) from w9aggi where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara, codlott, num });
			if (conteggiow9aggi == null || (conteggiow9aggi != null && (new Long(0)).equals(conteggiow9aggi))) {
				this.setTitolo(listaControlli, "Imprese aggiudicatarie / affidatarie");
				listaControlli.add(((new Object[] { "E", "Imprese aggiudicatarie / affidatarie", "Imprese aggiudicatarie / affidatarie", "Indicare almeno una impresa aggiudicataria" })));
			} else {
				this.validazioneImpreseAggiudicatarie(codgara, codlott, num, listaControlli);
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella conteggio delle imprese aggiudicatarie", "validazione1", e);
		}

		// Incarichi professionali
		try {
			Long conteggiow9inca = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? and codlott = ? and num = ? and sezione in ('PA','RA')", new Object[] { codgara, codlott, num });
			// if (conteggiow9inca == null
			// || (conteggiow9inca != null && (new
			// Long(0)).equals(conteggiow9inca)))
			// {
			// this.setTitolo(listaControlli, "Incarichi professionali");
			// String messaggio =
			// "Indicare almeno un incaricato / professionista";
			// listaControlli.add(((Object) (new Object[] { "E",
			// "Incarichi professionali", "Incarichi professionali", messaggio
			// })));
			// } else {
			if (conteggiow9inca != null && conteggiow9inca.longValue() != 0) {
				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "PA");
				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "RA");
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella conteggio degli incarichi professionali", "validazione1", e);
		}

    // Finanziamenti vengono considerati solo sulla prima aggiudicazione
    if (num.equals(new Long(1))) {
      this.validazioneFinanziamenti(codgara, codlott, num, listaControlli, false);
    }

		// Categorie di qualificazione SOA
		// this.validazioneCategorieQualificazione(codgara, codlott, num, listaControlli);

		if (logger.isDebugEnabled()) {
			logger.debug("validazione1: fine metodo");
		}
	}

	/**
	 * Metodo di controllo dei dati della pubblicita' della gara.
	 * 
	 * @param codgara
	 * @param codlott
	 * @param listaControlli
	 * @param pagina
	 * @return
	 * @throws SQLException
	 * @throws GestoreException
	 */
	private void validazioneW9PUBB(Long codgara, List<Object> listaControlli, String pagina) throws SQLException, GestoreException {
		String nomeTabella = "W9PUBB";

		Vector<?> datiW9PUBB = this.sqlManager.getVector("select " + " DATA_GUCE, " // 0
				+ " DATA_GURI, " // 1
				+ " DATA_ALBO, " // 2
				+ " QUOTIDIANI_NAZ, " // 3
				+ " QUOTIDIANI_REG, " // 4
				+ " PROFILO_COMMITTENTE, " // 5
				+ " SITO_MINISTERO_INF_TRASP, " // 6
				+ " SITO_OSSERVATORIO_CP, " // 7
				+ " DATA_BORE, " // 8
				+ " PERIODICI " // 9
				+ " from W9PUBB where CODGARA=? and CODLOTT=? and NUM_APPA = ? and NUM_PUBB = ?", new Object[] { codgara, new Long(1), new Long(1), new Long(1) });

		if (datiW9PUBB != null && datiW9PUBB.size() > 0) {

			double importo = 0;
			Double importoGara = (Double) this.sqlManager.getObject("select importo_gara from w9gara where codgara = ?", new Object[] { codgara });
			if (importoGara != null) {
				importo = UtilityMath.round(importoGara.doubleValue(), 2);
			}
			if (importo > 5150000) {
				// Gazzetta Ufficiale Comunita' Europea
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 0).getValue() == null) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GUCE", "W", pagina, "Il campo non &egrave; valorizzato");
				}
				// Gazzetta Ufficiale Repubblica Italiana
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 1).getValue() == null) {
					this.addAvviso(listaControlli, nomeTabella, "DATA_GURI", "W", pagina, "Il campo non &egrave; valorizzato");
				}
				// Numero quotidiani nazionali
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 3).getValue() == null) {
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_NAZ", "W", pagina, "Il valore del campo deve essere maggiore o uguale a 2");
				}
				// Numero quotidiani regionali
				if (SqlManager.getValueFromVectorParam(datiW9PUBB, 4).getValue() == null) {
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_REG", "W", pagina, "Il valore del campo deve essere maggiore o uguale a 2");
				}
			}

			// Profilo del committente
			if (SqlManager.getValueFromVectorParam(datiW9PUBB, 5).getValue() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
			} else {
				String profiloCommittente = SqlManager.getValueFromVectorParam(datiW9PUBB, 5).getValue().toString();
				if (importo > 5150000 && (!"1".equals(profiloCommittente))) {
					this.addAvviso(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", "W", pagina, "Valorizzare il campo a 'Si'");
				}
			}

			// Sito Informatico Ministero Infrastrutture
			if (SqlManager.getValueFromVectorParam(datiW9PUBB, 6).getValue() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
			} else {
				String sitoMinistreroCP = SqlManager.getValueFromVectorParam(datiW9PUBB, 6).getValue().toString();
				if (importo > 5150000 && (!"1".equals(sitoMinistreroCP))) {
					this.addAvviso(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", "W", pagina, "Valorizzare il campo a 'Si'");
				}
			}

			// Sito Informatico Osservatorio Contratti Pubblici
			if (SqlManager.getValueFromVectorParam(datiW9PUBB, 7).getValue() == null) {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
			}
		} else {
			String messaggio = "Valorizzare i dati relativi alla Pubblicit&agrave; gara";
			listaControlli.add(((new Object[] { "E", "Pubblicit&agrave; gara", "Pubblicit&agrave; gara", messaggio })));
		}
	}

	/**
	 * Controllo dei dati relativi ai finanziamenti per la fase di
	 * aggiudicazione per contratti di importo superiore a 40000 euro.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param adesioneAccordoQuadro
	 *            check caso di adesione accordo quadro
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneFinanziamenti(Long codgara, Long codlott, Long num, List<Object> listaControlli, Boolean adesioneAccordoQuadro) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneFinanziamenti: inizio metodo");
		}
		String nomeTabella;
		String pagina;
		try {

			nomeTabella = "W9FINA";
			String selectW9FINA = "select num_fina, " // 0
					+ " id_finanziamento, " // 1
					+ " importo_finanziamento " // 2
					+ " from w9fina where codgara = ? and codlott = ? and num_appa = ?";

			List<Object> listaControlliW9FINA = new Vector<Object>();
			List<?> datiW9FINA = this.sqlManager.getListVector(selectW9FINA, new Object[] { codgara, codlott, num });

			String tipoContratto = UtilitySITAT.getTipoContrattoLotto(codgara, codlott, this.sqlManager);
	    String flagEnteSpeciale = (String) this.sqlManager.getObject(
	    		"select flag_ente_speciale from w9lott where codgara = ? and codlott = ?", 
	    		new Object[] { codgara, codlott });
			int versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codgara);

	    boolean isEnteOrdinario = "O".equals(flagEnteSpeciale);
			
			if (datiW9FINA != null && datiW9FINA.size() > 0) {
				for (int i = 0; i < datiW9FINA.size(); i++) {
					Long numFina = (Long) SqlManager.getValueFromVectorParam(datiW9FINA.get(i), 0).getValue();
					pagina = "Finanziamento n. " + numFina.toString();
					
					if (SqlManager.getValueFromVectorParam(datiW9FINA.get(i), 1).getValue() == null) {
						this.addCampoObbligatorio(listaControlliW9FINA, nomeTabella, "ID_FINANZIAMENTO", pagina);
					}
					
					Double importoFinanz = SqlManager.getValueFromVectorParam(datiW9FINA.get(i), 2).doubleValue();
					if (importoFinanz == null) {
						this.addCampoObbligatorio(listaControlliW9FINA, nomeTabella, "IMPORTO_FINANZIAMENTO", pagina);
					} else if (importoFinanz.doubleValue() <= 0) {
						if (isEnteOrdinario && "L".equals(tipoContratto)) {
							listaControlliW9FINA.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_FINANZIAMENTO"), 
										"L'importo finanziamento deve essere maggiore di zero" })));
						}
					}
				}
			} else {
				if ((isEnteOrdinario && "L".equals(tipoContratto)) || versioneSimog >= 6 ) {
					listaControlliW9FINA.add(((new Object[] { "E", "Finanziamenti", "Finanziamenti", "Indicare almeno un finanziamento" })));
				}
			}

			if (!listaControlliW9FINA.isEmpty()) {
				this.setTitolo(listaControlli, "Finanziamenti");
				for (int i = 0; i < listaControlliW9FINA.size(); i++) {
					listaControlli.add(listaControlliW9FINA.get(i));
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative ai finanziamenti", "validazioneFinanziamenti", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneFinanziamenti: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase iniziale di esecuzione del contratto per
	 * appalti di importo superiore a 150.000 euro (codice 2).
	 * 
	 * @param codgara
	 *            Codice gara
	 * @param codlott
	 *            Codice lotto
	 * @param num
	 *            Numero progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione2(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazione2: inizio metodo");
		}
		
		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Inizio esecuzione contratto");
		}
	
		String nomeTabella = "";
		String pagina = "";
		Long numappa = null;
		try {
			nomeTabella = "W9INIZ";

			String selectW9INIZ = "select " 
			    + " data_stipula, " // 0
					+ " importo_cauzione, " // 1
					+ " data_app_prog_esec, " // 2
					+ " flag_frazionata, " // 3
					+ " data_verbale_cons, " // 4
					+ " data_verbale_def, " // 5
					+ " flag_riserva, " // 6
					+ " data_verb_inizio, " // 7
					+ " data_termine, " // 8
					+ " DATA_ESECUTIVITA, " // 9
					+ " DATA_INI_PROG_ESEC, " // 10
					+ " NUM_APPA " // 11
					+ " from w9iniz where codgara = ? and codlott = ? and num_iniz = ?";

			List<?> datiW9INIZ = this.sqlManager.getVector(selectW9INIZ, new Object[] { codgara, codlott, num });

			if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
				pagina = "Contratto di appalto";

				int versioneSimog = UtilitySITAT.getVersioneSimog(this.sqlManager, codgara);
				String sommaUrgenza = (String) this.sqlManager.getObject(
				    "select SOMMA_URGENZA from W9GARA where CODGARA=?", new Object[] { codgara });
				numappa = (Long) SqlManager.getValueFromVectorParam(datiW9INIZ, 11).getValue();
				String cigMaster = this.getCIGMaster(codgara, codlott);
				Date dataVerbAggiudicazione = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					dataVerbAggiudicazione = this.getDataAggiudicazioneInferiore(codgara, cigMaster, numappa);
				} else {
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Data_stipula contratto
				Date dataStipula = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 0).getValue();
				if (dataStipula != null ) {
				  if (dataVerbAggiudicazione != null) {
				    if (!(versioneSimog == 1 && StringUtils.equals(sommaUrgenza, "1"))) {
				      if (dataStipula.getTime() < dataVerbAggiudicazione.getTime()) {
				        String messaggio = this.getMessaggioConfrontoDate(dataStipula, ">=", dataVerbAggiudicazione,
				            "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(1));
				        this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, messaggio);
				      }
				    }
				  }
				}

				// Data esecutivita'
				Date dataEsecutivita = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 9).getValue();
				if (dataEsecutivita != null && dataStipula != null) {
					if (dataEsecutivita.before(dataStipula)) {
						String messaggio = this.getMessaggioConfrontoDate(dataEsecutivita, ">=", dataStipula, "W9APPA", "DATA_STIPULA", new Long(2));
						this.addAvviso(listaControlli, nomeTabella, "DATA_ESECUTIVITA", "E", pagina, messaggio);
					}
				}

				// Importo_cauzione
				if (SqlManager.getValueFromVectorParam(datiW9INIZ, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_CAUZIONE", pagina);
				}

				pagina = "Termine di esecuzione";

				// Data approvazione del progetto esecutivo
				Date dataApprovProgEsec = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 2).getValue();
				Date dataInizProgEsec = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 10).getValue();
				if (dataApprovProgEsec != null && dataInizProgEsec != null) {
					if (dataApprovProgEsec.getTime() <= dataInizProgEsec.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataApprovProgEsec, ">", dataInizProgEsec, "W9INIZ", "DATA_INI_PROG_ESEC", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_APP_PROG_ESEC", "E", pagina, messaggio);
					}
				}

				// Consegna frazionata
				String flagFrazionata = (String) SqlManager.getValueFromVectorParam(datiW9INIZ, 3).getValue();
				if (flagFrazionata == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_FRAZIONATA", pagina);
				}

				String flagRiserva = (String) SqlManager.getValueFromVectorParam(datiW9INIZ, 6).getValue();

				// Data verbale prima consegna lavori
				Date dataVerbaleCons = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 4).getValue();
				if (dataVerbaleCons == null && (flagFrazionata != null && "1".equals(flagFrazionata))) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_CONS", pagina);
				}

				if (dataVerbaleCons != null) {
					String nomeCampo = "DATA_VERBALE_CONS";
					if (flagRiserva != null && "2".equals(flagRiserva)) {
						if (dataStipula != null) {
							if (dataVerbaleCons.getTime() < dataStipula.getTime()) {
								String messaggio = this.getMessaggioConfrontoDate(dataVerbaleCons, ">=", dataStipula, "W9INIZ", "DATA_STIPULA", null);
								this.addAvviso(listaControlli, nomeTabella, nomeCampo, "E", pagina, messaggio);
							}
						}
					}
				}

				// Data verbale consegna definitiva
				Date dataVerbaleDef = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 5).getValue();
				if (flagFrazionata != null && "2".equals(flagFrazionata)) {
					if (dataVerbaleDef == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_DEF", pagina);
					}
				}

				if (dataVerbaleDef != null) {
					String nomeCampo = "DATA_VERBALE_DEF";
					if (flagRiserva != null && "2".equals(flagRiserva)) {
						if (dataStipula != null) {
							if (dataVerbaleDef.getTime() < dataStipula.getTime()) {
								String messaggio = this.getMessaggioConfrontoDate(dataVerbaleDef, ">=", dataStipula, "W9INIZ", "DATA_STIPULA", null);
								this.addAvviso(listaControlli, nomeTabella, nomeCampo, "E", pagina, messaggio);
							}
						}
					}
				}

				// Consegna sotto riserva di legge ?
				if (flagRiserva == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVA", pagina);
				}

				// Data effettivo inizio lavori
				Date dataVerbInizio = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 7).getValue();
				if(dataVerbInizio == null){
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_INIZIO", pagina);
				}
				if (flagRiserva != null && "2".equals(flagRiserva)) {
					if (dataStipula != null && dataVerbInizio != null) {
						if (dataVerbInizio.getTime() < dataStipula.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataVerbInizio, ">=", dataStipula, "W9INIZ", "DATA_STIPULA", null);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_INIZIO", "W", pagina, messaggio);
						}
					}
				}

				// Termine contrattuale per dare ultimazione lavori
				Date dataTermine = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 8).getValue();
				if(dataTermine == null){
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_TERMINE"),
					"Inserire la data fine prevista per dare ultimazione ai lavori/servizi/forniture" })));	
				}
				
				if (flagFrazionata != null && "2".equals(flagFrazionata)) {
					if (dataStipula != null && (dataTermine != null && (dataTermine.getTime() <= dataStipula.getTime()))
							|| (dataVerbInizio != null && (dataTermine != null && dataTermine.getTime() <= dataVerbInizio.getTime()))) {

						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_TERMINE"),
								"Data non congrua con data stipula contratto o data di effettivo inizio" })));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase iniziale", "validazione2", e);
		}

		try {
			nomeTabella = "W9PUES";
			pagina = "Pubblicazione esito di gara";

			String selectW9PUES = "select " + " data_guce, " // 0
					+ " data_guri, " // 1
					+ " quotidiani_naz, " // 2
					+ " quotidiani_reg, " // 3
					+ " DATA_ALBO, " // 4
					+ " PROFILO_COMMITTENTE, " // 5
					+ " SITO_MINISTERO_INF_TRASP, " // 6
					+ " SITO_OSSERVATORIO_CP " // 7
					+ " from w9pues where codgara = ? and codlott = ? and num_iniz = ?";

			List<?> datiW9PUES = this.sqlManager.getVector(selectW9PUES, new Object[] { codgara, codlott, num });
			if (datiW9PUES != null && datiW9PUES.size() > 0) {

				int indiceDatiW9PUES = 0;

				Date dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				// Data_guce
				Date dataGUCE = (Date) SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue();
				if (dataGUCE != null && dataVerbAggiudicazione != null) {
					if (dataGUCE.getTime() <= dataVerbAggiudicazione.getTime()) {
						String messaggio = this
								.getMessaggioConfrontoDate(dataGUCE, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_GUCE", "E", pagina, messaggio);
					}
				}

				// Data_guri
				indiceDatiW9PUES++; // indiceDatiW9PUES = 1
				Date dataGURI = (Date) SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue();
				if (dataGURI != null && dataVerbAggiudicazione != null) {
					if (dataGURI.getTime() <= dataVerbAggiudicazione.getTime()) {
						String messaggio = this
								.getMessaggioConfrontoDate(dataGURI, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_GURI", "E", pagina, messaggio);
					}
				}

				// Quotidiani_naz
				indiceDatiW9PUES++; // indiceDatiW9PUES = 2
				Long quotidianiNaz = (Long) SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue();
				if (quotidianiNaz != null && quotidianiNaz.longValue() > 20) {
					String messaggio = "Il valore digitato (" + quotidianiNaz.toString() + ") &egrave; maggiore di 20: verificare il valore";
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_NAZ", "W", pagina, messaggio);
				}

				// Quotidiani_reg
				indiceDatiW9PUES++; // indiceDatiW9PUES = 3
				Long quotidianiReg = (Long) SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue();
				if (quotidianiReg != null && quotidianiReg.longValue() > 20) {
					String messaggio = "Il valore digitato (" + quotidianiReg.toString() + ") &egrave; maggiore di 20: verificare il valore";
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_REG", "W", pagina, messaggio);
				}

				// Profilo committente
				indiceDatiW9PUES = indiceDatiW9PUES + 2; // indiceDatiW9PUES = 5
				if (SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
				}
				// Sito Informatico Ministero Infrastrutture
				indiceDatiW9PUES++;
				if (SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
				}
				// Sito Informatico Osservatorio Contratti Pubblici
				indiceDatiW9PUES++;
				if (SqlManager.getValueFromVectorParam(datiW9PUES, indiceDatiW9PUES).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
				}
			} else {
				this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
				this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase iniziale", "validazione2", e);
		}

		// Misure aggiuntive e migliorative per la sicurezza
		try {
			Long conteggiow9Missic = (Long) this.sqlManager.getObject("select count(*) from W9MISSIC where CODGARA=? and CODLOTT=? and NUM_INIZ=?", new Object[] { codgara, codlott, num });
			if (conteggiow9Missic != null && conteggiow9Missic.longValue() != 0) {
				this.validazioneMisureAggiuntiveSicurezza(codgara, codlott, num, listaControlli);
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla pagina misure aggiuntive/migliorative " + "sicurezza della fase iniziale sopra soglia", "validazione2", e);
		}

		// Incarichi professionali
		try {
			Long conteggiow9inca = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? and codlott = ? and num = ? and sezione = 'IN'", new Object[] { codgara, codlott, num });
			if (conteggiow9inca != null && conteggiow9inca.longValue() != 0) {
				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "IN");
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella conteggio degli incarichi professionali", "validazione2", e);
		}

		// Controllo dei dati della sicurezza
		try {
			pagina = "Scheda sicurezza";
			this.validazioneW9SIC(codgara, codlott, num, listaControlli, pagina);
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla scheda sicurezza", "validazione2", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione2: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase di esecuzione ed avanzamento del contratto
	 * (codice 3).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione3(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione3: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Avanzamento contratto");
		}
		
		String nomeTabella = "W9AVAN";
		String pagina = "Stato di avanzamento";
		String cigMaster = null; 
		try {
			String selectW9AVAN = "select " + " num_avan, " // 0
					+ " data_raggiungimento, " // 1
					+ " importo_sal," // 2
					+ " data_certificato, " // 3
					+ " importo_certificato, " // 4
					+ " flag_ritardo," // 5
					+ " num_giorni_scost, " // 6
					+ " num_giorni_proroga, " // 7
					+ " flag_pagamento," // 8
					+ " data_anticipazione, " // 9
					+ " importo_anticipazione, " // 10
					+ " NUM_APPA " // 11
					+ " from w9avan where codgara = ? and codlott = ? and num_avan = ?";

			List<?> datiW9AVAN = this.sqlManager.getVector(selectW9AVAN, new Object[] { codgara, codlott, num });
			cigMaster = this.getCIGMaster(codgara, codlott);
			if (datiW9AVAN != null && datiW9AVAN.size() > 0) {
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9AVAN, 11).getValue();
				
				Date dataVerbAggiudicazione = null;
				Double importoComplAppalto = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					importoComplAppalto = this.getImportoComplessivoAppaltoMultilotto(codgara, cigMaster, numappa);
					dataVerbAggiudicazione = this.getDataAggiudicazioneSuperiore(codgara, cigMaster, numappa);
				} else {
					importoComplAppalto = (Double) this.sqlManager.getObject("select importo_compl_appalto from w9appa where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara,
							codlott, numappa });
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Double importoComplessivoIntervento = null;
				Double importoComplessivoAppaltoRideterminato = null;
				Long numeroUltimaVariante = (Long) this.sqlManager.getObject("select max(NUM_VARI) from W9VARI where CODGARA=? and CODLOTT=? and NUM_APPA=?", new Object[] { codgara, codlott, numappa });
				if (numeroUltimaVariante != null && numeroUltimaVariante.longValue() > 0) {
					// importoComplessivoIntervento = (Double)
					// this.sqlManager.getObject(
					// "select IMP_COMPL_INTERVENTO from W9VARI where CODGARA=? and CODLOTT=? and NUM_VARI=? ",
					// new Object[]{codgara, codlott, numeroUltimaVariante});
					importoComplessivoAppaltoRideterminato = (Double) this.sqlManager.getObject("select IMP_COMPL_APPALTO from W9VARI where CODGARA=? and CODLOTT=? and NUM_VARI=? ", new Object[] {
							codgara, codlott, numeroUltimaVariante });
				}

				// Numero dello stato di avanzamento
				Long numAvanzamento = (Long) SqlManager.getValueFromVectorParam(datiW9AVAN, 0).getValue();
				if (numAvanzamento == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_AVAN", pagina);
				}

				// Importo anticipazione
				Double importoAnticipazione = (Double) SqlManager.getValueFromVectorParam(datiW9AVAN, 10).getValue();
				// Data dello stato di avanzamento
				Date dataRaggiungimento = (Date) SqlManager.getValueFromVectorParam(datiW9AVAN, 1).getValue();
				/*
				 * if (importo_anticipazione != null &&
				 * importo_anticipazione.doubleValue() != 0) { if
				 * (data_raggiungimento != null) { String messaggio =
				 * "La data non deve essere indicata se &egrave; valorizzato l\'importo anticipazione"
				 * ; this.addAvviso(listaControlli, nomeTabella,
				 * "DATA_RAGGIUNGIMENTO", "E", pagina, messaggio); } } else {
				 */

				if (dataRaggiungimento == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RAGGIUNGIMENTO", pagina);
				} else {
					if (dataVerbAggiudicazione != null) {
						if (dataRaggiungimento.getTime() <= dataVerbAggiudicazione.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataRaggiungimento, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
									CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "DATA_RAGGIUNGIMENTO", "W", pagina, messaggio);
						}
					}
				}
				// }

				// Importo SAL
				// Se esistono varianti, l'importo del SAL deve essere inferiore dell'importo complessivo
				// appalto rideterminato (W9VARI.IMP_COMPL_APPALTO) dell'ultima variante. Altrimenti
				// l'importo del SAL deve essere inferiore dell'importo complessivo dell'appalto
				// (W9APPA.IMPORTO_COMPL_APPALTO)
				Double importoSal = (Double) SqlManager.getValueFromVectorParam(datiW9AVAN, 2).getValue();

				if (importoSal == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SAL", pagina);
				} else {
					if (importoComplessivoAppaltoRideterminato != null) {
						if (importoSal.doubleValue() > importoComplessivoAppaltoRideterminato.doubleValue()) {
							String messaggio = "Valore elevato, verificare";
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_SAL", "E", pagina, messaggio);
						}
					} else {
						if (importoComplAppalto != null) {
							if (importoSal.doubleValue() > importoComplAppalto.doubleValue()) {
								String messaggio = this.getMessaggioControntoImporti(importoSal, "<=", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", new Long(
										CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
								this.addAvviso(listaControlli, nomeTabella, "IMPORTO_SAL", "E", pagina, messaggio);
							}
						}
					}
				}
				// Data emissione del certificato di pagamento
				/*Date dataCertificato = (Date) SqlManager.getValueFromVectorParam(datiW9AVAN, 3).getValue();
				if (importoAnticipazione != null && importoAnticipazione.doubleValue() != 0) {
					if (dataCertificato != null) {
						String messaggio = "La data non deve essere indicata se &egrave; valorizzato l\'importo anticipazione";
						this.addAvviso(listaControlli, nomeTabella, "DATA_CERTIFICATO", "E", pagina, messaggio);
					}
				} else {
					if (dataCertificato == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CERTIFICATO", pagina);
					} else {
						if (dataCertificato != null && dataVerbAggiudicazione != null) {
							if (dataCertificato.getTime() <= dataVerbAggiudicazione.getTime()) {
								String messaggio = this.getMessaggioConfrontoDate(dataCertificato, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
										CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
								this.addAvviso(listaControlli, nomeTabella, "DATA_CERTIFICATO", "E", pagina, messaggio);
							}
						}
					}
				}*/

				// Importo del certificato di pagamento
				Double importoCertificato = (Double) SqlManager.getValueFromVectorParam(datiW9AVAN, 4).getValue();
				if (importoCertificato == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", pagina);
				} else {
					if (importoComplessivoAppaltoRideterminato != null) {
						if (importoCertificato.doubleValue() > importoComplessivoAppaltoRideterminato.doubleValue()) {
							String messaggio = "Valore elevato, verificare"; // this.getMessaggioControntoImporti(importoCertificato,
																				// "<",
							// importoComplessivoAppaltoRideterminato, "W9VARI",
							// "IMP_COMPL_APPALTO", new
							// Long(CostantiW9.VARIANTE_CONTRATTO));
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", "E", pagina, messaggio);
						}
					} else if (importoComplAppalto != null) {
						if (importoCertificato.doubleValue() > importoComplAppalto.doubleValue()) {
							String messaggio = this.getMessaggioControntoImporti(importoCertificato, "<=", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", new Long(
									CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", "E", pagina, messaggio);
						}
					}
				}

				// Ritardo/anticipo
				String flagRitardo = (String) SqlManager.getValueFromVectorParam(datiW9AVAN, 5).getValue();
				if (flagRitardo == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RITARDO", pagina);
				}

				// Numero giorni di scostamento
				Long numGiorniScost = (Long) SqlManager.getValueFromVectorParam(datiW9AVAN, 6).getValue();
				if (flagRitardo != null && !"P".equals(flagRitardo)) {
					if (numGiorniScost == null || (numGiorniScost != null && numGiorniScost.longValue() <= 0)) {
						this.addAvviso(listaControlli, nomeTabella, "NUM_GIORNI_SCOST", "E", pagina, "Il campo deve essere valorizzato con numero maggiore di zero");
					}
				}

				// Modalita' di pagamento del corrispettivo
				if (SqlManager.getValueFromVectorParam(datiW9AVAN, 8).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_PAGAMENTO", pagina);
				}

				// Data del certificato di pagamenti relativo all'anticipazione DATA_ANTICIPAZIONE
				Date dataAnticipazione = (Date) SqlManager.getValueFromVectorParam(datiW9AVAN, 9).getValue();
				if (dataAnticipazione != null) {
					if (dataVerbAggiudicazione != null && (dataAnticipazione.getTime() <= dataVerbAggiudicazione.getTime())) {
						String messaggio = "Data antecedente la data di aggiudicazione";
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_ANTICIPAZIONE"), messaggio })));
					}

					// Se W9FASI.NUM > 1, il campo non dovrebbe essere valorizzato
					if (numAvanzamento != null && numAvanzamento.longValue() > 1) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_ANTICIPAZIONE", "W", pagina, "Anticipazione non ammessa in uno stato di avanzamento successivo al primo");
					} else {
						if (importoAnticipazione == null) {
							listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_ANTICIPAZIONE"),
									"La data non deve essere indicata se l'importo di anticipazione non &egrave; stato valorizzato" })));
						}
					}
				} else {
					if (importoAnticipazione != null) {
						listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DATA_ANTICIPAZIONE"),
						"Non &egrave; stata indicata la data del certificato di pagamento relativo all'anticipazione" })));
					}
				}

				// Importo anticipazione
				if (importoAnticipazione != null && (numAvanzamento != null && numAvanzamento.longValue() > 1)) {
					String messaggio = "Anticipazione non ammessa in uno stato di avanzamento successivo al primo";
					this.addAvviso(listaControlli, nomeTabella, "IMPORTO_ANTICIPAZIONE", "W", pagina, messaggio);
				}

				if (importoAnticipazione != null) {
					if (importoComplessivoAppaltoRideterminato != null) {
						if (importoAnticipazione.doubleValue() >= importoComplessivoAppaltoRideterminato.doubleValue()) {
							String messaggio = "Valore elevato, verificare"; 
							// this.getMessaggioControntoImporti(importoAnticipazione,
							// "<", importoComplessivoAppaltoRideterminato,
							// "W9VARI", "IMP_COMPL_APPALTO",
							// new Long(CostantiW9.VARIANTE_CONTRATTO));
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_ANTICIPAZIONE", "E", pagina, messaggio);
						}
					} else if (importoComplAppalto != null) {
						if (importoAnticipazione.doubleValue() >= importoComplAppalto.doubleValue()) {
							String messaggio = this.getMessaggioControntoImporti(importoAnticipazione, "<", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", new Long(
									CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_ANTICIPAZIONE", "E", pagina, messaggio);
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di avanzamento", "validazione3", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione3: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase elenco imprese invitate/partecipanti (codice
	 * 101).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione101(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione101: inizio metodo");
		}

		String nomeTabella = "W9IMPRESE";
		String pagina = "";

		try {

			// sono nel caso di una impresa multipla
			String selectNumRaggW9IMPRESE = "select distinct num_ragg,tipoagg from w9Imprese where codgara = ? and codlott = ? and tipoagg != 3 order by num_ragg, tipoagg";
			List<?> numeriRaggruppamentiW9IMPRESE = this.sqlManager.getListVector(selectNumRaggW9IMPRESE, new Object[] { codgara, codlott });

			for (int j = 0; j < numeriRaggruppamentiW9IMPRESE.size(); j++) {

				// lavoro sul singolo raggruppamento di imprese
				Vector<?> vettoreNumeroRaggruppamento = (Vector<?>) numeriRaggruppamentiW9IMPRESE.get(j);
				Long numeroRaggruppamento = (Long) ((JdbcParametro) vettoreNumeroRaggruppamento.get(0)).getValue();
				Long tipoImpresa = (Long) ((JdbcParametro) vettoreNumeroRaggruppamento.get(1)).getValue();

				List<Object> listaControlliRowW9Impresa = new ArrayList<Object>();
				// ottenuta la lista le assegno un titolo da visualizzare
				String titolo = "<b>Identificativo Impresa : Gruppo_R" + numeroRaggruppamento + "</b>";
				this.setTitolo(listaControlliRowW9Impresa, titolo);

				// conto il numero di imprese partecipanti
				String countNumRaggW9IMPRESE = "select count(codimp) from  w9Imprese where codgara = ? and codlott = ? and tipoagg = ?  and num_ragg = ? ";
				int numeroImpreseRaggruppamentoW9IMPRESE = ((Long) this.sqlManager.getObject(countNumRaggW9IMPRESE, new Object[] { codgara, codlott, tipoImpresa, numeroRaggruppamento })).intValue();

				// conto tutti i codici fiscali siano duplici
				String countCodiciFiscaliDupliciW9Imprese = "select "
						+ "impr.cfimp, count(impr.codimp) as CodiciFiscaliDuplici "
						+ // includo anche il codice fiscale oltre il conteggio
							// se dovesse servire in futuro
						"from impr join w9imprese on impr.codimp = w9imprese.codimp " + "where w9imprese.codgara = ? and w9imprese.codlott = ? and w9imprese.tipoagg = ?  and w9imprese.num_ragg = ? "
						+ "group by impr.cfimp having count(impr.codimp) > 1";

				List<?> CodiciFiscaliDupliciW9Imprese = this.sqlManager.getListVector(countCodiciFiscaliDupliciW9Imprese, new Object[] { codgara, codlott, tipoImpresa, numeroRaggruppamento });

				if (tipoImpresa == 1) {
					if (numeroImpreseRaggruppamentoW9IMPRESE < 2) {
						listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "", "<b>Numero delle Imprese</b><br />Per le ATI indicare almeno due imprese" })));
					}

					// estraggo tutti i valori nulli nel ruolo
					String selectRuoliNulliW9Imprese = "select codimp from w9imprese where codgara = ? and codlott = ? and tipoagg = ?  and num_ragg = ? and ruolo is null";
					List<?> ruoliNulliW9Imprese = this.sqlManager.getListVector(selectRuoliNulliW9Imprese, new Object[] { codgara, codlott, tipoImpresa, numeroRaggruppamento });

					if (!ruoliNulliW9Imprese.isEmpty()) {
						listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "", "<b>Ruolo nelle Imprese</b><br />Per le ATI tutti i ruoli devono essere indicati" })));
					} else {
						// conto quanti sono i ruoli mandatari
						String countMandatariW9IMPRESE = "select count(codimp) from w9imprese where codgara = ? and codlott = ? and tipoagg = ?  and num_ragg = ? and ruolo = 1";
						int numeroMandatariW9IMPRESE = ((Long) this.sqlManager.getObject(countMandatariW9IMPRESE, new Object[] { codgara, codlott, tipoImpresa, numeroRaggruppamento })).intValue();

						if (numeroMandatariW9IMPRESE == 0) {
							listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "", "<b>Ruolo nelle Imprese</b><br />Per le ATI indicare almeno una impresa mandataria" })));
						}
						if (numeroMandatariW9IMPRESE > 1) {
							listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "", "<b>Ruolo nelle Imprese</b><br />Per le ATI indicare una sola impresa mandataria" })));
						}
					}
				}

				if (!CodiciFiscaliDupliciW9Imprese.isEmpty()) {
					listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "",
							"<b>Univocit&agrave; nelle Imprese</b><br />Non possono essere presenti pi&ugrave; imprese con identico Codice Fiscale" })));
				}

				String selectW9IMPRESE = "select " + " partecip, " // 0
						+ " num_impr, " // 1
						+ " ruolo, " // 2
						+ " codimp " // 3
						+ " from w9imprese where codgara = ? and codlott = ? and tipoagg = ? and num_ragg = ? order by tipoagg asc, num_impr asc, num_ragg asc";
				// datiw9Impresa rappresenta un raggruppamento di imprese
				List<?> datiW9IMPRESE = this.sqlManager.getListVector(selectW9IMPRESE, new Object[] { codgara, codlott, tipoImpresa, numeroRaggruppamento });
				for (int k = 0; k < datiW9IMPRESE.size(); k++) {
					// w9Impresa rappresenta la singola impresa all'interno di
					// un raggruppamento di imprese
					Vector<?> w9Impresa = (Vector<?>) datiW9IMPRESE.get(k);

					// controllo la validita' della singola impresa
					String codiceImpresa = (String) ((JdbcParametro) w9Impresa.get(3)).getValue();
					// Codice dell'impresa invitata/partecipante
					if (isStringaValorizzata(codiceImpresa)) {
						Long countImpr = (Long) this.sqlManager.getObject("select count(*) from IMPR where CODIMP=?", new Object[] { codiceImpresa });
						if (countImpr != null && countImpr.longValue() == 1) {
							this.validazioneArrayImprese(listaControlliRowW9Impresa, pagina, new String[] { codiceImpresa }, false);
						} else {
							listaControlliRowW9Impresa.add(((new Object[] { "E", pagina, "", "L'impresa n. " + (k + 1) + " non \u00E8 presente nell'archivio imprese." })));
						}
					} else {
						this.addCampoObbligatorio(listaControlliRowW9Impresa, nomeTabella, "CODIMP", pagina);
					}
				}
				if (listaControlliRowW9Impresa.size() > 1) {
					listaControlli.addAll(listaControlliRowW9Impresa);
				}
			}

			// sono nel caso di un'impresa singola
			String selectW9IMPRESESingole = "select "
			    + " partecip, " // 0
					+ " num_impr, " // 1
					+ " codimp "    // 2
					+ " from W9IMPRESE where codgara = ? and codlott = ? and tipoagg = 3 order by  num_impr asc";
			List<?> datiW9IMPRESESingole = this.sqlManager.getListVector(selectW9IMPRESESingole, new Object[] { codgara, codlott });
			for (int k = 0; k < datiW9IMPRESESingole.size(); k++) {
				// W9ImpresaSingola rappresenta la singola impresa
				Vector<?> W9ImpresaSingola = (Vector<?>) datiW9IMPRESESingole.get(k);
				List<Object> listaControlliRowW9ImpresaSingola = new ArrayList<Object>();

				// controllo la validita' della singola impresa
				String codiceImpresa = (String) ((JdbcParametro) W9ImpresaSingola.get(2)).getValue();

				// ottenuta la lista le assegno un titoloImprSingola da visualizzare
				String titoloImprSingola = "<b>Identificativo Impresa : " + codiceImpresa + "</b>";
				this.setTitolo(listaControlliRowW9ImpresaSingola, titoloImprSingola);

				// Codice dell'impresa invitata/partecipante
				if (isStringaValorizzata(codiceImpresa)) {
					Long countImpr = (Long) this.sqlManager.getObject("select count(*) from IMPR where CODIMP=?", new Object[] { codiceImpresa });
					if (countImpr != null && countImpr.longValue() == 1) {
						this.validazioneArrayImprese(listaControlliRowW9ImpresaSingola, pagina, new String[] { codiceImpresa }, false);
					} else {
						listaControlliRowW9ImpresaSingola.add(((new Object[] { "E", pagina, "", "L'impresa n. " + (k + 1) + " non \u00E8 presente nell'archivio imprese." })));
					}
				} else {
					this.addCampoObbligatorio(listaControlliRowW9ImpresaSingola, nomeTabella, "CODIMP", pagina);
				}

				if (listaControlliRowW9ImpresaSingola.size() > 1) {
					listaControlli.addAll(listaControlliRowW9ImpresaSingola);
				}
			}

			// fine del try
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase elenco imprese invitate/partecipanti", "validazione101", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione101: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase di avanzamento semplificato del contratto
	 * (codice 102).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione102(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione102: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Stato di avanzamento " + num);
		}
		
		String nomeTabella = "W9AVAN";
		String pagina = "Stato di avanzamento";

		try {
			String selectW9AVAN = "select " 
					+ " data_certificato, " // 0
					+ " importo_certificato " // 1
					+ " from w9avan where codgara = ? and codlott = ? and num_avan = ?";

			List<?> datiW9AVAN = this.sqlManager.getVector(selectW9AVAN, new Object[] { codgara, codlott, num });

			if (datiW9AVAN != null && datiW9AVAN.size() > 0) {

				// Data emissione del certificato di pagamento
				Date dataCertificato = (Date) SqlManager.getValueFromVectorParam(datiW9AVAN, 0).getValue();

				if (dataCertificato == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CERTIFICATO", pagina);
				}


				// Importo del certificato di pagamento
				Double importoCertificato = (Double) SqlManager.getValueFromVectorParam(datiW9AVAN, 1).getValue();
				
				if (importoCertificato == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", pagina);
				} /*else {
					Double importoComplAppalto = (Double) this.sqlManager.getObject("select importo_compl_appalto from w9appa where codgara = ? and codlott = ? and num_appa = 1", new Object[] {
							codgara, codlott });
					
					Double importoComplessivoAppaltoRideterminato = null;
					Long numeroUltimaVariante = (Long) this.sqlManager.getObject("select max(NUM_VARI) from W9VARI where CODGARA=? and CODLOTT=? ", new Object[] { codgara, codlott });
					if (numeroUltimaVariante != null && numeroUltimaVariante.longValue() > 0) {
						importoComplessivoAppaltoRideterminato = (Double) this.sqlManager.getObject("select IMP_COMPL_APPALTO from W9VARI where CODGARA=? and CODLOTT=? and NUM_VARI=? ", new Object[] {
								codgara, codlott, numeroUltimaVariante });
					}
					
					if(importoComplessivoAppaltoRideterminato != null){
						if (importoCertificato.doubleValue() > importoComplessivoAppaltoRideterminato) {
							String messaggio = "Valore elevato, verificare";
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", "E", pagina, messaggio);
						}
					} else {
						if (importoComplAppalto != null) {	
							if (importoCertificato.doubleValue() > importoComplAppalto.doubleValue()) {
								String messaggio = "Valore elevato, verificare"; // this.getMessaggioControntoImporti(
								// importoCertificato, "<", importoComplAppalto,
								// "W9APPA",
								// "IMPORTO_COMPL_APPALTO", new
								// Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE));
								this.addAvviso(listaControlli, nomeTabella, "IMPORTO_CERTIFICATO", "E", pagina, messaggio);
							}
						}
					}
				}*/
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di avanzamento semplificata", "validazione102", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione102: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase di conclusione del contratto (codice 4).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione4(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione4: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Conclusione esecuzione contratto");
		}
		
		String nomeTabella = "W9CONC";
		String pagina = "";
		String cigMaster = null;
		try {
			String selectW9CONC = "select " 
			    + " intantic, " // 0
					+ " id_motivo_interr, " // 1
					+ " id_motivo_risol," // 2
					+ " data_risoluzione, " // 3
					+ " flag_oneri, " // 4
					+ " oneri_risoluzione, " // 5
					+ " flag_polizza, " // 6
					+ " data_ultimazione, " // 7
					+ " DATA_VERBALE_CONSEGNA, " // 8
					+ " TERMINE_CONTRATTO_ULT, " // 9
					+ " NUM_INFORTUNI ," // 10
					+ " NUM_INF_PERM, " // 11
					+ " NUM_INF_MORT, " // 12
					+ " NUM_GIORNI_PROROGA, " // 13
					+ " NUM_APPA " // 14
					+ "from w9conc where codgara = ? and codlott = ? and num_conc = ?";

			List<?> datiW9CONC = this.sqlManager.getVector(selectW9CONC, new Object[] { codgara, codlott, num });
			cigMaster = this.getCIGMaster(codgara, codlott);
			if (datiW9CONC != null && datiW9CONC.size() > 0) {

				pagina = "Interruzione anticipata del procedimento";
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 14).getValue();
				Date dataVerbAggiudicazione = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					dataVerbAggiudicazione = this.getDataAggiudicazioneInferiore(codgara, cigMaster, numappa);
				} else {
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Interruzione anticipata del contratto
				String intantic = (String) SqlManager.getValueFromVectorParam(datiW9CONC, 0).getValue();
				if (intantic == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "INTANTIC", pagina);
				}

				if (intantic != null && "1".equals(intantic)) {
					// Causa dell'interruzione anticipata
					Long idMotivoInterr = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 1).getValue();
					if (idMotivoInterr == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_INTERR", pagina);
					}

					// Motivazione della risoluzione
					Long idMotivoRisol = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 2).getValue();
					if (idMotivoInterr != null && idMotivoInterr.intValue() == 2) {
						if (idMotivoRisol == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_RISOL", pagina);
						}
					}

					// Data risoluzione
					Date dataRisoluzione = (Date) SqlManager.getValueFromVectorParam(datiW9CONC, 3).getValue();
					if (idMotivoInterr != null) {
						if (dataRisoluzione == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RISOLUZIONE", pagina);
						} else {
							//Date dataStipula = this.getDataStipulaContratto(codgara, codlott, numappa);
							if (dataVerbAggiudicazione != null) {
								if (dataRisoluzione.getTime() <= dataVerbAggiudicazione.getTime()) {
									String messaggio = this.getMessaggioConfrontoDate(dataRisoluzione, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
									this.addAvviso(listaControlli, nomeTabella, "DATA_RISOLUZIONE", "E", pagina, messaggio);
								}
							}
						}
					}

					// Oneri economici derivanti da risoluzione / recesso
					String flagOneri = (String) SqlManager.getValueFromVectorParam(datiW9CONC, 4).getValue();
					if (idMotivoInterr != null) {
						switch (idMotivoInterr.intValue()) {
						case 2:
						case 4:
						case 5:
							if (flagOneri == null) {
								this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ONERI", pagina);
							}
							break;

						default:
							break;
						}
					}

					// Importo degli oneri
					Double oneriRisoluzione = (Double) SqlManager.getValueFromVectorParam(datiW9CONC, 5).getValue();

					if (flagOneri != null && !"0".equals(flagOneri) && oneriRisoluzione == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", pagina);
					}

					Double importoComplIntervento = (Double) this.sqlManager.getObject("select importo_compl_intervento from w9appa where codgara = ? and codlott = ? and num_appa = ?", new Object[] {
							codgara, codlott, numappa });
					if (oneriRisoluzione != null && importoComplIntervento != null) {
						if (oneriRisoluzione.doubleValue() >= importoComplIntervento.doubleValue()) {
							String messaggio = this.getMessaggioControntoImporti(oneriRisoluzione, "<", importoComplIntervento, "W9APPA", "IMPORTO_COMPL_INTERVENTO", new Long(
									CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, messaggio);
						}
					}

					if (flagOneri != null && oneriRisoluzione != null) {
						if (!"0".equals(flagOneri) && oneriRisoluzione.doubleValue() == 0) {
							String messaggio = "Il valore \"No\" &egrave; ammesso solo se al campo \""
							  + this.getDescrizioneCampo("W9CONC", "FLAG_ONERI")
									+ "\" &egrave; stato selezionato il valore \"Senza Oneri\"";
							this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, messaggio);
						}
					}

					// Flag polizza
					if (idMotivoInterr != null && SqlManager.getValueFromVectorParam(datiW9CONC, 6).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_POLIZZA", pagina);
					}
				}

        if (UtilitySITAT.existsFase(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, sqlManager)
            || UtilitySITAT.existsFase(codgara, codlott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, sqlManager)) {
          
  				pagina = "Ultimazione lavori";
  				// Data verbale di consegna
  				if (SqlManager.getValueFromVectorParam(datiW9CONC, 8).getValue() == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERBALE_CONSEGNA", pagina);
  				}

  				// Data termine contrattuale ultimazione
  				if (intantic != null && "2".equals(intantic) && SqlManager.getValueFromVectorParam(datiW9CONC, 9).getValue() == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "TERMINE_CONTRATTO_ULT", pagina);
  				}
  				// Data ultimazione
  				Date dataUltimazione = (Date) SqlManager.getValueFromVectorParam(datiW9CONC, 7).getValue();
  				if (intantic != null && "2".equals(intantic) && dataUltimazione == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", pagina);
  				} else {
  					if (dataVerbAggiudicazione != null && dataUltimazione != null && (dataUltimazione.getTime() <= dataVerbAggiudicazione.getTime())) {
  						String messaggio = this.getMessaggioConfrontoDate(dataUltimazione, ">", dataVerbAggiudicazione, 
  						    "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
  						this.addAvviso(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", "E", pagina, messaggio);
  					}
  				}
  				
  				Long numeroInfortumi = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 10).getValue();
  				Long numeroInfortumiPostumiPermanenti = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 11).getValue();
  				Long numeroInfortumiMortali = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 12).getValue();
  
  				// Numero infortuni
  				if (intantic != null && "2".equals(intantic) && numeroInfortumi == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_INFORTUNI", pagina);
  				} else {
  					if (numeroInfortumiPostumiPermanenti != null && numeroInfortumiMortali != null) {
  						if (numeroInfortumi.longValue() < (numeroInfortumiPostumiPermanenti.longValue() + numeroInfortumiMortali.longValue())) {
  							String messaggio = "Il numero di infortuni deve essere maggiore o uguale alla " + "somma del numero di inforuni con postumi permanenti e infortuni mortali";
  							this.addAvviso(listaControlli, nomeTabella, "NUM_INFORTUNI", "E", pagina, messaggio);
  						}
  					}
  
  					if (numeroInfortumi != null && numeroInfortumi.longValue() > 9) {
  						this.addAvviso(listaControlli, nomeTabella, "NUM_INFORTUNI", "W", pagina, "Il numero di infortuni &egrave; maggiore di 9");
  					}
  				}
  
  				// Numero infortuni con postumi permanenti
  				if (intantic != null && "2".equals(intantic) && numeroInfortumiPostumiPermanenti == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_INF_PERM", pagina);
  				}
  				// Numero infortuni mortali
  				if (intantic != null && "2".equals(intantic) && numeroInfortumiMortali == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_INF_MORT", pagina);
  				}
  				
  				// Numero di giorni di proroga
  				Long numeroGiorniProroga = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 13).getValue();
  				if (numeroGiorniProroga != null && numeroGiorniProroga.longValue() < 0) {
  					this.addAvviso(listaControlli, nomeTabella, "NUM_GIORNI_PROROGA", "E", pagina, "Il numero dei giorni di proroga deve essere maggiore o uguale a zero");
  				}
				}

				Long numeroFasiPrecedentiDaInviare = (Long) this.sqlManager.getObject(
					"select count(*) from W9FASI where CODGARA=? and CODLOTT=? and NUM_APPA=? and FASE_ESECUZIONE not in (4,5) and DAEXPORT='1'",
						new Object[] { codgara, codlott, numappa });
				if (numeroFasiPrecedentiDaInviare.longValue() > 0) {
					listaControlli.add(((new Object[] { "W", pagina, "", "Esistono delle schede non trasmesse. Trasmettendo la scheda conclusione, non sar\u00E0 pi\u00F9 possibile trasmetterle"})));
				}

				Long numeroSospensioniAperte = (Long) this.sqlManager.getObject(
				    "select count(*) from W9SOSP where CODGARA=? and CODLOTT=? and NUM_APPA=? and DATA_VERB_RIPR is null",
				      new Object[] { codgara, codlott, numappa });
				
				if (numeroSospensioniAperte.longValue() > 0) {
				  List<Vector<?>> listaNumSosp = this.sqlManager.getListVector("select NUM_SOSP from W9SOSP where CODGARA=? and CODLOTT=? and NUM_APPA=? and DATA_VERB_RIPR is null order by NUM_SOSP asc",
				      new Object[] { codgara, codlott, numappa });
				  
				  String msgSopensioniAperte = "Esistono delle sospensioni senza data di ripresa (vedi sospensioni n. ";
				  String strNumSosp = "";
				  for (int iu = 0; iu < listaNumSosp.size(); iu++) {
				    Vector<?> vect = listaNumSosp.get(iu);
				    String temp = ((Long) SqlManager.getValueFromVectorParam(vect, 0).getValue()).toString();
				    strNumSosp = strNumSosp.concat(temp);
				    if (iu < (listaNumSosp.size() -1)) {
				      strNumSosp = strNumSosp.concat(", ");
				    }
				  }
				  msgSopensioniAperte = msgSopensioniAperte.concat(strNumSosp).concat(")");
				  
				  this.addAvviso(listaControlli, "W9SOSP", "DATA_VERB_RISP", "E", "", msgSopensioniAperte);
				}
				
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di conclusione del contratto", "validazione4", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione4: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase di collaudo (codice 5).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione5(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione5: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Collaudo contratto");
		}
		
		String nomeTabella = "W9COLL";
		Long numappa = null;
		String cigMaster = null;
		try {
			String pagina = "Collaudo/verifica di conformit&agrave;...";

			String selectW9COLL = "select " 
					+ " data_regolare_esec, " // 0
					+ " data_collaudo_stat, " // 1
					+ " modo_collaudo,"       // 2
					+ " data_nomina_coll, "   // 3
					+ " data_inizio_oper, "   // 4
					+ " data_cert_collaudo, " // 5
					+ " data_delibera, "      // 6
					+ " esito_collaudo, "     // 7
					+ " imp_finale_lavori, "  // 8
					+ " imp_finale_servizi, " // 9
					+ " imp_finale_fornit, " // 10
					+ " imp_subtotale, "     // 11
					+ " imp_finale_secur, "  // 12
					+ " imp_progettazione, " // 13
					+ " imp_disposizione, "  // 14
					+ " num_appa,"					 // 15
					+ " tipo_collaudo "      // 16
					+ " from W9COLL where codgara = ? and codlott = ? and num_coll = ?";
			cigMaster = this.getCIGMaster(codgara, codlott);
			List<?> datiW9COLL = this.sqlManager.getVector(selectW9COLL, new Object[] { codgara, codlott, num });

			if (datiW9COLL != null && datiW9COLL.size() > 0) {
				numappa = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 15).getValue();
				Date dataUltimazione = (Date) this.sqlManager.getObject("select data_ultimazione from w9conc where codgara=? and codlott=? and num_appa=?",
						new Object[] { codgara, codlott, numappa });

				// DATA_VERB_INIZIO.W9INIZ
				Date dataEffettivoInizioLavori = (Date) this.sqlManager.getObject("select data_verb_inizio from w9iniz where codgara=? and codlott=? and num_appa=?", 
						new Object[] { codgara, codlott, numappa });

				Vector<?> datiW9APPA = this.sqlManager.getVector("select data_verb_aggiudicazione, IMPORTO_COMPL_INTERVENTO from w9appa where codgara=? and codlott=? and num_appa=?", 
						new Object[] { codgara, codlott, numappa });

				Double importoComplessivoIntervento = null;
				if (StringUtils.isNotEmpty(cigMaster)) {
					importoComplessivoIntervento = this.getImportoComplessivoInterventoMultilotto(codgara, cigMaster, numappa);
				} else {
					importoComplessivoIntervento = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue();
				}
				
				String tipoContratto = UtilitySITAT.getTipoContrattoLotto(codgara, codlott, this.sqlManager);

				// Tipo di collaudo
				Long tipoCollaudo = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 16).getValue();
				// Modalita' di collaudo
				Long modalitaCollaudo = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 2).getValue();
				// Data del certificato di regolare esecuzione e Data redazione del
				// certificato di collaudo devono essere valorizzate in alternativa
				Date dataRegolareEsec = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 0).getValue();
				
				if (tipoCollaudo == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_COLLAUDO", pagina);
				} else if (tipoCollaudo.intValue() == 1) {
					if (modalitaCollaudo == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "MODO_COLLAUDO", pagina);
					}
					
					if (importoComplessivoIntervento != null && importoComplessivoIntervento.doubleValue() <= 500000 && ("L".equals(tipoContratto) || "CL".equals(tipoContratto))) {
						this.addAvviso(listaControlli, nomeTabella, "TIPO_COLLAUDO", "W", pagina, "Redigere certificato di regolare esecuzione in luogo del certificato di collaudo");
					}
				} else {
					if (dataRegolareEsec == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", pagina);
					}
					
					if (importoComplessivoIntervento != null && importoComplessivoIntervento.doubleValue() > 1000000 && ("L".equals(tipoContratto) || "CL".equals(tipoContratto))) {
						this.addAvviso(listaControlli, nomeTabella, "TIPO_COLLAUDO", "W", pagina, "Redigere certificato di collaudo in luogo del certificato di regolare esecuzione");
					}					
				}

				if (dataRegolareEsec != null) {
					if (dataUltimazione != null) {
						if (dataRegolareEsec.getTime() <= dataUltimazione.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataRegolareEsec, ">", dataUltimazione, "W9CONC", "DATA_ULTIMAZIONE", new Long(
									CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "E", pagina, messaggio);
						}
					}
				} else {
					if (importoComplessivoIntervento != null && importoComplessivoIntervento.doubleValue() <= 1000000 && ("L".equals(tipoContratto) || "CL".equals(tipoContratto))) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_REGOLARE_ESEC", "W", pagina, "Redigere certificato di collaudo in luogo del certificato di regolare esecuzione");
					}
				}

				// Data del collaudo statico
				Date dataCollaudoStatico = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 1).getValue();
				if (dataCollaudoStatico != null && dataEffettivoInizioLavori != null) {
					if (dataCollaudoStatico.getTime() <= dataEffettivoInizioLavori.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataCollaudoStatico, ">", dataEffettivoInizioLavori, "W9INIZ", "DATA_VERB_INIZIO", new Long(
								CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_COLLAUDO_STAT", "E", pagina, messaggio);
					}
				}

				// Data nomina collaudatore/commissione
				Date dataNominaColl = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 3).getValue();
				if (tipoCollaudo != null && tipoCollaudo.intValue() == 1 && dataNominaColl == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_NOMINA_COLL", pagina);
				}

				// Data inizio operazioni di collaudo
				Date dataInizOper = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 4).getValue();
				if (tipoCollaudo != null && tipoCollaudo.intValue() == 1 && dataInizOper == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_INIZIO_OPER", pagina);
				}

				if (dataInizOper != null && dataNominaColl != null) {
					if (dataInizOper.getTime() < dataNominaColl.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataInizOper, ">=", dataNominaColl, "W9COLL", "DATA_NOMINA_COLL", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_INIZIO_OPER", "E", pagina, messaggio);
					}
				}

				// Data redazione certificato di collaudo
				Date dataCertCollaudo = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 5).getValue();
				if (tipoCollaudo != null && tipoCollaudo.intValue() == 1 && dataCertCollaudo == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_CERT_COLLAUDO", pagina);
				}

				if (dataInizOper != null && dataCertCollaudo != null) {
					if (dataInizOper.getTime() > dataCertCollaudo.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataInizOper, "<", dataCertCollaudo, "W9COLL", "DATA_CERT_COLLAUDO", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_INIZIO_OPER", "E", pagina, messaggio);
					}
				}

				// Data delibera di ammissibilita' del collaudo
				Date dataDelibera = (Date) SqlManager.getValueFromVectorParam(datiW9COLL, 6).getValue();
				if (dataDelibera != null && dataCertCollaudo != null) {
					if (dataDelibera.getTime() < dataCertCollaudo.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataDelibera, ">=", dataCertCollaudo, "W9COLL", "DATA_CERT_COLLAUDO", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_DELIBERA", "E", pagina, messaggio);
					}
				}

				// Esito del collaudo
				if (SqlManager.getValueFromVectorParam(datiW9COLL, 7).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ESITO_COLLAUDO", pagina);
				}
				// Importo finale lavori
				Double impFinaleLavori = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 8).getValue();
				if (tipoContratto != null && ("L".equals(tipoContratto) || "CL".equals(tipoContratto)) && (impFinaleLavori == null || (impFinaleLavori != null && impFinaleLavori.doubleValue() <= 0))) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_FINALE_LAVORI", pagina);
				}

				// Importo finale servizi
				Double impFinaleServizi = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 9).getValue();
				if (tipoContratto != null && ("S".equals(tipoContratto) || "CS".equals(tipoContratto))
						&& (impFinaleServizi == null || (impFinaleServizi != null && impFinaleServizi.doubleValue() <= 0))) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_FINALE_SERVIZI", pagina);
				}

				// Importo finale forniture
				Double impFinaleFornit = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 10).getValue();
				if (tipoContratto != null && "F".equals(tipoContratto) && (impFinaleFornit == null || (impFinaleFornit != null && impFinaleFornit.doubleValue() <= 0))) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_FINALE_FORNIT", pagina);
				}

				Double impSubtotale = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 11).getValue();

				// Importo per l'attuazione della sicurezza
				Double impFinaleSicur = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 12).getValue();
				if (impSubtotale != null && impFinaleSicur != null) {
					if (impFinaleSicur.doubleValue() >= impSubtotale.doubleValue()) {
						String messaggio = "L\'importo digitato (" + this.importoToStringa(impFinaleSicur) + ") deve essere inferiore al valore del campo \""
								+ this.getDescrizioneCampo("W9COLL", "IMP_SUBTOTALE") + "\" (" + this.importoToStringa(impSubtotale) + "): verificare l\'importo digitato";
						this.addAvviso(listaControlli, nomeTabella, "IMP_FINALE_SECUR", "E", pagina, messaggio);
					}
				}

				// Importo progettazione
				Double impProgettazione = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 13).getValue();
				if (impProgettazione != null && impSubtotale != null) {
					if (impProgettazione.doubleValue() >= impSubtotale.doubleValue()) {
						String messaggio = "L\'importo digitato (" + this.importoToStringa(impProgettazione) + ") deve essere inferiore al valore del campo \""
								+ this.getDescrizioneCampo("W9COLL", "IMP_SUBTOTALE") + "\" (" + this.importoToStringa(impSubtotale) + "): verificare l\'importo digitato";
						this.addAvviso(listaControlli, nomeTabella, "IMP_PROGETTAZIONE", "W", pagina, messaggio);
					}
				}

				// Importo progettazione somme a disposizione
				Double impDisposizione = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 14).getValue();
				if (impDisposizione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", pagina);
				} else if (impDisposizione.doubleValue() == 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", "W", pagina, "Verificare il valore dell'importo a disposizione");
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di collaudo", "validazione5", e);
		}

		// Ulteriori riserve
		try {
			String pagina = "";

			String selectW9COLL = "select " 
					+ " amm_num_definite, " // 0
					+ " amm_num_dadef, " // 1
					+ " amm_importo_rich, " // 2
					+ " amm_importo_def, " // 3
					+ " arb_num_definite, " // 4
					+ " arb_num_dadef, " // 5
					+ " arb_importo_rich, " // 6
					+ " arb_importo_def, " // 7
					+ " giu_num_definite, " // 8
					+ " giu_num_dadef, " // 9
					+ " giu_importo_rich, " // 10
					+ " giu_importo_def, " // 11
					+ " tra_num_definite, " // 12
					+ " tra_num_dadef, " // 13
					+ " tra_importo_rich, " // 14
					+ " tra_importo_def ," // 15
					+ " IMP_COMPL_INTERVENTO " // 16
					+ " from w9coll where codgara = ? and codlott = ? and num_coll = ?";
			List<?> datiW9COLL = this.sqlManager.getVector(selectW9COLL, new Object[] { codgara, codlott, num });

			if (datiW9COLL != null && datiW9COLL.size() > 0) {
				// In via amministrativa
				Long ammNumDefinite = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 0).getValue();
				Long ammNumDaDef = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 1).getValue();
				Double ammImportoRich = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 2).getValue();
				Double ammImportoDef = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 3).getValue();
				Double impComplIntervento = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 16).getValue();

				if ((ammNumDefinite != null && ammNumDefinite.longValue() > 0) || (ammNumDaDef != null && ammNumDaDef.longValue() > 0)) {
					pagina = "Riserve in via amministrativa...";
					if (ammImportoRich == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "AMM_IMPORTO_RICH", pagina);
					} else {
						if (impComplIntervento != null && (ammImportoRich.doubleValue() > impComplIntervento.doubleValue())) {
							String messaggio = this.getMessaggioControntoImporti(ammImportoRich, "<=", impComplIntervento, "W9COLL", "IMP_COMPL_INTERVENTO", null);
							this.addAvviso(listaControlli, nomeTabella, "AMM_IMPORTO_RICH", "E", pagina, messaggio);
						}
					}

					if (ammImportoDef == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "AMM_IMPORTO_DEF", pagina);
					} else {
						if (impComplIntervento != null && (ammImportoDef.doubleValue() > impComplIntervento.doubleValue())) {
							String messaggio = this.getMessaggioControntoImporti(ammImportoDef, "<=", impComplIntervento, "W9COLL", "IMP_COMPL_INTERVENTO", null);
							this.addAvviso(listaControlli, nomeTabella, "AMM_IMPORTO_RICH", "E", pagina, messaggio);
						}
					}
				}

				// In via arbitrale
				Long arbNumDefinite = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 4).getValue();
				Long arbNumDaDef = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 5).getValue();
				Double arbImportoRich = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 6).getValue();
				Double arbImportoDef = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 7).getValue();

				if ((arbNumDefinite != null && arbNumDefinite.longValue() > 0) || (arbNumDaDef != null && arbNumDaDef.longValue() > 0)) {
					pagina = "Riserve in via arbitrale...";
					if (arbImportoRich == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ARB_IMPORTO_RICH", pagina);
					}
					if (arbImportoDef == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ARB_IMPORTO_DEF", pagina);
					}
				}

				// In via giudiziale
				Long giuNumDefinite = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 8).getValue();
				Long giuNumDadef = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 9).getValue();
				Double giuImportoRich = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 10).getValue();
				Double giuImportoDef = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 11).getValue();

				if ((giuNumDefinite != null && giuNumDefinite.longValue() > 0) || (giuNumDadef != null && giuNumDadef.longValue() > 0)) {
					pagina = "Riserve in via giudiziale...";
					if (giuImportoRich == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "GIU_IMPORTO_RICH", pagina);
					}
					if (giuImportoDef == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "GIU_IMPORTO_DEF", pagina);
					}
				}

				// In via transattiva
				Long traNumDefinite = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 12).getValue();
				Long traNumDadef = (Long) SqlManager.getValueFromVectorParam(datiW9COLL, 13).getValue();
				Double traImportoRich = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 14).getValue();
				Double traImportoDef = (Double) SqlManager.getValueFromVectorParam(datiW9COLL, 15).getValue();

				if ((traNumDefinite != null && traNumDefinite.longValue() > 0) || (traNumDadef != null && traNumDadef.longValue() > 0)) {
					pagina = "Riserve in via transattiva...";
					if (traImportoRich == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TRA_IMPORTO_RICH", pagina);
					}
					if (traImportoDef == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TRA_IMPORTO_DEF", pagina);
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al collaudo: seconda parte", "validazione5", e);
		}

		// Aggiungo, in coda, la validazione delle singole schede dei soggetti
		// incaricati
		try {
			Long conteggiow9inca = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? and codlott = ? and num = ? and sezione = 'CO'", new Object[] { codgara, codlott, num });
			// if (conteggiow9inca == null
			// || (conteggiow9inca != null && (new
			// Long(0)).equals(conteggiow9inca)))
			// {
			// this.setTitolo(listaControlli, "Incarichi professionali");
			// String messaggio =
			// "Indicare almeno un incaricato / professionista";
			// listaControlli.add(((Object) (new Object[] { "E",
			// "Incarichi professionali", "Incarichi professionali", messaggio
			// })));
			// } else {
			if (conteggiow9inca != null && conteggiow9inca.longValue() != 0) {
				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "CO");
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella conteggio degli incarichi professionali", "validazione2", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione5: fine metodo");
		}
	}

	/**
	 * Controllo dati per la sospensione (codice 6).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione6(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione6: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Sospensione contratto");
		}
		
		String nomeTabella = "W9SOSP";
		String pagina = "Sospensione dell\'esecuzione";

		try {
			String selectW9SOSP = "select " + " num_sosp, " // 0
					+ " data_verb_sosp, " // 1
					+ " data_verb_ripr, " // 2
					+ " id_motivo_sosp, " // 3
					+ " flag_supero_tempo, " // 4
					+ " flag_riserve, " // 5
					+ " flag_verbale, " // 6
					+ " num_appa " // 7
					+ " from w9sosp where codgara = ? and codlott = ? and num_sosp = ?";

			List<?> datiW9SOSP = this.sqlManager.getVector(selectW9SOSP, new Object[] { codgara, codlott, num });

			if (datiW9SOSP != null && datiW9SOSP.size() > 0) {
				
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9SOSP, 7).getValue();
				Date dataVerbInizio = (Date) this.sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where CODGARA=? and CODLOTT=? and NUM_APPA=?", new Object[] { codgara, codlott, numappa });
				// Numero sospensione
				if (SqlManager.getValueFromVectorParam(datiW9SOSP, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NUM_SOSP", pagina);
				}

				// Data del verbale di sospensione
				Date dataVerbSosp = (Date) SqlManager.getValueFromVectorParam(datiW9SOSP, 1).getValue();
				if (dataVerbSosp == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_SOSP", pagina);
				}
				if (dataVerbSosp != null && dataVerbInizio != null) {
					if (dataVerbSosp.getTime() <= dataVerbInizio.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataVerbSosp, ">", dataVerbInizio, "W9INIZ", "DATA_VERB_INIZIO", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_SOSP", "E", pagina, messaggio);
					}
				}

				// Data del verbale di ripresa
				Date dataVerbRipresa = (Date) SqlManager.getValueFromVectorParam(datiW9SOSP, 2).getValue();
				if (dataVerbRipresa != null && dataVerbSosp != null) {
					if (dataVerbRipresa.getTime() <= dataVerbSosp.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataVerbRipresa, ">", dataVerbSosp, "W9SOSP", "DATA_VERB_SOSP", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_RIPR", "E", pagina, messaggio);
					}
				}

				// Motivazione della sospensione
				if (SqlManager.getValueFromVectorParam(datiW9SOSP, 3).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_SOSP", pagina);
				}

				// E' stato superato il quarto del tempo contrattuale ?
				if (SqlManager.getValueFromVectorParam(datiW9SOSP, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SUPERO_TEMPO", pagina);
				}

				// Iscrizione di riserve ?
				if (SqlManager.getValueFromVectorParam(datiW9SOSP, 5).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVE", pagina);
				}

				// Verbale non sottoscritto dall'appaltatore
				if (SqlManager.getValueFromVectorParam(datiW9SOSP, 6).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_VERBALE", pagina);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla sospensione", "validazione6", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione6: fine metodo");
		}
	}

	/**
	 * Controllo dati per la variante (codice 7).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione7(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione7: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Modifica contrattuale");
		}

		String nomeTabella = "W9VARI";
		String pagina = "";

		try {
			String selectW9VARI = "select " 
				+ " data_verb_appr, "       // 0
				+ " altre_motivazioni, "    // 1
				+ " imp_ridet_lavori, "     // 2
				+ " imp_ridet_servizi, "    // 3
				+ " imp_ridet_fornit, "     // 4
				+ " imp_sicurezza, "        // 5
				+ " imp_progettazione, "    // 6
				+ " imp_disposizione, "     // 7
				+ " data_atto_aggiuntivo, " // 8
				+ " IMP_NON_ASSOG,"         // 9
				+ " CIG_NUOVA_PROC, "        //10
				+ " NUM_APPA, "        		//11
				+ " NUM_GIORNI_PROROGA " 	//12
				+ " from w9vari where codgara = ? and codlott = ? and num_vari = ?";

			List<?> datiW9VARI = this.sqlManager.getVector(selectW9VARI, new Object[] { codgara, codlott, num });

			if (datiW9VARI != null && datiW9VARI.size() > 0) {
				pagina = "Modifica contrattuale";
				//NUM_APPA
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9VARI, 11).getValue();
				// Data approvazione variante
				Date dataVerbApprVariante = (Date) SqlManager.getValueFromVectorParam(datiW9VARI, 0).getValue();
				if (dataVerbApprVariante == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_APPR", pagina);
				} else {
					// Data effettivo inizio lavori
					Date dataVerbInizio = (Date) this.sqlManager.getObject("select DATA_VERB_INIZIO from w9iniz where codgara=? and codlott=? and num_appa=?", new Object[] { codgara, codlott, numappa });

					if (dataVerbInizio != null) {
						if (dataVerbApprVariante.getTime() <= dataVerbInizio.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataVerbApprVariante, ">", dataVerbInizio, "W9INIZ", "DATA_VERB_INIZIO", null);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_APPR", "W", pagina, messaggio);
						}
					}
				}
				// Motivazioni
				pagina = "Motivazioni della modifica";
				// Deve eeserci almeno una motivazione indicata verifico che ci sia almeno una riga in W9MOTI
				Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9moti where codgara = ? and codlott = ? and num_vari = ?", new Object[] { codgara, codlott, num });
				if (conteggio == null || (conteggio != null && conteggio.longValue() == 0)) {
					String messaggio = "Indicare almeno una motivazione che ha determinato l\'insorgere di una modifica contrattuale";
					listaControlli.add(((new Object[] {  "E" , pagina, "Motivazioni della modifica contrattuale", messaggio })));
				}

				String altreMotivazioni = SqlManager.getValueFromVectorParam(datiW9VARI, 1).getStringValue();
				if (StringUtils.isEmpty(altreMotivazioni)) {
					// Se altre motivazioni e' vuoto controllo se c'è una riga in W9MOTI con id_motivo_var = 8 (Altre cause impreviste ed imprevedibili)
					// se esiste l'errore è bloccante
					Long conteggioMotivazione8 = (Long) this.sqlManager.getObject("select count(*) from w9moti where codgara = ? and codlott = ? and num_vari = ? and id_motivo_var = ?", new Object[] { codgara, codlott, num, new Long(8) });
					if (conteggioMotivazione8 != null && conteggioMotivazione8.longValue() > 0) {
						String messaggio = "Indicare in 'Altre motivazioni' la causa che ha determinato l\'insorgere di una modifica contrattuale";
						this.addAvviso(listaControlli, nomeTabella, "ALTRE_MOTIVAZIONI", "E", pagina, messaggio);
					}
				}

				// Data atto aggiuntivo
				pagina = "Atti aggiuntivi / sottomissione";
				Date dataAttoAggiuntivo = (Date) SqlManager.getValueFromVectorParam(datiW9VARI, 8).getValue();
				Date dataEffettivoInizioLFS = (Date)
				this.sqlManager.getObject("select DATA_VERB_INIZIO from W9INIZ where CODGARA=? and CODLOTT=? and NUM_APPA = ?",
						new Object[] { codgara, codlott, numappa });
				if (dataAttoAggiuntivo != null && dataEffettivoInizioLFS != null) {
					if (dataAttoAggiuntivo.getTime() <= dataEffettivoInizioLFS.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataAttoAggiuntivo, ">", 
								dataEffettivoInizioLFS, "W9INIZ", "DATA_VERB_INIZIO", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_ATTO_AGGIUNTIVO", "W", pagina, messaggio);
					}
				}

				// Cig nuova procedura
				String cigNuovaProcedura = SqlManager.getValueFromVectorParam(datiW9VARI, 10).getStringValue();
				Long conteggioMotivazione18 = (Long) this.sqlManager.getObject(
						"select count(*) from W9MOTI where CODGARA=? and CODLOTT=? and NUM_VARI=? and ID_MOTIVO_VAR=18",
						new Object[] { codgara, codlott, num });
				if ((conteggioMotivazione18 != null && conteggioMotivazione18.longValue() == 1) ) {
					if (StringUtils.isEmpty(altreMotivazioni) && StringUtils.isEmpty(cigNuovaProcedura)) {
						this.addAvviso(listaControlli, nomeTabella, "CIG_NUOVA_PROC", "E", "Motivazioni della modifica", 
								"In caso di proroga tecnica e' necessario indicare il CIG della nuova procedura avviata ovvero "
								+ "le motivazioni della proroga nel campo 'Altre motivazioni'");
					} else if (!StringUtils.isEmpty(cigNuovaProcedura)){
						if (!UtilitySITAT.isCigValido(cigNuovaProcedura) && !UtilitySITAT.isSmartCigValido(cigNuovaProcedura)) {
							//TODO UtilitySITAT.isSmartCigValido(cigNuovaProcedura);
							this.addAvviso(listaControlli, nomeTabella, "CIG_NUOVA_PROC", "E", "Motivazioni della modifica",
							"Il CIG indicato per la nuova procedura non e' valido");
						}
					}
					Long numGiorniProroga = (Long) SqlManager.getValueFromVectorParam(datiW9VARI, 12).getValue();
					if (numGiorniProroga == null || numGiorniProroga <= 0) {
						this.addAvviso(listaControlli, nomeTabella, "NUM_GIORNI_PROROGA", "E", pagina,
						"Il valore indicato deve essere maggiore di 0");
					}
				}
				// Importi lavori, servizi e forniture
				pagina = "Quadro economico della modifica contrattuale";
				Double impRidetLavori = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 2).getValue();
				Double impRidetServizi = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 3).getValue();
				Double impRidetFornit = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 4).getValue();

				if (impRidetLavori == null || (impRidetLavori != null && new Double(0).equals(impRidetLavori))) {
					if (impRidetServizi == null || (impRidetServizi != null && new Double(0).equals(impRidetServizi))) {
						if (impRidetFornit == null || (impRidetFornit != null && new Double(0).equals(impRidetFornit))) {

							String descrizione = this.getDescrizioneCampo(nomeTabella, "IMP_RIDET_LAVORI");
							descrizione = descrizione + ", " + this.getDescrizioneCampo(nomeTabella, "IMP_RIDET_SERVIZI");
							descrizione = descrizione + ", " + this.getDescrizioneCampo(nomeTabella, "IMP_RIDET_FORNIT");
							String messaggio = "L\'importo di almeno uno dei tre campi indicati deve essere > 0";

							listaControlli.add(((new Object[] { "E", pagina, descrizione, messaggio })));
						}
					}
				}

				// Importo sicurezza
				Double impSicurezza = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 5).getValue();
				if (impSicurezza != null && impSicurezza.doubleValue() <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_SICUREZZA", "W", pagina, 
					"Non &egrave; stato valorizzato l'importo della sicurezza");
				}

				// Importo progettazione
				Double impProgettazione = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 6).getValue();
				if (impProgettazione != null && impProgettazione.doubleValue() <= 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_PROGETTAZIONE", "W", pagina, 
					"Non &egrave; stato valorizzato l'importo della progettazione");
				}

				// Importo somme a disposizione
				/*Double impDisposizione = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 7).getValue();
				if (impDisposizione != null && impDisposizione.doubleValue() == 0) {
					String messaggio = "L\'importo digitato dovrebbe essere > 0";
					this.addAvviso(listaControlli, nomeTabella, "IMP_DISPOSIZIONE", "W", pagina, messaggio);
				}*/

				// Eventuali ulteriori somme non assoggettate al ribasso d'asta
				Double impNonAssog = (Double) SqlManager.getValueFromVectorParam(datiW9VARI, 9).getValue();
				if (impNonAssog != null && impNonAssog.doubleValue() == 0) {
					this.addAvviso(listaControlli, nomeTabella, "IMP_NON_ASSOG", "W", pagina, 
					"Non è stato valorizzato l'importo delle ulteriori somme non soggette a ribasso");
				}

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla modifica contrattuale", "validazione7", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione7: fine metodo");
		}
	}

	/**
	 * Controllo dati per l'accordo bonario (codice 8).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione8(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione8: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Accordo bonario");
		}
		
		String nomeTabella = "W9ACCO";
		String pagina = "Accordo bonario";

		try {
			String selectW9ACCO = "select " + " data_accordo, " // 0
					+ " oneri_derivanti, " // 1
					+ " NUM_APPA " // 2
					+ " from w9acco where codgara = ? and codlott = ? and num_acco = ?";

			List<?> datiW9ACCO = this.sqlManager.getVector(selectW9ACCO, new Object[] { codgara, codlott, num });

			if (datiW9ACCO != null && datiW9ACCO.size() > 0) {
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9ACCO, 2).getValue();
				Date dataStipula = this.getDataStipulaContratto(codgara, codlott, numappa);
				// Data dell'accordo bonario
				Date dataAccordo = (Date) SqlManager.getValueFromVectorParam(datiW9ACCO, 0).getValue();
				if (dataAccordo == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ACCORDO", pagina);
				}

				if (dataAccordo != null && dataStipula != null) {
					if (dataAccordo.getTime() <= dataStipula.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataAccordo, ">", dataStipula, "W9INIZ", "DATA_STIPULA", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_ACCORDO", "E", pagina, messaggio);
					}
				}

				// Oneri derivanti
				Double oneriDerivanti = (Double) SqlManager.getValueFromVectorParam(datiW9ACCO, 1).getValue();
				if (oneriDerivanti != null && oneriDerivanti.doubleValue() == 0) {
					String messaggio = "L\'importo digitato dovrebbe essere >0";
					this.addAvviso(listaControlli, nomeTabella, "ONERI_DERIVANTI", "W", pagina, messaggio);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'accordo bonario", "validazione8", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione8: fine metodo");
		}
	}

	/**
	 * Controllo dati per il subappalto (codice 9).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione9(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione9: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Subappalto");
		}
	
		String nomeTabella = "W9SUBA";
		String pagina = "Subappalto";
		String cigMaster = null;
		try {
			String selectW9SUBA = "select " 
					+ " data_autorizzazione, " // 0
					+ " importo_presunto, " // 1
					+ " importo_effettivo, " // 2
					+ " id_categoria, " // 3
					+ " id_cpv, " // 4
					+ " codimp, " // 5
					+ " codimp_aggiudicatrice, " // 6
					+ " NUM_APPA " // 7
					+ " from w9suba where codgara = ? and codlott = ? and num_suba = ?";
			cigMaster = this.getCIGMaster(codgara, codlott);
			List<?> datiW9SUBA = this.sqlManager.getVector(selectW9SUBA, new Object[] { codgara, codlott, num });

			if (datiW9SUBA != null && datiW9SUBA.size() > 0) {
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9SUBA, 7).getValue();
				
				Date dataStipula = this.getDataStipulaContratto(codgara, codlott, numappa);
				Date dataVerbAggiudicazione = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					dataVerbAggiudicazione = this.getDataAggiudicazioneInferiore(codgara, cigMaster, numappa);
				} else {
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Denominazione impresa aggiudicatrice
				String codimpAggiudicatrice = (String) SqlManager.getValueFromVectorParam(datiW9SUBA, 6).getValue();
				if (isStringaValorizzata(codimpAggiudicatrice)) {
					this.validazioneImpresa(listaControlli, "Impresa aggiudicatrice", codimpAggiudicatrice, false);

					Long numImpreseAgg = (Long) this.sqlManager.getObject("select count(*) from w9aggi where codgara=? and codlott=? and num_appa=? and codimp=?", new Object[] { codgara, codlott, numappa,
							codimpAggiudicatrice });

					if (numImpreseAgg.longValue() != 1) {
						this.addAvviso(listaControlli, nomeTabella, "CODIMP_AGGIUDICATRICE", "E", pagina,
								"L'impresa aggiudicatrice subappaltante non &egrave; fra le imprese aggiudicatarie dell'appalto");
					}
				}

				// Importo presunto
				//if (SqlManager.getValueFromVectorParam(datiW9SUBA, 1).getValue() == null) {
				//	this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_PRESUNTO", pagina);
				//}
				if (SqlManager.getValueFromVectorParam(datiW9SUBA, 1).getValue() == null || !((Double) SqlManager.getValueFromVectorParam(datiW9SUBA, 1).getValue() > 0)){
					this.addAvviso(listaControlli, nomeTabella, "IMPORTO_PRESUNTO", "E", pagina,
					"Importo presunto : Il campo e' obbligatorio, e deve avere un valore maggiore di 0");		
				}
				
				// Data autorizzazione subappalto
				Date dataAutorizzazione = (Date) SqlManager.getValueFromVectorParam(datiW9SUBA, 0).getValue();
				if (dataAutorizzazione != null) {
					if (dataStipula != null) {
						if (dataAutorizzazione.getTime() <= dataStipula.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataAutorizzazione, ">", dataStipula, "W9INIZ", "DATA_STIPULA", null);
							this.addAvviso(listaControlli, nomeTabella, "DATA_AUTORIZZAZIONE", "W", pagina, messaggio);
						}
					}

					if (dataVerbAggiudicazione != null) {
						if (dataAutorizzazione.getTime() < dataVerbAggiudicazione.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataAutorizzazione, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
									CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
							this.addAvviso(listaControlli, nomeTabella, "DATA_AUTORIZZAZIONE", "W", pagina, messaggio);
						}
					}
				}

				// Impresa subappaltatrice
				String codimp = (String) SqlManager.getValueFromVectorParam(datiW9SUBA, 5).getValue();
				if (codimp == null || (codimp != null && codimp.trim().length() == 0)) {
					listaControlli.add(((new Object[] { "E", pagina, "Denominazione dell\'impresa subappaltatrice", "Il campo &egrave; obbligatorio" })));
				} else {
					this.validazioneImpresa(listaControlli, "Impresa subappaltatrice", codimp, false);
				}

				// Categoria
				if (SqlManager.getValueFromVectorParam(datiW9SUBA, 3).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA", pagina);
				}

				// Codice CPV
				if (SqlManager.getValueFromVectorParam(datiW9SUBA, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CPV", pagina);
				} else {
				  if (!this.validazioneCodiceCPV(SqlManager.getValueFromVectorParam(datiW9SUBA, 4).getStringValue())) {
				    listaControlli.add(((new Object[] { "E", pagina, "Codice CPV", "Codice CPV non valido" })));
				  }
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al subappalto", "validazione9", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione9: fine metodo");
		}
	}

	/**
	 * Validazione dei dati del flusso 'Stipula accordo quadro' (codice 11 <=>
	 * A20).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione11(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione11: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Stipula accordo quadro");
		}

		String nomeTabella = "";
		String pagina = "";
		Long numappa = null;
		String cigMaster = null;
		try {
			nomeTabella = "W9INIZ";
			cigMaster = this.getCIGMaster(codgara, codlott);
			String selectW9INIZ = "select " + " data_stipula, " // 0
					+ " data_scadenza, " // 1
					+ " NUM_APPA " // 2
					+ " from w9iniz where codgara = ? and codlott = ? and num_iniz = ?";

			List<?> datiW9INIZ = this.sqlManager.getVector(selectW9INIZ, new Object[] { codgara, codlott, num });
			if (datiW9INIZ != null && datiW9INIZ.size() > 0) {
				pagina = "Dati generali";
				numappa = (Long) SqlManager.getValueFromVectorParam(datiW9INIZ, 2).getValue();
				Date dataVerbAggiudicazione = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					dataVerbAggiudicazione = this.getDataAggiudicazioneSuperiore(codgara, cigMaster, numappa);
				} else {
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Data_stipula contratto
				Date dataStipula = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 0).getValue();
				Date dataScadenza = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 1).getValue();
				if (dataStipula != null && dataVerbAggiudicazione != null) {
					if (dataStipula.getTime() < dataVerbAggiudicazione.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataStipula, ">=", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
								CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, messaggio);
					}
				}

				if (dataStipula != null && dataScadenza != null) {
					if (dataStipula.getTime() >= dataScadenza.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataStipula, "<", dataScadenza, "W9INIZ", "DATA_SCADENZA", null);
						this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, messaggio);
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase stipula accordo quadro", "validazione11", e);
		}

		try {
			nomeTabella = "W9PUES";
			pagina = "Pubblicazione esito di gara";

			String selectW9PUES = "select " + " data_guce, " // 0
					+ " data_guri, " // 1
					+ " quotidiani_naz, " // 2
					+ " quotidiani_reg, " // 3
					+ " PROFILO_COMMITTENTE, " // 4
					+ " SITO_MINISTERO_INF_TRASP, " // 5
					+ " SITO_OSSERVATORIO_CP " // 6
					+ " from w9pues where codgara = ? and codlott = ? and num_iniz = ?";

			List<?> datiW9PUES = this.sqlManager.getVector(selectW9PUES, new Object[] { codgara, codlott, num });

			if (datiW9PUES != null && datiW9PUES.size() > 0) {
				Date dataVerbAggiudicazione = null;
				if (cigMaster != null && !cigMaster.equals("")) {
					dataVerbAggiudicazione = this.getDataAggiudicazioneInferiore(codgara, cigMaster, numappa);
				} else {
					dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
				}
				// Data_guce
				Date dataGUCE = (Date) SqlManager.getValueFromVectorParam(datiW9PUES, 0).getValue();
				if (dataGUCE != null && dataVerbAggiudicazione != null) {
					if (dataGUCE.getTime() <= dataVerbAggiudicazione.getTime()) {
						String messaggio = this
								.getMessaggioConfrontoDate(dataGUCE, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_GUCE", "E", pagina, messaggio);
					}
				}

				// Data_guri
				Date dataGURI = (Date) SqlManager.getValueFromVectorParam(datiW9PUES, 1).getValue();
				if (dataGURI != null && dataVerbAggiudicazione != null) {
					if (dataGURI.getTime() <= dataVerbAggiudicazione.getTime()) {
						String messaggio = this
								.getMessaggioConfrontoDate(dataGURI, ">", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA));
						this.addAvviso(listaControlli, nomeTabella, "DATA_GURI", "E", pagina, messaggio);
					}
				}

				// Quotidiani_naz
				Long quotidianiNaz = (Long) SqlManager.getValueFromVectorParam(datiW9PUES, 2).getValue();
				if (quotidianiNaz != null && quotidianiNaz.longValue() > 20) {
					String messaggio = "Il valore digitato (" + quotidianiNaz.toString() + ") &egrave; maggiore di 20: verificare il valore";
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_NAZ", "W", pagina, messaggio);
				}

				// Quotidiani_reg
				Long quotidianiReg = (Long) SqlManager.getValueFromVectorParam(datiW9PUES, 3).getValue();
				if (quotidianiReg != null && quotidianiReg.longValue() > 20) {
					String messaggio = "Il valore digitato (" + quotidianiReg.toString() + ") &egrave; maggiore di 20: verificare il valore";
					this.addAvviso(listaControlli, nomeTabella, "QUOTIDIANI_REG", "W", pagina, messaggio);
				}

				// Profilo committente
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PROFILO_COMMITTENTE", pagina);
				}
				// Sito Informatico Ministero Infrastrutture
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 5).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_MINISTERO_INF_TRASP", pagina);
				}
				// Sito Informatico Osservatorio Contratti Pubblici
				if (SqlManager.getValueFromVectorParam(datiW9PUES, 6).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "SITO_OSSERVATORIO_CP", pagina);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase adesione accordo quadro", "validazione11", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione11: fine metodo");
		}
	}

	/**
	 * Validazione dei dati del flusso 'Adesione accordo quadro' (codice 12 <=>
	 * A21).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione12(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione12: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Adesione accordo quadro");
		}
		
		String nomeTabella = null;
		String pagina = null;

		// Prima parte: Validazione dati adesione
		try {
			nomeTabella = "W9APPA";
			String selectW9APPA = "select " 
			    + " COD_STRUMENTO, "     // 0
					+ " IMPORTO_LAVORI, "    // 1
					+ " IMPORTO_SERVIZI, "   // 2
					+ " IMPORTO_FORNITURE, " // 3
					+ " DATA_VERB_AGGIUDICAZIONE, " // 4
					+ " IMPORTO_AGGIUDICAZIONE, "   // 5
					+ " IMPORTO_SUBTOTALE, " // 6
					+ " PERC_RIBASSO_AGG, "  // 7
					+ " PERC_OFF_AUMENTO, "  // 8
					+ " FLAG_RICH_SUBAPPALTO, " // 9
					+ " IMP_NON_ASSOG, "     // 10
					+ " IMPORTO_PROGETTAZIONE, " // 11
					+ " IMPORTO_ATTUAZIONE_SICUREZZA " // 12
					+ " from W9APPA where CODGARA=? and CODLOTT=? and NUM_APPA=?";

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, num });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				pagina = "Aggiudicazione - affidamento";

				Double percRibassoAgg = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue();
				Double percOffAumento = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue();

				if (percRibassoAgg != null && (percRibassoAgg.doubleValue() < 0 || percRibassoAgg.doubleValue() >= 100)) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", "W", pagina, "Il valore percentuale deve essere &ge; 0 e &lt; 100");
				}
				if (percOffAumento != null && (percOffAumento.doubleValue() < 0 || percOffAumento.doubleValue() >= 100)) {
					this.addAvviso(listaControlli, nomeTabella, "PERC_OFF_AUMENTO", "W", pagina, "Il valore percentuale deve essere &ge; 0 e &lt; 100");
				}
				if ((percOffAumento != null && percRibassoAgg != null) || (percOffAumento == null && percRibassoAgg == null)) {
					String descrizione = this.getDescrizioneCampo(nomeTabella, "PERC_RIBASSO_AGG").concat(", ").concat(this.getDescrizioneCampo(nomeTabella, "PERC_OFF_AUMENTO"));

					String messaggio = "Valorizzare solo uno dei campi proposti";
					listaControlli.add(((new Object[] { "E", pagina, descrizione, messaggio })));
				}

				// Importo di aggiudicazione
				Double importoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 5).getValue();
				if (importoAggiudicazione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", pagina);
				}

				// Importo Lavori
				double importoLavori = 0;
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue() != null) {
					importoLavori = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue()).doubleValue();
				}
				// Importo servizi
				double importoServizi = 0;
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue() != null) {
					importoServizi = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue()).doubleValue();
				}
				// Importo forniture
				double importoForniture = 0;
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue() != null) {
					importoForniture = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue()).doubleValue();
				}

				if (importoLavori == 0 && importoServizi == 0 && importoForniture == 0) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_LAVORI"),
							"Valorizzare almeno un campo riferito a importi lavori, servizi, forniture" })));
				}

				// *** Controllo congruenza dell'importo di aggiudicazione ***
				if (importoAggiudicazione != null) {
					double importoAttuazioneSicurezza = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 12).getValue() != null) {
						importoAttuazioneSicurezza = SqlManager.getValueFromVectorParam(datiW9APPA, 12).doubleValue().doubleValue();
					}

					double importoNonAssog = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 10).getValue() != null) {
						importoNonAssog = SqlManager.getValueFromVectorParam(datiW9APPA, 10).doubleValue().doubleValue();
					}

					double importoProgettazione = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue() != null) {
						importoProgettazione = SqlManager.getValueFromVectorParam(datiW9APPA, 11).doubleValue().doubleValue();
					}

					double importoAggiudicazioneCalcolato = 0;

					importoAggiudicazioneCalcolato = importoNonAssog + importoLavori + importoServizi + importoForniture + importoProgettazione + importoAttuazioneSicurezza;

					importoAggiudicazioneCalcolato = UtilityMath.round(importoAggiudicazioneCalcolato, 2);

					if (importoAggiudicazione.doubleValue() > importoAggiudicazioneCalcolato) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "W", pagina, "Verificare il valore dell'importo totale di aggiudicazione");
					}

					double importoSubtotale = 0;
					if (SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue() != null) {
						importoSubtotale = ((Double) SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue()).doubleValue();
					}

					double impAggiudicazione = 0;
					double percRibAgg = 0;
					double percOffAum = 0;
					double impSubTot = 0;
					double impAttSic = 0;

					if (importoAggiudicazione != null) {
						impAggiudicazione = importoAggiudicazione.doubleValue();
					}
					if (percRibassoAgg != null) {
						percRibAgg = percRibassoAgg.doubleValue();
					}
					if (percOffAumento != null) {
						percOffAum = percOffAumento.doubleValue();
					}

					impSubTot = importoSubtotale;
					impAttSic = importoAttuazioneSicurezza;

					if (Math.abs(UtilityMath.round((impAggiudicazione - (impSubTot * ((100 - percRibAgg + percOffAum) / 100) + impAttSic)), 2)) > 0.01) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "W", pagina, "L'importo di aggiudicazione non coincide con l'importo calcolato");
					}
				}

				// Data di aggiudicazione definitiva
				Date dataVerbAggiudicazione = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 4).getValue();
				if (dataVerbAggiudicazione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
				} else {
					GregorianCalendar dataMinimaCal = new GregorianCalendar(2008, 0, 1);
					// valore precedente era 01/01/2000
					Date dataMinima = dataMinimaCal.getTime();
					Date dataOdierna = new Date();
					if (dataVerbAggiudicazione != null && dataOdierna != null && dataMinima != null) {
						if (dataVerbAggiudicazione.getTime() > dataOdierna.getTime() || dataVerbAggiudicazione.getTime() < dataMinima.getTime()) {
							String messaggio = "La data digitata (" + UtilityDate.convertiData(dataVerbAggiudicazione, UtilityDate.FORMATO_GG_MM_AAAA) + ") deve essere compresa tra il "
									+ UtilityDate.convertiData(dataMinima, UtilityDate.FORMATO_GG_MM_AAAA) + " ed il " + UtilityDate.convertiData(dataOdierna, UtilityDate.FORMATO_GG_MM_AAAA);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, messaggio);
						}
					}
				}

				// L'affidatario ha richiesto di subappaltare
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RICH_SUBAPPALTO", pagina);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase adesione accordo quadro (seconda parte)", "validazione12", e);
		}

		// Terza parte: validazione dati imprese aggiudicatarie/affidatarie
		try {
			Long conteggiow9aggi = (Long) this.sqlManager.getObject("select count(*) from w9aggi where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara, codlott, num });
			if (conteggiow9aggi == null || (conteggiow9aggi != null && conteggiow9aggi.longValue() == 0)) {
				this.setTitolo(listaControlli, "Imprese aggiudicatarie / affidatarie");
				listaControlli.add(((new Object[] { "E", "Imprese aggiudicatarie / affidatarie", "Imprese aggiudicatarie / affidatarie", "Indicare almeno una impresa aggiudicataria" })));
			} else {
				this.validazioneImpreseAggiudicatarie(codgara, codlott, num, listaControlli);
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella conteggio delle imprese aggiudicatarie/aggiudicatarie della adesione all'accordo quadro (terza parte)", "validazione12", e);
		}
		
		// Quarta parte: validazione incarichi professionali
		this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "RQ");

    // Quinta parte: validazione finanziamenti
		this.validazioneFinanziamenti(codgara, codlott, num, listaControlli, true);

		if (logger.isDebugEnabled()) {
			logger.debug("validazione12: fine metodo");
		}
	}

	/**
	 * Controllo dati per l'istanza di recesso (codice 10).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione10(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione10: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Istanza recesso");
		}

		String nomeTabella = "W9RITA";
		String pagina = "Istanza di recesso";

		try {

			String selectW9RITA = "select " 
			    + " data_ist_recesso, " // 0
					+ " FLAG_ACCOLTA, " // 1
					+ " DATA_TERMINE, " // 2
					+ " DATA_CONSEGNA, " // 3
					+ " DURATA_SOSP, " // 4
					+ " FLAG_TARDIVA, " // 5
					+ " FLAG_RIPRESA, " // 6
					+ " FLAG_RISERVA, " // 7
					+ " TIPO_COMUN " // 8
					+ " from w9rita where codgara = ? and codlott = ? and num_rita = ?";

			List<?> datiW9RITA = this.sqlManager.getVector(selectW9RITA, new Object[] { codgara, codlott, num });

			if (datiW9RITA != null && datiW9RITA.size() > 0) {

				// Termine previsto per la consegna
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 2).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_TERMINE", pagina);
				}
				// Data della consegna dei lavori
				Date dataConsegnaLavori = (Date) SqlManager.getValueFromVectorParam(datiW9RITA, 3).getValue();
				if (dataConsegnaLavori != null) {
					GregorianCalendar dataOdierna = new GregorianCalendar();
					if (dataConsegnaLavori.getYear() > dataOdierna.get(Calendar.YEAR)) {
						this.addAvviso(listaControlli, nomeTabella, "DATA_CONSEGNA", "W", pagina, 
						    "L'anno di consegna lavori deve essere minore o uguale all'anno corrente");
					}
				}
				// Durata della sospensione in giorni
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_SOSP", pagina);
				}
				// Data presentazione istanza recesso
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_IST_RECESSO", pagina);
				}
				// Si e' proceduto a cosegna tardiva?
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 5).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_TARDIVA", pagina);
				}
				// Si e' proceduto alla ripresa dei lavori?
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 6).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RIPRESA", pagina);
				}
				// L'appaltatore ha formulato riserve?
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 7).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVA", pagina);
				}
				// Tipo di comunicazione
				if (SqlManager.getValueFromVectorParam(datiW9RITA, 8).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_COMUN", pagina);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all\'istanza di recesso", "validazione10", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione10: fine metodo");
		}
	}

	/**
	 * Ritorna il campo W9APPA.DATA_VERB_AGGIUDICAZIONE a partire da codice gara
	 * e codice lotto. Il campo W9APPA.NUM_APPA e' di default pari a 1.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param numappa
	 *            progressivo appalto
	 * @return Ritorna il campo W9APPA.DATA_VERB_AGGIUDICAZIONE
	 * @throws SQLException
	 *             SQLException
	 */
	private Date getDataVerbaleAggiudicazione(final Long codgara, final Long codlott, final Long numappa) throws SQLException {
		return (Date) this.sqlManager.getObject("select data_verb_aggiudicazione from w9appa where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara, codlott, numappa });
	}

	/**
	 * Ritorna la data aggiudicazione inferiore (DAI) il campo W9APPA.DATA_VERB_AGGIUDICAZIONE 
	 * data minore tra i valori presenti nei lotti accorpat a partire da codice gara
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param cig
	 *            Codice cig master
	 * @param numappa
	 *            progressivo appalto
	 * @return Ritorna la data aggiudicazione inferiore (DAI)
	 * @throws SQLException
	 *             SQLException
	 */
	private Date getDataAggiudicazioneInferiore(final Long codgara, final String cig, final Long numappa) throws SQLException {
		return (Date) this.sqlManager.getObject("select min(data_verb_aggiudicazione) from w9appa left join w9lott on w9appa.codgara=w9lott.codgara and w9appa.codlott=w9lott.codlott where w9lott.codgara = ? and w9lott.CIG_MASTER_ML = ? and w9appa.num_appa = ?", new Object[] { codgara, cig, numappa });
	}
	/**
	 * Ritorna la data aggiudicazione superiore (DAS) il campo W9APPA.DATA_VERB_AGGIUDICAZIONE 
	 * data maggiore tra i valori presenti nei lotti accorpat a partire da codice gara
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param cig
	 *            Codice cig master
	 * @param numappa
	 *            progressivo appalto
	 * @return Ritorna la data aggiudicazione superiore (DAS)
	 * @throws SQLException
	 *             SQLException
	 */
	private Date getDataAggiudicazioneSuperiore(final Long codgara, final String cig, final Long numappa) throws SQLException {
		return (Date) this.sqlManager.getObject("select max(data_verb_aggiudicazione) from w9appa left join w9lott on w9appa.codgara=w9lott.codgara and w9appa.codlott=w9lott.codlott where w9lott.codgara = ? and w9lott.CIG_MASTER_ML = ? and w9appa.num_appa = ?", new Object[] { codgara, cig, numappa });
	}
	/**
	 * Ritorna il campo W9INIZ.DATA_STIPULA a partire da codice gara e codice
	 * lotto.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param numappa
	 *            Progressivo appalto
	 * @return Ritorna il campo W9INIZ.DATA_STIPULA
	 * @throws SQLException
	 *             SQLException
	 */
	private Date getDataStipulaContratto(final Long codgara, final Long codlott, final Long numappa) throws SQLException {
		return (Date) this.sqlManager.getObject("select data_stipula from w9iniz where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara, codlott, numappa });
	}

	/**
	 * Ritorna importo complessivo appalto multilotto (ICA) il campo W9APPA.importo_compl_appalto 
	 * somma degli importi totali dei singoli lotti
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param cig
	 *            Codice cig master
	 * @param numappa
	 *            progressivo appalto
	 * @return Ritorna importo complessivo appalto multilotto (ICA)
	 * @throws SQLException
	 *             SQLException
	 */
	private Double getImportoComplessivoAppaltoMultilotto(final Long codgara, final String cig, final Long numappa) throws SQLException {
		return (Double) this.sqlManager.getObject("select sum(importo_compl_appalto) from w9appa left join w9lott on w9appa.codgara=w9lott.codgara and w9appa.codlott=w9lott.codlott where w9lott.codgara = ? and w9lott.CIG_MASTER_ML = ? and w9appa.num_appa = ?", new Object[] { codgara, cig, numappa });
	}
	
	/**
	 * Ritorna importo complessivo intervento multilotto (ICI) il campo W9APPA.importo_compl_appalto 
	 * somma degli importi intervento dei singoli lotti
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param cig
	 *            Codice cig master
	 * @param numappa
	 *            progressivo appalto
	 * @return Ritorna importo complessivo intervento multilotto (ICI)
	 * @throws SQLException
	 *             SQLException
	 */
	private Double getImportoComplessivoInterventoMultilotto(final Long codgara, final String cig, final Long numappa) throws SQLException {
		return (Double) this.sqlManager.getObject("select sum(IMPORTO_COMPL_INTERVENTO) from w9appa left join w9lott on w9appa.codgara=w9lott.codgara and w9appa.codlott=w9lott.codlott where w9lott.codgara = ? and w9lott.CIG_MASTER_ML = ? and w9appa.num_appa = ?", new Object[] { codgara, cig, numappa });
	}
	
	/**
	 * Controllo dati per la fase di conclusione del contratto per appalti di
	 * importo inferiore a 150.000 euro (codice 985).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param num
	 *            Progressivo del flusso
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione985(final Long codgara, final Long codlott, final Long num, final List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione985: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Conclusione contratto");
		}

		String nomeTabella = "W9CONC";
		String pagina = "";

		try {
			String selectW9CONC = "select " + " intantic, id_motivo_interr, id_motivo_risol, " // 0, 1, 2
					+ " data_risoluzione, flag_oneri, oneri_risoluzione, " // 3, 4, 5
					+ " flag_polizza, data_ultimazione, " // 6, 7
					+ " NUM_APPA " // 8
					+ " from w9conc where codgara = ? and codlott = ? and num_conc = ?";

			List<?> datiW9CONC = this.sqlManager.getVector(selectW9CONC, new Object[] { codgara, codlott, num });

			if (datiW9CONC != null && datiW9CONC.size() > 0) {
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 8).getValue();
				Date dataStipula = this.getDataStipulaContratto(codgara, codlott, numappa);
				pagina = "Interruzione anticipata del procedimento";

				// Interruzione anticipata del contratto
				String intantic = (String) SqlManager.getValueFromVectorParam(datiW9CONC, 0).getValue();
				if (intantic == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "INTANTIC", pagina);
				}

				if (intantic != null && "1".equals(intantic)) {
					// Causa dell'interruzione anticipata
					Long idMotivoInterr = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 1).getValue();
					if (idMotivoInterr == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_INTERR", pagina);
					}
					// Motivazione della risoluzione
					Long idMotivoRisol = (Long) SqlManager.getValueFromVectorParam(datiW9CONC, 2).getValue();
					if (idMotivoInterr != null && idMotivoInterr.intValue() == 2) {
						if (idMotivoRisol == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MOTIVO_RISOL", pagina);
						}
					}

					// Data risoluzione
					Date dataRisoluzione = (Date) SqlManager.getValueFromVectorParam(datiW9CONC, 3).getValue();
					if (idMotivoInterr != null && idMotivoInterr.intValue() == 2) {
						if (dataRisoluzione == null) {
							this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_RISOLUZIONE", pagina);
						}
						if (dataRisoluzione != null && dataStipula != null) {
							if (dataRisoluzione.getTime() <= dataStipula.getTime()) {
								String messaggio = this.getMessaggioConfrontoDate(dataRisoluzione, ">", dataStipula, "W9INIZ", "DATA_STIPULA", new Long(CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO));
								this.addAvviso(listaControlli, nomeTabella, "DATA_RISOLUZIONE", "E", pagina, messaggio);
							}
						}
					}

					// Oneri economici derivanti da risoluzione / recesso
					String flagOneri = (String) SqlManager.getValueFromVectorParam(datiW9CONC, 4).getValue();
					if (idMotivoInterr != null) {
						switch (idMotivoInterr.intValue()) {
						case 2:
						case 4:
						case 5:
							if (flagOneri == null) {
								this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ONERI", pagina);
							}
							break;

						default:
							break;
						}
					}

					// Importo degli oneri
					Double oneriRisoluzione = (Double) SqlManager.getValueFromVectorParam(datiW9CONC, 5).getValue();

					if (flagOneri != null && !"0".equals(flagOneri) && oneriRisoluzione == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", pagina);
					}

					Double importoComplIntervento = (Double) this.sqlManager.getObject("select importo_compl_intervento from w9appa where codgara = ? and codlott = ? and num_appa = ?",
							new Object[] { codgara, codlott, numappa });
					if (oneriRisoluzione != null && importoComplIntervento != null) {
						if (oneriRisoluzione.doubleValue() >= importoComplIntervento.doubleValue()) {
							String messaggio = this.getMessaggioControntoImporti(oneriRisoluzione, "<", importoComplIntervento, "W9APPA", "IMPORTO_COMPL_INTERVENTO", new Long(
									CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE));
							this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, messaggio);
						}
					}

					if (flagOneri != null && oneriRisoluzione != null) {
						if (!"0".equals(flagOneri) && oneriRisoluzione.doubleValue() == 0) {
							String messaggio = "Il valore \"0\" &egrave; ammesso solo se al campo \"" + this.getDescrizioneCampo("W9CONC", "FLAG_ONERI") + "\""
									+ " &egrave; stato selezionato il valore \"Senza Oneri\"";
							this.addAvviso(listaControlli, nomeTabella, "ONERI_RISOLUZIONE", "E", pagina, messaggio);
						}
					}

					// Flag polizza
					if (SqlManager.getValueFromVectorParam(datiW9CONC, 6).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_POLIZZA", pagina);
					}
				}

				if (UtilitySITAT.existsFase(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, sqlManager)
				    || UtilitySITAT.existsFase(codgara, codlott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, sqlManager)) {

  				pagina = "Ultimazione lavori";
  
  				// Data ultimazione
  				Date dataUltimazione = (Date) SqlManager.getValueFromVectorParam(datiW9CONC, 7).getValue();
  				if (dataUltimazione == null) {
  					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", pagina);
  				} else {
  					Date dataVerbAgg = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);
  					if (dataVerbAgg != null) {
  						if (dataUltimazione.getTime() <= dataVerbAgg.getTime()) {
  							String messaggio = this.getMessaggioConfrontoDate(dataUltimazione, ">", dataVerbAgg, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
  									CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE));
  							this.addAvviso(listaControlli, nomeTabella, "DATA_ULTIMAZIONE", "E", pagina, messaggio);
  						}
  					}
  				}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase di conclusione del contratto", "validazione985", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione985: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase iniziale di esecuzione del contratto per
	 * appalti di importo inferiore a 150.000 euro (codice 986).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione986(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione986: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Inizio esecuzione contratto");
		}

		String nomeTabella = "W9INIZ";
		String pagina = "";

		try {

			String selectW9INIZ = "select " + " data_stipula, " // 0
					+ " flag_riserva, " // 1
					+ " data_verb_inizio, " // 2
					+ " data_termine, " // 3
					+ " NUM_APPA " // 4
					+ " from w9iniz where codgara = ? and codlott = ? and num_iniz = ?";

			List<?> datiW9INIZ = this.sqlManager.getVector(selectW9INIZ, new Object[] { codgara, codlott, num });

			if (datiW9INIZ != null && datiW9INIZ.size() > 0) {

				pagina = "Contratto di appalto";
				Long numappa = (Long) SqlManager.getValueFromVectorParam(datiW9INIZ, 4).getValue();
				Date dataVerbAggiudicazione = this.getDataVerbaleAggiudicazione(codgara, codlott, numappa);

				// Data_stipula contratto
				Date dataStipula = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 0).getValue();
				if (dataStipula != null && dataVerbAggiudicazione != null) {
					if (dataStipula.getTime() < dataVerbAggiudicazione.getTime()) {
						String messaggio = this.getMessaggioConfrontoDate(dataStipula, ">=", dataVerbAggiudicazione, "W9APPA", "DATA_VERB_AGGIUDICAZIONE", new Long(
								CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE));
						this.addAvviso(listaControlli, nomeTabella, "DATA_STIPULA", "E", pagina, messaggio);
					}
				}

				pagina = "Termine di esecuzione";

				// Consegna sotto riserva di legge ?
				String flagRiserva = (String) SqlManager.getValueFromVectorParam(datiW9INIZ, 1).getValue();
				if (flagRiserva == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_RISERVA", pagina);
				}

				// Data effettivo inizio lavori
				Date dataVerbInizio = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 2).getValue();
				if (dataVerbInizio == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_INIZIO", pagina);
				}

				if (dataVerbInizio != null) {
					String nomeCampo = "DATA_VERB_INIZIO";
					if (flagRiserva != null && "2".equals(flagRiserva)) {
						if (dataStipula != null) {
							if (dataVerbInizio.getTime() < dataStipula.getTime()) {
								String messaggio = this.getMessaggioConfrontoDate(dataVerbInizio, ">=", dataStipula, "W9INIZ", "DATA_STIPULA", null);
								this.addAvviso(listaControlli, nomeTabella, nomeCampo, "E", pagina, messaggio);
							}
						}
					}
				}

				// Termine contrattuale per dare inizio ai lavori
				Date dataTermine = (Date) SqlManager.getValueFromVectorParam(datiW9INIZ, 3).getValue();
				if (dataTermine == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_TERMINE", pagina);
				}
				if (dataTermine != null) {
					String nomeCampo = "DATA_TERMINE";
					if (dataStipula != null) {
						if (dataTermine.getTime() <= dataStipula.getTime()) {
							String messaggio = this.getMessaggioConfrontoDate(dataTermine, ">", dataStipula, "W9INIZ", "DATA_STIPULA", null);
							this.addAvviso(listaControlli, nomeTabella, nomeCampo, "E", pagina, messaggio);
						}
					}
				}

				// Misure aggiuntive e migliorative per la sicurezza
				try {
					Long conteggiow9Missic = (Long) this.sqlManager.getObject("select count(*) from W9MISSIC where CODGARA=? and CODLOTT=? and NUM_INIZ=?", new Object[] { codgara, codlott, num });
					if (conteggiow9Missic != null && conteggiow9Missic.longValue() != 0) {
						this.validazioneMisureAggiuntiveSicurezza(codgara, codlott, num, listaControlli);
					}
				} catch (SQLException e) {
					throw new GestoreException("Errore nella lettura delle informazioni relative alla pagina misure aggiuntive/migliorative " + "sicurezza della fase iniziale sopra soglia",
							"validazione986", e);
				}

				// Controllo degli incarichi professionali
				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "IN");

				// Controllo dei dati della sicurezza
				try {
					pagina = "Scheda sicurezza";
					this.validazioneW9SIC(codgara, codlott, num, listaControlli, pagina);
				} catch (SQLException e) {
					throw new GestoreException("Errore nella lettura delle informazioni relative alla scheda sicurezza", "validazione986", e);
				}

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla fase iniziale", "validazione986", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione986: fine metodo");
		}

	}

	/**
	 * Controllo delle informazioni relative alla sicurezza
	 * 
	 * @param codgara
	 * @param codlott
	 * @param num
	 * @param listaControlli
	 * @throws GestoreException
	 */
	private void validazioneW9SIC(Long codgara, Long codlott, Long num, List<Object> listaControlli, String pagina) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneW9SIC: inizio metodo");
		}
		String nomeTabella = "W9SIC";

		List<?> datiW9SIC = this.sqlManager.getVector("select pianscic, dirope, tutor from w9sic where codgara=? and codlott=? and num_iniz=?",
		    new Object[] { codgara, codlott, num });

		boolean isManodopera = false;
		String manod = (String) this.sqlManager.getObject("select manod from w9lott where codgara = ? and codlott = ?", new Object[] { codgara, codlott });
		if (isStringaValorizzata(manod) && "1".equals(manod)) {
			isManodopera = true;
		}
		if (isManodopera) {
			if (datiW9SIC != null && datiW9SIC.size() > 0) {
				// Presenza piano di sicurezza e coordinamento ?

				String piansic = (String) SqlManager.getValueFromVectorParam(datiW9SIC, 0).getValue();
				if (piansic == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PIANSCIC", pagina);
				} else if ("1".equals(piansic)) {
					// Se e' stato selezionato 'Si' bisogna controllare che
					// esiste in W9INCA
					// una occorrenza con ID_RUOLO = 6 (se non e' stata gia'
					// comunicata con
					// l'aggiudazione)
					Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9inca where " + "codgara = ? and codlott = ? and num = ? "
							+ "and (sezione = 'IN' or sezione = 'PA' or sezione = 'RA') " + "and id_ruolo = 6", new Object[] { codgara, codlott, num });
					if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
						listaControlli.add(((new Object[] { "E", "Incarichi professionali", "Incarichi professionali", "Inserire il coordinatore della sicurezza in fase di progettazione" })));
					}
				}

				// E' stato nominato il direttore operativo ?
				String dirope = (String) SqlManager.getValueFromVectorParam(datiW9SIC, 1).getValue();
				if (dirope == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DIROPE", pagina);
				} else if ("1".equals(dirope)) {
					// Se e' stato selezionato 'Si' bisogna controllare che
					// esiste in W9INCA
					// una occorrenza con ID_RUOLO = 98
					Long conteggio = (Long) this.sqlManager.getObject("select count(*) from " + "w9inca " + "where codgara = ? " + "and codlott = ? " + "and num = ? " + "and sezione = 'IN' "
							+ "and id_ruolo = 98", new Object[] { codgara, codlott, num });
					if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
						listaControlli.add(((new Object[] { "E", "Incarichi professionali", "Incarichi professionali", "Inserire il direttore operativo" })));
					}
				}

				// E' stato nominato il tutor ?
				String tutor = (String) SqlManager.getValueFromVectorParam(datiW9SIC, 2).getValue();
				if (tutor == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TUTOR", pagina);
				} else if ("1".equals(tutor)) {
					// Se e' stato selezionato 'Si' bisogna controllare che
					// esiste in W9INCA
					// una occorrenza con ID_RUOLO = 99
					Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? " + "and codlott = ? and num = ? and sezione = 'IN' " + "and id_ruolo = 99",
							new Object[] { codgara, codlott, num });
					if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
						listaControlli.add(((new Object[] { "E", "Incarichi professionali", "Incarichi professionali", "Inserire il tutor" })));
					}
				}

			} else {
				String messaggio = "E' necessario valorizzare i dati relativi alla sicurezza " + "in quando il lotto prevede posa in opera o manodopera";
				listaControlli.add(((new Object[] { "E", "Scheda sicurezza", "Scheda sicurezza", messaggio })));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneW9SIC: fine metodo");
		}
	}

	/**
	 * Controllo dati per la fase di aggiudicazione per appalti di importo
	 * inferiore a 150.000 euro (codice 987).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione987(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione987: inizio metodo");
		}

		if (!listaControlli.isEmpty()) {
			this.setTitolo(listaControlli, "Aggiudicazione");
		}

		String nomeTabella = "W9APPA";
		String pagina = "Fase di aggiudicazione/affidamento";

		try {
			Long idModoGara = (Long) this.sqlManager.getObject("select id_modo_gara from w9lott where codgara = ? and codlott = ?", new Object[] { codgara, codlott });

			String selectW9APPA = "select " + " importo_subtotale, " // 0
					+ " importo_attuazione_sicurezza, " // 1
					+ " importo_compl_appalto, " // 2
					+ " importo_disposizione, " // 3
					+ " importo_compl_intervento, " // 4
					+ " perc_ribasso_agg, " // 5
					+ " perc_off_aumento, " // 6
					+ " data_verb_aggiudicazione, " // 7
					+ " importo_aggiudicazione, " // 8
					+ " DURATA_CON, " // 9
					+ " DATA_TERMINE, " // 10
					+ " DATA_STIPULA " // 11
					+ " from w9appa where codgara = ? and codlott = ? and num_appa = ?";

			List<?> datiW9APPA = this.sqlManager.getVector(selectW9APPA, new Object[] { codgara, codlott, num });

			if (datiW9APPA != null && datiW9APPA.size() > 0) {
				Double importoSubtotale = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 0).getValue();
				Double importoAttuazioneSicurezza = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 1).getValue();
				Double importoComplAppalto = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 2).getValue();

				// Importo dell'appalto al netto della sicurezza
				if (importoSubtotale == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_SUBTOTALE", pagina);
				}
				if (importoSubtotale != null && importoComplAppalto != null) {
					if (importoSubtotale.doubleValue() > importoComplAppalto.doubleValue()) {
						String messaggio = this.getMessaggioControntoImporti(importoSubtotale, "<=", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", null);
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_SUBTOTALE", "E", pagina, messaggio);
					}
				}

				// Importo totale per l'attuazione della sicurezza
				if (importoAttuazioneSicurezza == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_ATTUAZIONE_SICUREZZA", pagina);
				}
				if (importoAttuazioneSicurezza != null && importoComplAppalto != null) {
					if (importoAttuazioneSicurezza.doubleValue() > importoComplAppalto.doubleValue()) {
						String messaggio = this.getMessaggioControntoImporti(importoAttuazioneSicurezza, "<=", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", null);
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_ATTUAZIONE_SICUREZZA", "E", pagina, messaggio);
					}
				}
				// Importo complessivo dell'appalto
				if (importoComplAppalto == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_COMPL_APPALTO", pagina);
				}
				// Importo disposizione
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 3).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_DISPOSIZIONE", pagina);
				}

				// Importo complessivo intervento
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_COMPL_INTERVENTO", pagina);
				}

				// Ribasso di aggiudicazione
				Double percRibassoAgg = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 5).getValue();
				// Offerta in aumento %
				Double percOffAumento = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 6).getValue();
				if (idModoGara != null && (new Long(1)).equals(idModoGara) && percOffAumento == null) {
					if (percRibassoAgg == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PERC_RIBASSO_AGG", pagina);
					}
				}

				if (idModoGara != null && (new Long(2)).equals(idModoGara) && percRibassoAgg == null) {
					if (percOffAumento == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PERC_OFF_AUMENTO", pagina);
					}
				}

				if (percOffAumento != null && percRibassoAgg != null) {
					listaControlli
							.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "PERC_OFF_AUMENTO"), "Sono stati indicati sia Ribasso aggiudicazione che Offerta in aumento" })));
				}

				// Data di aggiudicazione definitiva
				Date dataVerbAggiudicazione = (Date) SqlManager.getValueFromVectorParam(datiW9APPA, 7).getValue();
				if (dataVerbAggiudicazione == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
				} else {
					GregorianCalendar dataMinimaCal = new GregorianCalendar(2000, 0, 1);
					Date dataMinima = dataMinimaCal.getTime();
					Date dataOdierna = new Date();
					if (dataVerbAggiudicazione != null && dataOdierna != null && dataMinima != null) {
						if (dataVerbAggiudicazione.getTime() > dataOdierna.getTime() || dataVerbAggiudicazione.getTime() < dataMinima.getTime()) {
							String messaggio = "La data digitata (" + UtilityDate.convertiData(dataVerbAggiudicazione, UtilityDate.FORMATO_GG_MM_AAAA) + ") " + "deve essere compresa tra il "
									+ UtilityDate.convertiData(dataMinima, UtilityDate.FORMATO_GG_MM_AAAA) + " ed il " + UtilityDate.convertiData(dataOdierna, UtilityDate.FORMATO_GG_MM_AAAA);
							this.addAvviso(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", "E", pagina, messaggio);
						}
					}
				}

				// Importo di aggiudicazione
				if (importoComplAppalto != null && importoComplAppalto.doubleValue() > 0) {
					Double importoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(datiW9APPA, 8).getValue();
					if (importoAggiudicazione == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", pagina);
					}
					if (importoAggiudicazione != null && percRibassoAgg != null && percRibassoAgg.doubleValue() > 0) {
						if (importoAggiudicazione.doubleValue() >= importoComplAppalto.doubleValue()) {
							String messaggio = this.getMessaggioControntoImporti(importoAggiudicazione, "<", importoComplAppalto, "W9APPA", "IMPORTO_COMPL_APPALTO", null);
							this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "E", pagina, messaggio);
						}
					}

					double impAggiudicazione = 0;
					double percRibAgg = 0;
					double percOffAum = 0;
					double impSubTot = 0;
					double impAttSic = 0;

					if (importoAggiudicazione != null) {
						impAggiudicazione = importoAggiudicazione.doubleValue();
					}
					if (percRibassoAgg != null) {
						percRibAgg = percRibassoAgg.doubleValue();
					}
					if (percOffAumento != null) {
						percOffAum = percOffAumento.doubleValue();
					}
					if (importoSubtotale != null) {
						impSubTot = importoSubtotale.doubleValue();
					}
					if (importoAttuazioneSicurezza != null) {
						impAttSic = importoAttuazioneSicurezza.doubleValue();
					}

					if (Math.abs(UtilityMath.round((impAggiudicazione - (impSubTot * ((100 - percRibAgg + percOffAum) / 100) + impAttSic)), 2)) > 0.01) {
						this.addAvviso(listaControlli, nomeTabella, "IMPORTO_AGGIUDICAZIONE", "W", pagina, "L'importo di aggiudicazione non coincide con l'importo calcolato");
					}
				}

				// Durata contrattuale in giorni
				Long durataContrattuale = (Long) SqlManager.getValueFromVectorParam(datiW9APPA, 9).getValue();
				if (durataContrattuale == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_CON", pagina);
				} else if (durataContrattuale != null && durataContrattuale.longValue() <= 0) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "DURATA_CON"), "La durata contrattuale in giorni deve essere maggiore di zero" })));
				}

				// Termine contrattuale per dare ultimazione lavori
				// if (SqlManager.getValueFromVectorParam(datiW9APPA,
				// 10).getValue() == null) {
				// this.addCampoObbligatorio(listaControlli, nomeTabella,
				// "DATA_TERMINE", pagina);
				// }

				// Data stipula contratto
				if (SqlManager.getValueFromVectorParam(datiW9APPA, 11).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_STIPULA", pagina);
				}

				// Imprese aggiudicatarie
				Long conteggiow9aggi = (Long) this.sqlManager.getObject("select count(*) from w9aggi where codgara = ? and codlott = ? and num_appa = ?", new Object[] { codgara, codlott, num });
				if (conteggiow9aggi == null || (conteggiow9aggi != null && (new Long(0)).equals(conteggiow9aggi))) {
					this.setTitolo(listaControlli, "Imprese aggiudicatarie / affidatarie");
					String messaggio = "Indicare almeno una impresa aggiudicataria";
					listaControlli.add(((new Object[] { "E", "Imprese aggiudicatarie / affidatarie", "Imprese aggiudicatarie / affidatarie", messaggio })));
				} else {
					this.validazioneImpreseAggiudicatarie(codgara, codlott, num, listaControlli);
				}

				// Incarichi professionali
				try {
					Long conteggiow9inca = (Long) this.sqlManager.getObject("select count(*) from w9inca where codgara = ? and codlott = ? and num = ? and sezione in ('RS','RE')",
							new Object[] { codgara, codlott, num });

					if (conteggiow9inca != null && conteggiow9inca.longValue() != 0) {
						this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "RS");
						this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "RE");
						/*
						 * } else { this.setTitolo(listaControlli,
						 * "Incarichi professionali");
						 * listaControlli.add(((Object) (new Object[] { "E",
						 * "Incarichi professionali", "Incarichi professionali",
						 * "Indicare almeno un incaricato / professionista"})));
						 */
					}
				} catch (SQLException e) {
					throw new GestoreException("Errore nella conteggio degli incarichi professionali", "validazione987", e);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni misure aggiuntive e migliorative della sicurezza", "validazione987", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione987: fine metodo");
		}
	}

	/**
	 * Controllo dati per le Misure aggiuntive e migliorative per la sicurezza
	 * (codice 993).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	/*
	 * private void validazione993(Long codgara, Long codlott, Long num,
	 * List<Object> listaControlli) throws GestoreException { if
	 * (logger.isDebugEnabled()) {
	 * logger.debug("validazione993: inizio metodo"); }
	 * 
	 * String nomeTabella = "W9MISSIC"; String pagina =
	 * "Misure aggiuntive e migliorative sicurezza";
	 * 
	 * try { String selectW9MISSIC = "select descmis, codimp " +
	 * " from w9missic where codgara = ? and codlott = ? and num_miss = ? ";
	 * 
	 * List< ? > datiW9MISSIC = this.sqlManager.getVector(selectW9MISSIC, new
	 * Object[]{codgara, codlott, num});
	 * 
	 * if (datiW9MISSIC != null && datiW9MISSIC.size() > 0) { // Descrizione
	 * delle principali carenze if
	 * (SqlManager.getValueFromVectorParam(datiW9MISSIC, 0).getValue() == null)
	 * { this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCMIS",
	 * pagina); }
	 * 
	 * String codimp = (String) SqlManager.getValueFromVectorParam(
	 * datiW9MISSIC, 1).getValue(); if (codimp == null) { String messaggio =
	 * "Il campo &egrave; obbligatorio"; listaControlli.add(((new Object[] {
	 * "E", pagina, "Denominazione dell\'impresa aggiudicataria", messaggio
	 * }))); } else { this.validazioneImpresa(listaControlli,
	 * "Impresa aggiudicataria", codimp); } } } catch (SQLException e) { throw
	 * new GestoreException(
	 * "Errore nella lettura delle informazioni misure aggiuntive e migliorative della sicurezza"
	 * , "validazione993", e); }
	 * 
	 * if (logger.isDebugEnabled()) {
	 * logger.debug("validazione993: fine metodo"); } }
	 */

	/**
	 * Controllo dati per la segnalazione infortuni (codice 994).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione994(final Long codgara, final Long codlott, final Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione994: inizio metodo");
		}

		String nomeTabella = "W9INFOR";
		String pagina = "Segnalazione infortuni";

		try {
			String selectW9INFOR = "select ninfort, ngiornate, codimp " + " from w9infor where codgara = ? and codlott = ? and num_infor = ? ";

			List<?> datiW9INFOR = this.sqlManager.getVector(selectW9INFOR, new Object[] { codgara, codlott, num });
			if (datiW9INFOR != null && datiW9INFOR.size() > 0) {
				// Numero infortuni totali
				if (SqlManager.getValueFromVectorParam(datiW9INFOR, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NINFORT", pagina);
				}
				// Numero giornate riconosciute
				if (SqlManager.getValueFromVectorParam(datiW9INFOR, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NGIORNATE", pagina);
				}

				// Impresa aggiudicataria
				String codimp = (String) SqlManager.getValueFromVectorParam(datiW9INFOR, 2).getValue();
				if (codimp == null) {
					String messaggio = "Il campo &egrave; obbligatorio";
					listaControlli.add(((new Object[] { "E", pagina, "Denominazione dell\'impresa aggiudicataria", messaggio })));
				} else {
					this.validazioneImpresa(listaControlli, "Impresa aggiudicataria", codimp, false);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla segnalazione infortuni", "validazione994", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione994: fine metodo");
		}
	}

	/**
	 * Controllo dati per le Inadempienze disposizioni sicurezza e regolarita'
	 * del lavoro ai sensi dell'art.23 LR 38/07 lavoro (codice 995).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione995(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione995: inizio metodo");
		}

		String nomeTabella = "W9INASIC";
		String pagina = "Inadempienze disposizioni sicurezza e regolarit&agrave; del lavoro";

		try {
			String selectW9INASIC = "select " + " dainasic, " // 0
					+ " comma3a, " // 1
					+ " comma3b, " // 2
					+ " comma45a, " // 3
					+ " comma5, " // 4
					+ " comma6, " // 5
					+ " commaltro, " // 6
					+ " codimp " // 7
					+ " from w9inasic where codgara = ? and codlott = ? and num_inad = ? ";

			List<?> datiW9INASIC = this.sqlManager.getVector(selectW9INASIC, new Object[] { codgara, codlott, num });

			if (datiW9INASIC != null && datiW9INASIC.size() > 0) {

				// Data riscontro inadempienza
				if (SqlManager.getValueFromVectorParam(datiW9INASIC, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DAINASIC", pagina);
				}

				// Violazioni art. 23
				String comma3a = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 1).getValue();
				String comma3b = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 2).getValue();
				String comma45a = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 3).getValue();
				String comma5 = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 4).getValue();
				String comma6 = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 5).getValue();
				String commaltro = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 6).getValue();

				if (comma3a == null && comma3b == null && comma45a == null && comma5 == null && comma6 == null && commaltro == null) {
					String descrizione = this.getDescrizioneCampo(nomeTabella, "COMMA3A");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "COMMA3B");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "COMMA45A");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "COMMA5");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "COMMA6");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "COMMALTRO");

					String messaggio = "Indicare almeno una delle violazioni elencate";
					listaControlli.add(((new Object[] { "E", pagina, descrizione, messaggio })));
				}

				// Impresa aggiudicataria
				String codimp = (String) SqlManager.getValueFromVectorParam(datiW9INASIC, 7).getValue();
				if (codimp != null) {
					this.validazioneImpresa(listaControlli, "Impresa aggiudicataria", codimp, false);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni inadempienze predisposizioni sicurezza e regolarit&agrave; lavoro", "validazione995", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione995: fine metodo");
		}
	}

	/**
	 * Controllo dati per Mancata aggiudicazione definitiva o pagamento a
	 * seguito verifica negativa regolarita' contributiva ed assicurativa ai
	 * sensi art.17 c. 1 e 2 LR 38/07 (codice 996)
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione996(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione996: inizio metodo");
		}

		String nomeTabella = "W9REGCON";
		String pagina = "Mancata aggiudicazione definitiva o pagamento a seguito verifica negativa" + " regolarit&agrave; contributiva ed assicurativa";

		try {
			String selectW9REGCON = "select " + " descare, " // 0
					+ " codimp, " // 1
					+ " iddurc, " // 2
					+ " datadurc, " // 3
					+ " cassaedi, " // 4
					+ " riscontro_irr, " // 5
					+ " datacomun " // 6
					+ " from w9regcon where codgara = ? and codlott = ? and num_rego = ? ";

			List<?> datiW9REGCON = this.sqlManager.getVector(selectW9REGCON, new Object[] { codgara, codlott, num });

			if (datiW9REGCON != null && datiW9REGCON.size() > 0) {
				// Descrizione delle principali carenze
				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCARE", pagina);
				}

				String codimp = (String) SqlManager.getValueFromVectorParam(datiW9REGCON, 1).getValue();
				if (codimp != null) {
					this.validazioneImpresa(listaControlli, "Impresa aggiudicataria", codimp, false);
				}
				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 2).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IDDURC", pagina);
				}

				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 3).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATADURC", pagina);
				}

				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CASSAEDI", pagina);
				}

				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 5).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "RISCONTRO_IRR", pagina);
				}

				if (SqlManager.getValueFromVectorParam(datiW9REGCON, 6).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATACOMUN", pagina);
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni verifica regolarit&agrave; contributiva ed assicurativa", "validazione996", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione996: fine metodo");
		}
	}

	/**
	 * Controllo dati per Esito negativo verifiche dell'idoneita' tecnico
	 * professionale ai sensi dell'art. 16 c.1 LR 38/07 (codice 997)
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione997(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione997: inizio metodo");
		}

		String nomeTabella = "W9TECPRO";
		String pagina = "Esito negativo verifiche dell'idoneit&agrave; tecnico professionale";

		try {
			String selectW9TECPRO = "select " + " descare, " // 0
					+ " codimp, " // 1
					+ " datauni, " // 2
					+ " inido1, " // 3
					+ " inido2, " // 4
					+ " inido3, " // 5
					+ " inido4, " // 6
					+ " inido5, " // 7
					+ " inido6, " // 8
					+ " inido7 " // 9
					+ " from w9tecpro where codgara = ? and codlott = ? and num_tpro = ? ";

			List<?> datiW9TECPRO = this.sqlManager.getVector(selectW9TECPRO, new Object[] { codgara, codlott, num });

			if (datiW9TECPRO != null && datiW9TECPRO.size() > 0) {
				// Descrizione delle principali carenze
				if (SqlManager.getValueFromVectorParam(datiW9TECPRO, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCARE", pagina);
				}

				String codimp = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 1).getValue();
				if (codimp != null) {
					this.validazioneImpresa(listaControlli, "Impresa aggiudicataria", codimp, false);
				}

				// Data verifica inidoneita'
				if (SqlManager.getValueFromVectorParam(datiW9TECPRO, 2).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATAUNI", pagina);
				}

				String inido1 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 3).getValue();
				String inido2 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 4).getValue();
				String inido3 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 5).getValue();
				String inido4 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 6).getValue();
				String inido5 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 7).getValue();
				String inido6 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 8).getValue();
				String inido7 = (String) SqlManager.getValueFromVectorParam(datiW9TECPRO, 9).getValue();

				if (inido1 == null && inido2 == null && inido3 == null && inido4 == null && inido5 == null && inido6 == null && inido7 == null) {
					String descrizione = this.getDescrizioneCampo(nomeTabella, "INIDO1");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO2");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO3");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO4");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO5");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO6");
					descrizione += ", " + this.getDescrizioneCampo(nomeTabella, "INIDO7");
					String messaggio = "Valorizzare almeno uno dei campi proposti";
					listaControlli.add(((new Object[] { "E", pagina, descrizione, messaggio })));
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni esito negativo verifica idoneit&agrave; tecnico-professionale", "validazione997", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione997: fine metodo");
		}
	}

	/**
	 * Controllo dati per la scheda cantiere (codice 998).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione998(Long codgara, Long codlott, Long num, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione998: inizio metodo");
		}
		
		String nomeTabella = "W9CANT";
		String pagina = "Apertura cantiere";

		try {
			String selectW9CANT = "select diniz, dlav, indcan, prov, comune, NUM_APPA " + " from w9cant where codgara = ? and codlott = ? and num_cant = ?";

			List<?> datiW9CANT = this.sqlManager.getVector(selectW9CANT, new Object[] { codgara, codlott, num });

			if (datiW9CANT != null && datiW9CANT.size() > 0) {
				Long numappa = (Long)SqlManager.getValueFromVectorParam(datiW9CANT, 5).getValue();
				// Data inizio lavori
				if (SqlManager.getValueFromVectorParam(datiW9CANT, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DINIZ", pagina);
				}

				// Data presunta dei lavori
				if (SqlManager.getValueFromVectorParam(datiW9CANT, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DLAV", pagina);
				}

				// Indirizzo del cantiere
				if (SqlManager.getValueFromVectorParam(datiW9CANT, 2).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "INDCAN", pagina);
				}

				// Provincia del cantiere
				if (SqlManager.getValueFromVectorParam(datiW9CANT, 3).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PROV", pagina);
				}

				// Comune del cantiere
				if (SqlManager.getValueFromVectorParam(datiW9CANT, 4).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COMUNE", pagina);
				}

				// Se esistono dei destinatari allora una serie di campi
				// diventano obbligatori
				String selectW9CANDEST = "select DEST from W9CANTDEST where codgara=? and codlott=? and num_cant=?";

				List<?> datiW9CANTDEST = this.sqlManager.getListVector(selectW9CANDEST, new Object[] { codgara, codlott, num });

				// if (datiW9CANTDEST != null && datiW9CANTDEST.size() > 0) {
				String selectW9CANT1 = "select COORD_X, COORD_Y, LATIT, LONGIT, NUMLAV, NUMIMP, LAVAUT, "
						+ " TIPOPERA, TIPINTERV, TIPCOSTR, MAILRIC from W9CANT where codgara=? and codlott=? and num_cant=?";

				List<?> datiW9CANT1 = this.sqlManager.getVector(selectW9CANT1, new Object[] { codgara, codlott, num });

				if (datiW9CANTDEST != null && datiW9CANTDEST.size() > 0 && (datiW9CANT1 == null || (datiW9CANT1 != null && datiW9CANT1.size() < 1))) {
					// inserire una pratica DURC
				}

				if (datiW9CANTDEST != null && datiW9CANTDEST.size() > 0) {

					// Coordinata Gauss Boaga X cantiere
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 0).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "COORD_X", pagina);
					}

					// Coordinata Gauss Boaga Y cantiere
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 1).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "COORD_Y", pagina);
					}

					// Coordinata WGS84 latitudine
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 2).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "LATIT", pagina);
					}

					// Coordinata WGS84 longitudine
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 3).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "LONGIT", pagina);
					}

					// Numero massimo dei lavoratori
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 4).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NUMLAV", pagina);
					}

					// Numero delle imprese
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 5).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NUMIMP", pagina);
					}

					// Numero dei lavoratori autonomi
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 6).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "LAVAUT", pagina);
					}

					// Tipo opera
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 7).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOPERA", pagina);
					}

					// Tipologia di intervento
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 8).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPINTERV", pagina);
					}

					// Tipologia costruttiva
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 9).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPCOSTR", pagina);
					}

					// Indirizzo mail di ricezione attestato
					if (SqlManager.getValueFromVectorParam(datiW9CANT1, 10).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "MAILRIC", pagina);
					}

				}

				String sqlW9CANTIMP = "select count(*) from W9CANTIMP where CODGARA=? and CODLOTT=? and NUM_CANT=?";

				Long numeroImprese = (Long) this.sqlManager.getObject(sqlW9CANTIMP, new Object[] { codgara, codlott, num });

				if (datiW9CANTDEST != null && datiW9CANTDEST.size() > 0) {
					if (numeroImprese == null || (numeroImprese != null && numeroImprese.longValue() == 0)) {
						listaControlli.add(((new Object[] { "E", "Imprese", "Imprese", "Indicare almeno un impresa" })));
					} else {

						nomeTabella = "W9CANTIMP";
						sqlW9CANTIMP = "select CIPDURC, PROTDURC, DATDURC, CODIMP from W9CANTIMP " + "where CODGARA=? and CODLOTT=? and NUM_CANT=?";

						List<?> listaCANTIMP = this.sqlManager.getListVector(sqlW9CANTIMP, new Object[] { codgara, codlott, num });

						if (listaCANTIMP != null && listaCANTIMP.size() > 0) {
							for (int wi = 0; wi < listaCANTIMP.size(); wi++) {
								Vector<?> datiCANTIMP = (Vector<?>) listaCANTIMP.get(wi);

								if (SqlManager.getValueFromVectorParam(datiCANTIMP, 0).getValue() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "CIPDURC", pagina);
								} else {
									String cipdurc = SqlManager.getValueFromVectorParam(datiCANTIMP, 0).getStringValue();
									if (!cipdurc.matches("[0-9]{14}") || cipdurc.length() != 14) {
										listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CIPDURC"),
												"Indicare un codice di 14 caratteri costituito da soli numeri" })));
									}
								}
								if (SqlManager.getValueFromVectorParam(datiCANTIMP, 1).getValue() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "PROTDURC", pagina);
								} else {
									String protdurc = SqlManager.getValueFromVectorParam(datiCANTIMP, 1).getStringValue();
									if (!protdurc.matches("[0-9]{8}") || protdurc.length() != 8) {
										listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "PROTDURC"),
												"Inserire un protocollo pratica DURC valido per l'impresa (8 caratteri numerici)" })));
									}
								}
								if (SqlManager.getValueFromVectorParam(datiCANTIMP, 2).getValue() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "DATDURC", pagina);
								}
								if (SqlManager.getValueFromVectorParam(datiCANTIMP, 3).getValue() == null) {
									this.addCampoObbligatorio(listaControlli, nomeTabella, "CODIMP", pagina);
								}
							}
						}
					}
				}

				this.validazioneIncarichiProfessionali(codgara, codlott, num, listaControlli, "NP");

				// Controlli sugli incarichi professionali: devono essere almeno
				// 2,
				// uno con id_ruolo=6 e uno con id_ruolo=7
				String sqlW9INCA = "select count(*) from W9INCA where CODGARA=? and CODLOTT=? and NUM=? and SEZIONE='NP'";

				Long numeroIncarichi = (Long) this.sqlManager.getObject(sqlW9INCA, new Object[] { codgara, codlott, num });

				if (datiW9CANTDEST != null && datiW9CANTDEST.size() > 0) {
					if (numeroIncarichi == null || (numeroIncarichi != null && numeroIncarichi < 2)) {
						listaControlli.add(((new Object[] { "E", "Incarichi professionali", "Incarichi professionali",
								"Indicare il coordinatore sicurezza fase progettazione e il coordinatore sicurezza in corso d'opera" })));
					} else {
						// Determinare se tra gli incarichi professionali,
						// almeno uno ha id_ruolo=6
						// e almeno uno ha id_ruolo=7
						pagina = "Incarichi professionali";
						nomeTabella = "W9INCA";

						sqlW9INCA = "select ID_RUOLO from W9INCA where CODGARA=? and CODLOTT=? and NUM=? and SEZIONE='NP'";

						List<?> listaW9INCA = this.sqlManager.getListVector(sqlW9INCA, new Object[] { codgara, codlott, num });

						int countIdRuolo6 = 0;
						int countIdRuolo7 = 0;
						if (listaW9INCA != null && listaW9INCA.size() > 1) {
							for (int wi = 0; wi < listaW9INCA.size(); wi++) {
								Vector<?> vect = (Vector<?>) listaW9INCA.get(wi);
								JdbcParametro jdbcParIdRuolo = (JdbcParametro) vect.get(0);

								if (jdbcParIdRuolo != null) {
									Long idRuolo = (Long) jdbcParIdRuolo.getValue();
									if (idRuolo != null) {
										if (idRuolo.longValue() == 6) {
											countIdRuolo6++;
										}

										if (idRuolo.longValue() == 7) {
											countIdRuolo7++;
										}
									}
								}
							}
						}

						if (countIdRuolo6 < 1 || countIdRuolo7 < 1) {
							listaControlli.add(((new Object[] { "E", "Incarichi professionali", "Incarichi professionali",
									"Indicare il coordinatore sicurezza fase progettazione e il coordinatore sicurezza in corso d'opera" })));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla apertura cantiere", "validazione998", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione998: fine metodo");
		}
	}

	/**
	 * Controllo dai dati per l'invio dei dati della gara, del bando di gara e
	 * dei lotti in essa contenuti al fine di ricevere il CIG (codice 988).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione988RegioneMarche(Long codgara, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione988RegioneMarche: inizio metodo");
		}

		List<?> controlli;
		String sqlControlli, valore, messaggio;
		int codiceControllo;

		try {
			String pagina = "";
			// recupero i controlli da effettuare per l'invio della gara
			sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM w9controlli WHERE (codcontrollo = 1001 OR codcontrollo = 1002 OR codcontrollo = 3001 OR codcontrollo = 3002 OR codcontrollo = 3003 OR codcontrollo = 3004 OR codcontrollo = 3005 OR codcontrollo = 3006 OR codcontrollo = 3007 OR codcontrollo = 3008 OR codcontrollo = 3009) AND tipocontrollo is not null AND tipocontrollo <> ''";
			controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});

			String selectW9GARA = "select oggetto, idavgara, prev_bando, codein, importo_gara, id_modo_indizione, tipo_app, dguri, dscade"
			    + " from w9gara where codgara = ?";

			DataColumnContainer gara = new DataColumnContainer(this.sqlManager, "W9GARA", selectW9GARA, new Object[] { codgara });

			if (gara != null) {
				pagina = "Dati generali della gara";
				//String prevBando = gara.getString("prev_bando");
				Long nrLotti = (Long) this.sqlManager.getObject("select count(*) from w9lott where codgara = ? ", new Object[] { codgara });

				if (controlli != null && controlli.size() > 0) {
					for (int i = 0; i < controlli.size(); i++) {
						// verifico il controllo da effettuare
						valore = SqlManager.getValueFromVectorParam(controlli.get(i), 1).getStringValue();
						if (StringUtils.isNotEmpty(valore)) {
							codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(i), 0).getStringValue());
							messaggio = SqlManager.getValueFromVectorParam(controlli.get(i), 2).getStringValue();

							switch (codiceControllo) {
							case 1001:
								// valorizzazione codice fiscale stazione
								// appaltante
							case 1002:
								// Il C.F. della stazione appaltante deve essere
								// un C.F. valido di persona giuridica
								String codiceFiscale = (String) this.sqlManager.getObject("select cfein from uffint where codein = ? ", new Object[] { gara.getString("codein") });
								// controllo partita iva / codice fiscale
								// stazione appaltante obbligatoria
								if (codiceControllo == 1001) {
									if (!isStringaValorizzata(codiceFiscale)) {
										this.addAlert(listaControlli, valore, "Dati generali - stazione appaltante", messaggio);
									}
								} else if (codiceControllo == 1002) {
									if (!UtilityFiscali.isValidPartitaIVA(codiceFiscale)) {
										this.addAlert(listaControlli, valore, "Dati generali - stazione appaltante", messaggio);
									}
								}
								break;
							case 3001:
								// Oggetto della gara obbligatorio
								if (!isStringaValorizzata(gara.getString("oggetto"))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3002:
								// Codice della gara obbligatorio
								if (!isStringaValorizzata(gara.getString("idavgara"))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3003:
								// Importo gara obbligatorio
								if (gara.getDouble("importo_gara") == null || gara.getDouble("importo_gara") <= 0) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3004:
								// Modalita' indizione gara obbligatoria
								if (gara.getLong("id_modo_indizione") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3005:
								// Modalita' di realizzazione obbligatoria
								if (gara.getLong("tipo_app") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3006:
								// Data pubblicazione sulla GURI obbligatoria se
								// PREV_BANDO='1'
								/*if (isStringaValorizzata(prevBando) && prevBando.equals("1") && gara.getData("dguri") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}*/
								break;
							case 3007:
								// Data scadenza obbligatoria se data guri è valorizzata
								if (gara.getData("dguri") != null && gara.getData("dscade") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3008:
								// Inserire almeno un documento di gara se
								// se data guri è valorizzata
								Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9docgara where codgara = ? and file_allegato is not null", new Object[] { codgara });
								if (gara.getData("dguri") != null && (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio)))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 3009:// *****************************************************
								// Inserire almeno un lotto
								if (nrLotti == null || (nrLotti != null && (new Long(0)).equals(nrLotti))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							}
						}
					}
				}

				if (nrLotti != 0) {
					// Controllo dei dati dei lotti associata alla gara
					this.validazioneLottiRegioneMarche(codgara, listaControlli, pagina);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla gara", "validazione988RegioneMarche", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione988RegioneMarche: fine metodo");
		}
	}

	/**
	 * @param codgara
	 * @param listaControlli
	 * @param pagina
	 * @throws GestoreException
	 */
	private void validazioneLottiRegioneMarche(Long codgara, List<Object> listaControlli, String pagina) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneLottiRegioneMarche: inizio metodo");
		}

		String valore;
		String messaggio;

		try {

			// recupero i controlli da effettuare per i dati degli interventi
			String sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM w9controlli WHERE (codcontrollo = 3010 OR codcontrollo = 3011 OR codcontrollo = 3012) AND tipocontrollo is not null AND tipocontrollo <> ''";
			List<?> controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});

			if (controlli != null && controlli.size() > 0) {
				String queryLotti = "select oggetto, cig, importo_lotto from w9lott where codgara = ?";
				List<?> lotti = this.sqlManager.getListHashMap(queryLotti, new Object[] { codgara });

				if (lotti != null && lotti.size() > 0) {
					for (int i = 0; i < lotti.size(); i++) {
						DataColumnContainer lotto = new DataColumnContainer(sqlManager, "w9lott", queryLotti, new Object[] { codgara });
						lotto.setValoriFromMap((HashMap<?, ?>) lotti.get(i), true);

						for (int j = 0; j < controlli.size(); j++) {
							// verifico il controllo da effettuare
							valore = SqlManager.getValueFromVectorParam(controlli.get(j), 1).getStringValue();
							if (StringUtils.isNotEmpty(valore)) {
								int codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(j), 0).getStringValue());
								messaggio = SqlManager.getValueFromVectorParam(controlli.get(j), 2).getStringValue();

								switch (codiceControllo) {
								case 3010:
									// Oggetto del lotto obbligatorio
									if (!isStringaValorizzata(lotto.getString("oggetto"))) {
										this.addAlert(listaControlli, valore, pagina, messaggio);
									}
									break;
								case 3011:
									// Codice CIG obbligatorio
									if (!isStringaValorizzata(lotto.getString("cig"))) {
										this.addAlert(listaControlli, valore, pagina, messaggio);
									}
									break;
								case 3012:
									// Importo lotto obbligatorio
									if (lotto.getDouble("importo_lotto") == null || lotto.getDouble("importo_lotto") <= 0) {
										this.addAlert(listaControlli, valore, pagina, messaggio);
									}
									break;
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative ai lotti", "validazione988RegioneMarche", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneLottiRegioneMarche: fine metodo");
		}
	}

	/**
	 * Controllo dei dati per la fase "Comunicazione esito".
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione984(Long codgara, Long codlott, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione984: inizio metodo");
		}

		String nomeTabella = "W9ESITO";
		String pagina = "Esito procedura di selezione del contratto";
		
		try {
			Vector<?> datiW9ESITO = this.sqlManager.getVector("select esito_procedura, data_verb_aggiudicazione, importo_aggiudicazione " 
					+ "from w9esito where codgara=? and codlott=?", new Object[] { codgara, codlott });
			if (datiW9ESITO != null && datiW9ESITO.size() > 0) {
				Long esitoProcedura = (Long) ((JdbcParametro) datiW9ESITO.get(0)).getValue();

				List<Object> listaControlli984 = new ArrayList<Object>();
				
				if (esitoProcedura == null) {
					this.addCampoObbligatorio(listaControlli984, nomeTabella, "ESITO_PROCEDURA", pagina);
				/*} else {
					if (((JdbcParametro) datiW9ESITO.get(1)).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DATA_VERB_AGGIUDICAZIONE", pagina);
					}
					if ((new Long(1)).equals(esitoProcedura)) {
						Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9esito where codgara = ? and codlott = ? and file_allegato is not null", new Object[] { codgara,
								codlott });
						if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
							listaControlli.add(((new Object[] { "E", "Pubblicazione dell\'esito della gara", "Pubblicazione dell\'esito della gara",
									"Indicare un file PDF per la pubblicazione sul sito della Regione" })));
						}
					}*/
				}

				// Object dataVerbAggiud = ((JdbcParametro)
				// datiW9ESITO.get(0)).getValue();
				// if (dataVerbAggiud == null) {
				// this.addCampoObbligatorio(listaControlli, nomeTabella,
				// "DATA_VERB_AGGIUDICAZIONE", pagina);
				// }

				if (!listaControlli984.isEmpty()) {
					this.setTitolo(listaControlli, "Esito procedura di selezione del contratto");
					for (int j = 0; j < listaControlli984.size(); j++) {
						listaControlli.add(listaControlli984.get(j));
					}
				}
				
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'esito", "validazione984", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione984: fine metodo");
		}
	}

	/**
	 * Controllo dei dati per la fase "Comunicazione esito".
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            codice del lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione984RegioneMarche(Long codgara, Long codlott, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione984RegioneMarche: inizio metodo");
		}

		List<?> controlli;
		String sqlControlli, valore, messaggio;
		int codiceControllo;
		try {
			String pagina = "";

			// recupero i controlli da effettuare per l'invio dell'esitoa
			sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM w9controlli WHERE (codcontrollo = 4001 OR codcontrollo = 4002 OR codcontrollo = 4003) AND tipocontrollo is not null AND tipocontrollo <> ''";
			controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});

			String selectW9ESITO = "select esito_procedura, data_verb_aggiudicazione" + " from w9esito where codgara = ? and codlott = ? ";

			DataColumnContainer esito = new DataColumnContainer(this.sqlManager, "W9ESITO", selectW9ESITO, new Object[] { codgara, codlott });

			if (esito != null) {
				pagina = "Comunicazione esito";

				if (controlli != null && controlli.size() > 0) {
					for (int i = 0; i < controlli.size(); i++) {
						// verifico il controllo da effettuare
						valore = SqlManager.getValueFromVectorParam(controlli.get(i), 1).getStringValue();

						if (valore != null && !valore.equals("")) {
							codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(i), 0).getStringValue());
							messaggio = SqlManager.getValueFromVectorParam(controlli.get(i), 2).getStringValue();

							switch (codiceControllo) {
							case 4001:
								// Esito della procedura obbligatorio
								if (esito.getLong("esito_procedura") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 4002:
								// Data aggiudicazione obbligatoria
								if (esito.getData("data_verb_aggiudicazione") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 4003:
								// File esito obbligatorio
								Long allegato = (Long) this.sqlManager.getObject("select count(*) from w9esito where codgara = ? and codlott = ? and file_allegato is not null", new Object[] {
										codgara, codlott });
								if (allegato == null || (allegato != null && (new Long(0)).equals(allegato))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relativa all'esito", "validazione984RegioneMarche", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione984RegioneMarche: fine metodo");
		}
	}

	/**
	 * Controllo dai dati per l'invio dei dati della gara, del bando di gara e
	 * dei lotti in essa contenuti al fine di ricevere il CIG (codice 988).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlotto
	 *            Codice del lotto della gara (opzionale)
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione988(Long codgara, Long codlotto, List<Object> listaControlli, String profiloAttivo) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione988: inizio metodo");
		}

		try {
			String nomeTabella = "W9GARA";
			String pagina = "";

			String selectW9GARA = "select " + " oggetto, " // 0
					+ " idavgara, " // 1
					+ " tipo_app, " // 2
					+ " impdis, " // 3
					+ " importo_gara, " // 4
					+ " nlotti, " // 5
					+ " codein, " // 6
					+ " idcc, " // 7
					+ " flag_sa_agente, " // 8
					+ " id_tipologia_sa, " // 9
					+ " rup, " // 10
					+ " denom_sa_agente, " // 11
					+ " cf_sa_agente, " // 12
					+ " tipologia_procedura, " // 13
					+ " flag_centrale_stipula, " // 14
					+ " flag_ente_speciale, " // 15
					+ " id_modo_indizione, " // 16
					+ " cig_accquadro, " // 17
					+ " ric_alluv, " // 18
					+ " dguri, " // 19
					+ " dscade, " // 20
					+ " somma_urgenza, " // 21
					+ " durata_accquadro, " // 22
					+ " ver_simog " // 23
					+ " from w9gara where codgara = ?";

			List<?> datiW9GARA = this.sqlManager.getVector(selectW9GARA, new Object[] { codgara });

			if (datiW9GARA != null && datiW9GARA.size() > 0) {
				pagina = "Dati generali della gara";

				// Versione simog
				Long verSimog = (Long) SqlManager.getValueFromVectorParam(datiW9GARA, 23).getValue();
				
				// Oggetto della gara
				if (SqlManager.getValueFromVectorParam(datiW9GARA, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
				}

				// Identificativo delle gara
				if (SqlManager.getValueFromVectorParam(datiW9GARA, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IDAVGARA", pagina);
				}

				// Flag ente speciale e modo indizione
				String flagEnteSpeciale = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 15).getValue();
				if (!isStringaValorizzata(flagEnteSpeciale)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ENTE_SPECIALE", pagina);
				/*} else {
					if ("S".equalsIgnoreCase(flagEnteSpeciale) && SqlManager.getValueFromVectorParam(datiW9GARA, 16).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_INDIZIONE", pagina);
					}*/
				}

				// Tipo di appalto
				Long tipoApp = (Long) SqlManager.getValueFromVectorParam(datiW9GARA, 2).getValue();
				if (tipoApp == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_APP", pagina);
				}

				// CIG Accordo Quadro
				if (tipoApp != null && ((new Long(2)).equals(tipoApp) || (new Long(11)).equals(tipoApp))) {
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 17).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG_ACCQUADRO", pagina);
					}
				}

		        // Somma urgenza e Articolo somma urgenza 
		        if (SqlManager.getValueFromVectorParam(datiW9GARA, 21).getValue() == null) {
		          this.addAvviso(listaControlli, nomeTabella, "SOMMA_URGENZA", "W", pagina, 
		              "Campo non valorizzato");
		        }
		        
		        // Durata accordo quadro
		        if (verSimog != null && verSimog < 3 && tipoApp != null && ((new Long(9)).equals(tipoApp) || (new Long(17)).equals(tipoApp) || (new Long(18)).equals(tipoApp))) {
		          if (SqlManager.getValueFromVectorParam(datiW9GARA, 22).getValue() == null) {
		            this.addCampoObbligatorio(listaControlli, nomeTabella, "DURATA_ACCQUADRO", pagina);
		          } else if (((Long)SqlManager.getValueFromVectorParam(datiW9GARA, 22).getValue()).longValue() <= 0) {
		            this.addAvviso(listaControlli, nomeTabella, "DURATA_ACCQUADRO", "E", pagina, "Il valore del campo deve essere maggiore di zero");
		          }
		        }
				
				// E' prevista la pubblicazione del bando
				/*String previstaPubblicazBando = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 18).getValue();
				if (previstaPubblicazBando == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PREV_BANDO", pagina);
				}*/

				// Importo della gara
        		JdbcParametro jdbcPar = SqlManager.getValueFromVectorParam(datiW9GARA, 4);
				if (jdbcPar.getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_GARA", pagina);
				} else if (((Double) jdbcPar.getValue()).doubleValue() == 0d) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_GARA"), "Il valore del campo deve essere maggiore di zero" } )));
				}

				// RUP - Responsabile unico del procedimento
				String rup = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 10).getValue();
				if (rup == null) {
					String messaggio = "E\' obbligatorio indicare il responsabile unico del procedimento";
					listaControlli.add(((new Object[] { "E", "Responsabile unico del procedimento", "Responsabile unico del procedimento", messaggio })));
				} else {
					this.validazioneTecnico(listaControlli, "Responsabile unico del procedimento", rup);
				}

				// Stazione appaltante
				pagina = "Stazione appaltante";
				String codein = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 6).getValue();
				if (codein == null) {
					String messaggio = "E\' obbligatorio indicare la stazione appaltante";
					listaControlli.add(((new Object[] { "E", pagina, "Stazione appaltante", messaggio })));
				} else {
					this.validazioneStazioneAppaltante(listaControlli, "Stazione appaltante", codein);
				}

				// Centro di costo
				pagina = "Centro di costo";
				Long idcc = (Long) SqlManager.getValueFromVectorParam(datiW9GARA, 7).getValue();
				if (idcc == null) {
					String messaggio = "E\' obbligatorio indicare il centro di costo";
					listaControlli.add(((new Object[] { "E", pagina, "Centro di costo", messaggio })));
				} else {
					this.validazioneCentroCosto(listaControlli, "Centro di costo", idcc);
				}

				// La stazione appaltante agisce per conto di altro soggetto ?
				pagina = "Soggetto per cui agisce la stazione appaltante";
				String flagSaAgente = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 8).getValue();
				if (verSimog < 5 && flagSaAgente == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SA_AGENTE", pagina);
				}

				// Stazione appaltante agente
				if (flagSaAgente != null && "1".equals(flagSaAgente) && verSimog < 5) {
					// Tipologia della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 9).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPOLOGIA_SA", pagina);
					}

					// Denominazione della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 11).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DENOM_SA_AGENTE", pagina);
					}

					// Codice fiscale della stazione appaltante agente
					String cfSaAgente = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 12).getValue();
					if (!isStringaValorizzata(cfSaAgente)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CF_SA_AGENTE", pagina);
					} else {
						if ((!UtilityFiscali.isValidCodiceFiscale(cfSaAgente)) && (!UtilityFiscali.isValidPartitaIVA(cfSaAgente))) {
							listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CF_SA_AGENTE"), "Il campo non rispetta il formato previsto" })));
						}
					}

					// Tipologia procedura (nel caso agisca per conto di altri)
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 13).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOLOGIA_PROCEDURA", pagina);
					}

					// La centrale di committenza provvede alla stipula?
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 14).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_CENTRALE_STIPULA", pagina);
					}
				}

				//pagina = "Bando";
				//if (previstaPubblicazBando != null && "1".equals(previstaPubblicazBando)) {
					// Data pubblicazione del bando sulla GURI
					//if (SqlManager.getValueFromVectorParam(datiW9GARA, 19).getValue() == null) {
					//	this.addCampoObbligatorio(listaControlli, nomeTabella, "DGURI", pagina);
				//}

				// Data scadenza obbligatoria se data GURI è valorizzata
				/*if (SqlManager.getValueFromVectorParam(datiW9GARA, 19).getValue() != null) {
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 20).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DSCADE", pagina);
					}

					Long conteggio = (Long) this.sqlManager.getObject(
					    "select count(*) from w9docgara where codgara = ? and (file_allegato is not null or url is not null)", new Object[] { codgara });
					if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
						String messaggio = "Indicare almeno un file PDF per la pubblicazione sul sito della Regione";
						listaControlli.add(((new Object[] { "E", "Bando", "Bando", messaggio })));
					}
				}*/
				
				// Controllo dei dati di pubblicita' della gara
				try {
					pagina = "Pubblicit&agrave; gara";
					this.validazioneW9PUBB(codgara, listaControlli, pagina);
				} catch (SQLException e) {
					throw new GestoreException("Errore nella lettura delle informazioni relative alla gara", "validazione988", e);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla gara", "validazione988", e);
		}

		// Controllo dei dati dei lotti associata alla gara
		try {
			List<?> datiW9LOTT = null;
			if (codlotto != null) {
				datiW9LOTT = this.sqlManager.getListVector("select codlott from w9lott where codgara = ? and codlott = ?", new Object[] { codgara, codlotto });
			} else {
				datiW9LOTT = this.sqlManager.getListVector("select codlott from w9lott where codgara = ?", new Object[] { codgara });
			}

			if (datiW9LOTT != null && datiW9LOTT.size() > 0) {
				for (int i = 0; i < datiW9LOTT.size(); i++) {
					List<Object> listaControlliW9LOTT = new Vector<Object>();
					Long codlott = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT.get(i), 0).getValue();
					this.validazioneLotto(codgara, codlott, listaControlliW9LOTT, profiloAttivo);

					if (!listaControlliW9LOTT.isEmpty()) {
					  String codiceCig = null;
			      Long numeroLotto = (Long) this.sqlManager.getObject(
			          "select NLOTTO from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott });
			      if (numeroLotto != null) {
              this.setTitolo(listaControlli, "Dati del lotto n. " + numeroLotto.toString());
			      } else {
			        codiceCig = (String) this.sqlManager.getObject(
			            "select CIG from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codgara, codlott });
			        this.setTitolo(listaControlli, "Dati del lotto CIG = " + codiceCig);
			      }

						for (int j = 0; j < listaControlliW9LOTT.size(); j++) {
							listaControlli.add(listaControlliW9LOTT.get(j));
						}
					}
				}
			} else {
				String messaggio = "Non esiste alcun lotto associato alla gara";
				listaControlli.add(((new Object[] { "E", "Lotto", "Lotto", messaggio })));
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative ai lotti", "validazione988", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione988: fine metodo");
		}
	}

	/**
	 * Controllo dai dati per l'invio dei dati della gara semplificata, del
	 * bando di gara e dei lotti semplificati in essa contenuti al fine di
	 * ricevere il CIG (codice 13).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlotto
	 *            Codice del lotto della gara (opzionale)
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione13(Long codgara, Long codlotto, List<Object> listaControlli, String profiloAttivo) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione13: inizio metodo");
		}

		try {
			String nomeTabella = "W9GARA";
			String pagina = "";

			String selectW9GARA = "select " + " oggetto, " // 0
					+ " idavgara, " // 1
					+ " tipo_app, " // 2
					+ " impdis, " // 3
					+ " importo_gara, " // 4
					+ " nlotti, " // 5
					+ " codein, " // 6
					+ " idcc, " // 7
					+ " flag_sa_agente, " // 8
					+ " id_tipologia_sa, " // 9
					+ " rup, " // 10
					+ " denom_sa_agente, " // 11
					+ " cf_sa_agente, " // 12
					+ " tipologia_procedura, " // 13
					+ " flag_centrale_stipula, " // 14
					+ " flag_ente_speciale, " // 15
					+ " id_modo_indizione, " // 16
					+ " cig_accquadro, " // 17
					+ " prev_bando, " // 18
					+ " dguri, " // 19
					+ " dscade, " // 20
					+ " ric_alluv " // 21
					+ " from w9gara where codgara = ?";

			List<?> datiW9GARA = this.sqlManager.getVector(selectW9GARA, new Object[] { codgara });

			if (datiW9GARA != null && datiW9GARA.size() > 0) {
				pagina = "Dati generali della gara";

				// Oggetto della gara
				if (SqlManager.getValueFromVectorParam(datiW9GARA, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
				}

				// Identificativo delle gara
				if (SqlManager.getValueFromVectorParam(datiW9GARA, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IDAVGARA", pagina);
				}

				// Flag ente speciale e modo indizione
				String flagEnteSpeciale = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 15).getValue();
				if (!isStringaValorizzata(flagEnteSpeciale)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_ENTE_SPECIALE", pagina);
				} else {
					if ("S".equalsIgnoreCase(flagEnteSpeciale) && SqlManager.getValueFromVectorParam(datiW9GARA, 16).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_INDIZIONE", pagina);
					}
				}

				// Tipo di appalto
				Long tipoApp = (Long) SqlManager.getValueFromVectorParam(datiW9GARA, 2).getValue();
				if (tipoApp == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_APP", pagina);
				}

				// CIG Accordo Quadro
				if (tipoApp != null && ((new Long(2)).equals(tipoApp) || (new Long(11)).equals(tipoApp))) {
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 17).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CIG_ACCQUADRO", pagina);
					}
				}

				// E' prevista la pubblicazione del bando
				/*String previstaPubblicazBando = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 18).getValue();
				if (previstaPubblicazBando == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "PREV_BANDO", pagina);
				}*/

				// L'importo della gara e' disponibile ?
				String impdis = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 3).getValue();
				
				// Importo della gara
				if (impdis != null && "1".equals(impdis)) {
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 4).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_GARA", pagina);
					}
				}

				// Numero totale dei lotti - Campo calcolato non e' necessario
				// controllare

				// RUP - Responsabile unico del procedimento
				String rup = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 10).getValue();
				if (rup == null) {
					String messaggio = "E\' obbligatorio indicare il responsabile unico del procedimento";
					listaControlli.add(((new Object[] { "E", "Responsabile unico del procedimento", "Responsabile unico del procedimento", messaggio })));
				} else {
					this.validazioneTecnico(listaControlli, "Responsabile unico del procedimento", rup);
				}

				// Ordinanza ricostruzione alluvione Lunigiana ed Elba?
				if (SqlManager.getValueFromVectorParam(datiW9GARA, 21).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "RIC_ALLUV", pagina);
				}

				// Stazione appaltante
				pagina = "Stazione appaltante";
				String codein = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 6).getValue();
				if (codein == null) {
					String messaggio = "E\' obbligatorio indicare la stazione appaltante";
					listaControlli.add(((new Object[] { "E", pagina, "Stazione appaltante", messaggio })));
				} else {
					this.validazioneStazioneAppaltante(listaControlli, "Stazione appaltante", codein);
				}

				// Centro di costo
				pagina = "Centro di costo";
				Long idcc = (Long) SqlManager.getValueFromVectorParam(datiW9GARA, 7).getValue();
				if (idcc == null) {
					String messaggio = "E\' obbligatorio indicare il centro di costo";
					listaControlli.add(((new Object[] { "E", pagina, "Centro di costo", messaggio })));
				} else {
					this.validazioneCentroCosto(listaControlli, "Centro di costo", idcc);
				}

				// La stazione appaltante agisce per conto di altro soggetto ?
				pagina = "Soggetto per cui agisce la stazione appaltante";
				String flagSaAgente = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 8).getValue();
				if (flagSaAgente == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_SA_AGENTE", pagina);
				}

				// Stazione appaltante agente
				if (flagSaAgente != null && "1".equals(flagSaAgente)) {
					// Tipologia della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 9).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPOLOGIA_SA", pagina);
					}

					// Denominazione della stazione appaltante agente
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 11).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DENOM_SA_AGENTE", pagina);
					}

					// Codice fiscale della stazione appaltante agente
					String cfSaAgente = (String) SqlManager.getValueFromVectorParam(datiW9GARA, 12).getValue();
					if (!isStringaValorizzata(cfSaAgente)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CF_SA_AGENTE", pagina);
					} else {
						if ((!UtilityFiscali.isValidCodiceFiscale(cfSaAgente)) && (!UtilityFiscali.isValidPartitaIVA(cfSaAgente))) {
							listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CF_SA_AGENTE"), "Il campo non rispetta il formato previsto" })));
						}
					}

					// Tipologia procedura (nel caso agisca per conto di altri)
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 13).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOLOGIA_PROCEDURA", pagina);
					}

					// La centrale di committenza provvede alla stipula?
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 14).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FLAG_CENTRALE_STIPULA", pagina);
					}
				}

				/*
				pagina = "Bando";
				if (previstaPubblicazBando != null && "1".equals(previstaPubblicazBando)) {
					// Data pubblicazione del bando sulla GURI
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 19).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DGURI", pagina);
					}

					// Data scadenza
					if (SqlManager.getValueFromVectorParam(datiW9GARA, 20).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DSCADE", pagina);
					}

					Long conteggio = (Long) this.sqlManager.getObject("select count(*) from w9docgara where codgara = ? and file_allegato is not null", new Object[] { codgara });
					if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
						String messaggio = "Indicare almeno un file PDF per la pubblicazione sul sito della Regione";
						listaControlli.add(((new Object[] { "E", "Bando", "Bando", messaggio })));
					}
				}
				*/

				// Controllo dei dati di pubblicita' della gara
				try {
					pagina = "Pubblicit&agrave; gara";
					this.validazioneW9PUBB(codgara, listaControlli, pagina);
				} catch (SQLException e) {
					throw new GestoreException("Errore nella lettura delle informazioni relative alla gara", "validazione988", e);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla gara", "validazione988", e);
		}

		// Controllo dei dati dei lotti associata alla gara
		try {
			List<?> datiW9LOTT = null;
			if (codlotto != null) {
				datiW9LOTT = this.sqlManager.getListVector("select codlott from w9lott where codgara = ? and codlott = ?", new Object[] { codgara, codlotto });
			} else {
				datiW9LOTT = this.sqlManager.getListVector("select codlott from w9lott where codgara = ?", new Object[] { codgara });
			}

			if (datiW9LOTT != null && datiW9LOTT.size() > 0) {
				for (int i = 0; i < datiW9LOTT.size(); i++) {
					List<Object> listaControlliW9LOTT = new Vector<Object>();
					Long codlott = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT.get(i), 0).getValue();
					this.validazioneLotto(codgara, codlott, listaControlliW9LOTT, profiloAttivo);

					if (!listaControlliW9LOTT.isEmpty()) {
						String titolo = "Dati del lotto n. " + codlott.toString();
						this.setTitolo(listaControlli, titolo);
						for (int j = 0; j < listaControlliW9LOTT.size(); j++) {
							listaControlli.add(listaControlliW9LOTT.get(j));
						}
					}
				}
			} else {
				String messaggio = "Non esiste alcun lotto associato alla gara";
				listaControlli.add(((new Object[] { "E", "Lotto", "Lotto", messaggio })));
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative ai lotti", "validazione988", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione13: fine metodo");
		}
	}

	
	/**
	 * Validazioni del flusso pubblicazione documenti.
	 * 
	 * @param codiceGara
	 * @param numeroPubblicazione
	 * @param listaControlli
	 * @param profiloAttivo
	 * @throws GestoreException
	 */
	private void validazione901(Long codiceGara, Long numeroPubblicazione, List<Object> listaControlli,
	    String profiloAttivo) throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("validazione901: inizio metodo");
    }
    
    String nomeTabella = "W9PUBBLICAZIONI";
    String pagina = "Dati generali";
    
    try {
      List<?> datiPubblicazione = this.sqlManager.getVector(
          "select TIPDOC, DESCRIZ, DATAPUBB from W9PUBBLICAZIONI where CODGARA=? and NUM_PUBB=?",
          new Object[] { codiceGara, numeroPubblicazione });

      if (datiPubblicazione != null && datiPubblicazione.size() > 0) {

        // Tipologia pubblicazione
        Long tipoPubblicazione = (Long) SqlManager.getValueFromVectorParam(datiPubblicazione, 0).getValue();
        if (tipoPubblicazione == null) {
          this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPDOC", pagina);
        } else {
          if (tipoPubblicazione.longValue() == 99) {
            // Descrizione pubblicazione
            if (SqlManager.getValueFromVectorParam(datiPubblicazione, 1).getValue() == null) {
              this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCRIZ", pagina);
            }
          }
        }

        // Eventuali al campi
        
        // Documenti della pubblicazione
        Long conteggio = (Long) this.sqlManager.getObject(
            "select count(*) from W9DOCGARA where CODGARA=? and NUM_PUBB=?", 
            new Object[] { codiceGara, numeroPubblicazione });
        if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
          //String messaggio = "Indicare almeno un file documento per la pubblicazione sul sito della Regione";
          String messaggio = "Allegare almeno un file del documento relativo all'atto oppure indicare almeno una URL relativa al documento stesso";
          listaControlli.add(((new Object[] { "E", pagina, "FILE_ALLEGATO", messaggio })));
        } else {
          // Lista dei documenti senza File allegato e senza URL e
          // dei documenti con File allegato e con URL.
          List<?>  listaDocumenti = this.sqlManager.getListHashMap(
              "select NUMDOC, TITOLO from W9DOCGARA " +
              " where (FILE_ALLEGATO is null and url is null) or " +
                    " (FILE_ALLEGATO is not null and url is not null)" +
              " and CODGARA=? and NUM_PUBB=?",
              new Object[] { codiceGara, numeroPubblicazione } );
          
          if (listaDocumenti != null && listaDocumenti.size() > 0) {
            String messaggio = "In ogni documento indicare un file PDF o l'URL del file stesso e non entrambi";
            listaControlli.add(((new Object[] { "E", pagina, "FILE_ALLEGATO", messaggio })));
          }
        }
        
        pagina = "Lotti";
        Long conteggio1 = (Long) this.sqlManager.getObject(
            "select count(*) from W9PUBLOTTO where CODGARA=? and NUM_PUBB=?",
            new Object[] { codiceGara, numeroPubblicazione });
        if (conteggio1 == null || (conteggio1 != null && (new Long(0)).equals(conteggio1))) {
          String messaggio = "Associare la pubblicazione ad almeno un lotto";
          listaControlli.add(((new Object[] { "E", "", "Pubblicazione", messaggio })));
        }
        
      }
    } catch (SQLException e) {
      throw new GestoreException("Errore nella lettura delle informazioni relative alla pubblicazione dei documenti", "validazione901", e);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazione901: fine metodo");
    }
	}
	
	/**
	 * Controllo dei dati per l'invio dei dati dell'avviso (codice 989).
	 * 
	 * @param codein
	 *            Codice stazione appaltante
	 * @param idAvviso
	 *            Id Avviso
	 * @param codSistema
	 *            Codice Sistema
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione989(String codein, Long idavviso, Long codSistema, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazione989: inizio metodo");
		}

		String nomeTabella = "AVVISO";
		String pagina = "Pubblicazione avvisi";

		if (idavviso == null) {
			this.addCampoObbligatorio(listaControlli, nomeTabella, "IDAVVISO", pagina);
		}

		try {
			List<?> datiAvviso = this.sqlManager.getVector(
				"select tipoavv, dataavv, descri from avviso where codein = ? and idavviso = ? and codsistema=?",
			    new Object[] { codein, idavviso, codSistema });

			if (datiAvviso != null && datiAvviso.size() > 0) {
				// Tipologia avviso
				if (SqlManager.getValueFromVectorParam(datiAvviso, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOAVV", pagina);
				}

				// Data avviso
				if (SqlManager.getValueFromVectorParam(datiAvviso, 1).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DATAAVV", pagina);
				}

				// Descrizione avviso
				if (SqlManager.getValueFromVectorParam(datiAvviso, 2).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCRI", pagina);
				}

				Long conteggio = (Long) this.sqlManager.getObject(
						"select count(*) from W9DOCAVVISO where codein = ? and idavviso = ? and (file_allegato is not null OR url is not null)",
						new Object[] { codein, idavviso });
				if (conteggio == null || (conteggio != null && (new Long(0)).equals(conteggio))) {
					String messaggio = "Indicare un file PDF per la pubblicazione dell'avviso";
					listaControlli.add(((new Object[] { "E", "Pubblicazione", "Pubblicazione", messaggio })));
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'avviso", "validazione989", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione989: inizio metodo");
		}
	}

	/**
	 * Controllo dei dati per l'invio dei dati dell'avviso (codice 989).
	 * 
	 * @param codein
	 *            Codice stazione appaltante
	 * @param idAvviso
	 *            Id Avviso
	 * @param codSistema
	 *            Codice Sistema
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione989RegioneMarche(String codein, Long idavviso, Long codSistema, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazione989RegioneMarche: inizio metodo");
		}

		List<?> controlli;
		String sqlControlli, valore, messaggio;
		int codiceControllo;
		try {
			String pagina = "";
			// recupero i controlli da effettuare per l'invio dell'esitoa
			sqlControlli = "SELECT codcontrollo, tipocontrollo, msgcontrollo FROM w9controlli " + "WHERE (codcontrollo = 2001 OR codcontrollo = 2002 OR codcontrollo = 2003 OR codcontrollo = 2004) "
					+ "AND tipocontrollo is not null AND tipocontrollo <> ''";
			controlli = this.sqlManager.getListVector(sqlControlli, new Object[] {});

			String selectAVVISO = "select tipoavv, dataavv, descri " + " from avviso where codein = ? and idavviso = ? and codSistema=?";

			DataColumnContainer avviso = new DataColumnContainer(this.sqlManager, "AVVISO", selectAVVISO, new Object[] { codein, idavviso, codSistema });

			if (avviso != null) {
				pagina = "Pubblicazione avvisi";

				if (controlli != null && controlli.size() > 0) {
					for (int i = 0; i < controlli.size(); i++) {
						// verifico il controllo da effettuare
						valore = SqlManager.getValueFromVectorParam(controlli.get(i), 1).getStringValue();
						if (valore != null && !valore.equals("")) {
							codiceControllo = new Integer(SqlManager.getValueFromVectorParam(controlli.get(i), 0).getStringValue());
							messaggio = SqlManager.getValueFromVectorParam(controlli.get(i), 2).getStringValue();
							switch (codiceControllo) {
							case 2001:
								// Tipologia avviso obbligatoria
								if (avviso.getLong("tipoavv") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 2002:
								// Data avviso obbligatoria
								if (avviso.getData("dataavv") == null) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 2003:
								// Descrizione avviso obbligatoria
								if (!isStringaValorizzata(avviso.getString("descri"))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							case 2004:
								// File avviso obbligatorio
								Long allegato = (Long) this.sqlManager.getObject("select count(*) from avviso where codein=? and idavviso=?" + "and codsistema=? and file_allegato is not null",
										new Object[] { codein, idavviso, codSistema });
								if (allegato == null || (allegato != null && (new Long(0)).equals(allegato))) {
									this.addAlert(listaControlli, valore, pagina, messaggio);
								}
								break;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'avviso", "validazione989RegioneMarche", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazione989RegioneMarche: inizio metodo");
		}
	}

	/**
	 * Controllo dati per i programmi triennali per lavori.
	 * 
	 * @param contri
	 *            contri
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione992(Long contri, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione992: inizio metodo");
		}

		String nomeTabella = "PIATRI";
		String pagina = "Programma triennale lavori";

		try {
			String selectPIATRI = "select " + " anntri, " // 0
					+ " respro, " // 1
					+ " dv1tri, " // 2
					+ " dv2tri, " // 3
					+ " dv3tri, " // 4
					+ " im1tri, " // 5
					+ " im2tri, " // 6
					+ " im3tri, " // 7
					+ " mu1tri, " // 8
					+ " mu2tri, " // 9
					+ " mu3tri, " // 10
					+ " pr1tri, " // 11
					+ " pr2tri, " // 12
					+ " pr3tri, " // 13
					+ " al1tri, " // 14
					+ " al2tri, " // 15
					+ " al3tri, " // 16
					+ " cenint  " // 17
					+ " from piatri where contri = ?";

			List<?> datiPIATRI = this.sqlManager.getVector(selectPIATRI, new Object[] { contri });

			if (datiPIATRI != null && datiPIATRI.size() > 0) {

				// Anno di inizio del programma triennale
				Long anntri = (Long) SqlManager.getValueFromVectorParam(datiPIATRI, 0).getValue();
				if (anntri == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ANNTRI", pagina);
				}

				// Responsabile del programma
				String respro = (String) SqlManager.getValueFromVectorParam(datiPIATRI, 1).getValue();
				if (respro == null) {
					String messaggio = "E\' obbligatorio indicare il responsabile del programma triennale";
					listaControlli.add(((new Object[] { "E", "Responsabile del programma", "Responsabile del programma", messaggio })));
				} else {
					this.validazioneTecnico(listaControlli, "Responsabile del programma", respro);
				}

				// Ufficio intestatario - Stazione appaltante
				pagina = "Stazione appaltante";
				String cenint = (String) SqlManager.getValueFromVectorParam(datiPIATRI, 17).getValue();
				if (cenint == null) {
					String messaggio = "E\' obbligatorio indicare la stazione appaltante";
					listaControlli.add(((new Object[] { "E", pagina, "Stazione appaltante", messaggio })));
				} else {
					this.validazioneStazioneAppaltante(listaControlli, "Stazione appaltante", cenint);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al programma triennale lavori", "validazione992", e);
		}

		// Controllo degli importi
		pagina = "Importi";

		String listaCampi[] = new String[21];
		listaCampi[0] = "DV1TRI";
		listaCampi[1] = "DV2TRI";
		listaCampi[2] = "DV3TRI";
		listaCampi[3] = "IM1TRI";
		listaCampi[4] = "IM2TRI";
		listaCampi[5] = "IM3TRI";
		listaCampi[6] = "MU1TRI";
		listaCampi[7] = "MU2TRI";
		listaCampi[8] = "MU3TRI";
		listaCampi[9] = "PR1TRI";
		listaCampi[10] = "PR2TRI";
		listaCampi[11] = "PR3TRI";
		listaCampi[12] = "AL1TRI";
		listaCampi[13] = "AL2TRI";
		listaCampi[14] = "AL3TRI";
		listaCampi[15] = "BI1TRI";
		listaCampi[16] = "BI2TRI";
		listaCampi[17] = "BI3TRI";
		listaCampi[18] = "TO1TRI";
		listaCampi[19] = "TO2TRI";
		listaCampi[20] = "TO3TRI";

		try {
			String selectPIATRI = "select " + listaCampi[0] + ", " + listaCampi[1] + ", " + listaCampi[2] + ", " + listaCampi[3] + ", " + listaCampi[4] + ", " + listaCampi[5] + ", " + listaCampi[6]
					+ ", " + listaCampi[7] + ", " + listaCampi[8] + ", " + listaCampi[9] + ", " + listaCampi[10] + ", " + listaCampi[11] + ", " + listaCampi[12] + ", " + listaCampi[13] + ", "
					+ listaCampi[14] + ", " + listaCampi[15] + ", " + listaCampi[16] + ", " + listaCampi[17] + ", " + listaCampi[18] + ", " + listaCampi[19] + ", " + listaCampi[20]
					+ " from piatri where contri = ?";

			List<?> datiPIATRI = this.sqlManager.getVector(selectPIATRI, new Object[] { contri });

			if (datiPIATRI != null && datiPIATRI.size() > 0) {
				String messaggio = "L\'importo &egrave; pari a zero: si &egrave; sicuri del valore indicato?";

				for (int i = 0; i < 21; i++) {
					Double importo = (Double) SqlManager.getValueFromVectorParam(datiPIATRI, i).getValue();
					if (importo != null && importo.doubleValue() == 0) {
						this.addAvviso(listaControlli, nomeTabella, listaCampi[i], "W", pagina, messaggio);
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al programma triennale", "validazione992", e);
		}

		// Controllo dei dati degli interventi associati al programma triennale
		// lavori
		try {
			List<?> datiINTTRI = this.sqlManager.getListVector("select conint, nprogint from inttri where contri = ? order by nprogint", new Object[] { contri });

			if (datiINTTRI != null && datiINTTRI.size() > 0) {
				for (int i = 0; i < datiINTTRI.size(); i++) {
					List<Object> listaControlliINTTRI = new Vector<Object>();
					Long conint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(i), 0).getValue();
					Long nprogint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(i), 1).getValue();
					this.validazioneInterventoLavori(contri, conint, listaControlliINTTRI);

					if (!listaControlliINTTRI.isEmpty()) {
						String titolo = "Dati dell\'intervento n. " + nprogint.toString();
						this.setTitolo(listaControlli, titolo);
						for (int j = 0; j < listaControlliINTTRI.size(); j++) {
							listaControlli.add(listaControlliINTTRI.get(j));
						}
					}
				}
			} else {
				listaControlli.add(((new Object[] { "E", "Intervento", "Intervento", "Non esiste alcun intervento associato al programma triennale" })));
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative agli interventi", "validazione992", e);
		}

		// Controllo dei Lavori in economia

		// Validazione Lavori in economia (ECOTRI)
		String selectLavoriInEconomia = "select CONECO, DESCRI, STIMA " + "FROM ECOTRI where CONTRI = ? ORDER BY CONECO ASC";

		try {
			List<?> datiLavoriInEconomia = this.sqlManager.getListVector(selectLavoriInEconomia, new Object[] { contri });

			if (datiLavoriInEconomia.size() > 0) {
				nomeTabella = "ECOTRI";

				for (int ja = 0; ja < datiLavoriInEconomia.size(); ja++) {
					boolean addMsgControllo = false;
					Long numeroLavEco = null;
					Vector<?> lavoroInEconomia = (Vector<?>) datiLavoriInEconomia.get(ja);
					List<Object> listaControlliECOTRI = new Vector<Object>();

					if (SqlManager.getValueFromVectorParam(lavoroInEconomia, 0).getValue() == null) {
						this.addCampoObbligatorio(listaControlliECOTRI, nomeTabella, "CONECO", pagina);
						addMsgControllo = true;
					} else {
						numeroLavEco = (Long) SqlManager.getValueFromVectorParam(lavoroInEconomia, 0).getValue();
					}
					if (SqlManager.getValueFromVectorParam(lavoroInEconomia, 1).getValue() == null) {
						this.addCampoObbligatorio(listaControlliECOTRI, nomeTabella, "DESCRI", pagina);
						addMsgControllo = true;
					}
					if (SqlManager.getValueFromVectorParam(lavoroInEconomia, 2).getValue() == null) {
						this.addCampoObbligatorio(listaControlliECOTRI, nomeTabella, "STIMA", pagina);
						addMsgControllo = true;
					}

					if (addMsgControllo) {
						String titolo = null;
						if (numeroLavEco == null) {
							titolo = "Dati dei lavori in economia ";
						} else {
							titolo = "Dati dei lavori in economia n. " + numeroLavEco.toString();
						}
						this.setTitolo(listaControlli, titolo);
						for (int j = 0; j < listaControlliECOTRI.size(); j++) {
							listaControlli.add(listaControlliECOTRI.get(j));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative ai lavori in economia", "validazioneInterventoLavori", e);
		}

		// File allegato
		/*
		 * try { Long conteggio; conteggio = (Long) this.sqlManager.getObject(
		 * "select count(*) from piatri where contri = ? and file_allegato is not null"
		 * , new Object[] { contri }); if (conteggio == null || (conteggio !=
		 * null && (new Long(0)).equals(conteggio))) { String messaggio =
		 * "Indicare un file PDF per la pubblicazione sul sito della Regione";
		 * listaControlli.add(((new Object[] { "E", "Pubblicazione",
		 * "Pubblicazione", messaggio }))); } } catch (SQLException e) { throw
		 * new GestoreException("Errore nella controllo del file PDF",
		 * "validazione992", e); }
		 */

		if (logger.isDebugEnabled()) {
			logger.debug("validazione992: inizio metodo");
		}
	}

	/**
	 * Validazione del programma biennale degli acquisti.
	 * 
	 * @param contri
	 * @param listaControlli
	 * @throws GestoreException
	 */
	private void validazione981(Long contri, List<Object> listaControlli) throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("validazione981: inizio metodo");
    }
    
    try {
      this.tabControlliManager.eseguiControlliPianoBiennaleAcquisti(contri, "PUBB_SCP", listaControlli);
    } catch (SQLException e) {
      throw new GestoreException("Errore nella lettura delle informazioni relative al programma biennale acquisti",
          "validazione981", e);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazione981: fine metodo");
    }
	}
  
	/**
	 * Validazione del programma triennale delle opere pubbliche.
	 * 
	 * @param contri
	 * @param listaControlli
	 * @throws GestoreException
	 */
	private void validazione982(Long contri, List<Object> listaControlli) throws GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("validazione982: inizio metodo");
    }

    try {
      this.tabControlliManager.eseguiControlliPianoTriennaleOperePubbliche(contri, "PUBB_SCP", listaControlli);
    } catch (SQLException e) {
      throw new GestoreException("Errore nella lettura delle informazioni relative al programma triennale delle opere pubbliche",
          "validazione982", e);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazione982: fine metodo");
    }
  }
	
	
	/**
	 * Controllo dati per i programmi annuali forniture e servizi.
	 * 
	 * @param contri
	 *            contri
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazione991(Long contri, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazione991: inizio metodo");
		}

		String nomeTabella = "PIATRI";
		String pagina = "Programma annuale forniture e servizi";
		try {
			List<?> datiPIATRI = this.sqlManager.getVector("select id, anntri, respro, CENINT from piatri where contri = ?", new Object[] { contri });

			if (datiPIATRI != null && datiPIATRI.size() > 0) {
				// Identificativo
				if (SqlManager.getValueFromVectorParam(datiPIATRI, 0).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID", pagina);
				}

				// Anno del programma annuale
				Long anntri = (Long) SqlManager.getValueFromVectorParam(datiPIATRI, 1).getValue();
				if (anntri == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ANNTRI", pagina);
				}

				// Responsabile del programma
				String respro = (String) SqlManager.getValueFromVectorParam(datiPIATRI, 2).getValue();
				if (respro == null) {
					listaControlli.add(((new Object[] { "E", "Responsabile del programma", "Responsabile del programma", "E\' obbligatorio indicare il responsabile del programma annuale" })));
				} else {
					this.validazioneTecnico(listaControlli, "Responsabile del programma", respro);
				}

				// Ufficio intestatario
				// Stazione appaltante
				pagina = "Stazione appaltante";
				String codein = (String) SqlManager.getValueFromVectorParam(datiPIATRI, 3).getValue();
				if (codein == null) {
					String messaggio = "E\' obbligatorio indicare la stazione appaltante";
					listaControlli.add(((new Object[] { "E", pagina, "Stazione appaltante", messaggio })));
				} else {
					this.validazioneStazioneAppaltante(listaControlli, "Stazione appaltante", codein);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al programma annuale", "validazione991", e);
		}

		// Controllo degli importi
		try {

			String listaCampi[] = new String[5];
			listaCampi[0] = "AL1TRI";
			listaCampi[1] = "BI1TRI";
			listaCampi[2] = "RG1TRI";
			listaCampi[3] = "IMPRFS";
			listaCampi[4] = "TO1TRI";

			List<?> datiPIATRI = this.sqlManager.getVector("select " + listaCampi[0] + ", " + listaCampi[1] + ", " + listaCampi[2] + ", " + listaCampi[3] + ", " + listaCampi[4]
					+ " from piatri where contri = ?", new Object[] { contri });

			if (datiPIATRI != null && datiPIATRI.size() > 0) {

				for (int i = 0; i < 5; i++) {
					Double importo = (Double) SqlManager.getValueFromVectorParam(datiPIATRI, i).getValue();
					if (importo != null && importo.doubleValue() == 0) {
						this.addAvviso(listaControlli, nomeTabella, listaCampi[i], "W", pagina, "L\'importo &egrave; pari a zero: si &egrave; sicuri del valore indicato?");
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al programma annuale", "validazione992", e);
		}

		// Controllo dei dati degli interventi associati al programma triennale
		// lavori
		try {
			List<?> datiINTTRI = this.sqlManager.getListVector("select conint from inttri where contri = ?", new Object[] { contri });

			if (datiINTTRI != null && datiINTTRI.size() > 0) {
				for (int i = 0; i < datiINTTRI.size(); i++) {
					List<Object> listaControlliINTTRI = new Vector<Object>();
					Long conint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI.get(i), 0).getValue();
					this.validazioneInterventoFornitureServizi(contri, conint, listaControlliINTTRI);

					if (!listaControlliINTTRI.isEmpty()) {
						String titolo = "Dati dell\'intervento n. " + conint.toString();
						this.setTitolo(listaControlli, titolo);
						for (int j = 0; j < listaControlliINTTRI.size(); j++) {
							listaControlli.add(listaControlliINTTRI.get(j));
						}
					}
				}
			} else {
				listaControlli.add(((new Object[] { "E", "Intervento", "Intervento", "Non esiste alcun intervento associato al programma annuale" })));
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative agli interventi", "validazione992", e);
		}

		// File allegato
		/*
		 * try { pagina = "Pubblicazione sul sito della Regione"; Long
		 * conteggio; conteggio = (Long) this.sqlManager.getObject(
		 * "select count(*) from piatri where contri = ? and file_allegato is not null"
		 * , new Object[] { contri }); if (conteggio == null || (conteggio !=
		 * null && (new Long(0)).equals(conteggio))) { listaControlli.add(((new
		 * Object[] { "E", "Pubblicazione", "Pubblicazione",
		 * "Indicare un file PDF per la pubblicazione sul sito della Regione"
		 * }))); } } catch (SQLException e) { throw new
		 * GestoreException("Errore nella controllo del file PDF",
		 * "validazione991", e); }
		 */

		if (logger.isDebugEnabled()) {
			logger.debug("validazione991: inizio metodo");
		}
	}

	/**
	 * Controllo dati della lista delle imprese aggiudicatarie.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneImpreseAggiudicatarie(Long codgara, Long codlott, Long numappa, List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImpreseAggiudicatarie: inizio metodo");
		}
		try {
			//inizia il controllo su tutte le imprese aggiudicatarie
			String titoloControlliW9AGGI = "Imprese aggiudicatarie / affidatarie";
			List<Object> listaControlliW9AGGICompleta = new Vector<Object>();

			// se non sono nel caso di un accordo quadro
      if (!UtilitySITAT.isSAQ(codgara, this.sqlManager)) {

        boolean erroreNumeroAggiudicatarie = false;
        
        // conto il numero di imprese singole
        String countAggiudicatarieSingole = "select count (distinct codimp) from w9aggi where codgara = ? and codlott = ? and num_appa = ? and id_gruppo is null";
        int numeroAggiudicatarieSingole = ((Long) this.sqlManager.getObject(countAggiudicatarieSingole, new Object[] { codgara, codlott, numappa })).intValue();

        // conto il numero di imprese multiple
        String countAggiudicatarieMultiple = "select count (distinct id_gruppo) from w9aggi where codgara = ? and codlott = ? and num_appa = ? and id_gruppo is not null";
        int numeroAggiudicatarieMultiple = ((Long) this.sqlManager.getObject(countAggiudicatarieMultiple, new Object[] { codgara, codlott, numappa })).intValue();

        // la loro somma deve fare 1
        if((numeroAggiudicatarieMultiple + numeroAggiudicatarieSingole) > 1 ) {
          erroreNumeroAggiudicatarie = true;
        }
        
        if (erroreNumeroAggiudicatarie) {
          listaControlliW9AGGICompleta.add(((new Object[] { "E", " ", " ",
            "Non &egrave; possibile indicare pi&ugrave; di un aggiudicatario" })));
        }
      }

      // recupero tutte le imprese aggiudicatrici ordinate per id_gruppo e id_tipoagg
      String selectNumRaggW9AGGI = "select distinct id_gruppo,id_tipoagg from w9aggi where codgara = ? and codlott = ? and num_appa = ? order by id_gruppo, id_tipoagg";
			List<?> numeriRaggruppamentiW9AGGI = this.sqlManager.getListVector(selectNumRaggW9AGGI, new Object[] { codgara, codlott, numappa });
			boolean isMultiaggiudicatario = numeriRaggruppamentiW9AGGI.size()>1;
			for (int j = 0; j < numeriRaggruppamentiW9AGGI.size(); j++) {

				Long numeroRaggruppamentoAggiudicataria = null;
				Long tipoAggiudicataria = null;

				Vector<?> vettoreNumeroRaggruppamentoAggiudicataria = (Vector<?>) numeriRaggruppamentiW9AGGI.get(j);
				numeroRaggruppamentoAggiudicataria = (Long) ((JdbcParametro) vettoreNumeroRaggruppamentoAggiudicataria.get(0)).getValue();
				tipoAggiudicataria = (Long) ((JdbcParametro) vettoreNumeroRaggruppamentoAggiudicataria.get(1)).getValue();
				
				boolean isImpresaSingola = (new Long(3)).equals(tipoAggiudicataria) || (new Long(4)).equals(tipoAggiudicataria);
				
				String andIdGruppo = "";
				if (isImpresaSingola) {
				  andIdGruppo = "and id_gruppo is null";
				} else {
				  andIdGruppo = "and id_gruppo = ".concat(numeroRaggruppamentoAggiudicataria.toString());
				}
				
				// select utilizzata per l'estrazione dei dati da utilizzare durante il controllo
				String selectW9AGGI = "select w1.num_aggi, " // 0
						+ " w1.id_tipoagg, " // 1
						+ " w1.ruolo, " // 2
						+ " w1.codimp, " // 3
						+ " w1.flag_avvalimento, " // 4
						+ " w1.codimp_ausiliaria, " // 5
						+ " w1.id_gruppo," // 6
						+ " i1.nomest," // 7
						+ " i1.cfimp," //8
						+ " w1.importo_aggiudicazione," //9
						+ " w1.perc_ribasso_agg," //10
						+ " w1.perc_off_aumento" //11
						+ " from w9aggi w1" //tabella riferimetno
						+ " left outer join impr i1 on w1.codimp=i1.codimp" // join per il nome esteso
						+ " where codgara = ? and codlott = ? and num_appa = ? and id_tipoagg = ? " + andIdGruppo;

				// recupero la tipologia di appalto (se presente)
				Long tipoAppalto = (Long) this.sqlManager.getObject("select tipo_app from w9gara where codgara=?", new Object[] { codgara });

				if (tipoAppalto == null) {
					this.addCampoObbligatorio(listaControlliW9AGGICompleta, "W9GARA", "TIPO_APP", "Dati generali della gara");
				} else {

					List<?> datiW9AGGI = this.sqlManager.getListVector(selectW9AGGI, new Object[] { codgara, codlott, numappa, tipoAggiudicataria });

					String titolo = "";
					String nomeTabella = "W9AGGI";
					if (!isImpresaSingola) {
					  titolo = "<b>Identificativo Impresa : Gruppo_R" + numeroRaggruppamentoAggiudicataria + "</b>";
					} 
					if (isImpresaSingola) {
						//se il tipo aggiudicatario è impresa singola o GEIE e esistono più record con questa tipologia nella W9AGGI siamo 
						//nel caso di multiaggiudicatario
						if (datiW9AGGI != null && datiW9AGGI.size() > 1) {
							isMultiaggiudicatario = true;
						}
					}
					if (datiW9AGGI != null && datiW9AGGI.size() > 0) {

						List<Object> listaControlliW9AGGI = new Vector<Object>();
						int numeroImpreseSingole = 0;
						int numeroImpreseMandatarie = 0;
						int numeroImpreseMandanti = 0;

						String cfControllo = null;
						Boolean cfControlloTestMsg = false;
						// inizio a ciclare sulle singole imprese aggiudicatarie estratte
						for (int i = 0; i < datiW9AGGI.size(); i++) {
							boolean isMandante = false;
							Long numAggi = (Long) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 0).getValue();
							String codimpTitle = SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 3).getStringValue();
							String nomEstTitle = SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 7).getStringValue();
							String codFiscImpr = SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 8).getStringValue();
							
							List<Object> listaControlliW9AGGISingolaImpresa = new Vector<Object>();
							String titoloSingolaImpresa = "Impresa : " + nomEstTitle;
							String sottoTitoloSingolaImpresa = null;
							// se mi trovo nel caso di impresa singola pongo l'identificativo impresa come titolo
							// e aggiungo un sottotitolo, in modo da non dover gestire distintamente 
							// il caso aggiudicataria multipla/singola
							if (! isImpresaSingola) {
								titoloSingolaImpresa = "<b>Identificativo Impresa : " + codimpTitle + "</b>";
								sottoTitoloSingolaImpresa = "Impresa : " + nomEstTitle;
							}

							String pagina = "Impresa " + codimpTitle;

							// Tipologia del soggetto aggiudicatario
							Long idTipoAgg = (Long) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 1).getValue();
							if (idTipoAgg == null) {
								this.addCampoObbligatorio(listaControlliW9AGGISingolaImpresa, nomeTabella, "ID_TIPOAGG", pagina);
							} else {
								if (new Long(3).equals(idTipoAgg) || new Long(4).equals(idTipoAgg)) {
									// Impresa singola o GEIE
									numeroImpreseSingole++;
								} else if (new Long(1).equals(idTipoAgg)) {
									// Raggruppamento di imprese (ATI)

									// Ruolo nell'associazione
									Long ruolo = (Long) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 2).getValue();
									if (ruolo == null) {
										this.addCampoObbligatorio(listaControlliW9AGGISingolaImpresa, nomeTabella, "RUOLO", pagina);
									} else {
										if (ruolo.longValue() == 1) {
											numeroImpreseMandatarie++;
										}
										if (ruolo.longValue() == 2) {
											numeroImpreseMandanti++;
											isMandante = true;
										}
									}
								}
							}

							// controllo tipologia per imprese singole (dev'essere 3 o 4 per GEIE)
							if (isImpresaSingola) {
								if (!(new Long(3).equals(idTipoAgg)) && !(new Long(4).equals(idTipoAgg))) {
									listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", "", "", "Le aggiudicatarie senza un gruppo devono avere il tipo impresa singola, in caso di raggruppamento di aggiudicatarie va indicato l'id del gruppo" })));
								}
							}
							
							// Impresa aggiudicataria controlli di validazione sull'impresa
							String codimp = (String) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 3).getValue();
							if (!isStringaValorizzata(codimp)) {
								listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", pagina, "Denominazione dell\'impresa aggiudicataria", "Il campo &egrave; obbligatorio" })));
							} else {
								this.validazioneImpresa(listaControlliW9AGGISingolaImpresa, pagina, codimp, true);
							}

							// Controllo ricorso all'avvalimento ...
							String flagAvvalimento = (String) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 4).getValue();

							// ... e conseguente Impresa ausiliaria
							if (flagAvvalimento != null && (!"".equals(flagAvvalimento) && (!"0".equals(flagAvvalimento)))) {
								String codImpAusiliaria = (String) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 5).getValue();
								if (!isStringaValorizzata(codImpAusiliaria)) {
									listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", "Impresa ausiliaria n. " + numAggi.toString(), "Denominazione dell\'impresa ausiliaria",
											"Il campo &egrave; obbligatorio" })));
								} else {
									this.validazioneImpresa(listaControlliW9AGGISingolaImpresa, "Impresa ausiliaria n. " + numAggi.toString(), codImpAusiliaria, true);
								}
							} else {
								if (flagAvvalimento == null || "".equals(flagAvvalimento)) {
									listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", "Impresa ausiliaria n. " + numAggi.toString(), this.getDescrizioneCampo(nomeTabella, "FLAG_AVVALIMENTO"),
											"Il campo &egrave; obbligatorio" })));
								}
							}
							// controllo solo se multiaggiudicatario importo di aggiudicazione
							if (isMultiaggiudicatario && !isMandante) {
								Double importoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 9).getValue();
								if (importoAggiudicazione==null || importoAggiudicazione <= 0) {
									listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_AGGIUDICAZIONE"),
									"non &egrave; stato inserito l'importo per l'aggiudicatario" })));
								}
								Double ribassoAggiudicazione = (Double) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 10).getValue();
								Double offertaAumento = (Double) SqlManager.getValueFromVectorParam(datiW9AGGI.get(i), 11).getValue();
								if (ribassoAggiudicazione != null && ribassoAggiudicazione> 0 && offertaAumento != null && offertaAumento>0) {
									listaControlliW9AGGISingolaImpresa.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "PERC_RIBASSO_AGG"),
									"Sono stati indicati sia Ribasso aggiudicazione che Offerta in aumento" })));
								}
							}
							// controllo sui codici fiscali
							if (!isImpresaSingola) {
								if (cfControllo == null) {
									cfControllo = codFiscImpr;
								} else {
									if (cfControllo.equals(codFiscImpr)) {
										cfControlloTestMsg = true;
									}
								}
							}
							
							// riempio la sezione degli avvisi per le singole imprese
							if (!listaControlliW9AGGISingolaImpresa.isEmpty()) {
								this.setTitolo(listaControlliW9AGGI, titoloSingolaImpresa);
								if (StringUtils.isNotEmpty(sottoTitoloSingolaImpresa)) {
									this.setTitolo(listaControlliW9AGGI, sottoTitoloSingolaImpresa);	
								}
								for (int ii = 0; ii < listaControlliW9AGGISingolaImpresa.size(); ii++) {
									listaControlliW9AGGI.add(listaControlliW9AGGISingolaImpresa.get(ii));
								}
							}
						}

						// per singolo gruppo vado a controllare la gestione di mandanti e mandatarie (riferito ATI)
						if (numeroImpreseMandatarie == 0 && numeroImpreseMandanti > 0) {
							listaControlliW9AGGI.add(((new Object[] { "E", "", "", "Inserire una mandantaria per il raggruppamento di impresa" })));
						}
						if (numeroImpreseMandatarie == 1 && numeroImpreseMandanti == 0) {
							listaControlliW9AGGI.add(((new Object[] { "E", "", "", "Inserire almeno una mandante per il raggruppamento di impresa" })));
						}
						if (numeroImpreseMandatarie > 1) {
							listaControlliW9AGGI.add(((new Object[] { "E", "", "", "Non \u00E8 possibile indicare pi\u00F9 di una impresa mandataria" })));
						}

						// per singolo gruppo vado a controllare che la tipologia sia uguale per tutte le imprese ad essa legate
						if (!isImpresaSingola) {
							// per singolo gruppo controllo che dove l'id gruppo e' null  avvina tutte valorizzato lo stesso tipo agg
							String countTipoAggiudicatarieMulti = "select count (distinct id_tipoagg) from w9aggi where codgara = ? and codlott = ? and num_appa = ? and id_gruppo = ?";
							int numeroTipoAggiudicatarieMulti = ((Long) this.sqlManager.getObject(countTipoAggiudicatarieMulti, new Object[] { codgara, codlott, numappa, numeroRaggruppamentoAggiudicataria })).intValue();
							if (numeroTipoAggiudicatarieMulti > 1) {
								listaControlliW9AGGI.add(((new Object[] { "E", "", "", "Non \u00E8 possibile per singolo gruppo indicare imprese aggiudicatarie con 'Tipologia del soggetto aggiudicatario' diverso" })));	
							}
						}
						
						// per singolo gruppo vado a controllare la presenza di molteplici cf uguali
						if (cfControlloTestMsg) {
							listaControlliW9AGGI.add(((new Object[] { "E", "", "", "Non \u00E8 possibile per singolo gruppo indicare molteplici imprese aggiudicatarie con identico Codice Fiscale" })));	
						}

						// riempio le sezioni degli avvisi per i gruppi 
						// considerando gruppo quello con idgruppo null(aggiudicatarie singole)
						if (!listaControlliW9AGGI.isEmpty()) {
							if (StringUtils.isNotEmpty(titolo)) {
								this.setTitolo(listaControlliW9AGGICompleta, titolo);
							}
							for (int i = 0; i < listaControlliW9AGGI.size(); i++) {
								listaControlliW9AGGICompleta.add(listaControlliW9AGGI.get(i));
							}
						}
					}
				}
			}

			// NOTA : la forma degli avvisi e' nella forma  
			//		  icona - avviso1 - avviso2 / avviso3  
			// 		  riportati in tabella nelle 3 colonne corrispondenti
			
			if (!listaControlliW9AGGICompleta.isEmpty()) {
				this.setTitolo(listaControlli, titoloControlliW9AGGI);
				for (int i = 0; i < listaControlliW9AGGICompleta.size(); i++) {
					listaControlli.add(listaControlliW9AGGICompleta.get(i));
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alle imprese aggiudicatarie", "validazioneImpreseAggiudicatarie", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImpreseAggiudicatarie: fine metodo");
		}
	}

	/**
	 * Controllo dati per le misure aggiuntive e migliorative per la sicurezza.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneMisureAggiuntiveSicurezza(final Long codgara, final Long codlott, final Long num, final List<Object> listaControlli) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneMisureAggiuntiveSicurezza: inizio metodo");
		}

		try {
			String nomeTabella = "W9MISSIC";
			String pagina = "Misure aggiuntive e migliorative per la sicurezza";

			String sqlW9MISSIC = "select DESCMIS from W9MISSIC where CODGARA=? and CODLOTT=? and NUM_INIZ=?";
			List<?> datiW9MISSIS = this.sqlManager.getListVector(sqlW9MISSIC, new Object[] { codgara, codlott, num });

			List<Object> listaControlliW9MISSIC = new Vector<Object>();

			if (datiW9MISSIS != null && datiW9MISSIS.size() == 1) {
				JdbcParametro objDescMis = SqlManager.getValueFromVectorParam(datiW9MISSIS.get(0), 0);
				if (objDescMis.getValue() == null) {
					this.addCampoObbligatorio(listaControlliW9MISSIC, nomeTabella, "DESCMIS", pagina);
				}
			}

			String sqlW9DOCFASE = "select count(*) from W9DOCFASE where CODGARA=? and CODLOTT=? and NUM_FASE=? " + "and FASE_ESECUZIONE=? ";
			Long numeroDocumenti = (Long) this.sqlManager.getObject(sqlW9DOCFASE + "and TITOLO is not null", new Object[] { codgara, codlott, num, new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA) });
			Long numeroDocumentiConAllegato = (Long) this.sqlManager.getObject(sqlW9DOCFASE + " and FILE_ALLEGATO is not null", new Object[] { codgara, codlott, num,
					new Long(CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA) });

			if (numeroDocumenti.longValue() > 0 && !numeroDocumenti.equals(numeroDocumentiConAllegato)) {
				listaControlliW9MISSIC.add(new Object[] { "E", pagina, "", "Indicare un file allegato per ogni documento definito nella scheda" });
			}

			if (listaControlliW9MISSIC.size() > 0) {
				for (int ui = 0; ui < listaControlliW9MISSIC.size(); ui++) {
					listaControlli.add(listaControlliW9MISSIC.get(ui));
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alle misure " + "aggiuntive e migliorative della sicurezza", "validazioneMisureAggiuntiveSicurezza", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneMisureAggiuntiveSicurezza: fine metodo");
		}
	}

	/**
	 * Controllo dati per gli incarichi professionali.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param num
	 *            Progressivo
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param sezione
	 *            Sezione
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneIncarichiProfessionali(Long codgara, Long codlott, Long num, List<Object> listaControlli, String sezione) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneIncarichiProfessionali: inizio metodo");
		}
		try {
			String selectW9INCA = "select " + " num_inca, " // 0
					+ " id_ruolo, " // 1
					+ " codtec, " // 2
					+ " cig_prog_esterna, " // 3
					+ " data_aff_prog_esterna, " // 4
					+ " data_cons_prog_esterna, " // 5
					+ " SEZIONE " // 6
					+ " from w9inca where codgara = ? and codlott = ? and num = ? and sezione = ? order by num_inca";
			List<?> datiW9INCA = this.sqlManager.getListVector(selectW9INCA, new Object[] { codgara, codlott, num, sezione });

			String nomeTabella = "W9INCA";
			String titolo = "Incarichi professionali";

			if (datiW9INCA != null && datiW9INCA.size() > 0) {

				List<Object> listaControlliW9INCA = new Vector<Object>();
				String pagina = null;

				for (int i = 0; i < datiW9INCA.size(); i++) {
					pagina = "Incaricato n. " + (i + 1);

					// Tipologia del soggetto
					Long idRuolo = (Long) SqlManager.getValueFromVectorParam(datiW9INCA.get(i), 1).getValue();
					if (idRuolo == null) {
						this.addCampoObbligatorio(listaControlliW9INCA, nomeTabella, "ID_RUOLO", pagina);
					}

					// Denominazione
					String codtec = (String) SqlManager.getValueFromVectorParam(datiW9INCA.get(i), 2).getValue();
					if (!isStringaValorizzata(codtec)) {
						listaControlliW9INCA.add(((new Object[] { "E", pagina, "Denominazione", "Il campo &egrave; obbligatorio" })));
					}

					if (idRuolo != null && idRuolo.longValue() == 2) {
						// CIG affidamento incarico esterno
						String str = (String) SqlManager.getValueFromVectorParam(datiW9INCA.get(i), 3).getValue();
						if (!isStringaValorizzata(str)) {
							this.addAvviso(listaControlliW9INCA, nomeTabella, "CIG_PROG_ESTERNA", "W", pagina, "Assenza del CIG dell\'affidamento di incarico esterno di progettazione");
						}

						// Data affidamento incarico esterno
						if (SqlManager.getValueFromVectorParam(datiW9INCA.get(i), 4).getValue() == null) {
							this.addCampoObbligatorio(listaControlliW9INCA, nomeTabella, "DATA_AFF_PROG_ESTERNA", pagina);
						}

						// Data consegna progetto
						if (SqlManager.getValueFromVectorParam(datiW9INCA.get(i), 5).getValue() == null) {
							this.addCampoObbligatorio(listaControlliW9INCA, nomeTabella, "DATA_CONS_PROG_ESTERNA", pagina);
						}

						// ***********************
						// ATTENZIONE: data inferiore alla data richiesta CIG ?
						// ***********************

					}

					// Controllo dei dati dell'anagrafica
					if (codtec != null) {
						this.validazioneTecnico(listaControlliW9INCA, pagina, codtec);
					}
				}

				if (!listaControlliW9INCA.isEmpty()) {
					this.setTitolo(listaControlli, titolo);
					for (int i = 0; i < listaControlliW9INCA.size(); i++) {
						listaControlli.add(listaControlliW9INCA.get(i));
					}
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative agli incaricati", "validazioneIncarichiProfessionali", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneIncarichiProfessionali: fine metodo");
		}
	}

	/**
	 * Controllo dati per una singola impresa identificata dal codice CODIMP.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param pagina
	 *            Pagina
	 * @param codimp
	 *            Codice impresa
	 * @param isAggiudicataria
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneImpresa(List<Object> listaControlli, String pagina, String codimp, boolean isAggiudicataria) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImpresa: inizio metodo");
		}
		try {

			String nomeTabella = "IMPR";
			String locPagina = new String(pagina + " (Archivio)");

			List<?> datiIMPR = this.sqlManager.getVector("select nomest, cfimp, pivimp, nazimp from impr where codimp = ?", new Object[] { codimp });

			String nomeImpresa = "";

			if (datiIMPR != null && datiIMPR.size() > 0) {
				String str = (String) SqlManager.getValueFromVectorParam(datiIMPR, 0).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NOMEST", locPagina);

					listaControlli.add(((new Object[] { "E", locPagina, this.getDescrizioneCampo(nomeTabella, "NOMEST"), "Il campo &egrave; obbligatorio. (Codice Impresa = " + codimp + ")." })));
				} else {
					nomeImpresa = str;
				}

				String cfimp = (String) SqlManager.getValueFromVectorParam(datiIMPR, 1).getValue();
				String pivimp = (String) SqlManager.getValueFromVectorParam(datiIMPR, 2).getValue();
				if ((!isStringaValorizzata(cfimp)) && (!isStringaValorizzata(pivimp))) {
					String descrizione = this.getDescrizioneCampo(nomeTabella, "CFIMP");
					descrizione = descrizione + ", " + this.getDescrizioneCampo(nomeTabella, "PIVIMP");
					listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Valorizzare almeno uno dei due campi (Impresa: " + nomeImpresa + ")" })));
				}

				Long nazimp = (Long) SqlManager.getValueFromVectorParam(datiIMPR, 3).getValue();
				if (nazimp == null) {
					listaControlli.add(((new Object[] { "E", locPagina, this.getDescrizioneCampo(nomeTabella, "NAZIMP"), "Il campo &egrave; obbligatorio. (Impresa: " + nomeImpresa + ")." })));

				} else if (new Long(1).equals(nazimp)) {
					if (isStringaValorizzata(cfimp)) {
						if (!(UtilityFiscali.isValidCodiceFiscale(cfimp) || UtilityFiscali.isValidPartitaIVA(cfimp))) {
							String descrizione = this.getDescrizioneCampo(nomeTabella, "CFIMP");
							listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Il campo non rispetta il formato previsto. (Impresa: " + nomeImpresa + ")" })));
						}
					}

					if (isStringaValorizzata(pivimp)) {
						if (!UtilityFiscali.isValidPartitaIVA(pivimp)) {
							String descrizione = this.getDescrizioneCampo(nomeTabella, "PIVIMP");
							listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Il campo non rispetta il formato previsto. (Impresa: " + nomeImpresa + ")" })));
						}
					}
				}

				// Controllo legale rappresentante
				// Bisogna controllare che esista almeno un legale rappresentante.
				// Successivamente controllare, per ogni tecnico dell'impresa le informazioni obbligatorie
				// previste il controllo viene svolto solo se ci troviamo nel caso di imprese aggiudicatarie
				if (isAggiudicataria) {
					List<?> datiIMPLEG = this.sqlManager.getListVector("select codleg, legini, legfin from impleg where codimp2 = ?", new Object[] { codimp });
					if (datiIMPLEG != null && datiIMPLEG.size() > 0) {
						for (int i = 0; i < datiIMPLEG.size(); i++) {
	
							List<Object> listaControlliIMPLEG = new Vector<Object>();
	
							String codleg = (String) SqlManager.getValueFromVectorParam(datiIMPLEG.get(i), 0).getValue();
	
							locPagina = locPagina + " - Dati del tecnico " + codleg + " (Archivio)";
							this.validazioneTecnicoImpresa(listaControlliIMPLEG, locPagina, codleg, nazimp);
	
							// Controllo date di inizio e fine validita'
							Date legini = (Date) SqlManager.getValueFromVectorParam(datiIMPLEG.get(i), 1).getValue();
							Date legfin = (Date) SqlManager.getValueFromVectorParam(datiIMPLEG.get(i), 2).getValue();
							if (legini != null && legfin != null) {
								if (legfin.getTime() < legini.getTime()) {
									String messaggio = this.getMessaggioConfrontoDate(legfin, ">=", legini, "IMPLEG", "LEGINI", null);
									this.addAvviso(listaControlliIMPLEG, "IMPLEG", "LEGFIN", "E", locPagina, messaggio);
								}
							}
							if (!listaControlliIMPLEG.isEmpty()) {
								for (int j = 0; j < listaControlliIMPLEG.size(); j++) {
									listaControlli.add(listaControlliIMPLEG.get(j));
								}
							}
						}
					} else {
						String messaggio = "Non esiste alcun legale rappresentante associato all'impresa (Impresa: " + nomeImpresa + ")";
						listaControlli.add(((new Object[] { "E", locPagina, "Legale rappresentante dell'impresa", messaggio })));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'impresa", "validazioneImpresa", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImpresa: fine metodo");
		}
	}

	private void validazioneArrayImprese(List<Object> listaControlli, String pagina, String[] codimp, Boolean isAggiudicataria) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("validazioneArrayImprese: inizio metodo");
		}
		try {

			String nomeTabella = "IMPR";
			pagina = "";

			for (int i = 0; i < codimp.length; i++) {
				List<Object> listaControlliIMPR = new Vector<Object>();

				List<?> datiIMPR = this.sqlManager.getVector("select nomest, cfimp, pivimp, nazimp from impr where codimp = ?", new Object[] { codimp[i] });

				String nomeImpresa = "";

				if (datiIMPR != null && datiIMPR.size() > 0) {
					String str = (String) SqlManager.getValueFromVectorParam(datiIMPR, 0).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliIMPR, nomeTabella, "NOMEST", pagina);
					} else {
						nomeImpresa = str;
					}

					String cfimp = (String) SqlManager.getValueFromVectorParam(datiIMPR, 1).getValue();
					String pivimp = (String) SqlManager.getValueFromVectorParam(datiIMPR, 2).getValue();
					if ((!isStringaValorizzata(cfimp)) && (!isStringaValorizzata(pivimp))) {
						String descrizione = this.getDescrizioneCampo(nomeTabella, "CFIMP");
						descrizione = descrizione + ", " + this.getDescrizioneCampo(nomeTabella, "PIVIMP");
						listaControlliIMPR.add(((new Object[] { "E", pagina, descrizione, "Valorizzare almeno uno dei due campi" })));
					}

					Long nazimp = (Long) SqlManager.getValueFromVectorParam(datiIMPR, 3).getValue();

					if (nazimp == null) {
						this.addCampoObbligatorio(listaControlliIMPR, nomeTabella, "NAZIMP", pagina);
					} else if (new Long(1).equals(nazimp)) {
						if (isStringaValorizzata(cfimp)) {
							if (!(UtilityFiscali.isValidCodiceFiscale(cfimp) || UtilityFiscali.isValidPartitaIVA(cfimp))) {
								String descrizione = this.getDescrizioneCampo(nomeTabella, "CFIMP");
								listaControlliIMPR.add(((new Object[] { "E", pagina, descrizione, "Il campo non rispetta il formato previsto" })));
							}
						}

						if (isStringaValorizzata(pivimp)) {
							if (!UtilityFiscali.isValidPartitaIVA(pivimp)) {
								String descrizione = this.getDescrizioneCampo(nomeTabella, "PIVIMP");
								listaControlliIMPR.add(((new Object[] { "E", pagina, descrizione, "Il campo non rispetta il formato previsto" })));
							}
						}
					}

					// Controllo legale rappresentante
					// Bisogna controllare che esista almeno un legale
					// rappresentante.
					// Successivamente controllare, per ogni tecnico
					// dell'impresa
					// le informazioni obbligatorie previste
					if(isAggiudicataria){
						List<?> datiIMPLEG = this.sqlManager.getListVector("select codleg, legini, legfin from impleg where codimp2 = ?", new Object[] { codimp[i] });
	
						if (datiIMPLEG != null && datiIMPLEG.size() > 0) {
							for (int ii = 0; ii < datiIMPLEG.size(); ii++) {
	
								List<Object> listaControlliIMPLEG = new Vector<Object>();
	
								String codleg = (String) SqlManager.getValueFromVectorParam(datiIMPLEG.get(ii), 0).getValue();
	
								pagina = "";
								this.validazioneTecnicoImpresa(listaControlliIMPLEG, pagina, codleg, nazimp);
	
								// Controllo date di inizio e fine validita'
								Date legini = (Date) SqlManager.getValueFromVectorParam(datiIMPLEG.get(ii), 1).getValue();
								Date legfin = (Date) SqlManager.getValueFromVectorParam(datiIMPLEG.get(ii), 2).getValue();
								if (legini != null && legfin != null) {
									if (legfin.getTime() < legini.getTime()) {
										String messaggio = this.getMessaggioConfrontoDate(legfin, ">=", legini, "IMPLEG", "LEGINI", null);
										this.addAvviso(listaControlliIMPLEG, "IMPLEG", "LEGFIN", "E", pagina, messaggio);
									}
								}
								if (!listaControlliIMPLEG.isEmpty()) {
									for (int j = 0; j < listaControlliIMPLEG.size(); j++) {
										this.setTitolo(listaControlliIMPR, "Legale rappresentante n. " + (ii + 1));
										listaControlliIMPR.add(listaControlliIMPLEG.get(j));
									}
								}
							}
						} else {
							String messaggio = "Non esiste alcun legale rappresentante associato all'impresa";
							listaControlliIMPR.add(((new Object[] { "E", pagina, "Legale rappresentante dell'impresa", messaggio })));
						}
					}
				} else {
					// L'impresa e' stata cancellata dall'archivio IMPR.
					listaControlliIMPR.add(((new Object[] { "E", pagina, "Impresa cancellata", "" })));
				}

				if (!listaControlliIMPR.isEmpty()) {

					String titolo = "";
					if (StringUtils.isNotEmpty(nomeImpresa)) {
						titolo = "Impresa: " + nomeImpresa;
					} else {
						titolo = "Codice impresa: " + codimp[i];
					}
					this.setTitolo(listaControlli, titolo);
					for (int j = 0; j < listaControlliIMPR.size(); j++) {
						listaControlli.add(listaControlliIMPR.get(j));
					}
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'impresa", "validazioneArrayImprese", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneArrayImprese: fine metodo");
		}
	}

	/**
	 * Controllo dati per il singole tecnico dell'impresa identificato dal
	 * codice CODTIM.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param pagina
	 *            Pagina
	 * @param codtim
	 *            Codice tecnico dell'impresa
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneTecnicoImpresa(List<Object> listaControlli, String pagina, String codtim, Long nazimp) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneTecnicoImpresa: inizio metodo");
		}
		try {
			String locPagina = pagina + " (Archivio)";

			String selectTEIM = "select COGTIM, NOMETIM, CFTIM, DINTIM, DFITIM from TEIM where codtim = ?";
			List<?> datiTEIM = this.sqlManager.getVector(selectTEIM, new Object[] { codtim });
			if (datiTEIM != null && datiTEIM.size() > 0) {

				String cogtim = (String) SqlManager.getValueFromVectorParam(datiTEIM, 0).getValue();
				if (!isStringaValorizzata(cogtim)) {
					this.addCampoObbligatorio(listaControlli, "TEIM", "COGTIM", locPagina);
				}

				String nometim = (String) SqlManager.getValueFromVectorParam(datiTEIM, 1).getValue();
				if (!isStringaValorizzata(nometim)) {
					this.addCampoObbligatorio(listaControlli, "TEIM", "NOMETIM", locPagina);
				}

				if (nazimp != null && (new Long(1)).equals(nazimp)) {
					String cftim = (String) SqlManager.getValueFromVectorParam(datiTEIM, 2).getValue();
					if (!isStringaValorizzata(cftim)) {
						this.addCampoObbligatorio(listaControlli, "TEIM", "CFTIM", locPagina);
					} else {
						if (!UtilityFiscali.isValidCodiceFiscale(cftim)) {
							String descrizione = this.getDescrizioneCampo("TEIM", "CFTIM");
							listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Il campo non rispetta il formato previsto" })));
						}
					}
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al tecnico dell'impresa", "validazioneTecnicoImpresa", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneTecnicoImpresa: fine metodo");
		}
	}

	/**
	 * Controllo dati per il singolo tecnico identificato da CODTEC.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param pagina
	 *            Pagina
	 * @param codtec
	 *            Codice del tecnico
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneTecnico(List<Object> listaControlli, String pagina, String codtec) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneTecnico: inizio metodo");
		}
		try {
			String locPagina = pagina + " (Archivio)";

			String selectTECNI = "select TELTEC, FAXTEC, CFTEC, CITTEC, EMATEC, COGTEI from TECNI where CODTEC = ?";
			List<?> datiTECNI = this.sqlManager.getVector(selectTECNI, new Object[] { codtec });

			if (datiTECNI != null && datiTECNI.size() > 0) {
				String appoggio = null;
				appoggio = (String) SqlManager.getValueFromVectorParam(datiTECNI, 0).getValue();
				if (isStringaValorizzata(appoggio) && isStringaNumerica(appoggio)) {
					this.addAvviso(listaControlli, "TECNI", "TELTEC", "W", locPagina, "Il campo non &egrave; costituito di soli numeri");
				}

				appoggio = null;
				appoggio = (String) SqlManager.getValueFromVectorParam(datiTECNI, 1).getValue();
				if (isStringaValorizzata(appoggio) && isStringaNumerica(appoggio)) {
					this.addAvviso(listaControlli, "TECNI", "FAXTEC", "W", locPagina, "Il campo non &egrave; costituito di soli numeri");
				}

				String cftec = (String) SqlManager.getValueFromVectorParam(datiTECNI, 2).getValue();
				if (!isStringaValorizzata(cftec)) {
					this.addCampoObbligatorio(listaControlli, "TECNI", "CFTEC", locPagina);
				} else {
					if ((!UtilityFiscali.isValidCodiceFiscale(cftec)) && (!UtilityFiscali.isValidPartitaIVA(cftec))) {
						String descrizione = this.getDescrizioneCampo("TECNI", "CFTEC");
						listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Il campo non rispetta il formato previsto" })));
					}
				}
				appoggio = null;
				appoggio = (String) SqlManager.getValueFromVectorParam(datiTECNI, 3).getValue();
				if (!isStringaValorizzata(appoggio)) {
					this.addAvviso(listaControlli, "TECNI", "CITTEC", "W", locPagina, "Valorizza il campo " + this.getDescrizioneCampo("TECNI", "CITTEC"));
				} else {
				  this.validazioneCodiceIstatComune(appoggio, locPagina, listaControlli);
				}

				appoggio = null;
				appoggio = (String) SqlManager.getValueFromVectorParam(datiTECNI, 5).getValue();
				if (!isStringaValorizzata(appoggio)) {
					this.addCampoObbligatorio(listaControlli, "TECNI", "COGTEI", locPagina);
				}

				appoggio = null;
				appoggio = (String) SqlManager.getValueFromVectorParam(datiTECNI, 4).getValue();
				if (isStringaValorizzata(appoggio)) {
					if (!appoggio.matches("[^@]+@[^@]+.[^@]+")) {
						this.addAvviso(listaControlli, "TECNI", "EMATEC", "W", locPagina, "Il campo non rispetta il formato previsto");
					}

					if (appoggio.length() > 60) {
						this.addAvviso(listaControlli, "TECNI", "EMATEC", "E", locPagina, "L'indirizzo di posta elettronica non deve superare i 60 caratteri");
					}
				}
			} else {
				this.addAvviso(listaControlli, "TECNI", "CODTEC", "E", locPagina, "Tecnico non presente nell'anagrafica");
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al tecnico", "validazioneTecnico", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneTecnico: fine metodo");
		}
	}
  /**
   * @param appoggio
   * @param locPagina
   * @param listaControlli
   * @throws SQLException
   */
  private void validazioneCodiceIstatComune(String appoggio, String locPagina,
      List<Object> listaControlli) throws SQLException {
    if (Pattern.compile("[0-9]{9}").matcher(appoggio).matches()) {
      Long numero = (Long) this.sqlManager.getObject(
          "select count(*) from TABSCHE where TABCOD='S2003' and TABCOD1='09' and TABCOD3=?",
          new Object[] { appoggio });
      if (numero.longValue() != 1) {
        // Codice ISTAT del comune inesistente
        String descrizione = this.getDescrizioneCampo("TECNI", "CITTEC");
        listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Codice ISTAT inesistente" })));
      }
    } else {
      // Codice ISTAT del comune non valido
      String descrizione = this.getDescrizioneCampo("TECNI", "CITTEC");
      listaControlli.add(((new Object[] { "E", locPagina, descrizione, "Codice ISTAT non valido" })));
    }
  }

	/**
	 * Controllo dei dati per una singola stazione appaltante identificata dal
	 * codice CODEIN.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param pagina
	 *            Pagina
	 * @param codein
	 *            Codein
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneStazioneAppaltante(List<Object> listaControlli, String pagina, String codein) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneStazioneAppaltante: inizio metodo");
		}
		String nomeTabella = "UFFINT";
		String locPagina = pagina + " (Archivio)";

		try {

			String selectUFFINT = "select " + " capein, " // 0
					+ " cfein, " // 1
					+ " emaiin, " // 2
					+ " faxein, " // 3
					+ " codcit, " // 4
					+ " tipoin, " // 5
					+ " nciein, " // 6
					+ " nomein, " // 7
					+ " telein, " // 8
					+ " viaein " // 9
					+ " from uffint where codein = ?";

			List<Object> listaControlliUFFINT = new Vector<Object>();

			List<?> datiUFFINT = this.sqlManager.getVector(selectUFFINT, new Object[] { codein });

			if (datiUFFINT != null && datiUFFINT.size() > 0) {
				String str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 7).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlliUFFINT, nomeTabella, "NOMEIN", locPagina);
				}

				//if (SqlManager.getValueFromVectorParam(datiUFFINT, 5).getValue() == null) {
				//	this.addCampoObbligatorio(listaControlliUFFINT, nomeTabella, "TIPOIN", locPagina);
				//}

				// E' una partita IVA?
				String cfein = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 1).getValue();
				if (!isStringaValorizzata(cfein)) {
					this.addCampoObbligatorio(listaControlliUFFINT, nomeTabella, "CFEIN", locPagina);
				} else {
					if (!UtilityFiscali.isValidPartitaIVA(cfein)) {
						String descrizione = this.getDescrizioneCampo(nomeTabella, "CFEIN");
						listaControlli.add(((new Object[] { "W", locPagina, descrizione, "Il campo non rispetta il formato previsto" })));
					}
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 4).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "CODCIT", "W", locPagina,
				      "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "CODCIT")));
				} else {
				  this.validazioneCodiceIstatComune(str, locPagina, listaControlli);
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 0).getValue();
				if (SqlManager.getValueFromVectorParam(datiUFFINT, 0).getValue() == null) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "CAPEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "CAPEIN")));
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 9).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "VIAEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "VIAEIN")));
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 6).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "NCIEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "NCIEIN")));
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 8).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "TELEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "TELEIN")));
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 3).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "FAXEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "FAXEIN")));
				}

				str = (String) SqlManager.getValueFromVectorParam(datiUFFINT, 2).getValue();
				if (!isStringaValorizzata(str)) {
				  this.addAvviso(listaControlliUFFINT, nomeTabella, "EMAEIN", "W", locPagina,
              "Valorizzare il campo ".concat(this.getDescrizioneCampo("UFFINT", "EMAEIN")));
				}

				if (!listaControlliUFFINT.isEmpty()) {
					this.setTitolo(listaControlli, "Dati anagrafici della stazione appaltante");
					for (int i = 0; i < listaControlliUFFINT.size(); i++) {
						listaControlli.add(listaControlliUFFINT.get(i));
					}
				}
			}

		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alla stazione appaltante", "validazioneStazioneAppaltante", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneStazioneAppaltante: fine metodo");
		}
	}

	/**
	 * Validazione del centro di costo identificato dal codice IDCENTRO.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param pagina
	 *            Pagina
	 * @param idcentro
	 *            Id centro di costo
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneCentroCosto(List<Object> listaControlli, String pagina, Long idcentro) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCentroCosto: inizio metodo");
		}
		String nomeTabella = "CENTRICOSTO";
		String locPagina = pagina + "(Archivio)";

		try {
			List<Object> listaControlliCENTRICOSTO = new Vector<Object>();

			String selectCENTRICOSTO = "select codcentro, denomcentro from centricosto where idcentro = ?";

			List<?> datiCENTRICOSTO = this.sqlManager.getVector(selectCENTRICOSTO, new Object[] { idcentro });

			if (datiCENTRICOSTO != null && datiCENTRICOSTO.size() > 0) {
				String str = (String) SqlManager.getValueFromVectorParam(datiCENTRICOSTO, 0).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlliCENTRICOSTO, nomeTabella, "CODCENTRO", locPagina);
				}

				str = (String) SqlManager.getValueFromVectorParam(datiCENTRICOSTO, 1).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlliCENTRICOSTO, nomeTabella, "DENOMCENTRO", locPagina);
				}

				if (!listaControlliCENTRICOSTO.isEmpty()) {
					this.setTitolo(listaControlli, "Dati anagrafici del centro di costo");
					for (int i = 0; i < listaControlliCENTRICOSTO.size(); i++) {
						listaControlli.add(listaControlliCENTRICOSTO.get(i));
					}
				}

			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al centro di costo", "validazioneCentroCosto", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCentroCosto: fine metodo");
		}
	}

	/**
	 * Validazione del singolo intervento di un piano triennale.
	 * 
	 * @param contri
	 *            Contri
	 * @param conint
	 *            Conint
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneInterventoLavori(Long contri, Long conint, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInterventoLavori: inizio metodo");
		}
		String nomeTabella = "INTTRI";
		String pagina = "Intervento";

		try {

			Long anntri = (Long) this.sqlManager.getObject("select anntri from piatri where contri = ? ", new Object[] { contri });

			String selectINTTRI = "select " + " annint, " // 0
					+ " desint, " // 1
					+ " codrup, " // 2
					+ " annrif, " // 3
					+ " sezint, " // 4
					+ " interv, " // 5
					+ " catint, " // 6
					+ " comint, " // 7
					+ " nuts,   " // 8
					+ " codcpv, " // 9
					+ " fiintr, " // 10
					+ " prgint, " // 11
					+ " apcint, " // 12
					+ " urcint, " // 13
					+ " stapro, " // 14
					+ " ailint, " // 15
					+ " tilint, " // 16
					+ " aflint, " // 17
					+ " tflint, " // 18
					+ " totint, " // 19
					+ " TCPINT, " // 20
					+ " ICPINT " // 21
					+ " from INTTRI where CONTRI=? and CONINT=?";

			List<?> datiINTTRI = this.sqlManager.getVector(selectINTTRI, new Object[] { contri, conint });

			if (datiINTTRI != null && datiINTTRI.size() > 0) {
				int indiceDatiINTTRI = 0;

				String annint = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();

				indiceDatiINTTRI++; // indiceDatiINTTRI = 1
				// Descrizione dell'intervento
				String str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DESINT", pagina);
				}

				// ANNINT e' uguale a Si?
				boolean isANNINTuguale1 = "1".equals(annint);

				indiceDatiINTTRI++; // indiceDatiINTTRI = 2
				// Responsabile del programma
				String codrup = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (isANNINTuguale1) {
					if (!isStringaValorizzata(codrup)) {
						listaControlli.add(((new Object[] { "E", "Responsabile unico del procedimento", "Responsabile unico del procedimento",
								"E\' obbligatorio indicare il responsabile unico del procedimento" })));
					} else {
						this.validazioneTecnico(listaControlli, "Responsabile unico del procedimento", codrup);
					}
				} else {
					if (isStringaValorizzata(codrup)) {
						this.validazioneTecnico(listaControlli, "Responsabile unico del procedimento", codrup);
					}
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 3
				// Annualita' di riferimento dell'intervento (1a, 2a o 3a
				// annual.)
				if (SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ANNRIF", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 4
				// Classificazione intervento: tipologia
				str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				String classificIntervento = null;
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "SEZINT", pagina);
				} else {
					classificIntervento = new String(str);
				}

				boolean isSEZINTdiverso0607 = classificIntervento == null || (classificIntervento != null && !"06".equals(classificIntervento) && !"07".equals(classificIntervento));

				boolean isTCPINTdiverso01 = false;
				Object objTCPINT = SqlManager.getValueFromVectorParam(datiINTTRI, 20).getValue();
				if (objTCPINT == null || (objTCPINT != null && !"01".equals(objTCPINT.toString()))) {
					isTCPINTdiverso01 = true;
				}

				// Tipologia apporto di capitale privato
				Double icpint = (Double) SqlManager.getValueFromVectorParam(datiINTTRI, 21).getValue();
				if (icpint != null && icpint.doubleValue() != 0) {
					if (SqlManager.getValueFromVectorParam(datiINTTRI, 20).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TCPINT", pagina);
					}
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 5
				// Classificazione intervento: categoria
				if (isSEZINTdiverso0607 && isTCPINTdiverso01) {
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "INTERV", pagina);
					}
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 6
				// Sotto categoria intervento
				if (isSEZINTdiverso0607) {
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CATINT", pagina);
					}
				}
				if (isANNINTuguale1 && isSEZINTdiverso0607 && isTCPINTdiverso01) {
					indiceDatiINTTRI++; // indiceDatiINTTRI = 7
					// Codice ISTAT
					String comint = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
					indiceDatiINTTRI++; // indiceDatiINTTRI = 8
					// Codice NUTS
					String nuts = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
					if ((!isStringaValorizzata(comint)) && (!isStringaValorizzata(nuts))) {
						String descrizione = this.getDescrizioneCampo(nomeTabella, "COMINT") + ", " + this.getDescrizioneCampo(nomeTabella, "NUTS");
						listaControlli.add(((new Object[] { "E", pagina, descrizione, "Valorizzare il luogo di esecuzione (ISTAT o NUTS)" })));
					}

					if (isStringaValorizzata(comint)) {
					  this.validazioneCodiceIstatComune(comint, pagina, listaControlli);
					}
					
					// Codice CPV
					// str = (String)
					// SqlManager.getValueFromVectorParam(datiINTTRI,
					// 8).getValue();
					// if (! isStringaValorizzata(str)) {
					// this.addCampoObbligatorio(listaControlli, nomeTabella,
					// "CODCPV", pagina);
					// }
				} else {
					indiceDatiINTTRI = indiceDatiINTTRI + 2;
				}

				// Intervento inserito nel piano annuale ?
				if (!isStringaValorizzata(annint)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ANNINT", pagina);
				}
				if (isANNINTuguale1 && isSEZINTdiverso0607 && isTCPINTdiverso01) {
					// Finalita' dell'intervento
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, 10).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "FIINTR", pagina);
					}
					// Priorita' dell'intervento in generale
					if (SqlManager.getValueFromVectorParam(datiINTTRI, 11).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PRGINT", pagina);
					}
					// Problematiche ambientale paesistico : conforme ?
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, 12).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "APCINT", pagina);
					}
					// Problematiche urbanistico territoriale : conforme ?
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, 13).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "URCINT", pagina);
					}
					// Stato Progettazione approvata
					str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, 14).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "STAPRO", pagina);
					}
					// Tempi di esecuzione - Anno inizio lavori
					Long ailint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI, 15).getValue();
					if (ailint == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "AILINT", pagina);
					}
					if (ailint != null && anntri != null) {
						if (ailint.longValue() != anntri.longValue()) {
							String messaggio = "Il valore digitato (" + ailint.toString() + ") deve essere uguale al valore (" + anntri.toString() + ") del campo \""
									+ this.getDescrizioneCampo("PIATRI", "ANNTRI") + "\" del Programma";
							this.addAvviso(listaControlli, nomeTabella, "AILINT", "E", pagina, messaggio);
						}
					}
					// Tempi di esecuzione - Trimestre inizio lavori
					Long tilint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI, 16).getValue();
					if (tilint == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TILINT", pagina);
					}
					if (tilint != null && (tilint.longValue() < 1 || tilint.longValue() > 4)) {
						String messaggio = "Il valore digitato (" + tilint.longValue() + ") deve essere compreso tra 1 e 4";
						this.addAvviso(listaControlli, nomeTabella, "TILINT", "E", pagina, messaggio);
					}
					// Tempi di esecuzione - Anno fine lavori
					Long aflint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI, 17).getValue();
					if (aflint == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "AFLINT", pagina);
					}
					if (aflint != null && anntri != null) {
						if (aflint.longValue() < anntri.longValue()) {
							String messaggio = "Il valore digitato (" + aflint.toString() + ") deve essere maggiore od uguale al valore (" + anntri.toString() + ") del campo \""
									+ this.getDescrizioneCampo("PIATRI", "ANNTRI") + "\" del Programma";
							this.addAvviso(listaControlli, nomeTabella, "AFLINT", "E", pagina, messaggio);
						}
					}
					// Tempi di esecuzione - Trimestre fine lavori
					Long tflint = (Long) SqlManager.getValueFromVectorParam(datiINTTRI, 18).getValue();
					if (tflint == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TFLINT", pagina);
					}
					if (tflint != null && (tflint.longValue() < 1 || tflint.longValue() > 4)) {
						String messaggio = "Il valore digitato (" + tflint.longValue() + ") deve essere compreso tra 1 e 4";
						this.addAvviso(listaControlli, nomeTabella, "TFLINT", "E", pagina, messaggio);
					}
				}

				// Importo totale dell'intervento
				if (isTCPINTdiverso01) {
					if (SqlManager.getValueFromVectorParam(datiINTTRI, 19).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "TOTINT", pagina);
					} else {
						Double importoTotInterv = (Double) SqlManager.getValueFromVectorParam(datiINTTRI, 19).getValue();
						if (importoTotInterv.doubleValue() <= 0) {
							this.addAvviso(listaControlli, nomeTabella, "TOTINT", "W", pagina, "Valorizzare con valore maggiore di zero");
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'intervento", "validazioneInterventoLavori", e);
		}

		// Controllo dei dati degli immobili da trasferire
		try {
			String selectImportiTrasferimentoImmobili = "select im1tri, im2tri, im3tri from inttri where contri = ? and conint = ?";
			List<?> datiImportiTrasferimentoImmobili = this.sqlManager.getVector(selectImportiTrasferimentoImmobili, new Object[] { contri, conint });

			Double im1tri = null;
			if (SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 0).getValue() != null) {
				im1tri = SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 0).doubleValue();
			}
			Double im2tri = null;
			if (SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 1).getValue() != null) {
				im2tri = SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 1).doubleValue();
			}
			Double im3tri = null;
			if (SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 2).getValue() != null) {
				im3tri = SqlManager.getValueFromVectorParam(datiImportiTrasferimentoImmobili, 2).doubleValue();
			}

			double sommaImportiTrasferimentoImmobili = 0;

			if (im1tri != null) {
				sommaImportiTrasferimentoImmobili += im1tri.doubleValue();
			}
			if (im2tri != null) {
				sommaImportiTrasferimentoImmobili += im2tri.doubleValue();
			}
			if (im3tri != null) {
				sommaImportiTrasferimentoImmobili += im3tri.doubleValue();
			}

			String selectIMMTRAI = "select numimm from immtrai where contri = ? and conint = ?";

			List<?> datiIMMTRAI = this.sqlManager.getListVector(selectIMMTRAI, new Object[] { contri, conint });

			if (datiIMMTRAI != null && datiIMMTRAI.size() > 0) {
				if (sommaImportiTrasferimentoImmobili == 0) {
					String messaggio = "Non &egrave; possibile indicare immobili da trasferire se almeno uno degli importi non &egrave; diverso da 0";
					String descrizione = this.getDescrizioneCampo("INTTRI", "IM1TRI");
					descrizione = descrizione + ", " + this.getDescrizioneCampo("INTTRI", "IM2TRI") + ", " + this.getDescrizioneCampo("INTTRI", "IM3TRI");
					listaControlli.add(((new Object[] { "E", "Immobili da trasferire", descrizione, messaggio })));
				} else {
					for (int i = 0; i < datiIMMTRAI.size(); i++) {
						List<Object> listaControlliIMMTRAI = new Vector<Object>();
						Long numimm = (Long) SqlManager.getValueFromVectorParam(datiIMMTRAI.get(i), 0).getValue();
						this.validazioneImmobile(contri, conint, numimm, listaControlliIMMTRAI);

						if (!listaControlliIMMTRAI.isEmpty()) {
							String titolo = "Immobile da trasferire n. " + numimm.toString();
							this.setTitolo(listaControlli, titolo);
							for (int j = 0; j < listaControlliIMMTRAI.size(); j++) {
								listaControlli.add(listaControlliIMMTRAI.get(j));
							}
						}
					}
				}
			} else {
				if (sommaImportiTrasferimentoImmobili > 0) {
					listaControlli.add(((new Object[] { "E", "Immobili da trasferire", "Immobili da trasferire", "Indicare gli immobili da trasferire" })));
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative agli immobili", "validazioneInterventoLavori", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneInterventoLavori: fine metodo");
		}
	}

	/**
	 * Controllo dei dati per l'intervento associato al programma annuale
	 * forniture e servizi.
	 * 
	 * @param contri
	 *            Contri
	 * @param conint
	 *            Conint
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneInterventoFornitureServizi(Long contri, Long conint, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneIntervento: inizio metodo");
		}
		String nomeTabella = "INTTRI";
		String pagina = "Intervento";

		try {

			String selectINTTRI = "select " + " conint, " // 0
					+ " desint, " // 1
					+ " codrup, " // 2
					+ " comint, " // 3
					+ " nuts, " // 4
					+ " codcpv, " // 5
					+ " tipoin, " // 6
					+ " prgint, " // 7
					+ " mesein, " // 8
					+ " mantri, " // 9
					+ " ditint " // 10
					+ " from inttri where contri = ? and conint = ?";

			List<?> datiINTTRI = this.sqlManager.getVector(selectINTTRI, new Object[] { contri, conint });

			if (datiINTTRI != null && datiINTTRI.size() > 0) {

				int indiceDatiINTTRI = 0;

				// Identificativo dell'intervento
				if (SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CONINT", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 1
				// Descrizione dell'intervento
				String str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "DESINT", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 2
				// Responsabile del programma
				String codrup = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (isStringaValorizzata(codrup)) {
					this.validazioneTecnico(listaControlli, "Responsabile unico del procedimento", codrup);
				}
				/*
				 * if (! isStringaValorizzata(codrup)) {
				 * listaControlli.add(((Object) (new Object[] { "E",
				 * "Responsabile unico del procedimento",
				 * "Responsabile unico del procedimento",
				 * "E\' obbligatorio indicare il responsabile unico del procedimento"
				 * }))); } else { this.validazioneTecnico(listaControlli,
				 * "Responsabile unico del procedimento", codrup); }
				 */

				indiceDatiINTTRI++; // indiceDatiINTTRI = 3
				// Codice ISTAT
				String comint = (String) SqlManager.getValueFromVectorParam(datiINTTRI, 3).getValue();
				if (! isStringaValorizzata(comint)) {
				  this.validazioneCodiceIstatComune(comint, pagina, listaControlli);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 4
				// Codice NUTS
				/*
				 * String nuts = (String)
				 * SqlManager.getValueFromVectorParam(datiINTTRI, 4).getValue();
				 * if ((!isStringaValorizzata(comint)) &&
				 * (!isStringaValorizzata(nuts))) { String descrizione =
				 * this.getDescrizioneCampo(nomeTabella, "COMINT") + ", " +
				 * this.getDescrizioneCampo(nomeTabella, "NUTS");
				 * listaControlli.add(((new Object[] { "E", pagina, descrizione,
				 * "Valorizzare uno dei due campi" }))); }
				 * 
				 * if (isStringaValorizzata(comint) &&
				 * isStringaValorizzata(nuts)) { String descrizione =
				 * this.getDescrizioneCampo(nomeTabella, "COMINT"); descrizione
				 * += ", " + this.getDescrizioneCampo(nomeTabella, "NUTS");
				 * listaControlli.add(((new Object[] { "E", pagina, descrizione,
				 * "Valorizzare solo uno dei due campi" }))); }
				 */

				indiceDatiINTTRI++; // indiceDatiINTTRI = 5
				// Codice CPV
				// str = (String) SqlManager.getValueFromVectorParam(datiINTTRI,
				// 5).getValue();
				// if (! isStringaValorizzata(str)) {
				// this.addCampoObbligatorio(listaControlli, nomeTabella,
				// "CODCPV",
				// pagina);
				// }

				indiceDatiINTTRI++; // indiceDatiINTTRI = 6
				// Tipologia dell'intervento
				str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPOIN", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 7
				// Priorita' dell'intervento in generale
				/*
				 * if (SqlManager.getValueFromVectorParam(datiINTTRI,
				 * indiceDatiINTTRI).getValue() == null) {
				 * this.addCampoObbligatorio(listaControlli, nomeTabella,
				 * "PRGINT", pagina); }
				 */

				indiceDatiINTTRI++; // indiceDatiINTTRI = 8
				// Mese dell'anno
				if (SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "MESEIN", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 9
				// E' previsto l'uso di manodopera
				str = (String) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "MANTRI", pagina);
				}

				indiceDatiINTTRI++; // indiceDatiINTTRI = 10
				// Importo disponibilita' finanziaria
				Double ditint = (Double) SqlManager.getValueFromVectorParam(datiINTTRI, indiceDatiINTTRI).getValue();
				if (ditint != null && ditint.doubleValue() == 0) {
					String messaggio = "L\'importo &egrave; pari a zero: si &egrave; sicuri del valore indicato?";
					this.addAvviso(listaControlli, nomeTabella, "DITINT", "W", pagina, messaggio);
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative all'intervento", "validazioneIntervento", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneIntervento: fine metodo");
		}
	}

	/**
	 * Validazione del singolo immobile.
	 * 
	 * @param contri
	 * @param conint
	 * @param numimm
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneImmobile(Long contri, Long conint, Long numimm, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImmobile: inizio metodo");
		}
		String nomeTabella = "IMMTRAI";
		String pagina = "Immobile da trasferire";

		try {
			String selectIMMTRAI = "select " + " numimm, " // 0
					+ " annimm, " // 1
					+ " desimm, " // 2
					+ " proimm, " // 3
					+ " valimm " // 4
					+ " from immtrai where contri = ? and conint = ? and numimm = ?";

			List<?> datiIMMTRAI = this.sqlManager.getVector(selectIMMTRAI, new Object[] { contri, conint, numimm });

			if (datiIMMTRAI != null && datiIMMTRAI.size() > 0) {

				String selectDisponibilita = "select di1int, di2int, di3int from inttri where contri = ? and conint = ?";
				List<?> datiDisponibilita = this.sqlManager.getVector(selectDisponibilita, new Object[] { contri, conint });

				Double di1int = null;
				if (SqlManager.getValueFromVectorParam(datiDisponibilita, 0).getValue() != null) {
					di1int = SqlManager.getValueFromVectorParam(datiDisponibilita, 0).doubleValue();
				}
				Double di2int = null;
				if (SqlManager.getValueFromVectorParam(datiDisponibilita, 1).getValue() != null) {
					di2int = SqlManager.getValueFromVectorParam(datiDisponibilita, 1).doubleValue();
				}
				Double di3int = null;
				if (SqlManager.getValueFromVectorParam(datiDisponibilita, 2).getValue() != null) {
					di3int = SqlManager.getValueFromVectorParam(datiDisponibilita, 2).doubleValue();
				}

				double sommaDisponibilita = 0;

				if (di1int != null) {
					sommaDisponibilita += di1int.doubleValue();
				}
				if (di2int != null) {
					sommaDisponibilita += di2int.doubleValue();
				}
				if (di3int != null) {
					sommaDisponibilita += di3int.doubleValue();
				}

				if (sommaDisponibilita != 0) {
					int indiceDatiIMMTRAI = 0;

					// N. progressivo degli immobili dell'intervento
					if (SqlManager.getValueFromVectorParam(datiIMMTRAI, indiceDatiIMMTRAI).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "NUMIMM", pagina);
					}
					indiceDatiIMMTRAI++; // indiceDatiIMMTRAI = 1
					// Anno
					// if (SqlManager.getValueFromVectorParam(datiIMMTRAI,
					// indiceDatiIMMTRAI).getValue() == null) {
					// this.addCampoObbligatorio(listaControlli, nomeTabella,
					// "ANNIMM", pagina);
					// }
					indiceDatiIMMTRAI++; // indiceDatiIMMTRAI = 2
					// Descrizione dell'immobile
					String str = (String) SqlManager.getValueFromVectorParam(datiIMMTRAI, indiceDatiIMMTRAI).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DESIMM", pagina);
					}
					indiceDatiIMMTRAI++; // indiceDatiIMMTRAI = 3
					// Tipo proprieta' (piena proprieta' o solo diritto di sup.)
					if (SqlManager.getValueFromVectorParam(datiIMMTRAI, indiceDatiIMMTRAI).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "PROIMM", pagina);
					}
					indiceDatiIMMTRAI++; // indiceDatiIMMTRAI = 4
					// Valore stimato dell'immobile
					if (SqlManager.getValueFromVectorParam(datiIMMTRAI, indiceDatiIMMTRAI).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "VALIMM", pagina);
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative agli immobili", "validazioneImmobile", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneImmobile: fine metodo");
		}
	}

	/**
	 * Validazione del singolo lotto identificato dalla chiave Codice Gara
	 * (CODGARA) e Codice Lotto (CODLOTT).
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneLotto(final Long codgara, final Long codlott, final List<Object> listaControlli, String profiloAttivo) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneLotto: inizio metodo");
		}
		String nomeTabella = "W9LOTT";
		String pagina = "";

		try {
			String selectW9LOTT = "select " + " oggetto, " // 0
					+ " nlotto, " // 1
					+ " comcon, " // 2
					+ " descom, " // 3
					+ " cigcom, " // 4
					+ " somma_urgenza, " // 5
					+ " tipo_contratto, " // 6
					+ " flag_ente_speciale, " // 7
					+ " art_e1, " // 8
					+ " art_e2, " // 9
					+ " rup, " // 10
					+ " id_scelta_contraente, " // 11
					+ " id_modo_gara, " // 12
					+ " impdisp, " // 13
					+ " importo_lotto, " // 14
					+ " importo_attuazione_sicurezza, " // 15
					+ " cup, " // 16
					+ " cpv, " // 17
					+ " manod, " // 18
					+ " importo_tot, " // 19
					+ " id_categoria_prevalente, "// 20
					+ " clascat, " // 21
					+ " luogo_istat, " // 22
					+ " luogo_nuts, " // 23
					+ " id_tipo_prestazione, " // 24
					+ " id_scelta_contraente_50, " // 25
					+ " CUPESENTE, " // 26
					+ " CUIINT " // 27
					+ " from w9lott where codgara = ? and codlott = ?";

			int versioneSimog = UtilitySITAT.getVersioneSimog(sqlManager, codgara);
			
			List<?> datiW9LOTT = this.sqlManager.getVector(selectW9LOTT, new Object[] { codgara, codlott });
			if (datiW9LOTT != null && datiW9LOTT.size() > 0) {
				pagina = "Dati generali";

				// Oggetto del lotto
				String str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 0).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "OGGETTO", pagina);
				}

				// Numero del lotto
				Long nlotto = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT, 1).getValue();
				if (nlotto == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "NLOTTO", pagina);
				}
				if (nlotto != null && nlotto.longValue() <= 0) {
					String messaggio = "Il valore digitato deve essere maggiore od uguale a 1";
					this.addAvviso(listaControlli, nomeTabella, "NLOTTO", "E", pagina, messaggio);
				}


				/* Lotto derivante da contratto precedente ?
				String comcon = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 2).getValue();
				if (!isStringaValorizzata(comcon)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "COMCON", pagina);
				}

				if (comcon != null && "1".equals(comcon)) {

					// Motivo del completamento
					if (SqlManager.getValueFromVectorParam(datiW9LOTT, 3).getValue() == null) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "DESCOM", pagina);
					}

					// CIG del contratto da cui deriva il lotto
					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 4).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CIGCOM", pagina);
					}
				} else {
				}*/

				// Somma urgenza ?
				//str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 5).getValue();
				//if (!isStringaValorizzata(str)) {
				//	this.addCampoObbligatorio(listaControlli, nomeTabella, "SOMMA_URGENZA", pagina);
				//}

				// Tipo contratto
				String tipoContratto = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 6).getValue();
				if (!isStringaValorizzata(tipoContratto)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "TIPO_CONTRATTO", pagina);
				}

				// Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06 ?
				//str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 8).getValue();
				//if (!isStringaValorizzata(str)) {
				//	this.addCampoObbligatorio(listaControlli, nomeTabella, "ART_E1", pagina);
				//}

				// Contratto escluso ex art 16, 17, 18 D. Lgs. 163/06 ?
				//str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 9).getValue();
				//if (!isStringaValorizzata(str)) {
				//	this.addCampoObbligatorio(listaControlli, nomeTabella, "ART_E2", pagina);
				//}

				if (tipoContratto != null) {
					Long conteggiow9appaforn = (Long) this.sqlManager.getObject("select count(*) from w9appaforn where codgara = ? and codlott = ? and id_appalto is not null", new Object[] { codgara,
							codlott });

					// Modalita' acquisizione fornitura servizi
					if ("F".equals(tipoContratto) || "S".equals(tipoContratto) || "CS".equals(tipoContratto)) {
						if (conteggiow9appaforn == null || (conteggiow9appaforn != null && (new Long(0)).equals(conteggiow9appaforn))) {
							String messaggio = "Indicare \"Si\" in almeno una modalit&agrave; fra quelle previste";
							listaControlli.add(((new Object[] { "E", "Modalit&agrave; di acquisizione forniture / servizi", "Modalit&agrave; di acquisizione forniture / servizi", messaggio })));
						}
					} /*
					 * else { if (conteggiow9appaforn == null ||
					 * (conteggiow9appaforn != null && (new
					 * Long(0)).equals(conteggiow9appaforn))) {
					 * 
					 * String messaggio =
					 * "Indicare \"Si\" in almeno una modalit&agrave; fra quelle previste"
					 * ; listaControlli.add(((new Object[] { "W",
					 * "Modalit&agrave; di acquisizione forniture / servizi",
					 * "Modalit&agrave; di acquisizione forniture / servizi",
					 * messaggio }))); } }
					 */

					// Tipologia del lavoro
					if ("L".equals(tipoContratto) || "CL".equals(tipoContratto)) {
						Long conteggiow9appalav = (Long) this.sqlManager.getObject(
						    "select count(*) from w9appalav where codgara = ? and codlott = ? and id_appalto is not null", new Object[] {
								codgara, codlott });
						if (conteggiow9appalav == null || (conteggiow9appalav != null && (new Long(0)).equals(conteggiow9appalav))) {
							String messaggio = "Indicare \"Si\" in almeno una tipologia fra quelle previste";
							listaControlli.add(((new Object[] { "E", "Tipologia lavoro", "Tipologia lavoro", messaggio })));
						} else {
							// Il valore 'Manutenzione' non e' piu' valido.
							// Sostituirlo con 'Manutenzione ordinaria' o
							// 'Manutenzione straordinaria'
							List< ? > listaAppaLav = this.sqlManager.getListVector(
							    "select id_appalto from w9appalav where codgara = ? and codlott = ? and id_appalto is not null",
							    new Object[] { codgara, codlott });

							boolean trovatoManutenzione11 = false;
							if (listaAppaLav != null && listaAppaLav.size() > 0) {
								for (int oo = 0; oo < listaAppaLav.size() && !trovatoManutenzione11; oo++) {
									Vector< ? > appaLav = (Vector< ? >) listaAppaLav.get(oo);

									if (appaLav != null && appaLav.size() > 0) {
										Long idAppalto = (Long) SqlManager.getValueFromVectorParam(appaLav, 0).getValue();

										if (new Long(11).equals(idAppalto)) {
											String messaggio = "Il valore 'Manutenzione' non &egrave; pi&ugrave; valido. Sostituirlo " + "con 'Manutenzione ordinaria' o 'Manutenzione straordinaria'";
											listaControlli.add(((new Object[] { "W", "Tipologia lavoro", "Tipologia lavoro", messaggio })));
										}
									}
								}
							}
						}
					}
				}

				// Procedura di scelta del contraente
				Long idSceltaContraente = (Long) SqlManager.getValueFromVectorParam(datiW9LOTT, 11).getValue();
				if (idSceltaContraente == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_SCELTA_CONTRAENTE", pagina);
				}

				// Condizioni
				if (versioneSimog == 1 && (idSceltaContraente != null && (idSceltaContraente.longValue() == 4 || idSceltaContraente.longValue() == 10))) {
					Long conteggiow9cond = (Long) this.sqlManager.getObject(
						"select count(*) from w9cond where codgara = ? and codlott = ? and id_condizione is not null",
						new Object[] { codgara, codlott });
					if (conteggiow9cond == null || (conteggiow9cond != null && (new Long(0)).equals(conteggiow9cond))) {
						String messaggio = "Indicare \"Si\" in almeno una condizione fra quelle previste";
						listaControlli.add(((new Object[] { "E", "Condizioni", "Condizioni", messaggio })));
					}
				}

				// Criterio di aggiudicazione
				// Valorizzare se W3ID_CONDI != 2, 5, 7, 9, 10, 11, 14, 16,17, 18, 23, 24, 28
				Long numeroCondizioni = (Long) this.sqlManager.getObject("select count(*) from W9COND where CODGARA=? and CODLOTT=? "
						+ "and ID_CONDIZIONE in (2, 5, 7, 9, 10, 11, 14, 16, 17, 18, 23, 24, 28)", new Object[] { codgara, codlott });
				boolean isAAQ = UtilitySITAT.isAAQ(codgara, this.sqlManager);
				Long numeroCondizioniNegoziata = (Long) this.sqlManager.getObject("select count(*) from W9COND where CODGARA=? and CODLOTT=? "
						+ "and ID_CONDIZIONE in (34, 35, 36, 37, 39, 41, 42, 43, 44)", new Object[] { codgara, codlott });
				
				//if (numeroCondizioni > 0 && SqlManager.getValueFromVectorParam(datiW9LOTT, indiceDatiW9Lott).getValue() == null) {
				if ((!isAAQ && ((versioneSimog >= 2 && (idSceltaContraente != null && idSceltaContraente.intValue() != 15 && idSceltaContraente.intValue() != 16 && idSceltaContraente.intValue() != 17 && idSceltaContraente.intValue() != 18 && idSceltaContraente.intValue() != 20 && idSceltaContraente.intValue() != 31) && numeroCondizioniNegoziata == 0) 
						|| (versioneSimog == 1 && numeroCondizioni == 0))) && 
				    SqlManager.getValueFromVectorParam(datiW9LOTT, 12).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_MODO_GARA", pagina);
				}

        if (SqlManager.getValueFromVectorParam(datiW9LOTT, 27).getValue() != null) {
          // Controllo del formato del CUIINT (codice unico dell'intervento)
          String cuiint = SqlManager.getValueFromVectorParam(datiW9LOTT, 27).getStringValue();
          if (cuiint.length() == 21 || cuiint.length() == 20) {
            if (StringUtils.isAlphanumeric(cuiint)) {
              // Il CUI deve essere nel formato: L/F/S + PartitaIva + Anno + progressivo a cinque cifre 
              if (cuiint.length() == 21) {
                String partitaIva = StringUtils.substring(cuiint, 1, 12);
                String anno = StringUtils.substring(cuiint, 12, 16);
                String progressivo = StringUtils.substring(cuiint, 16);
                if (! ((cuiint.indexOf("L") == 0 || cuiint.indexOf("F") == 0 || cuiint.indexOf("S") == 0)
                    && (UtilityFiscali.isValidPartitaIVA(partitaIva) 
                    && (StringUtils.isNumeric(anno) && anno.length() == 4 && new Long(anno).longValue() > 2000) 
                        && StringUtils.isNumeric(progressivo)&& progressivo.length() == 5))) {
                  // Errore
                  listaControlli.add(((new Object[] { "E", "Codice CUI", "Codice CUI", "Il codice CUI non &egrave; valido" })));
                }
              }
            } else {
              // Il CUI deve essere nel formato: PartitaIva + Anno + progressivo a cinque cifre
              if (cuiint.length() == 20) {
                String partitaIva = StringUtils.substring(cuiint, 0, 11);
                String anno = StringUtils.substring(cuiint, 12, 15);
                String progressivo = StringUtils.substring(cuiint, 15);
                if ( ! (UtilityFiscali.isValidPartitaIVA(partitaIva) 
                    && (StringUtils.isNumeric(anno) && anno.length() == 4 && new Long(anno).longValue() > 2000) 
                        && StringUtils.isNumeric(progressivo) && progressivo.length() == 5)) {
                  // Errore
                  listaControlli.add(((new Object[] { "E", "Codice CUI", "Codice CUI", "Il codice CUI non &egrave; valido" })));
                }
              } else {
              // Errore
                listaControlli.add(((new Object[] { "E", "Codice CUI", "Codice CUI", "Il codice CUI non &egrave; valido" })));
              }
            }
          } else {
            // Errore
            listaControlli.add(((new Object[] { "E", "Codice CUI", "Codice CUI", "Il codice CUI non &egrave; valido" })));
          }
        }
				
				pagina = "Dati economici";

				// L'importo e' disponibile ?
				str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 13).getValue();
				
				// Importo del lotto al netto degli oneri di sicurezza
				if (SqlManager.getValueFromVectorParam(datiW9LOTT, 14).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_LOTTO", pagina);
				} else if (((Double) SqlManager.getValueFromVectorParam(datiW9LOTT, 14).getValue()).doubleValue() == 0d) {
					listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "IMPORTO_LOTTO"), "Il valore del campo deve essere maggiore di zero" } )));
				}
				
				// Importo per l'attuazione della sicurezza
				if (SqlManager.getValueFromVectorParam(datiW9LOTT, 15).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "IMPORTO_ATTUAZIONE_SICUREZZA", pagina);
				}

				//pagina = " ";

				// Codice CUP
				if (tipoContratto != null && ("L".equals(tipoContratto) || "CL".equals(tipoContratto))) {
				  if (!UtilitySITAT.isSAQ(codgara, sqlManager)) {
  				  Long isManutezioneNonOrdinaria = (Long) this.sqlManager.getObject(
  				      "select count(*) from W9APPALAV where CODGARA=? and CODLOTT=? and ID_APPALTO <> 12",
  				      new Object[] { codgara, codlott });
  				  if (isManutezioneNonOrdinaria.longValue() > 0) {
  				    str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 16).getValue();
  				    String esenteCup = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 26).getValue();
  				    if ("2".equals(esenteCup)) {
	  				  if (! isStringaValorizzata(str)) {
	  				    listaControlli.add(((new Object[] { "E", pagina, "Codice CUP / Esente CUP", "Il lotto prevede il codice CUP" })));
	  				  } else {
	  				    if (str.length() != 15) {
	  			          listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CUP"), "Il codice CUP non \u00E8 valido" })));
	  			        }
	  				  }
  				    } else {
  				    	listaControlli.add(((new Object[] { "E", pagina, "Codice CUP / Esente CUP", "Codice CUP obbligatorio" })));
  				    }
  				  }
				  }
				}

				// Codice CPV
				str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 17).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "CPV", pagina);
				} else if (!this.validazioneCodiceCPV(str)) {
          listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CPV"), "Codice CPV non valido" })));
				}

				// CPV Secondari
				this.validazioneCPVSecondari(codgara, codlott, listaControlli);

				// Posa in opera o manodopera
				str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 18).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "MANOD", pagina);
				}

				Double importoTot = (Double) SqlManager.getValueFromVectorParam(datiW9LOTT, 19).getValue();

				// Categoria prevalente
				str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 20).getValue();
				if (!isStringaValorizzata(str)) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_CATEGORIA_PREVALENTE", pagina);
				}

				// Classe importo categoria prevalente
				if (isStringaValorizzata(str) && (!"FB".equals(str)) && (!"FS".equals(str)) && importoTot != null && importoTot.doubleValue() >= 40000) {
					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 21).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlli, nomeTabella, "CLASCAT", pagina);
					}
				}

				// Ulteriori Categorie
				this.validazioneUlterioriCategorie(codgara, codlott, listaControlli);

				pagina = "Luogo di esecuzione";

				// Luogo ISTAT e NUTS
				String luogoISTAT = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 22).getValue();
				String luogoNUTS = (String) SqlManager.getValueFromVectorParam(datiW9LOTT, 23).getValue();

				if ((!isStringaValorizzata(luogoISTAT)) && (!isStringaValorizzata(luogoNUTS))) {
					String descrizione = this.getDescrizioneCampo(nomeTabella, "LUOGO_ISTAT") + ", " + this.getDescrizioneCampo(nomeTabella, "LUOGO_NUTS");
					String messaggio = "Valorizzare il luogo di esecuzione (ISTAT o NUTS)";
					listaControlli.add(((new Object[] { "E", pagina, descrizione, messaggio })));
				}

				if (isStringaValorizzata(luogoISTAT)) {
				  this.validazioneCodiceIstatComune(luogoISTAT, pagina, listaControlli);
				}
				    
				/*
				 * if (isStringaValorizzata(luogoISTAT)) { if
				 * (luogoISTAT.length() >= 3 &&
				 * !"009".equals(luogoISTAT.substring(0, 3))) { String messaggio
				 * =
				 * "Il valore digitato indica un luogo al di fuori della Regione Toscana"
				 * ; this.addAvviso(listaControlli, nomeTabella, "LUOGO_ISTAT",
				 * "W", pagina, messaggio); } if (luogoISTAT.length() < 3) {
				 * String messaggio =
				 * "Il valore digitato indica un luogo al di fuori della Regione Toscana"
				 * ; this.addAvviso(listaControlli, nomeTabella, "LUOGO_ISTAT",
				 * "W", pagina, messaggio); } }
				 * 
				 * if (isStringaValorizzata(luogoNUTS)) { if (luogoNUTS.length()
				 * >= 4 && !"ITE1".equals(luogoNUTS.substring(0, 4))) { String
				 * messaggio =
				 * "Il valore digitato indica un luogo al di fuori della Regione Toscana"
				 * ; this.addAvviso(listaControlli, nomeTabella, "LUOGO_NUTS",
				 * "W", pagina, messaggio); } if (luogoNUTS.length() < 4) { if
				 * (luogoNUTS.length() == 3 && !"ITE".equals(luogoNUTS)) {
				 * String messaggio =
				 * "Il valore digitato indica un luogo al di fuori della Regione Toscana"
				 * ; this.addAvviso(listaControlli, nomeTabella, "LUOGO_NUTS",
				 * "W", pagina, messaggio); } } }
				 */

				pagina = "Prestazioni comprese nell'appalto";

				// Prestazioni comprese nell'appalto
				if (SqlManager.getValueFromVectorParam(datiW9LOTT, 24).getValue() == null) {
					this.addCampoObbligatorio(listaControlli, nomeTabella, "ID_TIPO_PRESTAZIONE", pagina);
				}

				if (this.geneManager.getProfili().checkProtec(profiloAttivo, "PAGE", "VIS", "W9.W9LOTT-scheda.INCA")) {
				  this.validazioneIncarichiProfessionali(codgara, codlott, new Long(1), listaControlli, "RA");
				}

				// Procedura di scelta del contraente ai sensi del D.lgs. 50/2016
				if (versioneSimog == 1 && this.geneManager.getProfili().checkProtec(profiloAttivo, "COLS", "VIS", "W9.W9LOTT.ID_SCELTA_CONTRAENTE_50")) {
  				if (SqlManager.getValueFromVectorParam(datiW9LOTT, 25).getValue() == null) {
  					String messaggio = "Il valore Procedura di scelta del contraente ai sensi del D.lgs. 50/2016 non &egrave; stato valorizzato";
  					listaControlli.add(((new Object[] { "W", "Procedura di scelta del contraente ai sensi del D.lgs. 50/2016", "Procedura di scelta del contraente ai sensi del D.lgs. 50/2016", messaggio })));
  				}
				}
			}		
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative al lotto", "validazioneLotto", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneLotto: fine metodo");
		}
	}

	/**
	 * Controllo dei dati per le ulteriori categorie.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneUlterioriCategorie(final Long codgara, final Long codlott, final List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneUlterioriCategorie: inizio metodo");
		}

		String selectW9LOTTCATE = "select " + " categoria, " // 0
				+ " clascat, " // 1
				+ " SCORPORABILE, " // 2
				+ " SUBAPPALTABILE " // 3
				+ " from w9lottcate " + " where codgara = ? and codlott = ? order by num_cate";

		try {
			List<?> datiW9LOTTCATE = this.sqlManager.getListVector(selectW9LOTTCATE, new Object[] { codgara, codlott });

			if (datiW9LOTTCATE != null && datiW9LOTTCATE.size() > 0) {

				String nomeTabella = "W9LOTTCATE";
				List<Object> listaControlliW9LOTTCATE = new Vector<Object>();
				String pagina = "";

				String str = null;
				for (int i = 0; i < datiW9LOTTCATE.size(); i++) {
					pagina = "Ulteriore categoria n. " + (i + 1);

					int indiceDatiW9LottCate = 0;

					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTTCATE.get(i), indiceDatiW9LottCate).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliW9LOTTCATE, nomeTabella, "CATEGORIA", pagina);
					}

					indiceDatiW9LottCate++; // indiceDatiW9LottCate = 1
					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTTCATE.get(i), indiceDatiW9LottCate).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliW9LOTTCATE, nomeTabella, "CLASCAT", pagina);
					}

					indiceDatiW9LottCate++; // indiceDatiW9LottCate = 2
					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTTCATE.get(i), indiceDatiW9LottCate).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliW9LOTTCATE, nomeTabella, "SCORPORABILE", pagina);
					}

					indiceDatiW9LottCate++; // indiceDatiW9LottCate = 3
					str = (String) SqlManager.getValueFromVectorParam(datiW9LOTTCATE.get(i), indiceDatiW9LottCate).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliW9LOTTCATE, nomeTabella, "SUBAPPALTABILE", pagina);
					}
				}

				if (!listaControlliW9LOTTCATE.isEmpty()) {
					this.setTitolo(listaControlli, "Ulteriori categorie");
					for (int i = 0; i < listaControlliW9LOTTCATE.size(); i++) {
						listaControlli.add(listaControlliW9LOTTCATE.get(i));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alle ulteriori categorie", "validazioneUlterioriCategorie", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneUlterioriCategorie: fine metodo");
		}
	}

	/**
	 * Controllo dei dati per CPV secondari.
	 * 
	 * @param codgara
	 *            Codice della gara
	 * @param codlott
	 *            Codice del lotto
	 * @param listaControlli
	 *            Lista dei controlli
	 * @throws GestoreException
	 *             GestoreException
	 */
	private void validazioneCPVSecondari(final Long codgara, final Long codlott, List<Object> listaControlli) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCPVSecondari: inizio metodo");
		}
		String selectW9CPV = "select cpv from w9cpv " + "where codgara = ? and codlott = ? order by num_cpv";

		try {
			List<?> datiW9CPV = this.sqlManager.getListVector(selectW9CPV, new Object[] { codgara, codlott });

			if (datiW9CPV != null && datiW9CPV.size() > 0) {
				String nomeTabella = "W9CPV";
				List<Object> listaControlliW9CPV = new Vector<Object>();
				String pagina = "";
				String str = null;

				for (int i = 0; i < datiW9CPV.size(); i++) {
					pagina = "CPV secondario n. " + (i + 1);

					str = (String) SqlManager.getValueFromVectorParam(datiW9CPV.get(i), 0).getValue();
					if (!isStringaValorizzata(str)) {
						this.addCampoObbligatorio(listaControlliW9CPV, nomeTabella, "CPV", pagina);
					} else if (!this.validazioneCodiceCPV(str)) {
					  listaControlliW9CPV.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, "CPV"), "Codice CPV non valido" })));
					}
				}

				if (!listaControlliW9CPV.isEmpty()) {
					this.setTitolo(listaControlli, "Codice CPV secondario");
					for (int i = 0; i < listaControlliW9CPV.size(); i++) {
						listaControlli.add(listaControlliW9CPV.get(i));
					}
				}
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle informazioni relative alle ulteriori categorie", 
			    "validazioneCPVSecondari", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validazioneCPVSecondari: fine metodo");
		}
	}

	private boolean validazioneCodiceCPV(String codiceCPV) throws GestoreException {
	  boolean result = true;
	  
	  try {
  	  if (new Long(0).equals(((Long) this.sqlManager.getObject(
          "select count(*) from TABCPV where CPVCOD4=?", new Object[] { codiceCPV })))) {
        result = false;
      }
	  } catch (SQLException e) {
      throw new GestoreException("Errore nella validazione del codice CPV", 
          "validazioneCodiceCPV", e);
    }
	  return result;
	}
	
	/**
	 * Aggiunge un messaggio bloccante alla listaControlli.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @param pagina
	 *            Pagina
	 */
	private void addCampoObbligatorio(List<Object> listaControlli, final String nomeTabella, final String nomeCampo, final String pagina) {
		if (logger.isDebugEnabled()) {
			logger.debug("addCampoObbligatorio: inizio metodo");
		}

		listaControlli.add(((new Object[] { "E", pagina, this.getDescrizioneCampo(nomeTabella, nomeCampo), "Il campo &egrave; obbligatorio" })));

		if (logger.isDebugEnabled()) {
			logger.debug("addCampoObbligatorio: fine metodo");
		}
	}

	/**
	 * Aggiunge un messaggio di avviso alla listaControlli.
	 * 
	 * @param listaControlli
	 *            Lista dei controlli
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @param tipo
	 *            Tipo di messaggio
	 * @param pagina
	 *            Pagina
	 * @param messaggio
	 *            Messaggio
	 */
	private void addAvviso(List<Object> listaControlli, final String nomeTabella, final String nomeCampo, final String tipo, final String pagina, final String messaggio) {
		if (logger.isDebugEnabled()) {
			logger.debug("addAvviso: inizio metodo");
		}

		listaControlli.add(((new Object[] { tipo, pagina, this.getDescrizioneCampo(nomeTabella, nomeCampo), messaggio })));

		if (logger.isDebugEnabled()) {
			logger.debug("addAvviso: fine metodo");
		}
	}

	private void addAlert(List<Object> listaControlli, final String tipo, final String pagina, final String messaggio) {
		if (logger.isDebugEnabled()) {
			logger.debug("addAlert: inizio metodo");
		}

		listaControlli.add(((Object) (new Object[] { tipo, pagina, "", messaggio })));

		if (logger.isDebugEnabled()) {
			logger.debug("addAlert: fine metodo");
		}
	}

	/**
	 * Restituisce la descrizione WEB del campo.
	 * 
	 * @param nomeTabella
	 *            Nome della tabella
	 * @param nomeCampo
	 *            Nome del campo
	 * @return Ritorna la descrizione del campo a partire da tabella e campo
	 */
	private String getDescrizioneCampo(final String nomeTabella, final String nomeCampo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneCampo: inizio metodo");
		}
		String descrizione = "";
		Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(nomeTabella + "." + nomeCampo);
		if (c != null) {
			descrizione = c.getDescrizioneWEB();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneCampo: fine metodo");
		}
		return descrizione;
	}

	/**
	 * Restituisce la descrizione della fase di esecuzione.
	 * 
	 * @param flusso
	 *            Flusso
	 * @return Ritorna la descrizione della fase di esecuzione.
	 * @throws GestoreException
	 *             GestoreException
	 */
	private String getDescrizioneFlusso(final Long flusso) throws GestoreException {
		String descrizione = "";

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneFlusso: inizio metodo");
		}
		try {
			descrizione = (String) this.sqlManager.getObject("select tab1desc from tab1 where tab1cod = 'W3020' and tab1tip = ?", new Object[] { flusso });
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle descrizione del tipo di flusso", "getDescrizioneFlusso", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneFlusso: fine metodo");
		}
		return descrizione;
	}

	/**
	 * Restituisce il titolo per il controllo dati inseriti della anagrafica
	 * gara/lotto e anagrafica lotto.
	 * 
	 * @param codiceGara
	 * @param codiceLotto
	 * @return
	 * @throws GestoreException
	 */
	private String getDescrizioneGaraLotto(final Long codiceGara, final Long codiceLotto) throws GestoreException {
		String descrizione = "";

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneGaraLotto: inizio metodo");
		}
		try {
			if (codiceLotto == null) {
				descrizione = "Gara " + (String) this.sqlManager.getObject("select W9GARA.IDAVGARA from W9GARA where W9GARA.CODGARA = ?", new Object[] { codiceGara });
			} else {
				descrizione = "Lotto " + (String) this.sqlManager.getObject("select W9LOTT.CIG from W9LOTT where W9LOTT.CODGARA=? and W9LOTT.CODLOTT=?", new Object[] { codiceGara, codiceLotto });
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura delle descrizione del tipo di flusso", "getDescrizioneGaraLotto", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDescrizioneGaraLotto: fine metodo");
		}
		return descrizione;
	}

	/**
	 * Restituisce una stringa con la descrizione del confronto.
	 * 
	 * @param dataConfronto1
	 *            Prima data di confronto
	 * @param parametroConfronto
	 *            Parametro di confronto
	 * @param dataConfronto2
	 *            Seconda data di confronto
	 * @param entitaConfronto2
	 *            Entita' della seconda data di confronto
	 * @param campoConfronto2
	 *            Campo con la seconda data di confronto
	 * @param faseConfronto2
	 *            Fase del campo della seconda daa di confronto
	 * @return Ritorna la stringa per la descrizione del confornto fra date
	 * @throws GestoreException
	 *             GestoreException
	 */
	private String getMessaggioConfrontoDate(final Date dataConfronto1, final String parametroConfronto, final Date dataConfronto2, final String entitaConfronto2, final String campoConfronto2,
			final Long faseConfronto2) throws GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getMessaggioConfrontoDate: inizio metodo");
		}
		String descrizione = "La data indicata (" + UtilityDate.convertiData(dataConfronto1, UtilityDate.FORMATO_GG_MM_AAAA) + ") deve essere ";

		if ("<".equals(parametroConfronto)) {
			descrizione += "precedente";
		}
		if ("<=".equals(parametroConfronto)) {
			descrizione += "precedente o uguale";
		}
		if ("=".equals(parametroConfronto)) {
			descrizione += "uguale";
		}
		if (">".equals(parametroConfronto)) {
			descrizione += "successiva";
		}
		if (">=".equals(parametroConfronto)) {
			descrizione += "successiva o uguale";
		}

		descrizione += " alla \"" + this.getDescrizioneCampo(entitaConfronto2, campoConfronto2) + "\" (" + UtilityDate.convertiData(dataConfronto2, UtilityDate.FORMATO_GG_MM_AAAA) + ")";

		if (faseConfronto2 != null) {
			descrizione += " indicata nella \"" + this.getDescrizioneFlusso(faseConfronto2) + "\"";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMessaggioConfrontoDate: fine metodo");
		}
		return descrizione;

	}

	/**
	 * Restituisce una stringa con il messaggio di confronto.
	 * 
	 * @param importoConfronto1
	 *            Primo importo di confronto
	 * @param parametroConfronto
	 *            Parametro di confronto
	 * @param importoConfronto2
	 *            Secondo importo di confronto
	 * @param entitaConfronto2
	 *            Entita' del secondo importo di confronto
	 * @param campoConfronto2
	 *            Campo del secondo importo di confronto
	 * @param faseConfronto2
	 *            Fase del campo della seconda daa di confronto
	 * @return Ritorna la stringa di confronto tra due importi
	 * @throws GestoreException
	 *             GestoreException
	 */
	private String getMessaggioControntoImporti(final Double importoConfronto1, final String parametroConfronto, final Double importoConfronto2, final String entitaConfronto2,
			final String campoConfronto2, final Long faseConfronto2) throws GestoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMessaggioControntoImporti: inizio metodo");
		}

		String descrizione = "L\'importo digitato (" + this.importoToStringa(importoConfronto1) + ") deve essere ";

		if ("<".equals(parametroConfronto)) {
			descrizione += "inferiore";
		}
		if ("<=".equals(parametroConfronto)) {
			descrizione += "inferiore o uguale";
		}
		if ("=".equals(parametroConfronto)) {
			descrizione += "uguale";
		}
		if (">".equals(parametroConfronto)) {
			descrizione += "superiore";
		}
		if (">=".equals(parametroConfronto)) {
			descrizione += "superiore o uguale";
		}

		descrizione += " al valore del campo \"" + this.getDescrizioneCampo(entitaConfronto2, campoConfronto2) + "\" (" + this.importoToStringa(importoConfronto2) + ")";

		if (faseConfronto2 != null) {
			descrizione += " della \"" + this.getDescrizioneFlusso(faseConfronto2) + "\"";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMessaggioControntoImporti: fine metodo");
		}
		return descrizione;
	}

	/**
	 * Restituisce la rappresentazione in stringa dell'importo passato.
	 * 
	 * @param importo
	 *            Importo
	 * @return Ritorna la rappresentazione in stringa dell'importo passato come
	 *         argomento
	 */
	private String importoToStringa(final Double importo) {

		if (logger.isDebugEnabled()) {
			logger.debug("importoToStringa: inizio metodo");
		}
		String ret = "";

		double valore = importo.doubleValue();
		if (valore != 0) {
			DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
			simbolo.setDecimalSeparator(',');
			simbolo.setGroupingSeparator('.');
			DecimalFormat decFormat = new DecimalFormat("###,###,###,##0.00", simbolo);
			ret = decFormat.format(valore) + "&nbsp;&euro;";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("importoToStringa: fine metodo");
		}

		return ret;
	}

	/**
	 * Utilizzata per settare il tipo T ossia il titolo all'interno di una
	 * tabella.
	 * 
	 * @param listaControlli
	 *            Lista controlli
	 * @param titolo
	 *            Titolo
	 */
	private void setTitolo(final List<Object> listaControlli, final String titolo) {

		if (logger.isDebugEnabled()) {
			logger.debug("setTitolo: inizio metodo");
		}
		listaControlli.add(((new Object[] { "T", titolo, "", "" })));
		if (logger.isDebugEnabled()) {
			logger.debug("setTitolo: fine metodo");
		}
	}

	/**
	 * Valorizzazione di una stringa.
	 * 
	 * @param str
	 *            Stringa
	 * @return Ritorna true se <i>str</i> e' null oppure, se diversa da null, se
	 *         <i>str.trim()</i> ha un numero di caratteri maggiore di zero
	 */
	private boolean isStringaValorizzata(final String str) {
		boolean result = false;

		if (str != null && str.trim().length() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Gestione della validazione di un valore numerico che rappresenta una
	 * percentuale, con gestione del messaggio Si presume che il l'argomento
	 * <i>percentuale</i> sia diverso da null.
	 * 
	 * @param percentuale
	 *            Percentuale
	 * @return Ritorna true se valore >= 0 e < 100
	 */
	private boolean isPercentualeValida(final Double percentuale) {
		boolean result = false;

		if (percentuale.doubleValue() >= 0 && percentuale.doubleValue() < 100) {
			result = true;
		}
		return result;
	}

	/**
	 * Valorizzazione di una stringa con solo numeri.
	 * 
	 * @param str
	 *            Stringa
	 * @return Ritorna true se <i>str</i> e' costituita da soli numeri, false
	 *         altrimenti (ritorna false se str e' null)
	 */
	private boolean isStringaNumerica(final String str) {
		boolean result = false;
		if (isStringaValorizzata(str) && str.matches("/^[0-9]+$/")) {
			result = true;
		}
		return result;
	}
  /**
   * Validazione dei fabbisogni.
   * 
   *
   * @param params
   *        stringa di fabbisogni
   *        stringa codiceFunzione tab_controlli
   * @throws JspException
   *             JspException
   */
  public HashMap<String, Object> validateFabbisogni(Object[] params) throws JspException {
    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();

    // Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla
    // proposta/fabbisogno(TAB_CONTROLLI).

    // ////////////////////////////
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneFabbisogni: inizio metodo");
    }
    
    try {
      String titolo = null;
      List<Object> listaControlli = new Vector<Object>();
      PageContext pageContext = (PageContext) params[0];
      String fabbisogni = (String) params[1];
      String codFunzione = (String) params[2];

      try {
    	  String funzionalita = (String)pageContext.getAttribute("funzionalita");
        this.tabControlliManager.eseguiControlliFabbisogni(fabbisogni,
            codFunzione, funzionalita, listaControlli);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore nella lettura delle informazioni relative al programma triennale delle opere pubbliche",
            "validazioneFabbisogni", e);
      }

      infoValidazione.put("titolo", titolo);
      infoValidazione.put("listaControlli", listaControlli);

      // Controllo errori e warning
      int numeroErrori = 0;
      int numeroWarning = 0;

      if (!listaControlli.isEmpty()) {
        if (logger.isDebugEnabled()) {
          logger
              .debug("Messaggi di errore durante la validazione dei fabbisogni");
        }

        if (listaControlli.get(0) instanceof MessaggioControlloBean) {
          infoValidazione.put("controlliSuBean", Boolean.TRUE);
          for (int i = 0; i < listaControlli.size(); i++) {
            MessaggioControlloBean messaggioControllo =
                (MessaggioControlloBean) listaControlli.get(i);
            if ("E".equals(messaggioControllo.getTipoMessaggio())) {
              numeroErrori++;
            }
            if ("W".equals(messaggioControllo.getTipoMessaggio())) {
              numeroWarning++;
            }

            if (logger.isDebugEnabled()) {
              logger.debug(messaggioControllo.getPagina()
                  + " - "
                  + messaggioControllo.getTipoMessaggio()
                  + " - "
                  + messaggioControllo.getDescrizioneCampo()
                  + " - "
                  + messaggioControllo.getMessaggio());
            }
          }
        } else {
          for (int i = 0; i < listaControlli.size(); i++) {
            Object[] controllo = (Object[]) listaControlli.get(i);
            String tipo = (String) controllo[0];
            if ("E".equals(tipo)) {
              numeroErrori++;
            }
            if ("W".equals(tipo)) {
              numeroWarning++;
            }

            if (logger.isDebugEnabled()) {
              logger.debug(controllo[0]
                  + " - "
                  + controllo[1]
                  + " - "
                  + controllo[2]
                  + " - "
                  + controllo[3]);
            }
          }
        }
      }

      infoValidazione.put("numeroErrori", numeroErrori);
      infoValidazione.put("numeroWarning", numeroWarning);
    } catch (GestoreException e) {
      throw new JspException("Errore nella funzione di controllo dei dati", e);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneFabbisogni: fine metodo");
    }
    // /////////////////////////////

    return infoValidazione;
  }
  
  /**
   * Ritorna il CIG MASTER se il lotto passato come parametro è il CIG MASTER.
   * 
   * @param codgara
   *            Codice della gara
   * @param codlott
   *            Codice del lotto
   * @return Ritorna CIG_MASTER_ML
   * @throws SQLException
   *             SQLException
   */
  private String getCIGMaster(final Long codgara, final Long codlott) throws SQLException {
	  String cigMaster = (String) this.sqlManager.getObject("select CIG_MASTER_ML from W9LOTT where CODGARA = ? and CODLOTT = ? and CIG=CIG_MASTER_ML", new Object[] { codgara, codlott});
	  return cigMaster;
  }

}