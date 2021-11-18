<%
	/*
	 * Created on: 16/03/2012
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

<%
	/* Dati generali del lavoro */
%>

<c:set var="codgara" value='${gene:getValCampo(key,"CODGARA")}' />
<c:if test="${modoAperturaScheda ne 'NUOVO'}">
	<c:set var="listaExistsFasiInteraGara" value='${gene:callFunction2("it.eldasoft.w9.tags.funzioni.ListaExistsFasiInteraGaraFunction", pageContext, codgara)}' />
</c:if>

<gene:formScheda entita="W9GARA_ENTINAZ" gestisciProtezioni="true"
	plugin="it.eldasoft.w9.tags.gestori.plugin.GestoreW9GARAENTINAZ"
	gestore="it.eldasoft.w9.tags.gestori.submit.GestoreW9GARAENTINAZ">
	<gene:gruppoCampi idProtezioni="GEN">
		<gene:campoScheda>
			<td colspan="2"><b>Dati generali della Gara</b></td>
		</gene:campoScheda>

		<gene:campoScheda campo="CODGARA" modificabile="false" visibile='${modo ne "NUOVO"}'/>
		<gene:campoScheda campo="OGGETTO" obbligatorio="true" />
		<gene:campoScheda campo="IDAVGARA" modificabile="true" />
		<gene:campoScheda campo="IMPORTO_GARA" />
		<gene:campoScheda campo="NLOTTI" visibile="false" />
		<gene:campoScheda campo="TIPO_APP" />
		<gene:campoScheda campo="DGURI" />
		<gene:campoScheda campo="DSCADE" />
		
		<gene:archivio titolo="TECNICI"
			lista='${gene:if(gene:checkProt(pageContext, "COLS.MOD.W9.W9GARA_ENTINAZ.RESPRO"),"gene/tecni/tecni-lista-popup.jsp","")}'
			scheda='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext, "MASC.VIS","GENE.SchedaTecni"),"gene/tecni/tecni-scheda-popup.jsp","")}'
			campi="TECNI.CODTEC;TECNI.NOMTEC" chiave="W9GARA_ENTINAZ_RUP" >
			<gene:campoScheda campo="RUP" defaultValue="${idTecnico}" visibile="false" />
			<gene:campoScheda campo="NOMTEC" entita="TECNI"
				defaultValue="${nomeTecnico}" where="TECNI.CODTEC=W9GARA_ENTINAZ.RUP"
				modificabile="${sessionScope.profiloUtente.abilitazioneStd eq 'A'}" title="RUP">
			</gene:campoScheda>
		</gene:archivio>
		
		<gene:campoScheda campo="ID_SCELTA_CONTRAENTE" />
		<gene:campoScheda campo="ID_MODO_GARA" />
		<gene:campoScheda campo="TIPO_CONTRATTO" />

		<gene:campoScheda>
			<td colspan="2"><b>Stazione appaltante</b></td>
		</gene:campoScheda>
		<gene:archivio titolo="STAZIONE APPALTANTE" lista=""
			scheda='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda.jsp","")}'
			schedaPopUp='${gene:if(gene:checkProtObj( pageContext,"MASC.VIS","GENE.SchedaUffint"),"gene/uffint/uffint-scheda-popup.jsp","")}'
			campi="UFFINT.CODEIN;UFFINT.NOMEIN" chiave="W9GARA_ENTINAZ_CODEIN" where=""
			formName="formStazApp">
			<gene:campoScheda campo="CODEIN" visibile="false"
				defaultValue="${sessionScope.uffint}" />
			<gene:campoScheda campo="NOMEIN" entita="UFFINT"
				defaultValue="${nomein}" where="UFFINT.CODEIN=W9GARA_ENTINAZ.CODEIN"
				modificabile="false" title="Stazione appaltante" />
		</gene:archivio>

	</gene:gruppoCampi>

	<jsp:include
		page="/WEB-INF/pages/gene/attributi/sezione-attributi-generici.jsp">
		<jsp:param name="entitaParent" value="W9GARA_ENTINAZ" />
	</jsp:include>
	<c:if test="${isPermesso eq 'false'}">
		<gene:redefineInsert name="pulsanteNuovo" />
		<gene:redefineInsert name="schedaNuovo" />
		<c:if test="${sessionScope.profiloUtente.abilitazioneStd ne 'A'}">
			<gene:redefineInsert name="schedaModifica" />
			<gene:redefineInsert name="pulsanteModifica" />
		</c:if>
	</c:if>
	<gene:campoScheda>
		<jsp:include page="/WEB-INF/pages/commons/pulsantiScheda.jsp" />
	</gene:campoScheda>
</gene:formScheda>
<gene:javaScript>
	
/*	function popupValidazione(flusso,key1,key2,key3) {
	   var comando = "href=w9/commons/popup-validazione-generale.jsp";
	   comando = comando + "&flusso=" + flusso;
	   comando = comando + "&key1=" + key1;
	   comando = comando + "&key2=" + key2;
	   comando = comando + "&key3=" + key3;
	   openPopUpCustom(comando, "validazione", 500, 650, "yes", "yes");
	}*/
	
</gene:javaScript>
