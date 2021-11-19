<%/*
   * Created on 11-apr-2017
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<tr>
		<td class="titolomenulaterale">Migra gara: Azioni</td>
	</tr>
<c:choose>
	<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
				  	and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq 0
				  	and empty sessionScope.MIGRAZIONE_SA_BEAN.errore}" >	
		<tr>
			<td class="vocemenulaterale"><a href="javascript:eseguiMigrazione();" tabindex="1501">Continua ?</a></td>
		</tr>
		<tr>
			<td class="vocemenulaterale"><a href="javascript:annulla();" tabindex="1502">Annulla</a></td>
		</tr>
	</c:when>
	<c:when test="${sessionScope.MIGRAZIONE_SA_BEAN.numeroGareControllate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
						and sessionScope.MIGRAZIONE_SA_BEAN.numeroGareMigrate eq sessionScope.MIGRAZIONE_SA_BEAN.numeroGareDaMigrare
						and empty sessionScope.MIGRAZIONE_SA_BEAN.errore}" >
		<tr>
			<td class="vocemenulaterale"><a href="javascript:esci();" tabindex="1501">Esci</a></td>
		</tr>
	</c:when>
	<c:otherwise>
	<tr>
		<td class="vocemenulaterale"><a href="javascript:annulla();" tabindex="1502">Annulla</a></td>
	</tr>
	</c:otherwise>
</c:choose>
