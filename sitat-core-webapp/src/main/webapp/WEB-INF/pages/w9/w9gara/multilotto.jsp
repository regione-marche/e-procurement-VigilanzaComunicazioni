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
<gene:template file="scheda-template.jsp">

<gene:setString name="titoloMaschera" value='Imposta monitoraggio multilotto' />
<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="corpo">

	<c:choose>
		<c:when test="${!empty alert}">
			<table class="lista">
				<tr>
					<td colspan="2" class="valore-dato">
						<br>
						<b>ATTENZIONE: nella gara è già impostato il monitoraggio multilotto; procedendo ne verranno cancellate le impostazioni. 
						<br><br>Continuare?</b>
						<br><br>
					</td>
				</tr>
			</table>
		</c:when>
		<c:when test="${!empty errore}">
			<table class="lista">
				<tr>
					<td colspan="2" class="valore-dato">
						<label style="color:red"><b>${errore}</b></label>
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<br>
			<b><label id="infoCigMaster"> - Selezionare con un click, la riga relativa al lotto CIG Master</label></b>
			<br><br>
			<table id="lottiList" class="datilista" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th width="50">Accoppia
							<a href="javascript:selectAll();"><img src='${pageContext.request.contextPath}/img/ico_check.gif' height='15' width='15' title='Seleziona tutti'></a>
							<a href="javascript:selectNone();"><img src='${pageContext.request.contextPath}/img/ico_uncheck.gif' height='15' width='15' title='Deseleziona tutti'></a>
						</th>
						<th width="50">N. progr.</th>
						<th width="50">Codice CIG</th>
						<th>Oggetto</th>
						<th width="50">Importo</th>
					</tr>
				</thead>
				<tbody>
				</tbody>  
			</table>	
		</c:otherwise>
	</c:choose>
	
	<table class="dettaglio-notab">
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Annulla" id="annulla" title="Annulla" class="bottone-azione"/>&nbsp;&nbsp;
				<input type="button" value="Continua" id="continua" title="Continua" class="bottone-azione" />&nbsp;&nbsp;
				<input type="button" value="Termina" id="termina" title="Termina" class="bottone-azione" />&nbsp;&nbsp;
			</td>
		</tr>
	</table>

</gene:redefineInsert>

<gene:javaScript>

$( "#annulla" ).on("click",function() {
	historyVaiIndietroDi(1);
});
		
<c:choose>
	<c:when test="${!empty alert}">
		$("#termina").hide();
		
		$( "#continua" ).on("click",function() {
    		location.href = '${pageContext.request.contextPath}/w9/Multilotto.do?' + csrfToken + '&metodo=inizializza&reset=ok&codgara=${codgara}';
		});
	</c:when>
	<c:when test="${!empty errore}">
		$("#termina").hide();
		$("#continua").hide();
	</c:when>
	<c:otherwise>
	
	
var dataSet = ${requestScope.lotti };
var avanzamento = 0;//0-scelta CIG Master, 1-Scelta CIG accoppiati
var raggruppamentoAttivo = 0;//raggruppamento attivo
var continua = false;//flag che gestisce la visualizzazione dei tasti "Continua" e "Termina"
	
$(document).ready(function() {
    $('#lottiList').DataTable( {
        data: dataSet,
		columns: [
		{
                "data":   "accoppiato",
                "render": function ( data, type, row ) {
                    if ( type === 'display' ) {
                        return '<input type="checkbox" class="editor-active">';
                    }
                    return data;
                },
                "orderable": false,
                className: "dt-body-center"
        },
        { data: 'nlott' },
        { data: 'cig' },
        { data: 'oggetto' },
        { data: 'importo', render: $.fn.dataTable.render.number( '.', ',', 2 ) }
        ],
        "order": [[1,'asc']],
        "oLanguage": {
        	"oPaginate": {
				"sNext": "Successiva",
				"sPrevious": "Precedente",
				"sFirst": "Prima",
				"sLast": "Ultima"
         	},
         	"sSearch": "Ricerca e Seleziona la riga del CIG master:",
         	"sInfo": "Trovati _TOTAL_ lotti. Visualizzazione da _START_ a _END_.",
		 	"sInfoFiltered":   "(su _MAX_ lotti totali)",
		 	"sLengthMenu": "_MENU_ lotti per pagina",
		 	"sEmptyTable": "Nessun lotto estratto",
		 	"sInfoEmpty": "Nessun lotto estratto",
		 	"sZeroRecords": "Nessun lotto estratto"
       	},
		rowCallback: function ( row, data ) {
            // Set the checked state of the checkbox in the table
            $('input.editor-active', row).prop( 'checked', data.accoppiato == 1 );
            $('input.editor-active', row).prop( 'disabled', data.master == 1 );
        }
    } );
    
    var table = $('#lottiList').DataTable();
    checkTerminato();
 	table.column( 0 ).visible( false );
 	
 	$('#lottiList tbody').on( 'click', 'tr', function () {
		var data = table.row( this ).data();
		if (avanzamento == 0) {
			//table.$('tr.selected').removeClass('selected');
          	//$(this).addClass('selected');
			raggruppamentoAttivo = data['raggruppamento'];
			data['master']=1;
			data['accoppiato'] = 1;
			$(this).toggleClass('selected');
           	avanzamento = 1; //MasterCig selezionato
           	//nascondere filtro ricerca
			$(".dataTables_filter").hide();
			table.search("");
			table.column( 0 ).visible( true );
			$('#infoCigMaster').text(" - Selezionare le righe dei lotti da accorpare al CIG Master");
			enableDisableButton();
		} else if (avanzamento == 1 && data['master'] != 1) {
			if (data['accoppiato'] == 1) {
				data['accoppiato'] = 0;
			} else {
				data['accoppiato'] = 1;
			}
		}
        //visualizzare cig dello stesso raggruppamento 
        table.draw();
   	} )
   	.on( 'mouseover', 'tr', function () {
        $( table.rows().nodes() ).removeClass( 'tableRollOverEffect1' );
        $( table.row( this ).nodes() ).addClass( 'tableRollOverEffect1' );
    } )
    .on( 'mouseleave', function () {
        $( table.rows().nodes() ).removeClass( 'tableRollOverEffect1' );
    } );
   
    $( "#continua" ).on("click",function() {
    	table.rows().iterator( 'row', function ( context, index ) {
    		var row = this.row(index).data();
    		if (raggruppamentoAttivo == row.raggruppamento) {
    			row.visibile = 0;
    		}
		} );
		raggruppamentoAttivo = 0;
		avanzamento = 0;
		$(".dataTables_filter").show();
		table.draw();
		$('#infoCigMaster').text("- Selezionare con un click, la riga relativa al lotto CIG Master");
		checkTerminato();
	});
 	
 	$( "#termina" ).on("click",function() {
 		var associazioni = "";
    	table.rows().iterator( 'row', function ( context, index ) {
    		var row = this.row(index).data();
    		if (row.accoppiato) {
    			associazioni += row.raggruppamento + ";" + row.nlott + ";" + row.master + "-";
    		}
		} );
		location.href = '${pageContext.request.contextPath}/w9/Multilotto.do?' + csrfToken + '&metodo=validazione&codgara=${codgara}&associazioni=' + associazioni;
	});
	
	function checkTerminato() {
		//verifico se ho ancora accoppiamenti da fare
		var raggruppamento = 0;
		continua = false;
    	table.rows().iterator( 'row', function ( context, index ) {
    		var row = this.row(index).data();
    		if (row.visibile == 1) {
    			if (raggruppamento == 0) {
    				raggruppamento = row.raggruppamento;
    			} else if (row.raggruppamento != raggruppamento) {
    				continua = true;
    			}
    		}
		} );
		enableDisableButton();
	}
	
	function enableDisableButton() {
	    if (avanzamento == 0) {
	    	$("#continua").attr("disabled","disabled");
	    	$("#continua").css("color","gray");
	    	$("#termina").attr("disabled","disabled");
	    	$("#termina").css("color","gray");
	    } else if (continua) {
	    	$("#continua").removeAttr("disabled");
	    	$("#continua").css("color","white");
	    	$("#termina").attr("disabled","disabled");
	    	$("#termina").css("color","gray");
		}
		else {
			$("#continua").attr("disabled","disabled");
			$("#continua").css("color","gray");
	    	$("#termina").removeAttr("disabled");
	    	$("#termina").css("color","white");
		}
	}
	
} );

function selectAll() {
	var table = $('#lottiList').DataTable();
	table.rows().iterator( 'row', function ( context, index ) {
    	var row = this.row(index).data();
    	if (raggruppamentoAttivo == row.raggruppamento && row.visibile == 1) {
    		row.accoppiato = 1;
    	}
	} );
	table.draw();
}

function selectNone() {
	var table = $('#lottiList').DataTable();
	table.rows().iterator( 'row', function ( context, index ) {
    	var row = this.row(index).data();
    	if (raggruppamentoAttivo == row.raggruppamento && row.visibile == 1 && row.master == 0) {
    		row.accoppiato = 0;
    	}
	} );
	table.draw();
}

$.fn.dataTable.ext.search.push(
    function( settings, data, dataIndex, row, counter ) {
        //visualizzo solo i cig dello dello stesso raggruppamento del master e non ancora valutati
        if ( row.visibile == 1 && (raggruppamentoAttivo == 0 || raggruppamentoAttivo == row.raggruppamento))
        {
            return true;
        }
        return false;
    }
);
		
	</c:otherwise>
</c:choose>

</gene:javaScript>

</gene:template>

