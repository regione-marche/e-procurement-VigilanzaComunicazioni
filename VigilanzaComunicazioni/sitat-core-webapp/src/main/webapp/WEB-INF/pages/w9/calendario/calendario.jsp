<%--
/*
 * Created on: 21-nov-2014
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
 
 // Calendario scadenza pratiche

--%>
<% // questa jsp e' la copia della jsp di Geneweb %>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:template file="calendario-template.jsp" gestisciProtezioni="true" schema="GENE" idMaschera="G_SCADENZ-calendario">

	<c:if test="${fn:contains(header['user-agent'], 'MSIE')}">
		<gene:redefineInsert name="doctype">
			<!DOCTYPE html>
		</gene:redefineInsert>
	</c:if>
	
	<gene:redefineInsert name="addHistory">
		<gene:historyAdd titolo='Calendario pratiche' id='CAL-SCADENZ' replaceParam="" />
	</gene:redefineInsert>
	
	<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

	<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/fullcalendar-2.2.6.min.js"></script>
	<link rel="STYLESHEET" type="text/css" href="${pageContext.request.contextPath}/css/jquery/fullcalendar2.2.6/fullcalendar.css">
	
	<style type="text/css">
		.completed {background-color: green; font-size:1em;} 
		.tooltip {font-size:1em; max-width:300px; padding:2px;}
	</style>
	</gene:redefineInsert>

	<gene:setString name="titoloMaschera" value="Calendario pratiche" />

	<gene:redefineInsert name="corpo">
	<table class="dettaglio-notab">
		<tr>
			<td><div id='calendario'></div></td>
		</tr>
	</table>
	
	<gene:javaScript>
	$(document).ready(function() {
		$('#calendario').fullCalendar({
			theme : true,
			firstDay: 1,
			monthNames : ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
			dayNamesShort : ['Dom', 'Lun', 'Mar', 'Mer','Gio', 'Ven', 'Sab'],
			buttonText: {
				today: 'oggi'
			},
			eventLimit: 4, // for all non-agenda views
			eventLimitText: 'altri eventi',
			events: function(start, end, timezone, callback) {
				$.ajax({
					url: '${pageContext.request.contextPath}/w9/GetScadenzeFasiGare.do',
					type: 'POST',
					dataType: 'json',
					data: {
						start: start.toJSON(),
						end:   end.toJSON()
					},
					success: function(msg) {
						var events = [];
						$(msg).each(function() {
							events.push({
								title: 'Scadenza ' + $(this).attr('cig'),
								start: $(this).attr('scadenza'),
								url: '${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9lott/w9lott-scheda.jsp&key=W9LOTT.CODGARA=N:' + $(this).attr('codiceGara') + ';W9LOTT.CODLOTT=N:' + $(this).attr('codiceLotto'),
								className: 'completed',
								// altri attributi ad uso del tooltip
								codiceGara: $(this).attr('codiceGara'),
								codiceLotto: $(this).attr('codiceLotto'),
								cig: $(this).attr('cig'),
								fase_esecuzione: $(this).attr('fase_esecuzione'),
								num: $(this).attr('num'),
								descrizione: $(this).attr('descrizione')
							});
						});
						//alert("Prima del callback...");

						callback(events);
					},
					error: function() {
						alert('Errori durante la lettura delle fasi in scadenza');
					}
				});
			},
			eventClick: function(calEvent, jsEvent, view) {
		        if (event.url) {
					window.open(event.url);
				}
			},
			eventMouseover: function(calEvent, jsEvent) {
				if(calEvent.fase_esecuzione == 3 || calEvent.fase_esecuzione == 6 || calEvent.fase_esecuzione == 7 || calEvent.fase_esecuzione == 9) {
					var tooltip = '<div id="tooltipevent" class="fc-event tooltip" style="position:absolute;z-index:10001;">'
					+ '<b>CIG:</b> ' + calEvent.cig + '</br>' 
					+ '<b>Fase:</b> ' + calEvent.descrizione + '</br>'
					+ '<b>Numero:</b> ' + calEvent.num + '</br>'
					+ '<br><min>(Clicca per aprire il lotto)</min>'
					+ '</div>';
				} else {
					var tooltip = '<div id="tooltipevent" class="fc-event tooltip" style="position:absolute;z-index:10001;">'
					+ '<b>CIG:</b> ' + calEvent.cig + '</br>' 
					+ '<b>Fase:</b> ' + calEvent.descrizione + '</br>'
					+ '<br><min>(Clicca per aprire il lotto)</min>'
					+ '</div>';
				}
				$("body").append(tooltip);
				$(this).on("mouseover",function(e) {
					$(this).css('z-index', 10000);
					$('.tooltipevent').fadeIn('500');
					$('.tooltipevent').fadeTo('10', 1.9);
					}).mousemove(function(e) {
						$('#tooltipevent').css('top', e.pageY + 10);
						$('#tooltipevent').css('left', e.pageX + 20);
				});
			},
			eventMouseout: function(calEvent, jsEvent) {
				$('#tooltipevent').remove();
			}
		});
	});

	</gene:javaScript>
	</gene:redefineInsert>
</gene:template>
