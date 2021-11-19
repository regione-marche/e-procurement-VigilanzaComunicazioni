/*
 * XML Type:  Condizioni
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Condizioni(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class CondizioniImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni
{
    
    public CondizioniImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCONDIZIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_CONDIZIONE");
    
    
    /**
     * Gets the "ID_CONDIZIONE" element
     */
    public java.lang.String getIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCONDIZIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_CONDIZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetIDCONDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDCONDIZIONE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_CONDIZIONE" element
     */
    public void setIDCONDIZIONE(java.lang.String idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDCONDIZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDCONDIZIONE$0);
            }
            target.setStringValue(idcondizione);
        }
    }
    
    /**
     * Sets (as xml) the "ID_CONDIZIONE" element
     */
    public void xsetIDCONDIZIONE(org.apache.xmlbeans.XmlString idcondizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDCONDIZIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDCONDIZIONE$0);
            }
            target.set(idcondizione);
        }
    }
}
