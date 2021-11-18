/*
 * XML Type:  StipulaInizio
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML StipulaInizio(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class StipulaInizioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.StipulaInizio
{
    
    public StipulaInizioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATASTIPULA$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_STIPULA");
    private static final javax.xml.namespace.QName FLAGRISERVA$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RISERVA");
    private static final javax.xml.namespace.QName DATAVERBINIZIO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_INIZIO");
    private static final javax.xml.namespace.QName DATATERMINE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_TERMINE");
    
    
    /**
     * Gets the "DATA_STIPULA" element
     */
    public java.util.Calendar getDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASTIPULA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_STIPULA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATASTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASTIPULA$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_STIPULA" element
     */
    public void setDATASTIPULA(java.util.Calendar datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASTIPULA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATASTIPULA$0);
            }
            target.setCalendarValue(datastipula);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_STIPULA" element
     */
    public void xsetDATASTIPULA(org.apache.xmlbeans.XmlDateTime datastipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASTIPULA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATASTIPULA$0);
            }
            target.set(datastipula);
        }
    }
    
    /**
     * Gets the "FLAG_RISERVA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRISERVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVA$2, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RISERVA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRISERVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVA$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RISERVA" element
     */
    public void setFLAGRISERVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagriserva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRISERVA$2);
            }
            target.setEnumValue(flagriserva);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RISERVA" element
     */
    public void xsetFLAGRISERVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagriserva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVA$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGRISERVA$2);
            }
            target.set(flagriserva);
        }
    }
    
    /**
     * Gets the "DATA_VERB_INIZIO" element
     */
    public java.util.Calendar getDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBINIZIO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_INIZIO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBINIZIO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_INIZIO" element
     */
    public boolean isSetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAVERBINIZIO$4) != 0;
        }
    }
    
    /**
     * Sets the "DATA_VERB_INIZIO" element
     */
    public void setDATAVERBINIZIO(java.util.Calendar dataverbinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBINIZIO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBINIZIO$4);
            }
            target.setCalendarValue(dataverbinizio);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_INIZIO" element
     */
    public void xsetDATAVERBINIZIO(org.apache.xmlbeans.XmlDateTime dataverbinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBINIZIO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBINIZIO$4);
            }
            target.set(dataverbinizio);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_INIZIO" element
     */
    public void unsetDATAVERBINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAVERBINIZIO$4, 0);
        }
    }
    
    /**
     * Gets the "DATA_TERMINE" element
     */
    public java.util.Calendar getDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_TERMINE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_TERMINE" element
     */
    public boolean isSetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATATERMINE$6) != 0;
        }
    }
    
    /**
     * Sets the "DATA_TERMINE" element
     */
    public void setDATATERMINE(java.util.Calendar datatermine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATATERMINE$6);
            }
            target.setCalendarValue(datatermine);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_TERMINE" element
     */
    public void xsetDATATERMINE(org.apache.xmlbeans.XmlDateTime datatermine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATATERMINE$6);
            }
            target.set(datatermine);
        }
    }
    
    /**
     * Unsets the "DATA_TERMINE" element
     */
    public void unsetDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATATERMINE$6, 0);
        }
    }
}
