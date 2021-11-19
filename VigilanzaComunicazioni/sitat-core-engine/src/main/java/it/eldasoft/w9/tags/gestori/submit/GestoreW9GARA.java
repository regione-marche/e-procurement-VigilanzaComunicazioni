package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreW9GARA extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return null;
  }

  public String getCampoNumericoChiave() {
    return "CODGARA";
  }

  public String getEntita() {
    return "W9GARA";
  }

  public void postDelete(DataColumnContainer arg0) throws GestoreException {
  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {
  }

  public void postUpdate(DataColumnContainer impl) throws GestoreException {

    try {
      Long codgara = impl.getLong("W9GARA.CODGARA");
      String updateRupLotti = "UPDATE W9LOTT SET RUP =? WHERE CODGARA=?";

      TransactionStatus status = this.sqlManager.startTransaction();
      String sqlSelectRup = "select RUP from W9GARA where CODGARA = ?";
      String rupLotti;
      rupLotti = (String) this.sqlManager.getObject(sqlSelectRup,
          new Object[] { codgara });

      this.sqlManager.update(updateRupLotti, new Object[] { rupLotti, codgara });
      this.sqlManager.commitTransaction(status);

    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il aggiornamento dei Rup dei Lotti",
          "aggiornamento.ricalcoloCostiProgramma", e);
    }
  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    String sqlSelectCancellazione = "";
    GeneManager gene = this.getGeneManager();

    // si ricava il valore della chiave primaria di W9GARA
    Long codgara = impl.getColumn("W9GARA.CODGARA").getValue().longValue();

    // l'array listaEntita contiene le tabelle in cui verranno fatte le
    // eliminazioni degli elementi collegati ad una gara
    String[] listaEntita = new String[] { "W9LOTT", "W9APPA", "W9AGGI",
        "W9INCA", "W9INIZ", "W9AVAN", "W9CONC", "W9COLL", "W9SOSP", "W9VARI",
        "W9ACCO", "W9SUBA", "W9RITA", "W9SIC", "W9TECPRO", "W9REGCON",
        "W9MISSIC", "W9INASIC", "W9INFOR", "W9CANT", "W9DOCGARA", "W9PUBB",
        "W9ESITO", "W9IMPRESE", "W9PUBBLICAZIONI" };

    for (int k = 0; k < listaEntita.length; k++) {
      sqlSelectCancellazione = "DELETE FROM "
          + listaEntita[k]
          + " WHERE CODGARA = "
          + codgara;
      try {
        gene.getSql().execute(sqlSelectCancellazione);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore durante la cancellazione di una Gara",
            "cancellazione.gara", e);
      }
    }
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
	  ProfiloUtente profiloUtente = (ProfiloUtente) this.getRequest().getSession().getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);
	  impl.addColumn("W9GARA.SYSCON", new Long(profiloUtente.getId()));

    super.preInsert(status, impl);

    if (impl.isColumn("W9PUBB.CODGARA")) {
      impl.setValue("W9PUBB.CODGARA", impl.getLong("W9GARA.CODGARA"));
      impl.setValue("W9PUBB.CODLOTT", new Long(1));
      impl.setValue("W9PUBB.NUM_APPA", new Long(1));
      impl.setValue("W9PUBB.NUM_PUBB", new Long(1));
      this.inserisci(status, impl, new GestoreW9PUBB());
    } else {

      LoginManager loginManager = (LoginManager) UtilitySpring.getBean("loginManager",
          getServletContext(), LoginManager.class);
      
      W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
          getServletContext(), W9Manager.class);
      
      String codUffInt = (String) this.getRequest().getSession().getAttribute(
          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      
      try {
        if (w9Manager.isStazioneAppaltanteConPermessiDaApplicareInCreazione(codUffInt)) {
          w9Manager.updateAttribuzioneModello(impl.getLong("W9GARA.CODGARA"), new Long(1));
        }
      } catch (SQLException e) {
        throw new GestoreException("Errore nell'inserimento dei permessi nella W9PERMESSI " +
        		"per la gara in creazione", null, e);
      }
      
      if (impl.isColumn("W9GARA.RUP") && impl.getColumn("W9GARA.RUP").getValue().getValue() == null) {
          
        try {
          // CREARE UN TECNICO e ASSOCIARLO ALL'UTENTE CONNESSO
          Account account = loginManager.getAccountById(profiloUtente.getId());
          
          DataColumn uffint = new DataColumn("TECNI.CGENTEI", new JdbcParametro(
              JdbcParametro.TIPO_TESTO, codUffInt));

          DataColumn nometec = new DataColumn("TECNI.NOMTEC", new JdbcParametro(
                JdbcParametro.TIPO_TESTO, account.getNome()));
          DataColumn syscon = new DataColumn("TECNI.SYSCON", new JdbcParametro(
              JdbcParametro.TIPO_NUMERICO, new Long(account.getIdAccount())));
          String pk = this.geneManager.calcolaCodificaAutomatica("TECNI", "CODTEC");

          impl.getColumn("W9GARA.RUP").setValue(
              new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
          
          DataColumn codiceTecnico = new DataColumn("TECNI.CODTEC",
              new JdbcParametro(JdbcParametro.TIPO_TESTO, pk));
          
          DataColumnContainer dcc = new DataColumnContainer(new DataColumn[] {
              codiceTecnico, uffint, nometec, syscon });

          if (StringUtils.isNotEmpty(account.getCodfisc())) {
            dcc.addColumn("TECNI.CFTEC", new JdbcParametro(
                JdbcParametro.TIPO_TESTO, account.getCodfisc()));
          }
          
          if (StringUtils.isNotEmpty(account.getEmail())) { 
            dcc.addColumn("TECNI.EMATEC", JdbcParametro.TIPO_TESTO, account.getEmail());
          }
          
          dcc.getColumn("TECNI.CODTEC").setChiave(true);
          dcc.insert("TECNI", this.sqlManager);
          
        } catch(CriptazioneException ce) {
          throw new GestoreException("", null, ce);
        } catch(SQLException se) {
          throw new GestoreException("", null, se);
        }
      }
    }
  }


  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

    // Gestore per il salvataggio dei documenti che costituiscono il bando di
    // gara
    GestoreW9DOCGARA gestoreW9DOCGARA = new GestoreW9DOCGARA();
    AbstractGestoreChiaveNumerica gestore = new DefaultGestoreEntitaChiaveNumerica(
        "W9DOCGARA", "NUMDOC", new String[] { "CODGARA" }, this.getRequest());
    gestoreW9DOCGARA.setForm(this.getForm());

    gestoreW9DOCGARA.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
        gestore, "W9DOCGARA",
        new DataColumn[] { impl.getColumn("W9GARA.CODGARA") }, null);

    // 28/09/2011 Gestione della pubblicita', entita W9PUBB
    // Se in aggiornamento di una gara esistente bisogna controllare
    // se la riga di W9PUBB esiste ed in tal caso aggiornarla.
    // Se non esiste e' necessario provvedere al suo inserimento
    if (impl.isColumn("W9PUBB.CODGARA")) {
      if (impl.getColumn("W9PUBB.CODGARA").getValue().getValue() == null) {
        impl.setValue("W9PUBB.CODGARA", impl.getLong("W9GARA.CODGARA"));
        impl.setValue("W9PUBB.CODLOTT", new Long(1));
        impl.setValue("W9PUBB.NUM_APPA", new Long(1));
        impl.setValue("W9PUBB.NUM_PUBB", new Long(1));
        this.inserisci(status, impl, new GestoreW9PUBB());
      } else {
        this.update(status, impl, new GestoreW9PUBB());
      }
    }

    if (impl.isColumn("W9GARA.FLAG_ENTE_SPECIALE")) {
      DataColumn campoFlagEnteSpeciale = impl.getColumn("W9GARA.FLAG_ENTE_SPECIALE");
      if (campoFlagEnteSpeciale.isModified()) {
        Long codiceGara = impl.getLong("W9GARA.CODGARA");

        try {
          if (campoFlagEnteSpeciale.getValue().getValue() != null) {
            this.sqlManager.update(
                "update W9LOTT set FLAG_ENTE_SPECIALE=? where CODGARA=?",
                new Object[] {
                    campoFlagEnteSpeciale.getValue().getStringValue(),
                    codiceGara });
          } else {
            this.sqlManager.update(
                "update W9LOTT set FLAG_ENTE_SPECIALE=null where CODGARA=?",
                new Object[] { codiceGara });
          }
        } catch (SQLException e) {
          throw new GestoreException("Errore nell'aggiornamento del campo "
              + "FLAG_ENTE_SPECIALE di tutti i lotti della gara", null, e);
        }
      }
    }
  }

  /**
   * Setta il campo FILE_ALLEGATO nel container in modo da svuotarlo o da
   * effettuare il salvataggio del file uploadato
   * 
   * @param dcc
   *        container dei dati inseriti nella form
   * @param formFile
   *        oggetto contenente il bytearray con il contenuto del file uploadato
   * @param entita
   *        nome dell'entita in cui inserire il BLOB
   * @throws GestoreException
   */
  /*
   * public static void setFileAllegato(DataColumnContainer dcc, FormFile
   * formFile, String entita) throws GestoreException { final double
   * MAX_FILE_SIZE = Math.pow(2, 20) * 10; if (dcc.isColumn("FILEDAALLEGARE") &&
   * dcc.getString("FILEDAALLEGARE") != null &&
   * !dcc.getString("FILEDAALLEGARE").trim().equals("")) { ByteArrayOutputStream
   * baos = null; try { if (dcc.getString("FILEDAALLEGARE").equals("canc")) { //
   * se si deve effettuare la cancellazione, si crea una colonna con // valore
   * null ma con un valore originario fittizio diverso da null, // in modo da
   * forzare l'update a null dcc.addColumn(entita + ".FILE_ALLEGATO",
   * JdbcParametro.TIPO_BINARIO, null); ByteArrayOutputStream oldVal = new
   * ByteArrayOutputStream(); oldVal.write("FITTIZIO".getBytes());
   * dcc.getColumn(entita + ".FILE_ALLEGATO").setObjectOriginalValue(oldVal); }
   * else { // se il dato e' stato aggiornato, allora si crea la colonna con lo
   * // stream ricevuto mediante upload if(formFile.getFileSize() >
   * MAX_FILE_SIZE){ throw new
   * GestoreException("Il file selezionato ha una dimensione " +
   * "superiore al massimo consentito (10 MB)", "upload.overflow", null ); }
   * else { baos = new ByteArrayOutputStream();
   * baos.write(formFile.getFileData()); dcc.addColumn(entita +
   * ".FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, baos); } } } catch
   * (FileNotFoundException e) { throw new
   * GestoreException("File da caricare non trovato", "upload", e); } catch
   * (IOException e) { throw new GestoreException(
   * "Si e' verificato un errore durante la scrittura del buffer per il salvataggio del file allegato su DB"
   * , "upload", e); } } }
   */

}