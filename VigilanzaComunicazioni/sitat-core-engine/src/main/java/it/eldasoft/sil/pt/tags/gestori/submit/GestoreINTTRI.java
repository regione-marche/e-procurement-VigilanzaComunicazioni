package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.UtilityStruts;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.bl.ControlloCpv;
import it.eldasoft.sil.pt.bl.GestioneServiziCUPManager;
import it.eldasoft.sil.pt.bl.PtManager;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.utils.utility.UtilityStringhe;
import it.eldasoft.w9.common.CostantiSitatSA;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreINTTRI extends AbstractGestoreChiaveNumerica {

	/** manager per operazioni di interrogazione del db. */
	private GestioneServiziCUPManager gestioneServiziCUPManager;

	private PtManager ptManager;
	
	@Override
	public String[] getAltriCampiChiave() {
		return new String[] { "CONTRI" };
	}

	@Override
	public String getCampoNumericoChiave() {
		return "CONINT";
	}

	@Override
	public String getEntita() {
		return "INTTRI";
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		super.setRequest(request);
		// Estraggo il manager di Piattaforma Gare
		this.gestioneServiziCUPManager = (GestioneServiziCUPManager) UtilitySpring.getBean("gestioneServiziCUPManager", this.getServletContext(), GestioneServiziCUPManager.class);
		this.ptManager = (PtManager) UtilitySpring.getBean("ptManager", this.getServletContext(), PtManager.class);
	}

	@Override
	public void afterDeleteEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		try {
			Long contri = impl.getLong("INTTRI.CONTRI");
			Long conint = impl.getLong("INTTRI.CONINT");
			this.sqlManager.update("delete from RIS_CAPITOLO where CONTRI=? and CONINT=?", new Object[] { contri, conint });

			PtManager m = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
			m.aggiornaCostiPiatri(contri);
			// se lo stato attuale del programma è pubblicato o esportato lo
			// metto in modifica
			m.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));

		} catch (Exception e) {
			throw new GestoreException("Errore durante il ricalcolo dei costi complessivi del Programma", "aggiornamento.ricalcoloCostiProgramma", e);
		}
	}

	@Override
	public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		try {
			Long contri = impl.getLong("INTTRI.CONTRI");
			Long conint = impl.getLong("INTTRI.CONINT");

			PtManager m = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
			m.aggiornaCostiPiatri(contri);
			// se lo stato attuale del programma è pubblicato o esportato lo metto in modifica
			m.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
			// aggiorno la sequenza corretta degli interventi
			m.verificaSequenzaInterventi(contri);

		  // Inserimento IMPORTIINTERVENTI (personalizzazione Comune di Pisa)
	    String profilo = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
	    if (geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.INTTRI_CAMPI_COMUNE_PISA")) {
	      for (int i=1; i < 4; i++) {
	        if (impl.isModifiedColumn("HOUTRI"+i)) {
	          this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,7,?,?)",
	              new Object[] { contri, conint, new Long(i), impl.getDouble("HOUTRI"+i) } );
	        }
	        if (impl.isModifiedColumn("IASTRI"+i)) {
            this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,8,?,?)",
                new Object[] { contri, conint, new Long(i), impl.getDouble("IASTRI"+i) } );
          }
	      }
	      
	      if (impl.isModifiedColumn("HOUTRI9")) {
          this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,7,?,?)",
              new Object[] { contri, conint,  new Long(9), impl.getDouble("HOUTRI9") } );
        }
        if (impl.isModifiedColumn("IASTRI9")) {
          this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,8,?,?)",
              new Object[] { contri, conint, new Long(9), impl.getDouble("IASTRI9") } );
        }
	    }
			
		} catch (Exception e) {
			throw new GestoreException("Errore durante il ricalcolo dei costi complessivi del Programma", "aggiornamento.ricalcoloCostiProgramma", e);
		}
	}

	@Override
	public void afterUpdateEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		try {
			Long contri = impl.getLong("INTTRI.CONTRI");
			PtManager m = (PtManager) UtilitySpring.getBean("ptManager", getServletContext(), PtManager.class);
			m.aggiornaCostiPiatri(contri);
			// se lo stato attuale del programma è pubblicato o esportato lo  metto in modifica
			m.updateStatoProgramma(contri, new Long(3), new Long(2), new Long(5));
			// aggiorno la sequenza corretta degli interventi solo
			// se modifico l'annualità di riferimento
			if (impl.isModifiedColumn("INTTRI.ANNRIF")) {
				m.verificaSequenzaInterventi(contri);
			}
			///////////////////////////////////////////////////
			//Aprile 2019
			//Se viene modificato il CUI di un intervento legato ad una richiesta di intervento/acquisto, aggiornare il CUI di quest’ultima
			if (impl.isModifiedColumn("INTTRI.CUIINT")) {
			  Long conint = impl.getLong("INTTRI.CONINT");
			  String selectApprovazioni = "select conint from PTAPPROVAZIONI where ID_PROGRAMMA=? and ID_INTERV_PROGRAMMA=?";
              Long conintPta = (Long) sqlManager.getObject(selectApprovazioni, new Object[] { contri, conint } );
              if(conintPta != null){
                String newCuiint = impl.getString("INTTRI.CUIINT");
                sqlManager.update("update INTTRI set CUIINT=? where CONTRI=0 and CONINT=?", new Object[] { newCuiint, conintPta });                
              }
            }
			// /////////////////////////////////////////////////
		} catch (Exception e) {
			throw new GestoreException("Errore durante il ricalcolo dei costi complessivi del Programma", "aggiornamento.ricalcoloCostiProgramma", e);
		}
	}

	@Override
	public void preDelete(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		String sqlSelectCancellazione = "";

		Long contri = impl.getColumn("INTTRI.CONTRI").getValue().longValue();
		Long conint = impl.getColumn("INTTRI.CONINT").getValue().longValue();

		// Cancellazione dei dati del CUP
		try {
			String sqlDeleteCUPLOC = "delete from cuploc where id in "
			  + "(select id from cupdati where contri=? and conint=?)";
      this.sqlManager.update(sqlDeleteCUPLOC, new Object[] { contri, conint });

      String sqlDeleteCUPDATI = "delete from cupdati where contri=? and conint=?";
      this.sqlManager.update(sqlDeleteCUPDATI, new Object[] { contri, conint });

		} catch (SQLException e) {
			throw new GestoreException("Errore durante la cancellazione dei dati di richiesta CUP associati all'intervento", "cancellazione.intervento", e);
		}

		//Cancellazione record di PTAPPROVAZIONI collegati all'intervento
		try {
		  this.sqlManager.update("delete from PTAPPROVAZIONI where id_programma=? and id_interv_programma=?", new Object[] { contri, conint });
		} catch (Exception e) {
		  throw new GestoreException("Errore durante la cancellazione di un'Approvazione", e.toString());
		}
        
		//cancellazione Immobili
		String[] listaEntita = new String[] { "IMMTRAI" };
		for (int k = 0; k < listaEntita.length; k++) {
		  sqlSelectCancellazione = "DELETE FROM " + listaEntita[k] + " WHERE CONTRI=? AND CONINT=?";
			try {
			  this.sqlManager.update(sqlSelectCancellazione, new Object[] { contri, conint });
			} catch (SQLException e) {
				throw new GestoreException("Errore durante la cancellazione di un Intervento (1)", "cancellazione.intervento", e);
			}
		}
		
		// Cancellazione IMPORTIINTERVENTI (personalizzazione Comune di Pisa)
    String profilo = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    if (geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.INTTRI_CAMPI_COMUNE_PISA")) {
      try {
        this.sqlManager.update(
            "DELETE FROM IMPORTIINTERVENTO WHERE CONTRI=? AND CONINT=?",
                new Object[] { contri, conint });
      } catch (SQLException e) {
        throw new GestoreException("Errore durante la cancellazione di un Intervento (2)", "cancellazione.intervento", e);
      }
    }
		
	}

	@Override
	public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		// gestione schede multiple di IMMTRAI
		GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
		gest.setGeneManager(this.geneManager);
		// Calcolo il max progressivo del cui immobili in db
		Long lcontri = impl.getLong("INTTRI.CONTRI");
		boolean univocitaCui = false;
		try {
		  ////////////////////////////////////////////////////////////////////////
	    //         Nuova gestione Calcolo CUIIM (febbraio 2019)
		  ////////////////////////////////////////////////////////////////////////		  
			this.getRequest().setAttribute("contri", lcontri);
			gest.gestioneIMMTRAIdaINTTRI(this.getRequest(), status, impl, this.getServletContext());
			// viene settato ad '1' il valore di MANTRI se il CPV primario
			// selezionato fa parte di un determinato range ed il valore di
			// TIPOIN è uguale a 'S'
			String cpvPrimario = impl.getString("INTTRI.CODCPV");
			ArrayList<String[]> listaControlli = new ArrayList<String[]>();
			listaControlli.add(new String[] { "50100000-6:50884000-5", "!50310000-1:50324200-4", "!50116510-9", "!50190000-3", "!50229000-6", "51000000-9:51900000-1" });
			listaControlli.add(new String[] { "60100000-9:60183000-4", "!60160000-7", "!60161000-4", "!6022000-6", "64120000-3:64121200-2" });
			listaControlli.add(new String[] { "60410000-5:60424120-3", "!60411000-2", "!60421000-5", "60500000-3", "60440000-4:60445000-9" });
			listaControlli.add(new String[] { "60160000-7", "60161000-4", "60411000-2", "60421000-5" });
			listaControlli.add(new String[] { "64200000-8:64228200-2", "72318000-7", "72700000-7:72720000-3" });
			listaControlli.add(new String[] { "66100000-1:66620000-3" });
			listaControlli.add(new String[] { "50310000-1:50324200-4", "72000000-5:72920000-5", "!72318000-7", "!72700000-7:72720000-3", "79342410-4" });
			listaControlli.add(new String[] { "73000000-2:73436000-7", "!73200000-4", "!73210000-7", "!73220000-0" });
			listaControlli.add(new String[] { "79210000-9:79223000-3" });
			listaControlli.add(new String[] { "79300000-7:79330000-6", "79342310-9:79342311-6" });
			listaControlli.add(new String[] { "73200000-4:73220000-0", "79342000-3", "79432100-4", "79432300-6", "79432320-2", "79432321-9", "79910000-6", "79910000-7", "98362000-8" });
			listaControlli.add(new String[] { "71000000-8:71900000-7", "!71550000-8", "79994000-8" });
			listaControlli.add(new String[] { "79341000-6:79342200-5", "!79342000-3", "79342100-4" });
			listaControlli.add(new String[] { "70300000-4:70340000-6", "90900000-6:90924000-0" });
			listaControlli.add(new String[] { "79800000-2:79824000-6", "79970000-6:79980000-7" });
			listaControlli.add(new String[] { "90400000-1:90743200-9", "!90712200-3", "90910000-9:90920000-2", "50190000-3", "50229000-6", "50243000-0" });
			listaControlli.add(new String[] { "55000000-0:55524000-9", "98340000-8:98341100-6" });
			listaControlli.add(new String[] { "60200000-0:60220000-6" });
			listaControlli.add(new String[] { "60600000-4:60653000-0", "63727000-1:63727200-3", "63000000-9:63734000-3", "!63711200-8", "!63712700-0", "!63712710-3", "!63727000-1:63727200-3",
					"98361000-1" });
			listaControlli.add(new String[] { "79100000-5:79140000-7" });
			listaControlli.add(new String[] { "79600000-0:79635000-4", "!79611000-0", "!79632000-3", "!79633000-0", "98500000-8:98514000-9", "79700000-1:79723000-8" });
			listaControlli.add(new String[] { "80100000-5:80660000-8", "!80533000-9", "!80533100-0", "!80533200-1" });
			listaControlli.add(new String[] { "85000000-9:85323000-9", "79611000-0", "!85321000-5", "!85322000-2" });
			listaControlli.add(new String[] { "79995000-5:79995200-7", "92000000-1:92700000-8", "!92230000-2", "!92231000-9", "!92232000-6" });
			String[] rangeCpv = new String[] {};
			if (impl.isColumn("INTTRI.TIPOIN")) {
				String tipoin = impl.getString("INTTRI.TIPOIN");
				boolean esiste = false;
				for (int i = 0; i < listaControlli.size(); i++) {
					rangeCpv = listaControlli.get(i);
					esiste = ControlloCpv.controlloCpv(cpvPrimario, rangeCpv);
					if (esiste) {
						break;
					}
				}
				Long norma = (Long) sqlManager.getObject("select norma from piatri where contri = ?", new Object[] { impl.getColumn("INTTRI.CONTRI").getValue().longValue() });
				if (norma.intValue() == 1) {
					if (esiste && (tipoin != null && tipoin.equals("S"))) {
						impl.setValue("INTTRI.MANTRI", "1");
					}
				}
			}
			String valoreCatint = "";
			if (impl.isColumn("INTTRI.CATINT") && impl.getString("INTTRI.CATINT") != null && !impl.getString("INTTRI.CATINT").equals("")) {
				valoreCatint = impl.getString("INTTRI.CATINT").split("-")[1];
				impl.setValue("INTTRI.CATINT", valoreCatint);
			}
		} catch (SQLException ec) {
			throw new GestoreException("Errore durante la determinazione dei CUI associati all'intervento", "cui.intervento", ec);
		}
		super.preInsert(status, impl);

		// CUP
		if (impl.isColumn("INTTRI.CUPPRG")) {
			String cupnew = UtilityStringhe.convertiNullInStringaVuota(impl.getString("INTTRI.CUPPRG"));

			boolean isIntegrazioneWsCUP = false;
			String urlIntegrazione = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
			if (StringUtils.isNotEmpty(urlIntegrazione)) {
				isIntegrazioneWsCUP = true;
			}

			String[] credenzialiWsCup = new String[3];
			if (isIntegrazioneWsCUP) {
				String cupwsuser = getRequest().getParameter("cupwsuser");
				String cupwspass = getRequest().getParameter("cupwspass");

	      Long contri = impl.getColumn("INTTRI.CONTRI").getValue().longValue();
	      Long conint = impl.getColumn("INTTRI.CONINT").getValue().longValue();

	      if (impl.isColumn("INTTRI.NPROGINT")) {
	        impl.setValue("INTTRI.NPROGINT", conint);
	      }
				
				if (StringUtils.isNotEmpty(cupwsuser) && StringUtils.isNotEmpty(cupwspass)) {
				  try {
				    credenzialiWsCup = this.gestioneServiziCUPManager.gestioneCredenzialiCUP(this.getRequest());
        
				    if (StringUtils.isNotEmpty(cupnew)) {
              this.gestioneServiziCUPManager.gestioneCUPDATI(status, impl, credenzialiWsCup, this.getRequest());
				    }
          } catch (GestoreException ge) {
            // Errore nell'interazione con i servizi CUP. Non è stato possibile verificare
            // il CUP indicato, ma il CUP lo si salva lo stesso
            try {
              this.sqlManager.update("insert into CUPDATI(ID,CUP,CONTRI,CONINT) values ((select " + sqlManager.getDBFunction("isnull", new String[] { "max(id)", "0" })
                  + "+1 from CUPDATI),?,?,?)", new Object[] { cupnew, contri, conint });
              
              UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.intervento.integrazioneCup.erroreServiziSimog", new Object[] {});
            } catch (SQLException e) {
              throw new GestoreException("Errore in aggiornamento del cup (1)", "", e);
            }
          }
				} else {
				  try {
            this.sqlManager.update("insert into CUPDATI(ID,CUP,CONTRI,CONINT) values ((select " + sqlManager.getDBFunction("isnull", new String[] { "max(id)", "0" })
                + "+1 from CUPDATI),?,?,?)", new Object[] { cupnew, contri, conint });
            
            UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.intervento.integrazioneCup.noCredenziali", new Object[] {});
          } catch (SQLException e) {
            throw new GestoreException("Errore in aggiornamento del cup (2)", "", e);
          }
				}
			}
		}

		if (impl.isColumn("INTTRI.CUIINT")) {
			String codein = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
			try {
				String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codein });
				Long contri = impl.getColumn("INTTRI.CONTRI").getValue().longValue();
				String tipoin = (String) impl.getString("INTTRI.TIPOIN");
				Long anntri = (Long) this.sqlManager.getObject("select ANNTRI from PIATRI where CONTRI=?", new Object[] { contri });
				if (StringUtils.isNotEmpty(cfein)) {
					impl.setValue("INTTRI.PRIMANN", anntri);
				  String lcuiint = impl.getString("INTTRI.CUIINT");
				  
					if (StringUtils.isNotEmpty(lcuiint)) {
						univocitaCui = controlloUnivocitaCui(lcuiint, lcontri);
						if (!univocitaCui) {
							throw new GestoreException("Errore nella valorizzazione del campo CUIINT, il codice non e' univoco.", "univocitaCuiInt.error");
						}
					} else {
						String newCuiInt = ptManager.calcolaCUIIntervento(contri, tipoin, cfein, anntri.toString());
						impl.setValue("INTTRI.CUIINT", newCuiInt);
					}
				}
			} catch (SQLException se) {
				throw new GestoreException("Errore nella valorizzazione del campo CUIINT", "", se);
			}
		}
		try {
		  Long tiprog = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where CONTRI=?", new Object[] { lcontri} );
		  //se tipro programma AliProg4 non modifico il TIPINT
		  if (tiprog != null && !tiprog.equals(new Long(3))) {
  			if (impl.isColumn("INTTRI.TIPINT")) {
  			  impl.setValue("INTTRI.TIPINT", tiprog);
  			} else {
  			  impl.addColumn("INTTRI.TIPINT", JdbcParametro.TIPO_NUMERICO, tiprog);
  			}
		  }
		} catch (SQLException se) {
		  throw new GestoreException("Errore nella valorizzazione del campo TIPINT", "", se);
		}

	}

	@Override
	public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
		// gestione schede multiple di IMMTRAI
		GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
		gest.setGeneManager(this.geneManager);
		// Calcolo il max progressivo del cui immobili in db
		Long lcontri = impl.getLong("INTTRI.CONTRI");
		String lcuiint = null;
		String lcuiintOrig = null;
		if (impl.isColumn("INTTRI.CUIINT")) {
		  lcuiint = impl.getString("INTTRI.CUIINT");
		  lcuiintOrig = impl.getColumn("INTTRI.CUIINT").getOriginalValue().toString();
		}
		String tipoin = (String) impl.getString("INTTRI.TIPOIN");
		
		boolean univocitaCui = false;

		try {
			if (StringUtils.isNotEmpty(lcuiint) && !lcuiint.equals(lcuiintOrig)) {
				univocitaCui = controlloUnivocitaCui(lcuiint, lcontri);
				if (!univocitaCui) {
					throw new GestoreException("Errore nella valorizzazione del campo CUIINT, il codice non e' univoco.", "univocitaCuiInt.error");
				}
			} else {
				if (StringUtils.isEmpty(lcuiint)) {
				  if (impl.isColumn("INTTRI.CUIINT")) {
				    Long anntri = impl.getLong("INTTRI.PRIMANN");
				    String anntriStr = "";
				    if (anntri != null) {
				      anntriStr = anntri.toString();
				    }
				    
				    String codein = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
				    String codeinStr = "";
            if (codein != null) {
              codeinStr = codein;
            }
				    
				    String cfein = (String) this.sqlManager.getObject("select CFEIN from UFFINT where CODEIN=?", new Object[] { codeinStr });				    
				    String cfeinStr = "";
            if (cfein != null) {
              cfeinStr = cfein;
            }
				  
				    String newCuiInt = this.ptManager.calcolaCUIIntervento(lcontri, tipoin, cfeinStr, anntriStr);
					  impl.setValue("INTTRI.CUIINT", newCuiInt);
					}
				}
			}
			////////////////////////////////////////////////////////
			//Nuova gestione Calcolo CUIIM (febbraio 2019)
			//////////////////////////////////////////////////////
			this.getRequest().setAttribute("contri", lcontri);

			gest.gestioneIMMTRAIdaINTTRI(this.getRequest(), status, impl, this.getServletContext());
			// viene settato ad '1' il valore di MANTRI se il CPV primario
			// selezionato fa parte di un determinato range ed il valore di
			// TIPOIN è uguale a '2'
			String cpvPrimario = impl.getString("INTTRI.CODCPV");
			ArrayList<String[]> listaControlli = new ArrayList<String[]>();
			listaControlli.add(new String[] { "50100000-6:50884000-5", "!50310000-1:50324200-4", "!50116510-9", "!50190000-3", "!50229000-6", "51000000-9:51900000-1" });
			listaControlli.add(new String[] { "60100000-9:60183000-4", "!60160000-7", "!60161000-4", "!6022000-6", "64120000-3:64121200-2" });
			listaControlli.add(new String[] { "60410000-5:60424120-3", "!60411000-2", "!60421000-5", "60500000-3", "60440000-4:60445000-9" });
			listaControlli.add(new String[] { "60160000-7", "60161000-4", "60411000-2", "60421000-5" });
			listaControlli.add(new String[] { "64200000-8:64228200-2", "72318000-7", "72700000-7:72720000-3" });
			listaControlli.add(new String[] { "66100000-1:66620000-3" });
			listaControlli.add(new String[] { "50310000-1:50324200-4", "72000000-5:72920000-5", "!72318000-7", "!72700000-7:72720000-3", "79342410-4" });
			listaControlli.add(new String[] { "73000000-2:73436000-7", "!73200000-4", "!73210000-7", "!73220000-0" });
			listaControlli.add(new String[] { "79210000-9:79223000-3" });
			listaControlli.add(new String[] { "79300000-7:79330000-6", "79342310-9:79342311-6" });
			listaControlli.add(new String[] { "73200000-4:73220000-0", "79342000-3", "79432100-4", "79432300-6", "79432320-2", "79432321-9", "79910000-6", "79910000-7", "98362000-8" });
			listaControlli.add(new String[] { "71000000-8:71900000-7", "!71550000-8", "79994000-8" });
			listaControlli.add(new String[] { "79341000-6:79342200-5", "!79342000-3", "79342100-4" });
			listaControlli.add(new String[] { "70300000-4:70340000-6", "90900000-6:90924000-0" });
			listaControlli.add(new String[] { "79800000-2:79824000-6", "79970000-6:79980000-7" });
			listaControlli.add(new String[] { "90400000-1:90743200-9", "!90712200-3", "90910000-9:90920000-2", "50190000-3", "50229000-6", "50243000-0" });
			listaControlli.add(new String[] { "55000000-0:55524000-9", "98340000-8:98341100-6" });
			listaControlli.add(new String[] { "60200000-0:60220000-6" });
			listaControlli.add(new String[] { "60600000-4:60653000-0", "63727000-1:63727200-3", "63000000-9:63734000-3", "!63711200-8", "!63712700-0", "!63712710-3", "!63727000-1:63727200-3",
					"98361000-1" });
			listaControlli.add(new String[] { "79100000-5:79140000-7" });
			listaControlli.add(new String[] { "79600000-0:79635000-4", "!79611000-0", "!79632000-3", "!79633000-0", "98500000-8:98514000-9", "79700000-1:79723000-8" });
			listaControlli.add(new String[] { "80100000-5:80660000-8", "!80533000-9", "!80533100-0", "!80533200-1" });
			listaControlli.add(new String[] { "85000000-9:85323000-9", "79611000-0", "!85321000-5", "!85322000-2" });
			listaControlli.add(new String[] { "79995000-5:79995200-7", "92000000-1:92700000-8", "!92230000-2", "!92231000-9", "!92232000-6" });
			String[] rangeCpv = new String[] {};
			if (impl.isColumn("INTTRI.TIPOIN")) {
				boolean esiste = false;
				for (int i = 0; i < listaControlli.size(); i++) {
					rangeCpv = listaControlli.get(i);
					esiste = ControlloCpv.controlloCpv(cpvPrimario, rangeCpv);
					if (esiste) {
						break;
					}
				}
				Long norma = (Long) sqlManager.getObject("select norma from piatri where contri = ?", new Object[] { impl.getColumn("INTTRI.CONTRI").getValue().longValue() });
				if (norma.intValue() == 1) {
					if (esiste && (tipoin != null && tipoin.equals("S"))) {
						impl.setValue("INTTRI.MANTRI", "1");
					}
				}
			}
			String valoreCatint = "";
			if (impl.isColumn("INTTRI.CATINT") && impl.getString("INTTRI.CATINT") != null && !impl.getString("INTTRI.CATINT").equals("")) {
				valoreCatint = impl.getString("INTTRI.CATINT").split("-")[1];
				impl.setValue("INTTRI.CATINT", valoreCatint);
			}
		} catch (SQLException ec) {
			throw new GestoreException("Errore durante la determinazione dei CUI associati all'intervento", "cui.intervento", ec);
		}

		// Integrazione LFS (se modifico CODINT metto a '2' il campo CEFINT questo perchè l'intervento non è più collegato al lavoro)
		if (impl.isColumn("INTTRI.CODINT") && impl.getColumn("INTTRI.CODINT").isModified()) {
			if (impl.isColumn("INTTRI.CEFINT")) {
				impl.setValue("INTTRI.CEFINT", "2");
			} else {
				impl.addColumn("INTTRI.CEFINT", JdbcParametro.TIPO_TESTO, "2");
			}
		}
		// CUP
		boolean isIntegrazioneWsCUP = false;
		String urlIntegrazione = ConfigManager.getValore(CostantiSitatSA.RICHIESTA_CUP_TYPE);
		if (urlIntegrazione != null && !"".equals(urlIntegrazione)) {
			isIntegrazioneWsCUP = true;
		}
		if (impl.isColumn("INTTRI.CUPPRG")) {
			String cup  = UtilityStringhe.convertiNullInStringaVuota(impl.getString("INTTRI.CUPPRG"));
			Long contri = impl.getColumn("INTTRI.CONTRI").getValue().longValue();
			Long conint = impl.getColumn("INTTRI.CONINT").getValue().longValue();
			
			if (isIntegrazioneWsCUP) {
				String cupwsuser = getRequest().getParameter("cupwsuser");
				String cupwspass = getRequest().getParameter("cupwspass");
				
				if (StringUtils.isNotEmpty(cupwsuser) && StringUtils.isNotEmpty(cupwspass)) {
				  String[] credenzialiWsCup = this.gestioneServiziCUPManager.gestioneCredenzialiCUP(this.getRequest());
				  
				  if (StringUtils.isNotEmpty(cup)) {
				    try {
				      this.gestioneServiziCUPManager.gestioneCUPDATI(status, impl, credenzialiWsCup, this.getRequest());
				    } catch (GestoreException ge) {
				      // Errore nell'interazione con i servizi CUP. Non è stato possibile verificare
				      // il CUP indicato, ma il CUP lo si salva lo stesso
				      try {
	              this.sqlManager.update("update CUPDATI set CUP=? where CONTRI=? AND CONINT=?", new Object[] { cup, contri, conint });
	              
	              UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.intervento.integrazioneCup.erroreServiziSimog", new Object[] {});
	              
		          } catch (SQLException e) {
		            throw new GestoreException("Errore in aggiornamento del cup (1)", "", e);
		          }
				    }
	        } else {
	          try {
	            this.sqlManager.update("update CUPDATI set CUP=null where CONTRI=? AND CONINT=?", new Object[] { contri, conint });
	          } catch (SQLException e) {
	            throw new GestoreException("Errore in aggiornamento del cup (2)", "", e);
	          }
	        }
				} else {
				  // Le credenziali di accesso ai servizi CUP non sono valorizzati, allora si salva il CUP
				  // e si visualizza un messaggio di warning 
				  
				  try {
				    if (StringUtils.isNotEmpty(cup)) {
				      this.sqlManager.update("update CUPDATI set CUP=? where CONTRI=? AND CONINT=?", new Object[] { cup, contri, conint });
				      
				      UtilityStruts.addMessage(this.getRequest(), "warning", "warnings.intervento.integrazioneCup.noCredenziali", null);
				    } else {
				      this.sqlManager.update("update CUPDATI set CUP=null where CONTRI=? AND CONINT=?", new Object[] { contri, conint });
				    }
          } catch (SQLException e) {
            throw new GestoreException("Errore in aggiornamento del cup (3)", "", e);
          }
				}
			} else {
			  // Senza integrazione memorizzo solo il codice cup
				try {
				  if (StringUtils.isNotEmpty(cup)) {
				    this.sqlManager.update("update CUPDATI set CUP=? where CONTRI=? AND CONINT=?", new Object[] { cup, contri, conint });
				  } else {
				    this.sqlManager.update("update CUPDATI set CUP=null where CONTRI=? AND CONINT=?", new Object[] { contri, conint });
				  }
				} catch (SQLException e) {
					throw new GestoreException("Errore in aggiornamento del cup (4)", "", e);
				}
			}
		}
		
		
    // Inserimento IMPORTIINTERVENTI (personalizzazione Comune di Pisa)
    String profilo = (String) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
    if (geneManager.getProfili().checkProtec(profilo, "FUNZ", "VIS", "ALT.INTTRI_CAMPI_COMUNE_PISA")) {
      
      Long contri = impl.getLong("INTTRI.CONTRI");
      Long conint = impl.getLong("INTTRI.CONINT");
      
      try {
        for (int i=1; i < 4; i++) {
          if (impl.isModifiedColumn("HOUTRI"+i)) {
            this.sqlManager.update("Delete from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=? and ANNO=?", 
                new Object[] { contri, conint, new Long(7), new Long(i) });
            if (impl.getDouble("HOUTRI"+i) != null && impl.getDouble("HOUTRI"+i).doubleValue() != 0) {
              this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,7,?,?)",
                new Object[] { contri, conint, new Long(i), impl.getDouble("HOUTRI"+i) } );
            }
          }
          if (impl.isModifiedColumn("IASTRI"+i)) {
            this.sqlManager.update("Delete from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=? and ANNO=?", 
                new Object[] { contri, conint, new Long(8), new Long(i) });
            if (impl.getDouble("IASTRI"+i) != null && impl.getDouble("IASTRI"+i).doubleValue() != 0) {
              this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,8,?,?)",
                new Object[] { contri, conint, new Long(i), impl.getDouble("IASTRI"+i) } );
            }
          }
        }
        
        if (impl.isModifiedColumn("HOUTRI9")) {
          this.sqlManager.update("Delete from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=? and ANNO=?", 
              new Object[] { contri, conint, new Long(7), new Long(9) });
          if (impl.getDouble("HOUTRI9") != null && impl.getDouble("HOUTRI9").doubleValue() != 0) {
            this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,7,?,?)",
                new Object[] { contri, conint, new Long(9), impl.getDouble("HOUTRI9") } );
          }
        }
        if (impl.isModifiedColumn("IASTRI9")) {
          this.sqlManager.update("Delete from IMPORTIINTERVENTO where CONTRI=? and CONINT=? and TIPIMP=? and ANNO=?", 
              new Object[] { contri, conint, new Long(8), new Long(9) });
          if (impl.getDouble("IASTRI9") != null && impl.getDouble("IASTRI9").doubleValue() != 0) {
            this.sqlManager.update("Insert into IMPORTIINTERVENTO (CONTRI,CONINT,TIPIMP,ANNO,IMPORTO) values (?,?,8,?,?)",
                new Object[] { contri, conint, new Long(9), impl.getDouble("IASTRI9") } );
          }
        }
      } catch (SQLException se) {
        throw new GestoreException("Errore in aggiornamento degli IMPORTIINTERVENTI", "", se);
      }
    }
		
	}

	public void postInsert(DataColumnContainer impl) throws GestoreException {
	}

	public void postUpdate(DataColumnContainer impl) throws GestoreException {
	}

	public void postDelete(DataColumnContainer impl) throws GestoreException {
	}

	public boolean controlloUnivocitaCui(String cui, Long contri) throws SQLException {

		boolean result = false;
		Object[] paramCui = new Object[] { cui, contri, contri };
		String fromInttri = "select count(*) from inttri where cuiint = ? and contri = ? ";
		String fromInrtri = "select count(*) from inrtri where cuiint = ? and contri = ? ";
		
		fromInttri = "select count(*) from inttri where cuiint=? and contri in ("
		  + " select contri from piatri where anntri=(select anntri from piatri where contri=?) "
		  + " and tiprog=(select tiprog from piatri where contri=?))";

		fromInrtri = "select count(*) from inrtri where cuiint=? and contri in ("
		  + " select contri from piatri where anntri=(select anntri from piatri where contri=?) " 
		  + " and tiprog=(select tiprog from piatri where contri=?))";

		Long numCuiInttri = (Long) sqlManager.getObject(fromInttri, paramCui);
		Long numCuiInrtri = (Long) sqlManager.getObject(fromInrtri, paramCui);

		if (numCuiInttri + numCuiInrtri == 0) {
			result = true;
		}
		return result;

	}

	/*public String calcolaCUI(Long contri, String tipoin, String cfein, String anntri) throws SQLException, GestoreException {

		String result = null;
		Long progressivoCalcolato = new Long(0);

		String datiComuniPianiTriennali = "select coalesce(anntri,0), coalesce(cenint,''), coalesce(tiprog,0) from piatri where contri=? ";
		String maxCuiInterventi = "select coalesce(max(cuiint),'') from inttri where contri in (select contri from piatri where anntri=? and cenint=? and tiprog=?)";
		String maxCuiInterventiNonRiproposti = "select coalesce(max(cuiint),'') from inrtri where contri in (select contri from piatri where anntri=? and cenint=? and tiprog=?)";

		Object[] parametriCui = null;
		try {
			Vector datiCalcoloMaxCui = this.sqlManager.getVector(datiComuniPianiTriennali, new Object[] { contri });

			if (datiCalcoloMaxCui.size() > 0) {
				parametriCui = new Object[] { new Long(datiCalcoloMaxCui.get(0).toString()), datiCalcoloMaxCui.get(1).toString(), new Long(datiCalcoloMaxCui.get(2).toString()) };

				String maxCuiInt = (String) this.sqlManager.getObject(maxCuiInterventi, parametriCui);
				String maxCuiIntNRip = (String) this.sqlManager.getObject(maxCuiInterventiNonRiproposti, parametriCui);

				if (StringUtils.isNotEmpty(maxCuiInt)) {
					progressivoCalcolato = new Long(maxCuiInt.substring((maxCuiInt.length()-5), maxCuiInt.length()));
				}
				if (StringUtils.isNotEmpty(maxCuiIntNRip)) {
					Long progressivoCalcolatoTemp = new Long(maxCuiIntNRip.substring((maxCuiIntNRip.length()-5), maxCuiIntNRip.length()));
					if (progressivoCalcolatoTemp > progressivoCalcolato) {
						progressivoCalcolato = progressivoCalcolatoTemp;
					}
				}
			}
			progressivoCalcolato = progressivoCalcolato + 1;
			result = tipoin.concat(cfein).concat(anntri).concat(UtilityStringhe.fillLeft(
			    progressivoCalcolato.toString(), '0', 5));
		} catch (SQLException e) {
			throw new GestoreException("Errore nel calcolo del progressivo per l'intervento " + contri, "", e);
		}
		return result;

	}*/

}
