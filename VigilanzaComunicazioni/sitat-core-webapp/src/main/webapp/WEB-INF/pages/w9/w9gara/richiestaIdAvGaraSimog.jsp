<%
	/*
	 * Created on 16-feb-2012
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
	
	<gene:setString name="titoloMaschera" value='Richiesta creazione nuova gara in SIMOG' />
	
	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="corpo">
		<gene:formPagine gestisciProtezioni="true">
			<gene:pagina title="Richiesta creazione IDAVGARA" >

				<gene:formScheda entita="W9GARA" plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9GARA" gestore="it.eldasoft.w9.tags.gestori.submit.GestoreRichiestaIdGara" >
					<gene:gruppoCampi idProtezioni="GEN">
						<gene:campoScheda>
							<td colspan="2"><b>Dati generali della Gara</b></td>
						</gene:campoScheda>
						<gene:campoScheda campo="CODGARA" visibile="false" />
						<gene:campoScheda campo="OGGETTO" obbligatorio="true" />
						<gene:campoScheda campo="FLAG_ENTE_SPECIALE" obbligatorio="true" />
						<gene:campoScheda campo="ID_MODO_INDIZIONE" >
							<gene:checkCampoScheda funzione="controlloFlagEnteSpeciale();" obbligatorio="true" 
							messaggio="Il campo Modalita' di indizione della gara &egrave; obbligatorio se il settore della gara &egrave; di tipo speciale" onsubmit="true"/>
						</gene:campoScheda>
						<gene:campoScheda campo="TIPO_APP" obbligatorio="true" />
						
						<gene:campoScheda campo="IMPORTO_GARA" />
						<gene:archivio titolo="TECNICI"
							lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.W9GARA.RESPRO"),"gene/tecni/tecni-lista-popup.jsp","")}'
							scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
							schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
							campi="TECNI.CODTEC;TECNI.NOMTEC;TECNI.CFTEC" chiave="W9GARA_RUP" >
							<gene:campoScheda campo="RUP" defaultValue="${idTecnico}"
								visibile="false" />
							<gene:campoScheda campo="NOMTEC" entita="TECNI"
								defaultValue="${nomeTecnico}" where="TECNI.CODTEC=W9GARA.RUP"
								modificabile="${sessionScope.profiloUtente.abilitazioneStd eq 'A'}"
								title="RUP">
							</gene:campoScheda>
							<gene:campoScheda campo="CFTEC" entita="TECNI" defaultValue="${cfTecnico}" where="TECNI.CODTEC=W9GARA.RUP" modificabile="false" /> 
						</gene:archivio>
						<gene:campoScheda>
							<td colspan="2"><b>Stazione appaltante</b></td>
						</gene:campoScheda>
						<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
							scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
							schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
							campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="W9GARA_CODEIN" where=""
							formName="formStazApp">
							<gene:campoScheda campo="CODEIN" visibile="false"
								defaultValue="${sessionScope.uffint}" />
							<gene:campoScheda campo="NOMEIN" entita="UFFINT"
								defaultValue="${nomein}" where="UFFINT.CODEIN = W9GARA.CODEIN"
								modificabile="false" title="Stazione appaltante" />
						</gene:archivio>

						<gene:archivio titolo="Centri di Costo"
							lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.W9.W9GARA.IDCC"),"w9/centricosto/centricosto-lista-popup.jsp","")}'
							scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaCentri"),"w9/centricosto/centricosto-scheda.jsp","")}'
							schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaCentri"),"w9/centricosto/centricosto-scheda-popup.jsp","")}'
							where="CENTRICOSTO.CODEIN = '${sessionScope.uffint}'"
							campi="CENTRICOSTO.IDCENTRO;CENTRICOSTO.CODCENTRO;CENTRICOSTO.DENOMCENTRO"
							chiave="W9GARA_IDCC">
							<gene:campoScheda campo="IDCC" visibile="false" />
							<gene:campoScheda campo="CODCENTRO" entita="CENTRICOSTO"
								where="CENTRICOSTO.IDCENTRO=W9GARA.IDCC" obbligatorio="true"
								title="Codice del Centro di Costo" definizione="T40" />
							<gene:campoScheda campo="DENOMCENTRO" entita="CENTRICOSTO"
								where="CENTRICOSTO.IDCENTRO=W9GARA.IDCC" obbligatorio="true"
								title="Denominazione del Centro di Costo" definizione="T250" />
						</gene:archivio>
						<!--gene:campoScheda campo="FLAG_SA_AGENTE" /-->
						<!--gene:campoScheda campo="ID_TIPOLOGIA_SA" /-->
						<!--gene:campoScheda campo="DENOM_SA_AGENTE" /-->
						<!--gene:campoScheda campo="CF_SA_AGENTE" /-->
						<!--gene:campoScheda campo="TIPOLOGIA_PROCEDURA" /-->
						<!--gene:campoScheda campo="FLAG_CENTRALE_STIPULA" /-->					
						
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

					<gene:campoScheda>
						<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
					</gene:campoScheda>
				</gene:formScheda>

			</gene:pagina>
		</gene:formPagine>
	</gene:redefineInsert>

	<gene:javaScript>
		document.forms[0].jspPathTo.value = document.forms[0].jspPath.value; 
		
		function controlloFlagEnteSpeciale() {
			var flagEnteSpeciale = getValue("W9GARA_FLAG_ENTE_SPECIALE");
			var modoIndizione = getValue("W9GARA_ID_MODO_INDIZIONE");
			
			var result = true;
			if("S" == flagEnteSpeciale) {
				if("" == modoIndizione) {
					result = false;
				}
			}
			return result;
		}

	</gene:javaScript>
</gene:template>