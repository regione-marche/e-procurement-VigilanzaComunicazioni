------ SCHEMA SITAT ORT ----------
SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @
--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 5.0.0 --> 5.0.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.0.0' or numver like '5.0.0.%')) = 1
	THEN

		------------------------------        
		----Aggiunzione campi FABBISOGNI
		SET v_sql = 'ALTER TABLE FABBISOGNI ADD COLUMN SOGGBUD VARCHAR(1)';		
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE FABBISOGNI ADD COLUMN TIPOSTUDIO NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE FABBISOGNI ADD COLUMN TIPOCONV NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE FABBISOGNI ADD COLUMN TIPORAPPR NUMERIC(7)';
		EXECUTE immediate v_sql;
		
		----Aggiunzione campi INTTRI
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IV3TRI NUMERIC(24,5)';		
		EXECUTE immediate v_sql;
		
		----Aggiunzione campi RIS_CAPITOLO
		SET v_sql = 'ALTER TABLE RIS_CAPITOLO ADD COLUMN IV3CB NUMERIC(24,5)';		
		EXECUTE immediate v_sql;
		

		--TAB1
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT026',1,'B',null,null,null);
        Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT026',2,'M',null,null,null);

		----Aggiornamento C0CAMPI 
		--FABBISOGNI
		Delete from C0CAMPI WHERE COC_MNE_UNI in ('SOGGBUD.FABBISOGNI.PIANI','TIPOSTUDIO.FABBISOGNI.PIANI','TIPOCONV.FABBISOGNI.PIANI','TIPORAPPR.FABBISOGNI.PIANI');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',null,'SOGGBUD.FABBISOGNI.PIANI','T2SOGGBUD','Acquisto soggetto a budget o mero monitoraggio','Budget/Monitoraggio?','A2',null,'SN',null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPOSTUDIO.FABBISOGNI.PIANI','T2TIPOSTUDIO','Tipologia studio/consulenza','Tipo studio/consul.','A100','PT026',null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPOCONV.FABBISOGNI.PIANI','T2TIPOCONV','Tipologia convegnistica','Tipo convegnistica','A100','PT026',null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPORAPPR.FABBISOGNI.PIANI','T2TIPORAPPR','Tipologia rappresentanza','Tipo rappresentanza','A100','PT026',null,null);
		--INTTRI
		Delete from C0CAMPI WHERE COC_MNE_UNI in ('IV3TRI.INTTRI.PIANI');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IV3TRI.INTTRI.PIANI','T2IV3TRIINT','Importo IVA (3''anno)','Importo IVA 3''anno','F24.5',null,'MONEY','Importo IVA (3''anno)');
		--RIS_CAPITOLO
		Delete from C0CAMPI WHERE COC_MNE_UNI in ('IV3CB.RIS_CAPITOLO.PIANI');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IV3CB.RIS_CAPITOLO.PIANI','T2IV3CB','Importo IVA (3''anno)','Importo IVA 3''anno','F24.5',null,'MONEY','Importo IVA (3''anno)');


		-- W_OGGETTI
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'ALT.W9.ModificheRER', 'Modifiche per Regione Emilia Romagna', 13540);
		
		-- W_AZIONI             
		Insert into W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) Values ('FUNZ', 'VIS', 'ALT.W9.ModificheRER', 'Visualizza', 0, 1, 1, 1930133945);
		
		--------------------------------		
		
		-- ELDAVER
		Update ELDAVER set NUMVER='5.0.1', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.0.0 --> 5.0.2
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.0.0' or numver like '5.0.0.%') ) = 1
	THEN

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------

		UPDATE C0CAMPI SET COC_DES='Numero gara SIMOG / Smart CIG', COC_DES_FRM='N. Gara/SmartCIG', COC_DES_WEB='Numero gara SIMOG / Smart CIG' where COC_MNE_UNI='ID_GARA.V_W3GARE.W3';
		UPDATE C0CAMPI SET COC_DES='Durata accordo quadro in giorni', COC_DES_WEB='Durata accordo quadro in giorni', COC_DES_FRM='Durata accordo quadr' where COC_MNE_UNI in ('DURATA_ACCQUADRO.W9GARA.W9','DURATA_ACCQUADRO.W9APPA.W9');
		
		Update W9CF_PUBB set NOME='Documentazione di gara - Bando di gara di appalto, concessione o concorso',CL_WHERE_VIS='(L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,6,8,20,27,29,30,34) OR PREV_BANDO=''1'')' where ID=3;
		Update W9CF_PUBB set NOME='Documentazione di gara - Lettera di invito',NUMORD=60,CL_WHERE_VIS='L.ID_SCELTA_CONTRAENTE in (2,4,10,15,19,28,29,30,32,34)' where ID=6;
		
		IF ( select count(*) from TAB1 where TAB1COD='W3031' and TAB1TIP=999 ) = 0
		THEN
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3031',999,'Lavori oppure beni e servizi non elencati nell''art. 1 dPCM 24 dicembre 2015');
		END IF;
		
		UPDATE ELDAVER set NUMVER='5.0.2', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

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


