------------------------------
-- AGGIORNAMENTO SITAT	
-- NOTA: si salta la versione 2.2.0 per allinearsi alla versione del proxy
------------------------------
-- GENE 1.4.45
------------------------------
-----------------------------------------------------
---   Aggiornamento da 1.4.43 a 1.4.45 di Gene    ---
-----------------------------------------------------


delete from SITATORT.tab4 where tab4tip in ('G_z07','G_z08');
delete from SITATORT.tab2 where tab2cod='G_z07';
delete from SITATORT.tab2 where tab2cod='G_z08';

update SITATORT.usrsys set sysab3='U' where sysab3='N';
update SITATORT.usrsys set sysabg='U' where sysabg='N';


Update SITATORT.TAB1 set tab1desc='0.21' where TAB1COD='Ag006' and TAB1TIP=1;

Insert into SITATORT.TAB6 (TAB6COD,TAB6TIP,TAB6DESC) values ('G__1','G_z07','Classificazione delle categorie per forniture');
Insert into SITATORT.TAB6 (TAB6COD,TAB6TIP,TAB6DESC) values ('G__1','G_z08','Classificazione delle categorie per servizi');
Insert into SITATORT.TAB6 (TAB6COD,TAB6TIP,TAB6DESC) values ('G__1','G_035','Classificazione delle categorie per forniture (descrizione breve)');
Insert into SITATORT.TAB6 (TAB6COD,TAB6TIP,TAB6DESC) values ('G__1','G_036','Classificazione delle categorie per servizi (descrizione breve)');



Update SITATORT.TAB1 set tab1desc='Raggruppamento temporaneo di concorrenti (art.34 c.1/d,e DLgs 163/2006)'  where TAB1COD='Ag008' and TAB1TIP=3;
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('Ag008',5,'Operatore economico stabilito in altri Stati membri (art.34 c.1/f-bis DLgs 163/2006)');

Update SITATORT.C0CAMPI Set C0C_TAB1='G_038' where c0c_mne_ber like 'G_TIPLAVG%';


-- Aggiornamenti della versione di G_
UPDATE SITATORT.ELDAVER SET NUMVER = '1.4.45' WHERE CODAPP= 'G_';


------------------------------
-- GENEWEB 1.6.2
------------------------------
-- -----------------------------------------------------
-- ---   Aggiornamento di GeneWeb da 1.6.0 a 1.6.2   ---
-- -----------------------------------------------------

ALTER TABLE SITATORT.W_INVCOM ADD COLUMN COMNUMPROT VARCHAR(10);
ALTER TABLE SITATORT.W_INVCOM ADD COLUMN COMDATPROT TIMESTAMP;
REORG TABLE SITATORT.W_INVCOM;

ALTER TABLE SITATORT.W_LOGEVENTI ALTER COLUMN IPEVENTO SET DATA TYPE VARCHAR(40);
REORG TABLE SITATORT.W_LOGEVENTI;


CREATE TABLE SITATORT.W_CONFCOM
 (NUMPRO NUMERIC(7) NOT NULL,
GENERE NUMERIC(7),
MODTIT VARCHAR(200),
MODDESC VARCHAR(2000),
NUMORD NUMERIC(8,2),
COMINTEST VARCHAR(1),
COMMSGOGG VARCHAR(300),
COMMSGTES VARCHAR(2000),
FILTROSOG VARCHAR(2000)
) IN TBS_SITATORT_N0 INDEX IN TBS_SITATORT_N0;
 ALTER TABLE SITATORT.W_CONFCOM ADD CONSTRAINT W_CONFCOM_PK PRIMARY KEY (NUMPRO);

GRANT SELECT,Insert,UPDATE,DELETE ON TABLE SITATORT.W_CONFCOM TO USER SITATORT;

Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (1,1,'Comunicazione della decisione di non aggiudicare l''appalto','Viene inviata a tutte le ditte in gara',50,'1','Comunicazione di non aggiudicazione','con la presente si informano i concorrenti che codesta amministrazione non intende procedere con l''aggiudicazione dell''appalto che Þ da ritenersi annullato.',NULL);
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (12,1,'Comunicazione della seduta pubblica di apertura offerte','Viene inviata alle ditte che hanno presentato offerta',1,'1','Comunicazione apertura offerte','si comunica che il giorno _________ alle ore _____ presso __________  si procederÓ all''apertura delle buste contenenti le offerte.','(ditg.ammgar is null or ditg.ammgar = ''1'' or (ditg.ammgar = ''2'' and ditg.fasgar > 1))');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (2,1,'Comunicazione dell''esclusione alle ditte candidate','Viene inviata alle ditte escluse nella fase di ''Ricezione plichi'' o in una fase precedente',2,'1','Esclusione dalla gara','con la presente si comunica l''esclusione dalla procedura di gara.','ditg.ammgar = ''2'' and ditg.fasgar <= 1');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (3,1,'Comunicazione dell''esclusione alle ditte offerenti','Viene inviata alle ditte escluse in una fase successiva a quella di ''Ricezione plichi''',3,'1','Esclusione dalla gara','con la presente si comunica l''esclusione dalla procedura di gara.','ditg.ammgar = ''2'' and ditg.fasgar > 1 and ditg.fasgar <= 6');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (4,1,'Comunicazione della richiesta documenti per comprova requisiti alla ditta aggiudicataria','Viene inviata alla ditta aggiudicataria provvisoria',4,'1','Richiesta documenti per comprova requisiti','facendo seguito all''aggiudicazione provvisoria della gara, con la presente si richiede di presentare la documentazione necessaria per la comprova del possesso dei requisiti di ordine generale, nonchÚ della capacitÓ economico-finanziaria e tecnico/organiz.','ditg.staggi = 4');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (5,1,'Comunicazione della richiesta documenti per comprova requisiti alla ditta seconda in graduatoria','Viene inviata alla ditta seconda aggiudicataria provvisoria',5,'1','Richiesta documenti per comprova requisiti','facendo seguito all''aggiudicazione provvisoria della gara, con la presente si richiede di presentare la documentazione necessaria per la comprova del possesso dei requisiti di ordine generale, nonchÚ della capacitÓ economico-finanziaria e tecnico/organiz.','ditg.staggi = 5');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (6,2,'Comunicazione dell''aggiudicazione definitiva alla ditta aggiudicataria','Viene inviata alla ditta aggiudicataria definitiva',6,'1','Aggiudicazione definitiva','a seguito dell''espletamento della procedura di appalto, si comunica che l''appalto Þ stato aggiudicato in via definitiva a codesta ditta.','exists (select gare.ngara from gare where gare.ngara=ditg.ngara5 and ditg.dittao = gare.ditta)');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (7,2,'Comunicazione dell''aggiudicazione definitiva e dello svincolo della cauzione provvioria alle ditte non aggiudicatarie','Viene inviata alle ditte non aggiudicatarie',7,'1','Aggiudicazione definitiva e svincolo cauzione provvisoria','a seguito dell''espletamento della procedura di appalto, si comunica l''avvenuta aggiudicazione definitiva e pertanto codesta ditta pu= ritenere svincolata la propria cauzione provvisoria.','(ditg.ammgar is null or ditg.ammgar <> ''2'') and not exists (select gare.ngara from gare where gare.ngara=ditg.ngara5 and (ditg.dittao = gare.ditta or gare.ditta is null))');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (8,2,'Comunicazione della data di avvenuta stipulazione del contratto alla ditta aggiudicataria','Viene inviata alla ditta aggiudicataria definitiva',8,'1','Data di avvenuta stipulazione del contratto','con la presente si comunica che il contratto per la procedura d''appalto Þ stato stipulato con data __________','exists (select gare.ngara from gare where gare.ngara=ditg.ngara5 and ditg.dittao = gare.ditta)');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (9,2,'Comunicazione della data di avvenuta stipulazione del contratto alle ditte non aggiudicatarie','Viene inviata alle ditte non aggiudicatarie',9,'1','Data di avvenuta stipulazione del contratto','con la presente si comunica che il contratto per la procedura d''appalto Þ stato stipulato con data __________ con la ditta _________','(ditg.ammgar is null or ditg.ammgar <> ''2'') and not exists (select gare.ngara from gare where gare.ngara=ditg.ngara5 and (ditg.dittao = gare.ditta or gare.ditta is null))');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (10,3,'Comunicazione dell''aggiudicazione definitiva','Viene inviata alle ditte che non sono state escluse dalla gara',6,'1','Aggiudicazione definitiva','a seguito dell''espletamento della procedura di appalto, si comunica l''esito della stessa e l''aggiudicazione definitiva dei vari lotti.','(ditg.ammgar is null or ditg.ammgar <> ''2'')');
Insert into SITATORT.W_CONFCOM (NUMPRO,GENERE,MODTIT,MODDESC,NUMORD,COMINTEST,COMMSGOGG,COMMSGTES,FILTROSOG) values (11,3,'Comunicazione della data di avvenuta stipulazione del contratto','Viene inviata alle ditte che non sono state escluse dalla gara',7,'1','Data di avvenuta stipulazione del contratto','con la presente si comunica la stipula del contratto in data _________  per i lotti _______ della procedura d''appalto.','(ditg.ammgar is null or ditg.ammgar <> ''2'')');

Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W_004',1,'Gara (di qualsiasi tipologia)');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W_004',2,'Gara a lotto unico o lotto di gara divisa in lotti con offerte distinte');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W_004',3,'Gara divisa in lotti con offerta unica');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W_004',10,'Elenco operatori economici');



Insert into SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (100000122,'P','','COMNUMPROT.W_INVCOM.GENEWEB','COMNUMPROT','Numero protocollo documento','Num.prot. doc.','A10','','','Numero protocollo');
Insert into SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (100000123,'P','','COMDATPROT.W_INVCOM.GENEWEB','COMDATPROT','Data protocollo documento','Data prot. doc.','A10','','DATA_ELDA','Data protocollo');
Delete from SITATORT.c0campi where c0c_mne_ber='W_IPEVENTO';
Insert into SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (100000505,'P','','IPEVENTO.W_LOGEVENTI.GENEWEB','W_IPEVENTO','IP client evento','IP client evento','A20','','','');



Insert into SITATORT.C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,COE_FRM_RE) values ('W_CONFCOM.GENEWEB',10000200,'P','CONFCOM','Configurazione modelli di comunicazioni','NUMPRO.W_CONFCOM.GENEWEB', NULL, NULL, NULL);


-- Aggiornamenti della versione di W_
UPDATE SITATORT.ELDAVER SET NUMVER = '1.6.2'  WHERE CODAPP= 'W_';


------------------------------
-- SITATORT 2.3.0
--------------------------

ALTER TABLE SITATORT.W9FLUSSI ADD COLUMN DATIMP TIMESTAMP;
REORG TABLE SITATORT.W9FLUSSI;
Insert into SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATIMP.W9FLUSSI.W9','W9IBDTIMP','Data importazione comunicazione','Data importazione','A10',null,'DATA_ELDA','Data importazione');

ALTER TABLE SITATORT.W9GARA ADD COLUMN RIC_ALLUV VARCHAR(1);
REORG TABLE SITATORT.W9GARA;
Insert into SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'RIC_ALLUV.W9GARA.W9','W9GARICALLUV','Ordinanza ricostruzione alluvione Lunigiana ed Elba?','Ordinanza alluvione?','A2',null,'SN','Interv. ai sensi ordinanza ricostr. alluvione Lunigiana ed Elba');

-- Aggiornamento del campo W9GARA.TIPO_APP per effetto del cambiamento del tabellato W3999 
update SITATORT.W9GARA g set TIPO_APP = 3 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Contratto di concessione di lavori');
update SITATORT.W9GARA g set TIPO_APP = 4 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Contratto di concessione di servizi e/o forniture');
update SITATORT.W9GARA g set TIPO_APP = 5 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Finanza di progetto');
update SITATORT.W9GARA g set TIPO_APP = 6 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Affidamento unitario a contraente generale');
update SITATORT.W9GARA g set TIPO_APP = 7 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Acquisizione in economia');
update SITATORT.W9GARA g set TIPO_APP = 8 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Locazione finanziaria di opere pubbliche o di pubblica utilità');
update SITATORT.W9GARA g set TIPO_APP = 9 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Accordo quadro/convenzione');
update SITATORT.W9GARA g set TIPO_APP =10 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Concorsi di progettazione/concorsi di idee');
update SITATORT.W9GARA g set TIPO_APP =11 where TIPO_APP in (select tab1tip from SITATORT.tab1 t where g.tipo_app=t.tab1tip and tab1cod='W3999' and tab1desc='Contratti d''appalto discendente da accordo quadro/convenzione senza successivo confronto competitivo');

Update SITATORT.TAB1 Set tab1desc='Contratto di concessione di lavori' where tab1cod= 'W3999' and tab1tip=3;
Update SITATORT.TAB1 Set tab1desc='Contratto di concessione di servizi e/o forniture' where tab1cod= 'W3999' and tab1tip=4;
Update SITATORT.TAB1 Set tab1desc='Finanza di progetto' where tab1cod= 'W3999' and tab1tip=5;
Update SITATORT.TAB1 Set tab1desc='Affidamento unitario a contraente generale' where tab1cod= 'W3999' and tab1tip=6;
Update SITATORT.TAB1 Set tab1desc='Acquisizione in economia' where tab1cod= 'W3999' and tab1tip=7;
Update SITATORT.TAB1 Set tab1desc='Locazione finanziaria di opere pubbliche o di pubblica utilità' where tab1cod= 'W3999' and tab1tip=8;
Update SITATORT.TAB1 Set tab1desc='Accordo quadro/convenzione' where tab1cod= 'W3999' and tab1tip=9;
Update SITATORT.TAB1 Set tab1desc='Concorsi di progettazione/concorsi di idee' where tab1cod= 'W3999' and tab1tip=10;
Update SITATORT.TAB1 Set tab1desc='Contratti d''appalto discendente da accordo quadro/convenzione senza successivo confronto competitivo' where tab1cod= 'W3999' and tab1tip=11;
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3999',12,'Scelta del socio privato nella società mista');


Update SITATORT.TAB1 Set tab1desc='Istanza di recesso' where tab1cod= 'W3020' and tab1tip=10;

Update SITATORT.TAB1 Set tab1desc='Procedura negoziata senza previa indizione di gara ex art. 221 D.Lgs. 163/2006' where tab1cod= 'W3005' and tab1tip=10;


Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3996',7,'Procedura ristretta semplificata art.123 c2 D.Lgs.163/06');
Update SITATORT.TAB1 Set tab1nord=tab1tip where tab1cod='W3996' and tab1tip<>6;
Update SITATORT.TAB1 Set tab1nord=99 where tab1cod='W3996' and tab1tip=6;

Update SITATORT.C0CAMPI SET coc_des='Imp. finale comp. lavori (al netto di IVA e oneri sicur.)',coc_des_web='Imp. finale comp. lavori (al netto di IVA e oneri sicurezza)' where c0c_mne_ber='W3IF_LAVOR';
Update SITATORT.C0CAMPI SET coc_des='Imp. finale comp. servizi (al netto di IVA e oneri sicur.)',coc_des_web='Imp. finale comp. servizi (al netto di IVA e oneri sicurezza)' where c0c_mne_ber='W3IF_SERVI';
Update SITATORT.C0CAMPI SET coc_des='Imp. finale comp. forniture (al netto di IVA e oneri sicur.)',coc_des_web='Imp. finale comp. forniture (al netto di IVA e oneri sicurezza)' where c0c_mne_ber='W3IF_FORNI';
Update SITATORT.C0CAMPI SET coc_des_web='Imp. progettazione (per lavori art.53 c2 lett.b.c. D.Lgs.163/06)' where c0c_mne_ber='W3IMP_PROG';


Update SITATORT.TAB1 Set tab1desc='Inadempienze disposizioni sicurezza e regolarità del lavoro nei cantieri (art.23 LR 38/07)' where tab1cod='W3020' and tab1tip=995;
Update SITATORT.C0CAMPI SET coc_des='Mancata adozione sistemi di rilevazione presenze (comma3)',coc_des_web='Mancata adozione sistemi di rilevazione presenze (comma3)' where c0c_mne_ber='W3COMMA3A';
Update SITATORT.C0CAMPI SET coc_des='Mancata dimostrazione regolarità rapporti lavoro (comma 3)',coc_des_web='Mancata dimostrazione regolarità rapporti lavoro (comma 3)' where c0c_mne_ber='W3COMMA3B';
Update SITATORT.C0CAMPI SET coc_des='Mancato svolgimento momenti formativi (comma 4)',coc_des_web='Mancato svolgimento momenti formativi (comma 4)' where c0c_mne_ber='W3COMMA45A';
Update SITATORT.C0CAMPI SET coc_des_web='Mancata estensione formaz. agli altri soggetti nel cantiere (comma 5)' where c0c_mne_ber='W3COMMA5';
Update SITATORT.C0CAMPI SET coc_des_web='Inad. moduli informativi di ingr. per lavoratori nel cantiere (comma 6)' where c0c_mne_ber='W3COMMA6';


UPDATE SITATORT.ELDAVER Set numver='2.3.0' where codapp='W9';

