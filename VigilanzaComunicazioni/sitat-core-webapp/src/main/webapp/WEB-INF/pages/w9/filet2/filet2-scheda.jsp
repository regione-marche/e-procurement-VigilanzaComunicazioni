<%
  /*
   * Created on 17-07-2012
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true"	schema="W9" idMaschera="W9PRODOTTI-scheda">
	<gene:redefineInsert name="corpo">
		<gene:setString name="titoloMaschera" value='Prodotto' />
		<gene:formScheda entita="FILET2">
			<gene:campoScheda>
				<td colspan="2"><b>Dati Prodotto</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="SUBLOTTO"/>
			<gene:campoScheda campo="COMPSUBLOTTO"/>
			<gene:campoScheda campo="DESCPROD"/>
			<gene:campoScheda campo="AIC_PARAF"/>
			<gene:campoScheda campo="UNI_MIS"/>
			<gene:campoScheda campo="FORMA"/>
			<gene:campoScheda campo="QUANT"/>
			<gene:campoScheda campo="COD_FORN"/>
			<gene:campoScheda campo="PIVA_FORN"/>
			<gene:campoScheda campo="RAG_SOC_FORN"/>
			<gene:campoScheda campo="NOM_COMME"/>
			<gene:campoScheda campo="ATC_CND"/>
			<gene:campoScheda campo="CLA_FARM"/>
			<gene:campoScheda campo="COD_REP_DM"/>
			<gene:campoScheda campo="COD_PRO_INT_FORN"/>
			<gene:campoScheda campo="COD_BARRE"/>
			<gene:campoScheda campo="CLASSE_MERC"/>
			<gene:campoScheda campo="NUM_PEZZ_CONF"/>
			<gene:campoScheda campo="COD_LIST_ACQ"/>
			<gene:campoScheda campo="PREZ_LIST_CONF"/>
			<gene:campoScheda campo="PREZ_OFFE_UNIT"/>
			<gene:campoScheda campo="FLAG_EX"/>
			<gene:campoScheda campo="SCONTO"/>
			<gene:campoScheda campo="SCONTO2"/>
			<gene:campoScheda campo="ALI_IVA"/>
			<gene:campoScheda campo="ALI_IVA_AGE"/>
			<gene:campoScheda campo="COD_LIST_VEND"/>
			<gene:campoScheda campo="CLASS_1_LIV"/>
			<gene:campoScheda campo="CLASS_2_LIV"/>
			<gene:campoScheda campo="STATO_ARI"/>
			<gene:campoScheda campo="GG_SCORTA_MIN"/>
			<gene:campoScheda campo="GG_SCORTA_MAX"/>
			<gene:campoScheda campo="TEMP_CONS"/>
			<gene:campoScheda campo="GG_PRE_CAP_CONS"/>
			<gene:campoScheda campo="IMB_SEC"/>
			<gene:campoScheda campo="MIN_FATT"/>
			<gene:campoScheda campo="PREV_TRAS_SPEC"/>
			<gene:campoScheda campo="CONF_VEND_PICC"/>
			<gene:campoScheda campo="VALORE_LOTTO"/>
			<gene:campoScheda campo="PESO"/>
			<gene:campoScheda campo="DIM_X"/>
			<gene:campoScheda campo="DIM_Y"/>
			<gene:campoScheda campo="DIM_Z"/>
			<gene:campoScheda campo="VOLUMINOSO"/>
			<gene:campoScheda campo="COD_SOST"/>
			<gene:campoScheda campo="MOD_SOSTIT"/>
			<gene:campoScheda campo="QUANT_MIN_RIOR"/>
			<gene:campoScheda campo="QUANT_MIN_REINT"/>
			
			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteModifica" />
			
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
		
	</gene:redefineInsert>

</gene:template>