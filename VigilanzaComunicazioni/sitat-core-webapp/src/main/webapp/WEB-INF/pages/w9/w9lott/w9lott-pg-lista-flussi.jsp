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
<c:set var="eliminabili" value="false" />
<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}' />
<c:set var="prot" value='${gene:checkProtFunz(pageContext, "INS", "INS")}' />

<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.InitListaFlussiLottoFunction" parametro="${key}" />
<gene:redefineInsert name="listaNuovo" />
<gene:redefineInsert name="pulsanteListaInserisci" />

<gene:redefineInsert name="listaEliminaSelezione" />
<gene:redefineInsert name="pulsanteListaEliminaSelezione" />

<c:choose>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
		<c:set var="inviiSimogAbilitati" value="true" />
		<c:set var="orderByCampo" value="4" />
	</c:when>
	<c:otherwise>
		<c:set var="inviiSimogAbilitati" value="false" />
		<c:set var="orderByCampo" value="2" />
	</c:otherwise>
</c:choose>

<table class="dettaglio-tab-lista">
	<tr>
		<td>
			<gene:formLista entita="W9FLUSSI" pagesize="20"	tableclass="datilista"
			  sortColumn="${orderByCampo}" gestisciProtezioni="true"
				where='W9FLUSSI.KEY01 = #W9LOTT.CODGARA# and W9FLUSSI.AREA=1 and W9FLUSSI.KEY02=#W9LOTT.CODLOTT#'>
	
				<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
					<c:if test="${currentRow >= 0}">
						<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"	onClick="chiaveRiga='${chiaveRigaJava}'">
							<c:if	test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}'>
								<gene:PopUpItemResource	resource="popupmenu.tags.lista.visualizza" title="Visualizza invio" />
							</c:if>
							<c:if test='${datiRiga.W9FLUSSI_TINVIO2 ge 90}'>
								<gene:PopUpItemResource	resource="popupmenu.tags.lista.elimina" title="Elimina invio" />
							</c:if>
						</gene:PopUp>
					</c:if>
				</gene:campoLista>
	
				<c:set var="link"	value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
				<c:if test='${inviiSimogAbilitati}' >
					<gene:campoLista entita="W9XML" campo="NUM_ERRORE" where="W9FLUSSI.IDFLUSSO=W9XML.IDFLUSSO" visibile="false"/>
					<gene:campoLista title="Stato" width="50">
						<center>
							<c:choose>
								<c:when test='${empty datiRiga.W9XML_NUM_ERRORE}'>
									&nbsp;
								</c:when>
								<c:when test='${datiRiga.W9XML_NUM_ERRORE eq 0}'>
									<img src="${pageContext.request.contextPath}/img/flag_verde.gif" height="16" width="16" title="Positivo">
								</c:when>
								<c:when test='${datiRiga.W9XML_NUM_ERRORE > 0}'>
									<img src="${pageContext.request.contextPath}/img/flag_rosso.gif" height="16" width="16" title="Negativo">
								</c:when>
							</c:choose>
						</center>
					</gene:campoLista>
				</c:if>
				<gene:campoLista campo="IDFLUSSO" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" width="200" title="ID" />
				<gene:campoLista campo="AREA" headerClass="sortable" width="150" title="Area" visibile="false" />
				<gene:campoLista campo="KEY01" headerClass="sortable" width="150" title="Fase / evento" visibile="false" />
				<c:choose>
					<c:when test='${datiRiga.W9FLUSSI_AREA eq 3}' >
						<c:set var="titoloKey02" value="Codice sistema avviso" />
					</c:when>
					<c:otherwise>
						<c:set var="titoloKey02" value="Fase / evento" />
					</c:otherwise>
				</c:choose>
				<gene:campoLista campo="KEY02" headerClass="sortable" width="150" title="${titoloKey02}" visibile="${false and (datiRiga.W9FLUSSI_AREA eq 1 or datiRiga.W9FLUSSI_AREA eq 3)}" />
				<gene:campoLista campo="KEY03" headerClass="sortable" width="150" title="Fase / evento" />
				<gene:campoLista campo="KEY04" headerClass="sortable" width="200" title="N." />
				<gene:campoLista campo="TINVIO2" headerClass="sortable" width="75" />
				<gene:campoLista campo="DATINV" headerClass="sortable" width="75" />
				
				<gene:campoLista campo="XML" visibile="false" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreLunghezzaCampo" />
				
			<c:if test="${gene:checkProt(pageContext,'FUNZ.VIS.ALT.W9.W9FLUSSI_XML')}">
				<gene:campoLista title="&nbsp;" campo="XMLq" entita="W9FLUSSI" campoFittizio="true" definizione="T200;0;;;" >
					<c:choose>
						<c:when test="${datiRiga.W9FLUSSI_XML eq 1}" >
							<a href='javascript:apriAllegato("${gene:getValCampo(chiaveRigaJava,"IDFLUSSO")}");'>
								<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
							</a>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
				</gene:campoLista>
			</c:if>
			</gene:formLista>
		</td>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>
<gene:javaScript>
	
	function apriAllegato(valore) {
		var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		var par = new String('idflusso=' + valore + '&tab=flussi');
		document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
	}
</gene:javaScript>