/*
 * Created on 17/ott/2018
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */
package it.eldasoft.sil.pt.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

import java.sql.SQLException;

import org.springframework.transaction.TransactionStatus;


public class GestoreFABBISOGNI extends AbstractGestoreChiaveNumerica {

  
  @Override
  public String[] getAltriCampiChiave() {
    return new String[] {"CONTRI"};
  }

  @Override
  public String getCampoNumericoChiave() {
    return "CONINT";
  }

  @Override
  public String getEntita() {
    return "INTTRI";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {
    // TODO Auto-generated method stub

  }


  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {
  }


  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {
  }

  
  @Override
  public void afterInsertEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      try {
          Long lcontri = impl.getLong("INTTRI.CONTRI");
          Long lconint = impl.getLong("INTTRI.CONINT");
          String sCodein = impl.getString("FABBISOGNI.CODEIN");
          Long  lsyscon= impl.getLong("FABBISOGNI.SYSCON");
          Long lstato = impl.getLong("FABBISOGNI.STATO");
          
          String sSoggbud = impl.getString("FABBISOGNI.SOGGBUD");
          Long lTipostudio = impl.getLong("FABBISOGNI.TIPOSTUDIO");
          Long lTipoconv = impl.getLong("FABBISOGNI.TIPOCONV");
          Long lTiporappr = impl.getLong("FABBISOGNI.TIPORAPPR");


          DataColumnContainer dccFABBISOGNI = new DataColumnContainer(new DataColumn[] { new DataColumn("FABBISOGNI.CONINT", new JdbcParametro(
              JdbcParametro.TIPO_NUMERICO, lconint)) });
          dccFABBISOGNI.addColumn("FABBISOGNI.SYSCON", JdbcParametro.TIPO_NUMERICO, lsyscon);
          dccFABBISOGNI.addColumn("FABBISOGNI.STATO", JdbcParametro.TIPO_NUMERICO, lstato);
          dccFABBISOGNI.addColumn("FABBISOGNI.CODEIN", JdbcParametro.TIPO_TESTO, sCodein);
          
          dccFABBISOGNI.addColumn("FABBISOGNI.SOGGBUD", JdbcParametro.TIPO_TESTO, sSoggbud);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPOSTUDIO", JdbcParametro.TIPO_TESTO, lTipostudio);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPOCONV", JdbcParametro.TIPO_TESTO, lTipoconv);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPORAPPR", JdbcParametro.TIPO_TESTO, lTiporappr);
          
          dccFABBISOGNI.insert("FABBISOGNI", this.sqlManager);

      } catch (Exception e) {
          throw new GestoreException("Errore nell'inserimento della proposta", "insert.proposta", e);
      }
  }
  
  
  @Override
  public void afterDeleteEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      try {
          Long contri = impl.getLong("INTTRI.CONTRI");
          Long conint = impl.getLong("INTTRI.CONINT");
          
          this.sqlManager.update("delete from RIS_CAPITOLO where CONTRI=? and CONINT=?", new Object[] { contri, conint });

          this.sqlManager.update("delete from FABBISOGNI where CONINT=?", new Object[] { conint });

      } catch (Exception e) {
        throw new GestoreException("Errore durante la cancellazione di una proposta", "cancellazione.proposta", e);
      }
  }
  
  @Override
  public void afterUpdateEntita(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      // public void postUpdate(DataColumnContainer impl) throws
      // GestoreException {
      try {
          Long lconint = impl.getLong("INTTRI.CONINT");
          Long lstato = impl.getLong("FABBISOGNI.STATO");
          //Se lo stato vale 2 e Se si apportano modifiche la proposta sarà rimessa in stato "In compilazione" (stato = 1) 
          if(impl.isModifiedTable("INTTRI") && lstato == 2){
            lstato = new Long(1);
          }
          
          String sSoggbud = impl.getString("FABBISOGNI.SOGGBUD");
          Long lTipostudio = impl.getLong("FABBISOGNI.TIPOSTUDIO");
          Long lTipoconv = impl.getLong("FABBISOGNI.TIPOCONV");
          Long lTiporappr = impl.getLong("FABBISOGNI.TIPORAPPR");          
          
          DataColumnContainer dccFABBISOGNI = new DataColumnContainer(new DataColumn[] { new DataColumn("FABBISOGNI.CONINT", new JdbcParametro(
              JdbcParametro.TIPO_NUMERICO, lconint)) });
          dccFABBISOGNI.setOriginalValue("FABBISOGNI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, lconint));
          dccFABBISOGNI.getColumn("FABBISOGNI.CONINT").setChiave(true);

          dccFABBISOGNI.addColumn("FABBISOGNI.STATO", JdbcParametro.TIPO_NUMERICO, lstato);
          
          dccFABBISOGNI.addColumn("FABBISOGNI.SOGGBUD", JdbcParametro.TIPO_TESTO, sSoggbud);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPOSTUDIO", JdbcParametro.TIPO_TESTO, lTipostudio);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPOCONV", JdbcParametro.TIPO_TESTO, lTipoconv);
          dccFABBISOGNI.addColumn("FABBISOGNI.TIPORAPPR", JdbcParametro.TIPO_TESTO, lTiporappr);
          
          dccFABBISOGNI.update("FABBISOGNI", this.sqlManager);

      } catch (Exception e) {
          throw new GestoreException("Errore nell'aggiornamento della proposta", "aggiornamento.proposta", e);
      }
  }
  
  
  @Override
  public void preDelete(TransactionStatus arg0, DataColumnContainer arg1) throws GestoreException {

    //verificare bene questa condizione
    Long lconint = arg1.getLong("FABBISOGNI.CONINT");
//
    arg1.addColumn("INTTRI.CONTRI", JdbcParametro.TIPO_NUMERICO, new Long(0));
    arg1.setOriginalValue("INTTRI.CONTRI", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(0)));
    arg1.getColumn("INTTRI.CONTRI").setChiave(true);
    arg1.addColumn("INTTRI.CONINT", JdbcParametro.TIPO_NUMERICO, lconint);
    arg1.setOriginalValue("INTTRI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, lconint));
    arg1.getColumn("INTTRI.CONINT").setChiave(true);
//
//    arg1.setOriginalValue("FABBISOGNI.CONINT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, lconint));
//    arg1.getColumn("FABBISOGNI.CONINT").setChiave(true);

    //GeneManager geneOpere = this.getGeneManager();
    String sqlSelectCancellazione = "";

    try {

      String[] listaEntita = new String[] { "IMMTRAI" } ;

      for (int k = 0; k < listaEntita.length; k++) {
          sqlSelectCancellazione = "DELETE FROM " + listaEntita[k] + " WHERE CONTRI = ? AND CONINT = ?";
          this.sqlManager.update(sqlSelectCancellazione, new Object[] { new Long(0), lconint });
      }
      
    } catch (SQLException e) {
      throw new GestoreException("Errore durante la cancellazione di una proposta", "cancellazione.proposta", e);
    }

  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      // gestione schede multiple di IMMTRAI
      GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
      gest.setGeneManager(this.geneManager);

      Long lcontri = impl.getLong("INTTRI.CONTRI");
      boolean univocitaCui = false;

      try {
          //////////////////////////////////////////////////////////////////////////
//          gest.gestioneIMMTRAIdaINTTRI(this.getRequest(), status, impl, this.getServletContext());
          gest.gestioneIMMTRAIdaFABBISOGNI(this.getRequest(), status, impl, this.getServletContext());
          //////////////////////////////////////////////////////////////////////////
      } catch (Exception ec) {
          throw new GestoreException("Errore durante la determinazione dei CUI associati all'intervento", "cui.intervento", ec);
      }
      super.preInsert(status, impl);      

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer impl) throws GestoreException {
      // gestione schede multiple di IMMTRAI
      GestoreIMMTRAIMultiplo gest = new GestoreIMMTRAIMultiplo();
      gest.setGeneManager(this.geneManager);
      try {
          //////////////////////////////////////////////////////////////////////////
          //gest.gestioneIMMTRAIdaINTTRI(this.getRequest(), status, impl, this.getServletContext());
          gest.gestioneIMMTRAIdaFABBISOGNI(this.getRequest(), status, impl, this.getServletContext());
          //////////////////////////////////////////////////////////////////////////          
      } catch (Exception ec) {
          throw new GestoreException("Errore durante la determinazione dei CUI associati all'intervento", "cui.intervento", ec);
      }
  }
  
  
}
