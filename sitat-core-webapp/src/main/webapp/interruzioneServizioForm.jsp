
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
		<form action="./setInterruzioneServizio.jsp" method="post" name="interruzioneServizioForm" >
		Set interruzione servizio (tutti i campi sono obbligatori):
		<br>
		<br>
		Data interruzione: <input type="text" name="dataInterruzione" value="" />
		<br>
		Ora inizio interruzione: <input type="text" name="oraInizioInterruzione" value="" />
		<br>
		Ora fine interruzione: <input type="text" name="oraFineInterruzione" value="" />
		<br>
		</form>
		<input type="button" value="Invia richiesta" title="Invia richiesta" onclick="javascript:submitForm();" />
	</body>
</html>