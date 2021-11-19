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

	<gene:setString name="titoloMaschera" value='Registrazione utente'/>

	<gene:redefineInsert name="corpo">
	<c:choose>
	<c:when test='${requestScope.appBloccata eq "1"}'>
		<div class="errori-javascript-dettaglio">
			<ul>
			<html:messages id="error" message="true" property="error">
				<br>
				<li class="errori-javascript-err">ATTENZIONE:
				<small>Applicazione non ancora attivata</small></li>
				<br>
			</html:messages>
			</ul>
		</div>
	</c:when>
	<c:otherwise>
		<gene:formScheda entita="USRSYS" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreFormRegistrazioneSCP" >
			<input type="hidden" name="EXIST_VIAEIN" id="EXIST_VIAEIN" value=""/>
			<input type="hidden" name="EXIST_NCIEIN" id="EXIST_NCIEIN" value=""/>
			<input type="hidden" name="EXIST_CITEIN" id="EXIST_CITEIN" value=""/>
			<input type="hidden" name="EXIST_PROEIN" id="EXIST_PROEIN" value=""/>
			<input type="hidden" name="EXIST_CAPEIN" id="EXIST_CAPEIN" value=""/>
			<input type="hidden" name="EXIST_TELEIN" id="EXIST_TELEIN" value=""/>
			<input type="hidden" name="EXIST_FAXEIN" id="EXIST_FAXEIN" value=""/>
			<input type="hidden" name="EXIST_EMAIIN" id="EXIST_EMAIIN" value=""/>
			<gene:campoScheda>
				<td colspan="2"><br><b>Dati anagrafici dell'utente</b></td>
			</gene:campoScheda>
			<gene:campoScheda title="Codice dell'anagrafico" campo="CODICE"  campoFittizio="true" definizione="T10;;;;" modificabile='${modoAperturaScheda eq "NUOVO"}' visibile="false" />
			<gene:campoScheda campo="NOME" campoFittizio="true" definizione="T80;;;;" title="Nome" obbligatorio="true" modificabile="${empty requestScope.nome}" value="${requestScope.nome}"/>
			<gene:campoScheda campo="COGNOME" campoFittizio="true" definizione="T80;;;;" title="Cognome" obbligatorio="true" modificabile="${empty requestScope.cognome}" value="${requestScope.cognome}"/>
			<gene:campoScheda campo="TELEFONO" campoFittizio="true" definizione="T50;;;;" title="Telefono"/>
			<gene:campoScheda campo="EMAIL" campoFittizio="true" definizione="T100;;;;" title="E-mail" obbligatorio="true" value="${requestScope.email}"/>
			
			<gene:campoScheda>
				<td colspan="2"><br><b>Registrazione</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CODFISC" campoFittizio="true" definizione="T16;;;;" title="Codice fiscale / Login" obbligatorio="true" visibile='${requestScope.flagLdap ne "3" }' modificabile="${empty requestScope.codfisc}" value="${requestScope.codfisc}"/>
			<gene:campoScheda campo="CONFCODFISC" campoFittizio="true" definizione="T16;;;;" title="Conferma Codice fiscale / Login" obbligatorio="true" visibile='${requestScope.flagLdap ne "3" }' modificabile="${empty requestScope.codfisc}" value="${requestScope.codfisc}"/>
			<gene:campoScheda campo="SYSCON" visibile="false"/>
			<gene:campoScheda campo="SYSUTE" visibile="false"/>
			<gene:campoScheda campo="SYSNOM" visibile="false" title="Login" entita="USRSYS" where="usrsys.syscon = utent.syscon"/>
			
			<c:choose>
				<c:when test='${requestScope.flagLdap ne "1" && requestScope.flagLdap ne "3"}'>
					<gene:campoScheda campo="SYSPWD" visibile="false" entita="USRSYS" where="usrsys.syscon = utent.syscon" />
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
				</c:when>
				<c:otherwise>
					<gene:campoScheda campo="SYSPWD" visibile="false" entita="USRSYS" where="usrsys.syscon = utent.syscon" value=""/>
				</c:otherwise>
			</c:choose>
			
			<gene:campoScheda campo="FLAG_LDAP" value="${requestScope.flagLdap}" visibile="false"/>
			<gene:campoScheda campo="DN" campoFittizio="true" definizione="T250;;;;" title="DN" value="${requestScope.dn}" visibile="false"/>
			
			<c:choose>
				<c:when test='${requestScope.ruolo eq "0" }'>
				<gene:campoScheda campo="SYSAB3" title="Ruolo" obbligatorio="true" visibile="false" entita="USRSYS" where="usrsys.syscon = utent.syscon" value="U" />
				</c:when>
				<c:when test='${requestScope.ruolo eq "1" }'>
				<gene:campoScheda campo="SYSAB3" title="Ruolo" obbligatorio="true" entita="USRSYS" where="usrsys.syscon = utent.syscon">
					<gene:addValue value="" descr="" />
					<c:if test='${!empty listaValoriRuolo}'>
						<c:forEach items="${listaValoriRuolo}" var="valoriRuolo">
						<gene:addValue value="${valoriRuolo[0]}"
							descr="${valoriRuolo[1]}" />
						</c:forEach>
					</c:if>
				</gene:campoScheda>			
				</c:when>
				<c:when test='${requestScope.ruolo eq "2" }'>
				<gene:campoScheda campo="SYSABG" title="Ruolo" obbligatorio="true" entita="USRSYS" where="usrsys.syscon = utent.syscon">
					<gene:addValue value="" descr="" />
					<c:if test='${!empty listaValoriRuolo}'>
						<c:forEach items="${listaValoriRuolo}" var="valoriRuolo">
						<gene:addValue value="${valoriRuolo[0]}"
							descr="${valoriRuolo[1]}" />
						</c:forEach>
					</c:if>
				</gene:campoScheda>			
				</c:when>
				<c:when test='${requestScope.ruolo eq "3" }'>
				<gene:campoScheda campo="SYSABC" title="Ruolo" obbligatorio="true" entita="USRSYS" where="usrsys.syscon = utent.syscon">
					<gene:addValue value="" descr="" />
					<c:if test='${!empty listaValoriRuoloContratti}'>
						<c:forEach items="${listaValoriRuoloContratti}" var="valoriRuoloContratti">
						<gene:addValue value="${valoriRuoloContratti[0]}"
							descr="${valoriRuoloContratti[1]}" />
						</c:forEach>
					</c:if>
				</gene:campoScheda>			
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			
		
			
			<c:if test='${!empty listaProfiliDisponibili}'>
				<gene:campoScheda>
					<td colspan="2"><br><b>Applicativi disponibili</b></td>
				</gene:campoScheda>
				<gene:campoScheda>
					<td class="etichetta-dato">Applicativi (*)</td>
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
				<td colspan="2"><br><b>Ulteriori indicazioni</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="MSGAMM" campoFittizio="true" definizione="T2000;0;;NOTE;" title="Messaggio per l'amministratore" />
			
			
			<c:if test='${requestScope.isUffintAbilitati eq "1" }'>
				<gene:campoScheda>
					<td colspan="2"><br><b>Sezione riservata al Dirigente</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="NOMEDIRIGENTE" campoFittizio="true" definizione="T100" title="Nome e cognome del Dirigente" modificabile="true" visibile="true" />
				<gene:campoScheda campo="SERVIZIO" campoFittizio="true" definizione="T100" title="del servizio" modificabile="true" visibile="true" /> 
				<gene:campoScheda>
					<td colspan="2"><br><b>Ente</b></td>
				</gene:campoScheda>
				<gene:campoScheda entita="UFFINT" campo="CODEIN" />
				<gene:campoScheda title="Codice fiscale" entita="UFFINT" campo="CFEIN" obbligatorio="true"/>
				<gene:campoScheda addTr="false">
	
				<tr id="rowUFFINT_AVVISO">
					<td colspan="2">
						<br>
						<i>Non esiste alcun ente con il codice fiscale o la partita IVA indicate, valorizzare anche i campi seguenti</i>
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
				
				<gene:campoScheda addTr="false">
					
					<tr id="rowVIS_REFERENTE">
						<td colspan="2">
						<br>
							<b>L'Ente indicato è già registrato o esiste già una registrazione di un altro soggetto in qualità di Referente, indica una delle seguenti preferenze:</b>
							<br>
							
							<p style="margin-top:0px; padding-left: 24px;">
							<input type="radio" name="tipoSubentro" id="SUBENTRO" value="1">
							Intendo subentrare al referente <input type="text" name="referente" id="referente"> codice fiscale <input type="text" name="cfreferente" id="cfreferente"></p>
							
							<p style="margin-top:0px; padding-left: 24px;">
							<input type="radio" name="tipoSubentro" id="AGGIUNGI" value="2">
							Richiedo un accesso aggiuntivo per l'ente</p>
							
							<p style="margin-top:0px; padding-left: 24px;">
							<input type="radio" name="tipoSubentro" id="ALTRO" value="3">
							Altro, specificare <input type="text" name="specificaaltro" id="specificaaltro"></p>
						</td>
					</tr>
				</gene:campoScheda>
				
				<gene:campoScheda entita="UFFINT" campo="NOMEIN" obbligatorio="true" />
				<gene:campoScheda entita="UFFINT" campo="TIPOIN" obbligatorio="true" />
				<gene:campoScheda entita="UFFINT" campo="VIAEIN" obbligatorio="true" />
				<gene:campoScheda entita="UFFINT" campo="NCIEIN" />
				<gene:campoScheda entita="UFFINT" campo="CITEIN" obbligatorio="true" />
			
				<gene:campoScheda entita="UFFINT" campo="PROEIN" obbligatorio="true" />
				<gene:campoScheda addTr="false">
				<tr id="rowVIS_UFFINT_PROEIN">
					<td class="etichetta-dato">Provincia</td>
					<td class="valore-dato"> 
						<div id="VIS_UFFINT_PROEIN"></div>
					</td>
				</tr>
				</gene:campoScheda>
			
				<gene:campoScheda entita="UFFINT" campo="CAPEIN" obbligatorio="true" />
				<gene:campoScheda addTr="false">
				<tr id="rowVIS_UFFINT_CAPEIN">
					<td class="etichetta-dato">C.A.P.</td>
					<td class="valore-dato"> 
						<div id="VIS_UFFINT_CAPEIN"></div>
					</td>
				</tr>
				</gene:campoScheda>
			
				<gene:campoScheda entita="UFFINT" campo="CODCIT" obbligatorio="true"/>
				<gene:campoScheda addTr="false">
				<tr id="rowVIS_UFFINT_CODCIT">
					<td class="etichetta-dato">Codice ISTAT del comune</td>
					<td class="valore-dato"> 
						<div id="VIS_UFFINT_CODCIT"></div>
					</td>
				</tr>
				</gene:campoScheda>
			
				<gene:campoScheda entita="UFFINT" campo="TELEIN" />
				<gene:campoScheda entita="UFFINT" campo="FAXEIN" />
				<gene:campoScheda entita="UFFINT" campo="EMAIIN" title="PEC dell'Ente" />
			
			
			</c:if>
			
			<gene:campoScheda>
			<td colspan="2">
				<br>
				<b>Scarica il modello di abilitazione al servizio</b>
				<br>
				Per completare la registrazione &egrave; necessario scaricare il presente
				
					<a class="link-229" href="javascript:apriModelloAbilitazioneServizio();" >
				
				modello di abilitazione al servizio già compilato</a>, firmarlo digitalmente e <br>
				allegarlo alla presente scheda di registrazione (vedi "Allega documenti").
			</td>
			</gene:campoScheda> 
			<gene:campoScheda nome="ALLDOC">
				<td colspan="2">
				<br>
				<b>Allega documenti</b>
				<br>
				Nel caso in cui siano presenti pi&ugrave; documenti &egrave; necessario archiviarli in un unico file (.zip).
				<i><br>Utilizza il pulsante "Scegli file" per caricare i documenti</i>
				</td>
			</gene:campoScheda>
				<gene:campoScheda campo="NOMEDOC" campoFittizio="true" definizione="T50" title="Documento caricato" modificabile="true" visibile="false" />
				<gene:campoScheda title="Nome file" visibile="true">
					<input type="file" name="selezioneFile" id="selezioneFile" class="file" size="70" onkeydown="return bloccaCaratteriDaTastiera(event);" onchange='javascript:scegliFile();'/>
			</gene:campoScheda>


			<gene:campoScheda> 
				<td colspan="2">
					<br>
					<b>Informativa trattamento dati personali</b>
					<br>
					Si informa che i dati personali forniti ed acquisiti contestualmente alla registrazione ai servizi scelti, nonche' i dati necessari all'erogazione di tali servizi, saranno trattati, nel rispetto delle garanzie di riservatezza e delle misure di sicurezza previste dalla normativa vigente attraverso strumenti informatici, telematici e manuali, con logiche strettamente correlate alle finalita' del trattamento. 
					Per ulteriori dettagli <a href="https://www.mit.gov.it/privacy-cookie" class="link-generico">cliccare qui</a>
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
						<INPUT type="button" class="bottone-azione" value="Registra" title="Registra" onclick="javascript:eseguiSubmit()">
					</gene:insert>
					<gene:insert name="pulsanteAnnulla">
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla registrazione" onclick="javascript:annullaScheda()">
					</gene:insert>
					&nbsp;
				</td>
			</gene:campoScheda>
			<gene:fnJavaScriptScheda funzione='upperCase("UFFINT_CFEIN", "#UFFINT_CFEIN#")' elencocampi='UFFINT_CFEIN' esegui="false" />
			<gene:fnJavaScriptScheda funzione='upperCase("CODFISC", "#CODFISC#")' elencocampi='CODFISC' esegui="false" />
			<gene:fnJavaScriptScheda funzione='upperCase("CONFCODFISC", "#CONFCODFISC#")' elencocampi='CONFCODFISC' esegui="false" />
		</gene:formScheda>
	</c:otherwise>
	</c:choose>
		

	</gene:redefineInsert>

	<gene:javaScript>
		
		
		
		document.forms[0].encoding="multipart/form-data";
		
			var changeUFFINT = false;	

			document.getElementById("NOME").size= 25;			
			document.getElementById("COGNOME").size= 25;
			document.getElementById("TELEFONO").size= 35;
			document.getElementById("EMAIL").size= 50;
			document.getElementById("referente").size= 40;
			document.getElementById("cfreferente").size= 20;
			document.getElementById("specificaaltro").size= 60;
			document.getElementById("NOMEDIRIGENTE").size= 60;
			document.getElementById("SERVIZIO").size= 60;
			<c:if test='${requestScope.isUffintAbilitati eq "1" }'>
				document.getElementById("UFFINT_NOMEIN").cols= 55;
				document.getElementById("UFFINT_CFEIN").size= 20;
				document.getElementById("UFFINT_VIAEIN").size= 30;
				document.getElementById("UFFINT_TELEIN").size= 30;
				document.getElementById("UFFINT_FAXEIN").size= 30;
				document.getElementById("UFFINT_EMAIIN").size= 50;
				document.getElementById("UFFINT_CITEIN").size= 50;
			</c:if>				
			
			
			function gestioneAction() {
				var nuovaAction = contextPath + "/SchedaNoSessione.do?" + csrfToken;
				document.forms[0].action = nuovaAction;
				nuovaAction = "commons/redirect.jsp";
				document.forms[0].jspPathTo.value = nuovaAction;
			}
			
		
			$(document).ready(function()
			{
				$("#CODFISC").css("text-transform","uppercase");
				$("#CONFCODFISC").css("text-transform","uppercase");
				$("#CONFCODFISC").on('paste',function(e) {
  					e.preventDefault();
				});
				$("#UFFINT_CFEIN").css("text-transform","uppercase");
				$('#UFFINT_CFEIN').on("change",function() {
				  getDescrizioneEnte("");
				});
				$('#UFFINT_CFEIN').on("keyup",function() {
				  getDescrizioneEnte("");
				});
				
				$("#rowUFFINT_CODEIN").hide();							
				$('#UFFINT_CITEIN').blur(function() {
				  resetUFFINT_CITEIN();
				});
				
				$("#rowUFFINT_CODEIN").hide();							
				$("#rowUFFINT_PROEIN").hide();
				$("#rowUFFINT_CAPEIN").hide();
				$("#rowUFFINT_CODCIT").hide();
				
				$("#realperson").css("text-transform","uppercase");
				$("#realperson").realperson({length: 6, regenerate: 'Rigenera codice'});

				jQuery.validator.addMethod("isSelectUffint",
					function(value, element) {return isSelectUffint(value);},
					"Selezionare un valore");

				jQuery.validator.addMethod("controlloCFPIVA",
				function(value, element) {
					return checkCodFis(value);
				},
				"Il valore specificato non è valido");
				
				jQuery.validator.addMethod("controlloPIVA",
				function(value, element) {
					return checkParIva(value);
				},
				"Il valore specificato non è valido");
				
				jQuery.validator.addMethod("isLoginInesistente",
					function(value, element) {return !isLoginEsistente(value);},
					"Login esistente");
								
				jQuery.validator.addMethod("isPasswordCaratteriAmmessi",
					function(value, element) {return isPasswordCaratteriAmmessi(value);},
					"La password contiene caratteri non ammessi");	

				jQuery.validator.addMethod("isPasswordMinimo2Numerici",
					function(value, element) {return isPasswordMinimo2Numerici(value);},
					"La password deve contenere almeno 2 caratteri numerici");
			
				jQuery.validator.addMethod("passwordSimilarityNOME",
					function(value, element) {return !passwordSimilarity(value,$("#NOME").val());},
					"Password simile al nome dell'utente");

				jQuery.validator.addMethod("passwordSimilarityCOGNOME",
					function(value, element) {return !passwordSimilarity(value,$("#COGNOME").val());},
					"Password simile al cognome dell'utente");
					
				jQuery.validator.addMethod("passwordSimilarityCODFISC",
					function(value, element) {return !passwordSimilarity(value,$("#CODFISC").val());},
					"Password simile al codice fiscale / login di registrazione");
				
				
				jQuery.validator.addMethod("isCodiceControlloCorretto",
					function(value, element) {return isCodiceControlloCorretto();},
				"Il codice di controllo non e' corretto");
				
				jQuery.validator.addMethod("isNomeReferenteRichiesto",
					function(value, element) {
					 if (document.getElementById("SUBENTRO").checked) 
						return (value.length > 0);
					 else 
					    return true;},
					"L'indicazione del referente è obbligatoria");
			
				jQuery.validator.addMethod("isCfReferenteRichiesto",
					function(value, element) {
					 if (document.getElementById("SUBENTRO").checked) 
						return (value.length > 0);
					 else 
					    return true;},
					"L'indicazione del Codice Fiscale referente è obbligatoria");
					
				jQuery.validator.addMethod("isSpecificareAltroRichiesto",
					function(value, element) {
					 if (document.getElementById("ALTRO").checked) 
						return (value.length > 0);
					 else 
					    return true;},
					"Specificare la motivazione");
					
			    $("form[name^='formScheda']").validate(
			    {
			        rules:
			        {
			            NOME: "required",
			            COGNOME: "required",
			            CODFISC: 
		            	{
		            		required: true,
		            		minlength: 16,
		            		controlloCFPIVA: true
		            	},
		            	CONFCODFISC: 
		            	{
		            		required: true,
		            		equalTo: "#CODFISC"
		            	},
		            	tipoSubentro: "required",
		            	referente: {
		            		isNomeReferenteRichiesto:true
		            	},
		            	cfreferente: {
		            		isCfReferenteRichiesto:true,
		            		controlloCFPIVA: true
		            	},
		            	specificaaltro: {
		            		isSpecificareAltroRichiesto:true
		            	},
			            UFFINT_NOMEIN: "required",
			            NOMEDIRIGENTE: "required",
			            SERVIZIO: "required",
			            UFFINT_CFEIN:
			            {
							required: true,
			            	controlloPIVA: true
			            },
			            UFFINT_TIPOIN: "required",
		            	UFFINT_VIAEIN: "required",
		            	UFFINT_PROEIN: "required",
		            	UFFINT_CAPEIN: "required",
		            	UFFINT_CITEIN: "required",
		            	UFFINT_CODCIT: "required",
		            	UFFINT_EMAIIN:
		            	{
		            		email: true
		            	},
			            password:
			            {
			            	required: true,
			            	isPasswordCaratteriAmmessi: true,
			            	minlength: 8,
			            	isPasswordMinimo2Numerici: true,
			            	passwordSimilarityNOME: true,
			            	passwordSimilarityCOGNOME: true,
			            	passwordSimilarityCODFISC: true
			            },
			            confPassword:
			            {
			            	required: true,
			            	equalTo: "#password"
			            },
			            EMAIL:
			            {
			                required: true,
			                email: true
			            },
			            USRSYS_SYSAB3:
			            {
			                required: true
			            },
			            USRSYS_SYSABG:
			            {
			                required: true
			            },
			            USRSYS_SYSABC:
			            {
			                required: true
			            },
			            applicativi:
			            {
			                required: true
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
			            NOME: "Inserire il nome",
			            COGNOME: "Inserire il cognome",
			            CODFISC:
			            {
			            	required: "Inserire il codice fiscale",
			            	isLoginInesistente: "La login indicata e' gia' utilizzata",
			            	controlloCFPIVA: "Il valore specificato non rispetta il formato previsto"
			            },
			            CONFCODFISC:
			            {
			            	required: "Confermare il codice fiscale",
			            	equalTo: "I codici fiscali non coincidono" 
			            },
			            tipoSubentro: "Scegliere una delle seguenti opzioni",
			            NOMEDIRIGENTE: "Inserire il nome e cognome del dirigente",
			            SERVIZIO: "Inserire il servizio",
			            UFFINT_NOMEIN: "Inserire la denominazione",
			            UFFINT_CFEIN:
			           	{
			            	required: "Inserire il codice fiscale o la partita IVA",
			            	controlloPIVA: "Il valore specificato non rispetta il formato previsto",
			            	isSelectUffint: "Selezionare un valore dalla lista"
							
			            },
			            UFFINT_TIPOIN: "Inserire la tipologia",
		            	UFFINT_VIAEIN: "Inserire la via",
		            	UFFINT_PROEIN: "Inserire la provincia",
		            	UFFINT_CAPEIN: "Inserire il codice avviamento postale",
		            	UFFINT_CITEIN: "Inserire il comune",
		            	UFFINT_CODCIT: "Inserire il codice ISTAT",
		            	UFFINT_EMAIIN: 
		            	{
		            		email: "Inserire un indirizzo e-mail valido"
		            	},
			            password: 
			            {
			            	required: "Inserire la password",
			            	minlength: "La password deve essere lunga almeno 8 caratteri" 
			            },
			            confPassword: 
		            	{
		            	 	required: "Confermare la password",
		            		equalTo: "Le due password non coincidono" 	
		            	},
			            EMAIL:
			            {
			            	required: "Inserire l'indirizzo e-mail",
			            	email: "Inserire un indirizzo e-mail valido"
			            },
			            USRSYS_SYSAB3:
			            {
			            	required: "Inserire il ruolo"
			            },
			            USRSYS_SYSABG:
			            {
			            	required: "Inserire il ruolo"
			            },
			            USRSYS_SYSABC:
			            {
			            	required: "Inserire il ruolo"
			            },
			            applicativi:
			            {
			            	required: "Scegliere almeno un applicativo tra quelli proposti"
			            },
			            selezioneFile:
						{
			            	required: "Inserire i documenti richiesti"
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
				
				$(function() {
					$("#UFFINT_CITEIN" ).autocomplete({
						delay: 0,
					    autoFocus: true,
					    position: { 
					    	my : "left top",
					    	at: "left bottom"
					    },
						source: function( request, response ) {
							$("#UFFINT_PROEIN").val("");
							$("#UFFINT_CAPEIN").val("");
							$("#UFFINT_CODCIT").val("");
							$("#VIS_UFFINT_PROEIN").text("");
							$("#VIS_UFFINT_CAPEIN").text("");
							$("#VIS_UFFINT_CODCIT").text("");							
							
							var comune = $("#UFFINT_CITEIN").val();
							$.ajax({
								async: false,
							    type: "GET",
			                    dataType: "json",
			                    beforeSend: function(x) {
			        			if(x && x.overrideMimeType) {
			            			x.overrideMimeType("application/json;charset=UTF-8");
							       }
			    				},
			                    url: "${pageContext.request.contextPath}/w9/GetListaComuni.do",
			                    data: "comune=" + comune + "%25",
								success: function( data ) {
									if (!data) {
										response([]);
									} else {
										response( $.map( data, function( item ) {
											return {
												label: item[0].value.substr(0,32) + " (Provincia: " + item[2].value + ", C.A.P.: " + item[4].value + ", codice ISTAT: " + item[3].value + ")",
												value: item[0].value.substr(0,32),
												valuePROEIN: item[1].value,
												valuePROEIN_DESC: item[2].value,
												valueCAPEIN: item[4].value,
												valueCODCIT: item[3].value
											}
										}));
									} 
								},
			                    error: function(e){
			                        alert("Comune: errore durante la lettura della lista dei comuni");
			                    }
							});
						},
						minLength: 1,
						select: function( event, ui ) {
							$("#UFFINT_PROEIN").val(ui.item.valuePROEIN);
							$("#UFFINT_CAPEIN").val(ui.item.valueCAPEIN);
							$("#UFFINT_CODCIT").val(ui.item.valueCODCIT);
							$("#VIS_UFFINT_PROEIN").text(ui.item.valuePROEIN_DESC);
							$("#VIS_UFFINT_CAPEIN").text(ui.item.valueCAPEIN);
							$("#VIS_UFFINT_CODCIT").text(ui.item.valueCODCIT);
						},
						open: function() {
							$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
						},
						close: function() {
							$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
						},
						change: function(event, ui) {
							var comune = $("#UFFINT_CITEIN").val();
							$.ajax({
								async: false,
							    type: "GET",
			                    dataType: "json",
			                    beforeSend: function(x) {
			        			if(x && x.overrideMimeType) {
			            			x.overrideMimeType("application/json;charset=UTF-8");
							       }
			    				},
			                    url: "${pageContext.request.contextPath}/w9/GetListaComuni.do",
			                    data: "comune=" + comune,
								success: function( data ) {
									if (!data) {
										$("#UFFINT_CITEIN").val("");
										$("#UFFINT_PROEIN").val("");
										$("#UFFINT_CAPEIN").val("");
										$("#UFFINT_CODCIT").val("");
										$("#VIS_UFFINT_PROEIN").text("");
										$("#VIS_UFFINT_CAPEIN").text("");
										$("#VIS_UFFINT_CODCIT").text("");
									} 
								},
								error: function(e){
		                        	$("#UFFINT_CITEIN").val("");
									$("#UFFINT_PROEIN").val("");
									$("#UFFINT_CAPEIN").val("");
									$("#UFFINT_CODCIT").val("");
									$("#VIS_UFFINT_PROEIN").text("");
									$("#VIS_UFFINT_CAPEIN").text("");
									$("#VIS_UFFINT_CODCIT").text("");
			                    }
							});
						}
					});
				});
				
			});		
					
		
			function upperCase(campo, valore){
				document.getElementById(campo).value=valore.toUpperCase();
			}
						
			function annullaScheda(){
			
			<c:choose>
				<c:when test='${requestScope.flagLdap ne "3" }'>
					window.location.href="InitLogin.do?" +csrfToken;
				</c:when>
				<c:otherwise>
					document.location.href="SessionTimeOut.do?" +csrfToken;
				</c:otherwise>
			</c:choose>
				
			}

			function eseguiSubmit(){
				//$("#password").rules("add", "required minlength isPasswordCaratteriAmmessi isPasswordMinimo2Numerici passwordSimilarityNOME passwordSimilarityCOGNOME passwordSimilarityCODFISC");
				//$("#confPassword").rules("add", "required equalTo");
				$("#selezioneFile").rules("add", "required");
				$("#informativaPrivacy").rules("add", "required");
				$("#realperson").rules("add", "required minlength isCodiceControlloCorretto");
				
				if ($("form[name^='formScheda']").validate().form()) {
					var eseguiSubmit = false;
					<c:choose>
						<c:when test='${requestScope.flagLdap ne "1" && requestScope.flagLdap ne "3"  }'>
							if (controllaCampoPassword(document.forms[0].password,8,true)) {
								document.forms[0].USRSYS_SYSPWD.value = document.forms[0].password.value;
								eseguiSubmit = true;
							}
						</c:when>
						<c:otherwise>
								eseguiSubmit = true;
						</c:otherwise>
					</c:choose>
					 if(eseguiSubmit==true){
						document.forms[0].USRSYS_SYSNOM.value = document.forms[0].CODFISC.value;
						document.forms[0].metodo.value="update";
						if(activeForm.onsubmit()){
							bloccaRichiesteServer();
							document.forms[0].submit();
						}
					 }
				}
			}
			
			function apriModelloAbilitazioneServizio(){
				//$("#password").rules("remove", "required minlength isPasswordCaratteriAmmessi isPasswordMinimo2Numerici passwordSimilarityNOME passwordSimilarityCOGNOME passwordSimilarityCODFISC");
				//$("#confPassword").rules("remove", "required equalTo");
				$("#selezioneFile").rules("remove", "required");
				$("#informativaPrivacy").rules("remove", "required");
				$("#realperson").rules("remove", "required minlength isCodiceControlloCorretto");
			            
				if ($("form[name^='formScheda']").validate().form()) {
					generatePDF();
				}
				//var w = 700;
				//var h = 500;
				//var l = Math.floor((screen.width-w)/2);
				//var t = Math.floor((screen.height-h)/2);
				//document.location.href='ModelloRegistrazione.do?metodo=download';
			}
			
			
			function gestioneSezioneUFFINT(visibile) {
				if (visibile == false) {
					$("#rowUFFINT_AVVISO").hide();
					$("#rowUFFINT_NOMEIN").hide();
					$("#rowUFFINT_TIPOIN").hide();
					$("#rowUFFINT_VIAEIN").hide();
					$("#rowUFFINT_NCIEIN").hide();
					$("#rowVIS_UFFINT_PROEIN").hide();
					$("#rowVIS_UFFINT_CAPEIN").hide();
					$("#rowUFFINT_CITEIN").hide();
					$("#rowVIS_UFFINT_CODCIT").hide();
					$("#rowUFFINT_TELEIN").hide();
					$("#rowUFFINT_FAXEIN").hide();
					$("#rowUFFINT_EMAIIN").hide();	
					$("#UFFINT_NOMEIN").val("");
					$("#UFFINT_TIPOIN").val("");
					$("#UFFINT_VIAEIN").val("");
					$("#UFFINT_NCIEIN").val("");
					$("#VIS_UFFINT_PROEIN").text("");
					$("#VIS_UFFINT_CAPEIN").text("");
					$("#UFFINT_CITEIN").val("");
					$("#VIS_UFFINT_CODCIT").text("");
					$("#UFFINT_TELEIN").val("");
					$("#UFFINT_FAXEIN").val("");
					$("#UFFINT_EMAIIN").val("");
				} else {
					$("#rowUFFINT_NOMEIN").show();
					$("#rowUFFINT_TIPOIN").show();
					$("#rowUFFINT_VIAEIN").show();
					$("#rowUFFINT_NCIEIN").show();
					$("#rowVIS_UFFINT_PROEIN").show();
					$("#rowVIS_UFFINT_CAPEIN").show();
					$("#rowUFFINT_CITEIN").show();
					$("#rowVIS_UFFINT_CODCIT").show();
					$("#rowUFFINT_TELEIN").show();
					$("#rowUFFINT_FAXEIN").show();
					$("#rowUFFINT_EMAIIN").show();
				}
			};

			
			function scegliFile() {
				var selezioneFile = document.getElementById("selezioneFile").value;
				var lunghezza_stringa=selezioneFile.length;
				var posizione_barra=selezioneFile.lastIndexOf("\\");
				var nome=selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
				setValue("NOMEDOC",nome);
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
									$("#rowVIS_REFERENTE").show();
									$("#UFFINT_CODEIN").val(data.CODEIN.value);
									$("#EXIST_VIAEIN").val(data.VIAEIN.value);
									$("#EXIST_NCIEIN").val(data.NCIEIN.value);
									$("#EXIST_CITEIN").val(data.CITEIN.value);
									$("#EXIST_PROEIN").val(data.PROEIN.value);
									$("#EXIST_CAPEIN").val(data.CAPEIN.value);
									$("#EXIST_TELEIN").val(data.TELEIN.value);
									$("#EXIST_FAXEIN").val(data.FAXEIN.value);
									$("#EXIST_EMAIIN").val(data.EMAIIN.value);
									gestioneSezioneUFFINT(false);
									result = true;
								} else {
									$("#VIS_UFFINT_NOMEIN").html("");
									$("#rowVIS_UFFINT_NOMEIN").hide();
									$("#rowVIS_REFERENTE").hide();
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
						$("#rowVIS_REFERENTE").hide();
						$("#UFFINT_CODEIN").val("");
					}
					return result;
			}

				if ($("#UFFINT_CFEIN").val() != "") {
					getDescrizioneEnte("");
				} else {
					$("#rowVIS_UFFINT_NOMEIN").hide();
					$("#rowVIS_REFERENTE").hide();
					gestioneSezioneUFFINT(false);
				}
				
				
			function isSelectUffint(cfein) {
					return changeUFFINT;
			}				
				
			function isLoginEsistente(login) {
					var isLoginEsistente = false;
	                $.ajax({
	                    type: "GET",
	                    dataType: "json",
	                    async: false,
	                    beforeSend: function(x) {
	        			if(x && x.overrideMimeType) {
	            			x.overrideMimeType("application/json;charset=UTF-8");
					       }
	    				},
	                    url: "${pageContext.request.contextPath}/IsLoginEsistente.do",
	                    data: "login=" + login,
	                    success: function(data){
	                    	if (data.loginEsistente == true) {
	                        	isLoginEsistente = true;
	                        } else {
	                        	isLoginEsistente = false;
	                        }
	                    },
	                    error: function(e){
	                        alert("Codice fiscale / login: errore durante il controllo di univocita'");
	                    }
	                });
	              return isLoginEsistente;
	        }

			
			
			function isPasswordCaratteriAmmessi(password){
					var caratteriAmmessi = " ~#\"$%&'()*+,-./0123456789:;<=>?!@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_abcdefghijklmnopqrstuvwxyz";
					var result = true;
					var index = 0;
  					while(index < password.length & result){
	  					if(caratteriAmmessi.indexOf(password.charAt(index)) < 0){
		  					result = false;
	  					} else {
	  						index = index+1;
	  					}
	  				}
					return result;
			}
				
				
			function passwordSimilarity(password,similarityValue) {
					var result = false;
					if (similarityValue && password.toLowerCase().match(similarityValue.toLowerCase())) {
						result = true;
					} else {
						result = false;
					}
					return result;
			}
				
				
			function isPasswordMinimo2Numerici(password){
					var result = true;
				  	var index = 0;
				    var numInteri = 0;
				    var oggettoEspressioneRegolare = new RegExp("^[0-9]$");
					while(index < password.length){
						if (oggettoEspressioneRegolare.test(password.charAt(index))){
							numInteri = numInteri + 1
						}
						index = index + 1;
					}
					if(numInteri < 2) {
						result = false;
				  	}		
					return result;
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
						$("#rowVIS_REFERENTE").show();
						$("#UFFINT_CODEIN").val(ui.item.valueCODEIN);
						gestioneSezioneUFFINT(false);
						changeUFFINT = true;
						getDescrizioneEnte("");
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
									$("#rowVIS_REFERENTE").hide();
									$("#UFFINT_CODEIN").val("");
									gestioneSezioneUFFINT(true);
								} 
							},
							error: function(e){
	                        		$("#VIS_UFFINT_NOMEIN").html("");
									$("#rowVIS_UFFINT_NOMEIN").hide();
									$("#rowVIS_REFERENTE").hide();
									$("#UFFINT_CODEIN").val("");
									gestioneSezioneUFFINT(true);
		                    }
						});
					}
				});
			});
			
			function generatePDF() 
			{
				var doc = new jsPDF();
				var formRegistrazione = "<html><body>";
				formRegistrazione += "<h3 align='center'>RICHIESTA ABILITAZIONE REFERENTE AMMINISTRAZIONE AL SERVIZIO CONTRATTI PUBBLICI</h3>";
				
				formRegistrazione += "<h4>AL SERVIZIO SCP DEL MINISTERO DELLE INFRASTRUTTURE - DIPARTIMENTO PER LE INFRASTRUTTURE, I SISTEMI INFORMATIVI E STATISTICI - DIREZIONE GENERALE PER LA REGOLAZIONE E I CONTRATTI PUBBLICI</h4>";
				
				formRegistrazione += "<div>Il sottoscritto: <b>" + document.getElementById("NOMEDIRIGENTE").value + "</b></div>";
				formRegistrazione += "<div>Dirigente del servizio: <b>" + document.getElementById("SERVIZIO").value + "</b></div>";
				
				
				if ($("#UFFINT_CODEIN").val() == "") {
					//nuova stazione appaltante
					formRegistrazione += "<div>Dell'amministrazione: <b>" + document.getElementById("UFFINT_NOMEIN").value + "</b></div>";
					formRegistrazione += "<div>Codice fiscale: <b>" + document.getElementById("UFFINT_CFEIN").value + "</b></div>";
					formRegistrazione += "<div>Indirizzo: <b>" + document.getElementById("UFFINT_VIAEIN").value + "</b></div>";
					formRegistrazione += "<div>Numero civico: <b>" + document.getElementById("UFFINT_NCIEIN").value + "</b></div>";
					formRegistrazione += "<div>Comune: <b>" + document.getElementById("UFFINT_CITEIN").value + "</b></div>";
					formRegistrazione += "<div>Provincia: <b>" + document.getElementById("UFFINT_PROEIN").value + "</b></div>";
					formRegistrazione += "<div>C.A.P.: <b>" + document.getElementById("UFFINT_CAPEIN").value + "</b></div>";
					formRegistrazione += "<div>Telefono: <b>" + document.getElementById("UFFINT_TELEIN").value + "</b></div>";
					formRegistrazione += "<div>FAX: <b>" + document.getElementById("UFFINT_FAXEIN").value + "</b></div>";
					formRegistrazione += "<div>PEC o e-mail dell'Ente: <b>" + document.getElementById("UFFINT_EMAIIN").value + "</b></div>";
				} else {
					//stazione appaltante esistente
					formRegistrazione += "<div>Dell'amministrazione: <b>" + $("#VIS_UFFINT_NOMEIN").html() + "</b></div>";
					formRegistrazione += "<div>Codice fiscale: <b>" + document.getElementById("UFFINT_CFEIN").value + "</b></div>";
					
					formRegistrazione += "<div>Indirizzo: <b>" + document.getElementById("EXIST_VIAEIN").value + "</b></div>";
					formRegistrazione += "<div>Numero civico: <b>" + document.getElementById("EXIST_NCIEIN").value + "</b></div>";
					formRegistrazione += "<div>Comune: <b>" + document.getElementById("EXIST_CITEIN").value + "</b></div>";
					formRegistrazione += "<div>Provincia: <b>" + document.getElementById("EXIST_PROEIN").value + "</b></div>";
					formRegistrazione += "<div>C.A.P.: <b>" + document.getElementById("EXIST_CAPEIN").value + "</b></div>";
					formRegistrazione += "<div>Telefono: <b>" + document.getElementById("EXIST_TELEIN").value + "</b></div>";
					formRegistrazione += "<div>FAX: <b>" + document.getElementById("EXIST_FAXEIN").value + "</b></div>";
					formRegistrazione += "<div>PEC dell'Ente: <b>" + document.getElementById("EXIST_EMAIIN").value + "</b></div>";
				}
				formRegistrazione += "<div>-</div>";
				formRegistrazione += "<div><b>DICHIARA</b></div>";
				formRegistrazione += "<div>di essere dipendente dell'Amministrazione indicata la quale è una delle Amministrazioni di cui all'art. 3 del D.Lgs. 50/2016. Certifica che tutte le informazioni contenute nel modulo di adesione ai sensi degli articoli 46 e 47 del DPR 445/2000 sono vere, consapevole delle sanzioni penali previste dall'art. 76 del DPR 445/2000 per le ipotesi di falsità in atti e dichiarazioni mendaci.</div>";
				formRegistrazione += "<div>-</div>";
				formRegistrazione += "<div><b>CHIEDE</b></div>";
				formRegistrazione += "<div>l'abilitazione ai seguenti servizi applicativi:</div>";
				formRegistrazione += "<div>-</div>";
				var listaApplicativi="";
				$("input[type='checkbox']").each(function() {
                	if(this.checked) 
                	{
                		if (this.value == "SA-ESITI-SCP") {
                			formRegistrazione += "<div><b>Avvisi, Procedure di affidamento, Esiti</b></div>";
                			formRegistrazione += "<div>Servizio web finalizzato alla pubblicità sul sito www.serviziocontrattipubblici.it degli avvisi, bandi e procedure di affidamento, esiti, atti e documenti di appalti per lavori, forniture e servizi</div>";
                			formRegistrazione += "<div>-</div>";
                		}
                		if (this.value == "SA-PROGRAMMI-SCP") {
                			formRegistrazione += "<div><b>Comunicazioni di programmi</b></div>";
                			formRegistrazione += "<div>Comunicazioni di eventi di programmi triennali/annuali di lavori, forniture e servizi</div>";
                			formRegistrazione += "<div>-</div>";
                		}
                	}
            	});
            	
				formRegistrazione += "<div>per il proprio Referente: <b>" + document.getElementById("NOME").value + " " + document.getElementById("COGNOME").value + "</b></div>";
				formRegistrazione += "<div>Codice fiscale / Login: <b>" + document.getElementById("CODFISC").value + "</b></div>";
				formRegistrazione += "<div>E-mail: <b>" + document.getElementById("EMAIL").value + "</b></div>";
				formRegistrazione += "<div>Telefono: <b>" + document.getElementById("TELEFONO").value + "</b></div>";
				formRegistrazione += "<div>-</div>";
				if ($("#UFFINT_CODEIN").val() == "") {
					//nuova stazione appaltante
					formRegistrazione += "<div>Come nuovo referente</div>";
				}
				else {
					//stazione appaltante esistente
					if (document.getElementById("SUBENTRO").checked) {
						formRegistrazione += "<div>che subentrerà al precedente Referente <b>" + document.getElementById("referente").value + "</b> codice fiscale <b>" + document.getElementById("cfreferente").value + "</b></div>";
					} else if (document.getElementById("ALTRO").checked) {
						formRegistrazione += "<div>precisando che <b>" + document.getElementById("specificaaltro").value + "</b></div>";
					} else {
						formRegistrazione += "<div>richiedendo un accesso in aggiunta a quelli già disponibili per altri Referenti dell'Amministrazione</div>";
					}
				}
				
				formRegistrazione += "<div></div>";
				var d = new Date();
				if ($("#UFFINT_CODEIN").val() == "") {
					formRegistrazione += "<div>" + document.getElementById("UFFINT_CITEIN").value + ", li ";
				}
				else {
					formRegistrazione += "<div>" + document.getElementById("EXIST_CITEIN").value + ", li ";
				}
				
				formRegistrazione += d.toLocaleDateString() + "</div>";
				formRegistrazione += "<div><b>In fede</b></div>";
				
				formRegistrazione += "<div>" + document.getElementById("NOMEDIRIGENTE").value + "</div>";
				formRegistrazione += "<div>Dirigente del servizio: " + document.getElementById("SERVIZIO").value + "</div>";
				if ($("#UFFINT_CODEIN").val() == "") {
					formRegistrazione += "<div>Dell'amministrazione: " + document.getElementById("UFFINT_NOMEIN").value + "</div>";
				}
				else {
					formRegistrazione += "<div>Dell'amministrazione: " + $("#VIS_UFFINT_NOMEIN").html() + "</div>";
				}
				formRegistrazione += "</body></html>";
				
				var specialElementHandlers = {
    			'#editor': function (element, renderer) {
        			return true;
    				}
				};

				doc.fromHTML(formRegistrazione, 15, 15, {
        			'width': 170,
            		'elementHandlers': specialElementHandlers });
    			doc.save('ModelloRegistrazione.pdf');
			}
		</gene:javaScript>
		
</gene:template>