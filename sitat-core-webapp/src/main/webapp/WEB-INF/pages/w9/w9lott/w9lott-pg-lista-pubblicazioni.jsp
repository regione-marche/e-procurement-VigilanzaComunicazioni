
<%
	/*
	 * Created on 28-nov-2008
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

<!-- Settaggio delle stringhe utilizate nel template -->

<c:set var="codiceGara" value='${gene:getValCampo(key,"CODGARA")}'
	scope="request" />
<c:set var="codiceLotto" value='${gene:getValCampo(key,"CODLOTT")}'
	scope="request" />

<gene:set name="titoloMenu" scope="requestScope">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td><c:set var="wherePubblicazioniLotti"
				value=" W9PUBBLICAZIONI.CODGARA = ${codiceGara} AND W9PUBBLICAZIONI.NUM_PUBB IN (SELECT W9PUBBLICAZIONI.NUM_PUBB FROM W9PUBBLICAZIONI JOIN W9PUBLOTTO ON W9PUBBLICAZIONI.CODGARA = W9PUBLOTTO.CODGARA AND W9PUBBLICAZIONI.NUM_PUBB = W9PUBLOTTO.NUM_PUBB WHERE W9PUBLOTTO.CODGARA = ${codiceGara} AND W9PUBLOTTO.CODLOTT = ${codiceLotto})" />

			<gene:formLista entita="W9PUBBLICAZIONI" pagesize="20"
				tableclass="datilista" gestisciProtezioni="true"
				where="${wherePubblicazioniLotti}" sortColumn='2'>

				<gene:campoLista title="Opzioni <center>${titoloMenu}</center>">
					<!--input type="checkbox" name="keys" value="${chiaveRiga}" / -->
					<gene:PopUp variableJs="rigaPopUpMenu" />
				</gene:campoLista>

				<gene:campoLista campo="NUM_PUBB" headerClass="sortable" />
				
				<gene:campoLista campo="TIPDOC" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreFiltroIDSceltaContraente" headerClass="sortable"
				href="${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=${chiaveRigaJava}&tipoPubblicazione=${datiRiga.W9PUBBLICAZIONI_TIPDOC}"/>

				<gene:redefineInsert name="addPulsanti">
					<gene:redefineInsert name="pulsanteListaInserisci" />
					<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
				</gene:redefineInsert>

				<gene:redefineInsert name="listaNuovo" />
				<gene:redefineInsert name="listaEliminaSelezione" />

			</gene:formLista>
		</td>
	</tr>
</table>