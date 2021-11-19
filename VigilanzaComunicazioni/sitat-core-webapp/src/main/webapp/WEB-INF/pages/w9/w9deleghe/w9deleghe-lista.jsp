<%
/*
 * Created on: 13-Lug-2009
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Lista degli utenti */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetValoriDelegheRUPFunction" />

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9DELEGHE-Scheda">
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
		<link rel="STYLESHEET" type="text/css" href="${pageContext.request.contextPath}/css/jquery/dataTables/jquery.dataTables.css">
	</gene:redefineInsert>
	<gene:setString name="titoloMaschera" value="Gruppo di collaborazione" />
	
	<gene:redefineInsert name="corpo">
					<table class="dettaglio-notab">
						<td class="valore-dato" colspan="2">
						RUP: <select name="codRup" id="codRup">
						<c:if test='${!empty listaRUP}'>
							<c:forEach items="${listaRUP}" var="valoriRUP">
								<option value="${valoriRUP[0]}">${valoriRUP[1]}</option>
							</c:forEach>
						</c:if>
						</select>
						</td>
					</table>

					<table id="userList" class="datilista" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th width="50">Associato</th>
								<th>Codice tecnico</th>
								<th>Nome tecnico</th>
								<th>Codice fiscale</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<table cellspacing="0" width="100%">
						<tr>
						 <td align="right" id="pulsantiera">
						 	<INPUT type="button" id="modifica-associazioni" class="bottone-azione" value='Modifica associazioni' title='Modifica associazioni' onclick="javascript:modificaAssociazioni();">
						 </td>
						</tr>
					</table>
	<input type="hidden" name="modoAperturaScheda" id="modoAperturaScheda" value="VISUALIZZA"/>
	<input type="hidden" name="listaTecniciAssociati" id="listaTecniciAssociati" value=""/>
	
	
	</gene:redefineInsert>
	
	<gene:javaScript>

  $(document).ready(function() {
	_wait();
    $('#userList').dataTable( {
    	"bFilter": false,
    	"bLengthChange": false,
        "processing": true,
		"deferRender": true,
        "iDisplayLength": 100,
		"oLanguage": {
		 "oPaginate": {
			"sNext": "Successiva",
			"sPrevious": "Precedente",
			"sFirst": "Prima",
			"sLast": "Ultima"
         },
         "sProcessing": "",
		 "sSearch": "Ricerca:",
		 "sInfo": "Trovati _TOTAL_ utenti. Visualizzazione da _START_ a _END_.",
		 "sInfoFiltered":   "(su _MAX_ utenti totali)",
		 "sLengthMenu": "_MENU_ utenti per pagina",
		 "sEmptyTable": "Nessun utente estratto",
		 "sInfoEmpty": "Nessun utente estratto",
		 "sZeroRecords": "Nessun utente estratto"
        },
		"columns": [
			{
                "data":   "active",
                "render": function ( data, type, row ) {
                    if ( type === 'display' ) {
                        return '<input type="checkbox" class="editor-active">';
                    }
                    return data;
                },
                className: "dt-body-center"
            },
            { "data": "codiceTecni" },
            { "data": "nomeTecni" },
            { "data": "cfTecni" }
        ],
		"order": [[2,'asc']],
        "ajax": {
            "url": "${pageContext.request.contextPath}/w9/GetDeleghe.do?codRup=${listaRUP[0][0]}&modoAperturaScheda=" + document.getElementById('modoAperturaScheda').value,
			"complete": function() {
					_nowait();
	            }
        },
		rowCallback: function ( row, data ) {
            // Set the checked state of the checkbox in the table
            $('input.editor-active', row).prop( 'checked', data.active == 1 );
        }
    } );
    
    
	var table = $('#userList').DataTable();
    $('#userList tbody')
        .on( 'mouseover', 'tr', function () {
            $( table.rows().nodes() ).removeClass( 'tableRollOverEffect1' );
            $( table.row( this ).nodes() ).addClass( 'tableRollOverEffect1' );
        } )
        .on( 'mouseleave', function () {
            $( table.rows().nodes() ).removeClass( 'tableRollOverEffect1' );
        } );
        if (document.getElementById('modoAperturaScheda').value == "VISUALIZZA") {
			table.column( 0 ).visible( false );
		} else {
			table.column( 0 ).visible( true );
		}
		table.column( 1 ).visible( false );
	
} );

$('#userList').on( 'change', 'input.editor-active', function () {
	var table = $('#userList').DataTable();
	var column = $(this).parents('td');
	var enable = $(this).prop( 'checked' );
	if (enable) {
		table.cell(column).data('1').draw();
	} else {
		table.cell(column).data('0').draw();
	}
} );

function _wait() {
	document.getElementById('bloccaScreen').style.visibility='visible';
	document.getElementById('wait').style.visibility='visible';
	$("#wait").offset({ top: $(window).height() / 2, left: ($(window).width() / 2) - 200});
}
	
function _nowait() {
	document.getElementById('bloccaScreen').style.visibility='hidden';
	document.getElementById('wait').style.visibility='hidden';
}

function modificaAssociazioni() {
   	var table = $('#userList').DataTable();
   	var codRup = document.getElementById('codRup').value;
   	document.getElementById('modoAperturaScheda').value = "MODIFICA";
   	table.ajax.url( '${pageContext.request.contextPath}/w9/GetDeleghe.do?modoAperturaScheda=' + document.getElementById('modoAperturaScheda').value + '&codRup=' + codRup ).load();
	table.column( 0 ).visible( true );
	$("#modifica-associazioni").remove();
	var pulsanti = '<INPUT type="button" id="salva" class="bottone-azione" value="Salva" title="Salva" onclick="javascript:salva();">&nbsp;<INPUT type="button" id="annulla" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();">';
	$("#pulsantiera").append(pulsanti);
}

function salva() {
   	var table = $('#userList').DataTable();
   	document.getElementById('listaTecniciAssociati').value = '';
   	table.rows().indexes().each( function (idx) {
		if (table.cell( idx, 0 ).data() == '1') {
			document.getElementById('listaTecniciAssociati').value += table.cell( idx, 1 ).data() + ";";
		}
	} );
   	var codRup = document.getElementById('codRup').value;
   	document.getElementById('modoAperturaScheda').value = "VISUALIZZA";
   	table.ajax.url( '${pageContext.request.contextPath}/w9/GetDeleghe.do?listaTecniciAssociati=' + document.getElementById('listaTecniciAssociati').value + '&modoAperturaScheda=' + document.getElementById('modoAperturaScheda').value + '&codRup=' + codRup ).load();
	table.column( 0 ).visible( false );
	$("#salva").remove();
	$("#annulla").remove();
	var pulsanti = '<INPUT type="button" id="modifica-associazioni" class="bottone-azione" value="Modifica associazioni" title="Modifica associazioni" onclick="javascript:modificaAssociazioni();">';
	$("#pulsantiera").append(pulsanti);
}

function annulla() {
   	var table = $('#userList').DataTable();
   	var codRup = document.getElementById('codRup').value;
   	document.getElementById('modoAperturaScheda').value = "VISUALIZZA";
   	table.ajax.url( '${pageContext.request.contextPath}/w9/GetDeleghe.do?modoAperturaScheda=' + document.getElementById('modoAperturaScheda').value + '&codRup=' + codRup ).load();
	table.column( 0 ).visible( false );
	$("#salva").remove();
	$("#annulla").remove();
	var pulsanti = '<INPUT type="button" id="modifica-associazioni" class="bottone-azione" value="Modifica associazioni" title="Modifica associazioni" onclick="javascript:modificaAssociazioni();">';
	$("#pulsantiera").append(pulsanti);
}


</gene:javaScript>

</gene:template>


	

	

  