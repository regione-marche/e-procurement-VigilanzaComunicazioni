/*
 * XML Type:  MotiviVariante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML MotiviVariante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class MotiviVarianteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.MotiviVariante
{
    
    public MotiviVarianteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDMOTIVOVAR$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_MOTIVO_VAR");
    
    
    /**
     * Gets the "ID_MOTIVO_VAR" element
     */
    public java.lang.String getIDMOTIVOVAR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOVAR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_VAR" element
     */
    public org.apache.xmlbeans.XmlString xgetIDMOTIVOVAR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOVAR$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_VAR" element
     */
    public void setIDMOTIVOVAR(java.lang.String idmotivovar)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOVAR$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDMOTIVOVAR$0);
            }
            target.setStringValue(idmotivovar);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_VAR" element
     */
    public void xsetIDMOTIVOVAR(org.apache.xmlbeans.XmlString idmotivovar)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOVAR$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDMOTIVOVAR$0);
            }
            target.set(idmotivovar);
        }
    }
}
