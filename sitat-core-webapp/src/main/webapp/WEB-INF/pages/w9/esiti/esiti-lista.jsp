<%
	/*
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

<c:choose>
	<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
		<c:set var="where1" value=" W9PUBBLICAZIONI.CODGARA in (select CODGARA from W9GARA where CODEIN = '${sessionScope.uffint}')" />
	</c:when>
	<c:otherwise>
		<c:set var="where1" value=" W9PUBBLICAZIONI.CODGARA in (select CODGARA from W9GARA where CODEIN = '${sessionScope.uffint}') AND W9PUBBLICAZIONI.CODGARA in (select p.CODGARA from V_W9PERMESSI p, V_RUOLOTECNICO r where p.CODGARA=r.CODGARA and p.PERMESSO > 1 and p.RUOLO=r.ID_RUOLO and r.SYSCON = ${sessionScope.profiloUtente.id})" />
	</c:otherwise>
</c:choose>


<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9ESITI-lista">
	<gene:setString name="titoloMaschera" value="Lista Esiti" />
	<c:set var="tipoRicerca" value="esiti" scope="session" />
	<c:set var="eliminabili" value="false" />
	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.W9.W9ESITO-scheda")}' />
	
	<gene:redefineInsert name="corpo">
		
		<gene:set name="titoloMenu">
			<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
		</gene:set>
		
		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="W9PUBBLICAZIONI" where="${where1}" pagesize="20" tableclass="datilista" sortColumn="4"
							gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9PUBBLICAZIONI" >
						<gene:campoLista campo="TIPDOC" visibile="false"/>
						<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" width="50">
							<c:if test="${currentRow >= 0}">
								<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'" >
									<gene:PopUpItemResource	resource="popupmenu.tags.lista.visualizza" title="Visualizza esito"
									 href="location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=${chiaveRigaJava}&tipoPubblicazione=${datiRiga.W9PUBBLICAZIONI_TIPDOC}'" />
								</gene:PopUp>
								<c:if test='${not empty datiRiga.W9FLUSSI_IDFLUSSO and gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
									<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguita.png"	title="Esito pubblicato" alt="Esito pubblicato" />
								</c:if>
							</c:if>
						</gene:campoLista>

						<%	// Campi veri e propri	%>
						
						<gene:campoLista campo="NUM_PUBB" visibile="false" />
						<gene:campoLista campo="CODGARA" headerClass="sortable"/>
						<gene:campoLista campo="OGGETTO" entita="W9GARA" where="W9GARA.CODGARA=W9PUBBLICAZIONI.CODGARA" headerClass="sortable" href="${pageContext.request.contextPath}/ApriPagina.do?href=w9/w9pubblicazioni/w9pubblicazioni-scheda.jsp&key=${chiaveRigaJava}&tipoPubblicazione=${datiRiga.W9PUBBLICAZIONI_TIPDOC}"/>
						<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
							<gene:campoLista campo="SYSUTE" entita="USRSYS" from="W9GARA" where="W9GARA.CODGARA=W9PUBBLICAZIONI.CODGARA and W9GARA.SYSCON=USRSYS.SYSCON" />
						</c:if>
						<gene:campoLista title="CIG" campo="LISTA_CIG" campoFittizio="true" definizione="T200;0;;;" width="30" >
							<c:set var="listaCIG" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.GetListaCIGEsitiFunction", pageContext, gene:getValCampo(chiaveRigaJava,"CODGARA"), gene:getValCampo(chiaveRigaJava,"NUM_PUBB"))}' />
							${listaCIG}
						</gene:campoLista>
						
						<gene:campoLista campo="IDFLUSSO" entita="W9FLUSSI" where="W9FLUSSI.KEY01=W9PUBBLICAZIONI.CODGARA and W9FLUSSI.KEY03 = 901 and W9FLUSSI.KEY04=W9PUBBLICAZIONI.NUM_PUBB and W9FLUSSI.AREA = 2  and W9FLUSSI.CFSA = (select CFEIN from UFFINT where CODEIN ='${sessionScope.uffint}')" visibile="false" />
						
					</gene:formLista>
				</td>
			</tr>

			<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
			<gene:redefineInsert name="listaEliminaSelezione" />

			<gene:redefineInsert name="pulsanteListaInserisci" />
			<gene:redefineInsert name="listaNuovo" />
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	
</gene:template>
