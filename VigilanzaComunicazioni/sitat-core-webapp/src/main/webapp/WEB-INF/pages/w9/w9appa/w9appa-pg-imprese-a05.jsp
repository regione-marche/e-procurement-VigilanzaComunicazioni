
<%
	/*
	 * Created on: 04/08/2009
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
<gene:formScheda entita="W9APPA" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA"
	where='W9APPA.CODGARA = #W9APPA.CODGARA# AND W9APPA.CODLOTT = #W9APPA.CODLOTT# AND W9APPA.NUM_APPA = #W9APPA.NUM_APPA#'>
	
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<c:set var="num" value='${gene:getValCampo(key,"NUM_APPA")}' />
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<gene:campoScheda campo="CODLOTT" visibile="false" />
	<gene:campoScheda campo="CODGARA" visibile="false" />
	<gene:campoScheda campo="NUM_APPA" visibile="false" />

	<gene:campoScheda entita="W9AGGI" campo="CODGARA"
		where="W9AGGI.CODGARA = ${codgara} and W9AGGI.CODLOTT = ${codlott} and W9AGGI.NUM_APPA = ${num}"
		visibile="false" defaultValue="${datiRiga.W9APPA_CODGARA}" />
	<gene:campoScheda entita="W9AGGI" campo="CODLOTT"
		where="W9AGGI.CODGARA = ${codgara} and W9AGGI.CODLOTT = ${codlott} and W9AGGI.NUM_APPA = ${num}"
		visibile="false" defaultValue="${datiRiga.W9APPA_CODLOTT}" />
	<gene:campoScheda entita="W9AGGI" campo="NUM_APPA"
		where="W9AGGI.CODGARA = ${codgara} and W9AGGI.CODLOTT = ${codlott} and W9AGGI.NUM_APPA = ${num}"
		visibile="false" defaultValue="${datiRiga.W9APPA_NUM}" />

	<!-- scheda multipla -->
	<c:if test='${modo ne "NUOVO"}'>
		<gene:callFunction
			obj="it.eldasoft.w9.tags.funzioni.GetImpreseAggiudicatarieFunction"
			parametro="${key}" />
	</c:if>

	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
		<jsp:param name="entita" value='W9AGGI' />
		<jsp:param name="chiave"
			value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};${gene:getValCampo(key, "NUM_APPA")}' />
		<jsp:param name="nomeAttributoLista" value='listaImpreseAggiudicatarie' />
		<jsp:param name="idProtezioni" value="W9AGGI" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9appa/imprese_aggiudicatarie.jsp" />
		<jsp:param name="arrayCampi" value="'W9AGGI_CODGARA_','W9AGGI_CODLOTT_','W9AGGI_NUM_APPA_','W9AGGI_NUM_AGGI_','IMPR_NOMEST_','W9AGGI_CODIMP_','W9AGGI_ID_TIPOAGG_','W9AGGI_RUOLO_','W9AGGI_FLAG_AVVALIMENTO_','IMPR_NOMEST_AUSILIARIA_','W9AGGI_CODIMP_AUSILIARIA_'"/>
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,true,true,true,false,true,false,false" />
		<jsp:param name="usaContatoreLista" value="true" />
		<jsp:param name="sezioneListaVuota" value="true" />
		<jsp:param name="titoloSezione" value="Imprese aggiudicatarie / affidatarie" />
		<jsp:param name="titoloNuovaSezione" value="Nuova impresa aggiudicataria / affidataria" />
		<jsp:param name="descEntitaVociLink" value="Imprese aggiudicatarie / affidatarie" />
		<jsp:param name="msgRaggiuntoMax"  value="e Imprese aggiudicatarie / affidatarie" />
	</jsp:include>
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

