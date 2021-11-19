package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.common.CostantiW9;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

public class GestioneValidazioneTitleFunction extends AbstractFunzioneTag {

  static Logger logger = Logger.getLogger(GestioneValidazioneTitleFunction.class);

  public GestioneValidazioneTitleFunction() {
    super(5, new Class[] {PageContext.class, String.class, String.class, String.class, String.class});
  }

  @Override
  public String function(PageContext pageContext, Object params[]) throws JspException {
    SqlManager sqlManager = (SqlManager)UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    Long flusso = new Long((String) params[1]); // Tipo di flusso come da tabellato W3020

    Long codgara = null;
    Long codlott = null;
    // Long num = null;
    Long contri = null;

    String selectTitolo = null;
    List< ? > datiTitolo = null;
    
    String descrizione = "";

    try {

      switch (flusso.intValue()) {

      case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA :  // Fase aggiudicazione/affidamento (>150.000 euro)
      case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA: // Fase iniziale esecuzione contratto (>150.000 euro)
      case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:	// Fase esecuzione e avanzamento del contratto
      case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:	// Fase di conclusione del contratto (>150.000 euro)
      case CostantiW9.COLLAUDO_CONTRATTO:	// Fase di collaudo del contratto
      case CostantiW9.SOSPENSIONE_CONTRATTO:	// Sospensione del contratto
      case CostantiW9.VARIANTE_CONTRATTO:	// Variante del contratto
      case CostantiW9.ACCORDO_BONARIO:	// Accordi bonari
      case CostantiW9.SUBAPPALTO:	// Subappalti
      case CostantiW9.ISTANZA_RECESSO: // Istanza di recesso
      case CostantiW9.STIPULA_ACCORDO_QUADRO: // Stipula accordo quadro
      case CostantiW9.ADESIONE_ACCORDO_QUADRO: // Adesione accordo quadro
      case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI: // Elenco imprese invitate/partecipanti
      case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO: // Avanzamento contratto semplificato
      case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE: // Fase aggiudicazione/affidamento appalto (<150.000 euro)
      case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO: // Fase iniziale esecuzione contratto (<150.000 euro)
      case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO: // Fase di conclusione del contratto (<150.000 euro)
      //case 993: // Misure aggiuntive e migliorative sicurezza
      case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI: // Scheda segnalazione infortuni
      case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI: // Inadempienze predisposizioni sicurezza e regolarita' lavoro
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA: // Esito negativo verifica regolarita' contributiva ed assicurativa
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA: // Esito negativo verifica idoneita' tecnico-professionale dell'impresa aggiudicataria
      case CostantiW9.APERTURA_CANTIERE: // Apertura cantiere
      //case 999: // Scheda sicurezza
        codgara = new Long((String) params[2]);
        codlott = new Long((String) params[3]);
        // num = new Long((String) params[4]);

        selectTitolo = "select cig, oggetto from w9lott where codgara = ? and codlott = ?";
        datiTitolo = sqlManager.getVector(selectTitolo, new Object[] {codgara, codlott});
        if (datiTitolo != null && datiTitolo.size() > 0) {
          String cig = (String) SqlManager.getValueFromVectorParam(datiTitolo, 0).getValue();
          String oggetto = (String) SqlManager.getValueFromVectorParam(datiTitolo, 1).getValue();
          if (cig != null) {
            descrizione = cig + " - ";
          }
          if (oggetto != null) {
            descrizione += oggetto;
          }
        }
        break;

      case CostantiW9.ANAGRAFICA_GARA_LOTTI: // Anagrafica Gara e Lotto/i + CIG
      case CostantiW9.PUBBLICAZIONE_DOCUMENTI: // Pubblicazioni documenti
        codgara = new Long((String) params[2]);
        Long provDato = (Long) sqlManager.getObject(
                "select PROV_DATO from W9GARA where CODGARA=?",
                new Object[]{ codgara } );
        //se sto pubblicando un atto per una gara smartCIG
        if(new Long(4).equals(provDato)) {
        	selectTitolo = "select w9lott.cig from w9gara join w9lott on w9gara.codgara = w9lott.codgara where w9gara.codgara = ?";
        } else {
        	selectTitolo = "select idavgara from w9gara where codgara = ?";
        }
        descrizione = "Gara " + (String) sqlManager.getObject(selectTitolo, new Object[] {codgara});
        break;

      case CostantiW9.PUBBLICAZIONE_AVVISO: // Avviso
        break;

      case CostantiW9.PROGRAMMA_TRIENNALE_LAVORI: // Programma triennale ed elenco annuale Lavori
      case CostantiW9.PROGRAMMA_TRIENNALE_FORNITURE_SERVIZI: // Programma triennale ed elenco annuale Forniture e Servizi
        contri = new Long((String) params[2]);
        selectTitolo = "select id from piatri where contri = ?";
        descrizione = (String) sqlManager.getObject(selectTitolo, new Object[] {contri});
        break;

      default:
        break;
      }

    } catch (SQLException e) {
      throw new JspException("Errore nella selezione del titolo", e);
    }

    return descrizione;
  }

}


