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
		<gene:setString name="titoloMaschera" value='Cambia RUP' />
		<gene:redefineInsert name="corpo">
			<table class="lista">
				<tr>
					<td>
						<br>
						<b>ATTENZIONE!</b>
						<br>
						<br>
						Cambiando il RUP non si avr&agrave; pi&ugrave; accesso alla gara: questa sar&agrave; visibile solo al nuovo RUP.
						<br><br>
						<b>Si desidera continuare ?</b>
						<br>&nbsp;<br>
					</td>
				</tr>
				<tr>
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:continua(${param.codgara})">
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla()">&nbsp;
					</td>
				</tr>
			</table>
		</gene:redefineInsert>
		<gene:javaScript>
			function annulla(){
				window.close();
			}
		
			function continua(codgara){
				location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/w9gara/w9gara-cambia-rup-scheda.jsp&codgara=' + codgara;
			}
		</gene:javaScript>	
	</gene:template>
</div>
