package it.eldasoft.sil.pt.tags.gestori.submit;

import org.springframework.transaction.TransactionStatus;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.DefaultGestoreEntitaChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;

public class GestoreCUPDATI extends AbstractGestoreChiaveNumerica {

  @Override
  public String[] getAltriCampiChiave() {
    return null;
  }

  @Override
  public String getCampoNumericoChiave() {
    return "ID";
  }

  @Override
  public String getEntita() {
    return "CUPDATI";
  }

  @Override
  public void postDelete(DataColumnContainer datiForm) throws GestoreException {

  }

  @Override
  public void postInsert(DataColumnContainer datiForm) throws GestoreException {

  }

  @Override
  public void postUpdate(DataColumnContainer datiForm) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {

    GeneManager geneManager = this.getGeneManager();
    Long id = datiForm.getLong("CUPDATI.ID");
    geneManager.deleteTabelle(new String[] { "CUPLOC" }, "ID = ?",
        new Object[] { id });

  }

  public void preInsert(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {

    super.preInsert(status, datiForm);
    
    ProfiloUtente profilo = (ProfiloUtente) this.getRequest().getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);
    datiForm.addColumn("CUPDATI.SYSCON", new Long(profilo.getId()));    

    this.gestioneCUPLOC(status, datiForm);

  }

  @Override
  public void preUpdate(TransactionStatus status, DataColumnContainer datiForm)
      throws GestoreException {

    this.gestioneCUPLOC(status, datiForm);

  }

  /**
   * 
   * @param status
   * @param datiForm
   * @throws GestoreException
   */
  private void gestioneCUPLOC(TransactionStatus status,
      DataColumnContainer datiForm) throws GestoreException {

    AbstractGestoreChiaveNumerica gestoremultiploCUPLOC = new DefaultGestoreEntitaChiaveNumerica(
        "CUPLOC", "NUM", new String[] { "ID" }, this.getRequest());
    this.gestisciAggiornamentiRecordSchedaMultipla(status, datiForm,
        gestoremultiploCUPLOC, "CUPLOC",
        new DataColumn[] { datiForm.getColumn("CUPDATI.ID") }, null);

  }

}
