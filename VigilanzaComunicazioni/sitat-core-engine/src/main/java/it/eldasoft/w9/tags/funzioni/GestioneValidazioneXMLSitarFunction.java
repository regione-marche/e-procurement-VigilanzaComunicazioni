package it.eldasoft.w9.tags.funzioni;

import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.tags.utils.AbstractFunzioneTag;
import it.eldasoft.utils.spring.UtilitySpring;
import it.eldasoft.w9.bl.ExportSitarManager;
import it.eldasoft.w9.common.CostantiW9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class GestioneValidazioneXMLSitarFunction extends AbstractFunzioneTag {

  public GestioneValidazioneXMLSitarFunction() {
    super(2, new Class[] { PageContext.class, String.class});

  }

  public String function(PageContext pageContext, Object[] params) throws JspException {
	  ExportSitarManager manager = (ExportSitarManager) UtilitySpring.getBean(
        "ExportSitarManager", pageContext, ExportSitarManager.class);
	  SqlManager sqlManager = (SqlManager) UtilitySpring.getBean("sqlManager", pageContext, SqlManager.class);

    String chiave = (String)params[1];
    
    DataColumnContainer key = new DataColumnContainer(chiave);
    Long codgara = (Long) (key.getColumnsBySuffix("CODGARA", true))[0].getValue().getValue();
    Long codlott = (Long) (key.getColumnsBySuffix("CODLOTT", true))[0].getValue().getValue();
    Long progressivo = (Long) (key.getColumnsBySuffix("NUM", true))[0].getValue().getValue();
    Long fase = (Long) (key.getColumnsBySuffix("FASE_ESECUZIONE", true))[0].getValue().getValue();
    XmlObject document = null;
    HashMap<String, Object> infoValidazione = null;
    String nomeFile = "";
    try {
    	
    	String tipoAppalto = (String)sqlManager.getObject("SELECT TIPO_CONTRATTO FROM W9LOTT WHERE CODGARA = ? AND CODLOTT = ?", new Object[] {codgara, codlott});
    	switch (fase.intValue()) {
    	case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
    		nomeFile = "scheda_iec";
    		document = manager.getSchedaInizialeEsecuzioneContratto(codgara, codlott, progressivo);
    		break;
    	case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
            //verifico se chiusura anticipata
    		String chiusuraAnticipata = (String)sqlManager.getObject("SELECT INTANTIC FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ?", new Object[] {codgara, codlott, progressivo});
        	if (chiusuraAnticipata != null && chiusuraAnticipata.equals("1")) {
        		nomeFile = "scheda_chiusura_anticipata_contratto";
        		document = manager.getSchedaChiusuraAnticipataContratto(codgara, codlott, progressivo);
        	} else {
        		nomeFile = "scheda_conclusione_contratto";
        		document = manager.getSchedaConclusione(codgara, codlott, progressivo);
        	}
            break;
    	case CostantiW9.FASE_SEMPLIFICATA_CONCLUSIONE_CONTRATTO:
    		String chiusuraAnticipataSemplificata = (String)sqlManager.getObject("SELECT INTANTIC FROM W9CONC WHERE CODGARA = ? AND CODLOTT = ? AND NUM_CONC = ?", new Object[] {codgara, codlott, progressivo});
        	if (chiusuraAnticipataSemplificata != null && chiusuraAnticipataSemplificata.equals("1")) {
        		nomeFile = "scheda_chiusura_anticipata_contratto";
        		document = manager.getSchedaChiusuraAnticipataContratto(codgara, codlott, progressivo);
        	} else {
        		nomeFile = "scheda_ultimaz_lavori_sottosoglia";
        		document = manager.getSchedaConclusioneSemplificata(codgara, codlott, progressivo);
        	}
    		break;
    	case CostantiW9.COLLAUDO_CONTRATTO: 
    		nomeFile = "scheda_collaudo";
    		document = manager.getSchedaCollaudo(codgara, codlott, progressivo);
            break;
    	case CostantiW9.SOSPENSIONE_CONTRATTO: 
    		nomeFile = "scheda_sospensione";
    		document = manager.getSchedaSospensione(codgara, codlott, progressivo);
            break;
    	case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
    		nomeFile = "scheda_SAL";
    		document = manager.getSchedaEsecuzioneAvanzamento(codgara, codlott, progressivo);
            break;
    	case CostantiW9.ACCORDO_BONARIO:
    		nomeFile = "scheda_accordo_bonario";
    		document = manager.getSchedaAccordoBonario(codgara, codlott, progressivo);
            break;
    	case CostantiW9.SUBAPPALTO:
    		nomeFile = "scheda_subappalto";
    		document = manager.getSchedaSubappalto(codgara, codlott, progressivo);
            break;	
    	case CostantiW9.VARIANTE_CONTRATTO:
    		if (tipoAppalto.equals("L")) {
    			nomeFile = "scheda_variante_lav";
    			document = manager.getSchedaVarianteLav(codgara, codlott, progressivo);
    		} else {
    			nomeFile = "scheda_variante_serv_forn";
    			document = manager.getSchedaVarianteServForn(codgara, codlott, progressivo);
    		}
            break;
    	case CostantiW9.ADESIONE_ACCORDO_QUADRO:
    		if (tipoAppalto.equals("L")) {
    			nomeFile = "scheda_agg_lavori_sez_oss_adesione";
    			document = manager.getSchedaAdesioneLav(codgara, codlott, progressivo);
    		} else {
    			nomeFile = "scheda_agg_serv_forn_sez_oss_adesione";
    			document = manager.getSchedaAdesioneServForn(codgara, codlott, progressivo);
    		}
    		break;
    	case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
    		if (tipoAppalto.equals("L")) {
    			nomeFile = "scheda_agg_lavori_sez_oss";
    			document = manager.getSchedaAggiudicazioneLavori(codgara, codlott, progressivo);
    		} else {
    			nomeFile = "scheda_agg_serv_forn_sez_oss";
    			document = manager.getSchedaAggiudicazioneServForn(codgara, codlott, progressivo);
    		}
    		break;
    	case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
    		nomeFile = "scheda_aggiudicazione_sez_oss_rid";
    		document = manager.getSchedaAggiudicazioneSemplificata(codgara, codlott, progressivo);
            break;
    	case CostantiW9.ANAGRAFICA_GARA_LOTTI:
    		if (tipoAppalto.equals("L")) {
    			nomeFile = "scheda_bando_lavori";
    			document = manager.getSchedaBandoLavori(codgara, codlott, progressivo);
    		} else {
    			nomeFile = "scheda_bando_serv_forn";
    			document = manager.getSchedaBandoServForn(codgara, codlott, progressivo);
    		}
    		break;           
    	}
    	
    	infoValidazione = manager.validate(document, fase);
        
    	if ((Integer)infoValidazione.get("numeroErrori") == 0) {
    		ArrayList<Object> validationErrors = new ArrayList<Object>();
      	  	XmlOptions validationOptions = new XmlOptions();
      	  	validationOptions.setErrorListener(validationErrors);
      	      	
      		if (!document.validate(validationOptions)) {
      			String listaErroriValidazione = "";
      	      	Iterator< ? > iter = validationErrors.iterator();
      	      	while (iter.hasNext()) {
      	      		listaErroriValidazione += iter.next() + "\n";
      	      	}
      	      	infoValidazione.put("titolo", "Controlli validazione XML");
      	      	@SuppressWarnings("unchecked")
				List<Object> listaControlli = (List<Object>)infoValidazione.get("listaControlli");
      	      	int numeroErrori = (Integer)infoValidazione.get("numeroErrori");
      	      	numeroErrori ++;
      	      	infoValidazione.put("numeroErrori", new Integer(numeroErrori));
    			listaControlli.add(((new Object[] { "E", "Validazione", "Validazione XML", listaErroriValidazione })));
      		}
    	}
    } catch (Exception e) {
    	;
    	//String error = e.getMessage();
    } 
	
    pageContext.setAttribute("titolo", infoValidazione.get("titolo"));
    pageContext.setAttribute("listaControlli", infoValidazione.get("listaControlli"));
    pageContext.setAttribute("numeroWarning", infoValidazione.get("numeroWarning"));
    pageContext.setAttribute("numeroErrori", infoValidazione.get("numeroErrori"));

    if (infoValidazione.get("numeroErrori").toString().equals("0")) {
    	document.documentProperties().setEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
    						"attachment;filename="+ nomeFile + "_" + codgara + ".xml");
        try {
        	ServletOutputStream out = response.getOutputStream();
        	String documentString = document.toString();
        	documentString = documentString.replaceAll(nomeFile, "scheda");
        	documentString = documentString.replaceAll(" xmlns=\"xmlbeans.sitar.schede.w9.eldasoft.it\"", "");
        	byte[] theByteArray = documentString.getBytes();
        	out.write(theByteArray, 0, theByteArray.length);
        	out.flush();
        	out.close();
        } catch (Exception e) {
        	;
        }
    }
    return null;
  }

}
