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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="idUtente" value="${sessionScope.profiloUtente.id}" />
<c:set var="nomeUtente" value="${sessionScope.profiloUtente.nome}" />

<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
<c:set var="fabbisogni" value="${param.fabbisogni}" />
<c:set var="funzionalita" value="${param.funzionalita}" />
<c:set var="contri_PT" value="${param.contri_PT}" />
<c:set var="conint_PT" value="${param.conint_PT}" />



<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PTAPPROVAZIONI-scheda">
	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='Approvazione del fabbisogno' />

		<c:choose>
			<c:when test="${fn:contains(funzionalita, 'RDP')}">
				<c:set var="faseappr" value="3" />
			</c:when>
			<c:when test="${funzionalita eq 'RDS_Annulla' || funzionalita eq 'RDS_ApprovaRichiediRevisione' || funzionalita eq 'RDS_RichiediRevisione'}">
				<c:set var="faseappr" value="2" />
			</c:when>
			<c:otherwise>
				<c:set var="faseappr" value= "1" />
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${funzionalita eq 'RDP_RimuoviRichiediRevisione'}">
				<c:set var="esitoappr" value="3" />
				<c:set var="modificaesito" value="no" />
			</c:when>
			<c:when test="${funzionalita eq 'RDS_Annulla'}">
				<c:set var="esitoappr" value="4" />
				<c:set var="modificaesito" value="no" />
			</c:when>
			<c:when test="${funzionalita eq 'RDS_RichiediRevisione'}">
				<c:set var="esitoappr" value="3" />
				<c:set var="modificaesito" value="no" />
			</c:when>
			<c:otherwise>
				<c:set var="modificaesito" value="si" />
			</c:otherwise>
		</c:choose>
		
		<gene:formScheda entita="PTAPPROVAZIONI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreApprovazioni" >

			<gene:campoScheda campo="CONINT" visibile="false" />
			<gene:campoScheda campo="NUMAPPR" visibile="false" />
			<gene:campoScheda campo="DATAAPPR" modificabile="false"/>
			<gene:campoScheda campo="SYSAPPR" defaultValue='${idUtente}' visibile="false" />
			<gene:campoScheda campo="UTENTEAPPR" defaultValue='${nomeUtente}' visibile="false" />
			<gene:campoScheda campo="FASEAPPR" visibile="true" defaultValue="${faseappr}" modificabile="false"/>
			<gene:campoScheda campo="ESITOAPPR" obbligatorio="true" defaultValue="${esitoappr}" modificabile="${gene:if(modificaesito eq 'no',false,true)}"/>
			<gene:campoScheda>
				<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_ACCETTA_DICHIARAZIONE")}'>
				<tr id="sezioneDichiarazione" style="visibility:collapse;">
					<td class="etichetta-dato">Dichiarazione (*)</td>
					<td class="valore-dato">						
						<input type="checkbox" name="dichiarazione" id="dichiarazione"/>
						Dichiaro che l'intervento è programmato nel rispetto dei documenti programmatori e in coerenza con il bilancio e secondo le norme che disciplinano la programmazione economico-finanziaria degli enti, come previsto dall'art. 21, c. 1 del D.Lgs. n. 50/2016
					</td>
				</tr>
				</c:if>
			</gene:campoScheda>
			<gene:campoScheda campo="NOTEAPPR" >
				<gene:checkCampoScheda funzione='!("##" == "" && "#PTAPPROVAZIONI_ESITOAPPR#" > 1)' obbligatorio="true" messaggio="Specificare una nota." 
										onsubmit="true"/> 
			</gene:campoScheda>
			<gene:fnJavaScriptScheda funzione='gestioneBottoni()' elencocampi="PTAPPROVAZIONI_ESITOAPPR" esegui="true"/>

			<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
			<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
			<input type="hidden" name="fabbisogni" id="fabbisogni" value="${fabbisogni}" />
			<input type="hidden" name="funzionalita" id="funzionalita" value="${funzionalita}" />	
			<input type="hidden" name="contri_PT" id="contri_PT" value="${contri_PT}" />	
			<input type="hidden" name="conint_PT" id="conint_PT" value="${conint_PT}" />	

			<gene:redefineInsert name="schedaNuovo" />

			<gene:redefineInsert name="schedaConferma" />

			<gene:campoScheda>
				<tr>
					<td class="comandi-dettaglio" colSpan="2">						
						<INPUT id="bottone_Valida" type="button" class="bottone-azione" value="Continua" title="Continua" onclick="javascript:validazione();">&nbsp;
						<INPUT id="bottone_Conferma" type="button" class="bottone-azione" value="Conferma" title="Conferma" onclick="javascript:schedaConferma();">&nbsp;
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:schedaAnnulla()">&nbsp;
					</td>
				</tr>		
			</gene:campoScheda>
			
			
		</gene:formScheda>
		
		<gene:javaScript>
			initDataAppr();
		
			<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
			<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->				
			impostaEsitoAppr();
	
			document.forms[0].jspPathTo.value="piani/ptapprovazioni/ptapprovazioni-conclusione-funzionalita.jsp";

			<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
			<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
			function impostaEsitoAppr() {
				var funzionalita = $('#funzionalita').val();
				switch(funzionalita) {
				    case "RDP_RichiediRevisioneRespingi":
				        $("#PTAPPROVAZIONI_ESITOAPPR option[value='1']").remove();
				        $("#PTAPPROVAZIONI_ESITOAPPR option[value='4']").remove();
				        break;
				    case "RDS_ApprovaRichiediRevisione":
				        $("#PTAPPROVAZIONI_ESITOAPPR option[value='2']").remove();
				        $("#PTAPPROVAZIONI_ESITOAPPR option[value='4']").remove();
				        break;
				    case "RDS_Annulla":
				    	break;
				    default:
				    	$("#PTAPPROVAZIONI_ESITOAPPR option[value='4']").remove();
				        break;
				}
			}
			
			function gestioneBottoni() {
				var funzionalita = $('#funzionalita').val();
				var esito = getValue("PTAPPROVAZIONI_ESITOAPPR");
				showObj("bottone_Valida", false);
				showObj("bottone_Conferma", true);
				<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_ACCETTA_DICHIARAZIONE")}'>
					document.getElementById('sezioneDichiarazione').style.visibility = "collapse";
				</c:if>
				if(esito == 1 && (funzionalita == "RAF_ApprovaRespingi" || 
				funzionalita == "RDS_ApprovaRichiediRevisione"))
				{
					showObj("bottone_Valida", true);
					showObj("bottone_Conferma", false);
					<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_ACCETTA_DICHIARAZIONE")}'>
						document.getElementById('sezioneDichiarazione').style.visibility = "visible";
					</c:if>
				}
			}

			function initDataAppr() {
			
				var dataOggi = new Date();
				var giorno = dataOggi.getDate();
				if(giorno < 10){
					giorno = "0" + giorno;
				}
				var mese = dataOggi.getMonth() + 1;
				if(mese < 10){
					mese = "0" + mese;
				}
				var anno = dataOggi.getFullYear();
				var oggi = giorno + "/" + mese + "/" + anno;
				setValue("PTAPPROVAZIONI_DATAAPPR",oggi);
			
			}			


			//Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI).		
			function validazione(){
				var funzionalita = $('#funzionalita').val();
				var comando = "href=piani/commons/popup-validazione-fabbisogni.jsp";
				comando = comando + "&fabbisogni="+$('#fabbisogni').val();
				if (funzionalita == "RAF_ApprovaRespingi") {
					comando = comando + "&codFunzione=INOLTRO_RDP";
				} else if (funzionalita == "RDS_ApprovaRichiediRevisione") {
					comando = comando + "&codFunzione=INOLTRO_RAF";
				} else {
					comando = comando + "&codFunzione=INOLTRO_RDP";
				}
				comando = comando + "&invio=conferma";
				<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.RAF_ACCETTA_DICHIARAZIONE")}'>
					if (!document.getElementById('dichiarazione').checked) {
						alert("Prima di proseguire è necessario accettare la dichiarazione!");
						return;
					}
				</c:if>
				
				openPopUpCustom(comando, "validazione", 500, 650, 1, 1);
			}


		</gene:javaScript>
		
	</gene:redefineInsert>	
</gene:template>
