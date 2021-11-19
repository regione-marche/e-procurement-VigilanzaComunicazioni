------ SCHEMA SITAT SA ----------
SET SCHEMA = SITATSA;
SET CURRENT PATH = SITATSA;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.2.1 --> 5.3.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.2.1' or numver like '5.2.1.%') ) = 1
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
			
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN VER_SIMOG NUMERIC(5)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN FLAG_SA_AGENTE_GARA VARCHAR(1)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN ID_F_DELEGATE NUMERIC(7)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN CF_AMM_AGENTE_GARA VARCHAR(20)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN DEN_AMM_AGENTE_GARA VARCHAR(250)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
			
			SET v_sql = 'ALTER TABLE W3LOTT ADD COLUMN CATEGORIA_MERC NUMERIC(7)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3LOTT');
			
			SET v_sql = 'CREATE TABLE W3CPV	(
				NUMGARA NUMERIC(10) NOT NULL,
				NUMLOTT NUMERIC(10) NOT NULL,
				NUM_CPV NUMERIC(3) NOT NULL,
				CPV VARCHAR(12),
				PRIMARY KEY (NUMGARA, NUMLOTT, NUM_CPV))';
			EXECUTE immediate v_sql;
			
			Update ELDAVER set NUMVER='100' where CODAPP='W9_UPDATE';
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='100' ) = 1
		THEN

			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VER_SIMOG.W3GARA.W3','W3GAVERSIM','Versione SIMOG','Versione SIMOG','N5',null,null,'Versione SIMOG');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FLAG_SA_AGENTE_GARA.W3GARA.W3','W3GAFLAG_DELEGA','La stazione appaltante agisce per conto di altro soggetto?','Agisce per altro?','A2',null,'SN','La stazione appaltante agisce per conto di altro soggetto?');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_F_DELEGATE.W3GARA.W3','W3GAFUNZ_DELEGA','Funzioni delegate','Funzioni delegate','A100','W3038',null,'Funzioni delegate');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CF_AMM_AGENTE_GARA.W3GARA.W3','W3GACF_DELEGA','Codice fiscale soggetto per conto del quale agisce la S.A.','Codice fiscale','A20',null,null,'Codice fiscale soggetto per conto del quale agisce la S.A.');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DEN_AMM_AGENTE_GARA.W3GARA.W3','W3GADEN_DELEGA','Denominazione Amministrazione per la quale agisce la S.A.','Denominazione','A250',null,'NOTE','Denominazione Amministrazione per la quale agisce la S.A.');
			
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUMGARA.W3CPV.W3','W3CPVNUMGARA','Codice della gara','Cod. gara','N10',null,null,'Codice della gara');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUMLOTT.W3CPV.W3','W3CPVNUMLOTT','Numero del lotto','Num. lotto','N10',null,null,'Numero del lotto');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUM_CPV.W3CPV.W3','W3CPVNUMCPV','Numero progressivo','Numero condizione','N3',null,null,'Numero progressivo');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CPV.W3CPV.W3','W3CPVCPV','Codice CPV secondario','Codice CPV second.','A12',null,null,'Codice CPV secondario');

			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CATEGORIA_MERC.W3LOTT.W3','W3LOTTCATEGORIA_MERC','Categoria merceologica','Cat. merceologica','A100','W3031',null,'Categoria merceologica');
			
			Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W3CPV.W3',0,'E','W3CPV','Elenco CPV secondarie','NUMGARA.W3CPV.W3,NUMLOTT.W3CPV.W3','W3LOTT.W3','NUMGARA.W3LOTT.W3,NUMLOTT.W3LOTT.W3',null,null,'w3_cpv');
			
			SET v_sql = 'Update W3GARA set VER_SIMOG=3 WHERE ID_GARA IS NOT NULL';
			EXECUTE immediate v_sql;
			SET v_sql = 'Update W3GARA set VER_SIMOG=4 WHERE ID_GARA IS NULL';
			EXECUTE immediate v_sql;

			Update W9LOTT set NLOTTO=CODLOTT WHERE NLOTTO is null;

			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',6,'Accordo quadro',null);
			
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3038',1,'Solo aggiudicazione',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3038',2,'Aggiudicazione e stipula del contratto',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3038',3,'Aggiudicazione, stipula ed esecuzione del contratto',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3038',4,'Proposta di aggiudicazione',null);
			
			Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3037' and TAB1TIP in (3);
			Update TAB1 set TAB1DESC='Modifiche contrattuali o varianti per le quali è necessaria una nuova procedura di affidamento' WHERE TAB1COD='W3037' and TAB1TIP = 8;
			
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.wspubblicazioni.tipoInstallazione','3','Servizi di pubblicazione','Tipologia di installazione dei servizi di pubblicazione. Valori ammessi: 1=Vigilanza, 2=SA, 3=OR',null);
			Delete from W_CONFIG where CHIAVE='it.eldasoft.pt.norma.lavori';
			Delete from W_CONFIG where CHIAVE='it.eldasoft.pt.norma.fornitureServizi';

			Delete from W_CONFIG where CHIAVE='eventi.log';
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','eventi.log','1','Eventi','Abilita tracciatura su W_LOGEVENTI. Valori ammessi: 0 = non abilitata, 1 [default] = abilitata',null);			
			Delete from W_CONFIG where CHIAVE='it.eldasoft.generatoreRicerche.tracciaTabelle';
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.generatoreRicerche.tracciaTabelle','a_A;b_B',null,'Elenco delle tabelle, separate da ";", le cui esecuzioni vanno tracciate con un evento',null);

			Delete from C0CAMPI where COC_MNE_UNI='ESCLUSIONE_REGIME_SPECIALE.W9LOTT.W9' and C0C_MNE_BER='W9ESREGSPE';
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ESCLUSIONE_REGIME_SPECIALE.W9LOTT.W9','W9ESREGSPE', 'Contratto regime particolare di appalto','Regime Part','A2',null,'SN','Contratto regime particolare di appalto (speciale o alleggerito)');

			Delete From W_AZIONI Where OGGETTO='ALT.W3.richiesteSIMOG';
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9LOTT.ESCLUSIONE_REGIME_SPECIALE','Modifica',0,1,1,1066804568);
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9LOTT.CONTRATTO_ESCLUSO_ALLEGGERITO','Modifica',0,1,1,2506553193);
			
			UPDATE W9GARA SET VER_SIMOG=4 WHERE DATA_PUBBLICAZIONE >= TO_DATE('2020-01-01', 'YYYY-MM-DD');
			
			Update ELDAVER set NUMVER='200' where CODAPP='W9_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='200' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.3.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

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
-- Versione: 5.2.0 --> 5.3.0
--------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.2.0' or numver like '5.2.0.%')) = 1
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
		


			Update ELDAVER set NUMVER='2' where CODAPP='PT_UPDATE';
			
		END IF;

		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='2' ) = 1 THEN
			
			Delete from C0ENTIT where C0E_NOM='FABBISOGNI.PIANI';
			Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('FABBISOGNI.PIANI',0,'E','FABBISOGNI','Fabbisogni dei programmi','CONINT.FABBISOGNI.PIANI','INTTRI.PIANI','CONINT.INTTRI.PIANI',null,null,'pt_fabb');

			Delete from w_config Where CHIAVE='it.eldasoft.pt.ImportaProposteFiltroCodeinAttivo';
			
			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) Values ('FUNZ', 'ALT.PIANI.RDS_CAMBIAUTENTESA', 'RDS Cambia utente/ufficio intestatario', 13690);
			
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.PIANI.RDS_CAMBIAUTENTESA','Visualizza',0,1,1,2388871135);
			
			Update ELDAVER set NUMVER='3' where CODAPP='PT_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='3' ) = 1 THEN
			--- ELDAVER
			Update ELDAVER set NUMVER='5.3.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='PT_UPDATE';
		END IF;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.3.0 --> 5.3.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.3.0' or numver like '5.3.0.%') ) = 1
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
			
			SET v_sql = 'ALTER TABLE W9AGGI ADD COLUMN IMPORTO_AGGIUDICAZIONE NUMERIC(24,5)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W9AGGI ADD COLUMN PERC_RIBASSO_AGG NUMERIC(13,9)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W9AGGI ADD COLUMN PERC_OFF_AUMENTO NUMERIC(13,9)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9AGGI');
			
			Update ELDAVER set NUMVER='100' where CODAPP='W9_UPDATE';
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='100' ) = 1
		THEN
		
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IMPORTO_AGGIUDICAZIONE.W9AGGI.W9','W3AGIMP_AGGI','Importo di aggiudicazione','Imp. aggiudicazione','F15',null,'MONEY','Importo di aggiudicazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PERC_OFF_AUMENTO.W9AGGI.W9','W3AGPERC_OFF','Offerta in aumento %','Offerta aumento','F13.9',null,'PRC','Offerta in aumento %');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PERC_RIBASSO_AGG.W9AGGI.W9','W3AGPERC_RIB','Ribasso di aggiudicazione %','Ribasso aggiudicaz.','F13.9',null,'PRC','Ribasso di aggiudicazione %');

			Update C0CAMPI set COC_DES='La s.a. agisce per conto di altro singolo soggetto?', COC_DES_WEB='La s.a. agisce per conto di altro singolo soggetto?' where COC_MNE_UNI='FLAG_SA_AGENTE_GARA.W3GARA.W3';
			
			delete from w_azioni where oggetto = 'ALT.W3.richiesteSIMOG';
			
			delete from TAB1 where TAB1COD='W3031';
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',1,'Farmaci',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',2,'Vaccini',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',3,'Stent',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',4,'Ausili per incontinenza (ospedalieri e territoriali)',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',5,'Protesi d''anca',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',6,'Medicazioni generali',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',7,'Defibrillatori',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',8,'Pace-maker',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',9,'Aghi e siringhe',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',10,'Servizi integrati per la gestione delle apparecchiature elettromedicali',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',11,'Servizi di pulizia per gli enti del Servizio Sanitario Nazionale',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',12,'Servizi di ristorazione per gli enti del Servizio Sanitario Nazionale',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',13,'Servizi di lavanderia per gli enti del Servizio Sanitario Nazionale',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',14,'Servizi di smaltimento rifiuti sanitari',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',15,'Vigilanza armata',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',16,'Facility management immobili',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',17,'Pulizia immobili',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',18,'Guardiania',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',19,'Manutenzione immobili e impianti',null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',20,'Guanti (chirurgici e non)',100);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',21,'Suture',101);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',22,'Ossigenoterapia',102);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',23,'Diabetologia territoriale',103);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',24,'Servizio di trasporto scolastico',104);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',25,'Manutenzione strade (servizi e forniture)',105);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',999,'Lavori oppure beni e servizi non elencati nell''art. 1 dPCM 24 dicembre 2015',null);
			
			SET v_sql = 'Update W9AGGI set ID_GRUPPO= null where ID_TIPOAGG=4';
			EXECUTE IMMEDIATE v_sql;
			
			Update W_CONFIG set VALORE='5.3.1' where CHIAVE='it.eldasoft.rt.sitatproxy.versioneTracciatoXML';
			Update W_CONFIG set VALORE='5.3.1' where CHIAVE='it.eldasoft.sitatproxy.versioneTracciatoXML';
			
			Update ELDAVER set NUMVER='200' where CODAPP='W9_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='200' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.3.1', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

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
-- Versione: 5.3.0 --> 5.3.1
--------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.3.0' or numver like '5.3.0.%')) = 1
	THEN
		Update c0entit set c0e_key= 'CONTRI.OITRI.PIANI' where c0e_nom='OITRI.PIANI';
		Update ELDAVER set NUMVER='5.3.1', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';
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


