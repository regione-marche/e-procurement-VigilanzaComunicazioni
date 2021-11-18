/*
 * XML Type:  TipologiaL
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML TipologiaL(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class TipologiaLImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL
{
    
    public TipologiaLImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDAPPALTOL$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_APPALTO_L");
    
    
    /**
     * Gets the "ID_APPALTO_L" element
     */
    public java.lang.String getIDAPPALTOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTOL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_APPALTO_L" element
     */
    public org.apache.xmlbeans.XmlString xgetIDAPPALTOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTOL$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_APPALTO_L" element
     */
    public void setIDAPPALTOL(java.lang.String idappaltol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTOL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDAPPALTOL$0);
            }
            target.setStringValue(idappaltol);
        }
    }
    
    /**
     * Sets (as xml) the "ID_APPALTO_L" element
     */
    public void xsetIDAPPALTOL(org.apache.xmlbeans.XmlString idappaltol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTOL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDAPPALTOL$0);
            }
            target.set(idappaltol);
        }
    }
}
