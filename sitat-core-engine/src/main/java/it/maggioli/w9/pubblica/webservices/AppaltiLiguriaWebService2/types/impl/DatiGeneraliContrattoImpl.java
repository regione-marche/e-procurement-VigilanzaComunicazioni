/*
 * XML Type:  DatiGeneraliContratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML DatiGeneraliContratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class DatiGeneraliContrattoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto
{
    
    public DatiGeneraliContrattoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTROLLOINVIO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "CONTROLLO_INVIO");
    private static final javax.xml.namespace.QName DATICOMUNI$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_COMUNI");
    private static final javax.xml.namespace.QName DATICOMUNIESTESI$4 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_COMUNI_ESTESI");
    private static final javax.xml.namespace.QName LISTAOFFERENTI$6 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_OFFERENTI");
    private static final javax.xml.namespace.QName SEZIONEAGGIUDICAZIONE$8 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_AGGIUDICAZIONE");
    private static final javax.xml.namespace.QName SEZIONEINIZIO$10 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_INIZIO");
    private static final javax.xml.namespace.QName SEZIONESAL$12 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_SAL");
    private static final javax.xml.namespace.QName SEZIONEVARIANTI$14 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_VARIANTI");
    private static final javax.xml.namespace.QName SEZIONESOSPENSIONI$16 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_SOSPENSIONI");
    private static final javax.xml.namespace.QName SEZIONECONCLUSIONE$18 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_CONCLUSIONE");
    private static final javax.xml.namespace.QName SEZIONECOLLAUDO$20 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_COLLAUDO");
    private static final javax.xml.namespace.QName SEZIONESUBAPPALTI$22 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_SUBAPPALTI");
    private static final javax.xml.namespace.QName SEZIONERITARDIRECESSI$24 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_RITARDI_RECESSI");
    private static final javax.xml.namespace.QName SEZIONEACCORDIBONARI$26 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "SEZIONE_ACCORDI_BONARI");
    
    
    /**
     * Gets the "CONTROLLO_INVIO" element
     */
    public java.lang.String getCONTROLLOINVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTROLLOINVIO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CONTROLLO_INVIO" element
     */
    public org.apache.xmlbeans.XmlString xgetCONTROLLOINVIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTROLLOINVIO$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CONTROLLO_INVIO" element
     */
    public void setCONTROLLOINVIO(java.lang.String controlloinvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTROLLOINVIO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTROLLOINVIO$0);
            }
            target.setStringValue(controlloinvio);
        }
    }
    
    /**
     * Sets (as xml) the "CONTROLLO_INVIO" element
     */
    public void xsetCONTROLLOINVIO(org.apache.xmlbeans.XmlString controlloinvio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTROLLOINVIO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTROLLOINVIO$0);
            }
            target.set(controlloinvio);
        }
    }
    
    /**
     * Gets the "DATI_COMUNI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni getDATICOMUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni)get_store().find_element_user(DATICOMUNI$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_COMUNI" element
     */
    public void setDATICOMUNI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni daticomuni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni)get_store().find_element_user(DATICOMUNI$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni)get_store().add_element_user(DATICOMUNI$2);
            }
            target.set(daticomuni);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_COMUNI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni addNewDATICOMUNI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuni)get_store().add_element_user(DATICOMUNI$2);
            return target;
        }
    }
    
    /**
     * Gets the "DATI_COMUNI_ESTESI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi getDATICOMUNIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().find_element_user(DATICOMUNIESTESI$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_COMUNI_ESTESI" element
     */
    public void setDATICOMUNIESTESI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi daticomuniestesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().find_element_user(DATICOMUNIESTESI$4, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().add_element_user(DATICOMUNIESTESI$4);
            }
            target.set(daticomuniestesi);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_COMUNI_ESTESI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi addNewDATICOMUNIESTESI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().add_element_user(DATICOMUNIESTESI$4);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_OFFERENTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti getLISTAOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti)get_store().find_element_user(LISTAOFFERENTI$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "LISTA_OFFERENTI" element
     */
    public boolean isSetLISTAOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LISTAOFFERENTI$6) != 0;
        }
    }
    
    /**
     * Sets the "LISTA_OFFERENTI" element
     */
    public void setLISTAOFFERENTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti listaofferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti)get_store().find_element_user(LISTAOFFERENTI$6, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti)get_store().add_element_user(LISTAOFFERENTI$6);
            }
            target.set(listaofferenti);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_OFFERENTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti addNewLISTAOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti)get_store().add_element_user(LISTAOFFERENTI$6);
            return target;
        }
    }
    
    /**
     * Unsets the "LISTA_OFFERENTI" element
     */
    public void unsetLISTAOFFERENTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LISTAOFFERENTI$6, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione getSEZIONEAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione)get_store().find_element_user(SEZIONEAGGIUDICAZIONE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "SEZIONE_AGGIUDICAZIONE" element
     */
    public void setSEZIONEAGGIUDICAZIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione sezioneaggiudicazione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione)get_store().find_element_user(SEZIONEAGGIUDICAZIONE$8, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione)get_store().add_element_user(SEZIONEAGGIUDICAZIONE$8);
            }
            target.set(sezioneaggiudicazione);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_AGGIUDICAZIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione addNewSEZIONEAGGIUDICAZIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAggiudicazione)get_store().add_element_user(SEZIONEAGGIUDICAZIONE$8);
            return target;
        }
    }
    
    /**
     * Gets the "SEZIONE_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio getSEZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio)get_store().find_element_user(SEZIONEINIZIO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_INIZIO" element
     */
    public boolean isSetSEZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONEINIZIO$10) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_INIZIO" element
     */
    public void setSEZIONEINIZIO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio sezioneinizio)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio)get_store().find_element_user(SEZIONEINIZIO$10, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio)get_store().add_element_user(SEZIONEINIZIO$10);
            }
            target.set(sezioneinizio);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_INIZIO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio addNewSEZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneInizio)get_store().add_element_user(SEZIONEINIZIO$10);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_INIZIO" element
     */
    public void unsetSEZIONEINIZIO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONEINIZIO$10, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_SAL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL getSEZIONESAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL)get_store().find_element_user(SEZIONESAL$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_SAL" element
     */
    public boolean isSetSEZIONESAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONESAL$12) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_SAL" element
     */
    public void setSEZIONESAL(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL sezionesal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL)get_store().find_element_user(SEZIONESAL$12, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL)get_store().add_element_user(SEZIONESAL$12);
            }
            target.set(sezionesal);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_SAL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL addNewSEZIONESAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSAL)get_store().add_element_user(SEZIONESAL$12);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_SAL" element
     */
    public void unsetSEZIONESAL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONESAL$12, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_VARIANTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti getSEZIONEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti)get_store().find_element_user(SEZIONEVARIANTI$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_VARIANTI" element
     */
    public boolean isSetSEZIONEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONEVARIANTI$14) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_VARIANTI" element
     */
    public void setSEZIONEVARIANTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti sezionevarianti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti)get_store().find_element_user(SEZIONEVARIANTI$14, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti)get_store().add_element_user(SEZIONEVARIANTI$14);
            }
            target.set(sezionevarianti);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_VARIANTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti addNewSEZIONEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneVarianti)get_store().add_element_user(SEZIONEVARIANTI$14);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_VARIANTI" element
     */
    public void unsetSEZIONEVARIANTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONEVARIANTI$14, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_SOSPENSIONI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni getSEZIONESOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni)get_store().find_element_user(SEZIONESOSPENSIONI$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_SOSPENSIONI" element
     */
    public boolean isSetSEZIONESOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONESOSPENSIONI$16) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_SOSPENSIONI" element
     */
    public void setSEZIONESOSPENSIONI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni sezionesospensioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni)get_store().find_element_user(SEZIONESOSPENSIONI$16, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni)get_store().add_element_user(SEZIONESOSPENSIONI$16);
            }
            target.set(sezionesospensioni);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_SOSPENSIONI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni addNewSEZIONESOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSospensioni)get_store().add_element_user(SEZIONESOSPENSIONI$16);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_SOSPENSIONI" element
     */
    public void unsetSEZIONESOSPENSIONI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONESOSPENSIONI$16, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_CONCLUSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione getSEZIONECONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione)get_store().find_element_user(SEZIONECONCLUSIONE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_CONCLUSIONE" element
     */
    public boolean isSetSEZIONECONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONECONCLUSIONE$18) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_CONCLUSIONE" element
     */
    public void setSEZIONECONCLUSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione sezioneconclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione)get_store().find_element_user(SEZIONECONCLUSIONE$18, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione)get_store().add_element_user(SEZIONECONCLUSIONE$18);
            }
            target.set(sezioneconclusione);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_CONCLUSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione addNewSEZIONECONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione)get_store().add_element_user(SEZIONECONCLUSIONE$18);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_CONCLUSIONE" element
     */
    public void unsetSEZIONECONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONECONCLUSIONE$18, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo getSEZIONECOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo)get_store().find_element_user(SEZIONECOLLAUDO$20, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_COLLAUDO" element
     */
    public boolean isSetSEZIONECOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONECOLLAUDO$20) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_COLLAUDO" element
     */
    public void setSEZIONECOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo sezionecollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo)get_store().find_element_user(SEZIONECOLLAUDO$20, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo)get_store().add_element_user(SEZIONECOLLAUDO$20);
            }
            target.set(sezionecollaudo);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo addNewSEZIONECOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo)get_store().add_element_user(SEZIONECOLLAUDO$20);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_COLLAUDO" element
     */
    public void unsetSEZIONECOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONECOLLAUDO$20, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti getSEZIONESUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti)get_store().find_element_user(SEZIONESUBAPPALTI$22, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_SUBAPPALTI" element
     */
    public boolean isSetSEZIONESUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONESUBAPPALTI$22) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_SUBAPPALTI" element
     */
    public void setSEZIONESUBAPPALTI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti sezionesubappalti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti)get_store().find_element_user(SEZIONESUBAPPALTI$22, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti)get_store().add_element_user(SEZIONESUBAPPALTI$22);
            }
            target.set(sezionesubappalti);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_SUBAPPALTI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti addNewSEZIONESUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneSubappalti)get_store().add_element_user(SEZIONESUBAPPALTI$22);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_SUBAPPALTI" element
     */
    public void unsetSEZIONESUBAPPALTI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONESUBAPPALTI$22, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_RITARDI_RECESSI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi getSEZIONERITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi)get_store().find_element_user(SEZIONERITARDIRECESSI$24, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_RITARDI_RECESSI" element
     */
    public boolean isSetSEZIONERITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONERITARDIRECESSI$24) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_RITARDI_RECESSI" element
     */
    public void setSEZIONERITARDIRECESSI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi sezioneritardirecessi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi)get_store().find_element_user(SEZIONERITARDIRECESSI$24, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi)get_store().add_element_user(SEZIONERITARDIRECESSI$24);
            }
            target.set(sezioneritardirecessi);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_RITARDI_RECESSI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi addNewSEZIONERITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi)get_store().add_element_user(SEZIONERITARDIRECESSI$24);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_RITARDI_RECESSI" element
     */
    public void unsetSEZIONERITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONERITARDIRECESSI$24, 0);
        }
    }
    
    /**
     * Gets the "SEZIONE_ACCORDI_BONARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari getSEZIONEACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari)get_store().find_element_user(SEZIONEACCORDIBONARI$26, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "SEZIONE_ACCORDI_BONARI" element
     */
    public boolean isSetSEZIONEACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SEZIONEACCORDIBONARI$26) != 0;
        }
    }
    
    /**
     * Sets the "SEZIONE_ACCORDI_BONARI" element
     */
    public void setSEZIONEACCORDIBONARI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari sezioneaccordibonari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari)get_store().find_element_user(SEZIONEACCORDIBONARI$26, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari)get_store().add_element_user(SEZIONEACCORDIBONARI$26);
            }
            target.set(sezioneaccordibonari);
        }
    }
    
    /**
     * Appends and returns a new empty "SEZIONE_ACCORDI_BONARI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari addNewSEZIONEACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneAccordiBonari)get_store().add_element_user(SEZIONEACCORDIBONARI$26);
            return target;
        }
    }
    
    /**
     * Unsets the "SEZIONE_ACCORDI_BONARI" element
     */
    public void unsetSEZIONEACCORDIBONARI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SEZIONEACCORDIBONARI$26, 0);
        }
    }
}
