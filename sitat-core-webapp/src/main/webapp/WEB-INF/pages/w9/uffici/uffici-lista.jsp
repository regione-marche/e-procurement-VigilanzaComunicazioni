
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
/* Lista dei tecnici progettisti */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:historyClear/>

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="ListaUffici">
<gene:setString name="titoloMaschera" value="Lista degli uffici"/>
	
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="UFFICI" where="UFFICI.CODEIN = '${sessionScope.uffint}'" sortColumn="3"
						pagesize="20" tableclass="datilista" gestisciProtezioni="true" pathScheda="w9/uffici/uffici-scheda.jsp">
					<gene:campoLista title="Opzioni<center>${titoloMenu}</center>"
						width="50">
						<gene:PopUp variableJs="rigaPopUpMenu" onClick="chiaveRiga='${chiaveRigaJava}'" />
						<c:if test='${gene:checkProtFunz(pageContext,"DEL","LISTADELSEL")}'>
							<input type="checkbox" name="keys" value="${chiaveRiga}" />
						</c:if>
					</gene:campoLista>
					<% // Campi veri e propri %>

					<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
					<gene:campoLista campo="ID" visibile="false"/>
					<gene:campoLista campo="CODEIN" visibile="false"/>
					<gene:campoLista campo="DENOM" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" />
					<gene:campoLista campo="CODICE_UFFICIO" headerClass="sortable" />
				</gene:formLista></td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	<% //Aggiunta dei menu sulla riga %>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.visualizza"
			title="Visualizza anagrafica Uffici" />
	<c:if test='${gene:checkProtFunz(pageContext, "MOD", "MOD")}'>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.modifica"
			title="Modifica anagrafica Uffici" />
	</c:if>
	<c:if test='${gene:checkProtFunz(pageContext, "DEL", "DEL")}'>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.elimina"
			title="Elimina anagrafica Uffici" />
	</c:if>
</gene:template>
