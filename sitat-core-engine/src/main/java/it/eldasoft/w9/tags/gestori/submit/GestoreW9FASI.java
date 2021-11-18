package it.eldasoft.w9.tags.gestori.submit;

import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.web.struts.tags.gestori.AbstractGestoreChiaveNumerica;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

/**
 * Gestore dell'entita' W9FASI.
 */
public class GestoreW9FASI extends AbstractGestoreChiaveNumerica {

	public GestoreW9FASI() {
		super(false);
	}
	
  @Override
  public String[] getAltriCampiChiave() {
    return new String[] { "CODGARA", "CODLOTT", "FASE_ESECUZIONE" };
  }

  @Override
  public String getCampoNumericoChiave() {
    return "NUM";
  }

  @Override
  public String getEntita() {
    return "W9FASI";
  }

  @Override
  public void postDelete(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postInsert(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void postUpdate(DataColumnContainer arg0) throws GestoreException {

  }

  @Override
  public void preDelete(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

    // si ricava il valore delle chiavi primarie di W9FASI
    Long codgara = impl.getColumn("V_W9INVIIFASI.CODGARA").getValue().longValue();
    Long codlott = impl.getColumn("V_W9INVIIFASI.CODLOTT").getValue().longValue();
    //Long numappa = impl.getColumn("W9FASI.NUM_APPA").getValue().longValue();
    Long numeroFase = impl.getColumn("V_W9INVIIFASI.NUM").getValue().longValue();
    int faseEsecuzione = Integer.parseInt(impl.getColumn("V_W9INVIIFASI.FASE_ESECUZIONE").getValue().getStringValue());

    String condizioneWhereComune = " WHERE CODGARA=" + codgara + " AND CODLOTT=" + codlott;
    // vengono create le query in base al valore di FASE_ESECUZIONE
    String[] altreEntita = null;
    List<String> queryCancellazione = new ArrayList<String>();
    queryCancellazione.add("DELETE FROM W9FASI " + condizioneWhereComune + " and FASE_ESECUZIONE=" + faseEsecuzione 
    		+ " AND NUM=" + numeroFase );
    
    try {
      switch (faseEsecuzione) {
      case CostantiW9.COMUNICAZIONE_ESITO:
        queryCancellazione.add("DELETE FROM W9ESITO " + condizioneWhereComune);
        if (! UtilitySITAT.existsFase(codgara, codlott, null, CostantiW9.STIPULA_ACCORDO_QUADRO, this.sqlManager)) {
          queryCancellazione.add("DELETE FROM W9PUES " + condizioneWhereComune + " AND NUM_INIZ=" + numeroFase);
        }
        break;
      case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:
        queryCancellazione.add("DELETE FROM W9IMPRESE " + condizioneWhereComune);
        break;
      case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
      case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
        queryCancellazione.add("DELETE FROM W9APPA " + condizioneWhereComune
            + " AND NUM_APPA=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9INCA " + condizioneWhereComune
            + " AND NUM=" + numeroFase + " AND SEZIONE in ('PA', 'RA', 'RS', 'RE')");
        altreEntita = new String[] { "W9AGGI", "W9FINA", "W9REQU" };
        break;
      case CostantiW9.ADESIONE_ACCORDO_QUADRO:
        queryCancellazione.add("DELETE FROM W9APPA " + condizioneWhereComune
            + " AND NUM_APPA=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9INCA " + condizioneWhereComune
            + " AND NUM=" + numeroFase + " AND SEZIONE in ('RQ')");
        altreEntita = new String[] { "W9AGGI", "W9FINA", "W9REQU" };
        break;
      case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
      case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:  
      case CostantiW9.STIPULA_ACCORDO_QUADRO:
        queryCancellazione.add("DELETE FROM W9INIZ " + condizioneWhereComune
            + " AND NUM_INIZ=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9INCA " + condizioneWhereComune
            + " AND NUM=" + numeroFase + " AND SEZIONE IN ('IN','SI')");
        queryCancellazione.add("DELETE FROM W9MISSIC " + condizioneWhereComune
            + " AND NUM_INIZ=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9SIC " + condizioneWhereComune
            + " AND NUM_INIZ=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9PUES " + condizioneWhereComune + " AND NUM_INIZ=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9DOCFASE " + condizioneWhereComune + " AND FASE_ESECUZIONE = 993 AND NUM_FASE=" + numeroFase);
        break;
      case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
      case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:
        queryCancellazione.add("DELETE FROM W9AVAN " + condizioneWhereComune
            + " AND NUM_AVAN=" + numeroFase);
        break;
      case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
      case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
        queryCancellazione.add("DELETE FROM W9CONC " + condizioneWhereComune
            + " AND NUM_CONC=" + numeroFase);
        break;
      case CostantiW9.COLLAUDO_CONTRATTO:
        queryCancellazione.add("DELETE FROM W9COLL " + condizioneWhereComune
            + " AND NUM_COLL=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9INCA " + condizioneWhereComune
            + " AND NUM=" + numeroFase + " AND SEZIONE = 'CO'");
        break;
      case CostantiW9.SOSPENSIONE_CONTRATTO:
        queryCancellazione.add("DELETE FROM W9SOSP " + condizioneWhereComune
            + " AND NUM_SOSP=" + numeroFase);
        break;
      case CostantiW9.VARIANTE_CONTRATTO:
        queryCancellazione.add("DELETE FROM W9VARI " + condizioneWhereComune
            + " AND NUM_VARI=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9MOTI " + condizioneWhereComune
            + " AND NUM_VARI=" + numeroFase);
        break;
      case CostantiW9.ACCORDO_BONARIO:
        queryCancellazione.add("DELETE FROM W9ACCO " + condizioneWhereComune
            + " AND NUM_ACCO=" + numeroFase);
        break;
      case CostantiW9.SUBAPPALTO:
        queryCancellazione.add("DELETE FROM W9SUBA " + condizioneWhereComune
            + " AND NUM_SUBA=" + numeroFase);
        break;
      case CostantiW9.ISTANZA_RECESSO:
        queryCancellazione.add("DELETE FROM W9RITA " + condizioneWhereComune
            + " AND NUM_RITA=" + numeroFase);
        break;
      /*case CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA:
        queryCancellazione.add("DELETE FROM W9MISSIC " + condizioneWhereComune
            + " AND NUM_MISS=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9DOCFASE " + condizioneWhereComune
            + " AND FASE_ESECUZIONE = 993 AND NUM_FASE = " + numeroFase);
        break;*/
      case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:
        queryCancellazione.add("DELETE FROM W9INFOR " + condizioneWhereComune
            + " AND NUM_INFOR=" + numeroFase);
        break;
      case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:
        queryCancellazione.add("DELETE FROM W9INASIC " + condizioneWhereComune
            + " AND NUM_INAD=" + numeroFase);
        break;
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:
        queryCancellazione.add("DELETE FROM W9REGCON " + condizioneWhereComune
            + " AND NUM_REGO=" + numeroFase);
        break;
      case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:
        queryCancellazione.add("DELETE FROM W9TECPRO " + condizioneWhereComune
            + " AND NUM_TPRO=" + numeroFase);
        break;
      case CostantiW9.APERTURA_CANTIERE:
        queryCancellazione.add("DELETE FROM W9CANT " + condizioneWhereComune
            + " AND NUM_CANT=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9CANTDEST " + condizioneWhereComune
            + " AND NUM_CANT=" + numeroFase);
        queryCancellazione.add("DELETE FROM W9CANTIMP " + condizioneWhereComune
            + " AND NUM_CANT=" + numeroFase); 
        queryCancellazione.add("DELETE FROM W9INCA " + condizioneWhereComune
            + " AND NUM=" + numeroFase + " AND SEZIONE IN ('NP')");
        break;
        default:
          break;
      }

      if (altreEntita != null && altreEntita.length > 0) {
        for (int k = 0; k < altreEntita.length; k++) {
          queryCancellazione.add("DELETE FROM "
              + altreEntita[k] + condizioneWhereComune + " AND NUM_APPA = " + numeroFase);
        }
      }

      if (!queryCancellazione.isEmpty()) {
        for (int ii = 0; ii < queryCancellazione.size(); ii++) {
          this.getGeneManager().getSql().execute(queryCancellazione.get(ii));
        }
      }
    } catch (SQLException e) {
      throw new GestoreException("Errore durante la cancellazione di una Fase",
          "cancellazione.fase", e);
    }
  }

  @Override
  public void preUpdate(TransactionStatus arg0, DataColumnContainer arg1)
  throws GestoreException {
  }

  @Override
  public void preInsert(TransactionStatus status, DataColumnContainer impl)
  throws GestoreException {

    // Valorizzazione del campo NUM (campo numerico della chiave)
    Long numeroFase = null;
    if (impl.isColumn("W9FASI.NUM")) { 
      numeroFase = impl.getColumn("W9FASI.NUM").getValue().longValue();
    }
    if (numeroFase == null || (numeroFase != null && numeroFase < 1)) {
      super.preInsert(status, impl);
     }
    super.inserisci(status, impl);
  }

}