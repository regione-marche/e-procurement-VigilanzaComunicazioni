<%/*
       * Created on 11-dic-2014
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test='${param.updateLista eq 1}' >
	<c:set var="isNavigazioneDisabilitata" value="1" scope="request"/>
	<c:set var="isNavigazioneDisattiva" value="1" scope="request"/>
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9CF_REGPERMESSI-scheda">
	<gene:setString name="titoloMaschera" value='Attribuzione incarichi' />

	<c:if test='${param.metodo ne "nuova"}' >
		<gene:historyClear/>
		<gene:historyAdd titolo='${gene:getString(pageContext,"titoloMaschera",gene:resource("label.tags.template.trova.titolo"))}' id="lista" />
	</c:if>
		
	<gene:redefineInsert name="corpo">
		
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Permessi" idProtezioni="PERMESSI" >
				<jsp:include page="w9cf_regpermessi-lista.jsp" />
			</gene:pagina>
			
			<gene:pagina title="Stazioni appaltanti" idProtezioni="STAZ_APP" >
				<c:choose>
					<c:when test='${param.updateLista eq 1}'>
						<jsp:include page="w9cf_modpermessi_sa-listaMod.jsp" />
					</c:when>
					<c:otherwise>
						<jsp:include page="w9cf_modpermessi_sa-lista.jsp" />
					</c:otherwise>
				</c:choose>
			</gene:pagina>
		</gene:formPagine>
	
	</gene:redefineInsert>
	
</gene:template>
