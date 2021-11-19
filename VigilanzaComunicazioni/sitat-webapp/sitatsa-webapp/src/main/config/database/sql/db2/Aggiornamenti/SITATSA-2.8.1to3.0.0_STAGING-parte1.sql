------ SCHEMA SITAT SA ----------
SET SCHEMA = SITATSA;
SET CURRENT PATH = SITATSA;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @


------------------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA parte 1
-- Database: DB2
-- Versione: 2.8.1 --> 3.0.0
------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF (select count(*) from eldaver where codapp='W9' and (numver='2.8.1' or numver like '2.8.1.%'))=1
	THEN
	
	------------------------------------------------------------------------------------------------
	------------------------		MODIFICA STRUTTURA TABELLE			------------------------
	------------------------------------------------------------------------------------------------
		
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN CAM VARCHAR(2)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN SISMA VARCHAR(2)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN IDUFFICIO NUMERIC(10)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE W9LOTT ADD COLUMN ID_SCELTA_CONTRAENTE_50 NUMERIC(7)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE W9DOCGARA ADD COLUMN NUM_PUBB NUMERIC(3) NOT NULL DEFAULT 1';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9DOCGARA DROP PRIMARY KEY';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9DOCGARA ADD PRIMARY KEY (CODGARA,NUM_PUBB,NUMDOC)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9DOCGARA ALTER COLUMN NUM_PUBB DROP DEFAULT';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9DOCGARA');
		

		SET v_sql = 
		'CREATE TABLE W9CF_PUBB (
		ID NUMERIC(3) NOT NULL,
		NOME VARCHAR(250),
		NUMORD NUMERIC(3),
		CL_WHERE_VIS VARCHAR(2000),
		CL_WHERE_AB VARCHAR(2000),
		CAMPI_VIS VARCHAR(2000),
		CAMPI_OBB VARCHAR(2000),
		PRIMARY KEY (ID)
		)';
		EXECUTE immediate v_sql;

		SET v_sql = 
		'CREATE TABLE W9PUBBLICAZIONI (
		CODGARA NUMERIC(10) NOT NULL,
		NUM_PUBB NUMERIC(3) NOT NULL,
		TIPDOC NUMERIC(7),
		DESCRIZ VARCHAR(100),
		DATAPUBB TIMESTAMP,
		DATASCAD TIMESTAMP,
		DATA_DECRETO TIMESTAMP,
		DATA_PROVVEDIMENTO TIMESTAMP,
		NUM_PROVVEDIMENTO  VARCHAR(50),
		DATA_STIPULA TIMESTAMP,
		NUM_REPERTORIO VARCHAR(50),
		PRIMARY KEY (CODGARA,NUM_PUBB)
		)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 
		'CREATE TABLE W9PUBLOTTO (
		CODGARA NUMERIC(10) NOT NULL,
		NUM_PUBB NUMERIC(3) NOT NULL,
		CODLOTT NUMERIC(10) NOT NULL,
		PRIMARY KEY (CODGARA,NUM_PUBB,CODLOTT)
		)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 
		'CREATE TABLE UFFICI (
		ID NUMERIC(10) NOT NULL,
		CODEIN VARCHAR(16), 
		DENOM VARCHAR(250), 
		PRIMARY KEY (ID)
		)';
		EXECUTE immediate v_sql;
	
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------
		
		DELETE FROM C0CAMPI where COC_MNE_UNI = 'ID_SCELTA_CONTRAENTE_50.W9LOTT.W9';
		DELETE FROM C0CAMPI where COC_MNE_UNI LIKE '%.W9PUBBLICAZIONI.W9';
		DELETE FROM C0CAMPI where COC_MNE_UNI LIKE '%.W9PUBLOTTO.W9';
		DELETE FROM C0CAMPI where COC_MNE_UNI = 'NUM_PUBB.W9DOCGARA.W9';
		DELETE FROM C0CAMPI where COC_MNE_UNI LIKE '%.W9CF_PUBB.W9';
		DELETE FROM C0ENTIT where C0E_NOM IN ('W9PUBBLICAZIONI.W9','W9PUBLOTTO.W9','W9PUBLOTTO.W9#1','W9CF_PUBB.W9');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'CAM.W9GARA.W9','W9GACAM','Ambito di applicazione Criteri Ambientali Minimi?','Criteri Amb. Minimi?','A2',null,'SN','Contratto in ambito di applicazione dei Criteri Ambientali Minimi?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'SISMA.W9GARA.W9','W9GASISMA','Nesso di causalità con gli eventi sismici maggio 2012?','Eventi sismici?','A2',null,'SN','Nesso di causalità con gli eventi sismici maggio 2012?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',NULL,'IDUFFICIO.W9GARA.W9','W9GAUFF','ID ufficio','ID ufficio','N10',NULL,NULL,'ID ufficio');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_SCELTA_CONTRAENTE_50.W9LOTT.W9','W9ID_SCEL50','Procedura di scelta contraente ai sensi del D.lgs. 50/2016','Scelta contr.(50)','A100','W9020',null,'Procedura di scelta del contraente ai sensi del D.lgs. 50/2016');

		DELETE FROM C0CAMPI WHERE COC_MNE_UNI LIKE '%.W9PUBBLICAZIONI.W9';
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','CODGARA.W9PUBBLICAZIONI.W9','W9PBCODGARA','Codice della gara','Cod. gara','N10',null,null,'Codice della gara');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUM_PUBB.W9PUBBLICAZIONI.W9','W9PBNUMPUBB','Numero progressivo pubblicazione','Numero pubblicazione','N3',null,null,'Numero progressivo pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPDOC.W9PUBBLICAZIONI.W9','W9PBTIPDOC','Tipologia documento','Tipo documento','N7',null,null,'Tipologia documento');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DESCRIZ.W9PUBBLICAZIONI.W9','W9PBDESCRIZ','Eventuale specificazione','Ev. specificazione','A100',null,null,'Eventuale specificazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATAPUBB.W9PUBBLICAZIONI.W9','W9PBDATAPUBB','Data pubblicazione','Data pubblicazione','A10',null,'DATA_ELDA','Data pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATASCAD.W9PUBBLICAZIONI.W9','W9PBDATASCAD','Data scadenza','Data scadenza','A10',null,'DATA_ELDA','Data scadenza');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_DECRETO.W9PUBBLICAZIONI.W9','W9PBDATADEC','Data decreto','Data decreto','A10',null,'DATA_ELDA','Data decreto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_PROVVEDIMENTO.W9PUBBLICAZIONI.W9','W9PBDATAPR','Data provvedimento','Data provvedimento','A10',null,'DATA_ELDA','Data provvedimento');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUM_PROVVEDIMENTO.W9PUBBLICAZIONI.W9','W9PBNUMPR','Numero provvedimento','Numero provvedimento','A50',null,null,'Numero provvedimento');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_STIPULA.W9PUBBLICAZIONI.W9','W9PBDATAST','Data stipula','Data stipula','A10',null,'DATA_ELDA','Data stipula');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUM_REPERTORIO.W9PUBBLICAZIONI.W9','W9PBNUMPER','Numero repertorio','Numero repertorio','A50',null,null,'Numero repertorio');
		
		UPDATE C0CAMPI SET C0C_TAB1 = 'N7' WHERE COC_MNE_UNI = 'CODSISTEMA.AVVISO.W9';

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','CODGARA.W9PUBLOTTO.W9','W9PLCODGARA','Codice della gara','Cod. gara','N10',null,null,'Codice della gara');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUM_PUBB.W9PUBLOTTO.W9','W9PLNUMPUBB','Numero progressivo pubblicazione','Numero pubblicazione','N3',null,null,'Numero progressivo pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','CODLOTT.W9PUBLOTTO.W9','W9PLCODLOTT','Numero del lotto','Num. lotto','N10',null,null,'Numero del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUM_PUBB.W9DOCGARA.W9','W9DGNUMPUBB','Numero progressivo pubblicazione','Numero pubblicazione','N3',null,null,'Numero progressivo pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','ID.W9CF_PUBB.W9','W9CFPID','Codice della configurazione','Cod. configurazione','N3',null,null,'Codice della configurazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOME.W9CF_PUBB.W9','W9CFPNOME','Nome della pubblicazione','Nome pubblicazione','A250',null,null,'Nome della pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUMORD.W9CF_PUBB.W9','W9CFPNUMORD','Numero ordine','Numero ordine','N3',null,null,'Numero ordine');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CL_WHERE_VIS.W9CF_PUBB.W9','W9CFPWHEREV','Clausola where per visualizzazione','Clausola visualizz.','A2000',null,'NOTE','Clausola where per visualizzazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CL_WHERE_AB.W9CF_PUBB.W9','W9CFPWHEREA','Clausola where per abilitazione','Clausola abilitaz.','A2000',null,'NOTE','Clausola where per abilitazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CAMPI_VIS.W9CF_PUBB.W9','W9CFCAMPIVIS','Campi visualizzati','Campi visualizzati','A2000',null,'NOTE','Campi visualizzati');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CAMPI_OBB.W9CF_PUBB.W9','W9CFCAMPIOBB','Campi obbligatori','Campi obbligatori','A2000',null,'NOTE','Campi obbligatori');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','ID.UFFICI.W9','W9UFFID','ID ufficio','ID ufficio','N10',null,null,'ID ufficio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODEIN.UFFICI.W9','W9UFFCODEIN','Codice stazione appaltante','Cod. Staz.appaltante','A16',null,null,'Codice stazione appaltante');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DENOM.UFFICI.W9','W9UFFDENOM','Denominazione ufficio','Denom. ufficio','A250',null,null,'Denominazione ufficio');

		Update C0CAMPI set C0C_TAB1='W3z05' where COC_MNE_UNI='TIPOIN.INTTRI.PIANI';
		Update C0ENTIT set C0E_KEY='CODGARA.W9DOCGARA.W9,NUM_PUBB.W9DOCGARA',C0E_NOM_EX='W9PUBBLICAZIONI.W9',COE_KEY_EX='CODGARA.W9PUBBLICAZIONI.W9,NUM_PUBB.W9PUBBLICAZIONI.W9' where C0E_NOM='W9DOCGARA.W9';
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9PUBBLICAZIONI.W9',0,'P','PUBBL','Pubblicazione atti e documenti','CODGARA.W9PUBBLICAZIONI.W9','W9GARA.W9','CODGARA.W9GARA.W9',null,null,'w9_pubb0');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9PUBLOTTO.W9',0,'P','PUBLO','Pubblicazione atti e documenti per lotto','CODGARA.W9PUBLOTTO.W9,NUM_PUBB.W9PUBLOTTO','W9PUBBLICAZIONI.W9','CODGARA.W9PUBBLICAZIONI.W9,NUM_PUBB.W9PUBBLICAZIONI.W9',null,null,'w9_publ0');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9PUBLOTTO.W9#1',0,'P','PUBLO1','Pubblicazione atti e documenti per lotto','CODGARA.W9PUBLOTTO.W9,CODLOTT.W9PUBLOTTO','W9LOTT.W9','CODGARA.W9LOTT.W9,CODLOTT.W9LOTT.W9',null,null,'w9_publ1');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9CF_PUBB.W9',0,'E','CFTIPPUB','Configurazione tipologie atti e documenti','ID.W9CF_PUBB.W9',null,null,null,null,'w9_cfpubb');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('UFFICI.W9',99050800,'E','UFFICI','Uffici','ID.UFFICI.W9',null,null,null,null,'w9_uffici');

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',1,'Procedura aperta',1);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',2,'Affidamento diretto Art.36 c.2a',3);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',3,'Procedura disciplinata da regolamento interno per settori speciali Art.36 c.8',4);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',4,'Procedura negoziata previa consultazione almeno 5 operatori Art.36',5);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',5,'Procedura competitiva con negoziazione Art.62',6);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',6,'Procedura negoziata senza previa pubblicazione',7);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',7,'Procedura negoziata con previa indizione di gara Art. 124 (settori speciali)',8);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',8,'Procedura negoziata senza previa indizione di gara Art.125 (settori speciali) ',9);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',9,'Affidamento diretto a società raggruppate/consorziate o controllate nelle concessioni di LL.PP.',10);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',10,'Affidamento diretto in adesione ad accordo quadro/convenzione',11);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',11,'Confronto competitivo in adesione ad accordo quadro/convenzione',12);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',12,'Procedura ristretta',13);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',13,'Procedura ai sensi dei regolamenti degli organi costituzionali',14);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',14,'Procedura derivante da legge regionale',15);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',15,'Accordo quadro',16);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',16,'Convenzione',17);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',17,'Sistema dinamico di acquisizione',18);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',18,'Dialogo competitivo',19);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',19,'Procedura art.16 c.2-bis DPR 380/2001 per opere urbanizz. a scomputo primarie sotto soglia com.',20);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',20,'Parternariato per l''innovazione Art 65',21);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',21,'Concorso di progettazione o di idee in due fasi ex art.154 c.4 e 5 e art.156 c.7',22);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',22,'Procedura di affidamento a contraente vincolato da disposizioni sovraordinate',23);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9020',23,'Procedura negoziata art.36 b,c,d',2);
		Update TAB1 set TAB1DESC='Anagrafica gara e lotti' where TAB1COD='W3020' and TAB1TIP=988;

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3020',901,'Pubblicazioni',0);
		
	SET v_sql = 'Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS) values 
		(1,''Delibera a contrarre o atto equivalente'',1,''L.ID_SCELTA_CONTRAENTE_50 IS NOT NULL'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(2,''Avviso per manifestazione di interesse, indagini di mercato (art.36 comma 2b,2c.2d) e avviso contratto di sponsorizzazione (art.19)'',2,''L.ID_SCELTA_CONTRAENTE_50 IN (2,6,8,23)'',''DATAPUBB|DATASCAD|''),
		(3,''Bando di gara di appalto, concessione o concorso e documentazione di gara'',3,''(L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,15,16,20,21,23) OR PREV_BANDO=''''1'''')'',''DATAPUBB|DATASCAD|''),
		(4,''Avviso in merito alla modifica dell''''ordine di importanza dei criteri, bando di concessione (art.173)'',4,''L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,20,21,23)'',''DATAPUBB|''),
		(5,''Avviso costituzione del privilegio'',5,''L.ID_SCELTA_CONTRAENTE_50=1'',''DATAPUBB|''),
		(6,''Lettera di invito'',6,''L.ID_SCELTA_CONTRAENTE_50 IN (2,3,4,5,6,7,8,12,13,14,20,21,23)'',''DATAPUBB|DATASCAD|''),
		(7,''Provvedimento che determina le esclusioni dalla procedura di affidamento e le ammissioni all''''esito delle valutazioni dei requisiti soggettivi, economico-finanziari, e tecnico-professionali'',7,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(8,''Provvedimento di nomina commissione di aggiudicazione e Curricula dei componenti della stessa in caso di criterio di aggiudicazione oepv '',8,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(9,''Provvedimento per eventuali esclusioni a seguito verifica offerte tecniche'',9,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(10,''Provvedimento per eventuali esclusioni a seguito apertura offerte economiche'',10,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(11,''Provvedimento per formazione Commissione per la valutazione dell''''offerta anomala nel caso del criterio del prezzo più basso'',10,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(12,''Provvedimento per eventuale esclusione offerta anomala'',12,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(13,''Provvedimento di aggiudicazione non efficace con elenco verbali delle commissione di gara'',13,''L.ID_SCELTA_CONTRAENTE_50 IS NOT NULL'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(15,''Provvedimento di aggiudicazione decreto o ordinativo'',15,''L.ID_SCELTA_CONTRAENTE_50 IN (2,3,9,10,11,13,14)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(16,''Provvedimento di revoca dell''''aggiudicazione'',16,''L.ID_SCELTA_CONTRAENTE_50 IS NOT NULL'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(17,''Provvedimento di gara non aggiudicata o deserta'',17,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(18,''Provvedimento di aggiudicazione efficace'',18,''L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,17,18,19,20,21,22,23)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(19,''Decreto o determina di affidamento  di lavori, servizi e forniture di somma urgenza e di protezione civile (art.163)'',19,''L.ID_SCELTA_CONTRAENTE_50 IN (2)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|''),
		(20,''Avviso di aggiudicazione o affidamento (esito di gara)'',20,''L.ID_SCELTA_CONTRAENTE_50 IS NOT NULL'',''DATAPUBB|''),
		(21,''Testo integrale contratto di acquisto beni e servizi di importo unitario superiore al milione di euro'',21,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (2,23)'',''DATAPUBB|DATA_STIPULA|NUM_REPERTORIO''),
		(22,''Provvedimento di autorizzazione subappalto'',22,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16)'',''DATAPUBB|''),
		(23,''Atto per eventuale scioglimento contratto per eccesso durata sospensione esecuzione'',23,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16)'',''DATAPUBB|''),
		(24,''Provvedimento di eventuali modifiche al contratto d''''appalto'',24,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16)'',''DATAPUBB|''),
		(25,''Provvedimento di eventuale recesso dal contratto'',25,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO''),
		(26,''Provvedimento di eventuale risoluzione del contratto'',26,''L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16)'',''DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO''),
		(99,''Altro documento'',99,''1=1'',''DATAPUBB|'')';
		EXECUTE IMMEDIATE v_sql;

		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('PAGE','W9.W9GARA-scheda.PUBBLICAZIONI','Scheda gara - Pagina Pubblicazioni',3670);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('PAGE','W9.W9GARA-scheda.W9PUBB','Scheda gara - Pagina Pubblicità gara',3680);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('MASC','W9.W9PUBBLICAZIONI-scheda','Scheda pubblicazioni',3690);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('PAGE','W9.W9PUBBLICAZIONI-scheda.LOTTI','Scheda pubblicazioni - Pagina Lotti',3700);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('PAGE','W9.W9PUBBLICAZIONI-scheda.INVII','Scheda pubblicazioni - Pagina Invii',3710);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('SEZ','W9.W9PUBBLICAZIONI-scheda.DETTPUBBLICAZIONI.GEN','Pubblicazioni Sezione Dati Generali',3720);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('SEZ','W9.W9PUBBLICAZIONI-scheda.DETTPUBBLICAZIONI.W9DOCGARA','Pubblicazioni Sezione Documenti',3730);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','INS.W9.W9PUBBLICAZIONI-scheda.INVII.LISTANUOVO','Nuovo Invio',3740);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('PAGE','W9.W9PUBBLICAZIONI-scheda.DETTPUBBLICAZIONI','Scheda pubblicazioni - Pagina Dati Generali',3750);
		Insert into W_OGGETTI(TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','MOD.W9.W9PUBBLICAZIONI-scheda.DETTPUBBLICAZIONI.SCHEDAMOD','Modifica',3760);
		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('SUBMENU','ARCHIVI.Archivio-uffici','Archivio uffici',6030);
		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.W9.XML-RLO','Creazione XML fasi per RLO',3780);
		
		Update W9APPA set VAL_SOGLIA_ANOMALIA=null,NUM_OFFERTE_FUORI_SOGLIA=null where NUM_OFFERTE_AMMESSE<5;

		Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','registrazione.utente.mail.jndi.name',null,'Registrazione utente','Nome della risorsa JNDI per l''invio mail in fase di registrazione',null);
		
	------------------------------------------------------------------------------------------------
	------------------------		MODIFICA STRUTTURA TABELLE			------------------------
	------------------------------------------------------------------------------------------------
	
		SET v_sql = 
		'CREATE TABLE W9FLUSSI_ELIMINATI (
		IDFLUSSO NUMERIC(10) NOT NULL,
		AREA NUMERIC(7), 
		KEY01 NUMERIC(10), 
		KEY02 NUMERIC(10), 
		KEY03 NUMERIC(10), 
		KEY04 NUMERIC(10), 
		TINVIO2 NUMERIC(7), 
		DATINV TIMESTAMP, 
		DATCANC TIMESTAMP, 
		NOTEINVIO VARCHAR(2000), 
		MOTIVOCANC VARCHAR(2000), 
		AUTORE VARCHAR(100),
		XML CLOB (100 M),
		CODCOMP NUMERIC(12), 
		IDCOMUN NUMERIC(10), 
		VERXML VARCHAR(20),
		CFSA VARCHAR(16),
		CODOGG VARCHAR(20),
		DATIMP TIMESTAMP, 
		PRIMARY KEY (IDFLUSSO)
		)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 
		'CREATE view V_W9FLUSSI (TIPO_INVIO, IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV)
			AS SELECT ''1'', IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV
			FROM W9FLUSSI
			UNION
			SELECT ''2'', IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV
			FROM W9FLUSSI_ELIMINATI';
		EXECUTE immediate v_sql;
		
		SET v_sql = 
		'CREATE view V_W9INBOX
		(
		TIPO_INVIO, IDCOMUN, STACOM, NOMEIN, DATRIC, AREA, KEY03, KEY04, CFSA, AUTORE, DATIMP, VERXML, MSG, CODOGG, TINVIO2, IDEGOV
		)
		AS
		SELECT
		''1'', W9INBOX.IDCOMUN, STACOM, NOMEIN, DATRIC, AREA, KEY03, KEY04, CFSA, AUTORE, DATIMP, VERXML, MSG, CODOGG, TINVIO2, IDEGOV
		FROM W9INBOX JOIN W9FLUSSI ON W9INBOX.IDCOMUN=W9FLUSSI.IDCOMUN LEFT JOIN UFFINT ON W9FLUSSI.IDCOMUN = W9INBOX.IDCOMUN AND W9FLUSSI.CFSA = UFFINT.CFEIN
		UNION
		SELECT
		''2'', W9INBOX.IDCOMUN, STACOM, NOMEIN, DATRIC, AREA, KEY03, KEY04, CFSA, AUTORE, DATIMP, VERXML, MSG, CODOGG, TINVIO2, IDEGOV
		FROM W9INBOX JOIN W9FLUSSI_ELIMINATI ON W9INBOX.IDCOMUN=W9FLUSSI_ELIMINATI.IDCOMUN LEFT JOIN UFFINT ON W9FLUSSI_ELIMINATI.IDCOMUN = W9INBOX.IDCOMUN AND W9FLUSSI_ELIMINATI.CFSA = UFFINT.CFEIN
		';
		EXECUTE immediate v_sql;
		
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------

		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('V_W9FLUSSI.W9',80011701,'E','VFLUSSI','Flussi di comunicazione','IDFLUSSO.V_W9FLUSSI.W9',null,null,null,null,'w9_vflussi');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9FLUSSI_ELIMINATI.W9',0,'E','FLUSSIE','Flussi di comunicazione','IDFLUSSO.W9FLUSSI_ELIMINATI.W9',null,null,null,null,'w9_flussie');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('V_W9INBOX.W9',0,'E','VINBOX','Vista Inbox','IDCOMUN.V_W9INBOX.W9',null,null,null,null,'w9_vinbox');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','IDFLUSSO.V_W9FLUSSI.W9','W9VFLID','ID dell''invio','ID invio','N10',null,null,'ID dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPO_INVIO.V_W9FLUSSI.W9','W9VTINVIO','tipo di invio','Tipo di invio','A2',null,null,'Tipo di invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TINVIO2.V_W9FLUSSI.W9','W9VFLTINVIO','tipo di invio','Tipo di invio','A100','W3998',null,'Tipo di invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY01.V_W9FLUSSI.W9','W9VFLKEY01','Codice della gara, del programma o dell''avviso','Key 01','N10',null,null,'Codice della gara, del programma o dell''avviso');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY02.V_W9FLUSSI.W9','W9VFLKEY02','Codice del lotto','Key 02','N10',null,null,'Codice del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY03.V_W9FLUSSI.W9','W9VFLKEY03','Fase di esecuzione','Key 03','A100','W3020',null,'Tipologia della fase di esecuzione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY04.V_W9FLUSSI.W9','W9VFLKEY04','Numero progressivo fase','Key 04','N3',null,null,'Numero progressivo fase');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATINV.V_W9FLUSSI.W9','W9VFLDATINV','Data invio','Data invio','A10',null,'DATA_ELDA','Data invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AREA.V_W9FLUSSI.W9','W9VFLAREA','Area dell''invio','Area invio','A100','W9004',null,'Area dell''invio');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','IDFLUSSO.W9FLUSSI_ELIMINATI.W9','W9FLEID','ID dell''invio','ID invio','N10',null,null,'ID dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TINVIO2.W9FLUSSI_ELIMINATI.W9','W9FLETINVIO','tipo di invio','Tipo di invio','A100','W3998',null,'Tipo di invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY01.W9FLUSSI_ELIMINATI.W9','W9FLEKEY01','Codice della gara, del programma o dell''avviso','Key 01','N10',null,null,'Codice della gara, del programma o dell''avviso');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY02.W9FLUSSI_ELIMINATI.W9','W9FLEKEY02','Codice del lotto','Key 02','N10',null,null,'Codice del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY03.W9FLUSSI_ELIMINATI.W9','W9FLEKEY03','Fase di esecuzione','Key 03','A100','W3020',null,'Tipologia della fase di esecuzione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY04.W9FLUSSI_ELIMINATI.W9','W9FLEKEY04','Numero progressivo fase','Key 04','N3',null,null,'Numero progressivo fase');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATINV.W9FLUSSI_ELIMINATI.W9','W9FLEDATINV','Data invio','Data invio','A10',null,'DATA_ELDA','Data invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATCANC.W9FLUSSI_ELIMINATI.W9','W9FLEDATCANC','Data cancellazione','Data cancellazione','A10',null,'DATA_ELDA','Data cancellazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOTEINVIO.W9FLUSSI_ELIMINATI.W9','W9NOTINVIOE','Note invio','Note invio','A2000',null,'NOTE','Note invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MOTIVOCANC.W9FLUSSI_ELIMINATI.W9','W9MOTCANCE','Causa cancellazione','Causa','A2000',null,'NOTE','Causa cancellazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AUTORE.W9FLUSSI_ELIMINATI.W9','W9FLEAUTORE','Nome dell''autore dell''invio','Autore','A100',null,null,'Nome dell''autore dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AREA.W9FLUSSI_ELIMINATI.W9','W9FLEAREA','Area dell''invio','Area invio','A100','W9004',null,'Area dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VERXML.W9FLUSSI_ELIMINATI.W9','W9FLEVERXML','Versione file XML','Versione XML','A20',null,null,'Versione file XML');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'P',null,'XML.W9FLUSSI_ELIMINATI.W9','W9FLEXML','File XML','File XML','A2000',null,'CLOB','File XML');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','IDCOMUN.V_W9INBOX.W9','W9VIBID','Numero della comunicazione','Numero comunicazione','N10',null,null,'Numero della comunicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPO_INVIO.V_W9INBOX.W9','W9VIBTINVIO','tipo di invio','Tipo di invio','A2',null,null,'Tipo di invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'STACOM.V_W9INBOX.W9','W9VIBSTACOM','Stato della comunicazione','Stato','A100','W9003',null,'Stato comunicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOMEIN.V_W9INBOX.W9','W9VNOMEIN','Denominazione','Denominazione','A254',null,'NOTE','Denominazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATRIC.V_W9INBOX.W9','W9VIBDTRIC','Data ricezione comunicazione','Data ricezione','A10',null,'TIMESTAMP','Data ricezione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY03.V_W9INBOX.W9','W9VIFLKEY03','Fase di esecuzione','Key 03','A100','W3020',null,'Tipologia della fase di esecuzione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY04.V_W9INBOX.W9','W9VIFLKEY04','Numero progressivo fase','Key 04','N3',null,null,'Numero progressivo fase');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODOGG.V_W9INBOX.W9','W9VFLCODOGG','Codice dell''oggetto dell''invio','Codice oggetto invio','A20',null,null,'Codice oggetto invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TINVIO2.V_W9INBOX.W9','W9VFLTINV2','Tipo di invio','Tipo di invio','A100','W3998',null,'Tipo di invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AREA.V_W9INBOX.W9','W9VFLARE','Area dell''invio','Area invio','A100','W9004',null,'Area dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CFSA.V_W9INBOX.W9','W9VFLCFSA','Codice fiscale della stazione appaltante','C.F. stazione appalt','A16',null,null,'Cod.fiscale stazione appaltante');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AUTORE.V_W9INBOX.W9','W9VFLAUTORE','Nome dell''autore dell''invio','Autore','A100',null,null,'Nome dell''autore dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATIMP.V_W9INBOX.W9','W9VIBDTIMP','Data importazione comunicazione','Data importazione','A10',null,'DATA_ELDA','Data importazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VERXML.V_W9INBOX.W9','W9VFLVERXML','Versione file XML','Versione XML','A20',null,null,'Versione file XML');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MSG.V_W9INBOX.W9','W9VIBMSG','Messaggio di errore','Messaggio errore','A254',null,'NOTE','Messaggio di errore');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IDEGOV.V_W9INBOX.W9','W9VIBIDEGOV','ID e-gov del messaggio','ID e-gov','A200',null,null,'ID e-gov del messaggio');

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3998',-1,'Cancellazione flusso',5);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9007',95,'Richieste di cancellazione',10);
		
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','W9.W9GARA.CAM','Visualizza',0,1,1,3700645506);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','W9.W9GARA.SISMA','Visualizza',0,1,1,1309392300);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('PAGE','VIS','W9.W9GARA-scheda.BANDOGARA','Visualizza',0,1,1,3079290743);
		
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('SUBMENU','VIS','ARCHIVI.Archivio-tecnici-imprese','Visualizza',0,1,1,209006732);
		
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('SUBMENU','VIS','ARCHIVI.Archivio-uffici','Visualizza',0,1,1,1268768942);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','W9.UFFICI.DENOM','Visualizza',0,1,1,3284357329);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.W9.XML-RLO','Visualizza',0,1,1,3895397778);
		
		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','ALT.W9.W9GARA-lista.FunzioniAvanzate','Funzioni avanzate',3772);
		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','ALT.W9.W9GARA-lista.MigraGare','Funzioni avanzate - Migrazione gare',3775);
		
		update W_OGGETTI set OGGETTO='DEL.PIANI.PIATRI-lista.DEL' where OGGETTO='DEL.W9.PIATRI-lista.DEL';
		UPDATE W_CONFIG SET VALORE='3.0.0' WHERE CODAPP='W9' AND CHIAVE IN ('it.eldasoft.sitatproxy.versioneTracciatoXML','it.eldasoft.rt.sitatproxy.versioneTracciatoXML'); 
		Update ELDAVER set NUMVER='3.0.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@


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

--- Properties specifiche di SitatSA
--#SET TERMINATOR ;

UPDATE W_CONFIG SET VALORE='3.0.0' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.sitatproxy.versioneTracciatoXML';
UPDATE W_CONFIG SET VALORE='velocitymodelamministratore.html' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.utente.mail.amministratore.model';
UPDATE W_CONFIG SET VALORE='velocitymodelutente.html' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.utente.mail.utente.model';
UPDATE W_CONFIG SET VALORE='Riepilogo richiesta registrazione utente' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.utente.mail.utente.oggetto';
UPDATE W_CONFIG SET VALORE='mail/SitatSA' WHERE CODAPP='W9' and CHIAVE='registrazione.utente.mail.jndi.name';
	
DELETE FROM W_QUARTZ WHERE BEAN_ID = 'AVCPManagerTrigger';
DELETE FROM W_QUARTZ WHERE BEAN_ID = 'cartMessageReaderTrigger';
DELETE FROM W_QUARTZ WHERE BEAN_ID = 'NotificaCantiereEdileManagerTrigger';
	
--- SitatSA di Sviluppo su DB2

UPDATE W_CONFIG set VALORE='DB2' where CODAPP='W9' and CHIAVE='it.eldasoft.dbms';
UPDATE W_CONFIG set VALORE='Sitat Stazione appaltante' where CODAPP='W9' and CHIAVE='it.eldasoft.titolo';
UPDATE W_CONFIG set VALORE='1' where CODAPP='W9' and CHIAVE='it.eldasoft.registrazione.attivaForm';
UPDATE W_CONFIG set VALORE='registrazione-utente-w9.jsp?modo=NUOVO' where CODAPP='W9' and CHIAVE='it.eldasoft.registrazione.pagina';
UPDATE W_CONFIG set VALORE='SA-APPA' where CODAPP='W9' and CHIAVE='it.eldasoft.account.insert.profiloDefault';

UPDATE W_CONFIG SET VALORE='SA-APPA,SA-AVVISI,SA-PIATRI' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.profiliDisponibili';
UPDATE W_CONFIG SET VALORE='/apps/tix/webapps/SitatSA/velocitymodel' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.utente.velocity.model.path';
UPDATE W_CONFIG SET VALORE='Richiesta accesso SITAT' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.registrazione.utente.mail.amministratore.oggetto';

UPDATE W_CONFIG SET VALORE='http://carttestnal.rete.toscana.it:8080/proxy-sitat/services/Sitat' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.sitatproxy.url';

UPDATE W_CONFIG SET VALORE='0' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.richiestacup.type';
UPDATE W_CONFIG SET VALORE='http://cupwebwscoll.tesoro.it' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.richiestacup.ws.url';
UPDATE W_CONFIG SET VALORE='http://159.213.32.211:8080/cart/PD/SPCRegioneToscana/SPCMinisteroEconomiaFinanzeCollaudo/SPCElaborazioniCUP/' WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.richiestacup.pdd.url';
UPDATE W_CONFIG SET VALORE=null WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.richiestacup.pdd.username';
UPDATE W_CONFIG SET VALORE=null WHERE CODAPP='W9' AND CHIAVE='it.eldasoft.richiestacup.pdd.password';

--- Attivazione applicativo
INSERT INTO W_ATT (CODAPP,CHIAVE,VALORE) VALUES ('W9','it.eldasoft.codiceCliente','1091');
INSERT INTO W_ATT (CODAPP,CHIAVE,VALORE) VALUES ('W9','it.eldasoft.acquirenteSW','Regione Toscana');
INSERT INTO W_ATT (CODAPP,CHIAVE,VALORE) VALUES ('W9','it.eldasoft.responsabileCliente','Luca Menegatti');
INSERT INTO W_ATT (CODAPP,CHIAVE,VALORE) VALUES ('W9','it.eldasoft.opzioni','OP101');

