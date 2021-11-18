I file presenti in queste cartelle fanno parte del client del servizio SOAP dell'Osservatorio regionale della regione Liguria, presso Liguria Digitale.
Il client è stato generato con Axis2 1.6.2 eseguendo il seguente comando:

%AXIS2_HOME%\bin\wsdl2java -uri src\main\wsdl\AppaltiLiguriaWebService.wsdl -d xmlbeans -s -ssi -o build\client -ns2p http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService.wsdl=it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2,http://10.20.3.19:8080/pubblica/webservices/types=it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types


Di seguito si riporta il wsdl del servizio:

<?xml version='1.0' encoding='UTF-8'?>
<wsdl:definitions name="AppaltiLiguriaWebService" targetNamespace="http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService.wsdl" xmlns:ns1="http://schemas.xmlsoap.org/soap/http" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService.wsdl" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
  <wsdl:types>
	<xs:schema elementFormDefault="qualified" targetNamespace="http://10.20.3.19:8080/pubblica/webservices/types" version="1.0" xmlns:tns="http://10.20.3.19:8080/pubblica/webservices/types" xmlns:xs="http://www.w3.org/2001/XMLSchema">
		<xs:element name="contratto" type="tns:Contratto" />
			<xs:complexType name="Contratto">
				<xs:sequence>
				<xs:element name="DATI_ENTE" type="tns:DatiEnte" />
				<xs:element name="DATI_GENERALI_CONTRATTO" type="tns:DatiGeneraliContratto" />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name="DatiEnte">
				<xs:sequence>
					<xs:element name="CODICE_FISCALE" type="xs:string" />
					<xs:element name="ID_USER_ENTE" type="xs:string" />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name="DatiGeneraliContratto">
				<xs:sequence>
					<xs:element name="CONTROLLO_INVIO" type="xs:string" />
					<xs:element name="DATI_COMUNI" type="tns:DatiComuni" />
					<xs:element name="DATI_COMUNI_ESTESI" type="tns:DatiComuniEstesi" />
					<xs:element minOccurs="0" name="LISTA_OFFERENTI" type="tns:ListaOfferenti" />
					<xs:element name="SEZIONE_AGGIUDICAZIONE" type="tns:SezioneAggiudicazione" />
					<xs:element minOccurs="0" name="SEZIONE_INIZIO" type="tns:SezioneInizio" />
					<xs:element minOccurs="0" name="SEZIONE_SAL" type="tns:SezioneSAL" />
					<xs:element minOccurs="0" name="SEZIONE_VARIANTI" type="tns:SezioneVarianti" />
					<xs:element minOccurs="0" name="SEZIONE_SOSPENSIONI" type="tns:SezioneSospensioni" />
					<xs:element minOccurs="0" name="SEZIONE_CONCLUSIONE" type="tns:SezioneConclusione" />
					<xs:element minOccurs="0" name="SEZIONE_COLLAUDO" type="tns:SezioneCollaudo" />
					<xs:element minOccurs="0" name="SEZIONE_SUBAPPALTI" type="tns:SezioneSubappalti" />
					<xs:element minOccurs="0" name="SEZIONE_RITARDI_RECESSI" type="tns:SezioneRitardiRecessi" />
					<xs:element minOccurs="0" name="SEZIONE_ACCORDI_BONARI" type="tns:SezioneAccordiBonari" />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name="DatiComuni">
				<xs:sequence>
					<xs:element name="CODICE_UFFICIO" type="xs:string" />
					<xs:element name="DESCR_UFFICIO" type="xs:string" />
					<xs:element minOccurs="0" name="IMPORTO_LIQUIDATO" type="xs:double" />
					<xs:element name="OGGETTO_GARA" type="xs:string" />
					<xs:element name="ID_GARA" type="xs:string" />
					<xs:element minOccurs="0" name="FLAG_IMPORTO_GARA_DISP" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="IMPORTO_GARA" type="xs:double" />
					<xs:element name="NUM_LOTTI" type="xs:long" />
					<xs:element name="FLAG_ENTE" type="tns:FlagSOType" />
					<xs:element minOccurs="0" name="MODO_INDIZIONE" type="xs:string" />
					<xs:element name="MODO_REALIZZAZIONE" type="xs:string" />
					<xs:element minOccurs="0" name="CIG_AQ" type="xs:string" />
					<xs:element name="FLAG_SA_AGENTE" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="ID_TIPOLOGIA_SA" type="xs:string" />
					<xs:element minOccurs="0" name="DEN_AMM_AGENTE" type="xs:string" />
					<xs:element minOccurs="0" name="CF_AMM_AGENTE" type="xs:string" />
					<xs:element minOccurs="0" name="TIPOLOGIA_PROCEDURA" type="xs:string" />
					<xs:element minOccurs="0" name="FLAG_CENTRALE_STIPULA" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="DATA_GURI_GARA" type="xs:dateTime" />
					<xs:element minOccurs="0" name="DATA_SCADENZA_GARA" type="xs:dateTime" />
					<xs:element minOccurs="0" name="CAM_GARA" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="SISMA_GARA" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="INDSEDE_GARA" type="xs:string" />
					<xs:element minOccurs="0" name="COMSEDE_GARA" type="xs:string" />
					<xs:element minOccurs="0" name="PROSEDE_GARA" type="xs:string" />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name="DatiComuniEstesi">
				<xs:sequence>
					<xs:element name="OGGETTO" type="xs:string" />
					<xs:element minOccurs="0" name="FLAG_SOMMA_URGENZA" type="tns:FlagSNType" />
					<xs:element name="IMPORTO_LOTTO" type="xs:double" />
					<xs:element name="CPV" type="xs:string" />
					<xs:element name="ID_SCELTA_CONTRAENTE" type="xs:string" />
					<xs:element minOccurs="0" name="PROC_SCELTA_502016" type="xs:string" />
					<xs:element name="CAT_PREVALENTE" type="tns:CategoriaType" />
					<xs:element name="NUM_PROGR_LOTTO" type="xs:long" />
					<xs:element name="NUM_LOTTO_GARA" type="xs:long" />
					<xs:element name="FLAG_POSA" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="FLAG_IMPORTO_DISP" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="CLASSE_IMPORTO" type="tns:ClasseImportoType" />
					<xs:element name="FLAG_LOTTO_DERIVANTE" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="MOTIVO_COMPLETAMENTO" type="xs:string" />
					<xs:element minOccurs="0" name="CIG_PADRE" type="xs:string" />
					<xs:element minOccurs="0" name="NUM_PROGRESSIVO" type="xs:string" />
					<xs:element name="TIPO_APPALTO" type="tns:TipoSchedaType" />
					<xs:element name="FLAG_TIPO_SETTORE" type="tns:FlagSOType" />
					<xs:element name="ID_MODO_GARA" type="xs:string" />
					<xs:element name="LUOGO_ISTAT" type="xs:string" />
					<xs:element minOccurs="0" name="NUTS" type="xs:string" />
					<xs:element name="ID_TIPO_PRESTAZIONE" type="xs:string" />
					<xs:element name="CIG" type="xs:string" />
					<xs:element name="IMPORTO_SIC" type="xs:double" />
					<xs:element minOccurs="0" name="CUP" type="xs:string" />
					<xs:element minOccurs="0" name="FLAG_1632006_1" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="FLAG_1632006_2" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="DATA_PUBBLICAZIONE" type="xs:dateTime" />
					<xs:element minOccurs="0" name="FLAG_SOTTOSOGLIA" type="tns:FlagSNType" />
					<xs:element name="responsabile" type="tns:DatiResponsabile" />
					<xs:element minOccurs="0" name="lista-Id_TipologiaFS" type="tns:ListaTipologiaFS" />
					<xs:element minOccurs="0" name="lista-Id_TipologiaL" type="tns:ListaTipologiaL" />
					<xs:element minOccurs="0" name="lista-ID_Condizioni" type="tns:ListaCondizioni" />
					<xs:element minOccurs="0" name="lista-ID_CPVSecondari" type="tns:ListaCPVSecondari" />
					<xs:element maxOccurs="unbounded" minOccurs="0" name="ListaDatiSoggettiEstesi" type="tns:DatiSoggettiEstesi" />
				</xs:sequence>
			</xs:complexType>
			<xs:complexType name="DatiResponsabile">
				<xs:sequence>
					<xs:element minOccurs="0" name="INDIRIZZO" type="xs:string" />
					<xs:element minOccurs="0" name="CIVICO" type="xs:string" />
					<xs:element minOccurs="0" name="LOCALITA_TECN" type="xs:string" />
					<xs:element minOccurs="0" name="CAP" type="xs:string" />
					<xs:element minOccurs="0" name="TELEFONO" type="xs:string" />
					<xs:element minOccurs="0" name="FAX" type="xs:string" />
					<xs:element name="CODICE_FISCALE" type="xs:string" />
					<xs:element name="CODICE_ISTAT_COMUNE" type="xs:string" />
					<xs:element minOccurs="0" name="EMAIL" type="xs:string" />
					<xs:element name="COGNOME" type="xs:string" />
					<xs:element minOccurs="0" name="NOME" type="xs:string" />
					<xs:element minOccurs="0" name="PRO_TECN" type="xs:string" />
					<xs:element minOccurs="0" name="CODICE_STATO" type="xs:string" />
					<xs:element minOccurs="0" name="COMUNE_ESTERO" type="xs:string" />
					<xs:element minOccurs="0" name="NCCIAA" type="xs:string" />
					<xs:element name="ALBO_PROFESSIONALE" type="tns:FlagSNType" />
					<xs:element minOccurs="0" name="CF_ASSOCIATI" type="xs:string" />
					<xs:element minOccurs="0" name="DENOMINAZIONE_ASSOCIATI" type="xs:string" />
				</xs:sequence>
			</xs:complexType>
<xs:complexType name="ListaTipologiaFS">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_TipologiaFS" type="tns:TipologiaFS" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="TipologiaFS">
<xs:sequence>
<xs:element name="ID_APPALTO_FS" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaTipologiaL">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_TipologiaL" type="tns:TipologiaL" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="TipologiaL">
<xs:sequence>
<xs:element name="ID_APPALTO_L" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaCondizioni">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="ID_Condizioni" type="tns:Condizioni" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Condizioni">
<xs:sequence>
<xs:element name="ID_CONDIZIONE" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaCPVSecondari">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="ID_CPVSecondari" type="tns:CPVSecondari" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="CPVSecondari">
<xs:sequence>
<xs:element name="CPVCOMP" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiSoggettiEstesi">
<xs:sequence>
<xs:element name="ID_RUOLO" type="xs:string" />
<xs:element minOccurs="0" name="CIG_PROG_ESTERNA" type="xs:string" />
<xs:element minOccurs="0" name="DATA_AFF_PROG_ESTERNA" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_CONS_PROG_ESTERNA" type="xs:dateTime" />
<xs:element minOccurs="0" name="SEZIONE" type="xs:string" />
<xs:element minOccurs="0" name="COSTO_PROGETTO" type="xs:double" />
<xs:element minOccurs="0" name="FLAG_COLLAUDATORE" type="tns:FlagCollaudatoreType" />
<xs:element minOccurs="0" name="IMPORTO_COLLAUDATORE" type="xs:double" />
<xs:element name="responsabile" type="tns:DatiResponsabile" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaOfferenti">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_Offerenti" type="tns:Offerente" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Offerente">
<xs:sequence>
<xs:element name="TIPOLOGIA_SOGGETTO" type="xs:string" />
<xs:element name="FlagPartecipante" type="xs:string" />
<xs:element name="aggiudicatario" type="tns:DatiAggiudicatari" />
<xs:element minOccurs="0" name="RUOLO" type="xs:string" />
<xs:element minOccurs="0" name="ID_GRUPPO" type="xs:int" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiAggiudicatari">
<xs:sequence>
<xs:element minOccurs="0" name="INPS" type="xs:string" />
<xs:element minOccurs="0" name="MOTIVO_ASSENZA_INPS" type="xs:string" />
<xs:element name="DENOMINAZIONE" type="xs:string" />
<xs:element minOccurs="0" name="CODICE_FISCALE" type="xs:string" />
<xs:element minOccurs="0" name="PARTITA_IVA" type="xs:string" />
<xs:element minOccurs="0" name="INDIRIZZO" type="xs:string" />
<xs:element minOccurs="0" name="CIVICO" type="xs:string" />
<xs:element minOccurs="0" name="PROVINCIA" type="xs:string" />
<xs:element minOccurs="0" name="CAP" type="xs:string" />
<xs:element minOccurs="0" name="LOCALITA_IMP" type="xs:string" />
<xs:element minOccurs="0" name="CITTA" type="xs:string" />
<xs:element minOccurs="0" name="CODICE_STATO" type="xs:string" />
<xs:element minOccurs="0" name="TELEFONO" type="xs:string" />
<xs:element minOccurs="0" name="FAX" type="xs:string" />
<xs:element minOccurs="0" name="CELL_IMP" type="xs:string" />
<xs:element minOccurs="0" name="EMAIL" type="xs:string" />
<xs:element minOccurs="0" name="PEC_IMP" type="xs:string" />
<xs:element minOccurs="0" name="WEB_IMP" type="xs:string" />
<xs:element name="NCCIAA" type="xs:string" />
<xs:element name="NATURA_GIURIDICA" type="xs:string" />
<xs:element name="TIPOLOGIA_SOGGETTO" type="xs:string" />
<xs:element minOccurs="0" name="COMUNE_ESTERO" type="xs:string" />
<xs:element maxOccurs="unbounded" minOccurs="0" name="listaLegaleRappresentante" nillable="true" type="tns:DatiLegaleRappresentante" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiLegaleRappresentante">
<xs:sequence>
<xs:element name="COGNOME" type="xs:string" />
<xs:element name="NOME" type="xs:string" />
<xs:element minOccurs="0" name="CODICE_FISCALE" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneAggiudicazione">
<xs:sequence>
<xs:element name="DATI_AGGIUDICAZIONE" type="tns:DatiAggiudicazione" />
<xs:element name="PUBBLICAZIONE_AGGIUDICAZIONE" type="tns:Pubblicazione" />
<xs:element name="LISTA_FINANZIAMENTI" type="tns:ListaFinanziamenti" />
<xs:element name="LISTA_CATEGORIE" type="tns:ListaCategorie" />
<xs:element name="LISTA_AGGIUDICATARI" type="tns:ListaAggiudicatari" />
<xs:element name="LISTA_DATI_SOGGETTI_ESTESI_AGG" type="tns:ListaDatiSoggettiEstesi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiAggiudicazione">
<xs:sequence>
<xs:element name="FIN_REGIONALE" type="tns:FlagSNType" />
<xs:element name="FLAG_IMPORTI" type="tns:FlagImportiType" />
<xs:element name="FLAG_SICUREZZA" type="tns:FlagImportiType" />
<xs:element name="FLAG_LIVELLO_PROGETTUALE" type="xs:string" />
<xs:element name="VERIFICA_CAMPIONE" type="tns:FlagSNType" />
<xs:element name="DATA_VERB_AGGIUDICAZIONE" type="xs:dateTime" />
<xs:element name="IMPORTO_AGGIUDICAZIONE" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_DISPOSIZIONE" type="xs:double" />
<xs:element name="ASTA_ELETTRONICA" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="PERC_RIBASSO_AGG" type="xs:float" />
<xs:element minOccurs="0" name="PERC_OFF_AUMENTO" type="xs:float" />
<xs:element minOccurs="0" name="IMPORTO_APPALTO" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_INTERVENTO" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_NETTO_SIC" type="xs:double" />
<xs:element name="PROCEDURA_ACC" type="tns:FlagSNType" />
<xs:element name="PREINFORMAZIONE" type="tns:FlagSNType" />
<xs:element name="TERMINE_RIDOTTO" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="ID_MODO_INDIZIONE" type="xs:string" />
<xs:element minOccurs="0" name="NUM_IMPRESE_INVITATE" type="xs:long" />
<xs:element minOccurs="0" name="NUM_IMPRESE_RICHIESTA_INVITO" type="xs:long" />
<xs:element name="NUM_IMPRESE_OFFERENTI" type="xs:long" />
<xs:element name="NUM_OFFERTE_AMMESSE" type="xs:long" />
<xs:element minOccurs="0" name="DATA_SCADENZA_RICHIESTA_INVITO" type="xs:dateTime" />
<xs:element name="DATA_SCADENZA_PRES_OFFERTA" type="xs:dateTime" />
<xs:element minOccurs="0" name="IMPORTO_LAVORI" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_SERVIZI" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_FORNITURE" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_ATTUAZIONE_SICUREZZA" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_PROGETTAZIONE" type="xs:double" />
<xs:element minOccurs="0" name="REQUISITI_SS" type="xs:string" />
<xs:element minOccurs="0" name="FLAG_AQ" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="DATA_INVITO" type="xs:dateTime" />
<xs:element minOccurs="0" name="NUM_MANIF_INTERESSE" type="xs:long" />
<xs:element minOccurs="0" name="DATA_MANIF_INTERESSE" type="xs:dateTime" />
<xs:element name="FLAG_RICH_SUBAPPALTO" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="NUM_OFFERTE_ESCLUSE" type="xs:long" />
<xs:element minOccurs="0" name="OFFERTA_MASSIMO" type="xs:float" />
<xs:element minOccurs="0" name="OFFERTA_MINIMA" type="xs:float" />
<xs:element minOccurs="0" name="VAL_SOGLIA_ANOMALIA" type="xs:float" />
<xs:element minOccurs="0" name="NUM_OFFERTE_FUORI_SOGLIA" type="xs:long" />
<xs:element minOccurs="0" name="NUM_IMP_ESCL_INSUF_GIUST" type="xs:long" />
<xs:element name="COD_STRUMENTO" type="tns:TipoStrumentoType" />
<xs:element minOccurs="0" name="IMP_NON_ASSOG" type="xs:double" />
<xs:element minOccurs="0" name="MODALITA_RIAGGIUDICAZIONE" type="xs:string" />
<xs:element minOccurs="0" name="DURATA_AQ" type="xs:long" />
<xs:element name="OPERE_URBANIZ_SCOMPUTO" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="TIPO_ATTO" type="xs:string" />
<xs:element minOccurs="0" name="DATA_ATTO" type="xs:dateTime" />
<xs:element minOccurs="0" name="NUM_ATTO" type="xs:string" />
<xs:element minOccurs="0" name="PERC_IVA" type="xs:float" />
<xs:element minOccurs="0" name="IMPORTO_IVA" type="xs:double" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Pubblicazione">
<xs:sequence>
<xs:element minOccurs="0" name="DATA_GUCE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_GURI" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_BUR" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_ALBO" type="xs:dateTime" />
<xs:element minOccurs="0" name="QUOTIDIANI_NAZ" type="xs:long" />
<xs:element minOccurs="0" name="QUOTIDIANI_REG" type="xs:long" />
<xs:element minOccurs="0" name="QUOTIDIANI_LOC" type="xs:long" />
<xs:element name="PROFILO_COMMITTENTE" type="tns:FlagSNType" />
<xs:element name="SITO_MINISTERO_INF_TRASP" type="tns:FlagSNType" />
<xs:element name="SITO_OSSERVATORIO_CP" type="tns:FlagSNType" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaFinanziamenti">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_Finanziamenti" type="tns:Finanziamenti" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Finanziamenti">
<xs:sequence>
<xs:element name="ID_FINANZIAMENTO" type="tns:TipoFinanziamentoType" />
<xs:element name="IMPORTO_FINANZIAMENTO" type="xs:double" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaCategorie">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_Categorie" type="tns:Categorie" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Categorie">
<xs:sequence>
<xs:element minOccurs="0" name="ID_CATEGORIA" type="tns:CategoriaType" />
<xs:element minOccurs="0" name="CLASSE_IMPORTO" type="tns:ClasseImportoType" />
<xs:element minOccurs="0" name="PREVALENTE" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="SCORPORABILE" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="SUBAPPALTABILE" type="tns:FlagSNType" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaAggiudicatari">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_Aggiudicatari" type="tns:Aggiudicatario" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="Aggiudicatario">
<xs:sequence>
<xs:element name="TIPOLOGIA_SOGGETTO" type="xs:string" />
<xs:element minOccurs="0" name="RUOLO" type="xs:string" />
<xs:element minOccurs="0" name="FLAG_AVVALIMENTO" type="xs:string" />
<xs:element minOccurs="0" name="ID_GRUPPO" type="xs:long" />
<xs:element minOccurs="0" name="MOTIVO_AVVALIMENTO" type="xs:string" />
<xs:element name="aggiudicatario" type="tns:DatiAggiudicatari" />
<xs:element minOccurs="0" name="aggiudicatario_avv" type="tns:DatiAggiudicatari" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiSoggettiEstesi">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_DatiSoggettiEstesi" type="tns:DatiSoggettiEstesi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneInizio">
<xs:sequence>
<xs:element name="DATI_INIZIO" type="tns:DatiInizio" />
<xs:element name="PUBBLICAZIONE_INIZIO" type="tns:Pubblicazione" />
<xs:element minOccurs="0" name="LISTA_DATI_SOGGETTI_ESTESI_INIZIO" type="tns:ListaDatiSoggettiEstesi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiInizio">
<xs:complexContent>
<xs:extension base="tns:StipulaInizio">
<xs:sequence>
<xs:element name="DATA_ESECUTIVITA" type="xs:dateTime" />
<xs:element name="IMPORTO_CAUZIONE" type="xs:double" />
<xs:element minOccurs="0" name="DATA_INI_PROG_ESEC" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_APP_PROG_ESEC" type="xs:dateTime" />
<xs:element name="FLAG_FRAZIONATA" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="DATA_VERB_PRIMA_CONSEGNA" type="xs:dateTime" />
<xs:element name="DATA_VERBALE_DEF" type="xs:dateTime" />
</xs:sequence>
</xs:extension>
</xs:complexContent>
</xs:complexType>
<xs:complexType name="StipulaInizio">
<xs:sequence>
<xs:element name="DATA_STIPULA" type="xs:dateTime" />
<xs:element name="FLAG_RISERVA" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="DATA_VERB_INIZIO" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_TERMINE" type="xs:dateTime" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneSAL">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_SAL" type="tns:ListaDatiAvanzamento" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiAvanzamento">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiAvanzamento" type="tns:DatiAvanzamento" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiAvanzamento">
<xs:sequence>
<xs:element name="SUBAPPALTI" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="DATA_CERTIFICATO" type="xs:dateTime" />
<xs:element name="IMPORTO_CERTIFICATO" type="xs:double" />
<xs:element name="FLAG_RITARDO" type="tns:FlagRitardoType" />
<xs:element minOccurs="0" name="NUM_GIORNI_SCOST" type="xs:long" />
<xs:element minOccurs="0" name="NUM_GIORNI_PROROGA" type="xs:long" />
<xs:element name="FLAG_PAGAMENTO" type="xs:string" />
<xs:element minOccurs="0" name="DATA_ANTICIPAZIONE" type="xs:dateTime" />
<xs:element minOccurs="0" name="IMPORTO_ANTICIPAZIONE" type="xs:double" />
<xs:element name="DATA_RAGGIUNGIMENTO" type="xs:dateTime" />
<xs:element name="IMPORTO_SAL" type="xs:double" />
<xs:element minOccurs="0" name="DENOMINAZIONE" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneVarianti">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_VARIANTI" type="tns:ListaDatiVariante" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiVariante">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiVariante" type="tns:DatiVariante" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiVariante">
<xs:sequence>
<xs:element name="LISTA_MOTIVI_VARIANTE" type="tns:ListaMotiviVariante" />
<xs:element name="FLAG_VARIANTE" type="tns:FlagVarianteType" />
<xs:element name="QUINTO_OBBLIGO" type="tns:FlagSNType" />
<xs:element name="FLAG_IMPORTI" type="tns:FlagImportiType" />
<xs:element name="FLAG_SICUREZZA" type="tns:FlagImportiType" />
<xs:element name="DATA_VERB_APPR" type="xs:dateTime" />
<xs:element minOccurs="0" name="ALTRE_MOTIVAZIONI" type="xs:string" />
<xs:element minOccurs="0" name="IMP_RIDET_LAVORI" type="xs:double" />
<xs:element minOccurs="0" name="IMP_RIDET_SERVIZI" type="xs:double" />
<xs:element minOccurs="0" name="IMP_RIDET_FORNIT" type="xs:double" />
<xs:element minOccurs="0" name="IMP_SICUREZZA" type="xs:double" />
<xs:element minOccurs="0" name="IMP_PROGETTAZIONE" type="xs:double" />
<xs:element minOccurs="0" name="IMP_DISPOSIZIONE" type="xs:double" />
<xs:element minOccurs="0" name="DATA_ATTO_AGGIUNTIVO" type="xs:dateTime" />
<xs:element minOccurs="0" name="NUM_GIORNI_PROROGA" type="xs:long" />
<xs:element name="IMPORTO_SUBTOTALE" type="xs:double" />
<xs:element name="IMPORTO_RIDETERMINATO" type="xs:double" />
<xs:element name="IMPORTO_COMPLESSIVO" type="xs:double" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaMotiviVariante">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="Id_MotiviVariante" type="tns:MotiviVariante" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="MotiviVariante">
<xs:sequence>
<xs:element name="ID_MOTIVO_VAR" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneSospensioni">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_SOSPENSIONI" type="tns:ListaDatiSospensione" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiSospensione">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiSospensione" type="tns:DatiSospensione" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiSospensione">
<xs:sequence>
<xs:element name="NUM_PROGRESSIVO" type="xs:long" />
<xs:element name="DATA_VERB_SOSP" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_VERB_RIPR" type="xs:dateTime" />
<xs:element name="ID_MOTIVO_SOSP" type="xs:string" />
<xs:element name="FLAG_SUPERO_TEMPO" type="tns:FlagSNType" />
<xs:element name="FLAG_RISERVE" type="tns:FlagSNType" />
<xs:element name="FLAG_VERBALE" type="tns:FlagSNType" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneConclusione">
<xs:sequence>
<xs:element name="DATI_CONCLUSIONE" type="tns:DatiConclusione" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiConclusione">
<xs:sequence>
<xs:element name="FLAG_INTERR_ANTICIPATA" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="ID_MOTIVO_INTERR" type="xs:string" />
<xs:element minOccurs="0" name="ID_MOTIVO_RISOL" type="xs:string" />
<xs:element minOccurs="0" name="DATA_RISOLUZIONE" type="xs:dateTime" />
<xs:element minOccurs="0" name="FLAG_ONERI" type="xs:string" />
<xs:element minOccurs="0" name="ONERI_RISOLUZIONE" type="xs:double" />
<xs:element name="FLAG_POLIZZA" type="tns:FlagSNType" />
<xs:element name="DATA_VERB_CONSEGNA_AVVIO" type="xs:dateTime" />
<xs:element name="DATA_TERMINE_CONTRATTUALE" type="xs:dateTime" />
<xs:element name="NUM_INFORTUNI" type="xs:long" />
<xs:element name="DATA_ULTIMAZIONE" type="xs:dateTime" />
<xs:element name="NUM_INF_PERM" type="xs:long" />
<xs:element name="NUM_INF_MORT" type="xs:long" />
<xs:element minOccurs="0" name="ORE_LAVORATE" type="xs:double" />
<xs:element minOccurs="0" name="NUM_GIORNI_PROROGA" type="xs:long" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneCollaudo">
<xs:sequence>
<xs:element name="DATI_COLLAUDO" type="tns:DatiCollaudo" />
<xs:element minOccurs="0" name="LISTA_DATI_SOGGETTI_ESTESI_COLL" type="tns:ListaDatiSoggettiEstesi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiCollaudo">
<xs:sequence>
<xs:element name="FLAG_SINGOLO_COMMISSIONE" type="tns:FlagSingoloCommissioneType" />
<xs:element name="DATA_APPROVAZIONE" type="xs:dateTime" />
<xs:element name="FLAG_IMPORTI" type="tns:FlagImportiType" />
<xs:element name="FLAG_SICUREZZA" type="tns:FlagImportiType" />
<xs:element minOccurs="0" name="DATA_INIZIO_AMM_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_FINE_AMM_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="ONERI_AMM_RISERVE" type="xs:double" />
<xs:element minOccurs="0" name="DATA_INIZIO_ARB_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_FINE_ARB_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="ONERI_ARB_RISERVE" type="xs:double" />
<xs:element minOccurs="0" name="DATA_INIZIO_GIU_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_FINE_GIU_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="ONERI_GIU_RISERVE" type="xs:double" />
<xs:element minOccurs="0" name="DATA_INIZIO_TRA_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_FINE_TRA_RISERVE" type="xs:dateTime" />
<xs:element minOccurs="0" name="ONERI_TRA_RISERVE" type="xs:double" />
<xs:element name="FLAG_SUBAPPALTATORI" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="DATA_REGOLARE_ESEC" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_COLLAUDO_STAT" type="xs:dateTime" />
<xs:element minOccurs="0" name="MODO_COLLAUDO" type="xs:string" />
<xs:element name="DATA_NOMINA_COLL" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_INIZIO_OPER" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_CERT_COLLAUDO" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_DELIBERA" type="xs:dateTime" />
<xs:element name="ESITO_COLLAUDO" type="tns:FlagEsitoCollaudoType" />
<xs:element minOccurs="0" name="IMP_FINALE_LAVORI" type="xs:double" />
<xs:element minOccurs="0" name="IMP_FINALE_SERVIZI" type="xs:double" />
<xs:element minOccurs="0" name="IMP_FINALE_FORNIT" type="xs:double" />
<xs:element minOccurs="0" name="IMP_FINALE_SECUR" type="xs:double" />
<xs:element minOccurs="0" name="IMP_PROGETTAZIONE" type="xs:double" />
<xs:element name="IMP_DISPOSIZIONE" type="xs:double" />
<xs:element minOccurs="0" name="AMM_NUM_DEFINITE" type="xs:long" />
<xs:element minOccurs="0" name="AMM_NUM_DADEF" type="xs:long" />
<xs:element minOccurs="0" name="AMM_IMPORTO_RICH" type="xs:double" />
<xs:element minOccurs="0" name="AMM_IMPORTO_DEF" type="xs:double" />
<xs:element minOccurs="0" name="ARB_NUM_DEFINITE" type="xs:long" />
<xs:element minOccurs="0" name="ARB_NUM_DADEF" type="xs:long" />
<xs:element minOccurs="0" name="ARB_IMPORTO_RICH" type="xs:double" />
<xs:element minOccurs="0" name="ARB_IMPORTO_DEF" type="xs:double" />
<xs:element minOccurs="0" name="GIU_NUM_DEFINITE" type="xs:long" />
<xs:element minOccurs="0" name="GIU_NUM_DADEF" type="xs:long" />
<xs:element minOccurs="0" name="GIU_IMPORTO_RICH" type="xs:double" />
<xs:element minOccurs="0" name="GIU_IMPORTO_DEF" type="xs:double" />
<xs:element minOccurs="0" name="TRA_NUM_DEFINITE" type="xs:long" />
<xs:element minOccurs="0" name="TRA_NUM_DADEF" type="xs:long" />
<xs:element minOccurs="0" name="TRA_IMPORTO_RICH" type="xs:double" />
<xs:element minOccurs="0" name="TRA_IMPORTO_DEF" type="xs:double" />
<xs:element name="IMPORTO_SUBTOTALE" type="xs:double" />
<xs:element name="IMPORTO_FINALE" type="xs:double" />
<xs:element name="IMPORTO_CONSUNTIVO" type="xs:double" />
<xs:element minOccurs="0" name="FLAG_LAVORI_ESTESI" type="tns:FlagSNType" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneSubappalti">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_SUBAPPALTI" type="tns:ListaDatiSubappalto" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiSubappalto">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiSubappalto" type="tns:DatiSubappalto" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiSubappalto">
<xs:sequence>
<xs:element name="DATA_AUTORIZZAZIONE" type="xs:dateTime" />
<xs:element name="OGGETTO_SUBAPPALTO" type="xs:string" />
<xs:element name="IMPORTO_PRESUNTO" type="xs:double" />
<xs:element name="IMPORTO_EFFETTIVO" type="xs:double" />
<xs:element name="ID_CATEGORIA" type="tns:CategoriaType" />
<xs:element name="ID_CPV" type="xs:string" />
<xs:element name="aggiudicatario" type="tns:DatiAggiudicatari" />
<xs:element minOccurs="0" name="arch3_impagg" type="tns:DatiAggiudicatari" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneRitardiRecessi">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_RITARDI_RECESSI" type="tns:ListaDatiRitardiRecessi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiRitardiRecessi">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiRitardiRecessi" type="tns:DatiRecesso" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiRecesso">
<xs:sequence>
<xs:element name="DATA_TERMINE" type="xs:dateTime" />
<xs:element minOccurs="0" name="DATA_CONSEGNA" type="xs:dateTime" />
<xs:element name="FLAG_TIPO_COMUNICAZIONE" type="tns:FlagTCType" />
<xs:element name="DURATA_SOSP" type="xs:long" />
<xs:element minOccurs="0" name="MOTIVO_SOSP" type="xs:string" />
<xs:element name="DATA_IST_RECESSO" type="xs:dateTime" />
<xs:element minOccurs="0" name="FLAG_ACCOLTA" type="tns:FlagSNType" />
<xs:element name="FLAG_TARDIVA" type="tns:FlagSNType" />
<xs:element name="FLAG_RIPRESA" type="tns:FlagSNType" />
<xs:element name="FLAG_RISERVA" type="tns:FlagSNType" />
<xs:element minOccurs="0" name="IMPORTO_SPESE" type="xs:double" />
<xs:element minOccurs="0" name="IMPORTO_ONERI" type="xs:double" />
<xs:element minOccurs="0" name="GG_RITARDO" type="xs:long" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SezioneAccordiBonari">
<xs:sequence>
<xs:element minOccurs="0" name="DATI_ACCORDI_BONARI" type="tns:ListaDatiAccordiBonari" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiAccordiBonari">
<xs:sequence>
<xs:element maxOccurs="unbounded" minOccurs="0" name="Id_DatiAccordiBonari" type="tns:DatiAccordo" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="DatiAccordo">
<xs:sequence>
<xs:element name="DATA_INIZIO_ACC" type="xs:dateTime" />
<xs:element name="DATA_FINE_ACC" type="xs:dateTime" />
<xs:element name="DATA_ACCORDO" type="xs:dateTime" />
<xs:element name="ONERI_DERIVANTI" type="xs:double" />
<xs:element name="NUM_RISERVE" type="xs:long" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="ListaDatiComuniEstesi">
<xs:sequence>
<xs:element maxOccurs="unbounded" name="DatiComuniEstesi" type="tns:DatiComuniEstesi" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="SoggettiContratto">
<xs:sequence>
<xs:element name="LISTA_AGGIUDICATARI" type="tns:ListaAggiudicatari" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="AggiornaContrattoInviato">
<xs:sequence>
<xs:element name="DATI_ENTE" type="tns:DatiEnte" />
<xs:element name="ID_APPALTO" type="xs:string" />
<xs:element name="CIG" type="xs:string" />
<xs:element name="IMPORTO_LIQUIDATO" type="xs:double" />
</xs:sequence>
</xs:complexType>
<xs:simpleType name="FlagSNType">
<xs:restriction base="xs:string">
<xs:enumeration value="S" />
<xs:enumeration value="N" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagSOType">
<xs:restriction base="xs:string">
<xs:enumeration value="S" />
<xs:enumeration value="O" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="CategoriaType">
<xs:restriction base="xs:string">
<xs:enumeration value="AA" />
<xs:enumeration value="FB" />
<xs:enumeration value="FS" />
<xs:enumeration value="OG 1" />
<xs:enumeration value="OG 10" />
<xs:enumeration value="OG 11" />
<xs:enumeration value="OG 12" />
<xs:enumeration value="OG 13" />
<xs:enumeration value="OG 2" />
<xs:enumeration value="OG 3" />
<xs:enumeration value="OG 4" />
<xs:enumeration value="OG 5" />
<xs:enumeration value="OG 6" />
<xs:enumeration value="OG 7" />
<xs:enumeration value="OG 8" />
<xs:enumeration value="OG 9" />
<xs:enumeration value="OS 1" />
<xs:enumeration value="OS 10" />
<xs:enumeration value="OS 11" />
<xs:enumeration value="OS 12-A" />
<xs:enumeration value="OS 12-B" />
<xs:enumeration value="OS 13" />
<xs:enumeration value="OS 14" />
<xs:enumeration value="OS 15" />
<xs:enumeration value="OS 16" />
<xs:enumeration value="OS 17" />
<xs:enumeration value="OS 18-A" />
<xs:enumeration value="OS 18-B" />
<xs:enumeration value="OS 19" />
<xs:enumeration value="OS 20-A" />
<xs:enumeration value="OS 20-B" />
<xs:enumeration value="OS 21" />
<xs:enumeration value="OS 22" />
<xs:enumeration value="OS 23" />
<xs:enumeration value="OS 24" />
<xs:enumeration value="OS 25" />
<xs:enumeration value="OS 26" />
<xs:enumeration value="OS 27" />
<xs:enumeration value="OS 28" />
<xs:enumeration value="OS 29" />
<xs:enumeration value="OS 2-A" />
<xs:enumeration value="OS 2-B" />
<xs:enumeration value="OS 3" />
<xs:enumeration value="OS 30" />
<xs:enumeration value="OS 31" />
<xs:enumeration value="OS 32" />
<xs:enumeration value="OS 33" />
<xs:enumeration value="OS 34" />
<xs:enumeration value="OS 35" />
<xs:enumeration value="OS 4" />
<xs:enumeration value="OS 5" />
<xs:enumeration value="OS 6" />
<xs:enumeration value="OS 7" />
<xs:enumeration value="OS 8" />
<xs:enumeration value="OS 9" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="ClasseImportoType">
<xs:restriction base="xs:string">
<xs:enumeration value="I" />
<xs:enumeration value="II" />
<xs:enumeration value="III" />
<xs:enumeration value="IIIB" />
<xs:enumeration value="IV" />
<xs:enumeration value="IVB" />
<xs:enumeration value="V" />
<xs:enumeration value="VI" />
<xs:enumeration value="VII" />
<xs:enumeration value="VIII" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="TipoSchedaType">
<xs:restriction base="xs:string">
<xs:enumeration value="L" />
<xs:enumeration value="F" />
<xs:enumeration value="S" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagCollaudatoreType">
<xs:restriction base="xs:string">
<xs:enumeration value="I" />
<xs:enumeration value="E" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagImportiType">
<xs:restriction base="xs:string">
<xs:enumeration value="M" />
<xs:enumeration value="C" />
<xs:enumeration value="E" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="TipoStrumentoType">
<xs:restriction base="xs:string">
<xs:enumeration value="A01" />
<xs:enumeration value="A02" />
<xs:enumeration value="A03" />
<xs:enumeration value="A04" />
<xs:enumeration value="A05" />
<xs:enumeration value="A06" />
<xs:enumeration value="B01" />
<xs:enumeration value="B02" />
<xs:enumeration value="B03" />
<xs:enumeration value="B04" />
<xs:enumeration value="B05" />
<xs:enumeration value="B06" />
<xs:enumeration value="B07" />
<xs:enumeration value="B09" />
<xs:enumeration value="B10" />
<xs:enumeration value="B11" />
<xs:enumeration value="B12" />
<xs:enumeration value="B13" />
<xs:enumeration value="B14" />
<xs:enumeration value="B15" />
<xs:enumeration value="C01" />
<xs:enumeration value="C02" />
<xs:enumeration value="C03" />
<xs:enumeration value="C04" />
<xs:enumeration value="C05" />
<xs:enumeration value="C06" />
<xs:enumeration value="C07" />
<xs:enumeration value="D01" />
<xs:enumeration value="D02" />
<xs:enumeration value="E01" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="TipoFinanziamentoType">
<xs:restriction base="xs:string">
<xs:enumeration value="A" />
<xs:enumeration value="B" />
<xs:enumeration value="C01" />
<xs:enumeration value="C02" />
<xs:enumeration value="C03" />
<xs:enumeration value="C04" />
<xs:enumeration value="C05" />
<xs:enumeration value="C06" />
<xs:enumeration value="D" />
<xs:enumeration value="E" />
<xs:enumeration value="F" />
<xs:enumeration value="G" />
<xs:enumeration value="H" />
<xs:enumeration value="I" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagRitardoType">
<xs:restriction base="xs:string">
<xs:enumeration value="P" />
<xs:enumeration value="A" />
<xs:enumeration value="R" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagVarianteType">
<xs:restriction base="xs:string">
<xs:enumeration value="A" />
<xs:enumeration value="D" />
<xs:enumeration value="S" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagSingoloCommissioneType">
<xs:restriction base="xs:string">
<xs:enumeration value="S" />
<xs:enumeration value="C" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagEsitoCollaudoType">
<xs:restriction base="xs:string">
<xs:enumeration value="P" />
<xs:enumeration value="N" />
</xs:restriction>
</xs:simpleType>
<xs:simpleType name="FlagTCType">
<xs:restriction base="xs:string">
<xs:enumeration value="R" />
<xs:enumeration value="S" />
</xs:restriction>
</xs:simpleType>
</xs:schema>
<xs:schema attributeFormDefault="unqualified" elementFormDefault="unqualified" targetNamespace="http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService.wsdl" xmlns:ns1="http://10.20.3.19:8080/pubblica/webservices/types" xmlns:tns="http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService.wsdl" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<xs:import namespace="http://10.20.3.19:8080/pubblica/webservices/types" />
<xs:element name="invioContratto" type="tns:invioContratto" />
<xs:element name="invioContrattoResponse" type="tns:invioContrattoResponse" />
<xs:complexType name="invioContratto">
<xs:sequence>
<xs:element minOccurs="0" name="contratto" type="ns1:Contratto" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="invioContrattoResponse">
<xs:sequence>
<xs:element form="qualified" minOccurs="0" name="return" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:complexType name="checkFaultBean">
<xs:sequence>
<xs:element minOccurs="0" name="message" type="xs:string" />
</xs:sequence>
</xs:complexType>
<xs:element name="JAXBException" type="tns:JAXBException" />
<xs:complexType name="JAXBException">
<xs:sequence />
</xs:complexType>
<xs:element name="CheckVerifyFault" nillable="true" type="tns:checkFaultBean" />
</xs:schema>
  </wsdl:types>
  <wsdl:message name="JAXBException">
    <wsdl:part element="tns:JAXBException" name="JAXBException">
    </wsdl:part>
  </wsdl:message>
  <wsdl:message name="wsException">
    <wsdl:part element="tns:CheckVerifyFault" name="wsException">
    </wsdl:part>
  </wsdl:message>
  <wsdl:message name="invioContratto">
    <wsdl:part element="tns:invioContratto" name="parameters">
    </wsdl:part>
  </wsdl:message>
  <wsdl:message name="invioContrattoResponse">
    <wsdl:part element="tns:invioContrattoResponse" name="parameters">
    </wsdl:part>
  </wsdl:message>
  <wsdl:portType name="AppaltiLiguriaWebService">
    <wsdl:operation name="invioContratto">
      <wsdl:input message="tns:invioContratto" name="invioContratto">
    </wsdl:input>
      <wsdl:output message="tns:invioContrattoResponse" name="invioContrattoResponse">
    </wsdl:output>
      <wsdl:fault message="tns:JAXBException" name="JAXBException">
    </wsdl:fault>
      <wsdl:fault message="tns:wsException" name="wsException">
    </wsdl:fault>
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="AppaltiLiguriaWebServiceSoapBinding" type="tns:AppaltiLiguriaWebService">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="invioContratto">
      <soap:operation soapAction="urn:invioContratto" style="document" />
      <wsdl:input name="invioContratto">
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output name="invioContrattoResponse">
        <soap:body use="literal" />
      </wsdl:output>
      <wsdl:fault name="JAXBException">
        <soap:fault name="JAXBException" use="literal" />
      </wsdl:fault>
      <wsdl:fault name="wsException">
        <soap:fault name="wsException" use="literal" />
      </wsdl:fault>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="AppaltiLiguriaWebService">
    <wsdl:port binding="tns:AppaltiLiguriaWebServiceSoapBinding" name="AppaltiLiguriaPort">
      <soap:address location="http://10.20.3.19:8080/pubblica/webservices/AppaltiLiguriaWebService" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>

