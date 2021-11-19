package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreEntita;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.GeneraIdGaraCigManager;

import java.util.HashMap;

import org.springframework.transaction.TransactionStatus;

/**
 * Gestore non standard per invocare il WS Simog di inserimento nuova gara.
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreRichiestaIdGara extends AbstractGestoreEntita {

  public GestoreRichiestaIdGara() {
    super(false);
  }

  @Override
  public String getEntita() {
    return "W9GARA";
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

    HashMap<String, Object> risultato = generaIdGaraCigManager.generaIdGara(datiForm);

    if (risultato.containsKey("ESITO")) {
      if (risultato.get("ESITO").equals("OK")) {
        String idAvGara = (String) risultato.get("IDAVGARA");
        this.getRequest().setAttribute("ESITO", "OK");
        this.getRequest().setAttribute("IDAVGARAfromSIMOG", idAvGara);

        if (datiForm.isColumn("W9GARA.IDAVGARA")) {
          datiForm.getColumn("W9GARA.IDAVGARA").setObjectOriginalValue(idAvGara);
        } else {
          datiForm.addColumn("W9GARA.IDAVGARA", JdbcParametro.TIPO_TESTO, idAvGara);
        }

        AbstractGestoreChiaveNumerica gestoreW9GARA =
          new DefaultGestoreEntitaChiaveNumerica("W9GARA", "CODGARA", null, this.getRequest());

        // Calcolo della chiave come maxId + 1
        gestoreW9GARA.preInsert(status, datiForm);
        gestoreW9GARA.inserisci(status, datiForm);
        
        this.getRequest().setAttribute("codiceGARA", datiForm.getLong("W9GARA.CODGARA"));
        
      } else {
        // Si e' verificato un errore.
        // Operazione in cui si e' verificato l'errore.
        String operazione = (String) risultato.get("OPERAZIONE");
        // Dettaglio dell'errore
        String errore = (String) risultato.get("ERRORE");

        this.getRequest().setAttribute("ESITO", "KO");
        this.getRequest().setAttribute("OPERAZIONE", operazione);
        this.getRequest().setAttribute("ERRORE", errore);
      }
    } else {
      this.getRequest().setAttribute("ESITO", "KO");
      this.getRequest().setAttribute("OPERAZIONE", "Errore inaspettato");
      this.getRequest().setAttribute("ERRORE", "Il risultato dell'operazione non contiene l'esito.");
    }
  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
  throws GestoreException {
  }

}
