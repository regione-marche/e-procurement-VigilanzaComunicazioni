 <%
/*
     *
     * Copyright (c) EldaSoft S.p.A.
     * Tutti i diritti sono riservati.
     *
     * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
     * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
     * aver prima formalizzato un accordo specifico con EldaSoft.

     Descrizione:
		Selezione del dettaglio delle categoria dell'intervento
		Parametri:
			param.modo 
				Modo di apertura MODIFICA per modifica o VISUALIZZA per visualizzazione
			param.campo 
				Nome del campo in cui vi e' il dettaglio
			param.valore 
				Valore del campo

*/
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>


<gene:template file="popup-template.jsp">
	<gene:setString name="titoloMaschera" value="Dettaglio localizzazione"/>
	<gene:redefineInsert name="corpo">
		<gene:formScheda gestisciProtezioni="false" entita="">
			<gene:campoScheda campo="STATO" nome="STATO" title="Stato" obbligatorio="true" definizione="T10" 
				value="${param.stato}" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoStato"/>
			<gene:campoScheda campo="REGIONE" nome="REGIONE" title="Regione" obbligatorio="true" definizione="T10" 
				value="${param.regione}"  gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoRegione"/>
			<gene:campoScheda campo="PROVINCIA"  nome="PROVINCIA" title="Provincia" obbligatorio="true" definizione="T10"  
				value="${param.provincia}" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoProvincia"/>
			<gene:campoScheda campo="COMUNE" nome="COMUNE" title="Comune" obbligatorio="true" definizione="T10"  
				value="${param.comune}"  gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoComune"/>
			<gene:campoScheda visibile='${param.modo eq "MODIFICA"}'>
				<td class="comandi-dettaglio" colSpan="2">	
						<INPUT type="button" class="bottone-azione" value="Conferma" title="Salva modifiche" onclick="javascript:conferma()" />
						<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla modifiche" onclick="javascript:annulla()" />
				</td>			
			</gene:campoScheda>

			<gene:fnJavaScriptScheda funzione='modifyStato()' elencocampi="STATO" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyRegione()' elencocampi="REGIONE" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modifyProvincia()' elencocampi="PROVINCIA" esegui="false" />
			<gene:fnJavaScriptScheda funzione='reloadLOCALIZZAZIONE()' elencocampi="STATO;REGIONE;PROVINCIA" esegui="false" />
			
		</gene:formScheda>
		
  </gene:redefineInsert>

	<gene:javaScript>


		function modifyStato(){
			setValue("REGIONE","-1", false);
			setValue("PROVINCIA","-1", false);
			setValue("COMUNE","-1", false);
		}

		function modifyRegione(){
			setValue("PROVINCIA","-1", false);
			setValue("COMUNE","-1", false);
		}
		
		function modifyProvincia(){
			setValue("COMUNE","-1", false);
		}		
		
		function reloadLOCALIZZAZIONE() {
			var href = "piani/cuploc/cuploc-popup-localizzazione.jsp&modo=MODIFICA";
			href = href + "&contatore=" + ${param.contatore};
			href = href + "&stato=" + getValue("STATO");
			href = href + "&regione=" + getValue("REGIONE");
			href = href + "&provincia=" + getValue("PROVINCIA");
			href = href + "&comune=" + getValue("COMUNE");
			bloccaRichiesteServer();
			document.location.href="${pageContext.request.contextPath}/ApriPagina.do?" + csrfToken + "&href=" + href;
		}
		
		function conferma(){
			opener.setValue("CUPLOC_STATO_${param.contatore}",getValue("STATO"));
			opener.setValue("CUPLOC_REGIONE_${param.contatore}",getValue("REGIONE"));
			opener.setValue("CUPLOC_PROVINCIA_${param.contatore}",getValue("PROVINCIA"));
			opener.setValue("CUPLOC_COMUNE_${param.contatore}",getValue("COMUNE"));
			
			var statoDesc = document.getElementById("STATO").options[document.getElementById("STATO").selectedIndex].text;
			var regioneDesc = document.getElementById("REGIONE").options[document.getElementById("REGIONE").selectedIndex].text;
			var provinciaDesc = document.getElementById("PROVINCIA").options[document.getElementById("PROVINCIA").selectedIndex].text;
			var comuneDesc = document.getElementById("COMUNE").options[document.getElementById("COMUNE").selectedIndex].text;
			
			opener.setValue("STATO_DESC_${param.contatore}",statoDesc);
			opener.setValue("REGIONE_DESC_${param.contatore}",regioneDesc);
			opener.setValue("PROVINCIA_DESC_${param.contatore}",provinciaDesc);
			opener.setValue("COMUNE_DESC_${param.contatore}",comuneDesc);

			window.close();
		}
		
		function annulla(){
			window.close();
		}

	</gene:javaScript>
</gene:template>

