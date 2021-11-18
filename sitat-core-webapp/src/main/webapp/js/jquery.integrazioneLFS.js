/** 
 * Gestione ricerca lavori, associazione Codice Lavoro e lettura scheda lavoro
 * 
 */

var _tableListaLavori = null;

var myIntegrazioneLFS = myIntegrazioneLFS || (function(){
	
	var _ctx;
	var _endpoint;
	var _contri;
	var _conint;

	var _args = {};
	return {
		init: function(ctx,endpoint,contri,conint) {
			_ctx = ctx;
			_endpoint = endpoint;
			
			var _lastchar = _endpoint.substr(_endpoint.length - 1);
			if (_lastchar == '/') {
				_endpoint = _endpoint.substr(0, _endpoint.length - 1);
			}
			
			_contri = contri;
			_conint = conint;
			
		},
		creaFinestraListaLavori: function() {
			_creaFinestraListaLavori();
		},
		creaFinestraSchedaLavoro: function() {
			_creaFinestraSchedaLavoro();
		},
		creaLinkListaLavori: function(anchor) {
			var _codint = $("#INTTRI_CODINT").val();
			var _associato = $("#INTTRI_CEFINT").val();
			if (_codint == null || _codint.trim() == "" || _associato == null || _associato.trim() != "1") {
				_creaLinkListaLavori(anchor);
			}
		},
		creaLinkSchedaLavoro: function(anchor) {
			var _codint = $("#INTTRI_CODINT").val();
			var _associato = $("#INTTRI_CEFINT").val();
			if (_codint != null && _codint.trim() != "" && _associato != null && _associato == "1") {
				_creaLinkSchedaLavoro(anchor);
			}
		},
		getCtx: function() {
			return _ctx;
		},
		getEndpoint: function() {
			return _endpoint;
		},
		getContri: function() {
			return _contri;
		},
		getConint: function() {
			return _conint;
		}
	};
}());


var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
})();


/**
 * Crea la finestra modale con la lista dei lavori 
 */
function _creaFinestraListaLavori() {
	var _finestraListaLavori = $("<div/>",{"id": "finestraListaLavori", "title":"Lista dei lavori/contratti"});
	_finestraListaLavori.dialog({
		open: function(event, ui) { 
			//$(this).parent().css("background","#FFFFFF");
			$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
			$(this).parent().css("border-color","#C0C0C0");
			var _divtitlebar = $(this).parent().find("div.ui-dialog-titlebar");
	    	_divtitlebar.css("border","0px");
	    	_divtitlebar.css("background","#FFFFFF");
	    	
	    	var _dialog_title = $(this).parent().find("span.ui-dialog-title");
	    	_dialog_title.css("font-size","13px");
	    	_dialog_title.css("font-weight","bold");
	    	_dialog_title.css("color","#002856");
	    	
	    	$(this).parent().find("div.ui-dialog-buttonpane").css("background","#FFFFFF");
		},
   		autoOpen: false,
       	show: {
       		effect: "blind",
       		duration: 350
           },
        hide: {
           	effect: "blind",
           	duration: 350
        },
        resizable: true,
   		height: 550,
   		width: 1000,
   		minHeight: 350,
   		minWidth: 650,
   		modal: true,
   		focusCleanup: true,
   		cache: false,
        buttons: {
        "Chiudi" : function() {
           		$(this).dialog( "close" );
        	}
        }
	});
		
	_finestraListaLavori.on( "dialogclose", function( event, ui ) {
		if (_tableListaLavori != null) {
			_tableListaLavori.destroy(true);
		}
		$("#listaLavoriContainer").remove();
	});
	
} 

/**
 * Crea la finestra modale per la consultazione del lavoro
 */
function _creaFinestraSchedaLavoro() {
	var _finestraSchedaLavoro = $("<div/>",{"id": "finestraSchedaLavoro", "title":"Scheda del lavoro/contratto"});
	_finestraSchedaLavoro.dialog({
		open: function(event, ui) { 
			//$(this).parent().css("background","#FFFFFF");
			$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
			$(this).parent().css("border-color","#C0C0C0");
			var _divtitlebar = $(this).parent().find("div.ui-dialog-titlebar");
	    	_divtitlebar.css("border","0px");
	    	_divtitlebar.css("background","#FFFFFF");
	    	
	    	var _dialog_title = $(this).parent().find("span.ui-dialog-title");
	    	_dialog_title.css("font-size","13px");
	    	_dialog_title.css("font-weight","bold");
	    	_dialog_title.css("color","#002856");
	    	
	    	$(this).parent().find("div.ui-dialog-buttonpane").css("background","#FFFFFF");
		},
   		autoOpen: false,
       	show: {
       		effect: "blind",
       		duration: 350
           },
        hide: {
           	effect: "blind",
           	duration: 350
        },
        resizable: true,
   		height: 400,
   		width: 650,
   		minHeight: 350,
   		minWidth: 650,
   		modal: true,
   		focusCleanup: true,
   		cache: false,
        buttons: {
        "Chiudi" : function() {
           		$(this).dialog( "close" );
        	}
        }
	});
		
	_finestraSchedaLavoro.on( "dialogclose", function( event, ui ) {
		$("#schedaLavoroContainer").remove();
	});
	
} 



/**
 * Utilita' per la creazione del link di accesso alla lista dei lavori
 * @param anchor
 */
function _creaLinkListaLavori(anchor) {
 	setTimeout(function(){
		var _linkListaLavori = $("<a/>",{"id": "linkListaLavori", "class": "link-generico", "text": "Ricerca lavori/contratti e collega Codice Lavoro", "title": "Ricerca lavori/contratti e collega Codice Lavoro"});
		
		_linkListaLavori.css("margin-left","10px");
		_linkListaLavori.css("display","inline-block");

		anchor.append(_linkListaLavori);
		
		_linkListaLavori.on("click",function() {
			$("#finestraListaLavori").dialog("open");
			_creaContainerListaLavori();
		 });
 	}, 300);
 } 

/**
 * Utilita' per la creazione del link di accesso alla scheda del lavoro
 * @param anchor
 */

function _creaLinkSchedaLavoro(anchor) {
 	setTimeout(function(){
		var _linkSchedaLavoro = $("<a/>",{"id": "linkSchedaLavoro", "class": "link-generico", "text": "Consulta lavoro/contratto", "title": "Consulta lavoro/contratto"});
		
		_linkSchedaLavoro.css("margin-left","10px");
		_linkSchedaLavoro.css("display","inline-block");

		anchor.append(_linkSchedaLavoro);
		
		_linkSchedaLavoro.on("click",function() {
			$("#finestraSchedaLavoro").dialog("open");
			_creaPopolaSchedaLavoro();
		 });
 	}, 300);
 } 

/**
 * Crea il contenitore del datatable con la lista dei lavori
 */
function _creaContainerListaLavori() {
	var _container = $("<table/>", {"id": "listaLavoriContainer", "class": "dettaglio-notab"});
	_container.css("font","11px Verdana, Arial, Helvetica, sans-serif");
	var _tr = $("<tr/>");
	var _td = $("<td/>");
	_td.css("border-bottom","0px");
	
	var _divSearchLavoriContainer = $("<div/>", {"margin-top": "25px", "margin-bottom": "25px"});
	var _divListaLavoriContainer = $("<div/>", {"id": "divListaLavoriContainer", "margin-top": "25px", "margin-bottom": "25px", "width" : "98%"});

	var _span_search_codlav = $("<span/>");
	var _search_codlav = $("<input/>",{"id":"search_codlav","size": "20"});
	_span_search_codlav.append("Cerca lavori per codice lavoro&nbsp;");
	_span_search_codlav.append(_search_codlav);
	_divSearchLavoriContainer.append(_span_search_codlav);

	var _span_search_titolo = $("<span/>");
	var _search_titolo = $("<input/>",{"id":"search_titolo","size": "30"});
	_span_search_titolo.append("&nbsp; titolo &nbsp;");
	_span_search_titolo.append(_search_titolo);
	_divSearchLavoriContainer.append(_span_search_titolo);
	
	var _span_search_rup = $("<span/>");
	var _search_rup = $("<input/>",{"id":"search_rup","size": "30"});
	_span_search_rup.append("&nbsp; e RUP &nbsp;");
	_span_search_rup.append(_search_rup);
	_divSearchLavoriContainer.append(_span_search_rup).append("<br>");
	
	_td.append(_divSearchLavoriContainer);
	_td.append(_divListaLavoriContainer);
	_td.append("<br/><br/>");
	_tr.append(_td);
	_container.append(_tr);
	$("#finestraListaLavori").append(_container);
	
	_creaListaLavori();
	
	$("body").on("keyup", "#search_codlav", function() {
    	delay(function(){
    		var search_codlav = $("#search_codlav").val();
    		var search_titolo = $("#search_titolo").val();
    		var search_rup = $("#search_rup").val();
    		search_codlav = search_codlav.trim();
    		search_titolo = search_titolo.trim();
    		search_rup = search_rup.trim();
    		if (search_codlav.length > 0 || search_titolo.length > 0 || search_rup.length > 0) {
    			_getListaLavori();	
    		} else {
    			if (_tableListaLavori != null) {
    				//_tableListaLavori.destroy(true);
    			}
    		}
    	}, 600);
	});
	
	$("body").on("keyup", "#search_titolo", function() {
    	delay(function(){
    		var search_codlav = $("#search_codlav").val();
    		var search_titolo = $("#search_titolo").val();
    		var search_rup = $("#search_rup").val();
    		search_codlav = search_codlav.trim();
    		search_titolo = search_titolo.trim();
    		search_rup = search_rup.trim();
    		if (search_codlav.length > 0 || search_titolo.length > 0 || search_rup.length > 0) {
    			_getListaLavori();	
    		} else {
    			if (_tableListaLavori != null) {
    				//_tableListaLavori.destroy(true);
    			}
    		}
    	}, 600);
	});
	
	$("body").on("keyup", "#search_rup", function() {
    	delay(function(){
    		var search_codlav = $("#search_codlav").val();
    		var search_titolo = $("#search_titolo").val();
    		var search_rup = $("#search_rup").val();
    		search_codlav = search_codlav.trim();
    		search_titolo = search_titolo.trim();
    		search_rup = search_rup.trim();
    		if (search_codlav.length > 0 || search_titolo.length > 0 || search_rup.length > 0) {
    			_getListaLavori();	
    		} else {
    			if (_tableListaLavori != null) {
    				//_tableListaLavori.destroy(true);
    			}
    		}
    	}, 600);
	});

}


function _creaPopolaSchedaLavoro() {
	
	_wait();
	
	var _table = $("<table/>", {"id": "schedaLavoroContainer", "class": "dettaglio-notab"});
	_table.css("font","11px Verdana, Arial, Helvetica, sans-serif");
	_table.css("border-top","1px solid #A0AABA ");
	_table.css("margin-top","10px");
	
	var descrizioneIntervento = ["Codice lavoro",
	     "Titolo del progetto",
	     "Codice CUI",
	     "Importo totale",
	     "Data ultima approvazione",
	     "Codice CUP di progetto",
	     "CIG",
	     "RUP"
	     ];
	
	var campiIntervento = ["codicelavoro",
	       "titolo",
	       "cui",
	       "importototalelavoro",
	       "dataultimaapprovazione",
	       "cup",
	       "cig",
	       "rup"
	       ];
	
	var iInterventi;
	for (iInterventi = 0; iInterventi < 8; iInterventi++) {
		var _tr = $("<tr/>");
		var _td1 = $("<td/>", {"text" : descrizioneIntervento[iInterventi]});
		_td1.css("background-color","#EFEFEF");
		_td1.css("border-top","#A0AABA 1px solid");
		_td1.css("width","400px");
		_td1.css("padding-right","10px");
		_td1.css("text-align","right");
		_td1.css("height","25px");
		var _td2 = $("<td/>",{"id": campiIntervento[iInterventi],"class":"valore-dato"});
		_tr.append(_td1).append(_td2);
		_table.append(_tr);
	}
	
	_table.css("font","11px Verdana, Arial, Helvetica, sans-serif");
	_table.css("border-top","1px solid #A0AABA ");
	_table.css("margin-top","10px");

	
	var _codlav = $("#INTTRI_CODINT").val();
	
	$.ajax({
		"type": "POST",
		"dataType": "json",
		"async": true,
		"beforeSend": function(x) {
			if(x && x.overrideMimeType) {
				x.overrideMimeType("application/json;charset=utf-8");
			}
		},
		"url": myIntegrazioneLFS.getCtx() + "/piani/GetILFSListaLavori.do",
		"data": {
			"endpoint" : myIntegrazioneLFS.getEndpoint(),
			"search_codlav" : _codlav
		},
		"success": function(jsonResult){
			_nowait();
			if (jsonResult.esito == true) {
				$.each(jsonResult.lavoro, function(k, v) {
					k = k.replace("'", "\\'");
					if(k == "rup") {
						if (v.nome != null && v.cognome != null) {
							$("[id='rup']").text(v.nome + " " + v.cognome);
						}
					} else {
						$("[id='"+ k + "']").text(v);
					}
					
				});
			} else {
				var _tr = $("<tr/>");
				var _td1 = $("<td/>",{"colspan":"2"});
				_td1.append("<br>").append(jsonResult.messaggio).append("<br><br>");
				_tr.append(_td1)
				_table.append(_tr);
			}

		},
		"error": function ( e ) {
			_nowait();
			alert("Si e' verificato un errore durante l'interazione con i servizi di integrazione LFS");
		},
		"complete" : function() {

		}
	});
	
	$("#finestraSchedaLavoro").append(_table);

}

/**
 * Crea la tabella di base per la lista dei lavori
 */
function _creaListaLavori() {
	
	$("#listaLavori").remove();
	
	var _table = $("<table/>", {"id": "listaLavori", "class": "integrazioneLFS", "cellspacing": "0", "width" : "100%"});
	var _thead = $("<thead/>");
	var _tr0 = $("<tr/>",{"class":"intestazione"});
	_tr0.append($("<th/>",{"text":"Collega","class":"associa","rowspan":"2"}));
	_tr0.append($("<th/>",{"text":"Lavoro/contratto","colspan":"4"}));
	_tr0.append($("<th/>",{"text":"RUP","colspan":"3"}));
 	_thead.append(_tr0);
	var _tr1 = $("<tr/>",{"class":"intestazione"});
	_tr1.append($("<th/>",{"text":"Codice lavoro"}));
	_tr1.append($("<th/>",{"text":"Codice CUI"}));
	_tr1.append($("<th/>",{"text":"Descrizione"}));
	_tr1.append($("<th/>",{"text":"Importo"}));
	_tr1.append($("<th/>",{"text":"Codice fiscale"}));
	_tr1.append($("<th/>",{"text":"Nome"}));
	_tr1.append($("<th/>",{"text":"Cognome"}));
 	_thead.append(_tr1);
 	_table.append(_thead);
 	var _tfoot = $('<tfoot/>');
 	var _tr2 = $('<tr/>',{"class":"intestazione"});
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tr2.append($('<th/>'));
	_tfoot.append(_tr2);
	_table.append(_tfoot);
 	$("#listaLavoriContainer").append(_table);
}



/**
 * Collega il Lavoro al codice CUI 
 * @param iRow
 * @param numeroubicazione
 */
function _collegaLavoro(iRow) {
	_wait();
	var _codlav = _tableListaLavori.row(iRow).data().codicelavoro;
	$.ajax({
		"type": "POST",
		"dataType": "json",
		"async": true,
		"beforeSend": function(x) {
			if(x && x.overrideMimeType) {
				x.overrideMimeType("application/json;charset=UTF-8");
			}
		},
		"url": myIntegrazioneLFS.getCtx() + "/piani/SetILFSCodlav.do",
		"data": {
			"endpoint" : myIntegrazioneLFS.getEndpoint(),
			"contri" : myIntegrazioneLFS.getContri(),
			"conint" : myIntegrazioneLFS.getConint(),
			"codlav" : _codlav
		},
		"success": function(jsonResult){
			_nowait();
     		setTimeout(function(){historyVaiIndietroDi(0);}, 1000);
			$("#finestraListaLavori").dialog("close");
		},
		"error": function ( e ) {
			_nowait();
			alert("Si e' verificato un errore durante l'interazione con i servizi di integrazione LFS");
		},
		"complete" : function() {

		}
	});

}

/**
 * Lettura della lista dei lavori
 * 
 */
function _getListaLavori() {

	if (_tableListaLavori != null) {
		_tableListaLavori.destroy(true);
	}
	
	_creaListaLavori();


	var search_codlav = $("#search_codlav").val();
	search_codlav = search_codlav.trim();
	search_codlav = search_codlav.replace(/%/g, '%25');
	search_codlav = search_codlav.replace(/ /g, '%20');
	search_codlav = search_codlav.replace(/!/g, '%21');
	search_codlav = search_codlav.replace(/"/g, '%22');
	search_codlav = search_codlav.replace(/#/g, '%23');
	search_codlav = search_codlav.replace(/&/g, '%26');
	
	var search_titolo = $("#search_titolo").val();
	search_titolo = search_titolo.trim();
	search_titolo = search_titolo.replace(/%/g, '%25');
	search_titolo = search_titolo.replace(/ /g, '%20');
	search_titolo = search_titolo.replace(/!/g, '%21');
	search_titolo = search_titolo.replace(/"/g, '%22');
	search_titolo = search_titolo.replace(/#/g, '%23');
	search_titolo = search_titolo.replace(/&/g, '%26');
	
	var search_rup = $("#search_rup").val();
	search_rup = search_rup.trim();
	search_rup = search_rup.replace(/%/g, '%25');
	search_rup = search_rup.replace(/ /g, '%20');
	search_rup = search_rup.replace(/!/g, '%21');
	search_rup = search_rup.replace(/"/g, '%22');
	search_rup = search_rup.replace(/#/g, '%23');
	search_rup = search_rup.replace(/&/g, '%26');
	
	_tableListaLavori = $("#listaLavori").DataTable( {
		"ajax": {
			"url": myIntegrazioneLFS.getCtx() + "/piani/GetILFSListaLavori.do",
			"data": {
				"endpoint" : myIntegrazioneLFS.getEndpoint(),
				"search_codlav" : search_codlav,
				"search_titolo" : search_titolo,
				"search_rup" : search_rup
			},
			"dataSrc": function ( json ) {
				if (json.lavoro != null) {
					if (json.lavoro.length > 1){
						return json.lavoro;
					} else {
						return jQuery.makeArray(json.lavoro);
					}
				} else {
					return "";
				}
			},
			"error": function ( e ) {
				alert("Si e' verificato un errore durante l'interazione con i servizi di LFS");
			}
		},
		"columns": [
			{
				"targets": 0,
				"data": null,
				"bSortable": false,
				"sClass": "associa",
		        "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
		        	var __oCodiceFiscaleRUP = null;
		        	if (oData.rup != null) {
		        		__oCodiceFiscaleRUP = oData.rup.codicefiscale;
		        	}
		        	var __oCui = oData.cui;
		        	var __oCodiceLavoro = oData.codicelavoro;
		        	
		        	if (__oCodiceLavoro != null && __oCodiceLavoro.trim() != "" && __oCodiceFiscaleRUP != null && __oCodiceFiscaleRUP.trim() != "" && (__oCui == null || __oCui.trim() == "")) {
			        	var _html = "<a href='javascript:_collegaLavoro(" + iRow + ")'>";
			        	_html += "<img title='Collega Lavoro' height='20' width='20' alt='Collega Lavoro' src='" + myIntegrazioneLFS.getCtx() +  "/img/ico_link.gif'>";
			        	_html += "</a>";
			            $(nTd).html(_html);
		        	} else {
		        		$(nTd).html("&nbsp;");
		        	}
		        }
			},
			{ "data": "codicelavoro" , "defaultContent": "", "className": "center"},
			{ "data": "cui" , "defaultContent": "", "className": "center"},
			{ "data": "titolo", "defaultContent": "", "className": "center" },
			{ "data": "importototalelavoro" , "defaultContent": "", "className": "center"},
			{ "data": "rup.codicefiscale", "defaultContent": "" },
			{ "data": "rup.nome", "defaultContent": "" },
			{ "data": "rup.cognome", "defaultContent": "" }
		],
		"language": {
			"sEmptyTable":     "Nessun lavoro trovato",
			"sInfo":           "Visualizzazione da _START_ a _END_ di _TOTAL_ lavori",
			"sInfoEmpty":      "",
			"sInfoFiltered":   "(su _MAX_ lavori totali)",
			"sInfoPostFix":    "",
			"sInfoThousands":  ",",
			"sLengthMenu":     "Visualizza _MENU_",
			"sLoadingRecords": "Elaborazione...",
			"sProcessing":     "Elaborazione...",
			"sSearch":         "Cerca lavori",
			"sZeroRecords":    "Nessun lavoro trovato",
			"oPaginate": {
				"sFirst":      "<<",
				"sPrevious":   "<",
				"sNext":       ">",
				"sLast":       ">>"
			}
		},
		initComplete: function () {
			var api = this.api();
 			api.columns().indexes().flatten().each( function ( i ) {
				if (i == 1 || i == 2) {
					var column = api.column( i );
					var select = $('<select><option value=""></option></select>').appendTo( $(column.footer()).empty() ).on( 'change', function () {
						var val = $(this).val();
 						column.search( val ? '^'+val+'$' : '', true, false ).draw();
					});
 					column.data().unique().sort().each( function ( d, j ) {
 						if (d != null) {
 							select.append( '<option value="'+d+'">'+d+'</option>' )
 						}
					});
				}
			});
		},
		"order": [[ 1, "asc" ],[2, "asc"]],
		"lengthMenu": [[6], ["6"]]
	});
	
	$("#listaLavori_length").hide();
	$("#listaLavori_filter").hide();
	
}

function _wait() {
	$("#bloccaScreen").css("visibility","visible");
	$('#bloccaScreen').css("width",$(document).width());
	$('#bloccaScreen').css("height",$(document).height());
	$("#wait").css("visibility","visible");
	$("#wait").offset({ top: $(window).height() / 2, left: ($(window).width() / 2) - 200});
}

function _nowait() {
	document.getElementById('bloccaScreen').style.visibility='hidden';
	document.getElementById('wait').style.visibility='hidden';
}





