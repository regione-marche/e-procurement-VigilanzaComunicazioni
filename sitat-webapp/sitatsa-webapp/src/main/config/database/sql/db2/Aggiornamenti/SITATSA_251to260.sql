------------------------------
-- AGGIORNAMENTO SITAT	
------------------------------
-- GENE 1.4.50
------------------------------


------------------------------
-- GENEWEB 2.0.0
------------------------------


------------------------------
-- SITATSA 2.6.0
--------------------------

--Update SITATSA.TAB1 Set TAB1DESC='Scheda Cantiere/notifica preliminare' where TAB1COD='W3020' and TAB1TIP=998;


ALTER TABLE SITATSA.W9CANT ADD COLUMN TIPOPERA NUMERIC(7);
ALTER TABLE SITATSA.W9CANT ADD COLUMN TIPINTERV NUMERIC(7);
ALTER TABLE SITATSA.W9CANT ADD COLUMN TIPCOSTR NUMERIC(7);
REORG TABLE SITATSA.W9CANT;

ALTER TABLE SITATSA.W9REQU ALTER COLUMN ID_CATEGORIA SET DATA TYPE VARCHAR(10);
ALTER TABLE SITATSA.W9SUBA ALTER COLUMN ID_CATEGORIA SET DATA TYPE VARCHAR(10);
ALTER TABLE SITATSA.W9LOTT ALTER COLUMN ID_CATEGORIA_PREVALENTE SET DATA TYPE VARCHAR(10);
ALTER TABLE SITATSA.W9LOTTCATE ALTER COLUMN CATEGORIA SET DATA TYPE VARCHAR(10);
ALTER TABLE SITATSA.W9LOTT_ENTINAZ ALTER COLUMN ID_CATEGORIA_PREVALENTE SET DATA TYPE VARCHAR(10);




Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPOPERA.W9CANT.W9','W9CATIPOPERA','Tipo di opera','Tipo di opera','A100','W9009',null,'Tipo di opera');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPINTERV.W9CANT.W9','W9CATIPINTER','Tipologia di intervento','Tipologia intervento','A100','W9010',null,'Tipologia di intervento');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TIPCOSTR.W9CANT.W9','W9CATIPCOSTR','Tipologia costruttiva','Tipol. costruttiva','A100','W9011',null,'Tipologia costruttiva');

Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 1, 'Edilizia civile OG1 Nuova edilizia civile compresi impianti e forniture',null,1);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 2, 'Edilizia civile Ristrutturazione di edifici civili',null,2);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 3, 'Edilizia produttiva OG1 Nuova edilizia industriale esclusi impianti',null,3);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 4, 'Edilizia produttiva Ristrutturazione di edifici industriali esclusi impianti',null,4);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 5, 'Restauro e manutenzione di beni tutelati OG2 restauro e manutenzione di beni tutelati',null,5);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 6, 'Opere stradali, ponti, ferroviarie OG3 Opere stradali',null,6);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 7, 'Opere stradali, ponti, ferroviarie OG3 Ponti',null,7);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 8, 'Opere stradali, ponti, ferroviarie OG3 Ferroviarie',null,8);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 9, 'Opere d''arte nel sottosuolo OG4 Gallerie, Trafori',null,9);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 10, 'Opere d''arte nel sottosuolo Parcheggi sotterranei',null,10);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 11, 'Opere d''arte nel sottosuolo Tunnel',null,11);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 12, 'Opere idrauliche, marittime, idroelettriche OG5 Dighe',null,12);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 13, 'Opere idrauliche, marittime, idroelettriche OG6 Acquedotti e fognature',null,13);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 14, 'Opere idrauliche, marittime, idroelettriche OG6 Gasdotti',null,14);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 15, 'Opere idrauliche, marittime, idroelettriche OG6 Oleodotti',null,15);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 16, 'Opere idrauliche, marittime, idroelettriche OG6 Opere di irrigazione ed evacuazione',null,16);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 17, 'Opere idrauliche, marittime, idroelettriche OG7 Opere marittime',null,17);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 18, 'Opere idrauliche, marittime, idroelettriche OG8 Opere fluviali',null,18);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 19, 'Opere di bonifica, sistemazione forestale e di sterro OG12 – Bonifica e protezione ambientale',null,19);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 20, 'Opere di bonifica, sistemazione forestale e di sterro OG13  Opere di ingegneria naturalistica',null,20);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 21, 'Parti strutturali di linee elettriche/impianti elettrici OG9 Impianti produzione di energia elett.',null,21);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9009', 22, 'Impianti per la trasformazione e distribuzione di energia elettrica OG10',null,22);

Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 1, 'Nuova costruzione',null,1);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 2, 'Manutenzione',null,2);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 3, 'Demolizione',null,3);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 4, 'Ristrutturazione',null,4);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 5, 'Riparazione',null,5);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 6, 'Conservazione',null,6);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 7, 'Risanamento',null,7);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9010', 8, 'Montaggio e/o Smontaggio',null,8);

Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 1, 'Muratura',null,1);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 2, 'Cemento armato',null,2);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 3, 'Legno',null,3);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 4, 'Acciaio',null,4);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 5, 'Prefabbricati',null,5);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9011', 6, 'Altro',null,6);

Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3004', 9, 'Responsabile subfase del procedimento',null,null);

Update SITATSA.TAB1 set TAB1DESC='Affidamento in economia - Cottimo fiduciario' where TAB1COD='W3005' and TAB1TIP=7;
Update SITATSA.TAB1 set TAB1DESC='Dialogo competitivo D.Lgs 163/2006 art.58' where TAB1COD='W3005' and TAB1TIP=8;
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3005', 18, 'Affidamento diretto in adesione ad accordo quadro/convenzione',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3005', 19, 'Confronto competitivo in adesione ad accordo quadro/convenzione',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3005', 20, 'Procedura ai sensi dei regolamenti degli organi costituzionali',null,null);

Delete from SITATSA.TAB1 where TAB1COD='W3006' and TAB1TIP =26;
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3006', 31, 'D. Lgs. 163/2006, art.122, c.7 (N.B. Solo per lavori)',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3006', 32, 'D. Lgs. 163/2006, art.91, c.2',null,null);

Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 7, 'Sopravvenute esigenze normative e regolamentari',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 8, 'Cause impreviste ed imprevedibili',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 9, 'Eventi dipendenti dalla natura e specificità dei beni e dei luoghi',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 10, 'Miglioramento o migliore funzionalità delle prestazioni',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 11, 'Art. 205 c.1 D.Lgs 163/2006',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 12, 'Art. 205 c.3 D.Lgs 163/2006',null,null);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W3017', 13, 'Art. 205 c.4 D.Lgs 163/2006',null,null);

--Eliminazione temporanea notifiche preliminari
Delete from SITATSA.TAB1 WHERE TAB1COD='W9008';


Delete from SITATSA.TAB2 where TAB2COD='W3z03';
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','FB','FB','Fornitura di beni',null,10);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','FS','FS','Fornitura di servizi',null,20);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','AA','AA','ALTRO (Stazioni appaltanti con sistema di qualificazione proprio)',null,30);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG1','OG1','OG1 - Edifici civili e industriali',null,40);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG2','OG2','OG2 - Restauro e manutenzione dei beni immobili sottoposti a tutela ai sensi delle disposizioni in m',null,50);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG3','OG3','OG3 - Strade, autostrade, ponti, viadotti, ferrovie, metropolitane, funicolari, piste aeroportuali e',null,60);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG4','OG4','OG4 - Opere d''arte nel sottosuolo',null,70);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG5','OG5','OG5 - Dighe',null,80);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG6','OG6','OG6 - Acquedotti, gasdotti, oleodotti, opere di irrigazione e di evacuazione',null,90);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG7','OG7','OG7 - Opere marittime e lavori di dragaggio',null,100);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG8','OG8','OG8 - Opere fluviali, di difesa, di sistemazione idraulica e di bonifica',null,110);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG9','OG9','OG9 - Impianti per la produzione di energia elettrica',null,120);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG10','OG10','OG10 - Impianti per la trasformazione alta/media tensione e per la distribuzione di energia elettric',null,130);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG11','OG11','OG11 - Impianti tecnologici',null,140);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG12','OG12','OG12 - Opere ed impianti di bonifica e protezione ambientale',null,150);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OG13','OG13','OG13 - Opere di ingegneria naturalistica',null,160);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS1','OS1','OS1 - Lavori in terra',null,170);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS2A','OS2-A','OS2-A - Superfici decorate di beni immobili del patrimonio culturale e beni culturali mobili di...',null,180);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS2B','OS2-B','OS2-B - Beni culturali mobili di interesse archivistico e librario',null,190);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS3','OS3','OS3 - Impianti idrico - sanitario, cucine, lavanderie',null,200);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS4','OS4','OS4 - Impianti elettromeccanici trasportatori',null,210);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS5','OS5','OS5 - Impianti pneumatici e antintrusione',null,220);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS6','OS6','OS6 - Finiture di opere generali in materiali lignei, plastici, metallici e vetrosi',null,230);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS7','OS7','OS7 - Finiture di opere generali di natura edile',null,240);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS8','OS8','OS8 - Opere di impermeabilizzazione',null,250);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS9','OS9','OS9 - Impianti per la segnaletica luminosa e la sicurezza del traffico',null,260);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS10','OS10','OS10 - Segnaletica stradale non luminosa',null,270);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS11','OS11','OS11 - Apparecchiature strutturali speciali',null,280);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS12A','OS12-A','OS12-A - Barriere stradali di sicurezza',null,290);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS12B','OS12-B','OS12-B - Barriere paramassi, fermaneve e simili',null,300);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS13','OS13','OS13 - Strutture prefabbricate in cemento armato',null,310);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS14','OS14','OS14 - Impianti di smaltimento e recupero rifiuti',null,320);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS15','OS15','OS15 - Pulizia di acque marine, lacustri, fluviali',null,330);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS16','OS16','OS16 - Impianti per centrali produzione energia elettrica',null,340);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS17','OS17','OS17 - Linee telefoniche ed impianti di telefonia',null,350);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS18A','OS18-A','OS18-A - Componenti strutturali in acciaio',null,360);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS18B','OS18-B','OS18-B - Componenti per facciate continue',null,370);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS19','OS19','OS19 - Impianti di reti di telecomunicazione e di trasmissione dati',null,380);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS20A','OS20-A','OS20-A - Rilevamenti topografici',null,390);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS20B','OS20-B','OS20-B - Indagini geognostiche',null,400);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS21','OS21','OS21 - Opere strutturali speciali',null,410);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS22','OS22','OS22 - Impianti di potabilizzazione e depurazione',null,420);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS23','OS23','OS23 - Demolizione di opere',null,430);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS24','OS24','OS24 - Verde e arredo urbano',null,440);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS25','OS25','OS25 - Scavi archeologici',null,450);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS26','OS26','OS26 - Pavimentazioni e sovrastrutture speciali',null,460);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS27','OS27','OS27 - Impianti per la trazione elettrica',null,470);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS28','OS28','OS28 - Impianti termici e di condizionamento',null,480);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS29','OS29','OS29 - Armamento ferroviario',null,490);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS30','OS30','OS30 - Impianti interni elettrici, telefonici, radiotelefonici e televisivi',null,500);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS31','OS31','OS31 - Impianti per la mobilita'' sospesa',null,510);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS32','OS32','OS32 - Strutture in legno',null,520);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS33','OS33','OS33 - Coperture speciali',null,530);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS34','OS34','OS34 - Sistemi antirumore per infrastrutture di mobilita''',null,540);
Insert into SITATSA.TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2MOD,TAB2NORD) values ('W3z03','OS35','OS35','OS35 - Interventi a basso impatto ambientale',null,550);



Update SITATSA.C0CAMPI set COC_DES='Data effettivo inizio lavori/forniture/servizi',COC_DES_WEB='Data effettivo inizio lavori/forniture/servizi' where C0C_MNE_BER='W3DVERB_IN';
Update SITATSA.C0CAMPI set COC_DES='Termine contratt. per ultimazione lavori/forniture/servizi',COC_DES_WEB='Termine contrattuale per ultimazione lavori/forniture/servizi' where C0C_MNE_BER='W3DTERM1';

-------------------------------------------
--   CREAZIONE TABELLA FILET2 PER ESTAV  --
-------------------------------------------

CREATE TABLE SITATSA.FILET2
(
   GRUPPO VARCHAR(20),
   CODGARA NUMERIC(10,0) NOT NULL,
   CODLOTT NUMERIC(10,0) NOT NULL,
   SUBLOTTO VARCHAR(3),
   COMPSUBLOTTO VARCHAR(10),
   DESCPROD VARCHAR(200),
   DESCAGG VARCHAR(400),
   AIC_PARAF VARCHAR(20),
   UNI_MIS VARCHAR(20),
   FORMA VARCHAR(100),
   QUANT NUMERIC(15,0),
   COD_FORN VARCHAR(6),
   PIVA_FORN VARCHAR(20),
   RAG_SOC_FORN VARCHAR(100),
   NOM_COMME VARCHAR(100),
   ATC_CND VARCHAR(50),
   CLA_FARM VARCHAR(6),
   COD_REP_DM VARCHAR(20),
   COD_PRO_INT_FORN VARCHAR(25),
   COD_BARRE VARCHAR(20),
   CLASSE_MERC VARCHAR(6),
   NUM_PEZZ_CONF VARCHAR(25),
   COD_LIST_ACQ VARCHAR(10),
   PREZ_LIST_CONF NUMERIC(24,5),
   PREZ_OFFE_UNIT NUMERIC(24,5),
   FLAG_EX VARCHAR(1),
   SCONTO NUMERIC(10,5),
   SCONTO2 NUMERIC(10,5),
   ALI_IVA VARCHAR(3),
   ALI_IVA_AGE VARCHAR(3),
   COD_LIST_VEND NUMERIC(6,0),
   CLASS_1_LIV VARCHAR(2),
   CLASS_2_LIV VARCHAR(2),
   STATO_ARI VARCHAR(1),
   GG_SCORTA_MIN NUMERIC(3,0),
   GG_SCORTA_MAX NUMERIC(3,0),
   TEMP_CONS VARCHAR(2),
   GG_PRE_CAP_CONS NUMERIC(3,0),
   IMB_SEC NUMERIC(6,0),
   MIN_FATT NUMERIC(24,5),
   PREV_TRAS_SPEC VARCHAR(1),
   CONF_VEND_PICC VARCHAR(3),
   VALORE_LOTTO NUMERIC(24,5),
   PESO NUMERIC(10,5),
   DIM_X NUMERIC(10,0),
   DIM_Y NUMERIC(10,0),
   DIM_Z NUMERIC(10,0),
   VOLUMINOSO VARCHAR(2),
   COD_SOST VARCHAR(20),
   MOD_SOSTIT VARCHAR(1),
   QUANT_MIN_RIOR VARCHAR(20),
   QUANT_MIN_REINT VARCHAR(20)
) IN TBS_SITATSA_N0 INDEX IN TBS_SITATSA_N0;
ALTER TABLE SITATSA.FILET2 ADD CONSTRAINT FILET2_PK PRIMARY KEY (CODGARA,CODLOTT);
GRANT SELECT,INSERT,UPDATE,DELETE ON TABLE SITATSA.FILET2 TO USER SITATSA;

-------------------------------------------
--   MODIFICHE C0ENTIT FILET2 PER ESTAV  --
-------------------------------------------
Insert into SITATSA.C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('FILET2.W9',0,'P','FILET2','File T2','CODGARA.FILET2.W9,CODLOTT.FILET2.W9',null,null,null,null,'w9_conc0');

-------------------------------------------
--   MODIFICHE C0CAMPI FILET2 PER ESTAV  --
-------------------------------------------
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'GRUPPO.FILET2.W9','W9T2GRUPPO','Gruppo','Gruppo','A20',null,null,'Gruppo');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','CODGARA.FILET2.W9','W9T2CODGARA','Codice gara','Codice Gara','N10',null,null,'Codice gara');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','CODLOTT.FILET2.W9','W9T2CODLOT','Lotto','Lotto','N10',null,null,'Lotto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SUBLOTTO.FILET2.W9','W9T2SUBLOT','Sublotto','Sublotto','A3',null,null,'Sublotto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COMPSUBLOTTO.FILET2.W9','W9T2CMPSUBLT','Componente sublotto','Componente sublotto','A10',null,null,'Componente sublotto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DESCPROD.FILET2.W9','W9T2DESCPR','Descrizione Prodotto (per farmaci: Principio Attivo)','Descr. Prodotto','A200',null,null,'Descrizione Prodotto (per farmaci: Principio Attivo)');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DESCAGG.FILET2.W9','W9T2DESCAG','Descrizione aggiuntiva','Descr. Aggiuntiva','A400',null,null,'Descrizione aggiuntiva');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AIC_PARAF.FILET2.W9','W9T2AIC_PAF','AIC o PARAF','AIC o PARAF','A20',null,null,'AIC o PARAF');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'UNI_MIS.FILET2.W9','W9T2UNI_MI','Unità di misura','Unità di misura','A20',null,null,'Unità di misura');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FORMA.FILET2.W9','W9T2FORMA','Forma farmaceutica o altre specificazioni','Form. farm. o altra','A100',null,null,'Forma farmaceutica o altre specificazioni');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'QUANT.FILET2.W9','W9T2QUANT','Quantità complessiva','Quantità compless.','N15',null,null,'Quantità complessiva');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_FORN.FILET2.W9','W9T2COD_FO','Codice Fornitore','Codice Fornitore','A6',null,null,'Codice Fornitore');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PIVA_FORN.FILET2.W9','W9T2PIVA_FN','Partita IVA Fornitore','PIVA Fornitore','A20',null,null,'Partita IVA Fornitore');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'RAG_SOC_FORN.FILET2.W9','W9T2RAGSOCFO','Ragione Sociale Fornitore','Rag. Soc. Fornitore','A100',null,null,'Ragione Sociale Fornitore');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOM_COMME.FILET2.W9','W9T2NOM_COE','Nome Commerciale','Nome Commerciale','A100',null,null,'Nome Commerciale');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ATC_CND.FILET2.W9','W9T2ATC_CN','ATC','ATC','A50',null,null,'ATC');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CLA_FARM.FILET2.W9','W9T2CLA_FA','Classe farmaceutica','Classe farmaceutica','A6',null,null,'Classe farmaceutica');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_REP_DM.FILET2.W9','W9T2CODREPDM','Codice Repertorio DM (per dispositivi medici)','Cod. Repertorio DM','A20',null,null,'Codice Repertorio DM (per dispositivi medici)');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_PRO_INT_FORN.FILET2.W9','W9T2CODPRINF','Codice prodotto interno del Fornitore','Cod. Prod. Int. Forn','A20',null,null,'Codice prodotto interno del Fornitore');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_BARRE.FILET2.W9','W9T2COD_BAE','Codice a Barre','Cod. a Barre','A20',null,null,'Codice a Barre');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CLASSE_MERC.FILET2.W9','W9T2CLASMERC','Classe Merceologica','Classe Merceologica','A50',null,null,'Classe Merceologica');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUM_PEZZ_CONF.FILET2.W9','W9T2N_PEZCOF','Numero di pezzi per confezione','Num. di pz per conf','A25',null,null,'Numero di pezzi per confezione');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_LIST_ACQ.FILET2.W9','W9T2CODLISAC','Codice Listino di acquisto','Cod. List. di acqui.','A20',null,null,'Codice Listino di acquisto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PREZ_LIST_CONF.FILET2.W9','W9T2PREZLTAC','Prezzo di listino a confezione','Prez. list. a conf','F15',null,null,'Prezzo di listino a confezione');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PREZ_OFFE_UNIT.FILET2.W9','W9T2PREZOFUN','Prezzo offerto unitario','Prez. offerto unitat','F15',null,null,'Prezzo offerto unitario');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FLAG_EX.FILET2.W9','W9T2FLAG_E','Flag Ex Factory','Flag Ex Factory','A1',null,null,'Flag Ex Factory');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SCONTO.FILET2.W9','W9T2SCONTO','Sconto','Sconto','N10',null,null,'Sconto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SCONTO2.FILET2.W9','W9T2SCONTO2','Sconto 2','Sconto 2','N10',null,null,'Sconto 2');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALI_IVA.FILET2.W9','W9T2ALI_IV','Aliquota IVA','Aliquota IVA','A3',null,null,'Aliquota IVA');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALI_IVA_AGE.FILET2.W9','W9T2ALIIVAGE','Aliquota IVA agevolata','Aliquota IVA Agevol.','A3',null,null,'Aliquota IVA agevolata');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_LIST_VEND.FILET2.W9','W9T2CODLISVE','Codice Listino di vendita','Cod. Listino di vend','A50',null,null,'Codice Listino di vendita');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CLASS_1_LIV.FILET2.W9','W9T2CLASSL1','Classificazione 1 livello','Class. 1 liv.','A10',null,null,'Classificazione 1 livello');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CLASS_2_LIV.FILET2.W9','W9T2CLASSL2','Classificazione 2 livello','Class. 2 liv.','A10',null,null,'Classificazione 2 livello');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'STATO_ARI.FILET2.W9','W9T2STATO_I','Stato articolo','Stato articolo','A10',null,null,'Stato articolo');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'GG_SCORTA_MIN.FILET2.W9','W9T2GSCORMIN','Giorni scorta minima','Gg scorta min.','A3',null,null,'Giorni scorta minima');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'GG_SCORTA_MAX.FILET2.W9','W9T2GSCORMAX','Giorni scorta massima','Gg scorta max.','A3',null,null,'Giorni scorta massima');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TEMP_CONS.FILET2.W9','W9T2TEMP_CS','Temperatura di conservazione','Temp. di conserv.','A3',null,null,'Temperatura di conservazione');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'GG_PRE_CAP_CONS.FILET2.W9','W9T2GPRECACO','Giorni previsti in capitolato per la consegna','Gg previsti in cap.','A3',null,null,'Giorni previsti in capitolato per la consegna');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IMB_SEC.FILET2.W9','W9T2IMB_SE','Imballo secondario','Imballo secondario','A25',null,null,'Imballo secondario');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MIN_FATT.FILET2.W9','W9T2MIN_FA','Minimo fatturabile','Minimo fatturabile','F15',null,null,'Minimo fatturabile');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PREV_TRAS_SPEC.FILET2.W9','W9T2PREVTRSP','Previsto trasporto speciale?','Prev. trasp. spec.?','A3',null,null,'Previsto trasporto speciale?');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CONF_VEND_PICC.FILET2.W9','W9T2CONFVEPI','Confezione di vendita piccola','Confez. di vendita','A3',null,null,'Confezione di vendita piccola');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VALORE_LOTTO.FILET2.W9','W9T2VALOLOTT','Valore lotto','Valore lotto','N15',null,null,'Valore lotto');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PESO.FILET2.W9','W9T2PESO','Peso','Peso','N10',null,null,'Peso');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_X.FILET2.W9','W9T2DIM_X','Dimensioni x','Dimensioni x','N10',null,null,'Dimensioni x');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_Y.FILET2.W9','W9T2DIM_Y','Dimensioni y','Dimensioni y','N10',null,null,'Dimensioni y');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_Z.FILET2.W9','W9T2DIM_Z','Dimensioni z','Dimensioni z','N10',null,null,'Dimensioni z');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VOLUMINOSO.FILET2.W9','W9T2VOLUMISO','Voluminoso','Voluminoso','A2',null,null,'Voluminoso');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COD_SOST.FILET2.W9','W9T2COD_SO','Codice in sostituzione','Cod. in sostituzione','A20',null,null,'Codice in sostituzione');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MOD_SOSTIT.FILET2.W9','W9T2MOD_SOST','Mod. sostit.','Mod. sostit.','A1',null,null,'Mod. sostit.');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'QUANT_MIN_RIOR.FILET2.W9','W9T2QMINRIOR','Quantità minima di riordino','Quantità min riord.','A20',null,null,'Quantità minima di riordino');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'QUANT_MIN_REINT.FILET2.W9','W9T2QMINRENT','Quantità minima di reintegro','Quantità min reint.','A20',null,null,'Quantità minima di reintegro');



ALTER TABLE SITATSA.W9LOTT ADD COLUMN DEC VARCHAR(10);
ALTER TABLE SITATSA.W9LOTT ADD COLUMN INVIO_CONSENTITO NUMERIC(7);
REORG TABLE SITATSA.W9LOTT;

ALTER TABLE SITATSA.W9APPA ADD COLUMN IVA NUMERIC(13,9);
ALTER TABLE SITATSA.W9APPA ADD COLUMN IMPORTO_IVA NUMERIC(24,5);
ALTER TABLE SITATSA.W9APPA ADD COLUMN ALTRE_SOMME NUMERIC(24,5);
REORG TABLE SITATSA.W9APPA;




Update SITATSA.W9APPA set IVA=0, IMPORTO_IVA=0, ALTRE_SOMME=IMPORTO_DISPOSIZIONE;


Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DEC.W9LOTT.W9','W3LOTDEC','Codice del DEC','Codice DEC','A10',null,null,'Codice del DEC');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'INVIO_CONSENTITO.W9LOTT.W9','W9LOINVIOCON','Invio schede post aggiudicazione consentito a','Invio consentito a','A100','W9012',null,'Invio schede post aggiudicazione consentito a');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IVA.W9APPA.W9','W9APIVA','IVA %','IVA %','F13.9',null,'PRC','IVA %');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IMPORTO_IVA.W9APPA.W9','W9APIMPIVA','Importo IVA','Importo IVA','F15',null,'MONEY','Importo IVA');
Insert into SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALTRE_SOMME.W9APPA.W9','W9APALTSOM','Altre somme a disposizione','Altre somme dispos.','F15',null,'MONEY','Altre somme a disposizione');



Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9012',1,'Solo RUP',null,1);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9012',2,'Solo DEC',null,2);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9012',3,'RUP e DEC',null,3);
Insert into SITATSA.TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9013',1,'21',null,null);


INSERT INTO SITATSA.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.PRODOTTI', 'Importazione file prodotti Estav', 3380);
INSERT INTO SITATSA.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9GARA-scheda.PopolamentoMassivoLotti', 'Popolamento massivo schede lotti', 3410);

INSERT INTO SITATSA.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('FUNZ','VIS', 'ALT.W9.W9GARA-scheda.PopolamentoMassivoLotti','Visualizza',0,1,1,675619582);
INSERT INTO SITATSA.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('FUNZ','VIS', 'ALT.W9.PRODOTTI','Visualizza',0,1,1,3996373229);
INSERT INTO SITATSA.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('COLS','VIS', 'W9.W9LOTT.DEC','Visualizza',0,1,1,2925547259);
INSERT INTO SITATSA.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('COLS','VIS', 'W9.W9LOTT.INVIO_CONSENTITO','Visualizza',0,1,1,893207224);

Delete from SITATSA.W_PROAZI WHERE COD_PROFILO='SA-APPA' AND TIPO='COLS' AND AZIONE='MOD' AND OGGETTO='W9.W9LOTT.SOMMA_URGENZA';

Delete from SITATSA.W_PROAZI where cod_profilo='SA-AVVISI' and oggetto='ARCHIVI.Archivio-staz-appaltanti' and tipo='SUBMENU';
Insert into SITATSA.W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','ARCHIVI.Archivio-tecnici',1,2191674685);




UPDATE SITATSA.ELDAVER SET NUMVER='2.6.0' WHERE CODAPP='W9';

