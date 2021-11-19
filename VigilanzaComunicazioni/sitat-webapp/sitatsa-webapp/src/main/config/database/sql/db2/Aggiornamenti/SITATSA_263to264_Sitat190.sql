create or replace view Sitat190.V_DATI_DITTE_PARTECIPANTI as
select 
case when TIPOAGG=3  Then d.codimp ELSE 'A' || cast(a.CODGARA as char(10)) || '/' || substr(digits(a.codlott),length(digits(a.codlott)) -3 ) ||  digits(NUM_RAGG) END as ditta,
cast(d.CODGARA as char(10)) || '/' || substr(digits(d.codlott),length(digits(d.codlott)) -3 )  as lotto,
case when TIPOAGG=3 then 1 else 2 end as tipo,
i.nomest as ragsoc 
from 
SITATSA.W9IMPRESE a
join (select codgara,codlott,codimp from SITATSA.W9IMPRESE where TIPOAGG<>1 UNION select codgara,codlott,min(codimp) as codimp from SITATSA.W9IMPRESE where TIPOAGG=1 and RUOLO=1 group by codgara,codlott) d on d.CODGARA=a.CODGARA and d.CODLOTT=a.CODLOTT and d.CODIMP=a.CODIMP
join SITATSA.impr i on a.codimp=i.codimp;



create or replace  view Sitat190.V_DATI_IMPRESE_RAGGRUPPAMENTI as
select distinct 
'A' || cast(d.CODGARA as char(10)) || '/' || substr(digits(d.codlott),length(digits(d.codlott)) -3 ) ||  digits(d.NUM_RAGG) as cod_associazione,
i.codimp as cod_ditta,
case when i.codimp=RTI.codimp then '1' else '2' end as tipo_ruolo,
i.nomest as ragsoc,
i.cfimp as codfisc,
i.pivimp as piva,
case when i.nazimp=1 or i.nazimp is null then 'ITALIA' else 'ESTERO' end as nazione,
case d.TIPOAGG when 1 then 3 when 2 then 2 end as tipoAssociazione
from 
SITATSA.W9IMPRESE d join SITATSA.impr i on d.codimp=i.codimp and TIPOAGG<>3
join (select * from SITATSA.W9IMPRESE A WHERE (TIPOAGG=1 and RUOLO=1) or (TIPOAGG in (2,4) and NUM_IMPR=(select min(NUM_IMPR) from SITATSA.W9IMPRESE B WHERE A.CODGARA=B.CODGARA AND A.CODLOTT=B.CODLOTT AND A.NUM_RAGG=B.NUM_RAGG))) RTI on d.CODGARA=RTI.CODGARA AND d.CODLOTT=RTI.CODLOTT; 
