package it.eldasoft.w9.tags.funzioni;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;

/**
 * Classe per l'estrazione del nome della direzione di appartenenza di un
 * utente dato il codice.
 * 
 */
public class GetDirUtenteFunction extends AbstractFunzioneTag {

  /**
   * Costruttore.
   */
  public GetDirUtenteFunction() {
    super(1, new Class[] { PageContext.class });
  }

  /**
   * Estrae la descrizione del tabellato a partire dal codice, se valorizzato.
   * 
   * @see it.eldasoft.gene.tags.utils.AbstractFunzioneTag#function(javax.servlet.jsp.PageContext,
   *      java.lang.Object[])
   */
  public String function(PageContext pageContext, Object[] params)
      throws JspException {
    String descrizione = "";

    ProfiloUtente profiloUtente = (ProfiloUtente) pageContext.getAttribute(
            CostantiGenerali.PROFILO_UTENTE_SESSIONE, PageContext.SESSION_SCOPE);
    
    descrizione = profiloUtente.getLivelloStd();
    return descrizione;
  }

}
