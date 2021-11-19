
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

<c:set var="sequenzaInterventiOk" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaSequenzaInterventiFunction", pageContext, key)}' scope="request" />

<c:set var="visualizzaLink"
	value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}' />

<gene:set name="tipint"	value="1" scope="sessionScope"/>
	
<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>
<c:set var="attivaIncolla"
	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.AttivaIncollaFunction",pageContext,key)}' />
<c:set var="visualizzaLink"
	value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}' />
<c:set var="prot" value='${gene:checkProtFunz(pageContext, "INS", "INS")}' />
<c:choose>
	<c:when test="${prot}">
		<gene:redefineInsert name="addToAzioni">
			<tr>
				<td class="vocemenulaterale" ><a 
				 href="javascript:copiaMultiIntervento();" tabindex="1504"
				 title="Copia selezionati">Copia selezionati</a></td>
			</tr>
			<c:if test='${attivaIncolla eq "attivo" }'>
				<tr>
					<td class="vocemenulaterale"><a
						href="javascript:incollaIntervento('${key}');" tabindex="1505"
						title="Incolla">Incolla</a></td>
				</tr>
			</c:if>
			<tr>
				<td class="vocemenulaterale" ><a 
				 href="javascript:cambiaRUPInterventi();" tabindex="1504"
				 title="Cambia RUP">Cambia RUP</a></td>
			</tr>			
		</gene:redefineInsert>
		<gene:redefineInsert name="listaNuovo">
			<td class="vocemenulaterale"><a href="javascript:listaNuovo();"
				title="Inserisci" tabindex="1501"> Nuovo </a></td>
			<gene:set name="titoloMenu">
				<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
			</gene:set>
		</gene:redefineInsert>
	</c:when>
	<c:otherwise>
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="pulsanteListaInserisci" />
	</c:otherwise>
</c:choose>
<%
  // Creo la lista per INTTRI
%>
<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="INTTRI" pagesize="20" sortColumn="2;3"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINTTRI"
			tableclass="datilista" gestisciProtezioni="true"
			where='INTTRI.CONTRI = #PIATRI.CONTRI# AND (INTTRI.TIPINT IS NULL OR INTTRI.TIPINT = 1) '>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>"
				width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
						onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if
							test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}'>
							<gene:PopUpItemResource
								resource="popupmenu.tags.lista.visualizza"
								title="Visualizza dettaglio" />
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
								title="Elimina" />
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "INS","INS")}'>
							<gene:PopUpItem title="Copia"
								href="javascript:copiaIntervento('${chiaveRiga}');" />
						</c:if>
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<c:set var="link"
				value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="ANNRIF" title="Annualit&agrave;" width="70" ordinabile="false"/>
			<c:if test='${sequenzaInterventiOk eq "TRUE"}'>
				<c:choose>
					<c:when test='${updateLista eq "1"}' >
						<gene:campoLista campo="NPROGINT" title="Nr." width="90" href="${gene:if(visualizzaLink, link, '')}" ordinabile="false" />
					</c:when>
					<c:otherwise>
						<gene:campoLista campo="NPROGINT" title="Nr." width="90" href="${gene:if(visualizzaLink, link, '')}" ordinabile="false" >
							<img class="up" src="${pageContext.request.contextPath}/img/${applicationScope.pathImg}scrollup.png" alt="Sposta su" align="right"/>
							<img class="down" src="${pageContext.request.contextPath}/img/${applicationScope.pathImg}scrolldown.png" alt="Sposta giu" align="right"/>						
						</gene:campoLista>
					</c:otherwise>
				</c:choose> 
				<gene:campoLista campo="CONINT" visibile="false"/>
			</c:if>
			<c:if test='${sequenzaInterventiOk eq "FALSE"}'>
				<gene:campoLista campo="NPROGINT" visibile="false"/>
				<gene:campoLista campo="CONINT" title="Nr." width="50" href="${gene:if(visualizzaLink, link, '')}" ordinabile="false"/>
			</c:if>
			<gene:campoLista campo="CODINT" width="70" ordinabile="false"/>
			<gene:campoLista campo="DESINT" width="390" ordinabile="false"/>
			<gene:campoLista campo="TOTINT" width="120" ordinabile="false"/>
			<gene:campoLista campo="ANNINT" width="70" ordinabile="false"/>
		</gene:formLista></td>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>

<gene:redefineInsert name="head">
	<!--   <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.7.2.min.js"></script> -->
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-ui-1.8.21.custom.min.js"></script> -->
</gene:redefineInsert>

<gene:javaScript>
	
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
	
	function copiaIntervento(idIntervento) {
		var actionCopia = "${pageContext.request.contextPath}/piani/CopiaIntervento.do";
		var par = new String('chiave=' + idIntervento);
		openPopUpActionCustom(actionCopia, par, 'CopiaIntervento', 1, 1, "no", "no");
	}
	
	function copiaMultiIntervento() {
		var valoriDaCopiare = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
		 } else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
            copiaIntervento(document.forms[0].keys.value); 
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriDaCopiare += document.forms[0].keys[i].value + ";;";
				}
			}
			valoriDaCopiare = valoriDaCopiare.substring(0,(valoriDaCopiare.length - 2));
			copiaIntervento(valoriDaCopiare);
		}
	}

	function incollaIntervento(idContri) {
		if(confirm('Incollare l\'intervento(gli interventi) copiato(copiati) dal programma ' + '${idProgramma}' + '?')) {
			var actionIncolla = "${pageContext.request.contextPath}/piani/IncollaIntervento.do";
			var par = new String('keyatt=' + idContri);
			openPopUpActionCustom(actionIncolla, par, 'IncollaIntervento', 1, 1, "no", "no");
		}
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
	  $.ajax({ url : '${pageContext.request.contextPath}/piani/AggiornaSequenzaInterventi.do', 
	  data : 'nprogint=' + nprogint + '&conint=' + conint + '&action=' + action,
	  success: function(response){ 
	   if(response == 'success') {
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

</gene:javaScript>

