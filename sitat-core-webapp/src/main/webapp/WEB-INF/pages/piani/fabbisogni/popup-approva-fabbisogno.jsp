
<%
	/*
	 * Created on 10-feb-2015
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.InizializzaUpdateListaFunction" parametro="${chiave}" />


<c:set var="idUtente" value="${sessionScope.profiloUtente.id}" />

<c:if test='${updateLista eq 1}' >
	<c:set var="isNavigazioneDisabilitata" value="1" scope="request" />
</c:if>

<gene:template file="popup-template.jsp" gestisciProtezioni="false" >
	<gene:setString name="titoloMaschera" value="Approvazione fabbisogno" />
	<gene:setString name="entita" value="PTAPPROVAZIONI" />
	
		
	<gene:redefineInsert name="corpo">
					
		<table class="lista">
			<tr>
				<td><gene:formLista entita="PTAPPROVAZIONI" pagesize="20" tableclass="datilista" gestisciProtezioni="false" sortColumn="1" where ="CONINT = ${param.chiave}" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreApprovazioni" >
					<gene:redefineInsert name="listaNuovo" />
					<gene:redefineInsert name="listaEliminaSelezione" />
					<gene:redefineInsert name="documentiAzioni" />					
					
										
					<gene:redefineInsert name="addToAzioni">
						<c:choose>
						<c:when test='${updateLista eq 1}'>
							<tr>
								<td class="vocemenulaterale">
									<a href="javascript:listaConferma();" title="Salva modifiche" tabindex="1500">
										${gene:resource("label.tags.template.dettaglio.schedaConferma")}
									</a>
								</td>
							</tr>
							<tr>
								<td class="vocemenulaterale">
									<a href="javascript:annulla();" title="Annulla modifiche" tabindex="1501">
										${gene:resource("label.tags.template.dettaglio.schedaAnnulla")}
									</a>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${datiRiga.rowCount > 0}">
							<tr>
								<td class="vocemenulaterale">
									<a href="javascript:listaApriInModifica();" title='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' tabindex="1505">
										${gene:resource("label.tags.template.dettaglio.schedaModifica")}
									</a>
								</td>
							</tr>
							</c:if>
						</c:otherwise>
						</c:choose>
					</gene:redefineInsert>
					
			<gene:campoLista campo="CONINT" visibile="false"  edit="${(updateLista eq 1)}" />
			<gene:campoLista campo="NUMAPPR" visibile="false"  edit="${(updateLista eq 1)}" />
			<gene:campoLista campo="DATAAPPR" visibile="true"  />
			<gene:campoLista campo="FASEAPPR" visibile="true"  />
			<gene:campoLista campo="ESITOAPPR" visibile="true" edit="${(updateLista eq 1)}" />
			<gene:campoLista campo="NOTEAPPR" visibile="true" edit="${(updateLista eq 1)}" />
			
			
					<input type="hidden" name="numeroApprovazioni" id="numeroApprovazioni" value="" />
					<input type="hidden" name="updateLista" id="updateLista" value="${updateLista}" />
					<input type="hidden" name="chiave" id="chiave" value="${param.chiave}" />
				</gene:formLista></td>
			</tr>
			
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					
						<c:choose>
						 <c:when test='${updateLista eq 1 }'>
							<INPUT type="button" class="bottone-azione" value="Salva" title="Salva modifiche" onclick="javascript:listaConferma();">
							<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla();">
						 </c:when>
						 <c:otherwise>
							<c:if test="${datiRiga.rowCount > 0}">
								<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' title='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' onclick="javascript:listaApriInModifica();">&nbsp;&nbsp;&nbsp;
								<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:chiudi()">&nbsp;
							</c:if>
						 </c:otherwise>
						</c:choose>
				</td>
			</tr>
					
					
			
		</table>
	</gene:redefineInsert>
	<gene:javaScript>
	
	document.getElementById("numeroApprovazioni").value = ${currentRow}+1;
	
	
	
	function annulla(){
			document.forms[0].updateLista.value = "0";
			listaAnnullaModifica();
	}

	function chiudi(){
			window.close();
	}
	

	
		
</gene:javaScript>
</gene:template>