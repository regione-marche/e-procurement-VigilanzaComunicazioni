/*
 * Created on 17/mar/2010
 *
 * Copyright (c) EldaSoft S.p.A.
 * Tutti i diritti sono riservati.
 *
 * Questo codice sorgente e' materiale confidenziale di proprieta' di EldaSoft S.p.A.
 * In quanto tale non puo' essere distribuito liberamente ne' utilizzato a meno di 
 * aver prima formalizzato un accordo specifico con EldaSoft.
 */

package it.eldasoft.sil.pt.bl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import it.eldasoft.gene.bl.LoginManager;
import it.eldasoft.gene.bl.SqlManager;
import it.eldasoft.gene.db.datautils.DataColumnContainer;
import it.eldasoft.gene.db.domain.admin.Account;
import it.eldasoft.gene.tags.utils.UtilityTags;
import it.eldasoft.gene.web.struts.tags.gestori.GestoreException;
import it.eldasoft.sil.pt.web.aliprog4.xmlbeans.PtDocument;
import it.eldasoft.sil.pt.ws.AliceProgrammi.EsitoInserisciProgramma;
import it.eldasoft.sil.pt.ws.xmlbeans.BeneImmobile;
import it.eldasoft.sil.pt.ws.xmlbeans.Intervento;
import it.eldasoft.sil.pt.ws.xmlbeans.InterventoAnnuale;
import it.eldasoft.sil.pt.ws.xmlbeans.ListaInterventiDocument;
import it.eldasoft.sil.pt.ws.xmlbeans.ListaProgrammiDocument;
import it.eldasoft.sil.pt.ws.xmlbeans.Programma;
import it.eldasoft.sil.pt.ws.xmlbeans.SchedaInterventoFornitureServizi;
import it.eldasoft.sil.pt.ws.xmlbeans.SchedaInterventoLavori;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;

/**
 * @author Stefano.Cestaro
 *
 */
public class WebServiceProgrammiTriennaliManager {

	static Logger logger = Logger.getLogger(WebServiceProgrammiTriennaliManager.class);

	private SqlManager sqlManager;

	protected LoginManager loginManager;
	
	private ImportExportXMLManager importExportXMLManager;
	  
	/**
	 * 
	 * @return
	 */
	public SqlManager getSqlManager() {
		return this.sqlManager;
	}

	/**
	 * 
	 * @param sqlManager
	 */
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}
	
	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}
	
	public void setImportExportXMLManager(ImportExportXMLManager importExportXMLManager) {
		this.importExportXMLManager = importExportXMLManager;
	}
	
	public String rispostaListaProgrammi(String id, int anno, int tipo) throws java.rmi.RemoteException {
		
		String response = "";
    	String sqlSelectPiatri = "select CONTRI,ID,ANNTRI,BRETRI,TIPROG from piatri where 1=1";
    	ListaProgrammiDocument doc = ListaProgrammiDocument.Factory.newInstance();
    	ListaProgrammiDocument.ListaProgrammi listaProgrammi = doc.addNewListaProgrammi();
	
    	try {
    		if (id != null && !id.equals(""))
    			sqlSelectPiatri += " and id='" + id + "'";
    		if (anno != -1)
    			sqlSelectPiatri += " and anntri=" + anno ;
    		if (tipo != -1)
    			sqlSelectPiatri += " and tiprog=" + tipo;
    		
    		List< ? > programmi = this.sqlManager.getListHashMap(sqlSelectPiatri, new Object[] { });
    		if (programmi != null && programmi.size() > 0) {
    			for (int i = 0; i < programmi.size(); i++) {
					 DataColumnContainer piatri = new DataColumnContainer(sqlManager, "PIATRI", sqlSelectPiatri, new Object[] { });
					 piatri.setValoriFromMap((HashMap< ? , ? >) programmi.get(i), true);
					 Programma programma = listaProgrammi.addNewProgramma();
					 programma.setKey(piatri.getLong("CONTRI"));
					 programma.setId((piatri.getString("ID")!=null)?piatri.getString("ID"):"-1");
					 programma.setAnno(piatri.getLong("ANNTRI"));
					 programma.setDescrizione((piatri.getString("BRETRI")!=null)?piatri.getString("BRETRI"):"");
					 programma.setTipo(piatri.getLong("TIPROG"));
				 }
    		}
    		response =  doc.toString();
		} catch (Exception e) { 
			throw new RemoteException("Errore durante l'interazione con la base dati", e);
		} 		
		
		return response;
	}
	
	public String rispostaListaInterventi(int contri) throws java.rmi.RemoteException {
		
		String response = "";
    	String sqlSelectInttri = "select CONINT,CODINT,DESINT,TOTINT,ANNINT from inttri where contri = ?";
    	ListaInterventiDocument doc = ListaInterventiDocument.Factory.newInstance();
    	ListaInterventiDocument.ListaInterventi listaInterventi = doc.addNewListaInterventi();
    	
    	try {
    		Long tipoProgramma = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where contri = ?", new Object[] { contri });
    		if (tipoProgramma == 3) {
    			sqlSelectInttri += " and tipint = 1";
    		}
    		List< ? > interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri });
    		if (interventi != null && interventi.size() > 0) {
    			for (int i = 0; i < interventi.size(); i++) {
					 DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri });
					 inttri.setValoriFromMap((HashMap <?, ?>) interventi.get(i), true);
					 Intervento intervento = listaInterventi.addNewIntervento();
					 intervento.setNr(inttri.getLong("CONINT"));
					 intervento.setCodInterno((inttri.getString("CODINT")!=null)?inttri.getString("CODINT"):"");
					 intervento.setDescrizione((inttri.getString("DESINT")!=null)?inttri.getString("DESINT"):"");
					 intervento.setImpTotale((inttri.getDouble("TOTINT")!=null)?inttri.getDouble("TOTINT"):0);
					 intervento.setPianoAnnuale((inttri.getString("ANNINT")!=null && inttri.getString("ANNINT").equals("1"))?"1":"");
				 }
    		}
    		response =  doc.toString();
				 
		} catch (Exception e) { 
			throw new RemoteException("Errore durante l'interazione con la base dati", e);
		} 		
		
		return response;
	}
	
	public String rispostaSchedaIntervento(int contri, int conint) throws java.rmi.RemoteException {
		String response = "";
    	try {
    		//verifico se il programma passato come parametro è di Fornitura servizi o triennale
    		Long tipoProgramma = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where contri = ?", new Object[] { contri });
    		if (tipoProgramma != null) {
    			if (tipoProgramma == 3) {
    				tipoProgramma = (Long) this.sqlManager.getObject("select TIPINT from INTTRI where contri = ? and conint = ?", new Object[] { contri, conint });
    				tipoProgramma = (tipoProgramma!=null)? tipoProgramma:1;
    			}
    			if (tipoProgramma == 1) {
    				response = rispostaSchedaInterventoLavori(contri, conint);
    			} else {
    				response = rispostaSchedaInterventoFornitureServizi(contri, conint);
    			}
    		}
				 
		} catch (Exception e) { 
			throw new RemoteException("Errore durante l'interazione con la base dati", e);
		} 		
		
		return response;
	}
	
	public String rispostaSchedaInterventoLavori(int contri, int conint) throws java.rmi.RemoteException {
		
		String response = "";
    	String sqlSelectInttri = "select * from inttri where contri = ? and conint = ?";
    	SchedaInterventoLavori intervento = SchedaInterventoLavori.Factory.newInstance();
    	try {
    		List< ? > interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri , conint });
    		if (interventi != null && interventi.size() == 1) {
    			
    			DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri , conint });
				inttri.setValoriFromMap((HashMap) interventi.get(0), true);
				intervento.setNr(conint);
				intervento.setCodInterno((inttri.getString("CODINT")!=null)?inttri.getString("CODINT"):"");
				intervento.setDescrizione((inttri.getString("DESINT")!=null)?inttri.getString("DESINT"):"");
				
				if (inttri.getString("CODRUP")!=null) {
					String cfTecnico = (String) this.sqlManager.getObject("select CFTEC from TECNI where CODTEC = ?", new Object[] { inttri.getString("CODRUP") });
					intervento.setRup((cfTecnico!=null)?cfTecnico:"");
					String nomeTecnico = (String) this.sqlManager.getObject("select NOMTEC from TECNI where CODTEC = ?", new Object[] { inttri.getString("CODRUP") });
					intervento.setNomeTecnico((nomeTecnico != null)? nomeTecnico : "");
				} else {
					intervento.setRup("");
					intervento.setNomeTecnico("");
				}
				
				intervento.setAnnualitaRif((inttri.getLong("ANNRIF")!=null)?inttri.getLong("ANNRIF"):0);
				intervento.setTipologia((inttri.getString("SEZINT")!=null)?inttri.getString("SEZINT"):"");
				intervento.setCategoria((inttri.getString("INTERV")!=null)?inttri.getString("INTERV"):"");
				intervento.setSottocategoria((inttri.getString("CATINT")!=null)?inttri.getString("CATINT"):"");
				intervento.setCodiceIstat((inttri.getString("COMINT")!=null)?inttri.getString("COMINT"):"");
				intervento.setNuts((inttri.getString("NUTS")!=null)?inttri.getString("NUTS"):"");
				intervento.setCup((inttri.getString("CUPPRG")!=null)?inttri.getString("CUPPRG"):"");
				intervento.setCpv((inttri.getString("CODCPV")!=null)?inttri.getString("CODCPV"):"");
				intervento.setPianoAnnuale((inttri.getString("ANNINT")!=null && inttri.getString("ANNINT").equals("1"))?"1":"");
				intervento.setPriorita((inttri.getLong("PRGINT")!=null)?inttri.getLong("PRGINT"):0);
				intervento.setImpStanzBilancio1((inttri.getDouble("BI1TRI")!=null)?inttri.getDouble("BI1TRI"):0);
				intervento.setImpStanzBilancio2((inttri.getDouble("BI2TRI")!=null)?inttri.getDouble("BI2TRI"):0);
				intervento.setImpStanzBilancio3((inttri.getDouble("BI3TRI")!=null)?inttri.getDouble("BI3TRI"):0);
				intervento.setImpVincolato1((inttri.getDouble("DV1TRI")!=null)?inttri.getDouble("DV1TRI"):0);
				intervento.setImpVincolato2((inttri.getDouble("DV2TRI")!=null)?inttri.getDouble("DV2TRI"):0);
				intervento.setImpVincolato3((inttri.getDouble("DV3TRI")!=null)?inttri.getDouble("DV3TRI"):0);
				intervento.setImpTrasfImmobile1((inttri.getDouble("IM1TRI")!=null)?inttri.getDouble("IM1TRI"):0);
				intervento.setImpTrasfImmobile2((inttri.getDouble("IM2TRI")!=null)?inttri.getDouble("IM2TRI"):0);
				intervento.setImpTrasfImmobile3((inttri.getDouble("IM3TRI")!=null)?inttri.getDouble("IM3TRI"):0);
				intervento.setImpMutuo1((inttri.getDouble("MU1TRI")!=null)?inttri.getDouble("MU1TRI"):0);
				intervento.setImpMutuo2((inttri.getDouble("MU2TRI")!=null)?inttri.getDouble("MU2TRI"):0);
				intervento.setImpMutuo3((inttri.getDouble("MU3TRI")!=null)?inttri.getDouble("MU3TRI"):0);
				intervento.setImpAltro1((inttri.getDouble("AL1TRI")!=null)?inttri.getDouble("AL1TRI"):0);
				intervento.setImpAltro2((inttri.getDouble("AL2TRI")!=null)?inttri.getDouble("AL2TRI"):0);
				intervento.setImpAltro3((inttri.getDouble("AL3TRI")!=null)?inttri.getDouble("AL3TRI"):0);
				intervento.setImpPrivato1((inttri.getDouble("PR1TRI")!=null)?inttri.getDouble("PR1TRI"):0);
				intervento.setImpPrivato2((inttri.getDouble("PR2TRI")!=null)?inttri.getDouble("PR2TRI"):0);
				intervento.setImpPrivato3((inttri.getDouble("PR3TRI")!=null)?inttri.getDouble("PR3TRI"):0);
				intervento.setTipoImpPrivato((inttri.getString("TCPINT")!=null)?inttri.getString("TCPINT"):"0");
				//se intervento fa parte del piano annuale inserisco i dati relativi
				if (intervento.getPianoAnnuale().equals("1"))
				{
					InterventoAnnuale annuale = intervento.addNewSchedaAnnuale();
					annuale.setFinalita((inttri.getString("FIINTR")!=null)?inttri.getString("FIINTR"):"");
					annuale.setStatoProgettazione((inttri.getString("STAPRO")!=null)?inttri.getString("STAPRO"):"");
					annuale.setAmbientale((inttri.getString("APCINT")!=null)?inttri.getString("APCINT"):"");
					annuale.setUrbanistico((inttri.getString("URCINT")!=null)?inttri.getString("URCINT"):"");
					if (inttri.getLong("AILINT")!=null)
						annuale.setAnnoInizio(inttri.getLong("AILINT"));
					if (inttri.getLong("TILINT")!=null)
						annuale.setTrimestreInizio(inttri.getLong("TILINT"));
					if (inttri.getLong("AFLINT")!=null)
						annuale.setAnnoFine(inttri.getLong("AFLINT"));
					if (inttri.getLong("TFLINT")!=null)
						annuale.setTrimestreFine(inttri.getLong("TFLINT"));
				}
				//se ci sono degli immobili inserisco i loro dati
				if (intervento.getImpTrasfImmobile1() + intervento.getImpTrasfImmobile2() + intervento.getImpTrasfImmobile3() > 0)
				{
					String sqlSelectImmtrai = "select * from immtrai where contri = ? and conint = ?";
					List immobili = this.sqlManager.getListHashMap(sqlSelectImmtrai, new Object[] { contri , conint});
					if (immobili != null && immobili.size() > 0) {
						SchedaInterventoLavori.Immobili beniImmobili = intervento.addNewImmobili();
						for (int i = 0; i < immobili.size(); i++) {
							DataColumnContainer immtrai = new DataColumnContainer(sqlManager, "IMMTRAI", sqlSelectImmtrai, new Object[] { contri, conint });
							immtrai.setValoriFromMap((HashMap) immobili.get(i), true);
							BeneImmobile bene = beniImmobili.addNewBene();
							bene.setDescrizione((immtrai.getString("DESIMM")!=null)?immtrai.getString("DESIMM"):"");
							if (immtrai.getLong("PROIMM")!=null)
								bene.setTipoProprieta(immtrai.getLong("PROIMM"));
							bene.setValore((immtrai.getDouble("VALIMM")!=null)?immtrai.getDouble("VALIMM"):0);
							if (immtrai.getLong("ANNIMM")!=null)
								bene.setAnno(immtrai.getLong("ANNIMM"));
						}
					}
				}
				response =  intervento.toString();
    		}
    		
				 
		} catch (Exception e) { 
			throw new RemoteException("Errore durante l'interazione con la base dati", e);
		} 		
		
		return response;
	}

	public String rispostaSchedaInterventoFornitureServizi(int contri, int conint) throws java.rmi.RemoteException {
		
		String response = "";
    	String sqlSelectInttri = "select * from inttri where contri = ? and conint = ?";
    	SchedaInterventoFornitureServizi intervento = SchedaInterventoFornitureServizi.Factory.newInstance();
    	try {
    		List interventi = this.sqlManager.getListHashMap(sqlSelectInttri, new Object[] { contri , conint });
    		if (interventi != null && interventi.size() == 1) {
    			
    			DataColumnContainer inttri = new DataColumnContainer(sqlManager, "INTTRI", sqlSelectInttri, new Object[] { contri , conint });
				inttri.setValoriFromMap((HashMap) interventi.get(0), true);
				
				intervento.setNr(conint);
				intervento.setCodInterno((inttri.getString("CODINT")!=null)?inttri.getString("CODINT"):"");
				intervento.setDescrizione((inttri.getString("DESINT")!=null)?inttri.getString("DESINT"):"");
				if (inttri.getString("CODRUP")!=null) {
					String cfTecnico = (String) this.sqlManager.getObject("select CFTEC from TECNI where CODTEC = ?", new Object[] { inttri.getString("CODRUP") });
					intervento.setRup((cfTecnico!=null)?cfTecnico:"");
				} else {
					intervento.setRup("");
				}
				intervento.setCodiceIstat((inttri.getString("COMINT")!=null)?inttri.getString("COMINT"):"");
				intervento.setNuts((inttri.getString("NUTS")!=null)?inttri.getString("NUTS"):"");
				intervento.setCpv((inttri.getString("CODCPV")!=null)?inttri.getString("CODCPV"):"");
				intervento.setTipologia((inttri.getString("TIPOIN")!=null)?inttri.getString("TIPOIN"):"");
				intervento.setPriorita((inttri.getLong("PRGINT")!=null)?inttri.getLong("PRGINT"):0);
				intervento.setMese((inttri.getLong("MESEIN")!=null)?inttri.getLong("MESEIN"):0);
				intervento.setNormativaRiferimento((inttri.getString("NORRIF")!=null)?inttri.getString("NORRIF"):"");
				intervento.setStrumentoProgramma((inttri.getString("STRUPR")!=null)?inttri.getString("STRUPR"):"");
				intervento.setPrevistaManodopera((inttri.getString("MANTRI")!=null)?inttri.getString("MANTRI"):"");
				intervento.setImpAltro((inttri.getDouble("AL1TRI")!=null)?inttri.getDouble("AL1TRI"):0);
				intervento.setImpStanzBilancio((inttri.getDouble("BI1TRI")!=null)?inttri.getDouble("BI1TRI"):0);
				intervento.setImpRegionale((inttri.getDouble("RG1TRI")!=null)?inttri.getDouble("RG1TRI"):0);
				intervento.setImpStatoUE((inttri.getDouble("IMPRFS")!=null)?inttri.getDouble("IMPRFS"):0);
				response =  intervento.toString();
    		}
    		
				 
		} catch (Exception e) { 
			throw new RemoteException("Errore durante l'interazione con la base dati", e);
		} 		
		
		return response;
	}

	
	public boolean inserisciSchedaIntervento(String interventoXML, int contri, javax.xml.rpc.holders.StringHolder out) throws java.rmi.RemoteException {
		boolean response = false;
    	try {
    		 //verifica se esiste il lavoro e di che tipologia è
    		Long tipoProgramma = (Long) this.sqlManager.getObject("select TIPROG from PIATRI where contri = ?", new Object[] { contri });
    		if (tipoProgramma !=null)
    		{
    			if (tipoProgramma == 1)
    				response = inserisciSchedaInterventoLavori(interventoXML,contri,out);
    			else
    				response = inserisciSchedaInterventoFornitureServizi(interventoXML,contri,out);
    		}
    		else
    			out.value += "Il lavoro indicato non esiste\n";
    	    
		} catch (Exception e) { 
			out.value += e.getMessage() + "\n";
		} 		
    	return response;
	}

	public boolean inserisciSchedaInterventoLavori(String interventoXML, int contri, javax.xml.rpc.holders.StringHolder out) throws java.rmi.RemoteException {
		boolean response = false;
    	try {
    	    	//verifico la validità della stringa XML
    	    	SchedaInterventoLavori intervento = SchedaInterventoLavori.Factory.parse(interventoXML);
        		ArrayList<?> validationErrors = new ArrayList<Object>();
        	    XmlOptions validationOptions = new XmlOptions();
        	    validationOptions.setErrorListener(validationErrors);
        	    boolean isValid = intervento.validate(validationOptions);
        	    if (!isValid) {
        	      Iterator<?> iter = validationErrors.iterator();
        	      while (iter.hasNext()) {
        	    	  out.value += iter.next() + "\n";
        	      }
        	    }
        	    else
        	    {	
         	    	String fields = "CONTRI,";
        	    	ArrayList values = new ArrayList();
        	    	values.add(contri);
        	    	//ricavo il codice intervento
        	    	Long conint = (Long) this.sqlManager.getObject("select " + this.sqlManager.getDBFunction("isnull",new String[] {"max(conint)","0"})  + " from inttri where contri = ?", new Object[] { contri }) + 1;
        	    	fields+="CONINT,";
        	    	values.add(conint);
        	    	//inserisco l'intervento e gli eventuali immobili
        	    	if (intervento.getCodInterno()!=null) {
        	    		fields+="CODINT,";
        	    		values.add(intervento.getCodInterno());
        	    	}
        	    	if (intervento.getDescrizione()!=null) {
        	    		fields+="DESINT,"; 
        	    		values.add(intervento.getDescrizione());
        	    	}
        	    	if (intervento.getRup()!=null) {
        	    		fields+="CODRUP,";
        	    		values.add(intervento.getRup());
        	    	}
        	    	fields+="ANNRIF,";
    	    		values.add(intervento.getAnnualitaRif());
        	    	if (intervento.getTipologia()!=null) {
        	    		fields+="SEZINT,";
        	    		values.add(intervento.getTipologia());
        	    	}
        	    	if (intervento.getCategoria()!=null) {
        	    		fields+="INTERV,";
        	    		values.add(intervento.getCategoria());
        	    	}
        	    	if (intervento.getSottocategoria()!=null) {
        	    		fields+="CATINT,";
        	    		values.add(intervento.getSottocategoria());
        	    	}
        	    	if (intervento.getCodiceIstat()!=null) {
        	    		fields+="COMINT,";
        	    		values.add(intervento.getCodiceIstat());
        	    	}
        	    	if (intervento.getNuts()!=null) {
        	    		fields+="NUTS,";
        	    		values.add(intervento.getNuts());
        	    	}
        	    	if (intervento.getCup()!=null) {
        	    		fields+="CUPPRG,";
        	    		values.add(intervento.getCup());
        	    	}
        	    	if (intervento.getCpv()!=null) {
        	    		fields+="CODCPV,";
        	    		values.add(intervento.getCpv());
        	    	}
        	    	if (intervento.getPianoAnnuale()!=null) {
        	    		fields+="ANNINT,";
        	    		values.add(intervento.getPianoAnnuale());
        	    	}
       	    		fields+="PRGINT,";
        	    	values.add(intervento.getPriorita());
       	    		fields+="BI1TRI,";
        	    	values.add(intervento.getImpStanzBilancio1());
        	    	fields+="BI2TRI,";
        	    	values.add(intervento.getImpStanzBilancio2());
        	    	fields+="BI3TRI,";
        	    	values.add(intervento.getImpStanzBilancio3());
        	    	fields+="DV1TRI,";
        	    	values.add(intervento.getImpVincolato1());
        	    	fields+="DV2TRI,";
        	    	values.add(intervento.getImpVincolato2());
        	    	fields+="DV3TRI,";
        	    	values.add(intervento.getImpVincolato3());
        	    	fields+="IM1TRI,";
        	    	values.add(intervento.getImpTrasfImmobile1());
        	    	fields+="IM2TRI,";
        	    	values.add(intervento.getImpTrasfImmobile2());
        	    	fields+="IM3TRI,";
        	    	values.add(intervento.getImpTrasfImmobile3());
        	    	fields+="MU1TRI,";
        	    	values.add(intervento.getImpMutuo1());
        	    	fields+="MU2TRI,";
        	    	values.add(intervento.getImpMutuo2());
        	    	fields+="MU3TRI,";
        	    	values.add(intervento.getImpMutuo3());
        	    	fields+="AL1TRI,";
        	    	values.add(intervento.getImpAltro1());
        	    	fields+="AL2TRI,";
        	    	values.add(intervento.getImpAltro2());
        	    	fields+="AL3TRI,";
        	    	values.add(intervento.getImpAltro3());
        	    	fields+="PR1TRI,";
        	    	values.add(intervento.getImpPrivato1());
        	    	fields+="PR2TRI,";
        	    	values.add(intervento.getImpPrivato2());
        	    	fields+="PR3TRI,";
        	    	values.add(intervento.getImpPrivato3());
        	    	
        	    	fields+="DI1INT,DI2INT,DI3INT,DITINT,ICPINT,TOTINT,";
        	    	double impDisponiblie1 = intervento.getImpStanzBilancio1()+intervento.getImpVincolato1()+intervento.getImpTrasfImmobile1()+intervento.getImpMutuo1()+intervento.getImpAltro1();
        	    	double impDisponiblie2 = intervento.getImpStanzBilancio2()+intervento.getImpVincolato2()+intervento.getImpTrasfImmobile2()+intervento.getImpMutuo2()+intervento.getImpAltro2();
        	    	double impDisponiblie3 = intervento.getImpStanzBilancio3()+intervento.getImpVincolato3()+intervento.getImpTrasfImmobile3()+intervento.getImpMutuo3()+intervento.getImpAltro3();
        	    	double impPrivatoTot = intervento.getImpPrivato1()+intervento.getImpPrivato2()+intervento.getImpPrivato3();
        	    	values.add(impDisponiblie1);
        	    	values.add(impDisponiblie2);
        	    	values.add(impDisponiblie3);
        	    	values.add(impDisponiblie1+impDisponiblie2+impDisponiblie3);
        	    	values.add(impPrivatoTot);
        	    	values.add(impDisponiblie1+impDisponiblie2+impDisponiblie3+impPrivatoTot);
        	    	
        	    	if (intervento.getTipoImpPrivato()!=null) {
        	    		fields+="TCPINT,";
        	    		values.add(intervento.getTipoImpPrivato());
        	    	}
        	    	
        	    	//se intervento fa parte del piano annuale inserisco i dati relativi
    				if (intervento.getPianoAnnuale().equals("1"))
    				{
    					InterventoAnnuale annuale = intervento.getSchedaAnnuale();
    					if (annuale.getFinalita()!=null) {
            	    		fields+="FIINTR,";
            	    		values.add(annuale.getFinalita());
            	    	}
    					if (annuale.getStatoProgettazione()!=null) {
            	    		fields+="STAPRO,";
            	    		values.add(annuale.getStatoProgettazione());
            	    	}
    					if (annuale.getAmbientale()!=null) {
            	    		fields+="APCINT,";
            	    		values.add(annuale.getAmbientale());
            	    	}
    					if (annuale.getUrbanistico()!=null) {
            	    		fields+="URCINT,";
            	    		values.add(annuale.getUrbanistico());
            	    	}
    					fields+="AILINT,";
            	    	values.add(annuale.getAnnoInizio());
            	    	fields+="TILINT,";
            	    	values.add(annuale.getTrimestreInizio());
            	    	fields+="AFLINT,";
            	    	values.add(annuale.getAnnoFine());
            	    	fields+="TFLINT,";
            	    	values.add(annuale.getTrimestreFine());
            	    	
    				}
    				
        	    	fields = fields.substring(0, fields.length()-1);
        	    	String sqlInsertIntervento = "INSERT INTO INTTRI (" + fields + ") VALUES (";
        	    	for (int i =0 ; i < values.size(); i++)
        	    		sqlInsertIntervento += "?,";
        	    	sqlInsertIntervento = sqlInsertIntervento.substring(0, sqlInsertIntervento.length()-1) + ")";
        	    	//inserisco l'intervento
    				this.sqlManager.update(sqlInsertIntervento, values.toArray());
    				//this.ptManager.aggiornaCostiPiatri(new Long(contri));
    				
    				//se ci sono degli immobili inserisco i loro dati
    				SchedaInterventoLavori.Immobili immobili = intervento.getImmobili();
    				if (immobili!=null)
    				{
    					String sqlInsertImmobile = "";
    					BeneImmobile beniImmobili[] = immobili.getBeneArray();
    					for(int i = 0; i<beniImmobili.length; i ++)
    					{
    						BeneImmobile bene = beniImmobili[i];
    						fields = "CONTRI,CONINT,NUMIMM";
    	        	    	values.clear();
    	        	    	values.add(contri);
    	        	    	values.add(conint);
    	        	    	values.add(i+1);
    	        	    	if (bene.getDescrizione()!=null) {
                	    		fields+=",DESIMM";
                	    		values.add(bene.getDescrizione());
                	    	}
    	        	    	fields+=",PROIMM";
                	    	values.add(bene.getTipoProprieta());
                	    	fields+=",VALIMM";
                	    	values.add(bene.getValore());
                	    	fields+=",ANNIMM";
                	    	values.add(bene.getAnno());
                	    	
                	    	sqlInsertImmobile = "INSERT INTO IMMTRAI (" + fields + ") VALUES (";
                	    	for (int j =0 ; j < values.size(); j++)
                	    		sqlInsertImmobile += "?,";
                	    	sqlInsertImmobile = sqlInsertImmobile.substring(0, sqlInsertImmobile.length()-1) + ")";
                	    	//inserisco l'intervento
            				this.sqlManager.update(sqlInsertImmobile, values.toArray());
    					}
    				}
    				response = true;
        	    }
    	   
		} catch (Exception e) { 
			out.value += e.getMessage() + "\n";
		} 		
    	return response;
	}

	public boolean inserisciSchedaInterventoFornitureServizi(String interventoXML, int contri, javax.xml.rpc.holders.StringHolder out) throws java.rmi.RemoteException {
		boolean response = false;
    	try {
    	    	//verifico la validità della stringa XML
    			SchedaInterventoFornitureServizi intervento = SchedaInterventoFornitureServizi.Factory.parse(interventoXML);
    	    	
        		ArrayList<?> validationErrors = new ArrayList<Object>();
        	    XmlOptions validationOptions = new XmlOptions();
        	    validationOptions.setErrorListener(validationErrors);
        	    boolean isValid = intervento.validate(validationOptions);
        	    if (!isValid) {
        	      Iterator<?> iter = validationErrors.iterator();
        	      while (iter.hasNext()) {
        	    	  out.value += iter.next() + "\n";
        	      }
        	    }
        	    else
        	    {	
         	    	String fields = "CONTRI,";
        	    	ArrayList values = new ArrayList();
        	    	values.add(contri);
        	    	//ricavo il codice intervento
        	    	Long conint = (Long) this.sqlManager.getObject("select " + this.sqlManager.getDBFunction("isnull",new String[] {"max(conint)","0"})  + " from inttri where contri = ?", new Object[] { contri }) + 1;
        	    	fields+="CONINT,";
        	    	values.add(conint);
        	    	//inserisco l'intervento 
        	    	if (intervento.getCodInterno()!=null) {
        	    		fields+="CODINT,";
        	    		values.add(intervento.getCodInterno());
        	    	}
        	    	if (intervento.getDescrizione()!=null) {
        	    		fields+="DESINT,"; 
        	    		values.add(intervento.getDescrizione());
        	    	}
        	    	if (intervento.getRup()!=null) {
        	    		fields+="CODRUP,";
        	    		values.add(intervento.getRup());
        	    	}
        	    	if (intervento.getCodiceIstat()!=null) {
        	    		fields+="COMINT,";
        	    		values.add(intervento.getCodiceIstat());
        	    	}
        	    	if (intervento.getNuts()!=null) {
        	    		fields+="NUTS,";
        	    		values.add(intervento.getNuts());
        	    	}
        	    	if (intervento.getCpv()!=null) {
        	    		fields+="CODCPV,";
        	    		values.add(intervento.getCpv());
        	    	}
        	    	if (intervento.getTipologia()!=null) {
        	    		fields+="TIPOIN,";
        	    		values.add(intervento.getTipologia());
        	    	}
        	    	fields+="PRGINT,";
        	    	values.add(intervento.getPriorita());
        	    	fields+="MESEIN,";
        	    	values.add(intervento.getMese());
        	    	if (intervento.getNormativaRiferimento()!=null) {
        	    		fields+="NORRIF,";
        	    		values.add(intervento.getNormativaRiferimento());
        	    	}
        	    	if (intervento.getStrumentoProgramma()!=null) {
        	    		fields+="STRUPR,";
        	    		values.add(intervento.getStrumentoProgramma());
        	    	}
        	    	if (intervento.getPrevistaManodopera()!=null) {
        	    		fields+="MANTRI,";
        	    		values.add(intervento.getPrevistaManodopera());
        	    	}
        	    	fields+="AL1TRI,";
        	    	values.add(intervento.getImpAltro());
        	    	fields+="BI1TRI,";
        	    	values.add(intervento.getImpStanzBilancio());
        	    	fields+="RG1TRI,";
        	    	values.add(intervento.getImpRegionale());
        	    	fields+="IMPRFS,";
        	    	values.add(intervento.getImpStatoUE());
        	    	fields+="DITINT,";
        	    	values.add(intervento.getImpStatoUE()+intervento.getImpRegionale()+intervento.getImpStanzBilancio()+intervento.getImpAltro());
        	    	
        	    	    				
        	    	fields = fields.substring(0, fields.length()-1);
        	    	String sqlInsertIntervento = "INSERT INTO INTTRI (" + fields + ") VALUES (";
        	    	for (int i =0 ; i < values.size(); i++)
        	    		sqlInsertIntervento += "?,";
        	    	sqlInsertIntervento = sqlInsertIntervento.substring(0, sqlInsertIntervento.length()-1) + ")";
        	    	//inserisco l'intervento
    				this.sqlManager.update(sqlInsertIntervento, values.toArray());
    				response = true;
        	    }
    	   
		} catch (Exception e) { 
			out.value += e.getMessage() + "\n";
		} 		
    	return response;
	}
	
	public EsitoInserisciProgramma inserisciProgramma(String login, String password, String id, String xmlProgramma) {
		EsitoInserisciProgramma result = new EsitoInserisciProgramma();
		Long contri = null;
		PtDocument doc = null;
		String codein = null;
		Long syscon = null;
    	try {
    		result.setEsito(true);
    		//verifico la presenza delle credenziali
    		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
    			result.setEsito(false);
    			result.setId("");
    			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.credenzialiNonValorizzate", null, false));
    		} 
    		if (result.isEsito()){
    			//verifico la presenza della stringa XMLProgramma
    			if (StringUtils.isEmpty(xmlProgramma)) {
    				result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.programmaNonValorizzato", null, false));
    			}
    		}
    		if (result.isEsito()){
    			// verifico validità credenziali
    			Account account = null;
				account = this.loginManager.getAccountByLoginEPassword(login, password);
				if (account == null) {
    				result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.credenzialiNonValide", null, false));
				} else {
					syscon = new Long(account.getIdAccount());
				}
    		}
    		if (result.isEsito()){
    			//Se l'id è valorizzato verifico che esista effettivamente nel db
				if (!StringUtils.isEmpty(id)) {
					contri = (Long) sqlManager.getObject("select contri from piatri where id = ?", new Object[] { id });
					if (contri == null) {
						result.setEsito(false);
	        			result.setId("");
	        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.idProgrammaNonEsiste", null, false));
					} 
				}
    		}
    		if (result.isEsito()){
    			//verifico validità documento xml
	    		try {
	    			doc = PtDocument.Factory.parse(xmlProgramma);
	    		} catch (Exception ex) {
	    			result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.xmlProgrammiNonValido", null, false));
	    		}
    		}
    		if (result.isEsito()){
    			//verifico sintassi del documento
	    		ArrayList<Object> validationErrors = new ArrayList<Object>();
		        XmlOptions validationOptions = new XmlOptions();
		        validationOptions.setErrorListener(validationErrors);
		        boolean isValid = doc.validate(validationOptions);

		        if (!isValid) {
		        	String listaErroriValidazione = "";
		        	Iterator<?> iter = validationErrors.iterator();
		        	while (iter.hasNext()) {
		        		listaErroriValidazione += iter.next() + "\n";
		        	}
		          	result.setEsito(false);
      				result.setId("");
      				result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.xmlProgrammiNonConforme" + listaErroriValidazione, null, false));
		        }
    		}
    		if (result.isEsito()){
    			//verifico stazione appaltante
	        	codein = (String) sqlManager.getObject("select codein from uffint where cfein = ? and idammin = ?", new Object[] { doc.getPt().getAmmCodfisc(), new Long(doc.getPt().getIdEnte()) });
	        	if (StringUtils.isEmpty(codein)) {
	        		result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.credenzialiSbagliate", null, false));
	        	}
    		}
    		if (result.isEsito()){
    			//verifico se la stazione appaltante è associata all'utente che ha richiamato il servizio
    			Long exist = (Long) sqlManager.getObject("select count(*) from usr_ein where syscon = ? and codein = ?", new Object[] { syscon, codein });
    			if (exist == 0) {
    				result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.utenteNonAssociato", null, false));
    			}
    		}
    		if (result.isEsito()){
    			String idProgramma = importExportXMLManager.inserisciProgramma(doc, codein, id);
    			if (idProgramma.equals("")){
    				result.setEsito(false);
        			result.setId("");
        			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.inserimentoProgrammaDB", null, false));
    			} else {
    				result.setId(idProgramma);
    				result.setMessaggio("success");
    			}
    		}
		} catch (Exception e) { 
			result.setEsito(false);
			result.setId("");
			result.setMessaggio(UtilityTags.getResource("errors.WSProgrammi.inserisciProgramma.erroreGenerico", null, false));
		} 		
    	return result;
	}
	
}
