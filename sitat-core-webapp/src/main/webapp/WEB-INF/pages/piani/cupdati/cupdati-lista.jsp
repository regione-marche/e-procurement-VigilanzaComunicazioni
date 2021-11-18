
<%
	/*
	 * Created on 20-Ott-2008
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

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPDATIFunction" />

<c:choose>
	<c:when test="${!empty sessionScope.uffint}">
 		<c:set var="filtro" value="CODEIN = '${sessionScope.uffint}' AND SYSCON = ${profiloUtente.id}"/>
	</c:when>
	<c:otherwise>
		<c:set var="filtro" value="SYSCON = ${profiloUtente.id}"/>
	</c:otherwise>
</c:choose>

<gene:template file="lista-template.jsp" gestisciProtezioni="true"
	idMaschera="CUPDATI-lista" schema="PIANI">
	<gene:setString name="titoloMaschera" value="Lista dei CUP" />
	<gene:setString name="entita" value="CUPDATI" />
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
			<tr>
				<td><gene:formLista entita="CUPDATI" pagesize="20" where="${filtro}"
					tableclass="datilista" gestisciProtezioni="true" sortColumn="-3"
					gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreCUPDATI">
					
					<gene:campoLista title="Opzioni <br><center>${titoloMenu}</center>"
						width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
								onClick="chiaveRiga='${chiaveRigaJava}'">
								<gene:PopUpItemResource
									resource="popupmenu.tags.lista.visualizza"
									title="Visualizza" />
								<c:if test='${gene:checkProtFunz(pageContext, "MOD","MOD")}'>
									<gene:PopUpItemResource
										resource="popupmenu.tags.lista.modifica"
										title="Modifica" />
								</c:if>
								<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
									<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
										title="Elimina" />
								</c:if>
							</gene:PopUp>
							<c:if test='${gene:checkProtFunz(pageContext,"DEL","LISTADELSEL")}'>
								<input type="checkbox" name="keys" value="${chiaveRiga}" />
							</c:if>
						</c:if>
					</gene:campoLista>
					<gene:campoLista campo="ID" visibile="false"/>
					<gene:campoLista title="Anno della decisione" campo="ANNO_DECISIONE" width="50"
						href="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
					<gene:campoLista campo="CUP" width="100"
						href="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
					<gene:campoLista campo="DATA_DEFINITIVO" 
						href="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />	
					<gene:campoLista campo="NATURA" >
						<gene:addValue value="" descr="" />
						<c:if test='${!empty listaValoriNatura}'>
							<c:forEach items="${listaValoriNatura}" var="valoriNatura">
								<gene:addValue value="${valoriNatura[0]}"
									descr="${valoriNatura[2]}" />
							</c:forEach>
						</c:if>
					</gene:campoLista>
					<gene:campoLista campo="TIPOLOGIA" >
						<gene:addValue value="" descr="" />
						<c:if test='${!empty datiRiga.CUPDATI_NATURA}'> 
							<c:if test='${!empty listaValoriTipologia}'>
								<c:forEach items="${listaValoriTipologia}" var="valoriTipologia">
									<c:if test="${valoriTipologia[0] eq datiRiga.CUPDATI_NATURA}">
										<gene:addValue value="${valoriTipologia[1]}"
											descr="${valoriTipologia[2]}" />					
									</c:if>
								</c:forEach>
							</c:if>
						</c:if>
					</gene:campoLista>
					<gene:campoLista title="Descrizione" campo="DESCRIZIONE_INTERVENTO" />
					<gene:campoLista title="Costo" campo="COSTO" />
					<gene:campoLista campo="FINANZIAMENTO" />
				</gene:formLista></td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	
</gene:template>