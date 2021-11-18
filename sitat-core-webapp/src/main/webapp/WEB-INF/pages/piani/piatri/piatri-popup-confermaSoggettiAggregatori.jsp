<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<gene:template file="popup-message-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="listaInterventi">
	<gene:redefineInsert name="addHistory" />
	
	<gene:setString name="titoloMaschera" value="Report acquisti per soggetti aggregatori" />
	<gene:redefineInsert name="corpo">
		<br>
		Questa funzionalit&agrave; genera il report in tracciato standard xls da utilizzare per comunicare, ai sensi dell'articolo 21 comma 6 del Codice dei contratti pubblici, al Tavolo Tecnico dei Soggetti Aggregatori l'elenco degli acquisti di beni e servizi di importo superiore a 1 milione di euro che si prevede di inserire nella programmazione biennale.
		<br><br>
		Il report dovr&agrave; essere verificato, eventualmente integrato ed inviato all'indirizzo di PEC: <a href="mailto:programmazione.biennale@pec.mef.gov.it" target="_top">programmazione.biennale@pec.mef.gov.it</a> 
		<br><br>
	</gene:redefineInsert>

	<gene:javaScript>

		function conferma() {
			window.opener.scaricaFileSoggettoAggregatore();
			window.close();
		}

		function annulla() {
			window.close();
		}
		
	</gene:javaScript>
</gene:template>

