<%/*
       * Created on 03/08/2009
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

<div style="display: none" >

<c:set var="filtroINTTRI" value="1" scope="session" />

<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="INTTRI-popup-lista">
	<gene:setString name="titoloMaschera" value="Lista Interventi"/>

 	
 	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
  	<%// Creo la lista per peri %>
		<table class="lista">
			<tr>
				<td>

  	<gene:formLista entita="INTTRI" pagesize="20" tableclass="datilista" sortColumn="3" gestisciProtezioni="true" >
		<% // Campi veri e propri %>

		<gene:campoLista campo="CONTRI" visibile="false"/>
		<gene:campoLista campo="CONINT" visibile="false"/>
		<gene:campoLista campo="TIPINT" headerClass="sortable"/>
	</gene:formLista>
		
				</td>					
				</tr>
		  
		</table>
  </gene:redefineInsert>
  
  <gene:javaScript>

		$(document).ready(function() {
			window.opener.document.forms[0].trovaAddWhere.value = document.forms[0].trovaAddWhere.value;
			window.opener.document.forms[0].trovaParameter.value = document.forms[0].trovaParameter.value;
			window.opener.listaVaiAPagina(0);
			window.close();
		});

  </gene:javaScript>
</gene:template>

</div>