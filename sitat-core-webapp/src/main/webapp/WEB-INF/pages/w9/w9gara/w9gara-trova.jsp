<%/*
       * Created on 22-Set-2006
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

<gene:template file="ricerca-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9GARA-trova">
	<gene:setString name="titoloMaschera" value="Ricerca gare"/>
	
	<% // Ridefinisco il corpo della ricerca %>
	<gene:redefineInsert name="corpo">

  	<gene:formTrova entita="W9GARA" gestisciProtezioni="true" >
		<gene:gruppoCampi idProtezioni="GEN">
			<tr><td colspan="3"><b>Dati della gara</b></td></tr>
			<gene:campoTrova campo="OGGETTO"/>
			<gene:campoTrova campo="TIPO_APP"/>
			<gene:campoTrova campo="IDAVGARA"/>
			<gene:campoTrova campo="IMPORTO_GARA"/>
			<gene:campoTrova campo="DENOM" entita="UFFICI" where="UFFICI.ID = W9GARA.IDUFFICIO"/>
			<gene:campoTrova campo="SITUAZIONE"/>
			<tr>
				<td class="etichetta-dato">Visualizza anche gare archiviate</td>
				<td class="operatore-trova">&nbsp;</td>
				<td class="valore-dato-trova">
					<select name="archiviate" id="archiviate">
						<option value="N">No</option>
						<option value="S">Si</option>
					</select>
				</td>
			</tr>
			<tr><td colspan="3"><b>Dati del lotto</b></td></tr>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="CIG"/>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="OGGETTO"/>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="ID_SCELTA_CONTRAENTE"/>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="TIPO_CONTRATTO"/>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="IMPORTO_TOT"/>
			<gene:campoTrova entita="W9LOTT" where="W9LOTT.CODGARA = W9GARA.CODGARA" campo="CUP"/>
		</gene:gruppoCampi>
    </gene:formTrova>    
  </gene:redefineInsert>
  
  <gene:javaScript>
  	<c:if test='${! empty cercaGareArchiviate}'>
  	var valore = '${cercaGareArchiviate}';
 	if (valore == 'S') {
 		document.getElementById('archiviate').selectedIndex = 1;
 	} else if(valore == 'N') { 
 		document.getElementById('archiviate').selectedIndex = 0;
 	}
  	</c:if>
  </gene:javaScript>
  
</gene:template>
