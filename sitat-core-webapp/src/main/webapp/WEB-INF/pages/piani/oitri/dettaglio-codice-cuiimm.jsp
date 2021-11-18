<%
/*
     * Created on: 03/06/2011
     *
     * Copyright (c) EldaSoft S.p.A.
     * Tutti i diritti sono riservati.
     *
     * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
     * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
     * aver prima formalizzato un accordo specifico con EldaSoft.

     Descrizione:
		Selezione del dettaglio delle categoria dell'intervento
		Parametri:
			param.modo 
				Modo di apertura MODIFICA per modifica o VISUALIZZA per visualizzazione
			param.campo 
				Nome del campo in cui vi è il dettaglio
			param.valore 
				Valore del campo

*/
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="conint" value="0" />
<c:set var="contri" value="${param.contri}" />

<c:set var="void"  value='${gene:callFunction3("it.eldasoft.sil.pt.tags.funzioni.GetBaseCuiImmobileFunction", pageContext, contri, conint)}' />
		
<c:choose>
	<c:when test='${empty param.cui}'>
		<c:set var="cui" value="I${cfamm}${anno}${progr}" />
	</c:when>
	<c:otherwise>
		<c:set var="cui" value="${param.cui}" />
		<c:set var="anno"  value="${fn:substring( cui, 12,16 )}" />
		<c:set var="progr"  value="${fn:substring( cui, 16,21 )}" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test='${modo eq "VISUALIZZA"}'>
		<c:set var="masktitle"  value="Dettaglio codice univoco immobile" />
	</c:when>
	<c:otherwise>
		<c:set var="masktitle"  value="Modifica codice univoco immobile" />
	</c:otherwise>
</c:choose>

<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera" value="${masktitle}"/>
	<gene:redefineInsert name="corpo">
		<gene:formScheda gestisciProtezioni="false" entita="">
			<gene:campoScheda campo="CUIIMM" nome="CUIIMM" title="Codice Univoco Immobile" definizione="T20" modificabile="false" value="${cui}"/>
			<gene:campoScheda campo="CUIIMM_ORIG" nome="CUIIMM_ORIG" title="Codice Univoco Immobile originario" visibile="false" modificabile="false" definizione="T20" value="${cui}"/>
			<gene:campoScheda><td colspan="2">&nbsp;</td></gene:campoScheda>
			<gene:campoScheda campo="CFAMM" nome="CFAMM" title="Codice fiscale amministrazione" campoFittizio="true" definizione="T11"  
				modificabile="false" value="${cfamm}" />
			<gene:campoScheda campo="ANNO" nome="ANNO" title="Anno del primo programma in cui l'intervento e' stato inserito" campoFittizio="true" definizione="T4"
			 modificabile="false" value="${anno}">
			  <gene:checkCampoScheda funzione='controlloAnno("##")' onsubmit="false" obbligatorio="true" messaggio="L'anno non può essere successivo a quello del programma" />
			</gene:campoScheda>
			<gene:campoScheda campo="PROGR" nome="PROGR" title="Progressivo" campoFittizio="true" definizione="T5" value="${progr}" />
			<gene:campoScheda visibile='${param.modo eq "MODIFICA"}'>
				<td class="comandi-dettaglio" colSpan="2">	
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Salva modifiche" onclick="javascript:conferma()" />
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla()" />
						&nbsp;
				</td>
			</gene:campoScheda>

			<gene:fnJavaScriptScheda funzione='modifyCFAMM()' elencocampi="CFAMM" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyANNO()' elencocampi="ANNO" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyPROGR()' elencocampi="PROGR" esegui="false" />
			
		</gene:formScheda>
		
  </gene:redefineInsert>

	<gene:javaScript>

		function modifyCFAMM() {
			setValue("ANNO","", false);
			setValue("PROGR","", false);
			calcolaCUIIMM();
		}

		function modifyANNO() {
			setValue("PROGR","", false);
			calcolaCUIIMM();
		}
		

		function modifyPROGR() {
			if($.isNumeric(getValue("PROGR"))){
				if (getValue("PROGR").length < 5) {
					setValue("PROGR",fillCharLeft(getValue("PROGR"),5,'0'));
				}
				calcolaCUIIMM();
			} else{
				alert("Il campo progressivo deve essere un valore numerico");
				setValue("PROGR", getOriginalValue("PROGR"));
			}	
		}
		
		
		function calcolaCUIIMM(){
			var valore="";
			if (getValue("CFAMM") != "") {
				valore = "I"; 
				valore += getValue("CFAMM");
				valore += getValue("ANNO");
				valore += getValue("PROGR");
			}
			setValue("CUIIMM",valore);
		}
		
		function controlloAnno(anno) {
			if (anno > ${anno})
				return false;
			else
				return true;
		}
		
		function conferma(){
			clearMsg();
			var cui = getValue("CUIIMM");
			var cuiOrig = getValue("CUIIMM_ORIG");
			
			if (cui != cuiOrig) {
				var tabella_sezione_multipla = "IMMTRAI_CUIIMM_";
				var contatore = 1;
				var sentinella = true;
				var presenza_duplicato = false;
				var tabella_pagina_origine;
			
				while(sentinella) {
					tabella_pagina_origine = tabella_sezione_multipla + contatore;
					riga_pagina_origine = "row" + tabella_sezione_multipla + contatore;
					if (!window.opener.document.getElementById(tabella_pagina_origine)) {
	    				sentinella = false;
					} else {
						if (tabella_pagina_origine != "${param.campo1}"){
							if (window.opener.document.getElementById(riga_pagina_origine).offsetWidth > 0 && window.opener.document.getElementById(riga_pagina_origine).offsetHeight > 0) {
								if (window.opener.document.getElementById(tabella_pagina_origine).value == document.getElementById("CUIIMM").value) {
									sentinella = false;
									presenza_duplicato = true;
								}
							}
						}
					}
					contatore ++;
				}
				
				if (presenza_duplicato) {
					alert("Errore durante la lettura dei codici CUI degli immobili, il codice selezionato e' gia' presente");
				} else {
					opener.setValue("${param.campo1}", getValue("CUIIMM"));
					window.close();
				}
				
			} else {
				opener.setValue("${param.campo1}",getValue("CUIIMM"));
				window.close();
			}
		
		}
		
		function annulla(){
			window.close();
		}

		$(document).ready(function() {
    	outMsg("il CUI deve essere modificato solo nel caso in cui sia necessario riportare un codice preesistente e non presente nell'applicativo","WAR");
			onOffMsg();
		});
	</gene:javaScript>
</gene:template>

