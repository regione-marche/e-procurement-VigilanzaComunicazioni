<%
	/*
	 * Created on 03/08/2009
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


<c:set var="numeroStazioniAppaltanti" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.ContaStazioniAppaltantiPresentiFunction", pageContext)}'/>

<c:choose>
	<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
		<c:set var="where1" value="W9GARA.CODEIN = '${sessionScope.uffint}'" />
	</c:when>
	<c:otherwise>
		<c:set var="where1" value=" W9GARA.CODEIN = '${sessionScope.uffint}' AND W9GARA.CODGARA in (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ${sessionScope.profiloUtente.id})" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test='${param.archiviate eq "N"}'>
		<c:set var="where2" value="${where1} AND (W9GARA.SITUAZIONE <= 90 OR W9GARA.SITUAZIONE IS NULL)" />
	</c:when>
	<c:otherwise>
		<c:set var="where2" value="${where1}" />
	</c:otherwise>
</c:choose>

<c:set var="cercaGareArchiviate" scope="session" value="${param.archiviate}"/>

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9GARA-lista">
	<gene:setString name="titoloMaschera" value="Lista Gare" />
	<c:set var="tipoRicerca" value="bandi" scope="session" />
	<c:set var="eliminabili" value="false" />
	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9GARA-scheda")}' />
	
	<gene:redefineInsert name="addToAzioni">
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-lista.FunzioniAvanzate") and (gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-lista.FunzioniAvanzate.MigraGare"))}'>
			<tr id="rowFunzioniAvanzate">
				<td class="vocemenulaterale">
					<a href="javascript:visualizzaFunzAvan();" title="Funzioni avanzate" tabindex="1505">
						Funzioni avanzate &nbsp;&nbsp;&nbsp;&nbsp;<img id="imgRight" src="${pageContext.request.contextPath}/img/arrow-right.gif" ><img id="imgDown" src="${pageContext.request.contextPath}/img/arrow-down.gif" >
					</a>
				</td>
			</tr>
			<c:if test='${(gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-lista.FunzioniAvanzate.MigraGare")) && (numeroStazioniAppaltanti > 1)}'>
				<tr id="rowMigraGare">
					<td  class="vocemenulaterale" style="text-align: right;">
						<a href="javascript:migraSA();" title="Migra gare" tabindex="1505">
							Migra gare&nbsp;
						</a>
					</td>
				</tr>
			</c:if>
		</c:if>
		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.TRASFERISCI-APPALTO") and sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			<tr>
				<td  class="vocemenulaterale">
					<a href="javascript:trasferisciAppalto();" title="Trasferisci appalti a..." tabindex="1506">
						Trasferisci appalti a...&nbsp;
					</a>
				</td>
			</tr>
		</c:if>
	</gene:redefineInsert>
	
	<gene:redefineInsert name="corpo">
		
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		
		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="W9GARA" where="${where2}" pagesize="20" tableclass="datilista" sortColumn="-2"
							gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARA" >
						<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="80">
							<c:if test="${currentRow >= 0}">
								<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'" >
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9GARA-scheda")}' >
										<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
											<gene:PopUpItemResource	resource="popupmenu.tags.lista.visualizza" title="Visualizza anagrafica gara" href="chiaveRiga='${chiaveRigaJava}';listaVisualizzaCheckProv('${datiRiga.W9GARA_PROV_DATO}')"/>
										</c:if>
										<c:if test='${! gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
											<gene:PopUpItemResource	resource="popupmenu.tags.lista.visualizza" title="Visualizza anagrafica gara" />
										</c:if>
									</c:if>

									<c:set var="userIsRup" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsUserRupFunction",pageContext,chiaveRiga,"W9GARA")}' />
									<c:set var="permessoUser" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetPermessoUserFunction",pageContext,chiaveRiga,"W9GARA")}' />
									<c:set var="modifica" value='${userIsRup eq "si" || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq "A" || sessionScope.profiloUtente.abilitazioneStd eq "C"}' />
									<c:set var="cancella" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,chiaveRiga,"W9GARA")}' />

									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9GARA-scheda") && gene:checkProtFunz(pageContext, "MOD","MOD") && modifica}'>
										<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
											<gene:PopUpItemResource	resource="popupmenu.tags.lista.modifica" title="Modifica anagrafica gara" href="chiaveRiga='${chiaveRigaJava}';listaModificaCheckProv('${datiRiga.W9GARA_PROV_DATO}')"/>
										</c:if>
										<c:if test='${! gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
											<gene:PopUpItemResource	resource="popupmenu.tags.lista.modifica" title="Modifica anagrafica gara" />
										</c:if>
									</c:if>

									<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && (cancella eq "si") && (modifica)}'>
										<gene:PopUpItemResource 
												resource="popupmenu.tags.lista.elimina"
														title="Elimina anagrafica gara" />
										<c:set var="eliminabili" value="true" />
									</c:if>
									
									<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
									
									<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9GARA-scheda") && gene:checkProtFunz(pageContext, "MOD","MOD") && sessionScope.profiloUtente.abilitazioneStd ne "A" && (userIsRup eq "si" || permessoUser ge 4)}'>
										<gene:PopUpItem title="Cambia RUP" href="javascript:cambiaRUP(${datiRiga.W9GARA_CODGARA})"/>
									</c:if>
									
									<c:if test='${datiRiga.W9GARA_SITUAZIONE == 91 && !gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
										<gene:PopUpItem title="Annulla archiviazione" href="javascript:archiviaGara(${datiRiga.W9GARA_CODGARA}, 'false');" />
									</c:if>
									
									<c:if test='${datiRiga.W9GARA_SITUAZIONE != 91 && !gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
										<gene:PopUpItem title="Archivia" href="javascript:archiviaGara(${datiRiga.W9GARA_CODGARA}, 'true');" />
									</c:if>
									
								</gene:PopUp>
								<c:if test='${not empty datiRiga.W9FLUSSI_IDFLUSSO and gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
									<c:if test='${not empty datiRiga.W9GARA_ID_RICEVUTO}'>
										<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguita.png"	title="Procedura pubblicata" alt="Procedura pubblicata" />
									</c:if>
									<c:if test='${empty datiRiga.W9GARA_ID_RICEVUTO}'>
										<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguitaOR.png"	title="Procedura pubblicata Osservatorio" alt="Procedura pubblicata Osservatorio" />
									</c:if>
								</c:if>
							</c:if>
						</gene:campoLista>

						<%	// Campi veri e propri	%>
						<gene:campoLista campo="CODGARA" visibile="false"/>
						<gene:campoLista campo="CODGARA" entita="W9PERMESSI" where="W9GARA.CODGARA=W9PERMESSI.CODGARA" visibile="false" />
				<c:if test='${gene:checkProt(pageContext, "COLS.VIS.W9.W9PERMESSI.CODGARA")}'>
						<gene:campoLista width="31" title="&nbsp;" campoFittizio="true" >
							<c:choose>
								<c:when test='${datiRiga.W9PERMESSI_CODGARA ne ""}' >
									<img src="${pageContext.request.contextPath}/img/garaConModello.png"
											title="Gara con permessi di accesso differenziati" alt="Gara con permessi di accesso differenziati" />
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</gene:campoLista>
				</c:if>
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
							<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizzaCheckProv('${datiRiga.W9GARA_PROV_DATO}');" />
						</c:if>
						<c:if test='${! gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.CHECK-PROVENIENZA")}'>
							<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
						</c:if>
						<gene:campoLista campo="OGGETTO" visibile="true" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" width="280" />
						<gene:campoLista campo="TIPO_APP" headerClass="sortable" width="290" />
						<gene:campoLista campo="SITUAZIONE" headerClass="sortable" width="290" />
						<gene:campoLista campo="IMPORTO_GARA" headerClass="sortable" width="150" />
						<c:if test='${gene:checkProt(pageContext, "COLS.VIS.W9.W9GARA.SYSCON")}'>
							<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
								<gene:campoLista campo="SYSUTE" entita="USRSYS"  where="W9GARA.SYSCON=USRSYS.SYSCON" />
							</c:if>
						</c:if>

						<gene:campoLista title="CIG" headerClass="sortable" campo="LISTA_CIG" campoFittizio="true" definizione="T200;0;;;" width="30" >
							<c:set var="listaCIG" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetListaCIGFunction", pageContext, gene:getValCampo(chiaveRigaJava,"CODGARA"))}' />
							${listaCIG}
						</gene:campoLista>

						<gene:campoLista campo="PROV_DATO" visibile="false"/>
						<gene:campoLista campo="IDFLUSSO" entita="W9FLUSSI" where="W9FLUSSI.KEY01=W9GARA.CODGARA and W9FLUSSI.AREA = 2  and W9FLUSSI.CFSA = (select CFEIN from UFFINT where CODEIN ='${sessionScope.uffint}')" visibile="false" />
						<gene:campoLista campo="ID_RICEVUTO" visibile="false"/>	
					</gene:formLista>
				</td>
			</tr>

			<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
			<gene:redefineInsert name="listaEliminaSelezione" />

			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	
	<gene:javaScript>
	
		function cambiaRUP(codgara){
			openPopUpCustom("href=w9/w9gara/w9gara-cambia-rup-popup.jsp&codgara=" + codgara, "cambiaRUP", 690, 320, "yes", "yes");
		}
		
		function archiviaGara(codgara, archivia) {
  		if (archivia == 'false' || confirm("Archiviare la gara?\n Una gara può essere archiviata solo se sono state completate tutte le comunicazioni.\n Una gara archiviata sarà nascosta nella lista, e potrà essere visualizzata solo tramite la ricerca avanzata.")) {
  			var action = "${pageContext.request.contextPath}/w9/ArchiviaGara.do?" + csrfToken + "&";
  			var par = new String('codgara=' + codgara + '&archivia=' + archivia );
  			document.forms[0].action = action + par;
  			document.forms[0].metodo.value="archiviaGara";
				document.forms[0].submit();
			}
		}
		
		// Visualizzazione del dettaglio
		function listaVisualizzaCheckProv(prov_dato) {
			var action = "${pageContext.request.contextPath}/w9/SchedaANAS.do?" + csrfToken + "&PROV_DATO=" + prov_dato;
			document.forms[0].action = action;
			document.forms[0].key.value=chiaveRiga;
			document.forms[0].metodo.value="apri";
			document.forms[0].activePage.value="0";
			document.forms[0].submit();
		}
		
		// Visualizzazione del dettaglio in modifica
		function listaModificaCheckProv(prov_dato) {
			var action = "${pageContext.request.contextPath}/w9/SchedaANAS.do?" + csrfToken + "&PROV_DATO=" + prov_dato;
			document.forms[0].action = action;
			document.forms[0].key.value=chiaveRiga;
			document.forms[0].metodo.value="modifica";
			document.forms[0].activePage.value="0";
			document.forms[0].submit();
		}
		
		function migraSA() {
			var numeroOggetti = contaCheckSelezionati(document.getElementsByName("keys")); 
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
			} else {
				if (confirm("Sono stati selezionati " + numeroOggetti + " elementi.\n\nProcedere con la migrazione ad altra stazione appalatante ?")) {
					document.forms[0].action = "${pageContext.request.contextPath}/w9/MigraSA.do?" + csrfToken + "&metodo=inizializza";
					document.forms[0].submit();
				}
			}
		}
		
		function trasferisciAppalto() {
			var gare = document.getElementsByName("keys");
			var numeroOggetti = contaCheckSelezionati(gare); 
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
			} else {
				var listaGare="";
				for (i = 0; i < gare.length; i++) {
					if (gare[i].checked)
						listaGare += gare[i].value + "--";
          		}
				location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/commons/trasferisci-appalto.jsp&procedura=gare&chiavi='+listaGare;
			}
		}
		
		$(document).ready(function() {
			$('#rowMigraGare').hide();
			$('#imgDown').hide();
			
			// DA AGGIUNGERE AL CONTROLLO ALTRE SOTTOVOCI SE INSERITE NELLA JSP
			if(!($('#rowMigraGare').length > 0)){
				showObj("rowFunzioniAvanzate", false);
			}
			
		});
		
		function visualizzaFunzAvan() {
			if( isObjShow("rowMigraGare")) {
				showObj("rowMigraGare", false);
				showObj("imgDown", false);
				showObj("imgRight", true);
			} else {
				showObj("rowMigraGare", true);
				showObj("imgDown", true);
				showObj("imgRight", false);
			}
		}
		
	</gene:javaScript>
	
</gene:template>