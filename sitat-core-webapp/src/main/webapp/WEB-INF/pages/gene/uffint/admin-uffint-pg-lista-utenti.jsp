
<%
  /*
   * Created on 06-nov-2009
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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<gene:redefineInsert name="listaEliminaSelezione"></gene:redefineInsert>
<gene:redefineInsert name="pulsanteListaEliminaSelezione"></gene:redefineInsert>
<gene:redefineInsert name="pulsanteListaInserisci">
	<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
		<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.lista.listaPageNuovo")}' title='${gene:resource("label.tags.template.lista.listaPageNuovo")}' onclick="javascript:listaNuovoWizard()">
	</c:if>
</gene:redefineInsert>
<gene:redefineInsert name="listaNuovo">
<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
<tr>
	<td class="vocemenulaterale">
		<c:if test='${isNavigazioneDisattiva ne "1"}'><a href="javascript:listaNuovoWizard();" title="Inserisci" tabindex="1501"></c:if>
		${gene:resource("label.tags.template.lista.listaPageNuovo")}
		<c:if test='${isNavigazioneDisattiva ne "1"}'></a></c:if>
	</td>
</tr>
</c:if>
</gene:redefineInsert>

<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="USR_EIN" pagesize="20"
			tableclass="datilista" sortColumn="2" gestisciProtezioni="true" 
			where='USR_EIN.CODEIN = #UFFINT.CODEIN#'
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreUSR_EIN">
			<gene:campoLista title="Opzioni" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
						onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if
							test='${gene:checkProt(pageContext, "MASC.VIS.GENE.USR_EIN-scheda")}'>
							<gene:PopUpItemResource
								resource="popupmenu.tags.lista.visualizza"
								title="Visualizza utente" />
						</c:if>
						<c:if
							test='${gene:checkProt(pageContext, "MASC.VIS.GENE.USR_EIN-scheda") && gene:checkProtFunz(pageContext, "MOD","MOD")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.modifica"
								title="Modifica utente" />
						</c:if>
						<c:if
							test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
								title="Elimina utente" />
						</c:if>
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<%
			  // Campi veri e propri
			%>
			<gene:campoLista campo="CODEIN" visibile="false"/>
			<gene:campoLista campo="SYSCON" visibile="false"/>
			<gene:campoLista entita="USRSYS" campo="SYSDISAB" where="USR_EIN.SYSCON = USRSYS.SYSCON" visibile="false"/>
			<gene:campoLista entita="USRSYS" campo="SYSUTE" where="USR_EIN.SYSCON = USRSYS.SYSCON" />
			<gene:campoLista entita="TECNI" campo="CODTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN" visibile="false"/>
			<gene:campoLista entita="TECNI" campo="CFTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN" />
			
			<input type="hidden" name="delTecnico" value=""/>
			 
		</gene:formLista></td>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiListaPage.jsp" /></tr>	
</table>

<gene:javaScript>
	function listaNuovoWizard() {
<c:choose>
	<c:when test='${fn:indexOf(key, "UFFINT.CODEIN") >= 0}'>
		<c:set scope="request" var="paramKey" value="${param.key}" />
	</c:when>
	<c:otherwise>
		<c:set scope="request" var="paramKey" value="${keyParent}" />
	</c:otherwise>
</c:choose>
		document.location.href="${contextPath}/ApriPagina.do?" + csrfToken + "&href=gene/usr_ein/usr_ein-wizard.jsp?keyParent=${paramKey}";
	}

	function listaElimina(){
		var href = "href=gene/uffint/conferma-eliminazione-utente.jsp&chiaveRiga="+chiaveRiga+"&numeroPopUp=1";
		win = openPopUpCustom(href, "confermaEliminaUtente", 500, 165, "no", "yes");

		if(win!=null)
			win.focus();
	}

	function confermaDelete(delTecnico){
		closePopUps();
		document.forms[0].key.value = chiaveRiga;
		document.forms[0].metodo.value = "elimina";
		document.forms[0].delTecnico.value = delTecnico;
		document.forms[0].submit();
	}

</gene:javaScript>
