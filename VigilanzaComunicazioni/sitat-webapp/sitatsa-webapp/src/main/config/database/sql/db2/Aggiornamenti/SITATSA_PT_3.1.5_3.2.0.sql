--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione W9: 3.0.0
-- Versione PT= 3.1.5 --> 3.2.0
--------------------------------

SET SCHEMA = SITATSA;
SET CURRENT PATH = SITATSA;

UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='PT' and (numver='3.1.5' or numver like '3.1.5%') ) = 1
	THEN
	
		-----------------------------------------------------------------------------------------------
		----------------------------      MODIFICA STRUTTURA TABELLE       ----------------------------
		-----------------------------------------------------------------------------------------------

		--- INTERVENTI NON RIPROPOSTI
		SET v_sql= 
			'CREATE TABLE INRTRI(
				CONTRI NUMERIC(10) NOT NULL,
				CONINT NUMERIC(5) NOT NULL,
				CUIINT VARCHAR(20), 
				CUPPRG VARCHAR(15), 
				DESINT VARCHAR(2000),
				TOTINT NUMERIC(24,5), 
				PRGINT NUMERIC(2), 
				MOTIVO VARCHAR(2000), 
				PRIMARY KEY (CONTRI, CONINT)
			)';
		EXECUTE immediate v_sql;

		---  OPERE INCOMPIUTE
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
				FRUIBILE NUMERIC(1),
				STATOREAL VARCHAR(5),
				UTILIZZORID NUMERIC(1),
				DESTINAZIONEUSO VARCHAR(5),
				CESSIONE NUMERIC(1),
				INFRASTRUTTURA NUMERIC(1),
				VENDITA NUMERIC(1),
				DEMOLIZIONE NUMERIC(1),
				PRIMARY KEY (CONTRI, NUMOI)
			)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE PIATRI ADD COLUMN NORMA NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE PIATRI ADD COLUMN AP1TRI NUMERIC(24)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE PIATRI ADD COLUMN AP2TRI NUMERIC(24)';
		EXECUTE immediate v_sql;	
		SET v_sql = 'ALTER TABLE PIATRI ADD COLUMN AP3TRI NUMERIC(24)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql = 'UPDATE PIATRI SET NORMA=1';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN PRIMANN NUMERIC(5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN ACQALTINT VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN CUICOLL VARCHAR(25)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN LOTFUNZ VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN LAVCOMPL VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DURCONT NUMERIC(3)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN CONTESS VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN SCAMUT TIMESTAMP';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN VARIATO VARCHAR(1)';
		
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DV9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MU9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN PR9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN BI9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AP1TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AP2TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;	
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AP3TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AP9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AL9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DI9INT NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MESE NUMERIC(2)';
		EXECUTE immediate v_sql;		
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DIRGEN VARCHAR(160)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN STRUOP VARCHAR(160)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN REFERE VARCHAR(160)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN RESPUF VARCHAR(160)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN QUANTIT NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN UNIMIS NUMERIC(2)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN PROAFF NUMERIC(2)';
		EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DELEGA NUMERIC(1)';
		EXECUTE immediate v_sql; 
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN CODAUSA VARCHAR(20)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN SOGAGG VARCHAR(160)';
		EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN ACQVERDI NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVNORMA VARCHAR(160)';
		EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVOGGETT VARCHAR(500)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVCPV	VARCHAR(12)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVIMPNET NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVIVA	NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AVIMPTOT NUMERIC(24,5)';
		EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MATRIC VARCHAR(160)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MROGGETT VARCHAR(500)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MRCPV	VARCHAR(12)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MRIMPNET NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MRIVA	NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN MRIMPTOT NUMERIC(24,5)';
		EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IV1TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IV2TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IV9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN COPFIN NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN NCAPBIL VARCHAR(100)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IMPSPE VARCHAR(100)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN IMPALT NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ALTER COLUMN INTERV SET DATA TYPE VARCHAR(6)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD('REORG TABLE INTTRI');

		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN CUIIMM VARCHAR(30)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN COMIST VARCHAR(9)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN NUTS VARCHAR(5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN TITCOR NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN IMMDISP NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN PROGDISM NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN TIPDISP NUMERIC(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN VA1IMM NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN VA2IMM NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMMTRAI ADD COLUMN VA3IMM NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD('REORG TABLE IMMTRAI');
		
		----------------------------------------------
		---   Aggiornamento dati Piani Triennali   ---
		----------------------------------------------

		--- C0ENTIT
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) VALUES ('INRTRI.PIANI',80030300,'E','INTNONRIPR','Interventi non riproposti','CONTRI.INRTRI.PIANI,CONINT.INRTRI.PIANI','PIATRI.PIANI','CONTRI.PIATRI.PIANI',null,null,'pt_inrtri');
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) VALUES ('OITRI.PIANI',80030300,'E','OPERINCOMP','Opere incompiute','CONTRI.OITRI.PIANI,CONINT.OITRI.PIANI','PIATRI.PIANI','CONTRI.PIATRI.PIANI',null,null,'pt_oitri');
		
		--- C0CAMPI per INRTRI ---
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','CONTRI.INRTRI.PIANI','T2CONTINR','Numero progressivo del programma','N.progr.piano trien.','N10',null,null,'Numero progressivo del programma');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','CONINT.INRTRI.PIANI','T2CONINR','Numero progressivo dell''intervento','N.progr.intervento','N5',null,null,'Numero progressivo dell''intervento');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CUIINT.INRTRI.PIANI','T2CUIINR','Codice CUI','Codice CUI','A20',null,null,'Codice CUI');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CUPPRG.INRTRI.PIANI','T2CUPINR','Codice CUP di progetto (assegnato da CIPE)','Cod. CUP Progetto','A15',null,null,'Codice CUP di progetto (assegnato da CIPE)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DESINT.INRTRI.PIANI','T2DESINR','Descrizione dell''intervento','Descrizione','A2000',null,null,'Descrizione dell''intervento');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'TOTINT.INRTRI.PIANI','T2DITINR','Importo complessivo','Imp. complessivo','F15',null,'MONEY','Importo complessivo');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'PRGINT.INRTRI.PIANI','T2PRGINR','Priorita'' dell''intervento in generale (alta/media/bassa)','Priorita'' int.gener.','A100','PT008',null,'Priorita'' dell''intervento in generale');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'MOTIVO.INRTRI.PIANI','T2INRNMOT','Motivo per il quale l’intervento non è riproposto','Motivo','A2000',null,'NOTE','Motivo per il quale l’intervento non è riproposto');

		--- C0CAMPI per OITRI ---
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','CONTRI.OITRI.PIANI','CONTRIOITRI','Codice programma','Codice programma','N10',null,null,'Codice programma');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','NUMOI.OITRI.PIANI','NUMOIOITRIP','Contatore opera (progressivo per programma)','Contatore opera','N6',null,null,'Contatore opera (progressivo per programma)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CUP.OITRI.PIANI','CUPOITRIPIA','CUP','CUP','A15',null,null,'CUP');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CUPMASTER.OITRI.PIANI','CUPMASTEROI','CUP master','CUP master','A15',null,null,'CUP master');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DESCRIZIONE.OITRI.PIANI','DESCROITRIP','Descrizione opera','Descr. opera','A2000',null,null,'Descrizione opera');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DETERMINAZIONI.OITRI.PIANI','DETERMOITRI','Determinazioni dell''amministrazione','Det. amministraz.','A5','PTx02',null,'Determinazioni dell''amministrazione');   								
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AMBITOINT.OITRI.PIANI','AMBIINTOITR','Ambito di interesse dell''opera','Ambito interesse','A5','PTx04',null,'Ambito di interesse dell''opera');							
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'ANNOULTQE.OITRI.PIANI','ANNULTQEOIT','Anno ultimo q.e. approvato','Anno ultimo q.e.','N4',null,null,'Anno ultimo q.e. approvato');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPORTOINT.OITRI.PIANI','IMPINTOITRP','Importo complessivo dell''intervento','Imp.c. intervento','F15 ',null,'MONEY','Importo complessivo dell''intervento');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPORTOLAV.OITRI.PIANI','IMPLAVOITRP','Importo complessivo lavori','Imp.c. lavori','F15',null,'MONEY','Importo complessivo lavori');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'ONERIULTIM.OITRI.PIANI','ONERULTOITR','Oneri necessari per l''ultimazione dei lavori','Oneri ult. lavori','F15',null,'MONEY','Oneri necessari per l''ultimazione dei lavori');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPORTOSAL.OITRI.PIANI','IMPSALOITRP','Importo ultimo SAL','Importo ultimo SAL','F15',null,'MONEY','Importo ultimo SAL');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AVANZAMENTO.OITRI.PIANI','AVANZOITRIP','Percentuale avanzamento lavori','Perc. avan. lavori','F15',null,'PRC','Percentuale avanzamento lavori');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CAUSA.OITRI.PIANI','CAUSAOITRIP','Causa per la quale l''opera è incompiuta','Causa incompiuta','A5','PTx05',null,'Causa per la quale l''opera è incompiuta');							
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'FRUIBILE.OITRI.PIANI','FRUIBOITRIP','Opera fruibile?','Opera fruibile?','A2',null,'SN','Opera fruibile?');							
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'STATOREAL.OITRI.PIANI','STATREALOIT','Stato di realizzazione','Stato realiz.','A5','PTx06',null,'Stato di realizzazione');						
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'UTILIZZORID.OITRI.PIANI','UTILRIDOITR','Utilizzo ridimensionato?','U. ridimensionato','A2',null,'SN','Utilizzo ridimensionato?');   								
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DESTINAZIONEUSO.OITRI.PIANI','DESTUSOOITR','Destinazione d''uso','Dest. uso','A5','PTx07',null,'Destinazione d''uso');						   
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CESSIONE.OITRI.PIANI','CESSOITRIPI','Cessione per realizzazione di altra opera?','Cess. per alt. opera','A2',null,'SN','Cessione per realizzazione di altra opera?');	   								
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'INFRASTRUTTURA.OITRI.PIANI','INFRASTROIT','Parte di infrastruttura di rete?','Parte infras.rete','A2',null,'SN','Parte di infrastruttura di rete?');   								
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'VENDITA.OITRI.PIANI','VENDOITRIPI','Prevista la vendita','Prevista vendita','A2',null,'SN','Prevista la vendita');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DEMOLIZIONE.OITRI.PIANI','DEMOLOITRIP','Opera da demolire?','Opera demolire','A2',null,'SN','Opera da demolire?');						

		UPDATE TAB1 set TAB1DESC='Programma di lavori' where TAB1COD='W9002' and TAB1TIP=1;
		UPDATE TAB1 set TAB1DESC='Programma di forniture e servizi' where TAB1COD='W9002' and TAB1TIP=2;

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'NORMA.PIATRI.PIANI','T2NORMA','Normativa di riferimento','Norma di riferimento','A100','PT017',null,'Normativa di riferimento');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP1TRI.PIATRI.PIANI','T2AP1TRI','Finanziamenti art. 3 DL 310/1990 (1''anno)','Imp.finanz.1''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (1''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP2TRI.PIATRI.PIANI','T2AP2TRI','Finanziamenti art. 3 DL 310/1990 (2''anno)','Imp.finanz.2''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (2''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP3TRI.PIATRI.PIANI','T2AP3TRI','Finanziamenti art. 3 DL 310/1990 (3''anno)','Imp.finanz.3''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (3''anno)');
		
		INSERT INTO TAB1 (tab1cod,tab1tip,tab1desc,tab1mod,tab1nord,tab1arc) VALUES ('PT017',1,'DLgs 163/2006',null,1.00,null);
		INSERT INTO TAB1 (tab1cod,tab1tip,tab1desc,tab1mod,tab1nord,tab1arc) VALUES ('PT017',2,'DLgs 50/2016', null,2.00,null);
		
		--- C0CAMPI per INTTRI
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'PRIMANN.INTTRI.PIANI', 'T2PRIMA', 'Anno in cui l''acquisto è stato inserito nel programma','Anno inserim. acqui','N4',null,null,'Anno inserimento acquisto');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'ACQALTINT.INTTRI.PIANI','T2ACQALT', 'Acquisto ricompreso nell''importo di lavoro o altra acquisto?','Acquisto altro inter','A2',null,'SN','Acquisto ricompreso nell''importo di lavoro o altra acquisizione nel programma?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'CUICOLL.INTTRI.PIANI', 'T2CUICO', 'Codice CUI dell''intervento collegato','Cod. CUI inter. coll','A25',null,null,'Codice CUI dell''intervento collegato');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'LOTFUNZ.INTTRI.PIANI', 'T2LOTFU', 'Lotto funzionale?','Lotto funzionale?','A2',null,'SN','Lotto funzionale?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'LAVCOMPL.INTTRI.PIANI','T2LAVCO','Lavoro complesso?','Lavoro complesso?','A2',null,'SN','Lavoro complesso?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'DURCONT.INTTRI.PIANI', 'T2DURCO', 'Durata del contratto (mesi)','Durata contratto','N3',null,null,'Durata del contratto (mesi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'CONTESS.INTTRI.PIANI', 'T2CONTE', 'Nuovo affidamento contratto in essere?','Nuovo affidamento?','A2',null,'SN','Nuovo affidamento contratto in essere?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'SCAMUT.INTTRI.PIANI',  'T2SCAMU',  'Scadenza utilizzo finanziamento da mutuo','Scad.utilizzo finanz','A10',null,'DATA_ELDA','Scadenza finanziamento da mutuo');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'VARIATO.INTTRI.PIANI', 'T2VARIA', 'Intervento variato a seguito di modifica programma','Interv.var. per mod.','A2',null,'SN','Intervento variato a seguito di modifica programma');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'DV9TRI.INTTRI.PIANI',  'T2DV9INT','Entrate aventi destinazione vincolata per legge (anni succ)','Imp.dest.vinc. suc','F15',null,'MONEY','Entrate con destin. vincolata per legge (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MU9TRI.INTTRI.PIANI',  'T2MU9INT','Entrate acquisite mediante contrazione di mutuo (anni succ)',  'Imp.acquis.mutuo suc','F15',null,'MONEY','Entrate con contrazione di mutuo (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'PR9TRI.INTTRI.PIANI',  'T2PR9INT','Entrate acquisite con apporti di capitale priv. (anni succ)','Imp.capit.priv. succ','F15',null,'MONEY','Entrate con apporti di capitale privato (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'BI9TRI.INTTRI.PIANI',  'T2BI9INT','Stanziamenti di bilancio (anni successivi)','Imp.stanz.bilan.succ','F15',null,'MONEY','Stanziamenti di bilancio anni successivi"');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP1TRI.INTTRI.PIANI',  'T2AP1INT','Finanziamenti art. 3 DL 310/1990 (1''anno)','Imp.finanz.1''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (1''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP2TRI.INTTRI.PIANI',  'T2AP2INT','Finanziamenti art. 3 DL 310/1990 (2''anno)','Imp.finanz.2''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (2''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP3TRI.INTTRI.PIANI',  'T2AP3INT','Finanziamenti art. 3 DL 310/1990 (3''anno)','Imp.finanz.3''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (3''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AP9TRI.INTTRI.PIANI',  'T2AP9INT','Finanziamenti art. 3 DL 310/1990 (anni successivi)','Imp.finanz. anni suc','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AL9TRI.INTTRI.PIANI',  'T2AL9INT','Altre risorse disponibili (anni successivi)','Imp.altre risors.suc','F15',null,'MONEY','Altre risorse disponibili (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'DI9INT.INTTRI.PIANI',  'T2DI9INT','Imp.disp. finanziaria al netto di capitali priv.(anni succ)','Imp.disp.finanz. suc','F15',null,'MONEY','Imp.disp. finanziaria al netto di capitali privati (anni successivi)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IMPALT.INTTRI.PIANI',  'T2IMPALT','Importo risorse finanziarie altro','Imp.risorse.finan.al','F15',null,'MONEY','Importo risorse finanziarie altro');

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'TIPCAPPRIV.INTTRI.PIANI','T2TCAPPRIV','Tipologia apporto di capitale privato', 'Tipo Capitale privat','A500',null,null,'Tipologia apporto di capitale privato');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'VERIFBIL.INTTRI.PIANI','T2VERBIL','Verifica coerenza con bilancio da parte dell''ufficio?','Coerente con bilanc?','A2',null,'SN','Verifica coerenza con bilancio da parte dell''ufficio?');
		
		UPDATE C0CAMPI SET COC_DES='Settore',COC_DES_FRM='Settore',COC_DES_WEB='Settore' WHERE COC_MNE_UNI ='TIPOIN.INTTRI.PIANI';
		UPDATE C0CAMPI SET COC_DES='Livello di priorità',COC_DES_FRM='Livello priorità',COC_DES_WEB='Livello di priorità', C0C_TAB1='PT008' WHERE COC_MNE_UNI ='PRGINT.INTTRI.PIANI';

		UPDATE C0CAMPI SET C0C_TAB1='PT008' WHERE COC_MNE_UNI='PRGINT.INRTRI.PIANI';

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'CUIIMM.IMMTRAI.PIANI', 'T2CUIIMM','Codice univoco immobile','Cod. unico immobile','A30',null,null,'Codice univoco immobile');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'COMIST.IMMTRAI.PIANI', 'T2COMIST','Codice ISTAT Comune','Codice ISTAT Comune','A9',null,null,'Codice ISTAT Comune');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'NUTS.IMMTRAI.PIANI',   'T2NUTS01','Codice NUTS','Codice NUTS','A5',null,null,'Codice NUTS');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'TITCOR.IMMTRAI.PIANI', 'T2TITCOR','Trasferimento immobile a titolo corrispettivo','Trasf.imm.corrispet.','A100','PT013',null,'Trasferimento immobile a titolo corrispettivo');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IMMDISP.IMMTRAI.PIANI','T2IMMDIS','Immobile disponibile art. 21-c5','Immobile disp. art21','A100','PT014',null,'Immobile disponibile art. 21 c5');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'PROGDISM.IMMTRAI.PIANI','T2PROGDI','Già incluso in programma di dismissione art. 27 DL 201/2011','Prog.dismis.DL201/11','A100','PT015',null,'Già incluso in programma di dismissione art. 27 DL 201/2011');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'TIPDISP.IMMTRAI.PIANI','T2TIPDIS','Tipo disponibilità','Tipo disponibilità','A100','PT016',null,'Tipo disponibilità');
		
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'VA1TRI.IMMTRAI.PIANI',  'T2VA1INT','Valore stimato - primo anno',  'Val.stimato 1''anno','F15',null,'MONEY','Valore stimato - primo anno' );
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'VA2TRI.IMMTRAI.PIANI',  'T2VA2INT','Valore stimato - secondo anno','Val.stimato 2''anno','F15',null,'MONEY','Valore stimato - secondo anno');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'VA3TRI.IMMTRAI.PIANI',  'T2VA3INT','Valore stimato - terzo anno',  'Val.stimato 3''anno','F15',null,'MONEY','Valore stimato - terzo anno');
		
		--- TABELLATI ---

		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx02','a','Insussistenza dell''interesse pubblico a completamento e fruibilità',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx02','b','Si intende riprendere l''esecuzione avendo già reperito i finanziamenti',null,2,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx02','c','Si intende riprendere l''esecuzione una volta reperiti i finanziamenti',null,3,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx02','d','Si intende riprendere l''esecuzione senza finanziamenti aggiuntivi',null,4,null);
																						   
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx04','a','Nazionale',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx04','b','Regionale',null,2,null);
																						 
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','a','Mancanza di fondi',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','b1','Sospensione lavori o esigenza una variante per circostanze speciali',null,2,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','b2','Presenza di contenzioso',null,3,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','c','Sopravvenute nuove norme tecniche o disposizioni di legge',null,4,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','d','Fallimento, liq. coatta o concordato preventivo impresa, risol. o recesso contratto',null,5,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx05','e','Mancato interesse al completamento',null,6,null);
																					
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx06','a','Lavori interrotti oltre il termine per l''ultimazione',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx06','b','Lavori interrotti oltre il termine per l''ultimazione senza condizioni di riavvio',null,2,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx06','c','Lavori ultimati ma non collaudati nel termine previsto',null,3,null);
																					   
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx07','a','Prevista in progetto',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx07','b','Diversa da quella prevista in progetto',null,2,null);

		UPDATE C0CAMPI SET C0C_TAB1='PTx01' WHERE COC_MNE_UNI = 'SEZINT.INTTRI.PIANI';
		UPDATE C0CAMPI SET C0C_TAB1='PTj01' WHERE COC_MNE_UNI = 'INTERV.INTTRI.PIANI';
		UPDATE C0CAMPI SET C0C_TAB1='PT009' WHERE COC_MNE_UNI = 'TCPINT.INTTRI.PIANI';
		UPDATE C0CAMPI SET C0C_TAB1='PTx03' WHERE COC_MNE_UNI = 'FIINTR.INTTRI.PIANI';
		UPDATE C0CAMPI SET C0C_TAB1='PT012' WHERE COC_MNE_UNI = 'STAPRO.INTTRI.PIANI';

		--- Tipologia interventi ---
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','01','Nuova Costruzione',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','01a','Ampliamento',null,2,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','02','Demolizione',null,3,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','03','Recupero',null,4,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','04','Ristrutturazione',null,5,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','05','Restauro',null,6,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','06','Risanamento conservativo',null,7,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','07','Manutenzione straordinaria',null,8,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','08','Manutenzione ordinaria',null,9,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx01','09','Completamento',null,10,null);

		--- Categoria interventi ---
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A01-1', 'STRADALI',null,1,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A01-2', 'AEROPORTUALI',null,2,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A01-3', 'FERROVIE',null,3,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A01-4', 'MARITTIME LACUALI E FLUVIALI',null,4,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A01-88','ALTRE MODALITA'' DI TRASPORTO',null,5,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A02-5', 'DIFESA DEL SUOLO',null,6,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A02-11','OPERE DI PROTEZIONE AMBIENTE',null,7,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A02-15','RISORSE IDRICHE',null,8,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A02-99','ALTRE INFRASTRUTTURE PER AMBIENTE E TERRITORI',null,9,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A03-6', 'PRODUZIONE E DISTRIBUZIONE DI ENERGIA ELETTRIC',null,10,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A03-16','PRODUZIONE E DISTRIBUZIONE DI ENERGIA NON ELET',null,11,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A03-99','ALTRE INFRASTRUTTURE DEL SETTORE ENERGETICO',null,12,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A04-7', 'TELECOMUNICAZIONE E TECNOLOGIE INFORMATICHE',null,13,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A04-13','INFRASTRUTTURE PER L''AGRICOLTURA',null,14,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A04-14','INFRASTRUTTURE PER LA PESCA',null,15,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A04-39','INFRASTRUTTURE PER ATTIVITA'' INDUSTRIALI',null,16,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A04-40','ANNONA, COMMERCIO E ARTIGIANATO',null,17,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-8', 'EDILIZIA SOCIALE E SCOLASTICA',null,18,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-9', 'ALTRA EDILIZIA PUBBLICA',null,19,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-10','EDILIZIA ABITATIVA',null,20,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-11','BENI CULTURALI',null,21,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-12','SPORT E SPETTACOLO',null,22,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-30','EDILIZIA SANITARIA',null,23,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-31','CULTO',null,24,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-32','DIFESA',null,25,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-33','DIREZIONALE E AMMINISTRATIVO',null,26,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-34','GIUDIZIARIO E PENITENZIARIO',null,27,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-35','IGIENICO SANITARIO',null,28,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-36','PUBBLICA SICUREZZA',null,29,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A05-37','TURISTICO',null,30,null);
		INSERT INTO TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES ('PTj01','A06-90','ALTRE INFRASTRUTTURE PUBBLICHE N.C.A',null,31,null);

		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','ADN','Adeguamento normativo',null,1,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','AMB','Qualità ambientale',null,2,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','COP','Completamento Opera incompiuta',null,3,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','CPA','Conservazione patrimonio',null,4,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','MIS','Miglioramento e incremento di servizio',null,5,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','URB','Qualità urbana',null,6,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','VAB','Valorizzazione beni vincolati',null,7,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','DEM','Demolizione Opera incompiuta',null,8,null);
		INSERT INTO TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES ('PTx03','DEOP','Demolizione opere preesistenti e non più utilizzabili',null,9,null);

		--- Priorita' dell'intervento ---
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT008',1,'Priorità massima',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT008',2,'Priorità media',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT008',3,'Priorità minima',null,2.00,null);

		--- Tipologia apporto di capitale privato ---
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',1,'Finanza di progetto',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',2,'Concessione di costruzione e gestione',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',3,'Sponsorizzazione',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',4,'Società partecipate o di scopo',null,4.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',5,'Locazione finanziaria',null,5.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT009',6,'Altro',null,6.00,null);

		--- Intervento aggiunto o variato a seguito modifica programma ---
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT010',1,'Modifica ex art.5 comma 8 lettera b)',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT010',2,'Modifica ex art.5 comma 8 lettera c)',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT010',3,'Modifica ex art.5 comma 8 lettera d)',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT010',4,'Modifica ex art.5 comma 8 lettera e)',null,4.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT010',5,'Modifica ex art.5 comma 10',null,5.00,null);

		--- Acquisto aggiunto o variato a seguito modifica programma ---
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT011',1,'Modifica ex art.7 comma 7 lettera b)',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT011',2,'Modifica ex art.7 comma 7 lettera c)',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT011',3,'Modifica ex art.7 comma 7 lettera d)',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT011',4,'Modifica ex art.7 comma 7 lettera e)',null,4.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT011',5,'Modifica ex art.7 comma 8',null,5.00,null);

		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT012',1,'Progetto di fattibilità tecnico-economica: documento di fattibilità delle alternative progettuali',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT012',2,'Progetto di fattibilità tecnico-economica: documento finale',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT012',3,'Progetto definitivo',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT012',4,'Progetto esecutivo',null,4.00,null);

		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT013',1,'No',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT013',2,'Parziale',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT013',3,'Totale',null,3.00,null);
		
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT014',1,'No',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT014',2,'Si, cessione',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT014',3,'Si, in diritto di godimento',null,3.00,null);

		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT015',1,'No',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT015',2,'Si, come valorizzazione',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT015',3,'Si, come allienazione',null,3.00,null);
		
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',1,'Cessione della titolarità dell''opera ad altro ente pubblico',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',2,'Cessione della titolarità dell''opera a soggetto esercente una funzione pubblica',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',3,'Vendita al mercato privato',null,3.00,null);

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MESE.INTTRI.PIANI','T2MESEINT','Mese previsto per l''avvio della procedura','Mese avvio procedura','A15','W3995',null,'Mese previsto per l''avvio della procedura');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'DIRGEN.INTTRI.PIANI','T2DIRGENINT','Direzione generale','Direzione generale','A160',null,null,'Direzione generale');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'STRUOP.INTTRI.PIANI','T2STRUOPINT','Struttura operativa','Struttura operativa','A160',null,null,'Struttura operativa');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'REFERE.INTTRI.PIANI','T2REFEREINT','Referente per i dati comunicati','Referente dati comun','A160',null,null,'Referente per i dati comunicati');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'RESPUF.INTTRI.PIANI','T2RESPUFINT','Dirigente responsabile d''ufficio','Dirigente d''ufficio','A160',null,null,'Dirigente responsabile d''ufficio');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'QUANTIT.INTTRI.PIANI','T2QUANTITINT','Quantità','Quantità','F12.3',null,null,'Quantità');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'UNIMIS.INTTRI.PIANI','T2UNIMISINT','Unità di misura','Unità di misura','A20','PT019',null,'Unità di misura');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'PROAFF.INTTRI.PIANI','T2PROAFFINT','Procedura affidamento','Proced. affidamento','A50','PT020',null ,'Procedura affidamento');

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'DELEGA.INTTRI.PIANI','T2DELEGAINT','Si intende delegare la procedura di affidamento?','Delega proc. affida?','A2',null,'SN','Si intende delegare la procedura di affidamento?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'CODAUSA.INTTRI.PIANI','T2CAUSAINT','Codice AUSA del Soggetto aggregatore','Cod.AUSA sog.aggreg.','A20',null,null,'Codice AUSA del Soggetto aggregatore');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'SOGAGG.INTTRI.PIANI','T2SOGGAGGINT','Denominazione del Soggetto aggregatore','Den.sog. aggregatore','A160',null,null,'Denominazione del Soggetto aggregatore');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'ACQVERDI.INTTRI.PIANI','T2ACQVERINT','Intervento riguardante acquisti verdi?','Acquisti verdi?','A25','PT018',null,'Intervento riguardante acquisti verdi?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVNORMA.INTTRI.PIANI','T2AVNORMAINT','Riferimento normativo','Riferim. normativo','A160',null,null,'Riferimento normativo');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVOGGETT.INTTRI.PIANI','T2AVOGGETINT','Oggetto','Oggetto','A500',null,null,'Oggetto');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVCPV.INTTRI.PIANI','T2AVCPVINT','Codice CPV','Codice CPV','A12',null,null,'Codice CPV');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVIMPNET.INTTRI.PIANI','T2AVIMPNETINT','Importo al netto dell''IVA','Importo al netto IVA','F24.5',null,'MONEY','Importo al netto dell''IVA');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVIVA.INTTRI.PIANI','T2AVIVAINT','Importo IVA','Importo IVA','F24.5',null,'MONEY','Importo IVA');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'AVIMPTOT.INTTRI.PIANI','T2AVIMPTOTINT','Importo Totale','Importo Totale','F24.5',null,'MONEY','Importo Totale');

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MATRIC.INTTRI.PIANI','T2MATRICINT','Acquisto di beni realizzati con materiali riciclati?','Acquisto beni ricicl','A25','PT018',null,'Acquisto di beni realizzati con materiali riciclati?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MROGGETT.INTTRI.PIANI','T2MROGGETINT','Oggetto','Oggetto','A500',null,null,'Oggetto');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MRCPV.INTTRI.PIANI','T2MRCPVINT','Codice CPV','Codice CPV','A12',null,null,'Codice CPV');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MRIMPNET.INTTRI.PIANI','T2MRIMPNETINT','Importo al netto dell''IVA','Importo al netto IVA','F24.5',null,'MONEY','Importo al netto dell''IVA');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MRIVA.INTTRI.PIANI','T2MRIVAINT','Importo IVA','Importo IVA','F24.5',null,'MONEY','Importo IVA');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'MRIMPTOT.INTTRI.PIANI','T2MRIMPTOTINT','Importo Totale','Importo Totale','F24.5',null,'MONEY','Importo Totale');

		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IV1TRI.INTTRI.PIANI','T2IV1TRIINT','Importo IVA (1''anno)','Importo IVA 1''anno','F24.5',null,'MONEY','Importo IVA (1''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IV2TRI.INTTRI.PIANI','T2IV2TRIINT','Importo IVA (2''anno)','Importo IVA 2''anno','F24.5',null,'MONEY','Importo IVA (2''anno)');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IV9TRI.INTTRI.PIANI','T2IV9TRIINT','Importo IVA (anni successivi)','Importo IVA anni suc','F24.5',null,'MONEY','Importo IVA (anni successivi)');
			
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'COPFIN.INTTRI.PIANI','T2COPFININT','L''acquisto ha copertura finanziaria?','Copertura finanz.?','A2',null,'SN','L''acquisto ha copertura finanziaria?');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'NCAPBIL.INTTRI.PIANI','T2NCAPBILINT','Numero capitoli di bilancio','N.capitoli bilancio','A100',null,null,'Numero capitoli di bilancio');
		INSERT INTO C0CAMPI (coc_conta,c0c_tip,c0c_chi,coc_mne_uni,c0c_mne_ber,coc_des,coc_des_frm,c0c_fs,c0c_tab1,coc_dom,coc_des_web) VALUES (0,'E',null,'IMPSPE.INTTRI.PIANI','T2IMPSPEINT','Impegni di spesa sui capitoli','Impegni spesa capit.','A100',null,null,'Impegni di spesa sui capitoli');

		UPDATE C0CAMPI SET COC_DES = 'Normativa di riferimento', COC_DES_WEB='Normativa di riferimento' WHERE COC_MNE_UNI ='NORRIF.INTTRI.PIANI';
		UPDATE C0CAMPI SET COC_DES='Numero intervento CUI',COC_DES_FRM='N. intervento CUI', COC_DES_WEB='Numero intervento CUI' WHERE COC_MNE_UNI ='CUIINT.INTTRI.PIANI';
		
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT018',1,'No',null,null,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT018',2,'Interamente',null,null,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT018',3,'In parte',null,null,null);

		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 1,'ora',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 2,'giorno',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 3,'grammo',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 4,'chilogrammo',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 5,'quintale',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 6,'tonnellata',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 7,'millilitro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 8,'litro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019', 9,'ettolitro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',10,'millimetro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',11,'centimetro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',12,'metro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',13,'chilometro',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',14,'metro quadrato',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',15,'metro cubo',NULL,0,NULL);

		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',1,'Procedura aperta',null,1.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',2,'Affidamento diretto Art.36 c.2a',null,2.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',3,'Procedura disciplinata da regolamento interno per settori speciali Art.36 c.8',null,3.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',4,'Procedura negoziata previa consultazione almeno 5 operatori Art.36',null,4.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',5,'Procedura competitiva con negoziazione Art.62',null,5.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',6,'Procedura negoziata senza previa pubblicazione',null,6.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',7,'Procedura negoziata con previa indizione di gara Art. 124 (settori speciali)',null,7.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',8,'Procedura negoziata senza previa indizione di gara Art.125 (settori speciali) ',null,8.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',9,'Affidamento diretto a società raggruppate/consorziate o controllate nelle concessioni di LL.PP.',null,9.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',10,'Affidamento diretto in adesione ad accordo quadro/convenzione',null,10.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',11,'Confronto competitivo in adesione ad accordo quadro/convenzione',null,11.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',12,'Procedura ristretta',null,12.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',13,'Procedura ai sensi dei regolamenti degli organi costituzionali',null,13.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',14,'Procedura derivante da legge regionale',null,14.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',15,'Accordo quadro',null,15.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',16,'Convenzione',null,16.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',17,'Sistema dinamico di acquisizione',null,17.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',18,'Dialogo competitivo',null,18.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',19,'Procedura art.16 c.2-bis DPR 380/2001 per opere urbanizz. a scomputo primarie sotto soglia com.',null,19.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',20,'Parternariato per l''innovazione Art 65',null,20.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',21,'Concorso di progettazione o di idee in due fasi ex art.154 c.4 e 5 e art.156 c.7',null,21.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',22,'Procedura di affidamento a contraente vincolato da disposizioni sovraordinate',null,22.00,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT020',23,'Procedura negoziata art.36 b,c,d',null,2.00,null);

		INSERT INTO W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) VALUES ('W9','it.eldasoft.pt.norma.lavori','1','Programmi triennali/biennali','Normativa da utilizzare per i nuovi programmi di lavori (1: DLgs 163/2006, 2: DLgs 50/2016)',null);
		INSERT INTO W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) VALUES ('W9','it.eldasoft.pt.norma.fornitureServizi','2','Programmi triennali/biennali','Normativa da utilizzare per i nuovi programmi di forniture e servizi (1: DLgs 163/2006, 2: DLgs 50/2016)',null);
		
		SET v_sql= 'ALTER TABLE INTTRI ADD TIPCAPPRIV VARCHAR(500)';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE INTTRI ADD VERIFBIL NUMERIC(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',16,'a corpo',NULL,0,NULL);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT019',17,'unità',NULL,0,NULL);
		
		UPDATE ELDAVER set DATVET=CURRENT_TIMESTAMP WHERE CODAPP='W9';
		UPDATE ELDAVER set NUMVER='3.2.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP='PT';

		commit;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@


--#SET TERMINATOR ;

UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure ASSEGNA_GRANT;
UPDATE COMMAND OPTIONS USING s ON;

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
	SET S_USER=S_USER || ',SSELDAS';	--------		Utente Eldasoft			----------------
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
UPDATE COMMAND OPTIONS USING s ON@

--#SET TERMINATOR ;

