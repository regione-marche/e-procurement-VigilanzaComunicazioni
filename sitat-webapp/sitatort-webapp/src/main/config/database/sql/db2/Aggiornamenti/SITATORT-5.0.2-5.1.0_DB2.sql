------ SCHEMA SITAT ORT ----------
SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @
create procedure aggiorna ()
begin

	IF (select count(*) from eldaver where codapp='G_' and numver like '1.12.%')=1
	THEN
			update cais set quaobb='1' where (caisim like 'OG%' or caisim like 'OS%') and tiplavg=1;
			update cais set acontec='1' where caisim='OS12B';

			Delete from TABSCHE where tabcod='S2003' and tabcod1='09' and tabcod3 in ('005024126','005024127');
			INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8665', 'COLCERESA', '005024126', '36060', 'M426');
			INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8666', 'LUSIANA CONCO', '005024127', '36046', 'M427');

			Update ELDAVER set NUMVER = '1.13.0', DATVET = CURRENT_TIMESTAMP where CODAPP = 'G_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.6.%')=1
	THEN

		update eldaver set numver = '2.7.0', datvet = CURRENT_TIMESTAMP where codapp = 'W_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.7.%')=1
	THEN

		update eldaver set numver = '2.8.0', datvet = CURRENT_TIMESTAMP where codapp = 'W_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.8.%')=1
    THEN
	
		SET v_sql = 'ALTER TABLE WSFASCICOLO ADD COLUMN ISRISERVA VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;

		delete from c0campi where c0c_mne_ber in ('WSISRISERV');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ISRISERVA.WSFASCICOLO.GENEWEB ', 'WSISRISERV', 'Riservatezza attiva per il fascicolo?', 'Riservatezza attiva?', 'A2', NULL, 'SN', NULL);

		update eldaver set numver = '2.9.0', datvet=CURRENT_TIMESTAMP where codapp = 'W_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.0.2 --> 5.1.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.0.2' or numver like '5.0.2.%') ) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('W9_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='1' ) = 1
		THEN
			SET v_sql = 'ALTER TABLE W9VARI ADD COLUMN CIG_NUOVA_PROC VARCHAR(10)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9VARI');
			
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN DURATA_ACCQUADRO NUMERIC(5)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
			
			SET v_sql = 'ALTER TABLE W3LOTT ADD COLUMN MOTIVO_COLLEGAMENTO NUMERIC(7)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3LOTT');
		
			SET v_sql = 'Update ELDAVER set NUMVER=''2'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='2' ) = 1
		THEN
			--- C0ENTIT e C0CAMPI 
			Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W_APPLICATION_KEYS.W9',0,'E','W_APP_KEYS','Application Keys','CLIENTID.W_APPLICATION_KEYS.W9',null,null,null,null,'w_app_keys');

			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (1,'E','P','CLIENTID.W_APPLICATION_KEYS.W9','WAKCLIENTID','Id per l''autenticazione ai servizi di pubblicazione','Client Id','A255',null,null,'Id per l''autenticazione ai servizi di pubblicazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY.W_APPLICATION_KEYS.W9','WAKKEY','Chiave per l''autenticazione ai servizi di pubblicazione','Client Key','A255',null,null,'Chiave per l''autenticazione ai servizi di pubblicazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOTE.W_APPLICATION_KEYS.W9','WAKNOTE','Note','Note','A2000',null,null,'Note');
			
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DURATA_ACCQUADRO.W3GARA.W3','W3GADURACCQ','Durata convenzione o accordo quadro in giorni','Durata acc.quadro','N5',null,null,'Durata convenzione o accordo quadro in giorni');
			
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MOTIVO_COLLEGAMENTO.W3LOTT.W3','W3_LOTT_MOTIVO_COLL','L''appalto prevede una delle seguenti ipotesi di collegamento','Ipotesi collegamento','A100','W3037',null,'L''appalto prevede una delle seguenti ipotesi di collegamento?');
		
			--- Modifica record di W9VARI
			Update C0CAMPI set COC_DES='Giorni proroga concessi (non per modifiche contrattuali)', COC_DES_WEB='Numero di giorni di proroga concessi (non conseguenti a modifiche contrattuali)' where coc_mne_uni = 'NUM_GIORNI_PROROGA.W9AVAN.W9';
			Update C0CAMPI set COC_DES='Numero della modifica', COC_DES_WEB='Numero della modifica' where coc_mne_uni = 'NUM_VARI.W9VARI.W9';
			Update C0CAMPI set COC_DES='Data di approvazione della modifica', COC_DES_WEB='Data di approvazione della modifica' where coc_mne_uni = 'DATA_VERB_APPR.W9VARI.W9';
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CIG_NUOVA_PROC.W9VARI.W9','W3CIGNPROC','CIG della nuova procedura avviata','CIG nuova procedura','A10',null,null,'CIG della nuova procedura avviata');
			
			UPDATE C0CAMPI SET COC_DES='Motivo urgenza',COC_DES_FRM='Motivo urgenza',COC_DES_WEB='Motivo urgenza' WHERE COC_MNE_UNI='ESTREMA_URGENZA.W3GARA.W3';
			UPDATE C0CAMPI SET COC_DES='Estrema urgenza/Esecuzione di lavori di somma urgenza',COC_DES_FRM='Estrema urgenza',COC_DES_WEB='Estrema urgenza/Esecuzione di lavori di somma urgenza' WHERE COC_MNE_UNI='URGENZA_DL133.W3GARA.W3';
			UPDATE C0CAMPI SET COC_DES='Contrato escluso o rientrante nel regime alleggerito',COC_DES_FRM='Contrato escluso',COC_DES_WEB='Contrato escluso o rientrante nel regime alleggerito' WHERE COC_MNE_UNI='FLAG_ESCLUSO.W3LOTT.W3';
			UPDATE C0CAMPI SET COC_DES='CIG collegato',COC_DES_FRM='CIG collegato',COC_DES_WEB='CIG collegato' WHERE COC_MNE_UNI='CIG_ORIGINE_RIP.W3LOTT.W3';
			UPDATE C0CAMPI SET COC_DES='Contratto regime particolare di appalto',COC_DES_FRM='Regimi particolari ?',COC_DES_WEB='Contratto regime particolare di appalto (speciale o alleggerito)' WHERE COC_MNE_UNI='FLAG_REGIME.W3LOTT.W3';
			UPDATE C0CAMPI SET COC_DES='Regime particolare di appalto',COC_DES_FRM='Regime particolare',COC_DES_WEB='Regime particolare di appalto' WHERE COC_MNE_UNI='ART_REGIME.W3LOTT.W3';
			UPDATE C0CAMPI SET COC_DES='Anno ultimo programma in cui è inserito',COC_DES_FRM='Anno ult. programma',COC_DES_WEB='Anno ultimo programma in cui è inserito' WHERE COC_MNE_UNI='PRIMA_ANNUALITA.W3LOTT.W3';
		
			SET v_sql = 'Update ELDAVER set NUMVER=''3'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='3' ) = 1
		THEN
			--- Modifiche tabellati
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9018',4,'Inserimento manuale SmartCIG',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3017',18,'Proroga tecnica',null);
			
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',1,'Ripetizione di lavori o servizi analoghi',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',2,'Consegne complementari',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',3,'Lavori, servizi o forniture supplementari',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',4,'Procedura a seguito precedente revoca aggiudicazione o rescissione contrattuale, senza graduatoria',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',5,'Procedura a seguito di precedente gara annullata o deserta (senza esito)',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',6,'Affidamento esecuzione o progettazione lavori successiva a progett. o livello inferiore di progett.',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',7,'Affidamento da parte di aggiudicatari di contratti di concessione o di project financing',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',8,'Nuovo contratto originato da variante oltre il 20%',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',9,'II fase Concorso di progettazione e idee',0);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3037',10,'No, nessuna ipotesi di collegamento',0);
		
			Delete from TAB2 where TAB2COD='W3z21';
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','37','37','Servizi sociali e altri servizi specifici di cui all''allegato IX esclusi appalti artt.143 e 144');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','38','38','Concorsi di progettazione nei settori speciali');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','39','39','Appalti riservati per determinati servizi');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','40','40','Servizi di ristorazione');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','41','41','Appalti nel settore dei beni culturali');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','42','42','Concorsi di progettazione e di idee');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','43','43','Servizi di ricerca e sviluppo');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','44','44','Appalti nei settori della Difesa e della Sicurezza');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','45','45','Contratti misti concernenti aspetti di difesa e sicurezza');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','46','46','Contratti secretati');
		
			Delete from TAB2 where TAB2COD='W3z19';
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','03','03','APPALTI NEI SETTORI DELLA DIFESA E DELLA SICUREZZA',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','04','04','CONTRATTI SECRETATI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','05','05','CONTRATTI E CONCORSI DI PROGETTAZIONE AGGIUDICATI O ORGANIZZATI IN BASE A NORME INTERNAZIONALI','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','12','12','CONTRATTI AFFIDATI AD UN ENTE AGGIUD. O DA UN CONCES. AD IMP COLLEGATE, EX ART 218 E 149 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','18','18','CONTRATTO D''IMPORTO FINO A 150.000 EURO PER ORGANI COSTITUZIONALI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','19','19','CONTRATTI DI IMPORTO INFERIORE A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','20','20','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 19 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','21','21','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 22 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','22','22','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 23 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','23','23','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 24 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','24','24','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 26 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','25','25','CONTRATTI FINO A € 40.000 EX ART. 20 c. 2 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','26','26','CONTRATTI FINO A € 40.000 EX ART. 21 DEL CODICE CON PREVALENZA SERVIZI DI CUI ALL''ALLEGATO IIB','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','27','27','CONTRATTI FINO A € 40.000 ESCLUSI EX ART. 20 c. 1 DEL CODICE','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','28','28','CONTRATTI FINO A € 40.000 ESCLUSI EX ART 21 CON PREVALENZA SERVIZI DI CUI ALL''ALLEGATO IIA','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','29','29','CONTRATTI DI ACQUISTO E LOCAZIONE DI IMMOBILI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','30','30','OPERE A SCOMPUTO FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','31','31','COPROGETTAZIONE FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','32','32','CONTRATTI FINO A € 40.000 NEL SETTORE DELL’ACQUA, DELL''ENERGIA E DEI TRASPORTI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','33','33','CONTRATTI FINO A € 40.000 NEI SETTORI DEI SERVIZI POSTALI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','34','34','APPALTI FINO A € 40.000 AGGIUDICATI DA ENTI PER ACQUISTO ACQUA E PRODUZIONE DI ENERGIA',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','35','35','CONCESSIONI FINO A € 40.000 SETTORE IDRICO ESCLUSE DALL''APPLICAZIONE DEL COD CONTRATTI PUBBLICI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','36','36','CONTRATTI FINO A € 40.000 NEI SETTORI DELLE COMUNICAZIONI ELETTRONICHE',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','37','37','APPALTI/CONCORSI DI PROGETTAZIONE AGGIUDICATI/ORGANIZZATI PER ALTRI FINI O ESERCIZIO IN PAESE TERZO',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','38','38','CONTRATTI E CONCORSI DI PROGETTAZIONE AGGIUDICATI IN BASE A NORME INTERNAZIONALI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','39','39','CONTRATTI FINO A € 40.000 NEI SETTORI MEDIA AUDIOVISI O RADIOFONICI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','40','40','SERVIZI DI ARBITRATO E DI CONCILIAZIONE FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','41','41','SERVIZI LEGALI FINO A € 40.000 ESCLUSI DALL''APPLICAZIONE DEL CODICE',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','42','42','ACQUISTO TITOLI E STRUMENTI FINANZIARI FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','43','43','PRESTITI FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','44','44','SERVIZI DIFESA E PROTEZIONE CIVILE FINO A € 40.000 FORNITI DA ORGANIZZAZIONI SENZA SCOPO LUCRO',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','45','45','SERVIZI DI TRASPORTO PUBBLICO DI PASSEGGERI PER FERROVIA O METROPOLITANA FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','46','46','SERVIZI CONNESSI A CAMPAGNE POLITICHE FINO A € 40.000 AGGIUDICATI DA UN PARTITO POLITICO',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','47','47','APPALTI PER ACQUISTO PRODOTTI AGRICOLI/ALIMENTARI FINO A € 10.000 ANNUI E € 40.000 TOT',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','48','48','SERVIZI TRASPORTO AEREO NORMA CE N.1008/2008, TRASPORTO PUBBL. NORMA N.1370/2007 FINO € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','49','49','CONCESSIONI SERVIZI DI LOTTERIE FINO A € 40.000 AGGIUDICATE SULLA BASE DI UN DIRITTO ESCLUSIVO',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','50','50','CONCESSIONI IN PAESE TERZO IN CIRCOSTANZE CHE NON COMPORTINO MATERIALE DI UNA RETE O DI UN''AREA UE',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','51','51','CONTRATTI FINO A € 40.000 DI SPONSORIZZAZIONE TECNICA',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','52','52','OPERA PUBBLICA REALIZZATA A SPESE DEL PRIVATO FINO A € 40.000',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','53','53','PAGAMENTI DEI CONCESSIONARI DI FINANZIAMENTI PUBBLICI ANCHE EUROPEI',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','54','54','CONCESSIONI, APPALTI E ACCORDI FINO A € 40.000 TRA ENTI E AMMINISTRAZIONI DEL SETTORE PUBBLICO','1');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','55','55','APPALTI E CONCESSIONI AGGIUDICATI A UN''IMPRESA COLLEGATA',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','56','56','APPALTI NEI SETTORI SPECIALI E CONCESSIONI AGGIUDICATI AD UNA JOINT-VENTURE',null);
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z19','57','57','CONTRATTI DI SERVIZI FINO A € 40.000 AGGIUDICATI IN BASE A UN DIRITTO ESCLUSIVO',null);
		
			Update TAB2 set TAB2ARC='1' WHERE TAB2COD='W3z20' and TAB2TIP in ('03','14','17','39');
			Delete from TAB2 WHERE TAB2COD='W3z20' and TAB2TIP in ('32','33','34','35','36','37','38');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','32','32','AFFIDAMENTO RISERVATO');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','33','33','PROCEDURA NEGOZIATA PER AFFIDAMENTI SOTTO SOG');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','34','34','PROCEDURA ART.16 COMMA 2-BIS DPR 380/2001 PER OPERE URB. A SCOMPUTO PRIMARIE SOTTO SOGLIA COMU.');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','35','35','PARTERNARIATO PER L''INNOVAZIONE');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','36','36','AFFIDAMENTO DIRETTO PER LAVORI, SERVIZI O FORNITURE SUPPLEMENTARI');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','37','37','PROCEDURA COMPETITIVA CON NEGOZIAZIONE');
			Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','38','38','PROCEDURA DISCIPLINATA DA REGOLAMENTO INTERNO PER SETTORI SPECIALI');
		
			UPDATE TAB1 SET TAB1NORD = 100 WHERE TAB1COD='W3031' and TAB1TIP=20;
			UPDATE TAB1 SET TAB1NORD = 101 WHERE TAB1COD='W3031' and TAB1TIP=21;
			UPDATE TAB1 SET TAB1NORD = 102 WHERE TAB1COD='W3031' and TAB1TIP=22;
			UPDATE TAB1 SET TAB1NORD = 103 WHERE TAB1COD='W3031' and TAB1TIP=23;
			UPDATE TAB1 SET TAB1NORD = 104 WHERE TAB1COD='W3031' and TAB1TIP=24;
			UPDATE TAB1 SET TAB1NORD = 105 WHERE TAB1COD='W3031' and TAB1TIP=25;
		
			Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3006' and TAB1TIP in (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32);
			Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3027' and TAB1TIP in (16,26,27,28);
		
			Update TAB1 set TAB1DESC='Modifica contrattuale' where TAB1COD='W3020' and TAB1TIP=7;
			Update TAB1 set TAB1ARC='1' where TAB1COD='W3005' and TAB1TIP=35;
			
			Delete from TAB1 where TAB1COD='W3005' and TAB1TIP=27;
			Update W9LOTT set ID_SCELTA_CONTRAENTE=NULL where ID_SCELTA_CONTRAENTE=27;
			
			SET v_sql = 'Update ELDAVER set NUMVER=''4'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='4' ) = 1
		THEN
			--- W_OGGETTI e W_AZIONI
			--- Rimozione di voci profilabili per cancellazione delle funzioni associate
			DELETE from W_OGGETTI where OGGETTO like '%ALT.W9.W9HOME.ImportRegioneMarche%';
			DELETE from W_AZIONI where OGGETTO like '%ALT.W9.W9HOME.ImportRegioneMarche%';
			DELETE from W_OGGETTI where OGGETTO like '%ALT.W9.XML-SITAR%';
			DELETE from W_AZIONI where OGGETTO like '%ALT.W9.XML-SITAR%';
			DELETE from W_OGGETTI where OGGETTO like '%ALT.W9.INVIIRM%';
			DELETE from W_AZIONI where OGGETTO like '%ALT.W9.INVIIRM%';

			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.W9.APPLICATION-KEYS','Gestione credenziali servizi di pubblicazione',6370);
			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.W9.W9LOTT-SMARTCIG','Inserimento manuale SmartCIG',6380);
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.APPLICATION-KEYS','Visualizza',0,1,1,3705424382);
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.W9LOTT-SMARTCIG','Visualizza',0,1,1,581430379);
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W3.richiesteSIMOG','Visualizza',0,1,1,3533226305);
			
			SET v_sql = 'Update ELDAVER set NUMVER=''5'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='5' ) = 1
		THEN
			Update W_CONFIG set valore='5.1.0' where CODAPP='W9' and chiave='it.eldasoft.sitatproxy.versioneTracciatoXML';
			Update W_CONFIG set valore='5.1.0' where CODAPP='W9' and chiave='it.eldasoft.rt.sitatproxy.versioneTracciatoXML';
			
			SET v_sql = 'Update ELDAVER set NUMVER=''6'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='6' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.1.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='W9_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 5.0.1 --> 5.1.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.0.1' or numver like '5.0.1.%')) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('PT_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='1' ) = 1
		THEN
			SET v_sql = 'ALTER TABLE OITRI ADD COLUMN DISCONTINUITA_RETE VARCHAR(1)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE OITRI');
			
			SET v_sql = 'Update ELDAVER set NUMVER=''2'' where CODAPP=''PT_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='2' ) = 1
		THEN
			--- C0CAMPI 
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DISCONTINUITA_RETE.OITRI.PIANI','DISCRETEOITRI','Crea discontinuità nella rete?','Discontinuità?','A2',null,'SN','Crea discontinuità nella rete?');
			Update C0CAMPI set COC_DES = 'Immobile disponibile art. 21-c5', COC_DES_WEB='Immobile disponibile art. 21-c5' where COC_MNE_UNI = 'IMMDISP.IMMTRAI.PIANI';
			
			--- TAB_CONTROLLI
			Insert into TAB_CONTROLLI (CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT','PUBB_SCP',942,'OITRI',null,'DISCONTINUITA_RETE','T.DISCONTINUITA_RETE is not null and T.INFRASTRUTTURA = ''1''','Valorizzare il campo','E');

			--- WS_CONTROLLI
			Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 362, 'OITRI', 'Opera incompiuta', 'Discontinuità nella rete', 'Valorizzare il campo', 'E');

			--- W_OGGETTI
			insert into W_OGGETTI(tipo, oggetto, descr, ord) VALUES('FUNZ', 'INS.PIANI.INTTRI-scheda.INTERVENTO.Agg-Immobili', 'Aggiungi immobile', 13550);
			insert into W_OGGETTI(tipo, oggetto, descr, ord) VALUES('FUNZ', 'INS.PIANI.PIATRI-scheda.INTERVENTI.INS', 'Inserisci Interventi', 13560);

			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDS_CONTRASSEGNACOMPLETATO', 'RDS Contrassegna come completato', 13570);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDS_SOTTOPONIADAPPRFINANZ', 'RDS Sottoponi ad approvazione finanziaria', 13580);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDS_INOLTRAALRDP', 'RDS Inoltra al referente della programmazione', 13590);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDS_ANNULLA', 'RDS Annulla proposta', 13600);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RAF_APPROVARESPINGI', 'RAF Approva/Respingi', 13610);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDP_RICHIEDIREVISIONERESPINGI', 'RDP Richiedi revisione/Respingi', 13620);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDP_INSERISCIINPROGRAMMAZIONE', 'RDP Inserisci in programmazione', 13630);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.RDP_SEGNALAPROCEDURAAVVIATA', 'RDP Segnala procedura avviata', 13640);	
			
			Delete from W_OGGETTI where OGGETTO = 'PIANI.PIATRI-scheda.FLUSSIPROGRAMMI';
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('PAGE', 'PIANI.PIATRI-scheda.FLUSSIPROGRAMMI', 'Pagina "Pubblica" o "Invii"', 13650);
			Insert into W_OGGETTI(TIPO, OGGETTO, DESCR, ORD) Values('FUNZ', 'ALT.W9.FiltroUffintListaProposte', 'Filtro Uffint su lista Proposte', 13660);

			Delete from TAB_CONTROLLI where CODAPP='PT' and entita='RIS_CAPITOLO';
			Delete from TAB_CONTROLLI where CODAPP='PT' and  titolo='RIS_CAPITOLO.NCAPBIL';
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',530,'INTTRI','Risorse per capitolo di bilancio','RIS_CAPITOLO.NCAPBIL','NOT EXISTS (select 1 from RIS_CAPITOLO c where c.CONTRI=T.CONTRI and c.CONINT=T.CONINT and c.NCAPBIL is null)','Valorizzre il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',530,'INTTRI','Risorse per capitolo di bilancio','RIS_CAPITOLO.NCAPBIL','NOT EXISTS (select 1 from RIS_CAPITOLO c where c.CONTRI=T.CONTRI and c.CONINT=T.CONINT and c.NCAPBIL is null)','Valorizzre il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',530,'INTTRI','Risorse per capitolo di bilancio','RIS_CAPITOLO.NCAPBIL','NOT EXISTS (select 1 from RIS_CAPITOLO c where c.CONTRI=T.CONTRI and c.CONINT=T.CONINT and c.NCAPBIL is null)','Valorizzre il campo','E');
		
			Delete from TAB_CONTROLLI where  codapp='PT' and codfunzione='PUBB_SCP' and num>=1010 and num <=1027;
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1010,'OITRI','Altri dati','Codice ISTAT o NUTS','T.ISTAT is not null or T.NUTS is not null','Valorizzare uno dei due campi','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1011,'OITRI','Altri dati','SEZINT','T.SEZINT is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1012,'OITRI','Altri dati','INTERV','T.INTERV is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1013,'OITRI','Altri dati','REQ_CAP','T.REQ_CAP is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1014,'OITRI','Altri dati','REQ_PRGE','T.REQ_PRGE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1015,'OITRI','Altri dati','DIM_UM','T.DIM_UM is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1016,'OITRI','Altri dati','DIM_QTA','T.DIM_QTA is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1017,'OITRI','Altri dati','SPONSORIZZAZIONE','T.SPONSORIZZAZIONE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1018,'OITRI','Altri dati','FINANZA_PROGETTO','T.FINANZA_PROGETTO is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1019,'OITRI','Altri dati','COSTO','T.COSTO is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1020,'OITRI','Altri dati','FINANZIAMENTO','T.FINANZIAMENTO is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1021,'OITRI','Altri dati','Copertura finanziaria Statale','T.COP_STATALE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1022,'OITRI','Altri dati','Copertura finanziaria Regionale','T.COP_REGIONALE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1023,'OITRI','Altri dati','Copertura finanziaria Provinciale','T.COP_PROVINCIALE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1024,'OITRI','Altri dati','Copertura finanziaria Comunale','T.COP_COMUNALE is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1025,'OITRI','Altri dati','Copertura finanziaria Comunitaria','T.COP_COMUNITARIA is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1026,'OITRI','Altri dati','Copertura finanziaria Altro','T.COP_ALTRAPUBBLICA is not null','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',1027,'OITRI','Altri dati','Copertura finanziaria Privata','T.COP_PRIVATA is not null','Valorizzare il campo','E');

			Delete from TAB_CONTROLLI where  codapp='PT' and codfunzione in ('INOLTRO_RAF','INOLTRO_RDP') and entita='OITRI';
			
			--- W_CONFIG
			Insert into W_CONFIG (CODAPP, CHIAVE, VALORE, SEZIONE, DESCRIZIONE, CRIPTATO) Values ('W9', 'it.eldasoft.pt.ImportaProposteFiltroCodeinAttivo', '0', 'Programmi triennali/biennali', 'Importa Proposte Filtro Codein Attivo. Valori ammessi: 0 [default] = non attivo, 1 = attivo', NULL);

			SET v_sql = 'Update ELDAVER set NUMVER=''3'' where CODAPP=''PT_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='3' ) = 1
		THEN
			-- ELDAVER
			Update ELDAVER set NUMVER='5.1.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='PT_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--#SET TERMINATOR @

UPDATE COMMAND OPTIONS USING s OFF@
DROP procedure ASSEGNA_GRANT@


CREATE PROCEDURE ASSEGNA_GRANT ()
BEGIN
  DECLARE EOF INT DEFAULT 0;
  DECLARE S_SQL VARCHAR(200);
  DECLARE S_TABELLA VARCHAR(50);
  DECLARE S_USER VARCHAR(50);
  
  DECLARE C1 CURSOR FOR select TABNAME from SYSCAT.TABLES where TABSCHEMA =CURRENT SCHEMA and BASE_TABSCHEMA is null and status='N';
  DECLARE C2 CURSOR FOR select SEQNAME from SYSCAT.SEQUENCES where SEQSCHEMA =CURRENT SCHEMA;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET EOF = 1;

	SET S_USER=CURRENT SCHEMA;
	SET S_USER=S_USER || ',SREST_RW';	--------		Utente per i servizi rest  -------
	SET S_USER=S_USER || ',SSELDAS';	--------		Utente Eldasoft			----------------
	SET S_USER=S_USER || ',LM07421';	--------	Utente Luca Menegatti	----------------

  OPEN C1;
  WHILE EOF = 0 DO
    FETCH FROM C1 INTO S_TABELLA;
  	SET S_SQL = 'GRANT SELECT,INSERT,UPDATE,DELETE ON TABLE "' || S_TABELLA || '" TO USER ' || S_USER;
  	EXECUTE IMMEDIATE S_SQL;
  END WHILE;
  CLOSE C1;


  OPEN C2;
  WHILE EOF = 0 DO
    FETCH FROM C2 INTO S_TABELLA;
  	SET S_SQL = 'GRANT USAGE ON SEQUENCE ' || S_TABELLA || ' TO ' || S_USER;
  	EXECUTE IMMEDIATE S_SQL;
  END WHILE;
  CLOSE C2;
END@

call ASSEGNA_GRANT@
drop procedure ASSEGNA_GRANT@
UPDATE COMMAND OPTIONS USING s ON@

--#SET TERMINATOR ;


