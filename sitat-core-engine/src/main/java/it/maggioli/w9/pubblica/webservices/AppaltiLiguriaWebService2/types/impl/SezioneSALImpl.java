/*
 * XML Type:  SezioneSAL
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneSAL(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneSALImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL
{
    
    public SezioneSALImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATISAL$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_SAL");
    
    
    /**
     * Gets the "DATI_SAL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento getDATISAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento)get_store().find_element_user(DATISAL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_SAL" element
     */
    public boolean isSetDATISAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISAL$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_SAL" element
     */
    public void setDATISAL(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento datisal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento)get_store().find_element_user(DATISAL$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento)get_store().add_element_user(DATISAL$0);
            }
            target.set(datisal);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_SAL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento addNewDATISAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiAvanzamento)get_store().add_element_user(DATISAL$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_SAL" element
     */
    public void unsetDATISAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISAL$0, 0);
        }
    }
}
