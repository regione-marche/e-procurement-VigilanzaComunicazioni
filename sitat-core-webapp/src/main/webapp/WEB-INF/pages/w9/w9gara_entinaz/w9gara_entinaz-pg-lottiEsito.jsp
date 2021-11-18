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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%/* Dati generali del lavoro */%>
<gene:formScheda entita="W9GARA_ENTINAZ" gestisciProtezioni="true"
		gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARAENTINAZ" >

	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />

	<gene:campoScheda campo="CODGARA" visibile="false" />
	
	<!-- scheda multipla -->
	<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetLottiEsitiEntinazFunction" parametro="${key}" />
	
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W9LOTT_ENTINAZ'/>
		<jsp:param name="chiave" value='${gene:getValCampo(key, "CODGARA")}'/>
		<jsp:param name="nomeAttributoLista" value='listaLottiEsiti' />
		<jsp:param name="idProtezioni" value="W9LOTT_ENTINAZ" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9gara_entinaz/w9lott_entinaz-lottiEsito.jsp" />
		<jsp:param name="arrayCampi" value="'W9LOTT_ENTINAZ_CODLOTT_','W9LOTT_ENTINAZ_CIG_','W9LOTT_ENTINAZ_OGGETTO_','W9LOTT_ENTINAZ_IMPORTO_TOT_','W9LOTT_ENTINAZ_CPV_','TABCPV_CPVDESC_','CPVDESC_','W9LOTT_ENTINAZ_ID_CATEGORIA_PREVALENTE_','W9LOTT_ENTINAZ_CLASCAT_','W9LOTT_ENTINAZ_IMP_AGG_','W9LOTT_ENTINAZ_IMPORTO_AGGIUDICAZIONE_','W9LOTT_ENTINAZ_DATA_VERB_AGGIUDICAZIONE_','W9LOTT_ENTINAZ_FILE_ALLEGATO_', 'VISUALIZZAFILEALLEGATO_','NOMEFILEALLEGATO_', 'W9LOTT_ENTINAZ_FILEDAALLEGARE_'" />
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="sezioneListaVuota" value="true"/>
		<jsp:param name="titoloSezione" value="Lotto ed esito" />
		<jsp:param name="titoloNuovaSezione" value="Nuovo lotto ed esito" />
		<jsp:param name="descEntitaVociLink" value=" lotto ed esito" />
		<jsp:param name="msgRaggiuntoMax" value="e lotti ed esiti" />
	</jsp:include>
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>

<c:if test="${modo eq 'MODIFICA'}">
	document.forms[0].encoding="multipart/form-data";

	var tmpSchedaConferma = schedaConferma;
	var tmpSchedaAnnulla = schedaAnnulla;
	
	var schedaConferma = function() {
		// per ciascuna sezione dinamica visibile nella pagina si controlla che,
		// se il campo TITOLO e' valorizzato, allora anche il campo di tipo input file lo sia
		var numeroSezioni = eval(getValue("NUMERO_W9DOCGARA"));
		var continua = true;
		for(var er=1; er <= numeroSezioni && continua; er++){
			if(isObjShow("rowW9DOCGARA_TITOLO_" + er)){
				if(getValue("W9DOCGARA_TITOLO_" + er) != "") {
					if(document.getElementById("selFile[" + er + "]") != null && getValue("selFile[" + er + "]") == ""){
						alert("I campi 'Nome file' sono obbligatori");
						continua = false;
					}
				}
			}
		}

		if(continua) {
			document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
			tmpSchedaConferma();
		}
	}
	
	var schedaAnnulla = function() {
		document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
		tmpSchedaAnnulla();
	}

<c:forEach items="${listaLottiEsiti}" var="item" varStatus="status">
	var linksetJsPopUpVISUALIZZAFILEALLEGATO_${status.count} = "";

	linksetJsPopUpVISUALIZZAFILEALLEGATO_${status.count}+=creaVocePopUpChiusura("${pageContext.request.contextPath}/");
	linksetJsPopUpVISUALIZZAFILEALLEGATO_${status.count}+=creaPopUpSubmenu("javascript:eliminaFile(${status.count});hideMenuPopup();",0,"&nbsp;Elimina");

	<!-- listaLottiEsiti[0][1] = '${listaLottiEsiti[0][1]}' -->
	<!-- listaLottiEsiti[0][10] = '${listaLottiEsiti[0][10]}' -->
	<!-- listaLottiEsiti[0][12] = '${listaLottiEsiti[0][12]}' -->
	<!--  item[12] = '${item[12].value}' , item[12] is empty = '${empty item[12].value}' , items[12] eq '1' = '${item[12].value eq "1"}' -->
<c:choose>
	<c:when test="${empty item[12].value}">
		showObj("rowVISUALIZZAFILEALLEGATO_${status.count}", false);
	</c:when>
	<c:when test="${empty item[12].value eq '1'}" >
		showObj("rowVISUALIZZAFILEALLEGATO_${status.count}", true);
		showObj("rowNOMEFILEALLEGATO_${status.count}", false);
	</c:when>
</c:choose>

</c:forEach>

<c:forEach begin="${fn:length(listaLottiEsiti) + 1}" end="${param.contatore + fn:length(listaLottiEsiti)}" var="index1" >
	var linksetJsPopUpVISUALIZZAFILEALLEGATO_${index1} = "";
	showObj("rowNOMEFILEALLEGATO_${index1}", ${(modoAperturaScheda ne "VISUALIZZA") && (empty listaLottiEsiti[index1][12])});
		
	linksetJsPopUpVISUALIZZAFILEALLEGATO_${index1}+=creaVocePopUpChiusura("${pageContext.request.contextPath}/");
	linksetJsPopUpVISUALIZZAFILEALLEGATO_${index1}+=creaPopUpSubmenu("javascript:eliminaFile(${index1});hideMenuPopup();",0,"&nbsp;Elimina");
</c:forEach>

	function eliminaFile(indice) {
		if(confirm("Eliminare il file Pdf allegato ?")) {
			showObj("rowVISUALIZZAFILEALLEGATO_"+indice, false);
			showObj("rowNOMEFILEALLEGATO_"+indice, true);
			setValue("W9LOTT_ENTINAZ_FILEDAALLEGARE_"+indice, "canc");
			alert("W9LOTT_ENTINAZ_FILEDAALLEGARE_"+indice + " = '" + getValue("W9LOTT_ENTINAZ_FILEDAALLEGARE_"+indice) + "'");
		}
	}

	function scegliFile(indice) {
		var selezioneFile = document.getElementById("selFile[" + indice + "]").value;
		var lunghezza_stringa = selezioneFile.length;
		var posizione_barra = selezioneFile.lastIndexOf("\\");
		var posizione_punto = selezioneFile.lastIndexOf(".");
		var estensione = selezioneFile.substring(posizione_punto+1, lunghezza_stringa).toUpperCase();
		
		if(estensione != "PDF") {
			alert("E' permessa la selezione solo di file in formato PDF");
			document.getElementById("selFile[" + indice + "]").value = "";
			setValue("W9LOTT_ENTINAZ_FILEDAALLEGARE_" + indice, "");
		} else {
			var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
			setValue("W9LOTT_ENTINAZ_FILEDAALLEGARE_" + indice, nome);
		}
	}

</c:if>

	function formCPV(modifica, indice) {
		openPopUpCustom("href=commons/descriz-codice-cpv.jsp&key=" + document.forms[0].key.value + "&keyParent=" + document.forms[0].keyParent.value + "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")+"&campo=W9LOTT_ENTINAZ_CPV_" + indice + "&campoDescr=CPVDESC_" + indice + "&valore="+ (getValue("W9LOTT_ENTINAZ_CPV_"+indice) == "" ? "45000000-7" : getValue("W9LOTT_ENTINAZ_CPV_"+indice))+"&valoreDescr="+ (getValue("CPVDESC_"+indice) == "" ? "Lavori di costruzione" : getValue("CPVDESC_"+indice)), "formCPV", 700, 300, 1, 1);
	}

	function apriAllegato(codlott) {
		var href = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		document.location.href = href+"?" + csrfToken + "&codgara="+getValue("W9GARA_ENTINAZ_CODGARA")+"&codlott=" + codlott + "&tab=entinaz";
	}

</gene:javaScript>