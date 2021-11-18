/*
 * XML Type:  SezioneRitardiRecessi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneRitardiRecessi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneRitardiRecessiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneRitardiRecessi
{
    
    public SezioneRitardiRecessiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIRITARDIRECESSI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_RITARDI_RECESSI");
    
    
    /**
     * Gets the "DATI_RITARDI_RECESSI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi getDATIRITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi)get_store().find_element_user(DATIRITARDIRECESSI$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "DATI_RITARDI_RECESSI" element
     */
    public boolean isSetDATIRITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATIRITARDIRECESSI$0) != 0;
        }
    }
    
    /**
     * Sets the "DATI_RITARDI_RECESSI" element
     */
    public void setDATIRITARDIRECESSI(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi datiritardirecessi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi)get_store().find_element_user(DATIRITARDIRECESSI$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi)get_store().add_element_user(DATIRITARDIRECESSI$0);
            }
            target.set(datiritardirecessi);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_RITARDI_RECESSI" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi addNewDATIRITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiRitardiRecessi)get_store().add_element_user(DATIRITARDIRECESSI$0);
            return target;
        }
    }
    
    /**
     * Unsets the "DATI_RITARDI_RECESSI" element
     */
    public void unsetDATIRITARDIRECESSI()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATIRITARDIRECESSI$0, 0);
        }
    }
}
