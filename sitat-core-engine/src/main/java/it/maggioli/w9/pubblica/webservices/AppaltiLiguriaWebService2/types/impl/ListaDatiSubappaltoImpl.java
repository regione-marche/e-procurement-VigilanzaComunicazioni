/*
 * XML Type:  ListaDatiSubappalto
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiSubappalto(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiSubappaltoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiSubappalto
{
    
    public ListaDatiSubappaltoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATISUBAPPALTO$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiSubappalto");
    
    
    /**
     * Gets array of all "Id_DatiSubappalto" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto[] getIdDatiSubappaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATISUBAPPALTO$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiSubappalto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto getIdDatiSubappaltoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto)get_store().find_element_user(IDDATISUBAPPALTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiSubappalto" element
     */
    public int sizeOfIdDatiSubappaltoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATISUBAPPALTO$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiSubappalto" element
     */
    public void setIdDatiSubappaltoArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto[] idDatiSubappaltoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiSubappaltoArray, IDDATISUBAPPALTO$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiSubappalto" element
     */
    public void setIdDatiSubappaltoArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto idDatiSubappalto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto)get_store().find_element_user(IDDATISUBAPPALTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiSubappalto);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiSubappalto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto insertNewIdDatiSubappalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto)get_store().insert_element_user(IDDATISUBAPPALTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiSubappalto" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto addNewIdDatiSubappalto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiSubappalto)get_store().add_element_user(IDDATISUBAPPALTO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiSubappalto" element
     */
    public void removeIdDatiSubappalto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATISUBAPPALTO$0, i);
        }
    }
}
