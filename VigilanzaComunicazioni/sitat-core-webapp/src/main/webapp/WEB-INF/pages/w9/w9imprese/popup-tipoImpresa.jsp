<%
/*
 * Created on: 18-set-2014
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="listaOpzioniDisponibili" value="${fn:join(opzDisponibili,'#')}#"/>

<div style="width:97%;">

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="W9" 
	idMaschera="W9IMPRESE-scheda">

	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value="Tipologia del soggetto partecipante" />
		<gene:formScheda entita="W9IMPRESE" >

			<gene:campoScheda campo="CODGARA" visibile="false" />
			<gene:campoScheda campo="CODLOTT" visibile="false" />
			<gene:campoScheda campo="NUM_IMPR" visibile="false" />
			<gene:campoScheda campo="NUM_RAGG" visibile="false" />
			
			<gene:campoScheda campo="TIPOAGG" modificabile="true" obbligatorio="true" />

			<gene:campoScheda>
				<td class="comandi-dettaglio" colSpan="2">
					<INPUT type="button"  class="bottone-azione" value='Conferma' title='Conferma' onclick="javascript:conferma()">
					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla"    onclick="javascript:window.close()">
				&nbsp;
				</td>
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>
	
	function conferma() {
		var tipoAgg = getValue("W9IMPRESE_TIPOAGG");
		if (tipoAgg != "") {
		  window.opener.nuovo(tipoAgg);
		  window.close();
		} else {
		  clearMsg();
		  outMsg("Selezionare la tipologia di soggetto partecipante", "ERR");
		  onOffMsg();
		}
	}
	
	</gene:javaScript>
</gene:template>
</div>