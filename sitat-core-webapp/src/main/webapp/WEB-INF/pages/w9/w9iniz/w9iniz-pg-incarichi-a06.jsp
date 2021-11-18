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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%/* Dati generali del lavoro */%>
<gene:formScheda entita="W9INIZ" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9INIZ"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9INIZ"
	where='W9INIZ.CODGARA = #W9INIZ.CODGARA# AND W9INIZ.CODLOTT = #W9INIZ.CODLOTT# AND W9INIZ.NUM_INIZ = #W9INIZ.NUM_INIZ#' >

	<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' scope="request" />
	<c:set var="codlott" value='${gene:getValCampo(key,"CODLOTT")}' scope="request" />
	<c:set var="num" value='${gene:getValCampo(key,"NUM_INIZ")}'    scope="request" />

	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
			
	<c:if test='${permessoUser lt 4 || aggiudicazioneSelezionata ne ultimaAggiudicazione}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<gene:campoScheda campo="CODLOTT" visibile="false" />
	<gene:campoScheda campo="CODGARA" visibile="false" />
	<gene:campoScheda campo="NUM_INIZ" visibile="false" />
	<gene:campoScheda campo="NUM_APPA" visibile="false" />

	<!-- campi per  W9FASI-->
	<gene:campoScheda campo="FASE_ESECUZIONE" entita="W9FASI" visibile="false" value="${flusso}"/>
	
	<!-- scheda multipla -->
	<c:set var="keyW9inca" value="${key};W9INCA.SEZIONE=T:IN-A06;W9INCA.NUM=N:${num}" scope="request"/>
	<c:if test='${modo ne "NUOVO"}' >
		<gene:callFunction obj="it.eldasoft.w9.tags.funzioni.GetIncarichiProfessionaliFunction" parametro="${key};SEZIONE=T:IN" />
	</c:if>
	
	<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
		<jsp:param name="entita" value='W9INCA'/>
		<jsp:param name="chiave" value='${gene:getValCampo(key, "CODGARA")};${gene:getValCampo(key, "CODLOTT")};${gene:getValCampo(key, "NUM_INIZ")}'/>
		<jsp:param name="nomeAttributoLista" value='listaIncarichiProfessionali' />
		<jsp:param name="idProtezioni" value="W9INCA" />
		<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/incarichi_professionali/incarichi_professionali.jsp" />
		<jsp:param name="arrayCampi" value="'W9INCA_CODGARA_','W9INCA_CODLOTT_','W9INCA_NUM_','W9INCA_SEZIONE_','W9INCA_NUM_INCA_','W9INCA_CODTEC_','TECNI_NOMTEC_','W9INCA_ID_RUOLO_','W9INCA_CIG_PROG_ESTERNA_','W9INCA_DATA_AFF_PROG_ESTERNA_','W9INCA_DATA_CONS_PROG_ESTERNA_'" />
		<jsp:param name="arrayVisibilitaCampi" value="false,false,false,false,false,false,true,true,false,false,false" />
		<jsp:param name="usaContatoreLista" value="true"/>
		<jsp:param name="sezioneListaVuota" value="true"/>
		<jsp:param name="titoloSezione" value="Incarichi professionali" />
		<jsp:param name="titoloNuovaSezione" value="Nuovo Incarico professionale" />
		<jsp:param name="descEntitaVociLink" value="incarichi professionali" />
		<jsp:param name="msgRaggiuntoMax" value="e incarichi professionali" />
	</jsp:include>	

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>