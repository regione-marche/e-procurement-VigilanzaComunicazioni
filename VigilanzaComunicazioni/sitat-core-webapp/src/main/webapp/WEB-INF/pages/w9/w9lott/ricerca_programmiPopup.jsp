<%/*
       * Created on 29-Set-2009
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>

<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera"	value="Ricerca Programmi" />
	<gene:redefineInsert name="corpo">
		<gene:formTrova entita="INTTRI" lista="w9/w9lott/lista_programmiPopup.jsp?numeroPopUp=1&keyLott=${param.keyLott}" gestisciProtezioni="true" >
			<gene:gruppoCampi idProtezioni="GEN">
				<tr><td colspan="3"><b>Dati programa</b></td></tr>
				<gene:campoTrova campo="DESINT" />
				<gene:campoTrova campo="ANNTRI" entita="PIATRI" where="PIATRI.CONTRI = INTTRI.CONTRI"/>
				<gene:campoTrova campo="TIPROG" entita="PIATRI" where="PIATRI.CONTRI = INTTRI.CONTRI"/>
			</gene:gruppoCampi>
		</gene:formTrova>
	</gene:redefineInsert>
</gene:template>