<%
	/*
	 * Created on: 04/08/2009
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


<gene:formScheda entita="W9COLL" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9COLL">
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
	
	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>	
	
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Collaudo/verifica di conformit&agrave; delle prestazioni eseguite</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODGARA" visibile="false" defaultValue='${gene:getValCampo(key,"CODGARA")}' />
		<gene:campoScheda campo="CODLOTT" visibile="false" defaultValue='${gene:getValCampo(key,"CODLOTT")}' />
		<gene:campoScheda campo="NUM_APPA" visibile="false" defaultValue="${aggiudicazioneSelezionata}" />
		<gene:campoScheda campo="NUM_COLL" visibile="false" />
		<gene:campoScheda campo="TIPO_COLLAUDO" obbligatorio="true" />
		<gene:campoScheda campo="DATA_REGOLARE_ESEC" />
		<gene:campoScheda campo="DATA_COLLAUDO_STAT" />
		<gene:campoScheda campo="MODO_COLLAUDO" />
		<gene:campoScheda campo="FLAG_SINGOLO_COMMISSIONE" />
		<gene:campoScheda campo="FLAG_SUBAPPALTATORI" />
		<gene:campoScheda campo="DATA_NOMINA_COLL" />
		<gene:campoScheda campo="DATA_INIZIO_OPER" />
		<gene:campoScheda campo="DATA_CERT_COLLAUDO" />
		<gene:campoScheda campo="DATA_DELIBERA" />
		<gene:campoScheda campo="DATA_APPROVAZIONE" />
		<gene:campoScheda campo="ESITO_COLLAUDO" />
		
		<gene:campoScheda campo="IMP_FINALE_LAVORI" />
		<gene:campoScheda campo="IMP_FINALE_SERVIZI" />
		<gene:campoScheda campo="IMP_FINALE_FORNIT" />
		<gene:campoScheda campo="FLAG_IMPORTI" />
		<gene:campoScheda campo="IMP_SUBTOTALE" modificabile="false">
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9COLL_IMP_FINALE_LAVORI#","#W9COLL_IMP_FINALE_SERVIZI#","#W9COLL_IMP_FINALE_FORNIT#")))' 
  				elencocampi="W9COLL_IMP_FINALE_LAVORI;W9COLL_IMP_FINALE_SERVIZI;W9COLL_IMP_FINALE_FORNIT" />
		</gene:campoScheda>
		<gene:campoScheda campo="IMP_FINALE_SECUR" />
		<gene:campoScheda campo="FLAG_SICUREZZA" />
		<gene:campoScheda campo="IMP_PROGETTAZIONE" />
		<gene:campoScheda campo="IMP_COMPL_APPALTO" modificabile="false" >
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9COLL_IMP_SUBTOTALE#","#W9COLL_IMP_FINALE_SECUR#","#W9COLL_IMP_PROGETTAZIONE#")))' 
  				elencocampi="W9COLL_IMP_SUBTOTALE;W9COLL_IMP_FINALE_SECUR;W9COLL_IMP_PROGETTAZIONE" />
		</gene:campoScheda>
		<gene:campoScheda campo="IMP_DISPOSIZIONE" />
		<gene:campoScheda campo="IMP_COMPL_INTERVENTO" modificabile="false" >
			<gene:calcoloCampoScheda 
   				funzione='toMoney(sommaCampi(new Array("#W9COLL_IMP_COMPL_APPALTO#","#W9COLL_IMP_DISPOSIZIONE#")))' 
  				elencocampi="W9COLL_IMP_COMPL_APPALTO;W9COLL_IMP_DISPOSIZIONE" />
		</gene:campoScheda>
		<gene:campoScheda campo="LAVORI_ESTESI" />
		
		<gene:campoScheda>
			<td colspan="2"><b>Ulteriori riserve definite o da definire
			in via amministrativa in sede di collaudo </b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="AMM_IMPORTO_DEF" />
		<gene:campoScheda campo="AMM_NUM_DEFINITE" />
		<gene:campoScheda campo="AMM_NUM_DADEF" />
		<gene:campoScheda campo="AMM_IMPORTO_RICH" />
		<gene:campoScheda campo="DATA_INIZIO_AMM_RISERVE" />
		<gene:campoScheda campo="DATA_FINE_AMM_RISERVE" />

		<gene:campoScheda>
			<td colspan="2"><b>Ulteriori riserve definite o da definire	in via arbitrale</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="ARB_IMPORTO_DEF" />
		<gene:campoScheda campo="ARB_NUM_DEFINITE" />
		<gene:campoScheda campo="ARB_NUM_DADEF" />
		<gene:campoScheda campo="ARB_IMPORTO_RICH" />
		<gene:campoScheda campo="DATA_INIZIO_ARB_RISERVE" />
		<gene:campoScheda campo="DATA_FINE_ARB_RISERVE" />
		
		<gene:campoScheda>
			<td colspan="2"><b>Ulteriori riserve definite o da definire	in via giudiziale</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="GIU_IMPORTO_DEF" />
		<gene:campoScheda campo="GIU_NUM_DEFINITE" />
		<gene:campoScheda campo="GIU_NUM_DADEF" />
		<gene:campoScheda campo="GIU_IMPORTO_RICH" />
		<gene:campoScheda campo="DATA_INIZIO_GIU_RISERVE" />
		<gene:campoScheda campo="DATA_FINE_GIU_RISERVE" />
		
		<gene:campoScheda>
			<td colspan="2"><b>Ulteriori riserve definite o da definire in via transattiva</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="TRA_IMPORTO_DEF" />
		<gene:campoScheda campo="TRA_NUM_DEFINITE" />
		<gene:campoScheda campo="TRA_NUM_DADEF" />
		<gene:campoScheda campo="TRA_IMPORTO_RICH" />
		<gene:campoScheda campo="DATA_INIZIO_TRA_RISERVE" />
		<gene:campoScheda campo="DATA_FINE_TRA_RISERVE" />

	</gene:gruppoCampi>
	
	<!-- campi per  W9FASI-->
	<gene:campoScheda campo="CODGARA" entita="W9FASI" visibile="false" defaultValue="${codgara}" 
				where="W9FASI.CODGARA = W9COLL.CODGARA AND W9FASI.CODLOTT = W9COLL.CODLOTT AND W9FASI.NUM = W9COLL.NUM_COLL AND W9FASI.NUM_APPA = W9COLL.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="CODLOTT" entita="W9FASI" visibile="false" defaultValue="${codlott}" 
				where="W9FASI.CODGARA = W9COLL.CODGARA AND W9FASI.CODLOTT = W9COLL.CODLOTT AND W9FASI.NUM = W9COLL.NUM_COLL AND W9FASI.NUM_APPA = W9COLL.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="NUM_APPA" entita="W9FASI" visibile="false" defaultValue="${aggiudicazioneSelezionata}" 
				where="W9FASI.CODGARA = W9COLL.CODGARA AND W9FASI.CODLOTT = W9COLL.CODLOTT AND W9FASI.NUM = W9COLL.NUM_COLL AND W9FASI.NUM_APPA = W9COLL.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	<gene:campoScheda campo="NUM" entita="W9FASI" visibile="false" 
				where="W9FASI.CODGARA = W9COLL.CODGARA AND W9FASI.CODLOTT = W9COLL.CODLOTT AND W9FASI.NUM = W9COLL.NUM_COLL AND W9FASI.NUM_APPA = W9COLL.NUM_APPA AND W9FASI.FASE_ESECUZIONE=${flusso}"/>
	
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	<gene:javaScript>
	 function sommaCampi(valori) {
		 var i, ret=0;
		 for(i=0;i < valori.length; i++){
		   if(valori[i] != ""){
		     ret += eval(valori[i]);
		   }
		 }
		 return eval(ret).toFixed(2);
 	 }
	  	 
 	 $(document).ready(function() {
 <c:if test='${modo ne "NUOVO"}'>
 		changeTIPOCOLLAUDO();
 </c:if>
 	 
 	 	$("#W9COLL_MODO_COLLAUDO").on("change",function() {
			if (getValue("W9COLL_MODO_COLLAUDO") != null && getValue("W9COLL_MODO_COLLAUDO") != "" ) {
				setValue("W9COLL_DATA_REGOLARE_ESEC", "");
				showObj("rowW9COLL_DATA_REGOLARE_ESEC", false);
			} else {
				showObj("rowW9COLL_DATA_REGOLARE_ESEC", true);
			}
		});
		
		$("#W9COLL_TIPO_COLLAUDO").on("change",function() {
			changeTIPOCOLLAUDO();
		});
 	 });
 	 
 	 
	function changeTIPOCOLLAUDO() {
		if ("1" == getValue("W9COLL_TIPO_COLLAUDO")) {
			showObj("rowW9COLL_MODO_COLLAUDO", true);
			showObj("rowW9COLL_DATA_NOMINA_COLL", true);
			showObj("rowW9COLL_DATA_INIZIO_OPER", true);
			showObj("rowW9COLL_DATA_REGOLARE_ESEC", false);
			setValue("W9COLL_DATA_REGOLARE_ESEC", "");
		} else if ("2" == getValue("W9COLL_TIPO_COLLAUDO")) {
			showObj("rowW9COLL_MODO_COLLAUDO", false);
			showObj("rowW9COLL_DATA_NOMINA_COLL", false);
			showObj("rowW9COLL_DATA_INIZIO_OPER", false);
			showObj("rowW9COLL_DATA_REGOLARE_ESEC", true);
			setValue("W9COLL_MODO_COLLAUDO", "", false);
			setValue("W9COLL_DATA_NOMINA_COLL", "");
			setValue("W9COLL_DATA_INIZIO_OPER", "");
		} else {
			showObj("rowW9COLL_MODO_COLLAUDO", true);
			showObj("rowW9COLL_DATA_NOMINA_COLL", true);
			showObj("rowW9COLL_DATA_INIZIO_OPER", true);
			showObj("rowW9COLL_DATA_REGOLARE_ESEC", true);
		}
	}
	</gene:javaScript>
</gene:formScheda>