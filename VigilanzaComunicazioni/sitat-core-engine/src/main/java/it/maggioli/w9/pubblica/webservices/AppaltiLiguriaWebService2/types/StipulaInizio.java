/*
 * XML Type:  StipulaInizio
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML StipulaInizio(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface StipulaInizio extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(StipulaInizio.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("stipulainizioe76ftype");
    
    /**
     * Gets the "DATA_STIPULA" element
     */
    java.util.Calendar getDATASTIPULA();
    
    /**
     * Gets (as xml) the "DATA_STIPULA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATASTIPULA();
    
    /**
     * Sets the "DATA_STIPULA" element
     */
    void setDATASTIPULA(java.util.Calendar datastipula);
    
    /**
     * Sets (as xml) the "DATA_STIPULA" element
     */
    void xsetDATASTIPULA(org.apache.xmlbeans.XmlDateTime datastipula);
    
    /**
     * Gets the "FLAG_RISERVA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRISERVA();
    
    /**
     * Gets (as xml) the "FLAG_RISERVA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRISERVA();
    
    /**
     * Sets the "FLAG_RISERVA" element
     */
    void setFLAGRISERVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagriserva);
    
    /**
     * Sets (as xml) the "FLAG_RISERVA" element
     */
    void xsetFLAGRISERVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagriserva);
    
    /**
     * Gets the "DATA_VERB_INIZIO" element
     */
    java.util.Calendar getDATAVERBINIZIO();
    
    /**
     * Gets (as xml) the "DATA_VERB_INIZIO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBINIZIO();
    
    /**
     * True if has "DATA_VERB_INIZIO" element
     */
    boolean isSetDATAVERBINIZIO();
    
    /**
     * Sets the "DATA_VERB_INIZIO" element
     */
    void setDATAVERBINIZIO(java.util.Calendar dataverbinizio);
    
    /**
     * Sets (as xml) the "DATA_VERB_INIZIO" element
     */
    void xsetDATAVERBINIZIO(org.apache.xmlbeans.XmlDateTime dataverbinizio);
    
    /**
     * Unsets the "DATA_VERB_INIZIO" element
     */
    void unsetDATAVERBINIZIO();
    
    /**
     * Gets the "DATA_TERMINE" element
     */
    java.util.Calendar getDATATERMINE();
    
    /**
     * Gets (as xml) the "DATA_TERMINE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATATERMINE();
    
    /**
     * True if has "DATA_TERMINE" element
     */
    boolean isSetDATATERMINE();
    
    /**
     * Sets the "DATA_TERMINE" element
     */
    void setDATATERMINE(java.util.Calendar datatermine);
    
    /**
     * Sets (as xml) the "DATA_TERMINE" element
     */
    void xsetDATATERMINE(org.apache.xmlbeans.XmlDateTime datatermine);
    
    /**
     * Unsets the "DATA_TERMINE" element
     */
    void unsetDATATERMINE();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
