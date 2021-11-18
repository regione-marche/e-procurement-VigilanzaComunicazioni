<%
  /*
   * Created on 11-gen-2018
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="PTAPPROVAZIONI" pagesize="20" sortColumn="2" tableclass="datilista" gestisciProtezioni="true"
			where='PTAPPROVAZIONI.CONINT = #INTTRI.CONINT#'>

				<gene:campoLista campo="CONINT" visibile="false"/>
				<gene:campoLista campo="NUMAPPR" visibile="false"/>
				<gene:campoLista campo="DATAAPPR" />
				<gene:campoLista campo="FASEAPPR"/>
				<gene:campoLista campo="ESITOAPPR" />
				<gene:campoLista campo="UTENTEAPPR" />
				<gene:campoLista campo="NOTEAPPR" visibile="false"  />
				<gene:campoLista campo="NOTEAPPR_TRONCATO_" definizione="T2000;;;NOTE;" title="Note" campoFittizio="true" ordinabile="true" value="${fn:substring(datiRiga.PTAPPROVAZIONI_NOTEAPPR, 0, 199)}" visibile="true" >
					<c:if test="${fn:length(datiRiga.PTAPPROVAZIONI_NOTEAPPR) ge 200}">
						<span id="OVER200_${datiRiga.row}"><b> (<i><u>leggi tutto</u></i>)</b> </span> 
					</c:if>
					<input id="NOTEAPPR_COMPLETO_${datiRiga.row}" type="hidden" value="${datiRiga.PTAPPROVAZIONI_NOTEAPPR}" />
				</gene:campoLista>
		</gene:formLista></td>
	</tr>
	<gene:redefineInsert name="listaNuovo" />
	<gene:redefineInsert name="listaEliminaSelezione" />
</table>

<div id="DIV_NOTEAPPR_DESCR" class="ui-widget ui-widget-content ui-corner-all" style="position:absolute; width:388px; height:180px; z-index:99999; display:none;">
		<table class="dettaglio-notab">
			<tr>
				<td class="valore-dato" style="padding-top: 8px; border-bottom: 0px;">
					<textarea readonly id="TEXT_NOTEAPPR_DESCR" rows="10" cols="35" style="resize: none;"></textarea>					
				</td>
			</tr>
			<tr>
				<td class="valore-dato" style="border-bottom: 0px; border-top: 0px;">
					<input type="button" class="bottone-azione" title="Chiudi" value="Chiudi" onclick="javascript:$('#DIV_NOTEAPPR_DESCR').hide(200);" />	
				</td>
			</tr>
		</table>
	</div>
		
	
	<gene:javaScript>
			
		$("[id^='OVER200_']").on("mouseover",function() {			
			$(this).css('cursor', 'pointer');
		});
			
		$("[id^='OVER200_']").on("click",function() {			
			var id_over200= $(this).attr("id");
			var id_noteappr_completo = "NOTEAPPR_COMPLETO_"+id_over200.substring(8);
			var valore_by_id = $("#"+id_noteappr_completo).val();
			var position = $(this).position();
			$("#DIV_NOTEAPPR_DESCR").css('top',position.top + 20);
			$("#DIV_NOTEAPPR_DESCR").css('left',position.left);
			$("#TEXT_NOTEAPPR_DESCR").css('width',$("#DIV_NOTEAPPR_DESCR").width()- 20);
			$("#TEXT_NOTEAPPR_DESCR").css('height',$("#DIV_NOTEAPPR_DESCR").height() - 50);
			$("#DIV_NOTEAPPR_DESCR").show(100);
			$("#TEXT_NOTEAPPR_DESCR").focus();
			$("#TEXT_NOTEAPPR_DESCR").val(valore_by_id);		
		});
			
	</gene:javaScript>

