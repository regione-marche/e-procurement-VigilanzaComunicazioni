/*
 * XML Type:  DatiEnte
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiEnte(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiEnteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte
{
    
    public DatiEnteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEFISCALE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_FISCALE");
    private static final javax.xml.namespace.QName IDUSERENTE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_USER_ENTE");
    
    
    /**
     * Gets the "CODICE_FISCALE" element
     */
    public java.lang.String getCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE" element
     */
    public org.apache.xmlbeans.XmlString xgetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE" element
     */
    public void setCODICEFISCALE(java.lang.String codicefiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$0);
            }
            target.setStringValue(codicefiscale);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE" element
     */
    public void xsetCODICEFISCALE(org.apache.xmlbeans.XmlString codicefiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEFISCALE$0);
            }
            target.set(codicefiscale);
        }
    }
    
    /**
     * Gets the "ID_USER_ENTE" element
     */
    public java.lang.String getIDUSERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDUSERENTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_USER_ENTE" element
     */
    public org.apache.xmlbeans.XmlString xgetIDUSERENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDUSERENTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_USER_ENTE" element
     */
    public void setIDUSERENTE(java.lang.String iduserente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDUSERENTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDUSERENTE$2);
            }
            target.setStringValue(iduserente);
        }
    }
    
    /**
     * Sets (as xml) the "ID_USER_ENTE" element
     */
    public void xsetIDUSERENTE(org.apache.xmlbeans.XmlString iduserente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDUSERENTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDUSERENTE$2);
            }
            target.set(iduserente);
        }
    }
}
