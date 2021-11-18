package it.eldasoft.w9.bl;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Manager per la gestione delle operazioni sulla lista delle imprese
 * invitate/partecipanti
 * 
 * @author Luca.Giacomazzo
 */
public class W9AggiudicatarieManager {

	private static Logger logger = Logger.getLogger(W9AggiudicatarieManager.class);

	private SqlManager sqlManager;

	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	@SuppressWarnings("unchecked")
	public List<Vector<?>> getListaW9Aggiudicatarie(PageContext pageContext, Long codiceGara, Long numAppalto, Long codiceLotto) throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getListaW9Imprese: inizio metodo");
		}

		// non esistendo un bean che descriva i dati necessari in visualizzazione
		// ne viene creato uno tramite vettori
		List<Vector<?>> listaW9Aggiudicatarie =  new Vector<Vector<?>>();

		// seleziono tutte le aggiudicatarie ordinate per tipo, per id gruppo ed infine per num aggi
		// attenzione!! cambiare l'ordinamento puo' falsare i dati in visualizzazione
		List<?> listVectorW9Aggiudicatarie = this.sqlManager.getListVector(
						"select w1.codgara, w1.codlott, w1.num_appa, w1.num_aggi, w1.id_tipoagg, t1.tab1desc, w1.codimp, i1.nomest, w1.id_gruppo, w1.ruolo, t2.tab1desc,"
								+ " w1.flag_avvalimento, w1.codice_inps, w1.codice_inail, w1.codice_cassa, w1.codimp_ausiliaria, i2.nomest,"
								+ " (select count(w2.id_gruppo) from w9aggi w2 where w2.codgara = w1.codgara and w2.codlott=w1.codlott and w2.num_appa=w1.num_appa and w1.id_gruppo=w2.id_gruppo) as numeroImpr"
								+ " from w9aggi w1 left outer join tab1 t2 on w1.ruolo=t2.tab1tip and t2.tab1cod='W3011'"
								+ " left outer join impr i1 on w1.codimp=i1.codimp"
								+ " left outer join impr i2 on w1.codimp_ausiliaria=i2.codimp, tab1 t1"
								+ " where w1.codgara = ? and w1.codlott = ? and w1.num_appa = ?"
								+ " and w1.id_tipoagg=t1.tab1tip and t1.tab1cod='W3010'"
								+ " order by w1.id_tipoagg asc, w1.id_gruppo asc, w1.num_aggi asc",
						new Object[] { codiceGara, codiceLotto, numAppalto });

		if (listVectorW9Aggiudicatarie != null && listVectorW9Aggiudicatarie.size() > 0) {

			for (int i = 0; i < listVectorW9Aggiudicatarie.size(); i++) {

				Vector<?> impresaDataColumn = (Vector<?>) listVectorW9Aggiudicatarie.get(i);

				@SuppressWarnings("rawtypes")
				Vector impresa = new Vector();

				// dati per impresa Aggiudicataria estratta
				// Long CodiceGara = ((JdbcParametro)impresaDataColumn.get(0)).longValue();
				// Long CodiceLotto = ((JdbcParametro)impresaDataColumn.get(1)).longValue();
				// Long NumAppalto = ((JdbcParametro)impresaDataColumn.get(2)).longValue();
				Long numeroAggiudicataria = ((JdbcParametro) impresaDataColumn.get(3)).longValue();
				Long idGruppo = ((JdbcParametro) impresaDataColumn.get(8)).longValue();
				Long tipoAggiudicataria = ((JdbcParametro) impresaDataColumn.get(4)).longValue();
				String temp = ((JdbcParametro) impresaDataColumn.get(5)).getStringValue();
				String descTipoImpresa = StringUtils.substring(temp, 0, temp.indexOf("(") - 1);
				Long numeroImpreseGruppo = ((JdbcParametro) impresaDataColumn.get(17)).longValue();

				// dati per singolo record (in caso di impresa multipla)
				String codiceImpresa = ((JdbcParametro) impresaDataColumn.get(6)).getStringValue();
				String nomeEstesoImpr = null;
				String nomeEstesoImprTemp = ((JdbcParametro) impresaDataColumn.get(7)).getStringValue();

				// GENERO IDENTIFICATIVO IMPRESA
				// basato sul num aggi per le imprese singole
				// basato sull'id gruppo per imprese multiple
				String identificativoImpresa = "";
				if (tipoAggiudicataria.equals(new Long(3)) || tipoAggiudicataria.equals(new Long(4))) {
					identificativoImpresa = codiceImpresa.toString();
				} else {
					identificativoImpresa = "Gruppo_R" + idGruppo.toString();
				}

				// GENERO IL NOME DELL'IMPRESA AGGIUDICATARIA
				// per le imprese multiple e' la concatenazione 
				// di tutti i nomi estesi delle imprese associate
				if (numeroImpreseGruppo > 0) {
					StringBuffer bufferNomiImprese = new StringBuffer();

					for (int j = 0; j < numeroImpreseGruppo && (i + j) < listVectorW9Aggiudicatarie.size(); j++) {

						impresaDataColumn = (Vector<?>) listVectorW9Aggiudicatarie.get(i + j);

						String tempCodiceImpresa = ((JdbcParametro) impresaDataColumn.get(6)).getStringValue();
						String tempDescRuoloImpresa = ((JdbcParametro) impresaDataColumn.get(10)).getStringValue();
						String tempNomEstesoImpresa = ((JdbcParametro) impresaDataColumn.get(7)).getStringValue();

						if (tempNomEstesoImpresa == null || tempNomEstesoImpresa.equals("")) {
							bufferNomiImprese.append(" <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>");

						} else {
							bufferNomiImprese.append(" - ");

							// Attenzione!! non sarebbe corretto l'inserimetno di codice html nel segmento di logica
							// ma non era possibile fare altrimenti per la visualizzazione della scheda per la singola impresa 
							// tramite pressione del link associato
							bufferNomiImprese.append("<a href=\"javascript:visualizzaImpresaScheda('" + tempCodiceImpresa + "')\">"
									+ tempNomEstesoImpresa + "</a>");

						}
						if (1 == tipoAggiudicataria) {
							bufferNomiImprese.append(" (");
							bufferNomiImprese.append(tempDescRuoloImpresa);
							bufferNomiImprese.append(")");
						}

						bufferNomiImprese.append("<br>");

					}

					nomeEstesoImpr = bufferNomiImprese.substring(0, bufferNomiImprese.length() - 4);

					i = i + numeroImpreseGruppo.intValue() - 1;
				} else {
					
					if (nomeEstesoImprTemp == null || nomeEstesoImprTemp.equals("")) {
						nomeEstesoImpr = " <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>";
					} else {
						// Attenzione!! non sarebbe corretto l'inserimetno di codice html nel segmento di logica
						// ma non era possibile fare altrimenti per la visualizzazione della scheda per la singola impresa 
						// tramite pressione del link associato
						nomeEstesoImpr = "<a href=\"javascript:visualizzaImpresaScheda('" + codiceImpresa + "')\">" + nomeEstesoImprTemp + "</a>";
					}
				}

				impresa.add(codiceGara);
				impresa.add(numAppalto);
				impresa.add(codiceLotto);
				impresa.add(numeroAggiudicataria);
				impresa.add(idGruppo);
				impresa.add(identificativoImpresa);
				impresa.add(tipoAggiudicataria);
				impresa.add(descTipoImpresa);
				impresa.add(nomeEstesoImpr);
				impresa.add(numeroImpreseGruppo);
				
				listaW9Aggiudicatarie.add(impresa);

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getListaW9Imprese: fine metodo");
		}

		return listaW9Aggiudicatarie;
	}

	@SuppressWarnings("unchecked")
	public List<Vector<?>> getListaAggiudicatariEsito(PageContext pageContext, Long codiceGara, Long numPubb) throws SQLException, GestoreException {

		if (logger.isDebugEnabled()) {
			logger.debug("getListaAggiudicatariEsito: inizio metodo");
		}

		// non esistendo un bean che descriva i dati necessari in visualizzazione
		// ne viene creato uno tramite vettori
		List<Vector<?>> listaAggiudicatari =  new Vector<Vector<?>>();
 
		// seleziono tutte le aggiudicatarie ordinate per tipo, per id gruppo ed infine per num aggi
		// attenzione!! cambiare l'ordinamento puo' falsare i dati in visualizzazione
		List<?> listVectorAggiudicatari = this.sqlManager.getListVector(
						"select w1.codgara, w1.num_pubb, w1.num_aggi, w1.id_tipoagg, t1.tab1desc, w1.codimp, i1.nomest, w1.id_gruppo, w1.ruolo, t2.tab1desc,"
								+ " w1.codice_inps, w1.codice_inail, w1.codice_cassa,"
								+ " (select count(w2.id_gruppo) from esiti_aggiudicatari w2 where w2.codgara = w1.codgara and w2.num_pubb=w1.num_pubb and w1.id_gruppo=w2.id_gruppo) as numeroImpr"
								+ " from esiti_aggiudicatari w1 left outer join tab1 t2 on w1.ruolo=t2.tab1tip and t2.tab1cod='W3011'"
								+ " left outer join impr i1 on w1.codimp=i1.codimp, tab1 t1"
								+ " where w1.codgara = ? and w1.num_pubb = ?"
								+ " and w1.id_tipoagg=t1.tab1tip and t1.tab1cod='W3010'"
								+ " order by w1.id_tipoagg asc, w1.id_gruppo asc, w1.num_aggi asc",
						new Object[] { codiceGara, numPubb });

		if (listVectorAggiudicatari != null && listVectorAggiudicatari.size() > 0) {

			for (int i = 0; i < listVectorAggiudicatari.size(); i++) {

				Vector<?> impresaDataColumn = (Vector<?>) listVectorAggiudicatari.get(i);

				@SuppressWarnings("rawtypes")
				Vector impresa = new Vector();

				// dati per impresa Aggiudicataria estratta
				Long numeroAggiudicataria = ((JdbcParametro) impresaDataColumn.get(2)).longValue();
				Long idGruppo = ((JdbcParametro) impresaDataColumn.get(7)).longValue();
				Long tipoAggiudicataria = ((JdbcParametro) impresaDataColumn.get(3)).longValue();
				String temp = ((JdbcParametro) impresaDataColumn.get(4)).getStringValue();
				String descTipoImpresa = StringUtils.substring(temp, 0, temp.indexOf("(") - 1);
				Long numeroImpreseGruppo = ((JdbcParametro) impresaDataColumn.get(13)).longValue();

				// dati per singolo record (in caso di impresa multipla)
				String codiceImpresa = ((JdbcParametro) impresaDataColumn.get(5)).getStringValue();
				String nomeEstesoImpr = null;
				String nomeEstesoImprTemp = ((JdbcParametro) impresaDataColumn.get(6)).getStringValue();

				// GENERO IDENTIFICATIVO IMPRESA
				// basato sul num aggi per le imprese singole
				// basato sull'id gruppo per imprese multiple
				String identificativoImpresa = "";
				if (tipoAggiudicataria.equals(new Long(3))) {
					identificativoImpresa = codiceImpresa.toString();
				} else {
					identificativoImpresa = "Gruppo_R" + idGruppo.toString();
				}

				// GENERO IL NOME DELL'IMPRESA AGGIUDICATARIA
				// per le imprese multiple e' la concatenazione 
				// di tutti i nomi estesi delle imprese associate
				if (numeroImpreseGruppo > 0) {
					StringBuffer bufferNomiImprese = new StringBuffer();

					for (int j = 0; j < numeroImpreseGruppo && (i + j) < listVectorAggiudicatari.size(); j++) {

						impresaDataColumn = (Vector<?>) listVectorAggiudicatari.get(i + j);

						String tempCodiceImpresa = ((JdbcParametro) impresaDataColumn.get(5)).getStringValue();
						String tempDescRuoloImpresa = ((JdbcParametro) impresaDataColumn.get(9)).getStringValue();
						String tempNomEstesoImpresa = ((JdbcParametro) impresaDataColumn.get(6)).getStringValue();

						if (tempNomEstesoImpresa == null || tempNomEstesoImpresa.equals("")) {
							bufferNomiImprese.append(" <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>");

						} else {
							bufferNomiImprese.append(" - ");

							// Attenzione!! non sarebbe corretto l'inserimetno di codice html nel segmento di logica
							// ma non era possibile fare altrimenti per la visualizzazione della scheda per la singola impresa 
							// tramite pressione del link associato
							bufferNomiImprese.append("<a href=\"javascript:visualizzaImpresaScheda('" + tempCodiceImpresa + "')\">"
									+ tempNomEstesoImpresa + "</a>");

						}
						if (1 == tipoAggiudicataria) {
							bufferNomiImprese.append(" (");
							bufferNomiImprese.append(tempDescRuoloImpresa);
							bufferNomiImprese.append(")");
						}

						bufferNomiImprese.append("<br>");

					}

					nomeEstesoImpr = bufferNomiImprese.substring(0, bufferNomiImprese.length() - 4);

					i = i + numeroImpreseGruppo.intValue() - 1;
				} else {
					
					if (nomeEstesoImprTemp == null || nomeEstesoImprTemp.equals("")) {
						nomeEstesoImpr = " <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>";
					} else {
						// Attenzione!! non sarebbe corretto l'inserimetno di codice html nel segmento di logica
						// ma non era possibile fare altrimenti per la visualizzazione della scheda per la singola impresa 
						// tramite pressione del link associato
						nomeEstesoImpr = "<a href=\"javascript:visualizzaImpresaScheda('" + codiceImpresa + "')\">" + nomeEstesoImprTemp + "</a>";
					}
				}

				impresa.add(codiceGara);
				impresa.add(numPubb);
				impresa.add(numeroAggiudicataria);
				impresa.add(idGruppo);
				impresa.add(identificativoImpresa);
				impresa.add(tipoAggiudicataria);
				impresa.add(descTipoImpresa);
				impresa.add(nomeEstesoImpr);
				impresa.add(numeroImpreseGruppo);
				
				listaAggiudicatari.add(impresa);

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getListaAggiudicatariEsito: fine metodo");
		}

		return listaAggiudicatari;
	}
	
	/**
	 * Delete di un gruppo di impresa.
	 * 
	 * @param codiceGara
	 * @param numAppalto
	 * @param codiceLotto
	 * @param idGruppo
	 * @throws SQLException
	 */
	public void deleteGruppoImprese(Long flusso, Long codiceGara, Long numAppalto, Long codiceLotto, Long idGruppo) throws SQLException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getListaW9Imprese: inizio metodo");
		}
		
		this.sqlManager.update("delete from W9AGGI where CODGARA=? and NUM_APPA=? and CODLOTT=? and ID_GRUPPO=? ", new Object[] { codiceGara, numAppalto, codiceLotto, idGruppo });
		
	    this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='1' WHERE  CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = 1",
	            new Object[] { codiceGara, codiceLotto, flusso}) ;
		
	}

	/**
	 * Delete di un gruppo di impresa.
	 * 
	 * @param codiceGara
	 * @param numPubb
	 * @param idGruppo
	 * @throws SQLException
	 */
	public void deleteGruppoImpreseEsito(Long codiceGara, Long numPubb, Long idGruppo) throws SQLException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("deleteGruppoImpreseEsito: inizio metodo");
		}
		
		this.sqlManager.update("delete from ESITI_AGGIUDICATARI where CODGARA=? and NUM_PUBB=? and ID_GRUPPO=? ", new Object[] { codiceGara, numPubb, idGruppo });
		
	}
	/**
	 * Delete impresa singola.
	 * 
	 * @param codiceGara
	 * @param numAppalto
	 * @param codiceLotto
	 * @param numImpr
	 * @throws SQLException
	 */
	public void deleteImpresaSingola(Long flusso, Long codiceGara, Long numAppalto, Long codiceLotto, Long numImpr) throws SQLException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("getListaW9Imprese: inizio metodo");
		} 
		
		this.sqlManager.update("delete from W9AGGI where CODGARA=? and NUM_APPA=? and CODLOTT=? and NUM_AGGI=? ", new Object[] { codiceGara, numAppalto, codiceLotto, numImpr });

	    this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='1' WHERE  CODGARA = ? AND CODLOTT = ? AND FASE_ESECUZIONE = ? AND NUM = 1",
	            new Object[] { codiceGara, codiceLotto, flusso}) ;
		
	}

	/**
	 * Delete impresa singola.
	 * 
	 * @param codiceGara
	 * @param numPubb
	 * @param numImpr
	 * @throws SQLException
	 */
	public void deleteImpresaSingolaEsito(Long codiceGara, Long numPubb, Long numImpr) throws SQLException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("deleteImpresaSingolaEsito: inizio metodo");
		} 
		
		this.sqlManager.update("delete from ESITI_AGGIUDICATARI where CODGARA=? and NUM_PUBB=? and NUM_AGGI=? ", new Object[] { codiceGara, numPubb, numImpr });
	}
	
	/**
	 * Delete imprese selezionate dalla lista.
	 * 
	 * @param keys
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public void deleteImpreseSelez(String[] keys) throws NumberFormatException, SQLException {
		
		for (int i = 0; i < keys.length; i++) {
			String[] arrayChiave = keys[i].split(";");

			if (arrayChiave.length == 5) {
				this.deleteImpresaSingola(Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]),
						Long.parseLong(arrayChiave[3]),Long.parseLong(arrayChiave[4]));
			} else if (arrayChiave.length == 6) {
				// Delete gruppo di impresa
				this.deleteGruppoImprese(Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]),
						Long.parseLong(arrayChiave[3]),Long.parseLong(arrayChiave[5]));
			}
		}
	}


	public void insertW9Aggiudicataria(Long codGara, Long numAppa,	Long codLott, Long numImpr, Long numRagg) throws GestoreException{
		
	  try {
	    // caso impresa singola
	    if (numRagg == null) {
	      String getAllMemberImpr = "select CODIMP, TIPOAGG from W9IMPRESE where CODGARA=? and CODLOTT=? and NUM_IMPR=?";
      	List<?> listVectorW9ImpresePartecipanti = this.sqlManager.getListVector(getAllMemberImpr,new Object[]{codGara, codLott, numImpr});
  			if (listVectorW9ImpresePartecipanti != null && listVectorW9ImpresePartecipanti.size() > 0) {
  				for (int j = 0; j < listVectorW9ImpresePartecipanti.size(); j++) {
  					Vector<?> impresaDataColumn = (Vector<?>) listVectorW9ImpresePartecipanti.get(j);
  
  					// dati per impresa Aggiudicataria estratta
  					String codImp = ((JdbcParametro)impresaDataColumn.get(0)).getStringValue();
  					Long tipoAgg = ((JdbcParametro)impresaDataColumn.get(1)).longValue();
  					
  		    	Long numAggi = null;
  		    	Long flagAvvalimento = 0L;
  		    		
  		    	// calcolo il max+1 per il numero aggiudicataria
  		    	String numAggiSqlMaxP1 = "select max(num_aggi) from w9aggi where codgara = ? and codlott = ? and num_appa = ?";
  		    	numAggi = (Long) this.sqlManager.getObject(numAggiSqlMaxP1, new Object[] {codGara, codLott, numAppa});
  					if (numAggi == null) {
  						numAggi = 0L;
  					}
  					numAggi++;
  					
  					String insertW9Aggi = "INSERT INTO w9aggi (CODGARA,NUM_APPA,CODLOTT,NUM_AGGI,ID_TIPOAGG,FLAG_AVVALIMENTO,CODIMP) VALUES (?,?,?,?,?,?,?)";
  					Object[] valueInsertW9Aggi = new Object[]{codGara, numAppa, codLott, numAggi, tipoAgg, flagAvvalimento, codImp};
  					this.sqlManager.update(insertW9Aggi, valueInsertW9Aggi);
  				}
  			}
    	} else {
    	  // caso impresa multipla
    		String getAllMemberImpr = "SELECT CODIMP, TIPOAGG, RUOLO FROM w9imprese where codgara = ? and codlott = ? and num_ragg = ?";
    		List<?> listVectorW9ImpresePartecipanti = this.sqlManager.getListVector(getAllMemberImpr,new Object[] { codGara, codLott, numRagg } );
    		if (listVectorW9ImpresePartecipanti != null && listVectorW9ImpresePartecipanti.size() > 0) {

  				// calcolo il max+1 per il l'id gruppo
  				String idGruppoSqlMaxP1 = "select max(id_gruppo) from w9aggi where codgara = ? and codlott = ? and num_appa = ?";	
  				Long idGruppo = null;
  				idGruppo = (Long) this.sqlManager.getObject(idGruppoSqlMaxP1, new Object[] {codGara, codLott, numAppa});
  				if (idGruppo == null) {
  					idGruppo = 0L;
  				}
  				idGruppo++; 
  				
  				for (int j = 0; j < listVectorW9ImpresePartecipanti.size(); j++) {
   					Vector<?> impresaDataColumn = (Vector<?>) listVectorW9ImpresePartecipanti.get(j);
  
  					// dati per impresa Aggiudicataria estratta
  					String codImp = ((JdbcParametro)impresaDataColumn.get(0)).getStringValue();
  					Long tipoAgg = ((JdbcParametro)impresaDataColumn.get(1)).longValue();
  					
  					Long ruolo = null;
  					if(impresaDataColumn.get(2) != null){
  						ruolo = ((JdbcParametro)impresaDataColumn.get(2)).longValue();
  					}
  					
  					Long numAggi = null;
  		    	Long flagAvvalimento = 0L;
  		    		
  		    	// calcolo il max+1 per il numero aggiudicataria
  		    	String numAggiSqlMaxP1 = "select max(num_aggi) from w9aggi where codgara = ? and codlott = ? and num_appa = ?";
  		    	numAggi = (Long) this.sqlManager.getObject(numAggiSqlMaxP1, new Object[] {codGara, codLott, numAppa});
  					if (numAggi == null) {
  						numAggi = 0L;
  					}
  					numAggi++; 
  					
  					String insertW9Aggi = "INSERT INTO w9aggi (CODGARA,NUM_APPA,CODLOTT,NUM_AGGI,ID_TIPOAGG,RUOLO,FLAG_AVVALIMENTO,CODIMP,ID_GRUPPO) VALUES (?,?,?,?,?,?,?,?,?)";
  					Object[] valueInsertW9Aggi = new Object[]{codGara, numAppa, codLott, numAggi, tipoAgg, ruolo, flagAvvalimento, codImp, idGruppo};
  					if(ruolo == null){
  						insertW9Aggi = "INSERT INTO w9aggi (CODGARA,NUM_APPA,CODLOTT,NUM_AGGI,ID_TIPOAGG,FLAG_AVVALIMENTO,CODIMP,ID_GRUPPO) VALUES (?,?,?,?,?,?,?,?)";
  						valueInsertW9Aggi = new Object[]{codGara, numAppa, codLott, numAggi, tipoAgg, flagAvvalimento, codImp, idGruppo};
  					}
  
  					this.sqlManager.update(insertW9Aggi, valueInsertW9Aggi);
  				}
  			}
    	}
		} catch (SQLException e) {
			throw new GestoreException("Errore nell'esecuzione della query per l'estrazione dei dati del Soggetto Aggiudicatario","", e);
		}
	}
}
