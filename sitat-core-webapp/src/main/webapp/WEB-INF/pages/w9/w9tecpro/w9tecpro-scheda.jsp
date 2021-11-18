
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"
	schema="W9" idMaschera="W9TECPRO-scheda">
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<c:set var="flusso" value='997' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='${gene:getValCampo(key,"NUM_TPRO")}'	scope="request" />
	
	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9TECPRO")}' scope="request" />
	
	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, "Nuovo Esito negativo idoneit&agrave; tecnico professionale", "Esito negativo idoneit&agrave; tecnico professionale")}' />
	
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9TECPRO" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9TECPRO">
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda>
					<td colspan="2"><b>Dati generali </b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="CODGARA" visibile="false"
					value="${codgara}" />
				<gene:campoScheda campo="CODLOTT" visibile="false"
					value="${codlott}" />
				<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
				<gene:campoScheda campo="NUM_TPRO" modificabile="false"
					visibile="${modoAperturaScheda ne 'NUOVO'}" />
				<gene:campoScheda campo="DESCARE" />
				<gene:archivio titolo="Impresa" obbligatorio="true"
					lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9TECPRO.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
					scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
					schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
					campi="IMPR.CODIMP;IMPR.NOMEST" chiave="W9TECPRO_CODIMP" 
					where="(IMPR.CODIMP in (select CODIMP from W9AGGI where CODGARA=${codgara} and CODLOTT=${codlott} and NUM_APPA=${aggiudicazioneSelezionata}) or IMPR.CODIMP in (select CODIMP from W9SUBA where CODGARA=${codgara} and CODLOTT=${codlott} and NUM_APPA=${aggiudicazioneSelezionata}))"
					formName="formImprese">
					<gene:campoScheda campo="CODIMP" visibile="false" />
					<gene:campoScheda campo="NOMEST" entita="IMPR" where="IMPR.CODIMP=W9TECPRO.CODIMP" title="Denom. Impresa" definizione="T61" />
				</gene:archivio>
			</gene:gruppoCampi>
			<gene:campoScheda campo="DATAUNI" />
			<gene:campoScheda campo="INIDO1" />
			<gene:campoScheda campo="INIDO2" />
			<gene:campoScheda campo="INIDO3" />
			<gene:campoScheda campo="INIDO4" />
			<gene:campoScheda campo="INIDO5" />
			<gene:campoScheda campo="INIDO6" />
			<gene:campoScheda campo="INIDO7" />
		<!-- campi per  W9FASI-->
			<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
				where="W9FASI.CODGARA = W9TECPRO.CODGARA AND W9FASI.CODLOTT = W9TECPRO.CODLOTT AND W9FASI.NUM = W9TECPRO.NUM_TPRO AND W9FASI.NUM_APPA = W9TECPRO.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
				where="W9FASI.CODGARA = W9TECPRO.CODGARA AND W9FASI.CODLOTT = W9TECPRO.CODLOTT AND W9FASI.NUM = W9TECPRO.NUM_TPRO AND W9FASI.NUM_APPA = W9TECPRO.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
				where="W9FASI.CODGARA = W9TECPRO.CODGARA AND W9FASI.CODLOTT = W9TECPRO.CODLOTT AND W9FASI.NUM = W9TECPRO.NUM_TPRO AND W9FASI.NUM_APPA = W9TECPRO.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
			<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
			<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9TECPRO.CODGARA AND W9FASI.CODLOTT = W9TECPRO.CODLOTT AND W9FASI.NUM = W9TECPRO.NUM_TPRO AND W9FASI.NUM_APPA = W9TECPRO.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
				
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
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
</gene:template>