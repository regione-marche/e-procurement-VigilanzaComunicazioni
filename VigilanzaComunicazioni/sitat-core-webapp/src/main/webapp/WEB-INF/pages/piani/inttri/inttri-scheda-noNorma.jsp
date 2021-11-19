<%
  /*
   * Created on 18-ott-2007
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

<c:choose>
	<c:when test='${modo eq "NUOVO"}'>
		${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetNextConintFunction", pageContext, keyParent)}
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(keyParent,"CONTRI")};INTTRI.CONINT=N:${count}'
			scope="request" />
	</c:when>
	<c:otherwise>
		<c:set var="keyINTTRI"
			value='INTTRI.CONTRI=N:${gene:getValCampo(key,"CONTRI")};INTTRI.CONINT=N:${gene:getValCampo(key,"CONINT")}'
			scope="request" />
	</c:otherwise>
</c:choose>

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetCUPWSUserFunction" />
<c:set var="isIntegrazioneWsCUP" value='${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.IsIntegrazioneWsCUPFunction",  pageContext)}' />
<c:set var="esisteProfiloCup" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.EsisteProfiloFunction", pageContext, "PRO_CUP")}' scope="request" />
	
<gene:template file="scheda-template.jsp" gestisciProtezioni="true" schema="PIANI" idMaschera="INTTRI-scheda">
	<gene:redefineInsert name="corpo">
		<c:if test='${tipint eq 1}'>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"INTTRI")}' />
		</c:if>
		<c:if test='${tipint eq 2}'>
			<gene:setString name="titoloMaschera" value='${gene:callFunction2("it.eldasoft.sil.pt.tags.funzioni.GetTitleFunction",pageContext,"INTTRIFS")}' />
		</c:if>

	<gene:redefineInsert name="head">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?v=${sessionScope.versioneModuloAttivo}"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery/cpvvp/jquery.cpvvp.mod.css" >
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.nuts.mod.js?v=${sessionScope.versioneModuloAttivo}"></script>
	</gene:redefineInsert>

		<gene:formScheda entita="INTTRI" gestisciProtezioni="true"
			gestore="it.eldasoft.sil.pt.tags.gestori.submit.GestoreINTTRI"
			plugin="it.eldasoft.sil.pt.tags.gestori.plugin.GestoreINTTRI">
			<gene:gruppoCampi idProtezioni="GEN">
				<gene:campoScheda>
					<td colspan="2"><b>Dati generali</b></td>
				</gene:campoScheda>
				<gene:campoScheda campo="TIPINT" defaultValue="${tipint}" visibile="false"/>
				<gene:campoScheda campo="CONINT" visibile="false" value='${gene:getValCampo(keyINTTRI,"CONINT")}' />
				<gene:campoScheda campo="CODINT" />
				<gene:campoScheda campo="CONTRI" defaultValue='${gene:getValCampo(keyParent,"CONTRI")}'	visibile="false" />
				<gene:campoScheda campo="DESINT" obbligatorio="true" />
				<gene:archivio titolo="TECNICI"
					lista='${gene:if(gene:checkProt( pageContext,"COLS.MOD.PIANI.INTTRI.CODRUP"),"gene/tecni/tecni-lista-popup.jsp","")}'
					scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
					schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
					campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="INTTRI_CODRUP" >
					<gene:campoScheda campo="CODRUP" visibile="false" />
					<gene:campoScheda campo="NOMTEC" campoFittizio="true" title="RUP"
						value="${nomeTecnico}" definizione="T61;0;;;" />
				</gene:archivio>
				<c:if test='${tipint eq 1}'>
					<gene:campoScheda campo="NPROGINT" defaultValue="${nprogint}" visibile="false"/>
					<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownFunction"	parametro="INTTRI" />
					<gene:campoScheda campo="ANNRIF" obbligatorio="true" title="Annualità di riferimento dell'intervento" defaultValue="1" >
						<gene:addValue value="" descr="" />
						<gene:addValue value="1" descr="1" />
						<gene:addValue value="2" descr="2" />
						<gene:addValue value="3" descr="3" />
					</gene:campoScheda>
					<gene:campoScheda campo="SEZINT" definizione="T100;0;PTj00" >
						<c:if test='${!empty listaValoriSezint}'>
							<c:forEach items="${listaValoriSezint}" var="valoriSezint">
								<gene:addValue value="${valoriSezint[0]}"
									descr="${valoriSezint[0]} ${valoriSezint[1]}" />
							</c:forEach>
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="INTERV" definizione="T100;0;PTj00" >
						<c:if test='${!empty listaValoriInterv}'>
							<c:forEach items="${listaValoriInterv}" var="valoriInterv">
								<gene:addValue value="${valoriInterv[0]}${valoriInterv[1]}"
									descr="${valoriInterv[0]} ${valoriInterv[1]} ${valoriInterv[2]}" />
							</c:forEach>
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="CATINT" value="${valoreCatint}" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreCampoCATINT" />
					<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false" 
						lista="gene/commons/istat-comuni-lista-popup.jsp" scheda=""
						schedaPopUp="" 
						campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3">
						<gene:campoScheda campo="PROVINCIA_ESECUZIONE" campoFittizio="true" definizione="T2;0;Agx15;;S3COPROVIN"
							title="Provincia luogo di esecuzione del contratto" value="${provincia}" />
						<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true" definizione="T120"	title="Comune luogo di esecuzione del contratto" value="${comune}" />
						<gene:campoScheda campo="COMINT" title="Codice ISTAT del Comune" />
					</gene:archivio>

					<gene:campoScheda campo="NUTS" href="#" modificabile="false" speciale="true" >
						<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
					</gene:campoScheda>
		
					<gene:campoScheda campo="CUPMST" visibile="false"/>
						
					<gene:campoScheda campo="CUPPRG" title="Codice CUP di progetto (assegnato da CIPE)" speciale="true">
	  	 			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" }'>
							<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
		 				</c:if>
    			</gene:campoScheda>
    			<gene:campoScheda campo="PRIMANN" /> 
    			<gene:campoScheda campo="FLAG_CUP" defaultValue="2"/>
					
    	
			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" }'>
				<gene:campoScheda>	
					<td colspan="2"><b>Credenziali per il servizio CUP</b></td>
				</gene:campoScheda>
				<c:choose>
					<c:when test="${empty cupwsuser}">
						<gene:campoScheda>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" />
								<input type="password" name="cupwspass" id="cupwspass" size="15" onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();"/>
							</td>
						</gene:campoScheda>
						<input type="hidden" name="recuperauser" value="0" />
						<input type="hidden" name="recuperapassword" value="0" />										
					</c:when>
					<c:otherwise>
						<gene:campoScheda>	
							<td class="etichetta-dato">Utente/Password</td>
							<td class="valore-dato">
								<input type="text" name="cupwsuser" id="cupwsuser" size="15" value="${cupwsuser}" onchange="javascript:resetRecuperaUser();"/>
								<input type="password" name="cupwspass" id="cupwspass" size="15" value="................"  onfocus="javascript:resetPassword();" onclick="javascript:resetPassword();" onchange="javascript:resetRecuperaPassword();"/>
							</td>
						</gene:campoScheda>
						<input type="hidden" name="recuperauser" value="1" />
						<input type="hidden" name="recuperapassword" value="1" />	
					</c:otherwise>
				</c:choose>
				<gene:campoScheda>	
					<td class="etichetta-dato">Memorizza le credenziali</td>
						<td class="valore-dato">
								<input type="checkbox" name="memorizza" value="memorizza" checked="checked"/>					
						</td>
				</gene:campoScheda>
    	</c:if>
        
					<gene:campoScheda campo="CODCPV" href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' modificabile="false" speciale="true">
						<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
							<gene:popupCampo titolo="Dettaglio codice CODCPV"	href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' />
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}"	href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})'
						campoFittizio="true" modificabile="false" definizione="T150" />
					<gene:campoScheda nome="previsto_allegato">
						<td class="etichetta-dato"></td>
						<td>&nbsp;&nbsp;&nbsp;Previsto dall'ALLEGATO IIA o IIB del
						DLgs. 163/06</td>
					</gene:campoScheda>
					<gene:campoScheda campo="TIPOIN" visibile="false" defaultValue="L" title="Tipologia dell'intervento" />
					<gene:campoScheda campo="ANNINT" />
					<gene:campoScheda campo="MANTRI" defaultValue="1" visibile="false" />
					<gene:campoScheda>
					<td colspan="2"><b>Dati elenco annuale</b></td>
					</gene:campoScheda>
					<gene:campoScheda campo="FIINTR" definizione="T100;0;PTj00" >
						<c:if test='${!empty listaValoriFiintr}'>
							<c:forEach items="${listaValoriFiintr}" var="valoriFiintr">
								<gene:addValue value="${valoriFiintr[0]}"
									descr="${valoriFiintr[0]} ${valoriFiintr[1]}" />
							</c:forEach>
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="PRGINT" title="Priorit&agrave; dell'intervento in generale"/>
					<gene:campoScheda campo="APCINT" />
					<gene:campoScheda campo="URCINT" />
					<gene:campoScheda campo="STAPRO" definizione="T100;0;PTj00" >
						<c:if test='${!empty listaValoriStapro}'>
							<c:forEach items="${listaValoriStapro}" var="valoriStapro">
								<gene:addValue value="${valoriStapro[0]}"
									descr="${valoriStapro[0]} ${valoriStapro[1]}" />
							</c:forEach>
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="AILINT" />
					<gene:campoScheda campo="TILINT">
						<gene:addValue value="" descr="" />
						<gene:addValue value="1" descr="1" />
						<gene:addValue value="2" descr="2" />
						<gene:addValue value="3" descr="3" />
						<gene:addValue value="4" descr="4" />
					</gene:campoScheda>
					<gene:campoScheda campo="AFLINT" />
					<gene:campoScheda campo="TFLINT">
						<gene:addValue value="" descr="" />
						<gene:addValue value="1" descr="1" />
						<gene:addValue value="2" descr="2" />
						<gene:addValue value="3" descr="3" />
						<gene:addValue value="4" descr="4" />
					</gene:campoScheda>
					
					<gene:campoScheda addTr="false">
						<tr>
						<td colspan="2">
						<table class="griglia">
							<tr>
								<td colspan="7"><b>Quadro economico</b></td>
							</tr>
							<tr>
								<td class="etichetta-dato">Tipologie Risorse</td>
								<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria primo anno</td>
								<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria secondo anno</td>
								<td class="etichetta-dato" colspan="2" >Disponibilit&agrave; finanziaria terzo anno</td>
							</tr>
							<tr>
								<td class="etichetta-dato">Entrate aventi destinazione vincolata per legge</td>
								</gene:campoScheda>
								<gene:campoScheda campo="DV1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="DV2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="DV3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato">Entrate acquisite mediante contrazione di mutuo</td>
								</gene:campoScheda>
								<gene:campoScheda campo="MU1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="MU2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="MU3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato">Trasferimento immobili art.53 commi 6-7 d.lgs.n. 163/2006</td>
								</gene:campoScheda>
								<gene:campoScheda campo="IM1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="IM2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="IM3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato">Stanziamenti di bilancio</td>
								</gene:campoScheda>
								<gene:campoScheda campo="BI1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="BI2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="BI3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato">Altre risorse disponibili</td>
								</gene:campoScheda>
								<gene:campoScheda campo="AL1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="AL2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="AL3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato"><b>Importo disponibilit&agrave; finanziaria al netto di capitali privati</b></td>
								</gene:campoScheda>
									<gene:campoScheda campo="DI1INT" modificabile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI1TRI#","#INTTRI_DV1TRI#","#INTTRI_IM1TRI#","#INTTRI_MU1TRI#","#INTTRI_AL1TRI#")))'
										elencocampi="INTTRI_BI1TRI;INTTRI_DV1TRI;INTTRI_IM1TRI;INTTRI_MU1TRI;INTTRI_AL1TRI" />
									</gene:campoScheda>
								
								
									<gene:campoScheda campo="DI2INT" modificabile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI2TRI#","#INTTRI_DV2TRI#","#INTTRI_IM2TRI#","#INTTRI_MU2TRI#","#INTTRI_AL2TRI#")))'
										elencocampi="INTTRI_BI2TRI;INTTRI_DV2TRI;INTTRI_IM2TRI;INTTRI_MU2TRI;INTTRI_AL2TRI" />
									</gene:campoScheda>
								
								
									<gene:campoScheda campo="DI3INT" modificabile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda 
										funzione='toMoney(sommaCampi(new Array("#INTTRI_BI3TRI#","#INTTRI_DV3TRI#","#INTTRI_IM3TRI#","#INTTRI_MU3TRI#","#INTTRI_AL3TRI#")))'
										elencocampi="INTTRI_BI3TRI;INTTRI_DV3TRI;INTTRI_IM3TRI;INTTRI_MU3TRI;INTTRI_AL3TRI" />
									</gene:campoScheda>
									
									<gene:campoScheda campo="DITINT" modificabile="false" visibile="false" >
										<gene:calcoloCampoScheda
										funzione='toMoney(sommaCampi(new Array("#INTTRI_DI1INT#","#INTTRI_DI2INT#","#INTTRI_DI3INT#")))'
										elencocampi="INTTRI_DI1INT;INTTRI_DI2INT;INTTRI_DI3INT" />
									</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							<tr>
								<td class="etichetta-dato">Entrate acquisite mediante apporti di capitale privato</td>
								</gene:campoScheda>
								<gene:campoScheda campo="PR1TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="PR2TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="PR3TRI" addTr="false" hideTitle="true"/>
								<gene:campoScheda campo="ICPINT" modificabile="false" visibile="false" >
									<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#INTTRI_PR1TRI#","#INTTRI_PR2TRI#","#INTTRI_PR3TRI#")))'
									elencocampi="INTTRI_PR1TRI;INTTRI_PR2TRI;INTTRI_PR3TRI" />
								</gene:campoScheda>
								<gene:campoScheda addTr="false">
							</tr>
							
							<tr>
								<td class="etichetta-dato"><b>Totale disponibilit&agrave; finanziaria</b></td>
								</gene:campoScheda>
								
								<gene:campoScheda campo="DF1TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 1° anno" 
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("DF1TRI",toMoney(sommaCampi(new Array("#INTTRI_DI1INT#","#INTTRI_PR1TRI#"))))' elencocampi="INTTRI_DI1INT;INTTRI_PR1TRI" esegui="true"/>
								
								<gene:campoScheda campo="DF2TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 2° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("DF2TRI",toMoney(sommaCampi(new Array("#INTTRI_DI2INT#","#INTTRI_PR2TRI#"))))' elencocampi="INTTRI_DI2INT;INTTRI_PR2TRI" esegui="true"/>
								
								<gene:campoScheda campo="DF3TRI" campoFittizio="true" definizione="F24.5;0;;MONEY;" title="Totale disponibilit&agrave; finanziaria 3° anno"  
									modificabile="false" addTr="false" hideTitle="true">
								</gene:campoScheda>
								<gene:fnJavaScriptScheda funzione='setValue("DF3TRI",toMoney(sommaCampi(new Array("#INTTRI_DI3INT#","#INTTRI_PR3TRI#"))))' elencocampi="INTTRI_DI3INT;INTTRI_PR3TRI" esegui="true"/>
																
								<gene:campoScheda addTr="false">
							</tr>
							
							<tr>
								<td class="etichetta-dato" colspan="5"><b>Importo totale dell'intervento</b></td>
								</gene:campoScheda>
								<gene:campoScheda campo="TOTINT" modificabile="false" addTr="false" hideTitle="true">
									<gene:calcoloCampoScheda
									funzione='toMoney(sommaCampi(new Array("#INTTRI_DITINT#","#INTTRI_ICPINT#")))'
									elencocampi="INTTRI_DITINT;INTTRI_ICPINT" />
							</tr>							
						</table>
						</td>
						</tr>
					</gene:campoScheda>

					<gene:campoScheda campo="TCPINT" definizione="T100;0;PTj00">
						<c:if test='${!empty listaValoriTcpint}'>
							<c:forEach items="${listaValoriTcpint}" var="valoriTcpint">
								<gene:addValue value="${valoriTcpint[0]}"	descr="${valoriTcpint[0]} ${valoriTcpint[1]}" />
							</c:forEach>
						</c:if>
					</gene:campoScheda>

					<jsp:include page="immobili_da_trasferire.jsp">
						<jsp:param name="keyInttri" value="${keyINTTRI}" />
					</jsp:include>
				</c:if>
				<!--  intervento per forniture o servizi -->
				<c:if test='${tipint eq 2}'>
				
					<gene:gruppoCampi idProtezioni="FS_ISTAT_NUTS">
						<gene:archivio titolo="Comuni" chiave="" where="" formName="" inseribile="false"
							lista="gene/commons/istat-comuni-lista-popup.jsp" 
							scheda=""
							schedaPopUp=""
							campi="TB1.TABCOD3;TABSCHE.TABDESC;TABSCHE.TABCOD3"	>
							<gene:campoScheda campo="PROVINCIA_ESECUZIONE"	campoFittizio="true" definizione="T2;0;Agx15;;S3COPROVIN"
							title="Provincia luogo di esecuzione del contratto"	value="${provincia}" />
							<gene:campoScheda campo="COMUNE_ESECUZIONE" campoFittizio="true" definizione="T120"
							title="Comune luogo di esecuzione del contratto" value="${comune}" />
							<gene:campoScheda campo="COMINT" title="Codice ISTAT del Comune" />
						</gene:archivio>

						<gene:campoScheda campo="NUTS" href="#" modificabile="false" speciale="true" >
							<gene:popupCampo titolo="Dettaglio codice NUTS" href="#" />
						</gene:campoScheda>
					</gene:gruppoCampi>
					
					<gene:campoScheda campo="CUPMST" visibile="false"/>
						
					<gene:campoScheda campo="CUPPRG" title="Codice CUP di progetto (assegnato da CIPE)" speciale="true">
	  	 			<c:if test='${modoAperturaScheda ne "VISUALIZZA" and isIntegrazioneWsCUP eq "true" }'>
							<gene:popupCampo titolo="Ricerca/verifica codice CUP"	href='javascript:richiestaListaCUP()' />
		 				</c:if>
    			</gene:campoScheda>
    			<gene:campoScheda campo="FLAG_CUP" defaultValue="1"/>
        
					<gene:campoScheda campo="CODCPV" href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})'
						modificabile="false" speciale="true">
						<c:if test='${modoAperturaScheda ne "VISUALIZZA"}'>
							<gene:popupCampo titolo="Dettaglio codice CODCPV"	href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' />
						</c:if>
					</gene:campoScheda>
					<gene:campoScheda campo="CPVDESC" title="Descrizione CPV" value="${cpvdescr}" modificabile="false" definizione="T150" 
						href='javascript:formCPV(${modoAperturaScheda ne "VISUALIZZA"})' campoFittizio="true" />
					<gene:campoScheda nome="previsto_allegato">
						<td class="etichetta-dato"></td>
						<td>&nbsp;&nbsp;&nbsp;Previsto dall'ALLEGATO IIA o IIB del DLgs. 163/06</td>
					</gene:campoScheda>
					<gene:campoScheda campo="ANNRIF" defaultValue="1" visibile="false" />
					<gene:campoScheda campo="ANNINT" defaultValue="1" visibile="false" />
					<gene:campoScheda campo="TIPOIN" gestore="it.eldasoft.sil.pt.tags.gestori.decoratori.GestoreTipoinAnnuale" />
					<gene:gruppoCampi idProtezioni="FS_ALTRI_DATI">
						<gene:campoScheda>
							<td colspan="2"><b>Altri dati</b></td>
						</gene:campoScheda>
						<gene:campoScheda campo="PRGINT" />
						<gene:campoScheda campo="MESEIN" />
						<gene:campoScheda campo="NORRIF" />
						<gene:campoScheda campo="STRUPR" />
						<gene:campoScheda campo="MANTRI" />
					</gene:gruppoCampi>
					<!-- il valore di MANTRI viene valorizzato con la funzione conferma() contenuta in descriz-codice-cpv.jsp;
					 al momento della chiamata di tale jsp, il parametro campoRange viene valorizzato con INTTRI_MANTRI
					 è stata effettuata questa scelta perchè lo stesso controllo viene effettuato anche nella scheda multipla
					 dei lotti (cpv_secondario.jsp) ma in quest'ultimo caso bisogna valorizzare un altro campo (W9LOTT_MANOD)-->
					 <gene:campoScheda>
						<td colspan="2"><b>Fonti delle risorse finanziarie e importo</b></td>
					</gene:campoScheda>
					<gene:campoScheda campo="IMPRFS" />
					<gene:campoScheda campo="RG1TRI" />
					<gene:campoScheda campo="MU1TRI" title="Risorse acquisite mediante contrazioni di mutuo"/>
					<gene:campoScheda campo="PR1TRI" title="Risorse acquisite mediante apporti di capitali privati"/>
					<gene:campoScheda campo="BI1TRI" title="Stanziamenti di bilancio" />
					<gene:campoScheda campo="AL1TRI" title="Altre risorse disponibili" />
					<gene:campoScheda campo="DI1INT" modificabile="false"	title="Totale importo presunto">
						<gene:calcoloCampoScheda funzione='toMoney(sommaCampi(new Array("#INTTRI_AL1TRI#","#INTTRI_BI1TRI#","#INTTRI_RG1TRI#","#INTTRI_IMPRFS#","#INTTRI_MU1TRI#","#INTTRI_PR1TRI#")))'
							elencocampi="INTTRI_AL1TRI;INTTRI_BI1TRI;INTTRI_RG1TRI;INTTRI_IMPRFS;INTTRI_MU1TRI;INTTRI_PR1TRI" />
					</gene:campoScheda>
					<gene:campoScheda campo="DITINT" visibile="false">
						<gene:calcoloCampoScheda funzione='toMoney(sommaCampi(new Array("#INTTRI_AL1TRI#","#INTTRI_BI1TRI#","#INTTRI_RG1TRI#","#INTTRI_IMPRFS#","#INTTRI_MU1TRI#","#INTTRI_PR1TRI#")))'
							elencocampi="INTTRI_AL1TRI;INTTRI_BI1TRI;INTTRI_RG1TRI;INTTRI_IMPRFS;INTTRI_MU1TRI;INTTRI_PR1TRI" />
					</gene:campoScheda>
				</c:if>
				

			</gene:gruppoCampi>
			<gene:fnJavaScriptScheda funzione='modifyANNRIF("#INTTRI_ANNRIF#")'	elencocampi="INTTRI_ANNRIF" esegui="true" />
			<gene:fnJavaScriptScheda funzione='modifyFLAG_CUP("#INTTRI_FLAG_CUP#")'	elencocampi="INTTRI_FLAG_CUP" esegui="true" />
			
			<gene:fnJavaScriptScheda funzione='modifyANNINT("#INTTRI_ANNINT#")'	elencocampi="INTTRI_ANNINT" esegui="false" />
			<gene:fnJavaScriptScheda funzione='visualizzaEtichetta(${gene:callFunction("it.eldasoft.sil.pt.tags.funzioni.VerificaRangeCpvFunction",codcpv)})'
				elencocampi="INTTRI_ANNINT" esegui="true" />
			<gene:fnJavaScriptScheda funzione='modInterv("#INTTRI_INTERV#")' elencocampi="INTTRI_INTERV" esegui="false" />
			<gene:fnJavaScriptScheda funzione='modCatint("#INTTRI_INTERV#","#INTTRI_CATINT#")' elencocampi="INTTRI_CATINT" esegui="false" />

			<jsp:include page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
				<jsp:param name="entitaParent" value="INTTRI" />
			</jsp:include>

			<gene:campoScheda>
				<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
			</gene:campoScheda>
		</gene:formScheda>
		<gene:javaScript>
		
			var arrayC2006=new Array();

			<c:forEach items="${listaValoriCatint}" var="valore">
				arrayC2006.push(new DatoTabellato(${gene:string4Js(valore[0])}, ${gene:string4Js(valore[1])},${gene:string4Js(valore[2])}) );
			</c:forEach>

			
			function sommaCampi(valori)	{
			  var i, ret=0;
			  for(i=0;i < valori.length;i++){
			  	if(valori[i] != ""){
			    	ret += eval(valori[i]);
			  	}
			  }
			  return eval(ret).toFixed(2);
	 		}
	 		
			function modifyANNRIF(valore) {
				var vis = (valore == "1");
				if (vis) {
				  setValue("INTTRI_ANNINT","1");
				  modifyANNINT(1);
				} else {
					setValue("INTTRI_ANNINT","2");
				  modifyANNINT(2);
				}
			}
			
			function modifyFLAG_CUP(valore) {
				var vis = (valore == "1");
				if (vis) {
					setValue("INTTRI_CUPPRG","");
				}
				showObj("rowINTTRI_CUPPRG",!vis);
			}
			
			function modifyANNINT(valore) {
				var vis = (valore == 1);
				showObj("rowINTTRI_FIINTR",vis);
				showObj("rowINTTRI_APCINT",vis);
				showObj("rowINTTRI_URCINT",vis);
				showObj("rowINTTRI_STAPRO",vis);
				showObj("rowINTTRI_AILINT",vis);
				showObj("rowINTTRI_TILINT",vis);
				showObj("rowINTTRI_AFLINT",vis);
				showObj("rowINTTRI_TFLINT",vis);
				if (!vis) {
					setValue("INTTRI_FIINTR","");
					setValue("INTTRI_APCINT","");
					setValue("INTTRI_URCINT","");
					setValue("INTTRI_STAPRO","");
					setValue("INTTRI_AILINT","");
					setValue("INTTRI_TILINT","");
					setValue("INTTRI_AFLINT","");
					setValue("INTTRI_TFLINT","");
				}
			}
				
			function formCPV(modifica){
				// Eseguo l'apertura della maschera con il dettaglio delle categoria dell'intervento
		<c:choose>
			<c:when test='${tipint eq 1}'>
				openPopUpCustom("href=commons/descriz-codice-cpv.jsp&key=" + document.forms[0].key.value + "&keyParent=" + document.forms[0].keyParent.value + "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")+"&campo=INTTRI_CODCPV&campoRange=&campoDescr=CPVDESC&campoEtichetta=previsto_allegato&valore="+ (getValue("INTTRI_CODCPV") == "" ? "45000000-7" : getValue("INTTRI_CODCPV"))+"&valoreDescr="+ (getValue("CPVDESC") == "" ? "Lavori di costruzione" : getValue("CPVDESC")), "formCPV", 700, 300, 1, 1);
			</c:when>
			<c:otherwise>
				openPopUpCustom("href=commons/descriz-codice-cpv.jsp&key=" + document.forms[0].key.value + "&keyParent=" + document.forms[0].keyParent.value + "&modo="+(modifica ? "MODIFICA":"VISUALIZZA")+"&campo=INTTRI_CODCPV&campoRange=INTTRI_MANTRI&campoDescr=CPVDESC&campoEtichetta=previsto_allegato&valore="+ (getValue("INTTRI_CODCPV") == "" ? "45000000-7" : getValue("INTTRI_CODCPV"))+"&valoreDescr="+ (getValue("CPVDESC") == "" ? "Lavori di costruzione" : getValue("CPVDESC")), "formCPV", 700, 300, 1, 1);
			</c:otherwise>
		</c:choose>
			}
			
			function visualizzaEtichetta(visualizza){
				if(visualizza != '' && visualizza=='true') {
					showObj("rowprevisto_allegato", true);
				} else {
					showObj("rowprevisto_allegato", false);
				}
			}
			
			function modInterv(valore){
				setValue("INTTRI_CATINT","");
				caricaCatint(valore);
			}
			
			function caricaCatint(codice){
				var array = filtraDati(arrayC2006,codice);
				var i;
				var opt=document.forms[0].INTTRI_CATINT.options;
				opt.length = 0;
				opt.add(new Option("", ""));
				for(i=0; i < array.length ;i++){
					opt.add(new Option(array[i].cod1+" "+array[i].descr, array[i].cod+"-"+array[i].cod1));
				}
			}
			
			function DatoTabellato(aCod, aCod1, aDescr ) {
				var cod;
				var cod1;
				var descr;
				this.cod = aCod;
				this.cod1 = aCod1;
				this.descr = aDescr;
			}
				
			function filtraDati(arrayDati, codice){
				var lArray = new Array();
				var i;
				var obj;
				for(i=0;i < arrayDati.length;i++){
					obj = arrayDati[i];
					if(codice == '') {
						lArray.push(obj);
					} else {
						if(obj.cod == codice){
							lArray.push(obj);
						}
					}
				}
				return lArray;
			}
			
			function modCatint(valoreInterv, valoreCatint){
				if(valoreInterv=='' && valoreCatint!=''){
					var valoreCatintSplit = valoreCatint.split("-");
					setValue("INTTRI_INTERV", valoreCatintSplit[0]);
					setValue("INTTRI_CATINT", valoreCatint);
				}
			}
			
			function gestioneCUP() {
				<c:if test='${modo ne "NUOVO"}'>
					location.href = "${pageContext.request.contextPath}/piani/RedirectCUP.do?" + csrfToken + "&contri=" + ${gene:getValCampo(key,"CONTRI")} + "&conint=" + ${gene:getValCampo(key,"CONINT")};
				</c:if>
			}
			
			function richiestaListaCUP() {
				openPopUpCustom("href=piani/cupdati/cupdati-popup-richiesta-lista.jsp?campoCUP=INTTRI_CUPPRG&codiceCUP=" + getValue("INTTRI_CUPPRG"), "listaCUP", 900, 550, 1, 1);
			}
			
			function resetRecuperaUser() {
				document.forms[0].recuperauser.value = "0";
			}
	
			function resetRecuperaPassword() {
				document.forms[0].recuperapassword.value = "0";
			}
	
			function resetPassword() {
				document.forms[0].cupwspass.value = "";
			}	

	$(window).ready(function () {
		var _contextPath="${pageContext.request.contextPath}";
		myjstreecpvvp.init([_contextPath]);

		_creaFinestraAlberoNUTS();
		_creaLinkAlberoNUTS($("#INTTRI_NUTS").parent(), "${modo}", $("#INTTRI_NUTS"), $("#W9LOTT_LUOGO_NUTSview") );

		$("input[name^='INTTRI_NUTS']").attr('readonly','readonly');
		$("input[name^='INTTRI_NUTS']").attr('tabindex','-1');
		$("input[name^='INTTRI_NUTS']").css('border-width','1px');
		$("input[name^='INTTRI_NUTS']").css('background-color','#E0E0E0');
	});
				
		</gene:javaScript>
	</gene:redefineInsert>
	<c:if test='${tipint ne 2}'>
		<gene:redefineInsert name="addToDocumenti">
			<jsp:include page="../commons/addtodocumenti-gestione-cup.jsp" />
		</gene:redefineInsert>
	</c:if>
</gene:template>