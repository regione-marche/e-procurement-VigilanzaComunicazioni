<%
	/*
	 * Created on: 19/10/2009
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="idflusso" value='${gene:getValCampo(key,"IDFLUSSO")}'></c:set>
<c:set var="area" value='${gene:getValCampo(key,"AREA")}'></c:set>
<c:set var="key01" value='${gene:getValCampo(key,"KEY01")}'></c:set>
<c:set var="key02" value='${gene:getValCampo(key,"KEY02")}'></c:set>
<c:set var="key03" value='${gene:getValCampo(key,"KEY03")}'></c:set>
<c:set var="key04" value='${gene:getValCampo(key,"KEY04")}'></c:set>
<c:set var="tipoInvio" value='${gene:getValCampo(key,"TINVIO2")}'></c:set>
<c:set var="cancella" value="${param.cancella}"/>
<c:set var="msgCancellazione" value="${msgCancellazione}"/>

<gene:callFunction obj="it.eldasoft.gene.tags.functions.DebugSviluppoFunction" />

<c:if test='${(key03 eq "901") or (key03 eq "990") or (key03 eq "988") or (key03 eq "13") or (key03 eq "983")}'>
	<c:set var="tipoInvio" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetBandoRettificaFunction", pageContext,key)}'></c:set>
</c:if>

<c:if test='${(area eq "") || (key01 eq "")}'>
	${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetDatiFlussoFunction",pageContext,key)}
</c:if>

<c:if test='${(area eq 1) or (area eq 2) or (area eq 5)}'>
	<c:set var="idavgaraCigPresent" value='${requestScope.idavgaraCigPresent}'/>
</c:if>
<c:if test='${not empty param.creaBando}'>
	<c:set var="creaSoloBando" value="true" scope="page"/>
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"	schema="W9" idMaschera="W9FLUSSI-scheda">

<c:choose>
<c:when test='${!empty msgCancellazione}'>
	<gene:redefineInsert name="corpo">
	<form id="form1" name="form1">
		<table class="dettaglio-notab" >
			<tr><td colSpan="2" ><br><b>La cancellazione della fase &egrave andata a buon fine. Torna alla lista delle fasi.</b><br><br></td></tr>
			<tr>
			<td class="comandi-dettaglio" colSpan="2">
				<gene:insert name="pulsanteListaEliminaSelezione">
					<INPUT type="button" class="bottone-azione" title='Annulla'	value="Indietro"	onclick="javascript:historyVaiIndietroDi(1);">
				</gene:insert> &nbsp;
			</td></tr>
		</table>
	</form>
	</gene:redefineInsert>
</c:when>
<c:when test='${gene:getValCampo(key,"BANDO")}'>
	<gene:redefineInsert name="corpo">
	<gene:formScheda entita="W9FLUSSI" gestisciProtezioni="true" >
	
		<gene:redefineInsert name="schedaConferma">
			<tr>
				<td class="vocemenulaterale"><a href="javascript:scegliFase();"
				title="Inserisci" tabindex="1501"> Conferma </a>
				</td>
			</tr>
		</gene:redefineInsert>
		<gene:redefineInsert name="schedaAnnulla">
			<tr>
				<td class="vocemenulaterale"><a href="javascript:schedaAnnulla();"
			title="Inserisci" tabindex="1502"> Annulla </a>
				</td>
			</tr>
		</gene:redefineInsert>
	</gene:formScheda>
	
	<form id="form1" name="form1">
		<table class="dettaglio-notab" >
			<tr><td colSpan="2" ><b>Selezionare l'invio desiderato:</b></td></tr>
			<tr class="datilista" ><td><input type="radio" name="group1" value="gara" <c:if test='${creaSoloBando}'> checked="checked"</c:if> ></td><td class="datilista">Anagrafica Gara/Lotti</td></tr>
			<tr class="datilista" ><td><input type="radio" name="group1" value="bando" <c:if test='${creaSoloBando}'> disabled="disabled" </c:if> ></td><td class="datilista">Bando</td></tr>
			<tr>
			<td class="comandi-dettaglio" colSpan="2">
				<gene:insert name="pulsanteListaInserisci">
					<INPUT type="button" class="bottone-azione" title='Conferma' value="Conferma"	onclick="javascript:scegliFase();">
				</gene:insert>
				<gene:insert name="pulsanteListaEliminaSelezione">
					<INPUT type="button" class="bottone-azione" title='Annulla'	value="Annulla"	onclick="javascript:schedaAnnulla();">
				</gene:insert> &nbsp;
			</td></tr>
		</table>
	</form>
	</gene:redefineInsert>
</c:when>
<c:otherwise>

	<c:if test="${empty cancella}">
		<gene:setString name="titoloMaschera"
		value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9FLUSSI")}' />
	</c:if>
	<c:if test="${!empty cancella}">
		<gene:setString name="titoloMaschera" value='Richiesta cancellazione' />
		<c:set var="tipoInvio" value='-1'/>
	</c:if>
	
	<gene:redefineInsert name="schedaConferma" >
		<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:popupValidazione('${key03}', '${key01}', '${key02}', '${key04}');" title="Prosegui" tabindex="1501">Prosegui</a>
				</td>	
			</tr>
			</c:if>
	</gene:redefineInsert>
	<gene:redefineInsert name="schedaAnnulla">
		<c:if test='${gene:checkProtFunz(pageContext,"INS","LISTANUOVO")}'>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:schedaAnnulla();" title="Annulla invio" tabindex="1502">Annulla</a></td>
			</tr>
		</c:if>
	</gene:redefineInsert>
	<gene:redefineInsert name="pulsanteSalva" >
		<input type="button" class="bottone-azione" value='Prosegui' title='Prosegui' onclick="javascript:popupValidazione('${key03}', '${key01}', '${key02}', '${key04}');">&nbsp;
	</gene:redefineInsert>
	<gene:redefineInsert name="pulsanteAnnulla">
		<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla invio" onclick="javascript:schedaAnnulla()">
	</gene:redefineInsert>
	
	<gene:redefineInsert name="schedaModifica" />
	<gene:redefineInsert name="pulsanteModifica" />
	
	<gene:redefineInsert name="schedaNuovo" >
		<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO") and (numeroFlussiGara eq 0 or (numeroFlussiGara > 0 and idavgaraCigPresent eq "true"))}'>
			<tr>
				<td class="vocemenulaterale">
					<a href="javascript:aggiungiInvio();" title="Nuovo" tabindex="1501">
						${gene:resource("label.tags.template.lista.listaNuovo")}</a></td>
			</tr>
		</c:if>
	</gene:redefineInsert>
	<gene:redefineInsert name="pulsanteNuovo" >
		<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO") and (numeroFlussiGara eq 0 or (numeroFlussiGara > 0 and idavgaraCigPresent eq "true"))}'>
			<INPUT type="button" class="bottone-azione" title='Nuovo'	value="Nuovo" onclick="javascript:aggiungiInvio();">
		</c:if>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9FLUSSI" gestisciProtezioni="true"
			plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9FLUSSI"
			gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9FLUSSI">
			<gene:gruppoCampi idProtezioni="GEN">
			<c:if test='${msgEsito eq "ko" and (not empty requestScope.erroriValidazionePerDebug or not empty requestScope.erroriProxyPerDebug)}' >
				<gene:campoScheda addTr="false" >
					<tr id="erroreInvioFlusso" >
						<td colspan="2" class="valore-dato">
							<br>
							<a href="javascript:visualizzaErroriPerDebug();" alt="Dettaglio dell'errore per assistenza tecnica">Dettaglio dell'errore per assistenza tecnica</a><br>
							<div id="erroriPerDebug" style="display: none" >
								<c:choose>
									<c:when test="${not empty requestScope.erroriValidazionePerDebug}">
										<br><b>Errore validazione XML:</b><br>
										<c:forEach var="errore" items="${requestScope.erroriValidazionePerDebug}" varStatus="stato" >
											<c:if test="${stato.first}" >
												<ul>
											</c:if>
												<c:out value="<li>${errore}</li>" escapeXml="false" />
											<c:if test="${stato.last}" >
												</ul>
											</c:if>
										</c:forEach>
									</c:when>
									<c:when test="${not empty requestScope.erroriProxyPerDebug}">
										<br><b>Errore di interazione con il proxy:</b><br>
										<c:forEach var="errore" items="${requestScope.erroriProxyPerDebug}" varStatus="stato" >											<c:if test="${stato.first}" >
												<ul>
											</c:if>
												<c:out value="<li>${errore}</li>" escapeXml="false" />
											<c:if test="${stato.last}" >
												</ul>
											</c:if>
										</c:forEach>
									</c:when>
								</c:choose>
								<br>
									<c:out value="${requestScope.datiErroreFlusso}" escapeXml="false" />
							</div>
							<br>
						</td>
					</tr>
				</gene:campoScheda>
			</c:if>

				<gene:campoScheda campo="IDFLUSSO" modificabile="false" visibile='${modoAperturaScheda ne "NUOVO"}'/>
				<gene:campoScheda campo="AREA" value="${area}" visibile="false" modificabile="false" />
				<gene:campoScheda campo="KEY01" defaultValue="${key01}" visibile="false" />
				<gene:campoScheda campo="KEY02" defaultValue="${key02}" modificabile="false" visibile='${(area eq "1")}' />
				<gene:campoScheda campo="KEY03" defaultValue="${key03}" visibile='${(area eq "1") || (area eq "2")}' modificabile="false" />
				<gene:campoScheda campo="KEY04" defaultValue="${key04}" visibile='${area eq "1"}' modificabile="false" />
				<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetValoriDropdownFunction" parametro="FLUSSI" />
				
				<c:set var="invioFittizio" value='false'/>
				<c:if test='${tipoInvio ne "-1"}'>
					<c:if test='${!empty listaValoriTinvio2}'>
						<c:forEach items="${listaValoriTinvio2}" var="valoriTinvio2">
							<c:if test='${valoriTinvio2[0] eq "91"}'>
								<c:set var="invioFittizio" value='true'/>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
				<c:choose>
					<c:when test='${invioFittizio eq "true" or isSviluppo}'>
							<gene:campoScheda campo="TINVIO2" visibile="false" />
							<gene:campoScheda campo="TINVIO2FIT" campoFittizio="true" definizione="N7;;;" title="Tipo di invio" obbligatorio="true" >
								<c:if test='${!empty listaValoriTinvio2}'>
									<gene:addValue value="" descr="" />
									<c:if test='${tipoInvio eq "1"}'>
										<gene:addValue value="1" descr="Primo invio" />
									</c:if>
									<c:if test='${tipoInvio eq "2"}'>
										<gene:addValue value="2" descr="Rettifica o integrazione" />
									</c:if>
									<c:if test='${tipoInvio eq "-1"}'>
										<gene:addValue value="-1" descr="Richiesta cancellazione" />
									</c:if>
									<c:if test='${isSviluppo}'>
										<gene:addValue value="99" descr="Test" />
									</c:if>
									<c:if test='${invioFittizio eq "true"}'>
										<gene:addValue value="91" descr="Fittizio" />
									</c:if>
								</c:if>
							</gene:campoScheda>
					</c:when>
					<c:otherwise>
						<gene:campoScheda campo="TINVIO2" defaultValue="${tipoInvio}" modificabile="false" visibile="${empty cancella}"/>
					</c:otherwise>
				</c:choose>
				
				<jsp:useBean id="now" class="java.util.Date" scope="request" />
				<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="nowFormat" />
				<gene:campoScheda campo="DATINV" defaultValue="${nowFormat}" modificabile="${datiRiga.W9FLUSSI_TINVIO2 eq 91}" />
				<gene:campoScheda campo="DATINV2" campoFittizio="true" title="Data invio" definizione="D;0;;;W9FLDATINV" modificabile="true" />
				<c:if test="${empty cancella}">
					<gene:campoScheda campo="NOTEINVIO" />
				</c:if>
				<c:if test="${!empty cancella}">
					<gene:campoScheda campo="NOTEINVIO" title="Causa richiesta" obbligatorio="true" />
				</c:if>
				<!--gene:campoScheda campo="SYSUTE" entita="USRSYS" where="USRSYS.SYSCON = W9FLUSSI.CODCOMP" /-->
				<!--gene-:-campoScheda campo="SYSUTE" entita="USRSYS" title="Nominativo del compilatore" definizione="T" where="USRSYS.SYSCON = W9FLUSSI.CODCOMP" modificabile="false"	defaultValue="${sessionScope.profiloUtente.nome}" /-->
				<gene:campoScheda campo="AUTORE" modificabile="false"	defaultValue="${sessionScope.profiloUtente.nome}" />
				<gene:campoScheda campo="CODCOMP" visibile="false" value="${sessionScope.profiloUtente.id}" />
				<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetDatiUffintFunction" parametro="${sessionScope.uffint}" />	
				<gene:campoScheda campo="CFSA" visibile="false" value="${cfein}" />
				<gene:campoScheda campo="XML" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreLunghezzaCampo" visibile="false" />
		<c:choose>
			<c:when test='${datiRiga.W9FLUSSI_XML eq 1}' >
				<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA" and gene:checkProt( pageContext,"FUNZ.VIS.ALT.W9.W9FLUSSI_XML")}' modificabile="false">
					<a href='javascript:apriAllegato();'>
						<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
					</a>
				</gene:campoScheda>
			</c:when>
			<c:otherwise>
				<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA" and gene:checkProt( pageContext,"FUNZ.VIS.ALT.W9.W9FLUSSI_XML")}' modificabile="false" >
					&nbsp;
				</gene:campoScheda>
			</c:otherwise>
		</c:choose>
				<gene:campoScheda campo="VERXML" modificabile="false" defaultValue="${requestScope.versioneTracciatoXML}" />
			</gene:gruppoCampi>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
				<gene:campoScheda campo="SIMOGUSER" campoFittizio="true" visibile="false" definizione="T100"/>
				<gene:campoScheda campo="SIMOGPASS" campoFittizio="true" visibile="false" definizione="T100"/>
				<gene:campoScheda campo="RECUPERAUSER" campoFittizio="true" visibile="false" definizione="T1"/>
				<gene:campoScheda campo="RECUPERAPASS" campoFittizio="true" visibile="false" definizione="T1"/>
				<gene:campoScheda campo="MEMORIZZA" campoFittizio="true" visibile="false" definizione="T5"/>
				<gene:campoScheda entita="W9XML" campo="NUM_ERRORE" where="W9FLUSSI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="NUM_WARNING" where="W9FLUSSI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="CODGARA" where="W9FLUSSI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="CODLOTT" where="W9FLUSSI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="NUMXML" where="W9FLUSSI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<c:if test='${!empty datiRiga.W9XML_NUM_ERRORE}'>
					<gene:campoScheda>
						<td colspan="2"><b>Elaborazione eseguita dal SIMOG</b></td>
					</gene:campoScheda>
					<gene:campoScheda campo="NUMERO_ERRORI" title="Numero errori riscontrati" campoFittizio="true" value="${datiRiga.W9XML_NUM_ERRORE}" definizione="N5;0"/>
					<gene:campoScheda campo="NUMERO_AVVISI" title="Numero avvisi" campoFittizio="true" value="${datiRiga.W9XML_NUM_WARNING}" definizione="N5;0"/>
					<gene:campoScheda>
						<td colspan="2"><b>Esito dell'elaborazione</b></td>
					</gene:campoScheda>
					<gene:campoScheda>
					<c:set var="codgara" value="${datiRiga.W9XML_CODGARA}" />
					<c:set var="codlott" value="${datiRiga.W9XML_CODLOTT}" />
					<c:set var="numxml" value="${datiRiga.W9XML_NUMXML}" />
					<gene:sqlSelectList nome="errori_avvisi" tipoOut="VectorString">
						select LIVELLO, CODICE, DESCRIZIONE, SCHEDA from W9XMLANOM where W9XMLANOM.CODGARA = ${codgara} AND W9XMLANOM.CODLOTT = ${codlott} AND W9XMLANOM.NUMXML = ${numxml}
					</gene:sqlSelectList>
					<table class="datilista">
						<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Codice anomalia</th>
							<th>Descrizione anomalia</th>
							<th>Tipo scheda</th>
						</tr>
						</thead>
					<c:forEach items="${errori_avvisi}" var="avviso" varStatus="stato">
						<c:if test="${stato.index%2 == 0}">
							<tr class="even">
						</c:if>
						<c:if test="${stato.index%2 == 1}">
							<tr class="odd">
						</c:if>
						<td>
							<c:choose>
								<c:when test="${avviso[0] == 'ERRORE'}">
									<img width="20" height="20" title="Errore" alt="Errore" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
								</c:when>
								<c:when test="${avviso[0] == 'ATTENZIONE'}">
									<img width="20" height="20" title="Attenzione" alt="Attenzione" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
								</c:when>
								<c:when test="${avviso[0] == 'AVVISO'}">
									<img width="20" height="20" title="Avviso" alt="Avviso" src="${pageContext.request.contextPath}/img/controllo_i.gif"/>	
								</c:when>
							</c:choose>
						</td>
						<td>${avviso[1]}</td>
						<td>${avviso[2]}</td>
						<td>${avviso[3]}</td>
						</tr>
					</c:forEach>
					
					</table>
					</gene:campoScheda>
				</c:if>
			</c:if>
			<gene:fnJavaScriptScheda funzione='valorizzaTinvio(#TINVIO2FIT#);' elencocampi="TINVIO2FIT" esegui="false" />
			<gene:fnJavaScriptScheda funzione='visualizzaDataInvio(#W9FLUSSI_TINVIO2#);' elencocampi="W9FLUSSI_TINVIO2" esegui="true" />
			<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
				<jsp:param name="entitaParent" value="W9FLUSSI" />
			</jsp:include>

			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>

</c:otherwise>
</c:choose>

	<gene:javaScript>
		
		<c:if test='${msgEsito eq "ok"}'>
			<c:choose>
				<c:when test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
					alert("I dati sono stati inviati all'Osservatorio Regionale con esito positivo");
				</c:when>
				<c:otherwise>
					alert("I dati sono stati inviati a Simog con esito positivo");
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<c:if test='${msgEsito eq "ko"}'>
			<c:choose>
				<c:when test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
					alert("Invio non riuscito. Si sono verificati degli errori durante l'invio dei dati all'Osservatorio Regionale.");
				</c:when>
				<c:when test='${not empty requestScope.msgEsitoLoaderAppalto}'>
					alert("I dati sono stati inviati a Simog con esito negativo. ");
				</c:when>
				<c:otherwise>
					alert("Invio non riuscito. Si sono verificati degli errori durante l'invio dei dati");
				</c:otherwise>
			</c:choose>
		</c:if>
		

		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('idflusso=' + getValue("W9FLUSSI_IDFLUSSO") + '&tab=flussi');
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
		
		function valorizzaTinvio(valore) {
			setValue("W9FLUSSI_TINVIO2", valore);
		}
		
		function visualizzaDataInvio(valore) {
	<c:choose>
		<c:when test='${modo ne "VISUALIZZA"}'>
			if (valore == "91") {
				showObj("rowDATINV2", true);
				showObj("rowW9FLUSSI_DATINV", false);
			} else {
				showObj("rowDATINV2", false);
				showObj("rowW9FLUSSI_DATINV", true);
			}
		</c:when>
		<c:otherwise>
			showObj("rowDATINV2", false);
			showObj("rowW9FLUSSI_DATINV", true);
		</c:otherwise>
	</c:choose>
		}
		
		function scegliFase () {
			var chosen = "";
			var len = document.form1.group1.length;
			var pag = "";
			var flussoSel = '988';
			
			for (i = 0; i < len; i++) {
				if (document.form1.group1[i].checked) {
					chosen = document.form1.group1[i].value;
				}
			}
			if (chosen != '') {
				var action="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9flussi/w9flussi-scheda.jsp";
				if (chosen == 'bando') flussoSel = '990';
				document.forms[0].key.value="W9FLUSSI.AREA=N:2;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY03=N:"+flussoSel+";W9FLUSSI.TINVIO2=N:${tipoInvio}";
				document.forms[0].action=action;
				document.forms[0].metodo.value="nuovo";
				document.forms[0].activePage.value="0";
				document.forms[0].submit();
			} else {
				alert('Per procedere &egrave; necessario effettuare una scelta.');
			}
		}
		
		function aggiungiInvio() {
			document.forms[0].key.value="W9FLUSSI.AREA=N:2;W9FLUSSI.KEY01=N:${key01};W9FLUSSI.KEY03=N:${flusso};BANDO=T:true";  //W9FLUSSI.TINVIO2=N:"+tipoInvio+";
			document.forms[0].metodo.value="nuovo";
			document.forms[0].activePage.value="0";
			document.forms[0].submit();
		}
		
		function visualizzaErroriPerDebug(){
			var obj = document.getElementById("erroriPerDebug");
			if(isObjShow("erroriPerDebug"))
				showObj("erroriPerDebug", false);
			else
				showObj("erroriPerDebug", true);
		}

		function popupValidazioneRM(flusso,key1,key2,key3) {
   			var comando = "href=w9/commons/popup-validazione-generale.jsp";
   			<c:choose>
   				<c:when test='${area eq 3}'>
   					comando = comando + "&flusso=" + flusso;
   					comando = comando + "&key1=${gene:getValCampo(keyParent,"CODEIN")}";
   					comando = comando + "&key2=" + key1;
   					comando = comando + "&key3=" + key2;
   					comando = comando + "&invio=ok";
   				</c:when>
   				<c:otherwise>
   					comando = comando + "&flusso=" + flusso;
   					comando = comando + "&key1=" + key1;
   					comando = comando + "&key2=" + key2;
   					comando = comando + "&key3=" + key3;
   					comando = comando + "&invio=ok";
   				</c:otherwise>
   			</c:choose>

   			openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
  		}
  
    function popupValidazione(flusso,key1,key2,key3) {
    	setValue("W9FLUSSI_XML", "");
    	var tipoInvio = getValue("W9FLUSSI_TINVIO2");
    	if ("91" != tipoInvio) {
    		<c:if test='${empty cancella}'>
    			var comando = "href=w9/commons/popup-validazione-generale.jsp";
    		</c:if>
    		<c:if test='${!empty cancella}'>
    			var comando = "href=w9/commons/popup-elimina-scheda-simog.jsp";
    		</c:if>
			<c:choose>
				<c:when test='${area eq 3}'>
					comando = comando + "&flusso=" + flusso;
					comando = comando + "&key1=${gene:getValCampo(keyParent,"CODEIN")}";
					comando = comando + "&key2=" + key1;
					comando = comando + "&key3=" + key2;
					comando = comando + "&key4=1&invio=ok";
	   		</c:when>
	   		<c:otherwise>
					comando = comando + "&flusso=" + flusso;
					comando = comando + "&key1=" + key1;
					comando = comando + "&key2=" + key2;
					comando = comando + "&key3=" + key3;
					comando = comando + "&invio=ok";
	   		</c:otherwise>
			</c:choose>
				
				openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
			} else {
				setValue("W9FLUSSI_DATINV", getValue("DATINV2"));
				schedaConferma();
			}
		}
  
	</gene:javaScript>
</gene:template>
