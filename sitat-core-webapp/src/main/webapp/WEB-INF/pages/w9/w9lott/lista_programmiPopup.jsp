<%/*
       * Created on 01-Ott-2009
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
<c:when test='${!empty esitoInserimento && esitoInserimento eq "ok"}'>
	<% //parte successiva all'inserimento, permette di aggiornare la pagina chiamante  %>
	<script type="text/javascript">
		function chiudiFinestra()
		{
			window.opener.historyReload();
			window.close();
		}
	</script>
	<!-- la precedente funzione javascript viene lanciata al caricamento della pagina -->
	<body onload="javascript:chiudiFinestra()"> 
	</body>
</c:when>
<c:otherwise>
<c:set var="wher" value="INTTRI.CONTRI IN (SELECT CONTRI FROM PIATRI WHERE PIATRI.CENINT = '${sessionScope.uffint}')" />
<c:set var="keyLott" value="${param.keyLott}" scope="request"/>
<c:set var="tmp" value=""></c:set>
<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera"	value="Lista Interventi" />
	<gene:redefineInsert name="gestioneHistory" ></gene:redefineInsert>
	<gene:redefineInsert name="corpo">
	<table class="lista">
	<tr>
		<td>
  		<gene:formLista entita="INTTRI" pagesize="10" tableclass="datilista" sortColumn="4" gestisciProtezioni="false"
  			where="${wher}" >
  			<gene:campoLista campo="CONINT" visibile="false" />
  			<gene:campoLista campo="CONTRI" visibile="false" />
			<gene:campoLista title="Opzioni" width="50">
				<c:if test="${currentRow >= 0}" >
				<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
					<c:choose>
						<c:when test='${param.keyLott eq ""}'>
							<c:set var="tmp" value="'${datiRiga.INTTRI_DESINT}', '${datiRiga.INTTRI_TOTINT}', '${datiRiga.INTTRI_CODCPV}', '${datiRiga.TABCPV_CPVDESC}', '${datiRiga.INTTRI_MANTRI}', '${datiRiga.INTTRI_COMINT}', '${datiRiga.INTTRI_NUTS}', '${datiRiga.PIATRI_TIPROG}', '${datiRiga.INTTRI_TIPOIN}'" />
							<gene:PopUpItem title="Seleziona" href="javascript:selezionaProgramma(${tmp});"/>
						</c:when>
						<c:otherwise>
							<gene:PopUpItem title="Seleziona" href="javascript:associaProgramma('${chiaveRigaJava}');"/>
						</c:otherwise>
					</c:choose>
				</gene:PopUp>
				</c:if>
			</gene:campoLista>
			<gene:campoLista campo="TIPROG" entita="PIATRI" where="INTTRI.CONTRI = PIATRI.CONTRI" headerClass="sortable" />
			<c:choose>
				<c:when test='${param.keyLott eq ""}'>
					<gene:campoLista campo="ANNTRI" href="javascript:selezionaProgramma(${tmp});" entita="PIATRI" where="INTTRI.CONTRI = PIATRI.CONTRI" headerClass="sortable"/>
				</c:when>
				<c:otherwise>
					<gene:campoLista campo="ANNTRI" href="javascript:associaProgramma('${chiaveRigaJava}');" entita="PIATRI" where="INTTRI.CONTRI = PIATRI.CONTRI" headerClass="sortable"/>
				</c:otherwise>
			</c:choose>
			<gene:campoLista campo="ANNRIF" headerClass="sortable"/>
			<gene:campoLista campo="DESINT" headerClass="sortable"/>
			<gene:campoLista campo="TOTINT" headerClass="sortable"/>
			<gene:campoLista campo="CODCPV" />
			<gene:campoLista campo="CPVDESC" entita="TABCPV" where="TABCPV.CPVCOD4 = INTTRI.CODCPV" visibile="false" />
			<gene:campoLista campo="MANTRI" visibile="false" />
			<gene:campoLista campo="COMINT" visibile="false" />
			<gene:campoLista campo="NUTS" visibile="false" />
			<gene:campoLista campo="TIPOIN" visibile="false" />
			<gene:campoLista campo="ID" visibile="false" entita="PIATRI" where="INTTRI.CONTRI = PIATRI.CONTRI" />
			<input type="hidden" name="keyLott" value="${keyLott}" />
		</gene:formLista>
		</td>
	</tr>
	<tr class="comandi-dettaglio">
		<td align="right">
			<INPUT type="button" class="bottone-azione" value="Nuova Ricerca" title="Nuova Ricerca" onclick="javascript:historyVaiA(0);"/>&nbsp;
		</td>
	</tr>
	</table>
	</gene:redefineInsert>
	<gene:javaScript>
	
	function associaProgramma(chiavePrg) {
		if(confirm('Copiare i dati del programma selezionato' + '?')) {
			var actionCopiaValori = "${pageContext.request.contextPath}/w9/CopiaValoriProgramma.do";
			var par = new String('keyLott=${keyLott}&keyProgr='+chiavePrg+'&cenint=${sessionScope.uffint}&numeroPopUp=1');
			location.href=actionCopiaValori+'?' + csrfToken + '&' +par;
		}
	}
	
	function selezionaProgramma(desint, totint, codcpv, codcpvdescr, mantri, comint, nuts, tiprog, tipoin){
		var importoLotto = 0;
		var importoSicurezza = 0;
		var totale = 0;
		if(confirm('Copiare i dati del programma selezionato' + '?')) {
			if(totint != '')
			{
				importoLotto = parseInt(totint);
			}
			if(window.opener.document.getElementById("W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA").value != '')
			{
				importoSicurezza = parseInt(window.opener.document.getElementById("W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA").value);
			}
			totale = importoLotto + importoSicurezza;
			window.opener.document.getElementById("W9LOTT_OGGETTO").value = desint;
			window.opener.document.getElementById("W9LOTT_IMPORTO_LOTTO").value = importoLotto;
			window.opener.document.getElementById("W9LOTT_IMPDISP").value = '1';
			window.opener.setValue("W9LOTT_CPV", codcpv);
			window.opener.setValue("CPVDESC", codcpvdescr);
			window.opener.document.getElementById("W9LOTT_MANOD").value = mantri;
			window.opener.setValue("W9LOTT_IMPORTO_TOT", totale);
			window.opener.setValue("W9LOTT_TIPO_CONTRATTO", tipoin);
			if(tiprog==1)
			{
				window.opener.document.getElementById("W9LOTT_LUOGO_ISTAT").value = comint;
				window.opener.document.getElementById("W9LOTT_LUOGO_NUTS").value = nuts;
			}
			
			window.close();
		}
	}
	</gene:javaScript>
</gene:template>
</c:otherwise>
</c:choose>