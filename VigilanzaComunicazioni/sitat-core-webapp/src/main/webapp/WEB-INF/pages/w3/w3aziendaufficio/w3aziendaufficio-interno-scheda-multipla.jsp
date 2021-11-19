<%
	/*
	 * Created on 01-Mar-2012
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>


<c:choose>

	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda modificabile="false" title="Identificativo" entita="W3AZIENDAUFFICIO" campo="ID_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;W3AZIUFF_ID" value="${item[0]}"/>
		<gene:campoScheda modificabile="false" title="Denominazione stazione appaltante" entita="W3AZIENDAUFFICIO" campo="UFFICIO_DENOM_${param.contatore}" campoFittizio="true" definizione="T250;0;;;W3AZIUFF_UFF_DENOM" value="${item[1]}"/>
		<gene:campoScheda modificabile="false" title="GUID stazione appaltante" entita="W3AZIENDAUFFICIO" campo="UFFICIO_ID_${param.contatore}" campoFittizio="true" definizione="T40;0;;;W3AZIUFF_UFF_ID" value="${item[2]}"/>
		<gene:campoScheda modificabile="false" title="Codice fiscale amministrazione" entita="W3AZIENDAUFFICIO" campo="AZIENDA_CF_${param.contatore}" campoFittizio="true" visibile="true" definizione="T16;0;;;W3AZIUFF_AZI_CF" value="${item[3]}"/>
		<gene:campoScheda modificabile="false" title="Denominazione amministrazione" entita="W3AZIENDAUFFICIO" campo="AZIENDA_DENOM_${param.contatore}" campoFittizio="true" visibile="true" definizione="T250;0;;;W3AZIUFF_AZI_DENOM" value="${item[4]}"/>
		<gene:campoScheda modificabile="false" title="Indice della collaborazione" entita="W3AZIENDAUFFICIO" campo="INDEXCOLL_${param.contatore}" campoFittizio="true" visibile="true" definizione="T100;0;;;W3AZIUFF_INDEXCOLL" value="${item[5]}"/>
	</c:when>

	<c:otherwise>
		<gene:campoScheda modificabile="false" title="Identificativo" entita="W3AZIENDAUFFICIO" campo="ID_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;W3AZIUFF_ID" />
		<gene:campoScheda modificabile="false" title="Denominazione stazione appaltante" entita="W3AZIENDAUFFICIO" campo="UFFICIO_DENOM_${param.contatore}" campoFittizio="true" definizione="T250;0;;;W3AZIUFF_UFF_DENOM" />
		<gene:campoScheda modificabile="false" title="GUID stazione appaltante" entita="W3AZIENDAUFFICIO" campo="UFFICIO_ID_${param.contatore}" campoFittizio="true" definizione="T40;0;;;W3AZIUFF_UFF_ID" />
		<gene:campoScheda modificabile="false" title="Codice fiscale amministrazione" entita="W3AZIENDAUFFICIO" campo="AZIENDA_CF_${param.contatore}" campoFittizio="true" visibile="true" definizione="T16;0;;;W3AZIUFF_AZI_CF" />
		<gene:campoScheda modificabile="false" title="Denominazione amministrazione" entita="W3AZIENDAUFFICIO" campo="AZIENDA_DENOM_${param.contatore}" campoFittizio="true" visibile="true" definizione="T250;0;;;W3AZIUFF_AZI_DENOM" />
		<gene:campoScheda modificabile="false" title="Indice della collaborazione" entita="W3AZIENDAUFFICIO" campo="INDEXCOLL_${param.contatore}" campoFittizio="true" visibile="true" definizione="T100;0;;;W3AZIUFF_INDEXCOLL" />
	</c:otherwise>

</c:choose>


