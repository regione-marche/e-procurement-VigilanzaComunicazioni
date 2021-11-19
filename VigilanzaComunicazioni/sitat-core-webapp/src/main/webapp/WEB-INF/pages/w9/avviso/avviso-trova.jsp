<%/*
       * Created on 26-Feb-2010
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

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="AVVISO-trova">
	<gene:setString name="titoloMaschera" value="Ricerca avvisi"/>
	
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
  	<gene:formTrova entita="AVVISO"  gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati generali avviso</b></td></tr>
			<gene:campoTrova campo="IDAVVISO"/>
			<gene:campoTrova campo="TIPOAVV"/>
			<gene:campoTrova campo="DATAAVV"/>
			<gene:campoTrova campo="DESCRI"/>
			<gene:campoTrova campo="CIG"/>
		</gene:gruppoCampi>
    </gene:formTrova>    
  </gene:redefineInsert>
</gene:template>