<%
  /*
   * Created on 07-nov-2017
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

	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.cpvvp.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/dataTable/dataTable/jquery.dataTables.css" >
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.integrazioneLFS.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>

		<style type="text/css">
	
		TABLE.integrazioneLFS {
			margin-top: 5px;
			margin-bottom: 5px;
			padding: 0px;
			font-size: 11px;
			border-collapse: collapse;
			border-left: 1px solid #A0AABA;
			border-top: 1px solid #A0AABA;
			border-right: 1px solid #A0AABA;
		}

		TABLE.integrazioneLFS TR.intestazione {
			background-color: #EFEFEF;
			border-bottom: 1px solid #A0AABA;
		}
		
		TABLE.integrazioneLFS TR.intestazione TH {
			padding: 2 15 2 5;
			text-align: center;
			font-weight: bold;
			border-left: 1px solid #A0AABA;
			border-right: 1px solid #A0AABA;
			border-top: 1px solid #A0AABA;
			border-bottom: 1px solid #A0AABA;
			height: 25px;
		}

		TABLE.iintegrazioneLFS TR.intestazione TH.associa {
			padding: 2 5 2 5;
			width: 50px;
		}
		
		TABLE.integrazioneLFS TR TD.center {
			text-align: center;
		}
	
	
		TABLE.integrazioneLFS TR {
			background-color: #FFFFFF;
		}

		TABLE.integrazioneLFS TR TD {
			padding-left: 3px;
			padding-top: 1px;
			padding-bottom: 1px;
			padding-right: 3px;
			text-align: left;
			border-left: 1px solid #A0AABA;
			border-right: 1px solid #A0AABA;
			border-top: 1px solid #A0AABA;
			border-bottom: 1px solid #A0AABA;
			height: 25px;
			font: 11px Verdana, Arial, Helvetica, sans-serif;
		}
		
		TABLE.integrazioneLFS TR TD.error {
			color: #D30000;
			font-weight: bold;
			padding: 10 10 10 10;
		}

		TABLE.integrazioneLFS TR TD.associa {
			padding: 2 5 2 5;
			width: 50px;
			text-align: center;
		}
		
		TABLE.integrazioneLFS TR TD.codice {
			width: 80px;
		}
		
		#linkListaLavori, #linkSchedaLavoro {
			color: black;
			float: right;
			background-color: #FFB45A; 
			padding-left: 5px;
			padding-right: 5px;
			padding-top: 1px;
			padding-bottom: 1px;
			border: 1px solid #A0AABA; 
			-moz-border-radius-topleft: 4px; 
			-webkit-border-top-left-radius: 4px; 
			-khtml-border-top-left-radius: 4px; 
			border-top-left-radius: 4px; 
			-moz-border-radius-topright: 4px;
			-webkit-border-top-right-radius: 4px;
			-khtml-border-top-right-radius: 4px;
			border-top-right-radius: 4px;
			-moz-border-radius-bottomleft: 4px; 
			-webkit-border-bottom-left-radius: 4px; 
			-khtml-border-bottom-left-radius: 4px; 
			border-bottom-left-radius: 4px; 
			-moz-border-radius-bottomright: 4px;
			-webkit-border-bottom-right-radius: 4px;
			-khtml-border-bottom-right-radius: 4px;
			border-bottom-right-radius: 4px;
		}
			
		</style>
	
	</gene:redefineInsert>

<c:choose>
	<c:when test='${modo eq "NUOVO"}'>
		${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNextConintFunction", pageContext, keyParent)}
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(keyParent,"CONTRI")};INTTRI.CONINT=N:${count}'
			scope="request" />
			</c:when>
	<c:otherwise>
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")};INTTRI.CONINT=N:${gene:getValCampo(key,"CONINT")}'
			scope="request" />
	</c:otherwise>
</c:choose>

<c:set var="tiprog" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, key)}' />

<c:set var="keyContri" value='${gene:getValCampo(keyINTTRI,"CONTRI")}' scope="request" />
<c:set var="isModifica" value="${modoAperturaScheda ne 'VISUALIZZA'}" />

<c:set var="isIntegrazioneWsCUP" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.IsIntegrazioneWsCUPFunction",  pageContext)}' />
<c:set var="esisteProfiloCup" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.EsisteProfiloFunction", pageContext, "PRO_CUP")}' scope="request" />

<c:set var="isQuadroEconomicoModificabile" value='${! gene:checkProtObj( pageContext,"PAGE.VIS","PIANI.INTTRI-scheda.RISORSEBILANCIO")}' scope="request" />	

<c:set var="isPersonalizzazionePisaAttiva" value='${gene:checkProtObj(pageContext,"FUNZ.VIS","ALT.PIANI.INTTRI_CAMPI_COMUNE_PISA")}' />

	<c:choose>
		<c:when test='${tiprog eq "1"}'>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"INTTRI")}' />
		</c:when>
		<c:otherwise>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"INTTRIFS")}' />
		</c:otherwise>
	</c:choose>
		
	<gene:formScheda entita="INTTRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINTTRI"
		plugin="it.eldasoft.sil.pt.tags.gestori.plugin.GestoreINTTRI">
		<gene:gruppoCampi idProtezioni="GEN">
			<gene:campoScheda>
				<td colspan="2"><b>Dati generali</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CONTRI" defaultValue='${gene:getValCampo(keyParent,"CONTRI")}'	visibile="false" />
			<gene:campoScheda campo="CONINT" visibile="false" value='${gene:getValCampo(keyINTTRI,"CONINT")}' />

			<gene:campoScheda campo="TIPROG" entita="PIATRI" where="PIATRI.CONTRI=INTTRI.CONTRI and INTTRI.CONTRI=${keyContri}" visibile="false" modificabile="false" defaultValue="${tiprog}" value="${tiprog}" />
			<gene:campoScheda campo="ANNTRI" entita="PIATRI" where="PIATRI.CONTRI=INTTRI.CONTRI and INTTRI.CONTRI=${keyContri}" visibile="false" modificabile="false" defaultValue="${anntri}" />
				
			<gene:campoScheda campo="TIPINT" visibile="false" defaultValue="${datiRiga.PIATRI_TIPROG}" />
			<gene:campoScheda campo="CUIINT" modificabile="false" speciale="true" obbligatorio="false" >
				<c:if test='${(modoAperturaScheda eq "MODIFICA" or modo eq "MODIFICA") and modificaCUI eq "si"}' >
					<gene:popupCampo titolo="Modifica CUI" href='javascript:modificaCui()' />
				</c:if>
			</gene:campoScheda>
				
			<c:choose>
				<c:when test='${tiprog eq 1}'>
					<gene:campoScheda campo="TIPOIN" modificabile="${datiRiga.INTTRI_CUIINT eq ''}" defaultValue="L" visibile="false" />
				</c:when>
				<c:when test='${tiprog eq 2}'>
					<gene:campoScheda campo="TIPOIN"  modificabile="${datiRiga.INTTRI_CUIINT eq ''}" 
						gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreTipoinAnnuale" obbligatorio="true" />
				</c:when>
			</c:choose>
			<gene:campoScheda campo="CEFINT" visibile="false"/>
			<gene:campoScheda campo="CODINT"/>			
		
			<gene:campoScheda campo="NPROGINT" visibile="false" defaultValue="${count}"/>

		<c:choose>
			<c:when test="${tiprog ne 2}">
				<gene:campoScheda campo="DESINT" obbligatorio="true" href=""  />
			</c:when>
			<c:otherwise>
				<gene:campoScheda campo="DESINT" obbligatorio="true" title="Descrizione dell'acquisto" />
			</c:otherwise>
		</c:choose>

			<gene:campoScheda campo="SOGGREF" visibile='${isPersonalizzazionePisaAttiva}' />
			<gene:campoScheda campo="DIREZIONE" visibile='${isPersonalizzazionePisaAttiva}' />
			<gene:campoScheda campo="CENTRICOSTO" visibile='${isPersonalizzazionePisaAttiva}' />
			
			<gene:campoScheda campo="PRIMANN" visibile="false" modificabile="false" />
			<gene:campoScheda campo="ANNRIF" obbligatorio="true">
					<gene:addValue value="1" descr="${datiRiga.PIATRI_ANNTRI}" />
					<gene:addValue value="2" descr="${datiRiga.PIATRI_ANNTRI+1}" />
					<c:if test="${datiRiga.PIATRI_TIPROG ne '2'}" >	
						<gene:addValue value="3" descr="${datiRiga.PIATRI_ANNTRI+2}" />
					</c:if>
			</gene:campoScheda>
			<gene:campoScheda campo="MESEIN" />
			<gene:campoScheda campo="FLAG_CUP" defaultValue="2" />
			<gene:campoScheda campo="CUPPRG" speciale="true" >
				<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" }' >
					<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
				</c:if>
			</gene:campoScheda>

			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" }'>
				<gene:campoScheda>
					<td colspan="2"><b>Credenziali per il servizio CUP</b></td>
				</gene:campoScheda>
				<c:choose>
					<c:when test="${empty cupwsuser}">
						<gene:campoScheda>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" />
								<input type="password" name="cupwspass" id="cupwspass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
							</td>
						</gene:campoScheda>
						<input type="hidden" name="recuperauser" value="0" />
						<input type="hidden" name="recuperapassword" value="0" />
					</c:when>
					<c:otherwise>
						<gene:campoScheda>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" value="${cupwsuser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="cupwspass" id="cupwspass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</td>
						</gene:campoScheda>
						<input type="hidden" name="recuperauser" value="1" />
						<input type="hidden" name="recuperapassword" value="1" />	
					</c:otherwise>
				</c:choose>
				<gene:campoScheda>	
					<td class="etichetta-dato">Memorizza le credenziali</td>
						<td class="valore-dato">
								<input type="checkbox" name="memorizza" value="memorizza" checked="checked"/>					
						</td>
				</gene:campoScheda>
    	</c:if>
			
			<gene:campoScheda campo="ACQALTINT" />
			<gene:campoScheda campo="CUICOLL" >
				<gene:checkCampoScheda funzione="controlloCUICOLL()" messaggio="Inserire in codice CUI di un intervento o di un altro acquisto del programma" obbligatorio="true" onsubmit="false" />
			</gene:campoScheda>

			<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false" 
				lista="gene/commons/istat-comuni-lista-popup.jsp" scheda=""
				schedaPopUp="" 
				campi="TABSCHE.TABCOD3;TABSCHE.TABDESC;TB1.TABCOD3">
				<gene:campoScheda campo="COMINT" title="Codice ISTAT del Comune" visibile="false" />
				<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true" 
					definizione="T120"	title="Comune luogo di esecuzione del contratto" 
					value="${comune}" visibile="${datiRiga.PIATRI_TIPROG ne '2'}" />			
				<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true" 
					definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia luogo di esecuzione del contratto" 
					value="${provincia}" visibile="${datiRiga.PIATRI_TIPROG ne '2'}" />
			</gene:archivio>

			<gene:campoScheda campo="NUTS" href="#" modificabile="false" speciale="true" >
				<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
			</gene:campoScheda>

			<gene:campoScheda campo="AREA" visibile='${isPersonalizzazionePisaAttiva}' />

			<gene:campoScheda campo="CODCPV" visibile="${tiprog eq 2}" href="#" speciale="true" >
				<gene:popupCampo titolo="Dettaglio CPV" href="#" />
			</gene:campoScheda>
			<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" campoFittizio="true" value="${cpvdescr}"	definizione="T150" visibile="${tiprog eq 2}" />

			<c:if test='${tiprog ne 1}'>
				<gene:campoScheda campo="QUANTIT" />
				<gene:campoScheda campo="UNIMIS" />
			</c:if>

			<gene:campoScheda campo="PRGINT" />

			<gene:archivio titolo="TECNICI"
					lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.PIANI.INTTRI.CODRUP"),"gene/tecni/tecni-lista-popup.jsp","")}'
					scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
					schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
					campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="INTTRI_CODRUP" >
				<gene:campoScheda campo="CODRUP" visibile="false" />
				<gene:campoScheda campo="NOMTEC" campoFittizio="true" title="RUP"
					value="${nomeTecnico}" definizione="T61;0;;;" />
			</gene:archivio>
				
			<gene:campoScheda campo="DIRGEN" />
			<gene:campoScheda campo="STRUOP" />
			<gene:campoScheda campo="RESPUF" />
			<gene:campoScheda campo="LOTFUNZ" />
			
			<gene:campoScheda campo="OPERESCOMP" visibile='${isPersonalizzazionePisaAttiva}' />
			
			<gene:campoScheda campo="LAVCOMPL" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
			<gene:campoScheda campo="DURCONT" visibile="${datiRiga.PIATRI_TIPROG eq '2'}" />
			<gene:campoScheda campo="CONTESS" visibile="${datiRiga.PIATRI_TIPROG eq '2'}" />
			<gene:campoScheda campo="SEZINT" visibile="${tiprog ne 2}" />
			<gene:campoScheda campo="INTERV" visibile="${tiprog ne 2}" title="Classificazione intervento: Settore e sottosettore"/>
			<gene:campoScheda campo="SCAMUT" visibile="${datiRiga.PIATRI_TIPROG ne '2'}"  />
		</gene:gruppoCampi>

		<gene:gruppoCampi idProtezioni="ELEANN">
			<gene:campoScheda id="elencoAnnuale" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" >
				<td colspan="2"><b>Dati elenco annuale</b></td> 
			</gene:campoScheda>
			<gene:campoScheda campo="ANNINT" visibile="false" />
			<gene:campoScheda campo="FIINTR" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
			<gene:campoScheda campo="URCINT" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
			<gene:campoScheda campo="APCINT" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
			<gene:campoScheda campo="STAPRO" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
		</gene:gruppoCampi>

		<c:if test="${tiprog eq '1'}" >
			<jsp:include page="immobili_da_trasferire_norma.jsp">
				<jsp:param name="keyInttri" value="${keyINTTRI}" />
			</jsp:include>
		</c:if>
				
		<c:choose>
			<c:when test='${tiprog eq 1}'>
				<c:set var="colspanGriglia" value="11" />
			</c:when>
			<c:when test='${tiprog eq 2}'>
				<c:set var="colspanGriglia" value="9" />
			</c:when>
		</c:choose>
				
			<gene:campoScheda addTr="false">
				<tr>
					<td colspan="2">
						<br>
						<table class="griglia">
							<tr>
								<td colspan="${colspanGriglia}" style="BORDER-RIGHT: #A0AABA 1px solid;"><b><center>Quadro delle risorse</center></b></td>
							</tr>
							<tr>
								<td rowspan="2" class="valore-dato"><center><b>Tipologie Risorse</b></center></td>
								<td colspan="${colspanGriglia-1}" class="valore-dato" style="BORDER-RIGHT: #A0AABA 1px solid;"><center>Stima dei costi</center></td>
							</tr>
							<tr>
								<td colspan="2" class="valore-dato"><center>Primo anno</center></td>
								<td colspan="2" class="valore-dato"><center>Secondo anno</center></td>
								<td id="scf_3a" class="valore-dato" colspan="2"><center>Terzo anno</center></td>
								<td colspan="2" class="valore-dato"><center>Annualit&agrave; successive</center></td>
								<td colspan="2" class="valore-dato"><center>Totale</center></td>
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da entrate aventi destinazione vincolata per legge</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="DV1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV3TRI" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="DV9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="DVNTRI" campo="DVNTRI" campoFittizio="true" title="Risorse derivanti da entrate aventi destinazione vincolata per legge" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da entrate acquisite mediante contrazione di mutuo</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="MU1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU3TRI" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="MU9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="MUNTRI" campo="MUNTRI" campoFittizio="true" title="Risorse derivanti da entrate acquisite mediante contrazione di mutuo" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>

					<c:choose>
						<c:when test='${! isPersonalizzazionePisaAttiva}'>
							<tr style='display: none;' >
						</c:when>
						<c:otherwise>
							<tr>
						</c:otherwise>
					</c:choose>
								<td class="etichetta-dato"><b>Importi della societ&agrave; in house</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="HOUTRI1" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${HOUTRI1}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda campo="HOUTRI2" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${HOUTRI2}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda campo="HOUTRI3" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${HOUTRI3}" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="HOUTRI9" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${HOUTRI9}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda id="HOUTRI" campo="HOUTRI" campoFittizio="true" title="Importo della societ&agrave; in house" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" value="${HOUTRITOT}" />
								<gene:campoScheda addTr="false">
							</tr>
							
					<c:choose>
						<c:when test='${! isPersonalizzazionePisaAttiva}'>
							<tr style='display: none;' >
						</c:when>
						<c:otherwise>
							<tr>
						</c:otherwise>
					</c:choose>
								<td class="etichetta-dato"><b>Importi da altri soggetti</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="IASTRI1" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${IASTRI1}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda campo="IASTRI2" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${IASTRI2}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda campo="IASTRI3" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${IASTRI3}" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="IASTRI9" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" value="${IASTRI9}" campoFittizio="true" definizione="F24.5;0;;MONEY;" />
								<gene:campoScheda id="IASTRI" campo="IASTRI" campoFittizio="true" title="Importo da altri soggetti" definizione="F15.2;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" value="${IASTRITOT}" />
								<gene:campoScheda addTr="false">
							</tr>

							<tr>
								<td class="etichetta-dato"><b>Risorse acquisite mediante apporti di capitale privato</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="PR1TRI" addTr="false" hideTitle="true" modificabile='${! isPersonalizzazionePisaAttiva}' />
								<gene:campoScheda campo="PR2TRI" addTr="false" hideTitle="true" modificabile='${! isPersonalizzazionePisaAttiva}' />
								<gene:campoScheda campo="PR3TRI" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile='${! isPersonalizzazionePisaAttiva}' />
								<gene:campoScheda campo="PR9TRI" addTr="false" hideTitle="true" modificabile='${! isPersonalizzazionePisaAttiva}' />
								<gene:campoScheda campo="ICPINT" addTr="false" hideTitle="true" modificabile="false" >
									<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#INTTRI_PR1TRI#","#INTTRI_PR2TRI#","#INTTRI_PR3TRI#","#INTTRI_PR9TRI#")))'
									elencocampi="INTTRI_PR1TRI;INTTRI_PR2TRI;INTTRI_PR3TRI;INTTRI_PR9TRI" />
								</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Stanziamenti di bilancio</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="BI1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="BI2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="BI3TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="BI9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="BINTRI" campo="BINTRI" campoFittizio="true" title="Stanziamenti di bilancio" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Finanziamenti art. 3 DL 310/1990</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="AP1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AP2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AP3TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="AP9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda id="APNTRI" campo="APNTRI" campoFittizio="true" title="Finanziamenti" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Risorse derivanti da trasferimento immobili</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="IM1TRI" addTr="false" hideTitle="true" modificabile="${datiRiga.PIATRI_TIPROG eq '2'}" />
								<gene:campoScheda campo="IM2TRI" addTr="false" hideTitle="true" modificabile="${datiRiga.PIATRI_TIPROG eq '2'}" />
								<gene:campoScheda campo="IM3TRI" addTr="false" hideTitle="true" modificabile="${datiRiga.PIATRI_TIPROG eq '2'}" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="IM9TRI" addTr="false" hideTitle="true" modificabile="${datiRiga.PIATRI_TIPROG eq '2'}" />
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
								<gene:campoScheda campo="AL1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL3TRI" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="AL9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
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
								
								<gene:campoScheda campo="INTRI_DF3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="false" addTr="false" hideTitle="true">
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
								<gene:campoScheda campo="GIASOS3" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="GIASOS9" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
								<gene:campoScheda campo="SPESESOST" modificabile="${isQuadroEconomicoModificabile}" addTr="false" hideTitle="true" />
								
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Totale</b></td>
								</gene:campoScheda>
															
								<gene:campoScheda campo="TOT1" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOT2" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOT3" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" />
								<gene:campoScheda campo="TOT9" campoFittizio="true" definizione="F24.5;0;;;" title="Totale disponibilit&agrave; annualit&agrave; successive" modificabile="false" addTr="false" hideTitle="true" />
								<gene:campoScheda campo="TOTINT" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:fnJavaScriptScheda funzione='setValue("INTTRI_TOTINT",toMoney(sommaCampi(new Array("#INTTRI_DITINT#","#INTTRI_ICPINT#","#INTTRI_SPESESOST#"))));calcoloImpNettoIvaQR();' elencocampi="INTTRI_DITINT;INTTRI_ICPINT;INTTRI_SPESESOST" esegui="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr id = "di_cui_iva">
								<td class="etichetta-dato"><b>Di cui IVA</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="IV1TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
								<gene:campoScheda campo="IV2TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
<%-- 								<gene:campoScheda campo="IV3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="false" addTr="false" hideTitle="true"/> --%>
								<gene:campoScheda campo="IV3TRI" visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="${isQuadroEconomicoModificabile}" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="IV9TRI" addTr="false" hideTitle="true" modificabile="${isQuadroEconomicoModificabile}" />
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

								<gene:campoScheda campo="INTRI_SI3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									visibile="${datiRiga.PIATRI_TIPROG eq '1'}" modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI3TRI",toMoney(sommaCampi(new Array("#INTTRI_DI3INT#","#INTTRI_PR3TRI#",-"#INTTRI_IV3TRI#"))))' elencocampi="INTTRI_DI3INT;INTTRI_PR3TRI;INTTRI_IV3TRI" esegui="true"/>
																
								<gene:campoScheda campo="INTRI_SI9TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; annualit&agrave; successive"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("INTRI_SI9TRI",toMoney(sommaCampi(new Array("#INTTRI_DI9INT#","#INTTRI_PR9TRI#",-"#INTTRI_IV9TRI#"))))' elencocampi="INTTRI_DI9INT;INTTRI_PR9TRI;INTTRI_IV9TRI" esegui="true"/>
								
								<gene:campoScheda id="SINTRI" campo="SINTRI" campoFittizio="true" title ="Importo al netto di IVA" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
						</table>
						<br>
					</td>
				</tr>
			</gene:campoScheda>
					
			<gene:campoScheda campo="TCPINT" />
			<gene:campoScheda campo="RG1TRI" visibile="${datiRiga.PIATRI_TIPROG eq '2'}" modificabile="${isQuadroEconomicoModificabile}" />
			<gene:campoScheda campo="IMPRFS" visibile="${datiRiga.PIATRI_TIPROG eq '2'}" modificabile="${isQuadroEconomicoModificabile}" />
			<gene:campoScheda campo="IMPALT" visibile="${datiRiga.PIATRI_TIPROG eq '2'}" modificabile="${isQuadroEconomicoModificabile}" />
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
				
		<gene:gruppoCampi>
			<gene:campoScheda>
				<td colspan="2"><b>Modalit&agrave; di affidamento</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="PROAFF" />
			<gene:campoScheda campo="DELEGA" />
			<gene:fnJavaScriptScheda funzione='gestioneDelega()' elencocampi="INTTRI_DELEGA" esegui="true"/>
			<c:choose>
				<c:when test="${tiprog eq 2}">
					<gene:campoScheda campo="CODAUSA" title="Codice AUSA Centrale di Committenza o Soggetto Aggregatore" />
					<gene:campoScheda campo="SOGAGG" title="Denominazione Centrale di Committenza o Soggetto Aggregatore" />
				</c:when>
				<c:otherwise>
					<gene:campoScheda campo="CODAUSA" />
					<gene:campoScheda campo="SOGAGG" />
				</c:otherwise>
			</c:choose>
		</gene:gruppoCampi>

		<gene:gruppoCampi>
			<gene:campoScheda>
				<td colspan="2"><b>Altri dati</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="VARIATO" visibile="false" />
			<c:choose>
				<c:when test='${tiprog eq 1}'>
					<gene:campoScheda campo="VARIATO2" title="Intervento variato a seguito di modifica programma" campoFittizio="true" definizione="A100;0;PT011"
					value="${datiRiga.INTTRI_VARIATO}" visibile="${aggiornamento ne '001'}"/>
				</c:when>
				<c:when test='${tiprog eq 2}'>
					<gene:campoScheda campo="VARIATO2" title="Intervento variato a seguito di modifica programma" campoFittizio="true" definizione="A100;0;PT010"
					value="${datiRiga.INTTRI_VARIATO}" visibile="${aggiornamento ne '001'}"/>
				</c:when>
			</c:choose>
			
			<gene:campoScheda campo="REFERE" />
			<gene:campoScheda campo="VALUTAZIONE" />
			<gene:campoScheda campo="INTNOTE" />
		</gene:gruppoCampi>

		<c:if test='${modificabile eq "no"}' >
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteNuovo" />
			<gene:redefineInsert name="pulsanteModifica" />
		</c:if>
		<gene:fnJavaScriptScheda funzione='modifyFLAG_CUP("#INTTRI_FLAG_CUP#")'	elencocampi="INTTRI_FLAG_CUP" esegui="true" />

		<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
			<jsp:param name="entitaParent" value="INTTRI" />
		</jsp:include>

		<gene:campoScheda>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
		</gene:campoScheda>
	</gene:formScheda>
	<gene:javaScript>
		
		<c:if test='${modo eq "VISUALIZZA" and modificabile ne "no"}' >
			<c:if test="${not empty urlIntegrazioneLFS}" >
				$(window).ready(function (){
				var _v1 = "${pageContext.request.contextPath}";
				var _v2 = "${urlIntegrazioneLFS}"
				var _v3 = $("#INTTRI_CONTRI").val();
				var _v4 = $("#INTTRI_CONINT").val();
				myIntegrazioneLFS.init(_v1,_v2,_v3,_v4);
				<c:if test='${modificabile ne "no"}' >
					myIntegrazioneLFS.creaFinestraListaLavori();
					myIntegrazioneLFS.creaLinkListaLavori($("#rowINTTRI_CODINT td:eq(1)"));
				</c:if>
				myIntegrazioneLFS.creaFinestraSchedaLavoro();
				myIntegrazioneLFS.creaLinkSchedaLavoro($("#rowINTTRI_CODINT td:eq(1)"));
			});
			</c:if>
		</c:if>
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
			
			annrif = toVal(getValue('INTTRI_ANNRIF'));
			tiprog = toVal(getValue('PIATRI_TIPROG'));
			acqAltInt = toVal(getValue('INTTRI_ACQALTINT'));
			copfin = toVal(getValue('INTTRI_COPFIN'));
			
			if (tiprog == 2)	{
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
		
			if (annrif == 1 && copfin != 2) {
				setValue("INTTRI_ANNINT","1");
			} else {
				setValue("INTTRI_ANNINT","2");
			}
			
			if (annrif == 1) {
				$("#rowelencoAnnuale").show();
				$("#rowINTTRI_FIINTR").show();
				$("#rowINTTRI_URCINT").show();
				$("#rowINTTRI_APCINT").show();
				$("#rowINTTRI_STAPRO").show();
				if (tiprog != 1) {
					$("#rowINTTRI_FIINTR").hide();
					$("#rowINTTRI_URCINT").hide();
					$("#rowINTTRI_APCINT").hide();
					$("#rowINTTRI_STAPRO").hide();
				}
			} else {
				$("#rowelencoAnnuale").hide();			
				$("#rowINTTRI_FIINTR").hide();
				$("#rowINTTRI_URCINT").hide();
				$("#rowINTTRI_APCINT").hide();
				$("#rowINTTRI_STAPRO").hide();
			}
			
			$('#INTTRI_ANNRIF').on("change",function() {
				annrif = toVal(getValue('INTTRI_ANNRIF'));
				tiprog = toVal(getValue('PIATRI_TIPROG'));

				if (annrif == 1 && copfin != 2) {
					setValue("INTTRI_ANNINT","1");
				} else {
					setValue("INTTRI_ANNINT","2");
				}
				
				if (annrif == 1) {
					$("#rowelencoAnnuale").show();			
					$("#rowINTTRI_FIINTR").show();
					$("#rowINTTRI_URCINT").show();
					$("#rowINTTRI_APCINT").show();
					$("#rowINTTRI_STAPRO").show();
					if (tiprog != 1) {
						$("#rowINTTRI_FIINTR").hide();
						$("#rowINTTRI_URCINT").hide();
						$("#rowINTTRI_APCINT").hide();
						$("#rowINTTRI_STAPRO").hide();
					}
				} else {
					$("#rowINTTRI_ANNINT").hide();
					$("#rowINTTRI_FIINTR").hide();
					$("#rowINTTRI_URCINT").hide();
					$("#rowINTTRI_APCINT").hide();
					$("#rowINTTRI_STAPRO").hide();
				}
			});
							
			$('#INTTRI_ACQALTINT').on("change",function() {
				acqAltInt = toVal(getValue('INTTRI_ACQALTINT'));
				if (acqAltInt == 2) {
					$("#rowINTTRI_CUICOLL").show();
				} else {
					$("#rowINTTRI_CUICOLL").hide();
					setValue("INTTRI_CUICOLL", "");
				}
			});

			copFinInt = toVal(getValue('INTTRI_COPFIN'));

			$('#INTTRI_COPFIN').on("change",function() {
				annrif = toVal(getValue('INTTRI_ANNRIF'));
				copfin = toVal(getValue('INTTRI_COPFIN'));
				
				if (annrif == 1 && copfin != 2) {
					setValue("INTTRI_ANNINT","1");
				} else {
					setValue("INTTRI_ANNINT","2");
				}
			});
			
		<c:if test="${modo ne 'VISUALIZZA'}" >
			$("#INTTRI_CODINT").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_MESEIN").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_QUANTIT").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_UNIMIS").css({
			"background-color": '#FFB45A'
			});

			$("#INTTRI_DIRGEN").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_STRUOP").css({
			"background-color": '#FFB45A'
			});

			$("#INTTRI_ACQVERDI").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_MATRIC").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_REFERE").css({
			"background-color": '#FFB45A'
			});
			
			$("#INTTRI_VALUTAZIONE").css({
			"background-color": '#FFB45A'
			});
			
		</c:if>

		});
			
			
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
			showObj("rowCAMPO_GENERICO16",!vis);
			showObj("rowCAMPO_GENERICO17",!vis);
			showObj("rowCAMPO_GENERICO18",!vis);
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
		<c:when test='${tiprog eq 1}'>
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
		<c:when test='${tiprog eq 1}'>
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
			openPopUpCustom("href=piani/oitri/dettaglio-codice-cuiimm.jsp&key=" + document.forms[0].key.value + 
				"&keyParent=" + document.forms[0].keyParent.value + "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA") + 
				"&campo1="+campo1+"&cui="+ getValue(campo1)+"&campo2="+campo2+"&contri="+ getValue(campo2), "formCUIIMM", 700, 300, 1, 1);
		}

		function richiestaListaCUP() {
			openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=INTTRI_CUPPRG&codiceCUP=" + getValue("INTTRI_CUPPRG"), "listaCUP", 900, 550, 1, 1);
		}
		
		function resetRecuperaUser() {
			document.forms[0].recuperauser.value = "0";
		}

		function resetRecuperaPassword() {
			document.forms[0].recuperapassword.value = "0";
		}

		function resetPassword() {
			document.forms[0].cupwspass.value = "";
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
		
	<c:if test='${isPersonalizzazionePisaAttiva}' >
		$('[id^="HOUTRI"]').on("change",
			function() {
				calcoloImportiTotaliQR("HOUTRI")
			}
		);

		$('[id^="IASTRI"]').on("change",
			function() {
				calcoloImportiTotaliQR("IASTRI")
			}
		);
	</c:if>
	
		function calcoloImportiTotaliQR(radix) {
			var imp_somma = 0;
			<c:if test='${isPersonalizzazionePisaAttiva}' >
			if (radix.length < 3) {
			</c:if>
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
			<c:if test='${isPersonalizzazionePisaAttiva}' >
			} else {
				var imp_sommaHou = 0;
				var imp_sommaIas = 0;
				var imp_somma1 = 0;
				var imp_somma2 = 0;
				var imp_somma3 = 0;
				var imp_somma9 = 0;
				if ($('#HOUTRI1').val() != null && $('#HOUTRI1').val() != "") {
					imp_sommaHou = imp_sommaHou + parseFloat($('#HOUTRI1').val());
					imp_somma1 = imp_somma1 + parseFloat($('#HOUTRI1').val());
				}
				if ($('#HOUTRI2').val() != null && $('#HOUTRI2').val() != "") {
					imp_sommaHou = imp_sommaHou + parseFloat($('#HOUTRI2').val());
					imp_somma2 = imp_somma2 + parseFloat($('#HOUTRI2').val());
				}
				if ($('#HOUTRI3').val() != null && $('#HOUTRI3').val() != "") {
					imp_sommaHou = imp_sommaHou + parseFloat($('#HOUTRI3').val());
					imp_somma3 = imp_somma3 + parseFloat($('#HOUTRI3').val());
				}
				if ($('#HOUTRI9').val() != null && $('#HOUTRI9').val() != "") {
					imp_sommaHou = imp_sommaHou + parseFloat($('#HOUTRI9').val());
					imp_somma9 = imp_somma9 + parseFloat($('#HOUTRI9').val());
				}
				if (imp_sommaHou >= 0) {
					valimp = toMoney(imp_sommaHou);
					setValue('HOUTRI',valimp);
					calcoloImpNettoIvaQR();
				}

				if ($('#IASTRI1').val() != null && $('#IASTRI1').val() != "") {
					imp_sommaIas = imp_sommaIas + parseFloat($('#IASTRI1').val());
					imp_somma1 = imp_somma1 + parseFloat($('#IASTRI1').val());
				}
				if ($('#IASTRI2').val() != null && $('#IASTRI2').val() != "") {
					imp_sommaIas = imp_sommaIas + parseFloat($('#IASTRI2').val());
					imp_somma2 = imp_somma2 + parseFloat($('#IASTRI2').val());
				}
				if ($('#IASTRI3').val() != null && $('#IASTRI3').val() != "") {
					imp_sommaIas = imp_sommaIas + parseFloat($('#IASTRI3').val());
					imp_somma3 = imp_somma3 + parseFloat($('#IASTRI3').val());
				}
				if ($('#IASTRI9').val() != null && $('#IASTRI9').val() != "") {
					imp_sommaIas = imp_sommaIas + parseFloat($('#IASTRI9').val());
					imp_somma9 = imp_somma9 + parseFloat($('#IASTRI9').val());
				}
				if (imp_sommaIas >= 0) {
					valimp = toMoney(imp_sommaIas);
					setValue('IASTRI',valimp);
					calcoloImpNettoIvaQR();
				}

				if (imp_somma1 >= 0) {
					valimp = toMoney(imp_somma1);
					setValue('INTTRI_PR1TRI',valimp);
					calcoloImpNettoIvaQR();
				}
				if (imp_somma2 >= 0) {
					valimp = toMoney(imp_somma2);
					setValue('INTTRI_PR2TRI',valimp);
					calcoloImpNettoIvaQR();
				}
				if (imp_somma3 >= 0) {
					valimp = toMoney(imp_somma3);
					setValue('INTTRI_PR3TRI',valimp);
					calcoloImpNettoIvaQR();
				}
				if (imp_somma9 >= 0) {
					valimp = toMoney(imp_somma9);
					setValue('INTTRI_PR9TRI',valimp);
					calcoloImpNettoIvaQR();
				}
			}
			</c:if>
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
		<c:if test='${tiprog eq 2}'>
			calcoloImportiTotaliQR("IV");
			calcoloImpNettoIvaQR();
		</c:if>
		
		calcoloTotImmobiliQR();

		<c:choose>
			<c:when test='${tiprog eq 1}'>
				$('.importo').css("width","100");
				$('.importoNoEdit').css("width","100");
			</c:when>
			<c:when test='${tiprog eq 2}'>
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
		<c:when test='${tiprog eq 2}'>
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
					  data: "nomeCampo=CUIINT&contri=${datiRiga.INTTRI_CONTRI}&anno=${datiRiga.INTTRI_ANNRIF}&conint=${datiRiga.INTTRI_CONINT}&cuiint=${datiRiga.INTTRI_CUIINT}&strDesc="+str,
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
		
	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
	
		_creaFinestraAlberoCpvVP();
		_creaLinkAlberoCpvVP($("#INTTRI_CODCPV").parent(), "${modo}", $("#INTTRI_CODCPV"), $("#INTTRI_CODCPVview"), $("#CPVDESC") );
		_creaLinkAlberoCpvVP($("#INTTRI_AVCPV").parent(),  "${modo}", $("#INTTRI_AVCPV"),  $("#INTTRI_AVCPVview"),  $("#AVCPVDESC") );
		_creaLinkAlberoCpvVP($("#INTTRI_MRCPV").parent(),  "${modo}", $("#INTTRI_MRCPV"),  $("#INTTRI_MRCPVview"),  $("#MRCPVDESC") );
		
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
		_creaLinkAlberoNUTS($("#INTTRI_NUTS").parent(), "${modo}", $("#INTTRI_NUTS"), $("#INTTRI_LUOGO_NUTSview") );

		$("input[name^='INTTRI_NUTS']").attr('readonly','readonly');
		$("input[name^='INTTRI_NUTS']").attr('tabindex','-1');
		$("input[name^='INTTRI_NUTS']").css('border-width','1px');
		$("input[name^='INTTRI_NUTS']").css('background-color','#E0E0E0');
	});

	</gene:javaScript>
<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />