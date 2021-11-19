/*
 * An XML document type.
 * Localname: invioContratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * A document containing one invioContratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl) element.
 *
 * This is a complex type.
 */
public class InvioContrattoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoDocument
{
    
    public InvioContrattoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INVIOCONTRATTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl", "invioContratto");
    
    
    /**
     * Gets the "invioContratto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto getInvioContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto)get_store().find_element_user(INVIOCONTRATTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "invioContratto" element
     */
    public void setInvioContratto(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto invioContratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto)get_store().find_element_user(INVIOCONTRATTO$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto)get_store().add_element_user(INVIOCONTRATTO$0);
            }
            target.set(invioContratto);
        }
    }
    
    /**
     * Appends and returns a new empty "invioContratto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto addNewInvioContratto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContratto)get_store().add_element_user(INVIOCONTRATTO$0);
            return target;
        }
    }
}
