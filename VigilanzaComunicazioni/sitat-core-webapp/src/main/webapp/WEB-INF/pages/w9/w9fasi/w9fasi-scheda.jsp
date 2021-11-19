<%/*
   * Created on 18-ott-2007
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9FASI-scheda">
	<gene:setString name="titoloMaschera"
			value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9LOTT-FASI")}' />
	
	<c:set var="key" value='${key}' scope="request" />

	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Definizione di una nuova fase" idProtezioni="DEFFASI" >
				<jsp:include page="w9fasi-pg-scheda.jsp" />
			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>
	
</gene:template>
