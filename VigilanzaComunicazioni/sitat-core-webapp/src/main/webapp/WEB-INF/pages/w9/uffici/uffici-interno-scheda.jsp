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
	/* Interno della scheda del centro di costo */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:setString name="titoloMaschera"
		value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"UFFICI")}' />

<gene:formScheda entita="UFFICI" gestisciProtezioni="true"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreUFFICI">
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="ID" visibile="false" />
		<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${sessionScope.uffint}" />
		<gene:campoScheda campo="DENOM" obbligatorio="true" />
		<gene:campoScheda campo="CODICE_UFFICIO" />
		<gene:campoScheda campo="CCPROG" />
	</gene:gruppoCampi>

	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="UFFICI" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>

</gene:formScheda>
<gene:javaScript>

</gene:javaScript>
