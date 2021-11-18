<%
/*
 * Created on: 03/09/2012
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<gene:template file="scheda-template.jsp">
<c:set var="key" value='${param.key}' scope="request" />
<gene:setString name="titoloMaschera" value='Scheda già inviata' />

<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >

</gene:redefineInsert>

<gene:redefineInsert name="corpo">

	<table class="dettaglio-notab">
		<tr>
			<td class="valore-dato" colspan="2">
			<br>
			<b>Con questa operazione verrà avviata una richiesta di annullamento dell'invio della scheda. Continuare?
			<br><br>
			</td>
		</tr>		
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:historyVaiIndietroDi(0);"/>&nbsp;&nbsp;
				<input type="button" value="Si prosegui" title="Si prosegui" class="bottone-azione" onclick="javascript:paginaEliminaFase();"/>&nbsp;
			</td>
		</tr>
	</table>

</gene:redefineInsert>

<gene:javaScript>

	function paginaEliminaFase() {
		var href = "href=w9/w9flussi/w9flussi-scheda.jsp&modo=NUOVO&key=${key}&cancella=si";
		document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
	}

</gene:javaScript>

</gene:template>

