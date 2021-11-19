
<%
	/*
	 * Created on: 01/10/2012
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

<c:set var="presenzaPiantiOpereIncompiutePrecedenti" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaPresenzaOpereIncompiutePrecedentiFunction", pageContext, key)}' scope="request" />
<c:set var="updateLista" value='${param.updateLista}' />
<gene:redefineInsert name="addToAzioni">
	<c:if test="${presenzaPiantiOpereIncompiutePrecedenti eq 'TRUE' and updateLista ne 1 and modificabile eq 'si' }" >
		<tr>
			<td class="vocemenulaterale" ><a 
				 href="javascript:apriListaOpereDaProgrammaPrecedente();" tabindex="1504"
				 title="Riporta opere da programma precedente">Riporta da programma precedente</a></td>
		</tr>
	</c:if>
</gene:redefineInsert>

<gene:redefineInsert name="listaNuovo">
	<c:if test='${modificabile eq "si"}' >
	<td class="vocemenulaterale"><a href="javascript:listaNuovo();"
		title="Inserisci" tabindex="1501"> Nuovo </a>
	</td>
	</c:if>
</gene:redefineInsert>

<c:if test='${modificabile eq "no"}' >
	<gene:redefineInsert name="pulsanteListaInserisci"/>
	<gene:redefineInsert name="listaEliminaSelezione" />
	<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
</c:if>
	
<%
	// Creo la lista per INTTRI
%>
<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="OITRI" pagesize="20" sortColumn="2;3"
				gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreOITRI"
				tableclass="datilista" gestisciProtezioni="true"
				where='OITRI.CONTRI = #PIATRI.CONTRI# '>

				<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				  <c:if test="${currentRow >= 0}">
						<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
									onClick="chiaveRiga='${chiaveRigaJava}'">
						  <c:if test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.OITRI-scheda")}'>
								<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
						  </c:if>
						  <c:if test='${gene:checkProtFunz(pageContext, "MOD","MOD") && modificabile eq "si"}'>
								<gene:PopUpItemResource resource="popupmenu.tags.lista.modifica" title="Modifica dettaglio" />
						  </c:if>
							<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && modificabile eq "si"}'>
								<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />
										<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
							</c:if>
						</gene:PopUp>
					</c:if>
				</gene:campoLista>


				<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />

				<gene:campoLista campo="CONTRI" visibile="false" />
				<gene:campoLista campo="NUMOI" visibile="false" />
				<gene:campoLista campo="CUP" href="${link}"/>
				<gene:campoLista campo="DESCRIZIONE" />
				<gene:campoLista campo="ANNOULTQE" />
				<gene:campoLista campo="AVANZAMENTO" />
			</gene:formLista>
		</td>
	</tr>
<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>

<gene:redefineInsert name="head">
	<!--   <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.7.2.min.js"></script> -->
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-ui-1.8.21.custom.min.js"></script> -->
</gene:redefineInsert>

<gene:javaScript>

	function apriListaOpereDaProgrammaPrecedente() {
		var action = "${pageContext.request.contextPath}/piani/GetListaOpereDaProgrammaPrecedente.do";
		var parametri = new String('codiceProgramma=${fn:substringAfter(key, ":")}');
		openPopUpActionCustom(action, parametri, 'listaOpereDaProgrammaPrecedente',1000,800,"yes","yes");
	}
	
</gene:javaScript>

