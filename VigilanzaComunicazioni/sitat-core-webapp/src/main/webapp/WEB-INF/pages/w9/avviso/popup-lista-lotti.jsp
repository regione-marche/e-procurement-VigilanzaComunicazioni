<%/*
       * Created on 01-Ott-2009
       *
       * Copyright (c) EldaSoft S.p.A.
       * Tutti i diritti sono riservati.
       * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
       * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
       * aver prima formalizzato un accordo specifico con EldaSoft.
       */
%>


<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="filtro" value="W9LOTT.CODGARA IN (SELECT CODGARA FROM W9GARA WHERE W9GARA.CODEIN = '${sessionScope.uffint}')" />
<c:set var="tmp" value=""></c:set>
<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera"	value="Lista Lotti" />
	<gene:redefineInsert name="gestioneHistory" ></gene:redefineInsert>
	<gene:redefineInsert name="corpo">
	<table class="lista">
	<tr>
		<td>
  		<gene:formLista entita="W9LOTT" pagesize="10" where="${filtro}" tableclass="datilista" sortColumn="4" gestisciProtezioni="false">
  			<gene:campoLista campo="CODGARA" visibile="false" />
  			<gene:campoLista campo="CODLOTT" visibile="false" />
			<gene:campoLista title="Opzioni" width="50">
				<c:if test="${currentRow >= 0}" >
				<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
					<c:set var="tmp" value="'${datiRiga.W9LOTT_CODGARA}', '${datiRiga.W9LOTT_CODLOTT}', '${datiRiga.W9LOTT_CIG}'" />
					<gene:PopUpItem title="Seleziona" href="javascript:selezionaLotto(${tmp});"/>
				</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<gene:campoLista campo="CIG" href="javascript:selezionaLotto(${tmp});" headerClass="sortable"/>
			<gene:campoLista campo="OGGETTO" headerClass="sortable"/>
		</gene:formLista>
		</td>
	</tr>
	</table>
	</gene:redefineInsert>
	<gene:javaScript>
	
	function selezionaLotto(codgara, codlott, cig){
		//window.opener.document.getElementById("AVVISO_CODGARA").value = codgara;
		//window.opener.document.getElementById("AVVISO_CODLOTT").value = codlott;
		window.opener.document.getElementById("AVVISO_CIG").value = cig;
		window.close();
	}
	</gene:javaScript>
</gene:template>
