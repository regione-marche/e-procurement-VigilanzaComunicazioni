
<%
/*
 * Created on: 02/10/2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%
/*
 * Created on: 22/09/2009
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

<fmt:setBundle basename="W9Resources" />

<c:set var="codgara" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="codlott" value='${fn:substringAfter(param.chiave, ";")}' />
<c:set var="keyW9LOTT" value="${keyW9LOTT}" scope="request"/>

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="W9LOTTCATE" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3CODGARA3" value="${item[0]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3LOTTO_I3" value="${item[1]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="NUM_CATE_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;W3NUM_CATE" value="${item[2]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CATEGORIA_${param.contatore}" campoFittizio="true" definizione="T5;0;W3z03;;W3CATEGORI" value="${item[3]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CLASCAT_${param.contatore}" campoFittizio="true" definizione="T5;0;W3z11;;W3CLASCATC" value="${item[4]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="SCORPORABILE_${param.contatore}" visibile="true" campoFittizio="true" definizione="T2;0;;SN;W9LCSCORPORA" value="${item[5]}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="SUBAPPALTABILE_${param.contatore}" visibile="true" campoFittizio="true" definizione="T2;0;;SN;W9LCSUBAPPAL" value="${item[6]}" />
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="W9LOTTCATE" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3CODGARA3" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3LOTTO_I3" />
		<gene:campoScheda entita="W9LOTTCATE" campo="NUM_CATE_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;W3NUM_CATE" />
		
		<gene:campoScheda nome="msgCategorie">
			<td colspan="2"><b>Nessuna ulteriore categoria inserita.</b></td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9LOTTCATE" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3CODGARA3" value="${codgara}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3LOTTO_I3" value="${codlott}" />
		<gene:campoScheda entita="W9LOTTCATE" campo="NUM_CATE_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;W3NUM_CATE" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CATEGORIA_${param.contatore}" campoFittizio="true" definizione="T5;0;W3z03;;W3CATEGORI" />
		<gene:campoScheda entita="W9LOTTCATE" campo="CLASCAT_${param.contatore}" campoFittizio="true" definizione="T5;0;W3z11;;W3CLASCATC" />
		<gene:campoScheda entita="W9LOTTCATE" campo="SCORPORABILE_${param.contatore}" visibile="true" campoFittizio="true" definizione="T2;0;;SN;W9LCSCORPORA" />
		<gene:campoScheda entita="W9LOTTCATE" campo="SUBAPPALTABILE_${param.contatore}" visibile="true" campoFittizio="true" definizione="T2;0;;SN;W9LCSUBAPPAL" />
	</c:otherwise>
</c:choose>
