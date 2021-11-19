
<%
  /*
   * Created on 26-feb-2010
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
	schema="W9" idMaschera="AVVISO-scheda">
	<c:set var="key" value='AVVISO.IDAVVISO=N:${gene:getValCampo(key,"IDAVVISO")};AVVISO.CODEIN=T:${sessionScope.uffint};AVVISO.CODSISTEMA=N:${gene:getValCampo(key,"CODSISTEMA")};' scope="request" />
	<c:set var="key1" value='${gene:getValCampo(key,"CODEIN")}' scope="request" />
	<c:set var="key2" value='${gene:getValCampo(key,"IDAVVISO")}' scope="request" />
	<c:set var="flusso" value='989' scope="request" />
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"AVVISO")}' />
	<c:set var="codein" value='${gene:getValCampo(key,"CODEIN")}' scope="request" />
	<c:set var="idavviso" value='${gene:getValCampo(key,"IDAVVISO")}' scope="request" />
	<c:set var="codsistema" value='${gene:getValCampo(key,"CODSISTEMA")}' scope="request" />
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">

			<gene:pagina title="Dati Avviso" idProtezioni="DATIGEN">
				<jsp:include page="avviso-pg-datigen.jsp" />
			</gene:pagina>
			<c:set var="title_invii" value="Invii"/>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
				<c:set var="title_invii" value="Pubblica"/>
			</c:if>
			<gene:pagina title="${title_invii}" idProtezioni="FLUSSIAVVISO">
				<jsp:include page="avviso-pg-lista-flussi.jsp" />
			</gene:pagina>
		</gene:formPagine>

	</gene:redefineInsert>
	<gene:redefineInsert name="addToAzioni">
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.AVVISO-scheda.ControlloDatiInseriti")}' >
			<jsp:include page="../commons/addToDocumenti-validazioneGaraLottiAvvisi.jsp" />
		</c:if>
	</gene:redefineInsert>
	<gene:javaScript>
		function popupValidazione(flusso,key1,key2,key3) {
		  
		   var comando = "href=w9/commons/popup-validazione-generale.jsp";
		   comando = comando + "&flusso=" + flusso;
		   comando = comando + "&key1=" + key1;
		   comando = comando + "&key2=" + key2;
		   comando = comando + "&key3=" + key3;
		   comando = comando + "&key4=1";
		   openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
		  }
  </gene:javaScript>
</gene:template>