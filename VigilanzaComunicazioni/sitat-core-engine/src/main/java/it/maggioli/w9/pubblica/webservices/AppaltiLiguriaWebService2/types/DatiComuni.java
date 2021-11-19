/*
 * XML Type:  DatiComuni
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiComuni(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiComuni extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiComuni.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("daticomunic6fatype");
    
    /**
     * Gets the "CODICE_UFFICIO" element
     */
    java.lang.String getCODICEUFFICIO();
    
    /**
     * Gets (as xml) the "CODICE_UFFICIO" element
     */
    org.apache.xmlbeans.XmlString xgetCODICEUFFICIO();
    
    /**
     * Sets the "CODICE_UFFICIO" element
     */
    void setCODICEUFFICIO(java.lang.String codiceufficio);
    
    /**
     * Sets (as xml) the "CODICE_UFFICIO" element
     */
    void xsetCODICEUFFICIO(org.apache.xmlbeans.XmlString codiceufficio);
    
    /**
     * Gets the "DESCR_UFFICIO" element
     */
    java.lang.String getDESCRUFFICIO();
    
    /**
     * Gets (as xml) the "DESCR_UFFICIO" element
     */
    org.apache.xmlbeans.XmlString xgetDESCRUFFICIO();
    
    /**
     * Sets the "DESCR_UFFICIO" element
     */
    void setDESCRUFFICIO(java.lang.String descrufficio);
    
    /**
     * Sets (as xml) the "DESCR_UFFICIO" element
     */
    void xsetDESCRUFFICIO(org.apache.xmlbeans.XmlString descrufficio);
    
    /**
     * Gets the "IMPORTO_LIQUIDATO" element
     */
    double getIMPORTOLIQUIDATO();
    
    /**
     * Gets (as xml) the "IMPORTO_LIQUIDATO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOLIQUIDATO();
    
    /**
     * True if has "IMPORTO_LIQUIDATO" element
     */
    boolean isSetIMPORTOLIQUIDATO();
    
    /**
     * Sets the "IMPORTO_LIQUIDATO" element
     */
    void setIMPORTOLIQUIDATO(double importoliquidato);
    
    /**
     * Sets (as xml) the "IMPORTO_LIQUIDATO" element
     */
    void xsetIMPORTOLIQUIDATO(org.apache.xmlbeans.XmlDouble importoliquidato);
    
    /**
     * Unsets the "IMPORTO_LIQUIDATO" element
     */
    void unsetIMPORTOLIQUIDATO();
    
    /**
     * Gets the "OGGETTO_GARA" element
     */
    java.lang.String getOGGETTOGARA();
    
    /**
     * Gets (as xml) the "OGGETTO_GARA" element
     */
    org.apache.xmlbeans.XmlString xgetOGGETTOGARA();
    
    /**
     * Sets the "OGGETTO_GARA" element
     */
    void setOGGETTOGARA(java.lang.String oggettogara);
    
    /**
     * Sets (as xml) the "OGGETTO_GARA" element
     */
    void xsetOGGETTOGARA(org.apache.xmlbeans.XmlString oggettogara);
    
    /**
     * Gets the "ID_GARA" element
     */
    java.lang.String getIDGARA();
    
    /**
     * Gets (as xml) the "ID_GARA" element
     */
    org.apache.xmlbeans.XmlString xgetIDGARA();
    
    /**
     * Sets the "ID_GARA" element
     */
    void setIDGARA(java.lang.String idgara);
    
    /**
     * Sets (as xml) the "ID_GARA" element
     */
    void xsetIDGARA(org.apache.xmlbeans.XmlString idgara);
    
    /**
     * Gets the "FLAG_IMPORTO_GARA_DISP" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGIMPORTOGARADISP();
    
    /**
     * Gets (as xml) the "FLAG_IMPORTO_GARA_DISP" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGIMPORTOGARADISP();
    
    /**
     * True if has "FLAG_IMPORTO_GARA_DISP" element
     */
    boolean isSetFLAGIMPORTOGARADISP();
    
    /**
     * Sets the "FLAG_IMPORTO_GARA_DISP" element
     */
    void setFLAGIMPORTOGARADISP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagimportogaradisp);
    
    /**
     * Sets (as xml) the "FLAG_IMPORTO_GARA_DISP" element
     */
    void xsetFLAGIMPORTOGARADISP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagimportogaradisp);
    
    /**
     * Unsets the "FLAG_IMPORTO_GARA_DISP" element
     */
    void unsetFLAGIMPORTOGARADISP();
    
    /**
     * Gets the "IMPORTO_GARA" element
     */
    double getIMPORTOGARA();
    
    /**
     * Gets (as xml) the "IMPORTO_GARA" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOGARA();
    
    /**
     * True if has "IMPORTO_GARA" element
     */
    boolean isSetIMPORTOGARA();
    
    /**
     * Sets the "IMPORTO_GARA" element
     */
    void setIMPORTOGARA(double importogara);
    
    /**
     * Sets (as xml) the "IMPORTO_GARA" element
     */
    void xsetIMPORTOGARA(org.apache.xmlbeans.XmlDouble importogara);
    
    /**
     * Unsets the "IMPORTO_GARA" element
     */
    void unsetIMPORTOGARA();
    
    /**
     * Gets the "NUM_LOTTI" element
     */
    long getNUMLOTTI();
    
    /**
     * Gets (as xml) the "NUM_LOTTI" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMLOTTI();
    
    /**
     * Sets the "NUM_LOTTI" element
     */
    void setNUMLOTTI(long numlotti);
    
    /**
     * Sets (as xml) the "NUM_LOTTI" element
     */
    void xsetNUMLOTTI(org.apache.xmlbeans.XmlLong numlotti);
    
    /**
     * Gets the "FLAG_ENTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType.Enum getFLAGENTE();
    
    /**
     * Gets (as xml) the "FLAG_ENTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType xgetFLAGENTE();
    
    /**
     * Sets the "FLAG_ENTE" element
     */
    void setFLAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType.Enum flagente);
    
    /**
     * Sets (as xml) the "FLAG_ENTE" element
     */
    void xsetFLAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType flagente);
    
    /**
     * Gets the "MODO_INDIZIONE" element
     */
    java.lang.String getMODOINDIZIONE();
    
    /**
     * Gets (as xml) the "MODO_INDIZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetMODOINDIZIONE();
    
    /**
     * True if has "MODO_INDIZIONE" element
     */
    boolean isSetMODOINDIZIONE();
    
    /**
     * Sets the "MODO_INDIZIONE" element
     */
    void setMODOINDIZIONE(java.lang.String modoindizione);
    
    /**
     * Sets (as xml) the "MODO_INDIZIONE" element
     */
    void xsetMODOINDIZIONE(org.apache.xmlbeans.XmlString modoindizione);
    
    /**
     * Unsets the "MODO_INDIZIONE" element
     */
    void unsetMODOINDIZIONE();
    
    /**
     * Gets the "MODO_REALIZZAZIONE" element
     */
    java.lang.String getMODOREALIZZAZIONE();
    
    /**
     * Gets (as xml) the "MODO_REALIZZAZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetMODOREALIZZAZIONE();
    
    /**
     * Sets the "MODO_REALIZZAZIONE" element
     */
    void setMODOREALIZZAZIONE(java.lang.String modorealizzazione);
    
    /**
     * Sets (as xml) the "MODO_REALIZZAZIONE" element
     */
    void xsetMODOREALIZZAZIONE(org.apache.xmlbeans.XmlString modorealizzazione);
    
    /**
     * Gets the "CIG_AQ" element
     */
    java.lang.String getCIGAQ();
    
    /**
     * Gets (as xml) the "CIG_AQ" element
     */
    org.apache.xmlbeans.XmlString xgetCIGAQ();
    
    /**
     * True if has "CIG_AQ" element
     */
    boolean isSetCIGAQ();
    
    /**
     * Sets the "CIG_AQ" element
     */
    void setCIGAQ(java.lang.String cigaq);
    
    /**
     * Sets (as xml) the "CIG_AQ" element
     */
    void xsetCIGAQ(org.apache.xmlbeans.XmlString cigaq);
    
    /**
     * Unsets the "CIG_AQ" element
     */
    void unsetCIGAQ();
    
    /**
     * Gets the "FLAG_SA_AGENTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGSAAGENTE();
    
    /**
     * Gets (as xml) the "FLAG_SA_AGENTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGSAAGENTE();
    
    /**
     * Sets the "FLAG_SA_AGENTE" element
     */
    void setFLAGSAAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagsaagente);
    
    /**
     * Sets (as xml) the "FLAG_SA_AGENTE" element
     */
    void xsetFLAGSAAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagsaagente);
    
    /**
     * Gets the "ID_TIPOLOGIA_SA" element
     */
    java.lang.String getIDTIPOLOGIASA();
    
    /**
     * Gets (as xml) the "ID_TIPOLOGIA_SA" element
     */
    org.apache.xmlbeans.XmlString xgetIDTIPOLOGIASA();
    
    /**
     * True if has "ID_TIPOLOGIA_SA" element
     */
    boolean isSetIDTIPOLOGIASA();
    
    /**
     * Sets the "ID_TIPOLOGIA_SA" element
     */
    void setIDTIPOLOGIASA(java.lang.String idtipologiasa);
    
    /**
     * Sets (as xml) the "ID_TIPOLOGIA_SA" element
     */
    void xsetIDTIPOLOGIASA(org.apache.xmlbeans.XmlString idtipologiasa);
    
    /**
     * Unsets the "ID_TIPOLOGIA_SA" element
     */
    void unsetIDTIPOLOGIASA();
    
    /**
     * Gets the "DEN_AMM_AGENTE" element
     */
    java.lang.String getDENAMMAGENTE();
    
    /**
     * Gets (as xml) the "DEN_AMM_AGENTE" element
     */
    org.apache.xmlbeans.XmlString xgetDENAMMAGENTE();
    
    /**
     * True if has "DEN_AMM_AGENTE" element
     */
    boolean isSetDENAMMAGENTE();
    
    /**
     * Sets the "DEN_AMM_AGENTE" element
     */
    void setDENAMMAGENTE(java.lang.String denammagente);
    
    /**
     * Sets (as xml) the "DEN_AMM_AGENTE" element
     */
    void xsetDENAMMAGENTE(org.apache.xmlbeans.XmlString denammagente);
    
    /**
     * Unsets the "DEN_AMM_AGENTE" element
     */
    void unsetDENAMMAGENTE();
    
    /**
     * Gets the "CF_AMM_AGENTE" element
     */
    java.lang.String getCFAMMAGENTE();
    
    /**
     * Gets (as xml) the "CF_AMM_AGENTE" element
     */
    org.apache.xmlbeans.XmlString xgetCFAMMAGENTE();
    
    /**
     * True if has "CF_AMM_AGENTE" element
     */
    boolean isSetCFAMMAGENTE();
    
    /**
     * Sets the "CF_AMM_AGENTE" element
     */
    void setCFAMMAGENTE(java.lang.String cfammagente);
    
    /**
     * Sets (as xml) the "CF_AMM_AGENTE" element
     */
    void xsetCFAMMAGENTE(org.apache.xmlbeans.XmlString cfammagente);
    
    /**
     * Unsets the "CF_AMM_AGENTE" element
     */
    void unsetCFAMMAGENTE();
    
    /**
     * Gets the "TIPOLOGIA_PROCEDURA" element
     */
    java.lang.String getTIPOLOGIAPROCEDURA();
    
    /**
     * Gets (as xml) the "TIPOLOGIA_PROCEDURA" element
     */
    org.apache.xmlbeans.XmlString xgetTIPOLOGIAPROCEDURA();
    
    /**
     * True if has "TIPOLOGIA_PROCEDURA" element
     */
    boolean isSetTIPOLOGIAPROCEDURA();
    
    /**
     * Sets the "TIPOLOGIA_PROCEDURA" element
     */
    void setTIPOLOGIAPROCEDURA(java.lang.String tipologiaprocedura);
    
    /**
     * Sets (as xml) the "TIPOLOGIA_PROCEDURA" element
     */
    void xsetTIPOLOGIAPROCEDURA(org.apache.xmlbeans.XmlString tipologiaprocedura);
    
    /**
     * Unsets the "TIPOLOGIA_PROCEDURA" element
     */
    void unsetTIPOLOGIAPROCEDURA();
    
    /**
     * Gets the "FLAG_CENTRALE_STIPULA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGCENTRALESTIPULA();
    
    /**
     * Gets (as xml) the "FLAG_CENTRALE_STIPULA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGCENTRALESTIPULA();
    
    /**
     * True if has "FLAG_CENTRALE_STIPULA" element
     */
    boolean isSetFLAGCENTRALESTIPULA();
    
    /**
     * Sets the "FLAG_CENTRALE_STIPULA" element
     */
    void setFLAGCENTRALESTIPULA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagcentralestipula);
    
    /**
     * Sets (as xml) the "FLAG_CENTRALE_STIPULA" element
     */
    void xsetFLAGCENTRALESTIPULA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagcentralestipula);
    
    /**
     * Unsets the "FLAG_CENTRALE_STIPULA" element
     */
    void unsetFLAGCENTRALESTIPULA();
    
    /**
     * Gets the "DATA_GURI_GARA" element
     */
    java.util.Calendar getDATAGURIGARA();
    
    /**
     * Gets (as xml) the "DATA_GURI_GARA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAGURIGARA();
    
    /**
     * True if has "DATA_GURI_GARA" element
     */
    boolean isSetDATAGURIGARA();
    
    /**
     * Sets the "DATA_GURI_GARA" element
     */
    void setDATAGURIGARA(java.util.Calendar datagurigara);
    
    /**
     * Sets (as xml) the "DATA_GURI_GARA" element
     */
    void xsetDATAGURIGARA(org.apache.xmlbeans.XmlDateTime datagurigara);
    
    /**
     * Unsets the "DATA_GURI_GARA" element
     */
    void unsetDATAGURIGARA();
    
    /**
     * Gets the "DATA_SCADENZA_GARA" element
     */
    java.util.Calendar getDATASCADENZAGARA();
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_GARA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATASCADENZAGARA();
    
    /**
     * True if has "DATA_SCADENZA_GARA" element
     */
    boolean isSetDATASCADENZAGARA();
    
    /**
     * Sets the "DATA_SCADENZA_GARA" element
     */
    void setDATASCADENZAGARA(java.util.Calendar datascadenzagara);
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_GARA" element
     */
    void xsetDATASCADENZAGARA(org.apache.xmlbeans.XmlDateTime datascadenzagara);
    
    /**
     * Unsets the "DATA_SCADENZA_GARA" element
     */
    void unsetDATASCADENZAGARA();
    
    /**
     * Gets the "CAM_GARA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getCAMGARA();
    
    /**
     * Gets (as xml) the "CAM_GARA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetCAMGARA();
    
    /**
     * True if has "CAM_GARA" element
     */
    boolean isSetCAMGARA();
    
    /**
     * Sets the "CAM_GARA" element
     */
    void setCAMGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum camgara);
    
    /**
     * Sets (as xml) the "CAM_GARA" element
     */
    void xsetCAMGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType camgara);
    
    /**
     * Unsets the "CAM_GARA" element
     */
    void unsetCAMGARA();
    
    /**
     * Gets the "SISMA_GARA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSISMAGARA();
    
    /**
     * Gets (as xml) the "SISMA_GARA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSISMAGARA();
    
    /**
     * True if has "SISMA_GARA" element
     */
    boolean isSetSISMAGARA();
    
    /**
     * Sets the "SISMA_GARA" element
     */
    void setSISMAGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum sismagara);
    
    /**
     * Sets (as xml) the "SISMA_GARA" element
     */
    void xsetSISMAGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType sismagara);
    
    /**
     * Unsets the "SISMA_GARA" element
     */
    void unsetSISMAGARA();
    
    /**
     * Gets the "INDSEDE_GARA" element
     */
    java.lang.String getINDSEDEGARA();
    
    /**
     * Gets (as xml) the "INDSEDE_GARA" element
     */
    org.apache.xmlbeans.XmlString xgetINDSEDEGARA();
    
    /**
     * True if has "INDSEDE_GARA" element
     */
    boolean isSetINDSEDEGARA();
    
    /**
     * Sets the "INDSEDE_GARA" element
     */
    void setINDSEDEGARA(java.lang.String indsedegara);
    
    /**
     * Sets (as xml) the "INDSEDE_GARA" element
     */
    void xsetINDSEDEGARA(org.apache.xmlbeans.XmlString indsedegara);
    
    /**
     * Unsets the "INDSEDE_GARA" element
     */
    void unsetINDSEDEGARA();
    
    /**
     * Gets the "COMSEDE_GARA" element
     */
    java.lang.String getCOMSEDEGARA();
    
    /**
     * Gets (as xml) the "COMSEDE_GARA" element
     */
    org.apache.xmlbeans.XmlString xgetCOMSEDEGARA();
    
    /**
     * True if has "COMSEDE_GARA" element
     */
    boolean isSetCOMSEDEGARA();
    
    /**
     * Sets the "COMSEDE_GARA" element
     */
    void setCOMSEDEGARA(java.lang.String comsedegara);
    
    /**
     * Sets (as xml) the "COMSEDE_GARA" element
     */
    void xsetCOMSEDEGARA(org.apache.xmlbeans.XmlString comsedegara);
    
    /**
     * Unsets the "COMSEDE_GARA" element
     */
    void unsetCOMSEDEGARA();
    
    /**
     * Gets the "PROSEDE_GARA" element
     */
    java.lang.String getPROSEDEGARA();
    
    /**
     * Gets (as xml) the "PROSEDE_GARA" element
     */
    org.apache.xmlbeans.XmlString xgetPROSEDEGARA();
    
    /**
     * True if has "PROSEDE_GARA" element
     */
    boolean isSetPROSEDEGARA();
    
    /**
     * Sets the "PROSEDE_GARA" element
     */
    void setPROSEDEGARA(java.lang.String prosedegara);
    
    /**
     * Sets (as xml) the "PROSEDE_GARA" element
     */
    void xsetPROSEDEGARA(org.apache.xmlbeans.XmlString prosedegara);
    
    /**
     * Unsets the "PROSEDE_GARA" element
     */
    void unsetPROSEDEGARA();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
