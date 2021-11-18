/*
 * An XML document type.
 * Localname: CheckVerifyFault
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * A document containing one CheckVerifyFault(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl) element.
 *
 * This is a complex type.
 */
public class CheckVerifyFaultDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckVerifyFaultDocument
{
    
    public CheckVerifyFaultDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CHECKVERIFYFAULT$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl", "CheckVerifyFault");
    
    
    /**
     * Gets the "CheckVerifyFault" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean getCheckVerifyFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().find_element_user(CHECKVERIFYFAULT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "CheckVerifyFault" element
     */
    public boolean isNilCheckVerifyFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().find_element_user(CHECKVERIFYFAULT$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * Sets the "CheckVerifyFault" element
     */
    public void setCheckVerifyFault(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean checkVerifyFault)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().find_element_user(CHECKVERIFYFAULT$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().add_element_user(CHECKVERIFYFAULT$0);
            }
            target.set(checkVerifyFault);
        }
    }
    
    /**
     * Appends and returns a new empty "CheckVerifyFault" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean addNewCheckVerifyFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().add_element_user(CHECKVERIFYFAULT$0);
            return target;
        }
    }
    
    /**
     * Nils the "CheckVerifyFault" element
     */
    public void setNilCheckVerifyFault()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().find_element_user(CHECKVERIFYFAULT$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean)get_store().add_element_user(CHECKVERIFYFAULT$0);
            }
            target.setNil();
        }
    }
}
