<%
	/*
	 * Created on 29-ott-2009
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

<gene:template file="lista-template.jsp" gestisciProtezioni="true"
	schema="W9" idMaschera="W9FASI-NUOVO-RETTIFICA-lista">
	<gene:setString name="titoloMaschera"	value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9LOTT")} - Seleziona l&apos;Invio ' />
	<c:set var="key" value='${param.key}' scope="request" />
	<c:set var="whereLista"
		value="W9FASI.CODGARA =#W9LOTT.CODGARA# and W9FASI.CODLOTT =#W9LOTT.CODLOTT#"></c:set>

	<gene:redefineInsert name="corpo">
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="listaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<table class="lista">
			<tr>
				<td><gene:formLista entita="W9FASI" pagesize="20" pathScheda="w9/w9flussi/inserimento-rettifica-flussi.jsp"
					tableclass="datilista" sortColumn="2" gestisciProtezioni="true" where='${whereLista}'>

					<c:set var="key01" value='${gene:getValCampo(key,"CODGARA") }' />
					<c:set var="key02" value='${gene:getValCampo(key,"CODLOTT") }' />
					<gene:campoLista title="" width="20">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
								onClick="chiaveRiga='${chiaveRigaJava}'">
								<c:set var="label" value=""></c:set>
								<c:set var="tipoInvio" value=""></c:set>
								<c:choose>
									<c:when test='${datiRiga.W9FLUSSI_IDFLUSSO ne ""}'>
										<c:set var="label" value="Rettifica"></c:set>
										<c:set var="tipoInvio" value="2"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="label" value="Crea invio"></c:set>
										<c:set var="tipoInvio" value="1"></c:set>
									</c:otherwise>
								</c:choose>
								<c:set var="chiaveFlussiInserimento" value='W9FLUSSI.KEY03=N:${gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE")};W9FLUSSI.KEY04=N:${gene:getValCampo(chiaveRigaJava,"NUM")}'></c:set>

								<c:set var="ctrlProp" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.VerificaFlussiPropedeuticiFunction", pageContext, chiaveRigaJava)}' />
								<c:choose>
									<c:when test='${ctrlProp eq "false" and tipoInvio eq "1"}'>
										<gene:PopUpItem title="Invio non possibile" href="" />
									</c:when>
									<c:otherwise>
										<gene:PopUpItem title="${label}" href="paginaSelezioneNuovoFlusso('${chiaveFlussiInserimento}', '${tipoInvio}')" />
									</c:otherwise>
								</c:choose>

							</gene:PopUp>
						</c:if>
					</gene:campoLista>
					<c:set var="link"
						value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetRedirectFunction",pageContext,chiaveRigaJava, 1)}' />

					<gene:campoLista campo="FASE_ESECUZIONE" headerClass="sortable"
						href="javascript:paginaVisualizza('${link}','${chiaveRigaJava}');"
						width="500" />
					<gene:campoLista campo="NUM" headerClass="sortable" width="100" />
					<gene:campoLista campo="IDFLUSSO" visibile="false"
						entita="W9FLUSSI" where="W9FLUSSI.KEY01=W9FASI.CODGARA AND W9FLUSSI.KEY02=W9FASI.CODLOTT AND W9FLUSSI.KEY03=W9FASI.FASE_ESECUZIONE AND W9FLUSSI.KEY04=W9FASI.NUM"
						headerClass="sortable" />

					<input type="hidden" name="tipoFlusso" id="tipoFlusso"
						value="${param.tipoFlusso}" />

				</gene:formLista></td>
			</tr>
			<tr>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
			</tr>
		</table>
		<gene:javaScript>
			function paginaSelezioneNuovoFlusso(chiave, tipoInvio){
				document.forms[0].key.value='W9FLUSSI.AREA=N:1;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY02=N:${key02};'+chiave+';W9FLUSSI.TINVIO2=N:'+tipoInvio;
				document.forms[0].metodo.value="nuovo";
				document.forms[0].activePage.value="0";
				document.forms[0].pathScheda.value="w9/w9flussi/w9flussi-scheda.jsp";
				document.forms[0].submit();
			}
			
			function paginaVisualizza(pag,keyup) {
				var action="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/" + pag; // + "?chiave=" + keyup;
				document.forms[0].key.value=pag.substring(pag.indexOf('?chiave=') + 8);
				document.forms[0].action=action;
				document.forms[0].activePage.value="0";
				document.forms[0].submit();
			}
		</gene:javaScript>
	</gene:redefineInsert>
</gene:template>