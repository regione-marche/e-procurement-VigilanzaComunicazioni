<%
/*
 * Created on: 20/05/2014
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>

<c:set var="filtroUffint" value=""/> 
<c:if test="${! empty sessionScope.uffint && fn:contains(archiviFiltrati,'IMPR')}">
	<c:set var="filtroUffint" value="IMPR.CGENIMP = '${sessionScope.uffint}'"/>
</c:if>

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9IMPRESE-scheda">
	<c:choose>
		<c:when test="${not empty param.codGara}">
			<c:set var="key" value='W9IMPRESE.CODGARA=N:${param.codGara};W9IMPRESE.CODLOTT=N:${param.codLott}' scope="request" />
			<c:set var="codgara" value="${param.codGara}" scope="request" />
			<c:set var="codlott" value="${param.codLott}" scope="request" />
			<c:set var="flusso" value="101" scope="request" />
			<c:set var="key1" value="${param.codGara}" scope="request" />
			<c:set var="key2" value="${param.codLott}" scope="request" />
			<c:set var="key3" value="1" scope="request" />
		</c:when>
		<c:when test="${not empty requestScope.codGara}">
			<c:set var="key" value='W9IMPRESE.CODGARA=N:${requestScope.codGara};W9IMPRESE.CODLOTT=N:${requestScope.codLott}' scope="request" />
			<c:set var="codgara" value="${requestScope.codGara}" scope="request" />
			<c:set var="codlott" value="${requestScope.codLott}" scope="request" />
			<c:set var="flusso" value="101" scope="request" />
			<c:set var="key1" value="${requestScope.codGara}" scope="request" />
			<c:set var="key2" value="${requestScope.codLott}" scope="request" />
			<c:set var="key3" value="1" scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
			<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
			<c:set var="flusso" value="101" scope="request" />
			<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
			<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
			<c:set var="key3" value="1" scope="request" />
		</c:otherwise>
	</c:choose>

	<gene:setString name="titoloMaschera" value="Elenco imprese invitate/partecipanti" />
	
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
	
	<form name="listaW9Imprese" action="ListaW9Imprese.do">
		<table class="lista">
			<tr>
				<td>
					<display:table name="listaW9Imprese" id="impresa" class="datilista" sort="list" pagesize="20" requestURI="ListaW9Imprese.do" >
						<display:column title="Opzioni<br>${titoloMenu}" style="width:50px">
							<elda:linkPopupRecord idRecord="${impresa.codiceGara};${impresa.codiceLotto};${impresa.numeroImpresa};${impresa.numRaggruppamento}" contextPath="${pageContext.request.contextPath}"/>
							&nbsp;<input type="checkbox" name="keys" id="" value="${impresa.codiceGara};${impresa.codiceLotto};${impresa.numeroImpresa};${impresa.numRaggruppamento}"/>
						</display:column>
						
						
						<display:column title="Id. impresa" sortable="true" headerClass="sortable" >
							<a href="javascript:visualizza('${impresa.codiceGara};${impresa.codiceLotto};${impresa.numeroImpresa};${impresa.numRaggruppamento}')" />
								${impresa.identificativoImpresa}
							</a>
						</display:column>
						<display:column property="descTipoImpresa" title="Descr. tipo impresa" sortable="true" headerClass="sortable" />
						<display:column property="nomeEstesoImpr" title="Nome impresa" sortable="false" headerClass="sortable" />
						<display:column property="descPartecip" title="Partecipa?" sortable="true" headerClass="sortable" />
					</display:table>
				</td>
			</tr>

			<gene:redefineInsert name="pulsanteListaInserisci">
				<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
					<INPUT type="button" class="bottone-azione" value='Aggiungi impresa' title='Aggiungi impresa' onclick="javascript:aggiungiImpresa(0)">
				</c:if>
			</gene:redefineInsert>

			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp"/></tr>
		</table>
			<input type="hidden" name="metodo" id="metodo" value="eliminaSelez" >
			<input type="hidden" name="key" id="key" value="${key}" >
	</form>

	<form id="formVisualizzaImprese" name="formVisualizzaImprese" action="${pageContext.request.contextPath}/Archivio.do" method="post" >
		<input type="hidden" name="metodo" value="scheda" />
		<input type="hidden" name="jspPath" value="/WEB-INF/pages/w9/w9imprese/w9imprese-lista.jsp" />
		<input type="hidden" name="jspPathTo" value="" />
		<input type="hidden" name="activePage" value="" />
		<input type="hidden" name="isPopUp" value="0" />
		<input type="hidden" name="numeroPopUp" value="" />
		<input type="hidden" name="modo" value="VISUALIZZA" />
		<input type="hidden" name="archTitolo" value="IMPRESE" />
		<input type="hidden" name="archObbligatorio" value="0" />
		<input type="hidden" name="archLista" value="gene/impr/impr-lista-popup.jsp" />
		<input type="hidden" name="archScheda" value="gene/impr/impr-scheda.jsp" />
		<input type="hidden" name="archSchedapopup" value="gene/impr/impr-scheda-popup.jsp" />
		<input type="hidden" name="archCampi" value="IMPR_NOMEST_1;W9IMPRESE_CODIMP_1" />
		<input type="hidden" name="archCampiArchivio" value="IMPR.NOMEST;IMPR.CODIMP" />
		<input type="hidden" name="archCampiNoSet" value="" />
		<input type="hidden" name="archChiave" value="W9IMPRESE_CODIMP_1" />
		<input type="hidden" name="archValueChiave" value="" />
		<input type="hidden" name="archTipoCampoChanged" value="" />
		<input type="hidden" name="archCampoChanged" value="" />
		<input type="hidden" name="archValueCampoChanged" value="" />
		<input type="hidden" name="archWhereLista" value="${filtroUffint}" />
		<input type="hidden" name="archSiPuoInserire" value="1" />
		<input type="hidden" name="archIsOpenInPopUp" value="" />
	</form>

	</gene:redefineInsert>
	
	<gene:redefineInsert name="listaNuovo">
		<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:aggiungiImpresa(0);" title="Aggiungi impresa" tabindex="1502">
						Aggiungi impresa
					</a>
				</td>
			</tr>
		</c:if>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>

	<gene:javaScript>
	
		function visualizzaImpresaScheda(codiceImpresa){
			document.formVisualizzaImprese.archValueChiave.value = "T:" + codiceImpresa;
			document.formVisualizzaImprese.submit(false);
		}
		
		function generaPopupListaOpzioniRecord(id) {
			<elda:jsBodyPopup varJS="linkset" contextPath="${pageContext.request.contextPath}">
				<elda:jsVocePopup functionJS="visualizza('\"+id+\"')" descrizione="Visualizza impresa"/>
				<c:if test='${gene:checkProtFunz(pageContext, "MOD", "MOD")}'>
					<elda:jsVocePopup functionJS="modifica('\"+id+\"')" descrizione="Modifica impresa"/>
				</c:if>			
				<c:if test='${gene:checkProtFunz(pageContext, "DEL", "DEL")}'>		
					<elda:jsVocePopup functionJS="elimina('\"+id+\"')" descrizione="Elimina impresa"/>
				</c:if>
			</elda:jsBodyPopup>
			return linkset;
		}
	
		function popupValidazione(flusso,key1,key2,key3) {
			var comando = "href=w9/commons/popup-validazione-generale.jsp";
			comando = comando + "&flusso=" + flusso;
			comando = comando + "&key1=" + key1;
			comando = comando + "&key2=" + key2;
			comando = comando + "&key3=" + key3;
			openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
		}
	
		function aggiungiImpresa(tipo) {
			var href = "href=w9/w9imprese/w9imprese-scheda.jsp&modo=NUOVO&codGara=${key1}&codLott=${key2}";
			if (tipo == 1) {
				href = href + "&annulla=1";
			}
			document.location.href="${pageContext.request.contextPath}"+"/ApriPagina.do?" + csrfToken + "&" +href;
		}
		
		function visualizza(chiave) {
			var arrayChiave = chiave.split(";");
			//alert("chiave.length = " + arrayChiave.length);

			var codiceGara  = arrayChiave[0];
			var codiceLotto = arrayChiave[1];
			var numImpresa  = arrayChiave[2];
			var numRaggrup  = arrayChiave[3];
			
			var tempKey = document.forms[0].key.value + ";W9IMPRESE.NUM_IMPR=N:" + numImpresa;
			if ("" == numRaggrup) {
				// Apertura dettaglio di una impresa singola
				var href = "href=w9/w9imprese/w9imprese-scheda.jsp&modo=VISUALIZZA&codGara="+ codiceGara + "&codLott=" + codiceLotto + "&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			} else {
				// Apertura dettaglio di un gruppo di imprese
				var href = "href=w9/w9imprese/w9imprese-scheda.jsp&modo=VISUALIZZA&codGara="+ codiceGara + "&codLott=" + codiceLotto + "&numImpr=" + numImpresa + "&numRagg=" + numRaggrup + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
		
		function modifica(chiave) {
			var arrayChiave = chiave.split(";");
			var codiceGara = arrayChiave[0];
			var codiceLotto = arrayChiave[1];
			var numImpresa = arrayChiave[2];
			var numRaggrup = arrayChiave[3];
				
			var tempKey = document.forms[0].key.value + ";W9IMPRESE.NUM_IMPR=N:" + numImpresa;
			if ("" == numRaggrup) {
				// Apertura in modifica del dettaglio di una impresa singola
				var href = "href=w9/w9imprese/w9imprese-scheda.jsp&modo=MODIFICA&codGara="+ codiceGara + "&codLott=" + codiceLotto + "&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
				
			} else {
				// Apertura in modifica del dettaglio di un gruppo di imprese
				var href = "href=w9/w9imprese/w9imprese-scheda.jsp&modo=MODIFICA&codGara="+ codiceGara + "&codLott=" + codiceLotto + "&numImpr=" + numImpresa + "&numRagg=" + numRaggrup + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}

		function nuovo(tipoImpresa) {
			bloccaRichiesteServer();
			var href = "href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=NUOVO&codGara=${key1}&codLott=${key2}&tipoImpresa=" + tipoImpresa; // + "&key=" + tempKey;
			document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
		}

		function elimina(chiave) {
				
			<c:set var="localkey" value='${codgara};${codlott};${flusso};1' scope="request" />
			<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
		
			if (confirm("Procedere con l'eliminazione?")) {
				bloccaRichiesteServer();
				document.location.href = "${pageContext.request.contextPath}/w9/ListaW9Imprese.do?" + csrfToken + "&metodo=elimina&key=" + chiave;
			}
		}

		function listaEliminaSelezione() {
							
			<c:set var="localkey" value='${codgara};${codlott};${flusso};1' scope="request" />
			<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
		
			var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		  if (numeroOggetti == 0) {
	      alert("Nessun elemento selezionato nella lista");
		  } else {
	   	  if (confirm("Sono stati selezionati " + numeroOggetti + " elementi. Procedere con l'eliminazione?")) {
	   	  	bloccaRichiesteServer();
					document.forms[0].metodo.value = "eliminaSelez";
					document.forms[0].submit();
				}
			}
		}

		<c:if test="${empty listaW9Imprese}" >
			bloccaRichiesteServer();
			aggiungiImpresa(1);
		</c:if>	
	
	</gene:javaScript>
</gene:template>
