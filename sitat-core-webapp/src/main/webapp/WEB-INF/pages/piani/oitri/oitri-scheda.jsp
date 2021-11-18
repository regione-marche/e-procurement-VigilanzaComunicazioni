<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>


<c:choose>
	<c:when test='${modo eq "NUOVO"}'>
		${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNextConintFunction", pageContext, keyParent)}
		<c:set var="keyOITRI"
			value='OITRI.CONTRI=N:${gene:getValCampo(keyParent,"CONTRI")};OITRI.CONINT=N:0'
			scope="request" />
		<c:set var="contri"
			value='${gene:getValCampo(keyParent,"CONTRI")}'
			scope="request" />
			</c:when>
	<c:otherwise>
		<c:set var="modificabile" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,key,"PIATRI")}' scope="request"/>
		<c:set var="keyOITRI"
			value='OITRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")};OITRI.CONINT=N:0'
			scope="request" />
		<c:set var="contri"
			value='${gene:getValCampo(key,"CONTRI")}'
			scope="request" />
	</c:otherwise>
</c:choose>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"
	schema="PIANI" idMaschera="OITRI-scheda">
	<gene:redefineInsert name="corpo">
	
		<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"OITRI")}' />
	
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Dati Generali" idProtezioni="DATIGEN">
				<jsp:include page="oitri-scheda-pg-datigen.jsp" />
			</gene:pagina>
			<gene:pagina title="Altri Dati" idProtezioni="FLUSSIPROGRAMMI">
				<jsp:include page="oitri-scheda-pg-altridati.jsp" />
			</gene:pagina>		
		</gene:formPagine>
	</gene:redefineInsert>
</gene:template>