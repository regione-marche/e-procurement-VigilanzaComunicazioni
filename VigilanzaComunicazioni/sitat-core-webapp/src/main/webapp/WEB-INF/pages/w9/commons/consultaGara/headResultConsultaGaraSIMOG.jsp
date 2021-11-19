<%
/*
 * Created on 10-nov-2010
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/controlliFormali.js"></script>

<script type="text/javascript">
<!--

	function nuovoCaricamento(){
		document.location.href = "ConsultaGara.do?" + csrfToken + "&metodo=inizializza";
	}
	
	function annulla(){
	  document.location.href = '../Home.do?' + csrfToken;
	}

	function prosegui(){
		document.location.href="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9gara/w9gara-scheda.jsp&key=W9GARA.CODGARA=N:${codgara}";
	}
-->
</script type="text/javascript">