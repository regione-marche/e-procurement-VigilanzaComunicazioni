
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>
<c:set var="stato" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetStatoProgrammaFunction",pageContext,key)}' />
<table class="dettaglio-tab-lista">   
	<tr>
		<td><gene:formLista entita="PTFLUSSI" pagesize="20"
			tableclass="datilista" sortColumn="2" gestisciProtezioni="true"
			where='PTFLUSSI.KEY01 = #PIATRI.CONTRI#'>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
						onClick="chiaveRiga='${chiaveRigaJava}'">
							<gene:PopUpItemResource
								resource="popupmenu.tags.lista.visualizza"
								title="Visualizza invio" />
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			
			<c:set var="id_invio" value="ID pubblicazione"/>
			<c:set var="tipo_invio" value="Tipo di pubblicazione"/>
			<c:set var="data_invio" value="Data pubblicazione"/>
			<c:if test='${pubblica ne "1" || gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.SITAT")}'>
				<c:set var="id_invio" value="ID invio"/>
				<c:set var="tipo_invio" value="Tipo di invio"/>
				<c:set var="data_invio" value="Data invio"/>
			</c:if>
			
			<gene:campoLista campo="IDFLUSSO" title="${id_invio}" headerClass="sortable" href="${link}"/>
			<gene:campoLista campo="TIPINV" title="${tipo_invio}" headerClass="sortable" />
			<gene:campoLista campo="DATINV" title="${data_invio}" headerClass="sortable"  />
			<gene:campoLista campo="XML" visibile="false" />
			<gene:campoLista title="XML" campo="XML" entita="PTFLUSSI" campoFittizio="true" definizione="T200;0;;;" width="30" >
				<c:if test="${empty datiRiga.PTFLUSSI_XML}">
					<center><img src="${pageContext.request.contextPath}/img/exportXMLGrigio.gif" title="File non presente"/>
					</center>
				</c:if>
				<c:if test="${!empty datiRiga.PTFLUSSI_XML}">
					<center><a href='javascript:apriAllegato("${gene:getValCampo(chiaveRigaJava,"IDFLUSSO")}", "xml");'>
						<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
					</a></center>
				</c:if>
			</gene:campoLista>
			<gene:campoLista title="PDF" campo="FILE_ALLEGATO" entita="PTFLUSSI" campoFittizio="true" definizione="T200;0;;;" width="30" >
				<c:set var="esistePdf" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.EsistePdfFlussoFunction", pageContext, gene:getValCampo(chiaveRigaJava,"IDFLUSSO"))}' />
				<c:if test="${esistePdf eq 'false'}">
					<center><img src="${pageContext.request.contextPath}/img/pdf_not_found.gif" title="File non presente"/>
					</center>
				</c:if>
				<c:if test="${esistePdf eq 'true'}">
					<center><a href='javascript:apriAllegato("${gene:getValCampo(chiaveRigaJava,"IDFLUSSO")}", "pdf");'>
						<img src="${pageContext.request.contextPath}/img/pdf.gif" />
					</a></center>
				</c:if>
			</gene:campoLista>
		</gene:formLista></td>    
	</tr>
	<tr>
		<c:if test="${stato == '2' || stato == '5' || modificabile eq 'no'}">
			<gene:redefineInsert name="listaNuovo" />
			<gene:redefineInsert name="pulsanteListaInserisci" />
			
		</c:if>
		
		<gene:redefineInsert name="listaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
	</tr>
</table>

<gene:javaScript>
	function apriAllegato(valore, tipoFile) {
		var actionAllegato = "${pageContext.request.contextPath}/piani/VisualizzaAllegato.do";
		var par = new String('idflusso=' + valore + '&tab=flussi&tipo=' + tipoFile);
		document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
	}
	
	
</gene:javaScript>


