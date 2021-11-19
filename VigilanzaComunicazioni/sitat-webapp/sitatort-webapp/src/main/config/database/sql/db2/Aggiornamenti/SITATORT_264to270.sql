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


Update SITATORT.C0CAMPI set COC_DES_WEB='Codice gara restituito dall''ANAC' where C0C_MNE_BER in ('W3IDGARA','W9GNIDGARA');
Update SITATORT.C0CAMPI set COC_DES='Lotto da esportare verso SITATORT o ANAC?',COC_DES_WEB='Lotto da esportare verso SITATORT o ANAC?' where C0C_MNE_BER='W9LODAEXP';



Update SITATORT.ELDAVER SET NUMVER='2.7.0', DATVET=CURRENT TIMESTAMP WHERE CODAPP='W9';

