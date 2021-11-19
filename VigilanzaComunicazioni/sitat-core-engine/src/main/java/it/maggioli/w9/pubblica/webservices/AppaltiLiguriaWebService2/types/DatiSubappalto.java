/*
 * XML Type:  DatiSubappalto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiSubappalto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiSubappalto extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiSubappalto.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datisubappalto9802type");
    
    /**
     * Gets the "DATA_AUTORIZZAZIONE" element
     */
    java.util.Calendar getDATAAUTORIZZAZIONE();
    
    /**
     * Gets (as xml) the "DATA_AUTORIZZAZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAAUTORIZZAZIONE();
    
    /**
     * Sets the "DATA_AUTORIZZAZIONE" element
     */
    void setDATAAUTORIZZAZIONE(java.util.Calendar dataautorizzazione);
    
    /**
     * Sets (as xml) the "DATA_AUTORIZZAZIONE" element
     */
    void xsetDATAAUTORIZZAZIONE(org.apache.xmlbeans.XmlDateTime dataautorizzazione);
    
    /**
     * Gets the "OGGETTO_SUBAPPALTO" element
     */
    java.lang.String getOGGETTOSUBAPPALTO();
    
    /**
     * Gets (as xml) the "OGGETTO_SUBAPPALTO" element
     */
    org.apache.xmlbeans.XmlString xgetOGGETTOSUBAPPALTO();
    
    /**
     * Sets the "OGGETTO_SUBAPPALTO" element
     */
    void setOGGETTOSUBAPPALTO(java.lang.String oggettosubappalto);
    
    /**
     * Sets (as xml) the "OGGETTO_SUBAPPALTO" element
     */
    void xsetOGGETTOSUBAPPALTO(org.apache.xmlbeans.XmlString oggettosubappalto);
    
    /**
     * Gets the "IMPORTO_PRESUNTO" element
     */
    double getIMPORTOPRESUNTO();
    
    /**
     * Gets (as xml) the "IMPORTO_PRESUNTO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOPRESUNTO();
    
    /**
     * Sets the "IMPORTO_PRESUNTO" element
     */
    void setIMPORTOPRESUNTO(double importopresunto);
    
    /**
     * Sets (as xml) the "IMPORTO_PRESUNTO" element
     */
    void xsetIMPORTOPRESUNTO(org.apache.xmlbeans.XmlDouble importopresunto);
    
    /**
     * Gets the "IMPORTO_EFFETTIVO" element
     */
    double getIMPORTOEFFETTIVO();
    
    /**
     * Gets (as xml) the "IMPORTO_EFFETTIVO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOEFFETTIVO();
    
    /**
     * Sets the "IMPORTO_EFFETTIVO" element
     */
    void setIMPORTOEFFETTIVO(double importoeffettivo);
    
    /**
     * Sets (as xml) the "IMPORTO_EFFETTIVO" element
     */
    void xsetIMPORTOEFFETTIVO(org.apache.xmlbeans.XmlDouble importoeffettivo);
    
    /**
     * Gets the "ID_CATEGORIA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum getIDCATEGORIA();
    
    /**
     * Gets (as xml) the "ID_CATEGORIA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType xgetIDCATEGORIA();
    
    /**
     * Sets the "ID_CATEGORIA" element
     */
    void setIDCATEGORIA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType.Enum idcategoria);
    
    /**
     * Sets (as xml) the "ID_CATEGORIA" element
     */
    void xsetIDCATEGORIA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CategoriaType idcategoria);
    
    /**
     * Gets the "ID_CPV" element
     */
    java.lang.String getIDCPV();
    
    /**
     * Gets (as xml) the "ID_CPV" element
     */
    org.apache.xmlbeans.XmlString xgetIDCPV();
    
    /**
     * Sets the "ID_CPV" element
     */
    void setIDCPV(java.lang.String idcpv);
    
    /**
     * Sets (as xml) the "ID_CPV" element
     */
    void xsetIDCPV(org.apache.xmlbeans.XmlString idcpv);
    
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
     * Gets the "arch3_impagg" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getArch3Impagg();
    
    /**
     * True if has "arch3_impagg" element
     */
    boolean isSetArch3Impagg();
    
    /**
     * Sets the "arch3_impagg" element
     */
    void setArch3Impagg(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari arch3Impagg);
    
    /**
     * Appends and returns a new empty "arch3_impagg" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewArch3Impagg();
    
    /**
     * Unsets the "arch3_impagg" element
     */
    void unsetArch3Impagg();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
