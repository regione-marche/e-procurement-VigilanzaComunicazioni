<%/*
       * Created on 23/09/2014
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
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<fmt:setBundle basename="global" />

<c:set var="result" value="${gene:callFunction('it.eldasoft.gene.tags.functions.GetParametriRegistrazioneUtenteFunction',pageContext)}"/>

<gene:template file="scheda-nomenu-template.jsp">
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.realperson.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/0.9.0rc1/jspdf.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/ui/std/jquery-ui.css" />
		
		<style type="text/css">
			label.error {
				float: none;
				color: white;
 				background-color: #E40000; 
				padding-left: 5px;
				padding-right: 5px;
				padding-top: 2px;
				padding-bottom: 2px;
				vertical-align: middle;
				margin-left: 5px;
 				border: 1px solid #BA0000; 
 				-moz-border-radius-topleft: 4px; 
 				-webkit-border-top-left-radius: 4px; 
 				-khtml-border-top-left-radius: 4px; 
 				border-top-left-radius: 4px; 
				-moz-border-radius-topright: 4px;
				-webkit-border-top-right-radius: 4px;
				-khtml-border-top-right-radius: 4px;
				border-top-right-radius: 4px;
 				-moz-border-radius-bottomleft: 4px; 
 				-webkit-border-bottom-left-radius: 4px; 
 				-khtml-border-bottom-left-radius: 4px; 
 				border-bottom-left-radius: 4px; 
				-moz-border-radius-bottomright: 4px;
				-webkit-border-bottom-right-radius: 4px;
				-khtml-border-bottom-right-radius: 4px;
				border-bottom-right-radius: 4px;
			}
		 
			.error {
				color:Red;
			}
			
			.realperson-challenge {
				display: inline-block;
				vertical-align: bottom;
				color: #000;
				padding-right: 5px;
			}
			.realperson-text {
				font-family: "Courier New",monospace;
				font-size: 5px;
				font-weight: bold;
				letter-spacing: -1px;
				line-height: 2px;
			}
			.realperson-regen {
				padding-top: 4px;
				font-size: 10px;
				text-align: left;
				padding-left: 10px;
				cursor: pointer;
				font-style: italic;
				color: #454545;
			}
			.realperson-disabled {
				opacity: 0.75;
				filter: Alpha(Opacity=75);
			}
			.realperson-disabled .realperson-regen {
				cursor: default;
			}
			
			.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl { -moz-border-radius-topleft: 0px; -webkit-border-top-left-radius: 0px; -khtml-border-top-left-radius: 0px; border-top-left-radius: 0px; }
			.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr { -moz-border-radius-topright: 0px; -webkit-border-top-right-radius: 0px; -khtml-border-top-right-radius: 0px; border-top-right-radius: 0px; }
			.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl { -moz-border-radius-bottomleft: 0px; -webkit-border-bottom-left-radius: 0px; -khtml-border-bottom-left-radius: 0px; border-bottom-left-radius: 0px; }
			.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br { -moz-border-radius-bottomright: 0px; -webkit-border-bottom-right-radius: 0px; -khtml-border-bottom-right-radius: 0px; border-bottom-right-radius: 0px; }
			.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus { border: 1px solid #fbcb09; background: #fdf5ce; font-weight: bold; color: #AD3600; }

		</style>
		
	</gene:redefineInsert>

	<gene:setString name="titoloMaschera" value='Richiesta abilitazione accesso al servizio di cooperazione applicativa e interoperatibilità'/>

	<gene:redefineInsert name="corpo">

	<gene:formScheda entita="W_COOPUSR" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreFormRichiestaCredenziali" >
		<gene:campoScheda>
			<td colspan="2"><br><b>Dirigente Responsabile</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="ID_RICHIESTA" visibile="false" />
		<gene:campoScheda campo="STATO" visibile="false" defaultValue="1"/>
		<gene:campoScheda campo="DIRIGENTE"  title="Nome e Cognome" obbligatorio="true" />
		<gene:campoScheda campo="SERVIZIO_DIRIGENTE"  title="Responsabile del servizio" />
		<gene:campoScheda campo="TEL_DIRIGENTE"  title="Telefono" obbligatorio="true"/>
		<gene:campoScheda campo="MAIL_DIRIGENTE"  title="E-mail o PEC" obbligatorio="true" />
			
		<gene:campoScheda>
			<td colspan="2"><br><b>Per l'ente</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="UFFINT" campo="CODEIN" visibile="false" /> 
		<gene:campoScheda title="Codice fiscale" entita="UFFINT" campo="CFEIN" obbligatorio="true"/>
		<gene:campoScheda addTr="false">
			<tr id="rowUFFINT_AVVISO">
				<td colspan="2">
					<br>
					<i>Non esiste alcun ente con il codice fiscale o la partita IVA indicate, prima di procedere è necessario richiedere tramite servizio assistenza la registrazione dell'anagrafica dell'amministrazione.</i>
					<br>
				</td>
			</tr>
		</gene:campoScheda>			
		<gene:campoScheda addTr="false">
			<tr id="rowVIS_UFFINT_NOMEIN">
				<td class="etichetta-dato">Denominazione</td>
				<td class="valore-dato"> 
					<div id="VIS_UFFINT_NOMEIN"></div>
				</td>
			</tr>
		</gene:campoScheda>
			
		<c:if test='${!empty listaProfiliDisponibili}'>
			<gene:campoScheda>
				<td colspan="2"><br><b>Chiede abilitazione all'accesso ai servizi di interoperabilita' e cooperazione applicativa SCP per i servizi di pubblicazione abbinati ai seguenti applicativi </b></td>
			</gene:campoScheda>
			<gene:campoScheda>
				<td class="etichetta-dato">Servizi (*)</td>
				<td class="valore-dato">
					<c:forEach items="${listaProfiliDisponibili}" step="1" var="valoriProfiliDisponibili" varStatus="ciclo" >
						<input style="vertical-align: middle;" type="checkbox" name="applicativi" id="applicativi" value="${valoriProfiliDisponibili[0]}"/>
						<span style="vertical-align: middle;">${valoriProfiliDisponibili[1]}</span>
						<p style="margin-top:0px; padding-left: 24px;"><i>${valoriProfiliDisponibili[2]}</i></p>
					</c:forEach>
					<div style="margin-bottom: 5px;" id="messaggioApplicativi"></div>
				</td>
			</gene:campoScheda>
		</c:if>

		<gene:campoScheda>
			<td colspan="2"><br><b>Dal proprio IP pubblico statico</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="INDIRIZZI_IP" title="Uno o piu' indirizzi separati da ;" />
			
		<gene:campoScheda>
			<td colspan="2"><br><b>Eventuale messaggio per il gestore del sistema</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="MESSAGGIO" title="Messaggio per il gestore" />
			
		<gene:campoScheda>
			<td colspan="2"><br><b>Referente Tecnico Informatico per l'Amministrazione</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="TECNICO"  title="Nome e Cognome" obbligatorio="true" />
		<gene:campoScheda campo="TEL_TECNICO"  title="Telefono" obbligatorio="true"/>
		<gene:campoScheda campo="MAIL_TECNICO"  title="E-mail o PEC" obbligatorio="true" />
			
		<gene:campoScheda>
			<td colspan="2">
				<br>
				<b>Scarica il modello di richiesta di abilitazione ai servizi di cooperazione applicativa</b>
				<br>
				Per completare la richiesta &egrave; necessario scaricare il presente
				
					<a class="link-229" href="javascript:apriModelloAbilitazioneServizio();" >
				
				modello di abilitazione ai servizi già compilato</a>, firmarlo digitalmente e <br>
				allegarlo alla presente scheda di registrazione (vedi "Allega documenti").
			</td>
		</gene:campoScheda> 
		<gene:campoScheda nome="ALLDOC">
			<td colspan="2">
				<br>
				<b>Allega documento</b>
				<i><br>Utilizza il pulsante "Scegli file" per caricare il modulo</i>
			</td>
		</gene:campoScheda>
		<gene:campoScheda title="Nome file" visibile="true">
			<input type="file" accept=".pdf,.p7m,.tsd" name="selezioneFile" id="selezioneFile" class="file" size="70" onkeydown="return bloccaCaratteriDaTastiera(event);" onchange='javascript:scegliFile(this.value);'/>
		</gene:campoScheda>
		<gene:campoScheda title="File da allegare" campo="FILEDAALLEGARE" campoFittizio="true" visibile="false" definizione="T70;0" />

		<gene:campoScheda> 
			<td colspan="2">
				<br>
				<b>Informativa trattamento dati personali</b>
				<br>
				Si informa che i dati personali forniti ed acquisiti contestualmente alla registrazione ai servizi scelti, nonche' i dati necessari all'erogazione di tali servizi, saranno trattati, nel rispetto delle garanzie di riservatezza e delle misure di sicurezza previste dalla normativa vigente attraverso strumenti informatici, telematici e manuali, con logiche strettamente correlate alle finalita' del trattamento. 
				Per ulteriori dettagli <a href="https://www.mit.gov.it/privacy-cookie" class="link-generico">cliccare qui</a>
			    <br>	
			    <br>		       					
				<span style="vertical-align: middle;">Accetto (*)&nbsp;</span><input style="vertical-align: middle;" type="checkbox" name="informativaPrivacy" id="informativaPrivacy" title="Accetto le condizioni d'uso"/>
			</td>				
		</gene:campoScheda>	
			
		<gene:campoScheda>
			<td colspan="2"><br><b>Dimostra di non essere un robot</b></td>
		</gene:campoScheda>
		<gene:campoScheda>
			<td class="etichetta-dato">Codice di controllo (*)</td>
			<td class="valore-dato">
				<input type="text" id="realperson" name="realperson" maxlength="15" size="15" />&nbsp;
			</td>
		</gene:campoScheda>
			
		<gene:campoScheda>	
			<td colSpan="2">
				<i><br>(*) Campi obbligatori</i>
			</td>
		</gene:campoScheda>
		
		<gene:campoScheda>	
			<td class="comandi-dettaglio" colSpan="2">
				<gene:insert name="pulsanteSalva">
					<INPUT type="button" class="bottone-azione" value="Inoltra Richiesta" title="Inoltra Richiesta" onclick="javascript:eseguiSubmit()">
				</gene:insert>
				<gene:insert name="pulsanteAnnulla">
					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla registrazione" onclick="javascript:annullaScheda()">
				</gene:insert>
				&nbsp;
			</td>
		</gene:campoScheda>
	</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>

	document.forms[0].encoding="multipart/form-data";
		
	var changeUFFINT = false;	

	document.getElementById("W_COOPUSR_DIRIGENTE").size= 50;
	document.getElementById("W_COOPUSR_SERVIZIO_DIRIGENTE").size= 50;
	document.getElementById("W_COOPUSR_TEL_DIRIGENTE").size= 35;
	document.getElementById("W_COOPUSR_MAIL_DIRIGENTE").size= 50;
	
	document.getElementById("W_COOPUSR_INDIRIZZI_IP").size= 50;
	
	document.getElementById("W_COOPUSR_TECNICO").size= 50;
	document.getElementById("W_COOPUSR_TEL_TECNICO").size= 35;
	document.getElementById("W_COOPUSR_MAIL_TECNICO").size= 50;
	
	function gestioneAction() {
		var nuovaAction = contextPath + "/SchedaNoSessione.do?" + csrfToken;
		document.forms[0].action = nuovaAction;
		nuovaAction = "commons/redirect.jsp";
		document.forms[0].jspPathTo.value = nuovaAction;
	}
			
	$(document).ready(function()
	{
		$("#UFFINT_CFEIN").css("text-transform","uppercase");
		$('#UFFINT_CFEIN').on("change",function() {
		  getDescrizioneEnte("");
		});
		$('#UFFINT_CFEIN').on("keyup",function() {
		  getDescrizioneEnte("");
		});
				
		$("#realperson").css("text-transform","uppercase");
		$("#realperson").realperson({length: 6, regenerate: 'Rigenera codice'});

		jQuery.validator.addMethod("isSelectUffint",
			function(value, element) {return isSelectUffint(value);},
			"Selezionare un valore");

		jQuery.validator.addMethod("controlloPIVA",
			function(value, element) {
				return checkParIva(value);
			},
			"Il valore specificato non è valido");
				
		jQuery.validator.addMethod("isCodiceControlloCorretto",
			function(value, element) {return isCodiceControlloCorretto();},
			"Il codice di controllo non e' corretto");
				
	    $("form[name^='formScheda']").validate(
		{
			rules:
			{
				W_COOPUSR_DIRIGENTE: "required",
				W_COOPUSR_TEL_DIRIGENTE: "required",
			    W_COOPUSR_MAIL_DIRIGENTE: 
			    	{
			        	required: true,
			        	email: true
			        },
			    UFFINT_CFEIN:
			      	{
						required: true,
			        	controlloPIVA: true,
			        	isSelectUffint: true
			      	},
			    applicativi:
			        {
			            required: true
			        },
			    W_COOPUSR_TECNICO: "required",
			    W_COOPUSR_TEL_TECNICO: "required",
			    W_COOPUSR_MAIL_TECNICO: 
			    	{
			        	required: true,
			        	email: true
			        },
				selezioneFile:
			        {
			            required: true
			        },
			    informativaPrivacy:
			        {
			            required: true
			        },
   			    realperson:
			        {
			            required: true,
			            minlength: 6,
			            isCodiceControlloCorretto: true
			        }	            
			},
			messages:
			        {
			            W_COOPUSR_DIRIGENTE: "Inserire il nominativo del Dirigente",
			            W_COOPUSR_TEL_DIRIGENTE: "Inserire il telefono del Dirigente",
			            W_COOPUSR_MAIL_DIRIGENTE:
			            {
			            	required: "Inserire l'indirizzo e-mail",
			            	email: "Inserire un indirizzo e-mail valido"
			            },
			            UFFINT_CFEIN:
			           	{
			            	required: "Inserire il codice fiscale o la partita IVA",
			            	controlloPIVA: "Il valore specificato non rispetta il formato previsto",
			            	isSelectUffint: "Selezionare un valore dalla lista"
			            },
			            W_COOPUSR_TECNICO: "Inserire il nominativo del Tecnico",
			            W_COOPUSR_TEL_TECNICO: "Inserire il telefono del Tecnico",
			            W_COOPUSR_MAIL_TECNICO:
			            {
			            	required: "Inserire l'indirizzo e-mail",
			            	email: "Inserire un indirizzo e-mail valido"
			            },
			            applicativi:
			            {
			            	required: "Scegliere almeno un servizio tra quelli proposti"
			            },
			            selezioneFile:
						{
			            	required: "Inserire il documento richiesto"
			            },
			            informativaPrivacy:
			            {
			            	required: "Per procedere e' necessario accettare le condizioni"
			            },
			            realperson:
			            {
			            	required: "Digitare il codice di controllo",
			            	minlength: "Digitare tutti i 6 caratteri del codice di controllo"
			            }
			        },
			        errorPlacement: function(error, element) {
				    	if (element.attr("name") == "applicativi") {
				       		error.appendTo( $("#messaggioApplicativi") );
				       	} else {
				       		error.insertAfter(element);
				       	}
					}
				});
			});		
					
		
			function annullaScheda(){
				window.location.href="InitLogin.do";
			}

			function eseguiSubmit(){
				$("#selezioneFile").rules("add", "required");
				$("#informativaPrivacy").rules("add", "required");
				$("#realperson").rules("add", "required minlength isCodiceControlloCorretto");
				
				if ($("form[name^='formScheda']").validate().form()) {
					document.forms[0].metodo.value="update";
					if(activeForm.onsubmit()){
						bloccaRichiesteServer();
						document.forms[0].submit();
					}
				}
			}
			
			function apriModelloAbilitazioneServizio(){
				$("#selezioneFile").rules("remove", "required");
				$("#informativaPrivacy").rules("remove", "required");
				$("#realperson").rules("remove", "required minlength isCodiceControlloCorretto");
			            
				if ($("form[name^='formScheda']").validate().form()) {
					generatePDF();
				}
			}
			
			/*function scegliFile() {
				var selezioneFile = document.getElementById("selezioneFile").value;
				var lunghezza_stringa=selezioneFile.length;
				var posizione_barra=selezioneFile.lastIndexOf("\\");
				var nome=selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
				setValue("NOMEDOC",nome);
			}*/
			
			function scegliFile(valore) {
				selezioneFile = document.getElementById("selezioneFile").value;
				var lunghezza_stringa = selezioneFile.length;
				var posizione_barra = selezioneFile.lastIndexOf("\\");
				var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
				if(nome.length>100){
					alert("Il nome del file non può superare i 100 caratteri!");
					document.getElementById("selezioneFile").value="";
					setValue("FILEDAALLEGARE","");
				}else{
					setValue("FILEDAALLEGARE" ,nome);
				}
			}
			
			function getDescrizioneEnte(valore) {
					var result = false;
	                var cfein = $("#UFFINT_CFEIN").val();
	                if (cfein != "" && checkParIva(cfein)) {
						$.ajax({
							type: "GET",
							dataType: "json",
							async: false,
							beforeSend: function(x) {
							if(x && x.overrideMimeType) {
								x.overrideMimeType("application/json;charset=UTF-8");
							   }
							},
							url: "${pageContext.request.contextPath}/w9/GetDescrizioneEnte.do",
							data: "cfein=" + cfein,
							success: function(data){
								if (data.enteEsistente == true) {
									$("#VIS_UFFINT_NOMEIN").html(data.NOMEIN.value);
									$("#rowVIS_UFFINT_NOMEIN").show();
									$("#UFFINT_CODEIN").val(data.CODEIN.value);
									gestioneSezioneUFFINT(false);
									result = true;
								} else {
									$("#VIS_UFFINT_NOMEIN").html("");
									$("#rowVIS_UFFINT_NOMEIN").hide();
									$("#UFFINT_CODEIN").val("");
									gestioneSezioneUFFINT(true);
								}
							},
							error: function(e){
								alert("Ente: errore durante la lettura delle informazioni");
							}
						});
					} else {
						$("#VIS_UFFINT_NOMEIN").html("");
						$("#rowVIS_UFFINT_NOMEIN").hide();
						$("#UFFINT_CODEIN").val("");
					}
					return result;
			}

				if ($("#UFFINT_CFEIN").val() != "") {
					getDescrizioneEnte("");
				} else {
					$("#rowVIS_UFFINT_NOMEIN").hide();
					gestioneSezioneUFFINT(false);
				}
				
				
			function isSelectUffint(cfein) {
					return changeUFFINT;
			}				
			
	        function isCodiceControlloCorretto() {
					var isCodiceControlloCorretto = false;
	                $.ajax({
	                    type: "GET",
	                    dataType: "json",
	                    async: false,
	                    beforeSend: function(x) {
	        			if(x && x.overrideMimeType) {
	            			x.overrideMimeType("application/json;charset=UTF-8");
					       }
	    				},
	                    url: "${pageContext.request.contextPath}/IsCodiceControlloCorretto.do",
	                    data: "realpersonHash=" + $("input[name=realpersonHash]").val() + "&realperson=" + $("#realperson").val(),
	                    success: function(data){
	                    	if (data.codiceControlloCorretto == true) {
	                        	isCodiceControlloCorretto = true;
	                        } else {
	                        	isCodiceControlloCorretto = false;
	                        }
	                    },
	                    error: function(e){
	                        isCodiceControlloCorretto = false;
	                    }
	                });
	                return isCodiceControlloCorretto;
	        }
	        
	        function SetApplicativiDisponibili(){
				var appdisp = '';
				var pv = ';';
				var isAppDisp = false;
					<c:forEach items="${listaProfiliDisponibili}" var="valoriProfiliDisponibili" varStatus="ciclo">
						if(document.getElementById("APPDISP_${ciclo.index}").checked){
							var app_i = document.getElementById("APPDISP_${ciclo.index}").value;
							var appdisp_i = "${valoriProfiliDisponibili[0]}";
							appdisp += appdisp_i;
							appdisp +=pv;
							isAppDisp = true;
						}
					</c:forEach>
					if(isAppDisp){
						setValue("APPDISP",appdisp);
					}else{
						alert("Segliere almeno uno fra gli applicativi disponibili");
					}
				return isAppDisp;
			}
			
        	$(function() {
				$("#UFFINT_CFEIN").autocomplete({
					delay: 0,
				    autoFocus: true,
				    position: { 
				    	my : "left top",
				    	at: "left bottom"
				    },
					source: function( request, response ) {
						changeUFFINT = false;
						var cfamm = $("#UFFINT_CFEIN").val();
						$.ajax({
							async: false,
						    type: "GET",
		                    dataType: "json",
		                    beforeSend: function(x) {
		        			if(x && x.overrideMimeType) {
		            			x.overrideMimeType("application/json;charset=UTF-8");
						       }
		    				},
		                    url: "${pageContext.request.contextPath}/GetListaEnti.do",
		                    data: "cfamm=" + cfamm,
							success: function( data ) {
								if (!data) {
									response([]);
								} else {
									response( $.map( data, function( item ) {
										changeUFFINT = true;
										return {
											label: (item[1].value == "")?item[0].value:item[0].value + " (C.F: " + item[1].value + ")",
											value: (item[1].value == "")?cfamm:item[1].value,
											valueNOMEIN: item[0].value,
											valueCODEIN: item[2].value
										}
									}));
								} 
							},
		                    error: function(e){
		                        alert("Errore durante la lettura della lista degli enti");
		                    }
						});
					},
					minLength: 1,
					select: function( event, ui ) {
						$("#VIS_UFFINT_NOMEIN").html(ui.item.valueNOMEIN);
						$("#rowVIS_UFFINT_NOMEIN").show();
						$("#UFFINT_CODEIN").val(ui.item.valueCODEIN);
						changeUFFINT = true;
						gestioneSezioneUFFINT(false);
						//getDescrizioneEnte("");
					},
					open: function() {
						$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
					},
					close: function() {
						$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
					},
					change: function(event, ui) {
						var cfamm = $("#UFFINT_CFEIN").val();
						$.ajax({
							async: false,
						    type: "GET",
		                    dataType: "json",
		                    beforeSend: function(x) {
		        			if(x && x.overrideMimeType) {
		            			x.overrideMimeType("application/json;charset=UTF-8");
						       }
		    				},
		                    url: "${pageContext.request.contextPath}/GetListaEnti.do",
		                    data: "cfamm=" + cfamm,
							success: function( data ) {
								if (!data) {
									$("#VIS_UFFINT_NOMEIN").html("");
									$("#rowVIS_UFFINT_NOMEIN").hide();
									$("#UFFINT_CODEIN").val("");
									gestioneSezioneUFFINT(true);
								} 
							},
							error: function(e){
	                        		$("#VIS_UFFINT_NOMEIN").html("");
									$("#rowVIS_UFFINT_NOMEIN").hide();
									$("#UFFINT_CODEIN").val("");
									gestioneSezioneUFFINT(true);
		                    }
						});
					}
				});
			});
			
			function gestioneSezioneUFFINT(visibile) {
				if (visibile == true) {
					$("#rowUFFINT_AVVISO").show();
					$("#rowUFFINT_NOMEIN").show();
				} else {
					$("#rowUFFINT_AVVISO").hide();
					$("#rowUFFINT_NOMEIN").hide();
					$("#UFFINT_NOMEIN").val("");
				}
			};
			
			function generatePDF() 
			{
				var doc = new jsPDF();
				doc.setFontSize(11);
				doc.text(110, 20, 'Spett.');
				doc.setFontType('bold');
				doc.text(125, 20, 'Servizio Contratti Pubblici');
				doc.text(125, 25, 'Ministero delle infrastrutture e');
				doc.text(125, 30, 'della mobilità sostenibili');
				doc.setFontType('normal');
				doc.text(125, 35, 'Direzione generale per la');
				doc.text(125, 40, 'regolazione dei contratti pubblici e');
				doc.text(125, 45, 'la vigilanza sulle grandi opere');
				doc.text(120, 55, 'pubblicazione.bandi@pec.mit.gov.it');
				doc.setFontType('bold');
				doc.text(30, 65, 'Oggetto: Richiesta abilitazione accesso al servizio di cooperazione applicativa');
				doc.text(48, 70, 'e interoperabilità SCP');
				doc.setFontType('normal');
				doc.text(30, 80, 'Il sottoscritto:');
				doc.text(30, 85, 'In qualità di Dirigente Responsabile del servizio:');
				doc.text(30, 90, "dell'Amministrazione:");
				doc.text(30, 95, 'con Codice Fiscale:');
				doc.setFontType('bold');
				doc.text(75, 80, document.getElementById("W_COOPUSR_DIRIGENTE").value);
				doc.text(115, 85, document.getElementById("W_COOPUSR_SERVIZIO_DIRIGENTE").value);
				doc.text(75, 90, $("#VIS_UFFINT_NOMEIN").html());
				doc.text(75, 95, document.getElementById("UFFINT_CFEIN").value);
				doc.setFontSize(13);
				doc.text(90, 105, "CHIEDE");
				doc.setFontSize(11);
				doc.setFontType('normal');
				doc.text(30, 115, "l'abilitazione all'accesso ai servizi di interoperabilità e cooperazione applicativa SCP");
				doc.text(30, 120, "per i servizi di pubblicazione di:");
				var listaApplicativi="";
				var indice = 0;
				var top = 125;
				doc.setFontType('bold');
				$("input[type='checkbox']").each(function() {
                	if(this.checked) 
                	{
						if (indice == 0) {
							doc.text(30, top, "- ${listaProfiliDisponibili[0][1]}");
						}
						else if (indice == 1) {
							doc.text(30, top, "- ${listaProfiliDisponibili[1][1]}");
						}
						else if (indice == 2) {
							doc.text(30, top, "- ${listaProfiliDisponibili[2][1]}");
						}
						top += 5;
                	}
					indice++;
            	});
				doc.setFontType('normal');
				doc.text(30, 135, 'dal proprio IP pubblico statico:');
				doc.setFontType('bold');
				doc.text(85, 135, document.getElementById("W_COOPUSR_INDIRIZZI_IP").value);
				doc.setFontType('normal');
				doc.text(30, 145, "Indica quale Referente Tecnico Informatico per l'Amministrazione:");
				doc.text(30, 150, "Nome e Cognome:");
				doc.text(30, 155, "N. Telefono:");
				doc.text(30, 160, "Indirizzo mail:");
				doc.setFontType('bold');
				doc.text(75, 150, document.getElementById("W_COOPUSR_TECNICO").value);
				doc.text(75, 155, document.getElementById("W_COOPUSR_TEL_TECNICO").value);
				doc.text(75, 160, document.getElementById("W_COOPUSR_MAIL_TECNICO").value);
				doc.setFontSize(13);
				doc.text(85, 170, "PRENDE ATTO");
				doc.setFontSize(11);
				doc.setFontType('normal');
				doc.text(30, 180, "a) che i servizi di esercizio sono erogati dal lunedi al venerdi dalle ore 08.30 alle");
				doc.text(35, 185, "17.30, esclusi i giorni festivi. In tali fasce orarie è garantita esclusivamente la");
				doc.text(35, 190, "presa in carico entro 2 ore lavorative di eventuali segnalazioni di disservizio.");
				doc.text(35, 195, "Oltre tali fasce orarie i servizi non sono presidiati e quindi garantiti;");
				doc.text(30, 205, "b) che il gestore del servizio si riserva di apportare unilateralmente qualunque");
				doc.text(35, 210, "variazione ritenga opportuno o necessario in relazione a intervenute modifiche");
				doc.text(35, 215, "della normativa di riferimento, a nuove esigenze funzionali o tecnologiche");
				doc.text(35, 220, "ritenute necessarie dal Servizio Contratti Pubblici - Ministero delle infrastrutture");
				doc.text(35, 225, "e della mobilità sostenibili");
				doc.text(30, 235, "c) ogni eventuale variazione sarà comunicata all'indirizzo mail del Referente");
				doc.text(35, 240, "Tecnico Informatico, oppure resa disponibile in apposita area del sistema;");
				doc.text(30, 250, "d) che l'Amministrazione richiedente, fruitore del servizio, è responsabile di ogni");
				doc.text(35, 255, "intervento di manutenzione evolutiva e/o adeguativa dei sistemi di colloquio a");
				doc.text(35, 260, "propria cura e spese e senza che questo pregiudichi in alcun modo la piena");
				doc.text(35, 265, "autonomia decisionale del Servizio Contratti Pubblici - Ministero delle");
				doc.text(35, 270, "infrastrutture e della mobilità sostenibili in qualità di soggetto erogatore; è");
				doc.text(35, 275, "altresi cura dell'Amministrazione richiedente comunicare per tempo ogni");
				doc.text(35, 280, "variazione dei riferimenti e del proprio Referente Tecnico Informatico.");
				doc.addPage()
				doc.setFontType('bold');
				doc.setFontSize(13);
				doc.text(30, 20, "ACCETTA I SEGUENTI REQUISITI PER IL TRATTAMENTO DEI DATI");
				doc.text(55, 25, "RESI DISPONIBILI MEDIANTE I SERVIZI");
				doc.setFontSize(11);
				doc.text(30, 35, "1) Trattamento dei dati.");
				doc.setFontType('normal');
				doc.text(75, 35, "L'Accesso ai dati erogati dai servizi e le relative");
				doc.text(35, 40, "modalità di trattamento devono:");
				doc.text(35, 45, "a) avvenire nello stretto rispetto delle finalità istituzionali relative alle");
				doc.text(40, 50, "comunicazioni al Servizio Contratti Pubblici - Ministero delle infrastrutture e");
				doc.text(40, 55, "della mobilità sostenibili e ai sensi del codice dei contratti e ai decreti,");
				doc.text(40, 60, "circolari e comunicazioni di suddetto Ministero");
				doc.text(35, 65, "b) operando secondo modalità tali da garantire l'integrità, la riservatezza e la");
				doc.text(40, 70, "disponibilità dei dati stessi;");
				doc.text(35, 75, "c) trattati in modo lecito e secondo correttezza, per scopi determinati, espliciti");
				doc.text(40, 80, "e legittimi, pertinenti e non eccedenti rispetto alle finalità per le quali sono");
				doc.text(40, 85, "stati raccolti.");
				doc.setFontType('bold');
				doc.text(30, 95, "2) Responsabiltà.");
				doc.setFontType('normal');
				doc.text(65, 95, "Il responsabile dell'Ente che richiede autorizzazione al servizio");
				doc.text(35, 100, "è responsabile delle attività di trattamento dei dati stessi e di eventuali attività");
				doc.text(35, 105, "di trattamento effettuato in difformità a quanto indicato nel pt 1)");
				doc.setFontType('bold');
				doc.text(30, 115, "3) Controllo Accessi.");
				doc.setFontType('normal');
				doc.text(70, 115, "Il controllo dell'accesso avviene attraverso l'assegnazione di");
				doc.text(35, 120, "credenziali personali del Dirigente del Servizio per l'Amministrazione");
				doc.text(35, 125, "richiedente.");
				doc.setFontType('bold');
				doc.text(30, 135, "4) Tracciamento delle attività.");
				doc.setFontType('normal');
				doc.text(85, 135, "A fini di sicurezza gli accessi sono oggetto di");
				doc.text(35, 140, "tracciamento. Il tracciamento è messo in atto dai sistemi Firewall di Front End e");
				doc.text(35, 145, "dai log applicativi in essere sul sistema.");
				doc.setFontType('bold');
				doc.text(30, 155, "5) Scambio dei dati:");
				doc.setFontType('normal');
				doc.text(70, 155, "L'accesso avviene tramite protocollo HTTPS.");
				
				doc.text(30, 170, "Data .............................");
				
				doc.text(80, 185, "Il Dirigente del Servizio");
				doc.text(85, 190, "(firma leggibile)");
				doc.text(70, 200, "...................................................");
			
    			doc.save('ModelloRichiestaCredenziali.pdf');
			}
		</gene:javaScript>
		
</gene:template>