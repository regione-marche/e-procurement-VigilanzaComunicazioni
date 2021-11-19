/*
 * XML Type:  ListaTipologiaL
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaL
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaTipologiaL(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaTipologiaLImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaTipologiaL
{
    
    public ListaTipologiaLImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDTIPOLOGIAL$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_TipologiaL");
    
    
    /**
     * Gets array of all "Id_TipologiaL" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL[] getIdTipologiaLArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDTIPOLOGIAL$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_TipologiaL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL getIdTipologiaLArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL)get_store().find_element_user(IDTIPOLOGIAL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_TipologiaL" element
     */
    public int sizeOfIdTipologiaLArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDTIPOLOGIAL$0);
        }
    }
    
    /**
     * Sets array of all "Id_TipologiaL" element
     */
    public void setIdTipologiaLArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL[] idTipologiaLArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idTipologiaLArray, IDTIPOLOGIAL$0);
        }
    }
    
    /**
     * Sets ith "Id_TipologiaL" element
     */
    public void setIdTipologiaLArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL idTipologiaL)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL)get_store().find_element_user(IDTIPOLOGIAL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idTipologiaL);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_TipologiaL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL insertNewIdTipologiaL(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL)get_store().insert_element_user(IDTIPOLOGIAL$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_TipologiaL" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL addNewIdTipologiaL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.TipologiaL)get_store().add_element_user(IDTIPOLOGIAL$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_TipologiaL" element
     */
    public void removeIdTipologiaL(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDTIPOLOGIAL$0, i);
        }
    }
}
