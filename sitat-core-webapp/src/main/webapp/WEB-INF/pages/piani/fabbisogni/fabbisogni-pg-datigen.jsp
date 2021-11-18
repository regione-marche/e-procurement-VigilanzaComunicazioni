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

<!-- ------------------------------------------------ -->
<!-- ----- AGOSTO 2019 -- RICHIESTE ETTORE --- -- -->
<!-- rendere profilabili tutte le funzioni introdotte -->
<c:set var="checkProtRDS_ContrassegnaCompletato" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_CONTRASSEGNACOMPLETATO")}'/>
<c:set var="checkProtRDS_SottoponiAdApprFinanz" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_SOTTOPONIADAPPRFINANZ")}'/>
<c:set var="checkProtRDS_InoltraAlRdp" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_INOLTRAALRDP")}'/>
<c:set var="checkProtRDS_Annulla" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_ANNULLA")}'/>
<c:set var="checkProtRDS_ApprovaRichiediRevisione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_APPROVARICHIEDIREVISIONE")}'/>
<c:set var="checkProtRDS_RichiediRevisione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDS_RICHIEDIREVISIONE")}'/>
<c:set var="checkProtRAF_ApprovaRespingi" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_APPROVARESPINGI")}'/>

<c:set var="checkProtRDP_RichiediRevisioneRespingi" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_RICHIEDIREVISIONERESPINGI")}'/>
<c:set var="checkProtRDP_InserisciInProgrammazione" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_INSERISCIINPROGRAMMAZIONE")}'/>
<c:set var="checkProtRDP_SegnalaProceduraAvviata" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RDP_SEGNALAPROCEDURAAVVIATA")}'/>
<!-- ------------------------------------------------ -->

<c:set var="ModificheRER" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.ModificheRER")}' />

<c:choose>
	<c:when test='${modo eq "NUOVO"}'>
		${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNextConintFunction", pageContext, key)}
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")};INTTRI.CONINT=N:${count}'
			scope="request" />
			</c:when>
	<c:otherwise>
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")};INTTRI.CONINT=N:${gene:getValCampo(key,"CONINT")}'
			scope="request" />
	</c:otherwise>
</c:choose>

<gene:formScheda entita="INTTRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreFABBISOGNI"
	plugin="it.eldasoft.sil.pt.tags.gestori.plugin.GestorePLUGINFABBISOGNI">

	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.cpvvp.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
	</gene:redefineInsert>
	
	<c:set var="isModifica" value="${modoAperturaScheda ne 'VISUALIZZA'}" />
	
	<c:set var="isModifcabileAnnoeQuadro" value="${datiRiga.FABBISOGNI_QE_SL ne '1'}" />
	
	<!-- 	febbraio 2019 -->
	<c:set var="isQuadroEconomicoModificabile" value='${! gene:checkProtObj( pageContext,"PAGE.VIS","PIANI.FABBISOGNI-scheda.FABBCAPSPESA")}' scope="request" />

	<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
		<c:set var="contri" value='${gene:getValCampo(key, "INTTRI.CONTRI")}' />
		<c:set var="conint" value='${gene:getValCampo(key, "INTTRI.CONINT")}' />
		${gene:callFunction3("it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownAILINTFunction", pageContext, contri, conint)}
	</c:if>

	<gene:gruppoCampi idProtezioni="DJ">
			<gene:campoScheda campo="CONINT" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" visibile="false"  />
			<gene:campoScheda campo="SYSCON" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" defaultValue='${sessionScope.profiloUtente.id}' visibile="false"  />
			<gene:campoScheda campo="CODEIN" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" defaultValue='${sessionScope.uffint}' visibile="false"  />
	</gene:gruppoCampi>
	<gene:gruppoCampi idProtezioni="DATIGEN">
			<gene:campoScheda>
				<td colspan="2"><b>Dati generali</b></td> 
			</gene:campoScheda>
				<gene:campoScheda campo="CONTRI" visibile="false" defaultValue='0' />
		<gene:campoScheda campo="CONINT" visibile="false" />
		<gene:campoScheda campo="TIPINT" visibile="false" defaultValue='${tipint}'/>
		<gene:campoScheda campo="QE_SL" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" visibile="false"/>
		<c:choose>
			<c:when test='${tipint eq 1}'>
				<gene:campoScheda campo="TIPOIN"  defaultValue="L"  modificabile="false"  />
			</c:when>
			<c:otherwise>
			<gene:campoScheda campo="TIPOIN"  gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreTipoinAnnuale" />
			</c:otherwise>
		</c:choose>
		<gene:campoScheda campo="CUIINT" modificabile="false"/>
		<gene:campoScheda campo="CODINT" />
		<gene:campoScheda campo="DESINT" />
		<gene:campoScheda campo="STATO"  entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" defaultValue='1' modificabile="false"   />
		<gene:campoScheda campo="MESEIN"/>
<!-- 		----------------------------- -->
		<gene:campoScheda campo="AILINT" title="Anno avvio procedura contrattuale" modificabile="${isModifcabileAnnoeQuadro}" >
		<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
			<gene:addValue value="" descr="" />
			<c:if test='${!empty listaValoriAILINT}'>
				<c:choose>
					<c:when test='${tipint eq 1}'>
						<c:set var="end" value="3" />
					</c:when>
					<c:otherwise>
						<c:set var="end" value="2" />
					</c:otherwise>
				</c:choose>
				<c:forEach items="${listaValoriAILINT}" var="valoriAILINT" begin="0" end="${end}">
					<gene:addValue value="${valoriAILINT}"
						descr="${valoriAILINT}" />
				</c:forEach>
			</c:if>
		</c:if>
		</gene:campoScheda>
		<gene:fnJavaScriptScheda funzione='aggiornaTextAnno("#INTTRI_AILINT#")' elencocampi='INTTRI_AILINT' esegui="false"/>
<!-- 		-----------------------------		 -->
		<gene:campoScheda campo="FLAG_CUP" defaultValue="2" />
		<gene:campoScheda campo="CUPPRG"/>
		<gene:fnJavaScriptScheda funzione='gestioneCUP()' elencocampi="INTTRI_FLAG_CUP" esegui="true"/>
		<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false" 
				lista="gene/commons/istat-comuni-lista-popup.jsp" scheda=""
				schedaPopUp="" 
				campi="TABSCHE.TABCOD3;TABSCHE.TABDESC">
				<gene:campoScheda campo="COMINT" title="Codice ISTAT del Comune" visibile="false" />
				<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true" 
					definizione="T120"	title="Codice ISTAT del Comune" 
					value="${comune}" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />		
		</gene:archivio>
		
		<gene:campoScheda campo="ACQALTINT" />
		<gene:campoScheda campo="CUICOLL" >
			<gene:checkCampoScheda funzione="controlloCUICOLL()" messaggio="Inserire in codice CUI di un intervento o di un altro acquisto del programma" obbligatorio="true" onsubmit="false" />
		</gene:campoScheda>
		
		<gene:campoScheda campo="NUTS" href="#" modificabile="false" speciale="true" >
			<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
		</gene:campoScheda>
		
		<gene:campoScheda campo="CODCPV" visibile="${tipint eq 2}" href="#" speciale="true" >
			<gene:popupCampo titolo="Dettaglio CPV" href="#" />
		</gene:campoScheda>

		<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}" campoFittizio="true" definizione="T150" visibile="${tipint eq 2}" />
		
		<c:if test='${tipint ne 1}'>
			<gene:campoScheda campo="QUANTIT" />
			<gene:campoScheda campo="UNIMIS" />
		</c:if>
		<gene:campoScheda campo="PRGINT"/>
		<gene:archivio titolo="TECNICI"
				lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.PIANI.INTTRI.CODRUP"),"gene/tecni/tecni-lista-popup.jsp","")}'
				scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
				schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
				campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="INTTRI_CODRUP" >
			<gene:campoScheda campo="CODRUP" defaultValue="${idTecnico}" visibile="false" />
			<gene:campoScheda campo="NOMTEC" campoFittizio="true" title="RUP"
				value="${nomeTecnico}" definizione="T61;0;;;" />
		</gene:archivio>
		<gene:campoScheda campo="DIRGEN"/>
		<gene:campoScheda campo="STRUOP"/>
		<gene:campoScheda campo="RESPUF"/>
		<gene:campoScheda campo="LOTFUNZ" />
		<gene:campoScheda campo="LAVCOMPL" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />
		<gene:campoScheda campo="DURCONT" visibile="${datiRiga.INTTRI_TIPINT eq '2'}" />
		<gene:campoScheda campo="CONTESS" visibile="${datiRiga.INTTRI_TIPINT eq '2'}" />
		<gene:campoScheda campo="SEZINT" visibile="${datiRiga.INTTRI_TIPINT ne '2'}"  />
		<gene:campoScheda campo="INTERV" visibile="${datiRiga.INTTRI_TIPINT ne '2'}"  />
		<gene:campoScheda campo="SCAMUT" visibile="${datiRiga.INTTRI_TIPINT ne '2'}"  />
		
		<gene:gruppoCampi visibile="${datiRiga.INTTRI_TIPINT eq '2' && ModificheRER}" >
			<gene:campoScheda campo="SOGGBUD" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" />
			<gene:fnJavaScriptScheda funzione='modifySOGGBUD("#FABBISOGNI_SOGGBUD#")' elencocampi="FABBISOGNI_SOGGBUD" esegui="true" />
			<gene:campoScheda campo="TIPOSTUDIO" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT"  />
			<gene:campoScheda campo="TIPOCONV" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT"   />
			<gene:campoScheda campo="TIPORAPPR" entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT"   />
		</gene:gruppoCampi>
		
	</gene:gruppoCampi>

	<gene:gruppoCampi idProtezioni="DATIELANN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati elenco annuale</b></td> 
		</gene:campoScheda>
		<gene:campoScheda campo="FIINTR" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />
		<gene:campoScheda campo="URCINT" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />
		<gene:campoScheda campo="APCINT" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />
		<gene:campoScheda campo="STAPRO" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" />
	</gene:gruppoCampi>

		<c:if test="${tipint eq '1'}" >
			<jsp:include page="/WEB-INF/pages/piani/inttri/immobili_da_trasferire_norma.jsp" >	
				<jsp:param name="keyInttri" value="${keyINTTRI}" />
			</jsp:include>
		</c:if>

			<c:choose>
				<c:when test='${tipint eq 1 || ModificheRER}'>
					<c:set var="colspanGriglia" value="11" />
				</c:when>
				<c:when test='${tipint eq 2}'>
					<c:set var="colspanGriglia" value="9" />
				</c:when>
			</c:choose>


					<gene:campoScheda addTr="false">
						<tr>
						<td colspan="2">
						<table class="griglia">
							<tr>
								<td colspan="${colspanGriglia}" style="BORDER-RIGHT: #A0AABA 1px solid;"><b><center>Quadro delle risorse</center></b></td>
							</tr>
							<tr>
								<td rowspan="2" class="valore-dato"><center><b>Tipologie Risorse</b></center></td>
								<td colspan="${colspanGriglia-1}" class="valore-dato" style="BORDER-RIGHT: #A0AABA 1px solid;"><center>Stima dei costi</center></td>
							</tr>
							<tr>
								<td id="scf_1a" colspan="2" class="valore-dato"><center>Primo anno<br>${gene:if(empty datiRiga.INTTRI_AILINT,null,datiRiga.INTTRI_AILINT)}</center></td>
								<td id="scf_2a" colspan="2" class="valore-dato"><center>Secondo anno<br>${gene:if(empty datiRiga.INTTRI_AILINT,null,datiRiga.INTTRI_AILINT + 1)}</center></td>
								<td id="scf_3a" class="valore-dato" colspan="2"><center>Terzo anno<br>${gene:if(empty datiRiga.INTTRI_AILINT,null,datiRiga.INTTRI_AILINT + 2)}</center></td>
								<td colspan="2" class="valore-dato"><center>Annualit&agrave; successive</center></td>
								<td colspan="2" class="valore-dato"><center>Totale</center></td>
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da entrate aventi destinazione vincolata per legge</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="DV1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV9TRI" addTr="false" hideTitle="true"  modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="DVNTRI" campo="DVNTRI" campoFittizio="true" title="Risorse derivanti da entrate aventi destinazione vincolata per legge" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da entrate acquisite mediante contrazione di mutuo</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="MU1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="MUNTRI" campo="MUNTRI" campoFittizio="true" title="Risorse derivanti da entrate acquisite mediante contrazione di mutuo" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse acquisite mediante apporti di capitale privato</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="PR1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro}" />
								<gene:campoScheda campo="PR2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro}" />
								<gene:campoScheda campo="PR3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro}" />
								<gene:campoScheda campo="PR9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro}" />
								<gene:campoScheda campo="ICPINT" modificabile="false" addTr="false" hideTitle="true" >
									<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#INTTRI_PR1TRI#","#INTTRI_PR2TRI#","#INTTRI_PR3TRI#","#INTTRI_PR9TRI#")))'
									elencocampi="INTTRI_PR1TRI;INTTRI_PR2TRI;INTTRI_PR3TRI;INTTRI_PR9TRI" />
								</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Stanziamenti di bilancio</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="BI1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="BI2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="BI3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="BI9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="BINTRI" campo="BINTRI" campoFittizio="true" title="Stanziamenti di bilancio" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Finanziamenti art. 3 DL 310/1990</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="AP1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AP2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AP3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AP9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="APNTRI" campo="APNTRI" campoFittizio="true" title="Finanziamenti" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da trasferimento immobili</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="IM1TRI" addTr="false" hideTitle="true"  modificabile="${ datiRiga.INTTRI_TIPINT eq '2' && isModifcabileAnnoeQuadro }"/>
								<gene:campoScheda campo="IM2TRI" addTr="false" hideTitle="true"  modificabile="${ datiRiga.INTTRI_TIPINT eq '2' && isModifcabileAnnoeQuadro }"/>
								<gene:campoScheda campo="IM3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true"  modificabile="${ datiRiga.INTTRI_TIPINT eq '2' && isModifcabileAnnoeQuadro }"/>
								<gene:campoScheda campo="IM9TRI" addTr="false" hideTitle="true"  modificabile="${ datiRiga.INTTRI_TIPINT eq '2' && isModifcabileAnnoeQuadro }"/>
								<gene:campoScheda id="IMNTRI" campo="IMNTRI" campoFittizio="true" title="Risorse derivanti da trasferimento immobili" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" >
									<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#INTTRI_IM1TRI#","#INTTRI_IM2TRI#","#INTTRI_IM3TRI#","#INTTRI_IM9TRI#")))'
									elencocampi="INTTRI_IM1TRI;INTTRI_IM2TRI;INTTRI_IM3TRI;INTTRI_IM9TRI" />
								</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Altra tipologia</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="AL1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL3TRI" addTr="false" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="ALNTRI" campo="ALNTRI" campoFittizio="true" title="Altra tipologia" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							
							<tr>
							
								</gene:campoScheda>
									<gene:campoScheda campo="DI1INT" modificabile="false" visibile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI1TRI#","#INTTRI_DV1TRI#","#INTTRI_IM1TRI#","#INTTRI_MU1TRI#","#INTTRI_AL1TRI#","#INTTRI_AP1TRI#")))'
										elencocampi="INTTRI_BI1TRI;INTTRI_DV1TRI;INTTRI_IM1TRI;INTTRI_MU1TRI;INTTRI_AL1TRI;INTTRI_AP1TRI" />
									</gene:campoScheda>

									<gene:campoScheda campo="DI2INT" modificabile="false" visibile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI2TRI#","#INTTRI_DV2TRI#","#INTTRI_IM2TRI#","#INTTRI_MU2TRI#","#INTTRI_AL2TRI#","#INTTRI_AP2TRI#")))'
										elencocampi="INTTRI_BI2TRI;INTTRI_DV2TRI;INTTRI_IM2TRI;INTTRI_MU2TRI;INTTRI_AL2TRI;INTTRI_AP2TRI" />
									</gene:campoScheda>

									<gene:campoScheda campo="DI3INT" modificabile="false" visibile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda 
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI3TRI#","#INTTRI_DV3TRI#","#INTTRI_IM3TRI#","#INTTRI_MU3TRI#","#INTTRI_AL3TRI#","#INTTRI_AP3TRI#")))'
										elencocampi="INTTRI_BI3TRI;INTTRI_DV3TRI;INTTRI_IM3TRI;INTTRI_MU3TRI;INTTRI_AL3TRI;INTTRI_AP3TRI" />
									</gene:campoScheda>

									<gene:campoScheda campo="DI9INT" modificabile="false" visibile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda 
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI9TRI#","#INTTRI_DV9TRI#","#INTTRI_IM9TRI#","#INTTRI_MU9TRI#","#INTTRI_AL9TRI#","#INTTRI_AP9TRI#")))'
										elencocampi="INTTRI_BI9TRI;INTTRI_DV9TRI;INTTRI_IM9TRI;INTTRI_MU9TRI;INTTRI_AL9TRI;INTTRI_AP9TRI" />
									</gene:campoScheda>
									<gene:campoScheda campo="DITINT" modificabile="false" visibile="false" addTr="false" hideTitle="true">
										<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_DI1INT#","#INTTRI_DI2INT#","#INTTRI_DI3INT#","#INTTRI_DI9INT#")))'
										elencocampi="INTTRI_DI1INT;INTTRI_DI2INT;INTTRI_DI3INT;INTTRI_DI9INT" />
									</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Importo complessivo</b></td>
								</gene:campoScheda>
															
								<gene:campoScheda campo="INTRI_DF1TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 1° anno" 
										modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_DF1TRI",toMoney(sommaCampi(new Array("#INTTRI_DI1INT#","#INTTRI_PR1TRI#"))))' elencocampi="INTTRI_DI1INT;INTTRI_PR1TRI" esegui="true"/>
								
								<gene:campoScheda campo="INTRI_DF2TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 2° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_DF2TRI",toMoney(sommaCampi(new Array("#INTTRI_DI2INT#","#INTTRI_PR2TRI#"))))' elencocampi="INTTRI_DI2INT;INTTRI_PR2TRI" esegui="true"/>
								
								<gene:campoScheda campo="INTRI_DF3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_DF3TRI",toMoney(sommaCampi(new Array("#INTTRI_DI3INT#","#INTTRI_PR3TRI#"))))' elencocampi="INTTRI_DI3INT;INTTRI_PR3TRI" esegui="true"/>
																
								<gene:campoScheda campo="INTRI_DF9TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; annualit&agrave; successive"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_DF9TRI",toMoney(sommaCampi(new Array("#INTTRI_DI9INT#","#INTTRI_PR9TRI#"))))' elencocampi="INTTRI_DI9INT;INTTRI_PR9TRI" esegui="true"/>

								<gene:campoScheda campo="DFTRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria" 
									modificabile="false" addTr="false" hideTitle="true"/>
								<gene:fnJavaScriptScheda funzione='setValue("DFTRI",toMoney(sommaCampi(new Array("#INTRI_DF1TRI#","#INTRI_DF2TRI#","#INTRI_DF3TRI#","#INTRI_DF9TRI#"))))' elencocampi="INTRI_DF1TRI;INTRI_DF2TRI;INTRI_DF3TRI;INTRI_DF9TRI" esegui="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Spese gi&agrave; sostenute</b></td>
								</gene:campoScheda>															
								<gene:campoScheda campo="GIASOS1" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
								<gene:campoScheda campo="GIASOS2" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
								<gene:campoScheda campo="GIASOS3" campoFittizio="true" definizione="F24.5;0;;;" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="GIASOS9" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
								<gene:campoScheda campo="SPESESOST" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Totale</b></td>
								</gene:campoScheda>
															
								<gene:campoScheda campo="TOT1" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOT2" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOT3" campoFittizio="true" definizione="F24.5;0;;;" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOT9" campoFittizio="true" definizione="F24.5;0;;;" title="Totale disponibilit&agrave; annualit&agrave; successive" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOTINT" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:fnJavaScriptScheda funzione='setValue("INTTRI_TOTINT",toMoney(sommaCampi(new Array("#INTTRI_DITINT#","#INTTRI_ICPINT#","#INTTRI_SPESESOST#"))));calcoloImpNettoIvaQR();' elencocampi="INTTRI_DITINT;INTTRI_ICPINT;INTTRI_SPESESOST" esegui="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr id = "di_cui_iva">
								<td class="etichetta-dato"><b>Di cui IVA</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="IV1TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="IV2TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
<%-- 								<gene:campoScheda campo="IV3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.INTTRI_TIPINT eq '1'}" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" /> --%>
								<gene:campoScheda campo="IV3TRI" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="IV9TRI" addTr="false" hideTitle="true" modificabile="${isModifcabileAnnoeQuadro && isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="IVNTRI" campo="IVNTRI" campoFittizio="true" title ="Di cui IVA" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr id = "imp_netto_iva">
								<td class="etichetta-dato"><b>Importo al netto di IVA</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="INTRI_SI1TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 1° anno" 
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI1TRI",toMoney(sommaCampi(new Array("#INTTRI_DI1INT#","#INTTRI_PR1TRI#",-"#INTTRI_IV1TRI#"))))' elencocampi="INTTRI_DI1INT;INTTRI_PR1TRI;INTTRI_IV1TRI" esegui="true"/>
								
								<gene:campoScheda campo="INTRI_SI2TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 2° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI2TRI",toMoney(sommaCampi(new Array("#INTTRI_DI2INT#","#INTTRI_PR2TRI#",-"#INTTRI_IV2TRI#"))))' elencocampi="INTTRI_DI2INT;INTTRI_PR2TRI;INTTRI_IV2TRI" esegui="true"/>

<%-- 								<gene:campoScheda campo="INTRI_SI3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" title="Totale disponibilit&agrave; finanziaria 3° anno"   --%>
<!-- 									 addTr="false" hideTitle="true"> -->
<%-- 								</gene:campoScheda> --%>
								<gene:campoScheda campo="INTRI_SI3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.INTTRI_TIPINT eq '1' || ModificheRER}" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI3TRI",toMoney(sommaCampi(new Array("#INTTRI_DI3INT#","#INTTRI_PR3TRI#",-"#INTTRI_IV3TRI#"))))' elencocampi="INTTRI_DI3INT;INTTRI_PR3TRI;INTTRI_IV3TRI" esegui="true"/>
																
								<gene:campoScheda campo="INTRI_SI9TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; annualit&agrave; successive"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI9TRI",toMoney(sommaCampi(new Array("#INTTRI_DI9INT#","#INTTRI_PR9TRI#",-"#INTTRI_IV9TRI#"))))' elencocampi="INTTRI_DI9INT;INTTRI_PR9TRI;INTTRI_IV9TRI" esegui="true"/>
								
								<gene:campoScheda id="SINTRI" campo="SINTRI" campoFittizio="true" title ="Importo al netto di IVA" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
						</table>
						</td>
						</tr>
					</gene:campoScheda>

	
			<gene:campoScheda campo="TCPINT" />
			<gene:campoScheda campo="RG1TRI" visibile="${datiRiga.INTTRI_TIPINT eq '2'}"  />
			<gene:campoScheda campo="IMPRFS" visibile="${datiRiga.INTTRI_TIPINT eq '2'}"  />
			<gene:campoScheda campo="IMPALT" visibile="${datiRiga.INTTRI_TIPINT eq '2'}" />
			<gene:campoScheda campo="COPFIN" defaultValue="1" />
			
			<gene:gruppoCampi idProtezioni="ACQVERDI">
				<gene:campoScheda id="TITOLOACQVERDI">
					<td colspan="2"><b>Acquisti verdi</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="ACQVERDI" defaultValue="1" />
				<gene:campoScheda campo="NORRIF" />
				<gene:campoScheda campo="AVOGGETT" />
					
				<gene:campoScheda campo="AVCPV" speciale="true" href="#" >
					<gene:popupCampo titolo="Dettaglio CPV" href="#" />
				</gene:campoScheda>
				<gene:campoScheda campo="AVCPVDESC" title="Descrizione CPV" value="${avcpvdescr}" campoFittizio="true" definizione="T150" />
					
				<gene:campoScheda campo="AVIMPNET" />
				<gene:campoScheda campo="AVIVA" />
				<gene:campoScheda campo="AVIMPTOT" modificabile="false" />
				
				<gene:fnJavaScriptScheda funzione='gestioneAcquistiVerdi()' elencocampi="INTTRI_ACQVERDI" esegui="true"/>
				<gene:fnJavaScriptScheda funzione='calcoloAVIMPTOT()' elencocampi="INTTRI_AVIMPNET;INTTRI_AVIVA" esegui="false"/>
			</gene:gruppoCampi>
	
			<gene:gruppoCampi idProtezioni="MATRIC">
				<gene:campoScheda id="TITOLOMATRIC">
					<td colspan="2"><b>Materiali riciclati</b></td>
				</gene:campoScheda>
	
				<gene:campoScheda campo="MATRIC" defaultValue="1" />
				<gene:campoScheda campo="MROGGETT" />
				<gene:campoScheda campo="MRCPV" speciale="true" href="#" >
					<gene:popupCampo titolo="Dettaglio CPV" href="#" />
				</gene:campoScheda>
				<gene:campoScheda campo="MRCPVDESC"  title="Descrizione CPV" value="${mrcpvdescr}" campoFittizio="true" definizione="T150" />
					
				<gene:campoScheda campo="MRIMPNET" />
				<gene:campoScheda campo="MRIVA" />
				<gene:campoScheda campo="MRIMPTOT" modificabile="false" />
				
				<gene:fnJavaScriptScheda funzione='gestioneMaterialiRiciclati()' elencocampi="INTTRI_MATRIC" esegui="true"/>
				<gene:fnJavaScriptScheda funzione='calcoloMRIMPTOT()' elencocampi="INTTRI_MRIMPNET;INTTRI_MRIVA" esegui="false"/>
			</gene:gruppoCampi>
				
			<gene:gruppoCampi idProtezioni="MODAFF">
				<gene:campoScheda>
					<td colspan="2"><b>Affidamento</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="PROAFF" />
				<gene:campoScheda campo="DELEGA" />
				<gene:fnJavaScriptScheda funzione='gestioneDelega()' elencocampi="INTTRI_DELEGA" esegui="true"/>
				<c:choose>
					<c:when test="${datiRiga.INTTRI_TIPINT eq 2}">
						<gene:campoScheda campo="CODAUSA" title="Codice AUSA Centrale di Committenza o Soggetto Aggregatore" />
						<gene:campoScheda campo="SOGAGG" title="Denominazione Centrale di Committenza o Soggetto Aggregatore" />
					</c:when>
					<c:otherwise>
						<gene:campoScheda campo="CODAUSA" />
						<gene:campoScheda campo="SOGAGG" />
					</c:otherwise>
				</c:choose>
				<gene:campoScheda campo="DATAAVVPRO"  entita="FABBISOGNI" where="INTTRI.CONTRI=0 AND INTTRI.CONINT=FABBISOGNI.CONINT" visibile="${datiRiga.FABBISOGNI_STATO eq 22}"/>
			</gene:gruppoCampi>

			<gene:gruppoCampi idProtezioni="ALTDAT">
				<gene:campoScheda>
					<td colspan="2"><b>Altri dati</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="VARIATO" visibile="false" />
				<c:choose>
					<c:when test='${tipint eq 1}'>
						<gene:campoScheda campo="VARIATO2" title="Intervento variato a seguito di modifica programma" campoFittizio="true" definizione="A100;0;PT011"
						value="${datiRiga.INTTRI_VARIATO}" visibile="${datiRiga.FABBISOGNI_STATO ne '1'}"/>
					</c:when>
					<c:when test='${tipint eq 2}'>
						<gene:campoScheda campo="VARIATO2" title="Acquisto variato a seguito di modifica programma" campoFittizio="true" definizione="A100;0;PT010"
						value="${datiRiga.INTTRI_VARIATO}" visibile="${datiRiga.FABBISOGNI_STATO ne '1'}"/>
					</c:when>
				</c:choose>
				
				<gene:campoScheda campo="INTNOTE" />
			</gene:gruppoCampi>


	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>

	<!-- Le schede devono essere modificabili solo se stato in 1, 2, 11, 12 -->
	<c:if test="${!(stato eq '1' || stato eq '2' || stato eq '11' || stato eq '12')}">
   		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:redefineInsert name="addToAzioni">

	<!-- 	//////////////////////////////////////////////	 -->
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
	<c:choose>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>		
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '1' || datiRiga.FABBISOGNI_STATO eq '11'}">
	    				<c:if test="${checkProtRDS_ContrassegnaCompletato}">
			      		<td class="vocemenulaterale">
										<a href="javascript:eseguiFabbisognoScheda('${key}','RDS_ContrassegnaCompletato');" title="Contrassegna come completato" tabindex="1510">
											Contrassegna come completato
										</a>
					  		</td>
				  		</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '2'}">
	    				<c:if test="${checkProtRDS_ApprovaRichiediRevisione}">
			      		<td class="vocemenulaterale">
										<a href="javascript:approvaFabbisognoScheda('${key}','RDS_ApprovaRichiediRevisione');" title="Approva/Richiedi revisione" tabindex="1511">
											Approva/Richiedi revisione
										</a>
					  		</td>
				  		</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '15'}">
	    				<c:if test="${checkProtRDS_RichiediRevisione}">
			      		<td class="vocemenulaterale">
										<a href="javascript:approvaFabbisognoScheda('${key}','RDS_RichiediRevisione');" title="Richiedi revisione" tabindex="1511">
											Richiedi revisione
										</a>
					  		</td>
				  		</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${(datiRiga.FABBISOGNI_STATO eq '1' && !ModificheRER) || (datiRiga.FABBISOGNI_STATO eq '2' && !ModificheRER) || (datiRiga.FABBISOGNI_STATO eq '11' && !ModificheRER) || datiRiga.FABBISOGNI_STATO eq '15'}">
	    				<c:if test="${checkProtRDS_SottoponiAdApprFinanz}">
		      			<td class="vocemenulaterale">
									<a href="javascript:eseguiFabbisognoScheda('${key}','RDS_SottoponiAdApprFinanz');" title="Inoltra proposta" tabindex="1520">
										Inoltra proposta
									</a>
				  			</td>
		  				</c:if>
		  			</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '15'}">
	    				<c:if test="${checkProtRDS_InoltraAlRdp}">
		      			<td class="vocemenulaterale">
									<a href="javascript:eseguiFabbisognoScheda('${key}','RDS_InoltraAlRdp');" title="Inoltra proposta al referente della programmazione" tabindex="1521">
										Inoltra proposta al referente della programmazione
									</a>
				  			</td>
			  			</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '11' || datiRiga.FABBISOGNI_STATO eq '15'}">
	    				<c:if test="${checkProtRDS_Annulla}">
		      			<td class="vocemenulaterale">
										<a href="javascript:approvaFabbisognoScheda('${key}','RDS_Annulla');" title="Annulla proposta" tabindex="1522">
											Annulla proposta
										</a>
					  		</td>
			  			</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '2' || datiRiga.FABBISOGNI_STATO eq '11' || datiRiga.FABBISOGNI_STATO eq '15' || datiRiga.FABBISOGNI_STATO eq '21'}">
	    				<c:if test="${checkProtRDP_SegnalaProceduraAvviata}">
		      			<td class="vocemenulaterale">
									<a href="javascript:eseguiFabbisognoScheda('${key}','RDP_SegnalaProceduraAvviata');" title="Contrassegna come avviato" tabindex="1533">
										Contrassegna come avviato
									</a>
				  			</td>
			  			</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		</c:when>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN")}'>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '3'}">
	    				<c:if test="${checkProtRAF_ApprovaRespingi}">
		      			<td class="vocemenulaterale">
									<a href="javascript:approvaFabbisognoScheda('${key}','RAF_ApprovaRespingi');" title="Approva/Respingi" tabindex="1530">
									Approva/Respingi
								</a>
				  		</td>
			  		</c:if>
		  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		</c:when>
		<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<!----- AGOSTO 2019 -- RICHIESTE ETTORE--- -->
	    			<!-- 		aggiungere stato='2' -->
						<c:if test="${datiRiga.FABBISOGNI_STATO eq '4'}">
	    				<c:if test="${checkProtRDP_RichiediRevisioneRespingi}">
		      			<td class="vocemenulaterale">
									<a href="javascript:approvaFabbisognoScheda('${key}','RDP_RichiediRevisioneRespingi');" title="Richiedi revisione/Respingi" tabindex="1531">
										Richiedi revisione/Respingi
									</a>
				  			</td>
			  			</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	    			<c:if test="${datiRiga.FABBISOGNI_STATO eq '4' || datiRiga.FABBISOGNI_STATO eq '21'}">
	    				<c:if test="${checkProtRDP_InserisciInProgrammazione}">
		      			<td class="vocemenulaterale">
									<a href="javascript:inserisciInProgrammazioneScheda('${datiRiga.INTTRI_CONINT}','RDP_InserisciInProgrammazione','${datiRiga.INTTRI_TIPINT}','${datiRiga.INTTRI_AILINT}');" title="Inserisci in programmazione" tabindex="1532">
										Inserisci in programmazione
									</a>
				  			</td>
			  			</c:if>
			  		</c:if>
				</c:when>
			</c:choose>
		</tr>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>





</gene:redefineInsert>

	<gene:javaScript>
			
		$(document).ready(function() {
			function checkTipCapPriv()	{
				pr1tri = toVal(getValue('INTTRI_PR1TRI'));
				pr2tri = toVal(getValue('INTTRI_PR2TRI'));
				pr3tri = toVal(getValue('INTTRI_PR3TRI'));
				pr9tri = toVal(getValue('INTTRI_PR9TRI'));
				if((pr1tri+pr2tri+pr3tri+pr9tri)>0){
					$("#rowINTTRI_TCPINT").show();
				}
				else{
					$("#rowINTTRI_TCPINT").hide();
				}
			}
			
			checkTipCapPriv();
			
			$('#INTTRI_PR1TRI').on("change",function() {
				checkTipCapPriv();
			});
			$('#INTTRI_PR2TRI').on("change",function() {
				checkTipCapPriv();
			});
			$('#INTTRI_PR3TRI').on("change",function() {
				checkTipCapPriv();
			});
			$('#INTTRI_PR9TRI').on("change",function() {
				checkTipCapPriv();
			});

			
			$("#rowMODCUIINT").hide();
			
			tipint = toVal(getValue('INTTRI_TIPINT'));
			acqAltInt = toVal(getValue('INTTRI_ACQALTINT'));			
			if (tipint == 2)	{
				$("#rowINTTRI_ACQALTINT").show();
				if (acqAltInt == 2) {
					$("#rowINTTRI_CUICOLL").show();
				} else {
					$("#rowINTTRI_CUICOLL").hide();
					setValue("INTTRI_CUICOLL", "");
				}
			} else {
				$("#rowINTTRI_ACQALTINT").hide();
				$("#rowINTTRI_CUICOLL").hide();
				setValue("INTTRI_ACQALTINT", "");
				setValue("INTTRI_CUICOLL", "");
			}
			
			$('#INTTRI_ACQALTINT').on("change",function() {
				acqAltInt = toVal(getValue('INTTRI_ACQALTINT'));
				if (acqAltInt == 2) {
					$("#rowINTTRI_CUICOLL").show();
				} else {
					$("#rowINTTRI_CUICOLL").hide();
					setValue("INTTRI_CUICOLL", "");
				}
			});
			

		<c:if test="${modo ne 'VISUALIZZA'}" >
		
			stato = getValue('FABBISOGNI_STATO')
			if(stato==2){
				alert('ATTENZIONE: la proposta è già stata confermata.Se si apportano modifiche sarà rimesso in stato "in compilazione"');
			}
		
			
		</c:if>

		});
			
			
		function aggiornaTextAnno(ailint){
			var primoAnno = "";
			var secondoAnno = "";
			var terzoAnno = "";
			if (ailint != "") {
				primoAnno = parseInt(ailint);
				secondoAnno = parseInt(ailint) + 1;
				terzoAnno = parseInt(ailint) + 2;
			}
			$('#scf_1a').html('<center>Primo anno<br>' +  primoAnno + '</center>');
			$('#scf_2a').html('<center>Secondo anno<br>' +  secondoAnno + '</center>');
			$('#scf_3a').html('<center>Terzo anno<br>' +  terzoAnno + '</center>');			
		}	
			
			
		function controlloCUICOLL() {
			var acqaltint = getValue("INTTRI_ACQALTINT");
			var cuiint =  getValue("INTTRI_CUIINT");
			var cuicoll = getValue("INTTRI_CUICOLL");
			var result = true;
			
			if ("2" == acqaltint) {
				if (cuiint != "") {
					if (cuicoll == "") {
						result = false;
					} else if (cuiint == cuicoll) {
						result = false;
					}
				} else {
					if (cuicoll == "") {
						result = false;
					}
				}
			}
			return result;
		}
			
		function modifySOGGBUD(valore){
			var vis=true;
			if(valore!="1"){
				vis=false;
				setValue("FABBISOGNI_TIPOSTUDIO", "");
				setValue("FABBISOGNI_TIPOCONV", "");
				setValue("FABBISOGNI_TIPORAPPR", "");
			}
			showObj("rowFABBISOGNI_TIPOSTUDIO",vis);
			showObj("rowFABBISOGNI_TIPOCONV",vis);
			showObj("rowFABBISOGNI_TIPORAPPR",vis);
		}
			
			
		function sommaCampi(valori)	{
		  var i, ret=0;
		  for (i=0;i < valori.length;i++) {
			if (valori[i] != "") {
				ret += eval(valori[i]);
			}
		  }
		  return eval(ret).toFixed(2);
		}

		function modifyFLAG_CUP(valore) {
			var vis = (valore == 1);
			if (vis) {
			  setValue("INTTRI_CUPPRG","");
			}
			showObj("rowINTTRI_CUPPRG",!vis);
		}
		
		function modifyANNINT(valore) {
			var vis = (valore == 1);
			//showObj("rowINTTRI_DELEGA",vis);
			showObj("rowINTTRI_STAPRO",vis);
			showObj("rowINTTRI_FIINTR",vis);
			showObj("rowINTTRI_APCINT",vis);
			showObj("rowINTTRI_URCINT",vis);
			showObj("rowINTTRI_AILINT",vis);
			showObj("rowINTTRI_TILINT",vis);
			showObj("rowINTTRI_AFLINT",vis);
			showObj("rowINTTRI_TFLINT",vis);
			if (!vis) {
				//setValue("INTTRI_DELEGA","");
				setValue("INTTRI_STAPRO","");
				setValue("INTTRI_FIINTR","");
				setValue("INTTRI_APCINT","");
				setValue("INTTRI_URCINT","");
				setValue("INTTRI_AILINT","");
				setValue("INTTRI_TILINT","");
				setValue("INTTRI_AFLINT","");
				setValue("INTTRI_TFLINT","");
			}
		}

		function gestioneAcquistiVerdi() {
			var acqverdi = getValue("INTTRI_ACQVERDI");
			var visualizza = ("3" == acqverdi);
			var visualizza1 = ("2" == acqverdi);
			showObj("rowINTTRI_NORRIF", visualizza | visualizza1);
			showObj("rowINTTRI_AVOGGETT", visualizza);
			showObj("rowINTTRI_AVCPV", visualizza);
			showObj("rowAVCPVDESC", visualizza);
			showObj("rowINTTRI_AVIMPNET", visualizza);
			showObj("rowINTTRI_AVIVA", visualizza);
			showObj("rowINTTRI_AVIMPTOT", visualizza);
			if (!visualizza) {
				setValue("INTTRI_AVOGGETT","");
				setValue("INTTRI_AVCPV","");
				setValue("AVCPVDESC","");
				setValue("INTTRI_AVIMPNET","");
				setValue("INTTRI_AVIVA","");
				setValue("INTTRI_AVIMPTOT","");
			}
			if (!(visualizza | visualizza1)) {
				setValue("INTTRI_NORRIF","");
			}
		}
		
		function gestioneDelega() {
			var delega = getValue("INTTRI_DELEGA");
			var visualizza = ("1" == delega);
			showObj("rowINTTRI_CODAUSA", visualizza);
			showObj("rowINTTRI_SOGAGG", visualizza);
			if (! visualizza) {
				setValue("INTTRI_CODAUSA","");
				setValue("INTTRI_SOGAGG", "");
			}
		}


		function gestioneCUP() {
			var flag_cup = getValue("INTTRI_FLAG_CUP");
			var visualizza = ("1" != flag_cup);
			showObj("rowINTTRI_CUPPRG", visualizza);
			if (! visualizza) {
				setValue("INTTRI_CUPPRG","");
			}
		}


		function gestioneMaterialiRiciclati() {
			var acqverdi = getValue("INTTRI_MATRIC");
			var visualizza = ("3" == acqverdi);
			showObj("rowINTTRI_MROGGETT", visualizza);
			showObj("rowINTTRI_MRCPV", visualizza);
			showObj("rowMRCPVDESC", visualizza);
			showObj("rowINTTRI_MRIMPNET", visualizza);
			showObj("rowINTTRI_MRIVA", visualizza);
			showObj("rowINTTRI_MRIMPTOT", visualizza);
			if (!visualizza) {
				setValue("INTTRI_MROGGETT","");
				setValue("INTTRI_MRCPV","");
				setValue("MRCPVDESC","");
				setValue("INTTRI_MRIMPNET","");
				setValue("INTTRI_MRIVA","");
				setValue("INTTRI_MRIMPTOT","");
			}
		}

		function calcoloAVIMPTOT() {
			var avImpNet = 0;
			if (getValue("INTTRI_AVIMPNET") != "") {
				avImpNet = getValue("INTTRI_AVIMPNET");
			} 
			var avIva = 0;
			if (getValue("INTTRI_AVIVA") != "") {
				avIva = getValue("INTTRI_AVIVA");
			}
			var impTot = formatNumber(parseFloat(avImpNet) + parseFloat(avIva), 15.2);
			setValue("INTTRI_AVIMPTOT", impTot);
		}
		
		function calcoloMRIMPTOT() {
			var mrImpNet = 0;
			if (getValue("INTTRI_MRIMPNET") != "") {
				mrImpNet = getValue("INTTRI_MRIMPNET");
			} 
			var mrIva = 0;
			if (getValue("INTTRI_MRIVA") != "") {
				mrIva = getValue("INTTRI_MRIVA");
			}
			var impTot = formatNumber(parseFloat(mrImpNet) + parseFloat(mrIva), 15.2);
			setValue("INTTRI_MRIMPTOT", impTot);
		}

		function formCPV(modifica) {
			// Eseguo l'apertura della maschera con il dettaglio delle categoria dell'intervento
	<c:choose>
		<c:when test='${tipint eq 1}'>
			openPopUpCustom("href=commons/descriz-codice-cpv.jsp"
				+ "&key=" + document.forms[0].key.value 
					+ "&keyParent=" + document.forms[0].keyParent.value 
					+ "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")
					+ "&campo=INTTRI_CODCPV&campoRange=&campoDescr=CPVDESC&campoEtichetta=previsto_allegato" 
					+ "&valore="+ (getValue("INTTRI_CODCPV") == "" ? "45000000-7" : getValue("INTTRI_CODCPV"))
					+ "&valoreDescr="+ (getValue("CPVDESC") == "" ? "Lavori di costruzione" : getValue("CPVDESC")), "formCPV", 700, 300, 1, 1);
		</c:when>
		<c:otherwise>
			openPopUpCustom("href=commons/descriz-codice-cpv.jsp&key=" 
					+ document.forms[0].key.value + "&keyParent=" 
					+ document.forms[0].keyParent.value 
					+ "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")
					+ "&campo=INTTRI_CODCPV&campoRange=&campoDescr=CPVDESC&campoEtichetta=previsto_allegato"
					+ "&valore="+ (getValue("INTTRI_CODCPV") == "" ? "45000000-7" : getValue("INTTRI_CODCPV"))
					+ "&valoreDescr="+ (getValue("CPVDESC") == "" ? "Lavori di costruzione" : getValue("CPVDESC")), "formCPV", 700, 300, 1, 1);
		</c:otherwise>
	</c:choose>
		}
		
		function formCPV1(modifica, campoCPV, campoDescCPV) {
			// Eseguo l'apertura della maschera con il dettaglio delle categoria dell'intervento
	<c:choose>
		<c:when test='${datiRiga.INTTRI_TIPINT eq 1}'>
			openPopUpCustom("href=commons/descriz-codice-cpv.jsp"
					+ "&key=" + document.forms[0].key.value 
					+ "&keyParent=" + document.forms[0].keyParent.value 
					+ "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA")
					+ "&campo=" + campoCPV 
					+ "&campoRange=&campoDescr=" + campoDescCPV 
					+ "&campoEtichetta=previsto_allegato&valore=" + (getValue(campoCPV) == "" ? "45000000-7" : getValue(campoCPV)) 
					+ "&valoreDescr="+ (getValue(campoDescCPV) == "" ? "Lavori di costruzione" : getValue(campoDescCPV)), "formCPV", 700, 300, 1, 1);
		</c:when>
		<c:otherwise>
			openPopUpCustom("href=commons/descriz-codice-cpv.jsp"
					+ "&key=" + document.forms[0].key.value 
					+ "&keyParent=" + document.forms[0].keyParent.value 
					+ "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA")
					+ "&campo=" + campoCPV 
					+ "&campoRange=&campoDescr=" + campoDescCPV
					+ "&campoEtichetta=previsto_allegato&valore=" + (getValue(campoCPV) == "" ? "45000000-7" : getValue(campoCPV))
					+ "&valoreDescr="+ (getValue(campoDescCPV) == "" ? "Lavori di costruzione" : getValue(campoDescCPV)), "formCPV", 700, 300, 1, 1);
		</c:otherwise>
	</c:choose>
		}
		
		function formCUIIMM(modifica, campo1, campo2) {				
			openPopUpCustom("href=piani/inttri/dettaglio-codice-cuiimm.jsp&key=" + document.forms[0].key.value + 
				"&keyParent=" + document.forms[0].keyParent.value + "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA") + 
				"&campo1="+campo1+"&cui="+ getValue(campo1)+"&campo2="+campo2+"&contri="+ getValue(campo2), "formCUIIMM", 700, 300, 1, 1);
		}

		function richiestaListaCUP() {
			openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=INTTRI_CUPPRG&codiceCUP=" + getValue("INTTRI_CUPPRG"), "listaCUP", 900, 550, 1, 1);
		}
		
		$('[id^="IMMTRAI_VA1IMM_"]').on("change",
			function() {
				calcoloImportiTotaliImmobili(1)
			}
		);
		
		$('[id^="IMMTRAI_VA2IMM_"]').on("change",
			function() {
				calcoloImportiTotaliImmobili(2)
			}
		);
		$('[id^="IMMTRAI_VA3IMM_"]').on("change",
			function() {
				calcoloImportiTotaliImmobili(3)
			}
		);
		$('[id^="IMMTRAI_VA9IMM_"]').on("change",
			function() {
				calcoloImportiTotaliImmobili(9)
			}
		);
		
		function calcoloTotImmobiliQR() {
				var imp_tot = 0;

				if ($('#INTTRI_IM1TRI').val() != "") imp_tot = imp_tot + parseFloat($('#INTTRI_IM1TRI').val());

				if ($('#INTTRI_IM2TRI').val() != "") imp_tot = imp_tot + parseFloat($('#INTTRI_IM2TRI').val());

				if ($('#INTTRI_IM3TRI').val() != "") imp_tot = imp_tot + parseFloat($('#INTTRI_IM3TRI').val());

				if ($('#INTTRI_IM9TRI').val() != "") imp_tot = imp_tot + parseFloat($('#INTTRI_IM9TRI').val());

				if (imp_tot >= 0) {
					valimp = toMoney(imp_tot);

					setValue('IMNTRIedit',valimp);
				}
		}
		
		
		function calcoloImportiTotaliImmobili(tipo) {
			var imp_somma = 0;
			if (tipo == 1) {
				var c_riga_imp = $('[id^="rowIMMTRAI_VA1IMM_"]:visible');
			}
			if (tipo == 2) {
				var c_riga_imp = $('[id^="rowIMMTRAI_VA2IMM_"]:visible');
			}
			if (tipo == 3) {
				var c_riga_imp = $('[id^="rowIMMTRAI_VA3IMM_"]:visible');
			}
			if (tipo == 9) {
				var c_riga_imp = $('[id^="rowIMMTRAI_VA9IMM_"]:visible');
			}
			
			c_riga_imp.each(function(i) {
				var contatore_imp = $(this).attr('id').substring(18);
				if (tipo == 1) {
					if ($('#IMMTRAI_VA1IMM_' + contatore_imp).val() != "") imp_somma = imp_somma + parseFloat($('#IMMTRAI_VA1IMM_' + contatore_imp).val());
				}
				if (tipo == 2) {
					if ($('#IMMTRAI_VA2IMM_' + contatore_imp).val() != "") imp_somma = imp_somma + parseFloat($('#IMMTRAI_VA2IMM_' + contatore_imp).val());
				}
				if (tipo == 3) {
					if ($('#IMMTRAI_VA3IMM_' + contatore_imp).val() != "") imp_somma = imp_somma + parseFloat($('#IMMTRAI_VA3IMM_' + contatore_imp).val());
				}
				if (tipo == 9) {
					if ($('#IMMTRAI_VA9IMM_' + contatore_imp).val() != "") imp_somma = imp_somma + parseFloat($('#IMMTRAI_VA9IMM_' + contatore_imp).val());
				}
			});
			
			if (imp_somma >= 0) {
				if (tipo == 1) {
					$('#INTTRI_IM1TRIedit').val(formatNumber(imp_somma,18.2));
					setValue("INTTRI_IM1TRI",formatNumber(imp_somma,18.2));
				}
				if (tipo == 2) {
					$('#INTTRI_IM2TRIedit').val(formatNumber(imp_somma,18.2));
					setValue("INTTRI_IM2TRI",formatNumber(imp_somma,18.2));
				}
				if (tipo == 3) {
					$('#INTTRI_IM3TRIedit').val(formatNumber(imp_somma,18.2));
					setValue("INTTRI_IM3TRI",formatNumber(imp_somma,18.2));
				}
				if (tipo == 9) {
					$('#INTTRI_IM9TRIedit').val(formatNumber(imp_somma,18.2));
					setValue("INTTRI_IM9TRI",formatNumber(imp_somma,18.2));
				}
			}
			calcoloTotImmobiliQR();
		}
		
		$('[id^="INTTRI_DV"]').on("change",
			function() {
				calcoloImportiTotaliQR("DV")
			}
		);
		
		$('[id^="INTTRI_MU"]').on("change",
			function() {
				calcoloImportiTotaliQR("MU")
			}
		);
		$('[id^="INTTRI_PR"]').on("change",
			function() {
				calcoloImportiTotaliQR("PR")
			}
		);

		$('[id^="INTTRI_BI"]').on("change",
			function() {
				calcoloImportiTotaliQR("BI")
			}
		);

		$('[id^="INTTRI_AP"]').on("change",
			function() {
				calcoloImportiTotaliQR("AP")
			}
		);

		$('[id^="INTTRI_AL"]').on("change",
			function() {
				calcoloImportiTotaliQR("AL")
			}
		);

		$('[id^="INTTRI_IV"]').on("change",
			function() {
				calcoloImportiTotaliQR("IV")
			}
		);

		function calcoloImportiTotaliQR(radix) {
			var imp_somma = 0;
			if ($('#INTTRI_' + radix + '1TRI').val() != null && $('#INTTRI_' + radix + '1TRI').val() != "") {
				imp_somma = imp_somma + parseFloat($('#INTTRI_' + radix + '1TRI').val());
			}
			if ($('#INTTRI_' + radix + '2TRI').val() != null && $('#INTTRI_' + radix + '2TRI').val() != "") {
				imp_somma = imp_somma + parseFloat($('#INTTRI_' + radix + '2TRI').val());
			}
			if ($('#INTTRI_' + radix + '3TRI').val() != null && $('#INTTRI_' + radix + '3TRI').val() != "") {
				imp_somma = imp_somma + parseFloat($('#INTTRI_' + radix + '3TRI').val());
			}
			if ($('#INTTRI_' + radix + '9TRI').val() != null && $('#INTTRI_' + radix + '9TRI').val() != "") {
				imp_somma = imp_somma + parseFloat($('#INTTRI_' + radix + '9TRI').val());
			}
			if (imp_somma >= 0) {
				valimp = toMoney(imp_somma);
				setValue(radix + 'NTRI',valimp);
				calcoloImpNettoIvaQR();
			}
		}
	
		function calcoloImpNettoIvaQR() {
			var imp_somma = 0;
			var imp_diff = 0;
			if ($('#INTTRI_TOTINT').val() != null && $('#INTTRI_TOTINT').val() != "") {
				imp_somma = imp_somma + parseFloat($('#INTTRI_TOTINT').val());
			}
			if ($('#INTTRI_IV1TRI').val() != null && $('#INTTRI_IV1TRI').val() != "") {
				imp_diff = imp_diff + parseFloat($('#INTTRI_IV1TRI').val());
			}
			if ($('#INTTRI_IV2TRI').val() != null && $('#INTTRI_IV2TRI').val() != "") {
				imp_diff = imp_diff + parseFloat($('#INTTRI_IV2TRI').val());
			}
			if ($('#INTTRI_IV3TRI').val() != null && $('#INTTRI_IV3TRI').val() != "") {
				imp_diff = imp_diff + parseFloat($('#INTTRI_IV3TRI').val());
			}
			if ($('#INTTRI_IV9TRI').val() != null && $('#INTTRI_IV9TRI').val() != "") {
				imp_diff = imp_diff + parseFloat($('#INTTRI_IV9TRI').val());
			}
			if ((imp_somma - imp_diff) >= 0) {
				imp_somma = imp_somma - imp_diff;
			}
			if (imp_somma >= 0) {
				valimp = toMoney(imp_somma);
				setValue('SINTRI',valimp);
			}
		}
		
		calcoloImportiTotaliQR("DV");
		calcoloImportiTotaliQR("MU");
		calcoloImportiTotaliQR("PR");
		calcoloImportiTotaliQR("BI");
		calcoloImportiTotaliQR("AP");
		calcoloImportiTotaliQR("IM");
		calcoloImportiTotaliQR("AL");
		calcoloImportiTotaliQR("DF");
		<c:if test='${tipint eq 2}'>
			calcoloImportiTotaliQR("IV");
			calcoloImpNettoIvaQR();
		</c:if>
		
		calcoloTotImmobiliQR();

		<c:choose>
			<c:when test='${tipint eq 1}'>
				$('.importo').css("width","100");
				$('.importoNoEdit').css("width","100");
			</c:when>
			<c:when test='${tipint eq 2}'>
				$('.importo').css("width","130");
				$('.importoNoEdit').css("width","130");
			</c:when>
		</c:choose>
	
	<c:choose>
		<c:when test='${gene:checkProt(pageContext, "COLS.VIS.PIANI.INTTRI.IV1TRI")}'>
			$('#di_cui_iva').show();
			$('#imp_netto_iva').show();
		</c:when>
		<c:otherwise>
			$('#di_cui_iva').hide();
			$('#imp_netto_iva').hide();
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test='${tipint eq 2 and not ModificheRER}'>
			$('#scf_3a').hide();
		</c:when>
		<c:otherwise>
			$('#scf_3a').show();
		</c:otherwise>
	</c:choose>

		$('.importo').css("width","100");
		$('.importoNoEdit').css("width","100");
		

	<c:if test='${modo ne "VISUALIZZA"}' >
		
		$(function() {
			$("#INTTRI_CUICOLL").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeCUICOLL = false;
					var str = $("#INTTRI_CUICOLL").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=CUIINT&contri=${datiRiga.INTTRI_CONTRI}&anno=${datiRiga.INTTRI_ANNRIF}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value + ' - ' + item[1].value,
										value: item[0].value,
										valueCUICOLL: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_CUICOLL").val(ui.item.valueCUICOLL);
					changeCUICOLL = true;
				}
			});
		});	
		
      $(function() {
			$("#INTTRI_DIRGEN").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeDIRGEN = false;
					var str = $("#INTTRI_DIRGEN").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=DIRGEN&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value,
										value: item[0].value,
										valueDIRGEN: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_DIRGEN").val(ui.item.valueDIRGEN);
					changeDIRGEN = true;
				}
			});
		});
				
		$(function() {
			$("#INTTRI_STRUOP").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeSTRUOP = false;
					var str = $("#INTTRI_STRUOP").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=STRUOP&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value,
										value: item[0].value,
										valueSTRUOP: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_STRUOP").val(ui.item.valueSTRUOP);
					changeSTRUOP = true;
				}
			});
		});
				
		$(function() {
			$("#INTTRI_REFERE").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeREFERE = false;
					var str = $("#INTTRI_REFERE").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=REFERE&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value,
										value: item[0].value,
										valueREFERE: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_REFERE").val(ui.item.valueREFERE);
					changeREFERE = true;
				}
			});
		});

      $(function() {
			$("#INTTRI_RESPUF").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeRESPUF = false;
					var str = $("#INTTRI_RESPUF").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=RESPUF&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value,
										value: item[0].value,
										valueRESPUF: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_RESPUF").val(ui.item.valueRESPUF);
					changeRESPUF = true;
				}
			});
		});

      $(function() {
			$("#INTTRI_CODAUSA").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeCODAUSA = false;
					var str = $("#INTTRI_CODAUSA").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=CODAUSA&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[0].value,
										value: item[0].value,
										valueCODAUSA: item[0].value,
										valueSOGAGG: item[1].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_CODAUSA").val(ui.item.valueCODAUSA);
					$("#INTTRI_SOGAGG").val(ui.item.valueSOGAGG);
					changeCODAUSA = true;
				}
			});
		});

		$(function() {
			$("#INTTRI_SOGAGG").autocomplete({
				delay: 0,
			  autoFocus: true,
			  position: { 
				my : "left top",
				at: "left bottom"
			  },
				source: function( request, response ) {
					changeSOGGAGG = false;
					var str = $("#INTTRI_SOGAGG").val();
					$.ajax({
						async: false,
					  type: "GET",
			  dataType: "json",
			  beforeSend: function(x) {
				  if (x && x.overrideMimeType) {
					x.overrideMimeType("application/json;charset=UTF-8");
						}
					},
			  url: "${pageContext.request.contextPath}/piani/GetDatiInterventi.do",
			  data: "nomeCampo=SOGAGG&contri=${datiRiga.INTTRI_CONTRI}&conint=${datiRiga.INTTRI_CONINT}&strDesc="+str,
						success: function( data ) {
							if (!data) {
								response([]);
							} else {
								response( $.map( data, function( item ) {
									return {
										label: item[1].value,
										value: item[1].value,
										valueSOGAGG: item[1].value,
										valueCODAUSA: item[0].value
									}
								}));
							} 
						},
			  error: function(e) {
				alert("Errore durante la lettura della lista degli interventi precedenti");
			  }
					});
				},
				minLength: 1,
				select: function( event, ui ) {
					$("#INTTRI_SOGAGG").val(ui.item.valueSOGAGG);
					$("#INTTRI_CODAUSA").val(ui.item.valueCODAUSA);
					changeSOGGAGG = true;
				}
			});
		});
		
		function modificaCui() {
			openPopUpCustom("href=piani/inttri/dettaglio-codice-cuiint.jsp&modo=MODIFICA&key=${keyINTTRI}&cui="+getValue("INTTRI_CUIINT")+"&tipoin="+getValue("INTTRI_TIPOIN"), "formCUIINT", 700, 300, "yes", "yes");				
		}
		
		var tmpSchedaConferma = schedaConferma;
	
		var schedaConferma = function(){
			var variato2 = getValue('VARIATO2');
			setValue("INTTRI_VARIATO",variato2);
			$('#INTTRI_ANNINT').prop('disabled', false);
			tmpSchedaConferma();
		}
		
	</c:if>
	
	
		<!-- 	//////////////////////////////////////////////	 -->
		<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
		<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
		
		function eseguiFabbisognoScheda(chiave,funzionalita) {
			openPopUpCustom("href=piani/fabbisogni/popup-contrassegna-fabbisogno.jsp?fabbisogni=" + chiave +"&funzionalita="+funzionalita, "eseguiFabbisognoScheda", 650, 400, 1, 1);
		}
		
	
<!-- 		function approvaFabbisogno(chiave,funzione,chiavi_concatenate) { -->
<!-- 			location.href = "${pageContext.request.contextPath}/ApriPagina.do?href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&key="+chiave+"&funzionalita="+chiavi_concatenate+"&chiavi_concatenate="+chiavi_concatenate; -->
<!-- 		} -->
		
		function approvaFabbisognoScheda(chiave,funzionalita) {
			location.href = "${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&fabbisogni="+chiave+"&funzionalita="+funzionalita;
		}
		
<!-- 		function richiediRevisioneRespingiFabbisogno(chiave,funzione) { -->
<%-- 			location.href = "${pageContext.request.contextPath}/ApriPagina.do?href=piani/ptapprovazioni/ptapprovazioni-scheda.jsp&modo=NUOVO&key="+chiave+"&funzionalita="+funzione; --%>
<!-- 		} -->
	
		function inserisciInProgrammazioneScheda(conint,funzionalita,tipint,ailint) {
			openPopUpCustom("href=piani/piatri/piatri-popup-inserisciInProgrammazione.jsp&fabbisogni="+conint+"&funzionalita="+funzionalita+"&tipint="+tipint+"&ailintMin="+ailint+"&ailintMax="+ailint, "inserisciInProgrammazioneSelezionati",1000,500,1,1);
		}

		function inSviluppo(chiave) {
		  			alert("Funzione in sviluppo!")
		}
		
		<!-- 	//////////////////////////////////////////////	 -->

	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
		
		_creaFinestraAlberoCpvVP();
		_creaLinkAlberoCpvVP($("#INTTRI_CODCPV").parent(), "${modo}", $("#INTTRI_CODCPV"), $("#INTTRI_CODCPVview"), $("#CPVDESC") );
		_creaLinkAlberoCpvVP($("#INTTRI_AVCPV").parent(), "${modo}", $("#INTTRI_AVCPV"), $("#INTTRI_AVCPVview"), $("#AVCPVDESC") );
		_creaLinkAlberoCpvVP($("#INTTRI_MRCPV").parent(), "${modo}", $("#INTTRI_MRCPV"), $("#INTTRI_MRCPVview"), $("#MRCPVDESC") );
		
		$("input[name*='CPV']").attr('readonly','readonly');
		$("input[name*='CPV']").attr('tabindex','-1');
		$("input[name*='CPV']").css('border-color','#A3A6FF');
		$("input[name*='CPV']").css('border-width','1px');
		$("input[name*='CPV']").css('background-color','#E0E0E0');
		
		$("input[name*='CPVDESC']").attr('readonly','readonly');
		$("input[name*='CPVDESC']").attr('tabindex','-1');
		$("input[name*='CPVDESC']").css('border-color','#A3A6FF');
		$("input[name*='CPVDESC']").css('border-width','1px');
		$("input[name*='CPVDESC']").css('background-color','#E0E0E0');
		
		_creaFinestraAlberoNUTS();
		_creaLinkAlberoNUTS($("#INTTRI_NUTS").parent(), "${modo}", $("#INTTRI_NUTS"), $("#W9LOTT_LUOGO_NUTSview") );

		$("input[name^='INTTRI_NUTS']").attr('readonly','readonly');
		$("input[name^='INTTRI_NUTS']").attr('tabindex','-1');
		$("input[name^='INTTRI_NUTS']").css('border-width','1px');
		$("input[name^='INTTRI_NUTS']").css('background-color','#E0E0E0');		
	});

	</gene:javaScript>
