/*
 * An XML document type.
 * Localname: JAXBException
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * A document containing one JAXBException(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl) element.
 *
 * This is a complex type.
 */
public class JAXBExceptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBExceptionDocument
{
    
    public JAXBExceptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName JAXBEXCEPTION$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl", "JAXBException");
    
    
    /**
     * Gets the "JAXBException" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException getJAXBException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException)get_store().find_element_user(JAXBEXCEPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "JAXBException" element
     */
    public void setJAXBException(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException jaxbException)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException)get_store().find_element_user(JAXBEXCEPTION$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException)get_store().add_element_user(JAXBEXCEPTION$0);
            }
            target.set(jaxbException);
        }
    }
    
    /**
     * Appends and returns a new empty "JAXBException" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException addNewJAXBException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.JAXBException)get_store().add_element_user(JAXBEXCEPTION$0);
            return target;
        }
    }
}
