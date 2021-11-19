
<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Form di ricerca dei tecnici */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true"
	schema="W9" idMaschera="TrovaCentri">

<gene:setString name="titoloMaschera" value="Ricerca Centri di Costo"/>

	<gene:redefineInsert name="corpo">
		<gene:formTrova entita="CENTRICOSTO" gestisciProtezioni="true" lista="w9/centricosto/centricosto-lista.jsp">
			<gene:gruppoCampi idProtezioni="gen">
				<gene:campoTrova campo="CODCENTRO" />
				<gene:campoTrova campo="CODEIN" />
			</gene:gruppoCampi>
		</gene:formTrova>
	</gene:redefineInsert>
</gene:template>
