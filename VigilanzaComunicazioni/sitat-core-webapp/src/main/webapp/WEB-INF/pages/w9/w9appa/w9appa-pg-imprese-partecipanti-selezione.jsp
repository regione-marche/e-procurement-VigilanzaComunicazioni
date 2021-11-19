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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<% /* Dati generali del lavoro */ %>
<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9IMPRESE-scheda">

	<c:set var="isAccordoQuadro" value='${param.isAccordoQuadro}' />
	<c:set var="codGara" value='${param.codGara}' />
	<c:set var="numAppa" value='${param.numAppa}' />
	<c:set var="codLott" value='${param.codLott}' />

<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>

<c:set var="filtroUffint" value=""/> 
<c:if test="${! empty sessionScope.uffint && fn:contains(archiviFiltrati,'IMPR')}">
	<c:set var="filtroUffint" value=" IMPR.CGENIMP = '${sessionScope.uffint}'"/>
</c:if>

	<%/* inserimento step nel breadcrumb per evitare al ciclo operativo di geneweb di tornare alla selezione della fase lotto */%>
	<gene:redefineInsert name="addHistory">
		<c:if test='${modo eq "VISUALIZZA"}'>
		<gene:historyAdd 
					titolo="Dettaglio Imprese Aggiudicatarie"
					id="${key};FaseAggiudicazione" />
		</c:if>
	</gene:redefineInsert>
	
	<% /* recupero id gruppo e numero aggiudicataria o come parametro o come attributo per la visualizzazione*/ %>
	<c:set var="id_gruppoLoc" value='${param.id_gruppo}' />
	<c:set var="numImprLoc" value='${param.numImpr}' />

	<c:if test='${empty numImprLoc}' >
		<c:set var="id_gruppoLoc" value='${requestScope.id_gruppo}' />
		<c:set var="numImprLoc" value='${requestScope.numImpr}' />
	</c:if>
	
	<c:set var="id_gruppo" value='${id_gruppoLoc}' />
	<c:set var="numImpr" value='${numImprLoc}' />

	<c:set var="key_aggiudicataria" value='${key};NUMIMPR=N:${numImpr};ID_GRUPPO=N:${id_gruppo}' />

	<gene:redefineInsert name="listaNuovo" />
	<gene:redefineInsert name="listaEliminaSelezione" />
	<gene:redefineInsert name="addToAzioni" >
		<tr>
			<td class="vocemenulaterale">
				<a href="javascript:listaInserisciSelezione();" title="Seleziona" tabindex="1502">
					Seleziona</a>
			</td>
		</tr>
	</gene:redefineInsert>
	<gene:redefineInsert name="corpo">
		
	<!--  gene lista di w9imprese	-->
	
	<c:set var="key" value="${key}" scope="request" />
	<c:set var="codgara" value="${codGara}" scope="request" />
	<c:set var="codlott" value="${codLott}" scope="request" />
	<c:set var="flusso" value="101" scope="request" />
	<c:set var="key1" value="${codGara}" scope="request" />
	<c:set var="key2" value="${codLott}" scope="request" />
	
	<gene:setString name="titoloMaschera" value="Elenco imprese partecipanti selezionabili" />	
	
	<form name="listaW9SelezionePartecipanti" action="ListaW9SelezionePartecipanti.do">
		<table class="lista">
			<tr>
				<td>
					<display:table name="listaW9SelezionePartecipanti" id="impresa" class="datilista" sort="list" pagesize="20" requestURI="ListaW9SelezionePartecipanti.do" >
						
						<display:column title="Seleziona<br>${titoloMenu}" style="width:50px">
							<input type="checkbox" name="keys" id="" value="${codGara};${numAppa};${codLott};${impresa.numeroImpresa};${impresa.numRaggruppamento}"
							onChange='javascript:multipleCheck();' />
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

			<tr>
				<td class="comandi-dettaglio" colSpan="2">	
					<INPUT type="button" class="bottone-azione" value="Seleziona" title="Seleziona" onclick='javascript:listaInserisciSelezione();'>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
			<input type="hidden" name="metodo" id="metodo" value="eliminaSelez" >
			<input type="hidden" name="key" id="key" value="${key}" >
			
	</form>

			<%/* Inserimento valori per aggirare limitazione di geneweb nella gestione scheda innestata */%>
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
	
	<gene:javaScript>
		document.forms[0].action=document.forms[0].action + "?numImpr=${numImpr}&id_gruppo=${id_gruppo}";	
		
		function removeLinkList(){
				$('#impresa a').contents().unwrap();
		}
		window.onload = removeLinkList;
		
		// nel caso non stia elaborando un accordo quadro, 
		// la selezione delle ditte partecipanti deve permettere una sola scelta
		function multipleCheck(){
			<c:if test="${empty isAccordoQuadro}" >
				var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);

				for (var i = 0; i < document.forms[0].keys.length; i++){
					if(document.forms[0].keys[i].checked || numeroOggetti == 0){
						document.forms[0].keys[i].disabled=false;
					}
					else{
						document.forms[0].keys[i].disabled=true;
					}
				}
			</c:if>
		}
		
		// in inserimento devo forzare l'annulla per tornare alla scheda precedente senza errori
		<c:if test='${param.noBackButton eq 1}'>
			function schedaAnnulla(){
					bloccaRichiesteServer();
					// torno alla lista
					historyVaiIndietroDi(0);
			}
		</c:if>
	
		// sovrascrivo il metodo torna indietro per non finire in selezione fase
		var comeBack = historyVaiIndietroDi;
		historyVaiIndietroDi = function(value) {
			comeBack(0);
		}
	
		function visualizzaImpresaScheda(codiceImpresa){
			document.formVisualizzaImprese.archValueChiave.value = "T:" + codiceImpresa;
			document.formVisualizzaImprese.submit(false);
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

		function listaInserisciSelezione() {
			var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		  if (numeroOggetti == 0) {
	      alert("Nessun elemento selezionato nella lista");
		  } else {
	   	  if (confirm("Sono stati selezionati " + numeroOggetti + " elementi. Procedere con l'inserimento?")) {
	   	  	bloccaRichiesteServer();
					document.forms[0].metodo.value = "inserisciSelez";
					document.forms[0].submit();
				}
			}
		}
	
		<c:if test='${not empty RISULTATO and RISULTATO eq "OK"}' >
			bloccaRichiesteServer();
			// torno alla lista
			historyVaiIndietroDi(0);
		</c:if>
		
	</gene:javaScript>
</gene:template>

