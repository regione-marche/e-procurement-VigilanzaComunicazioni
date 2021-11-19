<%
  /* 
   * Created on 09-Gen-2009
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="listaOpzioniUtenteAbilitate" value="${fn:join(profiloUtente.funzioniUtenteAbilitate,'#')}#" />
	
	<fmt:setBundle basename="AliceResources" />
	<c:set var="nomeEntitaParametrizzata">
		<fmt:message key="label.tags.uffint.multiplo" />
	</c:set>
	<c:if test="${! empty sessionScope.profiloUtente and empty isUserRup}">
		<c:set var="isUserRup" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetCategoriaUserFunction", pageContext)}' scope="session"/>
	</c:if>
<script type="text/javascript">
<!-- 
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.ARCHIVI.Archivio-staz-appaltanti")}'>
		<c:choose>
			<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.GENE.SchedaUffint.Utenti")}'>
				linksetSubMenuArchivi += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=gene/uffint/uffint-trova.jsp", 1246, "Archivio ${fn:toLowerCase(nomeEntitaParametrizzata)}");
			</c:when>
			<c:otherwise>
				linksetSubMenuArchivi += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=gene/uffint/admin-uffint-trova.jsp", 1246, "Archivio ${fn:toLowerCase(nomeEntitaParametrizzata)}");
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.ARCHIVI.Archivio-centricosto")}'>
	linksetSubMenuArchivi += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/centricosto/centricosto-trova.jsp", 1247, "Archivio centri di costo");
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.ARCHIVI.Archivio-uffici") and (sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C")}'>
	linksetSubMenuArchivi += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/uffici/uffici-lista.jsp", 1248, "Archivio uffici");
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.AMMINISTRAZIONE.AttribuzioneIncarichi")}'>
	linksetSubMenuUtilita += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/w9cf_regpermessi/w9cf_regpermessi-scheda.jsp", 1280, "Modelli di attribuzione incarichi");
	</c:if>
	
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.PRODOTTI")}'>
	linksetSubMenuUtilita += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/commons/t2-import.jsp", 1277, "Importazione file T2");
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.GRUPPO-RUP")}'>
		<c:if test="${! empty sessionScope.profiloUtente and (sessionScope.profiloUtente.abilitazioneStd eq 'A' || isUserRup eq '1')}">
			linksetSubMenuUtilita += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/w9deleghe/w9deleghe-lista.jsp", 1278, "Gestisci gruppo di collaborazione");
		</c:if>
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.UTILITA.Subentro")}'>
		<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			linksetSubMenuUtilita += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/commons/subentro.jsp", 1279, "Trasferimento dati per subentro");
		</c:if>
	</c:if>
	<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.UTILITA.ApplicationKeys")}'>
		<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			linksetSubMenuUtilita += creaVoceSubmenu("${contextPath}/ApriPagina.do?"+csrfToken+"&href=w9/w_application_keys/w_application_keys-trova.jsp", 1280, "Credenziali servizi di pubblicazione");
		</c:if>
	</c:if>
function initPage(){
	}

-->
</script>
