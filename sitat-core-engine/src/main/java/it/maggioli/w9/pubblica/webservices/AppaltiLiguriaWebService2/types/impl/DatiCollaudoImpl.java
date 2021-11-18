/*
 * XML Type:  DatiCollaudo
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiCollaudo(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiCollaudoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo
{
    
    public DatiCollaudoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FLAGSINGOLOCOMMISSIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SINGOLO_COMMISSIONE");
    private static final javax.xml.namespace.QName DATAAPPROVAZIONE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_APPROVAZIONE");
    private static final javax.xml.namespace.QName FLAGIMPORTI$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_IMPORTI");
    private static final javax.xml.namespace.QName FLAGSICUREZZA$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SICUREZZA");
    private static final javax.xml.namespace.QName DATAINIZIOAMMRISERVE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_AMM_RISERVE");
    private static final javax.xml.namespace.QName DATAFINEAMMRISERVE$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_FINE_AMM_RISERVE");
    private static final javax.xml.namespace.QName ONERIAMMRISERVE$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_AMM_RISERVE");
    private static final javax.xml.namespace.QName DATAINIZIOARBRISERVE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_ARB_RISERVE");
    private static final javax.xml.namespace.QName DATAFINEARBRISERVE$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_FINE_ARB_RISERVE");
    private static final javax.xml.namespace.QName ONERIARBRISERVE$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_ARB_RISERVE");
    private static final javax.xml.namespace.QName DATAINIZIOGIURISERVE$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_GIU_RISERVE");
    private static final javax.xml.namespace.QName DATAFINEGIURISERVE$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_FINE_GIU_RISERVE");
    private static final javax.xml.namespace.QName ONERIGIURISERVE$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_GIU_RISERVE");
    private static final javax.xml.namespace.QName DATAINIZIOTRARISERVE$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_TRA_RISERVE");
    private static final javax.xml.namespace.QName DATAFINETRARISERVE$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_FINE_TRA_RISERVE");
    private static final javax.xml.namespace.QName ONERITRARISERVE$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_TRA_RISERVE");
    private static final javax.xml.namespace.QName FLAGSUBAPPALTATORI$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SUBAPPALTATORI");
    private static final javax.xml.namespace.QName DATAREGOLAREESEC$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_REGOLARE_ESEC");
    private static final javax.xml.namespace.QName DATACOLLAUDOSTAT$36 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_COLLAUDO_STAT");
    private static final javax.xml.namespace.QName MODOCOLLAUDO$38 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MODO_COLLAUDO");
    private static final javax.xml.namespace.QName DATANOMINACOLL$40 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_NOMINA_COLL");
    private static final javax.xml.namespace.QName DATAINIZIOOPER$42 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_INIZIO_OPER");
    private static final javax.xml.namespace.QName DATACERTCOLLAUDO$44 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_CERT_COLLAUDO");
    private static final javax.xml.namespace.QName DATADELIBERA$46 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_DELIBERA");
    private static final javax.xml.namespace.QName ESITOCOLLAUDO$48 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ESITO_COLLAUDO");
    private static final javax.xml.namespace.QName IMPFINALELAVORI$50 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_FINALE_LAVORI");
    private static final javax.xml.namespace.QName IMPFINALESERVIZI$52 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_FINALE_SERVIZI");
    private static final javax.xml.namespace.QName IMPFINALEFORNIT$54 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_FINALE_FORNIT");
    private static final javax.xml.namespace.QName IMPFINALESECUR$56 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_FINALE_SECUR");
    private static final javax.xml.namespace.QName IMPPROGETTAZIONE$58 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_PROGETTAZIONE");
    private static final javax.xml.namespace.QName IMPDISPOSIZIONE$60 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_DISPOSIZIONE");
    private static final javax.xml.namespace.QName AMMNUMDEFINITE$62 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "AMM_NUM_DEFINITE");
    private static final javax.xml.namespace.QName AMMNUMDADEF$64 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "AMM_NUM_DADEF");
    private static final javax.xml.namespace.QName AMMIMPORTORICH$66 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "AMM_IMPORTO_RICH");
    private static final javax.xml.namespace.QName AMMIMPORTODEF$68 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "AMM_IMPORTO_DEF");
    private static final javax.xml.namespace.QName ARBNUMDEFINITE$70 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ARB_NUM_DEFINITE");
    private static final javax.xml.namespace.QName ARBNUMDADEF$72 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ARB_NUM_DADEF");
    private static final javax.xml.namespace.QName ARBIMPORTORICH$74 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ARB_IMPORTO_RICH");
    private static final javax.xml.namespace.QName ARBIMPORTODEF$76 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ARB_IMPORTO_DEF");
    private static final javax.xml.namespace.QName GIUNUMDEFINITE$78 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "GIU_NUM_DEFINITE");
    private static final javax.xml.namespace.QName GIUNUMDADEF$80 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "GIU_NUM_DADEF");
    private static final javax.xml.namespace.QName GIUIMPORTORICH$82 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "GIU_IMPORTO_RICH");
    private static final javax.xml.namespace.QName GIUIMPORTODEF$84 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "GIU_IMPORTO_DEF");
    private static final javax.xml.namespace.QName TRANUMDEFINITE$86 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TRA_NUM_DEFINITE");
    private static final javax.xml.namespace.QName TRANUMDADEF$88 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TRA_NUM_DADEF");
    private static final javax.xml.namespace.QName TRAIMPORTORICH$90 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TRA_IMPORTO_RICH");
    private static final javax.xml.namespace.QName TRAIMPORTODEF$92 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TRA_IMPORTO_DEF");
    private static final javax.xml.namespace.QName IMPORTOSUBTOTALE$94 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_SUBTOTALE");
    private static final javax.xml.namespace.QName IMPORTOFINALE$96 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_FINALE");
    private static final javax.xml.namespace.QName IMPORTOCONSUNTIVO$98 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_CONSUNTIVO");
    private static final javax.xml.namespace.QName FLAGLAVORIESTESI$100 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_LAVORI_ESTESI");
    
    
    /**
     * Gets the "FLAG_SINGOLO_COMMISSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType.Enum getFLAGSINGOLOCOMMISSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSINGOLOCOMMISSIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SINGOLO_COMMISSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType xgetFLAGSINGOLOCOMMISSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType)get_store().find_element_user(FLAGSINGOLOCOMMISSIONE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SINGOLO_COMMISSIONE" element
     */
    public void setFLAGSINGOLOCOMMISSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType.Enum flagsingolocommissione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSINGOLOCOMMISSIONE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSINGOLOCOMMISSIONE$0);
            }
            target.setEnumValue(flagsingolocommissione);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SINGOLO_COMMISSIONE" element
     */
    public void xsetFLAGSINGOLOCOMMISSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType flagsingolocommissione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType)get_store().find_element_user(FLAGSINGOLOCOMMISSIONE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSingoloCommissioneType)get_store().add_element_user(FLAGSINGOLOCOMMISSIONE$0);
            }
            target.set(flagsingolocommissione);
        }
    }
    
    /**
     * Gets the "DATA_APPROVAZIONE" element
     */
    public java.util.Calendar getDATAAPPROVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAPPROVAZIONE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_APPROVAZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAAPPROVAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAPPROVAZIONE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_APPROVAZIONE" element
     */
    public void setDATAAPPROVAZIONE(java.util.Calendar dataapprovazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAPPROVAZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAAPPROVAZIONE$2);
            }
            target.setCalendarValue(dataapprovazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_APPROVAZIONE" element
     */
    public void xsetDATAAPPROVAZIONE(org.apache.xmlbeans.XmlDateTime dataapprovazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAPPROVAZIONE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAAPPROVAZIONE$2);
            }
            target.set(dataapprovazione);
        }
    }
    
    /**
     * Gets the "FLAG_IMPORTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGIMPORTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_IMPORTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGIMPORTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_IMPORTI" element
     */
    public void setFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagimporti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGIMPORTI$4);
            }
            target.setEnumValue(flagimporti);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_IMPORTI" element
     */
    public void xsetFLAGIMPORTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagimporti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGIMPORTI$4);
            }
            target.set(flagimporti);
        }
    }
    
    /**
     * Gets the "FLAG_SICUREZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum getFLAGSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$6, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SICUREZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType xgetFLAGSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SICUREZZA" element
     */
    public void setFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType.Enum flagsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSICUREZZA$6);
            }
            target.setEnumValue(flagsicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SICUREZZA" element
     */
    public void xsetFLAGSICUREZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType flagsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGSICUREZZA$6);
            }
            target.set(flagsicurezza);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_AMM_RISERVE" element
     */
    public java.util.Calendar getDATAINIZIOAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOAMMRISERVE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_AMM_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOAMMRISERVE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_AMM_RISERVE" element
     */
    public boolean isSetDATAINIZIOAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIZIOAMMRISERVE$8) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_AMM_RISERVE" element
     */
    public void setDATAINIZIOAMMRISERVE(java.util.Calendar datainizioammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOAMMRISERVE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOAMMRISERVE$8);
            }
            target.setCalendarValue(datainizioammriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_AMM_RISERVE" element
     */
    public void xsetDATAINIZIOAMMRISERVE(org.apache.xmlbeans.XmlDateTime datainizioammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOAMMRISERVE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOAMMRISERVE$8);
            }
            target.set(datainizioammriserve);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_AMM_RISERVE" element
     */
    public void unsetDATAINIZIOAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIZIOAMMRISERVE$8, 0);
        }
    }
    
    /**
     * Gets the "DATA_FINE_AMM_RISERVE" element
     */
    public java.util.Calendar getDATAFINEAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEAMMRISERVE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_FINE_AMM_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAFINEAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEAMMRISERVE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_FINE_AMM_RISERVE" element
     */
    public boolean isSetDATAFINEAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAFINEAMMRISERVE$10) != 0;
        }
    }
    
    /**
     * Sets the "DATA_FINE_AMM_RISERVE" element
     */
    public void setDATAFINEAMMRISERVE(java.util.Calendar datafineammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEAMMRISERVE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAFINEAMMRISERVE$10);
            }
            target.setCalendarValue(datafineammriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_FINE_AMM_RISERVE" element
     */
    public void xsetDATAFINEAMMRISERVE(org.apache.xmlbeans.XmlDateTime datafineammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEAMMRISERVE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAFINEAMMRISERVE$10);
            }
            target.set(datafineammriserve);
        }
    }
    
    /**
     * Unsets the "DATA_FINE_AMM_RISERVE" element
     */
    public void unsetDATAFINEAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAFINEAMMRISERVE$10, 0);
        }
    }
    
    /**
     * Gets the "ONERI_AMM_RISERVE" element
     */
    public double getONERIAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIAMMRISERVE$12, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_AMM_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERIAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIAMMRISERVE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_AMM_RISERVE" element
     */
    public boolean isSetONERIAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ONERIAMMRISERVE$12) != 0;
        }
    }
    
    /**
     * Sets the "ONERI_AMM_RISERVE" element
     */
    public void setONERIAMMRISERVE(double oneriammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIAMMRISERVE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERIAMMRISERVE$12);
            }
            target.setDoubleValue(oneriammriserve);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_AMM_RISERVE" element
     */
    public void xsetONERIAMMRISERVE(org.apache.xmlbeans.XmlDouble oneriammriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIAMMRISERVE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERIAMMRISERVE$12);
            }
            target.set(oneriammriserve);
        }
    }
    
    /**
     * Unsets the "ONERI_AMM_RISERVE" element
     */
    public void unsetONERIAMMRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ONERIAMMRISERVE$12, 0);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_ARB_RISERVE" element
     */
    public java.util.Calendar getDATAINIZIOARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOARBRISERVE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_ARB_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOARBRISERVE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_ARB_RISERVE" element
     */
    public boolean isSetDATAINIZIOARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIZIOARBRISERVE$14) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_ARB_RISERVE" element
     */
    public void setDATAINIZIOARBRISERVE(java.util.Calendar datainizioarbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOARBRISERVE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOARBRISERVE$14);
            }
            target.setCalendarValue(datainizioarbriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_ARB_RISERVE" element
     */
    public void xsetDATAINIZIOARBRISERVE(org.apache.xmlbeans.XmlDateTime datainizioarbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOARBRISERVE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOARBRISERVE$14);
            }
            target.set(datainizioarbriserve);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_ARB_RISERVE" element
     */
    public void unsetDATAINIZIOARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIZIOARBRISERVE$14, 0);
        }
    }
    
    /**
     * Gets the "DATA_FINE_ARB_RISERVE" element
     */
    public java.util.Calendar getDATAFINEARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEARBRISERVE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_FINE_ARB_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAFINEARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEARBRISERVE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_FINE_ARB_RISERVE" element
     */
    public boolean isSetDATAFINEARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAFINEARBRISERVE$16) != 0;
        }
    }
    
    /**
     * Sets the "DATA_FINE_ARB_RISERVE" element
     */
    public void setDATAFINEARBRISERVE(java.util.Calendar datafinearbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEARBRISERVE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAFINEARBRISERVE$16);
            }
            target.setCalendarValue(datafinearbriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_FINE_ARB_RISERVE" element
     */
    public void xsetDATAFINEARBRISERVE(org.apache.xmlbeans.XmlDateTime datafinearbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEARBRISERVE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAFINEARBRISERVE$16);
            }
            target.set(datafinearbriserve);
        }
    }
    
    /**
     * Unsets the "DATA_FINE_ARB_RISERVE" element
     */
    public void unsetDATAFINEARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAFINEARBRISERVE$16, 0);
        }
    }
    
    /**
     * Gets the "ONERI_ARB_RISERVE" element
     */
    public double getONERIARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIARBRISERVE$18, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_ARB_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERIARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIARBRISERVE$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_ARB_RISERVE" element
     */
    public boolean isSetONERIARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ONERIARBRISERVE$18) != 0;
        }
    }
    
    /**
     * Sets the "ONERI_ARB_RISERVE" element
     */
    public void setONERIARBRISERVE(double oneriarbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIARBRISERVE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERIARBRISERVE$18);
            }
            target.setDoubleValue(oneriarbriserve);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_ARB_RISERVE" element
     */
    public void xsetONERIARBRISERVE(org.apache.xmlbeans.XmlDouble oneriarbriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIARBRISERVE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERIARBRISERVE$18);
            }
            target.set(oneriarbriserve);
        }
    }
    
    /**
     * Unsets the "ONERI_ARB_RISERVE" element
     */
    public void unsetONERIARBRISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ONERIARBRISERVE$18, 0);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_GIU_RISERVE" element
     */
    public java.util.Calendar getDATAINIZIOGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOGIURISERVE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_GIU_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOGIURISERVE$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_GIU_RISERVE" element
     */
    public boolean isSetDATAINIZIOGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIZIOGIURISERVE$20) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_GIU_RISERVE" element
     */
    public void setDATAINIZIOGIURISERVE(java.util.Calendar datainiziogiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOGIURISERVE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOGIURISERVE$20);
            }
            target.setCalendarValue(datainiziogiuriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_GIU_RISERVE" element
     */
    public void xsetDATAINIZIOGIURISERVE(org.apache.xmlbeans.XmlDateTime datainiziogiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOGIURISERVE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOGIURISERVE$20);
            }
            target.set(datainiziogiuriserve);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_GIU_RISERVE" element
     */
    public void unsetDATAINIZIOGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIZIOGIURISERVE$20, 0);
        }
    }
    
    /**
     * Gets the "DATA_FINE_GIU_RISERVE" element
     */
    public java.util.Calendar getDATAFINEGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEGIURISERVE$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_FINE_GIU_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAFINEGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEGIURISERVE$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_FINE_GIU_RISERVE" element
     */
    public boolean isSetDATAFINEGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAFINEGIURISERVE$22) != 0;
        }
    }
    
    /**
     * Sets the "DATA_FINE_GIU_RISERVE" element
     */
    public void setDATAFINEGIURISERVE(java.util.Calendar datafinegiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINEGIURISERVE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAFINEGIURISERVE$22);
            }
            target.setCalendarValue(datafinegiuriserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_FINE_GIU_RISERVE" element
     */
    public void xsetDATAFINEGIURISERVE(org.apache.xmlbeans.XmlDateTime datafinegiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINEGIURISERVE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAFINEGIURISERVE$22);
            }
            target.set(datafinegiuriserve);
        }
    }
    
    /**
     * Unsets the "DATA_FINE_GIU_RISERVE" element
     */
    public void unsetDATAFINEGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAFINEGIURISERVE$22, 0);
        }
    }
    
    /**
     * Gets the "ONERI_GIU_RISERVE" element
     */
    public double getONERIGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIGIURISERVE$24, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_GIU_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERIGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIGIURISERVE$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_GIU_RISERVE" element
     */
    public boolean isSetONERIGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ONERIGIURISERVE$24) != 0;
        }
    }
    
    /**
     * Sets the "ONERI_GIU_RISERVE" element
     */
    public void setONERIGIURISERVE(double onerigiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIGIURISERVE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERIGIURISERVE$24);
            }
            target.setDoubleValue(onerigiuriserve);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_GIU_RISERVE" element
     */
    public void xsetONERIGIURISERVE(org.apache.xmlbeans.XmlDouble onerigiuriserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIGIURISERVE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERIGIURISERVE$24);
            }
            target.set(onerigiuriserve);
        }
    }
    
    /**
     * Unsets the "ONERI_GIU_RISERVE" element
     */
    public void unsetONERIGIURISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ONERIGIURISERVE$24, 0);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_TRA_RISERVE" element
     */
    public java.util.Calendar getDATAINIZIOTRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOTRARISERVE$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_TRA_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOTRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOTRARISERVE$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_TRA_RISERVE" element
     */
    public boolean isSetDATAINIZIOTRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIZIOTRARISERVE$26) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_TRA_RISERVE" element
     */
    public void setDATAINIZIOTRARISERVE(java.util.Calendar datainiziotrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOTRARISERVE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOTRARISERVE$26);
            }
            target.setCalendarValue(datainiziotrariserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_TRA_RISERVE" element
     */
    public void xsetDATAINIZIOTRARISERVE(org.apache.xmlbeans.XmlDateTime datainiziotrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOTRARISERVE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOTRARISERVE$26);
            }
            target.set(datainiziotrariserve);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_TRA_RISERVE" element
     */
    public void unsetDATAINIZIOTRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIZIOTRARISERVE$26, 0);
        }
    }
    
    /**
     * Gets the "DATA_FINE_TRA_RISERVE" element
     */
    public java.util.Calendar getDATAFINETRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINETRARISERVE$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_FINE_TRA_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAFINETRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINETRARISERVE$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_FINE_TRA_RISERVE" element
     */
    public boolean isSetDATAFINETRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAFINETRARISERVE$28) != 0;
        }
    }
    
    /**
     * Sets the "DATA_FINE_TRA_RISERVE" element
     */
    public void setDATAFINETRARISERVE(java.util.Calendar datafinetrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAFINETRARISERVE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAFINETRARISERVE$28);
            }
            target.setCalendarValue(datafinetrariserve);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_FINE_TRA_RISERVE" element
     */
    public void xsetDATAFINETRARISERVE(org.apache.xmlbeans.XmlDateTime datafinetrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAFINETRARISERVE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAFINETRARISERVE$28);
            }
            target.set(datafinetrariserve);
        }
    }
    
    /**
     * Unsets the "DATA_FINE_TRA_RISERVE" element
     */
    public void unsetDATAFINETRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAFINETRARISERVE$28, 0);
        }
    }
    
    /**
     * Gets the "ONERI_TRA_RISERVE" element
     */
    public double getONERITRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERITRARISERVE$30, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_TRA_RISERVE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERITRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERITRARISERVE$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_TRA_RISERVE" element
     */
    public boolean isSetONERITRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ONERITRARISERVE$30) != 0;
        }
    }
    
    /**
     * Sets the "ONERI_TRA_RISERVE" element
     */
    public void setONERITRARISERVE(double oneritrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERITRARISERVE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERITRARISERVE$30);
            }
            target.setDoubleValue(oneritrariserve);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_TRA_RISERVE" element
     */
    public void xsetONERITRARISERVE(org.apache.xmlbeans.XmlDouble oneritrariserve)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERITRARISERVE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERITRARISERVE$30);
            }
            target.set(oneritrariserve);
        }
    }
    
    /**
     * Unsets the "ONERI_TRA_RISERVE" element
     */
    public void unsetONERITRARISERVE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ONERITRARISERVE$30, 0);
        }
    }
    
    /**
     * Gets the "FLAG_SUBAPPALTATORI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGSUBAPPALTATORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSUBAPPALTATORI$32, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SUBAPPALTATORI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGSUBAPPALTATORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSUBAPPALTATORI$32, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SUBAPPALTATORI" element
     */
    public void setFLAGSUBAPPALTATORI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagsubappaltatori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSUBAPPALTATORI$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSUBAPPALTATORI$32);
            }
            target.setEnumValue(flagsubappaltatori);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SUBAPPALTATORI" element
     */
    public void xsetFLAGSUBAPPALTATORI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagsubappaltatori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSUBAPPALTATORI$32, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGSUBAPPALTATORI$32);
            }
            target.set(flagsubappaltatori);
        }
    }
    
    /**
     * Gets the "DATA_REGOLARE_ESEC" element
     */
    public java.util.Calendar getDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAREGOLAREESEC$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_REGOLARE_ESEC" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAREGOLAREESEC$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_REGOLARE_ESEC" element
     */
    public boolean isSetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAREGOLAREESEC$34) != 0;
        }
    }
    
    /**
     * Sets the "DATA_REGOLARE_ESEC" element
     */
    public void setDATAREGOLAREESEC(java.util.Calendar dataregolareesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAREGOLAREESEC$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAREGOLAREESEC$34);
            }
            target.setCalendarValue(dataregolareesec);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_REGOLARE_ESEC" element
     */
    public void xsetDATAREGOLAREESEC(org.apache.xmlbeans.XmlDateTime dataregolareesec)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAREGOLAREESEC$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAREGOLAREESEC$34);
            }
            target.set(dataregolareesec);
        }
    }
    
    /**
     * Unsets the "DATA_REGOLARE_ESEC" element
     */
    public void unsetDATAREGOLAREESEC()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAREGOLAREESEC$34, 0);
        }
    }
    
    /**
     * Gets the "DATA_COLLAUDO_STAT" element
     */
    public java.util.Calendar getDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACOLLAUDOSTAT$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_COLLAUDO_STAT" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACOLLAUDOSTAT$36, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_COLLAUDO_STAT" element
     */
    public boolean isSetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATACOLLAUDOSTAT$36) != 0;
        }
    }
    
    /**
     * Sets the "DATA_COLLAUDO_STAT" element
     */
    public void setDATACOLLAUDOSTAT(java.util.Calendar datacollaudostat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACOLLAUDOSTAT$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATACOLLAUDOSTAT$36);
            }
            target.setCalendarValue(datacollaudostat);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_COLLAUDO_STAT" element
     */
    public void xsetDATACOLLAUDOSTAT(org.apache.xmlbeans.XmlDateTime datacollaudostat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACOLLAUDOSTAT$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATACOLLAUDOSTAT$36);
            }
            target.set(datacollaudostat);
        }
    }
    
    /**
     * Unsets the "DATA_COLLAUDO_STAT" element
     */
    public void unsetDATACOLLAUDOSTAT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATACOLLAUDOSTAT$36, 0);
        }
    }
    
    /**
     * Gets the "MODO_COLLAUDO" element
     */
    public java.lang.String getMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOCOLLAUDO$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODO_COLLAUDO" element
     */
    public org.apache.xmlbeans.XmlString xgetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOCOLLAUDO$38, 0);
            return target;
        }
    }
    
    /**
     * True if has "MODO_COLLAUDO" element
     */
    public boolean isSetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MODOCOLLAUDO$38) != 0;
        }
    }
    
    /**
     * Sets the "MODO_COLLAUDO" element
     */
    public void setMODOCOLLAUDO(java.lang.String modocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOCOLLAUDO$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODOCOLLAUDO$38);
            }
            target.setStringValue(modocollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "MODO_COLLAUDO" element
     */
    public void xsetMODOCOLLAUDO(org.apache.xmlbeans.XmlString modocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOCOLLAUDO$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODOCOLLAUDO$38);
            }
            target.set(modocollaudo);
        }
    }
    
    /**
     * Unsets the "MODO_COLLAUDO" element
     */
    public void unsetMODOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MODOCOLLAUDO$38, 0);
        }
    }
    
    /**
     * Gets the "DATA_NOMINA_COLL" element
     */
    public java.util.Calendar getDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATANOMINACOLL$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_NOMINA_COLL" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATANOMINACOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATANOMINACOLL$40, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_NOMINA_COLL" element
     */
    public void setDATANOMINACOLL(java.util.Calendar datanominacoll)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATANOMINACOLL$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATANOMINACOLL$40);
            }
            target.setCalendarValue(datanominacoll);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_NOMINA_COLL" element
     */
    public void xsetDATANOMINACOLL(org.apache.xmlbeans.XmlDateTime datanominacoll)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATANOMINACOLL$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATANOMINACOLL$40);
            }
            target.set(datanominacoll);
        }
    }
    
    /**
     * Gets the "DATA_INIZIO_OPER" element
     */
    public java.util.Calendar getDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOOPER$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_INIZIO_OPER" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOOPER$42, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_INIZIO_OPER" element
     */
    public boolean isSetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAINIZIOOPER$42) != 0;
        }
    }
    
    /**
     * Sets the "DATA_INIZIO_OPER" element
     */
    public void setDATAINIZIOOPER(java.util.Calendar datainiziooper)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAINIZIOOPER$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAINIZIOOPER$42);
            }
            target.setCalendarValue(datainiziooper);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_INIZIO_OPER" element
     */
    public void xsetDATAINIZIOOPER(org.apache.xmlbeans.XmlDateTime datainiziooper)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAINIZIOOPER$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAINIZIOOPER$42);
            }
            target.set(datainiziooper);
        }
    }
    
    /**
     * Unsets the "DATA_INIZIO_OPER" element
     */
    public void unsetDATAINIZIOOPER()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAINIZIOOPER$42, 0);
        }
    }
    
    /**
     * Gets the "DATA_CERT_COLLAUDO" element
     */
    public java.util.Calendar getDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACERTCOLLAUDO$44, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CERT_COLLAUDO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACERTCOLLAUDO$44, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CERT_COLLAUDO" element
     */
    public boolean isSetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATACERTCOLLAUDO$44) != 0;
        }
    }
    
    /**
     * Sets the "DATA_CERT_COLLAUDO" element
     */
    public void setDATACERTCOLLAUDO(java.util.Calendar datacertcollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACERTCOLLAUDO$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATACERTCOLLAUDO$44);
            }
            target.setCalendarValue(datacertcollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CERT_COLLAUDO" element
     */
    public void xsetDATACERTCOLLAUDO(org.apache.xmlbeans.XmlDateTime datacertcollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACERTCOLLAUDO$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATACERTCOLLAUDO$44);
            }
            target.set(datacertcollaudo);
        }
    }
    
    /**
     * Unsets the "DATA_CERT_COLLAUDO" element
     */
    public void unsetDATACERTCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATACERTCOLLAUDO$44, 0);
        }
    }
    
    /**
     * Gets the "DATA_DELIBERA" element
     */
    public java.util.Calendar getDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATADELIBERA$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_DELIBERA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATADELIBERA$46, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_DELIBERA" element
     */
    public boolean isSetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATADELIBERA$46) != 0;
        }
    }
    
    /**
     * Sets the "DATA_DELIBERA" element
     */
    public void setDATADELIBERA(java.util.Calendar datadelibera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATADELIBERA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATADELIBERA$46);
            }
            target.setCalendarValue(datadelibera);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_DELIBERA" element
     */
    public void xsetDATADELIBERA(org.apache.xmlbeans.XmlDateTime datadelibera)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATADELIBERA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATADELIBERA$46);
            }
            target.set(datadelibera);
        }
    }
    
    /**
     * Unsets the "DATA_DELIBERA" element
     */
    public void unsetDATADELIBERA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATADELIBERA$46, 0);
        }
    }
    
    /**
     * Gets the "ESITO_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType.Enum getESITOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOCOLLAUDO$48, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ESITO_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType xgetESITOCOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType)get_store().find_element_user(ESITOCOLLAUDO$48, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ESITO_COLLAUDO" element
     */
    public void setESITOCOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType.Enum esitocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESITOCOLLAUDO$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ESITOCOLLAUDO$48);
            }
            target.setEnumValue(esitocollaudo);
        }
    }
    
    /**
     * Sets (as xml) the "ESITO_COLLAUDO" element
     */
    public void xsetESITOCOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType esitocollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType)get_store().find_element_user(ESITOCOLLAUDO$48, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagEsitoCollaudoType)get_store().add_element_user(ESITOCOLLAUDO$48);
            }
            target.set(esitocollaudo);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_LAVORI" element
     */
    public double getIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALELAVORI$50, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_LAVORI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALELAVORI$50, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_LAVORI" element
     */
    public boolean isSetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPFINALELAVORI$50) != 0;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_LAVORI" element
     */
    public void setIMPFINALELAVORI(double impfinalelavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALELAVORI$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPFINALELAVORI$50);
            }
            target.setDoubleValue(impfinalelavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_LAVORI" element
     */
    public void xsetIMPFINALELAVORI(org.apache.xmlbeans.XmlDouble impfinalelavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALELAVORI$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPFINALELAVORI$50);
            }
            target.set(impfinalelavori);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_LAVORI" element
     */
    public void unsetIMPFINALELAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPFINALELAVORI$50, 0);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_SERVIZI" element
     */
    public double getIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALESERVIZI$52, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_SERVIZI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALESERVIZI$52, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_SERVIZI" element
     */
    public boolean isSetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPFINALESERVIZI$52) != 0;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_SERVIZI" element
     */
    public void setIMPFINALESERVIZI(double impfinaleservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALESERVIZI$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPFINALESERVIZI$52);
            }
            target.setDoubleValue(impfinaleservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_SERVIZI" element
     */
    public void xsetIMPFINALESERVIZI(org.apache.xmlbeans.XmlDouble impfinaleservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALESERVIZI$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPFINALESERVIZI$52);
            }
            target.set(impfinaleservizi);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_SERVIZI" element
     */
    public void unsetIMPFINALESERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPFINALESERVIZI$52, 0);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_FORNIT" element
     */
    public double getIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALEFORNIT$54, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_FORNIT" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALEFORNIT$54, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_FORNIT" element
     */
    public boolean isSetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPFINALEFORNIT$54) != 0;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_FORNIT" element
     */
    public void setIMPFINALEFORNIT(double impfinalefornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALEFORNIT$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPFINALEFORNIT$54);
            }
            target.setDoubleValue(impfinalefornit);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_FORNIT" element
     */
    public void xsetIMPFINALEFORNIT(org.apache.xmlbeans.XmlDouble impfinalefornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALEFORNIT$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPFINALEFORNIT$54);
            }
            target.set(impfinalefornit);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_FORNIT" element
     */
    public void unsetIMPFINALEFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPFINALEFORNIT$54, 0);
        }
    }
    
    /**
     * Gets the "IMP_FINALE_SECUR" element
     */
    public double getIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALESECUR$56, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_FINALE_SECUR" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALESECUR$56, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_FINALE_SECUR" element
     */
    public boolean isSetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPFINALESECUR$56) != 0;
        }
    }
    
    /**
     * Sets the "IMP_FINALE_SECUR" element
     */
    public void setIMPFINALESECUR(double impfinalesecur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPFINALESECUR$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPFINALESECUR$56);
            }
            target.setDoubleValue(impfinalesecur);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_FINALE_SECUR" element
     */
    public void xsetIMPFINALESECUR(org.apache.xmlbeans.XmlDouble impfinalesecur)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPFINALESECUR$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPFINALESECUR$56);
            }
            target.set(impfinalesecur);
        }
    }
    
    /**
     * Unsets the "IMP_FINALE_SECUR" element
     */
    public void unsetIMPFINALESECUR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPFINALESECUR$56, 0);
        }
    }
    
    /**
     * Gets the "IMP_PROGETTAZIONE" element
     */
    public double getIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPPROGETTAZIONE$58, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_PROGETTAZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPPROGETTAZIONE$58, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_PROGETTAZIONE" element
     */
    public boolean isSetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPPROGETTAZIONE$58) != 0;
        }
    }
    
    /**
     * Sets the "IMP_PROGETTAZIONE" element
     */
    public void setIMPPROGETTAZIONE(double impprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPPROGETTAZIONE$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPPROGETTAZIONE$58);
            }
            target.setDoubleValue(impprogettazione);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_PROGETTAZIONE" element
     */
    public void xsetIMPPROGETTAZIONE(org.apache.xmlbeans.XmlDouble impprogettazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPPROGETTAZIONE$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPPROGETTAZIONE$58);
            }
            target.set(impprogettazione);
        }
    }
    
    /**
     * Unsets the "IMP_PROGETTAZIONE" element
     */
    public void unsetIMPPROGETTAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPPROGETTAZIONE$58, 0);
        }
    }
    
    /**
     * Gets the "IMP_DISPOSIZIONE" element
     */
    public double getIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPDISPOSIZIONE$60, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_DISPOSIZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPDISPOSIZIONE$60, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMP_DISPOSIZIONE" element
     */
    public void setIMPDISPOSIZIONE(double impdisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPDISPOSIZIONE$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPDISPOSIZIONE$60);
            }
            target.setDoubleValue(impdisposizione);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_DISPOSIZIONE" element
     */
    public void xsetIMPDISPOSIZIONE(org.apache.xmlbeans.XmlDouble impdisposizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPDISPOSIZIONE$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPDISPOSIZIONE$60);
            }
            target.set(impdisposizione);
        }
    }
    
    /**
     * Gets the "AMM_NUM_DEFINITE" element
     */
    public long getAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMNUMDEFINITE$62, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_NUM_DEFINITE" element
     */
    public org.apache.xmlbeans.XmlLong xgetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(AMMNUMDEFINITE$62, 0);
            return target;
        }
    }
    
    /**
     * True if has "AMM_NUM_DEFINITE" element
     */
    public boolean isSetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMMNUMDEFINITE$62) != 0;
        }
    }
    
    /**
     * Sets the "AMM_NUM_DEFINITE" element
     */
    public void setAMMNUMDEFINITE(long ammnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMNUMDEFINITE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AMMNUMDEFINITE$62);
            }
            target.setLongValue(ammnumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_NUM_DEFINITE" element
     */
    public void xsetAMMNUMDEFINITE(org.apache.xmlbeans.XmlLong ammnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(AMMNUMDEFINITE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(AMMNUMDEFINITE$62);
            }
            target.set(ammnumdefinite);
        }
    }
    
    /**
     * Unsets the "AMM_NUM_DEFINITE" element
     */
    public void unsetAMMNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMMNUMDEFINITE$62, 0);
        }
    }
    
    /**
     * Gets the "AMM_NUM_DADEF" element
     */
    public long getAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMNUMDADEF$64, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_NUM_DADEF" element
     */
    public org.apache.xmlbeans.XmlLong xgetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(AMMNUMDADEF$64, 0);
            return target;
        }
    }
    
    /**
     * True if has "AMM_NUM_DADEF" element
     */
    public boolean isSetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMMNUMDADEF$64) != 0;
        }
    }
    
    /**
     * Sets the "AMM_NUM_DADEF" element
     */
    public void setAMMNUMDADEF(long ammnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMNUMDADEF$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AMMNUMDADEF$64);
            }
            target.setLongValue(ammnumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_NUM_DADEF" element
     */
    public void xsetAMMNUMDADEF(org.apache.xmlbeans.XmlLong ammnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(AMMNUMDADEF$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(AMMNUMDADEF$64);
            }
            target.set(ammnumdadef);
        }
    }
    
    /**
     * Unsets the "AMM_NUM_DADEF" element
     */
    public void unsetAMMNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMMNUMDADEF$64, 0);
        }
    }
    
    /**
     * Gets the "AMM_IMPORTO_RICH" element
     */
    public double getAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMIMPORTORICH$66, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_RICH" element
     */
    public org.apache.xmlbeans.XmlDouble xgetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(AMMIMPORTORICH$66, 0);
            return target;
        }
    }
    
    /**
     * True if has "AMM_IMPORTO_RICH" element
     */
    public boolean isSetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMMIMPORTORICH$66) != 0;
        }
    }
    
    /**
     * Sets the "AMM_IMPORTO_RICH" element
     */
    public void setAMMIMPORTORICH(double ammimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMIMPORTORICH$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AMMIMPORTORICH$66);
            }
            target.setDoubleValue(ammimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_RICH" element
     */
    public void xsetAMMIMPORTORICH(org.apache.xmlbeans.XmlDouble ammimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(AMMIMPORTORICH$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(AMMIMPORTORICH$66);
            }
            target.set(ammimportorich);
        }
    }
    
    /**
     * Unsets the "AMM_IMPORTO_RICH" element
     */
    public void unsetAMMIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMMIMPORTORICH$66, 0);
        }
    }
    
    /**
     * Gets the "AMM_IMPORTO_DEF" element
     */
    public double getAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMIMPORTODEF$68, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "AMM_IMPORTO_DEF" element
     */
    public org.apache.xmlbeans.XmlDouble xgetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(AMMIMPORTODEF$68, 0);
            return target;
        }
    }
    
    /**
     * True if has "AMM_IMPORTO_DEF" element
     */
    public boolean isSetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AMMIMPORTODEF$68) != 0;
        }
    }
    
    /**
     * Sets the "AMM_IMPORTO_DEF" element
     */
    public void setAMMIMPORTODEF(double ammimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AMMIMPORTODEF$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AMMIMPORTODEF$68);
            }
            target.setDoubleValue(ammimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "AMM_IMPORTO_DEF" element
     */
    public void xsetAMMIMPORTODEF(org.apache.xmlbeans.XmlDouble ammimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(AMMIMPORTODEF$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(AMMIMPORTODEF$68);
            }
            target.set(ammimportodef);
        }
    }
    
    /**
     * Unsets the "AMM_IMPORTO_DEF" element
     */
    public void unsetAMMIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AMMIMPORTODEF$68, 0);
        }
    }
    
    /**
     * Gets the "ARB_NUM_DEFINITE" element
     */
    public long getARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBNUMDEFINITE$70, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_NUM_DEFINITE" element
     */
    public org.apache.xmlbeans.XmlLong xgetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ARBNUMDEFINITE$70, 0);
            return target;
        }
    }
    
    /**
     * True if has "ARB_NUM_DEFINITE" element
     */
    public boolean isSetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARBNUMDEFINITE$70) != 0;
        }
    }
    
    /**
     * Sets the "ARB_NUM_DEFINITE" element
     */
    public void setARBNUMDEFINITE(long arbnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBNUMDEFINITE$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARBNUMDEFINITE$70);
            }
            target.setLongValue(arbnumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_NUM_DEFINITE" element
     */
    public void xsetARBNUMDEFINITE(org.apache.xmlbeans.XmlLong arbnumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ARBNUMDEFINITE$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(ARBNUMDEFINITE$70);
            }
            target.set(arbnumdefinite);
        }
    }
    
    /**
     * Unsets the "ARB_NUM_DEFINITE" element
     */
    public void unsetARBNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARBNUMDEFINITE$70, 0);
        }
    }
    
    /**
     * Gets the "ARB_NUM_DADEF" element
     */
    public long getARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBNUMDADEF$72, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_NUM_DADEF" element
     */
    public org.apache.xmlbeans.XmlLong xgetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ARBNUMDADEF$72, 0);
            return target;
        }
    }
    
    /**
     * True if has "ARB_NUM_DADEF" element
     */
    public boolean isSetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARBNUMDADEF$72) != 0;
        }
    }
    
    /**
     * Sets the "ARB_NUM_DADEF" element
     */
    public void setARBNUMDADEF(long arbnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBNUMDADEF$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARBNUMDADEF$72);
            }
            target.setLongValue(arbnumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_NUM_DADEF" element
     */
    public void xsetARBNUMDADEF(org.apache.xmlbeans.XmlLong arbnumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ARBNUMDADEF$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(ARBNUMDADEF$72);
            }
            target.set(arbnumdadef);
        }
    }
    
    /**
     * Unsets the "ARB_NUM_DADEF" element
     */
    public void unsetARBNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARBNUMDADEF$72, 0);
        }
    }
    
    /**
     * Gets the "ARB_IMPORTO_RICH" element
     */
    public double getARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBIMPORTORICH$74, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_RICH" element
     */
    public org.apache.xmlbeans.XmlDouble xgetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ARBIMPORTORICH$74, 0);
            return target;
        }
    }
    
    /**
     * True if has "ARB_IMPORTO_RICH" element
     */
    public boolean isSetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARBIMPORTORICH$74) != 0;
        }
    }
    
    /**
     * Sets the "ARB_IMPORTO_RICH" element
     */
    public void setARBIMPORTORICH(double arbimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBIMPORTORICH$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARBIMPORTORICH$74);
            }
            target.setDoubleValue(arbimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_RICH" element
     */
    public void xsetARBIMPORTORICH(org.apache.xmlbeans.XmlDouble arbimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ARBIMPORTORICH$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ARBIMPORTORICH$74);
            }
            target.set(arbimportorich);
        }
    }
    
    /**
     * Unsets the "ARB_IMPORTO_RICH" element
     */
    public void unsetARBIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARBIMPORTORICH$74, 0);
        }
    }
    
    /**
     * Gets the "ARB_IMPORTO_DEF" element
     */
    public double getARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBIMPORTODEF$76, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ARB_IMPORTO_DEF" element
     */
    public org.apache.xmlbeans.XmlDouble xgetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ARBIMPORTODEF$76, 0);
            return target;
        }
    }
    
    /**
     * True if has "ARB_IMPORTO_DEF" element
     */
    public boolean isSetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARBIMPORTODEF$76) != 0;
        }
    }
    
    /**
     * Sets the "ARB_IMPORTO_DEF" element
     */
    public void setARBIMPORTODEF(double arbimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARBIMPORTODEF$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARBIMPORTODEF$76);
            }
            target.setDoubleValue(arbimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "ARB_IMPORTO_DEF" element
     */
    public void xsetARBIMPORTODEF(org.apache.xmlbeans.XmlDouble arbimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ARBIMPORTODEF$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ARBIMPORTODEF$76);
            }
            target.set(arbimportodef);
        }
    }
    
    /**
     * Unsets the "ARB_IMPORTO_DEF" element
     */
    public void unsetARBIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARBIMPORTODEF$76, 0);
        }
    }
    
    /**
     * Gets the "GIU_NUM_DEFINITE" element
     */
    public long getGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUNUMDEFINITE$78, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_NUM_DEFINITE" element
     */
    public org.apache.xmlbeans.XmlLong xgetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GIUNUMDEFINITE$78, 0);
            return target;
        }
    }
    
    /**
     * True if has "GIU_NUM_DEFINITE" element
     */
    public boolean isSetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GIUNUMDEFINITE$78) != 0;
        }
    }
    
    /**
     * Sets the "GIU_NUM_DEFINITE" element
     */
    public void setGIUNUMDEFINITE(long giunumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUNUMDEFINITE$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GIUNUMDEFINITE$78);
            }
            target.setLongValue(giunumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_NUM_DEFINITE" element
     */
    public void xsetGIUNUMDEFINITE(org.apache.xmlbeans.XmlLong giunumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GIUNUMDEFINITE$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(GIUNUMDEFINITE$78);
            }
            target.set(giunumdefinite);
        }
    }
    
    /**
     * Unsets the "GIU_NUM_DEFINITE" element
     */
    public void unsetGIUNUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GIUNUMDEFINITE$78, 0);
        }
    }
    
    /**
     * Gets the "GIU_NUM_DADEF" element
     */
    public long getGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUNUMDADEF$80, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_NUM_DADEF" element
     */
    public org.apache.xmlbeans.XmlLong xgetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GIUNUMDADEF$80, 0);
            return target;
        }
    }
    
    /**
     * True if has "GIU_NUM_DADEF" element
     */
    public boolean isSetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GIUNUMDADEF$80) != 0;
        }
    }
    
    /**
     * Sets the "GIU_NUM_DADEF" element
     */
    public void setGIUNUMDADEF(long giunumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUNUMDADEF$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GIUNUMDADEF$80);
            }
            target.setLongValue(giunumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_NUM_DADEF" element
     */
    public void xsetGIUNUMDADEF(org.apache.xmlbeans.XmlLong giunumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(GIUNUMDADEF$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(GIUNUMDADEF$80);
            }
            target.set(giunumdadef);
        }
    }
    
    /**
     * Unsets the "GIU_NUM_DADEF" element
     */
    public void unsetGIUNUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GIUNUMDADEF$80, 0);
        }
    }
    
    /**
     * Gets the "GIU_IMPORTO_RICH" element
     */
    public double getGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUIMPORTORICH$82, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_RICH" element
     */
    public org.apache.xmlbeans.XmlDouble xgetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GIUIMPORTORICH$82, 0);
            return target;
        }
    }
    
    /**
     * True if has "GIU_IMPORTO_RICH" element
     */
    public boolean isSetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GIUIMPORTORICH$82) != 0;
        }
    }
    
    /**
     * Sets the "GIU_IMPORTO_RICH" element
     */
    public void setGIUIMPORTORICH(double giuimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUIMPORTORICH$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GIUIMPORTORICH$82);
            }
            target.setDoubleValue(giuimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_RICH" element
     */
    public void xsetGIUIMPORTORICH(org.apache.xmlbeans.XmlDouble giuimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GIUIMPORTORICH$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(GIUIMPORTORICH$82);
            }
            target.set(giuimportorich);
        }
    }
    
    /**
     * Unsets the "GIU_IMPORTO_RICH" element
     */
    public void unsetGIUIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GIUIMPORTORICH$82, 0);
        }
    }
    
    /**
     * Gets the "GIU_IMPORTO_DEF" element
     */
    public double getGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUIMPORTODEF$84, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "GIU_IMPORTO_DEF" element
     */
    public org.apache.xmlbeans.XmlDouble xgetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GIUIMPORTODEF$84, 0);
            return target;
        }
    }
    
    /**
     * True if has "GIU_IMPORTO_DEF" element
     */
    public boolean isSetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GIUIMPORTODEF$84) != 0;
        }
    }
    
    /**
     * Sets the "GIU_IMPORTO_DEF" element
     */
    public void setGIUIMPORTODEF(double giuimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIUIMPORTODEF$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GIUIMPORTODEF$84);
            }
            target.setDoubleValue(giuimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "GIU_IMPORTO_DEF" element
     */
    public void xsetGIUIMPORTODEF(org.apache.xmlbeans.XmlDouble giuimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GIUIMPORTODEF$84, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(GIUIMPORTODEF$84);
            }
            target.set(giuimportodef);
        }
    }
    
    /**
     * Unsets the "GIU_IMPORTO_DEF" element
     */
    public void unsetGIUIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GIUIMPORTODEF$84, 0);
        }
    }
    
    /**
     * Gets the "TRA_NUM_DEFINITE" element
     */
    public long getTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANUMDEFINITE$86, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_NUM_DEFINITE" element
     */
    public org.apache.xmlbeans.XmlLong xgetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(TRANUMDEFINITE$86, 0);
            return target;
        }
    }
    
    /**
     * True if has "TRA_NUM_DEFINITE" element
     */
    public boolean isSetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANUMDEFINITE$86) != 0;
        }
    }
    
    /**
     * Sets the "TRA_NUM_DEFINITE" element
     */
    public void setTRANUMDEFINITE(long tranumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANUMDEFINITE$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANUMDEFINITE$86);
            }
            target.setLongValue(tranumdefinite);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_NUM_DEFINITE" element
     */
    public void xsetTRANUMDEFINITE(org.apache.xmlbeans.XmlLong tranumdefinite)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(TRANUMDEFINITE$86, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(TRANUMDEFINITE$86);
            }
            target.set(tranumdefinite);
        }
    }
    
    /**
     * Unsets the "TRA_NUM_DEFINITE" element
     */
    public void unsetTRANUMDEFINITE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANUMDEFINITE$86, 0);
        }
    }
    
    /**
     * Gets the "TRA_NUM_DADEF" element
     */
    public long getTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANUMDADEF$88, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_NUM_DADEF" element
     */
    public org.apache.xmlbeans.XmlLong xgetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(TRANUMDADEF$88, 0);
            return target;
        }
    }
    
    /**
     * True if has "TRA_NUM_DADEF" element
     */
    public boolean isSetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANUMDADEF$88) != 0;
        }
    }
    
    /**
     * Sets the "TRA_NUM_DADEF" element
     */
    public void setTRANUMDADEF(long tranumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANUMDADEF$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANUMDADEF$88);
            }
            target.setLongValue(tranumdadef);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_NUM_DADEF" element
     */
    public void xsetTRANUMDADEF(org.apache.xmlbeans.XmlLong tranumdadef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(TRANUMDADEF$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(TRANUMDADEF$88);
            }
            target.set(tranumdadef);
        }
    }
    
    /**
     * Unsets the "TRA_NUM_DADEF" element
     */
    public void unsetTRANUMDADEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANUMDADEF$88, 0);
        }
    }
    
    /**
     * Gets the "TRA_IMPORTO_RICH" element
     */
    public double getTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRAIMPORTORICH$90, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_RICH" element
     */
    public org.apache.xmlbeans.XmlDouble xgetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRAIMPORTORICH$90, 0);
            return target;
        }
    }
    
    /**
     * True if has "TRA_IMPORTO_RICH" element
     */
    public boolean isSetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRAIMPORTORICH$90) != 0;
        }
    }
    
    /**
     * Sets the "TRA_IMPORTO_RICH" element
     */
    public void setTRAIMPORTORICH(double traimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRAIMPORTORICH$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRAIMPORTORICH$90);
            }
            target.setDoubleValue(traimportorich);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_RICH" element
     */
    public void xsetTRAIMPORTORICH(org.apache.xmlbeans.XmlDouble traimportorich)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRAIMPORTORICH$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TRAIMPORTORICH$90);
            }
            target.set(traimportorich);
        }
    }
    
    /**
     * Unsets the "TRA_IMPORTO_RICH" element
     */
    public void unsetTRAIMPORTORICH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRAIMPORTORICH$90, 0);
        }
    }
    
    /**
     * Gets the "TRA_IMPORTO_DEF" element
     */
    public double getTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRAIMPORTODEF$92, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "TRA_IMPORTO_DEF" element
     */
    public org.apache.xmlbeans.XmlDouble xgetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRAIMPORTODEF$92, 0);
            return target;
        }
    }
    
    /**
     * True if has "TRA_IMPORTO_DEF" element
     */
    public boolean isSetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRAIMPORTODEF$92) != 0;
        }
    }
    
    /**
     * Sets the "TRA_IMPORTO_DEF" element
     */
    public void setTRAIMPORTODEF(double traimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRAIMPORTODEF$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRAIMPORTODEF$92);
            }
            target.setDoubleValue(traimportodef);
        }
    }
    
    /**
     * Sets (as xml) the "TRA_IMPORTO_DEF" element
     */
    public void xsetTRAIMPORTODEF(org.apache.xmlbeans.XmlDouble traimportodef)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRAIMPORTODEF$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TRAIMPORTODEF$92);
            }
            target.set(traimportodef);
        }
    }
    
    /**
     * Unsets the "TRA_IMPORTO_DEF" element
     */
    public void unsetTRAIMPORTODEF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRAIMPORTODEF$92, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_SUBTOTALE" element
     */
    public double getIMPORTOSUBTOTALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSUBTOTALE$94, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOSUBTOTALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSUBTOTALE$94, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_SUBTOTALE" element
     */
    public void setIMPORTOSUBTOTALE(double importosubtotale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSUBTOTALE$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSUBTOTALE$94);
            }
            target.setDoubleValue(importosubtotale);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_SUBTOTALE" element
     */
    public void xsetIMPORTOSUBTOTALE(org.apache.xmlbeans.XmlDouble importosubtotale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSUBTOTALE$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOSUBTOTALE$94);
            }
            target.set(importosubtotale);
        }
    }
    
    /**
     * Gets the "IMPORTO_FINALE" element
     */
    public double getIMPORTOFINALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFINALE$96, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_FINALE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOFINALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFINALE$96, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_FINALE" element
     */
    public void setIMPORTOFINALE(double importofinale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOFINALE$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOFINALE$96);
            }
            target.setDoubleValue(importofinale);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_FINALE" element
     */
    public void xsetIMPORTOFINALE(org.apache.xmlbeans.XmlDouble importofinale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOFINALE$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOFINALE$96);
            }
            target.set(importofinale);
        }
    }
    
    /**
     * Gets the "IMPORTO_CONSUNTIVO" element
     */
    public double getIMPORTOCONSUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCONSUNTIVO$98, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_CONSUNTIVO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOCONSUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCONSUNTIVO$98, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_CONSUNTIVO" element
     */
    public void setIMPORTOCONSUNTIVO(double importoconsuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCONSUNTIVO$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOCONSUNTIVO$98);
            }
            target.setDoubleValue(importoconsuntivo);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_CONSUNTIVO" element
     */
    public void xsetIMPORTOCONSUNTIVO(org.apache.xmlbeans.XmlDouble importoconsuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCONSUNTIVO$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOCONSUNTIVO$98);
            }
            target.set(importoconsuntivo);
        }
    }
    
    /**
     * Gets the "FLAG_LAVORI_ESTESI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGLAVORIESTESI$100, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_LAVORI_ESTESI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGLAVORIESTESI$100, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_LAVORI_ESTESI" element
     */
    public boolean isSetFLAGLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGLAVORIESTESI$100) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_LAVORI_ESTESI" element
     */
    public void setFLAGLAVORIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flaglavoriestesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGLAVORIESTESI$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGLAVORIESTESI$100);
            }
            target.setEnumValue(flaglavoriestesi);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_LAVORI_ESTESI" element
     */
    public void xsetFLAGLAVORIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flaglavoriestesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGLAVORIESTESI$100, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGLAVORIESTESI$100);
            }
            target.set(flaglavoriestesi);
        }
    }
    
    /**
     * Unsets the "FLAG_LAVORI_ESTESI" element
     */
    public void unsetFLAGLAVORIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGLAVORIESTESI$100, 0);
        }
    }
}
