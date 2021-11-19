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

      // PAGINA CHE CONTIENE LA HOMEPAGE DI Sitat
      %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="fnucase"
	value='${gene:callFunction("it.eldasoft.gene.tags.utils.functions.GetUpperCaseDBFunction", "")}' />

<gene:setIdPagina schema="PIANI" maschera="HOMEPT" />

<gene:historyClear />

<HTML>
<HEAD>
<jsp:include page="/WEB-INF/pages/commons/headStd.jsp" />
<script type="text/javascript" src="${contextPath}/js/controlliFormali.js"></script>

<script type="text/javascript">
<!--
<c:if test='${applicationScope.backAbilitato ne "1"}' >
  // disabilitazione del pulsante back
  window.history.forward(1);
</c:if>
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
<script type="text/javascript">
<!--
		
		function trovaProgramma(){
		  // Eseguo la ricerca della programma
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = "";
			
			if(lsStr!=null && lsStr!=""){
				if (where != "") {
					where += " AND ";
				}
				where += "("; 
				if (!isNaN(lsStr) && lsStr.length == 4) {
					where += "PIATRI.ANNTRI = ? OR ";
					parametri += "N:"+lsStr+";";
				}
				where += "${fnucase}( PIATRI.BRETRI ) like ? )";
				parametri += "T:%"+lsStr+"%";
			}
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		//funzione di creazione nuovo programma triennale
		function nuovoProgramma(tiprog){
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=piani/piatri/piatri-scheda.jsp&modo=NUOVO&tiprog='+tiprog;
		}
		//crea un nuovo programma a partire da un file XML
		function ImportXML() {
			document.location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=piani/piatri/piatri-import-hiprog3.jsp';
		}
		
<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.PTHOME.PRO_CUP")}'>
		document.location.href = "${contextPath}/ApriPagina.do?href=piani/cupdati/cupdati-homept.jsp";
</c:if>	
		
-->
</script>
</HEAD>

<BODY onload="setVariables();checkLocation();initPage();">
<c:set var="profilo" value='${sessionScope.profiloAttivo }' />

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
			<TD></TD>
			<TD>
			<table class="contenitore-navbar">
				<tbody>
					<tr>
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
			<TD class="menuazioni">
				<div id="menulaterale"></div>
			</TD>
			<TD class="arealavoro">
			
			<jsp:include page="/WEB-INF/pages/commons/areaPreTitolo.jsp" />
			  
			<div class="contenitore-arealavoro">

			<c:if test='${not empty applicationScope.dataInterruzione}'>
				<table align="center" class="arealayout" style="width: 600">
					<tr>
						<td class="errore-generale">
						<br><br>
						Attenzione:
						<br><br>
						si comunica che per il giorno ${applicationScope.dataInterruzione} dalle ore ${applicationScope.oraInizioInterruzione} alle ore ${applicationScope.oraFineInterruzione}
						<br>&egrave; prevista l'interruzione del servizio per un aggiornamento.
						
						<br>
						<br>Si prega di uscire dall'applicativo prima dell'interruzione.
						<br>Ci scusiamo per i disagi.

						<br>
						<br>Grazie per la collaborazione.
						</td>
					</tr>
				</table>
			</c:if>

		<form name="formfind" action="javascript:trovaProgramma();">
			<table align="left" class="arealayout" style="width: 600">
				<tr>
					<td><div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
							<p>${sessionScope.descProfiloAttivo}</p></td>
						</tr>
						<tr>
							<td valign="middle">
								<br/><br/><div class="titoloInputGoogle">ricerca nei programmi</div>
								<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
					
						<a href="javascript:trovaProgramma()">
						<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova programma" align="top">
						</a>
					</td>
				</tr>
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.INS.PIANI.PIATRI")}'>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:nuovoProgramma(3)"
							title="Crea un nuovo programa">Crea un nuovo programma</a></b>
						</td>
					</tr>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:ImportXML();"
							title="Importa da XML">Importa da XML</a></b></td>
					</tr>
				</c:if>
			</table>
			</form>
			<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="piani/piatri/piatri-lista.jsp"> 
				<input type="hidden" name="entita" value="PIATRI" /> 
				<input type="hidden" name="trovaFrom" value="PIATRI" /> 
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaParameter" value="" /> 
				<input type="hidden" name="risultatiPerPagina" value="20" />
 			</form>
			<form name="listaNuovo" action="${contextPath}/Lista.do" method="post">
				<input type="hidden" name="jspPath" value="/WEB-INF/pages/piani/piatri/piatri-lista.jsp" /> 
				<input type="hidden" name="jspPathTo" value="" /> 
				<input type="hidden" name="activePage" value="" /> 
				<input type="hidden" name="isPopUp" value="0" /> 
				<input type="hidden" name="numeroPopUp" value="" /> 
				<input type="hidden" name="metodo" value="nuovo" /> 
				<input type="hidden" name="entita" value="PIATRI" /> 
				<input type="hidden" name="gestisciProtezioni" value="1" />
			</form>
			<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="piani/piatri/piatri-lista.jsp" />
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
<jsp:include page="/WEB-INF/pages/commons/bloccaRichieste.jsp" />

</BODY>
</HTML>