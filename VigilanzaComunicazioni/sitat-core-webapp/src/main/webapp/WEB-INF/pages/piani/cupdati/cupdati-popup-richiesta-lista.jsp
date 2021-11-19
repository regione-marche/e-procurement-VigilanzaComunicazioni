
<%
	/*
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */
%>


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${contextPath}/js/controlliFormali.js"></script>

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetCUPWSUserFunction" />
<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPDATIFunction" />

<div style="width: 97%;"><gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />
	<gene:setString name="titoloMaschera" value='Ricerca CUP assegnati' />

	<gene:redefineInsert name="corpo">
	
		<form action="${contextPath}/piani/RichiestaListaCUP.do" method="post" name="formRichiestaListaCUP" >

			<input type="hidden" name="campoCUP" value="${param.campoCUP}" />
	
			<table class="ricerca">
				<tr>
					<td class="valore-dato-trova" colspan="2">
						<br>
						Questa funzione permette di cercare, direttamente nella banca dati del CIPE, i CUP gi&agrave; assegnati ai progetti.</b>
						<br>
						<br>
					</td>
				</tr>
				
				<c:choose>
					<c:when test="${empty cupwsuser}">
						<tr>
							<td class="valore-dato" colspan="2">
								<br>
								Indicare le <b>credenziali</b> per l'accesso al servizio di generazione CUP.
								<br>
								<br>
							</td>
						</tr>
						<tr>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" />
								<input type="password" name="cupwspass" id="cupwspass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
							</td>
						</tr>
						<input type="hidden" name="recuperauser" value="0" />
						<input type="hidden" name="recuperapassword" value="0" />										
					</c:when>
					<c:otherwise>
						<tr>
							<td class="valore-dato" colspan="2">
								<br>
								Le <b>credenziali</b> per l'accesso al servizio sono state recuperate dalla precedente richiesta di generazione.
								<br>
								<br>
							</td>
						</tr>
						<tr>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" value="${cupwsuser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="cupwspass" id="cupwspass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</td>
						</tr>
						<input type="hidden" name="recuperauser" value="1" />
						<input type="hidden" name="recuperapassword" value="1" />	
					</c:otherwise>
				</c:choose>

				<tr>
					<td class="etichetta-dato">Memorizza le credenziali</td>
					<td class="valore-dato">
						<input type="checkbox" name="memorizza" value="memorizza" checked="checked"/>					
					</td>
				</tr>
				
				<tr>
					<td class="valore-dato-trova" colspan="2">
						<br>
						Indicare uno o pi&ugrave; <b>parametri di ricerca.</b>
						<br>
						<br>
					</td>
				</tr>
				
				<tr>	
					<td class="etichetta-dato">Anno della decisione</td>
					<td class="valore-dato-trova">
						<select name="annoDecisione" id="annoDecisione">
							<option value=""></option>
							<c:forEach items="${anniDiDecisione}" var="anno" >
								<option value="${anno}">${anno}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>	
					<td class="etichetta-dato">Codice CUP</td>
					<td class="valore-dato-trova">
						<input type="text" name="codiceCUP" id="codiceCUP" size="20" value="${param.codiceCUP}"/>
					</td>
				</tr>

				<tr>	
					<td class="etichetta-dato">Data assegnazione da</td>
					<td class="valore-dato-trova">
						<input type="text" name="dataGenerazioneDa" id="dataGenerazioneDa" 
							onblur="javascript:controllaInputData(this);" size="10" maxlength="10" class="data">
						&nbsp;a&nbsp;
						<input type="text" name="dataGenerazioneA"  id="dataGenerazioneA"
							onblur="javascript:controllaInputData(this);" size="10" maxlength="10" class="data"/>
						<span class="formatoParametro">&nbsp;(GG/MM/AAAA)</span>
					</td>
				</tr>

				<tr>
					<td class="etichetta-dato">Natura</td>
					<td class="valore-dato-trova">
						<select name="natura" id="natura" onchange="javascript:modificaNatura();">
							<option value="">&nbsp;</option>
							<c:if test='${!empty listaValoriNatura}'>
								<c:forEach items="${listaValoriNatura}" var="valoriNatura">
									<option value="${valoriNatura[0]}">${valoriNatura[2]}</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>

				<tr>	
					<td class="etichetta-dato">Tipologia</td>
					<td class="valore-dato-trova">
						<select name="tipologia" id="tipologia">
							<option value="">&nbsp;</option>
						</select>
					</td>
				</tr>				

				<tr>	
					<td class="etichetta-dato">Descrizione</td>
					<td class="valore-dato-trova">
						<input type="text" name="descrizione" id="descrizione" size="80"/>
					</td>
				</tr>

				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Trova" title="Trova"	onclick="javascript:trova();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>	
	</gene:redefineInsert>

	<gene:javaScript>
	
		var arrayTipologia=new Array();

		<c:forEach items="${listaValoriTipologia}" var="valoreTipologia">
			arrayTipologia.push(new tabellatoTipologia(${gene:string4Js(valoreTipologia[0])}, ${gene:string4Js(valoreTipologia[1])},${gene:string4Js(valoreTipologia[2])}));
		</c:forEach>

		function resetRecuperaUser() {
			document.formRichiestaListaCUP.recuperauser.value = "0";
		}
	
		function resetRecuperaPassword() {
			document.formRichiestaListaCUP.recuperapassword.value = "0";
		}
	
		function resetPassword() {
			document.formRichiestaListaCUP.cupwspass.value = "";
		}	
		
		function tabellatoTipologia(aCupcod1, aCupcod2, aCupDesc ) {
			var cupcod1;
			var cupcod2;
			var cupdesc;
			this.cupcod1 = aCupcod1;
			this.cupcod2 = aCupcod2;
			this.cupdesc = aCupDesc;
		}
		
		function annulla(){
			window.close();
		}
		
		function trova() {
			<c:choose>
				<c:when test="${empty cupwsuser}">
					var invia = "true";
				
					var cupwsuser = document.formRichiestaListaCUP.cupwsuser;
					if (cupwsuser.value == "") {
						alert("Inserire l'utente");
						invia = "false";
					}
					
					var cupwspass = document.formRichiestaListaCUP.cupwspass;			
					if (invia == "true" && cupwspass.value == "") {
						alert("Inserire la password");
						invia = "false";
					}
		
					if (invia == "true") {
						document.formRichiestaListaCUP.submit();
						bloccaRichiesteServer();
					}				
				</c:when>
				<c:otherwise>
					document.formRichiestaListaCUP.submit();
					bloccaRichiesteServer();
				</c:otherwise>
			</c:choose>
		}
		
		function modificaNatura(){
			var i;
			var valoreNatura = document.formRichiestaListaCUP.natura.value;
			var optTipologia = document.formRichiestaListaCUP.tipologia.options;
			optTipologia.length = 0;
			optTipologia.add(new Option("", ""));
			for(i=0; i < arrayTipologia.length ;i++){
				if (arrayTipologia[i].cupcod1 == valoreNatura) {
					optTipologia.add(new Option(arrayTipologia[i].cupdesc, arrayTipologia[i].cupcod2));
				}
			}
		}
		
		function controllaInputData(unCampoDiInput){
	  		return isData(unCampoDiInput);
		}
	
	</gene:javaScript>
</gene:template></div>

