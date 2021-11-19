<%
	/*
	 * Created on 11-ott-2011
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

<div style="width:97%;">

	<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="FABBISOGNI-schedaCambiaUtenteSA">
	
	<c:set var="stazioneAppaltante" value='${param.stazioneAppaltante}' scope="request" />
	<c:set var="fabbisogni" value='${param.fabbisogni}' scope="request" />
	<c:set var="conint" value='${gene:getValCampo(key,"CONINT")}' scope="request" />
	<c:set var="onchange" value='${param.onchange}' scope="request" />
	<c:set var="temp" value='${gene:callFunction3("it.eldasoft.sil.pt.tags.funzioni.GetUtentiSAFunction",pageContext,stazioneAppaltante, fabbisogni)}'/>
	<c:set var="entita" value="FABBISOGNI" />
		<gene:setString name="titoloMaschera" value='Cambia utente/ufficio intestatario' />
		
		<gene:redefineInsert name="corpo">
		
			<gene:formScheda entita="FABBISOGNI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreCambiaUtenteSA">
				<input type="hidden" name="listafabbisogni" value="${fabbisogni}" />
				<input type="hidden" name="utente" value="" />
				<c:set var="conint" value='${gene:getValCampo(key,"CONINT")}' scope="request" />
				<gene:campoScheda campo="CONINT" visibile="false"/>
				<gene:archivio titolo="STAZIONE APPALTANTE" 
				    lista='gene/uffint/uffint-lista-popup.jsp'
					scheda=''
					schedaPopUp=''
					campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="FABBISOGNI_CODEIN" where=""	formName="formStazApp">
					<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${stazioneAppaltante}" modificabile="true"/>
					<gene:campoScheda campo="NOMEIN" entita="UFFINT"  defaultValue="${nomein}" where="UFFINT.CODEIN = FABBISOGNI.CODEIN" modificabile="true"  title="Ufficio intestatario" />
				</gene:archivio>
		
				<gene:campoScheda>
					<td colspan="2"><b>Utente</b></td>
				</gene:campoScheda>
				<c:forEach items="${utenti}" step="1"  var="utente">
					<fmt:parseNumber var="syscon" type="number" value="${utente[0]}" />
					<c:if test='${syscon eq sysconFabbisogno}'>
						<gene:campoScheda><td colspan="2"><input type="radio" checked="checked" name="groupUtenti" value="${utente[0]}" > ${utente[1]} ${utente[2]}</td></gene:campoScheda>
					</c:if>
					<c:if test='${syscon ne sysconFabbisogno}'>
						<gene:campoScheda><td colspan="2"><input type="radio" name="groupUtenti" value="${utente[0]}" > ${utente[1]} ${utente[2]}</td></gene:campoScheda>
					</c:if>
				</c:forEach>
				<gene:campoScheda>
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:Conferma();">
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</gene:campoScheda>
				
				<gene:fnJavaScriptScheda funzione="gestioneSA('#FABBISOGNI_CODEIN#')" elencocampi="FABBISOGNI_CODEIN" esegui="false" />
			
			</gene:formScheda>
			
			
		</gene:redefineInsert>
		
		<gene:javaScript>
	
			document.forms[0].jspPathTo.value="piani/fabbisogni/popup-cambia-utenteSA-result.jsp";

			$("textarea").attr('rows','1'); 
			$("textarea").attr('cols','30');
			
			function annulla(){
				window.close();
			}
			
			function Conferma(){
				schedaConferma();
			}
		
			function gestioneSA(value) {
				location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/fabbisogni/popup-cambia-utenteSA.jsp&modo=NUOVO&stazioneAppaltante="+value+"&fabbisogni=${fabbisogni}&onchange=true";
			}
			
		</gene:javaScript>	
		
	</gene:template>

</div>
