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

<gene:setString name="titoloMaschera" value='Compilazione di un nuovo SmartCIG' />

<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:nuovoSmartCIG();" title="Avanti" tabindex="1503">Avanti</a>
		</td>
	</tr>
</gene:redefineInsert>

<gene:redefineInsert name="corpo">

	<table class="dettaglio-notab">
		<tr>
			<td class="valore-dato" colspan="2"><input type="radio" name="compilazione" id="manuale" value="1" onclick="">
			<b>Compilazione manuale di un nuovo SmartCIG</b> 
			</td>
		</tr>		
		<tr>
			<td class="valore-dato" colspan="2"><input type="radio" name="compilazione" id="simog" value="1" onclick=""> 
			<b>Importa da Sistema ANAC</b>
			</td>
		</tr>
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Indietro" title="Indietro" class="bottone-azione" onclick="javascript:Annulla();"/>&nbsp;&nbsp;
				<input type="button" value="Avanti" title="Avanti" class="bottone-azione" onclick="javascript:nuovoSmartCIG();"/>&nbsp;
			</td>
		</tr>
	</table>

</gene:redefineInsert>

<gene:javaScript>

	//funzione di creazione nuovo smartCIG
	function nuovoSmartCIG(){
		if (document.getElementById("simog").checked ) {
			location.href = '${pageContext.request.contextPath}/w9/ConsultaGara.do?' + csrfToken + '&metodo=inizializza&smartcig=ok';
		} else if (document.getElementById("manuale").checked) {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/w9gara/w9gara-scheda.jsp&modo=NUOVO&smartcig=si';
		} else {
			alert("Seleziona una delle due operazioni");
		}
	}
	
	function Annulla(){
		document.location.href = '${pageContext.request.contextPath}/Home.do?' + csrfToken;
	}

</gene:javaScript>

</gene:template>

