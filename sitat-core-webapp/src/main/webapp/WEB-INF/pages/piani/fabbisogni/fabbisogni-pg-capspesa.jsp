<%
  /*
   * Created on 11-gen-2018
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="RIS_CAPITOLO" pagesize="20" sortColumn="2;3;4"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreRISCAPITOLO"
			tableclass="datilista" gestisciProtezioni="true"
			where='RIS_CAPITOLO.CONTRI = #INTTRI.CONTRI# AND RIS_CAPITOLO.CONINT = #INTTRI.CONINT#'>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.RISCAPITOLO-scheda")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
						</c:if>
						<c:if test='${(gene:checkProtFunz(pageContext, "DEL","DEL")) && !(qe_sl eq "1") && (stato eq "1" || stato eq "2" || stato eq "11" || stato eq "12")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</c:if>
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
				<gene:campoLista campo="CONTRI" visibile="false"/> 
				<gene:campoLista campo="CONINT" visibile="false"/>
				<gene:campoLista campo="NUMCB" visibile="false"/>

			<gene:campoLista campo="NCAPBIL" width="550" ordinabile="true" href="${link}"/>
			<gene:campoLista campo="TO1CB" ordinabile="true" visibile="false" />
			<c:set var="to1cb" value="${datiRiga.RIS_CAPITOLO_TO1CB}" />
			<c:if test="${empty to1cb}">
				<c:set var="to1cb" value="0"/>
			</c:if>
			<gene:campoLista campo="TO2CB" ordinabile="true" visibile="false" />
			<c:set var="to2cb" value="${datiRiga.RIS_CAPITOLO_TO2CB}" />
			<c:if test="${empty to2cb}">
				<c:set var="to2cb" value="0"/>
			</c:if>
			<gene:campoLista campo="TO3CB" ordinabile="true" visibile="false" />
			<c:set var="to3cb" value="${datiRiga.RIS_CAPITOLO_TO3CB}" />
			<c:if test="${empty to3cb}">
				<c:set var="to3cb" value="0"/>
			</c:if>
			<gene:campoLista campo="TO9CB" ordinabile="true" visibile="false" />
			<c:set var="to9cb" value="${datiRiga.RIS_CAPITOLO_TO9CB}" />
			<c:if test="${empty to9cb}">
				<c:set var="to9cb" value="0"/>
			</c:if>
			<gene:campoLista title="Importo complessivo" campoFittizio="true" ordinabile="false" width="40" >
				<fmt:formatNumber type="number" pattern="###,###.00" value="${0+to1cb+to2cb+to3cb+to9cb}" currencySymbol="" currencyCode="EUR" />
				&nbsp;&euro;
			</gene:campoLista>
		</gene:formLista></td>
	</tr>
	
	<c:if test='${qe_sl eq "1" || !(stato eq "1" || stato eq "2" || stato eq "11" || stato eq "12")}' >
		<gene:redefineInsert name="pulsanteListaInserisci" />	
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="listaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
	</c:if>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>
	<gene:javaScript>
	
		function listaVisualizza(){
				document.forms[0].entita.value = "RIS_CAPITOLO";
				document.forms[0].key.value=chiaveRiga;
				document.forms[0].metodo.value="apri";
				document.forms[0].activePage.value="0";
				document.forms[0].submit();
		}

		function listaNuovo(){
				document.forms[0].entita.value = "RIS_CAPITOLO";
				document.forms[0].key.value="${key}";
				document.forms[0].metodo.value="nuovo";
				document.forms[0].activePage.value="0";
				document.forms[0].submit();
		}
	


	</gene:javaScript>
