<%/*
   * Created on 03-set-2009
   *
   * Copyright (c) EldaSoft S.p.A.
   * Tutti i diritti sono riservati.
   *
   * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
   * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
   * aver prima formalizzato un accordo specifico con EldaSoft.
   */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test='${modo ne "NUOVO"}'>
	<c:set var="attivaNuovo" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.VerificaPermessiFasiFlussiFunction",pageContext,key)}' />
	<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,key,"W9LOTT")}' scope="request" />
	<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,key,"W9LOTT")}' scope="request" />
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9LOTT-scheda">
	<c:set var="key" value='${key}' scope="request" />
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9LOTT")}' />

	<gene:redefineInsert name="corpo">
		
		<c:set var="modifica" value='${userIsRup eq "si" || sessionScope.profiloUtente.abilitazioneStd eq "A" || sessionScope.profiloUtente.abilitazioneStd eq "C" || permessoUser ge 4 }' scope="request"/>
		<c:if test='${!modifica}'>
			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteModifica" />
		</c:if>
		
		<gene:formPagine gestisciProtezioni="true">

			<gene:pagina title="Dati generali" idProtezioni="DATIDET">
				<jsp:include page="w9lott-pg-datigen.jsp" />
			</gene:pagina>
			<gene:pagina title="Incarichi professionali" idProtezioni="INCA" >
				<jsp:include page="w9lott-pg-incarichi.jsp" />
			</gene:pagina>
			<gene:pagina title="Fasi / eventi" idProtezioni="FASI" >
				<jsp:include page="../w9fasi/w9fasi-lista.jsp" />
			</gene:pagina>
			<gene:pagina title="Atti pubblicati" idProtezioni="PUBBLICAZIONI" >
				<jsp:include page="w9lott-pg-lista-pubblicazioni.jsp" />
			</gene:pagina>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.PRODOTTI")}'>
			<gene:pagina title="Prodotti" idProtezioni="PRODOTTI" >
				<jsp:include page="w9lott-pg-lista-prodotti.jsp" />
			</gene:pagina>
			</c:if>
			<gene:pagina title="Schede inviate" idProtezioni="FLUSSI" selezionabile="${attivaNuovo eq true}">
				<jsp:include page="w9lott-pg-lista-v-flussi.jsp" />
			</gene:pagina>

		</gene:formPagine>
	</gene:redefineInsert>
</gene:template>