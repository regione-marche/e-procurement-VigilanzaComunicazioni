<%
  /*
   * Created on: 16/03/2012
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

<gene:formScheda entita="W9GARA_ENTINAZ" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9GARAENTINAZ"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARAENTINAZ" >
	
	<gene:redefineInsert name="schedaNuovo"/>
	<gene:redefineInsert name="pulsanteNuovo"/>
	
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda campo="CODGARA" visibile="false" />
		<gene:campoScheda campo="DGURI" />
		<gene:campoScheda campo="DSCADE" />
	</gene:gruppoCampi>
	
	<c:set var="codiceGara" value="${fn:substringAfter(key, ':')}" />
	
	<c:choose>	
		<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and ctrlBlob eq 'false'}">
			<gene:campoScheda>
				<td colspan="2"><b>Documenti del Bando</b></td>
			</gene:campoScheda>
			<gene:campoScheda >
				<td colspan="2">Nessun file allegato</td>
			</gene:campoScheda>
		</c:when>
		<c:when test="${(modoAperturaScheda ne 'VISUALIZZA' and ctrlBlob eq 'false') or ctrlBlob eq 'true'}">

			<c:set var="tmp" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GestioneDocumentazioneBandoFunction", pageContext,codiceGara)}' />

			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W9DOCGARA'/>
				<jsp:param name="chiave" value='${codiceGara}'/>
				<jsp:param name="nomeAttributoLista" value='documentiBando' />
				<jsp:param name="idProtezioni" value="W9DOCGARA" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docgara/bando-documenti-gara.jsp"/>
				<jsp:param name="arrayCampi" value="'W9DOCGARA_CODGARA_', 'W9DOCGARA_NUMDOC_', 'W9DOCGARA_TITOLO_', 'W9DOCGARA_URL_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_'"/> 		
				<jsp:param name="titoloSezione" value="Documenti del Bando" />
				<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
				<jsp:param name="descEntitaVociLink" value="documento" />
				<jsp:param name="msgRaggiuntoMax" value="i documenti" />
				<jsp:param name="usaContatoreLista" value="true" />
			</jsp:include>
		</c:when>
	</c:choose>

	<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="W9GARA_ENTINAZ" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>
<gene:javaScript>

<c:if test="${modo eq 'MODIFICA'}">
	document.forms[0].encoding="multipart/form-data";

	var tmpSchedaConferma = schedaConferma;
	var tmpSchedaAnnulla = schedaAnnulla;
	
	var schedaConferma = function(){
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

</c:if>

	function scegliFile(indice) {
		var selezioneFile = document.getElementById("selFile[" + indice + "]").value;
		var lunghezza_stringa = selezioneFile.length;
		var posizione_barra = selezioneFile.lastIndexOf("\\");
		var posizione_punto = selezioneFile.lastIndexOf(".");
		var estensione = selezioneFile.substring(posizione_punto+1, lunghezza_stringa).toUpperCase();
		
		if(estensione != "PDF"){
			alert("E' permessa la selezione solo di file in formato PDF");
			document.getElementById("selFile[" + indice + "]").value = "";
			setValue("NOME_FILE_" + indice, "");
		} else {
			var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
			setValue("NOME_FILE_" + indice, nome);
		}
	}

	function visualizzaFileAllegato(numeroDoc) {
		var href = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		document.location.href = href+"?" + csrfToken + "&codgara=${codiceGara}&numdoc=" + numeroDoc + "&tab=gara";
	}

</gene:javaScript>