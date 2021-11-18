
<%
  /*
   * Created on 28-nov-2008
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

<c:set var="visualizzaLink"
	value='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}' />
	
<gene:set name="tipint"	value="2" scope="sessionScope"/>

<gene:set name="titoloMenu">
	<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
</gene:set>
<c:set var="attivaIncolla"
	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.AttivaIncollaFunction",pageContext,key)}' />

<c:set var="prot" value='${gene:checkProtFunz(pageContext, "INS", "INS")}' />
<c:choose>
	<c:when test="${prot}">
		<gene:redefineInsert name="addToAzioni">
			<tr>
				<td class="vocemenulaterale" ><a 
				 href="javascript:copiaMultiIntervento();" tabindex="1504"
				 title="Copia selezionati">Copia selezionati</a></td>
			</tr>
			<c:if test='${attivaIncolla eq "attivo" }'>
				<tr>
					<td class="vocemenulaterale"><a
						href="javascript:incollaIntervento('${key}');" tabindex="1505"
						title="Incolla">Incolla</a></td>
				</tr>
			</c:if>
			<tr>
				<td class="vocemenulaterale" ><a 
				 href="javascript:cambiaRUPInterventi();" tabindex="1504"
				 title="Cambia RUP">Cambia RUP</a></td>
			</tr>			
		</gene:redefineInsert>
		<gene:redefineInsert name="listaNuovo">
			<td class="vocemenulaterale"><a href="javascript:listaNuovo();"
				title="Inserisci" tabindex="1501"> Nuovo </a></td>
			<gene:set name="titoloMenu">
				<jsp:include page="/WEB-INF/pages/commons/iconeCheckUncheck.jsp" />
			</gene:set>
		</gene:redefineInsert>
	</c:when>
	<c:otherwise>
		<gene:redefineInsert name="listaNuovo" />
		<gene:redefineInsert name="pulsanteListaInserisci" />
	</c:otherwise>
</c:choose>
<%
  // Creo la lista per INTTRI
%>
<table class="dettaglio-tab-lista">
	<tr>
		<td><gene:formLista entita="INTTRI" pagesize="20" sortColumn="2"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINTTRI"
			tableclass="datilista" gestisciProtezioni="true"
			where='INTTRI.CONTRI = #PIATRI.CONTRI# AND (INTTRI.TIPINT IS NULL OR INTTRI.TIPINT = 2) '>

			<gene:campoLista title="Opzioni<center>${titoloMenu}</center>"
				width="50">
				<c:if test="${currentRow >= 0}">
					<gene:PopUp variableJs="rigaPopUpMenu${currentRow}"
						onClick="chiaveRiga='${chiaveRigaJava}'">
						<c:if
							test='${gene:checkProt(pageContext, "MASC.VIS.PIANI.INTTRI-scheda")}'>
							<gene:PopUpItemResource
								resource="popupmenu.tags.lista.visualizza"
								title="Visualizza dettaglio" />
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL")}'>
							<gene:PopUpItemResource resource="popupmenu.tags.lista.elimina"
								title="Elimina" />
							<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
						</c:if>
						<c:if test='${gene:checkProtFunz(pageContext, "INS","INS")}'>
							<gene:PopUpItem title="Copia"
								href="javascript:copiaIntervento('${chiaveRiga}');" />
						</c:if>
					</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<c:set var="link"
				value="javascript:chiaveRiga='${chiaveRigaJava}';listaVisualizza();" />
			<gene:campoLista campo="CONINT" headerClass="sortable" visibile="true" href="${gene:if(visualizzaLink, link, '')}" />
			<gene:campoLista campo="CODINT" headerClass="sortable" visibile="true" width="70" />
			<gene:campoLista campo="DESINT" headerClass="sortable" visibile="true" width="390" />
			<gene:campoLista campo="DITINT" headerClass="sortable" visibile="true" width="140" />
		</gene:formLista></td>
	</tr>
	<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
</table>

<gene:javaScript>

	function copiaIntervento(idIntervento) {
		var actionCopia = "${pageContext.request.contextPath}/piani/CopiaIntervento.do";
		var par = new String('chiave=' + idIntervento);
		openPopUpActionCustom(actionCopia, par, 'CopiaIntervento', 1, 1, "no", "no");
	}
	
	function copiaMultiIntervento() {
		var valoriDaCopiare = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
		 } else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
            copiaIntervento(document.forms[0].keys.value); 
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriDaCopiare += document.forms[0].keys[i].value + ";;";
				}
			}
			valoriDaCopiare = valoriDaCopiare.substring(0,(valoriDaCopiare.length - 2));
			copiaIntervento(valoriDaCopiare);
		}
	}

	function incollaIntervento(idContri) {
		if(confirm('Incollare l\'intervento(gli interventi) copiato(copiati) dal programma ' + '${idProgramma}' + '?')) {
			var actionIncolla = "${pageContext.request.contextPath}/piani/IncollaIntervento.do";
			var par = new String('keyatt=' + idContri);
			openPopUpActionCustom(actionIncolla, par, 'IncollaIntervento', 1, 1, "no", "no");
		}
	}
	
	function cambiaRUPInterventi() {
		var valoriDaCopiare = "";
		var numeroOggetti = contaCheckSelezionati(document.forms[0].keys);
		if (numeroOggetti == 0) {
			alert("Nessun elemento selezionato nella lista");
			return;
		 } else if (numeroOggetti == 1 && (document.forms[0].keys[0]==undefined)) {
		 	valoriDaCopiare = document.forms[0].keys.value;
        } else {
			for (var i = 0; i < document.forms[0].keys.length; i++) {
				if (document.forms[0].keys[i].checked) {
					valoriDaCopiare += document.forms[0].keys[i].value + ";;";
				}
			}
			valoriDaCopiare = valoriDaCopiare.substring(0,(valoriDaCopiare.length - 2));
		}
		if(confirm('La funzione cambierà il RUP in tutti gli interventi selezionati. Continuare?')) {
			openPopUpCustom("href=piani/piatri/popup-cambia-RUP.jsp?interventi=" + valoriDaCopiare, "cambiaRUP", 650, 400, 1, 1);
		}
	}
	
</gene:javaScript>

