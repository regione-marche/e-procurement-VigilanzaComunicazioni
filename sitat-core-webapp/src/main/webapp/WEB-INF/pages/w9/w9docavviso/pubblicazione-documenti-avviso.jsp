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

<c:set var="codein" value='${fn:substringBefore(param.chiave, ";")}' />
<c:set var="idavviso" value='${fn:substringBefore(fn:substringAfter(param.chiave, ";"), ";")}' />
<c:set var="codsistema" value='${fn:substringAfter(fn:substringAfter(param.chiave, ";"), ";")}' />


<c:choose>
	
	<c:when test="${param.tipoDettaglio eq 1}">
		<gene:campoScheda campo="CODEIN_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="T16;1;;;W9DACODEIN" value="${item[0]}" />
		<gene:campoScheda campo="IDAVVISO_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DAIDAVV" value="${item[1]}" />
		<gene:campoScheda campo="CODSISTEMA_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N7;1;;;W9DACODSIS" value="${item[2]}" />
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DANUMDOC" value="${item[3]}" />
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" definizione="T250;0;;;W9DATITOLO" obbligatorio="true" value="${item[4]}"/>
		<gene:campoScheda campo="URL_${param.contatore}" href="${item[5]}" entita="W9DOCAVVISO" campoFittizio="true" definizione="T2000;0;;;W9DAURL" value="${item[5]}" visibile="${not empty item[5].value}" >
			<gene:checkCampoScheda funzione="controlloURL(${param.contatore})" 
				messaggio="il campo URL non è valido. Deve iniziare con uno dei seguenti prefissi: http://, https://, ftp://, ftps://. Ad esempio: https://www.amministrazione.it/gara01/bando.pdf" obbligatorio="true" onsubmit="true" />
		</gene:campoScheda>
		<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false" />
		<c:if test='${not empty item[6].value}'>
			<gene:campoScheda campo="VISUALIZZA_FILE_${param.contatore}" title="File documentazione"
			campoFittizio="true" modificabile="false" definizione="T200;0" >
				<a href='javascript:visualizzaFileAllegato(${item[3]});'>Visualizza</a>
			</gene:campoScheda>
		</c:if>
		<c:set var="numDocumenti" value="${param.contatore}" scope="request" />
	</c:when>
	
	<c:otherwise>
		<gene:campoScheda campo="CODEIN_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="T16;1;;;W9DACODEIN" value="${codein}"/>
		<gene:campoScheda campo="IDAVVISO_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N10;1;;;W9DAIDAVV" value="${idavviso}"/>
		<gene:campoScheda campo="CODSISTEMA_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N7;1;;;W9DACODSIS" value="${codsistema}"/>
		<gene:campoScheda campo="NUMDOC_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" visibile="false" definizione="N4;1;;;W9DANUMDOC" />
		<gene:campoScheda campo="TITOLO_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" definizione="T250;0;;;W9DATITOLO" obbligatorio="true"/>
		<gene:campoScheda campo="URL_${param.contatore}" entita="W9DOCAVVISO" campoFittizio="true" definizione="T2000;0;;;W9DAURL" >
			<gene:checkCampoScheda funzione="controlloURL(${param.contatore})" 
				messaggio="il campo URL non è valido. Deve iniziare con uno dei seguenti prefissi: http://, https://, ftp://, ftps://. Ad esempio: https://www.amministrazione.it/gara01/bando.pdf" obbligatorio="true" onsubmit="true" />
		</gene:campoScheda>
		<c:if test='${!gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PUBBLICAZIONI.ONLY-URL")}'>
			<gene:campoScheda campo="NOME_FILE_${param.contatore}" title="NomeFILE" campoFittizio="true" definizione="T50" visibile="false"/>
			<c:if test='${modo eq "MODIFICA" || modo eq "NUOVO"}'>
				<gene:campoScheda title="Nome file" nome="selezioneFile_${param.contatore}" >
					<input type="file" name="selFile[${param.contatore}]" id="selFile[${param.contatore}]" class="file" size="70" onkeydown="return bloccaCaratteriDaTastiera(event);" onchange='javascript:scegliFile(${param.contatore});'/>
				</gene:campoScheda>
			</c:if>
		</c:if>
	</c:otherwise>
	
</c:choose>
