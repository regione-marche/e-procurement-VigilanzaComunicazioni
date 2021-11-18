<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<HTML>
<HEAD>
</HEAD>

<BODY onload="javascript:aggiornaPagina()">

</BODY>
<script type="text/javascript">
function aggiornaPagina()
{
	window.opener.location.href = "${pageContext.request.contextPath}/piani/ErrorGenerator.do?" + csrfToken;
	window.close();
}
</script>

</HTML>