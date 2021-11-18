
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

<%
  /* Dati generali del lavoro */
%>
<gene:formScheda entita="W9PUBBLICAZIONI" gestisciProtezioni="true" 
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9PUBBLICAZIONI" >
	
	<gene:redefineInsert name="schedaNuovo"/>
	<gene:redefineInsert name="pulsanteNuovo"/>
	
	<input type="hidden" name="tipoPubblicazione" value='${tipoPubblicazione}' />
	<input type="hidden" name="pathScheda" />
	
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda campo="CODGARA" visibile="false" defaultValue="${codiceGara}"/>
		<gene:campoScheda campo="NUM_PUBB" visibile="false"/>
		<gene:campoScheda campo="CODLOTT" campoFittizio="true" visibile="false" defaultValue="${codiceLotto}" definizione="N10"/>
				
		<gene:campoScheda campo="TIPDOC" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroIDSceltaContraente" defaultValue="${tipoPubblicazione}" modificabile="false" />
		<gene:campoScheda campo="DESCRIZ" />
			
		<gene:campoScheda campo="CAMPI_VIS" entita="W9CF_PUBB" where="W9CF_PUBB.ID=W9PUBBLICAZIONI.TIPDOC" visibile="false" defaultValue="${campiVisualizzabili}"/>
		<gene:campoScheda campo="CAMPI_OBB" entita="W9CF_PUBB" where="W9CF_PUBB.ID=W9PUBBLICAZIONI.TIPDOC" visibile="false" defaultValue="${campiObbligatori}"/>
		
		<gene:campoScheda campo="IDFLUSSO" visibile="false" entita="W9FLUSSI"
						where="W9FLUSSI.AREA=2 AND W9FLUSSI.KEY01=W9PUBBLICAZIONI.CODGARA AND W9FLUSSI.KEY03=901 AND W9FLUSSI.KEY04=W9PUBBLICAZIONI.NUM_PUBB" />

		<!-- Campi Dinamici -->
		<gene:campoScheda campo="DATAPUBB" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATAPUBB')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATAPUBB')}"/>
		<gene:campoScheda campo="DATASCAD" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATASCAD')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATASCAD')}"/>
		<gene:campoScheda campo="DATA_DECRETO" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATA_DECRETO')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATA_DECRETO')}"/>

		<gene:campoScheda campo="DATA_PROVVEDIMENTO" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATA_PROVVEDIMENTO')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATA_PROVVEDIMENTO')}"/>
		<gene:campoScheda campo="NUM_PROVVEDIMENTO" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'NUM_PROVVEDIMENTO')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'NUM_PROVVEDIMENTO')}"/>
		<gene:campoScheda campo="DATA_STIPULA" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATA_STIPULA')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATA_STIPULA')}"/>
		<gene:campoScheda campo="NUM_REPERTORIO" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'NUM_REPERTORIO')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'NUM_REPERTORIO')}"/>

		<gene:campoScheda title="Ribasso di aggiudicazione (nel caso di appalto con lotti &eacute; calcolato in relazione al ribasso dei singoli lotti)" campo="PERC_RIBASSO_AGG" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'PERC_RIBASSO_AGG')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'PERC_RIBASSO_AGG')}"/>
		<gene:campoScheda campo="PERC_OFF_AUMENTO" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'PERC_OFF_AUMENTO')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'PERC_OFF_AUMENTO')}"/>
		<gene:campoScheda campo="IMPORTO_AGGIUDICAZIONE" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'IMPORTO_AGGIUDICAZIONE')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'IMPORTO_AGGIUDICAZIONE')}"/>
		<gene:campoScheda campo="DATA_VERB_AGGIUDICAZIONE" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'DATA_VERB_AGGIUDICAZIONE')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'DATA_VERB_AGGIUDICAZIONE')}"/>
		
		<gene:campoScheda campo="URL_COMMITTENTE" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'URL_COMMITTENTE')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'URL_COMMITTENTE')}"/>
		<gene:campoScheda campo="URL_EPROC" visibile="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_VIS, 'URL_EPROC')}" obbligatorio="${fn:containsIgnoreCase(datiRiga.W9CF_PUBB_CAMPI_OBB, 'URL_EPROC')}"/>

	</gene:gruppoCampi>

	<c:if test='${modo ne "NUOVO"}'>
		<c:set var="tmp" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GestioneDocumentazionePubblicazioneFunction", pageContext,codiceGara,numeroPubblicazione)}' />
	</c:if>
	
	<c:choose>	
		<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and not(isDocPubblicazioneEstratti)}">
			<gene:campoScheda>
				<td colspan="2"><b>Documenti dell'atto</b></td>
			</gene:campoScheda>
			<gene:campoScheda >
				<td colspan="2">Nessun file allegato</td>
			</gene:campoScheda>
		</c:when>
		<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and isDocPubblicazioneEstratti}">

			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W9DOCGARA'/>
				<jsp:param name="chiave" value='${codiceGara}'/>
				<jsp:param name="nomeAttributoLista" value='documentiPubblicazione' />
				<jsp:param name="idProtezioni" value="W9DOCGARA" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docgara/pubblicazione-documenti-gara.jsp"/>
				<jsp:param name="arrayCampi" value="'W9DOCGARA_CODGARA_', 'W9DOCGARA_NUMDOC_', 'W9DOCGARA_TITOLO_', 'W9DOCGARA_URL_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_', 'W9DOCGARA_NUM_PUBB_'"/> 		
				<jsp:param name="titoloSezione" value="Documenti" />
				<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
				<jsp:param name="descEntitaVociLink" value="documento" />
				<jsp:param name="msgRaggiuntoMax" value="i documenti" />
				<jsp:param name="usaContatoreLista" value="true" />
			</jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W9DOCGARA'/>
				<jsp:param name="chiave" value='${codiceGara}'/>
				<jsp:param name="nomeAttributoLista" value='documentiPubblicazione' />
				<jsp:param name="idProtezioni" value="W9DOCGARA" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docgara/pubblicazione-documenti-gara.jsp"/>
				<jsp:param name="arrayCampi" value="'W9DOCGARA_CODGARA_', 'W9DOCGARA_NUMDOC_', 'W9DOCGARA_TITOLO_', 'W9DOCGARA_URL_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_', 'W9DOCGARA_NUM_PUBB_'"/> 		
				<jsp:param name="titoloSezione" value="Documenti" />
				<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
				<jsp:param name="descEntitaVociLink" value="documento" />
				<jsp:param name="msgRaggiuntoMax" value="i documenti" />
				<jsp:param name="usaContatoreLista" value="true" />
			</jsp:include>
		</c:otherwise>
	</c:choose>

		<gene:redefineInsert name="addToAzioni">
		<tr>
			<td class="vocemenulaterale">
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					<a href="javascript:aggiungi();"title="Aggiungi" tabindex="1502">
				</c:if>
				Aggiungi
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					</a>
				</c:if>
			</td>
		</tr>

		<c:if test="${empty datiRiga.W9FLUSSI_IDFLUSSO}">
		<tr>
			<td class="vocemenulaterale">
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					<a href="javascript:elimina();" title="Elimina" tabindex="1503">
				</c:if>
				Elimina
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					</a>
				</c:if>
			</td>
		</tr>
		<c:set var="testInvioPubblicazione" value="true" />
		</c:if>
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.FasiDiGara-schede.ControlloDatiInseriti")}'>
		<tr>
			<td class="vocemenulaterale">
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					<a href="javascript:popupValidazione('901', '${datiRiga.W9PUBBLICAZIONI_CODGARA}', '','${datiRiga.W9PUBBLICAZIONI_NUM_PUBB}');" title="Invia" tabindex="1504">
				</c:if>
					Controllo dati inseriti
				<c:if test="${modoAperturaScheda eq 'VISUALIZZA'}">
					</a>
				</c:if>
			</td>
		</tr>
		</c:if>
		
		<c:if test="${tipoRicerca eq 'esiti' and modoAperturaScheda eq 'VISUALIZZA' and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9SCP')}">
		<tr>
			<td class="vocemenulaterale">
				<a href="javascript:schedaAnagraficaGara();" title="Vai all'anagrafica gara/lotto" tabindex="1505">
				Vai all'anagrafica gara/lotto
				</a>
			</td>
		</tr>
		</c:if>
	</gene:redefineInsert>
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	

</gene:formScheda>

<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>
<gene:javaScript>

	function aggiungi() {
		document.location.href ="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=W9PUBBLICAZIONI.CODGARA=N:${codiceGara}&modo=NUOVO&tipoPubblicazione=${tipoPubblicazione}&codiceLotto=${codiceLotto}";
	}
	
	function elimina() {
		if(confirm("Eliminare l'atto e documenti corrispondenti?")) {
			// vedi w9flussi logica
			document.location.href ="${pageContext.request.contextPath}/w9/CancellaPubblicazione.do?" + csrfToken + "&codgara=${codiceGara}&numpubb=${numeroPubblicazione}";
		}
	}
				
	function popupValidazione(flusso,key1,key2,key3) {
		var comando = "href=w9/commons/popup-validazione-generale.jsp";
		comando = comando + "&flusso=" + flusso;
		comando = comando + "&key1=" + key1;
		comando = comando + "&key2=" + key2;
		comando = comando + "&key3=" + key3;
		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}

	// Visualizzazione Anagrafica Gara/Lotto
	function schedaAnagraficaGara() {
		document.forms[0].jspPathTo.value = "/WEB-INF/pages/w9/w9gara/w9gara-scheda.jsp";
		document.forms[0].entita.value = "W9GARA";
		document.forms[0].key.value="W9GARA.CODGARA=N:${codiceGara}";
		document.forms[0].metodo.value='apri';
		document.forms[0].activePage.value="0";
		document.forms[0].submit();
	}
	
<c:if test="${modo eq 'MODIFICA' || modo eq 'NUOVO'}">
	document.forms[0].encoding="multipart/form-data";

	var tmpSchedaConferma = schedaConferma;
	var tmpSchedaAnnulla = schedaAnnulla;
	
	var schedaConferma = function() {
		// per ciascuna sezione dinamica visibile nella pagina si controlla che,
		// se il campo TITOLO e' valorizzato, allora anche il campo di tipo input file lo sia
		var numeroSezioni = eval(getValue("NUMERO_W9DOCGARA"));
		var continua = true;
		for (var er = 1; er <= numeroSezioni && continua; er++) {
			if (isObjShow("rowW9DOCGARA_TITOLO_" + er)) {
				if (getValue("W9DOCGARA_TITOLO_" + er) != "") {
					if (getValue("defVISUALIZZA_FILE_" + er) == null || getValue("defVISUALIZZA_FILE_" + er) == "") {
						if (document.getElementById("selFile[" + er + "]") != null && getValue("selFile[" + er + "]") == "") {
							if (getValue("W9DOCGARA_URL_" + er) == "") {
								alert("Valorizzare il campo 'URL della documentazione'\noppure il campo 'Nome file'");
								continua = false;
							}
						} else {
							if (document.getElementById("selFile[" + er + "]") != null) {
								if (getValue("NOME_FILE_"+er) == "" && getValue("W9DOCGARA_URL_" + er) == "") {
									alert("Valorizzare il campo 'URL della documentazione'\noppure il campo 'Nome file'");
									continua = false;
								} else if (getValue("NOME_FILE_"+er) != "" && getValue("W9DOCGARA_URL_" + er) != "") {
									alert("Valorizzare il campo 'URL della documentazione'\noppure il campo 'Nome file'");
									continua = false;
								}
							} else {
								if (getValue("W9DOCGARA_URL_" + er) == "") {
									alert("Valorizzare il campo 'URL documentazione'");
									continua = false;
								}
							}
						}
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

</c:if>

	function scegliFile(indice) {
		var selezioneFile = document.getElementById("selFile[" + indice + "]").value;
		var lunghezza_stringa = selezioneFile.length;
		var posizione_barra = selezioneFile.lastIndexOf("\\");
		var posizione_punto = selezioneFile.lastIndexOf(".");
		var estensione = selezioneFile.substring(posizione_punto+1, lunghezza_stringa).toUpperCase();
		
		if (estensione != "PDF" && estensione != "P7M") {
			alert("E' permessa la selezione solo di file in formato PDF o P7M");
			document.getElementById("selFile[" + indice + "]").value = "";
			setValue("NOME_FILE_" + indice, "");
		} else {
			var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
			setValue("NOME_FILE_" + indice, nome);
		}
	}

	function visualizzaFileAllegato(numeroDoc) {
		var href = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		document.location.href = href+"?" + csrfToken + "&codgara=${codiceGara}&numPubb=${numeroPubblicazione}&numdoc=" + numeroDoc + "&tab=gara";
	}
	
	function controlloURL(progressivoCampo) {
		if (document.getElementById("selFile[" + progressivoCampo + "]") != null && getValue("selFile[" + progressivoCampo + "]") == "" ) {
			var strUrl = trimStringa(getValue("W9DOCGARA_URL_" + progressivoCampo));
			document.getElementById("W9DOCGARA_URL_" + progressivoCampo).value = strUrl;
			
			if (strUrl.toLowerCase().indexOf("http://") == 0 || strUrl.toLowerCase().indexOf("https://") == 0) {
				return true;
			} else if (strUrl.toLowerCase().indexOf("ftp://") == 0 || strUrl.toLowerCase().indexOf("ftps://") == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	$(document).ready(function() { 
		if ('${numDocumenti}' != '') {
			var numeroDocumenti= '${numDocumenti}';
			for (var i=1; i<=numeroDocumenti; i++) {
				var rigaDocumento = '#rowW9DOCGARA_URL_' + i + ' a';
				if ($(rigaDocumento).attr('href')) {
					var link = $(rigaDocumento).attr('href');
					if (link.toLowerCase().indexOf("http") < 0){
						var link = 'http://' + link;
						$(rigaDocumento).attr("href", link);
					}	
					$(rigaDocumento).attr("target","_blank");
				}
			}
		}
	});

</gene:javaScript>