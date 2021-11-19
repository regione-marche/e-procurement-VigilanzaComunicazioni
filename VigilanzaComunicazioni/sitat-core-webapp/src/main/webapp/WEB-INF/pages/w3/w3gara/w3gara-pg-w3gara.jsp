
<%
	/*
	 * Created on 20-Ott-2008
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

<gene:formScheda entita="W3GARA" gestisciProtezioni="true"
	gestore="it.eldasoft.sil.w3.tags.gestori.submit.GestoreW3GARA"
	plugin="it.eldasoft.sil.w3.tags.gestori.plugin.GestoreW3GARA">

	<c:set var="filtroUffint" value=""/>
	<c:set var="filtroUffintTecni" value=""/>  
	<c:if test="${!empty sessionScope.uffint}">
		<c:set var="filtroUffint" value=" w3aziendaufficio.azienda_cf = '${cfein}' and "/>
		<c:set var="filtroUffintTecni" value=" TECNI.CGENTEI = '${sessionScope.uffint}' and "/>
	</c:if>

	<c:set var="numgara" value='${gene:getValCampo(key,"NUMGARA")}' scope="request" />
	<c:set var="v3_04_6_Attiva" value='${gene:callFunction2("it.eldasoft.sil.w3.tags.funzioni.IsAttivaV3_04_6Function",pageContext,numgara)}' />
	<gene:campoScheda campo="ID_GARA" modificabile="false"/>
	<gene:campoScheda title="Stato della gara" campo="STATO_SIMOG" modificabile="false" defaultValue="1" />
	<gene:campoScheda title="Versione SIMOG" campo="VER_SIMOG" visibile="false" modificabile="false" defaultValue="4" />
	<gene:campoScheda campo="DATA_CREAZIONE" modificabile="false" />
	<gene:campoScheda campo="DATA_CONFERMA_GARA" modificabile="false" visibile="${datiRiga.W3GARA_STATO_SIMOG ne '1'}"/>
	<gene:campoScheda campo="DATA_CANCELLAZIONE_GARA" modificabile="false" visibile="${datiRiga.W3GARA_STATO_SIMOG eq '6'}"/>
	<gene:campoScheda campo="ID_MOTIVAZIONE" modificabile="false" visibile="${datiRiga.W3GARA_STATO_SIMOG eq '6'}"/>
	<gene:campoScheda campo="NOTE_CANC" modificabile="false" visibile="${datiRiga.W3GARA_STATO_SIMOG eq '6'}"/>
	<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${sessionScope.uffint}" />
	<gene:campoScheda>
		<td colspan="2"><b><br>SEZIONE I: RUP e COLLABORAZIONE (AMMINISTRAZIONE/STAZIONE APPALTANTE)<br><br></b></td>
	</gene:campoScheda>
	<gene:campoScheda>
		<td colspan="2"><b>I.1) Responsabile unico del procedimento</b></td>
	</gene:campoScheda>
	<gene:archivio titolo="Tecnici" inseribile="false"
		lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W3.W3GARA.RUP_CODTEC"),"gene/tecni/tecni-lista-popup.jsp","")}'
		scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
		where="${filtroUffintTecni} tecni.codtec in (select rup_codtec from w3usrsys where syscon= ${sessionScope.profiloUtente.id})"
		schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
		campi="TECNI.CODTEC;TECNI.NOMTEC;TECNI.CFTEC" chiave="W3GARA_RUP_CODTEC" >
		<gene:campoScheda campo="RUP_CODTEC" visibile="false" defaultValue="${rup_codtec}"/>
		<gene:campoScheda title="Denominazione" entita="TECNI" campo="NOMTEC" where="TECNI.CODTEC=W3GARA.RUP_CODTEC" defaultValue="${nomtec}"/>
		<gene:campoScheda title="Codice fiscale" entita="TECNI" campo="CFTEC" defaultValue="${cftec}"/>
	</gene:archivio>
	
	<gene:campoScheda>
		<td colspan="2"><b><br>I.2) Centri di Costo / Collaborazione associata al RUP</b></td>
	</gene:campoScheda>
	<gene:archivio titolo="Collaborazioni"
		lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W3.W3GARA.COLLABORAZIONI"),"w3/w3aziendaufficio/w3aziendaufficio-lista-popup.jsp","")}'
		scheda=""
		where="${filtroUffint} w3aziendaufficio.id in (select w3usrsyscoll.w3aziendaufficio_id from w3usrsyscoll, w3usrsys where w3usrsyscoll.syscon = w3usrsys.syscon and w3usrsyscoll.rup_codtec = '${datiRiga.W3GARA_RUP_CODTEC}' and w3usrsyscoll.syscon = ${sessionScope.profiloUtente.id})"
		schedaPopUp="w3/w3aziendaufficio/w3aziendaufficio-scheda-popup.jsp"
		campi="W3AZIENDAUFFICIO.ID;W3AZIENDAUFFICIO.UFFICIO_DENOM;W3AZIENDAUFFICIO.AZIENDA_CF;W3AZIENDAUFFICIO.AZIENDA_DENOM;W3AZIENDAUFFICIO.INDEXCOLL;" chiave="W3GARA_COLLABORAZIONE" >
		<gene:campoScheda campo="COLLABORAZIONE" visibile="false" defaultValue="${collaborazione}"/>
		<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="UFFICIO_DENOM" where="W3AZIENDAUFFICIO.ID=W3GARA.COLLABORAZIONE" defaultValue="${ufficio_denom}"/>
		<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="AZIENDA_CF" defaultValue="${azienda_cf}"/>
		<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="AZIENDA_DENOM" defaultValue="${azienda_denom}"/>
		<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="INDEXCOLL" defaultValue="${indexcoll}"/>
	</gene:archivio>
	
	<gene:campoScheda>
		<td colspan="2"><b><br>SEZIONE II: DATI DELLA GARA</b><br><br></td>
	</gene:campoScheda>
	<gene:campoScheda>
		<td colspan="2"><b>II.1) Dati generali</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="NUMGARA" visibile="false"/>
	<gene:campoScheda campo="OGGETTO" obbligatorio="true"/>
	<gene:campoScheda campo="TIPO_SCHEDA" obbligatorio="true"/>
	<gene:campoScheda campo="MODO_INDIZIONE" />
	<gene:campoScheda campo="ALLEGATO_IX" />
	<gene:campoScheda campo="MODO_REALIZZAZIONE" obbligatorio="true"/>
	<gene:campoScheda campo="DURATA_ACCQUADRO" visibile="${v3_04_6_Attiva eq 'false'}"/>
	<gene:campoScheda campo="STRUMENTO_SVOLGIMENTO" obbligatorio="true"/>
	<gene:campoScheda campo="NUMERO_LOTTI" modificabile="false" value="${numero_lotti}"/>
	<gene:campoScheda campo="IMPORTO_GARA" modificabile="false" value="${importo}"/>	
	<gene:campoScheda campo="URGENZA_DL133" obbligatorio="true" defaultValue="2"/>
	<gene:campoScheda campo="ESTREMA_URGENZA" />

	<gene:campoScheda>
		<td colspan="2"><b><br>II.2) Riferimento ad un accordo quadro</b></td>
	</gene:campoScheda>
	<gene:campoScheda campo="CIG_ACC_QUADRO" />
	
	<gene:campoScheda>
		<td colspan="2"><b><br>II.3) Esclusione AVCPASS</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="ESCLUSO_AVCPASS" obbligatorio="true"/>
	
	<gene:campoScheda>
		<td colspan="2"><b><br>II.4) Motivazioni per la richiesta CIG e categorie merceologiche</b></td>
	</gene:campoScheda>	
	<gene:campoScheda campo="M_RICH_CIG" />
	<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3GARAMERCFunction" parametro="${numgara}" />
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W3GARAMERC'/>
		<jsp:param name="chiave" value='${numgara}'/>
		<jsp:param name="nomeAttributoLista" value='datiW3GARAMERC' />
		<jsp:param name="idProtezioni" value="W3GARAMERC" />
		<jsp:param name="sezioneListaVuota" value="true" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3garamerc/w3garamerc-interno-scheda.jsp"/>
		<jsp:param name="arrayCampi" value="'W3GARAMERC_NUMGARA_','W3GARAMERC_NUMMERC_','W3GARAMERC_CATEGORIA_'"/>
		<jsp:param name="titoloSezione" value="<br>Categoria merceologica n. " />
		<jsp:param name="titoloNuovaSezione" value="<br>Nuova categoria merceologica" />
		<jsp:param name="descEntitaVociLink" value="categoria merceologica" />
		<jsp:param name="msgRaggiuntoMax" value="e categorie merceologiche"/>
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="numMaxDettagliInseribili" value="5"/>
		<jsp:param name="sezioneInseribile" value="true"/>
		<jsp:param name="sezioneEliminabile" value="true"/>
	</jsp:include>
	<gene:campoScheda>
		<td colspan="2"><b>&nbsp;</b></td>
	</gene:campoScheda>
	
	<gene:campoScheda campo="FLAG_SA_AGENTE_GARA" visibile="${datiRiga.W3GARA_VER_SIMOG eq 4}" defaultValue="2"/>
	<gene:campoScheda campo="ID_F_DELEGATE"/>
	<gene:campoScheda campo="CF_AMM_AGENTE_GARA"/>
	<gene:campoScheda campo="DEN_AMM_AGENTE_GARA"/>
	
	
	<gene:gruppoCampi idProtezioni="AD" visibile="${datiRiga.W3GARA_STATO_SIMOG!=null && datiRiga.W3GARA_STATO_SIMOG ge '2'}">
		<gene:campoScheda>
			<td colspan="2"><b><br>II.5) Altri dati</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="PROVV_PRESA_CARICO"  modificabile="false"/>
	</gene:gruppoCampi>
	
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<c:if test="${datiRiga.W3GARA_STATO_SIMOG eq '5' or datiRiga.W3GARA_STATO_SIMOG eq '6' or datiRiga.W3GARA_STATO_SIMOG eq '7'}">
		<gene:redefineInsert name="pulsanteModifica" />
		<gene:redefineInsert name="schedaModifica" />
	</c:if>
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>

	<gene:fnJavaScriptScheda funzione="gestioneMODO_REALIZZAZIONE('#W3GARA_MODO_REALIZZAZIONE#')" elencocampi="W3GARA_MODO_REALIZZAZIONE" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneTIPO_SCHEDA('#W3GARA_TIPO_SCHEDA#')" elencocampi="W3GARA_TIPO_SCHEDA" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneURGENZA_DL133('#W3GARA_URGENZA_DL133#')" elencocampi="W3GARA_URGENZA_DL133" esegui="true" />
	<gene:fnJavaScriptScheda funzione="gestioneRUP_CODTEC('#W3GARA_RUP_CODTEC#')" elencocampi="W3GARA_RUP_CODTEC" esegui="false" />
	<gene:fnJavaScriptScheda funzione="gestioneFLAG_SA_AGENTE_GARA('#W3GARA_FLAG_SA_AGENTE_GARA#')" elencocampi="W3GARA_FLAG_SA_AGENTE_GARA" esegui="true" />
	
</gene:formScheda>

<gene:redefineInsert name="addToDocumenti" >
	<jsp:include page="/WEB-INF/pages/w3/commons/addtodocumenti-idgaracig.jsp" >
		<jsp:param name="entita" value='W3GARA'/>
	</jsp:include>
</gene:redefineInsert>

<gene:javaScript>

	<c:if test='${modo eq "NUOVO"}' > 	
		$("[id^='W3GARAMERC_CATEGORIA_1']").val("999");		
		$("#W3GARAMERC_CATEGORIA_1").change();
	</c:if>
	
	function gestioneMODO_REALIZZAZIONE(value){
		if(value==2 || value==11){
			document.getElementById("rowW3GARA_CIG_ACC_QUADRO").style.display = '';
		} else {
			document.getElementById("rowW3GARA_CIG_ACC_QUADRO").style.display = 'none';
			document.forms[0].W3GARA_CIG_ACC_QUADRO.value='';
		}
	}
	
	function gestioneTIPO_SCHEDA(value){
		document.getElementById("rowW3GARA_MODO_INDIZIONE").style.display = (value == 'S' ? '':'none');
		if(value!='S'){
			document.forms[0].W3GARA_MODO_INDIZIONE.value='';
		}
	}
	
	function gestioneURGENZA_DL133(value){
		document.getElementById("rowW3GARA_ESTREMA_URGENZA").style.display = (value == '1' ? '':'none');
		if(value!='1'){
			document.forms[0].W3GARA_ESTREMA_URGENZA.value='';
		}
	}
	
	function gestioneRUP_CODTEC(value) {
		
		if (isValueChanged("W3GARA_RUP_CODTEC")) {
			document.forms[0].W3GARA_COLLABORAZIONE.value = '';
			document.forms[0].W3AZIENDAUFFICIO_UFFICIO_DENOM.value = '';
			document.forms[0].W3AZIENDAUFFICIO_AZIENDA_CF.value = '';
			document.forms[0].W3AZIENDAUFFICIO_AZIENDA_DENOM.value = '';
			document.forms[0].W3AZIENDAUFFICIO_INDEXCOLL.value = '';
		}
		
		var _input = $("input[value='w3/w3aziendaufficio/w3aziendaufficio-lista-popup.jsp']");
		var _sql = "${filtroUffint} w3aziendaufficio.id in (select w3usrsyscoll.w3aziendaufficio_id from w3usrsyscoll, w3usrsys where w3usrsyscoll.syscon = w3usrsys.syscon and w3usrsyscoll.rup_codtec = '" + value + "' and w3usrsyscoll.syscon = ${sessionScope.profiloUtente.id})";
		_input.parent().find("input[name='archWhereLista']").val(_sql);

	}

	function gestioneFLAG_SA_AGENTE_GARA(value){
		if(value=='1'){
			document.getElementById("rowW3GARA_ID_F_DELEGATE").style.display = '';
			document.getElementById("rowW3GARA_CF_AMM_AGENTE_GARA").style.display = '';
			document.getElementById("rowW3GARA_DEN_AMM_AGENTE_GARA").style.display = '';
		} else {
			document.getElementById("rowW3GARA_ID_F_DELEGATE").style.display = 'none';
			document.getElementById("rowW3GARA_CF_AMM_AGENTE_GARA").style.display = 'none';
			document.getElementById("rowW3GARA_DEN_AMM_AGENTE_GARA").style.display = 'none';
			document.forms[0].W3GARA_ID_F_DELEGATE.value='';
			document.forms[0].W3GARA_CF_AMM_AGENTE_GARA.value='';
			document.forms[0].W3GARA_DEN_AMM_AGENTE_GARA.value='';
		}
	}
	
	modifySelectOption($("#W3GARA_MODO_REALIZZAZIONE"));
	modifySelectOption($("#W3GARA_M_RICH_CIG"));
	
	function modifySelectOption(obj) {
		var obj_opt = obj.find("option");
		$.each(obj_opt, function( key, opt ) {
			var _title = opt.title;
			if (_title.length > 110) {
				 _title = _title.substring(0,110) + "...";
			}
			opt.text = _title;
		});
		obj.css("width","550px");	
	}
	
	
	
</gene:javaScript>
