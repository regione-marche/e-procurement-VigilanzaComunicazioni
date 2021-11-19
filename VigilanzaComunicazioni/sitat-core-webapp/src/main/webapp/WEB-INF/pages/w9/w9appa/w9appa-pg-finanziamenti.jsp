
<%
	/*
	 * Created on 25-Jan-2010
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	/* Dati generali del lavoro */
%>
<gene:formScheda entita="W9APPA" gestisciProtezioni="true"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA"
	where='W9APPA.CODGARA = #W9APPA.CODGARA# AND W9APPA.CODLOTT = #W9APPA.CODLOTT# AND W9APPA.NUM_APPA = #W9APPA.NUM_APPA#'>

	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<c:set var="numappa" value='${gene:getValCampo(key,"NUM_APPA")}' />
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<gene:campoScheda campo="CODLOTT" visibile="false" />
	<gene:campoScheda campo="CODGARA" visibile="false" />
	<gene:campoScheda campo="NUM_APPA" visibile="false" />

	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	
	<c:if test='${modo ne "NUOVO"}'>
		<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetFinanziamentiFunction" parametro="${key}" />
	</c:if>

	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
		<jsp:param name="entita" value='W9FINA' />
		<jsp:param name="chiave"
			value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};${gene:getValCampo(key, "NUM_APPA")}' />
		<jsp:param name="nomeAttributoLista" value='listaFinanziamenti' />
		<jsp:param name="idProtezioni" value="W9FINA" />
		<jsp:param name="jspDettaglioSingolo"
			value="/WEB-INF/pages/w9/w9appa/finanziamenti.jsp" />
		<jsp:param name="arrayCampi"
			value="'W9FINA_CODGARA_','W9FINA_CODLOTT_','W9FINA_NUM_APPA_','W9FINA_NUM_FINA_','W9FINA_ID_FINANZIAMENTO_','W9FINA_IMPORTO_FINANZIAMENTO_'" />
		<jsp:param name="usaContatoreLista" value="true" />
		<jsp:param name="sezioneListaVuota" value="true" />
		<jsp:param name="titoloSezione" value="Finanziamenti" />
		<jsp:param name="titoloNuovaSezione" value="Nuovo Finanziamento" />
		<jsp:param name="descEntitaVociLink" value="Finanziamenti" />
		<jsp:param name="msgRaggiuntoMax" value="e Finanziamenti" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>

	<c:if test='${not empty requestScope.importoComplessivoIntervento}' >
		
		var tmpShowNextElementoSchedaMultipla = showNextElementoSchedaMultipla;
		
		showNextElementoSchedaMultipla = function(tipo, campi, visibilitaCampi) {
			tmpShowNextElementoSchedaMultipla(tipo, campi, visibilitaCampi);
			if (lastIdW9FINAVisualizzata == 2) {
				setValue("W9FINA_IMPORTO_FINANZIAMENTO_2", "${requestScope.importoComplessivoIntervento}");
			}
		}

	</c:if>

</gene:javaScript>
<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />