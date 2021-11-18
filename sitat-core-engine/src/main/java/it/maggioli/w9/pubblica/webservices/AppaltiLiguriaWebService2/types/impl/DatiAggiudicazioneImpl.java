/*
 * XML Type:  DatiAggiudicazione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiAggiudicazione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiAggiudicazioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione
{
    
    public DatiAggiudicazioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FINREGIONALE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FIN_REGIONALE");
    private static final javax.xml.namespace.QName FLAGIMPORTI$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_IMPORTI");
    private static final javax.xml.namespace.QName FLAGSICUREZZA$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SICUREZZA");
    private static final javax.xml.namespace.QName FLAGLIVELLOPROGETTUALE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_LIVELLO_PROGETTUALE");
    private static final javax.xml.namespace.QName VERIFICACAMPIONE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "VERIFICA_CAMPIONE");
    private static final javax.xml.namespace.QName DATAVERBAGGIUDICAZIONE$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName IMPORTOAGGIUDICAZIONE$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName IMPORTODISPOSIZIONE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_DISPOSIZIONE");
    private static final javax.xml.namespace.QName ASTAELETTRONICA$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ASTA_ELETTRONICA");
    private static final javax.xml.namespace.QName PERCRIBASSOAGG$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PERC_RIBASSO_AGG");
    private static final javax.xml.namespace.QName PERCOFFAUMENTO$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PERC_OFF_AUMENTO");
    private static final javax.xml.namespace.QName IMPORTOAPPALTO$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_APPALTO");
    private static final javax.xml.namespace.QName IMPORTOINTERVENTO$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_INTERVENTO");
    private static final javax.xml.namespace.QName IMPORTONETTOSIC$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_NETTO_SIC");
    private static final javax.xml.namespace.QName PROCEDURAACC$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PROCEDURA_ACC");
    private static final javax.xml.namespace.QName PREINFORMAZIONE$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PREINFORMAZIONE");
    private static final javax.xml.namespace.QName TERMINERIDOTTO$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TERMINE_RIDOTTO");
    private static final javax.xml.namespace.QName IDMODOINDIZIONE$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_MODO_INDIZIONE");
    private static final javax.xml.namespace.QName NUMIMPRESEINVITATE$36 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_IMPRESE_INVITATE");
    private static final javax.xml.namespace.QName NUMIMPRESERICHIESTAINVITO$38 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_IMPRESE_RICHIESTA_INVITO");
    private static final javax.xml.namespace.QName NUMIMPRESEOFFERENTI$40 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_IMPRESE_OFFERENTI");
    private static final javax.xml.namespace.QName NUMOFFERTEAMMESSE$42 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_OFFERTE_AMMESSE");
    private static final javax.xml.namespace.QName DATASCADENZARICHIESTAINVITO$44 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_SCADENZA_RICHIESTA_INVITO");
    private static final javax.xml.namespace.QName DATASCADENZAPRESOFFERTA$46 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_SCADENZA_PRES_OFFERTA");
    private static final javax.xml.namespace.QName IMPORTOLAVORI$48 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_LAVORI");
    private static final javax.xml.namespace.QName IMPORTOSERVIZI$50 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_SERVIZI");
    private static final javax.xml.namespace.QName IMPORTOFORNITURE$52 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_FORNITURE");
    private static final javax.xml.namespace.QName IMPORTOATTUAZIONESICUREZZA$54 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_ATTUAZIONE_SICUREZZA");
    private static final javax.xml.namespace.QName IMPORTOPROGETTAZIONE$56 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_PROGETTAZIONE");
    private static final javax.xml.namespace.QName REQUISITISS$58 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "REQUISITI_SS");
    private static final javax.xml.namespace.QName FLAGAQ$60 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_AQ");
    private static final javax.xml.namespace.QName DATAINVITO$62 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INVITO");
    private static final javax.xml.namespace.QName NUMMANIFINTERESSE$64 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_MANIF_INTERESSE");
    private static final javax.xml.namespace.QName DATAMANIFINTERESSE$66 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_MANIF_INTERESSE");
    private static final javax.xml.namespace.QName FLAGRICHSUBAPPALTO$68 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RICH_SUBAPPALTO");
    private static final javax.xml.namespace.QName NUMOFFERTEESCLUSE$70 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_OFFERTE_ESCLUSE");
    private static final javax.xml.namespace.QName OFFERTAMASSIMO$72 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "OFFERTA_MASSIMO");
    private static final javax.xml.namespace.QName OFFERTAMINIMA$74 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "OFFERTA_MINIMA");
    private static final javax.xml.namespace.QName VALSOGLIAANOMALIA$76 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "VAL_SOGLIA_ANOMALIA");
    private static final javax.xml.namespace.QName NUMOFFERTEFUORISOGLIA$78 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_OFFERTE_FUORI_SOGLIA");
    private static final javax.xml.namespace.QName NUMIMPESCLINSUFGIUST$80 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_IMP_ESCL_INSUF_GIUST");
    private static final javax.xml.namespace.QName CODSTRUMENTO$82 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COD_STRUMENTO");
    private static final javax.xml.namespace.QName IMPNONASSOG$84 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_NON_ASSOG");
    private static final javax.xml.namespace.QName MODALITARIAGGIUDICAZIONE$86 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MODALITA_RIAGGIUDICAZIONE");
    private static final javax.xml.namespace.QName DURATAAQ$88 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DURATA_AQ");
    private static final javax.xml.namespace.QName OPEREURBANIZSCOMPUTO$90 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "OPERE_URBANIZ_SCOMPUTO");
    private static final javax.xml.namespace.QName TIPOATTO$92 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TIPO_ATTO");
    private static final javax.xml.namespace.QName DATAATTO$94 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ATTO");
    private static final javax.xml.namespace.QName NUMATTO$96 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_ATTO");
    private static final javax.xml.namespace.QName PERCIVA$98 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PERC_IVA");
    private static final javax.xml.namespace.QName IMPORTOIVA$100 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_IVA");
    
    
    /**
     * Gets the "FIN_REGIONALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFINREGIONALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FINREGIONALE$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FIN_REGIONALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFINREGIONALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FINREGIONALE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FIN_REGIONALE" element
     */
    public void setFINREGIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum finregionale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FINREGIONALE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FINREGIONALE$0);
            }
            target.setEnumValue(finregionale);
        }
    }
    
    /**
     * Sets (as xml) the "FIN_REGIONALE" element
     */
    public void xsetFINREGIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType finregionale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FINREGIONALE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FINREGIONALE$0);
            }
            target.set(finregionale);
        }
    }
    
    /**
     * Gets the "FLAG_IMPORTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGIMPORTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$2, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_IMPORTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGIMPORTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_IMPORTI" element
     */
    public void setFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagimporti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGIMPORTI$2);
            }
            target.setEnumValue(flagimporti);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_IMPORTI" element
     */
    public void xsetFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagimporti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGIMPORTI$2);
            }
            target.set(flagimporti);
        }
    }
    
    /**
     * Gets the "FLAG_SICUREZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SICUREZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SICUREZZA" element
     */
    public void setFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSICUREZZA$4);
            }
            target.setEnumValue(flagsicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SICUREZZA" element
     */
    public void xsetFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGSICUREZZA$4);
            }
            target.set(flagsicurezza);
        }
    }
    
    /**
     * Gets the "FLAG_LIVELLO_PROGETTUALE" element
     */
    public java.lang.String getFLAGLIVELLOPROGETTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGLIVELLOPROGETTUALE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_LIVELLO_PROGETTUALE" element
     */
    public org.apache.xmlbeans.XmlString xgetFLAGLIVELLOPROGETTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGLIVELLOPROGETTUALE$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_LIVELLO_PROGETTUALE" element
     */
    public void setFLAGLIVELLOPROGETTUALE(java.lang.String flaglivelloprogettuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGLIVELLOPROGETTUALE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGLIVELLOPROGETTUALE$6);
            }
            target.setStringValue(flaglivelloprogettuale);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_LIVELLO_PROGETTUALE" element
     */
    public void xsetFLAGLIVELLOPROGETTUALE(org.apache.xmlbeans.XmlString flaglivelloprogettuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGLIVELLOPROGETTUALE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLAGLIVELLOPROGETTUALE$6);
            }
            target.set(flaglivelloprogettuale);
        }
    }
    
    /**
     * Gets the "VERIFICA_CAMPIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getVERIFICACAMPIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERIFICACAMPIONE$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "VERIFICA_CAMPIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetVERIFICACAMPIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(VERIFICACAMPIONE$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "VERIFICA_CAMPIONE" element
     */
    public void setVERIFICACAMPIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum verificacampione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERIFICACAMPIONE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERIFICACAMPIONE$8);
            }
            target.setEnumValue(verificacampione);
        }
    }
    
    /**
     * Sets (as xml) the "VERIFICA_CAMPIONE" element
     */
    public void xsetVERIFICACAMPIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType verificacampione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(VERIFICACAMPIONE$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(VERIFICACAMPIONE$8);
            }
            target.set(verificacampione);
        }
    }
    
    /**
     * Gets the "DATA_VERB_AGGIUDICAZIONE" element
     */
    public java.util.Calendar getDATAVERBAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBAGGIUDICAZIONE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_AGGIUDICAZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBAGGIUDICAZIONE$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_AGGIUDICAZIONE" element
     */
    public void setDATAVERBAGGIUDICAZIONE(java.util.Calendar dataverbaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBAGGIUDICAZIONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBAGGIUDICAZIONE$10);
            }
            target.setCalendarValue(dataverbaggiudicazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_AGGIUDICAZIONE" element
     */
    public void xsetDATAVERBAGGIUDICAZIONE(org.apache.xmlbeans.XmlDateTime dataverbaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBAGGIUDICAZIONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBAGGIUDICAZIONE$10);
            }
            target.set(dataverbaggiudicazione);
        }
    }
    
    /**
     * Gets the "IMPORTO_AGGIUDICAZIONE" element
     */
    public double getIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOAGGIUDICAZIONE$12, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_AGGIUDICAZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOAGGIUDICAZIONE$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_AGGIUDICAZIONE" element
     */
    public void setIMPORTOAGGIUDICAZIONE(double importoaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOAGGIUDICAZIONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOAGGIUDICAZIONE$12);
            }
            target.setDoubleValue(importoaggiudicazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_AGGIUDICAZIONE" element
     */
    public void xsetIMPORTOAGGIUDICAZIONE(org.apache.xmlbeans.XmlDouble importoaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOAGGIUDICAZIONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOAGGIUDICAZIONE$12);
            }
            target.set(importoaggiudicazione);
        }
    }
    
    /**
     * Gets the "IMPORTO_DISPOSIZIONE" element
     */
    public double getIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTODISPOSIZIONE$14, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_DISPOSIZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTODISPOSIZIONE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_DISPOSIZIONE" element
     */
    public boolean isSetIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTODISPOSIZIONE$14) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_DISPOSIZIONE" element
     */
    public void setIMPORTODISPOSIZIONE(double importodisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTODISPOSIZIONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTODISPOSIZIONE$14);
            }
            target.setDoubleValue(importodisposizione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_DISPOSIZIONE" element
     */
    public void xsetIMPORTODISPOSIZIONE(org.apache.xmlbeans.XmlDouble importodisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTODISPOSIZIONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTODISPOSIZIONE$14);
            }
            target.set(importodisposizione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_DISPOSIZIONE" element
     */
    public void unsetIMPORTODISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTODISPOSIZIONE$14, 0);
        }
    }
    
    /**
     * Gets the "ASTA_ELETTRONICA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASTAELETTRONICA$16, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ASTA_ELETTRONICA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetASTAELETTRONICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(ASTAELETTRONICA$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ASTA_ELETTRONICA" element
     */
    public void setASTAELETTRONICA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum astaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASTAELETTRONICA$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ASTAELETTRONICA$16);
            }
            target.setEnumValue(astaelettronica);
        }
    }
    
    /**
     * Sets (as xml) the "ASTA_ELETTRONICA" element
     */
    public void xsetASTAELETTRONICA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType astaelettronica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(ASTAELETTRONICA$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(ASTAELETTRONICA$16);
            }
            target.set(astaelettronica);
        }
    }
    
    /**
     * Gets the "PERC_RIBASSO_AGG" element
     */
    public float getPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCRIBASSOAGG$18, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERC_RIBASSO_AGG" element
     */
    public org.apache.xmlbeans.XmlFloat xgetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCRIBASSOAGG$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "PERC_RIBASSO_AGG" element
     */
    public boolean isSetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERCRIBASSOAGG$18) != 0;
        }
    }
    
    /**
     * Sets the "PERC_RIBASSO_AGG" element
     */
    public void setPERCRIBASSOAGG(float percribassoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCRIBASSOAGG$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERCRIBASSOAGG$18);
            }
            target.setFloatValue(percribassoagg);
        }
    }
    
    /**
     * Sets (as xml) the "PERC_RIBASSO_AGG" element
     */
    public void xsetPERCRIBASSOAGG(org.apache.xmlbeans.XmlFloat percribassoagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCRIBASSOAGG$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(PERCRIBASSOAGG$18);
            }
            target.set(percribassoagg);
        }
    }
    
    /**
     * Unsets the "PERC_RIBASSO_AGG" element
     */
    public void unsetPERCRIBASSOAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERCRIBASSOAGG$18, 0);
        }
    }
    
    /**
     * Gets the "PERC_OFF_AUMENTO" element
     */
    public float getPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCOFFAUMENTO$20, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERC_OFF_AUMENTO" element
     */
    public org.apache.xmlbeans.XmlFloat xgetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCOFFAUMENTO$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "PERC_OFF_AUMENTO" element
     */
    public boolean isSetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERCOFFAUMENTO$20) != 0;
        }
    }
    
    /**
     * Sets the "PERC_OFF_AUMENTO" element
     */
    public void setPERCOFFAUMENTO(float percoffaumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCOFFAUMENTO$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERCOFFAUMENTO$20);
            }
            target.setFloatValue(percoffaumento);
        }
    }
    
    /**
     * Sets (as xml) the "PERC_OFF_AUMENTO" element
     */
    public void xsetPERCOFFAUMENTO(org.apache.xmlbeans.XmlFloat percoffaumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCOFFAUMENTO$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(PERCOFFAUMENTO$20);
            }
            target.set(percoffaumento);
        }
    }
    
    /**
     * Unsets the "PERC_OFF_AUMENTO" element
     */
    public void unsetPERCOFFAUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERCOFFAUMENTO$20, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_APPALTO" element
     */
    public double getIMPORTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOAPPALTO$22, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_APPALTO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOAPPALTO$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_APPALTO" element
     */
    public boolean isSetIMPORTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOAPPALTO$22) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_APPALTO" element
     */
    public void setIMPORTOAPPALTO(double importoappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOAPPALTO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOAPPALTO$22);
            }
            target.setDoubleValue(importoappalto);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_APPALTO" element
     */
    public void xsetIMPORTOAPPALTO(org.apache.xmlbeans.XmlDouble importoappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOAPPALTO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOAPPALTO$22);
            }
            target.set(importoappalto);
        }
    }
    
    /**
     * Unsets the "IMPORTO_APPALTO" element
     */
    public void unsetIMPORTOAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOAPPALTO$22, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_INTERVENTO" element
     */
    public double getIMPORTOINTERVENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOINTERVENTO$24, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_INTERVENTO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOINTERVENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOINTERVENTO$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_INTERVENTO" element
     */
    public boolean isSetIMPORTOINTERVENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOINTERVENTO$24) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_INTERVENTO" element
     */
    public void setIMPORTOINTERVENTO(double importointervento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOINTERVENTO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOINTERVENTO$24);
            }
            target.setDoubleValue(importointervento);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_INTERVENTO" element
     */
    public void xsetIMPORTOINTERVENTO(org.apache.xmlbeans.XmlDouble importointervento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOINTERVENTO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOINTERVENTO$24);
            }
            target.set(importointervento);
        }
    }
    
    /**
     * Unsets the "IMPORTO_INTERVENTO" element
     */
    public void unsetIMPORTOINTERVENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOINTERVENTO$24, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_NETTO_SIC" element
     */
    public double getIMPORTONETTOSIC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTONETTOSIC$26, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_NETTO_SIC" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTONETTOSIC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTONETTOSIC$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_NETTO_SIC" element
     */
    public boolean isSetIMPORTONETTOSIC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTONETTOSIC$26) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_NETTO_SIC" element
     */
    public void setIMPORTONETTOSIC(double importonettosic)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTONETTOSIC$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTONETTOSIC$26);
            }
            target.setDoubleValue(importonettosic);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_NETTO_SIC" element
     */
    public void xsetIMPORTONETTOSIC(org.apache.xmlbeans.XmlDouble importonettosic)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTONETTOSIC$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTONETTOSIC$26);
            }
            target.set(importonettosic);
        }
    }
    
    /**
     * Unsets the "IMPORTO_NETTO_SIC" element
     */
    public void unsetIMPORTONETTOSIC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTONETTOSIC$26, 0);
        }
    }
    
    /**
     * Gets the "PROCEDURA_ACC" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPROCEDURAACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCEDURAACC$28, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROCEDURA_ACC" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPROCEDURAACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PROCEDURAACC$28, 0);
            return target;
        }
    }
    
    /**
     * Sets the "PROCEDURA_ACC" element
     */
    public void setPROCEDURAACC(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum proceduraacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCEDURAACC$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCEDURAACC$28);
            }
            target.setEnumValue(proceduraacc);
        }
    }
    
    /**
     * Sets (as xml) the "PROCEDURA_ACC" element
     */
    public void xsetPROCEDURAACC(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType proceduraacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PROCEDURAACC$28, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(PROCEDURAACC$28);
            }
            target.set(proceduraacc);
        }
    }
    
    /**
     * Gets the "PREINFORMAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPREINFORMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREINFORMAZIONE$30, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PREINFORMAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPREINFORMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PREINFORMAZIONE$30, 0);
            return target;
        }
    }
    
    /**
     * Sets the "PREINFORMAZIONE" element
     */
    public void setPREINFORMAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum preinformazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREINFORMAZIONE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREINFORMAZIONE$30);
            }
            target.setEnumValue(preinformazione);
        }
    }
    
    /**
     * Sets (as xml) the "PREINFORMAZIONE" element
     */
    public void xsetPREINFORMAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType preinformazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PREINFORMAZIONE$30, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(PREINFORMAZIONE$30);
            }
            target.set(preinformazione);
        }
    }
    
    /**
     * Gets the "TERMINE_RIDOTTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getTERMINERIDOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TERMINERIDOTTO$32, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "TERMINE_RIDOTTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetTERMINERIDOTTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(TERMINERIDOTTO$32, 0);
            return target;
        }
    }
    
    /**
     * Sets the "TERMINE_RIDOTTO" element
     */
    public void setTERMINERIDOTTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum termineridotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TERMINERIDOTTO$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TERMINERIDOTTO$32);
            }
            target.setEnumValue(termineridotto);
        }
    }
    
    /**
     * Sets (as xml) the "TERMINE_RIDOTTO" element
     */
    public void xsetTERMINERIDOTTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType termineridotto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(TERMINERIDOTTO$32, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(TERMINERIDOTTO$32);
            }
            target.set(termineridotto);
        }
    }
    
    /**
     * Gets the "ID_MODO_INDIZIONE" element
     */
    public java.lang.String getIDMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMODOINDIZIONE$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MODO_INDIZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetIDMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMODOINDIZIONE$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_MODO_INDIZIONE" element
     */
    public boolean isSetIDMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDMODOINDIZIONE$34) != 0;
        }
    }
    
    /**
     * Sets the "ID_MODO_INDIZIONE" element
     */
    public void setIDMODOINDIZIONE(java.lang.String idmodoindizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMODOINDIZIONE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDMODOINDIZIONE$34);
            }
            target.setStringValue(idmodoindizione);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MODO_INDIZIONE" element
     */
    public void xsetIDMODOINDIZIONE(org.apache.xmlbeans.XmlString idmodoindizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMODOINDIZIONE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDMODOINDIZIONE$34);
            }
            target.set(idmodoindizione);
        }
    }
    
    /**
     * Unsets the "ID_MODO_INDIZIONE" element
     */
    public void unsetIDMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDMODOINDIZIONE$34, 0);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_INVITATE" element
     */
    public long getNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESEINVITATE$36, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_INVITATE" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESEINVITATE$36, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_IMPRESE_INVITATE" element
     */
    public boolean isSetNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMIMPRESEINVITATE$36) != 0;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_INVITATE" element
     */
    public void setNUMIMPRESEINVITATE(long numimpreseinvitate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESEINVITATE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMIMPRESEINVITATE$36);
            }
            target.setLongValue(numimpreseinvitate);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_INVITATE" element
     */
    public void xsetNUMIMPRESEINVITATE(org.apache.xmlbeans.XmlLong numimpreseinvitate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESEINVITATE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMIMPRESEINVITATE$36);
            }
            target.set(numimpreseinvitate);
        }
    }
    
    /**
     * Unsets the "NUM_IMPRESE_INVITATE" element
     */
    public void unsetNUMIMPRESEINVITATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMIMPRESEINVITATE$36, 0);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public long getNUMIMPRESERICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESERICHIESTAINVITO$38, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMIMPRESERICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESERICHIESTAINVITO$38, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public boolean isSetNUMIMPRESERICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMIMPRESERICHIESTAINVITO$38) != 0;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public void setNUMIMPRESERICHIESTAINVITO(long numimpreserichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESERICHIESTAINVITO$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMIMPRESERICHIESTAINVITO$38);
            }
            target.setLongValue(numimpreserichiestainvito);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public void xsetNUMIMPRESERICHIESTAINVITO(org.apache.xmlbeans.XmlLong numimpreserichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESERICHIESTAINVITO$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMIMPRESERICHIESTAINVITO$38);
            }
            target.set(numimpreserichiestainvito);
        }
    }
    
    /**
     * Unsets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    public void unsetNUMIMPRESERICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMIMPRESERICHIESTAINVITO$38, 0);
        }
    }
    
    /**
     * Gets the "NUM_IMPRESE_OFFERENTI" element
     */
    public long getNUMIMPRESEOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESEOFFERENTI$40, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_OFFERENTI" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMIMPRESEOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESEOFFERENTI$40, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_IMPRESE_OFFERENTI" element
     */
    public void setNUMIMPRESEOFFERENTI(long numimpreseofferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPRESEOFFERENTI$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMIMPRESEOFFERENTI$40);
            }
            target.setLongValue(numimpreseofferenti);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_OFFERENTI" element
     */
    public void xsetNUMIMPRESEOFFERENTI(org.apache.xmlbeans.XmlLong numimpreseofferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPRESEOFFERENTI$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMIMPRESEOFFERENTI$40);
            }
            target.set(numimpreseofferenti);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_AMMESSE" element
     */
    public long getNUMOFFERTEAMMESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEAMMESSE$42, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_AMMESSE" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMOFFERTEAMMESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEAMMESSE$42, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_AMMESSE" element
     */
    public void setNUMOFFERTEAMMESSE(long numofferteammesse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEAMMESSE$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMOFFERTEAMMESSE$42);
            }
            target.setLongValue(numofferteammesse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_AMMESSE" element
     */
    public void xsetNUMOFFERTEAMMESSE(org.apache.xmlbeans.XmlLong numofferteammesse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEAMMESSE$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMOFFERTEAMMESSE$42);
            }
            target.set(numofferteammesse);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public java.util.Calendar getDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZARICHIESTAINVITO$44, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZARICHIESTAINVITO$44, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public boolean isSetDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATASCADENZARICHIESTAINVITO$44) != 0;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public void setDATASCADENZARICHIESTAINVITO(java.util.Calendar datascadenzarichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZARICHIESTAINVITO$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATASCADENZARICHIESTAINVITO$44);
            }
            target.setCalendarValue(datascadenzarichiestainvito);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public void xsetDATASCADENZARICHIESTAINVITO(org.apache.xmlbeans.XmlDateTime datascadenzarichiestainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZARICHIESTAINVITO$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATASCADENZARICHIESTAINVITO$44);
            }
            target.set(datascadenzarichiestainvito);
        }
    }
    
    /**
     * Unsets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    public void unsetDATASCADENZARICHIESTAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATASCADENZARICHIESTAINVITO$44, 0);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    public java.util.Calendar getDATASCADENZAPRESOFFERTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZAPRESOFFERTA$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATASCADENZAPRESOFFERTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZAPRESOFFERTA$46, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    public void setDATASCADENZAPRESOFFERTA(java.util.Calendar datascadenzapresofferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZAPRESOFFERTA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATASCADENZAPRESOFFERTA$46);
            }
            target.setCalendarValue(datascadenzapresofferta);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    public void xsetDATASCADENZAPRESOFFERTA(org.apache.xmlbeans.XmlDateTime datascadenzapresofferta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZAPRESOFFERTA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATASCADENZAPRESOFFERTA$46);
            }
            target.set(datascadenzapresofferta);
        }
    }
    
    /**
     * Gets the "IMPORTO_LAVORI" element
     */
    public double getIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLAVORI$48, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_LAVORI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLAVORI$48, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_LAVORI" element
     */
    public boolean isSetIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOLAVORI$48) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_LAVORI" element
     */
    public void setIMPORTOLAVORI(double importolavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLAVORI$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOLAVORI$48);
            }
            target.setDoubleValue(importolavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_LAVORI" element
     */
    public void xsetIMPORTOLAVORI(org.apache.xmlbeans.XmlDouble importolavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLAVORI$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOLAVORI$48);
            }
            target.set(importolavori);
        }
    }
    
    /**
     * Unsets the "IMPORTO_LAVORI" element
     */
    public void unsetIMPORTOLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOLAVORI$48, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_SERVIZI" element
     */
    public double getIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSERVIZI$50, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SERVIZI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSERVIZI$50, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_SERVIZI" element
     */
    public boolean isSetIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOSERVIZI$50) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_SERVIZI" element
     */
    public void setIMPORTOSERVIZI(double importoservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSERVIZI$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSERVIZI$50);
            }
            target.setDoubleValue(importoservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SERVIZI" element
     */
    public void xsetIMPORTOSERVIZI(org.apache.xmlbeans.XmlDouble importoservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSERVIZI$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOSERVIZI$50);
            }
            target.set(importoservizi);
        }
    }
    
    /**
     * Unsets the "IMPORTO_SERVIZI" element
     */
    public void unsetIMPORTOSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOSERVIZI$50, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_FORNITURE" element
     */
    public double getIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFORNITURE$52, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_FORNITURE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFORNITURE$52, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_FORNITURE" element
     */
    public boolean isSetIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOFORNITURE$52) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_FORNITURE" element
     */
    public void setIMPORTOFORNITURE(double importoforniture)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFORNITURE$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOFORNITURE$52);
            }
            target.setDoubleValue(importoforniture);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_FORNITURE" element
     */
    public void xsetIMPORTOFORNITURE(org.apache.xmlbeans.XmlDouble importoforniture)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFORNITURE$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOFORNITURE$52);
            }
            target.set(importoforniture);
        }
    }
    
    /**
     * Unsets the "IMPORTO_FORNITURE" element
     */
    public void unsetIMPORTOFORNITURE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOFORNITURE$52, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public double getIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOATTUAZIONESICUREZZA$54, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOATTUAZIONESICUREZZA$54, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public boolean isSetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOATTUAZIONESICUREZZA$54) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public void setIMPORTOATTUAZIONESICUREZZA(double importoattuazionesicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOATTUAZIONESICUREZZA$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOATTUAZIONESICUREZZA$54);
            }
            target.setDoubleValue(importoattuazionesicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public void xsetIMPORTOATTUAZIONESICUREZZA(org.apache.xmlbeans.XmlDouble importoattuazionesicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOATTUAZIONESICUREZZA$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOATTUAZIONESICUREZZA$54);
            }
            target.set(importoattuazionesicurezza);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    public void unsetIMPORTOATTUAZIONESICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOATTUAZIONESICUREZZA$54, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_PROGETTAZIONE" element
     */
    public double getIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOPROGETTAZIONE$56, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_PROGETTAZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOPROGETTAZIONE$56, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_PROGETTAZIONE" element
     */
    public boolean isSetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOPROGETTAZIONE$56) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_PROGETTAZIONE" element
     */
    public void setIMPORTOPROGETTAZIONE(double importoprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOPROGETTAZIONE$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOPROGETTAZIONE$56);
            }
            target.setDoubleValue(importoprogettazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_PROGETTAZIONE" element
     */
    public void xsetIMPORTOPROGETTAZIONE(org.apache.xmlbeans.XmlDouble importoprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOPROGETTAZIONE$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOPROGETTAZIONE$56);
            }
            target.set(importoprogettazione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_PROGETTAZIONE" element
     */
    public void unsetIMPORTOPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOPROGETTAZIONE$56, 0);
        }
    }
    
    /**
     * Gets the "REQUISITI_SS" element
     */
    public java.lang.String getREQUISITISS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUISITISS$58, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "REQUISITI_SS" element
     */
    public org.apache.xmlbeans.XmlString xgetREQUISITISS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQUISITISS$58, 0);
            return target;
        }
    }
    
    /**
     * True if has "REQUISITI_SS" element
     */
    public boolean isSetREQUISITISS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REQUISITISS$58) != 0;
        }
    }
    
    /**
     * Sets the "REQUISITI_SS" element
     */
    public void setREQUISITISS(java.lang.String requisitiss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUISITISS$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REQUISITISS$58);
            }
            target.setStringValue(requisitiss);
        }
    }
    
    /**
     * Sets (as xml) the "REQUISITI_SS" element
     */
    public void xsetREQUISITISS(org.apache.xmlbeans.XmlString requisitiss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQUISITISS$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REQUISITISS$58);
            }
            target.set(requisitiss);
        }
    }
    
    /**
     * Unsets the "REQUISITI_SS" element
     */
    public void unsetREQUISITISS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REQUISITISS$58, 0);
        }
    }
    
    /**
     * Gets the "FLAG_AQ" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGAQ$60, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_AQ" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGAQ$60, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_AQ" element
     */
    public boolean isSetFLAGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGAQ$60) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_AQ" element
     */
    public void setFLAGAQ(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGAQ$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGAQ$60);
            }
            target.setEnumValue(flagaq);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_AQ" element
     */
    public void xsetFLAGAQ(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGAQ$60, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGAQ$60);
            }
            target.set(flagaq);
        }
    }
    
    /**
     * Unsets the "FLAG_AQ" element
     */
    public void unsetFLAGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGAQ$60, 0);
        }
    }
    
    /**
     * Gets the "DATA_INVITO" element
     */
    public java.util.Calendar getDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINVITO$62, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INVITO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINVITO$62, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INVITO" element
     */
    public boolean isSetDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINVITO$62) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INVITO" element
     */
    public void setDATAINVITO(java.util.Calendar datainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINVITO$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINVITO$62);
            }
            target.setCalendarValue(datainvito);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INVITO" element
     */
    public void xsetDATAINVITO(org.apache.xmlbeans.XmlDateTime datainvito)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINVITO$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINVITO$62);
            }
            target.set(datainvito);
        }
    }
    
    /**
     * Unsets the "DATA_INVITO" element
     */
    public void unsetDATAINVITO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINVITO$62, 0);
        }
    }
    
    /**
     * Gets the "NUM_MANIF_INTERESSE" element
     */
    public long getNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMMANIFINTERESSE$64, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_MANIF_INTERESSE" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMMANIFINTERESSE$64, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_MANIF_INTERESSE" element
     */
    public boolean isSetNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMMANIFINTERESSE$64) != 0;
        }
    }
    
    /**
     * Sets the "NUM_MANIF_INTERESSE" element
     */
    public void setNUMMANIFINTERESSE(long nummanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMMANIFINTERESSE$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMMANIFINTERESSE$64);
            }
            target.setLongValue(nummanifinteresse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_MANIF_INTERESSE" element
     */
    public void xsetNUMMANIFINTERESSE(org.apache.xmlbeans.XmlLong nummanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMMANIFINTERESSE$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMMANIFINTERESSE$64);
            }
            target.set(nummanifinteresse);
        }
    }
    
    /**
     * Unsets the "NUM_MANIF_INTERESSE" element
     */
    public void unsetNUMMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMMANIFINTERESSE$64, 0);
        }
    }
    
    /**
     * Gets the "DATA_MANIF_INTERESSE" element
     */
    public java.util.Calendar getDATAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAMANIFINTERESSE$66, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_MANIF_INTERESSE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAMANIFINTERESSE$66, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_MANIF_INTERESSE" element
     */
    public boolean isSetDATAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAMANIFINTERESSE$66) != 0;
        }
    }
    
    /**
     * Sets the "DATA_MANIF_INTERESSE" element
     */
    public void setDATAMANIFINTERESSE(java.util.Calendar datamanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAMANIFINTERESSE$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAMANIFINTERESSE$66);
            }
            target.setCalendarValue(datamanifinteresse);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_MANIF_INTERESSE" element
     */
    public void xsetDATAMANIFINTERESSE(org.apache.xmlbeans.XmlDateTime datamanifinteresse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAMANIFINTERESSE$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAMANIFINTERESSE$66);
            }
            target.set(datamanifinteresse);
        }
    }
    
    /**
     * Unsets the "DATA_MANIF_INTERESSE" element
     */
    public void unsetDATAMANIFINTERESSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAMANIFINTERESSE$66, 0);
        }
    }
    
    /**
     * Gets the "FLAG_RICH_SUBAPPALTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRICHSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRICHSUBAPPALTO$68, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RICH_SUBAPPALTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRICHSUBAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRICHSUBAPPALTO$68, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RICH_SUBAPPALTO" element
     */
    public void setFLAGRICHSUBAPPALTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagrichsubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRICHSUBAPPALTO$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRICHSUBAPPALTO$68);
            }
            target.setEnumValue(flagrichsubappalto);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RICH_SUBAPPALTO" element
     */
    public void xsetFLAGRICHSUBAPPALTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagrichsubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRICHSUBAPPALTO$68, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGRICHSUBAPPALTO$68);
            }
            target.set(flagrichsubappalto);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_ESCLUSE" element
     */
    public long getNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEESCLUSE$70, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_ESCLUSE" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEESCLUSE$70, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_OFFERTE_ESCLUSE" element
     */
    public boolean isSetNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMOFFERTEESCLUSE$70) != 0;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_ESCLUSE" element
     */
    public void setNUMOFFERTEESCLUSE(long numofferteescluse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEESCLUSE$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMOFFERTEESCLUSE$70);
            }
            target.setLongValue(numofferteescluse);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_ESCLUSE" element
     */
    public void xsetNUMOFFERTEESCLUSE(org.apache.xmlbeans.XmlLong numofferteescluse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEESCLUSE$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMOFFERTEESCLUSE$70);
            }
            target.set(numofferteescluse);
        }
    }
    
    /**
     * Unsets the "NUM_OFFERTE_ESCLUSE" element
     */
    public void unsetNUMOFFERTEESCLUSE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMOFFERTEESCLUSE$70, 0);
        }
    }
    
    /**
     * Gets the "OFFERTA_MASSIMO" element
     */
    public float getOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFERTAMASSIMO$72, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "OFFERTA_MASSIMO" element
     */
    public org.apache.xmlbeans.XmlFloat xgetOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(OFFERTAMASSIMO$72, 0);
            return target;
        }
    }
    
    /**
     * True if has "OFFERTA_MASSIMO" element
     */
    public boolean isSetOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OFFERTAMASSIMO$72) != 0;
        }
    }
    
    /**
     * Sets the "OFFERTA_MASSIMO" element
     */
    public void setOFFERTAMASSIMO(float offertamassimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFERTAMASSIMO$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFERTAMASSIMO$72);
            }
            target.setFloatValue(offertamassimo);
        }
    }
    
    /**
     * Sets (as xml) the "OFFERTA_MASSIMO" element
     */
    public void xsetOFFERTAMASSIMO(org.apache.xmlbeans.XmlFloat offertamassimo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(OFFERTAMASSIMO$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(OFFERTAMASSIMO$72);
            }
            target.set(offertamassimo);
        }
    }
    
    /**
     * Unsets the "OFFERTA_MASSIMO" element
     */
    public void unsetOFFERTAMASSIMO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OFFERTAMASSIMO$72, 0);
        }
    }
    
    /**
     * Gets the "OFFERTA_MINIMA" element
     */
    public float getOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFERTAMINIMA$74, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "OFFERTA_MINIMA" element
     */
    public org.apache.xmlbeans.XmlFloat xgetOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(OFFERTAMINIMA$74, 0);
            return target;
        }
    }
    
    /**
     * True if has "OFFERTA_MINIMA" element
     */
    public boolean isSetOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OFFERTAMINIMA$74) != 0;
        }
    }
    
    /**
     * Sets the "OFFERTA_MINIMA" element
     */
    public void setOFFERTAMINIMA(float offertaminima)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFERTAMINIMA$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFERTAMINIMA$74);
            }
            target.setFloatValue(offertaminima);
        }
    }
    
    /**
     * Sets (as xml) the "OFFERTA_MINIMA" element
     */
    public void xsetOFFERTAMINIMA(org.apache.xmlbeans.XmlFloat offertaminima)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(OFFERTAMINIMA$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(OFFERTAMINIMA$74);
            }
            target.set(offertaminima);
        }
    }
    
    /**
     * Unsets the "OFFERTA_MINIMA" element
     */
    public void unsetOFFERTAMINIMA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OFFERTAMINIMA$74, 0);
        }
    }
    
    /**
     * Gets the "VAL_SOGLIA_ANOMALIA" element
     */
    public float getVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALSOGLIAANOMALIA$76, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "VAL_SOGLIA_ANOMALIA" element
     */
    public org.apache.xmlbeans.XmlFloat xgetVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(VALSOGLIAANOMALIA$76, 0);
            return target;
        }
    }
    
    /**
     * True if has "VAL_SOGLIA_ANOMALIA" element
     */
    public boolean isSetVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VALSOGLIAANOMALIA$76) != 0;
        }
    }
    
    /**
     * Sets the "VAL_SOGLIA_ANOMALIA" element
     */
    public void setVALSOGLIAANOMALIA(float valsogliaanomalia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALSOGLIAANOMALIA$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALSOGLIAANOMALIA$76);
            }
            target.setFloatValue(valsogliaanomalia);
        }
    }
    
    /**
     * Sets (as xml) the "VAL_SOGLIA_ANOMALIA" element
     */
    public void xsetVALSOGLIAANOMALIA(org.apache.xmlbeans.XmlFloat valsogliaanomalia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(VALSOGLIAANOMALIA$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(VALSOGLIAANOMALIA$76);
            }
            target.set(valsogliaanomalia);
        }
    }
    
    /**
     * Unsets the "VAL_SOGLIA_ANOMALIA" element
     */
    public void unsetVALSOGLIAANOMALIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VALSOGLIAANOMALIA$76, 0);
        }
    }
    
    /**
     * Gets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public long getNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEFUORISOGLIA$78, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEFUORISOGLIA$78, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public boolean isSetNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMOFFERTEFUORISOGLIA$78) != 0;
        }
    }
    
    /**
     * Sets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public void setNUMOFFERTEFUORISOGLIA(long numoffertefuorisoglia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMOFFERTEFUORISOGLIA$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMOFFERTEFUORISOGLIA$78);
            }
            target.setLongValue(numoffertefuorisoglia);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public void xsetNUMOFFERTEFUORISOGLIA(org.apache.xmlbeans.XmlLong numoffertefuorisoglia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMOFFERTEFUORISOGLIA$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMOFFERTEFUORISOGLIA$78);
            }
            target.set(numoffertefuorisoglia);
        }
    }
    
    /**
     * Unsets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    public void unsetNUMOFFERTEFUORISOGLIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMOFFERTEFUORISOGLIA$78, 0);
        }
    }
    
    /**
     * Gets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public long getNUMIMPESCLINSUFGIUST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPESCLINSUFGIUST$80, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMIMPESCLINSUFGIUST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPESCLINSUFGIUST$80, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public boolean isSetNUMIMPESCLINSUFGIUST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMIMPESCLINSUFGIUST$80) != 0;
        }
    }
    
    /**
     * Sets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public void setNUMIMPESCLINSUFGIUST(long numimpesclinsufgiust)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMIMPESCLINSUFGIUST$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMIMPESCLINSUFGIUST$80);
            }
            target.setLongValue(numimpesclinsufgiust);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public void xsetNUMIMPESCLINSUFGIUST(org.apache.xmlbeans.XmlLong numimpesclinsufgiust)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMIMPESCLINSUFGIUST$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMIMPESCLINSUFGIUST$80);
            }
            target.set(numimpesclinsufgiust);
        }
    }
    
    /**
     * Unsets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    public void unsetNUMIMPESCLINSUFGIUST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMIMPESCLINSUFGIUST$80, 0);
        }
    }
    
    /**
     * Gets the "COD_STRUMENTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.Enum getCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODSTRUMENTO$82, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "COD_STRUMENTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType xgetCODSTRUMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType)get_store().find_element_user(CODSTRUMENTO$82, 0);
            return target;
        }
    }
    
    /**
     * Sets the "COD_STRUMENTO" element
     */
    public void setCODSTRUMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.Enum codstrumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODSTRUMENTO$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODSTRUMENTO$82);
            }
            target.setEnumValue(codstrumento);
        }
    }
    
    /**
     * Sets (as xml) the "COD_STRUMENTO" element
     */
    public void xsetCODSTRUMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType codstrumento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType)get_store().find_element_user(CODSTRUMENTO$82, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType)get_store().add_element_user(CODSTRUMENTO$82);
            }
            target.set(codstrumento);
        }
    }
    
    /**
     * Gets the "IMP_NON_ASSOG" element
     */
    public double getIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPNONASSOG$84, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_NON_ASSOG" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPNONASSOG$84, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_NON_ASSOG" element
     */
    public boolean isSetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPNONASSOG$84) != 0;
        }
    }
    
    /**
     * Sets the "IMP_NON_ASSOG" element
     */
    public void setIMPNONASSOG(double impnonassog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPNONASSOG$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPNONASSOG$84);
            }
            target.setDoubleValue(impnonassog);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_NON_ASSOG" element
     */
    public void xsetIMPNONASSOG(org.apache.xmlbeans.XmlDouble impnonassog)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPNONASSOG$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPNONASSOG$84);
            }
            target.set(impnonassog);
        }
    }
    
    /**
     * Unsets the "IMP_NON_ASSOG" element
     */
    public void unsetIMPNONASSOG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPNONASSOG$84, 0);
        }
    }
    
    /**
     * Gets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    public java.lang.String getMODALITARIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODALITARIAGGIUDICAZIONE$86, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODALITA_RIAGGIUDICAZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetMODALITARIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODALITARIAGGIUDICAZIONE$86, 0);
            return target;
        }
    }
    
    /**
     * True if has "MODALITA_RIAGGIUDICAZIONE" element
     */
    public boolean isSetMODALITARIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MODALITARIAGGIUDICAZIONE$86) != 0;
        }
    }
    
    /**
     * Sets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    public void setMODALITARIAGGIUDICAZIONE(java.lang.String modalitariaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODALITARIAGGIUDICAZIONE$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODALITARIAGGIUDICAZIONE$86);
            }
            target.setStringValue(modalitariaggiudicazione);
        }
    }
    
    /**
     * Sets (as xml) the "MODALITA_RIAGGIUDICAZIONE" element
     */
    public void xsetMODALITARIAGGIUDICAZIONE(org.apache.xmlbeans.XmlString modalitariaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODALITARIAGGIUDICAZIONE$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODALITARIAGGIUDICAZIONE$86);
            }
            target.set(modalitariaggiudicazione);
        }
    }
    
    /**
     * Unsets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    public void unsetMODALITARIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MODALITARIAGGIUDICAZIONE$86, 0);
        }
    }
    
    /**
     * Gets the "DURATA_AQ" element
     */
    public long getDURATAAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATAAQ$88, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_AQ" element
     */
    public org.apache.xmlbeans.XmlLong xgetDURATAAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(DURATAAQ$88, 0);
            return target;
        }
    }
    
    /**
     * True if has "DURATA_AQ" element
     */
    public boolean isSetDURATAAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DURATAAQ$88) != 0;
        }
    }
    
    /**
     * Sets the "DURATA_AQ" element
     */
    public void setDURATAAQ(long durataaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATAAQ$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DURATAAQ$88);
            }
            target.setLongValue(durataaq);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_AQ" element
     */
    public void xsetDURATAAQ(org.apache.xmlbeans.XmlLong durataaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(DURATAAQ$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(DURATAAQ$88);
            }
            target.set(durataaq);
        }
    }
    
    /**
     * Unsets the "DURATA_AQ" element
     */
    public void unsetDURATAAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DURATAAQ$88, 0);
        }
    }
    
    /**
     * Gets the "OPERE_URBANIZ_SCOMPUTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getOPEREURBANIZSCOMPUTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPEREURBANIZSCOMPUTO$90, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "OPERE_URBANIZ_SCOMPUTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetOPEREURBANIZSCOMPUTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(OPEREURBANIZSCOMPUTO$90, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OPERE_URBANIZ_SCOMPUTO" element
     */
    public void setOPEREURBANIZSCOMPUTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum opereurbanizscomputo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPEREURBANIZSCOMPUTO$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OPEREURBANIZSCOMPUTO$90);
            }
            target.setEnumValue(opereurbanizscomputo);
        }
    }
    
    /**
     * Sets (as xml) the "OPERE_URBANIZ_SCOMPUTO" element
     */
    public void xsetOPEREURBANIZSCOMPUTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType opereurbanizscomputo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(OPEREURBANIZSCOMPUTO$90, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(OPEREURBANIZSCOMPUTO$90);
            }
            target.set(opereurbanizscomputo);
        }
    }
    
    /**
     * Gets the "TIPO_ATTO" element
     */
    public java.lang.String getTIPOATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOATTO$92, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPO_ATTO" element
     */
    public org.apache.xmlbeans.XmlString xgetTIPOATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOATTO$92, 0);
            return target;
        }
    }
    
    /**
     * True if has "TIPO_ATTO" element
     */
    public boolean isSetTIPOATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPOATTO$92) != 0;
        }
    }
    
    /**
     * Sets the "TIPO_ATTO" element
     */
    public void setTIPOATTO(java.lang.String tipoatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOATTO$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOATTO$92);
            }
            target.setStringValue(tipoatto);
        }
    }
    
    /**
     * Sets (as xml) the "TIPO_ATTO" element
     */
    public void xsetTIPOATTO(org.apache.xmlbeans.XmlString tipoatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOATTO$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TIPOATTO$92);
            }
            target.set(tipoatto);
        }
    }
    
    /**
     * Unsets the "TIPO_ATTO" element
     */
    public void unsetTIPOATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPOATTO$92, 0);
        }
    }
    
    /**
     * Gets the "DATA_ATTO" element
     */
    public java.util.Calendar getDATAATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAATTO$94, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ATTO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAATTO$94, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ATTO" element
     */
    public boolean isSetDATAATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAATTO$94) != 0;
        }
    }
    
    /**
     * Sets the "DATA_ATTO" element
     */
    public void setDATAATTO(java.util.Calendar dataatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAATTO$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAATTO$94);
            }
            target.setCalendarValue(dataatto);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ATTO" element
     */
    public void xsetDATAATTO(org.apache.xmlbeans.XmlDateTime dataatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAATTO$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAATTO$94);
            }
            target.set(dataatto);
        }
    }
    
    /**
     * Unsets the "DATA_ATTO" element
     */
    public void unsetDATAATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAATTO$94, 0);
        }
    }
    
    /**
     * Gets the "NUM_ATTO" element
     */
    public java.lang.String getNUMATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMATTO$96, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_ATTO" element
     */
    public org.apache.xmlbeans.XmlString xgetNUMATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUMATTO$96, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_ATTO" element
     */
    public boolean isSetNUMATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMATTO$96) != 0;
        }
    }
    
    /**
     * Sets the "NUM_ATTO" element
     */
    public void setNUMATTO(java.lang.String numatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMATTO$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMATTO$96);
            }
            target.setStringValue(numatto);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_ATTO" element
     */
    public void xsetNUMATTO(org.apache.xmlbeans.XmlString numatto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUMATTO$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NUMATTO$96);
            }
            target.set(numatto);
        }
    }
    
    /**
     * Unsets the "NUM_ATTO" element
     */
    public void unsetNUMATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMATTO$96, 0);
        }
    }
    
    /**
     * Gets the "PERC_IVA" element
     */
    public float getPERCIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCIVA$98, 0);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "PERC_IVA" element
     */
    public org.apache.xmlbeans.XmlFloat xgetPERCIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCIVA$98, 0);
            return target;
        }
    }
    
    /**
     * True if has "PERC_IVA" element
     */
    public boolean isSetPERCIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERCIVA$98) != 0;
        }
    }
    
    /**
     * Sets the "PERC_IVA" element
     */
    public void setPERCIVA(float perciva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCIVA$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERCIVA$98);
            }
            target.setFloatValue(perciva);
        }
    }
    
    /**
     * Sets (as xml) the "PERC_IVA" element
     */
    public void xsetPERCIVA(org.apache.xmlbeans.XmlFloat perciva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_element_user(PERCIVA$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_element_user(PERCIVA$98);
            }
            target.set(perciva);
        }
    }
    
    /**
     * Unsets the "PERC_IVA" element
     */
    public void unsetPERCIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERCIVA$98, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_IVA" element
     */
    public double getIMPORTOIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOIVA$100, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_IVA" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOIVA$100, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_IVA" element
     */
    public boolean isSetIMPORTOIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOIVA$100) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_IVA" element
     */
    public void setIMPORTOIVA(double importoiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOIVA$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOIVA$100);
            }
            target.setDoubleValue(importoiva);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_IVA" element
     */
    public void xsetIMPORTOIVA(org.apache.xmlbeans.XmlDouble importoiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOIVA$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOIVA$100);
            }
            target.set(importoiva);
        }
    }
    
    /**
     * Unsets the "IMPORTO_IVA" element
     */
    public void unsetIMPORTOIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOIVA$100, 0);
        }
    }
}
