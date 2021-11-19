
<%
	/*
	 * Created on 18-ott-2007
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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="codiceGara" value='${gene:getValCampo(key,"CODGARA")}' scope="request"  />
<c:set var="tipoPubblicazione" value='${param.tipoPubblicazione}' scope="request" />
<c:set var="codiceLotto" value='${param.codiceLotto}' scope="request" />

<c:if test='${modo ne "NUOVO"}'>
	<c:set var="numeroPubblicazione" value='${gene:getValCampo(key,"NUM_PUBB")}' scope="request"  /> 		
</c:if>

<c:set var="vincoliVisualizzazioneObbligatorieta" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetVincoliVisualizzazioneObbligatorietaFunction",pageContext,tipoPubblicazione)}' scope="request" />


<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9PUBBLICAZIONI-scheda">

	<c:set var="flusso" value='901' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='' scope="request" />
	<c:set var="key3" value='' scope="request" />
	<c:set var="key4" value='${gene:getValCampo(key,"NUM_PUBB")}' scope="request" />

	<c:set var="userIsRup"
		value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,key,"W9GARA")}'
		scope="request" />

	<c:set var="numeroLottiPerPubblicazione" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetNumeroLottiGaraFunction",pageContext,codiceGara)}' scope="request" />

	<c:choose>
		<c:when test='${modo ne "NUOVO"}'>
			<c:set var="permessoUser"
				value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9GARA")}'
				scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="permessoUser" value='5' scope="request" />
		</c:otherwise>
	</c:choose>

	<c:set var="modifica"
		value='${userIsRup eq "si" || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq "A" || sessionScope.profiloUtente.abilitazioneStd eq "C"}'
		scope="request" />

	<gene:setString name="titoloMaschera" value="Dettaglio Atto" />

	<gene:redefineInsert name="corpo" > 
		<gene:formPagine gestisciProtezioni="true">

			<input type="hidden" name="tipoPubblicazione" value='${tipoPubblicazione}' />
			<gene:pagina title="Dati Generali" idProtezioni="DETTPUBBLICAZIONI" >
				<jsp:include page="w9pubblicazioni-dati-generali.jsp" />
			</gene:pagina>
			<c:if test='${tipoAtto eq "E" or tipoAtto eq "A"}'>
				<gene:pagina title="Imprese aggiudicatarie / affidatarie" idProtezioni="IMPR">
					<jsp:include page="w9pubblicazioni-aggiudicatari.jsp" />
				</gene:pagina>
			</c:if>
			<c:if test='${numeroLottiPerPubblicazione > 1}'>
				<gene:pagina title="Lotti" idProtezioni="LOTTI">
					<jsp:include page="w9pubblicazioni-lista-lotti.jsp" />
				</gene:pagina>
			</c:if>
			<c:set var="title_invii" value="Invii"/>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
				<c:set var="title_invii" value="Pubblica"/>
			</c:if>
			<gene:pagina title="${title_invii}" idProtezioni="INVII">
				<jsp:include page="w9pubblicazioni-lista-flussi.jsp" />
			</gene:pagina>
		</gene:formPagine>

	</gene:redefineInsert>
</gene:template>