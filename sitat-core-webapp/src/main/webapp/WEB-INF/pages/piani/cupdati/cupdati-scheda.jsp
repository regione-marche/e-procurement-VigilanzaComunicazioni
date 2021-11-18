
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

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"
	schema="PIANI" idMaschera="CUPDATI-scheda">
	<gene:setString name="titoloMaschera" value='Richiesta e gestione CUP' />

	<c:set var="id" value='${gene:getValCampo(key,"ID")}' scope="request" />

	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Dati generali" idProtezioni="DATIGEN">
				<jsp:include page="cupdati-pg-datigen.jsp" />
			</gene:pagina>
			<gene:pagina title="Descrizione e localizzazione" idProtezioni="DESCRIZIONE">
				<jsp:include page="cupdati-pg-descrizione.jsp" />
			</gene:pagina>
			<gene:pagina title="Finanziamento" idProtezioni="FINANZIAMENTO">
				<jsp:include page="cupdati-pg-finanziamento.jsp" />
			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="addToDocumenti" >
		<jsp:include page="/WEB-INF/pages/piani/cupdati/cupdati-addtodocumenti.jsp" />
	</gene:redefineInsert>

	<gene:javaScript>
		function popupValidazioneCUP(id) {
			openPopUpCustom("href=piani/cupdati/cupdati-popup-validazione.jsp&id=" + id, "validaziopneCUP", 500, 550, "yes", "yes");
		}
	
		function popupRichiestaCUP(id) {
			openPopUpCustom("href=piani/cupdati/cupdati-popup-avvia-richiesta.jsp&id=" + id, "richiestaCUP", 500, 450, "yes", "yes");
		}
	</gene:javaScript>

</gene:template>

