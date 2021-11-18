package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiW9;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Function per determinare il permesso dell'utente loggato sulla gara/lotto. 
 * 
 * @author Luca.Giacomazzo
 */
public class GetPermessoUserFunction extends AbstractFunzioneTag {

  public GetPermessoUserFunction() {
    super(3, new Class[] { PageContext.class, String.class, String.class });
  }

  @Override
  public String function(PageContext pageContext, Object[] params) throws JspException {

    W9Manager w9Manager = (W9Manager) UtilitySpring.getBean("w9Manager",
        pageContext, W9Manager.class); 

    ProfiloUtente profiloUtente = (ProfiloUtente) pageContext.getSession().getAttribute(
        CostantiGenerali.PROFILO_UTENTE_SESSIONE);

    // Ruolo dell'utente nella gara in analisi
    Long permessoUtente = null;
    
    if ("A".equals(profiloUtente.getAbilitazioneStd()) || "C".equals(profiloUtente.getAbilitazioneStd())) {
      permessoUtente =  new Long(5);
    } else {
      try {
        String key = params[1].toString();
        String entita = params[2].toString();
    
        DataColumnContainer keys = new DataColumnContainer(key);
        
        String ufficioIntestatario = (String) pageContext.getSession().getAttribute(
            CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
        
        Long codgara = null;
        Long codlotto = null;
        int idScheda = -1;
        
        if (entita.equals("W9GARA")) {
          codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
          idScheda = CostantiW9.ANAGRAFICA_GARA_LOTTI;
          
        } else if (entita.equals("W9LOTT")) {
          codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
          codlotto = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
          idScheda = CostantiW9.ANAGRAFICA_GARA_LOTTI;
          
        } else if (StringUtils.containsIgnoreCase(entita, "W9FASI")) {
          codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
          codlotto = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
          idScheda = (keys.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().longValue().intValue();
          
        } else if (StringUtils.containsIgnoreCase(entita, "W9")) {
          
          codgara = (keys.getColumnsBySuffix("CODGARA", true))[0].getValue().longValue();
          codlotto = (keys.getColumnsBySuffix("CODLOTT", true))[0].getValue().longValue();
          
          if (entita.equals("W9SUBA")) {
            idScheda = CostantiW9.SUBAPPALTO;
          } else if (entita.equals("W9ACCO")) {
            idScheda = CostantiW9.ACCORDO_BONARIO;
          } else if (entita.equals("W9APPA-A04")) {
            idScheda = CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE;
          } else if (entita.equals("W9APPA-A05")) {
            idScheda = CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA;
          } else if (entita.equals("W9APPA-A21")) {
            idScheda = CostantiW9.ADESIONE_ACCORDO_QUADRO;
          } else if (entita.equals("W9AVAN")) {
            idScheda = CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA;
          } else if (entita.equals("W9AVAN-A25")) {
            idScheda = CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO;
          } else if (entita.equals("W9CANT")) {
            idScheda = CostantiW9.APERTURA_CANTIERE;
          } else if (entita.equals("W9COLL")) {
            idScheda = CostantiW9.COLLAUDO_CONTRATTO;
          } else if (entita.equals("W9CONC-A09")) {
            idScheda = CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA;
          } else if (entita.equals("W9CONC-A10")) {
            idScheda = CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO;
          } else if (entita.equals("W9ESITO")) {
            idScheda = CostantiW9.COMUNICAZIONE_ESITO;
          } else if (entita.equals("W9INFOR")) {
            idScheda = CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI;
          } else if (entita.equals("W9INIZ-A06")) {
            idScheda = CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA;
          } else if (entita.equals("W9INIZ-A07")) {
            idScheda = CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO;
          } else if (entita.equals("W9INIZ-A20")) {
            idScheda = CostantiW9.STIPULA_ACCORDO_QUADRO;
          } else if (entita.equals("W9RITA")) {
            idScheda = CostantiW9.ISTANZA_RECESSO;
          } else if (entita.equals("W9SOSP")) {
            idScheda = CostantiW9.SOSPENSIONE_CONTRATTO;
          } else if (entita.equals("W9VARI")) {
            idScheda = CostantiW9.VARIANTE_CONTRATTO;
          } else if (entita.equals("W9TECPRO")) {
            idScheda = CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA;
          } else if (entita.equals("W9INASIC")) {
            idScheda = CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI;
          } else if (entita.equals("W9REGCON")) {
              idScheda = CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA;
          } else if (entita.equals("W9INFOR")) {
              idScheda = CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI;
          //} else if (entita.equals("W9MISSIC")) {
          //  idScheda = CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA;
          }
        }
  
        if (idScheda != -1) {
          permessoUtente = w9Manager.getPermessoUtente(codgara, codlotto, new Long(profiloUtente.getId()),
              ufficioIntestatario, idScheda);
        }
      } catch (SQLException sqle) {
        throw new JspException(
            "Errore in fase di esecuzione della query per determinare il permesso dell'utente sulla gara/lotto in analisi",
            sqle);
      } catch (Exception exc) {
        throw new JspException(
            "Errore in fase di esecuzione della query per determinare il permesso dell'utente sulla gara/lotto in analisi",
            exc);
      }
    }
    
    if (permessoUtente != null && permessoUtente.longValue() > 0) {
      return permessoUtente.toString();
    } else {
      return null;
    }
  }

}
