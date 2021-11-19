/*
 * XML Type:  DatiConclusione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiConclusione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiConclusione extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiConclusione.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("daticonclusioneff99type");
    
    /**
     * Gets the "FLAG_INTERR_ANTICIPATA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGINTERRANTICIPATA();
    
    /**
     * Gets (as xml) the "FLAG_INTERR_ANTICIPATA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGINTERRANTICIPATA();
    
    /**
     * Sets the "FLAG_INTERR_ANTICIPATA" element
     */
    void setFLAGINTERRANTICIPATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flaginterranticipata);
    
    /**
     * Sets (as xml) the "FLAG_INTERR_ANTICIPATA" element
     */
    void xsetFLAGINTERRANTICIPATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flaginterranticipata);
    
    /**
     * Gets the "ID_MOTIVO_INTERR" element
     */
    java.lang.String getIDMOTIVOINTERR();
    
    /**
     * Gets (as xml) the "ID_MOTIVO_INTERR" element
     */
    org.apache.xmlbeans.XmlString xgetIDMOTIVOINTERR();
    
    /**
     * True if has "ID_MOTIVO_INTERR" element
     */
    boolean isSetIDMOTIVOINTERR();
    
    /**
     * Sets the "ID_MOTIVO_INTERR" element
     */
    void setIDMOTIVOINTERR(java.lang.String idmotivointerr);
    
    /**
     * Sets (as xml) the "ID_MOTIVO_INTERR" element
     */
    void xsetIDMOTIVOINTERR(org.apache.xmlbeans.XmlString idmotivointerr);
    
    /**
     * Unsets the "ID_MOTIVO_INTERR" element
     */
    void unsetIDMOTIVOINTERR();
    
    /**
     * Gets the "ID_MOTIVO_RISOL" element
     */
    java.lang.String getIDMOTIVORISOL();
    
    /**
     * Gets (as xml) the "ID_MOTIVO_RISOL" element
     */
    org.apache.xmlbeans.XmlString xgetIDMOTIVORISOL();
    
    /**
     * True if has "ID_MOTIVO_RISOL" element
     */
    boolean isSetIDMOTIVORISOL();
    
    /**
     * Sets the "ID_MOTIVO_RISOL" element
     */
    void setIDMOTIVORISOL(java.lang.String idmotivorisol);
    
    /**
     * Sets (as xml) the "ID_MOTIVO_RISOL" element
     */
    void xsetIDMOTIVORISOL(org.apache.xmlbeans.XmlString idmotivorisol);
    
    /**
     * Unsets the "ID_MOTIVO_RISOL" element
     */
    void unsetIDMOTIVORISOL();
    
    /**
     * Gets the "DATA_RISOLUZIONE" element
     */
    java.util.Calendar getDATARISOLUZIONE();
    
    /**
     * Gets (as xml) the "DATA_RISOLUZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATARISOLUZIONE();
    
    /**
     * True if has "DATA_RISOLUZIONE" element
     */
    boolean isSetDATARISOLUZIONE();
    
    /**
     * Sets the "DATA_RISOLUZIONE" element
     */
    void setDATARISOLUZIONE(java.util.Calendar datarisoluzione);
    
    /**
     * Sets (as xml) the "DATA_RISOLUZIONE" element
     */
    void xsetDATARISOLUZIONE(org.apache.xmlbeans.XmlDateTime datarisoluzione);
    
    /**
     * Unsets the "DATA_RISOLUZIONE" element
     */
    void unsetDATARISOLUZIONE();
    
    /**
     * Gets the "FLAG_ONERI" element
     */
    java.lang.String getFLAGONERI();
    
    /**
     * Gets (as xml) the "FLAG_ONERI" element
     */
    org.apache.xmlbeans.XmlString xgetFLAGONERI();
    
    /**
     * True if has "FLAG_ONERI" element
     */
    boolean isSetFLAGONERI();
    
    /**
     * Sets the "FLAG_ONERI" element
     */
    void setFLAGONERI(java.lang.String flagoneri);
    
    /**
     * Sets (as xml) the "FLAG_ONERI" element
     */
    void xsetFLAGONERI(org.apache.xmlbeans.XmlString flagoneri);
    
    /**
     * Unsets the "FLAG_ONERI" element
     */
    void unsetFLAGONERI();
    
    /**
     * Gets the "ONERI_RISOLUZIONE" element
     */
    double getONERIRISOLUZIONE();
    
    /**
     * Gets (as xml) the "ONERI_RISOLUZIONE" element
     */
    org.apache.xmlbeans.XmlDouble xgetONERIRISOLUZIONE();
    
    /**
     * True if has "ONERI_RISOLUZIONE" element
     */
    boolean isSetONERIRISOLUZIONE();
    
    /**
     * Sets the "ONERI_RISOLUZIONE" element
     */
    void setONERIRISOLUZIONE(double oneririsoluzione);
    
    /**
     * Sets (as xml) the "ONERI_RISOLUZIONE" element
     */
    void xsetONERIRISOLUZIONE(org.apache.xmlbeans.XmlDouble oneririsoluzione);
    
    /**
     * Unsets the "ONERI_RISOLUZIONE" element
     */
    void unsetONERIRISOLUZIONE();
    
    /**
     * Gets the "FLAG_POLIZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGPOLIZZA();
    
    /**
     * Gets (as xml) the "FLAG_POLIZZA" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGPOLIZZA();
    
    /**
     * Sets the "FLAG_POLIZZA" element
     */
    void setFLAGPOLIZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagpolizza);
    
    /**
     * Sets (as xml) the "FLAG_POLIZZA" element
     */
    void xsetFLAGPOLIZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagpolizza);
    
    /**
     * Gets the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    java.util.Calendar getDATAVERBCONSEGNAAVVIO();
    
    /**
     * Gets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAVERBCONSEGNAAVVIO();
    
    /**
     * Sets the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    void setDATAVERBCONSEGNAAVVIO(java.util.Calendar dataverbconsegnaavvio);
    
    /**
     * Sets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    void xsetDATAVERBCONSEGNAAVVIO(org.apache.xmlbeans.XmlDateTime dataverbconsegnaavvio);
    
    /**
     * Gets the "DATA_TERMINE_CONTRATTUALE" element
     */
    java.util.Calendar getDATATERMINECONTRATTUALE();
    
    /**
     * Gets (as xml) the "DATA_TERMINE_CONTRATTUALE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATATERMINECONTRATTUALE();
    
    /**
     * Sets the "DATA_TERMINE_CONTRATTUALE" element
     */
    void setDATATERMINECONTRATTUALE(java.util.Calendar dataterminecontrattuale);
    
    /**
     * Sets (as xml) the "DATA_TERMINE_CONTRATTUALE" element
     */
    void xsetDATATERMINECONTRATTUALE(org.apache.xmlbeans.XmlDateTime dataterminecontrattuale);
    
    /**
     * Gets the "NUM_INFORTUNI" element
     */
    long getNUMINFORTUNI();
    
    /**
     * Gets (as xml) the "NUM_INFORTUNI" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMINFORTUNI();
    
    /**
     * Sets the "NUM_INFORTUNI" element
     */
    void setNUMINFORTUNI(long numinfortuni);
    
    /**
     * Sets (as xml) the "NUM_INFORTUNI" element
     */
    void xsetNUMINFORTUNI(org.apache.xmlbeans.XmlLong numinfortuni);
    
    /**
     * Gets the "DATA_ULTIMAZIONE" element
     */
    java.util.Calendar getDATAULTIMAZIONE();
    
    /**
     * Gets (as xml) the "DATA_ULTIMAZIONE" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDATAULTIMAZIONE();
    
    /**
     * Sets the "DATA_ULTIMAZIONE" element
     */
    void setDATAULTIMAZIONE(java.util.Calendar dataultimazione);
    
    /**
     * Sets (as xml) the "DATA_ULTIMAZIONE" element
     */
    void xsetDATAULTIMAZIONE(org.apache.xmlbeans.XmlDateTime dataultimazione);
    
    /**
     * Gets the "NUM_INF_PERM" element
     */
    long getNUMINFPERM();
    
    /**
     * Gets (as xml) the "NUM_INF_PERM" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMINFPERM();
    
    /**
     * Sets the "NUM_INF_PERM" element
     */
    void setNUMINFPERM(long numinfperm);
    
    /**
     * Sets (as xml) the "NUM_INF_PERM" element
     */
    void xsetNUMINFPERM(org.apache.xmlbeans.XmlLong numinfperm);
    
    /**
     * Gets the "NUM_INF_MORT" element
     */
    long getNUMINFMORT();
    
    /**
     * Gets (as xml) the "NUM_INF_MORT" element
     */
    org.apache.xmlbeans.XmlLong xgetNUMINFMORT();
    
    /**
     * Sets the "NUM_INF_MORT" element
     */
    void setNUMINFMORT(long numinfmort);
    
    /**
     * Sets (as xml) the "NUM_INF_MORT" element
     */
    void xsetNUMINFMORT(org.apache.xmlbeans.XmlLong numinfmort);
    
    /**
     * Gets the "ORE_LAVORATE" element
     */
    double getORELAVORATE();
    
    /**
     * Gets (as xml) the "ORE_LAVORATE" element
     */
    org.apache.xmlbeans.XmlDouble xgetORELAVORATE();
    
    /**
     * True if has "ORE_LAVORATE" element
     */
    boolean isSetORELAVORATE();
    
    /**
     * Sets the "ORE_LAVORATE" element
     */
    void setORELAVORATE(double orelavorate);
    
    /**
     * Sets (as xml) the "ORE_LAVORATE" element
     */
    void xsetORELAVORATE(org.apache.xmlbeans.XmlDouble orelavorate);
    
    /**
     * Unsets the "ORE_LAVORATE" element
     */
    void unsetORELAVORATE();
    
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
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
