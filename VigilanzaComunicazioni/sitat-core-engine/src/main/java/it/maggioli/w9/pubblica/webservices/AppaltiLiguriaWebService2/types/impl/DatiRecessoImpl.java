/*
 * XML Type:  DatiRecesso
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiRecesso(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiRecessoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiRecesso
{
    
    public DatiRecessoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATATERMINE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_TERMINE");
    private static final javax.xml.namespace.QName DATACONSEGNA$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_CONSEGNA");
    private static final javax.xml.namespace.QName FLAGTIPOCOMUNICAZIONE$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_TIPO_COMUNICAZIONE");
    private static final javax.xml.namespace.QName DURATASOSP$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DURATA_SOSP");
    private static final javax.xml.namespace.QName MOTIVOSOSP$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MOTIVO_SOSP");
    private static final javax.xml.namespace.QName DATAISTRECESSO$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_IST_RECESSO");
    private static final javax.xml.namespace.QName FLAGACCOLTA$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_ACCOLTA");
    private static final javax.xml.namespace.QName FLAGTARDIVA$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_TARDIVA");
    private static final javax.xml.namespace.QName FLAGRIPRESA$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RIPRESA");
    private static final javax.xml.namespace.QName FLAGRISERVA$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RISERVA");
    private static final javax.xml.namespace.QName IMPORTOSPESE$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_SPESE");
    private static final javax.xml.namespace.QName IMPORTOONERI$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_ONERI");
    private static final javax.xml.namespace.QName GGRITARDO$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "GG_RITARDO");
    
    
    /**
     * Gets the "DATA_TERMINE" element
     */
    public java.util.Calendar getDATATERMINE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINE$0, 0);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINE$0, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATATERMINE$0);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATATERMINE$0);
            }
            target.set(datatermine);
        }
    }
    
    /**
     * Gets the "DATA_CONSEGNA" element
     */
    public java.util.Calendar getDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACONSEGNA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CONSEGNA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACONSEGNA$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CONSEGNA" element
     */
    public boolean isSetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATACONSEGNA$2) != 0;
        }
    }
    
    /**
     * Sets the "DATA_CONSEGNA" element
     */
    public void setDATACONSEGNA(java.util.Calendar dataconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACONSEGNA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATACONSEGNA$2);
            }
            target.setCalendarValue(dataconsegna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CONSEGNA" element
     */
    public void xsetDATACONSEGNA(org.apache.xmlbeans.XmlDateTime dataconsegna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACONSEGNA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATACONSEGNA$2);
            }
            target.set(dataconsegna);
        }
    }
    
    /**
     * Unsets the "DATA_CONSEGNA" element
     */
    public void unsetDATACONSEGNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATACONSEGNA$2, 0);
        }
    }
    
    /**
     * Gets the "FLAG_TIPO_COMUNICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType.Enum getFLAGTIPOCOMUNICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGTIPOCOMUNICAZIONE$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_TIPO_COMUNICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType xgetFLAGTIPOCOMUNICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType)get_store().find_element_user(FLAGTIPOCOMUNICAZIONE$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_TIPO_COMUNICAZIONE" element
     */
    public void setFLAGTIPOCOMUNICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType.Enum flagtipocomunicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGTIPOCOMUNICAZIONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGTIPOCOMUNICAZIONE$4);
            }
            target.setEnumValue(flagtipocomunicazione);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_TIPO_COMUNICAZIONE" element
     */
    public void xsetFLAGTIPOCOMUNICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType flagtipocomunicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType)get_store().find_element_user(FLAGTIPOCOMUNICAZIONE$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagTCType)get_store().add_element_user(FLAGTIPOCOMUNICAZIONE$4);
            }
            target.set(flagtipocomunicazione);
        }
    }
    
    /**
     * Gets the "DURATA_SOSP" element
     */
    public long getDURATASOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATASOSP$6, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "DURATA_SOSP" element
     */
    public org.apache.xmlbeans.XmlLong xgetDURATASOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(DURATASOSP$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DURATA_SOSP" element
     */
    public void setDURATASOSP(long duratasosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATASOSP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DURATASOSP$6);
            }
            target.setLongValue(duratasosp);
        }
    }
    
    /**
     * Sets (as xml) the "DURATA_SOSP" element
     */
    public void xsetDURATASOSP(org.apache.xmlbeans.XmlLong duratasosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(DURATASOSP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(DURATASOSP$6);
            }
            target.set(duratasosp);
        }
    }
    
    /**
     * Gets the "MOTIVO_SOSP" element
     */
    public java.lang.String getMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOSOSP$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO_SOSP" element
     */
    public org.apache.xmlbeans.XmlString xgetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOSOSP$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVO_SOSP" element
     */
    public boolean isSetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOTIVOSOSP$8) != 0;
        }
    }
    
    /**
     * Sets the "MOTIVO_SOSP" element
     */
    public void setMOTIVOSOSP(java.lang.String motivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOSOSP$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOTIVOSOSP$8);
            }
            target.setStringValue(motivososp);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO_SOSP" element
     */
    public void xsetMOTIVOSOSP(org.apache.xmlbeans.XmlString motivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOSOSP$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOTIVOSOSP$8);
            }
            target.set(motivososp);
        }
    }
    
    /**
     * Unsets the "MOTIVO_SOSP" element
     */
    public void unsetMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOTIVOSOSP$8, 0);
        }
    }
    
    /**
     * Gets the "DATA_IST_RECESSO" element
     */
    public java.util.Calendar getDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAISTRECESSO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_IST_RECESSO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAISTRECESSO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAISTRECESSO$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_IST_RECESSO" element
     */
    public void setDATAISTRECESSO(java.util.Calendar dataistrecesso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAISTRECESSO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAISTRECESSO$10);
            }
            target.setCalendarValue(dataistrecesso);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_IST_RECESSO" element
     */
    public void xsetDATAISTRECESSO(org.apache.xmlbeans.XmlDateTime dataistrecesso)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAISTRECESSO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAISTRECESSO$10);
            }
            target.set(dataistrecesso);
        }
    }
    
    /**
     * Gets the "FLAG_ACCOLTA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGACCOLTA$12, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ACCOLTA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGACCOLTA$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ACCOLTA" element
     */
    public boolean isSetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGACCOLTA$12) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_ACCOLTA" element
     */
    public void setFLAGACCOLTA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagaccolta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGACCOLTA$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGACCOLTA$12);
            }
            target.setEnumValue(flagaccolta);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ACCOLTA" element
     */
    public void xsetFLAGACCOLTA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagaccolta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGACCOLTA$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGACCOLTA$12);
            }
            target.set(flagaccolta);
        }
    }
    
    /**
     * Unsets the "FLAG_ACCOLTA" element
     */
    public void unsetFLAGACCOLTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGACCOLTA$12, 0);
        }
    }
    
    /**
     * Gets the "FLAG_TARDIVA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGTARDIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGTARDIVA$14, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_TARDIVA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGTARDIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGTARDIVA$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_TARDIVA" element
     */
    public void setFLAGTARDIVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagtardiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGTARDIVA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGTARDIVA$14);
            }
            target.setEnumValue(flagtardiva);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_TARDIVA" element
     */
    public void xsetFLAGTARDIVA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagtardiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGTARDIVA$14, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGTARDIVA$14);
            }
            target.set(flagtardiva);
        }
    }
    
    /**
     * Gets the "FLAG_RIPRESA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRIPRESA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRIPRESA$16, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RIPRESA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRIPRESA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRIPRESA$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RIPRESA" element
     */
    public void setFLAGRIPRESA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagripresa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRIPRESA$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRIPRESA$16);
            }
            target.setEnumValue(flagripresa);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RIPRESA" element
     */
    public void xsetFLAGRIPRESA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagripresa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRIPRESA$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGRIPRESA$16);
            }
            target.set(flagripresa);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVA$18, 0);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVA$18, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVA$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRISERVA$18);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVA$18, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGRISERVA$18);
            }
            target.set(flagriserva);
        }
    }
    
    /**
     * Gets the "IMPORTO_SPESE" element
     */
    public double getIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSPESE$20, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SPESE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSPESE$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_SPESE" element
     */
    public boolean isSetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOSPESE$20) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_SPESE" element
     */
    public void setIMPORTOSPESE(double importospese)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSPESE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSPESE$20);
            }
            target.setDoubleValue(importospese);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SPESE" element
     */
    public void xsetIMPORTOSPESE(org.apache.xmlbeans.XmlDouble importospese)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSPESE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOSPESE$20);
            }
            target.set(importospese);
        }
    }
    
    /**
     * Unsets the "IMPORTO_SPESE" element
     */
    public void unsetIMPORTOSPESE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOSPESE$20, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_ONERI" element
     */
    public double getIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOONERI$22, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ONERI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOONERI$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ONERI" element
     */
    public boolean isSetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOONERI$22) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_ONERI" element
     */
    public void setIMPORTOONERI(double importooneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOONERI$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOONERI$22);
            }
            target.setDoubleValue(importooneri);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ONERI" element
     */
    public void xsetIMPORTOONERI(org.apache.xmlbeans.XmlDouble importooneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOONERI$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOONERI$22);
            }
            target.set(importooneri);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ONERI" element
     */
    public void unsetIMPORTOONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOONERI$22, 0);
        }
    }
    
    /**
     * Gets the "GG_RITARDO" element
     */
    public long getGGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GGRITARDO$24, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "GG_RITARDO" element
     */
    public org.apache.xmlbeans.XmlLong xgetGGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GGRITARDO$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "GG_RITARDO" element
     */
    public boolean isSetGGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GGRITARDO$24) != 0;
        }
    }
    
    /**
     * Sets the "GG_RITARDO" element
     */
    public void setGGRITARDO(long ggritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GGRITARDO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GGRITARDO$24);
            }
            target.setLongValue(ggritardo);
        }
    }
    
    /**
     * Sets (as xml) the "GG_RITARDO" element
     */
    public void xsetGGRITARDO(org.apache.xmlbeans.XmlLong ggritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GGRITARDO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(GGRITARDO$24);
            }
            target.set(ggritardo);
        }
    }
    
    /**
     * Unsets the "GG_RITARDO" element
     */
    public void unsetGGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GGRITARDO$24, 0);
        }
    }
}
