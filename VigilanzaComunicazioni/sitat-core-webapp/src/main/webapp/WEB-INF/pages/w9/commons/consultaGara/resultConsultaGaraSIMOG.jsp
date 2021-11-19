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

<%@ taglib uri="http://www.eldasoft.it/genetags" prefix="gene"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.eldasoft.it/tags" prefix="elda" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="dettaglio-notab">
   <tr>
     <td colspan="2">
     	<br>
     	Importazione dati eseguita correttamente.
     	<br><br>
     </td>
   </tr>
   <tr>
     <td class="comandi-dettaglio" colSpan="2">
     	<INPUT type="button" class="bottone-azione" value="Prosegui" title="Prosegui" onclick="javascript:prosegui();" >
       &nbsp;
     	<INPUT type="button" class="bottone-azione" value="Torna alla pagina principale" title="Torna alla pagina principale" onclick="javascript:annulla();" >
       &nbsp;
     </td>
   </tr>
</table>
