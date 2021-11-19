<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="key" value="OITRI.CONTRI=N:${param.codiceProgramma}" />

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="listaOpere">
	<gene:redefineInsert name="addHistory" />
	
	<gene:setString name="titoloMaschera" value="Lista opere incompiute del programma precedente" />
	
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>

		<form name="listaOpere" id="listaOpere" method="post" action="RiportaOpere.do?numeroPopUp=1">
			<table class="lista">
				<tr id="rowLista">
					<td colspan="2">
						<display:table name="listaOpere" id="opera" class="datilista" sort="list"  >
							<display:column class="associadati" title="Opzioni<br><center><a href='javascript:selezionaTutti(document.forms[0].keys);' Title='Seleziona Tutti'> <img src='${pageContext.request.contextPath}/img/ico_check.gif' height='15' width='15' alt='Seleziona Tutti'></a>&nbsp;<a href='javascript:deselezionaTutti(document.forms[0].keys);' Title='Deseleziona Tutti'><img src='${pageContext.request.contextPath}/img/ico_uncheck.gif' height='15' width='15' alt='Deseleziona Tutti'></a></center>" style="width:50px">
								<center>
									<input type="checkbox" name="keys" id="keys" value="${opera[0].value};${opera[1].value};${param.codiceProgramma}" />
								</center>
							</display:column>
							<display:column title="CUP" sortable="true" headerClass="sortable" >
								${opera[2].value}
							</display:column>
							<display:column title="Descr. opera" sortable="true" headerClass="sortable" >
								${opera[3].value}
							</display:column>
							<display:column title="Anno ultimo q.e." sortable="true" headerClass="sortable" >
								${opera[4].value}
							</display:column>
							<display:column title="Perc. avan. lavori" sortable="true" headerClass="sortable" >
								${opera[5].value}
							</display:column>
						</display:table>
					</td>
				</tr>
				<tr id="rowPulsanti">
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" id="Riporta_opere" class="bottone-azione" value='Riporta opere' title='Riporta opere selezionate' onclick="javascript:riportaOpere()">
						<INPUT type="button"  class="bottone-azione" value='Annulla' title='Annulla' onclick="javascript:window.close()">
						&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</gene:redefineInsert>

	<gene:javaScript>
		var numero = 0;
	
		function riportaOpere() {
			var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
				return;
			} else {
				document.listaOpere.submit();
			}
		}

		function annulla() {
			window.close();
		}
		
	</gene:javaScript>
</gene:template>