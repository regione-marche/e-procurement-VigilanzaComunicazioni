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

<c:choose>
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda campo="CODGARA_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DGCODGARA" value="${item[0]}" />
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DGNUMDOC" value="${item[1]}" />
		<gene:campoScheda campo="NUM_PUBB_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N3;1;;;W9DGNUMPUBB" value="1"/>
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" definizione="T50;0;;;W9DGTITOLO" obbligatorio="true" value="${item[2]}"/>
		<gene:campoScheda campo="URL_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" definizione="T2000;0;;;W9DGURL" value="${item[3]}"  visibile="${not empty item[3].value}" />
		<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false" />
		<c:if test='${not empty item[4].value}'>
			<gene:campoScheda campo="VISUALIZZA_FILE_${param.contatore}" title="File PDF per la pubblicazione sul sito della Regione"
				campoFittizio="true" modificabile="false" definizione="T200;0" >
					<a href='javascript:visualizzaFileAllegato(${item[1]}, ${item[5]});'>Visualizza</a>
			</gene:campoScheda>
		</c:if>


	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="CODGARA_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DGCODGARA" />
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DGNUMDOC" />
		<gene:campoScheda campo="NUM_PUBB_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" visibile="false" definizione="N3;1;;;W9DGNUMPUBB" value="1"/>
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" definizione="T50;0;;;W9DGTITOLO" obbligatorio="true"/>
		<gene:campoScheda campo="URL_${param.contatore}" entita="W9DOCGARA" campoFittizio="true" definizione="T2000;0;;;W9DGURL"  />
		<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false"/>
		<c:if test='${modo eq "MODIFICA"}'>
			<gene:campoScheda title="Nome file" nome="selezioneFile_${param.contatore}" >
				<input type="file" name="selFile[${param.contatore}]" id="selFile[${param.contatore}]" class="file" size="70" onkeydown="return bloccaCaratteriDaTastiera(event);" onchange='javascript:scegliFile(${param.contatore});'/>
			</gene:campoScheda>
		</c:if>
	</c:otherwise>
</c:choose>
