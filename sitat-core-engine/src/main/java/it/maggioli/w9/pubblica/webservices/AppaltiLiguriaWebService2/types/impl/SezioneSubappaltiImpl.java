/*
 * XML Type:  SezioneSubappalti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneSubappalti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneSubappaltiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti
{
    
    public SezioneSubappaltiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATISUBAPPALTI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_SUBAPPALTI");
    
    
    /**
     * Gets the "DATI_SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto getDATISUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto)get_store().find_element_user(DATISUBAPPALTI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_SUBAPPALTI" element
     */
    public boolean isSetDATISUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISUBAPPALTI$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_SUBAPPALTI" element
     */
    public void setDATISUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto datisubappalti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto)get_store().find_element_user(DATISUBAPPALTI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto)get_store().add_element_user(DATISUBAPPALTI$0);
            }
            target.set(datisubappalti);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto addNewDATISUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto)get_store().add_element_user(DATISUBAPPALTI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_SUBAPPALTI" element
     */
    public void unsetDATISUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISUBAPPALTI$0, 0);
        }
    }
}
