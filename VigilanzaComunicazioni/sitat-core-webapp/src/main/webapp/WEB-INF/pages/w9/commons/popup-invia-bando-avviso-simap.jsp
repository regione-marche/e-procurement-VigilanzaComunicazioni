
<%
  /*
			 * Created on 15-lug-2008
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div style="width: 97%;"><gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	<gene:setString name="titoloMaschera" value='Invio dei dati a SimapRT' />

	<gene:redefineInsert name="corpo">
	
		<form action="${contextPath}/w9/InviaBandoAvvisoSimap.do" method="post" name="formInviaBandoAvvisoSimap" >
			<input type="hidden" name="codgara" value="${param.codgara}" />
			
			<table class="dettaglio-notab">
				<tr>
					<td class="valore-dato" colspan="2">
						<br>
						Questa funzione prepara ed invia a SimapRT i dati della
						gara selezionata per il successivo controllo ed invio al
						<b>SIMAP della Comunit&agrave; Europea.</b>
						<br>
						<br>
						<br>
						<b>Credenziali (utente e password) per la connessione a SimapRT</b>
						<br>
						<br>
						<input id="correnti" type="radio" name="credenziali" value="CORRENTI" checked onchange="javascipt:gestioneCredenziali()">Credenziali correnti
						<br>
						<input id="altre" type="radio" name="credenziali" value="ALTRE" onchange="javascipt:gestioneCredenziali()">Altre credenziali
						<br>
					</td>
				</tr>

				<tr>	
					<td class="etichetta-dato">Utente</td>
					<td class="valore-dato">
						<input type="text" name="username" size="15" disabled/>
					</td>
				</tr>
				<tr>
					<td class="etichetta-dato">Password</td>
					<td class="valore-dato">
						<input type="password" name="password" size="15" disabled/>
					</td>
				</tr>
				
				<tr>
					<td class="valore-dato" colspan="2"> 
						<br>
						<br>
						<b>Tipologia bando o avviso</b>
						<br>
						<br>
						<input id="fs2" type="radio" name="formulario" value="FS2" checked>Bando di gara
						<br>
						<input id="fs3" type="radio" name="formulario" value="FS3">Avviso di aggiudicazione
						<br>
						<input id="fs9" type="radio" name="formulario" value="FS9">Bando di gara semplificato (sistema dinamico di acquisizione)						
						<br>
						<br>						
					</td>
				</tr>
	
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Invia bando/avviso" title="Invia bando/avviso"	onclick="javascript:inviabandoavvisosimap();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>

	<gene:javaScript>
		
		function annulla(){
			window.close();
		}
		
		
		function inviabandoavvisosimap() {
		
			var invia = "true";
		
			var altre = document.formInviaBandoAvvisoSimap.altre;
			if (altre.checked) {
				var username = document.formInviaBandoAvvisoSimap.username;
				var password = document.formInviaBandoAvvisoSimap.password;
				
				if (username.value == "") {
					alert("Inserire l'utente");
					invia = "false";
				}
				
				if (password.value == "") {
					alert("Inserire la password");
					invia = "false";
				}
			}
		
			var formulario = "";
			var fs2 = document.formInviaBandoAvvisoSimap.fs2;
			var fs3 = document.formInviaBandoAvvisoSimap.fs3;
			var fs9 = document.formInviaBandoAvvisoSimap.fs9;
			
			if (fs2.checked) {
				formulario = "FS2";
			} 
			if (fs3.checked) {
				formulario = "FS3";
			} 
			if (fs9.checked) {
				formulario = "FS9";
			} 
			
			if(formulario == "") {
		 		alert("Selezionare una tipologia di bando o avviso");
		 		invia = "false";
			}
				
			if (invia == "true") {
				document.formInviaBandoAvvisoSimap.submit();
				bloccaRichiesteServer();
			}
		}
		
		function gestioneCredenziali() {
			var altre = document.formInviaBandoAvvisoSimap.altre;
			
			if (altre.checked) {
				document.formInviaBandoAvvisoSimap.username.disabled = false;
				document.formInviaBandoAvvisoSimap.password.disabled = false;					
			} else {
				document.formInviaBandoAvvisoSimap.username.disabled = true;
				document.formInviaBandoAvvisoSimap.password.disabled = true;
				document.formInviaBandoAvvisoSimap.username.value = "";
				document.formInviaBandoAvvisoSimap.password.value = "";
			}			
		}
		
	
	</gene:javaScript>
</gene:template></div>

