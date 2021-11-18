<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<script type="text/javascript">

		</script>
	</head>
	<body>
		<br>	
		<h3>Consulta gara SIMOG:</h3>
		<br>
		<br>
		<c:if test='${not empty listaMessaggi}' >
			<c:forEach items="${listaMessaggi}" var="msg">
				-&nbsp;<c:out value="${msg}"/><br>
				<c:if test='${not empty exception}'>
					<c:out value="${exception}"/><br>
				</c:if>
			</c:forEach>
		</c:if>
		<br>
		<br>
		ResultXML.success = ${resultXML.success}<br>
		ResultXML.error = ${resultXML.error}<br>
		ResultXML.garaXML = <br>
		<c:out value="${resultXML.garaXML}" />
		<br>
		<br>
	</body>
</html>