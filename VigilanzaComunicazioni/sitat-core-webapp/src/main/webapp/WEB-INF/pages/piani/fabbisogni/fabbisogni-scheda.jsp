
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="FABBISOGNI-scheda">

<c:set var="key" value='INTTRI.CONTRI=N:0;INTTRI.CONINT=N:${gene:getValCampo(key,"CONINT")}' scope="request" />


	<c:choose>
		<c:when test='${modo eq "NUOVO"}'>
			<c:set var="tipint" value='${param.tipint}' scope="request" />
			<c:set var="isCheckProgrammaz" value='false'/>
		</c:when>
		<c:otherwise>
			<c:set var="tipint"	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetInttriFabbisogniFunction", pageContext, key)}' scope="request"/>
			<c:set var="isCheckProgrammaz" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.VerificaPresenzaProgrammazione", pageContext, key)}'/>
		</c:otherwise>
	</c:choose>



	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"FABBISOGNI")}' />

	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">

					<gene:pagina title="Dati Generali" idProtezioni="FABBDATIGEN">
						<jsp:include page="fabbisogni-pg-datigen.jsp" />
					</gene:pagina>
					<gene:pagina title="Capitoli di spesa" idProtezioni="FABBCAPSPESA">
						<jsp:include page="fabbisogni-pg-capspesa.jsp" />
					</gene:pagina>
					<gene:pagina title="Approvazione" idProtezioni="FABBAPPROVAZ">
						<jsp:include page="fabbisogni-pg-approvazione.jsp" />
					</gene:pagina>
					<c:if test="${isCheckProgrammaz}">
						<gene:pagina title="Programmazione" idProtezioni="FABBPROGRAMMAZ">
							<jsp:include page="fabbisogni-pg-programmazione.jsp" />
						</gene:pagina>
					</c:if>
			
		</gene:formPagine>
	</gene:redefineInsert>

	
	<gene:javaScript>
	
	<c:if test='${modo ne "VISUALIZZA" }'>
		document.forms[0].jspPathTo.value="piani/fabbisogni/fabbisogni-scheda.jsp";
		document.forms[0].entita.value = "INTTRI";
	</c:if>
	
	
	</gene:javaScript>
</gene:template>