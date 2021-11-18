/*
 * XML Type:  ListaFinanziamenti
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaFinanziamenti(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaFinanziamentiImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaFinanziamenti
{
    
    public ListaFinanziamentiImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDFINANZIAMENTI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_Finanziamenti");
    
    
    /**
     * Gets array of all "Id_Finanziamenti" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti[] getIdFinanziamentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDFINANZIAMENTI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_Finanziamenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti getIdFinanziamentiArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti)get_store().find_element_user(IDFINANZIAMENTI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_Finanziamenti" element
     */
    public int sizeOfIdFinanziamentiArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDFINANZIAMENTI$0);
        }
    }
    
    /**
     * Sets array of all "Id_Finanziamenti" element
     */
    public void setIdFinanziamentiArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti[] idFinanziamentiArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idFinanziamentiArray, IDFINANZIAMENTI$0);
        }
    }
    
    /**
     * Sets ith "Id_Finanziamenti" element
     */
    public void setIdFinanziamentiArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti idFinanziamenti)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti)get_store().find_element_user(IDFINANZIAMENTI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idFinanziamenti);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_Finanziamenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti insertNewIdFinanziamenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti)get_store().insert_element_user(IDFINANZIAMENTI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_Finanziamenti" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti addNewIdFinanziamenti()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Finanziamenti)get_store().add_element_user(IDFINANZIAMENTI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_Finanziamenti" element
     */
    public void removeIdFinanziamenti(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDFINANZIAMENTI$0, i);
        }
    }
}
