<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="isNavigazioneDisattiva" value="${isNavigazioneDisabilitata}" />

<tr>
	
	<c:if test='${esisteProfiloCup eq "TRUE"}'>
		<c:choose>
	    <c:when test='${isNavigazioneDisattiva ne "1"}'>
	      <td class="vocemenulaterale">
					<a href="javascript:gestioneCUP();" title="Gestione CUP" tabindex="1511">
						Gestione CUP
					</a>
			  </td>
			</c:when>
			<c:otherwise>
				<td>
				  Gestione CUP
			  </td>
		  </c:otherwise>
		</c:choose>
	</c:if>		
		
</tr>