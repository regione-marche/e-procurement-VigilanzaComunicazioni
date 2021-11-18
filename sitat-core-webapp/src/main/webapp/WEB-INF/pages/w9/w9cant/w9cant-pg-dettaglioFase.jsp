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

	<gene:formScheda entita="W9CANT" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9CANT" 
		gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9CANT" >
		<gene:redefineInsert name="schedaNuovo" />
		<gene:redefineInsert name="pulsanteNuovo" />
		
		<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteModifica" />
		</c:if>
		
		<gene:gruppoCampi idProtezioni="GEN">
			<gene:campoScheda>
				<td colspan="2"><b>Dati opera</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="TIPOPERA" />
			<gene:campoScheda campo="TIPINTERV" defaultValue="${requestScope.tipoIntervento}" />
			<gene:campoScheda campo="TIPCOSTR" />
			<gene:campoScheda>
				<td colspan="2"><b>Dati generali </b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CODGARA" visibile="false" value="${codgara}" />
			<gene:campoScheda campo="CODLOTT" visibile="false" value="${codlott}"  />
			<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
			<gene:campoScheda campo="NUM_CANT"  modificabile="false" visibile="${modoAperturaScheda ne 'NUOVO'}"/>
			<gene:campoScheda campo="DINIZ" defaultValue="${requestScope.dataInizioLavori}" />
			<gene:campoScheda campo="DLAV" />
			<gene:campoScheda campo="INDCAN" />
			
			<gene:campoScheda campo="CIVICO" />
			<gene:archivio titolo="Comuni" obbligatorio="false" scollegabile="true"
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.CAPTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.PROTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.LOCTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.CITTEC"),"gene/commons/istat-comuni-lista-popup.jsp","")}' 
				scheda="" 
				schedaPopUp="" 
				campi="TB1.TABCOD3;TABSCHE.TABCOD4;TABSCHE.TABDESC;TABSCHE.TABDESC;TABSCHE.TABCOD3" 
				chiave="" 
				where=""  
				formName="formIstat" 
				inseribile="false" >
				<gene:campoScheda campo="PROV"/>
				<gene:campoScheda campoFittizio="true" title="fittizio 1" campo="COM_PROTEC" definizione="T9" visibile="false"/>
				<gene:campoScheda campo="CAPTEC" title="fittizio 2" campoFittizio="true" definizione="T20" visibile="false"/>
				<gene:campoScheda campo="DENOM_COMUNE" campoFittizio="true" definizione="T120"
							title="Comune" value="${comune}" />
				<gene:campoScheda campo="COMUNE" visibile="false"/>
			</gene:archivio>
			
			<gene:campoScheda campo="COORD_X" >
				<gene:checkCampoScheda funzione="controlloGaussBoaga('X');" obbligatorio="true" messaggio="Coordinata Gauss Boaga X &ge; 1550300 e &le; 1775000" />
			</gene:campoScheda>
			<gene:campoScheda campo="COORD_Y" >
				<gene:checkCampoScheda funzione="controlloGaussBoaga('Y');" obbligatorio="true" messaggio="Coordinata Gauss Boaga Y &ge; 4600000 e &le; 4930000" />
			</gene:campoScheda>
			<gene:campoScheda campo="LATIT" >
				<gene:checkCampoScheda funzione="controlloWGS84('lat');" obbligatorio="true" messaggio="Latitudine WGS84 &ge; 42.180&deg; e &le; 44.471&deg;" />
			</gene:campoScheda>
			<gene:campoScheda campo="LONGIT" >
				<gene:checkCampoScheda funzione="controlloWGS84('lon');" obbligatorio="true" messaggio="Latitudine WGS84 &ge; 9.609&deg; e &le; 12.457&deg;" />
			</gene:campoScheda>
			<gene:campoScheda campo="NUMLAV" />
			<gene:campoScheda campo="NUMIMP" />
			<gene:campoScheda campo="LAVAUT" />
			
			<gene:campoScheda campo="INFRDA" />
			<gene:campoScheda campo="INFRA" />
			<gene:campoScheda campo="INFRDE" />
		</gene:gruppoCampi>
		
		<gene:gruppoCampi idProtezioni="DESTINATARI" >
			<gene:campoScheda nome="DestinatariNotifica ">
				<td colspan="2"><b>Destinatari notifica </b></td>
			</gene:campoScheda>
			<c:if test="${fn:length(datiTabellatoW9008List) >0}">
				<gene:campoScheda campo="MAILRIC" defaultValue="${requestScope.mailric}"/>	
				<c:forEach begin="0" end="${fn:length(datiTabellatoW9008List)-1}" var="indiceTabCant">
					<c:set var="valoreCantDest" value="0" />
					<c:if test='${modo ne "NUOVO"}'>
						<c:set var="valoreCantDest" value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.VerificaCondizioneDaTabellatoFunction", pageContext, key, datiTabellatoW9008List[indiceTabCant][0], "W9CANTDEST")}'></c:set>
					</c:if>
					<gene:campoScheda campoFittizio="true" entita="W9CANTDEST"
						campo="DEST_${indiceTabCant+1}" title="${datiTabellatoW9008List[indiceTabCant][1]}"
						value="${valoreCantDest}" definizione="N5;0;;SN;"
						gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoSiNoSenzaNull" />
					<gene:campoScheda campoFittizio="true" entita="W9CANTDEST"
						campo="VALORE_DEST_${indiceTabCant+1}"
						title="${datiTabellatoW9008List[indiceTabCant][1]}"
						value="${datiTabellatoW9008List[indiceTabCant][0]}" definizione="N5;0;;;" visibile="false" />
					<gene:fnJavaScriptScheda funzione='modifyOIF("#W9CANTDEST_DEST_${indiceTabCant+1}#")'	elencocampi="W9CANTDEST_DEST_${indiceTabCant+1}" esegui="true" />
				</c:forEach>
			</c:if>
					
		</gene:gruppoCampi>

	<!-- scheda multipla -->
	<c:if test='${modo ne "NUOVO"}'>
		<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetImpreseCantiereNotificheFunction"  parametro="${key}" />
	</c:if>

	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp">
		<jsp:param name="entita" value='W9CANTIMP' />
		<jsp:param name="chiave"
			value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};${gene:getValCampo(key, "NUM_APPA")}' />
		<jsp:param name="nomeAttributoLista" value='listaImpreseCantiereNotifiche' />
		<jsp:param name="idProtezioni" value="W9CANTIMP" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9cant/imprese_cantiere_notifiche.jsp" />
		<jsp:param name="arrayCampi" value="'W9CANTIMP_CODGARA_','W9CANTIMP_CODLOTT_','W9CANTIMP_NUM_CANT_','W9CANTIMP_NUM_IMP_','IMPR_NOMEST_','W9CANTIMP_CODIMP_','W9CANTIMP_CIPDURC_','W9CANTIMP_PROTDURC_','W9CANTIMP_DATDURC_'"/>
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,true,false,true,true,true" />
		<jsp:param name="usaContatoreLista" value="true" />
		<jsp:param name="sezioneListaVuota" value="true" />
		<jsp:param name="titoloSezione" value="Imprese" />
		<jsp:param name="titoloNuovaSezione" value="Nuova impresa" />
		<jsp:param name="descEntitaVociLink" value="Imprese" />
		<jsp:param name="msgRaggiuntoMax"  value="e Imprese" />
	</jsp:include>

	<!-- campi per  W9FASI-->
		<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
			where="W9FASI.CODGARA = W9CANT.CODGARA AND W9FASI.CODLOTT = W9CANT.CODLOTT AND W9FASI.NUM = W9CANT.NUM_CANT AND W9FASI.NUM_APPA = W9CANT.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
		<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
			where="W9FASI.CODGARA = W9CANT.CODGARA AND W9FASI.CODLOTT = W9CANT.CODLOTT AND W9FASI.NUM = W9CANT.NUM_CANT AND W9FASI.NUM_APPA = W9CANT.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
		<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
			where="W9FASI.CODGARA = W9CANT.CODGARA AND W9FASI.CODLOTT = W9CANT.CODLOTT AND W9FASI.NUM = W9CANT.NUM_CANT AND W9FASI.NUM_APPA = W9CANT.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
		<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
		<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
			where="W9FASI.CODGARA = W9CANT.CODGARA AND W9FASI.CODLOTT = W9CANT.CODLOTT AND W9FASI.NUM = W9CANT.NUM_CANT AND W9FASI.NUM_APPA = W9CANT.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	
		<!--gene-:-fnJavaScriptScheda funzione="visualizzaImprese();" elencocampi="W9CANTDEST_DEST_1;W9CANTDEST_DEST_2;W9CANTDEST_DEST_3;W9CANTDEST_DEST_4;W9CANTDEST_DEST_5" esegui="true"/-->

		<gene:campoScheda>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
		</gene:campoScheda>
	</gene:formScheda>
	<gene:javaScript>
	
	var checkEnable = false;
	var checkDisable = false;
	function modifyOIF(valore)
	{
		if (valore == 1 && !checkEnable) {
			showObj("rowW9CANT_MAILRIC",true);
			checkEnable = true;
			checkDisable = false;
			return;
		} else if (valore == 0 && !checkDisable) {
		<c:if test="${fn:length(datiTabellatoW9008List) >0}">
		<c:forEach begin="0" end="${fn:length(datiTabellatoW9008List)-1}" var="indiceTabCant">
			if (!checkDisable && document.getElementById("W9CANTDEST_DEST_${indiceTabCant+1}").value == 1) {
				return;
			}
		</c:forEach>
		</c:if>
			showObj("rowW9CANT_MAILRIC",false);
			checkDisable = true;
			checkEnable = false;
		}
	}
	
	function controlloGaussBoaga(var1) {
		var result = true;
		var valore = null;
		if (var1 == 'X') {
			if ("" != getValue("W9CANT_COORD_X")) {
				valore = new Number(getValue("W9CANT_COORD_X"));
				if (valore < 1550300 || valore > 1775000) {
					result = false;
				}
			}
		} else {
			if ("" != getValue("W9CANT_COORD_Y")) {
			valore = new Number(getValue("W9CANT_COORD_Y"));
				if (valore < 4600000 || valore > 4930000) {
					result = false;
				}
			}
		}
		return result;
	}
	
	function controlloWGS84(var1) {
		var result = true;
		var valore = null;
		if (var1 == 'lat') {
			if ("" != getValue("W9CANT_LATIT")) {
				valore = new Number(getValue("W9CANT_LATIT"));
				if (valore < 42.180 || valore > 44.471) {
					result = false;
				}
			}
		} else {
			if ("" != getValue("W9CANT_LONGIT")) {
				valore = new Number(getValue("W9CANT_LONGIT"));
				if (valore < 9.609 || valore > 12.457) {
					result = false;
				}
			}
		}
		return result;
	}
	
	</gene:javaScript>
