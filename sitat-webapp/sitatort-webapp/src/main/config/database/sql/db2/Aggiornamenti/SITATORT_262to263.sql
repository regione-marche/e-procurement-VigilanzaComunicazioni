------------------------------
-- AGGIORNAMENTO SITAT	
------------------------------
-- SITATORT 2.6.3
--------------------------

INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9HOME.ImportRegioneMarche', 'Importazione gare da XML per Regione Marche', 3450);
INSERT INTO SITATORT.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('FUNZ','VIS', 'ALT.W9.W9HOME.ImportRegioneMarche','Visualizza',0,1,1,4111343790);

UPDATE SITATORT.ELDAVER SET NUMVER='2.6.3' WHERE CODAPP='W9';

