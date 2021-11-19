<%
	/*
	 * Created on: 03/09/2009
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

<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />
<c:set var="flusso" value='988' scope="request" />
<c:set var="key1" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
<c:set var="key2" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
<c:set var="key3" value='' scope="request" />

<c:set var="key" value='${key}' scope="request" />

<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetTitleFunction",pageContext,"W9LOTT")}' />

<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
<c:if test='${(codgara eq "")||(empty codgara)}'>
	<c:set var="codgara" value='${gene:getValCampo(keyParent,"CODGARA")}' />
</c:if>
<c:choose>
	<c:when test='${modo eq "NUOVO"}'>
		${gene:callFunction2("it.eldasoft.w9.tags.funzioni.GetNextCodlottFunction", pageContext, codgara)}
		<c:set var="keyW9LOTT" value='W9LOTT.CODGARA=N:${codgara};W9LOTT.CODLOTT=N:${count}' scope="request" />
		<c:set var="codlott" value='${count}' />
	</c:when>
	<c:otherwise>
		<c:set var="keyW9LOTT" value='W9LOTT.CODGARA=N:${gene:getValCampo(key,"CODGARA")};W9LOTT.CODLOTT=N:${gene:getValCampo(key,"CODLOTT")}' scope="request" />
		<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' />
	</c:otherwise>
</c:choose>

<c:if test="${modo ne 'NUOVO'}">
	<c:set var="listaExistsFasi" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.ListaExistsFasiGaraLottoFunction", pageContext, codgara, codlott)}' />
</c:if>

<c:set var="isIntegrazioneWsCUP" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.IsIntegrazioneWsCUPFunction",  pageContext)}' />

<gene:formScheda entita="W9LOTT" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9LOTT"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9LOTT">
	
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.cpvvp.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
	</gene:redefineInsert>
	
<gene:redefineInsert name="addToAzioni">
	<c:choose>
		<c:when test='${modo eq "MODIFICA" and gene:checkProtFunz(pageContext, "MOD", "MOD")}'>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9LOTT-scheda.CopiaDaProgramma")}'>
				<tr>
					<td class="vocemenulaterale">
						<a href='javascript:apriImportaDatiProgrammi(${(modo eq "NUOVO")});' tabindex="1504" title="Importa dati">
							Copia da programma triennale/annuale
						</a>
					</td>
				</tr>
			</c:if>
		</c:when>
		<c:when test='${modo eq "VISUALIZZA"}'>
			<c:if test="${gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.RIALLINEASIMOG') and (userIsRup eq 'si' || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq 'A')}">
				<tr>
					<td class="vocemenulaterale">
						<a href='javascript:riallineaSIMOG();' tabindex="1505" title="Importa dati">Riallinea al SIMOG</a>
					</td>
				</tr>
			</c:if>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9GARA-scheda.ControlloDatiInseriti")}' >
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:popupValidazione('${flusso}','${key1}','${key2}','${key3}');" title="Controllo dati inseriti" tabindex="1506">
							Controllo dati inseriti
						</a>
					</td>
				</tr>
			</c:if>
			<c:if test="${gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.INVIAINDICISIMOG') and ((userIsRup eq 'si' || permessoUser ge 4 || sessionScope.profiloUtente.abilitazioneStd eq 'A'))}">
				<tr>
					<td class="vocemenulaterale">
						<a href='javascript:riallineaIndiciSIMOG();' tabindex="1507" title="Riallinea indici SIMOG">Riallinea indici SIMOG</a>
					</td>
				</tr>
			</c:if>
		</c:when>
	</c:choose>
</gene:redefineInsert>

<c:if test="${gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.REPORTINDICATORI') and isAttivoIndicatoriLotto eq true}">
	<gene:redefineInsert name="addToDocumenti">
		<tr>
			<td class="vocemenulaterale">
				<c:choose>
					<c:when test='${modo eq "VISUALIZZA"}'>
						<a href="javascript:sceltaTipoReportIndicatori();" title="Indicatori lotto" tabindex="1507">
							Report indicatori
						</a>
					</c:when>
					<c:otherwise>
						Report indicatori
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</gene:redefineInsert>
</c:if>
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODLOTT" visibile="false" value='${gene:getValCampo(keyW9LOTT,"CODLOTT")}' />
		<gene:campoScheda campo="CODGARA"	defaultValue='${codgara}'	visibile="false" />
		<gene:campoScheda campo="DAEXPORT" defaultValue='1'	visibile="false" />
		<gene:campoScheda campo="CIG" modificabile="true" />
		<gene:campoScheda campo="CODCUI" />
		<gene:campoScheda campo="OGGETTO" obbligatorio="true" />
		<gene:campoScheda campo="SITUAZIONE" modificabile="false" defaultValue="1"/>
		
		<gene:archivio titolo="TECNICI" 
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.W9LOTT.DEC"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9LOTT_DEC" >
			<gene:campoScheda campo="DEC" visibile="false"/>
			<gene:campoScheda campo="NOMTEC" campoFittizio="true" title="DEC" value="${nomeTecnico}" definizione="T61;0;;;" visibile='${gene:checkProt(pageContext, "COLS.VIS.W9.W9LOTT.DEC")}'/>
		</gene:archivio>
		
		<gene:campoScheda campo="NLOTTO" />
		<gene:campoScheda campo="CIG_MASTER_ML" modificabile="false" visibile="${!empty datiRiga.W9LOTT_CIG_MASTER_ML}"/>
		<gene:campoScheda campo="COMCON" visibile="false"/>
		<gene:campoScheda campo="DESCOM" visibile="false"/>
		<gene:campoScheda campo="CIGCOM" visibile="false"/>
		
<c:choose>
	<c:when test="${modo ne 'NUOVO'}" >
		<gene:campoScheda campo="TIPO_CONTRATTO" obbligatorio="true" >
			<gene:checkCampoScheda
				funzione='controllaTIPO_CONTRATTO(${fase_10})'
				messaggio='Il tipo di appalto non &egrave; compatibile con le fasi gi&agrave; create' 
				obbligatorio="true" />
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="TIPO_CONTRATTO" obbligatorio="true" />
	</c:otherwise>
</c:choose>

		<gene:campoScheda campo="FLAG_ENTE_SPECIALE" modificabile="false" defaultValue="${flagEnteSpeciale}"/>
		<gene:campoScheda campo="ART_E1" visibile="${datiRiga.W9GARA_VER_SIMOG <= 2}" >
			<gene:checkCampoScheda
				funzione='controllaART_E1(${fase_1})'
				messaggio='Il valore del campo Contratto escluso ex artt. 19/26 D.Lgs 163/06 non &egrave; compatibile con le fasi gi&agrave; create' 
				obbligatorio="true" />	
		</gene:campoScheda>
		<gene:campoScheda campo="ART_E2" visibile="false" />
		
		<gene:campoScheda campo="CONTRATTO_ESCLUSO_ALLEGGERITO" visibile="${datiRiga.W9GARA_VER_SIMOG > 2}" />
		<gene:campoScheda campo="ESCLUSIONE_REGIME_SPECIALE" visibile="${datiRiga.W9GARA_VER_SIMOG > 2}" />
		
		<gene:campoScheda campo="CUIINT" />
		<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" />
		<gene:campoScheda campo="VER_SIMOG" entita="W9GARA" where="W9GARA.CODGARA=W9LOTT.CODGARA" visibile="false" />
		<gene:campoScheda campo="ID_SCELTA_CONTRAENTE_50" visibile="${datiRiga.W9GARA_VER_SIMOG eq 1}" />
		<gene:campoScheda campo="ID_MODO_GARA" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreW9LottIdModoGara" />
		<gene:campoScheda campo="URL_EPROC" />
		<gene:campoScheda campo="URL_COMMITTENTE" />
		<gene:campoScheda campo="ID_SCHEDA_LOCALE" visibile="false" />
		<gene:campoScheda campo="DATA_PUBBLICAZIONE" modificabile="false" visibile="false" />
	</gene:gruppoCampi>

	<gene:gruppoCampi idProtezioni="RUP">
		<gene:campoScheda campo="RUP" value="${rupw9gara}" visibile="false" />
	</gene:gruppoCampi>
	<gene:gruppoCampi idProtezioni="MODFORN">
		<gene:campoScheda nome="ForniSer">
			<td colspan="2"><b>Modalit&agrave; di acquisizione forniture /	servizi</b></td>
		</gene:campoScheda>
		<c:if test="${fn:length(datiTabellatoFornList) > 0}">
			<c:forEach begin="0" end="${fn:length(datiTabellatoFornList)-1}" var="indiceTabForn">
				<c:set var="valoreForn" value="0" />
				<c:if test='${modo ne "NUOVO"}'>
					<c:set var="valoreForn"
						value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.VerificaCondizioneDaTabellatoFunction", pageContext, keyW9LOTT, datiTabellatoFornList[indiceTabForn][0], "W9APPAFORN")}'></c:set>
				</c:if>
				<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
					campo="ID_APPALTO_${indiceTabForn+1}"
					title="${datiTabellatoFornList[indiceTabForn][1]}"
					definizione="N5;0;;SN;" value="${valoreForn}" visibile="${datiTabellatoFornList[indiceTabForn][4] eq '' or (datiTabellatoFornList[indiceTabForn][4] eq '1' and valoreForn eq 1)}"
					gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNoSenzaNull" />
				<gene:campoScheda campoFittizio="true" entita="W9APPAFORN"
					campo="VALORE_ID_APPALTO_${indiceTabForn+1}"
					title="${datiTabellatoFornList[indiceTabForn][1]}"
					value="${datiTabellatoFornList[indiceTabForn][0]}"
					definizione="N5;0;;;" visibile="false" />
			</c:forEach>
		</c:if>
	</gene:gruppoCampi>
	<gene:gruppoCampi idProtezioni="MODLAV">
		<gene:campoScheda nome="TipLav">
			<td colspan="2"><b>Modalit&agrave; tipologia lavoro</b></td>
		</gene:campoScheda>
		<c:if test="${fn:length(datiTabellatoLavList) >0}">
			<c:forEach begin="0" end="${fn:length(datiTabellatoLavList)-1}"
				var="indiceTabLav">
				<c:set var="valoreLav" value="0" />
				<c:if test='${modo ne "NUOVO"}'>
					<c:set var="valoreLav"
						value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.VerificaCondizioneDaTabellatoFunction", pageContext, keyW9LOTT, datiTabellatoLavList[indiceTabLav][0], "W9APPALAV")}'></c:set>
				</c:if>
				<gene:campoScheda campoFittizio="true" entita="W9APPALAV"
					campo="ID_APPALTO_${indiceTabLav+1}"
					title="${datiTabellatoLavList[indiceTabLav][1]}"
					definizione="N5;0;;SN;" value="${valoreLav}"  visibile="${datiTabellatoLavList[indiceTabLav][4] eq '' or (datiTabellatoLavList[indiceTabLav][4] eq '1' and valoreLav eq 1)}"
					gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNoSenzaNull" />
				<gene:campoScheda campoFittizio="true" entita="W9APPALAV"
					campo="VALORE_ID_APPALTO_${indiceTabLav+1}"
					title="${datiTabellatoLavList[indiceTabLav][1]}"
					value="${datiTabellatoLavList[indiceTabLav][0]}"
					definizione="N5;0;;;" visibile="false" />
			</c:forEach>
		</c:if>
	</gene:gruppoCampi>

	<gene:gruppoCampi idProtezioni="CONDIZ" visibile="${datiRiga.W9GARA_VER_SIMOG eq 1}">
		<gene:campoScheda nome="CondizioniRicorsoProceduraNegoziata">
			<td colspan="2"><b>Condizioni che giustificano il ricorso alla procedura negoziata</b></td>
		</gene:campoScheda>
		<c:if test="${fn:length(datiTabellatoCondList) >0}" >
			<c:forEach begin="0" end="${fn:length(datiTabellatoCondList)-1}" var="indiceTabCond">
				<c:set var="valoreCond" value="0" />
				<c:set var="w9condtab1tip" value="${datiTabellatoCondList[indiceTabCond][0]}" />
				<c:set var="w9condtab1arc" value="${datiTabellatoCondList[indiceTabCond][4]}" />
				<c:if test='${modo ne "NUOVO"}'>
					<c:set var="valoreCond"
						value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.VerificaCondizioneDaTabellatoFunction", pageContext, key, w9condtab1tip, "W9COND")}'></c:set>
				</c:if>

				<gene:campoScheda campoFittizio="true" entita="W9COND"
					campo="ID_CONDIZIONE_${indiceTabCond+1}"
					title="${datiTabellatoCondList[indiceTabCond][1]}"
					value="${valoreCond}" definizione="N5;0;;SN;" 
					gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNoSenzaNull" />
				<gene:campoScheda campoFittizio="true" entita="W9COND"
					campo="VALORE_ID_CONDIZIONE_${indiceTabCond+1}"
					title="${datiTabellatoCondList[indiceTabCond][1]}"
					value="${datiTabellatoCondList[indiceTabCond][0]}"
					definizione="N5;0;;;" visibile="false" />
			</c:forEach>
		</c:if>
	</gene:gruppoCampi>

	<gene:gruppoCampi idProtezioni="DATIECONOMICI">
		<gene:campoScheda>
			<td colspan="2"><b>Dati economici</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="IMPDISP" visibile="false"/>
		<gene:campoScheda campo="IMPORTO_LOTTO" obbligatorio="true" />
		<gene:campoScheda campo="IMPORTO_ATTUAZIONE_SICUREZZA" />

<c:choose>
	<c:when test="${modo ne 'NUOVO'}">
		<gene:campoScheda campo="IMPORTO_TOT" modificabile="false">
			<gene:calcoloCampoScheda
				funzione='toMoney(sommaCampi(new Array("#W9LOTT_IMPORTO_LOTTO#","#W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA#")))'
				elencocampi="W9LOTT_IMPORTO_LOTTO;W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA" />
			<gene:checkCampoScheda
				funzione='controllaIMPORTO_TOT(${fase_1},${fase_11})'
				messaggio='L\'importo complessivo specificato non &egrave; compatibile con le fasi gi&agrave; create' 
				obbligatorio="true" />
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="IMPORTO_TOT" modificabile="false">
			<gene:calcoloCampoScheda
				funzione='toMoney(sommaCampi(new Array("#W9LOTT_IMPORTO_LOTTO#","#W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA#")))'
				elencocampi="W9LOTT_IMPORTO_LOTTO;W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA" />
		</gene:campoScheda>
	</c:otherwise>
</c:choose>
		<gene:campoScheda campo="CUPESENTE"/>
		<gene:campoScheda campo="CUP" speciale="true" >
			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" and gene:checkProt(pageContext, "COLS.MOD.W9.W9LOTT.CUP")}'>
				<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
			</c:if>
		</gene:campoScheda>
		
		<gene:campoScheda campo="CPV" title="Codice CPV" href="#" speciale='${gene:checkProt(pageContext, "COLS.MOD.W9.W9LOTT.CPV")}' />
		<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}" campoFittizio="true" definizione="T150" />
	</gene:gruppoCampi>
	<gene:gruppoCampi idProtezioni="CPVSEC">
		<!-- scheda multipla -->
		<c:if test='${modo ne "NUOVO"}'>
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetCpvSecondarioFunction" parametro="${keyW9LOTT}" />
		</c:if>
		<%//sottocategorie %>
		<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
			<jsp:param name="entita" value='W9CPV'/>
			<jsp:param name="chiave" value='${gene:getValCampo(keyW9LOTT, "CODGARA")};${gene:getValCampo(keyW9LOTT, "CODLOTT")}'/>
			<jsp:param name="nomeAttributoLista" value='listaCpvSecondario' />
			<jsp:param name="idProtezioni" value="W9CPV" />
			<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9lott/cpv_secondario.jsp" />
			<jsp:param name="arrayCampi" value="'W9CPV_CODGARA','W9CPV_CODLOTT', 'W9CPV_NUM_CPV_', 'W9CPV_CPV_','TABCPV_CPVDESC_','TABCPV_LABEL_'" />
			<jsp:param name="arrayVisibilitaCampi" value="false, false, false, true, true, false" />
			<jsp:param name="usaContatoreLista" value="true"/>
			<jsp:param name="sezioneListaVuota" value="false"/>
			<jsp:param name="titoloSezione" value="CPV Secondario" />
			<jsp:param name="titoloNuovaSezione" value="Nuovo CPV Secondario" />
			<jsp:param name="descEntitaVociLink" value="Descrizione CPV Secondario" />
			<jsp:param name="msgRaggiuntoMax" value="e Descrizione CPV Secondario" />
		</jsp:include>

		<gene:campoScheda>
			<td colspan="2"><b>&nbsp;</b></td>
		</gene:campoScheda>
		
<c:choose>
	<c:when test="${modo ne 'NUOVO'}" >
		<gene:campoScheda campo="MANOD" obbligatorio="true" 
			visibile='${datiRiga.W9LOTT_TIPO_CONTRATTO ne "L" and datiRiga.W9LOTT_TIPO_CONTRATTO ne "CL"}'>
			<gene:checkCampoScheda
				funzione='controllaMANOD(${fase_994},${fase_995},${fase_998})'
				messaggio='Il valore del campo Posa in opera o manodopera non &egrave; compatibile con le fasi gi&agrave; create' 
				obbligatorio="true" />	
		</gene:campoScheda>
	</c:when>
	<c:otherwise>
		<gene:campoScheda campo="MANOD" obbligatorio="true" 
			visibile='${datiRiga.W9LOTT_TIPO_CONTRATTO ne "L" and datiRiga.W9LOTT_TIPO_CONTRATTO ne "CL"}'/>
	</c:otherwise>
</c:choose>

		<gene:campoScheda campo="ID_CATEGORIA_PREVALENTE" />
		<gene:campoScheda campo="CLASCAT" />
	</gene:gruppoCampi>
	<gene:gruppoCampi idProtezioni="ULTCAT">
		<!--gene-:-campoScheda nome="labelUlterioriCategorie"-->
			<!--td colspan="2"><b>Ulteriori categorie</b></td-->
		<!--/gene-:-campoScheda-->
		<c:if test='${modo ne "NUOVO"}' >
			<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetCategorieFunction" parametro="${keyW9LOTT}" />
		</c:if>
		
		<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
			<jsp:param name="entita" value='W9LOTTCATE'/>
			<jsp:param name="chiave" value='${gene:getValCampo(keyW9LOTT, "CODGARA")};${gene:getValCampo(keyW9LOTT, "CODLOTT")}'/>
			<jsp:param name="nomeAttributoLista" value='listaCategorie' />
			<jsp:param name="idProtezioni" value="W9LOTTCATE" />
			<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9lott/ulteriori_categorie.jsp" />
			<jsp:param name="arrayCampi" value="'W9LOTTCATE_CODGARA_','W9LOTTCATE_CODLOTT_','W9LOTTCATE_NUM_CATE_','W9LOTTCATE_CATEGORIA_','W9LOTTCATE_CLASCAT_','W9LOTTCATE_SCORPORABILE_','W9LOTTCATE_SUBAPPALTABILE_'" />
			<jsp:param name="usaContatoreLista" value="true"/>
			<jsp:param name="sezioneListaVuota" value="false"/>
			<jsp:param name="titoloSezione" value="Ulteriori categorie" />
			<jsp:param name="titoloNuovaSezione" value="Nuova ulteriore categoria" />
			<jsp:param name="descEntitaVociLink" value="ulteriori categorie" />
			<jsp:param name="msgRaggiuntoMax" value="e ulteriori categorie" />
		</jsp:include>	

		<gene:campoScheda>
			<td colspan="2"><b>&nbsp;</b></td>
		</gene:campoScheda>
		<gene:archivio titolo="Comuni" lista="gene/commons/istat-comuni-lista-popup.jsp" scheda="" schedaPopUp=""
			campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3" chiave=""	where="" formName="" inseribile="false">
			<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true"
				definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia luogo di esecuzione del contratto"
				value="${descrProvinciaEsecuzione}" />
			<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true"
				definizione="T120;0;;;" title="Comune luogo di esecuzione del contratto"
				value="${denomComuneEsecuzione}" />
			<gene:campoScheda campo="LUOGO_ISTAT" title="Codice ISTAT del Comune luogo di esecuzione del contratto" />
		</gene:archivio>

		<gene:campoScheda campo="LUOGO_NUTS" href="#" speciale="true" >
			<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
		</gene:campoScheda>
		
		<gene:campoScheda campo="ID_TIPO_PRESTAZIONE" />
	</gene:gruppoCampi>
	
	<gene:fnJavaScriptScheda
		funzione='modifyProceduraNegoziata("#W9LOTT_ID_SCELTA_CONTRAENTE#","#W9LOTT_TIPO_CONTRATTO#","#W9LOTT_IMPORTO_TOT#","#W9LOTT_FLAG_ENTE_SPECIALE#")'
		elencocampi="W9LOTT_ID_SCELTA_CONTRAENTE;W9LOTT_TIPO_CONTRATTO;W9LOTT_IMPORTO_TOT;W9LOTT_FLAG_ENTE_SPECIALE" esegui="true" />
	
	<gene:fnJavaScriptScheda
		funzione='modifyClasseImportoCatPrevalente("#W9LOTT_ID_CATEGORIA_PREVALENTE#")'
		elencocampi="W9LOTT_ID_CATEGORIA_PREVALENTE" esegui="true" />
	<!--  gene:fnJavaScriptScheda
		funzione='bloccaCampo("#W9LOTT_COMCON#", 3)'
		elencocampi="W9LOTT_COMCON" esegui="true" / -->
	<gene:fnJavaScriptScheda
		funzione='bloccaCampo("#W9LOTT_TIPO_CONTRATTO#", 1)'
		elencocampi="W9LOTT_TIPO_CONTRATTO" esegui="false" />
	<gene:fnJavaScriptScheda
		funzione='bloccaCampo("#W9LOTT_TIPO_CONTRATTO#", 2)'
		elencocampi="W9LOTT_TIPO_CONTRATTO" esegui="true" />
	<gene:fnJavaScriptScheda funzione='modifyCUPESENTE("#W9LOTT_CUPESENTE#")'	elencocampi="W9LOTT_CUPESENTE" esegui="true" />
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	
</gene:formScheda>

	<form action="${pageContext.request.contextPath}/w9/GetIndicatori.do" method="post" name="formIndicatoriLotto">
		<input type="hidden" name="codiceCIG" value="" >
		<input type="hidden" name="tipoReportIndicatori" id="tipoReportIndicatori" value="" >
	</form>

<gene:javaScript>

	function modifyCUPESENTE(valore) {
		var vis = (valore == 1);
		if (vis) {
			setValue("W9LOTT_CUP","");
		}
		showObj("rowW9LOTT_CUP",!vis);
	}
	
	function modifyProceduraNegoziata(sceltaContraente, tipoContratto, importoTotale, flagEnteSpec) {
		var impTot =0;
		if (importoTotale !='') {
			impTot = parseFloat(importoTotale);
		}
		
		var visualizzaSceltaContraente = (sceltaContraente==4 || sceltaContraente==10);
		showObj("rowCondizioniRicorsoProceduraNegoziata", visualizzaSceltaContraente);
		
		if (visualizzaSceltaContraente) {
			var condLFS = true;
			var condL = true;
			var condF = true;
			var condS = true;
			var condImp = true;
			var condFlagEnt = true;
			//alert ("sceltaContraente:"+sceltaContraente+",tipoContratto:"+tipoContratto+", importoTotale:"+impTot+", flagEnteSpec:"+flagEnteSpec);
			
			showObj("rowCondizioniRicorsoProceduraNegoziata", true);
			for (var i=1; i<=${fn:length(datiTabellatoCondList)}; i++) {
				condL = true;
				condF = true;
				condS = true;
				condImp = true;
				condFlagEnt = true;
				
				if ('L' == tipoContratto) {
					if (i>=4 && i <=11) {
						condL = false;
					}
				} else if ('F' == tipoContratto) {
					if (i==1 || (i>=8 && i <=11) || i==(27-1) || i==(31-1)) {
						condF = false;
					}
				} else if ('S' == tipoContratto) {
					if (i == 1 || ( i>=4 && i <=7 ) || i==(27-1) || i==(31-1)) {
						condS = false;
					}
				}
				if (i==1 && impTot >= 1000000) {
					condImp = false;
				}
				if ('O' == flagEnteSpec) {
					if (i>=12 && i<=24 && i!=22) {
						condFlagEnt = false;
					}
				}
				
				// Se TIPO_CONTRATTO ='L' nascondere i valori       4, 5, 6, 7, 8, 9, 10, 11 
				// Se TIPO_CONTRATTO ='F' nascondere i valori    1,             8, 9, 10, 11, 27, 31 
				// Se TIPO_CONTRATTO ='S' nascondere i valori    1, 4, 5, 6, 7,               27, 31 
				// Se IMPORTO_TOT>= 1000000 nascondere il valore 1 
				// Se W9LOTT.FLAG_ENTE_SPECIALE='O' nascondere i valori 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24
				//alert(i+') LFS='+ condLFS+', condImp='+condImp+', condFlagEnt='+condFlagEnt);
				var visualizzaCondizione = (condL && condF && condS && condImp && condFlagEnt);
				showObj("rowW9COND_ID_CONDIZIONE_"+i, visualizzaCondizione);
				
				if (!visualizzaCondizione) {
					setValue("W9COND_ID_CONDIZIONE_" + i, "0");
				}
			}
		} else {
			for (var i=1; i<=${fn:length(datiTabellatoCondList)}; i++) {
				showObj("rowW9COND_ID_CONDIZIONE_"+i, false);
			}
			showObj("rowCondizioniRicorsoProceduraNegoziata", false);
		}
	}

	function modifyClasseImportoCatPrevalente(valore) {
		if (valore == "" || valore == 'FB' || valore == 'FS') {
			showObj("rowW9LOTT_CLASCAT", false);
			setValue("W9LOTT_CLASCAT", "");
		} else {
			showObj("rowW9LOTT_CLASCAT", true);
		}
	}
	
	function sommaCampi(valori)	{
	  var i, ret=0;
	  for (i=0; i < valori.length; i++) {
	    if(valori[i]!="")
	      ret += eval(valori[i]);
	  }
	  return eval(ret).toFixed(2);
	}

	function bloccaCampo(valore, campo) {
		if (campo==1) {
			var vis = (valore=="L" || valore=="CL");
			showObj("rowW9LOTT_CUP",vis);
			//showObj("rowTipLav",vis);
			if (!vis) {
				setValue("W9LOTT_CUP","");
				setValue("rowTipLav","");
				//setValue("W9LOTT_MANOD","");
			} else {
				setValue("W9LOTT_MANOD", 1);
			}
		}
	}
	
	function apriImportaDatiProgrammi(isNuovo) {
		if(isNuovo) {
			openPopUpCustom('href=w9/w9lott/ricerca_programmiPopup.jsp', 'formImportaProgrammi', 830, 300, 1, 1);
		} else {
			openPopUpCustom('href=w9/w9lott/ricerca_programmiPopup.jsp&keyLott=' + document.forms[0].key.value, 'formImportaProgrammi', 830, 300, 1, 1);
		}
	}
	
	function richiestaListaCUP() {
		openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=W9LOTT_CUP&codiceCUP=" + getValue("W9LOTT_CUP"), "listaCUP", 900, 550, 1, 1);
	}
	
	function controllaIMPORTO_TOT(fase_1, fase_11) {
		var importo_tot = getValue("W9LOTT_IMPORTO_TOT");

		if (importo_tot < 40000 && (fase_1 || fase_11)) {
			return false;
		}

		return true;
	}

	function controllaART_E1(fase_1) {
		var art_e1 = getValue("W9LOTT_ART_E1");
		if (art_e1 == '1' && fase_1) {
			return false;
		}
		return true;
	}
	
	function controllaART_E2(fase_984) {
		var art_e2 = getValue("W9LOTT_ART_E2");
		if (art_e2 == '1' && fase_984) {
			return false;
		}
		return true;
	}

	function riallineaSIMOG() {
		var codGara = getValue("W9LOTT_CODGARA");
		var codiceCIG = getValue("W9LOTT_CIG");
		openPopUpCustom('href=w9/w9lott/riallineaSimogPopup.jsp&codGara='+codGara+'&codiceCIG='+codiceCIG, 'formRiallineaSIMOG', 545, 320, 1, 1);
	}

	function riallineaIndiciSIMOG() {
		var codGara = getValue("W9LOTT_CODGARA");
		var codiceCIG = getValue("W9LOTT_CIG");
		openPopUpCustom('href=w9/w9lott/riallineaIndiciSimogPopup.jsp&codGara='+codGara+'&codiceCIG='+codiceCIG, 'formRiallineaIndiciSIMOG', 545, 280, 1, 1);
	}

	function popupValidazione(flusso,key1,key2,key3) {
		var comando = "href=w9/commons/popup-validazione-lotto.jsp";
		comando = comando + "&flusso=" + flusso;
		comando = comando + "&key1=" + key1;
		comando = comando + "&key2=" + key2;
		comando = comando + "&key3=" + key3;
		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
  }

<c:if test="${modo ne 'NUOVO'}" >
	function controllaTIPO_CONTRATTO(fase_10) {
		var tipo_contratto = getValue("W9LOTT_TIPO_CONTRATTO");
		if ((tipo_contratto != 'L' && tipo_contratto != 'CL') && fase_10) {
			return false;
		}
		return true;
	}

	function controllaMANOD(fase_994, fase_995, fase_998) {
		var manod = getValue("W9LOTT_MANOD");
		if (manod == '2' && (fase_994 || fase_995 || fase_998)) {
			return false;
		}
		return true;
	}
	
	function sceltaTipoReportIndicatori() {
		openPopUpCustom("href=w9/w9lott/popup-sceltaTipoReportIndicatori.jsp", "indicatori", 600, 265, "yes", "yes");
	}
	
	function indicatoriLotto() {
		var codiceCIG = getValue("W9LOTT_CIG");
		var tipo = document.formIndicatoriLotto.tipoReportIndicatori.value;
		if (codiceCIG != "" && tipo != "") {
			document.formIndicatoriLotto.codiceCIG.value = codiceCIG;
			document.formIndicatoriLotto.submit();
		}
	}
</c:if>

	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
		
		_creaFinestraAlberoCpvVP();

		_creaLinkAlberoCpvVP($("#W9LOTT_CPV").parent(), "VISUALIZZA", $("#W9LOTT_CPV"), $("#W9LOTT_CPVview"), $("#CPVDESC") );
		
		for (var i=1; i <= maxIdW9CPVVisualizzabile; i++) {
			var l1 = "#W9CPV_CPV_" + i;
			var l2 = "#W9CPV_CPV_" + i + "view";
			var l3 = "#TABCPV_CPVDESC_" + i;
			_creaLinkAlberoCpvVP($(l1).parent(), "VISUALIZZA", $(l1), $(l2), $(l3) );
		}
		
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
		_creaLinkAlberoNUTS($("#W9LOTT_LUOGO_NUTS").parent(), "${modo}", $("#W9LOTT_LUOGO_NUTS"), $("#W9LOTT_LUOGO_NUTSview") );

		$("input[name^='W9LOTT_LUOGO_NUTS']").attr('readonly','readonly');
		$("input[name^='W9LOTT_LUOGO_NUTS']").attr('tabindex','-1');
		$("input[name^='W9LOTT_LUOGO_NUTS']").css('border-width','1px');
		$("input[name^='W9LOTT_LUOGO_NUTS']").css('background-color','#E0E0E0');	
		
	});
	
</gene:javaScript>