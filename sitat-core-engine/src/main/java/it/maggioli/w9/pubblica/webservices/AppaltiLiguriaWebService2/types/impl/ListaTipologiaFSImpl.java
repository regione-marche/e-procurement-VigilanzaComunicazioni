/*
 * XML Type:  ListaTipologiaFS
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaFS
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaTipologiaFS(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaTipologiaFSImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaFS
{
    
    public ListaTipologiaFSImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDTIPOLOGIAFS$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_TipologiaFS");
    
    
    /**
     * Gets array of all "Id_TipologiaFS" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS[] getIdTipologiaFSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDTIPOLOGIAFS$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_TipologiaFS" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS getIdTipologiaFSArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS)get_store().find_element_user(IDTIPOLOGIAFS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_TipologiaFS" element
     */
    public int sizeOfIdTipologiaFSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDTIPOLOGIAFS$0);
        }
    }
    
    /**
     * Sets array of all "Id_TipologiaFS" element
     */
    public void setIdTipologiaFSArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS[] idTipologiaFSArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idTipologiaFSArray, IDTIPOLOGIAFS$0);
        }
    }
    
    /**
     * Sets ith "Id_TipologiaFS" element
     */
    public void setIdTipologiaFSArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS idTipologiaFS)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS)get_store().find_element_user(IDTIPOLOGIAFS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idTipologiaFS);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_TipologiaFS" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS insertNewIdTipologiaFS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS)get_store().insert_element_user(IDTIPOLOGIAFS$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_TipologiaFS" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS addNewIdTipologiaFS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaFS)get_store().add_element_user(IDTIPOLOGIAFS$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_TipologiaFS" element
     */
    public void removeIdTipologiaFS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDTIPOLOGIAFS$0, i);
        }
    }
}
