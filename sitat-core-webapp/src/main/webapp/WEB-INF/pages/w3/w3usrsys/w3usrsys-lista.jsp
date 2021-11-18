
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

<c:set var="filtroUffint" value=""/> 
<c:if test="${!empty sessionScope.uffint}">
	<c:set var="filtroUffint" value=" and TECNI.CGENTEI = '${sessionScope.uffint}'"/>
</c:if>

<gene:template file="lista-template.jsp" gestisciProtezioni="true"
	idMaschera="W3USRSYS-lista" schema="W3">
	<gene:setString name="titoloMaschera" value="RUP e Centri di Costo da SIMOG" />
	<gene:setString name="entita" value="W3USRSYS" />
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
			<tr>
				<td><gene:formLista entita="W3USRSYS"  where="W3USRSYS.SYSCON = ${sessionScope.profiloUtente.id} AND W3USRSYS.RUP_CODTEC in (SELECT CODTEC FROM TECNI WHERE TECNI.CODTEC IS NOT NULL ${filtroUffint})" pagesize="20" sortColumn="2"
					tableclass="datilista" gestisciProtezioni="true" gestore="it.eldasoft.sil.w3.tags.gestori.submit.GestoreW3USRSYS">
					<gene:campoLista title="Opzioni <br><center>${titoloMenu}</center>"
						width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
								onClick="chiaveRiga='${chiaveRigaJava}'">
								<gene:PopUpItemResource
									resource="popupmenu.tags.lista.visualizza"
									title="Visualizza rup" />
									
								<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
										<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
											title="Elimina rup" />
								</c:if>
							</gene:PopUp>
							
							<c:if test='${gene:checkProtFunz(pageContext,"DEL","LISTADELSEL")}'>
								<input type="checkbox" name="keys" value="${chiaveRiga}" />
							</c:if>
							
						</c:if>
					</gene:campoLista>
					
					<gene:campoLista campo="SYSCON" visibile="false"/>
					<gene:campoLista entita="TECNI" campo="CFTEC" where="W3USRSYS.RUP_CODTEC = TECNI.CODTEC" href="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
					<gene:campoLista entita="TECNI" campo="NOMTEC" where="W3USRSYS.RUP_CODTEC = TECNI.CODTEC"/>
				</gene:formLista></td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
</gene:template>