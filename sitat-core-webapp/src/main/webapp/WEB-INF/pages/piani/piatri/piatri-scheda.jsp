
<%
  /*
   * Created on 18-ott-2007
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

<!-- // Rimozione dalla sessione il filtro della lista interventi -->
<c:if test='${requestScope.activePage ne 2 and sessionScope.filtroINTTRI eq 1}' >
	<c:remove var="filtroINTTRI" scope="session" />
	<c:remove var="trovaINTTRI" scope="session" />
</c:if>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PIATRI-scheda">
	
	<c:set var="key" value='PIATRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")}' scope="request" />

	<c:choose>
		<c:when test='${modo eq "NUOVO"}'>
			<c:set var="tiprog" value='${param.tiprog}' scope="request" />
			<c:set var="pubblica" value='${param.pubblica}' scope="request" />
			<c:set var="norma" value='2' scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="tiprog"	value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTiprogFunction", pageContext, key)}' scope="request"/>
			<c:set var="interventoVSacquisto" value="${tiprog eq '2' ? 'acquisto' : 'intervento'}" scope="request"/>
			<c:set var="pubblica" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetPubblicaFunction", pageContext, key)}' scope="request"/>
			<c:set var="norma" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNormaFunction", pageContext, key)}' scope="request"/>
			<c:set var="modificabile" value='${gene:callFunction3("it.eldasoft.w9.tags.funzioni.IsEliminabileFunction",pageContext,key,"PIATRI")}' scope="request"/>
			
				<c:set var="key1" value='${gene:getValCampo(key,"CONTRI")}'	scope="request" />
				<c:set var="key2" value='' scope="request" />
				<c:set var="key3" value='' scope="request" />
				<c:choose>
					<c:when test="${norma eq 1 or empty norma}" >
						<c:if test='${tiprog eq "1"}'>
							<c:set var="flusso" value='992' scope="request" />
						</c:if>
						<c:if test='${tiprog eq "2"}'>
							<c:set var="flusso" value='991' scope="request" />
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test='${tiprog eq "1"}'>
							<c:set var="flusso" value='982' scope="request" />
						</c:if>
						<c:if test='${tiprog eq "2"}'>
							<c:set var="flusso" value='981' scope="request" />
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:if test='${tiprog eq "3"}'>
					<c:set var="flusso" value='982' scope="request" />
				</c:if>
		</c:otherwise>
	</c:choose>

	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"PIATRI")}' />

	<gene:redefineInsert name="addToDocumenti">
		<jsp:include page="../../w9/commons/addtodocumenti-validazione.jsp" />
	</gene:redefineInsert>

	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">

			<c:choose>
				<c:when test="${norma eq 1 or empty norma}" >
					<gene:pagina title="Dati Generali" idProtezioni="DATIGEN">
						<jsp:include page="piatri-pg-datigen.jsp" />
					</gene:pagina>
					<c:if test='${tiprog ne "2"}'>
						<gene:pagina title="Interventi" idProtezioni="INTERVENTI">
							<jsp:include page="piatri-pg-lista-interventi.jsp"/>
						</gene:pagina>
						<gene:pagina title="Elenco lavori in economia" idProtezioni="LAVORIECONOMIA">
							<jsp:include page="piatri-pg-lista-lavori-economia.jsp" />
						</gene:pagina>	
					</c:if>
					<c:if test='${tiprog ne "1"}'>
						<gene:pagina title="Forniture e servizi" idProtezioni="SERVIZI">
							<jsp:include page="piatri-pg-lista-forniture-servizi.jsp"/>
						</gene:pagina>
					</c:if>
					<gene:pagina title="Note" idProtezioni="NOTESCHEDE">
						<jsp:include page="piatri-pg-note.jsp" />
					</gene:pagina>
					<gene:pagina title="Riepilogo" idProtezioni="RIEPILOGO">
						<jsp:include page="piatri-pg-riepilogo.jsp" />
					</gene:pagina>
					<c:set var="title_invii" value="Pubblica"/>
					<c:if test='${pubblica ne "1"}'>
						<c:set var="title_invii" value="Invii"/>
					</c:if>
					<gene:pagina title="${title_invii}" idProtezioni="FLUSSIPROGRAMMI">
						<jsp:include page="piatri-pg-lista-flussi.jsp" />
					</gene:pagina>
				</c:when>
				<c:otherwise>
					<gene:pagina title="Dati Generali" idProtezioni="DATIGEN">
						<jsp:include page="piatri-pg-datigen-norma.jsp" />
					</gene:pagina>
					<c:if test='${tiprog eq "1"}'>
					<gene:pagina title="Opere incompiute" idProtezioni="OPEREINCOMPIUTE">
						<jsp:include page="piatri-pg-lista-opere-incompiute.jsp" />
					</gene:pagina> 
					</c:if>
					<gene:pagina title='${tiprog eq "2" ? "Acquisti" : "Interventi"}' idProtezioni="INTERVENTI">
						<jsp:include page="piatri-pg-lista-interventi-norma.jsp"/>
					</gene:pagina>
					
					<gene:pagina title='${tiprog eq "2" ? "Acquisti non riproposti" : "Interventi non riproposti"}' idProtezioni="INTERVENTINONRIPROPOSTI">
						<c:if test="${param.updateLista eq 1}" >
							<c:set var="isNavigazioneDisattiva" value="1" scope="request" />
							<c:set var="isNavigazioneDisabilitata" value="1" scope="request" />
						</c:if>
						<jsp:include page="piatri-pg-lista-interventi-non-riproposti.jsp"/>
					</gene:pagina>
					<gene:pagina title="Riepilogo" idProtezioni="RIEPILOGO">
						<jsp:include page="piatri-pg-riepilogo-norma.jsp" />
					</gene:pagina>
					<c:set var="title_invii" value="Pubblica"/>
					<c:if test='${pubblica ne "1"}'>
						<c:set var="title_invii" value="Invii"/>
					</c:if>
					<gene:pagina title="${title_invii}" idProtezioni="FLUSSIPROGRAMMI">
						<jsp:include page="piatri-pg-lista-flussi.jsp" />
					</gene:pagina>		
				</c:otherwise>
			</c:choose>
			
		</gene:formPagine>
	</gene:redefineInsert>

	<gene:javaScript>
	
		function popupValidazione(flusso,key1,key2,key3) {
			var comando = "href=w9/commons/popup-validazione-generale.jsp";
			comando = comando + "&flusso=" + flusso;
			comando = comando + "&key1=" + key1;
			comando = comando + "&key2=" + key2;
			comando = comando + "&key3=" + key3;
			openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
		}
	
	</gene:javaScript>
</gene:template>