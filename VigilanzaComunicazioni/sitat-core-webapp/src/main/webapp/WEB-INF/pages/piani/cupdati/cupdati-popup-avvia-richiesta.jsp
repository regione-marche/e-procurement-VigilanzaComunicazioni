
<%
	/*
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

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div style="width:97%;">

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	<gene:setString name="titoloMaschera"  value='Richiesta di generazione del codice CUP'  />
	
	<c:set var="id" value="${param.id}" />
	
	<gene:redefineInsert name="corpo">
		<table class="dettaglio-notab">
			<tr>
				<td class="valore-dato" colspan="2">
					<br>
					Questa funzione <b>prepara i dati e li invia al CIPE</b> per la <b>richiesta di generazione del codice CUP.</b>
					<br>
					<br>
					Tale richiesta, tuttavia, &egrave; possibile solamente se tutti i dati inseriti 
					superano i controlli di validit&agrave;.
					<br>
					<br>
				</td>
			</tr>

			<tr>
				<td colspan="2" class="comandi-dettaglio">
					<INPUT type="button" class="bottone-azione" value="Controlla ed invia i dati" title="Controlla ed invia i dati" onclick="javascript:avviaRichiestaCUP(${id});">
					<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</gene:redefineInsert>
	
	<gene:javaScript>
		document.forms[0].jspPathTo.value="piani/cupdati/cupdati-popup-avvia-richiesta.jsp";
		
		function annulla(){
			window.close();
		}
		
		function avviaRichiestaCUP(id){
			var action = "${pageContext.request.contextPath}/piani/AvviaRichiestaCUP.do";
 			document.location.href=action+'?' + csrfToken + '&id=' + id;
		}
	
	</gene:javaScript>	
</gene:template>

</div>

