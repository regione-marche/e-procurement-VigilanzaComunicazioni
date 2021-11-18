package it.eldasoft.w9.bl;

import it.avlp.simog.massload.xmlbeans.PresaInCaricoGaraDelegata;
import it.avlp.simog.massload.xmlbeans.PresaInCaricoGaraDelegataDocument;
import it.avlp.simog.massload.xmlbeans.PresaInCaricoGaraDelegataResponseDocument;
import it.avlp.simog.massload.xmlbeans.ResponsePresaInCarico;
import it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.utils.properties.ConfigManager;
import it.eldasoft.utils.sicurezza.CriptazioneException;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;
import it.eldasoft.w9.bl.simog.AccessoSimogManager;
import it.eldasoft.w9.bl.simog.TicketSimogBean;
import it.eldasoft.w9.common.CostantiSimog;
import it.toscana.rete.rfc.sitatort.ResponsePresaInCaricoGaraDelegata;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class GaraDelegataManager {

  /**
   * Logger della classe.
   */
  private static Logger logger = Logger.getLogger(GaraDelegataManager.class);

  /**
   * Manager per acceso ai servizi Simog senza gestione centralizzata del ticket rilasciato da Simog.
   */
  private AccessoSimogManager accessoSimogManager;

  public void setAccessoSimogManager(AccessoSimogManager accessoSimogManager) {
    this.accessoSimogManager = accessoSimogManager;
  }
  
  
  /**
   * Metodo per invocare il metodo presaInCaricoGaraDelegata dei SIMOG con ingresso
   * il numero gara (IdAvGara), cf del rup che sara' responsabile della gara.
   * 
   * @param idAvGara
   * @param cfRup cf del RUP che prende in carico la gara
   * òparam indiceColl indice di collaborazione
   * @return Ritorna l'xml contenente le informazioni di dettaglio della gara
   * @throws RemoteException RemoteException
   */
  public ResponsePresaInCaricoGaraDelegata presaInCaricoGaraDelegata(final String idGara, 
      final String cfRup, final String indiceColl, final String simogUser, final String simogPassword)
          throws RemoteException {

    ResponsePresaInCaricoGaraDelegata resultPresaInCarico = new ResponsePresaInCaricoGaraDelegata();

    logger.debug("presaInCaricoGaraDelegata: inizio metodo");

    // Inizializzazione dell'esito della risposta
    resultPresaInCarico.setSuccess(true);

    // Gestione della connessione
    String urlServiziSimog = null;
    String loginServiziSimog = null;
    String passwordServiziSimog = null;
    
    String a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);
    if (StringUtils.isNotEmpty(a)) {
      urlServiziSimog = new String(a);
    } else {
      logger.error("L'url per la connessione ai servizi SIMOG non e' definita");
    }
    
    if (StringUtils.isEmpty(simogUser)) {
      a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
      if (StringUtils.isNotEmpty(a)) {
        loginServiziSimog = new String(a);
      }
    } else {
      loginServiziSimog = new String(simogUser);
    }
    
    if (StringUtils.isEmpty(simogPassword)) {
      a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
      if (StringUtils.isNotEmpty(a)) {
        try {
          ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
              ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
              a.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
          passwordServiziSimog = new String(cript.getDatoNonCifrato());
        } catch (CriptazioneException e) {
          logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG");
        }
      }
    } else {
      passwordServiziSimog = new String(simogPassword);
    }
    
    if (StringUtils.isNotEmpty(urlServiziSimog)
        && StringUtils.isNotEmpty(loginServiziSimog)
            && StringUtils.isNotEmpty(passwordServiziSimog)) {

      boolean continua = true;

      if (continua) {
        SimogWSPDDServiceStub simogWsPddServiceStub = null;
        
        TicketSimogBean ticketSimogBean = null;
        if (StringUtils.isNotEmpty(simogUser) && StringUtils.isNotEmpty(simogPassword)) {
          // 
          ticketSimogBean = this.accessoSimogManager.simogLogin(simogUser, simogPassword);
        } else {
          // Richiesta del ticket all'oggetto per la gestione centralizzata della loginRPNT ai servizi SIMOG
          ticketSimogBean = this.accessoSimogManager.simogLoginRPNT(loginServiziSimog, passwordServiziSimog, cfRup);
        }
        if (ticketSimogBean != null) {
          if (ticketSimogBean.isSuccess()) {
            String ticket = ticketSimogBean.getTicketSimog();
            
            try {
              PresaInCaricoGaraDelegataDocument presaInCaricoGaraDelegataDoc = PresaInCaricoGaraDelegataDocument.Factory.newInstance();
              PresaInCaricoGaraDelegata presaInCaricoGaraDelegata = PresaInCaricoGaraDelegata.Factory.newInstance();
              presaInCaricoGaraDelegata.setIdgara(idGara);
              presaInCaricoGaraDelegata.setTicket(ticket);
              presaInCaricoGaraDelegata.setIndexCollaborazione(indiceColl);
              
              presaInCaricoGaraDelegataDoc.setPresaInCaricoGaraDelegata(presaInCaricoGaraDelegata);
              

              simogWsPddServiceStub = new SimogWSPDDServiceStub(urlServiziSimog);
              this.accessoSimogManager.setProxy(simogWsPddServiceStub);
              this.accessoSimogManager.setTimeout(simogWsPddServiceStub);

              logger.info("Invocazione metodo presaInCaricoGaraDelegata verso i Servizi SIMOG (Ticket="
                  + ticket + ", IdGara=" + idGara + ")");
              PresaInCaricoGaraDelegataResponseDocument presaInCaricoGaraDelegataResponseDoc =
                  simogWsPddServiceStub.presaInCaricoGaraDelegata(presaInCaricoGaraDelegataDoc);
              logger.info("Invocato metodo presaInCaricoGaraDelegata verso i Servizi SIMOG (Ticket="
                  + ticket + ", IdGara=" + idGara + ")");
              
              if (presaInCaricoGaraDelegataResponseDoc.getPresaInCaricoGaraDelegataResponse().isSetReturn()) {
                ResponsePresaInCarico presaInCaricoResponse = presaInCaricoGaraDelegataResponseDoc.getPresaInCaricoGaraDelegataResponse().getReturn();
                if (presaInCaricoResponse.getSuccess()) {
                  resultPresaInCarico.setSuccess(Boolean.TRUE);
                  if (presaInCaricoResponse.isSetError() && StringUtils.isNotEmpty(presaInCaricoResponse.getError())) {
                    resultPresaInCarico.setError(presaInCaricoResponse.getError());
                  }
                  if (presaInCaricoResponse.isSetMessaggio()) {
                    resultPresaInCarico.setMessaggio(presaInCaricoResponse.getMessaggio());
                  }
                  
                } else {
                  // Il metodo presaInCaricoGaraDelegata non e' terminato con successo.
                  String msg = "Il metodo presaInCaricoGaraDelegata di SIMOG e' fallito per la gara con IDGARA=" + idGara + " cfRup=" + cfRup;
                  if (presaInCaricoResponse.isSetError()) {
                    msg = msg.concat(". Dettaglio dell'errore: "
                        + presaInCaricoResponse.getError());
                  }
                  logger.error(msg);
                  
                  resultPresaInCarico.setSuccess(Boolean.FALSE);
                  if (presaInCaricoResponse.getError() != null) {
                    resultPresaInCarico.setError(presaInCaricoResponse.getError());
                  } else {
                    resultPresaInCarico.setError("Errore per inconsistenza dei dati ricevuti da SIMOG");
                  }
                }
              } else {
                // La risposta del metodo consultaNumeroGara non ha valorizzatto l'oggetto Return.
                logger.error("Errore nella risposta al metodo presaInCaricoGaraDelegata di SIMOG, perche' priva "
                    + "dell'oggetto che dovrebbe rappresentare la risposta della chiamata relativa alla "
                    + "gara con IDGARA=" + idGara);
                resultPresaInCarico.setSuccess(Boolean.FALSE);
                resultPresaInCarico.setError("Errore per inconsistenza dei dati ricevuti da SIMOG");
              }

            } catch (RemoteException re) {
              logger.error("Errore remoto nell'interazione con i servizi SIMOG per la chiamata presaInCaricoGaraDelegata con IDGARA="
                  + idGara + ", cfRup=" + cfRup, re);
              resultPresaInCarico.setError("Errore ");
              resultPresaInCarico.setSuccess(false);
            } catch (Throwable t) {
              logger.error("Errore inaspettato nella presa in carico gara delegata (IDGARA=" + idGara + ", cfRup=" + cfRup +
                  "). Contattare un amministratore.", t);
              resultPresaInCarico.setError("Errore inaspettato nella presa in carico gara delegata");
              resultPresaInCarico.setSuccess(false);
            } finally {
              if (!resultPresaInCarico.isSuccess() && StringUtils.isEmpty(resultPresaInCarico.getError())) {
                resultPresaInCarico.setError("Il servizio SIMOG e' momentaneamente non disponibile o non risponde correttamente. " +
                    "Riprovare piu' tardi. Se il problema persiste, segnalarlo all'amministratore di sistema.");
              }
              // Alla fine dell'interazione con i servizi Simog si rilascia il ticket usato
              this.accessoSimogManager.simogLogout(ticket);
            }
          } else {
            // LoginRPNT non riuscito
            if (StringUtils.isNotEmpty(ticketSimogBean.getMsgError())) {
              resultPresaInCarico.setError(ticketSimogBean.getMsgError());
            } else {
              resultPresaInCarico.setError("Errore non indicato nell'operazione di login ai servizi SIMOG");
            }
            resultPresaInCarico.setSuccess(false);
          }
        } else {
          // l'accessoSimogManager non e' riuscito a fornire un ticket,
          resultPresaInCarico.setError("Errore nell'operazione di login ai servizi SIMOG");
          resultPresaInCarico.setSuccess(false);
        }
      }
    } else {
      // Errore nella configurazione URL
      String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
          + "non sono specificati correttamente. Contattare un amministratore.";
      logger.error("Il tentativo di connessione ha generato il seguente errore: " + message);

      resultPresaInCarico.setError(message);
      resultPresaInCarico.setSuccess(false);
    }

    
    // Valore di default per il tipo di accesso ai servizi Simog
    /*int tipoAccessoWSSimog = 0;
    String a = ConfigManager.getValore(CostantiSimog.PROP_TIPO_ACCESSO);
    if (StringUtils.isNotEmpty(a)) {
      tipoAccessoWSSimog = Integer.parseInt(a);
    }
    
    switch (tipoAccessoWSSimog) {
      
    case CostantiSimog.SA_CONSULTA_GARA_ACCESSO_VIA_WS:
      this.prendiInCaricoGaraDelegata(idGara, cfRup, indiceColl, resultPresaInCarico, simogUser, simogPassword);
      break;
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_WS:
    case CostantiSimog.ORT_CONSULTA_GARA_VIA_PDD:
      this.prendiInCaricoGaraDelegata(idGara, cfRup, indiceColl, resultPresaInCarico, null, null);
      break;
    
    default:
      String msgError = "Errore nella configurazione dell'applicazione: non e' stato " +
          "indicato il tipo di accesso ai servizi SIMOG";
      logger.error(msgError);
      
      resultPresaInCarico.setSuccess(Boolean.FALSE);
      resultPresaInCarico.setError(msgError + ". Contattare un amministratore");
      break;
    }*/

    logger.debug("presaInCaricoGaraDelegata: fine metodo");

    return resultPresaInCarico;
  }
  
  /**
   * Ritorna le collaborazioni di SIMOG del Rup indicato attraverso cfRup.
   * 
   * @param cfRup
   * @param simogUser
   * @param simogPassword
   * @return Ritorna le collaborazioni di SIMOG del Rup 
   */
/*  public String getLoginRPNT(final String cfRup, final String simogUser, final String simogPassword) {
    
    logger.debug("getLoginRPNT: inizio metodo");
    
    // Gestione della connessione
    String loginServiziSimog = null;
    String passwordServiziSimog = null;
    
    String a = null;
    
    if (StringUtils.isEmpty(simogUser)) {
      a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
      if (StringUtils.isNotEmpty(a)) {
        loginServiziSimog = new String(a);
      }
    } else {
      loginServiziSimog = new String(simogUser);
    }
    
    if (StringUtils.isEmpty(simogPassword)) {
      a = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD);
      if (StringUtils.isNotEmpty(a)) {
        try {
          ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
              ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
              a.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
          passwordServiziSimog = new String(cript.getDatoNonCifrato());
        } catch (CriptazioneException e) {
          logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG");
        }
      }
    } else {
      loginServiziSimog = new String(simogPassword);
    }
    
    if (StringUtils.isNotEmpty(loginServiziSimog)
          && StringUtils.isNotEmpty(passwordServiziSimog)) {
    
      TicketSimogBean ticketSimogBean = this.accessoSimogManager.simogLoginRPNT(loginServiziSimog, passwordServiziSimog, cfRup);
      if (ticketSimogBean != null) {
        if (ticketSimogBean.isSuccess() && ticketSimogBean.getCollaborazioni() != null) {
          resultColl = ticketSimogBean.getCollaborazioni();
        } else {
          logger.error("Errore nell'operazione di loginRPNT");
        }
      } else {
        logger.error("Errore nell'operazione di loginRPNT");
      }
    } else {
      logger.error("Login e/o password per loginRPNT non valorizzate");
    }
    
    logger.debug("getLoginRPNT: fine metodo");
    
    return resultColl;
  }*/
  
}
