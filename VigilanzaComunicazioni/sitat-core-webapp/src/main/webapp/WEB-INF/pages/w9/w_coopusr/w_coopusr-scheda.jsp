
<%
	/*
	 * Created on 04-Nov-2008
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

	// Scheda degli intestatari della concessione stradale
%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" idMaschera="W_COOPUSR-Scheda" schema="W9">
	<c:set var="entita" value="W_COOPUSR" />
	<gene:setString name="titoloMaschera" value='Credenziali servizi di pubblicazione'/>

	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W_COOPUSR" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreFormRichiestaCredenziali"
		plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW_COOPUSR">
			
			<gene:campoScheda campo="ID_RICHIESTA" modificabile='false' />
			<gene:campoScheda campo="STATO" modificabile='false' />
			<gene:redefineInsert name="pulsanteNuovo" />
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="addToAzioni">
				<c:if test="${datiRiga.W_COOPUSR_STATO eq 1 || datiRiga.W_COOPUSR_STATO eq 3}">
					<tr>
						<td class="vocemenulaterale">
							<c:choose>
								<c:when test='${modo eq "VISUALIZZA"}'>
									<a href="javascript:abilita('${datiRiga.W_COOPUSR_ID_RICHIESTA}');" title="Abilita credenziali" tabindex="1504">
									Abilita credenziali
									</a>
								</c:when>
								<c:otherwise>
									Abilita credenziali
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
				<c:if test="${datiRiga.W_COOPUSR_STATO eq 1}">
					<tr>
						<td class="vocemenulaterale">
							<c:choose>
								<c:when test='${modo eq "VISUALIZZA"}'>
									<a href="javascript:rifiuta('${datiRiga.W_COOPUSR_ID_RICHIESTA}');" title="Rifiuta credenziali" tabindex="1505">
									Rifiuta credenziali
									</a>
								</c:when>
								<c:otherwise>
									Rifiuta credenziali
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
				<c:if test="${datiRiga.W_COOPUSR_STATO eq 2}">
					<tr>
						<td class="vocemenulaterale">
							<c:choose>
								<c:when test='${modo eq "VISUALIZZA"}'>
									<a href="javascript:disabilita('${datiRiga.W_COOPUSR_ID_RICHIESTA}');" title="Disabilita credenziali" tabindex="1506">
									Disabilita credenziali
									</a>
								</c:when>
								<c:otherwise>
									Disabilita credenziali
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</gene:redefineInsert>
			<gene:campoScheda campo="DATA_CREAZIONE" modificabile='false' />
			<gene:campoScheda>
				<td colspan="2"><b>Dati amministrazione richiedente</b></td>
			</gene:campoScheda>
			<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
				scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
				schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
				campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="W_COOPUSR_CODEIN" where="" 
				formName="formStazApp">
				<gene:campoScheda campo="CODEIN" visibile="false"/>
				<gene:campoScheda campo="NOMEIN" entita="UFFINT" where="UFFINT.CODEIN = W_COOPUSR.CODEIN"
					modificabile="false" title="Stazione appaltante" />
			</gene:archivio>
			<gene:campoScheda entita="UFFINT" campo="CFEIN"  where="UFFINT.CODEIN=W_COOPUSR.CODEIN" modificabile="false"/>
			<gene:campoScheda entita="UFFINT" campo="EMAI2IN"  where="UFFINT.CODEIN=W_COOPUSR.CODEIN" modificabile="false"/>
			<gene:campoScheda>
				<td colspan="2"><b>Dati Dirigente del servizio richiedente che avrà ruolo di Resp. per la cooperazione</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="DIRIGENTE" obbligatorio="true" />
			<gene:campoScheda campo="SERVIZIO_DIRIGENTE" />
			<gene:campoScheda campo="MAIL_DIRIGENTE" obbligatorio="true" />
			<gene:campoScheda campo="TEL_DIRIGENTE" obbligatorio="true"/>	
			<gene:campoScheda>
				<td colspan="2"><b>Dati del referente tecnico</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="TECNICO" obbligatorio="true" />
			<gene:campoScheda campo="MAIL_TECNICO" obbligatorio="true" />
			<gene:campoScheda campo="TEL_TECNICO" obbligatorio="true"/>
			<gene:campoScheda>
				<td colspan="2"><b>Servizi cooperazione applicativa</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="SERVIZI" visibile="false"/>
			<c:if test="${fn:length(datiServiziSelezionati) > 0}">
				<c:forEach begin="0" end="${fn:length(datiServiziSelezionati)-1}" var="indiceTabServizi">
					<c:set var="valoreServizio" value="${(fn:indexOf(datiRiga.W_COOPUSR_SERVIZI, datiServiziSelezionati[indiceTabServizi][0]) >= 0)? '1' : '2'}" />
					<gene:campoScheda campoFittizio="true" 
						campo="SERVIZIO_${indiceTabServizi+1}"
						title="${datiServiziSelezionati[indiceTabServizi][1]}"
						definizione="T1;0;;SN;" value="${valoreServizio}" />
					<gene:campoScheda campoFittizio="true"
						campo="VALORE_SERVIZIO_${indiceTabServizi+1}"
						title="${datiServiziSelezionati[indiceTabServizi][1]}"
						value="${datiServiziSelezionati[indiceTabServizi][0]}"
						definizione="T20;0;;;" visibile="false" />
				</c:forEach>
			</c:if>	
			<gene:campoScheda>
				<td colspan="2"><b>Credenziali</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CLIENT_ID" modificabile='false'/>
			<gene:campoScheda campo="CLIENT_KEY" modificabile='false' visibile="false"/>
			<gene:campoScheda title="Chiave per l'autenticazione ai servizi di pubblicazione" modificabile='false' campoFittizio="true" campo="CLIENT_KEY_CRIPTATO" definizione="T40" value="**********"/>
			<gene:campoScheda>
				<td colspan="2"><b>Utenza tecnica associata</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="SYSCON" visibile="false"/>	
			<gene:campoScheda entita="USRSYS" campo="SYSLOGIN"  where="USRSYS.SYSCON=W_COOPUSR.SYSCON" modificabile="false"/>
			<gene:campoScheda entita="USRSYS" campo="SYSPWD"  where="USRSYS.SYSCON=W_COOPUSR.SYSCON" modificabile="false" visibile="false"/>
			<c:if test="${empty datiRiga.W_COOPUSR_SYSCON}">
				<gene:campoScheda title="Password" modificabile='false' campoFittizio="true" campo="PASSWORD_CRIPTATO" definizione="T40" value=""/>
			</c:if>
			<c:if test="${!empty datiRiga.W_COOPUSR_SYSCON}">
				<gene:campoScheda title="Password" modificabile='false' campoFittizio="true" campo="PASSWORD_CRIPTATO" definizione="T40" value="**********"/>
			</c:if>
			<gene:campoScheda>
				<td colspan="2"><b>Altri</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="INDIRIZZI_IP" />
			<gene:campoScheda campo="DATA_ABILITAZIONE" modificabile='false'/>
			<gene:campoScheda campo="DATA_DISABILITAZIONE" modificabile='false'/>
			<gene:campoScheda campo="MESSAGGIO"/>
			
			<gene:campoScheda campo="IDPRG" visibile="false" entita="W_DOCDIG" where="${where}" />
			<gene:campoScheda campo="IDDOCDIG" visibile="false" entita="W_DOCDIG" where="${where}" />
			<gene:campoScheda campo="DIGNOMDOC" visibile="false" entita="W_DOCDIG" where="${where}" />
		
			<gene:campoScheda title="Modulo richiesta credenziali" modificabile='false' campo="PDFq" entita="W_COOPUSR" campoFittizio="true" definizione="T200;0;;;" >
				<a href="javascript:visualizzaFileAllegato('${datiRiga.W_DOCDIG_IDPRG}','${datiRiga.W_DOCDIG_IDDOCDIG}', '${datiRiga.W_DOCDIG_DIGNOMDOC}');">
					<img src="${pageContext.request.contextPath}/img/pdf.gif" />
				</a>
			</gene:campoScheda>
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>

		</gene:formScheda>
		
		<form name="formVisFirmaDigitale" id="formVisFirmaDigitale" action="${pageContext.request.contextPath}/ApriPagina.do" method="post">
			<input type="hidden" name="href" value="gene/system/firmadigitale/verifica-firmadigitale.jsp" />
			<input type="hidden" name="idprg" id="idprg" value="" />
			<input type="hidden" name="iddocdig" id="iddocdig" value="" />
		</form>
		
	</gene:redefineInsert>
	<gene:javaScript>
	
	function abilita(key) {
		if (confirm("Vuoi abilitare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?id_richiesta=' + key + '&azione=abilita';
		}
	}
	
	function disabilita(key) {
		if (confirm("Vuoi disabilitare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?id_richiesta=' + key + '&azione=disabilita';
		}
	}
	
	function rifiuta(key) {
		if (confirm("Vuoi rifiutare queste credenziali?")) {
			location.href = '${pageContext.request.contextPath}/w9/GestioneRichiesteCredenziali.do?id_richiesta=' + key + '&azione=rifiuta';
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
