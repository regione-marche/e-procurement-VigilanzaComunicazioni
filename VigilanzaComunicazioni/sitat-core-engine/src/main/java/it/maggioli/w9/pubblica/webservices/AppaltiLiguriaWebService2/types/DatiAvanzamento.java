/*
 * XML Type:  DatiAvanzamento
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiAvanzamento(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiAvanzamento extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiAvanzamento.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datiavanzamentoa457type");
    
    /**
     * Gets the "SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSUBAPPALTI();
    
    /**
     * Gets (as xml) the "SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSUBAPPALTI();
    
    /**
     * Sets the "SUBAPPALTI" element
     */
    void setSUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum subappalti);
    
    /**
     * Sets (as xml) the "SUBAPPALTI" element
     */
    void xsetSUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType subappalti);
    
    /**
     * Gets the "DATA_CERTIFICATO" element
     */
    java.util.Calendar getDATACERTIFICATO();
    
    /**
     * Gets (as xml) the "DATA_CERTIFICATO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATACERTIFICATO();
    
    /**
     * True if has "DATA_CERTIFICATO" element
     */
    boolean isSetDATACERTIFICATO();
    
    /**
     * Sets the "DATA_CERTIFICATO" element
     */
    void setDATACERTIFICATO(java.util.Calendar datacertificato);
    
    /**
     * Sets (as xml) the "DATA_CERTIFICATO" element
     */
    void xsetDATACERTIFICATO(org.apache.xmlbeans.XmlDateTime datacertificato);
    
    /**
     * Unsets the "DATA_CERTIFICATO" element
     */
    void unsetDATACERTIFICATO();
    
    /**
     * Gets the "IMPORTO_CERTIFICATO" element
     */
    double getIMPORTOCERTIFICATO();
    
    /**
     * Gets (as xml) the "IMPORTO_CERTIFICATO" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOCERTIFICATO();
    
    /**
     * Sets the "IMPORTO_CERTIFICATO" element
     */
    void setIMPORTOCERTIFICATO(double importocertificato);
    
    /**
     * Sets (as xml) the "IMPORTO_CERTIFICATO" element
     */
    void xsetIMPORTOCERTIFICATO(org.apache.xmlbeans.XmlDouble importocertificato);
    
    /**
     * Gets the "FLAG_RITARDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType.Enum getFLAGRITARDO();
    
    /**
     * Gets (as xml) the "FLAG_RITARDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType xgetFLAGRITARDO();
    
    /**
     * Sets the "FLAG_RITARDO" element
     */
    void setFLAGRITARDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType.Enum flagritardo);
    
    /**
     * Sets (as xml) the "FLAG_RITARDO" element
     */
    void xsetFLAGRITARDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType flagritardo);
    
    /**
     * Gets the "NUM_GIORNI_SCOST" element
     */
    long getNUMGIORNISCOST();
    
    /**
     * Gets (as xml) the "NUM_GIORNI_SCOST" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMGIORNISCOST();
    
    /**
     * True if has "NUM_GIORNI_SCOST" element
     */
    boolean isSetNUMGIORNISCOST();
    
    /**
     * Sets the "NUM_GIORNI_SCOST" element
     */
    void setNUMGIORNISCOST(long numgiorniscost);
    
    /**
     * Sets (as xml) the "NUM_GIORNI_SCOST" element
     */
    void xsetNUMGIORNISCOST(org.apache.xmlbeans.XmlLong numgiorniscost);
    
    /**
     * Unsets the "NUM_GIORNI_SCOST" element
     */
    void unsetNUMGIORNISCOST();
    
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
     * Gets the "FLAG_PAGAMENTO" element
     */
    java.lang.String getFLAGPAGAMENTO();
    
    /**
     * Gets (as xml) the "FLAG_PAGAMENTO" element
     */
    org.apache.xmlbeans.XmlString xgetFLAGPAGAMENTO();
    
    /**
     * Sets the "FLAG_PAGAMENTO" element
     */
    void setFLAGPAGAMENTO(java.lang.String flagpagamento);
    
    /**
     * Sets (as xml) the "FLAG_PAGAMENTO" element
     */
    void xsetFLAGPAGAMENTO(org.apache.xmlbeans.XmlString flagpagamento);
    
    /**
     * Gets the "DATA_ANTICIPAZIONE" element
     */
    java.util.Calendar getDATAANTICIPAZIONE();
    
    /**
     * Gets (as xml) the "DATA_ANTICIPAZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAANTICIPAZIONE();
    
    /**
     * True if has "DATA_ANTICIPAZIONE" element
     */
    boolean isSetDATAANTICIPAZIONE();
    
    /**
     * Sets the "DATA_ANTICIPAZIONE" element
     */
    void setDATAANTICIPAZIONE(java.util.Calendar dataanticipazione);
    
    /**
     * Sets (as xml) the "DATA_ANTICIPAZIONE" element
     */
    void xsetDATAANTICIPAZIONE(org.apache.xmlbeans.XmlDateTime dataanticipazione);
    
    /**
     * Unsets the "DATA_ANTICIPAZIONE" element
     */
    void unsetDATAANTICIPAZIONE();
    
    /**
     * Gets the "IMPORTO_ANTICIPAZIONE" element
     */
    double getIMPORTOANTICIPAZIONE();
    
    /**
     * Gets (as xml) the "IMPORTO_ANTICIPAZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOANTICIPAZIONE();
    
    /**
     * True if has "IMPORTO_ANTICIPAZIONE" element
     */
    boolean isSetIMPORTOANTICIPAZIONE();
    
    /**
     * Sets the "IMPORTO_ANTICIPAZIONE" element
     */
    void setIMPORTOANTICIPAZIONE(double importoanticipazione);
    
    /**
     * Sets (as xml) the "IMPORTO_ANTICIPAZIONE" element
     */
    void xsetIMPORTOANTICIPAZIONE(org.apache.xmlbeans.XmlDouble importoanticipazione);
    
    /**
     * Unsets the "IMPORTO_ANTICIPAZIONE" element
     */
    void unsetIMPORTOANTICIPAZIONE();
    
    /**
     * Gets the "DATA_RAGGIUNGIMENTO" element
     */
    java.util.Calendar getDATARAGGIUNGIMENTO();
    
    /**
     * Gets (as xml) the "DATA_RAGGIUNGIMENTO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATARAGGIUNGIMENTO();
    
    /**
     * Sets the "DATA_RAGGIUNGIMENTO" element
     */
    void setDATARAGGIUNGIMENTO(java.util.Calendar dataraggiungimento);
    
    /**
     * Sets (as xml) the "DATA_RAGGIUNGIMENTO" element
     */
    void xsetDATARAGGIUNGIMENTO(org.apache.xmlbeans.XmlDateTime dataraggiungimento);
    
    /**
     * Gets the "IMPORTO_SAL" element
     */
    double getIMPORTOSAL();
    
    /**
     * Gets (as xml) the "IMPORTO_SAL" element
     */
    org.apache.xmlbeans.XmlDouble xgetIMPORTOSAL();
    
    /**
     * Sets the "IMPORTO_SAL" element
     */
    void setIMPORTOSAL(double importosal);
    
    /**
     * Sets (as xml) the "IMPORTO_SAL" element
     */
    void xsetIMPORTOSAL(org.apache.xmlbeans.XmlDouble importosal);
    
    /**
     * Gets the "DENOMINAZIONE" element
     */
    java.lang.String getDENOMINAZIONE();
    
    /**
     * Gets (as xml) the "DENOMINAZIONE" element
     */
    org.apache.xmlbeans.XmlString xgetDENOMINAZIONE();
    
    /**
     * True if has "DENOMINAZIONE" element
     */
    boolean isSetDENOMINAZIONE();
    
    /**
     * Sets the "DENOMINAZIONE" element
     */
    void setDENOMINAZIONE(java.lang.String denominazione);
    
    /**
     * Sets (as xml) the "DENOMINAZIONE" element
     */
    void xsetDENOMINAZIONE(org.apache.xmlbeans.XmlString denominazione);
    
    /**
     * Unsets the "DENOMINAZIONE" element
     */
    void unsetDENOMINAZIONE();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
