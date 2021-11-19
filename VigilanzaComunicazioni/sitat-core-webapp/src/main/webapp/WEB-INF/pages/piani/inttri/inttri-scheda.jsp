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

<c:set var="contri" value='${gene:getValCampo(keyParent,"CONTRI")}'/>
<c:choose>
	<c:when test="${not empty keyParent}" >
		<c:set var="norma" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNormaFunction", pageContext, keyParent)}' scope="request" />
	</c:when>
	<c:otherwise>
		<c:set var="norma" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNormaFunction", pageContext, key)}' scope="request" />
	</c:otherwise>
</c:choose>
<c:if test='${modo ne "NUOVO"}'>
	<c:set var="modificabile" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,key,"PIATRI")}' scope="request"/>
</c:if>

<c:choose>
	<c:when test='${"2" eq norma}'>
	
		<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="INTTRI-scheda">
			<gene:redefineInsert name="corpo">
	
				<gene:formPagine gestisciProtezioni="true">
					<gene:pagina title='${tiprog eq "2" ? "Acquisto" : "Intervento"}' idProtezioni="INTERVENTO">
						<jsp:include page="inttri-scheda-norma.jsp" />
					</gene:pagina>
					<gene:pagina title="Risorse per capitolo di bilancio" idProtezioni="RISORSEBILANCIO">
						<jsp:include page="inttri-lista-risorseCapitoloBilancio.jsp" />
					</gene:pagina>
				</gene:formPagine>
			</gene:redefineInsert>
		</gene:template>
		
	</c:when>
	<c:otherwise>
		<jsp:include page="inttri-scheda-noNorma.jsp" />
	</c:otherwise>
</c:choose>

