/*
 * XML Type:  DatiAvanzamento
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiAvanzamento(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiAvanzamentoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAvanzamento
{
    
    public DatiAvanzamentoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SUBAPPALTI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SUBAPPALTI");
    private static final javax.xml.namespace.QName DATACERTIFICATO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_CERTIFICATO");
    private static final javax.xml.namespace.QName IMPORTOCERTIFICATO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_CERTIFICATO");
    private static final javax.xml.namespace.QName FLAGRITARDO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_RITARDO");
    private static final javax.xml.namespace.QName NUMGIORNISCOST$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_GIORNI_SCOST");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_GIORNI_PROROGA");
    private static final javax.xml.namespace.QName FLAGPAGAMENTO$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_PAGAMENTO");
    private static final javax.xml.namespace.QName DATAANTICIPAZIONE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ANTICIPAZIONE");
    private static final javax.xml.namespace.QName IMPORTOANTICIPAZIONE$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_ANTICIPAZIONE");
    private static final javax.xml.namespace.QName DATARAGGIUNGIMENTO$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_RAGGIUNGIMENTO");
    private static final javax.xml.namespace.QName IMPORTOSAL$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_SAL");
    private static final javax.xml.namespace.QName DENOMINAZIONE$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DENOMINAZIONE");
    
    
    /**
     * Gets the "SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBAPPALTI$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SUBAPPALTI$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "SUBAPPALTI" element
     */
    public void setSUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum subappalti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBAPPALTI$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUBAPPALTI$0);
            }
            target.setEnumValue(subappalti);
        }
    }
    
    /**
     * Sets (as xml) the "SUBAPPALTI" element
     */
    public void xsetSUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType subappalti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SUBAPPALTI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SUBAPPALTI$0);
            }
            target.set(subappalti);
        }
    }
    
    /**
     * Gets the "DATA_CERTIFICATO" element
     */
    public java.util.Calendar getDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACERTIFICATO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CERTIFICATO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACERTIFICATO$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CERTIFICATO" element
     */
    public boolean isSetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATACERTIFICATO$2) != 0;
        }
    }
    
    /**
     * Sets the "DATA_CERTIFICATO" element
     */
    public void setDATACERTIFICATO(java.util.Calendar datacertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACERTIFICATO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATACERTIFICATO$2);
            }
            target.setCalendarValue(datacertificato);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CERTIFICATO" element
     */
    public void xsetDATACERTIFICATO(org.apache.xmlbeans.XmlDateTime datacertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACERTIFICATO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATACERTIFICATO$2);
            }
            target.set(datacertificato);
        }
    }
    
    /**
     * Unsets the "DATA_CERTIFICATO" element
     */
    public void unsetDATACERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATACERTIFICATO$2, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_CERTIFICATO" element
     */
    public double getIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCERTIFICATO$4, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_CERTIFICATO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOCERTIFICATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCERTIFICATO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_CERTIFICATO" element
     */
    public void setIMPORTOCERTIFICATO(double importocertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCERTIFICATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOCERTIFICATO$4);
            }
            target.setDoubleValue(importocertificato);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_CERTIFICATO" element
     */
    public void xsetIMPORTOCERTIFICATO(org.apache.xmlbeans.XmlDouble importocertificato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCERTIFICATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOCERTIFICATO$4);
            }
            target.set(importocertificato);
        }
    }
    
    /**
     * Gets the "FLAG_RITARDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType.Enum getFLAGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRITARDO$6, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_RITARDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType xgetFLAGRITARDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType)get_store().find_element_user(FLAGRITARDO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_RITARDO" element
     */
    public void setFLAGRITARDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType.Enum flagritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGRITARDO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGRITARDO$6);
            }
            target.setEnumValue(flagritardo);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_RITARDO" element
     */
    public void xsetFLAGRITARDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType flagritardo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType)get_store().find_element_user(FLAGRITARDO$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagRitardoType)get_store().add_element_user(FLAGRITARDO$6);
            }
            target.set(flagritardo);
        }
    }
    
    /**
     * Gets the "NUM_GIORNI_SCOST" element
     */
    public long getNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNISCOST$8, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_GIORNI_SCOST" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNISCOST$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_GIORNI_SCOST" element
     */
    public boolean isSetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMGIORNISCOST$8) != 0;
        }
    }
    
    /**
     * Sets the "NUM_GIORNI_SCOST" element
     */
    public void setNUMGIORNISCOST(long numgiorniscost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNISCOST$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMGIORNISCOST$8);
            }
            target.setLongValue(numgiorniscost);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_GIORNI_SCOST" element
     */
    public void xsetNUMGIORNISCOST(org.apache.xmlbeans.XmlLong numgiorniscost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNISCOST$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMGIORNISCOST$8);
            }
            target.set(numgiorniscost);
        }
    }
    
    /**
     * Unsets the "NUM_GIORNI_SCOST" element
     */
    public void unsetNUMGIORNISCOST()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMGIORNISCOST$8, 0);
        }
    }
    
    /**
     * Gets the "NUM_GIORNI_PROROGA" element
     */
    public long getNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNIPROROGA$10, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_GIORNI_PROROGA" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNIPROROGA$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "NUM_GIORNI_PROROGA" element
     */
    public boolean isSetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUMGIORNIPROROGA$10) != 0;
        }
    }
    
    /**
     * Sets the "NUM_GIORNI_PROROGA" element
     */
    public void setNUMGIORNIPROROGA(long numgiorniproroga)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNIPROROGA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMGIORNIPROROGA$10);
            }
            target.setLongValue(numgiorniproroga);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_GIORNI_PROROGA" element
     */
    public void xsetNUMGIORNIPROROGA(org.apache.xmlbeans.XmlLong numgiorniproroga)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNIPROROGA$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMGIORNIPROROGA$10);
            }
            target.set(numgiorniproroga);
        }
    }
    
    /**
     * Unsets the "NUM_GIORNI_PROROGA" element
     */
    public void unsetNUMGIORNIPROROGA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUMGIORNIPROROGA$10, 0);
        }
    }
    
    /**
     * Gets the "FLAG_PAGAMENTO" element
     */
    public java.lang.String getFLAGPAGAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPAGAMENTO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_PAGAMENTO" element
     */
    public org.apache.xmlbeans.XmlString xgetFLAGPAGAMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGPAGAMENTO$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_PAGAMENTO" element
     */
    public void setFLAGPAGAMENTO(java.lang.String flagpagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPAGAMENTO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGPAGAMENTO$12);
            }
            target.setStringValue(flagpagamento);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_PAGAMENTO" element
     */
    public void xsetFLAGPAGAMENTO(org.apache.xmlbeans.XmlString flagpagamento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGPAGAMENTO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLAGPAGAMENTO$12);
            }
            target.set(flagpagamento);
        }
    }
    
    /**
     * Gets the "DATA_ANTICIPAZIONE" element
     */
    public java.util.Calendar getDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAANTICIPAZIONE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ANTICIPAZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAANTICIPAZIONE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ANTICIPAZIONE" element
     */
    public boolean isSetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAANTICIPAZIONE$14) != 0;
        }
    }
    
    /**
     * Sets the "DATA_ANTICIPAZIONE" element
     */
    public void setDATAANTICIPAZIONE(java.util.Calendar dataanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAANTICIPAZIONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAANTICIPAZIONE$14);
            }
            target.setCalendarValue(dataanticipazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ANTICIPAZIONE" element
     */
    public void xsetDATAANTICIPAZIONE(org.apache.xmlbeans.XmlDateTime dataanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAANTICIPAZIONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAANTICIPAZIONE$14);
            }
            target.set(dataanticipazione);
        }
    }
    
    /**
     * Unsets the "DATA_ANTICIPAZIONE" element
     */
    public void unsetDATAANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAANTICIPAZIONE$14, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_ANTICIPAZIONE" element
     */
    public double getIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOANTICIPAZIONE$16, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_ANTICIPAZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOANTICIPAZIONE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_ANTICIPAZIONE" element
     */
    public boolean isSetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOANTICIPAZIONE$16) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_ANTICIPAZIONE" element
     */
    public void setIMPORTOANTICIPAZIONE(double importoanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOANTICIPAZIONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOANTICIPAZIONE$16);
            }
            target.setDoubleValue(importoanticipazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_ANTICIPAZIONE" element
     */
    public void xsetIMPORTOANTICIPAZIONE(org.apache.xmlbeans.XmlDouble importoanticipazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOANTICIPAZIONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOANTICIPAZIONE$16);
            }
            target.set(importoanticipazione);
        }
    }
    
    /**
     * Unsets the "IMPORTO_ANTICIPAZIONE" element
     */
    public void unsetIMPORTOANTICIPAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOANTICIPAZIONE$16, 0);
        }
    }
    
    /**
     * Gets the "DATA_RAGGIUNGIMENTO" element
     */
    public java.util.Calendar getDATARAGGIUNGIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATARAGGIUNGIMENTO$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_RAGGIUNGIMENTO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATARAGGIUNGIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATARAGGIUNGIMENTO$18, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_RAGGIUNGIMENTO" element
     */
    public void setDATARAGGIUNGIMENTO(java.util.Calendar dataraggiungimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATARAGGIUNGIMENTO$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATARAGGIUNGIMENTO$18);
            }
            target.setCalendarValue(dataraggiungimento);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_RAGGIUNGIMENTO" element
     */
    public void xsetDATARAGGIUNGIMENTO(org.apache.xmlbeans.XmlDateTime dataraggiungimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATARAGGIUNGIMENTO$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATARAGGIUNGIMENTO$18);
            }
            target.set(dataraggiungimento);
        }
    }
    
    /**
     * Gets the "IMPORTO_SAL" element
     */
    public double getIMPORTOSAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSAL$20, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SAL" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOSAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSAL$20, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_SAL" element
     */
    public void setIMPORTOSAL(double importosal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSAL$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSAL$20);
            }
            target.setDoubleValue(importosal);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SAL" element
     */
    public void xsetIMPORTOSAL(org.apache.xmlbeans.XmlDouble importosal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSAL$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOSAL$20);
            }
            target.set(importosal);
        }
    }
    
    /**
     * Gets the "DENOMINAZIONE" element
     */
    public java.lang.String getDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DENOMINAZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "DENOMINAZIONE" element
     */
    public boolean isSetDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENOMINAZIONE$22) != 0;
        }
    }
    
    /**
     * Sets the "DENOMINAZIONE" element
     */
    public void setDENOMINAZIONE(java.lang.String denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONE$22);
            }
            target.setStringValue(denominazione);
        }
    }
    
    /**
     * Sets (as xml) the "DENOMINAZIONE" element
     */
    public void xsetDENOMINAZIONE(org.apache.xmlbeans.XmlString denominazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENOMINAZIONE$22);
            }
            target.set(denominazione);
        }
    }
    
    /**
     * Unsets the "DENOMINAZIONE" element
     */
    public void unsetDENOMINAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENOMINAZIONE$22, 0);
        }
    }
}
