
<%
	/*
	 * Created on: 30/04/2013
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="modifica-anagrafica-lotto-scheda">
	
	
	<gene:redefineInsert name="corpo">
	<gene:setString name="titoloMaschera" value='Modifica Anagrafica Lotti' />
	<gene:formScheda entita="W9LOTT" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9LOTT"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreModificheLottiGara">

		<c:set var="key" value='${key}' scope="request" />
		<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
		<c:if test='${(codgara eq "")||(empty codgara)}'>
			<c:set var="codgara" value='${gene:getValCampo(keyParent,"CODGARA")}' />
		</c:if>
		<input type="hidden" name="codgara" value="${codgara}" />
		<input type="hidden" name="tipoScheda" value="anagrafica-lotto" />
		
		<gene:gruppoCampi idProtezioni="MODFORN">
			<gene:campoScheda nome="ForniSer">
				<td colspan="2"><b>Modalit&agrave; di acquisizione forniture /	servizi</b></td>
			</gene:campoScheda>
			<c:if test="${fn:length(datiTabellatoFornList) > 0}">
				<c:forEach begin="0" end="${fn:length(datiTabellatoFornList)-1}" var="indiceTabForn">
					<c:set var="valoreForn" value="0" />
					<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
						campo="ID_APPALTO_${indiceTabForn+1}"
						title="${datiTabellatoFornList[indiceTabForn][1]}"
						definizione="N5;0;;SN;" value="${valoreForn}"
						gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNo" />
					<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
						campo="VALORE_ID_APPALTO_${indiceTabForn+1}"
						title="${datiTabellatoFornList[indiceTabForn][1]}"
						value="${datiTabellatoFornList[indiceTabForn][0]}"
						definizione="N5;0;;;" visibile="false" />
				</c:forEach>
			</c:if>
		</gene:gruppoCampi>
	
		<gene:gruppoCampi idProtezioni="MODLAV">
			<gene:campoScheda nome="TipLav">
				<td colspan="2"><b>Modalit&agrave; tipologia lavoro</b></td>
			</gene:campoScheda>
			<c:if test="${fn:length(datiTabellatoLavList) >0}">
				<c:forEach begin="0" end="${fn:length(datiTabellatoLavList)-1}"
					var="indiceTabLav">
					<c:set var="valoreLav" value="0" />
					<gene:campoScheda campoFittizio="true" entita="W9APPALAV"
						campo="ID_APPALTO_${indiceTabLav+1}"
						title="${datiTabellatoLavList[indiceTabLav][1]}"
						definizione="N5;0;;SN;" value="${valoreLav}"
						gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNo" />
					<gene:campoScheda campoFittizio="true" entita="W9APPALAV"
						campo="VALORE_ID_APPALTO_${indiceTabLav+1}"
						title="${datiTabellatoLavList[indiceTabLav][1]}"
						value="${datiTabellatoLavList[indiceTabLav][0]}"
						definizione="N5;0;;;" visibile="false" />
				</c:forEach>
			</c:if>
		</gene:gruppoCampi>
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="ID_TIPO_PRESTAZIONE" />
		<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" />
		<gene:campoScheda campo="CUIINT" />
		<gene:campoScheda campo="ID_SCELTA_CONTRAENTE_50" />
		<gene:campoScheda campo="ID_MODO_GARA" />
		<gene:campoScheda campo="URL_EPROC" />
		<gene:campoScheda campo="URL_COMMITTENTE" />
		
		<gene:gruppoCampi idProtezioni="CONDIZ">
			<gene:campoScheda nome="CondizioniRicorsoProceduraNegoziata">
				<td colspan="2"><b>Condizioni che giustificano il ricorso alla procedura negoziata</b></td>
			</gene:campoScheda>
			<c:if test="${fn:length(datiTabellatoCondList) >0}">
				<c:forEach begin="0" end="${fn:length(datiTabellatoCondList)-1}"
					var="indiceTabCond">
					<c:set var="valoreCond" value="0" />
					<gene:campoScheda campoFittizio="true" entita="W9COND"
						campo="ID_CONDIZIONE_${indiceTabCond+1}"
						title="${datiTabellatoCondList[indiceTabCond][1]}"
						value="${valoreCond}" definizione="N5;0;;SN;"
						gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNo" />
					<gene:campoScheda campoFittizio="true" entita="W9COND"
						campo="VALORE_ID_CONDIZIONE_${indiceTabCond+1}"
						title="${datiTabellatoCondList[indiceTabCond][1]}"
						value="${datiTabellatoCondList[indiceTabCond][0]}"
						definizione="N5;0;;;" visibile="false" />
				</c:forEach>
			</c:if>
		</gene:gruppoCampi>
		<gene:campoScheda>
			<td colspan="2"><b>Luogo acquisizione del contratto (ISTAT)</b></td>
		</gene:campoScheda>
		<gene:archivio titolo="Comuni" lista="gene/commons/istat-comuni-lista-popup.jsp" scheda="" schedaPopUp=""
			campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3" chiave=""	where="" formName="" inseribile="false">
			<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true"
				definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia luogo di esecuzione del contratto"
				value="${descrProvinciaEsecuzione}" />
			<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true"
				definizione="T120;0;;;" title="Comune luogo di esecuzione del contratto"
				value="${denomComuneEsecuzione}" />
			<gene:campoScheda campo="LUOGO_ISTAT" title="Codice ISTAT del Comune luogo di esecuzione del contratto" />
		</gene:archivio>
	
		<gene:redefineInsert name="pulsanteSalva" >
			<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:Conferma()">
		</gene:redefineInsert>
		<gene:redefineInsert name="schedaConferma">
			<tr>
				<td class="vocemenulaterale"><a href="javascript:Conferma();" title="Conferma" tabindex="1501">Conferma</a></td>
			</tr>
		</gene:redefineInsert>
		
		<gene:redefineInsert name="pulsanteNuovo" />
		<gene:redefineInsert name="schedaNuovo" />
	
		<gene:fnJavaScriptScheda
			funzione='modifySceltaContr("#W9LOTT_ID_SCELTA_CONTRAENTE#")'
			elencocampi="W9LOTT_ID_SCELTA_CONTRAENTE" esegui="true" />

		<gene:campoScheda>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
		</gene:campoScheda>
	</gene:formScheda>

	
	</gene:redefineInsert>
	
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addToDocumenti-validazioneGaraLottiAvvisi.jsp" />
	</gene:redefineInsert>
	
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	</gene:redefineInsert>
	
	<gene:javaScript>
	
	document.forms[0].jspPathTo.value="w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
	document.forms[0].jspPath.value="/WEB-INF/pages/w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
	
	function modifySceltaContr(valore) {
		var vis = (valore==4);
		showObj("rowCondizioniRicorsoProceduraNegoziata",vis);
		for(i=1; i<=${fn:length(datiTabellatoCondList)}; i++)
			showObj("rowW9COND_ID_CONDIZIONE_"+i,vis);

		if(!vis) {
			var numeroElementi = ${fn:length(datiTabellatoCondList)};
			for(var i=1; i<=numeroElementi; i++)
				setValue("W9COND_ID_CONDIZIONE_" + i,"");
		}
	}
	
	function Conferma() {
		response = confirm("Questa operazione modificher&agrave; i dati nelle maschere dei lotti. Continuare?");
		if (response) {
			schedaConferma();
		}
		return;
	}
	
		
	</gene:javaScript>

</gene:template>



