
<%
	/*
	 * Created on 11-ott-2011
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

<div style="width:97%;">

	<gene:template file="popup-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="W9GARA-cambia-rup-scheda">
		<c:choose>
			<c:when test='${RISULTATO != null}'>
				<c:set var="codgara" value='${CODGARA}' />
			</c:when>
			<c:otherwise>
				<c:set var="codgara" value="${param.codgara}" />
			</c:otherwise>
		</c:choose>
		
		<gene:setString name="titoloMaschera" value='Cambia RUP' />
		
		<gene:redefineInsert name="corpo">
			<c:choose>
				<c:when test='${RISULTATO eq "CAMBIARUPESEGUITO"}'>
					<c:set var="modo" value="APRI" scope="request" />	
				</c:when>
				<c:otherwise>
					<c:set var="modo" value="MODIFICA" scope="request" />
				</c:otherwise>
			</c:choose>
		
			<gene:formScheda entita="W9GARA" where="W9GARA.CODGARA = ${codgara}" gestisciProtezioni="true" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARACambiaRUP">
				
				<c:choose>
					<c:when test='${RISULTATO eq "CAMBIARUPESEGUITO"}'>
						<gene:campoScheda>
							<td colspan="2">
								<br>
								Il nuovo RUP &egrave; stato assegnato alla gara selezionata
								<br><br>
							</td>
						</gene:campoScheda>
					</c:when>
					
					<c:otherwise>
						<gene:campoScheda>
							<td colspan="2">
								<br>
								Indicare il nuovo RUP
								<br><br>
							</td>
						</gene:campoScheda>
					</c:otherwise>
				</c:choose>
				
				<gene:campoScheda campo="CODGARA" visibile="false" />
				<gene:campoScheda campo="OGGETTO" modificabile="false" />
				<gene:archivio titolo="TECNICI"
					lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.W9GARA.RESPRO"),"gene/tecni/tecni-lista-popup.jsp","")}'
					scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
					schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
					campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9GARA_RUP" >
					<gene:campoScheda campo="RUP" visibile="false" />
					<gene:campoScheda campo="NOMTEC" entita="TECNI" where="TECNI.CODTEC=W9GARA.RUP"	title="RUP" />
				</gene:archivio>
				
				<c:choose>
					<c:when test='${RISULTATO eq "CAMBIARUPESEGUITO"}'>
						<gene:campoScheda>
							<td class="comandi-dettaglio" colSpan="2">
								<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:annulla();">&nbsp;&nbsp;
							</td>
						</gene:campoScheda>
					</c:when>
					
					<c:otherwise>
						<gene:campoScheda>
							<td class="comandi-dettaglio" colSpan="2">
								<INPUT type="button" class="bottone-azione" value="Cambia RUP" title="Cambia RUP" onclick="javascript:cambiarup();">
								<INPUT type="button" class="bottone-azione" value="Annulla" title="Annulla" onclick="javascript:annulla();">&nbsp;&nbsp;
							</td>
						</gene:campoScheda>
					</c:otherwise>
				</c:choose>
			</gene:formScheda>
		</gene:redefineInsert>
		
		<gene:javaScript>
		
			document.forms[0].jspPathTo.value="w9/w9gara/w9gara-cambia-rup-scheda.jsp";

			showObj("jsPopUpW3GARA_OGGETTO", false);
			
		    <c:if test='${RISULTATO eq "CAMBIARUPESEGUITO"}'>
		    	window.opener.listaVaiAPagina(window.opener.document.forms[0].pgCorrente.value);
			</c:if>
			
			function annulla(){
				window.close();
			}
			
			function cambiarup(){
				schedaConferma();
			}
		
		</gene:javaScript>	
		
	</gene:template>

</div>
