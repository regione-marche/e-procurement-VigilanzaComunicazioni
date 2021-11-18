/*
 * Created on 15/mar/07
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.w9.tags.gestori.decoratori;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

/**
 * Gestore per il tabellato dell'incarico professionale. 
 * 
 * @author Luca.Giacomazzo
 */
public class GestoreFiltroRuoloIncaricato extends GestoreCampoTabellatoGen {

  /** Query per Scheda lotto di gara. */
  private final String queryTabellatoIdRuoloA03         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (14,15,16,17) order by TAB1TIP";
  /** Query per IdRuolo per aggiudicazione. */
  private final String queryTabellatoIdRuoloA05         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (1,2,3,4,5,6,7,8,14,15,16,17,18) Order by TAB1TIP";
  /** Query per IdRuolo per inizio contratto sopra soglia. */
  private final String queryTabellatoIdRuoloA06         = "select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (5,6,7,8,16,17,98,99) Order by TAB1TIP";
  /** Query per IdRuolo per inizio contratto sotto soglia. */
  private final String queryTabellatoIdRuoloA07         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (1,2,3,4,5,6,7,8,14,98,99) Order by TAB1TIP";
  /** Query per IdRuolo per Collaudo contratto. */
  private final String queryTabellatoIdRuoloA11         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (10,11,12,13) Order by TAB1TIP";
  //private final String queryTabellatoIdRuoloB01         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (1,2,3,4,5,6,7,8,14,99) Order by TAB1TIP";
  /** Query per IdRuolo per aggiudicazione sotto soglia. */
  private final String queryTabellatoIdRuoloA04         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP = 14";
  /** Query per IdRuolo per Adesione accordo quadro. */
  private final String queryTabellatoIdRuoloA21         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (5,6,14,15,16,17,18) order by TAB1TIP";
  /** Query per Scheda cantiere e notifiche preliminari. */
  private final String queryTabellatoIdRuoloB08         = "Select TAB1TIP, TAB1DESC from TAB1 Where TAB1COD = 'W3004' and TAB1TIP IN (6,7) order by TAB1TIP";

  /**
   * Costruttore.
   */
  public GestoreFiltroRuoloIncaricato() {
    super(false, "N2");
  }

  public SqlSelect getSql() {
    try {
      String valoreChiave = "";
      String query = "";
      PageContext page = this.getPageContext();
      if (page.getRequest().getParameter("keyW9inca") != null
          && !page.getRequest().getParameter("keyW9inca").equals("")) {
        valoreChiave = page.getRequest().getParameter("keyW9inca");
      } else if (page.getRequest().getAttribute("keyW9inca") != null
          && !page.getRequest().getAttribute("keyW9inca").equals("")) {
        valoreChiave = page.getRequest().getAttribute("keyW9inca").toString();
      }
      int indice = valoreChiave.indexOf("SEZIONE");
      if (indice != -1) {
        valoreChiave = valoreChiave.substring(indice);
        indice = valoreChiave.indexOf("T");
        String valoresezione = valoreChiave.substring(indice + 2, indice + 4);
        if (valoresezione.equals("PA")) {
          query = queryTabellatoIdRuoloA05;
        } else if (valoresezione.equals("CO")) {
          query = queryTabellatoIdRuoloA11;
        } else if (valoresezione.equals("IN")) {
          String tiposezione = valoreChiave.substring(indice + 5, indice + 8);
          if (tiposezione.equals("A06")) {
            query = queryTabellatoIdRuoloA06;
          } else if (tiposezione.equals("A07")) {
            query = queryTabellatoIdRuoloA07;
          }
        //} else if (valoresezione.equals("SI")) {
        //  query = queryTabellatoIdRuoloB01;
        } else if (valoresezione.equals("RR")) {
          query = queryTabellatoIdRuoloA04;
        } else if (valoresezione.equals("RQ")) {
          query = queryTabellatoIdRuoloA21;
        } else if (valoresezione.equals("NP"))  {
          query = queryTabellatoIdRuoloB08;
        } else if (valoresezione.equals("RA"))  {
          query = queryTabellatoIdRuoloA03;
        }
        
        HashMap< ? , ? > datiRiga = (HashMap< ? , ? >) this.getPageContext().getAttribute(
            "datiRiga", PageContext.REQUEST_SCOPE);
        if (datiRiga != null) {
          return new SqlSelect(query, new Object[] {});
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}