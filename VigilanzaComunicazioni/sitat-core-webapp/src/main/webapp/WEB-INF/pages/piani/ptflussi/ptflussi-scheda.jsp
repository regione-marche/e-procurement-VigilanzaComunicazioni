<%
  /*
   * Created on 17-07-2012
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="pubblica"	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetPubblicaFunction", pageContext, keyParent)}' scope="request"/>
<gene:template file="scheda-template.jsp" gestisciProtezioni="true"	schema="PIANI" idMaschera="PTFLUSSI-scheda">
	<gene:redefineInsert name="corpo">
			
		<c:set var="titoloMaschera" value="Nuova Pubblicazione del Programma"/>
		<c:set var="id_invio" value="ID pubblicazione"/>
		<c:set var="tipo_invio" value="Tipo di pubblicazione"/>
		<c:set var="data_invio" value="Data pubblicazione"/>
		<c:set var="autore" value="Nome dell'autore della pubblicazione"/>

		<c:if test='${pubblica ne "1" || gene:checkProt(pageContext, "FUNZ.VIS.ALT.PIANI.SITAT")}'>
			<c:set var="titoloMaschera" value="Nuovo Invio del Programma"/>
			<c:set var="id_invio" value="ID invio"/>
			<c:set var="tipo_invio" value="Tipo di invio"/>
			<c:set var="data_invio" value="Data invio"/>
			<c:set var="autore" value="Nome dell'autore dell'invio"/>
		</c:if>
		<gene:setString name="titoloMaschera" value='"${titoloMaschera}"' />
		
		<c:set var="contri" value='${gene:getValCampo(keyParent,"CONTRI")}' />
		<c:set var="contriKey" value='PIATRI.CONTRI=N:${gene:getValCampo(keyParent,"CONTRI")};' />
		<c:set var="tiprog"	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, contriKey)}' />
		<gene:formScheda entita="PTFLUSSI" 
			plugin="it.eldasoft.sil.pt.tags.gestori.plugin.GestorePTFLUSSI"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePTFLUSSI">
			
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda addTr="false" >
					<tr id="erroreInvioFlusso" >
						<td colspan="2" class="valore-dato">
							<c:if test="${not empty requestScope.erroriValidazione}">
								<br><b>Errore validazione XML:</b><br>
								<c:forEach var="errore" items="${requestScope.erroriValidazione}" varStatus="stato" >
									<c:if test="${stato.first}" >
										<ul>
									</c:if>
									<c:out value="<li>${errore}</li>" escapeXml="false" />
									<c:if test="${stato.last}" >
										</ul>
									</c:if>
								</c:forEach>
							</c:if>
						</td>
					</tr>
				</gene:campoScheda>
			</gene:gruppoCampi>
			
			<gene:campoScheda campo="IDFLUSSO" title="${id_invio}" modificabile="false" visibile='${modoAperturaScheda ne "NUOVO"}'/>
			<gene:campoScheda campo="KEY01" defaultValue="${contri}" visibile="false" />
			<gene:campoScheda campo="CFSA" defaultValue="${cfein}" modificabile="false"/>
			<gene:campoScheda campo="TIPINV" title="${tipo_invio}" defaultValue="${tipinv}" modificabile="false"/>
			<jsp:useBean id="now" class="java.util.Date" scope="request" />
			<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="nowFormat" />
			<gene:campoScheda campo="DATINV" title="${data_invio}" defaultValue="${nowFormat}" modificabile="false" />
			<gene:campoScheda campo="AUTORE" title="${autore}" defaultValue="${sessionScope.profiloUtente.nome}" modificabile="false" />
			<gene:campoScheda campo="NOTEINVIO" />
			<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA"}' modificabile="false">
				<c:if test="${ctrlXml eq 'true'}">
					<a href='javascript:apriAllegato("xml");'><img src="${pageContext.request.contextPath}/img/exportXML.gif" /></a>
				</c:if>
				<c:if test="${ctrlXml eq 'false'}">
					<img src="${pageContext.request.contextPath}/img/exportXMLGrigio.gif" title="File non presente"/>
				</c:if>
			</gene:campoScheda>
			<gene:campoScheda title="File PDF" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA"}' modificabile="false">
				<c:if test="${ctrlPdf eq 'true'}">
					<a href='javascript:apriAllegato("pdf");'><img src="${pageContext.request.contextPath}/img/pdf.gif" /></a>
				</c:if>
				<c:if test="${ctrlPdf eq 'false'}">
					<img src="${pageContext.request.contextPath}/img/pdf_not_found.gif" title="File non presente"/>
				</c:if>
			</gene:campoScheda>
			<gene:campoScheda campo="TIPROGF" campoFittizio="true" definizione="T10" visibile="false" value='${tiprog}' />
			<gene:campoScheda campo="IDAMMINISTRAZIONE" defaultValue="${idammin}" campoFittizio="true" definizione="N7" visibile="false" />
			<gene:campoScheda campo="EMAIL" campoFittizio="true" definizione="T100" visibile="false" />
			<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
				<jsp:param name="entitaParent" value="PTFLUSSI" />
			</jsp:include>

			<gene:redefineInsert name="pulsanteSalva">
				<input type="button"  class="bottone-azione" value='Conferma' title='Conferma' onclick="javascript:ControllaDatiProgramma('${contri}');">
			</gene:redefineInsert>
			<gene:redefineInsert name="schedaConferma"/>

			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteModifica" />
			
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
			
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
		
	</gene:redefineInsert>

<gene:javaScript>

	function ControllaDatiProgramma(contri) {
		var comando = "href=/piani/commons/popup-validazione-xml.jsp?verificaFlussi=true&contri="+ contri + "&pubblica=${pubblica}";
		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}
	
	function apriAllegato(tipoFile) {
		var actionAllegato = "${pageContext.request.contextPath}/piani/VisualizzaAllegato.do";
		var par = new String('idflusso=' + getValue("PTFLUSSI_IDFLUSSO") + '&tab=flussi&tipo=' + tipoFile );
		document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
	}

</gene:javaScript>

</gene:template>