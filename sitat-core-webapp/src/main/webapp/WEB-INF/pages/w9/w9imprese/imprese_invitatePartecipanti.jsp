<%
	/*
	 * Created on: 10/06/2014
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="AliceResources" />

<c:set var="codgara" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="codlott" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="numimpr" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}" >
		<gene:campoScheda entita="W9IMPRESE" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${item[0]}" />
		<gene:campoScheda entita="W9IMPRESE" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT"
			value="${item[1]}" />
		<gene:campoScheda entita="W9IMPRESE" campo="NUM_IMPR_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;W9IMNUMIMPR"
			value="${item[2]}" />
		<gene:campoScheda entita="W9IMPRESE" campo="NUM_RAGG_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;W9IMNUMRAGG"
			value="${item[3]}" />
			
		<gene:archivio titolo="IMPRESE"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9IMPRESE.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9IMPRESE_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				where="IMPR.CODIMP = ${item[4]}" definizione="T2000;0;;;NOMIMP"
				value='${item[5]}' />
			<gene:campoScheda title="Codice impresa"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				visibile="false" entita="W9IMPRESE" definizione="T10;0;;;W9IMCODIMP"
				value="${item[4]}" />
		</gene:archivio>

		<gene:campoScheda entita="W9IMPRESE" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;W9IMRUOLO" value="${item[6]}"
			obbligatorio="false" visibile="true" />

		<gene:campoScheda entita="W9IMPRESE" title="Partecipante?" campo="PARTECIP_${param.contatore}"
			campoFittizio="true" definizione="N3;1;W9015;;W9IMPARTEC"
			value="${item[7]}" visibile="false" />

		<gene:campoScheda entita="W9IMPRESE" title="Tipologia del soggetto"
			campo="TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;W9IMTIPOA" value="${item[8]}" visibile="false" />

	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">

	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9IMPRESE" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${codgara}" />
		<gene:campoScheda entita="W9IMPRESE" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT"
			value="${codlott}" />
		<gene:campoScheda entita="W9IMPRESE" campo="NUM_IMPR_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;W9IMNUMIMPR"
			value="${numimpr}" />

		<gene:campoScheda entita="W9IMPRESE" campo="NUM_RAGG_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;W9IMNUMRAGG" />

		<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
			scheda="gene/impr/impr-scheda.jsp"
			schedaPopUp="gene/impr/impr-scheda-popup.jsp"
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9IMPRESE_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				definizione="T2000;0;;;NOMIMP" />
			<gene:campoScheda title="Codice impresa"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				entita="W9IMPRESE" definizione="T10;0;;;W9IMCODIMP" visibile="false" />
		</gene:archivio>

		<gene:campoScheda entita="W9IMPRESE" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;W9IMRUOLO" obbligatorio="false" visibile="true" />

		<gene:campoScheda entita="W9IMPRESE" title="Partecipante?" campo="PARTECIP_${param.contatore}"
			campoFittizio="true" definizione="N3;1;W9015;;W9IMPARTEC" />

		<gene:campoScheda entita="W9IMPRESE" title="Tipologia del partecipante"
			campo="TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;W9IMTIPOA" />

	</c:otherwise>
</c:choose>

<gene:javaScript>


</gene:javaScript>
