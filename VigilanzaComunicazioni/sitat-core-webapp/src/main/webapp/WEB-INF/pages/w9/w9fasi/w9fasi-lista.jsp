<%
  /*
   * Created on 03/08/2009
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FASI-scheda")}' />
<c:set var="key" value='${key}' scope="request" />
<c:set var="eliminabili" value="false" />
<c:set var="prot" value='${gene:checkProtFunz(pageContext, "INS", "INS")}'/>
<c:set var="createXMLRLO" value='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.W9.XML-RLO")}'/>

<c:set var="attivaNuovo" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.VerificaPermessiFasiFlussiFunction",pageContext,key)}' />
<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.InitListaFlussiLottoFunction" parametro="${key}" />

<c:if test='${!empty sessionScope.AGGIUDICAZIONE_ACC_QUADRO}'>
	<c:remove var="AGGIUDICAZIONE_ACC_QUADRO" scope="session" />
</c:if>

<table class="dettaglio-tab-lista">
<c:choose>
	<c:when test="${attivaNuovo eq false}">
		<tr>
			<td align="center">
				<br>
				Nessuna comunicazione prevista per questa tipologia di lotto
				<br><br>
			</td>
		</tr>
		<tr>
			<td class="comandi-dettaglio" colSpan="2">&nbsp;</td>
		</tr>
	</c:when>
	<c:otherwise>

		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		
		<gene:redefineInsert name="addToAzioni">
			<c:if test='${ riaggiudica eq "si" and gene:checkProt(pageContext,"FUNZ.VIS.ALT.W9.RIAGGIUDICAZIONE")}'>
				<tr>
					<td class="vocemenulaterale" ><a 
					href="javascript:riaggiudica();" tabindex="1504"
					title="Riaggiudica">Riaggiudica</a></td>
				</tr>
			</c:if>
		</gene:redefineInsert>
		<c:if test='${! empty listaAggiudicazioni and fn:length(listaAggiudicazioni) gt 1}' >
			<tr id="rowFiltri">
				<td colspan="2">
					Aggiudicazioni
					<select name="listaAggiudicazioni" id="listaAggiudicazioni" onchange="javascript:cambiaAggiudicazione();" >
						<c:forEach var="itemAggiudicazione" items="${listaAggiudicazioni}" varStatus="index">
							<option value="${itemAggiudicazione[0]}"
							<c:if test="${aggiudicazioneSelezionata eq itemAggiudicazione[0]}">
								selected="selected"
							</c:if>
							>
							<fmt:parseNumber var = "numeroAppa" type = "number" value = "${itemAggiudicazione[0]}" />
							<c:choose>
								<c:when test="${numeroAppa == 1}">
								Aggiudicazione
								</c:when>
								<c:when test="${numeroAppa == 2}">
									Riaggiudicazione
								</c:when>
								<c:otherwise>
									${numeroAppa - 1}^ Riaggiudicazione
								</c:otherwise>
							</c:choose>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<gene:formLista entita="V_W9INVIIFASI" where="V_W9INVIIFASI.CODGARA = #W9LOTT.CODGARA# and V_W9INVIIFASI.CODLOTT=#W9LOTT.CODLOTT# and V_W9INVIIFASI.NUM_APPA=${aggiudicazioneSelezionata}" 
					pagesize="20" tableclass="datilista" sortColumn="3;4" gestisciProtezioni="true"
					gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9FASI" >
					
					<c:set var="key01" value='${gene:getValCampo(key,"CODGARA") }' />
					<c:set var="key02" value='${gene:getValCampo(key,"CODLOTT") }' />
					<c:set var="link" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetRedirectFunction",pageContext,chiaveRigaJava,aggiudicazioneSelezionata)}' />
					
					<c:set var="chiaveFlussiInserimento" value='W9FLUSSI.KEY03=N:${gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE")};W9FLUSSI.KEY04=N:${gene:getValCampo(chiaveRigaJava,"NUM")}'> </c:set>
					<c:set var="tipoInvio" value="1" />
					<c:if test='${mappaFlussiGiaInviati[datiRiga.V_W9INVIIFASI_FASE_ESECUZIONE]}'>
						<c:set var="tipoInvio" value="2" />
					</c:if>
					
					<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
						<c:set var="ctrlProp" scope="request" value='${mappaFlussiGiaInviati[datiRiga.V_W9INVIIFASI_FASE_ESECUZIONE] or mappaFlussiPropedeutici[datiRiga.V_W9INVIIFASI_FASE_ESECUZIONE]}' />
						
						<c:if test='${! gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
							<c:set var="ctrlProp" scope="request" value="${ctrlProp and not (empty requestScope.numeroFasiDelLotto or empty requestScope.numeroFlussiAnagraficaGara)}" />
						</c:if>
							
					<c:if test="${currentRow >= 0}">
						<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
							<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9FASI-scheda")}'>
								<gene:PopUpItemResource
										resource="popupmenu.tags.lista.visualizza"
										title="Visualizza fase/evento"
										href="javascript:creaLink('${link}','${chiaveRigaJava}', '');" />
							</c:if>
							
							<c:set var="resultIsFaseEliminabile" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext, chiaveRiga,"W9FASI")}' />
							<c:set var="cancellaFase" value='${fn:substringBefore(resultIsFaseEliminabile,"-")}' />
							<c:set var="annullaFase"  value='${fn:substringAfter(resultIsFaseEliminabile,"-")}'  />
							
							<c:set var="utenteAbilitato" value="no" />
							<c:choose>
								<c:when test='${userIsRup eq "si" }'>
									<c:set var="utenteAbilitato" value="si" />
								</c:when>
								<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
									<c:set var="utenteAbilitato" value="si" />
								</c:when>
								<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "U"}'>
									<c:set var="utenteAbilitato" value="si" />
								</c:when>
							</c:choose>
              				
							<c:if test='${gene:checkProtFunz(pageContext, "MOD", "MOD") and aggiudicazioneSelezionata eq ultimaAggiudicazione and !isMonitoraggioMultilotto}'>
								<gene:PopUpItemResource
										resource="popupmenu.tags.lista.modifica"
										title="Modifica fase/evento"
										href="javascript:creaLink('${link}','${chiaveRigaJava}', '&modo=MODIFICA')" />
							</c:if>
							<c:set var="isFaseEliminabile" value='${gene:checkProtFunz(pageContext, "DEL","DEL") and cancellaFase eq "si" and (annullaFase eq "no" or empty annullaFase) and aggiudicazioneSelezionata eq ultimaAggiudicazione and !isMonitoraggioMultilotto}' />
							<c:set var="isFaseAnnullabile" value='${gene:checkProtFunz(pageContext, "DEL","DEL") and annullaFase eq "si"  and aggiudicazioneSelezionata eq ultimaAggiudicazione and !isMonitoraggioMultilotto}'/>

							<c:choose>
								<c:when test='${isFaseAnnullabile and (utenteAbilitato eq "si" or (sessionScope.profiloUtente.abilitazioneStd eq "C" and empty datiRiga.V_W9INVIIFASI_IDFLUSSO)) and !empty datiRiga.V_W9INVIIFASI_IDFLUSSO and tipoInvio eq "2" and (empty datiRiga.V_W9INVIIFASI_TINVIO2 or datiRiga.V_W9INVIIFASI_TINVIO2 gt 0 or datiRiga.V_W9INVIIFASI_TINVIO2 eq -2)}'>
									<gene:PopUpItemResource resource="" title="Annulla invio fase/evento" href="javascript:paginaEliminaFase('${chiaveFlussiInserimento}', '${tipoInvio}')"/>
								</c:when>
								<c:when test='${isFaseEliminabile and (utenteAbilitato eq "si" or (sessionScope.profiloUtente.abilitazioneStd eq "C" and empty datiRiga.V_W9INVIIFASI_IDFLUSSO)) and (datiRiga.V_W9INVIIFASI_TINVIO2 lt 0 or datiRiga.V_W9INVIIFASI_TINVIO2 eq "" or empty datiRiga.V_W9INVIIFASI_TINVIO2) and empty situazioneCollaudo}' >
									<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina fase/evento" />
									<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
									<c:set var="eliminabili" value="true" />
								</c:when>
							</c:choose>
						</gene:PopUp>
					</c:if>
	
					</gene:campoLista>
				<%
				  // Campi veri e propri
				%>
				<gene:campoLista campo="FASE_ESECUZIONE" headerClass="sortable" width="440"
					href="javascript:creaLink('${link}','${chiaveRigaJava}', '');" />				

				<gene:campoLista campo="TAB1NORD" visibile="false" />

				<gene:campoLista campo="NUM" headerClass="sortable" width="100" />
				<gene:campoLista campo="IDFLUSSO" visibile="false" headerClass="sortable" width="80" />
				<gene:campoLista campo="TINVIO2" visibile="false" headerClass="sortable" width="80" />
				<gene:campoLista campo="DAEXPORT" visibile="false" />
				<gene:campoLista campo="NUM_ERRORE" visibile="false" headerClass="sortable" width="80" />
				
			<c:if test="${prot and gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9FASI-lista.NuovoInvio')}">
				<gene:campoLista title="Azione" width="60"><center>
							
					<c:choose>
						<c:when test='${utenteAbilitato eq "no"}'>
							<img src="${pageContext.request.contextPath}/img/no_flag.gif" title="Utente senza permesso di effettuare l'invio" />
						</c:when>
						<c:when test='${aggiudicazioneSelezionata ne ultimaAggiudicazione or isMonitoraggioMultilotto}'>
							<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile" />   <!-- busta grigia -->
						</c:when>
						<c:when test='${! empty daExportLotto}' >
							<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile. Inviare prima l'anagrafica di gara" />   <!-- busta grigia -->
						</c:when>
						<c:when test='${empty situazioneCollaudo and !lottoInRichiesteDiCancellazione and ctrlProp and 
							(datiRiga.V_W9INVIIFASI_DAEXPORT eq 1 and (
								(empty datiRiga.V_W9INVIIFASI_IDFLUSSO and empty datiRiga.V_W9INVIIFASI_NUM_ERRORE) or 
									(!empty datiRiga.V_W9INVIIFASI_IDFLUSSO and 
										((datiRiga.V_W9INVIIFASI_NUM_ERRORE eq 0 and datiRiga.V_W9INVIIFASI_TINVIO2 lt 0) or datiRiga.V_W9INVIIFASI_NUM_ERRORE gt 0)
							)))}'> <!-- busta blu -->
							<a href="javascript:paginaSelezioneNuovoFlusso('${chiaveFlussiInserimento}', '${tipoInvio}')"
								title="Invio da effettuare"> <img src="${pageContext.request.contextPath}/img/invioFlussoInAttesa.png" /> </a>
						</c:when>
						<c:when test='${lottoInRichiesteDiCancellazione}'>
							<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile per richieste di cancellazioni pendenti" />   <!-- busta grigia --> 
						</c:when>
						<c:when test='${(empty situazioneCollaudo and ctrlProp eq "false" and tipoInvio ne "2")}'>
							<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile" />   <!-- busta grigia -->
						</c:when>
						<c:when test='${!lottoInRichiesteDiCancellazione and datiRiga.V_W9INVIIFASI_DAEXPORT eq "2" and (!empty datiRiga.V_W9INVIIFASI_IDFLUSSO) and ((datiRiga.V_W9INVIIFASI_NUM_ERRORE eq 0 and datiRiga.V_W9INVIIFASI_TINVIO2 eq 1) or (datiRiga.V_W9INVIIFASI_TINVIO2 eq 2))}'> <!-- busta verde -->
							<c:if test='${!lottoInRichiesteDiCancellazione}'>
								<a href="javascript:paginaSelezioneNuovoFlusso('${chiaveFlussiInserimento}', '${tipoInvio}')"
									title="Invio gi&agrave; effettuato"> <img src="${pageContext.request.contextPath}/img/invioFlusso.png" /> </a>
							</c:if>
							<c:if test='${lottoInRichiesteDiCancellazione}'>
								<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile per richieste di cancellazioni pendenti" />
							</c:if>
						</c:when>
						<c:when test='${!lottoInRichiesteDiCancellazione and datiRiga.V_W9INVIIFASI_DAEXPORT eq "1" and (!empty datiRiga.V_W9INVIIFASI_IDFLUSSO) and datiRiga.V_W9INVIIFASI_NUM_ERRORE eq 0 and datiRiga.V_W9INVIIFASI_TINVIO2 gt 0}'> <!-- busta rossa -->
							<a href="javascript:paginaSelezioneNuovoFlusso('${chiaveFlussiInserimento}', '2')"
								title="Rettifica da inviare"> <img src="${pageContext.request.contextPath}/img/invioRettificaFlusso.png" /> </a>
						</c:when>
						<c:when test='${!(empty situazioneCollaudo) and !lottoInRichiesteDiCancellazione}'>
							<img src="${pageContext.request.contextPath}/img/invioFlussoBN.png"	title="Invio non possibile per monitoraggio terminato sul lotto" />   <!-- busta grigia -->
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
					<c:if test='${createXMLRLO}'>
						<c:if test='${(gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 1 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 2 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 3 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 4 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 5 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 6 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 7 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 8 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 9 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 10 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 987 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 11 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 12 or gene:getValCampo(chiaveRigaJava,"FASE_ESECUZIONE") eq 985) }'>
							<a href="javascript:creaXmlRLO('${chiaveRigaJava}')"
								title="Crea XML Regione Lombardia"> <img src="${pageContext.request.contextPath}/img/exportXML.gif" /> </a>
						</c:if>
					</c:if>
				</center></gene:campoLista>
			</c:if>
			</gene:formLista></td>
		</tr>
		
		<c:choose>
			<c:when test='${(empty situazioneCollaudo) and prot and !lottoInRichiesteDiCancellazione and (abilitaCreaNuovaFase or sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C") and aggiudicazioneSelezionata eq ultimaAggiudicazione and !isMonitoraggioMultilotto}'>
				<gene:redefineInsert name="listaNuovo">
					<td class="vocemenulaterale">
						<a href="javascript:creaNuovo('${key}')" title="Inserisci" tabindex="1501">Nuovo</a>
					</td>
				</gene:redefineInsert>
				
				<gene:redefineInsert name="pulsanteListaInserisci">
					<INPUT type="button" class="bottone-azione"
						title='${gene:resource("label.tags.template.lista.listaPageNuovo")}'
						value="Nuovo" onclick="javascript:creaNuovo('${key}');">
				</gene:redefineInsert>
			</c:when>
			<c:otherwise>
				<gene:redefineInsert name="listaNuovo" />
				
				<gene:redefineInsert name="pulsanteListaInserisci" />
			</c:otherwise>
		</c:choose>

		<c:if test='${eliminabili eq false or aggiudicazioneSelezionata ne ultimaAggiudicazione or isMonitoraggioMultilotto}'>
			<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
			<gene:redefineInsert name="listaEliminaSelezione" />
		</c:if>
		<tr>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
		</tr>

	</c:otherwise>
</c:choose>
</table>

<gene:javaScript>
	function creaLink(pag,keyup,modo) {
		if (pag.indexOf("w9imprese") >= 0) {
			
			var chiave = keyup.split(";");
			
			var codGara = chiave[0].split(":")[1];
			var codLott = chiave[1].split(":")[1];
			
			document.location.href = "${pageContext.request.contextPath}/w9/ListaW9Imprese.do?" + csrfToken + "&metodo=visualizza&codGara=" + codGara + "&codLott=" + codLott;
			// nel passaggio alla liste delle imprese invitate/partecipanti 
			// se pgLastSort e' valorizzato la pagina va in errore
			//document.forms[0].pgLastSort.value = null;
					
		} else {
			// per w9appa aggiungere una variabile che determini il tipo (a04,a05,a21) 
			var keyW9APPA = "";
			if (pag.indexOf("w9appa-scheda-a04") >= 0) {
				pag = pag.replace(/w9appa-scheda-a04/,"w9appa-scheda");
				keyW9APPA = "keyW9APPA=A04&";
			}
			if (pag.indexOf("w9appa-scheda-a05") >= 0) {
				pag = pag.replace(/w9appa-scheda-a05/,"w9appa-scheda");
				keyW9APPA = "keyW9APPA=A05&";
			}
			if (pag.indexOf("w9appa-scheda-a21") >= 0) {
				pag = pag.replace(/w9appa-scheda-a21/,"w9appa-scheda");
				keyW9APPA = "keyW9APPA=A21&";
			}
			var action="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&" + keyW9APPA + "href=w9/" + pag + modo;
			document.forms[0].key.value=pag.substring(pag.indexOf('?chiave=') + 8);
			document.forms[0].action=action;
			document.forms[0].activePage.value="0";
			document.forms[0].submit();
		}
	}
	
	function cambiaAggiudicazione() {
		var aggiudicazioneSelezionata = document.getElementById("listaAggiudicazioni").value;
		document.pagineForm.action += "&aggiudicazioneScelta=" + aggiudicazioneSelezionata;
		selezionaPagina(document.pagineForm.activePage.value);
	}
		
	function creaNuovo(key) {
		document.location.href = '${pageContext.request.contextPath}/w9/SelezionaNuovaFase.do?' + csrfToken + '&key=' + key;
	}

	function paginaSelezioneNuovoFlusso(chiave, tipoInvio) {
		document.forms[0].key.value='W9FLUSSI.AREA=N:1;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY02=N:${key02};'+chiave+';W9FLUSSI.TINVIO2=N:'+tipoInvio;
		document.forms[0].metodo.value="nuovo";
		document.forms[0].activePage.value="0";
		document.forms[0].pathScheda.value="w9/w9flussi/w9flussi-scheda.jsp";
		document.forms[0].submit();
	}
	
	function paginaEliminaFase(chiave, tipoInvio) {
		document.forms[0].key.value='W9FLUSSI.AREA=N:1;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY02=N:${key02};'+chiave+';W9FLUSSI.TINVIO2=N:'+tipoInvio;
		document.forms[0].metodo.value="nuovo";
		document.forms[0].activePage.value="0";
		document.forms[0].pathScheda.value="w9/w9fasi/w9fasi-elimina-scheda.jsp";
		document.forms[0].submit();
	}
	
	function creaXmlSitar(key) {
		var comando = "href=w9/commons/popup-validazione-xml-sitar.jsp?key=" + key;
		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}
	
	function creaXmlRLO(key) {
		document.location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/commons/create-import-xmlrlo.jsp?key=' + key
	}

	function riaggiudica() {
		var comando = "href=w9/w9fasi/riaggiudica-popup.jsp?progressivoRiaggiudicazione=${ultimaAggiudicazione + 1}&keyatt=${key}";
		openPopUpCustom(comando, "riaggiudica", 500, 650, "yes", "yes");
	}
	
	
<c:if test='${! empty daExportLotto}'>
	$(document).ready(function() {
		outMsg("Prima di inviare una fase è necessario inviare l'anagrafica di gara","ERR");
		onOffMsg();
	});
</c:if>
	
</gene:javaScript>