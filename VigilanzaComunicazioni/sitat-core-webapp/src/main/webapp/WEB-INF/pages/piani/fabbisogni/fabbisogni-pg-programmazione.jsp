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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>

<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="PIATRI" pagesize="20" sortColumn="2" tableclass="datilista" gestisciProtezioni="true"
			where='exists(select 1 from INTTRI,PTAPPROVAZIONI where piatri.CONTRI=PTAPPROVAZIONI.ID_PROGRAMMA AND 
			PTAPPROVAZIONI.ID_PROGRAMMA = INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA = INTTRI.CONINT AND 
			PTAPPROVAZIONI.CONINT = #INTTRI.CONINT#)' >

				<gene:campoLista campo="ANNTRI" />
				<gene:campoLista campo="ID"   />
				<gene:campoLista campo="BRETRI"  />
				<gene:campoLista campo="NOMTEC" entita="TECNI" title="Responsabile del programma" where="PIATRI.RESPRO=TECNI.CODTEC"  />
				<gene:campoLista campo="DAPTRI"  />
				<gene:campoLista campo="TOTINT" entita="INTTRI" from="PTAPPROVAZIONI" where="piatri.CONTRI=PTAPPROVAZIONI.ID_PROGRAMMA and PTAPPROVAZIONI.ID_PROGRAMMA = INTTRI.CONTRI and PTAPPROVAZIONI.ID_INTERV_PROGRAMMA = INTTRI.CONINT"  />
				
		</gene:formLista></td>
	</tr>
	<gene:redefineInsert name="listaNuovo" />
	<gene:redefineInsert name="listaEliminaSelezione" />
</table>