<%/*
       * Created on 07-Nov-2006
       *
       * Copyright (c) EldaSoft S.p.A.
       * Tutti i diritti sono riservati.
       *
       * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
       * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
       * aver prima formalizzato un accordo specifico con EldaSoft.
       */
%>
<% //Inserisco la Tag Library %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<!-- Dati anagrafica utente -->
<gene:template file="scheda-nomenu-template.jsp">
<gene:javaScript>
	function gestioneAction() {
		
	}
	
</gene:javaScript>
	
	<gene:setString name="titoloMaschera" value='Registrazione utente'/>
	
	<gene:redefineInsert name="corpo">

		<table class="dettaglio-home">
			<tr>
				<td class="sotto-voce">
					<br>
					I dati inseriti sono stati memorizzati.
					<br>
					<br>
					<b>Per completare la registrazione</b> ed essere abilitato all'uso del sistema ${applicationScope.appTitle},
					segua le indicazioni che riceverà per posta elettronica all'indirizzo email o PEC specificato nella maschera precedente.
					<br>
					<br>
					Grazie.
					<br>
					<br>
					<a href="InitLogin.do" class="link-generico">Vai alla home page</a>
				</td>
			</tr>
		</table>

</gene:redefineInsert>
</gene:template>