package it.eldasoft.sil.pt.tags.funzioni;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.sil.pt.bl.ControlloCpv;

public class VerificaRangeCpvFunction extends AbstractFunzioneTag {

  public VerificaRangeCpvFunction() {
    super(1, new Class[] { Object.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {

    String appartieneRange = "false";
    if (params[0] != null) {
      String valoreCpv = params[0].toString();
      ArrayList<String[]> listaControlli = new ArrayList<String[]>();
      listaControlli.add(new String[] { "50100000-6:50884000-5",
          "!50310000-1:50324200-4", "!50116510-9", "!50190000-3",
          "!50229000-6", "51000000-9:51900000-1" });
      listaControlli.add(new String[] { "60100000-9:60183000-4", "!60160000-7",
          "!60161000-4", "!6022000-6", "64120000-3:64121200-2" });
      listaControlli.add(new String[] { "60410000-5:60424120-3", "!60411000-2",
          "!60421000-5", "60500000-3", "60440000-4:60445000-9" });
      listaControlli.add(new String[] { "60160000-7", "60161000-4",
          "60411000-2", "60421000-5" });
      listaControlli.add(new String[] { "64200000-8:64228200-2", "72318000-7",
          "72700000-7:72720000-3" });
      listaControlli.add(new String[] { "66100000-1:66620000-3" });
      listaControlli.add(new String[] { "50310000-1:50324200-4",
          "72000000-5:72920000-5", "!72318000-7", "!72700000-7:72720000-3",
          "79342410-4" });
      listaControlli.add(new String[] { "73000000-2:73436000-7", "!73200000-4",
          "!73210000-7", "!73220000-0" });
      listaControlli.add(new String[] { "79210000-9:79223000-3" });
      listaControlli.add(new String[] { "79300000-7:79330000-6",
          "79342310-9:79342311-6" });
      listaControlli.add(new String[] { "73200000-4:73220000-0", "79342000-3",
          "79432100-4", "79432300-6", "79432320-2", "79432321-9", "79910000-6",
          "79910000-7", "98362000-8" });
      listaControlli.add(new String[] { "71000000-8:71900000-7", "!71550000-8",
          "79994000-8" });
      listaControlli.add(new String[] { "79341000-6:79342200-5", "!79342000-3",
          "79342100-4" });
      listaControlli.add(new String[] { "70300000-4:70340000-6",
          "90900000-6:90924000-0" });
      listaControlli.add(new String[] { "79800000-2:79824000-6",
          "79970000-6:79980000-7" });
      listaControlli.add(new String[] { "90400000-1:90743200-9", "!90712200-3",
          "90910000-9:90920000-2", "50190000-3", "50229000-6", "50243000-0" });
      listaControlli.add(new String[] { "55000000-0:55524000-9",
          "98340000-8:98341100-6" });
      listaControlli.add(new String[] { "60200000-0:60220000-6" });
      listaControlli.add(new String[] { "60600000-4:60653000-0",
          "63727000-1:63727200-3", "63000000-9:63734000-3", "!63711200-8",
          "!63712700-0", "!63712710-3", "!63727000-1:63727200-3", "98361000-1" });
      listaControlli.add(new String[] { "79100000-5:79140000-7" });
      listaControlli.add(new String[] { "79600000-0:79635000-4", "!79611000-0",
          "!79632000-3", "!79633000-0", "98500000-8:98514000-9",
          "79700000-1:79723000-8" });
      listaControlli.add(new String[] { "80100000-5:80660000-8", "!80533000-9",
          "!80533100-0", "!80533200-1" });
      listaControlli.add(new String[] { "85000000-9:85323000-9", "79611000-0",
          "!85321000-5", "!85322000-2" });
      listaControlli.add(new String[] { "79995000-5:79995200-7",
          "92000000-1:92700000-8", "!92230000-2", "!92231000-9", "!92232000-6" });
      String[] rangeCpv = new String[] {};

      boolean trovato = false;
      for (int i = 0; i < listaControlli.size(); i++) {
        rangeCpv = (String[]) listaControlli.get(i);
        trovato = ControlloCpv.controlloCpv(valoreCpv, rangeCpv);
        if (trovato) {
          appartieneRange = "true";
          break;
        }
      }
    }
    return appartieneRange;
  }

}
