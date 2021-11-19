<%
	/*
	 * Created on: 20/02/2012
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

<gene:template file="scheda-template.jsp" schema="W9" idMaschera="W9GARA-scheda">

	<gene:setString name="titoloMaschera" value='Richiesta creazione nuovo lotto di gara in SIMOG' />

	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
	</gene:redefineInsert>

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Richiesta creazione CIG" >

				<gene:formScheda entita="W9LOTT" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreRichiestaCIG" >
					<gene:gruppoCampi idProtezioni="GEN">
						<gene:campoScheda>
							<td colspan="2"><b>Dati generali del lotto</b></td>
						</gene:campoScheda>

						<gene:campoScheda campo="CODGARA" visibile="false" defaultValue="${param.codGara}"/>
						<gene:campoScheda campo="CODLOTT" visibile="false" />
						<gene:campoScheda title="Codice Gara ANAC a cui aggiungere il lotto" campo="IDAVGARA" campoFittizio="true" definizione="T20;0;;;" obbligatorio="true" defaultValue="${param.idAvGara}" />
						<gene:campoScheda campo="OGGETTO" obbligatorio="true" />
						<gene:campoScheda campo="SOMMA_URGENZA" obbligatorio="true" />
						
						<gene:campoScheda campo="IMPORTO_LOTTO" obbligatorio="true" />
						<gene:campoScheda campo="IMPORTO_ATTUAZIONE_SICUREZZA" />

						<gene:campoScheda campo="IMPORTO_TOT" modificabile="false">
							<gene:calcoloCampoScheda
								funzione='toMoney(sommaCampi(new Array("#W9LOTT_IMPORTO_LOTTO#","#W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA#")))'
								elencocampi="W9LOTT_IMPORTO_LOTTO;W9LOTT_IMPORTO_ATTUAZIONE_SICUREZZA" />
						</gene:campoScheda>
						
						<gene:campoScheda campo="CPV"
							href='javascript:formCPV(true)'
							modificabile="false" speciale="true" obbligatorio="true" >
							<c:if test='${true}'>
								<gene:popupCampo titolo="Dettaglio codice CODCPV"	href='formCPV(${true})' />
							</c:if>
							<gene:checkCampoScheda funzione="controlloCodiceCPV()" messaggio="Codice CPV non valido perch&egrave; troppo generico. Indicare un codice con ulteriori dettagli" onsubmit="true" obbligatorio="true"/>
						</gene:campoScheda>
						<gene:campoScheda campo="CPVDESC" title="Descrizione CPV"
							value="${cpvdescr}" campoFittizio="true" modificabile="false"
							definizione="T150" />
						
						<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" obbligatorio="true" />
						<gene:campoScheda campo="ID_CATEGORIA_PREVALENTE" obbligatorio="true" />
						<gene:campoScheda campo="TIPO_CONTRATTO" obbligatorio="true" />
						<gene:campoScheda campo="ART_E1" obbligatorio="true" />

						<!--gene-:-archivio titolo="Comuni" lista="gene/commons/istat-comuni-lista-popup.jsp" scheda="" schedaPopUp=""
							campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3" chiave=""	where="" formName="" inseribile="false">
							<gene-:-campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true"
								definizione="T2;0;Agx15;;S3COPROVIN" title="Provincia luogo di esecuzione del contratto"
								value="${descrProvinciaEsecuzione}" />
							<gene-:-campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true"
								definizione="T120;0;;;" title="Comune luogo di esecuzione del contratto"
								value="${denomComuneEsecuzione}" />
							<gene-:-campoScheda campo="LUOGO_ISTAT"
								title="Codice ISTAT del Comune luogo di esecuzione del contratto" />
						</--gene-:-archivio-->

						<gene:campoScheda campo="LUOGO_NUTS" href="#" speciale="true" obbligatorio="true" >
							<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
						</gene:campoScheda>

						<!--gene-:-campoScheda-->
							<!--td colspan="2"><b>Conferma inserimento SIMOG</b></td-->
						<!--/gene-:-campoScheda-->
						<!--gene-:-campoScheda title="Conferma inserimento SIMOG" campo="INSERT_SIMOG" campoFittizio="true" definizione="T1;0;;SN;" obbligatorio="false" modificabile="true" /-->
						<gene:campoScheda>
							<td colspan="2"><b>Credenziali di accesso SIMOG</b></td>
						</gene:campoScheda>
						<gene:campoScheda title="Login" campo="LOGIN" campoFittizio="true" definizione="T30;0;;;" obbligatorio="true" modificabile="true" />
						<gene:campoScheda title="Password" campo="PASSWORD" campoFittizio="true" definizione="T30;0;;;" modificabile="true" obbligatorio="true" />
					</gene:gruppoCampi>

					<gene:fnJavaScriptScheda
						funzione='controlloCodiceCPV()' elencocampi="W9LOTT_CPV" esegui="false" />

					<gene:campoScheda>
						<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
					</gene:campoScheda>
				</gene:formScheda>

			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>

	<gene:javaScript>

		document.forms[0].jspPathTo.value = document.forms[0].jspPath.value;

		function formCPV(modifica) {
			openPopUpCustom("href=commons/descriz-codice-cpv.jsp&key=" + document.forms[0].key.value + "&keyParent=" + document.forms[0].keyParent.value + "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")+"&campo=W9LOTT_CPV&campoRange=W9LOTT_MANOD&campoDescr=CPVDESC&valore="+ (getValue("W9LOTT_CPV") == "" ? "45000000-7" : getValue("W9LOTT_CPV"))+"&valoreDescr="+ (getValue("CPVDESC") == "" ? "Lavori di costruzione" : getValue("CPVDESC")), "formCPV", 700, 300, 1, 1);
		}

		function controlloCodiceCPV() {
			var codiceCPV = getValue("W9LOTT_CPV");
			if (codiceCPV != "") {
				if (codiceCPV.indexOf("00000-") > 0) {
					return false;
				} else {
					return true;
				}
			}
		}

		function sommaCampi(valori)	{
		  var i, ret=0;
		  for (i=0; i < valori.length; i++){
		    if (valori[i] != "")
		      ret += eval(valori[i]);
		  }
		  return eval(ret).toFixed(2);
		}
	
		function tornaHomePage() {
			document.location.href = "${pageContext.request.contextPath}/Home.do?" + csrfToken;
		}

	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);
		
		_creaFinestraAlberoNUTS();
		_creaLinkAlberoNUTS($("#W9LOTT_LUOGO_NUTS").parent(), "${modo}", $("#W9LOTT_LUOGO_NUTS"), $("#W9LOTT_LUOGO_NUTSview") );

		$("input[name^='W9LOTT_LUOGO_NUTS']").attr('readonly','readonly');
		$("input[name^='W9LOTT_LUOGO_NUTS']").attr('tabindex','-1');
		$("input[name^='W9LOTT_LUOGO_NUTS']").css('border-width','1px');
		$("input[name^='W9LOTT_LUOGO_NUTS']").css('background-color','#E0E0E0');	
		
	});

	</gene:javaScript>
</gene:template>
