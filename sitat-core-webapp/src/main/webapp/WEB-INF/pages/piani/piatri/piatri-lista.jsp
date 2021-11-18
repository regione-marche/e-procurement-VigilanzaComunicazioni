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

<c:set var="sceltaUfficioAttiva" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.UfficioSAAttivoFunction",pageContext)}' />
<c:choose>
	<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C" or !gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.APPLICA-FILTRO-UTENTE")}'>
		<c:set var="where1" value="PIATRI.CENINT = '${sessionScope.uffint}'" />
	</c:when>
	<c:otherwise>
		<c:set var="where1" value="PIATRI.SYSCON = ${sessionScope.profiloUtente.id} AND PIATRI.CENINT = '${sessionScope.uffint}'" />
	</c:otherwise>
</c:choose>
<c:if test="${sceltaUfficioAttiva eq '1' and !empty ufficioAttivo}">
	<c:set var="where1" value="${where1} AND PIATRI.IDUFFICIO = ${ufficioAttivo}" />
</c:if>
<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PIATRI-lista">
	<gene:setString name="titoloMaschera" value="Lista Programmi"/>
	<c:set var="eliminabili" value="false" />
	<c:set var="visualizzaStato" value='${gene:checkProt(pageContext, "COLS.VIS.PIANI.PIATRI.STATRI")}'/>
 	<c:set var="visualizzaLink" value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.PIATRI-scheda")}'/>
 	
 	<gene:redefineInsert name="addToAzioni">
 		<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.TRASFERISCI-APPALTO") and sessionScope.profiloUtente.abilitazioneStd eq "A"}'>
			<tr>
				<td  class="vocemenulaterale">
					<a href="javascript:trasferisciProgrammi();" title="Trasferisci programmi a..." tabindex="1505">
						Trasferisci programmi a...&nbsp;
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
	
  	<gene:formLista entita="PIATRI" where="${where1}" pagesize="20" tableclass="datilista" sortColumn="-3" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI" >
  			<c:if test='${visualizzaStato && datiRiga.PIATRI_STATRI == 2}'>
				<c:set var="link" value="javascript:alert('Attenzione: questo programma è già stato pubblicato. Eventuali modifiche potranno essere pubblicate a seguito di un nuovo invio.');chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			</c:if>
			<c:if test='${!visualizzaStato || datiRiga.PIATRI_STATRI != 2}'>
				<c:set var="link" value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			</c:if>
			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>"
						width="80">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if test='${visualizzaLink}' >
							<gene:PopUpItem title="Visualizza Programma" href="${link}" />
						</c:if>
						<c:set var="cancella" value="si" />
						<c:set var="cancella" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,chiaveRiga,"PIATRI")}' />
						<c:if test='${visualizzaStato}'>
							<c:if test='${datiRiga.PIATRI_STATRI != 1 && !empty datiRiga.PIATRI_STATRI}'>
								<c:set var="cancella" value="no" />
							</c:if>
							<c:if test='${datiRiga.PIATRI_CONTRI < 0}'>
								<c:set var="cancella" value="si" />
							</c:if>
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") && (cancella eq "si")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina" title="Elimina anagrafica Programma"/>
							<c:set var="eliminabili" value="true" />
						</c:if>
						<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.TRASFERISCI-APPALTO")}'>
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</c:if>
						<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.ANNULLAPUBBLICAZIONE")}'>
						  <c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.INS.PIANI.PIATRI")}'>
							<c:if test='${visualizzaStato && (datiRiga.PIATRI_STATRI == 2 || datiRiga.PIATRI_STATRI == 3 || datiRiga.PIATRI_STATRI == 5)}'>
								<gene:PopUpItem title="Annulla pubblicazione" href="javascript:annullaPubblicazione('${datiRiga.PIATRI_CONTRI}');" />
							</c:if>
						  </c:if>
						</c:if>
						<c:if test='${gene:checkProt(pageContext,"FUNZ.VIS.INS.PIANI.PIATRI")}'>
							<c:if test='${cancella eq "no" && datiRiga.PIATRI_NORMA == 2}'>
								<gene:PopUpItem title="Copia per aggiornamento" href="javascript:copiaPubblicazione('${datiRiga.PIATRI_CONTRI}');" />
							</c:if>
						</c:if>
					</gene:PopUp>
					<c:if test='${cancella eq "no" and gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.W9SCP")}'>
						<c:if test='${not empty datiRiga.PIATRI_ID_RICEVUTO}'>
							<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguita.png"	title="Programma pubblicato" alt="Programma pubblicato" />
						</c:if>
						<c:if test='${empty datiRiga.PIATRI_ID_RICEVUTO}'>
							<img src="${pageContext.request.contextPath}/img/pubblicazioneEseguitaOR.png"	title="Programma pubblicato Osservatorio" alt="Programma pubblicato Osservatorio" />
						</c:if>
					</c:if>
				</c:if>
			</gene:campoLista>
			<% // Campi veri e propri %>

			<gene:campoLista campo="CONTRI" visibile="false"/>
			<gene:campoLista campo="ID" visibile="true" headerClass="sortable" href="${gene:if(visualizzaLink, link, '')}" width="100" />
			<gene:campoLista campo="ANNTRI" headerClass="sortable"/>
			<gene:campoLista campo="BRETRI" headerClass="sortable"/>
			<gene:campoLista campo="TIPROG" headerClass="sortable"/>
			<c:if test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C"}'>
				<gene:campoLista campo="SYSUTE" entita="USRSYS"  where="PIATRI.SYSCON=USRSYS.SYSCON" />
			</c:if>
			<gene:campoLista campo="STATRI" visibile='${visualizzaStato}'/>
			<gene:campoLista campo="NORMA" visibile='false'/>
			<gene:campoLista campo="ID_RICEVUTO" visibile="false"/>	
		</gene:formLista>
				</td>					
				</tr>
				<gene:redefineInsert name="listaNuovo" />
				<gene:redefineInsert name="pulsanteListaInserisci" />
				<gene:redefineInsert name="pulsanteListaEliminaSelezione" />
				<gene:redefineInsert name="listaEliminaSelezione" />
				<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>				  
		</table>
  </gene:redefineInsert>
  
  <gene:javaScript>
  	function annullaPubblicazione(contri) {
  		if (confirm("Vuoi annullare la pubblicazione del programma?")) {
  			var action = "${pageContext.request.contextPath}/piani/AnnullaPubblicazione.do?" + csrfToken + "&";
  			var par = new String('contri=' + contri);
  			document.forms[0].action = action + par;
  			document.forms[0].metodo.value="annullaPubblicazione";
			document.forms[0].submit();
		}
	}
	
	  	function copiaPubblicazione(contri) {
  		if (confirm("Vuoi copiare il programma?")) {
  			var action = "${pageContext.request.contextPath}/piani/CopiaPubblicazione.do?" + csrfToken + "&";
  			var par = new String('contri=' + contri);
  			document.forms[0].action = action + par;
  			document.forms[0].metodo.value="copiaPubblicazione";
			document.forms[0].submit();
		}
	}
	
	function trasferisciProgrammi() {
		var programmi = document.getElementsByName("keys");
		var numeroOggetti = contaCheckSelezionati(programmi); 
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
		} else {
			var listaProgrammi="";
			for (i = 0; i < programmi.length; i++) {
				if (programmi[i].checked)
					listaProgrammi += programmi[i].value + "--";
          	}
			location.href = '${pageContext.request.contextPath}/ApriPagina.do?' + csrfToken + '&href=w9/commons/trasferisci-appalto.jsp&procedura=programmi&chiavi='+listaProgrammi;
		}
	}
  </gene:javaScript>
</gene:template>