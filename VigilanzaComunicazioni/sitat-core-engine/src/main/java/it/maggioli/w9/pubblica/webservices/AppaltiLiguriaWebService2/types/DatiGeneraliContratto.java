/*
 * XML Type:  DatiGeneraliContratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML DatiGeneraliContratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface DatiGeneraliContratto extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DatiGeneraliContratto.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("datigeneralicontrattobff2type");
    
    /**
     * Gets the "CONTROLLO_INVIO" element
     */
    java.lang.String getCONTROLLOINVIO();
    
    /**
     * Gets (as xml) the "CONTROLLO_INVIO" element
     */
    org.apache.xmlbeans.XmlString xgetCONTROLLOINVIO();
    
    /**
     * Sets the "CONTROLLO_INVIO" element
     */
    void setCONTROLLOINVIO(java.lang.String controlloinvio);
    
    /**
     * Sets (as xml) the "CONTROLLO_INVIO" element
     */
    void xsetCONTROLLOINVIO(org.apache.xmlbeans.XmlString controlloinvio);
    
    /**
     * Gets the "DATI_COMUNI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni getDATICOMUNI();
    
    /**
     * Sets the "DATI_COMUNI" element
     */
    void setDATICOMUNI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni daticomuni);
    
    /**
     * Appends and returns a new empty "DATI_COMUNI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni addNewDATICOMUNI();
    
    /**
     * Gets the "DATI_COMUNI_ESTESI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi getDATICOMUNIESTESI();
    
    /**
     * Sets the "DATI_COMUNI_ESTESI" element
     */
    void setDATICOMUNIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi daticomuniestesi);
    
    /**
     * Appends and returns a new empty "DATI_COMUNI_ESTESI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi addNewDATICOMUNIESTESI();
    
    /**
     * Gets the "LISTA_OFFERENTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti getLISTAOFFERENTI();
    
    /**
     * True if has "LISTA_OFFERENTI" element
     */
    boolean isSetLISTAOFFERENTI();
    
    /**
     * Sets the "LISTA_OFFERENTI" element
     */
    void setLISTAOFFERENTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti listaofferenti);
    
    /**
     * Appends and returns a new empty "LISTA_OFFERENTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti addNewLISTAOFFERENTI();
    
    /**
     * Unsets the "LISTA_OFFERENTI" element
     */
    void unsetLISTAOFFERENTI();
    
    /**
     * Gets the "SEZIONE_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione getSEZIONEAGGIUDICAZIONE();
    
    /**
     * Sets the "SEZIONE_AGGIUDICAZIONE" element
     */
    void setSEZIONEAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione sezioneaggiudicazione);
    
    /**
     * Appends and returns a new empty "SEZIONE_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione addNewSEZIONEAGGIUDICAZIONE();
    
    /**
     * Gets the "SEZIONE_INIZIO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio getSEZIONEINIZIO();
    
    /**
     * True if has "SEZIONE_INIZIO" element
     */
    boolean isSetSEZIONEINIZIO();
    
    /**
     * Sets the "SEZIONE_INIZIO" element
     */
    void setSEZIONEINIZIO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio sezioneinizio);
    
    /**
     * Appends and returns a new empty "SEZIONE_INIZIO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio addNewSEZIONEINIZIO();
    
    /**
     * Unsets the "SEZIONE_INIZIO" element
     */
    void unsetSEZIONEINIZIO();
    
    /**
     * Gets the "SEZIONE_SAL" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL getSEZIONESAL();
    
    /**
     * True if has "SEZIONE_SAL" element
     */
    boolean isSetSEZIONESAL();
    
    /**
     * Sets the "SEZIONE_SAL" element
     */
    void setSEZIONESAL(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL sezionesal);
    
    /**
     * Appends and returns a new empty "SEZIONE_SAL" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL addNewSEZIONESAL();
    
    /**
     * Unsets the "SEZIONE_SAL" element
     */
    void unsetSEZIONESAL();
    
    /**
     * Gets the "SEZIONE_VARIANTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti getSEZIONEVARIANTI();
    
    /**
     * True if has "SEZIONE_VARIANTI" element
     */
    boolean isSetSEZIONEVARIANTI();
    
    /**
     * Sets the "SEZIONE_VARIANTI" element
     */
    void setSEZIONEVARIANTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti sezionevarianti);
    
    /**
     * Appends and returns a new empty "SEZIONE_VARIANTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti addNewSEZIONEVARIANTI();
    
    /**
     * Unsets the "SEZIONE_VARIANTI" element
     */
    void unsetSEZIONEVARIANTI();
    
    /**
     * Gets the "SEZIONE_SOSPENSIONI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni getSEZIONESOSPENSIONI();
    
    /**
     * True if has "SEZIONE_SOSPENSIONI" element
     */
    boolean isSetSEZIONESOSPENSIONI();
    
    /**
     * Sets the "SEZIONE_SOSPENSIONI" element
     */
    void setSEZIONESOSPENSIONI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni sezionesospensioni);
    
    /**
     * Appends and returns a new empty "SEZIONE_SOSPENSIONI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni addNewSEZIONESOSPENSIONI();
    
    /**
     * Unsets the "SEZIONE_SOSPENSIONI" element
     */
    void unsetSEZIONESOSPENSIONI();
    
    /**
     * Gets the "SEZIONE_CONCLUSIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione getSEZIONECONCLUSIONE();
    
    /**
     * True if has "SEZIONE_CONCLUSIONE" element
     */
    boolean isSetSEZIONECONCLUSIONE();
    
    /**
     * Sets the "SEZIONE_CONCLUSIONE" element
     */
    void setSEZIONECONCLUSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione sezioneconclusione);
    
    /**
     * Appends and returns a new empty "SEZIONE_CONCLUSIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione addNewSEZIONECONCLUSIONE();
    
    /**
     * Unsets the "SEZIONE_CONCLUSIONE" element
     */
    void unsetSEZIONECONCLUSIONE();
    
    /**
     * Gets the "SEZIONE_COLLAUDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo getSEZIONECOLLAUDO();
    
    /**
     * True if has "SEZIONE_COLLAUDO" element
     */
    boolean isSetSEZIONECOLLAUDO();
    
    /**
     * Sets the "SEZIONE_COLLAUDO" element
     */
    void setSEZIONECOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo sezionecollaudo);
    
    /**
     * Appends and returns a new empty "SEZIONE_COLLAUDO" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo addNewSEZIONECOLLAUDO();
    
    /**
     * Unsets the "SEZIONE_COLLAUDO" element
     */
    void unsetSEZIONECOLLAUDO();
    
    /**
     * Gets the "SEZIONE_SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti getSEZIONESUBAPPALTI();
    
    /**
     * True if has "SEZIONE_SUBAPPALTI" element
     */
    boolean isSetSEZIONESUBAPPALTI();
    
    /**
     * Sets the "SEZIONE_SUBAPPALTI" element
     */
    void setSEZIONESUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti sezionesubappalti);
    
    /**
     * Appends and returns a new empty "SEZIONE_SUBAPPALTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti addNewSEZIONESUBAPPALTI();
    
    /**
     * Unsets the "SEZIONE_SUBAPPALTI" element
     */
    void unsetSEZIONESUBAPPALTI();
    
    /**
     * Gets the "SEZIONE_RITARDI_RECESSI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi getSEZIONERITARDIRECESSI();
    
    /**
     * True if has "SEZIONE_RITARDI_RECESSI" element
     */
    boolean isSetSEZIONERITARDIRECESSI();
    
    /**
     * Sets the "SEZIONE_RITARDI_RECESSI" element
     */
    void setSEZIONERITARDIRECESSI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi sezioneritardirecessi);
    
    /**
     * Appends and returns a new empty "SEZIONE_RITARDI_RECESSI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi addNewSEZIONERITARDIRECESSI();
    
    /**
     * Unsets the "SEZIONE_RITARDI_RECESSI" element
     */
    void unsetSEZIONERITARDIRECESSI();
    
    /**
     * Gets the "SEZIONE_ACCORDI_BONARI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari getSEZIONEACCORDIBONARI();
    
    /**
     * True if has "SEZIONE_ACCORDI_BONARI" element
     */
    boolean isSetSEZIONEACCORDIBONARI();
    
    /**
     * Sets the "SEZIONE_ACCORDI_BONARI" element
     */
    void setSEZIONEACCORDIBONARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari sezioneaccordibonari);
    
    /**
     * Appends and returns a new empty "SEZIONE_ACCORDI_BONARI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari addNewSEZIONEACCORDIBONARI();
    
    /**
     * Unsets the "SEZIONE_ACCORDI_BONARI" element
     */
    void unsetSEZIONEACCORDIBONARI();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
