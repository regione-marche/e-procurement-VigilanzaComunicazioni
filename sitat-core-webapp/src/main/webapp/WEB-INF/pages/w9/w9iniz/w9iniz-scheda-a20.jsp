<%
/*
 * Created on: 07/04/2011
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

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9INIZ-scheda">
	
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />

	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, "Nuova Fase di Stipula accordo quadro", "Fase di Stipula accordo quadro")}' />

	<c:set var="flusso" value='11' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='${gene:getValCampo(key,"NUM_INIZ")}' scope="request" />
	
	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9INIZ-A20")}' scope="request" />
	
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Dati generali" idProtezioni="GEN">
		
				<gene:formScheda entita="W9INIZ" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9INIZ"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9INIZ" >
					
					<gene:redefineInsert name="schedaNuovo" />
					<gene:redefineInsert name="pulsanteNuovo" />
			
					<c:if test='${permessoUser lt 4}' >
						<gene:redefineInsert name="schedaModifica" />
						<gene:redefineInsert name="pulsanteModifica" />
					</c:if>
			
					<gene:gruppoCampi idProtezioni="GEN">
						<gene:campoScheda campo="CODGARA" visibile="false" value='${gene:getValCampo(key,"CODGARA")}' defaultValue="${codgara}" />
						<gene:campoScheda campo="CODLOTT" visibile="false" value='${gene:getValCampo(key,"CODLOTT")}' defaultValue="${codlott}" />
						<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
						<gene:campoScheda campo="NUM_INIZ" visibile="false" value='${gene:getValCampo(key,"NUM_INIZ")}' />
		
						<gene:campoScheda>
							<td colspan="2"><b>Pubblicazione esito procedura di selezione</b></td>
						</gene:campoScheda>
						<gene:campoScheda campo="CODGARA" visibile="false" entita="W9PUES" value="${codgara}"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="CODLOTT" visibile="false" entita="W9PUES" value="${codlott}"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="NUM_INIZ" visibile="false" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						
						<gene:campoScheda campo="NUM_PUES" entita="W9PUES" visibile="false" value="1"
							where="W9PUES.CODGARA = W9INIZ.CODGARA AND W9PUES.CODLOTT = W9INIZ.CODLOTT AND W9PUES.NUM_INIZ = W9INIZ.NUM_INIZ" />
						
						<gene:campoScheda campo="DATA_GUCE" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="DATA_GURI" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="DATA_ALBO" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="QUOTIDIANI_NAZ" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="QUOTIDIANI_REG" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="PROFILO_COMMITTENTE" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="SITO_MINISTERO_INF_TRASP" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
						<gene:campoScheda campo="SITO_OSSERVATORIO_CP" entita="W9PUES"
							where="W9PUES.CODGARA=W9INIZ.CODGARA AND W9PUES.CODLOTT=W9INIZ.CODLOTT AND W9PUES.NUM_INIZ=W9INIZ.NUM_INIZ" />
				
						<gene:campoScheda>
							<td colspan="2"><b>Accordo quadro/convenzione</b></td>
						</gene:campoScheda>
						<gene:campoScheda campo="DATA_STIPULA" title="Data stipula accordo quadro/convenzione" />
						<gene:campoScheda>
							<td colspan="2"><b>Termine di esecuzione </b></td>
						</gene:campoScheda>
						<gene:campoScheda campo="DATA_DECORRENZA" />
						<gene:campoScheda campo="DATA_SCADENZA" />
				
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
					<gene:redefineInsert name="addToAzioni">
						<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
					</gene:redefineInsert>
				</gene:formScheda>
			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>
	<gene:javaScript>   
		
		function popupValidazione(flusso,key1,key2,key3) {
	  	var comando = "href=w9/commons/popup-validazione-generale.jsp";
	  	comando = comando + "&flusso=" + flusso;
	  	comando = comando + "&key1=" + key1;
	  	comando = comando + "&key2=" + key2;
	  	comando = comando + "&key3=" + key3;
	 		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	  }
	
	 	<c:if test='${modo eq "MODIFICA"}' >
			<c:set var="localkey" value='${codgara};${codlott};${flusso};${key3}' scope="request" />
			<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
		</c:if>
	</gene:javaScript>
	<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />
</gene:template>