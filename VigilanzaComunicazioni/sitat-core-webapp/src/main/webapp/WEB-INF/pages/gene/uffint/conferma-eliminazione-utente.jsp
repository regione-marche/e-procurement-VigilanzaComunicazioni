<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div style="width:98%;">
<gene:template file="popup-message-template.jsp">
	<gene:setString name="titoloMaschera" value='Eliminazione utente'/>
	<%/* Elimino la gestione dell'history per il popup corrente */%>
	<c:set var="isRUP" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.CheckRUPFunction",pageContext,param.chiaveRiga )}' />
	<gene:redefineInsert name="corpo">
		<br>
		&nbsp;&nbsp;Confermi l'eliminazione?<br>
		<br>
		<c:if test="${!isRUP}">
		&nbsp;&nbsp;<input type="checkbox" name="delTecnico" value="1" id="delTecnico" > Elimina l'anagrafica del tecnico per la stazione appaltante corrente<br>
		<br>
		</c:if>
			
	</gene:redefineInsert>
	<gene:javaScript>
		function conferma() {
			var delTecnico = 0;
			if (document.getElementById("delTecnico") && document.getElementById("delTecnico").checked) {
				delTecnico = 1;
			}
			opener.bloccaRichiesteServer();
			opener.confermaDelete(delTecnico);
		}
		
		function annulla() {
			window.close();
		}
	</gene:javaScript>
</gene:template>
</div>