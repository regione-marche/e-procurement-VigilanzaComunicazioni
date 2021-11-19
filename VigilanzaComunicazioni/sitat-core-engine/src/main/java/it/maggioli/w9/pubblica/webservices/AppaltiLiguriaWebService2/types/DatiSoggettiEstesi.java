/*
 * XML Type:  DatiSoggettiEstesi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiSoggettiEstesi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiSoggettiEstesi extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiSoggettiEstesi.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datisoggettiestesib194type");
    
    /**
     * Gets the "ID_RUOLO" element
     */
    java.lang.String getIDRUOLO();
    
    /**
     * Gets (as xml) the "ID_RUOLO" element
     */
    org.apache.xmlbeans.XmlString xgetIDRUOLO();
    
    /**
     * Sets the "ID_RUOLO" element
     */
    void setIDRUOLO(java.lang.String idruolo);
    
    /**
     * Sets (as xml) the "ID_RUOLO" element
     */
    void xsetIDRUOLO(org.apache.xmlbeans.XmlString idruolo);
    
    /**
     * Gets the "CIG_PROG_ESTERNA" element
     */
    java.lang.String getCIGPROGESTERNA();
    
    /**
     * Gets (as xml) the "CIG_PROG_ESTERNA" element
     */
    org.apache.xmlbeans.XmlString xgetCIGPROGESTERNA();
    
    /**
     * True if has "CIG_PROG_ESTERNA" element
     */
    boolean isSetCIGPROGESTERNA();
    
    /**
     * Sets the "CIG_PROG_ESTERNA" element
     */
    void setCIGPROGESTERNA(java.lang.String cigprogesterna);
    
    /**
     * Sets (as xml) the "CIG_PROG_ESTERNA" element
     */
    void xsetCIGPROGESTERNA(org.apache.xmlbeans.XmlString cigprogesterna);
    
    /**
     * Unsets the "CIG_PROG_ESTERNA" element
     */
    void unsetCIGPROGESTERNA();
    
    /**
     * Gets the "DATA_AFF_PROG_ESTERNA" element
     */
    java.util.Calendar getDATAAFFPROGESTERNA();
    
    /**
     * Gets (as xml) the "DATA_AFF_PROG_ESTERNA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAAFFPROGESTERNA();
    
    /**
     * True if has "DATA_AFF_PROG_ESTERNA" element
     */
    boolean isSetDATAAFFPROGESTERNA();
    
    /**
     * Sets the "DATA_AFF_PROG_ESTERNA" element
     */
    void setDATAAFFPROGESTERNA(java.util.Calendar dataaffprogesterna);
    
    /**
     * Sets (as xml) the "DATA_AFF_PROG_ESTERNA" element
     */
    void xsetDATAAFFPROGESTERNA(org.apache.xmlbeans.XmlDateTime dataaffprogesterna);
    
    /**
     * Unsets the "DATA_AFF_PROG_ESTERNA" element
     */
    void unsetDATAAFFPROGESTERNA();
    
    /**
     * Gets the "DATA_CONS_PROG_ESTERNA" element
     */
    java.util.Calendar getDATACONSPROGESTERNA();
    
    /**
     * Gets (as xml) the "DATA_CONS_PROG_ESTERNA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATACONSPROGESTERNA();
    
    /**
     * True if has "DATA_CONS_PROG_ESTERNA" element
     */
    boolean isSetDATACONSPROGESTERNA();
    
    /**
     * Sets the "DATA_CONS_PROG_ESTERNA" element
     */
    void setDATACONSPROGESTERNA(java.util.Calendar dataconsprogesterna);
    
    /**
     * Sets (as xml) the "DATA_CONS_PROG_ESTERNA" element
     */
    void xsetDATACONSPROGESTERNA(org.apache.xmlbeans.XmlDateTime dataconsprogesterna);
    
    /**
     * Unsets the "DATA_CONS_PROG_ESTERNA" element
     */
    void unsetDATACONSPROGESTERNA();
    
    /**
     * Gets the "SEZIONE" element
     */
    java.lang.String getSEZIONE();
    
    /**
     * Gets (as xml) the "SEZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetSEZIONE();
    
    /**
     * True if has "SEZIONE" element
     */
    boolean isSetSEZIONE();
    
    /**
     * Sets the "SEZIONE" element
     */
    void setSEZIONE(java.lang.String sezione);
    
    /**
     * Sets (as xml) the "SEZIONE" element
     */
    void xsetSEZIONE(org.apache.xmlbeans.XmlString sezione);
    
    /**
     * Unsets the "SEZIONE" element
     */
    void unsetSEZIONE();
    
    /**
     * Gets the "COSTO_PROGETTO" element
     */
    double getCOSTOPROGETTO();
    
    /**
     * Gets (as xml) the "COSTO_PROGETTO" element
     */
    org.apache.xmlbeans.XmlDouble xgetCOSTOPROGETTO();
    
    /**
     * True if has "COSTO_PROGETTO" element
     */
    boolean isSetCOSTOPROGETTO();
    
    /**
     * Sets the "COSTO_PROGETTO" element
     */
    void setCOSTOPROGETTO(double costoprogetto);
    
    /**
     * Sets (as xml) the "COSTO_PROGETTO" element
     */
    void xsetCOSTOPROGETTO(org.apache.xmlbeans.XmlDouble costoprogetto);
    
    /**
     * Unsets the "COSTO_PROGETTO" element
     */
    void unsetCOSTOPROGETTO();
    
    /**
     * Gets the "FLAG_COLLAUDATORE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType.Enum getFLAGCOLLAUDATORE();
    
    /**
     * Gets (as xml) the "FLAG_COLLAUDATORE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType xgetFLAGCOLLAUDATORE();
    
    /**
     * True if has "FLAG_COLLAUDATORE" element
     */
    boolean isSetFLAGCOLLAUDATORE();
    
    /**
     * Sets the "FLAG_COLLAUDATORE" element
     */
    void setFLAGCOLLAUDATORE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType.Enum flagcollaudatore);
    
    /**
     * Sets (as xml) the "FLAG_COLLAUDATORE" element
     */
    void xsetFLAGCOLLAUDATORE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType flagcollaudatore);
    
    /**
     * Unsets the "FLAG_COLLAUDATORE" element
     */
    void unsetFLAGCOLLAUDATORE();
    
    /**
     * Gets the "IMPORTO_COLLAUDATORE" element
     */
    double getIMPORTOCOLLAUDATORE();
    
    /**
     * Gets (as xml) the "IMPORTO_COLLAUDATORE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOCOLLAUDATORE();
    
    /**
     * True if has "IMPORTO_COLLAUDATORE" element
     */
    boolean isSetIMPORTOCOLLAUDATORE();
    
    /**
     * Sets the "IMPORTO_COLLAUDATORE" element
     */
    void setIMPORTOCOLLAUDATORE(double importocollaudatore);
    
    /**
     * Sets (as xml) the "IMPORTO_COLLAUDATORE" element
     */
    void xsetIMPORTOCOLLAUDATORE(org.apache.xmlbeans.XmlDouble importocollaudatore);
    
    /**
     * Unsets the "IMPORTO_COLLAUDATORE" element
     */
    void unsetIMPORTOCOLLAUDATORE();
    
    /**
     * Gets the "responsabile" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile getResponsabile();
    
    /**
     * Sets the "responsabile" element
     */
    void setResponsabile(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile responsabile);
    
    /**
     * Appends and returns a new empty "responsabile" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile addNewResponsabile();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
