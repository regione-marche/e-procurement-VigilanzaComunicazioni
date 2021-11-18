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

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PIATRI-trova">
	<gene:setString name="titoloMaschera" value="Ricerca programmi"/>
	
	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">
		<gene:redefineInsert name="trovaCreaNuovo" />

  	<% // Creo la form di trova con i campi dell'entità peri %>
  	<gene:formTrova entita="PIATRI" gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati generali</b></td></tr>
			<gene:campoTrova entita="UFFINT" where="UFFINT.CODEIN = PIATRI.CENINT" campo="NOMEIN" title="Denominazione stazione appaltante"/>
			<gene:campoTrova campo="ANNTRI"/>
			<gene:campoTrova campo="BRETRI"/>
		</gene:gruppoCampi>
    </gene:formTrova>    
  </gene:redefineInsert>
</gene:template>
