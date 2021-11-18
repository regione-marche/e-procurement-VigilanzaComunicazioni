<%/*
       * Created on 17-ott-2007
       *
       * Copyright (c) EldaSoft S.p.A.
       * Tutti i diritti sono riservati.
       *
       * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
       * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
       * aver prima formalizzato un accordo specifico con EldaSoft.
       */

      %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="fnucase" value='${gene:callFunction("it.eldasoft.gene.tags.utils.functions.GetUpperCaseDBFunction", "")}' />

<gene:setIdPagina schema="W9" maschera="w9inbox-home" />

<gene:historyClear />

<HTML>
<HEAD>
<jsp:include page="/WEB-INF/pages/commons/headStd.jsp" />
<script type="text/javascript">
<!--
	<jsp:include page="/WEB-INF/pages/commons/checkDisabilitaBack.jsp" />

  // al click nel documento si chiudono popup e menu
  if (ie4||ns6) document.onclick=hideSovrapposizioni;

  function hideSovrapposizioni() {
    //hideSubmenuNavbar();
    hideMenuPopup();
    hideSubmenuNavbar();
  }
-->
</script>
<jsp:include page="/WEB-INF/pages/commons/jsSubMenuComune.jsp" />
<jsp:include page="/WEB-INF/pages/commons/jsSubMenuSpecifico.jsp" />
<jsp:include page="/WEB-INF/pages/commons/vuoto.jsp" />
<script type="text/javascript">
<!--

			function trovaFlussiErrore(){
				var where = "";
				var parametri = "";
				where += "(V_W9INBOX.STACOM = 3)";
				parametri += "";
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
			}

			function trovaComunicazioniErroriSCP(){
				var where = "";
				var parametri = "";
				where += "(W9OUTBOX.STATO = 3)";
				parametri += "";
				formTrovaOUTBOX.trovaAddWhere.value=where;
				formTrovaOUTBOX.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrovaOUTBOX.submit();
			}

			function trovaFlussiWarning(){
				var where = "";
				var parametri = "";
				where += "(V_W9INBOX.STACOM = 4) AND IDCOMUN NOT IN(SELECT IDCOMUN FROM W9FLUSSI WHERE TINVIO2=-1) AND IDCOMUN NOT IN(SELECT IDCOMUN FROM W9FLUSSI_ELIMINATI WHERE TINVIO2=-1)";
				parametri += "";
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
			}
			
			function listaRichiesteCancellazione(){
				var where = "";
				var parametri = "";
				where += "(V_W9INBOX.STACOM = 4) AND (IDCOMUN IN(SELECT IDCOMUN FROM W9FLUSSI WHERE TINVIO2=-1) OR IDCOMUN IN(SELECT IDCOMUN FROM W9FLUSSI_ELIMINATI WHERE TINVIO2=-1))";
				parametri += "";
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
			}
			
			function trovaFeedbackNonAnalizzati(){
				var where = "";
				var parametri = "";
				where += "(W9XML.FEEDBACK_ANALISI = '2')";
				parametri += "";
				formTrovaXML.trovaAddWhere.value=where;
				formTrovaXML.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrovaXML.submit();
			}
-->
</script>
</HEAD>

<BODY onload="setVariables();checkLocation();initPage();">
<jsp:include page="/WEB-INF/pages/commons/bloccaRichieste.jsp" />

<c:set var="profilo" value='${sessionScope.profiloAttivo }' />
<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetContatoriInboxFunction" />
					
<TABLE class="arealayout">

	<colgroup width="150px"></colgroup>
	<colgroup width="*"></colgroup>
	<TBODY>
		<TR class="testata">
			<TD colspan="2">
				<jsp:include page="/WEB-INF/pages/commons/testata.jsp" />
			</TD>
		</TR>
		<TR class="menuprincipale">
			<TD>
				<img src="${contextPath}/img/spacer-azionicontesto.gif" alt="">
			</TD>
			<TD>
				<table class="contenitore-navbar">
					<tbody>
						<tr>
							<jsp:include page="/WEB-INF/pages/commons/menuSpecifico.jsp" />
							<jsp:include page="/WEB-INF/pages/commons/menuComune.jsp" />
						</tr>
					</tbody>
				</table>

				<!-- PARTE NECESSARIA PER VISUALIZZARE I SOTTOMENU DEL MENU PRINCIPALE DI NAVIGAZIONE -->
				<iframe id="iframesubnavmenu" class="gene"></iframe>
				<div id="subnavmenu" class="subnavbarmenuskin"
					onMouseover="highlightSubmenuNavbar(event,'on');"
					onMouseout="highlightSubmenuNavbar(event,'off');"></div>

			</TD>
		</TR>
		<TR>
			<TD class="menuazioni" valign="top">
				<div id="menulaterale"></div>
			</TD>
			<TD class="arealavoro">
				<jsp:include page="/WEB-INF/pages/commons/areaPreTitolo.jsp" />
				<div class="contenitore-arealavoro">
			
					<form name="formfind" action="javascript:trovaCUP();">
						<table align="left" class="dettaglio-home" style="width: 500">
							<tr>
								<td class="voce">
									<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
									<p>${sessionScope.descProfiloAttivo}</p>
								</td>
							</tr>
							
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9INBOX-HOME.FLUSSI")}' >
							<tr>
								<td valign="middle" class="voce">
									<p><b>Flussi</b></p>
									<p><a class="link-generico" href="${contextPath}/ApriPagina.do?href=w9/w9inbox/w9inbox-trova.jsp">
										Ricerca flussi
									</a></p>
									<p><a class="link-generico" href="javascript:trovaFlussiErrore()"	title="Elenco errori">
										<c:if test="${numeroErrori > 0}">
											<b>Elenco errori (${numeroErrori})</b>
										</c:if>
										<c:if test="${numeroErrori == 0}">
											Elenco errori
										</c:if>
									</a></p>
									<p><a class="link-generico" href="javascript:trovaFlussiWarning()"	title="Elenco warning">
										<c:if test="${numeroWarnings > 0}">
											<b>Elenco warning (${numeroWarnings})</b>
										</c:if>
										<c:if test="${numeroWarnings == 0}">
											Elenco warning
										</c:if>
									</a></p>
									<p><a class="link-generico" href="javascript:listaRichiesteCancellazione()"	title="Richieste cancellazione">
										<c:if test="${numeroCancellazioniNonEvase > 0}">
											<b>Richieste cancellazione non evase (${numeroCancellazioniNonEvase})</b>
										</c:if>
										<c:if test="${numeroCancellazioniNonEvase == 0}">
											Richieste cancellazione non evase
										</c:if>
									</a></p>
								</td>
							</tr>
						</c:if>
			
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9INBOX-HOME.FEEDBACK")}' >
							<tr>
								<td valign="middle" class="voce">
									<p><b>Feedback</b></p>
									<p><a class="link-generico" href="${contextPath}/ApriPagina.do?href=w9/w9xml/w9xml-trova.jsp">
										Ricerca feedback
									</a></p>
									<p><a class="link-generico" href="javascript:trovaFeedbackNonAnalizzati()" title="Elenco feedback non analizzati">
										<c:if test="${numeroFeedbackNonAnalizzati > 0}">
											<b>Elenco feedback non analizzati (${numeroFeedbackNonAnalizzati})</b>
										</c:if>
										<c:if test="${numeroFeedbackNonAnalizzati == 0}">
											Elenco feedback non analizzati
										</c:if>
									</a></p>
								</td>
							</tr>
						</c:if>
						
							<tr>
								<td valign="middle" class="voce">
									<p><b>Comunicazioni per SCP</b></p>
									<p><a class="link-generico" href="${contextPath}/ApriPagina.do?href=w9/w9outbox/w9outbox-trova.jsp">
										Ricerca comunicazioni
									</a></p>
									<p><a class="link-generico" href="javascript:trovaComunicazioniErroriSCP()" title="Elenco errori">
										<c:if test="${numeroComunicazioniSCPErrore > 0}">
											<b>Elenco errori (${numeroComunicazioniSCPErrore})</b>
										</c:if>
										<c:if test="${numeroComunicazioniSCPErrore == 0}">
											Elenco errori
										</c:if>
									</a></p>
								</td>
							</tr>
							
							<tr>
								<td align="center"><br>
								<br>
								<br>
								<br>
									<p style="color: #707070;">
										<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
									</p>
								</td>
							</tr>
							
						</table>
					</form>
					<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
						<input type="hidden" name="href" value="w9/v_w9inbox/v_w9inbox-lista.jsp" /> 
						<input type="hidden" name="entita" value="V_W9INBOX" /> 
						<input type="hidden" name="trovaAddWhere" value="" />
						<input type="hidden" name="trovaParameter" value="" /> 
						<input type="hidden" name="risultatiPerPagina" value="20" />
		 			</form>
		 			<form name="formTrovaXML" action="${contextPath}/ApriPagina.do" method="post">
						<input type="hidden" name="href" value="w9/w9xml/w9xml-lista.jsp" /> 
						<input type="hidden" name="entita" value="W9XML" /> 
						<input type="hidden" name="trovaAddWhere" value="" />
						<input type="hidden" name="trovaParameter" value="" /> 
						<input type="hidden" name="risultatiPerPagina" value="20" />
		 			</form>
		 			<form name="formTrovaOUTBOX" action="${contextPath}/ApriPagina.do" method="post">
						<input type="hidden" name="href" value="w9/w9outbox/w9outbox-lista.jsp" /> 
						<input type="hidden" name="entita" value="W9OUTBOX" /> 
						<input type="hidden" name="trovaAddWhere" value="" />
						<input type="hidden" name="trovaParameter" value="" /> 
						<input type="hidden" name="risultatiPerPagina" value="20" />
		 			</form>
				</div>

				<!-- PARTE NECESSARIA PER VISUALIZZARE I POPUP MENU DI OPZIONI PER CAMPO -->
				<IFRAME class="gene" id="iframepopmenu"></iframe>
				<div id="popmenu" class="popupmenuskin"
					onMouseover="highlightMenuPopup(event,'on');"
					onMouseout="highlightMenuPopup(event,'off');"></div>
				
			</TD>
		</TR>

		<TR>
			<TD COLSPAN="2">
				<div id="footer">
					<jsp:include page="/WEB-INF/pages/commons/footer.jsp" />
				</div>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<div id="contenitorefeedrss" style="position:absolute; left:630px; top:110px;" >
	<jsp:include page="/WEB-INF/pages/commons/feedRss.jsp" />
</div>		

</BODY>
</HTML>