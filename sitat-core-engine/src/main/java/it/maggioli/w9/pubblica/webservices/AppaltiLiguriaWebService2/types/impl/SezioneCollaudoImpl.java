/*
 * XML Type:  SezioneCollaudo
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML SezioneCollaudo(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class SezioneCollaudoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.SezioneCollaudo
{
    
    public SezioneCollaudoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICOLLAUDO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DATI_COLLAUDO");
    private static final javax.xml.namespace.QName LISTADATISOGGETTIESTESICOLL$2 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "LISTA_DATI_SOGGETTI_ESTESI_COLL");
    
    
    /**
     * Gets the "DATI_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo getDATICOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo)get_store().find_element_user(DATICOLLAUDO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "DATI_COLLAUDO" element
     */
    public void setDATICOLLAUDO(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo daticollaudo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo)get_store().find_element_user(DATICOLLAUDO$0, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo)get_store().add_element_user(DATICOLLAUDO$0);
            }
            target.set(daticollaudo);
        }
    }
    
    /**
     * Appends and returns a new empty "DATI_COLLAUDO" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo addNewDATICOLLAUDO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiCollaudo)get_store().add_element_user(DATICOLLAUDO$0);
            return target;
        }
    }
    
    /**
     * Gets the "LISTA_DATI_SOGGETTI_ESTESI_COLL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi getLISTADATISOGGETTIESTESICOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESICOLL$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "LISTA_DATI_SOGGETTI_ESTESI_COLL" element
     */
    public boolean isSetLISTADATISOGGETTIESTESICOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LISTADATISOGGETTIESTESICOLL$2) != 0;
        }
    }
    
    /**
     * Sets the "LISTA_DATI_SOGGETTI_ESTESI_COLL" element
     */
    public void setLISTADATISOGGETTIESTESICOLL(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi listadatisoggettiestesicoll)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().find_element_user(LISTADATISOGGETTIESTESICOLL$2, 0);
            if (target == null)
            {
                target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESICOLL$2);
            }
            target.set(listadatisoggettiestesicoll);
        }
    }
    
    /**
     * Appends and returns a new empty "LISTA_DATI_SOGGETTI_ESTESI_COLL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi addNewLISTADATISOGGETTIESTESICOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi)get_store().add_element_user(LISTADATISOGGETTIESTESICOLL$2);
            return target;
        }
    }
    
    /**
     * Unsets the "LISTA_DATI_SOGGETTI_ESTESI_COLL" element
     */
    public void unsetLISTADATISOGGETTIESTESICOLL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LISTADATISOGGETTIESTESICOLL$2, 0);
        }
    }
}
