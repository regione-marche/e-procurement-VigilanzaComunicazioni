<%
/*
 * Created on 10-nov-2011
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${smartcig eq 'ok'}">
Importa da Sistema ANAC
</c:if>
<c:if test="${smartcig ne 'ok'}">
Importa da SIMOG / Prendi in carico
</c:if>
