
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="AliceResources" />

<c:set var="codgara" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="codlott" value='${fn:substringAfter(param.chiave, ";")}' />
<c:set var="keyW9LOTT" value="${keyW9LOTT}" scope="request"/>

	<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="W9CPV" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" value="${item[0]}" />
		<gene:campoScheda entita="W9CPV" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${item[1]}" />
		<gene:campoScheda entita="W9CPV" campo="NUM_CPV_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_CPV" value="${item[2]}" />
		<gene:campoScheda entita="W9CPV" campo="CPV_${param.contatore}" speciale='${gene:checkProt(pageContext, "SEZ.MOD.W9.W9LOTT-scheda.DATIDET.W9CPV")}' href="#" title="Codice CPV Secondario" value="${item[3]}" campoFittizio="true" definizione="T12;0;;;G2CPVCOD4" />

		<gene:campoScheda title="Descrizione CPV Secondario" entita="TABCPV" campoFittizio="true" campo="CPVDESC_${param.contatore}"
			value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetDescFromCpvFunction",pageContext,item[3])}' definizione="T150;0">
		</gene:campoScheda>
		<gene:campoScheda visibile='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.VerificaRangeCpvFunction",item[3])}'
			nome="previsto_allegato_${param.contatore}">
			<td class="etichetta-dato"></td>
			<td>&nbsp;&nbsp;&nbsp;Previsto dall'ALLEGATO IIA o IIB del DLgs. 163/06</td>
		</gene:campoScheda>
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="W9CPV" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" />
		<gene:campoScheda entita="W9CPV" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT" />
		<gene:campoScheda>
			<td colspan="2"><b>Nessun CPV Secondario inserito.</b></td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9CPV" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" value="${codgara}" />
		<gene:campoScheda entita="W9CPV" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${codlott}" />
		<gene:campoScheda entita="W9CPV" campo="NUM_CPV_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_CPV" />
		<gene:campoScheda entita="W9CPV" campo="CPV_${param.contatore}" speciale='${gene:checkProt(pageContext, "SEZ.MOD.W9.W9LOTT-scheda.DATIDET.W9CPV")}' href="#" title="Codice CPV Secondario" campoFittizio="true" definizione="T12;0;;;G2CPVCOD4" />
		<gene:campoScheda title="Descrizione CPV Secondario" entita="TABCPV" campoFittizio="true" campo="CPVDESC_${param.contatore}" definizione="T150;0" />
		
	</c:otherwise>
</c:choose>
