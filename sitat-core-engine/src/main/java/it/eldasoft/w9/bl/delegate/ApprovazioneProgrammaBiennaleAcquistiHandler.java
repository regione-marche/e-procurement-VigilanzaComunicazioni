package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.ImmobileType;
import it.toscana.rete.rfc.sitat.types.InterventoNonRipropostoType;
import it.toscana.rete.rfc.sitat.types.InterventoType;
import it.toscana.rete.rfc.sitat.types.ProgrammaAcquistiType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument;
import it.toscana.rete.rfc.sitat.types.RisorsaCapitoloBilancioType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della richiesta di approvazione di un programma
 * biennale di acquisti.
 * 
 * @author Luca.Giacomazzo - Eldasoft S.p.A. Treviso
 */
public class ApprovazioneProgrammaBiennaleAcquistiHandler extends AbstractRequestHandler {

  //TODO Classe da rimuovere
  
  @Override
  protected String getNomeFlusso() {
    return "Programma biennale acquisti";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_APPROVAZIONE_PROGRAMMA_BIENNALE_ACQUISTI;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument) documento;
    return (FaseEstesaType) doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {

    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument) documento;
    ProgrammaAcquistiType programma = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getProgramma();
    
    // PRIMA SI EFFETTUANO TUTTI I CONTROLLI PRE AGGIORNAMENTO DB

    // verifica della non esistenza di un programma con lo stesso identificativo
    // in banca dati
    if (this.existsProgramma(programma.getT2IDCONTRI())) {
      logger.error("La richiesta di primo invio ha un id ("
          + programma.getT2IDCONTRI() + ") di un programma esistente in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di un programma gia' esistente in banca dati");
      // si termina con questo errore
      return;
    }

    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI
    // AGGIORNA IL DB
    this.insertDatiFlusso(doc, datiAggiornamento);

  }

  @Override
  protected void manageRettifica(XmlObject documento, DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    
    RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument doc = (RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument) documento;
    ProgrammaAcquistiType programma = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getProgramma();

    // PRIMA SI EFFETTUANO TUTTI I CONTROLLI PRE AGGIORNAMENTO DB

    // verifica della non esistenza di un programma con lo stesso identificativo in banca dati
    if (!this.existsProgramma(programma.getT2IDCONTRI())) {
      logger.error("La richiesta di rettifica ha un id ("
          + programma.getT2IDCONTRI()
          + ") di un programma che non esiste in DB");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di un programma non esistente in banca dati");
      // si termina con questo errore
      return;
    }

    // in caso di esistenza, si verifica che la tipologia del programma sia la medesima (lavori)
    if (!this.isProgrammaAcquisti(programma.getT2IDCONTRI())) {
      logger.error("La richiesta di rettifica con id ("
          + programma.getT2IDCONTRI()
          + ") di un programma che in DB e' per acquisti");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di un programma che in banca dati e' per acquisti");
      // si termina con questo errore
      return;
    }

    // SE SI ARRIVA QUI, ALLORA I CONTROLLI SONO ANDATI A BUON FINE PER CUI SI AGGIORNA IL DB
   
    this.deleteDatiFlusso(
        doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getProgramma().getT2IDCONTRI());
    this.insertDatiFlusso(doc, datiAggiornamento);
  }

  /**
   * Verifica se esiste in banca dati lo stesso programma a partire dall'id
   * fornito nel tracciato.
   * 
   * @param idContri
   * @return true se l'elemento esiste, false altrimenti
   * @throws SQLException
   */
  private boolean existsProgramma(final String idContri) throws SQLException {
    Long numeroOccorrenze = (Long) this.sqlManager.getObject(
        "select count(contri) from piatri where id = ?", new Object[] { idContri });
    return numeroOccorrenze.intValue() == 1;
  }
  
  /**
   * Verifica se il programma presente nel DB e' relativo alla stessa tipologia
   * del programma ricevuto nel flusso, ovvero acquisti (forniture/servizi).
   * 
   * @param idContri
   * @return true se il programma nel DB e' di acquisti (forniture e servizi), false altrimenti lavori
   * @throws SQLException SQLException
   */
  private boolean isProgrammaAcquisti(final String idContri) throws SQLException {
    Long tiprog = (Long) this.sqlManager.getObject(
        "select tiprog from piatri where id = ?", new Object[] { idContri });
    return tiprog.intValue() == 2;
  }

  /**
   * Elimina tutte le occorrenze dal DB dei dati da aggiornare (PIATRI, INTTRI,
   * IMMTRAI, OITRI, INRTRI, RIS_CAPITOLO).
   * 
   * @param idcontri
   * @throws SQLException SQLException
   */
  protected void deleteDatiFlusso(final String idContri) throws SQLException {
    Long contri = (Long) this.sqlManager.getObject( "select contri from piatri where id = ?",
        new Object[] { idContri });
    
    this.sqlManager.update("delete from piatri where contri = ?", new Object[] { contri }, 1);
    this.sqlManager.update("delete from inttri where contri = ?", new Object[] { contri });
    this.sqlManager.update("delete from immtrai where contri = ?", new Object[] { contri });
    this.sqlManager.update("delete from inrtri where contri = ?", new Object[] { contri });
    this.sqlManager.update("delete from oitri where contri = ?", new Object[] { contri });
    this.sqlManager.update("delete from ris_capitolo where contri = ?", new Object[] { contri });
  }

  /**
   * Effettua l'inserimento vero e proprio dei dati presenti nel flusso.
   * 
   * @param doc
   *        documento XML della richiesta
   * @param datiAggiornamento
   *        container con i dati del flusso
   * @throws SQLException SQLException
   * @throws GestoreException GestoreException
   */
  protected void insertDatiFlusso(final 
      RichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquistiDocument doc,
      final DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {

    ProgrammaAcquistiType programma = doc.getRichiestaRichiestaRispostaSincronaApprovazioneProgrammaBiennaleAcquisti().getProgramma();

    // estrazione della stazione appaltante di riferimento
    String pkUffint = this.getStazioneAppaltante(programma.getArch1());

    String pkTecni = this.getTecnico(programma.getArch2(), pkUffint);

    // predisposizione dati PIATRI (programmaType): i dati facoltativi vengono testati
    // con il metodo .isSetXXX per verificare se nel tracciato sono valorizzati
    Long idPiatri = new Long(
        this.genChiaviManager.getMaxId("PIATRI", "CONTRI") + 1);
    Long tiprog = new Long(2);
    
    datiAggiornamento.addColumn("PIATRI.CONTRI", JdbcParametro.TIPO_NUMERICO, idPiatri);
    datiAggiornamento.addColumn("PIATRI.ID", JdbcParametro.TIPO_TESTO, programma.getT2IDCONTRI());
    datiAggiornamento.addColumn("PIATRI.RESPRO", JdbcParametro.TIPO_TESTO, pkTecni);
    datiAggiornamento.addColumn("PIATRI.CENINT", JdbcParametro.TIPO_TESTO, pkUffint);
    datiAggiornamento.addColumn("PIATRI.TIPROG", JdbcParametro.TIPO_NUMERICO, new Long(2));
    datiAggiornamento.addColumn("PIATRI.NORMA", JdbcParametro.TIPO_NUMERICO, 
        new Long(programma.getT2NORMA().toString()));
    
    if (programma.isSetT2AL1TRI()) {
      datiAggiornamento.addColumn("PIATRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AL1TRI());
    }
    if (programma.isSetT2AL2TRI()) {
      datiAggiornamento.addColumn("PIATRI.AL2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AL2TRI());
    }
    if (programma.isSetT2AL3TRI()) {
      datiAggiornamento.addColumn("PIATRI.AL3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AL3TRI());
    }
    datiAggiornamento.addColumn("PIATRI.ANNTRI", JdbcParametro.TIPO_NUMERICO, new Long(programma.getT2ANNTRI()));
    if (programma.isSetT2AP1TRI()) {
      datiAggiornamento.addColumn("PIATRI.AP1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AP1TRI());
    }
    if (programma.isSetT2AP2TRI()) {
      datiAggiornamento.addColumn("PIATRI.AP2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AP2TRI());
    }
    if (programma.isSetT2AP3TRI()) {
      datiAggiornamento.addColumn("PIATRI.AP3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2AP3TRI());
    }
    if (programma.isSetT2BI1TRI()) {
      datiAggiornamento.addColumn("PIATRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2BI1TRI());
    }
    if (programma.isSetT2BI2TRI()) {
      datiAggiornamento.addColumn("PIATRI.BI2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2BI2TRI());
    }
    if (programma.isSetT2BI3TRI()) {
      datiAggiornamento.addColumn("PIATRI.BI3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2BI3TRI());
    }
    if (programma.isSetT2BRETRI()) {
      datiAggiornamento.addColumn("PIATRI.BRETRI", JdbcParametro.TIPO_TESTO, programma.getT2BRETRI());
    }
    if (programma.isSetT2DV1TRI()) {
      datiAggiornamento.addColumn("PIATRI.DV1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2DV1TRI());
    }
    if (programma.isSetT2DV2TRI()) {
      datiAggiornamento.addColumn("PIATRI.DV2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2DV2TRI());
    }
    if (programma.isSetT2DV3TRI()) {
      datiAggiornamento.addColumn("PIATRI.DV3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2DV3TRI());
    }
    if (programma.isSetT2IM1TRI()) {
      datiAggiornamento.addColumn("PIATRI.IM1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2IM1TRI());
    }
    if (programma.isSetT2IM2TRI()) {
      datiAggiornamento.addColumn("PIATRI.IM2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2IM2TRI());
    }
    if (programma.isSetT2IM3TRI()) {
      datiAggiornamento.addColumn("PIATRI.IM3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2IM3TRI());
    }
    if (programma.isSetT2MU1TRI()) {
      datiAggiornamento.addColumn("PIATRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2MU1TRI());
    }
    if (programma.isSetT2MU2TRI()) {
      datiAggiornamento.addColumn("PIATRI.MU2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2MU2TRI());
    }
    if (programma.isSetT2MU3TRI()) {
      datiAggiornamento.addColumn("PIATRI.MU3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2MU3TRI());
    }
    if (programma.isSetT2PR1TRI()) {
      datiAggiornamento.addColumn("PIATRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2PR1TRI());
    }
    if (programma.isSetT2PR2TRI()) {
      datiAggiornamento.addColumn("PIATRI.PR2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2PR2TRI());
    }
    if (programma.isSetT2PR3TRI()) {
      datiAggiornamento.addColumn("PIATRI.PR3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2PR3TRI());
    }
    if (programma.isSetT2TO1TRI()) {
      datiAggiornamento.addColumn("PIATRI.TO1TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2TO1TRI());
    }
    if (programma.isSetT2TO2TRI()) {
      datiAggiornamento.addColumn("PIATRI.TO2TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2TO2TRI());
    }
    if (programma.isSetT2TO3TRI()) {
      datiAggiornamento.addColumn("PIATRI.TO3TRI", JdbcParametro.TIPO_DECIMALE, programma.getT2TO3TRI());
    }
    
    if (programma.isSetT2TAPTRI()) {
      datiAggiornamento.addColumn("PIATRI.TAPTRI", JdbcParametro.TIPO_NUMERICO, 
          new Long(programma.getT2TAPTRI().toString()));
    }
    if (programma.isSetT2NAPTRI()) {
      datiAggiornamento.addColumn("PIATRI.NAPTRI", JdbcParametro.TIPO_TESTO, programma.getT2NAPTRI());
    }
    if (programma.isSetT2DTPUBAPP()) {
      datiAggiornamento.addColumn("PIATRI.DPAPPROV", JdbcParametro.TIPO_DATA, programma.getT2DTPUBAPP().getTime());
    }
    if (programma.isSetT2DAPTRI()) {
      datiAggiornamento.addColumn("PIATRI.DAPTRI", JdbcParametro.TIPO_DATA, programma.getT2DAPTRI().getTime());
    }
    if (programma.isSetT2TITOLOAPP()) {
      datiAggiornamento.addColumn("PIATRI.TITAPPROV", JdbcParametro.TIPO_TESTO, programma.getT2TITOLOAPP());
    }
    if (programma.isSetT2URLAPPROV()) {
      datiAggiornamento.addColumn("PIATRI.URLAPPROV", JdbcParametro.TIPO_TESTO, programma.getT2URLAPPROV());
    }
    if (programma.isSetT2TADOZI()) {
      datiAggiornamento.addColumn("PIATRI.TADOZI", JdbcParametro.TIPO_NUMERICO, 
          new Long(programma.getT2TADOZI().toString()));
    }
    if (programma.isSetT2NADOZI()) {
      datiAggiornamento.addColumn("PIATRI.NADOZI", JdbcParametro.TIPO_TESTO, programma.getT2NADOZI());
    }
    if (programma.isSetT2DTPUBADOZ()) {
      datiAggiornamento.addColumn("PIATRI.DPADOZI", JdbcParametro.TIPO_DATA, programma.getT2DTPUBADOZ().getTime());
    }
    if (programma.isSetT2DADOZI()) {
      datiAggiornamento.addColumn("PIATRI.DADOZI", JdbcParametro.TIPO_DATA, programma.getT2DADOZI().getTime());
    }
    if (programma.isSetT2TITOLOADOZ()) {
      datiAggiornamento.addColumn("PIATRI.TITADOZI", JdbcParametro.TIPO_TESTO, programma.getT2TITOLOADOZ());
    }
    if (programma.isSetT2URLADOZI()) {
      datiAggiornamento.addColumn("PIATRI.URLADOZI", JdbcParametro.TIPO_TESTO, programma.getT2URLADOZI());
    }
    
    // aggiornamento del campo BLOB per il PDF del file allegato
    ByteArrayOutputStream fileAllegato = new ByteArrayOutputStream();
    try {
      fileAllegato.write(programma.getFile());
    } catch (IOException e) {
      throw new GestoreException(
          "Errore durante la lettura del file allegato presente nella richiesta XML", "", e);
    }
    datiAggiornamento.addColumn("PIATRI.FILE_ALLEGATO", JdbcParametro.TIPO_BINARIO, fileAllegato);

    // inserimento in piatri
    datiAggiornamento.insert("PIATRI", this.sqlManager);
      
    // ciclo sugli interventi non riproposti (INRTRI)
    if (programma.getInterventiNonRipropostiArray() != null && programma.getInterventiNonRipropostiArray().length > 0) {
      for (int i=0; i < programma.getInterventiNonRipropostiArray().length; i++) {
        InterventoNonRipropostoType interventoNonRiproposto = programma.getInterventiNonRipropostiArray(i);
        if (interventoNonRiproposto != null) {
          
          // popolamento dei dati per l'inserimento di un intervento : si usa un
          // altro container dato che non e' un singolo elemento ma una lista di
          // elementi da inserire nella medesima tabella

          // si costruisce il container inserendo i campi chiave dell'entita' da salvare
          DataColumnContainer dccInterventoNonRiproposto = new DataColumnContainer(
              new DataColumn[] {
                  new DataColumn("INRTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
                  new DataColumn("INRTRI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, interventoNonRiproposto.getT2CONINR())) });

          if (interventoNonRiproposto.isSetT2CUIINR()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.CUIINT", JdbcParametro.TIPO_TESTO, interventoNonRiproposto.getT2CUIINR());
          }
          if (interventoNonRiproposto.isSetT2CUPINR()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.CUPPRG", JdbcParametro.TIPO_TESTO, interventoNonRiproposto.getT2CUPINR());
          }
          if (interventoNonRiproposto.isSetT2DESINR()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.DESINT", JdbcParametro.TIPO_TESTO, interventoNonRiproposto.getT2DESINR());
          }
          if (interventoNonRiproposto.isSetT2DITINR()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.TOTINT", JdbcParametro.TIPO_DECIMALE, interventoNonRiproposto.getT2DITINR());
          }
          if (interventoNonRiproposto.isSetT2INRNMOT()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.MOTIVO", JdbcParametro.TIPO_TESTO, interventoNonRiproposto.getT2INRNMOT());
          }
          if (interventoNonRiproposto.isSetT2PRGINR()) {
            dccInterventoNonRiproposto.addColumn("INRTRI.PRGINT", JdbcParametro.TIPO_NUMERICO, new Long(interventoNonRiproposto.getT2PRGINR().toString()));
          }

          // Inserimento dell'intervento non riproposto (INRTRI)
          dccInterventoNonRiproposto.insert("INRTRI", sqlManager);
        }
      }
    }
    
    // ciclo sugli interventi (INTTRI)
    InterventoType[] arrayInterventi = programma.getInterventiArray();
    if (arrayInterventi != null && arrayInterventi.length > 0) {

      for (int i=0; i < arrayInterventi.length; i++) {
        InterventoType intervento = arrayInterventi[i];
        // popolamento dei dati per l'inserimento di un intervento : si usa un
        // altro container dato che non e' un singolo elemento ma una lista di
        // elementi da inserire nella medesima tabella

        // si costruisce il container inserendo i campi chiave dell'entita' da salvare
        DataColumnContainer dccIntervento = new DataColumnContainer(
            new DataColumn[] {
                new DataColumn("INTTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, idPiatri)),
                new DataColumn("INTTRI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, intervento.getT2CONINT())) });
        
        dccIntervento.addColumn("INTTRI.TIPINT", JdbcParametro.TIPO_NUMERICO, tiprog);
        
        if (intervento.isSetT2ACQVERINT()) {
          dccIntervento.addColumn("INTTRI.ACQVERDI", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2ACQVERINT().toString()));
        }
        if (intervento.isSetT2IAL1TRI()) {
          dccIntervento.addColumn("INTTRI.AL1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IAL1TRI());
        }
        if (intervento.isSetT2IAL2TRI()) {
          dccIntervento.addColumn("INTTRI.AL2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IAL2TRI());
        }
        if (intervento.isSetT2IAL3TRI()) {
          dccIntervento.addColumn("INTTRI.AL3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IAL3TRI());
        }
        if (intervento.isSetT2AL9INT()) {
          dccIntervento.addColumn("INTTRI.AL9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2AL9INT());
        }
        if (intervento.isSetT2ANNINT()) {
          dccIntervento.addColumn("INTTRI.ANNINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2ANNINT()));
        }
        if (intervento.isSetT2ANNRIF()) {
          dccIntervento.addColumn("INTTRI.ANNRIF", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2ANNRIF()));
        }
        if (intervento.isSetT2AP1INT()) {
          dccIntervento.addColumn("INTTRI.AP1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2AP1INT());
        }
        if (intervento.isSetT2AP2INT()) {
          dccIntervento.addColumn("INTTRI.AP2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2AP2INT());
        }
        if (intervento.isSetT2AP3INT()) {
          dccIntervento.addColumn("INTTRI.AP3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2AP3INT());
        }
        if (intervento.isSetT2AP9INT()) {
          dccIntervento.addColumn("INTTRI.AP9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2AP9INT());
        }
        if (intervento.isSetT2APCINT()) {
          dccIntervento.addColumn("INTTRI.APCINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2APCINT()));
        }
        if (intervento.isSetT2AVCPVINT()) {
          dccIntervento.addColumn("INTTRI.AVCPV", JdbcParametro.TIPO_TESTO, intervento.getT2AVCPVINT());
        }
        if (intervento.isSetT2AVIMPNETINT()) {
          dccIntervento.addColumn("INTTRI.AVIMPNET", JdbcParametro.TIPO_DECIMALE, intervento.getT2AVIMPNETINT());
        }
        if (intervento.isSetT2AVIMPTOTINT()) {
          dccIntervento.addColumn("INTTRI.AVIMPTOT", JdbcParametro.TIPO_DECIMALE, intervento.getT2AVIMPTOTINT());
        }
        if (intervento.isSetT2AVIVAINT()) {
          dccIntervento.addColumn("INTTRI.AVIVA", JdbcParametro.TIPO_DECIMALE, intervento.getT2AVIVAINT());
        }
        if (intervento.isSetT2AVOGGETINT()) {
          dccIntervento.addColumn("INTTRI.AVOGGETT", JdbcParametro.TIPO_TESTO, intervento.getT2AVOGGETINT());
        }
        if (intervento.isSetT2IBI1TRI()) {
          dccIntervento.addColumn("INTTRI.BI1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IBI1TRI());
        }
        if (intervento.isSetT2IBI2TRI()) {
          dccIntervento.addColumn("INTTRI.BI2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IBI2TRI());
        }
        if (intervento.isSetT2IBI3TRI()) {
          dccIntervento.addColumn("INTTRI.BI3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IBI3TRI());
        }
        if (intervento.isSetT2BI9INT()) {
          dccIntervento.addColumn("INTTRI.BI9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2BI9INT());
        }
        if (intervento.isSetT2CAUSAINT()) {
          dccIntervento.addColumn("INTTRI.CODAUSA", JdbcParametro.TIPO_TESTO, intervento.getT2CAUSAINT());
        }
        if (intervento.isSetT2CODCPV()) {
          dccIntervento.addColumn("INTTRI.CODCPV", JdbcParametro.TIPO_TESTO, intervento.getT2CODCPV());
        }
        if (intervento.isSetT2CODINT()) {
          dccIntervento.addColumn("INTTRI.CODINT", JdbcParametro.TIPO_TESTO, intervento.getT2CODINT());
        }
        if (intervento.isSetResponsabile()) {
          String codTec = this.getTecnico(intervento.getResponsabile(), pkUffint);
          if (codTec != null && codTec.length() > 0) {
            dccIntervento.addColumn("INTTRI.CODRUP", JdbcParametro.TIPO_TESTO, codTec);
          }
        }
        if (intervento.isSetT2COMINT()) {
          dccIntervento.addColumn("INTTRI.COMINT", JdbcParametro.TIPO_TESTO, intervento.getT2COMINT());
        }
        if (intervento.isSetT2CONINT()) {
          dccIntervento.addColumn("INTTRI.CONINT", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2CONINT()));
        }
        if (intervento.isSetT2CONTE()) {
          dccIntervento.addColumn("INTTRI.CONTESS", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2CONTE()));
        }
        if (intervento.isSetT2COPFININT()) {
          dccIntervento.addColumn("INTTRI.COPFIN", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2COPFININT()));
        }
        if (intervento.isSetT2CUICO()) {
          dccIntervento.addColumn("INTTRI.CUICOLL", JdbcParametro.TIPO_TESTO, intervento.getT2CUICO());
        }
        if (intervento.isSetT2CUIINT()) {
          dccIntervento.addColumn("INTTRI.CUIINT", JdbcParametro.TIPO_TESTO, intervento.getT2CUIINT());
        }
        if (intervento.isSetT2CUPPRG()) {
          dccIntervento.addColumn("INTTRI.CUPPRG", JdbcParametro.TIPO_TESTO, intervento.getT2CUPPRG());
        }
        if (intervento.isSetT2DELEGAINT()) {
          dccIntervento.addColumn("INTTRI.DELEGA", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2DELEGAINT()));
        }
        if (intervento.isSetT2DESINT()) {
          dccIntervento.addColumn("INTTRI.DESINT", JdbcParametro.TIPO_TESTO, intervento.getT2DESINT());
        }
        if (intervento.isSetT2DI1INT()) {
          dccIntervento.addColumn("INTTRI.DI1INT", JdbcParametro.TIPO_DECIMALE, intervento.getT2DI1INT());
        }
        if (intervento.isSetT2DI2INT()) {
          dccIntervento.addColumn("INTTRI.DI2INT", JdbcParametro.TIPO_DECIMALE, intervento.getT2DI2INT());
        }
        if (intervento.isSetT2DI3INT()) {
          dccIntervento.addColumn("INTTRI.DI3INT", JdbcParametro.TIPO_DECIMALE, intervento.getT2DI3INT());
        }
        if (intervento.isSetT2DI9INT()) {
          dccIntervento.addColumn("INTTRI.DI9INT", JdbcParametro.TIPO_DECIMALE, intervento.getT2DI9INT());
        }
        if (intervento.isSetT2DIRGENINT()) {
          dccIntervento.addColumn("INTTRI.DIRGEN", JdbcParametro.TIPO_TESTO, intervento.getT2DIRGENINT());
        }
        if (intervento.isSetT2DITINT()) {
          dccIntervento.addColumn("INTTRI.DITINT", JdbcParametro.TIPO_DECIMALE, intervento.getT2DITINT());
        }
        if (intervento.isSetT2DURCO()) {
          dccIntervento.addColumn("INTTRI.DURCONT", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2DURCO()));
        }
        if (intervento.isSetT2IDV1TRI()) {
          dccIntervento.addColumn("INTTRI.DV1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IDV1TRI());
        }
        if (intervento.isSetT2IDV2TRI()) {
          dccIntervento.addColumn("INTTRI.DV2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IDV2TRI());
        }
        if (intervento.isSetT2IDV3TRI()) {
          dccIntervento.addColumn("INTTRI.DV3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IDV3TRI());
        }
        if (intervento.isSetT2DV9INT()) {
          dccIntervento.addColumn("INTTRI.DV9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2DV9INT());
        }
        if (intervento.isSetT2FIINTR()) {
          dccIntervento.addColumn("INTTRI.FIINTR", JdbcParametro.TIPO_TESTO, intervento.getT2FIINTR().toString());
        }
        if (intervento.isSetT2FLAGCUP()) {
          dccIntervento.addColumn("INTTRI.FLAG_CUP", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2FLAGCUP()));
        }
        if (intervento.isSetT2ICPINT()) {
          dccIntervento.addColumn("INTTRI.ICPINT", JdbcParametro.TIPO_DECIMALE, intervento.getT2ICPINT());
        }
        if (intervento.isSetT2IIM1TRI()) {
          dccIntervento.addColumn("INTTRI.IM1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IIM1TRI());
        }
        if (intervento.isSetT2IIM2TRI()) {
          dccIntervento.addColumn("INTTRI.IM2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IIM2TRI());
        }
        if (intervento.isSetT2IIM3TRI()) {
          dccIntervento.addColumn("INTTRI.IM3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IIM3TRI());
        }
        if (intervento.isSetT2IIM9TRI()) {
          dccIntervento.addColumn("INTTRI.IM9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IIM9TRI());
        }
        if (intervento.isSetT2IMPALT()) {
          dccIntervento.addColumn("INTTRI.IMPALT", JdbcParametro.TIPO_DECIMALE, intervento.getT2IMPALT());
        }
        if (intervento.isSetSUIAL1TRI()) {
          dccIntervento.addColumn("INTTRI.IMPRFS", JdbcParametro.TIPO_DECIMALE, intervento.getSUIAL1TRI());
        }
        if (intervento.isSetT2INTERV()) {
          dccIntervento.addColumn("INTTRI.INTERV", JdbcParametro.TIPO_TESTO, intervento.getT2INTERV().toString());
        }
        if (intervento.isSetT2INTNOTE()) {
          dccIntervento.addColumn("INTTRI.INTNOTE", JdbcParametro.TIPO_TESTO, intervento.getT2INTNOTE());
        }
        if (intervento.isSetT2IV1TRIINT()) {
          dccIntervento.addColumn("INTTRI.IV1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IV1TRIINT());
        }
        if (intervento.isSetT2IV2TRIINT()) {
          dccIntervento.addColumn("INTTRI.IV2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IV2TRIINT());
        }
        if (intervento.isSetT2IV9TRIINT()) {
          dccIntervento.addColumn("INTTRI.IV9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IV9TRIINT());
        }
        if (intervento.isSetT2LAVCO()) {
          dccIntervento.addColumn("INTTRI.LAVCOMPL", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2LAVCO()));
        }
        if (intervento.isSetT2LOTFU()) {
          dccIntervento.addColumn("INTTRI.LOTFUNZ", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2LOTFU()));
        }
        if (intervento.isSetT2MATRICINT()) {
          dccIntervento.addColumn("INTTRI.MATRIC", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2MATRICINT().toString()));
        }
        if (intervento.isSetFSMESE()) {
          dccIntervento.addColumn("INTTRI.MESEIN", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getFSMESE().toString()));
        }
        if (intervento.isSetT2MRCPVINT()) {
          dccIntervento.addColumn("INTTRI.MRCPV", JdbcParametro.TIPO_TESTO, intervento.getT2MRCPVINT());
        }
        if (intervento.isSetT2MRIMPNETINT()) {
          dccIntervento.addColumn("INTTRI.MRIMPNET", JdbcParametro.TIPO_DECIMALE, intervento.getT2MRIMPNETINT());
        }
        if (intervento.isSetT2MRIMPTOTINT()) {
          dccIntervento.addColumn("INTTRI.MRIMPTOT", JdbcParametro.TIPO_DECIMALE, intervento.getT2MRIMPTOTINT());
        }
        if (intervento.isSetT2MRIVAINT()) {
          dccIntervento.addColumn("INTTRI.MRIVA", JdbcParametro.TIPO_DECIMALE, intervento.getT2MRIVAINT());
        }
        if (intervento.isSetT2MROGGETINT()) {
          dccIntervento.addColumn("INTTRI.MROGGETT", JdbcParametro.TIPO_TESTO, intervento.getT2MROGGETINT());
        }
        if (intervento.isSetT2IMU1TRI()) {
          dccIntervento.addColumn("INTTRI.MU1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IMU1TRI());
        }
        if (intervento.isSetT2IMU2TRI()) {
          dccIntervento.addColumn("INTTRI.MU2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IMU2TRI());
        }
        if (intervento.isSetT2IMU3TRI()) {
          dccIntervento.addColumn("INTTRI.MU3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IMU3TRI());
        }
        if (intervento.isSetT2MU9INT()) {
          dccIntervento.addColumn("INTTRI.MU9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2MU9INT());
        }
        if (intervento.isSetFSNORMA()) {
          dccIntervento.addColumn("INTTRI.NORRIF", JdbcParametro.TIPO_TESTO, intervento.getFSNORMA());
        }
        if (intervento.isSetT2NUTS()) {
          dccIntervento.addColumn("INTTRI.NUTS", JdbcParametro.TIPO_TESTO, intervento.getT2NUTS());
        }
        if (intervento.isSetT2IPR1TRI()) {
          dccIntervento.addColumn("INTTRI.PR1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IPR1TRI());
        }
        if (intervento.isSetT2IPR2TRI()) {
          dccIntervento.addColumn("INTTRI.PR2TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IPR2TRI());
        }
        if (intervento.isSetT2IPR3TRI()) {
          dccIntervento.addColumn("INTTRI.PR3TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2IPR3TRI());
        }
        if (intervento.isSetT2PR9INT()) {
          dccIntervento.addColumn("INTTRI.PR9TRI", JdbcParametro.TIPO_DECIMALE, intervento.getT2PR9INT());
        }
        if (intervento.isSetT2PRGINT()) {
          dccIntervento.addColumn("INTTRI.PRGINT", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2PRGINT().toString()));
        }
        if (intervento.isSetT2PRIMA()) {
          dccIntervento.addColumn("INTTRI.PRIMANN", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2PRIMA()));
        }
        if (intervento.isSetT2PROAFFINT()) {
          dccIntervento.addColumn("INTTRI.PROAFF", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2PROAFFINT().toString()));
        }
        if (intervento.isSetT2QUANTITINT()) {
          dccIntervento.addColumn("INTTRI.QUANTIT", JdbcParametro.TIPO_DECIMALE, intervento.getT2QUANTITINT());
        }
        if (intervento.isSetT2REFEREINT()) {
          dccIntervento.addColumn("INTTRI.REFERE", JdbcParametro.TIPO_TESTO, intervento.getT2REFEREINT());
        }
        if (intervento.isSetT2RESPUFINT()) {
          dccIntervento.addColumn("INTTRI.RESPUF", JdbcParametro.TIPO_TESTO, intervento.getT2RESPUFINT());
        }
        if (intervento.isSetFSIRISREG()) {
          dccIntervento.addColumn("INTTRI.RG1TRI", JdbcParametro.TIPO_DECIMALE, intervento.getFSIRISREG());
        }
        if (intervento.isSetT2SCAMU()) {
          dccIntervento.addColumn("INTTRI.SCAMUT", JdbcParametro.TIPO_DATA, intervento.getT2SCAMU().getTime());
        }
        if (intervento.isSetT2SEZINT()) {
          dccIntervento.addColumn("INTTRI.SEZINT", JdbcParametro.TIPO_TESTO, intervento.getT2SEZINT().toString());
        }
        if (intervento.isSetT2SOGGAGGINT()) {
          dccIntervento.addColumn("INTTRI.SOGAGG", JdbcParametro.TIPO_TESTO, intervento.getT2SOGGAGGINT().toString());
        }
        if (intervento.isSetT2SPESESOST()) {
          dccIntervento.addColumn("INTTRI.SPESESOST", JdbcParametro.TIPO_DECIMALE, intervento.getT2SPESESOST());
        }
        if (intervento.isSetT2STAPRO()) {
          dccIntervento.addColumn("INTTRI.STAPRO", JdbcParametro.TIPO_TESTO, intervento.getT2STAPRO().toString());
        }
        if (intervento.isSetT2STRUOPINT()) {
          dccIntervento.addColumn("INTTRI.STRUOP", JdbcParametro.TIPO_TESTO, intervento.getT2STRUOPINT());
        }
        if (intervento.isSetT2TCPINT()) {
          dccIntervento.addColumn("INTTRI.TCPINT", JdbcParametro.TIPO_TESTO, intervento.getT2TCPINT().toString());
        }
        if (intervento.isSetT2TIPINT()) {
          dccIntervento.addColumn("INTTRI.TIPINT", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2TIPINT().toString()));
        }
        if (intervento.isSetFSTIPO()) {
          dccIntervento.addColumn("INTTRI.TIPOIN", JdbcParametro.TIPO_TESTO, intervento.getFSTIPO().toString());
        }
        if (intervento.isSetT2TOTINT()) {
          dccIntervento.addColumn("INTTRI.TOTINT", JdbcParametro.TIPO_DECIMALE, intervento.getT2TOTINT());
        }
        if (intervento.isSetT2UNIMISINT()) {
          dccIntervento.addColumn("INTTRI.UNIMIS", JdbcParametro.TIPO_DECIMALE, new Long(intervento.getT2UNIMISINT().toString()));
        }
        if (intervento.isSetT2URCINT()) {
          dccIntervento.addColumn("INTTRI.URCINT", JdbcParametro.TIPO_TESTO, this.getFlagSNDB(intervento.getT2URCINT()));
        }
        if (intervento.isSetT2VALUTAINT()) {
          dccIntervento.addColumn("INTTRI.VALUTAZIONE", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2VALUTAINT().toString()));
        }
        if (intervento.isSetT2VARIA()) {
          dccIntervento.addColumn("INTTRI.VARIATO", JdbcParametro.TIPO_NUMERICO, new Long(intervento.getT2VARIA().toString()));
        }
        if(intervento.isSetT2ACQALT()) {
          dccIntervento.addColumn("INTTRI.ACQALTINT", JdbcParametro.TIPO_TESTO, intervento.getT2ACQALT());
        }
        
        // Inserimento dell'intervento (INTTRI)
        dccIntervento.insert("INTTRI", this.sqlManager);
        
        if (intervento.getImmobiliArray() != null && intervento.getImmobiliArray().length > 0) {
          for (int j=0; j < intervento.getImmobiliArray().length; j++) {
            ImmobileType immobile = intervento.getImmobiliArray(j);

            if (immobile != null) {
              // si costruisce il container inserendo i campi chiave dell'entita' da salvare
              DataColumnContainer dccImmobile = new DataColumnContainer(
                  new DataColumn[] {
                      new DataColumn("IMMTRAI.CONTRI", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, idPiatri)),
                      new DataColumn("IMMTRAI.CONINT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, intervento.getT2CONINT())),
                      new DataColumn("IMMTRAI.NUMIMM", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, immobile.getT2INUMIMM())),
                      new DataColumn("IMMTRAI.CUIIMM", new JdbcParametro(
                          JdbcParametro.TIPO_TESTO, immobile.getT2CUIIMM())),
                      new DataColumn("IMMTRAI.DESIMM", new JdbcParametro(
                          JdbcParametro.TIPO_TESTO, immobile.getT2IDESIMM())) });
  
              if (immobile.isSetT2COMIST()) {
                dccImmobile.addColumn("IMMTRAI.COMIST", JdbcParametro.TIPO_TESTO, immobile.getT2COMIST());
              }
              if (immobile.isSetT2NUTS01() ) {
                dccImmobile.addColumn("IMMTRAI.NUTS", JdbcParametro.TIPO_TESTO, immobile.getT2NUTS01());
              }
              if (immobile.isSetT2TITCOR() ) {
                dccImmobile.addColumn("IMMTRAI.TITCOR", JdbcParametro.TIPO_NUMERICO, Long.parseLong(immobile.getT2TITCOR().toString()));
              }
              if (immobile.isSetT2IMMDIS() ) {
                dccImmobile.addColumn("IMMTRAI.IMMDISP", JdbcParametro.TIPO_DECIMALE, Long.parseLong(immobile.getT2IMMDIS().toString()));
              }
              if (immobile.isSetT2PROGDI()) {
                dccImmobile.addColumn("IMMTRAI.PROGDISM", JdbcParametro.TIPO_DECIMALE, Long.parseLong(immobile.getT2PROGDI().toString()));
              }
              if (immobile.isSetT2TIPDIS()) {
                dccImmobile.addColumn("IMMTRAI.TIPDISP", JdbcParametro.TIPO_DECIMALE, Long.parseLong(immobile.getT2TIPDIS().toString()));
              }
              if (immobile.isSetT2VA1INT()) {
                dccImmobile.addColumn("IMMTRAI.VA1IMM", JdbcParametro.TIPO_DECIMALE, immobile.getT2VA1INT() );
              }
              if (immobile.isSetT2VA2INT()) {
                dccImmobile.addColumn("IMMTRAI.VA2IMM", JdbcParametro.TIPO_DECIMALE, immobile.getT2VA2INT() );
              }
              if (immobile.isSetT2VA3INT()) {
                dccImmobile.addColumn("IMMTRAI.VA3IMM", JdbcParametro.TIPO_DECIMALE, immobile.getT2VA3INT() );
              }
              if (immobile.isSetT2IVA9INT()) {
                dccImmobile.addColumn("IMMTRAI.VA9IMM", JdbcParametro.TIPO_DECIMALE, immobile.getT2IVA9INT());
              }
              if (immobile.isSetT2IVALIMM()) {
                dccImmobile.addColumn("IMMTRAI.VALIMM", JdbcParametro.TIPO_DECIMALE, immobile.getT2IVALIMM());
              }
              
              // si richiama l'inserimento dell'immobile (IMMTRAI)
              dccImmobile.insert("IMMTRAI", this.sqlManager);
            }
          }
        }

        if (intervento.getRisorseCapitoloBilancioArray() != null && intervento.getRisorseCapitoloBilancioArray().length > 0) {
          for (int j=0; j < intervento.getRisorseCapitoloBilancioArray().length; j++) {
            RisorsaCapitoloBilancioType risorsaCapitoloBilancio = intervento.getRisorseCapitoloBilancioArray(j);
            
            if (risorsaCapitoloBilancio != null) {
              // si costruisce il container inserendo i campi chiave dell'entita' da salvare
              DataColumnContainer dccRisorsaCapitolo = new DataColumnContainer(
                  new DataColumn[] {
                      new DataColumn("RIS_CAPITOLO.CONTRI", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, idPiatri)),
                      new DataColumn("RIS_CAPITOLO.CONINT", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, intervento.getT2CONINT())),
                      new DataColumn("RIS_CAPITOLO.NUMCB", new JdbcParametro(
                          JdbcParametro.TIPO_NUMERICO, risorsaCapitoloBilancio.getT2NUMCB())) });
              
              if (risorsaCapitoloBilancio.isSetRG1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.RG1CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getRG1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2AL9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AL9CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2AL9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2AP1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AP1CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2AP1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2AP2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AP2CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2AP2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2AP3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AP3CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2AP3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2AP9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AP9CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2AP9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2BI9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.BI9CB", JdbcParametro.TIPO_DECIMALE,
                    risorsaCapitoloBilancio.getT2BI9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2NOTECB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.CBNOTE", JdbcParametro.TIPO_TESTO,
                  risorsaCapitoloBilancio.getT2NOTECB());
              }
              if (risorsaCapitoloBilancio.isSetT2DV9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.DV9CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2DV9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IAL1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AL1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IAL1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IAL2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AL2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IAL2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IAL3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.AL3CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IAL3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IBI1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.BI1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IBI1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IBI2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.BI2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IBI2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IBI3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.BI3CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IBI3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IDV1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.DV1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IDV1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IDV2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.DV2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IDV2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IDV3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.DV3CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IDV3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMPALTCB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IMPALTCB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IMPALTCB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMPRFSCB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IMPRFSCB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IMPRFSCB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMPSPECB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IMPSPE", JdbcParametro.TIPO_TESTO,
                  risorsaCapitoloBilancio.getT2IMPSPECB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMU1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.MU1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IMU1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMU2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.MU2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IMU2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IMU3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.MU3CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IMU3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2ITO1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.TO1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2ITO1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2ITO2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.TO2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2ITO2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2ITO3CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.TO3CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2ITO3CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IV1CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IV1CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IV1CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IV2CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IV2CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IV2CB());
              }
              if (risorsaCapitoloBilancio.isSetT2IV9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.IV9CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2IV9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2MU9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.MU9CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2MU9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2NCAPBILCB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.NCAPBIL", JdbcParametro.TIPO_TESTO,
                  risorsaCapitoloBilancio.getT2NCAPBILCB());
              }
              if (risorsaCapitoloBilancio.isSetT2SPESECB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.SPESESOST", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2SPESECB());
              }
              if (risorsaCapitoloBilancio.isSetT2TO9CB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.TO9CB", JdbcParametro.TIPO_DECIMALE,
                  risorsaCapitoloBilancio.getT2TO9CB());
              }
              if (risorsaCapitoloBilancio.isSetT2VERBILCB()) {
                dccRisorsaCapitolo.addColumn("RIS_CAPITOLO.VERIFBIL", JdbcParametro.TIPO_TESTO, 
                    this.getFlagSNDB(risorsaCapitoloBilancio.getT2VERBILCB()));
              }
              
              // si richiama l'inserimento della risorsa per capitolo di bilancio 
              dccRisorsaCapitolo.insert("RIS_CAPITOLO", sqlManager);
            }
          }
        }
        
        /*
        if (tab2.isSetListaTab3()) {
          // si cicla sugli immobili dell'intervento , ovvero la tabella tab3
          // (IMMTRAI)
          ListaTab3Type listaTab3 = tab2.getListaTab3();
          Tab3Type tab3 = null;
          for (int j = 0; j < listaTab3.sizeOfTab3Array(); j++) {
            tab3 = listaTab3.getTab3Array(j);

            // si costruisce il container inserendo i campi chiave dell'entita' da salvare
            DataColumnContainer dccImmobile = new DataColumnContainer(
                new DataColumn[] {
                    new DataColumn("IMMTRAI.CONTRI", new JdbcParametro(
                        JdbcParametro.TIPO_NUMERICO, idPiatri)),
                    new DataColumn("IMMTRAI.CONINT", new JdbcParametro(
                        JdbcParametro.TIPO_NUMERICO, tab2.getT2CONINT())),
                    new DataColumn("IMMTRAI.NUMIMM", new JdbcParametro(
                        JdbcParametro.TIPO_NUMERICO, tab3.getT2INUMIMM())) });

            // si aggiungono gli altri dati al container: per i campi non
            // obbligatori da tracciato si effettua il test con il corrispondente
            // metodo isSetXXX
            if (tab3.isSetT2IANNIMM()) {
              dccImmobile.addColumn("IMMTRAI.ANNIMM", JdbcParametro.TIPO_NUMERICO, new Long(tab3.getT2IANNIMM()));
            }
            if (tab3.isSetT2IDESIMM()) {
              dccImmobile.addColumn("IMMTRAI.DESIMM", JdbcParametro.TIPO_TESTO, tab3.getT2IDESIMM());
            }
            if (tab3.isSetT2IPROIMM()) {
              dccImmobile.addColumn("IMMTRAI.PROIMM", JdbcParametro.TIPO_NUMERICO, Long.parseLong(tab3.getT2IPROIMM().toString()));
            }
            if (tab3.isSetT2IVALIMM()) {
              dccImmobile.addColumn("IMMTRAI.VALIMM", JdbcParametro.TIPO_DECIMALE, tab3.getT2IVALIMM());
            }
            // si richiama l'inserimento dell'occorrenza di tab3 (IMMTRAI)
            dccImmobile.insert("IMMTRAI", this.sqlManager);
          }
        }*/
    }
    }
    
    // se la procedura di aggiornamento e' andata a buon fine, si aggiorna lo  stato dell'importazione
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
        
  }
  
}
