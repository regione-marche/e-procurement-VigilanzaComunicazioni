/*
 * XML Type:  Aggiudicatario
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Aggiudicatario(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class AggiudicatarioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Aggiudicatario
{
    
    public AggiudicatarioImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOLOGIASOGGETTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TIPOLOGIA_SOGGETTO");
    private static final javax.xml.namespace.QName RUOLO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "RUOLO");
    private static final javax.xml.namespace.QName FLAGAVVALIMENTO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FLAG_AVVALIMENTO");
    private static final javax.xml.namespace.QName IDGRUPPO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_GRUPPO");
    private static final javax.xml.namespace.QName MOTIVOAVVALIMENTO$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "MOTIVO_AVVALIMENTO");
    private static final javax.xml.namespace.QName AGGIUDICATARIO$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "aggiudicatario");
    private static final javax.xml.namespace.QName AGGIUDICATARIOAVV$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "aggiudicatario_avv");
    
    
    /**
     * Gets the "TIPOLOGIA_SOGGETTO" element
     */
    public java.lang.String getTIPOLOGIASOGGETTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIASOGGETTO$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIASOGGETTO$0, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIPOLOGIASOGGETTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIPOLOGIASOGGETTO$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TIPOLOGIASOGGETTO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TIPOLOGIASOGGETTO$0);
            }
            target.set(tipologiasoggetto);
        }
    }
    
    /**
     * Gets the "RUOLO" element
     */
    public java.lang.String getRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUOLO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "RUOLO" element
     */
    public org.apache.xmlbeans.XmlString xgetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RUOLO$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "RUOLO" element
     */
    public boolean isSetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RUOLO$2) != 0;
        }
    }
    
    /**
     * Sets the "RUOLO" element
     */
    public void setRUOLO(java.lang.String ruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUOLO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RUOLO$2);
            }
            target.setStringValue(ruolo);
        }
    }
    
    /**
     * Sets (as xml) the "RUOLO" element
     */
    public void xsetRUOLO(org.apache.xmlbeans.XmlString ruolo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RUOLO$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RUOLO$2);
            }
            target.set(ruolo);
        }
    }
    
    /**
     * Unsets the "RUOLO" element
     */
    public void unsetRUOLO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RUOLO$2, 0);
        }
    }
    
    /**
     * Gets the "FLAG_AVVALIMENTO" element
     */
    public java.lang.String getFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGAVVALIMENTO$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FLAG_AVVALIMENTO" element
     */
    public org.apache.xmlbeans.XmlString xgetFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGAVVALIMENTO$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "FLAG_AVVALIMENTO" element
     */
    public boolean isSetFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLAGAVVALIMENTO$4) != 0;
        }
    }
    
    /**
     * Sets the "FLAG_AVVALIMENTO" element
     */
    public void setFLAGAVVALIMENTO(java.lang.String flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGAVVALIMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGAVVALIMENTO$4);
            }
            target.setStringValue(flagavvalimento);
        }
    }
    
    /**
     * Sets (as xml) the "FLAG_AVVALIMENTO" element
     */
    public void xsetFLAGAVVALIMENTO(org.apache.xmlbeans.XmlString flagavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGAVVALIMENTO$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLAGAVVALIMENTO$4);
            }
            target.set(flagavvalimento);
        }
    }
    
    /**
     * Unsets the "FLAG_AVVALIMENTO" element
     */
    public void unsetFLAGAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLAGAVVALIMENTO$4, 0);
        }
    }
    
    /**
     * Gets the "ID_GRUPPO" element
     */
    public long getIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGRUPPO$6, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GRUPPO" element
     */
    public org.apache.xmlbeans.XmlLong xgetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(IDGRUPPO$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "ID_GRUPPO" element
     */
    public boolean isSetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDGRUPPO$6) != 0;
        }
    }
    
    /**
     * Sets the "ID_GRUPPO" element
     */
    public void setIDGRUPPO(long idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGRUPPO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDGRUPPO$6);
            }
            target.setLongValue(idgruppo);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GRUPPO" element
     */
    public void xsetIDGRUPPO(org.apache.xmlbeans.XmlLong idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(IDGRUPPO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(IDGRUPPO$6);
            }
            target.set(idgruppo);
        }
    }
    
    /**
     * Unsets the "ID_GRUPPO" element
     */
    public void unsetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDGRUPPO$6, 0);
        }
    }
    
    /**
     * Gets the "MOTIVO_AVVALIMENTO" element
     */
    public java.lang.String getMOTIVOAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOAVVALIMENTO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "MOTIVO_AVVALIMENTO" element
     */
    public org.apache.xmlbeans.XmlString xgetMOTIVOAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOAVVALIMENTO$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "MOTIVO_AVVALIMENTO" element
     */
    public boolean isSetMOTIVOAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOTIVOAVVALIMENTO$8) != 0;
        }
    }
    
    /**
     * Sets the "MOTIVO_AVVALIMENTO" element
     */
    public void setMOTIVOAVVALIMENTO(java.lang.String motivoavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOTIVOAVVALIMENTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOTIVOAVVALIMENTO$8);
            }
            target.setStringValue(motivoavvalimento);
        }
    }
    
    /**
     * Sets (as xml) the "MOTIVO_AVVALIMENTO" element
     */
    public void xsetMOTIVOAVVALIMENTO(org.apache.xmlbeans.XmlString motivoavvalimento)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOTIVOAVVALIMENTO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOTIVOAVVALIMENTO$8);
            }
            target.set(motivoavvalimento);
        }
    }
    
    /**
     * Unsets the "MOTIVO_AVVALIMENTO" element
     */
    public void unsetMOTIVOAVVALIMENTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOTIVOAVVALIMENTO$8, 0);
        }
    }
    
    /**
     * Gets the "aggiudicatario" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getAggiudicatario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "aggiudicatario" element
     */
    public void setAggiudicatario(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari aggiudicatario)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$10, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$10);
            }
            target.set(aggiudicatario);
        }
    }
    
    /**
     * Appends and returns a new empty "aggiudicatario" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewAggiudicatario()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$10);
            return target;
        }
    }
    
    /**
     * Gets the "aggiudicatario_avv" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari getAggiudicatarioAvv()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIOAVV$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "aggiudicatario_avv" element
     */
    public boolean isSetAggiudicatarioAvv()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGGIUDICATARIOAVV$12) != 0;
        }
    }
    
    /**
     * Sets the "aggiudicatario_avv" element
     */
    public void setAggiudicatarioAvv(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari aggiudicatarioAvv)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIOAVV$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIOAVV$12);
            }
            target.set(aggiudicatarioAvv);
        }
    }
    
    /**
     * Appends and returns a new empty "aggiudicatario_avv" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari addNewAggiudicatarioAvv()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIOAVV$12);
            return target;
        }
    }
    
    /**
     * Unsets the "aggiudicatario_avv" element
     */
    public void unsetAggiudicatarioAvv()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGGIUDICATARIOAVV$12, 0);
        }
    }
}
