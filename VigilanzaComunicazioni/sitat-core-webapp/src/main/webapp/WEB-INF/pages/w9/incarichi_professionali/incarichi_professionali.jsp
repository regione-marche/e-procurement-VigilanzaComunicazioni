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
<c:set var="codlott" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="num" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="keyW9inca" value="${keyW9inca}" scope="request"/>
<c:choose>
	<c:when test='${not empty requestScope.sezioneArtE1}'>
		<c:set var="sez" value='${requestScope.sezioneArtE1}' />
	</c:when>
	<c:otherwise>
		<c:set var="sez" value='${gene:getValCampo(keyW9inca,"SEZIONE")}' />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda entita="W9INCA" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3INCODGAR" value="${item[0]}" />
		<gene:campoScheda entita="W9INCA" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3INNUMLOT" value="${item[1]}" />
		<gene:campoScheda entita="W9INCA" campo="NUM_${param.contatore}" visibile="false" modificabile="false" campoFittizio="true" definizione="N3;1;;;W3NUM13" value="${item[2]}" />
		<gene:campoScheda entita="W9INCA" campo="SEZIONE_${param.contatore}" visibile="false" campoFittizio="true" definizione="T5;1;;;W3SEZIONE" value="${item[3]}" />
		<gene:campoScheda entita="W9INCA" campo="NUM_INCA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;W3NUMINCA" value="${item[4]}" />
		<gene:archivio titolo="TECNICI" formName="formArchivioIntestazione_${param.contatore}"
			lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9INCA.CODTEC"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9INCA_CODTEC_${param.contatore}" >
			<gene:campoScheda campo="CODTEC_${param.contatore}" value="${item[9]}" entita="W9INCA" campoFittizio="true" definizione="T10;0;;;CODTEC1" visibile="false"/>
			<gene:campoScheda campo="NOMTEC_${param.contatore}" entita="TECNI" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetDescFromArchFunction",pageContext,item[9],"W9INCA")}' where="TECNI.CODTEC=${item[9]}" campoFittizio="true" definizione="T61;0;;;NOMTEC1" title="Intestazione" />
		</gene:archivio>
		
		<c:choose>
			<c:when test='${not empty param.keyW9appa}'>
				<gene:campoScheda entita="W9INCA" campo="ID_RUOLO_${param.contatore}" campoFittizio="true" definizione="N7;0;W3004;;W3ID_RUOLO" value="${item[5]}" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroFlagAvvalimento" />
			</c:when>
			<c:otherwise>
				<gene:campoScheda entita="W9INCA" campo="ID_RUOLO_${param.contatore}" campoFittizio="true" definizione="N7;0;W3004;;W3ID_RUOLO" value="${item[5]}" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroRuoloIncaricato" />
			</c:otherwise>
		</c:choose>
		<gene:campoScheda entita="W9INCA" campo="CIG_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="T10;0;;;W3CIG_PROG" value="${item[6]}" />
		<gene:campoScheda entita="W9INCA" campo="DATA_AFF_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="D;0;;;W3DATA_AFF" value="${item[7]}" />
		<gene:campoScheda entita="W9INCA" campo="DATA_CONS_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="D;0;;;W3DATA_CON" value="${item[8]}" />
	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda>
			<td colspan="2">Nessun incarico professionale inserito.</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda entita="W9INCA" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3INCODGAR" value="${codgara}" />
		<gene:campoScheda entita="W9INCA" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W3INNUMLOT" value="${codlott}" />
		<gene:campoScheda entita="W9INCA" campo="NUM_${param.contatore}" visibile="false" modificabile="false" campoFittizio="true" definizione="N3;1;;;W3NUM13" value="${num}" />
		<gene:campoScheda entita="W9INCA" campo="SEZIONE_${param.contatore}" visibile="false" campoFittizio="true" definizione="T5;1;;;W3SEZIONE" value="${sez}" />
		<gene:campoScheda entita="W9INCA" campo="NUM_INCA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N3;1;;;W3NUMINCA" />
		<gene:archivio titolo="TECNICI" formName="formArchivioIntestazione_${param.contatore}"
			lista="gene/tecni/tecni-lista-popup.jsp"
			scheda="gene/tecni/tecni-scheda.jsp"
			schedaPopUp="gene/tecni/tecni-scheda-popup.jsp" 
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9INCA_CODTEC_${param.contatore}">
			<gene:campoScheda campo="CODTEC_${param.contatore}" entita="W9INCA" campoFittizio="true" definizione="T10;0;;;W3CODTEC" visibile="false"/>
			<gene:campoScheda campo="NOMTEC_${param.contatore}" title="Intestazione" entita="TECNI" campoFittizio="true" definizione="T61;0;;;NOMTEC1"  />
		</gene:archivio>
		
		<c:choose>
			<c:when test='${not empty param.keyW9appa}'>
				<gene:campoScheda entita="W9INCA" campo="ID_RUOLO_${param.contatore}" campoFittizio="true" definizione="N7;0;W3004;;W3ID_RUOLO" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroFlagAvvalimento" />
			</c:when>
			<c:otherwise>
				<gene:campoScheda entita="W9INCA" campo="ID_RUOLO_${param.contatore}" campoFittizio="true" definizione="N7;0;W3004;;W3ID_RUOLO" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroRuoloIncaricato" />
			</c:otherwise>
		</c:choose>
		<gene:campoScheda entita="W9INCA" campo="CIG_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="T10;0;;;W3CIG_PROG" />
		<gene:campoScheda entita="W9INCA" campo="DATA_AFF_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="D;0;;;W3DATA_AFF" />
		<gene:campoScheda entita="W9INCA" campo="DATA_CONS_PROG_ESTERNA_${param.contatore}" campoFittizio="true" definizione="D;0;;;W3DATA_CON" />
	</c:otherwise>
</c:choose>	

<c:if test="${param.tipoDettaglio ne 2}">
	<gene:fnJavaScriptScheda funzione="bloccaCampoDataAff(${param.contatore}, '#W9INCA_ID_RUOLO_${param.contatore}#')" elencocampi="W9INCA_ID_RUOLO_${param.contatore}" esegui="true" />
	<gene:fnJavaScriptScheda funzione="cambioSezione(${param.contatore}, '#W9INCA_ID_RUOLO_${param.contatore}#', '#W9INCA_SEZIONE_${param.contatore}#')" elencocampi="W9INCA_ID_RUOLO_${param.contatore}" esegui="false" />
</c:if>

<gene:javaScript>
	function bloccaCampoDataAff(idIncarico, idRuolo) {
		if ('2' == idRuolo) {
			showObj("rowW9INCA_CIG_PROG_ESTERNA_"  + idIncarico, true);	
			showObj("rowW9INCA_DATA_AFF_PROG_ESTERNA_"  + idIncarico, true);	
			showObj("rowW9INCA_DATA_CONS_PROG_ESTERNA_"  + idIncarico, true);	
		} else {
			setValue('W9INCA_CIG_PROG_ESTERNA_' + idIncarico, "");
			setValue('W9INCA_DATA_AFF_PROG_ESTERNA_' + idIncarico, "");
			setValue('W9INCA_DATA_CONS_PROG_ESTERNA_' + idIncarico, "");
			showObj("rowW9INCA_CIG_PROG_ESTERNA_" + idIncarico, false);
			showObj("rowW9INCA_DATA_AFF_PROG_ESTERNA_" + idIncarico, false);
			showObj("rowW9INCA_DATA_CONS_PROG_ESTERNA_" + idIncarico, false);
		}
	}
	
	function cambioSezione(idIncarico, idRuolo, sezioneIncarico) {
		if(sezioneIncarico != '' && (sezioneIncarico == "PA" || sezioneIncarico == "RA")) {
			if((idRuolo > 4 && idRuolo < 9) || (idRuolo > 13 && idRuolo < 19)) {
				setValue("W9INCA_SEZIONE_" + idIncarico, "RA");
			} else {
				setValue("W9INCA_SEZIONE_" + idIncarico, "PA");
			}
		}
	}
</gene:javaScript>