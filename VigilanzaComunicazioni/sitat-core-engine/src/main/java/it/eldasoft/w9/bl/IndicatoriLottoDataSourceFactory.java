package it.eldasoft.w9.bl;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe di appoggio usata per sviluppare il file JasperReport con IReport per
 * la creazione degli indicatori del lotto.
 * Vedere parte centrale del video a questa url: https://www.youtube.com/watch?v=rdrooeIVLYE
 */
public class IndicatoriLottoDataSourceFactory {

  public static List<IndicatoreBean> createBeanCollection() {
    
    List<IndicatoreBean> listaIndicatori = new ArrayList<IndicatoreBean>();
    
    IndicatoreBean indicatore = new IndicatoreBean();
    indicatore.setDescrizione("descrizione-00");
    indicatore.setUnitaDiMisura("unitaDiMisura-00");
    indicatore.setSogliaInferiore("sogliaInferiore-00");
    indicatore.setSogliaSuperiore("sogliaSuperiore-00");
    indicatore.setValore("valore-00");
    indicatore.setCalcolabile(Boolean.TRUE);
    indicatore.setAffidabilitaStima("2");
    indicatore.setContrattiSimiliStima("3");
    listaIndicatori.add(indicatore);
    
    indicatore = new IndicatoreBean();
    indicatore.setDescrizione("descrizione-01");
    indicatore.setUnitaDiMisura("giorni");
    indicatore.setSogliaInferiore("sogliaInferiore-01");
    indicatore.setSogliaSuperiore("sogliaSuperiore-01");
    indicatore.setValore("valore-01");
    indicatore.setAsterisco("*");
    indicatore.setCalcolabile(Boolean.TRUE);
    indicatore.setAffidabilitaStima("3");
    indicatore.setContrattiSimiliStima("4");
    listaIndicatori.add(indicatore);
    
    indicatore = new IndicatoreBean();
    indicatore.setDescrizione("descrizione-02");
    indicatore.setUnitaDiMisura("mesi");
    indicatore.setSogliaInferiore("sogliaInferiore-02");
    indicatore.setSogliaSuperiore("sogliaSuperiore-02");
    indicatore.setValore("valore-02");
    indicatore.setCalcolabile(Boolean.TRUE);
    indicatore.setAffidabilitaStima("4");
    indicatore.setContrattiSimiliStima("6");
    listaIndicatori.add(indicatore);
    
    return listaIndicatori;
    
  }
  
}
