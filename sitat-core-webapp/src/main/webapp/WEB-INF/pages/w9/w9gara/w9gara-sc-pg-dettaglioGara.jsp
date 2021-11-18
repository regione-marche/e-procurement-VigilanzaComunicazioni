
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

<%
	/* Dati generali del lavoro */
%>

<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
<c:set var="isIntegrazioneWsCUP" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.IsIntegrazioneWsCUPFunction",  pageContext)}' />

<gene:formScheda entita="W9GARA" gestisciProtezioni="false" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9GARASC"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARASC">
	<input type="hidden" name="smartcig" value="si" >
	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.cpvvp.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
	</gene:redefineInsert>
	
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali della Gara </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODGARA" visibile="false" />
		<gene:campoScheda entita="W9LOTT" campo="CODGARA" where="W9LOTT.CODGARA=W9GARA.CODGARA and W9LOTT.CODLOTT=1" visibile="false" />
		<gene:campoScheda entita="W9LOTT" campo="CODLOTT" defaultValue="1" where="W9LOTT.CODGARA=W9GARA.CODGARA and W9LOTT.CODLOTT=1" visibile="false" />
		<gene:campoScheda entita="W9LOTT" campo="DAEXPORT" defaultValue='1'	visibile="false" />
		<gene:campoScheda entita="W9LOTT" campo="CIG" title="Codice SmartCIG" modificabile="${modoAperturaScheda eq 'NUOVO'}" obbligatorio="true"/>
		<gene:campoScheda entita="W9LOTT" campo="OGGETTO" title="Oggetto" obbligatorio="true" />
		<gene:campoScheda entita="W9LOTT" campo="NLOTTO" visibile="false" defaultValue="1"/>
		<gene:campoScheda entita="W9LOTT" campo="TIPO_CONTRATTO" obbligatorio="true" />
		<gene:campoScheda entita="W9LOTT" campo="ID_SCELTA_CONTRAENTE" />
		<gene:campoScheda entita="W9LOTT" campo="ID_MODO_GARA" />
		<gene:campoScheda campo="VER_SIMOG" visibile="false" />
		<gene:campoScheda campo="PROV_DATO" defaultValue="4" visibile="false" />
		<gene:campoScheda campo="IDAVGARA" defaultValue="0" visibile="false" />
		<gene:campoScheda campo="FLAG_ENTE_SPECIALE" />
		<gene:campoScheda campo="TIPO_APP"/>
		<gene:campoScheda campo="CIG_ACCQUADRO" />
		<gene:archivio titolo="TECNICI"
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.W9GARA.RESPRO"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9GARA_RUP" >
			<gene:campoScheda campo="RUP" defaultValue="${idTecnico}"
				visibile="false" />
			<gene:campoScheda campo="NOMTEC" entita="TECNI"
				defaultValue="${nomeTecnico}" where="TECNI.CODTEC=W9GARA.RUP"
				modificabile="${sessionScope.profiloUtente.abilitazioneStd eq 'A' || gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9SCP')}"
				title="RUP" visibile="true">
			</gene:campoScheda>
		</gene:archivio>
		<gene:campoScheda>
			<td colspan="2"><b>Stazione appaltante</b></td>
		</gene:campoScheda>
		<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
			campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="W9GARA_CODEIN" where=""
			formName="formStazApp">
			<gene:campoScheda campo="CODEIN" visibile="false"
				defaultValue="${sessionScope.uffint}" />
			<gene:campoScheda campo="NOMEIN" entita="UFFINT"
				defaultValue="${nomein}" where="UFFINT.CODEIN = W9GARA.CODEIN"
				modificabile="false" title="Stazione appaltante" />
		</gene:archivio>
		<gene:archivio titolo="Ufficio/area di pertinenza"
			lista='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.ListaUffici"),"w9/uffici/uffici-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda-popup.jsp","")}'
			where="UFFICI.CODEIN = '${sessionScope.uffint}'"
			campi="UFFICI.ID;UFFICI.DENOM"
			chiave="W9GARA_IDUFFICIO">
			<gene:campoScheda campo="IDUFFICIO" visibile="false" />
			<gene:campoScheda campo="DENOM" entita="UFFICI"
				where="UFFICI.ID=W9GARA.IDUFFICIO" modificabile='${gene:checkProt(pageContext, "COLS.MOD.W9.W9GARA.IDUFFICIO")}'
				title="Ufficio/area di pertinenza" definizione="T250" />
		</gene:archivio>
		
		<gene:campoScheda campo="INDSEDE" defaultValue="${indirizzoSede}" />
		<gene:campoScheda campo="COMSEDE" defaultValue="${comuneSede}" />
		<gene:campoScheda campo="PROSEDE" defaultValue="${provinciaSede}" />
		
		<gene:campoScheda campo="FLAG_SA_AGENTE" defaultValue="2"/>
		<gene:campoScheda campo="ID_TIPOLOGIA_SA" />
		<gene:campoScheda campo="DENOM_SA_AGENTE" />
		<gene:campoScheda campo="CF_SA_AGENTE" />
		<gene:campoScheda campo="TIPOLOGIA_PROCEDURA" />
		<gene:campoScheda campo="FLAG_CENTRALE_STIPULA" />
		<input type="hidden" name="keys" id="keys" value="" /> 
		
		<gene:campoScheda>
			<td colspan="2"><b>Dati economici</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="W9LOTT" campo="IMPORTO_LOTTO" obbligatorio="true" />
		<gene:campoScheda entita="W9LOTT" campo="IMPORTO_ATTUAZIONE_SICUREZZA" />
		<gene:campoScheda entita="W9LOTT" campo="IMPORTO_TOT" modificabile="false">
						<gene:calcoloCampoScheda
						funzione='toMoney(sommaCampi(new Array("#W9LOTT_IMPORTO_LOTTO#","#W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA#")))'
						elencocampi="W9LOTT_IMPORTO_LOTTO;W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA" />
		</gene:campoScheda>
		<gene:campoScheda entita="W9LOTT" campo="CUPESENTE"/>
		<gene:campoScheda entita="W9LOTT" campo="CUP" speciale="true" >
			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" and gene:checkProt(pageContext, "COLS.MOD.W9.W9LOTT.CUP")}'>
				<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
			</c:if>
		</gene:campoScheda>
		
		<gene:campoScheda entita="W9LOTT" campo="CPV" title="Codice CPV" href="#" speciale="true" >
			<gene:popupCampo titolo="Dettaglio CPV" href="formCPV()" />
		</gene:campoScheda>

		<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}" campoFittizio="true" definizione="T150" />
		
		<gene:campoScheda entita="W9LOTT" campo="ID_CATEGORIA_PREVALENTE" />
		<gene:campoScheda entita="W9LOTT" campo="CLASCAT" />
		
		<gene:archivio titolo="Comuni" lista="gene/commons/istat-comuni-lista-popup.jsp" scheda="" schedaPopUp=""
			campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3" chiave=""	where="" formName="" inseribile="false">
			<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true"
				definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia luogo di esecuzione del contratto"
				value="${descrProvinciaEsecuzione}" />
			<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true"
				definizione="T120;0;;;" title="Comune luogo di esecuzione del contratto"
				value="${denomComuneEsecuzione}" />
			<gene:campoScheda entita="W9LOTT" campo="LUOGO_ISTAT" title="Codice ISTAT del Comune luogo di esecuzione del contratto" />
		</gene:archivio>

		<gene:campoScheda entita="W9LOTT" campo="LUOGO_NUTS" href="#" modificabile="false" speciale="true" >
			<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
		</gene:campoScheda>
		
	</gene:gruppoCampi>

	<gene:fnJavaScriptScheda
		funzione='bloccaCampoNomein("#W9GARA_FLAG_SA_AGENTE#")'
		elencocampi="W9GARA_FLAG_SA_AGENTE" esegui="true" />
	<gene:fnJavaScriptScheda
		funzione="gestioneTIPO_APP('#W9GARA_TIPO_APP#')" elencocampi="W9GARA_TIPO_APP" esegui="true" />	
	<gene:fnJavaScriptScheda
		funzione='modifyClasseImportoCatPrevalente("#W9LOTT_ID_CATEGORIA_PREVALENTE#")'
		elencocampi="W9LOTT_ID_CATEGORIA_PREVALENTE" esegui="true" />
	<gene:fnJavaScriptScheda funzione='modifyCUPESENTE("#W9LOTT_CUPESENTE#")'	elencocampi="W9LOTT_CUPESENTE" esegui="true" />
	
	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="W9GARA" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>
	
	function bloccaCampoNomein(valore) {
		var vis = (valore==1);

		showObj("rowW9GARA_ID_TIPOLOGIA_SA", vis);
		showObj("rowW9GARA_DENOM_SA_AGENTE", vis);
		showObj("rowW9GARA_CF_SA_AGENTE", vis);
		showObj("rowW9GARA_TIPOLOGIA_PROCEDURA", vis);
		showObj("rowW9GARA_FLAG_CENTRALE_STIPULA", vis);
		
		if (!vis)	{
			setValue("W9GARA_ID_TIPOLOGIA_SA", "");
			setValue("W9GARA_DENOM_SA_AGENTE", "");
			setValue("W9GARA_CF_SA_AGENTE", "");
			setValue("W9GARA_TIPOLOGIA_PROCEDURA", "");
			setValue("W9GARA_FLAG_CENTRALE_STIPULA", "");
		}
	}
	
	function gestioneTIPO_APP(value) {
		var visible = (value==2 || value==11);
	
		document.getElementById("rowW9GARA_CIG_ACCQUADRO").style.display = (visible ? '':'none');
		if (!visible) {
			document.forms[0].W9GARA_CIG_ACCQUADRO.value='';
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

	function modifyCUPESENTE(valore) {
		var vis = (valore == 1);
		if (vis) {
			setValue("W9LOTT_CUP","");
		}
		showObj("rowW9LOTT_CUP",!vis);
	}
	
	function sommaCampi(valori)	{
	  var i, ret=0;
	  for (i=0; i < valori.length; i++) {
	    if(valori[i]!="")
	      ret += eval(valori[i]);
	  }
	  return eval(ret).toFixed(2);
	}
	
	function popupValidazione(flusso,key1,key2,key3) {
	   var comando = "href=w9/commons/popup-validazione-generale.jsp";
	   comando = comando + "&flusso=" + flusso;
	   comando = comando + "&key1=" + key1;
	   comando = comando + "&key2=" + key2;
	   comando = comando + "&key3=" + key3;
	   openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}
	
	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
		
		_creaFinestraAlberoCpvVP();
		_creaLinkAlberoCpvVP($("#W9LOTT_CPV").parent(), "${modo}", $("#W9LOTT_CPV"), $("#W9LOTT_CPVview"), $("#CPVDESC") );
		
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