--#SET TERMINATOR ;

SET SCHEMA = SITATSA;


UPDATE COMMAND OPTIONS USING s OFF;
		  		----------------------------------------------
		---   Aggiornamento dati Piani Triennali   ---
		----------------------------------------------
		
		Update INTTRI set ANNRIF=1 where ANNRIF is null and TIPINT in (2,3);
		Update INTTRI set TIPINT= (select TIPROG from PIATRI P where P.CONTRI=INTTRI.CONTRI) where TIPINT is null;
		
		Delete from TAB_CONTROLLI where codapp='PT';
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',10,'PIATRI','Programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',20,'PIATRI','Dati generali','CENINT','EXISTS (SELECT 1 FROM UFFINT A WHERE A.CODEIN= T.CENINT and A.NOMEIN is not null)','Inserire l''Amministrazione','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',30,'PIATRI','Dati generali','RESPRO','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.RESPRO and A.NOMTEC is not null)','Indicare il responsabile del programma','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',40,'PIATRI','Dati generali','ID','T.ID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',50,'PIATRI','Dati generali','BRETRI','T.BRETRI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',60,'PIATRI','Dati generali','ANNTRI','T.ANNTRI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',70,'PIATRI','Dati generali','Elenco interventi','EXISTS (SELECT 1 FROM INTTRI I WHERE I.CONTRI=T.CONTRI)','Il programma non contiene interventi','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',80,'PIATRI','Archivio tecnici','TECNI.CITTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CITTEC is not null)','Indicare il codice ISTAT del comune del responsabile del programma','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',81,'PIATRI','Archivio tecnici','TECNI.CFTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CFTEC is not null)','Indicare il codice fiscale del responsabile del programma','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',90,'PIATRI','Adozione/approvazione','Adozione o approvazione','(T.DADOZI is not null or T.DAPTRI is not null) or tiprog in (2,3)','Inserire i dati di adozione e/o di approvazione del programma','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',91,'PIATRI','Adozione','DPADOZI','(T.DADOZI is null or T.DPADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',92,'PIATRI','Adozione','TITADOZI','(T.DADOZI is null or T.TITADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',93,'PIATRI','Adozione','URLADOZI','(T.DADOZI is null or T.URLADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',94,'PIATRI','Approvazione','DPAPPROV','T.DAPTRI is null or T.DPAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',95,'PIATRI','Approvazione','TITAPPROV','T.DAPTRI is null or T.TITAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',96,'PIATRI','Approvazione','URLAPPROV','T.DAPTRI is null or T.URLAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',97,'PIATRI','Approvazione','Approvazione','T.DAPTRI is not null or tiprog=1','Inserire i dati di approvazione del programma','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',100,'INTTRI','Intervento n. {0} del programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',110,'INTTRI','Dati generali','CUIINT','T.CUIINT is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',120,'INTTRI','Dati generali','DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',130,'INTTRI','Dati generali','ANNRIF','T.ANNRIF is not null or exists (select 1 from piatri p join inttri i on i.contri=p.contri and i.tipint=2 where p.tiprog=3 and p.contri=T.contri)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',140,'INTTRI','Dati generali','FLAG_CUP','T.FLAG_CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1'' or ANNINT=''2''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',160,'INTTRI','Dati generali','INTTRI.ACQALTINT','(T.ACQALTINT is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',170,'INTTRI','Dati generali','INTTRI.CUICOLL','(T.CUICOLL is not null or coalesce(T.ACQALTINT,1)<>2) or  T.TIPINT=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',180,'INTTRI','Dati generali','Codice ISTAT o NUTS del comune','T.COMINT is not null or T.TIPINT=2 or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',190,'INTTRI','Dati generali','NUTS','(T.NUTS is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',200,'INTTRI','Dati generali','TIPOIN','T.TIPOIN is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',210,'INTTRI','Dati generali','CODCPV','(T.CODCPV is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',220,'INTTRI','Dati generali','PRGINT','T.PRGINT is not null ','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',230,'INTTRI','Dati generali','CODRUP','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.CODRUP and A.NOMTEC is not null)','Indicare il responsabile dell''intervento','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',240,'INTTRI','Dati generali','LOTFUNZ','T.LOTFUNZ is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',250,'INTTRI','Dati generali','LAVCOMPL','T.LAVCOMPL is not null or T.TIPINT=2 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',260,'INTTRI','Dati generali','DURCONT','T.DURCONT is not null or T.TIPINT=1 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',270,'INTTRI','Dati generali','CONTESS','T.CONTESS is not null or T.TIPINT=1 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',280,'INTTRI','Dati generali','SEZINT','T.SEZINT is not null or T.TIPINT=2 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',290,'INTTRI','Dati generali','INTERV','T.INTERV is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',300,'INTTRI','Dati generali','ANNINT','T.ANNINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',310,'INTTRI','Dati generali','TCPINT','T.TCPINT is not null or coalesce(PR1TRI,0)+coalesce(PR2TRI,0)+coalesce(PR3TRI,0)+coalesce(PR9TRI,0)=0','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNINT<>''1'' or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',800,'OITRI','Opera incompiuta n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',810,'OITRI',null,'NUMOI','T.NUMOI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',820,'OITRI',null,'CUP','T.CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',830,'OITRI',null,'DESCRIZIONE','T.DESCRIZIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',840,'OITRI',null,'DETERMINAZIONI','T.DETERMINAZIONI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',850,'OITRI',null,'AMBITOINT','T.AMBITOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',860,'OITRI',null,'ANNOULTQE','T.ANNOULTQE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',870,'OITRI',null,'IMPORTOINT','T.IMPORTOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',880,'OITRI',null,'IMPORTOLAV','T.IMPORTOLAV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',890,'OITRI',null,'ONERIULTIM','T.ONERIULTIM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',900,'OITRI',null,'IMPORTOSAL','T.IMPORTOSAL is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',910,'OITRI',null,'AVANZAMENTO','T.AVANZAMENTO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',920,'OITRI',null,'CAUSA','T.CAUSA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',930,'OITRI',null,'STATOREAL','T.STATOREAL is not null','Indicare lo stato di realizzazione','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',940,'OITRI',null,'INFRASTRUTTURA','T.INFRASTRUTTURA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',950,'OITRI',null,'FRUIBILE','T.FRUIBILE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',960,'OITRI',null,'UTILIZZORID','T.UTILIZZORID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',970,'OITRI',null,'DESTINAZIONEUSO','T.DESTINAZIONEUSO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',980,'OITRI',null,'CESSIONE','T.CESSIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',990,'OITRI',null,'VENDITA','T.VENDITA is not null','Indicare se &egrave; prevista la vendita','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1000,'OITRI',null,'DEMOLIZIONE','T.DEMOLIZIONE is not null or coalesce(T.VENDITA,''1'')=''1''','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1200,'IMMTRAI','Immobile da trasferire n. {0} dell''intervento',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1210,'IMMTRAI',null,'NUMIMM','T.NUMIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1220,'IMMTRAI',null,'CUIIMM','T.CUIIMM is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1230,'IMMTRAI',null,'DESIMM','T.DESIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1240,'IMMTRAI',null,'Codice ISTAT o NUTS del comune','T.COMIST is not null or T.NUTS is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1250,'IMMTRAI',null,'TITCOR','T.TITCOR is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1260,'IMMTRAI',null,'IMMDISP','T.IMMDISP is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1270,'IMMTRAI',null,'PROGDISM','T.PROGDISM is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1280,'IMMTRAI',null,'TIPDISP','(T.TIPDISP is not null or T.NUMOI is null) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1290,'IMMTRAI',null,'VALIMM','T.VALIMM is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1400,'INRTRI','Intervento non riproposto n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1410,'INRTRI',null,'CUPPRG','T.CUPPRG is not null','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1420,'INRTRI',null,'DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1430,'INRTRI',null,'TOTINT','T.TOTINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1440,'INRTRI',null,'PRGINT','T.PRGINT is not null','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1450,'INRTRI',null,'MOTIVO','T.MOTIVO is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1600,'RIS_CAPITOLO','Risorse per capitolo di bilancio n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1610,'RIS_CAPITOLO',null,'NCAPBIL','T.NCAPBIL is not null','Valorizzare il campo','E');


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

		Delete from C0CAMPI where COC_MNE_UNI in ('URCINT.INTTRI.PIANI','APCINT.INTTRI.PIANI', 'IM1INT.INTTRI.PIANI', 'IM2INT.INTTRI.PIANI', 'IM3INT.INTTRI.PIANI','TIPCAPPRIV.INTTRI.PIANI') or  COC_MNE_UNI like 'VA_TRI.IMMTRAI.PIANI';

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
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'INTERV.OITRI.PIANI','T2INTEOI','Classificazione intervento: categoria','Categoria','A100','PTj01',null,'Classificazione intervento: categoria');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SEZINT.OITRI.PIANI','T2SEZOIT','Classificazione intervento: Tipologia','Tipologia intervento','A100','PTx01',null,'Classificazione intervento: Tipologia');
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
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_RICEVUTO.PIATRI.PIANI','T2IDR','ID ricevuto','ID ricevuto','N10',null,null,'ID ricevuto');
		
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

		Update C0CAMPI set COC_DES='Subcategoria intervento' where COC_MNE_UNI='CATINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: ex sottocategoria' where COC_MNE_UNI='CLAINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Finalità dell''intervento' where COC_MNE_UNI='FIINTR.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: ex categoria' where COC_MNE_UNI='SETINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: Tipologia' where COC_MNE_UNI='SEZINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Stato Progettazione approvata' where COC_MNE_UNI='STAPRO.INTTRI.PIANI';
		
		Update C0CAMPI set C0C_TAB1='PTx08' WHERE COC_MNE_UNI = 'TCPINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_TAB1='PTx09' WHERE COC_MNE_UNI = 'STAPRO.INTTRI.PIANI';

		Update C0CAMPI set C0C_FS='A2000' where COC_MNE_UNI='NOTSCHE4.PIATRI.PIANI';

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
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',1,'Cessione della titolarità dell''opera ad altro ente pubblico',null,1.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',2,'Cessione della titolarità dell''opera a soggetto esercente una funzione pubblica',null,2.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',3,'Vendita al mercato privato',null,3.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',4,'Disponibilità come fonte di finanziamento per realizzazione di intervento ai sensi del c. 5 art.21',null,4.00,null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',1,'No','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',2,'Sì','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',3,'Sì, con CUI non ancora attribuito','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',4,'Sì, interventi o acquisti diversi','2',4,null);
		
		-- tipologia apporto capitale privato
		Delete from TAB1 where TAB1COD='PT009';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','1','Finanza di progetto',null,1,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','2','Concessione',null,2,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','3','Sponsorizzazione',null,3,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','4','Società partecipate o di scopo',null,4,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','5','Locazione finanziaria',null,5,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','6','Contratto di disponibilità ',null,6,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','9','Altro',null,9,null);

		-- Satato progettazione approvato
		Delete from TAB1 where TAB1COD='PT012';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','1','Progetto di fattibilità tecnico-economica: documento di fattibilità delle alternative progettuali',null,1,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','2','Progetto di fattibilità tecnico-economica: documento finale',null,2,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','3','Progetto definitivo',null,3,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','4','Progetto esecutivo',null,4,null);


		-- Classificazione intervento: Tipologia
		Delete from TAB3 where TAB3cod='PTx01';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES
		('PTx01','01','01 - Nuova realizzazione',null,1,null),
		('PTx01','02','02 - Demolizione',null,2,null),
		('PTx01','03','03 - Recupero',null,3,null),
		('PTx01','04','04 - Ristrutturazione',null,4,null),
		('PTx01','05','05 - Restauro',null,5,null),
		('PTx01','08','08 - Ristrutturazione con efficientamento energetico',null,6,null),
		('PTx01','09','09 - Manutenzione straordinaria con efficientamento energetico',null,7,null),
		('PTx01','06','06 - Manutenzione ordinaria',null,8,null),
		('PTx01','07','07 - Manutenzione straordinaria',null,9,null),
		('PTx01','58','58 - Ampliamento o potenziamento',null,10,null),
		('PTx01','60','60 - Ammodernamento tecnologico e laboratoriale',null,11,null),
		('PTx01','59','59 - Lavori socialmente utili',null,12,null),
		('PTx01','99','99 - Altro',null,13,null);

		-- Classificazione intervento: categoria
		Delete from TAB5 where TAB5COD='PTj01';
		Insert into TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES
		('PTj01','01.01','01.01 - Stradali',null,1,null),
		('PTj01','01.02','01.02 - Aeroportuali',null,2,null),
		('PTj01','01.03','01.03 - Ferrovie',null,3,null),
		('PTj01','01.04','01.04 - Marittime lacuali e fluviali',null,4,null),
		('PTj01','01.05','01.05 - Trasporto urbano',null,5,null),
		('PTj01','01.06','01.06 - Trasporti multimodali e altre modalita'' di trasporto',null,6,null),
		('PTj01','02.05','02.05 - Difesa del suolo',null,7,null),
		('PTj01','02.10','02.10 - Smaltimento rifiuti',null,8,null),
		('PTj01','02.11','02.11 - Protezione, valorizzazione e fruizione dell''ambiente',null,9,null),
		('PTj01','02.12','02.12 - Riassetto e recupero di siti urbani e produttivi',null,10,null),
		('PTj01','02.15','02.15 - Risorse idriche e acque reflue',null,11,null),
		('PTj01','03.06','03.06 - Produzione di energia',null,12,null),
		('PTj01','03.16','03.16 - Distribuzione di energia',null,13,null),
		('PTj01','04.39','04.39 - Infrastrutture per l''attrezzatura di aree produttive',null,14,null),
		('PTj01','05.08','05.08 - Sociali e scolastiche',null,15,null),
		('PTj01','05.10','05.10 - Abitative',null,16,null),
		('PTj01','05.11','05.11 - Beni culturali',null,17,null),
		('PTj01','05.12','05.12 - Sport, spettacolo e tempo libero',null,18,null),
		('PTj01','05.30','05.30 - Sanitarie',null,19,null),
		('PTj01','05.31','05.31 - Culto',null,20,null),
		('PTj01','05.32','05.32 - Difesa',null,21,null),
		('PTj01','05.33','05.33 - Direzionali e amministrative',null,22,null),
		('PTj01','05.34','05.34 - Giudiziarie e penitenziarie',null,23,null),
		('PTj01','05.36','05.36 - Pubblica sicurezza',null,24,null),
		('PTj01','05.99','05.99 - Altre infrastrutture sociali',null,25,null),
		('PTj01','06.02','06.02 - Opere, impianti ed attrezzature per il settore silvo-forestale',null,26,null),
		('PTj01','06.13','06.13 - Opere, impianti ed attrezzature per l''agricoltura, la zootecnia e l''agroalimentare',null,27,null),
		('PTj01','06.14','06.14 - Impianti ed attrezzature per la pesca e l''acquacoltura',null,28,null),
		('PTj01','06.39','06.39 - Opere, impianti ed attrezzature per attivita'' industriali e l''artigianato',null,29,null),
		('PTj01','06.40','06.40 - Opere e infrastrutture per la ricerca',null,30,null),
		('PTj01','06.41','06.41 - Opere e strutture per il turismo',null,31,null),
		('PTj01','06.42','06.42 - Strutture ed attrezzature per il commercio e i servizi',null,32,null),
		('PTj01','06.43','06.43 - Opere e infrastrutture per l''impresa sociale',null,33,null),
		('PTj01','07.17','07.17 - Infrastrutture per telecomunicazioni',null,34,null),
		('PTj01','07.18','07.18 - Tecnologie informatiche',null,35,null),
		-- servizi
		('PTj01','08.60','08.60 - Progetti di diffusione e cooperazione pubblico-privata',null,36,null),
		('PTj01','08.61','08.61 - Progetti di ricerca presso universita'' e istituti di ricerca',null,37,null),
		('PTj01','08.62','08.62 - Progetti di ricerca e  di innovazione presso imprese',null,38,null),
		('PTj01','09.20','09.20 - Servizi alle imprese agricole, forestali e della pesca',null,39,null),
		('PTj01','09.21','09.21 - Servizi alle imprese industriali',null,40,null),
		('PTj01','09.22','09.22 - Servizi alle imprese turistiche',null,41,null),
		('PTj01','09.23','09.23 - Servizi di intermediazione finanziaria',null,42,null),
		('PTj01','09.25','09.25 - Servizi alle imprese del commercio',null,43,null),
		('PTj01','09.26','09.26 - Servizi alle nuove imprese (start up) e alle imprese sociali',null,44,null),
		('PTj01','10.01','10.01 - Servizi e tecnologie per l''informazione e le comunicazioni',null,45,null),
		('PTj01','10.02','10.02 - Servizi e applicazioni informatiche per i cittadini e le imprese',null,46,null),
		('PTj01','10.03','10.03 - Azioni innovatrici',null,47,null),
		('PTj01','10.30','10.30 - Servizi a supporto dello sviluppo e della qualificazione del sistema del lavoro',null,48,null),
		('PTj01','10.32','10.32 - Dispositivi e strumenti a supporto della qualificazione del sistema dell''offerta di formazione',null,49,null),
		('PTj01','10.33','10.33 - Dispositivi e strumenti a supporto della qualificazione del sistema dell''offerta di istruzione',null,50,null),
		('PTj01','10.34','10.34 - Dispositivi e strumenti a supporto dell''integrazione fra sistemi',null,51,null),
		('PTj01','10.35','10.35 - Dispositivi e strumenti a supporto della qualificazione nel sistema dei servizi sociali',null,52,null),
		('PTj01','10.41','10.41 - Servizi di assistenza tecnica alla P.A.',null,53,null),
		('PTj01','10.43','10.43 - Servizi di orientamento e accompagnamento al lavoro',null,54,null),
		('PTj01','10.92','10.92 - Servizi ai dipendenti di imprese produttive',null,55,null),
		('PTj01','10.93','10.93 - Servizi essenziali per la popolazione rurale',null,56,null),
		('PTj01','10.94','10.94 - Assistenza sociale e servizi alla persona',null,57,null),
		('PTj01','10.99','10.99 - Altri servizi per la collettivita''',null,58,null),
		('PTj01','11.70','11.70 - Scuola e istruzione',null,59,null),
		('PTj01','11.71','11.71 - Formazione per il lavoro',null,60,null),
		('PTj01','11.72','11.72 - Altri strumenti formativi e di work-experience',null,61,null),
		('PTj01','11.75','11.75 - Contributi ed incentivi al lavoro',null,62,null),
		('PTj01','11.80','11.80 - Altri sostegni per il mercato del lavoro',null,63,null);

		
		
		Update C0CAMPI SET C0C_TAB1='PT010', C0C_FS = 'A100', COC_DOM = null WHERE COC_MNE_UNI = 'VARIATO.INTTRI.PIANI';
		
		Update C0CAMPI set COC_DES='Numero provvedimento adozione', COC_DES_WEB='Numero provvedimento adozione', C0C_FS='A50' where COC_MNE_UNI = 'NADOZI.PIATRI.PIANI';
		Update C0CAMPI set COC_DES='Numero provvedimento approvazione', COC_DES_WEB='Numero provvedimento approvazione', C0C_FS='A50' where COC_MNE_UNI = 'NAPTRI.PIATRI.PIANI';

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DPADOZI.PIATRI.PIANI','T2DTPUBADOZ','Data pubblicazione','Data pubblicazione','A10',null,'DATA_ELDA','Data pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DPAPPROV.PIATRI.PIANI','T2DTPUBAPP','Data pubblicazione','Data pubblicazione','A10',null,'DATA_ELDA','Data pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TITADOZI.PIATRI.PIANI','T2TITOLOADOZ','Titolo del documento','Titolo','A250',null,null,'Titolo');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TITAPPROV.PIATRI.PIANI','T2TITOLOAPP','Titolo del documento','Titolo','A250',null,null,'Titolo');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SYSCON.PIATRI.PIANI','T2SYSCON','Utente','Utente','N12',null,null,'Utente');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IDUFFICIO.PIATRI.PIANI','PIAIDUFF','ID ufficio','ID ufficio','N10',null,null,'ID ufficio');

		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.PIANI.APPLICA-FILTRO-UTENTE','Applica filtro utente su Programmi',6100);

		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.PIANI.APPLICA-FILTRO-UTENTE','Visualizza',0,1,1,3894478462);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.PIATRI.SYSCON','Visualizza',0,1,1,3792932967);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.INTTRI.COPFIN','Visualizza',0,1,1,1479473519);

		Insert into w_note (tipo,oggetto,prog,modovis,nota) VALUES (2,'PIANI.PIATRI-scheda.FLUSSIPROGRAMMI',0,'1','<b>ATTENZIONE: solo per i programmi nel nuovo tracciato, dopo aver effettuato la Pubblicazione del programma non sarà più possibile effettuare modifiche!</b><div>Per aggiornare il programma è possibile effettuarne una copia, ritornando alla lista dei programmi, dal menu pop-up.&nbsp;</div><div>Si rimanda la manuale d''uso per ulteriori dettagli.&nbsp;<span style="font-size: smaller;"><span style="font-size: larger;">Per i programmi antecedenti il DM 14 del 16/01/2018 rimane invece la possibilità di apportare aggiornamenti e ripubblicare il medesimo programma.</span></span></div>');


		Update W_CONFIG set valore='2' where chiave like 'it.eldasoft.pt.norma.%';
UPDATE COMMAND OPTIONS USING s ON;