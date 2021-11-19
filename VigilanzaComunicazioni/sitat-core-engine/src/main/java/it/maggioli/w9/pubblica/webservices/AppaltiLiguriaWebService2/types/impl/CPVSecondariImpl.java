/*
 * XML Type:  CPVSecondari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML CPVSecondari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class CPVSecondariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari
{
    
    public CPVSecondariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CPVCOMP$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CPVCOMP");
    
    
    /**
     * Gets the "CPVCOMP" element
     */
    public java.lang.String getCPVCOMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPVCOMP$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CPVCOMP" element
     */
    public org.apache.xmlbeans.XmlString xgetCPVCOMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPVCOMP$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CPVCOMP" element
     */
    public void setCPVCOMP(java.lang.String cpvcomp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CPVCOMP$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CPVCOMP$0);
            }
            target.setStringValue(cpvcomp);
        }
    }
    
    /**
     * Sets (as xml) the "CPVCOMP" element
     */
    public void xsetCPVCOMP(org.apache.xmlbeans.XmlString cpvcomp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CPVCOMP$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CPVCOMP$0);
            }
            target.set(cpvcomp);
        }
    }
}
