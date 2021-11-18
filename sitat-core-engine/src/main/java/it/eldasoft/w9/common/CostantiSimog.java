package it.eldasoft.w9.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe di costanti per l'interazione con i servizi SIMOG
 * messi a disposizione dell'AVCP.
 * 
 * @author Luca.Giacomazzo
 */
public class CostantiSimog {

  // Tipi di accesso al WebService SIMOG per la funzione 'Consulta Gara'
  // ***   Valori validi per SitatSA   ***

  /**   Accesso ai servizi Simog direttamente da SitatSA  */
  public static final int SA_CONSULTA_GARA_ACCESSO_VIA_WS = 1;
  
  /**   Accesso ai servizi Simog in modo indiretto, attraverso SitatORT */
  public static final int SA_CONSULTA_GARA_ACCESSO_INDIRETTO_VIA_WS = 3;

  // Tipi di accesso al WebService SIMOG per la funzione 'Consulta Gara'
  // ***   Valori validi per SitatORT   ***
  
  /**   Accesso al WS Simog attraverso il Web Service di AVCP  */
  public static final int ORT_CONSULTA_GARA_VIA_WS = 5;

  /** Parametro da inviare a SIMOG nelle funzioni di consultaGara.
   * Il parametro rappresenta la versione di SIMOG alla quale il client
   * e' compatibile.
   */
  public static final String CONSULTA_GARA_SCHEDE = "3.04.4.1.6";
  
  /**
   * Tipo di accesso ai servizi SIMOG.
   */
  public static final String PROP_TIPO_ACCESSO                  = "it.eldasoft.simog.tipoAccesso";

  
  /**
   * URL del WS SIMOG o della PDD definita per accedere al SimogWS o di SitatORT per accedere al SimogWS in modo indiretto.
   */
  public static final String PROP_SIMOG_WS_URL                  = "it.eldasoft.simog.ws.url";

  /**
   * Login per accesso al WS SIMOG.
   */
  public static final String PROP_SIMOG_WS_LOGIN                = "it.eldasoft.simog.ws.login";

  /**
   * Password per accesso al WS SIMOG.
   */
  public static final String PROP_SIMOG_WS_PASSWORD             = "it.eldasoft.simog.ws.password";

  /**
   * Property definizione della url per chiamare il servizio loaderAppalto di SIMOG.
   */
  public static final String PROP_SIMOG_URL_LOADERAPPALTO        = "it.eldasoft.simog.loaderAppalto.url";

  /**
   * Property definizione della cartella di destinazione dei file xml di export per SIMOG.
   */
  public static final String PROP_SIMOG_XML_DATAPATH             = "it.eldasoft.simog.xml.datapath";

  /**
   * Property definizione del timeout di SitatSA nella chiamata consultaGara verso SitatORT o
   * per la definizione del timeout di SitatORT nella chiamata consultaGara verso i servizi SIMOG.
   */
  public static final String PROP_SITAT_CLIENT_TIMEOUT         = "it.eldasoft.sitat.consultaGara.clientTimeout";
  
  /**
   * Property definizione Abilita / disabilita controllo RUP nella chiamata consultaGara verso i servizi SIMOG.
   */
  public static final String PROP_SITAT_CONSULTA_GARA_CONTROLLO_RUP         = "it.eldasoft.sitat.consultaGara.controlloRUP";
  
  /**
   * Property definizione Abilita / disabilita controllo del codice fiscale della stazione Appaltante su consulta gara verso i servizi SIMOG.
   */
  public static final String PROP_SITAT_CONSULTA_GARA_CONTROLLO_CFSA         = "it.eldasoft.sitat.consultaGara.controlloCFSA";
 
  /**
   * Property definizione url del proxy per uscire dalla intranet del cliente per i servizi SIMOG.
   */
  public static final String PROP_SITAT_SERVIZI_SIMOG_PROXY_URL              = "it.eldasoft.simog.ws.proxy.url";
  
  /**
   * Property definizione porta del proxy per uscire dalla intranet del cliente per i servizi SIMOG.
   */
  public static final String PROP_SITAT_SERVIZI_SIMOG_PROXY_PORT              = "it.eldasoft.simog.ws.proxy.port";
  
  /**
   * Property definizione username di accesso al proxy per uscire dalla intranet del cliente per i servizi SIMOG.
   */
  public static final String PROP_SITAT_SERVIZI_SIMOG_PROXY_USERNAME          = "it.eldasoft.simog.ws.proxy.username";
  
  /**
   * Property definizione password di accesso al proxy per uscire dalla intranet del cliente per i servizi SIMOG.
   */
  public static final String PROP_SITAT_SERVIZI_SIMOG_PROXY_PASSWORD          = "it.eldasoft.simog.ws.proxy.password";
  
  /**
   * URL del WS SIMOG per la richiesta smartCIG.
   */
  public static final String PROP_SIMOG_WS_SMARTCIG_URL       				  = "it.eldasoft.simog.ws.smartcig.url";
  
  /**
   * Tipi di schede gestite in SIMOG.
   */
  public static final Map<Long, String> TipiSchede = new HashMap<Long, String>() { 
		
  	/**  UID  */
		private static final long serialVersionUID = 6054925881124664882L;
		{
		  put(new Long(984),"DATI_COMUNI");
		  put(new Long(1),"AGGIUDICAZIONE");
		  put(new Long(987),"SOTTOSOGLIA");
		  put(new Long(12),"ADESIONE");
		  put(new Long(2),"FASE_INIZIALE");
		  put(new Long(11),"STIPULA");
		  put(new Long(3),"STATO_AVANZAMENTO");
		  put(new Long(4),"FINE_LAVORI");
		  put(new Long(5),"COLLAUDO");
		  put(new Long(8),"ACCORDO_BONARIO");
		  put(new Long(6),"SOSPENSIONE");
		  put(new Long(7),"VARIANTE");
		  put(new Long(9),"SUBAPPALTO");
		  put(new Long(10),"IPOTESI_RECESSO");
		}
  };
  
}
