<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

 /* Lista popup di selezione del tecnico */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="filtroUffint" value=""/> 
<c:if test="${!empty sessionScope.uffint}">
	<c:set var="filtroUffint" value="CGENTEI = '${sessionScope.uffint}'"/>
</c:if>


<c:set var="interventi" value="${param.interventi}" />

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="lista-tecni-popup">
	<gene:setString name="titoloMaschera" value="Selezione del tecnico"/>
	<gene:redefineInsert name="corpo">

		<gene:formLista pagesize="25" tableclass="datilista" entita="TECNI" sortColumn="2" gestisciProtezioni="true" where="${filtroUffint}">
			<gene:campoLista campo="CODTEC" visibile="false"/>
			<gene:campoLista campo="NOMTEC" headerClass="sortable" href="javascript:CambiaRUP('${datiRiga.TECNI_CODTEC}','${datiRiga.TECNI_NOMTEC}')"/>
			<gene:campoLista campo="CFTEC" headerClass="sortable" width="120"/>
			<gene:campoLista campo="PIVATEC" headerClass="sortable" width="120"/>
		</gene:formLista>

		<form action="${contextPath}/piani/CambiaRUP.do?${csrfToken}" method="post" name="formCambiaRup" >
			<input type="hidden" name="interventi" value="${param.interventi}" />
			<input type="hidden" name="newRup" value="" />
		</form>
		
	</gene:redefineInsert>
  <gene:javaScript>
	
		function Annulla(){
			window.close();
		}
		
		// Modifica alla funzione che si sposta ad una determinata pagina
		function listaVaiAPagina(numpg){	
			document.forms[0].action = document.forms[0].action + "&interventi=" + "${interventi}";		
			document.forms[0].metodo.value="leggi";
			document.forms[0].pgVaiA.value=numpg;
			document.forms[0].key.value=document.forms[0].keyParent.value;	
			document.forms[0].submit();
		}
		// Modifica alla funzione che esegue l'ordinamento su una determinata colonna (gestito nella formListaTag)
		function listaOrdinaPer(campo){
			document.forms[0].action = document.forms[0].action + "&interventi=" + "${interventi}";		
			document.forms[0].metodo.value="leggi";
			document.forms[0].pgVaiA.value=0;
			document.forms[0].pgSort.value=campo;
			if (document.pagineForm)
				document.forms[0].entita.value=document.pagineForm.entita.value;
			document.forms[0].key.value=document.forms[0].keyParent.value;
			document.forms[0].submit();
		}
		
		
		
		function CambiaRUP(codiceRUP, nomeTec) {
			if (confirm("Hai scelto " + nomeTec + ". Vuoi procedere alla sostituzione?")) {
				document.formCambiaRup.newRup.value = codiceRUP;
				document.formCambiaRup.submit();
				bloccaRichiesteServer();
			}
		}
		
  </gene:javaScript>
</gene:template>
