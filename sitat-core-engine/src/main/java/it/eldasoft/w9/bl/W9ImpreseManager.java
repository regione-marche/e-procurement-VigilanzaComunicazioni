package it.eldasoft.w9.bl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.dao.vo.ImpresaPartecipante;

/**
 * Manager per la gestione delle operazioni sulla lista delle imprese invitate/partecipanti
 * 
 * @author Luca.Giacomazzo
 */
public class W9ImpreseManager {

  private static Logger logger = Logger.getLogger(W9ImpreseManager.class); 
  
  private SqlManager sqlManager;

  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }
  
  public List< ImpresaPartecipante > getListaW9Imprese(Long codiceGara, Long codiceLotto)
      throws SQLException, GestoreException {
    
    if (logger.isDebugEnabled()) {
      logger.debug("getListaW9Imprese: inizio metodo");
    }

    List< ImpresaPartecipante > listaW9Imprese = null;

    List< ? > listVectorW9Imprese = sqlManager.getListVector(
        "select w1.codgara, w1.codlott, w1.num_impr, w1.tipoagg, t1.tab1desc, w1.codimp, "   // COALESCE(i1.nomest, 'Impresa cancellata dall''archivio imprese')
        + " i1.nomest, w1.partecip, t2.tab1desc, w1.num_ragg, w1.ruolo, t3.tab1desc, "
        + " (select count(w2.num_ragg) from w9imprese w2 where w2.codgara=w1.codgara and w2.codlott=w1.codlott and w1.num_ragg=w2.num_ragg) as numeroImpr "
        + " from w9imprese w1 left outer join tab1 t3 on w1.ruolo=t3.tab1tip and t3.tab1cod='W3011' left outer join impr i1 on w1.codimp=i1.codimp, tab1 t1, tab1 t2 "
        + " where w1.codgara=? and w1.codlott=? and w1.tipoagg=t1.tab1tip and t1.tab1cod='W3010' and  w1.partecip=t2.tab1tip and t2.tab1cod='W9015' "
        + " order by w1.num_ragg asc, w1.num_impr asc, w1.tipoagg asc",
        new Object[] { codiceGara, codiceLotto });
    
    if (listVectorW9Imprese != null && listVectorW9Imprese.size() > 0) {
      listaW9Imprese = new ArrayList< ImpresaPartecipante >();
      
      for (int i = 0; i < listVectorW9Imprese.size(); i++) {
        ImpresaPartecipante impresa = new ImpresaPartecipante();
        
        Vector< ? > impresaDataColumn = (Vector< ? >) listVectorW9Imprese.get(i);
        
        impresa.setCodiceGara( ((JdbcParametro) impresaDataColumn.get(0)).longValue() );
        impresa.setCodiceLotto( ((JdbcParametro) impresaDataColumn.get(1)).longValue() );
        impresa.setNumeroImpresa( ((JdbcParametro) impresaDataColumn.get(2)).longValue() );
        impresa.setTipoImpresa( ((JdbcParametro) impresaDataColumn.get(3)).longValue() );
        String temp = ((JdbcParametro) impresaDataColumn.get(4)).getStringValue();
        impresa.setDescTipoImpresa( StringUtils.substring(temp, 0, temp.indexOf("(") - 1) );
        impresa.setCodiceImpresa( ((JdbcParametro) impresaDataColumn.get(5)).getStringValue() );
        
        impresa.setPartecip( ((JdbcParametro) impresaDataColumn.get(7)).longValue() );
        impresa.setDescPartecip( ((JdbcParametro) impresaDataColumn.get(8)).getStringValue() );
        impresa.setNumRaggruppamento( ((JdbcParametro) impresaDataColumn.get(9)).longValue() );
        impresa.setRuolo( ((JdbcParametro) impresaDataColumn.get(10)).longValue() );
        
        impresa.setNumeroImpreseGruppo( ((JdbcParametro) impresaDataColumn.get(12)).longValue() );
        
        String identificativoImpresaString = "";
        if (impresa.getTipoImpresa().equals(new Long(3))) {
        	identificativoImpresaString = impresa.getCodiceImpresa().toString();
        } else {
        	identificativoImpresaString = "Gruppo_R" + impresa.getNumRaggruppamento().toString();
        }
        
        impresa.setIdentificativoImpresa(identificativoImpresaString);
        
        if (impresa.getNumeroImpreseGruppo() > 0) {
          StringBuffer bufferNomiImprese = new StringBuffer();
          StringBuffer bufferDescrRuoli = new StringBuffer();
          
          for (int j = 0; j < impresa.getNumeroImpreseGruppo() && (i+j) < listVectorW9Imprese.size() ; j++) {
            
            impresaDataColumn = (Vector< ? >) listVectorW9Imprese.get(i+j);
            
            JdbcParametro tempParam = (JdbcParametro) impresaDataColumn.get(6);
            if (tempParam.getValue() == null) {
              bufferNomiImprese.append(" <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>");
            } else {
              bufferNomiImprese.append(" - ");
              bufferNomiImprese.append("<a href=\"javascript:visualizzaImpresaScheda('" + ((JdbcParametro) impresaDataColumn.get(5)).getStringValue() + "')\">" + tempParam.getStringValue() + "</a>");
            }
            if (1 == impresa.getTipoImpresa().intValue()) {
              bufferNomiImprese.append(" (");
              bufferNomiImprese.append(((JdbcParametro) impresaDataColumn.get(11)).getStringValue());
              bufferNomiImprese.append(")");
            }
            bufferNomiImprese.append("<br>");
            bufferDescrRuoli.append(((JdbcParametro) impresaDataColumn.get(11)).getStringValue() );
            bufferDescrRuoli.append("<br>");
            impresa.setDescRuolo( ((JdbcParametro) impresaDataColumn.get(11)).getStringValue() );
          }

          impresa.setNomeEstesoImpr(bufferNomiImprese.substring(0, bufferNomiImprese.length()-4));
          impresa.setDescRuolo(bufferDescrRuoli.substring(0, bufferDescrRuoli.length()-4));
          
          i = i + impresa.getNumeroImpreseGruppo().intValue() - 1;
        } else {
          JdbcParametro tempParam = (JdbcParametro) impresaDataColumn.get(6); 
          if (tempParam.getValue() == null) {
            impresa.setNomeEstesoImpr(" <span style=\"color:red\"> Impresa non presente nell'archivio delle imprese</span>");
          } else { 
            //impresa.setNomeEstesoImpr( ((JdbcParametro) impresaDataColumn.get(6)).getStringValue() );// nome dell'impresa singola aggiungo link alla pagina dell'imrpesa
            impresa.setNomeEstesoImpr("<a href=\"javascript:visualizzaImpresaScheda('" + ((JdbcParametro) impresaDataColumn.get(5)).getStringValue() + "')\">" + ((JdbcParametro) impresaDataColumn.get(6)).getStringValue() + "</a>");
          }
        }
        listaW9Imprese.add(impresa);
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getListaW9Imprese: fine metodo");
    }

    return listaW9Imprese;
  }
  
  /** 
   * Delete di un gruppo di impresa.
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param numRaggr
   * @throws SQLException
   */
  public void deleteGruppoImprese(Long codiceGara, Long codiceLotto, Long numRaggr ) throws SQLException {
    this.sqlManager.update("delete from W9IMPRESE where CODGARA=? and CODLOTT=? and NUM_IMPR in (" +
    		"select NUM_IMPR from W9IMPRESE where CODGARA=? and CODLOTT=? and NUM_RAGG=?) ",
        new Object[] { codiceGara, codiceLotto, codiceGara, codiceLotto, numRaggr }) ;
    
    this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='1' WHERE CODGARA=? AND CODLOTT=? AND FASE_ESECUZIONE=101 AND NUM=1",
        new Object[] { codiceGara, codiceLotto}) ;
  }
  
  /**
   * Delete impresa singola.
   * 
   * @param codiceGara
   * @param codiceLotto
   * @param numImpr
   * @throws SQLException
   */
  public void deleteImpresaSingola(Long codiceGara, Long codiceLotto, Long numImpr) throws SQLException {
    this.sqlManager.update("delete from W9IMPRESE where CODGARA=? and CODLOTT=? and NUM_IMPR=? ",
        new Object[] { codiceGara, codiceLotto, numImpr }) ;
    this.sqlManager.update("UPDATE W9FASI SET DAEXPORT='1' WHERE CODGARA=? AND CODLOTT=? AND FASE_ESECUZIONE=101 AND NUM=1",
        new Object[] { codiceGara, codiceLotto}) ;
  }

  /**
   * Delete imprese selezionate dalla lista.
   * 
   * @param keys
   * @throws NumberFormatException
   * @throws SQLException
   */
  public void deleteImpreseSelez(String[] keys) throws NumberFormatException, SQLException {
   
    for (int i = 0; i < keys.length; i++) {
      String[] arrayChiave = keys[i].split(";");
      
      if (arrayChiave.length == 3) {
        this.deleteImpresaSingola(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[2]));
      } else if (arrayChiave.length == 4) {
        // Delete gruppo di impresa
        this.deleteGruppoImprese(
            Long.parseLong(arrayChiave[0]), Long.parseLong(arrayChiave[1]), Long.parseLong(arrayChiave[3]));
      }
    }
  }
  
}
