package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.GeneraIdGaraCigManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

public class GestoreRichiestaCIG extends AbstractGestoreEntita {

  private static Logger logger = Logger.getLogger(GestoreRichiestaCIG.class);
  
  public GestoreRichiestaCIG() {
    super(false);
  }

  public GestoreRichiestaCIG(boolean isGestoreStandard) {
    super(isGestoreStandard);
  }

  @Override
  public String getEntita() {
    return "W9LOTT";
  }

  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {
  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException {

    GeneraIdGaraCigManager generaIdGaraCigManager = (GeneraIdGaraCigManager) UtilitySpring.getBean(
        "generaIdGaraCigManager", this.getServletContext(), GeneraIdGaraCigManager.class);

    HashMap<String, Object> risultato = generaIdGaraCigManager.generaCig(datiForm);
    String idAvGara = datiForm.getString("IDAVGARA");

    if (risultato.containsKey("ESITO")) {
      if (risultato.get("ESITO").equals("OK")) {
        String cig = (String) risultato.get("CIG");

        // Valorizzazione del campo W9LOTT.CODGARA, risalendo al campo
        // W9GARA.CODGARA a partire dal valore di IDAVGARA.
        Long codgara = null;
        String flagEnteSpeciale = null;
        try {
          Vector< ? > datiGara = sqlManager.getVector(
              "select CODGARA, FLAG_ENTE_SPECIALE from W9GARA where IDAVGARA=?",
              new Object[]{ idAvGara });
          
          codgara = ((JdbcParametro) datiGara.get(0)).longValue();
          
          if (StringUtils.isNotEmpty(((JdbcParametro) datiGara.get(1)).getStringValue())) {
            flagEnteSpeciale = ((JdbcParametro) datiGara.get(1)).getStringValue();
            datiForm.addColumn("W9LOTT.FLAG_ENTE_SPECIALE", JdbcParametro.TIPO_TESTO, flagEnteSpeciale);
          }

        } catch (SQLException e) {
          throw new GestoreException("Errore nel recupero del CODGARA a partire dal IDAVGARA di W9GARA", null, e);
        }

        datiForm.getColumn("W9LOTT.CODLOTT").setChiave(true);
        datiForm.getColumn("W9LOTT.CODGARA").setChiave(true);
        datiForm.getColumn("W9LOTT.CODGARA").setObjectValue(codgara);
        datiForm.addColumn("W9LOTT.CIG", JdbcParametro.TIPO_TESTO, cig);

        // Se il lotto e' per Lavori, allora si inizializza il campo W9LOTT.MANOD (Posa in opera?) a Si.
        if (datiForm.isColumn("W9LOTT.TIPO_CONTRATTO")) {
          String tipoContratto = datiForm.getString("W9LOTT.TIPO_CONTRATTO");
          if ("L".equalsIgnoreCase(tipoContratto)) {
            datiForm.addColumn("W9LOTT.MANOD", JdbcParametro.TIPO_TESTO, "1");
          }
        }

        // Il campo W9LOTT.ART_E2 viene sempre inizializzato a No.
        datiForm.addColumn("W9LOTT.ART_E2", JdbcParametro.TIPO_TESTO, "2");
        
        AbstractGestoreChiaveNumerica gestoreW9LOTT = new DefaultGestoreEntitaChiaveNumerica("W9LOTT",
            "CODLOTT", new String[]{ "CODGARA" }, this.getRequest());
        // Calcolo della chiave come maxId + 1
        gestoreW9LOTT.preInsert(status, datiForm);

        try {
          
          datiForm.insert("W9LOTT", sqlManager);
          
          this.getRequest().setAttribute("ESITO", "OK");
          this.getRequest().setAttribute("CIGfromSIMOG", cig);
          
        } catch (SQLException e) {
          
          logger.error("Errore inaspettato nell'inserimento del nuovo lotto", e);
          
          this.getRequest().setAttribute("ESITO", "KO");
          this.getRequest().setAttribute("OPERAZIONE", "Errore inaspettato nell'inserimento del lotto");
          this.getRequest().setAttribute("ERRORE", "Il risultato dell'operazione non contiene l'esito.");

          // Stop del processo di inserimento del lotto, invece di generare un'eccezione
          this.setStopProcess(true);
        }

      } else {
        // Si e' verificato un errore.
        // Operazione in cui si e' verificato l'errore.
        String operazione = (String) risultato.get("OPERAZIONE");
        // Dettaglio dell'errore
        String errore = (String) risultato.get("ERRORE");

        this.getRequest().setAttribute("ESITO", "KO");
        this.getRequest().setAttribute("OPERAZIONE", operazione);
        this.getRequest().setAttribute("ERRORE", errore);

        // Stop del processo di inserimento del lotto, invece di generare un'eccezione
        this.setStopProcess(true);
      }
    } else {

      this.getRequest().setAttribute("ESITO", "KO");
      this.getRequest().setAttribute("OPERAZIONE", "Errore inaspettato");
      this.getRequest().setAttribute("ERRORE", "Il risultato dell'operazione non contiene l'esito.");

      // Stop del processo di inserimento del lotto, invece di generare un'eccezione
      this.setStopProcess(true);
    }
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
    throws GestoreException {
  }

}