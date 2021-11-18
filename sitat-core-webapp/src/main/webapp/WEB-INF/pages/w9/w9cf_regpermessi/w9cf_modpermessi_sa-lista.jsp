<%/*
       * Created on 11-dic-2014
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

	<table class="dettaglio-tab-lista">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		
		<tr>
			<td>
				<gene:formLista entita="UFFINT" where=" CODEIN in (SELECT CODEIN from W9CF_MODPERMESSI_SA where CODCONF=1) "
						pagesize="2000" tableclass="datilista" sortColumn="2" gestisciProtezioni="false"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9CF_REGPERMESSI" >

					<gene:campoLista campo="CODEIN" ordinabile="false" visibile="true" />
					<gene:campoLista campo="NOMEIN" ordinabile="false" />

					<gene:campoLista campo="CODCONF" visibile="false" edit="${param.updateLista eq 1}" entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />
					<gene:campoLista campo="CODEIN"  visibile="false" entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />
					<gene:campoLista campo="APPLICA" ordinabile="false" entita="W9CF_MODPERMESSI_SA" where="UFFINT.CODEIN = W9CF_MODPERMESSI_SA.CODEIN" />

				</gene:formLista>
			</td>
		</tr>
		
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="listaEliminaSelezione" />
		
		<gene:redefineInsert name="listaNuovo">
			<td class="vocemenulaterale"><a
					href="javascript:listaApriInModifica();" title="Modifica associazioni"
					tabindex="1501">Modifica associazioni</a></td>
		</gene:redefineInsert>
		<gene:redefineInsert name="pulsanteListaInserisci">
				<INPUT type="button" class="bottone-azione" title='Modifica associazioni'
				value="Modifica associazioni" onclick="listaApriInModifica();">
		</gene:redefineInsert>

		<tr>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
		</tr>

</table>