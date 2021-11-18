
<%
/*
 * Created on: 08-mar-2007
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

 /* Lista popup di selezione del Centro di Costo*/
%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="lista-centricosto-popup">
	<gene:setString name="titoloMaschera" value="Lista dei Centri Costi" />
	<gene:redefineInsert name="corpo">
		<gene:formLista pagesize="25" tableclass="datilista" pathSchedaPopUp="w9/centricosto/centricosto-scheda-popup.jsp"
			entita="CENTRICOSTO" sortColumn="5" gestisciProtezioni="true"
			inserisciDaArchivio='${gene:checkProtFunz(pageContext,"INS","nuovo")}'>
			<% // Aggiungo gli item al menu contestuale di riga %>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="25">
				<gene:PopUp variableJs="rigaPopUpMenu" onClick="chiaveRiga='${chiaveRigaJava}'" />
			</gene:campoLista>
			<% // Campi della lista %>
			<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="CODEIN" visibile="false" />
			<gene:campoLista campo="IDCENTRO" visibile="false" />
			<gene:campoLista campo="CODCENTRO" headerClass="sortable" width="90" href="javascript:archivioSeleziona(${datiArchivioArrayJs})" />
			<gene:campoLista campo="DENOMCENTRO" headerClass="sortable"
				width="120"  />
		</gene:formLista>
		</td>
		</tr>
		</table>
	</gene:redefineInsert>
	<% //Aggiunta dei menu sulla riga %>
	<c:if test='${gene:checkProtObj(pageContext, "MASC.VIS", "GENE.SchedaCentri")}'>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.visualizza"
			title="Visualizza" />
	</c:if>
	<c:if test='${gene:checkProtObj(pageContext, "MASC.VIS", "GENE.SchedaCentri") and gene:checkProtFunz(pageContext, "MOD", "MOD")}'>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.modifica"
			title="Modifica" />
	</c:if>
	<c:if test='${gene:checkProtFunz(pageContext, "DEL", "DEL")}'>
		<gene:PopUpItemResource variableJs="rigaPopUpMenu"
			resource="popupmenu.tags.lista.elimina"
			title="Elimina" />
	</c:if>
</gene:template>
