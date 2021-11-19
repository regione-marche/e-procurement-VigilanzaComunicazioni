<%
/*
 * Created on: 20/03/2012
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
<c:set var="keyW9lott_entinaz" value="${keyW9inca}" scope="request"/>

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">

		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W9LNCODGARA" value="${item[0]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W9LNCODLOTT" value="${item[1]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CIG_${param.contatore}" campoFittizio="true" definizione="T10;0;;;W9LNCIG" value="${item[2]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="OGGETTO_${param.contatore}" campoFittizio="true" definizione="T1024;0;;NOTE;W9LNOGGETTO" value="${item[3]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMPORTO_TOT_${param.contatore}" campoFittizio="true" definizione="F15;0;;MONEY;W9LNIMPTOT" value="${item[4]}" />
		
		<gene:campoScheda campo="CPV_${param.contatore}" entita="W9LOTT_ENTINAZ"
			href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"}, ${param.contatore})'
			modificabile="false" speciale="true" campoFittizio="true" definizione="T12;0;;;W9LNCPV" value="${item[5]}">
			<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
				<gene:popupCampo titolo="Dettaglio codice CODCPV"
					href='formCPV(${modoAperturaScheda ne "VISUALIZZA"}, ${param.contatore})' />
			</c:if>
		</gene:campoScheda>
		<gene:campoScheda campo="CPVDESC_${param.contatore}" title="Descrizione CPV"
			value="${item[6]}" campoFittizio="true" modificabile="false" definizione="T150" />
		
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="ID_CATEGORIA_PREVALENTE_${param.contatore}" campoFittizio="true" definizione="T10;0;W3z03;;WLNID_CATE" value="${item[7]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CLASCAT_${param.contatore}" campoFittizio="true" definizione="T10;0;W3z11;;W9LNCLASCAT" value="${item[8]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMP_AGG_${param.contatore}" campoFittizio="true" definizione="T100;0;;;W9LNIMPAGG" value="${item[9]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMPORTO_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true" definizione="F15;0;;MONEY;W9LNIMP_AGGI" value="${item[10]}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="DATA_VERB_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true" definizione="D;0;;;W9LNDVERB" value="${item[11]}" />

		<gene:campoScheda campo="VISUALIZZAFILEALLEGATO_${param.contatore}" title="File esito"
			campoFittizio="true" modificabile="false" definizione="T200;0" visibile='${modo ne "NUOVO"}'>
			<c:choose>
				<c:when test='${empty item[12].value}'>
					Nessun File Allegato
				</c:when>
				<c:when test='${item[12].value eq "1"}'>
					<a href='javascript:apriAllegato(${param.contatore});'>Visualizza</a>
					<c:if test='${modoAperturaScheda eq "MODIFICA"}'>
						&nbsp;&nbsp;<A id="icoVISUALIZZAFILEALLEGATO_${param.contatore}"
							href="javascript:showMenuPopup('icoVISUALIZZAFILEALLEGATO_${param.contatore}',linksetJsPopUpVISUALIZZAFILEALLEGATO_${param.contatore});"><IMG
							src="${pageContext.request.contextPath}/img/opzioni_info.gif" title="" alt="" height="16"
							width="16"></A>
					</c:if>
				</c:when>
			</c:choose>
		</gene:campoScheda>
		
	<c:if test='${modoAperturaScheda eq "MODIFICA"}'>
		<gene:campoScheda campo="NOMEFILEALLEGATO_${param.contatore}"
			title="Seleziona il File PDF per la pubblicazione sul sito della Regione"
			campoFittizio="true" modificabile="false" definizione="T200;0">
					<input name="selFile[${param.contatore}]" id="selFile[${param.contatore}]" size="50" type="file" 
					onchange='javascript:scegliFile(${param.contatore});' onkeydown="blur()" onkeyup="blur()"/>
				&nbsp;&nbsp;
		</gene:campoScheda>
	</c:if>
		<gene:campoScheda campo="FILEDAALLEGARE_${param.contatore}" entita="W9LOTT_ENTINAZ" campoFittizio="true" visibile="false" definizione="T200;0" />

	</c:when>
	<c:when test="${param.tipoDettaglio eq 2}">
		<gene:campoScheda>
			<td colspan="2">Nessun lotto ed esito inserito.</td>
		</gene:campoScheda>
	</c:when>
	<c:otherwise>

		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CODGARA_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W9LNCODGARA" value="${codgara}" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CODLOTT_${param.contatore}" visibile="false" campoFittizio="true" definizione="N10;1;;;W9LNCODLOTT" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CIG_${param.contatore}" campoFittizio="true" definizione="T10;0;;;W9LNCIG" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="OGGETTO_${param.contatore}" campoFittizio="true" definizione="T1024;0;;NOTE;W9LNOGGETTO" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMPORTO_TOT_${param.contatore}" campoFittizio="true" definizione="F15;0;;MONEY;W9LNIMPTOT" />
		
		<gene:campoScheda campo="CPV_${param.contatore}" entita="W9LOTT_ENTINAZ"
			href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"}, ${param.contatore})'
			modificabile="false" speciale="true" campoFittizio="true" definizione="T12;0;;;W9LNCPV" >
			<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
				<gene:popupCampo titolo="Dettaglio codice CODCPV"
					href='formCPV(${modoAperturaScheda ne "VISUALIZZA"}, ${param.contatore})' />
			</c:if>
		</gene:campoScheda>
		<gene:campoScheda campo="CPVDESC_${param.contatore}" title="Descrizione CPV"
			value="" campoFittizio="true" modificabile="false" definizione="T150" />
		
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="ID_CATEGORIA_PREVALENTE_${param.contatore}" campoFittizio="true" definizione="T10;0;W3z03;;WLNID_CATE" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="CLASCAT_${param.contatore}" campoFittizio="true" definizione="T10;0;W3z11;;W9LNCLASCAT" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMP_AGG_${param.contatore}" campoFittizio="true" definizione="T100;0;;;W9LNIMPAGG" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="IMPORTO_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true" definizione="F15;0;;MONEY;W9LNIMP_AGGI" />
		<gene:campoScheda entita="W9LOTT_ENTINAZ" campo="DATA_VERB_AGGIUDICAZIONE_${param.contatore}" campoFittizio="true" definizione="D;0;;;W9LNDVERB" />
		
		<gene:campoScheda campo="VISUALIZZAFILEALLEGATO_${param.contatore}" title="File esito"
			campoFittizio="true" modificabile="false" definizione="T200;0" visibile='${modo eq "VISUALIZZA"}'>
					Nessun File Allegato
		</gene:campoScheda>
		<gene:campoScheda campo="NOMEFILEALLEGATO_${param.contatore}"
			title="Seleziona il File PDF per la pubblicazione sul sito della Regione"
			campoFittizio="true" modificabile="false" definizione="T200;0">
					<input name="selFile[${param.contatore}]" id="selFile[${param.contatore}]" type="file" size="50"
					onchange='javascript:scegliFile(${param.contatore});' onkeydown="blur()" onkeyup="blur()"/>
				&nbsp;&nbsp;
		</gene:campoScheda>
		<gene:campoScheda campo="FILEDAALLEGARE_${param.contatore}" entita="W9LOTT_ENTINAZ" campoFittizio="true" visibile="false" definizione="T200;0" />

	</c:otherwise>
</c:choose>
