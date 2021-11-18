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

		<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GestioneImmobiliFunction" parametro="${param.keyInttri}" />
		<c:set var="contri" value='${gene:getValCampo(keyINTTRI,"CONTRI")}'></c:set>
		<c:set var="conint" value='${gene:getValCampo(keyINTTRI,"CONINT")}'></c:set>
		<c:set var="contatore" value="1" scope="page"/>
		<c:choose>
		<c:when test='${!empty listaImmobiliDaTrasfInterv}'>
			<c:forEach items="${listaImmobiliDaTrasfInterv}" var="immobile"
				varStatus="indiceListaImmobiliDaTrasfInterv">
				<gene:gruppoCampi>
					<gene:campoScheda
						campo="DEL_IMMTRAI_${contatore}" entita="IMMTRAI"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda campo="MOD_IMMTRAI_${contatore}" entita="IMMTRAI"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda nome="datiImmobile_${contatore}">
						<td colspan="2"><b>Scheda Immobile
						${contatore}</b> <c:if
							test='${modoAperturaScheda ne "VISUALIZZA" and gene:checkProtFunz(pageContext, "DEL","Elimina-Immobile")}'>
							<gene:PopUp>
								<gene:PopUpItem title="Elimina immobile"
									href='eliminaImmobile(${contatore})' />
							</gene:PopUp>
						</c:if></td>
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
					<gene:campoScheda title="Descrizione dell'immobile" entita="IMMTRAI"
						campoFittizio="true" 
						campo="DESIMM_${contatore}"
						value="${immobile[3]}" definizione="T400;0;;;T2IDESIMM" />
					<gene:campoScheda title="Tipo propriet&agrave;" entita="IMMTRAI"
						campoFittizio="true" 
						campo="PROIMM_${contatore}"
						value="${immobile[4]}" definizione="N2;0;A2137;;T2IPROIMM" />
					<gene:campoScheda title="Valore stimato dell'immobile" entita="IMMTRAI"
						campoFittizio="true" 
						campo="VALIMM_${contatore}"
						value="${immobile[5]}" definizione="F24.5;0;;MONEY;T2IVALIMM" />
				</gene:gruppoCampi>
				<gene:fnJavaScriptScheda funzione="changeImmtrai(${contatore})" elencocampi="IMMTRAI_DESIMM_${contatore};IMMTRAI_PROIMM_${contatore};IMMTRAI_VALIMM_${contatore}" esegui="false" />
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
	<c:forEach var="nuoviImmobili" begin="1"
		end="5" step="1">
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
					<gene:PopUp>
						<gene:PopUpItem title="Elimina immobile"
							href='eliminaImmobile(${contatore})' />
					</gene:PopUp>
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
			<gene:campoScheda title="Descrizione dell'immobile" entita="IMMTRAI"
				campoFittizio="true" visibile="true"
				campo="DESIMM_${contatore}"
				definizione="T400;0;;;T2IDESIMM" />
			<gene:campoScheda title="Tipo propriet&agrave;" entita="IMMTRAI"
				campoFittizio="true" visibile="true"
				campo="PROIMM_${contatore}"
				definizione="N2;0;A2137;;T2IPROIMM" />
			<gene:campoScheda title="Valore stimato dell'immobile" entita="IMMTRAI"
				campoFittizio="true" visibile="true"
				campo="VALIMM_${contatore}"
				definizione="F24.5;0;;MONEY;T2IVALIMM" />

		</gene:gruppoCampi>
		<gene:fnJavaScriptScheda funzione="changeImmtrai(${contatore})" elencocampi="IMMTRAI_DESIMM_${contatore};IMMTRAI_PROIMM_${contatore};IMMTRAI_VALIMM_${contatore}" esegui="false" />
		<c:set var="nuoviElementi" value="${nuoviElementi + 1}" />
		<c:set var="contatore" value="${contatore + 1}" />
	</c:forEach>
	<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
		<c:if test='${gene:checkProtFunz(pageContext, "INS","Agg-Immobili")}'>
			<gene:campoScheda nome="LinkVisualizzaNuovoImmobile">
				<td>&nbsp;</td>
				<td class="valore-dato"><a
					href="javascript:visualizzaProssimoImmobile();" class="link-generico"><img
					src="${pageContext.request.contextPath}/img/opzioni_add.gif"
					title="" alt="Aggiungi immobile" height="16" width="16">&nbsp;Aggiungi
				immobile</a></td>
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
		showObj("rowIMMTRAI_PROIMM_"  + idImmobile, false);
		showObj("rowIMMTRAI_VALIMM_"  + idImmobile, false);
		
		if(sbiancaValori){
			setValue("IMMTRAI_NUMIMM_"  + idImmobile, "");
			setValue("IMMTRAI_DESIMM_"  + idImmobile, "");
			setValue("IMMTRAI_PROIMM_"  + idImmobile, "");
			setValue("IMMTRAI_VALIMM_"  + idImmobile, "");
		}
	}
	
	function visualizzaSezioneImmobile(idImmobile){
		showObj("rowdatiImmobile_" + idImmobile, true);
		showObj("rowIMMTRAI_NUMIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_DESIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_PROIMM_"  + idImmobile, true);
		showObj("rowIMMTRAI_VALIMM_"  + idImmobile, true);
		showObj("rowdatiImmobile", false);
	}
	
	var indiceProgressivo = 0;
	for(indiceProgressivo=idUltimoImmobileVisualizzato+1; indiceProgressivo <= maxIdImmobileVisualizzabile; indiceProgressivo++)
	{
		nascondiSezioneImmobile(indiceProgressivo, false);
	}
	
	function visualizzaProssimoImmobile(){
		var indice = idUltimoImmobileVisualizzato;
		indice++;
		var immobileEliminato = getValue("IMMTRAI_DEL_IMMTRAI_" + indice);
		while(indice <= maxIdImmobileVisualizzabile && immobileEliminato != "0"){
			indice++;
		}
		
		if(immobileEliminato != null && immobileEliminato == "0") {
			idUltimoImmobileVisualizzato = indice;
			visualizzaSezioneImmobile(indice);
			setValue("IMMTRAI_USE_IMMTRAI_" + indice, "0");
		}

		if(indice == maxIdImmobileVisualizzabile){
			showObj("rowLinkVisualizzaNuovoImmobile", false);
			showObj("rowMsgUltimoImmobile", true);
		}
	}
	
	function eliminaImmobile(idImmobile){
		if(confirm("Procedere con l'eliminazione ?")){
			nascondiSezioneImmobile(idImmobile, false);
		  setValue("IMMTRAI_DEL_IMMTRAI_" + idImmobile, "1");
		}
	}
	
	function changeImmtrai(idImmobile){
		if(document.getElementById("IMMTRAI_MOD_IMMTRAI_" + idImmobile) != null)
		{
			setValue("IMMTRAI_MOD_IMMTRAI_" + idImmobile, "1");
		}
		else
		{
			setValue("IMMTRAI_INS_IMMTRAI_" + idImmobile, "1");
		}
	}
	
	<c:if
		test='${modoAperturaScheda eq "NUOVO" or (modoAperturaScheda eq "MODIFICA" and empty listaImmobiliDaTrasfInterv)}'>
		setValue("USE_IMMTRAI_1", "0");
	</c:if>
	</gene:javaScript>