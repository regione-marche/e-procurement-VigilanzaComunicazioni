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
/* Scheda ufficio intestatario */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="SchedaSA-ADMIN">
	<% // Settaggio delle stringhe utilizzate nel template %>
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.gene.tags.functions.GetTitleFunction",pageContext,"UFFINT")}' />
	
	<gene:redefineInsert name="corpo">
	<gene:formPagine>
		<gene:pagina title="Dati generali" idProtezioni="DATIGEN">
			<jsp:include page="uffint-interno-scheda.jsp" />
		</gene:pagina>
		<gene:pagina title="Utenti" idProtezioni="UT">
			<jsp:include page="admin-uffint-pg-lista-utenti.jsp" />		
		</gene:pagina>
	</gene:formPagine>
	</gene:redefineInsert>

</gene:template>