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
		<c:set var="progressivoRiaggiudicazione" value="${param.progressivoRiaggiudicazione}" />
		<c:set var="keyatt" value="${param.keyatt}" />
		<gene:setString name="titoloMaschera" value='Riaggiudica' />
		<gene:redefineInsert name="corpo">
			<table class="lista">
				<tr>
					<td>
						<br>
						<b>La Riaggiudicazione</b> prevede la creazione di una nuova scheda di aggiudicazione.
						<br>Questo comporta che le schede già compilate per l'aggiudicazione precedente <b>non saranno più inviabili e modificabili</b>.  
						<br>Vuoi procedere con la riaggiudicazione?
						<br>&nbsp;<br>
					</td>
				</tr>
				<tr>
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" class="bottone-azione" value="Riaggiudica" title="Riaggiudica" onclick="javascript:riaggiudica()">&nbsp;
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla()">&nbsp;
					</td>
				</tr>
			</table>
		</gene:redefineInsert>
		<gene:javaScript>
			function annulla(){
				window.close();
			}
			
			function riaggiudica() {
				window.opener.location.href = '${pageContext.request.contextPath}/w9/RedirectNuovaFase.do?' + csrfToken + '&chosen=1&nuovaAggiudicazione=${progressivoRiaggiudicazione}&keyatt=${keyatt}';
				window.close();
			}
		</gene:javaScript>	
	</gene:template>
</div>
