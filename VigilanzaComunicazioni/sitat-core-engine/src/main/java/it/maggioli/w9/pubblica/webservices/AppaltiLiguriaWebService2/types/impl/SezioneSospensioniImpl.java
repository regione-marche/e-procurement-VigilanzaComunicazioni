/*
 * XML Type:  SezioneSospensioni
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneSospensioni(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneSospensioniImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni
{
    
    public SezioneSospensioniImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATISOSPENSIONI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_SOSPENSIONI");
    
    
    /**
     * Gets the "DATI_SOSPENSIONI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione getDATISOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione)get_store().find_element_user(DATISOSPENSIONI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_SOSPENSIONI" element
     */
    public boolean isSetDATISOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATISOSPENSIONI$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_SOSPENSIONI" element
     */
    public void setDATISOSPENSIONI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione datisospensioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione)get_store().find_element_user(DATISOSPENSIONI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione)get_store().add_element_user(DATISOSPENSIONI$0);
            }
            target.set(datisospensioni);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_SOSPENSIONI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione addNewDATISOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSospensione)get_store().add_element_user(DATISOSPENSIONI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_SOSPENSIONI" element
     */
    public void unsetDATISOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATISOSPENSIONI$0, 0);
        }
    }
}
