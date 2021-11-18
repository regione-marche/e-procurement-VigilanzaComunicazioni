package it.eldasoft.sil.pt.tags.funzioni;

import it.eldasoft.gene.tags.functions.AbstractGetTitleFunction;

import javax.servlet.jsp.PageContext;

public class GetTitleFunction extends AbstractGetTitleFunction {

  @Override
  public String[] initFunction() {


    return new String[] {
    // Programmi
        "PIATRI|Nuovo Programma |{0}"
            + "||select BRETRI from PIATRI where contri = #PIATRI.CONTRI#",

        // Interventi Triennali
        "INTTRI|Nuovo Intervento del Programma {0} |Intervento n.{0} del Programma {1}"
            + "|select PIATRI.ID from PIATRI where PIATRI.CONTRI = #CONTRI#"
            + "|select inttri.NPROGINT, PIATRI.ID from INTTRI, PIATRI "
            + "where inttri.conint = #INTTRI.CONINT# and inttri.contri = #INTTRI.CONTRI# AND PIATRI.CONTRI = INTTRI.CONTRI",

        // Interventi Forniture Servizi
        "INTTRIFS|Nuovo Acquisto del Programma {0} |Acquisto n.{0} del Programma {1}"
                + "|select PIATRI.ID from PIATRI where PIATRI.CONTRI = #CONTRI#"
                + "|select inttri.conint, PIATRI.ID from INTTRI, PIATRI "
                + "where inttri.conint = #INTTRI.CONINT# and inttri.contri = #INTTRI.CONTRI# AND PIATRI.CONTRI = INTTRI.CONTRI",

        // Stazione appaltanti
        "UFFINT|Nuova Stazione Appaltante | Stazione Appaltante n.{0}"
            + "||select CODEIN  from UFFINT "
            + "where codein = #UFFINT.CODEIN#",

        // Fabbisogni
            "FABBISOGNI|Nuova Proposta |Proposta n.{0}"
            + "||select CONINT from INTTRI where contri=0 and conint = #INTTRI.CONINT#",



    };
  }

  @Override
  protected String getTitleInserimento(PageContext pageContext, String table) {
    return null;
  }

  @Override
  protected String getTitleModifica(PageContext pageContext, String table,
      String keys) {
    return null;
  }
}
