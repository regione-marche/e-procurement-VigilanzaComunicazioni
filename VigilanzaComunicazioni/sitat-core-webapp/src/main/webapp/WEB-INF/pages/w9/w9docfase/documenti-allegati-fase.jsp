<%
/*
 * Created on: 28/03/2011
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="codgara" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="codlott" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="temp1" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="fase_esecuzione" value='${fn:substringBefore(temp1, ";")}' />
<c:set var="num_fase" value='${fn:substringAfter(temp1, ";")}' />

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda campo="CODGARA_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DFCODGARA" value="${item[0]}" />
		<gene:campoScheda campo="CODLOTT_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DFCODLOTT" value="${item[1]}" />
		<gene:campoScheda campo="FASE_ESECUZIONE_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N7;1;;;W9DFFASEESEC" value="${item[2]}" />
		<gene:campoScheda campo="NUM_FASE_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N5;1;;;W9DFNUMFASE" value="${item[3]}" />		
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DFNUMDOC" value="${item[4]}" />
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" definizione="T250;0;;;W9DFTITOLO" obbligatorio="true" value="${item[5]}"/>
		<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false" />
		<c:if test='${ctrlBlob eq "true"}'>
			<gene:campoScheda campo="VISUALIZZA_FILE_${param.contatore}" title="File PDF"
				campoFittizio="true" modificabile="false" definizione="T200;0" >		
					<a href='javascript:visualizzaFileAllegato(${item[0]},${item[1]},${item[2]},${item[3]},${item[4]});'>Visualizza</a>
			</gene:campoScheda>
		</c:if>

	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="CODGARA_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DFCODGARA" value="${codgara}" />
		<gene:campoScheda campo="CODLOTT_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DFCODLOTT" value="${codlott}" />
		<gene:campoScheda campo="FASE_ESECUZIONE_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N7;1;;;W9DFFASEESEC" value="${fase_esecuzione}" />
		<gene:campoScheda campo="NUM_FASE_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N5;1;;;W9DFNUMFASE" value="${num_fase}" />	
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DFNUMDOC" />
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCFASE" campoFittizio="true" obbligatorio="true" definizione="T250;0;;;W9DGTITOLO" />
		<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false"/>
		<gene:campoScheda title="Nome file (*)" nome="selezioneFile_${param.contatore}" >
			<input type="file" name="selFile[${param.contatore}]" id="selFile[${param.contatore}]" class="file" size="70" onkeydown="return bloccaCaratteriDaTastiera(event);" onchange='javascript:scegliFile(${param.contatore});'/>
		</gene:campoScheda>
	</c:otherwise>
</c:choose>
