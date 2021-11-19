package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiW9;

public class GestoreW9APPA extends AbstractGestoreChiaveNumericaEntitaW9 {

	@Override
	public String[] getAltriCampiChiave() {
		return new String[] { "CODGARA", "CODLOTT" };
	}

	@Override
	public String getCampoNumericoChiave() {
		return "NUM_APPA";
	}

	@Override
	public String getEntita() {
		return "W9APPA";
	}

	@Override
	public void postDelete(DataColumnContainer arg0) throws GestoreException {
	}

	@Override
	public void postInsert(DataColumnContainer impl) throws GestoreException {
	}

	@Override
	public void postUpdate(DataColumnContainer impl) throws GestoreException {
	}

	@Override
	public void preDelete(TransactionStatus arg0, DataColumnContainer impl) throws GestoreException {
	  
	  try {
      this.sqlManager.update("update W9LOTT set ESITO_PROCEDURA=null where CODGARA=? and CODLOTT=?",
          new Object[] { impl.getLong("W9APPA.CODGARA"), impl.getLong("W9APPA.CODLOTT") });
    } catch (SQLException e) {
      throw new GestoreException("Errore nell'aggiornamento di W9LOTT.ESITO_PROCEDURA", null, e);
    }
	  
	}

	@Override
	public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		// gestione schede multiple imprese aggiudicatari
		// controllo che esista la colonna tipo Aggiudicatario
		if (impl.isColumn("W9AGGI.ID_TIPOAGG")) {
			Long idGruppo = null;
			Long tmpIdGruppo = null;

			int numeroRecord = impl.getLong("NUMERO_W9AGGI").intValue();
			DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(impl.getColumns("W9AGGI", 0));

			// controllo se sono in modifica il campo num_aggi non indicizzato e' la mia sentinella
			// (oltre che il valore da tenere per un corretto reload della pagina)
			if (impl.isColumn("W9AGGI.NUM_AGGI")) {

				Long numImpresaDaMantenere = impl.getColumn("W9AGGI.NUM_AGGI").getValue().longValue();

				// controllo la presenza del numero impresa da mantenere come 'non eliminato'
				if (numImpresaDaMantenere != null) {
					boolean nonTrovato = true;
					for (int i = 1; i <= numeroRecord; i++) {
						if (impl.getColumn("W9AGGI.NUM_AGGI_" + i).getValue().longValue().equals(numImpresaDaMantenere) && impl.getColumn("W9AGGI.DEL_W9AGGI_" + i).getValue().getValue() == null) {
							nonTrovato = false;
							break;
						}
					}
					// se e' stato rimosso devo trovare il modo di reinserirlo
					if (nonTrovato) {
						int impresaSostituta = -1;
						int impresaDaMantenere = -1;
						boolean numImpresaDaMantenereNonSettato = true;

						for (int i = 1; i <= numeroRecord; i++) {
							// recupero l'indice dell'impresa da mantenere
							if (impl.getColumn("W9AGGI.NUM_AGGI_" + i).getValue().longValue().equals(numImpresaDaMantenere)) {
								impresaDaMantenere = i;
							}
							// ciclo per trovare una nuova impresa inserita
							// cui poter assegnare il suo num_aggi
							if (StringUtils.isNotEmpty(impl.getColumn("IMPR.NOMEST_" + i).getValue().getStringValue()) && impl.getColumn("W9AGGI.NUM_AGGI_" + i).getValue().longValue() == null
									&& impl.getColumn("W9AGGI.DEL_W9AGGI_" + i).getValue().getValue() == null) {
								impresaSostituta = i;
								numImpresaDaMantenereNonSettato = false;
								break;
							}
						}
						if (numImpresaDaMantenereNonSettato) {
							// nel caso non fossero presenti nuove imprese
							// scambio il num_aggi con un'impresa esistente
							for (int i = 1; i <= numeroRecord; i++) {
								if (impl.getColumn("W9AGGI.NUM_AGGI_" + i).getValue().longValue() != null && impl.getColumn("W9AGGI.DEL_W9AGGI_" + i).getValue().getValue() == null) {
									impresaSostituta = i;
								}
							}
						}
						// copio tutti i dati dell'impresa sostituta dentro
						// quella originaria tramite gli indici trovati
						// precedentemente
						impl.setValue("W9AGGI.RUOLO_" + impresaDaMantenere, impl.getColumn("W9AGGI.RUOLO_" + impresaSostituta).getValue().getValue());
						impl.setValue("W9AGGI.FLAG_AVVALIMENTO_" + impresaDaMantenere, impl.getColumn("W9AGGI.FLAG_AVVALIMENTO_" + impresaSostituta).getValue().getValue());
						impl.setValue("W9AGGI.CODIMP_" + impresaDaMantenere, impl.getColumn("W9AGGI.CODIMP_" + impresaSostituta).getValue().getValue());
						impl.setValue("W9AGGI.CODIMP_AUSILIARIA_" + impresaDaMantenere, impl.getColumn("W9AGGI.CODIMP_AUSILIARIA_" + impresaSostituta).getValue().getValue());
						impl.setValue("W9AGGI.ID_GRUPPO_" + impresaDaMantenere, impl.getColumn("W9AGGI.ID_GRUPPO_" + impresaSostituta).getValue().getValue());
						impl.setValue("W9AGGI.DEL_W9AGGI_" + impresaDaMantenere, null);
						impl.setValue("W9AGGI.MOD_W9AGGI_" + impresaDaMantenere, new Long(1));

						// cancello l'impresa sostituta
						if (numImpresaDaMantenereNonSettato) {
							impl.setValue("W9AGGI.DEL_W9AGGI_" + impresaSostituta, new Long(1));
						} else {
							// nel caso di un'impresa appena inserita pongo a
							// null tutti i suoi campi
							impl.setValue("W9AGGI.CODGARA_" + impresaSostituta, null);
							impl.setValue("W9AGGI.CODLOTT_" + impresaSostituta, null);
							impl.setValue("W9AGGI.NUM_APPA_" + impresaSostituta, null);
							impl.setValue("W9AGGI.NUM_AGGI_" + impresaSostituta, null);
							impl.setValue("W9AGGI.RUOLO_" + impresaSostituta, null);
							impl.setValue("W9AGGI.FLAG_AVVALIMENTO_" + impresaSostituta, null);
							impl.setValue("W9AGGI.CODIMP_" + impresaSostituta, null);
							impl.setValue("W9AGGI.CODIMP_AUSILIARIA_" + impresaSostituta, null);
							impl.setValue("W9AGGI.ID_GRUPPO_" + impresaSostituta, null);
							impl.setValue("W9AGGI.DEL_W9AGGI_" + impresaSostituta, null);
							impl.setValue("W9AGGI.MOD_W9AGGI_" + impresaSostituta, null);
						}
					}
				}
			}

			// controllo che il tipo sia diverso da 3
			if (!(impl.getColumn("W9AGGI.ID_TIPOAGG").getValue().getStringValue().equals("3")) && 
					!(impl.getColumn("W9AGGI.ID_TIPOAGG").getValue().getStringValue().equals("4"))) {
				// gestione impresa multipla

				// cerco tra i record inviati
				// la presenza di un id gruppo
				// valorizzato
				for (int i = 1; i <= numeroRecord; i++) {
					if (tmpDataColumnContainer.getColumn("W9AGGI.ID_GRUPPO_" + i).getValue().longValue() == null) {
						tmpIdGruppo = tmpDataColumnContainer.getColumn("W9AGGI.ID_GRUPPO_" + i).getValue().longValue();
						break;
					}
				}

				// se non presente alcun id gruppo
				if (tmpIdGruppo == null) {
					// calcolo da db il max + 1 dell'id gruppo
					String idGruppoSqlMaxP1 = "select max(id_gruppo) from w9aggi where codgara = ? and codlott = ? and num_appa = ?";
					try {
						idGruppo = (Long) this.sqlManager.getObject(idGruppoSqlMaxP1, new Object[] { impl.getColumn("W9APPA.CODGARA").getValue().longValue(),
								impl.getColumn("W9APPA.CODLOTT").getValue().longValue(), impl.getColumn("W9APPA.NUM_APPA").getValue().longValue() });

						if (idGruppo == null) {
							idGruppo = 0L;
						}
						idGruppo++;

						// e mi occupo di copiare ovunque l'idgruppo
						for (int i = 1; i <= numeroRecord; i++) {
							// il seguente controllo è superfluo poiche'
							// (attualmente) nella jsp in presenza di 
							// un id gruppo tutti gli altri vengono valorizzati
							if (tmpDataColumnContainer.getColumn("W9AGGI.ID_GRUPPO_" + i).getValue().longValue() == null) {
								tmpDataColumnContainer.getColumn("W9AGGI.ID_GRUPPO_" + i).setObjectValue(idGruppo);
							}
						}
					} catch (SQLException e) {
						throw new GestoreException("Errore nell'esecuzione della query per l'estrazione dei dati del Soggetto Aggiudicatario", "", e);
					}
				}
			}
		}

		// gestione "standard" dell'inserimento in w9aggi dei valori
		AbstractGestoreChiaveNumerica gestoreW9AGGI = new DefaultGestoreEntitaChiaveNumerica("W9AGGI", "NUM_AGGI", new String[] { "CODGARA", "CODLOTT", "NUM_APPA" }, this.getRequest());
		gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9AGGI, "W9AGGI",
				new DataColumn[] { impl.getColumn("W9APPA.CODGARA"), impl.getColumn("W9APPA.CODLOTT"), impl.getColumn("W9APPA.NUM_APPA") }, null);

		// setto il risultato ad ok per tornare indietro nella jsp e non avere
		// un livello superfluo nel breadcrumb
		if (impl.isColumn("W9AGGI.ID_TIPOAGG")) {
			this.getRequest().setAttribute("RISULTATO", "OK");
		}

		// gestione schede multiple incarichi professionali
		AbstractGestoreChiaveNumerica gestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica("W9INCA", "NUM_INCA", new String[] { "CODGARA", "CODLOTT", "NUM" }, this.getRequest());

		gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9INCA, "W9INCA",
				new DataColumn[] { impl.getColumn("W9APPA.CODGARA"), impl.getColumn("W9APPA.CODLOTT"), impl.getColumn("W9APPA.NUM_APPA") }, null);

		// gestione schede multiple finanziamenti
		AbstractGestoreChiaveNumerica gestoreW9FINA = new DefaultGestoreEntitaChiaveNumerica("W9FINA", "NUM_FINA", new String[] { "CODGARA", "CODLOTT", "NUM_APPA" }, this.getRequest());
		gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9FINA, "W9FINA",
				new DataColumn[] { impl.getColumn("W9APPA.CODGARA"), impl.getColumn("W9APPA.CODLOTT"), impl.getColumn("W9APPA.NUM_APPA") }, null);

		// gestione schede multiple categorie di qualificazione SOA
		GestoreW9REQUMultiplo gestRequ = new GestoreW9REQUMultiplo();
		gestRequ.setGeneManager(this.geneManager);
		gestRequ.gestioneW9REQUdaW9APPA(this.getRequest(), status, impl);

	}

	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		// Inserimento del RUP come incaricato professionale con idruolo=14
		// (Responsabile Unico procedimento)
		Long codGara = impl.getLong("W9APPA.CODGARA");
		Long codLott = impl.getLong("W9APPA.CODLOTT");
		Long numAppa = impl.getLong("W9APPA.NUM_APPA");
		if (numAppa == null) {
			numAppa = new Long(1);
		}
		impl.addColumn("W9INCA.CODGARA", JdbcParametro.TIPO_NUMERICO, codGara);
		impl.addColumn("W9INCA.CODLOTT", JdbcParametro.TIPO_NUMERICO, codLott);
		impl.addColumn("W9INCA.NUM", JdbcParametro.TIPO_NUMERICO, numAppa);
		impl.addColumn("W9INCA.NUM_INCA", JdbcParametro.TIPO_NUMERICO, new Long(1));

		impl.getColumn("W9INCA.CODGARA").setChiave(true);
		impl.getColumn("W9INCA.CODLOTT").setChiave(true);
		impl.getColumn("W9INCA.NUM_INCA").setChiave(true);
		impl.getColumn("W9INCA.NUM").setChiave(true);

		impl.addColumn("W9INCA.ID_RUOLO", JdbcParametro.TIPO_NUMERICO, new Long(14));
		try {
			Vector<?> datiLotto = sqlManager.getVector("select RUP, ART_E1 from W9LOTT where CODGARA=? and CODLOTT=?", new Object[] { codGara, codLott });

			String rup = ((JdbcParametro) datiLotto.get(0)).getStringValue();
			String artE1 = ((JdbcParametro) datiLotto.get(1)).getStringValue();
			int faseEsecuzione = Integer.parseInt(impl.getColumn("W9FASI.FASE_ESECUZIONE").getValue().getStringValue());
			switch (faseEsecuzione) {
			case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
				impl.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RA");
				break;
			case CostantiW9.ADESIONE_ACCORDO_QUADRO:
				impl.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RQ");
				break;
			default:
				if ("1".equals(artE1)) {
					impl.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RE");
				} else {
					impl.addColumn("W9INCA.SEZIONE", JdbcParametro.TIPO_TESTO, "RS");
				}
				break;
			}

			if (StringUtils.isNotEmpty(rup)) {
				impl.addColumn("W9INCA.CODTEC", JdbcParametro.TIPO_TESTO, rup);
			}
		} catch (SQLException e) {
			throw new GestoreException("Errore nella lettura dei dati del per la creazione dell'incarico "
					+ "professionale di default (RUP e' il primo tecnico come responsabile unico della procedura)", null, e);
		}
		AbstractGestoreChiaveNumerica gestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica("W9INCA", "NUM_INCA", new String[] { "CODGARA", "CODLOTT", "NUM" }, this.getRequest());
		this.inserisci(status, impl, gestoreW9INCA);

		super.preInsert(status, impl);

		try {
			this.sqlManager.update("update W9LOTT set ESITO_PROCEDURA=? where CODGARA=? and CODLOTT=?",
			    new Object[] { new Long(1), impl.getLong("W9APPA.CODGARA"), impl.getLong("W9APPA.CODLOTT") });
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'aggiornamento di W9LOTT.ESITO_PROCEDURA", null, e);
		}
		
		// Nell'inizializzazione dell'adesione accordo quadro si copiano anche le aggiudicatarie che risultano
		// aver vinto la gara da cui questo CIG deriva (vedi W9GARA.CIG_ACCQUADRO).
		HashMap<String, Object> mappaAggiudicazioneAccordoQuadro = (HashMap<String, Object>) this.getRequest().getSession().getAttribute("AGGIUDICAZIONE_ACC_QUADRO");
    if (mappaAggiudicazioneAccordoQuadro != null) {
      try {
        String codein = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
        
        // Inizializzazione delle imprese aggiudicatarie
        SoggAggiudicatarioType[] soggettiAggiudicatari = (SoggAggiudicatarioType[]) mappaAggiudicazioneAccordoQuadro.get("arrayAggiudicatari");
        DittaAusiliariaType[] ditteAusiliarie = (DittaAusiliariaType[]) mappaAggiudicazioneAccordoQuadro.get("arrayDitteAusiliarie");;
        AggiudicatarioType[] anagraficaAggiudicatari = (AggiudicatarioType[]) mappaAggiudicazioneAccordoQuadro.get("arrayAnagraficaAggiudicatari");
        
        W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager", this.getServletContext(), W9Manager.class);
        w9Manager.gestioneAggiudicatariPerInizializzazioneAdesioneAccordoQuadro(codGara, codLott,
            numAppa, codein, soggettiAggiudicatari, ditteAusiliarie, anagraficaAggiudicatari);

      } catch (SQLException e) {
        throw new GestoreException("Errore nell'inizializzazione dell'adesione accordo quadro e delle aggiudicatarie", null, e);
      } finally {
        this.getRequest().getSession().removeAttribute("AGGIUDICAZIONE_ACC_QUADRO");
      }
    } else {
    
	    // Se l'atto di esito/aggiudicazione e' stato inviato, allora si copiano le imprese aggiudicatarie dalla tabella ESITI_AGGIUDICATARI
	    try {
	    	Long counter = (Long) sqlManager.getObject(
		  			"select count(p1.CODGARA) from W9PUBBLICAZIONI p1 join W9PUBLOTTO p2 on p1.CODGARA=p2.CODGARA and p1.NUM_PUBB=p2.NUM_PUBB join W9CF_PUBB p3 on p1.TIPDOC=p3.ID "
		  			+ "where p1.CODGARA=? and p2.CODLOTT=? and p3.TIPO in ('A','E')",
		  					new Object[] { codGara, codLott });
		  	if (counter.intValue() > 0) {
		  		Long numPubb = (Long) sqlManager.getObject(
			  			"select p1.NUM_PUBB from W9PUBBLICAZIONI p1 join W9PUBLOTTO p2 on p1.CODGARA=p2.CODGARA and p1.NUM_PUBB=p2.NUM_PUBB join W9CF_PUBB p3 on p1.TIPDOC=p3.ID "
			  	  			+ "where p1.CODGARA=? and p2.CODLOTT=? and p3.TIPO in ('A','E')",
			  	  					new Object[] { codGara, codLott });
		  		List<Vector<?>> listaEsitoAggiudicarie = sqlManager.getListVector(
		  				"select NUM_AGGI, ID_TIPOAGG, RUOLO, CODIMP, ID_GRUPPO, CODICE_CASSA, CODICE_INAIL, CODICE_INPS "
		  				+ "from ESITI_AGGIUDICATARI where CODGARA=? and NUM_PUBB=? order by NUM_AGGI asc", new Object[] { codGara, numPubb });
		  		
		  		if (listaEsitoAggiudicarie != null && listaEsitoAggiudicarie.size() > 0) {
		  			for (int i=0; i < listaEsitoAggiudicarie.size(); i++) {
		  				
		  				Vector<JdbcParametro> esitoAggiudicataria = (Vector<JdbcParametro>) listaEsitoAggiudicarie.get(i);
		  				
		  				DataColumn codGaraAggi = new DataColumn("W9AGGI.CODGARA", new JdbcParametro(
					    		JdbcParametro.TIPO_NUMERICO, codGara));
					    DataColumn codLottAggi = new DataColumn("W9AGGI.CODLOTT", new JdbcParametro(
					    		JdbcParametro.TIPO_NUMERICO, codLott));
					    DataColumn numAppaAggi = new DataColumn("W9AGGI.NUM_APPA", new JdbcParametro(
					    		JdbcParametro.TIPO_NUMERICO, numAppa));
					    DataColumn numAggi = new DataColumn("W9AGGI.NUM_AGGI", esitoAggiudicataria.get(0));
					    
					    DataColumnContainer dccAggi = new DataColumnContainer(new DataColumn[] {
					        codGaraAggi, codLottAggi, numAppaAggi, numAggi} );
	
					    if (esitoAggiudicataria.get(1).getValue() != null) {
					    	dccAggi.addColumn("W9AGGI.ID_TIPOAGG", esitoAggiudicataria.get(1).getValue());
					    }
					    
					    if (esitoAggiudicataria.get(2).getValue() != null) {
					      dccAggi.addColumn("W9AGGI.RUOLO", esitoAggiudicataria.get(2).getValue());  
					    }
	
					    if (esitoAggiudicataria.get(3).getValue() != null) {
					    	dccAggi.addColumn("W9AGGI.CODIMP", esitoAggiudicataria.get(3).getValue());
					    }
	
					    if (esitoAggiudicataria.get(4).getValue() != null) {
				    		dccAggi.addColumn("W9AGGI.ID_GRUPPO", esitoAggiudicataria.get(4).getValue());
					    }
					    
					    if (esitoAggiudicataria.get(5).getValue() != null) {
				    		dccAggi.addColumn("W9AGGI.CODICE_CASSA", esitoAggiudicataria.get(5).getValue());
					    }
					    
					    if (esitoAggiudicataria.get(6).getValue() != null) {
				    		dccAggi.addColumn("W9AGGI.CODICE_INAIL", esitoAggiudicataria.get(6).getValue());
					    }
					    
					    if (esitoAggiudicataria.get(7).getValue() != null) {
				    		dccAggi.addColumn("W9AGGI.CODICE_INPS", esitoAggiudicataria.get(7).getValue());
					    }
					            
					    // Inserimento record in W9AGGI
					    dccAggi.insert("W9AGGI", this.sqlManager);
		  			}
		  		}
		  	}
	    } catch (SQLException e) {
	      throw new GestoreException("Errore nella delle aggiudicatarie dalla tabella ESITI_AGGIUDICATARI", null, e);
	    }
    }
	}

}
