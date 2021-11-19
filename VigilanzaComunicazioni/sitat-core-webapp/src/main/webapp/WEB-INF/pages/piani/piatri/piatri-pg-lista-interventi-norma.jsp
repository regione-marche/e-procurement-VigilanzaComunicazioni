<%
  /*
   * Created on 06-nov-2017
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

<c:set var="contri" value='${gene:getValCampo(key, "PIATRI.CONTRI")}' />
<c:set var="presenzaPiantiTriennaliPrecedenti" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaPresenzaPianiTriennaliPrecedentiFunction", pageContext, key)}' scope="request" />
<c:set var="updateLista" value='${param.updateLista}' />

			<c:set var="moduloFabbisogniAttivo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.pt.moduloFabbisogniAttivo")}' />

<c:set var="sequenzaInterventiOk" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaSequenzaInterventiFunction", pageContext, key)}' scope="request" />

<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}' />
	
<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>
<c:set var="attivaIncolla" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.AttivaIncollaFunction",pageContext,key)}' />
<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}' />
<c:set var="prot" value='${gene:checkProtFunz(pageContext, "INS", "INS")}' />

<c:choose>
	<c:when test="${prot}">
		<gene:redefineInsert name="addToAzioni">
			<c:if test='${modificabile eq "si"}' >
				<tr>
					<td class="vocemenulaterale" ><a 
				 	href="javascript:cambiaRUPInterventi();" tabindex="1504"
				 	title="Cambia RUP">Cambia RUP</a></td>
				</tr>
			</c:if>
			<c:if test="${(modificabile eq 'si' && moduloFabbisogniAttivo eq '1') }" >
				<tr id="rowGestisciProposte">
					<td class="vocemenulaterale" >
						<a href="javascript:visualizzaGestisciProposte();" title="Gestisci proposte" tabindex="1505">
							Gestisci proposte &nbsp;&nbsp;&nbsp;&nbsp;<img id="imgRight" src="${pageContext.request.contextPath}/img/arrow-right.gif" ><img id="imgDown" src="${pageContext.request.contextPath}/img/arrow-down.gif" >
						</a>
					</td>
				</tr>
			</c:if>
			<tr id="row_ImportaFabbisogni">
				<td class="vocemenulaterale" style="text-align: right;"><a 
			 	href="javascript:importaFabbisogni();" tabindex="1506"
			 	title="Importa proposte di ${interventoVSacquisto}">Importa proposte di ${interventoVSacquisto}</a></td> <!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->  <!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
			</tr>
			<c:if test="${presenzaPiantiTriennaliPrecedenti eq 'TRUE' and updateLista ne 1 and modificabile eq 'si' }" >
				<tr id="row_RichiediRevisioneInterventiAnnoPrecedente">
					<td class="vocemenulaterale" style="text-align: right;"><a 
					 href="javascript:apriListaRichiediRevisioneInterventiAnnoPrecedente();" tabindex="1507"
					 title="Richiedi revisione per interventi di anno precedente">Richiedi revisione per interventi di anno precedente</a></td>
				</tr>
				<tr>
					<td class="vocemenulaterale" ><a 
					 href="javascript:apriListaInterventiDaProgrammaPrecedente();" tabindex="1508"
					 title="Riporta interventi da programma precedente">Riporta da programma precedente</a></td>
				</tr>
			</c:if>
			<c:if test='${modificabile eq "si" && moduloFabbisogniAttivo eq "1"}' >
				<tr>
					<td class="vocemenulaterale" ><a 
					href="javascript:approvaFabbisogniListaSelezionati('RDP_RimuoviRichiediRevisione');" tabindex="1509"
					title="Rimuovi e richiedi revisione selezionati">Rimuovi e richiedi revisione selezionati</a></td>
				</tr>
			</c:if>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:impostaFiltro();" title='Imposta filtro' tabindex="1510">
						Imposta filtro
					</a>
				</td>
			</tr>
		</gene:redefineInsert>
		<gene:redefineInsert name="listaNuovo">
			<td class="vocemenulaterale"><a href="javascript:listaNuovo();"
				title="Inserisci" tabindex="1501">Nuovo</a></td>
			<gene:set name="titoloMenu">
				<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
			</gene:set>
		</gene:redefineInsert>
		<gene:redefineInsert name="addToDocumenti">
			<jsp:include page="../../w9/commons/addtodocumenti-validazione.jsp" />
			<c:if test="${requestScope.esisteViewReportSoggAggr and requestScope.tiprog eq 2}">
				<tr>
					<td class="vocemenulaterale" >
					<a href="javascript:confermaFileSoggettoAggregatore();" tabindex="1506"
					 title="Report acquisti per soggetti aggregatori">Report acquisti per soggetti aggregatori</a></td>
				</tr>
			</c:if>
		</gene:redefineInsert>
	</c:when>
	<c:otherwise>
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="pulsanteListaInserisci" />
	</c:otherwise>
</c:choose>
<c:if test='${modificabile eq "no"}' >
	<gene:redefineInsert name="listaNuovo"/>
	<gene:redefineInsert name="pulsanteListaInserisci"/>
	<gene:redefineInsert name="listaEliminaSelezione" />
	<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
</c:if>
<%
  // Creo la lista per INTTRI
%>
<table class="dettaglio-tab-lista">
<c:if test="${sessionScope.filtroINTTRI eq 1}" >
	<tr>
		<td style="font: 11px Verdana, Arial, Helvetica, sans-serif;">
			 <br><img src="${pageContext.request.contextPath}/img/filtro.gif" alt="Filtro">&nbsp;
			 <span style="color: #ff0028; font-weight: bold;">Lista filtrata</span>
			 &nbsp;&nbsp;&nbsp;[ 
			 <a href="javascript:annullaFiltro();"><img src="${pageContext.request.contextPath}/img/cancellaFiltro.gif" alt="Cancella filtro"></a>
			 <a class="link-generico" href="javascript:annullaFiltro();">Cancella filtro</a> ]
		</td>
	</tr>
</c:if>
  <tr>
	<td>
	  <gene:formLista entita="INTTRI" pagesize="20" sortColumn="3;4"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINTTRI" gestisciProtezioni="true"
			where='INTTRI.CONTRI = #PIATRI.CONTRI# ${filtroInterventi}' tableclass="datilista" >

		<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
		  <c:if test="${currentRow >= 0}">
				<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
							onClick="chiaveRiga='${chiaveRigaJava}'">
					<c:if test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}'>
						<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
					</c:if>
					<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && modificabile eq "si"}'>
						<c:if test="${!(not empty datiRiga.PTAPPROVAZIONI_CONINT && datiRiga.FABBISOGNI_STATO ne 22)}">
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />	
						</c:if>	
					</c:if>
					<c:if test='${modificabile eq "si" && moduloFabbisogniAttivo eq "1"}'>
						<c:if test="${not empty datiRiga.PTAPPROVAZIONI_CONINT && datiRiga.FABBISOGNI_STATO ne 22}">
							<gene:PopUpItem title="Rimuovi e richiedi revisione" href="javascript:approvaFabbisognoListaPopUpItem('${datiRiga.PTAPPROVAZIONI_CONINT}','RDP_RimuoviRichiediRevisione','${datiRiga.INTTRI_CONINT}');" />
						</c:if>
					</c:if>
					<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
					<input type="hidden" name="datiRigaPtapprConint" value="${datiRiga.PTAPPROVAZIONI_CONINT}" />
					<input type="hidden" name="datiRigaInttriConint" value="${datiRiga.INTTRI_CONINT}" />
					<input type="hidden" name="datiRigaStato" value="${datiRiga.FABBISOGNI_STATO}" />
				</gene:PopUp>
			</c:if>
		</gene:campoLista>
		<gene:campoLista title="" width="10">
			<c:if test='${not empty datiRiga.PTAPPROVAZIONI_CONINT}'>
				<c:choose>
					<c:when test='${datiRiga.FABBISOGNI_STATO eq 22}' >
						<img src="${pageContext.request.contextPath}/img/interventiCollegatiRichiestaInterventoAcquistoAvviata.png"	title="Interventi collegati ad una richiesta di intervento/acquisto Avviata" alt="Interventi collegati ad una richiesta di intervento/acquisto Avviata" />
					</c:when>
					<c:otherwise>
						<img src="${pageContext.request.contextPath}/img/interventiCollegatiRichiestaInterventoAcquisto.png"	title="Interventi collegati ad una richiesta di intervento/acquisto" alt="Interventi collegati ad una richiesta di intervento/acquisto" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</gene:campoLista>
		<c:set var="link"
				value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="ANNRIF" title="Annualit&agrave;" width="70" ordinabile="true"/>
			<c:if test='${true or (sequenzaInterventiOk eq "TRUE")}'>
				<c:choose>
					<c:when test='${true or (updateLista eq "1")}' >
						<gene:campoLista campo="NPROGINT" title="Nr." width="30" href="${gene:if(visualizzaLink, link, '')}" ordinabile="true" />
					</c:when>
					<c:otherwise>
						<gene:campoLista campo="NPROGINT" title="Nr." width="50" href="${gene:if(visualizzaLink, link, '')}" ordinabile="true" >
							<img class="up" src="${pageContext.request.contextPath}/img/${applicationScope.pathImg}scrollup.png" alt="Sposta su" align="right"/>
							<img class="down" src="${pageContext.request.contextPath}/img/${applicationScope.pathImg}scrolldown.png" alt="Sposta giu" align="right"/>						
						</gene:campoLista>
					</c:otherwise>
				</c:choose>
				<gene:campoLista campo="CONTRI" visibile="false"/>
				<gene:campoLista campo="CONINT" visibile="false"/>
			</c:if>
			<c:if test='${false and sequenzaInterventiOk eq "FALSE"}'>
				<gene:campoLista campo="NPROGINT" visibile="false"/>
				<gene:campoLista campo="CONINT" title="Nr." width="30" href="${gene:if(visualizzaLink, link, '')}" ordinabile="false"/>
			</c:if>
			<gene:campoLista campo="CUIINT" width="70" href="${gene:if(visualizzaLink, link, '')}" ordinabile="true"/>
			<gene:campoLista campo="DESINT" width="390" ordinabile="true" href="${gene:if(visualizzaLink, link, '')}" />
			<gene:campoLista campo="TOTINT" width="120" ordinabile="true"/>
			<gene:campoLista campo="ANNINT" width="70" ordinabile="false" visibile="${datiRiga.PIATRI_CONTRI eq '1'}" />
			<gene:campoLista campo="TIPROG" entita="PIATRI" where="PIATRI.CONTRI=INTTRI.CONTRI" visibile="false" />
			<gene:campoLista campo="CONINT" entita="PTAPPROVAZIONI" where="PTAPPROVAZIONI.ID_PROGRAMMA=INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA=INTTRI.CONINT"  ordinabile="false" visibile="false"/>
			<gene:campoLista campo="STATO" entita="FABBISOGNI" from="PTAPPROVAZIONI" where="PTAPPROVAZIONI.ID_PROGRAMMA=INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA=INTTRI.CONINT and PTAPPROVAZIONI.CONINT = FABBISOGNI.CONINT"  ordinabile="false" visibile="false"/>
		</gene:formLista></td>
		
		<form name="soggettoAggregatore" action="${pageContext.request.contextPath}/piani/GetReportAcquistiSoggettoAggregatore.do" method="post" >
			<input type="hidden" name="codiceProgramma" value="${key}" />
		</form>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>

<gene:javaScript>
	$(document).ready(function() {
		$('#row_ImportaFabbisogni').hide();
		$('#row_RichiediRevisioneInterventiAnnoPrecedente').hide();
		$('#imgDown').hide();			
	});

	function visualizzaGestisciProposte() {
		if( isObjShow("row_ImportaFabbisogni") ) {
			showObj("row_ImportaFabbisogni", false);
			showObj("row_RichiediRevisioneInterventiAnnoPrecedente", false);
			showObj("imgDown", false);
			showObj("imgRight", true);			
		} else {
			showObj("row_ImportaFabbisogni", true);	
			showObj("row_RichiediRevisioneInterventiAnnoPrecedente", true);
			showObj("imgDown", true);
			showObj("imgRight", false);			
		}
	}	
	
	$(".up,.down").on("mouseover",function() {
    	$(this).css('cursor', 'pointer');
	});
	
	hideAnnualita();
	showAnnualitaTop();
	showAnnualitaBottom();
	
	function hideAnnualita() {
		$("tr").find('td:eq(1):contains(1)').parent(":first").find("img[class='up']").css("visibility","hidden");
		$("tr").find('td:eq(1):contains(1)').parent(":last").find("img[class='down']").css("visibility","hidden");
		$("tr").find('td:eq(1):contains(2)').parent(":first").find("img[class='up']").css("visibility","hidden");
		$("tr").find('td:eq(1):contains(2)').parent(":last").find("img[class='down']").css("visibility","hidden");
		$("tr").find('td:eq(1):contains(3)').parent(":first").find("img[class='up']").css("visibility","hidden");
		$("tr").find('td:eq(1):contains(3)').parent(":last").find("img[class='down']").css("visibility","hidden");
	}
	
	function showAnnualitaTop() {
		if (document.forms[0].pgCorrente.value != 0) {
			var nprogint = (document.forms[0].risultatiPerPagina.value * (document.forms[0].pgCorrente.value)) + 1;
	 
			$.ajax({ url : '${pageContext.request.contextPath}/piani/VerificaAnnualitaRiferimento.do', 
	  		data : 'nprogint=' + nprogint + '&codice=${key}&action=prev',
	  		success: function(response){
	  			if(response == 'equal') {
	  				$("table.dettaglio-tab-lista tr.even:first-child").find("img[class='up']").css("visibility","visible");
	  			}
	  		}
			});
		}
	}
	
	function showAnnualitaBottom() {
		if (${datiRiga.row} != ${datiRiga.rowCount}) {
			var nprogint = ${datiRiga.row};
	 
			$.ajax({ url : '${pageContext.request.contextPath}/piani/VerificaAnnualitaRiferimento.do', 
	  		data : 'nprogint=' + nprogint + '&codice=${key}&action=next',
	  		success: function(response){
	  			if(response == 'equal') {
	  				$("table.dettaglio-tab-lista tr:last-child").find("img[class='down']").css("visibility","visible");
	  			}
	  		}
			});
		}
	}

	function apriListaInterventiDaProgrammaPrecedente() {
		var action = "${pageContext.request.contextPath}/piani/GetListaInterventiDaProgrammaPrecedente.do";
		var parametri = new String('codiceProgramma=${fn:substringAfter(key, ":")}');
		openPopUpActionCustom(action, parametri, 'listaInterventiDaProgrammaPrecedente',1000,800,"yes","yes");
	}
	
	function apriListaRichiediRevisioneInterventiAnnoPrecedente() {
		var action = "${pageContext.request.contextPath}/piani/GetListaInterventiDaProgrammaPrecedente.do";
		var parametri = new String('codiceProgramma=${fn:substringAfter(key, ":")}&tipo=RDP_RevisioneInterventiAnnoPrecedente');
		openPopUpActionCustom(action, parametri, 'listaInterventiDaProgrammaPrecedente',1000,800,"yes","yes");
	}
	
	function cambiaRUPInterventi() {
		var valoriDaCopiare = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
			return;
		 } else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
		 	valoriDaCopiare = document.forms[0].keys.value;
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriDaCopiare += document.forms[0].keys[i].value + ";;";
				}
			}
			valoriDaCopiare = valoriDaCopiare.substring(0,(valoriDaCopiare.length - 2));
		}
		if(confirm('La funzione cambierà il RUP in tutti gli interventi selezionati. Continuare?')) {
			openPopUpCustom("href=piani/piatri/popup-cambia-RUP.jsp?interventi=" + valoriDaCopiare, "cambiaRUP", 650, 400, 1, 1);
		}
	}
	
	function checkDown(nprogint) {
		if ((nprogint % document.forms[0].risultatiPerPagina.value) == 0 && nprogint != ${datiRiga.rowCount}) {
			listaVaiAPagina(document.forms[0].pgCorrente.value + 1);
		}
	}

	function checkUp(nprogint) {
		if (document.forms[0].pgCorrente.value != 0 && nprogint == (document.forms[0].risultatiPerPagina.value * document.forms[0].pgCorrente.value) + 1) {
			listaVaiAPagina(document.forms[0].pgCorrente.value - 1);
		}
	}
	
	$('.up,.down').on("click",function () {
     var row = $(this).parents('tr:first');
     var nprogint = row.children('td:nth-child(3)').text();
     var conint = row.children("td:nth-child(1)").find(":checkbox[name='keys']").attr("value");
     if ($(this).is('.up')) {
     	var action = "up";
     } else {
     	var action = "down";
     }
		$.ajax({ 
  		url : '${pageContext.request.contextPath}/piani/AggiornaSequenzaInterventi.do', 
  		data : 'nprogint=' + nprogint + '&conint=' + conint + '&action=' + action,
  		success: function(response){
	   		if (response == 'success') {
	   			if (action == 'up') {
	   				checkUp(nprogint);
	   				var tipo = row.prev().attr("class");
	           row.insertBefore(row.prev());
	           if (tipo == 'odd') {
							row.attr("class","odd");
							row.next().attr("class","even");
						} else {
							row.attr("class","even");
							row.next().attr("class","odd");
						}
						var newnprogint = row.next().children('td:nth-child(3)').text();
						row.next().children('td:nth-child(3)').find('a').text(nprogint);
						row.next().find("img[class='up']").css("visibility","visible");
	     		} else {
	     			checkDown(nprogint);
	     			var tipo = row.next().attr("class");
	           	row.insertAfter(row.next());
	           	if (tipo == 'odd') {
							row.attr("class","odd");
							row.prev().attr("class","even");
						} else {
							row.attr("class","even");
							row.prev().attr("class","odd");
						}
						var newnprogint = row.prev().children('td:nth-child(3)').text();
						row.prev().children('td:nth-child(3)').find('a').text(nprogint);
						row.prev().find("img[class='down']").css("visibility","visible");
	     		}
	     		row.children('td:nth-child(3)').find('a').text(newnprogint);
	     		row.find("img[class='up']").css("visibility","visible");
					row.find("img[class='down']").css("visibility","visible");
					hideAnnualita();
					showAnnualitaTop();
					showAnnualitaBottom();
				}
	  	}
		});
	});
	
	function scaricaFileSoggettoAggregatore() {
		document.soggettoAggregatore.submit();
	}
	
	function confermaFileSoggettoAggregatore() {
		openPopUp("href=piani/piatri/piatri-popup-confermaSoggettiAggregatori.jsp", "confermaReportSoggettoAggregatore", 600, 500, "no","no");
	}
	
	function importaFabbisogni() {
		var action = "${pageContext.request.contextPath}/piani/GetListaFabbisogni.do";
		var parametri = new String('contri=${contri}');
		
		openPopUpActionCustom(action, parametri, 'listaFabbisogni',1000,500,"yes","yes");
		
	}
	
	
	function approvaFabbisogniListaSelezionati(funzionalita) {
 		if(!listaSelezionatiProcedere(funzionalita)){
			return;
		}
		//////////////////
		var valoriFabbisogni = "";
		var valoriConintPT = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
			return;
		} else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
            valoriFabbisogni = document.forms[0].datiRigaPtapprConint.value;
			valoriConintPT = document.forms[0].datiRigaInttriConint.value;
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriFabbisogni += document.forms[0].datiRigaPtapprConint[i].value + ";;";
					valoriConintPT += document.forms[0].datiRigaInttriConint[i].value + ";;";
				}
			}
			valoriFabbisogni = valoriFabbisogni.substring(0,(valoriFabbisogni.length - 2));
			valoriConintPT = valoriConintPT.substring(0,(valoriConintPT.length - 2));
		}
		/////////////////
		location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&fabbisogni="+valoriFabbisogni+"&funzionalita="+funzionalita+"&contri_PT=${contri}&conint_PT="+valoriConintPT;
	}
	
	function approvaFabbisognoListaPopUpItem(chiave,funzionalita,valoriConintPT) {
		location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&fabbisogni="+chiave+"&funzionalita="+funzionalita+"&contri_PT=${contri}&conint_PT="+valoriConintPT;	
	}
	
	
	function listaSelezionatiProcedere(funzionalita){
		var valoreStato = 0;
		var domanda = true;
		var statiOK = "";
		switch(funzionalita) {
				  case "Elimina":
				    statiOK = ";;22;" ;
				    break;
				  case "RDP_RimuoviRichiediRevisione":
				    statiOK = ";1;2;3;4;11;12;21;31;32;";
				    break;	
		}
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
			return false;
		} else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
			valoreStato = document.forms[0].datiRigaStato.value;
			valoreStatoStr = valoreStato.toString();
			if(statiOK.indexOf(";"+valoreStatoStr+";") < 0){
				alert('Attenzione: funzionalità non attivabile sulla proposta!');
				//document.forms[0].keys.checked = false;
				return false;
			}
		}
		for (var i = 0; i < document.forms[0].keys.length; i++) {
			if (document.forms[0].keys[i].checked) {
				valoreStato = document.forms[0].datiRigaStato[i].value;
				valoreStatoStr = valoreStato.toString();
				if(statiOK.indexOf(";"+valoreStatoStr+";") < 0){
					if(domanda == true){
						if (confirm("Attenzione: la funzionalità avrà effetto solo sulle richieste per le quali è attuabile. Procedere comunque?")) {
								document.forms[0].keys[i].checked = false;
								domanda = false;
						}else{
							return false;
						}
					}else{
						document.forms[0].keys[i].checked = false;
					}
				}						
			}
		}
		return true;
	}
	
	//////////////////////////////////////////////////////////////
	//ridefinita funzione ancestor listaEliminaSelezione():
	var listaEliminaSelezioneDefault = listaEliminaSelezione;	
	// Elimina selezione
	function listaEliminaSelezioneCustom() {
		////////////////////////////////
		if(!listaSelezionatiProcedere("Elimina")){
			return;
		}
		///////////////////////////////
		listaEliminaSelezioneDefault();
	}	
	var listaEliminaSelezione = listaEliminaSelezioneCustom;
	//////////////////////////////////////////////////////////////

	function impostaFiltro() {
		var comando = "href=piani/piatri/popup-filtroInterventi-norma.jsp";
		var risultatiPerPagina = document.forms[0].risultatiPerPagina.value;
		openPopUpCustom(comando, "impostaFiltro", 850, 500, "yes", "yes");
	}
	
	function annullaFiltro() {
		bloccaRichiesteServer();
		var comando = "href=piani/piatri/popup-filtroInterventi-norma.jsp&annulla=1";
		openPopUpCustom(comando, "impostaFiltro", 10, 10, "no", "no");
	}
</gene:javaScript>

