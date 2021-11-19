<%
	/*
	 * Created on 15-mar-2012
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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<c:if test="${! empty sessionScope.uffint}">
	<c:set var="filtroCODEIN" value="CODEIN = '${sessionScope.uffint}'"/>
</c:if>

<gene:template file="lista-template.jsp" gestisciProtezioni="true"
	idMaschera="W9GARA_ENTINAZ-lista" schema="W9">
	<gene:setString name="titoloMaschera" value="Lista gare" />
	<gene:setString name="entita" value="W9GARA_ENTINAZ" />
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="W9GARA_ENTINAZ" pagesize="20" where="${filtroCODEIN}"
						tableclass="datilista" gestisciProtezioni="true" sortColumn="-3"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARA_ENTINAZ">
					
						<gene:campoLista title="Opzioni <br><center>${titoloMenu}</center>" width="50">
							<c:if test="${currentRow >= 0}">
								<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
									<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza" />
									<c:if test='${gene:checkProtFunz(pageContext, "MOD","MOD")}'>
										<gene:PopUpItemResource resource="popupmenu.tags.lista.modifica" title="Modifica" />
									</c:if>
									
									<c:set var="cancella"
									value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,chiaveRiga,"W9GARA_ENTINAZ")}' />
									
									<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && (cancella eq "si")}'>
										<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />
									</c:if>
								</gene:PopUp>
								<c:if test='${gene:checkProtFunz(pageContext,"DEL","LISTADELSEL")}'>
									<input type="checkbox" name="keys" value="${chiaveRiga}" />
								</c:if>
							</c:if>
						</gene:campoLista>
						<gene:campoLista campo="CODGARA" visibile="false"/>
					
						<gene:campoLista campo="OGGETTO" />
						<gene:campoLista campo="TIPO_APP" />
						<gene:campoLista campo="IMPORTO_GARA" />

					</gene:formLista>
				</td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	
</gene:template>