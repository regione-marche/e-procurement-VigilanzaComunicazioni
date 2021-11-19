/*
 * XML Type:  AggiornaContrattoInviato
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.AggiornaContrattoInviato
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML AggiornaContrattoInviato(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class AggiornaContrattoInviatoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.AggiornaContrattoInviato
{
    
    public AggiornaContrattoInviatoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIENTE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_ENTE");
    private static final javax.xml.namespace.QName IDAPPALTO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_APPALTO");
    private static final javax.xml.namespace.QName CIG$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CIG");
    private static final javax.xml.namespace.QName IMPORTOLIQUIDATO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_LIQUIDATO");
    
    
    /**
     * Gets the "DATI_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte getDATIENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().find_element_user(DATIENTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_ENTE" element
     */
    public void setDATIENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte datiente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().find_element_user(DATIENTE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().add_element_user(DATIENTE$0);
            }
            target.set(datiente);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte addNewDATIENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().add_element_user(DATIENTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "ID_APPALTO" element
     */
    public java.lang.String getIDAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_APPALTO" element
     */
    public org.apache.xmlbeans.XmlString xgetIDAPPALTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_APPALTO" element
     */
    public void setIDAPPALTO(java.lang.String idappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDAPPALTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDAPPALTO$2);
            }
            target.setStringValue(idappalto);
        }
    }
    
    /**
     * Sets (as xml) the "ID_APPALTO" element
     */
    public void xsetIDAPPALTO(org.apache.xmlbeans.XmlString idappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDAPPALTO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDAPPALTO$2);
            }
            target.set(idappalto);
        }
    }
    
    /**
     * Gets the "CIG" element
     */
    public java.lang.String getCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG" element
     */
    public org.apache.xmlbeans.XmlString xgetCIG()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIG$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CIG" element
     */
    public void setCIG(java.lang.String cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIG$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIG$4);
            }
            target.setStringValue(cig);
        }
    }
    
    /**
     * Sets (as xml) the "CIG" element
     */
    public void xsetCIG(org.apache.xmlbeans.XmlString cig)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIG$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIG$4);
            }
            target.set(cig);
        }
    }
    
    /**
     * Gets the "IMPORTO_LIQUIDATO" element
     */
    public double getIMPORTOLIQUIDATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLIQUIDATO$6, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_LIQUIDATO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOLIQUIDATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLIQUIDATO$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_LIQUIDATO" element
     */
    public void setIMPORTOLIQUIDATO(double importoliquidato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLIQUIDATO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOLIQUIDATO$6);
            }
            target.setDoubleValue(importoliquidato);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_LIQUIDATO" element
     */
    public void xsetIMPORTOLIQUIDATO(org.apache.xmlbeans.XmlDouble importoliquidato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLIQUIDATO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOLIQUIDATO$6);
            }
            target.set(importoliquidato);
        }
    }
}
