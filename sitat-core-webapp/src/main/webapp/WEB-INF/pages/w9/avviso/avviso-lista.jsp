<%/*
       * Created on 26/02/2010
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


<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="AVVISO-lista">
	<gene:setString name="titoloMaschera" value="Lista Avvisi"/>
	<c:set var="tipoRicerca" value="avvisi" scope="session" />
	<c:set var="eliminabili" value="false" />
 	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.AVVISO-scheda")}'/>
 	
 	<gene:redefineInsert name="addToAzioni">
 		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.TRASFERISCI-APPALTO") and sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			<tr>
				<td  class="vocemenulaterale">
					<a href="javascript:trasferisciAvvisi();" title="Trasferisci avvisi a..." tabindex="1505">
						Trasferisci avvisi a...&nbsp;
					</a>
				</td>
			</tr>
		</c:if>
 	</gene:redefineInsert>
 	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
  	<%// Creo la lista per peri %>
		<table class="lista">
			<tr>
				<td>
<c:choose>
	<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
		<c:set var="where1" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="where1" value=" AND (AVVISO.SYSCON = ${sessionScope.profiloUtente.id} OR AVVISO.SYSCON IS NULL)" />
	</c:otherwise>
</c:choose>
	
  	<gene:formLista entita="AVVISO" where="AVVISO.CODEIN = '${sessionScope.uffint}' ${where1}" pagesize="20" tableclass="datilista" sortColumn="2" gestisciProtezioni="true" >
			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="80">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if test='${gene:checkProt(pageContext, "MASC.VIS.W9.AVVISO-scheda")}' >
							<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza Avviso"/>
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "MOD", "MOD")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.modifica" title="Modifica Avviso" />
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") and empty datiRiga.W9FLUSSI_IDFLUSSO}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina Avviso"/>
						</c:if>
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.TRASFERISCI-APPALTO")}'>
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</c:if>
					</gene:PopUp>
					<c:if test='${not empty datiRiga.W9FLUSSI_IDFLUSSO and gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
						<c:if test='${not empty datiRiga.AVVISO_ID_RICEVUTO}'>
							<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguita.png"	title="Avviso pubblicato" alt="Avviso pubblicato" />
						</c:if>
						<c:if test='${empty datiRiga.AVVISO_ID_RICEVUTO}'>
							<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguitaOR.png"	title="Avviso pubblicato Osservatorio" alt="Avviso pubblicato Osservatorio" />
						</c:if>
					</c:if>
				</c:if>
			</gene:campoLista>
			<% // Campi veri e propri %>
			<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="IDAVVISO" visibile="true" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" width="100" />
			<gene:campoLista campo="CIG" headerClass="sortable"/>
			<gene:campoLista campo="TIPOAVV" headerClass="sortable"/>
			<gene:campoLista campo="DESCRI" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}"/>
			<gene:campoLista campo="DATAAVV" headerClass="sortable"/>
			<gene:campoLista campo="DATASCADENZA" headerClass="sortable"/>
			<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
				<gene:campoLista campo="SYSUTE" entita="USRSYS"  where="AVVISO.SYSCON=USRSYS.SYSCON" />
			</c:if>
			<gene:campoLista title="idFlusso" campo="IDFLUSSO" entita="W9FLUSSI" where="W9FLUSSI.KEY01=AVVISO.IDAVVISO and AVVISO.CODEIN = '${sessionScope.uffint}' and W9FLUSSI.AREA = 3  and W9FLUSSI.CFSA = (select CFEIN from UFFINT where CODEIN ='${sessionScope.uffint}')" visibile="false" />
			<gene:campoLista campo="ID_RICEVUTO" visibile="false"/>			
		</gene:formLista>
				</td>
			</tr>
			
			<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
			<gene:redefineInsert name="listaEliminaSelezione" />
			
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>				  
		</table>
  </gene:redefineInsert>
  <gene:javaScript>
		function trasferisciAvvisi() {
			var avvisi = document.getElementsByName("keys");
			var numeroOggetti = contaCheckSelezionati(avvisi); 
			if (numeroOggetti == 0) {
				alert("Nessun elemento selezionato nella lista");
			} else {
				var listaAvvisi="";
				for (i = 0; i < avvisi.length; i++) {
					if (avvisi[i].checked)
						listaAvvisi += avvisi[i].value + "--";
          		}
				location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/commons/trasferisci-appalto.jsp&procedura=avvisi&chiavi='+listaAvvisi;
			}
		}
  </gene:javaScript>
</gene:template>