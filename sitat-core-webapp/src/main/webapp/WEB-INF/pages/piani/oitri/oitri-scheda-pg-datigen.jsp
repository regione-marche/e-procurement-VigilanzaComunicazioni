<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>


		<gene:formScheda entita="OITRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreOITRI">

			<gene:campoScheda>
				<td colspan="2"><b>Dati generali programma</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CONTRI" value ="${contri}" visibile="false" />
			<gene:campoScheda campo="NUMOI" visibile="false" />
			<gene:campoScheda campo="CUP" />
			<gene:campoScheda campo="CUPMASTER" visibile="false" />
			<gene:campoScheda campo="DESCRIZIONE" />
			<gene:campoScheda campo="DETERMINAZIONI" />
			<gene:campoScheda campo="AMBITOINT" />
			<gene:campoScheda>
				<td colspan="2"><b>Importi</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="ANNOULTQE" />
			<gene:campoScheda campo="IMPORTOINT" />
			<gene:campoScheda campo="IMPORTOLAV" />
			<gene:campoScheda campo="IMPORTOSAL" />
			<gene:campoScheda campo="ONERIULTIM" />
			<gene:campoScheda campo="AVANZAMENTO" modificabile="false">
				<gene:calcoloCampoScheda 
   				funzione='calcoloAvanzamento("#OITRI_IMPORTOSAL#","#OITRI_IMPORTOLAV#","#OITRI_ONERIULTIM#")' 
  				elencocampi="OITRI_IMPORTOSAL;OITRI_IMPORTOLAV;OITRI_ONERIULTIM" />
			</gene:campoScheda>
			<gene:campoScheda>
				<td colspan="2"><b>Caratteristiche dell'opera</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CAUSA" />
			<gene:campoScheda campo="STATOREAL" />
			<gene:campoScheda campo="INFRASTRUTTURA" />
			<gene:campoScheda campo="DISCONTINUITA_RETE" />
			<gene:campoScheda campo="FRUIBILE" />

			<gene:campoScheda>
				<td colspan="2"><b>Possibile utilizzo dell'opera</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="UTILIZZORID" />
			<gene:campoScheda campo="DESTINAZIONEUSO" />
			<gene:campoScheda campo="CESSIONE" />
			<gene:campoScheda campo="VENDITA" />
			<gene:campoScheda campo="DEMOLIZIONE" />
			<gene:campoScheda campo="ONERI_SITO" />

			<jsp:include page="immobili_da_trasferire.jsp">
				<jsp:param name="keyInttri" value="${keyOITRI}" />
			</jsp:include>		

			<c:if test='${modificabile eq "no"}' >
				<gene:redefineInsert name="schedaNuovo" />
				<gene:redefineInsert name="schedaModifica" />
				<gene:redefineInsert name="pulsanteNuovo" />
				<gene:redefineInsert name="pulsanteModifica" />
			</c:if>
	
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
			
			<gene:fnJavaScriptScheda funzione='gestioneInfrastruttura()' elencocampi="OITRI_INFRASTRUTTURA" esegui="true" />
			
			<gene:javaScript>

			<c:choose>
				<c:when test='${(!empty listaImmobiliDaTrasfInterv)}'>
					var idUltimoImmobileVisualizzato = ${fn:length(listaImmobiliDaTrasfInterv)};
					var maxIdImmobileVisualizzabile = ${fn:length(listaImmobiliDaTrasfInterv)+5};
				</c:when>
				<c:otherwise>
					var idUltimoImmobileVisualizzato = 0;
					var maxIdImmobileVisualizzabile = 5;
				</c:otherwise>
			</c:choose>
			
			$(document).ready(function() {
				
				function checkVendita()	{
					var vendita = toVal(getValue('OITRI_VENDITA'));
					var cessione = toVal(getValue('OITRI_CESSIONE'));
					if (vendita == 2) {
						$("#rowOITRI_DEMOLIZIONE").show();
					} else {
						$("#rowOITRI_DEMOLIZIONE").hide();
						setValue("OITRI_DEMOLIZIONE", "");
					}
					
					if (vendita == 1 || cessione == 1) {
						$("#rowlabelImmTrasf").show();
						$("#rowLinkVisualizzaNuovoImmobile").show();
					} else {
						$("#rowlabelImmTrasf").hide();
						$("#rowdatiImmobile").hide();							
						for(var indiceProgressivo = 0; indiceProgressivo <= maxIdImmobileVisualizzabile; indiceProgressivo++) {
							nascondiSezioneImmobile(indiceProgressivo, false);
							
							if (getValue("IMMTRAI_CUIIMM_" + indiceProgressivo) != "") {
								setValue("IMMTRAI_DEL_IMMTRAI_" + indiceProgressivo, "1");
							}
						}
						$("#rowLinkVisualizzaNuovoImmobile").hide();
					}
				};
				
				checkVendita();
				$('#OITRI_VENDITA').on("change",function() {
					checkVendita();
				});
				$('#OITRI_CESSIONE').on("change",function() {
					checkVendita();
				});
			});
			
			function formCUIIMM(modifica, campo1, campo2) {
				<c:if test='${modo ne "VISUALIZZA"}'>
					alert("Attenzione: il CUI dell'immobile deve risultare univoco!");
				</c:if>
				
				openPopUpCustom("href=piani/oitri/dettaglio-codice-cuiimm.jsp&key=" + document.forms[0].key.value + 
					"&keyParent=" + document.forms[0].keyParent.value + "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA") + 
					"&campo1=" + campo1 + "&cui=" + getValue(campo1) + "&campo2=" + campo2 + "&contri="+ getValue(campo2),
					"formCUIIMM", 700, 300, 1, 1);
			}

			function calcoloAvanzamento(importoSAL, importoLAV, importoONERI) {
				var result = 0;
				if (importoONERI == "")
					importoONERI = "0";
				if (importoLAV == "")
					importoLAV = "0";
				if (importoSAL == "")
					importoSAL = "0";
				
				if (importoSAL == 0) {
					if ((eval(importoLAV) + eval(importoONERI)) != 0) {
						return "0";
					} else {
						return "";
					}
				} else {
					if ((eval(importoLAV) + eval(importoONERI)) > 0) {
						result = 100 * (eval(importoSAL) / (eval(importoLAV) + eval(importoONERI)));
					}
				}

				return eval(result).toFixed(2);
 			}
 	
 			function gestioneInfrastruttura() {
 				var infrastruttura = getValue("OITRI_INFRASTRUTTURA");
 				if ("1" == infrastruttura) {
 					showObj("rowOITRI_DISCONTINUITA_RETE", true);
 				} else {
 					showObj("rowOITRI_DISCONTINUITA_RETE", false);
 					setValue("OITRI_DISCONTINUITA_RETE", "");
 				}
 			}
 	
			</gene:javaScript>
		</gene:formScheda>