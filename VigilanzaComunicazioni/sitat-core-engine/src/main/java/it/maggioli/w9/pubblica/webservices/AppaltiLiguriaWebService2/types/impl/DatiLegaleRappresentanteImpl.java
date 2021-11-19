/*
 * XML Type:  DatiLegaleRappresentante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiLegaleRappresentante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiLegaleRappresentanteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante
{
    
    public DatiLegaleRappresentanteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COGNOME$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COGNOME");
    private static final javax.xml.namespace.QName NOME$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NOME");
    private static final javax.xml.namespace.QName CODICEFISCALE$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_FISCALE");
    
    
    /**
     * Gets the "COGNOME" element
     */
    public java.lang.String getCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COGNOME" element
     */
    public org.apache.xmlbeans.XmlString xgetCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "COGNOME" element
     */
    public void setCOGNOME(java.lang.String cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COGNOME$0);
            }
            target.setStringValue(cognome);
        }
    }
    
    /**
     * Sets (as xml) the "COGNOME" element
     */
    public void xsetCOGNOME(org.apache.xmlbeans.XmlString cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COGNOME$0);
            }
            target.set(cognome);
        }
    }
    
    /**
     * Gets the "NOME" element
     */
    public java.lang.String getNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOME" element
     */
    public org.apache.xmlbeans.XmlString xgetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NOME" element
     */
    public void setNOME(java.lang.String nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NOME$2);
            }
            target.setStringValue(nome);
        }
    }
    
    /**
     * Sets (as xml) the "NOME" element
     */
    public void xsetNOME(org.apache.xmlbeans.XmlString nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NOME$2);
            }
            target.set(nome);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE" element
     */
    public java.lang.String getCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_FISCALE" element
     */
    public boolean isSetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICEFISCALE$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEFISCALE$4);
            }
            target.set(codicefiscale);
        }
    }
    
    /**
     * Unsets the "CODICE_FISCALE" element
     */
    public void unsetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICEFISCALE$4, 0);
        }
    }
}
