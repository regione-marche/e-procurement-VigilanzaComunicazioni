
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


<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda"%>


<gene:template file="scheda-template.jsp" gestisciProtezioni="true" idMaschera="W3USRSYS-Scheda" schema="W3">

	<gene:setString name="titoloMaschera" value="Informazioni sul Responsabile Unico del Procedimento e la Lista delle Collaborazioni" />

	<gene:redefineInsert name="corpo">
		<gene:formScheda entita="W3USRSYS" gestisciProtezioni="true" gestore="it.eldasoft.sil.w3.tags.gestori.submit.GestoreW3USRSYS">

			<gene:campoScheda campo="SYSCON" visibile="false" defaultValue="${sessionScope.profiloUtente.id}"/>
			<gene:campoScheda>
				<td colspan="2">
					<br>
					<b>A) RESPONSABILE UNICO del PROCEDIMENTO</b>
					<br>
					<br>
					Ai fini delle comunicazioni con il sistema SIMOG è <b>obbligatorio</b> che tra i dati dell'utente correntemente connesso, 
					che in questo contesto assume il ruolo di Responsabile Unico del Procedimento, sia indicato anche il <b>codice fiscale</b>.
					<br>
					<br>
					<i>E', quindi, necessario collegare l'utente ad un tecnico dell'archivio dei tecnici</i>.
					<br>
					<br>
				</td>
			</gene:campoScheda>			
			
			<gene:archivio titolo="TECNICI"
				lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W3.W3USRSYS.RUP_CODTEC"),"gene/tecni/tecni-lista-popup.jsp","")}'
				scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
				schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
				campi="TECNI.CODTEC;TECNI.NOMTEC;TECNI.CFTEC" chiave="W3USRSYS_RUP_CODTEC" >
				<gene:campoScheda campo="RUP_CODTEC" visibile="false"/>
				<gene:campoScheda obbligatorio="true" title="Descrizione del RUP" entita="TECNI" campo="NOMTEC" from="W3USRSYS" where="TECNI.CODTEC = W3USRSYS.RUP_CODTEC" />
				<gene:campoScheda obbligatorio="true" title="Codice fiscale del RUP" entita="TECNI" campo="CFTEC" from="W3USRSYS" where="TECNI.CODTEC = W3USRSYS.RUP_CODTEC" />				
			</gene:archivio>
			
			<gene:callFunction obj="it.eldasoft.sil.w3.tags.funzioni.GestioneW3AZIENDAUFFICIOFunction" parametro="${gene:getValCampo(key, 'SYSCON')};${gene:getValCampo(key, 'RUP_CODTEC')}" />
			
			<c:if test="${!empty datiW3AZIENDAUFFICIO}">
			
				<gene:campoScheda>
					<td colspan="2">
					<br>
					<b>B) CENTRI di COSTO (Lista delle collaborazioni)</b>
					<br>
					<br>
					La lista delle collaborazioni ossia delle amministrazioni/stazioni appaltanti gestite dal RUP pu&ograve; essere ricavata solamente
					interrogando i servizi SIMOG.
					<br>
					<br>
					</td>
				</gene:campoScheda>	
			
				<jsp:include page="/WEB-INF/pages/commons/interno-scheda-multipla.jsp" >
					<jsp:param name="entita" value='W3AZIENDAUFFICIO'/>
					<jsp:param name="chiave" value='${key}'/>
					<jsp:param name="nomeAttributoLista" value='datiW3AZIENDAUFFICIO' />
					<jsp:param name="idProtezioni" value="W3AZIENDAUFFICIO" />
					<jsp:param name="sezioneListaVuota" value="true" />
					<jsp:param name="jspDettaglioSingolo" value="/WEB-INF/pages/w3/w3aziendaufficio/w3aziendaufficio-interno-scheda-multipla.jsp"/>
					<jsp:param name="arrayCampi" value="'W3AZIENDAUFFICIO_ID_', 'W3AZIENDAUFFICIO_UFFICIO_DENOM_', 'W3AZIENDAUFFICIO_UFFICIO_ID_', 'W3AZIENDAUFFICIO_AZIENDA_CF_', 'W3AZIENDAUFFICIO_AZIENDA_DENOM_', 'W3AZIENDAUFFICIO_INDEXCOLL_'  "/>
					<jsp:param name="arrayVisibilitaCampi" value="false,true,true,true,true,true" />
					<jsp:param name="titoloSezione" value="" />
					<jsp:param name="titoloNuovaSezione" value="Collaborazione" />
					<jsp:param name="descEntitaVociLink" value="collaborazione" />
					<jsp:param name="msgRaggiuntoMax" value="e collaborazioni"/>
					<jsp:param name="usaContatoreLista" value="false"/>
					<jsp:param name="numMaxDettagliInseribili" value="5"/>
				</jsp:include>
			
			</c:if>
			
			<c:choose>
				<c:when test='${modo eq "MODIFICA" or modo eq "NUOVO"}'>
							
				</c:when>
				<c:otherwise>
					<gene:redefineInsert name="pulsanteModifica">
           				<INPUT type="button" class="bottone-azione" value="Importa o aggiorna lista collaborazioni" title="Importa o aggiorna lista collaborazioni" onclick="javascript:richiestaCollaborazioni();">&nbsp;&nbsp;
					</gene:redefineInsert>
					<gene:redefineInsert name="schedaModifica">
						<tr>
							<td class="vocemenulaterale">
								<a href="javascript:richiestaCollaborazioni();" title="Importa o aggiorna lista collaborazioni" tabindex="1501">
								Importa o aggiorna lista collaborazioni</a></td>
						</tr>
					</gene:redefineInsert>
				</c:otherwise>
			</c:choose>
			
			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>	
			
		</gene:formScheda>
	</gene:redefineInsert>

	<gene:javaScript>
		document.forms[0].jspPathTo.value="w3/w3usrsys/w3usrsys-scheda.jsp";
		
		function richiestaCollaborazioni() {
			openPopUpCustom("href=w3/w3usrsys/w3usrsys-richiesta-collaborazioni.jsp?codrup=${gene:getValCampo(key, 'RUP_CODTEC')}", "richiestaCollaborazioni", 550, 300, "yes", "yes");
		}
		
		
	</gene:javaScript>	

</gene:template>


