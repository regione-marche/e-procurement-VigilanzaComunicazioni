
<%
	/*
	 * Created on 22-Set-2006
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

<c:set var="codiciW9XmlAnom" value='${gene:callFunction("it.eldasoft.w9.tags.funzioni.GetListaCodiciW9XmlAnomFunction",pageContext)}' scope="request" />

<c:set var="tipoDB" value="${gene:callFunction('it.eldasoft.gene.tags.utils.functions.GetTipoDBFunction', pageContext)}" />

	<c:choose>
		<c:when test='${tipoDB eq "ORA"}' >
			<c:set var="campoDataFeedback" value="CAST(DATA_FEEDBACK AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "MSQ"}' >
			<c:set var="campoDataFeedback" value="CONVERT(date, DATA_FEEDBACK)" />
		</c:when>
		<c:when test='${tipoDB eq "POS"}' >
			<c:set var="campoDataFeedback" value="CAST(DATA_FEEDBACK AS DATE)" />
		</c:when>
		<c:when test='${tipoDB eq "DB2"}' >
			<c:set var="campoDataFeedback" value="DATE(DATA_FEEDBACK)" />
		</c:when>
	</c:choose>

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9XML-trova">
	<gene:setString name="titoloMaschera" value="Ricerca feedback"/>

	<%
		// Ridefinisco il corpo della ricerca
	%>
	<gene:redefineInsert name="corpo">

		<c:if test='${not empty applicationScope.dataInterruzione}'>
			<table align="center" class="arealayout" style="width: 600">
				<tr>
					<td class="errore-generale"><br>
					<br> Attenzione: <br>
					<br> si comunica che per il giorno
						${applicationScope.dataInterruzione} dalle ore
						${applicationScope.oraInizioInterruzione} alle ore
						${applicationScope.oraFineInterruzione} <br>&egrave; prevista
						l'interruzione del servizio per un aggiornamento. <br> <br>Si
						prega di uscire dall'applicativo prima dell'interruzione. <br>Ci
						scusiamo per i disagi. <br> <br>Grazie per la
						collaborazione.<br>
					<br></td>
				</tr>
			</table>
		</c:if>

		<%
			// Creo la form di trova con i campi dell'entita' W9XML
		%>
		<gene:formTrova entita="W9XML" gestisciProtezioni="true">
			<gene:gruppoCampi idProtezioni="GEN">
				<tr>
					<td colspan="3"><b>Dati del filtro</b>
					</td>
				</tr>
				<gene:campoTrova entita="UFFINT" campo="NOMEIN" from="W9GARA"
					title="Stazione appaltante" definizione="T30;0;;;NOMEIN"
					where="W9GARA.CODGARA = W9XML.CODGARA AND W9GARA.CODEIN = UFFINT.CODEIN" />
				<gene:campoTrova campo="FEEDBACK_ANALISI" />

				<gene:campoTrova campo="${campoDataFeedback}" title="Data importazione dell'elaborazione" computed="true" definizione="D;0;;DATA_ELDA;W9XMLDTFED" />
				
				<gene:campoTrova entita="W9LOTT" campo="CIG"
					where="W9LOTT.CODGARA = W9XML.CODGARA AND W9LOTT.CODLOTT = W9XML.CODLOTT" />
				<gene:campoTrova campo="NUM_ERRORE" />

				<gene:campoTrova campo="CODICE" entita="W9XMLANOM"
					where="W9XML.CODGARA = W9XMLANOM.CODGARA AND W9XML.CODLOTT = W9XMLANOM.CODLOTT AND W9XML.NUMXML = W9XMLANOM.NUMXML" />
				<tr>
					<td class="etichetta-dato">Codice Anomalia:</td>
					<td class="operatore-dato"></td>
					<td class="valore-dato-trova"><select name="codLista" id="codLista">
							<option value=""></option>
							<c:if test='${!empty listaCodici}'>
								<c:forEach items="${listaCodici}" var="listaCodici">
									<option value="${listaCodici[0]}">${listaCodici[0]}</option>
								</c:forEach>
							</c:if>
					</select></td>
				</tr>

			</gene:gruppoCampi>
		</gene:formTrova>
	</gene:redefineInsert>
	<gene:javaScript>

  $(document).ready(function() {
		$("#rowCampo5").hide();

		var temp = getValue("Campo5");
		var mySelect = document.getElementById("codLista");
		
		for(var i, j = 0; i = mySelect.options[j]; j++) {
			if(i.value == temp) {
				mySelect.selectedIndex = j;
				break;
			}
		}
				
	  $("#codLista").on("change",function(){
		setValue("Campo5",getValue("codLista"));
	  });
  
  });
  
  </gene:javaScript>
</gene:template>
