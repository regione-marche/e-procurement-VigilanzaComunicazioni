<%
  /*
   * Created on 11-gen-2018
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


<c:set var="ModificheRER" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.ModificheRER")}' />
<c:set var="contri" value="${gene:getValCampo(keyParent,'CONTRI')}" />
<c:set var="ModificheRER_RIS_CAPITOLO" value='${ModificheRER && contri eq 0}' />

<c:set var="tiprog" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, key)}' />
<c:if test='${empty tiprog}'>
	<c:set var="tipint"	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetInttriFabbisogniFunction", pageContext, key)}' scope="request"/>
	<c:set var="tiprog" value="${tipint}" />
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="RISCAPITOLO-scheda">
	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='Risorsa per capitolo di bilancio' />

		<gene:formScheda entita="RIS_CAPITOLO" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreRISCAPITOLO" >

				<gene:campoScheda campo="CONTRI" visibile="false" defaultValue='${gene:getValCampo(keyParent,"CONTRI")}'	/>
				<gene:campoScheda campo="CONINT" visibile="false" defaultValue='${gene:getValCampo(keyParent,"CONINT")}' />
				<gene:campoScheda campo="NUMCB" visibile="false" />

				<gene:campoScheda campo="NCAPBIL" />
				<gene:campoScheda campo="IMPSPE"  visibile="${tiprog eq '1'}" />
				<gene:campoScheda campo="RG1CB" visibile="${tiprog eq '2'}" />
				<gene:campoScheda campo="IMPRFSCB" visibile="${tiprog eq '2'}" />
				<gene:campoScheda campo="IMPALTCB" visibile="${tiprog eq '2'}" />
				<gene:campoScheda campo="VERIFBIL" visibile="${tiprog eq '2'}" />
				
				<c:choose>
					<c:when test='${tiprog eq 1 || ModificheRER_RIS_CAPITOLO}'>
						<c:set var="colspanGriglia" value="10" />
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
							<td colspan="${colspanGriglia}" style="BORDER-RIGHT: #A0AABA 1px solid;"><b><center>Quadro delle risorse per capitolo di bilancio</center></b></td>
						</tr>
						<tr>
							<td rowspan="2" class="valore-dato"><center><b>Tipologie Risorse</b></center></td>
							<td colspan="${colspanGriglia-1}" class="valore-dato" style="BORDER-RIGHT: #A0AABA 1px solid;"><center>Stima dei costi</center></td>
						</tr>
						<tr>
							<td id="scf_1a" colspan="2" class="valore-dato"><center>Primo anno</center></td>
							<td id="scf_2a" colspan="2" class="valore-dato"><center>Secondo anno</center></td>
							<td id="scf_3a" class="valore-dato" colspan="2"><center>Terzo anno</center></td>
							<td colspan="2" class="valore-dato"><center>Annualit&agrave; successive</center></td>
							<td colspan="2" class="valore-dato"><center>Totale</center></td>
						</tr>
						<tr>
							<td class="etichetta-dato"><b>Risorse derivanti da entrate aventi destinazione vincolata per legge</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="DV1CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="DV2CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="DV3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="DV9CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda id="DVNTRI" campo="DVNTRI" campoFittizio="true" title="Risorse derivanti da entrate aventi destinazione vincolata per legge" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato"><b>Risorse derivanti da entrate acquisite mediante contrazione di mutuo</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="MU1CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="MU2CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="MU3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="MU9CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda id="MUNTRI" campo="MUNTRI" campoFittizio="true" title="Risorse derivanti da entrate acquisite mediante contrazione di mutuo" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato"><b>Stanziamenti di bilancio</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="BI1CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="BI2CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="BI3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="BI9CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda id="BINTRI" campo="BINTRI" campoFittizio="true" title="Stanziamenti di bilancio" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato"><b>Finanziamenti art. 3 DL 310/1990</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="AP1CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AP2CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AP3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="AP9CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda id="APNTRI" campo="APNTRI" campoFittizio="true" title="Finanziamenti" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato"><b>Altra tipologia</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="AL1CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AL2CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AL3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="AL9CB" addTr="false" hideTitle="true"/>
							<gene:campoScheda id="ALNTRI" campo="ALNTRI" campoFittizio="true" title="Altra tipologia" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>
						
						<tr>
							<td class="etichetta-dato"><b>Importo complessivo</b></td>
							</gene:campoScheda>
								<gene:campoScheda campo="TO1CB" modificabile="false" addTr="false" hideTitle="true">
								<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#RIS_CAPITOLO_DV1CB#","#RIS_CAPITOLO_MU1CB#","#RIS_CAPITOLO_BI1CB#","#RIS_CAPITOLO_AP1CB#","#RIS_CAPITOLO_AL1CB#")))'
									elencocampi="RIS_CAPITOLO_DV1CB;RIS_CAPITOLO_MU1CB;RIS_CAPITOLO_BI1CB;RIS_CAPITOLO_AP1CB;RIS_CAPITOLO_AL1CB" />
								</gene:campoScheda>

								<gene:campoScheda campo="TO2CB" modificabile="false" addTr="false" hideTitle="true">
								<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#RIS_CAPITOLO_DV2CB#","#RIS_CAPITOLO_MU2CB#","#RIS_CAPITOLO_BI2CB#","#RIS_CAPITOLO_AP2CB#","#RIS_CAPITOLO_AL2CB#")))'
									elencocampi="RIS_CAPITOLO_DV2CB;RIS_CAPITOLO_MU2CB;RIS_CAPITOLO_BI2CB;RIS_CAPITOLO_AP2CB;RIS_CAPITOLO_AL2CB" />
								</gene:campoScheda>

								<gene:campoScheda campo="TO3CB" modificabile="false" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}">
								<gene:calcoloCampoScheda 
									funzione='toMoney(sommaCampi(new Array("#RIS_CAPITOLO_DV3CB#","#RIS_CAPITOLO_MU3CB#","#RIS_CAPITOLO_BI3CB#","#RIS_CAPITOLO_AP3CB#","#RIS_CAPITOLO_AL3CB#")))'
									elencocampi="RIS_CAPITOLO_DV3CB;RIS_CAPITOLO_MU3CB;RIS_CAPITOLO_BI3CB;RIS_CAPITOLO_AP3CB;RIS_CAPITOLO_AL3CB" />
								</gene:campoScheda>

								<gene:campoScheda campo="TO9CB" modificabile="false" addTr="false" hideTitle="true">
								<gene:calcoloCampoScheda 
									funzione='toMoney(sommaCampi(new Array("#RIS_CAPITOLO_DV9CB#","#RIS_CAPITOLO_MU9CB#","#RIS_CAPITOLO_BI9CB#","#RIS_CAPITOLO_AP9CB#","#RIS_CAPITOLO_AL9CB#")))'
									elencocampi="RIS_CAPITOLO_DV9CB;RIS_CAPITOLO_MU9CB;RIS_CAPITOLO_BI9CB;RIS_CAPITOLO_AP9CB;RIS_CAPITOLO_AL9CB" />
								</gene:campoScheda>

								<gene:campoScheda campo="TOTCB" campoFittizio="true" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
									<gene:fnJavaScriptScheda 
									funzione='setValue("TOTCB",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO1CB#","#RIS_CAPITOLO_TO2CB#","#RIS_CAPITOLO_TO3CB#","#RIS_CAPITOLO_TO9CB#"))))'
									elencocampi="RIS_CAPITOLO_TO1CB;RIS_CAPITOLO_TO2CB;RIS_CAPITOLO_TO3CB;RIS_CAPITOLO_TO9CB" esegui="true" />
							<gene:campoScheda addTr="false">
						</tr>

						<tr>
							<td class="etichetta-dato"><b>Spese gi&agrave; sostenute</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="GIASOS1" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
							<gene:campoScheda campo="GIASOS2" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
							<gene:campoScheda campo="GIASOS3" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="GIASOS9" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="true" />
							<gene:campoScheda campo="SPESESOST" modificabile="true" addTr="false" hideTitle="true" />
							
							<gene:campoScheda addTr="false">
						</tr>

						<tr>
							<td class="etichetta-dato"><b>Totale</b></td>
							</gene:campoScheda>
														
							<gene:campoScheda campo="TOT1" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
							<gene:campoScheda campo="TOT2" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
							<gene:campoScheda campo="TOT3" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="TOT9" campoFittizio="true" definizione="F24.5;0;;;" modificabile="false" addTr="false" hideTitle="true" />
							<gene:campoScheda id="TOTINT" campo="TOTINT" campoFittizio="true" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
							<gene:fnJavaScriptScheda funzione='setValue("TOTINT",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO1CB#","#RIS_CAPITOLO_TO2CB#","#RIS_CAPITOLO_TO3CB#","#RIS_CAPITOLO_TO9CB#","#RIS_CAPITOLO_SPESESOST#"))));calcoloImpNettoIvaQR();' 
									elencocampi="RIS_CAPITOLO_TO1CB;RIS_CAPITOLO_TO2CB;RIS_CAPITOLO_TO3CB;RIS_CAPITOLO_TO9CB;RIS_CAPITOLO_SPESESOST" esegui="true"/>

							<gene:campoScheda addTr="false">
						</tr>

						<tr id="di_cui_iva">
							<td class="etichetta-dato"><b>Di cui IVA</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="IV1CB" addTr="false" hideTitle="true" />
							<gene:campoScheda campo="IV2CB" addTr="false" hideTitle="true" />
<%-- 							<gene:campoScheda campo="IV3CB" campoFittizio="true" definizione="F24.5;0;;MONEY;" visibile="${tiprog eq '1'}" modificabile="false" addTr="false" hideTitle="true" /> --%>
							<gene:campoScheda campo="IV3CB" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}"/>
							<gene:campoScheda campo="IV9CB" addTr="false" hideTitle="true" />
							<gene:campoScheda id="IVNTRI" campo="IVNTRI" campoFittizio="true" title ="Di cui IVA" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:campoScheda addTr="false">
						</tr>

						<tr id="imp_netto_iva">
							<td class="etichetta-dato"><b>Importo al netto di IVA</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="SI1TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 1° anno" 
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("SI1TRI",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO1CB#",-"#RIS_CAPITOLO_IV1CB#"))))' elencocampi="RIS_CAPITOLO_TO1CB;RIS_CAPITOLO_IV1CB" esegui="true"/>
							
							<gene:campoScheda campo="SI2TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 2° anno"  
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("SI2TRI",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO2CB#",-"#RIS_CAPITOLO_IV2CB#"))))' elencocampi="RIS_CAPITOLO_TO2CB;RIS_CAPITOLO_IV2CB" esegui="true"/>

<%-- 							<gene:campoScheda campo="SI3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"   --%>
<%-- 								visibile="${tiprog eq '1'}" modificabile="false" addTr="false" hideTitle="true"> --%>
<%-- 							</gene:campoScheda> --%>

							<gene:campoScheda campo="SI3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"  
								modificabile="false" addTr="false" hideTitle="true" visibile="${tiprog eq '1' || ModificheRER_RIS_CAPITOLO}">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("SI3TRI",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO3CB#",-"#RIS_CAPITOLO_IV3CB#"))))' elencocampi="RIS_CAPITOLO_TO3CB;RIS_CAPITOLO_IV3CB" esegui="true"/>

							
							<gene:campoScheda campo="SI9TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; annualit&agrave; successive"  
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("SI9TRI",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO9CB#",-"#RIS_CAPITOLO_IV9CB#"))))' elencocampi="RIS_CAPITOLO_TO9CB;RIS_CAPITOLO_IV9CB" esegui="true" />
							
							<gene:campoScheda id="SINTRI" campo="SINTRI" campoFittizio="true" title ="Importo al netto di IVA" definizione="F24.5;0;;MONEY;" modificabile="false" addTr="false" hideTitle="true" />
							<gene:fnJavaScriptScheda funzione='setValue("SINTRI",toMoney(sommaCampi(new Array("#RIS_CAPITOLO_TO1CB#",-"#RIS_CAPITOLO_IV1CB#","#RIS_CAPITOLO_TO2CB#",-"#RIS_CAPITOLO_IV2CB#","#RIS_CAPITOLO_TO3CB#",-"#RIS_CAPITOLO_IV3CB#","#RIS_CAPITOLO_TO9CB#",-"#RIS_CAPITOLO_IV9CB#"))))' 
								elencocampi="RIS_CAPITOLO_TO1CB;RIS_CAPITOLO_IV1CB,RIS_CAPITOLO_TO2CB;RIS_CAPITOLO_IV2CB,RIS_CAPITOLO_TO3CB;RIS_CAPITOLO_IV3CB,RIS_CAPITOLO_TO9CB;RIS_CAPITOLO_IV9CB" esegui="true" />

							<gene:campoScheda addTr="false">
						</tr>
					</table>
					<br>
					</td>
					</tr>
				</gene:campoScheda>

				<gene:gruppoCampi idProtezioni="CB_ALTRI_DATI">
					<gene:campoScheda>
						<td colspan="2"><b>Altri dati</b></td>
					</gene:campoScheda>
					<gene:campoScheda campo="CBNOTE" />
				</gene:gruppoCampi>

				<gene:campoScheda addTr="true">
					</gene:campoScheda>
				<gene:campoScheda></gene:campoScheda>

			<gene:campoScheda>
				<c:if test='${ (qe_sl eq "1") || !(stato eq "1" || stato eq "2" || stato eq "11" || stato eq "12") || (gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI") && datiRiga.RIS_CAPITOLO_CONTRI eq 0) }' >
					<gene:redefineInsert name="schedaModifica"/>
					<gene:redefineInsert name="pulsanteModifica" />
					<gene:redefineInsert name="schedaNuovo"/>
					<gene:redefineInsert name="pulsanteNuovo" />
				</c:if>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
				
			</gene:campoScheda>
		</gene:formScheda>
		<gene:javaScript>
			
			$(document).ready(function() {	
				var ailint = '${ailint}';
				var primoAnno = "";
				var secondoAnno = "";
				var terzoAnno = "";
				if (ailint != "") {
					primoAnno = parseInt(ailint);
					secondoAnno = parseInt(ailint) + 1;
					terzoAnno = parseInt(ailint) + 2;
				}
				$('#scf_1a').html('<center>Primo anno<br>' +  primoAnno + '</center>');
				$('#scf_2a').html('<center>Secondo anno<br>' +  secondoAnno + '</center>');
				$('#scf_3a').html('<center>Terzo anno<br>' +  terzoAnno + '</center>');			
			});
			
			function sommaCampi(valori)	{
			  var i, ret=0;
			  for (i=0;i < valori.length;i++) {
			  	if (valori[i] != "") {
			    	ret += eval(valori[i]);
			  	}
			  }
			  return eval(ret).toFixed(2);
	 		}
			
			$('[id^="RIS_CAPITOLO_DV"]').on("change",
				function() {
					calcoloImportiTotaliQR("DV")
				}
			);
			
			$('[id^="RIS_CAPITOLO_MU"]').on("change",
				function() {
					calcoloImportiTotaliQR("MU")
				}
			);

			$('[id^="RIS_CAPITOLO_BI"]').on("change",
				function() {
					calcoloImportiTotaliQR("BI")
				}
			);

			$('[id^="RIS_CAPITOLO_AP"]').on("change",
				function() {
					calcoloImportiTotaliQR("AP")
				}
			);

			$('[id^="RIS_CAPITOLO_AL"]').on("change",
				function() {
					calcoloImportiTotaliQR("AL")
				}
			);

			$('[id^="RIS_CAPITOLO_IV"]').on("change",
				function() {
					calcoloImportiTotaliQR("IV")
				}
			);

			function calcoloImportiTotaliQR(radix) {
				var imp_somma = 0;
				if ($('#RIS_CAPITOLO_' + radix + '1CB').val() != null && $('#RIS_CAPITOLO_' + radix + '1CB').val() != "") {
					imp_somma = imp_somma + parseFloat($('#RIS_CAPITOLO_' + radix + '1CB').val());
				}
				
				if ($('#RIS_CAPITOLO_' + radix + '2CB').val() != null && $('#RIS_CAPITOLO_' + radix + '2CB').val() != "") {
					imp_somma = imp_somma + parseFloat($('#RIS_CAPITOLO_' + radix + '2CB').val());
				}
				
				if ($('#RIS_CAPITOLO_' + radix + '3CB').val() != null && $('#RIS_CAPITOLO_' + radix + '3CB').val() != "") {
					imp_somma = imp_somma + parseFloat($('#RIS_CAPITOLO_' + radix + '3CB').val());
				}
				
				if ($('#RIS_CAPITOLO_' + radix + '9CB').val() != null && $('#RIS_CAPITOLO_' + radix + '9CB').val() != "") {
					imp_somma = imp_somma + parseFloat($('#RIS_CAPITOLO_' + radix + '9CB').val());
				}
				
				if (imp_somma >= 0) {
					valimp = toMoney(imp_somma);
					setValue(radix + 'NTRI',valimp);
					calcoloImpNettoIvaQR();
				}
			}
		
			function calcoloImpNettoIvaQR() {
				var imp_somma = 0;
				var imp_diff = 0;
				if ($('#TOTINT').val() != null && $('#TOTINT').val() != "") {
					imp_somma = imp_somma + parseFloat($('#TOTINT').val());
				}
				
				if ($('#IVNTRI').val() != null && $('#IVNTRI').val() != "") {
					imp_diff = imp_diff + parseFloat($('#IVNTRI').val());
				}
				/*
				if ($('#RIS_CAPITOLO_IV1CB').val() != null && $('#RIS_CAPITOLO_IV1CB').val() != "") {
					imp_diff = imp_diff + parseFloat($('#INTTRI_IV1TRI').val());
				}
				if ($('#RIS_CAPITOLO_IV2CB').val() != null && $('#RIS_CAPITOLO_IV2CB').val() != "") {
					imp_diff = imp_diff + parseFloat($('#RIS_CAPITOLO_IV2CB').val());
				}
				if ($('#RIS_CAPITOLO_IV3CB').val() != null && $('#RIS_CAPITOLO_IV3CB').val() != "") {
					imp_diff = imp_diff + parseFloat($('#RIS_CAPITOLO_IV3CB').val());
				}
				if ($('#RIS_CAPITOLO_IV9CB').val() != null && $('#RIS_CAPITOLO_IV9CB').val() != "") {
					imp_diff = imp_diff + parseFloat($('#RIS_CAPITOLO_IV9CB').val());
				}
				*/
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
		calcoloImportiTotaliQR("AL");
		calcoloImportiTotaliQR("DF");
		<c:if test='${tiprog eq 2}'>
			calcoloImportiTotaliQR("IV");
			calcoloImpNettoIvaQR();
		</c:if>

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
		<c:when test='${gene:checkProt(pageContext, "COLS.VIS.PIANI.RIS_CAPITOLO.IV1CB")}'>
			$('#di_cui_iva').show();
			$('#imp_netto_iva').show();
		</c:when>
		<c:otherwise>
			$('#di_cui_iva').hide();
			$('#imp_netto_iva').hide();
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test='${tiprog eq 2 and not ModificheRER_RIS_CAPITOLO}'>
			$('#scf_3a').hide();
		</c:when>
		<c:otherwise>
			$('#scf_3a').show();
		</c:otherwise>
	</c:choose>
	
		$('.importo').css("width","100");
		$('.importoNoEdit').css("width","100");
		

		</gene:javaScript>
	</gene:redefineInsert>
</gene:template>
