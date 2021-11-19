
<%/*
       * Created on 18-ott-2007
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"
	schema="W9" idMaschera="W9COLL-scheda">
	<c:set var="key" value='${key}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="flusso" value='5' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="key3" value='${gene:getValCampo(key,"NUM_COLL")}' scope="request" />
	
	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9COLL")}' scope="request" />
	
	<gene:setString name="titoloMaschera" value='${gene:if("NUOVO"==modo, "Nuova Fase di collaudo", "Fase di collaudo")}' />
	
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">

			<gene:pagina title="Dati generali" idProtezioni="DETTFASE">
				<jsp:include page="w9coll-pg-dettaglioFase.jsp" />
			</gene:pagina>
			<gene:pagina title="Incarichi professionali" idProtezioni="TECNI">
				<jsp:include page="w9coll-pg-incarichi.jsp" />
			</gene:pagina>

		</gene:formPagine>

	</gene:redefineInsert>
	<gene:redefineInsert name="addToAzioni">
		<jsp:include page="../commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>
	<gene:javaScript>
		function popupValidazione(flusso,key1,key2,key3) {
	  		var comando = "href=w9/commons/popup-validazione-generale.jsp";
	  		comando = comando + "&flusso=" + flusso;
	  		comando = comando + "&key1=" + key1;
	  		comando = comando + "&key2=" + key2;
	  		comando = comando + "&key3=" + key3;
	 		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	  }
	  
	<c:if test='${modo eq "MODIFICA"}' >
		<c:set var="localkey" value='${key1};${key2};${flusso};${key3}' scope="request" />
		<jsp:include page="/WEB-INF/pages/w9/commons/controlloJsFaseDaReinviare.jsp" />
	</c:if>
	  
</gene:javaScript>
</gene:template>
