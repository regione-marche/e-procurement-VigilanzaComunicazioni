
<%
	/*
	 * Created on 15-lug-2008
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>

<!-- Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI). -->

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<c:set var="fabbisogni" value="${param.fabbisogni}" />
	<c:set var="codFunzione" value="${param.codFunzione}" />
	<c:set var="invio" value="${param.invio}" />	
	<gene:setString name="titoloMaschera" value='Validazione Fabbisogni'/>
	
	
	<gene:redefineInsert name="corpo">
		<table class="lista">
			
			<c:set var="stato" value='${gene:callFunction3("it.eldasoft.sil.pt.tags.funzioni.GestioneValidazioneFabbisogniFunction",pageContext, fabbisogni, codFunzione)}'/>		

			<gene:set name="titoloGenerico" value="${titolo}" />
			<gene:set name="listaGenericaControlli" value="${listaControlli}" />
			<gene:set name="numeroErrori" value="${numeroErrori}" />
			<gene:set name="numeroWarning" value="${numeroWarning}" />
			
			<c:choose>
				<c:when test="${!empty listaControlli and !empty controlliSuBean}">
					<jsp:include page="../commons/popup-validazione-fabbisogni-interno-bean.jsp" />	
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<b>${titoloGenerico}</b>
							<br>&nbsp;<br>
							<b>Non &egrave; stato rilevato alcun problema.</b>
							<br>&nbsp;<br>				
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
				
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
				
					<c:if test='${numeroErrori eq 0 && !empty invio}'>
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:conferma()">						
					</c:if>

					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
			
		</table>

	</gene:redefineInsert>
	<gene:javaScript>
	
		//window.opener.currentPopUp = null;
	    //window.onfocus = resettaCurrentPopup;
	
		//function resettaCurrentPopup() {
		//	window.opener.currentPopUp=null;
		//}
		
		function annulla() {
			window.close();
		}
	
		function controlla() {
			window.location.reload();
		}
		
		
		
		function conferma(){
			window.close();
			window.opener.schedaConferma();
				
		}
	
		
	</gene:javaScript>
</gene:template>

</div>
