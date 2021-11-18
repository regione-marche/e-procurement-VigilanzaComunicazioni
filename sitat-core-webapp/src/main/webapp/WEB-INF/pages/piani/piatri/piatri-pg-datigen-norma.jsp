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

<c:set var="createXML" value='${gene:checkProt(pageContext,"FUNZ.VIS.ALT.PIANI.CREATE-XML")}'/>
<c:set var="sceltaUfficioAttiva" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.UfficioSAAttivoFunction",pageContext)}' />
<gene:formScheda entita="PIATRI" gestisciProtezioni="true" gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestorePIATRI">
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="ID" modificabile="false"	visibile="${modoAperturaScheda ne 'NUOVO'}" />
		<gene:campoScheda campo="CONTRI" visibile="false" />
		
		<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
			campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="PIATRI_CENINT" where=""	formName="formStazApp">
			<c:if test='${modo eq "NUOVO"}'>
				<gene:campoScheda campo="CENINT" visibile="false" value="${sessionScope.uffint}" />
			</c:if>
			<c:if test='${modo ne "NUOVO"}'>
				<gene:campoScheda campo="CENINT" visibile="false" value="${datiRiga.PIATRI_CENINT}" />
			</c:if>
			<gene:campoScheda campo="NOMEIN" entita="UFFINT"  where="UFFINT.CODEIN = PIATRI.CENINT" modificabile="false" visibile="${modoAperturaScheda ne 'NUOVO'}" title="Stazione appaltante" />
		</gene:archivio>
		
		<c:if test="${gene:checkProt(pageContext, 'COLS.VIS.PIANI.PIATRI.IDUFFICIO') and sceltaUfficioAttiva eq '1'}">
			<c:choose>
				<c:when test='${!empty ufficioAttivo}'>
					<c:if test='${modo eq "NUOVO"}'>
						<gene:campoScheda campo="IDUFFICIO" visibile="false" defaultValue="${ufficioAttivo}"/>
						<gene:campoScheda campo="DENOM" entita="UFFICI" defaultValue="${denominazioneUfficioAttivo}"
							where="UFFICI.ID=PIATRI.IDUFFICIO" modificabile='false'
							title="Ufficio/Centro di costo" definizione="T250" />
					</c:if>
					<c:if test='${modo ne "NUOVO"}'>
						<gene:campoScheda campo="IDUFFICIO" visibile="false" value="${ufficioAttivo}"/>
						<gene:campoScheda campo="DENOM" entita="UFFICI"
							where="UFFICI.ID=PIATRI.IDUFFICIO" modificabile='false'
							title="Ufficio/Centro di costo" definizione="T250" />
					</c:if>
				</c:when>
				<c:otherwise>
					<gene:archivio titolo="Ufficio/Centro di costo"
						lista='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.ListaUffici"),"w9/uffici/uffici-lista-popup.jsp","")}'
						scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda.jsp","")}'
						schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda-popup.jsp","")}'
						where="UFFICI.CODEIN = '${sessionScope.uffint}' and UFFICI.CCPROG='1'"
						campi="UFFICI.ID;UFFICI.DENOM"
						chiave="PIATRI_IDUFFICIO">
						<gene:campoScheda campo="IDUFFICIO" visibile="false" />
						<gene:campoScheda campo="DENOM" entita="UFFICI"
							where="UFFICI.ID=PIATRI.IDUFFICIO" modificabile='${gene:checkProt(pageContext, "COLS.MOD.PIANI.PIATRI.IDUFFICIO")}'
							title="Ufficio/area di pertinenza" definizione="T250" />
					</gene:archivio>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		
		<gene:campoScheda campo="NORMA" visibile="false" defaultValue="2" />
		
		<gene:campoScheda campo="TIPROG" defaultValue='${gene:if((not empty tiprog ),tiprog,3)}'
					modificabile="false" title="Tipo programma" obbligatorio="true"/>
		<gene:campoScheda campo="BRETRI"/>
		<gene:campoScheda campo="ANNTRI" visibile="true" obbligatorio="true" modificabile='${modo eq "NUOVO"}'>
			<gene:checkCampoScheda funzione="controlloAnno()"  
				messaggio="L'anno inserito non è corretto" obbligatorio="true" onsubmit="false" />
		</gene:campoScheda>
		<gene:campoScheda campo="PUBBLICA" defaultValue="1" modificabile="${modificabile eq 'si'}"/>
		<gene:campoScheda campo="STATRI" modificabile="false" defaultValue="1" />
		
		<gene:archivio titolo="TECNICI"
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.PIANI.PIATRI.RESPRO"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="PIATRI_RESPRO" >
			<gene:campoScheda campo="RESPRO" title="Codice del referente del programma" visibile="false"/>
			<gene:campoScheda campo="NOMTEC" entita="TECNI" where="TECNI.CODTEC=PIATRI.RESPRO"
				title="Nome del referente del programma" definizione="T61" />
		</gene:archivio>

		<c:if test='${tiprog eq "1"}'>
			<gene:gruppoCampi idProtezioni="ADOZ">
				<gene:campoScheda>
					<td colspan="2"><b>Adozione</b></td>
				</gene:campoScheda>		
				<gene:campoScheda campo="TADOZI" />
				<gene:campoScheda campo="NADOZI" />
				<gene:campoScheda campo="DPADOZI" />
				<gene:campoScheda campo="DADOZI" />
				<gene:campoScheda campo="TITADOZI" />
				<gene:campoScheda campo="URLADOZI" href="${datiRiga.PIATRI_URLADOZI}" >
					<gene:checkCampoScheda funzione="controlloUrlAdozi()" 
						messaggio="il campo URL non è valido. Deve iniziare con uno dei seguenti prefissi: http://, https://, ftp://, ftps://. Ad esempio: https://www.amministrazione.it/gara01/bando.pdf" obbligatorio="true" onsubmit="true" />
				</gene:campoScheda>
			</gene:gruppoCampi>
		</c:if>
		
		<gene:gruppoCampi idProtezioni="APPROV">
			<gene:campoScheda>
				<td colspan="2"><b>Approvazione</b></td>
			</gene:campoScheda>
			<gene:campoScheda campo="TAPTRI" />
			<gene:campoScheda campo="NAPTRI" />
			<gene:campoScheda campo="DPAPPROV" />
			<gene:campoScheda campo="DAPTRI" />
			<gene:campoScheda campo="TITAPPROV" />
			<gene:campoScheda campo="URLAPPROV" href="${datiRiga.PIATRI_URLAPPROV}" >
				<gene:checkCampoScheda funzione="controlloUrlApprov()" 
					messaggio="il campo URL non è valido. Deve iniziare con uno dei seguenti prefissi: http://, https://, ftp://, ftps://. Ad esempio: https://www.amministrazione.it/gara01/bando.pdf" obbligatorio="true" onsubmit="true" />
			</gene:campoScheda>
		</gene:gruppoCampi>
		
	</gene:gruppoCampi>
	
	<gene:redefineInsert name="schedaNuovo" />
	<gene:redefineInsert name="pulsanteNuovo" />
		
	<c:if test='${modificabile eq "no"}' >
		<gene:redefineInsert name="schedaModifica" />
		<gene:redefineInsert name="pulsanteModifica" />
	</c:if>
	
	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="PIATRI" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:redefineInsert name="addToAzioni">
	<c:if test='${createXML  && datiRiga.PIATRI_TIPROG ne 2}'>
		<tr>
			<c:choose>
	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'>
	      			<td class="vocemenulaterale">
					<a href="javascript:ExportXML('${datiRiga.PIATRI_CONTRI}');" title="Esporta in formato XML" tabindex="1511">
						Esporta in formato XML
					</a>
			  		</td>
				</c:when>
			<c:otherwise>
				<td>
				  Esporta in formato XML
			  	</td>
		  	</c:otherwise>
			</c:choose>
		</tr>
	</c:if>
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
<%-- 	<c:if test='${modificabile eq "si" && moduloFabbisogniAttivo eq "1"}'> --%>
<!-- 		<tr> -->
<%-- 			<c:choose> --%>
<%-- 	    		<c:when test='${isNavigazioneDisabilitata ne "1"}'> --%>
<!-- 	      			<td class="vocemenulaterale"> -->
<%-- 					<a href="javascript:importaFabbisogni();" title="Importa proposte di ${interventoVSacquisto}" tabindex="1512"> --%>
<%-- 						Importa proposte di ${interventoVSacquisto} --%>
<!-- 					</a> -->
<!-- 			  		</td> -->
<%-- 				</c:when> --%>
<%-- 			<c:otherwise> --%>
<!-- 				<td> -->
<%-- 				  Importa proposte di ${interventoVSacquisto} --%>
<!-- 			  	</td> -->
<%-- 		  	</c:otherwise> --%>
<%-- 			</c:choose> --%>
<!-- 		</tr> -->
<%-- 	</c:if> --%>
</gene:redefineInsert>
<gene:redefineInsert name="head">
	<!--  <link rel="STYLESHEET" type="text/css"	href="${pageContext.request.contextPath}/jquery/css/sunny/jquery-ui-1.8.21.custom.css"> -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.7.2.min.js"></script> -->
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-ui-1.8.21.custom.min.js"></script> -->
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.ui.datepicker-it.js"></script> -->
</gene:redefineInsert>

<gene:javaScript>

	$(document).ready(function() {
		var rigaDocumento = '#rowPIATRI_URLADOZI a';
		if ($(rigaDocumento).attr('href')) {
			$(rigaDocumento).attr("target", "_blank");
		}
		
		rigaDocumento = '#rowPIATRI_URLAPPROV a';
		if ($(rigaDocumento).attr('href')) {
			$(rigaDocumento).attr("target", "_blank");
		}
	});

	$(function() {
		$( "#PIATRI_DADOZI" ).datepicker($.datepicker.regional[ "it" ]);
		$( "#PIATRI_DAPTRI" ).datepicker($.datepicker.regional[ "it" ]);
		$( "#jsPopUpPIATRI_DADOZI" ).hide();
		$( "#jsPopUpPIATRI_DAPTRI" ).hide();
	});
		
	function ExportXML(idPiano) {
		var comando = "href=/piani/commons/popup-validazione-xml.jsp?verificaHiProg3=true&contri="+ idPiano;
		openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}
	
	function controlloUrlAdozi() {
		var strUrl = trimStringa(getValue("PIATRI_URLADOZI"));
		document.getElementById("PIATRI_URLADOZI").value = strUrl;
		if (strUrl.length > 0) {
			return controlloUrl(strUrl);
		} else {
			return true;
		}
	}
	
	function controlloUrlApprov() {
		var strUrl = trimStringa(getValue("PIATRI_URLAPPROV"));
		document.getElementById("PIATRI_URLAPPROV").value = strUrl;
		if (strUrl.length > 0) {
			return controlloUrl(strUrl);
		} else {
			return true;
		}
	}
	
	function controlloUrl(strUrl) {
		if (strUrl.toLowerCase().indexOf("http://") == 0 || strUrl.toLowerCase().indexOf("https://") == 0) {
			return true;
		} else if (strUrl.toLowerCase().indexOf("ftp://") == 0 || strUrl.toLowerCase().indexOf("ftps://") == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	function controlloAnno() {
		var strAnno = trimStringa(getValue("PIATRI_ANNTRI"));
		var anno = parseInt(strAnno, 10);
		var dt = new Date();
		var annoAttuale = dt.getYear();
		if (annoAttuale < 200) {
			annoAttuale = annoAttuale + 1900;
		}
		if (anno >= 2000 && anno <= annoAttuale + 4) {
			return true;
		} else {
			return false;
		}
	}
	
	<!--  	//Raccolta fabbisogni: modifiche per prototipo dicembre 2018 -->
	<!-- 	//vedi Raccolta fabbisogni.docx (mail da Lelio 26/11/2018) -->		
	function importaFabbisogni() {
		var action = "${pageContext.request.contextPath}/piani/GetListaFabbisogni.do";
		var piatri_contri = getValue("PIATRI_CONTRI");
		var parametri = new String('contri='+piatri_contri);
		openPopUpActionCustom(action, parametri, 'listaFabbisogni',1000,500,"yes","yes");
		
	}
	
</gene:javaScript>
<jsp:include page="/WEB-INF/pages/commons/defaultValues.jsp" />