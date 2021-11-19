<%
/*
 * Created on 15-Aprile-2013
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 * 
 * Questa pagina utilizza jquery per impostare valori di default per alcune schede dell'applicativo.
 * I valori di default possono essere diversi a seconda del cliente.
 */


%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:javaScript>
<c:if test='${modoAperturaScheda eq "NUOVO"}'>

	$("#W9APPA_COD_STRUMENTO").val("B02");
	$("#W9APPA_IMPORTO_ATTUAZIONE_SICUREZZA").val("0");
	$("#W9APPA_OPERE_URBANIZ_SCOMPUTO").val("2");
	$("#W9APPA_PROCEDURA_ACC").val("2");
	$("#W9APPA_PREINFORMAZIONE").val("2");
	$("#W9APPA_TERMINE_RIDOTTO").val("2");
	$("#W9APPA_ASTA_ELETTRONICA").val("2");
	$("#W9APPA_FLAG_RICH_SUBAPPALTO").val("2");

	$("#W9INIZ_FLAG_FRAZIONATA").val("1");

	$("#W9PUES_DATA_GURI").val("2");
	$("#W9PUES_DATA_ALBO").val("2");
	$("#W9PUES_QUOTIDIANI_NAZ").val("0");
	$("#W9PUES_PROFILO_COMMITTENTE").val("1");
	$("#W9PUES_SITO_MINISTERO_INF_TRASP").val("2");
	$("#W9PUES_SITO_OSSERVATORIO_CP").val("1");
</c:if>
	$("select[name^='W9FINA_ID_FINANZIAMENTO']").filter('[value=""]').val("B");
</gene:javaScript>

