<%
  /*
   * Created on: 04/08/2009
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="codiceGara" value="${fn:substringAfter(key, ':')}" />
<c:set var="dati" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPubblicazioniGaraJsonFunction", pageContext,codiceGara,null)}'></c:set>

<gene:formScheda entita="W9GARA" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9GARA" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARA">
	<gene:redefineInsert name="schedaModifica"/>
	<gene:redefineInsert name="pulsanteModifica"/>
	<gene:redefineInsert name="addToAzioni" >
		<jsp:include page="azioniComuni-schedaGara.jsp" />
	</gene:redefineInsert>
	
	<gene:campoScheda campo="NLOTTI" visibile="false"/>
	<gene:campoScheda campo="PROV_DATO" visibile="false"/>
	<gene:campoScheda>
		<c:if test="${dati eq '[]'}">
			<td colspan="2"><b>Non &egrave; possibile effettuare pubblicazioni</b></td>
		</c:if>
		<c:if test="${dati ne '[]'}">
			<td colspan="2"><b>&nbsp;</b>
			<c:if test="${datiRiga.W9GARA_NLOTTI > 1}">
			<input type="radio" id="radioTutte" name="group1" value="Tutte" checked="checked">Tutte
			<input type="radio" id="radioCIG" name="group1" value="Solo del CIG:">Solo del CIG:<input type="text" id="codiceCIG" name="codiceCIG" styleClass="testo" size="12" maxlength="10"/>
			</c:if>
			<br>
			<div id="jstree2" class="demo"></div></td>		
		</c:if>
	</gene:campoScheda>
</gene:formScheda>

<gene:redefineInsert name="head">
	<script src="${pageContext.request.contextPath}/js/jstree/vakata.js"></script>
	<script src="${pageContext.request.contextPath}/js/jstree/jstree.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.12.1.js"></script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jstree/themes/default/style.min.css" />
	<style type="text/css">
		#jstree2 .even > .jstree-wholerow { background:#E7F1FF; } 
		#jstree2 .odd > .jstree-wholerow { background:#CEDAEB; } 
		#jstree2 .even > .jstree-wholerow-hovered{background:#7A91E6}
		#jstree2 .odd > .jstree-wholerow-hovered{background:#7A91E6}
		#jstree2 .jstree-wholerow-hovered{background:#7A91E6}
		#jstree2 .even > .jstree-search{font-style:normal;color:black;font-weight:normal}
		#jstree2 .odd > .jstree-search{font-style:normal;color:black;font-weight:normal}
		#jstree2 .jstree-search{font-style:normal;color:black;font-weight:normal}
		
		#jstree2, .demo { max-width:100%; overflow:auto; box-shadow:0 0 5px #ccc; padding:10px; border-radius:5px; }
		
		SPAN.mcontatoreatti {
		font: 10px Verdana, Arial, Helvetica, sans-serif;
		font-weight: bold;
		color: #FFFFFF;
		border: 1px solid #D30000;
		background-color: #D30000;
		padding-left: 2px;
		padding-right: 2px;
		float: right;
		-moz-border-radius-topleft: 2px; 
		-webkit-border-top-left-radius: 2px; 
		-khtml-border-top-left-radius: 2px; 
		border-top-left-radius: 2px; 
		-moz-border-radius-topright: 2px;
		-webkit-border-top-right-radius: 2px;
		-khtml-border-top-right-radius: 2px;
		border-top-right-radius: 2px;
		-moz-border-radius-bottomleft: 2px; 
		-webkit-border-bottom-left-radius: 2px; 
		-khtml-border-bottom-left-radius: 2px; 
		border-bottom-left-radius: 2px; 
		-moz-border-radius-bottomright: 42px;
		-webkit-border-bottom-right-radius: 2px;
		-khtml-border-bottom-right-radius: 2px;
		border-bottom-right-radius: 2px;
		}
	</style>
	
</gene:redefineInsert>

<gene:javaScript>

$(function() {
	$("#codiceCIG").autocomplete({
		delay: 0,
	    autoFocus: true,
	    position: { 
	    	my : "left top",
	    	at: "left bottom"
	    },
		source: function( request, response ) {
			var cig = $("#codiceCIG").val();
			$.ajax({
				async: false,
			    type: "GET",
                dataType: "json",
                beforeSend: function(x) {
       			if(x && x.overrideMimeType) {
           			x.overrideMimeType("application/json;charset=UTF-8");
		        }
   				},
                url: "${pageContext.request.contextPath}/w9/GetListaCIGPubblicati.do",
				data: "codgara=" + ${codiceGara} + "&cig=" + cig,
				success: function( data ) {
					if (!data) {
						$('#jstree2').jstree(true).search('');
						afterLoadTree();
						response([]);
					} else {
						response( $.map( data, function( item ) {
							return {
								label: item[0].value,
								value: item[0].value
							}
						}));
					} 
				},
                error: function(e){
                    $('#jstree2').jstree(true).search('');
					afterLoadTree();
                }
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			$("#radioCIG").prop('checked', true);
			$('#jstree2').jstree(true).search(ui.item.value);
			afterLoadTree();
		},
		open: function() {
			$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		},
		close: function() {
			$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		},
		change: function(event, ui) {
			var cig = $("#codiceCIG").val();
			$.ajax({
				async: false,
			    type: "GET",
                dataType: "json",
                beforeSend: function(x) {
       			if(x && x.overrideMimeType) {
           			x.overrideMimeType("application/json;charset=UTF-8");
			       }
   				},
                url: "${pageContext.request.contextPath}/w9/GetListaCIGPubblicati.do",
                data: "codgara=" + ${codiceGara} + "&cig=" + cig,
				success: function( data ) {
					if (!data) {
						$("#codiceCIG").val("");
					} 
				},
				error: function(e){
                   	$("#codiceCIG").val("");
                }
			});
		}
	});
});
		
$(function () {
	$('#jstree2').jstree({'plugins':["wholerow","search"], 
		'core' : {
			"themes" : { "stripes" : true },
			'data' : ${dati}
				},
		"search": {
			'case_insensitive': true,
			'show_only_matches': true
			}
	});

	$('#radioTutte').on("click",function () {
        if ($(this).is(':checked')) {
            $('#jstree2').jstree(true).search('');
			afterLoadTree();
        }
    });
});

$("#jstree2").on('ready.jstree', function(event, data) {
	afterLoadTree();
});

$('#jstree2').on('select_node.jstree', function (e, data) {
    data.instance.toggle_node(data.node);
});

$("#jstree2").on("select_node.jstree", function (e, data) {
	var href = data.node.a_attr.href + "&" + csrfToken;
     document.location.href = href;
});

function afterLoadTree() {
	var $tree = $("#jstree2");
	$($tree.jstree().get_json($tree, {
      flat: true
    })).each(function(index, value) {
      var node = $("#jstree2").jstree().get_node(this.id);
      var lvl = node.parents.length;
	  	var title = node.text;
      var idx = index;
		  if (node.parent == "#") {
			  $("#jstree2").jstree("close_all");
				if (node.children_d.length > 1) {
					var nodiVisibili = 0;
					for(var i=0;i < node.children_d.length; i++) {
						var figlio = $("#jstree2").jstree().get_node(node.children_d[i]);
						if (!figlio.state.hidden) {
							nodiVisibili ++;
						}
					}
					$("li#"+node.id).find("i:last span").remove(".mcontatoreatti");
					$("li#"+node.id).find("i:last").append("<span class='mcontatoreatti'>"
					+ nodiVisibili + "</span>");
				} else {
					$("li#"+node.id).find("i:last span").remove(".mcontatoreatti");
				}
		  }
		});
}

</gene:javaScript>