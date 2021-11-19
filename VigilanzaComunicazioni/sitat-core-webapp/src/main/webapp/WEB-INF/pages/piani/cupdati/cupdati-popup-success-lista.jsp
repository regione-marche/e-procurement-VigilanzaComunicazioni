
<%
	/*
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

<div style="width:97%;">

<gene:callFunction obj="it.eldasoft.sil.pt.tags.funzioni.GetValoriDropdownCUPDATIFunction" />

<gene:template file="popup-template.jsp">

	<gene:redefineInsert name="addHistory" />
	<gene:redefineInsert name="gestioneHistory" />

	<gene:setString name="titoloMaschera" value='Lista codici CUP assegnati'/>
	<gene:redefineInsert name="corpo">
		<table class="lista">
			<tr>
				<td>
					<gene:formLista pagesize="1000" 
						tableclass="datilista"
						gestisciProtezioni="false"
						sortColumn="-4"
						varName="risultatoListaCUP">
						<gene:campoLista title="Opzioni" width="50">
							<c:if test="${currentRow >= 0}" >
								<gene:PopUp variableJs="jvarRow${currentRow}" onClick="chiaveRiga='${chiaveRigaJava}'">
									<gene:PopUpItem title="Seleziona" href="javascript:selezionaCodiceCUP('${datiRiga.OBJ2}');"/>
								</gene:PopUp>
							</c:if>
						</gene:campoLista>
						<gene:campoLista ordinabile="false" title="Anno della decisione" campo="C01" definizione="T4;0" />
						<gene:campoLista ordinabile="false" title="Codice CUP" campo="C02" definizione="T15;0" 
							href="javascript:selezionaCodiceCUP('${datiRiga.OBJ2}');" />
						<gene:campoLista ordinabile="false" title="Data assegnazione" campo="C03" definizione="T10;0" />
						<gene:campoLista ordinabile="false" title="Natura" campo="C04" definizione="T50;0" />
						<gene:campoLista ordinabile="false" title="Tipologia" campo="C05" definizione="T2;0" />
						<gene:campoLista ordinabile="false" title="Descrizione" campo="C06" definizione="T2000;0" />
						<gene:campoLista ordinabile="false" title="Costo (migliaia di euro)" campo="C08" definizione="T15;0" />
						<gene:campoLista ordinabile="false" title="Finanziamento (migliaia di euro)" campo="C09" definizione="T15;0" />
					</gene:formLista>
				</td>
			</tr>
			<tr>
				<td class="comandi-dettaglio" colSpan="2">
					<INPUT type="button" class="bottone-azione" value="Chiudi" title="Chiudi" onclick="javascript:chiudi();">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</gene:redefineInsert>
	<gene:javaScript>
		function chiudi(){
			window.close();
		}
	
		function selezionaCodiceCUP(codiceCUP) {
			opener.setValue("${campoCUP}",codiceCUP);
			window.close();
		}
	
	</gene:javaScript>	
</gene:template>

</div>

