
<%
	/*
	 * Created on 18-feb-2009
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

<tr>
	<td>
		<b>${titoloGenerico}</b>
		<br>
		Durante il controllo dei dati sono stati rilevati alcuni errori/avvisi:
		<br><br>
		&nbsp;
		<img width="20" height="20" title="Controllo bloccante: impedisce la validazione dei dati" alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>
		<i>&nbsp;numero totale degli errori bloccanti: ${numeroErrori}</i>
		<br>
		&nbsp;
		<img width="20" height="20" title="Avviso non bloccante: &egrave; una segnalazione che l'utente pu&ograve; anche ignorare" alt="Controllo non bloccante" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>
		<i>&nbsp;numero totale degli avvisi non bloccanti: ${numeroWarning}</i>
		<br>&nbsp;
	</td>
</tr>

<c:if test='${empty listaMessaggiDiControllo}'>
	<c:set var="listaMessaggiDiControllo" value="${listaGenericaControlli}" />
</c:if>

<tr>
	<td>
		<table id="listaerroriavvisi" class="datilista">
			<thead>
				<tr>
					<th width="22">
						&nbsp;
					</th>
					<th>
						Sezione
					</th>
					<th>
						Descrizione del campo e messaggio di controllo
					</th>				
				</tr>
			</thead>
			<tbody>
							
				<c:forEach items="${listaMessaggiDiControllo}" step="1" var="controllo" varStatus="status" >
					<c:if test="${status.index%2 == 0}">
						<tr class="even">
					</c:if>
					<c:if test="${status.index%2 == 1}">
						<tr class="odd">
					</c:if>
						
						<c:choose>
							<c:when test="${controllo.tipoMessaggio == 'T'}">
								<td colspan="3">
									<i>${controllo.pagina}</i>
								</td>
							</c:when>
							<c:otherwise>
								<td width="22">
									<c:choose>
										<c:when test="${controllo.tipoMessaggio == 'E'}">
											<img width="20" height="20" title="Controllo bloccante: impedisce la validazione dei dati" alt="Controllo bloccante" src="${pageContext.request.contextPath}/img/controllo_e.gif"/>	
										</c:when>
										<c:when test="${controllo.tipoMessaggio == 'W'}">
											<img width="20" height="20" title="Avviso non bloccante: &egrave; una segnalazione che l'utente pu&ograve; anche ignorare" alt="Controllo non bloccante" src="${pageContext.request.contextPath}/img/controllo_w.gif"/>	
										</c:when>
										<c:when test="${controllo.tipoMessaggio == 'I'}">
											<img width="20" height="20" title="Informazione" alt="Informazione" src="${pageContext.request.contextPath}/img/controllo_i.gif"/>	
										</c:when>
									</c:choose>
								</td>
								
								<c:set var="valueRow" value='1' />
								
								<c:choose>
									<c:when test="${! empty fn:trim(controllo.pagina)}">
										<c:choose>
											<c:when test="${controllo_1_precedente ne controllo.pagina}">
												<td>
													${controllo.pagina}											
												</td>
											</c:when>
											<c:otherwise>
												<td>
													&nbsp;
												</td>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:set var="valueRow" value='2' />
									</c:otherwise>
								</c:choose>
								
								<c:set var="controllo_1_precedente" value="${controllo.pagina}" />
								<td colspan="${valueRow}">
									<b>${controllo.descrizioneCampo}</b>
									<br>${controllo.messaggio}
								</td>	
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</td>
</tr>
