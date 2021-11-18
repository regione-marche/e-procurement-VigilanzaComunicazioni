package it.eldasoft.w9.bl.delegate;

import it.eldasoft.gene.db.datautils.DataColumn;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.sql.sqlparser.JdbcParametro;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.w9.common.CostantiFlussi;
import it.eldasoft.w9.common.StatoComunicazione;
import it.eldasoft.w9.common.CostantiW9;
import it.eldasoft.w9.utils.UtilitySITAT;
import it.toscana.rete.rfc.sitat.types.FaseEstesaType;
import it.toscana.rete.rfc.sitat.types.RichiestaRichiestaRispostaSincronaAperturaCantiereDocument;
import it.toscana.rete.rfc.sitat.types.Tab171Type;
import it.toscana.rete.rfc.sitat.types.Tab172Type;
import it.toscana.rete.rfc.sitat.types.Tab173Type;
import it.toscana.rete.rfc.sitat.types.Tab17Type;

import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

/**
 * ConcreteHandler del Design Pattern "Chain of Responsibility".<br>
 * E' l'handler per la gestione della scheda cantiere
 * 
 * @author Stefano.Sabbadin - Eldasoft S.p.A. Treviso
 */
public class AperturaCantiereHandler extends AbstractRequestHandler {

  Logger logger = Logger.getLogger(AperturaCantiereHandler.class);

  @Override
  protected String getNomeFlusso() {
    return "Scheda Cantiere";
  }

  @Override
  protected String getMainTagRequest() {
    return CostantiFlussi.MAIN_TAG_RICHIESTA_APERTURA_CANTIERE;
  }

  @Override
  protected XmlObject getXMLDocument(String xmlEvento) throws XmlException {
    return RichiestaRichiestaRispostaSincronaAperturaCantiereDocument.Factory.parse(xmlEvento);
  }

  @Override
  protected boolean isTest(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTest();
  }

  @Override
  protected FaseEstesaType getFaseInvio(XmlObject documento) {
    RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
    return doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();
  }

  @Override
  protected void managePrimoInvio(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di primo invio di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di fase scheda cantiere di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di primo invio scheda cantiere per lotto non aggiudicato in DB cig ("
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di fase scheda cantiere per un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.isFaseAbilitata(fase, CostantiW9.APERTURA_CANTIERE)) {
    	logger.error("La richiesta di primo invio scheda cantiere per lotto non aggiudicato in DB cig ("
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di fase scheda cantiere per un lotto non aggiudicato in banca dati");
      continua = false;
    }
    
    if (continua && this.existsFase(fase, CostantiW9.APERTURA_CANTIERE)) {
      logger.error("La richiesta di primo invio di una scheda cantiere gia' presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di fase scheda cantiere gia' presente in banca dati");
      logger.error("La richiesta di primo invio scheda cantiere per lotto non aggiudicato in DB cig ("
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Primo invio di fase scheda cantiere per un lotto non aggiudicato in banca dati");
      continua = false;
    }
      
    // Warnings
    if (continua && !ignoreWarnings) {
    	if (! this.isFaseVisualizzabile(fase, CostantiW9.APERTURA_CANTIERE)) {
        logger.error("La richiesta di primo invio scheda cantiere non prevista");
        this.setEsito(datiAggiornamento, StatoComunicazione.STATO_WARNING.getStato(),
        		"Primo invio di scheda cantiere non prevista");
        continua = false;
      }
    }

    if (continua) {
    	this.insertDatiFlusso(doc, datiAggiornamento, true);
    }
  }

  private void insertDatiFlusso(
      RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc,
      DataColumnContainer datiAggiornamento, boolean primoInvio) throws SQLException,
      GestoreException {

    Tab17Type tab17 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab17();
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();

    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1]; 
    int numCant = fase.getW3NUM5();
    
    String pkUffint = this.getCodiceUfficioIntestatario(fase.getW9FLCFSA());
    
    this.updateW9Fasi(codGara, codLott, fase, datiAggiornamento, primoInvio);

    DataColumnContainer dccCantiere = new DataColumnContainer(new DataColumn[] {
        new DataColumn("W9CANT.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
        new DataColumn("W9CANT.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
        new DataColumn("W9CANT.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant)))
        });

    dccCantiere.addColumn("W9CANT.NUM_APPA", JdbcParametro.TIPO_NUMERICO, new Long(fase.getW9NUMAPPA()));
    
    dccCantiere.addColumn("W9CANT.DINIZ", JdbcParametro.TIPO_DATA, tab17.getW3DINIZCA().getTime());

    dccCantiere.addColumn("W9CANT.DLAV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3DLAVCA()));
    dccCantiere.addColumn("W9CANT.INDCAN", JdbcParametro.TIPO_TESTO, tab17.getW3INDCAN());
   
    if (tab17.isSetW9CATIPOPERA()) {
      dccCantiere.addColumn("W9CANT.TIPOPERA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPOPERA().toString()));
    }
    
    if (tab17.isSetW9CATIPINTER()) {
      dccCantiere.addColumn("W9CANT.TIPINTERV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPINTER().toString()));
    }
    
    if (tab17.isSetW9CATIPCOSTR()) {
      dccCantiere.addColumn("W9CANT.TIPCOSTR", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CATIPCOSTR().toString()));
    }
    
    if (tab17.isSetW3INFRA()) {
    	dccCantiere.addColumn("W9CANT.INFRA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3INFRA()));
    }
    if (tab17.isSetW3INFRDA()) {
    	dccCantiere.addColumn("W9CANT.INFRDA", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW3INFRDA()));
    }
    if (tab17.isSetW3INFRDE()) {
    	dccCantiere.addColumn("W9CANT.INFRDE", JdbcParametro.TIPO_TESTO, tab17.getW3INFRDE());
    }
    if (tab17.isSetW3MAILRIC()) {
    	dccCantiere.addColumn("W9CANT.MAILRIC", JdbcParametro.TIPO_TESTO, tab17.getW3MAILRIC());
    }
    if (tab17.isSetW9CACIVICO()) {
      dccCantiere.addColumn("W9CANT.CIVICO", JdbcParametro.TIPO_TESTO, tab17.getW9CACIVICO());
    }
    dccCantiere.addColumn("W9CANT.COMUNE", JdbcParametro.TIPO_TESTO, tab17.getW9CACOMUNE());
    dccCantiere.addColumn("W9CANT.PROV", JdbcParametro.TIPO_TESTO, tab17.getW9CAPROV());
    if (tab17.isSetW9CACOORDX()) {
      dccCantiere.addColumn("W9CANT.COORD_X", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CACOORDX()));
    }
    if (tab17.isSetW9CACOORDY()) {
      dccCantiere.addColumn("W9CANT.COORD_Y", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CACOORDY()));
    }
    if (tab17.isSetW9CALATIT()) {
      dccCantiere.addColumn("W9CANT.LATIT", JdbcParametro.TIPO_DECIMALE, new Double(tab17.getW9CALATIT()));
    }
    if (tab17.isSetW9CALONGIT()) {
      dccCantiere.addColumn("W9CANT.LONGIT", JdbcParametro.TIPO_DECIMALE, new Double(tab17.getW9CALONGIT()));
    }
    if (tab17.isSetW9CANUMLAV()) {
      dccCantiere.addColumn("W9CANT.NUMLAV", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CANUMLAV()));
    }
    if (tab17.isSetW9CANUMIMP()) {
      dccCantiere.addColumn("W9CANT.NUMIMP", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CANUMIMP()));
    }
    if (tab17.isSetW9CALAVAUT()) {
      dccCantiere.addColumn("W9CANT.LAVAUT", JdbcParametro.TIPO_NUMERICO, new Long(tab17.getW9CALAVAUT()));
    }
    
    if (primoInvio) {
      dccCantiere.addColumn("W9CANT.VERSNOT", JdbcParametro.TIPO_NUMERICO, new Long(0));
    }
    
    
    Tab171Type[] arrayTab171 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab171Array();
    if (arrayTab171 != null && arrayTab171.length > 0) {
      dccCantiere.addColumn("W9CANT.DAEXPORT", JdbcParametro.TIPO_TESTO, "1");
    } else {
      dccCantiere.addColumn("W9CANT.DAEXPORT", JdbcParametro.TIPO_TESTO, "2");
    }
    dccCantiere.insert("W9CANT", this.sqlManager);

    // W9CANTDEST
    if (arrayTab171 != null && arrayTab171.length > 0) {
      for (int numTab171 = 0; numTab171 < arrayTab171.length; numTab171++) {
        Long tipoDestinatario = Long.parseLong(arrayTab171[numTab171].getW9DEDEST().toString());
        DataColumnContainer dccCantiereDest = new DataColumnContainer(new DataColumn[] {
            new DataColumn("W9CANTDEST.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9CANTDEST.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
            new DataColumn("W9CANTDEST.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
            new DataColumn("W9CANTDEST.DEST", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, tipoDestinatario))    
        });
        dccCantiereDest.insert("W9CANTDEST", this.sqlManager);
      }
    }
    
    // W9CANTIMP
    Tab172Type[] arrayTab172 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab172Array();
    if (arrayTab172 != null && arrayTab172.length > 0) {
      for (int numTab172 = 0; numTab172 < arrayTab172.length; numTab172++) {
        
        Tab172Type impresaCantiere = arrayTab172[numTab172];
        DataColumnContainer dccCantiereImpresa = new DataColumnContainer(new DataColumn[] {
            new DataColumn("W9CANTIMP.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9CANTIMP.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
            new DataColumn("W9CANTIMP.NUM_CANT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
            new DataColumn("W9CANTIMP.NUM_IMP", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numTab172+1)))    
        });
        if (impresaCantiere.isSetW9CICIPDURC()) {
          dccCantiereImpresa.addColumn("W9CANTIMP.CIPDURC", new JdbcParametro(JdbcParametro.TIPO_TESTO,
              impresaCantiere.getW9CICIPDURC()));
        }
        if (impresaCantiere.isSetW9CIPROTDURC()) {
          dccCantiereImpresa.addColumn("W9CANTIMP.PROTDURC", new JdbcParametro(JdbcParametro.TIPO_TESTO,
              impresaCantiere.getW9CIPROTDURC()));
        }
        if (impresaCantiere.isSetW9CIDATDURC()) {
          dccCantiereImpresa.addColumn("W9CANTIMP.DATDURC", new JdbcParametro(JdbcParametro.TIPO_DATA,
              impresaCantiere.getW9CIDATDURC().getTime()));
        }

        if (impresaCantiere.getArch3() != null) {
          String codiceImpresa = this.gestioneImpresa(impresaCantiere.getArch3(), pkUffint);
          if (StringUtils.isNotEmpty(codiceImpresa)) {
            dccCantiereImpresa.addColumn("W9CANTIMP.CODIMP", new JdbcParametro(JdbcParametro.TIPO_TESTO,
                codiceImpresa));
          }
        }
        dccCantiereImpresa.insert("W9CANTIMP", this.sqlManager);
      }
    }
    
    // W9INCA (SEZIONE = 'NP')
    Tab173Type[] arrayTab173 = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getTab173Array();
    if (arrayTab173 != null && arrayTab173.length > 0) {
      for (int numTab173 = 0; numTab173 < arrayTab173.length; numTab173++) {
        
        Tab173Type incaricatoProfessionale = arrayTab173[numTab173];
        DataColumnContainer dccIncaricoProfessionale = new DataColumnContainer(new DataColumn[] {
            new DataColumn("W9INCA.CODGARA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codGara)),
            new DataColumn("W9INCA.CODLOTT", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, codLott)),
            new DataColumn("W9INCA.NUM", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numCant))),
            new DataColumn("W9INCA.NUM_INCA", new JdbcParametro(JdbcParametro.TIPO_NUMERICO, new Long(numTab173+1))),
            new DataColumn("W9INCA.SEZIONE", new JdbcParametro(JdbcParametro.TIPO_TESTO, "NP"))
        });
        
        dccIncaricoProfessionale.addColumn("W9INCA.ID_RUOLO", new JdbcParametro(JdbcParametro.TIPO_NUMERICO,
            new Long(incaricatoProfessionale.getW3IDRUOLO().toString())));

        if (incaricatoProfessionale.getArch2() != null) {
          String codiceTecnico = this.getTecnico(incaricatoProfessionale.getArch2(), pkUffint);
          if (StringUtils.isNotEmpty(codiceTecnico)) {
            dccIncaricoProfessionale.addColumn("W9INCA.CODTEC", new JdbcParametro(JdbcParametro.TIPO_TESTO, codiceTecnico));
          }
        }
        dccIncaricoProfessionale.insert("W9INCA", this.sqlManager);
      }
    }
    
    this.setEsito(datiAggiornamento, StatoComunicazione.STATO_IMPORTATA.getStato(), null);
  }

  @Override
  protected void manageRettifica(XmlObject documento,
      DataColumnContainer datiAggiornamento, boolean ignoreWarnings)
      throws GestoreException, SQLException {
    RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc = (RichiestaRichiestaRispostaSincronaAperturaCantiereDocument) documento;
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();

    boolean continua = true;
    
    if (!this.existsLotto(fase)) {
      logger.error("La richiesta di rettifica scheda cantiere di un lotto non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di fase scheda cantiere di un lotto non presente in banca dati");
      continua = false;
    }

    if (continua && !this.existsAppalto(fase)) {
      logger.error("La richiesta di rettifica di un lotto non aggiudicato in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di fase scheda cantiere di un lotto non aggiudicato in banca dati");
      continua = false;
    }

    if (continua && !this.existsFase(fase, CostantiW9.APERTURA_CANTIERE)) {
      logger.error("La richiesta di rettifica  di una scheda cantiere non presente in DB (cig = "
          + fase.getW3FACIG() + ")");
      this.setEsito(datiAggiornamento, StatoComunicazione.STATO_ERRATA.getStato(),
          "Rettifica di fase scheda cantiere non presente in banca dati");
      continua = false;
    }
      
    if (continua) {
    	this.manageIntegrazioneORettifica(doc, datiAggiornamento);
    }
  }

  private void manageIntegrazioneORettifica(
      RichiestaRichiestaRispostaSincronaAperturaCantiereDocument doc,
      DataColumnContainer datiAggiornamento) throws SQLException,
      GestoreException {
    
    FaseEstesaType fase = doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase();
    
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    int numCant = fase.getW3NUM5();
    
    // Salvataggio dei valori dei campi IDSISPC e VERSNOT
    Vector< ? > datiW9CANT = this.sqlManager.getVector("select IDSISPC, VERSNOT from W9CANT where codgara = ? and codlott = ? and NUM_CANT=?",
        new Object[] { codGara, codLott, numCant });
    
    this.deleteDatiFlusso(doc.getRichiestaRichiestaRispostaSincronaAperturaCantiere().getFase());
    this.insertDatiFlusso(doc, datiAggiornamento, false);
    
    // Se i campi VERSNOT e IDSISPC sono valorizzati allora aggiorno l'occorrenza di W9CANt appena inserita
    if (datiW9CANT != null) {
      Long idSispc = (Long) ((JdbcParametro) datiW9CANT.get(0)).getValue();
      Long versioneNotifica = (Long) ((JdbcParametro) datiW9CANT.get(1)).getValue();
      
      String sqlUpdateW9CANT = "update W9CANT set CAMPO1=VALORE1, CAMPO2=VALORE2 where codgara = ? and codlott = ? and NUM_CANT=?"; 
      if (idSispc != null || versioneNotifica != null) {
    	  if (idSispc != null && versioneNotifica != null) {
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "CAMPO1", "IDSISPC");
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "VALORE1", idSispc.toString());
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "CAMPO2", "VERSNOT");
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "VALORE2", versioneNotifica.toString());
    	      } else if (idSispc == null && versioneNotifica != null) {
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "CAMPO1=VALORE1, CAMPO2", "VERSNOT");
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "VALORE2", versioneNotifica.toString());
    	      } else if (idSispc != null && versioneNotifica == null) {
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "CAMPO1", "IDSISPC");
    	        sqlUpdateW9CANT = StringUtils.replace(sqlUpdateW9CANT, "VALORE1, CAMPO2=VALORE2", idSispc.toString());
    	      }
    	      
    	      this.sqlManager.update(sqlUpdateW9CANT, new Object[] { codGara, codLott, numCant });
      }
    }
  }
  
  private void deleteDatiFlusso(FaseEstesaType fase) throws SQLException, GestoreException {
    Long[] longArray = this.getCodGaraCodLottByCIG(fase.getW3FACIG());
    Long codGara = longArray[0];
    Long codLott = longArray[1];
    int numCant = fase.getW3NUM5();
    
    Object[] sqlParam = new Object[] { codGara, codLott, numCant };
    
    this.sqlManager.update(
        "delete from w9cant where codgara = ? and codlott = ? and NUM_CANT=?", sqlParam, 1);
    this.sqlManager.update("delete from w9cantdest where codgara = ? and codlott = ? and NUM_CANT=?", sqlParam);
    this.sqlManager.update("delete from w9cantimp where codgara = ? and codlott = ? and NUM_CANT=?", sqlParam);
    this.sqlManager.update("delete from W9INCA where codgara = ? and codlott = ? and NUM=? and SEZIONE='NP'", sqlParam);
  }

}