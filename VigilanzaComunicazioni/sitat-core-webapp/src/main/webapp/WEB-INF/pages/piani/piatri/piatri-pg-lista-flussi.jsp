
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
<c:set var="visualizzaLink"
	value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}' />
<c:set var="flusso" value="${flusso}" />
<gene:redefineInsert name="listaNuovo">
	<c:if test='${modificabile eq "si" or norma == 1}' >
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
			<td class="vocemenulaterale"><a href="javascript:popupPubblicazione();" title="Pubblica" tabindex="1501"> Pubblica </a></td>
		</c:if>
		<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
			<td class="vocemenulaterale"><a href="javascript:listaNuovo('1');" title="Inserisci" tabindex="1501"> Nuovo </a></td>
		</c:if>
	</c:if>
</gene:redefineInsert>
<gene:redefineInsert name="pulsanteListaInserisci">
	<c:if test='${modificabile eq "si" or norma == 1}' >
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
			<INPUT type="button" class="bottone-azione"	title='Pubblica' value="Pubblica" onclick="javascript:popupPubblicazione();">
		</c:if>
		<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
			<INPUT type="button" class="bottone-azione"	title='${gene:resource("label.tags.template.lista.listaPageNuovo")}' value="Nuovo" onclick="javascript:listaNuovo('1');">
		</c:if>
	</c:if>
</gene:redefineInsert>
<gene:redefineInsert name="listaEliminaSelezione" />
<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
<table class="dettaglio-tab-lista">   
	<tr>
		<td><gene:formLista entita="W9FLUSSI" pagesize="20"
			tableclass="datilista" sortColumn="2" gestisciProtezioni="true"
			where='W9FLUSSI.KEY01 = #PIATRI.CONTRI# and W9FLUSSI.AREA=4'>


			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
						onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if
							test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FLUSSI-scheda")}'>
							<gene:PopUpItemResource
								resource="popupmenu.tags.lista.visualizza"
								title="Visualizza invio" />
						</c:if>
					</gene:PopUp>
					<gene:redefineInsert name="listaNuovo" />
					<gene:redefineInsert name="addToAzioni">
						<c:if test='${modificabile eq "si" or norma == 1}' >
							<tr>
								<td class="vocemenulaterale">
									<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
										<a href="javascript:listaNuovo('2');" tabindex="1504" title="Rettifica">Rettifica</a>
									</c:if>
									<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
										<a href="javascript:popupPubblicazione();" tabindex="1504" title="Pubblica">Pubblica</a>
									</c:if>
								</td>
							</tr>
						</c:if>
					</gene:redefineInsert>
					<gene:redefineInsert name="pulsanteListaInserisci">
						<c:if test='${modificabile eq "si" or norma == 1}' >
							<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
								<INPUT type="button" class="bottone-azione"	title='${gene:resource("label.tags.template.lista.listaPageNuovo")}' value="Nuovo" onclick="javascript:listaNuovo('2');">
							</c:if>
							<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.PUBBLICA-SCP")}'>
								<INPUT type="button" class="bottone-azione"	title='Pubblica' value="Pubblica" onclick="javascript:popupPubblicazione();">
							</c:if>							
						</c:if>
					</gene:redefineInsert>
				</c:if>
			</gene:campoLista>
			
			<c:set var="link"
				value="javascript:chiaveRiga='${chiaveRigaJava};W9FLUSSI.AREA=N:4;W9FLUSSI.KEY01=N:${key1}';listaVisualizza();" />
			<gene:campoLista campo="IDFLUSSO" headerClass="sortable"
				href="${gene:if(visualizzaLink, link, '')}"  title="Identificativo dell'invio" />
			<gene:campoLista campo="TINVIO2" headerClass="sortable" />
			<gene:campoLista campo="DATINV" headerClass="sortable"  />
			<gene:campoLista title="" campo="XML" entita="W9FLUSSI" campoFittizio="true" definizione="T200;0;;;" width="30" >
				<center><a href='javascript:apriAllegato("${gene:getValCampo(chiaveRigaJava,"IDFLUSSO")}");'>
					<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
				</a></center>
			</gene:campoLista>
		</gene:formLista></td>    
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>
<gene:javaScript>
	function listaNuovo(tipoInvio){
		document.forms[0].key.value="W9FLUSSI.AREA=N:4;W9FLUSSI.KEY01=N:${key1};W9FLUSSI.KEY03=N:${flusso};W9FLUSSI.TINVIO2=N:"+tipoInvio;
		document.forms[0].metodo.value="nuovo";
		document.forms[0].activePage.value="0";
		document.forms[0].submit();
	}
	
	function popupPubblicazione() {
		var comando = "href=w9/commons/popup-pubblicazione-scp.jsp";
		comando = comando + "&contri=${gene:getValCampo(key,"CONTRI")}&flusso=${flusso}";
	   	openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}
	
	function apriAllegato(valore) {
		var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		var par = new String('idflusso=' + valore + '&tab=flussi');
		document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
	}
</gene:javaScript>


