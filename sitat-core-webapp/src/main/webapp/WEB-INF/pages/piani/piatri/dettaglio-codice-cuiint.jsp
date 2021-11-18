<%
/*
     * Created on: 18/12/2017
     *
     * Copyright (c) EldaSoft S.p.A.
     * Tutti i diritti sono riservati.
     *
     * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
     * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
     * aver prima formalizzato un accordo specifico con EldaSoft.
*/
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>

<c:set var="modo" value="MODIFICA" />

<c:set var="masktitle" value="Modifica numero intervento CUI" />
<c:set var="campiChiave" value="${fn:split(key,';')}" />
<c:set var="contri" value="${fn:substringAfter(campiChiave[0],':')}" />
<c:set var="conint" value="${fn:substringAfter(campiChiave[1],':')}" />

<c:set var="cfamm" value='${gene:callFunction2("it.eldasoft.gene.tags.functions.GetCodfiscUffintFunction", pageContext, uffint)}' />
<c:set var="anno"  value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetAnnoPTFunction", pageContext, contri)}' />
		
<c:choose>
	<c:when test='${empty param.cui}'>
		<c:set var="progr" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetProgressivoCalcolatoPTFunction", pageContext, contri)}' />
		<c:set var="cui" value="F${cfamm}${anno}${progr}" />
		<c:set var="tiprog" value="${param.tiprog}" />
		<c:if test='${tiprog eq 1}'>
			<c:set var="cui" value="L${cfamm}${anno}${progr}" />
		</c:if>		
	</c:when>
	<c:otherwise>
		<c:set var="cui" value="${param.cui}" />
		<c:choose>
			<c:when test='${fn:length(cui) == 20}'>
				<c:set var="anno" value="${fn:substring(cui,11,15)}" />
				<c:set var="progr" value="${fn:substring(cui,15,20)}" />
			</c:when>
			<c:when test='${fn:length(cui) == 21}'>
				<c:set var="anno" value="${fn:substring(cui,12,16)}" />
				<c:set var="progr" value="${fn:substring(cui,16,21)}" />
			</c:when>
			<c:otherwise>
				<c:set var="progr" value="00001" />
			</c:otherwise>
		</c:choose>		
	</c:otherwise>
</c:choose>
	
<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera" value="${masktitle}"/>
	<gene:redefineInsert name="corpo">
		<gene:formScheda gestisciProtezioni="false" entita="">
			<gene:campoScheda campo="CUIINT" nome="CUIINT" title="Codice Univoco Intervento" modificabile="false" definizione="T20" value="${cui}"/>
			<gene:campoScheda campo="CUIINT_ORIG" nome="CUIINT_ORIG" title="Codice Univoco Intervento originario" visibile="false" modificabile="false" definizione="T20" value="${cui}"/>
			<gene:campoScheda><td colspan="2">&nbsp;</td></gene:campoScheda>
			<c:choose>
				<c:when test='${tiprog eq 1}'>
					<gene:campoScheda campo="TIPOIN" title="Settore" modificabile="false" value="L" campoFittizio="true" definizione="T1" visibile="false" />
				</c:when>
				<c:when test='${tiprog eq 2}'>
					<gene:campoScheda campo="TIPOIN" title="Settore" modificabile="true" value="F"
						gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreTipoinAnnuale" campoFittizio="true" definizione="T1" obbligatorio="true">
						<gene:checkCampoScheda funzione='controlloSettore("##")' onsubmit="false" obbligatorio="true" messaggio="Indicare il Settore" />
					</gene:campoScheda>
				</c:when>
			</c:choose>
			<gene:campoScheda campo="CFAMM" nome="CFAMM" title="Codice fiscale amministrazione" campoFittizio="true" definizione="T11" modificabile="false" value="${cfamm}" />
			<gene:campoScheda campo="ANNO" nome="ANNO" title="Anno del primo programma in cui l'intervento e' stato inserito" campoFittizio="true" definizione="T4" modificabile="true" value="${anno}" >
				<gene:checkCampoScheda funzione='controlloAnno("##")' onsubmit="false" obbligatorio="true" messaggio="L'anno non può essere successivo a quello del programma" />
			</gene:campoScheda>
			<gene:campoScheda campo="PROGR" nome="PROGR" title="Progressivo" campoFittizio="true" definizione="T5" value="${progr}" />
			<gene:campoScheda visibile='${param.modo eq "MODIFICA"}'>
				<td class="comandi-dettaglio" colSpan="2">	
					<INPUT type="button" class="bottone-azione" value="Conferma" title="Salva modifiche"  onclick="javascript:conferma()"/>
					<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla()" />
					&nbsp;
				</td>
			</gene:campoScheda>
			<gene:fnJavaScriptScheda funzione='modifyANNO()' elencocampi="ANNO" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyPROGR()' elencocampi="PROGR" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyTIPOIN()' elencocampi="TIPOIN" esegui="false" />
		</gene:formScheda>
  </gene:redefineInsert>
	<gene:javaScript>

		function modifyANNO() {
			if (getValue("ANNO").length == 4) {
				calcolaCUIINT();
			} else {
				alert("Indicare l'anno con 4 cifre");
			}
		}
		
		function modifyTIPOIN() {
			calcolaCUIINT();
		}
		
		function modifyPROGR() {
			if ($.isNumeric(getValue("PROGR"))) {
				if (getValue("PROGR").length < 5) {
					setValue("PROGR",fillCharLeft(getValue("PROGR"),5,'0'));
				}
				calcolaCUIINT();
			} else {
				alert("Il campo progressivo deve essere un valore numerico");
				setValue("PROGR",${progr});
			}	
		}
		
		function calcolaCUIINT() {
			var valore = "";
			if (getValue("ANNO") != "") {
				valore += getValue("TIPOIN") + "${cfamm}" + getValue("ANNO");
				valore += fillCharLeft(getValue("PROGR"),5,'0');
			}
			setValue("CUIINT", valore);
		}
		
		function annulla() {
			window.close();
		}

		function controlloAnno(anno) {
			if (anno > ${anno})
				return false;
			else
				return true;
		}
		
		function controlloSettore(settore) {
			if (settore == '')
				return false;
			else
				return true;
		}

		function conferma(){
			clearMsg();
			var cui = $("#CUIINT").val();
			var cuiOrig = $("#CUIINT_ORIG").val();
			if (cui != cuiOrig) {
				$.ajax({
					async: false,
					type: "GET",
					dataType: "json",
					beforeSend: function(x) {
						if (x && x.overrideMimeType) {
							x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
					url: "${pageContext.request.contextPath}/piani/IsCodiceInterventoUnico.do",
					data: "contri=${contri}&conint=${conint}&cui=" + cui,
					success: function( data ) {
						if (data[0] == null & data[1] == null) {
							opener.setValue("INRTRI_CUIINT", getValue("CUIINT"));
							window.close();
						} else {
							if (data[0] != null) {
								outMsg(data[0], "ERR");
								onOffMsgFlag(true);
							}
							if (data[1] != null) {
								outMsg(data[1], "ERR");
								onOffMsgFlag(true);
							}
						}
					},
					error: function(e) {
						alert("Errore durante la lettura dei codici CUI degli interventi");
					}
				});
			} else {
				opener.setValue("INRTRI_CUIINT", getValue("CUIINT"));
				window.close();
			}
		}

		$( document ).ready(function() {
    	outMsg("il CUI deve essere modificato solo nel caso in cui sia necessario riportare un codice preesistente e non presente nell'applicativo","WAR");
			onOffMsg();
		});


	</gene:javaScript>
</gene:template>
