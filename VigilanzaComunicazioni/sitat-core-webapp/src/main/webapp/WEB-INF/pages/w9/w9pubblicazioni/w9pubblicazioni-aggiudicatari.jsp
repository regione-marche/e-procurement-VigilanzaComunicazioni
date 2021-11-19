
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>

<c:set var="filtroUffint" value=""/> 
<c:if test="${! empty sessionScope.uffint && fn:contains(archiviFiltrati,'IMPR')}">
	<c:set var="filtroUffint" value="IMPR.CGENIMP = '${sessionScope.uffint}'"/>
</c:if>

<div style="width: 100%;">
	
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
	<c:set var="numeroPubblicazione" value='${gene:getValCampo(key,"NUM_PUBB")}' />
	
	<c:set var="dataAggiudicatari" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.EsitiAggiudicatariFunction", pageContext, key)}' />
	<c:set var="isW9FasePartecipante" value='${requestScope.isFasePartecipante}' />
	
	<c:if test="${empty isAccordoQuadro}">
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="listaEliminaSelezione" />
	</c:if>
	
	<table class="dettaglio-tab-lista">
		<tr>
			<td>
				<%/* uso del formlista dipedente dalla callfunction a 2 parametri per ottenere struttura personalizzata in visualizzazione*/%>
				<gene:formLista pagesize="1000" tableclass="datilista" gestisciProtezioni="false" sortColumn="2;3"
						varName="risultatoListaAggiudicatari" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreESITI_AGGIUDICATARI">

					<gene:campoLista title="Opzioni	&nbsp;" width="50" >
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
								<gene:PopUpItem title="Visualizza" href="javascript:visualizza('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');" />
								<gene:PopUpItem title="Modifica" href="javascript:modifica('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');" />
								<gene:PopUpItem title="Elimina" href="javascript:elimina('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');" />
							</gene:PopUp>
						</c:if>						
						<elda:linkPopupRecord idRecord="${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}"
							contextPath="${pageContext.request.contextPath}" />	&nbsp;
						<c:choose>
							<c:when test="${! empty isAccordoQuadro}">
								<input type="checkbox" name="keys" id="" value="W9FASI.FASE_ESECUZIONE=N:${flusso};ESITI_AGGIUDICATARI.CODGARA=N:${codgara};ESITI_AGGIUDICATARI.NUM_PUBB=N:${numeroPubblicazione};ESITI_AGGIUDICATARI.NUM_AGGI=N:${datiRiga.OBJ3};ESITI_AGGIUDICATARI.ID_GRUPPO=N:${datiRiga.OBJ4};" />
							</c:when>
							<c:otherwise>
								<input type="hidden" name="keys" id="" value="W9FASI.FASE_ESECUZIONE=N:${flusso};ESITI_AGGIUDICATARI.CODGARA=N:${codgara};ESITI_AGGIUDICATARI.NUM_PUBB=N:${numeroPubblicazione};ESITI_AGGIUDICATARI.NUM_AGGI=N:${datiRiga.OBJ3};ESITI_AGGIUDICATARI.ID_GRUPPO=N:${datiRiga.OBJ4};" />
							</c:otherwise>
						</c:choose>	
					</gene:campoLista>			
					
					<%/* SCHEMA DATI ESTRATTI :			*/%>
					<%/* 	C01 codiceGara  			*/%>
					<%/* 	C02 numeroPubblicazione		*/%>
					<%/* 	C03 numeroAggiudicataria 	*/%>
					<%/* 	C04 idGruppo 				*/%>
					<%/* 	C05 identificativoImpresa 	*/%>
					<%/* 	C06 tipoAggiudicataria 		*/%>
					<%/* 	C07 descTipoImpresa 		*/%>
					<%/* 	C08 nomeEstesoImpr 			*/%>
					<%/* 	C09 numeroImpreseGruppo 	*/%>

					<gene:campoLista title="Id. Impresa" campo="C05" definizione="T4;0" campoFittizio="true">
						<a href='javascript:visualizza("${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}");'>
							${datiRiga.OBJ5}
						</a>
					</gene:campoLista>
					<gene:campoLista title="Descr. Tipo Impresa" campo="C07" definizione="T40;0" />
					<gene:campoLista title="Nome Impresa" campo="C08" definizione="T2000;0" campoFittizio="true">
						${datiRiga.OBJ8}
					</gene:campoLista>
				</gene:formLista>
			</td>
		</tr>

		<tr>
			<td class="comandi-dettaglio" colSpan="2">	

				<%/*inserimento pulsanti scheda in base alla tipologia di gara */%>
				<c:if test="${fn:length(risultatoListaAggiudicatari) eq 0 || ! empty isAccordoQuadro}">
					<%/* se non presente alcun un record o accordo quadro */%>
					<INPUT type="button" class="bottone-azione" value="Aggiungi" title="Aggiungi" onclick='javascript:nuovo();'>&nbsp;&nbsp;
				</c:if>
			
				<c:if test="${fn:length(risultatoListaAggiudicatari) > 0}">
					<c:choose>
						<c:when test="${! empty isAccordoQuadro}">
							<%/* se accordo quadro */%>
							<INPUT type="button" class="bottone-azione" value="Elimina Selezionati" title="Elimina Selezionati" onclick='javascript:listaEliminaSelezione();'>&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<%/* se non vuoto */%>
							<INPUT type="button" class="bottone-azione" value="Modifica" title="Modifica" onclick="javascript:modifica('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');">&nbsp;&nbsp;
							<INPUT type="button" class="bottone-azione" value="Elimina" title="Elimina" onclick="javascript:elimina('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');">&nbsp;&nbsp;	
						</c:otherwise>
					</c:choose>
				</c:if>
			</td>
		</tr>
	</table>

	<c:if test="${true or fn:length(risultatoListaAggiudicatari) > 0}">
		<gene:redefineInsert name="addToAzioni" >
			<c:choose>
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
							<a href="javascript:modifica('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');" title="Modifica" tabindex="1503">Modifica</a>
						</td>
					</tr>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:elimina('${codgara};${numeroPubblicazione};${datiRiga.OBJ3};${datiRiga.OBJ4}');" title="Elimina" tabindex="1504">Elimina</a>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</gene:redefineInsert>
	</c:if>


	<%/* se non presente alcun un record o accordo quadro */%>
	<c:if test="${fn:length(risultatoListaAggiudicatari) eq 0 || ! empty isAccordoQuadro}">
		<gene:redefineInsert name="addToAzioni" >
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
			var numeroPubblicazione = arrayChiave[1];
			var numImpresa  = arrayChiave[2];
			var idGruppo  = arrayChiave[3];
			
			var tempKey = document.forms[0].keyParent.value ;
			//alert("tempKey = " + tempKey);
			if ("" == idGruppo) {
				// Apertura dettaglio di una impresa singola
				var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=VISUALIZZA&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			} else {
				// Apertura dettaglio di un gruppo di imprese
				var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=VISUALIZZA&isW9FasePartecipante=" + isW9FasePartecipante + "&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
	
		function modifica(chiave){
				
			var arrayChiave = chiave.split(";");
			
			var codiceGara  = arrayChiave[0];
			var numeroPubblicazione = arrayChiave[1];
			var numImpresa  = arrayChiave[2];
			var idGruppo  = arrayChiave[3];

			var tempKey = document.forms[0].keyParent.value ;
			if ("" == idGruppo) {
				// Modifica dettaglio di una impresa singola
				var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=MODIFICA&operazioneModificaAggi=1&numImpr=" + numImpresa + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			} else {
				// Modifica dettaglio di un gruppo di imprese
				var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=MODIFICA&operazioneModificaAggi=1&numImpr=" + numImpresa + "&id_gruppo=" + idGruppo + "&key=" + tempKey;
				document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
			}
		}
				
		function nuovo(){
			var isW9FasePartecipante = "${isW9FasePartecipante}";
		
			var isAccordoQuadro = "${isAccordoQuadro}";
			var codiceGara  = ${codgara};
			var numeroPubblicazione = ${numeroPubblicazione};
		
			var tempKey = document.forms[0].keyParent.value ;
			var href = "href=w9/esiti/aggiudicatari-imprese-scheda.jsp&modo=MODIFICA&key=" + tempKey + "&flusso=${flusso}";
			document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
		}
		
		function listaNuovo(){
			nuovo();
		}
		
		function elimina(chiave) {
			
			<c:set var="localkey" value='${codgara};${numeroPubblicazione};1' scope="request" />
			
			if (confirm("Procedere con l'eliminazione?")) {
				bloccaRichiesteServer();
				
				var arrayChiave = chiave.split(";");
			
				var codiceGara  = arrayChiave[0];
				var numeroPubblicazione = arrayChiave[1];
				var numImpresa  = arrayChiave[2];
				var idGruppo  = arrayChiave[3]
				
				var key = "ESITI_AGGIUDICATARI.CODGARA=N:" + codiceGara + ";ESITI_AGGIUDICATARI.NUM_PUBB=N:" + numeroPubblicazione + ";ESITI_AGGIUDICATARI.NUM_AGGI=N:" + numImpresa + ";ESITI_AGGIUDICATARI.ID_GRUPPO=N:" + idGruppo;
									
				document.forms[0].key.value=key;
				document.forms[0].metodo.value="elimina";
				document.forms[0].submit();
			}
		}

		function listaEliminaSelezione() {
			var flusso = "${flusso}";
			<c:set var="localkey" value='${codgara};${numeroPubblicazione};1' scope="request" />
			
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

	</gene:javaScript>
</div>
