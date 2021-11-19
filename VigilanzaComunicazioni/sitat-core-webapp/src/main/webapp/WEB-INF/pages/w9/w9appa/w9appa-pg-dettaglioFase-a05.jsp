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

<gene:formScheda entita="W9APPA" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9APPA"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9APPA">
	
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}'></c:set>
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}'></c:set>
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />

	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>

	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>

	<c:set var="riaggiudicazione" value='${aggiudicazioneSelezionata > 1}'/>
	<c:set var="jspPath" value='w9appa-pg-dettaglioFase-a05.jsp' scope="request" />
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati economici dell'appalto </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODLOTT" visibile="false" defaultValue='${gene:getValCampo(key,"CODLOTT")}' />
		<gene:campoScheda campo="CODGARA" visibile="false" defaultValue='${gene:getValCampo(key,"CODGARA")}' />
		<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue='${aggiudicazioneSelezionata}' />
		<gene:campoScheda campo="COD_STRUMENTO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initCOD_STRUMENTO}"/>
		<gene:campoScheda campo="IMPORTO_LAVORI" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_LAVORI}"/>
		<gene:campoScheda campo="IMPORTO_SERVIZI" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_SERVIZI}"/>
		<gene:campoScheda campo="IMPORTO_FORNITURE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_FORNITURE}"/>
		<gene:campoScheda campo="IMPORTO_SUBTOTALE" modificabile="false" defaultValue="${requestScope.initIMPORTO_SUBTOTALE}">
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_LAVORI#","#W9APPA_IMPORTO_SERVIZI#","#W9APPA_IMPORTO_FORNITURE#")))' 
  				elencocampi="W9APPA_IMPORTO_LAVORI;W9APPA_IMPORTO_SERVIZI;W9APPA_IMPORTO_FORNITURE"/>
		</gene:campoScheda>
		
		<gene:campoScheda campo="IMPORTO_ATTUAZIONE_SICUREZZA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_ATTUAZIONE_SICUREZZA}"/>
		<gene:campoScheda campo="FLAG_SICUREZZA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initFLAG_SICUREZZA}"/>
		<gene:campoScheda campo="IMPORTO_PROGETTAZIONE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_PROGETTAZIONE}"/>
		<gene:campoScheda campo="IMP_NON_ASSOG" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMP_NON_ASSOG}"/>
		<gene:campoScheda campo="IMPORTO_COMPL_APPALTO" modificabile="false" defaultValue="${requestScope.initIMPORTO_COMPL_APPALTO}">
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_SUBTOTALE#","#W9APPA_IMPORTO_ATTUAZIONE_SICUREZZA#","#W9APPA_IMPORTO_PROGETTAZIONE#","#W9APPA_IMP_NON_ASSOG#")))' 
  				elencocampi="W9APPA_IMPORTO_SUBTOTALE;W9APPA_IMPORTO_ATTUAZIONE_SICUREZZA;W9APPA_IMPORTO_PROGETTAZIONE;W9APPA_IMP_NON_ASSOG"/>
		</gene:campoScheda>
		
		<gene:campoScheda campo="IVA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.iva}"/>
		<gene:campoScheda campo="IMPORTO_IVA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initIMPORTO_IVA}">
			<gene:calcoloCampoScheda 
   				funzione='toMoney(calcoloIva("#W9APPA_IMPORTO_COMPL_APPALTO#","#W9APPA_IVA#"))' 
  				elencocampi="W9APPA_IMPORTO_COMPL_APPALTO;W9APPA_IVA" />
		</gene:campoScheda>
		<gene:campoScheda campo="ALTRE_SOMME" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initALTRE_SOMME}"/>
		
		<gene:campoScheda campo="IMPORTO_DISPOSIZIONE" modificabile="false" defaultValue="${requestScope.initIMPORTO_DISPOSIZIONE}">
			<gene:calcoloCampoScheda 
   				funzione='noZero(toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_IVA#","#W9APPA_ALTRE_SOMME#"))))' 
  				elencocampi="W9APPA_IMPORTO_IVA;W9APPA_ALTRE_SOMME" />
		</gene:campoScheda>
		<gene:campoScheda campo="IMPORTO_COMPL_INTERVENTO"
			modificabile="false" defaultValue="${requestScope.initIMPORTO_COMPL_INTERVENTO}">
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9APPA_IMPORTO_COMPL_APPALTO#","#W9APPA_IMPORTO_DISPOSIZIONE#")))' 
				elencocampi="W9APPA_IMPORTO_COMPL_APPALTO;W9APPA_IMPORTO_DISPOSIZIONE;"/>
		</gene:campoScheda>
		<gene:campoScheda campo="FLAG_IMPORTI" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initFLAG_IMPORTI}"/>
		<gene:campoScheda campo="OPERE_URBANIZ_SCOMPUTO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initOPERE_URBANIZ_SCOMPUTO}"/>
		<gene:campoScheda campo="FLAG_LIVELLO_PROGETTUALE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initFLAG_LIVELLO_PROGETTUALE}"/>
		<gene:campoScheda campo="MODALITA_RIAGGIUDICAZIONE" visibile="${datiRiga.W9APPA_NUM_APPA>1}" obbligatorio="${datiRiga.W9APPA_NUM_APPA>1}"/>
		
		<gene:campoScheda>
			<td colspan="2"><b>Dati procedurali dell'appalto </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="REQUISITI_SETT_SPEC" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initREQUISITI_SETT_SPEC}" visibile="${isFlagEnteSpeciale}" />
		<gene:campoScheda campo="FLAG_ACCORDO_QUADRO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initFLAG_ACCORDO_QUADRO}" visibile="false" />
		<gene:campoScheda campo="PROCEDURA_ACC" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initPROCEDURA_ACC}"/>
		<gene:campoScheda campo="PREINFORMAZIONE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initPREINFORMAZIONE}"/>
		<gene:campoScheda campo="TERMINE_RIDOTTO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initTERMINE_RIDOTTO}"/>
		<gene:campoScheda campo="ID_MODO_INDIZIONE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initID_MODO_INDIZIONE}" visibile="${isFlagEnteSpeciale && ver_simog eq 1}" />
		<gene:campoScheda campo="VERIFICA_CAMPIONE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initVERIFICA_CAMPIONE}"/>
		<gene:campoScheda campo="FIN_REGIONALE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initFIN_REGIONALE}"/>
		<gene:campoScheda>
			<td colspan="2"><b>Inviti ed offerte / soglia di anomalia </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="DATA_MANIF_INTERESSE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initDATA_MANIF_INTERESSE}"/>
		<gene:campoScheda campo="NUM_MANIF_INTERESSE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_MANIF_INTERESSE}"/>
		<gene:campoScheda campo="DATA_SCADENZA_RICHIESTA_INVITO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initDATA_SCADENZA_RICHIESTA_INVITO}" visibile="${(idSceltaContraente eq 2) or (idSceltaContraente eq 9)}" />
		<gene:campoScheda campo="NUM_IMPRESE_RICHIEDENTI" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_IMPRESE_RICHIEDENTI}" visibile="${(idSceltaContraente eq 2) or (idSceltaContraente eq 9)}" />
		<gene:campoScheda campo="DATA_INVITO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initDATA_INVITO}" visibile="${idSceltaContraente ne 1}" />
		<gene:campoScheda campo="NUM_IMPRESE_INVITATE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_IMPRESE_INVITATE}" visibile="${idSceltaContraente ne 1}" />
		<gene:campoScheda campo="DATA_SCADENZA_PRES_OFFERTA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initDATA_SCADENZA_PRES_OFFERTA}"/>
		
		<gene:campoScheda campo="NUM_IMPRESE_OFFERENTI" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_IMPRESE_OFFERENTI}"/>
		<gene:campoScheda campo="NUM_OFFERTE_AMMESSE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_OFFERTE_AMMESSE}"/>
		<gene:campoScheda campo="NUM_OFFERTE_ESCLUSE" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_OFFERTE_ESCLUSE}"/>
		<gene:campoScheda campo="NUM_OFFERTE_FUORI_SOGLIA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_OFFERTE_FUORI_SOGLIA}"/>
		<gene:campoScheda campo="NUM_IMP_ESCL_INSUF_GIUST" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initNUM_IMP_ESCL_INSUF_GIUST}"/>
		<gene:campoScheda campo="OFFERTA_MASSIMO" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initOFFERTA_MASSIMO}">
			<gene:checkCampoScheda funzione='confrontaOfferte()' obbligatorio="true" onsubmit="false" messaggio="Offerta massima deve essere maggiore dell'offerta minima"/>
		</gene:campoScheda>
		<gene:campoScheda campo="OFFERTA_MINIMA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initOFFERTA_MINIMA}">
			<gene:checkCampoScheda funzione='confrontaOfferte()' obbligatorio="true" onsubmit="false" messaggio="Offerta minima deve essere minore dell'offerta massima"/>
		</gene:campoScheda>
		<gene:campoScheda campo="VAL_SOGLIA_ANOMALIA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initVAL_SOGLIA_ANOMALIA}"/>
		<gene:campoScheda campo="ASTA_ELETTRONICA" modificabile="${!riaggiudicazione}" defaultValue="${requestScope.initASTA_ELETTRONICA}"/>

		<gene:campoScheda>
			<td colspan="2"><b>Aggiudicazione / affidamento </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="PERC_RIBASSO_AGG" defaultValue="${requestScope.initPercRibassoAggiu}" />
		<gene:campoScheda campo="PERC_OFF_AUMENTO" defaultValue="${requestScope.initPercOffAumento}" />
		<gene:campoScheda campo="IMPORTO_AGGIUDICAZIONE"   defaultValue="${requestScope.initImportoAggiu}" />
		<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE" defaultValue="${requestScope.initDataVerbAggiu}" />

		<gene:campoScheda campo="TIPO_ATTO" defaultValue="${requestScope.initTipoAtto}" />
		<gene:campoScheda campo="DATA_ATTO" defaultValue="${requestScope.initDataAtto}" /> 
		<gene:campoScheda campo="NUMERO_ATTO" defaultValue="${requestScope.initNumeroAtto}" />
		<gene:campoScheda campo="RELAZIONE_UNICA" visibile="${ver_simog ge 6}" />
		<gene:campoScheda campo="FLAG_RICH_SUBAPPALTO" />

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
		
	<gene:fnJavaScriptScheda funzione='gestioneVisibilitaCampiDaModoIndizione("#W9APPA_ID_MODO_INDIZIONE#")' elencocampi="W9APPA_ID_MODO_INDIZIONE" esegui="true" />
		
	<gene:fnJavaScriptScheda funzione='gestioneVisibilitaCampiDaNumOfferteAmmesse("#W9APPA_NUM_OFFERTE_AMMESSE#")' elencocampi="W9APPA_NUM_OFFERTE_AMMESSE" esegui="true" />

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	
	<c:if test='${modo eq "NUOVO" and aggiudicazioneInibita eq "S"}' >
		<gene:redefineInsert name="schedaConferma" />
		<gene:redefineInsert name="pulsanteSalva" />
	</c:if>
	
</gene:formScheda>

<gene:redefineInsert name="head">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>
<gene:javaScript>
	
	function sommaCampi(valori)
	 	{
		  var i, ret=0;
		  for(i=0;i < valori.length;i++){
		   if(valori[i]!=""){
		    ret += eval(valori[i]);
		   }
		  }
		  	return eval(ret).toFixed(2);
 		}
 
 	function noZero(valore)
	 	{
	 	  if (valore.val == 0) 
	 	    return "0.00";
	 	  else
	 	    return valore;
 		}
 		
 	function calcoloIva(valore, iva) {
		var result = 0;
		if (valore != "" && iva != "") {
			result = eval(valore) * eval(iva) / 100;
		}
		return eval(result).toFixed(2);
 	}
 	
	function gestioneVisibilitaCampiDaModoIndizione(idModoIndizione) {
		var idSceltaContraente = new Number(${idSceltaContraente});
		//alert("idSceltaContraente = " + idSceltaContraente);
		//alert("Test1: " + (idSceltaContraente >= 3) + "\nTest2: " + (idModoIndizione == 1) + "\nTest3: " + (idSceltaContraente >= 3 && idModoIndizione == 1));
		
		if (idSceltaContraente == 1) {
			showObj("rowW9APPA_DATA_SCADENZA_RICHIESTA_INVITO", false);
			showObj("rowW9APPA_NUM_IMPRESE_RICHIEDENTI", false);
			showObj("rowW9APPA_DATA_INVITO", false);
			//showObj("rowW9APPA_NUM_IMPRESE_INVITATE", false);

			setValue("W9APPA_DATA_SCADENZA_RICHIESTA_INVITO", "");
			setValue("W9APPA_NUM_IMPRESE_RICHIEDENTI", "");
			setValue("W9APPA_DATA_INVITO", "");
			//setValue("W9APPA_NUM_IMPRESE_INVITATE", "");
		} else {
			showObj("rowW9APPA_DATA_SCADENZA_RICHIESTA_INVITO", true);
			showObj("rowW9APPA_NUM_IMPRESE_RICHIEDENTI", true);
			showObj("rowW9APPA_DATA_INVITO", true);
			//showObj("rowW9APPA_NUM_IMPRESE_INVITATE", true);
		}
	}
	
	function gestioneVisibilitaCampiDaNumOfferteAmmesse(numeroOfferteAmmesse) {
		if (numeroOfferteAmmesse >= 5) {
			showObj("rowW9APPA_VAL_SOGLIA_ANOMALIA", true);
			showObj("rowW9APPA_NUM_OFFERTE_FUORI_SOGLIA", true);
		} else {
			showObj("rowW9APPA_VAL_SOGLIA_ANOMALIA", false);
			showObj("rowW9APPA_NUM_OFFERTE_FUORI_SOGLIA", false);
			setValue("W9APPA_VAL_SOGLIA_ANOMALIA", "");
			setValue("W9APPA_NUM_OFFERTE_FUORI_SOGLIA", "");
		}
	}
	
	function confrontaOfferte() {
		var offertaMassimo = getValue("W9APPA_OFFERTA_MASSIMO");
		var offertaMinima = getValue("W9APPA_OFFERTA_MINIMA");
		if (new Number(offertaMassimo).valueOf() < new Number(offertaMinima).valueOf())
			return false;
		else
			return true;
	}

<c:choose>
	<c:when test="${modo eq 'NUOVO' and aggiudicazioneInibita eq 'S'}">
	$(document).ready(function() {
  	outMsg("Non &egrave; possibile creare la fase di aggiudicazione prima dell'invio dell'atto di esito dell'aggiudicazione", "ERR");
  	onOffMsg();
  	$("tr[id^='rowW9APPA']").hide("10");
  	$("tr[id^='rowCAMPO']").hide("10");
		$("tr[id='rowCAMPO_GENERICO64']").show("10");
		// Attenzione aggiungere o togliere campi alla scheda comporta la modifica di quest'ultima istruzione
	});
	</c:when>
	<c:when test="${modo eq 'NUOVO' and aggiudicazioneInibita eq 'W'}">
	$( document ).ready(function() {
  	if (confirm("Attenzione: l'atto di esito dell'aggiudicazione non esiste o non e' ancora stato inviato. Desideri proseguire comunque?")) {
  	} else {
	  	$("tr[id^='rowW9APPA']").hide("5");
	  	$("tr[id^='rowCAMPO']").hide("5");
	  	$("tr[id='rowCAMPO_GENERICO64']").show("5");
			// Attenzione aggiungere o togliere campi alla scheda comporta la modifica di quest'ultima istruzione
	  	$("a[title^='Salva']").hide("5");
	  	$("input[title^='Salva']").hide("5");  		
  	}
	});
	
	</c:when>
</c:choose>

</gene:javaScript>
<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />