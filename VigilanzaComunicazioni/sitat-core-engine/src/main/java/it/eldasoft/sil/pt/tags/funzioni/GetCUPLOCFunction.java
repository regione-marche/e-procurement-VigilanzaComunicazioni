package it.eldasoft.sil.pt.tags.funzioni;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

public class GetCUPLOCFunction extends AbstractFunzioneTag {

  public GetCUPLOCFunction() {
    super(1, new Class[] { String.class });
  }

  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
        pageContext, SqlManager.class);

    if (params[0] != null && !"".equals(params[0])) {

      Long id = new Long((String) params[0]);
      String selectCUPLOC = "select id, num, stato, regione, provincia, comune from cuploc where id = ? order by num";

      try {

        List<Object> datiCUPLOCEstesa = new Vector<Object>();

        List<?> datiCUPLOC = sqlManager.getListVector(selectCUPLOC,
            new Object[] { id });

        if (datiCUPLOC != null && datiCUPLOC.size() > 0) {

          for (int i = 0; i < datiCUPLOC.size(); i++) {

            Long num = (Long) SqlManager.getValueFromVectorParam(
                datiCUPLOC.get(i), 1).getValue();

            // Stato
            String stato = (String) SqlManager.getValueFromVectorParam(
                datiCUPLOC.get(i), 2).getValue();
            String descrizioneStato = (String) sqlManager.getObject(
                "select cupdesc from cuptab where cupcod = 'CU004' and cupcod1 = ?",
                new Object[] { stato });

            // Regione
            String regione = (String) SqlManager.getValueFromVectorParam(
                datiCUPLOC.get(i), 3).getValue();
            String descrizioneRegione = "TUTTE LE REGIONI";
            if (regione != null && !"-1".equals(regione)) {
              descrizioneRegione = (String) sqlManager.getObject(
                  "select tabdesc from tabsche where tabcod = 'S2003' and tabcod1 = '05' and tabcod2 = ?",
                  new Object[] { "0" + regione });
            }

            // Provincia
            String provincia = (String) SqlManager.getValueFromVectorParam(
                datiCUPLOC.get(i), 4).getValue();
            String descrizioneProvincia = "TUTTE LE PROVINCE";
            if (provincia != null && !"-1".equals(provincia)) {
              descrizioneProvincia = (String) sqlManager.getObject(
                  "select tabdesc from tabsche where tabcod = 'S2003' and tabcod1 = '07' and tabcod2 = ?",
                  new Object[] { "0" + provincia });
            }

            // Comune
            String comune = (String) SqlManager.getValueFromVectorParam(
                datiCUPLOC.get(i), 5).getValue();
            String descrizioneComune = "TUTTI I COMUNI";
            if (comune != null && !"-1".equals(comune)) {
              descrizioneComune = (String) sqlManager.getObject(
                  "select tabdesc from tabsche where tabcod = 'S2003' and tabcod1 = '09' and tabcod3 = ?",
                  new Object[] { comune });
            }

            datiCUPLOCEstesa.add(((Object) (new Object[] { id, num, stato,
                regione, provincia, comune, descrizioneStato,
                descrizioneRegione, descrizioneProvincia, descrizioneComune })));
          }

        }

        pageContext.setAttribute("datiCUPLOC", datiCUPLOCEstesa,
            PageContext.REQUEST_SCOPE);

      } catch (SQLException e) {
        throw new JspException("Errore nella lettura delle localizzazioni", e);
      }
    }

    return null;

  }

}
