package it.eldasoft.w9.dao.ibatis;

import it.eldasoft.commons.beans.ControlloBean;
import it.eldasoft.gene.commons.web.spring.SqlMapClientDaoSupportBase;
import it.eldasoft.utils.metadata.cache.DizionarioTabelle;
import it.eldasoft.utils.metadata.domain.Campo;
import it.eldasoft.utils.metadata.domain.Tabella;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

public class SqlMapTabControlliDao extends SqlMapClientDaoSupportBase {

  public List<ControlloBean> getListaControlli(String codiceApplicazione, String entita, String codiceFunzione)  throws DataAccessException {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("codapp", codiceApplicazione);
    params.put("entita", entita);
    params.put("codFunzione", codiceFunzione);
    return (List<ControlloBean>) this.getSqlMapClientTemplate().queryForList("selectListaControlliEntita", params);
  }
  
  public int getControllo(ControlloBean controlloBean, Object valoreCampoChiave1, Object valoreCampoChiave2,
          Object valoreCampoChiave3, Object valoreCampoChiave4, String valoreCampoChiave5) throws DataAccessException {
    DizionarioTabelle dizTabelle = DizionarioTabelle.getInstance();
    Tabella tabella = dizTabelle.getDaNomeTabella(controlloBean.getEntita());
    List<Campo> listaCampiChiave = tabella.getCampiKey();

    String psCondizioneCampiChiave = this.creaCondizioneWhereCampiChiave(listaCampiChiave);
    
    String condizioneCampiChiave = "";
    if (valoreCampoChiave1 != null && StringUtils.isNotEmpty(valoreCampoChiave1.toString())) {
      if (valoreCampoChiave1 instanceof String) {
        condizioneCampiChiave = StringUtils.replaceOnce(psCondizioneCampiChiave, "?", 
            "'".concat(valoreCampoChiave1.toString()).concat("'"));
      } else {
        condizioneCampiChiave = StringUtils.replaceOnce(psCondizioneCampiChiave, "?", valoreCampoChiave1.toString());
      }
    }
    if (valoreCampoChiave2 != null && StringUtils.isNotEmpty(valoreCampoChiave2.toString())) {
      if (valoreCampoChiave2 instanceof String) {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", 
            "'".concat(valoreCampoChiave2.toString()).concat("'"));
      } else {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", valoreCampoChiave2.toString());
      }
    }
    if (valoreCampoChiave3 != null && StringUtils.isNotEmpty(valoreCampoChiave3.toString())) {
      if (valoreCampoChiave3 instanceof String) {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", 
            "'".concat(valoreCampoChiave3.toString()).concat("'"));
      } else {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", valoreCampoChiave3.toString());
      }
    }
    if (valoreCampoChiave4 != null && StringUtils.isNotEmpty(valoreCampoChiave4.toString())) {
      if (valoreCampoChiave4 instanceof String) {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", 
            "'".concat(valoreCampoChiave4.toString()).concat("'"));
      } else {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", valoreCampoChiave4.toString());
      }
    }

    if (StringUtils.isNotEmpty(valoreCampoChiave5)) {
        condizioneCampiChiave = StringUtils.replaceOnce(condizioneCampiChiave, "?", 
            "'".concat(valoreCampoChiave5).concat("'"));
    }
    
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("entita", controlloBean.getEntita());
    params.put("condizione", controlloBean.getCondizione());
    params.put("condizioneCampiChiave", condizioneCampiChiave);
    Long result = (Long) this.getSqlMapClientTemplate().queryForObject("getControllo", params);

    return result.intValue();
  }
  
  /**
   * Costruisce la condizione di where per i campi indicati nella <i>listaCampiChiave</i> come preparedStatement.
   * <br>Esempio: T.CAMPO1 = ? and T.CAMPO2 = ?
   * 
   * @param listaCampiChiave Lista di oggetti <i>Campo</i>
   * @return Ritorna la condizione di where per i campi indicati nella lista come preparedStatement
   */
  private String creaCondizioneWhereCampiChiave(List<Campo> listaCampiChiave) {
    StringBuffer strBuffer = new StringBuffer("") ;
    for (int i=0; i < listaCampiChiave.size(); i++) {
      Campo campoChiave = listaCampiChiave.get(i);
      strBuffer.append("T.");
      strBuffer.append(campoChiave.getNomeCampo());
      strBuffer.append(" = ?");
      if (i < listaCampiChiave.size()-1) {
        strBuffer.append(" and ");
      }
    }
    return strBuffer.toString();
  }
  
}
