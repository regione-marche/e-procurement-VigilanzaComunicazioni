<%
  /*
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

<c:if test='${modo eq "NUOVO"}'>
	<c:set var="contri"	value='${contri}' scope="request" />
	<c:set var="conint"	value='${conint}' scope="request" />
</c:if>


<gene:formScheda entita="CUPDATI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreCUPDATI">

	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<gene:campoScheda>
		<td colspan="2"><b>Dati generali</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="ID" visibile="false" />
	<gene:campoScheda campo="CONTRI" visibile="false" defaultValue="${contri}"/>
	<gene:campoScheda campo="CONINT" visibile="false" defaultValue="${conint}"/>
	<gene:campoScheda campo="ANNO_DECISIONE" obbligatorio="true" defaultValue="${anno_decisione}" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoAnnoDecisione" />
	<gene:campoScheda campo="CUP" modificabile="false"/>
	<gene:campoScheda campo="DATA_PROVVISORIO" visibile="false" />
	<gene:campoScheda campo="DATA_DEFINITIVO" modificabile="false" />
	<gene:campoScheda campo="DATA_CHIUSURA" />
	<gene:campoScheda campo="DESCRIZIONE_ESITO" />

	<gene:campoScheda campo="DESCRIZIONE_INTERVENTO" visibile="false" defaultValue="${descrizione_intervento}"/>
	<gene:campoScheda campo="SPONSORIZZAZIONE" visibile="false" defaultValue="N"/>
	<gene:campoScheda campo="FINANZA_PROGETTO" visibile="false" defaultValue="N"/>	
	<gene:campoScheda campo="COSTO" visibile="false" defaultValue="${costo}"/>
	<gene:campoScheda campo="FINANZIAMENTO" visibile="false" defaultValue="${finanziamento}"/>
	
	<gene:campoScheda campo="CODEIN" value="${sessionScope.uffint}"	visibile="false" />
	
	<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPDATIFunction" />
	
	<gene:campoScheda>
		<td colspan="2"><b>Natura e tipologia</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="NATURA" obbligatorio="true" defaultValue="${natura}">
		<gene:addValue value="" descr="" />
		<c:if test='${!empty listaValoriNatura}'>
			<c:forEach items="${listaValoriNatura}" var="valoriNatura">
				<gene:addValue value="${valoriNatura[0]}"
					descr="${valoriNatura[2]}" />
			</c:forEach>
		</c:if>
	</gene:campoScheda>

	<gene:campoScheda campo="TIPOLOGIA" obbligatorio="true" defaultValue="${tipologia}">
		<gene:addValue value="" descr="" />
		<c:if test='${!empty datiRiga.CUPDATI_NATURA}'> 
			<c:if test='${!empty listaValoriTipologia}'>
				<c:forEach items="${listaValoriTipologia}" var="valoriTipologia">
					<c:if test="${valoriTipologia[0] eq datiRiga.CUPDATI_NATURA}">
						<gene:addValue value="${valoriTipologia[1]}"
							descr="${valoriTipologia[2]}" />					
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>

	<gene:campoScheda>
		<td colspan="2"><b>Settore, sottosettore e categoria</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="SETTORE" obbligatorio="true" defaultValue="${settore}">
		<gene:addValue value="" descr="" />
		<c:if test='${!empty listaValoriSettore}'>
			<c:forEach items="${listaValoriSettore}" var="valoriSettore">
				<gene:addValue value="${valoriSettore[0]}"
					descr="${fn:substring(valoriSettore[3],0,70)}" />
			</c:forEach>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="SOTTOSETTORE" obbligatorio="true" defaultValue="${sottosettore}">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_SETTORE}">
			<c:if test='${!empty listaValoriSottosettore}'>
				<c:forEach items="${listaValoriSottosettore}" var="valoriSottosettore">
					<c:if test="${valoriSottosettore[0] eq datiRiga.CUPDATI_SETTORE}">
						<gene:addValue value="${valoriSottosettore[1]}"
							descr="${fn:substring(valoriSottosettore[3],0,70)}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="CATEGORIA" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_SETTORE && !empty datiRiga.CUPDATI_SOTTOSETTORE}">
			<c:if test='${!empty listaValoriCategoria}'>
				<c:forEach items="${listaValoriCategoria}" var="valoriCategoria">
					<c:if test="${valoriCategoria[0] eq datiRiga.CUPDATI_SETTORE && valoriCategoria[1] eq datiRiga.CUPDATI_SOTTOSETTORE}">
						<gene:addValue value="${valoriCategoria[2]}"
							descr="${fn:substring(valoriCategoria[3],0,70)}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>

	<gene:campoScheda campo="CPV" href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' modificabile="false" speciale="true" defaultValue="${cpv}">
		<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
			<gene:popupCampo titolo="Dettaglio codice CPV"	href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' />
		</c:if>
	</gene:campoScheda>
	<gene:campoScheda campo="CUMULATIVO" />
	
	<gene:campoScheda>
		<td colspan="2"><b>CUP Master</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="CUP_MASTER" defaultValue="${cup_master}" speciale="true">
		<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
			<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
		</c:if>
	</gene:campoScheda>
	<gene:campoScheda campo="RAGIONI_COLLEGAMENTO" />

	<gene:fnJavaScriptScheda funzione='gestioneNATURA("#CUPDATI_NATURA#")' elencocampi="CUPDATI_NATURA" esegui="true" />
	<gene:fnJavaScriptScheda funzione='modificaNATURA("#CUPDATI_NATURA#")' elencocampi="CUPDATI_NATURA" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaSETTORE("#CUPDATI_SETTORE#")' elencocampi="CUPDATI_SETTORE" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaSOTTOSETTORE("#CUPDATI_SETTORE#","#CUPDATI_SOTTOSETTORE#")' elencocampi="CUPDATI_SETTORE;CUPDATI_SOTTOSETTORE" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaCUPMASTER("#CUPDATI_CUP_MASTER#")' elencocampi="CUPDATI_CUP_MASTER" esegui="true" />
		
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

<gene:redefineInsert name="addToAzioni">

	<tr>
		<c:choose>
	        <c:when test='${isNavigazioneDisattiva ne "1" and !empty datiRiga.CUPDATI_CUP and !(datiRiga.CUPDATI_DESCRIZIONE_ESITO eq "REVOCATO" || datiRiga.CUPDATI_DESCRIZIONE_ESITO eq "CHIUSO")}'>
	          <td class="vocemenulaterale">
				<a href="javascript:richiestaChiusuraCUP();" title="Invio dati per chiusura CUP" tabindex="1514">
					Invio dati per chiusura CUP
				</a>
			  </td>
	        </c:when>
		    <c:otherwise>
		       <td>
					Invio dati per chiusura CUP
			   </td>
		    </c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<c:choose>
	        <c:when test='${isNavigazioneDisattiva ne "1" and !empty datiRiga.CUPDATI_CUP and !(datiRiga.CUPDATI_DESCRIZIONE_ESITO eq "REVOCATO" || datiRiga.CUPDATI_DESCRIZIONE_ESITO eq "CHIUSO")}'>
	          <td class="vocemenulaterale">
				<a href="javascript:richiestaRevocaCUP();" title="Invio dati per revoca CUP" tabindex="1515">
					Invio dati per revoca CUP
				</a>
			  </td>
	        </c:when>
		    <c:otherwise>
		       <td>
					Invio dati per revoca CUP
			   </td>
		    </c:otherwise>
		</c:choose>
	</tr>
</gene:redefineInsert>

<gene:javaScript>

	var arrayTipologia=new Array();
	var arraySottosettore = new Array();
	var arrayCategoria = new Array();


	<c:forEach items="${listaValoriTipologia}" var="valoreTipologia">
		arrayTipologia.push(new tabellatoTipologia(${gene:string4Js(valoreTipologia[0])}, ${gene:string4Js(valoreTipologia[1])},${gene:string4Js(valoreTipologia[2])}));
	</c:forEach>

	<c:forEach items="${listaValoriSottosettore}" var="valoreSottosettore">
		arraySottosettore.push(new tabellatoSottosettoreCategoria(${gene:string4Js(valoreSottosettore[0])}, ${gene:string4Js(valoreSottosettore[1])},${gene:string4Js(valoreSottosettore[2])},${gene:string4Js(valoreSottosettore[3])}));
	</c:forEach>

	<c:forEach items="${listaValoriCategoria}" var="valoreCategoria">
		arrayCategoria.push(new tabellatoSottosettoreCategoria(${gene:string4Js(valoreCategoria[0])}, ${gene:string4Js(valoreCategoria[1])},${gene:string4Js(valoreCategoria[2])},${gene:string4Js(valoreCategoria[3])}));
	</c:forEach>


	function tabellatoTipologia(aCupcod1, aCupcod2, aCupDesc ) {
		var cupcod1;
		var cupcod2;
		var cupdesc;
		this.cupcod1 = aCupcod1;
		this.cupcod2 = aCupcod2;
		this.cupdesc = aCupDesc;
	}
	
	function tabellatoSottosettoreCategoria(aCupcod1, aCupcod2, aCupcod3, aCupDesc ) {
		var cupcod1;
		var cupcod2;
		var cupcod3;
		var cupdesc;
		this.cupcod1 = aCupcod1;
		this.cupcod2 = aCupcod2;
		this.cupcod3 = aCupcod3;
		this.cupdesc = aCupDesc;
	}


	function gestioneNATURA(valoreNatura){
		if (valoreNatura == '06') {
			showObj("rowCUPDATI_CUMULATIVO", true);
		} else {
			showObj("rowCUPDATI_CUMULATIVO", false);
			setValue("CUPDATI_CUMULATIVO","");
		}
	}
	
	function modificaNATURA(valoreNatura){
		setValue("CUPDATI_TIPOLOGIA","");
		
		var i;
		var optTIPOLOGIA=document.forms[0].CUPDATI_TIPOLOGIA.options;
		optTIPOLOGIA.length = 0;
		optTIPOLOGIA.add(new Option("", ""));
		for(i=0; i < arrayTipologia.length ;i++){
			if (arrayTipologia[i].cupcod1 == valoreNatura) {
				optTIPOLOGIA.add(new Option(arrayTipologia[i].cupdesc, arrayTipologia[i].cupcod2));
			}
		}
	}


	function modificaSETTORE(valoreSettore){
	
		setValue("CUPDATI_SOTTOSETTORE","");
		setValue("CUPDATI_CATEGORIA","");
		
		var i;
		var optSOTTOSETTORE=document.forms[0].CUPDATI_SOTTOSETTORE.options;
		optSOTTOSETTORE.length = 0;
		optSOTTOSETTORE.add(new Option("", ""));
		for(i=0; i < arraySottosettore.length ;i++){
			if (arraySottosettore[i].cupcod1 == valoreSettore) {
				optSOTTOSETTORE.add(new Option(arraySottosettore[i].cupdesc, arraySottosettore[i].cupcod2));
			}
		}
	}
	
	
	function modificaSOTTOSETTORE(valoreSettore, valoreSottoSettore){

		setValue("CUPDATI_CATEGORIA","");
		
		var i;
		var optCATEGORIA=document.forms[0].CUPDATI_CATEGORIA.options;
		optCATEGORIA.length = 0;
		optCATEGORIA.add(new Option("", ""));
		for(i=0; i < arrayCategoria.length ;i++){
			if (arrayCategoria[i].cupcod1 == valoreSettore && arrayCategoria[i].cupcod2 == valoreSottoSettore) {
				optCATEGORIA.add(new Option(arrayCategoria[i].cupdesc, arrayCategoria[i].cupcod3));
			}
		}
	}

	
	function modificaCUPMASTER(valoreCUPMaster) {
		if (valoreCUPMaster != '') {
			showObj("rowCUPDATI_RAGIONI_COLLEGAMENTO", true);	
		} else {
			showObj("rowCUPDATI_RAGIONI_COLLEGAMENTO", false);	
			setValue("CUPDATI_RAGIONI_COLLEGAMENTO", "");	
		}
	}

	function formCPV(modifica){
		openPopUpCustom("href=commons/dettaglio-codice-cpv.jsp&key=" + document.forms[0].key.value + "&keyParent=" + document.forms[0].keyParent.value + "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")+"&campo=CUPDATI_CPV&valore="+ getValue("CUPDATI_CPV"), "formCPV", 700, 300, 1, 1);
	}
	
	
	function richiestaListaCUP() {
		openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=CUPDATI_CUP_MASTER&codiceCUP=" + getValue("CUPDATI_CUP_MASTER"), "listaCUP", 900, 550, 1, 1);
	}
	
	function richiestaChiusuraCUP() {
		openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-chiusura-revoca.jsp?id=" + getValue("CUPDATI_ID") + "&tipoOperazione=C&codiceCUP=" + getValue("CUPDATI_CUP"), "chiusuraCUP", 900, 400, 1, 1);
	}
	
	function richiestaRevocaCUP() {
		openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-chiusura-revoca.jsp?id=" + getValue("CUPDATI_ID") + "&tipoOperazione=R&codiceCUP=" + getValue("CUPDATI_CUP"), "revocaCUP", 900, 400, 1, 1);
	}

	
</gene:javaScript>

