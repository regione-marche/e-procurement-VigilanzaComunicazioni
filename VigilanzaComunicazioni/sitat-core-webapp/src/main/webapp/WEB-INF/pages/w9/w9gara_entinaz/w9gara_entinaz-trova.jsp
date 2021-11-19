<%
	/*
	 * Created on 15-Mar-2012
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

<gene:template file="ricerca-template.jsp" idMaschera="W9GARA_ENTINAZ-trova"
	gestisciProtezioni="true" schema="W9">
	<gene:setString name="titoloMaschera" value="Ricerca gare" />
	<gene:setString name="entita" value="W9GARA_ENTINAZ" />
	<gene:redefineInsert name="corpo">
		<gene:formTrova entita="W9GARA_ENTINAZ" gestisciProtezioni="true">
			<tr>
				<td colspan="3"><b>Dati della gara</b></td>
			</tr>
			<gene:campoTrova campo="OGGETTO" />
			<gene:campoTrova campo="IDAVGARA" />
			<gene:campoTrova campo="TIPO_APP" />

			<tr>
				<td colspan="3"><b>Dati del lotto</b></td>
			</tr>
			<gene:campoTrova campo="CIG" entita="W9LOTT_ENTINAZ" where="W9LOTT_GARA.CODGARA = W9LOTT_ENTINAZ.CODGARA" />
			<gene:campoTrova campo="OGGETTO" entita="W9LOTT_ENTINAZ" where="W9LOTT_GARA.CODGARA = W9LOTT_ENTINAZ.CODGARA" />

		</gene:formTrova>
	</gene:redefineInsert>

	<gene:javaScript>
	
	</gene:javaScript>

</gene:template>
