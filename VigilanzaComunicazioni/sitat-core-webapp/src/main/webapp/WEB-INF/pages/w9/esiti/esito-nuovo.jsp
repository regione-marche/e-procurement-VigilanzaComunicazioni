<%
/*
 * Created on: 03/09/2012
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<gene:template file="scheda-template.jsp">

<gene:setString name="titoloMaschera" value='Compilazione di un esito con CIG o SmartCIG' />
<c:set var="tipoAttoEsito" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetTipoAttoEsitoFunction",pageContext)}'/>
<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:nuovoEsito();" title="Avanti" tabindex="1503">Avanti</a>
		</td>
	</tr>
</gene:redefineInsert>

<gene:redefineInsert name="corpo">

	<table class="dettaglio-notab">
		<tr>
			<td class="valore-dato" colspan="2"><input type="radio" name="tipo" id="CIG" value="1" onclick="">
			<b>Pubblica un esito di un appalto / affidamento con CIG/SmartCIG
			<input type="text" id="codiceCIG" name="codiceCIG" onchange="validaCIG()" styleClass="testo" size="12" maxlength="10" /></b>
			<input type="hidden" id="codgara" name="codgara" value=""/>
			<input type="hidden" id="codlott" name="codlott" value=""/>
			<input type="hidden" id="tipoBando" name="tipoBando" value=""/>
			<input type="hidden" id="giaInserito" name="giaInserito" value=""/>
			</td>
		</tr>
		<tr>
			<td class="valore-dato" colspan="2"><input type="radio" name="tipo" id="GARA" value="1" onclick=""> 
			<b>Pubblica un esito per tutti i lotti (o alcuni) di un appalto / affidamento che ha per oggetto </b>
			<input type="text" id="codiceGara" name="codiceGara" styleClass="testo" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td class="valore-dato" colspan="2"> 
			<br>
			<b>ATTENZIONE</b>: per inserire l'esito devi aver inserito prima l'anagrafica della gara/CIG/SmartCIG; se non l'hai già fatto, ritorna indietro alla home page e inseriscila utilizzando i link:
			<br><br>
			<b>Compilazione di una procedura con SmartCIG</b>
			<br>
			se devi inserire uno SmartCIG
			<br><br>
			<b>Compilazione di una procedura con CIG</b>
			<br>
			se hai richiesto il CIG sul SIMOG, per recuperare i dati
			</td>
		</tr>
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Indietro" title="Indietro" class="bottone-azione" onclick="javascript:Annulla();"/>&nbsp;&nbsp;
				<input type="button" value="Avanti" title="Avanti" class="bottone-azione" onclick="javascript:nuovoEsito();"/>&nbsp;
			</td>
		</tr>
	</table>

</gene:redefineInsert>

<gene:javaScript>

$(document).ready(function()
			{
				$(function() {
					$("#codiceCIG").autocomplete({
						delay: 0,
					    autoFocus: true,
					    position: { 
					    	my : "left top",
					    	at: "left bottom"
					    },
						source: function( request, response ) {
							var cig = $("#codiceCIG").val();
							$.ajax({
								async: false,
							    type: "GET",
			                    dataType: "json",
			                    beforeSend: function(x) {
			        			if(x && x.overrideMimeType) {
			            			x.overrideMimeType("application/json;charset=UTF-8");
							       }
			    				},
			                    url: "${pageContext.request.contextPath}/w9/GetListaCIGEsiti.do",
			                    data: "cig=" + cig + "&esito=true",
								success: function( data ) {
									if (!data) {
										response([]);
									} else {
										response( $.map( data, function( item ) {
											return {
												label: "Oggetto: " + item[0].value + ", Lotto:" + item[1].value + ", CIG: " + item[2].value ,
												value: item[2].value,
												valueTipoBando: item[3].value,
												valueCodgara: item[4].value,
												valueCodlott: item[5].value,
												valueGiaInserito: item[6].value
											}
										}));
									} 
								},
			                    error: function(e){
			                        alert("CIG: errore durante la lettura della lista dei cig");
			                    }
							});
						},
						minLength: 1,
						select: function( event, ui ) {
							$("#CIG").attr('checked', true);
							$("#tipoBando").val(ui.item.valueTipoBando);
							$("#codgara").val(ui.item.valueCodgara);
							$("#codlott").val(ui.item.valueCodlott);
							$("#giaInserito").val(ui.item.valueGiaInserito);
							if (ui.item.valueGiaInserito == '1') {
								alert("Esito già inserito, vai direttamente alla procedura");
							}
						},
						open: function() {
							$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
						},
						close: function() {
							$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
						},
						change: function(event, ui) {
							var cig = $("#codiceCIG").val();
							$.ajax({
								async: false,
							    type: "GET",
			                    dataType: "json",
			                    beforeSend: function(x) {
			        			if(x && x.overrideMimeType) {
			            			x.overrideMimeType("application/json;charset=UTF-8");
							       }
			    				},
			                    url: "${pageContext.request.contextPath}/w9/GetListaCIGEsiti.do",
			                    data: "cig=" + cig + "&esito=true",
								success: function( data ) {
									if (!data) {
										$("#codiceCIG").val("");
									} 
								},
								error: function(e){
		                        	$("#codiceCIG").val("");
			                    }
							});
						}
					});
				});
			});

		$(document).ready(function()
			{
				$(function() {
					$("#codiceGara").autocomplete({
						delay: 0,
					    autoFocus: true,
					    position: { 
					    	my : "left top",
					    	at: "left bottom"
					    },
						source: function( request, response ) {
							var gara = $("#codiceGara").val();
							$.ajax({
								async: false,
							    type: "GET",
			                    dataType: "json",
			                    beforeSend: function(x) {
			        			if(x && x.overrideMimeType) {
			            			x.overrideMimeType("application/json;charset=UTF-8");
							       }
			    				},
			                    url: "${pageContext.request.contextPath}/w9/GetListaCIGEsiti.do",
			                    data: "gara=" + gara + "&esito=true",
								success: function( data ) {
									if (!data) {
										response([]);
									} else {
										response( $.map( data, function( item ) {
											return {
												label: "Oggetto: " + item[1].value + ", Importo gara: " + item[2].value ,
												value: item[0].value,
												valueGiaInserito: item[3].value
											}
										}));
									} 
								},
			                    error: function(e){
			                        alert("Procedure affidamento: errore durante la lettura della lista delle procedure");
			                    }
							});
						},
						minLength: 1,
						select: function( event, ui ) {
							$("#GARA").attr('checked', true);
							$("#codgara").val(ui.item.value);
							$("#giaInserito").val(ui.item.valueGiaInserito);
							if (ui.item.valueGiaInserito == '1') {
								alert("Esito già inserito, vai direttamente alla procedura");
							}
						},
						open: function() {
							$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
						},
						close: function() {
							$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
						}
					});
				});
			});
			
	var regularexp = new RegExp('^[a-zA-Z0-9]+$');

	function validaCIG() {
		var codiceCIG = trimStringa(document.getElementById("codiceCIG").value);
		if (codiceCIG != "") {
			if (!regularexp.test(codiceCIG)) {
				alert("Codice CIG non e' nel formato previsto.\nIndicare solo lettere e numeri oppure '*'");
				document.getElementById("codiceCIG").focus();
			}
		}
	}
	
	//funzione di creazione nuovo esito
	function nuovoEsito(){
		var giaInserito = document.getElementById("giaInserito").value;
		var cig = document.getElementById("codiceCIG").value;
		var gara = document.getElementById("codiceGara").value;
		var tipoBando = document.getElementById("tipoBando").value;
		var codgara = document.getElementById("codgara").value;
		var codlott = document.getElementById("codlott").value;
		
		if (document.getElementById("CIG").checked && codgara != null && codlott != null) {
			var key = "W9PUBBLICAZIONI.CODGARA=N:" + codgara;
			if (cig == "" || codgara == "" || codlott == "") {
				alert("Selezionare un valore dalla lista CIG per continuare");
			} else {
				if (giaInserito == '1') {
					location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=w9/atti/atti-documenti.jsp&codiceGara=' + codgara + '&cig=' + cig + '&codiceLotto='+codlott;
				} else {
					location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&modo=NUOVO&key=' + key + '&codiceLotto=' + codlott + '&tipoPubblicazione=${tipoAttoEsito}';
				}
			}
		} else if (document.getElementById("GARA").checked && codgara != null) {
			var key = "W9PUBBLICAZIONI.CODGARA=N:" + codgara;
			if (gara == "" || codgara == "") {
				alert("Selezionare un valore dalla lista gare per continuare");
			} else {
				if (giaInserito == '1') {
					location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=w9/atti/atti-documenti.jsp&codiceGara=' + codgara + '&cig=';
				} else {
					location.href = '${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&modo=NUOVO&key=' + key + '&tipoPubblicazione=${tipoAttoEsito}';
				}
			}
		} 
	}
	
	function Annulla(){
		document.location.href = '${pageContext.request.contextPath}/Home.do?' + csrfToken;
	}

</gene:javaScript>

</gene:template>

