<%
  /*
	 * Created on 15-Mar-2012
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"
	schema="W9" idMaschera="W9GARA_ENTINAZ-scheda">
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9GARA_ENTINAZ")}' />

	<c:set var="flusso" value='983' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="key2" value='' scope="request" />
	<c:set var="key3" value='' scope="request" />
	
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Dati generali" idProtezioni="DATIGENENTINAZ">
				<jsp:include page="w9gara_entinaz-pg-datigen.jsp" />
			</gene:pagina>
			<gene:pagina title="Bando" idProtezioni="BANDOENTINAZ">
				<jsp:include page="w9gara_entinaz-pg-bando.jsp" />
			</gene:pagina>
			<gene:pagina title="Lotti ed esito" idProtezioni="LOTTIENTINAZ">
				<jsp:include page="w9gara_entinaz-pg-lottiEsito.jsp" />
			</gene:pagina>
			<gene:pagina title="Invii" idProtezioni="FLUSSIENTINAZ">
				<jsp:include page="w9gara_entinaz-pg-lista-flussi.jsp" />
			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>

	<gene:redefineInsert name="addToDocumenti" >
		<jsp:include page="/WEB-INF/pages/w9/w9gara_entinaz/entinaz-addtodocumenti.jsp" />
	</gene:redefineInsert>

	<gene:javaScript>
		function popupValidazione(flusso, key01, key02, key03) {
			var comando = "href=w9/commons/popup-validazione-generale.jsp";
			comando = comando + "&flusso=" + flusso;
			comando = comando + "&key1=" + key01;
			comando = comando + "&key2=" + key02;
			comando = comando + "&key3=" + key03;
			openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");		
		}
	</gene:javaScript>

</gene:template>