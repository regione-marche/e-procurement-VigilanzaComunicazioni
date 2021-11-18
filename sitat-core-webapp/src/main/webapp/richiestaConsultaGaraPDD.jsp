<%@ page language="java" import="java.io.ByteArrayOutputStream" %>
<%@ page language="java" import="java.io.File" %>
<%@ page language="java" import="java.io.IOException" %>
<%@ page language="java" import="java.math.BigDecimal" %>
<%@ page language="java" import="java.rmi.RemoteException" %>
<%@ page language="java" import="java.sql.SQLException" %>
<%@ page language="java" import="java.util.Calendar" %>
<%@ page language="java" import="java.util.GregorianCalendar" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@ page language="java" import="java.util.Locale" %>
<%@ page language="java" import="java.sql.Timestamp" %>
<%@ page language="java" import="java.util.Date" %>

<%@ page language="java" import="javax.xml.rpc.ServiceException" %>

<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.FlagSNType" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.FlagSOType" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.GaraType" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.GaraWSDocument" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.LottoType" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.LottoWSDocument" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.TipoSchedaType" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.GaraWSDocument.GaraWS" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.LottoWSDocument.LottoWS" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseCancellaGara" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseCancellaLotto" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseCheckLogin" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseChiudiSession" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseConsultaGara" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseInserisciGara" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponseInserisciLotto" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponsePerfezionaGara" %>
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.ResponsePerfezionaLotto" %>
<%@ page language="java" import="it.eldasoft.gene.bl.SqlManager" %>
<%@ page language="java" import="it.eldasoft.gene.web.struts.tags.gestori.GestoreException" %>
<%@ page language="java" import="it.eldasoft.utils.properties.ConfigManager" %>

<%@ page language="java" import="it.eldasoft.w9.common.CostantiSimog" %>

<%@ page language="java" import="it.eldasoft.utils.sicurezza.CriptazioneException" %>
<%@ page language="java" import="it.eldasoft.utils.sicurezza.FactoryCriptazioneByte" %>
<%@ page language="java" import="it.eldasoft.utils.sicurezza.ICriptazioneByte" %>
<%@ page language="java" import="it.eldasoft.gene.commons.web.domain.CostantiGenerali" %>

<%@ page language="java" import="it.eldasoft.utils.spring.UtilitySpring" %>

<%@ page language="java" import="org.apache.axis.AxisProperties" %>
<%@ page language="java" import="java.util.Properties" %>
<%@ page language="java" import="java.util.Enumeration" %>
<%@ page language="java" import="java.net.URL" %>
<%@ page language="java" import="java.net.URLConnection" %>
<%@ page language="java" import="java.net.Proxy" %>
<%@ page language="java" import="org.apache.commons.lang.StringUtils" %>

<%@ page language="java" import="org.apache.axis.client.Stub" %>
<%@ page language="java" import="org.apache.axis.client.Call" %>
org.apache.axis2.client.ServiceClient
<%@ page language="java" import="it.avlp.simog.massload.xmlbeans.*" %>

<%@ page import="java.net.URL"%>
<%@ page import="java.net.Proxy"%>
<%@ page import="java.net.InetSocketAddress"%>
<%@ page import="java.net.SocketAddress"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<script type="text/javascript">
		
		</script>
	</head>
	<body>
		Consulta gara:
		<br>
		<br>
		<% 
			
		it.toscana.rete.rfc.sitatort.ResponseConsultaGara resultXML = new it.toscana.rete.rfc.sitatort.ResponseConsultaGara();  
			
		// Inizializzazione dell'esito della risposta
    	resultXML.setSuccess(false);
		
	    List listaMessaggi = new ArrayList(); 
		listaMessaggi.add("Primo msg");
			
		java.lang.String codiceCIG = request.getParameter("cig");
  		java.lang.String idAvGara = request.getParameter("idAvGara");

	    try {

			  // Gestione della connessione
			  java.lang.String url = null;
			  url = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_URL);

			  if (StringUtils.isEmpty(url)) {
				listaMessaggi.add("error: L'indirizzo per la connessione al web service SIMOG non e' definito");
				//logger.error("L'indirizzo per la connessione al web service SIMOG non e' definito");
			  }

			  java.lang.String login = null;
			  login = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_LOGIN);
			  
			  if (StringUtils.isEmpty(login)) {
				listaMessaggi.add("error: La login per la connessione al web service SIMOG non e' definita");
				//logger.error("La login per la connessione al web service SIMOG non e' definita");
			  }
		  
			  java.lang.String password = null;
			  password = ConfigManager.getValore(CostantiSimog.PROP_SIMOG_WS_PASSWORD); 
				  
			  if (StringUtils.isNotEmpty(password)) {
				try {
				  ICriptazioneByte cript = FactoryCriptazioneByte.getInstance(
					  ConfigManager.getValore(CostantiGenerali.PROP_TIPOLOGIA_CIFRATURA_DATI),
					  password.getBytes(), ICriptazioneByte.FORMATO_DATO_CIFRATO);
				  password = new String(cript.getDatoNonCifrato());
				} catch (CriptazioneException e) {
				  listaMessaggi.add("error: Errore nella decriptazione della password di accesso ai servizi SIMOG"); 
				  //logger.error("Errore nella decriptazione della password di accesso ai servizi SIMOG");
				}
			  }
			  
			  if (StringUtils.isEmpty(password)) {
				//logger.error("La password per la connessione al web service SIMOG non e' definita");
				listaMessaggi.add("error: La password per la connessione al web service SIMOG non e' definita");
			  }

				if (StringUtils.isNotEmpty(url)
				  && StringUtils.isNotEmpty(login)
				  && StringUtils.isNotEmpty(password)) {

				it.avlp.simog.massload.xmlbeans.SimogWSPDDServiceStub simogWsPddServiceStub = new SimogWSPDDServiceStub(url);

				it.avlp.simog.massload.xmlbeans.ResponseCheckLogin responseCheckLogin = null;
				it.avlp.simog.massload.xmlbeans.ResponseConsultaGara responseConsultaGara = null;
				it.avlp.simog.massload.xmlbeans.ResponseChiudiSession responseChiudiSessione = null;
			  
				LoginDocument loginDoc = LoginDocument.Factory.newInstance();
				Login loginIn = Login.Factory.newInstance();
				loginIn.setLogin(login);
				loginIn.setPassword(password);
				loginDoc.setLogin(loginIn);

				LoginResponseDocument loginResponseDoc = simogWsPddServiceStub.login(loginDoc);
				LoginResponse loginResponse = loginResponseDoc.getLoginResponse();

				if (loginResponse.isSetReturn()) {
					responseCheckLogin = loginResponse.getReturn();

					if (responseCheckLogin.getSuccess()) {
						String ticket = responseCheckLogin.getTicket();

						// Per accordi con Regione Toscana e SIMOG le collaborazioni non vengono popolate.
						// Al momento l'istruzione rispostaLogin.getColl() non deve essere usata.
						// Per il valore da assegnare a schede, si seguono le indicazioni degli accordi
						// menzionati: schede viene sempre settato a '0'
						String schede = "3.03.3.0";

						ConsultaGaraDocument consultaGaraDoc = ConsultaGaraDocument.Factory.newInstance();
						ConsultaGara consultaGara = ConsultaGara.Factory.newInstance();
						consultaGara.setTicket(ticket);
						consultaGara.setSchede(schede);
						consultaGara.setCIG(codiceCIG);
						consultaGaraDoc.setConsultaGara(consultaGara);

						ConsultaGaraResponseDocument consultaGaraResponseDoc = simogWsPddServiceStub.consultaGara(consultaGaraDoc);
						it.avlp.simog.massload.xmlbeans.SchedaDocument schedaDocument =
						  it.avlp.simog.massload.xmlbeans.SchedaDocument.Factory.newInstance();

						responseConsultaGara = consultaGaraResponseDoc.getConsultaGaraResponse().getReturn();
						if (responseConsultaGara != null) {
							schedaDocument.setScheda(responseConsultaGara.getGaraXML());
							if (responseConsultaGara.getSuccess()) {
								resultXML.setError(responseConsultaGara.getError());
								resultXML.setSuccess(responseConsultaGara.getSuccess());
								resultXML.setGaraXML(schedaDocument.toString());
							} else {
								listaMessaggi.add("error: La richiesta consultaGara con codice CIG pari a '" + codiceCIG + "' e' terminata "
									+ "con esito negativo, fornendo il seguente messaggio: " + responseConsultaGara.getError());
								resultXML.setError(responseConsultaGara.getError());
								resultXML.setSuccess(responseConsultaGara.getSuccess());
								resultXML.setGaraXML(null);
							}
						} else {
							listaMessaggi.add("error: La richiesta consultaGara con codice CIG pari a '"
								+ codiceCIG + "' terminata con esito negativo a causa dell'oggetto "
								+ "it.avlp.simog.massload.xmlbeans.ResponseConsultaGara non valorizzato.");

							resultXML.setError("Errore non previsto durante l'operazione accesso ai dati dei servizi SIMOG");
							resultXML.setSuccess(false);
							resultXML.setGaraXML(null);
						}

						ChiudiSessione chiudiSessione = ChiudiSessione.Factory.newInstance();
						chiudiSessione.setTicket(ticket);

						ChiudiSessioneDocument chiudiSessioneDoc = ChiudiSessioneDocument.Factory.newInstance();
						chiudiSessioneDoc.setChiudiSessione(chiudiSessione);

						ChiudiSessioneResponseDocument chiudiSessioneResponseDoc = 
						simogWsPddServiceStub.chiudiSessione(chiudiSessioneDoc);
						responseChiudiSessione = chiudiSessioneResponseDoc.getChiudiSessioneResponse().getReturn();
				  
						if (!responseChiudiSessione.getSuccess()) {
							listaMessaggi.add("error: La chiusura della connessione identificata dal ticket "
								+ ticket
								+ " ha generato il seguente errore: "
								+ responseChiudiSessione.getError());
						} else {
							listaMessaggi.add("error: Chiusura della connessione identificata dal ticket "
								+ ticket
								+ " terminata con successo");
						}
					} else {
						// login fallita
						listaMessaggi.add("error: Login per la richiesta consultaGara con codice CIG pari a '"
							+ codiceCIG + "' terminata con esito negativo. Dettagli dell'errore: "
							+ responseCheckLogin.getError());
				  
						resultXML.setError(responseCheckLogin.getError());
						resultXML.setSuccess(responseCheckLogin.getSuccess());
						resultXML.setGaraXML(null);
					}
				} else {
					// loginResponse non e' settato l'attributo return
					// Errore nella risposta alla login...
					listaMessaggi.add("error: Login per la richiesta consultaGara con codice CIG pari a '"
						+ codiceCIG + "' terminata con esito negativo a causa dell'oggetto "
						+	"it.avlp.simog.massload.xmlbeans.LoginResponse non valorizzato.");
				
					resultXML.setError("Errore non previsto nella risposta della login ai servizi SIMOG");
					resultXML.setSuccess(false);
					resultXML.setGaraXML(null);
				}
			} else {
				// Errore nella configurazione URL, login e password

				String message = "Errore durante la connessione ai servizi SIMOG: i parametri di connessione "
					+ "non sono specificati correttamente. Contattare un amministratore.";
				listaMessaggi.add("error: Il tentativo di connessione ha generato il seguente errore: " + message);

				resultXML.setError(message);
				resultXML.setSuccess(false);
				resultXML.setGaraXML(null);
			}

		} catch (RemoteException re) {
			listaMessaggi.add("error: Errore nell'interazione con la porta di dominio per l'accesso al "
				+ "WS Simog per la chiamata consultaGara durante la funzione 'Carica lotto da "
				+ "SIMOG' con codiceCIG=" + codiceCIG + ". Messaggio di errore: " + re.getMessage()); //, re);
			log("Errore nell'interazione con la porta di dominio per l'accesso al "
				+ "WS Simog per la chiamata consultaGara durante la funzione 'Carica lotto da "
				+ "SIMOG' con codiceCIG=" + codiceCIG + ".", re);
		} finally {
			if (!resultXML.isSuccess() && StringUtils.isEmpty(resultXML.getError())) {
				resultXML.setError("Il servizio SIMOG e' momentaneamente non disponibile o non risponde correttamente. Riprovare piu' tardi. Se il problema persiste, segnalarlo all'amministratore di sistema.");
			}
		}
	      
		if (listaMessaggi.size() > 0) {
			request.setAttribute("listaMessaggi", listaMessaggi); 
		}
			
		request.setAttribute("resultXML", resultXML);
			
		%>
		
		Consulta gara verso l'AVCP
		<br>
		<br>
		<c:if test='${not empty listaMessaggi}' >
			<c:forEach items="${listaMessaggi}" var="msg">
				-&nbsp;<c:out value="${msg}"/><br>
				<c:if test='${not empty exception}'>
					<c:out value="${exception}"/><br>
				</c:if>
			</c:forEach>
		</c:if>
		<br>
		<br>
		ResultXML.success = ${resultXML.success}<br>
		ResultXML.error = ${resultXML.error}<br>
		ResultXML.garaXML = <br>
		<c:out value="${resultXML.garaXML}" />
		<br>
		<br>
	</body>
</html>