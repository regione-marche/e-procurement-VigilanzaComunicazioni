<%
  /*
   * Created on 11-gen-2018
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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="funzionalita" value="${param.funzionalita}" />

<gene:template file="scheda-template.jsp" >
	<gene:redefineInsert name="corpo">
		
		<br/>
			Funzionalità in corso ...
		<br/><br/>
		
		<gene:javaScript>
			alert("Funzionalità conclusa con successo");
			<c:choose>
				<c:when test="${funzionalita eq 'RDP_RevisioneInterventiAnnoPrecedente'}" >
					window.close();
				</c:when>
				<c:otherwise>
					historyVaiIndietroDi(1);
				</c:otherwise>
			</c:choose>
			

		</gene:javaScript>
	</gene:redefineInsert>
</gene:template>
