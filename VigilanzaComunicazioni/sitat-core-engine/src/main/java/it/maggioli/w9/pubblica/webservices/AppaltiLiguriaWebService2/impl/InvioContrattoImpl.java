/*
 * XML Type:  invioContratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * An XML invioContratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl).
 *
 * This is a complex type.
 */
public class InvioContrattoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto
{
    
    public InvioContrattoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTRATTO$0 = 
        new javax.xml.namespace.QName("", "contratto");
    
    
    /**
     * Gets the "contratto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto getContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto)get_store().find_element_user(CONTRATTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "contratto" element
     */
    public boolean isSetContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTRATTO$0) != 0;
        }
    }
    
    /**
     * Sets the "contratto" element
     */
    public void setContratto(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto contratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto)get_store().find_element_user(CONTRATTO$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto)get_store().add_element_user(CONTRATTO$0);
            }
            target.set(contratto);
        }
    }
    
    /**
     * Appends and returns a new empty "contratto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto addNewContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto)get_store().add_element_user(CONTRATTO$0);
            return target;
        }
    }
    
    /**
     * Unsets the "contratto" element
     */
    public void unsetContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTRATTO$0, 0);
        }
    }
}
