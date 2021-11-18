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
		<gene:campoScheda campo="CODGARA" visibile="false" defaultValue='${gene:getValCampo(key,"CODGARA")}' value='${gene:getValCampo(key,"CODGARA")}'/>
		<gene:campoScheda campo="CODLOTT" visibile="false" defaultValue='${gene:getValCampo(key,"CODLOTT")}' value='${gene:getValCampo(key,"CODLOTT")}' />
		<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
		<gene:campoScheda campo="NUM_INIZ" visibile="false" defaultValue='${gene:getValCampo(key,"NUM_INIZ")}' value='${gene:getValCampo(key,"NUM_INIZ")}'/>
		<gene:campoScheda campo="DATA_STIPULA" defaultValue="${requestScope.dataStipulaContratto}" />
		<gene:campoScheda campo="DATA_ESECUTIVITA" />
		<gene:campoScheda campo="IMPORTO_CAUZIONE" />
		<gene:campoScheda campo="INCONTRI_VIGIL" visibile="${requestScope.visualizzaIncontriVigil eq 'TRUE'}"/>
		<gene:campoScheda>
			<td colspan="2"><b>Termine di esecuzione </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="DATA_INI_PROG_ESEC" />
		<gene:campoScheda campo="DATA_APP_PROG_ESEC" />
		<gene:campoScheda campo="FLAG_FRAZIONATA" />
		<gene:campoScheda campo="DATA_VERBALE_CONS" />
		<gene:campoScheda campo="DATA_VERBALE_DEF" />
		<gene:campoScheda campo="FLAG_RISERVA" />
		<gene:campoScheda campo="DATA_VERB_INIZIO" defaultValue="${requestScope.dataEffettivoInizioLavori}" />
		<gene:campoScheda campo="DATA_TERMINE" />
	</gene:gruppoCampi>
	
	<gene:gruppoCampi idProtezioni="campiPubblicazioneEsito" >
		<gene:campoScheda>
			<td colspan="2"><b>Pubblicazione esito di gara </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODGARA" entita="W9PUES" visibile="false" value="${codgara}"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ" />
		<gene:campoScheda campo="CODLOTT" entita="W9PUES" visibile="false" value="${codlott}"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ" />
		<gene:campoScheda campo="NUM_INIZ" entita="W9PUES" visibile="false" 
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ" />
		<gene:campoScheda campo="NUM_PUES" entita="W9PUES" visibile="false" 
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ" />

		<gene:campoScheda campo="DATA_GUCE" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizDATAGUCE}" />
		<gene:campoScheda campo="DATA_GURI" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizDATAGURI}" />
		<gene:campoScheda campo="DATA_ALBO" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizDATAALBO}" />
		<gene:campoScheda campo="QUOTIDIANI_NAZ" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizQUOTIDNAZ}" />
		<gene:campoScheda campo="QUOTIDIANI_REG" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizQUOTIDREG}" />
		<gene:campoScheda campo="PROFILO_COMMITTENTE" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizPROFCOMMIT}" />
		<gene:campoScheda campo="SITO_MINISTERO_INF_TRASP" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizINFTRASP}" />
		<gene:campoScheda campo="SITO_OSSERVATORIO_CP" entita="W9PUES"
			where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ"
			defaultValue="${requestScope.inizSITOOSS}" />
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
<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />