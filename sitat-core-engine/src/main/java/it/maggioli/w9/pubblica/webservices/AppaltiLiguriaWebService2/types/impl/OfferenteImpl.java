/*
 * XML Type:  Offerente
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Offerente(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class OfferenteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente
{
    
    public OfferenteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TIPOLOGIASOGGETTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "TIPOLOGIA_SOGGETTO");
    private static final javax.xml.namespace.QName FLAGPARTECIPANTE$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "FlagPartecipante");
    private static final javax.xml.namespace.QName AGGIUDICATARIO$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "aggiudicatario");
    private static final javax.xml.namespace.QName RUOLO$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "RUOLO");
    private static final javax.xml.namespace.QName IDGRUPPO$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_GRUPPO");
    
    
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
     * Gets the "FlagPartecipante" element
     */
    public java.lang.String getFlagPartecipante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPARTECIPANTE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FlagPartecipante" element
     */
    public org.apache.xmlbeans.XmlString xgetFlagPartecipante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGPARTECIPANTE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "FlagPartecipante" element
     */
    public void setFlagPartecipante(java.lang.String flagPartecipante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLAGPARTECIPANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLAGPARTECIPANTE$2);
            }
            target.setStringValue(flagPartecipante);
        }
    }
    
    /**
     * Sets (as xml) the "FlagPartecipante" element
     */
    public void xsetFlagPartecipante(org.apache.xmlbeans.XmlString flagPartecipante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLAGPARTECIPANTE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLAGPARTECIPANTE$2);
            }
            target.set(flagPartecipante);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$4, 0);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().find_element_user(AGGIUDICATARIO$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$4);
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
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiAggiudicatari)get_store().add_element_user(AGGIUDICATARIO$4);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUOLO$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RUOLO$6, 0);
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
            return get_store().count_elements(RUOLO$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUOLO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RUOLO$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RUOLO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RUOLO$6);
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
            get_store().remove_element(RUOLO$6, 0);
        }
    }
    
    /**
     * Gets the "ID_GRUPPO" element
     */
    public int getIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGRUPPO$8, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ID_GRUPPO" element
     */
    public org.apache.xmlbeans.XmlInt xgetIDGRUPPO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(IDGRUPPO$8, 0);
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
            return get_store().count_elements(IDGRUPPO$8) != 0;
        }
    }
    
    /**
     * Sets the "ID_GRUPPO" element
     */
    public void setIDGRUPPO(int idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IDGRUPPO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IDGRUPPO$8);
            }
            target.setIntValue(idgruppo);
        }
    }
    
    /**
     * Sets (as xml) the "ID_GRUPPO" element
     */
    public void xsetIDGRUPPO(org.apache.xmlbeans.XmlInt idgruppo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(IDGRUPPO$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(IDGRUPPO$8);
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
            get_store().remove_element(IDGRUPPO$8, 0);
        }
    }
}
