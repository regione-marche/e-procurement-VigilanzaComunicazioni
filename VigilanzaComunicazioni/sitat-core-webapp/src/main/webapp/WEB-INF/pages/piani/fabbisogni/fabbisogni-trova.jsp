<%/*
       * Created on 26-Feb-2010
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


<!-- N.B.: SE SI SPOSTA L'ORDINE DEI CAMPI DI RICERCA RICORDARSI DI CAMBIARE TUTTI I RIFERIMENTI NEL CODICE !!! (ESEMPIO: #Campo5 -> #Campo6)  -->


<c:choose>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.FABBISOGNI")}'>
		<c:set var="profilo" value="RDS" />
	</c:when>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.SA-FABB_APPRFIN")}'>
		<c:set var="profilo" value="RAF" />
	</c:when>
	<c:when test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
		<c:set var="profilo" value="RDP" />
	</c:when>
</c:choose>


<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9HOME.PIATRI")}'>
	<c:set var="profilo" value="RDP" />
</c:if>
<c:set var="checkProt_FiltroUffintListaProposte" value='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.FiltroUffintListaProposte")}'/>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="FABBISOGNI-trova">
	<gene:setString name="titoloMaschera" value="Ricerca Proposte"/>
	
	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">
		
	
  	<% // Creo la form di trova con i campi dell'entita' peri %>
  	<gene:formTrova entita="FABBISOGNI" lista="piani/fabbisogni/fabbisogni-lista.jsp?rbTrova=RicercaAvanzata"  gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati generali proposte</b></td></tr>
			<!-- 			campi comuni a tutti e tre i profili : RDS, RAF e RDP -->
			<c:if test='${!checkProt_FiltroUffintListaProposte}'>
				<c:if test='${profilo eq "RAF"}'>
					<gene:campoTrova campo="NOMEIN" title="Ente/Ufficio intestatario" entita="UFFINT" where="UFFINT.CODEIN=FABBISOGNI.CODEIN"/>
					<gene:campoTrova campo="NATGIU" entita="UFFINT" where="UFFINT.CODEIN=FABBISOGNI.CODEIN"/>
				</c:if>
			</c:if>
			<gene:campoTrova campo="CONINT" title="Codice" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			<gene:campoTrova campo="CODINT" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			<gene:campoTrova campo="DESINT" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			<gene:campoTrova campo="CUIINT" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			<gene:campoTrova campo="TIPOIN" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			
			<!-- 			solo per : RDS o RAF -->
			<c:if test='${profilo eq "RDS" || profilo eq "RAF"}'>
				<gene:campoTrova campo="STATO" />
				<gene:campoTrova campo="AILINT" title="Anno avvio procedura contrattuale" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>
			</c:if>
			<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
			<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->
			
			<!-- 			solo per : RAF -->
			<c:if test='${profilo eq "RAF"}'>
				<gene:campoTrova campo="CODRUP" entita="INTTRI" where="INTTRI.CONTRI=0 AND FABBISOGNI.CONINT=INTTRI.CONINT"/>	
			</c:if>
			
			<!-- 			solo per : RDP -->
			<c:if test='${profilo eq "RDP"}'>
				<gene:campoTrova campo="STATO" />
			</c:if>
		</gene:gruppoCampi>
    </gene:formTrova> 
	
	<gene:redefineInsert name="trovaCreaNuovo" />
	
  	</gene:redefineInsert>
  
  	<gene:javaScript>
					
		impostaStato();

		function impostaStato() {
			var profilo = '${profilo}';
			switch(profilo) {
				case "RAF":
			        $("#Campo5 option[value='1']").remove();
					$("#Campo5 option[value='2']").remove();
					$("#Campo5 option[value='11']").remove();
					$("#Campo5 option[value='12']").remove();
			        break;
			    case "RDP":
			        $("#Campo5 option[value='1']").remove();
			        break;
			}
		}	

	</gene:javaScript>
  
</gene:template>