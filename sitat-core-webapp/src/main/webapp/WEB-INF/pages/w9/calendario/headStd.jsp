<%/*
       * Created on 13-giu-2006
       *
       * Copyright (c) EldaSoft S.p.A.
       * Tutti i diritti sono riservati.
       *
       * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
       * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
       * aver prima formalizzato un accordo specifico con EldaSoft.
       */

      // PAGINA CHE CONTIENE LA PARTE STANDARD DELL'HEAD HTML
      %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setBundle basename="AliceResources" />
<c:set var="nomeEntitaParametrizzata">
	<fmt:message key="label.tags.uffint.singolo" />
</c:set>

<c:choose>
	<c:when test="${not empty param.title}">
		<TITLE>${param.title}</TITLE>
	</c:when>
	<c:otherwise>
		<TITLE>${applicationScope.appTitle}</TITLE>
	</c:otherwise>
</c:choose>

<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <%-- per forzare ultima versione di Internet Explorer --%>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="-1">
<link rel="shortcut icon"
	href="${contextPath}/img/favicon.ico">
<c:choose>
<c:when test="${!empty (applicationScope.pathCss)}">
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/jquery/ui/${applicationScope.pathCss}jquery-ui.css">
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/${applicationScope.pathCss}elda.css">
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/${applicationScope.pathCss}elda-custom.css">
</c:when>
<c:otherwise>
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/jquery/ui/std/jquery-ui.css">
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/std/elda.css">
<link rel="STYLESHEET" type="text/css"
	href="${contextPath}/css/std/elda-custom.css">
</c:otherwise>
</c:choose>
<script type="text/javascript"
	src="${contextPath}/js/general.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/navbarMenu.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/floatingMenu.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/popupMenu.js"></script>

<script type="text/javascript" src="${contextPath}/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/moment-2.2.1.min.js"></script>

<script type="text/javascript"
	src="${contextPath}/js/jquery-ui-1.8.23.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-it.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/custom.js"></script>
<script type="text/javascript">
//<!-- M.F. 20.10.2006 Setto il contextPath utilizzato nei javascript
var contextPath="${contextPath}";

if (navigator.appName == "Microsoft Internet Explorer" && !ie10) {
  document.write("<link REL='stylesheet' HREF='${contextPath}/css/${applicationScope.pathCss}elda-ie.css' TYPE='text/css'>");
} 

		function generaPopupOpzioniUtenteLoggato() {
			<elda:jsBodyPopup varJS="linkset" contextPath="${pageContext.request.contextPath}" chiudi="false">

			<c:if test='${profiloUtente.utenteLdap == 0 and gene:checkProt(pageContext,"SUBMENU.VIS.UTILITA.Cambia-password")}'>
				<elda:jsVocePopup functionJS="utLogCambiaPassword()" descrizione="Cambia password"/>	
			</c:if>
		<c:choose>
			<c:when test='${sessionScope.sentinellaCodProfiloUnico eq "1"}'>
				<c:if test='${sentinellaSelezionaUffint eq "1"}'>
				<elda:jsVocePopup functionJS="utLogCambiaUfficioIntestatario()" descrizione="Cambia ${fn:toLowerCase(nomeEntitaParametrizzata)}"/>	
				</c:if>
			</c:when>
			<c:otherwise>
				<elda:jsVocePopup functionJS="utLogCambiaProfilo()" descrizione="Cambia profilo"/>	
			</c:otherwise>
		</c:choose>
				
				<c:if test='${sentinellaAccessoAltroApplicativo eq "1"}'>
				<elda:jsVocePopup functionJS="accediAltroApplicativoLista('${contextPath}')" descrizione="Accedi ad altro applicativo"/>
				</c:if>
				
				<elda:jsVocePopup functionJS="utLogEsci()" descrizione="Esci"/>	
			</elda:jsBodyPopup>
			return linkset;
		}
		
		function utLogCambiaPassword() {
			document.location.href="${pageContext.request.contextPath}/geneAdmin/InitCambiaPasswordAdmin.do?" + csrfToken + "&metodo=cambioBase&provenienza=menu";
		}

		function utLogCambiaProfilo() {
			document.location.href="${pageContext.request.contextPath}/CheckProfilo.do?" + csrfToken;
		}
		
		function utLogCambiaUfficioIntestatario() {
			document.location.href="${pageContext.request.contextPath}/CheckUfficioIntestatario.do?" + csrfToken;
		}
		
		function utLogEsci() {
			if (confirm('Sei sicuro di volerti disconnettere?')) {
				document.location.href="${pageContext.request.contextPath}/Logout.do?" + csrfToken;
			}
		}
		
//-->
</script>
