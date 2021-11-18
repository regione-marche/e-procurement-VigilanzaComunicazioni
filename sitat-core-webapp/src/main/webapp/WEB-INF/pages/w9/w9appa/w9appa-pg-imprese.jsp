
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div style="width: 100%;">
	
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	<c:set var="numappa" value='${gene:getValCampo(key,"NUM_APPA")}' />

	<c:set var="dataW9Aggi" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.W9AppaPgImpreseFunction", pageContext, key)}' />
	<c:set var="isW9FasePartecipante" value='${requestScope.isFasePartecipante}' />
	
	<c:if test="${empty isAccordoQuadro || aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}">
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="listaEliminaSelezione" />
	</c:if>

<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>

<c:set var="filtroUffint" value=""/> 
<c:if test="${! empty sessionScope.uffint && fn:contains(archiviFiltrati,'IMPR')}">
	<c:set var="filtroUffint" value=" IMPR.CGENIMP = '${sessionScope.uffint}'"/>
</c:if>
	
	<table class="dettaglio-tab-lista">
		<tr>
			<td>
				<%/* uso del formlista dipedente dalla callfunction a 2 parametri per ottenere struttura personalizzata in visualizzazione*/%>
				<gene:formLista pagesize="1000" tableclass="datilista" gestisciProtezioni="false" sortColumn="2;3"
						varName="risultatoListaW9Aggiudicatarie" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9AGGI">

					<gene:campoLista title="Opzioni	&nbsp;" width="50" >
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
								<gene:PopUpItem title="Visualizza" href="javascript:visualizza('${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');" />
								<c:if test="${aggiudicazioneSelezionata eq ultimaAggiudicazione and !isMonitoraggioMultilotto}">
									<gene:PopUpItem title="Modifica" href="javascript:modifica('${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');" />
									<gene:PopUpItem title="Elimina" href="javascript:elimina('${flusso};${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');" />
								</c:if>
							</gene:PopUp>
						</c:if>						
						<elda:linkPopupRecord idRecord="${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}"
							contextPath="${pageContext.request.contextPath}" />	&nbsp;
						<c:choose>
							<c:when test="${! empty isAccordoQuadro}">
								<input type="checkbox" name="keys" id="" value="W9FASI.FASE_ESECUZIONE=N:${flusso};W9AGGI.CODGARA=N:${codgara};W9AGGI.NUM_APPA=N:${numappa};W9AGGI.CODLOTT=N:${codlott};W9AGGI.NUM_AGGI=N:${datiRiga.OBJ4};W9AGGI.ID_GRUPPO=N:${datiRiga.OBJ5};" />
							</c:when>
							<c:otherwise>
								<input type="hidden" name="keys" id="" value="W9FASI.FASE_ESECUZIONE=N:${flusso};W9AGGI.CODGARA=N:${codgara};W9AGGI.NUM_APPA=N:${numappa};W9AGGI.CODLOTT=N:${codlott};W9AGGI.NUM_AGGI=N:${datiRiga.OBJ4};W9AGGI.ID_GRUPPO=N:${datiRiga.OBJ5};" />
							</c:otherwise>
						</c:choose>	
					</gene:campoLista>			
					
					<%/* SCHEMA DATI ESTRATTI :			*/%>
					<%/* 	C01 codiceGara  			*/%>
					<%/* 	C02 numAppalto 				*/%>
					<%/* 	C03 codiceLotto 			*/%>
					<%/* 	C04 numeroAggiudicataria 	*/%>
					<%/* 	C05 idGruppo 				*/%>
					<%/* 	C06 identificativoImpresa 	*/%>
					<%/* 	C07 tipoAggiudicataria 		*/%>
					<%/* 	C08 descTipoImpresa 		*/%>
					<%/* 	C09 nomeEstesoImpr 			*/%>
					<%/* 	C10 numeroImpreseGruppo 	*/%>

					<gene:campoLista title="Id. Impresa" campo="C06" definizione="T4;0" campoFittizio="true">
						<a href='javascript:visualizza("${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}");'>
							${datiRiga.OBJ6}
						</a>
					</gene:campoLista>
					<gene:campoLista title="Descr. Tipo Impresa" campo="C08" definizione="T40;0" />
					<gene:campoLista title="Nome Impresa" campo="C09" definizione="T2000;0" campoFittizio="true">
						${datiRiga.OBJ9}
					</gene:campoLista>
				</gene:formLista>
			</td>
		</tr>

		<tr>
			<td class="comandi-dettaglio" colSpan="2">	

				<%/*inserimento pulsanti scheda in base alla tipologia di gara */%>
				<c:if test="${(fn:length(risultatoListaW9Aggiudicatarie) eq 0 || ! empty isAccordoQuadro) && aggiudicazioneSelezionata eq ultimaAggiudicazione && !isMonitoraggioMultilotto}">
					<%/* se non presente alcun un record o accordo quadro */%>
					<INPUT type="button" class="bottone-azione" value="Aggiungi" title="Aggiungi" onclick='javascript:nuovo();'>&nbsp;&nbsp;
				</c:if>
			
				<c:if test="${fn:length(risultatoListaW9Aggiudicatarie) > 0}">
					<c:choose>
						<c:when test="${aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}">
							&nbsp;&nbsp;
						</c:when>
						<c:when test="${! empty isAccordoQuadro}">
							<%/* se accordo quadro */%>
							<INPUT type="button" class="bottone-azione" value="Elimina Selezionati" title="Elimina Selezionati" onclick='javascript:listaEliminaSelezione();'>&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<%/* se non vuoto */%>
							<INPUT type="button" class="bottone-azione" value="Modifica" title="Modifica" onclick="javascript:modifica('${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');">&nbsp;&nbsp;
							<INPUT type="button" class="bottone-azione" value="Elimina" title="Elimina" onclick="javascript:elimina('${flusso};${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');">&nbsp;&nbsp;	
						</c:otherwise>
					</c:choose>
				</c:if>
			</td>
		</tr>
	</table>

	<c:if test="${true or fn:length(risultatoListaW9Aggiudicatarie) > 0}">
		<gene:redefineInsert name="listaEliminaSelezione" >
			<c:choose>
				<c:when test="${aggiudicazioneSelezionata ne ultimaAggiudicazione || isMonitoraggioMultilotto}">
					
				</c:when>
				<c:when test="${! empty isAccordoQuadro}">
					<%/* se accordo quadro */%>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:listaEliminaSelezione();" title="Elimina Selezionati" tabindex="1503">Elimina Selezionati</a>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
				<%/* se non vuoto */%>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:modifica('${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');" title="Modifica" tabindex="1503">Modifica</a>
						</td>
					</tr>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:elimina('${flusso};${codgara};${numappa};${codlott};${datiRiga.OBJ4};${datiRiga.OBJ5}');" title="Elimina" tabindex="1504">Elimina</a>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</gene:redefineInsert>
	</c:if>


	<%/* se non presente alcun un record o accordo quadro */%>
	<c:if test="${(fn:length(risultatoListaW9Aggiudicatarie) eq 0 || ! empty isAccordoQuadro) && aggiudicazioneSelezionata eq ultimaAggiudicazione && !isMonitoraggioMultilotto}">
		<gene:redefineInsert name="listaNuovo" >
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:nuovo();" title="Aggiungi" tabindex="1502">Aggiungi</a>
				</td>
			</tr>
		</gene:redefineInsert>
	</c:if>


	<%/* Inserimento valori per aggirare limitazione di geneweb nella gestione scheda innestata  (visualizzaimpresa) */%>
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

	<gene:javaScript>
		function visualizzaImpresaScheda(codiceImpresa){
			document.formVisualizzaImprese.archValueChiave.value = "T:" + codiceImpresa;
			document.formVisualizzaImprese.submit(false);
		}
	
		function visualizza(chiave) {
			var arrayChiave = chiave.split(";");
			//alert("chiave.length = " + arrayChiave.length);

			var isW9FasePartecipante = 0;
			if ("${isW9FasePartecipante}" != ""){
				isW9FasePartecipante = 1;
			}
			
			var codiceGara  = arrayChiave[0];
			var numeroAppalto = arrayChiave[1];
			var codiceLotto = arrayChiave[2];
			var numImpresa  = arrayChiave[3];
			var idGruppo  = arrayChiave[4];
			
			var tempKey = document.forms[0].keyParent.value ;
			//alert("tempKey = " + tempKey);
			if ("" == idGruppo) {
				// Apertura dettaglio di una impresa singola
				var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=VISUALIZZA&isAccordoQuadro=${isAccordoQuadro}&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			} else {
				// Apertura dettaglio di un gruppo di imprese
				var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=VISUALIZZA&isAccordoQuadro=${isAccordoQuadro}&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
	
		function modifica(chiave){
				
			var arrayChiave = chiave.split(";");
			
			var codiceGara  = arrayChiave[0];
			var numeroAppalto = arrayChiave[1];
			var codiceLotto = arrayChiave[2];
			var numImpresa  = arrayChiave[3];
			var idGruppo  = arrayChiave[4];

			var tempKey = document.forms[0].keyParent.value ;
			if ("" == idGruppo) {
				// Modifica dettaglio di una impresa singola
				var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=MODIFICA&isAccordoQuadro=${isAccordoQuadro}&operazioneModificaAggi=1&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			} else {
				// Modifica dettaglio di un gruppo di imprese
				var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=MODIFICA&isAccordoQuadro=${isAccordoQuadro}&operazioneModificaAggi=1&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
				
		function nuovo(){
			var isW9FasePartecipante = "${isW9FasePartecipante}";
		
			var isAccordoQuadro = "${isAccordoQuadro}";
			var codiceGara  = ${codgara};
			var numeroAppalto = ${numappa};
			var codiceLotto = ${codlott};
		
			// se la lista e' vuota ed e' presente la fase di partecipazione, per il primo inserimento entro nella shcermata di selezione delle imprese partecipanti
			if (isW9FasePartecipante != null && isW9FasePartecipante != "" && ${fn:length(risultatoListaW9Aggiudicatarie)} == 0){
				var tempKey = document.forms[0].keyParent.value ;
				var href = "metodo=visualizza&isAccordoQuadro=" + isAccordoQuadro + "&key=" + tempKey + "&codGara=" + codiceGara + "&numAppa=" + numeroAppalto + "&codLott=" + codiceLotto;
				document.location.href = "${pageContext.request.contextPath}/w9/ListaW9SelezionePartecipanti.do?" + csrfToken + "&" + href;
			} else {
				var tempKey = document.forms[0].keyParent.value ;
				var href = "href=w9/w9appa/w9appa-pg-imprese-scheda.jsp&modo=MODIFICA&isAccordoQuadro=${isAccordoQuadro}&key=" + tempKey + "&flusso=${flusso}";
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
		
		function listaNuovo(){
			nuovo();
		}
		
		function elimina(chiave) {
			
			<c:set var="localkey" value='${codgara};${codlott};${flusso};${numappa};1' scope="request" />
			<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
		
			if (confirm("Procedere con l'eliminazione?")) {
				bloccaRichiesteServer();
				
				var arrayChiave = chiave.split(";");
			
				var flusso = arrayChiave[0];
				var codiceGara  = arrayChiave[1];
				var numeroAppalto = arrayChiave[2];
				var codiceLotto = arrayChiave[3];
				var numImpresa  = arrayChiave[4];
				var idGruppo  = arrayChiave[5]
				
				var key = "W9FASI.FASE_ESECUZIONE=N:" + flusso + ";W9AGGI.CODGARA=N:" + codiceGara + ";W9AGGI.NUM_APPA=N:" + numeroAppalto + ";W9AGGI.CODLOTT=N:" + codiceLotto + ";W9AGGI.NUM_AGGI=N:" + numImpresa + ";W9AGGI.ID_GRUPPO=N:" + idGruppo;
									
				document.forms[0].key.value=key;
				document.forms[0].metodo.value="elimina";
				document.forms[0].submit();
			}
		}

		function listaEliminaSelezione() {
			var flusso = "${flusso}";
			<c:set var="localkey" value='${codgara};${codlott};${flusso};${numappa};1' scope="request" />
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

		function selezionaCodiceCUP(codiceCUP) {
			opener.setValue("${campoCUP}",codiceCUP);
			window.close();
		}
	</gene:javaScript>
</div>
