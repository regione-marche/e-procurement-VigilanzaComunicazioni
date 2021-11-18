/*
 * XML Type:  ListaOfferenti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaOfferenti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaOfferentiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaOfferenti
{
    
    public ListaOfferentiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDOFFERENTI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_Offerenti");
    
    
    /**
     * Gets array of all "Id_Offerenti" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente[] getIdOfferentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDOFFERENTI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_Offerenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente getIdOfferentiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente)get_store().find_element_user(IDOFFERENTI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_Offerenti" element
     */
    public int sizeOfIdOfferentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDOFFERENTI$0);
        }
    }
    
    /**
     * Sets array of all "Id_Offerenti" element
     */
    public void setIdOfferentiArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente[] idOfferentiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idOfferentiArray, IDOFFERENTI$0);
        }
    }
    
    /**
     * Sets ith "Id_Offerenti" element
     */
    public void setIdOfferentiArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente idOfferenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente)get_store().find_element_user(IDOFFERENTI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idOfferenti);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_Offerenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente insertNewIdOfferenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente)get_store().insert_element_user(IDOFFERENTI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_Offerenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente addNewIdOfferenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Offerente)get_store().add_element_user(IDOFFERENTI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_Offerenti" element
     */
    public void removeIdOfferenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDOFFERENTI$0, i);
        }
    }
}
