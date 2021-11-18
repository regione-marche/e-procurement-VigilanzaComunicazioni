<%@page language="java" import="java.util.*"%>
<%@page language="java" import="sun.net.*"%>

<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/general.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/controlliFormali.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/forms.js"></script>

		<script type="text/javascript">

			function consultaGara() {
				var codiceCIG = trimStringa(document.consultaGaraForm.cig.value);
				var isSetAccountSimog = trimStringa(document.consultaGaraForm.cfSimog.value) != "" &&
						(trimStringa(document.consultaGaraForm.passwordSimog.value) != "");
				if (codiceCIG != "" && codiceCIG != null) {
					if (isSetAccountSimog) {
						document.consultaGaraForm.metodo.value = "getCigVigilanza";
					} else {
						if ((trimStringa(document.consultaGaraForm.cfSimog.value) != "" &&
								(trimStringa(document.consultaGaraForm.passwordSimog.value) == "")) ||
								(trimStringa(document.consultaGaraForm.cfSimog.value) == "" &&
										(trimStringa(document.consultaGaraForm.passwordSimog.value) != ""))) {
							alert ("Valorizzare le credenziali Simog");
						}
					}

					document.consultaGaraForm.submit();
				} else {
					alert("Valorizzare il campo CIG");
				}
			}
			
			function visualizzaProprieta() {
				if (isObjShow("areaProprieta"))
					document.getElementById("areaProprieta").style.display="none";
				else
					document.getElementById("areaProprieta").style.display="";
			}
		</script>
	</head>
	<body>
		<h3>Consultazione gara:</h3>
		<br>

		<form action="${pageContext.request.contextPath}/w9/GetCig.do?_csrf=${sessionScope.OWASP_CSRFTOKEN}" method="post" name="consultaGaraForm" id="consultaGaraForm" >
			<table>
				<tr>
					<td style="text-align: right;">Codice CIG (*): </td>
					<td><input type="text" name="cig" id="cig" /></td>
					<input type="hidden" name="metodo" id="metodo" value="getCigOsservatorio" />
				</tr>
			</table>
			<br><br>
			Credenziali SIMOG (necessarie in configurazione Vigilanza)
			<table>
				<tr id="areaSimog1">
					<td style="text-align: right;">Codice fiscale utente: </td>
					<td><input type="text" name="cfSimog" id="cfSimog" length="24"></td>
				</tr>
				<tr id="areaSimog2">
					<td style="text-align: right;">Password: </td>
					<td><input type="password" name="passwordSimog" id="passwordSimog" length="24" maxlenght="24" ></td>
				</tr>
			</table>
		</form>
		<br>
		<input type="button" value="Invia richiesta" title="Invia richiesta" onclick="javascript:consultaGara();" />
		<br><br><br>


		<input type="checkbox" onclick="javascript:visualizzaProprieta();" > Visualizza propriet&agrave; del sistema
		
		<div id="areaProprieta" style="display: none;" >
		<br>
		<br>
		
		sun.net.InetAddressCachePolicy.get() = <%= sun.net.InetAddressCachePolicy.get() %>
		
		<br>
		<br>
<%

	Map map = System.getenv();

	%> Numero properties = <% System.getenv().size(); %> <br><br>
	
	1. variabili di ambiente 1:
	<% 
	if(! map.isEmpty()){
		Set keyset = map.keySet();
		if(! keyset.isEmpty()){
		  Iterator iter = keyset.iterator();
		  while(iter.hasNext()){
		    String tmp = (String) iter.next();
		    %>
		    chiave=<%= tmp.toString() %>
		    , valore=<%= map.get(tmp) %><br>
		    <%
		  }
		} else {
		  %>
		  Iterator delle chiavi vuoto<br>
		  <%
		}
	} else {
	  %>
	  Mappa delle chiavi vuota<br>
	  <%
	}
	%>
	<br><br>
	JAVA_HOME = <%= System.getProperty("JAVA_HOME") %>
	<br><br>
	2. variabili di ambiente:<br>
		<%
		
		Object terete =	System.getSecurityManager();
		if(terete != null){
		  %> C'e' un SecurityManager!!!  <%
		}
		
		Properties props = System.getProperties();
		Enumeration enumera = props.keys();// elements();
		
	  while(enumera.hasMoreElements()){
	    String tmp = (String) enumera.nextElement();
	    if(tmp != null && !tmp.equals("")){
	    	%>
	    	chiave=<%= tmp.toString() %> , valore=<%= props.getProperty(tmp) %><br>
	    	<%
	    }
	  }
		
%>
	</div>
	</body>
</html>