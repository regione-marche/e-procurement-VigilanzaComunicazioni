/*
 * XML Type:  DatiResponsabile
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiResponsabile(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiResponsabileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiResponsabile
{
    
    public DatiResponsabileImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INDIRIZZO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "INDIRIZZO");
    private static final javax.xml.namespace.QName CIVICO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CIVICO");
    private static final javax.xml.namespace.QName LOCALITATECN$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LOCALITA_TECN");
    private static final javax.xml.namespace.QName CAP$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CAP");
    private static final javax.xml.namespace.QName TELEFONO$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TELEFONO");
    private static final javax.xml.namespace.QName FAX$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FAX");
    private static final javax.xml.namespace.QName CODICEFISCALE$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_FISCALE");
    private static final javax.xml.namespace.QName CODICEISTATCOMUNE$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_ISTAT_COMUNE");
    private static final javax.xml.namespace.QName EMAIL$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "EMAIL");
    private static final javax.xml.namespace.QName COGNOME$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COGNOME");
    private static final javax.xml.namespace.QName NOME$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NOME");
    private static final javax.xml.namespace.QName PROTECN$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "PRO_TECN");
    private static final javax.xml.namespace.QName CODICESTATO$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CODICE_STATO");
    private static final javax.xml.namespace.QName COMUNEESTERO$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "COMUNE_ESTERO");
    private static final javax.xml.namespace.QName NCCIAA$28 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "NCCIAA");
    private static final javax.xml.namespace.QName ALBOPROFESSIONALE$30 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ALBO_PROFESSIONALE");
    private static final javax.xml.namespace.QName CFASSOCIATI$32 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CF_ASSOCIATI");
    private static final javax.xml.namespace.QName DENOMINAZIONEASSOCIATI$34 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DENOMINAZIONE_ASSOCIATI");
    
    
    /**
     * Gets the "INDIRIZZO" element
     */
    public java.lang.String getINDIRIZZO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$0, 0);
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
            return get_store().count_elements(INDIRIZZO$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INDIRIZZO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INDIRIZZO$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INDIRIZZO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INDIRIZZO$0);
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
            get_store().remove_element(INDIRIZZO$0, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$2, 0);
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
            return get_store().count_elements(CIVICO$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CIVICO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CIVICO$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CIVICO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CIVICO$2);
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
            get_store().remove_element(CIVICO$2, 0);
        }
    }
    
    /**
     * Gets the "LOCALITA_TECN" element
     */
    public java.lang.String getLOCALITATECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITATECN$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LOCALITA_TECN" element
     */
    public org.apache.xmlbeans.XmlString xgetLOCALITATECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITATECN$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "LOCALITA_TECN" element
     */
    public boolean isSetLOCALITATECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITATECN$4) != 0;
        }
    }
    
    /**
     * Sets the "LOCALITA_TECN" element
     */
    public void setLOCALITATECN(java.lang.String localitatecn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITATECN$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITATECN$4);
            }
            target.setStringValue(localitatecn);
        }
    }
    
    /**
     * Sets (as xml) the "LOCALITA_TECN" element
     */
    public void xsetLOCALITATECN(org.apache.xmlbeans.XmlString localitatecn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITATECN$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALITATECN$4);
            }
            target.set(localitatecn);
        }
    }
    
    /**
     * Unsets the "LOCALITA_TECN" element
     */
    public void unsetLOCALITATECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITATECN$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$6, 0);
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
            return get_store().count_elements(CAP$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAP$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CAP$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CAP$6);
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
            get_store().remove_element(CAP$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELEFONO$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELEFONO$8, 0);
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
            return get_store().count_elements(TELEFONO$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELEFONO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TELEFONO$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELEFONO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TELEFONO$8);
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
            get_store().remove_element(TELEFONO$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$10, 0);
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
            return get_store().count_elements(FAX$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAX$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAX$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAX$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAX$10);
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
            get_store().remove_element(FAX$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$12, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEFISCALE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEFISCALE$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEFISCALE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEFISCALE$12);
            }
            target.set(codicefiscale);
        }
    }
    
    /**
     * Gets the "CODICE_ISTAT_COMUNE" element
     */
    public java.lang.String getCODICEISTATCOMUNE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEISTATCOMUNE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CODICE_ISTAT_COMUNE" element
     */
    public org.apache.xmlbeans.XmlString xgetCODICEISTATCOMUNE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEISTATCOMUNE$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CODICE_ISTAT_COMUNE" element
     */
    public void setCODICEISTATCOMUNE(java.lang.String codiceistatcomune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICEISTATCOMUNE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICEISTATCOMUNE$14);
            }
            target.setStringValue(codiceistatcomune);
        }
    }
    
    /**
     * Sets (as xml) the "CODICE_ISTAT_COMUNE" element
     */
    public void xsetCODICEISTATCOMUNE(org.apache.xmlbeans.XmlString codiceistatcomune)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICEISTATCOMUNE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICEISTATCOMUNE$14);
            }
            target.set(codiceistatcomune);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$16, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$16, 0);
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
            return get_store().count_elements(EMAIL$16) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$16);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$16);
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
            get_store().remove_element(EMAIL$16, 0);
        }
    }
    
    /**
     * Gets the "COGNOME" element
     */
    public java.lang.String getCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "COGNOME" element
     */
    public org.apache.xmlbeans.XmlString xgetCOGNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$18, 0);
            return target;
        }
    }
    
    /**
     * Sets the "COGNOME" element
     */
    public void setCOGNOME(java.lang.String cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COGNOME$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COGNOME$18);
            }
            target.setStringValue(cognome);
        }
    }
    
    /**
     * Sets (as xml) the "COGNOME" element
     */
    public void xsetCOGNOME(org.apache.xmlbeans.XmlString cognome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COGNOME$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COGNOME$18);
            }
            target.set(cognome);
        }
    }
    
    /**
     * Gets the "NOME" element
     */
    public java.lang.String getNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "NOME" element
     */
    public org.apache.xmlbeans.XmlString xgetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "NOME" element
     */
    public boolean isSetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NOME$20) != 0;
        }
    }
    
    /**
     * Sets the "NOME" element
     */
    public void setNOME(java.lang.String nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NOME$20);
            }
            target.setStringValue(nome);
        }
    }
    
    /**
     * Sets (as xml) the "NOME" element
     */
    public void xsetNOME(org.apache.xmlbeans.XmlString nome)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NOME$20);
            }
            target.set(nome);
        }
    }
    
    /**
     * Unsets the "NOME" element
     */
    public void unsetNOME()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NOME$20, 0);
        }
    }
    
    /**
     * Gets the "PRO_TECN" element
     */
    public java.lang.String getPROTECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROTECN$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PRO_TECN" element
     */
    public org.apache.xmlbeans.XmlString xgetPROTECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROTECN$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "PRO_TECN" element
     */
    public boolean isSetPROTECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROTECN$22) != 0;
        }
    }
    
    /**
     * Sets the "PRO_TECN" element
     */
    public void setPROTECN(java.lang.String protecn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROTECN$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROTECN$22);
            }
            target.setStringValue(protecn);
        }
    }
    
    /**
     * Sets (as xml) the "PRO_TECN" element
     */
    public void xsetPROTECN(org.apache.xmlbeans.XmlString protecn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROTECN$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROTECN$22);
            }
            target.set(protecn);
        }
    }
    
    /**
     * Unsets the "PRO_TECN" element
     */
    public void unsetPROTECN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROTECN$22, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICESTATO$24, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICESTATO$24, 0);
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
            return get_store().count_elements(CODICESTATO$24) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODICESTATO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODICESTATO$24);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODICESTATO$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODICESTATO$24);
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
            get_store().remove_element(CODICESTATO$24, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEESTERO$26, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEESTERO$26, 0);
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
            return get_store().count_elements(COMUNEESTERO$26) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMUNEESTERO$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMUNEESTERO$26);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMUNEESTERO$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMUNEESTERO$26);
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
            get_store().remove_element(COMUNEESTERO$26, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NCCIAA$28, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NCCIAA$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "NCCIAA" element
     */
    public boolean isSetNCCIAA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NCCIAA$28) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NCCIAA$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NCCIAA$28);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NCCIAA$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NCCIAA$28);
            }
            target.set(ncciaa);
        }
    }
    
    /**
     * Unsets the "NCCIAA" element
     */
    public void unsetNCCIAA()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NCCIAA$28, 0);
        }
    }
    
    /**
     * Gets the "ALBO_PROFESSIONALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum getALBOPROFESSIONALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALBOPROFESSIONALE$30, 0);
            if (target == null)
            {
                return null;
            }
            return (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ALBO_PROFESSIONALE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType xgetALBOPROFESSIONALE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(ALBOPROFESSIONALE$30, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ALBO_PROFESSIONALE" element
     */
    public void setALBOPROFESSIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType.Enum alboprofessionale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALBOPROFESSIONALE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ALBOPROFESSIONALE$30);
            }
            target.setEnumValue(alboprofessionale);
        }
    }
    
    /**
     * Sets (as xml) the "ALBO_PROFESSIONALE" element
     */
    public void xsetALBOPROFESSIONALE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType alboprofessionale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().find_element_user(ALBOPROFESSIONALE$30, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.FlagSNType)get_store().add_element_user(ALBOPROFESSIONALE$30);
            }
            target.set(alboprofessionale);
        }
    }
    
    /**
     * Gets the "CF_ASSOCIATI" element
     */
    public java.lang.String getCFASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CFASSOCIATI$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CF_ASSOCIATI" element
     */
    public org.apache.xmlbeans.XmlString xgetCFASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CFASSOCIATI$32, 0);
            return target;
        }
    }
    
    /**
     * True if has "CF_ASSOCIATI" element
     */
    public boolean isSetCFASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CFASSOCIATI$32) != 0;
        }
    }
    
    /**
     * Sets the "CF_ASSOCIATI" element
     */
    public void setCFASSOCIATI(java.lang.String cfassociati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CFASSOCIATI$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CFASSOCIATI$32);
            }
            target.setStringValue(cfassociati);
        }
    }
    
    /**
     * Sets (as xml) the "CF_ASSOCIATI" element
     */
    public void xsetCFASSOCIATI(org.apache.xmlbeans.XmlString cfassociati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CFASSOCIATI$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CFASSOCIATI$32);
            }
            target.set(cfassociati);
        }
    }
    
    /**
     * Unsets the "CF_ASSOCIATI" element
     */
    public void unsetCFASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CFASSOCIATI$32, 0);
        }
    }
    
    /**
     * Gets the "DENOMINAZIONE_ASSOCIATI" element
     */
    public java.lang.String getDENOMINAZIONEASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEASSOCIATI$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DENOMINAZIONE_ASSOCIATI" element
     */
    public org.apache.xmlbeans.XmlString xgetDENOMINAZIONEASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONEASSOCIATI$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "DENOMINAZIONE_ASSOCIATI" element
     */
    public boolean isSetDENOMINAZIONEASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DENOMINAZIONEASSOCIATI$34) != 0;
        }
    }
    
    /**
     * Sets the "DENOMINAZIONE_ASSOCIATI" element
     */
    public void setDENOMINAZIONEASSOCIATI(java.lang.String denominazioneassociati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DENOMINAZIONEASSOCIATI$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DENOMINAZIONEASSOCIATI$34);
            }
            target.setStringValue(denominazioneassociati);
        }
    }
    
    /**
     * Sets (as xml) the "DENOMINAZIONE_ASSOCIATI" element
     */
    public void xsetDENOMINAZIONEASSOCIATI(org.apache.xmlbeans.XmlString denominazioneassociati)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DENOMINAZIONEASSOCIATI$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DENOMINAZIONEASSOCIATI$34);
            }
            target.set(denominazioneassociati);
        }
    }
    
    /**
     * Unsets the "DENOMINAZIONE_ASSOCIATI" element
     */
    public void unsetDENOMINAZIONEASSOCIATI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DENOMINAZIONEASSOCIATI$34, 0);
        }
    }
}
