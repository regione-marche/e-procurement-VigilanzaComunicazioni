/*
 * XML Type:  Contratto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML Contratto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ContrattoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Contratto
{
    
    public ContrattoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATIENTE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_ENTE");
    private static final javax.xml.namespace.QName DATIGENERALICONTRATTO$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_GENERALI_CONTRATTO");
    
    
    /**
     * Gets the "DATI_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte getDATIENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().find_element_user(DATIENTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_ENTE" element
     */
    public void setDATIENTE(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte datiente)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().find_element_user(DATIENTE$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().add_element_user(DATIENTE$0);
            }
            target.set(datiente);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_ENTE" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte addNewDATIENTE()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiEnte)get_store().add_element_user(DATIENTE$0);
            return target;
        }
    }
    
    /**
     * Gets the "DATI_GENERALI_CONTRATTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto getDATIGENERALICONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto)get_store().find_element_user(DATIGENERALICONTRATTO$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_GENERALI_CONTRATTO" element
     */
    public void setDATIGENERALICONTRATTO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto datigeneralicontratto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto)get_store().find_element_user(DATIGENERALICONTRATTO$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto)get_store().add_element_user(DATIGENERALICONTRATTO$2);
            }
            target.set(datigeneralicontratto);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_GENERALI_CONTRATTO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto addNewDATIGENERALICONTRATTO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiGeneraliContratto)get_store().add_element_user(DATIGENERALICONTRATTO$2);
            return target;
        }
    }
}
