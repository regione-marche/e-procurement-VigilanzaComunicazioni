------------------------------
-- AGGIORNAMENTO SITAT ORT (aggiornamento parte pubblica)


DROP view SITATORT.V_PUBB_GARA;

CREATE view SITATORT.V_PUBB_GARA
(CODGARA,OGGETTO,IDAVGARA,IMPDIS,IMPORTO_GARA,NLOTTI,TIPO_APP,CODEIN,CGARCIG,DGURI,DSCADE,
RUP,ID_SCELTA_CONTRAENTE,ID_MODO_GARA,TIPO_CONTRATTO,ID_SCELTA_CONTRAENTE_D,ID_MODO_GARA_D,TIPO_CONTRATTO_D,TIPO_APP_D,
SA,PROVINCIA_SA,FASEGARA,FASEGARA_D,DATAPUBB)
AS 
SELECT gl.*,tW3005.tab1desc,tW3007.tab1desc,tW3z05.tab2d2,tW3999.tab1desc,u.nomein,UPPER(tAgx15.tab3desc),
Case WHEN DSCADE - CURRENT DATE+1>0 THEN 1 WHEN DSCADE IS NULL THEN 0 ELSE 2 END,
Case WHEN DSCADE - CURRENT DATE+1>0 THEN 'IN CORSO' WHEN DSCADE IS NULL THEN 'NON DEFINITA' ELSE 'SCADUTA' END,
date(f.DATINV)
FROM 
(SELECT g.CODGARA,g.OGGETTO,IDAVGARA,IMPDIS,IMPORTO_GARA,NLOTTI,TIPO_APP,CODEIN,CGARCIG,date(DGURI) as DGURI,date(DSCADE) as DSCADE,g.RUP,
min(l.ID_SCELTA_CONTRAENTE) as ID_SCELTA_CONTRAENTE, min(ID_MODO_GARA) as ID_MODO_GARA, min (TIPO_CONTRATTO) as TIPO_CONTRATTO from sitatort.w9gara g left outer join  sitatort.w9lott l on g.codgara=l.codgara
group by g.CODGARA,g.CODEIN,g.OGGETTO,IDAVGARA,IMPDIS,IMPORTO_GARA,NLOTTI,TIPO_APP,CODEIN,CGARCIG,DGURI,DSCADE,g.RUP,IDEGOV) gl
join (select * from  SITATORT.W9FLUSSI WHERE key03=990 and tinvio2=1) f on gl.codgara=f.Key01
left outer join (select * from SITATORT.tab1 where tab1cod='W3005') tW3005 on gl.ID_SCELTA_CONTRAENTE=tW3005.tab1tip
left outer join (select * from SITATORT.tab1 where tab1cod='W3007') tW3007 on gl.ID_MODO_GARA=tW3007.tab1tip
left outer join (select * from SITATORT.tab2 where tab2cod='W3z05') tW3z05 on gl.TIPO_CONTRATTO=tW3z05.tab2tip
left outer join (select * from SITATORT.tab1 where tab1cod='W3999') tW3999 on gl.TIPO_APP=tW3999.tab1tip
join sitatort.uffint u on u.codein=gl.codein left outer join (select * from SITATORT.tab3 where tab3cod='Agx15') tAgx15 on u.PROEIN=tAgx15.tab3tip

UNION

select idbando + 1000000000,oggetto,'','',ImportoBaseGara,Lotticomplessivi,
CodContratto,'','',DataPubblicazioneGURI,DataScadenza,
'',CodProcedura,CodCriterio,CASE WHEN CodSettore=1 THEN 'L' WHEN CodSettore=2 THEN 'F' WHEN CodSettore=3 THEN 'S' ELSE 'M' END,
DexTipoProcedura,DexTipoCriterio,DexTipoSettore,DexTipoContratto,enti.Denominazione,prov.NomeProvincia,
Case WHEN DataScadenza - CURRENT DATE+1>0 THEN 1 ELSE 2 END,
Case WHEN DataScadenza - CURRENT DATE+1>0 THEN 'IN CORSO' ELSE 'SCADUTA' END,
DataIns
from SITATORT.bandi b
join SITATORT.Enti enti on b.IdStazApp=enti.IdEnte
left outer join SITATORT.ProvinceIstat prov on enti.Provincia=prov.CodProvincia
left outer join SITATORT.TipiContratto tcon on b.CodContratto=tcon.CodTipoContratto
left outer join SITATORT.TipiProceduraScelta_bandi tpro on b.CodProcedura=tpro.CodTipoProcedura
left outer join SITATORT.TipiCriterioAggiudicazione tcri on b.CodCriterio=tcri.CodTipoCriterio
left outer join SITATORT.TipiSettore tset on b.CodSettore=tset.CodTipoSettore

UNION
select idEsitoSenza + 2000000000,oggetto,'','',CAST(NULL as DECIMAL),Lotticomplessivi,
CodContratto,'','',CAST(NULL as DATE),CAST(NULL as DATE),
'',CodProcedura,0,CASE WHEN CodSettore=1 THEN 'L' WHEN CodSettore=2 THEN 'F' WHEN CodSettore=3 THEN 'S' ELSE 'M' END,
DexTipoProcedura,'',DexTipoSettore,DexTipoContratto,enti.Denominazione,prov.NomeProvincia,
0,'',CAST(NULL as DATE)
from SITATORT.EsitiSenzaBando b
join SITATORT.Enti enti on b.IdStazApp=enti.IdEnte
left outer join SITATORT.ProvinceIstat prov on enti.Provincia=prov.CodProvincia
left outer join SITATORT.TipiContratto tcon on b.CodContratto=tcon.CodTipoContratto
left outer join SITATORT.TipiProceduraScelta_esitisenzabando tpro on b.CodProcedura=tpro.CodTipoProcedura
left outer join SITATORT.TipiSettore tset on b.CodSettore=tset.CodTipoSettore;

GRANT SELECT ON TABLE SITATORT.V_PUBB_GARA TO USER SITATORT,PUBBLEG;


DROP view SITATORT.V_PUBB_PIATRI;
CREATE view SITATORT.V_PUBB_PIATRI
(CONTRI,CENINT,TIPROG,BRETRI,ANNTRI,FILE_ALLEGATO,NOMEFILE,SA,PROVINCIA_SA,TIPROG_D,RESPONSABILE,DATAPUBB,DATAULTAGG)
AS
SELECT
CONTRI,CENINT,TIPROG,BRETRI,ANNTRI,FILE_ALLEGATO,'Programma.pdf',u.NOMEIN,UPPER(tAgx15.tab3desc),tW9002.tab1desc,t.NOMTEC,f.DATAPUBB,f.DATAULTAGG
FROM SITATORT.PIATRI p
left outer join (select * from SITATORT.tab1 where tab1cod='W9002') tW9002 on TIPROG=tW9002.tab1tip
left outer join SITATORT.tecni t on t.codtec=p.RESPRO
join sitatort.uffint u on u.codein=p.cenint left outer join (select * from SITATORT.tab3 where tab3cod='Agx15') tAgx15 on u.PROEIN=tAgx15.tab3tip
left outer join (select key01,min(datinv) as DATAPUBB,max(datinv) as DATAULTAGG from sitatort.w9flussi where area=4 group by key01) f on f.key01=contri;

GRANT SELECT ON TABLE SITATORT.V_PUBB_PIATRI TO USER SITATORT,PUBBLEG;

