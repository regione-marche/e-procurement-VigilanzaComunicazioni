------------------------------
-- AGGIORNAMENTO SITAT	
------------------------------

------------------------------
--  GENE 1.4.55
------------------------------

------------------------------
--  GENEWEB 2.0.7
------------------------------

------------------------------
-- SITATSA 2.7.0
--------------------------


Update SITATSA.C0CAMPI set COC_DES_WEB='Codice gara restituito dall''ANAC' where C0C_MNE_BER in ('W3IDGARA','W9GNIDGARA');
Update SITATSA.C0CAMPI set COC_DES='Lotto da esportare verso SITATORT o ANAC?',COC_DES_WEB='Lotto da esportare verso SITATORT o ANAC?' where C0C_MNE_BER='W9LODAEXP';

INSERT INTO SITATSA.W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) VALUES ('SA-APPA','COLS','MOD','W9.W9LOTT.CUP',0,2223780328);

Update SITATSA.ELDAVER SET NUMVER='2.7.0', DATVET=CURRENT TIMESTAMP WHERE CODAPP='W9';

