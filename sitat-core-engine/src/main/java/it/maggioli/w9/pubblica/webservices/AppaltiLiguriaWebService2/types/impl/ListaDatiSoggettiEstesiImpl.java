/*
 * XML Type:  ListaDatiSoggettiEstesi
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiSoggettiEstesi(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiSoggettiEstesiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSoggettiEstesi
{
    
    public ListaDatiSoggettiEstesiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATISOGGETTIESTESI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiSoggettiEstesi");
    
    
    /**
     * Gets array of all "Id_DatiSoggettiEstesi" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi[] getIdDatiSoggettiEstesiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATISOGGETTIESTESI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiSoggettiEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi getIdDatiSoggettiEstesiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi)get_store().find_element_user(IDDATISOGGETTIESTESI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiSoggettiEstesi" element
     */
    public int sizeOfIdDatiSoggettiEstesiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATISOGGETTIESTESI$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiSoggettiEstesi" element
     */
    public void setIdDatiSoggettiEstesiArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi[] idDatiSoggettiEstesiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiSoggettiEstesiArray, IDDATISOGGETTIESTESI$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiSoggettiEstesi" element
     */
    public void setIdDatiSoggettiEstesiArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi idDatiSoggettiEstesi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi)get_store().find_element_user(IDDATISOGGETTIESTESI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiSoggettiEstesi);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiSoggettiEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi insertNewIdDatiSoggettiEstesi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi)get_store().insert_element_user(IDDATISOGGETTIESTESI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiSoggettiEstesi" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi addNewIdDatiSoggettiEstesi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSoggettiEstesi)get_store().add_element_user(IDDATISOGGETTIESTESI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiSoggettiEstesi" element
     */
    public void removeIdDatiSoggettiEstesi(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATISOGGETTIESTESI$0, i);
        }
    }
}
