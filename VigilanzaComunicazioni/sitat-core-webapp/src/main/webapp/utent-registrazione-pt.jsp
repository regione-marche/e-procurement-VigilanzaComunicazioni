<%/*
       * Created on 17-Ott-2010
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
<c:set var="moduloCondizioniDuso" value="${gene:callFunction('it.eldasoft.sil.pt.tags.funzioni.RecuperoFileCondizioniDUsoFunction',pageContext)}"/>

<gene:template file="scheda-nomenu-template.jsp">

	<gene:redefineInsert name="head">
		<!--  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.7.2.min.js"></script> -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
		
		<style type="text/css">
			label.error {
				float: none;
				color: red;
				padding-left: 5px;
				vertical-align: middle;
			}
		 
			.error {
				color:Red;
			}
			

			
		</style>
		
	</gene:redefineInsert>

	<gene:javaScript>
		function gestioneAction() {
			var nuovaAction = contextPath + "/SchedaNoSessione.do?" + csrfToken;
			document.forms[0].action = nuovaAction;
			nuovaAction = "commons/redirect.jsp";
			document.forms[0].jspPathTo.value = nuovaAction;
		}
	</gene:javaScript>
	
	<gene:setString name="titoloMaschera" value='Registrazione utente'/>
	
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="TECNI" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreTecniRegistrazione" >
			<gene:campoScheda>
				<td colspan="2"><br><b>Dati anagrafici dell'utente</b></td>
			</gene:campoScheda>
			<gene:campoScheda title="Codice dell'anagrafico" campo="CODTEC"  
				modificabile='${modoAperturaScheda eq "NUOVO"}' visibile="false" />
			<gene:campoScheda campo="NOMETEI" title="Nome" obbligatorio="true"/>
			<gene:campoScheda campo="COGTEI" title="Cognome" obbligatorio="true"/>
			<gene:campoScheda campo="PROTEC" />
			<gene:campoScheda campo="TELTEC" />
			
			<gene:campoScheda>
				<td colspan="2"><br><b>Ente</b></td>
			</gene:campoScheda>
			<gene:campoScheda entita="UFFINT" campo="NOMEIN" obbligatorio="true" />
			<gene:campoScheda entita="UFFINT" campo="TIPOIN" />
			<gene:campoScheda title="Codice fiscale ente" entita="UFFINT" campo="CFEIN" obbligatorio="true" />
			
			<gene:campoScheda>
				<td colspan="2"><br><b>Registrazione</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CFTEC" title="Codice fiscale / Login" obbligatorio="true" />
			<gene:campoScheda campo="SYSCON" visibile="false"/>
			<gene:campoScheda campo="SYSNOM" visibile="false" title="Login" entita="USRSYS" where="usrsys.syscon = utent.syscon"/>
			<gene:campoScheda campo="SYSPWD" visibile="false" entita="USRSYS" where="usrsys.syscon = utent.syscon"/>
			<gene:campoScheda>	
				<td class="etichetta-dato" >Password (*)</td>
				<td class="valore-dato">
					<input type="password" id="password" name="password" styleClass="testo" maxlength="30" size="20" />
					<span style="vertical-align: middle;"><i>&nbsp;(minino 8 caratteri, di cui 2 cifre)</i></span>
				</td>
			</gene:campoScheda>
			<gene:campoScheda>
				<td class="etichetta-dato" >Conferma password (*)</td>
				<td class="valore-dato"> 
					<input type="password" id="confPassword" name="confPassword" styleClass="testo" maxlength="30" size="20" />
				</td>
			</gene:campoScheda>
			<gene:campoScheda campo="EMATEC" obbligatorio="true"/>

			<gene:campoScheda> 
				<td colspan="2">
					<br>
					<b>Informativa trattamento dati personali ai sensi del D.Lgs. n. 196/2003</b>
					<br>
					Ai sensi dell'art. 13 del D. Lgs. n. 196/2003 (ex art. 10 della legge n. 675/96), si informa che i dati personali 
					forniti ed acquisiti contestualmente alla registrazione ai servizi scelti, nonche' i dati necessari 
					all'erogazione di tali servizi, saranno trattati, nel rispetto delle garanzie di riservatezza e delle misure di sicurezza 
			        previste dalla normativa vigente attraverso strumenti informatici, telematici e manuali, con logiche strettamente 
			        correlate alle finalita' del trattamento.
			       	<br>	
			       	<br>		       					
					<c:if test="${not empty moduloCondizioniDuso}">
						Dichiaro di aver preso visione e di accettare le condizioni d'uso del sito web. <br>
						(Cliccare <b><a href="${pageContext.request.contextPath}/${moduloCondizioniDuso}" class="link-generico" title="Condizioni d'uso" target="_blank">qui</a></b>
						per scaricare le condizioni d'uso)
						<br>
						<br>
					</c:if>	
					<span style="vertical-align: middle;">Accetto (*)&nbsp;</span><input style="vertical-align: middle;" type="checkbox" name="informativaPrivacy" id="informativaPrivacy" title="Accetto le condizioni d'uso"/>
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
						<INPUT type="button" class="bottone-azione" value="Registra" title="Registra" onclick="javascript:eseguiSubmit()">
					</gene:insert>
					<gene:insert name="pulsanteAnnulla">
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla registrazione" onclick="javascript:annullaScheda()">
					</gene:insert>
					&nbsp;
				</td>
			</gene:campoScheda>
			<gene:fnJavaScriptScheda funzione='upperCase("TECNI_CFTEC", "#TECNI_CFTEC#")' elencocampi='TECNI_CFTEC' esegui="false" />
			<gene:fnJavaScriptScheda funzione='upperCase("UFFINT_CFEIN", "#UFFINT_CFEIN#")' elencocampi='UFFINT_CFEIN' esegui="false" />
		</gene:formScheda>
		

		<gene:javaScript>	
		
			document.getElementById("TECNI_NOMETEI").size= 25;			
			document.getElementById("TECNI_COGTEI").size= 25;
			document.getElementById("TECNI_CFTEC").size= 20;
			document.getElementById("TECNI_TELTEC").size= 35;
			document.getElementById("UFFINT_NOMEIN").cols= 55;
			document.getElementById("UFFINT_CFEIN").size= 20;
			document.getElementById("TECNI_EMATEC").size= 50;
		
			$(document).ready(function()
			{
			
				jQuery.validator.addMethod("controlloCFPIVA",
				function(value, element) {
					return checkCodFis(value);
				},
				"Il valore specificato non &egrave; valido");
			
			    $("form[name^='formScheda']").validate(
			    {
			        rules:
			        {
			            TECNI_NOMETEI: "required",
			            TECNI_COGTEI: "required",
			            TECNI_CFTEC: 
		            	{
		            		required: true,
		            		minlength: 16,
		            		controlloCFPIVA: true
		            	},
			            UFFINT_NOMEIN: "required",
			            UFFINT_CFEIN:
			            {
		            		required: true,
		            		controlloCFPIVA: true
		            	},
			            password: "required",
			            confPassword:
			            {
			                required: true,
			                equalTo: "#password"
			            },
			            TECNI_EMATEC:
			            {
			                required: true,
			                email: true
			            },
			            informativaPrivacy:
			            {
			            	required: true
			            }			            
			        },
			        messages:
			        {
			            TECNI_NOMETEI: "Inserire il nome",
			            TECNI_COGTEI: "Inserire il cognome",
			            TECNI_CFTEC:
			            {
			            	required: "Inserire il codice fiscale",
			            	controlloCFPIVA: "Il valore specificato non rispetta il formato previsto"
			            },
			            UFFINT_NOMEIN: "Inserire la denominazione",
			            UFFINT_CFEIN:
			           	{
			            	required: "Inserire il codice fiscale",
			            	controlloCFPIVA: "Il valore specificato non rispetta il formato previsto"
			            },
			            password: "Inserire la password",
			            confPassword: 
		            	{
		            	 	required: "Conferma la password",
		            		equalTo: "Le due password non coincidono" 	
		            	},
			            TECNI_EMATEC:
			            {
			            	required: "Inserire l'indirizzo e-mail",
			            	email: "Inserire un indirizzo e-mail valido"
			            },
			            informativaPrivacy:
			            {
			            	required: "Per procedere e' necessario accettare le condizioni"
			            }
			        }
			    });
			});		
		
			function upperCase(campo, valore){
				document.getElementById(campo).value=valore.toUpperCase();
			}
						
			function annullaScheda(){
				window.location.href="InitLogin.do?" +csrfToken;
			}

			function eseguiSubmit(){
				if ($("form[name^='formScheda']").validate().form()) {
					if (controllaCampoPassword(document.forms[0].password,8,true)) { 
						document.forms[0].USRSYS_SYSNOM.value = document.forms[0].TECNI_CFTEC.value
						document.forms[0].USRSYS_SYSPWD.value = document.forms[0].password.value;
						document.forms[0].metodo.value="update";
						if(activeForm.onsubmit()){
							bloccaRichiesteServer();
							document.forms[0].submit();
						}
					}
				}
			}
			
		</gene:javaScript>
	</gene:redefineInsert>
</gene:template>