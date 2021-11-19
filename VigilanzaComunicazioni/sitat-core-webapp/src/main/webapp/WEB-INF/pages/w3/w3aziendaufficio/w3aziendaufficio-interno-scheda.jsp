<%
/*
 * Created on: 06-ott-2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Finanziamenti del lavoro */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<gene:formScheda entita="W3AZIENDAUFFICIO" gestisciProtezioni="true" >
	
	<gene:campoScheda campo="ID" />
	<gene:campoScheda campo="AZIENDA_CF" />
	<gene:campoScheda campo="AZIENDA_DENOM" />
	<gene:campoScheda campo="UFFICIO_DENOM" />
	<gene:campoScheda campo="UFFICIO_ID" />

	<gene:campoScheda>	
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>

</gene:formScheda>





