<%
  /*
   * Created on 03/08/2009
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

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9INBOX-lista">

	<gene:redefineInsert name="head">
		<style>
    		label, input { display:block; }
    		input.text { margin-bottom:12px; width:95%; padding: .4em; }
    		fieldset { padding:0; border:0; margin-top:25px; }
    		h1 { font-size: 1.2em; margin: .6em 0; }
    		div#users-contain { width: 350px; margin: 20px 0; }
    		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    		.ui-dialog .ui-state-error { padding: .3em; }
    		.validateTips { border: 1px solid transparent; padding: 0.3em; }
  		</style>
	</gene:redefineInsert>
	<gene:setString name="titoloMaschera" value="Lista Comunicazioni" />
	<c:set var="eliminabili" value="false" />
	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-scheda")}' />
	<c:set var="key" value='${key}' scope="request" />

	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<gene:redefineInsert name="listaNuovo" />

		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="V_W9INBOX" pagesize="20" tableclass="datilista" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreV_W9INBOX" sortColumn="-3" gestisciProtezioni="true" >
						<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" 	width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
								<c:if	test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-scheda")}'>
									<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
								</c:if>
								<c:if test='${datiRiga.V_W9INBOX_STACOM eq "4" and datiRiga.V_W9INBOX_TINVIO2 ne -1}'>
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-lista")}'>
										<gene:PopUpItemResource resource="" title="Esegui importazione" href="javascript:eseguiImport('${datiRiga.V_W9INBOX_IDCOMUN}', '1');" />
									</c:if>
									<c:if	test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-lista")}'>
										<gene:PopUpItemResource resource=""	title="Annulla importazione" href="javascript:eseguiImport('${datiRiga.V_W9INBOX_IDCOMUN}', '2');" />
									</c:if>
								</c:if>
								<c:if test='${datiRiga.V_W9INBOX_STACOM eq "3" and datiRiga.V_W9INBOX_TINVIO2 ne -1}'>
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-lista")}'>
										<gene:PopUpItemResource resource="" title="Riprova importazione" href="javascript:eseguiImport('${datiRiga.V_W9INBOX_IDCOMUN}', '3');" />
									</c:if>
								</c:if>
								<c:if test='${(datiRiga.V_W9INBOX_STACOM eq "99" or datiRiga.V_W9INBOX_STACOM eq "3") and datiRiga.V_W9INBOX_TINVIO2 ne -1}'>
									<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
										<gene:PopUpItemResource	resource="popupmenu.tags.lista.elimina" title="Elimina" />
									</c:if>
								</c:if>
								<c:if test='${datiRiga.V_W9INBOX_STACOM eq "4" and datiRiga.V_W9INBOX_TINVIO2 eq -1}'>
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9INBOX-lista")}'>
										<gene:PopUpItemResource resource="" title="Accetta richiesta" href="javascript:eseguiImport('${datiRiga.V_W9INBOX_IDCOMUN}', '4');" />
										<gene:PopUpItemResource resource="" title="Rifiuta richiesta" href="javascript:eseguiImport('${datiRiga.V_W9INBOX_IDCOMUN}', '2');" />
									</c:if>
								</c:if>
							</gene:PopUp>
						</c:if>
						</gene:campoLista>
					<% 
					  // Campi veri e propri
					%>
					
						<c:set var="link"	value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
						<gene:campoLista title="Stato" width="50">
							<center>
								<c:choose>
									<c:when test='${datiRiga.V_W9INBOX_TINVIO2 eq -1}'>
										<c:choose>
											<c:when test='${datiRiga.V_W9INBOX_STACOM eq "2"}'>
												<img src="${pageContext.request.contextPath}/img/invioFlusso.png" height="24" width="24" title="Evasa">
											</c:when>
											<c:when test='${datiRiga.V_W9INBOX_STACOM eq "3"}'>
												<img src="${pageContext.request.contextPath}/img/invioRettificaFlusso.png" height="24" width="24" title="Rifiutata">
											</c:when>
											<c:when test='${datiRiga.V_W9INBOX_STACOM eq "4"}'>
												<img src="${pageContext.request.contextPath}/img/invioFlussoInAttesa.png" height="24" width="24" title="Da evadere">
											</c:when>
										</c:choose>
									</c:when>
									<c:when test='${datiRiga.V_W9INBOX_STACOM eq "1"}'>
										<img src="${pageContext.request.contextPath}/img/flag_bianco.gif"	height="16" width="16" title="Ricevuta">
									</c:when>
									<c:when test='${datiRiga.V_W9INBOX_STACOM eq "2"}'>
										<img src="${pageContext.request.contextPath}/img/flag_verde.gif" height="16" width="16" title="Importata">
									</c:when>
									<c:when test='${datiRiga.V_W9INBOX_STACOM eq "3"}'>
										<img src="${pageContext.request.contextPath}/img/flag_rosso.gif" height="16" width="16" title="Errore">
									</c:when>
									<c:when test='${datiRiga.V_W9INBOX_STACOM eq "4"}'>
										<img src="${pageContext.request.contextPath}/img/flag_giallo.gif" height="16" width="16" title="Warning">
									</c:when>
									<c:when test='${datiRiga.V_W9INBOX_STACOM eq "99"}'>
										<img src="${pageContext.request.contextPath}/img/no_flag.gif" height="16" width="16" title="test">
									</c:when>
								</c:choose>
							</center>
						</gene:campoLista>
						<gene:campoLista campo="IDCOMUN" visibile="false" />
						<gene:campoLista campo="STACOM" visibile="false" />
						<gene:campoLista campo="NOMEIN" title="Denominazione stazione appaltante" ordinabile="false" />
						<gene:campoLista campo="DATRIC" title="Data e ora ricezione" headerClass="sortable" />
						<gene:campoLista campo="KEY03"  title="Tipologia flusso" />
						<gene:campoLista campo="CODOGG" />
						<gene:campoLista campo="TINVIO2" title="Tipo di invio" />
					</gene:formLista>
				</td>
			</tr>
			<c:if test='${eliminabili eq false}'>
				<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
				<gene:redefineInsert name="listaEliminaSelezione" />
			</c:if>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
		
		<div id="dialog-form" title="Motivazione rifiuto">
  			<p class="validateTips">Inserisci qui sotto la motivazione della decisione.</p>
 
  				<form>
   				 <fieldset>
					<label for="motivazione">Motivazione</label>
      				<input type="text" name="motivazione" id="motivazione" value="" class="text ui-widget-content ui-corner-all">
      				<!-- Allow form submission with keyboard without duplicating the dialog button -->
      				<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    			</fieldset>
  			</form>
		</div>

	<gene:javaScript>
	function eseguiImport(key, azione) {
		var continua = true;
		if(azione == "3")
			if(! confirm("Riprovare ad importare il flusso selezionato?"))
				continua = false;
		if(azione == "4")
			if(! confirm("Accetta richiesta di cancellazione?"))
				continua = false;
		if(azione == "2")
		{
			keyRifiutata=key;
			dialog.dialog( "open" );
			continua = false;
		}
		if(continua)
			location.href = '${pageContext.request.contextPath}/w9/EseguiImportazioneOrt.do?' + csrfToken + '&idcomun=' + key + '&azione=' + azione;
	}
			
	var dialog, keyRifiutata;
	
	$(document).ready(function(){ 
    	var form,
 
      // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
      motivazione = $( "#motivazione" ),
      allFields = $( [] ).add( motivazione ),
      tips = $( ".validateTips" );
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Lunghezza " + n + " deve essere compresa da " + min + " a " + max + "." );
        return false;
      } else {
        return true;
      }
    }
 
    function RifiutaRichiesta() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
      valid = valid && checkLength( motivazione, "motivazione", 1, 200 );
      if ( valid ) {
	    location.href = "${pageContext.request.contextPath}/w9/EseguiImportazioneOrt.do?" + csrfToken + "&idcomun=" + keyRifiutata + "&azione=2&motivazione=" + motivazione.val();
		dialog.dialog( "close" );
      }
      return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "Conferma": RifiutaRichiesta,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      RifiutaRichiesta();
    });
 
  } );
  
  			
	</gene:javaScript>
	</gene:redefineInsert>
</gene:template>