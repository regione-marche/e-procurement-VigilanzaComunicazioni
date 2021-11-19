<%/*
   * Created on 11-dic-2014
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

	<table class="dettaglio-tab-lista">
		<gene:set name="titoloMenu">
			<a href='javascript:selezionaAll();' Title='Seleziona tutti'> <img src='${pageContext.request.contextPath}/img/ico_check.gif' height='15' width='15' alt='Seleziona tutti'></a>
			&nbsp;
			<a href='javascript:deselezionaAll();' Title='Deseleziona tutti'><img src='${pageContext.request.contextPath}/img/ico_uncheck.gif' height='15' width='15' alt='Deseleziona tutti'></a>
		</gene:set>
		
		<tr>
			<td>
				<gene:formLista entita="UFFINT" 
						tableclass="datilista" sortColumn="3" gestisciProtezioni="false" pagesize="20"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9CF_MODPERMESSI_SA" >
					
					<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
						<center>
							<input type="checkbox" id="chiave_${datiRiga.row}" name="chiave_${datiRiga.row}" value="${datiRiga.UFFINT_CODEIN}" <c:if test='${datiRiga.UFFINT_CODEIN eq datiRiga.W9CF_MODPERMESSI_SA_CODEIN}'> checked="checked" </c:if> onclick="javascript:attivaApplica(${datiRiga.row})"/>
						</center>
					</gene:campoLista>
					
					<gene:campoLista campo="CODEIN" ordinabile="false" visibile="true"/>
					<gene:campoLista campo="NOMEIN" ordinabile="false" />
					
					<gene:campoLista campo="CODCONF" visibile="false" edit="${param.updateLista eq 1}" entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />
					<gene:campoLista campo="CODEIN"  visibile="false" entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />
					<gene:campoLista campo="APPLICA" ordinabile="false" edit="${param.updateLista eq 1}"
							entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />

				</gene:formLista>
			</td>
		</tr>

		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="listaEliminaSelezione" />

		<gene:redefineInsert name="listaNuovo">
			<tr><td class="vocemenulaterale"><a
					href="javascript:listaSalva();" title="Salva"
					tabindex="1501">Salva</a></td></tr>
			<tr><td class="vocemenulaterale"><a
					href="javascript:listaAnnullaModifica();" title="Annulla"
					tabindex="1501">Annulla</a></td></tr>
		</gene:redefineInsert>
		<gene:redefineInsert name="pulsanteListaInserisci">
				<INPUT type="button" class="bottone-azione" title='Modifica permessi'
				value="Salva" onclick="javascript:listaConferma();">
				
				<INPUT type="button" class="bottone-azione" title='Annulla'
				value="Annulla" onclick="javascript:listaAnnullaModifica();">
		</gene:redefineInsert>

		<tr>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
		</tr>
		
		<gene:javaScript>
			
			$(document).ready(function() {
				$( "form select" ).attr("disabled", "disabled");
				
				var arrayCheck = $( "form input:checkbox" );
				if (arrayCheck && arrayCheck.length > 0) {
					for (var i=1; i <= arrayCheck.length; i++) {
						if ( $("#chiave_"+ i).attr("checked") ) {
							$( "#W9CF_MODPERMESSI_SA_APPLICA_" + i).removeAttr("disabled");
						}
					}
				}
			});

			function attivaApplica(id) {
				if ( $( "#" + ("chiave_" + id)).attr("checked") ) {
					$( "#" + "W9CF_MODPERMESSI_SA_APPLICA_" + id).removeAttr("disabled");
					$( "#" + "W9CF_MODPERMESSI_SA_APPLICA_" + id).attr("value", "1");
				} else {
					$( "#W9CF_MODPERMESSI_SA_APPLICA_" + id).attr("disabled", "disabled");
					$( "#W9CF_MODPERMESSI_SA_APPLICA_" + id).attr("value", "");
				}
			}

			function selezionaAll() {
				$( "input:checkbox[name^='chiave_']" ).attr("checked", "checked");
				$( "select[name^='W9CF_MODPERMESSI_SA_APPLICA_']" ).removeAttr("disabled");
				$( "select[name^='W9CF_MODPERMESSI_SA_APPLICA_']" ).attr("value", "1");
			}
			
			function deselezionaAll() {
				$( "input:checkbox[name^='chiave_']" ).removeAttr("checked");
				$( "select[name^='W9CF_MODPERMESSI_SA_APPLICA_']" ).attr("disabled", "disabled");
				$( "select[name^='W9CF_MODPERMESSI_SA_APPLICA_']" ).attr("value", "");
			}

			function listaSalva() {
				$( "select[name^='W9CF_MODPERMESSI_SA_APPLICA_']" ).removeAttr("disabled");
				listaConferma();
			}

		</gene:javaScript>
</table>
