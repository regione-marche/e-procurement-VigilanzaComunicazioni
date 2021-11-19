<%
/*
 * Created on: 21/11/2008
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="esisteProfiloCup" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.EsisteProfiloFunction", pageContext, "PRO_CUP")}' scope="request" />

<gene:formScheda entita="PIATRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI">
					<gene:campoScheda campo="CONTRI" visibile="false" />
	  				<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GestioneLavoriEconomiaFunction" parametro="${key}" />
	  				<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
      					<jsp:param name="entita" value='ECOTRI'/>
      					<jsp:param name="chiave" value='${gene:getValCampo(key, "CONTRI")}'/>
      					<jsp:param name="nomeAttributoLista" value='listaLavoriInEconomia' />
      					<jsp:param name="idProtezioni" value="ECOTRI" />
      					<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/piani/ecotri/ecotri-interno-scheda.jsp"/>
      					<jsp:param name="arrayCampi" value="'ECOTRI_CONTRI_', 'ECOTRI_CONECO_', 'ECOTRI_DESCRI_', 'ECOTRI_CUPPRG_', 'ECOTRI_STIMA_'"/>
      					<jsp:param name="arrayVisibilitaCampi" value=""/>
      					<jsp:param name="titoloSezione" value="Lavori in economia" />
      					<jsp:param name="titoloNuovaSezione" value="Nuovo lavoro in economia" />
      					<jsp:param name="descEntitaVociLink" value="lavoro in economia" />
      					<jsp:param name="msgRaggiuntoMax" value="i lavori in economia"/>
      					<jsp:param name="usaContatoreLista" value="true"/>
						<jsp:param name="sezioneListaVuota" value="true"/>
   					</jsp:include>
   					
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaNuovo" />

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
	   					
</gene:formScheda>