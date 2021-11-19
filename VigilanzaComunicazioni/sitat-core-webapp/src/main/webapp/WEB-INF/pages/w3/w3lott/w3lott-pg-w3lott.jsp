 <%
	/*
	 * Created on 20-Ott-2008
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

<gene:formScheda entita="W3LOTT" gestisciProtezioni="true" 
	gestore="it.eldasoft.sil.w3.tags.gestori.submit.GestoreW3LOTT"
	plugin="it.eldasoft.sil.w3.tags.gestori.plugin.GestoreW3LOTT">
	
	<c:set var="v3_04_6_Attiva" value='${gene:callFunction2("it.eldasoft.sil.w3.tags.funzioni.IsAttivaV3_04_6Function",pageContext,gene:getValCampo(keyParent,"NUMGARA"))}' />
	
	<gene:campoScheda campo="NUMGARA" visibile="false" defaultValue='${gene:getValCampo(keyParent,"NUMGARA")}'/>
	<gene:campoScheda campo="NUMLOTT" visibile="false" />	
	<gene:campoScheda campo="CIG" modificabile="false"/>
	<gene:campoScheda title="Stato del lotto" campo="STATO_SIMOG" modificabile="false" defaultValue="1"/>
	<gene:campoScheda entita="W3GARA" campo="STATO_SIMOG" visibile="false" where="W3GARA.NUMGARA = W3LOTT.NUMGARA"/>
	<gene:campoScheda campo="DATA_CREAZIONE_LOTTO" modificabile="false" />
	<gene:campoScheda campo="DATA_PUBBLICAZIONE" modificabile="false" visibile="${datiRiga.W3LOTT_STATO_SIMOG ne '1'}"/>
	<gene:campoScheda campo="DATA_CANCELLAZIONE_LOTTO" modificabile="false" visibile="${datiRiga.W3LOTT_STATO_SIMOG eq '6'}"/>
	<gene:campoScheda campo="ID_MOTIVAZIONE" modificabile="false" visibile="${datiRiga.W3LOTT_STATO_SIMOG eq '6'}"/>
	<gene:campoScheda campo="NOTE_CANC" modificabile="false" visibile="${datiRiga.W3LOTT_STATO_SIMOG eq '6'}"/>
	
	<gene:campoScheda>
		<td colspan="2"><b><br>SEZIONE I: DATI DEL LOTTO<br><br></b></td>
	</gene:campoScheda>
	<gene:campoScheda>
		<td colspan="2"><b>I.1) Dati generali</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="OGGETTO" obbligatorio="true"/>
	<gene:campoScheda campo="TIPO_CONTRATTO" obbligatorio="true"/>
	
	<gene:campoScheda addTr="false">
		<tr id="rowTipologiaLavoro" name ="W3LOTTIPI_TIPOLOGIALAVORO" >
			<td colspan="2"><b><br>Tipologia lavoro</b></td>
		</tr>
	</gene:campoScheda>
	<c:forEach items="${tabellatoTipologiaLavoro}" var="tabellato" varStatus="indice" >
		<gene:campoScheda campo="TIPOLOGIALAVORO${tabellato[1]}" entita="W3LOTTTIPI" value="${tabellato[2]}" 
		 	campoFittizio="true" definizione="T1;0;;SN" title='${tabellato[0]}' 
		 	visibile="${tabellato[3] eq '' or empty tabellato[3] or (tabellato[3] eq '1' and tabellato[2] eq '1')}">
		</gene:campoScheda>
	</c:forEach>
	
	<gene:campoScheda addTr="false">
		<tr id="rowTipologiaFornitura" name ="W3LOTTIPI_TIPOLOGIAFORNITURA" >
			<td colspan="2"><b><br>Tipologia fornitura</b></td>
		</tr>
	</gene:campoScheda>
	<c:forEach items="${tabellatoTipologiaFornitura}" var="tabellato" varStatus="indice" >
		<gene:campoScheda campo="TIPOLOGIAFORNITURA${tabellato[1]}" entita="W3LOTTTIPI" value="${tabellato[2]}" 
		 	campoFittizio="true" definizione="T1;0;;SN" title='${tabellato[0]}' 
			visibile="${tabellato[3] eq '' or empty tabellato[3] or (tabellato[3] eq '1' and tabellato[2] eq '1')}">
		</gene:campoScheda>
	</c:forEach>
	<gene:campoScheda>
		<td colspan="2"><b><br>Ulteriori dati</b></td>
	</gene:campoScheda>
	
	<gene:campoScheda campo="FLAG_ESCLUSO" obbligatorio="true" defaultValue="2"/>
	<gene:campoScheda campo="ID_ESCLUSIONE" />
	<gene:campoScheda campo="FLAG_REGIME" obbligatorio="true" defaultValue="2"/>
	<gene:campoScheda campo="ART_REGIME" />

	<gene:campoScheda campo="CPV" title="Codice CPV" obbligatorio="true" href="#" speciale="true" >
		<gene:popupCampo titolo="Dettaglio CPV" href="formCPV()" />
	</gene:campoScheda>
	<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}" campoFittizio="true" definizione="T150" />
	
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3CPVFunction" parametro="${gene:getValCampo(key, 'NUMGARA')};${gene:getValCampo(key, 'NUMLOTT')}" />
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W3CPV'/>
		<jsp:param name="chiave" value='${key}'/>
		<jsp:param name="nomeAttributoLista" value='datiW3CPV' />
		<jsp:param name="idProtezioni" value="W3CPV" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3cpv/w3cpv-interno-scheda.jsp" />
		<jsp:param name="arrayCampi" value="'W3CPV_CODGARA','W3CPV_CODLOTT', 'W3CPV_NUM_CPV_', 'W3CPV_CPV_','TABCPV_CPVDESC_'" />
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="sezioneListaVuota" value="false"/>
		<jsp:param name="titoloSezione" value="CPV Secondario" />
		<jsp:param name="titoloNuovaSezione" value="Nuovo CPV Secondario" />
		<jsp:param name="descEntitaVociLink" value="Descrizione CPV Secondario" />
		<jsp:param name="msgRaggiuntoMax" value="e Descrizione CPV Secondario" />
	</jsp:include>
	<gene:campoScheda>
		<td colspan="2"><b>&nbsp;</b></td>
	</gene:campoScheda>
	<gene:campoScheda title="Categoria merceologica oggetto della fornitura di cui al DPCM soggetti aggregatori" campo="CATEGORIA_MERC" obbligatorio="true" visibile="${ver_simog eq 4}" gestore="it.eldasoft.sil.w3.tags.gestori.decoratori.GestoreCategorieMerceologiche"/>
	<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" obbligatorio="true"/>
	<gene:campoScheda campo="ID_AFF_RISERVATI"/>
	
	<gene:campoScheda>
		<tr id="rowListaCOND" name ="W3LOTT_LISTACOND" >
			<td colspan="2"><br><b>Condizioni che giustificano il ricorso alla procedura negoziata</b></td>
		</tr>
	</gene:campoScheda>
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3CONDFunction" parametro="${gene:getValCampo(key, 'NUMGARA')};${gene:getValCampo(key, 'NUMLOTT')}" />
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W3COND'/>
		<jsp:param name="chiave" value='${key}'/>
		<jsp:param name="nomeAttributoLista" value='datiW3COND' />
		<jsp:param name="idProtezioni" value="W3COND" />
		<jsp:param name="sezioneListaVuota" value="false" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3cond/w3cond-interno-scheda.jsp"/>
		<jsp:param name="arrayCampi" value="'W3COND_NUMGARA_', 'W3COND_NUMLOTT_', 'W3COND_NUM_COND_', 'W3COND_ID_CONDIZIONE_' "/>
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,true" />
		<jsp:param name="titoloSezione" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ulteriore condizione n. " />
		<jsp:param name="titoloNuovaSezione" value=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Altra condizione" />
		<jsp:param name="descEntitaVociLink" value="altra condizione" />
		<jsp:param name="msgRaggiuntoMax" value="e condizioni"/>
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="numMaxDettagliInseribili" value="5"/>
	</jsp:include>
	
	<gene:campoScheda>
		<td colspan="2"><br><b>I.2) Dati economici</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="IMPORTO_LOTTO" obbligatorio="true"/>
	<gene:campoScheda campo="IMPORTO_OPZIONI" title="di cui per opzioni/ripetizioni"/>
	<gene:campoScheda campo="IMPORTO_ATTUAZIONE_SICUREZZA" title="di cui per attuazione della sicurezza"/>
	<gene:campoScheda campo="IMPORTO_SA" visibile="false" defaultValue="0"/>
	<gene:campoScheda campo="IMPORTO_IMPRESA" visibile="false" defaultValue="0"/>
	<gene:campoScheda campo="DURATA_AFFIDAMENTO" visibile="${v3_04_6_Attiva eq 'true'}"/>
	<gene:campoScheda campo="DURATA_RINNOVI" visibile="${v3_04_6_Attiva eq 'true'}"/>
	
	<gene:campoScheda>
		<td colspan="2"><br><b>I.3) Categoria prevalente ed ulteriori categorie</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="ID_CATEGORIA_PREVALENTE" obbligatorio="true"/>
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3LOTTCATEFunction" parametro="${gene:getValCampo(key, 'NUMGARA')};${gene:getValCampo(key, 'NUMLOTT')}" />
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W3LOTTCATE'/>
		<jsp:param name="chiave" value='${key}'/>
		<jsp:param name="nomeAttributoLista" value='datiW3LOTTCATE' />
		<jsp:param name="idProtezioni" value="W3LOTTCATE" />
		<jsp:param name="sezioneListaVuota" value="false" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3lottcate/w3lottcate-interno-scheda.jsp"/>
		<jsp:param name="arrayCampi" value="'W3LOTTCATE_NUMGARA_', 'W3LOTTCATE_NUMLOTT_', 'W3LOTTCATE_NUMCATE_', 'W3LOTTCATE_CATEGORIA_' "/>
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,true" />
		<jsp:param name="titoloSezione" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ulteriore categoria n. " />
		<jsp:param name="titoloNuovaSezione" value=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Altra categoria" />
		<jsp:param name="descEntitaVociLink" value="altra categoria" />
		<jsp:param name="msgRaggiuntoMax" value="e categorie"/>
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="numMaxDettagliInseribili" value="5"/>
	</jsp:include>
	
	<gene:campoScheda>
		<td colspan="2"><br><b>I.4) Luogo di esecuzione</b></td>
	</gene:campoScheda>
	<gene:archivio titolo="Comuni" lista="gene/commons/istat-comuni-lista-popup.jsp" scheda="" schedaPopUp=""
		campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3" chiave=""	where="" formName="" inseribile="false">
		<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true"	definizione="T2;0;Agx15;;;" title="Provincia" value="${descrizioneProvincia}" />
		<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true" definizione="T120;0;;;" title="Comune"	value="${descrizioneComune}" />
		<gene:campoScheda campo="LUOGO_ISTAT" />
	</gene:archivio>
	
	<gene:campoScheda campo="LUOGO_NUTS" href="#" modificabile="false" speciale="true" >
		<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
	</gene:campoScheda>
	
	<gene:campoScheda>
		<td colspan="2"><br><b>I.6) Riferimento alla programmazione</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="FLAG_DL50" obbligatorio="true" defaultValue="1"/>
	<gene:campoScheda campo="PRIMA_ANNUALITA"/>
	<gene:campoScheda campo="ANNUALE_CUI_MININF"/>
	
	<gene:campoScheda>
		<td colspan="2"><br><b>I.7) Ripetizioni</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="FLAG_PREVEDE_RIP" title="L'appalto prevede ripetizioni o altre opzioni?" defaultValue="2" obbligatorio="true"/>
	<gene:campoScheda campo="MOTIVO_COLLEGAMENTO" obbligatorio="true"/>
	<gene:campoScheda campo="CIG_ORIGINE_RIP"/>

	<gene:campoScheda>
		<td colspan="2"><br><b>I.8) CUP</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="FLAG_CUP" obbligatorio="true"/>
	<gene:campoScheda addTr="false">
		<tr id="rowListaCUP" name ="W3LOTT_LISTACUP" >
			<td colspan="2"><b><br>Lista dei CUP associati al CIG</b></td>
		</tr>
	</gene:campoScheda>
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3LOTTCUPFunction" parametro="${gene:getValCampo(key, 'NUMGARA')};${gene:getValCampo(key, 'NUMLOTT')}" />
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W3LOTTCUP'/>
		<jsp:param name="chiave" value='${key}'/>
		<jsp:param name="nomeAttributoLista" value='datiW3LOTTCUP' />
		<jsp:param name="idProtezioni" value="W3LOTTCUP" />
		<jsp:param name="sezioneListaVuota" value="false" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3lottcup/w3lottcup-interno-scheda.jsp"/>
		<jsp:param name="arrayCampi" value="'W3LOTTCUP_NUMGARA_', 'W3LOTTCUP_NUMLOTT_', 'W3LOTTCUP_NUMCUP_', 'W3LOTTCUP_CUP_', 'W3LOTTCUP_DATI_DIPE_' "/>
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,true,true" />
		<jsp:param name="titoloSezione" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CUP " />
		<jsp:param name="titoloNuovaSezione" value=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CUP " />
		<jsp:param name="descEntitaVociLink" value="CUP" />
		<jsp:param name="msgRaggiuntoMax" value="e CUP"/>
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="numMaxDettagliInseribili" value="5"/>
	</jsp:include>
	
	<c:if test="${datiRiga.W3GARA_STATO_SIMOG eq '5'
		or datiRiga.W3GARA_STATO_SIMOG eq '6' or datiRiga.W3GARA_STATO_SIMOG eq '7'}">
		<gene:redefineInsert name="pulsanteNuovo" />
		<gene:redefineInsert name="schedaNuovo" />
	</c:if>

	<c:if test="${datiRiga.W3LOTT_STATO_SIMOG eq '5' 
		or datiRiga.W3LOTT_STATO_SIMOG eq '6'
		or datiRiga.W3LOTT_STATO_SIMOG eq '7'
		or datiRiga.W3GARA_STATO_SIMOG eq '5'
		or datiRiga.W3GARA_STATO_SIMOG eq '6'
		or datiRiga.W3GARA_STATO_SIMOG eq '7'}">
		<gene:redefineInsert name="pulsanteModifica" />
		<gene:redefineInsert name="schedaModifica" />
	</c:if>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	
	<gene:fnJavaScriptScheda funzione="gestioneFLAG_ESCLUSO('#W3LOTT_FLAG_ESCLUSO#')" elencocampi="W3LOTT_FLAG_ESCLUSO" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneFLAG_REGIME('#W3LOTT_FLAG_REGIME#')" elencocampi="W3LOTT_FLAG_REGIME" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneFLAG_DL50('#W3LOTT_FLAG_DL50#')" elencocampi="W3LOTT_FLAG_DL50" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneSCELTA_TIPO_CONTRATTO('#W3LOTT_TIPO_CONTRATTO#')" elencocampi="W3LOTT_TIPO_CONTRATTO" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneRESET_VALORI_TIPO_CONTRATTO()" elencocampi="W3LOTT_TIPO_CONTRATTO" esegui="false" />
	<gene:fnJavaScriptScheda funzione='gestioneOBBLIGATORIO_CUP("#W3LOTT_FLAG_CUP#")'	elencocampi="W3LOTT_FLAG_CUP" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneSCELTA_CONTRAENTE('#W3LOTT_ID_SCELTA_CONTRAENTE#')" elencocampi="W3LOTT_ID_SCELTA_CONTRAENTE" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneSCELTA_CONTRAENTE2('#W3LOTT_ID_SCELTA_CONTRAENTE#')" elencocampi="W3LOTT_ID_SCELTA_CONTRAENTE" esegui="false" />
	<gene:fnJavaScriptScheda funzione="gestioneFLAG_PREVEDE_RIP('#W3LOTT_FLAG_PREVEDE_RIP#')" elencocampi="W3LOTT_FLAG_PREVEDE_RIP" esegui="false" />
</gene:formScheda>


<gene:javaScript>

	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
	
		_creaFinestraAlberoCpvVP();
		_creaLinkAlberoCpvVP($("#W3LOTT_CPV").parent(), "${modo}", $("#W3LOTT_CPV"), $("#W3LOTT_CPVview"), $("#CPVDESC") );
		
		for (var i=1; i <= maxIdW3CPVVisualizzabile; i++) {
			var l1 = "#W3CPV_CPV_" + i;
			var l2 = "#W3CPV_CPV_" + i + "view";
			var l3 = "#TABCPV_CPVDESC_" + i;
			_creaLinkAlberoCpvVP($(l1).parent(), "${modo}", $(l1), $(l2), $(l3) );
		}
		
		$("input[name*='CPV']").attr('readonly','readonly');
		$("input[name*='CPV']").attr('tabindex','-1');
		$("input[name*='CPV']").css('border-color','#A3A6FF');
		$("input[name*='CPV']").css('border-width','1px');
		$("input[name*='CPV']").css('background-color','#E0E0E0');
		
		_creaFinestraAlberoNUTS();
		_creaLinkAlberoNUTS($("#W3LOTT_LUOGO_NUTS").parent(), "${modo}", $("#W3LOTT_LUOGO_NUTS"), $("#W9LOTT_LUOGO_NUTSview") );

		$("input[name^='W3LOTT_LUOGO_NUTS']").attr('readonly','readonly');
		$("input[name^='W3LOTT_LUOGO_NUTS']").attr('tabindex','-1');
		$("input[name^='W3LOTT_LUOGO_NUTS']").css('border-width','1px');
		$("input[name^='W3LOTT_LUOGO_NUTS']").css('background-color','#E0E0E0');
		
	});

	function gestioneFLAG_PREVEDE_RIP(value){
		if(value=='1'){
			document.forms[0].W3LOTT_MOTIVO_COLLEGAMENTO.value='10';
		}
	}
	
	function gestioneFLAG_ESCLUSO(value){
		document.getElementById("rowW3LOTT_ID_ESCLUSIONE").style.display = (value == '1' ? '':'none');
		if(value!='1'){
			document.forms[0].W3LOTT_ID_ESCLUSIONE.value='';
		}
	}
	
	function gestioneFLAG_REGIME(value){
		document.getElementById("rowW3LOTT_ART_REGIME").style.display = (value == '1' ? '':'none');
		if(value!='1'){
			document.forms[0].W3LOTT_ART_REGIME.value='';
		}
	}
	
	function gestioneFLAG_DL50(value){
		document.getElementById("rowW3LOTT_PRIMA_ANNUALITA").style.display = (value == '1' ? '':'none');
		document.getElementById("rowW3LOTT_ANNUALE_CUI_MININF").style.display = (value == '1' ? '':'none');
		if(value!='1'){
			document.forms[0].W3LOTT_PRIMA_ANNUALITA.value='';
			document.forms[0].W3LOTT_ANNUALE_CUI_MININF.value='';
		}
	}
	
	function gestioneOBBLIGATORIO_CUP(valore){
		if (valore == 1) {
			$("#rowListaCUP").show();
			$("[id^='rowW3LOTT_LISTACUP']").show();
			$("[id^='rowLinkAddW3LOTTCUP']").show();
		} else {
			$("#rowListaCUP").hide();
			$("[id^='rowW3LOTT_LISTACUP']").hide();
			$("[id^='rowLinkAddW3LOTTCUP']").hide();
			$("[id^='rowW3LOTTCUP_CUP_']").hide();
			$("[id^='rowW3LOTTCUP_DATI_DIPE_']").hide();
			$("[id^='rowtitoloW3LOTTCUP_']").hide();
		}
	}
	
	function gestioneSCELTA_TIPO_CONTRATTO(tipo_contratto){
		if (tipo_contratto=='' || tipo_contratto=='S') {
			$("#rowTipologiaFornitura").hide();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIAFORNITURA']").hide();
			$("#rowTipologiaLavoro").hide();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIALAVORO']").hide();
		}
	
		if (tipo_contratto=='L') {
			$("#rowTipologiaFornitura").hide();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIAFORNITURA']").hide();
			$("#rowTipologiaLavoro").show();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIALAVORO']").show();
		} 
		
		if (tipo_contratto=='F') {
			$("#rowTipologiaFornitura").show();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIAFORNITURA']").show();
			$("#rowTipologiaLavoro").hide();
			$("[id^='rowW3LOTTTIPI_TIPOLOGIALAVORO']").hide();
		} 
	}
	
	function gestioneSCELTA_CONTRAENTE(scelta_contraente){
		if (scelta_contraente == 4 || scelta_contraente == 10) {
			$("#rowListaCOND").show();
			$("[id^='rowW3LOTT_LISTACOND']").show();
			$("[id^='rowLinkAddW3COND']").show();
		} else {
			for (index = 1; index<= getValue("NUMERO_W3COND"); index++) {
				setValue("W3COND_DEL_W3COND_" + index,"1");
			}
			$("#rowListaCOND").hide();
			$("[id^='rowW3LOTT_LISTACOND']").hide();
			$("[id^='rowLinkAddW3COND']").hide();
			$("[id^='rowW3COND_ID_CONDIZIONE_']").hide();
			$("[id^='rowtitoloW3COND_']").hide();
		}

		var vis = (scelta_contraente != 32);
		if (vis) {
			setValue("W3LOTT_ID_AFF_RISERVATI","");
		}
		showObj("rowW3LOTT_ID_AFF_RISERVATI",!vis);
	}
	
	function gestioneSCELTA_CONTRAENTE2(scelta_contraente){
		if (scelta_contraente == 31) {
			document.forms[0].W3LOTT_MOTIVO_COLLEGAMENTO.value='8';
		}
	}
	
	function gestioneRESET_VALORI_TIPO_CONTRATTO(){
		$('[id^="rowW3LOTTTIPI_TIPOLOGIAFORNITURA"]').val("2");
		$('[id^="rowW3LOTTTIPI_TIPOLOGIALAVORO"]').val("2");
	}
	
	modifySelectOption($("#W3LOTT_ID_SCELTA_CONTRAENTE"));
	modifySelectOption($("#W3LOTT_ID_CATEGORIA_PREVALENTE"));
	
	function modifySelectOption(obj) {
		var obj_opt = obj.find("option");
		$.each(obj_opt, function( key, opt ) {
			var _title = opt.title;
			if (_title.length > 110) {
				 _title = _title.substring(0,110) + "...";
			}
			opt.text = _title;
		});
		obj.css("width","550px");	
	}
	
</gene:javaScript>
