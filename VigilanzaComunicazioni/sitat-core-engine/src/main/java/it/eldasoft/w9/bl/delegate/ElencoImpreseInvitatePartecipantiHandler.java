package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument;
import it.toscana.rete.rfc.sitat.types.Tab32Type;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione dell'elenco delle imprese invitate/partecipanti.
 * 
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 */
public class ElencoImpreseInvitatePartecipantiHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(ElencoImpreseInvitatePartecipantiHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Elenco imprese invitate/partecipanti";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_ELENCO_IMPRESE_INVITATE_PARTECIPANTI;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    
    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();

    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio elenco imprese invitate/partecipanti ha un cig ("
          + fase.getW3FACIG() + ") di un lotto non presente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Primo invio di un elenco imprese invitate/partecipanti di un lotto non presente in banca dati");
      // si termina con questo errore
      return;
    }
    
    if (this.existsFase(fase, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI)) {
      logger.error("La richiesta di primo invio di elenco imprese invitate/partecipanti gia' presente nel DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di elenco imprese invitate/partecipanti gia' presente in banca dati");
      // si termina con questo errore
      return;
    }
    
    if (! ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI)) {
    		logger.error("La richiesta di primo invio di elenco imprese invitate/partecipanti non e' prevista per il CIG=" + fase.getW3FACIG());
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
          "Primo invio di un elenco imprese invitate/partecipanti non previsto");
        // si termina con questo errore
        return;
    	}
    }
    
    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE
    // PER CUI SI AGGIORNA IL DB
    this.insertDatiFlusso(doc, datiAggiornamento, true);
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc = (RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();

    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica elenco imprese invitate/partecipanti di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
        "Rettifica di un elenco imprese invitate/partecipanti di un lotto non presente in banca dati");
      // si termina con questo errore
      return;
    }
    
    if (! this.existsFase(fase, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI)) {
      logger.error("La richiesta di rettifica di elenco imprese invitate/partecipanti non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di elenco imprese invitate/partecipanti non presente in banca dati");
      // si termina con questo errore
      return;
    }
    
    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI
    // A BUON FINE PER CUI SI AGGIORNA IL DB
    this.manageIntegrazioneORettifica(doc, datiAggiornamento);
  }

  /**
   * Inserimento dati del flusso in DB.
   * 
   * @param doc RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument
   * @param datiAggiornamento DataColumnContainer
   * @param primoInvio Primo invio
   * @throws SQLException
   * @throws GestoreException
   */
  private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc,
      final DataColumnContainer datiAggiornamento, final boolean primoInvio)
  throws SQLException, GestoreException {
    Tab32Type[] arrayTab32 = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getTab32Array();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();
    
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());

    this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

    for (int i = 0; i < arrayTab32.length; i++) {
      Tab32Type tab32 = arrayTab32[i];
      
      // predisposizione dati W9IMPRESE (tab32): i dati facoltativi vengono testati
      // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
      DataColumnContainer dccW9Imprese = new DataColumnContainer(new DataColumn[] {
          new DataColumn("W9IMPRESE.CODGARA", new JdbcParametro(
              JdbcParametro.TIPO_NUMERICO, codGara)),
              new DataColumn("W9IMPRESE.CODLOTT", new JdbcParametro(
                  JdbcParametro.TIPO_NUMERICO, codLott)),
                  new DataColumn("W9IMPRESE.NUM_IMPR", new JdbcParametro(
                      JdbcParametro.TIPO_NUMERICO, new Long(i+1))) });

      String pkImpresa = this.gestioneImpresa(tab32.getArch3(), pkUffint);
      dccW9Imprese.addColumn("W9IMPRESE.CODIMP", JdbcParametro.TIPO_TESTO, pkImpresa);

      dccW9Imprese.addColumn("W9IMPRESE.PARTECIP", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab32.getW9IMPARTEC().toString()));
      dccW9Imprese.addColumn("W9IMPRESE.TIPOAGG", JdbcParametro.TIPO_NUMERICO,
          Long.parseLong(tab32.getW9IMTIPOA().toString()));
      
      if (tab32.isSetW9IMRAGGR()) {
        dccW9Imprese.addColumn("W9IMPRESE.NUM_RAGG", JdbcParametro.TIPO_NUMERICO,
            new Long(tab32.getW9IMRAGGR()));
      }
      if (tab32.isSetW9IMRUOLO()) {
        dccW9Imprese.addColumn("W9IMPRESE.RUOLO", JdbcParametro.TIPO_NUMERICO,
            Long.parseLong(tab32.getW9IMRUOLO().toString()));
      }
      dccW9Imprese.insert("W9IMPRESE", sqlManager);
    }

    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo
    // stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }
  
  private void manageIntegrazioneORettifica(RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument doc, 
      DataColumnContainer datiAggiornamento) throws SQLException, GestoreException {

    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
  }
  
  /**
   * Cancellazione dei dati del flusso.
   * 
   * @param fase FaseEstesaType
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void deleteDatiFlusso(final FaseEstesaType fase) throws SQLException, GestoreException {
    HashMap<String, Long> hm = UtilitySITAT.getCodGaraCodLottByCIG(fase.getW3FACIG(), this.sqlManager);
    Long codGara = new Long(hm.get("CODGARA"));
    Long codLott = new Long(hm.get("CODLOTT"));

    this.sqlManager.update("delete from W9IMPRESE where codgara = ? and codlott = ?",
        new Object[] { codGara, codLott });
  }
  
  /**
   * 
   * @param operazioneElencoImprese
   * @param elencoImpreseInvitate
   * @param cfrup
   * @param cfsa
   * @throws SQLException
   * @throws GestoreException
   */
  public void gestioneElencoImpreseInvitateDaOR(String operazioneElencoImprese,
      RichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipantiDocument elencoImpreseInvitate,
      String cfrup, String cfsa) throws SQLException, GestoreException {
    
    FaseEstesaType faseType = elencoImpreseInvitate.getRichiestaRichiestaRispostaSincronaElencoImpreseInvitatePartecipanti().getFase();
    
    HashMap<String, Long> hm = UtilitySITAT.getCodGaraCodLottByCIG(faseType.getW3FACIG(), this.sqlManager);
    Long codiceGara = new Long(hm.get("CODGARA"));
    Long codiceLotto = new Long(hm.get("CODLOTT"));
    
    DataColumnContainer datiAggiornamento = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9LOTT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceGara)),
        new DataColumn("W9LOTT.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codiceLotto))
    });

    if ("importa".equals(operazioneElencoImprese)) {
      this.insertDatiFlusso(elencoImpreseInvitate, datiAggiornamento, true);
      
    } else if ("sovrascrivi".equals(operazioneElencoImprese)) { 
      this.deleteDatiFlusso(faseType);
      this.insertDatiFlusso(elencoImpreseInvitate, datiAggiornamento, false);
    }
  }

}
