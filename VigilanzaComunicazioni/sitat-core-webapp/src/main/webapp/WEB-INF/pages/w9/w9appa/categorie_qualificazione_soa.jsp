
<%
/*
 * Created on: 24/09/2009
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

<fmt:setBundle basename="AliceResources" />

	<gene:gruppoCampi idProtezioni="CATQU">
		<gene:campoScheda>
			<td colspan="2"><b>Categorie di qualificazione SOA</b></td>
		</gene:campoScheda>
<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}'></c:set>
<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}'></c:set>
<c:set var="numappa" value='${gene:getValCampo(key,"NUM_APPA")}'></c:set>
<c:set var="contatore" value="1" scope="page"/>
		<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetCatQualificazioneSoaFunction" parametro="${param.keyW9requ}" />
		<c:choose>
		<c:when test='${!empty listaCatQualificazioneSoa}'>
			<c:forEach items="${listaCatQualificazioneSoa}" var="catQualificazioneSoa"
				varStatus="indiceListaCatQualificazioneSoa">
				<gene:gruppoCampi>
					<gene:campoScheda
						campo="DEL_W9REQU_${contatore}" entita="W9REQU"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda campo="MOD_W9REQU_${contatore}" entita="W9REQU"
						campoFittizio="true" visibile="false" definizione="T1" value="0" />
					<gene:campoScheda nome="datiCatQualificazioneSoa_${contatore}">
						<td colspan="2"><b>Scheda Categoria di qualificazione SOA
						${contatore}</b> <c:if
							test='${modoAperturaScheda ne "VISUALIZZA" and gene:checkProtFunz(pageContext, "DEL","Elimina-CatQualificazioneSoa")}'>
							<gene:PopUp>
								<gene:PopUpItem title="Elimina categoria di qualificazione SOA"
									href='eliminaCatQualificazioneSoa(${contatore})' />
							</gene:PopUp>
						</c:if></td>
					</gene:campoScheda>

					
					<gene:campoScheda 
						campoFittizio="true" entita="W9REQU" 
						campo="CODGARA_${contatore}"
						value="${catQualificazioneSoa[0]}" definizione="N10;1;;;CODGARA" visibile="false" />
					<gene:campoScheda 
						campoFittizio="true"  entita="W9REQU"
						campo="CODLOTT_${contatore}"
						value="${catQualificazioneSoa[1]}" definizione="N10;1;;;CODLOTT" visibile="false" />
					<gene:campoScheda 
						campoFittizio="true" entita="W9REQU" 
						campo="NUM_APPA_${contatore}"
						value="${catQualificazioneSoa[2]}" definizione="N3;1;;;NUM_APPA" visibile="false" />
					<gene:campoScheda 
						campoFittizio="true" entita="W9REQU" 
						campo="NUM_REQU_${contatore}"
						value="${catQualificazioneSoa[3]}" definizione="N3;1;;;NUM_REQU" visibile="false" />
					<gene:campoScheda entita="W9REQU" title="Categorie"
						campoFittizio="true" 
						campo="ID_CATEGORIA_${contatore}"
						value="${catQualificazioneSoa[4]}" definizione="T5;0;W3z03;;ID_CATEGORIA" />
					<gene:campoScheda title="Classe importo" entita="W9REQU"
						campoFittizio="true" 
						campo="CLASSE_IMPORTO_${contatore}"
						value="${catQualificazioneSoa[5]}" definizione="T5;0;G_z09;;CLASSE_IMPORTO" />
					<gene:campoScheda title="Categoria prevalente ?" entita="W9REQU"
						campoFittizio="true" 
						campo="PREVALENTE_${contatore}"
						value="${catQualificazioneSoa[6]}" definizione="T1;0;;SN;PREVALENTE" />
					<gene:campoScheda title="Categoria scorporabile ?" entita="W9REQU"
						campoFittizio="true" 
						campo="SCORPORABILE_${contatore}"
						value="${catQualificazioneSoa[7]}" definizione="T1;0;;SN;SCORPORABILE" />
					<gene:campoScheda title="Categoria subappaltabile ?" entita="W9REQU"
						campoFittizio="true" 
						campo="SUBAPPALTABILE_${contatore}"
						value="${catQualificazioneSoa[8]}" definizione="T1;0;;SN;SUBAPPALTABILE" />
					
				</gene:gruppoCampi>
				<gene:fnJavaScriptScheda funzione="changeW9fin(${contatore})" elencocampi="W9REQU_ID_CATEGORIA_${contatore};W9REQU_CLASSE_IMPORTO_${contatore};W9REQU_PREVALENTE_${contatore};W9REQU_SCORPORABILE_${contatore};W9REQU_SUBAPPALTABILE_${contatore}" esegui="false" />
				<c:set var="contatore" value="${contatore +1}" />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:if test='${modoAperturaScheda ne "NUOVO"}'>
				<gene:gruppoCampi>
					<gene:campoScheda nome="datiCatQualificazioneSoa">
						<td colspan="2"><b>Nessuna categoria di qualificazione SOA inserita</b></td>
					</gene:campoScheda>
				</gene:gruppoCampi>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:set var="nuoviElementi" value="0" />
	<c:forEach var="nuoviCatQualificazioneSoa" begin="1"
		end="5" step="1">
		<gene:gruppoCampi>
			<gene:campoScheda campo="DEL_W9REQU_${contatore}" entita="W9REQU"
				campoFittizio="true" visibile="false" definizione="T1" value="0" />
			<gene:campoScheda campo="INS_W9REQU_${contatore}" entita="W9REQU"
				campoFittizio="true" visibile="false" definizione="T1" value="0" />
			<gene:campoScheda nome="datiCatQualificazioneSoa_${contatore}" visibile="true">
				<td colspan="2">
				<b>Nuova categoria di qualificazione SOA ${nuoviElementi + 1}</b>
				<c:if
					test='${modoAperturaScheda ne "VISUALIZZA" and gene:checkProtFunz(pageContext, "DEL","Elimina-CatQualificazioneSoa")}'>
					<gene:PopUp>
						<gene:PopUpItem title="Elimina categoria di qualificazione SOA"
							href='eliminaCatQualificazioneSoa(${contatore})' />
					</gene:PopUp>
				</c:if></td>
			</gene:campoScheda>
			
			<gene:campoScheda 
				campoFittizio="true" entita="W9REQU" 
				campo="CODGARA_${contatore}"
				value="${codgara}" definizione="N10;1;;;CODGARA" visibile="false" />
			<gene:campoScheda 
				campoFittizio="true"  entita="W9REQU"
				campo="CODLOTT_${contatore}"
				value="${codlott}" definizione="N10;1;;;CODLOTT" visibile="false" />
			<gene:campoScheda 
				campoFittizio="true" entita="W9REQU" 
				campo="NUM_APPA_${contatore}"
				value="${numappa}" definizione="N3;1;;;NUM_APPA" visibile="false" />
			<gene:campoScheda 
				campoFittizio="true" entita="W9REQU" 
				campo="NUM_REQU_${contatore}"
				definizione="N3;1;;;NUM_REQU" visibile="false" />
			<gene:campoScheda entita="W9REQU" title="Categorie"
				campoFittizio="true" 
				campo="ID_CATEGORIA_${contatore}"
				definizione="T5;0;W3z03;;ID_CATEGORIA" />
			<gene:campoScheda title="Classe importo" entita="W9REQU"
				campoFittizio="true" 
				campo="CLASSE_IMPORTO_${contatore}"
				definizione="T5;0;G_z09;;CLASSE_IMPORTO" />
			<gene:campoScheda title="Categoria prevalente ?" entita="W9REQU"
				campoFittizio="true" 
				campo="PREVALENTE_${contatore}"
				definizione="T1;0;;SN;PREVALENTE" />
			<gene:campoScheda title="Categoria scorporabile ?" entita="W9REQU"
				campoFittizio="true" 
				campo="SCORPORABILE_${contatore}"
				definizione="T1;0;;SN;SCORPORABILE" />
			<gene:campoScheda title="Categoria subappaltabile ?" entita="W9REQU"
				campoFittizio="true" 
				campo="SUBAPPALTABILE_${contatore}"
				definizione="T1;0;;SN;SUBAPPALTABILE" />

		</gene:gruppoCampi>
		<c:set var="nuoviElementi" value="${nuoviElementi + 1}" />
		<gene:fnJavaScriptScheda funzione="changeW9fin(${contatore})" elencocampi="W9REQU_ID_CATEGORIA_${contatore};W9REQU_CLASSE_IMPORTO_${contatore};W9REQU_PREVALENTE_${contatore};W9REQU_SCORPORABILE_${contatore};W9REQU_SUBAPPALTABILE_${contatore}" esegui="false" />
		<c:set var="contatore" value="${contatore + 1}" />
	</c:forEach>
	<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
		<c:if test='${gene:checkProtFunz(pageContext, "INS","Agg-CatQualificazioneSoa")}'>
			<gene:campoScheda nome="LinkVisualizzaNuovoCatQualificazioneSoa">
				<td>&nbsp;</td>
				<td class="valore-dato"><a
					href="javascript:visualizzaProssimoCatQualificazioneSoa();" class="link-generico"><img
					src="${pageContext.request.contextPath}/img/opzioni_add.gif"
					title="" alt="Aggiungi categoria di qualificazione SOA" height="16" width="16">&nbsp;Aggiungi
				categoria di qualificazione SOA</a></td>
			</gene:campoScheda>
			<gene:campoScheda nome="MsgUltimoCatQualificazioneSoa">
				<td>&nbsp;</td>
				<td class="valore-dato">Raggiunto il numero massimo di categorie di qualificazione SOA,
				per aggiungerne altri &egrave; necessario salvare.</td>
			</gene:campoScheda>
			<gene:campoScheda campo="NUMERO_W9REQU" campoFittizio="true" visibile="false" definizione="N3" value="${nuoviElementi+0+contatore}" />
		</c:if>
	</c:if>
	</gene:gruppoCampi>

	<gene:javaScript>
	showObj("rowMsgUltimoCatQualificazioneSoa", false);
	<c:choose>
		<c:when test='${(!empty listaCatQualificazioneSoa)}'>
			var idUltimoCatQualificazioneSoaVisualizzato = ${fn:length(listaCatQualificazioneSoa)};
			var maxIdCatQualificazioneSoaVisualizzabile = ${fn:length(listaCatQualificazioneSoa)+5};
		</c:when>
		<c:otherwise>
			var idUltimoCatQualificazioneSoaVisualizzato = 0;
			var maxIdCatQualificazioneSoaVisualizzabile = 5;
		</c:otherwise>
	</c:choose>
	function nascondiSezioneCatQualificazioneSoa(idCatQualificazioneSoa, sbiancaValori){
		showObj("rowdatiCatQualificazioneSoa_" + idCatQualificazioneSoa, false);
		showObj("rowW9REQU_ID_CATEGORIA_"  + idCatQualificazioneSoa, false);
		showObj("rowW9REQU_CLASSE_IMPORTO_"  + idCatQualificazioneSoa, false);
		showObj("rowW9REQU_PREVALENTE_"  + idCatQualificazioneSoa, false);
		showObj("rowW9REQU_SCORPORABILE_"  + idCatQualificazioneSoa, false);
		showObj("rowW9REQU_SUBAPPALTABILE_"  + idCatQualificazioneSoa, false);
		
		if(sbiancaValori){
			setValue("W9REQU_ID_CATEGORIA_"  + idCatQualificazioneSoa, "");
			setValue("W9REQU_CLASSE_IMPORTO_"  + idCatQualificazioneSoa, "");
			setValue("W9REQU_PREVALENTE_"  + idCatQualificazioneSoa, "");
			setValue("W9REQU_SCORPORABILE_"  + idCatQualificazioneSoa, "");
			setValue("W9REQU_SUBAPPALTABILE_"  + idCatQualificazioneSoa, "");
		}
	}
	
	function visualizzaSezioneCatQualificazioneSoa(idCatQualificazioneSoa){
		showObj("rowdatiCatQualificazioneSoa_" + idCatQualificazioneSoa, true);
		showObj("rowW9REQU_ID_CATEGORIA_"  + idCatQualificazioneSoa, true);
		showObj("rowW9REQU_CLASSE_IMPORTO_"  + idCatQualificazioneSoa, true);
		showObj("rowW9REQU_PREVALENTE_"  + idCatQualificazioneSoa, true);
		showObj("rowW9REQU_SCORPORABILE_"  + idCatQualificazioneSoa, true);
		showObj("rowW9REQU_SUBAPPALTABILE_"  + idCatQualificazioneSoa, true);
		showObj("rowdatiCatQualificazioneSoa", false);
	}
	
	var indiceProgressivo = 0;
	for(indiceProgressivo=idUltimoCatQualificazioneSoaVisualizzato+1; indiceProgressivo <= maxIdCatQualificazioneSoaVisualizzabile; indiceProgressivo++)
	{
		if(indiceProgressivo == 1 && ${modo eq "NUOVO"})
		{
			idUltimoCatQualificazioneSoaVisualizzato = 1;
		}
		else
		{
			nascondiSezioneCatQualificazioneSoa(indiceProgressivo, false);
		}
	}
	
	function visualizzaProssimoCatQualificazioneSoa(){
		var indice = idUltimoCatQualificazioneSoaVisualizzato;
		indice++;
		var catQualificazioneSoaEliminato = getValue("W9REQU_DEL_W9REQU_" + indice);
		while(indice <= maxIdCatQualificazioneSoaVisualizzabile && catQualificazioneSoaEliminato != "0"){
			indice++;
		}
		
		if(catQualificazioneSoaEliminato != null && catQualificazioneSoaEliminato == "0") {
			idUltimoCatQualificazioneSoaVisualizzato = indice;
			visualizzaSezioneCatQualificazioneSoa(indice);
			setValue("W9REQU_USE_W9REQU_" + indice, "0");
		}

		if(indice == maxIdCatQualificazioneSoaVisualizzabile){
			showObj("rowLinkVisualizzaNuovoCatQualificazioneSoa", false);
			showObj("rowMsgUltimoCatQualificazioneSoa", true);
		}
	}
	
	function eliminaCatQualificazioneSoa(idCatQualificazioneSoa){
		if(confirm("Procedere con l'eliminazione ?")){
			nascondiSezioneCatQualificazioneSoa(idCatQualificazioneSoa, false);
		  setValue("W9REQU_DEL_W9REQU_" + idCatQualificazioneSoa, "1");
		}
	}
	
	function changeW9fin(idCatQualificazioneSoa){
		if(document.getElementById("W9REQU_MOD_W9REQU_" + idCatQualificazioneSoa) != null)
		{
			setValue("W9REQU_MOD_W9REQU_" + idCatQualificazioneSoa, "1");
		}
		else
		{
			setValue("W9REQU_INS_W9REQU_" + idCatQualificazioneSoa, "1");
		}
	}
	
	
</gene:javaScript>
