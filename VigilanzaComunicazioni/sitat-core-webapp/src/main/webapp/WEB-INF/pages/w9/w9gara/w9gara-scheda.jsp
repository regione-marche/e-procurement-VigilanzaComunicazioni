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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9GARA-scheda">

	<c:set var="smartcig" value="no" scope="request"/>
	<c:if test="${!empty param.smartcig and param.smartcig eq 'si'}" >
		<c:set var="smartcig" value="si" scope="request"/>
	</c:if>
	
	<c:set var="flusso" value='988' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='' scope="request" />
	<c:set var="key3" value='' scope="request" />

<c:choose>
	<c:when test='${modo ne "NUOVO"}'>
		<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9GARA")}' scope="request" />
		<c:set var="smartcig" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.IsGaraSmartCigFunction",pageContext,key1)}' scope="request" />
		<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,key,"W9GARA")}' scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="permessoUser" value='5' scope="request" />
	</c:otherwise>
</c:choose>
	<c:set var="modifica" value='${userIsRup eq "si" || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq "A" || sessionScope.profiloUtente.abilitazioneStd eq "C"}' scope="request"/>

	<c:choose>
		<c:when test='${smartcig eq "si"}'>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9GARASC")}' />
		</c:when>
		<c:otherwise>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9GARA")}' />
		</c:otherwise>
	</c:choose>
		
	<gene:redefineInsert name="corpo">
	
	<c:if test='${not modifica}'>
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
		<gene:formPagine gestisciProtezioni="true">
			<c:choose>
				<c:when test='${smartcig eq "si"}'>
					<gene:pagina title="Dati generali" 	idProtezioni="DETTGARA">
						<jsp:include page="w9gara-sc-pg-dettaglioGara.jsp" />
					</gene:pagina>
					<gene:pagina title="Lista atti" idProtezioni="PUBBLICAZIONI">
						<jsp:include page="w9gara-pubblicazioni.jsp" />
					</gene:pagina>
				</c:when>
				<c:otherwise>
					<gene:pagina title="Dati generali" 	idProtezioni="DETTGARA">
						<jsp:include page="w9gara-pg-dettaglioGara.jsp" />
					</gene:pagina>
					<gene:pagina title="Lotti" idProtezioni="LOTTI">
						<jsp:include page="w9gara-pg-lista-lotti.jsp" />
					</gene:pagina>
					<c:if test='${!gene:checkProt(pageContext, "PAGE.VIS.W9.W9GARA-scheda.PUBBLICAZIONI")}'>
						<gene:pagina title="Bando e documentazione di gara" idProtezioni="BANDOGARA">
							<jsp:include page="w9gara-bando.jsp" /> 
						</gene:pagina>
					</c:if>
					<gene:pagina title="Lista atti" idProtezioni="PUBBLICAZIONI">
						<jsp:include page="w9gara-pubblicazioni.jsp" />
					</gene:pagina>
					<gene:pagina title="Pubblicit&agrave; gara" idProtezioni="W9PUBB">
						<jsp:include page="w9gara-pg-pubblicita-gara.jsp" />
					</gene:pagina>
					<c:set var="title_invii" value="Invii"/>
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
						<c:set var="title_invii" value="Pubblica"/>
					</c:if>
					<gene:pagina title="${title_invii}" idProtezioni="FLUSSIGARA">
						<jsp:include page="w9gara-pg-lista-flussi.jsp" />
					</gene:pagina>
				</c:otherwise>
			</c:choose>
			
		</gene:formPagine>

	</gene:redefineInsert>

	<gene:javaScript>
  function popupValidazione(flusso,key1,key2,key3) {
    var comando = "href=w9/commons/popup-validazione-generale.jsp";
    comando = comando + "&flusso=" + flusso;
    comando = comando + "&key1=" + key1;
    comando = comando + "&key2=" + key2;
    comando = comando + "&key3=" + key3;
    openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
  }
  
  function popupInviaBandoAvvisoSimap(codgara) {
    var comando = "href=w9/commons/popup-invia-bando-avviso-simap.jsp";
    comando = comando + "&codgara=" + codgara;
    openPopUpCustom(comando, "inviabandoavvisosimap", 450, 550, "yes", "yes");
  }
	</gene:javaScript>
</gene:template>