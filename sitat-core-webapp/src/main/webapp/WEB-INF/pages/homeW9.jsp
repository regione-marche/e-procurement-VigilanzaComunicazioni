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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="fnucase"
	value='${gene:callFunction("it.eldasoft.gene.tags.utils.functions.GetUpperCaseDBFunction", "")}' />
	
<c:set var="filtroLivelloUtenteW3GARA" value="V_W3GARE.DATA_CONFERMA_GARA IS NULL AND ((V_W3GARE.TIPO_GARA='S' AND V_W3GARE.STATO_SIMOG<>2) OR (V_W3GARE.TIPO_GARA='G' AND V_W3GARE.STATO_SIMOG<>7 AND V_W3GARE.STATO_SIMOG<>6 AND V_W3GARE.STATO_SIMOG<>99)) " />

<c:set var="moduloFabbisogniAttivo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.pt.moduloFabbisogniAttivo")}' />

<gene:setIdPagina schema="W9" maschera="HOMEW9" />

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
		
		function trovaProgramma(){
		  // Eseguo la ricerca della programma
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = "";
			if ((document.getElementById("prog1").checked == true )){
			    where = "PIATRI.TIPROG=1";
			} else if ((document.getElementById("prog2").checked == true )){
			    where = "PIATRI.TIPROG=2";
			}
			if(lsStr!=null && lsStr!=""){
				if (where != "") {
					where += " AND ";
				}
				where += "("; 
				if (!isNaN(lsStr) && lsStr.length == 4) {
					where += "PIATRI.ANNTRI = ? OR ";
					parametri += "N:"+lsStr+";";
				}
				where += "${fnucase}( PIATRI.BRETRI ) like ? OR ${fnucase}( PIATRI.ID ) like ?)";
				parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		//funzione di creazione nuovo programma triennale
		function nuovoProgramma(tiprog) {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=piani/piatri/piatri-scheda.jsp&modo=NUOVO&tiprog='+tiprog;
		}

		<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
		<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
		function elencoProposte() {
	 		alert("Funzione in sviluppo!")	
		}
		
		//funzione di creazione nuovo fabbisogno
		function nuovoFabbisogno(tipint) {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=piani/fabbisogni/fabbisogni-scheda.jsp&modo=NUOVO&tipint='+tipint;
		}
		
		//crea un nuovo programma a partire da un file XML
		function ImportXMLProgrammi() {
			document.location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=piani/piatri/piatri-import-hiprog3.jsp';
		}
		
		//funzione di creazione nuovo programma triennale
		function nuovaGara() {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/w9gara/w9gara-scheda.jsp&modo=NUOVO';
		}
		
		//funzione di Compilazione di un nuovo SmartCIG
		function nuovoSmartCig() {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/w9gara/smartcig-nuovo.jsp';
		}
		
		function trovaGara(){
		  // Eseguo la ricerca della programma
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = " (W9GARA.SITUAZIONE <= 90 OR W9GARA.SITUAZIONE IS NULL)";
			if(lsStr!=null && lsStr!=""){
				where += " AND (((UPPER(W9GARA.OGGETTO) like ? ) OR (UPPER(W9GARA.IDAVGARA) like ? )) OR exists (select 1 from W9LOTT where W9LOTT.CODGARA = W9GARA.CODGARA and (UPPER(W9LOTT.OGGETTO) like ? OR UPPER(W9LOTT.CIG) like ?))) ";
 				parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%;T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			formTrova.href.value="w9/w9gara/w9gara-lista.jsp";
			formTrova.entita.value="W9LOTT.CODGARA=W9GARA.CODGARA";
			formTrova.trovaFrom.value="W9LOTT.CODGARA=W9GARA.CODGARA";
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		//funzione ricerca avanzata
		function ricercaAvanzata(){
			apriPagina.href.value="w9/w9gara/w9gara-trova.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}
							
		function trovaPosta(){
		  // Eseguo la ricerca all interno di la "casella postale"
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = "";
			if(lsStr!=null && lsStr!=""){
				where = "W9INBOX.IDCOMUN in (select W9FLUSSI.IDCOMUN from W9FLUSSI, UFFINT where (W9FLUSSI.CFSA IN (select UFFINT.CFEIN from UFFINT where (${fnucase}(UFFINT.NOMEIN) like ?) OR (${fnucase}(W9FLUSSI.CFSA) like ?))))";
						parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}

		function caricaLottiSIMOG(){
			location.href = '${pageContext.request.contextPath}/w9/ConsultaGara.do?' + csrfToken + '&metodo=inizializza';
		}
		
		function caricaLottiOR() {
			location.href = '${pageContext.request.contextPath}/w9/ImportaDatiDaOR.do?' + csrfToken + '&metodo=inizializza';
		}
		
		function trovaGaraEntinaz() {
			
		 	var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var where = " (1 = 1)";
			var parametri = "";
			var filtroUtente = "";
			if (lsStr!=null && lsStr!="") {
				where = " AND (UPPER(W9GARA_ENTINAZ.OGGETTO) like ? OR exists (select 1 from W9LOTT_ENTINAZ where W9LOTT_ENTINAZ.CODGARA = W9GARA_ENTINAZ.CODGARA and (UPPER(W9LOTT_ENTINAZ.OGGETTO) like ? OR UPPER(W9LOTT_ENTINAZ.CIG) like ?))) ";
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
		
		function apriScadenziario() {
			document.location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/calendario/calendario.jsp';
		}
		
		function visualizzaNotifiche(){
			var obj = document.getElementById("notificheAvvisi");
			if(obj.style.display == "none") {
				obj.style.display="";
			}
			else {
				obj.style.display="none";
			}
		}
		
		function leggiNotifica(codnotifica, indice){
			jQuery.ajax({ url : '${pageContext.request.contextPath}/w9/LeggiNotifica.do', 
				data : 'notecod=' + codnotifica,
				success: function(response){ 
				  if (response == 'success') {
				    jQuery("#leggiNotifica" + indice).attr("src","${contextPath}/img/flag_rosso.gif");
					  jQuery("#leggiNotifica" + indice).attr("title","Notifica letta");
				  }
			  }
			});
		}
		
		function trovaGaraLotto(){
		 	var lsStr=formfind.findstr.value.toUpperCase();
			var where = "";
			var parametri = "";
			var filtroUtenteW3GARA = "${filtroLivelloUtenteW3GARA}";
			if(lsStr!=null && lsStr!=""){
				where += "((${fnucase}( v_w3gare.oggetto) like ?) OR (${fnucase}(v_w3gare.id_gara) like ?) OR (exists (select * from w3lott where v_w3gare.numgara = w3lott.numgara and ((${fnucase}(w3lott.oggetto) like ?) OR (${fnucase}(w3lott.cig) like ?)))))";
				parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%;T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			if (filtroUtenteW3GARA != ""){
			  if (where != "") {	
			  where += " AND ";
			  }
			  where += filtroUtenteW3GARA;
			}
			
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		
		function trovaGaraLottoAvanzata(){
			apriPagina.href.value="w3/v_w3gare/v_w3gare-trova.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}
		
		function nuovaAnagraficaGara(){
			apriPagina.href.value="w3/w3gara/w3gara-nuova.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}
		
		function consultaGaraLotto(){
			apriPagina.href.value="w3/w3gara/w3gara-consulta-gara-lotto.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}
		
		function apriSchedaRUP() {
			/*var key = "USRSYS.SYSCON=N:"+ ${profiloUtente.id};
			document.formSchedaW3USRSYS.key.value=key;
			bloccaRichiesteServer();
			document.formSchedaW3USRSYS.submit();*/
			apriPagina.href.value="w3/w3usrsys/w3usrsys-lista.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}
		
		function trovaAvviso(){
			// Eseguo la ricerca della programma
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = " AVVISO.IDAVVISO<>0 ";
			if(lsStr!=null && lsStr!=""){
				where += " AND ((UPPER(AVVISO.DESCRI) like ? ) OR (UPPER(AVVISO.CIG) like ? )) ";
	 			parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			formTrova.href.value="w9/avviso/avviso-lista.jsp";
			formTrova.entita.value="AVVISO";
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		//funzione ricerca avanzata
		function ricercaAvanzataAvvisi(){
			apriPagina.href.value="w9/avviso/avviso-trova.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}

		//funzione ricerca avanzata
		function ricercaAvanzataFabbisogni(){
			apriPagina.href.value="piani/fabbisogni/fabbisogni-trova.jsp";
			bloccaRichiesteServer();
			apriPagina.submit();
		}

		function nuovoAvviso() {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/avviso/avviso-scheda.jsp&modo=NUOVO';
		}
		
		function trovaEsito(){
			var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
			var parametri = "";
			var where = " W9PUBBLICAZIONI.TIPDOC IN (SELECT ID FROM W9CF_PUBB WHERE TIPO = 'A' OR TIPO = 'E') ";
			if(lsStr!=null && lsStr!=""){
				where += " AND (W9PUBBLICAZIONI.CODGARA IN (SELECT W9GARA.CODGARA FROM W9GARA WHERE (UPPER(W9GARA.OGGETTO) like ?))";
				where += " OR W9PUBBLICAZIONI.CODGARA IN (SELECT W9PUBLOTTO.CODGARA FROM W9PUBBLICAZIONI LEFT JOIN W9PUBLOTTO ON W9PUBBLICAZIONI.CODGARA=W9PUBLOTTO.CODGARA and W9PUBBLICAZIONI.NUM_PUBB=W9PUBLOTTO.NUM_PUBB and W9PUBBLICAZIONI.TIPDOC = (SELECT ID FROM W9CF_PUBB WHERE TIPO = 'A') left join W9LOTT on W9PUBLOTTO.CODGARA=W9LOTT.CODGARA and W9PUBLOTTO.CODLOTT=W9LOTT.CODLOTT WHERE (UPPER(W9LOTT.CIG) like ?)))";
				parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
			}
			formTrova.href.value="w9/esiti/esiti-lista.jsp";
			formTrova.entita.value="W9PUBBLICAZIONI";
			formTrova.trovaAddWhere.value=where;
			formTrova.trovaParameter.value=parametri;
			bloccaRichiesteServer();
			formTrova.submit();
		}
		
		function nuovoEsito() {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/esiti/esito-nuovo.jsp';
		}
		
		function aggiungiAtti() {
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/atti/atti-nuovo.jsp';
		}
	
		function trova(){
			if (document.getElementById("avvisi").checked) {
				trovaAvviso();
			} else if (document.getElementById("bandi").checked) {
				trovaGara();
			} else {
				trovaEsito();
			}
		}
		
		function trovaFabbisogniRds(){
			  // Eseguo la ricerca del fabbisogno
				var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
				var parametri = "";
				var where = "";
				var rbTrova = "";
				<!-- 	//////////////////////////////////////////////	 -->
				<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
				<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
				if ((document.getElementById("fabb1").checked == true )){
					rbTrova = "RDS_rbTrova1";
				    where = "(FABBISOGNI.STATO=1 OR FABBISOGNI.STATO=2 OR FABBISOGNI.STATO=11 OR FABBISOGNI.STATO=12)";
				} else if ((document.getElementById("fabb2").checked == true )){
					rbTrova = "RDS_rbTrova2";
					where = "(FABBISOGNI.STATO=3 OR FABBISOGNI.STATO=4 OR FABBISOGNI.STATO=15 OR FABBISOGNI.STATO=21 OR FABBISOGNI.STATO=22)";
				} else if ((document.getElementById("fabb3").checked == true )){
					rbTrova = "RDS_rbTrova3";
				    where = "(FABBISOGNI.STATO=31 OR FABBISOGNI.STATO=32)";
				}

				if(lsStr!=null && lsStr!=""){
					if (where != "") {
						where += " AND ";
					}
					where += "exists( select 1 from INTTRI where INTTRI.CONTRI = 0 AND INTTRI.CONINT= FABBISOGNI.CONINT AND ( ";
					
					if (!isNaN(lsStr) ) {
						where += "INTTRI.CONINT = ? OR ";
						parametri += "N:"+lsStr+";";
					}
					
					if (!isNaN(lsStr) && lsStr.length == 4) {
						where += "INTTRI.AILINT = ? OR ";
						parametri += "N:"+lsStr+";";
					}
					
					where += "${fnucase}( INTTRI.CODINT ) like ? OR ${fnucase}( INTTRI.DESINT ) like ? ";
					parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
					
					where += " ) )"; 
					
				}
				
				formTrova.href.value="piani/fabbisogni/fabbisogni-lista.jsp?rbTrova="+rbTrova;
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
	  }

		function trovaFabbisogniRaf(){
			  // Eseguo la ricerca dei fabbisogni appr. fin.
				var lsStr = trimStringa(formfind.findstr.value.toUpperCase());
				var parametri = "";
				var where = "";
				var rbTrova = "";
				<!-- 	//////////////////////////////////////////////	 -->
				<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
				<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
				if ((document.getElementById("fabb_apprfin1").checked == true )){
					rbTrova = "RAF_rbTrova1";
				    where = "FABBISOGNI.STATO=3";
				} else if ((document.getElementById("fabb_apprfin2").checked == true )){
					rbTrova = "RAF_rbTrova2";
				    where = "(FABBISOGNI.STATO=4 OR FABBISOGNI.STATO=21 OR FABBISOGNI.STATO=22 OR FABBISOGNI.STATO=31)";
				}
				<!-- 	//////////////////////////////////////////////	 -->
				
				if(lsStr!=null && lsStr!=""){
					if (where != "") {
						where += " AND ";
					}
					where += "exists( select 1 from INTTRI where INTTRI.CONTRI = 0 AND INTTRI.CONINT= FABBISOGNI.CONINT AND ( ";
					
					if (!isNaN(lsStr) ) {
						where += "INTTRI.CONINT = ? OR ";
						parametri += "N:"+lsStr+";";
					}
					
					if (!isNaN(lsStr) && lsStr.length == 4) {
						where += "INTTRI.AILINT = ? OR ";
						parametri += "N:"+lsStr+";";
					}
					
					where += "${fnucase}( INTTRI.CODINT ) like ? OR ${fnucase}( INTTRI.DESINT ) like ? ";
					parametri += "T:%"+lsStr+"%;T:%"+lsStr+"%";
					where += " ) )"; 
				}
				
				formTrova.href.value="piani/fabbisogni/fabbisogni-lista.jsp?rbTrova="+rbTrova;
				formTrova.trovaAddWhere.value=where;
				formTrova.trovaParameter.value=parametri;
				bloccaRichiesteServer();
				formTrova.submit();
	  }
		
		// Visualizzazione del dettaglio
		function listaVisualizzaGaraCheckProv(codgara, prov_dato) {
			var lAction = "${pageContext.request.contextPath}/w9/SchedaANAS.do?PROV_DATO=" + prov_dato;
			emulaListaGara.action = lAction;
			emulaListaGara.key.value = "W9GARA.CODGARA=N:" + codgara;
			emulaListaGara.metodo.value="apri";
			emulaListaGara.activePage.value="0";
			emulaListaGara.submit();
		}

		
<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-ADMIN")}'>
		
		<c:choose>
			<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.GENE.SchedaUffint.Utenti")}'>
				document.location.href = "${contextPath}/ApriPagina.do?" + csrfToken + "&href=gene/uffint/uffint-trova.jsp";
			</c:when>
			<c:otherwise>
				document.location.href = "${contextPath}/ApriPagina.do?" + csrfToken + "&href=gene/uffint/admin-uffint-trova.jsp";
			</c:otherwise>
		</c:choose>
		
</c:if>

<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.RICHIESTA-CREDENZIALI")}'>
	document.location.href = "${contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w_coopusr/w_coopusr-trova.jsp";
</c:if>

<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.ORT-INBX")}'>
		document.location.href = "${contextPath}/ApriPagina.do?" + csrfToken + "&href=w9/w9inbox/w9inbox-home.jsp";
</c:if>

<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-CUP")}'>
		document.location.href = "${contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/cupdati/cupdati-homept.jsp";
</c:if>
		
-->
</script>

<style>
a.tooltips {
  position: relative;
  display: inline;
}
a.tooltips span {
  position: absolute;
  width:400px;
  color: #FFFFFF;
  background: #000000;
  height: 100px;
  line-height: 20px;
  text-align: left;
  visibility: hidden;
  border-radius: 6px;
}
a.tooltips span:after {
  content: '';
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -8px;
  width: 0; height: 0;
  border-top: 8px solid #000000;
  border-right: 8px solid transparent;
  border-left: 8px solid transparent;
}
a:hover.tooltips span {
  visibility: visible;
  opacity: 0.8;
  bottom: 30px;
  left: 50%;
  margin-left: -76px;
  z-index: 999;
}
</style>

</HEAD>

<BODY onload="setVariables();checkLocation();initPage();">
<jsp:include page="/WEB-INF/pages/commons/bloccaRichieste.jsp" />

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
			
			<c:if test='${not empty applicationScope.proxyRTisDown}'>
				<table align="center" class="arealayout" style="width: 600">
					<tr>
						<td class="errore-generale">
						<br><br>
						Attenzione:
						<br><br>
						si comunica che temporaneamente non &egrave; possibile inviare flussi all'osservatorio regionale causa aggiornamenti all'infrastruttura.
						<br>
						<br>Ci scusiamo per i disagi.
						<br>
						<br>Grazie per la collaborazione.
						</td>
					</tr>
				</table>
			</c:if>

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

<c:choose>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>

		<form name="formfind" action="javascript:trovaProgramma();">
			<table align="left" class="arealayout" style="width: 600">
				<tr>
					<td>
						<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
						<p>${sessionScope.descProfiloAttivo}</p>
					</td>
				</tr>				
				
				<tr> 
					<td valign="middle">
						<br/><br/><div class="titoloInputGoogle">Ricerca nei programmi</div>
						<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
						<a href="javascript:trovaProgramma()">
						<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova programma" align="top">
						</a>
					</td>
				</tr>
				<TR id="compartimento">
					<TD><br><br><b>
					<form name="select_programma" action="" method="">
						<input type="radio" name="prog" id="prog1" value="1" onclick="" > Programmi di lavori
						<input type="radio" name="prog" id="prog2" value="2" onclick="" > Programmi di forniture e servizi
						<input type="radio" name="prog" id="prog3" value="0" onclick="" checked> Entrambi
						<br>							
					</form>					
					</b>
					</TD>
				</TR>
				<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO')}">
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:nuovoProgramma(1)"
							title="Crea un nuovo programa di lavori">Crea un nuovo programma di lavori</a></b>
						</td>
					</tr>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:nuovoProgramma(2);"
							title="Crea una nuovo programma di forniture e servizi">Crea un nuovo programma di forniture e servizi</a></b></td>
					</tr>
				</c:if>
				<!-- 	//////////////////////////////////////////////	 -->
				<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
				<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
				<c:if test="${moduloFabbisogniAttivo eq '1'}">
					<tr>
						<td><br><br><b>
<!-- 						<a class="link-generico" href="javascript:elencoProposte()" -->
						<a class="link-generico" href="javascript:ricercaAvanzataFabbisogni()"
							title="Elenco proposte di intervento/acquisto">Elenco proposte di intervento/acquisto</a></b>
						</td>
					</tr>
				</c:if>
				<!-- 	//////////////////////////////////////////////	 -->
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.IMPORT-XML")}' >
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:ImportXMLProgrammi();"
							title="Importa da XML">Importa da XML</a></b></td>
					</tr>
				</c:if>
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.GENE.Homepage-EseguiReport")}' >
					<tr>
						<td><br>
						<br><b>
						<a class="link-generico"
							href="${contextPath}/geneGenric/ListaRicerchePredefinite.do"
							title="Vai alla lista dei report predefiniti">Esegui un report</a></b>
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="left">
						<br><br>
						<br><br>
						<br>	
					</td>
				</tr>
				<tr>
					<td align="left">
						<br><br>
					<p style="color: #707070;">
						<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
					</p>
					</td>
				</tr>
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
	</c:when>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.APPA")}'>

			<form name="formfind" action="javascript:trovaGara();" >
				<table align="left" class="arealayout" style="width: 600">
					<tr>
						<td>
							<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
							<p>${sessionScope.descProfiloAttivo}</p>
						</td>
					</tr>
					<tr> 
						<td valign="middle">
							<br/><br/><div class="titoloInputGoogle">Ricerca in gare</div>
							<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
							<a href="javascript:trovaGara()">
							<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova gara" align="top">
							</a>
						</td>
					</tr>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:ricercaAvanzata()"
							title="Ricerca avanzata su Anagrafica Gare">Ricerca avanzata</a></b>
						</td>
					</tr>

				<c:set var="isUserTecnico" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.ControllUserTecniFunction", pageContext)}' scope="request" />
				
				<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO') 
											&& gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9HOME.LinkRecuperaDaSIMOG')}">
					<tr>
						<td><br><br>
							<b>
								<a class="link-generico" href="javascript:caricaLottiSIMOG();"
									title="Importa da SIMOG/Prendi in carico">Importa da SIMOG/Prendi in carico</a>
							</b>
						</td>
					</tr>
				</c:if>

				<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO') 
											&& gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9HOME.LinkRecuperaDaSITATOR')}">
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:caricaLottiOR();"
							title="Importa gara e lotti da OR">Importa gara e lotti da OR</a></b>
						</td>
					</tr>
				</c:if>

				<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO') 
											&& gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9HOME.LinkCreaNuovaGara')}">
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovaGara();"
								title="Crea una nuova gara">Crea una nuova gara
							</a></b>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO') 
											&& gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9LOTT-SMARTCIG')}">
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovoSmartCig();"
								title="Compilazione di un nuovo SmartCIG">Compilazione di un nuovo SmartCIG
							</a></b>
						</td>
					</tr>
				</c:if>
			
				<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.SCADENZIARIO")}'>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:apriScadenziario();"
							title="Importa da XML">Calendario scadenze</a></b></td>
					</tr>
				</c:if>
				<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.NOTIFICHE-AVVISI")}'>
					<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetNotificheAvvisiFunction" />
					<c:if test='${!empty listaAvvisi}'>
						<tr>
							<td><br><br>
								<table class="arealayout" >
										<tr>
											<td width="48" height="48" style="background-image:url(${pageContext.request.contextPath}/img/info.png);background-repeat:no-repeat;background-size:48px 48px;text-align: center;">
											<a class="link-generico" href="javascript:visualizzaNotifiche();" title="Ci sono delle notifiche da visualizzare"><font color="white"><b>${fn:length(listaAvvisi)}</b></font></a>
											</td>
											<td>&nbsp;</td>
										</tr>
								</table>

								<div id="notificheAvvisi" style="display: none" >
								<br>
								<table class="datilista">
							<c:forEach items="${listaAvvisi}" var="valoriAvvisi" varStatus="status">
								<c:if test="${status.index%2 == 0}">
									<tr>
								</c:if>
								<c:if test="${status.index%2 == 1}">
									<tr class="odd">
								</c:if>
									<td>
										<a href="javascript:leggiNotifica(${valoriAvvisi[0]}, ${status.index});" >
											<img id="leggiNotifica${status.index}" src="${contextPath}/img/flag_verde.gif" title="Segna notifica come letta" >
										</a> &nbsp; ${valoriAvvisi[1]} &nbsp; 

									<c:if test='${! gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
										<c:if test="${valoriAvvisi[5] eq ''}">
											<a class="tooltips" href="${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9gara/w9gara-scheda.jsp&key=W9GARA.CODGARA=N:${valoriAvvisi[4]}" >
										</c:if>
										<c:if test="${valoriAvvisi[5] ne ''}">
											<a class="tooltips" href="${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9lott/w9lott-scheda.jsp&key=W9LOTT.CODGARA=N:${valoriAvvisi[4]};W9LOTT.CODLOTT=N:${valoriAvvisi[5]}" >
										</c:if>
									</c:if>
									
									<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
										<a class="tooltips" href="javascript:listaVisualizzaGaraCheckProv(${valoriAvvisi[4]}, '${valoriAvvisi[6]}')" >
									</c:if>
										${valoriAvvisi[2]}<span><b>Data:</b> ${valoriAvvisi[1]}<br><b>Titolo:</b> ${valoriAvvisi[2]}<br><b>Descrizione:</b> ${valoriAvvisi[3]}</span></a>
									</td>
									
								</tr>
							</c:forEach>
								</table>
								
								</div>
							
							</td>
						</tr>
					</c:if>
				</c:if>
					
					<tr>
						<td align="center">
							<br><br>
							<p style="color: #707070;">
								<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
							</p>
						</td>
					</tr>
				</table>
			</form>
			
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
			<form name="emulaListaGara" action="" method="post">
				<input type="hidden" name="jspPath" value="/WEB-INF/pages/homeW9.jsp" />
				<input type="hidden" name="jspPathTo" value="" />
				<input type="hidden" name="activePage" value="" />
				<input type="hidden" name="isPopUp" value="0" />
				<input type="hidden" name="numeroPopUp" value="" />
				<input type="hidden" name="metodo" value="apri" />
				<input type="hidden" name="entita" value="W9GARA" />
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaAddFrom" value="" />
				<input type="hidden" name="trovaParameter" value="" />
				<input type="hidden" name="key" value="" />
				<input type="hidden" name="pathScheda" value="" />
				<input type="hidden" name="pathSchedaPopUp" value="" />
				<input type="hidden" name="keyParent" value="" />
				<input type="hidden" name="gestore" value="" />
				<input type="hidden" name="pgCorrente" value="0" />
				<input type="hidden" name="pgVaiA" value="" />
				<input type="hidden" name="pgSort" value="" />
				<input type="hidden" name="pgLastValori" value="" />
				<input type="hidden" name="pgLastSort" value="" />
				<input type="hidden" name="risultatiPerPagina" value="" />
				<input type="hidden" name="updateLista" value="0" />
			</form>
		</c:if>			
			
			<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="w9/w9gara/w9gara-lista.jsp"> 
				<input type="hidden" name="entita" value="W9LOTT.CODGARA=W9GARA.CODGARA" /> 
				<input type="hidden" name="trovaFrom" value="W9LOTT.CODGARA=W9GARA.CODGARA" /> 
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaParameter" value="" /> 
				<input type="hidden" name="risultatiPerPagina" value="20" />
 			</form>
			<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="w9/w9gara/w9gara-lista.jsp" />
			</form>
	</c:when>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.ENTINAZ")}'>
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
						<br/><br/><div class="titoloInputGoogle">Ricerca in gare</div>
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
						<td><br>
						<br><b>
						<a class="link-generico" href="javascript:nuovaGaraEntinaz();"
							title="Inserimento nuova anagrafica gara">Inserimento nuova anagrafica gara</a></b></td>
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

	</c:when>
	
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-AVVISI")}'>

			<form name="formfind" action="javascript:trovaAvviso();" >
				<table align="left" class="arealayout" style="width: 600">
					<tr>
						<td>
							<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
							<p>${sessionScope.descProfiloAttivo}</p>
						</td>
					</tr>
					<tr> 
						<td valign="middle">
							<br/><br/><div class="titoloInputGoogle">Ricerca in avvisi</div>
							<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
							<a href="javascript:trovaAvviso()">
							<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova avviso" align="top">
							</a>
						</td>
					</tr>
					<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovoAvviso();"
								title="Compilazione di un nuovo avviso">Compilazione di un nuovo avviso
							</a></b>
						</td>
					</tr>
					</c:if>
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:ricercaAvanzataAvvisi()"
							title="Ricerca avanzata su Anagrafica Avvisi">Ricerca avanzata</a></b>
						</td>
					</tr>

					<tr>
						<td align="center">
							<br><br>
							<p style="color: #707070;">
								<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
							</p>
						</td>
					</tr>
				</table>
			</form>
			<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="w9/avviso/avviso-lista.jsp"> 
				<input type="hidden" name="entita" value="AVVISO" /> 
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaParameter" value="" /> 
				<input type="hidden" name="risultatiPerPagina" value="20" />
 			</form>
			<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="w9/avviso/avviso-lista.jsp" />
			</form>
	</c:when>
	
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-ESITI")}'>

			<form name="formfind" action="javascript:trova();" >
				<table align="left" class="arealayout" style="width: 600">
					<tr>
						<td>
							<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
							<p>${sessionScope.descProfiloAttivo}</p>
						</td>
					</tr>
					<tr> 
						<td valign="middle">
							<br/><br/><div class="titoloInputGoogle">Ricerca per CIG, Numero gara ANAC, oggetto procedura di affidamento, esito, avviso</div>
							<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
							<a href="javascript:trova()">
							<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova esito" align="top">
							</a>
						</td>
					</tr>
					<TR id="compartimento">
						<TD><br><br><b>
							<input type="radio" name="ricerca" id="avvisi" value="0" onclick="" ${gene:if(tipoRicerca eq "avvisi","checked","")}> Avvisi
							<input type="radio" name="ricerca" id="bandi" value="1" onclick="" ${gene:if(empty tipoRicerca or tipoRicerca eq "bandi","checked","")}> Procedure di affidamento
							<input type="radio" name="ricerca" id="esiti" value="2" onclick="" ${gene:if(tipoRicerca eq "esiti","checked","")}> Esiti
							<br>							
						</b>
						</TD>
					</TR>
					<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovoAvviso();"
								title="Compilazione di un nuovo avviso">Compilazione di un nuovo avviso
							</a></b>
						</td>
					</tr>
					<c:if test="${gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9LOTT-SMARTCIG')}">
						<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovoSmartCig();"
								title="Compilazione di una procedura con SmartCIG">Compilazione di una procedura con SmartCIG
							</a></b>
						</td>
						</tr>
					</c:if>
					
					<c:set var="isUserTecnico" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.ControllUserTecniFunction", pageContext)}' scope="request" />
				
					<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO') 
						&& gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9HOME.LinkRecuperaDaSIMOG')}">
					<tr>
						<td><br><br>
							<b>
							<a class="link-generico" href="javascript:caricaLottiSIMOG();"
							title="Compilazione di una procedura con CIG">Compilazione di una procedura con CIG</a>
							</b>
						</td>
					</tr>
					</c:if>
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:nuovoEsito();"
								title="Compilazione di un esito con CIG o SmartCIG">Compilazione di un esito con CIG o SmartCIG
							</a></b>
						</td>
					</tr>
					<tr>
						<td><br><br><b>
							<a class="link-generico" href="javascript:aggiungiAtti();"
								title="Aggiungi atti e documenti ex art. 29 DLgs 50/2016">Aggiungi atti e documenti ex art. 29 DLgs 50/2016
							</a></b>
						</td>
					</tr>
					</c:if>
					<tr>
						<td align="center">
							<br><br>
							<p style="color: #707070;">
								<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
							</p>
						</td>
					</tr>
				</table>
			</form>
			<form name="formTrova" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value=""> 
				<input type="hidden" name="entita" value="" /> 
				<input type="hidden" name="trovaFrom" value="" />
				<input type="hidden" name="trovaAddWhere" value="" />
				<input type="hidden" name="trovaParameter" value="" /> 
				<input type="hidden" name="risultatiPerPagina" value="20" />
 			</form>
			<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
				<input type="hidden" name="href" value="" />
			</form>
	</c:when>
	
	
	<c:when test='${gene:checkProt(pageContext,"MENU.VIS.SIMOG-CIG")}'>
							<form name="formfind" action="javascript:trovaGaraLotto();">
								<table align="left" class="arealayout" style="width: 500">
									<tr>
										<td><div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
										<p>${sessionScope.descProfiloAttivo}</p></td>
									</tr>
									<tr>
										<td valign="middle">
											<br/><br/><div class="titoloInputGoogle">ricerca gare e lotti</div>
											<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
											<a href="javascript:trovaGaraLotto();">
											<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Ricerca gare e lotti" align="top">
											</a>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color: #707070;">
												<i>* Questa ricerca rapida considera solamente le gare non ancora pubblicate</i>
											</span>
										</td>
									</tr>
							
									<c:if test='${gene:checkProt(pageContext, "SUBMENU.VIS.SIMOG-CIG.Trova-gare-lotti")}'>
										<tr>
											<td><br><br>
											<b>
											<a class="link-generico" href="javascript:trovaGaraLottoAvanzata()"
												title="Ricerca avanzata delle gare e dei lotti">Ricerca avanzata delle gare e dei lotti</a>
											</b>
											</td>
										</tr>
									</c:if>
									
									
									<c:if test='${gene:checkProtFunz(pageContext,"INS","SCHEDANUOVO")}'>
										<tr>
											<td><br>
											<br><b>
											<a class="link-generico" href="javascript:nuovaAnagraficaGara();"
												title="Crea nuova anagrafica gara">Crea nuova anagrafica gara</a></b></td>
										</tr>
									</c:if>
						
									<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W3.richiesteSIMOG")}'>
										<tr>
											<td><br><br>
											<b>
											<a class="link-generico" href="javascript:consultaGaraLotto()"
												title="Recupera anagrafiche gara e lotto da SIMOG">Recupera anagrafiche gara e lotto da SIMOG</a>
											</b>
											</td>
										</tr>
									</c:if>
									<tr>
										<td><br><br>
											<b>
											<a class="link-generico" href="javascript:apriSchedaRUP()"
												title="Collega/Importa RUP e Centri di Costo da SIMOG">Collega/Importa RUP e Centri di Costo da SIMOG</a>
											</b>
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
								<input type="hidden" name="href" value="w3/v_w3gare/v_w3gare-lista.jsp" /> 
								<input type="hidden" name="entita" value="V_W3GARE" /> 
								<input type="hidden" name="trovaAddWhere" value="" />
								<input type="hidden" name="trovaParameter" value="" /> 
								<input type="hidden" name="risultatiPerPagina" value="20" />
				 			</form>
							<form name="listaNuovo" action="${contextPath}/Lista.do" method="post">
								<input type="hidden" name="jspPath" value="/WEB-INF/pages/w3/w3gara/w3gara-lista.jsp" /> 
								<input type="hidden" name="jspPathTo" value="" /> 
								<input type="hidden" name="activePage" value="" /> 
								<input type="hidden" name="isPopUp" value="0" /> 
								<input type="hidden" name="numeroPopUp" value="" /> 
								<input type="hidden" name="metodo" value="nuovo" /> 
								<input type="hidden" name="entita" value="W3GARA" /> 
								<input type="hidden" name="gestisciProtezioni" value="1" />
							</form>
							<form name="apriPagina" action="${contextPath}/ApriPagina.do" method="post">
								<input type="hidden" name="href" value="w3/w3gara/w3gara-lista.jsp" />
							</form>
							<form name="formSchedaW3USRSYS" action="${pageContext.request.contextPath}/ApriPagina.do" method="post">
								<input type="hidden" name="href" value="w3/w3usrsys/w3usrsys-scheda.jsp" /> 
								<input type="hidden" name="entita" value="USRSYS" />
								<input type="hidden" name="key" value="" />
								<input type="hidden" name="metodo" value="apri" />
								<input type="hidden" name="activePage" value="0" />
							</form>
	</c:when>
	
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>

		<form name="formfind" action="javascript:trovaFabbisogniRds();">
			<table align="left" class="arealayout" style="width: 600">
				<tr>
					<td>
						<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
						<p>${sessionScope.descProfiloAttivo}</p>
					</td>
				</tr>				
				
				<tr> 
					<td valign="middle">
						<br/><br/><div class="titoloInputGoogle">Ricerca nelle proposte</div>
						<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
						<a href="javascript:trovaFabbisogniRds()">
						<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova programma" align="top">
						</a>
					</td>
				</tr>
				<TR id="compartimento">
					<TD><br><br><b>
					<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
					<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
					<form name="select_fabbisogni" action="" method="">
						<input type="radio" name="fabb" id="fabb1" value="1" onclick="" checked> Proposte in lavorazione
						<input type="radio" name="fabb" id="fabb2" value="2" onclick="" > Proposte approvate o in approvazione
						<input type="radio" name="fabb" id="fabb3" value="3" onclick="" > Proposte respinte
						<br>							
					</form>					
					</b>
					</TD>
				</TR>
				
				
						<c:if test="${gene:checkProtFunz(pageContext,'INS','SCHEDANUOVO')}">
							<tr>
								<td><br><br><b>
								<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
								<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
								<a class="link-generico" href="javascript:nuovoFabbisogno(1)"
									title="Crea nuova proposta intervento">Crea nuova proposta intervento</a></b>
								</td>
							</tr>
							<tr>
								<td><br><br><b>
								<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
								<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
								<a class="link-generico" href="javascript:nuovoFabbisogno(2);"
									title="Crea nuova proposta acquisto">Crea nuova proposta acquisto</a></b></td>
							</tr>
						</c:if>
						<tr>
								<td><br><br><b>
								<a class="link-generico" href="javascript:ricercaAvanzataFabbisogni()"
									title="Ricerca avanzata su proposte">Ricerca avanzata</a></b>
								</td>
						</tr>
				
				
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.IMPORT-XML")}' >
					<tr>
						<td><br><br><b>
						<a class="link-generico" href="javascript:ImportXMLProgrammi();"
							title="Importa da XML">Importa da XML</a></b></td>
					</tr>
				</c:if>
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.GENE.Homepage-EseguiReport")}' >
					<tr>
						<td><br>
						<br><b>
						<a class="link-generico"
							href="${contextPath}/geneGenric/ListaRicerchePredefinite.do"
							title="Vai alla lista dei report predefiniti">Esegui un report</a></b>
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="left">
						<br><br>
						<br><br>
						<br>	
					</td>
				</tr>
				<tr>
					<td align="left">
						<br><br>
					<p style="color: #707070;">
						<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
					</p>
					</td>
				</tr>
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
	</c:when>

	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN")}'>
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	

		<form name="formfind" action="javascript:trovaFabbisogniRaf();">
			<table align="left" class="arealayout" style="width: 600">
				<tr>
					<td>
						<div class="margin0px titolomaschera">${sessionScope.nomeProfiloAttivo}</div>
						<p>${sessionScope.descProfiloAttivo}</p>
					</td>
				</tr>				
				
				<tr> 
					<td valign="middle">
						<br/><br/><div class="titoloInputGoogle">Ricerca nelle proposte</div>
						<input type="text" name="findstr" size="50" class="testo-cerca-oggetto" align="top" />
						<a href="javascript:trovaFabbisogniRaf()">
						<img src="${contextPath}/img/${applicationScope.pathImg}trova_oggetto.png" alt="Trova programma" align="top">
						</a>
					</td>
				</tr>
				<TR id="compartimento">
					<TD><br><br><b>
					<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
					<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->	
					<form name="select_fabbisogni" action="" method="">
						<input type="radio" name="fabb_apprfin" id="fabb_apprfin1" value="1" onclick="" checked> Proposte  da approvare 
						<input type="radio" name="fabb_apprfin" id="fabb_apprfin2" value="2" onclick="" > Proposte valutate 
<!-- 						<input type="radio" name="fabb_apprfin" id="fabb_apprfin3" value="3" onclick="" > Fabbisogni rifiutati -->
						<br>							
					</form>					
					</b>
					</TD>
				</TR>
				
				
						<tr>
								<td><br><br><b>
								<a class="link-generico" href="javascript:ricercaAvanzataFabbisogni()"
									title="Ricerca avanzata su proposte">Ricerca avanzata</a></b>
								</td>
						</tr>
				
				
				<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.GENE.Homepage-EseguiReport")}' >
					<tr>
						<td><br>
						<br><b>
						<a class="link-generico"
							href="${contextPath}/geneGenric/ListaRicerchePredefinite.do"
							title="Vai alla lista dei report predefiniti">Esegui un report</a></b>
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="left">
						<br><br>
						<br><br>
						<br>	
					</td>
				</tr>
				<tr>
					<td align="left">
						<br><br>
					<p style="color: #707070;">
						<jsp:include page="/WEB-INF/pages/gene/login/infoCustom.jsp"/>					
					</p>
					</td>
				</tr>
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
	</c:when>
	
						
</c:choose>


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