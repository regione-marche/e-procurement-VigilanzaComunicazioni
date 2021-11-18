<%
	/*
	 * Created on: 19/10/2009
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<gene:template file="scheda-template.jsp" gestisciProtezioni="true"	schema="W9" idMaschera="W9FLUSSI-scheda">

	<gene:setString name="titoloMaschera" value='Invio ${idflusso}' />
	
	<gene:redefineInsert name="schedaModifica" />
	<gene:redefineInsert name="pulsanteModifica" />
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />

	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9FLUSSI_ELIMINATI" gestisciProtezioni="true">
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda campo="IDFLUSSO" modificabile="false" />
				<gene:campoScheda campo="AREA"  visibile="false"/>
				<gene:campoScheda campo="KEY01" visibile="false" />
				<gene:campoScheda campo="KEY02" visibile='${(datiRiga.W9FLUSSI_ELIMINATI_AREA eq "1")}' />
				<gene:campoScheda campo="KEY03" visibile='${(datiRiga.W9FLUSSI_ELIMINATI_AREA eq "1") || (datiRiga.W9FLUSSI_ELIMINATI_AREA eq "2")}' />
				<gene:campoScheda campo="KEY04" visibile='${datiRiga.W9FLUSSI_ELIMINATI_AREA eq "1"}' />
				<gene:campoScheda campo="TINVIO2"/>
				<gene:campoScheda campo="DATINV"/>
				<gene:campoScheda campo="DATCANC"/>
				<gene:campoScheda campo="NOTEINVIO" />
				<gene:campoScheda campo="MOTIVOCANC" />
				<gene:campoScheda campo="AUTORE" />
				<gene:campoScheda campo="XML" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreLunghezzaCampo" visibile="false" />
		<c:choose>
			<c:when test='${datiRiga.W9FLUSSI_ELIMINATI_XML eq 1}' >
				<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA" and gene:checkProt( pageContext,"FUNZ.VIS.ALT.W9.W9FLUSSI_XML")}' modificabile="false">
					<a href='javascript:apriAllegato();'>
						<img src="${pageContext.request.contextPath}/img/exportXML.gif" />
					</a>
				</gene:campoScheda>
			</c:when>
			<c:otherwise>
				<gene:campoScheda title="File XML" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA" and gene:checkProt( pageContext,"FUNZ.VIS.ALT.W9.W9FLUSSI_XML")}' modificabile="false">
					&nbsp;
				</gene:campoScheda>
			</c:otherwise>
		</c:choose>
				<gene:campoScheda campo="VERXML" modificabile="false" defaultValue="${requestScope.versioneTracciatoXML}" />
			</gene:gruppoCampi>
			<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W9.INVIISIMOG")}'>
				<gene:campoScheda campo="SIMOGUSER" campoFittizio="true" visibile="false" definizione="T100"/>
				<gene:campoScheda campo="SIMOGPASS" campoFittizio="true" visibile="false" definizione="T100"/>
				<gene:campoScheda campo="RECUPERAUSER" campoFittizio="true" visibile="false" definizione="T1"/>
				<gene:campoScheda campo="RECUPERAPASS" campoFittizio="true" visibile="false" definizione="T1"/>
				<gene:campoScheda campo="MEMORIZZA" campoFittizio="true" visibile="false" definizione="T5"/>
				<gene:campoScheda entita="W9XML" campo="NUM_ERRORE" where="W9FLUSSI_ELIMINATI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="NUM_WARNING" where="W9FLUSSI_ELIMINATI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="CODGARA" where="W9FLUSSI_ELIMINATI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="CODLOTT" where="W9FLUSSI_ELIMINATI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<gene:campoScheda entita="W9XML" campo="NUMXML" where="W9FLUSSI_ELIMINATI.IDFLUSSO = W9XML.IDFLUSSO" visibile="false"/>
				<c:if test='${!empty datiRiga.W9XML_NUM_ERRORE}'>
					<gene:campoScheda>
						<td colspan="2"><b>Elaborazione eseguita dal SIMOG</b></td>
					</gene:campoScheda>
					<gene:campoScheda campo="NUMERO_ERRORI" title="Numero errori riscontrati" campoFittizio="true" value="${datiRiga.W9XML_NUM_ERRORE}" definizione="N5;0"/>
					<gene:campoScheda campo="NUMERO_AVVISI" title="Numero avvisi" campoFittizio="true" value="${datiRiga.W9XML_NUM_WARNING}" definizione="N5;0"/>
					<gene:campoScheda>
						<td colspan="2"><b>Esito dell'elaborazione</b></td>
					</gene:campoScheda>
					<gene:campoScheda>
					<c:set var="codgara" value="${datiRiga.W9XML_CODGARA}" />
					<c:set var="codlott" value="${datiRiga.W9XML_CODLOTT}" />
					<c:set var="numxml" value="${datiRiga.W9XML_NUMXML}" />
					<gene:sqlSelectList nome="errori_avvisi" tipoOut="VectorString">
						select LIVELLO, CODICE, DESCRIZIONE, SCHEDA from W9XMLANOM where W9XMLANOM.CODGARA = ${codgara} AND W9XMLANOM.CODLOTT = ${codlott} AND W9XMLANOM.NUMXML = ${numxml}
					</gene:sqlSelectList>
					<table class="datilista">
						<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Codice anomalia</th>
							<th>Descrizione anomalia</th>
							<th>Tipo scheda</th>
						</tr>
						</thead>
					<c:forEach items="${errori_avvisi}" var="avviso" varStatus="stato">
						<c:if test="${stato.index%2 == 0}">
							<tr class="even">
						</c:if>
						<c:if test="${stato.index%2 == 1}">
							<tr class="odd">
						</c:if>
						<td>
							<c:choose>
								<c:when test="${avviso[0] == 'ERRORE'}">
									<img width="20" height="20" title="Errore" alt="Errore" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
								</c:when>
								<c:when test="${avviso[0] == 'ATTENZIONE'}">
									<img width="20" height="20" title="Attenzione" alt="Attenzione" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
								</c:when>
								<c:when test="${avviso[0] == 'AVVISO'}">
									<img width="20" height="20" title="Avviso" alt="Avviso" src="${pageContext.request.contextPath}/img/controllo_i.gif"/>	
								</c:when>
							</c:choose>
						</td>
						<td>${avviso[1]}</td>
						<td>${avviso[2]}</td>
						<td>${avviso[3]}</td>
						</tr>
					</c:forEach>
					
					</table>
					</gene:campoScheda>
				</c:if>
			</c:if>
			<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
				<jsp:param name="entitaParent" value="W9FLUSSI_ELIMINATI" />
			</jsp:include>

			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>

		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('idflusso=' + getValue("W9FLUSSI_ELIMINATI_IDFLUSSO") + '&tab=flussi_eliminati');
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
		
	</gene:javaScript>
</gene:template>
