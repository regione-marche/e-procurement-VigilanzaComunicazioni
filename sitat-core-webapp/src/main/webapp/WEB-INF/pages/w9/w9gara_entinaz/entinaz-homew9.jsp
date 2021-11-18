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
    
       // PAGINA CHE CONTIENE LA HOMEPAGE DI Sitat per il profilo Enti Nazionali e contratti sotto 40000 euro
      %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="fnucase" value='${gene:callFunction("it.eldasoft.gene.tags.utils.functions.GetUpperCaseDBFunction", "")}' />

<gene:setIdPagina schema="W9" maschera="cupdati-homew9" />

<gene:historyClear />

<HTML>
<HEAD>
<jsp:include page="/WEB-INF/pages/commons/headStd.jsp" />
<script type="text/javascript" src="${contextPath}/js/controlliFormali.js"></script>

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

			function trovaGaraEntinaz() {
			 	var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
				var where = "";
				var parametri = "";
				var filtroUtente = "";
				if (lsStr!=null && lsStr!="") {
					where = " UPPER(W9GARA.OGGETTO) like ? OR exists (select 1 from W9LOTT_ENTINAZ where W9LOTT_ENTINAZ.CODGARA = W9GARA_ENTINAZ.CODGARA and (UPPER(W9LOTT_ENTINAZ.OGGETTO) like ? OR UPPER(W9LOTT_ENTINAZ.CIG) like ?)) ";
	 				parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%;T:%"+lsStr+"%";		
				}
				
				if (filtroUtente != "") {
				  if (where != "") {
				  	where += " AND "
				  }
				  where += filtroUtente;
				}
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
			}
			
			function nuovaGaraEntinaz() {
				bloccaRichiesteServer();
				listaNuovo.submit();
			}
			
			function trovaAvanzataEntinaz() {
				apriPagina.href.value="w9/w9gara_entinaz/w9gara_entinaz-trova.jsp";
				bloccaRichiesteServer();
				apriPagina.submit();
			}

-->
</script>
</HEAD>

<BODY onload="setVariables();checkLocation();initPage();">
<jsp:include page="/WEB-INF/pages/commons/bloccaRichieste.jsp" />
<TABLE class="arealayout">
	<!-- questa definizione dei gruppi di colonne serve a fissare la dimensione
	     dei td in modo da vincolare la posizione iniziale del menu' di navigazione
	     sopra l'area lavoro appena al termine del menu' contestuale -->
	<colgroup width="150px"></colgroup>
	<colgroup width="*"></colgroup>
	<TBODY>
		<TR class="testata">
			<TD colspan="2">
			<jsp:include page="/WEB-INF/pages/commons/testata.jsp" />
			</TD>
		</TR>
		<TR class="menuprincipale">
			<TD><img src="${contextPath}/img/spacer-azionicontesto.gif" alt=""></TD>
			<TD>
			<table class="contenitore-navbar">
				<tbody>
					<tr>
						<jsp:include page="/WEB-INF/pages/commons/menuSpecifico.jsp" />
						<jsp:include page="/WEB-INF/pages/commons/menuComune.jsp" />
					</tr>
				</tbody>
			</table>

			</TD>
		</TR>
		<TR>
			<TD class="menuazioni">
				<div id="menulaterale"></div>
			</TD>
			<TD class="arealavoro">
				<jsp:include page="/WEB-INF/pages/commons/areaPreTitolo.jsp" />
				<div class="contenitore-arealavoro">
			
				<form name="formfind" action="javascript:trovaGaraEntinaz();">
				<table align="left" class="arealayout" style="width: 500">
					<tr>
						<td>
							<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
							<p>${sessionScope.descProfiloAttivo}</p>
							</td>
						</tr>

						<tr>
							<td valign="middle">
								<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
								<a href="javascript:trovaGaraEntinaz()">
								<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova gara" align="top">
								</a>
							</td>
						</tr>
		
						<tr>
							<td><br><br>
							<b>
							<a class="link-generico" href="javascript:trovaAvanzataEntinaz()"
								title="Ricerca avanzata gare">Ricerca avanzata</a>
							</b>
							</td>
						</tr>
					
					<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
						<tr>
							<td>
								<br><br><b>
								<a class="link-generico" href="javascript:nuovaGaraEntinaz();"
									title="Inserimento nuova anagrafica gara">Inserimento nuova anagrafica gara</a></b>
							</td>
						</tr>
					</c:if>
		
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
					<input type="hidden" name="href" value="w9/w9gara_entinaz/w9gara_entinaz-lista.jsp" />
					<input type="hidden" name="entita" value="W9GARA_ENTINAZ" />
					<input type="hidden" name="trovaAddWhere" value="" />
					<input type="hidden" name="trovaParameter" value="" />
					<input type="hidden" name="risultatiPerPagina" value="20" />
	 			</form>
				<form name="listaNuovo" action="${contextPath}/Lista.do" method="post">
					<input type="hidden" name="jspPath" value="/WEB-INF/pages/w9/w9gara_entinaz/w9gara_entinaz-lista.jsp" />
					<input type="hidden" name="jspPathTo" value="" />
					<input type="hidden" name="activePage" value="" />
					<input type="hidden" name="isPopUp" value="0" />
					<input type="hidden" name="numeroPopUp" value="" />
					<input type="hidden" name="metodo" value="nuovo" />
					<input type="hidden" name="entita" value="W9GARA_ENTINAZ" />
					<input type="hidden" name="gestisciProtezioni" value="1" />
				</form>
				<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
					<input type="hidden" name="href" value="w9/w9gara_entinaz/w9gara_entinaz-lista.jsp" />
				</form>
				
			<!-- PARTE NECESSARIA PER VISUALIZZARE I SOTTOMENU DEL MENU PRINCIPALE DI NAVIGAZIONE -->
			<iframe id="iframesubnavmenu" class="gene"></iframe>
			<div id="subnavmenu" class="subnavbarmenuskin"
				onMouseover="highlightSubmenuNavbar(event,'on');"
				onMouseout="highlightSubmenuNavbar(event,'off');"></div>
				
				</div>
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