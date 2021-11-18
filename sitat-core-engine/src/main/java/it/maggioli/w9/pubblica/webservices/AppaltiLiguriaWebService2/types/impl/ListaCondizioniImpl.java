/*
 * XML Type:  ListaCondizioni
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCondizioni
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaCondizioni(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaCondizioniImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaCondizioni
{
    
    public ListaCondizioniImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDCONDIZIONI$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "ID_Condizioni");
    
    
    /**
     * Gets array of all "ID_Condizioni" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni[] getIDCondizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDCONDIZIONI$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "ID_Condizioni" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni getIDCondizioniArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni)get_store().find_element_user(IDCONDIZIONI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "ID_Condizioni" element
     */
    public int sizeOfIDCondizioniArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDCONDIZIONI$0);
        }
    }
    
    /**
     * Sets array of all "ID_Condizioni" element
     */
    public void setIDCondizioniArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni[] idCondizioniArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idCondizioniArray, IDCONDIZIONI$0);
        }
    }
    
    /**
     * Sets ith "ID_Condizioni" element
     */
    public void setIDCondizioniArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni idCondizioni)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni)get_store().find_element_user(IDCONDIZIONI$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idCondizioni);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "ID_Condizioni" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni insertNewIDCondizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni)get_store().insert_element_user(IDCONDIZIONI$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "ID_Condizioni" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni addNewIDCondizioni()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.Condizioni)get_store().add_element_user(IDCONDIZIONI$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "ID_Condizioni" element
     */
    public void removeIDCondizioni(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDCONDIZIONI$0, i);
        }
    }
}
