
<%
	/*
	 * Created on 18-ott-2006
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

	// PAGINA CHE CONTIENE L'ISTANZA DELLA SOTTOPARTE DELLA PAGINA DI LISTA GRUPPI 
	// CONTENENTE LA EFFETTIVA LISTA DEI GRUPPI E LE RELATIVE FUNZIONALITA'
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<c:set var="listaApriInModifica" value='${param.updateLista}' />

<c:choose>
	<c:when test="${listaApriInModifica eq 1}">
		<c:set var="isNavigazioneDisabilitata" value='1' scope="request" />
		<c:set var="listaLottiPubb"
			value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.GetListaLottiPubblicazioneFunction", pageContext,codiceGara,numeroPubblicazione,tipoPubblicazione)}' />
		<c:set var="wherePubblicazioniLotti" value="CODGARA = ${codiceGara}" />
	</c:when>
	<c:otherwise>
		<c:set var="wherePubblicazioniLotti"
			value="W9LOTT.CODGARA = ${codiceGara} AND W9LOTT.CODLOTT IN (SELECT CODLOTT FROM W9PUBLOTTO where W9PUBLOTTO.CODGARA=${codiceGara} AND W9PUBLOTTO.NUM_PUBB = ${numeroPubblicazione})" />
	</c:otherwise>
</c:choose>

<gene:set name="titoloMenu" scope="requestScope">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td>
			<gene:formLista entita="W9LOTT" pagesize="0" tableclass="datilista"
				gestisciProtezioni="true" where="${wherePubblicazioniLotti}"
				sortColumn='2' gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9LOTTIPUBBLICAZIONI">

				<gene:redefineInsert name="addToAzioni" >
					<tr>
						<td class="vocemenulaterale" >
							<a href="javascript:modificaAssociazioni();" title="Modifica Associazioni" tabindex="1503">Modifica Associazioni</a>
						</td>
					</tr>					
				</gene:redefineInsert>

				<input type="hidden" name="tipoPubblicazione" value='${tipoPubblicazione}' />
				<input type="hidden" name="modoVisualizzazioneLista" id="modoVisualizzazioneLista" value="0" />
				<input type="hidden" name="codiceGara" id="codiceGara" value="${codiceGara}" />
				<input type="hidden" name="numeroPubblicazione" id="numeroPubblicazione" value="${numeroPubblicazione}" />

				<c:if test="${listaApriInModifica eq 1}">
					<gene:campoLista title="Associato<center>${titoloMenu}</center>"
						visibile="true">
						<input type="checkbox" class="keysPubbLott" name="keys" value="${chiaveRiga}" 

						<c:if test='${! empty listaLottiPubblicazioneEstratti[gene:getValCampo(chiaveRiga,"W9LOTT.CODLOTT")]}'>
							checked="checked"
						</c:if>
						<c:if test='${empty listaLottiPubblicazioneAbilitati[gene:getValCampo(chiaveRiga,"W9LOTT.CODLOTT")]}'>
							disabled="disabled"
						</c:if>
						>
					</gene:campoLista>
				</c:if>

				<!-- Campi per la gestione -->
				<gene:campoLista campo="CODLOTT" edit="true" visibile="false" />
				<gene:campoLista campo="CODGARA" edit="true" visibile="false" />
				<gene:campoLista campo="NUM_PUBB" entita="W9PUBLOTTO"
					where="W9LOTT.CODGARA = W9PUBLOTTO.CODGARA" edit="true"
					visibile="false" />

				<!-- Campi veri e propri -->
				<gene:campoLista campo="CIG" headerClass="sortable" />
				<gene:campoLista campo="OGGETTO" headerClass="sortable" />
				<gene:campoLista campo="IMPORTO_LOTTO" headerClass="sortable" />


				<gene:redefineInsert name="addPulsanti">

					<c:choose>
						<c:when test="${listaApriInModifica eq 1}">
							<c:if test='true'>
								<INPUT type="button" class="bottone-azione" title='Salva'
									value="Salva" onclick="javascript:listaConferma();">
							</c:if>
							<c:if test='true'>
								<INPUT type="button" class="bottone-azione" title='Annulla'
									value="Annulla" onclick="javascript:listaAnnullaModifica();">
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test='true'>
								<INPUT type="button" class="bottone-azione"
									title='Modifica Associazioni' value="Modifica Associazioni"
									onclick="javascript:modificaAssociazioni();">
							</c:if>
						</c:otherwise>
					</c:choose>

					<gene:redefineInsert name="listaNuovo" />
					<gene:redefineInsert name="listaEliminaSelezione" />
					<gene:redefineInsert name="pulsanteListaInserisci" />
					<gene:redefineInsert name="pulsanteListaEliminaSelezione" />

				</gene:redefineInsert>


			</gene:formLista></td>
	</tr>

	<jsp:include page="/WEB-INF/pages/commons/pulsantiListaPage.jsp" />

</table>
<gene:javaScript>
	function modificaAssociazioni(){
		listaApriInModifica();
	}

 	var salva = listaConferma;
 	
 	listaConferma = function (){
 		var numberOfChecked = $('.keysPubbLott:checkbox:checked').length;
		if (numberOfChecked > 0){
			salva();	}
		else {
			alert("Non e' stato selezionato alcun lotto. E' necessario selezionare almeno un lotto da associare all'atto prima di effettuare il salvataggio.");
		}
 	}

</gene:javaScript>




