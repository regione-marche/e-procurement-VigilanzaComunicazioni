package it.eldasoft.w9.tags.gestori.submit;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.web.struts.UploadMultiploForm;

/**
 * Gestore per salvataggio occorrenze in W9DOCGARA
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreW9DOCGARA extends AbstractGestoreChiaveNumerica {

  // Dimensione massima del file (10Mb)
  final double MAX_FILE_SIZE = Math.pow(2, 20) * 10;

  @Override
  public String[] getAltriCampiChiave() {
    return new String[]{"CODGARA"};
  }

  @Override
  public String getCampoNumericoChiave() {
    return "NUMDOC";
  }

  @Override
  public String getEntita() {
    return "W9DOCGARA";
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
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {
  }

  /**
   * Copia e personalizzazione del metodo AbstractGestoreEntita.gestisciAggiornamentiRecordSchedaMultipla
   * per il salvataggio dei documenti del bando di gara
   * 
   * Gestisce le operazioni di update, insert, delete dei dettagli dei record di
   * una scheda multipla.<br>
   * Questo codice funziona se si presuppone di avere una pagina con un elenco
   * di record a scheda multipla,
   * <ul>
   * <li>la cui chiave e' una parte fissa comune piu' un progressivo</li>
   * <li>i dati da aggiornare sono appartenenti ad una sola entita'</li>
   * </ul>
   * 
   * @param status
   *        stato della transazione
   * @param dataColumnContainer
   *        container di partenza da cui filtrare i record
   * @param gestore
   *        gestore a chiave numerica per l'aggiornamento del record di una
   *        scheda multipla
   * @param suffisso
   *        suffisso da concatenare a "NUMERO_" per ottenere il campo che indica
   *        il numero di occorrenze presenti nel container
   * @param valoreChiave
   *        parte non numerica della chiave dell'entita', per la valorizzazione
   *        in fase di inserimento se i dati non sono presenti nel container
   * @param campiDaNonAggiornare
   *        elenco eventuale di ulteriori campi fittizi da eliminare prima di
   *        eseguire l'aggiornamento nel DB
   * @throws GestoreException GestoreException
   */
  @Override
  public void gestisciAggiornamentiRecordSchedaMultipla(
      final TransactionStatus status, final DataColumnContainer dataColumnContainer,
      final AbstractGestoreChiaveNumerica gestore, final String suffisso,
      final DataColumn[] valoreChiave, final String[] campiDaNonAggiornare)
          throws GestoreException {
    
    String nomeCampoNumeroRecord = "NUMERO_" + suffisso;
    String nomeCampoDelete = "DEL_" + suffisso;
    String nomeCampoMod = "MOD_" + suffisso;

    // Gestione delle pubblicazioni bando solo se esiste la colonna con il
    // numero di occorrenze
    if (dataColumnContainer.isColumn(nomeCampoNumeroRecord)) {

      // Estraggo dal dataColumnContainer tutte le occorrenze dei campi
      // dell'entita' definita per il gestore
      DataColumnContainer tmpDataColumnContainer = new DataColumnContainer(
          dataColumnContainer.getColumns(gestore.getEntita(), 0));

      int numeroRecord = dataColumnContainer.getLong(nomeCampoNumeroRecord).intValue();

      // Estrazione dell'oggetto hashMap che contiene tutti i file da salvare
      HashMap< ? , ? > hmFile = ((UploadMultiploForm) this.getForm()).getFormFiles();
      try {
        for (int i = 1; i <= numeroRecord; i++) {
          DataColumnContainer newDataColumnContainer = new DataColumnContainer(
              tmpDataColumnContainer.getColumnsBySuffix("_" + i, false));
  
          boolean deleteOccorrenza = newDataColumnContainer.isColumn(nomeCampoDelete)
              && "1".equals(newDataColumnContainer.getString(nomeCampoDelete));
          boolean updateOccorrenza = newDataColumnContainer.isColumn(nomeCampoMod)
              && "1".equals(newDataColumnContainer.getString(nomeCampoMod));
          
          // Rimozione dei campi fittizi (il campo per la marcatura della delete e
          // tutti gli eventuali campi passati come argomento)
          newDataColumnContainer.removeColumns(new String[] {
              gestore.getEntita() + "." + nomeCampoDelete,
              gestore.getEntita() + "." + nomeCampoMod, 
              gestore.getEntita() + "." + "NOME_FILE"});
          
          if (campiDaNonAggiornare != null) {
            for (int j = 0; j < campiDaNonAggiornare.length; j++) {
              campiDaNonAggiornare[j] =
                gestore.getEntita() + "." + campiDaNonAggiornare[j];
            }
            newDataColumnContainer.removeColumns(campiDaNonAggiornare);
          }
  
          if (deleteOccorrenza) {
            // Se e' stata richiesta l'eliminazione e il campo chiave numerica e'
            // diverso da null eseguo l'effettiva eliminazione del record
            if (newDataColumnContainer.getLong(gestore.getCampoNumericoChiave()) != null) {
              gestore.elimina(status, newDataColumnContainer);
            }
            // altrimenti e' stato eliminato un nuovo record non ancora inserito
            // ma predisposto nel form per l'inserimento
          } else {
            if (updateOccorrenza) {
              FormFile formFile = (FormFile) hmFile.get(new Long(i));
              if (formFile != null) {
                if (formFile.getFileSize() > 0 && formFile.getFileSize() <= MAX_FILE_SIZE) {
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  baos.write(formFile.getFileData());
    
                  newDataColumnContainer.addColumn("W9DOCGARA.FILE_ALLEGATO",
                      new JdbcParametro(JdbcParametro.TIPO_BINARIO, baos));
                  // Se e' stato modificato solo il campo contenente il file da uplodare, 
                  // allora bisogna forzare l'aggiornamento del record
                  updateOccorrenza = true;
                } else {
                  // Se si sta inserendo un nuovo file ed esso ha dimensione nulla e il campo URL non e'
                  // valorizzato allora si lancia un'eccezione. 
                  // Altrimenti si sta modificando il titolo di un documento (e il file non e' possibile modificarlo)
                  if (formFile.getFileSize() == 0 && StringUtils.isEmpty(newDataColumnContainer.getString("W9DOCGARA.URL"))
                      && newDataColumnContainer.getLong(gestore.getCampoNumericoChiave()) == null) {
                    throw new GestoreException("Errore nell'inserimento del file \"" +
                        formFile.getFileName() + "\": non e' possibile inserire documenti di dimensione nulla", "upload.emptyFile", null);
                    
                  } else if (formFile.getFileSize() > MAX_FILE_SIZE) {
                    throw new GestoreException("Errore nell'inserimento del file \"" +
                        formFile.getFileName() + "\": non e' possibile inserire documenti di dimensione maggiore di 10 Mb", "upload.overflow", null);
                  }
                }
              }

              // si settano tutti i campi chiave con i valori ereditati dal chiamante
              for (int z = 0; z < gestore.getAltriCampiChiave().length; z++) {
                if (newDataColumnContainer.getColumn(
                    gestore.getAltriCampiChiave()[z]).getValue().getValue() == null) {
                  newDataColumnContainer.getColumn(
                      gestore.getAltriCampiChiave()[z]).setValue(
                      valoreChiave[z].getValue());
                }
              }
              if (newDataColumnContainer.getLong(gestore.getCampoNumericoChiave()) == null) {
                gestore.inserisci(status, newDataColumnContainer);
              } else {
                gestore.update(status, newDataColumnContainer);
              }
            }
          }
        }
      } catch (FileNotFoundException fnf) {
        throw new GestoreException("Problemi nel caricare il file perche' non trovato", null, fnf);
      } catch (IOException io) {
        throw new GestoreException(
            "Si e' verificato un errore durante la scrittura del buffer per il salvataggio di un file su DB",
            null, io);
      }
    }
  }

}