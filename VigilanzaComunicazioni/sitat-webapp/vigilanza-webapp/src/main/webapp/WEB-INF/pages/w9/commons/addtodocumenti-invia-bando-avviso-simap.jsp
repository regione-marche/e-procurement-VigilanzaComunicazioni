<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />

	<tr>
		<c:choose>
	    <c:when test='${isNavigazioneDisattiva ne "1"}'>
	      <td class="vocemenulaterale">
					<a href="javascript:popupInviaBandoAvvisoSimap('${key1}');" title="Invia bando/avviso" tabindex="1511">
						Invia a Formulari Europei
					</a>
			  </td>
			</c:when>
			<c:otherwise>
				<td>
				  Invia a Formulari Europei
			  </td>
		  </c:otherwise>
		</c:choose>
	</tr>