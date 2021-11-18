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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<gene:template file="scheda-template.jsp">

<gene:setString name="titoloMaschera" value='Trasferimento dati per subentro' />

<c:set var="temp" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetInfoSubentroFunction",pageContext)}'/>
<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
</gene:redefineInsert>

<gene:redefineInsert name="corpo">
	<form method="post" name="formCambioUtente" action="${pageContext.request.contextPath}/w9/TrasferisciDatiSubentro.do" >
		<table class="dettaglio-tab" >
			<tr><td colSpan="3" class="even"><b>${nomeUffint} - CF ${cfUffint}</b></td></tr>
			<tr><td colSpan="3"><b><label id="msgErrore" style="color:red"></label></b></td></tr>
			<tr id="rowProgrammi">
				<td width="100">&emsp;Programmi</td>
				<td colSpan="2" align="left" ><input type="checkbox" id="programmi" name="programmi"/></td>
			</tr>
			<tr id="rowAvvisi">
				<td width="100">&emsp;Avvisi</td>
				<td colSpan="2" align="left" ><input type="checkbox" id="avvisi" name="avvisi"/></td>
			</tr>
			<tr id="rowBandi">
				<td width="100">&emsp;Bandi</td>
				<td colSpan="2" align="left" ><input type="checkbox" id="bandi" name="bandi"/></td>
			</tr>
			<tr>
				<td colSpan="2">
					<label><b>Trova nuovo referente per CF &emsp;</b></label>
				</td>
				<td>
					<div class="ui-widget">
  						<input id="nuovoReferente" name="nuovoReferente" size="80">
					</div>
				</td>
			</tr>
			
			<tr>
				<td colSpan="2">
					<label><b>Trova vecchio referente per CF </b></label>
				</td>
				<td>
  					<div class="ui-widget">
  						<input id="vecchioReferente" name="vecchioReferente" size="80">
					</div>
				</td>
			</tr>
		
			<tr class="comandi-dettaglio">
				<td class="comandi-dettaglio" colspan="3">
					<input type="button" value="Reset" title="Reset" class="bottone-azione" onclick="javascript:sbianca()">&nbsp;&nbsp;
					<input type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:historyVaiIndietroDi(1);"/>&nbsp;&nbsp;
					<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:conferma()">
				</td>
			</tr>
		</table>
	</form>
</gene:redefineInsert>

<gene:javaScript>
	<c:if test="${programmi eq false}">
		$("#rowProgrammi").hide();
	</c:if>
	<c:if test="${avvisi eq false}">
		$("#rowAvvisi").hide();
	</c:if>
	<c:if test="${bandi eq false}">
		$("#rowBandi").hide();
	</c:if>
	$( function() {
       	$( "#nuovoReferente" ).autocomplete({
      		source: ${utenti}
    	});
    	
    	$( "#nuovoReferente" ).autocomplete({
  			select: function( event, ui ) { 
  				$("#nuovoReferente").attr("readonly", true); 
  				$("#nuovoReferente").css({"background-color": '#FFB45A'	});
  			}
		});
  	});

  	$( function() {
       	$( "#vecchioReferente" ).autocomplete({
      		source: ${utenti}
    	});
    	$( "#vecchioReferente" ).autocomplete({
  			select: function( event, ui ) { 
  				$("#vecchioReferente").attr("readonly", true); 
  				$("#vecchioReferente").css({"background-color": '#FFB45A'});
  			}
		});
  	});
  
  
  	function sbianca(){
  		$("#vecchioReferente").attr("readonly", false); 
  		$("#vecchioReferente").css({"background-color": '#FFFFFF'});
  		$("#nuovoReferente").attr("readonly", false); 
  		$("#nuovoReferente").css({"background-color": '#FFFFFF'});
  		$('#msgErrore').text("");
		document.formCambioUtente.reset();
	}
	
	function conferma(){
		if (!document.getElementById("programmi").checked && 
		!document.getElementById("avvisi").checked && 
		!document.getElementById("bandi").checked) {
			$('#msgErrore').text("Selezionare almeno una tipologia da trasferire");
		} else {
			if ( !$("#nuovoReferente").is('[readonly]') ) { 
				$('#msgErrore').text("Selezionare il nuovo referente");
			} else if ( !$("#vecchioReferente").is('[readonly]') ) { 
				$('#msgErrore').text("Selezionare il vecchio referente");
			} else if ( $("#vecchioReferente").val() == $("#nuovoReferente").val() ) { 
				$('#msgErrore').text("Il nuovo referente non puo' coincidere con il vecchio");
			} else {
				document.formCambioUtente.submit();
				bloccaRichiesteServer();
			}
		}
	}
</gene:javaScript>

</gene:template>

