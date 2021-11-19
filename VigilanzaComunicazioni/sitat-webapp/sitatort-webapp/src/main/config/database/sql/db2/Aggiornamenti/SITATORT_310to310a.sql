------ SCHEMA SITAT ORT ----------
SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @


--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 3.1.0 --> 3.1.0.a
------------------------------


--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF (select count(*) from eldaver where codapp='W9' and numver='3.1.0')=1 THEN
	
	------------------------------------------------------------------------------------------------
	------------------------		MODIFICA STRUTTURA TABELLE			------------------------
	------------------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------------------
	----------------------------------------    MODIFICA DATI    -----------------------------------
	------------------------------------------------------------------------------------------------
		Insert into w_note (TIPO,OGGETTO,PROG,MODOVIS,NOTA) values (2,'W3.W3GARA-scheda.W3LOTT',0,'1','<b>ATTENZIONE:</b> dopo aver ottenuto il CIG provvisorio da questa applicazione, per la <b>"Conferma dei requisiti"</b> di gara (AVCPass) e quindi <b>perfezionare/pubblicare il CIG</b> è necessario procedere sul <a href="https://simog.anticorruzione.it">SIMOG</a>.<br>');
	
		Update ELDAVER set NUMVER='3.1.0.a', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
