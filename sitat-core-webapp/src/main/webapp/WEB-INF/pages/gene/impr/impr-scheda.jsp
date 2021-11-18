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
%>


<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test='${modo eq "VISUALIZZA" or modo eq "MODIFICA" or empty modo}' >
	<gene:sqlSelect nome="tipoImpresa" parametri='${key}' tipoOut="VectorString" >
		select TIPIMP from IMPR where CODIMP = #IMPR.CODIMP#
	</gene:sqlSelect>
</c:if>
<c:set var="esisteElenchiOperatori" value='${gene:callFunction("it.eldasoft.gene.tags.functions.EsisteElenchiOperatoriFunction", pageContext)}' />
<c:set var="titleRaggruppamento" value="Raggruppamento"/>
<c:if test='${tipoImpresa[0] eq "2" or tipoImpresa[0] eq "11"}'>
	<c:set var="titleRaggruppamento" value="Consorziate"/>
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="ImprScheda" >
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.gene.tags.functions.GetTitleFunction",pageContext,"IMPR")}' />
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true" >
			<gene:pagina title="Dati generali" idProtezioni="DATIGEN">
				<jsp:include page="impr-datigen.jsp" />
			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>
</gene:template>
