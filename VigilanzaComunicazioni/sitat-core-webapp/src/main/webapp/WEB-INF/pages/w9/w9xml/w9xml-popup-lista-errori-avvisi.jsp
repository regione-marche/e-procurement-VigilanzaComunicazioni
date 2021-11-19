
<%
	/*
	 * Created on 04-Nov-2008
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

	// Scheda degli intestatari della concessione stradale
%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>


<div style="width:97%;">

<gene:template file="popup-template.jsp">
	
	<c:set var="codgara" value="${param.codgara}" />
	<c:set var="codlott" value="${param.codlott}" />
	<c:set var="numxml" value="${param.numxml}" />

	<gene:setString name="titoloMaschera"  value='Esportazione n° ${numxml}' />

	<gene:redefineInsert name="corpo">
		<c:set var="modo" value="MODIFICA" scope="request" />
		
		<gene:formScheda entita="W9XML" where="W9XML.CODGARA = ${codgara} AND W9XML.CODLOTT = ${codlott} AND W9XML.NUMXML = ${numxml}" >
			
			<gene:campoScheda>
				<td colSpan="2">
					In questa pagina &egrave; possibile consultare l'esito dell'elaborazione eseguita dall'ANAC
					sui dati del contratto.
					<br>&nbsp;
					<br>
				</td>
			</gene:campoScheda>
			<gene:campoScheda campo="CODGARA" modificabile="false" />
			<gene:campoScheda campo="CODLOTT" modificabile="false" /> 
			<gene:campoScheda campo="NUMXML" title="N° esportazione" modificabile="false"/>
			<gene:campoScheda campo="CIG"  modificabile="false" entita="W9LOTT" where="W9LOTT.CODGARA = W9XML.CODGARA AND W9LOTT.CODLOTT = W9XML.CODLOTT" />
			<gene:campoScheda campo="DATA_EXPORT" modificabile="false" />
			<gene:campoScheda campo="DATA_INVIO" modificabile="false"/>
			<gene:campoScheda campo="DATA_ELABORAZIONE" modificabile="false"/>
			<gene:campoScheda campo="DATA_FEEDBACK" modificabile="false"/>
	
		</gene:formScheda>
			<table class="lista">
			<tr>
				<td>
					<br>&nbsp;<br>
					<b>Lista degli errori ed avvisi</b>
				</td>
			</tr>
			
			<tr>
				<td><gene:formLista entita="W9XMLANOM"
					where="W9XMLANOM.CODGARA = ${codgara} AND W9XMLANOM.CODLOTT = ${codlott} AND W9XMLANOM.NUMXML = ${numxml}"
					tableclass="datilista"
					sortColumn="1">
					<gene:campoLista campo="CODGARA" visibile="false" />
					<gene:campoLista campo="CODLOTT" visibile="false" /> 
					<gene:campoLista campo="NUMXML" visibile="false" /> 
					<gene:campoLista campo="NUM" visibile="false"/>
					<gene:campoLista campo="LIVELLO" visibile="false"/>
					<gene:campoLista title="" width="22">
						<c:choose>
							<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'ERRORE'}">
								<img width="20" height="20" title="Errore" alt="Errore" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
							</c:when>
							<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'ATTENZIONE'}">
								<img width="20" height="20" title="Attenzione" alt="Attenzione" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
							</c:when>
							<c:when test="${datiRiga.W9XMLANOM_LIVELLO == 'AVVISO'}">
								<img width="20" height="20" title="Avviso" alt="Avviso" src="${pageContext.request.contextPath}/img/controllo_i.gif"/>	
							</c:when>
						</c:choose>
					</gene:campoLista>
					<gene:campoLista campo="CODICE" />
					<gene:campoLista campo="DESCRIZIONE" />
					<gene:campoLista campo="ELEMENTO" visibile="false"/>
				</gene:formLista></td>
			</tr>
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla();">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		
		
	</gene:redefineInsert>

	<gene:javaScript>
	
		showObj("jsPopUpW9XML_NUMXML", false);
		showObj("jsPopUpW9XML_DATA_EXPORT", false);
		showObj("jsPopUpW9XML_DATA_INVIO", false);
		showObj("jsPopUpW9XML_DATA_ELABORAZIONE", false);
		showObj("jsPopUpW9XML_DATA_FEEDBACK", false);
		
		window.opener.currentPopUp=null;

	    window.onfocus=resettaCurrentPopup;

		function resettaCurrentPopup() {
			window.opener.currentPopUp=null;
		}
		
		function annulla(){
			window.close();
		}
		
	
	</gene:javaScript>	
</gene:template>

</div>

