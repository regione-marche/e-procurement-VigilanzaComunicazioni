/*
 * XML Type:  SezioneInizio
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneInizio(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneInizioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio
{
    
    public SezioneInizioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIINIZIO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_INIZIO");
    private static final javax.xml.namespace.QName PUBBLICAZIONEINIZIO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PUBBLICAZIONE_INIZIO");
    private static final javax.xml.namespace.QName LISTADATISOGGETTIESTESIINIZIO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_DATI_SOGGETTI_ESTESI_INIZIO");
    
    
    /**
     * Gets the "DATI_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio getDATIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio)get_store().find_element_user(DATIINIZIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_INIZIO" element
     */
    public void setDATIINIZIO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio datiinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio)get_store().find_element_user(DATIINIZIO$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio)get_store().add_element_user(DATIINIZIO$0);
            }
            target.set(datiinizio);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio addNewDATIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio)get_store().add_element_user(DATIINIZIO$0);
            return target;
        }
    }
    
    /**
     * Gets the "PUBBLICAZIONE_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione getPUBBLICAZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().find_element_user(PUBBLICAZIONEINIZIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PUBBLICAZIONE_INIZIO" element
     */
    public void setPUBBLICAZIONEINIZIO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione pubblicazioneinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().find_element_user(PUBBLICAZIONEINIZIO$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().add_element_user(PUBBLICAZIONEINIZIO$2);
            }
            target.set(pubblicazioneinizio);
        }
    }
    
    /**
     * Appends and returns a new empty "PUBBLICAZIONE_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione addNewPUBBLICAZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().add_element_user(PUBBLICAZIONEINIZIO$2);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_DATI_SOGGETTI_ESTESI_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi getLISTADATISOGGETTIESTESIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESIINIZIO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "LISTA_DATI_SOGGETTI_ESTESI_INIZIO" element
     */
    public boolean isSetLISTADATISOGGETTIESTESIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LISTADATISOGGETTIESTESIINIZIO$4) != 0;
        }
    }
    
    /**
     * Sets the "LISTA_DATI_SOGGETTI_ESTESI_INIZIO" element
     */
    public void setLISTADATISOGGETTIESTESIINIZIO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi listadatisoggettiestesiinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESIINIZIO$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESIINIZIO$4);
            }
            target.set(listadatisoggettiestesiinizio);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_DATI_SOGGETTI_ESTESI_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi addNewLISTADATISOGGETTIESTESIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESIINIZIO$4);
            return target;
        }
    }
    
    /**
     * Unsets the "LISTA_DATI_SOGGETTI_ESTESI_INIZIO" element
     */
    public void unsetLISTADATISOGGETTIESTESIINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LISTADATISOGGETTIESTESIINIZIO$4, 0);
        }
    }
}
