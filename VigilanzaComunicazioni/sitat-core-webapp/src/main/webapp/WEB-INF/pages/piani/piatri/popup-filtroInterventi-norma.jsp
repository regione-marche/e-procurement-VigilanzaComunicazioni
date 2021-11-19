<%
/* 
 * Created on: 05-04-2012
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Form di ricerca dei tecnici */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="listaOpzioniDisponibili" value="${fn:join(opzDisponibili,'#')}#"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="moduloFabbisogniAttivo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.pt.moduloFabbisogniAttivo")}' />
<c:set var="checkProt_FiltroUffintListaProposte" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.FiltroUffintListaProposte")}'/>

<c:set var="filtroUffint" value=""/> 
<c:if test="${!empty sessionScope.uffint}">
	<c:set var="filtroUffint" value=" and TECNI.CGENTEI = '${sessionScope.uffint}'"/>
</c:if>

<gene:template file="popup-template.jsp" >
	<gene:redefineInsert name="gestioneHistory" />
	<gene:redefineInsert name="addHistory" />
	<gene:setString name="titoloMaschera" value="Imposta filtro"/>
	
	<c:set var="modo" value="MODIFICA" scope="request" />

	<gene:redefineInsert name="corpo">
  		<gene:formTrova entita="INTTRI"  >
  			<gene:campoTrova campo="CONTRI" />
  				<c:if test='${moduloFabbisogniAttivo eq "1" && !checkProt_FiltroUffintListaProposte}'>
					<gene:campoTrova campo="NOMEIN" title="Ente/Ufficio fabbisogno" entita="UFFINT" from="PTAPPROVAZIONI" where="PTAPPROVAZIONI.ID_PROGRAMMA=INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA=INTTRI.CONINT and PTAPPROVAZIONI.CONINT in (SELECT CONINT FROM FABBISOGNI WHERE CODEIN=UFFINT.CODEIN)"/>
				</c:if>
				<gene:campoTrova campo="CUIINT" />
				<gene:campoTrova campo="CODINT" />
				<gene:campoTrova campo="NPROGINT" />
				<gene:campoTrova campo="DESINT" />
				<gene:campoTrova campo="ANNRIF" />

				<gene:campoTrova campo="CUPPRG" />
				<gene:campoTrova title="RUP" campo="NOMTEC" entita="TECNI" where="TECNI.CODTEC=INTTRI.CODRUP ${filtroUffint}" /> 
				<gene:campoTrova campo="TOTINT" />

			<input type="hidden" name="azione" id="azione" value="" />
			
		</gene:formTrova>
		
		<gene:javaScript>
				
			document.forms[0].jspPathTo.value="piani/piatri/popup-listaInterventi-norma.jsp";
			
			var trovaEsegui_Default = trovaEsegui;
			function trovaEseguiCustom() {
				window.opener.bloccaRichiesteServer();
				trovaEsegui_Default();
			}

			trovaEsegui = trovaEseguiCustom;

			$(document).ready(function() {
		<c:choose>
			<c:when test='${param.annulla eq 1}'>
				<c:remove var="filtroINTTRI" scope="session" />
				<c:remove var="trovaINTTRI" scope="session" />
				window.opener.document.forms[0].trovaAddWhere.value = "";
				window.opener.document.forms[0].trovaParameter.value = "";
				window.opener.selezionaPagina(2);
				window.close();
			</c:when>
			<c:otherwise>
				var localKeyParent = window.opener.document.forms[0].keyParent.value;
				var contri = localKeyParent.substring(localKeyParent.indexOf(":")+1);
				setValue("Campo0", contri);
				showObj("rowCampo0", false);					
			</c:otherwise>
		</c:choose>
			});
			
		</gene:javaScript>
		
	</gene:redefineInsert>
</gene:template>
