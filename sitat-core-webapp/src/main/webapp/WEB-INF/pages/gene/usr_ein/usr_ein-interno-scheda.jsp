<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

		<gene:campoScheda campo="CODEIN" visibile="false" defaultValue='${requestScope.codein}'/>
		<gene:campoScheda campo="SYSCON" visibile="false"/>
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="TECNI" campo="CODTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN" modificabile='${modoAperturaScheda eq "NUOVO"}' gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoCodificaAutomatica" obbligatorio="${isCodificaAutomatica eq 'false'}" />
		<gene:campoScheda entita="TECNI" campo="COGTEI" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="NOMETEI" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="NOMTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN">
			<gene:calcoloCampoScheda funzione='calcolaFromCognome("#TECNI_NOMTEC#","#TECNI_COGTEI#","#TECNI_NOMETEI#")' elencocampi="TECNI_COGTEI" />
			<gene:calcoloCampoScheda funzione='calcolaFromNome("#TECNI_NOMTEC#","#TECNI_COGTEI#","#TECNI_NOMETEI#")' elencocampi="TECNI_NOMETEI" />
		</gene:campoScheda>
		<gene:campoScheda entita="TECNI" campo="CFTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN">
			<gene:checkCampoScheda funzione='checkCodFis("##")' obbligatorio="false" messaggio='Il valore specificato non &egrave; valido.' />
		</gene:campoScheda>	
		<gene:campoScheda entita="TECNI" campo="INDTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="NCITEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="PROTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:archivio titolo="Comuni" obbligatorio="false" 
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.CAPTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.PROTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.LOCTEC") and gene:checkProt(pageContext, "COLS.MOD.GENE.TECNI.CITTEC"),"gene/commons/istat-comuni-lista-popup.jsp","")}' 
				scheda="" 
				schedaPopUp="" 
				campi="TB1.TABCOD3;TABSCHE.TABCOD4;TABSCHE.TABDESC;TABSCHE.TABCOD3" 
				chiave="" 
				where='${gene:if(!empty datiRiga.TECNI_PROTEC, gene:concat(gene:concat("TB1.TABCOD3 = \'", datiRiga.TECNI_PROTEC), "\'"), "")}'  
				formName="formIstat" 
				inseribile="false" >
			<gene:campoScheda campoFittizio="true" campo="COM_PROTEC" definizione="T9" visibile="false"/>
			<gene:campoScheda entita="TECNI" campo="CAPTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
			<gene:campoScheda entita="TECNI" campo="LOCTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
			<gene:campoScheda entita="TECNI" campo="CITTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		</gene:archivio>
		<gene:campoScheda entita="TECNI" campo="TELTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="FAXTEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="EMATEC" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN"/>
		<gene:campoScheda entita="TECNI" campo="CGENTEI" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN" defaultValue='${requestScope.codein}' visibile="false"/>
		<gene:campoScheda entita="TECNI" campo="SYSCON" from="USRSYS" where="USR_EIN.SYSCON = USRSYS.SYSCON AND USRSYS.SYSCON = TECNI.SYSCON AND TECNI.CGENTEI = USR_EIN.CODEIN" visibile="false"/>
		
		<gene:campoScheda>
			<td colspan="2"><b>Parametri per l'accesso all'applicativo</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="USRSYS" campo="SYSCON" where="USR_EIN.SYSCON = USRSYS.SYSCON" visibile="false"/>
		<gene:campoScheda entita="USRSYS" campo="SYSUTE" where="USR_EIN.SYSCON = USRSYS.SYSCON" visibile="false" />
		<gene:campoScheda entita="USRSYS" campo="SYSLOGIN" where="USR_EIN.SYSCON = USRSYS.SYSCON" obbligatorio="true" />
		
		<gene:getDatoWizard id="usrsys" name="USRSYS.SYSCON"/>
		<c:if test="${empty usrsys && modoAperturaScheda eq 'NUOVO'}">
		<gene:campoScheda entita="USRSYS" campo="SYSPWD" where="USR_EIN.SYSCON = USRSYS.SYSCON" gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoPassword" obbligatorio="true" definizione="T30;0"/>
		<gene:campoScheda title="Conferma password" campoFittizio="true" campo="CONFSYSPWD" gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreCampoPassword" obbligatorio="true" definizione="T30;0">
			<gene:checkCampoScheda funzione='"##"=="#USRSYS_SYSPWD#"' obbligatorio="true" messaggio='La password non &egrave; stata confermata correttamente, immettere nuovamente il valore.' />
		</gene:campoScheda>
		</c:if>
		
		<gene:campoScheda entita="USRSYS" campo="EMAIL" where="USR_EIN.SYSCON = USRSYS.SYSCON" visibile="false"/>
		<gene:campoScheda>
			<td colspan="2"><b>Privilegi</b></td>
		</gene:campoScheda>
		<gene:campoScheda entita="USRSYS" campo="SYSPWBOU" where="USR_EIN.SYSCON = USRSYS.SYSCON" visibile="false"/>
		<gene:campoScheda campoFittizio="true" campo="OPZ_UTENTE" title="Amministrazione utenti"
			value="${gene:if(! empty datiRiga.OPZ_UTENTE, datiRiga.OPZ_UTENTE, gene:concat(gene:if(fn:contains(datiRiga.USRSYS_SYSPWBOU, 'ou11|'),'ou11',''), gene:if(fn:contains(datiRiga.USRSYS_SYSPWBOU, 'ou12|'),'|ou12','')))}" 
			gestore="it.eldasoft.gene.tags.decorators.campi.gestori.GestoreOpzAmmUtenti" definizione="T5;0"></gene:campoScheda>
		<gene:campoScheda title="Contratti" entita="USRSYS" campo="SYSAB3" where="USR_EIN.SYSCON = USRSYS.SYSCON" gestore="it.eldasoft.w9.tags.gestori.decoratori.GestoreCampoSYSAB3"/>
		
		<gene:fnJavaScriptScheda funzione='changeComune("#TECNI_PROTEC#", "COM_PROTEC")' elencocampi='TECNI_PROTEC' esegui="false"/>
		<gene:fnJavaScriptScheda funzione='setValueIfNotEmpty("TECNI_PROTEC", "#COM_PROTEC#")' elencocampi='COM_PROTEC' esegui="false"/>

		<gene:fnJavaScriptScheda funzione='setValue("USRSYS_SYSUTE", "#TECNI_NOMTEC#")' elencocampi='TECNI_NOMTEC' esegui="false"/>
		<gene:fnJavaScriptScheda funzione='setValue("USRSYS_EMAIL", "#TECNI_EMATEC#")' elencocampi='TECNI_EMATEC' esegui="false"/>
		

<gene:javaScript>
		function trim(stringa){
			while (stringa.substring(0,1) == ' ')
			{
				stringa = stringa.substring(1, stringa.length);
			}
			while (stringa.substring(stringa.length-1, stringa.length) == ' ')
			{
				stringa = stringa.substring(0,stringa.length-1);
			}
			return stringa;
		}
		function calcolaFromNome(intest,cognome, nome){
			if(intest==cognome){
				return trim(trim(cognome)+" "+trim(nome));
			}
			return intest;
		}
		function calcolaFromCognome(intest,cognome, nome){
			if(nome==""){
				return trim(trim(cognome)+" "+trim(nome));
			}
			return intest;
		}
		
function changeComune(provincia, nomeUnCampoInArchivio) {
	changeFiltroArchivioComuni(provincia, nomeUnCampoInArchivio);
	setValue("TECNI_CAPTEC", "");
	setValue("TECNI_LOCTEC", "");
	setValue("TECNI_CITTEC", "");
}
</gene:javaScript>
