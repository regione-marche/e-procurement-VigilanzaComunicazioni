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

<gene:template file="lista-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9OUTBOX-lista">
	<gene:setString name="titoloMaschera" value="Lista comunicazioni SCP" />

	<gene:redefineInsert name="corpo">
		<gene:redefineInsert name="pulsanteListaInserisci" />
		<gene:redefineInsert name="listaNuovo" />

		<table class="lista">
			<tr>
				<td>
					<gene:formLista entita="W9OUTBOX" where="" pagesize="20" tableclass="datilista" sortColumn="4" gestisciProtezioni="true"
					gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9OUTBOX" >
						<gene:campoLista title="Opzioni<center>${titoloMenu}</center>" 	width="50">
						<c:if test="${currentRow >= 0}">
							<gene:PopUp variableJs="rigaPopUpMenu${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
								<c:if	test='${gene:checkProt(pageContext, "MASC.VIS.W9.W9OUTBOX-scheda")}'>
									<gene:PopUpItemResource resource="popupmenu.tags.lista.visualizza" title="Visualizza dettaglio" />
								</c:if>
								
								<c:if test='${datiRiga.W9OUTBOX_STATO eq 3}'>
										<gene:PopUpItemResource resource="" title="Riprova invio" href="javascript:eseguiInvio('${datiRiga.W9OUTBOX_IDCOMUN}');" />
								</c:if>
																
								<c:if test='${gene:checkProtFunz(pageContext, "DEL","DEL") and datiRiga.W9OUTBOX_STATO ne 2}'>
									<gene:PopUpItemResource	resource="popupmenu.tags.lista.elimina" title="Elimina" />
									<input type="checkbox" name="keys" value="${chiaveRigaJava}" />
								</c:if>
								
								
							</gene:PopUp>
						</c:if>
						</gene:campoLista>
					<% 
					  // Campi veri e propri
					%>
						<gene:campoLista campo="STATO" visibile="false"/>
						<gene:campoLista title="Esito" width="50">
							<center>
								<c:choose>
									<c:when test='${empty datiRiga.W9OUTBOX_STATO}'>
										&nbsp;
									</c:when>
									<c:when test='${datiRiga.W9OUTBOX_STATO eq 2}'>
										<img src="${pageContext.request.contextPath}/img/flag_verde.gif" height="16" width="16" title="Positivo">
									</c:when>
									<c:when test='${datiRiga.W9OUTBOX_STATO eq 1}'>
										<img src="${pageContext.request.contextPath}/img/flag_da_analizzare.gif" height="16" width="16" title="Da esportare">
									</c:when>
									<c:when test='${datiRiga.W9OUTBOX_STATO eq 3}'>
										<img src="${pageContext.request.contextPath}/img/flag_rosso.gif" height="16" width="16" title="Negativo">
									</c:when>
								</c:choose>
							</center>
						</gene:campoLista>
						<gene:campoLista campo="IDCOMUN"/>
						<gene:campoLista campo="NOMEIN" entita="UFFINT" where="UFFINT.CFEIN=W9OUTBOX.CFSA" ordinabile="false" />
						
						<gene:campoLista campo="AREA"/>
						<gene:campoLista campo="KEY01"/>
						<gene:campoLista campo="KEY02"/>
						<gene:campoLista campo="DATINV" headerClass="sortable" />
					</gene:formLista>
				</td>
			</tr>
			<tr><jsp:include page="/WEB-INF/pages/commons/pulsantiLista.jsp" /></tr>
		</table>
	</gene:redefineInsert>
	<gene:javaScript>

		function eseguiInvio(idcomun) {
			if (confirm("Riprovare ad inviare la comunicazione selezionata?")) {
				location.href = '${pageContext.request.contextPath}/w9/EseguiReinvioComunicazioneSCP.do?' + csrfToken + '&idcomun=' + idcomun;
			}
		}
		
	</gene:javaScript>
</gene:template>