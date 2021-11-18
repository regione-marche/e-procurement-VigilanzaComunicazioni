<%
/*
 * Created on 11-apr-2017
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>
<%@ page language="java" import="it.eldasoft.utils.properties.ConfigManager" %>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" name="formMigrazioneSA" action="${pageContext.request.contextPath}/w9/MigraSA.do?_csrf=${sessionScope.OWASP_CSRFTOKEN}" >
	<input type="hidden" name="metodo" value="" >
	<table class="dettaglio-notab">
    <tr>
		  <td class="valore-dato">
			  <br>
				Fase 1: recupero dati da Simog
				<br><br>
		  </td>
	    <td class="valore-dato">
	    	<br>
	    	${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate} / ${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}
	    	<br><br>
		  </td>
		  <td class="valore-dato">
	    	<br>
	   		<c:choose>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and  empty sessionScope.MIGRAZIONE_SA_BEAN.errore}">
	   				<img alt="Operazione in corso" src="${pageContext.request.contextPath}/img/wait.gif" width="20px" >
	   			</c:when>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate le sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and fn:containsIgnoreCase(sessionScope.MIGRAZIONE_SA_BEAN.errore, 'scaricaxml')}">
	   				<img alt="Operazione interrotta" src="${pageContext.request.contextPath}/img/flag_rosso.gif" width="20px" >
	   			</c:when>
	   			<c:otherwise>
	   				<img alt="Operazione completata" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
	   			</c:otherwise>
	   		</c:choose>
	    	<br><br>
		  </td>
    </tr>

	<c:if test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareRecuperate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
    <tr>
      <td class="valore-dato"> 
				<br>
				Fase 2: controllo preliminare dei dati
				<br><br>
      </td>
			<td class="valore-dato">
				<br>
				${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate} / ${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}
				<br><br>
			</td>
			<td class="valore-dato">
				<br>
	   		<c:choose>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and empty sessionScope.MIGRAZIONE_SA_BEAN.errore}">
	   				<img alt="Operazione in corso" src="${pageContext.request.contextPath}/img/wait.gif" width="20px" >
	   			</c:when>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate le sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and fn:containsIgnoreCase(sessionScope.MIGRAZIONE_SA_BEAN.errore, 'controlli')}">
	   				<img alt="Operazione interrotta" src="${pageContext.request.contextPath}/img/flag_rosso.gif" width="20px" >
	   			</c:when>
	   			<c:otherwise>
	   				<img alt="Operazione completata" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
	   			</c:otherwise>
	   		</c:choose>
				<br><br>
			</td>
    </tr>
  </c:if>

<c:choose>
	<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
						and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq 0 and empty sessionScope.MIGRAZIONE_SA_BEAN.errore}">
		<tr>
			<td class="valore-dato" colspan="3">
				<c:choose>
					<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare eq 1}">
						<br>
						La gara sar&agrave; migrata alla stazione appaltante indicata su SIMOG. Continuare?
						<br><br>
					</c:when>
					<c:otherwise>
						<br>
						Le gare saranno migrate alle stazioni appaltanti indicate su SIMOG. Continuare?
						<br><br>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:when>
	<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
							and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate gt 0 
							and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}">
    <tr>
      <td class="valore-dato"> 
				<br>
				Fase 3: migrazione gare alla stazione appaltante ${sessionScope.MIGRAZIONE_SA_BEAN.nomeStazAppDestinazione} (C.F. ${sessionScope.MIGRAZIONE_SA_BEAN.codiceFiscaleStazAppDestinazione})
				<br><br>
      </td>
			<td class="valore-dato">
				<br>
				${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate} / ${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}
				<br><br>
			</td>
			<td class="valore-dato">
				<br>
	   		<c:choose>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and empty sessioneScope.MIGRAZIONE_SA_BEAN.errore}">
	   				<img alt="Operazione in corso" src="${pageContext.request.contextPath}/img/wait.gif" width="20px" >
	   			</c:when>
	   			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate lt sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
	   										and fn:containsIgnoreCase(sessionScope.MIGRAZIONE_SA_BEAN.errore, 'updatesa')}">
	   				<img alt="Operazione interrotta" src="${pageContext.request.contextPath}/img/flag_rosso.gif" width="20px" >
	   			</c:when>
	   			<c:otherwise>
	   				<img alt="Operazione completata" src="${pageContext.request.contextPath}/img/flag_verde.gif" width="20px" >
	   			</c:otherwise>
	   		</c:choose>
				<br><br>
			</td>
    </tr>
	</c:when>
</c:choose>
	
	<c:if test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare}" >
		<tr>
      <td class="valore-dato" colspan="3">
				<br>
				<c:choose>
					<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare eq 1}" >
						La gara &egrave; stata migrata correttamente alla nuova stazione appaltante. Per completare l'operazione, effettuare un invio dell'anagrafica
					</c:when>
					<c:otherwise>
						Le gare sono state migrate correttamente alla nuova stazione appaltante. Per completare l'operazione, effettuare un invio dell'anagrafica
					</c:otherwise>
				</c:choose>
				<br><br>
			</td>
		</tr>
	</c:if>
	
	<tr>
		<td class="comandi-dettaglio" colspan="3">
			<c:if test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
							and empty sessionScope.MIGRAZIONE_SA_BEAN.errore 
			        and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq 0}" >
				<INPUT type="button" class="bottone-azione" value="Continua ?" title="Continua ?" onclick="javascript:eseguiMigrazione();">&nbsp;	
			</c:if>
		<c:choose>	
			<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
							and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
							and empty sessionScope.MIGRAZIONE_SA_BEAN.errore }" >
				<INPUT type="button" class="bottone-azione" value="Esci" title="Esci" onclick="javascript:esci();">&nbsp;	
			</c:when>
			<c:otherwise>
				<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();">&nbsp;
			</c:otherwise>
		</c:choose>		
		</td>
	</tr>
	
	</table>
</form>
