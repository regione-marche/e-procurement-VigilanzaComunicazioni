/*
 * XML Type:  DatiAggiudicazione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiAggiudicazione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiAggiudicazione extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiAggiudicazione.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datiaggiudicazione0502type");
    
    /**
     * Gets the "FIN_REGIONALE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFINREGIONALE();
    
    /**
     * Gets (as xml) the "FIN_REGIONALE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFINREGIONALE();
    
    /**
     * Sets the "FIN_REGIONALE" element
     */
    void setFINREGIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum finregionale);
    
    /**
     * Sets (as xml) the "FIN_REGIONALE" element
     */
    void xsetFINREGIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType finregionale);
    
    /**
     * Gets the "FLAG_IMPORTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGIMPORTI();
    
    /**
     * Gets (as xml) the "FLAG_IMPORTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGIMPORTI();
    
    /**
     * Sets the "FLAG_IMPORTI" element
     */
    void setFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagimporti);
    
    /**
     * Sets (as xml) the "FLAG_IMPORTI" element
     */
    void xsetFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagimporti);
    
    /**
     * Gets the "FLAG_SICUREZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGSICUREZZA();
    
    /**
     * Gets (as xml) the "FLAG_SICUREZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGSICUREZZA();
    
    /**
     * Sets the "FLAG_SICUREZZA" element
     */
    void setFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagsicurezza);
    
    /**
     * Sets (as xml) the "FLAG_SICUREZZA" element
     */
    void xsetFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagsicurezza);
    
    /**
     * Gets the "FLAG_LIVELLO_PROGETTUALE" element
     */
    java.lang.String getFLAGLIVELLOPROGETTUALE();
    
    /**
     * Gets (as xml) the "FLAG_LIVELLO_PROGETTUALE" element
     */
    org.apache.xmlbeans.XmlString xgetFLAGLIVELLOPROGETTUALE();
    
    /**
     * Sets the "FLAG_LIVELLO_PROGETTUALE" element
     */
    void setFLAGLIVELLOPROGETTUALE(java.lang.String flaglivelloprogettuale);
    
    /**
     * Sets (as xml) the "FLAG_LIVELLO_PROGETTUALE" element
     */
    void xsetFLAGLIVELLOPROGETTUALE(org.apache.xmlbeans.XmlString flaglivelloprogettuale);
    
    /**
     * Gets the "VERIFICA_CAMPIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getVERIFICACAMPIONE();
    
    /**
     * Gets (as xml) the "VERIFICA_CAMPIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetVERIFICACAMPIONE();
    
    /**
     * Sets the "VERIFICA_CAMPIONE" element
     */
    void setVERIFICACAMPIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum verificacampione);
    
    /**
     * Sets (as xml) the "VERIFICA_CAMPIONE" element
     */
    void xsetVERIFICACAMPIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType verificacampione);
    
    /**
     * Gets the "DATA_VERB_AGGIUDICAZIONE" element
     */
    java.util.Calendar getDATAVERBAGGIUDICAZIONE();
    
    /**
     * Gets (as xml) the "DATA_VERB_AGGIUDICAZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBAGGIUDICAZIONE();
    
    /**
     * Sets the "DATA_VERB_AGGIUDICAZIONE" element
     */
    void setDATAVERBAGGIUDICAZIONE(java.util.Calendar dataverbaggiudicazione);
    
    /**
     * Sets (as xml) the "DATA_VERB_AGGIUDICAZIONE" element
     */
    void xsetDATAVERBAGGIUDICAZIONE(org.apache.xmlbeans.XmlDateTime dataverbaggiudicazione);
    
    /**
     * Gets the "IMPORTO_AGGIUDICAZIONE" element
     */
    double getIMPORTOAGGIUDICAZIONE();
    
    /**
     * Gets (as xml) the "IMPORTO_AGGIUDICAZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOAGGIUDICAZIONE();
    
    /**
     * Sets the "IMPORTO_AGGIUDICAZIONE" element
     */
    void setIMPORTOAGGIUDICAZIONE(double importoaggiudicazione);
    
    /**
     * Sets (as xml) the "IMPORTO_AGGIUDICAZIONE" element
     */
    void xsetIMPORTOAGGIUDICAZIONE(org.apache.xmlbeans.XmlDouble importoaggiudicazione);
    
    /**
     * Gets the "IMPORTO_DISPOSIZIONE" element
     */
    double getIMPORTODISPOSIZIONE();
    
    /**
     * Gets (as xml) the "IMPORTO_DISPOSIZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTODISPOSIZIONE();
    
    /**
     * True if has "IMPORTO_DISPOSIZIONE" element
     */
    boolean isSetIMPORTODISPOSIZIONE();
    
    /**
     * Sets the "IMPORTO_DISPOSIZIONE" element
     */
    void setIMPORTODISPOSIZIONE(double importodisposizione);
    
    /**
     * Sets (as xml) the "IMPORTO_DISPOSIZIONE" element
     */
    void xsetIMPORTODISPOSIZIONE(org.apache.xmlbeans.XmlDouble importodisposizione);
    
    /**
     * Unsets the "IMPORTO_DISPOSIZIONE" element
     */
    void unsetIMPORTODISPOSIZIONE();
    
    /**
     * Gets the "ASTA_ELETTRONICA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getASTAELETTRONICA();
    
    /**
     * Gets (as xml) the "ASTA_ELETTRONICA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetASTAELETTRONICA();
    
    /**
     * Sets the "ASTA_ELETTRONICA" element
     */
    void setASTAELETTRONICA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum astaelettronica);
    
    /**
     * Sets (as xml) the "ASTA_ELETTRONICA" element
     */
    void xsetASTAELETTRONICA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType astaelettronica);
    
    /**
     * Gets the "PERC_RIBASSO_AGG" element
     */
    float getPERCRIBASSOAGG();
    
    /**
     * Gets (as xml) the "PERC_RIBASSO_AGG" element
     */
    org.apache.xmlbeans.XmlFloat xgetPERCRIBASSOAGG();
    
    /**
     * True if has "PERC_RIBASSO_AGG" element
     */
    boolean isSetPERCRIBASSOAGG();
    
    /**
     * Sets the "PERC_RIBASSO_AGG" element
     */
    void setPERCRIBASSOAGG(float percribassoagg);
    
    /**
     * Sets (as xml) the "PERC_RIBASSO_AGG" element
     */
    void xsetPERCRIBASSOAGG(org.apache.xmlbeans.XmlFloat percribassoagg);
    
    /**
     * Unsets the "PERC_RIBASSO_AGG" element
     */
    void unsetPERCRIBASSOAGG();
    
    /**
     * Gets the "PERC_OFF_AUMENTO" element
     */
    float getPERCOFFAUMENTO();
    
    /**
     * Gets (as xml) the "PERC_OFF_AUMENTO" element
     */
    org.apache.xmlbeans.XmlFloat xgetPERCOFFAUMENTO();
    
    /**
     * True if has "PERC_OFF_AUMENTO" element
     */
    boolean isSetPERCOFFAUMENTO();
    
    /**
     * Sets the "PERC_OFF_AUMENTO" element
     */
    void setPERCOFFAUMENTO(float percoffaumento);
    
    /**
     * Sets (as xml) the "PERC_OFF_AUMENTO" element
     */
    void xsetPERCOFFAUMENTO(org.apache.xmlbeans.XmlFloat percoffaumento);
    
    /**
     * Unsets the "PERC_OFF_AUMENTO" element
     */
    void unsetPERCOFFAUMENTO();
    
    /**
     * Gets the "IMPORTO_APPALTO" element
     */
    double getIMPORTOAPPALTO();
    
    /**
     * Gets (as xml) the "IMPORTO_APPALTO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOAPPALTO();
    
    /**
     * True if has "IMPORTO_APPALTO" element
     */
    boolean isSetIMPORTOAPPALTO();
    
    /**
     * Sets the "IMPORTO_APPALTO" element
     */
    void setIMPORTOAPPALTO(double importoappalto);
    
    /**
     * Sets (as xml) the "IMPORTO_APPALTO" element
     */
    void xsetIMPORTOAPPALTO(org.apache.xmlbeans.XmlDouble importoappalto);
    
    /**
     * Unsets the "IMPORTO_APPALTO" element
     */
    void unsetIMPORTOAPPALTO();
    
    /**
     * Gets the "IMPORTO_INTERVENTO" element
     */
    double getIMPORTOINTERVENTO();
    
    /**
     * Gets (as xml) the "IMPORTO_INTERVENTO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOINTERVENTO();
    
    /**
     * True if has "IMPORTO_INTERVENTO" element
     */
    boolean isSetIMPORTOINTERVENTO();
    
    /**
     * Sets the "IMPORTO_INTERVENTO" element
     */
    void setIMPORTOINTERVENTO(double importointervento);
    
    /**
     * Sets (as xml) the "IMPORTO_INTERVENTO" element
     */
    void xsetIMPORTOINTERVENTO(org.apache.xmlbeans.XmlDouble importointervento);
    
    /**
     * Unsets the "IMPORTO_INTERVENTO" element
     */
    void unsetIMPORTOINTERVENTO();
    
    /**
     * Gets the "IMPORTO_NETTO_SIC" element
     */
    double getIMPORTONETTOSIC();
    
    /**
     * Gets (as xml) the "IMPORTO_NETTO_SIC" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTONETTOSIC();
    
    /**
     * True if has "IMPORTO_NETTO_SIC" element
     */
    boolean isSetIMPORTONETTOSIC();
    
    /**
     * Sets the "IMPORTO_NETTO_SIC" element
     */
    void setIMPORTONETTOSIC(double importonettosic);
    
    /**
     * Sets (as xml) the "IMPORTO_NETTO_SIC" element
     */
    void xsetIMPORTONETTOSIC(org.apache.xmlbeans.XmlDouble importonettosic);
    
    /**
     * Unsets the "IMPORTO_NETTO_SIC" element
     */
    void unsetIMPORTONETTOSIC();
    
    /**
     * Gets the "PROCEDURA_ACC" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPROCEDURAACC();
    
    /**
     * Gets (as xml) the "PROCEDURA_ACC" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPROCEDURAACC();
    
    /**
     * Sets the "PROCEDURA_ACC" element
     */
    void setPROCEDURAACC(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum proceduraacc);
    
    /**
     * Sets (as xml) the "PROCEDURA_ACC" element
     */
    void xsetPROCEDURAACC(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType proceduraacc);
    
    /**
     * Gets the "PREINFORMAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPREINFORMAZIONE();
    
    /**
     * Gets (as xml) the "PREINFORMAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPREINFORMAZIONE();
    
    /**
     * Sets the "PREINFORMAZIONE" element
     */
    void setPREINFORMAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum preinformazione);
    
    /**
     * Sets (as xml) the "PREINFORMAZIONE" element
     */
    void xsetPREINFORMAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType preinformazione);
    
    /**
     * Gets the "TERMINE_RIDOTTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getTERMINERIDOTTO();
    
    /**
     * Gets (as xml) the "TERMINE_RIDOTTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetTERMINERIDOTTO();
    
    /**
     * Sets the "TERMINE_RIDOTTO" element
     */
    void setTERMINERIDOTTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum termineridotto);
    
    /**
     * Sets (as xml) the "TERMINE_RIDOTTO" element
     */
    void xsetTERMINERIDOTTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType termineridotto);
    
    /**
     * Gets the "ID_MODO_INDIZIONE" element
     */
    java.lang.String getIDMODOINDIZIONE();
    
    /**
     * Gets (as xml) the "ID_MODO_INDIZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetIDMODOINDIZIONE();
    
    /**
     * True if has "ID_MODO_INDIZIONE" element
     */
    boolean isSetIDMODOINDIZIONE();
    
    /**
     * Sets the "ID_MODO_INDIZIONE" element
     */
    void setIDMODOINDIZIONE(java.lang.String idmodoindizione);
    
    /**
     * Sets (as xml) the "ID_MODO_INDIZIONE" element
     */
    void xsetIDMODOINDIZIONE(org.apache.xmlbeans.XmlString idmodoindizione);
    
    /**
     * Unsets the "ID_MODO_INDIZIONE" element
     */
    void unsetIDMODOINDIZIONE();
    
    /**
     * Gets the "NUM_IMPRESE_INVITATE" element
     */
    long getNUMIMPRESEINVITATE();
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_INVITATE" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMIMPRESEINVITATE();
    
    /**
     * True if has "NUM_IMPRESE_INVITATE" element
     */
    boolean isSetNUMIMPRESEINVITATE();
    
    /**
     * Sets the "NUM_IMPRESE_INVITATE" element
     */
    void setNUMIMPRESEINVITATE(long numimpreseinvitate);
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_INVITATE" element
     */
    void xsetNUMIMPRESEINVITATE(org.apache.xmlbeans.XmlLong numimpreseinvitate);
    
    /**
     * Unsets the "NUM_IMPRESE_INVITATE" element
     */
    void unsetNUMIMPRESEINVITATE();
    
    /**
     * Gets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    long getNUMIMPRESERICHIESTAINVITO();
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMIMPRESERICHIESTAINVITO();
    
    /**
     * True if has "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    boolean isSetNUMIMPRESERICHIESTAINVITO();
    
    /**
     * Sets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    void setNUMIMPRESERICHIESTAINVITO(long numimpreserichiestainvito);
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    void xsetNUMIMPRESERICHIESTAINVITO(org.apache.xmlbeans.XmlLong numimpreserichiestainvito);
    
    /**
     * Unsets the "NUM_IMPRESE_RICHIESTA_INVITO" element
     */
    void unsetNUMIMPRESERICHIESTAINVITO();
    
    /**
     * Gets the "NUM_IMPRESE_OFFERENTI" element
     */
    long getNUMIMPRESEOFFERENTI();
    
    /**
     * Gets (as xml) the "NUM_IMPRESE_OFFERENTI" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMIMPRESEOFFERENTI();
    
    /**
     * Sets the "NUM_IMPRESE_OFFERENTI" element
     */
    void setNUMIMPRESEOFFERENTI(long numimpreseofferenti);
    
    /**
     * Sets (as xml) the "NUM_IMPRESE_OFFERENTI" element
     */
    void xsetNUMIMPRESEOFFERENTI(org.apache.xmlbeans.XmlLong numimpreseofferenti);
    
    /**
     * Gets the "NUM_OFFERTE_AMMESSE" element
     */
    long getNUMOFFERTEAMMESSE();
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_AMMESSE" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMOFFERTEAMMESSE();
    
    /**
     * Sets the "NUM_OFFERTE_AMMESSE" element
     */
    void setNUMOFFERTEAMMESSE(long numofferteammesse);
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_AMMESSE" element
     */
    void xsetNUMOFFERTEAMMESSE(org.apache.xmlbeans.XmlLong numofferteammesse);
    
    /**
     * Gets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    java.util.Calendar getDATASCADENZARICHIESTAINVITO();
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATASCADENZARICHIESTAINVITO();
    
    /**
     * True if has "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    boolean isSetDATASCADENZARICHIESTAINVITO();
    
    /**
     * Sets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    void setDATASCADENZARICHIESTAINVITO(java.util.Calendar datascadenzarichiestainvito);
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    void xsetDATASCADENZARICHIESTAINVITO(org.apache.xmlbeans.XmlDateTime datascadenzarichiestainvito);
    
    /**
     * Unsets the "DATA_SCADENZA_RICHIESTA_INVITO" element
     */
    void unsetDATASCADENZARICHIESTAINVITO();
    
    /**
     * Gets the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    java.util.Calendar getDATASCADENZAPRESOFFERTA();
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATASCADENZAPRESOFFERTA();
    
    /**
     * Sets the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    void setDATASCADENZAPRESOFFERTA(java.util.Calendar datascadenzapresofferta);
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_PRES_OFFERTA" element
     */
    void xsetDATASCADENZAPRESOFFERTA(org.apache.xmlbeans.XmlDateTime datascadenzapresofferta);
    
    /**
     * Gets the "IMPORTO_LAVORI" element
     */
    double getIMPORTOLAVORI();
    
    /**
     * Gets (as xml) the "IMPORTO_LAVORI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOLAVORI();
    
    /**
     * True if has "IMPORTO_LAVORI" element
     */
    boolean isSetIMPORTOLAVORI();
    
    /**
     * Sets the "IMPORTO_LAVORI" element
     */
    void setIMPORTOLAVORI(double importolavori);
    
    /**
     * Sets (as xml) the "IMPORTO_LAVORI" element
     */
    void xsetIMPORTOLAVORI(org.apache.xmlbeans.XmlDouble importolavori);
    
    /**
     * Unsets the "IMPORTO_LAVORI" element
     */
    void unsetIMPORTOLAVORI();
    
    /**
     * Gets the "IMPORTO_SERVIZI" element
     */
    double getIMPORTOSERVIZI();
    
    /**
     * Gets (as xml) the "IMPORTO_SERVIZI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOSERVIZI();
    
    /**
     * True if has "IMPORTO_SERVIZI" element
     */
    boolean isSetIMPORTOSERVIZI();
    
    /**
     * Sets the "IMPORTO_SERVIZI" element
     */
    void setIMPORTOSERVIZI(double importoservizi);
    
    /**
     * Sets (as xml) the "IMPORTO_SERVIZI" element
     */
    void xsetIMPORTOSERVIZI(org.apache.xmlbeans.XmlDouble importoservizi);
    
    /**
     * Unsets the "IMPORTO_SERVIZI" element
     */
    void unsetIMPORTOSERVIZI();
    
    /**
     * Gets the "IMPORTO_FORNITURE" element
     */
    double getIMPORTOFORNITURE();
    
    /**
     * Gets (as xml) the "IMPORTO_FORNITURE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOFORNITURE();
    
    /**
     * True if has "IMPORTO_FORNITURE" element
     */
    boolean isSetIMPORTOFORNITURE();
    
    /**
     * Sets the "IMPORTO_FORNITURE" element
     */
    void setIMPORTOFORNITURE(double importoforniture);
    
    /**
     * Sets (as xml) the "IMPORTO_FORNITURE" element
     */
    void xsetIMPORTOFORNITURE(org.apache.xmlbeans.XmlDouble importoforniture);
    
    /**
     * Unsets the "IMPORTO_FORNITURE" element
     */
    void unsetIMPORTOFORNITURE();
    
    /**
     * Gets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    double getIMPORTOATTUAZIONESICUREZZA();
    
    /**
     * Gets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOATTUAZIONESICUREZZA();
    
    /**
     * True if has "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    boolean isSetIMPORTOATTUAZIONESICUREZZA();
    
    /**
     * Sets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    void setIMPORTOATTUAZIONESICUREZZA(double importoattuazionesicurezza);
    
    /**
     * Sets (as xml) the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    void xsetIMPORTOATTUAZIONESICUREZZA(org.apache.xmlbeans.XmlDouble importoattuazionesicurezza);
    
    /**
     * Unsets the "IMPORTO_ATTUAZIONE_SICUREZZA" element
     */
    void unsetIMPORTOATTUAZIONESICUREZZA();
    
    /**
     * Gets the "IMPORTO_PROGETTAZIONE" element
     */
    double getIMPORTOPROGETTAZIONE();
    
    /**
     * Gets (as xml) the "IMPORTO_PROGETTAZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOPROGETTAZIONE();
    
    /**
     * True if has "IMPORTO_PROGETTAZIONE" element
     */
    boolean isSetIMPORTOPROGETTAZIONE();
    
    /**
     * Sets the "IMPORTO_PROGETTAZIONE" element
     */
    void setIMPORTOPROGETTAZIONE(double importoprogettazione);
    
    /**
     * Sets (as xml) the "IMPORTO_PROGETTAZIONE" element
     */
    void xsetIMPORTOPROGETTAZIONE(org.apache.xmlbeans.XmlDouble importoprogettazione);
    
    /**
     * Unsets the "IMPORTO_PROGETTAZIONE" element
     */
    void unsetIMPORTOPROGETTAZIONE();
    
    /**
     * Gets the "REQUISITI_SS" element
     */
    java.lang.String getREQUISITISS();
    
    /**
     * Gets (as xml) the "REQUISITI_SS" element
     */
    org.apache.xmlbeans.XmlString xgetREQUISITISS();
    
    /**
     * True if has "REQUISITI_SS" element
     */
    boolean isSetREQUISITISS();
    
    /**
     * Sets the "REQUISITI_SS" element
     */
    void setREQUISITISS(java.lang.String requisitiss);
    
    /**
     * Sets (as xml) the "REQUISITI_SS" element
     */
    void xsetREQUISITISS(org.apache.xmlbeans.XmlString requisitiss);
    
    /**
     * Unsets the "REQUISITI_SS" element
     */
    void unsetREQUISITISS();
    
    /**
     * Gets the "FLAG_AQ" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGAQ();
    
    /**
     * Gets (as xml) the "FLAG_AQ" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGAQ();
    
    /**
     * True if has "FLAG_AQ" element
     */
    boolean isSetFLAGAQ();
    
    /**
     * Sets the "FLAG_AQ" element
     */
    void setFLAGAQ(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagaq);
    
    /**
     * Sets (as xml) the "FLAG_AQ" element
     */
    void xsetFLAGAQ(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagaq);
    
    /**
     * Unsets the "FLAG_AQ" element
     */
    void unsetFLAGAQ();
    
    /**
     * Gets the "DATA_INVITO" element
     */
    java.util.Calendar getDATAINVITO();
    
    /**
     * Gets (as xml) the "DATA_INVITO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINVITO();
    
    /**
     * True if has "DATA_INVITO" element
     */
    boolean isSetDATAINVITO();
    
    /**
     * Sets the "DATA_INVITO" element
     */
    void setDATAINVITO(java.util.Calendar datainvito);
    
    /**
     * Sets (as xml) the "DATA_INVITO" element
     */
    void xsetDATAINVITO(org.apache.xmlbeans.XmlDateTime datainvito);
    
    /**
     * Unsets the "DATA_INVITO" element
     */
    void unsetDATAINVITO();
    
    /**
     * Gets the "NUM_MANIF_INTERESSE" element
     */
    long getNUMMANIFINTERESSE();
    
    /**
     * Gets (as xml) the "NUM_MANIF_INTERESSE" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMMANIFINTERESSE();
    
    /**
     * True if has "NUM_MANIF_INTERESSE" element
     */
    boolean isSetNUMMANIFINTERESSE();
    
    /**
     * Sets the "NUM_MANIF_INTERESSE" element
     */
    void setNUMMANIFINTERESSE(long nummanifinteresse);
    
    /**
     * Sets (as xml) the "NUM_MANIF_INTERESSE" element
     */
    void xsetNUMMANIFINTERESSE(org.apache.xmlbeans.XmlLong nummanifinteresse);
    
    /**
     * Unsets the "NUM_MANIF_INTERESSE" element
     */
    void unsetNUMMANIFINTERESSE();
    
    /**
     * Gets the "DATA_MANIF_INTERESSE" element
     */
    java.util.Calendar getDATAMANIFINTERESSE();
    
    /**
     * Gets (as xml) the "DATA_MANIF_INTERESSE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAMANIFINTERESSE();
    
    /**
     * True if has "DATA_MANIF_INTERESSE" element
     */
    boolean isSetDATAMANIFINTERESSE();
    
    /**
     * Sets the "DATA_MANIF_INTERESSE" element
     */
    void setDATAMANIFINTERESSE(java.util.Calendar datamanifinteresse);
    
    /**
     * Sets (as xml) the "DATA_MANIF_INTERESSE" element
     */
    void xsetDATAMANIFINTERESSE(org.apache.xmlbeans.XmlDateTime datamanifinteresse);
    
    /**
     * Unsets the "DATA_MANIF_INTERESSE" element
     */
    void unsetDATAMANIFINTERESSE();
    
    /**
     * Gets the "FLAG_RICH_SUBAPPALTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRICHSUBAPPALTO();
    
    /**
     * Gets (as xml) the "FLAG_RICH_SUBAPPALTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRICHSUBAPPALTO();
    
    /**
     * Sets the "FLAG_RICH_SUBAPPALTO" element
     */
    void setFLAGRICHSUBAPPALTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagrichsubappalto);
    
    /**
     * Sets (as xml) the "FLAG_RICH_SUBAPPALTO" element
     */
    void xsetFLAGRICHSUBAPPALTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagrichsubappalto);
    
    /**
     * Gets the "NUM_OFFERTE_ESCLUSE" element
     */
    long getNUMOFFERTEESCLUSE();
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_ESCLUSE" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMOFFERTEESCLUSE();
    
    /**
     * True if has "NUM_OFFERTE_ESCLUSE" element
     */
    boolean isSetNUMOFFERTEESCLUSE();
    
    /**
     * Sets the "NUM_OFFERTE_ESCLUSE" element
     */
    void setNUMOFFERTEESCLUSE(long numofferteescluse);
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_ESCLUSE" element
     */
    void xsetNUMOFFERTEESCLUSE(org.apache.xmlbeans.XmlLong numofferteescluse);
    
    /**
     * Unsets the "NUM_OFFERTE_ESCLUSE" element
     */
    void unsetNUMOFFERTEESCLUSE();
    
    /**
     * Gets the "OFFERTA_MASSIMO" element
     */
    float getOFFERTAMASSIMO();
    
    /**
     * Gets (as xml) the "OFFERTA_MASSIMO" element
     */
    org.apache.xmlbeans.XmlFloat xgetOFFERTAMASSIMO();
    
    /**
     * True if has "OFFERTA_MASSIMO" element
     */
    boolean isSetOFFERTAMASSIMO();
    
    /**
     * Sets the "OFFERTA_MASSIMO" element
     */
    void setOFFERTAMASSIMO(float offertamassimo);
    
    /**
     * Sets (as xml) the "OFFERTA_MASSIMO" element
     */
    void xsetOFFERTAMASSIMO(org.apache.xmlbeans.XmlFloat offertamassimo);
    
    /**
     * Unsets the "OFFERTA_MASSIMO" element
     */
    void unsetOFFERTAMASSIMO();
    
    /**
     * Gets the "OFFERTA_MINIMA" element
     */
    float getOFFERTAMINIMA();
    
    /**
     * Gets (as xml) the "OFFERTA_MINIMA" element
     */
    org.apache.xmlbeans.XmlFloat xgetOFFERTAMINIMA();
    
    /**
     * True if has "OFFERTA_MINIMA" element
     */
    boolean isSetOFFERTAMINIMA();
    
    /**
     * Sets the "OFFERTA_MINIMA" element
     */
    void setOFFERTAMINIMA(float offertaminima);
    
    /**
     * Sets (as xml) the "OFFERTA_MINIMA" element
     */
    void xsetOFFERTAMINIMA(org.apache.xmlbeans.XmlFloat offertaminima);
    
    /**
     * Unsets the "OFFERTA_MINIMA" element
     */
    void unsetOFFERTAMINIMA();
    
    /**
     * Gets the "VAL_SOGLIA_ANOMALIA" element
     */
    float getVALSOGLIAANOMALIA();
    
    /**
     * Gets (as xml) the "VAL_SOGLIA_ANOMALIA" element
     */
    org.apache.xmlbeans.XmlFloat xgetVALSOGLIAANOMALIA();
    
    /**
     * True if has "VAL_SOGLIA_ANOMALIA" element
     */
    boolean isSetVALSOGLIAANOMALIA();
    
    /**
     * Sets the "VAL_SOGLIA_ANOMALIA" element
     */
    void setVALSOGLIAANOMALIA(float valsogliaanomalia);
    
    /**
     * Sets (as xml) the "VAL_SOGLIA_ANOMALIA" element
     */
    void xsetVALSOGLIAANOMALIA(org.apache.xmlbeans.XmlFloat valsogliaanomalia);
    
    /**
     * Unsets the "VAL_SOGLIA_ANOMALIA" element
     */
    void unsetVALSOGLIAANOMALIA();
    
    /**
     * Gets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    long getNUMOFFERTEFUORISOGLIA();
    
    /**
     * Gets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMOFFERTEFUORISOGLIA();
    
    /**
     * True if has "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    boolean isSetNUMOFFERTEFUORISOGLIA();
    
    /**
     * Sets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    void setNUMOFFERTEFUORISOGLIA(long numoffertefuorisoglia);
    
    /**
     * Sets (as xml) the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    void xsetNUMOFFERTEFUORISOGLIA(org.apache.xmlbeans.XmlLong numoffertefuorisoglia);
    
    /**
     * Unsets the "NUM_OFFERTE_FUORI_SOGLIA" element
     */
    void unsetNUMOFFERTEFUORISOGLIA();
    
    /**
     * Gets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    long getNUMIMPESCLINSUFGIUST();
    
    /**
     * Gets (as xml) the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMIMPESCLINSUFGIUST();
    
    /**
     * True if has "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    boolean isSetNUMIMPESCLINSUFGIUST();
    
    /**
     * Sets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    void setNUMIMPESCLINSUFGIUST(long numimpesclinsufgiust);
    
    /**
     * Sets (as xml) the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    void xsetNUMIMPESCLINSUFGIUST(org.apache.xmlbeans.XmlLong numimpesclinsufgiust);
    
    /**
     * Unsets the "NUM_IMP_ESCL_INSUF_GIUST" element
     */
    void unsetNUMIMPESCLINSUFGIUST();
    
    /**
     * Gets the "COD_STRUMENTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.Enum getCODSTRUMENTO();
    
    /**
     * Gets (as xml) the "COD_STRUMENTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType xgetCODSTRUMENTO();
    
    /**
     * Sets the "COD_STRUMENTO" element
     */
    void setCODSTRUMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType.Enum codstrumento);
    
    /**
     * Sets (as xml) the "COD_STRUMENTO" element
     */
    void xsetCODSTRUMENTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipoStrumentoType codstrumento);
    
    /**
     * Gets the "IMP_NON_ASSOG" element
     */
    double getIMPNONASSOG();
    
    /**
     * Gets (as xml) the "IMP_NON_ASSOG" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPNONASSOG();
    
    /**
     * True if has "IMP_NON_ASSOG" element
     */
    boolean isSetIMPNONASSOG();
    
    /**
     * Sets the "IMP_NON_ASSOG" element
     */
    void setIMPNONASSOG(double impnonassog);
    
    /**
     * Sets (as xml) the "IMP_NON_ASSOG" element
     */
    void xsetIMPNONASSOG(org.apache.xmlbeans.XmlDouble impnonassog);
    
    /**
     * Unsets the "IMP_NON_ASSOG" element
     */
    void unsetIMPNONASSOG();
    
    /**
     * Gets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    java.lang.String getMODALITARIAGGIUDICAZIONE();
    
    /**
     * Gets (as xml) the "MODALITA_RIAGGIUDICAZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetMODALITARIAGGIUDICAZIONE();
    
    /**
     * True if has "MODALITA_RIAGGIUDICAZIONE" element
     */
    boolean isSetMODALITARIAGGIUDICAZIONE();
    
    /**
     * Sets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    void setMODALITARIAGGIUDICAZIONE(java.lang.String modalitariaggiudicazione);
    
    /**
     * Sets (as xml) the "MODALITA_RIAGGIUDICAZIONE" element
     */
    void xsetMODALITARIAGGIUDICAZIONE(org.apache.xmlbeans.XmlString modalitariaggiudicazione);
    
    /**
     * Unsets the "MODALITA_RIAGGIUDICAZIONE" element
     */
    void unsetMODALITARIAGGIUDICAZIONE();
    
    /**
     * Gets the "DURATA_AQ" element
     */
    long getDURATAAQ();
    
    /**
     * Gets (as xml) the "DURATA_AQ" element
     */
    org.apache.xmlbeans.XmlLong xgetDURATAAQ();
    
    /**
     * True if has "DURATA_AQ" element
     */
    boolean isSetDURATAAQ();
    
    /**
     * Sets the "DURATA_AQ" element
     */
    void setDURATAAQ(long durataaq);
    
    /**
     * Sets (as xml) the "DURATA_AQ" element
     */
    void xsetDURATAAQ(org.apache.xmlbeans.XmlLong durataaq);
    
    /**
     * Unsets the "DURATA_AQ" element
     */
    void unsetDURATAAQ();
    
    /**
     * Gets the "OPERE_URBANIZ_SCOMPUTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getOPEREURBANIZSCOMPUTO();
    
    /**
     * Gets (as xml) the "OPERE_URBANIZ_SCOMPUTO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetOPEREURBANIZSCOMPUTO();
    
    /**
     * Sets the "OPERE_URBANIZ_SCOMPUTO" element
     */
    void setOPEREURBANIZSCOMPUTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum opereurbanizscomputo);
    
    /**
     * Sets (as xml) the "OPERE_URBANIZ_SCOMPUTO" element
     */
    void xsetOPEREURBANIZSCOMPUTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType opereurbanizscomputo);
    
    /**
     * Gets the "TIPO_ATTO" element
     */
    java.lang.String getTIPOATTO();
    
    /**
     * Gets (as xml) the "TIPO_ATTO" element
     */
    org.apache.xmlbeans.XmlString xgetTIPOATTO();
    
    /**
     * True if has "TIPO_ATTO" element
     */
    boolean isSetTIPOATTO();
    
    /**
     * Sets the "TIPO_ATTO" element
     */
    void setTIPOATTO(java.lang.String tipoatto);
    
    /**
     * Sets (as xml) the "TIPO_ATTO" element
     */
    void xsetTIPOATTO(org.apache.xmlbeans.XmlString tipoatto);
    
    /**
     * Unsets the "TIPO_ATTO" element
     */
    void unsetTIPOATTO();
    
    /**
     * Gets the "DATA_ATTO" element
     */
    java.util.Calendar getDATAATTO();
    
    /**
     * Gets (as xml) the "DATA_ATTO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAATTO();
    
    /**
     * True if has "DATA_ATTO" element
     */
    boolean isSetDATAATTO();
    
    /**
     * Sets the "DATA_ATTO" element
     */
    void setDATAATTO(java.util.Calendar dataatto);
    
    /**
     * Sets (as xml) the "DATA_ATTO" element
     */
    void xsetDATAATTO(org.apache.xmlbeans.XmlDateTime dataatto);
    
    /**
     * Unsets the "DATA_ATTO" element
     */
    void unsetDATAATTO();
    
    /**
     * Gets the "NUM_ATTO" element
     */
    java.lang.String getNUMATTO();
    
    /**
     * Gets (as xml) the "NUM_ATTO" element
     */
    org.apache.xmlbeans.XmlString xgetNUMATTO();
    
    /**
     * True if has "NUM_ATTO" element
     */
    boolean isSetNUMATTO();
    
    /**
     * Sets the "NUM_ATTO" element
     */
    void setNUMATTO(java.lang.String numatto);
    
    /**
     * Sets (as xml) the "NUM_ATTO" element
     */
    void xsetNUMATTO(org.apache.xmlbeans.XmlString numatto);
    
    /**
     * Unsets the "NUM_ATTO" element
     */
    void unsetNUMATTO();
    
    /**
     * Gets the "PERC_IVA" element
     */
    float getPERCIVA();
    
    /**
     * Gets (as xml) the "PERC_IVA" element
     */
    org.apache.xmlbeans.XmlFloat xgetPERCIVA();
    
    /**
     * True if has "PERC_IVA" element
     */
    boolean isSetPERCIVA();
    
    /**
     * Sets the "PERC_IVA" element
     */
    void setPERCIVA(float perciva);
    
    /**
     * Sets (as xml) the "PERC_IVA" element
     */
    void xsetPERCIVA(org.apache.xmlbeans.XmlFloat perciva);
    
    /**
     * Unsets the "PERC_IVA" element
     */
    void unsetPERCIVA();
    
    /**
     * Gets the "IMPORTO_IVA" element
     */
    double getIMPORTOIVA();
    
    /**
     * Gets (as xml) the "IMPORTO_IVA" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOIVA();
    
    /**
     * True if has "IMPORTO_IVA" element
     */
    boolean isSetIMPORTOIVA();
    
    /**
     * Sets the "IMPORTO_IVA" element
     */
    void setIMPORTOIVA(double importoiva);
    
    /**
     * Sets (as xml) the "IMPORTO_IVA" element
     */
    void xsetIMPORTOIVA(org.apache.xmlbeans.XmlDouble importoiva);
    
    /**
     * Unsets the "IMPORTO_IVA" element
     */
    void unsetIMPORTOIVA();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
