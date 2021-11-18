
<%
	/*
	 * Created on 20-Ott-2008
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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<c:set var="tipoDB" value="${gene:callFunction('it.eldasoft.gene.tags.utils.functions.GetTipoDBFunction', pageContext)}" />
<c:choose>
	<c:when test='${tipoDB eq "ORA"}' >
		<c:set var="where" value="W_DOCDIG.IDPRG='W9' AND W_DOCDIG.DIGENT='W_COOPUSR' AND W_DOCDIG.DIGKEY1=TO_CHAR(W_COOPUSR.ID_RICHIESTA)"/>
	</c:when>
	<c:when test='${tipoDB eq "MSQ"}' >
		<c:set var="where" value="W_DOCDIG.IDPRG='W9' AND W_DOCDIG.DIGENT='W_COOPUSR' AND W_DOCDIG.DIGKEY1=CONVERT(varchar, W_COOPUSR.ID_RICHIESTA)"/>
	</c:when>
	<c:when test='${tipoDB eq "POS"}' >
		<c:set var="where" value="W_DOCDIG.IDPRG='W9' AND W_DOCDIG.DIGENT='W_COOPUSR' AND W_DOCDIG.DIGKEY1=CAST(W_COOPUSR.ID_RICHIESTA AS text)"/>
	</c:when>
	<c:when test='${tipoDB eq "DB2"}' >
		<c:set var="where" value="W_DOCDIG.IDPRG='W9' AND W_DOCDIG.DIGENT='W_COOPUSR' AND W_DOCDIG.DIGKEY1=trim(char(integer( W_COOPUSR.ID_RICHIESTA )))"/>
	</c:when>
</c:choose>

<gene:template file="lista-template.jsp" gestisciProtezioni="true"
	idMaschera="W_COOPUSR-lista" schema="W9">
	<gene:setString name="titoloMaschera" value="Richieste Credenziali servizi di pubblicazione" />
	<gene:setString name="entita" value="W_COOPUSR" />
	<gene:redefineInsert name="listaNuovo" />
	<gene:redefineInsert name="pulsanteListaInserisci" />
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<table class="lista">
			<tr>
				<td><gene:formLista entita="W_COOPUSR" pagesize="20" sortColumn="2"
					tableclass="datilista" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreFormRichiestaCredenziali">
					<gene:campoLista title="Opzioni <br><center>${titoloMenu}</center>"
						width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
								onClick="chiaveRiga='${chiaveRigaJava}'">
								<gene:PopUpItemResource
									resource="popupmenu.tags.lista.visualizza"
									title="Visualizza" />
								<c:choose>
									<c:when test="${datiRiga.W_COOPUSR_STATO eq 1}">
										<gene:PopUpItem title="Abilita credenziali" href="javascript:abilita('${datiRiga.W_COOPUSR_ID_RICHIESTA}');" />
										<gene:PopUpItem title="Rifiuta credenziali" href="javascript:rifiuta('${datiRiga.W_COOPUSR_ID_RICHIESTA}');" />
										<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina" />
									</c:when>
									<c:when test="${datiRiga.W_COOPUSR_STATO eq 2}">
										<gene:PopUpItem title="Disabilita credenziali" href="javascript:disabilita('${datiRiga.W_COOPUSR_ID_RICHIESTA}');"/>
									</c:when>
									<c:when test="${datiRiga.W_COOPUSR_STATO eq 3}">
										<gene:PopUpItem title="Abilita credenziali" href="javascript:abilita('${datiRiga.W_COOPUSR_ID_RICHIESTA}');"/>
									</c:when>
								</c:choose>
							</gene:PopUp>
							<c:if test="${datiRiga.W_COOPUSR_STATO eq 1}">
								<input type="checkbox" name="keys" value="${chiaveRiga}" />
							</c:if>
						</c:if>
					</gene:campoLista>
					<gene:campoLista campo="ID_RICHIESTA" href="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();"/>
					<gene:campoLista entita="UFFINT" campo="NOMEIN" title="Ente" where="UFFINT.CODEIN=W_COOPUSR.CODEIN"/>
					<gene:campoLista entita="UFFINT" campo="CFEIN" title="Codice Fiscale Ente" where="UFFINT.CODEIN=W_COOPUSR.CODEIN"/>
					<gene:campoLista campo="DATA_CREAZIONE" />
					<gene:campoLista campo="STATO" />
					<gene:campoLista campo="CLIENT_ID"/>
					
					<gene:campoLista campo="IDPRG" visibile="false" entita="W_DOCDIG" where="${where}" />
					<gene:campoLista campo="IDDOCDIG" visibile="false" entita="W_DOCDIG" where="${where}" />
					<gene:campoLista campo="DIGNOMDOC" visibile="false" entita="W_DOCDIG" where="${where}" />
		
					<gene:campoLista title="Modulo richiesta" campo="PDFq" entita="W_COOPUSR" campoFittizio="true" definizione="T200;0;;;" >
						<a href="javascript:visualizzaFileAllegato('${datiRiga.W_DOCDIG_IDPRG}','${datiRiga.W_DOCDIG_IDDOCDIG}', '${datiRiga.W_DOCDIG_DIGNOMDOC}');">
							<img src="${pageContext.request.contextPath}/img/pdf.gif" />
						</a>
					</gene:campoLista>
				
				</gene:formLista></td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
		
		<form name="formVisFirmaDigitale" id="formVisFirmaDigitale" action="${pageContext.request.contextPath}/ApriPagina.do" method="post">
			<input type="hidden" name="href" value="gene/system/firmadigitale/verifica-firmadigitale.jsp" />
			<input type="hidden" name="idprg" id="idprg" value="" />
			<input type="hidden" name="iddocdig" id="iddocdig" value="" />
		</form>
		
	</gene:redefineInsert>
	
	<gene:javaScript>
	
	function abilita(key) {
		if (confirm("Vuoi abilitare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?' + csrfToken + '&id_richiesta=' + key + '&azione=abilita';
		}
	}
	
	function disabilita(key) {
		if (confirm("Vuoi disabilitare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?' + csrfToken + '&id_richiesta=' + key + '&azione=disabilita';
		}
	}
	
	function rifiuta(key) {
		if (confirm("Vuoi rifiutare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?' + csrfToken + '&id_richiesta=' + key + '&azione=rifiuta';
		}
	}
	
	function visualizzaFileAllegato(idprg,iddocdig,dignomdoc) {
		var vet = dignomdoc.split(".");
		var ext = vet[vet.length-1];
		ext = ext.toUpperCase();
		if(ext=='P7M' || ext=='TSD'){
			document.formVisFirmaDigitale.idprg.value = idprg;
			document.formVisFirmaDigitale.iddocdig.value = iddocdig;
			document.formVisFirmaDigitale.submit();
		}else{
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('id_richiesta=' + iddocdig + '&tab=w_coopusr');
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
	}
	</gene:javaScript>

</gene:template>