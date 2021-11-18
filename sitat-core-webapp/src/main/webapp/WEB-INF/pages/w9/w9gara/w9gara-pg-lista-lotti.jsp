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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="eliminabili" value="false" />
<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9LOTT-scheda")}' />
<c:set var="numLottiGara" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetNumeroLottiGaraFunction", pageContext, key1)}' />
<c:set var="color" value="blue"/>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="azioniComuni-schedaGara.jsp" />
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-scheda.PopolamentoMassivoLotti")}'>
			<tr>
				<td class="vocemenulaterale" ><a 
					href="javascript:popolamentoMassivoSchede();" tabindex="1504"
					title="Popolamento massivo schede">Popolamento massivo schede</a></td>
			</tr>
		</c:if>
	</gene:redefineInsert>

<gene:redefineInsert name="listaNuovo">
	<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
		<td class="vocemenulaterale"><a href="javascript:listaNuovo();" title="Inserisci" tabindex="1501">Nuovo</a></td>
	</c:if>
</gene:redefineInsert>

<table class="dettaglio-tab-lista">
<c:if test="${numLottiGara > 20}">
	<tr id="rowFiltroLotti" >
		<td>
			<br>CIG o Oggetto del lotto:&nbsp;&nbsp;
			<input type="text" id="filtroLotti" name="filtroLotti" size="25" value="${param.cigOggetto}" />&nbsp;&nbsp;<input type="button" id="btnFiltroLotti" value="Filtra lotti" title="Filtra lotti" onclick="javascript:filtraLotti();" />
			<br><br>
			<c:if test="${not empty param.cigOggetto}" >
				<br><b>Lista lotti filtrata</b>
			</c:if>
		</td>
	</tr>
</c:if>

<c:choose>
	<c:when test='${sessionScope.profiloUtente.abilitazioneStd ne "A" and sessionScope.profiloUtente.abilitazioneStd ne "C"}'>
		<c:set var="where1" value=" W9LOTT.CODGARA = #W9GARA.CODGARA# AND W9LOTT.CODLOTT in (select r.CODLOTT from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ${sessionScope.profiloUtente.id} )" />
	</c:when>
	<c:otherwise>
		<c:set var="where1" value=" W9LOTT.CODGARA = #W9GARA.CODGARA#" />
	</c:otherwise>
</c:choose>
	
	<tr>
		<td>
			<gene:formLista entita="W9LOTT" pagesize="20"	tableclass="datilista" sortColumn="7;3" gestisciProtezioni="true"
					gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9LOTT" where="${where1}" >
			
				<input type="hidden" name="cigOggetto" id="cigOggetto" value="${param.cigOggetto}" />
			
				<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9LOTT-scheda")}'>
							<gene:PopUpItemResource
									resource="popupmenu.tags.lista.visualizza"
									title="Visualizza anagrafica lotto" />
						</c:if>
						
						<c:if test='${gene:checkProtFunz(pageContext, "MOD", "MOD") && modifica}'>
							<gene:PopUpItemResource	resource="popupmenu.tags.lista.modifica" title="Modifica anagrafica lotto" />
						</c:if>
						<c:set var="cancella" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,chiaveRiga,"W9LOTT")}' />
						<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && (cancella eq "si") && (userIsRup eq "si" || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq "A" || sessionScope.profiloUtente.abilitazioneStd eq "C")}'>
							<gene:PopUpItemResource 
									resource="popupmenu.tags.lista.elimina"
									title="Elimina anagrafica lotto" />
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
							<c:set var="eliminabili" value="true" />
						</c:if>
						<c:set var="param1" value="${chiaveRigaJava}" />
						<c:set var="isLottoInviabileAppaltiLiguria" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.IsLottoInviabileAppaltiLiguriaFunction",pageContext,param1)}' />
						
					</gene:PopUp>
				</c:if>
				</gene:campoLista>

				<% // Campi veri e propri	%>
				<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
				<gene:campoLista campo="CODLOTT" visibile="false"/>
				<gene:campoLista campo="NLOTTO" headerClass="sortable" width="70">
					<c:if test='${!empty datiRiga.W9LOTT_CIG_MASTER_ML}'>
						<c:if test='${!empty cigmaster}'>
							<c:if test='${cigmaster ne datiRiga.W9LOTT_CIG_MASTER_ML}'>
								<c:if test='${color eq "red"}'>
									<c:set var="color" value="blue" />
								</c:if>
								<c:if test='${color eq "blue"}'>
									<c:set var="color" value="red" />
								</c:if>
							</c:if>
						</c:if>
						<label>&nbsp;</label><label class="lottoAccorpato" style="background-color:${color}"><b>&nbsp;&nbsp;</b></label>
						<c:set var="cigmaster" value="${datiRiga.W9LOTT_CIG_MASTER_ML}" />
					</c:if>
				</gene:campoLista>
				<gene:campoLista campo="CIG" headerClass="sortable" width="95">
					<c:if test='${!empty datiRiga.W9LOTT_CIG_MASTER_ML && datiRiga.W9LOTT_CIG_MASTER_ML eq datiRiga.W9LOTT_CIG}'>
						<label>&nbsp;</label><label class="lottoCigMaster" style="background-color:${color}"><b>&nbsp;&nbsp;</b></label>
					</c:if>
				</gene:campoLista>
				<gene:campoLista campo="OGGETTO" visibile="true" headerClass="sortable"
						href="${gene:if(visualizzaLink, link, '')}" width="350" />
				<gene:campoLista campo="SITUAZIONE" />
				<gene:campoLista campo="CIG_MASTER_ML" visibile="false"/>
				<gene:campoLista campo="TIPO_CONTRATTO" headerClass="sortable" width="100" />
				<gene:campoLista campo="IMPORTO_LOTTO" headerClass="sortable"	width="150" />
				<c:set var="importoLotto" value="${datiRiga.W9LOTT_IMPORTO_LOTTO}" scope="request" />
				<fmt:parseNumber var="locImportoLotto" value="${importoLotto}" type="number" pattern="##.##" scope="request" />
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.W9.XML-REGLIGURIA")}' >
					<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or userIsRup eq "si"}'>
						<gene:campoLista title="&nbsp;" width="50" >
							<center>
								<c:choose>
									<c:when test='${isLottoInviabileAppaltiLiguria eq true}'>
										<a href="javascript:inviaDatiLotto('${chiaveRigaJava}')"
											title="Trasmetti a Appalti Liguria"> <img src="${pageContext.request.contextPath}/img/invioFlusso.png" />
										</a>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</center>
						</gene:campoLista>
					</c:if>
				</c:if>
				<gene:campoLista campo="ART_E1" visibile="false"/>
			</gene:formLista>
			
			<form name="formApriPagina" action="${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do" method="post" >
				<input type="hidden" name="metodo" value="inizio" />
				<input type="hidden" name="codiceGara" id="codiceGara" />
				<input type="hidden" name="codiceLotto" id="codiceLotto" />
			</form>
		</td>
	</tr>
	<c:if test='${eliminabili eq false}'>
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="listaEliminaSelezione" />
	</c:if>
	
	<c:if test='${not modifica}'>
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<gene:redefineInsert name="listaNuovo" />
	</c:if>
	
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>

<gene:javaScript>

	$(".lottoAccorpato").closest("tr").css("font-style","italic");
	$(".lottoCigMaster").closest("tr").css("font-weight","bold");
	
	function filtraLotti() {
		var filtroParametro = new String(document.getElementById("filtroLotti").value);
		filtroParametro = trimStringa(filtroParametro);
		if (filtroParametro != null && filtroParametro != "") {
			document.forms[0].trovaAddWhere.value = " (UPPER(W9LOTT.CIG) like UPPER('%" + filtroParametro + "%') OR UPPER(W9LOTT.OGGETTO) like UPPER('%" + filtroParametro + "%')) ";
			//document.getElementById("filtroLotti").value = filtroParametro;
			document.getElementById("cigOggetto").value = filtroParametro;
			listaVaiAPagina(0);
		} else {
			if (document.forms[0].trovaAddWhere.value != null && document.forms[0].trovaAddWhere.value != "") {
				document.forms[0].trovaAddWhere.value = "";
				document.getElementById("cigOggetto").value = "";
				listaVaiAPagina(0);
			}
		}
	}
	
	function popolamentoMassivoSchede() {
		bloccaRichiesteServer();
		document.location.href="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9lott/scelta-tipo-scheda.jsp?key=${key}";
	}

	function inviaDatiLotto(key) {
		var arrayStr = key.replace(";", ":");
		arrayStr = arrayStr.split(":");
		var codiceGara = arrayStr[1];
		var codiceLotto = arrayStr[3];
		document.formApriPagina.codiceGara.value = codiceGara;
		document.formApriPagina.codiceLotto.value = codiceLotto;
		openPopUpActionCustom("${pageContext.request.contextPath}/w9/InviaDatiLottoAL.do", "metodo=inizio&codiceGara="+ codiceGara +"&codiceLotto="+codiceLotto, "controlloDatiLottoAL", 550, 675, "yes", "yes");
	}

</gene:javaScript>