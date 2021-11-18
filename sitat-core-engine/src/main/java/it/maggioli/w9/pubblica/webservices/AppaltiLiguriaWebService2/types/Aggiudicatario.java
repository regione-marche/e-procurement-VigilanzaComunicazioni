/*
 * XML Type:  Aggiudicatario
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML Aggiudicatario(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface Aggiudicatario extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Aggiudicatario.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("aggiudicatario8760type");
    
    /**
     * Gets the "TIPOLOGIA_SOGGETTO" element
     */
    java.lang.String getTIPOLOGIASOGGETTO();
    
    /**
     * Gets (as xml) the "TIPOLOGIA_SOGGETTO" element
     */
    org.apache.xmlbeans.XmlString xgetTIPOLOGIASOGGETTO();
    
    /**
     * Sets the "TIPOLOGIA_SOGGETTO" element
     */
    void setTIPOLOGIASOGGETTO(java.lang.String tipologiasoggetto);
    
    /**
     * Sets (as xml) the "TIPOLOGIA_SOGGETTO" element
     */
    void xsetTIPOLOGIASOGGETTO(org.apache.xmlbeans.XmlString tipologiasoggetto);
    
    /**
     * Gets the "RUOLO" element
     */
    java.lang.String getRUOLO();
    
    /**
     * Gets (as xml) the "RUOLO" element
     */
    org.apache.xmlbeans.XmlString xgetRUOLO();
    
    /**
     * True if has "RUOLO" element
     */
    boolean isSetRUOLO();
    
    /**
     * Sets the "RUOLO" element
     */
    void setRUOLO(java.lang.String ruolo);
    
    /**
     * Sets (as xml) the "RUOLO" element
     */
    void xsetRUOLO(org.apache.xmlbeans.XmlString ruolo);
    
    /**
     * Unsets the "RUOLO" element
     */
    void unsetRUOLO();
    
    /**
     * Gets the "FLAG_AVVALIMENTO" element
     */
    java.lang.String getFLAGAVVALIMENTO();
    
    /**
     * Gets (as xml) the "FLAG_AVVALIMENTO" element
     */
    org.apache.xmlbeans.XmlString xgetFLAGAVVALIMENTO();
    
    /**
     * True if has "FLAG_AVVALIMENTO" element
     */
    boolean isSetFLAGAVVALIMENTO();
    
    /**
     * Sets the "FLAG_AVVALIMENTO" element
     */
    void setFLAGAVVALIMENTO(java.lang.String flagavvalimento);
    
    /**
     * Sets (as xml) the "FLAG_AVVALIMENTO" element
     */
    void xsetFLAGAVVALIMENTO(org.apache.xmlbeans.XmlString flagavvalimento);
    
    /**
     * Unsets the "FLAG_AVVALIMENTO" element
     */
    void unsetFLAGAVVALIMENTO();
    
    /**
     * Gets the "ID_GRUPPO" element
     */
    long getIDGRUPPO();
    
    /**
     * Gets (as xml) the "ID_GRUPPO" element
     */
    org.apache.xmlbeans.XmlLong xgetIDGRUPPO();
    
    /**
     * True if has "ID_GRUPPO" element
     */
    boolean isSetIDGRUPPO();
    
    /**
     * Sets the "ID_GRUPPO" element
     */
    void setIDGRUPPO(long idgruppo);
    
    /**
     * Sets (as xml) the "ID_GRUPPO" element
     */
    void xsetIDGRUPPO(org.apache.xmlbeans.XmlLong idgruppo);
    
    /**
     * Unsets the "ID_GRUPPO" element
     */
    void unsetIDGRUPPO();
    
    /**
     * Gets the "MOTIVO_AVVALIMENTO" element
     */
    java.lang.String getMOTIVOAVVALIMENTO();
    
    /**
     * Gets (as xml) the "MOTIVO_AVVALIMENTO" element
     */
    org.apache.xmlbeans.XmlString xgetMOTIVOAVVALIMENTO();
    
    /**
     * True if has "MOTIVO_AVVALIMENTO" element
     */
    boolean isSetMOTIVOAVVALIMENTO();
    
    /**
     * Sets the "MOTIVO_AVVALIMENTO" element
     */
    void setMOTIVOAVVALIMENTO(java.lang.String motivoavvalimento);
    
    /**
     * Sets (as xml) the "MOTIVO_AVVALIMENTO" element
     */
    void xsetMOTIVOAVVALIMENTO(org.apache.xmlbeans.XmlString motivoavvalimento);
    
    /**
     * Unsets the "MOTIVO_AVVALIMENTO" element
     */
    void unsetMOTIVOAVVALIMENTO();
    
    /**
     * Gets the "aggiudicatario" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getAggiudicatario();
    
    /**
     * Sets the "aggiudicatario" element
     */
    void setAggiudicatario(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari aggiudicatario);
    
    /**
     * Appends and returns a new empty "aggiudicatario" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewAggiudicatario();
    
    /**
     * Gets the "aggiudicatario_avv" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getAggiudicatarioAvv();
    
    /**
     * True if has "aggiudicatario_avv" element
     */
    boolean isSetAggiudicatarioAvv();
    
    /**
     * Sets the "aggiudicatario_avv" element
     */
    void setAggiudicatarioAvv(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari aggiudicatarioAvv);
    
    /**
     * Appends and returns a new empty "aggiudicatario_avv" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewAggiudicatarioAvv();
    
    /**
     * Unsets the "aggiudicatario_avv" element
     */
    void unsetAggiudicatarioAvv();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
