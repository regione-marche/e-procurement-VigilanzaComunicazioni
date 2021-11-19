<%
/*
 * Created on: 03/09/2012
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<gene:template file="scheda-template.jsp">
<gene:setString name="titoloMaschera" value='Importazione programmi triennali' />

<gene:redefineInsert name="documentiAzioni"></gene:redefineInsert>

<gene:redefineInsert name="addToAzioni" >
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:Importa();" title="Importa" tabindex="1503">Importa</a>
		</td>
	</tr>
	<tr>
		<td class="vocemenulaterale" >
			<a href="javascript:Annulla();" title="Annulla" tabindex="1504">Annulla</a>
		</td>
	</tr>
	
</gene:redefineInsert>

<gene:redefineInsert name="corpo">
<form method="post" enctype="multipart/form-data" name="formImportProgrammi" action="${pageContext.request.contextPath}/piani/ImportHiProg3.do">
	<table class="dettaglio-notab">
	    <tr>
	    	<td colspan="2">
	    	  <p>
		    	  <br>Questa funzione permette di caricare un nuovo programma triennale dall'esportazione XML del programma triennale.
				  <br>&nbsp;
				  <br>&nbsp;
			  </p>
			</td>
		</tr>
		<tr>
			<td class="etichetta-dato">File da importare (*)</td>
			<td class="valore-dato"><input type="file" name="selezioneFile" size="55" onkeydown="return bloccaCaratteriDaTastiera(event);"></td>
		</tr>
		<tr class="comandi-dettaglio">
			<td class="comandi-dettaglio" colspan="2">
				<input type="button" value="Importa" title="Importa" class="bottone-azione" onclick="javascript:Importa();"/>&nbsp;
				<input type="button" value="Annulla" title="Annulla" class="bottone-azione" onclick="javascript:Annulla();"/>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</form>

</gene:redefineInsert>

<gene:javaScript>

	function Importa(){
		if(document.formImportProgrammi.selezioneFile) {
			var nomeCompletoFile = "" + document.formImportProgrammi.selezioneFile.value;
			if(nomeCompletoFile != ""){
				if(nomeCompletoFile.substr(nomeCompletoFile.lastIndexOf('.')+1).toUpperCase() == "XML") {
					bloccaRichiesteServer();
					setTimeout("document.formImportProgrammi.submit()", 150);
				} else {
					alert("Il file selezionato non è nel formato valido. Deve avere estensione XML");
					document.formImportProgrammi.selezioneFile.value = "";
				}
			}
			if (document.formImportProgrammi.selezioneFile.value == "") {
				alert("Deve essere indicato il file da importare.");				
			}
		}
	}

	function Annulla(){
		document.location.href = '${pageContext.request.contextPath}/Home.do?' + csrfToken ;
	}

</gene:javaScript>

</gene:template>

