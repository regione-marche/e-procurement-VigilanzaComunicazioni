package it.eldasoft.commons;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eldasoft.commons.beans.ControlloBean;
import it.eldasoft.commons.beans.EntitaCampiChiaveBean;
import it.eldasoft.commons.beans.MessaggioControlloBean;
import it.eldasoft.commons.beans.ValoreCampiChiaveBean;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.metadata.cache.DizionarioCampi;
import it.eldasoft.utils.metadata.cache.DizionarioTabelle;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.metadata.domain.Tabella;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.dao.ibatis.SqlMapTabControlliDao;

/**
 * Manager per la gestione dei controlli su campi in fase di esportazione dei dati.
 * 
 * @author Luca.Giacomazzo
 */
public class TabControlliManager {
  
  static Logger logger = Logger.getLogger(TabControlliManager.class);
  
  private SqlMapTabControlliDao sqlMapTabControlliDao; 
  
  private SqlManager sqlManager; 
  
  public void setSqlMapTabControlliDao(SqlMapTabControlliDao sqlMapTabControlliDao) {
    this.sqlMapTabControlliDao = sqlMapTabControlliDao;
  }
  
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  /**
   * Metodo per la validazione di dei dati dell'anagrafica gara/lotto e di tutte le fasi esistenti per un lotto.
   * (usato da 
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param listaMessaggiDiControllo
   * @throws SQLException
   */
  public void eseguiControlliLotto(Long codiceGara, Long codiceLotto, String codiceFunzione, 
      List<MessaggioControlloBean> listaMessaggiDiControllo) throws SQLException {
   
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliLotto: inizio metodo");
    }

    HashMap<String, List<ValoreCampiChiaveBean>> hm = new HashMap<String, List<ValoreCampiChiaveBean>>();
    
    List<?> listaFasiDaEsportare = this.sqlManager.getListHashMap(
        "select f.FASE_ESECUZIONE from W9FASI f, TAB1 t where f.CODGARA=? and f.CODLOTT=? and f.FASE_ESECUZIONE = t.TAB1TIP " +
           "and TAB1COD = 'W3020' and TAB1TIP not in (999,992,991,990,989,983,13,901,984,998,997,996,995,994,988,102) order by tab1nord asc",
        new Object[] { codiceGara, codiceLotto } );
    
    List<String> tabelleDaControllare = new ArrayList<String>();
    
    this.getTabelleDaControllareAnagraficaGara(codiceGara, hm, tabelleDaControllare);
    this.getTabelleDaControllareAnagraficaLotto(codiceGara, codiceLotto, hm, tabelleDaControllare);

    long faseEsecuzionePrecedente = Long.MIN_VALUE;
    if (listaFasiDaEsportare != null && listaFasiDaEsportare.size() > 0) {
      for (int i=0; i < listaFasiDaEsportare.size(); i++) {
        HashMap<?, ?> datiFasiDaEsportare = (HashMap<?, ?>) listaFasiDaEsportare.get(i);
        Long faseEsecuzione = (Long) ((JdbcParametro) datiFasiDaEsportare.get("FASE_ESECUZIONE")).getValue();
        
        if (faseEsecuzione.longValue() != faseEsecuzionePrecedente) {
          faseEsecuzionePrecedente = faseEsecuzione.longValue();
          
          switch (faseEsecuzione.intValue()) {
          case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
            tabelleDaControllare.add("W9IMPRESE");
            List<?> listaNumeroImprese = this.sqlManager.getListHashMap(
                "select NUM_IMPR from W9IMPRESE where CODGARA=? and CODLOTT=? order by NUM_IMPR asc, num_ragg asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroImprese.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Imprese = new ArrayList<ValoreCampiChiaveBean>(listaNumeroImprese.size());
              for (int j=0; j < listaNumeroImprese.size(); j++) {
                HashMap<?, ?> hmNumeroImpresa = (HashMap<?, ?>) listaNumeroImprese.get(j);
                Long numeroImpresa = (Long) ((JdbcParametro) hmNumeroImpresa.get("NUM_IMPR")).getValue();
                listaChiaviW9Imprese.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto,numeroImpresa));
              }
              hm.put("W9IMPRESE", listaChiaviW9Imprese);
            }
            break;
  
          case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
          case CostantiW9.ADESIONE_ACCORDO_QUADRO:
          case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
            tabelleDaControllare.add("W9APPA");
            List<ValoreCampiChiaveBean> listaChiaviW9Appa = new ArrayList<ValoreCampiChiaveBean>(1);
            listaChiaviW9Appa.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1)));
            hm.put("W9APPA", listaChiaviW9Appa);
            
            tabelleDaControllare.add("W9AGGI");
            List<?> listaNumeroAggiudicatarie = this.sqlManager.getListHashMap(
                "select NUM_AGGI from W9AGGI where CODGARA=? and CODLOTT=? AND NUM_APPA=1 order by NUM_AGGI asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroAggiudicatarie.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Aggi = new ArrayList<ValoreCampiChiaveBean>(listaNumeroAggiudicatarie.size());
              for (int j=0; j < listaNumeroAggiudicatarie.size(); j++) {
                HashMap<?, ?> numeriAggiudicatarie = (HashMap<?, ?>) listaNumeroAggiudicatarie.get(j);
                Long numeroAggiudicataria = (Long) ((JdbcParametro) numeriAggiudicatarie.get("NUM_AGGI")).getValue();
                listaChiaviW9Aggi.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroAggiudicataria));
              }
              hm.put("W9AGGI", listaChiaviW9Aggi);
            }
            
            tabelleDaControllare.add("W9FINA");
            List<?> listaNumeroFinanziamenti = this.sqlManager.getListHashMap(
                "select NUM_FINA from W9FINA where CODGARA=? and CODLOTT=? AND NUM_APPA=1 order by NUM_FINA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroFinanziamenti.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Fina = new ArrayList<ValoreCampiChiaveBean>(listaNumeroFinanziamenti.size());
              for (int j=0; j < listaNumeroFinanziamenti.size(); j++) {
                HashMap<?, ?> numeriFinanziamenti = (HashMap<?, ?>) listaNumeroFinanziamenti.get(j);
                Long numeroFinanziamento = (Long) ((JdbcParametro) numeriFinanziamenti.get("NUM_FINA")).getValue();
                listaChiaviW9Fina.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroFinanziamento));
              }
              hm.put("W9AGGI", listaChiaviW9Fina);
            }
            
            tabelleDaControllare.add("W9REQU");
            List<?> listaNumeroRequisiti = this.sqlManager.getListHashMap(
                "select NUM_REQU from W9REQU where CODGARA=? and CODLOTT=? AND NUM_APPA=1 order by NUM_REQU asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroRequisiti.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Requ = new ArrayList<ValoreCampiChiaveBean>(listaNumeroRequisiti.size());
              for (int j=0; j < listaNumeroRequisiti.size(); j++) {
                HashMap<?, ?> numeriFinanziamenti = (HashMap<?, ?>) listaNumeroFinanziamenti.get(j);
                Long numeroRequisito = (Long) ((JdbcParametro) numeriFinanziamenti.get("NUM_REQU")).getValue();
                listaChiaviW9Requ.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroRequisito));
              }
              hm.put("W9REQU", listaChiaviW9Requ);
            }
            
            tabelleDaControllare.add("W9INCA-APPA");
            List<?> listaNumeroIncarichiProf = this.sqlManager.getListHashMap(
                "select NUM_INCA,SEZIONE from W9INCA where CODGARA=? and CODLOTT=? AND NUM=1 and SEZIONE in ('RA','PA','RS','RE','RQ') order by NUM_INCA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroIncarichiProf.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9IncaProf = new ArrayList<ValoreCampiChiaveBean>(listaNumeroIncarichiProf.size());
              for (int j=0; j < listaNumeroIncarichiProf.size(); j++) {
                HashMap<?, ?> numeriIncaricoProf = (HashMap<?, ?>) listaNumeroIncarichiProf.get(j);
                Long numeroIncaricoProf = (Long) ((JdbcParametro) numeriIncaricoProf.get("NUM_INCA")).getValue();
                String sezioneIncaricoProf = (String) ((JdbcParametro) numeriIncaricoProf.get("SEZIONE")).getValue();
                listaChiaviW9IncaProf.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroIncaricoProf,sezioneIncaricoProf));
              }
              hm.put("W9INCA-APPA", listaChiaviW9IncaProf);
            }
            break;
            
          case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
          case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:
            tabelleDaControllare.add("W9INIZ");
            List<ValoreCampiChiaveBean> listaChiaviW9Iniz = new ArrayList<ValoreCampiChiaveBean>(1);
            listaChiaviW9Iniz.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1)));
            hm.put("W9INIZ", listaChiaviW9Iniz);
            
            tabelleDaControllare.add("W9INCA-INIZ");
            List<?> listaNumeroIncarichiProfIniz = this.sqlManager.getListHashMap(
                "select NUM_INCA,SEZIONE from W9INCA where CODGARA=? and CODLOTT=? AND NUM=1 and SEZIONE='IN' order by NUM_INCA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroIncarichiProfIniz.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9IncaIniz = new ArrayList<ValoreCampiChiaveBean>(listaNumeroIncarichiProfIniz.size());
              for (int j=0; j < listaNumeroIncarichiProfIniz.size(); j++) {
                HashMap<?, ?> numeriIncarichiProfIniz = (HashMap<?, ?>) listaNumeroIncarichiProfIniz.get(j);
                Long numeroIncaricoProf = (Long) ((JdbcParametro) numeriIncarichiProfIniz.get("NUM_INCA")).getValue();
                String sezioneIncaricoProf = (String) ((JdbcParametro) numeriIncarichiProfIniz.get("SEZIONE")).getValue();
                listaChiaviW9IncaIniz.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroIncaricoProf,sezioneIncaricoProf));
              }
              hm.put("W9INCA-INIZ", listaChiaviW9IncaIniz);
            }
            
            tabelleDaControllare.add("W9MISSIC");
            List<?> listaNumeroMisureSicurezza = this.sqlManager.getListHashMap(
                "select NUM_MISS from W9MISSIC where CODGARA=? and CODLOTT=? order by NUM_MISS asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroMisureSicurezza.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Missic = new ArrayList<ValoreCampiChiaveBean>(listaNumeroMisureSicurezza.size());
              for (int j=0; j < listaNumeroMisureSicurezza.size(); j++) {
                HashMap<?, ?> numeriMisuraSicurezza = (HashMap<?, ?>) listaNumeroMisureSicurezza.get(j);
                Long numeroMisuraSicurezza = (Long) ((JdbcParametro) numeriMisuraSicurezza.get("NUM_MISS")).getValue();
                listaChiaviW9Missic.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto,numeroMisuraSicurezza));
              }
              hm.put("W9MISSIC", listaChiaviW9Missic);
            }
            
            tabelleDaControllare.add("W9SIC");
            List<?> listaNumeroSicurezze = this.sqlManager.getListHashMap(
                "select NUM_SIC from W9SIC where CODGARA=? and CODLOTT=? order by NUM_SIC asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroSicurezze.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Missic = new ArrayList<ValoreCampiChiaveBean>(listaNumeroSicurezze.size());
              for (int j=0; j < listaNumeroSicurezze.size(); j++) {
                HashMap<?, ?> numeriSicurezza = (HashMap<?, ?>) listaNumeroSicurezze.get(j);
                Long numeroSicurezza = (Long) ((JdbcParametro) numeriSicurezza.get("NUM_SIC")).getValue();
                listaChiaviW9Missic.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto,numeroSicurezza));
              }
              hm.put("W9SIC", listaChiaviW9Missic);
            }
            break;
  
          case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
          //case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
            tabelleDaControllare.add("W9AVAN");
            List<?> listaNumeroSAL = this.sqlManager.getListHashMap(
                "select NUM_AVAN from W9AVAN where CODGARA=? and CODLOTT=? order by NUM_AVAN asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroSAL.size() > 0) {
              Long[] arrayNumeroSAL = new Long[listaNumeroSAL.size()];
              List<ValoreCampiChiaveBean> listaChiaviW9Avan = new ArrayList<ValoreCampiChiaveBean>(listaNumeroSAL.size());
              for (int j=0; j < listaNumeroSAL.size(); j++) {
                HashMap<?, ?> numeriSAL = (HashMap<?, ?>) listaNumeroSAL.get(j);
                Long numeroSAL = (Long) ((JdbcParametro) numeriSAL.get("NUM_AVAN")).getValue();
                arrayNumeroSAL[j] = numeroSAL;
                listaChiaviW9Avan.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroSAL));
              }
              hm.put("W9AVAN", listaChiaviW9Avan);
            }
            break;
            
          case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
          case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
            tabelleDaControllare.add("W9CONC");
            List<ValoreCampiChiaveBean> listaChiaviW9Conc = new ArrayList<ValoreCampiChiaveBean>(1);
            listaChiaviW9Conc.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1)));
            hm.put("W9CONC", listaChiaviW9Conc);
            break;
            
          case CostantiW9.COLLAUDO_CONTRATTO:
            tabelleDaControllare.add("W9COLL");
            List<ValoreCampiChiaveBean> listaChiaviW9Coll = new ArrayList<ValoreCampiChiaveBean>(1);
            listaChiaviW9Coll.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1)));
            hm.put("W9COLL", listaChiaviW9Coll);
            
            tabelleDaControllare.add("W9INCA-COLL");
            List<?> listaNumeroIncarichiProfColl = this.sqlManager.getListHashMap(
                "select NUM_INCA,SEZIONE from W9INCA where CODGARA=? and CODLOTT=? AND NUM=1 and SEZIONE='CO' order by NUM_INCA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroIncarichiProfColl.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9IncaColl = new ArrayList<ValoreCampiChiaveBean>(listaNumeroIncarichiProfColl.size());
              for (int j=0; j < listaNumeroIncarichiProfColl.size(); j++) {
                HashMap<?, ?> numeriIncarichiProfColl = (HashMap<?, ?>) listaNumeroIncarichiProfColl.get(j);
                Long numeroIncaricoProf = (Long) ((JdbcParametro) numeriIncarichiProfColl.get("NUM_INCA")).getValue();
                String sezioneIncaricoProf = (String) ((JdbcParametro) numeriIncarichiProfColl.get("SEZIONE")).getValue();
                listaChiaviW9IncaColl.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, Long.valueOf(1),numeroIncaricoProf,sezioneIncaricoProf));
              }
              hm.put("W9INCA-COLL", listaChiaviW9IncaColl);
            }
            
            break;
            
          case CostantiW9.SOSPENSIONE_CONTRATTO:
            tabelleDaControllare.add("W9SOSP");
            List<?> listaNumeroSospensioni = this.sqlManager.getListHashMap(
                "select NUM_SOSP from W9SOSP where CODGARA=? and CODLOTT=? order by NUM_SOSP asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroSospensioni.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Sosp = new ArrayList<ValoreCampiChiaveBean>(listaNumeroSospensioni.size());
              for (int j=0; j < listaNumeroSospensioni.size(); j++) {
                HashMap<?, ?> numeroSospensioni = (HashMap<?, ?>) listaNumeroSospensioni.get(j);
                Long numeroSospensione = (Long) ((JdbcParametro) numeroSospensioni.get("NUM_SOSP")).getValue();
                listaChiaviW9Sosp.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroSospensione));
              }
              hm.put("W9SOSP", listaChiaviW9Sosp);
            }
            break;
            
          case CostantiW9.VARIANTE_CONTRATTO:
            tabelleDaControllare.add("W9VARI");
            List<?> listaNumeroVarianti = this.sqlManager.getListHashMap(
                "select NUM_VARI from W9VARI where CODGARA=? and CODLOTT=? order by NUM_VARI asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroVarianti.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9VARI = new ArrayList<ValoreCampiChiaveBean>(listaNumeroVarianti.size());
              for (int j=0; j < listaNumeroVarianti.size(); j++) {
                HashMap<?, ?> numeriVarianti = (HashMap<?, ?>) listaNumeroVarianti.get(j);
                Long numeroVariante = (Long) ((JdbcParametro) numeriVarianti.get("NUM_VARI")).getValue();
                listaChiaviW9VARI.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroVariante));
              }
              hm.put("W9VARI", listaChiaviW9VARI);
            }
            break;
            
          case CostantiW9.ACCORDO_BONARIO:
            tabelleDaControllare.add("W9ACCO");
            List<?> listaNumeroAccordiQuadro = this.sqlManager.getListHashMap(
                "select NUM_ACCO from W9ACCO where CODGARA=? and CODLOTT=? order by NUM_ACCO asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroAccordiQuadro.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Acco = new ArrayList<ValoreCampiChiaveBean>(listaNumeroAccordiQuadro.size());
              for (int j=0; j < listaNumeroAccordiQuadro.size(); j++) {
                HashMap<?, ?> numeriVarianti = (HashMap<?, ?>) listaNumeroAccordiQuadro.get(j);
                Long numeroAccordoQuadro = (Long) ((JdbcParametro) numeriVarianti.get("NUM_ACCO")).getValue();
                listaChiaviW9Acco.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroAccordoQuadro));
              }
              hm.put("W9ACCO", listaChiaviW9Acco);
            }
            break;
            
          case CostantiW9.SUBAPPALTO:
            tabelleDaControllare.add("W9SUBA");
            List<?> listaNumeroSubappalti = this.sqlManager.getListHashMap(
                "select NUM_SUBA from W9SUBA where CODGARA=? and CODLOTT=? order by NUM_SUBA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroSubappalti.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Suba = new ArrayList<ValoreCampiChiaveBean>(listaNumeroSubappalti.size());
              for (int j=0; j < listaNumeroSubappalti.size(); j++) {
                HashMap<?, ?> numeriSubappalti = (HashMap<?, ?>) listaNumeroSubappalti.get(j);
                Long numeroSubappalto = (Long) ((JdbcParametro) numeriSubappalti.get("NUM_SUBA")).getValue();
                listaChiaviW9Suba.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroSubappalto));
              }
              hm.put("W9SUBA", listaChiaviW9Suba);
            }
            break;
            
          case CostantiW9.ISTANZA_RECESSO:
            tabelleDaControllare.add("W9RITA");
            List<?> listaNumeroRitardi = this.sqlManager.getListHashMap(
                "select NUM_RITA from W9RITA where CODGARA=? and CODLOTT=? order by NUM_RITA asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroRitardi.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Rita = new ArrayList<ValoreCampiChiaveBean>(listaNumeroRitardi.size());
              for (int j=0; j < listaNumeroRitardi.size(); j++) {
                HashMap<?, ?> numeriRitardi = (HashMap<?, ?>) listaNumeroRitardi.get(j);
                Long numeroRitardo = (Long) ((JdbcParametro) numeriRitardi.get("NUM_SUBA")).getValue();
                listaChiaviW9Rita.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto, numeroRitardo));
              }
              hm.put("W9RITA", listaChiaviW9Rita);
            }
            break;
          case CostantiW9.STIPULA_ACCORDO_QUADRO:
            tabelleDaControllare.add("W9INIZ");
            List<ValoreCampiChiaveBean> listaChiaviW9Iniz1 = new ArrayList<ValoreCampiChiaveBean>(1);
            listaChiaviW9Iniz1.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1)));
            hm.put("W9INIZ", listaChiaviW9Iniz1);
            
            tabelleDaControllare.add("W9PUES");
            List<?> listaNumeroPubblicita = this.sqlManager.getListHashMap(
                "select NUM_PUES from W9PUES where CODGARA=? and CODLOTT=? AND NUM_INIZ=1 order by NUM_PUES asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroPubblicita.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Pues = new ArrayList<ValoreCampiChiaveBean>(listaNumeroPubblicita.size());
              for (int j=0; j < listaNumeroPubblicita.size(); j++) {
                HashMap<?, ?> numeriPubblicita = (HashMap<?, ?>) listaNumeroPubblicita.get(j);
                Long numeroPubblicita = (Long) ((JdbcParametro) numeriPubblicita.get("NUM_PUES")).getValue();
                listaChiaviW9Pues.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto, Long.valueOf(1),numeroPubblicita));
              }
              hm.put("W9PUES", listaChiaviW9Pues);
            }
            
            tabelleDaControllare.add("W9SIC");
            List<?> listaNumeroSicurezze1 = this.sqlManager.getListHashMap(
                "select NUM_SIC from W9SIC where CODGARA=? and CODLOTT=? NUM_SIC asc",
                    new Object[] { codiceGara, codiceLotto });
            if (listaNumeroSicurezze1.size() > 0) {
              List<ValoreCampiChiaveBean> listaChiaviW9Sic = new ArrayList<ValoreCampiChiaveBean>(listaNumeroSicurezze1.size());
              for (int j=0; j < listaNumeroSicurezze1.size(); j++) {
                HashMap<?, ?> numeriSicurezza = (HashMap<?, ?>) listaNumeroSicurezze1.get(j);
                Long numeroSicurezza = (Long) ((JdbcParametro) numeriSicurezza.get("NUM_SIC")).getValue();
                listaChiaviW9Sic.add(new ValoreCampiChiaveBean(codiceGara,codiceLotto,numeroSicurezza));
              }
              hm.put("W9SIC", listaChiaviW9Sic);
            }
            break;
          default:
            break;
          }
        } 
      }
    }
    
    if (tabelleDaControllare.size() > 0) {
      for (int i=0; i < tabelleDaControllare.size(); i++) {
        String entitaDaControllare = tabelleDaControllare.get(i);
        if (hm.containsKey(entitaDaControllare)) {
          List<ValoreCampiChiaveBean> listaValoreCampiChiave = hm.get(entitaDaControllare);
          for (int j=0; j < listaValoreCampiChiave.size(); j++) {
            this.eseguiControlliEntita("W9", entitaDaControllare, codiceFunzione,
                listaValoreCampiChiave.get(j), listaMessaggiDiControllo);
          }
        }
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliLotto: fine metodo");
    }
  }

  /**
   * Aggiunge all'oggetto <i>tabelleDaControllare</i> le tabelle da controllare per validare
   * l'anagrafica di gara.
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param hm
   * @param tabelleDaControllare
   */
  private void getTabelleDaControllareAnagraficaGara(Long codiceGara,
      HashMap<String, List<ValoreCampiChiaveBean>> hm, List<String> tabelleDaControllare) {
    
    tabelleDaControllare.add("W9GARA");
    List<ValoreCampiChiaveBean> listaChiaviW9Gara = new ArrayList<ValoreCampiChiaveBean>(1);
    listaChiaviW9Gara.add(new ValoreCampiChiaveBean(codiceGara));
    hm.put("W9GARA", listaChiaviW9Gara);
  }

  /**
   * Aggiunge all'oggetto <i>tabelleDaControllare</i> le tabelle da controllare per validare
   * l'anagrafica del lotto.
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param hm
   * @param tabelleDaControllare
   */
  private void getTabelleDaControllareAnagraficaLotto(Long codiceGara,
      Long codiceLotto, HashMap<String, List<ValoreCampiChiaveBean>> hm, List<String> tabelleDaControllare) {

    tabelleDaControllare.add("W9LOTT");
    List<ValoreCampiChiaveBean> listaChiaviW9Lott = new ArrayList<ValoreCampiChiaveBean>(1);
    listaChiaviW9Lott.add(new ValoreCampiChiaveBean(codiceGara, codiceLotto));
    hm.put("W9LOTT", listaChiaviW9Lott);
    tabelleDaControllare.add("W9COND");
    tabelleDaControllare.add("W9APPAFORN");
    tabelleDaControllare.add("W9APPALAV");
    tabelleDaControllare.add("W9LOTTCATE");
    tabelleDaControllare.add("W9CPV");
  }
  
  /**
   * Controlli di una entita'
   * 
   * @param codiceApplicazione Codice applicazione
   * @param entita Nome dell'entita' da controllare
   * @param valoreCampoChiave1 Valore del primo campo chiave dell'entita'
   * @param valoreCampoChiave2 Valore del secondo campo chiave dell'entita'
   */
  public void eseguiControlliEntita(String codiceApplicazione, String entita, String codiceFunzione, 
      ValoreCampiChiaveBean valoreCampiChiave, List<MessaggioControlloBean> listaMessaggiControllo)
          throws SQLException {
    this.eseguiControlliEntita(codiceApplicazione, entita, codiceFunzione, 
        valoreCampiChiave.getValoreCampoChiave1(), valoreCampiChiave.getValoreCampoChiave2(), 
        valoreCampiChiave.getValoreCampoChiave3(), valoreCampiChiave.getValoreCampoChiave4(),
        valoreCampiChiave.getValoreCampoChiave5(), listaMessaggiControllo);
  }
  
  /**
   * Controlli di una entita' con chiave costituita da quattro campi
   * 
   * @param codiceApplicazione Codice applicazione
   * @param entita Nome dell'entita' da controllare
   * @param valoreCampoChiave1 Valore del primo campo chiave dell'entita'
   * @param valoreCampoChiave2 Valore del secondo campo chiave dell'entita'
   * @param valoreCampoChiave3 Valore del terzo campo chiave dell'entita'
   * @param valoreCampoChiave4 Valore del quarto campo chiave dell'entita'
   * @throws SQLException 
   */
  private void eseguiControlliEntita(String codiceApplicazione, String entita, String codiceFunzione,
      Object valoreCampoChiave1, Object valoreCampoChiave2, Object valoreCampoChiave3,
      Object valoreCampoChiave4, String valoreCampoChiave5, List<MessaggioControlloBean> listaMessaggiControllo)
          throws SQLException {

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntita: inizio metodo");
    }
    
    if ("W9".equals(codiceApplicazione)) {
      this.eseguiControlliEntitaW9(entita, codiceFunzione, valoreCampoChiave1, valoreCampoChiave2,
          valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5, listaMessaggiControllo);
    }
    
    if ("PT".equals(codiceApplicazione)) {
      this.eseguiControlliEntitaPT(entita, codiceFunzione, valoreCampoChiave1, valoreCampoChiave2,
          valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5, listaMessaggiControllo);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntita: fine metodo");
    }
    
  }
  
  /**
   * Controlli di una entita' con codice applicazione W9 e con chiave costituita da cinque campi
   * 
   * @param codiceApplicazione Codice applicazione
   * @param entita Nome dell'entita' da controllare
   * @param valoreCampoChiave1 Valore del primo campo chiave dell'entita'
   * @param valoreCampoChiave2 Valore del secondo campo chiave dell'entita'
   * @param valoreCampoChiave3 Valore del terzo campo chiave dell'entita'
   * @param valoreCampoChiave4 Valore del quarto campo chiave dell'entita'
   * @param valoreCampoChiave5 Valore del quinto campo chiave dell'entita'
   * @throws SQLException 
   */
  private void eseguiControlliEntitaW9(String entita, String codiceFunzione, Object valoreCampoChiave1, 
      Object valoreCampoChiave2, Object valoreCampoChiave3, Object valoreCampoChiave4, String valoreCampoChiave5,
          List<MessaggioControlloBean> listaMessaggiControllo) throws SQLException {

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntitaW9: inizio metodo");
    }
    
    String codiceApplicazione = "W9";
    
    String entitaLoc = null;
    //String subLoc = null;
    if (StringUtils.contains(entita, "-")) {
      String[] tmp = entita.split("-");
      entitaLoc = tmp[0];
      //subLoc = tmp[1];
    } else {
      entitaLoc = new String(entita);
    }
    
    // Estrazione dei controlli per l'entita' indicata
    List<ControlloBean> listaContr = this.sqlMapTabControlliDao.getListaControlli(
        codiceApplicazione, entitaLoc, codiceFunzione);

    List<MessaggioControlloBean> listaLocaleMessaggiControllo = new ArrayList<MessaggioControlloBean>();
    if (listaContr != null && listaContr.size() > 0) {
      for (int i = 0; i < listaContr.size(); i++) {
        ControlloBean controllo = listaContr.get(i);
        if ("T".equals(controllo.getTipo())) {
          DizionarioTabelle dizTabelle = DizionarioTabelle.getInstance();
          Tabella tabella = dizTabelle.getDaNomeTabella(entitaLoc);
          List<Campo> listaCampiChiave = tabella.getCampiKey();
          switch (listaCampiChiave.size()) {
          case 1:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave1, listaLocaleMessaggiControllo, controllo);
            break;
          case 2:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave2, listaLocaleMessaggiControllo, controllo);
            break;
          case 3:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave3, listaLocaleMessaggiControllo, controllo);
            break;
          case 4:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave4, listaLocaleMessaggiControllo, controllo);
            break;
          case 5:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave5, listaLocaleMessaggiControllo, controllo);
            break;
          }
        } else {
          if (!this.isControlloVerificato(controllo, valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5)) {
            MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), controllo.getSezione(),
                this.getDescrizioneCampo(entitaLoc, controllo.getTitolo()), controllo.getMessaggio());
            listaLocaleMessaggiControllo.add(msgControllo);
          }
        }
      }
    }
    
    if (entitaLoc.equals("W9SUBA")) {
      List<?> listaDatiSubappalto = this.sqlManager.getListHashMap(
          "select CODIMP_AGGIUDICATRICE, CODIMP from W9SUBA where CODGARA=? and CODLOTT=? and NUM_SUBA=?", 
              new Object[] { valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3 } );
      if (listaDatiSubappalto != null && listaDatiSubappalto.size() > 0) {
        for (int i = 0; i < listaDatiSubappalto.size(); i++) {
          HashMap<?,?> hmDatiSubappalto = (HashMap<?,?>) listaDatiSubappalto.get(i);
          String codiceImpresaAggiudicatrice = ((JdbcParametro) 
              hmDatiSubappalto.get("CODIMP_AGGIUDICATRICE")).getStringValue();
          String codiceImpresa = ((JdbcParametro) hmDatiSubappalto.get("CODIMP")).getStringValue();
          if (StringUtils.isNotEmpty(codiceImpresaAggiudicatrice)) {
            this.validazioneImpresa(codiceImpresaAggiudicatrice, false, "Impresa aggiudicatrice",
                listaLocaleMessaggiControllo, codiceFunzione);
          }
          if (StringUtils.isNotEmpty(codiceImpresa)) {
            this.validazioneImpresa(codiceImpresa, false, "Impresa subappaltatrice",
                listaLocaleMessaggiControllo, codiceFunzione);
          }
        }
      }
    }

    if (entitaLoc.equals("W9IMPRESE")) {
      List<?> listaChiaviW9Imprese = this.sqlManager.getListHashMap(
          "select NUM_RAGG, CODIMP from W9IMPRESE where CODGARA=? and CODLOTT=? and NUM_IMPR=? order by NUM_RAGG asc", 
            new Object[] { valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3 });
      if (listaChiaviW9Imprese != null && listaChiaviW9Imprese.size() > 0) {
        for (int i=0; i < listaChiaviW9Imprese.size(); i++) {
          HashMap<?,?> hmDatiImprese = (HashMap<?,?>) listaChiaviW9Imprese.get(i);
          String codiceImpresa = ((JdbcParametro) hmDatiImprese.get("CODIMP")).getStringValue();
          String numRaggruppamento = ((JdbcParametro) 
              hmDatiImprese.get("NUM_RAGG")).getStringValue();

          if (StringUtils.isEmpty(numRaggruppamento)) {
            this.validazioneImpresa(codiceImpresa, false, "Impresa - Raggruppamento",
                listaLocaleMessaggiControllo, codiceFunzione);
          } else {
            this.validazioneImpresa(codiceImpresa, false, "Impresa - Raggruppamento " + numRaggruppamento,
                listaLocaleMessaggiControllo, codiceFunzione);
          }
        }
      }
    }
    
    if (entitaLoc.equals("W9AGGI")) {
      List<?> listaChiaviW9Aggiudicatarie = this.sqlManager.getListHashMap(
          "select CODIMP,CODIMP_AUSILIARIA from W9AGGI where CODGARA=? and CODLOTT=? and NUM_APPA=1 and NUM_AGGI=?", 
            new Object[] { valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3 });
      
      if (listaChiaviW9Aggiudicatarie != null && listaChiaviW9Aggiudicatarie.size() > 0) {
        for (int i=0; i < listaChiaviW9Aggiudicatarie.size(); i++) {
          HashMap<?,?> hmDatiAggiudicatarie = (HashMap<?,?>) listaChiaviW9Aggiudicatarie.get(i);
          String codiceImpresa = ((JdbcParametro) hmDatiAggiudicatarie.get("CODIMP")).getStringValue();
          String codiceImpresaAusiliaria = ((JdbcParametro) hmDatiAggiudicatarie.get("CODIMP_AUSILIARIA")).getStringValue();

          this.validazioneImpresa(codiceImpresa, true, "Impresa",
              listaLocaleMessaggiControllo, codiceFunzione);
          if (StringUtils.isNotEmpty(codiceImpresaAusiliaria)) {
            this.validazioneImpresa(codiceImpresaAusiliaria, true, "Impresa ausiliaria",
                listaLocaleMessaggiControllo, codiceFunzione);
          }
        }
      }
    }
    
    if (entitaLoc.equals("W9INCA")) {
      List<?> listaChiaviIncarichiProfessionali = this.sqlManager.getListHashMap(
          "select CODTEC from W9INCA where CODGARA=? and CODLOTT=? and NUM=? and NUM_INCA=? and SEZIONE=?", 
            new Object[] { valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5 });
      if (listaChiaviIncarichiProfessionali != null && listaChiaviIncarichiProfessionali.size() > 0) {
        for (Iterator<?> iterator = listaChiaviIncarichiProfessionali.iterator(); iterator.hasNext();) {
          HashMap<?,?> hmDatiIncarichiProf = (HashMap <?,?>) iterator.next();
          
          String codiceTecnico = ((JdbcParametro) hmDatiIncarichiProf.get("CODTEC")).getStringValue();
          this.validazioneTecnico(codiceTecnico, listaLocaleMessaggiControllo, codiceFunzione);
        }
      }
    }

    // Aggiunta dei messaggi di controllo locali alla lista che contiene tutti i messaggi.
    // Nell'aggiungere i messaggi si evita di aggiungere due messaggi consecutivi di tipo T (titoli)
    if (listaLocaleMessaggiControllo.size() > 0) {
      this.aggiungiMessaggiLocali(listaMessaggiControllo, listaLocaleMessaggiControllo);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntitaW9: fine metodo");
    }
  }

  /**
   * Controlli di una entita' con codice applicazione PT e con chiave costituita da cinque campi
   * 
   * @param codiceApplicazione Codice applicazione
   * @param entita Nome dell'entita' da controllare
   * @param valoreCampoChiave1 Valore del primo campo chiave dell'entita'
   * @param valoreCampoChiave2 Valore del secondo campo chiave dell'entita'
   * @param valoreCampoChiave3 Valore del terzo campo chiave dell'entita'
   * @param valoreCampoChiave4 Valore del quarto campo chiave dell'entita'
   * @param valoreCampoChiave5 Valore del quinto campo chiave dell'entita'
   * @throws SQLException 
   */
  private void eseguiControlliEntitaPT(String entita, String codiceFunzione, Object valoreCampoChiave1, 
      Object valoreCampoChiave2, Object valoreCampoChiave3, Object valoreCampoChiave4, String valoreCampoChiave5,
          List<MessaggioControlloBean> listaMessaggiControllo) throws SQLException {

    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntitaPT: inizio metodo");
    }
    
    String codiceApplicazione = "PT";
    
    // Estrazione dei controlli per l'entita' indicata
    List<ControlloBean> listaContr = this.sqlMapTabControlliDao.getListaControlli(
        codiceApplicazione, entita, codiceFunzione);

    List<MessaggioControlloBean> listaLocaleMessaggiControllo = new ArrayList<MessaggioControlloBean>();
    if (listaContr != null && listaContr.size() > 0) {
      for (int i = 0; i < listaContr.size(); i++) {
        ControlloBean controllo = listaContr.get(i);
        if ("T".equals(controllo.getTipo())) {
          DizionarioTabelle dizTabelle = DizionarioTabelle.getInstance();
          Tabella tabella = dizTabelle.getDaNomeTabella(entita);
          List<Campo> listaCampiChiave = tabella.getCampiKey();
          
          
          switch (listaCampiChiave.size()) {
          case 1:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave1, listaLocaleMessaggiControllo, controllo);
            break;
          case 2:
            //Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI).
            // Nel caso di controlli sulle entita' INTTRI, INRTRI, OITRI, bisogna cambiare il riferimento
            // all'occorrenza nel titolo del controllo visualizzato a video
            Object localValoreCampoChiave2 = null;
            if ("INTTRI".equals(entita)) {
              // N.B.: per INTTRI il riferimento è da cambiare solo per quei controlli che non riguardano i fabbisogni
              if ( !("INOLTRO_RAF".equals(codiceFunzione) || "INOLTRO_RDP".equals(codiceFunzione)) ) {
                localValoreCampoChiave2 = valoreCampoChiave2;
                //NPROGINT
                Long nprogint = (Long) this.sqlManager.getObject("select NPROGINT from INTTRI where CONTRI=? and CONINT=?", new Object[] {
                    valoreCampoChiave1, valoreCampoChiave2 });
                localValoreCampoChiave2 = nprogint;
                if (nprogint == null) localValoreCampoChiave2 = "";
              } else {
                localValoreCampoChiave2 = valoreCampoChiave2;
              }
            } else if ("INRTRI".equals(entita)) {  // CUIINT
              String cuiint = (String) this.sqlManager.getObject("select CUIINT from INRTRI where CONTRI=? and CONINT=?", new Object[] { valoreCampoChiave1, valoreCampoChiave2 } );
              if (StringUtils.isNotEmpty(cuiint))
                localValoreCampoChiave2 = cuiint;
              else
                localValoreCampoChiave2 = "";
            } else if ("OITRI".equals(entita)) {
              //OITRI ---  CUP
              String cup = (String) this.sqlManager.getObject("select CUP from OITRI where CONTRI=? and NUMOI=?", new Object[] { valoreCampoChiave1, valoreCampoChiave2 } );
              if (StringUtils.isNotEmpty(cup))
                localValoreCampoChiave2 = cup;
              else
                localValoreCampoChiave2 = "";
            }

            if (localValoreCampoChiave2 == null) {
              localValoreCampoChiave2 = valoreCampoChiave2;
            }
            
            this.creaMessaggioControlloDiTipoTitolo(localValoreCampoChiave2, listaLocaleMessaggiControllo, controllo);
            break;
          case 3:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave3, listaLocaleMessaggiControllo, controllo);
            break;
          case 4:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave4, listaLocaleMessaggiControllo, controllo);
            break;
          case 5:
            this.creaMessaggioControlloDiTipoTitolo(valoreCampoChiave5, listaLocaleMessaggiControllo, controllo);
            break;
          }
        } else {
          if (!this.isControlloVerificato(controllo, valoreCampoChiave1, valoreCampoChiave2, valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5)) {
            MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), controllo.getSezione(),
                this.getDescrizioneCampo(entita, controllo.getTitolo()), controllo.getMessaggio());
            listaLocaleMessaggiControllo.add(msgControllo);
          }
        }
      }
    }
    
    if (listaLocaleMessaggiControllo.size() > 1) {
      listaMessaggiControllo.addAll(listaLocaleMessaggiControllo);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliEntitaPT: fine metodo");
    }
  }
  
  
  /**
   *  Aggiunta dei messaggi di controllo locali alla lista che contiene tutti i messaggi.
   *  Nell'aggiungere i messaggi si evita di aggiungere due messaggi consecutivi di tipo T (titolo)
   *  
   * @param listaMessaggiControllo lista messaggi in cui aggiungere
   * @param listaLocaleMessaggiControllo lista messaggi da aggiungere
   */
  private void aggiungiMessaggiLocali( List<MessaggioControlloBean> listaMessaggiControllo,
      List<MessaggioControlloBean> listaLocaleMessaggiControllo) {
    for (Iterator<MessaggioControlloBean> iterator = listaLocaleMessaggiControllo.iterator(); iterator.hasNext();) {
      MessaggioControlloBean messaggioControlloBean = iterator.next();
      if (listaMessaggiControllo.size() > 0) {
        MessaggioControlloBean tmpMessaggioControlloBean = listaMessaggiControllo.get(listaMessaggiControllo.size()-1);
        if ("T".equals(tmpMessaggioControlloBean.getTipoMessaggio()) &&
            "T".equals(messaggioControlloBean.getTipoMessaggio()) && 
            (messaggioControlloBean.getPagina().indexOf("Archivio") < 0
            && 
            messaggioControlloBean.getPagina().indexOf("Sezione") < 0)) {
          listaMessaggiControllo.remove(tmpMessaggioControlloBean);
          tmpMessaggioControlloBean = null;
        }
        listaMessaggiControllo.add(messaggioControlloBean);
      } else {
        listaMessaggiControllo.add(messaggioControlloBean);
      }
    }
    
    // Se l'ultimo messaggio e' di tipo T (titolo) allora lo si rimuove
    if (listaMessaggiControllo.size() > 0) {
      if ("T".equals(listaMessaggiControllo.get(listaMessaggiControllo.size()-1).getTipoMessaggio())) {
        listaMessaggiControllo.remove(listaMessaggiControllo.size()-1);
      }
    }
  }

  /**
   * Creazione del messaggio di tipo titolo.
   * 
   * @param progressivo
   * @param listaLocaleMessaggiControllo
   * @param controllo
   */
  private void creaMessaggioControlloDiTipoTitolo(Object progressivo,
      List<MessaggioControlloBean> listaLocaleMessaggiControllo, ControlloBean controllo) {
    if (StringUtils.isNotEmpty(controllo.getSezione())) {
      String sezione = new String(controllo.getSezione());
      if (sezione.indexOf("{0}") > 0) {
        if (progressivo != null) {
          sezione = StringUtils.replace(sezione, "{0}", progressivo.toString());
        } else {
          sezione = StringUtils.replace(sezione, "{0}", "");
        }
      }
      MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), sezione,
          null, controllo.getMessaggio());
      listaLocaleMessaggiControllo.add(msgControllo);
    } else {
      MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), "",
          null, controllo.getMessaggio());
      listaLocaleMessaggiControllo.add(msgControllo);
    }
  }

  /**
   * Esegue il controllo indicato nell'argomento <i>controlloBean</i> e ritorna true se verificato, false altrimenti.
   * 
   * @param controlloBean controllo da verificare
   * @param valoreCampoChiave1 Valore del primo campo chiave dell'entita'
   * @param valoreCampoChiave2 Valore del secondo campo chiave dell'entita'
   * @param valoreCampoChiave3 Valore del terzo campo chiave dell'entita'
   * @param valoreCampoChiave4 Valore del quarto campo chiave dell'entita'
   * @return Ritornatrue se il controllo e' verificato, false altrimenti.
   */
  private boolean isControlloVerificato(ControlloBean controlloBean, Object valoreCampoChiave1, Object valoreCampoChiave2,
      Object valoreCampoChiave3, Object valoreCampoChiave4, String valoreCampoChiave5) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("isControlloVerificato: inizio metodo");
      StringBuffer str = new StringBuffer("isControlloVerificato: entita=");
      str.append(controlloBean.getEntita());
      str.append(", condizione=");
      str.append(controlloBean.getCondizione());
      str.append(", valori campi chiave:");
      str.append("chiave1=");
      if (valoreCampoChiave1 != null) {
        str.append(valoreCampoChiave1.toString());
      } else { 
        str.append("null");
      }
      str.append(", chiave2=");
      if (valoreCampoChiave2 != null) {
        str.append(valoreCampoChiave2.toString());
      } else { 
        str.append("null");
      }
      str.append(", chiave3=");
      if (valoreCampoChiave3 != null) {
        str.append(valoreCampoChiave3.toString());
      } else { 
        str.append("null");
      }
      str.append(", chiave4=");
      if (valoreCampoChiave4 != null) {
        str.append(valoreCampoChiave4.toString());
      } else {
        str.append("null");
      }
      str.append(", chiave5=");
      if (valoreCampoChiave5 != null) {
        str.append(valoreCampoChiave5.toString());
      } else {
        str.append("null");
      }
      logger.debug(str.toString());
    }
    
    int controllo =  this.sqlMapTabControlliDao.getControllo(controlloBean, valoreCampoChiave1, valoreCampoChiave2,
        valoreCampoChiave3, valoreCampoChiave4, valoreCampoChiave5);
    
    if (logger.isDebugEnabled()) {
      logger.debug("isControlloVerificato: fine metodo");
    }
    
    if (controllo == 1) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Ritorna la descrizione di <i>nomeTabella</i>.<i>nomeCampo</i> se esisstente nel DizionarioCampi,
   * altrimenti ritorna <i>nomeCampo</i>.
   * 
   * @param nomeTabella Nome tabella
   * @param nomeCampo Nome campo o altro valore
   * @return Ritorna la descrizione di nomeTabella.nomeCampo se esisstente nel DizionarioCampi,
   * altrimenti ritorna nomeCampo.
   */
  private String getDescrizioneCampo(final String nomeTabella, final String nomeCampo) {
    StringBuffer descrizione = new StringBuffer("");
    
    String[] arrayCampi = null;
    if (StringUtils.contains(nomeCampo, ";")) {
      arrayCampi = nomeCampo.split(";");
    } else {
      arrayCampi = new String[1];
      arrayCampi[0] = nomeCampo;
    }
    
    for (int i = 0; i < arrayCampi.length; i++) {
      Campo c = DizionarioCampi.getInstance().getCampoByNomeFisico(nomeTabella + "." + arrayCampi[i]);
      if (c != null) {
        descrizione.append(c.getDescrizioneWEB());
      } else {
        c = DizionarioCampi.getInstance().getCampoByNomeFisico(arrayCampi[i]);
        if (c != null) {
          descrizione.append(c.getDescrizioneWEB());
        } else {
          descrizione.append(nomeCampo);
        }
      }
      if (i > 0 && i < arrayCampi.length-1) {
        descrizione.append(", ");
      }
    }
    return descrizione.toString();
  }
  
  /**
   * Validazione dei dati dell'impresa (IMPR), distinguendo se e' un'impresa aggiudicataria o meno.
   * 
   * @param codiceImpresa
   * @param isAggiudicataria
   * @param pagina
   * @param listaMessaggiControllo
   * @throws SQLException
   */
  private void validazioneImpresa(String codiceImpresa, boolean isAggiudicataria, String pagina, 
      List<MessaggioControlloBean> listaMessaggiControllo, String codiceFunzione) throws SQLException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneImpresa: inizio metodo");
    }
    // Estrazione dei controlli per l'entita' indicata
    List<ControlloBean> listaContr = this.sqlMapTabControlliDao.getListaControlli("W9", "IMPR", codiceFunzione);
    
    List<MessaggioControlloBean> listaLocaleMessaggiControllo = new ArrayList<MessaggioControlloBean>();

    if (listaContr != null && listaContr.size() > 0) {
      for (int i = 0; i < listaContr.size(); i++) {
        ControlloBean controllo = listaContr.get(i);
        if ("T".equals(controllo.getTipo())) {
          if (StringUtils.isNotEmpty(pagina)) {
            String tmp = controllo.getSezione();
            controllo.setSezione(StringUtils.replace(tmp, "impresa", StringUtils.lowerCase(pagina)));
          }
          this.creaMessaggioControlloDiTipoTitolo(null, listaLocaleMessaggiControllo, controllo);
        } else {
          if (!isControlloVerificato(controllo, codiceImpresa, null, null, null, null)) {
            MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), controllo.getSezione(),
                this.getDescrizioneCampo("IMPR", controllo.getTitolo()), controllo.getMessaggio());
            listaLocaleMessaggiControllo.add(msgControllo);
          }
        }
      }
    }
    
    String codiceTecnicoImpresa = (String) this.sqlManager.getObject(
        "select CODLEG from IMPLEG where CODIMP2=?", new Object[] { codiceImpresa } );
    if (StringUtils.isNotEmpty(codiceTecnicoImpresa)) {
     this.validazioneLegaleRappresentante(codiceTecnicoImpresa, listaLocaleMessaggiControllo, codiceFunzione);
    }
    
    // Aggiunta dei messaggi di controllo locali alla lista che contiene tutti i messaggi.
    // Nell'aggiungere i messaggi si evita di aggiungere due messaggi consecutivi di tipo T (titoli)
    if (listaLocaleMessaggiControllo.size() > 0) {
      this.aggiungiMessaggiLocali(listaMessaggiControllo, listaLocaleMessaggiControllo);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneImpresa: fine metodo");
    }
  }

  /**
   * Validazione dei dati del legale rappresentante (TEIM).
   * 
   * @param codiceTecnicoImpresa
   * @param listaMessaggiControllo
   */
  private void validazioneLegaleRappresentante(String codiceTecnicoImpresa, 
      List<MessaggioControlloBean> listaMessaggiControllo, String codiceFunzione) {
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneLegaleRappresentante: inizio metodo");
    }

    // Estrazione dei controlli per l'entita' TEIM
    List<ControlloBean> listaContr = this.sqlMapTabControlliDao.getListaControlli("W9", "TEIM", codiceFunzione);
    List<MessaggioControlloBean> listaLocaleMessaggiControllo = new ArrayList<MessaggioControlloBean>();
    
    if (listaContr != null && listaContr.size() > 0) {
      for (int i = 0; i < listaContr.size(); i++) {
        ControlloBean controllo = listaContr.get(i);
        if ("T".equals(controllo.getTipo())) {
          this.creaMessaggioControlloDiTipoTitolo(null, listaLocaleMessaggiControllo, controllo);
        } else {
          if (!isControlloVerificato(controllo, codiceTecnicoImpresa, null, null, null, null)) {
            MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), controllo.getSezione(),
                this.getDescrizioneCampo("TEIM", controllo.getTitolo()), controllo.getMessaggio());
            listaLocaleMessaggiControllo.add(msgControllo);
          }
        }
      }
      if (listaLocaleMessaggiControllo.size() > 0) {
        this.aggiungiMessaggiLocali(listaMessaggiControllo, listaLocaleMessaggiControllo);
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneLegaleRappresentante: fine metodo");
    }
  }
  
  /**
   * Validazione del tecnico dell'impresa (TECNI).
   * 
   * @param codiceTecnicoImpresa
   * @param listaMessaggiControllo
   */
  private void validazioneTecnico(String codiceTecnicoImpresa, List<MessaggioControlloBean> listaMessaggiControllo,
      String codiceFunzione) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneTecnico: inizio metodo");
    }
    
    // Estrazione dei controlli per l'entita' TEIM
    List<ControlloBean> listaContr = this.sqlMapTabControlliDao.getListaControlli("W9", "TECNI", codiceFunzione);
    List<MessaggioControlloBean> listaLocaleMessaggiControllo = new ArrayList<MessaggioControlloBean>();
    
    if (listaContr != null && listaContr.size() > 0) {
      for (int i = 0; i < listaContr.size(); i++) {
        ControlloBean controllo = listaContr.get(i);
        
        if (!isControlloVerificato(controllo, codiceTecnicoImpresa, null, null, null, null)) {
          MessaggioControlloBean msgControllo = new MessaggioControlloBean(controllo.getTipo(), controllo.getSezione(),
              this.getDescrizioneCampo("TECNI", controllo.getTitolo()), controllo.getMessaggio());
          listaLocaleMessaggiControllo.add(msgControllo);
        }
      }
      if (listaLocaleMessaggiControllo.size() > 0) {
        this.aggiungiMessaggiLocali(listaMessaggiControllo, listaLocaleMessaggiControllo);
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("validazioneTecnico: fine metodo");
    }
  }
  
  /**
   * 
   * @param codicePianoTriennale (contri)
   * @param listaMessaggiDiControllo
   * @throws SQLException
   */
  public HashMap<String, Object> eseguiControlliPianiNorma2(Long codicePianoTriennale, String codiceFunzione)
      throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianiNorma2: inizio metodo");
    }
    
    Long tipoProgramma = (Long) this.sqlManager.getObject(
        "select TIPROG from PIATRI where CONTRI=?", new Object[] { codicePianoTriennale });
    
    List<Object> listaControlli = new ArrayList<Object>();
    
    if (new Long(1).equals(tipoProgramma)) {
      this.eseguiControlliPianoTriennaleOperePubbliche(codicePianoTriennale, codiceFunzione, listaControlli);
    } else if (new Long(2).equals(tipoProgramma)) {
      this.eseguiControlliPianoBiennaleAcquisti(codicePianoTriennale, codiceFunzione, listaControlli);
    }

    HashMap<String, Object> infoValidazione = new HashMap<String, Object>();
    
    int numeroWarning = 0;
    int numeroErrori = 0;
    for (int i=0; i < listaControlli.size(); i++) {
      MessaggioControlloBean messaggioControlloBean = (MessaggioControlloBean) listaControlli.get(i);
      if ("E".equals(messaggioControlloBean.getTipoMessaggio())) {
        numeroErrori++;
      } else if ("w".equals(messaggioControlloBean.getTipoMessaggio())) {
        numeroWarning++;
      }
    }

    infoValidazione.put("titolo", null);
    infoValidazione.put("listaControlli", listaControlli);
    infoValidazione.put("numeroWarning", numeroWarning);
    infoValidazione.put("numeroErrori", numeroErrori);
    infoValidazione.put("controlliSuBean", Boolean.TRUE);
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianiNorma2: fine metodo");
    }
    
    return infoValidazione;
  }
  
  
  /**
   * 
   * 
   * @param codicePianoTriennale (contri)
   * @param listaMessaggiDiControllo
   * @throws SQLException
   */
  public void eseguiControlliPianoTriennaleOperePubbliche(Long codicePianoTriennale, String codiceFunzione, 
      List<Object> listaControlli) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianoTriennaleOperePubbliche: inizio metodo");
    }

    List<MessaggioControlloBean> listaMessaggiDiControllo = new ArrayList<MessaggioControlloBean>();
    
    List<EntitaCampiChiaveBean> listaEntitaCampiChiave = new ArrayList<EntitaCampiChiaveBean>();
    listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "PIATRI", codicePianoTriennale));
    
    // Lista degli interventi
    List<?> listaNumeroInterventi = this.sqlManager.getListHashMap(
        "select CONINT from INTTRI where CONTRI=? order by CONINT asc", 
        new Object[] { codicePianoTriennale });
    if (listaNumeroInterventi.size() > 0) {
      for (int j=0; j < listaNumeroInterventi.size(); j++) {
        HashMap<?, ?> hmNumeroIntervento = (HashMap<?, ?>) listaNumeroInterventi.get(j);
        Long numeroIntervento = (Long) ((JdbcParametro) hmNumeroIntervento.get("CONINT")).getValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "INTTRI", codicePianoTriennale, numeroIntervento));
        
        // Lista degli immobili per l'intervento
        List<?> listaNumeroImmobili = this.sqlManager.getListHashMap(
            "select NUMIMM from IMMTRAI where CONTRI=? and CONINT=? and NUMOI is null order by NUMIMM asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroImmobili.size() > 0) {
          for (int k=0; k < listaNumeroImmobili.size(); k++) {
            HashMap<?, ?> hmNumeroImmobile = (HashMap<?, ?>) listaNumeroImmobili.get(k);
            Long numeroImmobile = (Long) ((JdbcParametro) hmNumeroImmobile.get("NUMIMM")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "IMMTRAI", codicePianoTriennale, numeroIntervento, numeroImmobile));
          }
        }
        
        // Lista delle risorse capitolo bi bilancio
        List<?> listaNumeroRisorseCapitolo = this.sqlManager.getListHashMap(
            "select NUMCB from RIS_CAPITOLO where CONTRI=? and CONINT=? order by NUMCB asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroRisorseCapitolo.size() > 0) {
          for (int k=0; k < listaNumeroRisorseCapitolo.size(); k++) {
            HashMap<?, ?> hmNumeroRisCapitolo = (HashMap<?, ?>) listaNumeroRisorseCapitolo.get(k);
            Long numeroRisCapitolo = (Long) ((JdbcParametro) hmNumeroRisCapitolo.get("NUMCB")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "RIS_CAPITOLO", codicePianoTriennale, numeroIntervento, numeroRisCapitolo));
          }
        }
      }
    }
    
    // Lista delle opere incompiute
    List<?> listaNumeroOpereIncompiute = this.sqlManager.getListHashMap(
        "select NUMOI from OITRI where CONTRI=? and NUMOI > 0 order by NUMOI asc", 
        new Object[] { codicePianoTriennale });
    if (listaNumeroOpereIncompiute.size() > 0) {
      for (int j=0; j < listaNumeroOpereIncompiute.size(); j++) {
        HashMap<?, ?> hmNumeroOpereIncompiute = (HashMap<?, ?>) listaNumeroOpereIncompiute.get(j);
        Long numeroOperaIncompiuta = (Long) ((JdbcParametro) hmNumeroOpereIncompiute.get("NUMOI")).getValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "OITRI", codicePianoTriennale, numeroOperaIncompiuta));
        
        // Lista degli immobili per l'intervento
        List<?> listaNumeroImmobili = this.sqlManager.getListHashMap(
            "select NUMIMM from IMMTRAI where CONTRI=? and NUMOI=? and CONINT=0 order by NUMIMM asc",
            new Object[] { codicePianoTriennale, numeroOperaIncompiuta });
        if (listaNumeroImmobili.size() > 0) {
          for (int k=0; k < listaNumeroImmobili.size(); k++) {
            HashMap<?, ?> hmNumeroImmobile = (HashMap<?, ?>) listaNumeroImmobili.get(k);
            Long numeroImmobile = (Long) ((JdbcParametro) hmNumeroImmobile.get("NUMIMM")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "IMMTRAI", codicePianoTriennale, new Long(0), numeroImmobile, numeroOperaIncompiuta));
          }
        }
      }
    }
    
    // Lista degli interventi non riproposti
    List<?> listaNumeroInterventiNonRiproposti = this.sqlManager.getListHashMap(
        "select CONINT from INRTRI where CONTRI=? order by CONINT asc",  
        new Object[] { codicePianoTriennale });
    if (listaNumeroInterventiNonRiproposti.size() > 0) {
      for (int j=0; j < listaNumeroInterventiNonRiproposti.size(); j++) {
        HashMap<?, ?> hmNumeroInterventoNonRiproposto = (HashMap<?, ?>) listaNumeroInterventiNonRiproposti.get(j);
        Long numeroInterventoNonRiproposto = (Long) ((JdbcParametro) hmNumeroInterventoNonRiproposto.get("CONINT")).getValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "INRTRI", codicePianoTriennale, numeroInterventoNonRiproposto));
      }
    }
    
    if (listaEntitaCampiChiave.size() > 0) {
      for (int i=0; i < listaEntitaCampiChiave.size(); i++) {
        EntitaCampiChiaveBean entitaCampiChiave = listaEntitaCampiChiave.get(i);

        this.eseguiControlliEntita(entitaCampiChiave.getCodiceApplicazione(), entitaCampiChiave.getEntita(), codiceFunzione,
            entitaCampiChiave.getValoreCampiChiaveBean(), listaMessaggiDiControllo);
      }
      
      // Copia degli elementi presenti nella listaMessaggiDiControllo nella listaControlli 
      if (listaMessaggiDiControllo.size() > 0) {
        for (Iterator<MessaggioControlloBean> iterator = listaMessaggiDiControllo.iterator(); iterator.hasNext();) {
          Object object = (Object) iterator.next();
          listaControlli.add(object);
        }
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianoTriennaleOperePubbliche: fine metodo");
    }
  }
  
  /**
   * 
   * 
   * @param codicePianoTriennale (contri)
   * @param listaMessaggiDiControllo
   * @throws SQLException
   */
  public void eseguiControlliPianoBiennaleAcquisti(Long codicePianoTriennale, String codiceFunzione,
      List<Object> listaControlli) throws SQLException {
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianoBiennaleAcquisti: inizio metodo");
    }

    List<MessaggioControlloBean> listaMessaggiDiControllo = new ArrayList<MessaggioControlloBean>();
    
    List<EntitaCampiChiaveBean> listaEntitaCampiChiave = new ArrayList<EntitaCampiChiaveBean>();
    listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "PIATRI", codicePianoTriennale));
    
    // Lista degli interventi
    List<?> listaNumeroInterventi = this.sqlManager.getListHashMap(
        "select CONINT from INTTRI where CONTRI=? order by CONINT asc", 
        new Object[] { codicePianoTriennale });
    if (listaNumeroInterventi.size() > 0) {
      for (int j=0; j < listaNumeroInterventi.size(); j++) {
        HashMap<?, ?> hmNumeroIntervento = (HashMap<?, ?>) listaNumeroInterventi.get(j);
        Long numeroIntervento = (Long) ((JdbcParametro) hmNumeroIntervento.get("CONINT")).getValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "INTTRI", codicePianoTriennale, numeroIntervento));
        // Lista degli immobili per l'intervento
        List<?> listaNumeroImmobili = this.sqlManager.getListHashMap(
            "select NUMIMM from IMMTRAI where CONTRI=? and CONINT=? and NUMOI is null order by NUMIMM asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroImmobili.size() > 0) {
          for (int k=0; k < listaNumeroImmobili.size(); k++) {
            HashMap<?, ?> hmNumeroImmobile = (HashMap<?, ?>) listaNumeroImmobili.get(k);
            Long numeroImmobile = (Long) ((JdbcParametro) hmNumeroImmobile.get("NUMIMM")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "IMMTRAI", codicePianoTriennale, numeroIntervento, numeroImmobile));
          }
        }
        
        // Lista delle risorse capitolo bi bilancio
        List<?> listaNumeroRisorseCapitolo = this.sqlManager.getListHashMap(
            "select NUMCB from RIS_CAPITOLO where CONTRI=? and CONINT=? order by NUMCB asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroRisorseCapitolo.size() > 0) {
          for (int k=0; k < listaNumeroRisorseCapitolo.size(); k++) {
            HashMap<?, ?> hmNumeroRisCapitolo = (HashMap<?, ?>) listaNumeroRisorseCapitolo.get(k);
            Long numeroRisCapitolo = (Long) ((JdbcParametro) hmNumeroRisCapitolo.get("NUMCB")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "RIS_CAPITOLO", codicePianoTriennale, numeroIntervento, numeroRisCapitolo));
          }
        }
      }
    }
    
    // Lista degli interventi non riproposti
    List<?> listaNumeroInterventiNonRiproposti = this.sqlManager.getListHashMap(
        "select CONINT from INRTRI where CONTRI=? order by CONINT asc",  
        new Object[] { codicePianoTriennale });
    if (listaNumeroInterventiNonRiproposti.size() > 0) {
      for (int j=0; j < listaNumeroInterventiNonRiproposti.size(); j++) {
        HashMap<?, ?> hmNumeroInterventoNonRiproposto = (HashMap<?, ?>) listaNumeroInterventiNonRiproposti.get(j);
        Long numeroInterventoNonRiproposto = (Long) ((JdbcParametro) hmNumeroInterventoNonRiproposto.get("CONINT")).getValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "INRTRI", codicePianoTriennale, numeroInterventoNonRiproposto));
      }
    }
    
    if (listaEntitaCampiChiave.size() > 0) {
      for (int i=0; i < listaEntitaCampiChiave.size(); i++) {
        EntitaCampiChiaveBean entitaCampiChiave = listaEntitaCampiChiave.get(i);
        this.eseguiControlliEntita(entitaCampiChiave.getCodiceApplicazione(), entitaCampiChiave.getEntita(), codiceFunzione,
            entitaCampiChiave.getValoreCampiChiaveBean(), listaMessaggiDiControllo);
      }
      
      // Copia degli elementi presenti nella listaMessaggiDiControllo nella listaControlli 
      if (listaMessaggiDiControllo.size() > 0) {
        for (Iterator<MessaggioControlloBean> iterator = listaMessaggiDiControllo.iterator(); iterator.hasNext();) {
          Object object = (Object) iterator.next();
          listaControlli.add(object);
        }
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliPianoBiennaleAcquisti: fine metodo");
    }
  }

  /**
   * 
   * 
   * @String fabbisogni (conint)
   * @String fabbisogni (codiceFunzione)
   * @String fabbisogni (funzionalita)
   * @List<Object> listaControlli
   * @throws SQLException
   * @throws GestoreException 
   */
  public void eseguiControlliFabbisogni(String fabbisogni, String codiceFunzione, String funzionalita,
      List<Object> listaControlli) throws SQLException, GestoreException {
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliFabbisogni: inizio metodo");
    }
    //Raccolta fabbisogni: aprile 2019. Controllo dei dati inseriti sulla proposta/fabbisogno(TAB_CONTROLLI).
    Long codicePianoTriennale = new Long(0);
    
    List<MessaggioControlloBean> listaMessaggiDiControllo = new ArrayList<MessaggioControlloBean>();

    List<EntitaCampiChiaveBean> listaEntitaCampiChiave = new ArrayList<EntitaCampiChiaveBean>();
    
    String[] chiavi = new String[] {};
    chiavi = fabbisogni.split(";;");

    if (chiavi.length > 0) {
      for (int j=0; j < chiavi.length; j++) {
        DataColumnContainer fabbisogniItem = new DataColumnContainer(
          chiavi[j]);
        Long numeroIntervento = (fabbisogniItem.getColumnsBySuffix("CONINT", true))[0].getValue().longValue();
        listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "INTTRI", codicePianoTriennale, numeroIntervento));
        
        // Lista degli immobili per l'intervento
        List<?> listaNumeroImmobili = this.sqlManager.getListHashMap(
            "select NUMIMM from IMMTRAI where CONTRI=? and CONINT=? and NUMOI is null order by NUMIMM asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroImmobili.size() > 0) {
          for (int k=0; k < listaNumeroImmobili.size(); k++) {
            HashMap<?, ?> hmNumeroImmobile = (HashMap<?, ?>) listaNumeroImmobili.get(k);
            Long numeroImmobile = (Long) ((JdbcParametro) hmNumeroImmobile.get("NUMIMM")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "IMMTRAI", codicePianoTriennale, numeroIntervento, numeroImmobile));
          }
        }
        
        // Lista delle risorse capitolo bi bilancio
        List<?> listaNumeroRisorseCapitolo = this.sqlManager.getListHashMap(
            "select NUMCB from RIS_CAPITOLO where CONTRI=? and CONINT=? order by NUMCB asc",
            new Object[] { codicePianoTriennale, numeroIntervento });
        if (listaNumeroRisorseCapitolo.size() > 0) {
          for (int k=0; k < listaNumeroRisorseCapitolo.size(); k++) {
            HashMap<?, ?> hmNumeroRisCapitolo = (HashMap<?, ?>) listaNumeroRisorseCapitolo.get(k);
            Long numeroRisCapitolo = (Long) ((JdbcParametro) hmNumeroRisCapitolo.get("NUMCB")).getValue();
            listaEntitaCampiChiave.add(new EntitaCampiChiaveBean("PT", "RIS_CAPITOLO", codicePianoTriennale, numeroIntervento, numeroRisCapitolo));
          }
        }
      }
    }
    
    if (listaEntitaCampiChiave.size() > 0) {
      for (int i=0; i < listaEntitaCampiChiave.size(); i++) {
    	  EntitaCampiChiaveBean entitaCampiChiave = listaEntitaCampiChiave.get(i);
    	  if ("RDS_SottoponiAdApprFinanz".equals(funzionalita)) {
    		  //Per inoltra proposta CODFUNZIONE= ‘INOLTRO_RAF’ se QE_SL= ‘2’ o vuoto, CODFUNZIONE= ‘INOLTRO_RDP’ se QE_SL= ‘1’.
    		  String qe_sl = (String)this.sqlManager.getObject("SELECT QE_SL FROM FABBISOGNI WHERE CONINT = ?", new Object[] {entitaCampiChiave.getValoreCampiChiaveBean().getValoreCampoChiave2()});
    		  if(qe_sl == null || "2".equals(qe_sl)) {
    			  codiceFunzione = "INOLTRO_RAF";
    		  } else {
    			  codiceFunzione = "INOLTRO_RDP";
    		  }
    	  }
    	  this.eseguiControlliEntita(entitaCampiChiave.getCodiceApplicazione(), entitaCampiChiave.getEntita(), codiceFunzione,
            entitaCampiChiave.getValoreCampiChiaveBean(), listaMessaggiDiControllo);
      }
      
      // Copia degli elementi presenti nella listaMessaggiDiControllo nella listaControlli 
      if (listaMessaggiDiControllo.size() > 0) {
        for (Iterator<MessaggioControlloBean> iterator = listaMessaggiDiControllo.iterator(); iterator.hasNext();) {
          Object object = (Object) iterator.next();
          listaControlli.add(object);
        }
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("eseguiControlliFabbisogni: fine metodo");
    }
  }
  
}
