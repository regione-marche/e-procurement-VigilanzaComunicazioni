/*
 * XML Type:  SezioneAccordiBonari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneAccordiBonari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneAccordiBonariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari
{
    
    public SezioneAccordiBonariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIACCORDIBONARI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_ACCORDI_BONARI");
    
    
    /**
     * Gets the "DATI_ACCORDI_BONARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari getDATIACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari)get_store().find_element_user(DATIACCORDIBONARI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_ACCORDI_BONARI" element
     */
    public boolean isSetDATIACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIACCORDIBONARI$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_ACCORDI_BONARI" element
     */
    public void setDATIACCORDIBONARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari datiaccordibonari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari)get_store().find_element_user(DATIACCORDIBONARI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari)get_store().add_element_user(DATIACCORDIBONARI$0);
            }
            target.set(datiaccordibonari);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_ACCORDI_BONARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari addNewDATIACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAccordiBonari)get_store().add_element_user(DATIACCORDIBONARI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_ACCORDI_BONARI" element
     */
    public void unsetDATIACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIACCORDIBONARI$0, 0);
        }
    }
}
