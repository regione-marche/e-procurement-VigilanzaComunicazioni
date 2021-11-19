/*
 * XML Type:  DatiSospensione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiSospensione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiSospensioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSospensione
{
    
    public DatiSospensioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NUMPROGRESSIVO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_PROGRESSIVO");
    private static final javax.xml.namespace.QName DATAVERBSOSP$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_SOSP");
    private static final javax.xml.namespace.QName DATAVERBRIPR$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_RIPR");
    private static final javax.xml.namespace.QName IDMOTIVOSOSP$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_MOTIVO_SOSP");
    private static final javax.xml.namespace.QName FLAGSUPEROTEMPO$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SUPERO_TEMPO");
    private static final javax.xml.namespace.QName FLAGRISERVE$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RISERVE");
    private static final javax.xml.namespace.QName FLAGVERBALE$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_VERBALE");
    
    
    /**
     * Gets the "NUM_PROGRESSIVO" element
     */
    public long getNUMPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMPROGRESSIVO$0, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_PROGRESSIVO" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMPROGRESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMPROGRESSIVO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_PROGRESSIVO" element
     */
    public void setNUMPROGRESSIVO(long numprogressivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMPROGRESSIVO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMPROGRESSIVO$0);
            }
            target.setLongValue(numprogressivo);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_PROGRESSIVO" element
     */
    public void xsetNUMPROGRESSIVO(org.apache.xmlbeans.XmlLong numprogressivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMPROGRESSIVO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMPROGRESSIVO$0);
            }
            target.set(numprogressivo);
        }
    }
    
    /**
     * Gets the "DATA_VERB_SOSP" element
     */
    public java.util.Calendar getDATAVERBSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBSOSP$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_SOSP" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBSOSP$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_SOSP" element
     */
    public void setDATAVERBSOSP(java.util.Calendar dataverbsosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBSOSP$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBSOSP$2);
            }
            target.setCalendarValue(dataverbsosp);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_SOSP" element
     */
    public void xsetDATAVERBSOSP(org.apache.xmlbeans.XmlDateTime dataverbsosp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBSOSP$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBSOSP$2);
            }
            target.set(dataverbsosp);
        }
    }
    
    /**
     * Gets the "DATA_VERB_RIPR" element
     */
    public java.util.Calendar getDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBRIPR$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_RIPR" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBRIPR$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_VERB_RIPR" element
     */
    public boolean isSetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAVERBRIPR$4) != 0;
        }
    }
    
    /**
     * Sets the "DATA_VERB_RIPR" element
     */
    public void setDATAVERBRIPR(java.util.Calendar dataverbripr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBRIPR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBRIPR$4);
            }
            target.setCalendarValue(dataverbripr);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_RIPR" element
     */
    public void xsetDATAVERBRIPR(org.apache.xmlbeans.XmlDateTime dataverbripr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBRIPR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBRIPR$4);
            }
            target.set(dataverbripr);
        }
    }
    
    /**
     * Unsets the "DATA_VERB_RIPR" element
     */
    public void unsetDATAVERBRIPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAVERBRIPR$4, 0);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_SOSP" element
     */
    public java.lang.String getIDMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOSOSP$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_SOSP" element
     */
    public org.apache.xmlbeans.XmlString xgetIDMOTIVOSOSP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOSOSP$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_SOSP" element
     */
    public void setIDMOTIVOSOSP(java.lang.String idmotivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOSOSP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDMOTIVOSOSP$6);
            }
            target.setStringValue(idmotivososp);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_SOSP" element
     */
    public void xsetIDMOTIVOSOSP(org.apache.xmlbeans.XmlString idmotivososp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOSOSP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDMOTIVOSOSP$6);
            }
            target.set(idmotivososp);
        }
    }
    
    /**
     * Gets the "FLAG_SUPERO_TEMPO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGSUPEROTEMPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSUPEROTEMPO$8, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SUPERO_TEMPO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGSUPEROTEMPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSUPEROTEMPO$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SUPERO_TEMPO" element
     */
    public void setFLAGSUPEROTEMPO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagsuperotempo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSUPEROTEMPO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSUPEROTEMPO$8);
            }
            target.setEnumValue(flagsuperotempo);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SUPERO_TEMPO" element
     */
    public void xsetFLAGSUPEROTEMPO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagsuperotempo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSUPEROTEMPO$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGSUPEROTEMPO$8);
            }
            target.set(flagsuperotempo);
        }
    }
    
    /**
     * Gets the "FLAG_RISERVE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVE$10, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RISERVE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVE$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RISERVE" element
     */
    public void setFLAGRISERVE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRISERVE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRISERVE$10);
            }
            target.setEnumValue(flagriserve);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RISERVE" element
     */
    public void xsetFLAGRISERVE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGRISERVE$10, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGRISERVE$10);
            }
            target.set(flagriserve);
        }
    }
    
    /**
     * Gets the "FLAG_VERBALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGVERBALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGVERBALE$12, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_VERBALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGVERBALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGVERBALE$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_VERBALE" element
     */
    public void setFLAGVERBALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagverbale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGVERBALE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGVERBALE$12);
            }
            target.setEnumValue(flagverbale);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_VERBALE" element
     */
    public void xsetFLAGVERBALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagverbale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGVERBALE$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGVERBALE$12);
            }
            target.set(flagverbale);
        }
    }
}
