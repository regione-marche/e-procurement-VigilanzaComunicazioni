/*
 * XML Type:  checkFaultBean
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.impl;
/**
 * An XML checkFaultBean(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/AppaltiLiguriaWebService.wsdl).
 *
 * This is a complex type.
 */
public class CheckFaultBeanImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.CheckFaultBean
{
    
    public CheckFaultBeanImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MESSAGE$0 = 
        new javax.xml.namespace.QName("", "message");
    
    
    /**
     * Gets the "message" element
     */
    public java.lang.String getMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "message" element
     */
    public org.apache.xmlbeans.XmlString xgetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "message" element
     */
    public boolean isSetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGE$0) != 0;
        }
    }
    
    /**
     * Sets the "message" element
     */
    public void setMessage(java.lang.String message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$0);
            }
            target.setStringValue(message);
        }
    }
    
    /**
     * Sets (as xml) the "message" element
     */
    public void xsetMessage(org.apache.xmlbeans.XmlString message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$0);
            }
            target.set(message);
        }
    }
    
    /**
     * Unsets the "message" element
     */
    public void unsetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGE$0, 0);
        }
    }
}
