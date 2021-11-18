package it.eldasoft.w9.web.struts;

import it.eldasoft.gene.bl.GeneManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.commons.web.domain.CostantiGenerali;
import it.eldasoft.gene.commons.web.domain.ProfiloUtente;
import it.eldasoft.gene.commons.web.struts.ActionBaseNoOpzioni;
import it.eldasoft.gene.commons.web.struts.CostantiGeneraliStruts;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.W9Manager;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action per apertura della pagina di seleziona di una nuova fase.
 */
public class SelezionaNuovaFaseAction extends ActionBaseNoOpzioni {

  /** SqlManager. */
  private SqlManager    sqlManager;
  
  /** W9Manager. */
  private W9Manager     w9Manager;
  
  /** Logger di classe. */
  private static Logger logger = Logger.getLogger(SelezionaNuovaFaseAction.class);

  /** Estrazione del tabellato W3020 a meno di alcuni valori. */
  private static final String SQL_SELECT_FROM_TABELLATO    = "select tab1desc, tab1tip, tab1nord from tab1 where tab1cod = 'W3020' and tab1tip not in (999,992,991,990,989,988,983,13,901,982,981) and tab1nord > 0 order by tab1nord asc";
  /** Estrazione di TAB1TIP del tabellato W3020 a meno di alcuni valori. */
  private static final String SQL_SELECT_FROM_TABELLATO_ID = "select tab1tip from tab1 where tab1cod = 'W3020' and tab1tip not in (999,992,991,990,989,988,983,13,901,982,981) and tab1nord > 0 order by tab1nord asc";
  /** Estrazione dell'importo totale del lotto. */
  private static final String SQL_IMPORTO_LOTTO            = "select importo_tot from w9lott where codgara = ? and codlott = ?";
  /** Estrazione del campo DAEXPORT del lotto. */
  private static final String SQL_DAEXPORT                 = "select daexport from w9lott where codgara = ? and codlott = ?";

  /**
   * Set di sqlManager.
   * 
   * @param sqlManager SqlManager
   */
  public void setSqlManager(SqlManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  /**
   * Set di w9Manager.
   * 
   * @param w9Manager W9Manager
   */
  public void setW9Manager(W9Manager w9Manager) {
    this.w9Manager = w9Manager;
  }  
  
  @Override
  protected ActionForward runAction(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    String target = CostantiGeneraliStruts.FORWARD_OK;
    try {
      String chiave = (String) request.getAttribute("key");
      DataColumnContainer key = new DataColumnContainer(chiave);
      Long codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
      Long codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
      Long numappa = new Long(request.getSession().getAttribute("aggiudicazioneSelezionata").toString());
      boolean daexp = false;
      
      GeneManager geneManager = (GeneManager) UtilitySpring.getBean("geneManager",
          request.getSession().getServletContext(), GeneManager.class);
      String profilo = (String)request.getSession().getAttribute(CostantiGenerali.PROFILO_ATTIVO);
      
      List< ? > datiTabellatoList = this.sqlManager.getListVector(
          SQL_SELECT_FROM_TABELLATO, new Object[] {});
      List< ? > idTabellatoList = this.sqlManager.getListVector(
          SQL_SELECT_FROM_TABELLATO_ID, new Object[] {});
      List<String> enabledTabellatoList = new ArrayList<String>();
      List<String> visibleTabellatoList = new ArrayList<String>();

      Double importoLotto = (Double) this.sqlManager.getObject(SQL_IMPORTO_LOTTO, new Object[]{codgara, codlott});
      // Inizializzazione a fini di evitare il caso remoto in cui il campo W9LOTT.IMPORT_TOT sia null
      if (importoLotto == null) {
        importoLotto = new Double(0);
      }
      String daexport = (String) this.sqlManager.getObject(SQL_DAEXPORT, new Object[]{codgara, codlott});

      if (StringUtils.equals("1", daexport)) {
        daexp = true;
      }
      // Per la creazione delle fasi in Alice Vigilanza laddove è abilitato l'invio delle fasi a Simog non serve
      // controllare l'invio dell'anagrafica gara e la presenza della fase esito (A22)
      boolean invioSimogVigilanza = UtilitySITAT.isConfigurazioneVigilanza(geneManager, profilo); 

      if (invioSimogVigilanza) {
        daexp = false;
      }
      List< ? > datiTabellatoListOrdered = datiTabellatoList;
      List< ? > idTabellatoListOrdered = idTabellatoList;

      List<Object[]> datiTabellatoList1 = new ArrayList<Object[]>();
      for (int yy = 0; yy < datiTabellatoList.size(); yy++) {
        Vector< ? > vec1 = (Vector< ? >) datiTabellatoList.get(yy);
        Object[] obj1 = new Object[]{((JdbcParametro) vec1.get(0)).getValue(),
            ((JdbcParametro) vec1.get(1)).getValue(), ((JdbcParametro) vec1.get(2)).getValue()};
        datiTabellatoList1.add(obj1);
      }

      request.getSession().setAttribute("datiTabellatoList", datiTabellatoList1);
      request.getSession().setAttribute("idTabellatoList", idTabellatoListOrdered);
      
      // Verifico se l'utente attualmente collegato e' il RUP per il lotto in questione
      HttpSession sessione = request.getSession();
      String ufficioIntestatario = (String) sessione.getAttribute(CostantiGenerali.UFFICIO_INTESTATARIO_ATTIVO);
      ProfiloUtente profiloUtente = (ProfiloUtente) sessione.getAttribute(CostantiGenerali.PROFILO_UTENTE_SESSIONE);

      // Ciclo per determinare quali fra tutte le possibili fasi sono selezionabili
      for (int i = 0; i < datiTabellatoListOrdered.size(); i++) {
        List< ? > elemento = (List< ? >) idTabellatoListOrdered.get(i);
        int codiceFaseEvento = Integer.parseInt(elemento.get(0).toString());

        Long permessoUtente = null;
        if ("A".equals(profiloUtente.getAbilitazioneStd()) || "C".equals(profiloUtente.getAbilitazioneStd())) {
          permessoUtente =  new Long(5);
        } else {
          permessoUtente = this.w9Manager.getPermessoUtente(codgara, codlott, new Long(profiloUtente.getId()),
              ufficioIntestatario, codiceFaseEvento);
        }
        if (permessoUtente == null || (permessoUtente != null && permessoUtente < 4)) {
          visibleTabellatoList.add("0");
          enabledTabellatoList.add("0");
        } else {
          switch (codiceFaseEvento) {
          case CostantiW9.COMUNICAZIONE_ESITO: // A22
            if (numappa.longValue() == 1 && UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.COMUNICAZIONE_ESITO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.COMUNICAZIONE_ESITO)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.COMUNICAZIONE_ESITO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI:   //A24
          	if (numappa.longValue() == 1 && UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI, this.sqlManager)) {
	            visibleTabellatoList.add("1");
	            if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI)
	            		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.ELENCO_IMPRESE_INVITATE_PARTECIPANTI, this.sqlManager)) {
	              enabledTabellatoList.add("1");
	            } else {
	              enabledTabellatoList.add("0");
	            }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
          	}
            break;

          case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:  // A04
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE) 
              		&& !esistenzaW9APPA(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE, this.sqlManager)) {
                enabledTabellatoList.add("1");
                // Si controlla solo che la fase 'Aggiudicazione semplificata' non esista gia' e che non
                // esista un record in W9APPA, visto che per il l'anagrafica e bando non esistera' mai un
                // record in W9FASI e quindi non si puo' eseguire il esistenzaFase(codgara, codlott, CostantiW9.A03);
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
          
          case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:  // A05
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA) 
              		&& !esistenzaW9APPA(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA, this.sqlManager)) {
                // Si controlla solo che la fase 'Aggiudicazione sopra soglia' non esista gia' e che non
                // esista un record in W9APPA, visto che per il l'anagrafica e bando non esistera' mai un
                // record in W9FASI e quindi non si puo' eseguire il esistenzaFase(codgara, codlott, CostantiW9.A03);
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.ADESIONE_ACCORDO_QUADRO:  // A21
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.ADESIONE_ACCORDO_QUADRO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.ADESIONE_ACCORDO_QUADRO) 
              		&& !esistenzaW9APPA(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.ADESIONE_ACCORDO_QUADRO, this.sqlManager)) {
                enabledTabellatoList.add("1");
                // Si controlla solo che la fase 'Adesione accordo quadro' non esista gia' e che non
                // esista un record in W9APPA, visto che per il l'anagrafica e bando non esistera' mai un
                // record in W9FASI e quindi non si puo' eseguire il esistenzaFase(codgara, codlott, CostantiW9.A03);
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:  // A06
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA) 
              		&& !esistenzaW9INIZ(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO:  // A07
           	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO) 
              		&& !esistenzaW9INIZ(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_INIZIO_CONTRATTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.STIPULA_ACCORDO_QUADRO:  // A20
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.STIPULA_ACCORDO_QUADRO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.STIPULA_ACCORDO_QUADRO) 
              		&& !esistenzaW9INIZ(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.STIPULA_ACCORDO_QUADRO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:  // A08
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
              				CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

            
          case CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO:   // A25
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO, this.sqlManager)) {
              visibleTabellatoList.add("1");

              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
                    		CostantiW9.AVANZAMENTO_CONTRATTO_APPALTO_SEMPLIFICATO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:   // A09
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA) 
              		&& !esistenzaW9CONC(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:  // A10
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO) 
              		&& !esistenzaW9CONC(codgara, codlott, numappa)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.COLLAUDO_CONTRATTO:  // A11
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.COLLAUDO_CONTRATTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && !this.esistenzaFase(codgara, codlott, numappa, CostantiW9.COLLAUDO_CONTRATTO)
              		&& UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.COLLAUDO_CONTRATTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.SOSPENSIONE_CONTRATTO: // A12
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.SOSPENSIONE_CONTRATTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.SOSPENSIONE_CONTRATTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.VARIANTE_CONTRATTO:    // A13
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.VARIANTE_CONTRATTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.VARIANTE_CONTRATTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;
            
          case CostantiW9.ACCORDO_BONARIO:  // A14
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.ACCORDO_BONARIO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.ACCORDO_BONARIO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.SUBAPPALTO:  // A15
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.SUBAPPALTO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.SUBAPPALTO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.ISTANZA_RECESSO:  // A16
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, CostantiW9.ISTANZA_RECESSO, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, CostantiW9.ISTANZA_RECESSO, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA:  // B02
          	if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, 
          				CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
              			CostantiW9.ESITO_NEGATIVO_VERIFICA_TECNICO_PROFESSIONALE_IMPRESA_AGGIUDICATARIA, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA:   // B03
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, 
          				CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
            			CostantiW9.ESITO_NEGATIVO_VERIFICA_CONTRIBUTIVA_ASSICURATIVA, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          /*case CostantiW9.MISURE_AGGIUNTIVE_SICUREZZA:   // B04
            if (r && !saq && ea) {
              visibleTabellatoList.add("1");
              if (!daexp && ((!this.esistenzaFase(codgara, codlott, CostantiW9.B04)) && (this.esistenzaFase(codgara, codlott, CostantiW9.A05) || this.esistenzaFase(codgara, codlott, CostantiW9.A04) || this.esistenzaFase(codgara, codlott, CostantiW9.A21)))) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;*/

          case CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI:   // B06
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, 
          					CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
            					CostantiW9.INADEMPIENZE_SICUREZZA_REGOLARITA_LAVORI, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI:   // B07
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, 
      							CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
            					CostantiW9.SCHEDA_SEGNALAZIONI_INFORTUNI, this.sqlManager)) {
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          case CostantiW9.APERTURA_CANTIERE:   // B08
            if (UtilitySITAT.isFaseVisualizzabile(codgara, codlott, 
      							CostantiW9.APERTURA_CANTIERE, this.sqlManager)) {
              visibleTabellatoList.add("1");
              if (!daexp && UtilitySITAT.isFaseAbilitata(codgara, codlott, numappa, 
        							CostantiW9.APERTURA_CANTIERE, this.sqlManager)) {
                enabledTabellatoList.add("1");
              } else {
                enabledTabellatoList.add("0");
              }
            } else {
              visibleTabellatoList.add("0");
              enabledTabellatoList.add("0");
            }
            break;

          default:
            break;
          }
        }
      }

      request.setAttribute("visibleTabellatoList", visibleTabellatoList);
      request.setAttribute("enabledTabellatoList", enabledTabellatoList);
      //request.setAttribute("aggiudicazioneAttiva", numappa);
      
      if (daexp) {
        this.aggiungiMessaggio(request, "errors.creaNuovaFase.invioAnagraficaBando");
      }
      
    } catch (GestoreException ge) {
      logger.error("Errore nell\'elaborazione della lista di Fasi inseribili.", ge);
      aggiungiMessaggio(request, "errors.inserisciFase.recuperoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    } catch (SQLException e) {
      logger.error("Errore nell\'elaborazione della lista di Fasi inseribili.", e);
      aggiungiMessaggio(request, "errors.inserisciFase.recuperoDati");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    } catch (ArrayIndexOutOfBoundsException e) {
      logger.error(
          "Errore in uno o piu' tra i campi: Importo Totale, Art E1, Art E2, Tipo Contratto, "
          + "Manodopera/Posa in Opera. Impossibile eseguire i controlli previsti per inserire nuova fase.",
          e);
      aggiungiMessaggio(request, "errors.inserisciFase.valoriNulli");
      target = CostantiGeneraliStruts.FORWARD_ERRORE_GENERALE;
    }

    return mapping.findForward(target);
  }
  
  /**
   * Determina l'esistenze di una fase (codiceFase).
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param numappa codice dell'appalto
   * @param codiceFase Codice della fase
   * @return Ritorna true se la fase <i>codiceFase</i> esiste in DB, false altrimenti
   * @throws SQLException SQLException
   */
  private boolean esistenzaFase(Long codgara, Long codlott, Long numappa, int codiceFase) throws SQLException {
    boolean esiste = false;
    Long valore;
    try {
      valore = (Long) this.sqlManager.getObject(
          "select count(*) from w9fasi where codgara = ? and codlott = ? and fase_esecuzione = ? and num_appa = ?",
          new Object[]{codgara, codlott, new Long(codiceFase), numappa});
      if (valore != null && valore.longValue() > 0) {
        esiste = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nella verifica della pre-esistenza della fase", e);
      throw e;
    }
    return esiste;
  }
  
  /**
   * Determina l'esistenze di un record in W9APPA.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param numappa codice dell'appalto
   * @return Ritorna true se esiste un record in W9APPA, false altrimenti
   * @throws SQLException SQLException
   */
  private boolean esistenzaW9APPA(Long codgara, Long codlott, Long numappa) throws SQLException {
    boolean esiste = false;
    Long valore;
    try {
      valore = (Long) this.sqlManager.getObject(
          "select count(*) from w9appa where codgara = ? and codlott = ? and num_appa = ?",
          new Object[]{codgara, codlott, numappa});
      if (valore != null && valore.longValue() > 0) {
        esiste = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nella verifica della pre-esistenza nella W9APPA", e);
      throw e;
    }
    return esiste;
  }
  
  /**
   * Determina l'esistenze di un record in W9INIZ.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param numappa codice dell'appalto
   * @return Ritorna true se esiste un record in W9INIZ, false altrimenti
   * @throws SQLException SQLException
   */
  private boolean esistenzaW9INIZ(Long codgara, Long codlott, Long numappa) throws SQLException {
    boolean esiste = false;
    Long valore;
    try {
      valore = (Long) this.sqlManager.getObject(
          "select count(*) from w9iniz where codgara = ? and codlott = ? and num_appa = ?",
          new Object[]{codgara, codlott, numappa});
      if (valore != null && valore.longValue() > 0) {
        esiste = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nella verifica della pre-esistenza nella W9INIZ", e);
      throw e;
    }
    return esiste;
  }
  
  /**
   * Determina l'esistenze di un record in W9CONC.
   * 
   * @param codgara Codice della gara
   * @param codlott Codice del lotto
   * @param numappa codice dell'appalto
   * @return Ritorna true se esiste un record in W9CONC, false altrimenti
   * @throws SQLException SQLException
   */
  private boolean esistenzaW9CONC(Long codgara, Long codlott, Long numappa) throws SQLException {
    boolean esiste = false;
    Long valore;
    try {
      valore = (Long) this.sqlManager.getObject(
          "select count(*) from w9conc where codgara = ? and codlott = ? and num_appa = ?",
          new Object[]{codgara, codlott, numappa});
      if (valore != null && valore.longValue() > 0) {
        esiste = true;
      }
    } catch (SQLException e) {
      logger.error("Errore nella verifica della pre-esistenza nella W9CONC", e);
      throw e;
    }
    return esiste;
  }
}