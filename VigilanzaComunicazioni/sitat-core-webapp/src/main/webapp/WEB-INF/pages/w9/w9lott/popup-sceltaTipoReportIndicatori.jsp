<%
	/*
   * Created on 19-settembre-2019
   *
   * Copyright (c) Maggioli S.p.A.
   * Tutti i diritti sono riservati.
   *
   * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
   * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
   * aver prima formalizzato un accordo specifico con Maggioli.
   */
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>
<gene:template file="popup-message-template.jsp">
	<gene:setString name="titoloMaschera"	value="Report indicatori" />
	<gene:redefineInsert name="corpo">
		<form action="" name="tipoReportForm" >
			<br><br>
			Selezionare il tipo di report desiderato:
			<br><br>
			<input type="radio" id="preliminare" name="tipoReportIndicatori" value="preliminare"> <b>Report preliminare</b>: distribuzione dei valori di normalità del gruppo di appartenenza
			<br><br>
			<input type="radio" id="anomalia" name="tipoReportIndicatori" value="anomalia"> <b>Report anomalia</b>:  valore degli indicatori riferiti alla procedura (Cig)
			<br>
			<br><br>
			<a class="link-generico" href="${contextPath}/doc/IstruzioniLetturaNotaMetodologica.pdf" download target="_parent" type="application/pdf">Istruzioni per la lettura e nota metodologica</a>
			<br>
			<br>
		</form>
  </gene:redefineInsert>
	<gene:javaScript>

	
	function annulla() {
		window.close();
	}

	function conferma() {
		var tipoReport = document.tipoReportForm.tipoReportIndicatori.value;
		if (tipoReport != null && tipoReport != "") {
			window.opener.document.getElementById("tipoReportIndicatori").value = tipoReport;
			window.opener.indicatoriLotto();
			window.close();
		} else {
			alert("Selezionare uno dei due tipi di report per continuare oppure annullare");
		}
	}
	
	</gene:javaScript>
</gene:template>
