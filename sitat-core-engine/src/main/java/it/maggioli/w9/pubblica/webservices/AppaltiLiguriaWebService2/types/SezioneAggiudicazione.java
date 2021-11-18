/*
 * XML Type:  SezioneAggiudicazione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types;


/**
 * An XML SezioneAggiudicazione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public interface SezioneAggiudicazione extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SezioneAggiudicazione.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s9B544F4075EA19B46E708810C26CE1A4").resolveHandle("sezioneaggiudicazione5201type");
    
    /**
     * Gets the "DATI_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione getDATIAGGIUDICAZIONE();
    
    /**
     * Sets the "DATI_AGGIUDICAZIONE" element
     */
    void setDATIAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione datiaggiudicazione);
    
    /**
     * Appends and returns a new empty "DATI_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione addNewDATIAGGIUDICAZIONE();
    
    /**
     * Gets the "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione getPUBBLICAZIONEAGGIUDICAZIONE();
    
    /**
     * Sets the "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    void setPUBBLICAZIONEAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione pubblicazioneaggiudicazione);
    
    /**
     * Appends and returns a new empty "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione addNewPUBBLICAZIONEAGGIUDICAZIONE();
    
    /**
     * Gets the "LISTA_FINANZIAMENTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti getLISTAFINANZIAMENTI();
    
    /**
     * Sets the "LISTA_FINANZIAMENTI" element
     */
    void setLISTAFINANZIAMENTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti listafinanziamenti);
    
    /**
     * Appends and returns a new empty "LISTA_FINANZIAMENTI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti addNewLISTAFINANZIAMENTI();
    
    /**
     * Gets the "LISTA_CATEGORIE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie getLISTACATEGORIE();
    
    /**
     * Sets the "LISTA_CATEGORIE" element
     */
    void setLISTACATEGORIE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie listacategorie);
    
    /**
     * Appends and returns a new empty "LISTA_CATEGORIE" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie addNewLISTACATEGORIE();
    
    /**
     * Gets the "LISTA_AGGIUDICATARI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari getLISTAAGGIUDICATARI();
    
    /**
     * Sets the "LISTA_AGGIUDICATARI" element
     */
    void setLISTAAGGIUDICATARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari listaaggiudicatari);
    
    /**
     * Appends and returns a new empty "LISTA_AGGIUDICATARI" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari addNewLISTAAGGIUDICATARI();
    
    /**
     * Gets the "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi getLISTADATISOGGETTIESTESIAGG();
    
    /**
     * Sets the "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    void setLISTADATISOGGETTIESTESIAGG(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi listadatisoggettiestesiagg);
    
    /**
     * Appends and returns a new empty "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi addNewLISTADATISOGGETTIESTESIAGG();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione newInstance() {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
