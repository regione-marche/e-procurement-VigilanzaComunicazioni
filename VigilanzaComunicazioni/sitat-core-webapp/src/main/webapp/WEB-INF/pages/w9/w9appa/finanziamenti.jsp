
<%
/*
 * Created on: 24/02/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="AliceResources" />

<c:set var="codgara" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="codlott" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="numappa" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="W9FINA" campo="CODGARA_${param.contatore}" campoFittizio="true" definizione="N10;1;;;CODGARA" value="${item[0]}" visibile="false" />
		<gene:campoScheda entita="W9FINA" campo="CODLOTT_${param.contatore}" campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${item[1]}" visibile="false"/>
		<gene:campoScheda entita="W9FINA" campo="NUM_APPA_${param.contatore}" campoFittizio="true"  definizione="N3;1;;;NUM_APPA" value="${item[2]}" visibile="false"/>
		<gene:campoScheda entita="W9FINA" campo="NUM_FINA_${param.contatore}"  campoFittizio="true" definizione="N3;1;;;NUM_FINA" value="${item[3]}" visibile="false"/>
		<gene:campoScheda entita="W9FINA" campo="ID_FINANZIAMENTO_${param.contatore}" campoFittizio="true"  definizione="T5;0;W3z02;;ID_FINANZIAMENTO" title="Tipo di finanziamento" value="${item[4]}" />
		<gene:campoScheda entita="W9FINA" campo="IMPORTO_FINANZIAMENTO_${param.contatore}" campoFittizio="true" definizione="F24.5;0;;MONEY;IMPORTO_FINANZIAMENTO" title ="Importo finanziamento" value="${item[5]}" />
		</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="W9FINA" campo="CODGARA_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;CODGARA" />
		<gene:campoScheda entita="W9FINA" campo="CODLOTT_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;CODLOTT" />
		<gene:campoScheda entita="W9FINA" campo="NUM_APPA_${param.contatore}" campoFittizio="true" visibile="false" definizione="N3;1;;;NUM_APPA"  />
		<gene:campoScheda entita="W9FINA" campo="NUM_FINA_${param.contatore}" campoFittizio="true" visibile="false" definizione="N3;1;;;NUM_APPA"  />
		<gene:campoScheda>
			<td colspan="2">Nessun finanziamento inserito</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9FINA" campo="CODGARA_${param.contatore}" campoFittizio="true" definizione="N10;1;;;CODGARA" value="${codgara}" visibile="false" />
		<gene:campoScheda entita="W9FINA" campo="CODLOTT_${param.contatore}" campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${codlott}" visibile="false"/>
		<gene:campoScheda entita="W9FINA" campo="NUM_APPA_${param.contatore}" campoFittizio="true"  definizione="N3;1;;;NUM_APPA" value="${numappa}" visibile="false"/>
		<gene:campoScheda entita="W9FINA" campo="NUM_FINA_${param.contatore}"  campoFittizio="true" definizione="N3;1;;;NUM_FINA" visibile="false"  />
		<gene:campoScheda entita="W9FINA" campo="ID_FINANZIAMENTO_${param.contatore}" campoFittizio="true"  definizione="T5;0;W3z02;;ID_FINANZIAMENTO" title="Tipo di 					  			finanziamento" />
		<gene:campoScheda entita="W9FINA" campo="IMPORTO_FINANZIAMENTO_${param.contatore}" campoFittizio="true" definizione="F24.5;0;;MONEY;IMPORTO_FINANZIAMENTO" title			 			="Importo finanziamento" />
	</c:otherwise>
</c:choose>	



