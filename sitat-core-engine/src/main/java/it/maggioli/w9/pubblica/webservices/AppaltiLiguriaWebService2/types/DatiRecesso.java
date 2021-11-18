/*
 * XML Type:  DatiRecesso
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiRecesso(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiRecesso extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiRecesso.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datirecessof7a5type");
    
    /**
     * Gets the "DATA_TERMINE" element
     */
    java.util.Calendar getDATATERMINE();
    
    /**
     * Gets (as xml) the "DATA_TERMINE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATATERMINE();
    
    /**
     * Sets the "DATA_TERMINE" element
     */
    void setDATATERMINE(java.util.Calendar datatermine);
    
    /**
     * Sets (as xml) the "DATA_TERMINE" element
     */
    void xsetDATATERMINE(org.apache.xmlbeans.XmlDateTime datatermine);
    
    /**
     * Gets the "DATA_CONSEGNA" element
     */
    java.util.Calendar getDATACONSEGNA();
    
    /**
     * Gets (as xml) the "DATA_CONSEGNA" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATACONSEGNA();
    
    /**
     * True if has "DATA_CONSEGNA" element
     */
    boolean isSetDATACONSEGNA();
    
    /**
     * Sets the "DATA_CONSEGNA" element
     */
    void setDATACONSEGNA(java.util.Calendar dataconsegna);
    
    /**
     * Sets (as xml) the "DATA_CONSEGNA" element
     */
    void xsetDATACONSEGNA(org.apache.xmlbeans.XmlDateTime dataconsegna);
    
    /**
     * Unsets the "DATA_CONSEGNA" element
     */
    void unsetDATACONSEGNA();
    
    /**
     * Gets the "FLAG_TIPO_COMUNICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType.Enum getFLAGTIPOCOMUNICAZIONE();
    
    /**
     * Gets (as xml) the "FLAG_TIPO_COMUNICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType xgetFLAGTIPOCOMUNICAZIONE();
    
    /**
     * Sets the "FLAG_TIPO_COMUNICAZIONE" element
     */
    void setFLAGTIPOCOMUNICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType.Enum flagtipocomunicazione);
    
    /**
     * Sets (as xml) the "FLAG_TIPO_COMUNICAZIONE" element
     */
    void xsetFLAGTIPOCOMUNICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType flagtipocomunicazione);
    
    /**
     * Gets the "DURATA_SOSP" element
     */
    long getDURATASOSP();
    
    /**
     * Gets (as xml) the "DURATA_SOSP" element
     */
    org.apache.xmlbeans.XmlLong xgetDURATASOSP();
    
    /**
     * Sets the "DURATA_SOSP" element
     */
    void setDURATASOSP(long duratasosp);
    
    /**
     * Sets (as xml) the "DURATA_SOSP" element
     */
    void xsetDURATASOSP(org.apache.xmlbeans.XmlLong duratasosp);
    
    /**
     * Gets the "MOTIVO_SOSP" element
     */
    java.lang.String getMOTIVOSOSP();
    
    /**
     * Gets (as xml) the "MOTIVO_SOSP" element
     */
    org.apache.xmlbeans.XmlString xgetMOTIVOSOSP();
    
    /**
     * True if has "MOTIVO_SOSP" element
     */
    boolean isSetMOTIVOSOSP();
    
    /**
     * Sets the "MOTIVO_SOSP" element
     */
    void setMOTIVOSOSP(java.lang.String motivososp);
    
    /**
     * Sets (as xml) the "MOTIVO_SOSP" element
     */
    void xsetMOTIVOSOSP(org.apache.xmlbeans.XmlString motivososp);
    
    /**
     * Unsets the "MOTIVO_SOSP" element
     */
    void unsetMOTIVOSOSP();
    
    /**
     * Gets the "DATA_IST_RECESSO" element
     */
    java.util.Calendar getDATAISTRECESSO();
    
    /**
     * Gets (as xml) the "DATA_IST_RECESSO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAISTRECESSO();
    
    /**
     * Sets the "DATA_IST_RECESSO" element
     */
    void setDATAISTRECESSO(java.util.Calendar dataistrecesso);
    
    /**
     * Sets (as xml) the "DATA_IST_RECESSO" element
     */
    void xsetDATAISTRECESSO(org.apache.xmlbeans.XmlDateTime dataistrecesso);
    
    /**
     * Gets the "FLAG_ACCOLTA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGACCOLTA();
    
    /**
     * Gets (as xml) the "FLAG_ACCOLTA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGACCOLTA();
    
    /**
     * True if has "FLAG_ACCOLTA" element
     */
    boolean isSetFLAGACCOLTA();
    
    /**
     * Sets the "FLAG_ACCOLTA" element
     */
    void setFLAGACCOLTA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagaccolta);
    
    /**
     * Sets (as xml) the "FLAG_ACCOLTA" element
     */
    void xsetFLAGACCOLTA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagaccolta);
    
    /**
     * Unsets the "FLAG_ACCOLTA" element
     */
    void unsetFLAGACCOLTA();
    
    /**
     * Gets the "FLAG_TARDIVA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGTARDIVA();
    
    /**
     * Gets (as xml) the "FLAG_TARDIVA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGTARDIVA();
    
    /**
     * Sets the "FLAG_TARDIVA" element
     */
    void setFLAGTARDIVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagtardiva);
    
    /**
     * Sets (as xml) the "FLAG_TARDIVA" element
     */
    void xsetFLAGTARDIVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagtardiva);
    
    /**
     * Gets the "FLAG_RIPRESA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRIPRESA();
    
    /**
     * Gets (as xml) the "FLAG_RIPRESA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRIPRESA();
    
    /**
     * Sets the "FLAG_RIPRESA" element
     */
    void setFLAGRIPRESA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagripresa);
    
    /**
     * Sets (as xml) the "FLAG_RIPRESA" element
     */
    void xsetFLAGRIPRESA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagripresa);
    
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
     * Gets the "IMPORTO_SPESE" element
     */
    double getIMPORTOSPESE();
    
    /**
     * Gets (as xml) the "IMPORTO_SPESE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOSPESE();
    
    /**
     * True if has "IMPORTO_SPESE" element
     */
    boolean isSetIMPORTOSPESE();
    
    /**
     * Sets the "IMPORTO_SPESE" element
     */
    void setIMPORTOSPESE(double importospese);
    
    /**
     * Sets (as xml) the "IMPORTO_SPESE" element
     */
    void xsetIMPORTOSPESE(org.apache.xmlbeans.XmlDouble importospese);
    
    /**
     * Unsets the "IMPORTO_SPESE" element
     */
    void unsetIMPORTOSPESE();
    
    /**
     * Gets the "IMPORTO_ONERI" element
     */
    double getIMPORTOONERI();
    
    /**
     * Gets (as xml) the "IMPORTO_ONERI" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOONERI();
    
    /**
     * True if has "IMPORTO_ONERI" element
     */
    boolean isSetIMPORTOONERI();
    
    /**
     * Sets the "IMPORTO_ONERI" element
     */
    void setIMPORTOONERI(double importooneri);
    
    /**
     * Sets (as xml) the "IMPORTO_ONERI" element
     */
    void xsetIMPORTOONERI(org.apache.xmlbeans.XmlDouble importooneri);
    
    /**
     * Unsets the "IMPORTO_ONERI" element
     */
    void unsetIMPORTOONERI();
    
    /**
     * Gets the "GG_RITARDO" element
     */
    long getGGRITARDO();
    
    /**
     * Gets (as xml) the "GG_RITARDO" element
     */
    org.apache.xmlbeans.XmlLong xgetGGRITARDO();
    
    /**
     * True if has "GG_RITARDO" element
     */
    boolean isSetGGRITARDO();
    
    /**
     * Sets the "GG_RITARDO" element
     */
    void setGGRITARDO(long ggritardo);
    
    /**
     * Sets (as xml) the "GG_RITARDO" element
     */
    void xsetGGRITARDO(org.apache.xmlbeans.XmlLong ggritardo);
    
    /**
     * Unsets the "GG_RITARDO" element
     */
    void unsetGGRITARDO();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
