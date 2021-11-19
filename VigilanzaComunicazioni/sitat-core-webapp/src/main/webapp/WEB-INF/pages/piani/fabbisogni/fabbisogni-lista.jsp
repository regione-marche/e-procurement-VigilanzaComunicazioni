<%/*
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- ------------------------------------------------ -->
<!-- ----- AGOSTO 2019 -- RICHIESTE ETTORE --- -- -->
<!-- rendere profilabili tutte le funzioni introdotte -->
<c:set var="checkProtRDS_ContrassegnaCompletato" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_CONTRASSEGNACOMPLETATO")}'/>
<c:set var="checkProtRDS_SottoponiAdApprFinanz" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_SOTTOPONIADAPPRFINANZ")}'/>
<c:set var="checkProtRDS_InoltraAlRdp" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_INOLTRAALRDP")}'/>
<c:set var="checkProtRDS_Annulla" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_ANNULLA")}'/>
<c:set var="checkProtRDS_ApprovaRichiediRevisione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_APPROVARICHIEDIREVISIONE")}'/>
<c:set var="checkProtRDS_RichiediRevisione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_RICHIEDIREVISIONE")}'/>
<c:set var="checkProtRDS_CambiaUtenteSA" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_CAMBIAUTENTESA")}'/>

<c:set var="checkProtRAF_ApprovaRespingi" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_APPROVARESPINGI")}'/>

<c:set var="checkProtRDP_RichiediRevisioneRespingi" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_RICHIEDIREVISIONERESPINGI")}'/>
<c:set var="checkProtRDP_InserisciInProgrammazione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_INSERISCIINPROGRAMMAZIONE")}'/>
<c:set var="checkProtRDP_SegnalaProceduraAvviata" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_SEGNALAPROCEDURAAVVIATA")}'/>
<!-------------------------------------------------- -->
<c:set var="ModificheRER" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.ModificheRER")}' />
<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>
<c:set var="statiVisibiliProfilo" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.GetFiltroStatoFabbisogniProfiloFunction", pageContext)}'/>
<!-- ------------------------------------------------ -->
<!-- ----- AGOSTO 2019 -- RICHIESTE ETTORE --- -- -->
<%-- <c:set var="filtroUffint" value=""/>  --%>
<%-- <c:if test="${! empty sessionScope.uffint && fn:contains(archiviFiltrati,'FABBISOGNI')}"> --%>
<%-- 	<c:set var="filtroUffint" value=" FABBISOGNI.CODEIN = '${sessionScope.uffint}' "/> --%>
<%-- </c:if> --%>
<c:set var="filtroUffint" value=" FABBISOGNI.CONINT IS NOT NULL "/>
<c:set var="checkProt_FiltroUffintListaProposte" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.FiltroUffintListaProposte")}'/>
<c:if test="${! empty sessionScope.uffint}">
	<c:if test='${checkProt_FiltroUffintListaProposte}'>
		<c:set var="filtroUffint" value=" FABBISOGNI.CODEIN = '${sessionScope.uffint}' "/>
	</c:if>
</c:if>
<!--- Filtro Stati Fabbisogni per profilo -->
<c:set var="filtroStatoPerProfilo" value=""/>
<c:if test="${statiVisibiliProfilo ne ''}">
	<c:set var="filtroStatoPerProfilo" value=" AND FABBISOGNI.STATO in (${statiVisibiliProfilo}) "/>
</c:if>

<c:choose>
	<c:when test='${not empty param.rbTrova}'>
		<c:set var="rbTrova" value="${param.rbTrova}" />
	</c:when>
	<c:otherwise>
		<c:set var="rbTrova" value="${input_rbTrova}" />
	</c:otherwise>
</c:choose>


<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="FABBISOGNI-lista">
	<gene:setString name="titoloMaschera" value="Lista Proposte"/>
	<c:set var="eliminabili" value="false" />
	<c:set var="visualizzaStato" value='${gene:checkProt(pageContext, "COLS.VIS.PIANI.PIATRI.STATRI")}'/>
 	<c:set var="visualizzaLink" value='true'/>
 	
<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->
 	<gene:redefineInsert name="addToAzioni">
	<c:choose>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>
<%-- 			<c:if test="${rbTrova eq 'RDS_rbTrova1' || rbTrova eq 'RicercaAvanzata'}"> --%>
<!-- 				Le funzioni compaiono come sottomenu della Funzione "Gestisci proposte" -->
				<!-- ----- AGOSTO 2019 -- RICHIESTE ETTORE --- -- -->
				<c:if test='${checkProtRDS_ContrassegnaCompletato || checkProtRDS_SottoponiAdApprFinanz || checkProtRDS_InoltraAlRdp || checkProtRDS_Annulla || checkProtRDP_RichiediRevisioneRespingi || checkProtRDS_ApprovaRichiediRevisione || checkProtRDS_RichiediRevisione || checkProtRDP_SegnalaProceduraAvviata || checkProtRDS_CambiaUtenteSA}'>
					<tr id="rowGestisciProposte_RDS">
						<td class="vocemenulaterale" >
							<a href="javascript:visualizzaGestisciProposte_RDS();" title="Gestisci proposte" tabindex="1500">
								Gestisci proposte &nbsp;&nbsp;&nbsp;&nbsp;<img id="imgRight" src="${pageContext.request.contextPath}/img/arrow-right.gif" ><img id="imgDown" src="${pageContext.request.contextPath}/img/arrow-down.gif" >
							</a>
						</td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_ContrassegnaCompletato}'>
					<tr id="rowRDS_ContrassegnaCompletato">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:eseguiFabbisogniListaSelezionati('RDS_ContrassegnaCompletato');" tabindex="1501"
						 title="Contrassegna selezionati come completati">Contrassegna selezionati come completati</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_ApprovaRichiediRevisione}'>
					<tr id="rowRDS_ApprovaRichiediRevisione">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:approvaFabbisogniListaSelezionati('RDS_ApprovaRichiediRevisione');" tabindex="1502"
						 title="Approva/Richiedi revisione">Approva/Richiedi revisione</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_RichiediRevisione}'>
					<tr id="rowRDS_RichiediRevisione">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:approvaFabbisogniListaSelezionati('RDS_RichiediRevisione');" tabindex="1502"
						 title="Richiedi revisione">Richiedi revisione</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_SottoponiAdApprFinanz}'>
					<tr id="rowRDS_SottoponiAdApprFinanz">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:eseguiFabbisogniListaSelezionati('RDS_SottoponiAdApprFinanz');" tabindex="1503"
						 title="Inoltra proposta">Inoltra proposta</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_InoltraAlRdp}'>
					<tr id="rowRDS_InoltraAlRdp">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:eseguiFabbisogniListaSelezionati('RDS_InoltraAlRdp');" tabindex="1504"
						 title="Inoltra proposta al referente della programmazione">Inoltra proposta al referente della programmazione</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDS_Annulla}'>
					<tr id="rowRDS_Annulla">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:approvaFabbisogniListaSelezionati('RDS_Annulla');" tabindex="1505"
						 title="Annulla proposta">Annulla proposta</a></td>
					</tr>
				</c:if>
				<!----- AGOSTO 2019 -- RICHIESTE ETTORE--- -->
				<c:if test='${checkProtRDP_RichiediRevisioneRespingi}'>
					<tr id="rowRDP_RichiediRevisioneRespingi">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:approvaFabbisogniListaSelezionati('RDP_RichiediRevisioneRespingi');" tabindex="1506"
						 title="Richiedi revisione/Respingi selezionati">Richiedi revisione/Respingi selezionati</a></td>
					</tr>
				</c:if>
				<c:if test='${checkProtRDP_SegnalaProceduraAvviata}'>
				<tr id="rowRDP_SegnalaProceduraAvviata">
					<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:eseguiFabbisogniListaSelezionati('RDP_SegnalaProceduraAvviata');" tabindex="1506"
					 title="Contrassegna come avviato per selezionati">Contrassegna come avviato per selezionati</a></td>
				</tr>
				</c:if>
				<c:if test='${checkProtRDS_CambiaUtenteSA}'>
					<tr id="rowRDS_CambiaUtenteSA">
						<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:cambiaUtenteSA('RDS_CambiaUtenteSA');" tabindex="1507"
						 title="Cambia utente/ufficio intestatario">Cambia utente/ufficio intestatario</a></td>
					</tr>
				</c:if>
<%-- 			</c:if>		 --%>
		</c:when>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN")}'>
			<c:if test='${checkProtRAF_ApprovaRespingi}'>
				<tr>
					<td class="vocemenulaterale" ><a 
					 href="javascript:approvaFabbisogniListaSelezionati('RAF_ApprovaRespingi');" tabindex="1503"
					 title="Approva/Respingi selezionati">Approva/Respingi selezionati</a></td>
				</tr>
			</c:if>
		</c:when>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
			<c:if test='${checkProtRDP_RichiediRevisioneRespingi || checkProtRDP_InserisciInProgrammazione}'>
				<tr id="rowGestisciProposte_RDP">
						<td class="vocemenulaterale">
							<a href="javascript:visualizzaGestisciProposte_RDP();" title="Gestisci proposte" tabindex="1500">
								Gestisci proposte &nbsp;&nbsp;&nbsp;&nbsp;<img id="imgRight" src="${pageContext.request.contextPath}/img/arrow-right.gif" ><img id="imgDown" src="${pageContext.request.contextPath}/img/arrow-down.gif" >
							</a>
						</td>
				</tr>
			</c:if>
			<c:if test='${checkProtRDP_RichiediRevisioneRespingi}'>
				<tr id="rowRDP_RichiediRevisioneRespingi">
					<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:approvaFabbisogniListaSelezionati('RDP_RichiediRevisioneRespingi');" tabindex="1504"
					 title="Richiedi revisione/Respingi selezionati">Richiedi revisione/Respingi selezionati</a></td>
				</tr>
			</c:if>
			<c:if test='${checkProtRDP_InserisciInProgrammazione}'>
				<tr id="rowRDP_InserisciInProgrammazione">
					<td class="vocemenulaterale" style="text-align: right;"><a href="javascript:inserisciInProgrammazioneSelezionati('RDP_InserisciInProgrammazione');" tabindex="1505"
					title="Inserisci in programmazione selezionati">Inserisci in programmazione selezionati</a></td>
				</tr>
			</c:if>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
 	
 	
 	
 	</gene:redefineInsert>
 	
 	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
  	<%// Creo la lista per peri %>
		<table class="lista">
			<tr>
				<td>
				
	<!-- 	//////////////////////////////////////////////	 -->
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>
		<c:choose>
			<c:when test='${sessionScope.profiloUtente.abilitazioneGare ne "A"}'>
					<c:set var="where1" value="FABBISOGNI.SYSCON = ${sessionScope.profiloUtente.id}" />
			</c:when>
		</c:choose>
	</c:if>		
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN")}'>
		<c:set var="where1" value= " NOT(FABBISOGNI.STATO = 1 OR FABBISOGNI.STATO = 2 OR FABBISOGNI.STATO = 11 OR FABBISOGNI.STATO = 12)" />
		<gene:redefineInsert name="listaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
	</c:if>				
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
<%-- 		<c:set var="where1" value= " (FABBISOGNI.STATO = 4 OR FABBISOGNI.STATO = 21 OR FABBISOGNI.STATO = 22)" /> --%>
		<c:set var="where1" value= " NOT(FABBISOGNI.STATO = 1)" />
		<gene:redefineInsert name="listaEliminaSelezione" />
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
	</c:if>
	<!-- 			//////////////////////////////////////////////	 -->
	
	<c:choose>
		<c:when test="${! empty where1 && ! empty filtroUffint}">
			<c:set var="where1" value="${where1} AND ${filtroUffint} ${filtroStatoPerProfilo}" />
		</c:when>
		<c:otherwise>
			<c:set var="where1" value= "${where1}${filtroUffint} ${filtroStatoPerProfilo}" />
		</c:otherwise>
	</c:choose>
	
  	<gene:formLista entita="FABBISOGNI" where="${where1}" pagesize="20" tableclass="datilista" sortColumn="-2" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreFABBISOGNI">
		
		<gene:campoLista title="Opzioni<center>${titoloMenu}</center>"
			width="50">
			<c:if test="${currentRow >= 0}">
				<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
					onClick="chiaveRiga='${chiaveRigaJava}'">
					<c:if
						test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.FABBISOGNI-scheda")}'>
						<gene:PopUpItemResource
							resource="popupmenu.tags.lista.visualizza"
							title="Visualizza dettaglio" />
					</c:if>
					<!--	//////////////////////////////////////////////	 -->
					<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
					<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
					<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && !(gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")) }'>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '1' || datiRiga.FABBISOGNI_STATO eq '2'}">
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
							title="Elimina" />	
						</c:if>							
					</c:if>
					<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
<%-- 					<input type="checkbox" name="keys" value="${chiaveRigaJava}--FABBISOGNI_STATO=${datiRiga.FABBISOGNI_STATO}" /> --%>
					<input name="datiRigaStato" type="hidden" value="${datiRiga.FABBISOGNI_STATO}" />
					<input name="datiRigaTipint" type="hidden" value="${datiRiga.INTTRI_TIPINT}" />
					<input name="datiRigaAilint" type="hidden" value="${datiRiga.INTTRI_AILINT}" />
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '1' || datiRiga.FABBISOGNI_STATO eq '11'}">
							<c:if test="${checkProtRDS_ContrassegnaCompletato}">
								<gene:PopUpItem title="Contrassegna come completato"
									href="javascript:eseguiFabbisognoListaPopUpItem('${chiaveRiga}','RDS_ContrassegnaCompletato');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '2'}">
							<c:if test="${checkProtRDS_ApprovaRichiediRevisione}">
								<gene:PopUpItem title="Approva/Richiedi revisione"
									href="javascript:approvaFabbisognoListaPopUpItem('${chiaveRiga}','RDS_ApprovaRichiediRevisione');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '15'}">
							<c:if test="${checkProtRDS_RichiediRevisione}">
								<gene:PopUpItem title="Richiedi revisione"
									href="javascript:approvaFabbisognoListaPopUpItem('${chiaveRiga}','RDS_RichiediRevisione');" />
							</c:if>
						</c:if>
						<c:if test="${(datiRiga.FABBISOGNI_STATO eq '1' && !ModificheRER) || (datiRiga.FABBISOGNI_STATO eq '2' && !ModificheRER) || (datiRiga.FABBISOGNI_STATO eq '11' && !ModificheRER) || datiRiga.FABBISOGNI_STATO eq '15'}">
							<c:if test="${checkProtRDS_SottoponiAdApprFinanz}">
								<gene:PopUpItem title="Inoltra proposta"
									href="javascript:eseguiFabbisognoListaPopUpItem('${chiaveRiga}','RDS_SottoponiAdApprFinanz');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '15'}">
							<c:if test="${checkProtRDS_InoltraAlRdp}">
								<gene:PopUpItem title="Inoltra proposta al referente della programmazione"
									href="javascript:eseguiFabbisognoListaPopUpItem('${chiaveRiga}','RDS_InoltraAlRdp');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '11' || datiRiga.FABBISOGNI_STATO eq '15'}">
							<c:if test="${checkProtRDS_Annulla}">
								<gene:PopUpItem title="Annulla proposta"
									href="javascript:approvaFabbisognoListaPopUpItem('${chiaveRiga}','RDS_Annulla');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '4' || datiRiga.FABBISOGNI_STATO eq '21'}">
							<c:if test="${checkProtRDP_SegnalaProceduraAvviata}">
								<gene:PopUpItem title="Contrassegna come avviato"
									href="javascript:eseguiFabbisognoListaPopUpItem('${chiaveRiga}','RDP_SegnalaProceduraAvviata');" />
							</c:if>
						</c:if>	
						
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '1' || datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '11' || datiRiga.FABBISOGNI_STATO eq '15'}">
							<c:if test="${checkProtRDS_CambiaUtenteSA}">
								<gene:PopUpItem title="Cambia utente/ufficio intestatario"
									href="javascript:cambiaUtenteSAPopUpItem('${chiaveRiga}');" />
							</c:if>
						</c:if>
						
					</c:if>
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN") && (datiRiga.FABBISOGNI_STATO eq "3")}'>
						<c:if test="${checkProtRAF_ApprovaRespingi}">
							<gene:PopUpItem title="Approva/Respingi"
								href="javascript:approvaFabbisognoListaPopUpItem('${chiaveRiga}','RAF_ApprovaRespingi');" />
						</c:if>
					</c:if>
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>					
						<!----- AGOSTO 2019 -- RICHIESTE ETTORE--- -->
						<!-- 		aggiungere stato='2' -->
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '4'}">
							<c:if test="${checkProtRDP_RichiediRevisioneRespingi}">
								<gene:PopUpItem title="Richiedi revisione/Respingi"
									href="javascript:approvaFabbisognoListaPopUpItem('${chiaveRiga}','RDP_RichiediRevisioneRespingi');" />
							</c:if>
						</c:if>
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '4' || datiRiga.FABBISOGNI_STATO eq '21'}">
							<c:if test="${checkProtRDP_InserisciInProgrammazione}">
								<gene:PopUpItem title="Inserisci in programmazione"
									href="javascript:inserisciInProgrammazioneListaPopUpItem('${datiRiga.FABBISOGNI_CONINT}','RDP_InserisciInProgrammazione','${datiRiga.INTTRI_TIPINT}','${datiRiga.INTTRI_AILINT}');" />
							</c:if>
						</c:if>	
						
					</c:if>
					<!-- 	//////////////////////////////////////////////	 -->
				</gene:PopUp>
			</c:if>
		</gene:campoLista>
		
		<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
		
		<gene:campoLista campo="CONINT" title="Codice" visibile="true"/>
		<gene:campoLista campo="CODINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" />
		<gene:campoLista campo="DESINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" href="${gene:if(visualizzaLink, link, '')}"/>
		<gene:campoLista campo="CUIINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" />
		<gene:campoLista campo="TIPINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" visibile="false"/>
		<gene:campoLista campo="TIPOIN" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" />
		<gene:campoLista campo="TOTINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" />
		<gene:campoLista campo="AILINT" entita="INTTRI" where="INTTRI.CONTRI=0 and INTTRI.CONINT=FABBISOGNI.CONINT" visibile="false"/>
		<gene:campoLista campo="STATO" visibile="true"/>
		
		<input type="hidden" id="input_rbTrova" name="input_rbTrova" value="${rbTrova}" />
		
	</gene:formLista>
				</td>					
				</tr>
					<gene:redefineInsert name="pulsanteListaInserisci" />	
					<gene:redefineInsert name="listaNuovo" />
				<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>				  
		</table>
  </gene:redefineInsert>
  
  <gene:javaScript>
  
  <!-- 	//////////////////////////////////////////////	 -->
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
	function eseguiFabbisogniListaSelezionati(funzionalita) {
		////////////////////////////////
		if(!listaSelezionatiProcedere(funzionalita)){
			return;
		}		
		///////////////////////////////
		var valoriFabbisogni = "";
		valoriFabbisogni = listaValoriSelezionati();
		if(valoriFabbisogni == ""){
			return;
		}	
		openPopUpCustom("href=piani/fabbisogni/popup-contrassegna-fabbisogno.jsp?fabbisogni=" + valoriFabbisogni +"&funzionalita="+funzionalita, "eseguiFabbisogniListaSelezionati", 650, 400, 1, 1);
	}
	

	function eseguiFabbisognoListaPopUpItem(chiave,funzionalita) {
		openPopUpCustom("href=piani/fabbisogni/popup-contrassegna-fabbisogno.jsp?fabbisogni=" + chiave +"&funzionalita="+funzionalita, "eseguiFabbisognoListaPopUpItem", 650, 400, 1, 1);
	}
	
	function cambiaUtenteSA(funzionalita) {
		////////////////////////////////
		if(!listaSelezionatiProcedere(funzionalita)){
			return;
		}		
		///////////////////////////////
		var valoriFabbisogni = "";
		valoriFabbisogni = listaValoriSelezionati();
		if(valoriFabbisogni == ""){
			return;
		}	
		openPopUpCustom("href=piani/fabbisogni/popup-cambia-utenteSA.jsp&modo=NUOVO&stazioneAppaltante=${sessionScope.uffint}&fabbisogni=" + valoriFabbisogni, "cambiaUtenteSA", 650, 400, 1, 1);
	}
	
	function cambiaUtenteSAPopUpItem(chiave) {
		openPopUpCustom("href=piani/fabbisogni/popup-cambia-utenteSA.jsp&modo=NUOVO&stazioneAppaltante=${sessionScope.uffint}&fabbisogni=" + chiave, "cambiaUtenteSA", 650, 400, 1, 1);
	}

	
	<!-- 	//////////////////////////////////////////////	 -->
	
  	function approvaFabbisogniListaSelezionati(funzionalita) {
 		if(!listaSelezionatiProcedere(funzionalita)){
			return;
		}
		//////////////////
		var valoriFabbisogni = "";
		valoriFabbisogni = listaValoriSelezionati();
		if(valoriFabbisogni == ""){
			return;
		}
		/////////////////
		location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&fabbisogni="+valoriFabbisogni+"&funzionalita="+funzionalita;
	}
	
	
	function approvaFabbisognoListaPopUpItem(chiave,funzionalita) {
		location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&fabbisogni="+chiave+"&funzionalita="+funzionalita;	
	}

	

	function inserisciInProgrammazioneSelezionati(funzionalita) {
 		if(!listaSelezionatiProcedere(funzionalita)){
			return;
		}
		//////////////////////////////////////
		var valoreTipint = 0;
		var valoreTipintOld = 0;
		var valoreAilint = 0;
		var valoreAilintMin = 0;
		var valoreAilintMax = 0;
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (!(numeroOggetti == 1 && (document.forms[0].keys[0]==undefined))) {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoreTipint = document.forms[0].datiRigaTipint[i].value;
					if(valoreTipintOld!=0 && valoreTipintOld!=valoreTipint){
						alert('La funzione non è eseguibile in quanto sono selezionate sia proposte di intervento che di acquisto');
						return;
					}
					valoreTipintOld = valoreTipint;
					////////////////////////////////
					valoreAilint = document.forms[0].datiRigaAilint[i].value;
					if(valoreAilintMin==0){
						valoreAilintMin = valoreAilint;
					}	
					if(valoreAilintMax==0){
						valoreAilintMax = valoreAilint;
					}
					if(valoreAilint < valoreAilintMin){
						valoreAilintMin = valoreAilint;
					}	
					if(valoreAilint > valoreAilintMax){
						valoreAilintMax = valoreAilint;
					}									
				}
			}
		}else {
			valoreTipint = document.forms[0].datiRigaTipint.value;
			valoreAilintMin = document.forms[0].datiRigaAilint.value;
			valoreAilintMax = document.forms[0].datiRigaAilint.value;
		}
		//////////////////////////////////////
		//////////////////////////////////////
		if(valoreTipint == 1){
			if((valoreAilintMax - valoreAilintMin) > 2 ){
				alert('Attenzione: gli interventi selezionati devono ricadere in un triennio!');
				return;
			}
		} else {
			if((valoreAilintMax - valoreAilintMin) > 1 ){
				alert('Attenzione: gli interventi selezionati devono ricadere in un biennio!');
				return;
			}
		}
		var valoriFabbisogni = "";
		valoriFabbisogni = listaValoriSelezionati();
		if(valoriFabbisogni == ""){
			return;
		}
		valoriFabbisogni = valoriFabbisogni.replace(/FABBISOGNI.CONINT=N:/gi, "");
		//location.href = "${pageContext.request.contextPath}/ApriPagina.do?href=piani/piatri/piatri-lista-inserisciInProgrammazione.jsp&fabbisogni="+valoriFabbisogni+"&funzionalita="+funzionalita+"&tipint="+valoreTipint;
		openPopUpCustom("href=piani/piatri/piatri-popup-inserisciInProgrammazione.jsp&fabbisogni="+valoriFabbisogni+"&funzionalita="+funzionalita+"&tipint="+valoreTipint+"&ailintMin="+valoreAilintMin+"&ailintMax="+valoreAilintMax, "inserisciInProgrammazioneSelezionati",1000,500,1,1);
	}

	function inserisciInProgrammazioneListaPopUpItem(conint,funzionalita,tipint,ailint) {
  			openPopUpCustom("href=piani/piatri/piatri-popup-inserisciInProgrammazione.jsp&fabbisogni="+conint+"&funzionalita="+funzionalita+"&tipint="+tipint+"&ailintMin="+ailint+"&ailintMax="+ailint, "inserisciInProgrammazioneSelezionati",1000,500,1,1);
	}  
  	<!-- 	//////////////////////////////////////////////	 -->
  
  	function listaSelezionatiProcedere(funzionalita){
		var valoreStato = 0;
		var domanda = true;
		var statiOK = "";
		switch(funzionalita) {
				  case "Elimina":
				    statiOK = ";1;2;" ;
				    break;
				  case "RDS_ContrassegnaCompletato":
				    statiOK = ";1;11;" ;
				    break;
				  case "RDS_ApprovaRichiediRevisione":
				    statiOK = ";2;" ;
				    break;
				  case "RDS_RichiediRevisione":
				    statiOK = ";15;" ;
				    break;
<c:if test="${ModificheRER}">				    
				  case "RDS_SottoponiAdApprFinanz":
				    statiOK = ";15;" ;
				    break;
</c:if>			
<c:if test="${!ModificheRER}">				    
				  case "RDS_SottoponiAdApprFinanz":
				    statiOK = ";1;2;11;15;" ;
				    break;
</c:if>		    
				  case "RDS_InoltraAlRdp":
				    statiOK = ";2;15;" ;
				    break;	
				  case "RDS_Annulla":
				    statiOK = ";2;11;15;" ;
				    break;
			     case "RAF_ApprovaRespingi":
				    statiOK = ";3;" ;
				    break;
				 case "RDP_RichiediRevisioneRespingi":
					 	//----- AGOSTO 2019 -- RICHIESTE ETTORE--- --
				    //statiOK = ";4;21;" ;
				    statiOK = ";4;" ;
				    break;	
			     case "RDP_InserisciInProgrammazione":
				    statiOK = ";4;21;" ;
				    break;
			    case "RDP_SegnalaProceduraAvviata":
				    statiOK = ";2;11;15;21;" ;
				    break;
				case "RDS_CambiaUtenteSA":
					statiOK = ";1;2;11;15;";
					break;
		}
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
			valoreStato = document.forms[0].datiRigaStato.value;
			valoreStatoStr = valoreStato.toString();
			if(statiOK.indexOf(";"+valoreStatoStr+";") < 0){
				alert('Attenzione: funzionalità non attivabile sulla proposta in tale stato!');
				//document.forms[0].keys.checked = false;
				return false;
			}
		}
		for (var i = 0; i < document.forms[0].keys.length; i++) {
			if (document.forms[0].keys[i].checked) {
				valoreStato = document.forms[0].datiRigaStato[i].value;
				valoreStatoStr = valoreStato.toString();
				if(statiOK.indexOf(";"+valoreStatoStr+";") < 0){
					if(domanda == true){
						if (confirm("Attenzione: la funzionalità avrà effetto solo sulle richieste per le quali è attuabile, in base al proprio stato. Procedere comunque?")) {
								document.forms[0].keys[i].checked = false;
								domanda = false;
						}else{
							return false;
						}
					}else{
						document.forms[0].keys[i].checked = false;
					}
				}						
			}
		}
		return true;
	}
	
	
	function listaValoriSelezionati(){
		var valoriFabbisogni = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
			//return valoriFabbisogni;
		} else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
            valoriFabbisogni = document.forms[0].keys.value;
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriFabbisogni += document.forms[0].keys[i].value + ";;";
				}
			}
			valoriFabbisogni = valoriFabbisogni.substring(0,(valoriFabbisogni.length - 2));
		}
		return valoriFabbisogni;
	}
	
	//////////////////////////////////////////////////////////////
	//ridefinita funzione ancestor listaEliminaSelezione():
	var listaEliminaSelezioneDefault = listaEliminaSelezione;	
	// Elimina selezione
	function listaEliminaSelezioneCustom() {
		////////////////////////////////
		if(!listaSelezionatiProcedere("Elimina")){
			return;
		}
		///////////////////////////////
		listaEliminaSelezioneDefault();
	}	
	var listaEliminaSelezione = listaEliminaSelezioneCustom;
	//////////////////////////////////////////////////////////////
  
  	$(document).ready(function() {
			$('#rowRDS_ContrassegnaCompletato').hide();
			$('#rowRDS_ApprovaRichiediRevisione').hide();
			$('#rowRDS_RichiediRevisione').hide();
			$('#rowRDS_SottoponiAdApprFinanz').hide();
			$('#rowRDS_InoltraAlRdp').hide();
			$('#rowRDS_Annulla').hide();
			$('#rowRDP_RichiediRevisioneRespingi').hide();
			$('#rowRDP_InserisciInProgrammazione').hide();
			$('#rowRDP_SegnalaProceduraAvviata').hide();
			$('#rowRDS_CambiaUtenteSA').hide();
			$('#imgDown').hide();
		});
  
  	function visualizzaGestisciProposte_RDS() {
  		//----- AGOSTO 2019 -- RICHIESTE ETTORE--- --
			if( isObjShow("rowRDS_ContrassegnaCompletato") || isObjShow("rowRDS_SottoponiAdApprFinanz") || isObjShow("rowRDS_InoltraAlRdp") || isObjShow("rowRDS_Annulla") || isObjShow("rowRDP_RichiediRevisioneRespingi") || isObjShow("rowRDS_ApprovaRichiediRevisione") || isObjShow("rowRDS_RichiediRevisione") || isObjShow("rowRDS_CambiaUtenteSA")) {
				showObj("rowRDS_ContrassegnaCompletato", false);
				showObj("rowRDS_ApprovaRichiediRevisione", false);
				showObj("rowRDS_RichiediRevisione", false);
				showObj("rowRDS_SottoponiAdApprFinanz", false);
				showObj("rowRDS_InoltraAlRdp", false);
				showObj("rowRDS_Annulla", false);
				showObj("rowRDP_RichiediRevisioneRespingi", false);
				showObj("rowRDS_CambiaUtenteSA", false);
				showObj("imgDown", false);
				showObj("imgRight", true);
			} else {
				showObj("rowRDS_ContrassegnaCompletato", true);
				showObj("rowRDS_ApprovaRichiediRevisione", true);
				showObj("rowRDS_RichiediRevisione", true);
				showObj("rowRDS_SottoponiAdApprFinanz", true);
				showObj("rowRDS_InoltraAlRdp", true);
				showObj("rowRDS_Annulla", true);
				showObj("rowRDP_RichiediRevisioneRespingi", true);
				showObj("rowRDS_CambiaUtenteSA", true);
				showObj("imgDown", true);
				showObj("imgRight", false);
			}
		}
  
  	function visualizzaGestisciProposte_RDP() {
			if( isObjShow("rowRDP_RichiediRevisioneRespingi") || isObjShow("rowRDP_InserisciInProgrammazione") || isObjShow("rowRDP_SegnalaProceduraAvviata") ) {
				showObj("rowRDP_RichiediRevisioneRespingi", false);
				showObj("rowRDP_InserisciInProgrammazione", false);
				showObj("rowRDP_SegnalaProceduraAvviata", false);
				showObj("imgDown", false);
				showObj("imgRight", true);
			} else {
				showObj("rowRDP_RichiediRevisioneRespingi", true);
				showObj("rowRDP_InserisciInProgrammazione", true);
				showObj("rowRDP_SegnalaProceduraAvviata", true);
				showObj("imgDown", true);
				showObj("imgRight", false);
			}
		}
  
  </gene:javaScript>
</gene:template>