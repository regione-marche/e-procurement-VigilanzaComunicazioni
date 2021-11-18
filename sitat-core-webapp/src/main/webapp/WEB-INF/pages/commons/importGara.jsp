<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insert  definition=".listaNoAzioniDef" flush="true">

	<tiles:put name="head" type="string">
	</tiles:put>
	
	<tiles:put name="titoloMaschera" type="string" value="Import Gara" />
		
	<tiles:put name="dettaglio" type="string">
	
	<form name="query" method="post" action="${pageContext.request.contextPath}/w9/ImportGara.do" enctype="multipart/form-data">
		<table class="ricerca">
			<tr>
				<td colspan="2">
					La seguente funzione recupera i dati per l'import di una gara o piu' gare, comprese le pubblicazioni, 
					a partire dai file di export generati seguendo lo schema di esportazione e contenuti in un file zip.<br /><br />
				</td>
			</tr>
			<tr>
				<td class="etichetta-dato">Inserire il file per l'import</td>
				<td class="valore-dato">
					<input type="file" name="fileImportGara" id="fileImportGara" />
				</td>
			</tr>
			<tr class="comandi-dettaglio">
				<td colspan="2">
					<input type="submit" value="Importa" class="bottone-azione" />
					<input type="button" value="Cancella" class="bottone-azione" onClick="window.location.reload()" />&nbsp;
				</td>
			</tr>
		</table>
	</form>

	</tiles:put>

</tiles:insert>
