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

<gene:formScheda entita="W9INIZ" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9INIZ"
		gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9INIZ" >
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />

	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<c:set var="key" value='${key}' scope="request" />
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Contratto di appalto </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODGARA" visibile="false" value='${gene:getValCampo(key,"CODGARA")}' defaultValue='${gene:getValCampo(key,"CODGARA")}' />
		<gene:campoScheda campo="CODLOTT" visibile="false" value='${gene:getValCampo(key,"CODLOTT")}' defaultValue='${gene:getValCampo(key,"CODLOTT")}' />
		<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
		<gene:campoScheda campo="NUM_INIZ" visibile="false" value='${gene:getValCampo(key,"NUM_INIZ")}' defaultValue='${gene:getValCampo(key,"NUM_INIZ")}' />
		<gene:campoScheda campo="DATA_STIPULA" />
		<gene:campoScheda>
			<td colspan="2"><b>Termine di esecuzione </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="FLAG_RISERVA" />
		<gene:campoScheda campo="DATA_VERB_INIZIO" defaultValue="${requestScope.dataEffettivoInizioLavori}" />
		<gene:campoScheda campo="DATA_TERMINE" />
	</gene:gruppoCampi>
	
	<!-- campi per  W9FASI-->
	<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
				where="W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ AND W9FASI.NUM_APPA = W9INIZ.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
				where="W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ AND W9FASI.NUM_APPA = W9INIZ.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
				where="W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ AND W9FASI.NUM_APPA = W9INIZ.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ AND W9FASI.NUM_APPA = W9INIZ.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>
</gene:javaScript>