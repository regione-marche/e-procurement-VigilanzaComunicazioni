
<%
  /*
   * Created on: 26/02/2010
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

<gene:formScheda entita="AVVISO" gestisciProtezioni="true" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreAVVISO" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreAVVISO" >
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${sessionScope.uffint}" />
		<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
			campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="AVVISO_CODEIN" where=""
			formName="formStazApp">
			<gene:campoScheda campo="CODEIN" visibile="false"
				defaultValue="${sessionScope.uffint}" />
			<gene:campoScheda campo="NOMEIN" entita="UFFINT"
				defaultValue="${nomein}" where="UFFINT.CODEIN = AVVISO.CODEIN"
				modificabile="false" title="Stazione appaltante" />
		</gene:archivio>
		<gene:archivio titolo="Ufficio/area di pertinenza"
			lista='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.ListaUffici"),"w9/uffici/uffici-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","W9.SchedaUffici"),"w9/uffici/uffici-scheda-popup.jsp","")}'
			where="UFFICI.CODEIN = '${sessionScope.uffint}'"
			campi="UFFICI.ID;UFFICI.DENOM"
			chiave="AVVISO_IDUFFICIO">
			<gene:campoScheda campo="IDUFFICIO" visibile="false" />
			<gene:campoScheda campo="DENOM" entita="UFFICI"
				where="UFFICI.ID=AVVISO.IDUFFICIO" modificabile='${gene:checkProt(pageContext, "COLS.MOD.W9.AVVISO.IDUFFICIO")}'
				title="Ufficio/area di pertinenza" definizione="T250" />
		</gene:archivio>
		<gene:campoScheda campo="IDAVVISO" modificabile="false" />
		<gene:campoScheda campo="CODSISTEMA" visibile="false" defaultValue="1" />
		<gene:campoScheda campo="CODGARA" visibile="false" />
		<gene:campoScheda campo="CODLOTT" visibile="false" />
		<gene:campoScheda campo="RUP" visibile="false" defaultValue="${idTecnico}"/>
		
		<gene:archivio titolo="TECNICI"
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.AVVISO.RUP"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="AVVISO_RUP" >
			<gene:campoScheda campo="RUP" defaultValue="${idTecnico}"
				visibile="false" />
			<gene:campoScheda campo="NOMTEC" entita="TECNI"
				defaultValue="${nomeTecnico}" where="TECNI.CODTEC=AVVISO.RUP"
				modificabile="${sessionScope.profiloUtente.abilitazioneStd eq 'A' || gene:checkProt(pageContext, 'FUNZ.VIS.ALT.W9.W9SCP')}"
				title="RUP" visibile="true">
			</gene:campoScheda>
		</gene:archivio>
		
		
		<gene:archivio titolo="Lotti"
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.PIANI.PIATRI.RESPRO"),"w9/avviso/popup-lista-lotti.jsp","")}'
			scheda=''
			schedaPopUp=''
			inseribile="false"
			scollegabile="true"
			campi="W9LOTT.CODGARA;W9LOTT.CODLOTT;W9LOTT.CIG" chiave="" >
			<gene:campoScheda campo="CODGARA" visibile="false"/>
			<gene:campoScheda campo="CODLOTT" visibile="false"/>
			<gene:campoScheda campo="CIG">
				<gene:checkCampoScheda funzione='checkCodiceCIG()' obbligatorio="true" onsubmit="false" messaggio="Codice CIG o SmartCIG non valido" />
			</gene:campoScheda>
		</gene:archivio>
		<gene:campoScheda campo="CUP" />
		<gene:campoScheda campo="CUIINT" />
		<gene:campoScheda campo="TIPOAVV" />
		<gene:campoScheda campo="DATAAVV" />
		<gene:campoScheda campo="DESCRI" />
		<gene:campoScheda campo="DATASCADENZA" />
		<gene:campoScheda>
			<td colspan="2"><b>Ubicazione</b></td>
		</gene:campoScheda>
		<gene:campoScheda campo="INDSEDE" />
		<gene:campoScheda campo="COMSEDE" />
		<gene:campoScheda campo="PROSEDE" />
		
	</gene:gruppoCampi>

	<c:if test='${modo ne "NUOVO"}'>
		<c:set var="tmp" value='${gene:callFunction4("it.eldasoft.w9.tags.funzioni.GestioneDocumentazioneAvvisoFunction", pageContext, codein, idavviso, codsistema)}' />
	</c:if>
	
	<c:choose>	
		<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and not(isDocPubblicazioneEstratti)}">
			<gene:campoScheda>
				<td colspan="2"><b>Documenti della pubblicazione</b></td>
			</gene:campoScheda>
			<gene:campoScheda >
				<td colspan="2">Nessun file allegato</td>
			</gene:campoScheda>
		</c:when>
		<c:when test="${modoAperturaScheda eq 'VISUALIZZA' and isDocPubblicazioneEstratti}">
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W9DOCAVVISO'/>
				<jsp:param name="chiave" value='${gene:getValCampo(key,"CODEIN")};${gene:getValCampo(key,"IDAVVISO")};${gene:getValCampo(key,"CODSISTEMA")}'/>
				<jsp:param name="nomeAttributoLista" value='documentiPubblicazione' />
				<jsp:param name="idProtezioni" value="W9DOCAVVISO" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docavviso/pubblicazione-documenti-avviso.jsp"/>
				<jsp:param name="arrayCampi" value="'W9DOCAVVISO_CODEIN_', 'W9DOCAVVISO_IDAVVISO_', 'W9DOCAVVISO_CODSISTEMA_', 'W9DOCAVVISO_NUMDOC_', 'W9DOCAVVISO_TITOLO_', 'W9DOCAVVISO_URL_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_'"/> 		
				<jsp:param name="titoloSezione" value="Documenti" />
				<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
				<jsp:param name="descEntitaVociLink" value="documento" />
				<jsp:param name="msgRaggiuntoMax" value="i documenti" />
				<jsp:param name="usaContatoreLista" value="true" />
			</jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
				<jsp:param name="entita" value='W9DOCAVVISO'/>
				<jsp:param name="chiave" value='${gene:getValCampo(key,"CODEIN")};${gene:getValCampo(key,"IDAVVISO")};${gene:getValCampo(key,"CODSISTEMA")}'/>
				<jsp:param name="nomeAttributoLista" value='documentiPubblicazione' />
				<jsp:param name="idProtezioni" value="W9DOCAVVISO" />
				<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w9/w9docavviso/pubblicazione-documenti-avviso.jsp"/>
				<jsp:param name="arrayCampi" value="'W9DOCAVVISO_CODEIN_', 'W9DOCAVVISO_IDAVVISO_', 'W9DOCAVVISO_CODSISTEMA_', 'W9DOCAVVISO_NUMDOC_', 'W9DOCAVVISO_TITOLO_', 'W9DOCAVVISO_URL_', 'NOME_FILE_', 'VISUALIZZA_FILE_', 'selezioneFile_'"/> 		
				<jsp:param name="titoloSezione" value="Documenti" />
				<jsp:param name="titoloNuovaSezione" value="Nuovo documento" />
				<jsp:param name="descEntitaVociLink" value="documento" />
				<jsp:param name="msgRaggiuntoMax" value="i documenti" />
				<jsp:param name="usaContatoreLista" value="true" />
			</jsp:include>
		</c:otherwise>
	</c:choose>
	
	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="AVVISO" />
	</jsp:include>

	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:redefineInsert name="head">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
</gene:redefineInsert>
<gene:javaScript>
	
	<c:if test="${modo eq 'MODIFICA' || modo eq 'NUOVO'}">
	document.forms[0].encoding="multipart/form-data";

	var tmpSchedaConferma = schedaConferma;
	var tmpSchedaAnnulla = schedaAnnulla;
	
	var schedaConferma = function() {
		// per ciascuna sezione dinamica visibile nella pagina si controlla che,
		// se il campo TITOLO e' valorizzato, allora anche il campo di tipo input file lo sia
		var numeroSezioni = eval(getValue("NUMERO_W9DOCAVVISO"));
		var continua = true;
		for (var er = 1; er <= numeroSezioni && continua; er++) {
			if (isObjShow("rowW9DOCAVVISO_TITOLO_" + er)) {
				if (getValue("W9DOCAVVISO_TITOLO_" + er) != "") {
					if(getValue("defVISUALIZZA_FILE_" + er) == null || getValue("defVISUALIZZA_FILE_" + er) == ""){
						if(document.getElementById("selFile[" + er + "]") != null && getValue("selFile[" + er + "]") == "") {
							if (getValue("W9DOCAVVISO_URL_" + er) == "") {
								alert("Valorizzare il campo 'URL al documento'\noppure il campo 'Nome file'");
								continua = false;
							}
						} else {
							if (document.getElementById("selFile[" + er + "]") != null) {
								if (getValue("NOME_FILE_"+er) == "" && getValue("W9DOCAVVISO_URL_" + er) == "") {
									alert("Valorizzare il campo 'URL al documento'\noppure il campo 'Nome file'");
									continua = false;
								} else if (getValue("NOME_FILE_"+er) != "" && getValue("W9DOCAVVISO_URL_" + er) != "") {
									alert("Valorizzare il campo 'URL al documento'\noppure il campo 'Nome file'");
									continua = false;
								}
							} else {
								if (getValue("W9DOCAVVISO_URL_" + er) == "") {
									alert("Valorizzare il campo 'URL al documento'");
									continua = false;
								}
							}
						}
					}
				}
			}
		}

		if (continua) {
			document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
			tmpSchedaConferma();
		}
	}
	
	var schedaAnnulla = function() {
		document.forms[0].action = "${pageContext.request.contextPath}/w9/Scheda.do?" + csrfToken;
		tmpSchedaAnnulla();
	}

</c:if>

	function scegliFile(indice) {
		var selezioneFile = document.getElementById("selFile[" + indice + "]").value;
		var lunghezza_stringa = selezioneFile.length;
		var posizione_barra = selezioneFile.lastIndexOf("\\");
		var posizione_punto = selezioneFile.lastIndexOf(".");
		var estensione = selezioneFile.substring(posizione_punto+1, lunghezza_stringa).toUpperCase();
		
		if (estensione != "PDF" && estensione != "P7M") {
			alert("E' permessa la selezione solo di file in formato PDF o P7M");
			document.getElementById("selFile[" + indice + "]").value = "";
			setValue("NOME_FILE_" + indice, "");
		} else {
			var nome = selezioneFile.substring(posizione_barra+1,lunghezza_stringa).toUpperCase();
			setValue("NOME_FILE_" + indice, nome);
		}
	}

	function visualizzaFileAllegato(numeroDoc) {
		var href = "${pageContext.request.contextPath}/w9/VisualizzaAllegato.do";
		document.location.href = href+"?" + csrfToken + "&codein=${codein}&idavviso=${idavviso}&codsistema=${codsistema}&numdoc=" + numeroDoc + "&tab=avviso";
	}
	
	function controlloURL(progressivoCampo) {
		if (document.getElementById("selFile[" + progressivoCampo + "]") != null && getValue("selFile[" + progressivoCampo + "]") == "" ) {
			var strUrl = trimStringa(getValue("W9DOCAVVISO_URL_" + progressivoCampo));
			document.getElementById("W9DOCAVVISO_URL_" + progressivoCampo).value = strUrl;
			
			if (strUrl.toLowerCase().indexOf("http://") == 0 || strUrl.toLowerCase().indexOf("https://") == 0) {
				return true;
			} else if (strUrl.toLowerCase().indexOf("ftp://") == 0 || strUrl.toLowerCase().indexOf("ftps://") == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	// Validazione formale dello SmartCIG
	function checkCodiceSmartCIG() {
		var codiceCIG = getValue("AVVISO_CIG");
		var result = false;
		if (codiceCIG.length == 10) {
			if (codiceCIG.startsWith("X") || codiceCIG.startsWith("Z") || codiceCIG.startsWith("Y")) {
				var strK = codiceCIG.substring(1,3);//Estraggo la firma
				var strC4_10 = codiceCIG.substring(3,10);
				if (isStringaEsadecimale(strK, 2) && isStringaEsadecimale(strC4_10, 7)) {
					var nDecStrK = parseInt(strK, 16); //trasformo in decimale
					var nDecStrC4_10 = parseInt(strC4_10, 16); //trasformo in decimale
					//Calcola Firma
					var nDecStrK_chk = ((nDecStrC4_10 * 1/1) * 211 % 251);
					if (nDecStrK == nDecStrK_chk) {
						  result = true;
					}
				}
			}
		} else if (codiceCIG.length == 0) {
			result = true;
		}
 		return result;
 	}
	
 	function isStringaNumerica(str, len) {
 		var oggettoEspressioneRegolare = new RegExp("^[0-9]{" + len + "}$");
		return oggettoEspressioneRegolare.test(str);
 	}
 	
 	function isStringaEsadecimale(str, len) {
 		var oggettoEspressioneRegolare = new RegExp("^[a-fA-F0-9]{" + len + "}$");
		return oggettoEspressioneRegolare.test(str);
 	}

	// leftPad della stringa str, con il carattere padStr, con lunghezza len
 	function leftPadFunction(str, padStr, len) {
		while (str.length < len) {
			str = padStr + str;
		}
		return str;
	}

	// Validazione formale del codiceCIG
	function checkCodiceCIG() {
		var codiceCIG = getValue("AVVISO_CIG");
		var result = false;
		if (codiceCIG.length == 10) {
			if (codiceCIG != "0000000000") {
				var primi7CaratteriCIG = codiceCIG.substring(0,7);
				var ultimi3CaratteriCIG = codiceCIG.substring(7).toUpperCase();
				
				if (isStringaNumerica(primi7CaratteriCIG, 7) && isStringaEsadecimale(ultimi3CaratteriCIG, 3)) {
					var oper1 = parseInt(primi7CaratteriCIG);
					var resto = (oper1 * 211) % 4091;
					var strResto = leftPadFunction(resto.toString(16),'0',3).toUpperCase();
					
					if (ultimi3CaratteriCIG == strResto) {
						result = true;
					}
				}
			}
		} else if (codiceCIG.length == 0) {
			result = true;
		}
 		return result || checkCodiceSmartCIG();
 	}
	
	$(document).ready(function() { 
		if ('${numDocumenti}' != '') {
			var numeroDocumenti= '${numDocumenti}';
			for (var i=1; i<=numeroDocumenti; i++) {
				var rigaDocumento = '#rowW9DOCAVVISO_URL_' + i + ' a';
				if ($(rigaDocumento).attr('href')) {
					var link = $(rigaDocumento).attr('href');
					if (link.toLowerCase().indexOf("http") < 0){
						var link = 'http://' + link;
						$(rigaDocumento).attr("href", link);
					}	
					$(rigaDocumento).attr("target","_blank");
				}
			}
		}
	});

</gene:javaScript>