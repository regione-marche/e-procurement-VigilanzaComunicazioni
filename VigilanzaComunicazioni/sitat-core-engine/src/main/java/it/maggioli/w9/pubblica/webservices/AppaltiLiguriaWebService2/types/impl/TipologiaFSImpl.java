/*
 * XML Type:  TipologiaFS
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML TipologiaFS(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class TipologiaFSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS
{
    
    public TipologiaFSImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDAPPALTOFS$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_APPALTO_FS");
    
    
    /**
     * Gets the "ID_APPALTO_FS" element
     */
    public java.lang.String getIDAPPALTOFS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTOFS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_APPALTO_FS" element
     */
    public org.apache.xmlbeans.XmlString xgetIDAPPALTOFS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTOFS$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_APPALTO_FS" element
     */
    public void setIDAPPALTOFS(java.lang.String idappaltofs)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTOFS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDAPPALTOFS$0);
            }
            target.setStringValue(idappaltofs);
        }
    }
    
    /**
     * Sets (as xml) the "ID_APPALTO_FS" element
     */
    public void xsetIDAPPALTOFS(org.apache.xmlbeans.XmlString idappaltofs)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTOFS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDAPPALTOFS$0);
            }
            target.set(idappaltofs);
        }
    }
}
