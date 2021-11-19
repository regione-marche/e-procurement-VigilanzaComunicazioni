<%/*
   * Created on 11-nov-2011
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

<c:if test="${param.metodo eq 'inizializza' }">
<gene:template file="menuAzioni-template.jsp">
	<gene:insert name="addHistory">
		<gene:historyAdd titolo='Importa da SIMOG / Prendi in carico' id="scheda" />
	</gene:insert>
</gene:template>
</c:if>
	<tr>
		<td class="titolomenulaterale">Importa/Prendi:&nbsp;Azioni</td>
	</tr>
	<tr>
		<td class="vocemenulaterale">
			<a href="javascript:prosegui();"	tabindex="1500">Prosegui</a>
		</td>
	</tr>
	<tr>
		<td class="vocemenulaterale">
			<a href="javascript:annulla();" tabindex="1501">Torna alla pagina principale</a>
		</td>
	</tr>
