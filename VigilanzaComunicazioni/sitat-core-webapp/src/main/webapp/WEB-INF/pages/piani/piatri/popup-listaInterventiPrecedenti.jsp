<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>

<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="key" value="INTTRI.CONTRI=N:${param.codiceProgramma}" />
<c:set var="tiprog" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, key)}' />

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="listaInterventi">
	<gene:redefineInsert name="addHistory" />
	
	<c:choose>
		<c:when test='${param.tipo eq "ANN"}'>
			<gene:setString name="titoloMaschera" value="Lista interventi annullati o respinti del programma precedente" />
		</c:when>
		<c:otherwise>
			<gene:setString name="titoloMaschera" value="Lista interventi del programma precedente" />
		</c:otherwise>
	</c:choose>
	
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>

	<table class="lista">
		<tr>
			<td>
			
	<c:choose>
		<c:when test='${param.tipo eq "INR"}'>
				<% // Per gli interventi non riproposti %>
				<c:choose>
					<c:when test='${tiprog eq 1}'>
						La lista presenta l'elenco degli interventi nella prima annualit&agrave; dei programmi dell'anno precedente che non sono gi&agrave; stati riportati nel programma attuale
					</c:when>
					<c:otherwise>
						La lista presenta l'elenco degli acquisti nella prima annualit&agrave; dei programmi dell'anno precedente che non sono gi&agrave; stati riportati nel programma attuale
					</c:otherwise>
				</c:choose>
		</c:when>
		<c:when test='${param.tipo eq "ANN"}'>
			<% // Per gli interventi annullati %>
				<c:choose>
					<c:when test='${tiprog eq 1}'>
						La lista presenta l'elenco degli interventi respinti o annullati nella prima annualit&agrave; dei programmi dell'anno precedente che non sono gi&agrave; stati riportati nel programma attuale
					</c:when>
					<c:otherwise>
						La lista presenta l'elenco degli acquisti respinti o annullati nella prima annualit&agrave; dei programmi dell'anno precedente che non sono gi&agrave; stati riportati nel programma attuale
					</c:otherwise>
				</c:choose>
		</c:when>
		<c:otherwise>
				<c:choose>
					<c:when test='${tiprog eq 1}'>
						La lista presenta l'elenco degli interventi dei programmi precedenti che non sono gi&agrave; stati riportati nel programma attuale
					</c:when>
					<c:otherwise>
						La lista presenta l'elenco degli acquisti dei programmi precedenti che non sono gi&agrave; stati riportati nel programma attuale
					</c:otherwise>
				</c:choose>
		</c:otherwise>
	</c:choose>
				<br><br>
			</td>
		</tr>
	</table>

		<form name="listaInterventi" id="listaInterventi" method="post" action="RiportaInterventi.do?numeroPopUp=1">
			<table class="lista">
				<tr id="rowFiltri">
					<td colspan="2">
					<c:if test='${! empty listaIdProgrammi and fn:length(listaIdProgrammi) gt 0 and ! empty listaAnnualitaInterventiProgPrecedenti}' >
						<br>
						Filtra lista
						<br><br>
					</c:if>
					<c:choose>
						<c:when test='${! empty listaIdProgrammi and fn:length(listaIdProgrammi) gt 0}' >
						ID Programma: <select name="idProgramma" id="idProgramma" onchange="javascript:filtraLista();" >
							<c:forEach var="idProg1" items="${listaIdProgrammi}" varStatus="index">
								<c:choose>
									<c:when test="${idProg eq idProg1[0]}">
										<option value="${idProg1[0]}" selected="selected" >${idProg1[0]}</option>
									</c:when>
									<c:otherwise>
										<option value="${idProg1[0]}">${idProg1[0]}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<input type="hidden" name="idProgramma" id="idProgramma" value="" />
						</c:otherwise>
					</c:choose>
				
				<c:choose>
					<c:when test="${! empty listaAnnualitaInterventiProgPrecedenti}" >
						Anno intervento: <select name="annoIntervento" id="annoIntervento" onchange="javascript:filtraLista();" >
							<c:choose>
								<c:when test="${param.anno eq '-1' or request.anno eq '-1'}">
									<option value="-1" selected="selected" ></option>
								</c:when>
								<c:otherwise>
									<option value="-1"></option>
								</c:otherwise>
							</c:choose>
							<c:forEach var="anno1" items="${listaAnnualitaInterventiProgPrecedenti}" varStatus="status">
								<c:choose>
									<c:when test="${param.anno eq anno1[1] or request.anno eq anno1[1]}">
										<option value="${anno1[1]}" selected="selected" >${anno1[0]}</option>
									</c:when>
									<c:otherwise>
										<option value="${anno1[1]}">${anno1[0]}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="annoIntervento" id="annoIntervento" value="1" />
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${! empty listaAnnualitaInterventiProgPrecedenti || ! empty listaInterventi}" >
						CUI intervento: 
						<input type="text" name="cuiIntervento" id="cuiIntervento" value="" >
					</c:when>
					<c:otherwise>
						<input type="hidden" name="cuiIntervento" id="cuiIntervento" value="" />
					</c:otherwise>
				</c:choose>
				
					</td>
				</tr>
				<tr id="rowLista">
					<td colspan="2">
						<display:table name="listaInterventi" id="intervento" class="datilista" sort="list" requestURI="GetListaInterventiDaProgrammaPrecedente.do?codiceProgramma=${param.codiceProgramma}&numeroPopUp=1" >
							<display:column class="associadati" title="Opzioni<br><center><a href='javascript:selezionaTutti(document.forms[0].keys);' Title='Seleziona Tutti'> <img src='${pageContext.request.contextPath}/img/ico_check.gif' height='15' width='15' alt='Seleziona Tutti'></a>&nbsp;<a href='javascript:deselezionaTutti(document.forms[0].keys);' Title='Deseleziona Tutti'><img src='${pageContext.request.contextPath}/img/ico_uncheck.gif' height='15' width='15' alt='Deseleziona Tutti'></a></center>" style="width:50px">
								<center>
									<input type="checkbox" name="keys" id="keys" value="${intervento[0].value};${intervento[1].value};${param.codiceProgramma}" />
									<input type="hidden" name="datiRigaFabbisogniConint" value="${intervento[6].value}" />
								</center>
							</display:column>
							<display:column title="Annualita'" sortable="true" headerClass="sortable" >
								${intervento[5].value}
							</display:column>
							<display:column title="N. intervento CUI" sortable="true" headerClass="sortable" >
								${intervento[2].value}
							</display:column>
							<display:column title="Descrizione intervento" sortable="true" headerClass="sortable" >
								${intervento[3].value}
							</display:column>
							<fmt:formatNumber type="number" value="${intervento[4].value}" minFractionDigits="2" maxFractionDigits="2" groupingUsed="true" var="importo" />
							<c:choose>
								<c:when test="${empty importo}" >
									<c:set var="importo" value="0,00 &euro;" />
								</c:when>
								<c:otherwise>
									<c:set var="importo" value="${importo}  &euro;" />
								</c:otherwise>
							</c:choose>
							<display:column title="Importo totale intervento" sortable="false" headerClass="sortable" value="${importo}" >
							</display:column>
						</display:table>
					</td>
				</tr>
				<tr id="rowPulsanti">
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" id="Richiedi_revisione" class="bottone-azione" value='Richiedi revisione' title='Richiedi revisione selezionati' onclick="javascript:richiediRevisione()">
						<INPUT type="button" id="Riporta_interventi" class="bottone-azione" value='Riporta interventi' title='Riporta interventi selezionati' onclick="javascript:riportaInterventi()">
						<INPUT type="button"  class="bottone-azione" value='Annulla' title='Annulla' onclick="javascript:window.close()">
						&nbsp;
					</td>
				</tr>
			<c:if test="${empty param.tipo}" >
				<tr id="rowOperazione" style="display: none;" >
					<td colspan="2">
						<br>
						<b>Selezionare una delle seguenti opzioni:</b>
						<br><br>
					</td>
				</tr>
				<tr id="rowOperazione1" style="display: none;" >
					<td style="vertical-align: top;" >
						<input type="radio" name="operazione" id="operazione1" value="1" >&nbsp;
					</td>
					<td style="vertical-align: top;" >
						<b>Sbiancare il quadro economico</b>
						<br>
						Gli interventi precedentemente selezionati verranno copiati nel nuovo programma senza riportare gli importi del quadro economico
						<br><br>
					</td>
				</tr>
				<tr id="rowOperazione3" style="display: none;" >
					<td style="vertical-align: top;" >
						<input type="radio" name="operazione" id="operazione3" value="3" >&nbsp;
					</td>
					<td style="vertical-align: top;" >
						<b>Mantenere invariati gli importi del quadro economico</b>
						<br>
						Gli interventi precedentemente selezionati verranno copiati nel nuovo programma riportando gli importi del quadro economico nelle medesime annualit&agrave;
						<br><br>
					</td>
				</tr>
				<tr id="rowOperazione2" style="display: none;" >
					<td style="vertical-align: top;" >
						<input type="radio" name="operazione" id="operazione2" value="2" >&nbsp;
					</td>
					<td style="vertical-align: top;" >
						<b>Traslare gli importi di una annualit&agrave;</b>
						<br>
						Gli interventi precedentemente selezionati verranno copiati nel nuovo programma traslando gli importi del quadro economico di una annualit&agrave;. Gli importi della prima e seconda annualit&agrave; del programma precedente verranno riportati come somma nella prima annualit&agrave; del nuovo programma; i rimanenti importi verranno riportati traslati di una annualit&agrave;
						<br><br>
					</td>
				</tr>
				<tr id="rowPulsantiOperazione" style="display: none;" >
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button"  class="bottone-azione" value='Conferma' title='Riporta interventi selezionati' onclick="javascript:continua()">
						<INPUT type="button"  class="bottone-azione" value='Annulla' title='Annulla' onclick="javascript:window.close()">
						&nbsp;
					</td>
				</tr>
				
			</c:if>
			
			</table>
			
<c:choose>
	<c:when test="${param.tipo eq 'INR'}" >
			<input type="hidden" name="metodo" value="riportaInterventiNonRiproposti" />
	</c:when>
	<c:when test="${param.tipo eq 'ANN'}" >
			<input type="hidden" name="metodo" value="riportaInterventiAnnullatiRespinti" />
	</c:when>
	<c:otherwise>
			<input type="hidden" name="metodo" value="riportaInterventi" />
	</c:otherwise>
</c:choose>
		</form>
	</gene:redefineInsert>

	<gene:javaScript>
		var numero = 0;
	
		<c:choose>
			<c:when test="${param.tipo eq 'RDP_RevisioneInterventiAnnoPrecedente'}" >
				showObj("Riporta_interventi", false);
			</c:when>
			<c:otherwise>
				showObj("Richiedi_revisione", false);
			</c:otherwise>
		</c:choose>
		
		function richiediRevisione() {
			var valoriFabbisogni = "";
			var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
				return;
			} else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
				valoriFabbisogni = document.forms[0].datiRigaFabbisogniConint.value;
			} else {
				for (var i = 0; i < document.forms[0].keys.length; i++) {
					if (document.forms[0].keys[i].checked) {
						valoriFabbisogni += document.forms[0].datiRigaFabbisogniConint[i].value + ";;";
					}
				}
				valoriFabbisogni = valoriFabbisogni.substring(0,(valoriFabbisogni.length - 2));
			}
			//location.href = "${pageContext.request.contextPath}/ApriPagina.do?href=piani/ptapprovazioni/ptapprovazioni-popup-scheda.jsp&modo=NUOVO&fabbisogni=&funzionalita=";	
			location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-popup-scheda.jsp&modo=NUOVO&fabbisogni="+valoriFabbisogni+"&funzionalita=${param.tipo}&contri_PT=${contri}";
		}
	
	
		function riportaInterventi() {
			var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
				return;
			} else {
		<c:choose>
			<c:when test="${param.tipo eq 'INR'}" >
				document.listaInterventi.submit();
			</c:when>
			<c:when test="${param.tipo eq 'ANN'}" >
				document.listaInterventi.submit();
			</c:when>
			<c:when test="${not empty copiaInterventiDaProgrammaConStessaAnnualita}" >
				document.getElementById("operazione3").checked = true;
				document.listaInterventi.submit();
			</c:when>
			<c:otherwise>
				showObj("rowPulsanti", false);
				showObj("rowLista", false);
				showObj("rowFiltri", false);
				showObj("rowOperazione", true);
				showObj("rowOperazione1", true);
				showObj("rowOperazione2", true);
				showObj("rowOperazione3", true);
				showObj("rowPulsantiOperazione", true);
			</c:otherwise>
		</c:choose>
			}
		}

		function continua() {
			//var oper = document.listaInterventi.operazione.value;
			if (document.getElementById("operazione1").checked || document.getElementById("operazione2").checked || document.getElementById("operazione3").checked) {
				document.listaInterventi.submit();
			} else {
				alert("Selezionare una delle opzioni"); 
			}
		}

		function annulla() {
			window.close();
		}
		
		function filtraLista() {
			var filtroIdProg = document.getElementById("idProgramma").value;
			var filtroAnnoIntervento = document.getElementById("annoIntervento").value;
			var temp = "GetListaInterventiDaProgrammaPrecedente.do?" + csrfToken + "&codiceProgramma=${param.codiceProgramma}&tipo=${param.tipo}&numeroPopUp=1";
			if (filtroIdProg.length > 0) {
				temp = temp + "&idProg=" + filtroIdProg;
			}
			if (filtroAnnoIntervento.length > 0) {
				temp = temp + "&anno=" + filtroAnnoIntervento;
			}
			document.location.href= "${pageContext.request.contextPath}/piani/" + temp;
		}
		
		$('#cuiIntervento').on("input",function() {
			$val = $(this).val();
			$("#intervento tbody tr").each(function(){
			if($(this).children('td:nth-child(3)').text().indexOf($val) == -1) 
			{
				$(this).children('td:nth-child(1)').find(":checkbox[name='keys']").attr('checked', false);
				$(this).hide();
			}
			else
				$(this).show();
			});
		});

		
	</gene:javaScript>
</gene:template>