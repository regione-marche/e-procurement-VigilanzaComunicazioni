/*
 * XML Type:  SezioneSubappalti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML SezioneSubappalti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface SezioneSubappalti extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SezioneSubappalti.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("sezionesubappaltif8c7type");
    
    /**
     * Gets the "DATI_SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto getDATISUBAPPALTI();
    
    /**
     * True if has "DATI_SUBAPPALTI" element
     */
    boolean isSetDATISUBAPPALTI();
    
    /**
     * Sets the "DATI_SUBAPPALTI" element
     */
    void setDATISUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto datisubappalti);
    
    /**
     * Appends and returns a new empty "DATI_SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto addNewDATISUBAPPALTI();
    
    /**
     * Unsets the "DATI_SUBAPPALTI" element
     */
    void unsetDATISUBAPPALTI();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
