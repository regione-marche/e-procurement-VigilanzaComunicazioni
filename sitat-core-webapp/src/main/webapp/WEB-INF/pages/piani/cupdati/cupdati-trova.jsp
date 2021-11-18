
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

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPDATIFunction" />

<gene:template file="ricerca-template.jsp" idMaschera="CUPDATI-trova"
	gestisciProtezioni="true" schema="PIANI">
	<gene:setString name="titoloMaschera" value="Ricerca CUP" />
	<gene:setString name="entita" value="CUPDATI" />
	<gene:redefineInsert name="corpo">
		<gene:formTrova entita="CUPDATI" gestisciProtezioni="true">
			<tr>
				<td colspan="3"><b>Dati generali</b></td>
			</tr>
			<gene:campoTrova campo="ANNO_DECISIONE" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoAnnoDecisione"/>
			<gene:campoTrova campo="CUP" />
			<gene:campoTrova title="Data assegnazione" campo="DATA_DEFINITIVO" />
			<gene:campoTrova campo="NATURA" defaultValue=""/>
			<gene:campoTrova campo="TIPOLOGIA" defaultValue=""/>
			
			<tr>
				<td class="etichetta-dato">Natura</td>
				<td class="operatore-trova">&nbsp;</td>
				<td class="valore-dato-trova">
					<select name="natura" id="natura" onchange="javascript:modificaNatura();">
						<option value="">&nbsp;</option>
						<c:if test='${!empty listaValoriNatura}'>
							<c:forEach items="${listaValoriNatura}" var="valoriNatura">
								<option value="${valoriNatura[0]}">${valoriNatura[2]}</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>

			<tr>	
				<td class="etichetta-dato">Tipologia</td>
				<td class="operatore-trova">&nbsp;</td>
				<td class="valore-dato-trova">
					<select name="tipologia" id="tipologia" onchange="javascript:modificaTipologia();">
						<option value="">&nbsp;</option>
					</select>
				</td>
			</tr>	
			
			<gene:campoTrova title="Descrizione" campo="DESCRIZIONE_INTERVENTO" />

		</gene:formTrova>
	</gene:redefineInsert>

	<gene:javaScript>
	
		setValue("Campo3","");
		setValue("Campo4","");
		showObj("rowCampo3",false);
		showObj("rowCampo4",false);
		
	
		var arrayTipologia=new Array();
		
		<c:forEach items="${listaValoriTipologia}" var="valoreTipologia">
			arrayTipologia.push(new tabellatoTipologia(${gene:string4Js(valoreTipologia[0])}, ${gene:string4Js(valoreTipologia[1])},${gene:string4Js(valoreTipologia[2])}));
		</c:forEach>
	
		function tabellatoTipologia(aCupcod1, aCupcod2, aCupDesc ) {
			var cupcod1;
			var cupcod2;
			var cupdesc;
			this.cupcod1 = aCupcod1;
			this.cupcod2 = aCupcod2;
			this.cupdesc = aCupDesc;
		}
	
		function modificaNatura(){
			var i;
			var valoreNatura = document.forms[0].natura.value;
			var optTipologia = document.forms[0].tipologia.options;
			optTipologia.length = 0;
			optTipologia.add(new Option("", ""));
			for(i=0; i < arrayTipologia.length ;i++){
				if (arrayTipologia[i].cupcod1 == valoreNatura) {
					optTipologia.add(new Option(arrayTipologia[i].cupdesc, arrayTipologia[i].cupcod2));
				}
			}
			setValue("Campo3",valoreNatura);
		}
		
		function modificaTipologia(){
			setValue("Campo4",document.forms[0].tipologia.value);
		}
	</gene:javaScript>
</gene:template>
