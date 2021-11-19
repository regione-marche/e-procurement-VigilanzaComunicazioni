--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
------------------------------

------------------------------
-- SITAT 2.8.0
--------------------------

------------------------------------------------------------------------------------------------
------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
------------------------------------------------------------------------------------------------
ALTER TABLE SITATSA.W9AGGI ADD COLUMN ID_GRUPPO NUMERIC(3);
REORG TABLE SITATSA.W9AGGI;


------------------------------------------------------------------------------------------------
------------------------         MODIFICA DATI                           -----------------------
------------------------------------------------------------------------------------------------
UPDATE SITATSA.W9AGGI SET ID_GRUPPO=1 WHERE ID_TIPOAGG<>3;

INSERT INTO SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_GRUPPO.W9AGGI.W9','W3AGIDGRP','Numero raggruppamento','Numero raggruppam.','N3',null,null,'Numero raggruppamento');


INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','ALT.W9.W9HOME.LinkRecuperaDaSITATOR','Visualizzazione link Recupera dati da OR',3355);
INSERT INTO SITATSA.W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.W9HOME.LinkRecuperaDaSITATOR','Visualizzazione link Recupera dati da OR',0,,,);


INSERT INTO SITATSA.C0CAMPI  (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) 
VALUES (0,'E',null,'FASE_ESECUZIONE.W9XML.W9','W9XMLFASEESEC','Fase di esecuzione','Fase di esecuzione','A100','W3020',null,'Tipologia della fase di esecuzione');

INSERT INTO SITATSA.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) 
VALUES (0,'E',null,'NUM.W9XML.W9','W9XMLNUM','Numero progressivo fase','Numero progressivo','N5',null,null,'Numero progressivo fase');


-- aggiornamento campo dati nazione
UPDATE SITATSA.IMPR	SET NAZIMP = null WHERE NAZIMP = 99;
UPDATE SITATSA.TEIM	SET NAZTIM = null WHERE NAZTIM = 99;
UPDATE SITATSA.TECNI SET NAZTEI = null WHERE NAZTEI = 99;
UPDATE SITATSA.IMPIND SET NAZIMP = null WHERE NAZIMP = 99;
UPDATE SITATSA.UTENT SET NAZUTE = null WHERE NAZUTE = 99;
UPDATE SITATSA.TECN SET NAZTEC = null WHERE NAZTEC = 99;
UPDATE SITATSA.UFFINT SET CODNAZ = null WHERE CODNAZ = 99;
UPDATE SITATSA.PUNTICON	SET CODNAZ = null WHERE CODNAZ = 99;


-- gestione della visualizzazione su popup dei centri di costo
INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('MASC','W9.lista-centricosto-popup','Lista Centri di costo su popup',3640);
INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('MASC','W9.SchedaCentriCosto','Scheda Centro di costo su popup',3641);

INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.W9.lista-centricosto-popup.MOD','Modifica Lista Centri di costo su popup',3650);
INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','INS.W9.lista-centricosto-popup.nuovo','Inserimento Lista Centri di costo su popup',3651);
INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.W9.SchedaCentriCosto.SCHEDAMOD','Modifica Scheda Centro di costo su popup',3652);
INSERT INTO SITATSA.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','INS.W9.SchedaCentriCosto.SCHEDANUOVO','Inserimento Scheda Centro di costo su popup',3653);
 
 -- default non attivo
INSERT INTO SITATSA.W_OGGETTI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','INS.W9.lista-centricosto-popup.nuovo','Visualizza',0,1,1,1978773804);
INSERT INTO SITATSA.W_OGGETTI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','INS.W9.SchedaCentriCosto.SCHEDANUOVO','Visualizza',0,1,1,403792812);
INSERT INTO SITATSA.W_OGGETTI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','MOD.W9.lista-centricosto-popup.MOD','Visualizza',0,1,1,2662581498);
INSERT INTO SITATSA.W_OGGETTI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','MOD.W9.SchedaCentriCosto.SCHEDAMOD','Visualizza',0,1,1,3151749585);


-- campo escluso lotto non modificabile
INSERT INTO SITATSA.W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','MOD','W9.W9LOTT.ART_E1','Modifica',0,1,1,1667451274);


UPDATE SITATSA.ELDAVER SET NUMVER='2.8.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP='W9';


-- Cambiamento frase nella validazione impresa --
UPDATE SITATSA.C0CAMPI SET COC_DES = 'Ruolo nell''associazione',COC_DES_WEB = 'Ruolo nell''associazione' WHERE COC_CONTA = 0 AND C0C_TIP = 'E' AND COC_MNE_UNI = 'RUOLO.W9AGGI.W9' AND C0C_MNE_BER = 'W3RUOLO';


