
<%
	/*
	 * Created on: 04/08/2009
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


<gene:formScheda entita="W9GARA" gestisciProtezioni="true"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARA"
	where='W9GARA.CODGARA=#W9GARA.CODGARA#'>
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<gene:redefineInsert name="addToAzioni" >
		<jsp:include page="azioniComuni-schedaGara.jsp" />
	</gene:redefineInsert>

	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda campo="CODGARA"  visibile="false" />
		<gene:campoScheda campo="CODGARA"  entita="W9PUBB" where="W9PUBB.CODGARA=W9GARA.CODGARA and W9PUBB.CODLOTT=1 AND W9PUBB.NUM_APPA = 1 AND W9PUBB.NUM_PUBB = 1" visibile="false" />
		<gene:campoScheda campo="CODLOTT"  entita="W9PUBB" visibile="false"/>
		<gene:campoScheda campo="NUM_APPA" entita="W9PUBB" visibile="false" />
		<gene:campoScheda campo="NUM_PUBB" entita="W9PUBB" visibile="false" />
		<gene:campoScheda campo="DATA_GUCE" entita="W9PUBB" />
		<gene:campoScheda campo="DATA_GURI" entita="W9PUBB" />
		<gene:campoScheda campo="DATA_ALBO" entita="W9PUBB"	/>
		<gene:campoScheda campo="QUOTIDIANI_NAZ" entita="W9PUBB" />
		<gene:campoScheda campo="QUOTIDIANI_REG" entita="W9PUBB" />
		<gene:campoScheda campo="PROFILO_COMMITTENTE" entita="W9PUBB" />
		<gene:campoScheda campo="SITO_MINISTERO_INF_TRASP" entita="W9PUBB" />
		<gene:campoScheda campo="SITO_OSSERVATORIO_CP" entita="W9PUBB" />
		<gene:campoScheda campo="DATA_BORE" entita="W9PUBB"	/>
		<gene:campoScheda campo="PERIODICI" entita="W9PUBB" />
		
	</gene:gruppoCampi>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

