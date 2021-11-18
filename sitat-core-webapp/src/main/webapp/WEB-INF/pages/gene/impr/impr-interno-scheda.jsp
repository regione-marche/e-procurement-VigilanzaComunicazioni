<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
/* Interno della scheda del tecnico */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setBundle basename="AliceResources" />

<c:set var="isCodificaAutomatica" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.IsCodificaAutomaticaFunction", pageContext, "IMPR", "CODIMP")}'/>
<c:set var="obbligatorioCodFisc" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.EsisteControlloObbligatorietaCodFiscPivaFunction", pageContext, "IMPR","CFIMP")}'/>
<c:set var="obbligatorioPIVA" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.EsisteControlloObbligatorietaCodFiscPivaFunction", pageContext, "IMPR","PIVIMP")}'/>
<c:set var="obbligatoriaCorrettezzaCodFisc" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.EsisteControlloCorrettezzaFunction", pageContext, "IMPR","CFIMP")}'/>
<c:set var="obbligatoriaCorrettezzaPIVA" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.EsisteControlloCorrettezzaFunction", pageContext, "IMPR","PIVIMP")}'/>

<c:set var="valoreItalia" value='${gene:callFunction3("it.eldasoft.gene.tags.functions.GetCodiceTabellatoDaDescrFunction", pageContext, "Ag010","Italia")}' scope="request"/>
<c:set var="saltareControlloObbligPivaLibProfessionista" value='${gene:callFunction2("it.eldasoft.gene.tags.functions.saltareControlloObbligPivaFunction", pageContext, "1")}'/>

<c:set var="listaOpzioniDisponibili" value="${fn:join(opzDisponibili,'#')}#"/>
<c:set var="archiviFiltrati" value='${gene:callFunction("it.eldasoft.gene.tags.functions.GetPropertyFunction", "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati")}'/>

<gene:formScheda entita="IMPR" gestisciProtezioni="true" plugin="it.eldasoft.gene.web.struts.tags.gestori.plugin.GestoreImpresa" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreIMPRTEIM" >

	<c:if test='${modoAperturaScheda eq "VISUALIZZA"}'>
		<c:set var="codiceImpresa" value='${fn:substringAfter(key, ":")}' />
		<c:set var="impresaRegistrata" value='${gene:callFunction2("it.eldasoft.gene.tags.functions.ImpresaRegistrataSuPortaleFunction",  pageContext, codiceImpresa )}'/>
		<c:if test="${impresaRegistrata eq 'SI' }">
			<c:set var="isSsoProtocollo" value='${gene:callFunction("it.eldasoft.gene.tags.functions.IsSsoProtocolloAbilitatoFunction", pageContext)}'/>
		</c:if>
	</c:if>
			
	<c:if test='${modoAperturaScheda eq "VISUALIZZA" and (tipologiaImpresa ne 3 and tipologiaImpresa ne 10) and isIntegrazionePortaleAlice eq "true"}'>
		<gene:redefineInsert name="addToAzioni" >
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.GENE.ImprScheda.RegistraSuPortale") and impresaRegistrata ne "SI"}' >
				<tr>
					<td class="vocemenulaterale" >
						<a href="javascript:registraSuPortale()" title="Registra su portale" tabindex="1505">Registra su portale</a>
					</td>
				</tr>
			</c:if>
			<c:if test='${bloccoImpresaRegistrata eq "1" and gene:checkProt(pageContext, "FUNZ.VIS.ALT.GENE.ImprScheda.InviaMailAttivazioneSuPortale")}'>
				<tr>
					<td class="vocemenulaterale" >
						<a href="javascript:inviaMailAttivazioneSuPortale()" title="Invia mail di attivazione utenza su portale" tabindex="1505">Invia mail di attivazione utenza su portale</a>
					</td>
				</tr>
			</c:if>
			<c:if test='${isSsoProtocollo eq "true" and gene:checkProt(pageContext, "FUNZ.VIS.ALT.GENE.ImprScheda.SoggettoDelegaPortaleAppalti")}'>
				<tr>
					<td class="vocemenulaterale" >
						<a href="javascript:soggettoDelegaPortale()" title="Soggetto con delega su portale" tabindex="1506">Soggetto con delega su portale</a>
					</td>
				</tr>
			</c:if>
		</gene:redefineInsert>
	</c:if>
	
	<gene:gruppoCampi idProtezioni="GEN" >
		<gene:campoScheda  nome="GEN">
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>

		<gene:campoScheda campo="CODIMP" keyCheck="true" modificabile='${modoAperturaScheda eq "NUOVO"}' gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoCodificaAutomatica" obbligatorio="${isCodificaAutomatica eq 'false'}" />
		<gene:campoScheda campo="NOMEST" gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoNote"/>
		<gene:campoScheda campo="NOMIMP" visibile="false" >
			<%/* Aggiungo il calcolo partendo dal nome esteso */%>
			<gene:calcoloCampoScheda funzione='"#IMPR_NOMEST#".substr(0,60)' elencocampi="IMPR_NOMEST" />
		</gene:campoScheda>

		<gene:campoScheda campo="NATGIUI"/>
		<gene:campoScheda campo="GFIIMP" visibile="false" />
<c:choose>
	<c:when test='${isModificaDatiRegistrati eq "true"}'>
		 <gene:campoScheda campo="CFIMP" obbligatorio='${obbligatorioCodFisc}'>
			<gene:checkCampoScheda funzione='checkCodFisNazionalita("##",document.getElementById("IMPR_NAZIMP"))' obbligatorio="true" messaggio='Il valore del codice fiscale specificato non è valido.' onsubmit="false"/>
		 </gene:campoScheda>	
	</c:when>
	<c:otherwise>
		 <gene:campoScheda campo="CFIMP" obbligatorio='${obbligatorioCodFisc}'>
			<gene:checkCampoScheda funzione='checkCodFisNazionalita("##",document.getElementById("IMPR_NAZIMP"))' obbligatorio="${obbligatoriaCorrettezzaCodFisc}" messaggio='Il valore specificato non è valido.' onsubmit="false"/>
		 </gene:campoScheda>	
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test='${isModificaDatiRegistrati eq "true"}'>
		 <gene:campoScheda campo="PIVIMP" title ='Partita I.V.A. ${gene:if(modo ne "VISUALIZZA" && obbligatorioPIVA eq "true", "(*)","") }'>
			<gene:checkCampoScheda funzione='checkPivaNazionalita("##",document.getElementById("IMPR_NAZIMP"))' obbligatorio="true" messaggio='Il valore specificato non è valido.' onsubmit="false"/>
		 </gene:campoScheda>
	</c:when>
	<c:otherwise>
		 <gene:campoScheda campo="PIVIMP" title ='Partita I.V.A. ${gene:if(modo ne "VISUALIZZA" && obbligatorioPIVA eq "true", "(*)","") }'>
			<gene:checkCampoScheda funzione='checkPivaNazionalita("##",document.getElementById("IMPR_NAZIMP"))' obbligatorio="${obbligatoriaCorrettezzaPIVA}" messaggio='Il valore specificato non è valido.' onsubmit="false"/>
		 </gene:campoScheda>
	</c:otherwise>
</c:choose>
		
		<gene:campoScheda campo="CGENIMP" defaultValue="${gene:if(fn:contains(archiviFiltrati,'IMPR') && !empty sessionScope.uffint,sessionScope.uffint,'')}" visibile='${fn:contains(listaOpzioniDisponibili, "OP127#")}'/>
		<gene:campoScheda campo="TIPIMP" />
		<gene:campoScheda campo="NCCIAA" />
		<gene:campoScheda campo="REGDIT" />
		<gene:campoScheda campo="NINAIL" />
		<gene:campoScheda campo="NINPS" />
		<gene:campoScheda campo="ALBTEC" />
	</gene:gruppoCampi>
	
	<gene:gruppoCampi idProtezioni="LEG" >
		<gene:campoScheda  nome="LEG">
			<td colspan="2"><b>Legale Rappresentante</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="TEIM" where="IMPR.CODIMP=IMPLEG.CODIMP2 and IMPLEG.CODLEG=TEIM.CODTIM" from="IMPLEG" campo="CODTIM" visibile="false"/>
		<gene:campoScheda entita="TEIM" where="IMPR.CODIMP=IMPLEG.CODIMP2 and IMPLEG.CODLEG=TEIM.CODTIM" from="IMPLEG" campo="COGTIM" />
		<gene:campoScheda entita="TEIM" where="IMPR.CODIMP=IMPLEG.CODIMP2 and IMPLEG.CODLEG=TEIM.CODTIM" from="IMPLEG" campo="NOMETIM" />
		<gene:campoScheda entita="TEIM" where="IMPR.CODIMP=IMPLEG.CODIMP2 and IMPLEG.CODLEG=TEIM.CODTIM" from="IMPLEG" campo="CFTIM" >
			<gene:checkCampoScheda funzione='checkCodFisNazionalita("##",document.getElementById("TEIM_CFTIM"))' obbligatorio="${obbligatoriaCorrettezzaCodFisc}" messaggio='Il valore specificato non è valido.' onsubmit="false"/>
		</gene:campoScheda>
	</gene:gruppoCampi>
	
	<gene:gruppoCampi idProtezioni="IND" >
		<gene:campoScheda  nome="IND">
			<td colspan="2"><b>Indirizzo</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="INDIMP"/>
		<gene:campoScheda campo="NCIIMP"/>
		<gene:campoScheda campo="PROIMP"/>
		<gene:archivio titolo="Comuni" obbligatorio="false" scollegabile="true"
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.GENE.IMPR.CAPIMP") and gene:checkProt(pageContext, "COLS.MOD.GENE.IMPR.PROIMP") and gene:checkProt(pageContext, "COLS.MOD.GENE.IMPR.LOCIMP") and gene:checkProt(pageContext, "COLS.MOD.GENE.IMPR.CODCIT"),"gene/commons/istat-comuni-lista-popup.jsp","")}' 
				scheda="" 
				schedaPopUp="" 
				campi="TB1.TABCOD3;TABSCHE.TABCOD4;TABSCHE.TABDESC" 
				chiave="" 
				where='${gene:if(!empty datiRiga.IMPR_PROIMP, gene:concat(gene:concat("TB1.TABCOD3 = \'", datiRiga.IMPR_PROIMP), "\'"), "")}' 
				formName="formIstat" 
				inseribile="false" 
				>
			<gene:campoScheda campoFittizio="true" campo="COM_PROIMP" definizione="T9" visibile="false"/>
			<gene:campoScheda campo="CAPIMP"/>
			<gene:campoScheda campo="LOCIMP"/>
	
		</gene:archivio>
		<gene:campoScheda campo="NAZIMP"/>
		<gene:campoScheda campo="TELIMP"/>
		<gene:campoScheda campo="FAXIMP"/>
		
		<c:choose>
			<c:when test='${isModificaDatiRegistrati eq "true"}'>
			 	<gene:campoScheda campo="EMAIIP">
					<gene:checkCampoScheda funzione='isMailValida("##")' obbligatorio="true" messaggio="L'indirizzo email non e' sintatticamente valido." />
			 	</gene:campoScheda>	
			 	<gene:campoScheda campo="EMAI2IP">
					<gene:checkCampoScheda funzione='isMailValida("##")' obbligatorio="true" messaggio="L'indirizzo pec non e' sintatticamente valido." />
			 	</gene:campoScheda>	
			</c:when>
			<c:otherwise>
		 		<gene:campoScheda campo="EMAIIP"/>
		 		<gene:campoScheda campo="EMAI2IP"/>
			</c:otherwise>
		</c:choose>
	</gene:gruppoCampi>
	
	<gene:fnJavaScriptScheda funzione='aggiornaNazionalita("#IMPR_LOCIMP#", "${valoreItalia}", "IMPR_NAZIMP")' elencocampi='IMPR_LOCIMP' esegui="false"/>
	<c:set var="codiceImpresa" value='${gene:getValCampo(key, "IMPR.CODIMP")}' />

	<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="IMPR"/>
	</jsp:include>
	
	<gene:campoScheda>	
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
		
	<c:if test='${bloccoImpresaRegistrata eq "1"  and isIntegrazionePortaleAlice eq "true"}'>
		<gene:redefineInsert name="schedaModifica">
			<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD") && gene:checkProt(pageContext,"FUNZ.VIS.ALT.GENE.ModificaDatiRegistrati")}'>
				<tr>
					<td class="vocemenulaterale">
						<a href="javascript:schedaModifica();" title="Modifica dati registrati" tabindex="1501">
						Modifica dati registrati</a></td>
				</tr>
			</c:if>
		</gene:redefineInsert>
		<gene:redefineInsert name="pulsanteModifica" >
			<c:if test='${gene:checkProtFunz(pageContext,"MOD","SCHEDAMOD") && gene:checkProt(pageContext,"FUNZ.VIS.ALT.GENE.ModificaDatiRegistrati")}'>
				<INPUT type="button"  class="bottone-azione" value='Modifica dati registrati' title='Modifica dati registrati' onclick="javascript:schedaModifica()">
			</c:if>
		</gene:redefineInsert>
		<c:set var="isModificaDatiRegistrati" value="true" scope="request" />
	</c:if>
	<c:if test='${impresaRegistrata eq "SI"  and isIntegrazionePortaleAlice ne "true"}'>
		<gene:redefineInsert name="schedaModifica"/>
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>	

		<input  type="hidden" name="MOD_DATI_REG" title = "MOD_DATI_REG" value="${isModificaDatiRegistrati}" />
		<input  type="hidden" name="numElementiListaPersonale" id = "numElementiListaPersonale" value="" />
	


	<gene:fnJavaScriptScheda funzione='changeComune("#IMPR_PROIMP#", "COM_PROIMP")' elencocampi='IMPR_PROIMP' esegui="false"/>
	<gene:fnJavaScriptScheda funzione='setValueIfNotEmpty("IMPR_PROIMP", "#COM_PROIMP#")' elencocampi='COM_PROIMP' esegui="false"/>

	
	
</gene:formScheda>
<gene:javaScript>

function changeComune(provincia, nomeUnCampoInArchivio) {
	changeFiltroArchivioComuni(provincia, nomeUnCampoInArchivio);
	setValue("IMPR_CAPIMP", "");
	setValue("IMPR_LOCIMP", "");
}
 
 function isMailValida(valore){
 	if(valore!=null && valore!=""){
 		return isFormatoEmailValido(valore);
 	}
 	return true;
 }
 
 <c:if test='${!(modo eq "VISUALIZZA")}'>
 	var schedaConferma_Default = schedaConferma;
 	
 	function schedaConferma_Custom(){
 	 var tipimp=getValue("IMPR_TIPIMP");
 	 var obbligatoriaCorrettezzaCodFisc="${obbligatoriaCorrettezzaCodFisc }";
 	 var controlloOkCodFisc=true;
 	 var isModificaDatiRegistrati = "${isModificaDatiRegistrati }";
 	 clearMsg();
 	  	 	  	 	  	 
 	 if ((obbligatoriaCorrettezzaCodFisc== "true" || isModificaDatiRegistrati == "true") && tipimp!=3 && tipimp!=10){
 	 	var selectNazionalita= document.getElementById("IMPR_NAZIMP");
 	 	var isItalia= isNazionalitaItalia(selectNazionalita);
 	 	
 	 	if(isItalia == "si"){
	 	 	var codfisc=getValue("IMPR_CFIMP");
	 	 	controlloOkCodFisc=checkCodFis(codfisc);
	 	 	if(!controlloOkCodFisc){
	 	 		outMsg("Il valore del codice fiscale specificato non è valido", "ERR");
				onOffMsg();
	 	 	}
 	 	}
 	 }
 	 var esitoControlloPIVA = true;
 	 var obbligatorioPIVA="${obbligatorioPIVA }";
 	 if(obbligatorioPIVA == "true"){
 	 	var saltareControlloObbligPivaLibProfessionista = "${saltareControlloObbligPivaLibProfessionista }";
 	 	if (!(tipimp==3 || tipimp==10 || (tipimp == 6 && saltareControlloObbligPivaLibProfessionista == "true"))){
 	 		var piva=getValue("IMPR_PIVIMP");
 	 		if(piva==null || piva==""){
 	 			outMsg("Il campo partita I.V.A. è obbligatorio", "ERR");
				onOffMsg();
				esitoControlloPIVA = false;
			}
 	 	} 
 	 		
 	 }
 	 
 	 var obbligatoriaCorrettezzaPIVA="${obbligatoriaCorrettezzaPIVA }";
 	 var controlloOkPIVA=true;
 	 if ((obbligatoriaCorrettezzaPIVA=="true" || isModificaDatiRegistrati == "true") && tipimp!=3 && tipimp!=10){
 	 	var selectNazionalita= document.getElementById("IMPR_NAZIMP");
 	 	var isItalia= isNazionalitaItalia(selectNazionalita);
 	 	var piva=getValue("IMPR_PIVIMP");
 	 	if(isItalia == "si"){
 	 		controlloOkPIVA=checkParIva(piva);
	 	}else {
 	 		controlloOkPIVA = checkPivaEuropea(piva);
 	 	}
 	 	if(!controlloOkPIVA){
 	 		outMsg("Il valore della Partita I.V.A. o V.A.T. specificato non è valido", "ERR");
			onOffMsg();
 	 	}
 	 }
 	  	 
 	 if(controlloOkCodFisc && controlloOkPIVA && esitoControlloPIVA)
 	 	schedaConferma_Default();
 	}
 	
 	schedaConferma =   schedaConferma_Custom;

	function changeProvincia(provincia, nomeUnCampoInArchivio) {
		changeFiltroArchivioComuni(provincia, nomeUnCampoInArchivio);
		setValue("IMPR_CNATEC", "");
	}
	
 </c:if>
 
</gene:javaScript>
