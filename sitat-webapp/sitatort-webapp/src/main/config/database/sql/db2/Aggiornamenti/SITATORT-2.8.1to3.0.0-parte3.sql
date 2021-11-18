------ SCHEMA SITAT ORT ----------
SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;

UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @


------------------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA parte 3
-- Database: DB2
-- Versione: 2.8.1 --> 3.0.0
------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

IF (select count(*) from W9PUBBLICAZIONI)=0
	THEN
		
		------------------------------------------------------------------------------------------------
		-------------------------------    RECUPERO DATI ESISTENTI   -----------------------------------
		------------------------------------------------------------------------------------------------
		
		-- BANDI --
		SET v_sql = 'Insert into W9PUBBLICAZIONI (CODGARA,NUM_PUBB,TIPDOC,DATAPUBB,DATASCAD) select distinct D.CODGARA,1,3,G.DGURI,G.DSCADE FROM W9DOCGARA D join W9GARA G on G.CODGARA=D.CODGARA';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'Insert into W9PUBLOTTO (CODGARA, NUM_PUBB, CODLOTT) select P.CODGARA,P.NUM_PUBB,L.CODLOTT from W9PUBBLICAZIONI P join W9LOTT L on L.CODGARA=P.CODGARA';
		EXECUTE immediate v_sql;
		
		SELECT cast(MAX(idflusso) as char(10)) INTO v_max FROM W9FLUSSI;
		SET v_sql = 'Insert into W9FLUSSI (IDFLUSSO, AREA, KEY01, KEY03, KEY04, TINVIO2, DATINV, AUTORE, XML, VERXML, CFSA, NOTEINVIO) select ROW_NUMBER() OVER() + ' ||  v_max || ',  V.* from (select distinct  2, KEY01, 901, 1, TINVIO2, DATINV, AUTORE, ''<void>'', VERXML, CFSA, ''Inviato con la gara'' from w9Flussi F join W9DOCGARA D on D.codgara=F.key01 where area=2 and TINVIO2=1) V';
		EXECUTE immediate v_sql;
		
		--ESITI INVIATI --
		SET v_sql = 'Insert into W9PUBBLICAZIONI (CODGARA,NUM_PUBB,TIPDOC,DESCRIZ) select distinct CODGARA,CODLOTT+1,20,''Esito di gara'' FROM W9ESITO E join W9FLUSSI F on E.CODGARA=F.KEY01 and E.CODLOTT=F.KEY02 and F.KEY03=984 and F.TINVIO2=1 where data_verb_aggiudicazione >= ''2016-04-19'' and file_allegato is not null';
		EXECUTE immediate v_sql;

		SET v_sql = 'Insert into W9DOCGARA (CODGARA,NUM_PUBB,NUMDOC,TITOLO,FILE_ALLEGATO) select P.CODGARA,P.NUM_PUBB,1,''Esito di gara'',FILE_ALLEGATO FROM W9ESITO E join W9PUBBLICAZIONI P on E.CODGARA=P.CODGARA and E.CODLOTT=P.NUM_PUBB-1 and TIPDOC=20';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'Insert into W9PUBLOTTO (CODGARA, NUM_PUBB, CODLOTT) select P.CODGARA,P.NUM_PUBB,CODLOTT from W9ESITO E join W9PUBBLICAZIONI P on E.CODGARA=P.CODGARA and E.CODLOTT=P.NUM_PUBB-1 and TIPDOC=20';
		EXECUTE immediate v_sql;
		
		SELECT cast(MAX(idflusso) as char(10)) INTO v_max FROM W9FLUSSI;
		SET v_sql = 'Insert into W9FLUSSI (IDFLUSSO, AREA, KEY01, KEY03, KEY04, TINVIO2, DATINV, AUTORE, XML, VERXML, CFSA, NOTEINVIO) select ROW_NUMBER() OVER() + ' ||  v_max || ',  V.* from (select distinct  2, KEY01, 901, KEY02+1, TINVIO2, DATINV, AUTORE, ''<void>'', VERXML, CFSA, ''Inviato con esito'' from w9Flussi F join W9DOCGARA D on D.codgara=F.key01 where key03=984 and TINVIO2=1) V';
		EXECUTE immediate v_sql;

		------------------------------------------------------------------------------------------------
		-------------------------------    RECUPERO DATI ESISTENTI   -----------------------------------
		------------------------------------------------------------------------------------------------
		
		-- nuova gestione chiave W9FLUSSI --
		SELECT cast(MAX(idflusso) as char(10)) INTO v_max FROM W9FLUSSI;
		SET v_sql = 'Insert into W_GENCHIAVI (TABELLA, CHIAVE) VALUES(''W9FLUSSI'',' ||  v_max || ')';
		EXECUTE immediate v_sql;
	
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
