/*
 * An XML document type.
 * Localname: contratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ContrattoDocument
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * A document containing one contratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types) element.
 *
 * This is a complex type.
 */
public class ContrattoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ContrattoDocument
{
    
    public ContrattoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTRATTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "contratto");
    
    
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
}
