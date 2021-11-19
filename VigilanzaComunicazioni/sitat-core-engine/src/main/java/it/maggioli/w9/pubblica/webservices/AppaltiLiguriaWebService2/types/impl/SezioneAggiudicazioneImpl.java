/*
 * XML Type:  SezioneAggiudicazione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneAggiudicazione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneAggiudicazioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione
{
    
    public SezioneAggiudicazioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIAGGIUDICAZIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName PUBBLICAZIONEAGGIUDICAZIONE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PUBBLICAZIONE_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName LISTAFINANZIAMENTI$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_FINANZIAMENTI");
    private static final javax.xml.namespace.QName LISTACATEGORIE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_CATEGORIE");
    private static final javax.xml.namespace.QName LISTAAGGIUDICATARI$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_AGGIUDICATARI");
    private static final javax.xml.namespace.QName LISTADATISOGGETTIESTESIAGG$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_DATI_SOGGETTI_ESTESI_AGG");
    
    
    /**
     * Gets the "DATI_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione getDATIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione)get_store().find_element_user(DATIAGGIUDICAZIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_AGGIUDICAZIONE" element
     */
    public void setDATIAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione datiaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione)get_store().find_element_user(DATIAGGIUDICAZIONE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione)get_store().add_element_user(DATIAGGIUDICAZIONE$0);
            }
            target.set(datiaggiudicazione);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione addNewDATIAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicazione)get_store().add_element_user(DATIAGGIUDICAZIONE$0);
            return target;
        }
    }
    
    /**
     * Gets the "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione getPUBBLICAZIONEAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().find_element_user(PUBBLICAZIONEAGGIUDICAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    public void setPUBBLICAZIONEAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione pubblicazioneaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().find_element_user(PUBBLICAZIONEAGGIUDICAZIONE$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().add_element_user(PUBBLICAZIONEAGGIUDICAZIONE$2);
            }
            target.set(pubblicazioneaggiudicazione);
        }
    }
    
    /**
     * Appends and returns a new empty "PUBBLICAZIONE_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione addNewPUBBLICAZIONEAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione)get_store().add_element_user(PUBBLICAZIONEAGGIUDICAZIONE$2);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_FINANZIAMENTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti getLISTAFINANZIAMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti)get_store().find_element_user(LISTAFINANZIAMENTI$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_FINANZIAMENTI" element
     */
    public void setLISTAFINANZIAMENTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti listafinanziamenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti)get_store().find_element_user(LISTAFINANZIAMENTI$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti)get_store().add_element_user(LISTAFINANZIAMENTI$4);
            }
            target.set(listafinanziamenti);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_FINANZIAMENTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti addNewLISTAFINANZIAMENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti)get_store().add_element_user(LISTAFINANZIAMENTI$4);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_CATEGORIE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie getLISTACATEGORIE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie)get_store().find_element_user(LISTACATEGORIE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_CATEGORIE" element
     */
    public void setLISTACATEGORIE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie listacategorie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie)get_store().find_element_user(LISTACATEGORIE$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie)get_store().add_element_user(LISTACATEGORIE$6);
            }
            target.set(listacategorie);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_CATEGORIE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie addNewLISTACATEGORIE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCategorie)get_store().add_element_user(LISTACATEGORIE$6);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_AGGIUDICATARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari getLISTAAGGIUDICATARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().find_element_user(LISTAAGGIUDICATARI$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_AGGIUDICATARI" element
     */
    public void setLISTAAGGIUDICATARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari listaaggiudicatari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().find_element_user(LISTAAGGIUDICATARI$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().add_element_user(LISTAAGGIUDICATARI$8);
            }
            target.set(listaaggiudicatari);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_AGGIUDICATARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari addNewLISTAAGGIUDICATARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaAggiudicatari)get_store().add_element_user(LISTAAGGIUDICATARI$8);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi getLISTADATISOGGETTIESTESIAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESIAGG$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    public void setLISTADATISOGGETTIESTESIAGG(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi listadatisoggettiestesiagg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESIAGG$10, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESIAGG$10);
            }
            target.set(listadatisoggettiestesiagg);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_DATI_SOGGETTI_ESTESI_AGG" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi addNewLISTADATISOGGETTIESTESIAGG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESIAGG$10);
            return target;
        }
    }
}
