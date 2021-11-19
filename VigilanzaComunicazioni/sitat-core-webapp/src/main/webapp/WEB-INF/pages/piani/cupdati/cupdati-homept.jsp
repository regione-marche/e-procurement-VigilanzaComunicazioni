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

<gene:setIdPagina schema="PIANI" maschera="cupdati-homept" />

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
    		function trovaCUP(){
			 	var lsStr=formfind.findstr.value.toUpperCase();
				var where = "";
				var parametri = "";
				var filtroUtente = "";
				if(lsStr!=null && lsStr!=""){
					where += "(${fnucase}( CUPDATI.CUP ) like ? OR ${fnucase} (CUPDATI.DESCRIZIONE_INTERVENTO) like ?)";
					parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
				}
				if (filtroUtente != ""){
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

			function nuovoCUP(){
				bloccaRichiesteServer();
				listaNuovo.submit();
			}
			
			function trovaCUPAvanzata(){
				apriPagina.href.value="piani/cupdati/cupdati-trova.jsp";
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
	<colgroup width="800px"></colgroup>
	<colgroup width="*"></colgroup>
	<TBODY>
		<TR class="testata">
			<TD colspan="3">
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

			<!-- PARTE NECESSARIA PER VISUALIZZARE I SOTTOMENU DEL MENU PRINCIPALE DI NAVIGAZIONE -->
			<iframe id="iframesubnavmenu" class="gene"></iframe>
			<div id="subnavmenu" class="subnavbarmenuskin"
				onMouseover="highlightSubmenuNavbar(event,'on');"
				onMouseout="highlightSubmenuNavbar(event,'off');"></div>
			</TD>
			<TD align="right" nowrap><jsp:include
				page="/WEB-INF/pages/commons/pulsantiGenerali.jsp" /></TD>
		</TR>
		<TR>
			<TD class="menuazioni">
			<div id="menulaterale"></div>
			</TD>
			<TD class="arealavoro" colspan="2">
			<form name="formfind" action="javascript:trovaCUP();">
			<table align="left" class="arealayout" style="width: 500">
				<tr>
					<td><br>
					<br>
					<br>
					<img src="${contextPath}/img/${applicationScope.pathImg}scrittaAlice.gif" alt="Cerca"></td>
				</tr>
				<tr>
					<td valign="middle">
						<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
						<a href="javascript:trovaCUP()">
						<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Ricerca CUP" align="top">
						</a>
					</td>
				</tr>

				<tr>
					<td><br><br>
					<b>
					<a class="link-generico" href="javascript:trovaCUPAvanzata()"
						title="Ricerca avanzata CUP">Ricerca avanzata CUP</a>
					</b>
					</td>
				</tr>
				
				<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
					<tr>
						<td><br>
						<br><b>
						<a class="link-generico" href="javascript:nuovoCUP();"
							title="Richiesta nuovo CUP">Richiesta nuovo CUP</a></b></td>
					</tr>
				</c:if>
	
				<tr>
					<td align="center"><br>
					<br>
					<br>
					<br>
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.SITAT")}'> 
						<p style="color: #707070;">
							<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
						</p>
					</c:if>
					</td>
				</tr>
				
			</table>
			</form>
			<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="piani/cupdati/cupdati-lista.jsp" /> 
				<input type="hidden" name="entita" value="CUPDATI" /> 
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaParameter" value="" /> 
				<input type="hidden" name="risultatiPerPagina" value="20" />
 			</form>
			<form name="listaNuovo" action="${contextPath}/Lista.do" method="post">
				<input type="hidden" name="jspPath" value="/WEB-INF/pages/piani/cupdati/cupdati-lista.jsp" /> 
				<input type="hidden" name="jspPathTo" value="" /> 
				<input type="hidden" name="activePage" value="" /> 
				<input type="hidden" name="isPopUp" value="0" /> 
				<input type="hidden" name="numeroPopUp" value="" /> 
				<input type="hidden" name="metodo" value="nuovo" /> 
				<input type="hidden" name="entita" value="CUPDATI" /> 
				<input type="hidden" name="gestisciProtezioni" value="1" />
			</form>
			<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="piani/cupdati/cupdati-lista.jsp" />
			</form>
			</TD>
		</TR>
	</TBODY>
</TABLE>


</BODY>
</HTML>