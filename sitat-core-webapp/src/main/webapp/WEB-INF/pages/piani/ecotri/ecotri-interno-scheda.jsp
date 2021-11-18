<%
	/*
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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<c:choose>

	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda title="Programma" entita="ECOTRI" campo="CONTRI_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;T2ECONTRI" value="${item[0]}"/>
		<gene:campoScheda title="Lavoro" entita="ECOTRI" campo="CONECO_${param.contatore}" campoFittizio="true" visibile="false" definizione="N5;1;;;T2ECONECO" value="${item[1]}"/>
		<gene:campoScheda title="Descrizione" entita="ECOTRI" campo="DESCRI_${param.contatore}" campoFittizio="true" visibile="true" definizione="T2000;0;;;T2EDESCRI" value="${item[2]}" />
		<gene:campoScheda title="Codice CUP" entita="ECOTRI" campo="CUPPRG_${param.contatore}" campoFittizio="true" visibile="true" definizione="T15;0;;;T2ECUPPRG" value="${item[3]}">
			<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
				<c:if test='${esisteProfiloCup eq "TRUE"}'>
					<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
				</c:if>
			</c:if>
		</gene:campoScheda>
		<gene:campoScheda title="Stima lavori" entita="ECOTRI" campo="STIMA_${param.contatore}" campoFittizio="true" visibile="true" definizione="N24,5;0;;MONEY;T2ESTIMA" value="${item[4]}"/>
	</c:when>

	<c:otherwise>
		<gene:campoScheda title="Programma" entita="ECOTRI" campo="CONTRI_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;T2ECONTRI" value="${param.chiave}"/>
		<gene:campoScheda title="Lavoro" entita="ECOTRI" campo="CONECO_${param.contatore}" campoFittizio="true" visibile="false" definizione="N5;1;;;T2ECONECO" />
		<gene:campoScheda title="Descrizione" entita="ECOTRI" campo="DESCRI_${param.contatore}" campoFittizio="true" visibile="true" definizione="T2000;0;;;T2EDESCRI"/>
		<gene:campoScheda title="Codice CUP" entita="ECOTRI" campo="CUPPRG_${param.contatore}" campoFittizio="true" visibile="true" definizione="T15;0;;;T2ECUPPRG">
			<c:if test='${esisteProfiloCup eq "TRUE"}'>
				<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
			</c:if>
		</gene:campoScheda>
		<gene:campoScheda title="Stima lavori" entita="ECOTRI" campo="STIMA_${param.contatore}" campoFittizio="true" visibile="true" definizione="N24,5;0;;MONEY;T2ESTIMA"/>
	</c:otherwise>

</c:choose>

<gene:javaScript>
function richiestaListaCUP() {
	openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=ECOTRI_CUPPRG&codiceCUP=" + getValue("ECOTRI_CUPPRG"), "listaCUP", 900, 550, 1, 1);
}
</gene:javaScript>
	

