/*
 * XML Type:  DatiAccordo
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiAccordo(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiAccordoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAccordo
{
    
    public DatiAccordoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAINIZIOACC$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_ACC");
    private static final javax.xml.namespace.QName DATAFINEACC$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_FINE_ACC");
    private static final javax.xml.namespace.QName DATAACCORDO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ACCORDO");
    private static final javax.xml.namespace.QName ONERIDERIVANTI$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_DERIVANTI");
    private static final javax.xml.namespace.QName NUMRISERVE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_RISERVE");
    
    
    /**
     * Gets the "DATA_INIZIO_ACC" element
     */
    public java.util.Calendar getDATAINIZIOACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOACC$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_ACC" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOACC$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_ACC" element
     */
    public void setDATAINIZIOACC(java.util.Calendar datainizioacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOACC$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOACC$0);
            }
            target.setCalendarValue(datainizioacc);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_ACC" element
     */
    public void xsetDATAINIZIOACC(org.apache.xmlbeans.XmlDateTime datainizioacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOACC$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOACC$0);
            }
            target.set(datainizioacc);
        }
    }
    
    /**
     * Gets the "DATA_FINE_ACC" element
     */
    public java.util.Calendar getDATAFINEACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEACC$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_FINE_ACC" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAFINEACC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEACC$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_FINE_ACC" element
     */
    public void setDATAFINEACC(java.util.Calendar datafineacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEACC$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAFINEACC$2);
            }
            target.setCalendarValue(datafineacc);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_FINE_ACC" element
     */
    public void xsetDATAFINEACC(org.apache.xmlbeans.XmlDateTime datafineacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEACC$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAFINEACC$2);
            }
            target.set(datafineacc);
        }
    }
    
    /**
     * Gets the "DATA_ACCORDO" element
     */
    public java.util.Calendar getDATAACCORDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAACCORDO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ACCORDO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAACCORDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAACCORDO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_ACCORDO" element
     */
    public void setDATAACCORDO(java.util.Calendar dataaccordo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAACCORDO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAACCORDO$4);
            }
            target.setCalendarValue(dataaccordo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ACCORDO" element
     */
    public void xsetDATAACCORDO(org.apache.xmlbeans.XmlDateTime dataaccordo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAACCORDO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAACCORDO$4);
            }
            target.set(dataaccordo);
        }
    }
    
    /**
     * Gets the "ONERI_DERIVANTI" element
     */
    public double getONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIDERIVANTI$6, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_DERIVANTI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERIDERIVANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIDERIVANTI$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ONERI_DERIVANTI" element
     */
    public void setONERIDERIVANTI(double oneriderivanti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIDERIVANTI$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERIDERIVANTI$6);
            }
            target.setDoubleValue(oneriderivanti);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_DERIVANTI" element
     */
    public void xsetONERIDERIVANTI(org.apache.xmlbeans.XmlDouble oneriderivanti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIDERIVANTI$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERIDERIVANTI$6);
            }
            target.set(oneriderivanti);
        }
    }
    
    /**
     * Gets the "NUM_RISERVE" element
     */
    public long getNUMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMRISERVE$8, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_RISERVE" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMRISERVE$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_RISERVE" element
     */
    public void setNUMRISERVE(long numriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMRISERVE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMRISERVE$8);
            }
            target.setLongValue(numriserve);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_RISERVE" element
     */
    public void xsetNUMRISERVE(org.apache.xmlbeans.XmlLong numriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMRISERVE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMRISERVE$8);
            }
            target.set(numriserve);
        }
    }
}
