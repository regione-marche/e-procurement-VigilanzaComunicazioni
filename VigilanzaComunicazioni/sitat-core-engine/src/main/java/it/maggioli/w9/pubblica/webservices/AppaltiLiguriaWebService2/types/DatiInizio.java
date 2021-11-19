/*
 * XML Type:  DatiInizio
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiInizio(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiInizio extends it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiInizio.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datiinizio7e2dtype");
    
    /**
     * Gets the "DATA_ESECUTIVITA" element
     */
    java.util.Calendar getDATAESECUTIVITA();
    
    /**
     * Gets (as xml) the "DATA_ESECUTIVITA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAESECUTIVITA();
    
    /**
     * Sets the "DATA_ESECUTIVITA" element
     */
    void setDATAESECUTIVITA(java.util.Calendar dataesecutivita);
    
    /**
     * Sets (as xml) the "DATA_ESECUTIVITA" element
     */
    void xsetDATAESECUTIVITA(org.apache.xmlbeans.XmlDateTime dataesecutivita);
    
    /**
     * Gets the "IMPORTO_CAUZIONE" element
     */
    double getIMPORTOCAUZIONE();
    
    /**
     * Gets (as xml) the "IMPORTO_CAUZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOCAUZIONE();
    
    /**
     * Sets the "IMPORTO_CAUZIONE" element
     */
    void setIMPORTOCAUZIONE(double importocauzione);
    
    /**
     * Sets (as xml) the "IMPORTO_CAUZIONE" element
     */
    void xsetIMPORTOCAUZIONE(org.apache.xmlbeans.XmlDouble importocauzione);
    
    /**
     * Gets the "DATA_INI_PROG_ESEC" element
     */
    java.util.Calendar getDATAINIPROGESEC();
    
    /**
     * Gets (as xml) the "DATA_INI_PROG_ESEC" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAINIPROGESEC();
    
    /**
     * True if has "DATA_INI_PROG_ESEC" element
     */
    boolean isSetDATAINIPROGESEC();
    
    /**
     * Sets the "DATA_INI_PROG_ESEC" element
     */
    void setDATAINIPROGESEC(java.util.Calendar datainiprogesec);
    
    /**
     * Sets (as xml) the "DATA_INI_PROG_ESEC" element
     */
    void xsetDATAINIPROGESEC(org.apache.xmlbeans.XmlDateTime datainiprogesec);
    
    /**
     * Unsets the "DATA_INI_PROG_ESEC" element
     */
    void unsetDATAINIPROGESEC();
    
    /**
     * Gets the "DATA_APP_PROG_ESEC" element
     */
    java.util.Calendar getDATAAPPPROGESEC();
    
    /**
     * Gets (as xml) the "DATA_APP_PROG_ESEC" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAAPPPROGESEC();
    
    /**
     * True if has "DATA_APP_PROG_ESEC" element
     */
    boolean isSetDATAAPPPROGESEC();
    
    /**
     * Sets the "DATA_APP_PROG_ESEC" element
     */
    void setDATAAPPPROGESEC(java.util.Calendar dataappprogesec);
    
    /**
     * Sets (as xml) the "DATA_APP_PROG_ESEC" element
     */
    void xsetDATAAPPPROGESEC(org.apache.xmlbeans.XmlDateTime dataappprogesec);
    
    /**
     * Unsets the "DATA_APP_PROG_ESEC" element
     */
    void unsetDATAAPPPROGESEC();
    
    /**
     * Gets the "FLAG_FRAZIONATA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGFRAZIONATA();
    
    /**
     * Gets (as xml) the "FLAG_FRAZIONATA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGFRAZIONATA();
    
    /**
     * Sets the "FLAG_FRAZIONATA" element
     */
    void setFLAGFRAZIONATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagfrazionata);
    
    /**
     * Sets (as xml) the "FLAG_FRAZIONATA" element
     */
    void xsetFLAGFRAZIONATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagfrazionata);
    
    /**
     * Gets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    java.util.Calendar getDATAVERBPRIMACONSEGNA();
    
    /**
     * Gets (as xml) the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBPRIMACONSEGNA();
    
    /**
     * True if has "DATA_VERB_PRIMA_CONSEGNA" element
     */
    boolean isSetDATAVERBPRIMACONSEGNA();
    
    /**
     * Sets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    void setDATAVERBPRIMACONSEGNA(java.util.Calendar dataverbprimaconsegna);
    
    /**
     * Sets (as xml) the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    void xsetDATAVERBPRIMACONSEGNA(org.apache.xmlbeans.XmlDateTime dataverbprimaconsegna);
    
    /**
     * Unsets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    void unsetDATAVERBPRIMACONSEGNA();
    
    /**
     * Gets the "DATA_VERBALE_DEF" element
     */
    java.util.Calendar getDATAVERBALEDEF();
    
    /**
     * Gets (as xml) the "DATA_VERBALE_DEF" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBALEDEF();
    
    /**
     * Sets the "DATA_VERBALE_DEF" element
     */
    void setDATAVERBALEDEF(java.util.Calendar dataverbaledef);
    
    /**
     * Sets (as xml) the "DATA_VERBALE_DEF" element
     */
    void xsetDATAVERBALEDEF(org.apache.xmlbeans.XmlDateTime dataverbaledef);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
