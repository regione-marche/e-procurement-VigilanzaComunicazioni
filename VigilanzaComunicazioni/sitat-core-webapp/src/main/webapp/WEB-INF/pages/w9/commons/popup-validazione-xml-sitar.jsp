
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


<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<c:set var="key" value="${param.key}" />
	<gene:redefineInsert name="corpo">
		<table class="lista">
				<c:set var="stato" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GestioneValidazioneXMLSitarFunction",pageContext, key)}'/>
				<gene:setString name="titoloMaschera" value='Controlli preliminari ${titolo}' />
				<gene:set name="titoloGenerico" value="" />
				<gene:set name="listaGenericaControlli" value="${listaControlli}" />
				<gene:set name="numeroErrori" value="${numeroErrori}" />
				<gene:set name="numeroWarning" value="${numeroWarning}" />
				
				<c:choose>
		
				<c:when test="${!empty listaControlli}">
					<jsp:include page="../commons/popup-validazione-interno.jsp" />	
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
				
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</td>
				</tr>

		</table>

	</gene:redefineInsert>
	<gene:javaScript>
	
		window.opener.currentPopUp = null;
	    window.onfocus = resettaCurrentPopup;
	
		function resettaCurrentPopup() {
			window.opener.currentPopUp=null;
		}
		
		function annulla() {
			window.close();
		}

	</gene:javaScript>
</gene:template>

</div>
