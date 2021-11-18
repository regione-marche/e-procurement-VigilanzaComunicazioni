/*
 * XML Type:  ListaDatiVariante
 * Namespace: https://appaltiliguria.regione.liguria.it/pubblica/webservices/types
 * Java type: it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante
 *
 * Automatically generated - do not modify.
 */
package it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.impl;
/**
 * An XML ListaDatiVariante(@https://appaltiliguria.regione.liguria.it/pubblica/webservices/types).
 *
 * This is a complex type.
 */
public class ListaDatiVarianteImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.ListaDatiVariante
{
    
    public ListaDatiVarianteImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDDATIVARIANTE$0 = 
        new javax.xml.namespace.QName("https://appaltiliguria.regione.liguria.it/pubblica/webservices/types", "Id_DatiVariante");
    
    
    /**
     * Gets array of all "Id_DatiVariante" elements
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante[] getIdDatiVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDDATIVARIANTE$0, targetList);
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante[] result = new it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "Id_DatiVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante getIdDatiVarianteArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante)get_store().find_element_user(IDDATIVARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "Id_DatiVariante" element
     */
    public int sizeOfIdDatiVarianteArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDDATIVARIANTE$0);
        }
    }
    
    /**
     * Sets array of all "Id_DatiVariante" element
     */
    public void setIdDatiVarianteArray(it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante[] idDatiVarianteArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(idDatiVarianteArray, IDDATIVARIANTE$0);
        }
    }
    
    /**
     * Sets ith "Id_DatiVariante" element
     */
    public void setIdDatiVarianteArray(int i, it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante idDatiVariante)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante)get_store().find_element_user(IDDATIVARIANTE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(idDatiVariante);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Id_DatiVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante insertNewIdDatiVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante)get_store().insert_element_user(IDDATIVARIANTE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Id_DatiVariante" element
     */
    public it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante addNewIdDatiVariante()
    {
        synchronized (monitor())
        {
            check_orphaned();
            it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante target = null;
            target = (it.maggioli.w9.pubblica.webservices.AppaltiLiguriaWebService2.types.DatiVariante)get_store().add_element_user(IDDATIVARIANTE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Id_DatiVariante" element
     */
    public void removeIdDatiVariante(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDDATIVARIANTE$0, i);
        }
    }
}
