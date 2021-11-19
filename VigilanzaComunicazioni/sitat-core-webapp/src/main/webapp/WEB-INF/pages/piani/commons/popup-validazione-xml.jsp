
<%
	/*
	 * Created on 15-lug-2008
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



<div style="width:97%;">

<gene:template file="popup-template.jsp">
	
	<c:set var="contri" value="${param.contri}" />
	<c:set var="pubblica" value="${param.pubblica}" />
	<c:set var="verificaHiProg3" value="${param.verificaHiProg3}" />
	<c:set var="verificaFlussi" value="${param.verificaFlussi}" />
	<c:set var="verificaDatiProgramma" value="${param.verificaDatiProgramma}" />
	
	<c:if test="${!empty verificaHiProg3}">
		<gene:setString name="titoloMaschera" value='Verifica esportazione XML' />
	</c:if>
	<c:if test="${!empty verificaFlussi}">
		<gene:setString name="titoloMaschera" value='Controllo dati programma' />
	</c:if>
	<c:if test="${!empty verificaDatiProgramma}">
		<gene:setString name="titoloMaschera" value='Controllo dati inseriti' />
	</c:if>
	<gene:redefineInsert name="corpo">
		<table class="lista">
			<c:if test="${!empty verificaHiProg3}">
				<c:set var="stato" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GestioneValidazioneXmlFunction",pageContext,contri)}'/>
			</c:if>
			<c:if test="${empty verificaHiProg3}">
				<c:set var="stato" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GestioneValidazioneFunction",pageContext,contri)}'/>
			</c:if>				
			<gene:set name="titoloGenerico" value="${titolo}" />
			<gene:set name="listaGenericaControlli" value="${listaControlli}" />
			<gene:set name="numeroErrori" value="${numeroErrori}" />
			<gene:set name="numeroWarning" value="${numeroWarning}" />
			
			<c:choose>
		
				<c:when test="${!empty listaControlli}">
					<jsp:include page="../commons/popup-validazione-interno.jsp" />	
				</c:when>
				
				<c:otherwise>
					<tr>
						<td>
							<b>${titoloGenerico}</b>
							<br>&nbsp;<br>
							<b>Non &egrave stato rilevato alcun problema.</b>
							<br>&nbsp;<br>				
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<c:if test="${!empty verificaDatiProgramma}">
						<INPUT type="button" class="bottone-azione" value="Controlla nuovamente" title="Controlla nuovamente" onclick="javascript:controlla()">
					</c:if>
					<c:if test="${!empty verificaHiProg3}">
						<c:if test='${numeroErrori eq 0}'>
							<INPUT type="button" class="bottone-azione" value="Crea file XML" title="Crea file XML" onclick="javascript:CreateXML(${contri})">
						</c:if>
						<INPUT type="button" class="bottone-azione" value="Controlla nuovamente" title="Controlla nuovamente" onclick="javascript:controlla()">
					</c:if>
					
					<c:if test="${!empty verificaFlussi}">
						<c:if test='${pubblica eq "1" and gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
							<gene:sqlSelect nome="outSel" tipoOut="VectorString" >
							select email from usrsys where syscon = ${profiloUtente.id}
							</gene:sqlSelect>
							<c:set var="email" value="${outSel[0]}" />
						</c:if> 
						<gene:sqlSelect nome="outAdm" tipoOut="VectorString" >
						select idammin from uffint where codein = '${uffint}'
						</gene:sqlSelect>
						<c:set var="idammin" value="${outAdm[0]}" />
						<c:if test='${numeroErrori eq 0}'>
							<c:if test='${pubblica eq "1"}'>
								<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
									<INPUT type="button" class="bottone-azione" value="Pubblica" title="Pubblica" onclick="javascript:ConfermaInvio()">
								</c:if>
								<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
									<INPUT type="button" class="bottone-azione" value="Pubblica" title="Pubblica" onclick="javascript:ConfermaInvio()">
								</c:if>
							</c:if>
							<c:if test='${pubblica ne "1"}'>
								<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
									<INPUT type="button" class="bottone-azione" value="Prosegui" title="Prosegui" onclick="javascript:ShowIDAmministrazione()">
								</c:if>
								<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
									<INPUT type="button" class="bottone-azione" value="Conferma invio" title="Conferma invio" onclick="javascript:ConfermaInvio()">
								</c:if>
								
							</c:if>
						</c:if>
					</c:if>
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla()">&nbsp;
				</td>
			</tr>
			<c:if test="${!empty verificaFlussi}">
				<tr id="inputTextEmail" style="visibility:hidden;">	
					<td colspan="2">
						Indirizzo Email utilizzato per invio di eventuali comunicazioni
						<input id="email" size="50" type="text" name="email" value="${email}">

						<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:ConfermaInvio()">

					</td>
				</tr>
				<tr id="inputTextIDAmministrazione" style="visibility:hidden;">	
					<td colspan="2">
						ID amministrazione (da recuperare dal sito online)
						<c:if test="${empty idammin}">
							<input id="idAmministrazione" type="text" name="idAmministrazione">
						</c:if>
						<c:if test="${!empty idammin}">
							<input id="idAmministrazione" type="text" name="idAmministrazione" value="${idammin}">
						</c:if>
						<INPUT type="button" class="bottone-azione" value="Conferma invio" title="Conferma invio" onclick="javascript:ConfermaInvio()">
					</td>
				</tr>
			</c:if>

		</table>

	</gene:redefineInsert>
	
<gene:javaScript>

	window.opener.currentPopUp=null;

    window.onfocus=resettaCurrentPopup;

	function resettaCurrentPopup() {
		window.opener.currentPopUp=null;
	}
	
	function annulla(){
		window.close();
	}

	function controlla(){
		window.location.reload();
	}
	
	function CreateXML(idPiano) {
		var actionExportXML = "${pageContext.request.contextPath}/piani/CreateXMLProgrammiTriennali.do";
		var par = new String('contri=' + idPiano);
		document.location.href=actionExportXML+"?" + csrfToken + "&"+par;
	}
	
	function ShowIDAmministrazione() {
		document.getElementById('inputTextIDAmministrazione').style.visibility = "visible";
	}
	
	function ShowEmail() {
		document.getElementById('inputTextEmail').style.visibility = "visible";
	}
	
	function ConfermaInvio() {
		<c:if test='${pubblica eq "1"}'>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
				if (isFormatoEmailValido(document.getElementById('email').value)) {
					opener.setValue("EMAIL",document.getElementById('email').value);
					window.opener.schedaConferma();
					window.close();
				} else {
					alert("L'indirizzo email non e' sintatticamente valido.");
				}
			</c:if>
			<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
				window.opener.schedaConferma();
				window.close();
			</c:if>
		</c:if>
		<c:if test='${pubblica ne "1"}'>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
				if (isNumeric(document.getElementById('idAmministrazione').value)) {
					opener.setValue("IDAMMINISTRAZIONE",document.getElementById('idAmministrazione').value);
					window.opener.schedaConferma();
					window.close();
				} else {
					alert("L'ID amministrazione deve essere un valore numerico intero");
				}
			</c:if>
			<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
				window.opener.schedaConferma();
				window.close();
			</c:if>
		</c:if>
	}

	function isFormatoEmailValido(valoreMail){
	  var caratteriAmmessi = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
	  var esito = false
	  if(valoreMail.match(caratteriAmmessi)){
       esito = true;
    }
	  return esito;
	}

	function isNumeric(string) {  
  		var numericExpression = /^[0-9]+$/;  
    	if(string.match(numericExpression)) {  
      		return true;  
    	} else {  
      		return false;  
    	}  
	} 
</gene:javaScript>	
	
</gene:template>

</div>
