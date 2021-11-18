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
<c:set var="idUtente" value="${sessionScope.profiloUtente.id}" />
<c:set var="nomeUtente" value="${sessionScope.profiloUtente.nome}" />

<c:set var="keyPIATRI" value="PIATRI.CONTRI=N:${param.contri}" />
<c:set var="tiprog" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, keyPIATRI)}' />
<c:set var="checkProt_FiltroUffintListaProposte" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.FiltroUffintListaProposte")}'/>

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="listaInterventi">
	<gene:redefineInsert name="addHistory" />
	
	<gene:setString name="titoloMaschera" value="Importazione delle proposte" />
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>

		<form name="formListaFabbisogni" id="formListaFabbisogni" method="post" action="GetListaFabbisogni.do?numeroPopUp=1">
		
			<table id="tabRic" class="ricerca">
			
				<tr id="sezNOMEIN">	
					<td class="etichetta-dato">Ente/Ufficio intestatario</td>
					<td class="valore-dato-trova">
						<input type="text" name="nomeinRic" id="nomeinRic" size="73" value="${param.nomein}"/>
					</td>
				</tr>
				
				<tr id="sezNATGIU">
					<td class="etichetta-dato">${descrNATGIU}</td>
					<td class="valore-dato-trova">
						<select name="natgiuRic" id="natgiuRic" >
							<option value="">&nbsp;</option>
							<c:if test='${!empty listaNaturaGiuridica}'>
								<c:forEach items="${listaNaturaGiuridica}" var="naturaGiuridica">
									<option value="${naturaGiuridica[0]}">${naturaGiuridica[1]}</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>	
				
				<tr id="sezCONINT">	
					<td class="etichetta-dato">Codice</td>
					<td class="valore-dato-trova">
						<input type="text" name="conintRic" id="conintRic" size="6" value="${param.conint}"/>
					</td>
				</tr>
			
				<tr id="sezCODINT">	
					<td class="etichetta-dato">Codice interno attribuito dall'amministrazione</td>
					<td class="valore-dato-trova">
						<input type="text" name="codintRic" id="codintRic" size="24" value="${param.codint}"/>
					</td>
				</tr>
				
				<tr id="sezDESINT">	
					<td class="etichetta-dato">Descrizione dell'intervento</td>
					<td class="valore-dato-trova">
						<input type="text" name="desintRic" id="desintRic" size="73" value="${param.desint}"/>
					</td>
				</tr>
				
				<tr id="sezCUIINT">	
					<td class="etichetta-dato">Numero intervento CUI</td>
					<td class="valore-dato-trova">
						<input type="text" name="cuiintRic" id="cuiintRic" size="30" value="${param.cuiint}"/>
					</td>
				</tr>
				
				<c:if test="${tiprog eq '2'}" >
					<tr id="sezTIPOIN">
					<td class="etichetta-dato">Settore</td>
					<td class="valore-dato-trova">
						<select name="tipoinRic" id="tipoinRic" >
							<option value="">&nbsp;</option>
							<option value="F" >Forniture</option>
							<option value="S" >Servizi</option>
						</select>
					</td>
					</tr>
				</c:if>
				<!-- ////////////////////////////////////// -->
				<!--FEBBRAIO 2019 -->
				<tr id="sezRUP">	
					<td class="etichetta-dato">Nominativo del RUP</td>
					<td class="valore-dato-trova">
						<input type="text" name="rupRic" id="rupRic" size="73" />
					</td>
				</tr>

				<!--FEBBRAIO 2019 -->
				<!-- //... -->
				
				<tr id="sezCDI">
					<td class="etichetta-dato">Classe di importo</td>
					<td class="valore-dato-trova">
						<select name="cdiRic" id="cdiRic" >
							<option value="Sopra_soglia" selected>Sopra soglia ai fini della pubblicazione</option>
							<option value="Sotto_soglia" >Sotto soglia ai fini della pubblicazione</option>
							<option value="Tutti" >Tutti</option>
						</select>
					</td>
				</tr>		
				<!-- ////////////////////////////////////// -->

				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Trova" title="Trova"	onclick="javascript:trova();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>
				
				
				
				
				
				
			</table>
		
		
		
		
			<table id="tabLista" class="lista">
				<tr id="rowLista">
					<td colspan="2">
						<display:table name="listaFabbisogni" id="fabbisogni" class="datilista" sort="list"  >
							<display:column class="associadati" title="Opzioni<br><center><a href='javascript:selezionaTutti(document.forms[0].keys);' Title='Seleziona Tutti'> <img src='${pageContext.request.contextPath}/img/ico_check.gif' height='15' width='15' alt='Seleziona Tutti'></a>&nbsp;<a href='javascript:deselezionaTutti(document.forms[0].keys);' Title='Deseleziona Tutti'><img src='${pageContext.request.contextPath}/img/ico_uncheck.gif' height='15' width='15' alt='Deseleziona Tutti'></a></center>" style="width:50px">
								<center>
									<input type="checkbox" name="keys" id="keys" value="${fabbisogni[1].value}" />
								</center>
							</display:column>
							<display:column title="Numero progressivo dell'intervento" sortable="true" headerClass="sortable" >
								${fabbisogni[1].value}
							</display:column>
							<display:column title="Codice interno attribuito dall'amministrazione" sortable="true" headerClass="sortable" >
								${fabbisogni[2].value}
							</display:column>
							<display:column title="Descrizione intervento" sortable="true" headerClass="sortable" >
								${fabbisogni[3].value}
							</display:column>
							<display:column title="Numero intervento CUI" sortable="true" headerClass="sortable" >
								${fabbisogni[4].value}
							</display:column>
							<display:column title="Settore" sortable="true" headerClass="sortable" >
								${fabbisogni[5].value}
							</display:column>
							<display:column title="Importo" sortable="true" headerClass="sortable" >
								${fabbisogni[6].value}
							</display:column>
						</display:table>
					</td>
				</tr>
				<tr id="rowPulsanti">
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button"  class="bottone-azione" value='Importa proposte' title='Importa proposte selezionate' onclick="javascript:importaFabbisogni()">
						<INPUT type="button"  class="bottone-azione" value='Annulla' title='Annulla' onclick="javascript:window.close()">
						&nbsp;
					</td>
				</tr>
			
			</table>
			
			<table id="tabDettaglio" class="ricerca">
			
		
				<tr id="sezData">	
					<td class="etichetta-dato">Data approvazione</td>
					<td class="valore-dato">
						<span id="FABBISOGNI_DATAAPPRview" title="Data">${dataOggi}</span>
					</td>
				</tr>
				<tr id="sezEsito">	
					<td class="etichetta-dato">Esito</td>
					<td class="valore-dato">
						<span id="FABBISOGNI_ESITOAPPRview" title="Esito">Approvato</span>
					</td>
				</tr>
				
				<tr id="sezFase">	
					<td class="etichetta-dato">Fase approvazione</td>
					<td class="valore-dato">
						<span id="faseAppr" title="Fase">Inserimento in programmazione</span>
					</td>
				</tr>

				<tr id="sezNote">	
					<td class="etichetta-dato">Note</td>
					<td class="valore-dato">
						<textarea name="noteAppr" id="noteAppr" rows="4" cols="100"></textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" class="comandi-dettaglio">
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Conferma"	onclick="javascript:importaFabbisogni();">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
					</td>
				</tr>

			
			</table>
			
			<input type="hidden" id="idUtente" name="idUtente" value="${idUtente}" />
			<input type="hidden" id="nomeUtente" name="nomeUtente" value="${nomeUtente}" />
			<input type="hidden" id="contri" name="contri" value="${param.contri}" />
			<input type="hidden" id="tiprog" name="tiprog" value="${tiprog}" />
			<input type="hidden" id="anntri" name="anntri" value="${anntri}" />
			<input type="hidden" id="tipoForm" name="tipoForm" value="${tipoForm}" />
			<input type="hidden" id="fabbisogniDaImportare" name="fabbisogniDaImportare" value="${fabbisogniDaImportare}" />
		</form>
	</gene:redefineInsert>

	<gene:javaScript>
	
	
		<c:choose>
			<c:when test="${empty param.tipoForm}" >
				showObj("tabRic", true);
				showObj("tabLista", false);
				showObj("tabDettaglio", false);
			</c:when>
			<c:when test="${param.tipoForm eq '1'}" >
				showObj("tabRic", false);
				showObj("tabLista", true);
				showObj("tabDettaglio", false);
			</c:when>
			<c:when test="${param.tipoForm eq '2'}" >
				showObj("tabRic", false);
				showObj("tabLista", false);
				showObj("tabDettaglio", true);
			</c:when>
			<c:otherwise>
				showObj("tabRic", false);
				showObj("tabLista", false);
				showObj("tabDettaglio", false);
				window.opener.historyReload();
				window.close();
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${checkProt_FiltroUffintListaProposte}" >
				showObj("sezNOMEIN", false);
				showObj("sezNATGIU", false);
			</c:when>
			<c:otherwise>
				showObj("sezNOMEIN", true);
				showObj("sezNATGIU", true);
			</c:otherwise>
		</c:choose>
			
			function trova() {
				document.formListaFabbisogni.submit();
				bloccaRichiesteServer();
			}
	
	
	
		var numero = 0;
		
		
	
		function importaFabbisogni() {
			var fabbisogniDaImportare = "";
			<c:choose>
			<c:when test="${param.tipoForm eq '1'}" >
				var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
				if (numeroOggetti == 0) {
					alert("Nessun elemento selezionato nella lista");
					return;
				} else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
					fabbisogniDaImportare = document.forms[0].keys.value;
				
					document.formListaFabbisogni.fabbisogniDaImportare.value=fabbisogniDaImportare;
					document.formListaFabbisogni.submit();
		        } else {
					for (var i = 0; i < document.forms[0].keys.length; i++) {
						if (document.forms[0].keys[i].checked) {
							fabbisogniDaImportare += document.forms[0].keys[i].value + ";;";
						}
					}
					fabbisogniDaImportare = fabbisogniDaImportare.substring(0,(fabbisogniDaImportare.length - 2));
					
					document.formListaFabbisogni.fabbisogniDaImportare.value=fabbisogniDaImportare;
					document.formListaFabbisogni.submit();
				}
			</c:when>
			<c:otherwise>
					document.formListaFabbisogni.submit();
			</c:otherwise>
			</c:choose>
		}

		function continua() {
			var oper = document.listaInterventi.operazione.value;
			if (oper != 1 && oper != 2 && oper != 3) {
				alert("Selezionare una delle opzioni"); 
			} else {
				document.listaInterventi.submit();
			}
		}

		function annulla() {
			window.close();
		}
		
		function filtraLista() {
			var filtroIdProg = document.getElementById("idProgramma").value;
			var filtroAnnoIntervento = document.getElementById("annoIntervento").value;
	<c:choose>
		<c:when test="${! empty listaAnnualitaInterventiProgPrecedenti}" >
			var temp = "GetListaInterventiDaProgrammaPrecedente.do?" + csrfToken + "&codiceProgramma=${param.codiceProgramma}&numeroPopUp=1";
		</c:when>
		<c:otherwise>
			var temp = "GetListaInterventiDaProgrammaPrecedente.do?" + csrfToken + "&codiceProgramma=${param.codiceProgramma}&tipo=INR&numeroPopUp=1";
		</c:otherwise>
	</c:choose>
			if (filtroIdProg.length > 0) {
				temp = temp + "&idProg=" + filtroIdProg;
			}
			if (filtroAnnoIntervento.length > 0) {
				temp = temp + "&anno=" + filtroAnnoIntervento;
			}
			document.location.href= "${pageContext.request.contextPath}/piani/" + temp;
		}
		
		
		
		
	</gene:javaScript>
</gene:template>