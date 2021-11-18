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

<c:set var="eliminabili" value="false" />
<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}' />
<c:set var="flusso" value="${flusso}" />

<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.InitListaFlussiGaraFunction" parametro="${key}"/>
<c:set var="idavgaraCigPresent" value='${requestScope.idavgaraCigPresent}'/>

<table class="dettaglio-tab-lista">
	<tr>
		<td>
			<gene:formLista entita="W9FLUSSI" pagesize="20" tableclass="datilista" sortColumn="2" gestisciProtezioni="true"
					where='W9FLUSSI.KEY01=#W9GARA.CODGARA# and W9FLUSSI.AREA=2 and W9FLUSSI.KEY03=988'>

				<gene:redefineInsert name="listaEliminaSelezione" />
				<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
				
				<gene:redefineInsert name="listaNuovo" >
					<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO") and (numeroFlussiGara eq 0 or (numeroFlussiGara > 0 and idavgaraCigPresent eq "true"))}'>
						<tr>
							<td class="vocemenulaterale">
								<a href="javascript:inviaAnagraficaGaraLotto();" title="Nuovo" tabindex="1501">
									${gene:resource("label.tags.template.lista.listaNuovo")}</a></td>
						</tr>
					</c:if>
				</gene:redefineInsert>
				<gene:redefineInsert name="pulsanteListaInserisci" >
					<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO") and (numeroFlussiGara eq 0 or (numeroFlussiGara > 0 and idavgaraCigPresent eq "true"))}'>
						<INPUT type="button" class="bottone-azione" title='Nuovo'	value="Nuovo" onclick="javascript:inviaAnagraficaGaraLotto();">
					</c:if>
				</gene:redefineInsert>
	
				<gene:campoLista title="&nbsp;" width="50">
					<c:if test="${currentRow >= 0}">
						<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
							<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}'>
								<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza invio" />
							</c:if>
							<c:if test='${datiRiga.W9FLUSSI_TINVIO2 ge 90}'>
								<gene:PopUpItemResource	resource="popupmenu.tags.lista.elimina" title="Elimina invio" />
							</c:if>
						</gene:PopUp>
						<c:set var="righe" value="${currentRow}" />
					</c:if>
				</gene:campoLista>
	
				<c:set var="key01" value='${gene:getValCampo(key,"CODGARA") }' />
				<c:set var="link"
					value="javascript:chiaveRiga='${chiaveRigaJava};W9FLUSSI.AREA=N:2;W9FLUSSI.KEY03=N:988;W9FLUSSI.KEY01=N:${key01}';listaVisualizza();" />
				<gene:campoLista campo="IDFLUSSO" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" title="Identificativo dell'invio" width="200" />
				<gene:campoLista campo="AREA" headerClass="sortable" visibile="false"/>
				<gene:campoLista campo="KEY03" title="Oggetto" definizione="N10;0;W3020;;" />
				<gene:campoLista campo="TINVIO2" headerClass="sortable" />
				<gene:campoLista campo="DATINV" headerClass="sortable" />
				<c:if test="${gene:checkProt( pageContext,'FUNZ.VIS.ALT.W9.W9FLUSSI_XML')}">
				<gene:campoLista title="" campo="XML" entita="W9FLUSSI" campoFittizio="true" definizione="T200;0;;;" width="30">
					<center><a href='javascript:apriAllegato("${gene:getValCampo(chiaveRigaJava,"IDFLUSSO")}");'>
						<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
					</a></center>
				</gene:campoLista>
				</c:if>
	
			</gene:formLista>
		</td>
	</tr>
	<c:if test="${userIsRup eq 'no' && sessionScope.profiloUtente.abilitazioneStd ne 'A' && permessoUser ne 3 && permessoUser ne 5}" >
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<gene:redefineInsert name="listaNuovo" />
	</c:if>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>
<gene:javaScript>
	
	function apriAllegato(valore) {
		var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		var par = new String('idflusso=' + valore + '&tab=flussi');
		document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
	}
	
	function inviaAnagraficaGaraLotto(){
		document.forms[0].key.value='W9FLUSSI.AREA=N:2;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY03=N:988';
		document.forms[0].metodo.value="nuovo";
		document.forms[0].activePage.value="0";
		document.forms[0].pathScheda.value="w9/w9flussi/w9flussi-scheda.jsp";
		document.forms[0].submit();
	}
	
</gene:javaScript>
