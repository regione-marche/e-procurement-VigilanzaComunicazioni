/*
 * XML Type:  DatiSoggettiEstesi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiSoggettiEstesi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiSoggettiEstesiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi
{
    
    public DatiSoggettiEstesiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDRUOLO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_RUOLO");
    private static final javax.xml.namespace.QName CIGPROGESTERNA$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CIG_PROG_ESTERNA");
    private static final javax.xml.namespace.QName DATAAFFPROGESTERNA$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_AFF_PROG_ESTERNA");
    private static final javax.xml.namespace.QName DATACONSPROGESTERNA$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_CONS_PROG_ESTERNA");
    private static final javax.xml.namespace.QName SEZIONE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE");
    private static final javax.xml.namespace.QName COSTOPROGETTO$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COSTO_PROGETTO");
    private static final javax.xml.namespace.QName FLAGCOLLAUDATORE$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_COLLAUDATORE");
    private static final javax.xml.namespace.QName IMPORTOCOLLAUDATORE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_COLLAUDATORE");
    private static final javax.xml.namespace.QName RESPONSABILE$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "responsabile");
    
    
    /**
     * Gets the "ID_RUOLO" element
     */
    public java.lang.String getIDRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDRUOLO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_RUOLO" element
     */
    public org.apache.xmlbeans.XmlString xgetIDRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDRUOLO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_RUOLO" element
     */
    public void setIDRUOLO(java.lang.String idruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDRUOLO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDRUOLO$0);
            }
            target.setStringValue(idruolo);
        }
    }
    
    /**
     * Sets (as xml) the "ID_RUOLO" element
     */
    public void xsetIDRUOLO(org.apache.xmlbeans.XmlString idruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDRUOLO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDRUOLO$0);
            }
            target.set(idruolo);
        }
    }
    
    /**
     * Gets the "CIG_PROG_ESTERNA" element
     */
    public java.lang.String getCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIGPROGESTERNA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_PROG_ESTERNA" element
     */
    public org.apache.xmlbeans.XmlString xgetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIGPROGESTERNA$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "CIG_PROG_ESTERNA" element
     */
    public boolean isSetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIGPROGESTERNA$2) != 0;
        }
    }
    
    /**
     * Sets the "CIG_PROG_ESTERNA" element
     */
    public void setCIGPROGESTERNA(java.lang.String cigprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIGPROGESTERNA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIGPROGESTERNA$2);
            }
            target.setStringValue(cigprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_PROG_ESTERNA" element
     */
    public void xsetCIGPROGESTERNA(org.apache.xmlbeans.XmlString cigprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIGPROGESTERNA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIGPROGESTERNA$2);
            }
            target.set(cigprogesterna);
        }
    }
    
    /**
     * Unsets the "CIG_PROG_ESTERNA" element
     */
    public void unsetCIGPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIGPROGESTERNA$2, 0);
        }
    }
    
    /**
     * Gets the "DATA_AFF_PROG_ESTERNA" element
     */
    public java.util.Calendar getDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAFFPROGESTERNA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_AFF_PROG_ESTERNA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAFFPROGESTERNA$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_AFF_PROG_ESTERNA" element
     */
    public boolean isSetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAAFFPROGESTERNA$4) != 0;
        }
    }
    
    /**
     * Sets the "DATA_AFF_PROG_ESTERNA" element
     */
    public void setDATAAFFPROGESTERNA(java.util.Calendar dataaffprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAAFFPROGESTERNA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAAFFPROGESTERNA$4);
            }
            target.setCalendarValue(dataaffprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_AFF_PROG_ESTERNA" element
     */
    public void xsetDATAAFFPROGESTERNA(org.apache.xmlbeans.XmlDateTime dataaffprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAAFFPROGESTERNA$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAAFFPROGESTERNA$4);
            }
            target.set(dataaffprogesterna);
        }
    }
    
    /**
     * Unsets the "DATA_AFF_PROG_ESTERNA" element
     */
    public void unsetDATAAFFPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAAFFPROGESTERNA$4, 0);
        }
    }
    
    /**
     * Gets the "DATA_CONS_PROG_ESTERNA" element
     */
    public java.util.Calendar getDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACONSPROGESTERNA$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_CONS_PROG_ESTERNA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACONSPROGESTERNA$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_CONS_PROG_ESTERNA" element
     */
    public boolean isSetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATACONSPROGESTERNA$6) != 0;
        }
    }
    
    /**
     * Sets the "DATA_CONS_PROG_ESTERNA" element
     */
    public void setDATACONSPROGESTERNA(java.util.Calendar dataconsprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATACONSPROGESTERNA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATACONSPROGESTERNA$6);
            }
            target.setCalendarValue(dataconsprogesterna);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_CONS_PROG_ESTERNA" element
     */
    public void xsetDATACONSPROGESTERNA(org.apache.xmlbeans.XmlDateTime dataconsprogesterna)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATACONSPROGESTERNA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATACONSPROGESTERNA$6);
            }
            target.set(dataconsprogesterna);
        }
    }
    
    /**
     * Unsets the "DATA_CONS_PROG_ESTERNA" element
     */
    public void unsetDATACONSPROGESTERNA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATACONSPROGESTERNA$6, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE" element
     */
    public java.lang.String getSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEZIONE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SEZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEZIONE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE" element
     */
    public boolean isSetSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONE$8) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE" element
     */
    public void setSEZIONE(java.lang.String sezione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEZIONE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SEZIONE$8);
            }
            target.setStringValue(sezione);
        }
    }
    
    /**
     * Sets (as xml) the "SEZIONE" element
     */
    public void xsetSEZIONE(org.apache.xmlbeans.XmlString sezione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEZIONE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SEZIONE$8);
            }
            target.set(sezione);
        }
    }
    
    /**
     * Unsets the "SEZIONE" element
     */
    public void unsetSEZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONE$8, 0);
        }
    }
    
    /**
     * Gets the "COSTO_PROGETTO" element
     */
    public double getCOSTOPROGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTOPROGETTO$10, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "COSTO_PROGETTO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetCOSTOPROGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(COSTOPROGETTO$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "COSTO_PROGETTO" element
     */
    public boolean isSetCOSTOPROGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COSTOPROGETTO$10) != 0;
        }
    }
    
    /**
     * Sets the "COSTO_PROGETTO" element
     */
    public void setCOSTOPROGETTO(double costoprogetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTOPROGETTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COSTOPROGETTO$10);
            }
            target.setDoubleValue(costoprogetto);
        }
    }
    
    /**
     * Sets (as xml) the "COSTO_PROGETTO" element
     */
    public void xsetCOSTOPROGETTO(org.apache.xmlbeans.XmlDouble costoprogetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(COSTOPROGETTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(COSTOPROGETTO$10);
            }
            target.set(costoprogetto);
        }
    }
    
    /**
     * Unsets the "COSTO_PROGETTO" element
     */
    public void unsetCOSTOPROGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COSTOPROGETTO$10, 0);
        }
    }
    
    /**
     * Gets the "FLAG_COLLAUDATORE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType.Enum getFLAGCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGCOLLAUDATORE$12, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_COLLAUDATORE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType xgetFLAGCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType)get_store().find_element_user(FLAGCOLLAUDATORE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_COLLAUDATORE" element
     */
    public boolean isSetFLAGCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGCOLLAUDATORE$12) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_COLLAUDATORE" element
     */
    public void setFLAGCOLLAUDATORE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType.Enum flagcollaudatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGCOLLAUDATORE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGCOLLAUDATORE$12);
            }
            target.setEnumValue(flagcollaudatore);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_COLLAUDATORE" element
     */
    public void xsetFLAGCOLLAUDATORE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType flagcollaudatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType)get_store().find_element_user(FLAGCOLLAUDATORE$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagCollaudatoreType)get_store().add_element_user(FLAGCOLLAUDATORE$12);
            }
            target.set(flagcollaudatore);
        }
    }
    
    /**
     * Unsets the "FLAG_COLLAUDATORE" element
     */
    public void unsetFLAGCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGCOLLAUDATORE$12, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_COLLAUDATORE" element
     */
    public double getIMPORTOCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCOLLAUDATORE$14, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_COLLAUDATORE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCOLLAUDATORE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_COLLAUDATORE" element
     */
    public boolean isSetIMPORTOCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOCOLLAUDATORE$14) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_COLLAUDATORE" element
     */
    public void setIMPORTOCOLLAUDATORE(double importocollaudatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCOLLAUDATORE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOCOLLAUDATORE$14);
            }
            target.setDoubleValue(importocollaudatore);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_COLLAUDATORE" element
     */
    public void xsetIMPORTOCOLLAUDATORE(org.apache.xmlbeans.XmlDouble importocollaudatore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCOLLAUDATORE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOCOLLAUDATORE$14);
            }
            target.set(importocollaudatore);
        }
    }
    
    /**
     * Unsets the "IMPORTO_COLLAUDATORE" element
     */
    public void unsetIMPORTOCOLLAUDATORE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOCOLLAUDATORE$14, 0);
        }
    }
    
    /**
     * Gets the "responsabile" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile getResponsabile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile)get_store().find_element_user(RESPONSABILE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "responsabile" element
     */
    public void setResponsabile(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile responsabile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile)get_store().find_element_user(RESPONSABILE$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile)get_store().add_element_user(RESPONSABILE$16);
            }
            target.set(responsabile);
        }
    }
    
    /**
     * Appends and returns a new empty "responsabile" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile addNewResponsabile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile)get_store().add_element_user(RESPONSABILE$16);
            return target;
        }
    }
}
