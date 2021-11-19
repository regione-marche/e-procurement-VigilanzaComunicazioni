
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
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

	<gene:redefineInsert name="listaNuovo" />
	<gene:redefineInsert name="pulsanteListaInserisci" />

	<gene:redefineInsert name="listaEliminaSelezione" />
	<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
	
<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="FILET2" pagesize="20" tableclass="datilista"
		  sortColumn="2" gestisciProtezioni="true"
			where='FILET2.CODGARA = #W9LOTT.CODGARA# and FILET2.CODLOTT=#W9LOTT.CODLOTT#'>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"	onClick="chiaveRiga='${chiaveRigaJava}'">
						<gene:PopUpItemResource	resource="popupmenu.tags.lista.visualizza" title="Visualizza invio" />
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<%
				// Campi veri e propri
			%>
			<c:set var="link"	value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="CODGARA" visibile="false"/>
			<gene:campoLista campo="CODLOTT" visibile="false"/>
			<gene:campoLista campo="DESCPROD" headerClass="sortable" href="${link}" title="Descrizione prodotto" />
			<gene:campoLista campo="RAG_SOC_FORN" headerClass="sortable" title="Ragione sociale fornitore" />
		</gene:formLista></td>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>
