<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />
	
<c:if test="${param.entita eq 'W3GARA'}">
	<c:set var="stato_simog_gara" value='${gene:callFunction2("it.eldasoft.sil.w3.tags.funzioni.GetStatoSimogW3GaraFunction",pageContext,numgara)}' />
	<c:if test="${stato_simog_gara eq '1' or stato_simog_gara eq '3'}">
		<tr>
			<c:choose>
				<c:when test='${isNavigazioneDisattiva ne "1"}'>
					<td class="vocemenulaterale">
						<a href="javascript:popupValidazioneIDGARA('${numgara}');" title="Controlla dati inseriti" tabindex="1512">
							Controlla dati
						</a>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Controlla dati
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:if>

	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W3.richiesteSIMOG")}'>
		<c:if test="${stato_simog_gara eq '1'}">
			<tr>
				<c:choose>
					<c:when test='${isNavigazioneDisattiva ne "1"}'>
						<td class="vocemenulaterale">
							<a href="javascript:popupRichiestaIDGARA('${numgara}');" title="Invia, a SIMOG, richiesta di assegnazione del numero gara" tabindex="1513">
								Invia richiesta numero gara
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							Invia richiesta numero gara
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:if>
	
		<c:if test="${stato_simog_gara eq '3'}">
			<tr>
				<c:choose>
			    <c:when test='${isNavigazioneDisattiva ne "1"}'>
			      <td class="vocemenulaterale">
						  <a href="javascript:popupModificaIDGARA('${numgara}');" title="Invia, a SIMOG, richiesta di modifica gara" tabindex="1513">
							  Invia richiesta modifica
							</a>
					  </td>
			    </c:when>
				  <c:otherwise>
				    <td>
						  Invia richiesta modifica
					  </td>
				  </c:otherwise>
				</c:choose>
			</tr>
		</c:if>

		<c:if test="${stato_simog_gara eq '2' or stato_simog_gara eq '3' or stato_simog_gara eq '4' or stato_simog_gara eq '5'}">
			<tr>
				<c:choose>
			    <c:when test='${isNavigazioneDisattiva ne "1"}'>
			      <td class="vocemenulaterale">
						  <a href="javascript:popupCancellaIDGARA('${numgara}');" title="Invia, a SIMOG, richiesta di cancellazione gara" tabindex="1513">
							  Invia richiesta cancellazione
						  </a>
					  </td>
			    </c:when>
				  <c:otherwise>
				    <td>
						  Invia richiesta cancellazione
					  </td>
				  </c:otherwise>
				</c:choose>
			</tr>
		</c:if>
	</c:if>
</c:if>

<c:if test="${param.entita eq 'W3LOTT'}">
	<c:set var="stato_simog_gara" value='${gene:callFunction2("it.eldasoft.sil.w3.tags.funzioni.GetStatoSimogW3GaraFunction",pageContext,numgara)}' />	
	<c:set var="stato_simog_lotto" value='${gene:callFunction3("it.eldasoft.sil.w3.tags.funzioni.GetStatoSimogW3LottFunction",pageContext,numgara,numlott)}' />
	
	<c:if test="${stato_simog_lotto eq '1' or stato_simog_lotto eq '3'}">
		<tr>
			<c:choose>
				<c:when test='${isNavigazioneDisattiva ne "1"}'>
					<td class="vocemenulaterale">
						<a href="javascript:popupValidazioneCIG('${numgara}','${numlott}');" title="Controlla dati inseriti" tabindex="1512">
							Controlla dati
						</a>
				  </td>
				</c:when>
				<c:otherwise>
					<td>
					  Controlla dati
				  </td>
			  </c:otherwise>
			</c:choose>
		</tr>
	</c:if>

	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W3.richiesteSIMOG")}'>
		<c:if test="${stato_simog_gara eq '2' or stato_simog_gara eq '3' or stato_simog_gara eq '4' or stato_simog_gara eq '99'}">
			<c:if test="${stato_simog_lotto eq '1'}">
				<tr>
					<c:choose>
						<c:when test='${isNavigazioneDisattiva ne "1"}'>
							<td class="vocemenulaterale">
								<a href="javascript:popupRichiestaCIG('${numgara}','${numlott}');" title="Invia, a SIMOG, richiesta di assegnazione CIG" tabindex="1513">
									Invia richiesta CIG
								</a>
						  </td>
						</c:when>
					  <c:otherwise>
							<td>
								Invia richiesta CIG
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
			
			<c:if test="${stato_simog_lotto eq '3'}">
				<tr>
					<c:choose>
						<c:when test='${isNavigazioneDisattiva ne "1"}'>
							<td class="vocemenulaterale">
								<a href="javascript:popupModificaCIG('${numgara}','${numlott}');" title="Invia, a SIMOG, richiesta di modifica lotto" tabindex="1513">
									Invia richiesta modifica
								</a>
						  </td>
						</c:when>
						<c:otherwise>
							<td>
								Invia richiesta modifica
						  </td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
			
			<c:if test="${stato_simog_lotto eq '2' or stato_simog_lotto eq '3' or stato_simog_lotto eq '4' or stato_simog_lotto eq '5'}">
				<tr>
					<c:choose>
				    <c:when test='${isNavigazioneDisattiva ne "1"}'>
				      <td class="vocemenulaterale">
								<a href="javascript:popupCancellaCIG('${numgara}','${numlott}');" title="Invia, a SIMOG, richiesta di cancellazione lotto" tabindex="1513">
									Invia richiesta cancellazione
								</a>
						  </td>
				    </c:when>
					  <c:otherwise>
					    <td>
							  Invia richiesta cancellazione
						  </td>
					  </c:otherwise>
					</c:choose>
				</tr>
			</c:if>
		</c:if>
	</c:if>
</c:if>

<c:if test="${param.entita eq 'W3SMARTCIG'}">	
	<c:set var="stato_simog_gara" value='${param.stato}' />
	
	<c:if test="${stato_simog_gara eq '1' or stato_simog_gara eq '3'}">	
		<tr>
			<c:choose>
				<c:when test='${isNavigazioneDisattiva ne "1"}'>
					<td class="vocemenulaterale">
						<a href="javascript:popupValidazioneSMARTCIG('${codrich}');" title="Controlla dati inseriti" tabindex="1512">
							Controlla dati
						</a>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Controlla dati
					</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:if>

	<c:if test='${gene:checkProt(pageContext, "FUNZ.VIS.ALT.W3.richiesteSIMOG")}'>
		<c:if test="${stato_simog_gara eq '1'}">
			<tr>
				<c:choose>
					<c:when test='${isNavigazioneDisattiva ne "1"}'>
						<td class="vocemenulaterale">
							<a href="javascript:popupRichiestaSMARTCIG('${codrich}');" title="Invia, a SIMOG, richiesta di assegnazione SMARTCIG" tabindex="1513">
								Invia richiesta SMARTCIG
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							Invia richiesta SMARTCIG
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:if>

		<c:if test="${stato_simog_gara eq '2' or stato_simog_gara eq '3' or stato_simog_gara eq '4' or stato_simog_gara eq '5'}">
			<tr>
				<c:choose>
					<c:when test='${isNavigazioneDisattiva ne "1"}'>
						<td class="vocemenulaterale">
							<a href="javascript:popupAnnullaSMARTCIG('${codrich}');" title="Invia, a SIMOG, richiesta di annullamento SMARTCIG" tabindex="1513">
								Invia richiesta annullamento
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							Invia richiesta annullamento
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<c:choose>
					<c:when test='${isNavigazioneDisattiva ne "1"}'>
						<td class="vocemenulaterale">
							<a href="javascript:popupRichiestaSMARTCIG('${codrich}');" title="Invia, a SIMOG, richiesta di modifica SMARTCIG" tabindex="1514">
								Invia modifica SMARTCIG
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							Invia modifica SMARTCIG
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:if>
	</c:if>
</c:if>
