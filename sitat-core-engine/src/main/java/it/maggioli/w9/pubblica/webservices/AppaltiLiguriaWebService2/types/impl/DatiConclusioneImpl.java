/*
 * XML Type:  DatiConclusione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiConclusione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiConclusioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione
{
    
    public DatiConclusioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FLAGINTERRANTICIPATA$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_INTERR_ANTICIPATA");
    private static final javax.xml.namespace.QName IDMOTIVOINTERR$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_MOTIVO_INTERR");
    private static final javax.xml.namespace.QName IDMOTIVORISOL$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_MOTIVO_RISOL");
    private static final javax.xml.namespace.QName DATARISOLUZIONE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_RISOLUZIONE");
    private static final javax.xml.namespace.QName FLAGONERI$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_ONERI");
    private static final javax.xml.namespace.QName ONERIRISOLUZIONE$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ONERI_RISOLUZIONE");
    private static final javax.xml.namespace.QName FLAGPOLIZZA$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_POLIZZA");
    private static final javax.xml.namespace.QName DATAVERBCONSEGNAAVVIO$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_CONSEGNA_AVVIO");
    private static final javax.xml.namespace.QName DATATERMINECONTRATTUALE$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_TERMINE_CONTRATTUALE");
    private static final javax.xml.namespace.QName NUMINFORTUNI$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_INFORTUNI");
    private static final javax.xml.namespace.QName DATAULTIMAZIONE$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ULTIMAZIONE");
    private static final javax.xml.namespace.QName NUMINFPERM$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_INF_PERM");
    private static final javax.xml.namespace.QName NUMINFMORT$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_INF_MORT");
    private static final javax.xml.namespace.QName ORELAVORATE$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ORE_LAVORATE");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_GIORNI_PROROGA");
    
    
    /**
     * Gets the "FLAG_INTERR_ANTICIPATA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGINTERRANTICIPATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGINTERRANTICIPATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_INTERR_ANTICIPATA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGINTERRANTICIPATA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGINTERRANTICIPATA$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_INTERR_ANTICIPATA" element
     */
    public void setFLAGINTERRANTICIPATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flaginterranticipata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGINTERRANTICIPATA$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGINTERRANTICIPATA$0);
            }
            target.setEnumValue(flaginterranticipata);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_INTERR_ANTICIPATA" element
     */
    public void xsetFLAGINTERRANTICIPATA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flaginterranticipata)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGINTERRANTICIPATA$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGINTERRANTICIPATA$0);
            }
            target.set(flaginterranticipata);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_INTERR" element
     */
    public java.lang.String getIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOINTERR$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_INTERR" element
     */
    public org.apache.xmlbeans.XmlString xgetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOINTERR$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_INTERR" element
     */
    public boolean isSetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDMOTIVOINTERR$2) != 0;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_INTERR" element
     */
    public void setIDMOTIVOINTERR(java.lang.String idmotivointerr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVOINTERR$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDMOTIVOINTERR$2);
            }
            target.setStringValue(idmotivointerr);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_INTERR" element
     */
    public void xsetIDMOTIVOINTERR(org.apache.xmlbeans.XmlString idmotivointerr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVOINTERR$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDMOTIVOINTERR$2);
            }
            target.set(idmotivointerr);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_INTERR" element
     */
    public void unsetIDMOTIVOINTERR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDMOTIVOINTERR$2, 0);
        }
    }
    
    /**
     * Gets the "ID_MOTIVO_RISOL" element
     */
    public java.lang.String getIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVORISOL$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_MOTIVO_RISOL" element
     */
    public org.apache.xmlbeans.XmlString xgetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVORISOL$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_MOTIVO_RISOL" element
     */
    public boolean isSetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDMOTIVORISOL$4) != 0;
        }
    }
    
    /**
     * Sets the "ID_MOTIVO_RISOL" element
     */
    public void setIDMOTIVORISOL(java.lang.String idmotivorisol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDMOTIVORISOL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDMOTIVORISOL$4);
            }
            target.setStringValue(idmotivorisol);
        }
    }
    
    /**
     * Sets (as xml) the "ID_MOTIVO_RISOL" element
     */
    public void xsetIDMOTIVORISOL(org.apache.xmlbeans.XmlString idmotivorisol)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDMOTIVORISOL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDMOTIVORISOL$4);
            }
            target.set(idmotivorisol);
        }
    }
    
    /**
     * Unsets the "ID_MOTIVO_RISOL" element
     */
    public void unsetIDMOTIVORISOL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDMOTIVORISOL$4, 0);
        }
    }
    
    /**
     * Gets the "DATA_RISOLUZIONE" element
     */
    public java.util.Calendar getDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATARISOLUZIONE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_RISOLUZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATARISOLUZIONE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_RISOLUZIONE" element
     */
    public boolean isSetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATARISOLUZIONE$6) != 0;
        }
    }
    
    /**
     * Sets the "DATA_RISOLUZIONE" element
     */
    public void setDATARISOLUZIONE(java.util.Calendar datarisoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATARISOLUZIONE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATARISOLUZIONE$6);
            }
            target.setCalendarValue(datarisoluzione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_RISOLUZIONE" element
     */
    public void xsetDATARISOLUZIONE(org.apache.xmlbeans.XmlDateTime datarisoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATARISOLUZIONE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATARISOLUZIONE$6);
            }
            target.set(datarisoluzione);
        }
    }
    
    /**
     * Unsets the "DATA_RISOLUZIONE" element
     */
    public void unsetDATARISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATARISOLUZIONE$6, 0);
        }
    }
    
    /**
     * Gets the "FLAG_ONERI" element
     */
    public java.lang.String getFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGONERI$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ONERI" element
     */
    public org.apache.xmlbeans.XmlString xgetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGONERI$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_ONERI" element
     */
    public boolean isSetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGONERI$8) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_ONERI" element
     */
    public void setFLAGONERI(java.lang.String flagoneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGONERI$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGONERI$8);
            }
            target.setStringValue(flagoneri);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ONERI" element
     */
    public void xsetFLAGONERI(org.apache.xmlbeans.XmlString flagoneri)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGONERI$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLAGONERI$8);
            }
            target.set(flagoneri);
        }
    }
    
    /**
     * Unsets the "FLAG_ONERI" element
     */
    public void unsetFLAGONERI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGONERI$8, 0);
        }
    }
    
    /**
     * Gets the "ONERI_RISOLUZIONE" element
     */
    public double getONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIRISOLUZIONE$10, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ONERI_RISOLUZIONE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIRISOLUZIONE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "ONERI_RISOLUZIONE" element
     */
    public boolean isSetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ONERIRISOLUZIONE$10) != 0;
        }
    }
    
    /**
     * Sets the "ONERI_RISOLUZIONE" element
     */
    public void setONERIRISOLUZIONE(double oneririsoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ONERIRISOLUZIONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ONERIRISOLUZIONE$10);
            }
            target.setDoubleValue(oneririsoluzione);
        }
    }
    
    /**
     * Sets (as xml) the "ONERI_RISOLUZIONE" element
     */
    public void xsetONERIRISOLUZIONE(org.apache.xmlbeans.XmlDouble oneririsoluzione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ONERIRISOLUZIONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ONERIRISOLUZIONE$10);
            }
            target.set(oneririsoluzione);
        }
    }
    
    /**
     * Unsets the "ONERI_RISOLUZIONE" element
     */
    public void unsetONERIRISOLUZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ONERIRISOLUZIONE$10, 0);
        }
    }
    
    /**
     * Gets the "FLAG_POLIZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPOLIZZA$12, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_POLIZZA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGPOLIZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGPOLIZZA$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_POLIZZA" element
     */
    public void setFLAGPOLIZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagpolizza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPOLIZZA$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGPOLIZZA$12);
            }
            target.setEnumValue(flagpolizza);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_POLIZZA" element
     */
    public void xsetFLAGPOLIZZA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagpolizza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGPOLIZZA$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGPOLIZZA$12);
            }
            target.set(flagpolizza);
        }
    }
    
    /**
     * Gets the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    public java.util.Calendar getDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBCONSEGNAAVVIO$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBCONSEGNAAVVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBCONSEGNAAVVIO$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    public void setDATAVERBCONSEGNAAVVIO(java.util.Calendar dataverbconsegnaavvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBCONSEGNAAVVIO$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBCONSEGNAAVVIO$14);
            }
            target.setCalendarValue(dataverbconsegnaavvio);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_CONSEGNA_AVVIO" element
     */
    public void xsetDATAVERBCONSEGNAAVVIO(org.apache.xmlbeans.XmlDateTime dataverbconsegnaavvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBCONSEGNAAVVIO$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBCONSEGNAAVVIO$14);
            }
            target.set(dataverbconsegnaavvio);
        }
    }
    
    /**
     * Gets the "DATA_TERMINE_CONTRATTUALE" element
     */
    public java.util.Calendar getDATATERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINECONTRATTUALE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_TERMINE_CONTRATTUALE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATATERMINECONTRATTUALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINECONTRATTUALE$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_TERMINE_CONTRATTUALE" element
     */
    public void setDATATERMINECONTRATTUALE(java.util.Calendar dataterminecontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATATERMINECONTRATTUALE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATATERMINECONTRATTUALE$16);
            }
            target.setCalendarValue(dataterminecontrattuale);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_TERMINE_CONTRATTUALE" element
     */
    public void xsetDATATERMINECONTRATTUALE(org.apache.xmlbeans.XmlDateTime dataterminecontrattuale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATATERMINECONTRATTUALE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATATERMINECONTRATTUALE$16);
            }
            target.set(dataterminecontrattuale);
        }
    }
    
    /**
     * Gets the "NUM_INFORTUNI" element
     */
    public long getNUMINFORTUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFORTUNI$18, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INFORTUNI" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMINFORTUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFORTUNI$18, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INFORTUNI" element
     */
    public void setNUMINFORTUNI(long numinfortuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFORTUNI$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMINFORTUNI$18);
            }
            target.setLongValue(numinfortuni);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INFORTUNI" element
     */
    public void xsetNUMINFORTUNI(org.apache.xmlbeans.XmlLong numinfortuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFORTUNI$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMINFORTUNI$18);
            }
            target.set(numinfortuni);
        }
    }
    
    /**
     * Gets the "DATA_ULTIMAZIONE" element
     */
    public java.util.Calendar getDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAULTIMAZIONE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ULTIMAZIONE" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAULTIMAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAULTIMAZIONE$20, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_ULTIMAZIONE" element
     */
    public void setDATAULTIMAZIONE(java.util.Calendar dataultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAULTIMAZIONE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAULTIMAZIONE$20);
            }
            target.setCalendarValue(dataultimazione);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ULTIMAZIONE" element
     */
    public void xsetDATAULTIMAZIONE(org.apache.xmlbeans.XmlDateTime dataultimazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAULTIMAZIONE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAULTIMAZIONE$20);
            }
            target.set(dataultimazione);
        }
    }
    
    /**
     * Gets the "NUM_INF_PERM" element
     */
    public long getNUMINFPERM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFPERM$22, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INF_PERM" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMINFPERM()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFPERM$22, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INF_PERM" element
     */
    public void setNUMINFPERM(long numinfperm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFPERM$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMINFPERM$22);
            }
            target.setLongValue(numinfperm);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INF_PERM" element
     */
    public void xsetNUMINFPERM(org.apache.xmlbeans.XmlLong numinfperm)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFPERM$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMINFPERM$22);
            }
            target.set(numinfperm);
        }
    }
    
    /**
     * Gets the "NUM_INF_MORT" element
     */
    public long getNUMINFMORT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFMORT$24, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_INF_MORT" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMINFMORT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFMORT$24, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_INF_MORT" element
     */
    public void setNUMINFMORT(long numinfmort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMINFMORT$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMINFMORT$24);
            }
            target.setLongValue(numinfmort);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_INF_MORT" element
     */
    public void xsetNUMINFMORT(org.apache.xmlbeans.XmlLong numinfmort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMINFMORT$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMINFMORT$24);
            }
            target.set(numinfmort);
        }
    }
    
    /**
     * Gets the "ORE_LAVORATE" element
     */
    public double getORELAVORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORELAVORATE$26, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "ORE_LAVORATE" element
     */
    public org.apache.xmlbeans.XmlDouble xgetORELAVORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORELAVORATE$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "ORE_LAVORATE" element
     */
    public boolean isSetORELAVORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ORELAVORATE$26) != 0;
        }
    }
    
    /**
     * Sets the "ORE_LAVORATE" element
     */
    public void setORELAVORATE(double orelavorate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORELAVORATE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORELAVORATE$26);
            }
            target.setDoubleValue(orelavorate);
        }
    }
    
    /**
     * Sets (as xml) the "ORE_LAVORATE" element
     */
    public void xsetORELAVORATE(org.apache.xmlbeans.XmlDouble orelavorate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(ORELAVORATE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(ORELAVORATE$26);
            }
            target.set(orelavorate);
        }
    }
    
    /**
     * Unsets the "ORE_LAVORATE" element
     */
    public void unsetORELAVORATE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ORELAVORATE$26, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNIPROROGA$28, 0);
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
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNIPROROGA$28, 0);
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
            return get_store().count_elements(NUMGIORNIPROROGA$28) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMGIORNIPROROGA$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMGIORNIPROROGA$28);
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
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMGIORNIPROROGA$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMGIORNIPROROGA$28);
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
            get_store().remove_element(NUMGIORNIPROROGA$28, 0);
        }
    }
}
