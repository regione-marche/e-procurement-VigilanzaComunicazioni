<%
  /*
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

<c:if test='${modo eq "NUOVO"}'>
	<c:set var="contri"	value='${gene:getValCampo(param.chiave,"CONTRI")}' scope="request" />
	<c:set var="conint"	value='${gene:getValCampo(param.chiave,"CONINT")}' scope="request" />
	<c:set var="codgara" value='${gene:getValCampo(param.chiave,"CODGARA")}' scope="request" />
	<c:set var="codlott" value='${gene:getValCampo(param.chiave,"CODLOTT")}' scope="request" />
</c:if>

<gene:formScheda entita="CUPDATI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreCUPDATI">

	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<gene:campoScheda campo="ID" visibile="false" />
	<gene:campoScheda campo="NATURA" visibile="false"/>
	<gene:campoScheda campo="TIPOLOGIA" visibile="false"/>
	<gene:campoScheda campo="SETTORE" visibile="false"/>
	<gene:campoScheda campo="SOTTOSETTORE" visibile="false"/>
	<gene:campoScheda campo="CATEGORIA" visibile="false"/>
	<gene:campoScheda campo="CUMULATIVO" visibile="false"/>
	<gene:campoScheda>
		<td colspan="2"><b>Descrizione del progetto</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="BENEFICIARIO" />
	<gene:campoScheda campo="NOME_STRU_INFRASTR" />
	<gene:campoScheda campo="STR_INFRASTR_UNICA" />
	<gene:campoScheda campo="DENOM_IMPR_STAB" />
	<gene:campoScheda campo="RAGIONE_SOCIALE" />
	<gene:campoScheda campo="PARTITA_IVA" />
	<gene:campoScheda campo="DENOM_IMPR_STAB_PREC" />
	<gene:campoScheda campo="RAGIONE_SOCIALE_PREC" />	
	<gene:campoScheda campo="DENOMINAZIONE_PROGETTO" />
	<gene:campoScheda campo="ENTE" />		
	<gene:campoScheda campo="ATTO_AMMINISTR" />
	<gene:campoScheda campo="SCOPO_INTERVENTO" />
	<gene:campoScheda campo="TIPO_IND_AREA_RIFER" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoCU006"/>
	<gene:campoScheda campo="IND_AREA_RIFER" />
	<gene:campoScheda campo="DESCRIZIONE_INTERVENTO" />
	<gene:campoScheda campo="OBIETT_CORSO" />
	<gene:campoScheda campo="MOD_INTERVENTO_FREQUENZA" />
	<gene:campoScheda campo="SERVIZIO" />
	<gene:campoScheda campo="BENE" />
	<gene:campoScheda campo="FINALITA" >
		<gene:addValue value="" descr="" />
		<gene:addValue value="01" descr="NUOVE MODALITA DI FORNITURA DI SERVIZI AGLI UTENTI" />
		<gene:addValue value="02" descr="INCREMENTO DELLA OFFERTA DI SERVIZI AGLI UTENTI" />
		<gene:addValue value="03" descr="ALTRO" />	
	</gene:campoScheda>
	<gene:campoScheda campo="STRUM_PROGR" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoCU007"/>
	<gene:campoScheda campo="DESC_STRUM_PROGR" />
	<gene:campoScheda campo="ALTRE_INFORMAZIONI" />
	<gene:campoScheda campo="FLAGLEGGEOBIETTIVO" />
	<gene:campoScheda campo="ANNODELIBERA" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoAnnoDecisione"/>
	<gene:campoScheda campo="NUMDELIBERACIPE" />
	
	<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPATECO2007Function" />
	
	<gene:campoScheda nome="ATECO_2007">
		<td colspan="2"><b>Attività economica beneficiario</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="ATECO_SEZIONE" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test='${!empty listaValoriSezione}'>
			<c:forEach items="${listaValoriSezione}" var="valoriSezione">
				<gene:addValue value="${valoriSezione[0]}"
					descr="${valoriSezione[3]}" />
			</c:forEach>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="ATECO_DIVISIONE" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_ATECO_SEZIONE}">
			<c:if test='${!empty listaValoriDivisione}'>
				<c:forEach items="${listaValoriDivisione}" var="valoriDivisione">
					<c:if test="${valoriDivisione[2] eq datiRiga.CUPDATI_ATECO_SEZIONE}">
						<gene:addValue value="${valoriDivisione[0]}"
							descr="${valoriDivisione[3]}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="ATECO_GRUPPO" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_ATECO_DIVISIONE}">
			<c:if test='${!empty listaValoriGruppo}'>
				<c:set var="gruppo" value="${datiRiga.CUPDATI_ATECO_SEZIONE}.${datiRiga.CUPDATI_ATECO_DIVISIONE}"/>
				<c:forEach items="${listaValoriGruppo}" var="valoriGruppo">
					<c:if test="${valoriGruppo[2] eq gruppo}">
						<gene:addValue value="${valoriGruppo[0]}"
							descr="${valoriGruppo[3]}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="ATECO_CLASSE" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_ATECO_GRUPPO}">
			<c:if test='${!empty listaValoriClasse}'>
				<c:set var="classe" value="${datiRiga.CUPDATI_ATECO_SEZIONE}.${datiRiga.CUPDATI_ATECO_GRUPPO}"/>
				<c:forEach items="${listaValoriClasse}" var="valoriClasse">
					<c:if test="${valoriClasse[2] eq classe}">
						<gene:addValue value="${valoriClasse[0]}"
							descr="${valoriClasse[3]}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="ATECO_CATEGORIA" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_ATECO_CLASSE}">
			<c:if test='${!empty listaValoriCategoria}'>
				<c:set var="categoria" value="${datiRiga.CUPDATI_ATECO_SEZIONE}.${datiRiga.CUPDATI_ATECO_CLASSE}"/>
				<c:forEach items="${listaValoriCategoria}" var="valoriCategoria">
					<c:if test="${valoriCategoria[2] eq categoria}">
						<gene:addValue value="${valoriCategoria[0]}"
							descr="${valoriCategoria[3]}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:campoScheda campo="ATECO_SOTTOCATEGORIA" obbligatorio="true">
		<gene:addValue value="" descr="" />
		<c:if test="${!empty datiRiga.CUPDATI_ATECO_CATEGORIA}">
			<c:if test='${!empty listaValoriSottoCategoria}'>
				<c:set var="sotto" value="${datiRiga.CUPDATI_ATECO_SEZIONE}.${datiRiga.CUPDATI_ATECO_CATEGORIA}"/>
				<c:forEach items="${listaValoriSottoCategoria}" var="valoriSottoCategoria">
					<c:if test="${valoriSottoCategoria[2] eq sotto}">
						<gene:addValue value="${valoriSottoCategoria[0]}"
							descr="${valoriSottoCategoria[3]}" />
					</c:if>
				</c:forEach>
			</c:if>
		</c:if>
	</gene:campoScheda>
	
	<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetCUPLOCFunction" parametro='${gene:getValCampo(key,"ID")}' />	
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='CUPLOC'/>
		<jsp:param name="chiave" value='${gene:getValCampo(key, "ID")}'/>
		<jsp:param name="nomeAttributoLista" value='datiCUPLOC' />
		<jsp:param name="idProtezioni" value="CUPLOC" />
		<jsp:param name="sezioneListaVuota" value="true" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/piani/cuploc/cuploc-interno-scheda.jsp"/>
		<jsp:param name="arrayCampi" value="'CUPLOC_ID_', 'CUPLOC_NUM_', 'CUPLOC_STATO_', 'CUPLOC_REGIONE_', 'CUPLOC_PROVINCIA_', 'CUPLOC_COMUNE_', 'STATO_DESC_', 'REGIONE_DESC_', 'PROVINCIA_DESC_', 'COMUNE_DESC_'"/>
		<jsp:param name="titoloSezione" value="Localizzazione" />
		<jsp:param name="titoloNuovaSezione" value="Nuova localizzazione" />
		<jsp:param name="descEntitaVociLink" value="localizzazione" />
		<jsp:param name="msgRaggiuntoMax" value="e localizzazioni"/>
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="numMaxDettagliInseribili" value="5"/>
	</jsp:include>



	<gene:fnJavaScriptScheda funzione='gestioneDescrizione("#CUPDATI_NATURA#","#CUPDATI_TIPOLOGIA#","#CUPDATI_CUMULATIVO#")' elencocampi="CUPDATI_NATURA;CUPDATI_TIPOLOGIA;CUPDATI_CUMULATIVO" esegui="true" />	
	<gene:fnJavaScriptScheda funzione='gestioneStrumento("#CUPDATI_STRUM_PROGR#","#CUPDATI_CUMULATIVO#")' elencocampi="CUPDATI_STRUM_PROGR" esegui="true" />
	<gene:fnJavaScriptScheda funzione='modificaATECO_SEZIONE("#CUPDATI_ATECO_SEZIONE#")' elencocampi="CUPDATI_ATECO_SEZIONE" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaATECO_DIVISIONE("#CUPDATI_ATECO_SEZIONE#","#CUPDATI_ATECO_DIVISIONE#")' elencocampi="CUPDATI_ATECO_SEZIONE;CUPDATI_ATECO_DIVISIONE" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaATECO_GRUPPO("#CUPDATI_ATECO_SEZIONE#","#CUPDATI_ATECO_GRUPPO#")' elencocampi="CUPDATI_ATECO_SEZIONE;CUPDATI_ATECO_GRUPPO" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaATECO_CLASSE("#CUPDATI_ATECO_SEZIONE#","#CUPDATI_ATECO_CLASSE#")' elencocampi="CUPDATI_ATECO_SEZIONE;CUPDATI_ATECO_CLASSE" esegui="false" />
	<gene:fnJavaScriptScheda funzione='modificaATECO_CATEGORIA("#CUPDATI_ATECO_SEZIONE#","#CUPDATI_ATECO_CATEGORIA#")' elencocampi="CUPDATI_ATECO_SEZIONE;CUPDATI_ATECO_CATEGORIA" esegui="false" />
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>

<gene:javaScript>

	var arrayAtecoDivisione = new Array();
	var arrayAtecoGruppo = new Array();
	var arrayAtecoClasse = new Array();
	var arrayAtecoCategoria = new Array();
	var arrayAtecoSottoCategoria = new Array();

	<c:forEach items="${listaValoriDivisione}" var="valoreDivisione">
		arrayAtecoDivisione.push(new tabellatoAteco(${gene:string4Js(valoreDivisione[0])}, ${gene:string4Js(valoreDivisione[1])},${gene:string4Js(valoreDivisione[2])},${gene:string4Js(valoreDivisione[3])}));
	</c:forEach>
	
	<c:forEach items="${listaValoriGruppo}" var="valoreGruppo">
		arrayAtecoGruppo.push(new tabellatoAteco(${gene:string4Js(valoreGruppo[0])}, ${gene:string4Js(valoreGruppo[1])},${gene:string4Js(valoreGruppo[2])},${gene:string4Js(valoreGruppo[3])}));
	</c:forEach>
	
	<c:forEach items="${listaValoriClasse}" var="valoreClasse">
		arrayAtecoClasse.push(new tabellatoAteco(${gene:string4Js(valoreClasse[0])}, ${gene:string4Js(valoreClasse[1])},${gene:string4Js(valoreClasse[2])},${gene:string4Js(valoreClasse[3])}));
	</c:forEach>
	
	<c:forEach items="${listaValoriCategoria}" var="valoreCategoria">
		arrayAtecoCategoria.push(new tabellatoAteco(${gene:string4Js(valoreCategoria[0])}, ${gene:string4Js(valoreCategoria[1])},${gene:string4Js(valoreCategoria[2])},${gene:string4Js(valoreCategoria[3])}));
	</c:forEach>
	
	<c:forEach items="${listaValoriSottoCategoria}" var="valoreSottoCategoria">
		arrayAtecoSottoCategoria.push(new tabellatoAteco(${gene:string4Js(valoreSottoCategoria[0])}, ${gene:string4Js(valoreSottoCategoria[1])},${gene:string4Js(valoreSottoCategoria[2])},${gene:string4Js(valoreSottoCategoria[3])}));
	</c:forEach>
	
	function tabellatoAteco(aCupcod1, aCupcod2, aCupcod3, aCupDesc ) {
		var cupcod1;
		var cupcod2;
		var cupcod3;
		var cupdesc;
		this.cupcod1 = aCupcod1;
		this.cupcod2 = aCupcod2;
		this.cupcod3 = aCupcod3;
		this.cupdesc = aCupDesc;
	}
	
	function modificaATECO_SEZIONE(valoreSezione){
	
		setValue("CUPDATI_ATECO_DIVISIONE","");
		setValue("CUPDATI_ATECO_GRUPPO","");
		setValue("CUPDATI_ATECO_CLASSE","");
		setValue("CUPDATI_ATECO_CATEGORIA","");
		setValue("CUPDATI_ATECO_SOTTOCATEGORIA","");
		
		var i;
		var optDIVISIONE=document.forms[0].CUPDATI_ATECO_DIVISIONE.options;
		optDIVISIONE.length = 0;
		optDIVISIONE.add(new Option("", ""));
		for(i=0; i < arrayAtecoDivisione.length ;i++){
			if (arrayAtecoDivisione[i].cupcod3 == valoreSezione) {
				optDIVISIONE.add(new Option(arrayAtecoDivisione[i].cupdesc, arrayAtecoDivisione[i].cupcod1));
			}
		}
	}
	
	function modificaATECO_DIVISIONE(valoreSezione, valoreDivisione){
	
		setValue("CUPDATI_ATECO_GRUPPO","");
		setValue("CUPDATI_ATECO_CLASSE","");
		setValue("CUPDATI_ATECO_CATEGORIA","");
		setValue("CUPDATI_ATECO_SOTTOCATEGORIA","");
		
		var i;
		var optGRUPPO=document.forms[0].CUPDATI_ATECO_GRUPPO.options;
		optGRUPPO.length = 0;
		optGRUPPO.add(new Option("", ""));
		for(i=0; i < arrayAtecoGruppo.length ;i++){
			if (arrayAtecoGruppo[i].cupcod3 == valoreSezione + "." + valoreDivisione) {
				optGRUPPO.add(new Option(arrayAtecoGruppo[i].cupdesc, arrayAtecoGruppo[i].cupcod1));
			}
		}
	}
	
	function modificaATECO_GRUPPO(valoreSezione, valoreGruppo){
	
		setValue("CUPDATI_ATECO_CLASSE","");
		setValue("CUPDATI_ATECO_CATEGORIA","");
		setValue("CUPDATI_ATECO_SOTTOCATEGORIA","");
		
		var i;
		var optCLASSE=document.forms[0].CUPDATI_ATECO_CLASSE.options;
		optCLASSE.length = 0;
		optCLASSE.add(new Option("", ""));
		for(i=0; i < arrayAtecoClasse.length ;i++){
			if (arrayAtecoClasse[i].cupcod3 == valoreSezione + "." + valoreGruppo) {
				optCLASSE.add(new Option(arrayAtecoClasse[i].cupdesc, arrayAtecoClasse[i].cupcod1));
			}
		}
	}
	
	function modificaATECO_CLASSE(valoreSezione, valoreClasse){
	
		setValue("CUPDATI_ATECO_CATEGORIA","");
		setValue("CUPDATI_ATECO_SOTTOCATEGORIA","");
		
		var i;
		var optCATEGORIA=document.forms[0].CUPDATI_ATECO_CATEGORIA.options;
		optCATEGORIA.length = 0;
		optCATEGORIA.add(new Option("", ""));
		for(i=0; i < arrayAtecoCategoria.length ;i++){
			if (arrayAtecoCategoria[i].cupcod3 == valoreSezione + "." + valoreClasse) {
				optCATEGORIA.add(new Option(arrayAtecoCategoria[i].cupdesc, arrayAtecoCategoria[i].cupcod1));
			}
		}
	}
	
	function modificaATECO_CATEGORIA(valoreSezione, valoreCategoria){
	
		setValue("CUPDATI_ATECO_SOTTOCATEGORIA","");
		
		var i;
		var optSOTTOCATEGORIA=document.forms[0].CUPDATI_ATECO_SOTTOCATEGORIA.options;
		optSOTTOCATEGORIA.length = 0;
		optSOTTOCATEGORIA.add(new Option("", ""));
		for(i=0; i < arrayAtecoSottoCategoria.length ;i++){
			if (arrayAtecoSottoCategoria[i].cupcod3 == valoreSezione + "." + valoreCategoria) {
				optSOTTOCATEGORIA.add(new Option(arrayAtecoSottoCategoria[i].cupdesc, arrayAtecoSottoCategoria[i].cupcod1));
			}
		}
	}
	
	function formLocalizzazione(contatore, stato, regione, provincia, comune){
		var href = "href=piani/cuploc/cuploc-popup-localizzazione.jsp&modo=MODIFICA";
		href = href + "&contatore=" + contatore;
		href = href + "&stato=" + getValue(stato);
		href = href + "&regione=" + getValue(regione);
		href = href + "&provincia=" + getValue(provincia);
		href = href + "&comune=" + getValue(comune);
		openPopUpCustom(href, "formLocalizzazione", 700, 300, 1, 1);
	}

	function gestioneStrumento(valoreStrumento, valoreCumulativo) {
		if (valoreCumulativo != '1') {
			if (valoreStrumento == '00') {
				showObj("rowCUPDATI_DESC_STRUM_PROGR", false);	
				setValue("CUPDATI_DESC_STRUM_PROGR", "");	
			} else {
				showObj("rowCUPDATI_DESC_STRUM_PROGR", true);	
			}
		}
	}

	function gestioneDescrizione(valoreNatura, valoreTipologia, valoreCumulativo) {
		
		var visNOME_STRU_INFRASTR = false;
		var visSTR_INFRASTR_UNICA = false;
		var visTIPO_IND_AREA_RIFER = true;
		var visIND_AREA_RIFER = true;
		var visDESCRIZIONE_INTERVENTO = false;
		var visSTRUM_PROGR = true;	
		var visDESC_STRUM_PROGR = true;
		var visDENOM_IMPR_STAB = false;
		var visPARTITA_IVA = false;
		var visDENOM_IMPR_STAB_PREC = false;
		var visDENOMINAZIONE_PROGETTO = false;
		var visENTE = false;
		var visOBIETT_CORSO = false;
		var visMOD_INTERVENTO_FREQUENZA = false;
		var visSERVIZIO = false;
		var visBENE = false;
		var visRAGIONE_SOCIALE_PREC = false;
		var visFINALITA = false;	
		var visRAGIONE_SOCIALE = false;
		var visBENEFICIARIO = false;
		var visATTO_AMMINISTR = false;
		var visSCOPO_INTERVENTO = false;
		var visATECO_2007 = false;

		if (valoreCumulativo == '1') {
			visATTO_AMMINISTR = true;
			visSCOPO_INTERVENTO = true;
			visTIPO_IND_AREA_RIFER = false;
			visIND_AREA_RIFER = false;
			visSTRUM_PROGR = false;	
			visDESC_STRUM_PROGR = false;
			visATECO_2007 = true;
			
		} else {
		if (valoreNatura == '03') {
				visNOME_STRU_INFRASTR = true;
				visSTR_INFRASTR_UNICA = true;
				visDESCRIZIONE_INTERVENTO = true;
			}
			
			if (valoreNatura == '07') {
				visDENOM_IMPR_STAB = true;
				visPARTITA_IVA = true;
				visDENOM_IMPR_STAB_PREC = true;
				visDESCRIZIONE_INTERVENTO = true;
				visATECO_2007 = true;
			}
			
			
			if (valoreNatura == '02') {
				if (valoreTipologia != "") {
					if (valoreTipologia == '14') {
						visDENOMINAZIONE_PROGETTO = true;
						visENTE = true;
						visDESCRIZIONE_INTERVENTO = true;
					} 
					if (valoreTipologia == '12') {
						visDENOMINAZIONE_PROGETTO = true;
						visENTE = true;
						visOBIETT_CORSO = true;
						visMOD_INTERVENTO_FREQUENZA = true;
					}
					if (valoreTipologia != '12' && valoreTipologia != '14') {
						visNOME_STRU_INFRASTR = true;
						visSERVIZIO = true;
					}					
				}
			}
			
			if (valoreNatura == '01') {
				visNOME_STRU_INFRASTR = true;
				visBENE = true;
			}	
			
			if (valoreNatura == '08') {
				visRAGIONE_SOCIALE = true;
				visPARTITA_IVA = true;
				visRAGIONE_SOCIALE_PREC = true;
				visFINALITA = true;
				visATECO_2007 = true;
			}
			
			if (valoreNatura == '06') {
				visBENEFICIARIO = true;
				visPARTITA_IVA = true;
				visNOME_STRU_INFRASTR = true;
				visDESCRIZIONE_INTERVENTO = true;
				visATECO_2007 = true;
			}
		
		}
		
		
			
		showObj("rowATECO_2007", visATECO_2007);
		showObj("rowCUPDATI_ATECO_SEZIONE", visATECO_2007);
		showObj("rowCUPDATI_ATECO_DIVISIONE", visATECO_2007);
		showObj("rowCUPDATI_ATECO_GRUPPO", visATECO_2007);
		showObj("rowCUPDATI_ATECO_CLASSE", visATECO_2007);
		showObj("rowCUPDATI_ATECO_CATEGORIA", visATECO_2007);
		showObj("rowCUPDATI_ATECO_SOTTOCATEGORIA", visATECO_2007);
		
		showObj("rowCUPDATI_NOME_STRU_INFRASTR", visNOME_STRU_INFRASTR);
		showObj("rowCUPDATI_STR_INFRASTR_UNICA", visSTR_INFRASTR_UNICA);
		showObj("rowCUPDATI_TIPO_IND_AREA_RIFER", visTIPO_IND_AREA_RIFER);
		showObj("rowCUPDATI_IND_AREA_RIFER", visIND_AREA_RIFER);
		showObj("rowCUPDATI_DESCRIZIONE_INTERVENTO",visDESCRIZIONE_INTERVENTO);
		showObj("rowCUPDATI_STRUM_PROGR", visSTRUM_PROGR);
		showObj("rowCUPDATI_DESC_STRUM_PROGR", visDESC_STRUM_PROGR);		
		showObj("rowCUPDATI_DENOM_IMPR_STAB", visDENOM_IMPR_STAB);
		showObj("rowCUPDATI_PARTITA_IVA", visPARTITA_IVA);
		showObj("rowCUPDATI_DENOM_IMPR_STAB_PREC", visDENOM_IMPR_STAB_PREC);
		showObj("rowCUPDATI_DENOMINAZIONE_PROGETTO",visDENOMINAZIONE_PROGETTO);
		showObj("rowCUPDATI_ENTE",visENTE);
		showObj("rowCUPDATI_OBIETT_CORSO",visOBIETT_CORSO);
		showObj("rowCUPDATI_MOD_INTERVENTO_FREQUENZA",visMOD_INTERVENTO_FREQUENZA);
		showObj("rowCUPDATI_SERVIZIO",visSERVIZIO);
		showObj("rowCUPDATI_BENE",visBENE);
		showObj("rowCUPDATI_RAGIONE_SOCIALE",visRAGIONE_SOCIALE);
		showObj("rowCUPDATI_RAGIONE_SOCIALE_PREC",visRAGIONE_SOCIALE_PREC);
		showObj("rowCUPDATI_FINALITA",visFINALITA);
		showObj("rowCUPDATI_BENEFICIARIO",visBENEFICIARIO);		
		showObj("rowCUPDATI_ATTO_AMMINISTR",visATTO_AMMINISTR);		
		showObj("rowCUPDATI_SCOPO_INTERVENTO",visSCOPO_INTERVENTO);
		
		if (visNOME_STRU_INFRASTR == false)
			setValue("CUPDATI_NOME_STRU_INFRASTR", "");	

		if (visSTR_INFRASTR_UNICA == false) 
			setValue("CUPDATI_STR_INFRASTR_UNICA", "");	

		if (visTIPO_IND_AREA_RIFER == false)
			setValue("CUPDATI_TIPO_IND_AREA_RIFER", "");	
			
		if (visIND_AREA_RIFER == false)
			setValue("CUPDATI_IND_AREA_RIFER", "");	
			
		if (visDESCRIZIONE_INTERVENTO == false)
			setValue("CUPDATI_DESCRIZIONE_INTERVENTO", "");	

		if (visSTRUM_PROGR == false)
			setValue("CUPDATI_STRUM_PROGR", "");
			
		if (visDESC_STRUM_PROGR == false)
			setValue("CUPDATI_DESC_STRUM_PROGR", "");

		if (visDENOM_IMPR_STAB == false)
			setValue("CUPDATI_DENOM_IMPR_STAB", "");	

		if (visPARTITA_IVA == false)
			setValue("CUPDATI_PARTITA_IVA", "");	
		
		if (visDENOM_IMPR_STAB_PREC == false)
			setValue("CUPDATI_DENOM_IMPR_STAB_PREC", "");	
			
		if (visDENOMINAZIONE_PROGETTO == false)
			setValue("CUPDATI_DENOMINAZIONE_PROGETTO", "");

		if (visENTE == false)
			setValue("CUPDATI_ENTE", "");						

		if (visOBIETT_CORSO == false)
			setValue("CUPDATI_OBIETT_CORSO", "");	
			
		if (visMOD_INTERVENTO_FREQUENZA == false)
			setValue("CUPDATI_MOD_INTERVENTO_FREQUENZA", "");	

		if (visSERVIZIO == false)
			setValue("CUPDATI_SERVIZIO", "");

		if (visBENE == false)
			setValue("CUPDATI_BENE", "");
		
		if (visRAGIONE_SOCIALE == false)
			setValue("CUPDATI_RAGIONE_SOCIALE", "");
			
		if (visRAGIONE_SOCIALE_PREC == false)
			setValue("CUPDATI_RAGIONE_SOCIALE_PREC", "");			

		if (visFINALITA == false)
			setValue("CUPDATI_FINALITA", "");
			
		if (visBENEFICIARIO == false)
			setValue("CUPDATI_BENEFICIARIO", "");
			
		if (visDESCRIZIONE_INTERVENTO == false)
			setValue("CUPDATI_DESCRIZIONE_INTERVENTO", "");						

		if (visATTO_AMMINISTR == false)
			setValue("CUPDATI_ATTO_AMMINISTR", "");		

		if (visSCOPO_INTERVENTO == false)
			setValue("CUPDATI_SCOPO_INTERVENTO", "");					
	
	}
	
</gene:javaScript>

