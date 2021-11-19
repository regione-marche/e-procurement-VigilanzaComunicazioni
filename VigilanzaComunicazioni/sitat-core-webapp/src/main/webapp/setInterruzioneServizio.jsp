
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<script type="text/javascript">
			function submitForm(){
				var data = document.interruzioneServizioForm.dataInterruzione.value;
				var oraInizio = document.interruzioneServizioForm.oraInizioInterruzione.value;
				var oraFine = document.interruzioneServizioForm.oraFineInterruzione.value;
				
				if(data == null || data == "" || oraInizio == null || oraInizio == "" || oraFine == null || oraFine == "")
					alert("Per proseguire, valorizzare tutti i campi");
				else {
					document.interruzioneServizioForm.submit();
				}
			}
		
		</script>
	</head>
	<body>
		
		<c:set var="dataInterruzione" value="${param.dataInterruzione}" scope="application" />
		<c:set var="oraInizioInterruzione" value="${param.oraInizioInterruzione}" scope="application" />
		<c:set var="oraFineInterruzione" value="${param.oraFineInterruzione}" scope="application" />
	
		<form action="./setInterruzioneServizio.jsp" method="post" name="interruzioneServizioForm" >
		Set interruzione servizio: valori settati
		<br>
		<br>
		Data interruzione: ${applicationScope.dataInterruzione}
		<br>
		Ora inizio interruzione: ${applicationScope.oraInizioInterruzione}
		<br>
		Ora fine interruzione: ${applicationScope.oraFineInterruzione}
		<br>
		</form>
	</body>
</html>