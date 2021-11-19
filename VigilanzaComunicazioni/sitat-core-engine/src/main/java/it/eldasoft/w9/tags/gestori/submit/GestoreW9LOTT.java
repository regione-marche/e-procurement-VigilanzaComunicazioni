package it.eldasoft.w9.tags.gestori.submit;

import java.sql.SQLException;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class GestoreW9LOTT extends AbstractGestoreChiaveNumerica {

  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA" };
  }

  public String getCampoNumericoChiave() {
    return "CODLOTT";
  }

  public String getEntita() {
    return "W9LOTT";
  }

  public void postDelete(DataColumnContainer impl) throws GestoreException {
    try {
      // aggiorna il numero di lotti ogni volta che viene cancellato o aggiuntto un lotto
      Long codgara = impl.getLong("W9LOTT.CODGARA");
      W9Manager m = (W9Manager) UtilitySpring.getBean("w9Manager",
          getServletContext(), W9Manager.class);
      m.aggiornaNumLotti(codgara);
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il ricalcolo dei numeri di Lotti",
          "aggiornamento.ricalcoloNumeroLotti", e);
    }
  }

  public void postInsert(DataColumnContainer impl) throws GestoreException {
    try {
      // aggiorna il numero di lotti ogni volta che viene cancellato o aggiuntto un lotto
      Long codgara = impl.getLong("W9LOTT.CODGARA");
      W9Manager m = (W9Manager) UtilitySpring.getBean("w9Manager",
          getServletContext(), W9Manager.class);
      m.aggiornaNumLotti(codgara);
    } catch (Exception e) {
      throw new GestoreException(
          "Errore durante il ricalcolo dei numeri di Lotti",
          "aggiornamento.ricalcoloNumeroLotti", e);
    }
  }

  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  public void preDelete(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
    String sqlSelectCancellazione = "";
    GeneManager geneOpere = this.getGeneManager();

    // si ricavano i valori della chiave primaria di W9LOTT
    Long codgara = impl.getColumn("W9LOTT.CODGARA").getValue().longValue();
    Long codlott = impl.getColumn("W9LOTT.CODLOTT").getValue().longValue();

    // l'array listaEntita contiene le tabelle in cui verranno fatte le le
    // eliminazioni degli elementi collegati ad un lotto
    String[] listaEntita = new String[] { "W9APPAFORN", "W9COND", "W9APPALAV", "W9LOTTCATE",
        "W9CPV", "W9PUBLOTTO" };

    for (int k = 0; k < listaEntita.length; k++) {
      sqlSelectCancellazione = "DELETE FROM "
          + listaEntita[k]
          + " WHERE CODGARA = "
          + codgara
          + " AND CODLOTT = "
          + codlott;
      try {
        geneOpere.getSql().execute(sqlSelectCancellazione);
      } catch (SQLException e) {
        throw new GestoreException(
            "Errore durante la cancellazione di un Lotto", "cancellazione.lotto", e);
      }
    }
  }

  public void preInsert(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {
	  	  
	// rendo il codice NUTS in Uppercase per evitare problemi di inserimento nel DB   
	 impl.getColumn("W9LOTT.LUOGO_NUTS").setObjectValue(StringUtils.upperCase(impl.getColumn("W9LOTT.LUOGO_NUTS").getValue().stringValue()));
	  

	 
    // viene settato ad '1' il valore di MANOD se il CPV primario
    // selezionato fa parte di un determinato range ed il valore di
    // TIPO_CONTRATTO e' uguale a 'L'
    /*String cpvPrimario = impl.getString("W9LOTT.CPV");
    ArrayList<String[]> listaControlli = new ArrayList<String[]>();
    listaControlli.add(new String[] { "50100000-6:50884000-5",
        "!50310000-1:50324200-4", "!50116510-9", "!50190000-3", "!50229000-6",
        "51000000-9:51900000-1" });
    listaControlli.add(new String[] { "60100000-9:60183000-4", "!60160000-7",
        "!60161000-4", "!6022000-6", "64120000-3:64121200-2" });
    listaControlli.add(new String[] { "60410000-5:60424120-3", "!60411000-2",
        "!60421000-5", "60500000-3", "60440000-4:60445000-9" });
    listaControlli.add(new String[] { "60160000-7", "60161000-4", "60411000-2",
        "60421000-5" });
    listaControlli.add(new String[] { "64200000-8:64228200-2", "72318000-7",
        "72700000-7:72720000-3" });
    listaControlli.add(new String[] { "66100000-1:66620000-3" });
    listaControlli.add(new String[] { "50310000-1:50324200-4",
        "72000000-5:72920000-5", "!72318000-7", "!72700000-7:72720000-3",
        "79342410-4" });
    listaControlli.add(new String[] { "73000000-2:73436000-7", "!73200000-4",
        "!73210000-7", "!73220000-0" });
    listaControlli.add(new String[] { "79210000-9:79223000-3" });
    listaControlli.add(new String[] { "79300000-7:79330000-6",
        "79342310-9:79342311-6" });
    listaControlli.add(new String[] { "73200000-4:73220000-0", "79342000-3",
        "79432100-4", "79432300-6", "79432320-2", "79432321-9", "79910000-6",
        "79910000-7", "98362000-8" });
    listaControlli.add(new String[] { "71000000-8:71900000-7", "!71550000-8",
        "79994000-8" });
    listaControlli.add(new String[] { "79341000-6:79342200-5", "!79342000-3",
        "79342100-4" });
    listaControlli.add(new String[] { "70300000-4:70340000-6",
        "90900000-6:90924000-0" });
    listaControlli.add(new String[] { "79800000-2:79824000-6",
        "79970000-6:79980000-7" });
    listaControlli.add(new String[] { "90400000-1:90743200-9", "!90712200-3",
        "90910000-9:90920000-2", "50190000-3", "50229000-6", "50243000-0" });
    listaControlli.add(new String[] { "55000000-0:55524000-9",
        "98340000-8:98341100-6" });
    listaControlli.add(new String[] { "60200000-0:60220000-6" });
    listaControlli.add(new String[] { "60600000-4:60653000-0",
        "63727000-1:63727200-3", "63000000-9:63734000-3", "!63711200-8",
        "!63712700-0", "!63712710-3", "!63727000-1:63727200-3", "98361000-1" });
    listaControlli.add(new String[] { "79100000-5:79140000-7" });
    listaControlli.add(new String[] { "79600000-0:79635000-4", "!79611000-0",
        "!79632000-3", "!79633000-0", "98500000-8:98514000-9",
        "79700000-1:79723000-8" });
    listaControlli.add(new String[] { "80100000-5:80660000-8", "!80533000-9",
        "!80533100-0", "!80533200-1" });
    listaControlli.add(new String[] { "85000000-9:85323000-9", "79611000-0",
        "!85321000-5", "!85322000-2" });
    listaControlli.add(new String[] { "79995000-5:79995200-7",
        "92000000-1:92700000-8", "!92230000-2", "!92231000-9", "!92232000-6" });
    String[] rangeCpv = new String[] {};
    if (impl.isColumn("W9LOTT.TIPO_CONTRATTO")) {
      String tipoc = impl.getString("W9LOTT.TIPO_CONTRATTO");
      boolean esiste = false;
      for (int i = 0; i < listaControlli.size(); i++) {
        rangeCpv = (String[]) listaControlli.get(i);
        esiste = ControlloCpv.controlloCpv(cpvPrimario, rangeCpv);
        if (esiste) {
          break;
        }
      }
      if (esiste && (tipoc != null && (tipoc.toUpperCase().equals("L") || tipoc.toUpperCase().equals("CL")))) {
        impl.setValue("W9LOTT.MANOD", "1");
      }
    }*/
    super.preInsert(status, impl);

    // Inserimento in W9APPAFORN e/o W9APPALAV
    String tipoContratto = "";
    if (impl.isColumn("W9LOTT.TIPO_CONTRATTO")
        && impl.getColumn("W9LOTT.TIPO_CONTRATTO").getValue().stringValue() != null) {
      tipoContratto = impl.getColumn("W9LOTT.TIPO_CONTRATTO").getValue().stringValue();
    }
    if (!tipoContratto.equals("")) {
      // dopo aver scelto il valore nel menu 'Tipo contratto'
      // vengono distinte due possibilita' 'forniture e/o servizi' e 'lavoro'
      //if (tipoContratto.toUpperCase().equals("L") || tipoContratto.toUpperCase().equals("CL")) {
        int i = 1;
        DataColumnContainer implLavori = null;
        while (impl.isColumn("W9APPALAV.ID_APPALTO_" + i)) {

          DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
          implLavori = new DataColumnContainer(campiImpl);
          Long idAppalto = implLavori.getLong("W9APPALAV.ID_APPALTO");
          if (idAppalto != null && idAppalto.longValue() == 1) {
            implLavori.setValue("W9APPALAV.ID_APPALTO",
                implLavori.getLong("W9APPALAV.VALORE_ID_APPALTO"));
            implLavori.removeColumns(new String[] { "W9APPALAV.VALORE_ID_APPALTO" });
            implLavori.addColumn("W9APPALAV.CODGARA",
                impl.getColumn("W9LOTT.CODGARA"));
            implLavori.addColumn("W9APPALAV.CODLOTT",
                impl.getColumn("W9LOTT.CODLOTT"));
            implLavori.getColumn("W9APPALAV.CODGARA").setChiave(true);
            implLavori.getColumn("W9APPALAV.CODLOTT").setChiave(true);

            this.inserisci(status, implLavori, new GestoreW9APPALAV());
          }
          i++;
        }
      //} else {
        i = 1;
        DataColumnContainer implFornitori = null;
        while (impl.isColumn("W9APPAFORN.ID_APPALTO_" + i)) {

          DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
          implFornitori = new DataColumnContainer(campiImpl);
          Long idAppalto = implFornitori.getLong("W9APPAFORN.ID_APPALTO");
          if (idAppalto != null && idAppalto.longValue() == 1) {
            implFornitori.setValue("W9APPAFORN.ID_APPALTO",
                implFornitori.getLong("W9APPAFORN.VALORE_ID_APPALTO"));
            implFornitori.removeColumns(new String[] { "W9APPAFORN.VALORE_ID_APPALTO" });
            implFornitori.addColumn("W9APPAFORN.CODGARA",
                impl.getColumn("W9LOTT.CODGARA"));
            implFornitori.addColumn("W9APPAFORN.CODLOTT",
                impl.getColumn("W9LOTT.CODLOTT"));
            implFornitori.getColumn("W9APPAFORN.CODGARA").setChiave(true);
            implFornitori.getColumn("W9APPAFORN.CODLOTT").setChiave(true);

            this.inserisci(status, implFornitori, new GestoreW9APPAFORN());
          }
          i++;
        }
      //}
    }
    // inserimento in W9COND
    int i = 1;
    DataColumnContainer implCondizioni = null;
    long idSceltaContraente = 0;
    if (impl.getColumn("W9LOTT.ID_SCELTA_CONTRAENTE").getValue().longValue() != null) {
      idSceltaContraente = impl.getColumn("W9LOTT.ID_SCELTA_CONTRAENTE").getValue().longValue().longValue();
    }
    // se la scelta contraente
    // vale o 4 o 10 viene effettuato l'inserimento in W9COND
    while (impl.isColumn("W9COND.ID_CONDIZIONE_" + i)
        && (idSceltaContraente == 4 || idSceltaContraente == 10)) {

      DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
      implCondizioni = new DataColumnContainer(campiImpl);
      Long idCondizione = implCondizioni.getLong("W9COND.ID_CONDIZIONE");
      if (idCondizione != null && idCondizione.longValue() == 1) {
        implCondizioni.setValue("W9COND.ID_CONDIZIONE",
            implCondizioni.getLong("W9COND.VALORE_ID_CONDIZIONE"));
        implCondizioni.removeColumns(new String[] { "W9COND.VALORE_ID_CONDIZIONE" });
        implCondizioni.addColumn("W9COND.CODGARA",
            impl.getColumn("W9LOTT.CODGARA"));
        implCondizioni.addColumn("W9COND.CODLOTT",
            impl.getColumn("W9LOTT.CODLOTT"));
        implCondizioni.getColumn("W9COND.CODGARA").setChiave(true);
        implCondizioni.getColumn("W9COND.CODLOTT").setChiave(true);

        this.inserisci(status, implCondizioni, new GestoreW9COND());
      }
      i++;
    }

    // gestione schede multiple altre categorie
    AbstractGestoreChiaveNumerica gestoreW9LOTTCATE = new DefaultGestoreEntitaChiaveNumerica(
        "W9LOTTCATE", "NUM_CATE", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
	  gestisciAggiornamentiRecordSchedaMultipla(status, impl,
	      gestoreW9LOTTCATE, "W9LOTTCATE", new DataColumn[] {
				    impl.getColumn("W9LOTT.CODGARA"),
				    impl.getColumn("W9LOTT.CODLOTT")}, null);

    // inserimento schede multiple in W9CPV
	  AbstractGestoreChiaveNumerica gestoreW9CPV = new DefaultGestoreEntitaChiaveNumerica(
	      "W9CPV", "NUM_CPV", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
      gestisciAggiornamentiRecordSchedaMultipla(status, impl,
          gestoreW9CPV, "W9CPV", new DataColumn[] {
                impl.getColumn("W9LOTT.CODGARA"),
                impl.getColumn("W9LOTT.CODLOTT")}, null);
      
      W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
          getServletContext(), W9Manager.class);
      
      String codUffInt = (String) this.getRequest().getSession().getAttribute(
          CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      
      try {
        if (w9Manager.isStazioneAppaltanteConPermessi(codUffInt)) {
          
          String rup = (String) this.sqlManager.getObject("select RUP from W9GARA where CODGARA=?",
              new Object[] { impl.getLong("W9LOTT.CODGARA") } );
          
          // Inserimento dei ruolo per il RUP, altrimenti nessun utente accederebbe alla gara con permessi
          this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { impl.getLong("W9LOTT.CODGARA"), impl.getLong("W9LOTT.CODLOTT"), 
              new Long(1), new Long(1), "RA", 14, rup });
          this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { impl.getLong("W9LOTT.CODGARA"), impl.getLong("W9LOTT.CODLOTT"), 
              new Long(1), new Long(2), "RA", 15, rup });
          this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { impl.getLong("W9LOTT.CODGARA"), impl.getLong("W9LOTT.CODLOTT"), 
              new Long(1), new Long(3), "RA", 16, rup });
          this.sqlManager.update("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (?,?,?,?,?,?,?)", 
              new Object[] { impl.getLong("W9LOTT.CODGARA"), impl.getLong("W9LOTT.CODLOTT"), 
              new Long(1), new Long(4), "RA", 17, rup });
        }
      } catch (SQLException e) {
        throw new GestoreException("Errore nell'inserimento dei ruoli di default relativi lotto", null, e);
      }
  }

  public void preUpdate(TransactionStatus status, DataColumnContainer impl)
      throws GestoreException {

	// rendo il codice NUTS in Uppercase per evitare problemi di inserimento nel DB  
	impl.getColumn("W9LOTT.LUOGO_NUTS").setObjectValue(StringUtils.upperCase(impl.getColumn("W9LOTT.LUOGO_NUTS").getValue().stringValue())); 
	  
    if (impl.isColumn("W9INCA.NUM")) {
      // gestione schede multiple incarichi professionali
      AbstractGestoreChiaveNumerica gestoreW9INCA = new DefaultGestoreEntitaChiaveNumerica(
          "W9INCA", "NUM_INCA", new String[] { "CODGARA", "CODLOTT", "NUM" }, this.getRequest());
      gestisciAggiornamentiRecordSchedaMultipla(status, impl, gestoreW9INCA, "W9INCA",
          new DataColumn[] {impl.getColumn("W9LOTT.CODGARA"), impl.getColumn("W9LOTT.CODLOTT"),
          new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(1))) }, null);
    } else {
      
      W9Manager m = (W9Manager) UtilitySpring.getBean("w9Manager",
          getServletContext(), W9Manager.class);
  
      Long codGara = impl.getColumn("W9LOTT.CODGARA").getValue().longValue();
      Long codLotto = impl.getColumn("W9LOTT.CODLOTT").getValue().longValue();
      
      // Inserimento e/o modifica in W9APPAFORN e W9APPALAV
      String tipoContratto = "";
      if (impl.isColumn("W9LOTT.TIPO_CONTRATTO")
          && impl.getColumn("W9LOTT.TIPO_CONTRATTO").getValue().stringValue() != null) {
        tipoContratto = impl.getColumn("W9LOTT.TIPO_CONTRATTO").getValue().stringValue();
      }
      if (!tipoContratto.equals("")) {
        // dopo aver scelto il valore nel menu 'Tipo contratto'
        // vengono distinte due possibilita' 'forniture e/o servizi' e 'lavoro'
        //if (tipoContratto.toUpperCase().equals("L") || tipoContratto.toUpperCase().equals("CL")) {
        int i = 1;
        DataColumnContainer implLavori = null;
        boolean esisteGiaW9APPALAV = this.geneManager.countOccorrenze("W9APPALAV", 
            " codgara =? and codlott =? ", new Object[]{codGara, codLotto}) > 0; 
  
        if (esisteGiaW9APPALAV) {
          m.cancellazioneLott("W9APPALAV", codGara, codLotto);
        }
        while (impl.isColumn("W9APPALAV.ID_APPALTO_" + i)) {
          DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
          implLavori = new DataColumnContainer(campiImpl);
          Long idAppalto = implLavori.getLong("W9APPALAV.ID_APPALTO");
          if (idAppalto != null && idAppalto.longValue() == 1) {
            implLavori.setValue("W9APPALAV.ID_APPALTO",
                implLavori.getLong("W9APPALAV.VALORE_ID_APPALTO"));
            implLavori
                .removeColumns(new String[] { "W9APPALAV.VALORE_ID_APPALTO" });
            implLavori.addColumn("W9APPALAV.CODGARA",
                impl.getColumn("W9LOTT.CODGARA"));
            implLavori.addColumn("W9APPALAV.CODLOTT",
                impl.getColumn("W9LOTT.CODLOTT"));
            implLavori.getColumn("W9APPALAV.CODGARA").setChiave(true);
            implLavori.getColumn("W9APPALAV.CODLOTT").setChiave(true);

            this.inserisci(status, implLavori, new GestoreW9APPALAV());
          }
          i++;
        }
        // dopo aver inserito i riferimenti in W9APPALAV
        // vengono cancellati i riferimenti in W9APPAFORN
        //m.cancellazioneLott("W9APPAFORN",
        //    codGara,
        //    codLotto);
        //} else {
        i = 1;
        DataColumnContainer implFornitori = null;

        boolean esisteGiaW9APPAFORN = this.geneManager.countOccorrenze("W9APPAFORN",
              " codgara =? and codlott =? ", new Object[] { codGara, codLotto }) > 0;
        if (esisteGiaW9APPAFORN) {
          m.cancellazioneLott("W9APPAFORN", codGara, codLotto);
        }
        while (impl.isColumn("W9APPAFORN.ID_APPALTO_" + i)) {
          DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
          implFornitori = new DataColumnContainer(campiImpl);
          Long idAppalto = implFornitori.getLong("W9APPAFORN.ID_APPALTO");
          if (idAppalto != null && idAppalto.longValue() == 1) {
            implFornitori.setValue("W9APPAFORN.ID_APPALTO",
                implFornitori.getLong("W9APPAFORN.VALORE_ID_APPALTO"));
            implFornitori.removeColumns(new String[] { "W9APPAFORN.VALORE_ID_APPALTO" });
            implFornitori.addColumn("W9APPAFORN.CODGARA",
                impl.getColumn("W9LOTT.CODGARA"));
            implFornitori.addColumn("W9APPAFORN.CODLOTT",
                impl.getColumn("W9LOTT.CODLOTT"));
            implFornitori.getColumn("W9APPAFORN.CODGARA").setChiave(true);
            implFornitori.getColumn("W9APPAFORN.CODLOTT").setChiave(true);

            this.inserisci(status, implFornitori, new GestoreW9APPAFORN());
          }
          i++;
        }
        // dopo aver inserito i riferimenti in W9APPAFORN
        // vengono cancellati i riferimenti in W9APPALAV
        //m.cancellazioneLott("W9APPALAV", codGara, codLotto);
      //}
      } else {
        // nel caso in cui non viene selezionato alcun valore nel menu 'Tipo
        // contratto' vengono cancellati i riferimenti in W9APPAFORN e W9APPALAV
        m.cancellazioneLott("W9APPAFORN", codGara, codLotto);
        m.cancellazioneLott("W9APPALAV", codGara, codLotto);
      }
  
      // Inserimento e/o modifica in W9COND
      boolean esisteGiaW9COND = this.geneManager.countOccorrenze("W9COND", 
          " codgara =? and codlott =? ", new Object[]{codGara, codLotto}) > 0;

      long idSceltaContraente = 0;
      if (impl.getColumn("W9LOTT.ID_SCELTA_CONTRAENTE").getValue().longValue() != null) {
        idSceltaContraente = impl.getColumn("W9LOTT.ID_SCELTA_CONTRAENTE").getValue().longValue().longValue();
      }
      if (esisteGiaW9COND) {
        m.cancellazioneLott("W9COND", codGara, codLotto);
      }
      int i = 1;
      DataColumnContainer implCondizioni = null;
      // Se la scelta contraente vale o 4 o 10 viene effettuato l'inserimento in W9COND
      if (idSceltaContraente == 4 || idSceltaContraente == 10) {
        while (impl.isColumn("W9COND.ID_CONDIZIONE_" + i)) {
          DataColumn[] campiImpl = impl.getColumnsBySuffix("_" + i, false);
          implCondizioni = new DataColumnContainer(campiImpl);
          Long idCondizione = implCondizioni.getLong("W9COND.ID_CONDIZIONE");
          if (idCondizione != null && idCondizione.longValue() == 1) {
            implCondizioni.setValue("W9COND.ID_CONDIZIONE",
                implCondizioni.getLong("W9COND.VALORE_ID_CONDIZIONE"));
            implCondizioni.removeColumns(new String[] { "W9COND.VALORE_ID_CONDIZIONE" });
            implCondizioni.addColumn("W9COND.CODGARA", impl.getColumn("W9LOTT.CODGARA"));
            implCondizioni.addColumn("W9COND.CODLOTT", impl.getColumn("W9LOTT.CODLOTT"));
            implCondizioni.getColumn("W9COND.CODGARA").setChiave(true);
            implCondizioni.getColumn("W9COND.CODLOTT").setChiave(true);
    
            this.inserisci(status, implCondizioni, new GestoreW9COND());
          }
          i++;
        }
      }
      // gestione schede multiple altre categorie
      //GestoreW9LOTTCATEMultiplo gestLottCate = new GestoreW9LOTTCATEMultiplo();
      //gestLottCate.setGeneManager(this.geneManager);
      //if (tipoContratto.toUpperCase().equals("L") || tipoContratto.toUpperCase().equals("CL")) {
      //  gestLottCate.gestioneW9LOTTCATEdaW9LOTT(this.getRequest(), status, impl);
      //} else {
      //  gestLottCate.cancellazioneW9LOTTCATEdaW9LOTT(this.getRequest(), status,
      //  impl);
      //}
      AbstractGestoreChiaveNumerica GestoreW9LOTTCATE = new DefaultGestoreEntitaChiaveNumerica(
          "W9LOTTCATE", "NUM_CATE", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
      //if (tipoContratto.toUpperCase().equals("L") || tipoContratto.toUpperCase().equals("CL")) {
  	  this.gestisciAggiornamentiRecordSchedaMultipla(status, impl,
  			GestoreW9LOTTCATE, "W9LOTTCATE", new DataColumn[] {
  				impl.getColumn("W9LOTT.CODGARA"), impl.getColumn("W9LOTT.CODLOTT")}, null);
      //} else {
      //  cancellazioneW9LOTTCATEdaW9LOTT(this.getRequest(), status, impl);
      //}
  
      // inserimento schede multiple in W9CPV
  	  //GestoreW9CPVMultiplo gest = new GestoreW9CPVMultiplo();
  	  //gest.setGeneManager(this.geneManager);
  	  //gest.gestioneW9CPVdaW9LOTT(this.getRequest(), status, impl);
      AbstractGestoreChiaveNumerica GestoreW9CPV = new DefaultGestoreEntitaChiaveNumerica(
          "W9CPV", "NUM_CPV", new String[] { "CODGARA", "CODLOTT" }, this.getRequest());
        
      this.gestisciAggiornamentiRecordSchedaMultipla(status, impl, GestoreW9CPV, "W9CPV",
          new DataColumn[]{impl.getColumn("W9LOTT.CODGARA"), impl.getColumn("W9LOTT.CODLOTT")}, null);
  
      // viene settato ad '1' il valore di MANOD se il CPV primario
      // selezionato fa parte di un determinato range ed il valore di
      // TIPO_CONTRATTO e' uguale a 'L'
      /*String cpvPrimario = impl.getString("W9LOTT.CPV");
      ArrayList<String[]> listaControlli = new ArrayList<String[]>();
      listaControlli.add(new String[] { "50100000-6:50884000-5",
          "!50310000-1:50324200-4", "!50116510-9", "!50190000-3", "!50229000-6",
          "51000000-9:51900000-1" });
      listaControlli.add(new String[] { "60100000-9:60183000-4", "!60160000-7",
          "!60161000-4", "!6022000-6", "64120000-3:64121200-2" });
      listaControlli.add(new String[] { "60410000-5:60424120-3", "!60411000-2",
          "!60421000-5", "60500000-3", "60440000-4:60445000-9" });
      listaControlli.add(new String[] { "60160000-7", "60161000-4", "60411000-2",
          "60421000-5" });
      listaControlli.add(new String[] { "64200000-8:64228200-2", "72318000-7",
          "72700000-7:72720000-3" });
      listaControlli.add(new String[] { "66100000-1:66620000-3" });
      listaControlli.add(new String[] { "50310000-1:50324200-4",
          "72000000-5:72920000-5", "!72318000-7", "!72700000-7:72720000-3",
          "79342410-4" });
      listaControlli.add(new String[] { "73000000-2:73436000-7", "!73200000-4",
          "!73210000-7", "!73220000-0" });
      listaControlli.add(new String[] { "79210000-9:79223000-3" });
      listaControlli.add(new String[] { "79300000-7:79330000-6",
          "79342310-9:79342311-6" });
      listaControlli.add(new String[] { "73200000-4:73220000-0", "79342000-3",
          "79432100-4", "79432300-6", "79432320-2", "79432321-9", "79910000-6",
          "79910000-7", "98362000-8" });
      listaControlli.add(new String[] { "71000000-8:71900000-7", "!71550000-8",
          "79994000-8" });
      listaControlli.add(new String[] { "79341000-6:79342200-5", "!79342000-3",
          "79342100-4" });
      listaControlli.add(new String[] { "70300000-4:70340000-6",
          "90900000-6:90924000-0" });
      listaControlli.add(new String[] { "79800000-2:79824000-6",
          "79970000-6:79980000-7" });
      listaControlli.add(new String[] { "90400000-1:90743200-9", "!90712200-3",
          "90910000-9:90920000-2", "50190000-3", "50229000-6", "50243000-0" });
      listaControlli.add(new String[] { "55000000-0:55524000-9",
          "98340000-8:98341100-6" });
      listaControlli.add(new String[] { "60200000-0:60220000-6" });
      listaControlli.add(new String[] { "60600000-4:60653000-0",
          "63727000-1:63727200-3", "63000000-9:63734000-3", "!63711200-8",
          "!63712700-0", "!63712710-3", "!63727000-1:63727200-3", "98361000-1" });
      listaControlli.add(new String[] { "79100000-5:79140000-7" });
      listaControlli.add(new String[] { "79600000-0:79635000-4", "!79611000-0",
          "!79632000-3", "!79633000-0", "98500000-8:98514000-9",
          "79700000-1:79723000-8" });
      listaControlli.add(new String[] { "80100000-5:80660000-8", "!80533000-9",
          "!80533100-0", "!80533200-1" });
      listaControlli.add(new String[] { "85000000-9:85323000-9", "79611000-0",
          "!85321000-5", "!85322000-2" });
      listaControlli.add(new String[] { "79995000-5:79995200-7",
          "92000000-1:92700000-8", "!92230000-2", "!92231000-9", "!92232000-6" });
      String[] rangeCpv = new String[] {};
      
      if (impl.isColumn("W9LOTT.TIPO_CONTRATTO")) {
        String tipoc = impl.getString("W9LOTT.TIPO_CONTRATTO");
        boolean esiste = false;
        for (int k = 0; k < listaControlli.size(); k++) {
          rangeCpv = (String[]) listaControlli.get(k);
          esiste = ControlloCpv.controlloCpv(cpvPrimario, rangeCpv);
          if (esiste) {
            break;
          }
        }
        if (esiste && (tipoc != null && (tipoc.toUpperCase().equals("L") || tipoc.toUpperCase().equals("CL")))) {
          impl.setValue("W9LOTT.MANOD", "1");
        }
      }*/
    }
  }
  
}