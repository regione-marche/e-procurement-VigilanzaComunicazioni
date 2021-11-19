<%
	/*
	 * Created on 16-feb-2012
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:template file="scheda-template.jsp" >

	<gene:setString name="titoloMaschera" value='Esito richiesta creazione nuova gara in SIMOG' />
	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="schedaModifica" >
		<tr>
			<td class="vocemenulaterale">
				<a href="javascript:creaGara();" title="Crea nuova gara" tabindex="1501">Crea nuova gara</a>
			</td>
		</tr>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="schedaNuovo" >
		<tr>
			<td class="vocemenulaterale">
				<a href="javascript:creaLotto('${requestScope.IDAVGARAfromSIMOG}');" title="Aggiungi lotto alla gara" tabindex="1502">
				Aggiungi lotto alla gara</a>
			</td>
		</tr>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="corpo">
		<table class="dettaglio-tab">

	<c:choose>
		<c:when test="${requestScope.ESITO eq 'OK'}">
			<tr>
				<td>
					<b>Esito operazione:</b> operazione conclusa correttamente. La gara &egrave; stata creata in SIMOG<br><br>
				</td>
			</tr>
			<tr>
				<td>
					<b>Codice gara restituito dall'AVCP: </b> ${requestScope.IDAVGARAfromSIMOG}
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td>
					<b>Esito operazione:</b> operazione terminata con errore. La gara non &egrave; stata creata in SIMOG<br><br>
				</td>
			</tr>
			<tr>
				<td>
					<b>Operazione che ha generato l'errore:</b> ${requestScope.OPERAZIONE}
				</td>
			</tr>
			<tr>
				<td>
					<b>Dettaglio dell'errore:</b> ${requestScope.ERRORE}
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
			<tr>
				<td class="comandi-dettaglio" >
					<input type="button" alt="Torna alla home page" value="Home page" title="Home page" id="buttonhomePage" name="buttonHomePage" onclick="javascript:tornaHomePage();" />
					&nbsp;
					<input type="button" alt="Crea nuova gara" value="Crea nuova gara" title="Crea nuova gara" id="buttonCreaGara" name="buttonCreaGara" onclick="javascript:creaGara();" />
					&nbsp;
					<input type="button" alt="Aggiungi lotto alla gara" value="Aggiungi lotto alla gara" title="Aggiungi lotto alla gara" id="buttonCreaLotto" name="buttonCrea" onclick="javascript:creaLotto();" />
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</gene:redefineInsert>
	<gene:javaScript>
		function tornaHomePage() {
			document.location.href = "${pageContext.request.contextPath}/Home.do?" + csrfToken;
		}
	
		function creaGara() {
			document.location.href="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9gara/richiestaIdAvGara.jsp&modo=NUOVO";
		}
	
		function creaLotto(idAvGara) {
			var idAvGara = "${requestScope.IDAVGARAfromSIMOG}";
			var codGara = "${requestScope.codiceGARA}";
			var strHref = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9gara/richiestaCig.jsp&modo=NUOVO";
			if (idAvGara != "") {
				strHref = strHref + "&idAvGara="+idAvGara;
			}
			if (codGara != "") {
				strHref = strHref + "&codGara="+codGara;
			}
			document.location.href=strHref;
		}
	</gene:javaScript>
</gene:template>