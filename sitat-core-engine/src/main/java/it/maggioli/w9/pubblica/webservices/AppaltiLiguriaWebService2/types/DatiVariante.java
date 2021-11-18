/*
 * XML Type:  DatiVariante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiVariante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiVariante extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiVariante.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("dativariante1fa9type");
    
    /**
     * Gets the "LISTA_MOTIVI_VARIANTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante getLISTAMOTIVIVARIANTE();
    
    /**
     * Sets the "LISTA_MOTIVI_VARIANTE" element
     */
    void setLISTAMOTIVIVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante listamotivivariante);
    
    /**
     * Appends and returns a new empty "LISTA_MOTIVI_VARIANTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante addNewLISTAMOTIVIVARIANTE();
    
    /**
     * Gets the "FLAG_VARIANTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType.Enum getFLAGVARIANTE();
    
    /**
     * Gets (as xml) the "FLAG_VARIANTE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType xgetFLAGVARIANTE();
    
    /**
     * Sets the "FLAG_VARIANTE" element
     */
    void setFLAGVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType.Enum flagvariante);
    
    /**
     * Sets (as xml) the "FLAG_VARIANTE" element
     */
    void xsetFLAGVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType flagvariante);
    
    /**
     * Gets the "QUINTO_OBBLIGO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getQUINTOOBBLIGO();
    
    /**
     * Gets (as xml) the "QUINTO_OBBLIGO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetQUINTOOBBLIGO();
    
    /**
     * Sets the "QUINTO_OBBLIGO" element
     */
    void setQUINTOOBBLIGO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum quintoobbligo);
    
    /**
     * Sets (as xml) the "QUINTO_OBBLIGO" element
     */
    void xsetQUINTOOBBLIGO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType quintoobbligo);
    
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
     * Gets the "DATA_VERB_APPR" element
     */
    java.util.Calendar getDATAVERBAPPR();
    
    /**
     * Gets (as xml) the "DATA_VERB_APPR" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBAPPR();
    
    /**
     * Sets the "DATA_VERB_APPR" element
     */
    void setDATAVERBAPPR(java.util.Calendar dataverbappr);
    
    /**
     * Sets (as xml) the "DATA_VERB_APPR" element
     */
    void xsetDATAVERBAPPR(org.apache.xmlbeans.XmlDateTime dataverbappr);
    
    /**
     * Gets the "ALTRE_MOTIVAZIONI" element
     */
    java.lang.String getALTREMOTIVAZIONI();
    
    /**
     * Gets (as xml) the "ALTRE_MOTIVAZIONI" element
     */
    org.apache.xmlbeans.XmlString xgetALTREMOTIVAZIONI();
    
    /**
     * True if has "ALTRE_MOTIVAZIONI" element
     */
    boolean isSetALTREMOTIVAZIONI();
    
    /**
     * Sets the "ALTRE_MOTIVAZIONI" element
     */
    void setALTREMOTIVAZIONI(java.lang.String altremotivazioni);
    
    /**
     * Sets (as xml) the "ALTRE_MOTIVAZIONI" element
     */
    void xsetALTREMOTIVAZIONI(org.apache.xmlbeans.XmlString altremotivazioni);
    
    /**
     * Unsets the "ALTRE_MOTIVAZIONI" element
     */
    void unsetALTREMOTIVAZIONI();
    
    /**
     * Gets the "IMP_RIDET_LAVORI" element
     */
    double getIMPRIDETLAVORI();
    
    /**
     * Gets (as xml) the "IMP_RIDET_LAVORI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPRIDETLAVORI();
    
    /**
     * True if has "IMP_RIDET_LAVORI" element
     */
    boolean isSetIMPRIDETLAVORI();
    
    /**
     * Sets the "IMP_RIDET_LAVORI" element
     */
    void setIMPRIDETLAVORI(double impridetlavori);
    
    /**
     * Sets (as xml) the "IMP_RIDET_LAVORI" element
     */
    void xsetIMPRIDETLAVORI(org.apache.xmlbeans.XmlDouble impridetlavori);
    
    /**
     * Unsets the "IMP_RIDET_LAVORI" element
     */
    void unsetIMPRIDETLAVORI();
    
    /**
     * Gets the "IMP_RIDET_SERVIZI" element
     */
    double getIMPRIDETSERVIZI();
    
    /**
     * Gets (as xml) the "IMP_RIDET_SERVIZI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPRIDETSERVIZI();
    
    /**
     * True if has "IMP_RIDET_SERVIZI" element
     */
    boolean isSetIMPRIDETSERVIZI();
    
    /**
     * Sets the "IMP_RIDET_SERVIZI" element
     */
    void setIMPRIDETSERVIZI(double impridetservizi);
    
    /**
     * Sets (as xml) the "IMP_RIDET_SERVIZI" element
     */
    void xsetIMPRIDETSERVIZI(org.apache.xmlbeans.XmlDouble impridetservizi);
    
    /**
     * Unsets the "IMP_RIDET_SERVIZI" element
     */
    void unsetIMPRIDETSERVIZI();
    
    /**
     * Gets the "IMP_RIDET_FORNIT" element
     */
    double getIMPRIDETFORNIT();
    
    /**
     * Gets (as xml) the "IMP_RIDET_FORNIT" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPRIDETFORNIT();
    
    /**
     * True if has "IMP_RIDET_FORNIT" element
     */
    boolean isSetIMPRIDETFORNIT();
    
    /**
     * Sets the "IMP_RIDET_FORNIT" element
     */
    void setIMPRIDETFORNIT(double impridetfornit);
    
    /**
     * Sets (as xml) the "IMP_RIDET_FORNIT" element
     */
    void xsetIMPRIDETFORNIT(org.apache.xmlbeans.XmlDouble impridetfornit);
    
    /**
     * Unsets the "IMP_RIDET_FORNIT" element
     */
    void unsetIMPRIDETFORNIT();
    
    /**
     * Gets the "IMP_SICUREZZA" element
     */
    double getIMPSICUREZZA();
    
    /**
     * Gets (as xml) the "IMP_SICUREZZA" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPSICUREZZA();
    
    /**
     * True if has "IMP_SICUREZZA" element
     */
    boolean isSetIMPSICUREZZA();
    
    /**
     * Sets the "IMP_SICUREZZA" element
     */
    void setIMPSICUREZZA(double impsicurezza);
    
    /**
     * Sets (as xml) the "IMP_SICUREZZA" element
     */
    void xsetIMPSICUREZZA(org.apache.xmlbeans.XmlDouble impsicurezza);
    
    /**
     * Unsets the "IMP_SICUREZZA" element
     */
    void unsetIMPSICUREZZA();
    
    /**
     * Gets the "IMP_PROGETTAZIONE" element
     */
    double getIMPPROGETTAZIONE();
    
    /**
     * Gets (as xml) the "IMP_PROGETTAZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPPROGETTAZIONE();
    
    /**
     * True if has "IMP_PROGETTAZIONE" element
     */
    boolean isSetIMPPROGETTAZIONE();
    
    /**
     * Sets the "IMP_PROGETTAZIONE" element
     */
    void setIMPPROGETTAZIONE(double impprogettazione);
    
    /**
     * Sets (as xml) the "IMP_PROGETTAZIONE" element
     */
    void xsetIMPPROGETTAZIONE(org.apache.xmlbeans.XmlDouble impprogettazione);
    
    /**
     * Unsets the "IMP_PROGETTAZIONE" element
     */
    void unsetIMPPROGETTAZIONE();
    
    /**
     * Gets the "IMP_DISPOSIZIONE" element
     */
    double getIMPDISPOSIZIONE();
    
    /**
     * Gets (as xml) the "IMP_DISPOSIZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPDISPOSIZIONE();
    
    /**
     * True if has "IMP_DISPOSIZIONE" element
     */
    boolean isSetIMPDISPOSIZIONE();
    
    /**
     * Sets the "IMP_DISPOSIZIONE" element
     */
    void setIMPDISPOSIZIONE(double impdisposizione);
    
    /**
     * Sets (as xml) the "IMP_DISPOSIZIONE" element
     */
    void xsetIMPDISPOSIZIONE(org.apache.xmlbeans.XmlDouble impdisposizione);
    
    /**
     * Unsets the "IMP_DISPOSIZIONE" element
     */
    void unsetIMPDISPOSIZIONE();
    
    /**
     * Gets the "DATA_ATTO_AGGIUNTIVO" element
     */
    java.util.Calendar getDATAATTOAGGIUNTIVO();
    
    /**
     * Gets (as xml) the "DATA_ATTO_AGGIUNTIVO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAATTOAGGIUNTIVO();
    
    /**
     * True if has "DATA_ATTO_AGGIUNTIVO" element
     */
    boolean isSetDATAATTOAGGIUNTIVO();
    
    /**
     * Sets the "DATA_ATTO_AGGIUNTIVO" element
     */
    void setDATAATTOAGGIUNTIVO(java.util.Calendar dataattoaggiuntivo);
    
    /**
     * Sets (as xml) the "DATA_ATTO_AGGIUNTIVO" element
     */
    void xsetDATAATTOAGGIUNTIVO(org.apache.xmlbeans.XmlDateTime dataattoaggiuntivo);
    
    /**
     * Unsets the "DATA_ATTO_AGGIUNTIVO" element
     */
    void unsetDATAATTOAGGIUNTIVO();
    
    /**
     * Gets the "NUM_GIORNI_PROROGA" element
     */
    long getNUMGIORNIPROROGA();
    
    /**
     * Gets (as xml) the "NUM_GIORNI_PROROGA" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMGIORNIPROROGA();
    
    /**
     * True if has "NUM_GIORNI_PROROGA" element
     */
    boolean isSetNUMGIORNIPROROGA();
    
    /**
     * Sets the "NUM_GIORNI_PROROGA" element
     */
    void setNUMGIORNIPROROGA(long numgiorniproroga);
    
    /**
     * Sets (as xml) the "NUM_GIORNI_PROROGA" element
     */
    void xsetNUMGIORNIPROROGA(org.apache.xmlbeans.XmlLong numgiorniproroga);
    
    /**
     * Unsets the "NUM_GIORNI_PROROGA" element
     */
    void unsetNUMGIORNIPROROGA();
    
    /**
     * Gets the "IMPORTO_SUBTOTALE" element
     */
    double getIMPORTOSUBTOTALE();
    
    /**
     * Gets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOSUBTOTALE();
    
    /**
     * Sets the "IMPORTO_SUBTOTALE" element
     */
    void setIMPORTOSUBTOTALE(double importosubtotale);
    
    /**
     * Sets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    void xsetIMPORTOSUBTOTALE(org.apache.xmlbeans.XmlDouble importosubtotale);
    
    /**
     * Gets the "IMPORTO_RIDETERMINATO" element
     */
    double getIMPORTORIDETERMINATO();
    
    /**
     * Gets (as xml) the "IMPORTO_RIDETERMINATO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTORIDETERMINATO();
    
    /**
     * Sets the "IMPORTO_RIDETERMINATO" element
     */
    void setIMPORTORIDETERMINATO(double importorideterminato);
    
    /**
     * Sets (as xml) the "IMPORTO_RIDETERMINATO" element
     */
    void xsetIMPORTORIDETERMINATO(org.apache.xmlbeans.XmlDouble importorideterminato);
    
    /**
     * Gets the "IMPORTO_COMPLESSIVO" element
     */
    double getIMPORTOCOMPLESSIVO();
    
    /**
     * Gets (as xml) the "IMPORTO_COMPLESSIVO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOCOMPLESSIVO();
    
    /**
     * Sets the "IMPORTO_COMPLESSIVO" element
     */
    void setIMPORTOCOMPLESSIVO(double importocomplessivo);
    
    /**
     * Sets (as xml) the "IMPORTO_COMPLESSIVO" element
     */
    void xsetIMPORTOCOMPLESSIVO(org.apache.xmlbeans.XmlDouble importocomplessivo);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
