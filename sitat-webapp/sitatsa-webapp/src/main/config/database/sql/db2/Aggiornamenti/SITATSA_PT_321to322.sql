------ SCHEMA SITAT SA ----------
SET SCHEMA = SITATSA;
SET CURRENT PATH = SITATSA;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

--------------------------------------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 3.2.1 - aggiornamento di PT alla versione 3.2.2
--------------------------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='PT' and (numver='3.2.1' or numver like '3.2.1.%'))=1
	THEN

		-----------------------------------------------------------------------------------------------
		----------------------------      MODIFICA STRUTTURA TABELLE       ----------------------------
		-----------------------------------------------------------------------------------------------

		---  OPERE INCOMPIUTE
		DROP TABLE OITRI;
		SET v_sql = 
			'CREATE TABLE OITRI (
				CONTRI NUMERIC(10) NOT NULL,
				NUMOI NUMERIC(6) NOT NULL,
				CUP  VARCHAR(15),
				CUPMASTER  VARCHAR(15),
				DESCRIZIONE VARCHAR(2000),
				DETERMINAZIONI VARCHAR(5),
				AMBITOINT VARCHAR(5),
				ANNOULTQE  NUMERIC(4),
				IMPORTOINT NUMERIC(24,5),
				IMPORTOLAV NUMERIC(24,5),
				ONERIULTIM NUMERIC(24,5),
				IMPORTOSAL NUMERIC(24,5),
				AVANZAMENTO NUMERIC(13,9),
				CAUSA VARCHAR(5),
				FRUIBILE VARCHAR(1),
				STATOREAL VARCHAR(5),
				UTILIZZORID VARCHAR(1),
				REQ_CAP VARCHAR(1),
				REQ_PRGE VARCHAR(1),
				DIM_UM  VARCHAR(10),
				DIM_QTA  NUMERIC(24,5),
				DESTINAZIONEUSO VARCHAR(5),
				CESSIONE VARCHAR(1),
				INFRASTRUTTURA VARCHAR(1),
				VENDITA VARCHAR(1),
				DEMOLIZIONE VARCHAR(1), 
				ISTAT VARCHAR(20),
				NUTS VARCHAR(5),
				SEZINT VARCHAR(5),
				INTERV VARCHAR(6),
				SPONSORIZZAZIONE VARCHAR(1),
				FINANZA_PROGETTO VARCHAR(1),
				COSTO NUMERIC(24,5),
				FINANZIAMENTO NUMERIC(24,5),
				COP_STATALE VARCHAR(1),
				COP_REGIONALE VARCHAR(1),
				COP_PROVINCIALE VARCHAR(1),
				COP_COMUNALE VARCHAR(1),
				COP_ALTRAPUBBLICA VARCHAR(1),
				COP_COMUNITARIA VARCHAR(1),
				COP_PRIVATA VARCHAR(1),
				ONERI_SITO NUMERIC(24,5),
				PRIMARY KEY (CONTRI, NUMOI)
			)';

		SET v_sql = 'Alter table INTTRI DROP COLUMN IM1INT';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql = 'Alter table INTTRI DROP COLUMN IM2INT';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql = 'Alter table INTTRI DROP COLUMN IM3INT';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');

		SET v_sql='Alter table INTTRI ALTER COLUMN SEZINT set DATA TYPE VARCHAR(3)';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)';
		EXECUTE IMMEDIATE (v_sql);

		SET v_sql='Alter table INTTRI ADD IM9TRI NUMERIC(24,5)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');

		SET v_sql='Alter table OITRI ALTER COLUMN SEZINT set DATA TYPE VARCHAR(3)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE OITRI');

		SET v_sql='ALTER TABLE IMMTRAI ADD COLUMN NUMOI NUMERIC(6)';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table IMMTRAI ADD ALIENATI varchar(1)';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table IMMTRAI ADD  VA9IMM NUMERIC(24,5)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMMTRAI');

		SET v_sql='ALTER TABLE RIS_CAPITOLO ADD COLUMN CBNOTE VARCHAR(2000)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');

		--- Query di aggiornamento con cambio di tipo del campo: da NUMERIC a VARCHAR
		SET v_sql='Alter table PIATRI ADD COLUMN TEMP01 VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql='Update PIATRI SET TEMP01 = cast(trim(char(integer( PUBBLICA ))) as VARCHAR(1))';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table PIATRI DROP COLUMN PUBBLICA';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table PIATRI ADD COLUMN PUBBLICA VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql='Update PIATRI SET PUBBLICA = TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table PIATRI DROP COLUMN TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');

		SET v_sql='Alter table INRTRI ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)';
		EXECUTE IMMEDIATE (v_sql);
		
		--- Query di aggiornamento con cambio di tipo del campo: da NUMERIC a VARCHAR
		SET v_sql='Alter table INTTRI ADD COLUMN TEMP01 VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET TEMP01 = cast(trim(char(integer( FLAG_CUP ))) as VARCHAR(1))';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN FLAG_CUP';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ADD COLUMN FLAG_CUP VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET FLAG_CUP = TEMP01';
		EXECUTE IMMEDIATE (v_sql);

		--- Query di aggiornamento con cambio di tipo del campo: da NUMERIC a VARCHAR
		SET v_sql='Update INTTRI SET TEMP01 = NULL';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Update INTTRI SET TEMP01 = cast(trim(char(integer( DELEGA ))) as VARCHAR(1))';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN DELEGA';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ADD COLUMN DELEGA VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET DELEGA = TEMP01';
		EXECUTE IMMEDIATE (v_sql);

		--- Query di aggiornamento con cambio di tipo del campo: da NUMERIC a VARCHAR
		SET v_sql='Update INTTRI SET TEMP01 = NULL';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Update INTTRI SET TEMP01 = cast(trim(char(integer( COPFIN ))) as VARCHAR(1))';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN COPFIN';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ADD COLUMN COPFIN VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET COPFIN = TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');

		--- Query di aggiornamento con cambio di tipo del campo: da VARCHAR a NUMERIC
		SET v_sql='Alter table INTTRI ADD COLUMN TEMP01 NUMERIC(7)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET TEMP01 =  CAST( MATRIC AS INT )';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN MATRIC';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ADD COLUMN MATRIC NUMERIC(7)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET MATRIC = TEMP01';
		EXECUTE IMMEDIATE (v_sql);

		--- Query di aggiornamento con cambio di tipo del campo: da VARCHAR a NUMERIC
		SET v_sql='Update INTTRI SET TEMP01 = NULL';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET TEMP01 = case ACQALTINT when '1' Then 1 when '2' then 2 end';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN ACQALTINT';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI ADD COLUMN ACQALTINT NUMERIC(7)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql='Update INTTRI SET ACQALTINT = TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table INTTRI DROP COLUMN TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');

		--- Query di aggiornamento con cambio di tipo del campo: da NUMERIC a VARCHAR
		SET v_sql='Alter table RIS_CAPITOLO ADD COLUMN TEMP01 VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');
		SET v_sql='Update RIS_CAPITOLO SET TEMP01 = cast(trim(char(integer( VERIFBIL ))) as VARCHAR(1))';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table RIS_CAPITOLO DROP COLUMN VERIFBIL';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table RIS_CAPITOLO ADD COLUMN VERIFBIL VARCHAR(1)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');
		SET v_sql='Update RIS_CAPITOLO SET VERIFBIL = TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table RIS_CAPITOLO DROP COLUMN TEMP01';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');

		SET v_sql='Alter table PIATRI ADD COLUMN URLAPPROV VARCHAR(2000)';
		EXECUTE IMMEDIATE (v_sql);
		SET v_sql='Alter table PIATRI ADD COLUMN URLADOZI VARCHAR(2000)';
		EXECUTE IMMEDIATE (v_sql);
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		
		----------------------------------------------
		---   Aggiornamento dati Piani Triennali   ---
		----------------------------------------------
		
		Delete from TAB_CONTROLLI;
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  10,'PIATRI','Programma',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  20,'PIATRI','Dati generali','CENINT','EXISTS (SELECT 1 FROM UFFINT A WHERE A.CODEIN= T.CENINT and A.NOMEIN is not null)','Inserire l''Amministrazione','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  30,'PIATRI','Dati generali','RESPRO','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.RESPRO and A.NOMTEC is not null)','Indicare il responsabile del programma','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  40,'PIATRI','Dati generali','ID','T.ID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  50,'PIATRI','Dati generali','BRETRI','T.BRETRI is not null','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  60,'PIATRI','Dati generali','ANNTRI','T.ANNTRI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  70,'PIATRI','Dati generali','CONTRI','EXISTS (SELECT 1 FROM INTTRI I WHERE I.CONTRI=T.CONTRI)','Il programma non contiene interventi','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  80,'PIATRI','Archivio tecnici','TECNI.CITTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CITTEC is not null)','Indicare il codice ISTAT del comune del responsabile del programma','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',  90,'PIATRI','Archivio tecnici','TECNI.CITTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CFTEC is not null)','Indicare il codice fiscale del responsabile del programma','W');

		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 100,'INTTRI','Intervento n. {0} del programma',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 110,'INTTRI','Dati generali','CUIINT','T.CUIINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 120,'INTTRI','Dati generali','DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 130,'INTTRI','Dati generali','ANNRIF','T.ANNRIF is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 140,'INTTRI','Dati generali','FLAG_CUP','T.FLAG_CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 160,'INTTRI','Dati generali','INTTRI.ACQALTINT','T.ACQALTINT is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 170,'INTTRI','Dati generali','INTTRI.CUICOLL','T.CUICOLL is not null or T.ACQALTINT<>2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 180,'INTTRI','Dati generali','Codice ISTAT o NUTS del comune','T.COMINT is not null or T.TIPINT=2 or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 190,'INTTRI','Dati generali','NUTS','T.NUTS is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 200,'INTTRI','Dati generali','TIPOIN','T.TIPOIN is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 210,'INTTRI','Dati generali','CODCPV','T.CODCPV is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 220,'INTTRI','Dati generali','PRGINT','T.PRGINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 230,'INTTRI','Dati generali','CODRUP','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.CODRUP and A.NOMTEC is not null)','Indicare il responsabile dell''intervento','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 240,'INTTRI','Dati generali','LOTFUNZ','T.LOTFUNZ is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 250,'INTTRI','Dati generali','LAVCOMPL','T.LAVCOMPL is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 260,'INTTRI','Dati generali','DURCONT','T.DURCONT is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 270,'INTTRI','Dati generali','CONTESS','T.CONTESS is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 280,'INTTRI','Dati generali','SEZINT','T.SEZINT is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 290,'INTTRI','Dati generali','INTERV','T.INTERV is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 300,'INTTRI','Dati generali','ANNINT','T.ANNINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 310,'INTTRI','Dati generali','TIPCAPPRIV','T.TIPCAPPRIV is not null or coalesce(PR1TRI,0)+coalesce(PR2TRI,0)+coalesce(PR3TRI,0)+coalesce(PR9TRI,0)=0','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 360,'INTTRI','Acquisti verdi','ACQVERDI','T.ACQVERDI is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 370,'INTTRI','Acquisti verdi','NORRIF','T.NORRIF is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 380,'INTTRI','Acquisti verdi','AVOGGETT','T.AVOGGETT is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 390,'INTTRI','Acquisti verdi','AVCPV','T.AVCPV is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 400,'INTTRI','Acquisti verdi','AVIMPNET','T.AVIMPNET is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 410,'INTTRI','Acquisti verdi','AVIVA','T.AVIVA is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 420,'INTTRI','Acquisti verdi','AVIMPTOT','T.AVIMPTOT is not null or coalesce(T.ACQVERDI,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 430,'INTTRI','Materiali riciclati','MATRIC','T.MATRIC is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 440,'INTTRI','Materiali riciclati','MROGGETT','T.MROGGETT is not null or coalesce(T.MATRIC,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 450,'INTTRI','Materiali riciclati','MRCPV','T.MRCPV is not null or coalesce(T.MATRIC,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 460,'INTTRI','Materiali riciclati','MRIMPNET','T.MRIMPNET is not null or coalesce(T.MATRIC,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 470,'INTTRI','Materiali riciclati','MRIVA','T.MRIVA is not null or coalesce(T.MATRIC,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 480,'INTTRI','Materiali riciclati','MRIMPTOT','T.MRIMPTOT is not null or coalesce(T.MATRIC,0)<>3','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		                                                                                               
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 800,'OITRI','Opera incompiuta n. {0}',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 810,'OITRI',NULL,'NUMOI','T.NUMOI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 820,'OITRI',NULL,'CUP','T.CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 830,'OITRI',NULL,'DESCRIZIONE','T.DESCRIZIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 840,'OITRI',NULL,'DETERMINAZIONI','T.DETERMINAZIONI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 850,'OITRI',NULL,'AMBITOINT','T.AMBITOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 860,'OITRI',NULL,'ANNOULTQE','T.ANNOULTQE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 870,'OITRI',NULL,'IMPORTOINT','T.IMPORTOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 880,'OITRI',NULL,'IMPORTOLAV','T.IMPORTOLAV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 890,'OITRI',NULL,'ONERIULTIM','T.ONERIULTIM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 900,'OITRI',NULL,'IMPORTOSAL','T.IMPORTOSAL is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 910,'OITRI',NULL,'AVANZAMENTO','T.AVANZAMENTO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 920,'OITRI',NULL,'CAUSA','T.CAUSA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 930,'OITRI',NULL,'STATOREAL','T.STATOREAL is not null','Indicare lo stato di realizzazione','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 940,'OITRI',NULL,'INFRASTRUTTURA','T.INFRASTRUTTURA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 950,'OITRI',NULL,'FRUIBILE','T.FRUIBILE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 960,'OITRI',NULL,'UTILIZZORID','T.UTILIZZORID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 970,'OITRI',NULL,'DESTINAZIONEUSO','T.DESTINAZIONEUSO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 980,'OITRI',NULL,'CESSIONE','T.CESSIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT', 990,'OITRI',NULL,'VENDITA','T.VENDITA is not null','Indicare se &egrave; prevista la vendita','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1000,'OITRI',NULL,'DEMOLIZIONE','T.DEMOLIZIONE is not null or coalesce(T.VENDITA,1)=''1''','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1200,'IMMTRAI','Immobile da trasferire n. {0} dell''intervento',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1210,'IMMTRAI',NULL,'NUMIMM','T.NUMIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1220,'IMMTRAI',NULL,'CUIIMM','T.CUIIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1230,'IMMTRAI',NULL,'DESIMM','T.DESIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1240,'IMMTRAI',NULL,'Codice ISTAT o NUTS del comune','T.COMIST is not null or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1250,'IMMTRAI',NULL,'TITCOR','T.TITCOR is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1260,'IMMTRAI',NULL,'IMMDISP','T.IMMDISP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1270,'IMMTRAI',NULL,'PROGDISM','T.PROGDISM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1280,'IMMTRAI',NULL,'TIPDISP','T.TIPDISP is not null or T.NUMOI is null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1290,'IMMTRAI',NULL,'VALIMM','T.VALIMM is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1400,'INRTRI','Intervento non riproposto n. {0}',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1410,'INRTRI',NULL,'CUPPRG','T.CUPPRG is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1420,'INRTRI',NULL,'DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1430,'INRTRI',NULL,'TOTINT','T.TOTINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1440,'INRTRI',NULL,'PRGINT','T.PRGINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1450,'INRTRI',NULL,'MOTIVO','T.MOTIVO is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1600,'RIS_CAPITOLO','Risorse per capitolo di bilancio n. {0}',NULL,NULL,NULL,'T');
		Insert into TAB_CONTROLLI (CODAPP,NUM,ENTITA,SEZIONE,TITOLO,CONDIZIONE,MSG,TIPO) values ('PT',1610,'RIS_CAPITOLO',NULL,'NCAPBIL','T.NCAPBIL is not null','Valorizzare il campo','E');

		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.PIATRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.INRTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.INRTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.OITRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='NUMOI.OITRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='ID.CUPDATI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='ID.CUPLOC.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='NUM.CUPLOC.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.ECOTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONECO.ECOTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=3 WHERE COC_MNE_UNI='NUMIMM.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.INTTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.INTTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=3 WHERE COC_MNE_UNI='NUMCB.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='IDFLUSSO.PTFLUSSI.PIANI';

		Delete from C0CAMPI where COC_MNE_UNI in ('URCINT.INTTRI.PIANI','APCINT.INTTRI.PIANI', 'IM1INT.INTTRI.PIANI', 'IM2INT.INTTRI.PIANI', 'IM3INT.INTTRI.PIANI');
		Delete from C0CAMPI where coc_mne_uni like 'VA_TRI.IMMTRAI.PIANI';

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUMOI.IMMTRAI.PIANI','NUMOIIMMTRAI','Contatore opera (progressivo per programma)','Contatore opera','N6',null,null,'Contatore opera (progressivo per programma)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_QTA.OITRI.PIANI','DIMQTOITRP','Dimensionamento dell''opera (valore)','Dim.Val.Opera','F24.5',null,null,'Dimensionamento dell''opera (valore)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_UM.OITRI.PIANI','DIMUMOITRIP','Dimensionamento dell''opera (unita'' di misura)','Dim. opera','A15',null,null,'Dimensionamento dell''opera (unita'' di misura)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FINANZIAMENTO.OITRI.PIANI','FINANOITRP','Finanziamento assegnato','Fin. assegnato','F24.5',null,'MONEY','Finanziamento assegnato');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FINANZA_PROGETTO.OITRI.PIANI','FINPRGOITRI','Finanza di progetto','Fin. progetto','A2',null,'SN','Finanza di progetto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ISTAT.OITRI.PIANI','ISTATOIT','Codice ISTAT','Codice ISTAT','A20',null,null,'Codice ISTAT');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUTS.OITRI.PIANI','NUTCODOI','Codice NUTS','Codice NUTS','A5',null,null,'Codice NUTS');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'REQ_CAP.OITRI.PIANI','REQCAPOITRI','Opera rispondente a tutti i requisiti di capitolato?','Req. Capitolato?','A2',null,'SN','Opera rispondente a tutti i requisiti di capitolato?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'REQ_PRGE.OITRI.PIANI','REQPRGOITRI','Rispondente a tutti i requisiti ultimo progetto approvato?','Req.Ult.Progetto?','A2',null,'SN','Opera rispondente a tutti i requisiti dell''ultimo progetto approvato?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SPONSORIZZAZIONE.OITRI.PIANI','SPONSOITRIP','Sponsorizzazione','Sponsorizzazione','A2',null,'SN','Sponsorizzazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'INTERV.OITRI.PIANI','T2INTEOI','Classificazione intervento: categoria       (X_SCHS2005)','Categoria','A100','PTj01',null,'Classificazione intervento: categoria');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SEZINT.OITRI.PIANI','T2SEZOIT','Classificazione intervento: Tipologia       (X_SCHS2008)','Tipologia intervento','A100','PTx01',null,'Classificazione intervento: Tipologia');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COSTO.OITRI.PIANI','COSTOOITRP','Costo progetto','Costo progetto','F24.5',null,'MONEY','Costo progetto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_ALTRAPUBBLICA.OITRI.PIANI','CPAPOITRI','Altra Pubblica','Altra Pubblica','A2',null,'SN','Altra Pubblica');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_COMUNITARIA.OITRI.PIANI','CPCMOITRI','Comunitaria','Comunitaria','A2',null,'SN','Comunitaria');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_COMUNALE.OITRI.PIANI','CPCOOITRI','Comunale','Comunale','A2',null,'SN','Comunale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_PROVINCIALE.OITRI.PIANI','CPPROITRI','Provinciale','Provinciale','A2',null,'SN','Provinciale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_PRIVATA.OITRI.PIANI','CPPVOITRI','Privata','Privata','A2',null,'SN','Privata');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_REGIONALE.OITRI.PIANI','CPREOITRI','Regionale','Regionale','A2',null,'SN','Regionale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_STATALE.OITRI.PIANI','CPSTOITRI','Statale','Statale','A2',null,'SN','Statale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ONERI_SITO.OITRI.PIANI','ONESIOITRP','Oneri Sito','Oneri Sito','F24.5',null,'MONEY','Oneri Sito');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CBNOTE.RIS_CAPITOLO.PIANI','T2NOTEECB','Note','Note','A2000',null,'NOTE','Note');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URCINT.INTTRI.PIANI','T2URCINT','Svolta verifica conformità urbanistica?','Conforme urb.ter.?','A2',null,'SN','Svolta verifica conformità urbanistica?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'APCINT.INTTRI.PIANI','T2APCINT','Svolta verifica conformità vincoli ambientali?','Conforme vinc.amb.?','A2',null,'SN','Svolta verifica conformità vincoli ambientali?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA1IMM.IMMTRAI.PIANI','T2IVA1INT','Valore stimato dell''immobile - primo anno','Val. stimato 1'' anno','F15',null,'MONEY','Valore stimato dell''immobile - primo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA2IMM.IMMTRAI.PIANI','T2IVA2INT','Valore stimato dell''immobile - secondo anno ','Val. stimato 2'' anno','F15',null,'MONEY','Valore stimato dell''immobile - secondo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA3IMM.IMMTRAI.PIANI','T2IVA3INT','Valore stimato dell''immobile - terzo anno','Val. stimato 3'' anno','F15',null,'MONEY','Valore stimato dell''immobile - terzo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA9IMM.IMMTRAI.PIANI','T2IVA9INT','Valore stimato dell''immobile - annualità successive','Valore ann. succ.','F24.5',null,'MONEY','Valore stimato dell''immobile - annualità successive');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALIENATI.IMMTRAI.PIANI','T2ALIEN','Alienati per finanziamento e realizzazione di oopp','Alienati?','A2',null,'SN','Alienati per finanziamento e realizzazione di oopp');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IM9TRI.INTTRI.PIANI','T2IIM9TRI','Trasferimento immobili (annualità successive)','Imp. immob. succ.','F15',null,'MONEY','Trasferimento immobili (annualità successive)');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URLAPPROV.PIATRI.PIANI','T2URLAPPROV','Url atto di approvazione','Url atto approvaz.','A2000',null,null,'Url atto di approvazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URLADOZI.PIATRI.PIANI','T2URLADOZI','Url atto di adozione','Url atto adozione','A2000',null,null,'URL atto di adozione');
		
		Update C0CAMPI set COC_DES='Opera fruibile parzialmente?',COC_DES_WEB='Opera fruibile parzialmente?' where COC_MNE_UNI='FRUIBILE.OITRI.PIANI';
		Update C0CAMPI set COC_DES='Cessione o trasferimento immobile a titolo corrispettivo',COC_DES_WEB='Cessione o trasferimento immobile a titolo corrispettivo' where COC_MNE_UNI='TITCOR.IMMTRAI.PIANI';
		Update C0CAMPI set COC_DES='Concessi in diritto di godimento',COC_DES_WEB='Concessi in diritto di godimento',coc_des_frm='Diritto di godimento' where COC_MNE_UNI='IMMDISP.IMMTRAI.PIANI';
		Update C0CAMPI set COC_DES='Tipologia apporto di capitale privato' where COC_MNE_UNI='TCPINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_FS='A25' where COC_MNE_UNI='CUIINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_FS='A25' where COC_MNE_UNI='CUIINT.INRTRI.PIANI';
		Update C0CAMPI set c0c_fs='A100',c0c_tab1='PT022',coc_dom=null where COC_MNE_UNI='ACQALTINT.INTTRI.PIANI';
		
		Update C0CAMPI set COC_DES='Sono presenti acq. verdi art. 34 Dlgs 50/2016', COC_DES_WEB='Sono presenti acq. verdi art. 34 Dlgs 50/2016', COC_DES_FRM='Acq.VerdiAr.34-Dlgs' where coc_des='Intervento riguardante acquisti verdi?';
		Update C0CAMPI set COC_DES='Codice AUSA del Soggetto delegato', COC_DES_WEB='Codice AUSA del Soggetto delegato' where coc_des='Codice AUSA del Soggetto aggregatore';
		Update C0CAMPI set COC_DES='Denominazione del Soggetto delegato', COC_DES_WEB='Denominazione del Soggetto delegato' where coc_des='Denominazione del Soggetto aggregatore';

		Delete from  TAB1 where TAB1COD in ('PT010','PT011');
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',1,'Modifica ex art.7 comma 8 lettera b)','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',2,'Modifica ex art.7 comma 8 lettera c)','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',3,'Modifica ex art.7 comma 8 lettera d)','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',4,'Modifica ex art.7 comma 8 lettera e)','2',4,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',5,'Modifica ex art.7 comma 9','2',5,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',1,'Modifica ex art.5 comma 9 lettera b)','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',2,'Modifica ex art.5 comma 9 lettera c)','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',3,'Modifica ex art.5 comma 9 lettera d)','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',4,'Modifica ex art.5 comma 9 lettera e)','2',4,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',5,'Modifica ex art.5 comma 11','2',5,null);
		
		Delete from TAB1 where TAB1COD='PT016';
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',1,'Cessione della titolarità dell''opera ad altro ente pubblico',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',2,'Cessione della titolarità dell''opera a soggetto esercente una funzione pubblica',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',3,'Vendita al mercato privato',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',4,'Disponibilità come fonte di finanziamento per realizzazione di intervento ai sensi del c. 5 art.21',null,4.00,null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',1,'No','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',2,'Sì','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',3,'Sì, con CUI non ancora attribuito','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',4,'Sì, interventi o acquisti diversi','2',4,null);

		Update TAB5 set TAB5DESC='A01-1 - STRADALI' where TAB5COD='PTj01' and TAB5TIP='A01-1'; 
		Update TAB5 set TAB5DESC='A01-2 - AEROPORTUALI' where TAB5COD='PTj01' and TAB5TIP='A01-2'; 
		Update TAB5 set TAB5DESC='A01-3 - FERROVIE' where TAB5COD='PTj01' and TAB5TIP='A01-3'; 
		Update TAB5 set TAB5DESC='A01-4 - MARITTIME LACUALI E FLUVIALI' where TAB5COD='PTj01' and TAB5TIP='A01-4'; 
		Update TAB5 set TAB5DESC='A01-88 - ALTRE MODALITA'' DI TRASPORTO' where TAB5COD='PTj01' and TAB5TIP='A01-88';
		Update TAB5 set TAB5DESC='A02-11 - OPERE DI PROTEZIONE AMBIENTE' where TAB5COD='PTj01' and TAB5TIP='A02-11';
		Update TAB5 set TAB5DESC='A02-15 - RISORSE IDRICHE' where TAB5COD='PTj01' and TAB5TIP='A02-15';
		Update TAB5 set TAB5DESC='A02-5 - DIFESA DEL SUOLO' where TAB5COD='PTj01' and TAB5TIP='A02-5'; 
		Update TAB5 set TAB5DESC='A02-99 - ALTRE INFRASTRUTTURE PER AMBIENTE E TERRITORI' where TAB5COD='PTj01' and TAB5TIP='A02-99';
		Update TAB5 set TAB5DESC='A03-16 - PRODUZIONE E DISTRIBUZIONE DI ENERGIA NON ELET' where TAB5COD='PTj01' and TAB5TIP='A03-16';
		Update TAB5 set TAB5DESC='A03-6 - PRODUZIONE E DISTRIBUZIONE DI ENERGIA ELETTRIC' where TAB5COD='PTj01' and TAB5TIP='A03-6';
		Update TAB5 set TAB5DESC='A03-99 - ALTRE INFRASTRUTTURE DEL SETTORE ENERGETICO' where TAB5COD='PTj01' and TAB5TIP='A03-99';
		Update TAB5 set TAB5DESC='A04-13 - INFRASTRUTTURE PER L''AGRICOLTURA' where TAB5COD='PTj01' and TAB5TIP='A04-13';
		Update TAB5 set TAB5DESC='A04-14 - INFRASTRUTTURE PER LA PESCA' where TAB5COD='PTj01' and TAB5TIP='A04-14';
		Update TAB5 set TAB5DESC='A04-39 - INFRASTRUTTURE PER ATTIVITA'' INDUSTRIALI' where TAB5COD='PTj01' and TAB5TIP='A04-39';
		Update TAB5 set TAB5DESC='A04-40 - ANNONA, COMMERCIO E ARTIGIANATO' where TAB5COD='PTj01' and TAB5TIP='A04-40';
		Update TAB5 set TAB5DESC='A04-7 - TELECOMUNICAZIONE E TECNOLOGIE INFORMATICHE' where TAB5COD='PTj01' and TAB5TIP='A04-7';
		Update TAB5 set TAB5DESC='A05-10 - EDILIZIA ABITATIVA' where TAB5COD='PTj01' and TAB5TIP='A05-10';
		Update TAB5 set TAB5DESC='A05-11 - BENI CULTURALI' where TAB5COD='PTj01' and TAB5TIP='A05-11';
		Update TAB5 set TAB5DESC='A05-12 - SPORT E SPETTACOLO' where TAB5COD='PTj01' and TAB5TIP='A05-12';
		Update TAB5 set TAB5DESC='A05-30 - EDILIZIA SANITARIA' where TAB5COD='PTj01' and TAB5TIP='A05-30';
		Update TAB5 set TAB5DESC='A05-31 - CULTO' where TAB5COD='PTj01' and TAB5TIP='A05-31';
		Update TAB5 set TAB5DESC='A05-32 - DIFESA' where TAB5COD='PTj01' and TAB5TIP='A05-32';
		Update TAB5 set TAB5DESC='A05-33 - DIREZIONALE E AMMINISTRATIVO' where TAB5COD='PTj01' and TAB5TIP='A05-33';
		Update TAB5 set TAB5DESC='A05-34 - GIUDIZIARIO E PENITENZIARIO' where TAB5COD='PTj01' and TAB5TIP='A05-34';
		Update TAB5 set TAB5DESC='A05-35 - IGIENICO SANITARIO' where TAB5COD='PTj01' and TAB5TIP='A05-35';
		Update TAB5 set TAB5DESC='A05-36 - PUBBLICA SICUREZZA' where TAB5COD='PTj01' and TAB5TIP='A05-36';
		Update TAB5 set TAB5DESC='A05-37 - TURISTICO' where TAB5COD='PTj01' and TAB5TIP='A05-37';
		Update TAB5 set TAB5DESC='A05-8 - EDILIZIA SOCIALE E SCOLASTICA' where TAB5COD='PTj01' and TAB5TIP='A05-8';
		Update TAB5 set TAB5DESC='A05-9 - ALTRA EDILIZIA PUBBLICA' where TAB5COD='PTj01' and TAB5TIP='A05-9';
		Update TAB5 set TAB5DESC='A06-90 - ALTRE INFRASTRUTTURE PUBBLICHE N.C.A' where TAB5COD='PTj01' and TAB5TIP='A06-90';
		
		Update TAB1 set TAB1DESC='Concessione' where TAB1COD='PT009' and TAB1TIP=2;
		
		Update INTTRI set TIPINT= (select TIPROG from PIATRI P where P.CONTRI=INTTRI.CONTRI) where TIPINT is null;

		Update ELDAVER SET NUMVER = '3.2.2', DATVET=CURRENT_TIMESTAMP WHERE CODAPP='PT';

		COMMIT;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@



--#SET TERMINATOR ;

Update COMMAND OPTIONS USING s OFF;
DROP procedure ASSEGNA_GRANT;
Update COMMAND OPTIONS USING s ON;

--#SET TERMINATOR @

CREATE PROCEDURE ASSEGNA_GRANT ()
BEGIN
  DECLARE EOF INT DEFAULT 0;
  DECLARE S_SQL VARCHAR(200);
  DECLARE S_TABELLA VARCHAR(50);
  DECLARE S_USER VARCHAR(50);
  
  DECLARE C1 CURSOR FOR select TABNAME from SYSCAT.TABLES where TABSCHEMA =CURRENT SCHEMA and status='N';
  DECLARE C2 CURSOR FOR select SEQNAME from SYSCAT.SEQUENCES where SEQSCHEMA =CURRENT SCHEMA;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET EOF = 1;

	SET S_USER=CURRENT SCHEMA;
	SET S_USER=S_USER || ',SSELDAS';	--------		Utente Eldasoft		----------------
	SET S_USER=S_USER || ',LM07421';	--------	Utente Luca Menegatti	----------------

  OPEN C1;
  WHILE EOF = 0 DO
    FETCH FROM C1 INTO S_TABELLA;
  	SET S_SQL = 'GRANT SELECT,INSERT,UPDATE,DELETE ON TABLE ' || S_TABELLA || ' TO USER ' || S_USER;
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
Update COMMAND OPTIONS USING s ON@

--#SET TERMINATOR ;

