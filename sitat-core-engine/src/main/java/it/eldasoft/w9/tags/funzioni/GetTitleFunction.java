package it.eldasoft.w9.tags.funzioni;

import javax.servlet.jsp.PageContext;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.tags.functions.AbstractGetTitleFunction;
import it.eldasoft.utils.spring.SpringAppContext;
import it.eldasoft.utils.spring.UtilitySpring;
public class GetTitleFunction extends AbstractGetTitleFunction {

  public String[] initFunction() {
	
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager",
	      SpringAppContext.getServletContext(), SqlManager.class);

    return new String[] {
   
        // GARE
        "W9GARA|Nuova anagrafica gara|Gara {0}"
            + "||select W9GARA.IDAVGARA from W9GARA where W9GARA.CODGARA = #CODGARA#",

        // GARESC SmartCIG
        "W9GARASC|Nuova anagrafica gara|Gara {0}"
            + "||select W9LOTT.CIG from W9GARA join W9LOTT on W9GARA.CODGARA=W9LOTT.CODGARA where W9GARA.CODGARA = #CODGARA#",

        // Lista dei lotti della gara
        "W9GARA-LOTTI|Nuova anagrafica gara |Lista dei lotti della Gara n.{0}"
            + "||select CODGARA from W9GARA where codgara = #W9GARA.CODGARA#",

        // Lotti
        "W9LOTT|Nuovo Lotto |Lotto {0}"
            + "||select W9LOTT.CIG from W9LOTT where W9LOTT.CODLOTT = #CODLOTT# and W9LOTT.CODGARA=#CODGARA#",

        // Lista inserimento nuova fase
        "W9LOTT-FASI|Lotto {0} - Definizione di una nuova fase|"
            + "|select W9LOTT.CIG from W9LOTT where W9LOTT.CODLOTT = #CODLOTT# and W9LOTT.CODGARA=#CODGARA#|",

        // Scheda fase di aggiudicazione/affidamento (A05)
        //"W9APPA-SOPRA|Nuova Fase di aggiudicazione/affidamento |Fase di aggiudicazione/affidamento||",

        // Scheda fase semplificata di aggiudicazione/affidamento (A04)
        //"W9APPA-SOTTO|Nuova Fase semplificata di aggiudicazione/affidamento |Fase semplificata di aggiudicazione/affidamento||",
        
        // Scheda cantiere
        //"W9CANT|Nuova scheda Apertura del cantiere |Apertura del cantiere||",

        // Fase di conclusione del contratto (A09)
        //"W9CONC|Nuova Fase di conclusione del contratto |Fase di conclusione del contratto||",

        // Fase semplificata di conclusione del contratto (A10)
        //"W9CONC|Nuova Fase semplificata di conclusione del contratto |Fase semplificata di conclusione del contratto||",
        
        // Fase di collaudo del contratto (A11)
        //"W9COLL|Nuova Fase di collaudo |Fase di collaudo||",

        // Sospensione e ripresa del contratto (A12)
        //"W9SOSP|Nuova Sospensione |Sospensione||",

        // Variante del contratto (A13)
        //"W9VARI|Nuova Variante |Variante||",

        // Accordi bonari del contratto (A14)
        //"W9ACCO|Nuovo Accordo bonario |Accordo bonario||",

        // Subappalti del contratto (A15)
        //"W9SUBA|Nuovo Subappalto |Subappalto||",

        // Istanza di recesso (A16)
        //"W9RITA|Nuova Istanza di recesso |Istanza di recesso||",

        // Misure aggiuntive e migliorative sicurezza (B04)
        //"W9MISSIC|Nuova scheda Misure aggiuntive per la sicurezza|Misure aggiuntive per la sicurezza||",

        // Scheda segnalazione infortuni (B07)
        //"W9INFOR|Nuova Scheda segnalazione infortuni sul cantiere |Scheda segnalazione infortuni sul cantiere||",

        // Fase iniziale di esecuzione del contratto (A06)
        //"W9INIZ-SOPRA|Nuova Fase iniziale di esecuzione del contratto |Fase iniziale di esecuzione del contratto||",

        // Fase semplificata iniziale di esecuzione del contratto (A06)
        //"W9INIZ-SOTTO|Nuova Fase semplificata iniziale di esecuzione del contratto |Fase semplificata iniziale di esecuzione del contratto||",
        
        // Fase di esecuzione ed avanzamento del contratto (A08)
        //"W9AVAN|Nuova Fase di esecuzione ed avanzamento del contratto |Fase di esecuzione ed avanzamento del contratto||",

        // Inadempienze predisposizioni sicurezza e regolarita' lavoro (B06)
        //"W9INASIC|Nuova Scheda Inadempienze sicurezza |Scheda Inadempienze sicurezza ||",

        // Esito negativo verifica idoneita' tecnico-professionale dell’impresa
        // aggiudicataria (B02)
        //"W9TECPRO|Nuovo Esito negativo idoneita' tecnico professionale |Esito negativo idoneita' tecnico professionale ||",

        // Centro di Costi
        "CENTRICOSTO|Nuovo Centro di Costo| Centro di Costo n.{0}"
            + "||select IDCENTRO  from CENTRICOSTO where idcentro = #CENTRICOSTO.IDCENTRO#",

        // UFFICI
        "UFFICI|Nuovo Ufficio| Ufficio n.{0}"
            + "||select ID from UFFICI where id = #UFFICI.ID#",

        // Stazione appaltanti
        "UFFINT|Nuova Stazione Appaltante | Stazione Appaltante n.{0}"
            + "||select CODEIN  from UFFINT where codein = #UFFINT.CODEIN#",

        // Flussi
        "W9FLUSSI|Nuovo Invio | Invio {0}"
            + "||select IDFLUSSO  from W9FLUSSI "
            + "where idflusso = #W9FLUSSI.IDFLUSSO#",

        // Esito negativo verifica regolarita' contributiva ed assicurativa (B03)
        //"W9REGCON|Nuova Scheda irregolarita' contributiva ed assicurativa |Scheda irregolarita' contributiva ed assicurativa||",

        // Fase iniziale di Stipula accordo quadro (A20)
        //"W9INIZA20|Nuova Fase di Stipula accordo quadro |Fase di Stipula accordo quadro||",
                
        // Fase di adesione accordo quadro (A21)
        //"W9APPAA21|Nuova Fase di adesione accordo quadro |Fase di adesione accordo quadro||",
            
        // Fase di comunicazione esito (A22)
        //"W9ESITO|Nuova Fase di comunicazione esito |Fase di comunicazione esito||",

        // Fase Gare per enti nazionali
        "W9GARA_ENTINAZ|Nuova anagrafica gara|Gara {0}"
            + "||select " + sqlManager.getDBFunction("substr",new String[] {"W9GARA_ENTINAZ.OGGETTO","1","50"}) + " from W9GARA_ENTINAZ where W9GARA_ENTINAZ.CODGARA = #CODGARA#"
		
    };
  }

  protected String getTitleInserimento(PageContext pageContext, String table) {
    return null;
  }

  protected String getTitleModifica(PageContext pageContext, String table,
      String keys) {
    return null;
  }
}
