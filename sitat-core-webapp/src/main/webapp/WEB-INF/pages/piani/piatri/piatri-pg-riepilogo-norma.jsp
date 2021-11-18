<%
  /*
   * Created on: 07/07/2011
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

<c:set var="key"
		value='PIATRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")}'
		scope="request" />
		
<c:set var="tiprog"
	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, key)}' />


<c:set var="modificabile" value="false" />
<c:set var="modificaTotali" value="false"/>
<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALIPROGMIT")}'>
	<c:set var="modificabile" value="true" />
	<c:set var="modificaTotali" value="${param.modificaTotali}"/>
</c:if>
<gene:formScheda entita="PIATRI" gestisciProtezioni="true"
	plugin="it.eldasoft.sil.pt.tags.gestori.plugin.GestorePIATRI"
	gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI">
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda campo="ID"     visibile="false" />
		<gene:campoScheda campo="CONTRI" visibile="false" />
		<gene:campoScheda campo="TIPROG" visibile="false" />
		<gene:campoScheda campo="ANNTRI" visibile="false" />
		<gene:campoScheda campo="ANNTRI_L" campoFittizio="true"
			entita="PIATRI" definizione="N4;0;;;ANNTRI" visibile="false"
			value="${datiRiga.PIATRI_ANNTRI}" />
		<gene:campoScheda campo="ANNTRI_FS" campoFittizio="true"
			entita="PIATRI" definizione="N4;0;;;ANNTRI" visibile="false"
			value="${datiRiga.PIATRI_ANNTRI}" />

		<gene:campoScheda campo="CENINT" visibile="false" />
		
	
		
		<gene:campoScheda addTr="false">
			<tr>
				<td colspan="2">
					<table class="griglia">
						<tr>
							<td colspan="7"><b>Riepilogo</b></td>
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Tipologie Risorse</td>
							<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria primo anno</td>
							<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria secondo anno</td>
							<c:if test='${tiprog ne "2"}'>
								<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria terzo anno</td>
							</c:if>
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Risorse derivanti da entrate aventi destinazione vincolata per legge</td>
							</gene:campoScheda>
							<gene:campoScheda campo="DV1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="DV2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="DV3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Risorse derivanti da entrate acquisite mediante contrazione di mutuo</td>
							</gene:campoScheda>
							<gene:campoScheda campo="MU1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="MU2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="MU3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Entrate acquisite mediante apporti di capitale privato</td>
							</gene:campoScheda>
							<gene:campoScheda campo="PR1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="PR2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="PR3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Stanziamenti di bilancio</td>
							</gene:campoScheda>
							<gene:campoScheda campo="BI1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="BI2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="BI3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Finanziamenti acquisibili ai sensi dell'articolo 3 del decreto-legge 31 ottobre 1990, 
							n. 310, convertito con modificazioni dalla legge 22 dicembre 1990, n. 403</td>
							</gene:campoScheda>
							<gene:campoScheda campo="AP1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AP2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="AP3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Risorse derivanti da trasferimento di immobili</td>
							</gene:campoScheda>
							<gene:campoScheda campo="IM1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="IM2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="IM3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> >Altra tipologia</td>
							</gene:campoScheda>
							<gene:campoScheda campo="AL1TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<gene:campoScheda campo="AL2TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="AL3TRI" modificabile="${modificaTotali}" addTr="false" hideTitle="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> ><b>Importo disponibilit&agrave; finanziaria al netto di capitali privati</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="TO1TRI" modificabile="false"  addTr="false" hideTitle="true"/>
							<gene:fnJavaScriptScheda funzione='setValue("PIATRI_TO1TRI",toMoney(sommaCampi(new Array("#PIATRI_DV1TRI#","#PIATRI_MU1TRI#","#PIATRI_BI1TRI#","#PIATRI_AP1TRI#","#PIATRI_IM1TRI#","#PIATRI_AL1TRI#"))))' elencocampi="PIATRI_DV1TRI;PIATRI_MU1TRI;PIATRI_BI1TRI;PIATRI_AP1TRI;PIATRI_IM1TRI;PIATRI_AL1TRI" esegui="true"/>
							
							<gene:campoScheda campo="TO2TRI" modificabile="false" addTr="false" hideTitle="true"/>
							<gene:fnJavaScriptScheda funzione='setValue("PIATRI_TO2TRI",toMoney(sommaCampi(new Array("#PIATRI_DV2TRI#","#PIATRI_MU2TRI#","#PIATRI_BI2TRI#","#PIATRI_AP2TRI#","#PIATRI_IM2TRI#","#PIATRI_AL2TRI#"))))' elencocampi="PIATRI_DV2TRI;PIATRI_MU2TRI;PIATRI_BI2TRI;PIATRI_AP2TRI;PIATRI_IM2TRI;PIATRI_AL2TRI" esegui="true"/>
							
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="TO3TRI" modificabile="false" addTr="false" hideTitle="true"/>
								<gene:fnJavaScriptScheda funzione='setValue("PIATRI_TO3TRI",toMoney(sommaCampi(new Array("#PIATRI_DV3TRI#","#PIATRI_MU3TRI#","#PIATRI_BI3TRI#","#PIATRI_AP3TRI#","#PIATRI_IM3TRI#","#PIATRI_AL3TRI#"))))' elencocampi="PIATRI_DV3TRI;PIATRI_MU3TRI;PIATRI_BI3TRI;PIATRI_AP3TRI;PIATRI_IM3TRI;PIATRI_AL3TRI" esegui="true"/>
							</c:if>
							<gene:campoScheda addTr="false">
						</tr>						

						<tr>
							<td class="etichetta-dato" <c:if test='${tiprog eq "2"}'>colspan="3"</c:if> ><b>Totale disponibilit&agrave; finanziaria</b></td>
							</gene:campoScheda>
								
							<gene:campoScheda campo="DF1TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 1° anno" 
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("DF1TRI",toMoney(sommaCampi(new Array("#PIATRI_TO1TRI#","#PIATRI_PR1TRI#"))))' elencocampi="PIATRI_TO1TRI;PIATRI_PR1TRI" esegui="true"/>
								
							<gene:campoScheda campo="DF2TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 2° anno"  
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("DF2TRI",toMoney(sommaCampi(new Array("#PIATRI_TO2TRI#","#PIATRI_PR2TRI#"))))' elencocampi="PIATRI_TO2TRI;PIATRI_PR2TRI" esegui="true"/>
							
							<c:if test='${tiprog ne "2"}'>
								<gene:campoScheda campo="DF3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("DF3TRI",toMoney(sommaCampi(new Array("#PIATRI_TO3TRI#","#PIATRI_PR3TRI#"))))' elencocampi="PIATRI_TO3TRI;PIATRI_PR3TRI" esegui="true"/>
							</c:if>									
							<gene:campoScheda addTr="false">
						</tr>
						<tr>
							<td class="etichetta-dato" colspan="5"><b>Importo totale</b></td>
							</gene:campoScheda>
							<gene:campoScheda campo="TOTPRO" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Importo totale"  
								modificabile="false" addTr="false" hideTitle="true">
							</gene:campoScheda>
							<gene:fnJavaScriptScheda funzione='setValue("TOTPRO",toMoney(sommaCampi(new Array("#PIATRI_TO1TRI#","#PIATRI_TO2TRI#","#PIATRI_TO3TRI#","#PIATRI_PR1TRI#","#PIATRI_PR2TRI#","#PIATRI_PR3TRI#"))))' elencocampi="PIATRI_TO1TRI;PIATRI_TO2TRI;PIATRI_TO3TRI,PIATRI_PR1TRI;PIATRI_PR2TRI;PIATRI_PR3TRI" esegui="true"/>
							<gene:campoScheda addTr="false">
						</tr>	
					</table>
				</td>
			</tr>
			<input type="hidden" name="modificaTotali" />
		</gene:campoScheda>
	
		
	

		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALLEGATO")}'>
		<gene:campoScheda campo="VISUALIZZAFILEALLEGATO"
			title="Stampa delle schede del programma"
			campoFittizio="true" modificabile="false" definizione="T200;0"
			visibile='${modo ne "NUOVO"}'>
			<a id="visualizzaAllegatoNuovaNormativa" href='javascript:apriAllegatoNuovaNormativa();'>Visualizza</a>
		</gene:campoScheda>
		</c:if>
		
	</gene:gruppoCampi>

	<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="PIATRI" />
	</jsp:include>


	<gene:redefineInsert name="pulsanteModifica" />
	<gene:redefineInsert name="schedaModifica" />

	
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>

</gene:formScheda>
<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	<link rel="STYLESHEET" type="text/css"	href="${pageContext.request.contextPath}/css/${applicationScope.pathCss}piani.css">
</gene:redefineInsert>
<gene:javaScript>

	function sommaCampi(valori)	{
			  var i, ret=0;
			  for(i=0;i < valori.length;i++){
			  	if(valori[i] != ""){
			    	ret += eval(valori[i]);
			  	}
			  }
			  return eval(ret).toFixed(2);
	 		}
	 		
<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.ALLEGATO")}'>
<c:if test='${(modoAperturaScheda ne "VISUALIZZA")}'>
	document.forms[0].encoding="multipart/form-data";
</c:if>

	var linksetJsPopUpVISUALIZZAFILEALLEGATO="";
	var apertura = ${(modoAperturaScheda ne "VISUALIZZA")};
	var nonEsistenzaBlob = ${(ctrlBlob ne "true")}; 
	var allegatoDaVisualizzare = (apertura && nonEsistenzaBlob);
	
	linksetJsPopUpVISUALIZZAFILEALLEGATO+=creaVocePopUpChiusura("${pageContext.request.contextPath}/");
	linksetJsPopUpVISUALIZZAFILEALLEGATO+=creaPopUpSubmenu("javascript:javascript:eliminaFile();hideMenuPopup();",0,"&nbsp;Elimina");
		
	function apriAllegatoNuovaNormativa() {
		var contri = getValue("PIATRI_CONTRI");
		var piatriID = getValue("PIATRI_ID");
		var actionAllegatoNuovaNormativa = "${pageContext.request.contextPath}/piani/VisualizzaAllegatoNuovaNormativa.do";
		var par = new String("contri=" + contri + "&tiprog=${tiprog}&piatriID=" + piatriID + "&tab=piatri");
		document.getElementById('visualizzaAllegatoNuovaNormativa').onclick = function(){return false};
		document.location.href=actionAllegatoNuovaNormativa+"?" + csrfToken + "&" +par;
	}
	
	function modificaTotali() {
		document.forms[0].modificaTotali.value = "true";
		if(confirm("ATTENZIONE: eventuali modifiche agli importi complessivi del riepilogo verranno perse qualora si effettui una qualsiasi modifica ad uno degli interventi inseriti.\nVuoi davvero procedere con la modifica? ")) {
			schedaModifica();
		}
	}
	
	function modificaAccantonamento() {
		document.forms[0].modificaTotali.value = "false";
		schedaModifica();
	}
	
	function creaStampa() {
		var contri = getValue("PIATRI_CONTRI");
		var piatriID = getValue("PIATRI_ID");
		<c:if test='${tiprog ne "2"}'>
			var accantonamento = getValue("PIATRI_IMPACC");
			openPopUpCustom("href=piani/piatri/popupComponiPianoTriennale.jsp&accantonamento=" + accantonamento + "&contri="+contri+"&tiprog=${tiprog}&piatriID="+piatriID, "stampa", 500, 200, "yes", "yes");
		</c:if>
		<c:if test='${tiprog eq "2"}'>
			openPopUpCustom("href=piani/piatri/popupComponiPianoTriennale.jsp&contri="+contri+"&tiprog=${tiprog}&piatriID="+piatriID, "stampa", 500, 200, "yes", "yes");
		</c:if>
		//window.open("${pageContext.request.contextPath}/ApriPagina.do?href=piani/piatri/popupComponiPianoTriennale.jsp&contri="+contri+"&tiprog=${tiprog}", "stampa", "toolbar=no,menubar=yes,width=800,height=600,top=" + Math.floor((screen.height-600)/2) + ",left=" + Math.floor((screen.width-800)/2) + ",resizable=yes,scrollbars=yes");
	}
	//${pageContext.request.contextPath}/ApriPopup.do?

showObj("rowNOMEFILEALLEGATO", allegatoDaVisualizzare);
showObj("rowVISUALIZZAFILEALLEGATO", !allegatoDaVisualizzare);
</c:if>
</gene:javaScript>