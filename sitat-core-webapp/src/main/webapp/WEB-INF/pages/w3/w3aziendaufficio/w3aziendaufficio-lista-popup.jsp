<%
/*
 * Created on: 06-ott-2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

 /* Lista popup di selezione del tecnico */
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="W3" idMaschera="W3AZIENDAUFFICIO-lista-popup">
	<gene:setString name="titoloMaschera" value="Selezione della collaborazione (amministrazione/stazione appaltante)"/>
	<gene:redefineInsert name="corpo">
		<gene:formLista pagesize="25" tableclass="datilista" 
			entita="W3AZIENDAUFFICIO" sortColumn="2" gestisciProtezioni="true" 
			inserisciDaArchivio='${gene:checkProtFunz(pageContext,"INS","nuovo")}' >
			<gene:campoLista title="Opzioni" width="50">
				<c:if test="${currentRow >= 0}" >
				<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
					<gene:PopUpItem title="Seleziona" href="javascript:archivioSeleziona(${datiArchivioArrayJs});"/>
				</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<gene:campoLista campo="ID" visibile="false" width="30" href="javascript:archivioSeleziona(${datiArchivioArrayJs});" />
			<gene:campoLista title="Denominazione stazione appaltante" campo="UFFICIO_DENOM" href="javascript:archivioSeleziona(${datiArchivioArrayJs});" />
			<gene:campoLista title="Codice fiscale amministrazione" campo="AZIENDA_CF" />
			<gene:campoLista title="Denominazione amministrazione" campo="AZIENDA_DENOM" />
			<gene:campoLista title="Indice" width="50" campo="INDEXCOLL" />
		</gene:formLista>
  </gene:redefineInsert>
</gene:template>
