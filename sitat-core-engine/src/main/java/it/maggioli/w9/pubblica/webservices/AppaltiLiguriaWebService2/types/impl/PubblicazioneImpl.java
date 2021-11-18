/*
 * XML Type:  Pubblicazione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Pubblicazione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class PubblicazioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Pubblicazione
{
    
    public PubblicazioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATAGUCE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_GUCE");
    private static final javax.xml.namespace.QName DATAGURI$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_GURI");
    private static final javax.xml.namespace.QName DATABUR$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_BUR");
    private static final javax.xml.namespace.QName DATAALBO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ALBO");
    private static final javax.xml.namespace.QName QUOTIDIANINAZ$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "QUOTIDIANI_NAZ");
    private static final javax.xml.namespace.QName QUOTIDIANIREG$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "QUOTIDIANI_REG");
    private static final javax.xml.namespace.QName QUOTIDIANILOC$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "QUOTIDIANI_LOC");
    private static final javax.xml.namespace.QName PROFILOCOMMITTENTE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PROFILO_COMMITTENTE");
    private static final javax.xml.namespace.QName SITOMINISTEROINFTRASP$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SITO_MINISTERO_INF_TRASP");
    private static final javax.xml.namespace.QName SITOOSSERVATORIOCP$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SITO_OSSERVATORIO_CP");
    
    
    /**
     * Gets the "DATA_GUCE" element
     */
    public java.util.Calendar getDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGUCE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_GUCE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGUCE$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_GUCE" element
     */
    public boolean isSetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAGUCE$0) != 0;
        }
    }
    
    /**
     * Sets the "DATA_GUCE" element
     */
    public void setDATAGUCE(java.util.Calendar dataguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGUCE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAGUCE$0);
            }
            target.setCalendarValue(dataguce);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_GUCE" element
     */
    public void xsetDATAGUCE(org.apache.xmlbeans.XmlDateTime dataguce)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGUCE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAGUCE$0);
            }
            target.set(dataguce);
        }
    }
    
    /**
     * Unsets the "DATA_GUCE" element
     */
    public void unsetDATAGUCE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAGUCE$0, 0);
        }
    }
    
    /**
     * Gets the "DATA_GURI" element
     */
    public java.util.Calendar getDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGURI$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_GURI" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGURI$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_GURI" element
     */
    public boolean isSetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAGURI$2) != 0;
        }
    }
    
    /**
     * Sets the "DATA_GURI" element
     */
    public void setDATAGURI(java.util.Calendar dataguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGURI$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAGURI$2);
            }
            target.setCalendarValue(dataguri);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_GURI" element
     */
    public void xsetDATAGURI(org.apache.xmlbeans.XmlDateTime dataguri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGURI$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAGURI$2);
            }
            target.set(dataguri);
        }
    }
    
    /**
     * Unsets the "DATA_GURI" element
     */
    public void unsetDATAGURI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAGURI$2, 0);
        }
    }
    
    /**
     * Gets the "DATA_BUR" element
     */
    public java.util.Calendar getDATABUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATABUR$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_BUR" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATABUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATABUR$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_BUR" element
     */
    public boolean isSetDATABUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATABUR$4) != 0;
        }
    }
    
    /**
     * Sets the "DATA_BUR" element
     */
    public void setDATABUR(java.util.Calendar databur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATABUR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATABUR$4);
            }
            target.setCalendarValue(databur);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_BUR" element
     */
    public void xsetDATABUR(org.apache.xmlbeans.XmlDateTime databur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATABUR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATABUR$4);
            }
            target.set(databur);
        }
    }
    
    /**
     * Unsets the "DATA_BUR" element
     */
    public void unsetDATABUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATABUR$4, 0);
        }
    }
    
    /**
     * Gets the "DATA_ALBO" element
     */
    public java.util.Calendar getDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAALBO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ALBO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAALBO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ALBO" element
     */
    public boolean isSetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAALBO$6) != 0;
        }
    }
    
    /**
     * Sets the "DATA_ALBO" element
     */
    public void setDATAALBO(java.util.Calendar dataalbo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAALBO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAALBO$6);
            }
            target.setCalendarValue(dataalbo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ALBO" element
     */
    public void xsetDATAALBO(org.apache.xmlbeans.XmlDateTime dataalbo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAALBO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAALBO$6);
            }
            target.set(dataalbo);
        }
    }
    
    /**
     * Unsets the "DATA_ALBO" element
     */
    public void unsetDATAALBO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAALBO$6, 0);
        }
    }
    
    /**
     * Gets the "QUOTIDIANI_NAZ" element
     */
    public long getQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANINAZ$8, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUOTIDIANI_NAZ" element
     */
    public org.apache.xmlbeans.XmlLong xgetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANINAZ$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "QUOTIDIANI_NAZ" element
     */
    public boolean isSetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUOTIDIANINAZ$8) != 0;
        }
    }
    
    /**
     * Sets the "QUOTIDIANI_NAZ" element
     */
    public void setQUOTIDIANINAZ(long quotidianinaz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANINAZ$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUOTIDIANINAZ$8);
            }
            target.setLongValue(quotidianinaz);
        }
    }
    
    /**
     * Sets (as xml) the "QUOTIDIANI_NAZ" element
     */
    public void xsetQUOTIDIANINAZ(org.apache.xmlbeans.XmlLong quotidianinaz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANINAZ$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(QUOTIDIANINAZ$8);
            }
            target.set(quotidianinaz);
        }
    }
    
    /**
     * Unsets the "QUOTIDIANI_NAZ" element
     */
    public void unsetQUOTIDIANINAZ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUOTIDIANINAZ$8, 0);
        }
    }
    
    /**
     * Gets the "QUOTIDIANI_REG" element
     */
    public long getQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANIREG$10, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUOTIDIANI_REG" element
     */
    public org.apache.xmlbeans.XmlLong xgetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANIREG$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "QUOTIDIANI_REG" element
     */
    public boolean isSetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUOTIDIANIREG$10) != 0;
        }
    }
    
    /**
     * Sets the "QUOTIDIANI_REG" element
     */
    public void setQUOTIDIANIREG(long quotidianireg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANIREG$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUOTIDIANIREG$10);
            }
            target.setLongValue(quotidianireg);
        }
    }
    
    /**
     * Sets (as xml) the "QUOTIDIANI_REG" element
     */
    public void xsetQUOTIDIANIREG(org.apache.xmlbeans.XmlLong quotidianireg)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANIREG$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(QUOTIDIANIREG$10);
            }
            target.set(quotidianireg);
        }
    }
    
    /**
     * Unsets the "QUOTIDIANI_REG" element
     */
    public void unsetQUOTIDIANIREG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUOTIDIANIREG$10, 0);
        }
    }
    
    /**
     * Gets the "QUOTIDIANI_LOC" element
     */
    public long getQUOTIDIANILOC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANILOC$12, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUOTIDIANI_LOC" element
     */
    public org.apache.xmlbeans.XmlLong xgetQUOTIDIANILOC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANILOC$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "QUOTIDIANI_LOC" element
     */
    public boolean isSetQUOTIDIANILOC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUOTIDIANILOC$12) != 0;
        }
    }
    
    /**
     * Sets the "QUOTIDIANI_LOC" element
     */
    public void setQUOTIDIANILOC(long quotidianiloc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUOTIDIANILOC$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUOTIDIANILOC$12);
            }
            target.setLongValue(quotidianiloc);
        }
    }
    
    /**
     * Sets (as xml) the "QUOTIDIANI_LOC" element
     */
    public void xsetQUOTIDIANILOC(org.apache.xmlbeans.XmlLong quotidianiloc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUOTIDIANILOC$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(QUOTIDIANILOC$12);
            }
            target.set(quotidianiloc);
        }
    }
    
    /**
     * Unsets the "QUOTIDIANI_LOC" element
     */
    public void unsetQUOTIDIANILOC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUOTIDIANILOC$12, 0);
        }
    }
    
    /**
     * Gets the "PROFILO_COMMITTENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILOCOMMITTENTE$14, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROFILO_COMMITTENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetPROFILOCOMMITTENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PROFILOCOMMITTENTE$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "PROFILO_COMMITTENTE" element
     */
    public void setPROFILOCOMMITTENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum profilocommittente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILOCOMMITTENTE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROFILOCOMMITTENTE$14);
            }
            target.setEnumValue(profilocommittente);
        }
    }
    
    /**
     * Sets (as xml) the "PROFILO_COMMITTENTE" element
     */
    public void xsetPROFILOCOMMITTENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType profilocommittente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(PROFILOCOMMITTENTE$14, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(PROFILOCOMMITTENTE$14);
            }
            target.set(profilocommittente);
        }
    }
    
    /**
     * Gets the "SITO_MINISTERO_INF_TRASP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SITOMINISTEROINFTRASP$16, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SITO_MINISTERO_INF_TRASP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSITOMINISTEROINFTRASP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SITOMINISTEROINFTRASP$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "SITO_MINISTERO_INF_TRASP" element
     */
    public void setSITOMINISTEROINFTRASP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum sitoministeroinftrasp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SITOMINISTEROINFTRASP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SITOMINISTEROINFTRASP$16);
            }
            target.setEnumValue(sitoministeroinftrasp);
        }
    }
    
    /**
     * Sets (as xml) the "SITO_MINISTERO_INF_TRASP" element
     */
    public void xsetSITOMINISTEROINFTRASP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType sitoministeroinftrasp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SITOMINISTEROINFTRASP$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SITOMINISTEROINFTRASP$16);
            }
            target.set(sitoministeroinftrasp);
        }
    }
    
    /**
     * Gets the "SITO_OSSERVATORIO_CP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SITOOSSERVATORIOCP$18, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SITO_OSSERVATORIO_CP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSITOOSSERVATORIOCP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SITOOSSERVATORIOCP$18, 0);
            return target;
        }
    }
    
    /**
     * Sets the "SITO_OSSERVATORIO_CP" element
     */
    public void setSITOOSSERVATORIOCP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum sitoosservatoriocp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SITOOSSERVATORIOCP$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SITOOSSERVATORIOCP$18);
            }
            target.setEnumValue(sitoosservatoriocp);
        }
    }
    
    /**
     * Sets (as xml) the "SITO_OSSERVATORIO_CP" element
     */
    public void xsetSITOOSSERVATORIOCP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType sitoosservatoriocp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SITOOSSERVATORIOCP$18, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SITOOSSERVATORIOCP$18);
            }
            target.set(sitoosservatoriocp);
        }
    }
}
