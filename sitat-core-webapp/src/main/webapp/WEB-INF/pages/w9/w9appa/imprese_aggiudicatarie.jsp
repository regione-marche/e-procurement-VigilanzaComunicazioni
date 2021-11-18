
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
		<gene:campoScheda entita="W9AGGI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${item[0]}" />
		<gene:campoScheda entita="W9AGGI" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT"
			value="${item[1]}" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_APPA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_APPA"
			value="${item[2]}" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI"
			value="${item[3]}" />
		<gene:archivio titolo="IMPRESE"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9AGGI.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9AGGI_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Aggiudicataria"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				where="IMPR.CODIMP = ${item[4]}" definizione="T2000;0;;;NOMIMP"
				value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetDescFromArchFunction",pageContext,item[4],"W9AGGI")}' />
			<gene:campoScheda title="Codice impresa ausiliaria"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				visibile="false" entita="W9AGGI" definizione="T10;0;;;CODIMP_"
				value="${item[4]}" />
		</gene:archivio>
		<gene:campoScheda entita="W9AGGI"
			title="Tipologia del soggetto aggiudicatario"
			campo="ID_TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;ID_TIPOAGG" value="${item[5]}" />
		<gene:campoScheda entita="W9AGGI" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;RUOLO" value="${item[6]}" />
		<gene:campoScheda entita="W9AGGI"
			title="L'aggiudicatario ha fatto ricorso all'avvalimento ?"
			campo="FLAG_AVVALIMENTO_${param.contatore}" campoFittizio="true"
			definizione="T5;0;W3z09;;FLAG_AVVALIMENTO" value="${item[7]}" />
		<gene:archivio titolo="IMPRESE"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9AGGI.CODIMP"),"gene/impr/impr-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.ImprScheda"),"gene/impr/impr-scheda-popup.jsp","")}'
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9AGGI_CODIMP_AUSILIARIA_${param.contatore}"
			formName="formImprese_aus_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Ausiliaria"
				entita="IMPR" campo="NOMEST_AUSILIARIA_${param.contatore}"
				campoFittizio="true" where="IMPR.CODIMP = ${item[8]}"
				definizione="T2000;0;;;NOMIMP"
				value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetDescFromArchFunction",pageContext,item[8],"W9AGGI")}' />
			<gene:campoScheda title="Codice impresa ausiliaria"
				campo="CODIMP_AUSILIARIA_${param.contatore}" campoFittizio="true"
				visibile="false" entita="W9AGGI"
				definizione="T10;0;;;CODIMP_AUSILIARIA" value="${item[8]}" />
		</gene:archivio>
		<gene:campoScheda entita="W9AGGI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI"
			value="${item[9]}" />
		<gene:campoScheda entita="W9AGGI"
			campo="IMPORTO_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F24.5;0;;MONEY;W3AGIMP_AGGI" value="${item[10]}" />
		<gene:campoScheda entita="W9AGGI"
			campo="PERC_RIBASSO_AGG_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F13.9;0;;PRC;W3AGPERC_OFF" value="${item[11]}" />
		<gene:campoScheda entita="W9AGGI"
			campo="PERC_OFF_AUMENTO_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F13.9;0;;PRC;W3AGPERC_RIB" value="${item[12]}" />
		
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda entita="W9AGGI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA" />
		<gene:campoScheda entita="W9AGGI" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_APPA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_APPA" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI" />
		<gene:campoScheda entita="W9AGGI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI" />
		<gene:campoScheda>
			<td colspan="2">Nessuna impresa aggiudicataria inserita.</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9AGGI" campo="CODGARA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODGARA"
			value="${codgara}" />
		<gene:campoScheda entita="W9AGGI" campo="CODLOTT_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N10;1;;;CODLOTT"
			value="${codlott}" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_APPA_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_APPA"
			value="${numappa}" />
		<gene:campoScheda entita="W9AGGI" campo="NUM_AGGI_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;1;;;NUM_AGGI" />
		<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
			scheda="gene/impr/impr-scheda.jsp"
			schedaPopUp="gene/impr/impr-scheda-popup.jsp"
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9AGGI_CODIMP_${param.contatore}"
			formName="formImprese_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Aggiudicataria"
				entita="IMPR" campo="NOMEST_${param.contatore}" campoFittizio="true"
				definizione="T2000;0;;;NOMIMP" />
			<gene:campoScheda title="Codice impresa aggiudicataria"
				campo="CODIMP_${param.contatore}" campoFittizio="true"
				entita="W9AGGI" definizione="T10;0;;;CODIMP" visibile="false" />
		</gene:archivio>
		<gene:campoScheda entita="W9AGGI"
			title="Tipologia del soggetto aggiudicatario"
			campo="ID_TIPOAGG_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3010;;ID_TIPOAGG" />
		<gene:campoScheda entita="W9AGGI" title="Ruolo"
			campo="RUOLO_${param.contatore}" campoFittizio="true"
			definizione="N7;0;W3011;;RUOLO" />
		<gene:campoScheda entita="W9AGGI"
			title="L'aggiudicatario ha fatto ricorso all'avvalimento ?"
			campo="FLAG_AVVALIMENTO_${param.contatore}" campoFittizio="true"
			definizione="T5;0;W3z09;;FLAG_AVVALIMENTO" />
		<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
			scheda="gene/impr/impr-scheda.jsp"
			schedaPopUp="gene/impr/impr-scheda-popup.jsp"
			campi="IMPR.NOMEST;IMPR.CODIMP"
			chiave="W9AGGI_CODIMP_AUSILIARIA_${param.contatore}"
			formName="formImprese_aus_${param.contatore}" inseribile="true">
			<gene:campoScheda title="Denominazione Impresa Ausiliaria"
				entita="IMPR" campo="NOMEST_AUSILIARIA_${param.contatore}"
				campoFittizio="true" definizione="T2000;0;;;NOMIMP" />
			<gene:campoScheda title="Codice impresa ausiliaria"
				campo="CODIMP_AUSILIARIA_${param.contatore}" campoFittizio="true"
				entita="W9AGGI" definizione="T10;0;;;CODIMP_AUSILIARIA"
				visibile="false" />
		</gene:archivio>
		<gene:campoScheda entita="W9AGGI" campo="ID_GRUPPO_${param.contatore}"
			visibile="false" campoFittizio="true" definizione="N3;;;;NUM_AGGI" />
		<gene:campoScheda entita="W9AGGI" campo="IMPORTO_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F24.5;0;;MONEY;W3AGIMP_AGGI"/>
		<gene:campoScheda entita="W9AGGI"
			campo="PERC_RIBASSO_AGG_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F13.9;0;;PRC;W3AGPERC_OFF"/>
		<gene:campoScheda entita="W9AGGI"
			campo="PERC_OFF_AUMENTO_${param.contatore}" campoFittizio="true"
			visibile="${!empty isAccordoQuadro}" definizione="F13.9;0;;PRC;W3AGPERC_RIB"/>
	</c:otherwise>
</c:choose>
<gene:fnJavaScriptScheda funzione="bloccaCampoRuolo(${param.contatore}, '#W9AGGI_ID_TIPOAGG_${param.contatore}#')"
	elencocampi="W9AGGI_ID_TIPOAGG_${param.contatore}" esegui="true" />
<c:if test="${!empty isAccordoQuadro}">
	<gene:fnJavaScriptScheda funzione="verificaMandataria(${param.contatore}, '#W9AGGI_RUOLO_${param.contatore}#', '#W9AGGI_ID_TIPOAGG_${param.contatore}#')"
	elencocampi="W9AGGI_RUOLO_${param.contatore};W9AGGI_ID_TIPOAGG_${param.contatore}" esegui="true" />	
</c:if>

<gene:fnJavaScriptScheda funzione="bloccaCampoNomimp(${param.contatore}, '#W9AGGI_FLAG_AVVALIMENTO_${param.contatore}#')"
	elencocampi="W9AGGI_FLAG_AVVALIMENTO_${param.contatore}" esegui="true" />
<gene:javaScript>

	function bloccaCampoRuolo(idImpresa, valore) {
		if (valore != '') {
			if (valore == 1) {
				showObj("rowW9AGGI_RUOLO_"  + idImpresa, true);		
			} else {
				document.getElementById('W9AGGI_RUOLO_'+ idImpresa).value="";
				showObj("rowW9AGGI_RUOLO_"  + idImpresa, false);
			}
		}
	}

	function verificaMandataria(idImpresa, ruolo, tipo) {
		if (tipo == '1') {
			if (ruolo == '1') {
				showObj("rowW9AGGI_IMPORTO_AGGIUDICAZIONE_"  + idImpresa, true);
				showObj("rowW9AGGI_PERC_RIBASSO_AGG_"  + idImpresa, true);
				showObj("rowW9AGGI_PERC_OFF_AUMENTO_"  + idImpresa, true);
			} else {
				setValue("W9AGGI_IMPORTO_AGGIUDICAZIONE_" + idImpresa, "");
				setValue("W9AGGI_PERC_RIBASSO_AGG_" + idImpresa, "");
				setValue("W9AGGI_PERC_OFF_AUMENTO_" + idImpresa, "");
				showObj("rowW9AGGI_IMPORTO_AGGIUDICAZIONE_"  + idImpresa, false);
				showObj("rowW9AGGI_PERC_RIBASSO_AGG_"  + idImpresa, false);
				showObj("rowW9AGGI_PERC_OFF_AUMENTO_"  + idImpresa, false);
			}
		}
	}

	function bloccaCampoNomimp(idImpresa, valore) {
		if (valore != '') {
			valore = getValue("W9AGGI_FLAG_AVVALIMENTO_" + idImpresa);
			if (valore >= 1 && valore <= 3) {
				showObj("rowIMPR_NOMEST_AUSILIARIA_"  + idImpresa, true);	
				showObj("rowW9AGGI_CODIMP_AUSILIARIA_"  + idImpresa, true);	
			} else {
				document.getElementById("IMPR_NOMEST_AUSILIARIA_"+ idImpresa).value="";
				showObj("rowIMPR_NOMEST_AUSILIARIA_"  + idImpresa, false);
				document.getElementById("W9AGGI_CODIMP_AUSILIARIA_"+ idImpresa).value="";
				showObj("rowW9AGGI_CODIMP_AUSILIARIA_"  + idImpresa, false);
			}
		} else {
			document.getElementById("IMPR_NOMEST_AUSILIARIA_"+ idImpresa).value="";
			showObj("rowIMPR_NOMEST_AUSILIARIA_"  + idImpresa, false);
			document.getElementById("W9AGGI_CODIMP_AUSILIARIA_"+ idImpresa).value="";
			showObj("rowW9AGGI_CODIMP_AUSILIARIA_"  + idImpresa, false);
		}
	}

</gene:javaScript>
