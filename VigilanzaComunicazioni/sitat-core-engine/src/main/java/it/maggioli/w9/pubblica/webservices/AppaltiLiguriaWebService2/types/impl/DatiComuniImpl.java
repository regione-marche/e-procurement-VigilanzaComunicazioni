/*
 * XML Type:  DatiComuni
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiComuni(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiComuniImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni
{
    
    public DatiComuniImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CODICEUFFICIO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_UFFICIO");
    private static final javax.xml.namespace.QName DESCRUFFICIO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DESCR_UFFICIO");
    private static final javax.xml.namespace.QName IMPORTOLIQUIDATO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_LIQUIDATO");
    private static final javax.xml.namespace.QName OGGETTOGARA$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "OGGETTO_GARA");
    private static final javax.xml.namespace.QName IDGARA$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_GARA");
    private static final javax.xml.namespace.QName FLAGIMPORTOGARADISP$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_IMPORTO_GARA_DISP");
    private static final javax.xml.namespace.QName IMPORTOGARA$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "IMPORTO_GARA");
    private static final javax.xml.namespace.QName NUMLOTTI$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NUM_LOTTI");
    private static final javax.xml.namespace.QName FLAGENTE$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_ENTE");
    private static final javax.xml.namespace.QName MODOINDIZIONE$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MODO_INDIZIONE");
    private static final javax.xml.namespace.QName MODOREALIZZAZIONE$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MODO_REALIZZAZIONE");
    private static final javax.xml.namespace.QName CIGAQ$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CIG_AQ");
    private static final javax.xml.namespace.QName FLAGSAAGENTE$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_SA_AGENTE");
    private static final javax.xml.namespace.QName IDTIPOLOGIASA$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_TIPOLOGIA_SA");
    private static final javax.xml.namespace.QName DENAMMAGENTE$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DEN_AMM_AGENTE");
    private static final javax.xml.namespace.QName CFAMMAGENTE$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CF_AMM_AGENTE");
    private static final javax.xml.namespace.QName TIPOLOGIAPROCEDURA$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TIPOLOGIA_PROCEDURA");
    private static final javax.xml.namespace.QName FLAGCENTRALESTIPULA$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_CENTRALE_STIPULA");
    private static final javax.xml.namespace.QName DATAGURIGARA$36 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_GURI_GARA");
    private static final javax.xml.namespace.QName DATASCADENZAGARA$38 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATA_SCADENZA_GARA");
    private static final javax.xml.namespace.QName CAMGARA$40 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CAM_GARA");
    private static final javax.xml.namespace.QName SISMAGARA$42 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SISMA_GARA");
    private static final javax.xml.namespace.QName INDSEDEGARA$44 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "INDSEDE_GARA");
    private static final javax.xml.namespace.QName COMSEDEGARA$46 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COMSEDE_GARA");
    private static final javax.xml.namespace.QName PROSEDEGARA$48 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PROSEDE_GARA");
    
    
    /**
     * Gets the "CODICE_UFFICIO" element
     */
    public java.lang.String getCODICEUFFICIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUFFICIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_UFFICIO" element
     */
    public org.apache.xmlbeans.XmlString xgetCODICEUFFICIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEUFFICIO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_UFFICIO" element
     */
    public void setCODICEUFFICIO(java.lang.String codiceufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEUFFICIO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEUFFICIO$0);
            }
            target.setStringValue(codiceufficio);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_UFFICIO" element
     */
    public void xsetCODICEUFFICIO(org.apache.xmlbeans.XmlString codiceufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEUFFICIO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEUFFICIO$0);
            }
            target.set(codiceufficio);
        }
    }
    
    /**
     * Gets the "DESCR_UFFICIO" element
     */
    public java.lang.String getDESCRUFFICIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRUFFICIO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DESCR_UFFICIO" element
     */
    public org.apache.xmlbeans.XmlString xgetDESCRUFFICIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRUFFICIO$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "DESCR_UFFICIO" element
     */
    public void setDESCRUFFICIO(java.lang.String descrufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRUFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRUFFICIO$2);
            }
            target.setStringValue(descrufficio);
        }
    }
    
    /**
     * Sets (as xml) the "DESCR_UFFICIO" element
     */
    public void xsetDESCRUFFICIO(org.apache.xmlbeans.XmlString descrufficio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRUFFICIO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRUFFICIO$2);
            }
            target.set(descrufficio);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLIQUIDATO$4, 0);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLIQUIDATO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_LIQUIDATO" element
     */
    public boolean isSetIMPORTOLIQUIDATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOLIQUIDATO$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOLIQUIDATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOLIQUIDATO$4);
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
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOLIQUIDATO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOLIQUIDATO$4);
            }
            target.set(importoliquidato);
        }
    }
    
    /**
     * Unsets the "IMPORTO_LIQUIDATO" element
     */
    public void unsetIMPORTOLIQUIDATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOLIQUIDATO$4, 0);
        }
    }
    
    /**
     * Gets the "OGGETTO_GARA" element
     */
    public java.lang.String getOGGETTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OGGETTOGARA$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OGGETTO_GARA" element
     */
    public org.apache.xmlbeans.XmlString xgetOGGETTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OGGETTOGARA$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OGGETTO_GARA" element
     */
    public void setOGGETTOGARA(java.lang.String oggettogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OGGETTOGARA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OGGETTOGARA$6);
            }
            target.setStringValue(oggettogara);
        }
    }
    
    /**
     * Sets (as xml) the "OGGETTO_GARA" element
     */
    public void xsetOGGETTOGARA(org.apache.xmlbeans.XmlString oggettogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OGGETTOGARA$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OGGETTOGARA$6);
            }
            target.set(oggettogara);
        }
    }
    
    /**
     * Gets the "ID_GARA" element
     */
    public java.lang.String getIDGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGARA$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GARA" element
     */
    public org.apache.xmlbeans.XmlString xgetIDGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDGARA$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ID_GARA" element
     */
    public void setIDGARA(java.lang.String idgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGARA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDGARA$8);
            }
            target.setStringValue(idgara);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GARA" element
     */
    public void xsetIDGARA(org.apache.xmlbeans.XmlString idgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDGARA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDGARA$8);
            }
            target.set(idgara);
        }
    }
    
    /**
     * Gets the "FLAG_IMPORTO_GARA_DISP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGIMPORTOGARADISP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTOGARADISP$10, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_IMPORTO_GARA_DISP" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGIMPORTOGARADISP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGIMPORTOGARADISP$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_IMPORTO_GARA_DISP" element
     */
    public boolean isSetFLAGIMPORTOGARADISP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGIMPORTOGARADISP$10) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_IMPORTO_GARA_DISP" element
     */
    public void setFLAGIMPORTOGARADISP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagimportogaradisp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGIMPORTOGARADISP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGIMPORTOGARADISP$10);
            }
            target.setEnumValue(flagimportogaradisp);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_IMPORTO_GARA_DISP" element
     */
    public void xsetFLAGIMPORTOGARADISP(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagimportogaradisp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGIMPORTOGARADISP$10, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGIMPORTOGARADISP$10);
            }
            target.set(flagimportogaradisp);
        }
    }
    
    /**
     * Unsets the "FLAG_IMPORTO_GARA_DISP" element
     */
    public void unsetFLAGIMPORTOGARADISP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGIMPORTOGARADISP$10, 0);
        }
    }
    
    /**
     * Gets the "IMPORTO_GARA" element
     */
    public double getIMPORTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOGARA$12, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "IMPORTO_GARA" element
     */
    public org.apache.xmlbeans.XmlDouble xgetIMPORTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOGARA$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "IMPORTO_GARA" element
     */
    public boolean isSetIMPORTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMPORTOGARA$12) != 0;
        }
    }
    
    /**
     * Sets the "IMPORTO_GARA" element
     */
    public void setIMPORTOGARA(double importogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMPORTOGARA$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMPORTOGARA$12);
            }
            target.setDoubleValue(importogara);
        }
    }
    
    /**
     * Sets (as xml) the "IMPORTO_GARA" element
     */
    public void xsetIMPORTOGARA(org.apache.xmlbeans.XmlDouble importogara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(IMPORTOGARA$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(IMPORTOGARA$12);
            }
            target.set(importogara);
        }
    }
    
    /**
     * Unsets the "IMPORTO_GARA" element
     */
    public void unsetIMPORTOGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMPORTOGARA$12, 0);
        }
    }
    
    /**
     * Gets the "NUM_LOTTI" element
     */
    public long getNUMLOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMLOTTI$14, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "NUM_LOTTI" element
     */
    public org.apache.xmlbeans.XmlLong xgetNUMLOTTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMLOTTI$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NUM_LOTTI" element
     */
    public void setNUMLOTTI(long numlotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMLOTTI$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMLOTTI$14);
            }
            target.setLongValue(numlotti);
        }
    }
    
    /**
     * Sets (as xml) the "NUM_LOTTI" element
     */
    public void xsetNUMLOTTI(org.apache.xmlbeans.XmlLong numlotti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(NUMLOTTI$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(NUMLOTTI$14);
            }
            target.set(numlotti);
        }
    }
    
    /**
     * Gets the "FLAG_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType.Enum getFLAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGENTE$16, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType xgetFLAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType)get_store().find_element_user(FLAGENTE$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_ENTE" element
     */
    public void setFLAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType.Enum flagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGENTE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGENTE$16);
            }
            target.setEnumValue(flagente);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_ENTE" element
     */
    public void xsetFLAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType flagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType)get_store().find_element_user(FLAGENTE$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSOType)get_store().add_element_user(FLAGENTE$16);
            }
            target.set(flagente);
        }
    }
    
    /**
     * Gets the "MODO_INDIZIONE" element
     */
    public java.lang.String getMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOINDIZIONE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODO_INDIZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOINDIZIONE$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "MODO_INDIZIONE" element
     */
    public boolean isSetMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MODOINDIZIONE$18) != 0;
        }
    }
    
    /**
     * Sets the "MODO_INDIZIONE" element
     */
    public void setMODOINDIZIONE(java.lang.String modoindizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOINDIZIONE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODOINDIZIONE$18);
            }
            target.setStringValue(modoindizione);
        }
    }
    
    /**
     * Sets (as xml) the "MODO_INDIZIONE" element
     */
    public void xsetMODOINDIZIONE(org.apache.xmlbeans.XmlString modoindizione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOINDIZIONE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODOINDIZIONE$18);
            }
            target.set(modoindizione);
        }
    }
    
    /**
     * Unsets the "MODO_INDIZIONE" element
     */
    public void unsetMODOINDIZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MODOINDIZIONE$18, 0);
        }
    }
    
    /**
     * Gets the "MODO_REALIZZAZIONE" element
     */
    public java.lang.String getMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOREALIZZAZIONE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MODO_REALIZZAZIONE" element
     */
    public org.apache.xmlbeans.XmlString xgetMODOREALIZZAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOREALIZZAZIONE$20, 0);
            return target;
        }
    }
    
    /**
     * Sets the "MODO_REALIZZAZIONE" element
     */
    public void setMODOREALIZZAZIONE(java.lang.String modorealizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODOREALIZZAZIONE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODOREALIZZAZIONE$20);
            }
            target.setStringValue(modorealizzazione);
        }
    }
    
    /**
     * Sets (as xml) the "MODO_REALIZZAZIONE" element
     */
    public void xsetMODOREALIZZAZIONE(org.apache.xmlbeans.XmlString modorealizzazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODOREALIZZAZIONE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODOREALIZZAZIONE$20);
            }
            target.set(modorealizzazione);
        }
    }
    
    /**
     * Gets the "CIG_AQ" element
     */
    public java.lang.String getCIGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIGAQ$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIG_AQ" element
     */
    public org.apache.xmlbeans.XmlString xgetCIGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIGAQ$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "CIG_AQ" element
     */
    public boolean isSetCIGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIGAQ$22) != 0;
        }
    }
    
    /**
     * Sets the "CIG_AQ" element
     */
    public void setCIGAQ(java.lang.String cigaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIGAQ$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIGAQ$22);
            }
            target.setStringValue(cigaq);
        }
    }
    
    /**
     * Sets (as xml) the "CIG_AQ" element
     */
    public void xsetCIGAQ(org.apache.xmlbeans.XmlString cigaq)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIGAQ$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIGAQ$22);
            }
            target.set(cigaq);
        }
    }
    
    /**
     * Unsets the "CIG_AQ" element
     */
    public void unsetCIGAQ()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIGAQ$22, 0);
        }
    }
    
    /**
     * Gets the "FLAG_SA_AGENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGSAAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSAAGENTE$24, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_SA_AGENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGSAAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSAAGENTE$24, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FLAG_SA_AGENTE" element
     */
    public void setFLAGSAAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagsaagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGSAAGENTE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGSAAGENTE$24);
            }
            target.setEnumValue(flagsaagente);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_SA_AGENTE" element
     */
    public void xsetFLAGSAAGENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagsaagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGSAAGENTE$24, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGSAAGENTE$24);
            }
            target.set(flagsaagente);
        }
    }
    
    /**
     * Gets the "ID_TIPOLOGIA_SA" element
     */
    public java.lang.String getIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDTIPOLOGIASA$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_TIPOLOGIA_SA" element
     */
    public org.apache.xmlbeans.XmlString xgetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDTIPOLOGIASA$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_TIPOLOGIA_SA" element
     */
    public boolean isSetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDTIPOLOGIASA$26) != 0;
        }
    }
    
    /**
     * Sets the "ID_TIPOLOGIA_SA" element
     */
    public void setIDTIPOLOGIASA(java.lang.String idtipologiasa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDTIPOLOGIASA$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDTIPOLOGIASA$26);
            }
            target.setStringValue(idtipologiasa);
        }
    }
    
    /**
     * Sets (as xml) the "ID_TIPOLOGIA_SA" element
     */
    public void xsetIDTIPOLOGIASA(org.apache.xmlbeans.XmlString idtipologiasa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(IDTIPOLOGIASA$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(IDTIPOLOGIASA$26);
            }
            target.set(idtipologiasa);
        }
    }
    
    /**
     * Unsets the "ID_TIPOLOGIA_SA" element
     */
    public void unsetIDTIPOLOGIASA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDTIPOLOGIASA$26, 0);
        }
    }
    
    /**
     * Gets the "DEN_AMM_AGENTE" element
     */
    public java.lang.String getDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENAMMAGENTE$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DEN_AMM_AGENTE" element
     */
    public org.apache.xmlbeans.XmlString xgetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENAMMAGENTE$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "DEN_AMM_AGENTE" element
     */
    public boolean isSetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENAMMAGENTE$28) != 0;
        }
    }
    
    /**
     * Sets the "DEN_AMM_AGENTE" element
     */
    public void setDENAMMAGENTE(java.lang.String denammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENAMMAGENTE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENAMMAGENTE$28);
            }
            target.setStringValue(denammagente);
        }
    }
    
    /**
     * Sets (as xml) the "DEN_AMM_AGENTE" element
     */
    public void xsetDENAMMAGENTE(org.apache.xmlbeans.XmlString denammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENAMMAGENTE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENAMMAGENTE$28);
            }
            target.set(denammagente);
        }
    }
    
    /**
     * Unsets the "DEN_AMM_AGENTE" element
     */
    public void unsetDENAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENAMMAGENTE$28, 0);
        }
    }
    
    /**
     * Gets the "CF_AMM_AGENTE" element
     */
    public java.lang.String getCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CFAMMAGENTE$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_AMM_AGENTE" element
     */
    public org.apache.xmlbeans.XmlString xgetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CFAMMAGENTE$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "CF_AMM_AGENTE" element
     */
    public boolean isSetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CFAMMAGENTE$30) != 0;
        }
    }
    
    /**
     * Sets the "CF_AMM_AGENTE" element
     */
    public void setCFAMMAGENTE(java.lang.String cfammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CFAMMAGENTE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CFAMMAGENTE$30);
            }
            target.setStringValue(cfammagente);
        }
    }
    
    /**
     * Sets (as xml) the "CF_AMM_AGENTE" element
     */
    public void xsetCFAMMAGENTE(org.apache.xmlbeans.XmlString cfammagente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CFAMMAGENTE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CFAMMAGENTE$30);
            }
            target.set(cfammagente);
        }
    }
    
    /**
     * Unsets the "CF_AMM_AGENTE" element
     */
    public void unsetCFAMMAGENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CFAMMAGENTE$30, 0);
        }
    }
    
    /**
     * Gets the "TIPOLOGIA_PROCEDURA" element
     */
    public java.lang.String getTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIAPROCEDURA$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPOLOGIA_PROCEDURA" element
     */
    public org.apache.xmlbeans.XmlString xgetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIAPROCEDURA$32, 0);
            return target;
        }
    }
    
    /**
     * True if has "TIPOLOGIA_PROCEDURA" element
     */
    public boolean isSetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIPOLOGIAPROCEDURA$32) != 0;
        }
    }
    
    /**
     * Sets the "TIPOLOGIA_PROCEDURA" element
     */
    public void setTIPOLOGIAPROCEDURA(java.lang.String tipologiaprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIAPROCEDURA$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOLOGIAPROCEDURA$32);
            }
            target.setStringValue(tipologiaprocedura);
        }
    }
    
    /**
     * Sets (as xml) the "TIPOLOGIA_PROCEDURA" element
     */
    public void xsetTIPOLOGIAPROCEDURA(org.apache.xmlbeans.XmlString tipologiaprocedura)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIAPROCEDURA$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TIPOLOGIAPROCEDURA$32);
            }
            target.set(tipologiaprocedura);
        }
    }
    
    /**
     * Unsets the "TIPOLOGIA_PROCEDURA" element
     */
    public void unsetTIPOLOGIAPROCEDURA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIPOLOGIAPROCEDURA$32, 0);
        }
    }
    
    /**
     * Gets the "FLAG_CENTRALE_STIPULA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGCENTRALESTIPULA$34, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_CENTRALE_STIPULA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGCENTRALESTIPULA$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_CENTRALE_STIPULA" element
     */
    public boolean isSetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGCENTRALESTIPULA$34) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_CENTRALE_STIPULA" element
     */
    public void setFLAGCENTRALESTIPULA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum flagcentralestipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGCENTRALESTIPULA$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGCENTRALESTIPULA$34);
            }
            target.setEnumValue(flagcentralestipula);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_CENTRALE_STIPULA" element
     */
    public void xsetFLAGCENTRALESTIPULA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType flagcentralestipula)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(FLAGCENTRALESTIPULA$34, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(FLAGCENTRALESTIPULA$34);
            }
            target.set(flagcentralestipula);
        }
    }
    
    /**
     * Unsets the "FLAG_CENTRALE_STIPULA" element
     */
    public void unsetFLAGCENTRALESTIPULA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGCENTRALESTIPULA$34, 0);
        }
    }
    
    /**
     * Gets the "DATA_GURI_GARA" element
     */
    public java.util.Calendar getDATAGURIGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGURIGARA$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_GURI_GARA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATAGURIGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGURIGARA$36, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_GURI_GARA" element
     */
    public boolean isSetDATAGURIGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATAGURIGARA$36) != 0;
        }
    }
    
    /**
     * Sets the "DATA_GURI_GARA" element
     */
    public void setDATAGURIGARA(java.util.Calendar datagurigara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATAGURIGARA$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATAGURIGARA$36);
            }
            target.setCalendarValue(datagurigara);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_GURI_GARA" element
     */
    public void xsetDATAGURIGARA(org.apache.xmlbeans.XmlDateTime datagurigara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATAGURIGARA$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATAGURIGARA$36);
            }
            target.set(datagurigara);
        }
    }
    
    /**
     * Unsets the "DATA_GURI_GARA" element
     */
    public void unsetDATAGURIGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATAGURIGARA$36, 0);
        }
    }
    
    /**
     * Gets the "DATA_SCADENZA_GARA" element
     */
    public java.util.Calendar getDATASCADENZAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZAGARA$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DATA_SCADENZA_GARA" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDATASCADENZAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZAGARA$38, 0);
            return target;
        }
    }
    
    /**
     * True if has "DATA_SCADENZA_GARA" element
     */
    public boolean isSetDATASCADENZAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATASCADENZAGARA$38) != 0;
        }
    }
    
    /**
     * Sets the "DATA_SCADENZA_GARA" element
     */
    public void setDATASCADENZAGARA(java.util.Calendar datascadenzagara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASCADENZAGARA$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATASCADENZAGARA$38);
            }
            target.setCalendarValue(datascadenzagara);
        }
    }
    
    /**
     * Sets (as xml) the "DATA_SCADENZA_GARA" element
     */
    public void xsetDATASCADENZAGARA(org.apache.xmlbeans.XmlDateTime datascadenzagara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATASCADENZAGARA$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATASCADENZAGARA$38);
            }
            target.set(datascadenzagara);
        }
    }
    
    /**
     * Unsets the "DATA_SCADENZA_GARA" element
     */
    public void unsetDATASCADENZAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATASCADENZAGARA$38, 0);
        }
    }
    
    /**
     * Gets the "CAM_GARA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getCAMGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAMGARA$40, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CAM_GARA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetCAMGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(CAMGARA$40, 0);
            return target;
        }
    }
    
    /**
     * True if has "CAM_GARA" element
     */
    public boolean isSetCAMGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAMGARA$40) != 0;
        }
    }
    
    /**
     * Sets the "CAM_GARA" element
     */
    public void setCAMGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum camgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAMGARA$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAMGARA$40);
            }
            target.setEnumValue(camgara);
        }
    }
    
    /**
     * Sets (as xml) the "CAM_GARA" element
     */
    public void xsetCAMGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType camgara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(CAMGARA$40, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(CAMGARA$40);
            }
            target.set(camgara);
        }
    }
    
    /**
     * Unsets the "CAM_GARA" element
     */
    public void unsetCAMGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAMGARA$40, 0);
        }
    }
    
    /**
     * Gets the "SISMA_GARA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getSISMAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SISMAGARA$42, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SISMA_GARA" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetSISMAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SISMAGARA$42, 0);
            return target;
        }
    }
    
    /**
     * True if has "SISMA_GARA" element
     */
    public boolean isSetSISMAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SISMAGARA$42) != 0;
        }
    }
    
    /**
     * Sets the "SISMA_GARA" element
     */
    public void setSISMAGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum sismagara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SISMAGARA$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SISMAGARA$42);
            }
            target.setEnumValue(sismagara);
        }
    }
    
    /**
     * Sets (as xml) the "SISMA_GARA" element
     */
    public void xsetSISMAGARA(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType sismagara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(SISMAGARA$42, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(SISMAGARA$42);
            }
            target.set(sismagara);
        }
    }
    
    /**
     * Unsets the "SISMA_GARA" element
     */
    public void unsetSISMAGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SISMAGARA$42, 0);
        }
    }
    
    /**
     * Gets the "INDSEDE_GARA" element
     */
    public java.lang.String getINDSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDSEDEGARA$44, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INDSEDE_GARA" element
     */
    public org.apache.xmlbeans.XmlString xgetINDSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDSEDEGARA$44, 0);
            return target;
        }
    }
    
    /**
     * True if has "INDSEDE_GARA" element
     */
    public boolean isSetINDSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDSEDEGARA$44) != 0;
        }
    }
    
    /**
     * Sets the "INDSEDE_GARA" element
     */
    public void setINDSEDEGARA(java.lang.String indsedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDSEDEGARA$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDSEDEGARA$44);
            }
            target.setStringValue(indsedegara);
        }
    }
    
    /**
     * Sets (as xml) the "INDSEDE_GARA" element
     */
    public void xsetINDSEDEGARA(org.apache.xmlbeans.XmlString indsedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDSEDEGARA$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDSEDEGARA$44);
            }
            target.set(indsedegara);
        }
    }
    
    /**
     * Unsets the "INDSEDE_GARA" element
     */
    public void unsetINDSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDSEDEGARA$44, 0);
        }
    }
    
    /**
     * Gets the "COMSEDE_GARA" element
     */
    public java.lang.String getCOMSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMSEDEGARA$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COMSEDE_GARA" element
     */
    public org.apache.xmlbeans.XmlString xgetCOMSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMSEDEGARA$46, 0);
            return target;
        }
    }
    
    /**
     * True if has "COMSEDE_GARA" element
     */
    public boolean isSetCOMSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMSEDEGARA$46) != 0;
        }
    }
    
    /**
     * Sets the "COMSEDE_GARA" element
     */
    public void setCOMSEDEGARA(java.lang.String comsedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMSEDEGARA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMSEDEGARA$46);
            }
            target.setStringValue(comsedegara);
        }
    }
    
    /**
     * Sets (as xml) the "COMSEDE_GARA" element
     */
    public void xsetCOMSEDEGARA(org.apache.xmlbeans.XmlString comsedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMSEDEGARA$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMSEDEGARA$46);
            }
            target.set(comsedegara);
        }
    }
    
    /**
     * Unsets the "COMSEDE_GARA" element
     */
    public void unsetCOMSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMSEDEGARA$46, 0);
        }
    }
    
    /**
     * Gets the "PROSEDE_GARA" element
     */
    public java.lang.String getPROSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROSEDEGARA$48, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROSEDE_GARA" element
     */
    public org.apache.xmlbeans.XmlString xgetPROSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROSEDEGARA$48, 0);
            return target;
        }
    }
    
    /**
     * True if has "PROSEDE_GARA" element
     */
    public boolean isSetPROSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROSEDEGARA$48) != 0;
        }
    }
    
    /**
     * Sets the "PROSEDE_GARA" element
     */
    public void setPROSEDEGARA(java.lang.String prosedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROSEDEGARA$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROSEDEGARA$48);
            }
            target.setStringValue(prosedegara);
        }
    }
    
    /**
     * Sets (as xml) the "PROSEDE_GARA" element
     */
    public void xsetPROSEDEGARA(org.apache.xmlbeans.XmlString prosedegara)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROSEDEGARA$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROSEDEGARA$48);
            }
            target.set(prosedegara);
        }
    }
    
    /**
     * Unsets the "PROSEDE_GARA" element
     */
    public void unsetPROSEDEGARA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROSEDEGARA$48, 0);
        }
    }
}
