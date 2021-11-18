<%
/*
 * Created on: 6-Nov-2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Lista degli uffici intestatari */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="AliceResources" />
<c:set var="nomeEntitaParametrizzata" scope="request">
	<fmt:message key="label.tags.uffint.multiplo" />
</c:set>
<c:set var="nomeEntitaSingolaParametrizzata" scope="request">
	<fmt:message key="label.tags.uffint.singolo" />
</c:set>

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="ListaSA-ADMIN">
	<gene:setString name="titoloMaschera" value="Lista ${fn:toLowerCase(nomeEntitaParametrizzata)}"/>
	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.GENE.SchedaSA-ADMIN")}' scope="request"/>

	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu" scope="requestScope">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
		<tr><td >
		<gene:formLista entita="UFFINT" sortColumn="3" pagesize="20" tableclass="datilista" gestisciProtezioni="true" gestore="it.eldasoft.gene.web.struts.tags.gestori.GestoreUFFINT" pathScheda="gene/uffint/admin-uffint-scheda.jsp">
			<jsp:include page="uffint-interno-lista.jsp" />
		</gene:formLista>
		</td></tr>
		<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
  </gene:redefineInsert>
</gene:template>
