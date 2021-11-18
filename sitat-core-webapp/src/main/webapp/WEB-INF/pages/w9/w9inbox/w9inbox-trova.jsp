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
			<c:set var="campoDatric" value="CAST(DATRIC AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "MSQ"}' >
			<c:set var="campoDatric" value="CONVERT(date, DATRIC)" />
		</c:when>
		<c:when test='${tipoDB eq "POS"}' >
			<c:set var="campoDatric" value="CAST(DATRIC AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "DB2"}' >
			<c:set var="campoDatric" value="DATE(DATRIC)" />
		</c:when>
	</c:choose>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9INBOX-trova">
	<gene:setString name="titoloMaschera" value="Ricerca"/>
	
	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">

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
					<br>Grazie per la collaborazione.<br><br>
					
					</td>
				</tr>
			</table>
		</c:if>
	
  	<% // Creo la form di trova con i campi dell'entita' peri %>
  	<gene:formTrova entita="V_W9INBOX" gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati del filtro</b></td></tr>
			<gene:campoTrova campo="NOMEIN" title="Stazione appaltante" />
			<gene:campoTrova campo="STACOM"/>
			<gene:campoTrova campo="${campoDatric}" title="Data ricezione comunicazione" computed="true" definizione="D;0;;DATA_ELDA;W9IBDTRIC" />
			<gene:campoTrova campo="AREA"/>
			<gene:campoTrova campo="KEY03"/>
			<gene:campoTrova campo="CODOGG"/>
	
		</gene:gruppoCampi>
    </gene:formTrova>    
  </gene:redefineInsert>
</gene:template>
