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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:choose>
	<c:when test="${requestScope.inttriGiaInserite > 0}" >
		<script type="text/javascript">
			<c:choose>
			<c:when test="${requestScope.inttriGiaInserite eq 1}" >
				alert("Attenzione: proposta già inserita nel programma triennale");
			</c:when>
			<c:otherwise>
				alert("Attenzione: alcune proposte non sono state inserite perchè già presenti nel programma triennale");
			</c:otherwise>
			</c:choose>		
			window.opener.historyReload();
			window.close();
		</script>
	</c:when>
	<c:otherwise>
		
		<c:set var="idUtente" value="${sessionScope.profiloUtente.id}" />
		<c:set var="nomeUtente" value="${sessionScope.profiloUtente.nome}" />
		
		<c:choose>
			<c:when test='${sessionScope.profiloUtente.abilitazioneStd eq "A" or sessionScope.profiloUtente.abilitazioneStd eq "C" or !gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.APPLICA-FILTRO-UTENTE")}'>
				<c:set var="where1" value="PIATRI.CENINT = '${sessionScope.uffint}'" />
			</c:when>
			<c:otherwise>
				<c:set var="where1" value="PIATRI.SYSCON = ${sessionScope.profiloUtente.id} AND PIATRI.CENINT = '${sessionScope.uffint}'" />
			</c:otherwise>
		</c:choose>
		
		<c:set var="tiprog" value= "${param.tipint}" />
		<c:set var="ailintMin" value= "${param.ailintMin}" />
		<c:set var="ailintMax" value= "${param.ailintMax}" />
		
		<c:set var="where1" value= "${where1} AND PIATRI.NORMA = 2 AND PIATRI.TIPROG = ${tiprog}" />
		<c:choose>
			<c:when test='${tiprog eq 1}'>
				<c:set var="where1"  value= "${where1} and (PIATRI.ANNTRI <= ${ailintMin} AND PIATRI.ANNTRI >= (${ailintMax} - 2))" />
			</c:when>
			<c:otherwise>
				<c:set var="where1"  value= "${where1} and (PIATRI.ANNTRI <= ${ailintMin} AND PIATRI.ANNTRI >= (${ailintMax} - 1))" />
			</c:otherwise>
		</c:choose>
		<c:set var="where1" value= "${where1} and not exists(select 1 from W9FLUSSI where PIATRI.CONTRI=W9FLUSSI.KEY01 and W9FLUSSI.AREA = 4)" />
		
		<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="PIATRI-popup-inserisciInProgrammazione">
			<gene:setString name="titoloMaschera" value="Lista Programmi"/>		
			
			<gene:redefineInsert name="corpo">
				
				<table class="lista">
				<tr><td >
					<gene:formLista entita="PIATRI" where="${where1}" pagesize="20" tableclass="datilista" sortColumn="1" gestisciProtezioni="true" >
							
						<% // Campi veri e propri %>
						<gene:campoLista campo="CONTRI" visibile="false"/>
						<gene:campoLista campo="ANNTRI" headerClass="sortable"/>
						<gene:campoLista campo="ID" visibile="true" headerClass="sortable" width="100"  href="javascript:importaFabbisogni('${datiRiga.PIATRI_CONTRI}','${datiRiga.PIATRI_ANNTRI}')" tooltip="Inserisci in programmazione"/>			
						<gene:campoLista campo="BRETRI" headerClass="sortable"  />
						<gene:campoLista campo="TIPROG" headerClass="sortable"/>
							<gene:campoLista campo="SYSUTE" entita="USRSYS"  where="PIATRI.SYSCON=USRSYS.SYSCON" />
						<gene:campoLista campo="STATRI" visibile='${visualizzaStato}'/>
						<gene:campoLista campo="NORMA" visibile='true'/>
		
					</gene:formLista>
					
					<form name="formListaFabbisogni" id="formListaFabbisogni" method="post" action="piani/GetListaFabbisogni.do?numeroPopUp=1&tipoForm=3" >
						<input type="hidden" id="contri" name="contri" value="" />
						<input type="hidden" id="fabbisogniDaImportare" name="fabbisogniDaImportare" value="${param.fabbisogni}" />
						<input type="hidden" id="funzionalita" name="funzionalita" value="${param.funzionalita}" />
						<input type="hidden" id="tiprog" name="tiprog" value="${tiprog}" />
						<input type="hidden" id="anntri" name="anntri" value="" />
						<input type="hidden" id="idUtente" name="idUtente" value="${idUtente}" />
						<input type="hidden" id="nomeUtente" name="nomeUtente" value="${nomeUtente}" />
					</form>
					
				</td></tr>
				<tr>
					<gene:redefineInsert name="listaNuovo"/>
					<gene:redefineInsert name="listaEliminaSelezione" />
					<td class="comandi-dettaglio" colSpan="2">
						<INPUT type="button" class="bottone-azione" value="Annulla"	title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
						&nbsp;
					</td>
				</tr>
				</table>
			</gene:redefineInsert>
		  
			<gene:javaScript>
			
				function importaFabbisogni(valoreContri,valoreAnntri) {
					$("#contri").val(valoreContri);
					$("#anntri").val(valoreAnntri);
					
					document.formListaFabbisogni.submit();
				}
				
		
				function annulla() {
					window.close();
				}
				
				
			</gene:javaScript>
		  
		   
		</gene:template>

	</c:otherwise>
</c:choose>