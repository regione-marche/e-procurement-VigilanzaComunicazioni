/*
 * XML Type:  DatiAggiudicatari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiAggiudicatari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiAggiudicatariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari
{
    
    public DatiAggiudicatariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INPS$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "INPS");
    private static final javax.xml.namespace.QName MOTIVOASSENZAINPS$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MOTIVO_ASSENZA_INPS");
    private static final javax.xml.namespace.QName DENOMINAZIONE$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DENOMINAZIONE");
    private static final javax.xml.namespace.QName CODICEFISCALE$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_FISCALE");
    private static final javax.xml.namespace.QName PARTITAIVA$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PARTITA_IVA");
    private static final javax.xml.namespace.QName INDIRIZZO$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "INDIRIZZO");
    private static final javax.xml.namespace.QName CIVICO$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CIVICO");
    private static final javax.xml.namespace.QName PROVINCIA$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PROVINCIA");
    private static final javax.xml.namespace.QName CAP$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CAP");
    private static final javax.xml.namespace.QName LOCALITAIMP$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LOCALITA_IMP");
    private static final javax.xml.namespace.QName CITTA$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CITTA");
    private static final javax.xml.namespace.QName CODICESTATO$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_STATO");
    private static final javax.xml.namespace.QName TELEFONO$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TELEFONO");
    private static final javax.xml.namespace.QName FAX$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FAX");
    private static final javax.xml.namespace.QName CELLIMP$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CELL_IMP");
    private static final javax.xml.namespace.QName EMAIL$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "EMAIL");
    private static final javax.xml.namespace.QName PECIMP$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PEC_IMP");
    private static final javax.xml.namespace.QName WEBIMP$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "WEB_IMP");
    private static final javax.xml.namespace.QName NCCIAA$36 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NCCIAA");
    private static final javax.xml.namespace.QName NATURAGIURIDICA$38 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NATURA_GIURIDICA");
    private static final javax.xml.namespace.QName TIPOLOGIASOGGETTO$40 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TIPOLOGIA_SOGGETTO");
    private static final javax.xml.namespace.QName COMUNEESTERO$42 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COMUNE_ESTERO");
    private static final javax.xml.namespace.QName LISTALEGALERAPPRESENTANTE$44 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "listaLegaleRappresentante");
    
    
    /**
     * Gets the "INPS" element
     */
    public java.lang.String getINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INPS" element
     */
    public org.apache.xmlbeans.XmlString xgetINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPS$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "INPS" element
     */
    public boolean isSetINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INPS$0) != 0;
        }
    }
    
    /**
     * Sets the "INPS" element
     */
    public void setINPS(java.lang.String inps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INPS$0);
            }
            target.setStringValue(inps);
        }
    }
    
    /**
     * Sets (as xml) the "INPS" element
     */
    public void xsetINPS(org.apache.xmlbeans.XmlString inps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INPS$0);
            }
            target.set(inps);
        }
    }
    
    /**
     * Unsets the "INPS" element
     */
    public void unsetINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INPS$0, 0);
        }
    }
    
    /**
     * Gets the "MOTIVO_ASSENZA_INPS" element
     */
    public java.lang.String getMOTIVOASSENZAINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOASSENZAINPS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO_ASSENZA_INPS" element
     */
    public org.apache.xmlbeans.XmlString xgetMOTIVOASSENZAINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOASSENZAINPS$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVO_ASSENZA_INPS" element
     */
    public boolean isSetMOTIVOASSENZAINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOTIVOASSENZAINPS$2) != 0;
        }
    }
    
    /**
     * Sets the "MOTIVO_ASSENZA_INPS" element
     */
    public void setMOTIVOASSENZAINPS(java.lang.String motivoassenzainps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOASSENZAINPS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOTIVOASSENZAINPS$2);
            }
            target.setStringValue(motivoassenzainps);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO_ASSENZA_INPS" element
     */
    public void xsetMOTIVOASSENZAINPS(org.apache.xmlbeans.XmlString motivoassenzainps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOASSENZAINPS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOTIVOASSENZAINPS$2);
            }
            target.set(motivoassenzainps);
        }
    }
    
    /**
     * Unsets the "MOTIVO_ASSENZA_INPS" element
     */
    public void unsetMOTIVOASSENZAINPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOTIVOASSENZAINPS$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$4, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONE$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENOMINAZIONE$4);
            }
            target.set(denominazione);
        }
    }
    
    /**
     * Gets the "CODICE_FISCALE" element
     */
    public java.lang.String getCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_FISCALE" element
     */
    public org.apache.xmlbeans.XmlString xgetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_FISCALE" element
     */
    public boolean isSetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICEFISCALE$6) != 0;
        }
    }
    
    /**
     * Sets the "CODICE_FISCALE" element
     */
    public void setCODICEFISCALE(java.lang.String codicefiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$6);
            }
            target.setStringValue(codicefiscale);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_FISCALE" element
     */
    public void xsetCODICEFISCALE(org.apache.xmlbeans.XmlString codicefiscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEFISCALE$6);
            }
            target.set(codicefiscale);
        }
    }
    
    /**
     * Unsets the "CODICE_FISCALE" element
     */
    public void unsetCODICEFISCALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICEFISCALE$6, 0);
        }
    }
    
    /**
     * Gets the "PARTITA_IVA" element
     */
    public java.lang.String getPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PARTITAIVA$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PARTITA_IVA" element
     */
    public org.apache.xmlbeans.XmlString xgetPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PARTITAIVA$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "PARTITA_IVA" element
     */
    public boolean isSetPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PARTITAIVA$8) != 0;
        }
    }
    
    /**
     * Sets the "PARTITA_IVA" element
     */
    public void setPARTITAIVA(java.lang.String partitaiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PARTITAIVA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PARTITAIVA$8);
            }
            target.setStringValue(partitaiva);
        }
    }
    
    /**
     * Sets (as xml) the "PARTITA_IVA" element
     */
    public void xsetPARTITAIVA(org.apache.xmlbeans.XmlString partitaiva)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PARTITAIVA$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PARTITAIVA$8);
            }
            target.set(partitaiva);
        }
    }
    
    /**
     * Unsets the "PARTITA_IVA" element
     */
    public void unsetPARTITAIVA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PARTITAIVA$8, 0);
        }
    }
    
    /**
     * Gets the "INDIRIZZO" element
     */
    public java.lang.String getINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "INDIRIZZO" element
     */
    public org.apache.xmlbeans.XmlString xgetINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "INDIRIZZO" element
     */
    public boolean isSetINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INDIRIZZO$10) != 0;
        }
    }
    
    /**
     * Sets the "INDIRIZZO" element
     */
    public void setINDIRIZZO(java.lang.String indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZO$10);
            }
            target.setStringValue(indirizzo);
        }
    }
    
    /**
     * Sets (as xml) the "INDIRIZZO" element
     */
    public void xsetINDIRIZZO(org.apache.xmlbeans.XmlString indirizzo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDIRIZZO$10);
            }
            target.set(indirizzo);
        }
    }
    
    /**
     * Unsets the "INDIRIZZO" element
     */
    public void unsetINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INDIRIZZO$10, 0);
        }
    }
    
    /**
     * Gets the "CIVICO" element
     */
    public java.lang.String getCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CIVICO" element
     */
    public org.apache.xmlbeans.XmlString xgetCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "CIVICO" element
     */
    public boolean isSetCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CIVICO$12) != 0;
        }
    }
    
    /**
     * Sets the "CIVICO" element
     */
    public void setCIVICO(java.lang.String civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICO$12);
            }
            target.setStringValue(civico);
        }
    }
    
    /**
     * Sets (as xml) the "CIVICO" element
     */
    public void xsetCIVICO(org.apache.xmlbeans.XmlString civico)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIVICO$12);
            }
            target.set(civico);
        }
    }
    
    /**
     * Unsets the "CIVICO" element
     */
    public void unsetCIVICO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CIVICO$12, 0);
        }
    }
    
    /**
     * Gets the "PROVINCIA" element
     */
    public java.lang.String getPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIA$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PROVINCIA" element
     */
    public org.apache.xmlbeans.XmlString xgetPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCIA$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "PROVINCIA" element
     */
    public boolean isSetPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCIA$14) != 0;
        }
    }
    
    /**
     * Sets the "PROVINCIA" element
     */
    public void setPROVINCIA(java.lang.String provincia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCIA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCIA$14);
            }
            target.setStringValue(provincia);
        }
    }
    
    /**
     * Sets (as xml) the "PROVINCIA" element
     */
    public void xsetPROVINCIA(org.apache.xmlbeans.XmlString provincia)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCIA$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROVINCIA$14);
            }
            target.set(provincia);
        }
    }
    
    /**
     * Unsets the "PROVINCIA" element
     */
    public void unsetPROVINCIA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCIA$14, 0);
        }
    }
    
    /**
     * Gets the "CAP" element
     */
    public java.lang.String getCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CAP" element
     */
    public org.apache.xmlbeans.XmlString xgetCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "CAP" element
     */
    public boolean isSetCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAP$16) != 0;
        }
    }
    
    /**
     * Sets the "CAP" element
     */
    public void setCAP(java.lang.String cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAP$16);
            }
            target.setStringValue(cap);
        }
    }
    
    /**
     * Sets (as xml) the "CAP" element
     */
    public void xsetCAP(org.apache.xmlbeans.XmlString cap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAP$16);
            }
            target.set(cap);
        }
    }
    
    /**
     * Unsets the "CAP" element
     */
    public void unsetCAP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAP$16, 0);
        }
    }
    
    /**
     * Gets the "LOCALITA_IMP" element
     */
    public java.lang.String getLOCALITAIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAIMP$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LOCALITA_IMP" element
     */
    public org.apache.xmlbeans.XmlString xgetLOCALITAIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITAIMP$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "LOCALITA_IMP" element
     */
    public boolean isSetLOCALITAIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITAIMP$18) != 0;
        }
    }
    
    /**
     * Sets the "LOCALITA_IMP" element
     */
    public void setLOCALITAIMP(java.lang.String localitaimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITAIMP$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITAIMP$18);
            }
            target.setStringValue(localitaimp);
        }
    }
    
    /**
     * Sets (as xml) the "LOCALITA_IMP" element
     */
    public void xsetLOCALITAIMP(org.apache.xmlbeans.XmlString localitaimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITAIMP$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALITAIMP$18);
            }
            target.set(localitaimp);
        }
    }
    
    /**
     * Unsets the "LOCALITA_IMP" element
     */
    public void unsetLOCALITAIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITAIMP$18, 0);
        }
    }
    
    /**
     * Gets the "CITTA" element
     */
    public java.lang.String getCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITTA$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CITTA" element
     */
    public org.apache.xmlbeans.XmlString xgetCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITTA$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "CITTA" element
     */
    public boolean isSetCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CITTA$20) != 0;
        }
    }
    
    /**
     * Sets the "CITTA" element
     */
    public void setCITTA(java.lang.String citta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITTA$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITTA$20);
            }
            target.setStringValue(citta);
        }
    }
    
    /**
     * Sets (as xml) the "CITTA" element
     */
    public void xsetCITTA(org.apache.xmlbeans.XmlString citta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITTA$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITTA$20);
            }
            target.set(citta);
        }
    }
    
    /**
     * Unsets the "CITTA" element
     */
    public void unsetCITTA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CITTA$20, 0);
        }
    }
    
    /**
     * Gets the "CODICE_STATO" element
     */
    public java.lang.String getCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICESTATO$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_STATO" element
     */
    public org.apache.xmlbeans.XmlString xgetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICESTATO$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "CODICE_STATO" element
     */
    public boolean isSetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CODICESTATO$22) != 0;
        }
    }
    
    /**
     * Sets the "CODICE_STATO" element
     */
    public void setCODICESTATO(java.lang.String codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICESTATO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICESTATO$22);
            }
            target.setStringValue(codicestato);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_STATO" element
     */
    public void xsetCODICESTATO(org.apache.xmlbeans.XmlString codicestato)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICESTATO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICESTATO$22);
            }
            target.set(codicestato);
        }
    }
    
    /**
     * Unsets the "CODICE_STATO" element
     */
    public void unsetCODICESTATO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CODICESTATO$22, 0);
        }
    }
    
    /**
     * Gets the "TELEFONO" element
     */
    public java.lang.String getTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELEFONO$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TELEFONO" element
     */
    public org.apache.xmlbeans.XmlString xgetTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELEFONO$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "TELEFONO" element
     */
    public boolean isSetTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TELEFONO$24) != 0;
        }
    }
    
    /**
     * Sets the "TELEFONO" element
     */
    public void setTELEFONO(java.lang.String telefono)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELEFONO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TELEFONO$24);
            }
            target.setStringValue(telefono);
        }
    }
    
    /**
     * Sets (as xml) the "TELEFONO" element
     */
    public void xsetTELEFONO(org.apache.xmlbeans.XmlString telefono)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELEFONO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TELEFONO$24);
            }
            target.set(telefono);
        }
    }
    
    /**
     * Unsets the "TELEFONO" element
     */
    public void unsetTELEFONO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TELEFONO$24, 0);
        }
    }
    
    /**
     * Gets the "FAX" element
     */
    public java.lang.String getFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FAX" element
     */
    public org.apache.xmlbeans.XmlString xgetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "FAX" element
     */
    public boolean isSetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FAX$26) != 0;
        }
    }
    
    /**
     * Sets the "FAX" element
     */
    public void setFAX(java.lang.String fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAX$26);
            }
            target.setStringValue(fax);
        }
    }
    
    /**
     * Sets (as xml) the "FAX" element
     */
    public void xsetFAX(org.apache.xmlbeans.XmlString fax)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAX$26);
            }
            target.set(fax);
        }
    }
    
    /**
     * Unsets the "FAX" element
     */
    public void unsetFAX()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FAX$26, 0);
        }
    }
    
    /**
     * Gets the "CELL_IMP" element
     */
    public java.lang.String getCELLIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CELLIMP$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CELL_IMP" element
     */
    public org.apache.xmlbeans.XmlString xgetCELLIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CELLIMP$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "CELL_IMP" element
     */
    public boolean isSetCELLIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CELLIMP$28) != 0;
        }
    }
    
    /**
     * Sets the "CELL_IMP" element
     */
    public void setCELLIMP(java.lang.String cellimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CELLIMP$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CELLIMP$28);
            }
            target.setStringValue(cellimp);
        }
    }
    
    /**
     * Sets (as xml) the "CELL_IMP" element
     */
    public void xsetCELLIMP(org.apache.xmlbeans.XmlString cellimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CELLIMP$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CELLIMP$28);
            }
            target.set(cellimp);
        }
    }
    
    /**
     * Unsets the "CELL_IMP" element
     */
    public void unsetCELLIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CELLIMP$28, 0);
        }
    }
    
    /**
     * Gets the "EMAIL" element
     */
    public java.lang.String getEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "EMAIL" element
     */
    public org.apache.xmlbeans.XmlString xgetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "EMAIL" element
     */
    public boolean isSetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$30) != 0;
        }
    }
    
    /**
     * Sets the "EMAIL" element
     */
    public void setEMAIL(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$30);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "EMAIL" element
     */
    public void xsetEMAIL(org.apache.xmlbeans.XmlString email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$30);
            }
            target.set(email);
        }
    }
    
    /**
     * Unsets the "EMAIL" element
     */
    public void unsetEMAIL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$30, 0);
        }
    }
    
    /**
     * Gets the "PEC_IMP" element
     */
    public java.lang.String getPECIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PECIMP$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PEC_IMP" element
     */
    public org.apache.xmlbeans.XmlString xgetPECIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PECIMP$32, 0);
            return target;
        }
    }
    
    /**
     * True if has "PEC_IMP" element
     */
    public boolean isSetPECIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PECIMP$32) != 0;
        }
    }
    
    /**
     * Sets the "PEC_IMP" element
     */
    public void setPECIMP(java.lang.String pecimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PECIMP$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PECIMP$32);
            }
            target.setStringValue(pecimp);
        }
    }
    
    /**
     * Sets (as xml) the "PEC_IMP" element
     */
    public void xsetPECIMP(org.apache.xmlbeans.XmlString pecimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PECIMP$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PECIMP$32);
            }
            target.set(pecimp);
        }
    }
    
    /**
     * Unsets the "PEC_IMP" element
     */
    public void unsetPECIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PECIMP$32, 0);
        }
    }
    
    /**
     * Gets the "WEB_IMP" element
     */
    public java.lang.String getWEBIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBIMP$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "WEB_IMP" element
     */
    public org.apache.xmlbeans.XmlString xgetWEBIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WEBIMP$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "WEB_IMP" element
     */
    public boolean isSetWEBIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WEBIMP$34) != 0;
        }
    }
    
    /**
     * Sets the "WEB_IMP" element
     */
    public void setWEBIMP(java.lang.String webimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBIMP$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WEBIMP$34);
            }
            target.setStringValue(webimp);
        }
    }
    
    /**
     * Sets (as xml) the "WEB_IMP" element
     */
    public void xsetWEBIMP(org.apache.xmlbeans.XmlString webimp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WEBIMP$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WEBIMP$34);
            }
            target.set(webimp);
        }
    }
    
    /**
     * Unsets the "WEB_IMP" element
     */
    public void unsetWEBIMP()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WEBIMP$34, 0);
        }
    }
    
    /**
     * Gets the "NCCIAA" element
     */
    public java.lang.String getNCCIAA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NCCIAA$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NCCIAA" element
     */
    public org.apache.xmlbeans.XmlString xgetNCCIAA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NCCIAA$36, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NCCIAA" element
     */
    public void setNCCIAA(java.lang.String ncciaa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NCCIAA$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NCCIAA$36);
            }
            target.setStringValue(ncciaa);
        }
    }
    
    /**
     * Sets (as xml) the "NCCIAA" element
     */
    public void xsetNCCIAA(org.apache.xmlbeans.XmlString ncciaa)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NCCIAA$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NCCIAA$36);
            }
            target.set(ncciaa);
        }
    }
    
    /**
     * Gets the "NATURA_GIURIDICA" element
     */
    public java.lang.String getNATURAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NATURAGIURIDICA$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NATURA_GIURIDICA" element
     */
    public org.apache.xmlbeans.XmlString xgetNATURAGIURIDICA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NATURAGIURIDICA$38, 0);
            return target;
        }
    }
    
    /**
     * Sets the "NATURA_GIURIDICA" element
     */
    public void setNATURAGIURIDICA(java.lang.String naturagiuridica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NATURAGIURIDICA$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NATURAGIURIDICA$38);
            }
            target.setStringValue(naturagiuridica);
        }
    }
    
    /**
     * Sets (as xml) the "NATURA_GIURIDICA" element
     */
    public void xsetNATURAGIURIDICA(org.apache.xmlbeans.XmlString naturagiuridica)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NATURAGIURIDICA$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NATURAGIURIDICA$38);
            }
            target.set(naturagiuridica);
        }
    }
    
    /**
     * Gets the "TIPOLOGIA_SOGGETTO" element
     */
    public java.lang.String getTIPOLOGIASOGGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIASOGGETTO$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "TIPOLOGIA_SOGGETTO" element
     */
    public org.apache.xmlbeans.XmlString xgetTIPOLOGIASOGGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIASOGGETTO$40, 0);
            return target;
        }
    }
    
    /**
     * Sets the "TIPOLOGIA_SOGGETTO" element
     */
    public void setTIPOLOGIASOGGETTO(java.lang.String tipologiasoggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIASOGGETTO$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOLOGIASOGGETTO$40);
            }
            target.setStringValue(tipologiasoggetto);
        }
    }
    
    /**
     * Sets (as xml) the "TIPOLOGIA_SOGGETTO" element
     */
    public void xsetTIPOLOGIASOGGETTO(org.apache.xmlbeans.XmlString tipologiasoggetto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIASOGGETTO$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TIPOLOGIASOGGETTO$40);
            }
            target.set(tipologiasoggetto);
        }
    }
    
    /**
     * Gets the "COMUNE_ESTERO" element
     */
    public java.lang.String getCOMUNEESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEESTERO$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COMUNE_ESTERO" element
     */
    public org.apache.xmlbeans.XmlString xgetCOMUNEESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEESTERO$42, 0);
            return target;
        }
    }
    
    /**
     * True if has "COMUNE_ESTERO" element
     */
    public boolean isSetCOMUNEESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMUNEESTERO$42) != 0;
        }
    }
    
    /**
     * Sets the "COMUNE_ESTERO" element
     */
    public void setCOMUNEESTERO(java.lang.String comuneestero)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEESTERO$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMUNEESTERO$42);
            }
            target.setStringValue(comuneestero);
        }
    }
    
    /**
     * Sets (as xml) the "COMUNE_ESTERO" element
     */
    public void xsetCOMUNEESTERO(org.apache.xmlbeans.XmlString comuneestero)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEESTERO$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMUNEESTERO$42);
            }
            target.set(comuneestero);
        }
    }
    
    /**
     * Unsets the "COMUNE_ESTERO" element
     */
    public void unsetCOMUNEESTERO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMUNEESTERO$42, 0);
        }
    }
    
    /**
     * Gets array of all "listaLegaleRappresentante" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante[] getListaLegaleRappresentanteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(LISTALEGALERAPPRESENTANTE$44, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "listaLegaleRappresentante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante getListaLegaleRappresentanteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().find_element_user(LISTALEGALERAPPRESENTANTE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "listaLegaleRappresentante" element
     */
    public boolean isNilListaLegaleRappresentanteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().find_element_user(LISTALEGALERAPPRESENTANTE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "listaLegaleRappresentante" element
     */
    public int sizeOfListaLegaleRappresentanteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LISTALEGALERAPPRESENTANTE$44);
        }
    }
    
    /**
     * Sets array of all "listaLegaleRappresentante" element
     */
    public void setListaLegaleRappresentanteArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante[] listaLegaleRappresentanteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(listaLegaleRappresentanteArray, LISTALEGALERAPPRESENTANTE$44);
        }
    }
    
    /**
     * Sets ith "listaLegaleRappresentante" element
     */
    public void setListaLegaleRappresentanteArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante listaLegaleRappresentante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().find_element_user(LISTALEGALERAPPRESENTANTE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(listaLegaleRappresentante);
        }
    }
    
    /**
     * Nils the ith "listaLegaleRappresentante" element
     */
    public void setNilListaLegaleRappresentanteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().find_element_user(LISTALEGALERAPPRESENTANTE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "listaLegaleRappresentante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante insertNewListaLegaleRappresentante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().insert_element_user(LISTALEGALERAPPRESENTANTE$44, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "listaLegaleRappresentante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante addNewListaLegaleRappresentante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiLegaleRappresentante)get_store().add_element_user(LISTALEGALERAPPRESENTANTE$44);
            return target;
        }
    }
    
    /**
     * Removes the ith "listaLegaleRappresentante" element
     */
    public void removeListaLegaleRappresentante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LISTALEGALERAPPRESENTANTE$44, i);
        }
    }
}
