<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />

	<tr>
		<c:choose>
	        <c:when test='${isNavigazioneDisattiva ne "1"}'>
	          <td class="vocemenulaterale">
				<a href="javascript:popupValidazione('${flusso}','${key1}','','');" title="Controllo dati inseriti" tabindex="1512">
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
