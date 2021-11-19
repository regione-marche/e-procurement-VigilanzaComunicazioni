<%
/*
 * Created on: 10-nov-2008
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 *
 * Popup per campi ulteriori relativi alla ditta presenta nella lista delle
 * fasi di ricezione in analisi
 */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="listaOpzioniDisponibili" value="${fn:join(opzDisponibili,'#')}#"/>

<!--div style="width:97%;"-->

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" 
	idMaschera="W9IMPRESE-scheda">

	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='${gene:if(modo eq "NUOVO","Aggiungi impresa", gene:if(modo eq "MODIFICA", "Modifica impresa", "Dettaglio impresa"))}' />
		<gene:formScheda entita="W9IMPRESE" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9IMPRESE" >

			<gene:campoScheda campo="CODGARA" visibile="false" />
			<gene:campoScheda campo="CODLOTT" visibile="false" />
			<gene:campoScheda campo="NUM_IMPR" visibile="false" />
			<gene:campoScheda campo="NUM_RAGG" visibile="false" />
			
			<gene:campoScheda campo="TIPOAGG" defaultValue="${param.tipoImpresa}" modificabile="false" />

	<c:choose>
		<c:when test='${empty RISULTATO and modo ne "NUOVO" and not empty param.numImpr and empty param.numRagg}' >
			<% // Visualizzazione e modifica impresa singola %>

			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetImpreseInvitateFunction" parametro="${param.codGara};${param.codLott};${param.numImpr};${param.numRagg}" />

			<gene:campoScheda campo="PARTECIP" obbligatorio="true"/>
			<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
				scheda="gene/impr/impr-scheda.jsp"
				schedaPopUp="gene/impr/impr-scheda-popup.jsp"
				campi="IMPR.NOMEST;IMPR.CODIMP"
				chiave="W9IMPRESE_CODIMP"
				formName="formImprese" inseribile="true" >
				<gene:campoScheda title="Denominazione Impresa" entita="IMPR" campo="NOMEST" where="IMPR.CODIMP=W9IMPRESE.CODIMP" definizione="T2000;0;;;NOMIMP" />
				<gene:campoScheda title="Codice impresa" campo="CODIMP" entita="W9IMPRESE" visibile="false" />
			</gene:archivio>

		</c:when>
		<c:when test='${empty RISULTATO and modo eq "NUOVO" and not empty param.tipoImpresa and param.tipoImpresa eq 3}'>
			<% // Inserimento impresa singola %>
			
			<gene:campoScheda campo="PARTECIP" obbligatorio="true"/>
			<gene:archivio titolo="IMPRESE" lista="gene/impr/impr-lista-popup.jsp"
				scheda="gene/impr/impr-scheda.jsp"
				schedaPopUp="gene/impr/impr-scheda-popup.jsp"
				campi="IMPR.NOMEST;IMPR.CODIMP"
				chiave="W9IMPRESE_CODIMP"
				formName="formImprese" inseribile="true">
				<gene:campoScheda title="Denominazione Impresa" entita="IMPR" campo="NOMEST" definizione="T2000;0;;;NOMIMP" />
				<gene:campoScheda title="Codice impresa" campo="CODIMP" entita="W9IMPRESE" visibile="false" />
			</gene:archivio>

		</c:when>
		<c:when test='${empty RISULTATO and modo ne "NUOVO"}'>
			<% // Visualizzazione e modifica di gruppo di imprese %>
			
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetImpreseInvitateFunction" parametro="${param.codGara};${param.codLott};${param.numImpr};${param.numRagg}" />
			
			<gene:campoScheda campo="PARTECIP" obbligatorio="true"  />
			
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
				<jsp:param name="entita" value='W9IMPRESE' />
				<jsp:param name="chiave" value='${param.codGara};${param.codLott}}' />
				<jsp:param name="nomeAttributoLista" value='listaImpreseInvitatePartecipanti' />
				<jsp:param name="idProtezioni" value="W9IMPRESE" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9imprese/imprese_invitatePartecipanti.jsp" />
				<jsp:param name="arrayCampi" value="'W9IMPRESE_CODGARA_','W9IMPRESE_CODLOTT_','W9IMPRESE_NUM_IMPR_','W9IMPRESE_NUM_RAGG_','IMPR_NOMEST_','W9IMPRESE_CODIMP_','W9IMPRESE_RUOLO_','W9IMPRESE_PARTECIP_','W9IMPRESE_TIPOAGG_'"/>
				<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,true,true,true,false,false" />
				<jsp:param name="usaContatoreLista" value="true" />
				<jsp:param name="sezioneListaVuota" value="false" />
				<jsp:param name="titoloSezione" value="Impresa invitata / partecipante" />
				<jsp:param name="titoloNuovaSezione" value="Nuova impresa invitata / partecipante" />
				<jsp:param name="descEntitaVociLink" value="impresa invitata / partecipante" />
				<jsp:param name="msgRaggiuntoMax"  value="e Imprese invitate / partecipanti" />
				<jsp:param name="numMaxDettagliInseribili" value="10" />
			</jsp:include>

			<c:if test='${modo eq "MODIFICA"}'>
				<gene:fnJavaScriptScheda funzione="modificaPartecipazione()" elencocampi="W9IMPRESE_PARTECIP" esegui="false" />
			</c:if>

		</c:when>
		<c:when test='${empty RISULTATO and modo eq "NUOVO" and not empty param.tipoImpresa and param.tipoImpresa ne 3}'>

			<gene:campoScheda campo="PARTECIP" obbligatorio="true" />
			
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
				<jsp:param name="entita" value='W9IMPRESE' />
				<jsp:param name="chiave" value='${param.codGara};${param.codLott}}' />
				<jsp:param name="nomeAttributoLista" value='listaImpreseInvitatePartecipanti' />
				<jsp:param name="idProtezioni" value="W9IMPRESE" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9imprese/imprese_invitatePartecipanti.jsp" />
				<jsp:param name="arrayCampi" value="'W9IMPRESE_CODGARA_','W9IMPRESE_CODLOTT_','W9IMPRESE_NUM_IMPR_','W9IMPRESE_NUM_RAGG_','IMPR_NOMEST_','W9IMPRESE_CODIMP_','W9IMPRESE_RUOLO_','W9IMPRESE_PARTECIP_','W9IMPRESE_TIPOAGG_'"/>
				<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,true,true,true,false,false" />
				<jsp:param name="usaContatoreLista" value="true" />
				<jsp:param name="sezioneListaVuota" value="false" />
				<jsp:param name="titoloSezione" value="Imprese invitate / partecipanti" />
				<jsp:param name="titoloNuovaSezione" value="Nuova impresa invitata / partecipante" />
				<jsp:param name="descEntitaVociLink" value="impresa invitata / partecipante" />
				<jsp:param name="msgRaggiuntoMax"  value="e Imprese invitate / partecipanti" />
				<jsp:param name="numMaxDettagliInseribili" value="10" />
				<jsp:param name="tipoImpresa" value="${param.tipoImpresa}" />
			</jsp:include>

			<gene:fnJavaScriptScheda funzione="modificaPartecipazione()" elencocampi="W9IMPRESE_PARTECIP" esegui="false" />

		</c:when>
	</c:choose>

			<gene:redefineInsert name="schedaNuovo" />

			<gene:redefineInsert name="schedaModifica">
				<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD")}'>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:modificaImpresa();" title="Modifica dati" tabindex="1501">
							${gene:resource("label.tags.template.dettaglio.schedaModifica")}</a></td>
					</tr>
				</c:if>
			</gene:redefineInsert>

			<gene:redefineInsert name="schedaConferma">
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:schedaConfermaSalvataggio();" title="Salva modifiche" tabindex="1501">
								${gene:resource("label.tags.template.dettaglio.schedaConferma")}</a></td>
				</tr>
			</gene:redefineInsert>

			<gene:campoScheda>
				<td class="comandi-dettaglio" colSpan="2">
			
			<c:choose>
				<c:when test='${modo eq "VISUALIZZA"}' >
					<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD")}'>
						<INPUT type="button"  class="bottone-azione" value='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' title='${gene:resource("label.tags.template.dettaglio.schedaModifica")}' onclick="javascript:modificaImpresa();">
					</c:if>
				</c:when>
				<c:when test='${(modo eq "NUOVO" and not empty param.tipoImpresa) or modo eq "MODIFICA"}' >
					<INPUT type="button" class="bottone-azione" value="Salva" title="Salva modifiche" onclick="javascript:schedaConfermaSalvataggio();">
					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:schedaAnnulla();">
				</c:when>
			</c:choose>
				&nbsp;
				</td>
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>
	
	document.forms[0].jspPathTo.value="w9/w9imprese/w9imprese-schedaPopup-insert.jsp";

	var codGara = "${param.codGara}";
	var codLott = "${param.codLott}";
	setValue("W9IMPRESE_CODGARA", codGara);
	setValue("W9IMPRESE_CODLOTT", codLott);
	
	function gestioneTipoPartecipante() {
		var tipoAgg = getValue("W9IMPRESE_TIPOAGG");
		if (tipoAgg != "") {
			bloccaRichiesteServer();
			document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=NUOVO&codGara=${param.codGara}&codLott=${param.codLott}&tipoImpresa="+tipoAgg;
		}
	}
	
	function modificaPartecipazione() {
		var partecipazione = getValue("W9IMPRESE_PARTECIP");
		
		for (var i=1; i <= lastIdW9IMPRESEVisualizzata; i++) {
			if (getValue("elementoSchedaMultiplaVisibileW9IMPRESE_" + i) == "1") {
				setValue("W9IMPRESE_PARTECIP_" + i, partecipazione);
			}
		}
	}
	
	function schedaAnnullaW9Imprese() {
		var numRaggr = "${param.numRagg}";
		var href = "";
		if (numRaggr == "") {
			href = "href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=VISUALIZZA&codGara=${param.codGara}&codLott=${param.codLott}&numImpr=${param.numImpr}&key=${param.key}";
		} else {
			href = "href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=VISUALIZZA&codGara=${param.codGara}&codLott=${param.codLott}&numImpr=${param.numImpr}&numRagg=${param.numRagg}&key=${param.key}";
		}
		document.location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + href;
	}
	
	function modificaImpresa() {
	<c:choose>
		<c:when test="${not empty param.numImpr and empty param.numRagg}">
			var href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=MODIFICA&codGara=${param.codGara}&codLott=${param.codLott}&numImpr=${param.numImpr}&key=${param.key}";
			document.location.href = href;
		</c:when>
		<c:when test="${empty param.numImpr and not empty param.numRagg}">
			var href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9imprese/w9imprese-schedaPopup-insert.jsp&modo=MODIFICA&codGara=${param.codGara}&codLott=${param.codLott}&numRagg=${param.numRagg}&key=${param.key}";
			document.location.href = href;
		</c:when>
	</c:choose>
	}
	
	function schedaConfermaSalvataggio() {
	<c:choose>
		<c:when test='${(modo eq "NUOVO" and not empty param.tipoImpresa and param.tipoImpresa eq 3) or modo eq "MODIFICA"}'>
			schedaConferma();
		</c:when>
		<c:when test='${(modo eq "NUOVO" and not empty param.tipoImpresa and param.tipoImpresa ne 3) or modo eq "MODIFICA"}'>
			// Controllo che almeno due sezioni, fra quelle visualizzate (e quindi non eliminate),
			// siano valorizzate e che una impresa sia mandataria ed una mandante.
			
			var sezioniValorizzate = 0;
			var numeroImpreseMandatarie = 0;
			var numeroImpreseMandanti = 0;
			for (var i=1; i <= lastIdW9IMPRESEVisualizzata; i++) {
				if (getValue("elementoSchedaMultiplaVisibileW9IMPRESE_" + i) == "1") {
					if (getValue("W9IMPRESE_CODIMP_" + i) != "" || getValue("W9IMPRESE_RUOLO_" + i) != "") {
						sezioniValorizzate++;
					}
				}
			}
			
			if (sezioniValorizzate >= 1) {
				schedaConferma();
			} else {
				clearMsg();
				outMsg("Inserire almeno un'impresa", "ERR");
				onOffMsg();
			}
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>	
	}

	<c:if test='${not empty RISULTATO and RISULTATO eq "OK"}' >
		bloccaRichiesteServer();
		historyVaiIndietroDi(1);
	</c:if>
	
	</gene:javaScript>
</gene:template>
<!--/div-->