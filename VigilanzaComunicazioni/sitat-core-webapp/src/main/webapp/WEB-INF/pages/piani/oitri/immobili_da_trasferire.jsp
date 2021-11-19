<!-- Inserisco la Tag Library -->
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="AliceResources" />

	<gene:gruppoCampi idProtezioni="IMMOBTRASF">
		<gene:campoScheda nome="labelImmTrasf">
			<td colspan="2"><b>Immobili da trasferire</b></td>
		</gene:campoScheda>

		<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GestioneImmobiliFunction" parametro="${param.keyOITRI}" />
		<c:set var="contri" value='${gene:getValCampo(keyOITRI,"CONTRI")}'></c:set>
		<c:set var="conint" value='0'></c:set>
		<c:set var="numoi" value='${gene:getValCampo(keyOITRI,"NUMOI")}'></c:set>
		<c:set var="contatore" value="1" scope="page"/>
		<c:choose>
		<c:when test='${!empty listaImmobiliDaTrasfInterv}'>
			<c:forEach items="${listaImmobiliDaTrasfInterv}" var="immobile" varStatus="indiceListaImmobiliDaTrasfInterv">
				<gene:gruppoCampi>
					<gene:campoScheda
						campo="DEL_IMMTRAI_${contatore}" entita="IMMTRAI"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda campo="MOD_IMMTRAI_${contatore}" entita="IMMTRAI"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda nome="datiImmobile_${contatore}">
						<td colspan="2">
							<b>Scheda Immobile ${contatore}</b>
							<c:if test='${modoAperturaScheda ne "VISUALIZZA" and gene:checkProtFunz(pageContext, "DEL","Elimina-Immobile")}'>
								<gene:PopUp>
									<gene:PopUpItem title="Elimina immobile" href='eliminaImmobile(${contatore})' />
								</gene:PopUp>
							<!--div id="tdTitoloDestra_${contatore}" style="float:right;">
									<a href='javascript:eliminaImmobile(${contatore})' title="Elimina immobile" class="link-generico">Elimina</a>&nbsp;
									<a href='javascript:eliminaImmobile(${contatore})' title="Elimina immobile" class="link-generico"><img src='${pageContext.request.contextPath}/img/opzioni_del.gif' height='16' width='16' alt='Elimina ${param.descEntitaVociLink}'></a>&nbsp;
								</div-->
							</c:if>
						</td>
					</gene:campoScheda>
					<gene:campoScheda 
						campoFittizio="true"  entita="IMMTRAI"
						campo="CONTRI_${contatore}"
						value="${contri}" definizione="N3;1;;;CONTRI" visibile="false" />
					<gene:campoScheda 
						campoFittizio="true" entita="IMMTRAI" 
						campo="CONINT_${contatore}"
						value="${conint}" definizione="N5;1;;;CONINT" visibile="false" />
					<gene:campoScheda title="N. progressivo degli immobili dell'intervento" entita="IMMTRAI"
						campoFittizio="true" 
						campo="NUMIMM_${contatore}"
						value="${immobile[1]}" definizione="N4;1;;;NUMIMM" visibile="false" />
					<c:choose>
						<c:when test='${not empty modo and modo ne "VISUALIZZA" }'>
							<c:set var="modCUIIMM" value="true" scope="request"/>
						</c:when>
						<c:otherwise>
							<c:set var="modCUIIMM" value="false" scope="request"/>
						</c:otherwise>
					</c:choose>
					<gene:campoScheda campo="CUIIMM_${contatore}" entita="IMMTRAI"  campoFittizio="true" definizione="T50" title="Codice univoco immobile" value="${immobile[5]}" href="javascript:formCUIIMM(${modCUIIMM},'IMMTRAI_CUIIMM_${contatore}','IMMTRAI_CONTRI_${contatore}')" modificabile="false" speciale="true" >
						<gene:popupCampo titolo="Modifica codice univoco" href="formCUIIMM(${modCUIIMM},'IMMTRAI_CUIIMM_${contatore}','IMMTRAI_CONTRI_${contatore}')" />
					</gene:campoScheda>
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="DESIMM_${contatore}"
						value="${immobile[3]}" definizione="T400;0;;;T2IDESIMM" />
					<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false" 
						lista="gene/commons/istat-comuni-lista-popup.jsp" scheda=""
						schedaPopUp="" campi="TABSCHE.TABCOD3;TABSCHE.TABDESC;TB1.TABCOD3">
						<gene:campoScheda entita="IMMTRAI" campo="COMIST_${contatore}"  campoFittizio="true" definizione="T9" title="Codice ISTAT del comune" value="${immobile[6]}"  />
						<gene:campoScheda campo="COMUNE_${contatore}"  campoFittizio="true" definizione="T120"	title="Comune" value="${immobile[7]}" />
						<gene:campoScheda campo="PROVINCIA_${contatore}" campoFittizio="true" definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia" value="${immobile[8]}" />
					</gene:archivio>
					<c:choose>
						<c:when test='${not empty modo and modo ne "VISUALIZZA" }'>
							<c:set var="modCODNUTS" value="true" scope="request"/>
						</c:when>
						<c:otherwise>
							<c:set var="modCODNUTS" value="false" scope="request"/>
						</c:otherwise>
					</c:choose>
					<gene:campoScheda campo="NUTS_${contatore}" entita="IMMTRAI"  campoFittizio="true" definizione="T5" title="Codice NUTS" value="${immobile[12]}" href="javascript:formNUTS(${modCODNUTS},'IMMTRAI_NUTS_${contatore}')" modificabile="false" speciale="true" >
						<gene:popupCampo titolo="Dettaglio codice NUTS" href="formNUTS(${modCODNUTS},'IMMTRAI_NUTS_${contatore}')" />
					</gene:campoScheda>
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="TITCOR_${contatore}"
						value="${immobile[13]}" definizione="N7;0;PT013;;T2TITCOR" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="IMMDISP_${contatore}"
						value="${immobile[14]}" definizione="N7;0;PT014;;T2IMMDIS" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="PROGDISM_${contatore}"
						value="${immobile[15]}" definizione="N7;0;PT015;;T2PROGDI" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="TIPDISP_${contatore}"
						value="${immobile[16]}" definizione="N7;0;PT016;;T2TIPDIS" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="VA1IMM_${contatore}"
						value="${immobile[17]}" definizione="F24.5;0;;MONEY;T2IVA1INT" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="VA2IMM_${contatore}"
						value="${immobile[18]}" definizione="F24.5;0;;MONEY;T2IVA2INT" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="VA3IMM_${contatore}"
						value="${immobile[19]}" definizione="F24.5;0;;MONEY;T2IVA3INT" />						
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="VA9IMM_${contatore}"
						value="${immobile[20]}" definizione="F24.5;0;;MONEY;T2IVA9INT" />
					<gene:campoScheda entita="IMMTRAI"
						campoFittizio="true" 
						campo="VALIMM_${contatore}"
						visibile="true" modificabile="false"
						value="${immobile[4]}" definizione="F24.5;0;;MONEY;T2IVALIMM" />
				</gene:gruppoCampi>
				<gene:fnJavaScriptScheda funzione="changeImmtrai(${contatore})" 
					elencocampi="IMMTRAI_CUIIMM_${contatore};IMMTRAI_COMIST_${contatore};IMMTRAI_DESIMM_${contatore};IMMTRAI_VALIMM_${contatore};IMMTRAI_NUTS_${contatore};IMMTRAI_TITCOR_${contatore};IMMTRAI_IMMDISP_${contatore};IMMTRAI_PROGDISM_${contatore};IMMTRAI_TIPDISP_${contatore};IMMTRAI_VA1IMM_${contatore};IMMTRAI_VA2IMM_${contatore};IMMTRAI_VA3IMM_${contatore};IMMTRAI_VA9IMM_${contatore};IMMTRAI_VA9IMM_${contatore}"
					esegui="false" />
				<gene:fnJavaScriptScheda funzione="calcoloVALIMM(${contatore})" 
					elencocampi="IMMTRAI_VA1IMM_${contatore};IMMTRAI_VA2IMM_${contatore};IMMTRAI_VA3IMM_${contatore};IMMTRAI_VA9IMM_${contatore}"
					esegui="false" />
				<c:set var="contatore" value="${contatore +1}" />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:if test='${modoAperturaScheda ne "NUOVO"}'>
				<gene:gruppoCampi>
					<gene:campoScheda nome="datiImmobile">
						<td colspan="2"><b>Nessun immobile inserito</b></td>
					</gene:campoScheda>
				</gene:gruppoCampi>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:set var="nuoviElementi" value="0" />
	<c:forEach var="nuoviImmobili" begin="1" end="5" step="1">
		<gene:gruppoCampi>
			<gene:campoScheda campo="DEL_IMMTRAI_${contatore}" entita="IMMTRAI"
				campoFittizio="true" visibile="false" definizione="T1" value="0" />
			<gene:campoScheda campo="INS_IMMTRAI_${contatore}" entita="IMMTRAI"
				campoFittizio="true" visibile="false" definizione="T1" value="0" />
			<gene:campoScheda nome="datiImmobile_${contatore}" visibile="true">
				<td colspan="2">
				<b>Nuovo immobile ${nuoviElementi + 1}</b>
				<c:if
					test='${modoAperturaScheda ne "VISUALIZZA" and gene:checkProtFunz(pageContext, "DEL","Elimina-Immobile")}'>
					<div id="tdTitoloDestra_${contatore}" style="float:right;">
						<a href='javascript:eliminaImmobile(${contatore})' title="Elimina immobile" class="link-generico">Elimina</a>&nbsp;
						<a href='javascript:eliminaImmobile(${contatore})' title="Elimina immobile" class="link-generico"><img src='${pageContext.request.contextPath}/img/opzioni_del.gif' height='16' width='16' alt='Elimina ${param.descEntitaVociLink}'></a>&nbsp;
					</div>
				</c:if></td>
			</gene:campoScheda>
			
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" value="${contri}"
				campo="CONTRI_${contatore}" visibile="false"
				definizione="N3;1;;;CONTRI" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" value="${conint}"
				campo="CONINT_${contatore}" visibile="false"
				definizione="N5;1;;;CONINT" />
			<gene:campoScheda title="N. progressivo degli immobili dell'intervento" entita="IMMTRAI"
				campoFittizio="true"
				campo="NUMIMM_${contatore}" visibile="false"
				definizione="N4;1;;;NUMIMM" />
			<c:choose>
				<c:when test='${not empty modo and modo ne "VISUALIZZA" }'>
					<c:set var="modCUIIMM" value="true" scope="request"/>
				</c:when>
				<c:otherwise>
					<c:set var="modCUIIMM" value="false" scope="request"/>
				</c:otherwise>
			</c:choose>
			<gene:campoScheda campo="CUIIMM_${contatore}" entita="IMMTRAI"  campoFittizio="true" definizione="T50" title="Codice univoco immobile" href="javascript:formCUIIMM(${modCUIIMM},'IMMTRAI_CUIIMM_${contatore}','IMMTRAI_CONTRI_${contatore}')" modificabile="false" speciale="true" >
				<gene:popupCampo titolo="Modifica codice univoco immobile" href="formCUIIMM(${modCUIIMM},'IMMTRAI_CUIIMM_${contatore}','IMMTRAI_CONTRI_${contatore}')" />
			</gene:campoScheda>
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" visibile="true"
				campo="DESIMM_${contatore}"
				definizione="T400;0;;;T2IDESIMM" />
			<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false" 
				lista="gene/commons/istat-comuni-lista-popup.jsp" scheda=""
				schedaPopUp="" 
				campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3">
				<gene:campoScheda campo="PROVINCIA_${contatore}" campoFittizio="true" definizione="T2;0;Agx15;;S3COPROVIN"
					title="Provincia" value="" />
				<gene:campoScheda campo="COMUNE_${contatore}" campoFittizio="true" definizione="T120"	title="Comune" value="" />
				<gene:campoScheda entita="IMMTRAI" campo="COMIST_${contatore}" campoFittizio="true" definizione="T9;;;;T2COMIST" title="Codice ISTAT del comune" />
			</gene:archivio>
			<c:choose>
				<c:when test='${not empty modo and modo ne "VISUALIZZA" }'>
					<c:set var="modCODNUTS" value="true" scope="request"/>
				</c:when>
				<c:otherwise>
					<c:set var="modCODNUTS" value="false" scope="request"/>
				</c:otherwise>
			</c:choose>
			<gene:campoScheda campo="NUTS_${contatore}" entita="IMMTRAI"  campoFittizio="true" definizione="T5" title="Codice NUTS" href="javascript:formNUTS(${modCODNUTS},'IMMTRAI_NUTS_${contatore}')" modificabile="false" speciale="true" >
				<gene:popupCampo titolo="Dettaglio codice NUTS" href="formNUTS(${modCODNUTS},'IMMTRAI_NUTS_${contatore}')" />
			</gene:campoScheda>
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" 
				campo="TITCOR_${contatore}"
				definizione="N7;0;PT013;;T2TITCOR" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" 
				campo="IMMDISP_${contatore}"
				definizione="N7;0;PT014;;T2IMMDIS" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" 
				campo="PROGDISM_${contatore}"
				definizione="N7;0;PT015;;T2PROGDI" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" 
				campo="TIPDISP_${contatore}"
				definizione="N7;0;PT016;;T2TIPDIS" />				
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true"
				campo="VA1IMM_${contatore}"
				 definizione="F24.5;0;;MONEY;T2IVA1INT" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true"
				campo="VA2IMM_${contatore}"
				definizione="F24.5;0;;MONEY;T2IVA2INT" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true"
				campo="VA3IMM_${contatore}"
   			definizione="F24.5;0;;MONEY;T2IVA3INT" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true"
				campo="VA9IMM_${contatore}" 
     		definizione="F24.5;0;;MONEY;T2IVA9INT" />
			<gene:campoScheda entita="IMMTRAI"
				campoFittizio="true" visibile="true"
				campo="VALIMM_${contatore}" modificabile="false"
				definizione="F24.5;0;;MONEY;T2IVALIMM" />
		</gene:gruppoCampi>
		<gene:fnJavaScriptScheda funzione="changeImmtrai(${contatore})" 
			elencocampi="IMMTRAI_CUIIMM_${contatore};IMMTRAI_COMIST_${contatore};IMMTRAI_DESIMM_${contatore};IMMTRAI_VALIMM_${contatore};IMMTRAI_NUTS_${contatore}; IMMTRAI_TITCOR_${contatore};IMMTRAI_IMMDISP_${contatore};IMMTRAI_PROGDISM_${contatore};IMMTRAI_TIPDISP_${contatore};IMMTRAI_VA1IMM_${contatore};IMMTRAI_VA2IMM_${contatore};IMMTRAI_VA3IMM_${contatore};IMMTRAI_VA9IMM_${contatore}"
			esegui="false" />
		<gene:fnJavaScriptScheda funzione="calcoloVALIMM(${contatore})" 
			elencocampi="IMMTRAI_VA1IMM_${contatore};IMMTRAI_VA2IMM_${contatore};IMMTRAI_VA3IMM_${contatore};IMMTRAI_VA9IMM_${contatore}"
			esegui="false" />
		<c:set var="nuoviElementi" value="${nuoviElementi + 1}" />
		<c:set var="contatore" value="${contatore + 1}" />
	</c:forEach>
	<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
		<c:if test='${gene:checkProtFunz(pageContext, "INS","Agg-Immobili")}'>
			<gene:campoScheda nome="LinkVisualizzaNuovoImmobile">
				<td>&nbsp;</td>
				<td class="valore-dato">
					<a href="javascript:visualizzaProssimoImmobile();" class="link-generico">
						<img src="${pageContext.request.contextPath}/img/opzioni_add.gif"
						title="" alt="Aggiungi immobile" height="16" width="16">&nbsp;Aggiungi immobile
					</a>
				</td>
			</gene:campoScheda>
			<gene:campoScheda nome="MsgUltimoImmobile">
				<td>&nbsp;</td>
				<td class="valore-dato">Raggiunto il numero massimo di immobili,
				per aggiungerne altri &egrave; necessario salvare.</td>
			</gene:campoScheda>
			<gene:campoScheda campo="NUMERO_IMMTRAI" campoFittizio="true" visibile="false" definizione="N3" value="${nuoviElementi+0+contatore}" />
		</c:if>
	</c:if>
	</gene:gruppoCampi>
	<gene:javaScript>
	showObj("rowMsgUltimoImmobile", false);
	<c:choose>
		<c:when test='${(!empty listaImmobiliDaTrasfInterv)}'>
			var idUltimoImmobileVisualizzato = ${fn:length(listaImmobiliDaTrasfInterv)};
			var maxIdImmobileVisualizzabile = ${fn:length(listaImmobiliDaTrasfInterv)+5};
		</c:when>
		<c:otherwise>
			var idUltimoImmobileVisualizzato = 0;
			var maxIdImmobileVisualizzabile = 5;
		</c:otherwise>
	</c:choose>

	function nascondiSezioneImmobile(idImmobile, sbiancaValori){
		showObj("rowdatiImmobile_" + idImmobile, false);
		showObj("rowIMMTRAI_NUMIMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_DESIMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_VALIMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_CUIIMM_"  + idImmobile, false);
		showObj("rowPROVINCIA_"  + idImmobile, false);
		showObj("rowCOMUNE_"  + idImmobile, false);
		showObj("rowIMMTRAI_COMIST_"  + idImmobile, false);
		showObj("rowIMMTRAI_NUTS_"    + idImmobile, false);
		showObj("rowIMMTRAI_TITCOR_"  + idImmobile, false);
		showObj("rowIMMTRAI_IMMDISP_"  + idImmobile, false);
		showObj("rowIMMTRAI_PROGDISM_" + idImmobile, false);
		showObj("rowIMMTRAI_TIPDISP_" + idImmobile, false);
		showObj("rowIMMTRAI_VA1IMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_VA2IMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_VA3IMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_VA9IMM_"  + idImmobile, false);
		
		if(sbiancaValori){
			setValue("IMMTRAI_NUMIMM_"  + idImmobile, "");
			setValue("IMMTRAI_DESIMM_"  + idImmobile, "");
			setValue("IMMTRAI_VALIMM_"  + idImmobile, "");
			setValue("IMMTRAI_CUIIMM_"  + idImmobile, "");
			setValue("PROVINCIA_"  + idImmobile, "");
			setValue("COMUNE_"  + idImmobile, "");
			setValue("IMMTRAI_COMIST_"  + idImmobile, "");
			setValue("IMMTRAI_NUTS_"    + idImmobile, "");
			setValue("IMMTRAI_TITCOR_"  + idImmobile, "");
			setValue("IMMTRAI_IMMDISP_"  + idImmobile, "");
			setValue("IMMTRAI_PROGDISM_" + idImmobile, "");
			setValue("IMMTRAI_TIPDISP_" + idImmobile, "");
			setValue("IMMTRAI_VA1IMM_"  + idImmobile, "");
			setValue("IMMTRAI_VA2IMM_"  + idImmobile, "");
			setValue("IMMTRAI_VA3IMM_"  + idImmobile, "");
			setValue("IMMTRAI_VA9IMM_"  + idImmobile, "");
		}
	}
	
	function visualizzaSezioneImmobile(idImmobile){
		showObj("rowdatiImmobile_" + idImmobile, true);
		showObj("rowIMMTRAI_NUMIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_DESIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_VALIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_CUIIMM_"  + idImmobile, true);
		showObj("rowPROVINCIA_"  + idImmobile, true);
		showObj("rowCOMUNE_"  + idImmobile, true);
		showObj("rowIMMTRAI_COMIST_"  + idImmobile, true);
		showObj("rowIMMTRAI_NUTS_"    + idImmobile, true);
		showObj("rowIMMTRAI_TITCOR_"  + idImmobile, true);
		showObj("rowIMMTRAI_IMMDISP_"  + idImmobile, true);
		showObj("rowIMMTRAI_PROGDISM_" + idImmobile, true);
		showObj("rowIMMTRAI_TIPDISP_" + idImmobile, true);
		showObj("rowIMMTRAI_VA1IMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_VA2IMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_VA3IMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_VA9IMM_"  + idImmobile, true);
		showObj("rowdatiImmobile", false);
	}
	
	var indiceProgressivo = 0;
	for (indiceProgressivo=idUltimoImmobileVisualizzato+1; indiceProgressivo <= maxIdImmobileVisualizzabile; indiceProgressivo++) {
		nascondiSezioneImmobile(indiceProgressivo, false);
	}
	
	function visualizzaProssimoImmobile() {
		var indice = idUltimoImmobileVisualizzato;
		indice++;
		var immobileEliminato = getValue("IMMTRAI_DEL_IMMTRAI_" + indice);
		while(indice <= maxIdImmobileVisualizzabile && immobileEliminato != "0"){
			indice++;
		}
		
		if(immobileEliminato != null && immobileEliminato == "0") {
			idUltimoImmobileVisualizzato = indice;
			visualizzaSezioneImmobile(indice);
			//setValue("IMMTRAI_USE_IMMTRAI_" + indice, "0");
		}

		if(indice == maxIdImmobileVisualizzabile){
			showObj("rowLinkVisualizzaNuovoImmobile", false);
			showObj("rowMsgUltimoImmobile", true);
		}
	}
	
	function eliminaImmobile(idImmobile) {
		if(confirm("Procedere con l'eliminazione ?")){
			nascondiSezioneImmobile(idImmobile, false);
		  setValue("IMMTRAI_DEL_IMMTRAI_" + idImmobile, "1");
		  
		  calcoloImportiTotaliImmobili(1);
		  calcoloImportiTotaliImmobili(2);
		  calcoloImportiTotaliImmobili(3);
		}
	}
	
	function changeImmtrai(idImmobile) {
		if(document.getElementById("IMMTRAI_MOD_IMMTRAI_" + idImmobile) != null) {
			setValue("IMMTRAI_MOD_IMMTRAI_" + idImmobile, "1");
		} else {
			setValue("IMMTRAI_INS_IMMTRAI_" + idImmobile, "1");
		}
	}
	
	function calcoloVALIMM(contatore) {
		var imp_somma = 0;
		var imp1 = 0;
		var imp2 = 0;
		var imp3 = 0;
		var imp9 = 0;
		if (getValue("IMMTRAI_VA1IMM_" + contatore) != "")
			imp1 = parseFloat($('#IMMTRAI_VA1IMM_' + contatore).val());
		if (getValue("IMMTRAI_VA2IMM_" + contatore) != "")
			imp2 = parseFloat($('#IMMTRAI_VA2IMM_' + contatore).val());
		if (getValue("IMMTRAI_VA3IMM_" + contatore) != "")
			imp3 = parseFloat($('#IMMTRAI_VA3IMM_' + contatore).val());
		if (getValue("IMMTRAI_VA9IMM_" + contatore) != "")
			imp9 = parseFloat($('#IMMTRAI_VA9IMM_' + contatore).val());
		imp_somma = imp1 + imp2 + imp3 + imp9;
		
		if (imp_somma >= 0) {
			setValue("IMMTRAI_VALIMM_" + contatore, formatNumber(imp_somma,18.2));
		}
	}

	function formNUTS(modifica, campo) {
		openPopUpCustom("href=gene/tabnuts/dettaglio-codice-nuts.jsp&key=" + document.forms[0].key.value + 
			"&keyParent=" + document.forms[0].keyParent.value + "&modo=" + (modifica ? "MODIFICA":"VISUALIZZA") + 
			"&campo="+campo+"&valore="+ getValue(campo), "formNUTS", 700, 300, 1, 1);
	}
	
	<c:if test='${modoAperturaScheda eq "NUOVO" or (modoAperturaScheda eq "MODIFICA" and empty listaImmobiliDaTrasfInterv)}'>
		//setValue("USE_IMMTRAI_1", "0");
	</c:if>
	</gene:javaScript>