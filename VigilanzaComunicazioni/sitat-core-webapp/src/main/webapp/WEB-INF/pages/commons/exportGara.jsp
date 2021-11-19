<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<tiles:insert  definition=".listaNoAzioniDef" flush="true">

	<tiles:put name="head" type="string">
	</tiles:put>

	<tiles:put name="titoloMaschera" type="string" value="Export Gara" />

	<tiles:put name="dettaglio" type="string">
	
	<form name="query" method="post" action="${pageContext.request.contextPath}/w9/ExportGara.do">
		<table class="ricerca">
			<tr>
				<td colspan="2">
					La seguente funzione recupera i dati per l'export di una gara, comprese le pubblicazioni, 
					a partire dall'identificativo della gara.<br /><br />
					Possono essere indicate piu' gare da trattare separando i relativi identificativi con il carattere '-'.<br /><br />
					Verra' generato un file zip contenente la cartella con i file inerenti alle fasi della gara<br />
					che avranno un nome con questa forma : idgara_cig_n.fase_progressivo.xml<br />
					mentre per le pubblicazioni la forma sara' : idgara_numeroPubblicazione.xml<br />
					inoltre i flussi saranno presenti come : flusso_idgara_cig_n.fase_progressivo_dataInvio.xml.<br /><br />
					La gara esportata non verra' rimossa dall'archivio. <br /><br />
				</td>
			</tr>
			<tr>
				<td class="etichetta-dato">Inserire l'Id della gara da esportare</td>
				<td class="valore-dato">
					<input type="text" name="idGara" id="idGara" size="20" />
					<input type="hidden" name="uffint" id="uffint" value="${uffint}" />
					<input type="hidden" name="syscon" id="syscon" value="${profiloUtente.id}" />
				</td>
			</tr>
			<tr class="comandi-dettaglio">
				<td colspan="2">
					<input type="submit" value="Esporta" class="bottone-azione" />
					<input type="button" value="Cancella" class="bottone-azione" onClick="window.location.reload()" />&nbsp;
				</td>
			</tr>
		</table>
	</form>

	</tiles:put>

</tiles:insert>
