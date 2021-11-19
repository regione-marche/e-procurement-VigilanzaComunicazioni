/*
 * XML Type:  SezioneVarianti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneVarianti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneVariantiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti
{
    
    public SezioneVariantiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIVARIANTI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_VARIANTI");
    
    
    /**
     * Gets the "DATI_VARIANTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante getDATIVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante)get_store().find_element_user(DATIVARIANTI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_VARIANTI" element
     */
    public boolean isSetDATIVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIVARIANTI$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_VARIANTI" element
     */
    public void setDATIVARIANTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante dativarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante)get_store().find_element_user(DATIVARIANTI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante)get_store().add_element_user(DATIVARIANTI$0);
            }
            target.set(dativarianti);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_VARIANTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante addNewDATIVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante)get_store().add_element_user(DATIVARIANTI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_VARIANTI" element
     */
    public void unsetDATIVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIVARIANTI$0, 0);
        }
    }
}
