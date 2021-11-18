<%
/*
 * Created on: 16/04/2013
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<gene:template file="scheda-template.jsp">
<gene:setString name="titoloMaschera" value='Popolamento massivo schede lotto - Anagrafica lotto' />

<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:Conferma();" title="Conferma" tabindex="1503">Conferma</a>
		</td>
	</tr>
</gene:redefineInsert>

<gene:redefineInsert name="corpo">
<form method="post" name="formSchedaAnagraficaLotto" action="${pageContext.request.contextPath}/w9/SceltaTipoScheda.do">
	<table class="dettaglio-notab">
		<gene:campoScheda>
			<td colspan="2"><b>Anagrafica lotto</b></td>
		</gene:campoScheda>
		<c:if test="${fn:length(datiTabellatoFornList) > 0}">
			<c:forEach begin="0" end="${fn:length(datiTabellatoFornList)-1}" var="indiceTabForn">
				<c:set var="valoreForn" value="0" />
				<c:if test='${modo ne "NUOVO"}'>
					<c:set var="valoreForn"
						value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.VerificaCondizioneDaTabellatoFunction", pageContext, keyW9LOTT, datiTabellatoFornList[indiceTabForn][0], "W9APPAFORN")}'></c:set>
				</c:if>
				<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
					campo="ID_APPALTO_${indiceTabForn+1}"
					title="${datiTabellatoFornList[indiceTabForn][1]}"
					definizione="N5;0;;SN;" value="${valoreForn}"
					gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNoSenzaNull" />
				<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
					campo="VALORE_ID_APPALTO_${indiceTabForn+1}"
					title="${datiTabellatoFornList[indiceTabForn][1]}"
					value="${datiTabellatoFornList[indiceTabForn][0]}"
					definizione="N5;0;;;" visibile="false" />
			</c:forEach>
		</c:if>
		
		
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Conferma" title="Conferma" class="bottone-azione" onclick="javascript:Conferma();"/>&nbsp;
			</td>
		</tr>
	</table>
</form>

</gene:redefineInsert>

<gene:javaScript>

	function Conferma(){
		if(document.formImportfile.selezioneFile) {
			var nomeCompletoFile = "" + document.formImportfile.selezioneFile.value;
			if(nomeCompletoFile != ""){
				if(nomeCompletoFile.substr(nomeCompletoFile.lastIndexOf('.')+1).toUpperCase() == "XLS") {
					bloccaRichiesteServer();
					setTimeout("document.formImportfile.submit()", 150);
				} else {
					alert("Il file selezionato non &egrave; nel formato valido. Deve avere estensione XLS");
					document.formImportfile.selezioneFile.value = "";
				}
			}
			if (document.formImportfile.selezioneFile.value == "") {
				alert("Deve essere indicato il file da importare.");				
			}
		}
	}


</gene:javaScript>

</gene:template>

