<%
	/*
   * Created on: 15/07/2011
   *
   * Copyright (c) EldaSoft S.p.A.
   * Tutti i diritti sono riservati.
   *
   * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
   * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
   * aver prima formalizzato un accordo specifico con EldaSoft.
   */

%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<gene:template file="popup-message-template.jsp">
	
	<c:choose>
		<c:when test='${not empty param.contri}'>
			<c:set var="varContri" value="${param.contri}" />
			<c:set var="varTiprog" value="${param.tiprog}" />
			<c:set var="varPiatriID" value="${param.piatriID}" />
			<c:set var="varCtrl" value="A" />
			<c:if test="${varTiprog ne '2'}">
				<c:set var="varAccantonamento" value="${param.accantonamento}" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="varContri" value="${contri}" />
			<c:set var="varTiprog" value="${tiprog}" />
			<c:set var="varPiatriID" value="${piatriID}" />
			<c:set var="varCtrl" value="B" />
			<c:if test="${varTiprog ne '2'}">
				<c:set var="varAccantonamento" value="${accantonamento}" />
			</c:if>
		</c:otherwise>
	</c:choose>

	<gene:setString name="titoloMaschera" value="Stampa delle schede del programma"/>
	
	<gene:redefineInsert name="corpo">
		<c:set var="modo" value="NUOVO" scope="request" />
		<gene:formScheda entita="PIATRI" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreComposizioneProgrammi" >
			<input type="hidden" name="contri" value="${varContri}" />
			<input type="hidden" name="tiprog" value="${varTiprog}" />
			<input type="hidden" name="piatriID" value="${varPiatriID}" />
			<input type="hidden" name="varCtrl" value="${varCtrl}" />
			<c:if test="${varTiprog ne '2'}">
				<input type="hidden" name="accantonamento" value="${varAccantonamento}" />
			</c:if>
			<gene:campoScheda>
				<td colspan="2">
					<c:choose>
						<c:when test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
							<br>
							Allegato pdf creato con successo.
							<br><br>
						</c:when>
						<c:when test='${empty requestScope.RISULTATO or requestScope.RISULTATO eq "KO"}'>
							<br>
							Premere 'Conferma' per creare automaticamente l'allegato in formato pdf.
							<br>
							Premere 'Annulla' per interrompere l'operazione.
							<br><br>
						</c:when>
					</c:choose>
				</td>
			</gene:campoScheda>
			
			<c:if test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}'>
				<gene:redefineInsert name="buttons">
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</gene:redefineInsert>
			</c:if>
		</gene:formScheda>
  </gene:redefineInsert>
	<gene:javaScript>
	function annulla(){
		window.close();
	}
	
	document.forms[0].jspPathTo.value="piani/piatri/popupComponiPianoTriennale.jsp";

	function conferma(){
		schedaConferma();
	}
	
	<c:if test='${not empty requestScope.RISULTATO and requestScope.RISULTATO eq "OK"}' >
	window.opener.schedaAnnulla();
	</c:if>

	</gene:javaScript>
</gene:template>