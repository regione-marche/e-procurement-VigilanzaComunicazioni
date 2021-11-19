<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:choose>
	<c:when test='${empty ESITO}'>
		<jsp:include page="richiestaCigSimog.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="esitoRichiestaCigSimog.jsp" />
	</c:otherwise>
</c:choose>