<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<gene:template file="wizard-template.jsp" gestisciProtezioni="true" schema="W9" idMaschera="USR_EIN-wizard">
	<gene:setString name="titoloMaschera" value='Creazione guidata utente' />
	
	<c:set var="codein" value='${gene:getValCampo(param.keyParent,"CODEIN")}' scope="request"/>
	
	<gene:redefineInsert name="corpo">
		<gene:wizard gestisciProtezioni="true" entitaPrincipale="USR_EIN" gestoreSalvataggio="it.eldasoft.w9.tags.gestori.submit.GestoreUSR_EIN">

			<gene:getDatoWizard id="tipoSelezione" name="TIPO_SEL"/>

			<gene:paginaWizard title="Selezione tipologia" idProtezioni="TIP">
				<c:if test="${sottoPaginaWizard eq '0'}">
					<gene:formSchedaWizard entita="USR_EIN" gestisciProtezioni="true"
					gestoreNavigazione="it.eldasoft.w9.web.struts.navigazione.SelezioneTipologiaCreazioneUtenteAction">
						<gene:campoScheda>
							<td colspan="2">
							<b>Selezione della tipologia di creazione utente</b>
							<span class="info-wizard"> Questa procedura guidata permette di inserire un nuovo utente collegato ad un tecnico per la stazione appaltante. </span></td>
						</gene:campoScheda>
						
						<gene:campoScheda>
							<td colspan="2">
							<br>
						<input type="radio" name="CHECK_SEL" value="0" <c:if test="${datiRiga.TIPO_SEL eq 0}">checked="checked"</c:if> onchange="setValue('TIPO_SEL', 0)">&nbsp;<b>Nuovo utente</b><br>
						Questa modalit&agrave; consente di definire un nuovo utente specificandone tutte le informazioni che lo caratterizzano,
						ovvero i dati dell'anagrafica, i dati per l'accesso all'applicativo ed i privilegi.<br>
						<br>
						<input type="radio" name="CHECK_SEL" value="1" <c:if test="${datiRiga.TIPO_SEL eq 1}">checked="checked"</c:if> onchange="setValue('TIPO_SEL', 1)">&nbsp;<b>Importa utente da altra stazione appaltante</b><br>
						Questa modalit&agrave; consente di importare l'utente definito per un'altra stazione appaltante 
						all'interno della stazione appaltante in oggetto.<br>
						<br>
						<input type="radio" name="CHECK_SEL" value="2" <c:if test="${datiRiga.TIPO_SEL eq 2}">checked="checked"</c:if> onchange="setValue('TIPO_SEL', 2)">&nbsp;<b>Importa dall'archivio dei tecnici</b><br>
						Questa modalit&agrave; consente di definire un nuovo utente sfruttando i dati gi&agrave; definiti per un tecnico
						presente in archivio per la stazione appaltante, e consentendo di specificare i dati per l'accesso 
						all'applicativo ed i privilegi.<br>
						<br>
							</td>
						</gene:campoScheda>
						<gene:campoScheda campo="TIPO_SEL" campoFittizio="true" definizione="N" visibile="false" defaultValue="0" />
						<gene:campoScheda campo="CODEIN" visibile="false" defaultValue="${requestScope.codein}" />
						
						<gene:redefineInsert name="wizardFine"></gene:redefineInsert>
						<gene:redefineInsert name="pulsanteFine"></gene:redefineInsert>
						
						<gene:campoScheda>
							<jsp:include page="/WEB-INF/pages/commons/pulsantiWizard.jsp" />
						</gene:campoScheda>
					</gene:formSchedaWizard>
				</c:if>
			</gene:paginaWizard>
			
			<c:if test="${tipoSelezione eq 1}">
			<gene:paginaWizard title="Selezione utente" idProtezioni="SEL">

			<gene:formSchedaWizard entita="USRSYS" gestisciProtezioni="true"
				plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreSelezioneAccountAltraSA"
				gestoreNavigazione="it.eldasoft.w9.web.struts.navigazione.SelezioneAccountAltraSAAction" >
				<gene:campoScheda>
					<td colspan="2">
					<b>Lista utenti stazioni appaltanti</b>
					<span class="info-wizard">Selezionare la stazione appaltante di
							interesse, quindi dall'elenco selezionare l'utente da utilizzare
							per l'associazione nella stazione appaltante in oggetto. </span>
					</td>
				</gene:campoScheda>
				<gene:archivio 
					titolo="Stazioni appaltanti"
					inseribile="false" 
					campi="UFFINT.CODEIN;UFFINT.NOMEIN" 
					schedaPopUp="" 
					scheda="" 
					chiave="SA_IMPORT" 
					lista="gene/uffint/uffint-lista-popup.jsp"
					where="UFFINT.CODEIN <> '${codein}'">
				<gene:campoScheda title="Codice" campoFittizio="true" campo="SA_IMPORT" definizione="T16" visibile="false"/>
				<gene:campoScheda title="Stazione appaltante di origine" campoFittizio="true" campo="DESCSA_IMPORT" definizione="T254" />
				</gene:archivio>
				<gene:fnJavaScriptScheda elencocampi="DESCSA_IMPORT" esegui="false" funzione="setValue('USRSYS_SYSCON','');wizardExtra();" />
				<gene:campoScheda>
					<td colspan="2">
					<display:table name="${utenti}" class="datilista" id="utente">
						<display:column style="width:30px">
							<input type="radio" id="selUtente" name="selUtente" value="${tecnico[0]}" <c:if test="${datiRiga.USRSYS_SYSCON eq utente[0]}">checked="checked"</c:if> onchange="setValue('USRSYS_SYSCON', '${utente[0]}')">
						</display:column>
						<display:column property="[1]" title="Nome" />
						<display:column property="[2]" title="Login" />
					</display:table>
					</td>
				</gene:campoScheda>
				<gene:campoScheda campo="SYSCON" visibile="false" />						
				<gene:redefineInsert name="wizardFine"></gene:redefineInsert>
				<gene:redefineInsert name="pulsanteFine"></gene:redefineInsert>
				<gene:redefineInsert name="wizardAvanti">
					<tr id="linkAvanti">
						<td class="vocemenulaterale">
							<a href="javascript:wizardAvantiAccount();" title="Avanti &gt;" tabindex="1520">
								${gene:resource("label.tags.template.wizard.wizardAvanti")}</a></td>
					</tr>
				</gene:redefineInsert>
				<gene:redefineInsert name="pulsanteAvanti">
			<span id="btnAvanti">
				&nbsp;
				<INPUT type="button" class="bottone-azione" value="Avanti &gt;" title="Avanti" onclick="javascript:wizardAvantiAccount();">
			</span>
				</gene:redefineInsert>
				<gene:campoScheda>
					<jsp:include page="/WEB-INF/pages/commons/pulsantiWizard.jsp" />
				</gene:campoScheda>
				
				<gene:javaScript>
				function wizardAvantiAccount() {
					if (getValue('USRSYS_SYSCON') == "")
						alert("Selezionare un utente per poter procedere");
					else wizardAvanti();
				}
				</gene:javaScript>

			</gene:formSchedaWizard>
			</gene:paginaWizard>
			</c:if>

			<c:if test="${tipoSelezione eq 2}">
			<gene:paginaWizard title="Selezione tecnico" idProtezioni="SEL">
			
			<gene:formSchedaWizard entita="TECNI" gestisciProtezioni="true" tableClass="lista"
				plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreSelezioneTecnicoSA" 
				gestoreNavigazione="it.eldasoft.w9.web.struts.navigazione.SelezioneTecnicoSAAction" >
				<gene:campoScheda>
					<td colspan="2">
					<b>Lista tecnici</b>
					<span class="info-wizard">
					Il presente elenco &egrave; costituito dai tecnici definiti nella stazione appaltante.<br>
					Selezionare il tecnico da utilizzare per l'associazione dello stesso con l'utente. 
					</span>
					</td>
				</gene:campoScheda>
				<gene:campoScheda>
					<td colspan="2">
					<display:table name="${tecnici}" class="datilista" id="tecnico">
						<display:column style="width:30px">
							<input type="radio" id="selTecnico" name="selTecnico" value="${tecnico[0]}" <c:if test="${datiRiga.TECNI_CODTEC eq tecnico[0]}">checked="checked"</c:if> onchange="setValue('TECNI_CODTEC', '${tecnico[0]}')">
						</display:column>
						<display:column property="[1]" title="Nome" />
						<display:column property="[2]" title="Codice fiscale" />
					</display:table>
					</td>
				</gene:campoScheda>
				<gene:campoScheda campo="CODTEC" visibile="false" />
						
				<gene:redefineInsert name="wizardFine"></gene:redefineInsert>
				<gene:redefineInsert name="pulsanteFine"></gene:redefineInsert>
				<gene:redefineInsert name="wizardAvanti">
					<tr id="linkAvanti">
						<td class="vocemenulaterale">
							<a href="javascript:wizardAvantiTecnico();" title="Avanti &gt;" tabindex="1520">
								${gene:resource("label.tags.template.wizard.wizardAvanti")}</a></td>
					</tr>
				</gene:redefineInsert>
				<gene:redefineInsert name="pulsanteAvanti">
			<span id="btnAvanti">
				&nbsp;
				<INPUT type="button" class="bottone-azione" value="Avanti &gt;" title="Avanti" onclick="javascript:wizardAvantiTecnico();">
			</span>
				</gene:redefineInsert>
				<gene:campoScheda>
					<jsp:include page="/WEB-INF/pages/commons/pulsantiWizard.jsp" />
				</gene:campoScheda>
				
				<gene:javaScript>
				function wizardAvantiTecnico() {
					if (getValue('TECNI_CODTEC') == "")
						alert("Selezionare un utente per poter procedere");
					else wizardAvanti();
				}
				</gene:javaScript>

			</gene:formSchedaWizard>
			</gene:paginaWizard>
			</c:if>


			<gene:paginaWizard title="Inserimento dati" idProtezioni="INS">
			
			<gene:formSchedaWizard entita="USR_EIN" gestisciProtezioni="true">
				<gene:campoScheda>
					<td colspan="2">
					<b>Inserimento e completamento dati per l'utente/tecnico</b>
					<span class="info-wizard">Completare l'inserimento dell'utente valorizzandone i dati generali, i parametri di accesso 
					all'applicativo, ed i privilegi. </span></td>
				</gene:campoScheda>
				
				<jsp:include page="usr_ein-interno-scheda.jsp"/>
			
				<gene:campoScheda>
					<jsp:include page="/WEB-INF/pages/commons/pulsantiWizard.jsp" />
				</gene:campoScheda>

			</gene:formSchedaWizard>
			</gene:paginaWizard>


		</gene:wizard>

	</gene:redefineInsert>
</gene:template>
