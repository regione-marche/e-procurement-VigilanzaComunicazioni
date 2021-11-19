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

<c:if test='${param.updateLista eq 1}' >
	<c:set var="isNavigazioneDisabilitata" value="1" scope="request"/>
	<c:set var="isNavigazioneDisattiva" value="1" scope="request"/>
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9PERMESSI-scheda">
	<gene:setString name="titoloMaschera" value='Permessi accesso' />

	<c:set var="tempKey" value="W9GARA.CODGARA=N:${param.codgara}" />
	<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,tempKey,"W9GARA")}' scope="request"/>
		
	<c:choose>
		<c:when test='${modo ne "NUOVO"}'>
			<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,tempKey,"W9GARA")}' scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="permessoUser" value='5' scope="request" />
		</c:otherwise>
	</c:choose>
	<c:set var="modifica" value='${userIsRup eq "si" || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq "A"}' scope="request"/>

	<gene:redefineInsert name="corpo">

	<table class="dettaglio-notab">
		<tr>
			<td>
				<gene:formLista entita="W9PERMESSI" 
						pagesize="25" tableclass="datilista" sortColumn="2;3" gestisciProtezioni="true"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9PERMESSI" where="CODGARA=${param.codgara}" >
						<gene:campoLista campo="CODGARA" visibile="false" edit="${param.updateLista eq 1}" />
						<gene:campoLista campo="NUMREG" visibile="false" edit="${param.updateLista eq 1}" />
						<gene:campoLista campo="RUOLO" headerClass="sortable" />
						<gene:campoLista campo="MACROFASE" title="Fase" headerClass="sortable" />
						<gene:campoLista campo="PERMESSO" headerClass="sortable" edit="${param.updateLista eq 1}"
								gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreTabellatoNoOpzioneVuota" />
						<input type="hidden" id="CODGARA" name="codgara" value="" />
				</gene:formLista>
			</td>
		</tr>
		
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="listaEliminaSelezione" />
	
<c:choose>
	<c:when test='${modifica}' >
		<c:choose>
			<c:when test='${param.updateLista ne 1}'>
				<gene:redefineInsert name="listaNuovo"/>
				<gene:redefineInsert name="pulsanteListaInserisci" />
				
				<c:choose>
					<c:when test='${datiRiga.rowCount > 0 }' >
						<gene:redefineInsert name="addToAzioni">
							<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDA_A_LISTA")}'>
								<tr>
									<td class="vocemenulaterale">
										<a href="javascript:listaApriInModifica()" title="Modifica permessi"
											tabindex="1501">Modifica</a>
									</td>
								</tr>
							</c:if>
							
							<c:if test='${gene:checkProtFunz(pageContext,"DEL","SCHEDA_A_LISTA")}'>
								<tr>
									<td class="vocemenulaterale">
										<a href="javascript:cancellaPermessi()" title="Cancella regole"
											tabindex="1503">Cancella regole</a>
									</td>
								</tr>
							</c:if>
						</gene:redefineInsert>
		
						<gene:redefineInsert name="pulsanteListaInserisci">
							
							<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDA_A_LISTA")}'>
								<INPUT type="button" class="bottone-azione" title='Modifica permessi'
										value="Modifica" onclick="javascript:listaApriInModifica();">
							</c:if>
			
							<c:if test='${gene:checkProtFunz(pageContext,"DEL","SCHEDA_A_LISTA")}'>
								<INPUT type="button" class="bottone-azione" title='Cancella regole'
										value="Cancella regole" onclick="javascript:cancellaPermessi();">
							</c:if>						
						</gene:redefineInsert>
					</c:when>
					<c:otherwise>
		
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PERMESSI-Scheda.AttrubuisciModello")}'>
							<gene:redefineInsert name="addToAzioni">
								<tr>
									<td class="vocemenulaterale">
										<a href="javascript:attribuisciModello()" title="Attribuisci modello a fasi"
											tabindex="1502">Attribuisci modello a fasi</a>
									</td>
								</tr>
							</gene:redefineInsert>
						</c:if>
		
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9PERMESSI-Scheda.AttrubuisciModello")}'>
							<gene:redefineInsert name="pulsanteListaInserisci">
								<INPUT type="button" class="bottone-azione" title='Attribuisci modello a fasi'
									value="Attribuisci modello a fasi" onclick="javascript:attribuisciModello();">
							</gene:redefineInsert>
						</c:if>
		
					</c:otherwise>
				</c:choose>
			
			</c:when>
			<c:otherwise>

				<gene:redefineInsert name="listaNuovo">
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:listaConferma();" title="Salva"
								tabindex="1501">Salva</a>
						</td>
					</tr>
					<tr>
						<td class="vocemenulaterale">
							<a href="javascript:listaAnnullaModifica();" title="Annulla"
							tabindex="1501">Annulla</a>
						</td>
					</tr>
				</gene:redefineInsert>
				<gene:redefineInsert name="pulsanteListaInserisci">
					<INPUT type="button" class="bottone-azione" title='Modifica permessi'
						value="Salva" onclick="javascript:listaConferma();">
						
					<INPUT type="button" class="bottone-azione" title='Annulla'
						value="Annulla" onclick="javascript:listaAnnullaModifica();">
				</gene:redefineInsert>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<gene:redefineInsert name="listaNuovo"/>
		<gene:redefineInsert name="pulsanteListaInserisci"/>
	</c:otherwise>
</c:choose>
		<tr>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
		</tr>

</table>

	</gene:redefineInsert>
	
	<gene:javaScript>
		$(document).ready(function() {
			$( "#CODGARA" ).attr("value", "${param.codgara}");
			
			<c:if test='${datiRiga.rowCount eq 0}'>
				$( ".datilista > tbody > tr >td" ).text("Nessuna regola configurata. La gara non è gestita a fasi");
			</c:if>
		});
		
	<c:choose>
		<c:when test='${datiRiga.rowCount > 0}'>
			function attribuisciModello() {
				if (confirm("I permessi esistenti verranno cancellati.\nAlla gara verranno applicati i permessi di default.\n\nContinuare?")) {
					document.location.href = "${pageContext.request.contextPath}/w9/AttribuisciModello.do?" + csrfToken + "&codgara=${param.codgara}";
				}
			}
		</c:when>
		<c:otherwise>
			function attribuisciModello() {
				document.location.href = "${pageContext.request.contextPath}/w9/AttribuisciModello.do?" + csrfToken + "&codgara=${param.codgara}";
			}
		</c:otherwise>
	</c:choose>
	
	function cancellaPermessi() {
		if (confirm("I permessi esistenti verranno cancellati.\n\nContinuare?")) {
			document.location.href = "${pageContext.request.contextPath}/w9/CancellaModello.do?" + csrfToken + "&codgara=${param.codgara}";
		}
	}
	</gene:javaScript>
	
</gene:template>