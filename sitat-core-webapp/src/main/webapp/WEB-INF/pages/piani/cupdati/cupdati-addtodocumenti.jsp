<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />

	<tr>
		<c:choose>
	        <c:when test='${isNavigazioneDisattiva ne "1"}'>
	          <td class="vocemenulaterale">
				<a href="javascript:popupValidazioneCUP('${id}');" title="Controllo dati inseriti" tabindex="1512">
					Controllo dati inseriti
				</a>
			  </td>
	        </c:when>
		    <c:otherwise>
		       <td>
				  Controllo dati inseriti
			   </td>
		    </c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<c:choose>
	        <c:when test='${isNavigazioneDisattiva ne "1"}'>
	          <td class="vocemenulaterale">
				<a href="javascript:popupRichiestaCUP('${id}');" title="Invio dati per richiesta CUP" tabindex="1513">
					Invio dati per richiesta CUP
				</a>
			  </td>
	        </c:when>
		    <c:otherwise>
		       <td>
					Invio dati per richiesta CUP
			   </td>
		    </c:otherwise>
		</c:choose>
	</tr>
