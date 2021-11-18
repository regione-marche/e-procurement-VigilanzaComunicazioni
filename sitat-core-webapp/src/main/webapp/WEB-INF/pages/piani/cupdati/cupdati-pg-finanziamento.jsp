
<%
  /*
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:formScheda entita="CUPDATI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreCUPDATI">

	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<gene:campoScheda>
		<td colspan="2"><b>Finanziamento</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="ID" visibile="false" />
	<gene:campoScheda campo="SPONSORIZZAZIONE" >
		<gene:addValue value="" descr="" />
		<gene:addValue value="N" descr="No" />
		<gene:addValue value="P" descr="Parziale" />
		<gene:addValue value="T" descr="Totale" />			
	</gene:campoScheda>
	<gene:campoScheda campo="FINANZA_PROGETTO" >
		<gene:addValue value="" descr="" />
		<gene:addValue value="A" descr="Assistita" />
		<gene:addValue value="N" descr="No" />
		<gene:addValue value="P" descr="Pura" />	
	</gene:campoScheda>
	<gene:campoScheda campo="COSTO" />
	<gene:campoScheda campo="FINANZIAMENTO" />
	<gene:campoScheda>
		<td colspan="2"><b>Tipologia di copertura finanziaria</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="COP_STATALE" />
	<gene:campoScheda campo="COP_REGIONALE" />
	<gene:campoScheda campo="COP_PROVINCIALE" />
	<gene:campoScheda campo="COP_COMUNALE" />
	<gene:campoScheda campo="COP_ALTRAPUBBLICA" />
	<gene:campoScheda campo="COP_COMUNITARIA" />
	<gene:campoScheda campo="COP_PRIVATA" />
	<gene:campoScheda campo="COP_PUBBDACONFERMARE" />
	
 	    
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
