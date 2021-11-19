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


<%-- <gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PIATRI-popup-inserisciInProgrammazione"> --%>
<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PTAPPROVAZIONI-popup-scheda">
	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='Approvazione del fabbisogno' />

		<gene:formScheda entita="PTAPPROVAZIONI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreApprovazioni" >

			<gene:campoScheda campo="CONINT" visibile="false" />
			<gene:campoScheda campo="NUMAPPR" visibile="false" />
			<gene:campoScheda campo="DATAAPPR" modificabile="false"/>
			<gene:campoScheda campo="SYSAPPR" defaultValue='${idUtente}' visibile="false" />
			<gene:campoScheda campo="UTENTEAPPR" defaultValue='${nomeUtente}' visibile="false" />
			<gene:campoScheda campo="FASEAPPR" visibile="true" defaultValue="3" modificabile="false"/>
			<gene:campoScheda campo="ESITOAPPR" obbligatorio="true" defaultValue="3" modificabile="false"/>
			<gene:campoScheda campo="NOTEAPPR" obbligatorio="true" />
			

			<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
			<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
			<input type="hidden" name="fabbisogni" id="fabbisogni" value="${fabbisogni}" />
			<input type="hidden" name="funzionalita" id="funzionalita" value="${funzionalita}" />	
			
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />

			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
			
			<gene:redefineInsert name="pulsanteAnnulla">
				<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla()">
			</gene:redefineInsert>
			
			
		</gene:formScheda>
		
		<gene:javaScript>

			initDataAppr();
		
			document.forms[0].jspPathTo.value="piani/ptapprovazioni/ptapprovazioni-conclusione-funzionalita.jsp";

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


			function annulla() {
				window.close();
			}			
		

		</gene:javaScript>
		
	</gene:redefineInsert>	
</gene:template>
