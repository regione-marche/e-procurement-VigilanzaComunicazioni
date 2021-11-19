------------------------------
-- AGGIORNAMENTO SITAT ORT	
------------------------------
-- GENE 1.4.39
-- GENEWEB 1.5.5
-- SITATORT 2.0.2
--------------------------


-- SITAT

Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',23,'D.Lgs. 163/2006, art. 221, c. 1, lett. j) (N.B. Solo Settori Speciali');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',24,'D.Lgs. 163/2006, art. 221, c. 1, lett. k) (N.B. Solo Settori Speciali');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',25,'Altre motivazioni');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',26,'D. Lgs. 163/2006, art.122, c.7-bis (N.B. Solo per lavori');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',27,'D. Lgs. 163/2006, art.122, c.8 (N.B. Solo per lavori');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',28,'D. Lgs. 163/2006, art. 99, c.5 / art. 108 c.6 ');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',29,'Ricorso al mercato elettronico');
Insert into SITATORT.TAB1 (TAB1COD,TAB1TIP,TAB1DESC) values ('W3006',30,'Adesione ad accordo quadro/convenzione');


UPDATE SITATORT.ELDAVER Set numver='2.0.2' where codapp='W9';