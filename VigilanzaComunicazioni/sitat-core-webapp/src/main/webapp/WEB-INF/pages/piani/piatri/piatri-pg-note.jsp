
<%
  /*
   * Created on: 01/10/2012
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

<gene:formScheda entita="PIATRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI">
	<gene:campoScheda campo="CONTRI" visibile="false" />
	<gene:campoScheda campo="NOTSCHE1" />
	<c:if test='${tiprog ne "2"}'>
		<gene:campoScheda campo="NOTSCHE2" />
		<gene:campoScheda campo="NOTSCHE2B" />
		<gene:campoScheda campo="NOTSCHE3" />
	</c:if>
	<c:if test='${tiprog ne "1"}'>
		<gene:campoScheda campo="NOTSCHE4" />
	</c:if>
	
	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="PIATRI" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

