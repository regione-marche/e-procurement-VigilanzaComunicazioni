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

	<gene:formScheda entita="W9INIZ" gestisciProtezioni="true"
			plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9MISSIC"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9INIZ" >
			
			<c:set var="key" value='${key}' scope="request" />
			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}'></c:set>
			<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}'></c:set>
			<c:set var="flusso" value='993' scope="request" />
			<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
			<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
			<c:set var="key3" value='${gene:getValCampo(key,"NUM_MISS")}' scope="request" />

			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
			<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>
			
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda>
					<td colspan="2"><b>Dati generali </b></td>
				</gene:campoScheda>
				
				<gene:campoScheda campo="CODGARA" visibile="false" />
				<gene:campoScheda campo="CODLOTT" visibile="false" />
				<gene:campoScheda campo="NUM_INIZ" visibile="false" />
				<gene:campoScheda campo="NUM_APPA" visibile="false" />
				
				<!--gene-:-campo-Scheda entita="W9MISSIC" campo="CODGARA"  where="W9MISSIC.CODGARA= ${codgara} and W9MISSIC.CODLOTT = ${codlott} and W9MISSIC.NUM_MISS = ${num}" visibile="false" /-->
				<gene:campoScheda entita="W9MISSIC" campo="CODGARA"  where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ" visibile="false" />
				<gene:campoScheda entita="W9MISSIC" campo="CODLOTT"  where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ" visibile="false" />
				<gene:campoScheda entita="W9MISSIC" campo="NUM_MISS" where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ" visibile="${modoAperturaScheda ne 'NUOVO'}" />
				<gene:campoScheda entita="W9MISSIC" campo="DESCMIS"  where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ" /> 
				<gene:campoScheda entita="W9MISSIC" campo="NUM_INIZ"  where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ" visibile="false"/>
				
				<gene:archivio titolo="IMPRESE" obbligatorio="true"
						lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9MISSIC.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
						scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
						schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
						campi="IMPR.CODIMP;IMPR.NOMEST" chiave="W9MISSIC_CODIMP" 
						where="IMPR.CODIMP in (select CODIMP from W9AGGI where CODGARA=${codgara} and CODLOTT=${codlott} and NUM_APPA=${datiRiga.W9INIZ_NUM_APPA})" >
						<gene:campoScheda campo="CODIMP" visibile="false" from="W9INIZ" entita="W9MISSIC" where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ  AND W9MISSIC.CODIMP=IMPR.CODIMP" />
						<gene:campoScheda campo="NOMEST" entita="IMPR" from="W9MISSIC" where="W9MISSIC.CODGARA=W9INIZ.CODGARA and W9MISSIC.CODLOTT=W9INIZ.CODLOTT and W9MISSIC.NUM_INIZ=W9INIZ.NUM_INIZ AND W9MISSIC.CODIMP=IMPR.CODIMP" title="Denom. Impresa Aggiudicataria" definizione="T61" />
				</gene:archivio>
			</gene:gruppoCampi>

			<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
			
			<c:choose>	
				<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and ctrlBlob eq 'false'}">
					<gene:campoScheda>
						<td colspan="2"><b>Documentazione</b></td>
					</gene:campoScheda>
					<gene:campoScheda >
						<td colspan="2">Nessuna documentazione</td>
					</gene:campoScheda>
				</c:when>
				<c:otherwise>
					<c:set var="tmp" value='${gene:callFunction5("it.eldasoft.w9.tags.funzioni.GestioneDocumentazioneFaseFunction", pageContext, codgara, codlott, "993", gene:getValCampo(key, "NUM_INIZ"))}' />
					<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
						<jsp:param name="entita" value='W9DOCFASE'/>
						<jsp:param name="chiave" value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};993;${gene:getValCampo(key, "NUM_INIZ")}'/>
						<jsp:param name="nomeAttributoLista" value='datiW9DOCFASE' />
						<jsp:param name="idProtezioni" value="W9DOCFASE" />
						<jsp:param name="sezioneListaVuota" value="false" />
						<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docfase/documenti-allegati-fase.jsp"/>
						<jsp:param name="arrayCampi" value="'W9DOCFASE_CODGARA_','W9DOCFASE_CODLOTT_','W9DOCFASE_FASE_ESECUZIONE_','W9DOCFASE_NUM_FASE_','W9DOCFASE_NUMDOC_','W9DOCFASE_TITOLO_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_'"/> 		
						<jsp:param name="titoloSezione" value="Documentazione" />
						<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
						<jsp:param name="descEntitaVociLink" value="documento" />
						<jsp:param name="msgRaggiuntoMax" value="i documenti" />
						<jsp:param name="usaContatoreLista" value="true" />
					</jsp:include>
				</c:otherwise>
			</c:choose>
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	
<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>	
	
<gene:javaScript>   
	document.forms[0].encoding="multipart/form-data";

	var tmpSchedaConferma = schedaConferma;
	var tmpSchedaAnnulla = schedaAnnulla;
		
	var schedaConferma = function() {
		var numeroSezioni = eval(getValue("NUMERO_W9DOCFASE"));
		var continua = true;
		for(var er=1; er <= numeroSezioni && continua; er++) {
			if(isObjShow("rowW9DOCFASE_TITOLO_" + er)) {
				if(getValue("W9DOCFASE_TITOLO_" + er) != "") {
					if(document.getElementById("selFile[" + er + "]") != null && getValue("selFile[" + er + "]") == "") {
						alert("I campi 'Nome file' sono obbligatori");
						continua = false;
					}
				}
			}
		}

		if (continua) {
			document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
			tmpSchedaConferma();
		}
	}
	
	var schedaAnnulla = function() {
		document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
		tmpSchedaAnnulla();
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
			setValue("NOME_FILE_" + indice, "");
		} else {
			var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
			setValue("NOME_FILE_" + indice, nome);
		}
	}

	function visualizzaFileAllegato(codgara,codlott,fase_esecuzione,num_fase,numdoc) {
		var href = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		href = href + "?" + csrfToken + "&codgara=" + codgara;
		href = href + "&codlott=" + codlott;
		href = href + "&fase_esecuzione=" + fase_esecuzione;
		href = href + "&num_fase=" + num_fase;
		href = href + "&numdoc=" + numdoc;
		href = href + "&tab=fase";
		document.location.href = href;
	}
	
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
