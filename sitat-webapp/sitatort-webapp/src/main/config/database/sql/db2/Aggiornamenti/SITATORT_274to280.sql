--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA	
--------------------------------


------------------------------------------------------------------------------------------------
------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
------------------------------------------------------------------------------------------------
ALTER TABLE SITATORT.W9AGGI ADD COLUMN ID_GRUPPO NUMERIC(3);
REORG TABLE SITATORT.W9AGGI;


------------------------------------------------------------------------------------------------
------------------------         MODIFICA DATI                           -----------------------
------------------------------------------------------------------------------------------------
UPDATE SITATORT.W9AGGI SET ID_GRUPPO=1 WHERE ID_TIPOAGG<>3;

INSERT INTO SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_GRUPPO.W9AGGI.W9','W3AGIDGRP','Numero raggruppamento','Numero raggruppam.','N3',null,null,'Numero raggruppamento');


INSERT INTO SITATORT.W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','ALT.W9.W9HOME.LinkRecuperaDaSITATOR','Visualizzazione link Recupera dati da OR',3355);
INSERT INTO SITATORT.W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.W9HOME.LinkRecuperaDaSITATOR','Visualizzazione link Recupera dati da OR',0,,,);


INSERT INTO SITATORT.C0CAMPI  (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) 
VALUES (0,'E',null,'FASE_ESECUZIONE.W9XML.W9','W9XMLFASEESEC','Fase di esecuzione','Fase di esecuzione','A100','W3020',null,'Tipologia della fase di esecuzione');

INSERT INTO SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) 
VALUES (0,'E',null,'NUM.W9XML.W9','W9XMLNUM','Numero progressivo fase','Numero progressivo','N5',null,null,'Numero progressivo fase');

INSERT INTO SITATORT.C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) 
VALUES (0,'E',null,'IDFLUSSO.W9XML.W9','W9XMLID','ID dell''invio','ID invio','N10',null,null,'ID dell''invio');

-- aggiornamento campo dati nazione
UPDATE SITATORT.IMPR	SET NAZIMP = null WHERE NAZIMP = 99;
UPDATE SITATORT.TEIM	SET NAZTIM = null WHERE NAZTIM = 99;
UPDATE SITATORT.TECNI SET NAZTEI = null WHERE NAZTEI = 99;
UPDATE SITATORT.IMPIND SET NAZIMP = null WHERE NAZIMP = 99;
UPDATE SITATORT.UTENT SET NAZUTE = null WHERE NAZUTE = 99;
UPDATE SITATORT.TECN SET NAZTEC = null WHERE NAZTEC = 99;
UPDATE SITATORT.UFFINT SET CODNAZ = null WHERE CODNAZ = 99;
UPDATE SITATORT.PUNTICON	SET CODNAZ = null WHERE CODNAZ = 99;

-- campi dati per gestione invii LoaderAppalto
ALTER TABLE SITATORT.W9XML ADD FASE_ESECUZIONE DECIMAL;

ALTER TABLE SITATORT.W9XML ADD NUM DECIMAL;


UPDATE SITATORT.ELDAVER SET NUMVER='2.8.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP='W9';


-- Cambiamento frase nella validazione impresa --
UPDATE SITATORT.C0CAMPI SET COC_DES = 'Ruolo nell''associazione',COC_DES_WEB = 'Ruolo nell''associazione' WHERE COC_CONTA = 0 AND C0C_TIP = 'E' AND COC_MNE_UNI = 'RUOLO.W9AGGI.W9' AND C0C_MNE_BER = 'W3RUOLO';

