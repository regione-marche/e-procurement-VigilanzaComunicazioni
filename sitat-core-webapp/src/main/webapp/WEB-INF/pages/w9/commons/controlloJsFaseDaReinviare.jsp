<%
/*
 * Created on: 29/06/2016
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

<c:set var="isFaseDaReinviare" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.IsFaseDaReinviareFunction",pageContext,localkey)}' />

<c:if test='${isFaseDaReinviare}'>

$(document).ready(function() {
	setTimeout(function() {
		alert("ATTENZIONE: la scheda e' gia' stata inviata. Se si apportano\nmodifiche sara' necessario provvedere ad inviarla nuovamente");
	}, 75);
});

</c:if>
