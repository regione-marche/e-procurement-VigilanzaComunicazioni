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
		<tr>
			<td>
				<gene:formLista entita="W9CF_REGPERMESSI" 
						pagesize="25" tableclass="datilista" sortColumn="1;2" gestisciProtezioni="true"
						gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9CF_REGPERMESSI">
					
					<gene:campoLista campo="CODCONF" visibile="false" edit="${param.updateLista eq 1}"/>
					<gene:campoLista campo="NUMREG" visibile="false" edit="${param.updateLista eq 1}"/>
					<gene:campoLista campo="RUOLO" headerClass="sortable" />
					<gene:campoLista campo="MACROFASE" title="Fase" headerClass="sortable" />
					<gene:campoLista campo="PERMESSO" headerClass="sortable" edit="${param.updateLista eq 1}"
							gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreTabellatoNoOpzioneVuota"/>

				</gene:formLista>
			</td>
		</tr>
		
		<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
		<gene:redefineInsert name="listaEliminaSelezione" />
	
<c:choose>
	<c:when test='${param.updateLista ne 1}'>
		<gene:redefineInsert name="listaNuovo">
			<tr>
			<td class="vocemenulaterale"><a
					href="javascript:listaApriInModifica()" title="Modifica permessi"
					tabindex="1501">Modifica</a></td>
			</tr>
		</gene:redefineInsert>
		<gene:redefineInsert name="pulsanteListaInserisci">
				<INPUT type="button" class="bottone-azione" title='Modifica permessi'
				value="Modifica" onclick="javascript:listaApriInModifica();">
		</gene:redefineInsert>
	</c:when>
	<c:otherwise>
		<gene:redefineInsert name="listaNuovo">
			<tr><td class="vocemenulaterale"><a
					href="javascript:listaConferma();" title="Salva"
					tabindex="1501">Salva</a></td></tr>
			<tr><td class="vocemenulaterale"><a
					href="javascript:listaAnnullaModifica();" title="Annulla"
					tabindex="1501">Annulla</a></td></tr>
		</gene:redefineInsert>
		<gene:redefineInsert name="pulsanteListaInserisci">
				<INPUT type="button" class="bottone-azione" title='Modifica permessi'
				value="Salva" onclick="javascript:listaConferma();">
				
				<INPUT type="button" class="bottone-azione" title='Annulla'
				value="Annulla" onclick="javascript:listaAnnullaModifica();">
		</gene:redefineInsert>
	</c:otherwise>
</c:choose>
		<tr>
			<jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" />
		</tr>

</table>