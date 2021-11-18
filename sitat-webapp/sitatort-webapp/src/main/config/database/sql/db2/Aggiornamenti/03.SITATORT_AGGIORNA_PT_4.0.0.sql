------ SCHEMA SITAT ORT ----------
--#SET TERMINATOR ;

SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;



UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 3.2.1 --> 4.0.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '3.2.1' or numver like '3.2.1.%')) = 1
	THEN

		---  OPERE INCOMPIUTE
		SET v_sql= 'DROP TABLE OITRI';
		EXECUTE immediate v_sql;
		
		SET v_sql= 'CREATE TABLE OITRI (
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
			SEZINT VARCHAR(3),
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
		EXECUTE immediate v_sql;

		IF NOT EXISTS (SELECT * FROM SYSCAT.TABLES where TABSCHEMA =CURRENT SCHEMA AND TABNAME='TAB_CONTROLLI') THEN 
			SET v_sql= 'CREATE TABLE TAB_CONTROLLI (
				CODAPP VARCHAR(5) NOT NULL,
				NUM NUMERIC(7) NOT NULL,
				ENTITA VARCHAR(50),
				SEZIONE VARCHAR(50),
				TITOLO VARCHAR(100),
				CONDIZIONE VARCHAR(2000),
				MSG VARCHAR(2000),
				TIPO VARCHAR(1),
				PRIMARY KEY (CODAPP,NUM))';
			EXECUTE immediate v_sql;
		END IF;

		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM1INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM1INT';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM2INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM2INT';
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
			EXECUTE immediate v_sql;
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM3INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM3INT';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='TIPCAPPRIV') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN TIPCAPPRIV';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		
		SET v_sql= 'ALTER TABLE INTTRI
			ALTER COLUMN SEZINT set DATA TYPE VARCHAR(3)
			ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)
			ALTER COLUMN FIINTR set DATA TYPE VARCHAR(10)
			ADD COLUMN IM9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE INRTRI ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE IMMTRAI
			ADD COLUMN ALIENATI VARCHAR(1)
			ADD COLUMN VA9IMM NUMERIC(24,5)
			ADD COLUMN NUMOI NUMERIC(6)';
		EXECUTE immediate v_sql;
		
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN CBNOTE VARCHAR(2000)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE PIATRI
			ADD COLUMN URLAPPROV VARCHAR(2000)
			ADD COLUMN URLADOZI VARCHAR(2000)
			ADD COLUMN TITADOZI VARCHAR(250)
			ADD COLUMN TITAPPROV VARCHAR(250)
			ADD COLUMN DPADOZI TIMESTAMP
			ADD COLUMN DPAPPROV TIMESTAMP
			ADD COLUMN ID_GENERATO NUMERIC(10)
			ADD COLUMN ID_RICEVUTO NUMERIC(10)
			ADD COLUMN ID_CLIENT VARCHAR(255)
			ADD COLUMN SYSCON NUMERIC(12)
			ADD COLUMN IDUFFICIO NUMERIC(10)
			ALTER COLUMN NADOZI set DATA TYPE VARCHAR(50)
			ALTER COLUMN NAPTRI set DATA TYPE VARCHAR(50)';
		EXECUTE immediate v_sql;
		
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMMTRAI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');
		
		-- Modifica tipo di campo da numerico a varchar o viceversa
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN TEMP_PUBBLICA VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'Update PIATRI SET TEMP_PUBBLICA = CAST(CAST(PUBBLICA AS CHAR) AS VARCHAR(1))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN PUBBLICA';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN PUBBLICA VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update PIATRI SET PUBBLICA = TEMP_PUBBLICA';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN TEMP_PUBBLICA';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');

		SET v_sql= 'ALTER TABLE INTTRI 
									ADD COLUMN TEMP_FLAG_CUP VARCHAR(1)
									ADD COLUMN TEMP_DELEGA VARCHAR(1)
									ADD COLUMN TEMP_COPFIN VARCHAR(1)
									ADD COLUMN TEMP_MATRIC NUMERIC(7)
									ADD COLUMN TEMP_ACQALTINT NUMERIC(7)
									ADD COLUMN TEMP_VARIATO NUMERIC(7)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql= 'Update INTTRI SET TEMP_FLAG_CUP = CAST(CAST(FLAG_CUP AS CHAR) AS VARCHAR(1)),
									TEMP_DELEGA = CAST(CAST(DELEGA AS CHAR) AS VARCHAR(1)),
									TEMP_COPFIN = CAST(CAST(COPFIN AS CHAR) AS VARCHAR(1)),
									TEMP_MATRIC =  CAST(MATRIC AS INT ),
									TEMP_ACQALTINT = case CAST(CAST(ACQALTINT AS CHAR) AS VARCHAR(1)) when ''1'' Then 1 when ''2'' then 2 end,
									TEMP_VARIATO =  CAST(VARIATO AS INT)';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE INTTRI 
									DROP COLUMN FLAG_CUP
									DROP COLUMN DELEGA
									DROP COLUMN COPFIN
									DROP COLUMN MATRIC
									DROP COLUMN ACQALTINT
									DROP COLUMN VARIATO';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');	
		SET v_sql= 'ALTER TABLE INTTRI 
									ADD COLUMN FLAG_CUP VARCHAR(1)
									ADD COLUMN DELEGA VARCHAR(1)
									ADD COLUMN COPFIN VARCHAR(1)
									ADD COLUMN MATRIC NUMERIC(7)
									ADD COLUMN ACQALTINT NUMERIC(7)
									ADD COLUMN VARIATO NUMERIC(7)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql= 'Update INTTRI SET FLAG_CUP = TEMP_FLAG_CUP,
									DELEGA = TEMP_DELEGA,
									COPFIN = TEMP_COPFIN,
									MATRIC = TEMP_MATRIC,
									ACQALTINT = TEMP_ACQALTINT,
									VARIATO = TEMP_VARIATO';
		EXECUTE immediate v_sql;	
		SET v_sql= 'ALTER TABLE INTTRI 
									DROP COLUMN TEMP_FLAG_CUP
									DROP COLUMN TEMP_DELEGA
									DROP COLUMN TEMP_COPFIN
									DROP COLUMN TEMP_MATRIC
									DROP COLUMN TEMP_ACQALTINT
									DROP COLUMN TEMP_VARIATO';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');	
	
	
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN TEMP_VERIFBIL VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');	
		SET v_sql= 'Update RIS_CAPITOLO SET TEMP_VERIFBIL = CAST(CAST(VERIFBIL AS CHAR) AS VARCHAR(1))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO DROP COLUMN VERIFBIL';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');	
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN VERIFBIL VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');	
		SET v_sql= 'Update RIS_CAPITOLO SET VERIFBIL = TEMP_VERIFBIL';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO DROP COLUMN TEMP_VERIFBIL';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');	


		SET v_sql= 'CREATE VIEW V_REPORT_SOGG_AGGR as 
				SELECT u.cfein AS codicefiscaleente,
				cast (null as varchar(20)) AS codiceipa,
				u.nomein AS amministrazione,
				cast (null as varchar(20)) AS dipartimento,
				cast (null as varchar(20)) AS ufficio,
				s.tabdesc AS regione,
				u.proein AS provincia,
				COALESCE(u.viaein, '') || COALESCE(u.nciein, '') AS indirizzoente,
				u.telein AS telefonoente,
				u.emaiin AS mailente,
				u.emai2in AS pecente,
				t.nometei AS nomereferente,
				t.cogtei AS cognomereferente,
				t.cftec AS codicefiscalereferente,
				t.teltec AS telefonoreferente,
				t.ematec AS mailreferente,
				a.cuiint AS numerointerventocui,
				u.cfein AS codicefiscaleamministrazione,
				    CASE
				        WHEN substr(a.cuiint, 1, 1) in (''L'', ''F'', ''S'') THEN substr(a.cuiint, 13, 4)
				    END AS primaannualita,
				p.anntri + COALESCE(a.annrif, 0) - 1 AS anno,
				cast(''001'' as varchar(3))  AS identificativoproceduraacquisto,
				a.cupprg AS codicecup,
				    CASE
				        WHEN a.lotfunz = ''1'' THEN ''Si''
				        WHEN a.lotfunz = ''2'' THEN ''No''
				    END AS lottofunzionale,
				round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS importostimatolotto,
				n.descrizione AS ambitogeografico,
				a.cupmst AS codicecupmaster,
				t2.tab2d2 AS settore,
				a.codcpv AS cpv,
				a.desint AS descrizioneacquisto,
				cast (null as varchar(2)) AS conformitaambientale,
				    CASE
				        WHEN a.prgint IS NOT NULL THEN ''Livello '' || cast(a.prgint as char(1))
				    END AS priorita,
				rup.cftec AS codiceriscaleresponsabileprocedimento,
				rup.cogtei AS cognomeresponsabileprocedimento,
				rup.nometei AS nomeresponsabileprocedimento,
				a.quantit AS quantita,
				um.tab1desc AS unitamisura,
				a.durcont AS duratacontratto,
				round(COALESCE(a.di1int, 0) + COALESCE(a.pr1tri, 0), 2) AS stimacostiprogrammaprimoanno,
				round(COALESCE(a.di2int, 0) + COALESCE(a.pr2tri, 0), 2) AS stimacostiprogrammasecondoanno,
				round(COALESCE(a.di9int, 0) + COALESCE(a.pr9tri, 0), 2) AS costiannualitasuccessive,
				round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS stimacostiprogrammatotale,
				round(a.icpint, 2) AS importoapportocapitaleprivato,
				t3.tab3desc AS tipologiaapportocapitaleprivato,
				    CASE
				        WHEN a.delega = ''1'' THEN ''Si''
				        WHEN a.delega = ''2'' THEN ''No''
				    END AS delega,
				a.codausa AS codiceausa,
				a.sogagg AS denominazioneamministrazionedelegata,
				p.contri
		   FROM piatri p
				 JOIN uffint u ON u.codein = p.cenint
				 LEFT JOIN tecni t ON t.codtec = p.respro
				 LEFT JOIN inttri a ON a.contri = p.contri
				 LEFT JOIN tabnuts n ON n.codice = substr(a.nuts, 1, 4)
				 LEFT JOIN tab2 t2 ON t2.tab2cod = ''W3z05'' AND t2.tab2tip = a.tipoin
				 LEFT JOIN tecni rup ON rup.codtec = a.codrup
				 LEFT JOIN tab3 t3 ON t3.tab3cod = ''PTx08'' AND t3.tab3tip = a.tcpint
				 LEFT JOIN tabsche s ON s.tabcod = ''S2003'' AND s.tabcod1 = ''05'' AND s.tabcod2 = (''0'' || substr(u.codcit, 1, 3))
				 LEFT JOIN tab1 um ON um.tab1cod = ''PT019'' AND um.tab1tip = a.unimis
		  WHERE p.tiprog = 2 AND (COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0)) > 1000000';
		EXECUTE immediate v_sql;
		  


		Update ELDAVER set NUMVER='4.0.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';


	END IF;
end@

call aggiorna@
drop procedure aggiorna@
