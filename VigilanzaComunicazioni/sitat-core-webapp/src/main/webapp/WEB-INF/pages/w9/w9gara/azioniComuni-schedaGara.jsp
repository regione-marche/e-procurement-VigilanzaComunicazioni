<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

		<c:if test='${datiRiga.W9GARA_PROV_DATO ne 4 and gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-scheda.ControlloDatiInseriti") and (not gene:checkProt(pageContext,"FUNZ.VIS.ALT.W9.XML-REGLIGURIA"))}'>
			<jsp:include page="../commons/addToDocumenti-validazioneGaraLottiAvvisi.jsp" />
		</c:if>
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-scheda.InviaSimapRT")}'>
			<jsp:include page="../commons/addtodocumenti-invia-bando-avviso-simap.jsp" />
		</c:if>
