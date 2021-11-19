package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document;
import it.toscana.rete.rfc.sitat.types.Tab312Type;
import it.toscana.rete.rfc.sitat.types.Tab31Type;
import it.toscana.rete.rfc.sitat.types.Tab41Type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della Gara per ente nazionale o importo inferiore ai 40.000 euro.
 * 
 * @author Luca.Giacomazzo
 */
public class GaraEnteNazionaleSotto40000Handler extends AbstractRequestHandler {

  //TODO Classe da rimuovere
  
  @Override
  protected String getNomeFlusso() {
    return "Gara ente nazionale sotto 40.000 euro";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_GARE_ENTI_NAZIONALI_O_SOTTO_40000;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc =
      (RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document) documento;
    return doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc =
      (RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document) documento;
    return (FaseEstesaType) doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
  throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc =
      (RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document) documento;

    Tab31Type tab31 = doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab31();

    if (this.existsGara(tab31)) {
      logger.error("La richiesta di primo invio ha un id (" + tab31.getW9GNIDGARA()
          + ") di una gara per enti nazionali o sotto i 40.000 euro esistente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Primo invio "
          + "di una gara per enti nazionali o sotto i 40.000 euro gia' esistente in banca dati");
      return;
    }

    //if (!ignoreWarnings) {
    //}

    this.insertDatiFlusso(doc, datiAggiornamento, true, null);
  }

  @Override
  protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento,
      boolean ignoreWarnings) throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc =
      (RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document) documento;

    Tab31Type tab31 = doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab31();

    if (!this.existsGara(tab31)) {
      logger.error("La richiesta di rettifica ha un id (" + tab31.getW9GNIDGARA()
          + ") di una gara per enti nazionali o sotto i 40.000 euro esistente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(), "Rettifica "
          + "di una gara per enti nazionali o sotto i 40.000 euro non presente in banca dati");
      return;
    }

    //if (!ignoreWarnings) {
    //}

    this.manageIntegrazioneORettifica(doc, datiAggiornamento);
  }

  /**
   * Esistenza del record in W9GARA_ENTINAZ a partire da IDAVGARA.
   * 
   * @param tab31 Tab31Type
   * @return Ritorna true, se esiste in W9GARA_ENTINAZ un record con idAvGara pari a TAB31.W9GNIDGARA
   * @throws SQLException SQLException
   */
  private boolean existsGara(final Tab31Type tab31) throws SQLException {
    Long numeroOccorrenze = new Long(0);
    String idGara = "";
    if (StringUtils.isNotEmpty(tab31.getW9GNIDGARA())) {
      idGara = tab31.getW9GNIDGARA();
      numeroOccorrenze = (Long) this.sqlManager.getObject(
          "select count(CODGARA) from W9GARA_ENTINAZ where IDAVGARA = ?",
          new Object[] { idGara });
    }

    return numeroOccorrenze.intValue() == 1;
  }

  /**
   * Inserimento dei dati della gara, dei documenti del badno e dei lotti ed esiti.
   * 
   * @param doc RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document
   * @param datiAggiornamento DataColumnContainer
   * @param primoInvio Primo invio?
   * @param codagaradeleted Codice della gara cancellata (in casa di rettifica/integrazione)
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  private void insertDatiFlusso(final RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc,
      final DataColumnContainer datiAggiornamento, final boolean primoInvio,
      final Long codagaradeleted) throws SQLException, GestoreException {

    Tab31Type tab31 = doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab31();

    String pkUffint = this.getStazioneAppaltante(tab31.getArch1());

    Long codgara = null;
    if (primoInvio) {
      codgara = new Long(
          this.genChiaviManager.getMaxId("W9GARA_ENTINAZ", "CODGARA") + 1);
      if (codgara == null || (codgara != null && codgara.longValue() <= 3000000)) {
        codgara = new Long(3000001);
      }
    } else {
      codgara = codagaradeleted;
    }

    // Dati di W9GARA_ENTINAZ
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.CODGARA", JdbcParametro.TIPO_NUMERICO, codgara);
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.CODEIN", JdbcParametro.TIPO_TESTO, pkUffint);
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.OGGETTO", JdbcParametro.TIPO_TESTO, tab31.getW9GNOGGETTO());
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.IDAVGARA", JdbcParametro.TIPO_TESTO, tab31.getW9GNIDGARA());
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.NLOTTI", JdbcParametro.TIPO_NUMERICO, new Long(tab31.getW9GNNLOTTI()));
    datiAggiornamento.addColumn("W9GARA_ENTINAZ.TIPO_APP", JdbcParametro.TIPO_NUMERICO,
        Long.parseLong(tab31.getW9GNTIPOAPP().toString()));

    if (tab31.isSetW9GNIGARA()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.IMPORTO_GARA", JdbcParametro.TIPO_DECIMALE, tab31.getW9GNIGARA());
    }
    if (tab31.isSetW9GNDGURI()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.DGURI", JdbcParametro.TIPO_DATA, tab31.getW9GNDGURI().getTime());
    }
    if (tab31.isSetW9GNDSCADB()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.DSCADE ", JdbcParametro.TIPO_DATA, tab31.getW9GNDSCADB().getTime());
    }
    if (tab31.isSetW9GNIDSCEL()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.ID_SCELTA_CONTRAENTE", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab31.getW9GNIDSCEL().toString()));
    }
    if (tab31.isSetW9GNMODGAR()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.ID_MODO_GARA", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab31.getW9GNMODGAR().toString()));
    }
    if (tab31.isSetW9GNTIPOCON()) {
      datiAggiornamento.addColumn("W9GARA_ENTINAZ.TIPO_CONTRATTO", JdbcParametro.TIPO_TESTO, tab31.getW9GNTIPOCON().toString());
    }
    datiAggiornamento.insert("W9GARA_ENTINAZ", this.sqlManager);

    // Dati di W9DOCGARA (i documenti del bando)
    if (doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().sizeOfListaTab311Array() > 0) {
      Tab41Type[] arrayTab41 = doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getListaTab311Array();

      for (int iu = 0; iu < arrayTab41.length; iu++) {
        Tab41Type tab41 = arrayTab41[iu];

        datiAggiornamento.addColumn("W9DOCGARA.CODGARA", JdbcParametro.TIPO_NUMERICO, codgara);
        datiAggiornamento.addColumn("W9DOCGARA.NUMDOC", JdbcParametro.TIPO_NUMERICO, new Long(iu + 1));
        datiAggiornamento.addColumn("W9DOCGARA.TITOLO", JdbcParametro.TIPO_TESTO, tab41.getW9DGTITOLO());
        ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
        try {
          fileAllegato.write(tab41.getFile());
        } catch (IOException e) {
          throw new GestoreException(
              "Errore durante la lettura del file allegato presente nella richiesta XML", null, e);
        }
        datiAggiornamento.addColumn("W9DOCGARA.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
        datiAggiornamento.insert("W9DOCGARA", this.sqlManager);
      }
    }

    // Dati di W9LOTT_ENTINAZ
    if (doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().sizeOfTab312Array() > 0) {
      Tab312Type[] arrayTab312 = doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab312Array();

      for (int iu = 0; iu < arrayTab312.length; iu++) {
        Tab312Type tab312 = arrayTab312[iu];

        datiAggiornamento.addColumn("W9LOTT_ENTINAZ.CODGARA", JdbcParametro.TIPO_NUMERICO, codgara);
        datiAggiornamento.addColumn("W9LOTT_ENTINAZ.CODLOTT", JdbcParametro.TIPO_NUMERICO, new Long(iu + 1));
        datiAggiornamento.addColumn("W9LOTT_ENTINAZ.OGGETTO", JdbcParametro.TIPO_TESTO, tab312.getW9LNOGGETTO());
        datiAggiornamento.addColumn("W9LOTT_ENTINAZ.IMPORTO_TOT", JdbcParametro.TIPO_DECIMALE, tab312.getW9LNIMPTOT());
        datiAggiornamento.addColumn("W9LOTT_ENTINAZ.CIG", JdbcParametro.TIPO_TESTO, tab312.getW9LNCIG());
        if (tab312.isSetW9LNCPV()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.CPV", JdbcParametro.TIPO_TESTO, tab312.getW9LNCPV());
        }
        if (tab312.isSetW9LNIDCATE()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.ID_CATEGORIA_PREVALENTE", JdbcParametro.TIPO_TESTO, tab312.getW9LNIDCATE().toString());
        }
        if (tab312.isSetW9LNCLASCAT()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.CLASCAT", JdbcParametro.TIPO_TESTO, tab312.getW9LNCLASCAT().toString());
        }
        if (tab312.isSetW9LNIMPAGG()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.IMP_AGG", JdbcParametro.TIPO_TESTO, tab312.getW9LNIMPAGG());
        }
        if (tab312.isSetW9LNIMPAGGI()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.IMPORTO_AGGIUDICAZIONE", JdbcParametro.TIPO_DECIMALE, new Double(tab312.getW9LNIMPAGGI()));
        }
        if (tab312.isSetW9LNDVERB()) {
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.DATA_VERB_AGGIUDICAZIONE", JdbcParametro.TIPO_DATA, tab312.getW9LNDVERB().getTime());
        }
        if (tab312.isSetFile()) {
          ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
          try {
            fileAllegato.write(tab312.getFile());
          } catch (IOException e) {
            throw new GestoreException(
                "Errore durante la lettura del file allegato al lotto-esito presente nella richiesta XML", null, e);
          }
          datiAggiornamento.addColumn("W9LOTT_ENTINAZ.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);
        }

        datiAggiornamento.insert("W9LOTT_ENTINAZ", sqlManager);

        // Rimozione dei campi di W9LOTT_ENTINAZ dal DataColumnContainer per il successivo lotto
        datiAggiornamento.removeColumns(new String[]{
            "W9LOTT_ENTINAZ.CODGARA", "W9LOTT_ENTINAZ.CODLOTT", "W9LOTT_ENTINAZ.OGGETTO",
            "W9LOTT_ENTINAZ.IMPORTO_TOT", "W9LOTT_ENTINAZ.CIG", "W9LOTT_ENTINAZ.CPV",
            "W9LOTT_ENTINAZ.ID_CATEGORIA_PREVALENTE", "W9LOTT_ENTINAZ.CLASCAT",
            "W9LOTT_ENTINAZ.IMP_AGG", "W9LOTT_ENTINAZ.IMPORTO_AGGIUDICAZIONE",
            "W9LOTT_ENTINAZ.DATA_VERB_AGGIUDICAZIONE", "W9LOTT_ENTINAZ.FILE_ALLEGATO"});
      }
    }

    // Aggiornamento dell'esito dell'importazione dei dati
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  /**
   * Gestione dei flussi di rettifica/integrazione della gara ente nazionale o inferiore a 40.000 euro.
   * 
   * @param doc RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document
   * @param datiAggiornamento DataColumnContainer
   * @throws GestoreException GestoreException
   * @throws SQLException SQLException
   */
  private void manageIntegrazioneORettifica(final RichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000Document doc,
      final DataColumnContainer datiAggiornamento) throws GestoreException, SQLException {

    Long codgara = (Long) this.sqlManager.getObject("select codgara from w9gara_entinaz where idavgara = ?",
        new Object[]{doc.getRichiestaRichiestaRispostaSincronaGaraEnteNazionaleSotto40000().getTab31().getW9GNIDGARA()});
    this.deleteDatiFlusso(codgara);
    this.insertDatiFlusso(doc, datiAggiornamento, false, codgara);
  }

  /**
   * Cancellazione della gara e di tutte le occorrenze figlie.
   * 
   * @param codgara Codice della gara
   * @throws SQLException SQLException
   */
  private void deleteDatiFlusso(final Long codgara) throws SQLException {

    this.sqlManager.update("delete from w9gara_entinaz where codgara = ?",
        new Object[] { codgara }, 1);
    this.sqlManager.update("delete from w9lott_entinaz where codgara = ?",
        new Object[] { codgara });
    this.sqlManager.update("delete from w9docgara where codgara = ?",
        new Object[] { codgara });
  }

}