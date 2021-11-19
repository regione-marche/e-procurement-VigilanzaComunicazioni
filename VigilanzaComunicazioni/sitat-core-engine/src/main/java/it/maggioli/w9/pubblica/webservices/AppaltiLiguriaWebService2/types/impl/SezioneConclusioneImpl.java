/*
 * XML Type:  SezioneConclusione
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneConclusione(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneConclusioneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneConclusione
{
    
    public SezioneConclusioneImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICONCLUSIONE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_CONCLUSIONE");
    
    
    /**
     * Gets the "DATI_CONCLUSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione getDATICONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione)get_store().find_element_user(DATICONCLUSIONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_CONCLUSIONE" element
     */
    public void setDATICONCLUSIONE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione daticonclusione)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione)get_store().find_element_user(DATICONCLUSIONE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione)get_store().add_element_user(DATICONCLUSIONE$0);
            }
            target.set(daticonclusione);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_CONCLUSIONE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione addNewDATICONCLUSIONE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiConclusione)get_store().add_element_user(DATICONCLUSIONE$0);
            return target;
        }
    }
}
