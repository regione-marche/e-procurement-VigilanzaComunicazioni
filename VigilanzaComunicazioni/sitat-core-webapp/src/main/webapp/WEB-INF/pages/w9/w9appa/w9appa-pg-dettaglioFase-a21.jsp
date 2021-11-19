<%
	/*
	 * Created on: 12/04/2011
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
<c:set var="key" value='${key}' scope="request" />
<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}'></c:set>
<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}'></c:set>
<%
	/* Dati generali del lavoro */
%>
<gene:formScheda entita="W9APPA" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9APPA"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA">
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<gene:redefineInsert name="addToAzioni">
	<c:if test="${not gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIISIMOG')}">
		<tr>
			<c:choose>
		    <c:when test='${isNavigazioneDisattiva ne "1"}'>
		      <td class="vocemenulaterale">
						<a href="javascript:aggiornaDaAccordoQuadro();" title="Aggiorna da accordo quadro" tabindex="1510">
							Aggiorna da accordo quadro
						</a>
				  </td>
				</c:when>
				<c:otherwise>
					<td>
					  Aggiorna da accordo quadro
				  </td>
			  </c:otherwise>
			</c:choose>
		</tr>
	</c:if>
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>
	
	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	<c:if test='${modo eq "NUOVO" and (! empty requestScope.erroreInitAccordoQuadro or aggiudicazioneInibita eq "S")}' >
		<gene:redefineInsert name="pulsanteSalva" />
		<gene:redefineInsert name="schedaConferma" />
	</c:if>
	
	<c:set var="jspPath" value='w9appa-pg-dettaglioFase-a21.jsp' scope="request" />
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati economici dell'adesione </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODLOTT" visibile="false" defaultValue='${gene:getValCampo(key,"CODLOTT")}' />
		<gene:campoScheda campo="CODGARA" visibile="false" defaultValue='${gene:getValCampo(key,"CODGARA")}' />
		<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue='${aggiudicazioneSelezionata}' />
		<gene:campoScheda campo="COD_STRUMENTO" />
		<gene:campoScheda campo="IMPORTO_LAVORI" />
		<gene:campoScheda campo="IMPORTO_SERVIZI" />
		<gene:campoScheda campo="IMPORTO_FORNITURE" />
		<gene:campoScheda campo="IMPORTO_SUBTOTALE" modificabile="false" >
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_LAVORI#","#W9APPA_IMPORTO_SERVIZI#","#W9APPA_IMPORTO_FORNITURE#")))' 
  				elencocampi="W9APPA_IMPORTO_LAVORI;W9APPA_IMPORTO_SERVIZI;W9APPA_IMPORTO_FORNITURE"/>
		</gene:campoScheda>

		<gene:campoScheda campo="IMPORTO_ATTUAZIONE_SICUREZZA" />
		<gene:campoScheda campo="IMPORTO_PROGETTAZIONE" />
		<gene:campoScheda campo="IMP_NON_ASSOG" />

		<gene:campoScheda campo="IMPORTO_COMPL_APPALTO" modificabile="false" visibile="false">
			<gene:calcoloCampoScheda
					funzione='toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_SUBTOTALE#","#W9APPA_IMPORTO_ATTUAZIONE_SICUREZZA#","#W9APPA_IMPORTO_PROGETTAZIONE#","#W9APPA_IMP_NON_ASSOG#")))' 
					elencocampi="W9APPA_IMPORTO_SUBTOTALE;W9APPA_IMPORTO_ATTUAZIONE_SICUREZZA;W9APPA_IMPORTO_PROGETTAZIONE;W9APPA_IMP_NON_ASSOG"/>
		</gene:campoScheda>
		<gene:campoScheda campo="IMPORTO_COMPL_INTERVENTO" modificabile="false" visibile="false">
			<gene:calcoloCampoScheda funzione='toMoney("#W9APPA_IMPORTO_COMPL_APPALTO#")'	elencocampi="W9APPA_IMPORTO_COMPL_APPALTO"/>
		</gene:campoScheda>	
		
		<gene:campoScheda>
			<td colspan="2"><b>Aggiudicazione / affidamento </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="PERC_RIBASSO_AGG" defaultValue="${sessionScope.AGGIUDICAZIONE_ACC_QUADRO.PERC_RIBASSO_AGG}" />
		<gene:campoScheda campo="PERC_OFF_AUMENTO" defaultValue="${sessionScope.AGGIUDICAZIONE_ACC_QUADRO.PERC_OFF_AUMENTO}" />
<c:choose>
	<c:when test='${! empty sessionScope.AGGIUDICAZIONE_ACC_QUADRO}'>
		<gene:campoScheda campo="IMPORTO_AGGIUDICAZIONE"   defaultValue="${sessionScope.AGGIUDICAZIONE_ACC_QUADRO.IMPORTO_AGGIUDICAZIONE}" />
		<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE" defaultValue="${sessionScope.AGGIUDICAZIONE_ACC_QUADRO.DATA_VERB_AGGIUDICAZIONE}" />
	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="IMPORTO_AGGIUDICAZIONE"   defaultValue="${requestScope.initImportoAggiu}" />
		<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE" defaultValue="${requestScope.initDataVerbAggiu}" />
	</c:otherwise>
</c:choose>
		
		<gene:campoScheda campo="FLAG_RICH_SUBAPPALTO" defaultValue="${sessionScope.AGGIUDICAZIONE_ACC_QUADRO.FLAG_RICH_SUBAPPALTO}" />
		
		<gene:campoScheda campo="TIPO_ATTO" />
		<gene:campoScheda campo="DATA_ATTO" />
		<gene:campoScheda campo="NUMERO_ATTO" />
	</gene:gruppoCampi>
	
	<!-- campi per  W9FASI-->
	<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
		where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND W9FASI.NUM = W9APPA.NUM_APPA AND W9FASI.NUM_APPA = W9APPA.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
		where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND W9FASI.NUM = W9APPA.NUM_APPA AND W9FASI.NUM_APPA = W9APPA.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
		where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND W9FASI.NUM = W9APPA.NUM_APPA AND W9FASI.NUM_APPA = W9APPA.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
		where="W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND W9FASI.NUM = W9APPA.NUM_APPA AND W9FASI.NUM_APPA = W9APPA.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
		
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>

<gene:javaScript>
	function sommaCampi(valori)	{
		var i, ret=0;
		for(i=0;i < valori.length;i++){
			if(valori[i]!=""){
		    ret += eval(valori[i]);
		  }
		}
	  return eval(ret).toFixed(2);
 	}

	<c:if test='${modo eq "NUOVO" and ! empty requestScope.erroreInitAccordoQuadro}' >
 		$( document ).ready(function() {
			$("tr[id^='rowW9APPA']").hide("5");
	  	$("tr[id^='rowCAMPO']").hide("5");
	  	$("tr[id='rowCAMPO_GENERICO28']").show("5");
	  	$("a[title^='Salva']").hide("5");
	  	$("input[title^='Salva']").hide("5");
 		
    	// Visualizzazione del messaggio di errore
    	var msg = "${requestScope.erroreInitAccordoQuadro}";
    	outMsg(msg,"ERR");
		});
 	</c:if>
 	
 	function aggiornaDaAccordoQuadro() {
 		var codGara = getValue("W9APPA_CODGARA");
 		var codLott = getValue("W9APPA_CODLOTT");
 		var numAppa = getValue("W9APPA_NUM_APPA");
		openPopUpCustom('href=w9/w9appa/aggiornaDaAccordoQuadroPopup.jsp&codGara='+codGara+'&codLott='+codLott+'&numAppa='+numAppa, 'formAggiornaDaAQ', 545, 280, 1, 1);
 	}

<c:choose>
	<c:when test="${modo eq 'NUOVO' and aggiudicazioneInibita eq 'S'and empty requestScope.erroreInitAccordoQuadro}">
	$(document).ready(function() {
  	outMsg("Non &egrave; possibile creare la fase di adesione accordo quadro prima dell'invio dell'atto 'Provvedimento di adesione'", "ERR");
  	onOffMsg();
  	$("tr[id^='rowW9APPA']").hide("10");
  	$("tr[id^='rowCAMPO']").hide("10");
		$("tr[id='rowCAMPO_GENERICO28']").show("10");
	});
	</c:when>
	<c:when test="${modo eq 'NUOVO' and aggiudicazioneInibita eq 'W'}">
	var confermaCreazioneSchedaNonInizializzata = "${modo eq 'NUOVO' and (! empty requestScope.erroreInitAccordoQuadro)}";

	$( document).ready(function() {
		if (confermaCreazioneSchedaNonInizializzata) {
			if (confirm("ATTENZIONE: i servizi SIMOG sono momentaneamente non disponibili, e non e' stato possibile verificare la correttezza del CIG accordo quadro ed importarne i dati. Premere 'OK' per creare comunque la scheda, 'Annulla' per riprovare in un secondo momento")) {
				a1();
			}
		} else {
			a1();
		}
	});
	
	function a1() {
		if (confirm("Attenzione: il 'Provvedimento di adesione' non esiste o non e'ancora stato inviato. Desideri proseguire comunque?")) {
  	} else {
	  	$("tr[id^='rowW9APPA']").hide("5");
	  	$("tr[id^='rowCAMPO']").hide("5");
	  	$("tr[id='rowCAMPO_GENERICO28']").show("5");
	  	$("a[title^='Salva']").hide("5");
	  	$("input[title^='Salva']").hide("5");  		
  	}	
	}
 	</c:when>
</c:choose>
 	
 	
</gene:javaScript>