/*
 * XML Type:  ListaCPVSecondari
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCPVSecondari
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaCPVSecondari(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaCPVSecondariImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCPVSecondari
{
    
    public ListaCPVSecondariImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCPVSECONDARI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_CPVSecondari");
    
    
    /**
     * Gets array of all "ID_CPVSecondari" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari[] getIDCPVSecondariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDCPVSECONDARI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "ID_CPVSecondari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari getIDCPVSecondariArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari)get_store().find_element_user(IDCPVSECONDARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "ID_CPVSecondari" element
     */
    public int sizeOfIDCPVSecondariArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDCPVSECONDARI$0);
        }
    }
    
    /**
     * Sets array of all "ID_CPVSecondari" element
     */
    public void setIDCPVSecondariArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari[] idcpvSecondariArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idcpvSecondariArray, IDCPVSECONDARI$0);
        }
    }
    
    /**
     * Sets ith "ID_CPVSecondari" element
     */
    public void setIDCPVSecondariArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari idcpvSecondari)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari)get_store().find_element_user(IDCPVSECONDARI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idcpvSecondari);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "ID_CPVSecondari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari insertNewIDCPVSecondari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari)get_store().insert_element_user(IDCPVSECONDARI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "ID_CPVSecondari" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari addNewIDCPVSecondari()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.CPVSecondari)get_store().add_element_user(IDCPVSECONDARI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "ID_CPVSecondari" element
     */
    public void removeIDCPVSecondari(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDCPVSECONDARI$0, i);
        }
    }
}
