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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>

<c:choose>

	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda title="Id" entita="CUPLOC" campo="ID_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;CUPLOCID" value="${item[0]}"/>
		<gene:campoScheda title="Numero" entita="CUPLOC" campo="NUM_${param.contatore}" campoFittizio="true" visibile="false" definizione="N2;1;;;CUPLOCNUM" value="${item[1]}"/>
		<gene:campoScheda title="Stato" entita="CUPLOC" campo="STATO_${param.contatore}"campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCSTATO" value="${item[2]}" />
		<gene:campoScheda title="Regione" entita="CUPLOC" campo="REGIONE_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCREGIONE" value="${item[3]}"/>
		<gene:campoScheda title="Provincia" entita="CUPLOC" campo="PROVINCIA_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCPROVINCIA" value="${item[4]}"/>
		<gene:campoScheda title="Comune" entita="CUPLOC" campo="COMUNE_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCCOMUNE" value="${item[5]}" />
		<gene:campoScheda title="Stato" campo="STATO_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" value="${item[6]}" speciale="true">
			<gene:popupCampo titolo="Dettaglio localizzazione" href="javascript:formLocalizzazione(${param.contatore},'CUPLOC_STATO_${param.contatore}','CUPLOC_REGIONE_${param.contatore}','CUPLOC_PROVINCIA_${param.contatore}','CUPLOC_COMUNE_${param.contatore}')" />
		</gene:campoScheda>
		<gene:campoScheda title="Regione" campo="REGIONE_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" value="${item[7]}" />
		<gene:campoScheda title="Provincia" campo="PROVINCIA_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" value="${item[8]}" />
		<gene:campoScheda title="Comune" campo="COMUNE_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" value="${item[9]}" />				
	</c:when>

	<c:otherwise>
		<gene:campoScheda title="Id" entita="CUPLOC" campo="ID_${param.contatore}" campoFittizio="true" visibile="false" definizione="N10;1;;;CUPLOCID" value="${param.chiave}"/>
		<gene:campoScheda title="Numero" entita="CUPLOC" campo="NUM_${param.contatore}" campoFittizio="true" visibile="false" definizione="N2;1;;;CUPLOCNUM" />
		<gene:campoScheda title="Stato" entita="CUPLOC" campo="STATO_${param.contatore}" value="05" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCSTATO" />
		<gene:campoScheda title="Regione" entita="CUPLOC" campo="REGIONE_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCREGIONE"  />
		<gene:campoScheda title="Provincia" entita="CUPLOC" campo="PROVINCIA_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCPROVINCIA"  />
		<gene:campoScheda title="Comune" entita="CUPLOC" campo="COMUNE_${param.contatore}" campoFittizio="true" visibile="false" definizione="T10;0;;;CUPLOCCOMUNE" />
		<gene:campoScheda title="Stato" campo="STATO_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" speciale="true">
			<gene:popupCampo titolo="Dettaglio localizzazione" href="javascript:formLocalizzazione(${param.contatore},'CUPLOC_STATO_${param.contatore}','CUPLOC_REGIONE_${param.contatore}','CUPLOC_PROVINCIA_${param.contatore}','CUPLOC_COMUNE_${param.contatore}')" />
		</gene:campoScheda>
		<gene:campoScheda title="Regione" campo="REGIONE_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" />
		<gene:campoScheda title="Provincia" campo="PROVINCIA_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" />
		<gene:campoScheda title="Comune" campo="COMUNE_DESC_${param.contatore}" modificabile="false" campoFittizio="true" visibile="true" definizione="T2000;0" />				
	</c:otherwise>

</c:choose>


