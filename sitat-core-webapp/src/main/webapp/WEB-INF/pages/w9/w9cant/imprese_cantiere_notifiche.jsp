
<%
	/*
	 * Created on: 04/08/2009
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
<c:set var="numappa" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="W9CANTIMP" campo="CODGARA_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N10;1;;;CODGARA" value="${item[0]}" />
		<gene:campoScheda entita="W9CANTIMP" campo="CODLOTT_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${item[1]}" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_CANT_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N3;1;;;NUM_CANT" value="${item[2]}" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_IMP_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N3;1;;;NUM_IMP" value="${item[3]}" />
		<gene:archivio titolo="IMPRESE"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9CANTIMP.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9CANTIMP_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				where="IMPR.CODIMP = ${item[4]}" definizione="T2000;0;;;NOMIMP"
				value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetDescFromArchFunction",pageContext,item[4],"W9CANTIMP")}' />
			<gene:campoScheda title="Codice impresa ausiliaria"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				visibile="false" entita="W9CANTIMP" definizione="T10;0;;;CODIMP_"
				value="${item[4]}" />
		</gene:archivio>
		<gene:campoScheda entita="W9CANTIMP" title="Codice identificativo pratica DURC"
			campo="CIPDURC_${param.contatore}" campoFittizio="true"
			definizione="T14;0;;;W9CICIPDURC" value="${item[5]}" />
		<gene:campoScheda entita="W9CANTIMP" title="Protocollo pratica DURC"
			campo="PROTDURC_${param.contatore}" campoFittizio="true"
			definizione="T8;0;;;W9CIPROTDURC" value="${item[6]}" />
		<gene:campoScheda entita="W9CANTIMP" title="Data emissione DURC"
			campo="DATDURC_${param.contatore}" campoFittizio="true"
			definizione="T10;0;;DATA_ELDA;W9CIDATDURC" value="${item[7]}" />
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="W9CANTIMP" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" />
		<gene:campoScheda entita="W9CANTIMP" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_CANT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_CANT" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_IMP_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_IMP" />
		<gene:campoScheda>
			<td colspan="2">Nessuna impresa aggiudicataria inserita.</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9CANTIMP" campo="CODGARA_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N10;1;;;CODGARA" value="${codgara}" />
		<gene:campoScheda entita="W9CANTIMP" campo="CODLOTT_${param.contatore}" visibile="false"
				campoFittizio="true" definizione="N10;1;;;CODLOTT" value="${codlott}" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_CANT_${param.contatore}" visibile="false"
				campoFittizio="true" definizione="N3;1;;;NUM_CANT" value="${numappa}" />
		<gene:campoScheda entita="W9CANTIMP" campo="NUM_IMP_${param.contatore}" visibile="false" 
				campoFittizio="true" definizione="N3;1;;;NUM_IMP" />
		<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
			scheda="gene/impr/impr-scheda.jsp"
			schedaPopUp="gene/impr/impr-scheda-popup.jsp"
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9CANTIMP_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa" entita="IMPR" campo="NOMEST_${param.contatore}"
					campoFittizio="true" definizione="T2000;0;;;NOMIMP" />
			<gene:campoScheda title="Codice impresa aggiudicataria" campo="CODIMP_${param.contatore}" 
					campoFittizio="true" entita="W9CANTIMP" definizione="T10;0;;;W9CICODIMP" visibile="true" />
		</gene:archivio>
		<gene:campoScheda entita="W9CANTIMP" title="Codice identificativo pratica DURC"
			campo="CIPDURC_${param.contatore}" campoFittizio="true" definizione="T14;0;;;W9CICIPDURC" />
		<gene:campoScheda entita="W9CANTIMP" title="Protocollo pratica DURC"
			campo="PROTDURC_${param.contatore}" campoFittizio="true" definizione="T8;0;;;W9CIPROTDURC" />
		<gene:campoScheda entita="W9CANTIMP" title="Data emissione DURC"
			campo="DATDURC_${param.contatore}" campoFittizio="true" definizione="T10;0;;DATA_ELDA;W9CIDATDURC"  />
	</c:otherwise>
</c:choose>
<gene:javaScript>

</gene:javaScript>
