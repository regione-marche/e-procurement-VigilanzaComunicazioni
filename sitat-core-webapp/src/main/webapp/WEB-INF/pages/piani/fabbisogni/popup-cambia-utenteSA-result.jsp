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
		<gene:setString name="titoloMaschera" value='Cambia utente/ufficio intestatario' />
		<gene:redefineInsert name="corpo">
			<table class="lista">
				<tr>
					<td>
						
						<c:if test='${RISULTATO eq "CAMBIAUTENTESAESEGUITO"}'>
							<br>
							<b>OPERAZIONE ESEGUITA CON SUCCESSO</b>
							<br>
							<br>
							L'operazione di modifica dell'utente e ufficio intestatario è andata a buon fine
						</c:if>
						<c:if test='${RISULTATO eq "KO"}'>
							<br>
							<b>ERRORE!</b>
							<br>
							<br>
							Si è verificato un problema durante la modifica dell'utente e ufficio intestatario
						</c:if>
						<br>&nbsp;<br>
					</td>
				</tr>
				<tr>
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
					</td>
				</tr>
			</table>
		</gene:redefineInsert>
		<gene:javaScript>
			<c:if test='${RISULTATO eq "CAMBIAUTENTESAESEGUITO"}'>
		    	window.opener.listaVaiAPagina(window.opener.document.forms[0].pgCorrente.value);
			</c:if>
			function annulla(){
				window.close();
			}
		</gene:javaScript>	
	</gene:template>
</div>
