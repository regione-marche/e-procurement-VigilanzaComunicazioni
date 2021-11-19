/*
 * An XML document type.
 * Localname: invioContrattoResponse
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * A document containing one invioContrattoResponse(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl) element.
 *
 * This is a complex type.
 */
public class InvioContrattoResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponseDocument
{
    
    public InvioContrattoResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INVIOCONTRATTORESPONSE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl", "invioContrattoResponse");
    
    
    /**
     * Gets the "invioContrattoResponse" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse getInvioContrattoResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse)get_store().find_element_user(INVIOCONTRATTORESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "invioContrattoResponse" element
     */
    public void setInvioContrattoResponse(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse invioContrattoResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse)get_store().find_element_user(INVIOCONTRATTORESPONSE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse)get_store().add_element_user(INVIOCONTRATTORESPONSE$0);
            }
            target.set(invioContrattoResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "invioContrattoResponse" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse addNewInvioContrattoResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.InvioContrattoResponse)get_store().add_element_user(INVIOCONTRATTORESPONSE$0);
            return target;
        }
    }
}
