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

	Declare v_sql varchar(10000);

	IF (select count(*) from eldaver where codapp='G_' and numver = '1.14.1')=1
	THEN

		SET v_sql = 'ALTER TABLE TAB2 ALTER COLUMN TAB2D1 SET DATA TYPE VARCHAR(200)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE TAB2 ALTER COLUMN TAB2D2 SET DATA TYPE VARCHAR(200)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE TAB2');
		
		UPDATE C0CAMPI SET C0C_FS='A200' WHERE  C0C_MNE_BER='TAB2D1';
		UPDATE C0CAMPI SET C0C_FS='A200' WHERE  C0C_MNE_BER='TAB2D2';
		UPDATE C0CAMPI SET C0C_FS='F9.5' WHERE  C0C_MNE_BER='QUODIC';

		DELETE FROM C0ENTIT where c0e_nom='UFFINT.GENE#7' and c0e_key = 'CODEIN.UFFINT.GENE' and c0e_nom_ex = 'FABBISOGNI.PIANI' and coe_key_ex = 'CODEIN.FABBISOGNI.PIANI';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('UFFINT.GENE#7',0, 'C',NULL,NULL,'CODEIN.UFFINT.GENE','FABBISOGNI.PIANI','CODEIN.FABBISOGNI.PIANI',NULL,NULL,'g_10uffi');

		SET v_sql = 'CREATE TABLE ART80 (
			CODIMP VARCHAR(10) not null, 
			STATO NUMERIC(7), 
			DATA_RICHIESTA TIMESTAMP, 
			DATA_LETTURA TIMESTAMP, 
			CODEIN VARCHAR(16) not null,
			primary key (CODIMP,CODEIN))';
		EXECUTE IMMEDIATE v_sql;

		SET v_sql = 'ALTER TABLE ART80 ADD CONSTRAINT FK_ART80_IMPR FOREIGN KEY ( CODIMP ) REFERENCES IMPR( CODIMP ) ON DELETE CASCADE';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE ART80 ADD CONSTRAINT FK_ART80_UFFINT FOREIGN KEY ( CODEIN ) REFERENCES UFFINT( CODEIN ) ON DELETE CASCADE';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE ART80');

		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('ART80.GENE', 0, 'E', 'ART80', 'Articolo 80', 'CODIMP.ART80.GENE', NULL, NULL, 'g_art80', 'g_art80', 'g_art80');

		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'CODIMP.ART80.GENE', 'ART80_CODIMP', 'Codice dell''impresa', 'Codice', 'A10', NULL, NULL, 'Codice dell''anagrafico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'STATO.ART80.GENE', 'ART80_STATO', 'Stato documentale', 'Stato documentale', 'A100', 'G_063', NULL, 'Stato documentale');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'DATA_RICHIESTA.ART80.GENE', 'ART80_DRICH', 'Data invio anagrafica', 'Data invio', 'A10', NULL, 'DATA_ELDA', 'Data invio anagrafica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'DATA_LETTURA.ART80.GENE', 'ART80_DLETT', 'Data ultima lettura stato', 'Data lett. stato', 'A10', NULL, 'DATA_ELDA', 'Data ultima lettura stato');		
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'CODEIN.ART80.GENE', 'ART80_CODEIN', 'Ufficio di invio', 'Ufficio di invio', 'A16', NULL, NULL, 'Ufficio di invio');

		Update tabsche set tabdesc='SAPPADA (fino al 2017)' where tabcod='S2003' and  tabcod1='09' and  tabcod2='3674' and tabcod3='005025052';
		Delete from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3='006030189';
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8667', 'SAPPADA', '006030189', '32047', 'I421');	

		Update ELDAVER set NUMVER = '1.15.0', DATVET = CURRENT_TIMESTAMP where CODAPP = 'G_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF (select count(*) from eldaver where codapp='G_' and numver like '1.15.%')=1
	THEN
                
		SET v_sql = 'create table VERIFICHE (
			ID NUMERIC(12) not null,
			CFEIN VARCHAR(16) not null,
			CODIMP VARCHAR(10) not null,
			CONTESTO_VERIFICA VARCHAR(5),
			TIPO_VERIFICA NUMERIC(7),
			DESCR_VERIFICA VARCHAR(500),
			ESITO_VERIFICA NUMERIC(7),
			STATO_VERIFICA NUMERIC(7),
			GG_VALIDITA NUMERIC(3),
			GG_AVVISO_SCADENZA NUMERIC(3),
			DATA_ULTIMA_RICHIESTA TIMESTAMP,
			DATA_SILENZIO_ASSENSO TIMESTAMP,
			DATA_ULTIMA_CERTIFICAZIONE TIMESTAMP,
			DATA_SCADENZA TIMESTAMP,
			MODO_GESTIONE_VERIFICA NUMERIC(7),
			ENTE_COMPETENZA NUMERIC(7),
			IS_SILENZIO_ASSENSO VARCHAR(1),
			IS_ARCHIVIATO VARCHAR(1),
			primary key (ID))';
		EXECUTE IMMEDIATE v_sql;

		SET v_sql = 'create table DOCUMENTI_VERIFICHE (
			ID NUMERIC(12) not null,                
			ID_VERIFICA NUMERIC(12) not null,
			TIPO NUMERIC(7),
			DESCRIZIONE VARCHAR(500),
			PROTOCOLLO_RICHIESTA VARCHAR(20),
			NUMERO_PRATICA VARCHAR(20),
			GG_VALIDITA NUMERIC(3),
			GG_AVVISO_SCADENZA NUMERIC(3),
			DATA_INVIO_RICHIESTA TIMESTAMP,
			DATA_SILENZIO_ASSENSO TIMESTAMP,
			DATA_EMISSIONE TIMESTAMP,
			DATA_SCADENZA TIMESTAMP,
			MODALITA VARCHAR(500),
			NOTE_VERIFICA_DOC VARCHAR(2000),
			ESITO_VERIFICA_DOC NUMERIC(7),
			NOTE_ESITO_VERIFICA_DOC VARCHAR(2000),
			IDPRG VARCHAR(2),
			IDDOCDG NUMERIC(12),
			KEYSESS VARCHAR(500),
			CODTIM VARCHAR(10),
			ISARCHI VARCHAR(1),
			primary key (ID))';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'ALTER TABLE VERIFICHE ADD CONSTRAINT FK_VERIFICHE FOREIGN KEY (CODIMP) REFERENCES IMPR(CODIMP) ON DELETE CASCADE';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE VERIFICHE');
		
		SET v_sql = 'ALTER TABLE DOCUMENTI_VERIFICHE ADD CONSTRAINT FK_DOCUMENTI_VERIFICHE FOREIGN KEY (ID_VERIFICA) REFERENCES VERIFICHE(ID) ON DELETE CASCADE';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE DOCUMENTI_VERIFICHE');
		
		SET v_sql = 'ALTER TABLE UFFINT ALTER COLUMN CITEIN SET DATA TYPE VARCHAR(36)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE USRSYS');
		
		SET v_sql = 'ALTER TABLE PUNTICON ALTER COLUMN CITEIN SET DATA TYPE VARCHAR(36)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE USRSYS');
		
		SET v_sql = 'ALTER TABLE USRSYS ADD SYSULTACC TIMESTAMP';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE USRSYS');
		
		SET v_sql = 'Create Table G_LOGINKO (
			id NUMERIC(12) not null,
			username VARCHAR(60),
			logintime TIMESTAMP not null,
			ipaddress VARCHAR(40),
			primary key (ID))';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'ALTER TABLE IMPR ADD ISCRIAE VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD AEDSCAD TIMESTAMP';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD AEINCORSO VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD ISCRIESP VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD ISCRIRAT VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD RATING NUMERIC(7)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD RATDSCAD TIMESTAMP';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD RATINCORSO VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPR');

		update eldaver set numver = 'M1.1.16.0', datvet = CURRENT_TIMESTAMP where codapp = 'G_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@


create procedure aggiorna ()
BEGIN
    IF (select count(*) from eldaver where codapp = 'G_' and numver = 'M1.1.16.0')=1
	THEN
	
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re)
			VALUES ('VERIFICHE.GENE', 0, 'E', 'EL_VERIF', 'Verifiche imprese', 'ID.VERIFICHE.GENE', NULL, NULL, 'g_verif2', 'g_verif1', 'g_verif0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re)
			VALUES ('DOCUMENTI_VERIFICHE.GENE', 0, 'E', 'DOC_VERIF', 'Documenti verifiche Art.80', 'ID_VERIFICA.DOCUMENTI_VERIFICHE.GENE', 'VERIFICHE.GENE', 'ID.VERIFICHE.GENE', 'g_verif2', 'g_verif1', 'g_verif0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('G_LOGINKO.GENE ', 0, 'C', 'G_LOGINKO', 'Login fallite', 'ID.G_LOGINKO.GENE', NULL, NULL, 'g_logko0', 'g_logko0', 'g_logko0');
		-- VERIFICHE
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', 'P', 'ID.VERIFICHE.GENE', 'VERIF_ID', 'Id', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'CFEIN.VERIFICHE.GENE', 'VERIF_CFEIN', 'Codice fiscale S.A.', 'CF S.A.', 'A16', NULL, NULL, 'Codice fiscale dellaq S.A.');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'CODIMP.VERIFICHE.GENE', 'VERIF_CODIMP', 'Codice dell''impresa', 'Codice', 'A10', NULL, NULL, 'Codice dell''anagrafico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'CONTESTO_VERIFICA.VERIFICHE.GENE', 'VERIF_CONTESTO', 'Contesto verifica', 'Contesto verifica', 'A10', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'TIPO_VERIFICA.VERIFICHE.GENE', 'VERIF_TIPO', 'Tipo verifica', 'Tipo verifica', 'A100', 'G_z24', NULL, 'Tipo verifica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DESCR_VERIFICA.VERIFICHE.GENE', 'VERIF_DESCR', 'Descrizione verifica', 'Descr verifica', 'A500', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'ESITO_VERIFICA.VERIFICHE.GENE', 'VERIF_ESITO', 'Esito verifica', 'Esito verifica', 'A100', 'G_064', NULL, 'Esito verifica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'STATO_VERIFICA.VERIFICHE.GENE', 'VERIF_STATO', 'Stato verifica', 'Stato verifica', 'A100', 'G_068', NULL, 'Stato verifica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'GG_VALIDITA.VERIFICHE.GENE', 'VERIF_GGVALIDITA', 'Giorni di validita', 'GG validita', 'N3', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'GG_AVVISO_SCADENZA.VERIFICHE.GENE', 'VERIF_GGAVVSCADENZA', 'Giorni prima di avviso scadenza', 'Promemoria', 'N3', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_ULTIMA_RICHIESTA.VERIFICHE.GENE', 'VERIF_DULTRICH', 'Data ultima richiesta', 'Data ult. richiesta', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_SILENZIO_ASSENSO.VERIFICHE.GENE', 'VERIF_DSILASS', 'Data silenzio assenso', 'Data silenz. assenso', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
	VALUES (0, 'E', NULL, 'DATA_ULTIMA_CERTIFICAZIONE.VERIFICHE.GENE', 'VERIF_DULTCERT', 'Data ultima certificazione', 'Data ult. cert.', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_SCADENZA.VERIFICHE.GENE', 'VERIF_DSCAD', 'Data scadenza', 'Data scadenza', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
	VALUES (0, 'E', NULL, 'MODO_GESTIONE_VERIFICA.VERIFICHE.GENE', 'VERIF_MODOGESTV', 'Modo gestione verifica', 'Modo gestione', 'A100', 'G_065', NULL, 'Modo gestione verifica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
	VALUES (0, 'E', NULL, 'ENTE_COMPETENZA.VERIFICHE.GENE', 'VERIF_ENTECOMP', 'Ente competenza', 'Ente competenza', 'A100', 'G_066', NULL, 'Ente competenza');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'IS_SILENZIO_ASSENSO.VERIFICHE.GENE', 'VERIF_ISSILASS', 'Silenzio/assenso?', 'Silenzio/assenso?', 'A1', NULL, 'SN', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'IS_ARCHIVIATO.VERIFICHE.GENE', 'VERIF_ISARCH', 'Archiviato?', 'Archiviato?', 'A1', NULL, 'SN', NULL);
			
		--DOCUMENTI VERIFICHE
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', 'P', 'ID.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_ID', 'Id', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'ID_VERIFICA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_ID_VERIFICA', 'Id verifica', 'Id verifica', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'TIPO.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_TIPO', 'Tipo', 'Tipo', 'A100', 'G_067', NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DESCRIZIONE.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_DESC', 'Descrizione verifica', 'Descrizione verifica', 'A500', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'PROTOCOLLO_RICHIESTA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_PROTRICH', 'Protocollo richiesta', 'Protocollo richiesta', 'A20', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'NUMERO_PRATICA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_NUMPRAT', 'Numero pratica', 'Numero pratica', 'A20', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'GG_VALIDITA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_GGVALIDITA', 'Giorni di validita', 'GG validita', 'N3', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'GG_AVVISO_SCADENZA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_GGAVVSCADENZA', 'Giorni prima di avviso scadenza', 'Promemoria', 'N3', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_INVIO_RICHIESTA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_DINVIORICH', 'Data invio richiesta', 'Data invio richiesta', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_SILENZIO_ASSENSO.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_DSILASS', 'Data silenzio assenso', 'Data silenz. assenso', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_EMISSIONE.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_DEMISS', 'Data emissione', 'Data emissione', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'DATA_SCADENZA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_DSCAD', 'Data scadenza', 'Data scadenza', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'MODALITA.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_MODALITA', 'Modalita''', 'Modalita''', 'A500', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'NOTE_VERIFICA_DOC.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_NOTVFDOC', 'Note verifica documento', 'Note verif. doc.', 'A2000', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'ESITO_VERIFICA_DOC.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_ESVFDOC', 'Esito verifica documento', 'Esito verif. doc.', 'A100', 'G_064', NULL, 'Esito verifica documento');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'NOTE_ESITO_VERIFICA_DOC.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_NOTESVFDOC', 'Note esito verifica documento', 'Note esito vf. doc.', 'A2000', NULL, NULL, 'Note esito verifica documento');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) 
			Values (0, 'E', NULL, 'IDPRG.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_IDPRG', 'Codice applicativo (riferimento ad arch.documenti digitali)', 'Codice applicativo', 'A2', NULL, NULL, NULL);
		Insert into C0CAMPI(COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB)
			Values (0, 'E', NULL,'IDDOCDG.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_IDDOCDG', 'ID documento (riferimento ad arch.documenti digitali)', 'Codice documento', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web)
			VALUES (0, 'E', NULL, 'CODTIM.DOCUMENTI_VERIFICHE.GENE', 'DOCVF_CODTIM', 'Codice del soggetto', 'Cod. soggetto', 'A10', NULL, NULL, 'Codice soggetto');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB)
			VALUES (0,'E',NULL,'ISARCHI.DOCUMENTI_VERIFICHE.GENE','DOCVF_ISARCHI','Documento archiviato o superato ?','Doc. archiviato?','A2',NULL,'SN',NULL);

		-- G_LOGINKO E USRSYS
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'SYSULTACC.USRSYS.GENE', 'G_USYSULTAC', 'Data ultimo accesso', 'Ultimo accesso', 'A10', NULL, 'TIMESTAMP', NULL);
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'C', 'P','ID.G_LOGINKO.GENE', 'LOGKOID', 'Id registrazione', 'Id', 'N12', NULL, NULL, NULL);
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'C', NULL,'USERNAME.G_LOGINKO.GENE', 'LOGKOUSER', 'Username', 'Username', 'A60', NULL, NULL, NULL);
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'C', NULL,'LOGINTIME.G_LOGINKO.GENE', 'LOGKOTIME', 'Data tentativo di login fallito', 'Data', 'A19', NULL, 'TIMESTAMP', NULL);
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'C', NULL,'IPADDRESS.G_LOGINKO.GENE', 'LOGKOIP', 'Indirizzo IP', 'Indirizzo IP', 'A40', NULL, NULL, NULL);
		
		-- Iscrizione elenchi ricostruzione
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'ISCRIAE.IMPR.GENE', 'G_ISCRIAE', 'Iscritto a anagrafe antimaf.esecut.(art.30 c.6 DL 189/2016)?', 'Iscr.an.antim.esec.?', 'A2', NULL, 'SN', 'Iscritto a anagrafe antimafia esecutori (art.30 c.6 DL 189/2016)?');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'AEDSCAD.IMPR.GENE', 'G_AEDSCAD', 'Data scadenza iscrizione anagrafe antimafia esecutori', 'Data scad.iscr.an.es', 'A10', NULL, 'DATA_ELDA', 'Data scadenza iscrizione');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'AEINCORSO.IMPR.GENE', 'G_AEINCOR', 'Rinnovo iscrizione in corso anagrafe antimafia esecutori?', 'Rinn.in corso an.es?', 'A2', NULL, 'SN', 'Rinnovo in corso?');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'ISCRIESP.IMPR.GENE', 'G_ISCRIESP', 'Iscritto a elenco speciale profession.(art.34 DL 189/2016)? ', 'Iscr.el.spec.prof.?', 'A2', NULL, 'SN', 'Iscritto a elenco speciale professionisti (art.34 DL 189/2016)?');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'ISCRIRAT.IMPR.GENE', 'G_ISCRIRAT', 'Possiede rating di legalità?', 'Possiede rating leg?', 'A2', NULL, 'SN', 'Possiede rating di legalità?');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'RATING.IMPR.GENE', 'G_RATING', 'Rating di legalità  ', 'Rating legalità ', 'A100', 'G_069',  NULL,'Rating');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'RATDSCAD.IMPR.GENE', 'G_RATDSCAD', 'Data scadenza possesso rating di legalità ', 'Data scad.rating', 'A10', NULL, 'DATA_ELDA', 'Data scadenza rating');
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB) Values (0, 'E', NULL,'RATINCORSO.IMPR.GENE', 'G_RATINCOR', 'Aggiornamento rating di legalità in corso?', 'Agg.in corso rating?', 'A2', NULL, 'SN', 'Aggiornamento in corso?');

		INSERT INTO w_genchiavi (tabella, chiave) VALUES ('VERIFICHE', 0);
		INSERT INTO w_genchiavi (tabella, chiave) VALUES ('DOCUMENTI_VERIFICHE', 0);
		INSERT INTO w_genchiavi (tabella, chiave) VALUES ('G_LOGINKO', 0);
		
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','1', NULL,'Attestazione ottemperanza legge 68/99 (Art. 80 - Comma 5.I)');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','2', NULL,'Certificato anagrafe sanzione amministrative da reato e Casellario ANAC');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','3', NULL,'Certificato del casellario giudiziale relativo ai soggetti dotati di rappresentanza in carica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','4', NULL,'Certificato del casellario giudiziale relativo ai soggetti dotati di rappresentanza cessati nell''ultimo anno');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','5', NULL,'Certificazione Antimafia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','6', NULL,'DURC');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','7', NULL,'Attestazione di regolarità fiscale, Art. 80 - Comma 4');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','8', NULL,'Estratto casellario ANAC');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z24','9', NULL,'Visura Camerale');
		-- Corrispondenza con G_z24 per annoverare nell'ordine: default GG_VALIDITA verifica,GG_AVVISO_SCADENZA verifica
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','1', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','2', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','3', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','4', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','5', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','6', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','7', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','8', NULL,NULL);
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z25','9', NULL,NULL);
		--Esito verifica interna
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_064',1,'Regolare',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_064',2,'In lavorazione',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_064',3,'Anomalo',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_064',4,'Non applicabile',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_064',5,'Regolare per S.A.',NULL,0,NULL);
		--Modalità di gestione della verifica
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_065',1,'Autonoma',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_065',2,'Avcpass',NULL,0,NULL);
		--Ente di competenza a cui deve essere inviata la richiesta
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_066',1,'Agenzia delle entrate',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_066',2,'Cassa Previdenziale',NULL,0,NULL);
		--Tipo verifica
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_067',1,'Richiesta',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_067',2,'Certificazione',NULL,0,NULL);
		--Stato verifica
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_068',1,'In corso',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_068',2,'In scadenza',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_068',3,'Scaduta',NULL,0,NULL);

		--rating
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',1,'*',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',2,'*+',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',3,'*++',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',4,'**',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',5,'**+',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',6,'**++',NULL,NULL,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('G_069',7,'***',NULL,NULL,NULL);

		INSERT INTO tab6 (tab6cod, tab6tip, tab6desc) VALUES ('G__1', 'G_069', 'Rating');

		UPDATE C0CAMPI set C0C_FS = 'A36' where COC_MNE_UNI in ('CITEIN.PUNTICON.GENE','CITEIN.UFFINT.GENE');			
	
		-- gli utenti amministratori per il GDPR non devono accedere a tutti i dati
		UPDATE USRSYS SET SYSAB3='U',SYSABG='U',MERUOLO=1 WHERE SYSPWBOU LIKE '%ou89%';

		-- accentramento delle properties relative alle password all'interno della sezione "Gestione account"
		UPDATE W_CONFIG SET SEZIONE = 'Gestione account' WHERE CHIAVE = 'it.eldasoft.account.durataPassword';
		UPDATE W_CONFIG SET SEZIONE = 'Gestione account' WHERE CHIAVE = 'account.intervalloMinimoCambioPassword';

		update eldaver set numver = '1.16.0', datvet = CURRENT_TIMESTAMP where codapp = 'G_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.11.%') = 1
    THEN

		SET v_sql = 'CREATE TABLE W_TAGS 
		(CODAPP VARCHAR(10) NOT NULL, 
		TAGCOD VARCHAR(30) NOT NULL, 
		TAGVIEW VARCHAR(30),
		TAGDESC VARCHAR(200), 
		TAGCOLOR VARCHAR(20), 
		TAGBORDERCOLOR VARCHAR(20), 
		TAGMOD VARCHAR(1),
		primary key (CODAPP, TAGCOD))';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'CREATE TABLE W_TAGSLIST 
		(CODAPP VARCHAR(10) NOT NULL, 
		TAGCOD VARCHAR(30) NOT NULL, 
		TAGENTITY VARCHAR(30), 
		TAGFIELD VARCHAR(30), 
		TAGINFO CLOB , 
		TAGMOD VARCHAR(1))';
		EXECUTE IMMEDIATE v_sql;

		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('W_TAGS.GENEWEB', 0, 'E', 'W_TAGS', 'Tag: lista delle integrazioni', 'CODAPP.W_TAGS.GENEWEB,TAGCOD.W_TAGS.GENEWEB', NULL, NULL, NULL, NULL, 'tags');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('W_TAGSLIST.GENEWEB', 0, 'E', 'W_TAGSLIST', 'Tag: lista dei tag', 'CODAPP.W_TAGSLIST.GENEWEB,TAGCOD.W_TAGSLIST.GENEWEB', 'W_TAGSLIST.GENEWEB','CODAPP.W_TAGS.GENEWEB,TAGCOD.W_TAGS.GENEWEB', NULL, NULL, 'tagslist');

		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'CODAPP.W_TAGS.GENEWEB', 'WTAGSCODAPP', 'Codice applicativo', 'Cod. app.', 'A10', NULL, NULL, 'Codice applicativo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'TAGCOD.W_TAGS.GENEWEB', 'WTAGSTAGCOD', 'Codice integrazione', 'Cod. int.', 'A30', NULL, NULL, 'Codice integrazione');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGVIEW.W_TAGS.GENEWEB', 'WTAGSTAGVIEW', 'Codice etichetta', 'Cod. ett.', 'A30', NULL, NULL, 'Codice etichetta');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGDESC.W_TAGS.GENEWEB', 'WTAGSTAGDESC', 'Descrizione', 'Descrizione', 'A200', NULL, NULL, 'Descrizione');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGCOLOR.W_TAGS.GENEWEB', 'WTAGSTAGSCOLOR', 'Colore sfondo', 'Col. sfondo', 'A20', NULL, NULL, 'Colore sfondo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGBORDERCOLOR.W_TAGS.GENEWEB', 'WTAGSTAGBORDERCOLOR', 'Colore bordo', 'Col. bordo', 'A20', NULL, NULL, 'Colore bordo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGMOD.W_TAGS.GENEWEB', 'WTAGSTAGMOD', 'Modificabile ?', 'Mod. ?', 'A2', NULL, 'SN', 'Modificabile ?');

		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'CODAPP.W_TAGSLIST.GENEWEB', 'WTAGSLISTCODAPP', 'Codice applicativo', 'Cod. app.', 'A10', NULL, NULL, 'Codice applicativo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', 'P', 'TAGCOD.W_TAGSLIST.GENEWEB', 'WTAGSLISTTAGCOD', 'Codice integrazione', 'Cod. int.', 'A30', NULL, NULL, 'Codice integrazione');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', null, 'TAGENTITY.W_TAGSLIST.GENEWEB', 'WTAGSLISTTAGENTITY', 'Entita''', 'Entita''', 'A30', NULL, NULL, 'Entita''');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', null, 'TAGFIELD.W_TAGSLIST.GENEWEB', 'WTAGSLISTTAGFIELD', 'Campo', 'Campo', 'A30', NULL, NULL, 'Campo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', null, 'TAGINFO.W_TAGSLIST.GENEWEB', 'WTAGSLISTTAGINFO', 'Informazioni', 'Informazioni', 'A2000', NULL, 'CLOB', 'Informazioni');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'TAGMOD.W_TAGSLIST.GENEWEB', 'WTAGSLISTAGMOD', 'Modificabile ?', 'Mod. ?', 'A2', NULL, 'SN', 'Modificabile ?');

		insert into w_azioni (tipo,azione,oggetto,descr,valore,inor,viselenco,crc) values ('COLS','VIS','GENEWEB.W_TAGS.*','Visualizza',1,1,1,1009627889);
		insert into w_azioni (tipo,azione,oggetto,descr,valore,inor,viselenco,crc) values ('COLS','VIS','GENEWEB.W_TAGSLIST.*','Visualizza',1,1,1,569747633);
		
		update eldaver set numver = '2.12.0', datvet = CURRENT_TIMESTAMP where codapp = 'W_';

	END IF;
end@

--- Sono state volutamente escluse le tabelle RPA_LOG e RPA_SESSION e le viste RPA_C0CAMPI e RPA_C0ENTIT
--- perche' tabelle/viste specifiche del generatore modelli, che non e' usato in Regione Toscana.

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.12.0' or numver like '2.12.0.%'))=1
	THEN
	
		update eldaver set numver = '2.13.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.13.%')=1
	THEN

		INSERT INTO tab2 (tab2cod, tab2tip, tab2d1, tab2d2, tab2mod, tab2nord) VALUES ('G_z20', '14', 'StatoC', 'In protocollazione', NULL, NULL);
		INSERT INTO tab2 (tab2cod, tab2tip, tab2d1, tab2d2, tab2mod, tab2nord) VALUES ('G_z20', '15', 'StatoC', 'In protocollazione con errore', NULL, NULL);
	        
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'DEL.GENE.ImprScheda.VART80.DEL', 'Lista verifiche - Elimina', 14290);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'DEL.GENE.ImprScheda.VART80.LISTADELSEL', 'Lista verifiche - Elimina selezionati', 14300);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'MOD.GENE.ImprScheda.VART80.MOD', 'Lista verifiche - Modifica', 14310);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('MASC', 'GENE.VerificheScheda', 'Scheda verifica', 14320);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('PAGE', 'GENE.VerificheScheda.DATIGEN', 'Pagina "Dati Generali"', 14330);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'MOD.GENE.VerificheScheda.DATIGEN.SCHEDAMOD', 'Modifica', 14340);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'GENE.VerificheScheda.DOCVERIF', 'Pagina "Documenti verifica"', 14350);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'DEL.GENE.VerificheScheda.DOCVERIF.DEL', 'Lista documenti verifica - Elimina', 14360);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'DEL.GENE.VerificheScheda.DOCVERIF.LISTADELSEL', 'Lista documenti verifica - Elimina selezionati', 14370);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'MOD.GENE.VerificheScheda.DOCVERIF.MOD', 'Lista documenti verifica - Modifica', 14380);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'GENE.DOCUMENTI_VERIFICHE-scheda', 'Scheda documenti verifica', 14390);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'MOD.GENE.DOCUMENTI_VERIFICHE-scheda.SCHEDAMOD', 'Modifica', 14400);

                INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('SEZ', 'GENE.ImprScheda.DATIGEN.ISCELRIC', 'Sezione Iscrizione elenchi ricostruzione (DL 189/2016)', 1007);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('SEZ', 'GENE.ImprScheda.DATIGEN.RATING', 'Sezione Rating di legalità (DL 1/2012) ', 1008);

		update eldaver set numver = '2.14.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.2.0 --> 5.2.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.2.0' or numver like '5.2.0.%') ) = 1
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
			
			SET v_sql = 'ALTER TABLE W9LOTT ADD COLUMN CONTRATTO_ESCLUSO_ALLEGGERITO VARCHAR(1)';
			EXECUTE immediate v_sql;
			SET v_sql = 'ALTER TABLE W9LOTT ADD COLUMN ESCLUSIONE_REGIME_SPECIALE VARCHAR(1)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9LOTT');
			
			Update ELDAVER set NUMVER='100' where CODAPP='W9_UPDATE';
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='100' ) = 1
		THEN

			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CONTRATTO_ESCLUSO_ALLEGGERITO.W9LOTT.W9','W9CONESCAL','Contratto escluso o rientrante nel regime alleggerito?','Cont.escluso','A2',null,'SN','Contratto escluso o rientrante nel regime alleggerito?');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ESCLUSIONE_REGIME_SPECIALE.W9LOTT.W9','W9ESREGSPE','Esclusione o regime speciale?','Escl.RegSpe?','A2',null,'SN','Esclusione o regime speciale?');


			--- Durata Password: 180 giorni
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','account.durata','180','Gestione account','Durata massima account senza accessi (espressa in GIORNI) per utenti con applicati i controlli di sicurezza',null);
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','account.loginKO.maxNumTentativi','5','Gestione account','Numero massimo di tentativi di autenticazione oltre il quale viene bloccato l''accesso provvisoriamente',null);
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','account.loginKO.delaySecondi','30','Gestione account','Ritardo (espresso in SECONDI) introdotto in caso di tentativo di autenticazione per un utente bloccato provvisoriamente per superamento del limite di tentativi di autenticazione fallita',null);
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','account.loginKO.durataBloccoMinuti','30','Gestione account','Durata del blocco provvisorio (espresso in MINUTI) per un utente avente superato il limite di tentativi di autenticazione fallita',null);

			--- Definizione della W9CF_PUBB (qualora mancasse)
			Delete from C0ENTIT where C0E_NOM='W9CF_PUBB.W9' and COE_ARG='CFTIPPUB';
			Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9CF_PUBB.W9',0,'E','CFTIPPUB','Configurazione tipologie atti e documenti','ID.W9CF_PUBB.W9',null,null,null,null,'w9_cfpubb');

			--- Definizione dei campi della W9CF_PUBB (qualora mancassero)
			Delete from C0CAMPI where coc_mne_uni like '%.W9CF_PUBB.W9';
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (1,'E','P','ID.W9CF_PUBB.W9','W9CFPID','Codice della configurazione','Cod. configurazione','N3',null,null,'Codice della configurazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOME.W9CF_PUBB.W9','W9CFPNOME','Nome della pubblicazione','Nome pubblicazione','A250',null,null,'Nome della pubblicazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUMORD.W9CF_PUBB.W9','W9CFPNUMORD','Numero ordine','Numero ordine','N3',null,null,'Numero ordine');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CL_WHERE_VIS.W9CF_PUBB.W9','W9CFPWHEREV','Clausola where per visualizzazione','Clausola visualizz.','A2000',null,'NOTE','Clausola where per visualizzazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CL_WHERE_AB.W9CF_PUBB.W9','W9CFPWHEREA','Clausola where per abilitazione','Clausola abilitaz.','A2000',null,'NOTE','Clausola where per abilitazione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CAMPI_VIS.W9CF_PUBB.W9','W9CFCAMPIVIS','Campi visualizzati','Campi visualizzati','A2000',null,'NOTE','Campi visualizzati');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CAMPI_OBB.W9CF_PUBB.W9','W9CFCAMPIOBB','Campi obbligatori','Campi obbligatori','A2000',null,'NOTE','Campi obbligatori');

			Update W_CONFIG set valore='5.1.1' where chiave='it.eldasoft.sitatproxy.versioneTracciatoXML';
			Update W_CONFIG set valore='5.1.1' where chiave='it.eldasoft.rt.sitatproxy.versioneTracciatoXML';
			
			Update ELDAVER set NUMVER='200' where CODAPP='W9_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='200' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.2.1', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='W9_UPDATE';
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


