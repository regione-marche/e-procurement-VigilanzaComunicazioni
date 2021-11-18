<%
	/*
	 * Created on: 04/08/2009
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

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9OUTBOX-scheda">
	<gene:redefineInsert name="pulsanteNuovo" />
	<gene:redefineInsert name="schedaModifica" />
	<gene:redefineInsert name="pulsanteModifica" />
	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W9OUTBOX" gestisciProtezioni="true" >
			<gene:redefineInsert name="schedaNuovo" />
			<gene:redefineInsert name="pulsanteNuovo" />
				<gene:campoScheda campo="IDCOMUN" />
				<gene:campoScheda campo="NOMEIN" title="Denominazione stazione appaltante" entita="UFFINT" where="W9OUTBOX.CFSA=UFFINT.CFEIN" />
				<gene:campoScheda campo="KEY03" />
				<gene:campoScheda campo="AREA" />
				<c:choose>
					<c:when test='${datiRiga.W9OUTBOX_KEY03 eq 989}' >
						<gene:campoScheda campo="KEY01" title="ID avviso"/>
						<gene:campoScheda campo="ID" title="ID programma" entita="PIATRI" where="W9OUTBOX.KEY01=PIATRI.CONTRI" visibile="false" />
						<gene:campoScheda campo="IDAVGARA" title="ID gara" entita="W9GARA" where="W9OUTBOX.KEY01=W9GARA.CODGARA" visibile="false" />
						<gene:campoScheda campo="KEY04" visibile="false"/>
					</c:when>
					<c:when test='${datiRiga.W9OUTBOX_KEY03 eq 988}' >
						<gene:campoScheda campo="KEY01" visibile="false" />
						<gene:campoScheda campo="ID" title="ID programma" entita="PIATRI" where="W9OUTBOX.KEY01=PIATRI.CONTRI" visibile="false" />
						<gene:campoScheda campo="IDAVGARA" title="ID gara" entita="W9GARA" where="W9OUTBOX.KEY01=W9GARA.CODGARA" visibile="true"/>
						<gene:campoScheda campo="KEY04" visibile="false"/>
					</c:when>
					<c:when test='${datiRiga.W9OUTBOX_KEY03 eq 901}' >
						<gene:campoScheda campo="KEY01" visibile="false" />
						<gene:campoScheda campo="ID" title="ID programma" entita="PIATRI" where="W9OUTBOX.KEY01=PIATRI.CONTRI" visibile="false" />
						<gene:campoScheda campo="IDAVGARA" title="ID gara" entita="W9GARA" where="W9OUTBOX.KEY01=W9GARA.CODGARA" visibile="true"/>
						<gene:campoScheda campo="KEY04" title="Numero atto" />
					</c:when>
					<c:when test='${datiRiga.W9OUTBOX_KEY03 eq 982 or datiRiga.W9OUTBOX_KEY03 eq 981 or datiRiga.W9OUTBOX_KEY03 eq 991 or datiRiga.W9OUTBOX_KEY03 eq 992}' >
						<gene:campoScheda campo="KEY01" visibile="false" />
						<gene:campoScheda campo="ID" title="ID programma" entita="PIATRI" where="W9OUTBOX.KEY01=PIATRI.CONTRI" />
						<gene:campoScheda campo="IDAVGARA" title="ID gara" entita="W9GARA" where="W9OUTBOX.KEY01=W9GARA.CODGARA" visibile="false" />
						<gene:campoScheda campo="KEY04" visibile="false"/>
					</c:when>
					<c:otherwise>
						<gene:campoScheda campo="KEY01"/>
						<gene:campoScheda campo="ID" title="ID programma" entita="PIATRI" where="W9OUTBOX.KEY01=PIATRI.CONTRI" visibile="true" />
						<gene:campoScheda campo="IDAVGARA" title="ID gara" entita="W9GARA" where="W9OUTBOX.KEY01=W9GARA.CODGARA" visibile="true" />
						<gene:campoScheda campo="KEY04"/>
					</c:otherwise>
				</c:choose>

				<gene:campoScheda campo="DATINV" />
				<gene:campoScheda campo="STATO" />
				<gene:campoScheda title="Contenuto JSON" campoFittizio="true" visibile='${modoAperturaScheda eq "VISUALIZZA"}' modificabile="false">
					<c:if test="${datiRiga.W9OUTBOX_STATO ne 1}">
						<a href='javascript:apriAllegato();'><img
						src="${pageContext.request.contextPath}/img/exportXML.gif" /> </a>
					</c:if>
					
				</gene:campoScheda>
				<gene:campoScheda campo="MSG" />
				<gene:campoScheda campo="ID_RICEVUTO" />
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
	</gene:redefineInsert>
	<gene:javaScript>
	
		function apriAllegato() {
			var actionAllegato = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
			var par = new String('idcomun=' + getValue("W9OUTBOX_IDCOMUN") + '&tab=outbox');
			document.location.href=actionAllegato+"?" + csrfToken + "&" +par;
		}
		
	
	</gene:javaScript>
</gene:template>