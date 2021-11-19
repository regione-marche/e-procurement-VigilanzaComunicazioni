package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreW9PUBBLICAZIONI extends AbstractGestoreChiaveNumerica {

	@Override
	public String[] getAltriCampiChiave() {
		return new String[]{"CODGARA"};
	}

	@Override
	public String getCampoNumericoChiave() {
		return "NUM_PUBB";
	}

    @Override
	public String getEntita() {
		return "W9PUBBLICAZIONI";
	}

    @Override
	public void postDelete(DataColumnContainer arg0) throws GestoreException {
	}

  @Override
  public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
    try {
      GestoreW9DOCGARA gestoreW9DOCGARA = new GestoreW9DOCGARA();
      AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
          "W9DOCGARA", "NUMDOC", new String[] { "CODGARA", "NUM_PUBB" }, this.getRequest());
      gestoreW9DOCGARA.setForm(this.getForm());
  
      gestoreW9DOCGARA.gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestore, "W9DOCGARA",
          new DataColumn[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA"), impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB") }, null);
      Long codiceGara= impl.getColumn("W9PUBBLICAZIONI.CODGARA").getValue().longValue();
      Long numeroPubblicazione = impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB").getValue().longValue();
      
      String sqlLott = "select L.CODLOTT from W9LOTT L, W9GARA G where L.CODGARA=? and L.CODGARA=G.CODGARA ";
      Long tipoDocumenti = impl.getLong("W9PUBBLICAZIONI.TIPDOC");
      
      String strWhere = (String) this.sqlManager.getObject("select CL_WHERE_VIS from W9CF_PUBB where ID=?",
          new Object[] {tipoDocumenti} );
      if (StringUtils.isNotEmpty(strWhere)) {
        sqlLott = sqlLott.concat(" AND (").concat(strWhere).concat(")");
      }
      Long codiceLotto = impl.getLong("CODLOTT");
      //verifico se la pubblicazione è a livello di lotto o di gara
      if (codiceLotto != null) {
		  this.sqlManager.update("DELETE FROM W9PUBLOTTO WHERE CODGARA = ? AND NUM_PUBB = ? AND CODLOTT = ?",
                  new Object[] { codiceGara, numeroPubblicazione, codiceLotto });
    	  this.sqlManager.update("INSERT INTO W9PUBLOTTO (CODGARA, NUM_PUBB, CODLOTT) VALUES (?,?,?)",
                  new Object[] { codiceGara, numeroPubblicazione, codiceLotto });
      } else {
    	  List< ? > listaCodiciLotto = this.sqlManager.getListVector(sqlLott, new Object[] { codiceGara });
        if (listaCodiciLotto.size() > 0) {
          for (int i = 0; i < listaCodiciLotto.size(); i++) {
            Vector< ? > vettore = (Vector< ? >) listaCodiciLotto.get(i);
            codiceLotto = new Long (vettore.get(0).toString());
			this.sqlManager.update("DELETE FROM W9PUBLOTTO WHERE CODGARA = ? AND NUM_PUBB = ? AND CODLOTT = ?",
                  new Object[] { codiceGara, numeroPubblicazione, codiceLotto });
            this.sqlManager.update("INSERT INTO W9PUBLOTTO (CODGARA, NUM_PUBB, CODLOTT) VALUES (?,?,?)",
                new Object[] { codiceGara, numeroPubblicazione, codiceLotto });
          }
        }
      }
    } catch (SQLException e) {
      throw new GestoreException(
        "Errore nell'inserimento dei collegamenti ai lotti della gara della pubblicazione", "", e);
    }
  }
    
	@Override
	public void postInsert(DataColumnContainer impl) throws GestoreException {
	}
	
	@Override
	public void postUpdate(DataColumnContainer impl) throws GestoreException {
	}
	
	@Override
	public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		// cancello tutti i documenti della pubblicazione
		try {
			sqlManager.update("DELETE W9DOCGARA WHERE CODGARA = ? AND NUM_PUBB = ? ", new Object[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA"), impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB") });
			sqlManager.update("DELETE W9PUBLOTTO WHERE CODGARA = ? AND NUM_PUBB = ? ", new Object[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA"), impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB") });
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'eliminazione della pubblicazione della gara ", "", e);
		}

	}
	
	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		super.preInsert(status, impl);
	}

    @Override
	public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {

		// Gestore per il salvataggio dei documenti
		GestoreW9DOCGARA gestoreW9DOCGARA = new GestoreW9DOCGARA();
		AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica("W9DOCGARA", "NUMDOC", new String[] { "CODGARA", "NUM_PUBB" }, this.getRequest());
		gestoreW9DOCGARA.setForm(this.getForm());

		gestoreW9DOCGARA.gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestore, "W9DOCGARA",
				new DataColumn[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA"), impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB") }, null);
		
		//solo per pubblicazione di tipo Esito (esiti)
		// gestione schede multiple imprese aggiudicatari
		// controllo che esista la colonna tipo Aggiudicatario
		if (impl.isColumn("ESITI_AGGIUDICATARI.ID_TIPOAGG")) {
			Long idGruppo = null;
			Long tmpIdGruppo = null;

			int numeroRecord = impl.getLong("NUMERO_ESITI_AGGIUDICATARI").intValue();
			DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(impl.getColumns("ESITI_AGGIUDICATARI", 0));

			// controllo se sono in modifica
			// il campo num_aggi non indicizzato e' la mia sentinella
			// (oltre che il valore da tenere per un corretto reload della
			// pagina)
			if (impl.isColumn("ESITI_AGGIUDICATARI.NUM_AGGI")) {

				Long numImpresaDaMantenere = impl.getColumn("ESITI_AGGIUDICATARI.NUM_AGGI").getValue().longValue();

				// controllo la presenza del numero impresa da mantenere come
				// 'non eliminato'
				if (numImpresaDaMantenere != null) {
					boolean nonTrovato = true;
					for (int i = 1; i <= numeroRecord; i++) {
						if (impl.getColumn("ESITI_AGGIUDICATARI.NUM_AGGI_" + i).getValue().longValue() == numImpresaDaMantenere.longValue() && impl.getColumn("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + i).getValue().getValue() == null) {
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
							if (impl.getColumn("ESITI_AGGIUDICATARI.NUM_AGGI_" + i).getValue().longValue() == numImpresaDaMantenere.longValue()) {
								impresaDaMantenere = i;
							}
							// ciclo per trovare una nuova impresa inserita
							// cui poter assegnare il suo num_aggi
							if (StringUtils.isNotEmpty(impl.getColumn("IMPR.NOMEST_" + i).getValue().getStringValue()) && impl.getColumn("ESITI_AGGIUDICATARI.NUM_AGGI_" + i).getValue().longValue() == null
									&& impl.getColumn("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + i).getValue().getValue() == null) {
								impresaSostituta = i;
								numImpresaDaMantenereNonSettato = false;
								break;
							}
						}
						if (numImpresaDaMantenereNonSettato) {
							// nel caso non fossero presenti nuove imprese
							// scambio il num_aggi con un'impresa esistente
							for (int i = 1; i <= numeroRecord; i++) {
								if (impl.getColumn("ESITI_AGGIUDICATARI.NUM_AGGI_" + i).getValue().longValue() != null && impl.getColumn("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + i).getValue().getValue() == null) {
									impresaSostituta = i;
								}
							}
						}
						// copio tutti i dati dell'impresa sostituta dentro
						// quella originaria tramite gli indici trovati
						// precedentemente
						impl.setValue("ESITI_AGGIUDICATARI.RUOLO_" + impresaDaMantenere, impl.getColumn("ESITI_AGGIUDICATARI.RUOLO_" + impresaSostituta).getValue().getValue());
						impl.setValue("ESITI_AGGIUDICATARI.CODIMP_" + impresaDaMantenere, impl.getColumn("ESITI_AGGIUDICATARI.CODIMP_" + impresaSostituta).getValue().getValue());
						impl.setValue("ESITI_AGGIUDICATARI.ID_GRUPPO_" + impresaDaMantenere, impl.getColumn("ESITI_AGGIUDICATARI.ID_GRUPPO_" + impresaSostituta).getValue().getValue());
						impl.setValue("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + impresaDaMantenere, null);
						impl.setValue("ESITI_AGGIUDICATARI.MOD_ESITI_AGGIUDICATARI_" + impresaDaMantenere, new Long(1));

						// cancello l'impresa sostituta
						if (numImpresaDaMantenereNonSettato) {
							impl.setValue("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + impresaSostituta, new Long(1));
						} else {
							// nel caso di un'impresa appena inserita pongo a
							// null tutti i suoi campi
							impl.setValue("ESITI_AGGIUDICATARI.CODGARA_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.NUM_PUBB_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.NUM_AGGI_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.RUOLO_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.CODIMP_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.ID_GRUPPO_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.DEL_ESITI_AGGIUDICATARI_" + impresaSostituta, null);
							impl.setValue("ESITI_AGGIUDICATARI.MOD_ESITI_AGGIUDICATARI_" + impresaSostituta, null);
						}
					}
				}
			}

			// controllo che il tipo sia diverso da 3
			if (!(impl.getColumn("ESITI_AGGIUDICATARI.ID_TIPOAGG").getValue().getStringValue().equals("3"))) {
				// gestione impresa multipla

				// cerco tra i record inviati
				// la presenza di un id gruppo
				// valorizzato
				for (int i = 1; i <= numeroRecord; i++) {
					if (tmpDataColumnContainer.getColumn("ESITI_AGGIUDICATARI.ID_GRUPPO_" + i).getValue().longValue() == null) {
						tmpIdGruppo = tmpDataColumnContainer.getColumn("ESITI_AGGIUDICATARI.ID_GRUPPO_" + i).getValue().longValue();
						break;
					}
				}

				// se non presente alcun id gruppo
				if (tmpIdGruppo == null) {
					// calcolo da db il max + 1 dell'id gruppo
					String idGruppoSqlMaxP1 = "select max(id_gruppo) from ESITI_AGGIUDICATARI where codgara = ? and num_pubb = ?";
					try {
						idGruppo = (Long) this.sqlManager.getObject(idGruppoSqlMaxP1, new Object[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA").getValue().longValue(),
								impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB").getValue().longValue()});

						if (idGruppo == null) {
							idGruppo = 0L;
						}
						idGruppo++;

						// e mi occupo di copiare ovunque l'idgruppo
						for (int i = 1; i <= numeroRecord; i++) {
							// il seguente controllo è superfluo poiche'
							// (attualmente) nella jsp in presenza di 
							// un id gruppo tutti gli altri vengono valorizzati
							if (tmpDataColumnContainer.getColumn("ESITI_AGGIUDICATARI.ID_GRUPPO_" + i).getValue().longValue() == null) {
								tmpDataColumnContainer.getColumn("ESITI_AGGIUDICATARI.ID_GRUPPO_" + i).setObjectValue(idGruppo);
							}
						}
					} catch (SQLException e) {
						throw new GestoreException("Errore nell'esecuzione della query per l'estrazione dei dati del Soggetto Aggiudicatario", "", e);
					}
				}
			}
		}

		// gestione "standard" dell'inserimento in w9aggi dei valori
		AbstractGestoreChiaveNumerica gestoreESITI_AGGIUDICATARI = new DefaultGestoreEntitaChiaveNumerica("ESITI_AGGIUDICATARI", "NUM_AGGI", new String[] { "CODGARA", "NUM_PUBB"}, this.getRequest());
		gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreESITI_AGGIUDICATARI, "ESITI_AGGIUDICATARI",
				new DataColumn[] { impl.getColumn("W9PUBBLICAZIONI.CODGARA"), impl.getColumn("W9PUBBLICAZIONI.NUM_PUBB") }, null);

		// setto il risultato ad ok per tornare indietro nella jsp e non avere
		// un livello superfluo nel breadcrumb
		if (impl.isColumn("ESITI_AGGIUDICATARI.ID_TIPOAGG")) {
			this.getRequest().setAttribute("RISULTATO", "OK");
		}
		
	}
}