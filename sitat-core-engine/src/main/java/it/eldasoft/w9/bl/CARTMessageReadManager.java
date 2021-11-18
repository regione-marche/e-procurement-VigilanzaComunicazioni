package it.eldasoft.w9.bl;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.w9.bl.delegate.SitatRequestProcessorFacade;
import it.eldasoft.w9.common.CostantiSitatORT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.FaseType;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;

/**
 * Classe adibita allo scarico dei messaggi provenienti dal CART mediante
 * l'utilizzo dell'integration manager. Puo' essere richiamata sia via
 * scheduler, sia interattivamente.
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class CARTMessageReadManager {

  /**
   * Logger della classe.
   */
  Logger                              logger = Logger.getLogger(CARTMessageReadManager.class);

  /**
   * sitatRequestProcessorFacade SitatRequestProcessorFacade.
   */
  private SitatRequestProcessorFacade sitatRequestProcessorFacade;

  /**
   * salManager SqlManager.
   */
  private SqlManager                  sqlManager;

  /**
   * @param sitatRequestProcessorFacade
   *        sitatRequestProcessorFacade da settare internamente alla classe.
   */
  public void setSitatRequestProcessorFacade(
      final SitatRequestProcessorFacade sitatRequestProcessorFacade) {
    this.sitatRequestProcessorFacade = sitatRequestProcessorFacade;
  }

  public void setSqlManager(final SqlManager sqlMan){
    this.sqlManager = sqlMan;
  }

  public synchronized EsitoMessageReader getMessages() {
    EsitoMessageReader esito = new EsitoMessageReader();
    if (logger.isDebugEnabled()) {
      logger.debug("readMessages: inizio metodo");
    }

    // predisposizione parametri di connessione
    
    String url = ConfigManager.getValore(CostantiSitatORT.INTEGRATION_MANAGER_URL);
    String username = ConfigManager.getValore(CostantiSitatORT.INTEGRATION_MANAGER_USER);
    String password = ConfigManager.getValore(CostantiSitatORT.INTEGRATION_MANAGER_PASS);
   
    IntegrationManagerUtility imUtility = new IntegrationManagerUtility(url,
        username, password);
    String tipoServizio = ConfigManager.getValore(CostantiSitatORT.INTEGRATION_MANAGER_TYPE_SERVICE);
    String nomeServizio = ConfigManager.getValore(CostantiSitatORT.INTEGRATION_MANAGER_NAME_SERVICE);
    String azione = null;

    boolean messaggiLetti = false;
    try {
      // lettura messaggi
      Map<String, SPCoopMessage> messaggi = imUtility.getCartMessages(
          tipoServizio, nomeServizio, azione);
      esito.setNumeroMsgLetti(messaggi.size());
      messaggiLetti = true;

      Set<String> elencoIdEgov = messaggi.keySet();

      // L.G. 13/05/2011
      // Per evitare di importare flussi in modo casuale ed incorrere nell'errore
      // che si importi il flusso di aggiudicazione prima del flusso anagrafica gara

      List< ? > listaTab1Cod = this.sqlManager.getListVector("select TAB1TIP, TAB1NORD from TAB1 where TAB1COD = ? order by TAB1NORD asc", new Object[]{"W3020"});
      HashMap<Long, Double> mappaTab1Cod = new HashMap<Long, Double>();
      for (int iui = 0; iui < listaTab1Cod.size(); iui++) {
        Vector< ? > riga = (Vector< ? >) listaTab1Cod.get(iui);
        Long tab1tip = (Long) SqlManager.getValueFromVectorParam(riga, 0).getValue();
        Double tab1nord = (Double) SqlManager.getValueFromVectorParam(riga, 1).getValue();
        mappaTab1Cod.put(tab1tip, tab1nord);
      }

      List<FaseEstesaOrdinabile> listaFlussi = new ArrayList<FaseEstesaOrdinabile>();
      List<String> listaFlussiErrati = new ArrayList<String>();
      for (Iterator< ? > iterator = elencoIdEgov.iterator(); iterator.hasNext();) {
        String idEgov = (String) iterator.next();

        if (logger.isDebugEnabled()) {
          logger.debug("Lettura del messaggio con idEgov = " + idEgov);
        }
        FaseType fase = this.getFase(messaggi.get(idEgov));
        int num = -1;
        if (fase != null) {

          if (fase instanceof FaseEstesaType) {
            num = ((FaseEstesaType) fase).getW3NUM5();
          }

          FaseEstesaOrdinabile faseOrdinabile = new FaseEstesaOrdinabile(
              fase.getW9FLCFSA(),
              mappaTab1Cod.get(new Long(fase.getW3FASEESEC().toString())),
              new Long(fase.getW3PGTINVIO2().toString()),
              num != -1 ? new Long(num) : null, idEgov);

          listaFlussi.add(faseOrdinabile);
        } else {
          listaFlussiErrati.add(idEgov);
        }
      }
      // Ordinamento della lista dei flussi scaricati dal CART
      Collections.sort(listaFlussi);

      for (Iterator<FaseEstesaOrdinabile> iter = listaFlussi.iterator(); iter.hasNext();) {
        String idEgov = iter.next().getIdegov();
        if (logger.isDebugEnabled()) {
          logger.debug("Salvataggio messaggio con idEgov=" + idEgov + "\n"
              + new String(messaggi.get(idEgov).getMessage()));
        }
        this.saveMessage(imUtility, idEgov, messaggi.get(idEgov));
        esito.setNumeroMsgSalvati(esito.getNumeroMsgSalvati() + 1);
      }

      // Elaborazione degli eventuali flussi errati
      for (int ui = 0; ui < listaFlussiErrati.size(); ui++) {
        String idEgov = listaFlussiErrati.get(ui);
        if (logger.isDebugEnabled()) {
          logger.debug("Salvataggio messaggio con idEgov=" + idEgov + "\n"
              + new String(messaggi.get(idEgov).getMessage()));
        }
        this.saveMessage(imUtility, idEgov, messaggi.get(idEgov));
        esito.setNumeroMsgSalvati(esito.getNumeroMsgSalvati() + 1);
      }

      if (logger.isInfoEnabled()) {
        logger.info("Sono stati scaricati " + esito.getNumeroMsgSalvati() + " messaggi");
      }
    } catch (SQLException s) {
      String msg = "Errore nell'estrarre il tabellato W3020 da tab1";
      logger.error(msg, s);
      esito.setEsito(false);
      esito.setMsgErrore(msg + " - " + s.getCause().toString());
    } catch (SPCoopException e) {
      if ("CART_406".equalsIgnoreCase(e.getCodiceEccezione())) {
        logger.info(e.getDescrizioneEccezione());
      } else {
        String msg = null;
        // Eccezione legata a problemi relativi all'infrastruttura CART
        if (!messaggiLetti) {
          msg = "Errore durante la lettura della richiesta mediante Integration Manager: ";
        } else {
          msg = "Errore durante l'eliminazione di una richiesta mediante Integration Manager: ";
        }
        logger.error(msg + e.getDescrizioneEccezione(), e);
        esito.setEsito(false);
        esito.setMsgErrore(msg);
      }
    } catch (RemoteException e) {
      logger.error("Errore nella chiamata all'Integration Manager", e);
      esito.setEsito(false);
    } catch (ServiceException e) {
      logger.error("Errore durante la connessione all'Integration Manager", e);
      esito.setEsito(false);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("readMessages: fine metodo");
    }
    return esito;
  }

  /**
   * Salva il messaggio sul DB rendendolo persistente, ne processa il contenuto,
   * quindi lo elimina dalla coda dei messaggi.
   * 
   * @param imUtility
   *        proxy per l'interfacciamento con l'integration manager
   * @param idEgov
   *        id egov del messaggio
   * @param message
   *        messaggio
   * @throws SPCoopException
   * @throws RemoteException
   * @throws ServiceException
   */
  public void saveMessage(final IntegrationManagerUtility imUtility, final String idEgov,
      final SPCoopMessage message) throws SPCoopException, RemoteException, ServiceException {
    boolean esito = this.sitatRequestProcessorFacade.processEvento(message);
    if (esito) {
      imUtility.deleteCartMessageById(idEgov);
    }
  }

  /**
   * Estrae l'elemento fase della richiesta.
   * 
   * @param message
   * @return Ritorna l'elemento fase della richiesta
   * 
   * @throws SPCoopException SPCoopException
   * @throws RemoteException RemoteException
   * @throws ServiceException ServiceException
   */
  public FaseType getFase(final SPCoopMessage message) throws SPCoopException, RemoteException, ServiceException {
    return this.sitatRequestProcessorFacade.getFase(message);
  }

  /**
   * Classe per ordinare i flussi scaricati dal CART secondo alcuni degli
   * attributi dell'oggetto FaseType o FaseEstesaType.
   * 
   *  Il tabellato W3998 (Tipologia di invio) prevede i seguenti valori:
   *  1 - Primo invio
   *  2 - Rettifica
   *  3 - Integrazione
   *  99 - Test
   * 
   *  Secondo questo tabellato, due flussi con la stessa fase, ma con tipologia invio diversa,
   *  vengono importati per tipologia invio crescente (quindi rettifica viene importato prima
   *  di integrazione)
   * 
   * @author Luca.Giacomazzo
   */
  class FaseEstesaOrdinabile implements Comparable<FaseEstesaOrdinabile> {
    /**
     * CF della Stazione appaltante.
     */
    private final String codiceFiscaleSA;
    /**
     * Numero d'ordine della fase.
     */
    private Double numeroOrdineFaseFlusso;
    /**
     * Tipo di invio (primo invio, rettifica, integrazione).
     */
    private Long tipoInvio;
    /**
     * Numero Progressivo.
     */
    private Long numeroProgressivo;
    /**
     * IdEgov del flusso.
     */
    private String idegov;

    /**
     * Costruttore senza argomenti.
     */
    public FaseEstesaOrdinabile() {
      this.codiceFiscaleSA = null;
      this.numeroOrdineFaseFlusso = new Double(Double.MIN_VALUE);
      this.tipoInvio = new Long(Long.MIN_VALUE);
      this.numeroProgressivo = new Long(Long.MIN_VALUE);
      this.idegov = null;
    }

    /**
     * Costruttore con argomenti.
     * 
     * @param cfSA CF della Stazione appaltante
     * @param numeroOrdineFaseFlusso Numero d'ordine della fase
     * @param tipoInvio Tipo di invio (primo invio, rettifica, integrazione)
     * @param numeroProgressivo Numero Progressivo
     * @param idegov IdEgov del flusso
     */
    public FaseEstesaOrdinabile(final String cfSA, final Double numeroOrdineFaseFlusso,
        final Long tipoInvio, final Long numeroProgressivo, final String idegov) {
      this.codiceFiscaleSA = cfSA;

      if (numeroOrdineFaseFlusso != null) {
        this.numeroOrdineFaseFlusso = numeroOrdineFaseFlusso;
      } else {
        this.numeroOrdineFaseFlusso = new Double(Double.MIN_VALUE);
      }
      if (tipoInvio != null) {
        this.tipoInvio = tipoInvio;
      } else {
        this.tipoInvio = new Long(Long.MIN_VALUE);
      }
      if (numeroProgressivo != null) {
        this.numeroProgressivo = numeroProgressivo;
      } else {
        this.numeroProgressivo = new Long(Long.MIN_VALUE);
      }
      if (idegov != null && idegov.length() > 0) {
        this.idegov = idegov;
      }
    }

    /**
     * Ritorna il codice fiscale della stazione appaltante.
     * @return Ritorna il codice fiscale della stazione appaltante
     */
    public String getCodiceFiscaleSA() {
      return this.codiceFiscaleSA;
    }

    /**
     * Ritorna il numero d'ordine della fase.
     * @return Ritorna il numero d'ordine della fase
     */
    public Double getNumeroOrdineFaseFlusso() {
      return this.numeroOrdineFaseFlusso;
    }

    /**
     * Ritorna il numero progressivo.
     * @return Ritorna il numero progressivo
     */
    public Long getNumeroProgressivo() {
      return this.numeroProgressivo;
    }

    /**
     * Ritorna tipo invio.
     * @return Ritorna tipo invio
     */
    public Long getTipoInvio(){
      return this.tipoInvio;
    }

    /**
     * Ritorna idegov.
     * @return Ritorna idegov
     */
    public String getIdegov(){
      return this.idegov;
    }

    /**
     * Implementazione del metodo compareTo per l'oggetto FaseEstesaOrdinabile.
     * 
     * @param obj Oggetto di tipo FaseEstesaOrdinabile da confrontare
     * @return Ritorna 1 se l'oggetto e' maggiore dell'oggetto obj,
     *         -1 se l'oggetto e' minore dell'oggetto obj e 0 se se l'oggetto e' uguale all'oggetto obj
     */
    public int compareTo(final FaseEstesaOrdinabile obj) {
      int result = -1;
      if (obj != null) {
        result = this.codiceFiscaleSA.compareTo(obj.getCodiceFiscaleSA());
        if (result == 0) {
          result = this.numeroOrdineFaseFlusso.compareTo(obj.getNumeroOrdineFaseFlusso());
          if (result == 0) {
        	// Nel confrontare il tipo di invio bisogna considerare che in caso di flusso di richiesta cancellazione
        	// di una fase il tipo invio vale -1 che è sempre minore degli altri valori assunti dal tipo invio.
        	// (Vedi TAB1 - W3998).
        	// Per questo si usano due variabili temporanee per fare il confronto tra i due tipi di invio e in caso
        	// i valori dei tipi invio da confrontare siano minori di zero, allora si valorizza la variabile 
        	// temporanea a 98.
        	Long thisTipoInvio = this.tipoInvio;
        	Long objTipoInvio = obj.getTipoInvio();
        	if (this.tipoInvio < 0) {
        	  thisTipoInvio = new Long(98);
        	}
        	if (objTipoInvio < 0) {
        	  objTipoInvio = new Long(98);
        	}
            result = thisTipoInvio.compareTo(objTipoInvio);
            if (result == 0) {
              result = this.numeroProgressivo.compareTo(obj.getNumeroProgressivo());
              if (result == 0) {
                if (this.idegov == null) {
                  if (obj.getIdegov() == null) {
                    result = 0;
                  } else {
                    result = -1;
                  }
                } else {
                  if (obj.getIdegov() == null) {
                    result = 1;
                  } else {
                    // Si prova di capire l'ordine di invio base a data e ore-minuti indicati nell'IdEGov
                    String[] idEGovSplit = this.idegov.split("_");
                    String dataOraMin = idEGovSplit[idEGovSplit.length - 2] + "_" + idEGovSplit[idEGovSplit.length - 1];

                    String[] idEGovSplitObj = obj.getIdegov().split("_");
                    String dataOraMinObj = idEGovSplitObj[idEGovSplitObj.length - 2] + "_" + idEGovSplitObj[idEGovSplitObj.length - 1];
                    result = dataOraMin.compareTo(dataOraMinObj);
                  }
                }
              }
            }
          }
        }
      } else {
        throw new NullPointerException("argument is null");
      }
      return result;
    }

    /**
     * Implementazione del metodo toString().
     * 
     * @return Ritorna il toString() dell'oggetto FaseEstesaOrdinabile
     */
    @Override
    public String toString() {
      StringBuffer buffer = new StringBuffer("");
      buffer.append("CFSA = " + this.getCodiceFiscaleSA() + ", ");
      buffer.append("NumeroOrdineFaseFlusso = " + this.getNumeroOrdineFaseFlusso() + ", ");
      buffer.append("TipoInvio = " + this.getTipoInvio() + ", ");
      buffer.append("NumeroProgressivo = " + this.getNumeroProgressivo() + ", ");
      buffer.append("Idegov = " + this.getIdegov());

      return buffer.toString();
    }
  }

}