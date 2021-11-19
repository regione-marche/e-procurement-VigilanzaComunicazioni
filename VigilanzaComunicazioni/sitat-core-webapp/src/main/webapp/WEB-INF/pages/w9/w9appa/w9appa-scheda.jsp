<%
	/*
	 * Created on 25-set-2009
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice so	<!--
		a04 tipoAggi = RR
		a05 tipoAggi = PA
		a21 tipoAggi = RQ
		-->rgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9APPA-scheda">

	<c:if test='${param.keyW9APPA ne null}'>
		<c:set var="reindi" value="${param.keyW9APPA}" />
	</c:if>
	<c:if test='${empty reindi}'>
		<c:set var="reindi" value="${fn:toUpperCase(param.jspPath)}" />
	</c:if>	

	<c:if test='${reindi ne null}'>
	<c:choose>
		<c:when test='${fn:indexOf(reindi, "A04") >= "0"}'>
			<!-- Fase semplificata di aggiudicazione/affidamento -->
			<c:set var="keyTypeScheda" value='a04' scope="request" />
			<c:set var="keyFlusso" value='987' scope="request" />
			<c:set var="keyW9APPA" value='W9APPA-A04' scope="request" />
			<c:set var="keyTitoloNew" value="Nuova Fase semplificata di aggiudicazione/affidamento" scope="request" />
			<c:set var="keyTitolo" value="Fase semplificata di aggiudicazione/affidamento" scope="request" />
			<c:set var="datiBase" value="Dati aggiudicazione" scope="request" />
			<c:set var="datiBaseIdProtezioni" value="ADESIONE" scope="request" />
			<c:set var="datiBaseIncludePage" value="w9appa-pg-dettaglioFase-a04.jsp" scope="request" />
			<c:set var="protezioneIncarichi" value="INCAPROF" scope="request" />	
			<c:set var="paramTipoAggi" value="RR" scope="request" />
		</c:when>
		<c:when test='${fn:indexOf(reindi, "A05") >= "0"}'>
			<!-- Fase di aggiudicazione/affidamento -->
			<c:set var="keyTypeScheda" value='a05' scope="request" />
			<c:set var="keyFlusso" value='1' scope="request" />
			<c:set var="keyW9APPA" value='W9APPA-A05' scope="request" />
			<c:set var="keyTitoloNew" value="Nuova Fase di aggiudicazione/affidamento" scope="request" />
			<c:set var="keyTitolo" value="Fase di aggiudicazione/affidamento" scope="request" />
			<c:set var="datiBase" value="Dati aggiudicazione" scope="request" />
			<c:set var="datiBaseIdProtezioni" value="DETTFASE" scope="request" />
			<c:set var="datiBaseIncludePage" value="w9appa-pg-dettaglioFase-a05.jsp" scope="request" />
			<c:set var="protezioneIncarichi" value="TECNI" scope="request" />	
			<c:set var="paramTipoAggi" value="PA" scope="request" />
		</c:when>
		<c:when test='${fn:indexOf(reindi, "A21") >= "0"}'>
			<!-- Fase di adesione accordo quadro -->
			<c:set var="keyTypeScheda" value='a21' scope="request" />
			<c:set var="keyFlusso" value='12' scope="request" />
			<c:set var="keyW9APPA" value='W9APPA-A21' scope="request" />
			<c:set var="keyTitoloNew" value="Nuova Fase di adesione accordo quadro" scope="request" />
			<c:set var="keyTitolo" value="Fase di adesione accordo quadro" scope="request" />
			<c:set var="datiBase" value="Dati adesione" scope="request" />
			<c:set var="datiBaseIdProtezioni" value="ADESIONE" scope="request" />
			<c:set var="datiBaseIncludePage" value="w9appa-pg-dettaglioFase-a21.jsp" scope="request" />
			<c:set var="protezioneIncarichi" value="TECNI" scope="request" />	
			<c:set var="paramTipoAggi" value="RQ" scope="request" />	
		</c:when>
	</c:choose>

	<c:set var="key" value='${key}' scope="request" />
	<c:set var="flusso" value='${keyFlusso}' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='${gene:getValCampo(key,"NUM_APPA")}' scope="request" />

	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,keyW9APPA)}' scope="request" />

	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, keyTitoloNew, keyTitolo)}' />
	
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true"> 
			<gene:pagina title='${datiBase}' idProtezioni='${datiBaseIdProtezioni}'>
				<jsp:include page='${datiBaseIncludePage}'>
					<jsp:param name="datiBaseIdProtezioni" value='${datiBaseIdProtezioni}'/>
				</jsp:include>
			</gene:pagina>
			<gene:pagina title="Imprese aggiudicatarie / affidatarie" idProtezioni="IMPR">
				<!-- qui andrebbe inserita la lista delle imprese aggiudicanti -->	
				<jsp:include page="w9appa-pg-imprese.jsp" />
			</gene:pagina>
			<gene:pagina title="Incarichi professionali" idProtezioni='{protezioneIncarichi}'>	
				<jsp:include page="w9appa-pg-incarichi.jsp" />
			</gene:pagina>
			<c:if test='${fn:indexOf(reindi, "a04") < "0" && aggiudicazioneSelezionata eq 1}'>	
				<gene:pagina title="Finanziamenti" idProtezioni="FINA">
					<jsp:include page="w9appa-pg-finanziamenti-a05.jsp" />
				</gene:pagina>
			</c:if>
		</gene:formPagine>

	</gene:redefineInsert>

	<gene:javaScript>   
		document.pagineForm.action=document.pagineForm.action + "?keyW9APPA=${keyW9APPA}";	
		
		document.forms[0].action=document.forms[0].action + "?keyW9APPA=${keyW9APPA}";
			
		function popupValidazione(flusso,key1,key2,key3) {
	  		var comando = "href=w9/commons/popup-validazione-generale.jsp";
	  		comando = comando + "&flusso=" + flusso;
	  		comando = comando + "&key1=" + key1;
	  		comando = comando + "&key2=" + key2;
	  		comando = comando + "&key3=" + key3;
	 		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	  	}
	  	
 	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${key1};${key2};${keyFlusso};${key3}' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>
	  	
	</gene:javaScript>
	</c:if>
</gene:template>
