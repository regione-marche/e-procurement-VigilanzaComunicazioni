/*
 * XML Type:  DatiInizio
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiInizio(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiInizioImpl extends it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl.StipulaInizioImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiInizio
{
    
    public DatiInizioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAESECUTIVITA$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ESECUTIVITA");
    private static final javax.xml.namespace.QName IMPORTOCAUZIONE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_CAUZIONE");
    private static final javax.xml.namespace.QName DATAINIPROGESEC$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INI_PROG_ESEC");
    private static final javax.xml.namespace.QName DATAAPPPROGESEC$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_APP_PROG_ESEC");
    private static final javax.xml.namespace.QName FLAGFRAZIONATA$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_FRAZIONATA");
    private static final javax.xml.namespace.QName DATAVERBPRIMACONSEGNA$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_PRIMA_CONSEGNA");
    private static final javax.xml.namespace.QName DATAVERBALEDEF$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERBALE_DEF");
    
    
    /**
     * Gets the "DATA_ESECUTIVITA" element
     */
    public java.util.Calendar getDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESECUTIVITA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ESECUTIVITA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAESECUTIVITA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAESECUTIVITA$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_ESECUTIVITA" element
     */
    public void setDATAESECUTIVITA(java.util.Calendar dataesecutivita)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAESECUTIVITA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAESECUTIVITA$0);
            }
            target.setCalendarValue(dataesecutivita);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ESECUTIVITA" element
     */
    public void xsetDATAESECUTIVITA(org.apache.xmlbeans.XmlDateTime dataesecutivita)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAESECUTIVITA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAESECUTIVITA$0);
            }
            target.set(dataesecutivita);
        }
    }
    
    /**
     * Gets the "IMPORTO_CAUZIONE" element
     */
    public double getIMPORTOCAUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCAUZIONE$2, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_CAUZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOCAUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCAUZIONE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_CAUZIONE" element
     */
    public void setIMPORTOCAUZIONE(double importocauzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCAUZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOCAUZIONE$2);
            }
            target.setDoubleValue(importocauzione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_CAUZIONE" element
     */
    public void xsetIMPORTOCAUZIONE(org.apache.xmlbeans.XmlDouble importocauzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCAUZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOCAUZIONE$2);
            }
            target.set(importocauzione);
        }
    }
    
    /**
     * Gets the "DATA_INI_PROG_ESEC" element
     */
    public java.util.Calendar getDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIPROGESEC$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INI_PROG_ESEC" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIPROGESEC$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INI_PROG_ESEC" element
     */
    public boolean isSetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIPROGESEC$4) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INI_PROG_ESEC" element
     */
    public void setDATAINIPROGESEC(java.util.Calendar datainiprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIPROGESEC$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIPROGESEC$4);
            }
            target.setCalendarValue(datainiprogesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INI_PROG_ESEC" element
     */
    public void xsetDATAINIPROGESEC(org.apache.xmlbeans.XmlDateTime datainiprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIPROGESEC$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIPROGESEC$4);
            }
            target.set(datainiprogesec);
        }
    }
    
    /**
     * Unsets the "DATA_INI_PROG_ESEC" element
     */
    public void unsetDATAINIPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIPROGESEC$4, 0);
        }
    }
    
    /**
     * Gets the "DATA_APP_PROG_ESEC" element
     */
    public java.util.Calendar getDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAPPPROGESEC$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_APP_PROG_ESEC" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAPPPROGESEC$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_APP_PROG_ESEC" element
     */
    public boolean isSetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAAPPPROGESEC$6) != 0;
        }
    }
    
    /**
     * Sets the "DATA_APP_PROG_ESEC" element
     */
    public void setDATAAPPPROGESEC(java.util.Calendar dataappprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAPPPROGESEC$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAAPPPROGESEC$6);
            }
            target.setCalendarValue(dataappprogesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_APP_PROG_ESEC" element
     */
    public void xsetDATAAPPPROGESEC(org.apache.xmlbeans.XmlDateTime dataappprogesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAPPPROGESEC$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAAPPPROGESEC$6);
            }
            target.set(dataappprogesec);
        }
    }
    
    /**
     * Unsets the "DATA_APP_PROG_ESEC" element
     */
    public void unsetDATAAPPPROGESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAAPPPROGESEC$6, 0);
        }
    }
    
    /**
     * Gets the "FLAG_FRAZIONATA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGFRAZIONATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGFRAZIONATA$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_FRAZIONATA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGFRAZIONATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGFRAZIONATA$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_FRAZIONATA" element
     */
    public void setFLAGFRAZIONATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagfrazionata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGFRAZIONATA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGFRAZIONATA$8);
            }
            target.setEnumValue(flagfrazionata);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_FRAZIONATA" element
     */
    public void xsetFLAGFRAZIONATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagfrazionata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGFRAZIONATA$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGFRAZIONATA$8);
            }
            target.set(flagfrazionata);
        }
    }
    
    /**
     * Gets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public java.util.Calendar getDATAVERBPRIMACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBPRIMACONSEGNA$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBPRIMACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBPRIMACONSEGNA$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public boolean isSetDATAVERBPRIMACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAVERBPRIMACONSEGNA$10) != 0;
        }
    }
    
    /**
     * Sets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public void setDATAVERBPRIMACONSEGNA(java.util.Calendar dataverbprimaconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBPRIMACONSEGNA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBPRIMACONSEGNA$10);
            }
            target.setCalendarValue(dataverbprimaconsegna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public void xsetDATAVERBPRIMACONSEGNA(org.apache.xmlbeans.XmlDateTime dataverbprimaconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBPRIMACONSEGNA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBPRIMACONSEGNA$10);
            }
            target.set(dataverbprimaconsegna);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_PRIMA_CONSEGNA" element
     */
    public void unsetDATAVERBPRIMACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAVERBPRIMACONSEGNA$10, 0);
        }
    }
    
    /**
     * Gets the "DATA_VERBALE_DEF" element
     */
    public java.util.Calendar getDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBALEDEF$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERBALE_DEF" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBALEDEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBALEDEF$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERBALE_DEF" element
     */
    public void setDATAVERBALEDEF(java.util.Calendar dataverbaledef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBALEDEF$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBALEDEF$12);
            }
            target.setCalendarValue(dataverbaledef);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERBALE_DEF" element
     */
    public void xsetDATAVERBALEDEF(org.apache.xmlbeans.XmlDateTime dataverbaledef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBALEDEF$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBALEDEF$12);
            }
            target.set(dataverbaledef);
        }
    }
}
