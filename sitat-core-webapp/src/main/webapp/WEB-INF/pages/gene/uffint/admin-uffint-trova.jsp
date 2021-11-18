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
 /* Form di ricerca degli uffici intestatari */
%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="AliceResources" />
<c:set var="nomeEntitaParametrizzata" scope="request">
	<fmt:message key="label.tags.uffint.multiplo" />
</c:set>

<c:set var="filtroLivelloUtente"
	value='${gene:callFunction2("it.eldasoft.gene.tags.utils.functions.FiltroLivelloUtenteFunction", pageContext, "UFFINT")}' />

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="SA-ADMIN-trova" >

	<gene:setString name="titoloMaschera" value="Ricerca ${fn:toLowerCase(nomeEntitaParametrizzata)}"/>

	<gene:redefineInsert name="corpo">

		<c:if test='${not empty applicationScope.dataInterruzione}'>
			<table align="center" class="arealayout" style="width: 600">
				<tr>
					<td class="errore-generale">
					<br><br>
					Attenzione:
					<br><br>
					si comunica che per il giorno ${applicationScope.dataInterruzione} dalle ore ${applicationScope.oraInizioInterruzione} alle ore ${applicationScope.oraFineInterruzione}
					<br>&egrave; prevista l'interruzione del servizio per un aggiornamento.
					
					<br>
					<br>Si prega di uscire dall'applicativo prima dell'interruzione.
					<br>Ci scusiamo per i disagi.

					<br>
					<br>Grazie per la collaborazione.<br><br>
					
					</td>
				</tr>
			</table>
		</c:if>

  	<gene:formTrova entita="UFFINT" filtro="${filtroLivelloUtente}" gestisciProtezioni="true" lista="gene/uffint/admin-uffint-lista.jsp" scheda="gene/uffint/admin-uffint-scheda.jsp">
			<jsp:include page="uffint-interno-trova.jsp" />
		</gene:formTrova>
	</gene:redefineInsert>
</gene:template>
