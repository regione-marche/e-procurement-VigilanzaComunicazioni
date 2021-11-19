/*
 * XML Type:  ListaDatiComuniEstesi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiComuniEstesi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiComuniEstesi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiComuniEstesiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiComuniEstesi
{
    
    public ListaDatiComuniEstesiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATICOMUNIESTESI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "DatiComuniEstesi");
    
    
    /**
     * Gets array of all "DatiComuniEstesi" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi[] getDatiComuniEstesiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(DATICOMUNIESTESI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "DatiComuniEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi getDatiComuniEstesiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().find_element_user(DATICOMUNIESTESI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "DatiComuniEstesi" element
     */
    public int sizeOfDatiComuniEstesiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATICOMUNIESTESI$0);
        }
    }
    
    /**
     * Sets array of all "DatiComuniEstesi" element
     */
    public void setDatiComuniEstesiArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi[] datiComuniEstesiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(datiComuniEstesiArray, DATICOMUNIESTESI$0);
        }
    }
    
    /**
     * Sets ith "DatiComuniEstesi" element
     */
    public void setDatiComuniEstesiArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi datiComuniEstesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().find_element_user(DATICOMUNIESTESI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(datiComuniEstesi);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "DatiComuniEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi insertNewDatiComuniEstesi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().insert_element_user(DATICOMUNIESTESI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "DatiComuniEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi addNewDatiComuniEstesi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiComuniEstesi)get_store().add_element_user(DATICOMUNIESTESI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "DatiComuniEstesi" element
     */
    public void removeDatiComuniEstesi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATICOMUNIESTESI$0, i);
        }
    }
}
