<%
			/*
       * Created on: 16.15 14/03/2007
       *
       * Copyright (c) EldaSoft S.p.A.
       * Tutti i diritti sono riservati.
       *
       * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
       * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
       * aver prima formalizzato un accordo specifico con EldaSoft.
       */
      /*
				Descrizione:
					Selezione del dettaglio delle categoria dell'intervento
					Parametri:
						param.modo 
							Modo di apertura MODIFICA per modifica o VISUALIZZA per visualizzazione
						param.campo 
							Nome del campo in cui vi e' il dettaglio
						param.valore 
							Valore del campo
				Creato da:
					Marco Franceschin
			*/
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.SITAT")}'> 
<jsp:include page="/WEB-INF/pages/commons/bloccaModifica-scheda.jsp">
	<jsp:param name="entita" value="W9LOTT"/>
	<jsp:param name="inputFiltro" value="${key}"/>
	<jsp:param name="filtroCampoEntita" value="codgara=#CODGARA#"/>
</jsp:include>
</c:if>

<c:if test="${not empty param.campoLabel}">
	<c:set var="campoLabel" value="${param.campoLabel}" scope="request" />
</c:if>
<c:choose>
	<c:when test='${empty param.valore}'>
		<c:set var="valore" value="" />
		<c:set var="valoreDescr" value="" />
	</c:when>
	<c:when test='${fn:substring(param.valore,8,9) ne "-"}' >
		<gene:sqlSelect nome="outSel" tipoOut="VectorString" >
			select cpvcod4,cpvdesc from tabcpv 
				where cpvcod='S2020' and cpvcod4 like '${fn:substring( param.valore, 0,8 )}%'
		</gene:sqlSelect>
		<c:set var="valore" value="${outSel[0]}" />
		<c:set var="valoreDescr" value="${outSel[1]}" />
	</c:when>
	<c:otherwise>
		<gene:sqlSelect nome="outSel" tipoOut="VectorString" >
			select cpvcod4,cpvdesc from tabcpv 
				where cpvcod='S2020' and cpvcod4 = '${param.valore}'
		</gene:sqlSelect>
		<c:set var="valore" value="${outSel[0]}" />
		<c:set var="valoreDescr" value="${outSel[1]}" />
	</c:otherwise>
</c:choose>

<c:set var="appartieneRangeCpv" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.VerificaRangeCpvFunction",valore)}' />
<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera" value="Dettaglio codice C.P.V."/>
	<gene:redefineInsert name="corpo">
		<gene:formScheda gestisciProtezioni="false" entita="">
			<gene:campoScheda campo="CPVCOD" nome="CPVCOD" title="Codice C.P.V." definizione="T11" value="${valore}"/>
			<gene:campoScheda campo="CPVDESC" nome="CPVDESC" title="Descrizione C.P.V." definizione="T150" value="${valoreDescr}" visibile="false"/>
			<gene:campoScheda campo="CPVCOD0" nome="CPVCOD0" title="" definizione="T2" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoTABCPV_TABCOD0" 
				value="${fn:substring( valore, 0,2 )}" />
			<gene:campoScheda campo="CPVCOD1" nome="CPVCOD1" title="" definizione="T2" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoTABCPV_TABCOD1"
				value="${fn:substring( valore, 2,4 )}" />
			<gene:campoScheda campo="CPVCOD2" nome="CPVCOD2" title="" definizione="T2" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoTABCPV_TABCOD2" 
				value="${fn:substring( valore, 4,6 )}" />
			<gene:campoScheda campo="CPVCOD3" nome="CPVCOD3" title="" definizione="T2" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoTABCPV_TABCOD3" 
				value="${fn:substring( valore, 6,8 )}" />
			<gene:campoScheda visibile='${(autorizzatoModifiche ne "2") && param.modo eq "MODIFICA"}'>
				<td class="comandi-dettaglio" colSpan="2">	
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Salva modifiche" onclick="javascript:conferma()" />
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla()" />
				</td>			
			</gene:campoScheda>
				
			<%/* Aggiunta dei calcoli sui campi */%>
			<gene:fnJavaScriptScheda funzione='modifyValue(0)' elencocampi="CPVCOD0" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyValue(1)' elencocampi="CPVCOD1" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyValue(2)' elencocampi="CPVCOD2" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyValue(3)' elencocampi="CPVCOD3" esegui="false" />
			
			<gene:fnJavaScriptScheda funzione='reloadCPV()' elencocampi="CPVCOD" esegui="false" />
			
			<!-- gene:fnJavaScriptScheda funzione='calcolaCPV()' elencocampi="CPVCOD0;CPVCOD1;CPVCOD2;CPVCOD3" esegui="false" / -->
		</gene:formScheda>
		
  </gene:redefineInsert>
	<%/* Includo i javascript per la gestione delle classificazione */%>
	<gene:javaScript>
	
		function modifyValue(numcampo) {
			// Sbianco tutti i campi successivi e leggo i valori
			var i;
			for (i = numcampo+1; i < 4; i++) {
				setValue("CPVCOD"+i,"", false);
			}
			calcolaCPV();
		}
		
		function calcolaCPV() {
			var valore = "";
			if (getValue("CPVCOD0") != "") {
				valore += fillCharLeft(getValue("CPVCOD0"),2,"0");
				valore += fillCharLeft(getValue("CPVCOD1"),2,"0");
				valore += fillCharLeft(getValue("CPVCOD2"),2,"0");
				valore += fillCharLeft(getValue("CPVCOD3"),2,"0");
			}
			setValue("CPVCOD",valore);
		}
		
		function reloadCPV() {
			bloccaRichiesteServer();
			document.location.href="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=commons/descriz-codice-cpv.jsp&modo=MODIFICA&campo=${param.campo}&valore="+getValue("CPVCOD")+"&campoRange=${param.campoRange}&campoLabel=${param.campoLabel}&campoEtichetta=${param.campoEtichetta}&campoDescr=${param.campoDescr}&valoreDescr="+getValue("CPVDESC");
		}
		
		function conferma() {
			opener.setValue("${param.campo}",getValue("CPVCOD"));
			opener.setValue("${param.campoDescr}",getValue("CPVDESC"));
			
			var campoRange = "${param.campoRange}";
			var appartiene = "${appartieneRangeCpv }";
			var campoEtichetta = "${param.campoEtichetta}";
			var campoLabel = "${param.campoLabel}";
			var rigoCampoEtichetta = '';
			if (campoRange != '') {
				if (appartiene == 'true') {
					opener.setValue(campoRange,"1");
					if (campoLabel != '') {
						opener.setValue("${param.campoLabel}", "Previsto dall\'ALLEGATO IIA o IIB del DLgs. 163/06");
					}
					if (campoEtichetta != '') {
						rigoCampoEtichetta = 'row'+campoEtichetta;
						opener.showObj(rigoCampoEtichetta, true);
					}
				} else {
					if (campoLabel != '') {
						opener.setValue("${param.campoLabel}", "");
					}
					if (campoEtichetta != '') {
						rigoCampoEtichetta = 'row'+campoEtichetta;
						opener.showObj(rigoCampoEtichetta, false);
					}
				}
			}
			window.close();
		}
		
		function annulla(){
			window.close();
		}

	</gene:javaScript>
</gene:template>

