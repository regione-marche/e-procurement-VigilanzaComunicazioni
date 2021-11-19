<%/*
       * Created on 22-Set-2006
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

<c:set var="tipoDB" value="${gene:callFunction('it.eldasoft.gene.tags.utils.functions.GetTipoDBFunction', pageContext)}" />

	<c:choose>
		<c:when test='${tipoDB eq "ORA"}' >
			<c:set var="campoDatric" value="CAST(DATA_CREAZIONE AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "MSQ"}' >
			<c:set var="campoDatric" value="CONVERT(date, DATA_CREAZIONE)" />
		</c:when>
		<c:when test='${tipoDB eq "POS"}' >
			<c:set var="campoDatric" value="CAST(DATA_CREAZIONE AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "DB2"}' >
			<c:set var="campoDatric" value="DATE(DATA_CREAZIONE)" />
		</c:when>
	</c:choose>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W_COOPUSR-trova">
	<gene:setString name="titoloMaschera" value="Ricerca richieste abilitazioni accesso al servizio di cooperazione applicativa e interoperabilità"/>
	
	<gene:redefineInsert name="trovaCreaNuovo" />
	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">

  	<% // Creo la form di trova con i campi dell'entita' peri %>
  	<gene:formTrova entita="W_COOPUSR" gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati del filtro</b></td></tr>
			<gene:campoTrova campo="ID_RICHIESTA" />
			<gene:campoTrova campo="${campoDatric}" title="Data ricezione richiesta" computed="true" definizione="D;0;;DATA_ELDA;WCOO_DTCRE" />
			<gene:campoTrova campo="STATO"/>
			<gene:campoTrova entita="UFFINT" campo="NOMEIN" title="Denominazione Ente" where="UFFINT.CODEIN=W_COOPUSR.CODEIN"/>
			<gene:campoTrova entita="UFFINT" campo="CFEIN" title="Codice fiscale Ente" where="UFFINT.CODEIN=W_COOPUSR.CODEIN"/>
			<gene:campoTrova campo="DIRIGENTE"/>
			<gene:campoTrova campo="TECNICO"/>
			<gene:campoTrova campo="CLIENT_ID"/>
		</gene:gruppoCampi>
    </gene:formTrova>    
  </gene:redefineInsert>
</gene:template>
