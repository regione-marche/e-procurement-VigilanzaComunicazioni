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
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9CONC-scheda">
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="flusso" value='4' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='${gene:getValCampo(key,"NUM_CONC")}' scope="request" />

	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9CONC-A09")}' scope="request" />

	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, "Nuova Fase di conclusione del contratto", "Fase di conclusione del contratto")}' />

	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9CONC" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9CONC"
					gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9CONC">
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
			<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>
			
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda>
					<td colspan="2"><b>Interruzione anticipata del procedimento</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="CODGARA" visibile="false" value="${codgara}" defaultValue="${codgara}" />
				<gene:campoScheda campo="CODLOTT" visibile="false" value="${codlott}" defaultValue="${codlott}" />
				<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
				<gene:campoScheda campo="NUM_CONC" modificabile="false" visibile="false" />
				<gene:campoScheda campo="INTANTIC" modificabile="${empty requestScope.interruzioneAnticipata}" defaultValue="${requestScope.interruzioneAnticipata}" />
				<gene:campoScheda campo="ID_MOTIVO_INTERR" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreCampoW9ConcIdMotivoInterr" />
				<gene:campoScheda campo="ID_MOTIVO_RISOL" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreCampoW9ConcIdMotivoRisol" />
				<gene:campoScheda campo="DATA_RISOLUZIONE" />
				<gene:campoScheda campo="FLAG_ONERI" />
				<gene:campoScheda campo="ONERI_RISOLUZIONE" />
				<gene:campoScheda campo="FLAG_POLIZZA" />
			</gene:gruppoCampi>
			
			<gene:gruppoCampi idProtezioni="ULTLAV" visibile="${empty requestScope.interruzioneAnticipata}" >
				<gene:campoScheda>
					<td colspan="2"><b>Ultimazione lavori</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="DATA_VERBALE_CONSEGNA" defaultValue="${requestScope.dataVerbaleDef}" />
				<gene:campoScheda campo="TERMINE_CONTRATTO_ULT" defaultValue="${requestScope.dataTermine}" />
				<gene:campoScheda campo="DATA_ULTIMAZIONE" />
				<gene:campoScheda campo="NUM_INFORTUNI"  />
				<gene:campoScheda campo="NUM_INF_PERM" />
				<gene:campoScheda campo="NUM_INF_MORT" />
				<gene:campoScheda campo="NUM_GIORNI_PROROGA" defaultValue="${requestScope.sommaGiorniProroga}" />
				<gene:campoScheda campo="ORE_LAVORATE" />
			</gene:gruppoCampi>
			
			<!-- campi per  W9FASI-->
			<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
				where="W9FASI.CODGARA = W9CONC.CODGARA AND W9FASI.CODLOTT = W9CONC.CODLOTT AND W9FASI.NUM = W9CONC.NUM_CONC AND W9FASI.NUM_APPA = W9CONC.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
				where="W9FASI.CODGARA = W9CONC.CODGARA AND W9FASI.CODLOTT = W9CONC.CODLOTT AND W9FASI.NUM = W9CONC.NUM_CONC AND W9FASI.NUM_APPA = W9CONC.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
				where="W9FASI.CODGARA = W9CONC.CODGARA AND W9FASI.CODLOTT = W9CONC.CODLOTT AND W9FASI.NUM = W9CONC.NUM_CONC AND W9FASI.NUM_APPA = W9CONC.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9CONC.CODGARA AND W9FASI.CODLOTT = W9CONC.CODLOTT AND W9FASI.NUM = W9CONC.NUM_CONC AND W9FASI.NUM_APPA = W9CONC.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	
			<gene:fnJavaScriptScheda funzione='bloccaConcInt("#W9CONC_INTANTIC#")'
				elencocampi="W9CONC_INTANTIC" esegui="true" />
			<gene:fnJavaScriptScheda funzione='bloccaConcMotiv("#W9CONC_INTANTIC#", "#W9CONC_ID_MOTIVO_INTERR#")'
				elencocampi="W9CONC_ID_MOTIVO_INTERR" esegui="true" />
			<gene:fnJavaScriptScheda funzione='bloccaConcOner("#W9CONC_INTANTIC#", "#W9CONC_FLAG_ONERI#")'
				elencocampi="W9CONC_FLAG_ONERI" esegui="true" />
				
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>

			<gene:fnJavaScriptScheda funzione="setInfortuni()" elencocampi="W9CONC_NUM_INFORTUNI" esegui="false" />
			
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
  
 	function bloccaConcInt(intantic) {
		var visIntanticPolizza = (intantic==1);
		
		showObj("rowW9CONC_ID_MOTIVO_INTERR",visIntanticPolizza);
		showObj("rowW9CONC_FLAG_POLIZZA",visIntanticPolizza); 
		
		if (!visIntanticPolizza) {
			setValue("W9CONC_ID_MOTIVO_INTERR","");
			setValue("W9CONC_FLAG_POLIZZA","");
		}
	}
	
	function bloccaConcMotiv(intantic, idMotivoInterr) {
		var visInterrDataR = (intantic==1 && idMotivoInterr==2);
		var visFlagOneri = (idMotivoInterr==2 || idMotivoInterr==4 || idMotivoInterr==5 || idMotivoInterr==6);
		
		if((intantic==1 && idMotivoInterr==2)) {
			showObj("rowW9CONC_ID_MOTIVO_RISOL", true);
		} else {
			showObj("rowW9CONC_ID_MOTIVO_RISOL", false);
			setValue("W9CONC_ID_MOTIVO_RISOL","");
		}
		
		if(idMotivoInterr != "") {
			showObj("rowW9CONC_DATA_RISOLUZIONE", true);
		} else {
			setValue("W9CONC_DATA_RISOLUZIONE","");
			showObj("rowW9CONC_DATA_RISOLUZIONE", false);
		}
		
		showObj("rowW9CONC_FLAG_ONERI",visFlagOneri);
		if (!visFlagOneri) {
			setValue("W9CONC_FLAG_ONERI","");
		}
	}
	
	function bloccaConcOner(intantic, flagOneri) {
		var visImporto = (intantic==1 && flagOneri > 0);
		showObj("rowW9CONC_ONERI_RISOLUZIONE",visImporto);
		
		if (!visImporto) {
			setValue("W9CONC_ONERI_RISOLUZIONE","");
		}
	}
	
	function setInfortuni() {
		var numInfortuni = getValue("W9CONC_NUM_INFORTUNI");
		//alert("numInfortuni = " + numInfortuni);
		if ("0" == numInfortuni) {
			setValue("W9CONC_NUM_INF_PERM", 0);
			setValue("W9CONC_NUM_INF_MORT", 0);
		}
	}
	
	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${codgara};${codlott};${flusso};${key3}' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>
	
	</gene:javaScript>
</gene:template>