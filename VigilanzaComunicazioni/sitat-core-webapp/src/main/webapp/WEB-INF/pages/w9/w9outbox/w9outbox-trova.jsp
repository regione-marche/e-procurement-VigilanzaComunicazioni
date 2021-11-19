
<%
	/*
	 * Created on 22-Set-2006
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="tipoDB" value="${gene:callFunction('it.eldasoft.gene.tags.utils.functions.GetTipoDBFunction', pageContext)}" />

	<c:choose>
		<c:when test='${tipoDB eq "ORA"}' >
			<c:set var="campoDataInvio" value="CAST(DATINV AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "MSQ"}' >
			<c:set var="campoDataInvio" value="CONVERT(date, DATINV)" />
		</c:when>
		<c:when test='${tipoDB eq "POS"}' >
			<c:set var="campoDataInvio" value="CAST(DATINV AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "DB2"}' >
			<c:set var="campoDataInvio" value="DATE(DATINV)" />
		</c:when>
	</c:choose>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9OUTBOX-trova">
	<gene:setString name="titoloMaschera" value="Ricerca comunicazioni SCP"/>

	<%
		// Ridefinisco il corpo della ricerca
	%>
	<gene:redefineInsert name="corpo">

		<%
			// Creo la form di trova con i campi dell'entita' W9OUTBOX
		%>
		<gene:formTrova entita="W9OUTBOX" gestisciProtezioni="true">
			<gene:gruppoCampi idProtezioni="GEN">
				<tr>
					<td colspan="3"><b>Dati del filtro</b>
					</td>
				</tr>
				<gene:campoTrova entita="UFFINT" campo="NOMEIN" 
					title="Stazione appaltante" definizione="T30;0;;;NOMEIN"
					where="UFFINT.CFEIN = W9OUTBOX.CFSA" />
				<gene:campoTrova campo="AREA" />
				<gene:campoTrova campo="KEY01" />
				<gene:campoTrova campo="${campoDataInvio}" title="Data invio comunicazione" computed="true" definizione="D;0;;DATA_ELDA;W9XMLDTFED" />
				<gene:campoTrova campo="STATO" />

			</gene:gruppoCampi>
		</gene:formTrova>
	</gene:redefineInsert>
</gene:template>
