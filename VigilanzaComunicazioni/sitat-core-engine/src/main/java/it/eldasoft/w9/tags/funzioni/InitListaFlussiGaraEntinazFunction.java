package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Inizializzazione lista flussi delle gare per enti nazionali o sotto i 40.000 euro.
 * 
 * @author Luca.Giacomazzo
 */
public class InitListaFlussiGaraEntinazFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public InitListaFlussiGaraEntinazFunction() {
    super(1, new Class[]{String.class });
  }

  /**
   * Implementazione del metodo AbstractFunzioneTag.function.
   * 
   * @param pageContext PageContext
   * @param params Object[]
   * @throws JspException JspException
   */
  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    // Funzione di inizializzazione di:
    // - lista dei flussi nella scheda di una gara;
    // - scheda di un flusso della gara (e non dei lotti);

    // Inizializzazione sqlManager
    SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);
    String idavgaraCigPresent = "true";
    boolean trovatoIdAvGaraCig = false;

    // preparazione variabile necessaria alla funzione
    String codGaraProg = "";

    // controllo dei parametri passati dalla jsp e smistamento dei dati nelle relative variabili interne
    if (params != null && params[0] != null && !params[0].toString().trim().equals("")) {
      DataColumnContainer key = new DataColumnContainer(params[0].toString());
      codGaraProg = (key.getColumnsBySuffix("CODGARA", true))[0].getValue().toString();

      if (codGaraProg == null || codGaraProg.trim().equals("")) {
        idavgaraCigPresent = "false";
        trovatoIdAvGaraCig = true;
      }
    } else {
      idavgaraCigPresent = "false";
      trovatoIdAvGaraCig = true;
    }

    if (!trovatoIdAvGaraCig) {
      try {
        String sqlGetIDAVGARA = "select idavgara from w9gara_entinaz where codgara = " + codGaraProg;
        String idavgara = (String) sqlManager.getObject(sqlGetIDAVGARA, new Object[] {});

        if (idavgara == null || idavgara.trim().equals("")) {
          idavgaraCigPresent = "idavgara";
          trovatoIdAvGaraCig = true;
        }

        if (!trovatoIdAvGaraCig) {
          String sqlGetCIGLOTTI = "select cig, codlott from w9lott_entinaz where codgara = "
            + codGaraProg;

          List< ? > listaCig = sqlManager.getListVector(sqlGetCIGLOTTI, new Object[]{});

          if (listaCig.size() > 0) {
            for (int i = 0; i < listaCig.size() && !trovatoIdAvGaraCig; i++) {

              List< ? > elemento = (List< ? >) listaCig.get(i);
              JdbcParametro par = (JdbcParametro) elemento.get(0);

              if (par.getStringValue().toString() == null
                  || par.getStringValue().toString().trim().equals("")) {

                idavgaraCigPresent = "cig";
                trovatoIdAvGaraCig = true;
              }
            }
          }
        }
      } catch (SQLException e) {
        throw new JspException("Errore nel controllo dei dati necessari all'invio del flusso selezionato.", e);
      }
    }

    pageContext.setAttribute("idavgaraCigPresent", "" + idavgaraCigPresent, PageContext.REQUEST_SCOPE);

    // Determinare se esistono dei flussi per stabilire se creare il primo invio dell'anagrafica
    // gara/lotti oppure la sua rettifica/integrazione o l'invio del bando o una sua rettifica/integrazione
    try {
      Long numeroFlussiGara = (Long) sqlManager.getObject(
          "select count(*) from w9flussi where key01=" + codGaraProg, new Object[]{});
      if (numeroFlussiGara != null) {
        pageContext.setAttribute("numeroFlussiGara", numeroFlussiGara);
      }
    } catch (SQLException e) {
      throw new JspException("Errore nel controllo dei dati necessari all'invio del flusso selezionato.", e);
    }

    return null;

  }

}
