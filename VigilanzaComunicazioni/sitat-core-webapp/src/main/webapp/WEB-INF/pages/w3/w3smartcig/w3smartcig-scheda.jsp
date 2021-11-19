
<%
	/* 
	 * Created on 04-Nov-2008
	 *
	 * Copyright (c) EldaSoft S.p.A.
	 * Tutti i diritti sono riservati.
	 *
	 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
	 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
	 * aver prima formalizzato un accordo specifico con EldaSoft.
	 */

	// Scheda degli intestatari della concessione stradale
%>

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${contextPath}/js/funzioniIDGARACIG.js"></script>

<gene:template file="scheda-template.jsp" gestisciProtezioni="true" idMaschera="W3SMARTCIG-Scheda" schema="W3">
	<c:set var="entita" value="W3SMARTCIG" />
	<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.w3.tags.funzioni.GetTitleFunction",pageContext,entita)}'/>

	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W3SMARTCIG" gestisciProtezioni="true" gestore="it.eldasoft.sil.w3.tags.gestori.submit.GestoreW3SMARTCIG" 
		 plugin="it.eldasoft.sil.w3.tags.gestori.plugin.GestoreW3SMARTCIG" >
	
			<c:set var="codrich" value='${gene:getValCampo(key,"CODRICH")}' scope="request" />
	
			<gene:campoScheda campo="CIG" modificabile="false"/>
			<gene:campoScheda title="Stato della gara" campo="STATO" modificabile="false" defaultValue="1" />
			<gene:campoScheda campo="DATA_OPERAZIONE" modificabile="false" visibile="${datiRiga.W3SMARTCIG_STATO ne '1'}"/>
			<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${sessionScope.uffint}" />
			
			<gene:campoScheda>
				<td colspan="2"><b><br>SEZIONE I: RUP e COLLABORAZIONE (AMMINISTRAZIONE/STAZIONE APPALTANTE)<br><br></b></td>
			</gene:campoScheda>
			<gene:campoScheda>
				<td colspan="2"><b>I.1) Responsabile unico del procedimento</b></td>
			</gene:campoScheda>
			<gene:archivio titolo="Tecnici"
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W3.W3SMARTCIG.RUP"),"gene/tecni/tecni-lista-popup.jsp","")}'
				scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
				schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
				campi="TECNI.CODTEC;TECNI.NOMTEC;TECNI.CFTEC" chiave="W3SMARTCIG_RUP" >
				<gene:campoScheda campo="RUP" visibile="false" defaultValue="${rup}"/>
				<gene:campoScheda title="Denominazione" entita="TECNI" campo="NOMTEC" where="TECNI.CODTEC=W3SMARTCIG.RUP" defaultValue="${nomtec}"/>
				<gene:campoScheda title="Codice fiscale" entita="TECNI" campo="CFTEC" defaultValue="${cftec}"/>
			</gene:archivio>
	
			<gene:campoScheda>
				<td colspan="2"><b><br>I.2) Collaborazione associata al RUP</b></td>
			</gene:campoScheda>
			<gene:archivio titolo="Collaborazioni"
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W3.W3SMARTCIG.COLLABORAZIONI"),"w3/w3aziendaufficio/w3aziendaufficio-lista-popup.jsp","")}'
				scheda=""
				where="w3aziendaufficio.azienda_cf = '${cfein}' and w3aziendaufficio.id in (select w3usrsyscoll.w3aziendaufficio_id from w3usrsyscoll, w3usrsys where w3usrsyscoll.syscon = w3usrsys.syscon and w3usrsyscoll.rup_codtec = '${datiRiga.W3SMARTCIG_RUP}' and w3usrsyscoll.syscon = ${sessionScope.profiloUtente.id})"
				schedaPopUp="w3/w3aziendaufficio/w3aziendaufficio-scheda-popup.jsp"
				campi="W3AZIENDAUFFICIO.ID;W3AZIENDAUFFICIO.UFFICIO_DENOM;W3AZIENDAUFFICIO.AZIENDA_CF;W3AZIENDAUFFICIO.AZIENDA_DENOM;W3AZIENDAUFFICIO.INDEXCOLL;" chiave="W3GARA_COLLABORAZIONE" >
				<gene:campoScheda campo="COLLABORAZIONE" visibile="false" defaultValue="${collaborazione}"/>
				<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="UFFICIO_DENOM" where="W3AZIENDAUFFICIO.ID=W3SMARTCIG.COLLABORAZIONE" defaultValue="${ufficio_denom}"/>
				<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="AZIENDA_CF" defaultValue="${azienda_cf}"/>
				<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="AZIENDA_DENOM" defaultValue="${azienda_denom}"/>
				<gene:campoScheda entita="W3AZIENDAUFFICIO" campo="INDEXCOLL" defaultValue="${indexcoll}"/>
			</gene:archivio>
	
			<gene:campoScheda>
				<td colspan="2"><b><br>SEZIONE II: RICHIESTA CIG</b><br><br></td>
			</gene:campoScheda>
			<gene:campoScheda>
				<td colspan="2"><b>II.1) Dati generali</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CODRICH" visibile="false"/>
			<gene:campoScheda campo="OGGETTO" obbligatorio="true"/>
			<gene:campoScheda campo="FATTISPECIE" obbligatorio="true"/>
			<gene:campoScheda campo="IMPORTO" obbligatorio="true"/>
			<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" obbligatorio="true"/>
			<gene:campoScheda campo="TIPO_CONTRATTO" obbligatorio="true"/>
			
			<gene:campoScheda>
				<td colspan="2"><b><br>II.2) Riferimento ad un accordo quadro</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CIG_ACC_QUADRO" />
			<gene:campoScheda>
				<td colspan="2"><b><br>II.3) CUP</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="CUP" />
			<gene:campoScheda>
				<td colspan="2"><b><br>II.4) Motivazioni per la richiesta CIG e categorie merceologiche</b></td>
			</gene:campoScheda>	
			<gene:campoScheda campo="M_RICH_CIG"/>
			<gene:campoScheda campo="M_RICH_CIG_COMUNI" />
			<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3SMARTCIGMERCFunction" parametro="${codrich}" />
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W3SMARTCIGMERC'/>
				<jsp:param name="chiave" value='${codrich}'/>
				<jsp:param name="nomeAttributoLista" value='datiW3SMARTCIGMERC' />
				<jsp:param name="idProtezioni" value="W3SMARTCIGMERC" />
				<jsp:param name="sezioneListaVuota" value="true" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3smartcigmerc/w3smartcigmerc-interno-scheda.jsp"/>
				<jsp:param name="arrayCampi" value="'W3SMARTCIGMERC_CODRICH_','W3SMARTCIGMERC_NUMMERC_','W3SMARTCIGMERC_CATEGORIA_'"/>
				<jsp:param name="titoloSezione" value="<br>Categoria merceologica n. " />
				<jsp:param name="titoloNuovaSezione" value="<br>Nuova categoria merceologica" />
				<jsp:param name="descEntitaVociLink" value="categoria merceologica" />
				<jsp:param name="msgRaggiuntoMax" value="e categorie merceologiche"/>
				<jsp:param name="usaContatoreLista" value="true"/>
				<jsp:param name="numMaxDettagliInseribili" value="5"/>
				<jsp:param name="sezioneInseribile" value="true"/>
				<jsp:param name="sezioneEliminabile" value="true"/>
			</jsp:include>
	
			<gene:redefineInsert name="pulsanteNuovo" />
			<gene:redefineInsert name="schedaNuovo" />
	
			<c:if test="${datiRiga.W3SMARTCIG_STATO eq '99'}">
				<gene:redefineInsert name="pulsanteModifica" />
				<gene:redefineInsert name="schedaModifica" />
			</c:if>
		
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
			
			<gene:fnJavaScriptScheda funzione="gestioneRUP_CODTEC('#W3SMARTCIG_RUP#')" elencocampi="W3SMARTCIG_RUP" esegui="false" />
			
		</gene:formScheda>
		
		<gene:redefineInsert name="addToDocumenti" >
			<jsp:include page="/WEB-INF/pages/w3/commons/addtodocumenti-idgaracig.jsp" >
				<jsp:param name="entita" value='W3SMARTCIG'/>
				<jsp:param name="stato" value='${datiRiga.W3SMARTCIG_STATO}'/>
			</jsp:include>
		</gene:redefineInsert>

		<gene:javaScript>
			function modifySelectOption(obj) {
				var obj_opt = obj.find("option");
				$.each(obj_opt, function( key, opt ) {
				var _title = opt.title;
				if (_title.length > 110) {
				 	_title = _title.substring(0,110) + "...";
				}
				opt.text = _title;
				});
				obj.css("width","550px");	
			}
			
			function gestioneRUP_CODTEC(value) {
	
				if (isValueChanged("W3SMARTCIG_RUP")) {
					document.forms[0].W3SMARTCIG_COLLABORAZIONE.value = '';
					document.forms[0].W3AZIENDAUFFICIO_UFFICIO_DENOM.value = '';
					document.forms[0].W3AZIENDAUFFICIO_AZIENDA_CF.value = '';
					document.forms[0].W3AZIENDAUFFICIO_AZIENDA_DENOM.value = '';
					document.forms[0].W3AZIENDAUFFICIO_INDEXCOLL.value = '';
				}
		
				var _input = $("input[value='w3/w3aziendaufficio/w3aziendaufficio-lista-popup.jsp']");
				var _sql = "w3aziendaufficio.azienda_cf = '${cfein}' and w3aziendaufficio.id in (select w3usrsyscoll.w3aziendaufficio_id from w3usrsyscoll, w3usrsys where w3usrsyscoll.syscon = w3usrsys.syscon and w3usrsyscoll.rup_codtec = '" + value + "' and w3usrsyscoll.syscon = ${sessionScope.profiloUtente.id})";
				_input.parent().find("input[name='archWhereLista']").val(_sql);

			}
	
		</gene:javaScript>
	</gene:redefineInsert>

</gene:template>
