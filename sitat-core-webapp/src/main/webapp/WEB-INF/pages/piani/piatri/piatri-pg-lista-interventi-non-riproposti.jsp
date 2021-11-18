
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

<c:set var="presenzaPiantiTriennaliPrecedenti" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaPresenzaPianiTriennaliPrecedentiFunction", pageContext, key)}' scope="request" />
<c:set var="updateLista" value='${param.updateLista}' />

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<gene:redefineInsert name="addToAzioni">
	<c:if test="${presenzaPiantiTriennaliPrecedenti eq 'TRUE' and updateLista ne 1 and modificabile eq 'si'}" >
		<tr>
			<td class="vocemenulaterale" >
				<a href="javascript:apriListaInterventiDaProgrammaPrecedente()" tabindex="1507"
					title="Riporta da programma precedente">
					Riporta da programma precedente
				</a>
			</td>
		</tr>
	</c:if>
	<c:if test='${modificabile eq "si"}' >
	<c:choose>
		<c:when test='${updateLista ne 1}' >
			<c:if test='${numeroInterventiNonRipropostiPresenti > 0}'>
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:listaApriInModifica()" title="Modifica" tabindex="1503"> Modifica </a>
					</td>
				</tr>	
			</c:if>
			
			<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO") && presenzaPiantiTriennaliPrecedenti ne "TRUE"}'>
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:aggiungi()" title="Inserisci" tabindex="1501"> Inserisci </a>
					</td>
				</tr>
			</c:if>
				
			<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTADELSEL") && numeroInterventiNonRipropostiPresenti > 0}'>
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:listaEliminaSelezione()" title='${gene:resource("label.tags.template.lista.listaEliminaSelezione")}' tabindex="1502"> ${gene:resource("label.tags.template.lista.listaEliminaSelezione")} </a>
					</td>
				</tr>	
			</c:if>
			
			<c:if test='${requestScope.moduloFabbisogniAttivo eq 1}'>
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:apriListaInterventiAnnullati()" title='Importa proposte annullate' tabindex="1509">
							Importa proposte annullate
						</a>
					</td>
				</tr>
			</c:if>
			
		</c:when>
		<c:otherwise>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:listaConferma()" title="Salva modifiche" tabindex="1504"> Salva modifiche </a>
				</td>
			</tr>	
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:listaAnnullaModifica()" title="Annulla" tabindex="1505"> Annulla </a>
				</td>
			</tr>	
		</c:otherwise>
	</c:choose>
	</c:if>
</gene:redefineInsert>

<gene:redefineInsert name="listaNuovo" />
<gene:redefineInsert name="listaEliminaSelezione" />
<gene:redefineInsert name="pulsanteListaInserisci" />

<%
  // Creo la lista per INTTRI
%>
<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td>
			<gene:formLista entita="INRTRI" pagesize="20" sortColumn="2;3"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINRTRI"
			tableclass="datilista" gestisciProtezioni="true"
			where='INRTRI.CONTRI = #PIATRI.CONTRI#'>
			
			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<c:if test='${updateLista ne 1 and (gene:checkProtFunz(pageContext, "DEL","DEL") and modificabile eq "si")}'>
						<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</gene:PopUp>
					</c:if>
				</c:if>
			</gene:campoLista>
			<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			
			<gene:campoLista campo="CONTRI" visibile="false" edit="${updateLista eq 1}" />
			<gene:campoLista campo="CONINT" visibile="false" edit="${updateLista eq 1}" />
			<gene:campoLista campo="CUIINT" visibile="true" />
			<gene:campoLista campo="CUPPRG" visibile="true"/>
			<gene:campoLista campo="DESINT" visibile="true"/>
			<gene:campoLista campo="TOTINT" visibile="true"/>
			<gene:campoLista campo="PRGINT" visibile="true"/>
			<gene:campoLista campo="MOTIVO" visibile="true" edit="${updateLista eq 1}" />
			
			</gene:formLista>
		</td>
	</tr>

	<tr>
		<td class="comandi-dettaglio" colSpan="2">
			<c:if test='${modificabile eq "si"}' >
			<c:choose>
				<c:when test='${updateLista eq 1}'>
					<INPUT type="button" class="bottone-azione" value="Salva" title="Salva modifiche" onclick="javascript:listaConferma()">
					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:listaAnnullaModifica()">
				</c:when>
				<c:otherwise>
					<c:if test='${numeroInterventiNonRipropostiPresenti > 0}'>
						<INPUT type="button"  class="bottone-azione" value='Modifica' title='Modifica' onclick="javascript:listaApriInModifica()">
					</c:if>
					<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO") && presenzaPiantiTriennaliPrecedenti eq "FALSE"}'>
						<INPUT type="button"  class="bottone-azione" value='Inserisci' title='Inserisci' onclick="javascript:aggiungi()">
					</c:if>
					<c:if test='${gene:checkProtFunz(pageContext,"DEL","LISTADELSEL") && numeroInterventiNonRipropostiPresenti > 0}'>
						<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.lista.listaEliminaSelezione")}' title='${gene:resource("label.tags.template.lista.listaEliminaSelezione")}' onclick="javascript:listaEliminaSelezione()">
					</c:if>
				</c:otherwise>
			</c:choose>
			</c:if>
		&nbsp;
		</td>
	</tr>
</table>

<gene:javaScript>
	function apriListaInterventiDaProgrammaPrecedente() {
		var action = "${pageContext.request.contextPath}/piani/GetListaInterventiDaProgrammaPrecedente.do";
		var parametri = new String('codiceProgramma=${fn:substringAfter(key, ":")}&tipo=INR');
		openPopUpActionCustom(action, parametri, 'listaInterventiDaProgrammaPrecedente',1000,800,"yes","yes");
	}
	
	function apriListaInterventiAnnullati() {
		var action = "${pageContext.request.contextPath}/piani/GetListaInterventiDaProgrammaPrecedente.do";
		var parametri = new String('codiceProgramma=${fn:substringAfter(key, ":")}&tipo=ANN');
		openPopUpActionCustom(action, parametri, 'listaInterventiDaProgrammaPrecedente',1000,800,"yes","yes");
	}
	
	function aggiungi(contri) {
		var contri = '${fn:substringAfter(key,":")}';
		var comando = "href=/piani/piatri/popup-inserisci-intervento-non-riproposto.jsp?key="+contri +"&modo=NUOVO&tiprog=${tiprog}";
		openPopUpCustom(comando, "validazione", 800, 650, "yes", "yes");
	}
</gene:javaScript>
