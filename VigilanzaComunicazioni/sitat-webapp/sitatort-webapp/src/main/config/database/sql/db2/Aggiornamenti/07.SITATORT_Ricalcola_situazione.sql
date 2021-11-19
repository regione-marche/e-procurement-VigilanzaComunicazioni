--#SET TERMINATOR ;

SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;


		-- Ricalcolo la situazione
		Update W9LOTT l set SITUAZIONE=null where situazione <=8;
		Update W9LOTT l set SITUAZIONE=8 where exists (select 1 from W9ESITO t join W9FASI f on t.CODGARA=f.CODGARA and t.CODLOTT=f.CODLOTT join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE=984 and l.CODGARA=t.CODGARA and l.CODLOTT=t.CODLOTT AND t.ESITO_PROCEDURA>1) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=5 where exists (select 1 from W9SOSP t join W9FASI f  on t.CODGARA=f.CODGARA and t.CODLOTT=f.CODLOTT join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE=6 and l.CODGARA=t.CODGARA and l.CODLOTT=t.CODLOTT AND t.DATA_VERB_RIPR IS NULL) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=7 where exists (select 1 from W9FASI f join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE in (5,11,985) and l.CODGARA=i.KEY01 and l.CODLOTT=i.KEY02 ) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=6 where exists (select 1 from W9FASI f join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE =4 and l.CODGARA=i.KEY01 and l.CODLOTT=i.KEY02) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=4 where exists (select 1 from W9FASI f join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE =3 and l.CODGARA=i.KEY01 and l.CODLOTT=i.KEY02) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=3 where exists (select 1 from W9FASI f join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE in (2,986) and l.CODGARA=i.KEY01 and l.CODLOTT=i.KEY02) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=2 where exists (select 1 from W9FASI f join W9FLUSSI i on f.CODGARA=i.KEY01 and f.CODLOTT=i.KEY02 where f.FASE_ESECUZIONE in (1,987,12) and l.CODGARA=i.KEY01 and l.CODLOTT=i.KEY02) and SITUAZIONE is null;
		Update W9LOTT l set SITUAZIONE=1 where SITUAZIONE is null;

		Update W9GARA g set SITUAZIONE=null where situazione <=8;
		Update W9GARA g set SITUAZIONE=8 where exists (select 1 from W9LOTT l where l.SITUAZIONE=8 and g.CODGARA=l.CODGARA) and not exists(select 1 from W9LOTT l where l.SITUAZIONE<>8 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=1 where exists (select 1 from W9LOTT l where l.SITUAZIONE=1 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=2 where exists (select 1 from W9LOTT l where l.SITUAZIONE=2 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=3 where exists (select 1 from W9LOTT l where l.SITUAZIONE=3 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=4 where exists (select 1 from W9LOTT l where l.SITUAZIONE=4 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=5 where exists (select 1 from W9LOTT l where l.SITUAZIONE=5 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=6 where exists (select 1 from W9LOTT l where l.SITUAZIONE=6 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;
		Update W9GARA g set SITUAZIONE=7 where exists (select 1 from W9LOTT l where l.SITUAZIONE=7 and g.CODGARA=l.CODGARA) and SITUAZIONE is null;

