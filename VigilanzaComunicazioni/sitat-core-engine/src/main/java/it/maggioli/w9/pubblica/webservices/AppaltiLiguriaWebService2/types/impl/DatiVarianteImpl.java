/*
 * XML Type:  DatiVariante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiVariante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiVarianteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante
{
    
    public DatiVarianteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LISTAMOTIVIVARIANTE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_MOTIVI_VARIANTE");
    private static final javax.xml.namespace.QName FLAGVARIANTE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_VARIANTE");
    private static final javax.xml.namespace.QName QUINTOOBBLIGO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "QUINTO_OBBLIGO");
    private static final javax.xml.namespace.QName FLAGIMPORTI$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_IMPORTI");
    private static final javax.xml.namespace.QName FLAGSICUREZZA$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SICUREZZA");
    private static final javax.xml.namespace.QName DATAVERBAPPR$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_VERB_APPR");
    private static final javax.xml.namespace.QName ALTREMOTIVAZIONI$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ALTRE_MOTIVAZIONI");
    private static final javax.xml.namespace.QName IMPRIDETLAVORI$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_RIDET_LAVORI");
    private static final javax.xml.namespace.QName IMPRIDETSERVIZI$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_RIDET_SERVIZI");
    private static final javax.xml.namespace.QName IMPRIDETFORNIT$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_RIDET_FORNIT");
    private static final javax.xml.namespace.QName IMPSICUREZZA$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_SICUREZZA");
    private static final javax.xml.namespace.QName IMPPROGETTAZIONE$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_PROGETTAZIONE");
    private static final javax.xml.namespace.QName IMPDISPOSIZIONE$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMP_DISPOSIZIONE");
    private static final javax.xml.namespace.QName DATAATTOAGGIUNTIVO$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_ATTO_AGGIUNTIVO");
    private static final javax.xml.namespace.QName NUMGIORNIPROROGA$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_GIORNI_PROROGA");
    private static final javax.xml.namespace.QName IMPORTOSUBTOTALE$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_SUBTOTALE");
    private static final javax.xml.namespace.QName IMPORTORIDETERMINATO$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_RIDETERMINATO");
    private static final javax.xml.namespace.QName IMPORTOCOMPLESSIVO$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_COMPLESSIVO");
    
    
    /**
     * Gets the "LISTA_MOTIVI_VARIANTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante getLISTAMOTIVIVARIANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante)get_store().find_element_user(LISTAMOTIVIVARIANTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "LISTA_MOTIVI_VARIANTE" element
     */
    public void setLISTAMOTIVIVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante listamotivivariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante)get_store().find_element_user(LISTAMOTIVIVARIANTE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante)get_store().add_element_user(LISTAMOTIVIVARIANTE$0);
            }
            target.set(listamotivivariante);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_MOTIVI_VARIANTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante addNewLISTAMOTIVIVARIANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaMotiviVariante)get_store().add_element_user(LISTAMOTIVIVARIANTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "FLAG_VARIANTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType.Enum getFLAGVARIANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGVARIANTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_VARIANTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType xgetFLAGVARIANTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType)get_store().find_element_user(FLAGVARIANTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_VARIANTE" element
     */
    public void setFLAGVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType.Enum flagvariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGVARIANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGVARIANTE$2);
            }
            target.setEnumValue(flagvariante);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_VARIANTE" element
     */
    public void xsetFLAGVARIANTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType flagvariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType)get_store().find_element_user(FLAGVARIANTE$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagVarianteType)get_store().add_element_user(FLAGVARIANTE$2);
            }
            target.set(flagvariante);
        }
    }
    
    /**
     * Gets the "QUINTO_OBBLIGO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getQUINTOOBBLIGO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUINTOOBBLIGO$4, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "QUINTO_OBBLIGO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetQUINTOOBBLIGO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(QUINTOOBBLIGO$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "QUINTO_OBBLIGO" element
     */
    public void setQUINTOOBBLIGO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum quintoobbligo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUINTOOBBLIGO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUINTOOBBLIGO$4);
            }
            target.setEnumValue(quintoobbligo);
        }
    }
    
    /**
     * Sets (as xml) the "QUINTO_OBBLIGO" element
     */
    public void xsetQUINTOOBBLIGO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType quintoobbligo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(QUINTOOBBLIGO$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(QUINTOOBBLIGO$4);
            }
            target.set(quintoobbligo);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$6, 0);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTI$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGIMPORTI$6);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGIMPORTI$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGIMPORTI$6);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$8, 0);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSICUREZZA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSICUREZZA$8);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().find_element_user(FLAGSICUREZZA$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagImportiType)get_store().add_element_user(FLAGSICUREZZA$8);
            }
            target.set(flagsicurezza);
        }
    }
    
    /**
     * Gets the "DATA_VERB_APPR" element
     */
    public java.util.Calendar getDATAVERBAPPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBAPPR$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_VERB_APPR" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAVERBAPPR()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBAPPR$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DATA_VERB_APPR" element
     */
    public void setDATAVERBAPPR(java.util.Calendar dataverbappr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAVERBAPPR$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAVERBAPPR$10);
            }
            target.setCalendarValue(dataverbappr);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_VERB_APPR" element
     */
    public void xsetDATAVERBAPPR(org.apache.xmlbeans.XmlDateTime dataverbappr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAVERBAPPR$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAVERBAPPR$10);
            }
            target.set(dataverbappr);
        }
    }
    
    /**
     * Gets the "ALTRE_MOTIVAZIONI" element
     */
    public java.lang.String getALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALTREMOTIVAZIONI$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALTRE_MOTIVAZIONI" element
     */
    public org.apache.xmlbeans.XmlString xgetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTREMOTIVAZIONI$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "ALTRE_MOTIVAZIONI" element
     */
    public boolean isSetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ALTREMOTIVAZIONI$12) != 0;
        }
    }
    
    /**
     * Sets the "ALTRE_MOTIVAZIONI" element
     */
    public void setALTREMOTIVAZIONI(java.lang.String altremotivazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALTREMOTIVAZIONI$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ALTREMOTIVAZIONI$12);
            }
            target.setStringValue(altremotivazioni);
        }
    }
    
    /**
     * Sets (as xml) the "ALTRE_MOTIVAZIONI" element
     */
    public void xsetALTREMOTIVAZIONI(org.apache.xmlbeans.XmlString altremotivazioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTREMOTIVAZIONI$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ALTREMOTIVAZIONI$12);
            }
            target.set(altremotivazioni);
        }
    }
    
    /**
     * Unsets the "ALTRE_MOTIVAZIONI" element
     */
    public void unsetALTREMOTIVAZIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ALTREMOTIVAZIONI$12, 0);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_LAVORI" element
     */
    public double getIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETLAVORI$14, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_LAVORI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETLAVORI$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_LAVORI" element
     */
    public boolean isSetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPRIDETLAVORI$14) != 0;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_LAVORI" element
     */
    public void setIMPRIDETLAVORI(double impridetlavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETLAVORI$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPRIDETLAVORI$14);
            }
            target.setDoubleValue(impridetlavori);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_LAVORI" element
     */
    public void xsetIMPRIDETLAVORI(org.apache.xmlbeans.XmlDouble impridetlavori)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETLAVORI$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPRIDETLAVORI$14);
            }
            target.set(impridetlavori);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_LAVORI" element
     */
    public void unsetIMPRIDETLAVORI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPRIDETLAVORI$14, 0);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_SERVIZI" element
     */
    public double getIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETSERVIZI$16, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_SERVIZI" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETSERVIZI$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_SERVIZI" element
     */
    public boolean isSetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPRIDETSERVIZI$16) != 0;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_SERVIZI" element
     */
    public void setIMPRIDETSERVIZI(double impridetservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETSERVIZI$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPRIDETSERVIZI$16);
            }
            target.setDoubleValue(impridetservizi);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_SERVIZI" element
     */
    public void xsetIMPRIDETSERVIZI(org.apache.xmlbeans.XmlDouble impridetservizi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETSERVIZI$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPRIDETSERVIZI$16);
            }
            target.set(impridetservizi);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_SERVIZI" element
     */
    public void unsetIMPRIDETSERVIZI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPRIDETSERVIZI$16, 0);
        }
    }
    
    /**
     * Gets the "IMP_RIDET_FORNIT" element
     */
    public double getIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETFORNIT$18, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_RIDET_FORNIT" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETFORNIT$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_RIDET_FORNIT" element
     */
    public boolean isSetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPRIDETFORNIT$18) != 0;
        }
    }
    
    /**
     * Sets the "IMP_RIDET_FORNIT" element
     */
    public void setIMPRIDETFORNIT(double impridetfornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPRIDETFORNIT$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPRIDETFORNIT$18);
            }
            target.setDoubleValue(impridetfornit);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_RIDET_FORNIT" element
     */
    public void xsetIMPRIDETFORNIT(org.apache.xmlbeans.XmlDouble impridetfornit)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPRIDETFORNIT$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPRIDETFORNIT$18);
            }
            target.set(impridetfornit);
        }
    }
    
    /**
     * Unsets the "IMP_RIDET_FORNIT" element
     */
    public void unsetIMPRIDETFORNIT()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPRIDETFORNIT$18, 0);
        }
    }
    
    /**
     * Gets the "IMP_SICUREZZA" element
     */
    public double getIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPSICUREZZA$20, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMP_SICUREZZA" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPSICUREZZA$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_SICUREZZA" element
     */
    public boolean isSetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPSICUREZZA$20) != 0;
        }
    }
    
    /**
     * Sets the "IMP_SICUREZZA" element
     */
    public void setIMPSICUREZZA(double impsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPSICUREZZA$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPSICUREZZA$20);
            }
            target.setDoubleValue(impsicurezza);
        }
    }
    
    /**
     * Sets (as xml) the "IMP_SICUREZZA" element
     */
    public void xsetIMPSICUREZZA(org.apache.xmlbeans.XmlDouble impsicurezza)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPSICUREZZA$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPSICUREZZA$20);
            }
            target.set(impsicurezza);
        }
    }
    
    /**
     * Unsets the "IMP_SICUREZZA" element
     */
    public void unsetIMPSICUREZZA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPSICUREZZA$20, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPPROGETTAZIONE$22, 0);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPPROGETTAZIONE$22, 0);
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
            return get_store().count_elements(IMPPROGETTAZIONE$22) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPPROGETTAZIONE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPPROGETTAZIONE$22);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPPROGETTAZIONE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPPROGETTAZIONE$22);
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
            get_store().remove_element(IMPPROGETTAZIONE$22, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPDISPOSIZIONE$24, 0);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPDISPOSIZIONE$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMP_DISPOSIZIONE" element
     */
    public boolean isSetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPDISPOSIZIONE$24) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPDISPOSIZIONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPDISPOSIZIONE$24);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPDISPOSIZIONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPDISPOSIZIONE$24);
            }
            target.set(impdisposizione);
        }
    }
    
    /**
     * Unsets the "IMP_DISPOSIZIONE" element
     */
    public void unsetIMPDISPOSIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPDISPOSIZIONE$24, 0);
        }
    }
    
    /**
     * Gets the "DATA_ATTO_AGGIUNTIVO" element
     */
    public java.util.Calendar getDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAATTOAGGIUNTIVO$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_ATTO_AGGIUNTIVO" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAATTOAGGIUNTIVO$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_ATTO_AGGIUNTIVO" element
     */
    public boolean isSetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAATTOAGGIUNTIVO$26) != 0;
        }
    }
    
    /**
     * Sets the "DATA_ATTO_AGGIUNTIVO" element
     */
    public void setDATAATTOAGGIUNTIVO(java.util.Calendar dataattoaggiuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAATTOAGGIUNTIVO$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAATTOAGGIUNTIVO$26);
            }
            target.setCalendarValue(dataattoaggiuntivo);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_ATTO_AGGIUNTIVO" element
     */
    public void xsetDATAATTOAGGIUNTIVO(org.apache.xmlbeans.XmlDateTime dataattoaggiuntivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAATTOAGGIUNTIVO$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAATTOAGGIUNTIVO$26);
            }
            target.set(dataattoaggiuntivo);
        }
    }
    
    /**
     * Unsets the "DATA_ATTO_AGGIUNTIVO" element
     */
    public void unsetDATAATTOAGGIUNTIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAATTOAGGIUNTIVO$26, 0);
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
    
    /**
     * Gets the "IMPORTO_SUBTOTALE" element
     */
    public double getIMPORTOSUBTOTALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSUBTOTALE$30, 0);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSUBTOTALE$30, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOSUBTOTALE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOSUBTOTALE$30);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOSUBTOTALE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOSUBTOTALE$30);
            }
            target.set(importosubtotale);
        }
    }
    
    /**
     * Gets the "IMPORTO_RIDETERMINATO" element
     */
    public double getIMPORTORIDETERMINATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTORIDETERMINATO$32, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_RIDETERMINATO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTORIDETERMINATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTORIDETERMINATO$32, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_RIDETERMINATO" element
     */
    public void setIMPORTORIDETERMINATO(double importorideterminato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTORIDETERMINATO$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTORIDETERMINATO$32);
            }
            target.setDoubleValue(importorideterminato);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_RIDETERMINATO" element
     */
    public void xsetIMPORTORIDETERMINATO(org.apache.xmlbeans.XmlDouble importorideterminato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTORIDETERMINATO$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTORIDETERMINATO$32);
            }
            target.set(importorideterminato);
        }
    }
    
    /**
     * Gets the "IMPORTO_COMPLESSIVO" element
     */
    public double getIMPORTOCOMPLESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCOMPLESSIVO$34, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_COMPLESSIVO" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOCOMPLESSIVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCOMPLESSIVO$34, 0);
            return target;
        }
    }
    
    /**
     * Sets the "IMPORTO_COMPLESSIVO" element
     */
    public void setIMPORTOCOMPLESSIVO(double importocomplessivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOCOMPLESSIVO$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOCOMPLESSIVO$34);
            }
            target.setDoubleValue(importocomplessivo);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_COMPLESSIVO" element
     */
    public void xsetIMPORTOCOMPLESSIVO(org.apache.xmlbeans.XmlDouble importocomplessivo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOCOMPLESSIVO$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOCOMPLESSIVO$34);
            }
            target.set(importocomplessivo);
        }
    }
}
