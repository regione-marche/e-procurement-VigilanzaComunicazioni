
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
<c:set var="numPubb" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${item[0]}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_PUBB_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_PUBB"
			value="${item[1]}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI"
			value="${item[2]}" />
		<gene:archivio titolo="IMPRESE"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.ESITI_AGGIUDICATARI.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="ESITI_AGGIUDICATARI_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Aggiudicataria"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				where="IMPR.CODIMP = ${item[3]}" definizione="T2000;0;;;NOMIMP"
				value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetDescFromArchFunction",pageContext,item[3],"ESITI_AGGIUDICATARI")}' />
			<gene:campoScheda title="Codice impresa ausiliaria"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				visibile="false" entita="ESITI_AGGIUDICATARI" definizione="T10;0;;;CODIMP_"
				value="${item[3]}" />
		</gene:archivio>
		<gene:campoScheda entita="ESITI_AGGIUDICATARI"
			title="Tipologia del soggetto aggiudicatario"
			campo="ID_TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;ID_TIPOAGG" value="${item[4]}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;W3ESRUOLO" value="${item[5]}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI"
			value="${item[6]}" />
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_PUBB_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_PUBB" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI" />
		<gene:campoScheda>
			<td colspan="2">Nessuna impresa aggiudicataria inserita.</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${codgara}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_PUBB_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_PUBB"
			value="${numPubb}" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI" />
		<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
			scheda="gene/impr/impr-scheda.jsp"
			schedaPopUp="gene/impr/impr-scheda-popup.jsp"
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="ESITI_AGGIUDICATARI_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Aggiudicataria"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				definizione="T2000;0;;;NOMIMP" />
			<gene:campoScheda title="Codice impresa aggiudicataria"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				entita="ESITI_AGGIUDICATARI" definizione="T10;0;;;CODIMP" visibile="false" />
		</gene:archivio>
		<gene:campoScheda entita="ESITI_AGGIUDICATARI"
			title="Tipologia del soggetto aggiudicatario"
			campo="ID_TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;ID_TIPOAGG" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;W3ESRUOLO" />
		<gene:campoScheda entita="ESITI_AGGIUDICATARI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI" />
	</c:otherwise>
</c:choose>
<gene:fnJavaScriptScheda
	funzione="bloccaCampoRuolo(${param.contatore}, '#ESITI_AGGIUDICATARI_ID_TIPOAGG_${param.contatore}#')"
	elencocampi="ESITI_AGGIUDICATARI_ID_TIPOAGG_${param.contatore}" esegui="true" />
<gene:javaScript>

	function bloccaCampoRuolo(idImpresa, valore) {
		if (valore != '') {
			if(valore==1) {
				showObj("rowESITI_AGGIUDICATARI_RUOLO_"  + idImpresa, true);		
			} else {
				document.getElementById('ESITI_AGGIUDICATARI_RUOLO_'+ idImpresa).value="";
				showObj("rowESITI_AGGIUDICATARI_RUOLO_"  + idImpresa, false);
			}
			
			/*if(valore==3) {
				showObj("rowLinkAddESITI_AGGIUDICATARI", false);
			} else {
				showObj("rowLinkAddESITI_AGGIUDICATARI", true);
			}*/
		}
	}

</gene:javaScript>
