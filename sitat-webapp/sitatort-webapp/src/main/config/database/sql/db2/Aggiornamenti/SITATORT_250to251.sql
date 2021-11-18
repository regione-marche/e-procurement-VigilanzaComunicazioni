------------------------------
-- AGGIORNAMENTO SITAT	
------------------------------

------------------------------
-- SITATORT 2.5.1
--------------------------

INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9FASI-lista.NuovoInvio', 'Visualizzazione colonna Effettua nuovo invio', 3340);
INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9HOME.LinkRecuperaDaSIMOG', 'Visualizzazione link Recupera dati da SIMOG', 3350);
INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9HOME.LinkCreaNuovaGara', 'Visualizzazione link Crea nuova gara', 3360);
INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9HOME.LinkRegioneToscana', 'Visualizzazione link Sole 24 ore Regione Toscana', 3370);
INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9GARA-scheda.ControlloDatiInseriti', 'Controllo dati inseriti', 3390);
INSERT INTO SITATORT.W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.W9.W9LOTT-scheda.CopiaDaProgramma', 'Copia da programma triennale annuale', 3400);


INSERT INTO SITATORT.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('FUNZ', 'VIS', 'ALT.W9.W9HOME.LinkCreaNuovaGara','Visualizza', 0, 1, 1, 3624348451);
INSERT INTO SITATORT.W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) values ('FUNZ','VIS', 'ALT.W9.W9LOTT-scheda.CopiaDaProgramma','Visualizza',0,1,1,4127438285);





UPDATE SITATORT.ELDAVER SET NUMVER='2.5.1' WHERE CODAPP='W9';



