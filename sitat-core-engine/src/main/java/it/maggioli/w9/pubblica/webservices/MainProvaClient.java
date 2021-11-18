package it.maggioli.w9.pubblica.webservices;


import it.eldasoft.utils.properties.ConfigManager;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.AppaltiLiguriaWebServiceStub;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionException;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.WsException;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Categorie;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ContrattoDocument;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoFinanziamentoType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoSchedaType;
import it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringUtils;

public class MainProvaClient {

  /**
   * @param args
   * @throws RemoteException 
   * @throws CheckFaultBean 
   */
  public static void main(String[] args) throws RemoteException {

    String urlServizio = "http://apitest.comune.genova.it:8280/ligdig_infoappalti/";
    //urlServizio = "http://192.168.153.92:8280/ligdig_infoappalti/";
    //String urlServizio = "http://192.168.153.92:8280/ligdig_infoappalti/";
    //urlServizio = "http://192.168.1.105:8088/mockAppaltiLiguriaWebServiceSoapBinding/AppaltiLiguriaWebServiceSoapBinding";
    
    //urlServizio = "http://127.0.0.1:8088/mockAppaltiLiguriaWebServiceSoapBinding/";
    
    AppaltiLiguriaWebServiceStub proxy = new AppaltiLiguriaWebServiceStub(urlServizio);
    Contratto contratto = Contratto.Factory.newInstance();
    
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
    DatiEnte DATI_ENTE = DatiEnte.Factory.newInstance();
    DATI_ENTE.setCODICEFISCALE("00856930102");   
    DATI_ENTE.setIDUSERENTE("ComuneGenova");//Questo DEVE ESISTERE nel DB associato all'Ente
    contratto.setDATIENTE(DATI_ENTE);
    
    DatiGeneraliContratto datiGeneraliContratto = DatiGeneraliContratto.Factory.newInstance();
    
    datiGeneraliContratto.setCONTROLLOINVIO("1");//Accetto SOLO questo!
    DatiComuni datiComuni = DatiComuni.Factory.newInstance();
    datiComuni.setCODICEUFFICIO("UFF001");
    datiComuni.setDESCRUFFICIO("DESCRIZIONE UFFICIO");
    datiComuni.setOGGETTOGARA("OGG");
    datiComuni.setIDGARA("111133");
    datiComuni.setNUMLOTTI(1);
    datiComuni.setFLAGENTE(FlagSOType.O);
    datiComuni.setMODOREALIZZAZIONE("1");
    datiComuni.setFLAGSAAGENTE(FlagSNType.N);
    datiGeneraliContratto.setDATICOMUNI(datiComuni);
    
    DatiComuniEstesi datiComuniEstesi = DatiComuniEstesi.Factory.newInstance();
    datiComuniEstesi.setCIG("6475757BB8"); //"6945813A341");
    datiComuniEstesi.setOGGETTO("OGG");
    datiComuniEstesi.setFLAGSOMMAURGENZA(FlagSNType.N);
    datiComuniEstesi.setCPV("32984328489-222");
    datiComuniEstesi.setIDSCELTACONTRAENTE("1");
    datiComuniEstesi.setCATPREVALENTE(CategoriaType.Enum.forString("AA"));
    datiComuniEstesi.setFLAGPOSA(FlagSNType.S);
    datiComuniEstesi.setFLAGLOTTODERIVANTE(FlagSNType.N);
    datiComuniEstesi.setTIPOAPPALTO(TipoSchedaType.L);
    datiComuniEstesi.setFLAGTIPOSETTORE(FlagSOType.O);
    datiComuniEstesi.setIDMODOGARA("1");
    datiComuniEstesi.setLUOGOISTAT("11111");
    datiComuniEstesi.setIDTIPOPRESTAZIONE("1");
    datiComuniEstesi.setFLAG16320061(FlagSNType.S);
    datiComuniEstesi.setFLAG16320062(FlagSNType.S);
    
    DatiResponsabile responsabile=DatiResponsabile.Factory.newInstance();
    responsabile.setCODICEFISCALE("329874832");
    responsabile.setCODICEISTATCOMUNE("1111");
    responsabile.setCOGNOME("aaa");
    responsabile.setCODICESTATO("IT");
    responsabile.setALBOPROFESSIONALE(FlagSNType.S);
    datiComuniEstesi.setResponsabile(responsabile);
    
    datiGeneraliContratto.setDATICOMUNIESTESI(datiComuniEstesi);
    
    
    SezioneAggiudicazione sezioneAggiudicazione = SezioneAggiudicazione.Factory.newInstance();
    DatiAggiudicazione datiAggiudicazione = DatiAggiudicazione.Factory.newInstance();
    datiAggiudicazione.setFINREGIONALE(FlagSNType.S);
    datiAggiudicazione.setFLAGIMPORTI(FlagImportiType.C);
    datiAggiudicazione.setFLAGSICUREZZA(FlagImportiType.C);
    datiAggiudicazione.setPERCOFFAUMENTO(12.56f);
    datiAggiudicazione.setFLAGLIVELLOPROGETTUALE("3290");
    datiAggiudicazione.setVERIFICACAMPIONE(FlagSNType.S);
    datiAggiudicazione.setDATAVERBAGGIUDICAZIONE(calendar);
    datiAggiudicazione.setASTAELETTRONICA(FlagSNType.N);
    datiAggiudicazione.setPROCEDURAACC(FlagSNType.N);
    datiAggiudicazione.setPREINFORMAZIONE(FlagSNType.S);
    datiAggiudicazione.setTERMINERIDOTTO(FlagSNType.S);
    datiAggiudicazione.setDATASCADENZAPRESOFFERTA(calendar);
    datiAggiudicazione.setFLAGRICHSUBAPPALTO(FlagSNType.N);
    datiAggiudicazione.setCODSTRUMENTO(TipoStrumentoType.Enum.forString("A_01"));
    datiAggiudicazione.setOPEREURBANIZSCOMPUTO(FlagSNType.S);
    sezioneAggiudicazione.setDATIAGGIUDICAZIONE(datiAggiudicazione);
    
    Finanziamenti[] listaFinanziamenti= new Finanziamenti[1];          
    Finanziamenti finanziamento= Finanziamenti.Factory.newInstance();
    finanziamento.setIDFINANZIAMENTO(TipoFinanziamentoType.C_01);
    finanziamento.setIMPORTOFINANZIAMENTO(123.36);         
    listaFinanziamenti[0]=finanziamento;
    
    ListaFinanziamenti listafinanziamento= ListaFinanziamenti.Factory.newInstance();
    listafinanziamento.setIdFinanziamentiArray(listaFinanziamenti);
    sezioneAggiudicazione.setLISTAFINANZIAMENTI(listafinanziamento);
    
    Pubblicazione pubblicazione = Pubblicazione.Factory.newInstance();
    pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
    pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
    pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
        
    Categorie[] listaCategorie=new Categorie[0];
    ListaCategorie listacategorie= ListaCategorie.Factory.newInstance();
    listacategorie.setIdCategorieArray(listaCategorie);
    sezioneAggiudicazione.setLISTACATEGORIE(listacategorie);
    sezioneAggiudicazione.setPUBBLICAZIONEAGGIUDICAZIONE(pubblicazione);
    Aggiudicatario[] listaAggiudicatari = new Aggiudicatario[1];
    
    Aggiudicatario aggiudicatario= Aggiudicatario.Factory.newInstance();           
    aggiudicatario.setTIPOLOGIASOGGETTO("1");            
    DatiAggiudicatari datiAggiudicatario= DatiAggiudicatari.Factory.newInstance();
    datiAggiudicatario.setCODICEFISCALE("AAABBB0001112223");//NON OBBLIGATORIO 
    datiAggiudicatario.setPARTITAIVA("AAABBB0001112223");//NON OBBLIGATORIO
    datiAggiudicatario.setINPS("121121");
    datiAggiudicatario.setDENOMINAZIONE("DENOM");
    datiAggiudicatario.setCODICESTATO("IT");
    datiAggiudicatario.setNCCIAA("8232388");
    datiAggiudicatario.setNATURAGIURIDICA("3");
    datiAggiudicatario.setTIPOLOGIASOGGETTO("aaa");
    aggiudicatario.setAggiudicatario(datiAggiudicatario);
    listaAggiudicatari[0]=aggiudicatario;
    
    ListaAggiudicatari listaaggiudicatari = ListaAggiudicatari.Factory.newInstance();
    listaaggiudicatari.setIdAggiudicatariArray(listaAggiudicatari);
    sezioneAggiudicazione.setLISTAAGGIUDICATARI(listaaggiudicatari);
    
    DatiSoggettiEstesi[] listaDatiSoggettiEstesiAgg = new DatiSoggettiEstesi[2]; 
    DatiSoggettiEstesi datiSoggetti= DatiSoggettiEstesi.Factory.newInstance();
    datiSoggetti.setIDRUOLO("1");
    datiSoggetti.setDATAAFFPROGESTERNA(calendar);
    datiSoggetti.setDATACONSPROGESTERNA(calendar);
    datiSoggetti.setResponsabile(responsabile);
    listaDatiSoggettiEstesiAgg[0] = datiSoggetti;
    
    datiSoggetti= DatiSoggettiEstesi.Factory.newInstance();
    datiSoggetti.setIDRUOLO("2");
    datiSoggetti.setDATAAFFPROGESTERNA(calendar);
    datiSoggetti.setDATACONSPROGESTERNA(calendar);
    datiSoggetti.setResponsabile(responsabile);
    listaDatiSoggettiEstesiAgg[1] = datiSoggetti;
    
    ListaDatiSoggettiEstesi listadatisoggettiestesi = ListaDatiSoggettiEstesi.Factory.newInstance();
    listadatisoggettiestesi.setIdDatiSoggettiEstesiArray(listaDatiSoggettiEstesiAgg);
    sezioneAggiudicazione.setLISTADATISOGGETTIESTESIAGG(listadatisoggettiestesi);
    datiGeneraliContratto.setSEZIONEAGGIUDICAZIONE(sezioneAggiudicazione);
    
    //Provo una sezione NON OBBLIGATORIA (FASE INIZIALE)
    SezioneInizio sezioneInizio = SezioneInizio.Factory.newInstance();
    DatiInizio datiInizio = DatiInizio.Factory.newInstance();
    datiInizio.setDATASTIPULA(calendar);
    datiInizio.setFLAGRISERVA(FlagSNType.N);
    datiInizio.setDATAESECUTIVITA(calendar);
    datiInizio.setIMPORTOCAUZIONE(125.9999);
    datiInizio.setFLAGFRAZIONATA(FlagSNType.S);
    datiInizio.setDATAVERBALEDEF(calendar);
    sezioneInizio.setDATIINIZIO(datiInizio);
    sezioneInizio.setPUBBLICAZIONEINIZIO(pubblicazione );
    
    //Minimo 0
    DatiSoggettiEstesi[] LISTADATISOGGETTIESTESIINIZIO=new DatiSoggettiEstesi[0];
    ListaDatiSoggettiEstesi listadatisoggettiestesiinizio = ListaDatiSoggettiEstesi.Factory.newInstance();
    listadatisoggettiestesi.setIdDatiSoggettiEstesiArray(LISTADATISOGGETTIESTESIINIZIO);
    sezioneInizio.setLISTADATISOGGETTIESTESIINIZIO(listadatisoggettiestesiinizio);
    datiGeneraliContratto.setSEZIONEINIZIO(sezioneInizio);
    
    //Provo una sezione MULTIPLA NON OBBLIGATORIA (SAL)
    SezioneSAL sezioneSAL = SezioneSAL.Factory.newInstance();
    DatiAvanzamento[] datiSAL = new DatiAvanzamento[2];
    
    DatiAvanzamento datiAvanzamento= DatiAvanzamento.Factory.newInstance();
    datiAvanzamento.setSUBAPPALTI(FlagSNType.N);
    datiAvanzamento.setIMPORTOCERTIFICATO(125);
    datiAvanzamento.setFLAGRITARDO(FlagRitardoType.A);
    datiAvanzamento.setFLAGPAGAMENTO("N");
    datiAvanzamento.setDATARAGGIUNGIMENTO(calendar);
    datiAvanzamento.setIMPORTOSAL(58);
    datiSAL[0]=datiAvanzamento;
    
    datiAvanzamento= DatiAvanzamento.Factory.newInstance();
    datiAvanzamento.setSUBAPPALTI(FlagSNType.S);
    datiAvanzamento.setIMPORTOCERTIFICATO(3333);
    datiAvanzamento.setFLAGRITARDO(FlagRitardoType.P);
    datiAvanzamento.setFLAGPAGAMENTO("S");
    datiAvanzamento.setDATARAGGIUNGIMENTO(calendar);
    datiAvanzamento.setIMPORTOSAL(223);
    datiSAL[1]=datiAvanzamento;
    ListaDatiAvanzamento listadatiavanazamento = ListaDatiAvanzamento.Factory.newInstance();
    listadatiavanazamento.setIdDatiAvanzamentoArray(datiSAL);
    sezioneSAL.setDATISAL(listadatiavanazamento);
    
    datiGeneraliContratto.setSEZIONESAL(sezioneSAL);
    
    
    //Provo una sezione MULTIPLA NON OBBLIGATORIA (VARIANTE)    
    
    DatiVariante[] id_DatiVariante = new DatiVariante[1];
    DatiVariante datiVariante= DatiVariante.Factory.newInstance();
    datiVariante.setFLAGVARIANTE(FlagVarianteType.A);    
    datiVariante.setQUINTOOBBLIGO(FlagSNType.N);
    datiVariante.setFLAGIMPORTI(FlagImportiType.C);
    datiVariante.setFLAGSICUREZZA(FlagImportiType.C);
    datiVariante.setDATAVERBAPPR(calendar);
    datiVariante.setIMPORTORIDETERMINATO(123.32);//Campo x il futuro......
    id_DatiVariante[0] = datiVariante;    
   
    
    MotiviVariante[] lista_motivi_variante = new MotiviVariante[1];//Unica!
    MotiviVariante listaMotiviVariante= MotiviVariante.Factory.newInstance();
    listaMotiviVariante.setIDMOTIVOVAR("AAA");
    lista_motivi_variante[0] = listaMotiviVariante;
    ListaMotiviVariante listamotivivariante = ListaMotiviVariante.Factory.newInstance();
    listamotivivariante.setIdMotiviVarianteArray(lista_motivi_variante);
    datiVariante.setLISTAMOTIVIVARIANTE(listamotivivariante);

    
    
    //ListaDatiVariante DATI_VARIANTI = LISTA_DATI_VARIANTI;
    
    SezioneVarianti SEZIONE_VARIANTI= SezioneVarianti.Factory.newInstance();
    ListaDatiVariante listadativariante = ListaDatiVariante.Factory.newInstance();
    listadativariante.setIdDatiVarianteArray(id_DatiVariante);
    SEZIONE_VARIANTI.setDATIVARIANTI(listadativariante);
    
    datiGeneraliContratto.setSEZIONEVARIANTI(SEZIONE_VARIANTI);
    
    //Provo un Contenzioso
    SezioneAccordiBonari SEZIONE_ACCORDI_BONARI= SezioneAccordiBonari.Factory.newInstance();
    
    DatiAccordo[] DATI_ACCORDI_BONARI=new DatiAccordo[1];
    DatiAccordo datiAccordi= DatiAccordo.Factory.newInstance();
    datiAccordi.setDATAACCORDO(calendar);
    datiAccordi.setDATAINIZIOACC(calendar);
    datiAccordi.setDATAFINEACC(calendar);
    
    DATI_ACCORDI_BONARI[0]=datiAccordi;
    ListaDatiAccordiBonari listadatiaccordibonari = ListaDatiAccordiBonari.Factory.newInstance();
    listadatiaccordibonari.setIdDatiAccordiBonariArray(DATI_ACCORDI_BONARI);
    SEZIONE_ACCORDI_BONARI.setDATIACCORDIBONARI(listadatiaccordibonari);
    
    datiGeneraliContratto.setSEZIONEACCORDIBONARI(SEZIONE_ACCORDI_BONARI);
    
    SezioneConclusione SEZIONE_CONCLUSIONE= SezioneConclusione.Factory.newInstance();
    
    DatiConclusione DATI_CONCLUSIONE= DatiConclusione.Factory.newInstance();
    DATI_CONCLUSIONE.setFLAGINTERRANTICIPATA(FlagSNType.N);
    DATI_CONCLUSIONE.setDATARISOLUZIONE(calendar);
    DATI_CONCLUSIONE.setFLAGPOLIZZA(FlagSNType.S);
    DATI_CONCLUSIONE.setDATAVERBCONSEGNAAVVIO(calendar);
    DATI_CONCLUSIONE.setDATATERMINECONTRATTUALE(calendar);
    DATI_CONCLUSIONE.setDATAULTIMAZIONE(calendar);
    SEZIONE_CONCLUSIONE.setDATICONCLUSIONE(DATI_CONCLUSIONE);
    
    //Provo una sezione NON OBBLIGATORIA (FASE CONCLUSIONE)
    datiGeneraliContratto.setSEZIONECONCLUSIONE(SEZIONE_CONCLUSIONE);
    
    
    //Provo una sezione NON OBBLIGATORIA (FASE COLLAUDO)
    SezioneCollaudo SEZIONECOLLAUDO= SezioneCollaudo.Factory.newInstance();
    DatiCollaudo DATICOLLAUDO= DatiCollaudo.Factory.newInstance();
    DATICOLLAUDO.setFLAGSINGOLOCOMMISSIONE(FlagSingoloCommissioneType.S);
    DATICOLLAUDO.setDATAAPPROVAZIONE(calendar);
    DATICOLLAUDO.setFLAGIMPORTI(FlagImportiType.C);
    DATICOLLAUDO.setFLAGSICUREZZA(FlagImportiType.C);
    DATICOLLAUDO.setFLAGSUBAPPALTATORI(FlagSNType.N);
    DATICOLLAUDO.setMODOCOLLAUDO("A");
    DATICOLLAUDO.setDATANOMINACOLL(calendar);
    DATICOLLAUDO.setESITOCOLLAUDO(FlagEsitoCollaudoType.N);
    DATICOLLAUDO.setIMPDISPOSIZIONE(885.55);
    DATICOLLAUDO.setIMPORTOSUBTOTALE(858);
    DATICOLLAUDO.setIMPORTOFINALE(232);
    DATICOLLAUDO.setIMPORTOCONSUNTIVO(222);
    SEZIONECOLLAUDO.setDATICOLLAUDO(DATICOLLAUDO);
    //Minimo 0
    DatiSoggettiEstesi[] LISTADATISOGGETTIESTESICOLL = new DatiSoggettiEstesi[2];
    datiSoggetti= DatiSoggettiEstesi.Factory.newInstance();
    datiSoggetti.setIDRUOLO("1");
    datiSoggetti.setDATAAFFPROGESTERNA(calendar);
    datiSoggetti.setDATACONSPROGESTERNA(calendar);
    datiSoggetti.setResponsabile(responsabile);
    LISTADATISOGGETTIESTESICOLL[0]=datiSoggetti;
    
    datiSoggetti= DatiSoggettiEstesi.Factory.newInstance();
    datiSoggetti.setIDRUOLO("3");
    datiSoggetti.setDATAAFFPROGESTERNA(calendar);
    datiSoggetti.setDATACONSPROGESTERNA(calendar);
    datiSoggetti.setResponsabile(responsabile);
    LISTADATISOGGETTIESTESICOLL[1]=datiSoggetti;
    
    ListaDatiSoggettiEstesi listadatisoggettiestesicoll = ListaDatiSoggettiEstesi.Factory.newInstance();
    listadatisoggettiestesicoll.setIdDatiSoggettiEstesiArray(LISTADATISOGGETTIESTESICOLL);
    SEZIONECOLLAUDO.setLISTADATISOGGETTIESTESICOLL(listadatisoggettiestesicoll);
    
    datiGeneraliContratto.setSEZIONECOLLAUDO(SEZIONECOLLAUDO);
    
    contratto.setDATIGENERALICONTRATTO(datiGeneraliContratto);

    String contrattoXml = null;
    
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ContrattoDocument contrattoDocument = ContrattoDocument.Factory.newInstance();
      contrattoDocument.setContratto(contratto);
      contrattoDocument.save(baos);
      contrattoXml = baos.toString();
      baos.close();
    } catch (IOException e1) {
      System.out.println("Errore nella creazione del XML del contratto"); //, e1);
    }

    if (StringUtils.isNotEmpty(contrattoXml)) {
      System.out.println("Contratto in invio a Liguria Digitale:\n" + contrattoXml );
    }

    try {
      AppaltiLiguriaWebServiceStub appaltiLiguriaStub = new AppaltiLiguriaWebServiceStub(urlServizio);
      
      //String httpHeaderTokenName = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenName");
      String httpHeaderTokenValue = ConfigManager.getValore("it.eldasoft.appaltiLiguria.httpHeader.tokenValue");

      // Istruzioni recuperate da questo sito:
      // https://www.programcreek.com/java-api-examples/?class=org.apache.axis2.client.Options&method=setProperty

      Options options = appaltiLiguriaStub._getServiceClient().getOptions();
      List<Header> headerList = new ArrayList<Header>();
      Header header = new Header();
      header.setName(HTTPConstants.HEADER_AUTHORIZATION);
      header.setValue(httpHeaderTokenValue);
      headerList.add(header);
      options.setProperty(org.apache.axis2.transport.http.HTTPConstants.HTTP_HEADERS, headerList);
      appaltiLiguriaStub._getServiceClient().setOptions(options);
      
      appaltiLiguriaStub._getServiceClient().getOptions().setProperty(
          HTTPConstants.HEADER_AUTHORIZATION, httpHeaderTokenValue);
      
      InvioContratto invioContratto = InvioContratto.Factory.newInstance();
      invioContratto.setContratto(contratto);
      InvioContrattoDocument invioContrattoDocument = InvioContrattoDocument.Factory.newInstance();
      invioContrattoDocument.setInvioContratto(invioContratto);
      InvioContrattoResponseDocument invioContrattoResponseDocument = appaltiLiguriaStub.invioContratto(invioContrattoDocument);
      
      if (invioContrattoResponseDocument != null) {
        if (invioContrattoResponseDocument.getInvioContrattoResponse() != null) {
          InvioContrattoResponse invioContrattoResponse = invioContrattoResponseDocument.getInvioContrattoResponse();
          if (invioContrattoResponse.isSetReturn() && StringUtils.isNotEmpty(invioContrattoResponse.getReturn())) {
            String rispostaWs = invioContrattoResponse.getReturn();
            if (StringUtils.contains(rispostaWs, "ERR_") || StringUtils.contains(rispostaWs, "SAL/VARIANTI/SOSPENSIONI/CONTENSIOSI")) {
              //logger.error("L'invio dei dati del lotto con CIG = " + codiceCIG
              //  + " e' terminato con errore. Questo il messaggio di ritorno dal servizio: "
              //  + rispostaWs
              //  + " (Riferimento al lotto: CODGARA=" + codiceGara + ", CODLOTT=" + codiceLotto 
              //  + ". Rifermiento all'utente esecutore: SYSCON=" + profiloUtente.getId() + ")" );
              //result.setOk(Boolean.FALSE);
              //result.setMessaggi(new String[] { "" });
            } 
          }

          // Creazione del record in W9FLUSSI
          /*synchronized (contratto) {
            int w9FlussiId = this.genChiaviManager.getNextId("W9FLUSSI");
            this.sqlManager.update("insert into W9FLUSSI (IDFLUSSO,AREA,KEY01,KEY02,TINVIO2,DATINV,XML,CFSA,CODOGG,CODCOMP,AUTORE) values (?,?,?,?,?,?,?,?,?,?,?)",
              new Object[] { w9FlussiId, 1, codiceGara, codiceLotto, 1, new Timestamp(new GregorianCalendar().getTimeInMillis()),  
                contrattoXml, codiceFiscaleEnte, codiceCIG, profiloUtente.getId(), profiloUtente.getNome() });
          }*/
        }
      }
    } catch (AxisFault e) {
      StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
      if (StringUtils.isNotEmpty(e.getMessage())) {
        strBuffer.append(". Message: ");
        strBuffer.append(e.getMessage());
      }
      System.out.println(strBuffer.toString()); //, e);

      //result.setOk(Boolean.FALSE);
      //result.setMessaggi(new String[] { e.getMessage() });
      
      } catch (RemoteException re) {
        System.out.println("Errore remoto inaspettato durante l'interazione con il servizio"); //, re);

        //result.setOk(Boolean.FALSE);
        String str = "Errore remoto inaspettato durante l'invio dei dati";
        if (StringUtils.isNotEmpty(re.getLocalizedMessage())) {
          str = str.concat(" (".concat(re.getLocalizedMessage()).concat(")"));
        } else if (StringUtils.isNotEmpty(re.getMessage())) {
          str = str.concat(" (".concat(re.getMessage()).concat(")"));
        }
        //result.setMessaggi(new String[] { str });
      } catch (JAXBExceptionException je) {
        StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
        if (StringUtils.isNotEmpty(je.getMessage())) {
          strBuffer.append(". Message: ");
          strBuffer.append(je.getMessage());
        }
        System.out.println(strBuffer.toString()); //, je);

        //result.setOk(Boolean.FALSE);
        //result.setMessaggi(new String[] { je.getMessage() });

      } catch (WsException e) {
        StringBuffer strBuffer = new StringBuffer("Errore inaspettato durante l'interazione con il servizio");
        if (e.getFaultMessage()!= null) {
          strBuffer.append(". Message: ");
          strBuffer.append(e.getFaultMessage().getCheckVerifyFault().getMessage());
        }
        System.out.println(strBuffer.toString());//, e);

        //result.setOk(Boolean.FALSE);
        //result.setMessaggi(new String[] { e.getFaultMessage().getCheckVerifyFault().getMessage() });
      }

//    } else {
      System.out.println("Valorizzare tutte le properties della sezione 'Comune di Genova - Osservatorio Regione Liguria'");

      //result.setOk(Boolean.FALSE);
      //result.setMessaggi(new String[] { "Configurazione per la connessione all'Osservatorio Regionale incompleta. " });
  //  }
    
    //System.out.println(rispostaWs);

  }

}
